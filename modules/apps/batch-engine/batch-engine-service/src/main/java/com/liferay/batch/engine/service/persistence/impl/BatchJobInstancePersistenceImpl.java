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

import com.liferay.batch.engine.exception.NoSuchJobInstanceException;
import com.liferay.batch.engine.model.BatchJobInstance;
import com.liferay.batch.engine.model.impl.BatchJobInstanceImpl;
import com.liferay.batch.engine.model.impl.BatchJobInstanceModelImpl;
import com.liferay.batch.engine.service.persistence.BatchJobInstancePersistence;
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
import com.liferay.portal.kernel.util.StringUtil;
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
 * The persistence implementation for the batch job instance service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Ivica Cardic
 * @generated
 */
@ProviderType
public class BatchJobInstancePersistenceImpl
	extends BasePersistenceImpl<BatchJobInstance>
	implements BatchJobInstancePersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>BatchJobInstanceUtil</code> to access the batch job instance persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		BatchJobInstanceImpl.class.getName();

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
	 * Returns all the batch job instances where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching batch job instances
	 */
	@Override
	public List<BatchJobInstance> findByUuid(String uuid) {
		return findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the batch job instances where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>BatchJobInstanceModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of batch job instances
	 * @param end the upper bound of the range of batch job instances (not inclusive)
	 * @return the range of matching batch job instances
	 */
	@Override
	public List<BatchJobInstance> findByUuid(String uuid, int start, int end) {
		return findByUuid(uuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the batch job instances where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>BatchJobInstanceModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of batch job instances
	 * @param end the upper bound of the range of batch job instances (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching batch job instances
	 */
	@Override
	public List<BatchJobInstance> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<BatchJobInstance> orderByComparator) {

		return findByUuid(uuid, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the batch job instances where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>BatchJobInstanceModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of batch job instances
	 * @param end the upper bound of the range of batch job instances (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching batch job instances
	 */
	@Override
	public List<BatchJobInstance> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<BatchJobInstance> orderByComparator,
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

		List<BatchJobInstance> list = null;

		if (retrieveFromCache) {
			list = (List<BatchJobInstance>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (BatchJobInstance batchJobInstance : list) {
					if (!uuid.equals(batchJobInstance.getUuid())) {
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

			query.append(_SQL_SELECT_BATCHJOBINSTANCE_WHERE);

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
				query.append(BatchJobInstanceModelImpl.ORDER_BY_JPQL);
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
					list = (List<BatchJobInstance>)QueryUtil.list(
						q, getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<BatchJobInstance>)QueryUtil.list(
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
	 * Returns the first batch job instance in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching batch job instance
	 * @throws NoSuchJobInstanceException if a matching batch job instance could not be found
	 */
	@Override
	public BatchJobInstance findByUuid_First(
			String uuid, OrderByComparator<BatchJobInstance> orderByComparator)
		throws NoSuchJobInstanceException {

		BatchJobInstance batchJobInstance = fetchByUuid_First(
			uuid, orderByComparator);

		if (batchJobInstance != null) {
			return batchJobInstance;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append("}");

		throw new NoSuchJobInstanceException(msg.toString());
	}

	/**
	 * Returns the first batch job instance in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching batch job instance, or <code>null</code> if a matching batch job instance could not be found
	 */
	@Override
	public BatchJobInstance fetchByUuid_First(
		String uuid, OrderByComparator<BatchJobInstance> orderByComparator) {

		List<BatchJobInstance> list = findByUuid(uuid, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last batch job instance in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching batch job instance
	 * @throws NoSuchJobInstanceException if a matching batch job instance could not be found
	 */
	@Override
	public BatchJobInstance findByUuid_Last(
			String uuid, OrderByComparator<BatchJobInstance> orderByComparator)
		throws NoSuchJobInstanceException {

		BatchJobInstance batchJobInstance = fetchByUuid_Last(
			uuid, orderByComparator);

		if (batchJobInstance != null) {
			return batchJobInstance;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append("}");

		throw new NoSuchJobInstanceException(msg.toString());
	}

	/**
	 * Returns the last batch job instance in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching batch job instance, or <code>null</code> if a matching batch job instance could not be found
	 */
	@Override
	public BatchJobInstance fetchByUuid_Last(
		String uuid, OrderByComparator<BatchJobInstance> orderByComparator) {

		int count = countByUuid(uuid);

		if (count == 0) {
			return null;
		}

		List<BatchJobInstance> list = findByUuid(
			uuid, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the batch job instances before and after the current batch job instance in the ordered set where uuid = &#63;.
	 *
	 * @param batchJobInstanceId the primary key of the current batch job instance
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next batch job instance
	 * @throws NoSuchJobInstanceException if a batch job instance with the primary key could not be found
	 */
	@Override
	public BatchJobInstance[] findByUuid_PrevAndNext(
			long batchJobInstanceId, String uuid,
			OrderByComparator<BatchJobInstance> orderByComparator)
		throws NoSuchJobInstanceException {

		uuid = Objects.toString(uuid, "");

		BatchJobInstance batchJobInstance = findByPrimaryKey(
			batchJobInstanceId);

		Session session = null;

		try {
			session = openSession();

			BatchJobInstance[] array = new BatchJobInstanceImpl[3];

			array[0] = getByUuid_PrevAndNext(
				session, batchJobInstance, uuid, orderByComparator, true);

			array[1] = batchJobInstance;

			array[2] = getByUuid_PrevAndNext(
				session, batchJobInstance, uuid, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected BatchJobInstance getByUuid_PrevAndNext(
		Session session, BatchJobInstance batchJobInstance, String uuid,
		OrderByComparator<BatchJobInstance> orderByComparator,
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

		query.append(_SQL_SELECT_BATCHJOBINSTANCE_WHERE);

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
			query.append(BatchJobInstanceModelImpl.ORDER_BY_JPQL);
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
						batchJobInstance)) {

				qPos.add(orderByConditionValue);
			}
		}

		List<BatchJobInstance> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the batch job instances where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	@Override
	public void removeByUuid(String uuid) {
		for (BatchJobInstance batchJobInstance :
				findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(batchJobInstance);
		}
	}

	/**
	 * Returns the number of batch job instances where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching batch job instances
	 */
	@Override
	public int countByUuid(String uuid) {
		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = _finderPathCountByUuid;

		Object[] finderArgs = new Object[] {uuid};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_BATCHJOBINSTANCE_WHERE);

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
		"batchJobInstance.uuid = ?";

	private static final String _FINDER_COLUMN_UUID_UUID_3 =
		"(batchJobInstance.uuid IS NULL OR batchJobInstance.uuid = '')";

	private FinderPath _finderPathWithPaginationFindByUuid_C;
	private FinderPath _finderPathWithoutPaginationFindByUuid_C;
	private FinderPath _finderPathCountByUuid_C;

	/**
	 * Returns all the batch job instances where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching batch job instances
	 */
	@Override
	public List<BatchJobInstance> findByUuid_C(String uuid, long companyId) {
		return findByUuid_C(
			uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the batch job instances where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>BatchJobInstanceModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of batch job instances
	 * @param end the upper bound of the range of batch job instances (not inclusive)
	 * @return the range of matching batch job instances
	 */
	@Override
	public List<BatchJobInstance> findByUuid_C(
		String uuid, long companyId, int start, int end) {

		return findByUuid_C(uuid, companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the batch job instances where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>BatchJobInstanceModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of batch job instances
	 * @param end the upper bound of the range of batch job instances (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching batch job instances
	 */
	@Override
	public List<BatchJobInstance> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<BatchJobInstance> orderByComparator) {

		return findByUuid_C(
			uuid, companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the batch job instances where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>BatchJobInstanceModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of batch job instances
	 * @param end the upper bound of the range of batch job instances (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching batch job instances
	 */
	@Override
	public List<BatchJobInstance> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<BatchJobInstance> orderByComparator,
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

		List<BatchJobInstance> list = null;

		if (retrieveFromCache) {
			list = (List<BatchJobInstance>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (BatchJobInstance batchJobInstance : list) {
					if (!uuid.equals(batchJobInstance.getUuid()) ||
						(companyId != batchJobInstance.getCompanyId())) {

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

			query.append(_SQL_SELECT_BATCHJOBINSTANCE_WHERE);

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
				query.append(BatchJobInstanceModelImpl.ORDER_BY_JPQL);
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
					list = (List<BatchJobInstance>)QueryUtil.list(
						q, getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<BatchJobInstance>)QueryUtil.list(
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
	 * Returns the first batch job instance in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching batch job instance
	 * @throws NoSuchJobInstanceException if a matching batch job instance could not be found
	 */
	@Override
	public BatchJobInstance findByUuid_C_First(
			String uuid, long companyId,
			OrderByComparator<BatchJobInstance> orderByComparator)
		throws NoSuchJobInstanceException {

		BatchJobInstance batchJobInstance = fetchByUuid_C_First(
			uuid, companyId, orderByComparator);

		if (batchJobInstance != null) {
			return batchJobInstance;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(", companyId=");
		msg.append(companyId);

		msg.append("}");

		throw new NoSuchJobInstanceException(msg.toString());
	}

	/**
	 * Returns the first batch job instance in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching batch job instance, or <code>null</code> if a matching batch job instance could not be found
	 */
	@Override
	public BatchJobInstance fetchByUuid_C_First(
		String uuid, long companyId,
		OrderByComparator<BatchJobInstance> orderByComparator) {

		List<BatchJobInstance> list = findByUuid_C(
			uuid, companyId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last batch job instance in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching batch job instance
	 * @throws NoSuchJobInstanceException if a matching batch job instance could not be found
	 */
	@Override
	public BatchJobInstance findByUuid_C_Last(
			String uuid, long companyId,
			OrderByComparator<BatchJobInstance> orderByComparator)
		throws NoSuchJobInstanceException {

		BatchJobInstance batchJobInstance = fetchByUuid_C_Last(
			uuid, companyId, orderByComparator);

		if (batchJobInstance != null) {
			return batchJobInstance;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(", companyId=");
		msg.append(companyId);

		msg.append("}");

		throw new NoSuchJobInstanceException(msg.toString());
	}

	/**
	 * Returns the last batch job instance in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching batch job instance, or <code>null</code> if a matching batch job instance could not be found
	 */
	@Override
	public BatchJobInstance fetchByUuid_C_Last(
		String uuid, long companyId,
		OrderByComparator<BatchJobInstance> orderByComparator) {

		int count = countByUuid_C(uuid, companyId);

		if (count == 0) {
			return null;
		}

		List<BatchJobInstance> list = findByUuid_C(
			uuid, companyId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the batch job instances before and after the current batch job instance in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param batchJobInstanceId the primary key of the current batch job instance
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next batch job instance
	 * @throws NoSuchJobInstanceException if a batch job instance with the primary key could not be found
	 */
	@Override
	public BatchJobInstance[] findByUuid_C_PrevAndNext(
			long batchJobInstanceId, String uuid, long companyId,
			OrderByComparator<BatchJobInstance> orderByComparator)
		throws NoSuchJobInstanceException {

		uuid = Objects.toString(uuid, "");

		BatchJobInstance batchJobInstance = findByPrimaryKey(
			batchJobInstanceId);

		Session session = null;

		try {
			session = openSession();

			BatchJobInstance[] array = new BatchJobInstanceImpl[3];

			array[0] = getByUuid_C_PrevAndNext(
				session, batchJobInstance, uuid, companyId, orderByComparator,
				true);

			array[1] = batchJobInstance;

			array[2] = getByUuid_C_PrevAndNext(
				session, batchJobInstance, uuid, companyId, orderByComparator,
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

	protected BatchJobInstance getByUuid_C_PrevAndNext(
		Session session, BatchJobInstance batchJobInstance, String uuid,
		long companyId, OrderByComparator<BatchJobInstance> orderByComparator,
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

		query.append(_SQL_SELECT_BATCHJOBINSTANCE_WHERE);

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
			query.append(BatchJobInstanceModelImpl.ORDER_BY_JPQL);
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
						batchJobInstance)) {

				qPos.add(orderByConditionValue);
			}
		}

		List<BatchJobInstance> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the batch job instances where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	@Override
	public void removeByUuid_C(String uuid, long companyId) {
		for (BatchJobInstance batchJobInstance :
				findByUuid_C(
					uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(batchJobInstance);
		}
	}

	/**
	 * Returns the number of batch job instances where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching batch job instances
	 */
	@Override
	public int countByUuid_C(String uuid, long companyId) {
		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = _finderPathCountByUuid_C;

		Object[] finderArgs = new Object[] {uuid, companyId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_BATCHJOBINSTANCE_WHERE);

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
		"batchJobInstance.uuid = ? AND ";

	private static final String _FINDER_COLUMN_UUID_C_UUID_3 =
		"(batchJobInstance.uuid IS NULL OR batchJobInstance.uuid = '') AND ";

	private static final String _FINDER_COLUMN_UUID_C_COMPANYID_2 =
		"batchJobInstance.companyId = ?";

	private FinderPath _finderPathFetchByJN_JK;
	private FinderPath _finderPathCountByJN_JK;

	/**
	 * Returns the batch job instance where jobName = &#63; and jobKey = &#63; or throws a <code>NoSuchJobInstanceException</code> if it could not be found.
	 *
	 * @param jobName the job name
	 * @param jobKey the job key
	 * @return the matching batch job instance
	 * @throws NoSuchJobInstanceException if a matching batch job instance could not be found
	 */
	@Override
	public BatchJobInstance findByJN_JK(String jobName, String jobKey)
		throws NoSuchJobInstanceException {

		BatchJobInstance batchJobInstance = fetchByJN_JK(jobName, jobKey);

		if (batchJobInstance == null) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("jobName=");
			msg.append(jobName);

			msg.append(", jobKey=");
			msg.append(jobKey);

			msg.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(msg.toString());
			}

			throw new NoSuchJobInstanceException(msg.toString());
		}

		return batchJobInstance;
	}

	/**
	 * Returns the batch job instance where jobName = &#63; and jobKey = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param jobName the job name
	 * @param jobKey the job key
	 * @return the matching batch job instance, or <code>null</code> if a matching batch job instance could not be found
	 */
	@Override
	public BatchJobInstance fetchByJN_JK(String jobName, String jobKey) {
		return fetchByJN_JK(jobName, jobKey, true);
	}

	/**
	 * Returns the batch job instance where jobName = &#63; and jobKey = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param jobName the job name
	 * @param jobKey the job key
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the matching batch job instance, or <code>null</code> if a matching batch job instance could not be found
	 */
	@Override
	public BatchJobInstance fetchByJN_JK(
		String jobName, String jobKey, boolean retrieveFromCache) {

		jobName = Objects.toString(jobName, "");
		jobKey = Objects.toString(jobKey, "");

		Object[] finderArgs = new Object[] {jobName, jobKey};

		Object result = null;

		if (retrieveFromCache) {
			result = finderCache.getResult(
				_finderPathFetchByJN_JK, finderArgs, this);
		}

		if (result instanceof BatchJobInstance) {
			BatchJobInstance batchJobInstance = (BatchJobInstance)result;

			if (!Objects.equals(jobName, batchJobInstance.getJobName()) ||
				!Objects.equals(jobKey, batchJobInstance.getJobKey())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_SELECT_BATCHJOBINSTANCE_WHERE);

			boolean bindJobName = false;

			if (jobName.isEmpty()) {
				query.append(_FINDER_COLUMN_JN_JK_JOBNAME_3);
			}
			else {
				bindJobName = true;

				query.append(_FINDER_COLUMN_JN_JK_JOBNAME_2);
			}

			boolean bindJobKey = false;

			if (jobKey.isEmpty()) {
				query.append(_FINDER_COLUMN_JN_JK_JOBKEY_3);
			}
			else {
				bindJobKey = true;

				query.append(_FINDER_COLUMN_JN_JK_JOBKEY_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindJobName) {
					qPos.add(jobName);
				}

				if (bindJobKey) {
					qPos.add(jobKey);
				}

				List<BatchJobInstance> list = q.list();

				if (list.isEmpty()) {
					finderCache.putResult(
						_finderPathFetchByJN_JK, finderArgs, list);
				}
				else {
					if (list.size() > 1) {
						Collections.sort(list, Collections.reverseOrder());

						if (_log.isWarnEnabled()) {
							_log.warn(
								"BatchJobInstancePersistenceImpl.fetchByJN_JK(String, String, boolean) with parameters (" +
									StringUtil.merge(finderArgs) +
										") yields a result set with more than 1 result. This violates the logical unique restriction. There is no order guarantee on which result is returned by this finder.");
						}
					}

					BatchJobInstance batchJobInstance = list.get(0);

					result = batchJobInstance;

					cacheResult(batchJobInstance);
				}
			}
			catch (Exception e) {
				finderCache.removeResult(_finderPathFetchByJN_JK, finderArgs);

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
			return (BatchJobInstance)result;
		}
	}

	/**
	 * Removes the batch job instance where jobName = &#63; and jobKey = &#63; from the database.
	 *
	 * @param jobName the job name
	 * @param jobKey the job key
	 * @return the batch job instance that was removed
	 */
	@Override
	public BatchJobInstance removeByJN_JK(String jobName, String jobKey)
		throws NoSuchJobInstanceException {

		BatchJobInstance batchJobInstance = findByJN_JK(jobName, jobKey);

		return remove(batchJobInstance);
	}

	/**
	 * Returns the number of batch job instances where jobName = &#63; and jobKey = &#63;.
	 *
	 * @param jobName the job name
	 * @param jobKey the job key
	 * @return the number of matching batch job instances
	 */
	@Override
	public int countByJN_JK(String jobName, String jobKey) {
		jobName = Objects.toString(jobName, "");
		jobKey = Objects.toString(jobKey, "");

		FinderPath finderPath = _finderPathCountByJN_JK;

		Object[] finderArgs = new Object[] {jobName, jobKey};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_BATCHJOBINSTANCE_WHERE);

			boolean bindJobName = false;

			if (jobName.isEmpty()) {
				query.append(_FINDER_COLUMN_JN_JK_JOBNAME_3);
			}
			else {
				bindJobName = true;

				query.append(_FINDER_COLUMN_JN_JK_JOBNAME_2);
			}

			boolean bindJobKey = false;

			if (jobKey.isEmpty()) {
				query.append(_FINDER_COLUMN_JN_JK_JOBKEY_3);
			}
			else {
				bindJobKey = true;

				query.append(_FINDER_COLUMN_JN_JK_JOBKEY_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindJobName) {
					qPos.add(jobName);
				}

				if (bindJobKey) {
					qPos.add(jobKey);
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

	private static final String _FINDER_COLUMN_JN_JK_JOBNAME_2 =
		"batchJobInstance.jobName = ? AND ";

	private static final String _FINDER_COLUMN_JN_JK_JOBNAME_3 =
		"(batchJobInstance.jobName IS NULL OR batchJobInstance.jobName = '') AND ";

	private static final String _FINDER_COLUMN_JN_JK_JOBKEY_2 =
		"batchJobInstance.jobKey = ?";

	private static final String _FINDER_COLUMN_JN_JK_JOBKEY_3 =
		"(batchJobInstance.jobKey IS NULL OR batchJobInstance.jobKey = '')";

	public BatchJobInstancePersistenceImpl() {
		setModelClass(BatchJobInstance.class);

		setModelImplClass(BatchJobInstanceImpl.class);
		setModelPKClass(long.class);
		setEntityCacheEnabled(BatchJobInstanceModelImpl.ENTITY_CACHE_ENABLED);

		Map<String, String> dbColumnNames = new HashMap<String, String>();

		dbColumnNames.put("uuid", "uuid_");

		setDBColumnNames(dbColumnNames);
	}

	/**
	 * Caches the batch job instance in the entity cache if it is enabled.
	 *
	 * @param batchJobInstance the batch job instance
	 */
	@Override
	public void cacheResult(BatchJobInstance batchJobInstance) {
		entityCache.putResult(
			BatchJobInstanceModelImpl.ENTITY_CACHE_ENABLED,
			BatchJobInstanceImpl.class, batchJobInstance.getPrimaryKey(),
			batchJobInstance);

		finderCache.putResult(
			_finderPathFetchByJN_JK,
			new Object[] {
				batchJobInstance.getJobName(), batchJobInstance.getJobKey()
			},
			batchJobInstance);

		batchJobInstance.resetOriginalValues();
	}

	/**
	 * Caches the batch job instances in the entity cache if it is enabled.
	 *
	 * @param batchJobInstances the batch job instances
	 */
	@Override
	public void cacheResult(List<BatchJobInstance> batchJobInstances) {
		for (BatchJobInstance batchJobInstance : batchJobInstances) {
			if (entityCache.getResult(
					BatchJobInstanceModelImpl.ENTITY_CACHE_ENABLED,
					BatchJobInstanceImpl.class,
					batchJobInstance.getPrimaryKey()) == null) {

				cacheResult(batchJobInstance);
			}
			else {
				batchJobInstance.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all batch job instances.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(BatchJobInstanceImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the batch job instance.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(BatchJobInstance batchJobInstance) {
		entityCache.removeResult(
			BatchJobInstanceModelImpl.ENTITY_CACHE_ENABLED,
			BatchJobInstanceImpl.class, batchJobInstance.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache(
			(BatchJobInstanceModelImpl)batchJobInstance, true);
	}

	@Override
	public void clearCache(List<BatchJobInstance> batchJobInstances) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (BatchJobInstance batchJobInstance : batchJobInstances) {
			entityCache.removeResult(
				BatchJobInstanceModelImpl.ENTITY_CACHE_ENABLED,
				BatchJobInstanceImpl.class, batchJobInstance.getPrimaryKey());

			clearUniqueFindersCache(
				(BatchJobInstanceModelImpl)batchJobInstance, true);
		}
	}

	protected void cacheUniqueFindersCache(
		BatchJobInstanceModelImpl batchJobInstanceModelImpl) {

		Object[] args = new Object[] {
			batchJobInstanceModelImpl.getJobName(),
			batchJobInstanceModelImpl.getJobKey()
		};

		finderCache.putResult(
			_finderPathCountByJN_JK, args, Long.valueOf(1), false);
		finderCache.putResult(
			_finderPathFetchByJN_JK, args, batchJobInstanceModelImpl, false);
	}

	protected void clearUniqueFindersCache(
		BatchJobInstanceModelImpl batchJobInstanceModelImpl,
		boolean clearCurrent) {

		if (clearCurrent) {
			Object[] args = new Object[] {
				batchJobInstanceModelImpl.getJobName(),
				batchJobInstanceModelImpl.getJobKey()
			};

			finderCache.removeResult(_finderPathCountByJN_JK, args);
			finderCache.removeResult(_finderPathFetchByJN_JK, args);
		}

		if ((batchJobInstanceModelImpl.getColumnBitmask() &
			 _finderPathFetchByJN_JK.getColumnBitmask()) != 0) {

			Object[] args = new Object[] {
				batchJobInstanceModelImpl.getOriginalJobName(),
				batchJobInstanceModelImpl.getOriginalJobKey()
			};

			finderCache.removeResult(_finderPathCountByJN_JK, args);
			finderCache.removeResult(_finderPathFetchByJN_JK, args);
		}
	}

	/**
	 * Creates a new batch job instance with the primary key. Does not add the batch job instance to the database.
	 *
	 * @param batchJobInstanceId the primary key for the new batch job instance
	 * @return the new batch job instance
	 */
	@Override
	public BatchJobInstance create(long batchJobInstanceId) {
		BatchJobInstance batchJobInstance = new BatchJobInstanceImpl();

		batchJobInstance.setNew(true);
		batchJobInstance.setPrimaryKey(batchJobInstanceId);

		String uuid = PortalUUIDUtil.generate();

		batchJobInstance.setUuid(uuid);

		batchJobInstance.setCompanyId(companyProvider.getCompanyId());

		return batchJobInstance;
	}

	/**
	 * Removes the batch job instance with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param batchJobInstanceId the primary key of the batch job instance
	 * @return the batch job instance that was removed
	 * @throws NoSuchJobInstanceException if a batch job instance with the primary key could not be found
	 */
	@Override
	public BatchJobInstance remove(long batchJobInstanceId)
		throws NoSuchJobInstanceException {

		return remove((Serializable)batchJobInstanceId);
	}

	/**
	 * Removes the batch job instance with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the batch job instance
	 * @return the batch job instance that was removed
	 * @throws NoSuchJobInstanceException if a batch job instance with the primary key could not be found
	 */
	@Override
	public BatchJobInstance remove(Serializable primaryKey)
		throws NoSuchJobInstanceException {

		Session session = null;

		try {
			session = openSession();

			BatchJobInstance batchJobInstance = (BatchJobInstance)session.get(
				BatchJobInstanceImpl.class, primaryKey);

			if (batchJobInstance == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchJobInstanceException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(batchJobInstance);
		}
		catch (NoSuchJobInstanceException nsee) {
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
	protected BatchJobInstance removeImpl(BatchJobInstance batchJobInstance) {
		Session session = null;

		try {
			session = openSession();

			if (!session.contains(batchJobInstance)) {
				batchJobInstance = (BatchJobInstance)session.get(
					BatchJobInstanceImpl.class,
					batchJobInstance.getPrimaryKeyObj());
			}

			if (batchJobInstance != null) {
				session.delete(batchJobInstance);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (batchJobInstance != null) {
			clearCache(batchJobInstance);
		}

		return batchJobInstance;
	}

	@Override
	public BatchJobInstance updateImpl(BatchJobInstance batchJobInstance) {
		boolean isNew = batchJobInstance.isNew();

		if (!(batchJobInstance instanceof BatchJobInstanceModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(batchJobInstance.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					batchJobInstance);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in batchJobInstance proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom BatchJobInstance implementation " +
					batchJobInstance.getClass());
		}

		BatchJobInstanceModelImpl batchJobInstanceModelImpl =
			(BatchJobInstanceModelImpl)batchJobInstance;

		if (Validator.isNull(batchJobInstance.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			batchJobInstance.setUuid(uuid);
		}

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Date now = new Date();

		if (isNew && (batchJobInstance.getCreateDate() == null)) {
			if (serviceContext == null) {
				batchJobInstance.setCreateDate(now);
			}
			else {
				batchJobInstance.setCreateDate(
					serviceContext.getCreateDate(now));
			}
		}

		if (!batchJobInstanceModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				batchJobInstance.setModifiedDate(now);
			}
			else {
				batchJobInstance.setModifiedDate(
					serviceContext.getModifiedDate(now));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (batchJobInstance.isNew()) {
				session.save(batchJobInstance);

				batchJobInstance.setNew(false);
			}
			else {
				batchJobInstance = (BatchJobInstance)session.merge(
					batchJobInstance);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (!BatchJobInstanceModelImpl.COLUMN_BITMASK_ENABLED) {
			finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}
		else if (isNew) {
			Object[] args = new Object[] {batchJobInstanceModelImpl.getUuid()};

			finderCache.removeResult(_finderPathCountByUuid, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByUuid, args);

			args = new Object[] {
				batchJobInstanceModelImpl.getUuid(),
				batchJobInstanceModelImpl.getCompanyId()
			};

			finderCache.removeResult(_finderPathCountByUuid_C, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByUuid_C, args);

			finderCache.removeResult(_finderPathCountAll, FINDER_ARGS_EMPTY);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindAll, FINDER_ARGS_EMPTY);
		}
		else {
			if ((batchJobInstanceModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByUuid.getColumnBitmask()) !=
					 0) {

				Object[] args = new Object[] {
					batchJobInstanceModelImpl.getOriginalUuid()
				};

				finderCache.removeResult(_finderPathCountByUuid, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByUuid, args);

				args = new Object[] {batchJobInstanceModelImpl.getUuid()};

				finderCache.removeResult(_finderPathCountByUuid, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByUuid, args);
			}

			if ((batchJobInstanceModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByUuid_C.getColumnBitmask()) !=
					 0) {

				Object[] args = new Object[] {
					batchJobInstanceModelImpl.getOriginalUuid(),
					batchJobInstanceModelImpl.getOriginalCompanyId()
				};

				finderCache.removeResult(_finderPathCountByUuid_C, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByUuid_C, args);

				args = new Object[] {
					batchJobInstanceModelImpl.getUuid(),
					batchJobInstanceModelImpl.getCompanyId()
				};

				finderCache.removeResult(_finderPathCountByUuid_C, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByUuid_C, args);
			}
		}

		entityCache.putResult(
			BatchJobInstanceModelImpl.ENTITY_CACHE_ENABLED,
			BatchJobInstanceImpl.class, batchJobInstance.getPrimaryKey(),
			batchJobInstance, false);

		clearUniqueFindersCache(batchJobInstanceModelImpl, false);
		cacheUniqueFindersCache(batchJobInstanceModelImpl);

		batchJobInstance.resetOriginalValues();

		return batchJobInstance;
	}

	/**
	 * Returns the batch job instance with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the batch job instance
	 * @return the batch job instance
	 * @throws NoSuchJobInstanceException if a batch job instance with the primary key could not be found
	 */
	@Override
	public BatchJobInstance findByPrimaryKey(Serializable primaryKey)
		throws NoSuchJobInstanceException {

		BatchJobInstance batchJobInstance = fetchByPrimaryKey(primaryKey);

		if (batchJobInstance == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchJobInstanceException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return batchJobInstance;
	}

	/**
	 * Returns the batch job instance with the primary key or throws a <code>NoSuchJobInstanceException</code> if it could not be found.
	 *
	 * @param batchJobInstanceId the primary key of the batch job instance
	 * @return the batch job instance
	 * @throws NoSuchJobInstanceException if a batch job instance with the primary key could not be found
	 */
	@Override
	public BatchJobInstance findByPrimaryKey(long batchJobInstanceId)
		throws NoSuchJobInstanceException {

		return findByPrimaryKey((Serializable)batchJobInstanceId);
	}

	/**
	 * Returns the batch job instance with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param batchJobInstanceId the primary key of the batch job instance
	 * @return the batch job instance, or <code>null</code> if a batch job instance with the primary key could not be found
	 */
	@Override
	public BatchJobInstance fetchByPrimaryKey(long batchJobInstanceId) {
		return fetchByPrimaryKey((Serializable)batchJobInstanceId);
	}

	/**
	 * Returns all the batch job instances.
	 *
	 * @return the batch job instances
	 */
	@Override
	public List<BatchJobInstance> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the batch job instances.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>BatchJobInstanceModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of batch job instances
	 * @param end the upper bound of the range of batch job instances (not inclusive)
	 * @return the range of batch job instances
	 */
	@Override
	public List<BatchJobInstance> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the batch job instances.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>BatchJobInstanceModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of batch job instances
	 * @param end the upper bound of the range of batch job instances (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of batch job instances
	 */
	@Override
	public List<BatchJobInstance> findAll(
		int start, int end,
		OrderByComparator<BatchJobInstance> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the batch job instances.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>BatchJobInstanceModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of batch job instances
	 * @param end the upper bound of the range of batch job instances (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of batch job instances
	 */
	@Override
	public List<BatchJobInstance> findAll(
		int start, int end,
		OrderByComparator<BatchJobInstance> orderByComparator,
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

		List<BatchJobInstance> list = null;

		if (retrieveFromCache) {
			list = (List<BatchJobInstance>)finderCache.getResult(
				finderPath, finderArgs, this);
		}

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				query.append(_SQL_SELECT_BATCHJOBINSTANCE);

				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_BATCHJOBINSTANCE;

				if (pagination) {
					sql = sql.concat(BatchJobInstanceModelImpl.ORDER_BY_JPQL);
				}
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (!pagination) {
					list = (List<BatchJobInstance>)QueryUtil.list(
						q, getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<BatchJobInstance>)QueryUtil.list(
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
	 * Removes all the batch job instances from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (BatchJobInstance batchJobInstance : findAll()) {
			remove(batchJobInstance);
		}
	}

	/**
	 * Returns the number of batch job instances.
	 *
	 * @return the number of batch job instances
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(
			_finderPathCountAll, FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_BATCHJOBINSTANCE);

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
		return "batchJobInstanceId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_BATCHJOBINSTANCE;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return BatchJobInstanceModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the batch job instance persistence.
	 */
	public void afterPropertiesSet() {
		_finderPathWithPaginationFindAll = new FinderPath(
			BatchJobInstanceModelImpl.ENTITY_CACHE_ENABLED,
			BatchJobInstanceModelImpl.FINDER_CACHE_ENABLED,
			BatchJobInstanceImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findAll", new String[0]);

		_finderPathWithoutPaginationFindAll = new FinderPath(
			BatchJobInstanceModelImpl.ENTITY_CACHE_ENABLED,
			BatchJobInstanceModelImpl.FINDER_CACHE_ENABLED,
			BatchJobInstanceImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll",
			new String[0]);

		_finderPathCountAll = new FinderPath(
			BatchJobInstanceModelImpl.ENTITY_CACHE_ENABLED,
			BatchJobInstanceModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll",
			new String[0]);

		_finderPathWithPaginationFindByUuid = new FinderPath(
			BatchJobInstanceModelImpl.ENTITY_CACHE_ENABLED,
			BatchJobInstanceModelImpl.FINDER_CACHE_ENABLED,
			BatchJobInstanceImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByUuid",
			new String[] {
				String.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByUuid = new FinderPath(
			BatchJobInstanceModelImpl.ENTITY_CACHE_ENABLED,
			BatchJobInstanceModelImpl.FINDER_CACHE_ENABLED,
			BatchJobInstanceImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid",
			new String[] {String.class.getName()},
			BatchJobInstanceModelImpl.UUID_COLUMN_BITMASK);

		_finderPathCountByUuid = new FinderPath(
			BatchJobInstanceModelImpl.ENTITY_CACHE_ENABLED,
			BatchJobInstanceModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUuid",
			new String[] {String.class.getName()});

		_finderPathWithPaginationFindByUuid_C = new FinderPath(
			BatchJobInstanceModelImpl.ENTITY_CACHE_ENABLED,
			BatchJobInstanceModelImpl.FINDER_CACHE_ENABLED,
			BatchJobInstanceImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByUuid_C",
			new String[] {
				String.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByUuid_C = new FinderPath(
			BatchJobInstanceModelImpl.ENTITY_CACHE_ENABLED,
			BatchJobInstanceModelImpl.FINDER_CACHE_ENABLED,
			BatchJobInstanceImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid_C",
			new String[] {String.class.getName(), Long.class.getName()},
			BatchJobInstanceModelImpl.UUID_COLUMN_BITMASK |
			BatchJobInstanceModelImpl.COMPANYID_COLUMN_BITMASK);

		_finderPathCountByUuid_C = new FinderPath(
			BatchJobInstanceModelImpl.ENTITY_CACHE_ENABLED,
			BatchJobInstanceModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUuid_C",
			new String[] {String.class.getName(), Long.class.getName()});

		_finderPathFetchByJN_JK = new FinderPath(
			BatchJobInstanceModelImpl.ENTITY_CACHE_ENABLED,
			BatchJobInstanceModelImpl.FINDER_CACHE_ENABLED,
			BatchJobInstanceImpl.class, FINDER_CLASS_NAME_ENTITY,
			"fetchByJN_JK",
			new String[] {String.class.getName(), String.class.getName()},
			BatchJobInstanceModelImpl.JOBNAME_COLUMN_BITMASK |
			BatchJobInstanceModelImpl.JOBKEY_COLUMN_BITMASK);

		_finderPathCountByJN_JK = new FinderPath(
			BatchJobInstanceModelImpl.ENTITY_CACHE_ENABLED,
			BatchJobInstanceModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByJN_JK",
			new String[] {String.class.getName(), String.class.getName()});
	}

	public void destroy() {
		entityCache.removeCache(BatchJobInstanceImpl.class.getName());
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

	private static final String _SQL_SELECT_BATCHJOBINSTANCE =
		"SELECT batchJobInstance FROM BatchJobInstance batchJobInstance";

	private static final String _SQL_SELECT_BATCHJOBINSTANCE_WHERE =
		"SELECT batchJobInstance FROM BatchJobInstance batchJobInstance WHERE ";

	private static final String _SQL_COUNT_BATCHJOBINSTANCE =
		"SELECT COUNT(batchJobInstance) FROM BatchJobInstance batchJobInstance";

	private static final String _SQL_COUNT_BATCHJOBINSTANCE_WHERE =
		"SELECT COUNT(batchJobInstance) FROM BatchJobInstance batchJobInstance WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS = "batchJobInstance.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No BatchJobInstance exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No BatchJobInstance exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		BatchJobInstancePersistenceImpl.class);

	private static final Set<String> _badColumnNames = SetUtil.fromArray(
		new String[] {"uuid"});

}