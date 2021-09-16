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

package com.liferay.batch.planner.service.persistence.impl;

import com.liferay.batch.planner.exception.NoSuchMappingException;
import com.liferay.batch.planner.model.BatchPlannerMapping;
import com.liferay.batch.planner.model.BatchPlannerMappingTable;
import com.liferay.batch.planner.model.impl.BatchPlannerMappingImpl;
import com.liferay.batch.planner.model.impl.BatchPlannerMappingModelImpl;
import com.liferay.batch.planner.service.persistence.BatchPlannerMappingPersistence;
import com.liferay.batch.planner.service.persistence.impl.constants.BatchPlannerPersistenceConstants;
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
 * The persistence implementation for the batch planner mapping service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Igor Beslic
 * @generated
 */
@Component(
	service = {BatchPlannerMappingPersistence.class, BasePersistence.class}
)
public class BatchPlannerMappingPersistenceImpl
	extends BasePersistenceImpl<BatchPlannerMapping>
	implements BatchPlannerMappingPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>BatchPlannerMappingUtil</code> to access the batch planner mapping persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		BatchPlannerMappingImpl.class.getName();

	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List1";

	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List2";

	private FinderPath _finderPathWithPaginationFindAll;
	private FinderPath _finderPathWithoutPaginationFindAll;
	private FinderPath _finderPathCountAll;
	private FinderPath _finderPathWithPaginationFindByBatchPlannerPlanId;
	private FinderPath _finderPathWithoutPaginationFindByBatchPlannerPlanId;
	private FinderPath _finderPathCountByBatchPlannerPlanId;

	/**
	 * Returns all the batch planner mappings where batchPlannerPlanId = &#63;.
	 *
	 * @param batchPlannerPlanId the batch planner plan ID
	 * @return the matching batch planner mappings
	 */
	@Override
	public List<BatchPlannerMapping> findByBatchPlannerPlanId(
		long batchPlannerPlanId) {

		return findByBatchPlannerPlanId(
			batchPlannerPlanId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the batch planner mappings where batchPlannerPlanId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BatchPlannerMappingModelImpl</code>.
	 * </p>
	 *
	 * @param batchPlannerPlanId the batch planner plan ID
	 * @param start the lower bound of the range of batch planner mappings
	 * @param end the upper bound of the range of batch planner mappings (not inclusive)
	 * @return the range of matching batch planner mappings
	 */
	@Override
	public List<BatchPlannerMapping> findByBatchPlannerPlanId(
		long batchPlannerPlanId, int start, int end) {

		return findByBatchPlannerPlanId(batchPlannerPlanId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the batch planner mappings where batchPlannerPlanId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BatchPlannerMappingModelImpl</code>.
	 * </p>
	 *
	 * @param batchPlannerPlanId the batch planner plan ID
	 * @param start the lower bound of the range of batch planner mappings
	 * @param end the upper bound of the range of batch planner mappings (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching batch planner mappings
	 */
	@Override
	public List<BatchPlannerMapping> findByBatchPlannerPlanId(
		long batchPlannerPlanId, int start, int end,
		OrderByComparator<BatchPlannerMapping> orderByComparator) {

		return findByBatchPlannerPlanId(
			batchPlannerPlanId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the batch planner mappings where batchPlannerPlanId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BatchPlannerMappingModelImpl</code>.
	 * </p>
	 *
	 * @param batchPlannerPlanId the batch planner plan ID
	 * @param start the lower bound of the range of batch planner mappings
	 * @param end the upper bound of the range of batch planner mappings (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching batch planner mappings
	 */
	@Override
	public List<BatchPlannerMapping> findByBatchPlannerPlanId(
		long batchPlannerPlanId, int start, int end,
		OrderByComparator<BatchPlannerMapping> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath =
					_finderPathWithoutPaginationFindByBatchPlannerPlanId;
				finderArgs = new Object[] {batchPlannerPlanId};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByBatchPlannerPlanId;
			finderArgs = new Object[] {
				batchPlannerPlanId, start, end, orderByComparator
			};
		}

		List<BatchPlannerMapping> list = null;

		if (useFinderCache) {
			list = (List<BatchPlannerMapping>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (BatchPlannerMapping batchPlannerMapping : list) {
					if (batchPlannerPlanId !=
							batchPlannerMapping.getBatchPlannerPlanId()) {

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

			sb.append(_SQL_SELECT_BATCHPLANNERMAPPING_WHERE);

			sb.append(_FINDER_COLUMN_BATCHPLANNERPLANID_BATCHPLANNERPLANID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(BatchPlannerMappingModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(batchPlannerPlanId);

				list = (List<BatchPlannerMapping>)QueryUtil.list(
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
	 * Returns the first batch planner mapping in the ordered set where batchPlannerPlanId = &#63;.
	 *
	 * @param batchPlannerPlanId the batch planner plan ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching batch planner mapping
	 * @throws NoSuchMappingException if a matching batch planner mapping could not be found
	 */
	@Override
	public BatchPlannerMapping findByBatchPlannerPlanId_First(
			long batchPlannerPlanId,
			OrderByComparator<BatchPlannerMapping> orderByComparator)
		throws NoSuchMappingException {

		BatchPlannerMapping batchPlannerMapping =
			fetchByBatchPlannerPlanId_First(
				batchPlannerPlanId, orderByComparator);

		if (batchPlannerMapping != null) {
			return batchPlannerMapping;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("batchPlannerPlanId=");
		sb.append(batchPlannerPlanId);

		sb.append("}");

		throw new NoSuchMappingException(sb.toString());
	}

	/**
	 * Returns the first batch planner mapping in the ordered set where batchPlannerPlanId = &#63;.
	 *
	 * @param batchPlannerPlanId the batch planner plan ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching batch planner mapping, or <code>null</code> if a matching batch planner mapping could not be found
	 */
	@Override
	public BatchPlannerMapping fetchByBatchPlannerPlanId_First(
		long batchPlannerPlanId,
		OrderByComparator<BatchPlannerMapping> orderByComparator) {

		List<BatchPlannerMapping> list = findByBatchPlannerPlanId(
			batchPlannerPlanId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last batch planner mapping in the ordered set where batchPlannerPlanId = &#63;.
	 *
	 * @param batchPlannerPlanId the batch planner plan ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching batch planner mapping
	 * @throws NoSuchMappingException if a matching batch planner mapping could not be found
	 */
	@Override
	public BatchPlannerMapping findByBatchPlannerPlanId_Last(
			long batchPlannerPlanId,
			OrderByComparator<BatchPlannerMapping> orderByComparator)
		throws NoSuchMappingException {

		BatchPlannerMapping batchPlannerMapping =
			fetchByBatchPlannerPlanId_Last(
				batchPlannerPlanId, orderByComparator);

		if (batchPlannerMapping != null) {
			return batchPlannerMapping;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("batchPlannerPlanId=");
		sb.append(batchPlannerPlanId);

		sb.append("}");

		throw new NoSuchMappingException(sb.toString());
	}

	/**
	 * Returns the last batch planner mapping in the ordered set where batchPlannerPlanId = &#63;.
	 *
	 * @param batchPlannerPlanId the batch planner plan ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching batch planner mapping, or <code>null</code> if a matching batch planner mapping could not be found
	 */
	@Override
	public BatchPlannerMapping fetchByBatchPlannerPlanId_Last(
		long batchPlannerPlanId,
		OrderByComparator<BatchPlannerMapping> orderByComparator) {

		int count = countByBatchPlannerPlanId(batchPlannerPlanId);

		if (count == 0) {
			return null;
		}

		List<BatchPlannerMapping> list = findByBatchPlannerPlanId(
			batchPlannerPlanId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the batch planner mappings before and after the current batch planner mapping in the ordered set where batchPlannerPlanId = &#63;.
	 *
	 * @param batchPlannerMappingId the primary key of the current batch planner mapping
	 * @param batchPlannerPlanId the batch planner plan ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next batch planner mapping
	 * @throws NoSuchMappingException if a batch planner mapping with the primary key could not be found
	 */
	@Override
	public BatchPlannerMapping[] findByBatchPlannerPlanId_PrevAndNext(
			long batchPlannerMappingId, long batchPlannerPlanId,
			OrderByComparator<BatchPlannerMapping> orderByComparator)
		throws NoSuchMappingException {

		BatchPlannerMapping batchPlannerMapping = findByPrimaryKey(
			batchPlannerMappingId);

		Session session = null;

		try {
			session = openSession();

			BatchPlannerMapping[] array = new BatchPlannerMappingImpl[3];

			array[0] = getByBatchPlannerPlanId_PrevAndNext(
				session, batchPlannerMapping, batchPlannerPlanId,
				orderByComparator, true);

			array[1] = batchPlannerMapping;

			array[2] = getByBatchPlannerPlanId_PrevAndNext(
				session, batchPlannerMapping, batchPlannerPlanId,
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

	protected BatchPlannerMapping getByBatchPlannerPlanId_PrevAndNext(
		Session session, BatchPlannerMapping batchPlannerMapping,
		long batchPlannerPlanId,
		OrderByComparator<BatchPlannerMapping> orderByComparator,
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

		sb.append(_SQL_SELECT_BATCHPLANNERMAPPING_WHERE);

		sb.append(_FINDER_COLUMN_BATCHPLANNERPLANID_BATCHPLANNERPLANID_2);

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
			sb.append(BatchPlannerMappingModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(batchPlannerPlanId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						batchPlannerMapping)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<BatchPlannerMapping> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the batch planner mappings where batchPlannerPlanId = &#63; from the database.
	 *
	 * @param batchPlannerPlanId the batch planner plan ID
	 */
	@Override
	public void removeByBatchPlannerPlanId(long batchPlannerPlanId) {
		for (BatchPlannerMapping batchPlannerMapping :
				findByBatchPlannerPlanId(
					batchPlannerPlanId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(batchPlannerMapping);
		}
	}

	/**
	 * Returns the number of batch planner mappings where batchPlannerPlanId = &#63;.
	 *
	 * @param batchPlannerPlanId the batch planner plan ID
	 * @return the number of matching batch planner mappings
	 */
	@Override
	public int countByBatchPlannerPlanId(long batchPlannerPlanId) {
		FinderPath finderPath = _finderPathCountByBatchPlannerPlanId;

		Object[] finderArgs = new Object[] {batchPlannerPlanId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_BATCHPLANNERMAPPING_WHERE);

			sb.append(_FINDER_COLUMN_BATCHPLANNERPLANID_BATCHPLANNERPLANID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(batchPlannerPlanId);

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
		_FINDER_COLUMN_BATCHPLANNERPLANID_BATCHPLANNERPLANID_2 =
			"batchPlannerMapping.batchPlannerPlanId = ?";

	private FinderPath _finderPathFetchByBPPI_EFN_IFN;
	private FinderPath _finderPathCountByBPPI_EFN_IFN;

	/**
	 * Returns the batch planner mapping where batchPlannerPlanId = &#63; and externalFieldName = &#63; and internalFieldName = &#63; or throws a <code>NoSuchMappingException</code> if it could not be found.
	 *
	 * @param batchPlannerPlanId the batch planner plan ID
	 * @param externalFieldName the external field name
	 * @param internalFieldName the internal field name
	 * @return the matching batch planner mapping
	 * @throws NoSuchMappingException if a matching batch planner mapping could not be found
	 */
	@Override
	public BatchPlannerMapping findByBPPI_EFN_IFN(
			long batchPlannerPlanId, String externalFieldName,
			String internalFieldName)
		throws NoSuchMappingException {

		BatchPlannerMapping batchPlannerMapping = fetchByBPPI_EFN_IFN(
			batchPlannerPlanId, externalFieldName, internalFieldName);

		if (batchPlannerMapping == null) {
			StringBundler sb = new StringBundler(8);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("batchPlannerPlanId=");
			sb.append(batchPlannerPlanId);

			sb.append(", externalFieldName=");
			sb.append(externalFieldName);

			sb.append(", internalFieldName=");
			sb.append(internalFieldName);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchMappingException(sb.toString());
		}

		return batchPlannerMapping;
	}

	/**
	 * Returns the batch planner mapping where batchPlannerPlanId = &#63; and externalFieldName = &#63; and internalFieldName = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param batchPlannerPlanId the batch planner plan ID
	 * @param externalFieldName the external field name
	 * @param internalFieldName the internal field name
	 * @return the matching batch planner mapping, or <code>null</code> if a matching batch planner mapping could not be found
	 */
	@Override
	public BatchPlannerMapping fetchByBPPI_EFN_IFN(
		long batchPlannerPlanId, String externalFieldName,
		String internalFieldName) {

		return fetchByBPPI_EFN_IFN(
			batchPlannerPlanId, externalFieldName, internalFieldName, true);
	}

	/**
	 * Returns the batch planner mapping where batchPlannerPlanId = &#63; and externalFieldName = &#63; and internalFieldName = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param batchPlannerPlanId the batch planner plan ID
	 * @param externalFieldName the external field name
	 * @param internalFieldName the internal field name
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching batch planner mapping, or <code>null</code> if a matching batch planner mapping could not be found
	 */
	@Override
	public BatchPlannerMapping fetchByBPPI_EFN_IFN(
		long batchPlannerPlanId, String externalFieldName,
		String internalFieldName, boolean useFinderCache) {

		externalFieldName = Objects.toString(externalFieldName, "");
		internalFieldName = Objects.toString(internalFieldName, "");

		Object[] finderArgs = null;

		if (useFinderCache) {
			finderArgs = new Object[] {
				batchPlannerPlanId, externalFieldName, internalFieldName
			};
		}

		Object result = null;

		if (useFinderCache) {
			result = finderCache.getResult(
				_finderPathFetchByBPPI_EFN_IFN, finderArgs);
		}

		if (result instanceof BatchPlannerMapping) {
			BatchPlannerMapping batchPlannerMapping =
				(BatchPlannerMapping)result;

			if ((batchPlannerPlanId !=
					batchPlannerMapping.getBatchPlannerPlanId()) ||
				!Objects.equals(
					externalFieldName,
					batchPlannerMapping.getExternalFieldName()) ||
				!Objects.equals(
					internalFieldName,
					batchPlannerMapping.getInternalFieldName())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(5);

			sb.append(_SQL_SELECT_BATCHPLANNERMAPPING_WHERE);

			sb.append(_FINDER_COLUMN_BPPI_EFN_IFN_BATCHPLANNERPLANID_2);

			boolean bindExternalFieldName = false;

			if (externalFieldName.isEmpty()) {
				sb.append(_FINDER_COLUMN_BPPI_EFN_IFN_EXTERNALFIELDNAME_3);
			}
			else {
				bindExternalFieldName = true;

				sb.append(_FINDER_COLUMN_BPPI_EFN_IFN_EXTERNALFIELDNAME_2);
			}

			boolean bindInternalFieldName = false;

			if (internalFieldName.isEmpty()) {
				sb.append(_FINDER_COLUMN_BPPI_EFN_IFN_INTERNALFIELDNAME_3);
			}
			else {
				bindInternalFieldName = true;

				sb.append(_FINDER_COLUMN_BPPI_EFN_IFN_INTERNALFIELDNAME_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(batchPlannerPlanId);

				if (bindExternalFieldName) {
					queryPos.add(externalFieldName);
				}

				if (bindInternalFieldName) {
					queryPos.add(internalFieldName);
				}

				List<BatchPlannerMapping> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache) {
						finderCache.putResult(
							_finderPathFetchByBPPI_EFN_IFN, finderArgs, list);
					}
				}
				else {
					BatchPlannerMapping batchPlannerMapping = list.get(0);

					result = batchPlannerMapping;

					cacheResult(batchPlannerMapping);
				}
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		if (result instanceof List<?>) {
			return null;
		}
		else {
			return (BatchPlannerMapping)result;
		}
	}

	/**
	 * Removes the batch planner mapping where batchPlannerPlanId = &#63; and externalFieldName = &#63; and internalFieldName = &#63; from the database.
	 *
	 * @param batchPlannerPlanId the batch planner plan ID
	 * @param externalFieldName the external field name
	 * @param internalFieldName the internal field name
	 * @return the batch planner mapping that was removed
	 */
	@Override
	public BatchPlannerMapping removeByBPPI_EFN_IFN(
			long batchPlannerPlanId, String externalFieldName,
			String internalFieldName)
		throws NoSuchMappingException {

		BatchPlannerMapping batchPlannerMapping = findByBPPI_EFN_IFN(
			batchPlannerPlanId, externalFieldName, internalFieldName);

		return remove(batchPlannerMapping);
	}

	/**
	 * Returns the number of batch planner mappings where batchPlannerPlanId = &#63; and externalFieldName = &#63; and internalFieldName = &#63;.
	 *
	 * @param batchPlannerPlanId the batch planner plan ID
	 * @param externalFieldName the external field name
	 * @param internalFieldName the internal field name
	 * @return the number of matching batch planner mappings
	 */
	@Override
	public int countByBPPI_EFN_IFN(
		long batchPlannerPlanId, String externalFieldName,
		String internalFieldName) {

		externalFieldName = Objects.toString(externalFieldName, "");
		internalFieldName = Objects.toString(internalFieldName, "");

		FinderPath finderPath = _finderPathCountByBPPI_EFN_IFN;

		Object[] finderArgs = new Object[] {
			batchPlannerPlanId, externalFieldName, internalFieldName
		};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_COUNT_BATCHPLANNERMAPPING_WHERE);

			sb.append(_FINDER_COLUMN_BPPI_EFN_IFN_BATCHPLANNERPLANID_2);

			boolean bindExternalFieldName = false;

			if (externalFieldName.isEmpty()) {
				sb.append(_FINDER_COLUMN_BPPI_EFN_IFN_EXTERNALFIELDNAME_3);
			}
			else {
				bindExternalFieldName = true;

				sb.append(_FINDER_COLUMN_BPPI_EFN_IFN_EXTERNALFIELDNAME_2);
			}

			boolean bindInternalFieldName = false;

			if (internalFieldName.isEmpty()) {
				sb.append(_FINDER_COLUMN_BPPI_EFN_IFN_INTERNALFIELDNAME_3);
			}
			else {
				bindInternalFieldName = true;

				sb.append(_FINDER_COLUMN_BPPI_EFN_IFN_INTERNALFIELDNAME_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(batchPlannerPlanId);

				if (bindExternalFieldName) {
					queryPos.add(externalFieldName);
				}

				if (bindInternalFieldName) {
					queryPos.add(internalFieldName);
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

	private static final String
		_FINDER_COLUMN_BPPI_EFN_IFN_BATCHPLANNERPLANID_2 =
			"batchPlannerMapping.batchPlannerPlanId = ? AND ";

	private static final String
		_FINDER_COLUMN_BPPI_EFN_IFN_EXTERNALFIELDNAME_2 =
			"batchPlannerMapping.externalFieldName = ? AND ";

	private static final String
		_FINDER_COLUMN_BPPI_EFN_IFN_EXTERNALFIELDNAME_3 =
			"(batchPlannerMapping.externalFieldName IS NULL OR batchPlannerMapping.externalFieldName = '') AND ";

	private static final String
		_FINDER_COLUMN_BPPI_EFN_IFN_INTERNALFIELDNAME_2 =
			"batchPlannerMapping.internalFieldName = ?";

	private static final String
		_FINDER_COLUMN_BPPI_EFN_IFN_INTERNALFIELDNAME_3 =
			"(batchPlannerMapping.internalFieldName IS NULL OR batchPlannerMapping.internalFieldName = '')";

	public BatchPlannerMappingPersistenceImpl() {
		setModelClass(BatchPlannerMapping.class);

		setModelImplClass(BatchPlannerMappingImpl.class);
		setModelPKClass(long.class);

		setTable(BatchPlannerMappingTable.INSTANCE);
	}

	/**
	 * Caches the batch planner mapping in the entity cache if it is enabled.
	 *
	 * @param batchPlannerMapping the batch planner mapping
	 */
	@Override
	public void cacheResult(BatchPlannerMapping batchPlannerMapping) {
		entityCache.putResult(
			BatchPlannerMappingImpl.class, batchPlannerMapping.getPrimaryKey(),
			batchPlannerMapping);

		finderCache.putResult(
			_finderPathFetchByBPPI_EFN_IFN,
			new Object[] {
				batchPlannerMapping.getBatchPlannerPlanId(),
				batchPlannerMapping.getExternalFieldName(),
				batchPlannerMapping.getInternalFieldName()
			},
			batchPlannerMapping);
	}

	private int _valueObjectFinderCacheListThreshold;

	/**
	 * Caches the batch planner mappings in the entity cache if it is enabled.
	 *
	 * @param batchPlannerMappings the batch planner mappings
	 */
	@Override
	public void cacheResult(List<BatchPlannerMapping> batchPlannerMappings) {
		if ((_valueObjectFinderCacheListThreshold == 0) ||
			((_valueObjectFinderCacheListThreshold > 0) &&
			 (batchPlannerMappings.size() >
				 _valueObjectFinderCacheListThreshold))) {

			return;
		}

		for (BatchPlannerMapping batchPlannerMapping : batchPlannerMappings) {
			if (entityCache.getResult(
					BatchPlannerMappingImpl.class,
					batchPlannerMapping.getPrimaryKey()) == null) {

				cacheResult(batchPlannerMapping);
			}
		}
	}

	/**
	 * Clears the cache for all batch planner mappings.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(BatchPlannerMappingImpl.class);

		finderCache.clearCache(BatchPlannerMappingImpl.class);
	}

	/**
	 * Clears the cache for the batch planner mapping.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(BatchPlannerMapping batchPlannerMapping) {
		entityCache.removeResult(
			BatchPlannerMappingImpl.class, batchPlannerMapping);
	}

	@Override
	public void clearCache(List<BatchPlannerMapping> batchPlannerMappings) {
		for (BatchPlannerMapping batchPlannerMapping : batchPlannerMappings) {
			entityCache.removeResult(
				BatchPlannerMappingImpl.class, batchPlannerMapping);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(BatchPlannerMappingImpl.class);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(BatchPlannerMappingImpl.class, primaryKey);
		}
	}

	protected void cacheUniqueFindersCache(
		BatchPlannerMappingModelImpl batchPlannerMappingModelImpl) {

		Object[] args = new Object[] {
			batchPlannerMappingModelImpl.getBatchPlannerPlanId(),
			batchPlannerMappingModelImpl.getExternalFieldName(),
			batchPlannerMappingModelImpl.getInternalFieldName()
		};

		finderCache.putResult(
			_finderPathCountByBPPI_EFN_IFN, args, Long.valueOf(1));
		finderCache.putResult(
			_finderPathFetchByBPPI_EFN_IFN, args, batchPlannerMappingModelImpl);
	}

	/**
	 * Creates a new batch planner mapping with the primary key. Does not add the batch planner mapping to the database.
	 *
	 * @param batchPlannerMappingId the primary key for the new batch planner mapping
	 * @return the new batch planner mapping
	 */
	@Override
	public BatchPlannerMapping create(long batchPlannerMappingId) {
		BatchPlannerMapping batchPlannerMapping = new BatchPlannerMappingImpl();

		batchPlannerMapping.setNew(true);
		batchPlannerMapping.setPrimaryKey(batchPlannerMappingId);

		batchPlannerMapping.setCompanyId(CompanyThreadLocal.getCompanyId());

		return batchPlannerMapping;
	}

	/**
	 * Removes the batch planner mapping with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param batchPlannerMappingId the primary key of the batch planner mapping
	 * @return the batch planner mapping that was removed
	 * @throws NoSuchMappingException if a batch planner mapping with the primary key could not be found
	 */
	@Override
	public BatchPlannerMapping remove(long batchPlannerMappingId)
		throws NoSuchMappingException {

		return remove((Serializable)batchPlannerMappingId);
	}

	/**
	 * Removes the batch planner mapping with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the batch planner mapping
	 * @return the batch planner mapping that was removed
	 * @throws NoSuchMappingException if a batch planner mapping with the primary key could not be found
	 */
	@Override
	public BatchPlannerMapping remove(Serializable primaryKey)
		throws NoSuchMappingException {

		Session session = null;

		try {
			session = openSession();

			BatchPlannerMapping batchPlannerMapping =
				(BatchPlannerMapping)session.get(
					BatchPlannerMappingImpl.class, primaryKey);

			if (batchPlannerMapping == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchMappingException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(batchPlannerMapping);
		}
		catch (NoSuchMappingException noSuchEntityException) {
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
	protected BatchPlannerMapping removeImpl(
		BatchPlannerMapping batchPlannerMapping) {

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(batchPlannerMapping)) {
				batchPlannerMapping = (BatchPlannerMapping)session.get(
					BatchPlannerMappingImpl.class,
					batchPlannerMapping.getPrimaryKeyObj());
			}

			if (batchPlannerMapping != null) {
				session.delete(batchPlannerMapping);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (batchPlannerMapping != null) {
			clearCache(batchPlannerMapping);
		}

		return batchPlannerMapping;
	}

	@Override
	public BatchPlannerMapping updateImpl(
		BatchPlannerMapping batchPlannerMapping) {

		boolean isNew = batchPlannerMapping.isNew();

		if (!(batchPlannerMapping instanceof BatchPlannerMappingModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(batchPlannerMapping.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					batchPlannerMapping);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in batchPlannerMapping proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom BatchPlannerMapping implementation " +
					batchPlannerMapping.getClass());
		}

		BatchPlannerMappingModelImpl batchPlannerMappingModelImpl =
			(BatchPlannerMappingModelImpl)batchPlannerMapping;

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Date date = new Date();

		if (isNew && (batchPlannerMapping.getCreateDate() == null)) {
			if (serviceContext == null) {
				batchPlannerMapping.setCreateDate(date);
			}
			else {
				batchPlannerMapping.setCreateDate(
					serviceContext.getCreateDate(date));
			}
		}

		if (!batchPlannerMappingModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				batchPlannerMapping.setModifiedDate(date);
			}
			else {
				batchPlannerMapping.setModifiedDate(
					serviceContext.getModifiedDate(date));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (isNew) {
				session.save(batchPlannerMapping);
			}
			else {
				batchPlannerMapping = (BatchPlannerMapping)session.merge(
					batchPlannerMapping);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		entityCache.putResult(
			BatchPlannerMappingImpl.class, batchPlannerMappingModelImpl, false,
			true);

		cacheUniqueFindersCache(batchPlannerMappingModelImpl);

		if (isNew) {
			batchPlannerMapping.setNew(false);
		}

		batchPlannerMapping.resetOriginalValues();

		return batchPlannerMapping;
	}

	/**
	 * Returns the batch planner mapping with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the batch planner mapping
	 * @return the batch planner mapping
	 * @throws NoSuchMappingException if a batch planner mapping with the primary key could not be found
	 */
	@Override
	public BatchPlannerMapping findByPrimaryKey(Serializable primaryKey)
		throws NoSuchMappingException {

		BatchPlannerMapping batchPlannerMapping = fetchByPrimaryKey(primaryKey);

		if (batchPlannerMapping == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchMappingException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return batchPlannerMapping;
	}

	/**
	 * Returns the batch planner mapping with the primary key or throws a <code>NoSuchMappingException</code> if it could not be found.
	 *
	 * @param batchPlannerMappingId the primary key of the batch planner mapping
	 * @return the batch planner mapping
	 * @throws NoSuchMappingException if a batch planner mapping with the primary key could not be found
	 */
	@Override
	public BatchPlannerMapping findByPrimaryKey(long batchPlannerMappingId)
		throws NoSuchMappingException {

		return findByPrimaryKey((Serializable)batchPlannerMappingId);
	}

	/**
	 * Returns the batch planner mapping with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param batchPlannerMappingId the primary key of the batch planner mapping
	 * @return the batch planner mapping, or <code>null</code> if a batch planner mapping with the primary key could not be found
	 */
	@Override
	public BatchPlannerMapping fetchByPrimaryKey(long batchPlannerMappingId) {
		return fetchByPrimaryKey((Serializable)batchPlannerMappingId);
	}

	/**
	 * Returns all the batch planner mappings.
	 *
	 * @return the batch planner mappings
	 */
	@Override
	public List<BatchPlannerMapping> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the batch planner mappings.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BatchPlannerMappingModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of batch planner mappings
	 * @param end the upper bound of the range of batch planner mappings (not inclusive)
	 * @return the range of batch planner mappings
	 */
	@Override
	public List<BatchPlannerMapping> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the batch planner mappings.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BatchPlannerMappingModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of batch planner mappings
	 * @param end the upper bound of the range of batch planner mappings (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of batch planner mappings
	 */
	@Override
	public List<BatchPlannerMapping> findAll(
		int start, int end,
		OrderByComparator<BatchPlannerMapping> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the batch planner mappings.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BatchPlannerMappingModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of batch planner mappings
	 * @param end the upper bound of the range of batch planner mappings (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of batch planner mappings
	 */
	@Override
	public List<BatchPlannerMapping> findAll(
		int start, int end,
		OrderByComparator<BatchPlannerMapping> orderByComparator,
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

		List<BatchPlannerMapping> list = null;

		if (useFinderCache) {
			list = (List<BatchPlannerMapping>)finderCache.getResult(
				finderPath, finderArgs);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_BATCHPLANNERMAPPING);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_BATCHPLANNERMAPPING;

				sql = sql.concat(BatchPlannerMappingModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<BatchPlannerMapping>)QueryUtil.list(
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
	 * Removes all the batch planner mappings from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (BatchPlannerMapping batchPlannerMapping : findAll()) {
			remove(batchPlannerMapping);
		}
	}

	/**
	 * Returns the number of batch planner mappings.
	 *
	 * @return the number of batch planner mappings
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(
			_finderPathCountAll, FINDER_ARGS_EMPTY);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(
					_SQL_COUNT_BATCHPLANNERMAPPING);

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
		return "batchPlannerMappingId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_BATCHPLANNERMAPPING;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return BatchPlannerMappingModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the batch planner mapping persistence.
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

		_finderPathWithPaginationFindByBatchPlannerPlanId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByBatchPlannerPlanId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"batchPlannerPlanId"}, true);

		_finderPathWithoutPaginationFindByBatchPlannerPlanId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByBatchPlannerPlanId", new String[] {Long.class.getName()},
			new String[] {"batchPlannerPlanId"}, true);

		_finderPathCountByBatchPlannerPlanId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByBatchPlannerPlanId", new String[] {Long.class.getName()},
			new String[] {"batchPlannerPlanId"}, false);

		_finderPathFetchByBPPI_EFN_IFN = new FinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByBPPI_EFN_IFN",
			new String[] {
				Long.class.getName(), String.class.getName(),
				String.class.getName()
			},
			new String[] {
				"batchPlannerPlanId", "externalFieldName", "internalFieldName"
			},
			true);

		_finderPathCountByBPPI_EFN_IFN = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByBPPI_EFN_IFN",
			new String[] {
				Long.class.getName(), String.class.getName(),
				String.class.getName()
			},
			new String[] {
				"batchPlannerPlanId", "externalFieldName", "internalFieldName"
			},
			false);
	}

	@Deactivate
	public void deactivate() {
		entityCache.removeCache(BatchPlannerMappingImpl.class.getName());
	}

	@Override
	@Reference(
		target = BatchPlannerPersistenceConstants.SERVICE_CONFIGURATION_FILTER,
		unbind = "-"
	)
	public void setConfiguration(Configuration configuration) {
	}

	@Override
	@Reference(
		target = BatchPlannerPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setDataSource(DataSource dataSource) {
		super.setDataSource(dataSource);
	}

	@Override
	@Reference(
		target = BatchPlannerPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setSessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	@Reference
	protected EntityCache entityCache;

	@Reference
	protected FinderCache finderCache;

	private static final String _SQL_SELECT_BATCHPLANNERMAPPING =
		"SELECT batchPlannerMapping FROM BatchPlannerMapping batchPlannerMapping";

	private static final String _SQL_SELECT_BATCHPLANNERMAPPING_WHERE =
		"SELECT batchPlannerMapping FROM BatchPlannerMapping batchPlannerMapping WHERE ";

	private static final String _SQL_COUNT_BATCHPLANNERMAPPING =
		"SELECT COUNT(batchPlannerMapping) FROM BatchPlannerMapping batchPlannerMapping";

	private static final String _SQL_COUNT_BATCHPLANNERMAPPING_WHERE =
		"SELECT COUNT(batchPlannerMapping) FROM BatchPlannerMapping batchPlannerMapping WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS = "batchPlannerMapping.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No BatchPlannerMapping exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No BatchPlannerMapping exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		BatchPlannerMappingPersistenceImpl.class);

	@Override
	protected FinderCache getFinderCache() {
		return finderCache;
	}

	@Reference
	private BatchPlannerMappingModelArgumentsResolver
		_batchPlannerMappingModelArgumentsResolver;

}