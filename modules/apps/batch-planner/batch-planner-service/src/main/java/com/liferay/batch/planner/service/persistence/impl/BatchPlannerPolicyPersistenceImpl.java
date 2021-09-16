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

package com.liferay.batch.planner.service.persistence.impl;

import com.liferay.batch.planner.exception.NoSuchPolicyException;
import com.liferay.batch.planner.model.BatchPlannerPolicy;
import com.liferay.batch.planner.model.BatchPlannerPolicyTable;
import com.liferay.batch.planner.model.impl.BatchPlannerPolicyImpl;
import com.liferay.batch.planner.model.impl.BatchPlannerPolicyModelImpl;
import com.liferay.batch.planner.service.persistence.BatchPlannerPolicyPersistence;
import com.liferay.batch.planner.service.persistence.impl.constants.BatchPlannerPersistenceConstants;
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
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.service.persistence.BasePersistence;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.ProxyUtil;

import java.io.Serializable;

import java.lang.reflect.InvocationHandler;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import javax.sql.DataSource;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * The persistence implementation for the batch planner policy service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Igor Beslic
 * @generated
 */
@Component(
	service = {BatchPlannerPolicyPersistence.class, BasePersistence.class}
)
public class BatchPlannerPolicyPersistenceImpl
	extends BasePersistenceImpl<BatchPlannerPolicy>
	implements BatchPlannerPolicyPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>BatchPlannerPolicyUtil</code> to access the batch planner policy persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		BatchPlannerPolicyImpl.class.getName();

	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List1";

	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List2";

	private FinderPath _finderPathWithPaginationFindAll;
	private FinderPath _finderPathWithoutPaginationFindAll;
	private FinderPath _finderPathCountAll;
	private FinderPath _finderPathWithPaginationFindByBatchPlannerPlanId;
	private FinderPath _finderPathWithoutPaginationFindByBatchPlannerPlanId;
	private FinderPath _finderPathCountByBatchPlannerPlanId;

	/**
	 * Returns all the batch planner policies where batchPlannerPlanId = &#63;.
	 *
	 * @param batchPlannerPlanId the batch planner plan ID
	 * @return the matching batch planner policies
	 */
	@Override
	public List<BatchPlannerPolicy> findByBatchPlannerPlanId(
		long batchPlannerPlanId) {

		return findByBatchPlannerPlanId(
			batchPlannerPlanId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the batch planner policies where batchPlannerPlanId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BatchPlannerPolicyModelImpl</code>.
	 * </p>
	 *
	 * @param batchPlannerPlanId the batch planner plan ID
	 * @param start the lower bound of the range of batch planner policies
	 * @param end the upper bound of the range of batch planner policies (not inclusive)
	 * @return the range of matching batch planner policies
	 */
	@Override
	public List<BatchPlannerPolicy> findByBatchPlannerPlanId(
		long batchPlannerPlanId, int start, int end) {

		return findByBatchPlannerPlanId(batchPlannerPlanId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the batch planner policies where batchPlannerPlanId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BatchPlannerPolicyModelImpl</code>.
	 * </p>
	 *
	 * @param batchPlannerPlanId the batch planner plan ID
	 * @param start the lower bound of the range of batch planner policies
	 * @param end the upper bound of the range of batch planner policies (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching batch planner policies
	 */
	@Override
	public List<BatchPlannerPolicy> findByBatchPlannerPlanId(
		long batchPlannerPlanId, int start, int end,
		OrderByComparator<BatchPlannerPolicy> orderByComparator) {

		return findByBatchPlannerPlanId(
			batchPlannerPlanId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the batch planner policies where batchPlannerPlanId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BatchPlannerPolicyModelImpl</code>.
	 * </p>
	 *
	 * @param batchPlannerPlanId the batch planner plan ID
	 * @param start the lower bound of the range of batch planner policies
	 * @param end the upper bound of the range of batch planner policies (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching batch planner policies
	 */
	@Override
	public List<BatchPlannerPolicy> findByBatchPlannerPlanId(
		long batchPlannerPlanId, int start, int end,
		OrderByComparator<BatchPlannerPolicy> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath =
					_finderPathWithoutPaginationFindByBatchPlannerPlanId;
				finderArgs = new Object[] {batchPlannerPlanId};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByBatchPlannerPlanId;
			finderArgs = new Object[] {
				batchPlannerPlanId, start, end, orderByComparator
			};
		}

		List<BatchPlannerPolicy> list = null;

		if (useFinderCache) {
			list = (List<BatchPlannerPolicy>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (BatchPlannerPolicy batchPlannerPolicy : list) {
					if (batchPlannerPlanId !=
							batchPlannerPolicy.getBatchPlannerPlanId()) {

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

			sb.append(_SQL_SELECT_BATCHPLANNERPOLICY_WHERE);

			sb.append(_FINDER_COLUMN_BATCHPLANNERPLANID_BATCHPLANNERPLANID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(BatchPlannerPolicyModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(batchPlannerPlanId);

				list = (List<BatchPlannerPolicy>)QueryUtil.list(
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
	 * Returns the first batch planner policy in the ordered set where batchPlannerPlanId = &#63;.
	 *
	 * @param batchPlannerPlanId the batch planner plan ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching batch planner policy
	 * @throws NoSuchPolicyException if a matching batch planner policy could not be found
	 */
	@Override
	public BatchPlannerPolicy findByBatchPlannerPlanId_First(
			long batchPlannerPlanId,
			OrderByComparator<BatchPlannerPolicy> orderByComparator)
		throws NoSuchPolicyException {

		BatchPlannerPolicy batchPlannerPolicy = fetchByBatchPlannerPlanId_First(
			batchPlannerPlanId, orderByComparator);

		if (batchPlannerPolicy != null) {
			return batchPlannerPolicy;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("batchPlannerPlanId=");
		sb.append(batchPlannerPlanId);

		sb.append("}");

		throw new NoSuchPolicyException(sb.toString());
	}

	/**
	 * Returns the first batch planner policy in the ordered set where batchPlannerPlanId = &#63;.
	 *
	 * @param batchPlannerPlanId the batch planner plan ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching batch planner policy, or <code>null</code> if a matching batch planner policy could not be found
	 */
	@Override
	public BatchPlannerPolicy fetchByBatchPlannerPlanId_First(
		long batchPlannerPlanId,
		OrderByComparator<BatchPlannerPolicy> orderByComparator) {

		List<BatchPlannerPolicy> list = findByBatchPlannerPlanId(
			batchPlannerPlanId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last batch planner policy in the ordered set where batchPlannerPlanId = &#63;.
	 *
	 * @param batchPlannerPlanId the batch planner plan ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching batch planner policy
	 * @throws NoSuchPolicyException if a matching batch planner policy could not be found
	 */
	@Override
	public BatchPlannerPolicy findByBatchPlannerPlanId_Last(
			long batchPlannerPlanId,
			OrderByComparator<BatchPlannerPolicy> orderByComparator)
		throws NoSuchPolicyException {

		BatchPlannerPolicy batchPlannerPolicy = fetchByBatchPlannerPlanId_Last(
			batchPlannerPlanId, orderByComparator);

		if (batchPlannerPolicy != null) {
			return batchPlannerPolicy;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("batchPlannerPlanId=");
		sb.append(batchPlannerPlanId);

		sb.append("}");

		throw new NoSuchPolicyException(sb.toString());
	}

	/**
	 * Returns the last batch planner policy in the ordered set where batchPlannerPlanId = &#63;.
	 *
	 * @param batchPlannerPlanId the batch planner plan ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching batch planner policy, or <code>null</code> if a matching batch planner policy could not be found
	 */
	@Override
	public BatchPlannerPolicy fetchByBatchPlannerPlanId_Last(
		long batchPlannerPlanId,
		OrderByComparator<BatchPlannerPolicy> orderByComparator) {

		int count = countByBatchPlannerPlanId(batchPlannerPlanId);

		if (count == 0) {
			return null;
		}

		List<BatchPlannerPolicy> list = findByBatchPlannerPlanId(
			batchPlannerPlanId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the batch planner policies before and after the current batch planner policy in the ordered set where batchPlannerPlanId = &#63;.
	 *
	 * @param batchPlannerPolicyId the primary key of the current batch planner policy
	 * @param batchPlannerPlanId the batch planner plan ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next batch planner policy
	 * @throws NoSuchPolicyException if a batch planner policy with the primary key could not be found
	 */
	@Override
	public BatchPlannerPolicy[] findByBatchPlannerPlanId_PrevAndNext(
			long batchPlannerPolicyId, long batchPlannerPlanId,
			OrderByComparator<BatchPlannerPolicy> orderByComparator)
		throws NoSuchPolicyException {

		BatchPlannerPolicy batchPlannerPolicy = findByPrimaryKey(
			batchPlannerPolicyId);

		Session session = null;

		try {
			session = openSession();

			BatchPlannerPolicy[] array = new BatchPlannerPolicyImpl[3];

			array[0] = getByBatchPlannerPlanId_PrevAndNext(
				session, batchPlannerPolicy, batchPlannerPlanId,
				orderByComparator, true);

			array[1] = batchPlannerPolicy;

			array[2] = getByBatchPlannerPlanId_PrevAndNext(
				session, batchPlannerPolicy, batchPlannerPlanId,
				orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected BatchPlannerPolicy getByBatchPlannerPlanId_PrevAndNext(
		Session session, BatchPlannerPolicy batchPlannerPolicy,
		long batchPlannerPlanId,
		OrderByComparator<BatchPlannerPolicy> orderByComparator,
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

		sb.append(_SQL_SELECT_BATCHPLANNERPOLICY_WHERE);

		sb.append(_FINDER_COLUMN_BATCHPLANNERPLANID_BATCHPLANNERPLANID_2);

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
			sb.append(BatchPlannerPolicyModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(batchPlannerPlanId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						batchPlannerPolicy)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<BatchPlannerPolicy> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the batch planner policies where batchPlannerPlanId = &#63; from the database.
	 *
	 * @param batchPlannerPlanId the batch planner plan ID
	 */
	@Override
	public void removeByBatchPlannerPlanId(long batchPlannerPlanId) {
		for (BatchPlannerPolicy batchPlannerPolicy :
				findByBatchPlannerPlanId(
					batchPlannerPlanId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(batchPlannerPolicy);
		}
	}

	/**
	 * Returns the number of batch planner policies where batchPlannerPlanId = &#63;.
	 *
	 * @param batchPlannerPlanId the batch planner plan ID
	 * @return the number of matching batch planner policies
	 */
	@Override
	public int countByBatchPlannerPlanId(long batchPlannerPlanId) {
		FinderPath finderPath = _finderPathCountByBatchPlannerPlanId;

		Object[] finderArgs = new Object[] {batchPlannerPlanId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_BATCHPLANNERPOLICY_WHERE);

			sb.append(_FINDER_COLUMN_BATCHPLANNERPLANID_BATCHPLANNERPLANID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(batchPlannerPlanId);

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

	private static final String
		_FINDER_COLUMN_BATCHPLANNERPLANID_BATCHPLANNERPLANID_2 =
			"batchPlannerPolicy.batchPlannerPlanId = ?";

	private FinderPath _finderPathFetchByBPPI_N;
	private FinderPath _finderPathCountByBPPI_N;

	/**
	 * Returns the batch planner policy where batchPlannerPlanId = &#63; and name = &#63; or throws a <code>NoSuchPolicyException</code> if it could not be found.
	 *
	 * @param batchPlannerPlanId the batch planner plan ID
	 * @param name the name
	 * @return the matching batch planner policy
	 * @throws NoSuchPolicyException if a matching batch planner policy could not be found
	 */
	@Override
	public BatchPlannerPolicy findByBPPI_N(long batchPlannerPlanId, String name)
		throws NoSuchPolicyException {

		BatchPlannerPolicy batchPlannerPolicy = fetchByBPPI_N(
			batchPlannerPlanId, name);

		if (batchPlannerPolicy == null) {
			StringBundler sb = new StringBundler(6);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("batchPlannerPlanId=");
			sb.append(batchPlannerPlanId);

			sb.append(", name=");
			sb.append(name);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchPolicyException(sb.toString());
		}

		return batchPlannerPolicy;
	}

	/**
	 * Returns the batch planner policy where batchPlannerPlanId = &#63; and name = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param batchPlannerPlanId the batch planner plan ID
	 * @param name the name
	 * @return the matching batch planner policy, or <code>null</code> if a matching batch planner policy could not be found
	 */
	@Override
	public BatchPlannerPolicy fetchByBPPI_N(
		long batchPlannerPlanId, String name) {

		return fetchByBPPI_N(batchPlannerPlanId, name, true);
	}

	/**
	 * Returns the batch planner policy where batchPlannerPlanId = &#63; and name = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param batchPlannerPlanId the batch planner plan ID
	 * @param name the name
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching batch planner policy, or <code>null</code> if a matching batch planner policy could not be found
	 */
	@Override
	public BatchPlannerPolicy fetchByBPPI_N(
		long batchPlannerPlanId, String name, boolean useFinderCache) {

		name = Objects.toString(name, "");

		Object[] finderArgs = null;

		if (useFinderCache) {
			finderArgs = new Object[] {batchPlannerPlanId, name};
		}

		Object result = null;

		if (useFinderCache) {
			result = finderCache.getResult(
				_finderPathFetchByBPPI_N, finderArgs);
		}

		if (result instanceof BatchPlannerPolicy) {
			BatchPlannerPolicy batchPlannerPolicy = (BatchPlannerPolicy)result;

			if ((batchPlannerPlanId !=
					batchPlannerPolicy.getBatchPlannerPlanId()) ||
				!Objects.equals(name, batchPlannerPolicy.getName())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_SELECT_BATCHPLANNERPOLICY_WHERE);

			sb.append(_FINDER_COLUMN_BPPI_N_BATCHPLANNERPLANID_2);

			boolean bindName = false;

			if (name.isEmpty()) {
				sb.append(_FINDER_COLUMN_BPPI_N_NAME_3);
			}
			else {
				bindName = true;

				sb.append(_FINDER_COLUMN_BPPI_N_NAME_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(batchPlannerPlanId);

				if (bindName) {
					queryPos.add(name);
				}

				List<BatchPlannerPolicy> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache) {
						finderCache.putResult(
							_finderPathFetchByBPPI_N, finderArgs, list);
					}
				}
				else {
					BatchPlannerPolicy batchPlannerPolicy = list.get(0);

					result = batchPlannerPolicy;

					cacheResult(batchPlannerPolicy);
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
			return (BatchPlannerPolicy)result;
		}
	}

	/**
	 * Removes the batch planner policy where batchPlannerPlanId = &#63; and name = &#63; from the database.
	 *
	 * @param batchPlannerPlanId the batch planner plan ID
	 * @param name the name
	 * @return the batch planner policy that was removed
	 */
	@Override
	public BatchPlannerPolicy removeByBPPI_N(
			long batchPlannerPlanId, String name)
		throws NoSuchPolicyException {

		BatchPlannerPolicy batchPlannerPolicy = findByBPPI_N(
			batchPlannerPlanId, name);

		return remove(batchPlannerPolicy);
	}

	/**
	 * Returns the number of batch planner policies where batchPlannerPlanId = &#63; and name = &#63;.
	 *
	 * @param batchPlannerPlanId the batch planner plan ID
	 * @param name the name
	 * @return the number of matching batch planner policies
	 */
	@Override
	public int countByBPPI_N(long batchPlannerPlanId, String name) {
		name = Objects.toString(name, "");

		FinderPath finderPath = _finderPathCountByBPPI_N;

		Object[] finderArgs = new Object[] {batchPlannerPlanId, name};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_BATCHPLANNERPOLICY_WHERE);

			sb.append(_FINDER_COLUMN_BPPI_N_BATCHPLANNERPLANID_2);

			boolean bindName = false;

			if (name.isEmpty()) {
				sb.append(_FINDER_COLUMN_BPPI_N_NAME_3);
			}
			else {
				bindName = true;

				sb.append(_FINDER_COLUMN_BPPI_N_NAME_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(batchPlannerPlanId);

				if (bindName) {
					queryPos.add(name);
				}

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

	private static final String _FINDER_COLUMN_BPPI_N_BATCHPLANNERPLANID_2 =
		"batchPlannerPolicy.batchPlannerPlanId = ? AND ";

	private static final String _FINDER_COLUMN_BPPI_N_NAME_2 =
		"batchPlannerPolicy.name = ?";

	private static final String _FINDER_COLUMN_BPPI_N_NAME_3 =
		"(batchPlannerPolicy.name IS NULL OR batchPlannerPolicy.name = '')";

	public BatchPlannerPolicyPersistenceImpl() {
		setModelClass(BatchPlannerPolicy.class);

		setModelImplClass(BatchPlannerPolicyImpl.class);
		setModelPKClass(long.class);

		setTable(BatchPlannerPolicyTable.INSTANCE);
	}

	/**
	 * Caches the batch planner policy in the entity cache if it is enabled.
	 *
	 * @param batchPlannerPolicy the batch planner policy
	 */
	@Override
	public void cacheResult(BatchPlannerPolicy batchPlannerPolicy) {
		entityCache.putResult(
			BatchPlannerPolicyImpl.class, batchPlannerPolicy.getPrimaryKey(),
			batchPlannerPolicy);

		finderCache.putResult(
			_finderPathFetchByBPPI_N,
			new Object[] {
				batchPlannerPolicy.getBatchPlannerPlanId(),
				batchPlannerPolicy.getName()
			},
			batchPlannerPolicy);
	}

	private int _valueObjectFinderCacheListThreshold;

	/**
	 * Caches the batch planner policies in the entity cache if it is enabled.
	 *
	 * @param batchPlannerPolicies the batch planner policies
	 */
	@Override
	public void cacheResult(List<BatchPlannerPolicy> batchPlannerPolicies) {
		if ((_valueObjectFinderCacheListThreshold == 0) ||
			((_valueObjectFinderCacheListThreshold > 0) &&
			 (batchPlannerPolicies.size() >
				 _valueObjectFinderCacheListThreshold))) {

			return;
		}

		for (BatchPlannerPolicy batchPlannerPolicy : batchPlannerPolicies) {
			if (entityCache.getResult(
					BatchPlannerPolicyImpl.class,
					batchPlannerPolicy.getPrimaryKey()) == null) {

				cacheResult(batchPlannerPolicy);
			}
		}
	}

	/**
	 * Clears the cache for all batch planner policies.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(BatchPlannerPolicyImpl.class);

		finderCache.clearCache(BatchPlannerPolicyImpl.class);
	}

	/**
	 * Clears the cache for the batch planner policy.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(BatchPlannerPolicy batchPlannerPolicy) {
		entityCache.removeResult(
			BatchPlannerPolicyImpl.class, batchPlannerPolicy);
	}

	@Override
	public void clearCache(List<BatchPlannerPolicy> batchPlannerPolicies) {
		for (BatchPlannerPolicy batchPlannerPolicy : batchPlannerPolicies) {
			entityCache.removeResult(
				BatchPlannerPolicyImpl.class, batchPlannerPolicy);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(BatchPlannerPolicyImpl.class);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(BatchPlannerPolicyImpl.class, primaryKey);
		}
	}

	protected void cacheUniqueFindersCache(
		BatchPlannerPolicyModelImpl batchPlannerPolicyModelImpl) {

		Object[] args = new Object[] {
			batchPlannerPolicyModelImpl.getBatchPlannerPlanId(),
			batchPlannerPolicyModelImpl.getName()
		};

		finderCache.putResult(_finderPathCountByBPPI_N, args, Long.valueOf(1));
		finderCache.putResult(
			_finderPathFetchByBPPI_N, args, batchPlannerPolicyModelImpl);
	}

	/**
	 * Creates a new batch planner policy with the primary key. Does not add the batch planner policy to the database.
	 *
	 * @param batchPlannerPolicyId the primary key for the new batch planner policy
	 * @return the new batch planner policy
	 */
	@Override
	public BatchPlannerPolicy create(long batchPlannerPolicyId) {
		BatchPlannerPolicy batchPlannerPolicy = new BatchPlannerPolicyImpl();

		batchPlannerPolicy.setNew(true);
		batchPlannerPolicy.setPrimaryKey(batchPlannerPolicyId);

		batchPlannerPolicy.setCompanyId(CompanyThreadLocal.getCompanyId());

		return batchPlannerPolicy;
	}

	/**
	 * Removes the batch planner policy with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param batchPlannerPolicyId the primary key of the batch planner policy
	 * @return the batch planner policy that was removed
	 * @throws NoSuchPolicyException if a batch planner policy with the primary key could not be found
	 */
	@Override
	public BatchPlannerPolicy remove(long batchPlannerPolicyId)
		throws NoSuchPolicyException {

		return remove((Serializable)batchPlannerPolicyId);
	}

	/**
	 * Removes the batch planner policy with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the batch planner policy
	 * @return the batch planner policy that was removed
	 * @throws NoSuchPolicyException if a batch planner policy with the primary key could not be found
	 */
	@Override
	public BatchPlannerPolicy remove(Serializable primaryKey)
		throws NoSuchPolicyException {

		Session session = null;

		try {
			session = openSession();

			BatchPlannerPolicy batchPlannerPolicy =
				(BatchPlannerPolicy)session.get(
					BatchPlannerPolicyImpl.class, primaryKey);

			if (batchPlannerPolicy == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchPolicyException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(batchPlannerPolicy);
		}
		catch (NoSuchPolicyException noSuchEntityException) {
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
	protected BatchPlannerPolicy removeImpl(
		BatchPlannerPolicy batchPlannerPolicy) {

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(batchPlannerPolicy)) {
				batchPlannerPolicy = (BatchPlannerPolicy)session.get(
					BatchPlannerPolicyImpl.class,
					batchPlannerPolicy.getPrimaryKeyObj());
			}

			if (batchPlannerPolicy != null) {
				session.delete(batchPlannerPolicy);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (batchPlannerPolicy != null) {
			clearCache(batchPlannerPolicy);
		}

		return batchPlannerPolicy;
	}

	@Override
	public BatchPlannerPolicy updateImpl(
		BatchPlannerPolicy batchPlannerPolicy) {

		boolean isNew = batchPlannerPolicy.isNew();

		if (!(batchPlannerPolicy instanceof BatchPlannerPolicyModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(batchPlannerPolicy.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					batchPlannerPolicy);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in batchPlannerPolicy proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom BatchPlannerPolicy implementation " +
					batchPlannerPolicy.getClass());
		}

		BatchPlannerPolicyModelImpl batchPlannerPolicyModelImpl =
			(BatchPlannerPolicyModelImpl)batchPlannerPolicy;

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Date date = new Date();

		if (isNew && (batchPlannerPolicy.getCreateDate() == null)) {
			if (serviceContext == null) {
				batchPlannerPolicy.setCreateDate(date);
			}
			else {
				batchPlannerPolicy.setCreateDate(
					serviceContext.getCreateDate(date));
			}
		}

		if (!batchPlannerPolicyModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				batchPlannerPolicy.setModifiedDate(date);
			}
			else {
				batchPlannerPolicy.setModifiedDate(
					serviceContext.getModifiedDate(date));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (isNew) {
				session.save(batchPlannerPolicy);
			}
			else {
				batchPlannerPolicy = (BatchPlannerPolicy)session.merge(
					batchPlannerPolicy);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		entityCache.putResult(
			BatchPlannerPolicyImpl.class, batchPlannerPolicyModelImpl, false,
			true);

		cacheUniqueFindersCache(batchPlannerPolicyModelImpl);

		if (isNew) {
			batchPlannerPolicy.setNew(false);
		}

		batchPlannerPolicy.resetOriginalValues();

		return batchPlannerPolicy;
	}

	/**
	 * Returns the batch planner policy with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the batch planner policy
	 * @return the batch planner policy
	 * @throws NoSuchPolicyException if a batch planner policy with the primary key could not be found
	 */
	@Override
	public BatchPlannerPolicy findByPrimaryKey(Serializable primaryKey)
		throws NoSuchPolicyException {

		BatchPlannerPolicy batchPlannerPolicy = fetchByPrimaryKey(primaryKey);

		if (batchPlannerPolicy == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchPolicyException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return batchPlannerPolicy;
	}

	/**
	 * Returns the batch planner policy with the primary key or throws a <code>NoSuchPolicyException</code> if it could not be found.
	 *
	 * @param batchPlannerPolicyId the primary key of the batch planner policy
	 * @return the batch planner policy
	 * @throws NoSuchPolicyException if a batch planner policy with the primary key could not be found
	 */
	@Override
	public BatchPlannerPolicy findByPrimaryKey(long batchPlannerPolicyId)
		throws NoSuchPolicyException {

		return findByPrimaryKey((Serializable)batchPlannerPolicyId);
	}

	/**
	 * Returns the batch planner policy with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param batchPlannerPolicyId the primary key of the batch planner policy
	 * @return the batch planner policy, or <code>null</code> if a batch planner policy with the primary key could not be found
	 */
	@Override
	public BatchPlannerPolicy fetchByPrimaryKey(long batchPlannerPolicyId) {
		return fetchByPrimaryKey((Serializable)batchPlannerPolicyId);
	}

	/**
	 * Returns all the batch planner policies.
	 *
	 * @return the batch planner policies
	 */
	@Override
	public List<BatchPlannerPolicy> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the batch planner policies.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BatchPlannerPolicyModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of batch planner policies
	 * @param end the upper bound of the range of batch planner policies (not inclusive)
	 * @return the range of batch planner policies
	 */
	@Override
	public List<BatchPlannerPolicy> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the batch planner policies.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BatchPlannerPolicyModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of batch planner policies
	 * @param end the upper bound of the range of batch planner policies (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of batch planner policies
	 */
	@Override
	public List<BatchPlannerPolicy> findAll(
		int start, int end,
		OrderByComparator<BatchPlannerPolicy> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the batch planner policies.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BatchPlannerPolicyModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of batch planner policies
	 * @param end the upper bound of the range of batch planner policies (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of batch planner policies
	 */
	@Override
	public List<BatchPlannerPolicy> findAll(
		int start, int end,
		OrderByComparator<BatchPlannerPolicy> orderByComparator,
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

		List<BatchPlannerPolicy> list = null;

		if (useFinderCache) {
			list = (List<BatchPlannerPolicy>)finderCache.getResult(
				finderPath, finderArgs);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_BATCHPLANNERPOLICY);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_BATCHPLANNERPOLICY;

				sql = sql.concat(BatchPlannerPolicyModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<BatchPlannerPolicy>)QueryUtil.list(
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
	 * Removes all the batch planner policies from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (BatchPlannerPolicy batchPlannerPolicy : findAll()) {
			remove(batchPlannerPolicy);
		}
	}

	/**
	 * Returns the number of batch planner policies.
	 *
	 * @return the number of batch planner policies
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(
			_finderPathCountAll, FINDER_ARGS_EMPTY);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(
					_SQL_COUNT_BATCHPLANNERPOLICY);

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
		return "batchPlannerPolicyId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_BATCHPLANNERPOLICY;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return BatchPlannerPolicyModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the batch planner policy persistence.
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

		_finderPathWithPaginationFindByBatchPlannerPlanId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByBatchPlannerPlanId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"batchPlannerPlanId"}, true);

		_finderPathWithoutPaginationFindByBatchPlannerPlanId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByBatchPlannerPlanId", new String[] {Long.class.getName()},
			new String[] {"batchPlannerPlanId"}, true);

		_finderPathCountByBatchPlannerPlanId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByBatchPlannerPlanId", new String[] {Long.class.getName()},
			new String[] {"batchPlannerPlanId"}, false);

		_finderPathFetchByBPPI_N = new FinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByBPPI_N",
			new String[] {Long.class.getName(), String.class.getName()},
			new String[] {"batchPlannerPlanId", "name"}, true);

		_finderPathCountByBPPI_N = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByBPPI_N",
			new String[] {Long.class.getName(), String.class.getName()},
			new String[] {"batchPlannerPlanId", "name"}, false);
	}

	@Deactivate
	public void deactivate() {
		entityCache.removeCache(BatchPlannerPolicyImpl.class.getName());
	}

	@Override
	@Reference(
		target = BatchPlannerPersistenceConstants.SERVICE_CONFIGURATION_FILTER,
		unbind = "-"
	)
	public void setConfiguration(Configuration configuration) {
	}

	@Override
	@Reference(
		target = BatchPlannerPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setDataSource(DataSource dataSource) {
		super.setDataSource(dataSource);
	}

	@Override
	@Reference(
		target = BatchPlannerPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setSessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	@Reference
	protected EntityCache entityCache;

	@Reference
	protected FinderCache finderCache;

	private static final String _SQL_SELECT_BATCHPLANNERPOLICY =
		"SELECT batchPlannerPolicy FROM BatchPlannerPolicy batchPlannerPolicy";

	private static final String _SQL_SELECT_BATCHPLANNERPOLICY_WHERE =
		"SELECT batchPlannerPolicy FROM BatchPlannerPolicy batchPlannerPolicy WHERE ";

	private static final String _SQL_COUNT_BATCHPLANNERPOLICY =
		"SELECT COUNT(batchPlannerPolicy) FROM BatchPlannerPolicy batchPlannerPolicy";

	private static final String _SQL_COUNT_BATCHPLANNERPOLICY_WHERE =
		"SELECT COUNT(batchPlannerPolicy) FROM BatchPlannerPolicy batchPlannerPolicy WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS = "batchPlannerPolicy.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No BatchPlannerPolicy exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No BatchPlannerPolicy exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		BatchPlannerPolicyPersistenceImpl.class);

	@Override
	protected FinderCache getFinderCache() {
		return finderCache;
	}

	@Reference
	private BatchPlannerPolicyModelArgumentsResolver
		_batchPlannerPolicyModelArgumentsResolver;

}