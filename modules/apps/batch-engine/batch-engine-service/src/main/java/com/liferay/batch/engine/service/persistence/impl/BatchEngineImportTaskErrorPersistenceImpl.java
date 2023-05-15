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

import com.liferay.batch.engine.exception.NoSuchImportTaskErrorException;
import com.liferay.batch.engine.model.BatchEngineImportTaskError;
import com.liferay.batch.engine.model.BatchEngineImportTaskErrorTable;
import com.liferay.batch.engine.model.impl.BatchEngineImportTaskErrorImpl;
import com.liferay.batch.engine.model.impl.BatchEngineImportTaskErrorModelImpl;
import com.liferay.batch.engine.service.persistence.BatchEngineImportTaskErrorPersistence;
import com.liferay.batch.engine.service.persistence.BatchEngineImportTaskErrorUtil;
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
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.ProxyUtil;

import java.io.Serializable;

import java.lang.reflect.InvocationHandler;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.sql.DataSource;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * The persistence implementation for the batch engine import task error service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Shuyang Zhou
 * @generated
 */
@Component(service = BatchEngineImportTaskErrorPersistence.class)
public class BatchEngineImportTaskErrorPersistenceImpl
	extends BasePersistenceImpl<BatchEngineImportTaskError>
	implements BatchEngineImportTaskErrorPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>BatchEngineImportTaskErrorUtil</code> to access the batch engine import task error persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		BatchEngineImportTaskErrorImpl.class.getName();

	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List1";

	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List2";

	private FinderPath _finderPathWithPaginationFindAll;
	private FinderPath _finderPathWithoutPaginationFindAll;
	private FinderPath _finderPathCountAll;
	private FinderPath _finderPathWithPaginationFindByBatchEngineImportTaskId;
	private FinderPath
		_finderPathWithoutPaginationFindByBatchEngineImportTaskId;
	private FinderPath _finderPathCountByBatchEngineImportTaskId;

	/**
	 * Returns all the batch engine import task errors where batchEngineImportTaskId = &#63;.
	 *
	 * @param batchEngineImportTaskId the batch engine import task ID
	 * @return the matching batch engine import task errors
	 */
	@Override
	public List<BatchEngineImportTaskError> findByBatchEngineImportTaskId(
		long batchEngineImportTaskId) {

		return findByBatchEngineImportTaskId(
			batchEngineImportTaskId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the batch engine import task errors where batchEngineImportTaskId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BatchEngineImportTaskErrorModelImpl</code>.
	 * </p>
	 *
	 * @param batchEngineImportTaskId the batch engine import task ID
	 * @param start the lower bound of the range of batch engine import task errors
	 * @param end the upper bound of the range of batch engine import task errors (not inclusive)
	 * @return the range of matching batch engine import task errors
	 */
	@Override
	public List<BatchEngineImportTaskError> findByBatchEngineImportTaskId(
		long batchEngineImportTaskId, int start, int end) {

		return findByBatchEngineImportTaskId(
			batchEngineImportTaskId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the batch engine import task errors where batchEngineImportTaskId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BatchEngineImportTaskErrorModelImpl</code>.
	 * </p>
	 *
	 * @param batchEngineImportTaskId the batch engine import task ID
	 * @param start the lower bound of the range of batch engine import task errors
	 * @param end the upper bound of the range of batch engine import task errors (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching batch engine import task errors
	 */
	@Override
	public List<BatchEngineImportTaskError> findByBatchEngineImportTaskId(
		long batchEngineImportTaskId, int start, int end,
		OrderByComparator<BatchEngineImportTaskError> orderByComparator) {

		return findByBatchEngineImportTaskId(
			batchEngineImportTaskId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the batch engine import task errors where batchEngineImportTaskId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BatchEngineImportTaskErrorModelImpl</code>.
	 * </p>
	 *
	 * @param batchEngineImportTaskId the batch engine import task ID
	 * @param start the lower bound of the range of batch engine import task errors
	 * @param end the upper bound of the range of batch engine import task errors (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching batch engine import task errors
	 */
	@Override
	public List<BatchEngineImportTaskError> findByBatchEngineImportTaskId(
		long batchEngineImportTaskId, int start, int end,
		OrderByComparator<BatchEngineImportTaskError> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath =
					_finderPathWithoutPaginationFindByBatchEngineImportTaskId;
				finderArgs = new Object[] {batchEngineImportTaskId};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByBatchEngineImportTaskId;
			finderArgs = new Object[] {
				batchEngineImportTaskId, start, end, orderByComparator
			};
		}

		List<BatchEngineImportTaskError> list = null;

		if (useFinderCache) {
			list = (List<BatchEngineImportTaskError>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (BatchEngineImportTaskError batchEngineImportTaskError :
						list) {

					if (batchEngineImportTaskId !=
							batchEngineImportTaskError.
								getBatchEngineImportTaskId()) {

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

			sb.append(_SQL_SELECT_BATCHENGINEIMPORTTASKERROR_WHERE);

			sb.append(
				_FINDER_COLUMN_BATCHENGINEIMPORTTASKID_BATCHENGINEIMPORTTASKID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(BatchEngineImportTaskErrorModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(batchEngineImportTaskId);

				list = (List<BatchEngineImportTaskError>)QueryUtil.list(
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
	 * Returns the first batch engine import task error in the ordered set where batchEngineImportTaskId = &#63;.
	 *
	 * @param batchEngineImportTaskId the batch engine import task ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching batch engine import task error
	 * @throws NoSuchImportTaskErrorException if a matching batch engine import task error could not be found
	 */
	@Override
	public BatchEngineImportTaskError findByBatchEngineImportTaskId_First(
			long batchEngineImportTaskId,
			OrderByComparator<BatchEngineImportTaskError> orderByComparator)
		throws NoSuchImportTaskErrorException {

		BatchEngineImportTaskError batchEngineImportTaskError =
			fetchByBatchEngineImportTaskId_First(
				batchEngineImportTaskId, orderByComparator);

		if (batchEngineImportTaskError != null) {
			return batchEngineImportTaskError;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("batchEngineImportTaskId=");
		sb.append(batchEngineImportTaskId);

		sb.append("}");

		throw new NoSuchImportTaskErrorException(sb.toString());
	}

	/**
	 * Returns the first batch engine import task error in the ordered set where batchEngineImportTaskId = &#63;.
	 *
	 * @param batchEngineImportTaskId the batch engine import task ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching batch engine import task error, or <code>null</code> if a matching batch engine import task error could not be found
	 */
	@Override
	public BatchEngineImportTaskError fetchByBatchEngineImportTaskId_First(
		long batchEngineImportTaskId,
		OrderByComparator<BatchEngineImportTaskError> orderByComparator) {

		List<BatchEngineImportTaskError> list = findByBatchEngineImportTaskId(
			batchEngineImportTaskId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last batch engine import task error in the ordered set where batchEngineImportTaskId = &#63;.
	 *
	 * @param batchEngineImportTaskId the batch engine import task ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching batch engine import task error
	 * @throws NoSuchImportTaskErrorException if a matching batch engine import task error could not be found
	 */
	@Override
	public BatchEngineImportTaskError findByBatchEngineImportTaskId_Last(
			long batchEngineImportTaskId,
			OrderByComparator<BatchEngineImportTaskError> orderByComparator)
		throws NoSuchImportTaskErrorException {

		BatchEngineImportTaskError batchEngineImportTaskError =
			fetchByBatchEngineImportTaskId_Last(
				batchEngineImportTaskId, orderByComparator);

		if (batchEngineImportTaskError != null) {
			return batchEngineImportTaskError;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("batchEngineImportTaskId=");
		sb.append(batchEngineImportTaskId);

		sb.append("}");

		throw new NoSuchImportTaskErrorException(sb.toString());
	}

	/**
	 * Returns the last batch engine import task error in the ordered set where batchEngineImportTaskId = &#63;.
	 *
	 * @param batchEngineImportTaskId the batch engine import task ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching batch engine import task error, or <code>null</code> if a matching batch engine import task error could not be found
	 */
	@Override
	public BatchEngineImportTaskError fetchByBatchEngineImportTaskId_Last(
		long batchEngineImportTaskId,
		OrderByComparator<BatchEngineImportTaskError> orderByComparator) {

		int count = countByBatchEngineImportTaskId(batchEngineImportTaskId);

		if (count == 0) {
			return null;
		}

		List<BatchEngineImportTaskError> list = findByBatchEngineImportTaskId(
			batchEngineImportTaskId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the batch engine import task errors before and after the current batch engine import task error in the ordered set where batchEngineImportTaskId = &#63;.
	 *
	 * @param batchEngineImportTaskErrorId the primary key of the current batch engine import task error
	 * @param batchEngineImportTaskId the batch engine import task ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next batch engine import task error
	 * @throws NoSuchImportTaskErrorException if a batch engine import task error with the primary key could not be found
	 */
	@Override
	public BatchEngineImportTaskError[]
			findByBatchEngineImportTaskId_PrevAndNext(
				long batchEngineImportTaskErrorId, long batchEngineImportTaskId,
				OrderByComparator<BatchEngineImportTaskError> orderByComparator)
		throws NoSuchImportTaskErrorException {

		BatchEngineImportTaskError batchEngineImportTaskError =
			findByPrimaryKey(batchEngineImportTaskErrorId);

		Session session = null;

		try {
			session = openSession();

			BatchEngineImportTaskError[] array =
				new BatchEngineImportTaskErrorImpl[3];

			array[0] = getByBatchEngineImportTaskId_PrevAndNext(
				session, batchEngineImportTaskError, batchEngineImportTaskId,
				orderByComparator, true);

			array[1] = batchEngineImportTaskError;

			array[2] = getByBatchEngineImportTaskId_PrevAndNext(
				session, batchEngineImportTaskError, batchEngineImportTaskId,
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

	protected BatchEngineImportTaskError
		getByBatchEngineImportTaskId_PrevAndNext(
			Session session,
			BatchEngineImportTaskError batchEngineImportTaskError,
			long batchEngineImportTaskId,
			OrderByComparator<BatchEngineImportTaskError> orderByComparator,
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

		sb.append(_SQL_SELECT_BATCHENGINEIMPORTTASKERROR_WHERE);

		sb.append(
			_FINDER_COLUMN_BATCHENGINEIMPORTTASKID_BATCHENGINEIMPORTTASKID_2);

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
			sb.append(BatchEngineImportTaskErrorModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(batchEngineImportTaskId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						batchEngineImportTaskError)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<BatchEngineImportTaskError> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the batch engine import task errors where batchEngineImportTaskId = &#63; from the database.
	 *
	 * @param batchEngineImportTaskId the batch engine import task ID
	 */
	@Override
	public void removeByBatchEngineImportTaskId(long batchEngineImportTaskId) {
		for (BatchEngineImportTaskError batchEngineImportTaskError :
				findByBatchEngineImportTaskId(
					batchEngineImportTaskId, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			remove(batchEngineImportTaskError);
		}
	}

	/**
	 * Returns the number of batch engine import task errors where batchEngineImportTaskId = &#63;.
	 *
	 * @param batchEngineImportTaskId the batch engine import task ID
	 * @return the number of matching batch engine import task errors
	 */
	@Override
	public int countByBatchEngineImportTaskId(long batchEngineImportTaskId) {
		FinderPath finderPath = _finderPathCountByBatchEngineImportTaskId;

		Object[] finderArgs = new Object[] {batchEngineImportTaskId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_BATCHENGINEIMPORTTASKERROR_WHERE);

			sb.append(
				_FINDER_COLUMN_BATCHENGINEIMPORTTASKID_BATCHENGINEIMPORTTASKID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(batchEngineImportTaskId);

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
		_FINDER_COLUMN_BATCHENGINEIMPORTTASKID_BATCHENGINEIMPORTTASKID_2 =
			"batchEngineImportTaskError.batchEngineImportTaskId = ?";

	public BatchEngineImportTaskErrorPersistenceImpl() {
		setModelClass(BatchEngineImportTaskError.class);

		setModelImplClass(BatchEngineImportTaskErrorImpl.class);
		setModelPKClass(long.class);

		setTable(BatchEngineImportTaskErrorTable.INSTANCE);
	}

	/**
	 * Caches the batch engine import task error in the entity cache if it is enabled.
	 *
	 * @param batchEngineImportTaskError the batch engine import task error
	 */
	@Override
	public void cacheResult(
		BatchEngineImportTaskError batchEngineImportTaskError) {

		entityCache.putResult(
			BatchEngineImportTaskErrorImpl.class,
			batchEngineImportTaskError.getPrimaryKey(),
			batchEngineImportTaskError);
	}

	private int _valueObjectFinderCacheListThreshold;

	/**
	 * Caches the batch engine import task errors in the entity cache if it is enabled.
	 *
	 * @param batchEngineImportTaskErrors the batch engine import task errors
	 */
	@Override
	public void cacheResult(
		List<BatchEngineImportTaskError> batchEngineImportTaskErrors) {

		if ((_valueObjectFinderCacheListThreshold == 0) ||
			((_valueObjectFinderCacheListThreshold > 0) &&
			 (batchEngineImportTaskErrors.size() >
				 _valueObjectFinderCacheListThreshold))) {

			return;
		}

		for (BatchEngineImportTaskError batchEngineImportTaskError :
				batchEngineImportTaskErrors) {

			if (entityCache.getResult(
					BatchEngineImportTaskErrorImpl.class,
					batchEngineImportTaskError.getPrimaryKey()) == null) {

				cacheResult(batchEngineImportTaskError);
			}
		}
	}

	/**
	 * Clears the cache for all batch engine import task errors.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(BatchEngineImportTaskErrorImpl.class);

		finderCache.clearCache(BatchEngineImportTaskErrorImpl.class);
	}

	/**
	 * Clears the cache for the batch engine import task error.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(
		BatchEngineImportTaskError batchEngineImportTaskError) {

		entityCache.removeResult(
			BatchEngineImportTaskErrorImpl.class, batchEngineImportTaskError);
	}

	@Override
	public void clearCache(
		List<BatchEngineImportTaskError> batchEngineImportTaskErrors) {

		for (BatchEngineImportTaskError batchEngineImportTaskError :
				batchEngineImportTaskErrors) {

			entityCache.removeResult(
				BatchEngineImportTaskErrorImpl.class,
				batchEngineImportTaskError);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(BatchEngineImportTaskErrorImpl.class);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(
				BatchEngineImportTaskErrorImpl.class, primaryKey);
		}
	}

	/**
	 * Creates a new batch engine import task error with the primary key. Does not add the batch engine import task error to the database.
	 *
	 * @param batchEngineImportTaskErrorId the primary key for the new batch engine import task error
	 * @return the new batch engine import task error
	 */
	@Override
	public BatchEngineImportTaskError create(
		long batchEngineImportTaskErrorId) {

		BatchEngineImportTaskError batchEngineImportTaskError =
			new BatchEngineImportTaskErrorImpl();

		batchEngineImportTaskError.setNew(true);
		batchEngineImportTaskError.setPrimaryKey(batchEngineImportTaskErrorId);

		batchEngineImportTaskError.setCompanyId(
			CompanyThreadLocal.getCompanyId());

		return batchEngineImportTaskError;
	}

	/**
	 * Removes the batch engine import task error with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param batchEngineImportTaskErrorId the primary key of the batch engine import task error
	 * @return the batch engine import task error that was removed
	 * @throws NoSuchImportTaskErrorException if a batch engine import task error with the primary key could not be found
	 */
	@Override
	public BatchEngineImportTaskError remove(long batchEngineImportTaskErrorId)
		throws NoSuchImportTaskErrorException {

		return remove((Serializable)batchEngineImportTaskErrorId);
	}

	/**
	 * Removes the batch engine import task error with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the batch engine import task error
	 * @return the batch engine import task error that was removed
	 * @throws NoSuchImportTaskErrorException if a batch engine import task error with the primary key could not be found
	 */
	@Override
	public BatchEngineImportTaskError remove(Serializable primaryKey)
		throws NoSuchImportTaskErrorException {

		Session session = null;

		try {
			session = openSession();

			BatchEngineImportTaskError batchEngineImportTaskError =
				(BatchEngineImportTaskError)session.get(
					BatchEngineImportTaskErrorImpl.class, primaryKey);

			if (batchEngineImportTaskError == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchImportTaskErrorException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(batchEngineImportTaskError);
		}
		catch (NoSuchImportTaskErrorException noSuchEntityException) {
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
	protected BatchEngineImportTaskError removeImpl(
		BatchEngineImportTaskError batchEngineImportTaskError) {

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(batchEngineImportTaskError)) {
				batchEngineImportTaskError =
					(BatchEngineImportTaskError)session.get(
						BatchEngineImportTaskErrorImpl.class,
						batchEngineImportTaskError.getPrimaryKeyObj());
			}

			if (batchEngineImportTaskError != null) {
				session.delete(batchEngineImportTaskError);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (batchEngineImportTaskError != null) {
			clearCache(batchEngineImportTaskError);
		}

		return batchEngineImportTaskError;
	}

	@Override
	public BatchEngineImportTaskError updateImpl(
		BatchEngineImportTaskError batchEngineImportTaskError) {

		boolean isNew = batchEngineImportTaskError.isNew();

		if (!(batchEngineImportTaskError instanceof
				BatchEngineImportTaskErrorModelImpl)) {

			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(batchEngineImportTaskError.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					batchEngineImportTaskError);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in batchEngineImportTaskError proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom BatchEngineImportTaskError implementation " +
					batchEngineImportTaskError.getClass());
		}

		BatchEngineImportTaskErrorModelImpl
			batchEngineImportTaskErrorModelImpl =
				(BatchEngineImportTaskErrorModelImpl)batchEngineImportTaskError;

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Date date = new Date();

		if (isNew && (batchEngineImportTaskError.getCreateDate() == null)) {
			if (serviceContext == null) {
				batchEngineImportTaskError.setCreateDate(date);
			}
			else {
				batchEngineImportTaskError.setCreateDate(
					serviceContext.getCreateDate(date));
			}
		}

		if (!batchEngineImportTaskErrorModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				batchEngineImportTaskError.setModifiedDate(date);
			}
			else {
				batchEngineImportTaskError.setModifiedDate(
					serviceContext.getModifiedDate(date));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (isNew) {
				session.save(batchEngineImportTaskError);
			}
			else {
				batchEngineImportTaskError =
					(BatchEngineImportTaskError)session.merge(
						batchEngineImportTaskError);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		entityCache.putResult(
			BatchEngineImportTaskErrorImpl.class,
			batchEngineImportTaskErrorModelImpl, false, true);

		if (isNew) {
			batchEngineImportTaskError.setNew(false);
		}

		batchEngineImportTaskError.resetOriginalValues();

		return batchEngineImportTaskError;
	}

	/**
	 * Returns the batch engine import task error with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the batch engine import task error
	 * @return the batch engine import task error
	 * @throws NoSuchImportTaskErrorException if a batch engine import task error with the primary key could not be found
	 */
	@Override
	public BatchEngineImportTaskError findByPrimaryKey(Serializable primaryKey)
		throws NoSuchImportTaskErrorException {

		BatchEngineImportTaskError batchEngineImportTaskError =
			fetchByPrimaryKey(primaryKey);

		if (batchEngineImportTaskError == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchImportTaskErrorException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return batchEngineImportTaskError;
	}

	/**
	 * Returns the batch engine import task error with the primary key or throws a <code>NoSuchImportTaskErrorException</code> if it could not be found.
	 *
	 * @param batchEngineImportTaskErrorId the primary key of the batch engine import task error
	 * @return the batch engine import task error
	 * @throws NoSuchImportTaskErrorException if a batch engine import task error with the primary key could not be found
	 */
	@Override
	public BatchEngineImportTaskError findByPrimaryKey(
			long batchEngineImportTaskErrorId)
		throws NoSuchImportTaskErrorException {

		return findByPrimaryKey((Serializable)batchEngineImportTaskErrorId);
	}

	/**
	 * Returns the batch engine import task error with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param batchEngineImportTaskErrorId the primary key of the batch engine import task error
	 * @return the batch engine import task error, or <code>null</code> if a batch engine import task error with the primary key could not be found
	 */
	@Override
	public BatchEngineImportTaskError fetchByPrimaryKey(
		long batchEngineImportTaskErrorId) {

		return fetchByPrimaryKey((Serializable)batchEngineImportTaskErrorId);
	}

	/**
	 * Returns all the batch engine import task errors.
	 *
	 * @return the batch engine import task errors
	 */
	@Override
	public List<BatchEngineImportTaskError> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the batch engine import task errors.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BatchEngineImportTaskErrorModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of batch engine import task errors
	 * @param end the upper bound of the range of batch engine import task errors (not inclusive)
	 * @return the range of batch engine import task errors
	 */
	@Override
	public List<BatchEngineImportTaskError> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the batch engine import task errors.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BatchEngineImportTaskErrorModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of batch engine import task errors
	 * @param end the upper bound of the range of batch engine import task errors (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of batch engine import task errors
	 */
	@Override
	public List<BatchEngineImportTaskError> findAll(
		int start, int end,
		OrderByComparator<BatchEngineImportTaskError> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the batch engine import task errors.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>BatchEngineImportTaskErrorModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of batch engine import task errors
	 * @param end the upper bound of the range of batch engine import task errors (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of batch engine import task errors
	 */
	@Override
	public List<BatchEngineImportTaskError> findAll(
		int start, int end,
		OrderByComparator<BatchEngineImportTaskError> orderByComparator,
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

		List<BatchEngineImportTaskError> list = null;

		if (useFinderCache) {
			list = (List<BatchEngineImportTaskError>)finderCache.getResult(
				finderPath, finderArgs, this);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_BATCHENGINEIMPORTTASKERROR);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_BATCHENGINEIMPORTTASKERROR;

				sql = sql.concat(
					BatchEngineImportTaskErrorModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<BatchEngineImportTaskError>)QueryUtil.list(
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
	 * Removes all the batch engine import task errors from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (BatchEngineImportTaskError batchEngineImportTaskError :
				findAll()) {

			remove(batchEngineImportTaskError);
		}
	}

	/**
	 * Returns the number of batch engine import task errors.
	 *
	 * @return the number of batch engine import task errors
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
					_SQL_COUNT_BATCHENGINEIMPORTTASKERROR);

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
		return "batchEngineImportTaskErrorId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_BATCHENGINEIMPORTTASKERROR;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return BatchEngineImportTaskErrorModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the batch engine import task error persistence.
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

		_finderPathWithPaginationFindByBatchEngineImportTaskId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByBatchEngineImportTaskId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"batchEngineImportTaskId"}, true);

		_finderPathWithoutPaginationFindByBatchEngineImportTaskId =
			new FinderPath(
				FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
				"findByBatchEngineImportTaskId",
				new String[] {Long.class.getName()},
				new String[] {"batchEngineImportTaskId"}, true);

		_finderPathCountByBatchEngineImportTaskId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByBatchEngineImportTaskId",
			new String[] {Long.class.getName()},
			new String[] {"batchEngineImportTaskId"}, false);

		BatchEngineImportTaskErrorUtil.setPersistence(this);
	}

	@Deactivate
	public void deactivate() {
		BatchEngineImportTaskErrorUtil.setPersistence(null);

		entityCache.removeCache(BatchEngineImportTaskErrorImpl.class.getName());
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

	private static final String _SQL_SELECT_BATCHENGINEIMPORTTASKERROR =
		"SELECT batchEngineImportTaskError FROM BatchEngineImportTaskError batchEngineImportTaskError";

	private static final String _SQL_SELECT_BATCHENGINEIMPORTTASKERROR_WHERE =
		"SELECT batchEngineImportTaskError FROM BatchEngineImportTaskError batchEngineImportTaskError WHERE ";

	private static final String _SQL_COUNT_BATCHENGINEIMPORTTASKERROR =
		"SELECT COUNT(batchEngineImportTaskError) FROM BatchEngineImportTaskError batchEngineImportTaskError";

	private static final String _SQL_COUNT_BATCHENGINEIMPORTTASKERROR_WHERE =
		"SELECT COUNT(batchEngineImportTaskError) FROM BatchEngineImportTaskError batchEngineImportTaskError WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS =
		"batchEngineImportTaskError.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No BatchEngineImportTaskError exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No BatchEngineImportTaskError exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		BatchEngineImportTaskErrorPersistenceImpl.class);

	@Override
	protected FinderCache getFinderCache() {
		return finderCache;
	}

}