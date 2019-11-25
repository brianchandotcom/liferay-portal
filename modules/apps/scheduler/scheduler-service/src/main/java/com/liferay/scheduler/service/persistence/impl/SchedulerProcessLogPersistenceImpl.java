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

package com.liferay.scheduler.service.persistence.impl;

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
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.scheduler.exception.NoSuchProcessLogException;
import com.liferay.scheduler.model.SchedulerProcessLog;
import com.liferay.scheduler.model.impl.SchedulerProcessLogImpl;
import com.liferay.scheduler.model.impl.SchedulerProcessLogModelImpl;
import com.liferay.scheduler.service.persistence.SchedulerProcessLogPersistence;
import com.liferay.scheduler.service.persistence.impl.constants.SchedulerPersistenceConstants;

import java.io.Serializable;

import java.lang.reflect.InvocationHandler;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.sql.DataSource;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * The persistence implementation for the scheduler process log service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Alessio Antonio Rendina
 * @generated
 */
@Component(service = SchedulerProcessLogPersistence.class)
public class SchedulerProcessLogPersistenceImpl
	extends BasePersistenceImpl<SchedulerProcessLog>
	implements SchedulerProcessLogPersistence {

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>SchedulerProcessLogUtil</code> to access the scheduler process log persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		SchedulerProcessLogImpl.class.getName();

	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List1";

	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List2";

	private FinderPath _finderPathWithPaginationFindAll;
	private FinderPath _finderPathWithoutPaginationFindAll;
	private FinderPath _finderPathCountAll;
	private FinderPath _finderPathWithPaginationFindBySchedulerProcessId;
	private FinderPath _finderPathWithoutPaginationFindBySchedulerProcessId;
	private FinderPath _finderPathCountBySchedulerProcessId;

	/**
	 * Returns all the scheduler process logs where schedulerProcessId = &#63;.
	 *
	 * @param schedulerProcessId the scheduler process ID
	 * @return the matching scheduler process logs
	 */
	@Override
	public List<SchedulerProcessLog> findBySchedulerProcessId(
		long schedulerProcessId) {

		return findBySchedulerProcessId(
			schedulerProcessId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the scheduler process logs where schedulerProcessId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SchedulerProcessLogModelImpl</code>.
	 * </p>
	 *
	 * @param schedulerProcessId the scheduler process ID
	 * @param start the lower bound of the range of scheduler process logs
	 * @param end the upper bound of the range of scheduler process logs (not inclusive)
	 * @return the range of matching scheduler process logs
	 */
	@Override
	public List<SchedulerProcessLog> findBySchedulerProcessId(
		long schedulerProcessId, int start, int end) {

		return findBySchedulerProcessId(schedulerProcessId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the scheduler process logs where schedulerProcessId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SchedulerProcessLogModelImpl</code>.
	 * </p>
	 *
	 * @param schedulerProcessId the scheduler process ID
	 * @param start the lower bound of the range of scheduler process logs
	 * @param end the upper bound of the range of scheduler process logs (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching scheduler process logs
	 */
	@Override
	public List<SchedulerProcessLog> findBySchedulerProcessId(
		long schedulerProcessId, int start, int end,
		OrderByComparator<SchedulerProcessLog> orderByComparator) {

		return findBySchedulerProcessId(
			schedulerProcessId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the scheduler process logs where schedulerProcessId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SchedulerProcessLogModelImpl</code>.
	 * </p>
	 *
	 * @param schedulerProcessId the scheduler process ID
	 * @param start the lower bound of the range of scheduler process logs
	 * @param end the upper bound of the range of scheduler process logs (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching scheduler process logs
	 */
	@Override
	public List<SchedulerProcessLog> findBySchedulerProcessId(
		long schedulerProcessId, int start, int end,
		OrderByComparator<SchedulerProcessLog> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath =
					_finderPathWithoutPaginationFindBySchedulerProcessId;
				finderArgs = new Object[] {schedulerProcessId};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindBySchedulerProcessId;
			finderArgs = new Object[] {
				schedulerProcessId, start, end, orderByComparator
			};
		}

		List<SchedulerProcessLog> list = null;

		if (useFinderCache) {
			list = (List<SchedulerProcessLog>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (SchedulerProcessLog schedulerProcessLog : list) {
					if (schedulerProcessId !=
							schedulerProcessLog.getSchedulerProcessId()) {

						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(
					3 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				query = new StringBundler(3);
			}

			query.append(_SQL_SELECT_SCHEDULERPROCESSLOG_WHERE);

			query.append(
				_FINDER_COLUMN_SCHEDULERPROCESSID_SCHEDULERPROCESSID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				query.append(SchedulerProcessLogModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(schedulerProcessId);

				list = (List<SchedulerProcessLog>)QueryUtil.list(
					q, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					finderCache.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception e) {
				if (useFinderCache) {
					finderCache.removeResult(finderPath, finderArgs);
				}

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first scheduler process log in the ordered set where schedulerProcessId = &#63;.
	 *
	 * @param schedulerProcessId the scheduler process ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching scheduler process log
	 * @throws NoSuchProcessLogException if a matching scheduler process log could not be found
	 */
	@Override
	public SchedulerProcessLog findBySchedulerProcessId_First(
			long schedulerProcessId,
			OrderByComparator<SchedulerProcessLog> orderByComparator)
		throws NoSuchProcessLogException {

		SchedulerProcessLog schedulerProcessLog =
			fetchBySchedulerProcessId_First(
				schedulerProcessId, orderByComparator);

		if (schedulerProcessLog != null) {
			return schedulerProcessLog;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("schedulerProcessId=");
		msg.append(schedulerProcessId);

		msg.append("}");

		throw new NoSuchProcessLogException(msg.toString());
	}

	/**
	 * Returns the first scheduler process log in the ordered set where schedulerProcessId = &#63;.
	 *
	 * @param schedulerProcessId the scheduler process ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching scheduler process log, or <code>null</code> if a matching scheduler process log could not be found
	 */
	@Override
	public SchedulerProcessLog fetchBySchedulerProcessId_First(
		long schedulerProcessId,
		OrderByComparator<SchedulerProcessLog> orderByComparator) {

		List<SchedulerProcessLog> list = findBySchedulerProcessId(
			schedulerProcessId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last scheduler process log in the ordered set where schedulerProcessId = &#63;.
	 *
	 * @param schedulerProcessId the scheduler process ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching scheduler process log
	 * @throws NoSuchProcessLogException if a matching scheduler process log could not be found
	 */
	@Override
	public SchedulerProcessLog findBySchedulerProcessId_Last(
			long schedulerProcessId,
			OrderByComparator<SchedulerProcessLog> orderByComparator)
		throws NoSuchProcessLogException {

		SchedulerProcessLog schedulerProcessLog =
			fetchBySchedulerProcessId_Last(
				schedulerProcessId, orderByComparator);

		if (schedulerProcessLog != null) {
			return schedulerProcessLog;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("schedulerProcessId=");
		msg.append(schedulerProcessId);

		msg.append("}");

		throw new NoSuchProcessLogException(msg.toString());
	}

	/**
	 * Returns the last scheduler process log in the ordered set where schedulerProcessId = &#63;.
	 *
	 * @param schedulerProcessId the scheduler process ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching scheduler process log, or <code>null</code> if a matching scheduler process log could not be found
	 */
	@Override
	public SchedulerProcessLog fetchBySchedulerProcessId_Last(
		long schedulerProcessId,
		OrderByComparator<SchedulerProcessLog> orderByComparator) {

		int count = countBySchedulerProcessId(schedulerProcessId);

		if (count == 0) {
			return null;
		}

		List<SchedulerProcessLog> list = findBySchedulerProcessId(
			schedulerProcessId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the scheduler process logs before and after the current scheduler process log in the ordered set where schedulerProcessId = &#63;.
	 *
	 * @param schedulerProcessLogId the primary key of the current scheduler process log
	 * @param schedulerProcessId the scheduler process ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next scheduler process log
	 * @throws NoSuchProcessLogException if a scheduler process log with the primary key could not be found
	 */
	@Override
	public SchedulerProcessLog[] findBySchedulerProcessId_PrevAndNext(
			long schedulerProcessLogId, long schedulerProcessId,
			OrderByComparator<SchedulerProcessLog> orderByComparator)
		throws NoSuchProcessLogException {

		SchedulerProcessLog schedulerProcessLog = findByPrimaryKey(
			schedulerProcessLogId);

		Session session = null;

		try {
			session = openSession();

			SchedulerProcessLog[] array = new SchedulerProcessLogImpl[3];

			array[0] = getBySchedulerProcessId_PrevAndNext(
				session, schedulerProcessLog, schedulerProcessId,
				orderByComparator, true);

			array[1] = schedulerProcessLog;

			array[2] = getBySchedulerProcessId_PrevAndNext(
				session, schedulerProcessLog, schedulerProcessId,
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

	protected SchedulerProcessLog getBySchedulerProcessId_PrevAndNext(
		Session session, SchedulerProcessLog schedulerProcessLog,
		long schedulerProcessId,
		OrderByComparator<SchedulerProcessLog> orderByComparator,
		boolean previous) {

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_SCHEDULERPROCESSLOG_WHERE);

		query.append(_FINDER_COLUMN_SCHEDULERPROCESSID_SCHEDULERPROCESSID_2);

		if (orderByComparator != null) {
			String[] orderByConditionFields =
				orderByComparator.getOrderByConditionFields();

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
			query.append(SchedulerProcessLogModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(schedulerProcessId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						schedulerProcessLog)) {

				qPos.add(orderByConditionValue);
			}
		}

		List<SchedulerProcessLog> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the scheduler process logs where schedulerProcessId = &#63; from the database.
	 *
	 * @param schedulerProcessId the scheduler process ID
	 */
	@Override
	public void removeBySchedulerProcessId(long schedulerProcessId) {
		for (SchedulerProcessLog schedulerProcessLog :
				findBySchedulerProcessId(
					schedulerProcessId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(schedulerProcessLog);
		}
	}

	/**
	 * Returns the number of scheduler process logs where schedulerProcessId = &#63;.
	 *
	 * @param schedulerProcessId the scheduler process ID
	 * @return the number of matching scheduler process logs
	 */
	@Override
	public int countBySchedulerProcessId(long schedulerProcessId) {
		FinderPath finderPath = _finderPathCountBySchedulerProcessId;

		Object[] finderArgs = new Object[] {schedulerProcessId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_SCHEDULERPROCESSLOG_WHERE);

			query.append(
				_FINDER_COLUMN_SCHEDULERPROCESSID_SCHEDULERPROCESSID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(schedulerProcessId);

				count = (Long)q.uniqueResult();

				finderCache.putResult(finderPath, finderArgs, count);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String
		_FINDER_COLUMN_SCHEDULERPROCESSID_SCHEDULERPROCESSID_2 =
			"schedulerProcessLog.schedulerProcessId = ?";

	private FinderPath _finderPathWithPaginationFindByS_S;
	private FinderPath _finderPathWithoutPaginationFindByS_S;
	private FinderPath _finderPathCountByS_S;

	/**
	 * Returns all the scheduler process logs where schedulerProcessId = &#63; and status = &#63;.
	 *
	 * @param schedulerProcessId the scheduler process ID
	 * @param status the status
	 * @return the matching scheduler process logs
	 */
	@Override
	public List<SchedulerProcessLog> findByS_S(
		long schedulerProcessId, int status) {

		return findByS_S(
			schedulerProcessId, status, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the scheduler process logs where schedulerProcessId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SchedulerProcessLogModelImpl</code>.
	 * </p>
	 *
	 * @param schedulerProcessId the scheduler process ID
	 * @param status the status
	 * @param start the lower bound of the range of scheduler process logs
	 * @param end the upper bound of the range of scheduler process logs (not inclusive)
	 * @return the range of matching scheduler process logs
	 */
	@Override
	public List<SchedulerProcessLog> findByS_S(
		long schedulerProcessId, int status, int start, int end) {

		return findByS_S(schedulerProcessId, status, start, end, null);
	}

	/**
	 * Returns an ordered range of all the scheduler process logs where schedulerProcessId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SchedulerProcessLogModelImpl</code>.
	 * </p>
	 *
	 * @param schedulerProcessId the scheduler process ID
	 * @param status the status
	 * @param start the lower bound of the range of scheduler process logs
	 * @param end the upper bound of the range of scheduler process logs (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching scheduler process logs
	 */
	@Override
	public List<SchedulerProcessLog> findByS_S(
		long schedulerProcessId, int status, int start, int end,
		OrderByComparator<SchedulerProcessLog> orderByComparator) {

		return findByS_S(
			schedulerProcessId, status, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the scheduler process logs where schedulerProcessId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SchedulerProcessLogModelImpl</code>.
	 * </p>
	 *
	 * @param schedulerProcessId the scheduler process ID
	 * @param status the status
	 * @param start the lower bound of the range of scheduler process logs
	 * @param end the upper bound of the range of scheduler process logs (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching scheduler process logs
	 */
	@Override
	public List<SchedulerProcessLog> findByS_S(
		long schedulerProcessId, int status, int start, int end,
		OrderByComparator<SchedulerProcessLog> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByS_S;
				finderArgs = new Object[] {schedulerProcessId, status};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByS_S;
			finderArgs = new Object[] {
				schedulerProcessId, status, start, end, orderByComparator
			};
		}

		List<SchedulerProcessLog> list = null;

		if (useFinderCache) {
			list = (List<SchedulerProcessLog>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (SchedulerProcessLog schedulerProcessLog : list) {
					if ((schedulerProcessId !=
							schedulerProcessLog.getSchedulerProcessId()) ||
						(status != schedulerProcessLog.getStatus())) {

						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(
					4 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				query = new StringBundler(4);
			}

			query.append(_SQL_SELECT_SCHEDULERPROCESSLOG_WHERE);

			query.append(_FINDER_COLUMN_S_S_SCHEDULERPROCESSID_2);

			query.append(_FINDER_COLUMN_S_S_STATUS_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				query.append(SchedulerProcessLogModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(schedulerProcessId);

				qPos.add(status);

				list = (List<SchedulerProcessLog>)QueryUtil.list(
					q, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					finderCache.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception e) {
				if (useFinderCache) {
					finderCache.removeResult(finderPath, finderArgs);
				}

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first scheduler process log in the ordered set where schedulerProcessId = &#63; and status = &#63;.
	 *
	 * @param schedulerProcessId the scheduler process ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching scheduler process log
	 * @throws NoSuchProcessLogException if a matching scheduler process log could not be found
	 */
	@Override
	public SchedulerProcessLog findByS_S_First(
			long schedulerProcessId, int status,
			OrderByComparator<SchedulerProcessLog> orderByComparator)
		throws NoSuchProcessLogException {

		SchedulerProcessLog schedulerProcessLog = fetchByS_S_First(
			schedulerProcessId, status, orderByComparator);

		if (schedulerProcessLog != null) {
			return schedulerProcessLog;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("schedulerProcessId=");
		msg.append(schedulerProcessId);

		msg.append(", status=");
		msg.append(status);

		msg.append("}");

		throw new NoSuchProcessLogException(msg.toString());
	}

	/**
	 * Returns the first scheduler process log in the ordered set where schedulerProcessId = &#63; and status = &#63;.
	 *
	 * @param schedulerProcessId the scheduler process ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching scheduler process log, or <code>null</code> if a matching scheduler process log could not be found
	 */
	@Override
	public SchedulerProcessLog fetchByS_S_First(
		long schedulerProcessId, int status,
		OrderByComparator<SchedulerProcessLog> orderByComparator) {

		List<SchedulerProcessLog> list = findByS_S(
			schedulerProcessId, status, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last scheduler process log in the ordered set where schedulerProcessId = &#63; and status = &#63;.
	 *
	 * @param schedulerProcessId the scheduler process ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching scheduler process log
	 * @throws NoSuchProcessLogException if a matching scheduler process log could not be found
	 */
	@Override
	public SchedulerProcessLog findByS_S_Last(
			long schedulerProcessId, int status,
			OrderByComparator<SchedulerProcessLog> orderByComparator)
		throws NoSuchProcessLogException {

		SchedulerProcessLog schedulerProcessLog = fetchByS_S_Last(
			schedulerProcessId, status, orderByComparator);

		if (schedulerProcessLog != null) {
			return schedulerProcessLog;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("schedulerProcessId=");
		msg.append(schedulerProcessId);

		msg.append(", status=");
		msg.append(status);

		msg.append("}");

		throw new NoSuchProcessLogException(msg.toString());
	}

	/**
	 * Returns the last scheduler process log in the ordered set where schedulerProcessId = &#63; and status = &#63;.
	 *
	 * @param schedulerProcessId the scheduler process ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching scheduler process log, or <code>null</code> if a matching scheduler process log could not be found
	 */
	@Override
	public SchedulerProcessLog fetchByS_S_Last(
		long schedulerProcessId, int status,
		OrderByComparator<SchedulerProcessLog> orderByComparator) {

		int count = countByS_S(schedulerProcessId, status);

		if (count == 0) {
			return null;
		}

		List<SchedulerProcessLog> list = findByS_S(
			schedulerProcessId, status, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the scheduler process logs before and after the current scheduler process log in the ordered set where schedulerProcessId = &#63; and status = &#63;.
	 *
	 * @param schedulerProcessLogId the primary key of the current scheduler process log
	 * @param schedulerProcessId the scheduler process ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next scheduler process log
	 * @throws NoSuchProcessLogException if a scheduler process log with the primary key could not be found
	 */
	@Override
	public SchedulerProcessLog[] findByS_S_PrevAndNext(
			long schedulerProcessLogId, long schedulerProcessId, int status,
			OrderByComparator<SchedulerProcessLog> orderByComparator)
		throws NoSuchProcessLogException {

		SchedulerProcessLog schedulerProcessLog = findByPrimaryKey(
			schedulerProcessLogId);

		Session session = null;

		try {
			session = openSession();

			SchedulerProcessLog[] array = new SchedulerProcessLogImpl[3];

			array[0] = getByS_S_PrevAndNext(
				session, schedulerProcessLog, schedulerProcessId, status,
				orderByComparator, true);

			array[1] = schedulerProcessLog;

			array[2] = getByS_S_PrevAndNext(
				session, schedulerProcessLog, schedulerProcessId, status,
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

	protected SchedulerProcessLog getByS_S_PrevAndNext(
		Session session, SchedulerProcessLog schedulerProcessLog,
		long schedulerProcessId, int status,
		OrderByComparator<SchedulerProcessLog> orderByComparator,
		boolean previous) {

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(
				5 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(4);
		}

		query.append(_SQL_SELECT_SCHEDULERPROCESSLOG_WHERE);

		query.append(_FINDER_COLUMN_S_S_SCHEDULERPROCESSID_2);

		query.append(_FINDER_COLUMN_S_S_STATUS_2);

		if (orderByComparator != null) {
			String[] orderByConditionFields =
				orderByComparator.getOrderByConditionFields();

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
			query.append(SchedulerProcessLogModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(schedulerProcessId);

		qPos.add(status);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						schedulerProcessLog)) {

				qPos.add(orderByConditionValue);
			}
		}

		List<SchedulerProcessLog> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the scheduler process logs where schedulerProcessId = &#63; and status = &#63; from the database.
	 *
	 * @param schedulerProcessId the scheduler process ID
	 * @param status the status
	 */
	@Override
	public void removeByS_S(long schedulerProcessId, int status) {
		for (SchedulerProcessLog schedulerProcessLog :
				findByS_S(
					schedulerProcessId, status, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			remove(schedulerProcessLog);
		}
	}

	/**
	 * Returns the number of scheduler process logs where schedulerProcessId = &#63; and status = &#63;.
	 *
	 * @param schedulerProcessId the scheduler process ID
	 * @param status the status
	 * @return the number of matching scheduler process logs
	 */
	@Override
	public int countByS_S(long schedulerProcessId, int status) {
		FinderPath finderPath = _finderPathCountByS_S;

		Object[] finderArgs = new Object[] {schedulerProcessId, status};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_SCHEDULERPROCESSLOG_WHERE);

			query.append(_FINDER_COLUMN_S_S_SCHEDULERPROCESSID_2);

			query.append(_FINDER_COLUMN_S_S_STATUS_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(schedulerProcessId);

				qPos.add(status);

				count = (Long)q.uniqueResult();

				finderCache.putResult(finderPath, finderArgs, count);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_S_S_SCHEDULERPROCESSID_2 =
		"schedulerProcessLog.schedulerProcessId = ? AND ";

	private static final String _FINDER_COLUMN_S_S_STATUS_2 =
		"schedulerProcessLog.status = ?";

	public SchedulerProcessLogPersistenceImpl() {
		setModelClass(SchedulerProcessLog.class);

		setModelImplClass(SchedulerProcessLogImpl.class);
		setModelPKClass(long.class);

		Map<String, String> dbColumnNames = new HashMap<String, String>();

		dbColumnNames.put("output", "output_");

		setDBColumnNames(dbColumnNames);
	}

	/**
	 * Caches the scheduler process log in the entity cache if it is enabled.
	 *
	 * @param schedulerProcessLog the scheduler process log
	 */
	@Override
	public void cacheResult(SchedulerProcessLog schedulerProcessLog) {
		entityCache.putResult(
			entityCacheEnabled, SchedulerProcessLogImpl.class,
			schedulerProcessLog.getPrimaryKey(), schedulerProcessLog);

		schedulerProcessLog.resetOriginalValues();
	}

	/**
	 * Caches the scheduler process logs in the entity cache if it is enabled.
	 *
	 * @param schedulerProcessLogs the scheduler process logs
	 */
	@Override
	public void cacheResult(List<SchedulerProcessLog> schedulerProcessLogs) {
		for (SchedulerProcessLog schedulerProcessLog : schedulerProcessLogs) {
			if (entityCache.getResult(
					entityCacheEnabled, SchedulerProcessLogImpl.class,
					schedulerProcessLog.getPrimaryKey()) == null) {

				cacheResult(schedulerProcessLog);
			}
			else {
				schedulerProcessLog.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all scheduler process logs.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(SchedulerProcessLogImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the scheduler process log.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(SchedulerProcessLog schedulerProcessLog) {
		entityCache.removeResult(
			entityCacheEnabled, SchedulerProcessLogImpl.class,
			schedulerProcessLog.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@Override
	public void clearCache(List<SchedulerProcessLog> schedulerProcessLogs) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (SchedulerProcessLog schedulerProcessLog : schedulerProcessLogs) {
			entityCache.removeResult(
				entityCacheEnabled, SchedulerProcessLogImpl.class,
				schedulerProcessLog.getPrimaryKey());
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(
				entityCacheEnabled, SchedulerProcessLogImpl.class, primaryKey);
		}
	}

	/**
	 * Creates a new scheduler process log with the primary key. Does not add the scheduler process log to the database.
	 *
	 * @param schedulerProcessLogId the primary key for the new scheduler process log
	 * @return the new scheduler process log
	 */
	@Override
	public SchedulerProcessLog create(long schedulerProcessLogId) {
		SchedulerProcessLog schedulerProcessLog = new SchedulerProcessLogImpl();

		schedulerProcessLog.setNew(true);
		schedulerProcessLog.setPrimaryKey(schedulerProcessLogId);

		schedulerProcessLog.setCompanyId(CompanyThreadLocal.getCompanyId());

		return schedulerProcessLog;
	}

	/**
	 * Removes the scheduler process log with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param schedulerProcessLogId the primary key of the scheduler process log
	 * @return the scheduler process log that was removed
	 * @throws NoSuchProcessLogException if a scheduler process log with the primary key could not be found
	 */
	@Override
	public SchedulerProcessLog remove(long schedulerProcessLogId)
		throws NoSuchProcessLogException {

		return remove((Serializable)schedulerProcessLogId);
	}

	/**
	 * Removes the scheduler process log with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the scheduler process log
	 * @return the scheduler process log that was removed
	 * @throws NoSuchProcessLogException if a scheduler process log with the primary key could not be found
	 */
	@Override
	public SchedulerProcessLog remove(Serializable primaryKey)
		throws NoSuchProcessLogException {

		Session session = null;

		try {
			session = openSession();

			SchedulerProcessLog schedulerProcessLog =
				(SchedulerProcessLog)session.get(
					SchedulerProcessLogImpl.class, primaryKey);

			if (schedulerProcessLog == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchProcessLogException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(schedulerProcessLog);
		}
		catch (NoSuchProcessLogException nsee) {
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
	protected SchedulerProcessLog removeImpl(
		SchedulerProcessLog schedulerProcessLog) {

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(schedulerProcessLog)) {
				schedulerProcessLog = (SchedulerProcessLog)session.get(
					SchedulerProcessLogImpl.class,
					schedulerProcessLog.getPrimaryKeyObj());
			}

			if (schedulerProcessLog != null) {
				session.delete(schedulerProcessLog);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (schedulerProcessLog != null) {
			clearCache(schedulerProcessLog);
		}

		return schedulerProcessLog;
	}

	@Override
	public SchedulerProcessLog updateImpl(
		SchedulerProcessLog schedulerProcessLog) {

		boolean isNew = schedulerProcessLog.isNew();

		if (!(schedulerProcessLog instanceof SchedulerProcessLogModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(schedulerProcessLog.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					schedulerProcessLog);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in schedulerProcessLog proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom SchedulerProcessLog implementation " +
					schedulerProcessLog.getClass());
		}

		SchedulerProcessLogModelImpl schedulerProcessLogModelImpl =
			(SchedulerProcessLogModelImpl)schedulerProcessLog;

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Date now = new Date();

		if (isNew && (schedulerProcessLog.getCreateDate() == null)) {
			if (serviceContext == null) {
				schedulerProcessLog.setCreateDate(now);
			}
			else {
				schedulerProcessLog.setCreateDate(
					serviceContext.getCreateDate(now));
			}
		}

		if (!schedulerProcessLogModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				schedulerProcessLog.setModifiedDate(now);
			}
			else {
				schedulerProcessLog.setModifiedDate(
					serviceContext.getModifiedDate(now));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (schedulerProcessLog.isNew()) {
				session.save(schedulerProcessLog);

				schedulerProcessLog.setNew(false);
			}
			else {
				schedulerProcessLog = (SchedulerProcessLog)session.merge(
					schedulerProcessLog);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (!_columnBitmaskEnabled) {
			finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}
		else if (isNew) {
			Object[] args = new Object[] {
				schedulerProcessLogModelImpl.getSchedulerProcessId()
			};

			finderCache.removeResult(
				_finderPathCountBySchedulerProcessId, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindBySchedulerProcessId, args);

			args = new Object[] {
				schedulerProcessLogModelImpl.getSchedulerProcessId(),
				schedulerProcessLogModelImpl.getStatus()
			};

			finderCache.removeResult(_finderPathCountByS_S, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByS_S, args);

			finderCache.removeResult(_finderPathCountAll, FINDER_ARGS_EMPTY);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindAll, FINDER_ARGS_EMPTY);
		}
		else {
			if ((schedulerProcessLogModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindBySchedulerProcessId.
					 getColumnBitmask()) != 0) {

				Object[] args = new Object[] {
					schedulerProcessLogModelImpl.getOriginalSchedulerProcessId()
				};

				finderCache.removeResult(
					_finderPathCountBySchedulerProcessId, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindBySchedulerProcessId, args);

				args = new Object[] {
					schedulerProcessLogModelImpl.getSchedulerProcessId()
				};

				finderCache.removeResult(
					_finderPathCountBySchedulerProcessId, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindBySchedulerProcessId, args);
			}

			if ((schedulerProcessLogModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByS_S.getColumnBitmask()) !=
					 0) {

				Object[] args = new Object[] {
					schedulerProcessLogModelImpl.
						getOriginalSchedulerProcessId(),
					schedulerProcessLogModelImpl.getOriginalStatus()
				};

				finderCache.removeResult(_finderPathCountByS_S, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByS_S, args);

				args = new Object[] {
					schedulerProcessLogModelImpl.getSchedulerProcessId(),
					schedulerProcessLogModelImpl.getStatus()
				};

				finderCache.removeResult(_finderPathCountByS_S, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByS_S, args);
			}
		}

		entityCache.putResult(
			entityCacheEnabled, SchedulerProcessLogImpl.class,
			schedulerProcessLog.getPrimaryKey(), schedulerProcessLog, false);

		schedulerProcessLog.resetOriginalValues();

		return schedulerProcessLog;
	}

	/**
	 * Returns the scheduler process log with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the scheduler process log
	 * @return the scheduler process log
	 * @throws NoSuchProcessLogException if a scheduler process log with the primary key could not be found
	 */
	@Override
	public SchedulerProcessLog findByPrimaryKey(Serializable primaryKey)
		throws NoSuchProcessLogException {

		SchedulerProcessLog schedulerProcessLog = fetchByPrimaryKey(primaryKey);

		if (schedulerProcessLog == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchProcessLogException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return schedulerProcessLog;
	}

	/**
	 * Returns the scheduler process log with the primary key or throws a <code>NoSuchProcessLogException</code> if it could not be found.
	 *
	 * @param schedulerProcessLogId the primary key of the scheduler process log
	 * @return the scheduler process log
	 * @throws NoSuchProcessLogException if a scheduler process log with the primary key could not be found
	 */
	@Override
	public SchedulerProcessLog findByPrimaryKey(long schedulerProcessLogId)
		throws NoSuchProcessLogException {

		return findByPrimaryKey((Serializable)schedulerProcessLogId);
	}

	/**
	 * Returns the scheduler process log with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param schedulerProcessLogId the primary key of the scheduler process log
	 * @return the scheduler process log, or <code>null</code> if a scheduler process log with the primary key could not be found
	 */
	@Override
	public SchedulerProcessLog fetchByPrimaryKey(long schedulerProcessLogId) {
		return fetchByPrimaryKey((Serializable)schedulerProcessLogId);
	}

	/**
	 * Returns all the scheduler process logs.
	 *
	 * @return the scheduler process logs
	 */
	@Override
	public List<SchedulerProcessLog> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the scheduler process logs.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SchedulerProcessLogModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of scheduler process logs
	 * @param end the upper bound of the range of scheduler process logs (not inclusive)
	 * @return the range of scheduler process logs
	 */
	@Override
	public List<SchedulerProcessLog> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the scheduler process logs.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SchedulerProcessLogModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of scheduler process logs
	 * @param end the upper bound of the range of scheduler process logs (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of scheduler process logs
	 */
	@Override
	public List<SchedulerProcessLog> findAll(
		int start, int end,
		OrderByComparator<SchedulerProcessLog> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the scheduler process logs.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SchedulerProcessLogModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of scheduler process logs
	 * @param end the upper bound of the range of scheduler process logs (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of scheduler process logs
	 */
	@Override
	public List<SchedulerProcessLog> findAll(
		int start, int end,
		OrderByComparator<SchedulerProcessLog> orderByComparator,
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

		List<SchedulerProcessLog> list = null;

		if (useFinderCache) {
			list = (List<SchedulerProcessLog>)finderCache.getResult(
				finderPath, finderArgs, this);
		}

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				query.append(_SQL_SELECT_SCHEDULERPROCESSLOG);

				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_SCHEDULERPROCESSLOG;

				sql = sql.concat(SchedulerProcessLogModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				list = (List<SchedulerProcessLog>)QueryUtil.list(
					q, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					finderCache.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception e) {
				if (useFinderCache) {
					finderCache.removeResult(finderPath, finderArgs);
				}

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Removes all the scheduler process logs from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (SchedulerProcessLog schedulerProcessLog : findAll()) {
			remove(schedulerProcessLog);
		}
	}

	/**
	 * Returns the number of scheduler process logs.
	 *
	 * @return the number of scheduler process logs
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(
			_finderPathCountAll, FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_SCHEDULERPROCESSLOG);

				count = (Long)q.uniqueResult();

				finderCache.putResult(
					_finderPathCountAll, FINDER_ARGS_EMPTY, count);
			}
			catch (Exception e) {
				finderCache.removeResult(
					_finderPathCountAll, FINDER_ARGS_EMPTY);

				throw processException(e);
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
		return "schedulerProcessLogId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_SCHEDULERPROCESSLOG;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return SchedulerProcessLogModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the scheduler process log persistence.
	 */
	@Activate
	public void activate() {
		SchedulerProcessLogModelImpl.setEntityCacheEnabled(entityCacheEnabled);
		SchedulerProcessLogModelImpl.setFinderCacheEnabled(finderCacheEnabled);

		_finderPathWithPaginationFindAll = new FinderPath(
			entityCacheEnabled, finderCacheEnabled,
			SchedulerProcessLogImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);

		_finderPathWithoutPaginationFindAll = new FinderPath(
			entityCacheEnabled, finderCacheEnabled,
			SchedulerProcessLogImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll",
			new String[0]);

		_finderPathCountAll = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll",
			new String[0]);

		_finderPathWithPaginationFindBySchedulerProcessId = new FinderPath(
			entityCacheEnabled, finderCacheEnabled,
			SchedulerProcessLogImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findBySchedulerProcessId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindBySchedulerProcessId = new FinderPath(
			entityCacheEnabled, finderCacheEnabled,
			SchedulerProcessLogImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findBySchedulerProcessId", new String[] {Long.class.getName()},
			SchedulerProcessLogModelImpl.SCHEDULERPROCESSID_COLUMN_BITMASK |
			SchedulerProcessLogModelImpl.MODIFIEDDATE_COLUMN_BITMASK);

		_finderPathCountBySchedulerProcessId = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countBySchedulerProcessId", new String[] {Long.class.getName()});

		_finderPathWithPaginationFindByS_S = new FinderPath(
			entityCacheEnabled, finderCacheEnabled,
			SchedulerProcessLogImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByS_S",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByS_S = new FinderPath(
			entityCacheEnabled, finderCacheEnabled,
			SchedulerProcessLogImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByS_S",
			new String[] {Long.class.getName(), Integer.class.getName()},
			SchedulerProcessLogModelImpl.SCHEDULERPROCESSID_COLUMN_BITMASK |
			SchedulerProcessLogModelImpl.STATUS_COLUMN_BITMASK |
			SchedulerProcessLogModelImpl.MODIFIEDDATE_COLUMN_BITMASK);

		_finderPathCountByS_S = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByS_S",
			new String[] {Long.class.getName(), Integer.class.getName()});
	}

	@Deactivate
	public void deactivate() {
		entityCache.removeCache(SchedulerProcessLogImpl.class.getName());
		finderCache.removeCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@Override
	@Reference(
		target = SchedulerPersistenceConstants.SERVICE_CONFIGURATION_FILTER,
		unbind = "-"
	)
	public void setConfiguration(Configuration configuration) {
		super.setConfiguration(configuration);

		_columnBitmaskEnabled = GetterUtil.getBoolean(
			configuration.get(
				"value.object.column.bitmask.enabled.com.liferay.scheduler.model.SchedulerProcessLog"),
			true);
	}

	@Override
	@Reference(
		target = SchedulerPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setDataSource(DataSource dataSource) {
		super.setDataSource(dataSource);
	}

	@Override
	@Reference(
		target = SchedulerPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setSessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	private boolean _columnBitmaskEnabled;

	@Reference
	protected EntityCache entityCache;

	@Reference
	protected FinderCache finderCache;

	private static final String _SQL_SELECT_SCHEDULERPROCESSLOG =
		"SELECT schedulerProcessLog FROM SchedulerProcessLog schedulerProcessLog";

	private static final String _SQL_SELECT_SCHEDULERPROCESSLOG_WHERE =
		"SELECT schedulerProcessLog FROM SchedulerProcessLog schedulerProcessLog WHERE ";

	private static final String _SQL_COUNT_SCHEDULERPROCESSLOG =
		"SELECT COUNT(schedulerProcessLog) FROM SchedulerProcessLog schedulerProcessLog";

	private static final String _SQL_COUNT_SCHEDULERPROCESSLOG_WHERE =
		"SELECT COUNT(schedulerProcessLog) FROM SchedulerProcessLog schedulerProcessLog WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS = "schedulerProcessLog.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No SchedulerProcessLog exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No SchedulerProcessLog exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		SchedulerProcessLogPersistenceImpl.class);

	private static final Set<String> _badColumnNames = SetUtil.fromArray(
		new String[] {"output"});

	static {
		try {
			Class.forName(SchedulerPersistenceConstants.class.getName());
		}
		catch (ClassNotFoundException cnfe) {
			throw new ExceptionInInitializerError(cnfe);
		}
	}

}