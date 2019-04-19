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

import com.liferay.batch.engine.exception.NoSuchFileImportException;
import com.liferay.batch.engine.model.BatchFileImport;
import com.liferay.batch.engine.model.impl.BatchFileImportImpl;
import com.liferay.batch.engine.model.impl.BatchFileImportModelImpl;
import com.liferay.batch.engine.service.persistence.BatchFileImportPersistence;
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
 * The persistence implementation for the batch file import service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Ivica Cardic
 * @generated
 */
@ProviderType
public class BatchFileImportPersistenceImpl
	extends BasePersistenceImpl<BatchFileImport>
	implements BatchFileImportPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>BatchFileImportUtil</code> to access the batch file import persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		BatchFileImportImpl.class.getName();

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
	 * Returns all the batch file imports where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching batch file imports
	 */
	@Override
	public List<BatchFileImport> findByUuid(String uuid) {
		return findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the batch file imports where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>BatchFileImportModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of batch file imports
	 * @param end the upper bound of the range of batch file imports (not inclusive)
	 * @return the range of matching batch file imports
	 */
	@Override
	public List<BatchFileImport> findByUuid(String uuid, int start, int end) {
		return findByUuid(uuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the batch file imports where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>BatchFileImportModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of batch file imports
	 * @param end the upper bound of the range of batch file imports (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching batch file imports
	 */
	@Override
	public List<BatchFileImport> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<BatchFileImport> orderByComparator) {

		return findByUuid(uuid, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the batch file imports where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>BatchFileImportModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of batch file imports
	 * @param end the upper bound of the range of batch file imports (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching batch file imports
	 */
	@Override
	public List<BatchFileImport> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<BatchFileImport> orderByComparator,
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

		List<BatchFileImport> list = null;

		if (retrieveFromCache) {
			list = (List<BatchFileImport>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (BatchFileImport batchFileImport : list) {
					if (!uuid.equals(batchFileImport.getUuid())) {
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

			query.append(_SQL_SELECT_BATCHFILEIMPORT_WHERE);

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
				query.append(BatchFileImportModelImpl.ORDER_BY_JPQL);
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
					list = (List<BatchFileImport>)QueryUtil.list(
						q, getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<BatchFileImport>)QueryUtil.list(
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
	 * Returns the first batch file import in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching batch file import
	 * @throws NoSuchFileImportException if a matching batch file import could not be found
	 */
	@Override
	public BatchFileImport findByUuid_First(
			String uuid, OrderByComparator<BatchFileImport> orderByComparator)
		throws NoSuchFileImportException {

		BatchFileImport batchFileImport = fetchByUuid_First(
			uuid, orderByComparator);

		if (batchFileImport != null) {
			return batchFileImport;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append("}");

		throw new NoSuchFileImportException(msg.toString());
	}

	/**
	 * Returns the first batch file import in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching batch file import, or <code>null</code> if a matching batch file import could not be found
	 */
	@Override
	public BatchFileImport fetchByUuid_First(
		String uuid, OrderByComparator<BatchFileImport> orderByComparator) {

		List<BatchFileImport> list = findByUuid(uuid, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last batch file import in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching batch file import
	 * @throws NoSuchFileImportException if a matching batch file import could not be found
	 */
	@Override
	public BatchFileImport findByUuid_Last(
			String uuid, OrderByComparator<BatchFileImport> orderByComparator)
		throws NoSuchFileImportException {

		BatchFileImport batchFileImport = fetchByUuid_Last(
			uuid, orderByComparator);

		if (batchFileImport != null) {
			return batchFileImport;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append("}");

		throw new NoSuchFileImportException(msg.toString());
	}

	/**
	 * Returns the last batch file import in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching batch file import, or <code>null</code> if a matching batch file import could not be found
	 */
	@Override
	public BatchFileImport fetchByUuid_Last(
		String uuid, OrderByComparator<BatchFileImport> orderByComparator) {

		int count = countByUuid(uuid);

		if (count == 0) {
			return null;
		}

		List<BatchFileImport> list = findByUuid(
			uuid, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the batch file imports before and after the current batch file import in the ordered set where uuid = &#63;.
	 *
	 * @param batchFileImportId the primary key of the current batch file import
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next batch file import
	 * @throws NoSuchFileImportException if a batch file import with the primary key could not be found
	 */
	@Override
	public BatchFileImport[] findByUuid_PrevAndNext(
			long batchFileImportId, String uuid,
			OrderByComparator<BatchFileImport> orderByComparator)
		throws NoSuchFileImportException {

		uuid = Objects.toString(uuid, "");

		BatchFileImport batchFileImport = findByPrimaryKey(batchFileImportId);

		Session session = null;

		try {
			session = openSession();

			BatchFileImport[] array = new BatchFileImportImpl[3];

			array[0] = getByUuid_PrevAndNext(
				session, batchFileImport, uuid, orderByComparator, true);

			array[1] = batchFileImport;

			array[2] = getByUuid_PrevAndNext(
				session, batchFileImport, uuid, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected BatchFileImport getByUuid_PrevAndNext(
		Session session, BatchFileImport batchFileImport, String uuid,
		OrderByComparator<BatchFileImport> orderByComparator,
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

		query.append(_SQL_SELECT_BATCHFILEIMPORT_WHERE);

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
			query.append(BatchFileImportModelImpl.ORDER_BY_JPQL);
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
						batchFileImport)) {

				qPos.add(orderByConditionValue);
			}
		}

		List<BatchFileImport> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the batch file imports where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	@Override
	public void removeByUuid(String uuid) {
		for (BatchFileImport batchFileImport :
				findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(batchFileImport);
		}
	}

	/**
	 * Returns the number of batch file imports where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching batch file imports
	 */
	@Override
	public int countByUuid(String uuid) {
		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = _finderPathCountByUuid;

		Object[] finderArgs = new Object[] {uuid};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_BATCHFILEIMPORT_WHERE);

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
		"batchFileImport.uuid = ?";

	private static final String _FINDER_COLUMN_UUID_UUID_3 =
		"(batchFileImport.uuid IS NULL OR batchFileImport.uuid = '')";

	private FinderPath _finderPathWithPaginationFindByUuid_C;
	private FinderPath _finderPathWithoutPaginationFindByUuid_C;
	private FinderPath _finderPathCountByUuid_C;

	/**
	 * Returns all the batch file imports where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching batch file imports
	 */
	@Override
	public List<BatchFileImport> findByUuid_C(String uuid, long companyId) {
		return findByUuid_C(
			uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the batch file imports where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>BatchFileImportModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of batch file imports
	 * @param end the upper bound of the range of batch file imports (not inclusive)
	 * @return the range of matching batch file imports
	 */
	@Override
	public List<BatchFileImport> findByUuid_C(
		String uuid, long companyId, int start, int end) {

		return findByUuid_C(uuid, companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the batch file imports where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>BatchFileImportModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of batch file imports
	 * @param end the upper bound of the range of batch file imports (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching batch file imports
	 */
	@Override
	public List<BatchFileImport> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<BatchFileImport> orderByComparator) {

		return findByUuid_C(
			uuid, companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the batch file imports where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>BatchFileImportModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of batch file imports
	 * @param end the upper bound of the range of batch file imports (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching batch file imports
	 */
	@Override
	public List<BatchFileImport> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<BatchFileImport> orderByComparator,
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

		List<BatchFileImport> list = null;

		if (retrieveFromCache) {
			list = (List<BatchFileImport>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (BatchFileImport batchFileImport : list) {
					if (!uuid.equals(batchFileImport.getUuid()) ||
						(companyId != batchFileImport.getCompanyId())) {

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

			query.append(_SQL_SELECT_BATCHFILEIMPORT_WHERE);

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
				query.append(BatchFileImportModelImpl.ORDER_BY_JPQL);
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
					list = (List<BatchFileImport>)QueryUtil.list(
						q, getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<BatchFileImport>)QueryUtil.list(
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
	 * Returns the first batch file import in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching batch file import
	 * @throws NoSuchFileImportException if a matching batch file import could not be found
	 */
	@Override
	public BatchFileImport findByUuid_C_First(
			String uuid, long companyId,
			OrderByComparator<BatchFileImport> orderByComparator)
		throws NoSuchFileImportException {

		BatchFileImport batchFileImport = fetchByUuid_C_First(
			uuid, companyId, orderByComparator);

		if (batchFileImport != null) {
			return batchFileImport;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(", companyId=");
		msg.append(companyId);

		msg.append("}");

		throw new NoSuchFileImportException(msg.toString());
	}

	/**
	 * Returns the first batch file import in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching batch file import, or <code>null</code> if a matching batch file import could not be found
	 */
	@Override
	public BatchFileImport fetchByUuid_C_First(
		String uuid, long companyId,
		OrderByComparator<BatchFileImport> orderByComparator) {

		List<BatchFileImport> list = findByUuid_C(
			uuid, companyId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last batch file import in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching batch file import
	 * @throws NoSuchFileImportException if a matching batch file import could not be found
	 */
	@Override
	public BatchFileImport findByUuid_C_Last(
			String uuid, long companyId,
			OrderByComparator<BatchFileImport> orderByComparator)
		throws NoSuchFileImportException {

		BatchFileImport batchFileImport = fetchByUuid_C_Last(
			uuid, companyId, orderByComparator);

		if (batchFileImport != null) {
			return batchFileImport;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(", companyId=");
		msg.append(companyId);

		msg.append("}");

		throw new NoSuchFileImportException(msg.toString());
	}

	/**
	 * Returns the last batch file import in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching batch file import, or <code>null</code> if a matching batch file import could not be found
	 */
	@Override
	public BatchFileImport fetchByUuid_C_Last(
		String uuid, long companyId,
		OrderByComparator<BatchFileImport> orderByComparator) {

		int count = countByUuid_C(uuid, companyId);

		if (count == 0) {
			return null;
		}

		List<BatchFileImport> list = findByUuid_C(
			uuid, companyId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the batch file imports before and after the current batch file import in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param batchFileImportId the primary key of the current batch file import
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next batch file import
	 * @throws NoSuchFileImportException if a batch file import with the primary key could not be found
	 */
	@Override
	public BatchFileImport[] findByUuid_C_PrevAndNext(
			long batchFileImportId, String uuid, long companyId,
			OrderByComparator<BatchFileImport> orderByComparator)
		throws NoSuchFileImportException {

		uuid = Objects.toString(uuid, "");

		BatchFileImport batchFileImport = findByPrimaryKey(batchFileImportId);

		Session session = null;

		try {
			session = openSession();

			BatchFileImport[] array = new BatchFileImportImpl[3];

			array[0] = getByUuid_C_PrevAndNext(
				session, batchFileImport, uuid, companyId, orderByComparator,
				true);

			array[1] = batchFileImport;

			array[2] = getByUuid_C_PrevAndNext(
				session, batchFileImport, uuid, companyId, orderByComparator,
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

	protected BatchFileImport getByUuid_C_PrevAndNext(
		Session session, BatchFileImport batchFileImport, String uuid,
		long companyId, OrderByComparator<BatchFileImport> orderByComparator,
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

		query.append(_SQL_SELECT_BATCHFILEIMPORT_WHERE);

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
			query.append(BatchFileImportModelImpl.ORDER_BY_JPQL);
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
						batchFileImport)) {

				qPos.add(orderByConditionValue);
			}
		}

		List<BatchFileImport> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the batch file imports where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	@Override
	public void removeByUuid_C(String uuid, long companyId) {
		for (BatchFileImport batchFileImport :
				findByUuid_C(
					uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(batchFileImport);
		}
	}

	/**
	 * Returns the number of batch file imports where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching batch file imports
	 */
	@Override
	public int countByUuid_C(String uuid, long companyId) {
		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = _finderPathCountByUuid_C;

		Object[] finderArgs = new Object[] {uuid, companyId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_BATCHFILEIMPORT_WHERE);

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
		"batchFileImport.uuid = ? AND ";

	private static final String _FINDER_COLUMN_UUID_C_UUID_3 =
		"(batchFileImport.uuid IS NULL OR batchFileImport.uuid = '') AND ";

	private static final String _FINDER_COLUMN_UUID_C_COMPANYID_2 =
		"batchFileImport.companyId = ?";

	private FinderPath _finderPathFetchByBatchJobExecutionId;
	private FinderPath _finderPathCountByBatchJobExecutionId;

	/**
	 * Returns the batch file import where batchJobExecutionId = &#63; or throws a <code>NoSuchFileImportException</code> if it could not be found.
	 *
	 * @param batchJobExecutionId the batch job execution ID
	 * @return the matching batch file import
	 * @throws NoSuchFileImportException if a matching batch file import could not be found
	 */
	@Override
	public BatchFileImport findByBatchJobExecutionId(long batchJobExecutionId)
		throws NoSuchFileImportException {

		BatchFileImport batchFileImport = fetchByBatchJobExecutionId(
			batchJobExecutionId);

		if (batchFileImport == null) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("batchJobExecutionId=");
			msg.append(batchJobExecutionId);

			msg.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(msg.toString());
			}

			throw new NoSuchFileImportException(msg.toString());
		}

		return batchFileImport;
	}

	/**
	 * Returns the batch file import where batchJobExecutionId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param batchJobExecutionId the batch job execution ID
	 * @return the matching batch file import, or <code>null</code> if a matching batch file import could not be found
	 */
	@Override
	public BatchFileImport fetchByBatchJobExecutionId(
		long batchJobExecutionId) {

		return fetchByBatchJobExecutionId(batchJobExecutionId, true);
	}

	/**
	 * Returns the batch file import where batchJobExecutionId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param batchJobExecutionId the batch job execution ID
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the matching batch file import, or <code>null</code> if a matching batch file import could not be found
	 */
	@Override
	public BatchFileImport fetchByBatchJobExecutionId(
		long batchJobExecutionId, boolean retrieveFromCache) {

		Object[] finderArgs = new Object[] {batchJobExecutionId};

		Object result = null;

		if (retrieveFromCache) {
			result = finderCache.getResult(
				_finderPathFetchByBatchJobExecutionId, finderArgs, this);
		}

		if (result instanceof BatchFileImport) {
			BatchFileImport batchFileImport = (BatchFileImport)result;

			if ((batchJobExecutionId !=
					batchFileImport.getBatchJobExecutionId())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_SELECT_BATCHFILEIMPORT_WHERE);

			query.append(
				_FINDER_COLUMN_BATCHJOBEXECUTIONID_BATCHJOBEXECUTIONID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(batchJobExecutionId);

				List<BatchFileImport> list = q.list();

				if (list.isEmpty()) {
					finderCache.putResult(
						_finderPathFetchByBatchJobExecutionId, finderArgs,
						list);
				}
				else {
					if (list.size() > 1) {
						Collections.sort(list, Collections.reverseOrder());

						if (_log.isWarnEnabled()) {
							_log.warn(
								"BatchFileImportPersistenceImpl.fetchByBatchJobExecutionId(long, boolean) with parameters (" +
									StringUtil.merge(finderArgs) +
										") yields a result set with more than 1 result. This violates the logical unique restriction. There is no order guarantee on which result is returned by this finder.");
						}
					}

					BatchFileImport batchFileImport = list.get(0);

					result = batchFileImport;

					cacheResult(batchFileImport);
				}
			}
			catch (Exception e) {
				finderCache.removeResult(
					_finderPathFetchByBatchJobExecutionId, finderArgs);

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
			return (BatchFileImport)result;
		}
	}

	/**
	 * Removes the batch file import where batchJobExecutionId = &#63; from the database.
	 *
	 * @param batchJobExecutionId the batch job execution ID
	 * @return the batch file import that was removed
	 */
	@Override
	public BatchFileImport removeByBatchJobExecutionId(long batchJobExecutionId)
		throws NoSuchFileImportException {

		BatchFileImport batchFileImport = findByBatchJobExecutionId(
			batchJobExecutionId);

		return remove(batchFileImport);
	}

	/**
	 * Returns the number of batch file imports where batchJobExecutionId = &#63;.
	 *
	 * @param batchJobExecutionId the batch job execution ID
	 * @return the number of matching batch file imports
	 */
	@Override
	public int countByBatchJobExecutionId(long batchJobExecutionId) {
		FinderPath finderPath = _finderPathCountByBatchJobExecutionId;

		Object[] finderArgs = new Object[] {batchJobExecutionId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_BATCHFILEIMPORT_WHERE);

			query.append(
				_FINDER_COLUMN_BATCHJOBEXECUTIONID_BATCHJOBEXECUTIONID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(batchJobExecutionId);

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
		_FINDER_COLUMN_BATCHJOBEXECUTIONID_BATCHJOBEXECUTIONID_2 =
			"batchFileImport.batchJobExecutionId = ?";

	private FinderPath _finderPathWithPaginationFindByStatus;
	private FinderPath _finderPathWithoutPaginationFindByStatus;
	private FinderPath _finderPathCountByStatus;

	/**
	 * Returns all the batch file imports where status = &#63;.
	 *
	 * @param status the status
	 * @return the matching batch file imports
	 */
	@Override
	public List<BatchFileImport> findByStatus(String status) {
		return findByStatus(status, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the batch file imports where status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>BatchFileImportModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param status the status
	 * @param start the lower bound of the range of batch file imports
	 * @param end the upper bound of the range of batch file imports (not inclusive)
	 * @return the range of matching batch file imports
	 */
	@Override
	public List<BatchFileImport> findByStatus(
		String status, int start, int end) {

		return findByStatus(status, start, end, null);
	}

	/**
	 * Returns an ordered range of all the batch file imports where status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>BatchFileImportModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param status the status
	 * @param start the lower bound of the range of batch file imports
	 * @param end the upper bound of the range of batch file imports (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching batch file imports
	 */
	@Override
	public List<BatchFileImport> findByStatus(
		String status, int start, int end,
		OrderByComparator<BatchFileImport> orderByComparator) {

		return findByStatus(status, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the batch file imports where status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>BatchFileImportModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param status the status
	 * @param start the lower bound of the range of batch file imports
	 * @param end the upper bound of the range of batch file imports (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching batch file imports
	 */
	@Override
	public List<BatchFileImport> findByStatus(
		String status, int start, int end,
		OrderByComparator<BatchFileImport> orderByComparator,
		boolean retrieveFromCache) {

		status = Objects.toString(status, "");

		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			pagination = false;
			finderPath = _finderPathWithoutPaginationFindByStatus;
			finderArgs = new Object[] {status};
		}
		else {
			finderPath = _finderPathWithPaginationFindByStatus;
			finderArgs = new Object[] {status, start, end, orderByComparator};
		}

		List<BatchFileImport> list = null;

		if (retrieveFromCache) {
			list = (List<BatchFileImport>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (BatchFileImport batchFileImport : list) {
					if (!status.equals(batchFileImport.getStatus())) {
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

			query.append(_SQL_SELECT_BATCHFILEIMPORT_WHERE);

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
				query.append(BatchFileImportModelImpl.ORDER_BY_JPQL);
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
					list = (List<BatchFileImport>)QueryUtil.list(
						q, getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<BatchFileImport>)QueryUtil.list(
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
	 * Returns the first batch file import in the ordered set where status = &#63;.
	 *
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching batch file import
	 * @throws NoSuchFileImportException if a matching batch file import could not be found
	 */
	@Override
	public BatchFileImport findByStatus_First(
			String status, OrderByComparator<BatchFileImport> orderByComparator)
		throws NoSuchFileImportException {

		BatchFileImport batchFileImport = fetchByStatus_First(
			status, orderByComparator);

		if (batchFileImport != null) {
			return batchFileImport;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("status=");
		msg.append(status);

		msg.append("}");

		throw new NoSuchFileImportException(msg.toString());
	}

	/**
	 * Returns the first batch file import in the ordered set where status = &#63;.
	 *
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching batch file import, or <code>null</code> if a matching batch file import could not be found
	 */
	@Override
	public BatchFileImport fetchByStatus_First(
		String status, OrderByComparator<BatchFileImport> orderByComparator) {

		List<BatchFileImport> list = findByStatus(
			status, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last batch file import in the ordered set where status = &#63;.
	 *
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching batch file import
	 * @throws NoSuchFileImportException if a matching batch file import could not be found
	 */
	@Override
	public BatchFileImport findByStatus_Last(
			String status, OrderByComparator<BatchFileImport> orderByComparator)
		throws NoSuchFileImportException {

		BatchFileImport batchFileImport = fetchByStatus_Last(
			status, orderByComparator);

		if (batchFileImport != null) {
			return batchFileImport;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("status=");
		msg.append(status);

		msg.append("}");

		throw new NoSuchFileImportException(msg.toString());
	}

	/**
	 * Returns the last batch file import in the ordered set where status = &#63;.
	 *
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching batch file import, or <code>null</code> if a matching batch file import could not be found
	 */
	@Override
	public BatchFileImport fetchByStatus_Last(
		String status, OrderByComparator<BatchFileImport> orderByComparator) {

		int count = countByStatus(status);

		if (count == 0) {
			return null;
		}

		List<BatchFileImport> list = findByStatus(
			status, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the batch file imports before and after the current batch file import in the ordered set where status = &#63;.
	 *
	 * @param batchFileImportId the primary key of the current batch file import
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next batch file import
	 * @throws NoSuchFileImportException if a batch file import with the primary key could not be found
	 */
	@Override
	public BatchFileImport[] findByStatus_PrevAndNext(
			long batchFileImportId, String status,
			OrderByComparator<BatchFileImport> orderByComparator)
		throws NoSuchFileImportException {

		status = Objects.toString(status, "");

		BatchFileImport batchFileImport = findByPrimaryKey(batchFileImportId);

		Session session = null;

		try {
			session = openSession();

			BatchFileImport[] array = new BatchFileImportImpl[3];

			array[0] = getByStatus_PrevAndNext(
				session, batchFileImport, status, orderByComparator, true);

			array[1] = batchFileImport;

			array[2] = getByStatus_PrevAndNext(
				session, batchFileImport, status, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected BatchFileImport getByStatus_PrevAndNext(
		Session session, BatchFileImport batchFileImport, String status,
		OrderByComparator<BatchFileImport> orderByComparator,
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

		query.append(_SQL_SELECT_BATCHFILEIMPORT_WHERE);

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
			query.append(BatchFileImportModelImpl.ORDER_BY_JPQL);
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
						batchFileImport)) {

				qPos.add(orderByConditionValue);
			}
		}

		List<BatchFileImport> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the batch file imports where status = &#63; from the database.
	 *
	 * @param status the status
	 */
	@Override
	public void removeByStatus(String status) {
		for (BatchFileImport batchFileImport :
				findByStatus(
					status, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(batchFileImport);
		}
	}

	/**
	 * Returns the number of batch file imports where status = &#63;.
	 *
	 * @param status the status
	 * @return the number of matching batch file imports
	 */
	@Override
	public int countByStatus(String status) {
		status = Objects.toString(status, "");

		FinderPath finderPath = _finderPathCountByStatus;

		Object[] finderArgs = new Object[] {status};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_BATCHFILEIMPORT_WHERE);

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
		"batchFileImport.status = ?";

	private static final String _FINDER_COLUMN_STATUS_STATUS_3 =
		"(batchFileImport.status IS NULL OR batchFileImport.status = '')";

	public BatchFileImportPersistenceImpl() {
		setModelClass(BatchFileImport.class);

		setModelImplClass(BatchFileImportImpl.class);
		setModelPKClass(long.class);
		setEntityCacheEnabled(BatchFileImportModelImpl.ENTITY_CACHE_ENABLED);

		Map<String, String> dbColumnNames = new HashMap<String, String>();

		dbColumnNames.put("uuid", "uuid_");

		setDBColumnNames(dbColumnNames);
	}

	/**
	 * Caches the batch file import in the entity cache if it is enabled.
	 *
	 * @param batchFileImport the batch file import
	 */
	@Override
	public void cacheResult(BatchFileImport batchFileImport) {
		entityCache.putResult(
			BatchFileImportModelImpl.ENTITY_CACHE_ENABLED,
			BatchFileImportImpl.class, batchFileImport.getPrimaryKey(),
			batchFileImport);

		finderCache.putResult(
			_finderPathFetchByBatchJobExecutionId,
			new Object[] {batchFileImport.getBatchJobExecutionId()},
			batchFileImport);

		batchFileImport.resetOriginalValues();
	}

	/**
	 * Caches the batch file imports in the entity cache if it is enabled.
	 *
	 * @param batchFileImports the batch file imports
	 */
	@Override
	public void cacheResult(List<BatchFileImport> batchFileImports) {
		for (BatchFileImport batchFileImport : batchFileImports) {
			if (entityCache.getResult(
					BatchFileImportModelImpl.ENTITY_CACHE_ENABLED,
					BatchFileImportImpl.class,
					batchFileImport.getPrimaryKey()) == null) {

				cacheResult(batchFileImport);
			}
			else {
				batchFileImport.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all batch file imports.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(BatchFileImportImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the batch file import.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(BatchFileImport batchFileImport) {
		entityCache.removeResult(
			BatchFileImportModelImpl.ENTITY_CACHE_ENABLED,
			BatchFileImportImpl.class, batchFileImport.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache(
			(BatchFileImportModelImpl)batchFileImport, true);
	}

	@Override
	public void clearCache(List<BatchFileImport> batchFileImports) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (BatchFileImport batchFileImport : batchFileImports) {
			entityCache.removeResult(
				BatchFileImportModelImpl.ENTITY_CACHE_ENABLED,
				BatchFileImportImpl.class, batchFileImport.getPrimaryKey());

			clearUniqueFindersCache(
				(BatchFileImportModelImpl)batchFileImport, true);
		}
	}

	protected void cacheUniqueFindersCache(
		BatchFileImportModelImpl batchFileImportModelImpl) {

		Object[] args = new Object[] {
			batchFileImportModelImpl.getBatchJobExecutionId()
		};

		finderCache.putResult(
			_finderPathCountByBatchJobExecutionId, args, Long.valueOf(1),
			false);
		finderCache.putResult(
			_finderPathFetchByBatchJobExecutionId, args,
			batchFileImportModelImpl, false);
	}

	protected void clearUniqueFindersCache(
		BatchFileImportModelImpl batchFileImportModelImpl,
		boolean clearCurrent) {

		if (clearCurrent) {
			Object[] args = new Object[] {
				batchFileImportModelImpl.getBatchJobExecutionId()
			};

			finderCache.removeResult(
				_finderPathCountByBatchJobExecutionId, args);
			finderCache.removeResult(
				_finderPathFetchByBatchJobExecutionId, args);
		}

		if ((batchFileImportModelImpl.getColumnBitmask() &
			 _finderPathFetchByBatchJobExecutionId.getColumnBitmask()) != 0) {

			Object[] args = new Object[] {
				batchFileImportModelImpl.getOriginalBatchJobExecutionId()
			};

			finderCache.removeResult(
				_finderPathCountByBatchJobExecutionId, args);
			finderCache.removeResult(
				_finderPathFetchByBatchJobExecutionId, args);
		}
	}

	/**
	 * Creates a new batch file import with the primary key. Does not add the batch file import to the database.
	 *
	 * @param batchFileImportId the primary key for the new batch file import
	 * @return the new batch file import
	 */
	@Override
	public BatchFileImport create(long batchFileImportId) {
		BatchFileImport batchFileImport = new BatchFileImportImpl();

		batchFileImport.setNew(true);
		batchFileImport.setPrimaryKey(batchFileImportId);

		String uuid = PortalUUIDUtil.generate();

		batchFileImport.setUuid(uuid);

		batchFileImport.setCompanyId(companyProvider.getCompanyId());

		return batchFileImport;
	}

	/**
	 * Removes the batch file import with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param batchFileImportId the primary key of the batch file import
	 * @return the batch file import that was removed
	 * @throws NoSuchFileImportException if a batch file import with the primary key could not be found
	 */
	@Override
	public BatchFileImport remove(long batchFileImportId)
		throws NoSuchFileImportException {

		return remove((Serializable)batchFileImportId);
	}

	/**
	 * Removes the batch file import with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the batch file import
	 * @return the batch file import that was removed
	 * @throws NoSuchFileImportException if a batch file import with the primary key could not be found
	 */
	@Override
	public BatchFileImport remove(Serializable primaryKey)
		throws NoSuchFileImportException {

		Session session = null;

		try {
			session = openSession();

			BatchFileImport batchFileImport = (BatchFileImport)session.get(
				BatchFileImportImpl.class, primaryKey);

			if (batchFileImport == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchFileImportException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(batchFileImport);
		}
		catch (NoSuchFileImportException nsee) {
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
	protected BatchFileImport removeImpl(BatchFileImport batchFileImport) {
		Session session = null;

		try {
			session = openSession();

			if (!session.contains(batchFileImport)) {
				batchFileImport = (BatchFileImport)session.get(
					BatchFileImportImpl.class,
					batchFileImport.getPrimaryKeyObj());
			}

			if (batchFileImport != null) {
				session.delete(batchFileImport);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (batchFileImport != null) {
			clearCache(batchFileImport);
		}

		return batchFileImport;
	}

	@Override
	public BatchFileImport updateImpl(BatchFileImport batchFileImport) {
		boolean isNew = batchFileImport.isNew();

		if (!(batchFileImport instanceof BatchFileImportModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(batchFileImport.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					batchFileImport);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in batchFileImport proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom BatchFileImport implementation " +
					batchFileImport.getClass());
		}

		BatchFileImportModelImpl batchFileImportModelImpl =
			(BatchFileImportModelImpl)batchFileImport;

		if (Validator.isNull(batchFileImport.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			batchFileImport.setUuid(uuid);
		}

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Date now = new Date();

		if (isNew && (batchFileImport.getCreateDate() == null)) {
			if (serviceContext == null) {
				batchFileImport.setCreateDate(now);
			}
			else {
				batchFileImport.setCreateDate(
					serviceContext.getCreateDate(now));
			}
		}

		if (!batchFileImportModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				batchFileImport.setModifiedDate(now);
			}
			else {
				batchFileImport.setModifiedDate(
					serviceContext.getModifiedDate(now));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (batchFileImport.isNew()) {
				session.save(batchFileImport);

				batchFileImport.setNew(false);
			}
			else {
				batchFileImport = (BatchFileImport)session.merge(
					batchFileImport);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (!BatchFileImportModelImpl.COLUMN_BITMASK_ENABLED) {
			finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}
		else if (isNew) {
			Object[] args = new Object[] {batchFileImportModelImpl.getUuid()};

			finderCache.removeResult(_finderPathCountByUuid, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByUuid, args);

			args = new Object[] {
				batchFileImportModelImpl.getUuid(),
				batchFileImportModelImpl.getCompanyId()
			};

			finderCache.removeResult(_finderPathCountByUuid_C, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByUuid_C, args);

			args = new Object[] {batchFileImportModelImpl.getStatus()};

			finderCache.removeResult(_finderPathCountByStatus, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByStatus, args);

			finderCache.removeResult(_finderPathCountAll, FINDER_ARGS_EMPTY);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindAll, FINDER_ARGS_EMPTY);
		}
		else {
			if ((batchFileImportModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByUuid.getColumnBitmask()) !=
					 0) {

				Object[] args = new Object[] {
					batchFileImportModelImpl.getOriginalUuid()
				};

				finderCache.removeResult(_finderPathCountByUuid, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByUuid, args);

				args = new Object[] {batchFileImportModelImpl.getUuid()};

				finderCache.removeResult(_finderPathCountByUuid, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByUuid, args);
			}

			if ((batchFileImportModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByUuid_C.getColumnBitmask()) !=
					 0) {

				Object[] args = new Object[] {
					batchFileImportModelImpl.getOriginalUuid(),
					batchFileImportModelImpl.getOriginalCompanyId()
				};

				finderCache.removeResult(_finderPathCountByUuid_C, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByUuid_C, args);

				args = new Object[] {
					batchFileImportModelImpl.getUuid(),
					batchFileImportModelImpl.getCompanyId()
				};

				finderCache.removeResult(_finderPathCountByUuid_C, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByUuid_C, args);
			}

			if ((batchFileImportModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByStatus.getColumnBitmask()) !=
					 0) {

				Object[] args = new Object[] {
					batchFileImportModelImpl.getOriginalStatus()
				};

				finderCache.removeResult(_finderPathCountByStatus, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByStatus, args);

				args = new Object[] {batchFileImportModelImpl.getStatus()};

				finderCache.removeResult(_finderPathCountByStatus, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByStatus, args);
			}
		}

		entityCache.putResult(
			BatchFileImportModelImpl.ENTITY_CACHE_ENABLED,
			BatchFileImportImpl.class, batchFileImport.getPrimaryKey(),
			batchFileImport, false);

		clearUniqueFindersCache(batchFileImportModelImpl, false);
		cacheUniqueFindersCache(batchFileImportModelImpl);

		batchFileImport.resetOriginalValues();

		return batchFileImport;
	}

	/**
	 * Returns the batch file import with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the batch file import
	 * @return the batch file import
	 * @throws NoSuchFileImportException if a batch file import with the primary key could not be found
	 */
	@Override
	public BatchFileImport findByPrimaryKey(Serializable primaryKey)
		throws NoSuchFileImportException {

		BatchFileImport batchFileImport = fetchByPrimaryKey(primaryKey);

		if (batchFileImport == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchFileImportException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return batchFileImport;
	}

	/**
	 * Returns the batch file import with the primary key or throws a <code>NoSuchFileImportException</code> if it could not be found.
	 *
	 * @param batchFileImportId the primary key of the batch file import
	 * @return the batch file import
	 * @throws NoSuchFileImportException if a batch file import with the primary key could not be found
	 */
	@Override
	public BatchFileImport findByPrimaryKey(long batchFileImportId)
		throws NoSuchFileImportException {

		return findByPrimaryKey((Serializable)batchFileImportId);
	}

	/**
	 * Returns the batch file import with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param batchFileImportId the primary key of the batch file import
	 * @return the batch file import, or <code>null</code> if a batch file import with the primary key could not be found
	 */
	@Override
	public BatchFileImport fetchByPrimaryKey(long batchFileImportId) {
		return fetchByPrimaryKey((Serializable)batchFileImportId);
	}

	/**
	 * Returns all the batch file imports.
	 *
	 * @return the batch file imports
	 */
	@Override
	public List<BatchFileImport> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the batch file imports.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>BatchFileImportModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of batch file imports
	 * @param end the upper bound of the range of batch file imports (not inclusive)
	 * @return the range of batch file imports
	 */
	@Override
	public List<BatchFileImport> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the batch file imports.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>BatchFileImportModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of batch file imports
	 * @param end the upper bound of the range of batch file imports (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of batch file imports
	 */
	@Override
	public List<BatchFileImport> findAll(
		int start, int end,
		OrderByComparator<BatchFileImport> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the batch file imports.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>BatchFileImportModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of batch file imports
	 * @param end the upper bound of the range of batch file imports (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of batch file imports
	 */
	@Override
	public List<BatchFileImport> findAll(
		int start, int end,
		OrderByComparator<BatchFileImport> orderByComparator,
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

		List<BatchFileImport> list = null;

		if (retrieveFromCache) {
			list = (List<BatchFileImport>)finderCache.getResult(
				finderPath, finderArgs, this);
		}

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				query.append(_SQL_SELECT_BATCHFILEIMPORT);

				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_BATCHFILEIMPORT;

				if (pagination) {
					sql = sql.concat(BatchFileImportModelImpl.ORDER_BY_JPQL);
				}
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (!pagination) {
					list = (List<BatchFileImport>)QueryUtil.list(
						q, getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<BatchFileImport>)QueryUtil.list(
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
	 * Removes all the batch file imports from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (BatchFileImport batchFileImport : findAll()) {
			remove(batchFileImport);
		}
	}

	/**
	 * Returns the number of batch file imports.
	 *
	 * @return the number of batch file imports
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(
			_finderPathCountAll, FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_BATCHFILEIMPORT);

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
		return "batchFileImportId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_BATCHFILEIMPORT;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return BatchFileImportModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the batch file import persistence.
	 */
	public void afterPropertiesSet() {
		_finderPathWithPaginationFindAll = new FinderPath(
			BatchFileImportModelImpl.ENTITY_CACHE_ENABLED,
			BatchFileImportModelImpl.FINDER_CACHE_ENABLED,
			BatchFileImportImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findAll", new String[0]);

		_finderPathWithoutPaginationFindAll = new FinderPath(
			BatchFileImportModelImpl.ENTITY_CACHE_ENABLED,
			BatchFileImportModelImpl.FINDER_CACHE_ENABLED,
			BatchFileImportImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll",
			new String[0]);

		_finderPathCountAll = new FinderPath(
			BatchFileImportModelImpl.ENTITY_CACHE_ENABLED,
			BatchFileImportModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll",
			new String[0]);

		_finderPathWithPaginationFindByUuid = new FinderPath(
			BatchFileImportModelImpl.ENTITY_CACHE_ENABLED,
			BatchFileImportModelImpl.FINDER_CACHE_ENABLED,
			BatchFileImportImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByUuid",
			new String[] {
				String.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByUuid = new FinderPath(
			BatchFileImportModelImpl.ENTITY_CACHE_ENABLED,
			BatchFileImportModelImpl.FINDER_CACHE_ENABLED,
			BatchFileImportImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid",
			new String[] {String.class.getName()},
			BatchFileImportModelImpl.UUID_COLUMN_BITMASK);

		_finderPathCountByUuid = new FinderPath(
			BatchFileImportModelImpl.ENTITY_CACHE_ENABLED,
			BatchFileImportModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUuid",
			new String[] {String.class.getName()});

		_finderPathWithPaginationFindByUuid_C = new FinderPath(
			BatchFileImportModelImpl.ENTITY_CACHE_ENABLED,
			BatchFileImportModelImpl.FINDER_CACHE_ENABLED,
			BatchFileImportImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByUuid_C",
			new String[] {
				String.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByUuid_C = new FinderPath(
			BatchFileImportModelImpl.ENTITY_CACHE_ENABLED,
			BatchFileImportModelImpl.FINDER_CACHE_ENABLED,
			BatchFileImportImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid_C",
			new String[] {String.class.getName(), Long.class.getName()},
			BatchFileImportModelImpl.UUID_COLUMN_BITMASK |
			BatchFileImportModelImpl.COMPANYID_COLUMN_BITMASK);

		_finderPathCountByUuid_C = new FinderPath(
			BatchFileImportModelImpl.ENTITY_CACHE_ENABLED,
			BatchFileImportModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUuid_C",
			new String[] {String.class.getName(), Long.class.getName()});

		_finderPathFetchByBatchJobExecutionId = new FinderPath(
			BatchFileImportModelImpl.ENTITY_CACHE_ENABLED,
			BatchFileImportModelImpl.FINDER_CACHE_ENABLED,
			BatchFileImportImpl.class, FINDER_CLASS_NAME_ENTITY,
			"fetchByBatchJobExecutionId", new String[] {Long.class.getName()},
			BatchFileImportModelImpl.BATCHJOBEXECUTIONID_COLUMN_BITMASK);

		_finderPathCountByBatchJobExecutionId = new FinderPath(
			BatchFileImportModelImpl.ENTITY_CACHE_ENABLED,
			BatchFileImportModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByBatchJobExecutionId", new String[] {Long.class.getName()});

		_finderPathWithPaginationFindByStatus = new FinderPath(
			BatchFileImportModelImpl.ENTITY_CACHE_ENABLED,
			BatchFileImportModelImpl.FINDER_CACHE_ENABLED,
			BatchFileImportImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByStatus",
			new String[] {
				String.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByStatus = new FinderPath(
			BatchFileImportModelImpl.ENTITY_CACHE_ENABLED,
			BatchFileImportModelImpl.FINDER_CACHE_ENABLED,
			BatchFileImportImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByStatus",
			new String[] {String.class.getName()},
			BatchFileImportModelImpl.STATUS_COLUMN_BITMASK);

		_finderPathCountByStatus = new FinderPath(
			BatchFileImportModelImpl.ENTITY_CACHE_ENABLED,
			BatchFileImportModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByStatus",
			new String[] {String.class.getName()});
	}

	public void destroy() {
		entityCache.removeCache(BatchFileImportImpl.class.getName());
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

	private static final String _SQL_SELECT_BATCHFILEIMPORT =
		"SELECT batchFileImport FROM BatchFileImport batchFileImport";

	private static final String _SQL_SELECT_BATCHFILEIMPORT_WHERE =
		"SELECT batchFileImport FROM BatchFileImport batchFileImport WHERE ";

	private static final String _SQL_COUNT_BATCHFILEIMPORT =
		"SELECT COUNT(batchFileImport) FROM BatchFileImport batchFileImport";

	private static final String _SQL_COUNT_BATCHFILEIMPORT_WHERE =
		"SELECT COUNT(batchFileImport) FROM BatchFileImport batchFileImport WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS = "batchFileImport.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No BatchFileImport exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No BatchFileImport exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		BatchFileImportPersistenceImpl.class);

	private static final Set<String> _badColumnNames = SetUtil.fromArray(
		new String[] {"uuid"});

}