/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.service.persistence;

import com.liferay.portal.NoSuchBackgroundTaskException;
import com.liferay.portal.kernel.cache.CacheRegistryUtil;
import com.liferay.portal.kernel.dao.orm.EntityCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.InstanceFactory;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.UnmodifiableList;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.BackgroundTask;
import com.liferay.portal.model.CacheModel;
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.model.impl.BackgroundTaskImpl;
import com.liferay.portal.model.impl.BackgroundTaskModelImpl;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The persistence implementation for the background task service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see BackgroundTaskPersistence
 * @see BackgroundTaskUtil
 * @generated
 */
public class BackgroundTaskPersistenceImpl extends BasePersistenceImpl<BackgroundTask>
	implements BackgroundTaskPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link BackgroundTaskUtil} to access the background task persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = BackgroundTaskImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(BackgroundTaskModelImpl.ENTITY_CACHE_ENABLED,
			BackgroundTaskModelImpl.FINDER_CACHE_ENABLED,
			BackgroundTaskImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(BackgroundTaskModelImpl.ENTITY_CACHE_ENABLED,
			BackgroundTaskModelImpl.FINDER_CACHE_ENABLED,
			BackgroundTaskImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(BackgroundTaskModelImpl.ENTITY_CACHE_ENABLED,
			BackgroundTaskModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_G_T = new FinderPath(BackgroundTaskModelImpl.ENTITY_CACHE_ENABLED,
			BackgroundTaskModelImpl.FINDER_CACHE_ENABLED,
			BackgroundTaskImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByG_T",
			new String[] {
				Long.class.getName(), String.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_T = new FinderPath(BackgroundTaskModelImpl.ENTITY_CACHE_ENABLED,
			BackgroundTaskModelImpl.FINDER_CACHE_ENABLED,
			BackgroundTaskImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByG_T",
			new String[] { Long.class.getName(), String.class.getName() },
			BackgroundTaskModelImpl.GROUPID_COLUMN_BITMASK |
			BackgroundTaskModelImpl.TASKEXECUTORCLASSNAME_COLUMN_BITMASK |
			BackgroundTaskModelImpl.CREATEDATE_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_G_T = new FinderPath(BackgroundTaskModelImpl.ENTITY_CACHE_ENABLED,
			BackgroundTaskModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_T",
			new String[] { Long.class.getName(), String.class.getName() });

	/**
	 * Returns all the background tasks where groupId = &#63; and taskExecutorClassName = &#63;.
	 *
	 * @param groupId the group ID
	 * @param taskExecutorClassName the task executor class name
	 * @return the matching background tasks
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<BackgroundTask> findByG_T(long groupId,
		String taskExecutorClassName) throws SystemException {
		return findByG_T(groupId, taskExecutorClassName, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the background tasks where groupId = &#63; and taskExecutorClassName = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.BackgroundTaskModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param taskExecutorClassName the task executor class name
	 * @param start the lower bound of the range of background tasks
	 * @param end the upper bound of the range of background tasks (not inclusive)
	 * @return the range of matching background tasks
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<BackgroundTask> findByG_T(long groupId,
		String taskExecutorClassName, int start, int end)
		throws SystemException {
		return findByG_T(groupId, taskExecutorClassName, start, end, null);
	}

	/**
	 * Returns an ordered range of all the background tasks where groupId = &#63; and taskExecutorClassName = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.BackgroundTaskModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param taskExecutorClassName the task executor class name
	 * @param start the lower bound of the range of background tasks
	 * @param end the upper bound of the range of background tasks (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching background tasks
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<BackgroundTask> findByG_T(long groupId,
		String taskExecutorClassName, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_T;
			finderArgs = new Object[] { groupId, taskExecutorClassName };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_G_T;
			finderArgs = new Object[] {
					groupId, taskExecutorClassName,
					
					start, end, orderByComparator
				};
		}

		List<BackgroundTask> list = (List<BackgroundTask>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (BackgroundTask backgroundTask : list) {
				if ((groupId != backgroundTask.getGroupId()) ||
						!Validator.equals(taskExecutorClassName,
							backgroundTask.getTaskExecutorClassName())) {
					list = null;

					break;
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(4 +
						(orderByComparator.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(4);
			}

			query.append(_SQL_SELECT_BACKGROUNDTASK_WHERE);

			query.append(_FINDER_COLUMN_G_T_GROUPID_2);

			boolean bindTaskExecutorClassName = false;

			if (taskExecutorClassName == null) {
				query.append(_FINDER_COLUMN_G_T_TASKEXECUTORCLASSNAME_1);
			}
			else if (taskExecutorClassName.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_G_T_TASKEXECUTORCLASSNAME_3);
			}
			else {
				bindTaskExecutorClassName = true;

				query.append(_FINDER_COLUMN_G_T_TASKEXECUTORCLASSNAME_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(BackgroundTaskModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (bindTaskExecutorClassName) {
					qPos.add(taskExecutorClassName);
				}

				if (!pagination) {
					list = (List<BackgroundTask>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = new UnmodifiableList<BackgroundTask>(list);
				}
				else {
					list = (List<BackgroundTask>)QueryUtil.list(q,
							getDialect(), start, end);
				}

				cacheResult(list);

				FinderCacheUtil.putResult(finderPath, finderArgs, list);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(finderPath, finderArgs);

				throw processException(e);
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
	 * @throws com.liferay.portal.NoSuchBackgroundTaskException if a matching background task could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public BackgroundTask findByG_T_First(long groupId,
		String taskExecutorClassName, OrderByComparator orderByComparator)
		throws NoSuchBackgroundTaskException, SystemException {
		BackgroundTask backgroundTask = fetchByG_T_First(groupId,
				taskExecutorClassName, orderByComparator);

		if (backgroundTask != null) {
			return backgroundTask;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", taskExecutorClassName=");
		msg.append(taskExecutorClassName);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchBackgroundTaskException(msg.toString());
	}

	/**
	 * Returns the first background task in the ordered set where groupId = &#63; and taskExecutorClassName = &#63;.
	 *
	 * @param groupId the group ID
	 * @param taskExecutorClassName the task executor class name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching background task, or <code>null</code> if a matching background task could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public BackgroundTask fetchByG_T_First(long groupId,
		String taskExecutorClassName, OrderByComparator orderByComparator)
		throws SystemException {
		List<BackgroundTask> list = findByG_T(groupId, taskExecutorClassName,
				0, 1, orderByComparator);

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
	 * @throws com.liferay.portal.NoSuchBackgroundTaskException if a matching background task could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public BackgroundTask findByG_T_Last(long groupId,
		String taskExecutorClassName, OrderByComparator orderByComparator)
		throws NoSuchBackgroundTaskException, SystemException {
		BackgroundTask backgroundTask = fetchByG_T_Last(groupId,
				taskExecutorClassName, orderByComparator);

		if (backgroundTask != null) {
			return backgroundTask;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", taskExecutorClassName=");
		msg.append(taskExecutorClassName);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchBackgroundTaskException(msg.toString());
	}

	/**
	 * Returns the last background task in the ordered set where groupId = &#63; and taskExecutorClassName = &#63;.
	 *
	 * @param groupId the group ID
	 * @param taskExecutorClassName the task executor class name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching background task, or <code>null</code> if a matching background task could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public BackgroundTask fetchByG_T_Last(long groupId,
		String taskExecutorClassName, OrderByComparator orderByComparator)
		throws SystemException {
		int count = countByG_T(groupId, taskExecutorClassName);

		List<BackgroundTask> list = findByG_T(groupId, taskExecutorClassName,
				count - 1, count, orderByComparator);

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
	 * @throws com.liferay.portal.NoSuchBackgroundTaskException if a background task with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public BackgroundTask[] findByG_T_PrevAndNext(long backgroundTaskId,
		long groupId, String taskExecutorClassName,
		OrderByComparator orderByComparator)
		throws NoSuchBackgroundTaskException, SystemException {
		BackgroundTask backgroundTask = findByPrimaryKey(backgroundTaskId);

		Session session = null;

		try {
			session = openSession();

			BackgroundTask[] array = new BackgroundTaskImpl[3];

			array[0] = getByG_T_PrevAndNext(session, backgroundTask, groupId,
					taskExecutorClassName, orderByComparator, true);

			array[1] = backgroundTask;

			array[2] = getByG_T_PrevAndNext(session, backgroundTask, groupId,
					taskExecutorClassName, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected BackgroundTask getByG_T_PrevAndNext(Session session,
		BackgroundTask backgroundTask, long groupId,
		String taskExecutorClassName, OrderByComparator orderByComparator,
		boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_BACKGROUNDTASK_WHERE);

		query.append(_FINDER_COLUMN_G_T_GROUPID_2);

		boolean bindTaskExecutorClassName = false;

		if (taskExecutorClassName == null) {
			query.append(_FINDER_COLUMN_G_T_TASKEXECUTORCLASSNAME_1);
		}
		else if (taskExecutorClassName.equals(StringPool.BLANK)) {
			query.append(_FINDER_COLUMN_G_T_TASKEXECUTORCLASSNAME_3);
		}
		else {
			bindTaskExecutorClassName = true;

			query.append(_FINDER_COLUMN_G_T_TASKEXECUTORCLASSNAME_2);
		}

		if (orderByComparator != null) {
			String[] orderByConditionFields = orderByComparator.getOrderByConditionFields();

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
			query.append(BackgroundTaskModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		if (bindTaskExecutorClassName) {
			qPos.add(taskExecutorClassName);
		}

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(backgroundTask);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<BackgroundTask> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the background tasks where groupId = &#63; and taskExecutorClassName = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param taskExecutorClassName the task executor class name
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public void removeByG_T(long groupId, String taskExecutorClassName)
		throws SystemException {
		for (BackgroundTask backgroundTask : findByG_T(groupId,
				taskExecutorClassName, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
				null)) {
			remove(backgroundTask);
		}
	}

	/**
	 * Returns the number of background tasks where groupId = &#63; and taskExecutorClassName = &#63;.
	 *
	 * @param groupId the group ID
	 * @param taskExecutorClassName the task executor class name
	 * @return the number of matching background tasks
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public int countByG_T(long groupId, String taskExecutorClassName)
		throws SystemException {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_G_T;

		Object[] finderArgs = new Object[] { groupId, taskExecutorClassName };

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs,
				this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_BACKGROUNDTASK_WHERE);

			query.append(_FINDER_COLUMN_G_T_GROUPID_2);

			boolean bindTaskExecutorClassName = false;

			if (taskExecutorClassName == null) {
				query.append(_FINDER_COLUMN_G_T_TASKEXECUTORCLASSNAME_1);
			}
			else if (taskExecutorClassName.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_G_T_TASKEXECUTORCLASSNAME_3);
			}
			else {
				bindTaskExecutorClassName = true;

				query.append(_FINDER_COLUMN_G_T_TASKEXECUTORCLASSNAME_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (bindTaskExecutorClassName) {
					qPos.add(taskExecutorClassName);
				}

				count = (Long)q.uniqueResult();

				FinderCacheUtil.putResult(finderPath, finderArgs, count);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_G_T_GROUPID_2 = "backgroundTask.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_T_TASKEXECUTORCLASSNAME_1 = "backgroundTask.taskExecutorClassName IS NULL";
	private static final String _FINDER_COLUMN_G_T_TASKEXECUTORCLASSNAME_2 = "backgroundTask.taskExecutorClassName = ?";
	private static final String _FINDER_COLUMN_G_T_TASKEXECUTORCLASSNAME_3 = "(backgroundTask.taskExecutorClassName IS NULL OR backgroundTask.taskExecutorClassName = '')";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_T_S = new FinderPath(BackgroundTaskModelImpl.ENTITY_CACHE_ENABLED,
			BackgroundTaskModelImpl.FINDER_CACHE_ENABLED,
			BackgroundTaskImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByT_S",
			new String[] {
				String.class.getName(), Integer.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_T_S = new FinderPath(BackgroundTaskModelImpl.ENTITY_CACHE_ENABLED,
			BackgroundTaskModelImpl.FINDER_CACHE_ENABLED,
			BackgroundTaskImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByT_S",
			new String[] { String.class.getName(), Integer.class.getName() },
			BackgroundTaskModelImpl.TASKEXECUTORCLASSNAME_COLUMN_BITMASK |
			BackgroundTaskModelImpl.STATUS_COLUMN_BITMASK |
			BackgroundTaskModelImpl.CREATEDATE_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_T_S = new FinderPath(BackgroundTaskModelImpl.ENTITY_CACHE_ENABLED,
			BackgroundTaskModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByT_S",
			new String[] { String.class.getName(), Integer.class.getName() });

	/**
	 * Returns all the background tasks where taskExecutorClassName = &#63; and status = &#63;.
	 *
	 * @param taskExecutorClassName the task executor class name
	 * @param status the status
	 * @return the matching background tasks
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<BackgroundTask> findByT_S(String taskExecutorClassName,
		int status) throws SystemException {
		return findByT_S(taskExecutorClassName, status, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the background tasks where taskExecutorClassName = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.BackgroundTaskModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param taskExecutorClassName the task executor class name
	 * @param status the status
	 * @param start the lower bound of the range of background tasks
	 * @param end the upper bound of the range of background tasks (not inclusive)
	 * @return the range of matching background tasks
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<BackgroundTask> findByT_S(String taskExecutorClassName,
		int status, int start, int end) throws SystemException {
		return findByT_S(taskExecutorClassName, status, start, end, null);
	}

	/**
	 * Returns an ordered range of all the background tasks where taskExecutorClassName = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.BackgroundTaskModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param taskExecutorClassName the task executor class name
	 * @param status the status
	 * @param start the lower bound of the range of background tasks
	 * @param end the upper bound of the range of background tasks (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching background tasks
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<BackgroundTask> findByT_S(String taskExecutorClassName,
		int status, int start, int end, OrderByComparator orderByComparator)
		throws SystemException {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_T_S;
			finderArgs = new Object[] { taskExecutorClassName, status };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_T_S;
			finderArgs = new Object[] {
					taskExecutorClassName, status,
					
					start, end, orderByComparator
				};
		}

		List<BackgroundTask> list = (List<BackgroundTask>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (BackgroundTask backgroundTask : list) {
				if (!Validator.equals(taskExecutorClassName,
							backgroundTask.getTaskExecutorClassName()) ||
						(status != backgroundTask.getStatus())) {
					list = null;

					break;
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(4 +
						(orderByComparator.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(4);
			}

			query.append(_SQL_SELECT_BACKGROUNDTASK_WHERE);

			boolean bindTaskExecutorClassName = false;

			if (taskExecutorClassName == null) {
				query.append(_FINDER_COLUMN_T_S_TASKEXECUTORCLASSNAME_1);
			}
			else if (taskExecutorClassName.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_T_S_TASKEXECUTORCLASSNAME_3);
			}
			else {
				bindTaskExecutorClassName = true;

				query.append(_FINDER_COLUMN_T_S_TASKEXECUTORCLASSNAME_2);
			}

			query.append(_FINDER_COLUMN_T_S_STATUS_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(BackgroundTaskModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindTaskExecutorClassName) {
					qPos.add(taskExecutorClassName);
				}

				qPos.add(status);

				if (!pagination) {
					list = (List<BackgroundTask>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = new UnmodifiableList<BackgroundTask>(list);
				}
				else {
					list = (List<BackgroundTask>)QueryUtil.list(q,
							getDialect(), start, end);
				}

				cacheResult(list);

				FinderCacheUtil.putResult(finderPath, finderArgs, list);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(finderPath, finderArgs);

				throw processException(e);
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
	 * @throws com.liferay.portal.NoSuchBackgroundTaskException if a matching background task could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public BackgroundTask findByT_S_First(String taskExecutorClassName,
		int status, OrderByComparator orderByComparator)
		throws NoSuchBackgroundTaskException, SystemException {
		BackgroundTask backgroundTask = fetchByT_S_First(taskExecutorClassName,
				status, orderByComparator);

		if (backgroundTask != null) {
			return backgroundTask;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("taskExecutorClassName=");
		msg.append(taskExecutorClassName);

		msg.append(", status=");
		msg.append(status);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchBackgroundTaskException(msg.toString());
	}

	/**
	 * Returns the first background task in the ordered set where taskExecutorClassName = &#63; and status = &#63;.
	 *
	 * @param taskExecutorClassName the task executor class name
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching background task, or <code>null</code> if a matching background task could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public BackgroundTask fetchByT_S_First(String taskExecutorClassName,
		int status, OrderByComparator orderByComparator)
		throws SystemException {
		List<BackgroundTask> list = findByT_S(taskExecutorClassName, status, 0,
				1, orderByComparator);

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
	 * @throws com.liferay.portal.NoSuchBackgroundTaskException if a matching background task could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public BackgroundTask findByT_S_Last(String taskExecutorClassName,
		int status, OrderByComparator orderByComparator)
		throws NoSuchBackgroundTaskException, SystemException {
		BackgroundTask backgroundTask = fetchByT_S_Last(taskExecutorClassName,
				status, orderByComparator);

		if (backgroundTask != null) {
			return backgroundTask;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("taskExecutorClassName=");
		msg.append(taskExecutorClassName);

		msg.append(", status=");
		msg.append(status);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchBackgroundTaskException(msg.toString());
	}

	/**
	 * Returns the last background task in the ordered set where taskExecutorClassName = &#63; and status = &#63;.
	 *
	 * @param taskExecutorClassName the task executor class name
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching background task, or <code>null</code> if a matching background task could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public BackgroundTask fetchByT_S_Last(String taskExecutorClassName,
		int status, OrderByComparator orderByComparator)
		throws SystemException {
		int count = countByT_S(taskExecutorClassName, status);

		List<BackgroundTask> list = findByT_S(taskExecutorClassName, status,
				count - 1, count, orderByComparator);

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
	 * @throws com.liferay.portal.NoSuchBackgroundTaskException if a background task with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public BackgroundTask[] findByT_S_PrevAndNext(long backgroundTaskId,
		String taskExecutorClassName, int status,
		OrderByComparator orderByComparator)
		throws NoSuchBackgroundTaskException, SystemException {
		BackgroundTask backgroundTask = findByPrimaryKey(backgroundTaskId);

		Session session = null;

		try {
			session = openSession();

			BackgroundTask[] array = new BackgroundTaskImpl[3];

			array[0] = getByT_S_PrevAndNext(session, backgroundTask,
					taskExecutorClassName, status, orderByComparator, true);

			array[1] = backgroundTask;

			array[2] = getByT_S_PrevAndNext(session, backgroundTask,
					taskExecutorClassName, status, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected BackgroundTask getByT_S_PrevAndNext(Session session,
		BackgroundTask backgroundTask, String taskExecutorClassName,
		int status, OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_BACKGROUNDTASK_WHERE);

		boolean bindTaskExecutorClassName = false;

		if (taskExecutorClassName == null) {
			query.append(_FINDER_COLUMN_T_S_TASKEXECUTORCLASSNAME_1);
		}
		else if (taskExecutorClassName.equals(StringPool.BLANK)) {
			query.append(_FINDER_COLUMN_T_S_TASKEXECUTORCLASSNAME_3);
		}
		else {
			bindTaskExecutorClassName = true;

			query.append(_FINDER_COLUMN_T_S_TASKEXECUTORCLASSNAME_2);
		}

		query.append(_FINDER_COLUMN_T_S_STATUS_2);

		if (orderByComparator != null) {
			String[] orderByConditionFields = orderByComparator.getOrderByConditionFields();

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
			query.append(BackgroundTaskModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		if (bindTaskExecutorClassName) {
			qPos.add(taskExecutorClassName);
		}

		qPos.add(status);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(backgroundTask);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<BackgroundTask> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the background tasks where taskExecutorClassName = &#63; and status = &#63; from the database.
	 *
	 * @param taskExecutorClassName the task executor class name
	 * @param status the status
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public void removeByT_S(String taskExecutorClassName, int status)
		throws SystemException {
		for (BackgroundTask backgroundTask : findByT_S(taskExecutorClassName,
				status, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(backgroundTask);
		}
	}

	/**
	 * Returns the number of background tasks where taskExecutorClassName = &#63; and status = &#63;.
	 *
	 * @param taskExecutorClassName the task executor class name
	 * @param status the status
	 * @return the number of matching background tasks
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public int countByT_S(String taskExecutorClassName, int status)
		throws SystemException {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_T_S;

		Object[] finderArgs = new Object[] { taskExecutorClassName, status };

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs,
				this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_BACKGROUNDTASK_WHERE);

			boolean bindTaskExecutorClassName = false;

			if (taskExecutorClassName == null) {
				query.append(_FINDER_COLUMN_T_S_TASKEXECUTORCLASSNAME_1);
			}
			else if (taskExecutorClassName.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_T_S_TASKEXECUTORCLASSNAME_3);
			}
			else {
				bindTaskExecutorClassName = true;

				query.append(_FINDER_COLUMN_T_S_TASKEXECUTORCLASSNAME_2);
			}

			query.append(_FINDER_COLUMN_T_S_STATUS_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindTaskExecutorClassName) {
					qPos.add(taskExecutorClassName);
				}

				qPos.add(status);

				count = (Long)q.uniqueResult();

				FinderCacheUtil.putResult(finderPath, finderArgs, count);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_T_S_TASKEXECUTORCLASSNAME_1 = "backgroundTask.taskExecutorClassName IS NULL AND ";
	private static final String _FINDER_COLUMN_T_S_TASKEXECUTORCLASSNAME_2 = "backgroundTask.taskExecutorClassName = ? AND ";
	private static final String _FINDER_COLUMN_T_S_TASKEXECUTORCLASSNAME_3 = "(backgroundTask.taskExecutorClassName IS NULL OR backgroundTask.taskExecutorClassName = '') AND ";
	private static final String _FINDER_COLUMN_T_S_STATUS_2 = "backgroundTask.status = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_G_T_S = new FinderPath(BackgroundTaskModelImpl.ENTITY_CACHE_ENABLED,
			BackgroundTaskModelImpl.FINDER_CACHE_ENABLED,
			BackgroundTaskImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByG_T_S",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Integer.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_T_S = new FinderPath(BackgroundTaskModelImpl.ENTITY_CACHE_ENABLED,
			BackgroundTaskModelImpl.FINDER_CACHE_ENABLED,
			BackgroundTaskImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByG_T_S",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Integer.class.getName()
			},
			BackgroundTaskModelImpl.GROUPID_COLUMN_BITMASK |
			BackgroundTaskModelImpl.TASKEXECUTORCLASSNAME_COLUMN_BITMASK |
			BackgroundTaskModelImpl.STATUS_COLUMN_BITMASK |
			BackgroundTaskModelImpl.CREATEDATE_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_G_T_S = new FinderPath(BackgroundTaskModelImpl.ENTITY_CACHE_ENABLED,
			BackgroundTaskModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_T_S",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Integer.class.getName()
			});

	/**
	 * Returns all the background tasks where groupId = &#63; and taskExecutorClassName = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param taskExecutorClassName the task executor class name
	 * @param status the status
	 * @return the matching background tasks
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<BackgroundTask> findByG_T_S(long groupId,
		String taskExecutorClassName, int status) throws SystemException {
		return findByG_T_S(groupId, taskExecutorClassName, status,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the background tasks where groupId = &#63; and taskExecutorClassName = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.BackgroundTaskModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param taskExecutorClassName the task executor class name
	 * @param status the status
	 * @param start the lower bound of the range of background tasks
	 * @param end the upper bound of the range of background tasks (not inclusive)
	 * @return the range of matching background tasks
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<BackgroundTask> findByG_T_S(long groupId,
		String taskExecutorClassName, int status, int start, int end)
		throws SystemException {
		return findByG_T_S(groupId, taskExecutorClassName, status, start, end,
			null);
	}

	/**
	 * Returns an ordered range of all the background tasks where groupId = &#63; and taskExecutorClassName = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.BackgroundTaskModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param taskExecutorClassName the task executor class name
	 * @param status the status
	 * @param start the lower bound of the range of background tasks
	 * @param end the upper bound of the range of background tasks (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching background tasks
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<BackgroundTask> findByG_T_S(long groupId,
		String taskExecutorClassName, int status, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_T_S;
			finderArgs = new Object[] { groupId, taskExecutorClassName, status };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_G_T_S;
			finderArgs = new Object[] {
					groupId, taskExecutorClassName, status,
					
					start, end, orderByComparator
				};
		}

		List<BackgroundTask> list = (List<BackgroundTask>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (BackgroundTask backgroundTask : list) {
				if ((groupId != backgroundTask.getGroupId()) ||
						!Validator.equals(taskExecutorClassName,
							backgroundTask.getTaskExecutorClassName()) ||
						(status != backgroundTask.getStatus())) {
					list = null;

					break;
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(5 +
						(orderByComparator.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(5);
			}

			query.append(_SQL_SELECT_BACKGROUNDTASK_WHERE);

			query.append(_FINDER_COLUMN_G_T_S_GROUPID_2);

			boolean bindTaskExecutorClassName = false;

			if (taskExecutorClassName == null) {
				query.append(_FINDER_COLUMN_G_T_S_TASKEXECUTORCLASSNAME_1);
			}
			else if (taskExecutorClassName.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_G_T_S_TASKEXECUTORCLASSNAME_3);
			}
			else {
				bindTaskExecutorClassName = true;

				query.append(_FINDER_COLUMN_G_T_S_TASKEXECUTORCLASSNAME_2);
			}

			query.append(_FINDER_COLUMN_G_T_S_STATUS_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(BackgroundTaskModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (bindTaskExecutorClassName) {
					qPos.add(taskExecutorClassName);
				}

				qPos.add(status);

				if (!pagination) {
					list = (List<BackgroundTask>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = new UnmodifiableList<BackgroundTask>(list);
				}
				else {
					list = (List<BackgroundTask>)QueryUtil.list(q,
							getDialect(), start, end);
				}

				cacheResult(list);

				FinderCacheUtil.putResult(finderPath, finderArgs, list);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(finderPath, finderArgs);

				throw processException(e);
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
	 * @throws com.liferay.portal.NoSuchBackgroundTaskException if a matching background task could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public BackgroundTask findByG_T_S_First(long groupId,
		String taskExecutorClassName, int status,
		OrderByComparator orderByComparator)
		throws NoSuchBackgroundTaskException, SystemException {
		BackgroundTask backgroundTask = fetchByG_T_S_First(groupId,
				taskExecutorClassName, status, orderByComparator);

		if (backgroundTask != null) {
			return backgroundTask;
		}

		StringBundler msg = new StringBundler(8);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", taskExecutorClassName=");
		msg.append(taskExecutorClassName);

		msg.append(", status=");
		msg.append(status);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchBackgroundTaskException(msg.toString());
	}

	/**
	 * Returns the first background task in the ordered set where groupId = &#63; and taskExecutorClassName = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param taskExecutorClassName the task executor class name
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching background task, or <code>null</code> if a matching background task could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public BackgroundTask fetchByG_T_S_First(long groupId,
		String taskExecutorClassName, int status,
		OrderByComparator orderByComparator) throws SystemException {
		List<BackgroundTask> list = findByG_T_S(groupId, taskExecutorClassName,
				status, 0, 1, orderByComparator);

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
	 * @throws com.liferay.portal.NoSuchBackgroundTaskException if a matching background task could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public BackgroundTask findByG_T_S_Last(long groupId,
		String taskExecutorClassName, int status,
		OrderByComparator orderByComparator)
		throws NoSuchBackgroundTaskException, SystemException {
		BackgroundTask backgroundTask = fetchByG_T_S_Last(groupId,
				taskExecutorClassName, status, orderByComparator);

		if (backgroundTask != null) {
			return backgroundTask;
		}

		StringBundler msg = new StringBundler(8);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", taskExecutorClassName=");
		msg.append(taskExecutorClassName);

		msg.append(", status=");
		msg.append(status);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchBackgroundTaskException(msg.toString());
	}

	/**
	 * Returns the last background task in the ordered set where groupId = &#63; and taskExecutorClassName = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param taskExecutorClassName the task executor class name
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching background task, or <code>null</code> if a matching background task could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public BackgroundTask fetchByG_T_S_Last(long groupId,
		String taskExecutorClassName, int status,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByG_T_S(groupId, taskExecutorClassName, status);

		List<BackgroundTask> list = findByG_T_S(groupId, taskExecutorClassName,
				status, count - 1, count, orderByComparator);

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
	 * @throws com.liferay.portal.NoSuchBackgroundTaskException if a background task with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public BackgroundTask[] findByG_T_S_PrevAndNext(long backgroundTaskId,
		long groupId, String taskExecutorClassName, int status,
		OrderByComparator orderByComparator)
		throws NoSuchBackgroundTaskException, SystemException {
		BackgroundTask backgroundTask = findByPrimaryKey(backgroundTaskId);

		Session session = null;

		try {
			session = openSession();

			BackgroundTask[] array = new BackgroundTaskImpl[3];

			array[0] = getByG_T_S_PrevAndNext(session, backgroundTask, groupId,
					taskExecutorClassName, status, orderByComparator, true);

			array[1] = backgroundTask;

			array[2] = getByG_T_S_PrevAndNext(session, backgroundTask, groupId,
					taskExecutorClassName, status, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected BackgroundTask getByG_T_S_PrevAndNext(Session session,
		BackgroundTask backgroundTask, long groupId,
		String taskExecutorClassName, int status,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_BACKGROUNDTASK_WHERE);

		query.append(_FINDER_COLUMN_G_T_S_GROUPID_2);

		boolean bindTaskExecutorClassName = false;

		if (taskExecutorClassName == null) {
			query.append(_FINDER_COLUMN_G_T_S_TASKEXECUTORCLASSNAME_1);
		}
		else if (taskExecutorClassName.equals(StringPool.BLANK)) {
			query.append(_FINDER_COLUMN_G_T_S_TASKEXECUTORCLASSNAME_3);
		}
		else {
			bindTaskExecutorClassName = true;

			query.append(_FINDER_COLUMN_G_T_S_TASKEXECUTORCLASSNAME_2);
		}

		query.append(_FINDER_COLUMN_G_T_S_STATUS_2);

		if (orderByComparator != null) {
			String[] orderByConditionFields = orderByComparator.getOrderByConditionFields();

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
			query.append(BackgroundTaskModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		if (bindTaskExecutorClassName) {
			qPos.add(taskExecutorClassName);
		}

		qPos.add(status);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(backgroundTask);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<BackgroundTask> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the background tasks where groupId = &#63; and taskExecutorClassName = &#63; and status = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param taskExecutorClassName the task executor class name
	 * @param status the status
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public void removeByG_T_S(long groupId, String taskExecutorClassName,
		int status) throws SystemException {
		for (BackgroundTask backgroundTask : findByG_T_S(groupId,
				taskExecutorClassName, status, QueryUtil.ALL_POS,
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
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public int countByG_T_S(long groupId, String taskExecutorClassName,
		int status) throws SystemException {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_G_T_S;

		Object[] finderArgs = new Object[] {
				groupId, taskExecutorClassName, status
			};

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs,
				this);

		if (count == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_COUNT_BACKGROUNDTASK_WHERE);

			query.append(_FINDER_COLUMN_G_T_S_GROUPID_2);

			boolean bindTaskExecutorClassName = false;

			if (taskExecutorClassName == null) {
				query.append(_FINDER_COLUMN_G_T_S_TASKEXECUTORCLASSNAME_1);
			}
			else if (taskExecutorClassName.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_G_T_S_TASKEXECUTORCLASSNAME_3);
			}
			else {
				bindTaskExecutorClassName = true;

				query.append(_FINDER_COLUMN_G_T_S_TASKEXECUTORCLASSNAME_2);
			}

			query.append(_FINDER_COLUMN_G_T_S_STATUS_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (bindTaskExecutorClassName) {
					qPos.add(taskExecutorClassName);
				}

				qPos.add(status);

				count = (Long)q.uniqueResult();

				FinderCacheUtil.putResult(finderPath, finderArgs, count);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_G_T_S_GROUPID_2 = "backgroundTask.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_T_S_TASKEXECUTORCLASSNAME_1 = "backgroundTask.taskExecutorClassName IS NULL AND ";
	private static final String _FINDER_COLUMN_G_T_S_TASKEXECUTORCLASSNAME_2 = "backgroundTask.taskExecutorClassName = ? AND ";
	private static final String _FINDER_COLUMN_G_T_S_TASKEXECUTORCLASSNAME_3 = "(backgroundTask.taskExecutorClassName IS NULL OR backgroundTask.taskExecutorClassName = '') AND ";
	private static final String _FINDER_COLUMN_G_T_S_STATUS_2 = "backgroundTask.status = ?";

	/**
	 * Caches the background task in the entity cache if it is enabled.
	 *
	 * @param backgroundTask the background task
	 */
	@Override
	public void cacheResult(BackgroundTask backgroundTask) {
		EntityCacheUtil.putResult(BackgroundTaskModelImpl.ENTITY_CACHE_ENABLED,
			BackgroundTaskImpl.class, backgroundTask.getPrimaryKey(),
			backgroundTask);

		backgroundTask.resetOriginalValues();
	}

	/**
	 * Caches the background tasks in the entity cache if it is enabled.
	 *
	 * @param backgroundTasks the background tasks
	 */
	@Override
	public void cacheResult(List<BackgroundTask> backgroundTasks) {
		for (BackgroundTask backgroundTask : backgroundTasks) {
			if (EntityCacheUtil.getResult(
						BackgroundTaskModelImpl.ENTITY_CACHE_ENABLED,
						BackgroundTaskImpl.class, backgroundTask.getPrimaryKey()) == null) {
				cacheResult(backgroundTask);
			}
			else {
				backgroundTask.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all background tasks.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		if (_HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			CacheRegistryUtil.clear(BackgroundTaskImpl.class.getName());
		}

		EntityCacheUtil.clearCache(BackgroundTaskImpl.class.getName());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the background task.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(BackgroundTask backgroundTask) {
		EntityCacheUtil.removeResult(BackgroundTaskModelImpl.ENTITY_CACHE_ENABLED,
			BackgroundTaskImpl.class, backgroundTask.getPrimaryKey());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@Override
	public void clearCache(List<BackgroundTask> backgroundTasks) {
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (BackgroundTask backgroundTask : backgroundTasks) {
			EntityCacheUtil.removeResult(BackgroundTaskModelImpl.ENTITY_CACHE_ENABLED,
				BackgroundTaskImpl.class, backgroundTask.getPrimaryKey());
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

		return backgroundTask;
	}

	/**
	 * Removes the background task with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param backgroundTaskId the primary key of the background task
	 * @return the background task that was removed
	 * @throws com.liferay.portal.NoSuchBackgroundTaskException if a background task with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public BackgroundTask remove(long backgroundTaskId)
		throws NoSuchBackgroundTaskException, SystemException {
		return remove((Serializable)backgroundTaskId);
	}

	/**
	 * Removes the background task with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the background task
	 * @return the background task that was removed
	 * @throws com.liferay.portal.NoSuchBackgroundTaskException if a background task with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public BackgroundTask remove(Serializable primaryKey)
		throws NoSuchBackgroundTaskException, SystemException {
		Session session = null;

		try {
			session = openSession();

			BackgroundTask backgroundTask = (BackgroundTask)session.get(BackgroundTaskImpl.class,
					primaryKey);

			if (backgroundTask == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchBackgroundTaskException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(backgroundTask);
		}
		catch (NoSuchBackgroundTaskException nsee) {
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
	protected BackgroundTask removeImpl(BackgroundTask backgroundTask)
		throws SystemException {
		backgroundTask = toUnwrappedModel(backgroundTask);

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(backgroundTask)) {
				backgroundTask = (BackgroundTask)session.get(BackgroundTaskImpl.class,
						backgroundTask.getPrimaryKeyObj());
			}

			if (backgroundTask != null) {
				session.delete(backgroundTask);
			}
		}
		catch (Exception e) {
			throw processException(e);
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
	public BackgroundTask updateImpl(
		com.liferay.portal.model.BackgroundTask backgroundTask)
		throws SystemException {
		backgroundTask = toUnwrappedModel(backgroundTask);

		boolean isNew = backgroundTask.isNew();

		BackgroundTaskModelImpl backgroundTaskModelImpl = (BackgroundTaskModelImpl)backgroundTask;

		Session session = null;

		try {
			session = openSession();

			if (backgroundTask.isNew()) {
				session.save(backgroundTask);

				backgroundTask.setNew(false);
			}
			else {
				session.merge(backgroundTask);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (isNew || !BackgroundTaskModelImpl.COLUMN_BITMASK_ENABLED) {
			FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}

		else {
			if ((backgroundTaskModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_T.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						backgroundTaskModelImpl.getOriginalGroupId(),
						backgroundTaskModelImpl.getOriginalTaskExecutorClassName()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_G_T, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_T,
					args);

				args = new Object[] {
						backgroundTaskModelImpl.getGroupId(),
						backgroundTaskModelImpl.getTaskExecutorClassName()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_G_T, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_T,
					args);
			}

			if ((backgroundTaskModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_T_S.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						backgroundTaskModelImpl.getOriginalTaskExecutorClassName(),
						backgroundTaskModelImpl.getOriginalStatus()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_T_S, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_T_S,
					args);

				args = new Object[] {
						backgroundTaskModelImpl.getTaskExecutorClassName(),
						backgroundTaskModelImpl.getStatus()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_T_S, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_T_S,
					args);
			}

			if ((backgroundTaskModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_T_S.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						backgroundTaskModelImpl.getOriginalGroupId(),
						backgroundTaskModelImpl.getOriginalTaskExecutorClassName(),
						backgroundTaskModelImpl.getOriginalStatus()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_G_T_S, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_T_S,
					args);

				args = new Object[] {
						backgroundTaskModelImpl.getGroupId(),
						backgroundTaskModelImpl.getTaskExecutorClassName(),
						backgroundTaskModelImpl.getStatus()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_G_T_S, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_T_S,
					args);
			}
		}

		EntityCacheUtil.putResult(BackgroundTaskModelImpl.ENTITY_CACHE_ENABLED,
			BackgroundTaskImpl.class, backgroundTask.getPrimaryKey(),
			backgroundTask);

		return backgroundTask;
	}

	protected BackgroundTask toUnwrappedModel(BackgroundTask backgroundTask) {
		if (backgroundTask instanceof BackgroundTaskImpl) {
			return backgroundTask;
		}

		BackgroundTaskImpl backgroundTaskImpl = new BackgroundTaskImpl();

		backgroundTaskImpl.setNew(backgroundTask.isNew());
		backgroundTaskImpl.setPrimaryKey(backgroundTask.getPrimaryKey());

		backgroundTaskImpl.setBackgroundTaskId(backgroundTask.getBackgroundTaskId());
		backgroundTaskImpl.setGroupId(backgroundTask.getGroupId());
		backgroundTaskImpl.setCompanyId(backgroundTask.getCompanyId());
		backgroundTaskImpl.setUserId(backgroundTask.getUserId());
		backgroundTaskImpl.setUserName(backgroundTask.getUserName());
		backgroundTaskImpl.setCreateDate(backgroundTask.getCreateDate());
		backgroundTaskImpl.setModifiedDate(backgroundTask.getModifiedDate());
		backgroundTaskImpl.setName(backgroundTask.getName());
		backgroundTaskImpl.setServletContextNames(backgroundTask.getServletContextNames());
		backgroundTaskImpl.setTaskExecutorClassName(backgroundTask.getTaskExecutorClassName());
		backgroundTaskImpl.setTaskContext(backgroundTask.getTaskContext());
		backgroundTaskImpl.setCompleted(backgroundTask.isCompleted());
		backgroundTaskImpl.setCompletionDate(backgroundTask.getCompletionDate());
		backgroundTaskImpl.setStatus(backgroundTask.getStatus());
		backgroundTaskImpl.setStatusMessage(backgroundTask.getStatusMessage());

		return backgroundTaskImpl;
	}

	/**
	 * Returns the background task with the primary key or throws a {@link com.liferay.portal.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the background task
	 * @return the background task
	 * @throws com.liferay.portal.NoSuchBackgroundTaskException if a background task with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public BackgroundTask findByPrimaryKey(Serializable primaryKey)
		throws NoSuchBackgroundTaskException, SystemException {
		BackgroundTask backgroundTask = fetchByPrimaryKey(primaryKey);

		if (backgroundTask == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchBackgroundTaskException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				primaryKey);
		}

		return backgroundTask;
	}

	/**
	 * Returns the background task with the primary key or throws a {@link com.liferay.portal.NoSuchBackgroundTaskException} if it could not be found.
	 *
	 * @param backgroundTaskId the primary key of the background task
	 * @return the background task
	 * @throws com.liferay.portal.NoSuchBackgroundTaskException if a background task with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public BackgroundTask findByPrimaryKey(long backgroundTaskId)
		throws NoSuchBackgroundTaskException, SystemException {
		return findByPrimaryKey((Serializable)backgroundTaskId);
	}

	/**
	 * Returns the background task with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the background task
	 * @return the background task, or <code>null</code> if a background task with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public BackgroundTask fetchByPrimaryKey(Serializable primaryKey)
		throws SystemException {
		BackgroundTask backgroundTask = (BackgroundTask)EntityCacheUtil.getResult(BackgroundTaskModelImpl.ENTITY_CACHE_ENABLED,
				BackgroundTaskImpl.class, primaryKey);

		if (backgroundTask == _nullBackgroundTask) {
			return null;
		}

		if (backgroundTask == null) {
			Session session = null;

			try {
				session = openSession();

				backgroundTask = (BackgroundTask)session.get(BackgroundTaskImpl.class,
						primaryKey);

				if (backgroundTask != null) {
					cacheResult(backgroundTask);
				}
				else {
					EntityCacheUtil.putResult(BackgroundTaskModelImpl.ENTITY_CACHE_ENABLED,
						BackgroundTaskImpl.class, primaryKey,
						_nullBackgroundTask);
				}
			}
			catch (Exception e) {
				EntityCacheUtil.removeResult(BackgroundTaskModelImpl.ENTITY_CACHE_ENABLED,
					BackgroundTaskImpl.class, primaryKey);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return backgroundTask;
	}

	/**
	 * Returns the background task with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param backgroundTaskId the primary key of the background task
	 * @return the background task, or <code>null</code> if a background task with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public BackgroundTask fetchByPrimaryKey(long backgroundTaskId)
		throws SystemException {
		return fetchByPrimaryKey((Serializable)backgroundTaskId);
	}

	/**
	 * Returns all the background tasks.
	 *
	 * @return the background tasks
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<BackgroundTask> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the background tasks.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.BackgroundTaskModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of background tasks
	 * @param end the upper bound of the range of background tasks (not inclusive)
	 * @return the range of background tasks
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<BackgroundTask> findAll(int start, int end)
		throws SystemException {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the background tasks.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.BackgroundTaskModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of background tasks
	 * @param end the upper bound of the range of background tasks (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of background tasks
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<BackgroundTask> findAll(int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL;
			finderArgs = FINDER_ARGS_EMPTY;
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_ALL;
			finderArgs = new Object[] { start, end, orderByComparator };
		}

		List<BackgroundTask> list = (List<BackgroundTask>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 3));

				query.append(_SQL_SELECT_BACKGROUNDTASK);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_BACKGROUNDTASK;

				if (pagination) {
					sql = sql.concat(BackgroundTaskModelImpl.ORDER_BY_JPQL);
				}
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (!pagination) {
					list = (List<BackgroundTask>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = new UnmodifiableList<BackgroundTask>(list);
				}
				else {
					list = (List<BackgroundTask>)QueryUtil.list(q,
							getDialect(), start, end);
				}

				cacheResult(list);

				FinderCacheUtil.putResult(finderPath, finderArgs, list);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(finderPath, finderArgs);

				throw processException(e);
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
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public void removeAll() throws SystemException {
		for (BackgroundTask backgroundTask : findAll()) {
			remove(backgroundTask);
		}
	}

	/**
	 * Returns the number of background tasks.
	 *
	 * @return the number of background tasks
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public int countAll() throws SystemException {
		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_BACKGROUNDTASK);

				count = (Long)q.uniqueResult();

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_ALL,
					FINDER_ARGS_EMPTY, count);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_ALL,
					FINDER_ARGS_EMPTY);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Initializes the background task persistence.
	 */
	public void afterPropertiesSet() {
		String[] listenerClassNames = StringUtil.split(GetterUtil.getString(
					com.liferay.portal.util.PropsUtil.get(
						"value.object.listener.com.liferay.portal.model.BackgroundTask")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<BackgroundTask>> listenersList = new ArrayList<ModelListener<BackgroundTask>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<BackgroundTask>)InstanceFactory.newInstance(
							getClassLoader(), listenerClassName));
				}

				listeners = listenersList.toArray(new ModelListener[listenersList.size()]);
			}
			catch (Exception e) {
				_log.error(e);
			}
		}
	}

	public void destroy() {
		EntityCacheUtil.removeCache(BackgroundTaskImpl.class.getName());
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	private static final String _SQL_SELECT_BACKGROUNDTASK = "SELECT backgroundTask FROM BackgroundTask backgroundTask";
	private static final String _SQL_SELECT_BACKGROUNDTASK_WHERE = "SELECT backgroundTask FROM BackgroundTask backgroundTask WHERE ";
	private static final String _SQL_COUNT_BACKGROUNDTASK = "SELECT COUNT(backgroundTask) FROM BackgroundTask backgroundTask";
	private static final String _SQL_COUNT_BACKGROUNDTASK_WHERE = "SELECT COUNT(backgroundTask) FROM BackgroundTask backgroundTask WHERE ";
	private static final String _ORDER_BY_ENTITY_ALIAS = "backgroundTask.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No BackgroundTask exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No BackgroundTask exists with the key {";
	private static final boolean _HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE = com.liferay.portal.util.PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE;
	private static Log _log = LogFactoryUtil.getLog(BackgroundTaskPersistenceImpl.class);
	private static BackgroundTask _nullBackgroundTask = new BackgroundTaskImpl() {
			@Override
			public Object clone() {
				return this;
			}

			@Override
			public CacheModel<BackgroundTask> toCacheModel() {
				return _nullBackgroundTaskCacheModel;
			}
		};

	private static CacheModel<BackgroundTask> _nullBackgroundTaskCacheModel = new CacheModel<BackgroundTask>() {
			@Override
			public BackgroundTask toEntityModel() {
				return _nullBackgroundTask;
			}
		};
}