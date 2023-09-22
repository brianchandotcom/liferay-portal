/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.service.persistence.impl;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.change.tracking.CTColumnResolutionType;
import com.liferay.portal.kernel.dao.orm.EntityCache;
import com.liferay.portal.kernel.dao.orm.EntityCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderCache;
import com.liferay.portal.kernel.dao.orm.FinderCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.exception.NoSuchPermissionPropagationException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.PermissionPropagation;
import com.liferay.portal.kernel.model.PermissionPropagationTable;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.service.persistence.PermissionPropagationPersistence;
import com.liferay.portal.kernel.service.persistence.PermissionPropagationUtil;
import com.liferay.portal.kernel.service.persistence.change.tracking.helper.CTPersistenceHelperUtil;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.model.impl.PermissionPropagationImpl;
import com.liferay.portal.model.impl.PermissionPropagationModelImpl;

import java.io.Serializable;

import java.lang.reflect.InvocationHandler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The persistence implementation for the permission propagation service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class PermissionPropagationPersistenceImpl
	extends BasePersistenceImpl<PermissionPropagation>
	implements PermissionPropagationPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>PermissionPropagationUtil</code> to access the permission propagation persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		PermissionPropagationImpl.class.getName();

	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List1";

	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List2";

	private FinderPath _finderPathWithPaginationFindAll;
	private FinderPath _finderPathWithoutPaginationFindAll;
	private FinderPath _finderPathCountAll;
	private FinderPath _finderPathFetchByG_C_C_C;
	private FinderPath _finderPathCountByG_C_C_C;

	/**
	 * Returns the permission propagation where groupId = &#63; and companyId = &#63; and classNameId = &#63; and classPK = &#63; or throws a <code>NoSuchPermissionPropagationException</code> if it could not be found.
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the matching permission propagation
	 * @throws NoSuchPermissionPropagationException if a matching permission propagation could not be found
	 */
	@Override
	public PermissionPropagation findByG_C_C_C(
			long groupId, long companyId, long classNameId, long classPK)
		throws NoSuchPermissionPropagationException {

		PermissionPropagation permissionPropagation = fetchByG_C_C_C(
			groupId, companyId, classNameId, classPK);

		if (permissionPropagation == null) {
			StringBundler sb = new StringBundler(10);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("groupId=");
			sb.append(groupId);

			sb.append(", companyId=");
			sb.append(companyId);

			sb.append(", classNameId=");
			sb.append(classNameId);

			sb.append(", classPK=");
			sb.append(classPK);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchPermissionPropagationException(sb.toString());
		}

		return permissionPropagation;
	}

	/**
	 * Returns the permission propagation where groupId = &#63; and companyId = &#63; and classNameId = &#63; and classPK = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the matching permission propagation, or <code>null</code> if a matching permission propagation could not be found
	 */
	@Override
	public PermissionPropagation fetchByG_C_C_C(
		long groupId, long companyId, long classNameId, long classPK) {

		return fetchByG_C_C_C(groupId, companyId, classNameId, classPK, true);
	}

	/**
	 * Returns the permission propagation where groupId = &#63; and companyId = &#63; and classNameId = &#63; and classPK = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching permission propagation, or <code>null</code> if a matching permission propagation could not be found
	 */
	@Override
	public PermissionPropagation fetchByG_C_C_C(
		long groupId, long companyId, long classNameId, long classPK,
		boolean useFinderCache) {

		Object[] finderArgs = null;

		if (useFinderCache) {
			finderArgs = new Object[] {
				groupId, companyId, classNameId, classPK
			};
		}

		Object result = null;

		if (useFinderCache) {
			result = FinderCacheUtil.getResult(
				_finderPathFetchByG_C_C_C, finderArgs, this);
		}

		boolean productionMode = CTPersistenceHelperUtil.isProductionMode(
			PermissionPropagation.class);

		if (result instanceof PermissionPropagation) {
			PermissionPropagation permissionPropagation =
				(PermissionPropagation)result;

			if ((groupId != permissionPropagation.getGroupId()) ||
				(companyId != permissionPropagation.getCompanyId()) ||
				(classNameId != permissionPropagation.getClassNameId()) ||
				(classPK != permissionPropagation.getClassPK())) {

				result = null;
			}
			else if (!CTPersistenceHelperUtil.isProductionMode(
						PermissionPropagation.class,
						permissionPropagation.getPrimaryKey())) {

				result = null;
			}
		}
		else if (!productionMode && (result instanceof List<?>)) {
			result = null;
		}

		if (result == null) {
			StringBundler sb = new StringBundler(6);

			sb.append(_SQL_SELECT_PERMISSIONPROPAGATION_WHERE);

			sb.append(_FINDER_COLUMN_G_C_C_C_GROUPID_2);

			sb.append(_FINDER_COLUMN_G_C_C_C_COMPANYID_2);

			sb.append(_FINDER_COLUMN_G_C_C_C_CLASSNAMEID_2);

			sb.append(_FINDER_COLUMN_G_C_C_C_CLASSPK_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				queryPos.add(companyId);

				queryPos.add(classNameId);

				queryPos.add(classPK);

				List<PermissionPropagation> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache && productionMode) {
						FinderCacheUtil.putResult(
							_finderPathFetchByG_C_C_C, finderArgs, list);
					}
				}
				else {
					PermissionPropagation permissionPropagation = list.get(0);

					result = permissionPropagation;

					cacheResult(permissionPropagation);
				}
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		if (result instanceof List<?>) {
			return null;
		}
		else {
			return (PermissionPropagation)result;
		}
	}

	/**
	 * Removes the permission propagation where groupId = &#63; and companyId = &#63; and classNameId = &#63; and classPK = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the permission propagation that was removed
	 */
	@Override
	public PermissionPropagation removeByG_C_C_C(
			long groupId, long companyId, long classNameId, long classPK)
		throws NoSuchPermissionPropagationException {

		PermissionPropagation permissionPropagation = findByG_C_C_C(
			groupId, companyId, classNameId, classPK);

		return remove(permissionPropagation);
	}

	/**
	 * Returns the number of permission propagations where groupId = &#63; and companyId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the number of matching permission propagations
	 */
	@Override
	public int countByG_C_C_C(
		long groupId, long companyId, long classNameId, long classPK) {

		boolean productionMode = CTPersistenceHelperUtil.isProductionMode(
			PermissionPropagation.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByG_C_C_C;

			finderArgs = new Object[] {
				groupId, companyId, classNameId, classPK
			};

			count = (Long)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(5);

			sb.append(_SQL_COUNT_PERMISSIONPROPAGATION_WHERE);

			sb.append(_FINDER_COLUMN_G_C_C_C_GROUPID_2);

			sb.append(_FINDER_COLUMN_G_C_C_C_COMPANYID_2);

			sb.append(_FINDER_COLUMN_G_C_C_C_CLASSNAMEID_2);

			sb.append(_FINDER_COLUMN_G_C_C_C_CLASSPK_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				queryPos.add(companyId);

				queryPos.add(classNameId);

				queryPos.add(classPK);

				count = (Long)query.uniqueResult();

				if (productionMode) {
					FinderCacheUtil.putResult(finderPath, finderArgs, count);
				}
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

	private static final String _FINDER_COLUMN_G_C_C_C_GROUPID_2 =
		"permissionPropagation.groupId = ? AND ";

	private static final String _FINDER_COLUMN_G_C_C_C_COMPANYID_2 =
		"permissionPropagation.companyId = ? AND ";

	private static final String _FINDER_COLUMN_G_C_C_C_CLASSNAMEID_2 =
		"permissionPropagation.classNameId = ? AND ";

	private static final String _FINDER_COLUMN_G_C_C_C_CLASSPK_2 =
		"permissionPropagation.classPK = ?";

	public PermissionPropagationPersistenceImpl() {
		setModelClass(PermissionPropagation.class);

		setModelImplClass(PermissionPropagationImpl.class);
		setModelPKClass(long.class);

		setTable(PermissionPropagationTable.INSTANCE);
	}

	/**
	 * Caches the permission propagation in the entity cache if it is enabled.
	 *
	 * @param permissionPropagation the permission propagation
	 */
	@Override
	public void cacheResult(PermissionPropagation permissionPropagation) {
		if (permissionPropagation.getCtCollectionId() != 0) {
			return;
		}

		EntityCacheUtil.putResult(
			PermissionPropagationImpl.class,
			permissionPropagation.getPrimaryKey(), permissionPropagation);

		FinderCacheUtil.putResult(
			_finderPathFetchByG_C_C_C,
			new Object[] {
				permissionPropagation.getGroupId(),
				permissionPropagation.getCompanyId(),
				permissionPropagation.getClassNameId(),
				permissionPropagation.getClassPK()
			},
			permissionPropagation);
	}

	private int _valueObjectFinderCacheListThreshold;

	/**
	 * Caches the permission propagations in the entity cache if it is enabled.
	 *
	 * @param permissionPropagations the permission propagations
	 */
	@Override
	public void cacheResult(
		List<PermissionPropagation> permissionPropagations) {

		if ((_valueObjectFinderCacheListThreshold == 0) ||
			((_valueObjectFinderCacheListThreshold > 0) &&
			 (permissionPropagations.size() >
				 _valueObjectFinderCacheListThreshold))) {

			return;
		}

		for (PermissionPropagation permissionPropagation :
				permissionPropagations) {

			if (permissionPropagation.getCtCollectionId() != 0) {
				continue;
			}

			if (EntityCacheUtil.getResult(
					PermissionPropagationImpl.class,
					permissionPropagation.getPrimaryKey()) == null) {

				cacheResult(permissionPropagation);
			}
		}
	}

	/**
	 * Clears the cache for all permission propagations.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		EntityCacheUtil.clearCache(PermissionPropagationImpl.class);

		FinderCacheUtil.clearCache(PermissionPropagationImpl.class);
	}

	/**
	 * Clears the cache for the permission propagation.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(PermissionPropagation permissionPropagation) {
		EntityCacheUtil.removeResult(
			PermissionPropagationImpl.class, permissionPropagation);
	}

	@Override
	public void clearCache(List<PermissionPropagation> permissionPropagations) {
		for (PermissionPropagation permissionPropagation :
				permissionPropagations) {

			EntityCacheUtil.removeResult(
				PermissionPropagationImpl.class, permissionPropagation);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		FinderCacheUtil.clearCache(PermissionPropagationImpl.class);

		for (Serializable primaryKey : primaryKeys) {
			EntityCacheUtil.removeResult(
				PermissionPropagationImpl.class, primaryKey);
		}
	}

	protected void cacheUniqueFindersCache(
		PermissionPropagationModelImpl permissionPropagationModelImpl) {

		Object[] args = new Object[] {
			permissionPropagationModelImpl.getGroupId(),
			permissionPropagationModelImpl.getCompanyId(),
			permissionPropagationModelImpl.getClassNameId(),
			permissionPropagationModelImpl.getClassPK()
		};

		FinderCacheUtil.putResult(
			_finderPathCountByG_C_C_C, args, Long.valueOf(1));
		FinderCacheUtil.putResult(
			_finderPathFetchByG_C_C_C, args, permissionPropagationModelImpl);
	}

	/**
	 * Creates a new permission propagation with the primary key. Does not add the permission propagation to the database.
	 *
	 * @param permissionPropagationId the primary key for the new permission propagation
	 * @return the new permission propagation
	 */
	@Override
	public PermissionPropagation create(long permissionPropagationId) {
		PermissionPropagation permissionPropagation =
			new PermissionPropagationImpl();

		permissionPropagation.setNew(true);
		permissionPropagation.setPrimaryKey(permissionPropagationId);

		permissionPropagation.setCompanyId(CompanyThreadLocal.getCompanyId());

		return permissionPropagation;
	}

	/**
	 * Removes the permission propagation with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param permissionPropagationId the primary key of the permission propagation
	 * @return the permission propagation that was removed
	 * @throws NoSuchPermissionPropagationException if a permission propagation with the primary key could not be found
	 */
	@Override
	public PermissionPropagation remove(long permissionPropagationId)
		throws NoSuchPermissionPropagationException {

		return remove((Serializable)permissionPropagationId);
	}

	/**
	 * Removes the permission propagation with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the permission propagation
	 * @return the permission propagation that was removed
	 * @throws NoSuchPermissionPropagationException if a permission propagation with the primary key could not be found
	 */
	@Override
	public PermissionPropagation remove(Serializable primaryKey)
		throws NoSuchPermissionPropagationException {

		Session session = null;

		try {
			session = openSession();

			PermissionPropagation permissionPropagation =
				(PermissionPropagation)session.get(
					PermissionPropagationImpl.class, primaryKey);

			if (permissionPropagation == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchPermissionPropagationException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(permissionPropagation);
		}
		catch (NoSuchPermissionPropagationException noSuchEntityException) {
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
	protected PermissionPropagation removeImpl(
		PermissionPropagation permissionPropagation) {

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(permissionPropagation)) {
				permissionPropagation = (PermissionPropagation)session.get(
					PermissionPropagationImpl.class,
					permissionPropagation.getPrimaryKeyObj());
			}

			if ((permissionPropagation != null) &&
				CTPersistenceHelperUtil.isRemove(permissionPropagation)) {

				session.delete(permissionPropagation);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (permissionPropagation != null) {
			clearCache(permissionPropagation);
		}

		return permissionPropagation;
	}

	@Override
	public PermissionPropagation updateImpl(
		PermissionPropagation permissionPropagation) {

		boolean isNew = permissionPropagation.isNew();

		if (!(permissionPropagation instanceof
				PermissionPropagationModelImpl)) {

			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(permissionPropagation.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					permissionPropagation);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in permissionPropagation proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom PermissionPropagation implementation " +
					permissionPropagation.getClass());
		}

		PermissionPropagationModelImpl permissionPropagationModelImpl =
			(PermissionPropagationModelImpl)permissionPropagation;

		Session session = null;

		try {
			session = openSession();

			if (CTPersistenceHelperUtil.isInsert(permissionPropagation)) {
				if (!isNew) {
					session.evict(
						PermissionPropagationImpl.class,
						permissionPropagation.getPrimaryKeyObj());
				}

				session.save(permissionPropagation);
			}
			else {
				permissionPropagation = (PermissionPropagation)session.merge(
					permissionPropagation);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (permissionPropagation.getCtCollectionId() != 0) {
			if (isNew) {
				permissionPropagation.setNew(false);
			}

			permissionPropagation.resetOriginalValues();

			return permissionPropagation;
		}

		EntityCacheUtil.putResult(
			PermissionPropagationImpl.class, permissionPropagationModelImpl,
			false, true);

		cacheUniqueFindersCache(permissionPropagationModelImpl);

		if (isNew) {
			permissionPropagation.setNew(false);
		}

		permissionPropagation.resetOriginalValues();

		return permissionPropagation;
	}

	/**
	 * Returns the permission propagation with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the permission propagation
	 * @return the permission propagation
	 * @throws NoSuchPermissionPropagationException if a permission propagation with the primary key could not be found
	 */
	@Override
	public PermissionPropagation findByPrimaryKey(Serializable primaryKey)
		throws NoSuchPermissionPropagationException {

		PermissionPropagation permissionPropagation = fetchByPrimaryKey(
			primaryKey);

		if (permissionPropagation == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchPermissionPropagationException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return permissionPropagation;
	}

	/**
	 * Returns the permission propagation with the primary key or throws a <code>NoSuchPermissionPropagationException</code> if it could not be found.
	 *
	 * @param permissionPropagationId the primary key of the permission propagation
	 * @return the permission propagation
	 * @throws NoSuchPermissionPropagationException if a permission propagation with the primary key could not be found
	 */
	@Override
	public PermissionPropagation findByPrimaryKey(long permissionPropagationId)
		throws NoSuchPermissionPropagationException {

		return findByPrimaryKey((Serializable)permissionPropagationId);
	}

	/**
	 * Returns the permission propagation with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the permission propagation
	 * @return the permission propagation, or <code>null</code> if a permission propagation with the primary key could not be found
	 */
	@Override
	public PermissionPropagation fetchByPrimaryKey(Serializable primaryKey) {
		if (CTPersistenceHelperUtil.isProductionMode(
				PermissionPropagation.class, primaryKey)) {

			return super.fetchByPrimaryKey(primaryKey);
		}

		PermissionPropagation permissionPropagation = null;

		Session session = null;

		try {
			session = openSession();

			permissionPropagation = (PermissionPropagation)session.get(
				PermissionPropagationImpl.class, primaryKey);

			if (permissionPropagation != null) {
				cacheResult(permissionPropagation);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		return permissionPropagation;
	}

	/**
	 * Returns the permission propagation with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param permissionPropagationId the primary key of the permission propagation
	 * @return the permission propagation, or <code>null</code> if a permission propagation with the primary key could not be found
	 */
	@Override
	public PermissionPropagation fetchByPrimaryKey(
		long permissionPropagationId) {

		return fetchByPrimaryKey((Serializable)permissionPropagationId);
	}

	@Override
	public Map<Serializable, PermissionPropagation> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		if (CTPersistenceHelperUtil.isProductionMode(
				PermissionPropagation.class)) {

			return super.fetchByPrimaryKeys(primaryKeys);
		}

		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, PermissionPropagation> map =
			new HashMap<Serializable, PermissionPropagation>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			PermissionPropagation permissionPropagation = fetchByPrimaryKey(
				primaryKey);

			if (permissionPropagation != null) {
				map.put(primaryKey, permissionPropagation);
			}

			return map;
		}

		if ((databaseInMaxParameters > 0) &&
			(primaryKeys.size() > databaseInMaxParameters)) {

			Iterator<Serializable> iterator = primaryKeys.iterator();

			while (iterator.hasNext()) {
				Set<Serializable> page = new HashSet<>();

				for (int i = 0;
					 (i < databaseInMaxParameters) && iterator.hasNext(); i++) {

					page.add(iterator.next());
				}

				map.putAll(fetchByPrimaryKeys(page));
			}

			return map;
		}

		StringBundler sb = new StringBundler((primaryKeys.size() * 2) + 1);

		sb.append(getSelectSQL());
		sb.append(" WHERE ");
		sb.append(getPKDBName());
		sb.append(" IN (");

		for (Serializable primaryKey : primaryKeys) {
			sb.append((long)primaryKey);

			sb.append(",");
		}

		sb.setIndex(sb.index() - 1);

		sb.append(")");

		String sql = sb.toString();

		Session session = null;

		try {
			session = openSession();

			Query query = session.createQuery(sql);

			for (PermissionPropagation permissionPropagation :
					(List<PermissionPropagation>)query.list()) {

				map.put(
					permissionPropagation.getPrimaryKeyObj(),
					permissionPropagation);

				cacheResult(permissionPropagation);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		return map;
	}

	/**
	 * Returns all the permission propagations.
	 *
	 * @return the permission propagations
	 */
	@Override
	public List<PermissionPropagation> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the permission propagations.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>PermissionPropagationModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of permission propagations
	 * @param end the upper bound of the range of permission propagations (not inclusive)
	 * @return the range of permission propagations
	 */
	@Override
	public List<PermissionPropagation> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the permission propagations.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>PermissionPropagationModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of permission propagations
	 * @param end the upper bound of the range of permission propagations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of permission propagations
	 */
	@Override
	public List<PermissionPropagation> findAll(
		int start, int end,
		OrderByComparator<PermissionPropagation> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the permission propagations.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>PermissionPropagationModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of permission propagations
	 * @param end the upper bound of the range of permission propagations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of permission propagations
	 */
	@Override
	public List<PermissionPropagation> findAll(
		int start, int end,
		OrderByComparator<PermissionPropagation> orderByComparator,
		boolean useFinderCache) {

		boolean productionMode = CTPersistenceHelperUtil.isProductionMode(
			PermissionPropagation.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache && productionMode) {
				finderPath = _finderPathWithoutPaginationFindAll;
				finderArgs = FINDER_ARGS_EMPTY;
			}
		}
		else if (useFinderCache && productionMode) {
			finderPath = _finderPathWithPaginationFindAll;
			finderArgs = new Object[] {start, end, orderByComparator};
		}

		List<PermissionPropagation> list = null;

		if (useFinderCache && productionMode) {
			list = (List<PermissionPropagation>)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_PERMISSIONPROPAGATION);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_PERMISSIONPROPAGATION;

				sql = sql.concat(PermissionPropagationModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<PermissionPropagation>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache && productionMode) {
					FinderCacheUtil.putResult(finderPath, finderArgs, list);
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
	 * Removes all the permission propagations from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (PermissionPropagation permissionPropagation : findAll()) {
			remove(permissionPropagation);
		}
	}

	/**
	 * Returns the number of permission propagations.
	 *
	 * @return the number of permission propagations
	 */
	@Override
	public int countAll() {
		boolean productionMode = CTPersistenceHelperUtil.isProductionMode(
			PermissionPropagation.class);

		Long count = null;

		if (productionMode) {
			count = (Long)FinderCacheUtil.getResult(
				_finderPathCountAll, FINDER_ARGS_EMPTY, this);
		}

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(
					_SQL_COUNT_PERMISSIONPROPAGATION);

				count = (Long)query.uniqueResult();

				if (productionMode) {
					FinderCacheUtil.putResult(
						_finderPathCountAll, FINDER_ARGS_EMPTY, count);
				}
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
		return EntityCacheUtil.getEntityCache();
	}

	@Override
	protected String getPKDBName() {
		return "permissionPropagationId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_PERMISSIONPROPAGATION;
	}

	@Override
	public Set<String> getCTColumnNames(
		CTColumnResolutionType ctColumnResolutionType) {

		return _ctColumnNamesMap.getOrDefault(
			ctColumnResolutionType, Collections.emptySet());
	}

	@Override
	public List<String> getMappingTableNames() {
		return _mappingTableNames;
	}

	@Override
	public Map<String, Integer> getTableColumnsMap() {
		return PermissionPropagationModelImpl.TABLE_COLUMNS_MAP;
	}

	@Override
	public String getTableName() {
		return "PermissionPropagation";
	}

	@Override
	public List<String[]> getUniqueIndexColumnNames() {
		return _uniqueIndexColumnNames;
	}

	private static final Map<CTColumnResolutionType, Set<String>>
		_ctColumnNamesMap = new EnumMap<CTColumnResolutionType, Set<String>>(
			CTColumnResolutionType.class);
	private static final List<String> _mappingTableNames =
		new ArrayList<String>();
	private static final List<String[]> _uniqueIndexColumnNames =
		new ArrayList<String[]>();

	static {
		Set<String> ctControlColumnNames = new HashSet<String>();
		Set<String> ctStrictColumnNames = new HashSet<String>();

		ctControlColumnNames.add("mvccVersion");
		ctControlColumnNames.add("ctCollectionId");
		ctStrictColumnNames.add("groupId");
		ctStrictColumnNames.add("companyId");
		ctStrictColumnNames.add("classNameId");
		ctStrictColumnNames.add("classPK");
		ctStrictColumnNames.add("propagate");

		_ctColumnNamesMap.put(
			CTColumnResolutionType.CONTROL, ctControlColumnNames);
		_ctColumnNamesMap.put(
			CTColumnResolutionType.PK,
			Collections.singleton("permissionPropagationId"));
		_ctColumnNamesMap.put(
			CTColumnResolutionType.STRICT, ctStrictColumnNames);

		_uniqueIndexColumnNames.add(
			new String[] {"groupId", "companyId", "classNameId", "classPK"});
	}

	/**
	 * Initializes the permission propagation persistence.
	 */
	public void afterPropertiesSet() {
		_valueObjectFinderCacheListThreshold = GetterUtil.getInteger(
			PropsUtil.get(PropsKeys.VALUE_OBJECT_FINDER_CACHE_LIST_THRESHOLD));

		_finderPathWithPaginationFindAll = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0],
			new String[0], true);

		_finderPathWithoutPaginationFindAll = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0],
			new String[0], true);

		_finderPathCountAll = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll",
			new String[0], new String[0], false);

		_finderPathFetchByG_C_C_C = new FinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByG_C_C_C",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Long.class.getName(), Long.class.getName()
			},
			new String[] {"groupId", "companyId", "classNameId", "classPK"},
			true);

		_finderPathCountByG_C_C_C = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_C_C_C",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Long.class.getName(), Long.class.getName()
			},
			new String[] {"groupId", "companyId", "classNameId", "classPK"},
			false);

		PermissionPropagationUtil.setPersistence(this);
	}

	public void destroy() {
		PermissionPropagationUtil.setPersistence(null);

		EntityCacheUtil.removeCache(PermissionPropagationImpl.class.getName());
	}

	private static final String _SQL_SELECT_PERMISSIONPROPAGATION =
		"SELECT permissionPropagation FROM PermissionPropagation permissionPropagation";

	private static final String _SQL_SELECT_PERMISSIONPROPAGATION_WHERE =
		"SELECT permissionPropagation FROM PermissionPropagation permissionPropagation WHERE ";

	private static final String _SQL_COUNT_PERMISSIONPROPAGATION =
		"SELECT COUNT(permissionPropagation) FROM PermissionPropagation permissionPropagation";

	private static final String _SQL_COUNT_PERMISSIONPROPAGATION_WHERE =
		"SELECT COUNT(permissionPropagation) FROM PermissionPropagation permissionPropagation WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS =
		"permissionPropagation.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No PermissionPropagation exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No PermissionPropagation exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		PermissionPropagationPersistenceImpl.class);

	@Override
	protected FinderCache getFinderCache() {
		return FinderCacheUtil.getFinderCache();
	}

}