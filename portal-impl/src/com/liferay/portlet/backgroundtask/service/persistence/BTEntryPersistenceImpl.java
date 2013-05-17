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

package com.liferay.portlet.backgroundtask.service.persistence;

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
import com.liferay.portal.model.CacheModel;
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;

import com.liferay.portlet.backgroundtask.NoSuchEntryException;
import com.liferay.portlet.backgroundtask.model.BTEntry;
import com.liferay.portlet.backgroundtask.model.impl.BTEntryImpl;
import com.liferay.portlet.backgroundtask.model.impl.BTEntryModelImpl;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The persistence implementation for the b t entry service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see BTEntryPersistence
 * @see BTEntryUtil
 * @generated
 */
public class BTEntryPersistenceImpl extends BasePersistenceImpl<BTEntry>
	implements BTEntryPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link BTEntryUtil} to access the b t entry persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = BTEntryImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(BTEntryModelImpl.ENTITY_CACHE_ENABLED,
			BTEntryModelImpl.FINDER_CACHE_ENABLED, BTEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(BTEntryModelImpl.ENTITY_CACHE_ENABLED,
			BTEntryModelImpl.FINDER_CACHE_ENABLED, BTEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(BTEntryModelImpl.ENTITY_CACHE_ENABLED,
			BTEntryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_G_T = new FinderPath(BTEntryModelImpl.ENTITY_CACHE_ENABLED,
			BTEntryModelImpl.FINDER_CACHE_ENABLED, BTEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_T",
			new String[] {
				Long.class.getName(), String.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_T = new FinderPath(BTEntryModelImpl.ENTITY_CACHE_ENABLED,
			BTEntryModelImpl.FINDER_CACHE_ENABLED, BTEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByG_T",
			new String[] { Long.class.getName(), String.class.getName() },
			BTEntryModelImpl.GROUPID_COLUMN_BITMASK |
			BTEntryModelImpl.TASKEXECUTORCLASSNAME_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_G_T = new FinderPath(BTEntryModelImpl.ENTITY_CACHE_ENABLED,
			BTEntryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_T",
			new String[] { Long.class.getName(), String.class.getName() });

	/**
	 * Returns all the b t entries where groupId = &#63; and taskExecutorClassName = &#63;.
	 *
	 * @param groupId the group ID
	 * @param taskExecutorClassName the task executor class name
	 * @return the matching b t entries
	 * @throws SystemException if a system exception occurred
	 */
	public List<BTEntry> findByG_T(long groupId, String taskExecutorClassName)
		throws SystemException {
		return findByG_T(groupId, taskExecutorClassName, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the b t entries where groupId = &#63; and taskExecutorClassName = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.backgroundtask.model.impl.BTEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param taskExecutorClassName the task executor class name
	 * @param start the lower bound of the range of b t entries
	 * @param end the upper bound of the range of b t entries (not inclusive)
	 * @return the range of matching b t entries
	 * @throws SystemException if a system exception occurred
	 */
	public List<BTEntry> findByG_T(long groupId, String taskExecutorClassName,
		int start, int end) throws SystemException {
		return findByG_T(groupId, taskExecutorClassName, start, end, null);
	}

	/**
	 * Returns an ordered range of all the b t entries where groupId = &#63; and taskExecutorClassName = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.backgroundtask.model.impl.BTEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param taskExecutorClassName the task executor class name
	 * @param start the lower bound of the range of b t entries
	 * @param end the upper bound of the range of b t entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching b t entries
	 * @throws SystemException if a system exception occurred
	 */
	public List<BTEntry> findByG_T(long groupId, String taskExecutorClassName,
		int start, int end, OrderByComparator orderByComparator)
		throws SystemException {
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

		List<BTEntry> list = (List<BTEntry>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (BTEntry btEntry : list) {
				if ((groupId != btEntry.getGroupId()) ||
						!Validator.equals(taskExecutorClassName,
							btEntry.getTaskExecutorClassName())) {
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

			query.append(_SQL_SELECT_BTENTRY_WHERE);

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
				query.append(BTEntryModelImpl.ORDER_BY_JPQL);
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
					list = (List<BTEntry>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = new UnmodifiableList<BTEntry>(list);
				}
				else {
					list = (List<BTEntry>)QueryUtil.list(q, getDialect(),
							start, end);
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
	 * Returns the first b t entry in the ordered set where groupId = &#63; and taskExecutorClassName = &#63;.
	 *
	 * @param groupId the group ID
	 * @param taskExecutorClassName the task executor class name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching b t entry
	 * @throws com.liferay.portlet.backgroundtask.NoSuchEntryException if a matching b t entry could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public BTEntry findByG_T_First(long groupId, String taskExecutorClassName,
		OrderByComparator orderByComparator)
		throws NoSuchEntryException, SystemException {
		BTEntry btEntry = fetchByG_T_First(groupId, taskExecutorClassName,
				orderByComparator);

		if (btEntry != null) {
			return btEntry;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", taskExecutorClassName=");
		msg.append(taskExecutorClassName);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchEntryException(msg.toString());
	}

	/**
	 * Returns the first b t entry in the ordered set where groupId = &#63; and taskExecutorClassName = &#63;.
	 *
	 * @param groupId the group ID
	 * @param taskExecutorClassName the task executor class name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching b t entry, or <code>null</code> if a matching b t entry could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public BTEntry fetchByG_T_First(long groupId, String taskExecutorClassName,
		OrderByComparator orderByComparator) throws SystemException {
		List<BTEntry> list = findByG_T(groupId, taskExecutorClassName, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last b t entry in the ordered set where groupId = &#63; and taskExecutorClassName = &#63;.
	 *
	 * @param groupId the group ID
	 * @param taskExecutorClassName the task executor class name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching b t entry
	 * @throws com.liferay.portlet.backgroundtask.NoSuchEntryException if a matching b t entry could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public BTEntry findByG_T_Last(long groupId, String taskExecutorClassName,
		OrderByComparator orderByComparator)
		throws NoSuchEntryException, SystemException {
		BTEntry btEntry = fetchByG_T_Last(groupId, taskExecutorClassName,
				orderByComparator);

		if (btEntry != null) {
			return btEntry;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", taskExecutorClassName=");
		msg.append(taskExecutorClassName);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchEntryException(msg.toString());
	}

	/**
	 * Returns the last b t entry in the ordered set where groupId = &#63; and taskExecutorClassName = &#63;.
	 *
	 * @param groupId the group ID
	 * @param taskExecutorClassName the task executor class name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching b t entry, or <code>null</code> if a matching b t entry could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public BTEntry fetchByG_T_Last(long groupId, String taskExecutorClassName,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByG_T(groupId, taskExecutorClassName);

		List<BTEntry> list = findByG_T(groupId, taskExecutorClassName,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the b t entries before and after the current b t entry in the ordered set where groupId = &#63; and taskExecutorClassName = &#63;.
	 *
	 * @param btEntryId the primary key of the current b t entry
	 * @param groupId the group ID
	 * @param taskExecutorClassName the task executor class name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next b t entry
	 * @throws com.liferay.portlet.backgroundtask.NoSuchEntryException if a b t entry with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public BTEntry[] findByG_T_PrevAndNext(long btEntryId, long groupId,
		String taskExecutorClassName, OrderByComparator orderByComparator)
		throws NoSuchEntryException, SystemException {
		BTEntry btEntry = findByPrimaryKey(btEntryId);

		Session session = null;

		try {
			session = openSession();

			BTEntry[] array = new BTEntryImpl[3];

			array[0] = getByG_T_PrevAndNext(session, btEntry, groupId,
					taskExecutorClassName, orderByComparator, true);

			array[1] = btEntry;

			array[2] = getByG_T_PrevAndNext(session, btEntry, groupId,
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

	protected BTEntry getByG_T_PrevAndNext(Session session, BTEntry btEntry,
		long groupId, String taskExecutorClassName,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_BTENTRY_WHERE);

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
			query.append(BTEntryModelImpl.ORDER_BY_JPQL);
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
			Object[] values = orderByComparator.getOrderByConditionValues(btEntry);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<BTEntry> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the b t entries where groupId = &#63; and taskExecutorClassName = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param taskExecutorClassName the task executor class name
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByG_T(long groupId, String taskExecutorClassName)
		throws SystemException {
		for (BTEntry btEntry : findByG_T(groupId, taskExecutorClassName,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(btEntry);
		}
	}

	/**
	 * Returns the number of b t entries where groupId = &#63; and taskExecutorClassName = &#63;.
	 *
	 * @param groupId the group ID
	 * @param taskExecutorClassName the task executor class name
	 * @return the number of matching b t entries
	 * @throws SystemException if a system exception occurred
	 */
	public int countByG_T(long groupId, String taskExecutorClassName)
		throws SystemException {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_G_T;

		Object[] finderArgs = new Object[] { groupId, taskExecutorClassName };

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs,
				this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_BTENTRY_WHERE);

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

	private static final String _FINDER_COLUMN_G_T_GROUPID_2 = "btEntry.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_T_TASKEXECUTORCLASSNAME_1 = "btEntry.taskExecutorClassName IS NULL";
	private static final String _FINDER_COLUMN_G_T_TASKEXECUTORCLASSNAME_2 = "btEntry.taskExecutorClassName = ?";
	private static final String _FINDER_COLUMN_G_T_TASKEXECUTORCLASSNAME_3 = "(btEntry.taskExecutorClassName IS NULL OR btEntry.taskExecutorClassName = '')";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_G_S_T = new FinderPath(BTEntryModelImpl.ENTITY_CACHE_ENABLED,
			BTEntryModelImpl.FINDER_CACHE_ENABLED, BTEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_S_T",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				String.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_S_T = new FinderPath(BTEntryModelImpl.ENTITY_CACHE_ENABLED,
			BTEntryModelImpl.FINDER_CACHE_ENABLED, BTEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByG_S_T",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				String.class.getName()
			},
			BTEntryModelImpl.GROUPID_COLUMN_BITMASK |
			BTEntryModelImpl.STATUS_COLUMN_BITMASK |
			BTEntryModelImpl.TASKEXECUTORCLASSNAME_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_G_S_T = new FinderPath(BTEntryModelImpl.ENTITY_CACHE_ENABLED,
			BTEntryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_S_T",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				String.class.getName()
			});

	/**
	 * Returns all the b t entries where groupId = &#63; and status = &#63; and taskExecutorClassName = &#63;.
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @param taskExecutorClassName the task executor class name
	 * @return the matching b t entries
	 * @throws SystemException if a system exception occurred
	 */
	public List<BTEntry> findByG_S_T(long groupId, int status,
		String taskExecutorClassName) throws SystemException {
		return findByG_S_T(groupId, status, taskExecutorClassName,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the b t entries where groupId = &#63; and status = &#63; and taskExecutorClassName = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.backgroundtask.model.impl.BTEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @param taskExecutorClassName the task executor class name
	 * @param start the lower bound of the range of b t entries
	 * @param end the upper bound of the range of b t entries (not inclusive)
	 * @return the range of matching b t entries
	 * @throws SystemException if a system exception occurred
	 */
	public List<BTEntry> findByG_S_T(long groupId, int status,
		String taskExecutorClassName, int start, int end)
		throws SystemException {
		return findByG_S_T(groupId, status, taskExecutorClassName, start, end,
			null);
	}

	/**
	 * Returns an ordered range of all the b t entries where groupId = &#63; and status = &#63; and taskExecutorClassName = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.backgroundtask.model.impl.BTEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @param taskExecutorClassName the task executor class name
	 * @param start the lower bound of the range of b t entries
	 * @param end the upper bound of the range of b t entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching b t entries
	 * @throws SystemException if a system exception occurred
	 */
	public List<BTEntry> findByG_S_T(long groupId, int status,
		String taskExecutorClassName, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_S_T;
			finderArgs = new Object[] { groupId, status, taskExecutorClassName };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_G_S_T;
			finderArgs = new Object[] {
					groupId, status, taskExecutorClassName,
					
					start, end, orderByComparator
				};
		}

		List<BTEntry> list = (List<BTEntry>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (BTEntry btEntry : list) {
				if ((groupId != btEntry.getGroupId()) ||
						(status != btEntry.getStatus()) ||
						!Validator.equals(taskExecutorClassName,
							btEntry.getTaskExecutorClassName())) {
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

			query.append(_SQL_SELECT_BTENTRY_WHERE);

			query.append(_FINDER_COLUMN_G_S_T_GROUPID_2);

			query.append(_FINDER_COLUMN_G_S_T_STATUS_2);

			boolean bindTaskExecutorClassName = false;

			if (taskExecutorClassName == null) {
				query.append(_FINDER_COLUMN_G_S_T_TASKEXECUTORCLASSNAME_1);
			}
			else if (taskExecutorClassName.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_G_S_T_TASKEXECUTORCLASSNAME_3);
			}
			else {
				bindTaskExecutorClassName = true;

				query.append(_FINDER_COLUMN_G_S_T_TASKEXECUTORCLASSNAME_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(BTEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(status);

				if (bindTaskExecutorClassName) {
					qPos.add(taskExecutorClassName);
				}

				if (!pagination) {
					list = (List<BTEntry>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = new UnmodifiableList<BTEntry>(list);
				}
				else {
					list = (List<BTEntry>)QueryUtil.list(q, getDialect(),
							start, end);
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
	 * Returns the first b t entry in the ordered set where groupId = &#63; and status = &#63; and taskExecutorClassName = &#63;.
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @param taskExecutorClassName the task executor class name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching b t entry
	 * @throws com.liferay.portlet.backgroundtask.NoSuchEntryException if a matching b t entry could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public BTEntry findByG_S_T_First(long groupId, int status,
		String taskExecutorClassName, OrderByComparator orderByComparator)
		throws NoSuchEntryException, SystemException {
		BTEntry btEntry = fetchByG_S_T_First(groupId, status,
				taskExecutorClassName, orderByComparator);

		if (btEntry != null) {
			return btEntry;
		}

		StringBundler msg = new StringBundler(8);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", status=");
		msg.append(status);

		msg.append(", taskExecutorClassName=");
		msg.append(taskExecutorClassName);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchEntryException(msg.toString());
	}

	/**
	 * Returns the first b t entry in the ordered set where groupId = &#63; and status = &#63; and taskExecutorClassName = &#63;.
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @param taskExecutorClassName the task executor class name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching b t entry, or <code>null</code> if a matching b t entry could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public BTEntry fetchByG_S_T_First(long groupId, int status,
		String taskExecutorClassName, OrderByComparator orderByComparator)
		throws SystemException {
		List<BTEntry> list = findByG_S_T(groupId, status,
				taskExecutorClassName, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last b t entry in the ordered set where groupId = &#63; and status = &#63; and taskExecutorClassName = &#63;.
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @param taskExecutorClassName the task executor class name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching b t entry
	 * @throws com.liferay.portlet.backgroundtask.NoSuchEntryException if a matching b t entry could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public BTEntry findByG_S_T_Last(long groupId, int status,
		String taskExecutorClassName, OrderByComparator orderByComparator)
		throws NoSuchEntryException, SystemException {
		BTEntry btEntry = fetchByG_S_T_Last(groupId, status,
				taskExecutorClassName, orderByComparator);

		if (btEntry != null) {
			return btEntry;
		}

		StringBundler msg = new StringBundler(8);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", status=");
		msg.append(status);

		msg.append(", taskExecutorClassName=");
		msg.append(taskExecutorClassName);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchEntryException(msg.toString());
	}

	/**
	 * Returns the last b t entry in the ordered set where groupId = &#63; and status = &#63; and taskExecutorClassName = &#63;.
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @param taskExecutorClassName the task executor class name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching b t entry, or <code>null</code> if a matching b t entry could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public BTEntry fetchByG_S_T_Last(long groupId, int status,
		String taskExecutorClassName, OrderByComparator orderByComparator)
		throws SystemException {
		int count = countByG_S_T(groupId, status, taskExecutorClassName);

		List<BTEntry> list = findByG_S_T(groupId, status,
				taskExecutorClassName, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the b t entries before and after the current b t entry in the ordered set where groupId = &#63; and status = &#63; and taskExecutorClassName = &#63;.
	 *
	 * @param btEntryId the primary key of the current b t entry
	 * @param groupId the group ID
	 * @param status the status
	 * @param taskExecutorClassName the task executor class name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next b t entry
	 * @throws com.liferay.portlet.backgroundtask.NoSuchEntryException if a b t entry with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public BTEntry[] findByG_S_T_PrevAndNext(long btEntryId, long groupId,
		int status, String taskExecutorClassName,
		OrderByComparator orderByComparator)
		throws NoSuchEntryException, SystemException {
		BTEntry btEntry = findByPrimaryKey(btEntryId);

		Session session = null;

		try {
			session = openSession();

			BTEntry[] array = new BTEntryImpl[3];

			array[0] = getByG_S_T_PrevAndNext(session, btEntry, groupId,
					status, taskExecutorClassName, orderByComparator, true);

			array[1] = btEntry;

			array[2] = getByG_S_T_PrevAndNext(session, btEntry, groupId,
					status, taskExecutorClassName, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected BTEntry getByG_S_T_PrevAndNext(Session session, BTEntry btEntry,
		long groupId, int status, String taskExecutorClassName,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_BTENTRY_WHERE);

		query.append(_FINDER_COLUMN_G_S_T_GROUPID_2);

		query.append(_FINDER_COLUMN_G_S_T_STATUS_2);

		boolean bindTaskExecutorClassName = false;

		if (taskExecutorClassName == null) {
			query.append(_FINDER_COLUMN_G_S_T_TASKEXECUTORCLASSNAME_1);
		}
		else if (taskExecutorClassName.equals(StringPool.BLANK)) {
			query.append(_FINDER_COLUMN_G_S_T_TASKEXECUTORCLASSNAME_3);
		}
		else {
			bindTaskExecutorClassName = true;

			query.append(_FINDER_COLUMN_G_S_T_TASKEXECUTORCLASSNAME_2);
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
			query.append(BTEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		qPos.add(status);

		if (bindTaskExecutorClassName) {
			qPos.add(taskExecutorClassName);
		}

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(btEntry);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<BTEntry> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the b t entries where groupId = &#63; and status = &#63; and taskExecutorClassName = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @param taskExecutorClassName the task executor class name
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByG_S_T(long groupId, int status,
		String taskExecutorClassName) throws SystemException {
		for (BTEntry btEntry : findByG_S_T(groupId, status,
				taskExecutorClassName, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
				null)) {
			remove(btEntry);
		}
	}

	/**
	 * Returns the number of b t entries where groupId = &#63; and status = &#63; and taskExecutorClassName = &#63;.
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @param taskExecutorClassName the task executor class name
	 * @return the number of matching b t entries
	 * @throws SystemException if a system exception occurred
	 */
	public int countByG_S_T(long groupId, int status,
		String taskExecutorClassName) throws SystemException {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_G_S_T;

		Object[] finderArgs = new Object[] {
				groupId, status, taskExecutorClassName
			};

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs,
				this);

		if (count == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_COUNT_BTENTRY_WHERE);

			query.append(_FINDER_COLUMN_G_S_T_GROUPID_2);

			query.append(_FINDER_COLUMN_G_S_T_STATUS_2);

			boolean bindTaskExecutorClassName = false;

			if (taskExecutorClassName == null) {
				query.append(_FINDER_COLUMN_G_S_T_TASKEXECUTORCLASSNAME_1);
			}
			else if (taskExecutorClassName.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_G_S_T_TASKEXECUTORCLASSNAME_3);
			}
			else {
				bindTaskExecutorClassName = true;

				query.append(_FINDER_COLUMN_G_S_T_TASKEXECUTORCLASSNAME_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(status);

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

	private static final String _FINDER_COLUMN_G_S_T_GROUPID_2 = "btEntry.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_S_T_STATUS_2 = "btEntry.status = ? AND ";
	private static final String _FINDER_COLUMN_G_S_T_TASKEXECUTORCLASSNAME_1 = "btEntry.taskExecutorClassName IS NULL";
	private static final String _FINDER_COLUMN_G_S_T_TASKEXECUTORCLASSNAME_2 = "btEntry.taskExecutorClassName = ?";
	private static final String _FINDER_COLUMN_G_S_T_TASKEXECUTORCLASSNAME_3 = "(btEntry.taskExecutorClassName IS NULL OR btEntry.taskExecutorClassName = '')";

	/**
	 * Caches the b t entry in the entity cache if it is enabled.
	 *
	 * @param btEntry the b t entry
	 */
	public void cacheResult(BTEntry btEntry) {
		EntityCacheUtil.putResult(BTEntryModelImpl.ENTITY_CACHE_ENABLED,
			BTEntryImpl.class, btEntry.getPrimaryKey(), btEntry);

		btEntry.resetOriginalValues();
	}

	/**
	 * Caches the b t entries in the entity cache if it is enabled.
	 *
	 * @param btEntries the b t entries
	 */
	public void cacheResult(List<BTEntry> btEntries) {
		for (BTEntry btEntry : btEntries) {
			if (EntityCacheUtil.getResult(
						BTEntryModelImpl.ENTITY_CACHE_ENABLED,
						BTEntryImpl.class, btEntry.getPrimaryKey()) == null) {
				cacheResult(btEntry);
			}
			else {
				btEntry.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all b t entries.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		if (_HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			CacheRegistryUtil.clear(BTEntryImpl.class.getName());
		}

		EntityCacheUtil.clearCache(BTEntryImpl.class.getName());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the b t entry.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(BTEntry btEntry) {
		EntityCacheUtil.removeResult(BTEntryModelImpl.ENTITY_CACHE_ENABLED,
			BTEntryImpl.class, btEntry.getPrimaryKey());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@Override
	public void clearCache(List<BTEntry> btEntries) {
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (BTEntry btEntry : btEntries) {
			EntityCacheUtil.removeResult(BTEntryModelImpl.ENTITY_CACHE_ENABLED,
				BTEntryImpl.class, btEntry.getPrimaryKey());
		}
	}

	/**
	 * Creates a new b t entry with the primary key. Does not add the b t entry to the database.
	 *
	 * @param btEntryId the primary key for the new b t entry
	 * @return the new b t entry
	 */
	public BTEntry create(long btEntryId) {
		BTEntry btEntry = new BTEntryImpl();

		btEntry.setNew(true);
		btEntry.setPrimaryKey(btEntryId);

		return btEntry;
	}

	/**
	 * Removes the b t entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param btEntryId the primary key of the b t entry
	 * @return the b t entry that was removed
	 * @throws com.liferay.portlet.backgroundtask.NoSuchEntryException if a b t entry with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public BTEntry remove(long btEntryId)
		throws NoSuchEntryException, SystemException {
		return remove((Serializable)btEntryId);
	}

	/**
	 * Removes the b t entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the b t entry
	 * @return the b t entry that was removed
	 * @throws com.liferay.portlet.backgroundtask.NoSuchEntryException if a b t entry with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public BTEntry remove(Serializable primaryKey)
		throws NoSuchEntryException, SystemException {
		Session session = null;

		try {
			session = openSession();

			BTEntry btEntry = (BTEntry)session.get(BTEntryImpl.class, primaryKey);

			if (btEntry == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchEntryException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(btEntry);
		}
		catch (NoSuchEntryException nsee) {
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
	protected BTEntry removeImpl(BTEntry btEntry) throws SystemException {
		btEntry = toUnwrappedModel(btEntry);

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(btEntry)) {
				btEntry = (BTEntry)session.get(BTEntryImpl.class,
						btEntry.getPrimaryKeyObj());
			}

			if (btEntry != null) {
				session.delete(btEntry);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (btEntry != null) {
			clearCache(btEntry);
		}

		return btEntry;
	}

	@Override
	public BTEntry updateImpl(
		com.liferay.portlet.backgroundtask.model.BTEntry btEntry)
		throws SystemException {
		btEntry = toUnwrappedModel(btEntry);

		boolean isNew = btEntry.isNew();

		BTEntryModelImpl btEntryModelImpl = (BTEntryModelImpl)btEntry;

		Session session = null;

		try {
			session = openSession();

			if (btEntry.isNew()) {
				session.save(btEntry);

				btEntry.setNew(false);
			}
			else {
				session.merge(btEntry);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (isNew || !BTEntryModelImpl.COLUMN_BITMASK_ENABLED) {
			FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}

		else {
			if ((btEntryModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_T.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						btEntryModelImpl.getOriginalGroupId(),
						btEntryModelImpl.getOriginalTaskExecutorClassName()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_G_T, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_T,
					args);

				args = new Object[] {
						btEntryModelImpl.getGroupId(),
						btEntryModelImpl.getTaskExecutorClassName()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_G_T, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_T,
					args);
			}

			if ((btEntryModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_S_T.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						btEntryModelImpl.getOriginalGroupId(),
						btEntryModelImpl.getOriginalStatus(),
						btEntryModelImpl.getOriginalTaskExecutorClassName()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_G_S_T, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_S_T,
					args);

				args = new Object[] {
						btEntryModelImpl.getGroupId(),
						btEntryModelImpl.getStatus(),
						btEntryModelImpl.getTaskExecutorClassName()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_G_S_T, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_S_T,
					args);
			}
		}

		EntityCacheUtil.putResult(BTEntryModelImpl.ENTITY_CACHE_ENABLED,
			BTEntryImpl.class, btEntry.getPrimaryKey(), btEntry);

		return btEntry;
	}

	protected BTEntry toUnwrappedModel(BTEntry btEntry) {
		if (btEntry instanceof BTEntryImpl) {
			return btEntry;
		}

		BTEntryImpl btEntryImpl = new BTEntryImpl();

		btEntryImpl.setNew(btEntry.isNew());
		btEntryImpl.setPrimaryKey(btEntry.getPrimaryKey());

		btEntryImpl.setBtEntryId(btEntry.getBtEntryId());
		btEntryImpl.setGroupId(btEntry.getGroupId());
		btEntryImpl.setCompanyId(btEntry.getCompanyId());
		btEntryImpl.setUserId(btEntry.getUserId());
		btEntryImpl.setUserName(btEntry.getUserName());
		btEntryImpl.setCreateDate(btEntry.getCreateDate());
		btEntryImpl.setModifiedDate(btEntry.getModifiedDate());
		btEntryImpl.setCompletionDate(btEntry.getCompletionDate());
		btEntryImpl.setTaskContext(btEntry.getTaskContext());
		btEntryImpl.setTaskExecutorClassName(btEntry.getTaskExecutorClassName());
		btEntryImpl.setName(btEntry.getName());
		btEntryImpl.setServletContextNames(btEntry.getServletContextNames());
		btEntryImpl.setStatus(btEntry.getStatus());

		return btEntryImpl;
	}

	/**
	 * Returns the b t entry with the primary key or throws a {@link com.liferay.portal.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the b t entry
	 * @return the b t entry
	 * @throws com.liferay.portlet.backgroundtask.NoSuchEntryException if a b t entry with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public BTEntry findByPrimaryKey(Serializable primaryKey)
		throws NoSuchEntryException, SystemException {
		BTEntry btEntry = fetchByPrimaryKey(primaryKey);

		if (btEntry == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchEntryException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				primaryKey);
		}

		return btEntry;
	}

	/**
	 * Returns the b t entry with the primary key or throws a {@link com.liferay.portlet.backgroundtask.NoSuchEntryException} if it could not be found.
	 *
	 * @param btEntryId the primary key of the b t entry
	 * @return the b t entry
	 * @throws com.liferay.portlet.backgroundtask.NoSuchEntryException if a b t entry with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public BTEntry findByPrimaryKey(long btEntryId)
		throws NoSuchEntryException, SystemException {
		return findByPrimaryKey((Serializable)btEntryId);
	}

	/**
	 * Returns the b t entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the b t entry
	 * @return the b t entry, or <code>null</code> if a b t entry with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public BTEntry fetchByPrimaryKey(Serializable primaryKey)
		throws SystemException {
		BTEntry btEntry = (BTEntry)EntityCacheUtil.getResult(BTEntryModelImpl.ENTITY_CACHE_ENABLED,
				BTEntryImpl.class, primaryKey);

		if (btEntry == _nullBTEntry) {
			return null;
		}

		if (btEntry == null) {
			Session session = null;

			try {
				session = openSession();

				btEntry = (BTEntry)session.get(BTEntryImpl.class, primaryKey);

				if (btEntry != null) {
					cacheResult(btEntry);
				}
				else {
					EntityCacheUtil.putResult(BTEntryModelImpl.ENTITY_CACHE_ENABLED,
						BTEntryImpl.class, primaryKey, _nullBTEntry);
				}
			}
			catch (Exception e) {
				EntityCacheUtil.removeResult(BTEntryModelImpl.ENTITY_CACHE_ENABLED,
					BTEntryImpl.class, primaryKey);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return btEntry;
	}

	/**
	 * Returns the b t entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param btEntryId the primary key of the b t entry
	 * @return the b t entry, or <code>null</code> if a b t entry with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public BTEntry fetchByPrimaryKey(long btEntryId) throws SystemException {
		return fetchByPrimaryKey((Serializable)btEntryId);
	}

	/**
	 * Returns all the b t entries.
	 *
	 * @return the b t entries
	 * @throws SystemException if a system exception occurred
	 */
	public List<BTEntry> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the b t entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.backgroundtask.model.impl.BTEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of b t entries
	 * @param end the upper bound of the range of b t entries (not inclusive)
	 * @return the range of b t entries
	 * @throws SystemException if a system exception occurred
	 */
	public List<BTEntry> findAll(int start, int end) throws SystemException {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the b t entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.backgroundtask.model.impl.BTEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of b t entries
	 * @param end the upper bound of the range of b t entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of b t entries
	 * @throws SystemException if a system exception occurred
	 */
	public List<BTEntry> findAll(int start, int end,
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

		List<BTEntry> list = (List<BTEntry>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 3));

				query.append(_SQL_SELECT_BTENTRY);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_BTENTRY;

				if (pagination) {
					sql = sql.concat(BTEntryModelImpl.ORDER_BY_JPQL);
				}
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (!pagination) {
					list = (List<BTEntry>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = new UnmodifiableList<BTEntry>(list);
				}
				else {
					list = (List<BTEntry>)QueryUtil.list(q, getDialect(),
							start, end);
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
	 * Removes all the b t entries from the database.
	 *
	 * @throws SystemException if a system exception occurred
	 */
	public void removeAll() throws SystemException {
		for (BTEntry btEntry : findAll()) {
			remove(btEntry);
		}
	}

	/**
	 * Returns the number of b t entries.
	 *
	 * @return the number of b t entries
	 * @throws SystemException if a system exception occurred
	 */
	public int countAll() throws SystemException {
		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_BTENTRY);

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
	 * Initializes the b t entry persistence.
	 */
	public void afterPropertiesSet() {
		String[] listenerClassNames = StringUtil.split(GetterUtil.getString(
					com.liferay.portal.util.PropsUtil.get(
						"value.object.listener.com.liferay.portlet.backgroundtask.model.BTEntry")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<BTEntry>> listenersList = new ArrayList<ModelListener<BTEntry>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<BTEntry>)InstanceFactory.newInstance(
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
		EntityCacheUtil.removeCache(BTEntryImpl.class.getName());
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	private static final String _SQL_SELECT_BTENTRY = "SELECT btEntry FROM BTEntry btEntry";
	private static final String _SQL_SELECT_BTENTRY_WHERE = "SELECT btEntry FROM BTEntry btEntry WHERE ";
	private static final String _SQL_COUNT_BTENTRY = "SELECT COUNT(btEntry) FROM BTEntry btEntry";
	private static final String _SQL_COUNT_BTENTRY_WHERE = "SELECT COUNT(btEntry) FROM BTEntry btEntry WHERE ";
	private static final String _ORDER_BY_ENTITY_ALIAS = "btEntry.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No BTEntry exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No BTEntry exists with the key {";
	private static final boolean _HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE = com.liferay.portal.util.PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE;
	private static Log _log = LogFactoryUtil.getLog(BTEntryPersistenceImpl.class);
	private static BTEntry _nullBTEntry = new BTEntryImpl() {
			@Override
			public Object clone() {
				return this;
			}

			@Override
			public CacheModel<BTEntry> toCacheModel() {
				return _nullBTEntryCacheModel;
			}
		};

	private static CacheModel<BTEntry> _nullBTEntryCacheModel = new CacheModel<BTEntry>() {
			public BTEntry toEntityModel() {
				return _nullBTEntry;
			}
		};
}