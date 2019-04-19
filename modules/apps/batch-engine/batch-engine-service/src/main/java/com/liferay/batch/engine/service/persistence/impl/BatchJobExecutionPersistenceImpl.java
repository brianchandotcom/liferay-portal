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

import com.liferay.batch.engine.exception.NoSuchJobExecutionException;
import com.liferay.batch.engine.model.BatchJobExecution;
import com.liferay.batch.engine.model.impl.BatchJobExecutionImpl;
import com.liferay.batch.engine.model.impl.BatchJobExecutionModelImpl;
import com.liferay.batch.engine.service.persistence.BatchJobExecutionPersistence;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.orm.EntityCache;
import com.liferay.portal.kernel.dao.orm.FinderCache;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.service.persistence.CompanyProvider;
import com.liferay.portal.kernel.service.persistence.CompanyProviderWrapper;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;
import com.liferay.portal.spring.extender.service.ServiceReference;

import java.io.Serializable;

import java.lang.reflect.InvocationHandler;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence implementation for the batch job execution service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Ivica Cardic
 * @generated
 */
@ProviderType
public class BatchJobExecutionPersistenceImpl
	extends BasePersistenceImpl<BatchJobExecution>
	implements BatchJobExecutionPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>BatchJobExecutionUtil</code> to access the batch job execution persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		BatchJobExecutionImpl.class.getName();

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
	 * Returns all the batch job executions where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching batch job executions
	 */
	@Override
	public List<BatchJobExecution> findByUuid(String uuid) {
		return findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the batch job executions where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>BatchJobExecutionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of batch job executions
	 * @param end the upper bound of the range of batch job executions (not inclusive)
	 * @return the range of matching batch job executions
	 */
	@Override
	public List<BatchJobExecution> findByUuid(String uuid, int start, int end) {
		return findByUuid(uuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the batch job executions where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>BatchJobExecutionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of batch job executions
	 * @param end the upper bound of the range of batch job executions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching batch job executions
	 */
	@Override
	public List<BatchJobExecution> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<BatchJobExecution> orderByComparator) {

		return findByUuid(uuid, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the batch job executions where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>BatchJobExecutionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of batch job executions
	 * @param end the upper bound of the range of batch job executions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching batch job executions
	 */
	@Override
	public List<BatchJobExecution> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<BatchJobExecution> orderByComparator,
		boolean retrieveFromCache) {

		uuid = Objects.toString(uuid, "");

		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			pagination = false;
			finderPath = _finderPathWithoutPaginationFindByUuid;
			finderArgs = new Object[] {uuid};
		}
		else {
			finderPath = _finderPathWithPaginationFindByUuid;
			finderArgs = new Object[] {uuid, start, end, orderByComparator};
		}

		List<BatchJobExecution> list = null;

		if (retrieveFromCache) {
			list = (List<BatchJobExecution>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (BatchJobExecution batchJobExecution : list) {
					if (!uuid.equals(batchJobExecution.getUuid())) {
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

			query.append(_SQL_SELECT_BATCHJOBEXECUTION_WHERE);

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
			else if (pagination) {
				query.append(BatchJobExecutionModelImpl.ORDER_BY_JPQL);
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

				if (!pagination) {
					list = (List<BatchJobExecution>)QueryUtil.list(
						q, getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<BatchJobExecution>)QueryUtil.list(
						q, getDialect(), start, end);
				}

				cacheResult(list);

				finderCache.putResult(finderPath, finderArgs, list);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first batch job execution in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching batch job execution
	 * @throws NoSuchJobExecutionException if a matching batch job execution could not be found
	 */
	@Override
	public BatchJobExecution findByUuid_First(
			String uuid, OrderByComparator<BatchJobExecution> orderByComparator)
		throws NoSuchJobExecutionException {

		BatchJobExecution batchJobExecution = fetchByUuid_First(
			uuid, orderByComparator);

		if (batchJobExecution != null) {
			return batchJobExecution;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append("}");

		throw new NoSuchJobExecutionException(msg.toString());
	}

	/**
	 * Returns the first batch job execution in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching batch job execution, or <code>null</code> if a matching batch job execution could not be found
	 */
	@Override
	public BatchJobExecution fetchByUuid_First(
		String uuid, OrderByComparator<BatchJobExecution> orderByComparator) {

		List<BatchJobExecution> list = findByUuid(
			uuid, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last batch job execution in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching batch job execution
	 * @throws NoSuchJobExecutionException if a matching batch job execution could not be found
	 */
	@Override
	public BatchJobExecution findByUuid_Last(
			String uuid, OrderByComparator<BatchJobExecution> orderByComparator)
		throws NoSuchJobExecutionException {

		BatchJobExecution batchJobExecution = fetchByUuid_Last(
			uuid, orderByComparator);

		if (batchJobExecution != null) {
			return batchJobExecution;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append("}");

		throw new NoSuchJobExecutionException(msg.toString());
	}

	/**
	 * Returns the last batch job execution in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching batch job execution, or <code>null</code> if a matching batch job execution could not be found
	 */
	@Override
	public BatchJobExecution fetchByUuid_Last(
		String uuid, OrderByComparator<BatchJobExecution> orderByComparator) {

		int count = countByUuid(uuid);

		if (count == 0) {
			return null;
		}

		List<BatchJobExecution> list = findByUuid(
			uuid, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the batch job executions before and after the current batch job execution in the ordered set where uuid = &#63;.
	 *
	 * @param batchJobExecutionId the primary key of the current batch job execution
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next batch job execution
	 * @throws NoSuchJobExecutionException if a batch job execution with the primary key could not be found
	 */
	@Override
	public BatchJobExecution[] findByUuid_PrevAndNext(
			long batchJobExecutionId, String uuid,
			OrderByComparator<BatchJobExecution> orderByComparator)
		throws NoSuchJobExecutionException {

		uuid = Objects.toString(uuid, "");

		BatchJobExecution batchJobExecution = findByPrimaryKey(
			batchJobExecutionId);

		Session session = null;

		try {
			session = openSession();

			BatchJobExecution[] array = new BatchJobExecutionImpl[3];

			array[0] = getByUuid_PrevAndNext(
				session, batchJobExecution, uuid, orderByComparator, true);

			array[1] = batchJobExecution;

			array[2] = getByUuid_PrevAndNext(
				session, batchJobExecution, uuid, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected BatchJobExecution getByUuid_PrevAndNext(
		Session session, BatchJobExecution batchJobExecution, String uuid,
		OrderByComparator<BatchJobExecution> orderByComparator,
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

		query.append(_SQL_SELECT_BATCHJOBEXECUTION_WHERE);

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
			query.append(BatchJobExecutionModelImpl.ORDER_BY_JPQL);
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
						batchJobExecution)) {

				qPos.add(orderByConditionValue);
			}
		}

		List<BatchJobExecution> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the batch job executions where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	@Override
	public void removeByUuid(String uuid) {
		for (BatchJobExecution batchJobExecution :
				findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(batchJobExecution);
		}
	}

	/**
	 * Returns the number of batch job executions where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching batch job executions
	 */
	@Override
	public int countByUuid(String uuid) {
		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = _finderPathCountByUuid;

		Object[] finderArgs = new Object[] {uuid};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_BATCHJOBEXECUTION_WHERE);

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
		"batchJobExecution.uuid = ?";

	private static final String _FINDER_COLUMN_UUID_UUID_3 =
		"(batchJobExecution.uuid IS NULL OR batchJobExecution.uuid = '')";

	private FinderPath _finderPathWithPaginationFindByUuid_C;
	private FinderPath _finderPathWithoutPaginationFindByUuid_C;
	private FinderPath _finderPathCountByUuid_C;

	/**
	 * Returns all the batch job executions where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching batch job executions
	 */
	@Override
	public List<BatchJobExecution> findByUuid_C(String uuid, long companyId) {
		return findByUuid_C(
			uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the batch job executions where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>BatchJobExecutionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of batch job executions
	 * @param end the upper bound of the range of batch job executions (not inclusive)
	 * @return the range of matching batch job executions
	 */
	@Override
	public List<BatchJobExecution> findByUuid_C(
		String uuid, long companyId, int start, int end) {

		return findByUuid_C(uuid, companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the batch job executions where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>BatchJobExecutionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of batch job executions
	 * @param end the upper bound of the range of batch job executions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching batch job executions
	 */
	@Override
	public List<BatchJobExecution> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<BatchJobExecution> orderByComparator) {

		return findByUuid_C(
			uuid, companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the batch job executions where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>BatchJobExecutionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of batch job executions
	 * @param end the upper bound of the range of batch job executions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching batch job executions
	 */
	@Override
	public List<BatchJobExecution> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<BatchJobExecution> orderByComparator,
		boolean retrieveFromCache) {

		uuid = Objects.toString(uuid, "");

		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			pagination = false;
			finderPath = _finderPathWithoutPaginationFindByUuid_C;
			finderArgs = new Object[] {uuid, companyId};
		}
		else {
			finderPath = _finderPathWithPaginationFindByUuid_C;
			finderArgs = new Object[] {
				uuid, companyId, start, end, orderByComparator
			};
		}

		List<BatchJobExecution> list = null;

		if (retrieveFromCache) {
			list = (List<BatchJobExecution>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (BatchJobExecution batchJobExecution : list) {
					if (!uuid.equals(batchJobExecution.getUuid()) ||
						(companyId != batchJobExecution.getCompanyId())) {

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

			query.append(_SQL_SELECT_BATCHJOBEXECUTION_WHERE);

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
			else if (pagination) {
				query.append(BatchJobExecutionModelImpl.ORDER_BY_JPQL);
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

				if (!pagination) {
					list = (List<BatchJobExecution>)QueryUtil.list(
						q, getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<BatchJobExecution>)QueryUtil.list(
						q, getDialect(), start, end);
				}

				cacheResult(list);

				finderCache.putResult(finderPath, finderArgs, list);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first batch job execution in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching batch job execution
	 * @throws NoSuchJobExecutionException if a matching batch job execution could not be found
	 */
	@Override
	public BatchJobExecution findByUuid_C_First(
			String uuid, long companyId,
			OrderByComparator<BatchJobExecution> orderByComparator)
		throws NoSuchJobExecutionException {

		BatchJobExecution batchJobExecution = fetchByUuid_C_First(
			uuid, companyId, orderByComparator);

		if (batchJobExecution != null) {
			return batchJobExecution;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(", companyId=");
		msg.append(companyId);

		msg.append("}");

		throw new NoSuchJobExecutionException(msg.toString());
	}

	/**
	 * Returns the first batch job execution in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching batch job execution, or <code>null</code> if a matching batch job execution could not be found
	 */
	@Override
	public BatchJobExecution fetchByUuid_C_First(
		String uuid, long companyId,
		OrderByComparator<BatchJobExecution> orderByComparator) {

		List<BatchJobExecution> list = findByUuid_C(
			uuid, companyId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last batch job execution in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching batch job execution
	 * @throws NoSuchJobExecutionException if a matching batch job execution could not be found
	 */
	@Override
	public BatchJobExecution findByUuid_C_Last(
			String uuid, long companyId,
			OrderByComparator<BatchJobExecution> orderByComparator)
		throws NoSuchJobExecutionException {

		BatchJobExecution batchJobExecution = fetchByUuid_C_Last(
			uuid, companyId, orderByComparator);

		if (batchJobExecution != null) {
			return batchJobExecution;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(", companyId=");
		msg.append(companyId);

		msg.append("}");

		throw new NoSuchJobExecutionException(msg.toString());
	}

	/**
	 * Returns the last batch job execution in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching batch job execution, or <code>null</code> if a matching batch job execution could not be found
	 */
	@Override
	public BatchJobExecution fetchByUuid_C_Last(
		String uuid, long companyId,
		OrderByComparator<BatchJobExecution> orderByComparator) {

		int count = countByUuid_C(uuid, companyId);

		if (count == 0) {
			return null;
		}

		List<BatchJobExecution> list = findByUuid_C(
			uuid, companyId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the batch job executions before and after the current batch job execution in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param batchJobExecutionId the primary key of the current batch job execution
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next batch job execution
	 * @throws NoSuchJobExecutionException if a batch job execution with the primary key could not be found
	 */
	@Override
	public BatchJobExecution[] findByUuid_C_PrevAndNext(
			long batchJobExecutionId, String uuid, long companyId,
			OrderByComparator<BatchJobExecution> orderByComparator)
		throws NoSuchJobExecutionException {

		uuid = Objects.toString(uuid, "");

		BatchJobExecution batchJobExecution = findByPrimaryKey(
			batchJobExecutionId);

		Session session = null;

		try {
			session = openSession();

			BatchJobExecution[] array = new BatchJobExecutionImpl[3];

			array[0] = getByUuid_C_PrevAndNext(
				session, batchJobExecution, uuid, companyId, orderByComparator,
				true);

			array[1] = batchJobExecution;

			array[2] = getByUuid_C_PrevAndNext(
				session, batchJobExecution, uuid, companyId, orderByComparator,
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

	protected BatchJobExecution getByUuid_C_PrevAndNext(
		Session session, BatchJobExecution batchJobExecution, String uuid,
		long companyId, OrderByComparator<BatchJobExecution> orderByComparator,
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

		query.append(_SQL_SELECT_BATCHJOBEXECUTION_WHERE);

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
			query.append(BatchJobExecutionModelImpl.ORDER_BY_JPQL);
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
						batchJobExecution)) {

				qPos.add(orderByConditionValue);
			}
		}

		List<BatchJobExecution> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the batch job executions where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	@Override
	public void removeByUuid_C(String uuid, long companyId) {
		for (BatchJobExecution batchJobExecution :
				findByUuid_C(
					uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(batchJobExecution);
		}
	}

	/**
	 * Returns the number of batch job executions where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching batch job executions
	 */
	@Override
	public int countByUuid_C(String uuid, long companyId) {
		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = _finderPathCountByUuid_C;

		Object[] finderArgs = new Object[] {uuid, companyId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_BATCHJOBEXECUTION_WHERE);

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
		"batchJobExecution.uuid = ? AND ";

	private static final String _FINDER_COLUMN_UUID_C_UUID_3 =
		"(batchJobExecution.uuid IS NULL OR batchJobExecution.uuid = '') AND ";

	private static final String _FINDER_COLUMN_UUID_C_COMPANYID_2 =
		"batchJobExecution.companyId = ?";

	private FinderPath _finderPathWithPaginationFindBybatchJobInstanceId;
	private FinderPath _finderPathWithoutPaginationFindBybatchJobInstanceId;
	private FinderPath _finderPathCountBybatchJobInstanceId;

	/**
	 * Returns all the batch job executions where batchJobInstanceId = &#63;.
	 *
	 * @param batchJobInstanceId the batch job instance ID
	 * @return the matching batch job executions
	 */
	@Override
	public List<BatchJobExecution> findBybatchJobInstanceId(
		long batchJobInstanceId) {

		return findBybatchJobInstanceId(
			batchJobInstanceId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the batch job executions where batchJobInstanceId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>BatchJobExecutionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param batchJobInstanceId the batch job instance ID
	 * @param start the lower bound of the range of batch job executions
	 * @param end the upper bound of the range of batch job executions (not inclusive)
	 * @return the range of matching batch job executions
	 */
	@Override
	public List<BatchJobExecution> findBybatchJobInstanceId(
		long batchJobInstanceId, int start, int end) {

		return findBybatchJobInstanceId(batchJobInstanceId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the batch job executions where batchJobInstanceId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>BatchJobExecutionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param batchJobInstanceId the batch job instance ID
	 * @param start the lower bound of the range of batch job executions
	 * @param end the upper bound of the range of batch job executions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching batch job executions
	 */
	@Override
	public List<BatchJobExecution> findBybatchJobInstanceId(
		long batchJobInstanceId, int start, int end,
		OrderByComparator<BatchJobExecution> orderByComparator) {

		return findBybatchJobInstanceId(
			batchJobInstanceId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the batch job executions where batchJobInstanceId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>BatchJobExecutionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param batchJobInstanceId the batch job instance ID
	 * @param start the lower bound of the range of batch job executions
	 * @param end the upper bound of the range of batch job executions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching batch job executions
	 */
	@Override
	public List<BatchJobExecution> findBybatchJobInstanceId(
		long batchJobInstanceId, int start, int end,
		OrderByComparator<BatchJobExecution> orderByComparator,
		boolean retrieveFromCache) {

		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			pagination = false;
			finderPath = _finderPathWithoutPaginationFindBybatchJobInstanceId;
			finderArgs = new Object[] {batchJobInstanceId};
		}
		else {
			finderPath = _finderPathWithPaginationFindBybatchJobInstanceId;
			finderArgs = new Object[] {
				batchJobInstanceId, start, end, orderByComparator
			};
		}

		List<BatchJobExecution> list = null;

		if (retrieveFromCache) {
			list = (List<BatchJobExecution>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (BatchJobExecution batchJobExecution : list) {
					if ((batchJobInstanceId !=
							batchJobExecution.getBatchJobInstanceId())) {

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

			query.append(_SQL_SELECT_BATCHJOBEXECUTION_WHERE);

			query.append(
				_FINDER_COLUMN_BATCHJOBINSTANCEID_BATCHJOBINSTANCEID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else if (pagination) {
				query.append(BatchJobExecutionModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(batchJobInstanceId);

				if (!pagination) {
					list = (List<BatchJobExecution>)QueryUtil.list(
						q, getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<BatchJobExecution>)QueryUtil.list(
						q, getDialect(), start, end);
				}

				cacheResult(list);

				finderCache.putResult(finderPath, finderArgs, list);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first batch job execution in the ordered set where batchJobInstanceId = &#63;.
	 *
	 * @param batchJobInstanceId the batch job instance ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching batch job execution
	 * @throws NoSuchJobExecutionException if a matching batch job execution could not be found
	 */
	@Override
	public BatchJobExecution findBybatchJobInstanceId_First(
			long batchJobInstanceId,
			OrderByComparator<BatchJobExecution> orderByComparator)
		throws NoSuchJobExecutionException {

		BatchJobExecution batchJobExecution = fetchBybatchJobInstanceId_First(
			batchJobInstanceId, orderByComparator);

		if (batchJobExecution != null) {
			return batchJobExecution;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("batchJobInstanceId=");
		msg.append(batchJobInstanceId);

		msg.append("}");

		throw new NoSuchJobExecutionException(msg.toString());
	}

	/**
	 * Returns the first batch job execution in the ordered set where batchJobInstanceId = &#63;.
	 *
	 * @param batchJobInstanceId the batch job instance ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching batch job execution, or <code>null</code> if a matching batch job execution could not be found
	 */
	@Override
	public BatchJobExecution fetchBybatchJobInstanceId_First(
		long batchJobInstanceId,
		OrderByComparator<BatchJobExecution> orderByComparator) {

		List<BatchJobExecution> list = findBybatchJobInstanceId(
			batchJobInstanceId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last batch job execution in the ordered set where batchJobInstanceId = &#63;.
	 *
	 * @param batchJobInstanceId the batch job instance ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching batch job execution
	 * @throws NoSuchJobExecutionException if a matching batch job execution could not be found
	 */
	@Override
	public BatchJobExecution findBybatchJobInstanceId_Last(
			long batchJobInstanceId,
			OrderByComparator<BatchJobExecution> orderByComparator)
		throws NoSuchJobExecutionException {

		BatchJobExecution batchJobExecution = fetchBybatchJobInstanceId_Last(
			batchJobInstanceId, orderByComparator);

		if (batchJobExecution != null) {
			return batchJobExecution;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("batchJobInstanceId=");
		msg.append(batchJobInstanceId);

		msg.append("}");

		throw new NoSuchJobExecutionException(msg.toString());
	}

	/**
	 * Returns the last batch job execution in the ordered set where batchJobInstanceId = &#63;.
	 *
	 * @param batchJobInstanceId the batch job instance ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching batch job execution, or <code>null</code> if a matching batch job execution could not be found
	 */
	@Override
	public BatchJobExecution fetchBybatchJobInstanceId_Last(
		long batchJobInstanceId,
		OrderByComparator<BatchJobExecution> orderByComparator) {

		int count = countBybatchJobInstanceId(batchJobInstanceId);

		if (count == 0) {
			return null;
		}

		List<BatchJobExecution> list = findBybatchJobInstanceId(
			batchJobInstanceId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the batch job executions before and after the current batch job execution in the ordered set where batchJobInstanceId = &#63;.
	 *
	 * @param batchJobExecutionId the primary key of the current batch job execution
	 * @param batchJobInstanceId the batch job instance ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next batch job execution
	 * @throws NoSuchJobExecutionException if a batch job execution with the primary key could not be found
	 */
	@Override
	public BatchJobExecution[] findBybatchJobInstanceId_PrevAndNext(
			long batchJobExecutionId, long batchJobInstanceId,
			OrderByComparator<BatchJobExecution> orderByComparator)
		throws NoSuchJobExecutionException {

		BatchJobExecution batchJobExecution = findByPrimaryKey(
			batchJobExecutionId);

		Session session = null;

		try {
			session = openSession();

			BatchJobExecution[] array = new BatchJobExecutionImpl[3];

			array[0] = getBybatchJobInstanceId_PrevAndNext(
				session, batchJobExecution, batchJobInstanceId,
				orderByComparator, true);

			array[1] = batchJobExecution;

			array[2] = getBybatchJobInstanceId_PrevAndNext(
				session, batchJobExecution, batchJobInstanceId,
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

	protected BatchJobExecution getBybatchJobInstanceId_PrevAndNext(
		Session session, BatchJobExecution batchJobExecution,
		long batchJobInstanceId,
		OrderByComparator<BatchJobExecution> orderByComparator,
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

		query.append(_SQL_SELECT_BATCHJOBEXECUTION_WHERE);

		query.append(_FINDER_COLUMN_BATCHJOBINSTANCEID_BATCHJOBINSTANCEID_2);

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
			query.append(BatchJobExecutionModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(batchJobInstanceId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						batchJobExecution)) {

				qPos.add(orderByConditionValue);
			}
		}

		List<BatchJobExecution> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the batch job executions where batchJobInstanceId = &#63; from the database.
	 *
	 * @param batchJobInstanceId the batch job instance ID
	 */
	@Override
	public void removeBybatchJobInstanceId(long batchJobInstanceId) {
		for (BatchJobExecution batchJobExecution :
				findBybatchJobInstanceId(
					batchJobInstanceId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(batchJobExecution);
		}
	}

	/**
	 * Returns the number of batch job executions where batchJobInstanceId = &#63;.
	 *
	 * @param batchJobInstanceId the batch job instance ID
	 * @return the number of matching batch job executions
	 */
	@Override
	public int countBybatchJobInstanceId(long batchJobInstanceId) {
		FinderPath finderPath = _finderPathCountBybatchJobInstanceId;

		Object[] finderArgs = new Object[] {batchJobInstanceId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_BATCHJOBEXECUTION_WHERE);

			query.append(
				_FINDER_COLUMN_BATCHJOBINSTANCEID_BATCHJOBINSTANCEID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(batchJobInstanceId);

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
		_FINDER_COLUMN_BATCHJOBINSTANCEID_BATCHJOBINSTANCEID_2 =
			"batchJobExecution.batchJobInstanceId = ?";

	private FinderPath _finderPathWithPaginationFindBystatus;
	private FinderPath _finderPathWithoutPaginationFindBystatus;
	private FinderPath _finderPathCountBystatus;

	/**
	 * Returns all the batch job executions where status = &#63;.
	 *
	 * @param status the status
	 * @return the matching batch job executions
	 */
	@Override
	public List<BatchJobExecution> findBystatus(String status) {
		return findBystatus(status, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the batch job executions where status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>BatchJobExecutionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param status the status
	 * @param start the lower bound of the range of batch job executions
	 * @param end the upper bound of the range of batch job executions (not inclusive)
	 * @return the range of matching batch job executions
	 */
	@Override
	public List<BatchJobExecution> findBystatus(
		String status, int start, int end) {

		return findBystatus(status, start, end, null);
	}

	/**
	 * Returns an ordered range of all the batch job executions where status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>BatchJobExecutionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param status the status
	 * @param start the lower bound of the range of batch job executions
	 * @param end the upper bound of the range of batch job executions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching batch job executions
	 */
	@Override
	public List<BatchJobExecution> findBystatus(
		String status, int start, int end,
		OrderByComparator<BatchJobExecution> orderByComparator) {

		return findBystatus(status, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the batch job executions where status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>BatchJobExecutionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param status the status
	 * @param start the lower bound of the range of batch job executions
	 * @param end the upper bound of the range of batch job executions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching batch job executions
	 */
	@Override
	public List<BatchJobExecution> findBystatus(
		String status, int start, int end,
		OrderByComparator<BatchJobExecution> orderByComparator,
		boolean retrieveFromCache) {

		status = Objects.toString(status, "");

		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			pagination = false;
			finderPath = _finderPathWithoutPaginationFindBystatus;
			finderArgs = new Object[] {status};
		}
		else {
			finderPath = _finderPathWithPaginationFindBystatus;
			finderArgs = new Object[] {status, start, end, orderByComparator};
		}

		List<BatchJobExecution> list = null;

		if (retrieveFromCache) {
			list = (List<BatchJobExecution>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (BatchJobExecution batchJobExecution : list) {
					if (!status.equals(batchJobExecution.getStatus())) {
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

			query.append(_SQL_SELECT_BATCHJOBEXECUTION_WHERE);

			boolean bindStatus = false;

			if (status.isEmpty()) {
				query.append(_FINDER_COLUMN_STATUS_STATUS_3);
			}
			else {
				bindStatus = true;

				query.append(_FINDER_COLUMN_STATUS_STATUS_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else if (pagination) {
				query.append(BatchJobExecutionModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindStatus) {
					qPos.add(status);
				}

				if (!pagination) {
					list = (List<BatchJobExecution>)QueryUtil.list(
						q, getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<BatchJobExecution>)QueryUtil.list(
						q, getDialect(), start, end);
				}

				cacheResult(list);

				finderCache.putResult(finderPath, finderArgs, list);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first batch job execution in the ordered set where status = &#63;.
	 *
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching batch job execution
	 * @throws NoSuchJobExecutionException if a matching batch job execution could not be found
	 */
	@Override
	public BatchJobExecution findBystatus_First(
			String status,
			OrderByComparator<BatchJobExecution> orderByComparator)
		throws NoSuchJobExecutionException {

		BatchJobExecution batchJobExecution = fetchBystatus_First(
			status, orderByComparator);

		if (batchJobExecution != null) {
			return batchJobExecution;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("status=");
		msg.append(status);

		msg.append("}");

		throw new NoSuchJobExecutionException(msg.toString());
	}

	/**
	 * Returns the first batch job execution in the ordered set where status = &#63;.
	 *
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching batch job execution, or <code>null</code> if a matching batch job execution could not be found
	 */
	@Override
	public BatchJobExecution fetchBystatus_First(
		String status, OrderByComparator<BatchJobExecution> orderByComparator) {

		List<BatchJobExecution> list = findBystatus(
			status, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last batch job execution in the ordered set where status = &#63;.
	 *
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching batch job execution
	 * @throws NoSuchJobExecutionException if a matching batch job execution could not be found
	 */
	@Override
	public BatchJobExecution findBystatus_Last(
			String status,
			OrderByComparator<BatchJobExecution> orderByComparator)
		throws NoSuchJobExecutionException {

		BatchJobExecution batchJobExecution = fetchBystatus_Last(
			status, orderByComparator);

		if (batchJobExecution != null) {
			return batchJobExecution;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("status=");
		msg.append(status);

		msg.append("}");

		throw new NoSuchJobExecutionException(msg.toString());
	}

	/**
	 * Returns the last batch job execution in the ordered set where status = &#63;.
	 *
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching batch job execution, or <code>null</code> if a matching batch job execution could not be found
	 */
	@Override
	public BatchJobExecution fetchBystatus_Last(
		String status, OrderByComparator<BatchJobExecution> orderByComparator) {

		int count = countBystatus(status);

		if (count == 0) {
			return null;
		}

		List<BatchJobExecution> list = findBystatus(
			status, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the batch job executions before and after the current batch job execution in the ordered set where status = &#63;.
	 *
	 * @param batchJobExecutionId the primary key of the current batch job execution
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next batch job execution
	 * @throws NoSuchJobExecutionException if a batch job execution with the primary key could not be found
	 */
	@Override
	public BatchJobExecution[] findBystatus_PrevAndNext(
			long batchJobExecutionId, String status,
			OrderByComparator<BatchJobExecution> orderByComparator)
		throws NoSuchJobExecutionException {

		status = Objects.toString(status, "");

		BatchJobExecution batchJobExecution = findByPrimaryKey(
			batchJobExecutionId);

		Session session = null;

		try {
			session = openSession();

			BatchJobExecution[] array = new BatchJobExecutionImpl[3];

			array[0] = getBystatus_PrevAndNext(
				session, batchJobExecution, status, orderByComparator, true);

			array[1] = batchJobExecution;

			array[2] = getBystatus_PrevAndNext(
				session, batchJobExecution, status, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected BatchJobExecution getBystatus_PrevAndNext(
		Session session, BatchJobExecution batchJobExecution, String status,
		OrderByComparator<BatchJobExecution> orderByComparator,
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

		query.append(_SQL_SELECT_BATCHJOBEXECUTION_WHERE);

		boolean bindStatus = false;

		if (status.isEmpty()) {
			query.append(_FINDER_COLUMN_STATUS_STATUS_3);
		}
		else {
			bindStatus = true;

			query.append(_FINDER_COLUMN_STATUS_STATUS_2);
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
			query.append(BatchJobExecutionModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		if (bindStatus) {
			qPos.add(status);
		}

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						batchJobExecution)) {

				qPos.add(orderByConditionValue);
			}
		}

		List<BatchJobExecution> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the batch job executions where status = &#63; from the database.
	 *
	 * @param status the status
	 */
	@Override
	public void removeBystatus(String status) {
		for (BatchJobExecution batchJobExecution :
				findBystatus(
					status, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(batchJobExecution);
		}
	}

	/**
	 * Returns the number of batch job executions where status = &#63;.
	 *
	 * @param status the status
	 * @return the number of matching batch job executions
	 */
	@Override
	public int countBystatus(String status) {
		status = Objects.toString(status, "");

		FinderPath finderPath = _finderPathCountBystatus;

		Object[] finderArgs = new Object[] {status};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_BATCHJOBEXECUTION_WHERE);

			boolean bindStatus = false;

			if (status.isEmpty()) {
				query.append(_FINDER_COLUMN_STATUS_STATUS_3);
			}
			else {
				bindStatus = true;

				query.append(_FINDER_COLUMN_STATUS_STATUS_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindStatus) {
					qPos.add(status);
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

	private static final String _FINDER_COLUMN_STATUS_STATUS_2 =
		"batchJobExecution.status = ?";

	private static final String _FINDER_COLUMN_STATUS_STATUS_3 =
		"(batchJobExecution.status IS NULL OR batchJobExecution.status = '')";

	public BatchJobExecutionPersistenceImpl() {
		setModelClass(BatchJobExecution.class);

		setModelImplClass(BatchJobExecutionImpl.class);
		setModelPKClass(long.class);
		setEntityCacheEnabled(BatchJobExecutionModelImpl.ENTITY_CACHE_ENABLED);

		Map<String, String> dbColumnNames = new HashMap<String, String>();

		dbColumnNames.put("uuid", "uuid_");

		setDBColumnNames(dbColumnNames);
	}

	/**
	 * Caches the batch job execution in the entity cache if it is enabled.
	 *
	 * @param batchJobExecution the batch job execution
	 */
	@Override
	public void cacheResult(BatchJobExecution batchJobExecution) {
		entityCache.putResult(
			BatchJobExecutionModelImpl.ENTITY_CACHE_ENABLED,
			BatchJobExecutionImpl.class, batchJobExecution.getPrimaryKey(),
			batchJobExecution);

		batchJobExecution.resetOriginalValues();
	}

	/**
	 * Caches the batch job executions in the entity cache if it is enabled.
	 *
	 * @param batchJobExecutions the batch job executions
	 */
	@Override
	public void cacheResult(List<BatchJobExecution> batchJobExecutions) {
		for (BatchJobExecution batchJobExecution : batchJobExecutions) {
			if (entityCache.getResult(
					BatchJobExecutionModelImpl.ENTITY_CACHE_ENABLED,
					BatchJobExecutionImpl.class,
					batchJobExecution.getPrimaryKey()) == null) {

				cacheResult(batchJobExecution);
			}
			else {
				batchJobExecution.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all batch job executions.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(BatchJobExecutionImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the batch job execution.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(BatchJobExecution batchJobExecution) {
		entityCache.removeResult(
			BatchJobExecutionModelImpl.ENTITY_CACHE_ENABLED,
			BatchJobExecutionImpl.class, batchJobExecution.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@Override
	public void clearCache(List<BatchJobExecution> batchJobExecutions) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (BatchJobExecution batchJobExecution : batchJobExecutions) {
			entityCache.removeResult(
				BatchJobExecutionModelImpl.ENTITY_CACHE_ENABLED,
				BatchJobExecutionImpl.class, batchJobExecution.getPrimaryKey());
		}
	}

	/**
	 * Creates a new batch job execution with the primary key. Does not add the batch job execution to the database.
	 *
	 * @param batchJobExecutionId the primary key for the new batch job execution
	 * @return the new batch job execution
	 */
	@Override
	public BatchJobExecution create(long batchJobExecutionId) {
		BatchJobExecution batchJobExecution = new BatchJobExecutionImpl();

		batchJobExecution.setNew(true);
		batchJobExecution.setPrimaryKey(batchJobExecutionId);

		String uuid = PortalUUIDUtil.generate();

		batchJobExecution.setUuid(uuid);

		batchJobExecution.setCompanyId(companyProvider.getCompanyId());

		return batchJobExecution;
	}

	/**
	 * Removes the batch job execution with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param batchJobExecutionId the primary key of the batch job execution
	 * @return the batch job execution that was removed
	 * @throws NoSuchJobExecutionException if a batch job execution with the primary key could not be found
	 */
	@Override
	public BatchJobExecution remove(long batchJobExecutionId)
		throws NoSuchJobExecutionException {

		return remove((Serializable)batchJobExecutionId);
	}

	/**
	 * Removes the batch job execution with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the batch job execution
	 * @return the batch job execution that was removed
	 * @throws NoSuchJobExecutionException if a batch job execution with the primary key could not be found
	 */
	@Override
	public BatchJobExecution remove(Serializable primaryKey)
		throws NoSuchJobExecutionException {

		Session session = null;

		try {
			session = openSession();

			BatchJobExecution batchJobExecution =
				(BatchJobExecution)session.get(
					BatchJobExecutionImpl.class, primaryKey);

			if (batchJobExecution == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchJobExecutionException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(batchJobExecution);
		}
		catch (NoSuchJobExecutionException nsee) {
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
	protected BatchJobExecution removeImpl(
		BatchJobExecution batchJobExecution) {

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(batchJobExecution)) {
				batchJobExecution = (BatchJobExecution)session.get(
					BatchJobExecutionImpl.class,
					batchJobExecution.getPrimaryKeyObj());
			}

			if (batchJobExecution != null) {
				session.delete(batchJobExecution);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (batchJobExecution != null) {
			clearCache(batchJobExecution);
		}

		return batchJobExecution;
	}

	@Override
	public BatchJobExecution updateImpl(BatchJobExecution batchJobExecution) {
		boolean isNew = batchJobExecution.isNew();

		if (!(batchJobExecution instanceof BatchJobExecutionModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(batchJobExecution.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					batchJobExecution);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in batchJobExecution proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom BatchJobExecution implementation " +
					batchJobExecution.getClass());
		}

		BatchJobExecutionModelImpl batchJobExecutionModelImpl =
			(BatchJobExecutionModelImpl)batchJobExecution;

		if (Validator.isNull(batchJobExecution.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			batchJobExecution.setUuid(uuid);
		}

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Date now = new Date();

		if (isNew && (batchJobExecution.getCreateDate() == null)) {
			if (serviceContext == null) {
				batchJobExecution.setCreateDate(now);
			}
			else {
				batchJobExecution.setCreateDate(
					serviceContext.getCreateDate(now));
			}
		}

		if (!batchJobExecutionModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				batchJobExecution.setModifiedDate(now);
			}
			else {
				batchJobExecution.setModifiedDate(
					serviceContext.getModifiedDate(now));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (batchJobExecution.isNew()) {
				session.save(batchJobExecution);

				batchJobExecution.setNew(false);
			}
			else {
				batchJobExecution = (BatchJobExecution)session.merge(
					batchJobExecution);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (!BatchJobExecutionModelImpl.COLUMN_BITMASK_ENABLED) {
			finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}
		else if (isNew) {
			Object[] args = new Object[] {batchJobExecutionModelImpl.getUuid()};

			finderCache.removeResult(_finderPathCountByUuid, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByUuid, args);

			args = new Object[] {
				batchJobExecutionModelImpl.getUuid(),
				batchJobExecutionModelImpl.getCompanyId()
			};

			finderCache.removeResult(_finderPathCountByUuid_C, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByUuid_C, args);

			args = new Object[] {
				batchJobExecutionModelImpl.getBatchJobInstanceId()
			};

			finderCache.removeResult(
				_finderPathCountBybatchJobInstanceId, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindBybatchJobInstanceId, args);

			args = new Object[] {batchJobExecutionModelImpl.getStatus()};

			finderCache.removeResult(_finderPathCountBystatus, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindBystatus, args);

			finderCache.removeResult(_finderPathCountAll, FINDER_ARGS_EMPTY);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindAll, FINDER_ARGS_EMPTY);
		}
		else {
			if ((batchJobExecutionModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByUuid.getColumnBitmask()) !=
					 0) {

				Object[] args = new Object[] {
					batchJobExecutionModelImpl.getOriginalUuid()
				};

				finderCache.removeResult(_finderPathCountByUuid, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByUuid, args);

				args = new Object[] {batchJobExecutionModelImpl.getUuid()};

				finderCache.removeResult(_finderPathCountByUuid, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByUuid, args);
			}

			if ((batchJobExecutionModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByUuid_C.getColumnBitmask()) !=
					 0) {

				Object[] args = new Object[] {
					batchJobExecutionModelImpl.getOriginalUuid(),
					batchJobExecutionModelImpl.getOriginalCompanyId()
				};

				finderCache.removeResult(_finderPathCountByUuid_C, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByUuid_C, args);

				args = new Object[] {
					batchJobExecutionModelImpl.getUuid(),
					batchJobExecutionModelImpl.getCompanyId()
				};

				finderCache.removeResult(_finderPathCountByUuid_C, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByUuid_C, args);
			}

			if ((batchJobExecutionModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindBybatchJobInstanceId.
					 getColumnBitmask()) != 0) {

				Object[] args = new Object[] {
					batchJobExecutionModelImpl.getOriginalBatchJobInstanceId()
				};

				finderCache.removeResult(
					_finderPathCountBybatchJobInstanceId, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindBybatchJobInstanceId, args);

				args = new Object[] {
					batchJobExecutionModelImpl.getBatchJobInstanceId()
				};

				finderCache.removeResult(
					_finderPathCountBybatchJobInstanceId, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindBybatchJobInstanceId, args);
			}

			if ((batchJobExecutionModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindBystatus.getColumnBitmask()) !=
					 0) {

				Object[] args = new Object[] {
					batchJobExecutionModelImpl.getOriginalStatus()
				};

				finderCache.removeResult(_finderPathCountBystatus, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindBystatus, args);

				args = new Object[] {batchJobExecutionModelImpl.getStatus()};

				finderCache.removeResult(_finderPathCountBystatus, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindBystatus, args);
			}
		}

		entityCache.putResult(
			BatchJobExecutionModelImpl.ENTITY_CACHE_ENABLED,
			BatchJobExecutionImpl.class, batchJobExecution.getPrimaryKey(),
			batchJobExecution, false);

		batchJobExecution.resetOriginalValues();

		return batchJobExecution;
	}

	/**
	 * Returns the batch job execution with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the batch job execution
	 * @return the batch job execution
	 * @throws NoSuchJobExecutionException if a batch job execution with the primary key could not be found
	 */
	@Override
	public BatchJobExecution findByPrimaryKey(Serializable primaryKey)
		throws NoSuchJobExecutionException {

		BatchJobExecution batchJobExecution = fetchByPrimaryKey(primaryKey);

		if (batchJobExecution == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchJobExecutionException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return batchJobExecution;
	}

	/**
	 * Returns the batch job execution with the primary key or throws a <code>NoSuchJobExecutionException</code> if it could not be found.
	 *
	 * @param batchJobExecutionId the primary key of the batch job execution
	 * @return the batch job execution
	 * @throws NoSuchJobExecutionException if a batch job execution with the primary key could not be found
	 */
	@Override
	public BatchJobExecution findByPrimaryKey(long batchJobExecutionId)
		throws NoSuchJobExecutionException {

		return findByPrimaryKey((Serializable)batchJobExecutionId);
	}

	/**
	 * Returns the batch job execution with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param batchJobExecutionId the primary key of the batch job execution
	 * @return the batch job execution, or <code>null</code> if a batch job execution with the primary key could not be found
	 */
	@Override
	public BatchJobExecution fetchByPrimaryKey(long batchJobExecutionId) {
		return fetchByPrimaryKey((Serializable)batchJobExecutionId);
	}

	/**
	 * Returns all the batch job executions.
	 *
	 * @return the batch job executions
	 */
	@Override
	public List<BatchJobExecution> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the batch job executions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>BatchJobExecutionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of batch job executions
	 * @param end the upper bound of the range of batch job executions (not inclusive)
	 * @return the range of batch job executions
	 */
	@Override
	public List<BatchJobExecution> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the batch job executions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>BatchJobExecutionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of batch job executions
	 * @param end the upper bound of the range of batch job executions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of batch job executions
	 */
	@Override
	public List<BatchJobExecution> findAll(
		int start, int end,
		OrderByComparator<BatchJobExecution> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the batch job executions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>BatchJobExecutionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of batch job executions
	 * @param end the upper bound of the range of batch job executions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of batch job executions
	 */
	@Override
	public List<BatchJobExecution> findAll(
		int start, int end,
		OrderByComparator<BatchJobExecution> orderByComparator,
		boolean retrieveFromCache) {

		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			pagination = false;
			finderPath = _finderPathWithoutPaginationFindAll;
			finderArgs = FINDER_ARGS_EMPTY;
		}
		else {
			finderPath = _finderPathWithPaginationFindAll;
			finderArgs = new Object[] {start, end, orderByComparator};
		}

		List<BatchJobExecution> list = null;

		if (retrieveFromCache) {
			list = (List<BatchJobExecution>)finderCache.getResult(
				finderPath, finderArgs, this);
		}

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				query.append(_SQL_SELECT_BATCHJOBEXECUTION);

				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_BATCHJOBEXECUTION;

				if (pagination) {
					sql = sql.concat(BatchJobExecutionModelImpl.ORDER_BY_JPQL);
				}
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (!pagination) {
					list = (List<BatchJobExecution>)QueryUtil.list(
						q, getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<BatchJobExecution>)QueryUtil.list(
						q, getDialect(), start, end);
				}

				cacheResult(list);

				finderCache.putResult(finderPath, finderArgs, list);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Removes all the batch job executions from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (BatchJobExecution batchJobExecution : findAll()) {
			remove(batchJobExecution);
		}
	}

	/**
	 * Returns the number of batch job executions.
	 *
	 * @return the number of batch job executions
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(
			_finderPathCountAll, FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_BATCHJOBEXECUTION);

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
		return "batchJobExecutionId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_BATCHJOBEXECUTION;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return BatchJobExecutionModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the batch job execution persistence.
	 */
	public void afterPropertiesSet() {
		_finderPathWithPaginationFindAll = new FinderPath(
			BatchJobExecutionModelImpl.ENTITY_CACHE_ENABLED,
			BatchJobExecutionModelImpl.FINDER_CACHE_ENABLED,
			BatchJobExecutionImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findAll", new String[0]);

		_finderPathWithoutPaginationFindAll = new FinderPath(
			BatchJobExecutionModelImpl.ENTITY_CACHE_ENABLED,
			BatchJobExecutionModelImpl.FINDER_CACHE_ENABLED,
			BatchJobExecutionImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll",
			new String[0]);

		_finderPathCountAll = new FinderPath(
			BatchJobExecutionModelImpl.ENTITY_CACHE_ENABLED,
			BatchJobExecutionModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll",
			new String[0]);

		_finderPathWithPaginationFindByUuid = new FinderPath(
			BatchJobExecutionModelImpl.ENTITY_CACHE_ENABLED,
			BatchJobExecutionModelImpl.FINDER_CACHE_ENABLED,
			BatchJobExecutionImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByUuid",
			new String[] {
				String.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByUuid = new FinderPath(
			BatchJobExecutionModelImpl.ENTITY_CACHE_ENABLED,
			BatchJobExecutionModelImpl.FINDER_CACHE_ENABLED,
			BatchJobExecutionImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid",
			new String[] {String.class.getName()},
			BatchJobExecutionModelImpl.UUID_COLUMN_BITMASK |
			BatchJobExecutionModelImpl.BATCHJOBINSTANCEID_COLUMN_BITMASK);

		_finderPathCountByUuid = new FinderPath(
			BatchJobExecutionModelImpl.ENTITY_CACHE_ENABLED,
			BatchJobExecutionModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUuid",
			new String[] {String.class.getName()});

		_finderPathWithPaginationFindByUuid_C = new FinderPath(
			BatchJobExecutionModelImpl.ENTITY_CACHE_ENABLED,
			BatchJobExecutionModelImpl.FINDER_CACHE_ENABLED,
			BatchJobExecutionImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByUuid_C",
			new String[] {
				String.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByUuid_C = new FinderPath(
			BatchJobExecutionModelImpl.ENTITY_CACHE_ENABLED,
			BatchJobExecutionModelImpl.FINDER_CACHE_ENABLED,
			BatchJobExecutionImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid_C",
			new String[] {String.class.getName(), Long.class.getName()},
			BatchJobExecutionModelImpl.UUID_COLUMN_BITMASK |
			BatchJobExecutionModelImpl.COMPANYID_COLUMN_BITMASK |
			BatchJobExecutionModelImpl.BATCHJOBINSTANCEID_COLUMN_BITMASK);

		_finderPathCountByUuid_C = new FinderPath(
			BatchJobExecutionModelImpl.ENTITY_CACHE_ENABLED,
			BatchJobExecutionModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUuid_C",
			new String[] {String.class.getName(), Long.class.getName()});

		_finderPathWithPaginationFindBybatchJobInstanceId = new FinderPath(
			BatchJobExecutionModelImpl.ENTITY_CACHE_ENABLED,
			BatchJobExecutionModelImpl.FINDER_CACHE_ENABLED,
			BatchJobExecutionImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findBybatchJobInstanceId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindBybatchJobInstanceId = new FinderPath(
			BatchJobExecutionModelImpl.ENTITY_CACHE_ENABLED,
			BatchJobExecutionModelImpl.FINDER_CACHE_ENABLED,
			BatchJobExecutionImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findBybatchJobInstanceId", new String[] {Long.class.getName()},
			BatchJobExecutionModelImpl.BATCHJOBINSTANCEID_COLUMN_BITMASK);

		_finderPathCountBybatchJobInstanceId = new FinderPath(
			BatchJobExecutionModelImpl.ENTITY_CACHE_ENABLED,
			BatchJobExecutionModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countBybatchJobInstanceId", new String[] {Long.class.getName()});

		_finderPathWithPaginationFindBystatus = new FinderPath(
			BatchJobExecutionModelImpl.ENTITY_CACHE_ENABLED,
			BatchJobExecutionModelImpl.FINDER_CACHE_ENABLED,
			BatchJobExecutionImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findBystatus",
			new String[] {
				String.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindBystatus = new FinderPath(
			BatchJobExecutionModelImpl.ENTITY_CACHE_ENABLED,
			BatchJobExecutionModelImpl.FINDER_CACHE_ENABLED,
			BatchJobExecutionImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findBystatus",
			new String[] {String.class.getName()},
			BatchJobExecutionModelImpl.STATUS_COLUMN_BITMASK |
			BatchJobExecutionModelImpl.BATCHJOBINSTANCEID_COLUMN_BITMASK);

		_finderPathCountBystatus = new FinderPath(
			BatchJobExecutionModelImpl.ENTITY_CACHE_ENABLED,
			BatchJobExecutionModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countBystatus",
			new String[] {String.class.getName()});
	}

	public void destroy() {
		entityCache.removeCache(BatchJobExecutionImpl.class.getName());
		finderCache.removeCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@ServiceReference(type = CompanyProviderWrapper.class)
	protected CompanyProvider companyProvider;

	@ServiceReference(type = EntityCache.class)
	protected EntityCache entityCache;

	@ServiceReference(type = FinderCache.class)
	protected FinderCache finderCache;

	private static final String _SQL_SELECT_BATCHJOBEXECUTION =
		"SELECT batchJobExecution FROM BatchJobExecution batchJobExecution";

	private static final String _SQL_SELECT_BATCHJOBEXECUTION_WHERE =
		"SELECT batchJobExecution FROM BatchJobExecution batchJobExecution WHERE ";

	private static final String _SQL_COUNT_BATCHJOBEXECUTION =
		"SELECT COUNT(batchJobExecution) FROM BatchJobExecution batchJobExecution";

	private static final String _SQL_COUNT_BATCHJOBEXECUTION_WHERE =
		"SELECT COUNT(batchJobExecution) FROM BatchJobExecution batchJobExecution WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS = "batchJobExecution.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No BatchJobExecution exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No BatchJobExecution exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		BatchJobExecutionPersistenceImpl.class);

	private static final Set<String> _badColumnNames = SetUtil.fromArray(
		new String[] {"uuid"});

}