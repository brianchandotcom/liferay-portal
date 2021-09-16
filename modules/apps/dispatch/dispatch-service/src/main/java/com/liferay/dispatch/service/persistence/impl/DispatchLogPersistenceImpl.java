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

package com.liferay.dispatch.service.persistence.impl;

import com.liferay.dispatch.exception.NoSuchLogException;
import com.liferay.dispatch.model.DispatchLog;
import com.liferay.dispatch.model.DispatchLogTable;
import com.liferay.dispatch.model.impl.DispatchLogImpl;
import com.liferay.dispatch.model.impl.DispatchLogModelImpl;
import com.liferay.dispatch.service.persistence.DispatchLogPersistence;
import com.liferay.dispatch.service.persistence.impl.constants.DispatchPersistenceConstants;
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
 * The persistence implementation for the dispatch log service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Matija Petanjek
 * @generated
 */
@Component(service = {DispatchLogPersistence.class, BasePersistence.class})
public class DispatchLogPersistenceImpl
	extends BasePersistenceImpl<DispatchLog> implements DispatchLogPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>DispatchLogUtil</code> to access the dispatch log persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		DispatchLogImpl.class.getName();

	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List1";

	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List2";

	private FinderPath _finderPathWithPaginationFindAll;
	private FinderPath _finderPathWithoutPaginationFindAll;
	private FinderPath _finderPathCountAll;
	private FinderPath _finderPathWithPaginationFindByDispatchTriggerId;
	private FinderPath _finderPathWithoutPaginationFindByDispatchTriggerId;
	private FinderPath _finderPathCountByDispatchTriggerId;

	/**
	 * Returns all the dispatch logs where dispatchTriggerId = &#63;.
	 *
	 * @param dispatchTriggerId the dispatch trigger ID
	 * @return the matching dispatch logs
	 */
	@Override
	public List<DispatchLog> findByDispatchTriggerId(long dispatchTriggerId) {
		return findByDispatchTriggerId(
			dispatchTriggerId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the dispatch logs where dispatchTriggerId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DispatchLogModelImpl</code>.
	 * </p>
	 *
	 * @param dispatchTriggerId the dispatch trigger ID
	 * @param start the lower bound of the range of dispatch logs
	 * @param end the upper bound of the range of dispatch logs (not inclusive)
	 * @return the range of matching dispatch logs
	 */
	@Override
	public List<DispatchLog> findByDispatchTriggerId(
		long dispatchTriggerId, int start, int end) {

		return findByDispatchTriggerId(dispatchTriggerId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the dispatch logs where dispatchTriggerId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DispatchLogModelImpl</code>.
	 * </p>
	 *
	 * @param dispatchTriggerId the dispatch trigger ID
	 * @param start the lower bound of the range of dispatch logs
	 * @param end the upper bound of the range of dispatch logs (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching dispatch logs
	 */
	@Override
	public List<DispatchLog> findByDispatchTriggerId(
		long dispatchTriggerId, int start, int end,
		OrderByComparator<DispatchLog> orderByComparator) {

		return findByDispatchTriggerId(
			dispatchTriggerId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the dispatch logs where dispatchTriggerId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DispatchLogModelImpl</code>.
	 * </p>
	 *
	 * @param dispatchTriggerId the dispatch trigger ID
	 * @param start the lower bound of the range of dispatch logs
	 * @param end the upper bound of the range of dispatch logs (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching dispatch logs
	 */
	@Override
	public List<DispatchLog> findByDispatchTriggerId(
		long dispatchTriggerId, int start, int end,
		OrderByComparator<DispatchLog> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath =
					_finderPathWithoutPaginationFindByDispatchTriggerId;
				finderArgs = new Object[] {dispatchTriggerId};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByDispatchTriggerId;
			finderArgs = new Object[] {
				dispatchTriggerId, start, end, orderByComparator
			};
		}

		List<DispatchLog> list = null;

		if (useFinderCache) {
			list = (List<DispatchLog>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (DispatchLog dispatchLog : list) {
					if (dispatchTriggerId !=
							dispatchLog.getDispatchTriggerId()) {

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

			sb.append(_SQL_SELECT_DISPATCHLOG_WHERE);

			sb.append(_FINDER_COLUMN_DISPATCHTRIGGERID_DISPATCHTRIGGERID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(DispatchLogModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(dispatchTriggerId);

				list = (List<DispatchLog>)QueryUtil.list(
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
	 * Returns the first dispatch log in the ordered set where dispatchTriggerId = &#63;.
	 *
	 * @param dispatchTriggerId the dispatch trigger ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching dispatch log
	 * @throws NoSuchLogException if a matching dispatch log could not be found
	 */
	@Override
	public DispatchLog findByDispatchTriggerId_First(
			long dispatchTriggerId,
			OrderByComparator<DispatchLog> orderByComparator)
		throws NoSuchLogException {

		DispatchLog dispatchLog = fetchByDispatchTriggerId_First(
			dispatchTriggerId, orderByComparator);

		if (dispatchLog != null) {
			return dispatchLog;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("dispatchTriggerId=");
		sb.append(dispatchTriggerId);

		sb.append("}");

		throw new NoSuchLogException(sb.toString());
	}

	/**
	 * Returns the first dispatch log in the ordered set where dispatchTriggerId = &#63;.
	 *
	 * @param dispatchTriggerId the dispatch trigger ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching dispatch log, or <code>null</code> if a matching dispatch log could not be found
	 */
	@Override
	public DispatchLog fetchByDispatchTriggerId_First(
		long dispatchTriggerId,
		OrderByComparator<DispatchLog> orderByComparator) {

		List<DispatchLog> list = findByDispatchTriggerId(
			dispatchTriggerId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last dispatch log in the ordered set where dispatchTriggerId = &#63;.
	 *
	 * @param dispatchTriggerId the dispatch trigger ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching dispatch log
	 * @throws NoSuchLogException if a matching dispatch log could not be found
	 */
	@Override
	public DispatchLog findByDispatchTriggerId_Last(
			long dispatchTriggerId,
			OrderByComparator<DispatchLog> orderByComparator)
		throws NoSuchLogException {

		DispatchLog dispatchLog = fetchByDispatchTriggerId_Last(
			dispatchTriggerId, orderByComparator);

		if (dispatchLog != null) {
			return dispatchLog;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("dispatchTriggerId=");
		sb.append(dispatchTriggerId);

		sb.append("}");

		throw new NoSuchLogException(sb.toString());
	}

	/**
	 * Returns the last dispatch log in the ordered set where dispatchTriggerId = &#63;.
	 *
	 * @param dispatchTriggerId the dispatch trigger ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching dispatch log, or <code>null</code> if a matching dispatch log could not be found
	 */
	@Override
	public DispatchLog fetchByDispatchTriggerId_Last(
		long dispatchTriggerId,
		OrderByComparator<DispatchLog> orderByComparator) {

		int count = countByDispatchTriggerId(dispatchTriggerId);

		if (count == 0) {
			return null;
		}

		List<DispatchLog> list = findByDispatchTriggerId(
			dispatchTriggerId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the dispatch logs before and after the current dispatch log in the ordered set where dispatchTriggerId = &#63;.
	 *
	 * @param dispatchLogId the primary key of the current dispatch log
	 * @param dispatchTriggerId the dispatch trigger ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next dispatch log
	 * @throws NoSuchLogException if a dispatch log with the primary key could not be found
	 */
	@Override
	public DispatchLog[] findByDispatchTriggerId_PrevAndNext(
			long dispatchLogId, long dispatchTriggerId,
			OrderByComparator<DispatchLog> orderByComparator)
		throws NoSuchLogException {

		DispatchLog dispatchLog = findByPrimaryKey(dispatchLogId);

		Session session = null;

		try {
			session = openSession();

			DispatchLog[] array = new DispatchLogImpl[3];

			array[0] = getByDispatchTriggerId_PrevAndNext(
				session, dispatchLog, dispatchTriggerId, orderByComparator,
				true);

			array[1] = dispatchLog;

			array[2] = getByDispatchTriggerId_PrevAndNext(
				session, dispatchLog, dispatchTriggerId, orderByComparator,
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

	protected DispatchLog getByDispatchTriggerId_PrevAndNext(
		Session session, DispatchLog dispatchLog, long dispatchTriggerId,
		OrderByComparator<DispatchLog> orderByComparator, boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(3);
		}

		sb.append(_SQL_SELECT_DISPATCHLOG_WHERE);

		sb.append(_FINDER_COLUMN_DISPATCHTRIGGERID_DISPATCHTRIGGERID_2);

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
			sb.append(DispatchLogModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(dispatchTriggerId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(dispatchLog)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<DispatchLog> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the dispatch logs where dispatchTriggerId = &#63; from the database.
	 *
	 * @param dispatchTriggerId the dispatch trigger ID
	 */
	@Override
	public void removeByDispatchTriggerId(long dispatchTriggerId) {
		for (DispatchLog dispatchLog :
				findByDispatchTriggerId(
					dispatchTriggerId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(dispatchLog);
		}
	}

	/**
	 * Returns the number of dispatch logs where dispatchTriggerId = &#63;.
	 *
	 * @param dispatchTriggerId the dispatch trigger ID
	 * @return the number of matching dispatch logs
	 */
	@Override
	public int countByDispatchTriggerId(long dispatchTriggerId) {
		FinderPath finderPath = _finderPathCountByDispatchTriggerId;

		Object[] finderArgs = new Object[] {dispatchTriggerId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_DISPATCHLOG_WHERE);

			sb.append(_FINDER_COLUMN_DISPATCHTRIGGERID_DISPATCHTRIGGERID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(dispatchTriggerId);

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
		_FINDER_COLUMN_DISPATCHTRIGGERID_DISPATCHTRIGGERID_2 =
			"dispatchLog.dispatchTriggerId = ?";

	private FinderPath _finderPathWithPaginationFindByDTI_S;
	private FinderPath _finderPathWithoutPaginationFindByDTI_S;
	private FinderPath _finderPathCountByDTI_S;

	/**
	 * Returns all the dispatch logs where dispatchTriggerId = &#63; and status = &#63;.
	 *
	 * @param dispatchTriggerId the dispatch trigger ID
	 * @param status the status
	 * @return the matching dispatch logs
	 */
	@Override
	public List<DispatchLog> findByDTI_S(long dispatchTriggerId, int status) {
		return findByDTI_S(
			dispatchTriggerId, status, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the dispatch logs where dispatchTriggerId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DispatchLogModelImpl</code>.
	 * </p>
	 *
	 * @param dispatchTriggerId the dispatch trigger ID
	 * @param status the status
	 * @param start the lower bound of the range of dispatch logs
	 * @param end the upper bound of the range of dispatch logs (not inclusive)
	 * @return the range of matching dispatch logs
	 */
	@Override
	public List<DispatchLog> findByDTI_S(
		long dispatchTriggerId, int status, int start, int end) {

		return findByDTI_S(dispatchTriggerId, status, start, end, null);
	}

	/**
	 * Returns an ordered range of all the dispatch logs where dispatchTriggerId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DispatchLogModelImpl</code>.
	 * </p>
	 *
	 * @param dispatchTriggerId the dispatch trigger ID
	 * @param status the status
	 * @param start the lower bound of the range of dispatch logs
	 * @param end the upper bound of the range of dispatch logs (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching dispatch logs
	 */
	@Override
	public List<DispatchLog> findByDTI_S(
		long dispatchTriggerId, int status, int start, int end,
		OrderByComparator<DispatchLog> orderByComparator) {

		return findByDTI_S(
			dispatchTriggerId, status, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the dispatch logs where dispatchTriggerId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DispatchLogModelImpl</code>.
	 * </p>
	 *
	 * @param dispatchTriggerId the dispatch trigger ID
	 * @param status the status
	 * @param start the lower bound of the range of dispatch logs
	 * @param end the upper bound of the range of dispatch logs (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching dispatch logs
	 */
	@Override
	public List<DispatchLog> findByDTI_S(
		long dispatchTriggerId, int status, int start, int end,
		OrderByComparator<DispatchLog> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByDTI_S;
				finderArgs = new Object[] {dispatchTriggerId, status};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByDTI_S;
			finderArgs = new Object[] {
				dispatchTriggerId, status, start, end, orderByComparator
			};
		}

		List<DispatchLog> list = null;

		if (useFinderCache) {
			list = (List<DispatchLog>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (DispatchLog dispatchLog : list) {
					if ((dispatchTriggerId !=
							dispatchLog.getDispatchTriggerId()) ||
						(status != dispatchLog.getStatus())) {

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
					4 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				sb = new StringBundler(4);
			}

			sb.append(_SQL_SELECT_DISPATCHLOG_WHERE);

			sb.append(_FINDER_COLUMN_DTI_S_DISPATCHTRIGGERID_2);

			sb.append(_FINDER_COLUMN_DTI_S_STATUS_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(DispatchLogModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(dispatchTriggerId);

				queryPos.add(status);

				list = (List<DispatchLog>)QueryUtil.list(
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
	 * Returns the first dispatch log in the ordered set where dispatchTriggerId = &#63; and status = &#63;.
	 *
	 * @param dispatchTriggerId the dispatch trigger ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching dispatch log
	 * @throws NoSuchLogException if a matching dispatch log could not be found
	 */
	@Override
	public DispatchLog findByDTI_S_First(
			long dispatchTriggerId, int status,
			OrderByComparator<DispatchLog> orderByComparator)
		throws NoSuchLogException {

		DispatchLog dispatchLog = fetchByDTI_S_First(
			dispatchTriggerId, status, orderByComparator);

		if (dispatchLog != null) {
			return dispatchLog;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("dispatchTriggerId=");
		sb.append(dispatchTriggerId);

		sb.append(", status=");
		sb.append(status);

		sb.append("}");

		throw new NoSuchLogException(sb.toString());
	}

	/**
	 * Returns the first dispatch log in the ordered set where dispatchTriggerId = &#63; and status = &#63;.
	 *
	 * @param dispatchTriggerId the dispatch trigger ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching dispatch log, or <code>null</code> if a matching dispatch log could not be found
	 */
	@Override
	public DispatchLog fetchByDTI_S_First(
		long dispatchTriggerId, int status,
		OrderByComparator<DispatchLog> orderByComparator) {

		List<DispatchLog> list = findByDTI_S(
			dispatchTriggerId, status, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last dispatch log in the ordered set where dispatchTriggerId = &#63; and status = &#63;.
	 *
	 * @param dispatchTriggerId the dispatch trigger ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching dispatch log
	 * @throws NoSuchLogException if a matching dispatch log could not be found
	 */
	@Override
	public DispatchLog findByDTI_S_Last(
			long dispatchTriggerId, int status,
			OrderByComparator<DispatchLog> orderByComparator)
		throws NoSuchLogException {

		DispatchLog dispatchLog = fetchByDTI_S_Last(
			dispatchTriggerId, status, orderByComparator);

		if (dispatchLog != null) {
			return dispatchLog;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("dispatchTriggerId=");
		sb.append(dispatchTriggerId);

		sb.append(", status=");
		sb.append(status);

		sb.append("}");

		throw new NoSuchLogException(sb.toString());
	}

	/**
	 * Returns the last dispatch log in the ordered set where dispatchTriggerId = &#63; and status = &#63;.
	 *
	 * @param dispatchTriggerId the dispatch trigger ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching dispatch log, or <code>null</code> if a matching dispatch log could not be found
	 */
	@Override
	public DispatchLog fetchByDTI_S_Last(
		long dispatchTriggerId, int status,
		OrderByComparator<DispatchLog> orderByComparator) {

		int count = countByDTI_S(dispatchTriggerId, status);

		if (count == 0) {
			return null;
		}

		List<DispatchLog> list = findByDTI_S(
			dispatchTriggerId, status, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the dispatch logs before and after the current dispatch log in the ordered set where dispatchTriggerId = &#63; and status = &#63;.
	 *
	 * @param dispatchLogId the primary key of the current dispatch log
	 * @param dispatchTriggerId the dispatch trigger ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next dispatch log
	 * @throws NoSuchLogException if a dispatch log with the primary key could not be found
	 */
	@Override
	public DispatchLog[] findByDTI_S_PrevAndNext(
			long dispatchLogId, long dispatchTriggerId, int status,
			OrderByComparator<DispatchLog> orderByComparator)
		throws NoSuchLogException {

		DispatchLog dispatchLog = findByPrimaryKey(dispatchLogId);

		Session session = null;

		try {
			session = openSession();

			DispatchLog[] array = new DispatchLogImpl[3];

			array[0] = getByDTI_S_PrevAndNext(
				session, dispatchLog, dispatchTriggerId, status,
				orderByComparator, true);

			array[1] = dispatchLog;

			array[2] = getByDTI_S_PrevAndNext(
				session, dispatchLog, dispatchTriggerId, status,
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

	protected DispatchLog getByDTI_S_PrevAndNext(
		Session session, DispatchLog dispatchLog, long dispatchTriggerId,
		int status, OrderByComparator<DispatchLog> orderByComparator,
		boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				5 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(4);
		}

		sb.append(_SQL_SELECT_DISPATCHLOG_WHERE);

		sb.append(_FINDER_COLUMN_DTI_S_DISPATCHTRIGGERID_2);

		sb.append(_FINDER_COLUMN_DTI_S_STATUS_2);

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
			sb.append(DispatchLogModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(dispatchTriggerId);

		queryPos.add(status);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(dispatchLog)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<DispatchLog> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the dispatch logs where dispatchTriggerId = &#63; and status = &#63; from the database.
	 *
	 * @param dispatchTriggerId the dispatch trigger ID
	 * @param status the status
	 */
	@Override
	public void removeByDTI_S(long dispatchTriggerId, int status) {
		for (DispatchLog dispatchLog :
				findByDTI_S(
					dispatchTriggerId, status, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			remove(dispatchLog);
		}
	}

	/**
	 * Returns the number of dispatch logs where dispatchTriggerId = &#63; and status = &#63;.
	 *
	 * @param dispatchTriggerId the dispatch trigger ID
	 * @param status the status
	 * @return the number of matching dispatch logs
	 */
	@Override
	public int countByDTI_S(long dispatchTriggerId, int status) {
		FinderPath finderPath = _finderPathCountByDTI_S;

		Object[] finderArgs = new Object[] {dispatchTriggerId, status};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_DISPATCHLOG_WHERE);

			sb.append(_FINDER_COLUMN_DTI_S_DISPATCHTRIGGERID_2);

			sb.append(_FINDER_COLUMN_DTI_S_STATUS_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(dispatchTriggerId);

				queryPos.add(status);

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

	private static final String _FINDER_COLUMN_DTI_S_DISPATCHTRIGGERID_2 =
		"dispatchLog.dispatchTriggerId = ? AND ";

	private static final String _FINDER_COLUMN_DTI_S_STATUS_2 =
		"dispatchLog.status = ?";

	public DispatchLogPersistenceImpl() {
		Map<String, String> dbColumnNames = new HashMap<String, String>();

		dbColumnNames.put("output", "output_");

		setDBColumnNames(dbColumnNames);

		setModelClass(DispatchLog.class);

		setModelImplClass(DispatchLogImpl.class);
		setModelPKClass(long.class);

		setTable(DispatchLogTable.INSTANCE);
	}

	/**
	 * Caches the dispatch log in the entity cache if it is enabled.
	 *
	 * @param dispatchLog the dispatch log
	 */
	@Override
	public void cacheResult(DispatchLog dispatchLog) {
		entityCache.putResult(
			DispatchLogImpl.class, dispatchLog.getPrimaryKey(), dispatchLog);
	}

	private int _valueObjectFinderCacheListThreshold;

	/**
	 * Caches the dispatch logs in the entity cache if it is enabled.
	 *
	 * @param dispatchLogs the dispatch logs
	 */
	@Override
	public void cacheResult(List<DispatchLog> dispatchLogs) {
		if ((_valueObjectFinderCacheListThreshold == 0) ||
			((_valueObjectFinderCacheListThreshold > 0) &&
			 (dispatchLogs.size() > _valueObjectFinderCacheListThreshold))) {

			return;
		}

		for (DispatchLog dispatchLog : dispatchLogs) {
			if (entityCache.getResult(
					DispatchLogImpl.class, dispatchLog.getPrimaryKey()) ==
						null) {

				cacheResult(dispatchLog);
			}
		}
	}

	/**
	 * Clears the cache for all dispatch logs.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(DispatchLogImpl.class);

		finderCache.clearCache(DispatchLogImpl.class);
	}

	/**
	 * Clears the cache for the dispatch log.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(DispatchLog dispatchLog) {
		entityCache.removeResult(DispatchLogImpl.class, dispatchLog);
	}

	@Override
	public void clearCache(List<DispatchLog> dispatchLogs) {
		for (DispatchLog dispatchLog : dispatchLogs) {
			entityCache.removeResult(DispatchLogImpl.class, dispatchLog);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(DispatchLogImpl.class);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(DispatchLogImpl.class, primaryKey);
		}
	}

	/**
	 * Creates a new dispatch log with the primary key. Does not add the dispatch log to the database.
	 *
	 * @param dispatchLogId the primary key for the new dispatch log
	 * @return the new dispatch log
	 */
	@Override
	public DispatchLog create(long dispatchLogId) {
		DispatchLog dispatchLog = new DispatchLogImpl();

		dispatchLog.setNew(true);
		dispatchLog.setPrimaryKey(dispatchLogId);

		dispatchLog.setCompanyId(CompanyThreadLocal.getCompanyId());

		return dispatchLog;
	}

	/**
	 * Removes the dispatch log with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param dispatchLogId the primary key of the dispatch log
	 * @return the dispatch log that was removed
	 * @throws NoSuchLogException if a dispatch log with the primary key could not be found
	 */
	@Override
	public DispatchLog remove(long dispatchLogId) throws NoSuchLogException {
		return remove((Serializable)dispatchLogId);
	}

	/**
	 * Removes the dispatch log with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the dispatch log
	 * @return the dispatch log that was removed
	 * @throws NoSuchLogException if a dispatch log with the primary key could not be found
	 */
	@Override
	public DispatchLog remove(Serializable primaryKey)
		throws NoSuchLogException {

		Session session = null;

		try {
			session = openSession();

			DispatchLog dispatchLog = (DispatchLog)session.get(
				DispatchLogImpl.class, primaryKey);

			if (dispatchLog == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchLogException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(dispatchLog);
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
	protected DispatchLog removeImpl(DispatchLog dispatchLog) {
		Session session = null;

		try {
			session = openSession();

			if (!session.contains(dispatchLog)) {
				dispatchLog = (DispatchLog)session.get(
					DispatchLogImpl.class, dispatchLog.getPrimaryKeyObj());
			}

			if (dispatchLog != null) {
				session.delete(dispatchLog);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (dispatchLog != null) {
			clearCache(dispatchLog);
		}

		return dispatchLog;
	}

	@Override
	public DispatchLog updateImpl(DispatchLog dispatchLog) {
		boolean isNew = dispatchLog.isNew();

		if (!(dispatchLog instanceof DispatchLogModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(dispatchLog.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(dispatchLog);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in dispatchLog proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom DispatchLog implementation " +
					dispatchLog.getClass());
		}

		DispatchLogModelImpl dispatchLogModelImpl =
			(DispatchLogModelImpl)dispatchLog;

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Date date = new Date();

		if (isNew && (dispatchLog.getCreateDate() == null)) {
			if (serviceContext == null) {
				dispatchLog.setCreateDate(date);
			}
			else {
				dispatchLog.setCreateDate(serviceContext.getCreateDate(date));
			}
		}

		if (!dispatchLogModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				dispatchLog.setModifiedDate(date);
			}
			else {
				dispatchLog.setModifiedDate(
					serviceContext.getModifiedDate(date));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (isNew) {
				session.save(dispatchLog);
			}
			else {
				dispatchLog = (DispatchLog)session.merge(dispatchLog);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		entityCache.putResult(
			DispatchLogImpl.class, dispatchLogModelImpl, false, true);

		if (isNew) {
			dispatchLog.setNew(false);
		}

		dispatchLog.resetOriginalValues();

		return dispatchLog;
	}

	/**
	 * Returns the dispatch log with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the dispatch log
	 * @return the dispatch log
	 * @throws NoSuchLogException if a dispatch log with the primary key could not be found
	 */
	@Override
	public DispatchLog findByPrimaryKey(Serializable primaryKey)
		throws NoSuchLogException {

		DispatchLog dispatchLog = fetchByPrimaryKey(primaryKey);

		if (dispatchLog == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchLogException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return dispatchLog;
	}

	/**
	 * Returns the dispatch log with the primary key or throws a <code>NoSuchLogException</code> if it could not be found.
	 *
	 * @param dispatchLogId the primary key of the dispatch log
	 * @return the dispatch log
	 * @throws NoSuchLogException if a dispatch log with the primary key could not be found
	 */
	@Override
	public DispatchLog findByPrimaryKey(long dispatchLogId)
		throws NoSuchLogException {

		return findByPrimaryKey((Serializable)dispatchLogId);
	}

	/**
	 * Returns the dispatch log with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param dispatchLogId the primary key of the dispatch log
	 * @return the dispatch log, or <code>null</code> if a dispatch log with the primary key could not be found
	 */
	@Override
	public DispatchLog fetchByPrimaryKey(long dispatchLogId) {
		return fetchByPrimaryKey((Serializable)dispatchLogId);
	}

	/**
	 * Returns all the dispatch logs.
	 *
	 * @return the dispatch logs
	 */
	@Override
	public List<DispatchLog> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the dispatch logs.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DispatchLogModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of dispatch logs
	 * @param end the upper bound of the range of dispatch logs (not inclusive)
	 * @return the range of dispatch logs
	 */
	@Override
	public List<DispatchLog> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the dispatch logs.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DispatchLogModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of dispatch logs
	 * @param end the upper bound of the range of dispatch logs (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of dispatch logs
	 */
	@Override
	public List<DispatchLog> findAll(
		int start, int end, OrderByComparator<DispatchLog> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the dispatch logs.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DispatchLogModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of dispatch logs
	 * @param end the upper bound of the range of dispatch logs (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of dispatch logs
	 */
	@Override
	public List<DispatchLog> findAll(
		int start, int end, OrderByComparator<DispatchLog> orderByComparator,
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

		List<DispatchLog> list = null;

		if (useFinderCache) {
			list = (List<DispatchLog>)finderCache.getResult(
				finderPath, finderArgs);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_DISPATCHLOG);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_DISPATCHLOG;

				sql = sql.concat(DispatchLogModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<DispatchLog>)QueryUtil.list(
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
	 * Removes all the dispatch logs from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (DispatchLog dispatchLog : findAll()) {
			remove(dispatchLog);
		}
	}

	/**
	 * Returns the number of dispatch logs.
	 *
	 * @return the number of dispatch logs
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(
			_finderPathCountAll, FINDER_ARGS_EMPTY);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(_SQL_COUNT_DISPATCHLOG);

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
		return "dispatchLogId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_DISPATCHLOG;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return DispatchLogModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the dispatch log persistence.
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

		_finderPathWithPaginationFindByDispatchTriggerId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByDispatchTriggerId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"dispatchTriggerId"}, true);

		_finderPathWithoutPaginationFindByDispatchTriggerId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByDispatchTriggerId", new String[] {Long.class.getName()},
			new String[] {"dispatchTriggerId"}, true);

		_finderPathCountByDispatchTriggerId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByDispatchTriggerId", new String[] {Long.class.getName()},
			new String[] {"dispatchTriggerId"}, false);

		_finderPathWithPaginationFindByDTI_S = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByDTI_S",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {"dispatchTriggerId", "status"}, true);

		_finderPathWithoutPaginationFindByDTI_S = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByDTI_S",
			new String[] {Long.class.getName(), Integer.class.getName()},
			new String[] {"dispatchTriggerId", "status"}, true);

		_finderPathCountByDTI_S = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByDTI_S",
			new String[] {Long.class.getName(), Integer.class.getName()},
			new String[] {"dispatchTriggerId", "status"}, false);
	}

	@Deactivate
	public void deactivate() {
		entityCache.removeCache(DispatchLogImpl.class.getName());
	}

	@Override
	@Reference(
		target = DispatchPersistenceConstants.SERVICE_CONFIGURATION_FILTER,
		unbind = "-"
	)
	public void setConfiguration(Configuration configuration) {
	}

	@Override
	@Reference(
		target = DispatchPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setDataSource(DataSource dataSource) {
		super.setDataSource(dataSource);
	}

	@Override
	@Reference(
		target = DispatchPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setSessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	@Reference
	protected EntityCache entityCache;

	@Reference
	protected FinderCache finderCache;

	private static final String _SQL_SELECT_DISPATCHLOG =
		"SELECT dispatchLog FROM DispatchLog dispatchLog";

	private static final String _SQL_SELECT_DISPATCHLOG_WHERE =
		"SELECT dispatchLog FROM DispatchLog dispatchLog WHERE ";

	private static final String _SQL_COUNT_DISPATCHLOG =
		"SELECT COUNT(dispatchLog) FROM DispatchLog dispatchLog";

	private static final String _SQL_COUNT_DISPATCHLOG_WHERE =
		"SELECT COUNT(dispatchLog) FROM DispatchLog dispatchLog WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS = "dispatchLog.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No DispatchLog exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No DispatchLog exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		DispatchLogPersistenceImpl.class);

	private static final Set<String> _badColumnNames = SetUtil.fromArray(
		new String[] {"output"});

	@Override
	protected FinderCache getFinderCache() {
		return finderCache;
	}

	@Reference
	private DispatchLogModelArgumentsResolver
		_dispatchLogModelArgumentsResolver;

}