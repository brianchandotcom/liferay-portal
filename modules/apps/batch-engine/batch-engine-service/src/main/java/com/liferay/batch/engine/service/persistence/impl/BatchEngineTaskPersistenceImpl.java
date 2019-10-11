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

import com.liferay.batch.engine.exception.NoSuchTaskException;
import com.liferay.batch.engine.model.BatchEngineTask;
import com.liferay.batch.engine.model.impl.BatchEngineTaskImpl;
import com.liferay.batch.engine.model.impl.BatchEngineTaskModelImpl;
import com.liferay.batch.engine.service.persistence.BatchEngineTaskPersistence;
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
import com.liferay.portal.kernel.util.GetterUtil;
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
 * The persistence implementation for the batch engine task service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Shuyang Zhou
 * @generated
 */
@Component(service = BatchEngineTaskPersistence.class)
public class BatchEngineTaskPersistenceImpl
	extends BasePersistenceImpl<BatchEngineTask>
	implements BatchEngineTaskPersistence {

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>BatchEngineTaskUtil</code> to access the batch engine task persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		BatchEngineTaskImpl.class.getName();

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
	 * Returns all the batch engine tasks where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching batch engine tasks
	 */
	@Override
	public List<BatchEngineTask> findByUuid(String uuid) {
		return findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the batch engine tasks where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BatchEngineTaskModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of batch engine tasks
	 * @param end the upper bound of the range of batch engine tasks (not inclusive)
	 * @return the range of matching batch engine tasks
	 */
	@Override
	public List<BatchEngineTask> findByUuid(String uuid, int start, int end) {
		return findByUuid(uuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the batch engine tasks where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BatchEngineTaskModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of batch engine tasks
	 * @param end the upper bound of the range of batch engine tasks (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching batch engine tasks
	 */
	@Override
	public List<BatchEngineTask> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<BatchEngineTask> orderByComparator) {

		return findByUuid(uuid, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the batch engine tasks where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BatchEngineTaskModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of batch engine tasks
	 * @param end the upper bound of the range of batch engine tasks (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching batch engine tasks
	 */
	@Override
	public List<BatchEngineTask> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<BatchEngineTask> orderByComparator,
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

		List<BatchEngineTask> list = null;

		if (useFinderCache) {
			list = (List<BatchEngineTask>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (BatchEngineTask batchEngineTask : list) {
					if (!uuid.equals(batchEngineTask.getUuid())) {
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

			query.append(_SQL_SELECT_BATCHENGINETASK_WHERE);

			boolean bindUuid = false;

			if (uuid.isEmpty()) {
				query.append(_FINDER_COLUMN_UUID_UUID_3);
			}
			else {
				bindUuid = true;

				query.append(_FINDER_COLUMN_UUID_UUID_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				query.append(BatchEngineTaskModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindUuid) {
					qPos.add(uuid);
				}

				list = (List<BatchEngineTask>)QueryUtil.list(
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
	 * Returns the first batch engine task in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching batch engine task
	 * @throws NoSuchTaskException if a matching batch engine task could not be found
	 */
	@Override
	public BatchEngineTask findByUuid_First(
			String uuid, OrderByComparator<BatchEngineTask> orderByComparator)
		throws NoSuchTaskException {

		BatchEngineTask batchEngineTask = fetchByUuid_First(
			uuid, orderByComparator);

		if (batchEngineTask != null) {
			return batchEngineTask;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append("}");

		throw new NoSuchTaskException(msg.toString());
	}

	/**
	 * Returns the first batch engine task in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching batch engine task, or <code>null</code> if a matching batch engine task could not be found
	 */
	@Override
	public BatchEngineTask fetchByUuid_First(
		String uuid, OrderByComparator<BatchEngineTask> orderByComparator) {

		List<BatchEngineTask> list = findByUuid(uuid, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last batch engine task in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching batch engine task
	 * @throws NoSuchTaskException if a matching batch engine task could not be found
	 */
	@Override
	public BatchEngineTask findByUuid_Last(
			String uuid, OrderByComparator<BatchEngineTask> orderByComparator)
		throws NoSuchTaskException {

		BatchEngineTask batchEngineTask = fetchByUuid_Last(
			uuid, orderByComparator);

		if (batchEngineTask != null) {
			return batchEngineTask;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append("}");

		throw new NoSuchTaskException(msg.toString());
	}

	/**
	 * Returns the last batch engine task in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching batch engine task, or <code>null</code> if a matching batch engine task could not be found
	 */
	@Override
	public BatchEngineTask fetchByUuid_Last(
		String uuid, OrderByComparator<BatchEngineTask> orderByComparator) {

		int count = countByUuid(uuid);

		if (count == 0) {
			return null;
		}

		List<BatchEngineTask> list = findByUuid(
			uuid, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the batch engine tasks before and after the current batch engine task in the ordered set where uuid = &#63;.
	 *
	 * @param batchEngineTaskId the primary key of the current batch engine task
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next batch engine task
	 * @throws NoSuchTaskException if a batch engine task with the primary key could not be found
	 */
	@Override
	public BatchEngineTask[] findByUuid_PrevAndNext(
			long batchEngineTaskId, String uuid,
			OrderByComparator<BatchEngineTask> orderByComparator)
		throws NoSuchTaskException {

		uuid = Objects.toString(uuid, "");

		BatchEngineTask batchEngineTask = findByPrimaryKey(batchEngineTaskId);

		Session session = null;

		try {
			session = openSession();

			BatchEngineTask[] array = new BatchEngineTaskImpl[3];

			array[0] = getByUuid_PrevAndNext(
				session, batchEngineTask, uuid, orderByComparator, true);

			array[1] = batchEngineTask;

			array[2] = getByUuid_PrevAndNext(
				session, batchEngineTask, uuid, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected BatchEngineTask getByUuid_PrevAndNext(
		Session session, BatchEngineTask batchEngineTask, String uuid,
		OrderByComparator<BatchEngineTask> orderByComparator,
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

		query.append(_SQL_SELECT_BATCHENGINETASK_WHERE);

		boolean bindUuid = false;

		if (uuid.isEmpty()) {
			query.append(_FINDER_COLUMN_UUID_UUID_3);
		}
		else {
			bindUuid = true;

			query.append(_FINDER_COLUMN_UUID_UUID_2);
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
			query.append(BatchEngineTaskModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		if (bindUuid) {
			qPos.add(uuid);
		}

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						batchEngineTask)) {

				qPos.add(orderByConditionValue);
			}
		}

		List<BatchEngineTask> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the batch engine tasks where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	@Override
	public void removeByUuid(String uuid) {
		for (BatchEngineTask batchEngineTask :
				findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(batchEngineTask);
		}
	}

	/**
	 * Returns the number of batch engine tasks where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching batch engine tasks
	 */
	@Override
	public int countByUuid(String uuid) {
		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = _finderPathCountByUuid;

		Object[] finderArgs = new Object[] {uuid};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_BATCHENGINETASK_WHERE);

			boolean bindUuid = false;

			if (uuid.isEmpty()) {
				query.append(_FINDER_COLUMN_UUID_UUID_3);
			}
			else {
				bindUuid = true;

				query.append(_FINDER_COLUMN_UUID_UUID_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindUuid) {
					qPos.add(uuid);
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

	private static final String _FINDER_COLUMN_UUID_UUID_2 =
		"batchEngineTask.uuid = ?";

	private static final String _FINDER_COLUMN_UUID_UUID_3 =
		"(batchEngineTask.uuid IS NULL OR batchEngineTask.uuid = '')";

	private FinderPath _finderPathWithPaginationFindByUuid_C;
	private FinderPath _finderPathWithoutPaginationFindByUuid_C;
	private FinderPath _finderPathCountByUuid_C;

	/**
	 * Returns all the batch engine tasks where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching batch engine tasks
	 */
	@Override
	public List<BatchEngineTask> findByUuid_C(String uuid, long companyId) {
		return findByUuid_C(
			uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the batch engine tasks where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BatchEngineTaskModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of batch engine tasks
	 * @param end the upper bound of the range of batch engine tasks (not inclusive)
	 * @return the range of matching batch engine tasks
	 */
	@Override
	public List<BatchEngineTask> findByUuid_C(
		String uuid, long companyId, int start, int end) {

		return findByUuid_C(uuid, companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the batch engine tasks where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BatchEngineTaskModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of batch engine tasks
	 * @param end the upper bound of the range of batch engine tasks (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching batch engine tasks
	 */
	@Override
	public List<BatchEngineTask> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<BatchEngineTask> orderByComparator) {

		return findByUuid_C(
			uuid, companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the batch engine tasks where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BatchEngineTaskModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of batch engine tasks
	 * @param end the upper bound of the range of batch engine tasks (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching batch engine tasks
	 */
	@Override
	public List<BatchEngineTask> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<BatchEngineTask> orderByComparator,
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

		List<BatchEngineTask> list = null;

		if (useFinderCache) {
			list = (List<BatchEngineTask>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (BatchEngineTask batchEngineTask : list) {
					if (!uuid.equals(batchEngineTask.getUuid()) ||
						(companyId != batchEngineTask.getCompanyId())) {

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

			query.append(_SQL_SELECT_BATCHENGINETASK_WHERE);

			boolean bindUuid = false;

			if (uuid.isEmpty()) {
				query.append(_FINDER_COLUMN_UUID_C_UUID_3);
			}
			else {
				bindUuid = true;

				query.append(_FINDER_COLUMN_UUID_C_UUID_2);
			}

			query.append(_FINDER_COLUMN_UUID_C_COMPANYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				query.append(BatchEngineTaskModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindUuid) {
					qPos.add(uuid);
				}

				qPos.add(companyId);

				list = (List<BatchEngineTask>)QueryUtil.list(
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
	 * Returns the first batch engine task in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching batch engine task
	 * @throws NoSuchTaskException if a matching batch engine task could not be found
	 */
	@Override
	public BatchEngineTask findByUuid_C_First(
			String uuid, long companyId,
			OrderByComparator<BatchEngineTask> orderByComparator)
		throws NoSuchTaskException {

		BatchEngineTask batchEngineTask = fetchByUuid_C_First(
			uuid, companyId, orderByComparator);

		if (batchEngineTask != null) {
			return batchEngineTask;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(", companyId=");
		msg.append(companyId);

		msg.append("}");

		throw new NoSuchTaskException(msg.toString());
	}

	/**
	 * Returns the first batch engine task in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching batch engine task, or <code>null</code> if a matching batch engine task could not be found
	 */
	@Override
	public BatchEngineTask fetchByUuid_C_First(
		String uuid, long companyId,
		OrderByComparator<BatchEngineTask> orderByComparator) {

		List<BatchEngineTask> list = findByUuid_C(
			uuid, companyId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last batch engine task in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching batch engine task
	 * @throws NoSuchTaskException if a matching batch engine task could not be found
	 */
	@Override
	public BatchEngineTask findByUuid_C_Last(
			String uuid, long companyId,
			OrderByComparator<BatchEngineTask> orderByComparator)
		throws NoSuchTaskException {

		BatchEngineTask batchEngineTask = fetchByUuid_C_Last(
			uuid, companyId, orderByComparator);

		if (batchEngineTask != null) {
			return batchEngineTask;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(", companyId=");
		msg.append(companyId);

		msg.append("}");

		throw new NoSuchTaskException(msg.toString());
	}

	/**
	 * Returns the last batch engine task in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching batch engine task, or <code>null</code> if a matching batch engine task could not be found
	 */
	@Override
	public BatchEngineTask fetchByUuid_C_Last(
		String uuid, long companyId,
		OrderByComparator<BatchEngineTask> orderByComparator) {

		int count = countByUuid_C(uuid, companyId);

		if (count == 0) {
			return null;
		}

		List<BatchEngineTask> list = findByUuid_C(
			uuid, companyId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the batch engine tasks before and after the current batch engine task in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param batchEngineTaskId the primary key of the current batch engine task
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next batch engine task
	 * @throws NoSuchTaskException if a batch engine task with the primary key could not be found
	 */
	@Override
	public BatchEngineTask[] findByUuid_C_PrevAndNext(
			long batchEngineTaskId, String uuid, long companyId,
			OrderByComparator<BatchEngineTask> orderByComparator)
		throws NoSuchTaskException {

		uuid = Objects.toString(uuid, "");

		BatchEngineTask batchEngineTask = findByPrimaryKey(batchEngineTaskId);

		Session session = null;

		try {
			session = openSession();

			BatchEngineTask[] array = new BatchEngineTaskImpl[3];

			array[0] = getByUuid_C_PrevAndNext(
				session, batchEngineTask, uuid, companyId, orderByComparator,
				true);

			array[1] = batchEngineTask;

			array[2] = getByUuid_C_PrevAndNext(
				session, batchEngineTask, uuid, companyId, orderByComparator,
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

	protected BatchEngineTask getByUuid_C_PrevAndNext(
		Session session, BatchEngineTask batchEngineTask, String uuid,
		long companyId, OrderByComparator<BatchEngineTask> orderByComparator,
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

		query.append(_SQL_SELECT_BATCHENGINETASK_WHERE);

		boolean bindUuid = false;

		if (uuid.isEmpty()) {
			query.append(_FINDER_COLUMN_UUID_C_UUID_3);
		}
		else {
			bindUuid = true;

			query.append(_FINDER_COLUMN_UUID_C_UUID_2);
		}

		query.append(_FINDER_COLUMN_UUID_C_COMPANYID_2);

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
			query.append(BatchEngineTaskModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		if (bindUuid) {
			qPos.add(uuid);
		}

		qPos.add(companyId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						batchEngineTask)) {

				qPos.add(orderByConditionValue);
			}
		}

		List<BatchEngineTask> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the batch engine tasks where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	@Override
	public void removeByUuid_C(String uuid, long companyId) {
		for (BatchEngineTask batchEngineTask :
				findByUuid_C(
					uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(batchEngineTask);
		}
	}

	/**
	 * Returns the number of batch engine tasks where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching batch engine tasks
	 */
	@Override
	public int countByUuid_C(String uuid, long companyId) {
		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = _finderPathCountByUuid_C;

		Object[] finderArgs = new Object[] {uuid, companyId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_BATCHENGINETASK_WHERE);

			boolean bindUuid = false;

			if (uuid.isEmpty()) {
				query.append(_FINDER_COLUMN_UUID_C_UUID_3);
			}
			else {
				bindUuid = true;

				query.append(_FINDER_COLUMN_UUID_C_UUID_2);
			}

			query.append(_FINDER_COLUMN_UUID_C_COMPANYID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindUuid) {
					qPos.add(uuid);
				}

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

	private static final String _FINDER_COLUMN_UUID_C_UUID_2 =
		"batchEngineTask.uuid = ? AND ";

	private static final String _FINDER_COLUMN_UUID_C_UUID_3 =
		"(batchEngineTask.uuid IS NULL OR batchEngineTask.uuid = '') AND ";

	private static final String _FINDER_COLUMN_UUID_C_COMPANYID_2 =
		"batchEngineTask.companyId = ?";

	private FinderPath _finderPathWithPaginationFindByExecuteStatus;
	private FinderPath _finderPathWithoutPaginationFindByExecuteStatus;
	private FinderPath _finderPathCountByExecuteStatus;

	/**
	 * Returns all the batch engine tasks where executeStatus = &#63;.
	 *
	 * @param executeStatus the execute status
	 * @return the matching batch engine tasks
	 */
	@Override
	public List<BatchEngineTask> findByExecuteStatus(String executeStatus) {
		return findByExecuteStatus(
			executeStatus, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the batch engine tasks where executeStatus = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BatchEngineTaskModelImpl</code>.
	 * </p>
	 *
	 * @param executeStatus the execute status
	 * @param start the lower bound of the range of batch engine tasks
	 * @param end the upper bound of the range of batch engine tasks (not inclusive)
	 * @return the range of matching batch engine tasks
	 */
	@Override
	public List<BatchEngineTask> findByExecuteStatus(
		String executeStatus, int start, int end) {

		return findByExecuteStatus(executeStatus, start, end, null);
	}

	/**
	 * Returns an ordered range of all the batch engine tasks where executeStatus = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BatchEngineTaskModelImpl</code>.
	 * </p>
	 *
	 * @param executeStatus the execute status
	 * @param start the lower bound of the range of batch engine tasks
	 * @param end the upper bound of the range of batch engine tasks (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching batch engine tasks
	 */
	@Override
	public List<BatchEngineTask> findByExecuteStatus(
		String executeStatus, int start, int end,
		OrderByComparator<BatchEngineTask> orderByComparator) {

		return findByExecuteStatus(
			executeStatus, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the batch engine tasks where executeStatus = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BatchEngineTaskModelImpl</code>.
	 * </p>
	 *
	 * @param executeStatus the execute status
	 * @param start the lower bound of the range of batch engine tasks
	 * @param end the upper bound of the range of batch engine tasks (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching batch engine tasks
	 */
	@Override
	public List<BatchEngineTask> findByExecuteStatus(
		String executeStatus, int start, int end,
		OrderByComparator<BatchEngineTask> orderByComparator,
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

		List<BatchEngineTask> list = null;

		if (useFinderCache) {
			list = (List<BatchEngineTask>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (BatchEngineTask batchEngineTask : list) {
					if (!executeStatus.equals(
							batchEngineTask.getExecuteStatus())) {

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

			query.append(_SQL_SELECT_BATCHENGINETASK_WHERE);

			boolean bindExecuteStatus = false;

			if (executeStatus.isEmpty()) {
				query.append(_FINDER_COLUMN_EXECUTESTATUS_EXECUTESTATUS_3);
			}
			else {
				bindExecuteStatus = true;

				query.append(_FINDER_COLUMN_EXECUTESTATUS_EXECUTESTATUS_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				query.append(BatchEngineTaskModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindExecuteStatus) {
					qPos.add(executeStatus);
				}

				list = (List<BatchEngineTask>)QueryUtil.list(
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
	 * Returns the first batch engine task in the ordered set where executeStatus = &#63;.
	 *
	 * @param executeStatus the execute status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching batch engine task
	 * @throws NoSuchTaskException if a matching batch engine task could not be found
	 */
	@Override
	public BatchEngineTask findByExecuteStatus_First(
			String executeStatus,
			OrderByComparator<BatchEngineTask> orderByComparator)
		throws NoSuchTaskException {

		BatchEngineTask batchEngineTask = fetchByExecuteStatus_First(
			executeStatus, orderByComparator);

		if (batchEngineTask != null) {
			return batchEngineTask;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("executeStatus=");
		msg.append(executeStatus);

		msg.append("}");

		throw new NoSuchTaskException(msg.toString());
	}

	/**
	 * Returns the first batch engine task in the ordered set where executeStatus = &#63;.
	 *
	 * @param executeStatus the execute status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching batch engine task, or <code>null</code> if a matching batch engine task could not be found
	 */
	@Override
	public BatchEngineTask fetchByExecuteStatus_First(
		String executeStatus,
		OrderByComparator<BatchEngineTask> orderByComparator) {

		List<BatchEngineTask> list = findByExecuteStatus(
			executeStatus, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last batch engine task in the ordered set where executeStatus = &#63;.
	 *
	 * @param executeStatus the execute status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching batch engine task
	 * @throws NoSuchTaskException if a matching batch engine task could not be found
	 */
	@Override
	public BatchEngineTask findByExecuteStatus_Last(
			String executeStatus,
			OrderByComparator<BatchEngineTask> orderByComparator)
		throws NoSuchTaskException {

		BatchEngineTask batchEngineTask = fetchByExecuteStatus_Last(
			executeStatus, orderByComparator);

		if (batchEngineTask != null) {
			return batchEngineTask;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("executeStatus=");
		msg.append(executeStatus);

		msg.append("}");

		throw new NoSuchTaskException(msg.toString());
	}

	/**
	 * Returns the last batch engine task in the ordered set where executeStatus = &#63;.
	 *
	 * @param executeStatus the execute status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching batch engine task, or <code>null</code> if a matching batch engine task could not be found
	 */
	@Override
	public BatchEngineTask fetchByExecuteStatus_Last(
		String executeStatus,
		OrderByComparator<BatchEngineTask> orderByComparator) {

		int count = countByExecuteStatus(executeStatus);

		if (count == 0) {
			return null;
		}

		List<BatchEngineTask> list = findByExecuteStatus(
			executeStatus, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the batch engine tasks before and after the current batch engine task in the ordered set where executeStatus = &#63;.
	 *
	 * @param batchEngineTaskId the primary key of the current batch engine task
	 * @param executeStatus the execute status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next batch engine task
	 * @throws NoSuchTaskException if a batch engine task with the primary key could not be found
	 */
	@Override
	public BatchEngineTask[] findByExecuteStatus_PrevAndNext(
			long batchEngineTaskId, String executeStatus,
			OrderByComparator<BatchEngineTask> orderByComparator)
		throws NoSuchTaskException {

		executeStatus = Objects.toString(executeStatus, "");

		BatchEngineTask batchEngineTask = findByPrimaryKey(batchEngineTaskId);

		Session session = null;

		try {
			session = openSession();

			BatchEngineTask[] array = new BatchEngineTaskImpl[3];

			array[0] = getByExecuteStatus_PrevAndNext(
				session, batchEngineTask, executeStatus, orderByComparator,
				true);

			array[1] = batchEngineTask;

			array[2] = getByExecuteStatus_PrevAndNext(
				session, batchEngineTask, executeStatus, orderByComparator,
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

	protected BatchEngineTask getByExecuteStatus_PrevAndNext(
		Session session, BatchEngineTask batchEngineTask, String executeStatus,
		OrderByComparator<BatchEngineTask> orderByComparator,
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

		query.append(_SQL_SELECT_BATCHENGINETASK_WHERE);

		boolean bindExecuteStatus = false;

		if (executeStatus.isEmpty()) {
			query.append(_FINDER_COLUMN_EXECUTESTATUS_EXECUTESTATUS_3);
		}
		else {
			bindExecuteStatus = true;

			query.append(_FINDER_COLUMN_EXECUTESTATUS_EXECUTESTATUS_2);
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
			query.append(BatchEngineTaskModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		if (bindExecuteStatus) {
			qPos.add(executeStatus);
		}

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						batchEngineTask)) {

				qPos.add(orderByConditionValue);
			}
		}

		List<BatchEngineTask> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the batch engine tasks where executeStatus = &#63; from the database.
	 *
	 * @param executeStatus the execute status
	 */
	@Override
	public void removeByExecuteStatus(String executeStatus) {
		for (BatchEngineTask batchEngineTask :
				findByExecuteStatus(
					executeStatus, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(batchEngineTask);
		}
	}

	/**
	 * Returns the number of batch engine tasks where executeStatus = &#63;.
	 *
	 * @param executeStatus the execute status
	 * @return the number of matching batch engine tasks
	 */
	@Override
	public int countByExecuteStatus(String executeStatus) {
		executeStatus = Objects.toString(executeStatus, "");

		FinderPath finderPath = _finderPathCountByExecuteStatus;

		Object[] finderArgs = new Object[] {executeStatus};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_BATCHENGINETASK_WHERE);

			boolean bindExecuteStatus = false;

			if (executeStatus.isEmpty()) {
				query.append(_FINDER_COLUMN_EXECUTESTATUS_EXECUTESTATUS_3);
			}
			else {
				bindExecuteStatus = true;

				query.append(_FINDER_COLUMN_EXECUTESTATUS_EXECUTESTATUS_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindExecuteStatus) {
					qPos.add(executeStatus);
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

	private static final String _FINDER_COLUMN_EXECUTESTATUS_EXECUTESTATUS_2 =
		"batchEngineTask.executeStatus = ?";

	private static final String _FINDER_COLUMN_EXECUTESTATUS_EXECUTESTATUS_3 =
		"(batchEngineTask.executeStatus IS NULL OR batchEngineTask.executeStatus = '')";

	public BatchEngineTaskPersistenceImpl() {
		setModelClass(BatchEngineTask.class);

		setModelImplClass(BatchEngineTaskImpl.class);
		setModelPKClass(long.class);

		Map<String, String> dbColumnNames = new HashMap<String, String>();

		dbColumnNames.put("uuid", "uuid_");

		setDBColumnNames(dbColumnNames);
	}

	/**
	 * Caches the batch engine task in the entity cache if it is enabled.
	 *
	 * @param batchEngineTask the batch engine task
	 */
	@Override
	public void cacheResult(BatchEngineTask batchEngineTask) {
		entityCache.putResult(
			entityCacheEnabled, BatchEngineTaskImpl.class,
			batchEngineTask.getPrimaryKey(), batchEngineTask);

		batchEngineTask.resetOriginalValues();
	}

	/**
	 * Caches the batch engine tasks in the entity cache if it is enabled.
	 *
	 * @param batchEngineTasks the batch engine tasks
	 */
	@Override
	public void cacheResult(List<BatchEngineTask> batchEngineTasks) {
		for (BatchEngineTask batchEngineTask : batchEngineTasks) {
			if (entityCache.getResult(
					entityCacheEnabled, BatchEngineTaskImpl.class,
					batchEngineTask.getPrimaryKey()) == null) {

				cacheResult(batchEngineTask);
			}
			else {
				batchEngineTask.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all batch engine tasks.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(BatchEngineTaskImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the batch engine task.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(BatchEngineTask batchEngineTask) {
		entityCache.removeResult(
			entityCacheEnabled, BatchEngineTaskImpl.class,
			batchEngineTask.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@Override
	public void clearCache(List<BatchEngineTask> batchEngineTasks) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (BatchEngineTask batchEngineTask : batchEngineTasks) {
			entityCache.removeResult(
				entityCacheEnabled, BatchEngineTaskImpl.class,
				batchEngineTask.getPrimaryKey());
		}
	}

	/**
	 * Creates a new batch engine task with the primary key. Does not add the batch engine task to the database.
	 *
	 * @param batchEngineTaskId the primary key for the new batch engine task
	 * @return the new batch engine task
	 */
	@Override
	public BatchEngineTask create(long batchEngineTaskId) {
		BatchEngineTask batchEngineTask = new BatchEngineTaskImpl();

		batchEngineTask.setNew(true);
		batchEngineTask.setPrimaryKey(batchEngineTaskId);

		String uuid = PortalUUIDUtil.generate();

		batchEngineTask.setUuid(uuid);

		batchEngineTask.setCompanyId(CompanyThreadLocal.getCompanyId());

		return batchEngineTask;
	}

	/**
	 * Removes the batch engine task with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param batchEngineTaskId the primary key of the batch engine task
	 * @return the batch engine task that was removed
	 * @throws NoSuchTaskException if a batch engine task with the primary key could not be found
	 */
	@Override
	public BatchEngineTask remove(long batchEngineTaskId)
		throws NoSuchTaskException {

		return remove((Serializable)batchEngineTaskId);
	}

	/**
	 * Removes the batch engine task with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the batch engine task
	 * @return the batch engine task that was removed
	 * @throws NoSuchTaskException if a batch engine task with the primary key could not be found
	 */
	@Override
	public BatchEngineTask remove(Serializable primaryKey)
		throws NoSuchTaskException {

		Session session = null;

		try {
			session = openSession();

			BatchEngineTask batchEngineTask = (BatchEngineTask)session.get(
				BatchEngineTaskImpl.class, primaryKey);

			if (batchEngineTask == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchTaskException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(batchEngineTask);
		}
		catch (NoSuchTaskException nsee) {
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
	protected BatchEngineTask removeImpl(BatchEngineTask batchEngineTask) {
		Session session = null;

		try {
			session = openSession();

			if (!session.contains(batchEngineTask)) {
				batchEngineTask = (BatchEngineTask)session.get(
					BatchEngineTaskImpl.class,
					batchEngineTask.getPrimaryKeyObj());
			}

			if (batchEngineTask != null) {
				session.delete(batchEngineTask);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (batchEngineTask != null) {
			clearCache(batchEngineTask);
		}

		return batchEngineTask;
	}

	@Override
	public BatchEngineTask updateImpl(BatchEngineTask batchEngineTask) {
		boolean isNew = batchEngineTask.isNew();

		if (!(batchEngineTask instanceof BatchEngineTaskModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(batchEngineTask.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					batchEngineTask);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in batchEngineTask proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom BatchEngineTask implementation " +
					batchEngineTask.getClass());
		}

		BatchEngineTaskModelImpl batchEngineTaskModelImpl =
			(BatchEngineTaskModelImpl)batchEngineTask;

		if (Validator.isNull(batchEngineTask.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			batchEngineTask.setUuid(uuid);
		}

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Date now = new Date();

		if (isNew && (batchEngineTask.getCreateDate() == null)) {
			if (serviceContext == null) {
				batchEngineTask.setCreateDate(now);
			}
			else {
				batchEngineTask.setCreateDate(
					serviceContext.getCreateDate(now));
			}
		}

		if (!batchEngineTaskModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				batchEngineTask.setModifiedDate(now);
			}
			else {
				batchEngineTask.setModifiedDate(
					serviceContext.getModifiedDate(now));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (batchEngineTask.isNew()) {
				session.save(batchEngineTask);

				batchEngineTask.setNew(false);
			}
			else {
				session.evict(batchEngineTask);
				session.saveOrUpdate(batchEngineTask);
			}

			session.flush();
			session.clear();
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
			Object[] args = new Object[] {batchEngineTaskModelImpl.getUuid()};

			finderCache.removeResult(_finderPathCountByUuid, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByUuid, args);

			args = new Object[] {
				batchEngineTaskModelImpl.getUuid(),
				batchEngineTaskModelImpl.getCompanyId()
			};

			finderCache.removeResult(_finderPathCountByUuid_C, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByUuid_C, args);

			args = new Object[] {batchEngineTaskModelImpl.getExecuteStatus()};

			finderCache.removeResult(_finderPathCountByExecuteStatus, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByExecuteStatus, args);

			finderCache.removeResult(_finderPathCountAll, FINDER_ARGS_EMPTY);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindAll, FINDER_ARGS_EMPTY);
		}
		else {
			if ((batchEngineTaskModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByUuid.getColumnBitmask()) !=
					 0) {

				Object[] args = new Object[] {
					batchEngineTaskModelImpl.getOriginalUuid()
				};

				finderCache.removeResult(_finderPathCountByUuid, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByUuid, args);

				args = new Object[] {batchEngineTaskModelImpl.getUuid()};

				finderCache.removeResult(_finderPathCountByUuid, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByUuid, args);
			}

			if ((batchEngineTaskModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByUuid_C.getColumnBitmask()) !=
					 0) {

				Object[] args = new Object[] {
					batchEngineTaskModelImpl.getOriginalUuid(),
					batchEngineTaskModelImpl.getOriginalCompanyId()
				};

				finderCache.removeResult(_finderPathCountByUuid_C, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByUuid_C, args);

				args = new Object[] {
					batchEngineTaskModelImpl.getUuid(),
					batchEngineTaskModelImpl.getCompanyId()
				};

				finderCache.removeResult(_finderPathCountByUuid_C, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByUuid_C, args);
			}

			if ((batchEngineTaskModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByExecuteStatus.
					 getColumnBitmask()) != 0) {

				Object[] args = new Object[] {
					batchEngineTaskModelImpl.getOriginalExecuteStatus()
				};

				finderCache.removeResult(_finderPathCountByExecuteStatus, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByExecuteStatus, args);

				args = new Object[] {
					batchEngineTaskModelImpl.getExecuteStatus()
				};

				finderCache.removeResult(_finderPathCountByExecuteStatus, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByExecuteStatus, args);
			}
		}

		entityCache.putResult(
			entityCacheEnabled, BatchEngineTaskImpl.class,
			batchEngineTask.getPrimaryKey(), batchEngineTask, false);

		batchEngineTask.resetOriginalValues();

		return batchEngineTask;
	}

	/**
	 * Returns the batch engine task with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the batch engine task
	 * @return the batch engine task
	 * @throws NoSuchTaskException if a batch engine task with the primary key could not be found
	 */
	@Override
	public BatchEngineTask findByPrimaryKey(Serializable primaryKey)
		throws NoSuchTaskException {

		BatchEngineTask batchEngineTask = fetchByPrimaryKey(primaryKey);

		if (batchEngineTask == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchTaskException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return batchEngineTask;
	}

	/**
	 * Returns the batch engine task with the primary key or throws a <code>NoSuchTaskException</code> if it could not be found.
	 *
	 * @param batchEngineTaskId the primary key of the batch engine task
	 * @return the batch engine task
	 * @throws NoSuchTaskException if a batch engine task with the primary key could not be found
	 */
	@Override
	public BatchEngineTask findByPrimaryKey(long batchEngineTaskId)
		throws NoSuchTaskException {

		return findByPrimaryKey((Serializable)batchEngineTaskId);
	}

	/**
	 * Returns the batch engine task with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param batchEngineTaskId the primary key of the batch engine task
	 * @return the batch engine task, or <code>null</code> if a batch engine task with the primary key could not be found
	 */
	@Override
	public BatchEngineTask fetchByPrimaryKey(long batchEngineTaskId) {
		return fetchByPrimaryKey((Serializable)batchEngineTaskId);
	}

	/**
	 * Returns all the batch engine tasks.
	 *
	 * @return the batch engine tasks
	 */
	@Override
	public List<BatchEngineTask> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the batch engine tasks.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BatchEngineTaskModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of batch engine tasks
	 * @param end the upper bound of the range of batch engine tasks (not inclusive)
	 * @return the range of batch engine tasks
	 */
	@Override
	public List<BatchEngineTask> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the batch engine tasks.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BatchEngineTaskModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of batch engine tasks
	 * @param end the upper bound of the range of batch engine tasks (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of batch engine tasks
	 */
	@Override
	public List<BatchEngineTask> findAll(
		int start, int end,
		OrderByComparator<BatchEngineTask> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the batch engine tasks.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BatchEngineTaskModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of batch engine tasks
	 * @param end the upper bound of the range of batch engine tasks (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of batch engine tasks
	 */
	@Override
	public List<BatchEngineTask> findAll(
		int start, int end,
		OrderByComparator<BatchEngineTask> orderByComparator,
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

		List<BatchEngineTask> list = null;

		if (useFinderCache) {
			list = (List<BatchEngineTask>)finderCache.getResult(
				finderPath, finderArgs, this);
		}

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				query.append(_SQL_SELECT_BATCHENGINETASK);

				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_BATCHENGINETASK;

				sql = sql.concat(BatchEngineTaskModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				list = (List<BatchEngineTask>)QueryUtil.list(
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
	 * Removes all the batch engine tasks from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (BatchEngineTask batchEngineTask : findAll()) {
			remove(batchEngineTask);
		}
	}

	/**
	 * Returns the number of batch engine tasks.
	 *
	 * @return the number of batch engine tasks
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(
			_finderPathCountAll, FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_BATCHENGINETASK);

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
		return "batchEngineTaskId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_BATCHENGINETASK;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return BatchEngineTaskModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the batch engine task persistence.
	 */
	@Activate
	public void activate() {
		BatchEngineTaskModelImpl.setEntityCacheEnabled(entityCacheEnabled);
		BatchEngineTaskModelImpl.setFinderCacheEnabled(finderCacheEnabled);

		_finderPathWithPaginationFindAll = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, BatchEngineTaskImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);

		_finderPathWithoutPaginationFindAll = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, BatchEngineTaskImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll",
			new String[0]);

		_finderPathCountAll = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll",
			new String[0]);

		_finderPathWithPaginationFindByUuid = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, BatchEngineTaskImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUuid",
			new String[] {
				String.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByUuid = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, BatchEngineTaskImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid",
			new String[] {String.class.getName()},
			BatchEngineTaskModelImpl.UUID_COLUMN_BITMASK);

		_finderPathCountByUuid = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUuid",
			new String[] {String.class.getName()});

		_finderPathWithPaginationFindByUuid_C = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, BatchEngineTaskImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUuid_C",
			new String[] {
				String.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByUuid_C = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, BatchEngineTaskImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid_C",
			new String[] {String.class.getName(), Long.class.getName()},
			BatchEngineTaskModelImpl.UUID_COLUMN_BITMASK |
			BatchEngineTaskModelImpl.COMPANYID_COLUMN_BITMASK);

		_finderPathCountByUuid_C = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUuid_C",
			new String[] {String.class.getName(), Long.class.getName()});

		_finderPathWithPaginationFindByExecuteStatus = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, BatchEngineTaskImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByExecuteStatus",
			new String[] {
				String.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByExecuteStatus = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, BatchEngineTaskImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByExecuteStatus",
			new String[] {String.class.getName()},
			BatchEngineTaskModelImpl.EXECUTESTATUS_COLUMN_BITMASK);

		_finderPathCountByExecuteStatus = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByExecuteStatus",
			new String[] {String.class.getName()});
	}

	@Deactivate
	public void deactivate() {
		entityCache.removeCache(BatchEngineTaskImpl.class.getName());
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
		super.setConfiguration(configuration);

		_columnBitmaskEnabled = GetterUtil.getBoolean(
			configuration.get(
				"value.object.column.bitmask.enabled.com.liferay.batch.engine.model.BatchEngineTask"),
			true);
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

	private boolean _columnBitmaskEnabled;

	@Reference
	protected EntityCache entityCache;

	@Reference
	protected FinderCache finderCache;

	private static final String _SQL_SELECT_BATCHENGINETASK =
		"SELECT batchEngineTask FROM BatchEngineTask batchEngineTask";

	private static final String _SQL_SELECT_BATCHENGINETASK_WHERE =
		"SELECT batchEngineTask FROM BatchEngineTask batchEngineTask WHERE ";

	private static final String _SQL_COUNT_BATCHENGINETASK =
		"SELECT COUNT(batchEngineTask) FROM BatchEngineTask batchEngineTask";

	private static final String _SQL_COUNT_BATCHENGINETASK_WHERE =
		"SELECT COUNT(batchEngineTask) FROM BatchEngineTask batchEngineTask WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS = "batchEngineTask.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No BatchEngineTask exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No BatchEngineTask exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		BatchEngineTaskPersistenceImpl.class);

	private static final Set<String> _badColumnNames = SetUtil.fromArray(
		new String[] {"uuid"});

	static {
		try {
			Class.forName(BatchEnginePersistenceConstants.class.getName());
		}
		catch (ClassNotFoundException cnfe) {
			throw new ExceptionInInitializerError(cnfe);
		}
	}

}