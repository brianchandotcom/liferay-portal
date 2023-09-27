/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.service.persistence.impl;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.orm.EntityCache;
import com.liferay.portal.kernel.dao.orm.EntityCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderCache;
import com.liferay.portal.kernel.dao.orm.FinderCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.exception.NoSuchOrgLaborException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.OrgLabor;
import com.liferay.portal.kernel.model.OrgLaborTable;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.service.persistence.OrgLaborPersistence;
import com.liferay.portal.kernel.service.persistence.OrgLaborUtil;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.model.impl.OrgLaborImpl;
import com.liferay.portal.model.impl.OrgLaborModelImpl;

import java.io.Serializable;

import java.lang.reflect.InvocationHandler;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The persistence implementation for the org labor service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class OrgLaborPersistenceImpl
	extends BasePersistenceImpl<OrgLabor> implements OrgLaborPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>OrgLaborUtil</code> to access the org labor persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		OrgLaborImpl.class.getName();

	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List1";

	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List2";

	private FinderPath _finderPathWithPaginationFindAll;
	private FinderPath _finderPathWithoutPaginationFindAll;
	private FinderPath _finderPathCountAll;
	private FinderPath _finderPathWithPaginationFindByOrganizationId;
	private FinderPath _finderPathWithoutPaginationFindByOrganizationId;
	private FinderPath _finderPathCountByOrganizationId;

	/**
	 * Returns all the org labors where organizationId = &#63;.
	 *
	 * @param organizationId the organization ID
	 * @return the matching org labors
	 */
	@Override
	public List<OrgLabor> findByOrganizationId(long organizationId) {
		return findByOrganizationId(
			organizationId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the org labors where organizationId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OrgLaborModelImpl</code>.
	 * </p>
	 *
	 * @param organizationId the organization ID
	 * @param start the lower bound of the range of org labors
	 * @param end the upper bound of the range of org labors (not inclusive)
	 * @return the range of matching org labors
	 */
	@Override
	public List<OrgLabor> findByOrganizationId(
		long organizationId, int start, int end) {

		return findByOrganizationId(organizationId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the org labors where organizationId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OrgLaborModelImpl</code>.
	 * </p>
	 *
	 * @param organizationId the organization ID
	 * @param start the lower bound of the range of org labors
	 * @param end the upper bound of the range of org labors (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching org labors
	 */
	@Override
	public List<OrgLabor> findByOrganizationId(
		long organizationId, int start, int end,
		OrderByComparator<OrgLabor> orderByComparator) {

		return findByOrganizationId(
			organizationId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the org labors where organizationId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OrgLaborModelImpl</code>.
	 * </p>
	 *
	 * @param organizationId the organization ID
	 * @param start the lower bound of the range of org labors
	 * @param end the upper bound of the range of org labors (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching org labors
	 */
	@Override
	public List<OrgLabor> findByOrganizationId(
		long organizationId, int start, int end,
		OrderByComparator<OrgLabor> orderByComparator, boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByOrganizationId;
				finderArgs = new Object[] {organizationId};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByOrganizationId;
			finderArgs = new Object[] {
				organizationId, start, end, orderByComparator
			};
		}

		List<OrgLabor> list = null;

		if (useFinderCache) {
			list = (List<OrgLabor>)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (OrgLabor orgLabor : list) {
					if (organizationId != orgLabor.getOrganizationId()) {
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

			sb.append(_SQL_SELECT_ORGLABOR_WHERE);

			sb.append(_FINDER_COLUMN_ORGANIZATIONID_ORGANIZATIONID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(OrgLaborModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(organizationId);

				list = (List<OrgLabor>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
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
	 * Returns the first org labor in the ordered set where organizationId = &#63;.
	 *
	 * @param organizationId the organization ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching org labor
	 * @throws NoSuchOrgLaborException if a matching org labor could not be found
	 */
	@Override
	public OrgLabor findByOrganizationId_First(
			long organizationId, OrderByComparator<OrgLabor> orderByComparator)
		throws NoSuchOrgLaborException {

		OrgLabor orgLabor = fetchByOrganizationId_First(
			organizationId, orderByComparator);

		if (orgLabor != null) {
			return orgLabor;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("organizationId=");
		sb.append(organizationId);

		sb.append("}");

		throw new NoSuchOrgLaborException(sb.toString());
	}

	/**
	 * Returns the first org labor in the ordered set where organizationId = &#63;.
	 *
	 * @param organizationId the organization ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching org labor, or <code>null</code> if a matching org labor could not be found
	 */
	@Override
	public OrgLabor fetchByOrganizationId_First(
		long organizationId, OrderByComparator<OrgLabor> orderByComparator) {

		List<OrgLabor> list = findByOrganizationId(
			organizationId, 0, 1, orderByComparator);

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
	 * @throws NoSuchOrgLaborException if a matching org labor could not be found
	 */
	@Override
	public OrgLabor findByOrganizationId_Last(
			long organizationId, OrderByComparator<OrgLabor> orderByComparator)
		throws NoSuchOrgLaborException {

		OrgLabor orgLabor = fetchByOrganizationId_Last(
			organizationId, orderByComparator);

		if (orgLabor != null) {
			return orgLabor;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("organizationId=");
		sb.append(organizationId);

		sb.append("}");

		throw new NoSuchOrgLaborException(sb.toString());
	}

	/**
	 * Returns the last org labor in the ordered set where organizationId = &#63;.
	 *
	 * @param organizationId the organization ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching org labor, or <code>null</code> if a matching org labor could not be found
	 */
	@Override
	public OrgLabor fetchByOrganizationId_Last(
		long organizationId, OrderByComparator<OrgLabor> orderByComparator) {

		int count = countByOrganizationId(organizationId);

		if (count == 0) {
			return null;
		}

		List<OrgLabor> list = findByOrganizationId(
			organizationId, count - 1, count, orderByComparator);

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
	 * @throws NoSuchOrgLaborException if a org labor with the primary key could not be found
	 */
	@Override
	public OrgLabor[] findByOrganizationId_PrevAndNext(
			long orgLaborId, long organizationId,
			OrderByComparator<OrgLabor> orderByComparator)
		throws NoSuchOrgLaborException {

		OrgLabor orgLabor = findByPrimaryKey(orgLaborId);

		Session session = null;

		try {
			session = openSession();

			OrgLabor[] array = new OrgLaborImpl[3];

			array[0] = getByOrganizationId_PrevAndNext(
				session, orgLabor, organizationId, orderByComparator, true);

			array[1] = orgLabor;

			array[2] = getByOrganizationId_PrevAndNext(
				session, orgLabor, organizationId, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected OrgLabor getByOrganizationId_PrevAndNext(
		Session session, OrgLabor orgLabor, long organizationId,
		OrderByComparator<OrgLabor> orderByComparator, boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(3);
		}

		sb.append(_SQL_SELECT_ORGLABOR_WHERE);

		sb.append(_FINDER_COLUMN_ORGANIZATIONID_ORGANIZATIONID_2);

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
			sb.append(OrgLaborModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(organizationId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(orgLabor)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<OrgLabor> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the org labors where organizationId = &#63; from the database.
	 *
	 * @param organizationId the organization ID
	 */
	@Override
	public void removeByOrganizationId(long organizationId) {
		for (OrgLabor orgLabor :
				findByOrganizationId(
					organizationId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(orgLabor);
		}
	}

	/**
	 * Returns the number of org labors where organizationId = &#63;.
	 *
	 * @param organizationId the organization ID
	 * @return the number of matching org labors
	 */
	@Override
	public int countByOrganizationId(long organizationId) {
		FinderPath finderPath = _finderPathCountByOrganizationId;

		Object[] finderArgs = new Object[] {organizationId};

		Long count = (Long)FinderCacheUtil.getResult(
			finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_ORGLABOR_WHERE);

			sb.append(_FINDER_COLUMN_ORGANIZATIONID_ORGANIZATIONID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(organizationId);

				count = (Long)query.uniqueResult();

				FinderCacheUtil.putResult(finderPath, finderArgs, count);
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

	private static final String _FINDER_COLUMN_ORGANIZATIONID_ORGANIZATIONID_2 =
		"orgLabor.organizationId = ?";

	public OrgLaborPersistenceImpl() {
		setModelClass(OrgLabor.class);

		setModelImplClass(OrgLaborImpl.class);
		setModelPKClass(long.class);

		setTable(OrgLaborTable.INSTANCE);
	}

	/**
	 * Caches the org labor in the entity cache if it is enabled.
	 *
	 * @param orgLabor the org labor
	 */
	@Override
	public void cacheResult(OrgLabor orgLabor) {
		EntityCacheUtil.putResult(
			OrgLaborImpl.class, orgLabor.getPrimaryKey(), orgLabor);
	}

	private int _valueObjectFinderCacheListThreshold;

	/**
	 * Caches the org labors in the entity cache if it is enabled.
	 *
	 * @param orgLabors the org labors
	 */
	@Override
	public void cacheResult(List<OrgLabor> orgLabors) {
		if ((_valueObjectFinderCacheListThreshold == 0) ||
			((_valueObjectFinderCacheListThreshold > 0) &&
			 (orgLabors.size() > _valueObjectFinderCacheListThreshold))) {

			return;
		}

		for (OrgLabor orgLabor : orgLabors) {
			if (EntityCacheUtil.getResult(
					OrgLaborImpl.class, orgLabor.getPrimaryKey()) == null) {

				cacheResult(orgLabor);
			}
		}
	}

	/**
	 * Clears the cache for all org labors.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		EntityCacheUtil.clearCache(OrgLaborImpl.class);

		FinderCacheUtil.clearCache(OrgLaborImpl.class);
	}

	/**
	 * Clears the cache for the org labor.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(OrgLabor orgLabor) {
		EntityCacheUtil.removeResult(OrgLaborImpl.class, orgLabor);
	}

	@Override
	public void clearCache(List<OrgLabor> orgLabors) {
		for (OrgLabor orgLabor : orgLabors) {
			EntityCacheUtil.removeResult(OrgLaborImpl.class, orgLabor);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		FinderCacheUtil.clearCache(OrgLaborImpl.class);

		for (Serializable primaryKey : primaryKeys) {
			EntityCacheUtil.removeResult(OrgLaborImpl.class, primaryKey);
		}
	}

	/**
	 * Creates a new org labor with the primary key. Does not add the org labor to the database.
	 *
	 * @param orgLaborId the primary key for the new org labor
	 * @return the new org labor
	 */
	@Override
	public OrgLabor create(long orgLaborId) {
		OrgLabor orgLabor = new OrgLaborImpl();

		orgLabor.setNew(true);
		orgLabor.setPrimaryKey(orgLaborId);

		orgLabor.setCompanyId(CompanyThreadLocal.getCompanyId());

		return orgLabor;
	}

	/**
	 * Removes the org labor with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param orgLaborId the primary key of the org labor
	 * @return the org labor that was removed
	 * @throws NoSuchOrgLaborException if a org labor with the primary key could not be found
	 */
	@Override
	public OrgLabor remove(long orgLaborId) throws NoSuchOrgLaborException {
		return remove((Serializable)orgLaborId);
	}

	/**
	 * Removes the org labor with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the org labor
	 * @return the org labor that was removed
	 * @throws NoSuchOrgLaborException if a org labor with the primary key could not be found
	 */
	@Override
	public OrgLabor remove(Serializable primaryKey)
		throws NoSuchOrgLaborException {

		Session session = null;

		try {
			session = openSession();

			OrgLabor orgLabor = (OrgLabor)session.get(
				OrgLaborImpl.class, primaryKey);

			if (orgLabor == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchOrgLaborException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(orgLabor);
		}
		catch (NoSuchOrgLaborException noSuchEntityException) {
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
	protected OrgLabor removeImpl(OrgLabor orgLabor) {
		Session session = null;

		try {
			session = openSession();

			if (!session.contains(orgLabor)) {
				orgLabor = (OrgLabor)session.get(
					OrgLaborImpl.class, orgLabor.getPrimaryKeyObj());
			}

			if (orgLabor != null) {
				session.delete(orgLabor);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
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
	public OrgLabor updateImpl(OrgLabor orgLabor) {
		boolean isNew = orgLabor.isNew();

		if (!(orgLabor instanceof OrgLaborModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(orgLabor.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(orgLabor);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in orgLabor proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom OrgLabor implementation " +
					orgLabor.getClass());
		}

		OrgLaborModelImpl orgLaborModelImpl = (OrgLaborModelImpl)orgLabor;

		Session session = null;

		try {
			session = openSession();

			if (isNew) {
				session.save(orgLabor);
			}
			else {
				orgLabor = (OrgLabor)session.merge(orgLabor);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		EntityCacheUtil.putResult(
			OrgLaborImpl.class, orgLaborModelImpl, false, true);

		if (isNew) {
			orgLabor.setNew(false);
		}

		orgLabor.resetOriginalValues();

		return orgLabor;
	}

	/**
	 * Returns the org labor with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the org labor
	 * @return the org labor
	 * @throws NoSuchOrgLaborException if a org labor with the primary key could not be found
	 */
	@Override
	public OrgLabor findByPrimaryKey(Serializable primaryKey)
		throws NoSuchOrgLaborException {

		OrgLabor orgLabor = fetchByPrimaryKey(primaryKey);

		if (orgLabor == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchOrgLaborException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return orgLabor;
	}

	/**
	 * Returns the org labor with the primary key or throws a <code>NoSuchOrgLaborException</code> if it could not be found.
	 *
	 * @param orgLaborId the primary key of the org labor
	 * @return the org labor
	 * @throws NoSuchOrgLaborException if a org labor with the primary key could not be found
	 */
	@Override
	public OrgLabor findByPrimaryKey(long orgLaborId)
		throws NoSuchOrgLaborException {

		return findByPrimaryKey((Serializable)orgLaborId);
	}

	/**
	 * Returns the org labor with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param orgLaborId the primary key of the org labor
	 * @return the org labor, or <code>null</code> if a org labor with the primary key could not be found
	 */
	@Override
	public OrgLabor fetchByPrimaryKey(long orgLaborId) {
		return fetchByPrimaryKey((Serializable)orgLaborId);
	}

	/**
	 * Returns all the org labors.
	 *
	 * @return the org labors
	 */
	@Override
	public List<OrgLabor> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the org labors.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OrgLaborModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of org labors
	 * @param end the upper bound of the range of org labors (not inclusive)
	 * @return the range of org labors
	 */
	@Override
	public List<OrgLabor> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the org labors.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OrgLaborModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of org labors
	 * @param end the upper bound of the range of org labors (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of org labors
	 */
	@Override
	public List<OrgLabor> findAll(
		int start, int end, OrderByComparator<OrgLabor> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the org labors.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>OrgLaborModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of org labors
	 * @param end the upper bound of the range of org labors (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of org labors
	 */
	@Override
	public List<OrgLabor> findAll(
		int start, int end, OrderByComparator<OrgLabor> orderByComparator,
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

		List<OrgLabor> list = null;

		if (useFinderCache) {
			list = (List<OrgLabor>)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_ORGLABOR);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_ORGLABOR;

				sql = sql.concat(OrgLaborModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<OrgLabor>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
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
	 * Removes all the org labors from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (OrgLabor orgLabor : findAll()) {
			remove(orgLabor);
		}
	}

	/**
	 * Returns the number of org labors.
	 *
	 * @return the number of org labors
	 */
	@Override
	public int countAll() {
		Long count = (Long)FinderCacheUtil.getResult(
			_finderPathCountAll, FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(_SQL_COUNT_ORGLABOR);

				count = (Long)query.uniqueResult();

				FinderCacheUtil.putResult(
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
		return EntityCacheUtil.getEntityCache();
	}

	@Override
	protected String getPKDBName() {
		return "orgLaborId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_ORGLABOR;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return OrgLaborModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the org labor persistence.
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

		_finderPathWithPaginationFindByOrganizationId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByOrganizationId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"organizationId"}, true);

		_finderPathWithoutPaginationFindByOrganizationId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByOrganizationId",
			new String[] {Long.class.getName()},
			new String[] {"organizationId"}, true);

		_finderPathCountByOrganizationId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByOrganizationId",
			new String[] {Long.class.getName()},
			new String[] {"organizationId"}, false);

		OrgLaborUtil.setPersistence(this);
	}

	public void destroy() {
		OrgLaborUtil.setPersistence(null);

		EntityCacheUtil.removeCache(OrgLaborImpl.class.getName());
	}

	private static final String _SQL_SELECT_ORGLABOR =
		"SELECT orgLabor FROM OrgLabor orgLabor";

	private static final String _SQL_SELECT_ORGLABOR_WHERE =
		"SELECT orgLabor FROM OrgLabor orgLabor WHERE ";

	private static final String _SQL_COUNT_ORGLABOR =
		"SELECT COUNT(orgLabor) FROM OrgLabor orgLabor";

	private static final String _SQL_COUNT_ORGLABOR_WHERE =
		"SELECT COUNT(orgLabor) FROM OrgLabor orgLabor WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS = "orgLabor.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No OrgLabor exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No OrgLabor exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		OrgLaborPersistenceImpl.class);

	@Override
	protected FinderCache getFinderCache() {
		return FinderCacheUtil.getFinderCache();
	}

}