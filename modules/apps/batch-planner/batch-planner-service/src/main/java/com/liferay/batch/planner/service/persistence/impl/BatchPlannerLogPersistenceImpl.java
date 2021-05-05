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
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.service.persistence.BasePersistence;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.SetUtil;

import java.io.Serializable;

import java.lang.reflect.InvocationHandler;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
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
	private FinderPath _finderPathWithPaginationFindByBatchPlannerPlanId;
	private FinderPath _finderPathWithoutPaginationFindByBatchPlannerPlanId;
	private FinderPath _finderPathCountByBatchPlannerPlanId;

	/**
	 * Returns all the batch planner logs where batchPlannerPlanId = &#63;.
	 *
	 * @param batchPlannerPlanId the batch planner plan ID
	 * @return the matching batch planner logs
	 */
	@Override
	public List<BatchPlannerLog> findByBatchPlannerPlanId(
		long batchPlannerPlanId) {

		return findByBatchPlannerPlanId(
			batchPlannerPlanId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the batch planner logs where batchPlannerPlanId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BatchPlannerLogModelImpl</code>.
	 * </p>
	 *
	 * @param batchPlannerPlanId the batch planner plan ID
	 * @param start the lower bound of the range of batch planner logs
	 * @param end the upper bound of the range of batch planner logs (not inclusive)
	 * @return the range of matching batch planner logs
	 */
	@Override
	public List<BatchPlannerLog> findByBatchPlannerPlanId(
		long batchPlannerPlanId, int start, int end) {

		return findByBatchPlannerPlanId(batchPlannerPlanId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the batch planner logs where batchPlannerPlanId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BatchPlannerLogModelImpl</code>.
	 * </p>
	 *
	 * @param batchPlannerPlanId the batch planner plan ID
	 * @param start the lower bound of the range of batch planner logs
	 * @param end the upper bound of the range of batch planner logs (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching batch planner logs
	 */
	@Override
	public List<BatchPlannerLog> findByBatchPlannerPlanId(
		long batchPlannerPlanId, int start, int end,
		OrderByComparator<BatchPlannerLog> orderByComparator) {

		return findByBatchPlannerPlanId(
			batchPlannerPlanId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the batch planner logs where batchPlannerPlanId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BatchPlannerLogModelImpl</code>.
	 * </p>
	 *
	 * @param batchPlannerPlanId the batch planner plan ID
	 * @param start the lower bound of the range of batch planner logs
	 * @param end the upper bound of the range of batch planner logs (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching batch planner logs
	 */
	@Override
	public List<BatchPlannerLog> findByBatchPlannerPlanId(
		long batchPlannerPlanId, int start, int end,
		OrderByComparator<BatchPlannerLog> orderByComparator,
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

		List<BatchPlannerLog> list = null;

		if (useFinderCache) {
			list = (List<BatchPlannerLog>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (BatchPlannerLog batchPlannerLog : list) {
					if (batchPlannerPlanId !=
							batchPlannerLog.getBatchPlannerPlanId()) {

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

			sb.append(_FINDER_COLUMN_BATCHPLANNERPLANID_BATCHPLANNERPLANID_2);

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

				queryPos.add(batchPlannerPlanId);

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
	 * Returns the first batch planner log in the ordered set where batchPlannerPlanId = &#63;.
	 *
	 * @param batchPlannerPlanId the batch planner plan ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching batch planner log
	 * @throws NoSuchLogException if a matching batch planner log could not be found
	 */
	@Override
	public BatchPlannerLog findByBatchPlannerPlanId_First(
			long batchPlannerPlanId,
			OrderByComparator<BatchPlannerLog> orderByComparator)
		throws NoSuchLogException {

		BatchPlannerLog batchPlannerLog = fetchByBatchPlannerPlanId_First(
			batchPlannerPlanId, orderByComparator);

		if (batchPlannerLog != null) {
			return batchPlannerLog;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("batchPlannerPlanId=");
		sb.append(batchPlannerPlanId);

		sb.append("}");

		throw new NoSuchLogException(sb.toString());
	}

	/**
	 * Returns the first batch planner log in the ordered set where batchPlannerPlanId = &#63;.
	 *
	 * @param batchPlannerPlanId the batch planner plan ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching batch planner log, or <code>null</code> if a matching batch planner log could not be found
	 */
	@Override
	public BatchPlannerLog fetchByBatchPlannerPlanId_First(
		long batchPlannerPlanId,
		OrderByComparator<BatchPlannerLog> orderByComparator) {

		List<BatchPlannerLog> list = findByBatchPlannerPlanId(
			batchPlannerPlanId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last batch planner log in the ordered set where batchPlannerPlanId = &#63;.
	 *
	 * @param batchPlannerPlanId the batch planner plan ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching batch planner log
	 * @throws NoSuchLogException if a matching batch planner log could not be found
	 */
	@Override
	public BatchPlannerLog findByBatchPlannerPlanId_Last(
			long batchPlannerPlanId,
			OrderByComparator<BatchPlannerLog> orderByComparator)
		throws NoSuchLogException {

		BatchPlannerLog batchPlannerLog = fetchByBatchPlannerPlanId_Last(
			batchPlannerPlanId, orderByComparator);

		if (batchPlannerLog != null) {
			return batchPlannerLog;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("batchPlannerPlanId=");
		sb.append(batchPlannerPlanId);

		sb.append("}");

		throw new NoSuchLogException(sb.toString());
	}

	/**
	 * Returns the last batch planner log in the ordered set where batchPlannerPlanId = &#63;.
	 *
	 * @param batchPlannerPlanId the batch planner plan ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching batch planner log, or <code>null</code> if a matching batch planner log could not be found
	 */
	@Override
	public BatchPlannerLog fetchByBatchPlannerPlanId_Last(
		long batchPlannerPlanId,
		OrderByComparator<BatchPlannerLog> orderByComparator) {

		int count = countByBatchPlannerPlanId(batchPlannerPlanId);

		if (count == 0) {
			return null;
		}

		List<BatchPlannerLog> list = findByBatchPlannerPlanId(
			batchPlannerPlanId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the batch planner logs before and after the current batch planner log in the ordered set where batchPlannerPlanId = &#63;.
	 *
	 * @param batchPlannerLogId the primary key of the current batch planner log
	 * @param batchPlannerPlanId the batch planner plan ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next batch planner log
	 * @throws NoSuchLogException if a batch planner log with the primary key could not be found
	 */
	@Override
	public BatchPlannerLog[] findByBatchPlannerPlanId_PrevAndNext(
			long batchPlannerLogId, long batchPlannerPlanId,
			OrderByComparator<BatchPlannerLog> orderByComparator)
		throws NoSuchLogException {

		BatchPlannerLog batchPlannerLog = findByPrimaryKey(batchPlannerLogId);

		Session session = null;

		try {
			session = openSession();

			BatchPlannerLog[] array = new BatchPlannerLogImpl[3];

			array[0] = getByBatchPlannerPlanId_PrevAndNext(
				session, batchPlannerLog, batchPlannerPlanId, orderByComparator,
				true);

			array[1] = batchPlannerLog;

			array[2] = getByBatchPlannerPlanId_PrevAndNext(
				session, batchPlannerLog, batchPlannerPlanId, orderByComparator,
				false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected BatchPlannerLog getByBatchPlannerPlanId_PrevAndNext(
		Session session, BatchPlannerLog batchPlannerLog,
		long batchPlannerPlanId,
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
			sb.append(BatchPlannerLogModelImpl.ORDER_BY_JPQL);
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
	 * Removes all the batch planner logs where batchPlannerPlanId = &#63; from the database.
	 *
	 * @param batchPlannerPlanId the batch planner plan ID
	 */
	@Override
	public void removeByBatchPlannerPlanId(long batchPlannerPlanId) {
		for (BatchPlannerLog batchPlannerLog :
				findByBatchPlannerPlanId(
					batchPlannerPlanId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(batchPlannerLog);
		}
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

	private FinderPath _finderPathFetchByBPPI_BETERC;
	private FinderPath _finderPathCountByBPPI_BETERC;

	/**
	 * Returns the batch planner log where batchPlannerPlanId = &#63; and batchEngineTaskERC = &#63; or throws a <code>NoSuchLogException</code> if it could not be found.
	 *
	 * @param batchPlannerPlanId the batch planner plan ID
	 * @param batchEngineTaskERC the batch engine task erc
	 * @return the matching batch planner log
	 * @throws NoSuchLogException if a matching batch planner log could not be found
	 */
	@Override
	public BatchPlannerLog findByBPPI_BETERC(
			long batchPlannerPlanId, String batchEngineTaskERC)
		throws NoSuchLogException {

		BatchPlannerLog batchPlannerLog = fetchByBPPI_BETERC(
			batchPlannerPlanId, batchEngineTaskERC);

		if (batchPlannerLog == null) {
			StringBundler sb = new StringBundler(6);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("batchPlannerPlanId=");
			sb.append(batchPlannerPlanId);

			sb.append(", batchEngineTaskERC=");
			sb.append(batchEngineTaskERC);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchLogException(sb.toString());
		}

		return batchPlannerLog;
	}

	/**
	 * Returns the batch planner log where batchPlannerPlanId = &#63; and batchEngineTaskERC = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param batchPlannerPlanId the batch planner plan ID
	 * @param batchEngineTaskERC the batch engine task erc
	 * @return the matching batch planner log, or <code>null</code> if a matching batch planner log could not be found
	 */
	@Override
	public BatchPlannerLog fetchByBPPI_BETERC(
		long batchPlannerPlanId, String batchEngineTaskERC) {

		return fetchByBPPI_BETERC(batchPlannerPlanId, batchEngineTaskERC, true);
	}

	/**
	 * Returns the batch planner log where batchPlannerPlanId = &#63; and batchEngineTaskERC = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param batchPlannerPlanId the batch planner plan ID
	 * @param batchEngineTaskERC the batch engine task erc
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching batch planner log, or <code>null</code> if a matching batch planner log could not be found
	 */
	@Override
	public BatchPlannerLog fetchByBPPI_BETERC(
		long batchPlannerPlanId, String batchEngineTaskERC,
		boolean useFinderCache) {

		Object[] finderArgs = null;

		if (useFinderCache) {
			finderArgs = new Object[] {batchPlannerPlanId, batchEngineTaskERC};
		}

		Object result = null;

		if (useFinderCache) {
			result = finderCache.getResult(
				_finderPathFetchByBPPI_BETERC, finderArgs);
		}

		if (result instanceof BatchPlannerLog) {
			BatchPlannerLog batchPlannerLog = (BatchPlannerLog)result;

			if ((batchPlannerPlanId !=
					batchPlannerLog.getBatchPlannerPlanId()) ||
				!Objects.equals(
					batchEngineTaskERC,
					batchPlannerLog.getBatchEngineTaskERC())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_SELECT_BATCHPLANNERLOG_WHERE);

			sb.append(_FINDER_COLUMN_BPPI_BETERC_BATCHPLANNERPLANID_2);

			boolean bindBatchEngineTaskERC = false;

			if (batchEngineTaskERC == null) {
				sb.append(_FINDER_COLUMN_BPPI_BETERC_BATCHENGINETASKERC_1);
			}
			else if (batchEngineTaskERC.isEmpty()) {
				sb.append(_FINDER_COLUMN_BPPI_BETERC_BATCHENGINETASKERC_3);
			}
			else {
				bindBatchEngineTaskERC = true;

				sb.append(_FINDER_COLUMN_BPPI_BETERC_BATCHENGINETASKERC_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(batchPlannerPlanId);

				if (bindBatchEngineTaskERC) {
					queryPos.add(batchEngineTaskERC);
				}

				List<BatchPlannerLog> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache) {
						finderCache.putResult(
							_finderPathFetchByBPPI_BETERC, finderArgs, list);
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
	 * Removes the batch planner log where batchPlannerPlanId = &#63; and batchEngineTaskERC = &#63; from the database.
	 *
	 * @param batchPlannerPlanId the batch planner plan ID
	 * @param batchEngineTaskERC the batch engine task erc
	 * @return the batch planner log that was removed
	 */
	@Override
	public BatchPlannerLog removeByBPPI_BETERC(
			long batchPlannerPlanId, String batchEngineTaskERC)
		throws NoSuchLogException {

		BatchPlannerLog batchPlannerLog = findByBPPI_BETERC(
			batchPlannerPlanId, batchEngineTaskERC);

		return remove(batchPlannerLog);
	}

	/**
	 * Returns the number of batch planner logs where batchPlannerPlanId = &#63; and batchEngineTaskERC = &#63;.
	 *
	 * @param batchPlannerPlanId the batch planner plan ID
	 * @param batchEngineTaskERC the batch engine task erc
	 * @return the number of matching batch planner logs
	 */
	@Override
	public int countByBPPI_BETERC(
		long batchPlannerPlanId, String batchEngineTaskERC) {

		FinderPath finderPath = _finderPathCountByBPPI_BETERC;

		Object[] finderArgs = new Object[] {
			batchPlannerPlanId, batchEngineTaskERC
		};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_BATCHPLANNERLOG_WHERE);

			sb.append(_FINDER_COLUMN_BPPI_BETERC_BATCHPLANNERPLANID_2);

			boolean bindBatchEngineTaskERC = false;

			if (batchEngineTaskERC == null) {
				sb.append(_FINDER_COLUMN_BPPI_BETERC_BATCHENGINETASKERC_1);
			}
			else if (batchEngineTaskERC.isEmpty()) {
				sb.append(_FINDER_COLUMN_BPPI_BETERC_BATCHENGINETASKERC_3);
			}
			else {
				bindBatchEngineTaskERC = true;

				sb.append(_FINDER_COLUMN_BPPI_BETERC_BATCHENGINETASKERC_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(batchPlannerPlanId);

				if (bindBatchEngineTaskERC) {
					queryPos.add(batchEngineTaskERC);
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
		_FINDER_COLUMN_BPPI_BETERC_BATCHPLANNERPLANID_2 =
			"batchPlannerLog.batchPlannerPlanId = ? AND ";

	private static final String
		_FINDER_COLUMN_BPPI_BETERC_BATCHENGINETASKERC_1 =
			"batchPlannerLog.batchEngineTaskERC IS NULL";

	private static final String
		_FINDER_COLUMN_BPPI_BETERC_BATCHENGINETASKERC_2 =
			"batchPlannerLog.batchEngineTaskERC = ?";

	private static final String
		_FINDER_COLUMN_BPPI_BETERC_BATCHENGINETASKERC_3 =
			"(batchPlannerLog.batchEngineTaskERC IS NULL OR batchPlannerLog.batchEngineTaskERC = '')";

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

			if (dispatchTriggerERC == null) {
				sb.append(_FINDER_COLUMN_BPPI_DTERC_DISPATCHTRIGGERERC_1);
			}
			else if (dispatchTriggerERC.isEmpty()) {
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

			if (dispatchTriggerERC == null) {
				sb.append(_FINDER_COLUMN_BPPI_DTERC_DISPATCHTRIGGERERC_1);
			}
			else if (dispatchTriggerERC.isEmpty()) {
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

	private static final String _FINDER_COLUMN_BPPI_DTERC_DISPATCHTRIGGERERC_1 =
		"batchPlannerLog.dispatchTriggerERC IS NULL";

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
			_finderPathFetchByBPPI_BETERC,
			new Object[] {
				batchPlannerLog.getBatchPlannerPlanId(),
				batchPlannerLog.getBatchEngineTaskERC()
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

	/**
	 * Caches the batch planner logs in the entity cache if it is enabled.
	 *
	 * @param batchPlannerLogs the batch planner logs
	 */
	@Override
	public void cacheResult(List<BatchPlannerLog> batchPlannerLogs) {
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
			batchPlannerLogModelImpl.getBatchPlannerPlanId(),
			batchPlannerLogModelImpl.getBatchEngineTaskERC()
		};

		finderCache.putResult(
			_finderPathCountByBPPI_BETERC, args, Long.valueOf(1));
		finderCache.putResult(
			_finderPathFetchByBPPI_BETERC, args, batchPlannerLogModelImpl);

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

		Date now = new Date();

		if (isNew && (batchPlannerLog.getCreateDate() == null)) {
			if (serviceContext == null) {
				batchPlannerLog.setCreateDate(now);
			}
			else {
				batchPlannerLog.setCreateDate(
					serviceContext.getCreateDate(now));
			}
		}

		if (!batchPlannerLogModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				batchPlannerLog.setModifiedDate(now);
			}
			else {
				batchPlannerLog.setModifiedDate(
					serviceContext.getModifiedDate(now));
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
	public void activate(BundleContext bundleContext) {
		_bundleContext = bundleContext;

		_argumentsResolverServiceRegistration = _bundleContext.registerService(
			ArgumentsResolver.class,
			new BatchPlannerLogModelArgumentsResolver(),
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

		_finderPathFetchByBPPI_BETERC = new FinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByBPPI_BETERC",
			new String[] {Long.class.getName(), String.class.getName()},
			new String[] {"batchPlannerPlanId", "batchEngineTaskERC"}, true);

		_finderPathCountByBPPI_BETERC = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByBPPI_BETERC",
			new String[] {Long.class.getName(), String.class.getName()},
			new String[] {"batchPlannerPlanId", "batchEngineTaskERC"}, false);

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

		_argumentsResolverServiceRegistration.unregister();
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

	private BundleContext _bundleContext;

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

	private ServiceRegistration<ArgumentsResolver>
		_argumentsResolverServiceRegistration;

	private static class BatchPlannerLogModelArgumentsResolver
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

			BatchPlannerLogModelImpl batchPlannerLogModelImpl =
				(BatchPlannerLogModelImpl)baseModel;

			long columnBitmask = batchPlannerLogModelImpl.getColumnBitmask();

			if (!checkColumn || (columnBitmask == 0)) {
				return _getValue(
					batchPlannerLogModelImpl, columnNames, original);
			}

			Long finderPathColumnBitmask = _finderPathColumnBitmasksCache.get(
				finderPath);

			if (finderPathColumnBitmask == null) {
				finderPathColumnBitmask = 0L;

				for (String columnName : columnNames) {
					finderPathColumnBitmask |=
						batchPlannerLogModelImpl.getColumnBitmask(columnName);
				}

				if (finderPath.isBaseModelResult() &&
					(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION ==
						finderPath.getCacheName())) {

					finderPathColumnBitmask |= _ORDER_BY_COLUMNS_BITMASK;
				}

				_finderPathColumnBitmasksCache.put(
					finderPath, finderPathColumnBitmask);
			}

			if ((columnBitmask & finderPathColumnBitmask) != 0) {
				return _getValue(
					batchPlannerLogModelImpl, columnNames, original);
			}

			return null;
		}

		@Override
		public String getClassName() {
			return BatchPlannerLogImpl.class.getName();
		}

		@Override
		public String getTableName() {
			return BatchPlannerLogTable.INSTANCE.getTableName();
		}

		private static Object[] _getValue(
			BatchPlannerLogModelImpl batchPlannerLogModelImpl,
			String[] columnNames, boolean original) {

			Object[] arguments = new Object[columnNames.length];

			for (int i = 0; i < arguments.length; i++) {
				String columnName = columnNames[i];

				if (original) {
					arguments[i] =
						batchPlannerLogModelImpl.getColumnOriginalValue(
							columnName);
				}
				else {
					arguments[i] = batchPlannerLogModelImpl.getColumnValue(
						columnName);
				}
			}

			return arguments;
		}

		private static final Map<FinderPath, Long>
			_finderPathColumnBitmasksCache = new ConcurrentHashMap<>();

		private static final long _ORDER_BY_COLUMNS_BITMASK;

		static {
			long orderByColumnsBitmask = 0;

			orderByColumnsBitmask |= BatchPlannerLogModelImpl.getColumnBitmask(
				"modifiedDate");

			_ORDER_BY_COLUMNS_BITMASK = orderByColumnsBitmask;
		}

	}

}