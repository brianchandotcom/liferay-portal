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

import com.liferay.batch.planner.exception.NoSuchLogException;
import com.liferay.batch.planner.model.BatchPlannerLog;
import com.liferay.batch.planner.model.BatchPlannerLogTable;
import com.liferay.batch.planner.model.impl.BatchPlannerLogImpl;
import com.liferay.batch.planner.model.impl.BatchPlannerLogModelImpl;
import com.liferay.batch.planner.service.persistence.BatchPlannerLogPersistence;
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
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.io.Serializable;

import java.lang.reflect.InvocationHandler;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
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
 * The persistence implementation for the batch planner log service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Igor Beslic
 * @generated
 */
@Component(service = {BatchPlannerLogPersistence.class, BasePersistence.class})
public class BatchPlannerLogPersistenceImpl
	extends BasePersistenceImpl<BatchPlannerLog>
	implements BatchPlannerLogPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>BatchPlannerLogUtil</code> to access the batch planner log persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		BatchPlannerLogImpl.class.getName();

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
	 * Returns all the batch planner logs where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching batch planner logs
	 */
	@Override
	public List<BatchPlannerLog> findByCompanyId(long companyId) {
		return findByCompanyId(
			companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the batch planner logs where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BatchPlannerLogModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of batch planner logs
	 * @param end the upper bound of the range of batch planner logs (not inclusive)
	 * @return the range of matching batch planner logs
	 */
	@Override
	public List<BatchPlannerLog> findByCompanyId(
		long companyId, int start, int end) {

		return findByCompanyId(companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the batch planner logs where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BatchPlannerLogModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of batch planner logs
	 * @param end the upper bound of the range of batch planner logs (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching batch planner logs
	 */
	@Override
	public List<BatchPlannerLog> findByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<BatchPlannerLog> orderByComparator) {

		return findByCompanyId(companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the batch planner logs where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BatchPlannerLogModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of batch planner logs
	 * @param end the upper bound of the range of batch planner logs (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching batch planner logs
	 */
	@Override
	public List<BatchPlannerLog> findByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<BatchPlannerLog> orderByComparator,
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

		List<BatchPlannerLog> list = null;

		if (useFinderCache) {
			list = (List<BatchPlannerLog>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (BatchPlannerLog batchPlannerLog : list) {
					if (companyId != batchPlannerLog.getCompanyId()) {
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

			sb.append(_SQL_SELECT_BATCHPLANNERLOG_WHERE);

			sb.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(BatchPlannerLogModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(companyId);

				list = (List<BatchPlannerLog>)QueryUtil.list(
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
	 * Returns the first batch planner log in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching batch planner log
	 * @throws NoSuchLogException if a matching batch planner log could not be found
	 */
	@Override
	public BatchPlannerLog findByCompanyId_First(
			long companyId,
			OrderByComparator<BatchPlannerLog> orderByComparator)
		throws NoSuchLogException {

		BatchPlannerLog batchPlannerLog = fetchByCompanyId_First(
			companyId, orderByComparator);

		if (batchPlannerLog != null) {
			return batchPlannerLog;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchLogException(sb.toString());
	}

	/**
	 * Returns the first batch planner log in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching batch planner log, or <code>null</code> if a matching batch planner log could not be found
	 */
	@Override
	public BatchPlannerLog fetchByCompanyId_First(
		long companyId, OrderByComparator<BatchPlannerLog> orderByComparator) {

		List<BatchPlannerLog> list = findByCompanyId(
			companyId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last batch planner log in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching batch planner log
	 * @throws NoSuchLogException if a matching batch planner log could not be found
	 */
	@Override
	public BatchPlannerLog findByCompanyId_Last(
			long companyId,
			OrderByComparator<BatchPlannerLog> orderByComparator)
		throws NoSuchLogException {

		BatchPlannerLog batchPlannerLog = fetchByCompanyId_Last(
			companyId, orderByComparator);

		if (batchPlannerLog != null) {
			return batchPlannerLog;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchLogException(sb.toString());
	}

	/**
	 * Returns the last batch planner log in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching batch planner log, or <code>null</code> if a matching batch planner log could not be found
	 */
	@Override
	public BatchPlannerLog fetchByCompanyId_Last(
		long companyId, OrderByComparator<BatchPlannerLog> orderByComparator) {

		int count = countByCompanyId(companyId);

		if (count == 0) {
			return null;
		}

		List<BatchPlannerLog> list = findByCompanyId(
			companyId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the batch planner logs before and after the current batch planner log in the ordered set where companyId = &#63;.
	 *
	 * @param batchPlannerLogId the primary key of the current batch planner log
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next batch planner log
	 * @throws NoSuchLogException if a batch planner log with the primary key could not be found
	 */
	@Override
	public BatchPlannerLog[] findByCompanyId_PrevAndNext(
			long batchPlannerLogId, long companyId,
			OrderByComparator<BatchPlannerLog> orderByComparator)
		throws NoSuchLogException {

		BatchPlannerLog batchPlannerLog = findByPrimaryKey(batchPlannerLogId);

		Session session = null;

		try {
			session = openSession();

			BatchPlannerLog[] array = new BatchPlannerLogImpl[3];

			array[0] = getByCompanyId_PrevAndNext(
				session, batchPlannerLog, companyId, orderByComparator, true);

			array[1] = batchPlannerLog;

			array[2] = getByCompanyId_PrevAndNext(
				session, batchPlannerLog, companyId, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected BatchPlannerLog getByCompanyId_PrevAndNext(
		Session session, BatchPlannerLog batchPlannerLog, long companyId,
		OrderByComparator<BatchPlannerLog> orderByComparator,
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

		sb.append(_SQL_SELECT_BATCHPLANNERLOG_WHERE);

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
			sb.append(BatchPlannerLogModelImpl.ORDER_BY_JPQL);
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
						batchPlannerLog)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<BatchPlannerLog> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the batch planner logs where companyId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 */
	@Override
	public void removeByCompanyId(long companyId) {
		for (BatchPlannerLog batchPlannerLog :
				findByCompanyId(
					companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(batchPlannerLog);
		}
	}

	/**
	 * Returns the number of batch planner logs where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching batch planner logs
	 */
	@Override
	public int countByCompanyId(long companyId) {
		FinderPath finderPath = _finderPathCountByCompanyId;

		Object[] finderArgs = new Object[] {companyId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_BATCHPLANNERLOG_WHERE);

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
		"batchPlannerLog.companyId = ?";

	private FinderPath _finderPathFetchByBatchPlannerPlanId;
	private FinderPath _finderPathCountByBatchPlannerPlanId;

	/**
	 * Returns the batch planner log where batchPlannerPlanId = &#63; or throws a <code>NoSuchLogException</code> if it could not be found.
	 *
	 * @param batchPlannerPlanId the batch planner plan ID
	 * @return the matching batch planner log
	 * @throws NoSuchLogException if a matching batch planner log could not be found
	 */
	@Override
	public BatchPlannerLog findByBatchPlannerPlanId(long batchPlannerPlanId)
		throws NoSuchLogException {

		BatchPlannerLog batchPlannerLog = fetchByBatchPlannerPlanId(
			batchPlannerPlanId);

		if (batchPlannerLog == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("batchPlannerPlanId=");
			sb.append(batchPlannerPlanId);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchLogException(sb.toString());
		}

		return batchPlannerLog;
	}

	/**
	 * Returns the batch planner log where batchPlannerPlanId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param batchPlannerPlanId the batch planner plan ID
	 * @return the matching batch planner log, or <code>null</code> if a matching batch planner log could not be found
	 */
	@Override
	public BatchPlannerLog fetchByBatchPlannerPlanId(long batchPlannerPlanId) {
		return fetchByBatchPlannerPlanId(batchPlannerPlanId, true);
	}

	/**
	 * Returns the batch planner log where batchPlannerPlanId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param batchPlannerPlanId the batch planner plan ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching batch planner log, or <code>null</code> if a matching batch planner log could not be found
	 */
	@Override
	public BatchPlannerLog fetchByBatchPlannerPlanId(
		long batchPlannerPlanId, boolean useFinderCache) {

		Object[] finderArgs = null;

		if (useFinderCache) {
			finderArgs = new Object[] {batchPlannerPlanId};
		}

		Object result = null;

		if (useFinderCache) {
			result = finderCache.getResult(
				_finderPathFetchByBatchPlannerPlanId, finderArgs);
		}

		if (result instanceof BatchPlannerLog) {
			BatchPlannerLog batchPlannerLog = (BatchPlannerLog)result;

			if (batchPlannerPlanId != batchPlannerLog.getBatchPlannerPlanId()) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_SELECT_BATCHPLANNERLOG_WHERE);

			sb.append(_FINDER_COLUMN_BATCHPLANNERPLANID_BATCHPLANNERPLANID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(batchPlannerPlanId);

				List<BatchPlannerLog> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache) {
						finderCache.putResult(
							_finderPathFetchByBatchPlannerPlanId, finderArgs,
							list);
					}
				}
				else {
					if (list.size() > 1) {
						Collections.sort(list, Collections.reverseOrder());

						if (_log.isWarnEnabled()) {
							if (!useFinderCache) {
								finderArgs = new Object[] {batchPlannerPlanId};
							}

							_log.warn(
								"BatchPlannerLogPersistenceImpl.fetchByBatchPlannerPlanId(long, boolean) with parameters (" +
									StringUtil.merge(finderArgs) +
										") yields a result set with more than 1 result. This violates the logical unique restriction. There is no order guarantee on which result is returned by this finder.");
						}
					}

					BatchPlannerLog batchPlannerLog = list.get(0);

					result = batchPlannerLog;

					cacheResult(batchPlannerLog);
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
			return (BatchPlannerLog)result;
		}
	}

	/**
	 * Removes the batch planner log where batchPlannerPlanId = &#63; from the database.
	 *
	 * @param batchPlannerPlanId the batch planner plan ID
	 * @return the batch planner log that was removed
	 */
	@Override
	public BatchPlannerLog removeByBatchPlannerPlanId(long batchPlannerPlanId)
		throws NoSuchLogException {

		BatchPlannerLog batchPlannerLog = findByBatchPlannerPlanId(
			batchPlannerPlanId);

		return remove(batchPlannerLog);
	}

	/**
	 * Returns the number of batch planner logs where batchPlannerPlanId = &#63;.
	 *
	 * @param batchPlannerPlanId the batch planner plan ID
	 * @return the number of matching batch planner logs
	 */
	@Override
	public int countByBatchPlannerPlanId(long batchPlannerPlanId) {
		FinderPath finderPath = _finderPathCountByBatchPlannerPlanId;

		Object[] finderArgs = new Object[] {batchPlannerPlanId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_BATCHPLANNERLOG_WHERE);

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
			"batchPlannerLog.batchPlannerPlanId = ?";

	private FinderPath _finderPathFetchByBPPI_BEETERC;
	private FinderPath _finderPathCountByBPPI_BEETERC;

	/**
	 * Returns the batch planner log where batchPlannerPlanId = &#63; and batchEngineExportTaskERC = &#63; or throws a <code>NoSuchLogException</code> if it could not be found.
	 *
	 * @param batchPlannerPlanId the batch planner plan ID
	 * @param batchEngineExportTaskERC the batch engine export task erc
	 * @return the matching batch planner log
	 * @throws NoSuchLogException if a matching batch planner log could not be found
	 */
	@Override
	public BatchPlannerLog findByBPPI_BEETERC(
			long batchPlannerPlanId, String batchEngineExportTaskERC)
		throws NoSuchLogException {

		BatchPlannerLog batchPlannerLog = fetchByBPPI_BEETERC(
			batchPlannerPlanId, batchEngineExportTaskERC);

		if (batchPlannerLog == null) {
			StringBundler sb = new StringBundler(6);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("batchPlannerPlanId=");
			sb.append(batchPlannerPlanId);

			sb.append(", batchEngineExportTaskERC=");
			sb.append(batchEngineExportTaskERC);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchLogException(sb.toString());
		}

		return batchPlannerLog;
	}

	/**
	 * Returns the batch planner log where batchPlannerPlanId = &#63; and batchEngineExportTaskERC = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param batchPlannerPlanId the batch planner plan ID
	 * @param batchEngineExportTaskERC the batch engine export task erc
	 * @return the matching batch planner log, or <code>null</code> if a matching batch planner log could not be found
	 */
	@Override
	public BatchPlannerLog fetchByBPPI_BEETERC(
		long batchPlannerPlanId, String batchEngineExportTaskERC) {

		return fetchByBPPI_BEETERC(
			batchPlannerPlanId, batchEngineExportTaskERC, true);
	}

	/**
	 * Returns the batch planner log where batchPlannerPlanId = &#63; and batchEngineExportTaskERC = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param batchPlannerPlanId the batch planner plan ID
	 * @param batchEngineExportTaskERC the batch engine export task erc
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching batch planner log, or <code>null</code> if a matching batch planner log could not be found
	 */
	@Override
	public BatchPlannerLog fetchByBPPI_BEETERC(
		long batchPlannerPlanId, String batchEngineExportTaskERC,
		boolean useFinderCache) {

		batchEngineExportTaskERC = Objects.toString(
			batchEngineExportTaskERC, "");

		Object[] finderArgs = null;

		if (useFinderCache) {
			finderArgs = new Object[] {
				batchPlannerPlanId, batchEngineExportTaskERC
			};
		}

		Object result = null;

		if (useFinderCache) {
			result = finderCache.getResult(
				_finderPathFetchByBPPI_BEETERC, finderArgs);
		}

		if (result instanceof BatchPlannerLog) {
			BatchPlannerLog batchPlannerLog = (BatchPlannerLog)result;

			if ((batchPlannerPlanId !=
					batchPlannerLog.getBatchPlannerPlanId()) ||
				!Objects.equals(
					batchEngineExportTaskERC,
					batchPlannerLog.getBatchEngineExportTaskERC())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_SELECT_BATCHPLANNERLOG_WHERE);

			sb.append(_FINDER_COLUMN_BPPI_BEETERC_BATCHPLANNERPLANID_2);

			boolean bindBatchEngineExportTaskERC = false;

			if (batchEngineExportTaskERC.isEmpty()) {
				sb.append(
					_FINDER_COLUMN_BPPI_BEETERC_BATCHENGINEEXPORTTASKERC_3);
			}
			else {
				bindBatchEngineExportTaskERC = true;

				sb.append(
					_FINDER_COLUMN_BPPI_BEETERC_BATCHENGINEEXPORTTASKERC_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(batchPlannerPlanId);

				if (bindBatchEngineExportTaskERC) {
					queryPos.add(batchEngineExportTaskERC);
				}

				List<BatchPlannerLog> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache) {
						finderCache.putResult(
							_finderPathFetchByBPPI_BEETERC, finderArgs, list);
					}
				}
				else {
					BatchPlannerLog batchPlannerLog = list.get(0);

					result = batchPlannerLog;

					cacheResult(batchPlannerLog);
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
			return (BatchPlannerLog)result;
		}
	}

	/**
	 * Removes the batch planner log where batchPlannerPlanId = &#63; and batchEngineExportTaskERC = &#63; from the database.
	 *
	 * @param batchPlannerPlanId the batch planner plan ID
	 * @param batchEngineExportTaskERC the batch engine export task erc
	 * @return the batch planner log that was removed
	 */
	@Override
	public BatchPlannerLog removeByBPPI_BEETERC(
			long batchPlannerPlanId, String batchEngineExportTaskERC)
		throws NoSuchLogException {

		BatchPlannerLog batchPlannerLog = findByBPPI_BEETERC(
			batchPlannerPlanId, batchEngineExportTaskERC);

		return remove(batchPlannerLog);
	}

	/**
	 * Returns the number of batch planner logs where batchPlannerPlanId = &#63; and batchEngineExportTaskERC = &#63;.
	 *
	 * @param batchPlannerPlanId the batch planner plan ID
	 * @param batchEngineExportTaskERC the batch engine export task erc
	 * @return the number of matching batch planner logs
	 */
	@Override
	public int countByBPPI_BEETERC(
		long batchPlannerPlanId, String batchEngineExportTaskERC) {

		batchEngineExportTaskERC = Objects.toString(
			batchEngineExportTaskERC, "");

		FinderPath finderPath = _finderPathCountByBPPI_BEETERC;

		Object[] finderArgs = new Object[] {
			batchPlannerPlanId, batchEngineExportTaskERC
		};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_BATCHPLANNERLOG_WHERE);

			sb.append(_FINDER_COLUMN_BPPI_BEETERC_BATCHPLANNERPLANID_2);

			boolean bindBatchEngineExportTaskERC = false;

			if (batchEngineExportTaskERC.isEmpty()) {
				sb.append(
					_FINDER_COLUMN_BPPI_BEETERC_BATCHENGINEEXPORTTASKERC_3);
			}
			else {
				bindBatchEngineExportTaskERC = true;

				sb.append(
					_FINDER_COLUMN_BPPI_BEETERC_BATCHENGINEEXPORTTASKERC_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(batchPlannerPlanId);

				if (bindBatchEngineExportTaskERC) {
					queryPos.add(batchEngineExportTaskERC);
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

	private static final String
		_FINDER_COLUMN_BPPI_BEETERC_BATCHPLANNERPLANID_2 =
			"batchPlannerLog.batchPlannerPlanId = ? AND ";

	private static final String
		_FINDER_COLUMN_BPPI_BEETERC_BATCHENGINEEXPORTTASKERC_2 =
			"batchPlannerLog.batchEngineExportTaskERC = ?";

	private static final String
		_FINDER_COLUMN_BPPI_BEETERC_BATCHENGINEEXPORTTASKERC_3 =
			"(batchPlannerLog.batchEngineExportTaskERC IS NULL OR batchPlannerLog.batchEngineExportTaskERC = '')";

	private FinderPath _finderPathFetchByBPPI_BEITERC;
	private FinderPath _finderPathCountByBPPI_BEITERC;

	/**
	 * Returns the batch planner log where batchPlannerPlanId = &#63; and batchEngineImportTaskERC = &#63; or throws a <code>NoSuchLogException</code> if it could not be found.
	 *
	 * @param batchPlannerPlanId the batch planner plan ID
	 * @param batchEngineImportTaskERC the batch engine import task erc
	 * @return the matching batch planner log
	 * @throws NoSuchLogException if a matching batch planner log could not be found
	 */
	@Override
	public BatchPlannerLog findByBPPI_BEITERC(
			long batchPlannerPlanId, String batchEngineImportTaskERC)
		throws NoSuchLogException {

		BatchPlannerLog batchPlannerLog = fetchByBPPI_BEITERC(
			batchPlannerPlanId, batchEngineImportTaskERC);

		if (batchPlannerLog == null) {
			StringBundler sb = new StringBundler(6);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("batchPlannerPlanId=");
			sb.append(batchPlannerPlanId);

			sb.append(", batchEngineImportTaskERC=");
			sb.append(batchEngineImportTaskERC);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchLogException(sb.toString());
		}

		return batchPlannerLog;
	}

	/**
	 * Returns the batch planner log where batchPlannerPlanId = &#63; and batchEngineImportTaskERC = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param batchPlannerPlanId the batch planner plan ID
	 * @param batchEngineImportTaskERC the batch engine import task erc
	 * @return the matching batch planner log, or <code>null</code> if a matching batch planner log could not be found
	 */
	@Override
	public BatchPlannerLog fetchByBPPI_BEITERC(
		long batchPlannerPlanId, String batchEngineImportTaskERC) {

		return fetchByBPPI_BEITERC(
			batchPlannerPlanId, batchEngineImportTaskERC, true);
	}

	/**
	 * Returns the batch planner log where batchPlannerPlanId = &#63; and batchEngineImportTaskERC = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param batchPlannerPlanId the batch planner plan ID
	 * @param batchEngineImportTaskERC the batch engine import task erc
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching batch planner log, or <code>null</code> if a matching batch planner log could not be found
	 */
	@Override
	public BatchPlannerLog fetchByBPPI_BEITERC(
		long batchPlannerPlanId, String batchEngineImportTaskERC,
		boolean useFinderCache) {

		batchEngineImportTaskERC = Objects.toString(
			batchEngineImportTaskERC, "");

		Object[] finderArgs = null;

		if (useFinderCache) {
			finderArgs = new Object[] {
				batchPlannerPlanId, batchEngineImportTaskERC
			};
		}

		Object result = null;

		if (useFinderCache) {
			result = finderCache.getResult(
				_finderPathFetchByBPPI_BEITERC, finderArgs);
		}

		if (result instanceof BatchPlannerLog) {
			BatchPlannerLog batchPlannerLog = (BatchPlannerLog)result;

			if ((batchPlannerPlanId !=
					batchPlannerLog.getBatchPlannerPlanId()) ||
				!Objects.equals(
					batchEngineImportTaskERC,
					batchPlannerLog.getBatchEngineImportTaskERC())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_SELECT_BATCHPLANNERLOG_WHERE);

			sb.append(_FINDER_COLUMN_BPPI_BEITERC_BATCHPLANNERPLANID_2);

			boolean bindBatchEngineImportTaskERC = false;

			if (batchEngineImportTaskERC.isEmpty()) {
				sb.append(
					_FINDER_COLUMN_BPPI_BEITERC_BATCHENGINEIMPORTTASKERC_3);
			}
			else {
				bindBatchEngineImportTaskERC = true;

				sb.append(
					_FINDER_COLUMN_BPPI_BEITERC_BATCHENGINEIMPORTTASKERC_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(batchPlannerPlanId);

				if (bindBatchEngineImportTaskERC) {
					queryPos.add(batchEngineImportTaskERC);
				}

				List<BatchPlannerLog> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache) {
						finderCache.putResult(
							_finderPathFetchByBPPI_BEITERC, finderArgs, list);
					}
				}
				else {
					BatchPlannerLog batchPlannerLog = list.get(0);

					result = batchPlannerLog;

					cacheResult(batchPlannerLog);
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
			return (BatchPlannerLog)result;
		}
	}

	/**
	 * Removes the batch planner log where batchPlannerPlanId = &#63; and batchEngineImportTaskERC = &#63; from the database.
	 *
	 * @param batchPlannerPlanId the batch planner plan ID
	 * @param batchEngineImportTaskERC the batch engine import task erc
	 * @return the batch planner log that was removed
	 */
	@Override
	public BatchPlannerLog removeByBPPI_BEITERC(
			long batchPlannerPlanId, String batchEngineImportTaskERC)
		throws NoSuchLogException {

		BatchPlannerLog batchPlannerLog = findByBPPI_BEITERC(
			batchPlannerPlanId, batchEngineImportTaskERC);

		return remove(batchPlannerLog);
	}

	/**
	 * Returns the number of batch planner logs where batchPlannerPlanId = &#63; and batchEngineImportTaskERC = &#63;.
	 *
	 * @param batchPlannerPlanId the batch planner plan ID
	 * @param batchEngineImportTaskERC the batch engine import task erc
	 * @return the number of matching batch planner logs
	 */
	@Override
	public int countByBPPI_BEITERC(
		long batchPlannerPlanId, String batchEngineImportTaskERC) {

		batchEngineImportTaskERC = Objects.toString(
			batchEngineImportTaskERC, "");

		FinderPath finderPath = _finderPathCountByBPPI_BEITERC;

		Object[] finderArgs = new Object[] {
			batchPlannerPlanId, batchEngineImportTaskERC
		};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_BATCHPLANNERLOG_WHERE);

			sb.append(_FINDER_COLUMN_BPPI_BEITERC_BATCHPLANNERPLANID_2);

			boolean bindBatchEngineImportTaskERC = false;

			if (batchEngineImportTaskERC.isEmpty()) {
				sb.append(
					_FINDER_COLUMN_BPPI_BEITERC_BATCHENGINEIMPORTTASKERC_3);
			}
			else {
				bindBatchEngineImportTaskERC = true;

				sb.append(
					_FINDER_COLUMN_BPPI_BEITERC_BATCHENGINEIMPORTTASKERC_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(batchPlannerPlanId);

				if (bindBatchEngineImportTaskERC) {
					queryPos.add(batchEngineImportTaskERC);
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

	private static final String
		_FINDER_COLUMN_BPPI_BEITERC_BATCHPLANNERPLANID_2 =
			"batchPlannerLog.batchPlannerPlanId = ? AND ";

	private static final String
		_FINDER_COLUMN_BPPI_BEITERC_BATCHENGINEIMPORTTASKERC_2 =
			"batchPlannerLog.batchEngineImportTaskERC = ?";

	private static final String
		_FINDER_COLUMN_BPPI_BEITERC_BATCHENGINEIMPORTTASKERC_3 =
			"(batchPlannerLog.batchEngineImportTaskERC IS NULL OR batchPlannerLog.batchEngineImportTaskERC = '')";

	private FinderPath _finderPathFetchByBPPI_DTERC;
	private FinderPath _finderPathCountByBPPI_DTERC;

	/**
	 * Returns the batch planner log where batchPlannerPlanId = &#63; and dispatchTriggerERC = &#63; or throws a <code>NoSuchLogException</code> if it could not be found.
	 *
	 * @param batchPlannerPlanId the batch planner plan ID
	 * @param dispatchTriggerERC the dispatch trigger erc
	 * @return the matching batch planner log
	 * @throws NoSuchLogException if a matching batch planner log could not be found
	 */
	@Override
	public BatchPlannerLog findByBPPI_DTERC(
			long batchPlannerPlanId, String dispatchTriggerERC)
		throws NoSuchLogException {

		BatchPlannerLog batchPlannerLog = fetchByBPPI_DTERC(
			batchPlannerPlanId, dispatchTriggerERC);

		if (batchPlannerLog == null) {
			StringBundler sb = new StringBundler(6);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("batchPlannerPlanId=");
			sb.append(batchPlannerPlanId);

			sb.append(", dispatchTriggerERC=");
			sb.append(dispatchTriggerERC);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchLogException(sb.toString());
		}

		return batchPlannerLog;
	}

	/**
	 * Returns the batch planner log where batchPlannerPlanId = &#63; and dispatchTriggerERC = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param batchPlannerPlanId the batch planner plan ID
	 * @param dispatchTriggerERC the dispatch trigger erc
	 * @return the matching batch planner log, or <code>null</code> if a matching batch planner log could not be found
	 */
	@Override
	public BatchPlannerLog fetchByBPPI_DTERC(
		long batchPlannerPlanId, String dispatchTriggerERC) {

		return fetchByBPPI_DTERC(batchPlannerPlanId, dispatchTriggerERC, true);
	}

	/**
	 * Returns the batch planner log where batchPlannerPlanId = &#63; and dispatchTriggerERC = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param batchPlannerPlanId the batch planner plan ID
	 * @param dispatchTriggerERC the dispatch trigger erc
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching batch planner log, or <code>null</code> if a matching batch planner log could not be found
	 */
	@Override
	public BatchPlannerLog fetchByBPPI_DTERC(
		long batchPlannerPlanId, String dispatchTriggerERC,
		boolean useFinderCache) {

		dispatchTriggerERC = Objects.toString(dispatchTriggerERC, "");

		Object[] finderArgs = null;

		if (useFinderCache) {
			finderArgs = new Object[] {batchPlannerPlanId, dispatchTriggerERC};
		}

		Object result = null;

		if (useFinderCache) {
			result = finderCache.getResult(
				_finderPathFetchByBPPI_DTERC, finderArgs);
		}

		if (result instanceof BatchPlannerLog) {
			BatchPlannerLog batchPlannerLog = (BatchPlannerLog)result;

			if ((batchPlannerPlanId !=
					batchPlannerLog.getBatchPlannerPlanId()) ||
				!Objects.equals(
					dispatchTriggerERC,
					batchPlannerLog.getDispatchTriggerERC())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_SELECT_BATCHPLANNERLOG_WHERE);

			sb.append(_FINDER_COLUMN_BPPI_DTERC_BATCHPLANNERPLANID_2);

			boolean bindDispatchTriggerERC = false;

			if (dispatchTriggerERC.isEmpty()) {
				sb.append(_FINDER_COLUMN_BPPI_DTERC_DISPATCHTRIGGERERC_3);
			}
			else {
				bindDispatchTriggerERC = true;

				sb.append(_FINDER_COLUMN_BPPI_DTERC_DISPATCHTRIGGERERC_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(batchPlannerPlanId);

				if (bindDispatchTriggerERC) {
					queryPos.add(dispatchTriggerERC);
				}

				List<BatchPlannerLog> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache) {
						finderCache.putResult(
							_finderPathFetchByBPPI_DTERC, finderArgs, list);
					}
				}
				else {
					BatchPlannerLog batchPlannerLog = list.get(0);

					result = batchPlannerLog;

					cacheResult(batchPlannerLog);
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
			return (BatchPlannerLog)result;
		}
	}

	/**
	 * Removes the batch planner log where batchPlannerPlanId = &#63; and dispatchTriggerERC = &#63; from the database.
	 *
	 * @param batchPlannerPlanId the batch planner plan ID
	 * @param dispatchTriggerERC the dispatch trigger erc
	 * @return the batch planner log that was removed
	 */
	@Override
	public BatchPlannerLog removeByBPPI_DTERC(
			long batchPlannerPlanId, String dispatchTriggerERC)
		throws NoSuchLogException {

		BatchPlannerLog batchPlannerLog = findByBPPI_DTERC(
			batchPlannerPlanId, dispatchTriggerERC);

		return remove(batchPlannerLog);
	}

	/**
	 * Returns the number of batch planner logs where batchPlannerPlanId = &#63; and dispatchTriggerERC = &#63;.
	 *
	 * @param batchPlannerPlanId the batch planner plan ID
	 * @param dispatchTriggerERC the dispatch trigger erc
	 * @return the number of matching batch planner logs
	 */
	@Override
	public int countByBPPI_DTERC(
		long batchPlannerPlanId, String dispatchTriggerERC) {

		dispatchTriggerERC = Objects.toString(dispatchTriggerERC, "");

		FinderPath finderPath = _finderPathCountByBPPI_DTERC;

		Object[] finderArgs = new Object[] {
			batchPlannerPlanId, dispatchTriggerERC
		};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_BATCHPLANNERLOG_WHERE);

			sb.append(_FINDER_COLUMN_BPPI_DTERC_BATCHPLANNERPLANID_2);

			boolean bindDispatchTriggerERC = false;

			if (dispatchTriggerERC.isEmpty()) {
				sb.append(_FINDER_COLUMN_BPPI_DTERC_DISPATCHTRIGGERERC_3);
			}
			else {
				bindDispatchTriggerERC = true;

				sb.append(_FINDER_COLUMN_BPPI_DTERC_DISPATCHTRIGGERERC_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(batchPlannerPlanId);

				if (bindDispatchTriggerERC) {
					queryPos.add(dispatchTriggerERC);
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

	private static final String _FINDER_COLUMN_BPPI_DTERC_BATCHPLANNERPLANID_2 =
		"batchPlannerLog.batchPlannerPlanId = ? AND ";

	private static final String _FINDER_COLUMN_BPPI_DTERC_DISPATCHTRIGGERERC_2 =
		"batchPlannerLog.dispatchTriggerERC = ?";

	private static final String _FINDER_COLUMN_BPPI_DTERC_DISPATCHTRIGGERERC_3 =
		"(batchPlannerLog.dispatchTriggerERC IS NULL OR batchPlannerLog.dispatchTriggerERC = '')";

	public BatchPlannerLogPersistenceImpl() {
		Map<String, String> dbColumnNames = new HashMap<String, String>();

		dbColumnNames.put("size", "size_");

		setDBColumnNames(dbColumnNames);

		setModelClass(BatchPlannerLog.class);

		setModelImplClass(BatchPlannerLogImpl.class);
		setModelPKClass(long.class);

		setTable(BatchPlannerLogTable.INSTANCE);
	}

	/**
	 * Caches the batch planner log in the entity cache if it is enabled.
	 *
	 * @param batchPlannerLog the batch planner log
	 */
	@Override
	public void cacheResult(BatchPlannerLog batchPlannerLog) {
		entityCache.putResult(
			BatchPlannerLogImpl.class, batchPlannerLog.getPrimaryKey(),
			batchPlannerLog);

		finderCache.putResult(
			_finderPathFetchByBatchPlannerPlanId,
			new Object[] {batchPlannerLog.getBatchPlannerPlanId()},
			batchPlannerLog);

		finderCache.putResult(
			_finderPathFetchByBPPI_BEETERC,
			new Object[] {
				batchPlannerLog.getBatchPlannerPlanId(),
				batchPlannerLog.getBatchEngineExportTaskERC()
			},
			batchPlannerLog);

		finderCache.putResult(
			_finderPathFetchByBPPI_BEITERC,
			new Object[] {
				batchPlannerLog.getBatchPlannerPlanId(),
				batchPlannerLog.getBatchEngineImportTaskERC()
			},
			batchPlannerLog);

		finderCache.putResult(
			_finderPathFetchByBPPI_DTERC,
			new Object[] {
				batchPlannerLog.getBatchPlannerPlanId(),
				batchPlannerLog.getDispatchTriggerERC()
			},
			batchPlannerLog);
	}

	private int _valueObjectFinderCacheListThreshold;

	/**
	 * Caches the batch planner logs in the entity cache if it is enabled.
	 *
	 * @param batchPlannerLogs the batch planner logs
	 */
	@Override
	public void cacheResult(List<BatchPlannerLog> batchPlannerLogs) {
		if ((_valueObjectFinderCacheListThreshold == 0) ||
			((_valueObjectFinderCacheListThreshold > 0) &&
			 (batchPlannerLogs.size() >
				 _valueObjectFinderCacheListThreshold))) {

			return;
		}

		for (BatchPlannerLog batchPlannerLog : batchPlannerLogs) {
			if (entityCache.getResult(
					BatchPlannerLogImpl.class,
					batchPlannerLog.getPrimaryKey()) == null) {

				cacheResult(batchPlannerLog);
			}
		}
	}

	/**
	 * Clears the cache for all batch planner logs.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(BatchPlannerLogImpl.class);

		finderCache.clearCache(BatchPlannerLogImpl.class);
	}

	/**
	 * Clears the cache for the batch planner log.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(BatchPlannerLog batchPlannerLog) {
		entityCache.removeResult(BatchPlannerLogImpl.class, batchPlannerLog);
	}

	@Override
	public void clearCache(List<BatchPlannerLog> batchPlannerLogs) {
		for (BatchPlannerLog batchPlannerLog : batchPlannerLogs) {
			entityCache.removeResult(
				BatchPlannerLogImpl.class, batchPlannerLog);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(BatchPlannerLogImpl.class);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(BatchPlannerLogImpl.class, primaryKey);
		}
	}

	protected void cacheUniqueFindersCache(
		BatchPlannerLogModelImpl batchPlannerLogModelImpl) {

		Object[] args = new Object[] {
			batchPlannerLogModelImpl.getBatchPlannerPlanId()
		};

		finderCache.putResult(
			_finderPathCountByBatchPlannerPlanId, args, Long.valueOf(1));
		finderCache.putResult(
			_finderPathFetchByBatchPlannerPlanId, args,
			batchPlannerLogModelImpl);

		args = new Object[] {
			batchPlannerLogModelImpl.getBatchPlannerPlanId(),
			batchPlannerLogModelImpl.getBatchEngineExportTaskERC()
		};

		finderCache.putResult(
			_finderPathCountByBPPI_BEETERC, args, Long.valueOf(1));
		finderCache.putResult(
			_finderPathFetchByBPPI_BEETERC, args, batchPlannerLogModelImpl);

		args = new Object[] {
			batchPlannerLogModelImpl.getBatchPlannerPlanId(),
			batchPlannerLogModelImpl.getBatchEngineImportTaskERC()
		};

		finderCache.putResult(
			_finderPathCountByBPPI_BEITERC, args, Long.valueOf(1));
		finderCache.putResult(
			_finderPathFetchByBPPI_BEITERC, args, batchPlannerLogModelImpl);

		args = new Object[] {
			batchPlannerLogModelImpl.getBatchPlannerPlanId(),
			batchPlannerLogModelImpl.getDispatchTriggerERC()
		};

		finderCache.putResult(
			_finderPathCountByBPPI_DTERC, args, Long.valueOf(1));
		finderCache.putResult(
			_finderPathFetchByBPPI_DTERC, args, batchPlannerLogModelImpl);
	}

	/**
	 * Creates a new batch planner log with the primary key. Does not add the batch planner log to the database.
	 *
	 * @param batchPlannerLogId the primary key for the new batch planner log
	 * @return the new batch planner log
	 */
	@Override
	public BatchPlannerLog create(long batchPlannerLogId) {
		BatchPlannerLog batchPlannerLog = new BatchPlannerLogImpl();

		batchPlannerLog.setNew(true);
		batchPlannerLog.setPrimaryKey(batchPlannerLogId);

		batchPlannerLog.setCompanyId(CompanyThreadLocal.getCompanyId());

		return batchPlannerLog;
	}

	/**
	 * Removes the batch planner log with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param batchPlannerLogId the primary key of the batch planner log
	 * @return the batch planner log that was removed
	 * @throws NoSuchLogException if a batch planner log with the primary key could not be found
	 */
	@Override
	public BatchPlannerLog remove(long batchPlannerLogId)
		throws NoSuchLogException {

		return remove((Serializable)batchPlannerLogId);
	}

	/**
	 * Removes the batch planner log with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the batch planner log
	 * @return the batch planner log that was removed
	 * @throws NoSuchLogException if a batch planner log with the primary key could not be found
	 */
	@Override
	public BatchPlannerLog remove(Serializable primaryKey)
		throws NoSuchLogException {

		Session session = null;

		try {
			session = openSession();

			BatchPlannerLog batchPlannerLog = (BatchPlannerLog)session.get(
				BatchPlannerLogImpl.class, primaryKey);

			if (batchPlannerLog == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchLogException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(batchPlannerLog);
		}
		catch (NoSuchLogException noSuchEntityException) {
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
	protected BatchPlannerLog removeImpl(BatchPlannerLog batchPlannerLog) {
		Session session = null;

		try {
			session = openSession();

			if (!session.contains(batchPlannerLog)) {
				batchPlannerLog = (BatchPlannerLog)session.get(
					BatchPlannerLogImpl.class,
					batchPlannerLog.getPrimaryKeyObj());
			}

			if (batchPlannerLog != null) {
				session.delete(batchPlannerLog);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (batchPlannerLog != null) {
			clearCache(batchPlannerLog);
		}

		return batchPlannerLog;
	}

	@Override
	public BatchPlannerLog updateImpl(BatchPlannerLog batchPlannerLog) {
		boolean isNew = batchPlannerLog.isNew();

		if (!(batchPlannerLog instanceof BatchPlannerLogModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(batchPlannerLog.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					batchPlannerLog);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in batchPlannerLog proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom BatchPlannerLog implementation " +
					batchPlannerLog.getClass());
		}

		BatchPlannerLogModelImpl batchPlannerLogModelImpl =
			(BatchPlannerLogModelImpl)batchPlannerLog;

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Date date = new Date();

		if (isNew && (batchPlannerLog.getCreateDate() == null)) {
			if (serviceContext == null) {
				batchPlannerLog.setCreateDate(date);
			}
			else {
				batchPlannerLog.setCreateDate(
					serviceContext.getCreateDate(date));
			}
		}

		if (!batchPlannerLogModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				batchPlannerLog.setModifiedDate(date);
			}
			else {
				batchPlannerLog.setModifiedDate(
					serviceContext.getModifiedDate(date));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (isNew) {
				session.save(batchPlannerLog);
			}
			else {
				batchPlannerLog = (BatchPlannerLog)session.merge(
					batchPlannerLog);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		entityCache.putResult(
			BatchPlannerLogImpl.class, batchPlannerLogModelImpl, false, true);

		cacheUniqueFindersCache(batchPlannerLogModelImpl);

		if (isNew) {
			batchPlannerLog.setNew(false);
		}

		batchPlannerLog.resetOriginalValues();

		return batchPlannerLog;
	}

	/**
	 * Returns the batch planner log with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the batch planner log
	 * @return the batch planner log
	 * @throws NoSuchLogException if a batch planner log with the primary key could not be found
	 */
	@Override
	public BatchPlannerLog findByPrimaryKey(Serializable primaryKey)
		throws NoSuchLogException {

		BatchPlannerLog batchPlannerLog = fetchByPrimaryKey(primaryKey);

		if (batchPlannerLog == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchLogException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return batchPlannerLog;
	}

	/**
	 * Returns the batch planner log with the primary key or throws a <code>NoSuchLogException</code> if it could not be found.
	 *
	 * @param batchPlannerLogId the primary key of the batch planner log
	 * @return the batch planner log
	 * @throws NoSuchLogException if a batch planner log with the primary key could not be found
	 */
	@Override
	public BatchPlannerLog findByPrimaryKey(long batchPlannerLogId)
		throws NoSuchLogException {

		return findByPrimaryKey((Serializable)batchPlannerLogId);
	}

	/**
	 * Returns the batch planner log with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param batchPlannerLogId the primary key of the batch planner log
	 * @return the batch planner log, or <code>null</code> if a batch planner log with the primary key could not be found
	 */
	@Override
	public BatchPlannerLog fetchByPrimaryKey(long batchPlannerLogId) {
		return fetchByPrimaryKey((Serializable)batchPlannerLogId);
	}

	/**
	 * Returns all the batch planner logs.
	 *
	 * @return the batch planner logs
	 */
	@Override
	public List<BatchPlannerLog> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the batch planner logs.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BatchPlannerLogModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of batch planner logs
	 * @param end the upper bound of the range of batch planner logs (not inclusive)
	 * @return the range of batch planner logs
	 */
	@Override
	public List<BatchPlannerLog> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the batch planner logs.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BatchPlannerLogModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of batch planner logs
	 * @param end the upper bound of the range of batch planner logs (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of batch planner logs
	 */
	@Override
	public List<BatchPlannerLog> findAll(
		int start, int end,
		OrderByComparator<BatchPlannerLog> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the batch planner logs.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BatchPlannerLogModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of batch planner logs
	 * @param end the upper bound of the range of batch planner logs (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of batch planner logs
	 */
	@Override
	public List<BatchPlannerLog> findAll(
		int start, int end,
		OrderByComparator<BatchPlannerLog> orderByComparator,
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

		List<BatchPlannerLog> list = null;

		if (useFinderCache) {
			list = (List<BatchPlannerLog>)finderCache.getResult(
				finderPath, finderArgs);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_BATCHPLANNERLOG);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_BATCHPLANNERLOG;

				sql = sql.concat(BatchPlannerLogModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<BatchPlannerLog>)QueryUtil.list(
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
	 * Removes all the batch planner logs from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (BatchPlannerLog batchPlannerLog : findAll()) {
			remove(batchPlannerLog);
		}
	}

	/**
	 * Returns the number of batch planner logs.
	 *
	 * @return the number of batch planner logs
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(
			_finderPathCountAll, FINDER_ARGS_EMPTY);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(_SQL_COUNT_BATCHPLANNERLOG);

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
	public Set<String> getBadColumnNames() {
		return _badColumnNames;
	}

	@Override
	protected EntityCache getEntityCache() {
		return entityCache;
	}

	@Override
	protected String getPKDBName() {
		return "batchPlannerLogId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_BATCHPLANNERLOG;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return BatchPlannerLogModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the batch planner log persistence.
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

		_finderPathFetchByBatchPlannerPlanId = new FinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByBatchPlannerPlanId",
			new String[] {Long.class.getName()},
			new String[] {"batchPlannerPlanId"}, true);

		_finderPathCountByBatchPlannerPlanId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByBatchPlannerPlanId", new String[] {Long.class.getName()},
			new String[] {"batchPlannerPlanId"}, false);

		_finderPathFetchByBPPI_BEETERC = new FinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByBPPI_BEETERC",
			new String[] {Long.class.getName(), String.class.getName()},
			new String[] {"batchPlannerPlanId", "batchEngineExportTaskERC"},
			true);

		_finderPathCountByBPPI_BEETERC = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByBPPI_BEETERC",
			new String[] {Long.class.getName(), String.class.getName()},
			new String[] {"batchPlannerPlanId", "batchEngineExportTaskERC"},
			false);

		_finderPathFetchByBPPI_BEITERC = new FinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByBPPI_BEITERC",
			new String[] {Long.class.getName(), String.class.getName()},
			new String[] {"batchPlannerPlanId", "batchEngineImportTaskERC"},
			true);

		_finderPathCountByBPPI_BEITERC = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByBPPI_BEITERC",
			new String[] {Long.class.getName(), String.class.getName()},
			new String[] {"batchPlannerPlanId", "batchEngineImportTaskERC"},
			false);

		_finderPathFetchByBPPI_DTERC = new FinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByBPPI_DTERC",
			new String[] {Long.class.getName(), String.class.getName()},
			new String[] {"batchPlannerPlanId", "dispatchTriggerERC"}, true);

		_finderPathCountByBPPI_DTERC = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByBPPI_DTERC",
			new String[] {Long.class.getName(), String.class.getName()},
			new String[] {"batchPlannerPlanId", "dispatchTriggerERC"}, false);
	}

	@Deactivate
	public void deactivate() {
		entityCache.removeCache(BatchPlannerLogImpl.class.getName());
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

	private static final String _SQL_SELECT_BATCHPLANNERLOG =
		"SELECT batchPlannerLog FROM BatchPlannerLog batchPlannerLog";

	private static final String _SQL_SELECT_BATCHPLANNERLOG_WHERE =
		"SELECT batchPlannerLog FROM BatchPlannerLog batchPlannerLog WHERE ";

	private static final String _SQL_COUNT_BATCHPLANNERLOG =
		"SELECT COUNT(batchPlannerLog) FROM BatchPlannerLog batchPlannerLog";

	private static final String _SQL_COUNT_BATCHPLANNERLOG_WHERE =
		"SELECT COUNT(batchPlannerLog) FROM BatchPlannerLog batchPlannerLog WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS = "batchPlannerLog.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No BatchPlannerLog exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No BatchPlannerLog exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		BatchPlannerLogPersistenceImpl.class);

	private static final Set<String> _badColumnNames = SetUtil.fromArray(
		new String[] {"size"});

	@Override
	protected FinderCache getFinderCache() {
		return finderCache;
	}

	@Reference
	private BatchPlannerLogModelArgumentsResolver
		_batchPlannerLogModelArgumentsResolver;

}