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
import com.liferay.scheduler.exception.NoSuchProcessException;
import com.liferay.scheduler.model.SchedulerProcess;
import com.liferay.scheduler.model.impl.SchedulerProcessImpl;
import com.liferay.scheduler.model.impl.SchedulerProcessModelImpl;
import com.liferay.scheduler.service.persistence.SchedulerProcessPersistence;
import com.liferay.scheduler.service.persistence.impl.constants.SchedulerPersistenceConstants;

import java.io.Serializable;

import java.lang.reflect.InvocationHandler;

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
 * The persistence implementation for the scheduler process service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Alessio Antonio Rendina
 * @generated
 */
@Component(service = SchedulerProcessPersistence.class)
public class SchedulerProcessPersistenceImpl
	extends BasePersistenceImpl<SchedulerProcess>
	implements SchedulerProcessPersistence {

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>SchedulerProcessUtil</code> to access the scheduler process persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		SchedulerProcessImpl.class.getName();

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
	 * Returns all the scheduler processes where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching scheduler processes
	 */
	@Override
	public List<SchedulerProcess> findByCompanyId(long companyId) {
		return findByCompanyId(
			companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the scheduler processes where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SchedulerProcessModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of scheduler processes
	 * @param end the upper bound of the range of scheduler processes (not inclusive)
	 * @return the range of matching scheduler processes
	 */
	@Override
	public List<SchedulerProcess> findByCompanyId(
		long companyId, int start, int end) {

		return findByCompanyId(companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the scheduler processes where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SchedulerProcessModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of scheduler processes
	 * @param end the upper bound of the range of scheduler processes (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching scheduler processes
	 */
	@Override
	public List<SchedulerProcess> findByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<SchedulerProcess> orderByComparator) {

		return findByCompanyId(companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the scheduler processes where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SchedulerProcessModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of scheduler processes
	 * @param end the upper bound of the range of scheduler processes (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching scheduler processes
	 */
	@Override
	public List<SchedulerProcess> findByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<SchedulerProcess> orderByComparator,
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

		List<SchedulerProcess> list = null;

		if (useFinderCache) {
			list = (List<SchedulerProcess>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (SchedulerProcess schedulerProcess : list) {
					if (companyId != schedulerProcess.getCompanyId()) {
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

			query.append(_SQL_SELECT_SCHEDULERPROCESS_WHERE);

			query.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				query.append(SchedulerProcessModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				list = (List<SchedulerProcess>)QueryUtil.list(
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
	 * Returns the first scheduler process in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching scheduler process
	 * @throws NoSuchProcessException if a matching scheduler process could not be found
	 */
	@Override
	public SchedulerProcess findByCompanyId_First(
			long companyId,
			OrderByComparator<SchedulerProcess> orderByComparator)
		throws NoSuchProcessException {

		SchedulerProcess schedulerProcess = fetchByCompanyId_First(
			companyId, orderByComparator);

		if (schedulerProcess != null) {
			return schedulerProcess;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("companyId=");
		msg.append(companyId);

		msg.append("}");

		throw new NoSuchProcessException(msg.toString());
	}

	/**
	 * Returns the first scheduler process in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching scheduler process, or <code>null</code> if a matching scheduler process could not be found
	 */
	@Override
	public SchedulerProcess fetchByCompanyId_First(
		long companyId, OrderByComparator<SchedulerProcess> orderByComparator) {

		List<SchedulerProcess> list = findByCompanyId(
			companyId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last scheduler process in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching scheduler process
	 * @throws NoSuchProcessException if a matching scheduler process could not be found
	 */
	@Override
	public SchedulerProcess findByCompanyId_Last(
			long companyId,
			OrderByComparator<SchedulerProcess> orderByComparator)
		throws NoSuchProcessException {

		SchedulerProcess schedulerProcess = fetchByCompanyId_Last(
			companyId, orderByComparator);

		if (schedulerProcess != null) {
			return schedulerProcess;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("companyId=");
		msg.append(companyId);

		msg.append("}");

		throw new NoSuchProcessException(msg.toString());
	}

	/**
	 * Returns the last scheduler process in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching scheduler process, or <code>null</code> if a matching scheduler process could not be found
	 */
	@Override
	public SchedulerProcess fetchByCompanyId_Last(
		long companyId, OrderByComparator<SchedulerProcess> orderByComparator) {

		int count = countByCompanyId(companyId);

		if (count == 0) {
			return null;
		}

		List<SchedulerProcess> list = findByCompanyId(
			companyId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the scheduler processes before and after the current scheduler process in the ordered set where companyId = &#63;.
	 *
	 * @param schedulerProcessId the primary key of the current scheduler process
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next scheduler process
	 * @throws NoSuchProcessException if a scheduler process with the primary key could not be found
	 */
	@Override
	public SchedulerProcess[] findByCompanyId_PrevAndNext(
			long schedulerProcessId, long companyId,
			OrderByComparator<SchedulerProcess> orderByComparator)
		throws NoSuchProcessException {

		SchedulerProcess schedulerProcess = findByPrimaryKey(
			schedulerProcessId);

		Session session = null;

		try {
			session = openSession();

			SchedulerProcess[] array = new SchedulerProcessImpl[3];

			array[0] = getByCompanyId_PrevAndNext(
				session, schedulerProcess, companyId, orderByComparator, true);

			array[1] = schedulerProcess;

			array[2] = getByCompanyId_PrevAndNext(
				session, schedulerProcess, companyId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected SchedulerProcess getByCompanyId_PrevAndNext(
		Session session, SchedulerProcess schedulerProcess, long companyId,
		OrderByComparator<SchedulerProcess> orderByComparator,
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

		query.append(_SQL_SELECT_SCHEDULERPROCESS_WHERE);

		query.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

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
			query.append(SchedulerProcessModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(companyId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						schedulerProcess)) {

				qPos.add(orderByConditionValue);
			}
		}

		List<SchedulerProcess> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the scheduler processes where companyId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 */
	@Override
	public void removeByCompanyId(long companyId) {
		for (SchedulerProcess schedulerProcess :
				findByCompanyId(
					companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(schedulerProcess);
		}
	}

	/**
	 * Returns the number of scheduler processes where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching scheduler processes
	 */
	@Override
	public int countByCompanyId(long companyId) {
		FinderPath finderPath = _finderPathCountByCompanyId;

		Object[] finderArgs = new Object[] {companyId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_SCHEDULERPROCESS_WHERE);

			query.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

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

	private static final String _FINDER_COLUMN_COMPANYID_COMPANYID_2 =
		"schedulerProcess.companyId = ?";

	private FinderPath _finderPathFetchByC_N;
	private FinderPath _finderPathCountByC_N;

	/**
	 * Returns the scheduler process where companyId = &#63; and name = &#63; or throws a <code>NoSuchProcessException</code> if it could not be found.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @return the matching scheduler process
	 * @throws NoSuchProcessException if a matching scheduler process could not be found
	 */
	@Override
	public SchedulerProcess findByC_N(long companyId, String name)
		throws NoSuchProcessException {

		SchedulerProcess schedulerProcess = fetchByC_N(companyId, name);

		if (schedulerProcess == null) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("companyId=");
			msg.append(companyId);

			msg.append(", name=");
			msg.append(name);

			msg.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(msg.toString());
			}

			throw new NoSuchProcessException(msg.toString());
		}

		return schedulerProcess;
	}

	/**
	 * Returns the scheduler process where companyId = &#63; and name = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @return the matching scheduler process, or <code>null</code> if a matching scheduler process could not be found
	 */
	@Override
	public SchedulerProcess fetchByC_N(long companyId, String name) {
		return fetchByC_N(companyId, name, true);
	}

	/**
	 * Returns the scheduler process where companyId = &#63; and name = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching scheduler process, or <code>null</code> if a matching scheduler process could not be found
	 */
	@Override
	public SchedulerProcess fetchByC_N(
		long companyId, String name, boolean useFinderCache) {

		name = Objects.toString(name, "");

		Object[] finderArgs = null;

		if (useFinderCache) {
			finderArgs = new Object[] {companyId, name};
		}

		Object result = null;

		if (useFinderCache) {
			result = finderCache.getResult(
				_finderPathFetchByC_N, finderArgs, this);
		}

		if (result instanceof SchedulerProcess) {
			SchedulerProcess schedulerProcess = (SchedulerProcess)result;

			if ((companyId != schedulerProcess.getCompanyId()) ||
				!Objects.equals(name, schedulerProcess.getName())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_SELECT_SCHEDULERPROCESS_WHERE);

			query.append(_FINDER_COLUMN_C_N_COMPANYID_2);

			boolean bindName = false;

			if (name.isEmpty()) {
				query.append(_FINDER_COLUMN_C_N_NAME_3);
			}
			else {
				bindName = true;

				query.append(_FINDER_COLUMN_C_N_NAME_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				if (bindName) {
					qPos.add(name);
				}

				List<SchedulerProcess> list = q.list();

				if (list.isEmpty()) {
					if (useFinderCache) {
						finderCache.putResult(
							_finderPathFetchByC_N, finderArgs, list);
					}
				}
				else {
					SchedulerProcess schedulerProcess = list.get(0);

					result = schedulerProcess;

					cacheResult(schedulerProcess);
				}
			}
			catch (Exception e) {
				if (useFinderCache) {
					finderCache.removeResult(_finderPathFetchByC_N, finderArgs);
				}

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		if (result instanceof List<?>) {
			return null;
		}
		else {
			return (SchedulerProcess)result;
		}
	}

	/**
	 * Removes the scheduler process where companyId = &#63; and name = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @return the scheduler process that was removed
	 */
	@Override
	public SchedulerProcess removeByC_N(long companyId, String name)
		throws NoSuchProcessException {

		SchedulerProcess schedulerProcess = findByC_N(companyId, name);

		return remove(schedulerProcess);
	}

	/**
	 * Returns the number of scheduler processes where companyId = &#63; and name = &#63;.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @return the number of matching scheduler processes
	 */
	@Override
	public int countByC_N(long companyId, String name) {
		name = Objects.toString(name, "");

		FinderPath finderPath = _finderPathCountByC_N;

		Object[] finderArgs = new Object[] {companyId, name};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_SCHEDULERPROCESS_WHERE);

			query.append(_FINDER_COLUMN_C_N_COMPANYID_2);

			boolean bindName = false;

			if (name.isEmpty()) {
				query.append(_FINDER_COLUMN_C_N_NAME_3);
			}
			else {
				bindName = true;

				query.append(_FINDER_COLUMN_C_N_NAME_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				if (bindName) {
					qPos.add(name);
				}

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

	private static final String _FINDER_COLUMN_C_N_COMPANYID_2 =
		"schedulerProcess.companyId = ? AND ";

	private static final String _FINDER_COLUMN_C_N_NAME_2 =
		"schedulerProcess.name = ?";

	private static final String _FINDER_COLUMN_C_N_NAME_3 =
		"(schedulerProcess.name IS NULL OR schedulerProcess.name = '')";

	private FinderPath _finderPathWithPaginationFindByC_T;
	private FinderPath _finderPathWithoutPaginationFindByC_T;
	private FinderPath _finderPathCountByC_T;

	/**
	 * Returns all the scheduler processes where companyId = &#63; and type = &#63;.
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @return the matching scheduler processes
	 */
	@Override
	public List<SchedulerProcess> findByC_T(long companyId, String type) {
		return findByC_T(
			companyId, type, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the scheduler processes where companyId = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SchedulerProcessModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @param start the lower bound of the range of scheduler processes
	 * @param end the upper bound of the range of scheduler processes (not inclusive)
	 * @return the range of matching scheduler processes
	 */
	@Override
	public List<SchedulerProcess> findByC_T(
		long companyId, String type, int start, int end) {

		return findByC_T(companyId, type, start, end, null);
	}

	/**
	 * Returns an ordered range of all the scheduler processes where companyId = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SchedulerProcessModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @param start the lower bound of the range of scheduler processes
	 * @param end the upper bound of the range of scheduler processes (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching scheduler processes
	 */
	@Override
	public List<SchedulerProcess> findByC_T(
		long companyId, String type, int start, int end,
		OrderByComparator<SchedulerProcess> orderByComparator) {

		return findByC_T(companyId, type, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the scheduler processes where companyId = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SchedulerProcessModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @param start the lower bound of the range of scheduler processes
	 * @param end the upper bound of the range of scheduler processes (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching scheduler processes
	 */
	@Override
	public List<SchedulerProcess> findByC_T(
		long companyId, String type, int start, int end,
		OrderByComparator<SchedulerProcess> orderByComparator,
		boolean useFinderCache) {

		type = Objects.toString(type, "");

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByC_T;
				finderArgs = new Object[] {companyId, type};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByC_T;
			finderArgs = new Object[] {
				companyId, type, start, end, orderByComparator
			};
		}

		List<SchedulerProcess> list = null;

		if (useFinderCache) {
			list = (List<SchedulerProcess>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (SchedulerProcess schedulerProcess : list) {
					if ((companyId != schedulerProcess.getCompanyId()) ||
						!type.equals(schedulerProcess.getType())) {

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

			query.append(_SQL_SELECT_SCHEDULERPROCESS_WHERE);

			query.append(_FINDER_COLUMN_C_T_COMPANYID_2);

			boolean bindType = false;

			if (type.isEmpty()) {
				query.append(_FINDER_COLUMN_C_T_TYPE_3);
			}
			else {
				bindType = true;

				query.append(_FINDER_COLUMN_C_T_TYPE_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				query.append(SchedulerProcessModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				if (bindType) {
					qPos.add(type);
				}

				list = (List<SchedulerProcess>)QueryUtil.list(
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
	 * Returns the first scheduler process in the ordered set where companyId = &#63; and type = &#63;.
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching scheduler process
	 * @throws NoSuchProcessException if a matching scheduler process could not be found
	 */
	@Override
	public SchedulerProcess findByC_T_First(
			long companyId, String type,
			OrderByComparator<SchedulerProcess> orderByComparator)
		throws NoSuchProcessException {

		SchedulerProcess schedulerProcess = fetchByC_T_First(
			companyId, type, orderByComparator);

		if (schedulerProcess != null) {
			return schedulerProcess;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("companyId=");
		msg.append(companyId);

		msg.append(", type=");
		msg.append(type);

		msg.append("}");

		throw new NoSuchProcessException(msg.toString());
	}

	/**
	 * Returns the first scheduler process in the ordered set where companyId = &#63; and type = &#63;.
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching scheduler process, or <code>null</code> if a matching scheduler process could not be found
	 */
	@Override
	public SchedulerProcess fetchByC_T_First(
		long companyId, String type,
		OrderByComparator<SchedulerProcess> orderByComparator) {

		List<SchedulerProcess> list = findByC_T(
			companyId, type, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last scheduler process in the ordered set where companyId = &#63; and type = &#63;.
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching scheduler process
	 * @throws NoSuchProcessException if a matching scheduler process could not be found
	 */
	@Override
	public SchedulerProcess findByC_T_Last(
			long companyId, String type,
			OrderByComparator<SchedulerProcess> orderByComparator)
		throws NoSuchProcessException {

		SchedulerProcess schedulerProcess = fetchByC_T_Last(
			companyId, type, orderByComparator);

		if (schedulerProcess != null) {
			return schedulerProcess;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("companyId=");
		msg.append(companyId);

		msg.append(", type=");
		msg.append(type);

		msg.append("}");

		throw new NoSuchProcessException(msg.toString());
	}

	/**
	 * Returns the last scheduler process in the ordered set where companyId = &#63; and type = &#63;.
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching scheduler process, or <code>null</code> if a matching scheduler process could not be found
	 */
	@Override
	public SchedulerProcess fetchByC_T_Last(
		long companyId, String type,
		OrderByComparator<SchedulerProcess> orderByComparator) {

		int count = countByC_T(companyId, type);

		if (count == 0) {
			return null;
		}

		List<SchedulerProcess> list = findByC_T(
			companyId, type, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the scheduler processes before and after the current scheduler process in the ordered set where companyId = &#63; and type = &#63;.
	 *
	 * @param schedulerProcessId the primary key of the current scheduler process
	 * @param companyId the company ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next scheduler process
	 * @throws NoSuchProcessException if a scheduler process with the primary key could not be found
	 */
	@Override
	public SchedulerProcess[] findByC_T_PrevAndNext(
			long schedulerProcessId, long companyId, String type,
			OrderByComparator<SchedulerProcess> orderByComparator)
		throws NoSuchProcessException {

		type = Objects.toString(type, "");

		SchedulerProcess schedulerProcess = findByPrimaryKey(
			schedulerProcessId);

		Session session = null;

		try {
			session = openSession();

			SchedulerProcess[] array = new SchedulerProcessImpl[3];

			array[0] = getByC_T_PrevAndNext(
				session, schedulerProcess, companyId, type, orderByComparator,
				true);

			array[1] = schedulerProcess;

			array[2] = getByC_T_PrevAndNext(
				session, schedulerProcess, companyId, type, orderByComparator,
				false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected SchedulerProcess getByC_T_PrevAndNext(
		Session session, SchedulerProcess schedulerProcess, long companyId,
		String type, OrderByComparator<SchedulerProcess> orderByComparator,
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

		query.append(_SQL_SELECT_SCHEDULERPROCESS_WHERE);

		query.append(_FINDER_COLUMN_C_T_COMPANYID_2);

		boolean bindType = false;

		if (type.isEmpty()) {
			query.append(_FINDER_COLUMN_C_T_TYPE_3);
		}
		else {
			bindType = true;

			query.append(_FINDER_COLUMN_C_T_TYPE_2);
		}

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
			query.append(SchedulerProcessModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(companyId);

		if (bindType) {
			qPos.add(type);
		}

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						schedulerProcess)) {

				qPos.add(orderByConditionValue);
			}
		}

		List<SchedulerProcess> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the scheduler processes where companyId = &#63; and type = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param type the type
	 */
	@Override
	public void removeByC_T(long companyId, String type) {
		for (SchedulerProcess schedulerProcess :
				findByC_T(
					companyId, type, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(schedulerProcess);
		}
	}

	/**
	 * Returns the number of scheduler processes where companyId = &#63; and type = &#63;.
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @return the number of matching scheduler processes
	 */
	@Override
	public int countByC_T(long companyId, String type) {
		type = Objects.toString(type, "");

		FinderPath finderPath = _finderPathCountByC_T;

		Object[] finderArgs = new Object[] {companyId, type};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_SCHEDULERPROCESS_WHERE);

			query.append(_FINDER_COLUMN_C_T_COMPANYID_2);

			boolean bindType = false;

			if (type.isEmpty()) {
				query.append(_FINDER_COLUMN_C_T_TYPE_3);
			}
			else {
				bindType = true;

				query.append(_FINDER_COLUMN_C_T_TYPE_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				if (bindType) {
					qPos.add(type);
				}

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

	private static final String _FINDER_COLUMN_C_T_COMPANYID_2 =
		"schedulerProcess.companyId = ? AND ";

	private static final String _FINDER_COLUMN_C_T_TYPE_2 =
		"schedulerProcess.type = ?";

	private static final String _FINDER_COLUMN_C_T_TYPE_3 =
		"(schedulerProcess.type IS NULL OR schedulerProcess.type = '')";

	public SchedulerProcessPersistenceImpl() {
		setModelClass(SchedulerProcess.class);

		setModelImplClass(SchedulerProcessImpl.class);
		setModelPKClass(long.class);

		Map<String, String> dbColumnNames = new HashMap<String, String>();

		dbColumnNames.put("type", "type_");
		dbColumnNames.put("system", "system_");
		dbColumnNames.put("active", "active_");

		setDBColumnNames(dbColumnNames);
	}

	/**
	 * Caches the scheduler process in the entity cache if it is enabled.
	 *
	 * @param schedulerProcess the scheduler process
	 */
	@Override
	public void cacheResult(SchedulerProcess schedulerProcess) {
		entityCache.putResult(
			entityCacheEnabled, SchedulerProcessImpl.class,
			schedulerProcess.getPrimaryKey(), schedulerProcess);

		finderCache.putResult(
			_finderPathFetchByC_N,
			new Object[] {
				schedulerProcess.getCompanyId(), schedulerProcess.getName()
			},
			schedulerProcess);

		schedulerProcess.resetOriginalValues();
	}

	/**
	 * Caches the scheduler processes in the entity cache if it is enabled.
	 *
	 * @param schedulerProcesses the scheduler processes
	 */
	@Override
	public void cacheResult(List<SchedulerProcess> schedulerProcesses) {
		for (SchedulerProcess schedulerProcess : schedulerProcesses) {
			if (entityCache.getResult(
					entityCacheEnabled, SchedulerProcessImpl.class,
					schedulerProcess.getPrimaryKey()) == null) {

				cacheResult(schedulerProcess);
			}
			else {
				schedulerProcess.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all scheduler processes.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(SchedulerProcessImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the scheduler process.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(SchedulerProcess schedulerProcess) {
		entityCache.removeResult(
			entityCacheEnabled, SchedulerProcessImpl.class,
			schedulerProcess.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache(
			(SchedulerProcessModelImpl)schedulerProcess, true);
	}

	@Override
	public void clearCache(List<SchedulerProcess> schedulerProcesses) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (SchedulerProcess schedulerProcess : schedulerProcesses) {
			entityCache.removeResult(
				entityCacheEnabled, SchedulerProcessImpl.class,
				schedulerProcess.getPrimaryKey());

			clearUniqueFindersCache(
				(SchedulerProcessModelImpl)schedulerProcess, true);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(
				entityCacheEnabled, SchedulerProcessImpl.class, primaryKey);
		}
	}

	protected void cacheUniqueFindersCache(
		SchedulerProcessModelImpl schedulerProcessModelImpl) {

		Object[] args = new Object[] {
			schedulerProcessModelImpl.getCompanyId(),
			schedulerProcessModelImpl.getName()
		};

		finderCache.putResult(
			_finderPathCountByC_N, args, Long.valueOf(1), false);
		finderCache.putResult(
			_finderPathFetchByC_N, args, schedulerProcessModelImpl, false);
	}

	protected void clearUniqueFindersCache(
		SchedulerProcessModelImpl schedulerProcessModelImpl,
		boolean clearCurrent) {

		if (clearCurrent) {
			Object[] args = new Object[] {
				schedulerProcessModelImpl.getCompanyId(),
				schedulerProcessModelImpl.getName()
			};

			finderCache.removeResult(_finderPathCountByC_N, args);
			finderCache.removeResult(_finderPathFetchByC_N, args);
		}

		if ((schedulerProcessModelImpl.getColumnBitmask() &
			 _finderPathFetchByC_N.getColumnBitmask()) != 0) {

			Object[] args = new Object[] {
				schedulerProcessModelImpl.getOriginalCompanyId(),
				schedulerProcessModelImpl.getOriginalName()
			};

			finderCache.removeResult(_finderPathCountByC_N, args);
			finderCache.removeResult(_finderPathFetchByC_N, args);
		}
	}

	/**
	 * Creates a new scheduler process with the primary key. Does not add the scheduler process to the database.
	 *
	 * @param schedulerProcessId the primary key for the new scheduler process
	 * @return the new scheduler process
	 */
	@Override
	public SchedulerProcess create(long schedulerProcessId) {
		SchedulerProcess schedulerProcess = new SchedulerProcessImpl();

		schedulerProcess.setNew(true);
		schedulerProcess.setPrimaryKey(schedulerProcessId);

		schedulerProcess.setCompanyId(CompanyThreadLocal.getCompanyId());

		return schedulerProcess;
	}

	/**
	 * Removes the scheduler process with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param schedulerProcessId the primary key of the scheduler process
	 * @return the scheduler process that was removed
	 * @throws NoSuchProcessException if a scheduler process with the primary key could not be found
	 */
	@Override
	public SchedulerProcess remove(long schedulerProcessId)
		throws NoSuchProcessException {

		return remove((Serializable)schedulerProcessId);
	}

	/**
	 * Removes the scheduler process with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the scheduler process
	 * @return the scheduler process that was removed
	 * @throws NoSuchProcessException if a scheduler process with the primary key could not be found
	 */
	@Override
	public SchedulerProcess remove(Serializable primaryKey)
		throws NoSuchProcessException {

		Session session = null;

		try {
			session = openSession();

			SchedulerProcess schedulerProcess = (SchedulerProcess)session.get(
				SchedulerProcessImpl.class, primaryKey);

			if (schedulerProcess == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchProcessException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(schedulerProcess);
		}
		catch (NoSuchProcessException nsee) {
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
	protected SchedulerProcess removeImpl(SchedulerProcess schedulerProcess) {
		Session session = null;

		try {
			session = openSession();

			if (!session.contains(schedulerProcess)) {
				schedulerProcess = (SchedulerProcess)session.get(
					SchedulerProcessImpl.class,
					schedulerProcess.getPrimaryKeyObj());
			}

			if (schedulerProcess != null) {
				session.delete(schedulerProcess);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (schedulerProcess != null) {
			clearCache(schedulerProcess);
		}

		return schedulerProcess;
	}

	@Override
	public SchedulerProcess updateImpl(SchedulerProcess schedulerProcess) {
		boolean isNew = schedulerProcess.isNew();

		if (!(schedulerProcess instanceof SchedulerProcessModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(schedulerProcess.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					schedulerProcess);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in schedulerProcess proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom SchedulerProcess implementation " +
					schedulerProcess.getClass());
		}

		SchedulerProcessModelImpl schedulerProcessModelImpl =
			(SchedulerProcessModelImpl)schedulerProcess;

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Date now = new Date();

		if (isNew && (schedulerProcess.getCreateDate() == null)) {
			if (serviceContext == null) {
				schedulerProcess.setCreateDate(now);
			}
			else {
				schedulerProcess.setCreateDate(
					serviceContext.getCreateDate(now));
			}
		}

		if (!schedulerProcessModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				schedulerProcess.setModifiedDate(now);
			}
			else {
				schedulerProcess.setModifiedDate(
					serviceContext.getModifiedDate(now));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (schedulerProcess.isNew()) {
				session.save(schedulerProcess);

				schedulerProcess.setNew(false);
			}
			else {
				schedulerProcess = (SchedulerProcess)session.merge(
					schedulerProcess);
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
				schedulerProcessModelImpl.getCompanyId()
			};

			finderCache.removeResult(_finderPathCountByCompanyId, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByCompanyId, args);

			args = new Object[] {
				schedulerProcessModelImpl.getCompanyId(),
				schedulerProcessModelImpl.getType()
			};

			finderCache.removeResult(_finderPathCountByC_T, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByC_T, args);

			finderCache.removeResult(_finderPathCountAll, FINDER_ARGS_EMPTY);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindAll, FINDER_ARGS_EMPTY);
		}
		else {
			if ((schedulerProcessModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByCompanyId.
					 getColumnBitmask()) != 0) {

				Object[] args = new Object[] {
					schedulerProcessModelImpl.getOriginalCompanyId()
				};

				finderCache.removeResult(_finderPathCountByCompanyId, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByCompanyId, args);

				args = new Object[] {schedulerProcessModelImpl.getCompanyId()};

				finderCache.removeResult(_finderPathCountByCompanyId, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByCompanyId, args);
			}

			if ((schedulerProcessModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByC_T.getColumnBitmask()) !=
					 0) {

				Object[] args = new Object[] {
					schedulerProcessModelImpl.getOriginalCompanyId(),
					schedulerProcessModelImpl.getOriginalType()
				};

				finderCache.removeResult(_finderPathCountByC_T, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByC_T, args);

				args = new Object[] {
					schedulerProcessModelImpl.getCompanyId(),
					schedulerProcessModelImpl.getType()
				};

				finderCache.removeResult(_finderPathCountByC_T, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByC_T, args);
			}
		}

		entityCache.putResult(
			entityCacheEnabled, SchedulerProcessImpl.class,
			schedulerProcess.getPrimaryKey(), schedulerProcess, false);

		clearUniqueFindersCache(schedulerProcessModelImpl, false);
		cacheUniqueFindersCache(schedulerProcessModelImpl);

		schedulerProcess.resetOriginalValues();

		return schedulerProcess;
	}

	/**
	 * Returns the scheduler process with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the scheduler process
	 * @return the scheduler process
	 * @throws NoSuchProcessException if a scheduler process with the primary key could not be found
	 */
	@Override
	public SchedulerProcess findByPrimaryKey(Serializable primaryKey)
		throws NoSuchProcessException {

		SchedulerProcess schedulerProcess = fetchByPrimaryKey(primaryKey);

		if (schedulerProcess == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchProcessException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return schedulerProcess;
	}

	/**
	 * Returns the scheduler process with the primary key or throws a <code>NoSuchProcessException</code> if it could not be found.
	 *
	 * @param schedulerProcessId the primary key of the scheduler process
	 * @return the scheduler process
	 * @throws NoSuchProcessException if a scheduler process with the primary key could not be found
	 */
	@Override
	public SchedulerProcess findByPrimaryKey(long schedulerProcessId)
		throws NoSuchProcessException {

		return findByPrimaryKey((Serializable)schedulerProcessId);
	}

	/**
	 * Returns the scheduler process with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param schedulerProcessId the primary key of the scheduler process
	 * @return the scheduler process, or <code>null</code> if a scheduler process with the primary key could not be found
	 */
	@Override
	public SchedulerProcess fetchByPrimaryKey(long schedulerProcessId) {
		return fetchByPrimaryKey((Serializable)schedulerProcessId);
	}

	/**
	 * Returns all the scheduler processes.
	 *
	 * @return the scheduler processes
	 */
	@Override
	public List<SchedulerProcess> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the scheduler processes.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SchedulerProcessModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of scheduler processes
	 * @param end the upper bound of the range of scheduler processes (not inclusive)
	 * @return the range of scheduler processes
	 */
	@Override
	public List<SchedulerProcess> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the scheduler processes.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SchedulerProcessModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of scheduler processes
	 * @param end the upper bound of the range of scheduler processes (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of scheduler processes
	 */
	@Override
	public List<SchedulerProcess> findAll(
		int start, int end,
		OrderByComparator<SchedulerProcess> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the scheduler processes.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>SchedulerProcessModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of scheduler processes
	 * @param end the upper bound of the range of scheduler processes (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of scheduler processes
	 */
	@Override
	public List<SchedulerProcess> findAll(
		int start, int end,
		OrderByComparator<SchedulerProcess> orderByComparator,
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

		List<SchedulerProcess> list = null;

		if (useFinderCache) {
			list = (List<SchedulerProcess>)finderCache.getResult(
				finderPath, finderArgs, this);
		}

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				query.append(_SQL_SELECT_SCHEDULERPROCESS);

				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_SCHEDULERPROCESS;

				sql = sql.concat(SchedulerProcessModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				list = (List<SchedulerProcess>)QueryUtil.list(
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
	 * Removes all the scheduler processes from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (SchedulerProcess schedulerProcess : findAll()) {
			remove(schedulerProcess);
		}
	}

	/**
	 * Returns the number of scheduler processes.
	 *
	 * @return the number of scheduler processes
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(
			_finderPathCountAll, FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_SCHEDULERPROCESS);

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
		return "schedulerProcessId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_SCHEDULERPROCESS;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return SchedulerProcessModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the scheduler process persistence.
	 */
	@Activate
	public void activate() {
		SchedulerProcessModelImpl.setEntityCacheEnabled(entityCacheEnabled);
		SchedulerProcessModelImpl.setFinderCacheEnabled(finderCacheEnabled);

		_finderPathWithPaginationFindAll = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, SchedulerProcessImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);

		_finderPathWithoutPaginationFindAll = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, SchedulerProcessImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll",
			new String[0]);

		_finderPathCountAll = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll",
			new String[0]);

		_finderPathWithPaginationFindByCompanyId = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, SchedulerProcessImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByCompanyId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByCompanyId = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, SchedulerProcessImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByCompanyId",
			new String[] {Long.class.getName()},
			SchedulerProcessModelImpl.COMPANYID_COLUMN_BITMASK |
			SchedulerProcessModelImpl.MODIFIEDDATE_COLUMN_BITMASK);

		_finderPathCountByCompanyId = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByCompanyId",
			new String[] {Long.class.getName()});

		_finderPathFetchByC_N = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, SchedulerProcessImpl.class,
			FINDER_CLASS_NAME_ENTITY, "fetchByC_N",
			new String[] {Long.class.getName(), String.class.getName()},
			SchedulerProcessModelImpl.COMPANYID_COLUMN_BITMASK |
			SchedulerProcessModelImpl.NAME_COLUMN_BITMASK);

		_finderPathCountByC_N = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_N",
			new String[] {Long.class.getName(), String.class.getName()});

		_finderPathWithPaginationFindByC_T = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, SchedulerProcessImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByC_T",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByC_T = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, SchedulerProcessImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByC_T",
			new String[] {Long.class.getName(), String.class.getName()},
			SchedulerProcessModelImpl.COMPANYID_COLUMN_BITMASK |
			SchedulerProcessModelImpl.TYPE_COLUMN_BITMASK |
			SchedulerProcessModelImpl.MODIFIEDDATE_COLUMN_BITMASK);

		_finderPathCountByC_T = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_T",
			new String[] {Long.class.getName(), String.class.getName()});
	}

	@Deactivate
	public void deactivate() {
		entityCache.removeCache(SchedulerProcessImpl.class.getName());
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
				"value.object.column.bitmask.enabled.com.liferay.scheduler.model.SchedulerProcess"),
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

	private static final String _SQL_SELECT_SCHEDULERPROCESS =
		"SELECT schedulerProcess FROM SchedulerProcess schedulerProcess";

	private static final String _SQL_SELECT_SCHEDULERPROCESS_WHERE =
		"SELECT schedulerProcess FROM SchedulerProcess schedulerProcess WHERE ";

	private static final String _SQL_COUNT_SCHEDULERPROCESS =
		"SELECT COUNT(schedulerProcess) FROM SchedulerProcess schedulerProcess";

	private static final String _SQL_COUNT_SCHEDULERPROCESS_WHERE =
		"SELECT COUNT(schedulerProcess) FROM SchedulerProcess schedulerProcess WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS = "schedulerProcess.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No SchedulerProcess exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No SchedulerProcess exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		SchedulerProcessPersistenceImpl.class);

	private static final Set<String> _badColumnNames = SetUtil.fromArray(
		new String[] {"type", "system", "active"});

	static {
		try {
			Class.forName(SchedulerPersistenceConstants.class.getName());
		}
		catch (ClassNotFoundException cnfe) {
			throw new ExceptionInInitializerError(cnfe);
		}
	}

}