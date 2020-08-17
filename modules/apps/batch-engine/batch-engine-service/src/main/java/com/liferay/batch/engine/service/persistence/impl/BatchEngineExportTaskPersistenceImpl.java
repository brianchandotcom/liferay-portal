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

package com.liferay.batch.engine.service.persistence.impl;

import com.liferay.batch.engine.exception.NoSuchExportTaskException;
import com.liferay.batch.engine.model.BatchEngineExportTask;
import com.liferay.batch.engine.model.BatchEngineExportTaskTable;
import com.liferay.batch.engine.model.impl.BatchEngineExportTaskImpl;
import com.liferay.batch.engine.model.impl.BatchEngineExportTaskModelImpl;
import com.liferay.batch.engine.service.persistence.BatchEngineExportTaskPersistence;
import com.liferay.batch.engine.service.persistence.impl.constants.BatchEnginePersistenceConstants;
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
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;

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
 * The persistence implementation for the batch engine export task service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Shuyang Zhou
 * @generated
 */
@Component(service = BatchEngineExportTaskPersistence.class)
public class BatchEngineExportTaskPersistenceImpl
	extends BasePersistenceImpl<BatchEngineExportTask>
	implements BatchEngineExportTaskPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>BatchEngineExportTaskUtil</code> to access the batch engine export task persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		BatchEngineExportTaskImpl.class.getName();

	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List1";

	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List2";

	private FinderPath _finderPathWithPaginationFindAll;
	private FinderPath _finderPathWithoutPaginationFindAll;
	private FinderPath _finderPathCountAll;
	private FinderPath _finderPathWithPaginationFindByUuid;
	private FinderPath _finderPathWithoutPaginationFindByUuid;
	private FinderPath _finderPathCountByUuid;

	/**
	 * Returns all the batch engine export tasks where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching batch engine export tasks
	 */
	@Override
	public List<BatchEngineExportTask> findByUuid(String uuid) {
		return findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the batch engine export tasks where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BatchEngineExportTaskModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of batch engine export tasks
	 * @param end the upper bound of the range of batch engine export tasks (not inclusive)
	 * @return the range of matching batch engine export tasks
	 */
	@Override
	public List<BatchEngineExportTask> findByUuid(
		String uuid, int start, int end) {

		return findByUuid(uuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the batch engine export tasks where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BatchEngineExportTaskModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of batch engine export tasks
	 * @param end the upper bound of the range of batch engine export tasks (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching batch engine export tasks
	 */
	@Override
	public List<BatchEngineExportTask> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<BatchEngineExportTask> orderByComparator) {

		return findByUuid(uuid, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the batch engine export tasks where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BatchEngineExportTaskModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of batch engine export tasks
	 * @param end the upper bound of the range of batch engine export tasks (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching batch engine export tasks
	 */
	@Override
	public List<BatchEngineExportTask> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<BatchEngineExportTask> orderByComparator,
		boolean useFinderCache) {

		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByUuid;
				finderArgs = new Object[] {uuid};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByUuid;
			finderArgs = new Object[] {uuid, start, end, orderByComparator};
		}

		List<BatchEngineExportTask> list = null;

		if (useFinderCache) {
			list = (List<BatchEngineExportTask>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (BatchEngineExportTask batchEngineExportTask : list) {
					if (!uuid.equals(batchEngineExportTask.getUuid())) {
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

			sb.append(_SQL_SELECT_BATCHENGINEEXPORTTASK_WHERE);

			boolean bindUuid = false;

			if (uuid.isEmpty()) {
				sb.append(_FINDER_COLUMN_UUID_UUID_3);
			}
			else {
				bindUuid = true;

				sb.append(_FINDER_COLUMN_UUID_UUID_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(BatchEngineExportTaskModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindUuid) {
					queryPos.add(uuid);
				}

				list = (List<BatchEngineExportTask>)QueryUtil.list(
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
	 * Returns the first batch engine export task in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching batch engine export task
	 * @throws NoSuchExportTaskException if a matching batch engine export task could not be found
	 */
	@Override
	public BatchEngineExportTask findByUuid_First(
			String uuid,
			OrderByComparator<BatchEngineExportTask> orderByComparator)
		throws NoSuchExportTaskException {

		BatchEngineExportTask batchEngineExportTask = fetchByUuid_First(
			uuid, orderByComparator);

		if (batchEngineExportTask != null) {
			return batchEngineExportTask;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append("}");

		throw new NoSuchExportTaskException(sb.toString());
	}

	/**
	 * Returns the first batch engine export task in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching batch engine export task, or <code>null</code> if a matching batch engine export task could not be found
	 */
	@Override
	public BatchEngineExportTask fetchByUuid_First(
		String uuid,
		OrderByComparator<BatchEngineExportTask> orderByComparator) {

		List<BatchEngineExportTask> list = findByUuid(
			uuid, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last batch engine export task in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching batch engine export task
	 * @throws NoSuchExportTaskException if a matching batch engine export task could not be found
	 */
	@Override
	public BatchEngineExportTask findByUuid_Last(
			String uuid,
			OrderByComparator<BatchEngineExportTask> orderByComparator)
		throws NoSuchExportTaskException {

		BatchEngineExportTask batchEngineExportTask = fetchByUuid_Last(
			uuid, orderByComparator);

		if (batchEngineExportTask != null) {
			return batchEngineExportTask;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append("}");

		throw new NoSuchExportTaskException(sb.toString());
	}

	/**
	 * Returns the last batch engine export task in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching batch engine export task, or <code>null</code> if a matching batch engine export task could not be found
	 */
	@Override
	public BatchEngineExportTask fetchByUuid_Last(
		String uuid,
		OrderByComparator<BatchEngineExportTask> orderByComparator) {

		int count = countByUuid(uuid);

		if (count == 0) {
			return null;
		}

		List<BatchEngineExportTask> list = findByUuid(
			uuid, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the batch engine export tasks before and after the current batch engine export task in the ordered set where uuid = &#63;.
	 *
	 * @param batchEngineExportTaskId the primary key of the current batch engine export task
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next batch engine export task
	 * @throws NoSuchExportTaskException if a batch engine export task with the primary key could not be found
	 */
	@Override
	public BatchEngineExportTask[] findByUuid_PrevAndNext(
			long batchEngineExportTaskId, String uuid,
			OrderByComparator<BatchEngineExportTask> orderByComparator)
		throws NoSuchExportTaskException {

		uuid = Objects.toString(uuid, "");

		BatchEngineExportTask batchEngineExportTask = findByPrimaryKey(
			batchEngineExportTaskId);

		Session session = null;

		try {
			session = openSession();

			BatchEngineExportTask[] array = new BatchEngineExportTaskImpl[3];

			array[0] = getByUuid_PrevAndNext(
				session, batchEngineExportTask, uuid, orderByComparator, true);

			array[1] = batchEngineExportTask;

			array[2] = getByUuid_PrevAndNext(
				session, batchEngineExportTask, uuid, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected BatchEngineExportTask getByUuid_PrevAndNext(
		Session session, BatchEngineExportTask batchEngineExportTask,
		String uuid, OrderByComparator<BatchEngineExportTask> orderByComparator,
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

		sb.append(_SQL_SELECT_BATCHENGINEEXPORTTASK_WHERE);

		boolean bindUuid = false;

		if (uuid.isEmpty()) {
			sb.append(_FINDER_COLUMN_UUID_UUID_3);
		}
		else {
			bindUuid = true;

			sb.append(_FINDER_COLUMN_UUID_UUID_2);
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
			sb.append(BatchEngineExportTaskModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		if (bindUuid) {
			queryPos.add(uuid);
		}

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						batchEngineExportTask)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<BatchEngineExportTask> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the batch engine export tasks where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	@Override
	public void removeByUuid(String uuid) {
		for (BatchEngineExportTask batchEngineExportTask :
				findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(batchEngineExportTask);
		}
	}

	/**
	 * Returns the number of batch engine export tasks where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching batch engine export tasks
	 */
	@Override
	public int countByUuid(String uuid) {
		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = _finderPathCountByUuid;

		Object[] finderArgs = new Object[] {uuid};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_BATCHENGINEEXPORTTASK_WHERE);

			boolean bindUuid = false;

			if (uuid.isEmpty()) {
				sb.append(_FINDER_COLUMN_UUID_UUID_3);
			}
			else {
				bindUuid = true;

				sb.append(_FINDER_COLUMN_UUID_UUID_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindUuid) {
					queryPos.add(uuid);
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

	private static final String _FINDER_COLUMN_UUID_UUID_2 =
		"batchEngineExportTask.uuid = ?";

	private static final String _FINDER_COLUMN_UUID_UUID_3 =
		"(batchEngineExportTask.uuid IS NULL OR batchEngineExportTask.uuid = '')";

	private FinderPath _finderPathWithPaginationFindByUuid_C;
	private FinderPath _finderPathWithoutPaginationFindByUuid_C;
	private FinderPath _finderPathCountByUuid_C;

	/**
	 * Returns all the batch engine export tasks where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching batch engine export tasks
	 */
	@Override
	public List<BatchEngineExportTask> findByUuid_C(
		String uuid, long companyId) {

		return findByUuid_C(
			uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the batch engine export tasks where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BatchEngineExportTaskModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of batch engine export tasks
	 * @param end the upper bound of the range of batch engine export tasks (not inclusive)
	 * @return the range of matching batch engine export tasks
	 */
	@Override
	public List<BatchEngineExportTask> findByUuid_C(
		String uuid, long companyId, int start, int end) {

		return findByUuid_C(uuid, companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the batch engine export tasks where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BatchEngineExportTaskModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of batch engine export tasks
	 * @param end the upper bound of the range of batch engine export tasks (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching batch engine export tasks
	 */
	@Override
	public List<BatchEngineExportTask> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<BatchEngineExportTask> orderByComparator) {

		return findByUuid_C(
			uuid, companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the batch engine export tasks where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BatchEngineExportTaskModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of batch engine export tasks
	 * @param end the upper bound of the range of batch engine export tasks (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching batch engine export tasks
	 */
	@Override
	public List<BatchEngineExportTask> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<BatchEngineExportTask> orderByComparator,
		boolean useFinderCache) {

		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByUuid_C;
				finderArgs = new Object[] {uuid, companyId};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByUuid_C;
			finderArgs = new Object[] {
				uuid, companyId, start, end, orderByComparator
			};
		}

		List<BatchEngineExportTask> list = null;

		if (useFinderCache) {
			list = (List<BatchEngineExportTask>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (BatchEngineExportTask batchEngineExportTask : list) {
					if (!uuid.equals(batchEngineExportTask.getUuid()) ||
						(companyId != batchEngineExportTask.getCompanyId())) {

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

			sb.append(_SQL_SELECT_BATCHENGINEEXPORTTASK_WHERE);

			boolean bindUuid = false;

			if (uuid.isEmpty()) {
				sb.append(_FINDER_COLUMN_UUID_C_UUID_3);
			}
			else {
				bindUuid = true;

				sb.append(_FINDER_COLUMN_UUID_C_UUID_2);
			}

			sb.append(_FINDER_COLUMN_UUID_C_COMPANYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(BatchEngineExportTaskModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindUuid) {
					queryPos.add(uuid);
				}

				queryPos.add(companyId);

				list = (List<BatchEngineExportTask>)QueryUtil.list(
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
	 * Returns the first batch engine export task in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching batch engine export task
	 * @throws NoSuchExportTaskException if a matching batch engine export task could not be found
	 */
	@Override
	public BatchEngineExportTask findByUuid_C_First(
			String uuid, long companyId,
			OrderByComparator<BatchEngineExportTask> orderByComparator)
		throws NoSuchExportTaskException {

		BatchEngineExportTask batchEngineExportTask = fetchByUuid_C_First(
			uuid, companyId, orderByComparator);

		if (batchEngineExportTask != null) {
			return batchEngineExportTask;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append(", companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchExportTaskException(sb.toString());
	}

	/**
	 * Returns the first batch engine export task in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching batch engine export task, or <code>null</code> if a matching batch engine export task could not be found
	 */
	@Override
	public BatchEngineExportTask fetchByUuid_C_First(
		String uuid, long companyId,
		OrderByComparator<BatchEngineExportTask> orderByComparator) {

		List<BatchEngineExportTask> list = findByUuid_C(
			uuid, companyId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last batch engine export task in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching batch engine export task
	 * @throws NoSuchExportTaskException if a matching batch engine export task could not be found
	 */
	@Override
	public BatchEngineExportTask findByUuid_C_Last(
			String uuid, long companyId,
			OrderByComparator<BatchEngineExportTask> orderByComparator)
		throws NoSuchExportTaskException {

		BatchEngineExportTask batchEngineExportTask = fetchByUuid_C_Last(
			uuid, companyId, orderByComparator);

		if (batchEngineExportTask != null) {
			return batchEngineExportTask;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append(", companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchExportTaskException(sb.toString());
	}

	/**
	 * Returns the last batch engine export task in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching batch engine export task, or <code>null</code> if a matching batch engine export task could not be found
	 */
	@Override
	public BatchEngineExportTask fetchByUuid_C_Last(
		String uuid, long companyId,
		OrderByComparator<BatchEngineExportTask> orderByComparator) {

		int count = countByUuid_C(uuid, companyId);

		if (count == 0) {
			return null;
		}

		List<BatchEngineExportTask> list = findByUuid_C(
			uuid, companyId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the batch engine export tasks before and after the current batch engine export task in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param batchEngineExportTaskId the primary key of the current batch engine export task
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next batch engine export task
	 * @throws NoSuchExportTaskException if a batch engine export task with the primary key could not be found
	 */
	@Override
	public BatchEngineExportTask[] findByUuid_C_PrevAndNext(
			long batchEngineExportTaskId, String uuid, long companyId,
			OrderByComparator<BatchEngineExportTask> orderByComparator)
		throws NoSuchExportTaskException {

		uuid = Objects.toString(uuid, "");

		BatchEngineExportTask batchEngineExportTask = findByPrimaryKey(
			batchEngineExportTaskId);

		Session session = null;

		try {
			session = openSession();

			BatchEngineExportTask[] array = new BatchEngineExportTaskImpl[3];

			array[0] = getByUuid_C_PrevAndNext(
				session, batchEngineExportTask, uuid, companyId,
				orderByComparator, true);

			array[1] = batchEngineExportTask;

			array[2] = getByUuid_C_PrevAndNext(
				session, batchEngineExportTask, uuid, companyId,
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

	protected BatchEngineExportTask getByUuid_C_PrevAndNext(
		Session session, BatchEngineExportTask batchEngineExportTask,
		String uuid, long companyId,
		OrderByComparator<BatchEngineExportTask> orderByComparator,
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

		sb.append(_SQL_SELECT_BATCHENGINEEXPORTTASK_WHERE);

		boolean bindUuid = false;

		if (uuid.isEmpty()) {
			sb.append(_FINDER_COLUMN_UUID_C_UUID_3);
		}
		else {
			bindUuid = true;

			sb.append(_FINDER_COLUMN_UUID_C_UUID_2);
		}

		sb.append(_FINDER_COLUMN_UUID_C_COMPANYID_2);

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
			sb.append(BatchEngineExportTaskModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		if (bindUuid) {
			queryPos.add(uuid);
		}

		queryPos.add(companyId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						batchEngineExportTask)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<BatchEngineExportTask> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the batch engine export tasks where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	@Override
	public void removeByUuid_C(String uuid, long companyId) {
		for (BatchEngineExportTask batchEngineExportTask :
				findByUuid_C(
					uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(batchEngineExportTask);
		}
	}

	/**
	 * Returns the number of batch engine export tasks where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching batch engine export tasks
	 */
	@Override
	public int countByUuid_C(String uuid, long companyId) {
		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = _finderPathCountByUuid_C;

		Object[] finderArgs = new Object[] {uuid, companyId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_BATCHENGINEEXPORTTASK_WHERE);

			boolean bindUuid = false;

			if (uuid.isEmpty()) {
				sb.append(_FINDER_COLUMN_UUID_C_UUID_3);
			}
			else {
				bindUuid = true;

				sb.append(_FINDER_COLUMN_UUID_C_UUID_2);
			}

			sb.append(_FINDER_COLUMN_UUID_C_COMPANYID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindUuid) {
					queryPos.add(uuid);
				}

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

	private static final String _FINDER_COLUMN_UUID_C_UUID_2 =
		"batchEngineExportTask.uuid = ? AND ";

	private static final String _FINDER_COLUMN_UUID_C_UUID_3 =
		"(batchEngineExportTask.uuid IS NULL OR batchEngineExportTask.uuid = '') AND ";

	private static final String _FINDER_COLUMN_UUID_C_COMPANYID_2 =
		"batchEngineExportTask.companyId = ?";

	private FinderPath _finderPathWithPaginationFindByExecuteStatus;
	private FinderPath _finderPathWithoutPaginationFindByExecuteStatus;
	private FinderPath _finderPathCountByExecuteStatus;

	/**
	 * Returns all the batch engine export tasks where executeStatus = &#63;.
	 *
	 * @param executeStatus the execute status
	 * @return the matching batch engine export tasks
	 */
	@Override
	public List<BatchEngineExportTask> findByExecuteStatus(
		String executeStatus) {

		return findByExecuteStatus(
			executeStatus, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the batch engine export tasks where executeStatus = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BatchEngineExportTaskModelImpl</code>.
	 * </p>
	 *
	 * @param executeStatus the execute status
	 * @param start the lower bound of the range of batch engine export tasks
	 * @param end the upper bound of the range of batch engine export tasks (not inclusive)
	 * @return the range of matching batch engine export tasks
	 */
	@Override
	public List<BatchEngineExportTask> findByExecuteStatus(
		String executeStatus, int start, int end) {

		return findByExecuteStatus(executeStatus, start, end, null);
	}

	/**
	 * Returns an ordered range of all the batch engine export tasks where executeStatus = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BatchEngineExportTaskModelImpl</code>.
	 * </p>
	 *
	 * @param executeStatus the execute status
	 * @param start the lower bound of the range of batch engine export tasks
	 * @param end the upper bound of the range of batch engine export tasks (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching batch engine export tasks
	 */
	@Override
	public List<BatchEngineExportTask> findByExecuteStatus(
		String executeStatus, int start, int end,
		OrderByComparator<BatchEngineExportTask> orderByComparator) {

		return findByExecuteStatus(
			executeStatus, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the batch engine export tasks where executeStatus = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BatchEngineExportTaskModelImpl</code>.
	 * </p>
	 *
	 * @param executeStatus the execute status
	 * @param start the lower bound of the range of batch engine export tasks
	 * @param end the upper bound of the range of batch engine export tasks (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching batch engine export tasks
	 */
	@Override
	public List<BatchEngineExportTask> findByExecuteStatus(
		String executeStatus, int start, int end,
		OrderByComparator<BatchEngineExportTask> orderByComparator,
		boolean useFinderCache) {

		executeStatus = Objects.toString(executeStatus, "");

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByExecuteStatus;
				finderArgs = new Object[] {executeStatus};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByExecuteStatus;
			finderArgs = new Object[] {
				executeStatus, start, end, orderByComparator
			};
		}

		List<BatchEngineExportTask> list = null;

		if (useFinderCache) {
			list = (List<BatchEngineExportTask>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (BatchEngineExportTask batchEngineExportTask : list) {
					if (!executeStatus.equals(
							batchEngineExportTask.getExecuteStatus())) {

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

			sb.append(_SQL_SELECT_BATCHENGINEEXPORTTASK_WHERE);

			boolean bindExecuteStatus = false;

			if (executeStatus.isEmpty()) {
				sb.append(_FINDER_COLUMN_EXECUTESTATUS_EXECUTESTATUS_3);
			}
			else {
				bindExecuteStatus = true;

				sb.append(_FINDER_COLUMN_EXECUTESTATUS_EXECUTESTATUS_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(BatchEngineExportTaskModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindExecuteStatus) {
					queryPos.add(executeStatus);
				}

				list = (List<BatchEngineExportTask>)QueryUtil.list(
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
	 * Returns the first batch engine export task in the ordered set where executeStatus = &#63;.
	 *
	 * @param executeStatus the execute status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching batch engine export task
	 * @throws NoSuchExportTaskException if a matching batch engine export task could not be found
	 */
	@Override
	public BatchEngineExportTask findByExecuteStatus_First(
			String executeStatus,
			OrderByComparator<BatchEngineExportTask> orderByComparator)
		throws NoSuchExportTaskException {

		BatchEngineExportTask batchEngineExportTask =
			fetchByExecuteStatus_First(executeStatus, orderByComparator);

		if (batchEngineExportTask != null) {
			return batchEngineExportTask;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("executeStatus=");
		sb.append(executeStatus);

		sb.append("}");

		throw new NoSuchExportTaskException(sb.toString());
	}

	/**
	 * Returns the first batch engine export task in the ordered set where executeStatus = &#63;.
	 *
	 * @param executeStatus the execute status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching batch engine export task, or <code>null</code> if a matching batch engine export task could not be found
	 */
	@Override
	public BatchEngineExportTask fetchByExecuteStatus_First(
		String executeStatus,
		OrderByComparator<BatchEngineExportTask> orderByComparator) {

		List<BatchEngineExportTask> list = findByExecuteStatus(
			executeStatus, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last batch engine export task in the ordered set where executeStatus = &#63;.
	 *
	 * @param executeStatus the execute status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching batch engine export task
	 * @throws NoSuchExportTaskException if a matching batch engine export task could not be found
	 */
	@Override
	public BatchEngineExportTask findByExecuteStatus_Last(
			String executeStatus,
			OrderByComparator<BatchEngineExportTask> orderByComparator)
		throws NoSuchExportTaskException {

		BatchEngineExportTask batchEngineExportTask = fetchByExecuteStatus_Last(
			executeStatus, orderByComparator);

		if (batchEngineExportTask != null) {
			return batchEngineExportTask;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("executeStatus=");
		sb.append(executeStatus);

		sb.append("}");

		throw new NoSuchExportTaskException(sb.toString());
	}

	/**
	 * Returns the last batch engine export task in the ordered set where executeStatus = &#63;.
	 *
	 * @param executeStatus the execute status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching batch engine export task, or <code>null</code> if a matching batch engine export task could not be found
	 */
	@Override
	public BatchEngineExportTask fetchByExecuteStatus_Last(
		String executeStatus,
		OrderByComparator<BatchEngineExportTask> orderByComparator) {

		int count = countByExecuteStatus(executeStatus);

		if (count == 0) {
			return null;
		}

		List<BatchEngineExportTask> list = findByExecuteStatus(
			executeStatus, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the batch engine export tasks before and after the current batch engine export task in the ordered set where executeStatus = &#63;.
	 *
	 * @param batchEngineExportTaskId the primary key of the current batch engine export task
	 * @param executeStatus the execute status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next batch engine export task
	 * @throws NoSuchExportTaskException if a batch engine export task with the primary key could not be found
	 */
	@Override
	public BatchEngineExportTask[] findByExecuteStatus_PrevAndNext(
			long batchEngineExportTaskId, String executeStatus,
			OrderByComparator<BatchEngineExportTask> orderByComparator)
		throws NoSuchExportTaskException {

		executeStatus = Objects.toString(executeStatus, "");

		BatchEngineExportTask batchEngineExportTask = findByPrimaryKey(
			batchEngineExportTaskId);

		Session session = null;

		try {
			session = openSession();

			BatchEngineExportTask[] array = new BatchEngineExportTaskImpl[3];

			array[0] = getByExecuteStatus_PrevAndNext(
				session, batchEngineExportTask, executeStatus,
				orderByComparator, true);

			array[1] = batchEngineExportTask;

			array[2] = getByExecuteStatus_PrevAndNext(
				session, batchEngineExportTask, executeStatus,
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

	protected BatchEngineExportTask getByExecuteStatus_PrevAndNext(
		Session session, BatchEngineExportTask batchEngineExportTask,
		String executeStatus,
		OrderByComparator<BatchEngineExportTask> orderByComparator,
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

		sb.append(_SQL_SELECT_BATCHENGINEEXPORTTASK_WHERE);

		boolean bindExecuteStatus = false;

		if (executeStatus.isEmpty()) {
			sb.append(_FINDER_COLUMN_EXECUTESTATUS_EXECUTESTATUS_3);
		}
		else {
			bindExecuteStatus = true;

			sb.append(_FINDER_COLUMN_EXECUTESTATUS_EXECUTESTATUS_2);
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
			sb.append(BatchEngineExportTaskModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		if (bindExecuteStatus) {
			queryPos.add(executeStatus);
		}

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						batchEngineExportTask)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<BatchEngineExportTask> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the batch engine export tasks where executeStatus = &#63; from the database.
	 *
	 * @param executeStatus the execute status
	 */
	@Override
	public void removeByExecuteStatus(String executeStatus) {
		for (BatchEngineExportTask batchEngineExportTask :
				findByExecuteStatus(
					executeStatus, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(batchEngineExportTask);
		}
	}

	/**
	 * Returns the number of batch engine export tasks where executeStatus = &#63;.
	 *
	 * @param executeStatus the execute status
	 * @return the number of matching batch engine export tasks
	 */
	@Override
	public int countByExecuteStatus(String executeStatus) {
		executeStatus = Objects.toString(executeStatus, "");

		FinderPath finderPath = _finderPathCountByExecuteStatus;

		Object[] finderArgs = new Object[] {executeStatus};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_BATCHENGINEEXPORTTASK_WHERE);

			boolean bindExecuteStatus = false;

			if (executeStatus.isEmpty()) {
				sb.append(_FINDER_COLUMN_EXECUTESTATUS_EXECUTESTATUS_3);
			}
			else {
				bindExecuteStatus = true;

				sb.append(_FINDER_COLUMN_EXECUTESTATUS_EXECUTESTATUS_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindExecuteStatus) {
					queryPos.add(executeStatus);
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

	private static final String _FINDER_COLUMN_EXECUTESTATUS_EXECUTESTATUS_2 =
		"batchEngineExportTask.executeStatus = ?";

	private static final String _FINDER_COLUMN_EXECUTESTATUS_EXECUTESTATUS_3 =
		"(batchEngineExportTask.executeStatus IS NULL OR batchEngineExportTask.executeStatus = '')";

	public BatchEngineExportTaskPersistenceImpl() {
		Map<String, String> dbColumnNames = new HashMap<String, String>();

		dbColumnNames.put("uuid", "uuid_");

		setDBColumnNames(dbColumnNames);

		setModelClass(BatchEngineExportTask.class);

		setModelImplClass(BatchEngineExportTaskImpl.class);
		setModelPKClass(long.class);

		setTable(BatchEngineExportTaskTable.INSTANCE);
	}

	/**
	 * Caches the batch engine export task in the entity cache if it is enabled.
	 *
	 * @param batchEngineExportTask the batch engine export task
	 */
	@Override
	public void cacheResult(BatchEngineExportTask batchEngineExportTask) {
		entityCache.putResult(
			BatchEngineExportTaskImpl.class,
			batchEngineExportTask.getPrimaryKey(), batchEngineExportTask);

		batchEngineExportTask.resetOriginalValues();
	}

	/**
	 * Caches the batch engine export tasks in the entity cache if it is enabled.
	 *
	 * @param batchEngineExportTasks the batch engine export tasks
	 */
	@Override
	public void cacheResult(
		List<BatchEngineExportTask> batchEngineExportTasks) {

		for (BatchEngineExportTask batchEngineExportTask :
				batchEngineExportTasks) {

			if (entityCache.getResult(
					BatchEngineExportTaskImpl.class,
					batchEngineExportTask.getPrimaryKey()) == null) {

				cacheResult(batchEngineExportTask);
			}
			else {
				batchEngineExportTask.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all batch engine export tasks.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(BatchEngineExportTaskImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the batch engine export task.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(BatchEngineExportTask batchEngineExportTask) {
		entityCache.removeResult(
			BatchEngineExportTaskImpl.class,
			batchEngineExportTask.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@Override
	public void clearCache(List<BatchEngineExportTask> batchEngineExportTasks) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (BatchEngineExportTask batchEngineExportTask :
				batchEngineExportTasks) {

			entityCache.removeResult(
				BatchEngineExportTaskImpl.class,
				batchEngineExportTask.getPrimaryKey());
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(
				BatchEngineExportTaskImpl.class, primaryKey);
		}
	}

	/**
	 * Creates a new batch engine export task with the primary key. Does not add the batch engine export task to the database.
	 *
	 * @param batchEngineExportTaskId the primary key for the new batch engine export task
	 * @return the new batch engine export task
	 */
	@Override
	public BatchEngineExportTask create(long batchEngineExportTaskId) {
		BatchEngineExportTask batchEngineExportTask =
			new BatchEngineExportTaskImpl();

		batchEngineExportTask.setNew(true);
		batchEngineExportTask.setPrimaryKey(batchEngineExportTaskId);

		String uuid = PortalUUIDUtil.generate();

		batchEngineExportTask.setUuid(uuid);

		batchEngineExportTask.setCompanyId(CompanyThreadLocal.getCompanyId());

		return batchEngineExportTask;
	}

	/**
	 * Removes the batch engine export task with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param batchEngineExportTaskId the primary key of the batch engine export task
	 * @return the batch engine export task that was removed
	 * @throws NoSuchExportTaskException if a batch engine export task with the primary key could not be found
	 */
	@Override
	public BatchEngineExportTask remove(long batchEngineExportTaskId)
		throws NoSuchExportTaskException {

		return remove((Serializable)batchEngineExportTaskId);
	}

	/**
	 * Removes the batch engine export task with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the batch engine export task
	 * @return the batch engine export task that was removed
	 * @throws NoSuchExportTaskException if a batch engine export task with the primary key could not be found
	 */
	@Override
	public BatchEngineExportTask remove(Serializable primaryKey)
		throws NoSuchExportTaskException {

		Session session = null;

		try {
			session = openSession();

			BatchEngineExportTask batchEngineExportTask =
				(BatchEngineExportTask)session.get(
					BatchEngineExportTaskImpl.class, primaryKey);

			if (batchEngineExportTask == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchExportTaskException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(batchEngineExportTask);
		}
		catch (NoSuchExportTaskException noSuchEntityException) {
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
	protected BatchEngineExportTask removeImpl(
		BatchEngineExportTask batchEngineExportTask) {

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(batchEngineExportTask)) {
				batchEngineExportTask = (BatchEngineExportTask)session.get(
					BatchEngineExportTaskImpl.class,
					batchEngineExportTask.getPrimaryKeyObj());
			}

			if (batchEngineExportTask != null) {
				session.delete(batchEngineExportTask);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (batchEngineExportTask != null) {
			clearCache(batchEngineExportTask);
		}

		return batchEngineExportTask;
	}

	@Override
	public BatchEngineExportTask updateImpl(
		BatchEngineExportTask batchEngineExportTask) {

		boolean isNew = batchEngineExportTask.isNew();

		if (!(batchEngineExportTask instanceof
				BatchEngineExportTaskModelImpl)) {

			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(batchEngineExportTask.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					batchEngineExportTask);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in batchEngineExportTask proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom BatchEngineExportTask implementation " +
					batchEngineExportTask.getClass());
		}

		BatchEngineExportTaskModelImpl batchEngineExportTaskModelImpl =
			(BatchEngineExportTaskModelImpl)batchEngineExportTask;

		if (Validator.isNull(batchEngineExportTask.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			batchEngineExportTask.setUuid(uuid);
		}

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Date now = new Date();

		if (isNew && (batchEngineExportTask.getCreateDate() == null)) {
			if (serviceContext == null) {
				batchEngineExportTask.setCreateDate(now);
			}
			else {
				batchEngineExportTask.setCreateDate(
					serviceContext.getCreateDate(now));
			}
		}

		if (!batchEngineExportTaskModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				batchEngineExportTask.setModifiedDate(now);
			}
			else {
				batchEngineExportTask.setModifiedDate(
					serviceContext.getModifiedDate(now));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (batchEngineExportTask.isNew()) {
				session.save(batchEngineExportTask);

				batchEngineExportTask.setNew(false);
			}
			else {
				session.evict(
					BatchEngineExportTaskImpl.class,
					batchEngineExportTask.getPrimaryKeyObj());

				session.saveOrUpdate(batchEngineExportTask);
			}

			session.flush();
			session.clear();
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (isNew) {
			Object[] args = new Object[] {
				batchEngineExportTaskModelImpl.getUuid()
			};

			finderCache.removeResult(_finderPathCountByUuid, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByUuid, args);

			args = new Object[] {
				batchEngineExportTaskModelImpl.getUuid(),
				batchEngineExportTaskModelImpl.getCompanyId()
			};

			finderCache.removeResult(_finderPathCountByUuid_C, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByUuid_C, args);

			args = new Object[] {
				batchEngineExportTaskModelImpl.getExecuteStatus()
			};

			finderCache.removeResult(_finderPathCountByExecuteStatus, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByExecuteStatus, args);

			finderCache.removeResult(_finderPathCountAll, FINDER_ARGS_EMPTY);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindAll, FINDER_ARGS_EMPTY);
		}
		else {
			if ((batchEngineExportTaskModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByUuid.getColumnBitmask()) !=
					 0) {

				Object[] args = new Object[] {
					batchEngineExportTaskModelImpl.getOriginalUuid()
				};

				finderCache.removeResult(_finderPathCountByUuid, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByUuid, args);

				args = new Object[] {batchEngineExportTaskModelImpl.getUuid()};

				finderCache.removeResult(_finderPathCountByUuid, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByUuid, args);
			}

			if ((batchEngineExportTaskModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByUuid_C.getColumnBitmask()) !=
					 0) {

				Object[] args = new Object[] {
					batchEngineExportTaskModelImpl.getOriginalUuid(),
					batchEngineExportTaskModelImpl.getOriginalCompanyId()
				};

				finderCache.removeResult(_finderPathCountByUuid_C, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByUuid_C, args);

				args = new Object[] {
					batchEngineExportTaskModelImpl.getUuid(),
					batchEngineExportTaskModelImpl.getCompanyId()
				};

				finderCache.removeResult(_finderPathCountByUuid_C, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByUuid_C, args);
			}

			if ((batchEngineExportTaskModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByExecuteStatus.
					 getColumnBitmask()) != 0) {

				Object[] args = new Object[] {
					batchEngineExportTaskModelImpl.getOriginalExecuteStatus()
				};

				finderCache.removeResult(_finderPathCountByExecuteStatus, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByExecuteStatus, args);

				args = new Object[] {
					batchEngineExportTaskModelImpl.getExecuteStatus()
				};

				finderCache.removeResult(_finderPathCountByExecuteStatus, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByExecuteStatus, args);
			}
		}

		entityCache.putResult(
			BatchEngineExportTaskImpl.class,
			batchEngineExportTask.getPrimaryKey(), batchEngineExportTask,
			false);

		batchEngineExportTask.resetOriginalValues();

		return batchEngineExportTask;
	}

	/**
	 * Returns the batch engine export task with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the batch engine export task
	 * @return the batch engine export task
	 * @throws NoSuchExportTaskException if a batch engine export task with the primary key could not be found
	 */
	@Override
	public BatchEngineExportTask findByPrimaryKey(Serializable primaryKey)
		throws NoSuchExportTaskException {

		BatchEngineExportTask batchEngineExportTask = fetchByPrimaryKey(
			primaryKey);

		if (batchEngineExportTask == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchExportTaskException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return batchEngineExportTask;
	}

	/**
	 * Returns the batch engine export task with the primary key or throws a <code>NoSuchExportTaskException</code> if it could not be found.
	 *
	 * @param batchEngineExportTaskId the primary key of the batch engine export task
	 * @return the batch engine export task
	 * @throws NoSuchExportTaskException if a batch engine export task with the primary key could not be found
	 */
	@Override
	public BatchEngineExportTask findByPrimaryKey(long batchEngineExportTaskId)
		throws NoSuchExportTaskException {

		return findByPrimaryKey((Serializable)batchEngineExportTaskId);
	}

	/**
	 * Returns the batch engine export task with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param batchEngineExportTaskId the primary key of the batch engine export task
	 * @return the batch engine export task, or <code>null</code> if a batch engine export task with the primary key could not be found
	 */
	@Override
	public BatchEngineExportTask fetchByPrimaryKey(
		long batchEngineExportTaskId) {

		return fetchByPrimaryKey((Serializable)batchEngineExportTaskId);
	}

	/**
	 * Returns all the batch engine export tasks.
	 *
	 * @return the batch engine export tasks
	 */
	@Override
	public List<BatchEngineExportTask> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the batch engine export tasks.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BatchEngineExportTaskModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of batch engine export tasks
	 * @param end the upper bound of the range of batch engine export tasks (not inclusive)
	 * @return the range of batch engine export tasks
	 */
	@Override
	public List<BatchEngineExportTask> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the batch engine export tasks.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BatchEngineExportTaskModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of batch engine export tasks
	 * @param end the upper bound of the range of batch engine export tasks (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of batch engine export tasks
	 */
	@Override
	public List<BatchEngineExportTask> findAll(
		int start, int end,
		OrderByComparator<BatchEngineExportTask> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the batch engine export tasks.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BatchEngineExportTaskModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of batch engine export tasks
	 * @param end the upper bound of the range of batch engine export tasks (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of batch engine export tasks
	 */
	@Override
	public List<BatchEngineExportTask> findAll(
		int start, int end,
		OrderByComparator<BatchEngineExportTask> orderByComparator,
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

		List<BatchEngineExportTask> list = null;

		if (useFinderCache) {
			list = (List<BatchEngineExportTask>)finderCache.getResult(
				finderPath, finderArgs, this);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_BATCHENGINEEXPORTTASK);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_BATCHENGINEEXPORTTASK;

				sql = sql.concat(BatchEngineExportTaskModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<BatchEngineExportTask>)QueryUtil.list(
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
	 * Removes all the batch engine export tasks from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (BatchEngineExportTask batchEngineExportTask : findAll()) {
			remove(batchEngineExportTask);
		}
	}

	/**
	 * Returns the number of batch engine export tasks.
	 *
	 * @return the number of batch engine export tasks
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(
			_finderPathCountAll, FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(
					_SQL_COUNT_BATCHENGINEEXPORTTASK);

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
		return "batchEngineExportTaskId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_BATCHENGINEEXPORTTASK;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return BatchEngineExportTaskModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the batch engine export task persistence.
	 */
	@Activate
	public void activate() {
		_finderPathWithPaginationFindAll = new FinderPath(
			BatchEngineExportTaskImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);

		_finderPathWithoutPaginationFindAll = new FinderPath(
			BatchEngineExportTaskImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll",
			new String[0]);

		_finderPathCountAll = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll",
			new String[0]);

		_finderPathWithPaginationFindByUuid = new FinderPath(
			BatchEngineExportTaskImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUuid",
			new String[] {
				String.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByUuid = new FinderPath(
			BatchEngineExportTaskImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid",
			new String[] {String.class.getName()},
			BatchEngineExportTaskModelImpl.UUID_COLUMN_BITMASK);

		_finderPathCountByUuid = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByUuid", new String[] {String.class.getName()});

		_finderPathWithPaginationFindByUuid_C = new FinderPath(
			BatchEngineExportTaskImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUuid_C",
			new String[] {
				String.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByUuid_C = new FinderPath(
			BatchEngineExportTaskImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid_C",
			new String[] {String.class.getName(), Long.class.getName()},
			BatchEngineExportTaskModelImpl.UUID_COLUMN_BITMASK |
			BatchEngineExportTaskModelImpl.COMPANYID_COLUMN_BITMASK);

		_finderPathCountByUuid_C = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByUuid_C",
			new String[] {String.class.getName(), Long.class.getName()});

		_finderPathWithPaginationFindByExecuteStatus = new FinderPath(
			BatchEngineExportTaskImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByExecuteStatus",
			new String[] {
				String.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByExecuteStatus = new FinderPath(
			BatchEngineExportTaskImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByExecuteStatus",
			new String[] {String.class.getName()},
			BatchEngineExportTaskModelImpl.EXECUTESTATUS_COLUMN_BITMASK);

		_finderPathCountByExecuteStatus = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByExecuteStatus", new String[] {String.class.getName()});
	}

	@Deactivate
	public void deactivate() {
		entityCache.removeCache(BatchEngineExportTaskImpl.class.getName());
		finderCache.removeCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@Override
	@Reference(
		target = BatchEnginePersistenceConstants.SERVICE_CONFIGURATION_FILTER,
		unbind = "-"
	)
	public void setConfiguration(Configuration configuration) {
	}

	@Override
	@Reference(
		target = BatchEnginePersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setDataSource(DataSource dataSource) {
		super.setDataSource(dataSource);
	}

	@Override
	@Reference(
		target = BatchEnginePersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setSessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	@Reference
	protected EntityCache entityCache;

	@Reference
	protected FinderCache finderCache;

	private static final String _SQL_SELECT_BATCHENGINEEXPORTTASK =
		"SELECT batchEngineExportTask FROM BatchEngineExportTask batchEngineExportTask";

	private static final String _SQL_SELECT_BATCHENGINEEXPORTTASK_WHERE =
		"SELECT batchEngineExportTask FROM BatchEngineExportTask batchEngineExportTask WHERE ";

	private static final String _SQL_COUNT_BATCHENGINEEXPORTTASK =
		"SELECT COUNT(batchEngineExportTask) FROM BatchEngineExportTask batchEngineExportTask";

	private static final String _SQL_COUNT_BATCHENGINEEXPORTTASK_WHERE =
		"SELECT COUNT(batchEngineExportTask) FROM BatchEngineExportTask batchEngineExportTask WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS =
		"batchEngineExportTask.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No BatchEngineExportTask exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No BatchEngineExportTask exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		BatchEngineExportTaskPersistenceImpl.class);

	private static final Set<String> _badColumnNames = SetUtil.fromArray(
		new String[] {"uuid"});

	static {
		try {
			Class.forName(BatchEnginePersistenceConstants.class.getName());
		}
		catch (ClassNotFoundException classNotFoundException) {
			throw new ExceptionInInitializerError(classNotFoundException);
		}
	}

}