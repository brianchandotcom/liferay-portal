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

package com.liferay.portal.background.task.service.persistence.impl;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.background.task.exception.NoSuchBackgroundTaskException;
import com.liferay.portal.background.task.model.BackgroundTask;
import com.liferay.portal.background.task.model.BackgroundTaskTable;
import com.liferay.portal.background.task.model.impl.BackgroundTaskImpl;
import com.liferay.portal.background.task.model.impl.BackgroundTaskModelImpl;
import com.liferay.portal.background.task.service.persistence.BackgroundTaskPersistence;
import com.liferay.portal.background.task.service.persistence.impl.constants.BackgroundTaskPersistenceConstants;
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
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.StringUtil;

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
 * The persistence implementation for the background task service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
@Component(service = {BackgroundTaskPersistence.class, BasePersistence.class})
public class BackgroundTaskPersistenceImpl
	extends BasePersistenceImpl<BackgroundTask>
	implements BackgroundTaskPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>BackgroundTaskUtil</code> to access the background task persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		BackgroundTaskImpl.class.getName();

	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List1";

	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List2";

	private FinderPath _finderPathWithPaginationFindAll;
	private FinderPath _finderPathWithoutPaginationFindAll;
	private FinderPath _finderPathCountAll;
	private FinderPath _finderPathWithPaginationFindByGroupId;
	private FinderPath _finderPathWithoutPaginationFindByGroupId;
	private FinderPath _finderPathCountByGroupId;

	/**
	 * Returns all the background tasks where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching background tasks
	 */
	@Override
	public List<BackgroundTask> findByGroupId(long groupId) {
		return findByGroupId(
			groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the background tasks where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BackgroundTaskModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of background tasks
	 * @param end the upper bound of the range of background tasks (not inclusive)
	 * @return the range of matching background tasks
	 */
	@Override
	public List<BackgroundTask> findByGroupId(
		long groupId, int start, int end) {

		return findByGroupId(groupId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the background tasks where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BackgroundTaskModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of background tasks
	 * @param end the upper bound of the range of background tasks (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching background tasks
	 */
	@Override
	public List<BackgroundTask> findByGroupId(
		long groupId, int start, int end,
		OrderByComparator<BackgroundTask> orderByComparator) {

		return findByGroupId(groupId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the background tasks where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BackgroundTaskModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of background tasks
	 * @param end the upper bound of the range of background tasks (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching background tasks
	 */
	@Override
	public List<BackgroundTask> findByGroupId(
		long groupId, int start, int end,
		OrderByComparator<BackgroundTask> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByGroupId;
				finderArgs = new Object[] {groupId};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByGroupId;
			finderArgs = new Object[] {groupId, start, end, orderByComparator};
		}

		List<BackgroundTask> list = null;

		if (useFinderCache) {
			list = (List<BackgroundTask>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (BackgroundTask backgroundTask : list) {
					if (groupId != backgroundTask.getGroupId()) {
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

			sb.append(_SQL_SELECT_BACKGROUNDTASK_WHERE);

			sb.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(BackgroundTaskModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				list = (List<BackgroundTask>)QueryUtil.list(
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
	 * Returns the first background task in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching background task
	 * @throws NoSuchBackgroundTaskException if a matching background task could not be found
	 */
	@Override
	public BackgroundTask findByGroupId_First(
			long groupId, OrderByComparator<BackgroundTask> orderByComparator)
		throws NoSuchBackgroundTaskException {

		BackgroundTask backgroundTask = fetchByGroupId_First(
			groupId, orderByComparator);

		if (backgroundTask != null) {
			return backgroundTask;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append("}");

		throw new NoSuchBackgroundTaskException(sb.toString());
	}

	/**
	 * Returns the first background task in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching background task, or <code>null</code> if a matching background task could not be found
	 */
	@Override
	public BackgroundTask fetchByGroupId_First(
		long groupId, OrderByComparator<BackgroundTask> orderByComparator) {

		List<BackgroundTask> list = findByGroupId(
			groupId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last background task in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching background task
	 * @throws NoSuchBackgroundTaskException if a matching background task could not be found
	 */
	@Override
	public BackgroundTask findByGroupId_Last(
			long groupId, OrderByComparator<BackgroundTask> orderByComparator)
		throws NoSuchBackgroundTaskException {

		BackgroundTask backgroundTask = fetchByGroupId_Last(
			groupId, orderByComparator);

		if (backgroundTask != null) {
			return backgroundTask;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append("}");

		throw new NoSuchBackgroundTaskException(sb.toString());
	}

	/**
	 * Returns the last background task in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching background task, or <code>null</code> if a matching background task could not be found
	 */
	@Override
	public BackgroundTask fetchByGroupId_Last(
		long groupId, OrderByComparator<BackgroundTask> orderByComparator) {

		int count = countByGroupId(groupId);

		if (count == 0) {
			return null;
		}

		List<BackgroundTask> list = findByGroupId(
			groupId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the background tasks before and after the current background task in the ordered set where groupId = &#63;.
	 *
	 * @param backgroundTaskId the primary key of the current background task
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next background task
	 * @throws NoSuchBackgroundTaskException if a background task with the primary key could not be found
	 */
	@Override
	public BackgroundTask[] findByGroupId_PrevAndNext(
			long backgroundTaskId, long groupId,
			OrderByComparator<BackgroundTask> orderByComparator)
		throws NoSuchBackgroundTaskException {

		BackgroundTask backgroundTask = findByPrimaryKey(backgroundTaskId);

		Session session = null;

		try {
			session = openSession();

			BackgroundTask[] array = new BackgroundTaskImpl[3];

			array[0] = getByGroupId_PrevAndNext(
				session, backgroundTask, groupId, orderByComparator, true);

			array[1] = backgroundTask;

			array[2] = getByGroupId_PrevAndNext(
				session, backgroundTask, groupId, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected BackgroundTask getByGroupId_PrevAndNext(
		Session session, BackgroundTask backgroundTask, long groupId,
		OrderByComparator<BackgroundTask> orderByComparator, boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(3);
		}

		sb.append(_SQL_SELECT_BACKGROUNDTASK_WHERE);

		sb.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

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
			sb.append(BackgroundTaskModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(groupId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						backgroundTask)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<BackgroundTask> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the background tasks where groupId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 */
	@Override
	public void removeByGroupId(long groupId) {
		for (BackgroundTask backgroundTask :
				findByGroupId(
					groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(backgroundTask);
		}
	}

	/**
	 * Returns the number of background tasks where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching background tasks
	 */
	@Override
	public int countByGroupId(long groupId) {
		FinderPath finderPath = _finderPathCountByGroupId;

		Object[] finderArgs = new Object[] {groupId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_BACKGROUNDTASK_WHERE);

			sb.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

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

	private static final String _FINDER_COLUMN_GROUPID_GROUPID_2 =
		"backgroundTask.groupId = ?";

	private FinderPath _finderPathWithPaginationFindByCompanyId;
	private FinderPath _finderPathWithoutPaginationFindByCompanyId;
	private FinderPath _finderPathCountByCompanyId;

	/**
	 * Returns all the background tasks where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching background tasks
	 */
	@Override
	public List<BackgroundTask> findByCompanyId(long companyId) {
		return findByCompanyId(
			companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the background tasks where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BackgroundTaskModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of background tasks
	 * @param end the upper bound of the range of background tasks (not inclusive)
	 * @return the range of matching background tasks
	 */
	@Override
	public List<BackgroundTask> findByCompanyId(
		long companyId, int start, int end) {

		return findByCompanyId(companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the background tasks where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BackgroundTaskModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of background tasks
	 * @param end the upper bound of the range of background tasks (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching background tasks
	 */
	@Override
	public List<BackgroundTask> findByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<BackgroundTask> orderByComparator) {

		return findByCompanyId(companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the background tasks where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BackgroundTaskModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of background tasks
	 * @param end the upper bound of the range of background tasks (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching background tasks
	 */
	@Override
	public List<BackgroundTask> findByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<BackgroundTask> orderByComparator,
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

		List<BackgroundTask> list = null;

		if (useFinderCache) {
			list = (List<BackgroundTask>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (BackgroundTask backgroundTask : list) {
					if (companyId != backgroundTask.getCompanyId()) {
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

			sb.append(_SQL_SELECT_BACKGROUNDTASK_WHERE);

			sb.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(BackgroundTaskModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(companyId);

				list = (List<BackgroundTask>)QueryUtil.list(
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
	 * Returns the first background task in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching background task
	 * @throws NoSuchBackgroundTaskException if a matching background task could not be found
	 */
	@Override
	public BackgroundTask findByCompanyId_First(
			long companyId, OrderByComparator<BackgroundTask> orderByComparator)
		throws NoSuchBackgroundTaskException {

		BackgroundTask backgroundTask = fetchByCompanyId_First(
			companyId, orderByComparator);

		if (backgroundTask != null) {
			return backgroundTask;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchBackgroundTaskException(sb.toString());
	}

	/**
	 * Returns the first background task in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching background task, or <code>null</code> if a matching background task could not be found
	 */
	@Override
	public BackgroundTask fetchByCompanyId_First(
		long companyId, OrderByComparator<BackgroundTask> orderByComparator) {

		List<BackgroundTask> list = findByCompanyId(
			companyId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last background task in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching background task
	 * @throws NoSuchBackgroundTaskException if a matching background task could not be found
	 */
	@Override
	public BackgroundTask findByCompanyId_Last(
			long companyId, OrderByComparator<BackgroundTask> orderByComparator)
		throws NoSuchBackgroundTaskException {

		BackgroundTask backgroundTask = fetchByCompanyId_Last(
			companyId, orderByComparator);

		if (backgroundTask != null) {
			return backgroundTask;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchBackgroundTaskException(sb.toString());
	}

	/**
	 * Returns the last background task in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching background task, or <code>null</code> if a matching background task could not be found
	 */
	@Override
	public BackgroundTask fetchByCompanyId_Last(
		long companyId, OrderByComparator<BackgroundTask> orderByComparator) {

		int count = countByCompanyId(companyId);

		if (count == 0) {
			return null;
		}

		List<BackgroundTask> list = findByCompanyId(
			companyId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the background tasks before and after the current background task in the ordered set where companyId = &#63;.
	 *
	 * @param backgroundTaskId the primary key of the current background task
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next background task
	 * @throws NoSuchBackgroundTaskException if a background task with the primary key could not be found
	 */
	@Override
	public BackgroundTask[] findByCompanyId_PrevAndNext(
			long backgroundTaskId, long companyId,
			OrderByComparator<BackgroundTask> orderByComparator)
		throws NoSuchBackgroundTaskException {

		BackgroundTask backgroundTask = findByPrimaryKey(backgroundTaskId);

		Session session = null;

		try {
			session = openSession();

			BackgroundTask[] array = new BackgroundTaskImpl[3];

			array[0] = getByCompanyId_PrevAndNext(
				session, backgroundTask, companyId, orderByComparator, true);

			array[1] = backgroundTask;

			array[2] = getByCompanyId_PrevAndNext(
				session, backgroundTask, companyId, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected BackgroundTask getByCompanyId_PrevAndNext(
		Session session, BackgroundTask backgroundTask, long companyId,
		OrderByComparator<BackgroundTask> orderByComparator, boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(3);
		}

		sb.append(_SQL_SELECT_BACKGROUNDTASK_WHERE);

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
			sb.append(BackgroundTaskModelImpl.ORDER_BY_JPQL);
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
						backgroundTask)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<BackgroundTask> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the background tasks where companyId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 */
	@Override
	public void removeByCompanyId(long companyId) {
		for (BackgroundTask backgroundTask :
				findByCompanyId(
					companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(backgroundTask);
		}
	}

	/**
	 * Returns the number of background tasks where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching background tasks
	 */
	@Override
	public int countByCompanyId(long companyId) {
		FinderPath finderPath = _finderPathCountByCompanyId;

		Object[] finderArgs = new Object[] {companyId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_BACKGROUNDTASK_WHERE);

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
		"backgroundTask.companyId = ?";

	private FinderPath _finderPathWithPaginationFindByCompleted;
	private FinderPath _finderPathWithoutPaginationFindByCompleted;
	private FinderPath _finderPathCountByCompleted;

	/**
	 * Returns all the background tasks where completed = &#63;.
	 *
	 * @param completed the completed
	 * @return the matching background tasks
	 */
	@Override
	public List<BackgroundTask> findByCompleted(boolean completed) {
		return findByCompleted(
			completed, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the background tasks where completed = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BackgroundTaskModelImpl</code>.
	 * </p>
	 *
	 * @param completed the completed
	 * @param start the lower bound of the range of background tasks
	 * @param end the upper bound of the range of background tasks (not inclusive)
	 * @return the range of matching background tasks
	 */
	@Override
	public List<BackgroundTask> findByCompleted(
		boolean completed, int start, int end) {

		return findByCompleted(completed, start, end, null);
	}

	/**
	 * Returns an ordered range of all the background tasks where completed = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BackgroundTaskModelImpl</code>.
	 * </p>
	 *
	 * @param completed the completed
	 * @param start the lower bound of the range of background tasks
	 * @param end the upper bound of the range of background tasks (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching background tasks
	 */
	@Override
	public List<BackgroundTask> findByCompleted(
		boolean completed, int start, int end,
		OrderByComparator<BackgroundTask> orderByComparator) {

		return findByCompleted(completed, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the background tasks where completed = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BackgroundTaskModelImpl</code>.
	 * </p>
	 *
	 * @param completed the completed
	 * @param start the lower bound of the range of background tasks
	 * @param end the upper bound of the range of background tasks (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching background tasks
	 */
	@Override
	public List<BackgroundTask> findByCompleted(
		boolean completed, int start, int end,
		OrderByComparator<BackgroundTask> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByCompleted;
				finderArgs = new Object[] {completed};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByCompleted;
			finderArgs = new Object[] {
				completed, start, end, orderByComparator
			};
		}

		List<BackgroundTask> list = null;

		if (useFinderCache) {
			list = (List<BackgroundTask>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (BackgroundTask backgroundTask : list) {
					if (completed != backgroundTask.isCompleted()) {
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

			sb.append(_SQL_SELECT_BACKGROUNDTASK_WHERE);

			sb.append(_FINDER_COLUMN_COMPLETED_COMPLETED_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(BackgroundTaskModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(completed);

				list = (List<BackgroundTask>)QueryUtil.list(
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
	 * Returns the first background task in the ordered set where completed = &#63;.
	 *
	 * @param completed the completed
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching background task
	 * @throws NoSuchBackgroundTaskException if a matching background task could not be found
	 */
	@Override
	public BackgroundTask findByCompleted_First(
			boolean completed,
			OrderByComparator<BackgroundTask> orderByComparator)
		throws NoSuchBackgroundTaskException {

		BackgroundTask backgroundTask = fetchByCompleted_First(
			completed, orderByComparator);

		if (backgroundTask != null) {
			return backgroundTask;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("completed=");
		sb.append(completed);

		sb.append("}");

		throw new NoSuchBackgroundTaskException(sb.toString());
	}

	/**
	 * Returns the first background task in the ordered set where completed = &#63;.
	 *
	 * @param completed the completed
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching background task, or <code>null</code> if a matching background task could not be found
	 */
	@Override
	public BackgroundTask fetchByCompleted_First(
		boolean completed,
		OrderByComparator<BackgroundTask> orderByComparator) {

		List<BackgroundTask> list = findByCompleted(
			completed, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last background task in the ordered set where completed = &#63;.
	 *
	 * @param completed the completed
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching background task
	 * @throws NoSuchBackgroundTaskException if a matching background task could not be found
	 */
	@Override
	public BackgroundTask findByCompleted_Last(
			boolean completed,
			OrderByComparator<BackgroundTask> orderByComparator)
		throws NoSuchBackgroundTaskException {

		BackgroundTask backgroundTask = fetchByCompleted_Last(
			completed, orderByComparator);

		if (backgroundTask != null) {
			return backgroundTask;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("completed=");
		sb.append(completed);

		sb.append("}");

		throw new NoSuchBackgroundTaskException(sb.toString());
	}

	/**
	 * Returns the last background task in the ordered set where completed = &#63;.
	 *
	 * @param completed the completed
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching background task, or <code>null</code> if a matching background task could not be found
	 */
	@Override
	public BackgroundTask fetchByCompleted_Last(
		boolean completed,
		OrderByComparator<BackgroundTask> orderByComparator) {

		int count = countByCompleted(completed);

		if (count == 0) {
			return null;
		}

		List<BackgroundTask> list = findByCompleted(
			completed, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the background tasks before and after the current background task in the ordered set where completed = &#63;.
	 *
	 * @param backgroundTaskId the primary key of the current background task
	 * @param completed the completed
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next background task
	 * @throws NoSuchBackgroundTaskException if a background task with the primary key could not be found
	 */
	@Override
	public BackgroundTask[] findByCompleted_PrevAndNext(
			long backgroundTaskId, boolean completed,
			OrderByComparator<BackgroundTask> orderByComparator)
		throws NoSuchBackgroundTaskException {

		BackgroundTask backgroundTask = findByPrimaryKey(backgroundTaskId);

		Session session = null;

		try {
			session = openSession();

			BackgroundTask[] array = new BackgroundTaskImpl[3];

			array[0] = getByCompleted_PrevAndNext(
				session, backgroundTask, completed, orderByComparator, true);

			array[1] = backgroundTask;

			array[2] = getByCompleted_PrevAndNext(
				session, backgroundTask, completed, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected BackgroundTask getByCompleted_PrevAndNext(
		Session session, BackgroundTask backgroundTask, boolean completed,
		OrderByComparator<BackgroundTask> orderByComparator, boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(3);
		}

		sb.append(_SQL_SELECT_BACKGROUNDTASK_WHERE);

		sb.append(_FINDER_COLUMN_COMPLETED_COMPLETED_2);

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
			sb.append(BackgroundTaskModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(completed);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						backgroundTask)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<BackgroundTask> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the background tasks where completed = &#63; from the database.
	 *
	 * @param completed the completed
	 */
	@Override
	public void removeByCompleted(boolean completed) {
		for (BackgroundTask backgroundTask :
				findByCompleted(
					completed, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(backgroundTask);
		}
	}

	/**
	 * Returns the number of background tasks where completed = &#63;.
	 *
	 * @param completed the completed
	 * @return the number of matching background tasks
	 */
	@Override
	public int countByCompleted(boolean completed) {
		FinderPath finderPath = _finderPathCountByCompleted;

		Object[] finderArgs = new Object[] {completed};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_BACKGROUNDTASK_WHERE);

			sb.append(_FINDER_COLUMN_COMPLETED_COMPLETED_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(completed);

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

	private static final String _FINDER_COLUMN_COMPLETED_COMPLETED_2 =
		"backgroundTask.completed = ?";

	private FinderPath _finderPathWithPaginationFindByStatus;
	private FinderPath _finderPathWithoutPaginationFindByStatus;
	private FinderPath _finderPathCountByStatus;

	/**
	 * Returns all the background tasks where status = &#63;.
	 *
	 * @param status the status
	 * @return the matching background tasks
	 */
	@Override
	public List<BackgroundTask> findByStatus(int status) {
		return findByStatus(status, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the background tasks where status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BackgroundTaskModelImpl</code>.
	 * </p>
	 *
	 * @param status the status
	 * @param start the lower bound of the range of background tasks
	 * @param end the upper bound of the range of background tasks (not inclusive)
	 * @return the range of matching background tasks
	 */
	@Override
	public List<BackgroundTask> findByStatus(int status, int start, int end) {
		return findByStatus(status, start, end, null);
	}

	/**
	 * Returns an ordered range of all the background tasks where status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BackgroundTaskModelImpl</code>.
	 * </p>
	 *
	 * @param status the status
	 * @param start the lower bound of the range of background tasks
	 * @param end the upper bound of the range of background tasks (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching background tasks
	 */
	@Override
	public List<BackgroundTask> findByStatus(
		int status, int start, int end,
		OrderByComparator<BackgroundTask> orderByComparator) {

		return findByStatus(status, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the background tasks where status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BackgroundTaskModelImpl</code>.
	 * </p>
	 *
	 * @param status the status
	 * @param start the lower bound of the range of background tasks
	 * @param end the upper bound of the range of background tasks (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching background tasks
	 */
	@Override
	public List<BackgroundTask> findByStatus(
		int status, int start, int end,
		OrderByComparator<BackgroundTask> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByStatus;
				finderArgs = new Object[] {status};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByStatus;
			finderArgs = new Object[] {status, start, end, orderByComparator};
		}

		List<BackgroundTask> list = null;

		if (useFinderCache) {
			list = (List<BackgroundTask>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (BackgroundTask backgroundTask : list) {
					if (status != backgroundTask.getStatus()) {
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

			sb.append(_SQL_SELECT_BACKGROUNDTASK_WHERE);

			sb.append(_FINDER_COLUMN_STATUS_STATUS_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(BackgroundTaskModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(status);

				list = (List<BackgroundTask>)QueryUtil.list(
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
	 * Returns the first background task in the ordered set where status = &#63;.
	 *
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching background task
	 * @throws NoSuchBackgroundTaskException if a matching background task could not be found
	 */
	@Override
	public BackgroundTask findByStatus_First(
			int status, OrderByComparator<BackgroundTask> orderByComparator)
		throws NoSuchBackgroundTaskException {

		BackgroundTask backgroundTask = fetchByStatus_First(
			status, orderByComparator);

		if (backgroundTask != null) {
			return backgroundTask;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("status=");
		sb.append(status);

		sb.append("}");

		throw new NoSuchBackgroundTaskException(sb.toString());
	}

	/**
	 * Returns the first background task in the ordered set where status = &#63;.
	 *
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching background task, or <code>null</code> if a matching background task could not be found
	 */
	@Override
	public BackgroundTask fetchByStatus_First(
		int status, OrderByComparator<BackgroundTask> orderByComparator) {

		List<BackgroundTask> list = findByStatus(
			status, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last background task in the ordered set where status = &#63;.
	 *
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching background task
	 * @throws NoSuchBackgroundTaskException if a matching background task could not be found
	 */
	@Override
	public BackgroundTask findByStatus_Last(
			int status, OrderByComparator<BackgroundTask> orderByComparator)
		throws NoSuchBackgroundTaskException {

		BackgroundTask backgroundTask = fetchByStatus_Last(
			status, orderByComparator);

		if (backgroundTask != null) {
			return backgroundTask;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("status=");
		sb.append(status);

		sb.append("}");

		throw new NoSuchBackgroundTaskException(sb.toString());
	}

	/**
	 * Returns the last background task in the ordered set where status = &#63;.
	 *
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching background task, or <code>null</code> if a matching background task could not be found
	 */
	@Override
	public BackgroundTask fetchByStatus_Last(
		int status, OrderByComparator<BackgroundTask> orderByComparator) {

		int count = countByStatus(status);

		if (count == 0) {
			return null;
		}

		List<BackgroundTask> list = findByStatus(
			status, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the background tasks before and after the current background task in the ordered set where status = &#63;.
	 *
	 * @param backgroundTaskId the primary key of the current background task
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next background task
	 * @throws NoSuchBackgroundTaskException if a background task with the primary key could not be found
	 */
	@Override
	public BackgroundTask[] findByStatus_PrevAndNext(
			long backgroundTaskId, int status,
			OrderByComparator<BackgroundTask> orderByComparator)
		throws NoSuchBackgroundTaskException {

		BackgroundTask backgroundTask = findByPrimaryKey(backgroundTaskId);

		Session session = null;

		try {
			session = openSession();

			BackgroundTask[] array = new BackgroundTaskImpl[3];

			array[0] = getByStatus_PrevAndNext(
				session, backgroundTask, status, orderByComparator, true);

			array[1] = backgroundTask;

			array[2] = getByStatus_PrevAndNext(
				session, backgroundTask, status, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected BackgroundTask getByStatus_PrevAndNext(
		Session session, BackgroundTask backgroundTask, int status,
		OrderByComparator<BackgroundTask> orderByComparator, boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(3);
		}

		sb.append(_SQL_SELECT_BACKGROUNDTASK_WHERE);

		sb.append(_FINDER_COLUMN_STATUS_STATUS_2);

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
			sb.append(BackgroundTaskModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(status);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						backgroundTask)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<BackgroundTask> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the background tasks where status = &#63; from the database.
	 *
	 * @param status the status
	 */
	@Override
	public void removeByStatus(int status) {
		for (BackgroundTask backgroundTask :
				findByStatus(
					status, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(backgroundTask);
		}
	}

	/**
	 * Returns the number of background tasks where status = &#63;.
	 *
	 * @param status the status
	 * @return the number of matching background tasks
	 */
	@Override
	public int countByStatus(int status) {
		FinderPath finderPath = _finderPathCountByStatus;

		Object[] finderArgs = new Object[] {status};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_BACKGROUNDTASK_WHERE);

			sb.append(_FINDER_COLUMN_STATUS_STATUS_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

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

	private static final String _FINDER_COLUMN_STATUS_STATUS_2 =
		"backgroundTask.status = ?";

	private FinderPath _finderPathWithPaginationFindByG_T;
	private FinderPath _finderPathWithoutPaginationFindByG_T;
	private FinderPath _finderPathCountByG_T;
	private FinderPath _finderPathWithPaginationCountByG_T;

	/**
	 * Returns all the background tasks where groupId = &#63; and taskExecutorClassName = &#63;.
	 *
	 * @param groupId the group ID
	 * @param taskExecutorClassName the task executor class name
	 * @return the matching background tasks
	 */
	@Override
	public List<BackgroundTask> findByG_T(
		long groupId, String taskExecutorClassName) {

		return findByG_T(
			groupId, taskExecutorClassName, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the background tasks where groupId = &#63; and taskExecutorClassName = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BackgroundTaskModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param taskExecutorClassName the task executor class name
	 * @param start the lower bound of the range of background tasks
	 * @param end the upper bound of the range of background tasks (not inclusive)
	 * @return the range of matching background tasks
	 */
	@Override
	public List<BackgroundTask> findByG_T(
		long groupId, String taskExecutorClassName, int start, int end) {

		return findByG_T(groupId, taskExecutorClassName, start, end, null);
	}

	/**
	 * Returns an ordered range of all the background tasks where groupId = &#63; and taskExecutorClassName = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BackgroundTaskModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param taskExecutorClassName the task executor class name
	 * @param start the lower bound of the range of background tasks
	 * @param end the upper bound of the range of background tasks (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching background tasks
	 */
	@Override
	public List<BackgroundTask> findByG_T(
		long groupId, String taskExecutorClassName, int start, int end,
		OrderByComparator<BackgroundTask> orderByComparator) {

		return findByG_T(
			groupId, taskExecutorClassName, start, end, orderByComparator,
			true);
	}

	/**
	 * Returns an ordered range of all the background tasks where groupId = &#63; and taskExecutorClassName = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BackgroundTaskModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param taskExecutorClassName the task executor class name
	 * @param start the lower bound of the range of background tasks
	 * @param end the upper bound of the range of background tasks (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching background tasks
	 */
	@Override
	public List<BackgroundTask> findByG_T(
		long groupId, String taskExecutorClassName, int start, int end,
		OrderByComparator<BackgroundTask> orderByComparator,
		boolean useFinderCache) {

		taskExecutorClassName = Objects.toString(taskExecutorClassName, "");

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByG_T;
				finderArgs = new Object[] {groupId, taskExecutorClassName};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByG_T;
			finderArgs = new Object[] {
				groupId, taskExecutorClassName, start, end, orderByComparator
			};
		}

		List<BackgroundTask> list = null;

		if (useFinderCache) {
			list = (List<BackgroundTask>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (BackgroundTask backgroundTask : list) {
					if ((groupId != backgroundTask.getGroupId()) ||
						!taskExecutorClassName.equals(
							backgroundTask.getTaskExecutorClassName())) {

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

			sb.append(_SQL_SELECT_BACKGROUNDTASK_WHERE);

			sb.append(_FINDER_COLUMN_G_T_GROUPID_2);

			boolean bindTaskExecutorClassName = false;

			if (taskExecutorClassName.isEmpty()) {
				sb.append(_FINDER_COLUMN_G_T_TASKEXECUTORCLASSNAME_3);
			}
			else {
				bindTaskExecutorClassName = true;

				sb.append(_FINDER_COLUMN_G_T_TASKEXECUTORCLASSNAME_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(BackgroundTaskModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				if (bindTaskExecutorClassName) {
					queryPos.add(taskExecutorClassName);
				}

				list = (List<BackgroundTask>)QueryUtil.list(
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
	 * Returns the first background task in the ordered set where groupId = &#63; and taskExecutorClassName = &#63;.
	 *
	 * @param groupId the group ID
	 * @param taskExecutorClassName the task executor class name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching background task
	 * @throws NoSuchBackgroundTaskException if a matching background task could not be found
	 */
	@Override
	public BackgroundTask findByG_T_First(
			long groupId, String taskExecutorClassName,
			OrderByComparator<BackgroundTask> orderByComparator)
		throws NoSuchBackgroundTaskException {

		BackgroundTask backgroundTask = fetchByG_T_First(
			groupId, taskExecutorClassName, orderByComparator);

		if (backgroundTask != null) {
			return backgroundTask;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", taskExecutorClassName=");
		sb.append(taskExecutorClassName);

		sb.append("}");

		throw new NoSuchBackgroundTaskException(sb.toString());
	}

	/**
	 * Returns the first background task in the ordered set where groupId = &#63; and taskExecutorClassName = &#63;.
	 *
	 * @param groupId the group ID
	 * @param taskExecutorClassName the task executor class name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching background task, or <code>null</code> if a matching background task could not be found
	 */
	@Override
	public BackgroundTask fetchByG_T_First(
		long groupId, String taskExecutorClassName,
		OrderByComparator<BackgroundTask> orderByComparator) {

		List<BackgroundTask> list = findByG_T(
			groupId, taskExecutorClassName, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last background task in the ordered set where groupId = &#63; and taskExecutorClassName = &#63;.
	 *
	 * @param groupId the group ID
	 * @param taskExecutorClassName the task executor class name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching background task
	 * @throws NoSuchBackgroundTaskException if a matching background task could not be found
	 */
	@Override
	public BackgroundTask findByG_T_Last(
			long groupId, String taskExecutorClassName,
			OrderByComparator<BackgroundTask> orderByComparator)
		throws NoSuchBackgroundTaskException {

		BackgroundTask backgroundTask = fetchByG_T_Last(
			groupId, taskExecutorClassName, orderByComparator);

		if (backgroundTask != null) {
			return backgroundTask;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", taskExecutorClassName=");
		sb.append(taskExecutorClassName);

		sb.append("}");

		throw new NoSuchBackgroundTaskException(sb.toString());
	}

	/**
	 * Returns the last background task in the ordered set where groupId = &#63; and taskExecutorClassName = &#63;.
	 *
	 * @param groupId the group ID
	 * @param taskExecutorClassName the task executor class name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching background task, or <code>null</code> if a matching background task could not be found
	 */
	@Override
	public BackgroundTask fetchByG_T_Last(
		long groupId, String taskExecutorClassName,
		OrderByComparator<BackgroundTask> orderByComparator) {

		int count = countByG_T(groupId, taskExecutorClassName);

		if (count == 0) {
			return null;
		}

		List<BackgroundTask> list = findByG_T(
			groupId, taskExecutorClassName, count - 1, count,
			orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the background tasks before and after the current background task in the ordered set where groupId = &#63; and taskExecutorClassName = &#63;.
	 *
	 * @param backgroundTaskId the primary key of the current background task
	 * @param groupId the group ID
	 * @param taskExecutorClassName the task executor class name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next background task
	 * @throws NoSuchBackgroundTaskException if a background task with the primary key could not be found
	 */
	@Override
	public BackgroundTask[] findByG_T_PrevAndNext(
			long backgroundTaskId, long groupId, String taskExecutorClassName,
			OrderByComparator<BackgroundTask> orderByComparator)
		throws NoSuchBackgroundTaskException {

		taskExecutorClassName = Objects.toString(taskExecutorClassName, "");

		BackgroundTask backgroundTask = findByPrimaryKey(backgroundTaskId);

		Session session = null;

		try {
			session = openSession();

			BackgroundTask[] array = new BackgroundTaskImpl[3];

			array[0] = getByG_T_PrevAndNext(
				session, backgroundTask, groupId, taskExecutorClassName,
				orderByComparator, true);

			array[1] = backgroundTask;

			array[2] = getByG_T_PrevAndNext(
				session, backgroundTask, groupId, taskExecutorClassName,
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

	protected BackgroundTask getByG_T_PrevAndNext(
		Session session, BackgroundTask backgroundTask, long groupId,
		String taskExecutorClassName,
		OrderByComparator<BackgroundTask> orderByComparator, boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				5 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(4);
		}

		sb.append(_SQL_SELECT_BACKGROUNDTASK_WHERE);

		sb.append(_FINDER_COLUMN_G_T_GROUPID_2);

		boolean bindTaskExecutorClassName = false;

		if (taskExecutorClassName.isEmpty()) {
			sb.append(_FINDER_COLUMN_G_T_TASKEXECUTORCLASSNAME_3);
		}
		else {
			bindTaskExecutorClassName = true;

			sb.append(_FINDER_COLUMN_G_T_TASKEXECUTORCLASSNAME_2);
		}

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
			sb.append(BackgroundTaskModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(groupId);

		if (bindTaskExecutorClassName) {
			queryPos.add(taskExecutorClassName);
		}

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						backgroundTask)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<BackgroundTask> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the background tasks where groupId = any &#63; and taskExecutorClassName = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BackgroundTaskModelImpl</code>.
	 * </p>
	 *
	 * @param groupIds the group IDs
	 * @param taskExecutorClassNames the task executor class names
	 * @return the matching background tasks
	 */
	@Override
	public List<BackgroundTask> findByG_T(
		long[] groupIds, String[] taskExecutorClassNames) {

		return findByG_T(
			groupIds, taskExecutorClassNames, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the background tasks where groupId = any &#63; and taskExecutorClassName = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BackgroundTaskModelImpl</code>.
	 * </p>
	 *
	 * @param groupIds the group IDs
	 * @param taskExecutorClassNames the task executor class names
	 * @param start the lower bound of the range of background tasks
	 * @param end the upper bound of the range of background tasks (not inclusive)
	 * @return the range of matching background tasks
	 */
	@Override
	public List<BackgroundTask> findByG_T(
		long[] groupIds, String[] taskExecutorClassNames, int start, int end) {

		return findByG_T(groupIds, taskExecutorClassNames, start, end, null);
	}

	/**
	 * Returns an ordered range of all the background tasks where groupId = any &#63; and taskExecutorClassName = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BackgroundTaskModelImpl</code>.
	 * </p>
	 *
	 * @param groupIds the group IDs
	 * @param taskExecutorClassNames the task executor class names
	 * @param start the lower bound of the range of background tasks
	 * @param end the upper bound of the range of background tasks (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching background tasks
	 */
	@Override
	public List<BackgroundTask> findByG_T(
		long[] groupIds, String[] taskExecutorClassNames, int start, int end,
		OrderByComparator<BackgroundTask> orderByComparator) {

		return findByG_T(
			groupIds, taskExecutorClassNames, start, end, orderByComparator,
			true);
	}

	/**
	 * Returns an ordered range of all the background tasks where groupId = &#63; and taskExecutorClassName = &#63;, optionally using the finder cache.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BackgroundTaskModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param taskExecutorClassName the task executor class name
	 * @param start the lower bound of the range of background tasks
	 * @param end the upper bound of the range of background tasks (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching background tasks
	 */
	@Override
	public List<BackgroundTask> findByG_T(
		long[] groupIds, String[] taskExecutorClassNames, int start, int end,
		OrderByComparator<BackgroundTask> orderByComparator,
		boolean useFinderCache) {

		if (groupIds == null) {
			groupIds = new long[0];
		}
		else if (groupIds.length > 1) {
			groupIds = ArrayUtil.sortedUnique(groupIds);
		}

		if (taskExecutorClassNames == null) {
			taskExecutorClassNames = new String[0];
		}
		else if (taskExecutorClassNames.length > 1) {
			for (int i = 0; i < taskExecutorClassNames.length; i++) {
				taskExecutorClassNames[i] = Objects.toString(
					taskExecutorClassNames[i], "");
			}

			taskExecutorClassNames = ArrayUtil.sortedUnique(
				taskExecutorClassNames);
		}

		if ((groupIds.length == 1) && (taskExecutorClassNames.length == 1)) {
			return findByG_T(
				groupIds[0], taskExecutorClassNames[0], start, end,
				orderByComparator);
		}

		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderArgs = new Object[] {
					StringUtil.merge(groupIds),
					StringUtil.merge(taskExecutorClassNames)
				};
			}
		}
		else if (useFinderCache) {
			finderArgs = new Object[] {
				StringUtil.merge(groupIds),
				StringUtil.merge(taskExecutorClassNames), start, end,
				orderByComparator
			};
		}

		List<BackgroundTask> list = null;

		if (useFinderCache) {
			list = (List<BackgroundTask>)finderCache.getResult(
				_finderPathWithPaginationFindByG_T, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (BackgroundTask backgroundTask : list) {
					if (!ArrayUtil.contains(
							groupIds, backgroundTask.getGroupId()) ||
						!ArrayUtil.contains(
							taskExecutorClassNames,
							backgroundTask.getTaskExecutorClassName())) {

						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler sb = new StringBundler();

			sb.append(_SQL_SELECT_BACKGROUNDTASK_WHERE);

			if (groupIds.length > 0) {
				sb.append("(");

				sb.append(_FINDER_COLUMN_G_T_GROUPID_7);

				sb.append(StringUtil.merge(groupIds));

				sb.append(")");

				sb.append(")");

				sb.append(WHERE_AND);
			}

			if (taskExecutorClassNames.length > 0) {
				sb.append("(");

				for (int i = 0; i < taskExecutorClassNames.length; i++) {
					String taskExecutorClassName = taskExecutorClassNames[i];

					if (taskExecutorClassName.isEmpty()) {
						sb.append(_FINDER_COLUMN_G_T_TASKEXECUTORCLASSNAME_3);
					}
					else {
						sb.append(_FINDER_COLUMN_G_T_TASKEXECUTORCLASSNAME_2);
					}

					if ((i + 1) < taskExecutorClassNames.length) {
						sb.append(WHERE_OR);
					}
				}

				sb.append(")");
			}

			sb.setStringAt(
				removeConjunction(sb.stringAt(sb.index() - 1)), sb.index() - 1);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(BackgroundTaskModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				for (String taskExecutorClassName : taskExecutorClassNames) {
					if ((taskExecutorClassName != null) &&
						!taskExecutorClassName.isEmpty()) {

						queryPos.add(taskExecutorClassName);
					}
				}

				list = (List<BackgroundTask>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					finderCache.putResult(
						_finderPathWithPaginationFindByG_T, finderArgs, list);
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
	 * Removes all the background tasks where groupId = &#63; and taskExecutorClassName = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param taskExecutorClassName the task executor class name
	 */
	@Override
	public void removeByG_T(long groupId, String taskExecutorClassName) {
		for (BackgroundTask backgroundTask :
				findByG_T(
					groupId, taskExecutorClassName, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			remove(backgroundTask);
		}
	}

	/**
	 * Returns the number of background tasks where groupId = &#63; and taskExecutorClassName = &#63;.
	 *
	 * @param groupId the group ID
	 * @param taskExecutorClassName the task executor class name
	 * @return the number of matching background tasks
	 */
	@Override
	public int countByG_T(long groupId, String taskExecutorClassName) {
		taskExecutorClassName = Objects.toString(taskExecutorClassName, "");

		FinderPath finderPath = _finderPathCountByG_T;

		Object[] finderArgs = new Object[] {groupId, taskExecutorClassName};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_BACKGROUNDTASK_WHERE);

			sb.append(_FINDER_COLUMN_G_T_GROUPID_2);

			boolean bindTaskExecutorClassName = false;

			if (taskExecutorClassName.isEmpty()) {
				sb.append(_FINDER_COLUMN_G_T_TASKEXECUTORCLASSNAME_3);
			}
			else {
				bindTaskExecutorClassName = true;

				sb.append(_FINDER_COLUMN_G_T_TASKEXECUTORCLASSNAME_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				if (bindTaskExecutorClassName) {
					queryPos.add(taskExecutorClassName);
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

	/**
	 * Returns the number of background tasks where groupId = any &#63; and taskExecutorClassName = any &#63;.
	 *
	 * @param groupIds the group IDs
	 * @param taskExecutorClassNames the task executor class names
	 * @return the number of matching background tasks
	 */
	@Override
	public int countByG_T(long[] groupIds, String[] taskExecutorClassNames) {
		if (groupIds == null) {
			groupIds = new long[0];
		}
		else if (groupIds.length > 1) {
			groupIds = ArrayUtil.sortedUnique(groupIds);
		}

		if (taskExecutorClassNames == null) {
			taskExecutorClassNames = new String[0];
		}
		else if (taskExecutorClassNames.length > 1) {
			for (int i = 0; i < taskExecutorClassNames.length; i++) {
				taskExecutorClassNames[i] = Objects.toString(
					taskExecutorClassNames[i], "");
			}

			taskExecutorClassNames = ArrayUtil.sortedUnique(
				taskExecutorClassNames);
		}

		Object[] finderArgs = new Object[] {
			StringUtil.merge(groupIds), StringUtil.merge(taskExecutorClassNames)
		};

		Long count = (Long)finderCache.getResult(
			_finderPathWithPaginationCountByG_T, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler();

			sb.append(_SQL_COUNT_BACKGROUNDTASK_WHERE);

			if (groupIds.length > 0) {
				sb.append("(");

				sb.append(_FINDER_COLUMN_G_T_GROUPID_7);

				sb.append(StringUtil.merge(groupIds));

				sb.append(")");

				sb.append(")");

				sb.append(WHERE_AND);
			}

			if (taskExecutorClassNames.length > 0) {
				sb.append("(");

				for (int i = 0; i < taskExecutorClassNames.length; i++) {
					String taskExecutorClassName = taskExecutorClassNames[i];

					if (taskExecutorClassName.isEmpty()) {
						sb.append(_FINDER_COLUMN_G_T_TASKEXECUTORCLASSNAME_3);
					}
					else {
						sb.append(_FINDER_COLUMN_G_T_TASKEXECUTORCLASSNAME_2);
					}

					if ((i + 1) < taskExecutorClassNames.length) {
						sb.append(WHERE_OR);
					}
				}

				sb.append(")");
			}

			sb.setStringAt(
				removeConjunction(sb.stringAt(sb.index() - 1)), sb.index() - 1);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				for (String taskExecutorClassName : taskExecutorClassNames) {
					if ((taskExecutorClassName != null) &&
						!taskExecutorClassName.isEmpty()) {

						queryPos.add(taskExecutorClassName);
					}
				}

				count = (Long)query.uniqueResult();

				finderCache.putResult(
					_finderPathWithPaginationCountByG_T, finderArgs, count);
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

	private static final String _FINDER_COLUMN_G_T_GROUPID_2 =
		"backgroundTask.groupId = ? AND ";

	private static final String _FINDER_COLUMN_G_T_GROUPID_7 =
		"backgroundTask.groupId IN (";

	private static final String _FINDER_COLUMN_G_T_TASKEXECUTORCLASSNAME_2 =
		"backgroundTask.taskExecutorClassName = ?";

	private static final String _FINDER_COLUMN_G_T_TASKEXECUTORCLASSNAME_3 =
		"(backgroundTask.taskExecutorClassName IS NULL OR backgroundTask.taskExecutorClassName = '')";

	private FinderPath _finderPathWithPaginationFindByG_S;
	private FinderPath _finderPathWithoutPaginationFindByG_S;
	private FinderPath _finderPathCountByG_S;

	/**
	 * Returns all the background tasks where groupId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @return the matching background tasks
	 */
	@Override
	public List<BackgroundTask> findByG_S(long groupId, int status) {
		return findByG_S(
			groupId, status, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the background tasks where groupId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BackgroundTaskModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @param start the lower bound of the range of background tasks
	 * @param end the upper bound of the range of background tasks (not inclusive)
	 * @return the range of matching background tasks
	 */
	@Override
	public List<BackgroundTask> findByG_S(
		long groupId, int status, int start, int end) {

		return findByG_S(groupId, status, start, end, null);
	}

	/**
	 * Returns an ordered range of all the background tasks where groupId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BackgroundTaskModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @param start the lower bound of the range of background tasks
	 * @param end the upper bound of the range of background tasks (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching background tasks
	 */
	@Override
	public List<BackgroundTask> findByG_S(
		long groupId, int status, int start, int end,
		OrderByComparator<BackgroundTask> orderByComparator) {

		return findByG_S(groupId, status, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the background tasks where groupId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BackgroundTaskModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @param start the lower bound of the range of background tasks
	 * @param end the upper bound of the range of background tasks (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching background tasks
	 */
	@Override
	public List<BackgroundTask> findByG_S(
		long groupId, int status, int start, int end,
		OrderByComparator<BackgroundTask> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByG_S;
				finderArgs = new Object[] {groupId, status};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByG_S;
			finderArgs = new Object[] {
				groupId, status, start, end, orderByComparator
			};
		}

		List<BackgroundTask> list = null;

		if (useFinderCache) {
			list = (List<BackgroundTask>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (BackgroundTask backgroundTask : list) {
					if ((groupId != backgroundTask.getGroupId()) ||
						(status != backgroundTask.getStatus())) {

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

			sb.append(_SQL_SELECT_BACKGROUNDTASK_WHERE);

			sb.append(_FINDER_COLUMN_G_S_GROUPID_2);

			sb.append(_FINDER_COLUMN_G_S_STATUS_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(BackgroundTaskModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				queryPos.add(status);

				list = (List<BackgroundTask>)QueryUtil.list(
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
	 * Returns the first background task in the ordered set where groupId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching background task
	 * @throws NoSuchBackgroundTaskException if a matching background task could not be found
	 */
	@Override
	public BackgroundTask findByG_S_First(
			long groupId, int status,
			OrderByComparator<BackgroundTask> orderByComparator)
		throws NoSuchBackgroundTaskException {

		BackgroundTask backgroundTask = fetchByG_S_First(
			groupId, status, orderByComparator);

		if (backgroundTask != null) {
			return backgroundTask;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", status=");
		sb.append(status);

		sb.append("}");

		throw new NoSuchBackgroundTaskException(sb.toString());
	}

	/**
	 * Returns the first background task in the ordered set where groupId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching background task, or <code>null</code> if a matching background task could not be found
	 */
	@Override
	public BackgroundTask fetchByG_S_First(
		long groupId, int status,
		OrderByComparator<BackgroundTask> orderByComparator) {

		List<BackgroundTask> list = findByG_S(
			groupId, status, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last background task in the ordered set where groupId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching background task
	 * @throws NoSuchBackgroundTaskException if a matching background task could not be found
	 */
	@Override
	public BackgroundTask findByG_S_Last(
			long groupId, int status,
			OrderByComparator<BackgroundTask> orderByComparator)
		throws NoSuchBackgroundTaskException {

		BackgroundTask backgroundTask = fetchByG_S_Last(
			groupId, status, orderByComparator);

		if (backgroundTask != null) {
			return backgroundTask;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", status=");
		sb.append(status);

		sb.append("}");

		throw new NoSuchBackgroundTaskException(sb.toString());
	}

	/**
	 * Returns the last background task in the ordered set where groupId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching background task, or <code>null</code> if a matching background task could not be found
	 */
	@Override
	public BackgroundTask fetchByG_S_Last(
		long groupId, int status,
		OrderByComparator<BackgroundTask> orderByComparator) {

		int count = countByG_S(groupId, status);

		if (count == 0) {
			return null;
		}

		List<BackgroundTask> list = findByG_S(
			groupId, status, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the background tasks before and after the current background task in the ordered set where groupId = &#63; and status = &#63;.
	 *
	 * @param backgroundTaskId the primary key of the current background task
	 * @param groupId the group ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next background task
	 * @throws NoSuchBackgroundTaskException if a background task with the primary key could not be found
	 */
	@Override
	public BackgroundTask[] findByG_S_PrevAndNext(
			long backgroundTaskId, long groupId, int status,
			OrderByComparator<BackgroundTask> orderByComparator)
		throws NoSuchBackgroundTaskException {

		BackgroundTask backgroundTask = findByPrimaryKey(backgroundTaskId);

		Session session = null;

		try {
			session = openSession();

			BackgroundTask[] array = new BackgroundTaskImpl[3];

			array[0] = getByG_S_PrevAndNext(
				session, backgroundTask, groupId, status, orderByComparator,
				true);

			array[1] = backgroundTask;

			array[2] = getByG_S_PrevAndNext(
				session, backgroundTask, groupId, status, orderByComparator,
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

	protected BackgroundTask getByG_S_PrevAndNext(
		Session session, BackgroundTask backgroundTask, long groupId,
		int status, OrderByComparator<BackgroundTask> orderByComparator,
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

		sb.append(_SQL_SELECT_BACKGROUNDTASK_WHERE);

		sb.append(_FINDER_COLUMN_G_S_GROUPID_2);

		sb.append(_FINDER_COLUMN_G_S_STATUS_2);

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
			sb.append(BackgroundTaskModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(groupId);

		queryPos.add(status);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						backgroundTask)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<BackgroundTask> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the background tasks where groupId = &#63; and status = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param status the status
	 */
	@Override
	public void removeByG_S(long groupId, int status) {
		for (BackgroundTask backgroundTask :
				findByG_S(
					groupId, status, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(backgroundTask);
		}
	}

	/**
	 * Returns the number of background tasks where groupId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @return the number of matching background tasks
	 */
	@Override
	public int countByG_S(long groupId, int status) {
		FinderPath finderPath = _finderPathCountByG_S;

		Object[] finderArgs = new Object[] {groupId, status};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_BACKGROUNDTASK_WHERE);

			sb.append(_FINDER_COLUMN_G_S_GROUPID_2);

			sb.append(_FINDER_COLUMN_G_S_STATUS_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

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

	private static final String _FINDER_COLUMN_G_S_GROUPID_2 =
		"backgroundTask.groupId = ? AND ";

	private static final String _FINDER_COLUMN_G_S_STATUS_2 =
		"backgroundTask.status = ?";

	private FinderPath _finderPathWithPaginationFindByT_S;
	private FinderPath _finderPathWithoutPaginationFindByT_S;
	private FinderPath _finderPathCountByT_S;
	private FinderPath _finderPathWithPaginationCountByT_S;

	/**
	 * Returns all the background tasks where taskExecutorClassName = &#63; and status = &#63;.
	 *
	 * @param taskExecutorClassName the task executor class name
	 * @param status the status
	 * @return the matching background tasks
	 */
	@Override
	public List<BackgroundTask> findByT_S(
		String taskExecutorClassName, int status) {

		return findByT_S(
			taskExecutorClassName, status, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the background tasks where taskExecutorClassName = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BackgroundTaskModelImpl</code>.
	 * </p>
	 *
	 * @param taskExecutorClassName the task executor class name
	 * @param status the status
	 * @param start the lower bound of the range of background tasks
	 * @param end the upper bound of the range of background tasks (not inclusive)
	 * @return the range of matching background tasks
	 */
	@Override
	public List<BackgroundTask> findByT_S(
		String taskExecutorClassName, int status, int start, int end) {

		return findByT_S(taskExecutorClassName, status, start, end, null);
	}

	/**
	 * Returns an ordered range of all the background tasks where taskExecutorClassName = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BackgroundTaskModelImpl</code>.
	 * </p>
	 *
	 * @param taskExecutorClassName the task executor class name
	 * @param status the status
	 * @param start the lower bound of the range of background tasks
	 * @param end the upper bound of the range of background tasks (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching background tasks
	 */
	@Override
	public List<BackgroundTask> findByT_S(
		String taskExecutorClassName, int status, int start, int end,
		OrderByComparator<BackgroundTask> orderByComparator) {

		return findByT_S(
			taskExecutorClassName, status, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the background tasks where taskExecutorClassName = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BackgroundTaskModelImpl</code>.
	 * </p>
	 *
	 * @param taskExecutorClassName the task executor class name
	 * @param status the status
	 * @param start the lower bound of the range of background tasks
	 * @param end the upper bound of the range of background tasks (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching background tasks
	 */
	@Override
	public List<BackgroundTask> findByT_S(
		String taskExecutorClassName, int status, int start, int end,
		OrderByComparator<BackgroundTask> orderByComparator,
		boolean useFinderCache) {

		taskExecutorClassName = Objects.toString(taskExecutorClassName, "");

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByT_S;
				finderArgs = new Object[] {taskExecutorClassName, status};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByT_S;
			finderArgs = new Object[] {
				taskExecutorClassName, status, start, end, orderByComparator
			};
		}

		List<BackgroundTask> list = null;

		if (useFinderCache) {
			list = (List<BackgroundTask>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (BackgroundTask backgroundTask : list) {
					if (!taskExecutorClassName.equals(
							backgroundTask.getTaskExecutorClassName()) ||
						(status != backgroundTask.getStatus())) {

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

			sb.append(_SQL_SELECT_BACKGROUNDTASK_WHERE);

			boolean bindTaskExecutorClassName = false;

			if (taskExecutorClassName.isEmpty()) {
				sb.append(_FINDER_COLUMN_T_S_TASKEXECUTORCLASSNAME_3);
			}
			else {
				bindTaskExecutorClassName = true;

				sb.append(_FINDER_COLUMN_T_S_TASKEXECUTORCLASSNAME_2);
			}

			sb.append(_FINDER_COLUMN_T_S_STATUS_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(BackgroundTaskModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindTaskExecutorClassName) {
					queryPos.add(taskExecutorClassName);
				}

				queryPos.add(status);

				list = (List<BackgroundTask>)QueryUtil.list(
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
	 * Returns the first background task in the ordered set where taskExecutorClassName = &#63; and status = &#63;.
	 *
	 * @param taskExecutorClassName the task executor class name
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching background task
	 * @throws NoSuchBackgroundTaskException if a matching background task could not be found
	 */
	@Override
	public BackgroundTask findByT_S_First(
			String taskExecutorClassName, int status,
			OrderByComparator<BackgroundTask> orderByComparator)
		throws NoSuchBackgroundTaskException {

		BackgroundTask backgroundTask = fetchByT_S_First(
			taskExecutorClassName, status, orderByComparator);

		if (backgroundTask != null) {
			return backgroundTask;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("taskExecutorClassName=");
		sb.append(taskExecutorClassName);

		sb.append(", status=");
		sb.append(status);

		sb.append("}");

		throw new NoSuchBackgroundTaskException(sb.toString());
	}

	/**
	 * Returns the first background task in the ordered set where taskExecutorClassName = &#63; and status = &#63;.
	 *
	 * @param taskExecutorClassName the task executor class name
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching background task, or <code>null</code> if a matching background task could not be found
	 */
	@Override
	public BackgroundTask fetchByT_S_First(
		String taskExecutorClassName, int status,
		OrderByComparator<BackgroundTask> orderByComparator) {

		List<BackgroundTask> list = findByT_S(
			taskExecutorClassName, status, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last background task in the ordered set where taskExecutorClassName = &#63; and status = &#63;.
	 *
	 * @param taskExecutorClassName the task executor class name
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching background task
	 * @throws NoSuchBackgroundTaskException if a matching background task could not be found
	 */
	@Override
	public BackgroundTask findByT_S_Last(
			String taskExecutorClassName, int status,
			OrderByComparator<BackgroundTask> orderByComparator)
		throws NoSuchBackgroundTaskException {

		BackgroundTask backgroundTask = fetchByT_S_Last(
			taskExecutorClassName, status, orderByComparator);

		if (backgroundTask != null) {
			return backgroundTask;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("taskExecutorClassName=");
		sb.append(taskExecutorClassName);

		sb.append(", status=");
		sb.append(status);

		sb.append("}");

		throw new NoSuchBackgroundTaskException(sb.toString());
	}

	/**
	 * Returns the last background task in the ordered set where taskExecutorClassName = &#63; and status = &#63;.
	 *
	 * @param taskExecutorClassName the task executor class name
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching background task, or <code>null</code> if a matching background task could not be found
	 */
	@Override
	public BackgroundTask fetchByT_S_Last(
		String taskExecutorClassName, int status,
		OrderByComparator<BackgroundTask> orderByComparator) {

		int count = countByT_S(taskExecutorClassName, status);

		if (count == 0) {
			return null;
		}

		List<BackgroundTask> list = findByT_S(
			taskExecutorClassName, status, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the background tasks before and after the current background task in the ordered set where taskExecutorClassName = &#63; and status = &#63;.
	 *
	 * @param backgroundTaskId the primary key of the current background task
	 * @param taskExecutorClassName the task executor class name
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next background task
	 * @throws NoSuchBackgroundTaskException if a background task with the primary key could not be found
	 */
	@Override
	public BackgroundTask[] findByT_S_PrevAndNext(
			long backgroundTaskId, String taskExecutorClassName, int status,
			OrderByComparator<BackgroundTask> orderByComparator)
		throws NoSuchBackgroundTaskException {

		taskExecutorClassName = Objects.toString(taskExecutorClassName, "");

		BackgroundTask backgroundTask = findByPrimaryKey(backgroundTaskId);

		Session session = null;

		try {
			session = openSession();

			BackgroundTask[] array = new BackgroundTaskImpl[3];

			array[0] = getByT_S_PrevAndNext(
				session, backgroundTask, taskExecutorClassName, status,
				orderByComparator, true);

			array[1] = backgroundTask;

			array[2] = getByT_S_PrevAndNext(
				session, backgroundTask, taskExecutorClassName, status,
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

	protected BackgroundTask getByT_S_PrevAndNext(
		Session session, BackgroundTask backgroundTask,
		String taskExecutorClassName, int status,
		OrderByComparator<BackgroundTask> orderByComparator, boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				5 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(4);
		}

		sb.append(_SQL_SELECT_BACKGROUNDTASK_WHERE);

		boolean bindTaskExecutorClassName = false;

		if (taskExecutorClassName.isEmpty()) {
			sb.append(_FINDER_COLUMN_T_S_TASKEXECUTORCLASSNAME_3);
		}
		else {
			bindTaskExecutorClassName = true;

			sb.append(_FINDER_COLUMN_T_S_TASKEXECUTORCLASSNAME_2);
		}

		sb.append(_FINDER_COLUMN_T_S_STATUS_2);

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
			sb.append(BackgroundTaskModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		if (bindTaskExecutorClassName) {
			queryPos.add(taskExecutorClassName);
		}

		queryPos.add(status);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						backgroundTask)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<BackgroundTask> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the background tasks where taskExecutorClassName = any &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BackgroundTaskModelImpl</code>.
	 * </p>
	 *
	 * @param taskExecutorClassNames the task executor class names
	 * @param status the status
	 * @return the matching background tasks
	 */
	@Override
	public List<BackgroundTask> findByT_S(
		String[] taskExecutorClassNames, int status) {

		return findByT_S(
			taskExecutorClassNames, status, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the background tasks where taskExecutorClassName = any &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BackgroundTaskModelImpl</code>.
	 * </p>
	 *
	 * @param taskExecutorClassNames the task executor class names
	 * @param status the status
	 * @param start the lower bound of the range of background tasks
	 * @param end the upper bound of the range of background tasks (not inclusive)
	 * @return the range of matching background tasks
	 */
	@Override
	public List<BackgroundTask> findByT_S(
		String[] taskExecutorClassNames, int status, int start, int end) {

		return findByT_S(taskExecutorClassNames, status, start, end, null);
	}

	/**
	 * Returns an ordered range of all the background tasks where taskExecutorClassName = any &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BackgroundTaskModelImpl</code>.
	 * </p>
	 *
	 * @param taskExecutorClassNames the task executor class names
	 * @param status the status
	 * @param start the lower bound of the range of background tasks
	 * @param end the upper bound of the range of background tasks (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching background tasks
	 */
	@Override
	public List<BackgroundTask> findByT_S(
		String[] taskExecutorClassNames, int status, int start, int end,
		OrderByComparator<BackgroundTask> orderByComparator) {

		return findByT_S(
			taskExecutorClassNames, status, start, end, orderByComparator,
			true);
	}

	/**
	 * Returns an ordered range of all the background tasks where taskExecutorClassName = &#63; and status = &#63;, optionally using the finder cache.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BackgroundTaskModelImpl</code>.
	 * </p>
	 *
	 * @param taskExecutorClassName the task executor class name
	 * @param status the status
	 * @param start the lower bound of the range of background tasks
	 * @param end the upper bound of the range of background tasks (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching background tasks
	 */
	@Override
	public List<BackgroundTask> findByT_S(
		String[] taskExecutorClassNames, int status, int start, int end,
		OrderByComparator<BackgroundTask> orderByComparator,
		boolean useFinderCache) {

		if (taskExecutorClassNames == null) {
			taskExecutorClassNames = new String[0];
		}
		else if (taskExecutorClassNames.length > 1) {
			for (int i = 0; i < taskExecutorClassNames.length; i++) {
				taskExecutorClassNames[i] = Objects.toString(
					taskExecutorClassNames[i], "");
			}

			taskExecutorClassNames = ArrayUtil.sortedUnique(
				taskExecutorClassNames);
		}

		if (taskExecutorClassNames.length == 1) {
			return findByT_S(
				taskExecutorClassNames[0], status, start, end,
				orderByComparator);
		}

		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderArgs = new Object[] {
					StringUtil.merge(taskExecutorClassNames), status
				};
			}
		}
		else if (useFinderCache) {
			finderArgs = new Object[] {
				StringUtil.merge(taskExecutorClassNames), status, start, end,
				orderByComparator
			};
		}

		List<BackgroundTask> list = null;

		if (useFinderCache) {
			list = (List<BackgroundTask>)finderCache.getResult(
				_finderPathWithPaginationFindByT_S, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (BackgroundTask backgroundTask : list) {
					if (!ArrayUtil.contains(
							taskExecutorClassNames,
							backgroundTask.getTaskExecutorClassName()) ||
						(status != backgroundTask.getStatus())) {

						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler sb = new StringBundler();

			sb.append(_SQL_SELECT_BACKGROUNDTASK_WHERE);

			if (taskExecutorClassNames.length > 0) {
				sb.append("(");

				for (int i = 0; i < taskExecutorClassNames.length; i++) {
					String taskExecutorClassName = taskExecutorClassNames[i];

					if (taskExecutorClassName.isEmpty()) {
						sb.append(_FINDER_COLUMN_T_S_TASKEXECUTORCLASSNAME_6);
					}
					else {
						sb.append(_FINDER_COLUMN_T_S_TASKEXECUTORCLASSNAME_5);
					}

					if ((i + 1) < taskExecutorClassNames.length) {
						sb.append(WHERE_OR);
					}
				}

				sb.append(")");

				sb.append(WHERE_AND);
			}

			sb.append(_FINDER_COLUMN_T_S_STATUS_2);

			sb.setStringAt(
				removeConjunction(sb.stringAt(sb.index() - 1)), sb.index() - 1);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(BackgroundTaskModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				for (String taskExecutorClassName : taskExecutorClassNames) {
					if ((taskExecutorClassName != null) &&
						!taskExecutorClassName.isEmpty()) {

						queryPos.add(taskExecutorClassName);
					}
				}

				queryPos.add(status);

				list = (List<BackgroundTask>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					finderCache.putResult(
						_finderPathWithPaginationFindByT_S, finderArgs, list);
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
	 * Removes all the background tasks where taskExecutorClassName = &#63; and status = &#63; from the database.
	 *
	 * @param taskExecutorClassName the task executor class name
	 * @param status the status
	 */
	@Override
	public void removeByT_S(String taskExecutorClassName, int status) {
		for (BackgroundTask backgroundTask :
				findByT_S(
					taskExecutorClassName, status, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			remove(backgroundTask);
		}
	}

	/**
	 * Returns the number of background tasks where taskExecutorClassName = &#63; and status = &#63;.
	 *
	 * @param taskExecutorClassName the task executor class name
	 * @param status the status
	 * @return the number of matching background tasks
	 */
	@Override
	public int countByT_S(String taskExecutorClassName, int status) {
		taskExecutorClassName = Objects.toString(taskExecutorClassName, "");

		FinderPath finderPath = _finderPathCountByT_S;

		Object[] finderArgs = new Object[] {taskExecutorClassName, status};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_BACKGROUNDTASK_WHERE);

			boolean bindTaskExecutorClassName = false;

			if (taskExecutorClassName.isEmpty()) {
				sb.append(_FINDER_COLUMN_T_S_TASKEXECUTORCLASSNAME_3);
			}
			else {
				bindTaskExecutorClassName = true;

				sb.append(_FINDER_COLUMN_T_S_TASKEXECUTORCLASSNAME_2);
			}

			sb.append(_FINDER_COLUMN_T_S_STATUS_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindTaskExecutorClassName) {
					queryPos.add(taskExecutorClassName);
				}

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

	/**
	 * Returns the number of background tasks where taskExecutorClassName = any &#63; and status = &#63;.
	 *
	 * @param taskExecutorClassNames the task executor class names
	 * @param status the status
	 * @return the number of matching background tasks
	 */
	@Override
	public int countByT_S(String[] taskExecutorClassNames, int status) {
		if (taskExecutorClassNames == null) {
			taskExecutorClassNames = new String[0];
		}
		else if (taskExecutorClassNames.length > 1) {
			for (int i = 0; i < taskExecutorClassNames.length; i++) {
				taskExecutorClassNames[i] = Objects.toString(
					taskExecutorClassNames[i], "");
			}

			taskExecutorClassNames = ArrayUtil.sortedUnique(
				taskExecutorClassNames);
		}

		Object[] finderArgs = new Object[] {
			StringUtil.merge(taskExecutorClassNames), status
		};

		Long count = (Long)finderCache.getResult(
			_finderPathWithPaginationCountByT_S, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler();

			sb.append(_SQL_COUNT_BACKGROUNDTASK_WHERE);

			if (taskExecutorClassNames.length > 0) {
				sb.append("(");

				for (int i = 0; i < taskExecutorClassNames.length; i++) {
					String taskExecutorClassName = taskExecutorClassNames[i];

					if (taskExecutorClassName.isEmpty()) {
						sb.append(_FINDER_COLUMN_T_S_TASKEXECUTORCLASSNAME_6);
					}
					else {
						sb.append(_FINDER_COLUMN_T_S_TASKEXECUTORCLASSNAME_5);
					}

					if ((i + 1) < taskExecutorClassNames.length) {
						sb.append(WHERE_OR);
					}
				}

				sb.append(")");

				sb.append(WHERE_AND);
			}

			sb.append(_FINDER_COLUMN_T_S_STATUS_2);

			sb.setStringAt(
				removeConjunction(sb.stringAt(sb.index() - 1)), sb.index() - 1);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				for (String taskExecutorClassName : taskExecutorClassNames) {
					if ((taskExecutorClassName != null) &&
						!taskExecutorClassName.isEmpty()) {

						queryPos.add(taskExecutorClassName);
					}
				}

				queryPos.add(status);

				count = (Long)query.uniqueResult();

				finderCache.putResult(
					_finderPathWithPaginationCountByT_S, finderArgs, count);
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

	private static final String _FINDER_COLUMN_T_S_TASKEXECUTORCLASSNAME_2 =
		"backgroundTask.taskExecutorClassName = ? AND ";

	private static final String _FINDER_COLUMN_T_S_TASKEXECUTORCLASSNAME_3 =
		"(backgroundTask.taskExecutorClassName IS NULL OR backgroundTask.taskExecutorClassName = '') AND ";

	private static final String _FINDER_COLUMN_T_S_TASKEXECUTORCLASSNAME_5 =
		"(" + removeConjunction(_FINDER_COLUMN_T_S_TASKEXECUTORCLASSNAME_2) +
			")";

	private static final String _FINDER_COLUMN_T_S_TASKEXECUTORCLASSNAME_6 =
		"(" + removeConjunction(_FINDER_COLUMN_T_S_TASKEXECUTORCLASSNAME_3) +
			")";

	private static final String _FINDER_COLUMN_T_S_STATUS_2 =
		"backgroundTask.status = ?";

	private FinderPath _finderPathWithPaginationFindByG_N_T;
	private FinderPath _finderPathWithoutPaginationFindByG_N_T;
	private FinderPath _finderPathCountByG_N_T;
	private FinderPath _finderPathWithPaginationCountByG_N_T;

	/**
	 * Returns all the background tasks where groupId = &#63; and name = &#63; and taskExecutorClassName = &#63;.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param taskExecutorClassName the task executor class name
	 * @return the matching background tasks
	 */
	@Override
	public List<BackgroundTask> findByG_N_T(
		long groupId, String name, String taskExecutorClassName) {

		return findByG_N_T(
			groupId, name, taskExecutorClassName, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the background tasks where groupId = &#63; and name = &#63; and taskExecutorClassName = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BackgroundTaskModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param taskExecutorClassName the task executor class name
	 * @param start the lower bound of the range of background tasks
	 * @param end the upper bound of the range of background tasks (not inclusive)
	 * @return the range of matching background tasks
	 */
	@Override
	public List<BackgroundTask> findByG_N_T(
		long groupId, String name, String taskExecutorClassName, int start,
		int end) {

		return findByG_N_T(
			groupId, name, taskExecutorClassName, start, end, null);
	}

	/**
	 * Returns an ordered range of all the background tasks where groupId = &#63; and name = &#63; and taskExecutorClassName = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BackgroundTaskModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param taskExecutorClassName the task executor class name
	 * @param start the lower bound of the range of background tasks
	 * @param end the upper bound of the range of background tasks (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching background tasks
	 */
	@Override
	public List<BackgroundTask> findByG_N_T(
		long groupId, String name, String taskExecutorClassName, int start,
		int end, OrderByComparator<BackgroundTask> orderByComparator) {

		return findByG_N_T(
			groupId, name, taskExecutorClassName, start, end, orderByComparator,
			true);
	}

	/**
	 * Returns an ordered range of all the background tasks where groupId = &#63; and name = &#63; and taskExecutorClassName = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BackgroundTaskModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param taskExecutorClassName the task executor class name
	 * @param start the lower bound of the range of background tasks
	 * @param end the upper bound of the range of background tasks (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching background tasks
	 */
	@Override
	public List<BackgroundTask> findByG_N_T(
		long groupId, String name, String taskExecutorClassName, int start,
		int end, OrderByComparator<BackgroundTask> orderByComparator,
		boolean useFinderCache) {

		name = Objects.toString(name, "");
		taskExecutorClassName = Objects.toString(taskExecutorClassName, "");

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByG_N_T;
				finderArgs = new Object[] {
					groupId, name, taskExecutorClassName
				};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByG_N_T;
			finderArgs = new Object[] {
				groupId, name, taskExecutorClassName, start, end,
				orderByComparator
			};
		}

		List<BackgroundTask> list = null;

		if (useFinderCache) {
			list = (List<BackgroundTask>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (BackgroundTask backgroundTask : list) {
					if ((groupId != backgroundTask.getGroupId()) ||
						!name.equals(backgroundTask.getName()) ||
						!taskExecutorClassName.equals(
							backgroundTask.getTaskExecutorClassName())) {

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
					5 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				sb = new StringBundler(5);
			}

			sb.append(_SQL_SELECT_BACKGROUNDTASK_WHERE);

			sb.append(_FINDER_COLUMN_G_N_T_GROUPID_2);

			boolean bindName = false;

			if (name.isEmpty()) {
				sb.append(_FINDER_COLUMN_G_N_T_NAME_3);
			}
			else {
				bindName = true;

				sb.append(_FINDER_COLUMN_G_N_T_NAME_2);
			}

			boolean bindTaskExecutorClassName = false;

			if (taskExecutorClassName.isEmpty()) {
				sb.append(_FINDER_COLUMN_G_N_T_TASKEXECUTORCLASSNAME_3);
			}
			else {
				bindTaskExecutorClassName = true;

				sb.append(_FINDER_COLUMN_G_N_T_TASKEXECUTORCLASSNAME_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(BackgroundTaskModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				if (bindName) {
					queryPos.add(name);
				}

				if (bindTaskExecutorClassName) {
					queryPos.add(taskExecutorClassName);
				}

				list = (List<BackgroundTask>)QueryUtil.list(
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
	 * Returns the first background task in the ordered set where groupId = &#63; and name = &#63; and taskExecutorClassName = &#63;.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param taskExecutorClassName the task executor class name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching background task
	 * @throws NoSuchBackgroundTaskException if a matching background task could not be found
	 */
	@Override
	public BackgroundTask findByG_N_T_First(
			long groupId, String name, String taskExecutorClassName,
			OrderByComparator<BackgroundTask> orderByComparator)
		throws NoSuchBackgroundTaskException {

		BackgroundTask backgroundTask = fetchByG_N_T_First(
			groupId, name, taskExecutorClassName, orderByComparator);

		if (backgroundTask != null) {
			return backgroundTask;
		}

		StringBundler sb = new StringBundler(8);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", name=");
		sb.append(name);

		sb.append(", taskExecutorClassName=");
		sb.append(taskExecutorClassName);

		sb.append("}");

		throw new NoSuchBackgroundTaskException(sb.toString());
	}

	/**
	 * Returns the first background task in the ordered set where groupId = &#63; and name = &#63; and taskExecutorClassName = &#63;.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param taskExecutorClassName the task executor class name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching background task, or <code>null</code> if a matching background task could not be found
	 */
	@Override
	public BackgroundTask fetchByG_N_T_First(
		long groupId, String name, String taskExecutorClassName,
		OrderByComparator<BackgroundTask> orderByComparator) {

		List<BackgroundTask> list = findByG_N_T(
			groupId, name, taskExecutorClassName, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last background task in the ordered set where groupId = &#63; and name = &#63; and taskExecutorClassName = &#63;.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param taskExecutorClassName the task executor class name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching background task
	 * @throws NoSuchBackgroundTaskException if a matching background task could not be found
	 */
	@Override
	public BackgroundTask findByG_N_T_Last(
			long groupId, String name, String taskExecutorClassName,
			OrderByComparator<BackgroundTask> orderByComparator)
		throws NoSuchBackgroundTaskException {

		BackgroundTask backgroundTask = fetchByG_N_T_Last(
			groupId, name, taskExecutorClassName, orderByComparator);

		if (backgroundTask != null) {
			return backgroundTask;
		}

		StringBundler sb = new StringBundler(8);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", name=");
		sb.append(name);

		sb.append(", taskExecutorClassName=");
		sb.append(taskExecutorClassName);

		sb.append("}");

		throw new NoSuchBackgroundTaskException(sb.toString());
	}

	/**
	 * Returns the last background task in the ordered set where groupId = &#63; and name = &#63; and taskExecutorClassName = &#63;.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param taskExecutorClassName the task executor class name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching background task, or <code>null</code> if a matching background task could not be found
	 */
	@Override
	public BackgroundTask fetchByG_N_T_Last(
		long groupId, String name, String taskExecutorClassName,
		OrderByComparator<BackgroundTask> orderByComparator) {

		int count = countByG_N_T(groupId, name, taskExecutorClassName);

		if (count == 0) {
			return null;
		}

		List<BackgroundTask> list = findByG_N_T(
			groupId, name, taskExecutorClassName, count - 1, count,
			orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the background tasks before and after the current background task in the ordered set where groupId = &#63; and name = &#63; and taskExecutorClassName = &#63;.
	 *
	 * @param backgroundTaskId the primary key of the current background task
	 * @param groupId the group ID
	 * @param name the name
	 * @param taskExecutorClassName the task executor class name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next background task
	 * @throws NoSuchBackgroundTaskException if a background task with the primary key could not be found
	 */
	@Override
	public BackgroundTask[] findByG_N_T_PrevAndNext(
			long backgroundTaskId, long groupId, String name,
			String taskExecutorClassName,
			OrderByComparator<BackgroundTask> orderByComparator)
		throws NoSuchBackgroundTaskException {

		name = Objects.toString(name, "");
		taskExecutorClassName = Objects.toString(taskExecutorClassName, "");

		BackgroundTask backgroundTask = findByPrimaryKey(backgroundTaskId);

		Session session = null;

		try {
			session = openSession();

			BackgroundTask[] array = new BackgroundTaskImpl[3];

			array[0] = getByG_N_T_PrevAndNext(
				session, backgroundTask, groupId, name, taskExecutorClassName,
				orderByComparator, true);

			array[1] = backgroundTask;

			array[2] = getByG_N_T_PrevAndNext(
				session, backgroundTask, groupId, name, taskExecutorClassName,
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

	protected BackgroundTask getByG_N_T_PrevAndNext(
		Session session, BackgroundTask backgroundTask, long groupId,
		String name, String taskExecutorClassName,
		OrderByComparator<BackgroundTask> orderByComparator, boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				6 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(5);
		}

		sb.append(_SQL_SELECT_BACKGROUNDTASK_WHERE);

		sb.append(_FINDER_COLUMN_G_N_T_GROUPID_2);

		boolean bindName = false;

		if (name.isEmpty()) {
			sb.append(_FINDER_COLUMN_G_N_T_NAME_3);
		}
		else {
			bindName = true;

			sb.append(_FINDER_COLUMN_G_N_T_NAME_2);
		}

		boolean bindTaskExecutorClassName = false;

		if (taskExecutorClassName.isEmpty()) {
			sb.append(_FINDER_COLUMN_G_N_T_TASKEXECUTORCLASSNAME_3);
		}
		else {
			bindTaskExecutorClassName = true;

			sb.append(_FINDER_COLUMN_G_N_T_TASKEXECUTORCLASSNAME_2);
		}

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
			sb.append(BackgroundTaskModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(groupId);

		if (bindName) {
			queryPos.add(name);
		}

		if (bindTaskExecutorClassName) {
			queryPos.add(taskExecutorClassName);
		}

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						backgroundTask)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<BackgroundTask> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the background tasks where groupId = any &#63; and name = &#63; and taskExecutorClassName = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BackgroundTaskModelImpl</code>.
	 * </p>
	 *
	 * @param groupIds the group IDs
	 * @param name the name
	 * @param taskExecutorClassNames the task executor class names
	 * @return the matching background tasks
	 */
	@Override
	public List<BackgroundTask> findByG_N_T(
		long[] groupIds, String name, String[] taskExecutorClassNames) {

		return findByG_N_T(
			groupIds, name, taskExecutorClassNames, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the background tasks where groupId = any &#63; and name = &#63; and taskExecutorClassName = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BackgroundTaskModelImpl</code>.
	 * </p>
	 *
	 * @param groupIds the group IDs
	 * @param name the name
	 * @param taskExecutorClassNames the task executor class names
	 * @param start the lower bound of the range of background tasks
	 * @param end the upper bound of the range of background tasks (not inclusive)
	 * @return the range of matching background tasks
	 */
	@Override
	public List<BackgroundTask> findByG_N_T(
		long[] groupIds, String name, String[] taskExecutorClassNames,
		int start, int end) {

		return findByG_N_T(
			groupIds, name, taskExecutorClassNames, start, end, null);
	}

	/**
	 * Returns an ordered range of all the background tasks where groupId = any &#63; and name = &#63; and taskExecutorClassName = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BackgroundTaskModelImpl</code>.
	 * </p>
	 *
	 * @param groupIds the group IDs
	 * @param name the name
	 * @param taskExecutorClassNames the task executor class names
	 * @param start the lower bound of the range of background tasks
	 * @param end the upper bound of the range of background tasks (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching background tasks
	 */
	@Override
	public List<BackgroundTask> findByG_N_T(
		long[] groupIds, String name, String[] taskExecutorClassNames,
		int start, int end,
		OrderByComparator<BackgroundTask> orderByComparator) {

		return findByG_N_T(
			groupIds, name, taskExecutorClassNames, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the background tasks where groupId = &#63; and name = &#63; and taskExecutorClassName = &#63;, optionally using the finder cache.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BackgroundTaskModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param taskExecutorClassName the task executor class name
	 * @param start the lower bound of the range of background tasks
	 * @param end the upper bound of the range of background tasks (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching background tasks
	 */
	@Override
	public List<BackgroundTask> findByG_N_T(
		long[] groupIds, String name, String[] taskExecutorClassNames,
		int start, int end, OrderByComparator<BackgroundTask> orderByComparator,
		boolean useFinderCache) {

		if (groupIds == null) {
			groupIds = new long[0];
		}
		else if (groupIds.length > 1) {
			groupIds = ArrayUtil.sortedUnique(groupIds);
		}

		name = Objects.toString(name, "");

		if (taskExecutorClassNames == null) {
			taskExecutorClassNames = new String[0];
		}
		else if (taskExecutorClassNames.length > 1) {
			for (int i = 0; i < taskExecutorClassNames.length; i++) {
				taskExecutorClassNames[i] = Objects.toString(
					taskExecutorClassNames[i], "");
			}

			taskExecutorClassNames = ArrayUtil.sortedUnique(
				taskExecutorClassNames);
		}

		if ((groupIds.length == 1) && (taskExecutorClassNames.length == 1)) {
			return findByG_N_T(
				groupIds[0], name, taskExecutorClassNames[0], start, end,
				orderByComparator);
		}

		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderArgs = new Object[] {
					StringUtil.merge(groupIds), name,
					StringUtil.merge(taskExecutorClassNames)
				};
			}
		}
		else if (useFinderCache) {
			finderArgs = new Object[] {
				StringUtil.merge(groupIds), name,
				StringUtil.merge(taskExecutorClassNames), start, end,
				orderByComparator
			};
		}

		List<BackgroundTask> list = null;

		if (useFinderCache) {
			list = (List<BackgroundTask>)finderCache.getResult(
				_finderPathWithPaginationFindByG_N_T, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (BackgroundTask backgroundTask : list) {
					if (!ArrayUtil.contains(
							groupIds, backgroundTask.getGroupId()) ||
						!name.equals(backgroundTask.getName()) ||
						!ArrayUtil.contains(
							taskExecutorClassNames,
							backgroundTask.getTaskExecutorClassName())) {

						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler sb = new StringBundler();

			sb.append(_SQL_SELECT_BACKGROUNDTASK_WHERE);

			if (groupIds.length > 0) {
				sb.append("(");

				sb.append(_FINDER_COLUMN_G_N_T_GROUPID_7);

				sb.append(StringUtil.merge(groupIds));

				sb.append(")");

				sb.append(")");

				sb.append(WHERE_AND);
			}

			boolean bindName = false;

			if (name.isEmpty()) {
				sb.append(_FINDER_COLUMN_G_N_T_NAME_3);
			}
			else {
				bindName = true;

				sb.append(_FINDER_COLUMN_G_N_T_NAME_2);
			}

			if (taskExecutorClassNames.length > 0) {
				sb.append("(");

				for (int i = 0; i < taskExecutorClassNames.length; i++) {
					String taskExecutorClassName = taskExecutorClassNames[i];

					if (taskExecutorClassName.isEmpty()) {
						sb.append(_FINDER_COLUMN_G_N_T_TASKEXECUTORCLASSNAME_3);
					}
					else {
						sb.append(_FINDER_COLUMN_G_N_T_TASKEXECUTORCLASSNAME_2);
					}

					if ((i + 1) < taskExecutorClassNames.length) {
						sb.append(WHERE_OR);
					}
				}

				sb.append(")");
			}

			sb.setStringAt(
				removeConjunction(sb.stringAt(sb.index() - 1)), sb.index() - 1);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(BackgroundTaskModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindName) {
					queryPos.add(name);
				}

				for (String taskExecutorClassName : taskExecutorClassNames) {
					if ((taskExecutorClassName != null) &&
						!taskExecutorClassName.isEmpty()) {

						queryPos.add(taskExecutorClassName);
					}
				}

				list = (List<BackgroundTask>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					finderCache.putResult(
						_finderPathWithPaginationFindByG_N_T, finderArgs, list);
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
	 * Removes all the background tasks where groupId = &#63; and name = &#63; and taskExecutorClassName = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param taskExecutorClassName the task executor class name
	 */
	@Override
	public void removeByG_N_T(
		long groupId, String name, String taskExecutorClassName) {

		for (BackgroundTask backgroundTask :
				findByG_N_T(
					groupId, name, taskExecutorClassName, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			remove(backgroundTask);
		}
	}

	/**
	 * Returns the number of background tasks where groupId = &#63; and name = &#63; and taskExecutorClassName = &#63;.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param taskExecutorClassName the task executor class name
	 * @return the number of matching background tasks
	 */
	@Override
	public int countByG_N_T(
		long groupId, String name, String taskExecutorClassName) {

		name = Objects.toString(name, "");
		taskExecutorClassName = Objects.toString(taskExecutorClassName, "");

		FinderPath finderPath = _finderPathCountByG_N_T;

		Object[] finderArgs = new Object[] {
			groupId, name, taskExecutorClassName
		};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_COUNT_BACKGROUNDTASK_WHERE);

			sb.append(_FINDER_COLUMN_G_N_T_GROUPID_2);

			boolean bindName = false;

			if (name.isEmpty()) {
				sb.append(_FINDER_COLUMN_G_N_T_NAME_3);
			}
			else {
				bindName = true;

				sb.append(_FINDER_COLUMN_G_N_T_NAME_2);
			}

			boolean bindTaskExecutorClassName = false;

			if (taskExecutorClassName.isEmpty()) {
				sb.append(_FINDER_COLUMN_G_N_T_TASKEXECUTORCLASSNAME_3);
			}
			else {
				bindTaskExecutorClassName = true;

				sb.append(_FINDER_COLUMN_G_N_T_TASKEXECUTORCLASSNAME_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				if (bindName) {
					queryPos.add(name);
				}

				if (bindTaskExecutorClassName) {
					queryPos.add(taskExecutorClassName);
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

	/**
	 * Returns the number of background tasks where groupId = any &#63; and name = &#63; and taskExecutorClassName = any &#63;.
	 *
	 * @param groupIds the group IDs
	 * @param name the name
	 * @param taskExecutorClassNames the task executor class names
	 * @return the number of matching background tasks
	 */
	@Override
	public int countByG_N_T(
		long[] groupIds, String name, String[] taskExecutorClassNames) {

		if (groupIds == null) {
			groupIds = new long[0];
		}
		else if (groupIds.length > 1) {
			groupIds = ArrayUtil.sortedUnique(groupIds);
		}

		name = Objects.toString(name, "");

		if (taskExecutorClassNames == null) {
			taskExecutorClassNames = new String[0];
		}
		else if (taskExecutorClassNames.length > 1) {
			for (int i = 0; i < taskExecutorClassNames.length; i++) {
				taskExecutorClassNames[i] = Objects.toString(
					taskExecutorClassNames[i], "");
			}

			taskExecutorClassNames = ArrayUtil.sortedUnique(
				taskExecutorClassNames);
		}

		Object[] finderArgs = new Object[] {
			StringUtil.merge(groupIds), name,
			StringUtil.merge(taskExecutorClassNames)
		};

		Long count = (Long)finderCache.getResult(
			_finderPathWithPaginationCountByG_N_T, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler();

			sb.append(_SQL_COUNT_BACKGROUNDTASK_WHERE);

			if (groupIds.length > 0) {
				sb.append("(");

				sb.append(_FINDER_COLUMN_G_N_T_GROUPID_7);

				sb.append(StringUtil.merge(groupIds));

				sb.append(")");

				sb.append(")");

				sb.append(WHERE_AND);
			}

			boolean bindName = false;

			if (name.isEmpty()) {
				sb.append(_FINDER_COLUMN_G_N_T_NAME_3);
			}
			else {
				bindName = true;

				sb.append(_FINDER_COLUMN_G_N_T_NAME_2);
			}

			if (taskExecutorClassNames.length > 0) {
				sb.append("(");

				for (int i = 0; i < taskExecutorClassNames.length; i++) {
					String taskExecutorClassName = taskExecutorClassNames[i];

					if (taskExecutorClassName.isEmpty()) {
						sb.append(_FINDER_COLUMN_G_N_T_TASKEXECUTORCLASSNAME_3);
					}
					else {
						sb.append(_FINDER_COLUMN_G_N_T_TASKEXECUTORCLASSNAME_2);
					}

					if ((i + 1) < taskExecutorClassNames.length) {
						sb.append(WHERE_OR);
					}
				}

				sb.append(")");
			}

			sb.setStringAt(
				removeConjunction(sb.stringAt(sb.index() - 1)), sb.index() - 1);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindName) {
					queryPos.add(name);
				}

				for (String taskExecutorClassName : taskExecutorClassNames) {
					if ((taskExecutorClassName != null) &&
						!taskExecutorClassName.isEmpty()) {

						queryPos.add(taskExecutorClassName);
					}
				}

				count = (Long)query.uniqueResult();

				finderCache.putResult(
					_finderPathWithPaginationCountByG_N_T, finderArgs, count);
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

	private static final String _FINDER_COLUMN_G_N_T_GROUPID_2 =
		"backgroundTask.groupId = ? AND ";

	private static final String _FINDER_COLUMN_G_N_T_GROUPID_7 =
		"backgroundTask.groupId IN (";

	private static final String _FINDER_COLUMN_G_N_T_NAME_2 =
		"backgroundTask.name = ? AND ";

	private static final String _FINDER_COLUMN_G_N_T_NAME_3 =
		"(backgroundTask.name IS NULL OR backgroundTask.name = '') AND ";

	private static final String _FINDER_COLUMN_G_N_T_TASKEXECUTORCLASSNAME_2 =
		"backgroundTask.taskExecutorClassName = ?";

	private static final String _FINDER_COLUMN_G_N_T_TASKEXECUTORCLASSNAME_3 =
		"(backgroundTask.taskExecutorClassName IS NULL OR backgroundTask.taskExecutorClassName = '')";

	private FinderPath _finderPathWithPaginationFindByG_T_C;
	private FinderPath _finderPathWithoutPaginationFindByG_T_C;
	private FinderPath _finderPathCountByG_T_C;
	private FinderPath _finderPathWithPaginationCountByG_T_C;

	/**
	 * Returns all the background tasks where groupId = &#63; and taskExecutorClassName = &#63; and completed = &#63;.
	 *
	 * @param groupId the group ID
	 * @param taskExecutorClassName the task executor class name
	 * @param completed the completed
	 * @return the matching background tasks
	 */
	@Override
	public List<BackgroundTask> findByG_T_C(
		long groupId, String taskExecutorClassName, boolean completed) {

		return findByG_T_C(
			groupId, taskExecutorClassName, completed, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the background tasks where groupId = &#63; and taskExecutorClassName = &#63; and completed = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BackgroundTaskModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param taskExecutorClassName the task executor class name
	 * @param completed the completed
	 * @param start the lower bound of the range of background tasks
	 * @param end the upper bound of the range of background tasks (not inclusive)
	 * @return the range of matching background tasks
	 */
	@Override
	public List<BackgroundTask> findByG_T_C(
		long groupId, String taskExecutorClassName, boolean completed,
		int start, int end) {

		return findByG_T_C(
			groupId, taskExecutorClassName, completed, start, end, null);
	}

	/**
	 * Returns an ordered range of all the background tasks where groupId = &#63; and taskExecutorClassName = &#63; and completed = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BackgroundTaskModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param taskExecutorClassName the task executor class name
	 * @param completed the completed
	 * @param start the lower bound of the range of background tasks
	 * @param end the upper bound of the range of background tasks (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching background tasks
	 */
	@Override
	public List<BackgroundTask> findByG_T_C(
		long groupId, String taskExecutorClassName, boolean completed,
		int start, int end,
		OrderByComparator<BackgroundTask> orderByComparator) {

		return findByG_T_C(
			groupId, taskExecutorClassName, completed, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the background tasks where groupId = &#63; and taskExecutorClassName = &#63; and completed = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BackgroundTaskModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param taskExecutorClassName the task executor class name
	 * @param completed the completed
	 * @param start the lower bound of the range of background tasks
	 * @param end the upper bound of the range of background tasks (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching background tasks
	 */
	@Override
	public List<BackgroundTask> findByG_T_C(
		long groupId, String taskExecutorClassName, boolean completed,
		int start, int end, OrderByComparator<BackgroundTask> orderByComparator,
		boolean useFinderCache) {

		taskExecutorClassName = Objects.toString(taskExecutorClassName, "");

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByG_T_C;
				finderArgs = new Object[] {
					groupId, taskExecutorClassName, completed
				};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByG_T_C;
			finderArgs = new Object[] {
				groupId, taskExecutorClassName, completed, start, end,
				orderByComparator
			};
		}

		List<BackgroundTask> list = null;

		if (useFinderCache) {
			list = (List<BackgroundTask>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (BackgroundTask backgroundTask : list) {
					if ((groupId != backgroundTask.getGroupId()) ||
						!taskExecutorClassName.equals(
							backgroundTask.getTaskExecutorClassName()) ||
						(completed != backgroundTask.isCompleted())) {

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
					5 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				sb = new StringBundler(5);
			}

			sb.append(_SQL_SELECT_BACKGROUNDTASK_WHERE);

			sb.append(_FINDER_COLUMN_G_T_C_GROUPID_2);

			boolean bindTaskExecutorClassName = false;

			if (taskExecutorClassName.isEmpty()) {
				sb.append(_FINDER_COLUMN_G_T_C_TASKEXECUTORCLASSNAME_3);
			}
			else {
				bindTaskExecutorClassName = true;

				sb.append(_FINDER_COLUMN_G_T_C_TASKEXECUTORCLASSNAME_2);
			}

			sb.append(_FINDER_COLUMN_G_T_C_COMPLETED_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(BackgroundTaskModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				if (bindTaskExecutorClassName) {
					queryPos.add(taskExecutorClassName);
				}

				queryPos.add(completed);

				list = (List<BackgroundTask>)QueryUtil.list(
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
	 * Returns the first background task in the ordered set where groupId = &#63; and taskExecutorClassName = &#63; and completed = &#63;.
	 *
	 * @param groupId the group ID
	 * @param taskExecutorClassName the task executor class name
	 * @param completed the completed
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching background task
	 * @throws NoSuchBackgroundTaskException if a matching background task could not be found
	 */
	@Override
	public BackgroundTask findByG_T_C_First(
			long groupId, String taskExecutorClassName, boolean completed,
			OrderByComparator<BackgroundTask> orderByComparator)
		throws NoSuchBackgroundTaskException {

		BackgroundTask backgroundTask = fetchByG_T_C_First(
			groupId, taskExecutorClassName, completed, orderByComparator);

		if (backgroundTask != null) {
			return backgroundTask;
		}

		StringBundler sb = new StringBundler(8);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", taskExecutorClassName=");
		sb.append(taskExecutorClassName);

		sb.append(", completed=");
		sb.append(completed);

		sb.append("}");

		throw new NoSuchBackgroundTaskException(sb.toString());
	}

	/**
	 * Returns the first background task in the ordered set where groupId = &#63; and taskExecutorClassName = &#63; and completed = &#63;.
	 *
	 * @param groupId the group ID
	 * @param taskExecutorClassName the task executor class name
	 * @param completed the completed
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching background task, or <code>null</code> if a matching background task could not be found
	 */
	@Override
	public BackgroundTask fetchByG_T_C_First(
		long groupId, String taskExecutorClassName, boolean completed,
		OrderByComparator<BackgroundTask> orderByComparator) {

		List<BackgroundTask> list = findByG_T_C(
			groupId, taskExecutorClassName, completed, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last background task in the ordered set where groupId = &#63; and taskExecutorClassName = &#63; and completed = &#63;.
	 *
	 * @param groupId the group ID
	 * @param taskExecutorClassName the task executor class name
	 * @param completed the completed
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching background task
	 * @throws NoSuchBackgroundTaskException if a matching background task could not be found
	 */
	@Override
	public BackgroundTask findByG_T_C_Last(
			long groupId, String taskExecutorClassName, boolean completed,
			OrderByComparator<BackgroundTask> orderByComparator)
		throws NoSuchBackgroundTaskException {

		BackgroundTask backgroundTask = fetchByG_T_C_Last(
			groupId, taskExecutorClassName, completed, orderByComparator);

		if (backgroundTask != null) {
			return backgroundTask;
		}

		StringBundler sb = new StringBundler(8);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", taskExecutorClassName=");
		sb.append(taskExecutorClassName);

		sb.append(", completed=");
		sb.append(completed);

		sb.append("}");

		throw new NoSuchBackgroundTaskException(sb.toString());
	}

	/**
	 * Returns the last background task in the ordered set where groupId = &#63; and taskExecutorClassName = &#63; and completed = &#63;.
	 *
	 * @param groupId the group ID
	 * @param taskExecutorClassName the task executor class name
	 * @param completed the completed
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching background task, or <code>null</code> if a matching background task could not be found
	 */
	@Override
	public BackgroundTask fetchByG_T_C_Last(
		long groupId, String taskExecutorClassName, boolean completed,
		OrderByComparator<BackgroundTask> orderByComparator) {

		int count = countByG_T_C(groupId, taskExecutorClassName, completed);

		if (count == 0) {
			return null;
		}

		List<BackgroundTask> list = findByG_T_C(
			groupId, taskExecutorClassName, completed, count - 1, count,
			orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the background tasks before and after the current background task in the ordered set where groupId = &#63; and taskExecutorClassName = &#63; and completed = &#63;.
	 *
	 * @param backgroundTaskId the primary key of the current background task
	 * @param groupId the group ID
	 * @param taskExecutorClassName the task executor class name
	 * @param completed the completed
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next background task
	 * @throws NoSuchBackgroundTaskException if a background task with the primary key could not be found
	 */
	@Override
	public BackgroundTask[] findByG_T_C_PrevAndNext(
			long backgroundTaskId, long groupId, String taskExecutorClassName,
			boolean completed,
			OrderByComparator<BackgroundTask> orderByComparator)
		throws NoSuchBackgroundTaskException {

		taskExecutorClassName = Objects.toString(taskExecutorClassName, "");

		BackgroundTask backgroundTask = findByPrimaryKey(backgroundTaskId);

		Session session = null;

		try {
			session = openSession();

			BackgroundTask[] array = new BackgroundTaskImpl[3];

			array[0] = getByG_T_C_PrevAndNext(
				session, backgroundTask, groupId, taskExecutorClassName,
				completed, orderByComparator, true);

			array[1] = backgroundTask;

			array[2] = getByG_T_C_PrevAndNext(
				session, backgroundTask, groupId, taskExecutorClassName,
				completed, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected BackgroundTask getByG_T_C_PrevAndNext(
		Session session, BackgroundTask backgroundTask, long groupId,
		String taskExecutorClassName, boolean completed,
		OrderByComparator<BackgroundTask> orderByComparator, boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				6 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(5);
		}

		sb.append(_SQL_SELECT_BACKGROUNDTASK_WHERE);

		sb.append(_FINDER_COLUMN_G_T_C_GROUPID_2);

		boolean bindTaskExecutorClassName = false;

		if (taskExecutorClassName.isEmpty()) {
			sb.append(_FINDER_COLUMN_G_T_C_TASKEXECUTORCLASSNAME_3);
		}
		else {
			bindTaskExecutorClassName = true;

			sb.append(_FINDER_COLUMN_G_T_C_TASKEXECUTORCLASSNAME_2);
		}

		sb.append(_FINDER_COLUMN_G_T_C_COMPLETED_2);

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
			sb.append(BackgroundTaskModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(groupId);

		if (bindTaskExecutorClassName) {
			queryPos.add(taskExecutorClassName);
		}

		queryPos.add(completed);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						backgroundTask)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<BackgroundTask> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the background tasks where groupId = any &#63; and taskExecutorClassName = any &#63; and completed = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BackgroundTaskModelImpl</code>.
	 * </p>
	 *
	 * @param groupIds the group IDs
	 * @param taskExecutorClassNames the task executor class names
	 * @param completed the completed
	 * @return the matching background tasks
	 */
	@Override
	public List<BackgroundTask> findByG_T_C(
		long[] groupIds, String[] taskExecutorClassNames, boolean completed) {

		return findByG_T_C(
			groupIds, taskExecutorClassNames, completed, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the background tasks where groupId = any &#63; and taskExecutorClassName = any &#63; and completed = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BackgroundTaskModelImpl</code>.
	 * </p>
	 *
	 * @param groupIds the group IDs
	 * @param taskExecutorClassNames the task executor class names
	 * @param completed the completed
	 * @param start the lower bound of the range of background tasks
	 * @param end the upper bound of the range of background tasks (not inclusive)
	 * @return the range of matching background tasks
	 */
	@Override
	public List<BackgroundTask> findByG_T_C(
		long[] groupIds, String[] taskExecutorClassNames, boolean completed,
		int start, int end) {

		return findByG_T_C(
			groupIds, taskExecutorClassNames, completed, start, end, null);
	}

	/**
	 * Returns an ordered range of all the background tasks where groupId = any &#63; and taskExecutorClassName = any &#63; and completed = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BackgroundTaskModelImpl</code>.
	 * </p>
	 *
	 * @param groupIds the group IDs
	 * @param taskExecutorClassNames the task executor class names
	 * @param completed the completed
	 * @param start the lower bound of the range of background tasks
	 * @param end the upper bound of the range of background tasks (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching background tasks
	 */
	@Override
	public List<BackgroundTask> findByG_T_C(
		long[] groupIds, String[] taskExecutorClassNames, boolean completed,
		int start, int end,
		OrderByComparator<BackgroundTask> orderByComparator) {

		return findByG_T_C(
			groupIds, taskExecutorClassNames, completed, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the background tasks where groupId = &#63; and taskExecutorClassName = &#63; and completed = &#63;, optionally using the finder cache.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BackgroundTaskModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param taskExecutorClassName the task executor class name
	 * @param completed the completed
	 * @param start the lower bound of the range of background tasks
	 * @param end the upper bound of the range of background tasks (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching background tasks
	 */
	@Override
	public List<BackgroundTask> findByG_T_C(
		long[] groupIds, String[] taskExecutorClassNames, boolean completed,
		int start, int end, OrderByComparator<BackgroundTask> orderByComparator,
		boolean useFinderCache) {

		if (groupIds == null) {
			groupIds = new long[0];
		}
		else if (groupIds.length > 1) {
			groupIds = ArrayUtil.sortedUnique(groupIds);
		}

		if (taskExecutorClassNames == null) {
			taskExecutorClassNames = new String[0];
		}
		else if (taskExecutorClassNames.length > 1) {
			for (int i = 0; i < taskExecutorClassNames.length; i++) {
				taskExecutorClassNames[i] = Objects.toString(
					taskExecutorClassNames[i], "");
			}

			taskExecutorClassNames = ArrayUtil.sortedUnique(
				taskExecutorClassNames);
		}

		if ((groupIds.length == 1) && (taskExecutorClassNames.length == 1)) {
			return findByG_T_C(
				groupIds[0], taskExecutorClassNames[0], completed, start, end,
				orderByComparator);
		}

		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderArgs = new Object[] {
					StringUtil.merge(groupIds),
					StringUtil.merge(taskExecutorClassNames), completed
				};
			}
		}
		else if (useFinderCache) {
			finderArgs = new Object[] {
				StringUtil.merge(groupIds),
				StringUtil.merge(taskExecutorClassNames), completed, start, end,
				orderByComparator
			};
		}

		List<BackgroundTask> list = null;

		if (useFinderCache) {
			list = (List<BackgroundTask>)finderCache.getResult(
				_finderPathWithPaginationFindByG_T_C, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (BackgroundTask backgroundTask : list) {
					if (!ArrayUtil.contains(
							groupIds, backgroundTask.getGroupId()) ||
						!ArrayUtil.contains(
							taskExecutorClassNames,
							backgroundTask.getTaskExecutorClassName()) ||
						(completed != backgroundTask.isCompleted())) {

						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler sb = new StringBundler();

			sb.append(_SQL_SELECT_BACKGROUNDTASK_WHERE);

			if (groupIds.length > 0) {
				sb.append("(");

				sb.append(_FINDER_COLUMN_G_T_C_GROUPID_7);

				sb.append(StringUtil.merge(groupIds));

				sb.append(")");

				sb.append(")");

				sb.append(WHERE_AND);
			}

			if (taskExecutorClassNames.length > 0) {
				sb.append("(");

				for (int i = 0; i < taskExecutorClassNames.length; i++) {
					String taskExecutorClassName = taskExecutorClassNames[i];

					if (taskExecutorClassName.isEmpty()) {
						sb.append(_FINDER_COLUMN_G_T_C_TASKEXECUTORCLASSNAME_6);
					}
					else {
						sb.append(_FINDER_COLUMN_G_T_C_TASKEXECUTORCLASSNAME_5);
					}

					if ((i + 1) < taskExecutorClassNames.length) {
						sb.append(WHERE_OR);
					}
				}

				sb.append(")");

				sb.append(WHERE_AND);
			}

			sb.append(_FINDER_COLUMN_G_T_C_COMPLETED_2);

			sb.setStringAt(
				removeConjunction(sb.stringAt(sb.index() - 1)), sb.index() - 1);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(BackgroundTaskModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				for (String taskExecutorClassName : taskExecutorClassNames) {
					if ((taskExecutorClassName != null) &&
						!taskExecutorClassName.isEmpty()) {

						queryPos.add(taskExecutorClassName);
					}
				}

				queryPos.add(completed);

				list = (List<BackgroundTask>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					finderCache.putResult(
						_finderPathWithPaginationFindByG_T_C, finderArgs, list);
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
	 * Removes all the background tasks where groupId = &#63; and taskExecutorClassName = &#63; and completed = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param taskExecutorClassName the task executor class name
	 * @param completed the completed
	 */
	@Override
	public void removeByG_T_C(
		long groupId, String taskExecutorClassName, boolean completed) {

		for (BackgroundTask backgroundTask :
				findByG_T_C(
					groupId, taskExecutorClassName, completed,
					QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(backgroundTask);
		}
	}

	/**
	 * Returns the number of background tasks where groupId = &#63; and taskExecutorClassName = &#63; and completed = &#63;.
	 *
	 * @param groupId the group ID
	 * @param taskExecutorClassName the task executor class name
	 * @param completed the completed
	 * @return the number of matching background tasks
	 */
	@Override
	public int countByG_T_C(
		long groupId, String taskExecutorClassName, boolean completed) {

		taskExecutorClassName = Objects.toString(taskExecutorClassName, "");

		FinderPath finderPath = _finderPathCountByG_T_C;

		Object[] finderArgs = new Object[] {
			groupId, taskExecutorClassName, completed
		};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_COUNT_BACKGROUNDTASK_WHERE);

			sb.append(_FINDER_COLUMN_G_T_C_GROUPID_2);

			boolean bindTaskExecutorClassName = false;

			if (taskExecutorClassName.isEmpty()) {
				sb.append(_FINDER_COLUMN_G_T_C_TASKEXECUTORCLASSNAME_3);
			}
			else {
				bindTaskExecutorClassName = true;

				sb.append(_FINDER_COLUMN_G_T_C_TASKEXECUTORCLASSNAME_2);
			}

			sb.append(_FINDER_COLUMN_G_T_C_COMPLETED_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				if (bindTaskExecutorClassName) {
					queryPos.add(taskExecutorClassName);
				}

				queryPos.add(completed);

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

	/**
	 * Returns the number of background tasks where groupId = any &#63; and taskExecutorClassName = any &#63; and completed = &#63;.
	 *
	 * @param groupIds the group IDs
	 * @param taskExecutorClassNames the task executor class names
	 * @param completed the completed
	 * @return the number of matching background tasks
	 */
	@Override
	public int countByG_T_C(
		long[] groupIds, String[] taskExecutorClassNames, boolean completed) {

		if (groupIds == null) {
			groupIds = new long[0];
		}
		else if (groupIds.length > 1) {
			groupIds = ArrayUtil.sortedUnique(groupIds);
		}

		if (taskExecutorClassNames == null) {
			taskExecutorClassNames = new String[0];
		}
		else if (taskExecutorClassNames.length > 1) {
			for (int i = 0; i < taskExecutorClassNames.length; i++) {
				taskExecutorClassNames[i] = Objects.toString(
					taskExecutorClassNames[i], "");
			}

			taskExecutorClassNames = ArrayUtil.sortedUnique(
				taskExecutorClassNames);
		}

		Object[] finderArgs = new Object[] {
			StringUtil.merge(groupIds),
			StringUtil.merge(taskExecutorClassNames), completed
		};

		Long count = (Long)finderCache.getResult(
			_finderPathWithPaginationCountByG_T_C, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler();

			sb.append(_SQL_COUNT_BACKGROUNDTASK_WHERE);

			if (groupIds.length > 0) {
				sb.append("(");

				sb.append(_FINDER_COLUMN_G_T_C_GROUPID_7);

				sb.append(StringUtil.merge(groupIds));

				sb.append(")");

				sb.append(")");

				sb.append(WHERE_AND);
			}

			if (taskExecutorClassNames.length > 0) {
				sb.append("(");

				for (int i = 0; i < taskExecutorClassNames.length; i++) {
					String taskExecutorClassName = taskExecutorClassNames[i];

					if (taskExecutorClassName.isEmpty()) {
						sb.append(_FINDER_COLUMN_G_T_C_TASKEXECUTORCLASSNAME_6);
					}
					else {
						sb.append(_FINDER_COLUMN_G_T_C_TASKEXECUTORCLASSNAME_5);
					}

					if ((i + 1) < taskExecutorClassNames.length) {
						sb.append(WHERE_OR);
					}
				}

				sb.append(")");

				sb.append(WHERE_AND);
			}

			sb.append(_FINDER_COLUMN_G_T_C_COMPLETED_2);

			sb.setStringAt(
				removeConjunction(sb.stringAt(sb.index() - 1)), sb.index() - 1);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				for (String taskExecutorClassName : taskExecutorClassNames) {
					if ((taskExecutorClassName != null) &&
						!taskExecutorClassName.isEmpty()) {

						queryPos.add(taskExecutorClassName);
					}
				}

				queryPos.add(completed);

				count = (Long)query.uniqueResult();

				finderCache.putResult(
					_finderPathWithPaginationCountByG_T_C, finderArgs, count);
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

	private static final String _FINDER_COLUMN_G_T_C_GROUPID_2 =
		"backgroundTask.groupId = ? AND ";

	private static final String _FINDER_COLUMN_G_T_C_GROUPID_7 =
		"backgroundTask.groupId IN (";

	private static final String _FINDER_COLUMN_G_T_C_TASKEXECUTORCLASSNAME_2 =
		"backgroundTask.taskExecutorClassName = ? AND ";

	private static final String _FINDER_COLUMN_G_T_C_TASKEXECUTORCLASSNAME_3 =
		"(backgroundTask.taskExecutorClassName IS NULL OR backgroundTask.taskExecutorClassName = '') AND ";

	private static final String _FINDER_COLUMN_G_T_C_TASKEXECUTORCLASSNAME_5 =
		"(" + removeConjunction(_FINDER_COLUMN_G_T_C_TASKEXECUTORCLASSNAME_2) +
			")";

	private static final String _FINDER_COLUMN_G_T_C_TASKEXECUTORCLASSNAME_6 =
		"(" + removeConjunction(_FINDER_COLUMN_G_T_C_TASKEXECUTORCLASSNAME_3) +
			")";

	private static final String _FINDER_COLUMN_G_T_C_COMPLETED_2 =
		"backgroundTask.completed = ?";

	private FinderPath _finderPathWithPaginationFindByG_T_S;
	private FinderPath _finderPathWithoutPaginationFindByG_T_S;
	private FinderPath _finderPathCountByG_T_S;
	private FinderPath _finderPathWithPaginationCountByG_T_S;

	/**
	 * Returns all the background tasks where groupId = &#63; and taskExecutorClassName = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param taskExecutorClassName the task executor class name
	 * @param status the status
	 * @return the matching background tasks
	 */
	@Override
	public List<BackgroundTask> findByG_T_S(
		long groupId, String taskExecutorClassName, int status) {

		return findByG_T_S(
			groupId, taskExecutorClassName, status, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the background tasks where groupId = &#63; and taskExecutorClassName = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BackgroundTaskModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param taskExecutorClassName the task executor class name
	 * @param status the status
	 * @param start the lower bound of the range of background tasks
	 * @param end the upper bound of the range of background tasks (not inclusive)
	 * @return the range of matching background tasks
	 */
	@Override
	public List<BackgroundTask> findByG_T_S(
		long groupId, String taskExecutorClassName, int status, int start,
		int end) {

		return findByG_T_S(
			groupId, taskExecutorClassName, status, start, end, null);
	}

	/**
	 * Returns an ordered range of all the background tasks where groupId = &#63; and taskExecutorClassName = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BackgroundTaskModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param taskExecutorClassName the task executor class name
	 * @param status the status
	 * @param start the lower bound of the range of background tasks
	 * @param end the upper bound of the range of background tasks (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching background tasks
	 */
	@Override
	public List<BackgroundTask> findByG_T_S(
		long groupId, String taskExecutorClassName, int status, int start,
		int end, OrderByComparator<BackgroundTask> orderByComparator) {

		return findByG_T_S(
			groupId, taskExecutorClassName, status, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the background tasks where groupId = &#63; and taskExecutorClassName = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BackgroundTaskModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param taskExecutorClassName the task executor class name
	 * @param status the status
	 * @param start the lower bound of the range of background tasks
	 * @param end the upper bound of the range of background tasks (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching background tasks
	 */
	@Override
	public List<BackgroundTask> findByG_T_S(
		long groupId, String taskExecutorClassName, int status, int start,
		int end, OrderByComparator<BackgroundTask> orderByComparator,
		boolean useFinderCache) {

		taskExecutorClassName = Objects.toString(taskExecutorClassName, "");

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByG_T_S;
				finderArgs = new Object[] {
					groupId, taskExecutorClassName, status
				};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByG_T_S;
			finderArgs = new Object[] {
				groupId, taskExecutorClassName, status, start, end,
				orderByComparator
			};
		}

		List<BackgroundTask> list = null;

		if (useFinderCache) {
			list = (List<BackgroundTask>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (BackgroundTask backgroundTask : list) {
					if ((groupId != backgroundTask.getGroupId()) ||
						!taskExecutorClassName.equals(
							backgroundTask.getTaskExecutorClassName()) ||
						(status != backgroundTask.getStatus())) {

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
					5 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				sb = new StringBundler(5);
			}

			sb.append(_SQL_SELECT_BACKGROUNDTASK_WHERE);

			sb.append(_FINDER_COLUMN_G_T_S_GROUPID_2);

			boolean bindTaskExecutorClassName = false;

			if (taskExecutorClassName.isEmpty()) {
				sb.append(_FINDER_COLUMN_G_T_S_TASKEXECUTORCLASSNAME_3);
			}
			else {
				bindTaskExecutorClassName = true;

				sb.append(_FINDER_COLUMN_G_T_S_TASKEXECUTORCLASSNAME_2);
			}

			sb.append(_FINDER_COLUMN_G_T_S_STATUS_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(BackgroundTaskModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				if (bindTaskExecutorClassName) {
					queryPos.add(taskExecutorClassName);
				}

				queryPos.add(status);

				list = (List<BackgroundTask>)QueryUtil.list(
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
	 * Returns the first background task in the ordered set where groupId = &#63; and taskExecutorClassName = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param taskExecutorClassName the task executor class name
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching background task
	 * @throws NoSuchBackgroundTaskException if a matching background task could not be found
	 */
	@Override
	public BackgroundTask findByG_T_S_First(
			long groupId, String taskExecutorClassName, int status,
			OrderByComparator<BackgroundTask> orderByComparator)
		throws NoSuchBackgroundTaskException {

		BackgroundTask backgroundTask = fetchByG_T_S_First(
			groupId, taskExecutorClassName, status, orderByComparator);

		if (backgroundTask != null) {
			return backgroundTask;
		}

		StringBundler sb = new StringBundler(8);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", taskExecutorClassName=");
		sb.append(taskExecutorClassName);

		sb.append(", status=");
		sb.append(status);

		sb.append("}");

		throw new NoSuchBackgroundTaskException(sb.toString());
	}

	/**
	 * Returns the first background task in the ordered set where groupId = &#63; and taskExecutorClassName = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param taskExecutorClassName the task executor class name
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching background task, or <code>null</code> if a matching background task could not be found
	 */
	@Override
	public BackgroundTask fetchByG_T_S_First(
		long groupId, String taskExecutorClassName, int status,
		OrderByComparator<BackgroundTask> orderByComparator) {

		List<BackgroundTask> list = findByG_T_S(
			groupId, taskExecutorClassName, status, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last background task in the ordered set where groupId = &#63; and taskExecutorClassName = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param taskExecutorClassName the task executor class name
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching background task
	 * @throws NoSuchBackgroundTaskException if a matching background task could not be found
	 */
	@Override
	public BackgroundTask findByG_T_S_Last(
			long groupId, String taskExecutorClassName, int status,
			OrderByComparator<BackgroundTask> orderByComparator)
		throws NoSuchBackgroundTaskException {

		BackgroundTask backgroundTask = fetchByG_T_S_Last(
			groupId, taskExecutorClassName, status, orderByComparator);

		if (backgroundTask != null) {
			return backgroundTask;
		}

		StringBundler sb = new StringBundler(8);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", taskExecutorClassName=");
		sb.append(taskExecutorClassName);

		sb.append(", status=");
		sb.append(status);

		sb.append("}");

		throw new NoSuchBackgroundTaskException(sb.toString());
	}

	/**
	 * Returns the last background task in the ordered set where groupId = &#63; and taskExecutorClassName = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param taskExecutorClassName the task executor class name
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching background task, or <code>null</code> if a matching background task could not be found
	 */
	@Override
	public BackgroundTask fetchByG_T_S_Last(
		long groupId, String taskExecutorClassName, int status,
		OrderByComparator<BackgroundTask> orderByComparator) {

		int count = countByG_T_S(groupId, taskExecutorClassName, status);

		if (count == 0) {
			return null;
		}

		List<BackgroundTask> list = findByG_T_S(
			groupId, taskExecutorClassName, status, count - 1, count,
			orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the background tasks before and after the current background task in the ordered set where groupId = &#63; and taskExecutorClassName = &#63; and status = &#63;.
	 *
	 * @param backgroundTaskId the primary key of the current background task
	 * @param groupId the group ID
	 * @param taskExecutorClassName the task executor class name
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next background task
	 * @throws NoSuchBackgroundTaskException if a background task with the primary key could not be found
	 */
	@Override
	public BackgroundTask[] findByG_T_S_PrevAndNext(
			long backgroundTaskId, long groupId, String taskExecutorClassName,
			int status, OrderByComparator<BackgroundTask> orderByComparator)
		throws NoSuchBackgroundTaskException {

		taskExecutorClassName = Objects.toString(taskExecutorClassName, "");

		BackgroundTask backgroundTask = findByPrimaryKey(backgroundTaskId);

		Session session = null;

		try {
			session = openSession();

			BackgroundTask[] array = new BackgroundTaskImpl[3];

			array[0] = getByG_T_S_PrevAndNext(
				session, backgroundTask, groupId, taskExecutorClassName, status,
				orderByComparator, true);

			array[1] = backgroundTask;

			array[2] = getByG_T_S_PrevAndNext(
				session, backgroundTask, groupId, taskExecutorClassName, status,
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

	protected BackgroundTask getByG_T_S_PrevAndNext(
		Session session, BackgroundTask backgroundTask, long groupId,
		String taskExecutorClassName, int status,
		OrderByComparator<BackgroundTask> orderByComparator, boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				6 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(5);
		}

		sb.append(_SQL_SELECT_BACKGROUNDTASK_WHERE);

		sb.append(_FINDER_COLUMN_G_T_S_GROUPID_2);

		boolean bindTaskExecutorClassName = false;

		if (taskExecutorClassName.isEmpty()) {
			sb.append(_FINDER_COLUMN_G_T_S_TASKEXECUTORCLASSNAME_3);
		}
		else {
			bindTaskExecutorClassName = true;

			sb.append(_FINDER_COLUMN_G_T_S_TASKEXECUTORCLASSNAME_2);
		}

		sb.append(_FINDER_COLUMN_G_T_S_STATUS_2);

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
			sb.append(BackgroundTaskModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(groupId);

		if (bindTaskExecutorClassName) {
			queryPos.add(taskExecutorClassName);
		}

		queryPos.add(status);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						backgroundTask)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<BackgroundTask> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the background tasks where groupId = &#63; and taskExecutorClassName = any &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BackgroundTaskModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param taskExecutorClassNames the task executor class names
	 * @param status the status
	 * @return the matching background tasks
	 */
	@Override
	public List<BackgroundTask> findByG_T_S(
		long groupId, String[] taskExecutorClassNames, int status) {

		return findByG_T_S(
			groupId, taskExecutorClassNames, status, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the background tasks where groupId = &#63; and taskExecutorClassName = any &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BackgroundTaskModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param taskExecutorClassNames the task executor class names
	 * @param status the status
	 * @param start the lower bound of the range of background tasks
	 * @param end the upper bound of the range of background tasks (not inclusive)
	 * @return the range of matching background tasks
	 */
	@Override
	public List<BackgroundTask> findByG_T_S(
		long groupId, String[] taskExecutorClassNames, int status, int start,
		int end) {

		return findByG_T_S(
			groupId, taskExecutorClassNames, status, start, end, null);
	}

	/**
	 * Returns an ordered range of all the background tasks where groupId = &#63; and taskExecutorClassName = any &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BackgroundTaskModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param taskExecutorClassNames the task executor class names
	 * @param status the status
	 * @param start the lower bound of the range of background tasks
	 * @param end the upper bound of the range of background tasks (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching background tasks
	 */
	@Override
	public List<BackgroundTask> findByG_T_S(
		long groupId, String[] taskExecutorClassNames, int status, int start,
		int end, OrderByComparator<BackgroundTask> orderByComparator) {

		return findByG_T_S(
			groupId, taskExecutorClassNames, status, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the background tasks where groupId = &#63; and taskExecutorClassName = &#63; and status = &#63;, optionally using the finder cache.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BackgroundTaskModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param taskExecutorClassName the task executor class name
	 * @param status the status
	 * @param start the lower bound of the range of background tasks
	 * @param end the upper bound of the range of background tasks (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching background tasks
	 */
	@Override
	public List<BackgroundTask> findByG_T_S(
		long groupId, String[] taskExecutorClassNames, int status, int start,
		int end, OrderByComparator<BackgroundTask> orderByComparator,
		boolean useFinderCache) {

		if (taskExecutorClassNames == null) {
			taskExecutorClassNames = new String[0];
		}
		else if (taskExecutorClassNames.length > 1) {
			for (int i = 0; i < taskExecutorClassNames.length; i++) {
				taskExecutorClassNames[i] = Objects.toString(
					taskExecutorClassNames[i], "");
			}

			taskExecutorClassNames = ArrayUtil.sortedUnique(
				taskExecutorClassNames);
		}

		if (taskExecutorClassNames.length == 1) {
			return findByG_T_S(
				groupId, taskExecutorClassNames[0], status, start, end,
				orderByComparator);
		}

		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderArgs = new Object[] {
					groupId, StringUtil.merge(taskExecutorClassNames), status
				};
			}
		}
		else if (useFinderCache) {
			finderArgs = new Object[] {
				groupId, StringUtil.merge(taskExecutorClassNames), status,
				start, end, orderByComparator
			};
		}

		List<BackgroundTask> list = null;

		if (useFinderCache) {
			list = (List<BackgroundTask>)finderCache.getResult(
				_finderPathWithPaginationFindByG_T_S, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (BackgroundTask backgroundTask : list) {
					if ((groupId != backgroundTask.getGroupId()) ||
						!ArrayUtil.contains(
							taskExecutorClassNames,
							backgroundTask.getTaskExecutorClassName()) ||
						(status != backgroundTask.getStatus())) {

						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler sb = new StringBundler();

			sb.append(_SQL_SELECT_BACKGROUNDTASK_WHERE);

			sb.append(_FINDER_COLUMN_G_T_S_GROUPID_2);

			if (taskExecutorClassNames.length > 0) {
				sb.append("(");

				for (int i = 0; i < taskExecutorClassNames.length; i++) {
					String taskExecutorClassName = taskExecutorClassNames[i];

					if (taskExecutorClassName.isEmpty()) {
						sb.append(_FINDER_COLUMN_G_T_S_TASKEXECUTORCLASSNAME_6);
					}
					else {
						sb.append(_FINDER_COLUMN_G_T_S_TASKEXECUTORCLASSNAME_5);
					}

					if ((i + 1) < taskExecutorClassNames.length) {
						sb.append(WHERE_OR);
					}
				}

				sb.append(")");

				sb.append(WHERE_AND);
			}

			sb.append(_FINDER_COLUMN_G_T_S_STATUS_2);

			sb.setStringAt(
				removeConjunction(sb.stringAt(sb.index() - 1)), sb.index() - 1);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(BackgroundTaskModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				for (String taskExecutorClassName : taskExecutorClassNames) {
					if ((taskExecutorClassName != null) &&
						!taskExecutorClassName.isEmpty()) {

						queryPos.add(taskExecutorClassName);
					}
				}

				queryPos.add(status);

				list = (List<BackgroundTask>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					finderCache.putResult(
						_finderPathWithPaginationFindByG_T_S, finderArgs, list);
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
	 * Removes all the background tasks where groupId = &#63; and taskExecutorClassName = &#63; and status = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param taskExecutorClassName the task executor class name
	 * @param status the status
	 */
	@Override
	public void removeByG_T_S(
		long groupId, String taskExecutorClassName, int status) {

		for (BackgroundTask backgroundTask :
				findByG_T_S(
					groupId, taskExecutorClassName, status, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			remove(backgroundTask);
		}
	}

	/**
	 * Returns the number of background tasks where groupId = &#63; and taskExecutorClassName = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param taskExecutorClassName the task executor class name
	 * @param status the status
	 * @return the number of matching background tasks
	 */
	@Override
	public int countByG_T_S(
		long groupId, String taskExecutorClassName, int status) {

		taskExecutorClassName = Objects.toString(taskExecutorClassName, "");

		FinderPath finderPath = _finderPathCountByG_T_S;

		Object[] finderArgs = new Object[] {
			groupId, taskExecutorClassName, status
		};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_COUNT_BACKGROUNDTASK_WHERE);

			sb.append(_FINDER_COLUMN_G_T_S_GROUPID_2);

			boolean bindTaskExecutorClassName = false;

			if (taskExecutorClassName.isEmpty()) {
				sb.append(_FINDER_COLUMN_G_T_S_TASKEXECUTORCLASSNAME_3);
			}
			else {
				bindTaskExecutorClassName = true;

				sb.append(_FINDER_COLUMN_G_T_S_TASKEXECUTORCLASSNAME_2);
			}

			sb.append(_FINDER_COLUMN_G_T_S_STATUS_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				if (bindTaskExecutorClassName) {
					queryPos.add(taskExecutorClassName);
				}

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

	/**
	 * Returns the number of background tasks where groupId = &#63; and taskExecutorClassName = any &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param taskExecutorClassNames the task executor class names
	 * @param status the status
	 * @return the number of matching background tasks
	 */
	@Override
	public int countByG_T_S(
		long groupId, String[] taskExecutorClassNames, int status) {

		if (taskExecutorClassNames == null) {
			taskExecutorClassNames = new String[0];
		}
		else if (taskExecutorClassNames.length > 1) {
			for (int i = 0; i < taskExecutorClassNames.length; i++) {
				taskExecutorClassNames[i] = Objects.toString(
					taskExecutorClassNames[i], "");
			}

			taskExecutorClassNames = ArrayUtil.sortedUnique(
				taskExecutorClassNames);
		}

		Object[] finderArgs = new Object[] {
			groupId, StringUtil.merge(taskExecutorClassNames), status
		};

		Long count = (Long)finderCache.getResult(
			_finderPathWithPaginationCountByG_T_S, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler();

			sb.append(_SQL_COUNT_BACKGROUNDTASK_WHERE);

			sb.append(_FINDER_COLUMN_G_T_S_GROUPID_2);

			if (taskExecutorClassNames.length > 0) {
				sb.append("(");

				for (int i = 0; i < taskExecutorClassNames.length; i++) {
					String taskExecutorClassName = taskExecutorClassNames[i];

					if (taskExecutorClassName.isEmpty()) {
						sb.append(_FINDER_COLUMN_G_T_S_TASKEXECUTORCLASSNAME_6);
					}
					else {
						sb.append(_FINDER_COLUMN_G_T_S_TASKEXECUTORCLASSNAME_5);
					}

					if ((i + 1) < taskExecutorClassNames.length) {
						sb.append(WHERE_OR);
					}
				}

				sb.append(")");

				sb.append(WHERE_AND);
			}

			sb.append(_FINDER_COLUMN_G_T_S_STATUS_2);

			sb.setStringAt(
				removeConjunction(sb.stringAt(sb.index() - 1)), sb.index() - 1);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				for (String taskExecutorClassName : taskExecutorClassNames) {
					if ((taskExecutorClassName != null) &&
						!taskExecutorClassName.isEmpty()) {

						queryPos.add(taskExecutorClassName);
					}
				}

				queryPos.add(status);

				count = (Long)query.uniqueResult();

				finderCache.putResult(
					_finderPathWithPaginationCountByG_T_S, finderArgs, count);
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

	private static final String _FINDER_COLUMN_G_T_S_GROUPID_2 =
		"backgroundTask.groupId = ? AND ";

	private static final String _FINDER_COLUMN_G_T_S_TASKEXECUTORCLASSNAME_2 =
		"backgroundTask.taskExecutorClassName = ? AND ";

	private static final String _FINDER_COLUMN_G_T_S_TASKEXECUTORCLASSNAME_3 =
		"(backgroundTask.taskExecutorClassName IS NULL OR backgroundTask.taskExecutorClassName = '') AND ";

	private static final String _FINDER_COLUMN_G_T_S_TASKEXECUTORCLASSNAME_5 =
		"(" + removeConjunction(_FINDER_COLUMN_G_T_S_TASKEXECUTORCLASSNAME_2) +
			")";

	private static final String _FINDER_COLUMN_G_T_S_TASKEXECUTORCLASSNAME_6 =
		"(" + removeConjunction(_FINDER_COLUMN_G_T_S_TASKEXECUTORCLASSNAME_3) +
			")";

	private static final String _FINDER_COLUMN_G_T_S_STATUS_2 =
		"backgroundTask.status = ?";

	private FinderPath _finderPathWithPaginationFindByG_N_T_C;
	private FinderPath _finderPathWithoutPaginationFindByG_N_T_C;
	private FinderPath _finderPathCountByG_N_T_C;
	private FinderPath _finderPathWithPaginationCountByG_N_T_C;

	/**
	 * Returns all the background tasks where groupId = &#63; and name = &#63; and taskExecutorClassName = &#63; and completed = &#63;.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param taskExecutorClassName the task executor class name
	 * @param completed the completed
	 * @return the matching background tasks
	 */
	@Override
	public List<BackgroundTask> findByG_N_T_C(
		long groupId, String name, String taskExecutorClassName,
		boolean completed) {

		return findByG_N_T_C(
			groupId, name, taskExecutorClassName, completed, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the background tasks where groupId = &#63; and name = &#63; and taskExecutorClassName = &#63; and completed = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BackgroundTaskModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param taskExecutorClassName the task executor class name
	 * @param completed the completed
	 * @param start the lower bound of the range of background tasks
	 * @param end the upper bound of the range of background tasks (not inclusive)
	 * @return the range of matching background tasks
	 */
	@Override
	public List<BackgroundTask> findByG_N_T_C(
		long groupId, String name, String taskExecutorClassName,
		boolean completed, int start, int end) {

		return findByG_N_T_C(
			groupId, name, taskExecutorClassName, completed, start, end, null);
	}

	/**
	 * Returns an ordered range of all the background tasks where groupId = &#63; and name = &#63; and taskExecutorClassName = &#63; and completed = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BackgroundTaskModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param taskExecutorClassName the task executor class name
	 * @param completed the completed
	 * @param start the lower bound of the range of background tasks
	 * @param end the upper bound of the range of background tasks (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching background tasks
	 */
	@Override
	public List<BackgroundTask> findByG_N_T_C(
		long groupId, String name, String taskExecutorClassName,
		boolean completed, int start, int end,
		OrderByComparator<BackgroundTask> orderByComparator) {

		return findByG_N_T_C(
			groupId, name, taskExecutorClassName, completed, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the background tasks where groupId = &#63; and name = &#63; and taskExecutorClassName = &#63; and completed = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BackgroundTaskModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param taskExecutorClassName the task executor class name
	 * @param completed the completed
	 * @param start the lower bound of the range of background tasks
	 * @param end the upper bound of the range of background tasks (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching background tasks
	 */
	@Override
	public List<BackgroundTask> findByG_N_T_C(
		long groupId, String name, String taskExecutorClassName,
		boolean completed, int start, int end,
		OrderByComparator<BackgroundTask> orderByComparator,
		boolean useFinderCache) {

		name = Objects.toString(name, "");
		taskExecutorClassName = Objects.toString(taskExecutorClassName, "");

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByG_N_T_C;
				finderArgs = new Object[] {
					groupId, name, taskExecutorClassName, completed
				};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByG_N_T_C;
			finderArgs = new Object[] {
				groupId, name, taskExecutorClassName, completed, start, end,
				orderByComparator
			};
		}

		List<BackgroundTask> list = null;

		if (useFinderCache) {
			list = (List<BackgroundTask>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (BackgroundTask backgroundTask : list) {
					if ((groupId != backgroundTask.getGroupId()) ||
						!name.equals(backgroundTask.getName()) ||
						!taskExecutorClassName.equals(
							backgroundTask.getTaskExecutorClassName()) ||
						(completed != backgroundTask.isCompleted())) {

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
					6 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				sb = new StringBundler(6);
			}

			sb.append(_SQL_SELECT_BACKGROUNDTASK_WHERE);

			sb.append(_FINDER_COLUMN_G_N_T_C_GROUPID_2);

			boolean bindName = false;

			if (name.isEmpty()) {
				sb.append(_FINDER_COLUMN_G_N_T_C_NAME_3);
			}
			else {
				bindName = true;

				sb.append(_FINDER_COLUMN_G_N_T_C_NAME_2);
			}

			boolean bindTaskExecutorClassName = false;

			if (taskExecutorClassName.isEmpty()) {
				sb.append(_FINDER_COLUMN_G_N_T_C_TASKEXECUTORCLASSNAME_3);
			}
			else {
				bindTaskExecutorClassName = true;

				sb.append(_FINDER_COLUMN_G_N_T_C_TASKEXECUTORCLASSNAME_2);
			}

			sb.append(_FINDER_COLUMN_G_N_T_C_COMPLETED_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(BackgroundTaskModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				if (bindName) {
					queryPos.add(name);
				}

				if (bindTaskExecutorClassName) {
					queryPos.add(taskExecutorClassName);
				}

				queryPos.add(completed);

				list = (List<BackgroundTask>)QueryUtil.list(
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
	 * Returns the first background task in the ordered set where groupId = &#63; and name = &#63; and taskExecutorClassName = &#63; and completed = &#63;.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param taskExecutorClassName the task executor class name
	 * @param completed the completed
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching background task
	 * @throws NoSuchBackgroundTaskException if a matching background task could not be found
	 */
	@Override
	public BackgroundTask findByG_N_T_C_First(
			long groupId, String name, String taskExecutorClassName,
			boolean completed,
			OrderByComparator<BackgroundTask> orderByComparator)
		throws NoSuchBackgroundTaskException {

		BackgroundTask backgroundTask = fetchByG_N_T_C_First(
			groupId, name, taskExecutorClassName, completed, orderByComparator);

		if (backgroundTask != null) {
			return backgroundTask;
		}

		StringBundler sb = new StringBundler(10);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", name=");
		sb.append(name);

		sb.append(", taskExecutorClassName=");
		sb.append(taskExecutorClassName);

		sb.append(", completed=");
		sb.append(completed);

		sb.append("}");

		throw new NoSuchBackgroundTaskException(sb.toString());
	}

	/**
	 * Returns the first background task in the ordered set where groupId = &#63; and name = &#63; and taskExecutorClassName = &#63; and completed = &#63;.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param taskExecutorClassName the task executor class name
	 * @param completed the completed
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching background task, or <code>null</code> if a matching background task could not be found
	 */
	@Override
	public BackgroundTask fetchByG_N_T_C_First(
		long groupId, String name, String taskExecutorClassName,
		boolean completed,
		OrderByComparator<BackgroundTask> orderByComparator) {

		List<BackgroundTask> list = findByG_N_T_C(
			groupId, name, taskExecutorClassName, completed, 0, 1,
			orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last background task in the ordered set where groupId = &#63; and name = &#63; and taskExecutorClassName = &#63; and completed = &#63;.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param taskExecutorClassName the task executor class name
	 * @param completed the completed
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching background task
	 * @throws NoSuchBackgroundTaskException if a matching background task could not be found
	 */
	@Override
	public BackgroundTask findByG_N_T_C_Last(
			long groupId, String name, String taskExecutorClassName,
			boolean completed,
			OrderByComparator<BackgroundTask> orderByComparator)
		throws NoSuchBackgroundTaskException {

		BackgroundTask backgroundTask = fetchByG_N_T_C_Last(
			groupId, name, taskExecutorClassName, completed, orderByComparator);

		if (backgroundTask != null) {
			return backgroundTask;
		}

		StringBundler sb = new StringBundler(10);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", name=");
		sb.append(name);

		sb.append(", taskExecutorClassName=");
		sb.append(taskExecutorClassName);

		sb.append(", completed=");
		sb.append(completed);

		sb.append("}");

		throw new NoSuchBackgroundTaskException(sb.toString());
	}

	/**
	 * Returns the last background task in the ordered set where groupId = &#63; and name = &#63; and taskExecutorClassName = &#63; and completed = &#63;.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param taskExecutorClassName the task executor class name
	 * @param completed the completed
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching background task, or <code>null</code> if a matching background task could not be found
	 */
	@Override
	public BackgroundTask fetchByG_N_T_C_Last(
		long groupId, String name, String taskExecutorClassName,
		boolean completed,
		OrderByComparator<BackgroundTask> orderByComparator) {

		int count = countByG_N_T_C(
			groupId, name, taskExecutorClassName, completed);

		if (count == 0) {
			return null;
		}

		List<BackgroundTask> list = findByG_N_T_C(
			groupId, name, taskExecutorClassName, completed, count - 1, count,
			orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the background tasks before and after the current background task in the ordered set where groupId = &#63; and name = &#63; and taskExecutorClassName = &#63; and completed = &#63;.
	 *
	 * @param backgroundTaskId the primary key of the current background task
	 * @param groupId the group ID
	 * @param name the name
	 * @param taskExecutorClassName the task executor class name
	 * @param completed the completed
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next background task
	 * @throws NoSuchBackgroundTaskException if a background task with the primary key could not be found
	 */
	@Override
	public BackgroundTask[] findByG_N_T_C_PrevAndNext(
			long backgroundTaskId, long groupId, String name,
			String taskExecutorClassName, boolean completed,
			OrderByComparator<BackgroundTask> orderByComparator)
		throws NoSuchBackgroundTaskException {

		name = Objects.toString(name, "");
		taskExecutorClassName = Objects.toString(taskExecutorClassName, "");

		BackgroundTask backgroundTask = findByPrimaryKey(backgroundTaskId);

		Session session = null;

		try {
			session = openSession();

			BackgroundTask[] array = new BackgroundTaskImpl[3];

			array[0] = getByG_N_T_C_PrevAndNext(
				session, backgroundTask, groupId, name, taskExecutorClassName,
				completed, orderByComparator, true);

			array[1] = backgroundTask;

			array[2] = getByG_N_T_C_PrevAndNext(
				session, backgroundTask, groupId, name, taskExecutorClassName,
				completed, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected BackgroundTask getByG_N_T_C_PrevAndNext(
		Session session, BackgroundTask backgroundTask, long groupId,
		String name, String taskExecutorClassName, boolean completed,
		OrderByComparator<BackgroundTask> orderByComparator, boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				7 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(6);
		}

		sb.append(_SQL_SELECT_BACKGROUNDTASK_WHERE);

		sb.append(_FINDER_COLUMN_G_N_T_C_GROUPID_2);

		boolean bindName = false;

		if (name.isEmpty()) {
			sb.append(_FINDER_COLUMN_G_N_T_C_NAME_3);
		}
		else {
			bindName = true;

			sb.append(_FINDER_COLUMN_G_N_T_C_NAME_2);
		}

		boolean bindTaskExecutorClassName = false;

		if (taskExecutorClassName.isEmpty()) {
			sb.append(_FINDER_COLUMN_G_N_T_C_TASKEXECUTORCLASSNAME_3);
		}
		else {
			bindTaskExecutorClassName = true;

			sb.append(_FINDER_COLUMN_G_N_T_C_TASKEXECUTORCLASSNAME_2);
		}

		sb.append(_FINDER_COLUMN_G_N_T_C_COMPLETED_2);

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
			sb.append(BackgroundTaskModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(groupId);

		if (bindName) {
			queryPos.add(name);
		}

		if (bindTaskExecutorClassName) {
			queryPos.add(taskExecutorClassName);
		}

		queryPos.add(completed);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						backgroundTask)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<BackgroundTask> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the background tasks where groupId = any &#63; and name = &#63; and taskExecutorClassName = &#63; and completed = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BackgroundTaskModelImpl</code>.
	 * </p>
	 *
	 * @param groupIds the group IDs
	 * @param name the name
	 * @param taskExecutorClassName the task executor class name
	 * @param completed the completed
	 * @return the matching background tasks
	 */
	@Override
	public List<BackgroundTask> findByG_N_T_C(
		long[] groupIds, String name, String taskExecutorClassName,
		boolean completed) {

		return findByG_N_T_C(
			groupIds, name, taskExecutorClassName, completed, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the background tasks where groupId = any &#63; and name = &#63; and taskExecutorClassName = &#63; and completed = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BackgroundTaskModelImpl</code>.
	 * </p>
	 *
	 * @param groupIds the group IDs
	 * @param name the name
	 * @param taskExecutorClassName the task executor class name
	 * @param completed the completed
	 * @param start the lower bound of the range of background tasks
	 * @param end the upper bound of the range of background tasks (not inclusive)
	 * @return the range of matching background tasks
	 */
	@Override
	public List<BackgroundTask> findByG_N_T_C(
		long[] groupIds, String name, String taskExecutorClassName,
		boolean completed, int start, int end) {

		return findByG_N_T_C(
			groupIds, name, taskExecutorClassName, completed, start, end, null);
	}

	/**
	 * Returns an ordered range of all the background tasks where groupId = any &#63; and name = &#63; and taskExecutorClassName = &#63; and completed = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BackgroundTaskModelImpl</code>.
	 * </p>
	 *
	 * @param groupIds the group IDs
	 * @param name the name
	 * @param taskExecutorClassName the task executor class name
	 * @param completed the completed
	 * @param start the lower bound of the range of background tasks
	 * @param end the upper bound of the range of background tasks (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching background tasks
	 */
	@Override
	public List<BackgroundTask> findByG_N_T_C(
		long[] groupIds, String name, String taskExecutorClassName,
		boolean completed, int start, int end,
		OrderByComparator<BackgroundTask> orderByComparator) {

		return findByG_N_T_C(
			groupIds, name, taskExecutorClassName, completed, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the background tasks where groupId = &#63; and name = &#63; and taskExecutorClassName = &#63; and completed = &#63;, optionally using the finder cache.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BackgroundTaskModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param taskExecutorClassName the task executor class name
	 * @param completed the completed
	 * @param start the lower bound of the range of background tasks
	 * @param end the upper bound of the range of background tasks (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching background tasks
	 */
	@Override
	public List<BackgroundTask> findByG_N_T_C(
		long[] groupIds, String name, String taskExecutorClassName,
		boolean completed, int start, int end,
		OrderByComparator<BackgroundTask> orderByComparator,
		boolean useFinderCache) {

		if (groupIds == null) {
			groupIds = new long[0];
		}
		else if (groupIds.length > 1) {
			groupIds = ArrayUtil.sortedUnique(groupIds);
		}

		name = Objects.toString(name, "");
		taskExecutorClassName = Objects.toString(taskExecutorClassName, "");

		if (groupIds.length == 1) {
			return findByG_N_T_C(
				groupIds[0], name, taskExecutorClassName, completed, start, end,
				orderByComparator);
		}

		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderArgs = new Object[] {
					StringUtil.merge(groupIds), name, taskExecutorClassName,
					completed
				};
			}
		}
		else if (useFinderCache) {
			finderArgs = new Object[] {
				StringUtil.merge(groupIds), name, taskExecutorClassName,
				completed, start, end, orderByComparator
			};
		}

		List<BackgroundTask> list = null;

		if (useFinderCache) {
			list = (List<BackgroundTask>)finderCache.getResult(
				_finderPathWithPaginationFindByG_N_T_C, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (BackgroundTask backgroundTask : list) {
					if (!ArrayUtil.contains(
							groupIds, backgroundTask.getGroupId()) ||
						!name.equals(backgroundTask.getName()) ||
						!taskExecutorClassName.equals(
							backgroundTask.getTaskExecutorClassName()) ||
						(completed != backgroundTask.isCompleted())) {

						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler sb = new StringBundler();

			sb.append(_SQL_SELECT_BACKGROUNDTASK_WHERE);

			if (groupIds.length > 0) {
				sb.append("(");

				sb.append(_FINDER_COLUMN_G_N_T_C_GROUPID_7);

				sb.append(StringUtil.merge(groupIds));

				sb.append(")");

				sb.append(")");

				sb.append(WHERE_AND);
			}

			boolean bindName = false;

			if (name.isEmpty()) {
				sb.append(_FINDER_COLUMN_G_N_T_C_NAME_3);
			}
			else {
				bindName = true;

				sb.append(_FINDER_COLUMN_G_N_T_C_NAME_2);
			}

			boolean bindTaskExecutorClassName = false;

			if (taskExecutorClassName.isEmpty()) {
				sb.append(_FINDER_COLUMN_G_N_T_C_TASKEXECUTORCLASSNAME_3);
			}
			else {
				bindTaskExecutorClassName = true;

				sb.append(_FINDER_COLUMN_G_N_T_C_TASKEXECUTORCLASSNAME_2);
			}

			sb.append(_FINDER_COLUMN_G_N_T_C_COMPLETED_2);

			sb.setStringAt(
				removeConjunction(sb.stringAt(sb.index() - 1)), sb.index() - 1);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(BackgroundTaskModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindName) {
					queryPos.add(name);
				}

				if (bindTaskExecutorClassName) {
					queryPos.add(taskExecutorClassName);
				}

				queryPos.add(completed);

				list = (List<BackgroundTask>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					finderCache.putResult(
						_finderPathWithPaginationFindByG_N_T_C, finderArgs,
						list);
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
	 * Removes all the background tasks where groupId = &#63; and name = &#63; and taskExecutorClassName = &#63; and completed = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param taskExecutorClassName the task executor class name
	 * @param completed the completed
	 */
	@Override
	public void removeByG_N_T_C(
		long groupId, String name, String taskExecutorClassName,
		boolean completed) {

		for (BackgroundTask backgroundTask :
				findByG_N_T_C(
					groupId, name, taskExecutorClassName, completed,
					QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(backgroundTask);
		}
	}

	/**
	 * Returns the number of background tasks where groupId = &#63; and name = &#63; and taskExecutorClassName = &#63; and completed = &#63;.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param taskExecutorClassName the task executor class name
	 * @param completed the completed
	 * @return the number of matching background tasks
	 */
	@Override
	public int countByG_N_T_C(
		long groupId, String name, String taskExecutorClassName,
		boolean completed) {

		name = Objects.toString(name, "");
		taskExecutorClassName = Objects.toString(taskExecutorClassName, "");

		FinderPath finderPath = _finderPathCountByG_N_T_C;

		Object[] finderArgs = new Object[] {
			groupId, name, taskExecutorClassName, completed
		};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(5);

			sb.append(_SQL_COUNT_BACKGROUNDTASK_WHERE);

			sb.append(_FINDER_COLUMN_G_N_T_C_GROUPID_2);

			boolean bindName = false;

			if (name.isEmpty()) {
				sb.append(_FINDER_COLUMN_G_N_T_C_NAME_3);
			}
			else {
				bindName = true;

				sb.append(_FINDER_COLUMN_G_N_T_C_NAME_2);
			}

			boolean bindTaskExecutorClassName = false;

			if (taskExecutorClassName.isEmpty()) {
				sb.append(_FINDER_COLUMN_G_N_T_C_TASKEXECUTORCLASSNAME_3);
			}
			else {
				bindTaskExecutorClassName = true;

				sb.append(_FINDER_COLUMN_G_N_T_C_TASKEXECUTORCLASSNAME_2);
			}

			sb.append(_FINDER_COLUMN_G_N_T_C_COMPLETED_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				if (bindName) {
					queryPos.add(name);
				}

				if (bindTaskExecutorClassName) {
					queryPos.add(taskExecutorClassName);
				}

				queryPos.add(completed);

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

	/**
	 * Returns the number of background tasks where groupId = any &#63; and name = &#63; and taskExecutorClassName = &#63; and completed = &#63;.
	 *
	 * @param groupIds the group IDs
	 * @param name the name
	 * @param taskExecutorClassName the task executor class name
	 * @param completed the completed
	 * @return the number of matching background tasks
	 */
	@Override
	public int countByG_N_T_C(
		long[] groupIds, String name, String taskExecutorClassName,
		boolean completed) {

		if (groupIds == null) {
			groupIds = new long[0];
		}
		else if (groupIds.length > 1) {
			groupIds = ArrayUtil.sortedUnique(groupIds);
		}

		name = Objects.toString(name, "");
		taskExecutorClassName = Objects.toString(taskExecutorClassName, "");

		Object[] finderArgs = new Object[] {
			StringUtil.merge(groupIds), name, taskExecutorClassName, completed
		};

		Long count = (Long)finderCache.getResult(
			_finderPathWithPaginationCountByG_N_T_C, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler();

			sb.append(_SQL_COUNT_BACKGROUNDTASK_WHERE);

			if (groupIds.length > 0) {
				sb.append("(");

				sb.append(_FINDER_COLUMN_G_N_T_C_GROUPID_7);

				sb.append(StringUtil.merge(groupIds));

				sb.append(")");

				sb.append(")");

				sb.append(WHERE_AND);
			}

			boolean bindName = false;

			if (name.isEmpty()) {
				sb.append(_FINDER_COLUMN_G_N_T_C_NAME_3);
			}
			else {
				bindName = true;

				sb.append(_FINDER_COLUMN_G_N_T_C_NAME_2);
			}

			boolean bindTaskExecutorClassName = false;

			if (taskExecutorClassName.isEmpty()) {
				sb.append(_FINDER_COLUMN_G_N_T_C_TASKEXECUTORCLASSNAME_3);
			}
			else {
				bindTaskExecutorClassName = true;

				sb.append(_FINDER_COLUMN_G_N_T_C_TASKEXECUTORCLASSNAME_2);
			}

			sb.append(_FINDER_COLUMN_G_N_T_C_COMPLETED_2);

			sb.setStringAt(
				removeConjunction(sb.stringAt(sb.index() - 1)), sb.index() - 1);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindName) {
					queryPos.add(name);
				}

				if (bindTaskExecutorClassName) {
					queryPos.add(taskExecutorClassName);
				}

				queryPos.add(completed);

				count = (Long)query.uniqueResult();

				finderCache.putResult(
					_finderPathWithPaginationCountByG_N_T_C, finderArgs, count);
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

	private static final String _FINDER_COLUMN_G_N_T_C_GROUPID_2 =
		"backgroundTask.groupId = ? AND ";

	private static final String _FINDER_COLUMN_G_N_T_C_GROUPID_7 =
		"backgroundTask.groupId IN (";

	private static final String _FINDER_COLUMN_G_N_T_C_NAME_2 =
		"backgroundTask.name = ? AND ";

	private static final String _FINDER_COLUMN_G_N_T_C_NAME_3 =
		"(backgroundTask.name IS NULL OR backgroundTask.name = '') AND ";

	private static final String _FINDER_COLUMN_G_N_T_C_TASKEXECUTORCLASSNAME_2 =
		"backgroundTask.taskExecutorClassName = ? AND ";

	private static final String _FINDER_COLUMN_G_N_T_C_TASKEXECUTORCLASSNAME_3 =
		"(backgroundTask.taskExecutorClassName IS NULL OR backgroundTask.taskExecutorClassName = '') AND ";

	private static final String _FINDER_COLUMN_G_N_T_C_COMPLETED_2 =
		"backgroundTask.completed = ?";

	public BackgroundTaskPersistenceImpl() {
		setModelClass(BackgroundTask.class);

		setModelImplClass(BackgroundTaskImpl.class);
		setModelPKClass(long.class);

		setTable(BackgroundTaskTable.INSTANCE);
	}

	/**
	 * Caches the background task in the entity cache if it is enabled.
	 *
	 * @param backgroundTask the background task
	 */
	@Override
	public void cacheResult(BackgroundTask backgroundTask) {
		entityCache.putResult(
			BackgroundTaskImpl.class, backgroundTask.getPrimaryKey(),
			backgroundTask);
	}

	private int _valueObjectFinderCacheListThreshold;

	/**
	 * Caches the background tasks in the entity cache if it is enabled.
	 *
	 * @param backgroundTasks the background tasks
	 */
	@Override
	public void cacheResult(List<BackgroundTask> backgroundTasks) {
		if ((_valueObjectFinderCacheListThreshold == 0) ||
			((_valueObjectFinderCacheListThreshold > 0) &&
			 (backgroundTasks.size() > _valueObjectFinderCacheListThreshold))) {

			return;
		}

		for (BackgroundTask backgroundTask : backgroundTasks) {
			if (entityCache.getResult(
					BackgroundTaskImpl.class, backgroundTask.getPrimaryKey()) ==
						null) {

				cacheResult(backgroundTask);
			}
		}
	}

	/**
	 * Clears the cache for all background tasks.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(BackgroundTaskImpl.class);

		finderCache.clearCache(BackgroundTaskImpl.class);
	}

	/**
	 * Clears the cache for the background task.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(BackgroundTask backgroundTask) {
		entityCache.removeResult(BackgroundTaskImpl.class, backgroundTask);
	}

	@Override
	public void clearCache(List<BackgroundTask> backgroundTasks) {
		for (BackgroundTask backgroundTask : backgroundTasks) {
			entityCache.removeResult(BackgroundTaskImpl.class, backgroundTask);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(BackgroundTaskImpl.class);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(BackgroundTaskImpl.class, primaryKey);
		}
	}

	/**
	 * Creates a new background task with the primary key. Does not add the background task to the database.
	 *
	 * @param backgroundTaskId the primary key for the new background task
	 * @return the new background task
	 */
	@Override
	public BackgroundTask create(long backgroundTaskId) {
		BackgroundTask backgroundTask = new BackgroundTaskImpl();

		backgroundTask.setNew(true);
		backgroundTask.setPrimaryKey(backgroundTaskId);

		backgroundTask.setCompanyId(CompanyThreadLocal.getCompanyId());

		return backgroundTask;
	}

	/**
	 * Removes the background task with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param backgroundTaskId the primary key of the background task
	 * @return the background task that was removed
	 * @throws NoSuchBackgroundTaskException if a background task with the primary key could not be found
	 */
	@Override
	public BackgroundTask remove(long backgroundTaskId)
		throws NoSuchBackgroundTaskException {

		return remove((Serializable)backgroundTaskId);
	}

	/**
	 * Removes the background task with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the background task
	 * @return the background task that was removed
	 * @throws NoSuchBackgroundTaskException if a background task with the primary key could not be found
	 */
	@Override
	public BackgroundTask remove(Serializable primaryKey)
		throws NoSuchBackgroundTaskException {

		Session session = null;

		try {
			session = openSession();

			BackgroundTask backgroundTask = (BackgroundTask)session.get(
				BackgroundTaskImpl.class, primaryKey);

			if (backgroundTask == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchBackgroundTaskException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(backgroundTask);
		}
		catch (NoSuchBackgroundTaskException noSuchEntityException) {
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
	protected BackgroundTask removeImpl(BackgroundTask backgroundTask) {
		Session session = null;

		try {
			session = openSession();

			if (!session.contains(backgroundTask)) {
				backgroundTask = (BackgroundTask)session.get(
					BackgroundTaskImpl.class,
					backgroundTask.getPrimaryKeyObj());
			}

			if (backgroundTask != null) {
				session.delete(backgroundTask);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (backgroundTask != null) {
			clearCache(backgroundTask);
		}

		return backgroundTask;
	}

	@Override
	public BackgroundTask updateImpl(BackgroundTask backgroundTask) {
		boolean isNew = backgroundTask.isNew();

		if (!(backgroundTask instanceof BackgroundTaskModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(backgroundTask.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					backgroundTask);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in backgroundTask proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom BackgroundTask implementation " +
					backgroundTask.getClass());
		}

		BackgroundTaskModelImpl backgroundTaskModelImpl =
			(BackgroundTaskModelImpl)backgroundTask;

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Date date = new Date();

		if (isNew && (backgroundTask.getCreateDate() == null)) {
			if (serviceContext == null) {
				backgroundTask.setCreateDate(date);
			}
			else {
				backgroundTask.setCreateDate(
					serviceContext.getCreateDate(date));
			}
		}

		if (!backgroundTaskModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				backgroundTask.setModifiedDate(date);
			}
			else {
				backgroundTask.setModifiedDate(
					serviceContext.getModifiedDate(date));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (isNew) {
				session.save(backgroundTask);
			}
			else {
				backgroundTask = (BackgroundTask)session.merge(backgroundTask);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		entityCache.putResult(
			BackgroundTaskImpl.class, backgroundTaskModelImpl, false, true);

		if (isNew) {
			backgroundTask.setNew(false);
		}

		backgroundTask.resetOriginalValues();

		return backgroundTask;
	}

	/**
	 * Returns the background task with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the background task
	 * @return the background task
	 * @throws NoSuchBackgroundTaskException if a background task with the primary key could not be found
	 */
	@Override
	public BackgroundTask findByPrimaryKey(Serializable primaryKey)
		throws NoSuchBackgroundTaskException {

		BackgroundTask backgroundTask = fetchByPrimaryKey(primaryKey);

		if (backgroundTask == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchBackgroundTaskException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return backgroundTask;
	}

	/**
	 * Returns the background task with the primary key or throws a <code>NoSuchBackgroundTaskException</code> if it could not be found.
	 *
	 * @param backgroundTaskId the primary key of the background task
	 * @return the background task
	 * @throws NoSuchBackgroundTaskException if a background task with the primary key could not be found
	 */
	@Override
	public BackgroundTask findByPrimaryKey(long backgroundTaskId)
		throws NoSuchBackgroundTaskException {

		return findByPrimaryKey((Serializable)backgroundTaskId);
	}

	/**
	 * Returns the background task with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param backgroundTaskId the primary key of the background task
	 * @return the background task, or <code>null</code> if a background task with the primary key could not be found
	 */
	@Override
	public BackgroundTask fetchByPrimaryKey(long backgroundTaskId) {
		return fetchByPrimaryKey((Serializable)backgroundTaskId);
	}

	/**
	 * Returns all the background tasks.
	 *
	 * @return the background tasks
	 */
	@Override
	public List<BackgroundTask> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the background tasks.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BackgroundTaskModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of background tasks
	 * @param end the upper bound of the range of background tasks (not inclusive)
	 * @return the range of background tasks
	 */
	@Override
	public List<BackgroundTask> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the background tasks.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BackgroundTaskModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of background tasks
	 * @param end the upper bound of the range of background tasks (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of background tasks
	 */
	@Override
	public List<BackgroundTask> findAll(
		int start, int end,
		OrderByComparator<BackgroundTask> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the background tasks.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BackgroundTaskModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of background tasks
	 * @param end the upper bound of the range of background tasks (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of background tasks
	 */
	@Override
	public List<BackgroundTask> findAll(
		int start, int end, OrderByComparator<BackgroundTask> orderByComparator,
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

		List<BackgroundTask> list = null;

		if (useFinderCache) {
			list = (List<BackgroundTask>)finderCache.getResult(
				finderPath, finderArgs);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_BACKGROUNDTASK);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_BACKGROUNDTASK;

				sql = sql.concat(BackgroundTaskModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<BackgroundTask>)QueryUtil.list(
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
	 * Removes all the background tasks from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (BackgroundTask backgroundTask : findAll()) {
			remove(backgroundTask);
		}
	}

	/**
	 * Returns the number of background tasks.
	 *
	 * @return the number of background tasks
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(
			_finderPathCountAll, FINDER_ARGS_EMPTY);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(_SQL_COUNT_BACKGROUNDTASK);

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
		return "backgroundTaskId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_BACKGROUNDTASK;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return BackgroundTaskModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the background task persistence.
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

		_finderPathWithPaginationFindByGroupId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByGroupId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"groupId"}, true);

		_finderPathWithoutPaginationFindByGroupId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByGroupId",
			new String[] {Long.class.getName()}, new String[] {"groupId"},
			true);

		_finderPathCountByGroupId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByGroupId",
			new String[] {Long.class.getName()}, new String[] {"groupId"},
			false);

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

		_finderPathWithPaginationFindByCompleted = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByCompleted",
			new String[] {
				Boolean.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"completed"}, true);

		_finderPathWithoutPaginationFindByCompleted = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByCompleted",
			new String[] {Boolean.class.getName()}, new String[] {"completed"},
			true);

		_finderPathCountByCompleted = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByCompleted",
			new String[] {Boolean.class.getName()}, new String[] {"completed"},
			false);

		_finderPathWithPaginationFindByStatus = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByStatus",
			new String[] {
				Integer.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"status"}, true);

		_finderPathWithoutPaginationFindByStatus = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByStatus",
			new String[] {Integer.class.getName()}, new String[] {"status"},
			true);

		_finderPathCountByStatus = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByStatus",
			new String[] {Integer.class.getName()}, new String[] {"status"},
			false);

		_finderPathWithPaginationFindByG_T = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_T",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {"groupId", "taskExecutorClassName"}, true);

		_finderPathWithoutPaginationFindByG_T = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByG_T",
			new String[] {Long.class.getName(), String.class.getName()},
			new String[] {"groupId", "taskExecutorClassName"}, true);

		_finderPathCountByG_T = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_T",
			new String[] {Long.class.getName(), String.class.getName()},
			new String[] {"groupId", "taskExecutorClassName"}, false);

		_finderPathWithPaginationCountByG_T = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "countByG_T",
			new String[] {Long.class.getName(), String.class.getName()},
			new String[] {"groupId", "taskExecutorClassName"}, false);

		_finderPathWithPaginationFindByG_S = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_S",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {"groupId", "status"}, true);

		_finderPathWithoutPaginationFindByG_S = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByG_S",
			new String[] {Long.class.getName(), Integer.class.getName()},
			new String[] {"groupId", "status"}, true);

		_finderPathCountByG_S = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_S",
			new String[] {Long.class.getName(), Integer.class.getName()},
			new String[] {"groupId", "status"}, false);

		_finderPathWithPaginationFindByT_S = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByT_S",
			new String[] {
				String.class.getName(), Integer.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {"taskExecutorClassName", "status"}, true);

		_finderPathWithoutPaginationFindByT_S = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByT_S",
			new String[] {String.class.getName(), Integer.class.getName()},
			new String[] {"taskExecutorClassName", "status"}, true);

		_finderPathCountByT_S = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByT_S",
			new String[] {String.class.getName(), Integer.class.getName()},
			new String[] {"taskExecutorClassName", "status"}, false);

		_finderPathWithPaginationCountByT_S = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "countByT_S",
			new String[] {String.class.getName(), Integer.class.getName()},
			new String[] {"taskExecutorClassName", "status"}, false);

		_finderPathWithPaginationFindByG_N_T = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_N_T",
			new String[] {
				Long.class.getName(), String.class.getName(),
				String.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"groupId", "name", "taskExecutorClassName"}, true);

		_finderPathWithoutPaginationFindByG_N_T = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByG_N_T",
			new String[] {
				Long.class.getName(), String.class.getName(),
				String.class.getName()
			},
			new String[] {"groupId", "name", "taskExecutorClassName"}, true);

		_finderPathCountByG_N_T = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_N_T",
			new String[] {
				Long.class.getName(), String.class.getName(),
				String.class.getName()
			},
			new String[] {"groupId", "name", "taskExecutorClassName"}, false);

		_finderPathWithPaginationCountByG_N_T = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "countByG_N_T",
			new String[] {
				Long.class.getName(), String.class.getName(),
				String.class.getName()
			},
			new String[] {"groupId", "name", "taskExecutorClassName"}, false);

		_finderPathWithPaginationFindByG_T_C = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_T_C",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Boolean.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"groupId", "taskExecutorClassName", "completed"},
			true);

		_finderPathWithoutPaginationFindByG_T_C = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByG_T_C",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Boolean.class.getName()
			},
			new String[] {"groupId", "taskExecutorClassName", "completed"},
			true);

		_finderPathCountByG_T_C = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_T_C",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Boolean.class.getName()
			},
			new String[] {"groupId", "taskExecutorClassName", "completed"},
			false);

		_finderPathWithPaginationCountByG_T_C = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "countByG_T_C",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Boolean.class.getName()
			},
			new String[] {"groupId", "taskExecutorClassName", "completed"},
			false);

		_finderPathWithPaginationFindByG_T_S = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_T_S",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"groupId", "taskExecutorClassName", "status"}, true);

		_finderPathWithoutPaginationFindByG_T_S = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByG_T_S",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Integer.class.getName()
			},
			new String[] {"groupId", "taskExecutorClassName", "status"}, true);

		_finderPathCountByG_T_S = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_T_S",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Integer.class.getName()
			},
			new String[] {"groupId", "taskExecutorClassName", "status"}, false);

		_finderPathWithPaginationCountByG_T_S = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "countByG_T_S",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Integer.class.getName()
			},
			new String[] {"groupId", "taskExecutorClassName", "status"}, false);

		_finderPathWithPaginationFindByG_N_T_C = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_N_T_C",
			new String[] {
				Long.class.getName(), String.class.getName(),
				String.class.getName(), Boolean.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {
				"groupId", "name", "taskExecutorClassName", "completed"
			},
			true);

		_finderPathWithoutPaginationFindByG_N_T_C = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByG_N_T_C",
			new String[] {
				Long.class.getName(), String.class.getName(),
				String.class.getName(), Boolean.class.getName()
			},
			new String[] {
				"groupId", "name", "taskExecutorClassName", "completed"
			},
			true);

		_finderPathCountByG_N_T_C = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_N_T_C",
			new String[] {
				Long.class.getName(), String.class.getName(),
				String.class.getName(), Boolean.class.getName()
			},
			new String[] {
				"groupId", "name", "taskExecutorClassName", "completed"
			},
			false);

		_finderPathWithPaginationCountByG_N_T_C = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "countByG_N_T_C",
			new String[] {
				Long.class.getName(), String.class.getName(),
				String.class.getName(), Boolean.class.getName()
			},
			new String[] {
				"groupId", "name", "taskExecutorClassName", "completed"
			},
			false);
	}

	@Deactivate
	public void deactivate() {
		entityCache.removeCache(BackgroundTaskImpl.class.getName());
	}

	@Override
	@Reference(
		target = BackgroundTaskPersistenceConstants.SERVICE_CONFIGURATION_FILTER,
		unbind = "-"
	)
	public void setConfiguration(Configuration configuration) {
	}

	@Override
	@Reference(
		target = BackgroundTaskPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setDataSource(DataSource dataSource) {
		super.setDataSource(dataSource);
	}

	@Override
	@Reference(
		target = BackgroundTaskPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setSessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	@Reference
	protected EntityCache entityCache;

	@Reference
	protected FinderCache finderCache;

	private static final String _SQL_SELECT_BACKGROUNDTASK =
		"SELECT backgroundTask FROM BackgroundTask backgroundTask";

	private static final String _SQL_SELECT_BACKGROUNDTASK_WHERE =
		"SELECT backgroundTask FROM BackgroundTask backgroundTask WHERE ";

	private static final String _SQL_COUNT_BACKGROUNDTASK =
		"SELECT COUNT(backgroundTask) FROM BackgroundTask backgroundTask";

	private static final String _SQL_COUNT_BACKGROUNDTASK_WHERE =
		"SELECT COUNT(backgroundTask) FROM BackgroundTask backgroundTask WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS = "backgroundTask.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No BackgroundTask exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No BackgroundTask exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		BackgroundTaskPersistenceImpl.class);

	@Override
	protected FinderCache getFinderCache() {
		return finderCache;
	}

	@Reference
	private BackgroundTaskModelArgumentsResolver
		_backgroundTaskModelArgumentsResolver;

}