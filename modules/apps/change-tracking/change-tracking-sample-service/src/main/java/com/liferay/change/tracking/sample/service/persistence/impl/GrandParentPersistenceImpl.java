/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.change.tracking.sample.service.persistence.impl;

import com.liferay.change.tracking.sample.exception.NoSuchGrandParentException;
import com.liferay.change.tracking.sample.model.GrandParent;
import com.liferay.change.tracking.sample.model.GrandParentTable;
import com.liferay.change.tracking.sample.model.impl.GrandParentImpl;
import com.liferay.change.tracking.sample.model.impl.GrandParentModelImpl;
import com.liferay.change.tracking.sample.service.persistence.GrandParentPersistence;
import com.liferay.change.tracking.sample.service.persistence.GrandParentUtil;
import com.liferay.change.tracking.sample.service.persistence.impl.constants.CTSPersistenceConstants;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.configuration.Configuration;
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
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.ProxyUtil;

import java.io.Serializable;

import java.lang.reflect.InvocationHandler;

import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.sql.DataSource;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * The persistence implementation for the grand parent service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
@Component(service = GrandParentPersistence.class)
public class GrandParentPersistenceImpl
	extends BasePersistenceImpl<GrandParent> implements GrandParentPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>GrandParentUtil</code> to access the grand parent persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		GrandParentImpl.class.getName();

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
	 * Returns all the grand parents where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching grand parents
	 */
	@Override
	public List<GrandParent> findByCompanyId(long companyId) {
		return findByCompanyId(
			companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the grand parents where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>GrandParentModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of grand parents
	 * @param end the upper bound of the range of grand parents (not inclusive)
	 * @return the range of matching grand parents
	 */
	@Override
	public List<GrandParent> findByCompanyId(
		long companyId, int start, int end) {

		return findByCompanyId(companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the grand parents where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>GrandParentModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of grand parents
	 * @param end the upper bound of the range of grand parents (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching grand parents
	 */
	@Override
	public List<GrandParent> findByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<GrandParent> orderByComparator) {

		return findByCompanyId(companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the grand parents where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>GrandParentModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of grand parents
	 * @param end the upper bound of the range of grand parents (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching grand parents
	 */
	@Override
	public List<GrandParent> findByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<GrandParent> orderByComparator,
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

		List<GrandParent> list = null;

		if (useFinderCache) {
			list = (List<GrandParent>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (GrandParent grandParent : list) {
					if (companyId != grandParent.getCompanyId()) {
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

			sb.append(_SQL_SELECT_GRANDPARENT_WHERE);

			sb.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(GrandParentModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(companyId);

				list = (List<GrandParent>)QueryUtil.list(
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
	 * Returns the first grand parent in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching grand parent
	 * @throws NoSuchGrandParentException if a matching grand parent could not be found
	 */
	@Override
	public GrandParent findByCompanyId_First(
			long companyId, OrderByComparator<GrandParent> orderByComparator)
		throws NoSuchGrandParentException {

		GrandParent grandParent = fetchByCompanyId_First(
			companyId, orderByComparator);

		if (grandParent != null) {
			return grandParent;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchGrandParentException(sb.toString());
	}

	/**
	 * Returns the first grand parent in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching grand parent, or <code>null</code> if a matching grand parent could not be found
	 */
	@Override
	public GrandParent fetchByCompanyId_First(
		long companyId, OrderByComparator<GrandParent> orderByComparator) {

		List<GrandParent> list = findByCompanyId(
			companyId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last grand parent in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching grand parent
	 * @throws NoSuchGrandParentException if a matching grand parent could not be found
	 */
	@Override
	public GrandParent findByCompanyId_Last(
			long companyId, OrderByComparator<GrandParent> orderByComparator)
		throws NoSuchGrandParentException {

		GrandParent grandParent = fetchByCompanyId_Last(
			companyId, orderByComparator);

		if (grandParent != null) {
			return grandParent;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchGrandParentException(sb.toString());
	}

	/**
	 * Returns the last grand parent in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching grand parent, or <code>null</code> if a matching grand parent could not be found
	 */
	@Override
	public GrandParent fetchByCompanyId_Last(
		long companyId, OrderByComparator<GrandParent> orderByComparator) {

		int count = countByCompanyId(companyId);

		if (count == 0) {
			return null;
		}

		List<GrandParent> list = findByCompanyId(
			companyId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the grand parents before and after the current grand parent in the ordered set where companyId = &#63;.
	 *
	 * @param grandParentId the primary key of the current grand parent
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next grand parent
	 * @throws NoSuchGrandParentException if a grand parent with the primary key could not be found
	 */
	@Override
	public GrandParent[] findByCompanyId_PrevAndNext(
			long grandParentId, long companyId,
			OrderByComparator<GrandParent> orderByComparator)
		throws NoSuchGrandParentException {

		GrandParent grandParent = findByPrimaryKey(grandParentId);

		Session session = null;

		try {
			session = openSession();

			GrandParent[] array = new GrandParentImpl[3];

			array[0] = getByCompanyId_PrevAndNext(
				session, grandParent, companyId, orderByComparator, true);

			array[1] = grandParent;

			array[2] = getByCompanyId_PrevAndNext(
				session, grandParent, companyId, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected GrandParent getByCompanyId_PrevAndNext(
		Session session, GrandParent grandParent, long companyId,
		OrderByComparator<GrandParent> orderByComparator, boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(3);
		}

		sb.append(_SQL_SELECT_GRANDPARENT_WHERE);

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
			sb.append(GrandParentModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(companyId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(grandParent)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<GrandParent> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the grand parents where companyId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 */
	@Override
	public void removeByCompanyId(long companyId) {
		for (GrandParent grandParent :
				findByCompanyId(
					companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(grandParent);
		}
	}

	/**
	 * Returns the number of grand parents where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching grand parents
	 */
	@Override
	public int countByCompanyId(long companyId) {
		FinderPath finderPath = _finderPathCountByCompanyId;

		Object[] finderArgs = new Object[] {companyId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_GRANDPARENT_WHERE);

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
		"grandParent.companyId = ?";

	public GrandParentPersistenceImpl() {
		setModelClass(GrandParent.class);

		setModelImplClass(GrandParentImpl.class);
		setModelPKClass(long.class);

		setTable(GrandParentTable.INSTANCE);
	}

	/**
	 * Caches the grand parent in the entity cache if it is enabled.
	 *
	 * @param grandParent the grand parent
	 */
	@Override
	public void cacheResult(GrandParent grandParent) {
		entityCache.putResult(
			GrandParentImpl.class, grandParent.getPrimaryKey(), grandParent);
	}

	private int _valueObjectFinderCacheListThreshold;

	/**
	 * Caches the grand parents in the entity cache if it is enabled.
	 *
	 * @param grandParents the grand parents
	 */
	@Override
	public void cacheResult(List<GrandParent> grandParents) {
		if ((_valueObjectFinderCacheListThreshold == 0) ||
			((_valueObjectFinderCacheListThreshold > 0) &&
			 (grandParents.size() > _valueObjectFinderCacheListThreshold))) {

			return;
		}

		for (GrandParent grandParent : grandParents) {
			if (entityCache.getResult(
					GrandParentImpl.class, grandParent.getPrimaryKey()) ==
						null) {

				cacheResult(grandParent);
			}
		}
	}

	/**
	 * Clears the cache for all grand parents.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(GrandParentImpl.class);

		finderCache.clearCache(GrandParentImpl.class);
	}

	/**
	 * Clears the cache for the grand parent.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(GrandParent grandParent) {
		entityCache.removeResult(GrandParentImpl.class, grandParent);
	}

	@Override
	public void clearCache(List<GrandParent> grandParents) {
		for (GrandParent grandParent : grandParents) {
			entityCache.removeResult(GrandParentImpl.class, grandParent);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(GrandParentImpl.class);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(GrandParentImpl.class, primaryKey);
		}
	}

	/**
	 * Creates a new grand parent with the primary key. Does not add the grand parent to the database.
	 *
	 * @param grandParentId the primary key for the new grand parent
	 * @return the new grand parent
	 */
	@Override
	public GrandParent create(long grandParentId) {
		GrandParent grandParent = new GrandParentImpl();

		grandParent.setNew(true);
		grandParent.setPrimaryKey(grandParentId);

		grandParent.setCompanyId(CompanyThreadLocal.getCompanyId());

		return grandParent;
	}

	/**
	 * Removes the grand parent with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param grandParentId the primary key of the grand parent
	 * @return the grand parent that was removed
	 * @throws NoSuchGrandParentException if a grand parent with the primary key could not be found
	 */
	@Override
	public GrandParent remove(long grandParentId)
		throws NoSuchGrandParentException {

		return remove((Serializable)grandParentId);
	}

	/**
	 * Removes the grand parent with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the grand parent
	 * @return the grand parent that was removed
	 * @throws NoSuchGrandParentException if a grand parent with the primary key could not be found
	 */
	@Override
	public GrandParent remove(Serializable primaryKey)
		throws NoSuchGrandParentException {

		Session session = null;

		try {
			session = openSession();

			GrandParent grandParent = (GrandParent)session.get(
				GrandParentImpl.class, primaryKey);

			if (grandParent == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchGrandParentException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(grandParent);
		}
		catch (NoSuchGrandParentException noSuchEntityException) {
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
	protected GrandParent removeImpl(GrandParent grandParent) {
		Session session = null;

		try {
			session = openSession();

			if (!session.contains(grandParent)) {
				grandParent = (GrandParent)session.get(
					GrandParentImpl.class, grandParent.getPrimaryKeyObj());
			}

			if (grandParent != null) {
				session.delete(grandParent);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (grandParent != null) {
			clearCache(grandParent);
		}

		return grandParent;
	}

	@Override
	public GrandParent updateImpl(GrandParent grandParent) {
		boolean isNew = grandParent.isNew();

		if (!(grandParent instanceof GrandParentModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(grandParent.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(grandParent);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in grandParent proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom GrandParent implementation " +
					grandParent.getClass());
		}

		GrandParentModelImpl grandParentModelImpl =
			(GrandParentModelImpl)grandParent;

		Session session = null;

		try {
			session = openSession();

			if (isNew) {
				session.save(grandParent);
			}
			else {
				grandParent = (GrandParent)session.merge(grandParent);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		entityCache.putResult(
			GrandParentImpl.class, grandParentModelImpl, false, true);

		if (isNew) {
			grandParent.setNew(false);
		}

		grandParent.resetOriginalValues();

		return grandParent;
	}

	/**
	 * Returns the grand parent with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the grand parent
	 * @return the grand parent
	 * @throws NoSuchGrandParentException if a grand parent with the primary key could not be found
	 */
	@Override
	public GrandParent findByPrimaryKey(Serializable primaryKey)
		throws NoSuchGrandParentException {

		GrandParent grandParent = fetchByPrimaryKey(primaryKey);

		if (grandParent == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchGrandParentException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return grandParent;
	}

	/**
	 * Returns the grand parent with the primary key or throws a <code>NoSuchGrandParentException</code> if it could not be found.
	 *
	 * @param grandParentId the primary key of the grand parent
	 * @return the grand parent
	 * @throws NoSuchGrandParentException if a grand parent with the primary key could not be found
	 */
	@Override
	public GrandParent findByPrimaryKey(long grandParentId)
		throws NoSuchGrandParentException {

		return findByPrimaryKey((Serializable)grandParentId);
	}

	/**
	 * Returns the grand parent with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param grandParentId the primary key of the grand parent
	 * @return the grand parent, or <code>null</code> if a grand parent with the primary key could not be found
	 */
	@Override
	public GrandParent fetchByPrimaryKey(long grandParentId) {
		return fetchByPrimaryKey((Serializable)grandParentId);
	}

	/**
	 * Returns all the grand parents.
	 *
	 * @return the grand parents
	 */
	@Override
	public List<GrandParent> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the grand parents.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>GrandParentModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of grand parents
	 * @param end the upper bound of the range of grand parents (not inclusive)
	 * @return the range of grand parents
	 */
	@Override
	public List<GrandParent> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the grand parents.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>GrandParentModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of grand parents
	 * @param end the upper bound of the range of grand parents (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of grand parents
	 */
	@Override
	public List<GrandParent> findAll(
		int start, int end, OrderByComparator<GrandParent> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the grand parents.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>GrandParentModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of grand parents
	 * @param end the upper bound of the range of grand parents (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of grand parents
	 */
	@Override
	public List<GrandParent> findAll(
		int start, int end, OrderByComparator<GrandParent> orderByComparator,
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

		List<GrandParent> list = null;

		if (useFinderCache) {
			list = (List<GrandParent>)finderCache.getResult(
				finderPath, finderArgs, this);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_GRANDPARENT);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_GRANDPARENT;

				sql = sql.concat(GrandParentModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<GrandParent>)QueryUtil.list(
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
	 * Removes all the grand parents from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (GrandParent grandParent : findAll()) {
			remove(grandParent);
		}
	}

	/**
	 * Returns the number of grand parents.
	 *
	 * @return the number of grand parents
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(
			_finderPathCountAll, FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(_SQL_COUNT_GRANDPARENT);

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
		return "grandParentId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_GRANDPARENT;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return GrandParentModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the grand parent persistence.
	 */
	@Activate
	public void activate() {
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

		GrandParentUtil.setPersistence(this);
	}

	@Deactivate
	public void deactivate() {
		GrandParentUtil.setPersistence(null);

		entityCache.removeCache(GrandParentImpl.class.getName());
	}

	@Override
	@Reference(
		target = CTSPersistenceConstants.SERVICE_CONFIGURATION_FILTER,
		unbind = "-"
	)
	public void setConfiguration(Configuration configuration) {
	}

	@Override
	@Reference(
		target = CTSPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setDataSource(DataSource dataSource) {
		super.setDataSource(dataSource);
	}

	@Override
	@Reference(
		target = CTSPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setSessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	@Reference
	protected EntityCache entityCache;

	@Reference
	protected FinderCache finderCache;

	private static final String _SQL_SELECT_GRANDPARENT =
		"SELECT grandParent FROM GrandParent grandParent";

	private static final String _SQL_SELECT_GRANDPARENT_WHERE =
		"SELECT grandParent FROM GrandParent grandParent WHERE ";

	private static final String _SQL_COUNT_GRANDPARENT =
		"SELECT COUNT(grandParent) FROM GrandParent grandParent";

	private static final String _SQL_COUNT_GRANDPARENT_WHERE =
		"SELECT COUNT(grandParent) FROM GrandParent grandParent WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS = "grandParent.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No GrandParent exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No GrandParent exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		GrandParentPersistenceImpl.class);

	@Override
	protected FinderCache getFinderCache() {
		return finderCache;
	}

}