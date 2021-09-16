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

package com.liferay.portal.workflow.kaleo.service.persistence.impl;

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
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.workflow.kaleo.exception.NoSuchLogException;
import com.liferay.portal.workflow.kaleo.model.KaleoLog;
import com.liferay.portal.workflow.kaleo.model.KaleoLogTable;
import com.liferay.portal.workflow.kaleo.model.impl.KaleoLogImpl;
import com.liferay.portal.workflow.kaleo.model.impl.KaleoLogModelImpl;
import com.liferay.portal.workflow.kaleo.service.persistence.KaleoLogPersistence;
import com.liferay.portal.workflow.kaleo.service.persistence.impl.constants.KaleoPersistenceConstants;

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
 * The persistence implementation for the kaleo log service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
@Component(service = {KaleoLogPersistence.class, BasePersistence.class})
public class KaleoLogPersistenceImpl
	extends BasePersistenceImpl<KaleoLog> implements KaleoLogPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>KaleoLogUtil</code> to access the kaleo log persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		KaleoLogImpl.class.getName();

	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List1";

	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List2";

	private FinderPath _finderPathWithPaginationFindAll;
	private FinderPath _finderPathWithoutPaginationFindAll;
	private FinderPath _finderPathCountAll;
	private FinderPath _finderPathWithPaginationFindByCompanyId;
	private FinderPath _finderPathWithoutPaginationFindByCompanyId;
	private FinderPath _finderPathCountByCompanyId;

	/**
	 * Returns all the kaleo logs where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching kaleo logs
	 */
	@Override
	public List<KaleoLog> findByCompanyId(long companyId) {
		return findByCompanyId(
			companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the kaleo logs where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoLogModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of kaleo logs
	 * @param end the upper bound of the range of kaleo logs (not inclusive)
	 * @return the range of matching kaleo logs
	 */
	@Override
	public List<KaleoLog> findByCompanyId(long companyId, int start, int end) {
		return findByCompanyId(companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the kaleo logs where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoLogModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of kaleo logs
	 * @param end the upper bound of the range of kaleo logs (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching kaleo logs
	 */
	@Override
	public List<KaleoLog> findByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<KaleoLog> orderByComparator) {

		return findByCompanyId(companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the kaleo logs where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoLogModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of kaleo logs
	 * @param end the upper bound of the range of kaleo logs (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching kaleo logs
	 */
	@Override
	public List<KaleoLog> findByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<KaleoLog> orderByComparator, boolean useFinderCache) {

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

		List<KaleoLog> list = null;

		if (useFinderCache) {
			list = (List<KaleoLog>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (KaleoLog kaleoLog : list) {
					if (companyId != kaleoLog.getCompanyId()) {
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

			sb.append(_SQL_SELECT_KALEOLOG_WHERE);

			sb.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(KaleoLogModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(companyId);

				list = (List<KaleoLog>)QueryUtil.list(
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
	 * Returns the first kaleo log in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching kaleo log
	 * @throws NoSuchLogException if a matching kaleo log could not be found
	 */
	@Override
	public KaleoLog findByCompanyId_First(
			long companyId, OrderByComparator<KaleoLog> orderByComparator)
		throws NoSuchLogException {

		KaleoLog kaleoLog = fetchByCompanyId_First(
			companyId, orderByComparator);

		if (kaleoLog != null) {
			return kaleoLog;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchLogException(sb.toString());
	}

	/**
	 * Returns the first kaleo log in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching kaleo log, or <code>null</code> if a matching kaleo log could not be found
	 */
	@Override
	public KaleoLog fetchByCompanyId_First(
		long companyId, OrderByComparator<KaleoLog> orderByComparator) {

		List<KaleoLog> list = findByCompanyId(
			companyId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last kaleo log in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching kaleo log
	 * @throws NoSuchLogException if a matching kaleo log could not be found
	 */
	@Override
	public KaleoLog findByCompanyId_Last(
			long companyId, OrderByComparator<KaleoLog> orderByComparator)
		throws NoSuchLogException {

		KaleoLog kaleoLog = fetchByCompanyId_Last(companyId, orderByComparator);

		if (kaleoLog != null) {
			return kaleoLog;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchLogException(sb.toString());
	}

	/**
	 * Returns the last kaleo log in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching kaleo log, or <code>null</code> if a matching kaleo log could not be found
	 */
	@Override
	public KaleoLog fetchByCompanyId_Last(
		long companyId, OrderByComparator<KaleoLog> orderByComparator) {

		int count = countByCompanyId(companyId);

		if (count == 0) {
			return null;
		}

		List<KaleoLog> list = findByCompanyId(
			companyId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the kaleo logs before and after the current kaleo log in the ordered set where companyId = &#63;.
	 *
	 * @param kaleoLogId the primary key of the current kaleo log
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next kaleo log
	 * @throws NoSuchLogException if a kaleo log with the primary key could not be found
	 */
	@Override
	public KaleoLog[] findByCompanyId_PrevAndNext(
			long kaleoLogId, long companyId,
			OrderByComparator<KaleoLog> orderByComparator)
		throws NoSuchLogException {

		KaleoLog kaleoLog = findByPrimaryKey(kaleoLogId);

		Session session = null;

		try {
			session = openSession();

			KaleoLog[] array = new KaleoLogImpl[3];

			array[0] = getByCompanyId_PrevAndNext(
				session, kaleoLog, companyId, orderByComparator, true);

			array[1] = kaleoLog;

			array[2] = getByCompanyId_PrevAndNext(
				session, kaleoLog, companyId, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected KaleoLog getByCompanyId_PrevAndNext(
		Session session, KaleoLog kaleoLog, long companyId,
		OrderByComparator<KaleoLog> orderByComparator, boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(3);
		}

		sb.append(_SQL_SELECT_KALEOLOG_WHERE);

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
			sb.append(KaleoLogModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(companyId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(kaleoLog)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<KaleoLog> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the kaleo logs where companyId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 */
	@Override
	public void removeByCompanyId(long companyId) {
		for (KaleoLog kaleoLog :
				findByCompanyId(
					companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(kaleoLog);
		}
	}

	/**
	 * Returns the number of kaleo logs where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching kaleo logs
	 */
	@Override
	public int countByCompanyId(long companyId) {
		FinderPath finderPath = _finderPathCountByCompanyId;

		Object[] finderArgs = new Object[] {companyId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_KALEOLOG_WHERE);

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
		"kaleoLog.companyId = ?";

	private FinderPath _finderPathWithPaginationFindByKaleoDefinitionVersionId;
	private FinderPath
		_finderPathWithoutPaginationFindByKaleoDefinitionVersionId;
	private FinderPath _finderPathCountByKaleoDefinitionVersionId;

	/**
	 * Returns all the kaleo logs where kaleoDefinitionVersionId = &#63;.
	 *
	 * @param kaleoDefinitionVersionId the kaleo definition version ID
	 * @return the matching kaleo logs
	 */
	@Override
	public List<KaleoLog> findByKaleoDefinitionVersionId(
		long kaleoDefinitionVersionId) {

		return findByKaleoDefinitionVersionId(
			kaleoDefinitionVersionId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the kaleo logs where kaleoDefinitionVersionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoLogModelImpl</code>.
	 * </p>
	 *
	 * @param kaleoDefinitionVersionId the kaleo definition version ID
	 * @param start the lower bound of the range of kaleo logs
	 * @param end the upper bound of the range of kaleo logs (not inclusive)
	 * @return the range of matching kaleo logs
	 */
	@Override
	public List<KaleoLog> findByKaleoDefinitionVersionId(
		long kaleoDefinitionVersionId, int start, int end) {

		return findByKaleoDefinitionVersionId(
			kaleoDefinitionVersionId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the kaleo logs where kaleoDefinitionVersionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoLogModelImpl</code>.
	 * </p>
	 *
	 * @param kaleoDefinitionVersionId the kaleo definition version ID
	 * @param start the lower bound of the range of kaleo logs
	 * @param end the upper bound of the range of kaleo logs (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching kaleo logs
	 */
	@Override
	public List<KaleoLog> findByKaleoDefinitionVersionId(
		long kaleoDefinitionVersionId, int start, int end,
		OrderByComparator<KaleoLog> orderByComparator) {

		return findByKaleoDefinitionVersionId(
			kaleoDefinitionVersionId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the kaleo logs where kaleoDefinitionVersionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoLogModelImpl</code>.
	 * </p>
	 *
	 * @param kaleoDefinitionVersionId the kaleo definition version ID
	 * @param start the lower bound of the range of kaleo logs
	 * @param end the upper bound of the range of kaleo logs (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching kaleo logs
	 */
	@Override
	public List<KaleoLog> findByKaleoDefinitionVersionId(
		long kaleoDefinitionVersionId, int start, int end,
		OrderByComparator<KaleoLog> orderByComparator, boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath =
					_finderPathWithoutPaginationFindByKaleoDefinitionVersionId;
				finderArgs = new Object[] {kaleoDefinitionVersionId};
			}
		}
		else if (useFinderCache) {
			finderPath =
				_finderPathWithPaginationFindByKaleoDefinitionVersionId;
			finderArgs = new Object[] {
				kaleoDefinitionVersionId, start, end, orderByComparator
			};
		}

		List<KaleoLog> list = null;

		if (useFinderCache) {
			list = (List<KaleoLog>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (KaleoLog kaleoLog : list) {
					if (kaleoDefinitionVersionId !=
							kaleoLog.getKaleoDefinitionVersionId()) {

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

			sb.append(_SQL_SELECT_KALEOLOG_WHERE);

			sb.append(
				_FINDER_COLUMN_KALEODEFINITIONVERSIONID_KALEODEFINITIONVERSIONID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(KaleoLogModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(kaleoDefinitionVersionId);

				list = (List<KaleoLog>)QueryUtil.list(
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
	 * Returns the first kaleo log in the ordered set where kaleoDefinitionVersionId = &#63;.
	 *
	 * @param kaleoDefinitionVersionId the kaleo definition version ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching kaleo log
	 * @throws NoSuchLogException if a matching kaleo log could not be found
	 */
	@Override
	public KaleoLog findByKaleoDefinitionVersionId_First(
			long kaleoDefinitionVersionId,
			OrderByComparator<KaleoLog> orderByComparator)
		throws NoSuchLogException {

		KaleoLog kaleoLog = fetchByKaleoDefinitionVersionId_First(
			kaleoDefinitionVersionId, orderByComparator);

		if (kaleoLog != null) {
			return kaleoLog;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("kaleoDefinitionVersionId=");
		sb.append(kaleoDefinitionVersionId);

		sb.append("}");

		throw new NoSuchLogException(sb.toString());
	}

	/**
	 * Returns the first kaleo log in the ordered set where kaleoDefinitionVersionId = &#63;.
	 *
	 * @param kaleoDefinitionVersionId the kaleo definition version ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching kaleo log, or <code>null</code> if a matching kaleo log could not be found
	 */
	@Override
	public KaleoLog fetchByKaleoDefinitionVersionId_First(
		long kaleoDefinitionVersionId,
		OrderByComparator<KaleoLog> orderByComparator) {

		List<KaleoLog> list = findByKaleoDefinitionVersionId(
			kaleoDefinitionVersionId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last kaleo log in the ordered set where kaleoDefinitionVersionId = &#63;.
	 *
	 * @param kaleoDefinitionVersionId the kaleo definition version ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching kaleo log
	 * @throws NoSuchLogException if a matching kaleo log could not be found
	 */
	@Override
	public KaleoLog findByKaleoDefinitionVersionId_Last(
			long kaleoDefinitionVersionId,
			OrderByComparator<KaleoLog> orderByComparator)
		throws NoSuchLogException {

		KaleoLog kaleoLog = fetchByKaleoDefinitionVersionId_Last(
			kaleoDefinitionVersionId, orderByComparator);

		if (kaleoLog != null) {
			return kaleoLog;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("kaleoDefinitionVersionId=");
		sb.append(kaleoDefinitionVersionId);

		sb.append("}");

		throw new NoSuchLogException(sb.toString());
	}

	/**
	 * Returns the last kaleo log in the ordered set where kaleoDefinitionVersionId = &#63;.
	 *
	 * @param kaleoDefinitionVersionId the kaleo definition version ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching kaleo log, or <code>null</code> if a matching kaleo log could not be found
	 */
	@Override
	public KaleoLog fetchByKaleoDefinitionVersionId_Last(
		long kaleoDefinitionVersionId,
		OrderByComparator<KaleoLog> orderByComparator) {

		int count = countByKaleoDefinitionVersionId(kaleoDefinitionVersionId);

		if (count == 0) {
			return null;
		}

		List<KaleoLog> list = findByKaleoDefinitionVersionId(
			kaleoDefinitionVersionId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the kaleo logs before and after the current kaleo log in the ordered set where kaleoDefinitionVersionId = &#63;.
	 *
	 * @param kaleoLogId the primary key of the current kaleo log
	 * @param kaleoDefinitionVersionId the kaleo definition version ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next kaleo log
	 * @throws NoSuchLogException if a kaleo log with the primary key could not be found
	 */
	@Override
	public KaleoLog[] findByKaleoDefinitionVersionId_PrevAndNext(
			long kaleoLogId, long kaleoDefinitionVersionId,
			OrderByComparator<KaleoLog> orderByComparator)
		throws NoSuchLogException {

		KaleoLog kaleoLog = findByPrimaryKey(kaleoLogId);

		Session session = null;

		try {
			session = openSession();

			KaleoLog[] array = new KaleoLogImpl[3];

			array[0] = getByKaleoDefinitionVersionId_PrevAndNext(
				session, kaleoLog, kaleoDefinitionVersionId, orderByComparator,
				true);

			array[1] = kaleoLog;

			array[2] = getByKaleoDefinitionVersionId_PrevAndNext(
				session, kaleoLog, kaleoDefinitionVersionId, orderByComparator,
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

	protected KaleoLog getByKaleoDefinitionVersionId_PrevAndNext(
		Session session, KaleoLog kaleoLog, long kaleoDefinitionVersionId,
		OrderByComparator<KaleoLog> orderByComparator, boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(3);
		}

		sb.append(_SQL_SELECT_KALEOLOG_WHERE);

		sb.append(
			_FINDER_COLUMN_KALEODEFINITIONVERSIONID_KALEODEFINITIONVERSIONID_2);

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
			sb.append(KaleoLogModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(kaleoDefinitionVersionId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(kaleoLog)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<KaleoLog> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the kaleo logs where kaleoDefinitionVersionId = &#63; from the database.
	 *
	 * @param kaleoDefinitionVersionId the kaleo definition version ID
	 */
	@Override
	public void removeByKaleoDefinitionVersionId(
		long kaleoDefinitionVersionId) {

		for (KaleoLog kaleoLog :
				findByKaleoDefinitionVersionId(
					kaleoDefinitionVersionId, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			remove(kaleoLog);
		}
	}

	/**
	 * Returns the number of kaleo logs where kaleoDefinitionVersionId = &#63;.
	 *
	 * @param kaleoDefinitionVersionId the kaleo definition version ID
	 * @return the number of matching kaleo logs
	 */
	@Override
	public int countByKaleoDefinitionVersionId(long kaleoDefinitionVersionId) {
		FinderPath finderPath = _finderPathCountByKaleoDefinitionVersionId;

		Object[] finderArgs = new Object[] {kaleoDefinitionVersionId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_KALEOLOG_WHERE);

			sb.append(
				_FINDER_COLUMN_KALEODEFINITIONVERSIONID_KALEODEFINITIONVERSIONID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(kaleoDefinitionVersionId);

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
		_FINDER_COLUMN_KALEODEFINITIONVERSIONID_KALEODEFINITIONVERSIONID_2 =
			"kaleoLog.kaleoDefinitionVersionId = ?";

	private FinderPath _finderPathWithPaginationFindByKaleoInstanceId;
	private FinderPath _finderPathWithoutPaginationFindByKaleoInstanceId;
	private FinderPath _finderPathCountByKaleoInstanceId;

	/**
	 * Returns all the kaleo logs where kaleoInstanceId = &#63;.
	 *
	 * @param kaleoInstanceId the kaleo instance ID
	 * @return the matching kaleo logs
	 */
	@Override
	public List<KaleoLog> findByKaleoInstanceId(long kaleoInstanceId) {
		return findByKaleoInstanceId(
			kaleoInstanceId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the kaleo logs where kaleoInstanceId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoLogModelImpl</code>.
	 * </p>
	 *
	 * @param kaleoInstanceId the kaleo instance ID
	 * @param start the lower bound of the range of kaleo logs
	 * @param end the upper bound of the range of kaleo logs (not inclusive)
	 * @return the range of matching kaleo logs
	 */
	@Override
	public List<KaleoLog> findByKaleoInstanceId(
		long kaleoInstanceId, int start, int end) {

		return findByKaleoInstanceId(kaleoInstanceId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the kaleo logs where kaleoInstanceId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoLogModelImpl</code>.
	 * </p>
	 *
	 * @param kaleoInstanceId the kaleo instance ID
	 * @param start the lower bound of the range of kaleo logs
	 * @param end the upper bound of the range of kaleo logs (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching kaleo logs
	 */
	@Override
	public List<KaleoLog> findByKaleoInstanceId(
		long kaleoInstanceId, int start, int end,
		OrderByComparator<KaleoLog> orderByComparator) {

		return findByKaleoInstanceId(
			kaleoInstanceId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the kaleo logs where kaleoInstanceId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoLogModelImpl</code>.
	 * </p>
	 *
	 * @param kaleoInstanceId the kaleo instance ID
	 * @param start the lower bound of the range of kaleo logs
	 * @param end the upper bound of the range of kaleo logs (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching kaleo logs
	 */
	@Override
	public List<KaleoLog> findByKaleoInstanceId(
		long kaleoInstanceId, int start, int end,
		OrderByComparator<KaleoLog> orderByComparator, boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByKaleoInstanceId;
				finderArgs = new Object[] {kaleoInstanceId};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByKaleoInstanceId;
			finderArgs = new Object[] {
				kaleoInstanceId, start, end, orderByComparator
			};
		}

		List<KaleoLog> list = null;

		if (useFinderCache) {
			list = (List<KaleoLog>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (KaleoLog kaleoLog : list) {
					if (kaleoInstanceId != kaleoLog.getKaleoInstanceId()) {
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

			sb.append(_SQL_SELECT_KALEOLOG_WHERE);

			sb.append(_FINDER_COLUMN_KALEOINSTANCEID_KALEOINSTANCEID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(KaleoLogModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(kaleoInstanceId);

				list = (List<KaleoLog>)QueryUtil.list(
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
	 * Returns the first kaleo log in the ordered set where kaleoInstanceId = &#63;.
	 *
	 * @param kaleoInstanceId the kaleo instance ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching kaleo log
	 * @throws NoSuchLogException if a matching kaleo log could not be found
	 */
	@Override
	public KaleoLog findByKaleoInstanceId_First(
			long kaleoInstanceId, OrderByComparator<KaleoLog> orderByComparator)
		throws NoSuchLogException {

		KaleoLog kaleoLog = fetchByKaleoInstanceId_First(
			kaleoInstanceId, orderByComparator);

		if (kaleoLog != null) {
			return kaleoLog;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("kaleoInstanceId=");
		sb.append(kaleoInstanceId);

		sb.append("}");

		throw new NoSuchLogException(sb.toString());
	}

	/**
	 * Returns the first kaleo log in the ordered set where kaleoInstanceId = &#63;.
	 *
	 * @param kaleoInstanceId the kaleo instance ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching kaleo log, or <code>null</code> if a matching kaleo log could not be found
	 */
	@Override
	public KaleoLog fetchByKaleoInstanceId_First(
		long kaleoInstanceId, OrderByComparator<KaleoLog> orderByComparator) {

		List<KaleoLog> list = findByKaleoInstanceId(
			kaleoInstanceId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last kaleo log in the ordered set where kaleoInstanceId = &#63;.
	 *
	 * @param kaleoInstanceId the kaleo instance ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching kaleo log
	 * @throws NoSuchLogException if a matching kaleo log could not be found
	 */
	@Override
	public KaleoLog findByKaleoInstanceId_Last(
			long kaleoInstanceId, OrderByComparator<KaleoLog> orderByComparator)
		throws NoSuchLogException {

		KaleoLog kaleoLog = fetchByKaleoInstanceId_Last(
			kaleoInstanceId, orderByComparator);

		if (kaleoLog != null) {
			return kaleoLog;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("kaleoInstanceId=");
		sb.append(kaleoInstanceId);

		sb.append("}");

		throw new NoSuchLogException(sb.toString());
	}

	/**
	 * Returns the last kaleo log in the ordered set where kaleoInstanceId = &#63;.
	 *
	 * @param kaleoInstanceId the kaleo instance ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching kaleo log, or <code>null</code> if a matching kaleo log could not be found
	 */
	@Override
	public KaleoLog fetchByKaleoInstanceId_Last(
		long kaleoInstanceId, OrderByComparator<KaleoLog> orderByComparator) {

		int count = countByKaleoInstanceId(kaleoInstanceId);

		if (count == 0) {
			return null;
		}

		List<KaleoLog> list = findByKaleoInstanceId(
			kaleoInstanceId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the kaleo logs before and after the current kaleo log in the ordered set where kaleoInstanceId = &#63;.
	 *
	 * @param kaleoLogId the primary key of the current kaleo log
	 * @param kaleoInstanceId the kaleo instance ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next kaleo log
	 * @throws NoSuchLogException if a kaleo log with the primary key could not be found
	 */
	@Override
	public KaleoLog[] findByKaleoInstanceId_PrevAndNext(
			long kaleoLogId, long kaleoInstanceId,
			OrderByComparator<KaleoLog> orderByComparator)
		throws NoSuchLogException {

		KaleoLog kaleoLog = findByPrimaryKey(kaleoLogId);

		Session session = null;

		try {
			session = openSession();

			KaleoLog[] array = new KaleoLogImpl[3];

			array[0] = getByKaleoInstanceId_PrevAndNext(
				session, kaleoLog, kaleoInstanceId, orderByComparator, true);

			array[1] = kaleoLog;

			array[2] = getByKaleoInstanceId_PrevAndNext(
				session, kaleoLog, kaleoInstanceId, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected KaleoLog getByKaleoInstanceId_PrevAndNext(
		Session session, KaleoLog kaleoLog, long kaleoInstanceId,
		OrderByComparator<KaleoLog> orderByComparator, boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(3);
		}

		sb.append(_SQL_SELECT_KALEOLOG_WHERE);

		sb.append(_FINDER_COLUMN_KALEOINSTANCEID_KALEOINSTANCEID_2);

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
			sb.append(KaleoLogModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(kaleoInstanceId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(kaleoLog)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<KaleoLog> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the kaleo logs where kaleoInstanceId = &#63; from the database.
	 *
	 * @param kaleoInstanceId the kaleo instance ID
	 */
	@Override
	public void removeByKaleoInstanceId(long kaleoInstanceId) {
		for (KaleoLog kaleoLog :
				findByKaleoInstanceId(
					kaleoInstanceId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(kaleoLog);
		}
	}

	/**
	 * Returns the number of kaleo logs where kaleoInstanceId = &#63;.
	 *
	 * @param kaleoInstanceId the kaleo instance ID
	 * @return the number of matching kaleo logs
	 */
	@Override
	public int countByKaleoInstanceId(long kaleoInstanceId) {
		FinderPath finderPath = _finderPathCountByKaleoInstanceId;

		Object[] finderArgs = new Object[] {kaleoInstanceId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_KALEOLOG_WHERE);

			sb.append(_FINDER_COLUMN_KALEOINSTANCEID_KALEOINSTANCEID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(kaleoInstanceId);

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
		_FINDER_COLUMN_KALEOINSTANCEID_KALEOINSTANCEID_2 =
			"kaleoLog.kaleoInstanceId = ?";

	private FinderPath _finderPathWithPaginationFindByKaleoTaskInstanceTokenId;
	private FinderPath
		_finderPathWithoutPaginationFindByKaleoTaskInstanceTokenId;
	private FinderPath _finderPathCountByKaleoTaskInstanceTokenId;

	/**
	 * Returns all the kaleo logs where kaleoTaskInstanceTokenId = &#63;.
	 *
	 * @param kaleoTaskInstanceTokenId the kaleo task instance token ID
	 * @return the matching kaleo logs
	 */
	@Override
	public List<KaleoLog> findByKaleoTaskInstanceTokenId(
		long kaleoTaskInstanceTokenId) {

		return findByKaleoTaskInstanceTokenId(
			kaleoTaskInstanceTokenId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the kaleo logs where kaleoTaskInstanceTokenId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoLogModelImpl</code>.
	 * </p>
	 *
	 * @param kaleoTaskInstanceTokenId the kaleo task instance token ID
	 * @param start the lower bound of the range of kaleo logs
	 * @param end the upper bound of the range of kaleo logs (not inclusive)
	 * @return the range of matching kaleo logs
	 */
	@Override
	public List<KaleoLog> findByKaleoTaskInstanceTokenId(
		long kaleoTaskInstanceTokenId, int start, int end) {

		return findByKaleoTaskInstanceTokenId(
			kaleoTaskInstanceTokenId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the kaleo logs where kaleoTaskInstanceTokenId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoLogModelImpl</code>.
	 * </p>
	 *
	 * @param kaleoTaskInstanceTokenId the kaleo task instance token ID
	 * @param start the lower bound of the range of kaleo logs
	 * @param end the upper bound of the range of kaleo logs (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching kaleo logs
	 */
	@Override
	public List<KaleoLog> findByKaleoTaskInstanceTokenId(
		long kaleoTaskInstanceTokenId, int start, int end,
		OrderByComparator<KaleoLog> orderByComparator) {

		return findByKaleoTaskInstanceTokenId(
			kaleoTaskInstanceTokenId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the kaleo logs where kaleoTaskInstanceTokenId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoLogModelImpl</code>.
	 * </p>
	 *
	 * @param kaleoTaskInstanceTokenId the kaleo task instance token ID
	 * @param start the lower bound of the range of kaleo logs
	 * @param end the upper bound of the range of kaleo logs (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching kaleo logs
	 */
	@Override
	public List<KaleoLog> findByKaleoTaskInstanceTokenId(
		long kaleoTaskInstanceTokenId, int start, int end,
		OrderByComparator<KaleoLog> orderByComparator, boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath =
					_finderPathWithoutPaginationFindByKaleoTaskInstanceTokenId;
				finderArgs = new Object[] {kaleoTaskInstanceTokenId};
			}
		}
		else if (useFinderCache) {
			finderPath =
				_finderPathWithPaginationFindByKaleoTaskInstanceTokenId;
			finderArgs = new Object[] {
				kaleoTaskInstanceTokenId, start, end, orderByComparator
			};
		}

		List<KaleoLog> list = null;

		if (useFinderCache) {
			list = (List<KaleoLog>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (KaleoLog kaleoLog : list) {
					if (kaleoTaskInstanceTokenId !=
							kaleoLog.getKaleoTaskInstanceTokenId()) {

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

			sb.append(_SQL_SELECT_KALEOLOG_WHERE);

			sb.append(
				_FINDER_COLUMN_KALEOTASKINSTANCETOKENID_KALEOTASKINSTANCETOKENID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(KaleoLogModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(kaleoTaskInstanceTokenId);

				list = (List<KaleoLog>)QueryUtil.list(
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
	 * Returns the first kaleo log in the ordered set where kaleoTaskInstanceTokenId = &#63;.
	 *
	 * @param kaleoTaskInstanceTokenId the kaleo task instance token ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching kaleo log
	 * @throws NoSuchLogException if a matching kaleo log could not be found
	 */
	@Override
	public KaleoLog findByKaleoTaskInstanceTokenId_First(
			long kaleoTaskInstanceTokenId,
			OrderByComparator<KaleoLog> orderByComparator)
		throws NoSuchLogException {

		KaleoLog kaleoLog = fetchByKaleoTaskInstanceTokenId_First(
			kaleoTaskInstanceTokenId, orderByComparator);

		if (kaleoLog != null) {
			return kaleoLog;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("kaleoTaskInstanceTokenId=");
		sb.append(kaleoTaskInstanceTokenId);

		sb.append("}");

		throw new NoSuchLogException(sb.toString());
	}

	/**
	 * Returns the first kaleo log in the ordered set where kaleoTaskInstanceTokenId = &#63;.
	 *
	 * @param kaleoTaskInstanceTokenId the kaleo task instance token ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching kaleo log, or <code>null</code> if a matching kaleo log could not be found
	 */
	@Override
	public KaleoLog fetchByKaleoTaskInstanceTokenId_First(
		long kaleoTaskInstanceTokenId,
		OrderByComparator<KaleoLog> orderByComparator) {

		List<KaleoLog> list = findByKaleoTaskInstanceTokenId(
			kaleoTaskInstanceTokenId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last kaleo log in the ordered set where kaleoTaskInstanceTokenId = &#63;.
	 *
	 * @param kaleoTaskInstanceTokenId the kaleo task instance token ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching kaleo log
	 * @throws NoSuchLogException if a matching kaleo log could not be found
	 */
	@Override
	public KaleoLog findByKaleoTaskInstanceTokenId_Last(
			long kaleoTaskInstanceTokenId,
			OrderByComparator<KaleoLog> orderByComparator)
		throws NoSuchLogException {

		KaleoLog kaleoLog = fetchByKaleoTaskInstanceTokenId_Last(
			kaleoTaskInstanceTokenId, orderByComparator);

		if (kaleoLog != null) {
			return kaleoLog;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("kaleoTaskInstanceTokenId=");
		sb.append(kaleoTaskInstanceTokenId);

		sb.append("}");

		throw new NoSuchLogException(sb.toString());
	}

	/**
	 * Returns the last kaleo log in the ordered set where kaleoTaskInstanceTokenId = &#63;.
	 *
	 * @param kaleoTaskInstanceTokenId the kaleo task instance token ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching kaleo log, or <code>null</code> if a matching kaleo log could not be found
	 */
	@Override
	public KaleoLog fetchByKaleoTaskInstanceTokenId_Last(
		long kaleoTaskInstanceTokenId,
		OrderByComparator<KaleoLog> orderByComparator) {

		int count = countByKaleoTaskInstanceTokenId(kaleoTaskInstanceTokenId);

		if (count == 0) {
			return null;
		}

		List<KaleoLog> list = findByKaleoTaskInstanceTokenId(
			kaleoTaskInstanceTokenId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the kaleo logs before and after the current kaleo log in the ordered set where kaleoTaskInstanceTokenId = &#63;.
	 *
	 * @param kaleoLogId the primary key of the current kaleo log
	 * @param kaleoTaskInstanceTokenId the kaleo task instance token ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next kaleo log
	 * @throws NoSuchLogException if a kaleo log with the primary key could not be found
	 */
	@Override
	public KaleoLog[] findByKaleoTaskInstanceTokenId_PrevAndNext(
			long kaleoLogId, long kaleoTaskInstanceTokenId,
			OrderByComparator<KaleoLog> orderByComparator)
		throws NoSuchLogException {

		KaleoLog kaleoLog = findByPrimaryKey(kaleoLogId);

		Session session = null;

		try {
			session = openSession();

			KaleoLog[] array = new KaleoLogImpl[3];

			array[0] = getByKaleoTaskInstanceTokenId_PrevAndNext(
				session, kaleoLog, kaleoTaskInstanceTokenId, orderByComparator,
				true);

			array[1] = kaleoLog;

			array[2] = getByKaleoTaskInstanceTokenId_PrevAndNext(
				session, kaleoLog, kaleoTaskInstanceTokenId, orderByComparator,
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

	protected KaleoLog getByKaleoTaskInstanceTokenId_PrevAndNext(
		Session session, KaleoLog kaleoLog, long kaleoTaskInstanceTokenId,
		OrderByComparator<KaleoLog> orderByComparator, boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(3);
		}

		sb.append(_SQL_SELECT_KALEOLOG_WHERE);

		sb.append(
			_FINDER_COLUMN_KALEOTASKINSTANCETOKENID_KALEOTASKINSTANCETOKENID_2);

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
			sb.append(KaleoLogModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(kaleoTaskInstanceTokenId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(kaleoLog)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<KaleoLog> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the kaleo logs where kaleoTaskInstanceTokenId = &#63; from the database.
	 *
	 * @param kaleoTaskInstanceTokenId the kaleo task instance token ID
	 */
	@Override
	public void removeByKaleoTaskInstanceTokenId(
		long kaleoTaskInstanceTokenId) {

		for (KaleoLog kaleoLog :
				findByKaleoTaskInstanceTokenId(
					kaleoTaskInstanceTokenId, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			remove(kaleoLog);
		}
	}

	/**
	 * Returns the number of kaleo logs where kaleoTaskInstanceTokenId = &#63;.
	 *
	 * @param kaleoTaskInstanceTokenId the kaleo task instance token ID
	 * @return the number of matching kaleo logs
	 */
	@Override
	public int countByKaleoTaskInstanceTokenId(long kaleoTaskInstanceTokenId) {
		FinderPath finderPath = _finderPathCountByKaleoTaskInstanceTokenId;

		Object[] finderArgs = new Object[] {kaleoTaskInstanceTokenId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_KALEOLOG_WHERE);

			sb.append(
				_FINDER_COLUMN_KALEOTASKINSTANCETOKENID_KALEOTASKINSTANCETOKENID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(kaleoTaskInstanceTokenId);

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
		_FINDER_COLUMN_KALEOTASKINSTANCETOKENID_KALEOTASKINSTANCETOKENID_2 =
			"kaleoLog.kaleoTaskInstanceTokenId = ?";

	private FinderPath _finderPathWithPaginationFindByKITI_T;
	private FinderPath _finderPathWithoutPaginationFindByKITI_T;
	private FinderPath _finderPathCountByKITI_T;

	/**
	 * Returns all the kaleo logs where kaleoInstanceTokenId = &#63; and type = &#63;.
	 *
	 * @param kaleoInstanceTokenId the kaleo instance token ID
	 * @param type the type
	 * @return the matching kaleo logs
	 */
	@Override
	public List<KaleoLog> findByKITI_T(long kaleoInstanceTokenId, String type) {
		return findByKITI_T(
			kaleoInstanceTokenId, type, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the kaleo logs where kaleoInstanceTokenId = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoLogModelImpl</code>.
	 * </p>
	 *
	 * @param kaleoInstanceTokenId the kaleo instance token ID
	 * @param type the type
	 * @param start the lower bound of the range of kaleo logs
	 * @param end the upper bound of the range of kaleo logs (not inclusive)
	 * @return the range of matching kaleo logs
	 */
	@Override
	public List<KaleoLog> findByKITI_T(
		long kaleoInstanceTokenId, String type, int start, int end) {

		return findByKITI_T(kaleoInstanceTokenId, type, start, end, null);
	}

	/**
	 * Returns an ordered range of all the kaleo logs where kaleoInstanceTokenId = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoLogModelImpl</code>.
	 * </p>
	 *
	 * @param kaleoInstanceTokenId the kaleo instance token ID
	 * @param type the type
	 * @param start the lower bound of the range of kaleo logs
	 * @param end the upper bound of the range of kaleo logs (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching kaleo logs
	 */
	@Override
	public List<KaleoLog> findByKITI_T(
		long kaleoInstanceTokenId, String type, int start, int end,
		OrderByComparator<KaleoLog> orderByComparator) {

		return findByKITI_T(
			kaleoInstanceTokenId, type, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the kaleo logs where kaleoInstanceTokenId = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoLogModelImpl</code>.
	 * </p>
	 *
	 * @param kaleoInstanceTokenId the kaleo instance token ID
	 * @param type the type
	 * @param start the lower bound of the range of kaleo logs
	 * @param end the upper bound of the range of kaleo logs (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching kaleo logs
	 */
	@Override
	public List<KaleoLog> findByKITI_T(
		long kaleoInstanceTokenId, String type, int start, int end,
		OrderByComparator<KaleoLog> orderByComparator, boolean useFinderCache) {

		type = Objects.toString(type, "");

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByKITI_T;
				finderArgs = new Object[] {kaleoInstanceTokenId, type};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByKITI_T;
			finderArgs = new Object[] {
				kaleoInstanceTokenId, type, start, end, orderByComparator
			};
		}

		List<KaleoLog> list = null;

		if (useFinderCache) {
			list = (List<KaleoLog>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (KaleoLog kaleoLog : list) {
					if ((kaleoInstanceTokenId !=
							kaleoLog.getKaleoInstanceTokenId()) ||
						!type.equals(kaleoLog.getType())) {

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

			sb.append(_SQL_SELECT_KALEOLOG_WHERE);

			sb.append(_FINDER_COLUMN_KITI_T_KALEOINSTANCETOKENID_2);

			boolean bindType = false;

			if (type.isEmpty()) {
				sb.append(_FINDER_COLUMN_KITI_T_TYPE_3);
			}
			else {
				bindType = true;

				sb.append(_FINDER_COLUMN_KITI_T_TYPE_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(KaleoLogModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(kaleoInstanceTokenId);

				if (bindType) {
					queryPos.add(type);
				}

				list = (List<KaleoLog>)QueryUtil.list(
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
	 * Returns the first kaleo log in the ordered set where kaleoInstanceTokenId = &#63; and type = &#63;.
	 *
	 * @param kaleoInstanceTokenId the kaleo instance token ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching kaleo log
	 * @throws NoSuchLogException if a matching kaleo log could not be found
	 */
	@Override
	public KaleoLog findByKITI_T_First(
			long kaleoInstanceTokenId, String type,
			OrderByComparator<KaleoLog> orderByComparator)
		throws NoSuchLogException {

		KaleoLog kaleoLog = fetchByKITI_T_First(
			kaleoInstanceTokenId, type, orderByComparator);

		if (kaleoLog != null) {
			return kaleoLog;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("kaleoInstanceTokenId=");
		sb.append(kaleoInstanceTokenId);

		sb.append(", type=");
		sb.append(type);

		sb.append("}");

		throw new NoSuchLogException(sb.toString());
	}

	/**
	 * Returns the first kaleo log in the ordered set where kaleoInstanceTokenId = &#63; and type = &#63;.
	 *
	 * @param kaleoInstanceTokenId the kaleo instance token ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching kaleo log, or <code>null</code> if a matching kaleo log could not be found
	 */
	@Override
	public KaleoLog fetchByKITI_T_First(
		long kaleoInstanceTokenId, String type,
		OrderByComparator<KaleoLog> orderByComparator) {

		List<KaleoLog> list = findByKITI_T(
			kaleoInstanceTokenId, type, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last kaleo log in the ordered set where kaleoInstanceTokenId = &#63; and type = &#63;.
	 *
	 * @param kaleoInstanceTokenId the kaleo instance token ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching kaleo log
	 * @throws NoSuchLogException if a matching kaleo log could not be found
	 */
	@Override
	public KaleoLog findByKITI_T_Last(
			long kaleoInstanceTokenId, String type,
			OrderByComparator<KaleoLog> orderByComparator)
		throws NoSuchLogException {

		KaleoLog kaleoLog = fetchByKITI_T_Last(
			kaleoInstanceTokenId, type, orderByComparator);

		if (kaleoLog != null) {
			return kaleoLog;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("kaleoInstanceTokenId=");
		sb.append(kaleoInstanceTokenId);

		sb.append(", type=");
		sb.append(type);

		sb.append("}");

		throw new NoSuchLogException(sb.toString());
	}

	/**
	 * Returns the last kaleo log in the ordered set where kaleoInstanceTokenId = &#63; and type = &#63;.
	 *
	 * @param kaleoInstanceTokenId the kaleo instance token ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching kaleo log, or <code>null</code> if a matching kaleo log could not be found
	 */
	@Override
	public KaleoLog fetchByKITI_T_Last(
		long kaleoInstanceTokenId, String type,
		OrderByComparator<KaleoLog> orderByComparator) {

		int count = countByKITI_T(kaleoInstanceTokenId, type);

		if (count == 0) {
			return null;
		}

		List<KaleoLog> list = findByKITI_T(
			kaleoInstanceTokenId, type, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the kaleo logs before and after the current kaleo log in the ordered set where kaleoInstanceTokenId = &#63; and type = &#63;.
	 *
	 * @param kaleoLogId the primary key of the current kaleo log
	 * @param kaleoInstanceTokenId the kaleo instance token ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next kaleo log
	 * @throws NoSuchLogException if a kaleo log with the primary key could not be found
	 */
	@Override
	public KaleoLog[] findByKITI_T_PrevAndNext(
			long kaleoLogId, long kaleoInstanceTokenId, String type,
			OrderByComparator<KaleoLog> orderByComparator)
		throws NoSuchLogException {

		type = Objects.toString(type, "");

		KaleoLog kaleoLog = findByPrimaryKey(kaleoLogId);

		Session session = null;

		try {
			session = openSession();

			KaleoLog[] array = new KaleoLogImpl[3];

			array[0] = getByKITI_T_PrevAndNext(
				session, kaleoLog, kaleoInstanceTokenId, type,
				orderByComparator, true);

			array[1] = kaleoLog;

			array[2] = getByKITI_T_PrevAndNext(
				session, kaleoLog, kaleoInstanceTokenId, type,
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

	protected KaleoLog getByKITI_T_PrevAndNext(
		Session session, KaleoLog kaleoLog, long kaleoInstanceTokenId,
		String type, OrderByComparator<KaleoLog> orderByComparator,
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

		sb.append(_SQL_SELECT_KALEOLOG_WHERE);

		sb.append(_FINDER_COLUMN_KITI_T_KALEOINSTANCETOKENID_2);

		boolean bindType = false;

		if (type.isEmpty()) {
			sb.append(_FINDER_COLUMN_KITI_T_TYPE_3);
		}
		else {
			bindType = true;

			sb.append(_FINDER_COLUMN_KITI_T_TYPE_2);
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
			sb.append(KaleoLogModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(kaleoInstanceTokenId);

		if (bindType) {
			queryPos.add(type);
		}

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(kaleoLog)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<KaleoLog> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the kaleo logs where kaleoInstanceTokenId = &#63; and type = &#63; from the database.
	 *
	 * @param kaleoInstanceTokenId the kaleo instance token ID
	 * @param type the type
	 */
	@Override
	public void removeByKITI_T(long kaleoInstanceTokenId, String type) {
		for (KaleoLog kaleoLog :
				findByKITI_T(
					kaleoInstanceTokenId, type, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			remove(kaleoLog);
		}
	}

	/**
	 * Returns the number of kaleo logs where kaleoInstanceTokenId = &#63; and type = &#63;.
	 *
	 * @param kaleoInstanceTokenId the kaleo instance token ID
	 * @param type the type
	 * @return the number of matching kaleo logs
	 */
	@Override
	public int countByKITI_T(long kaleoInstanceTokenId, String type) {
		type = Objects.toString(type, "");

		FinderPath finderPath = _finderPathCountByKITI_T;

		Object[] finderArgs = new Object[] {kaleoInstanceTokenId, type};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_KALEOLOG_WHERE);

			sb.append(_FINDER_COLUMN_KITI_T_KALEOINSTANCETOKENID_2);

			boolean bindType = false;

			if (type.isEmpty()) {
				sb.append(_FINDER_COLUMN_KITI_T_TYPE_3);
			}
			else {
				bindType = true;

				sb.append(_FINDER_COLUMN_KITI_T_TYPE_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(kaleoInstanceTokenId);

				if (bindType) {
					queryPos.add(type);
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

	private static final String _FINDER_COLUMN_KITI_T_KALEOINSTANCETOKENID_2 =
		"kaleoLog.kaleoInstanceTokenId = ? AND ";

	private static final String _FINDER_COLUMN_KITI_T_TYPE_2 =
		"kaleoLog.type = ?";

	private static final String _FINDER_COLUMN_KITI_T_TYPE_3 =
		"(kaleoLog.type IS NULL OR kaleoLog.type = '')";

	private FinderPath _finderPathWithPaginationFindByKCN_KCPK_KITI_T;
	private FinderPath _finderPathWithoutPaginationFindByKCN_KCPK_KITI_T;
	private FinderPath _finderPathCountByKCN_KCPK_KITI_T;

	/**
	 * Returns all the kaleo logs where kaleoClassName = &#63; and kaleoClassPK = &#63; and kaleoInstanceTokenId = &#63; and type = &#63;.
	 *
	 * @param kaleoClassName the kaleo class name
	 * @param kaleoClassPK the kaleo class pk
	 * @param kaleoInstanceTokenId the kaleo instance token ID
	 * @param type the type
	 * @return the matching kaleo logs
	 */
	@Override
	public List<KaleoLog> findByKCN_KCPK_KITI_T(
		String kaleoClassName, long kaleoClassPK, long kaleoInstanceTokenId,
		String type) {

		return findByKCN_KCPK_KITI_T(
			kaleoClassName, kaleoClassPK, kaleoInstanceTokenId, type,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the kaleo logs where kaleoClassName = &#63; and kaleoClassPK = &#63; and kaleoInstanceTokenId = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoLogModelImpl</code>.
	 * </p>
	 *
	 * @param kaleoClassName the kaleo class name
	 * @param kaleoClassPK the kaleo class pk
	 * @param kaleoInstanceTokenId the kaleo instance token ID
	 * @param type the type
	 * @param start the lower bound of the range of kaleo logs
	 * @param end the upper bound of the range of kaleo logs (not inclusive)
	 * @return the range of matching kaleo logs
	 */
	@Override
	public List<KaleoLog> findByKCN_KCPK_KITI_T(
		String kaleoClassName, long kaleoClassPK, long kaleoInstanceTokenId,
		String type, int start, int end) {

		return findByKCN_KCPK_KITI_T(
			kaleoClassName, kaleoClassPK, kaleoInstanceTokenId, type, start,
			end, null);
	}

	/**
	 * Returns an ordered range of all the kaleo logs where kaleoClassName = &#63; and kaleoClassPK = &#63; and kaleoInstanceTokenId = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoLogModelImpl</code>.
	 * </p>
	 *
	 * @param kaleoClassName the kaleo class name
	 * @param kaleoClassPK the kaleo class pk
	 * @param kaleoInstanceTokenId the kaleo instance token ID
	 * @param type the type
	 * @param start the lower bound of the range of kaleo logs
	 * @param end the upper bound of the range of kaleo logs (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching kaleo logs
	 */
	@Override
	public List<KaleoLog> findByKCN_KCPK_KITI_T(
		String kaleoClassName, long kaleoClassPK, long kaleoInstanceTokenId,
		String type, int start, int end,
		OrderByComparator<KaleoLog> orderByComparator) {

		return findByKCN_KCPK_KITI_T(
			kaleoClassName, kaleoClassPK, kaleoInstanceTokenId, type, start,
			end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the kaleo logs where kaleoClassName = &#63; and kaleoClassPK = &#63; and kaleoInstanceTokenId = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoLogModelImpl</code>.
	 * </p>
	 *
	 * @param kaleoClassName the kaleo class name
	 * @param kaleoClassPK the kaleo class pk
	 * @param kaleoInstanceTokenId the kaleo instance token ID
	 * @param type the type
	 * @param start the lower bound of the range of kaleo logs
	 * @param end the upper bound of the range of kaleo logs (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching kaleo logs
	 */
	@Override
	public List<KaleoLog> findByKCN_KCPK_KITI_T(
		String kaleoClassName, long kaleoClassPK, long kaleoInstanceTokenId,
		String type, int start, int end,
		OrderByComparator<KaleoLog> orderByComparator, boolean useFinderCache) {

		kaleoClassName = Objects.toString(kaleoClassName, "");
		type = Objects.toString(type, "");

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByKCN_KCPK_KITI_T;
				finderArgs = new Object[] {
					kaleoClassName, kaleoClassPK, kaleoInstanceTokenId, type
				};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByKCN_KCPK_KITI_T;
			finderArgs = new Object[] {
				kaleoClassName, kaleoClassPK, kaleoInstanceTokenId, type, start,
				end, orderByComparator
			};
		}

		List<KaleoLog> list = null;

		if (useFinderCache) {
			list = (List<KaleoLog>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (KaleoLog kaleoLog : list) {
					if (!kaleoClassName.equals(kaleoLog.getKaleoClassName()) ||
						(kaleoClassPK != kaleoLog.getKaleoClassPK()) ||
						(kaleoInstanceTokenId !=
							kaleoLog.getKaleoInstanceTokenId()) ||
						!type.equals(kaleoLog.getType())) {

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

			sb.append(_SQL_SELECT_KALEOLOG_WHERE);

			boolean bindKaleoClassName = false;

			if (kaleoClassName.isEmpty()) {
				sb.append(_FINDER_COLUMN_KCN_KCPK_KITI_T_KALEOCLASSNAME_3);
			}
			else {
				bindKaleoClassName = true;

				sb.append(_FINDER_COLUMN_KCN_KCPK_KITI_T_KALEOCLASSNAME_2);
			}

			sb.append(_FINDER_COLUMN_KCN_KCPK_KITI_T_KALEOCLASSPK_2);

			sb.append(_FINDER_COLUMN_KCN_KCPK_KITI_T_KALEOINSTANCETOKENID_2);

			boolean bindType = false;

			if (type.isEmpty()) {
				sb.append(_FINDER_COLUMN_KCN_KCPK_KITI_T_TYPE_3);
			}
			else {
				bindType = true;

				sb.append(_FINDER_COLUMN_KCN_KCPK_KITI_T_TYPE_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(KaleoLogModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindKaleoClassName) {
					queryPos.add(kaleoClassName);
				}

				queryPos.add(kaleoClassPK);

				queryPos.add(kaleoInstanceTokenId);

				if (bindType) {
					queryPos.add(type);
				}

				list = (List<KaleoLog>)QueryUtil.list(
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
	 * Returns the first kaleo log in the ordered set where kaleoClassName = &#63; and kaleoClassPK = &#63; and kaleoInstanceTokenId = &#63; and type = &#63;.
	 *
	 * @param kaleoClassName the kaleo class name
	 * @param kaleoClassPK the kaleo class pk
	 * @param kaleoInstanceTokenId the kaleo instance token ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching kaleo log
	 * @throws NoSuchLogException if a matching kaleo log could not be found
	 */
	@Override
	public KaleoLog findByKCN_KCPK_KITI_T_First(
			String kaleoClassName, long kaleoClassPK, long kaleoInstanceTokenId,
			String type, OrderByComparator<KaleoLog> orderByComparator)
		throws NoSuchLogException {

		KaleoLog kaleoLog = fetchByKCN_KCPK_KITI_T_First(
			kaleoClassName, kaleoClassPK, kaleoInstanceTokenId, type,
			orderByComparator);

		if (kaleoLog != null) {
			return kaleoLog;
		}

		StringBundler sb = new StringBundler(10);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("kaleoClassName=");
		sb.append(kaleoClassName);

		sb.append(", kaleoClassPK=");
		sb.append(kaleoClassPK);

		sb.append(", kaleoInstanceTokenId=");
		sb.append(kaleoInstanceTokenId);

		sb.append(", type=");
		sb.append(type);

		sb.append("}");

		throw new NoSuchLogException(sb.toString());
	}

	/**
	 * Returns the first kaleo log in the ordered set where kaleoClassName = &#63; and kaleoClassPK = &#63; and kaleoInstanceTokenId = &#63; and type = &#63;.
	 *
	 * @param kaleoClassName the kaleo class name
	 * @param kaleoClassPK the kaleo class pk
	 * @param kaleoInstanceTokenId the kaleo instance token ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching kaleo log, or <code>null</code> if a matching kaleo log could not be found
	 */
	@Override
	public KaleoLog fetchByKCN_KCPK_KITI_T_First(
		String kaleoClassName, long kaleoClassPK, long kaleoInstanceTokenId,
		String type, OrderByComparator<KaleoLog> orderByComparator) {

		List<KaleoLog> list = findByKCN_KCPK_KITI_T(
			kaleoClassName, kaleoClassPK, kaleoInstanceTokenId, type, 0, 1,
			orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last kaleo log in the ordered set where kaleoClassName = &#63; and kaleoClassPK = &#63; and kaleoInstanceTokenId = &#63; and type = &#63;.
	 *
	 * @param kaleoClassName the kaleo class name
	 * @param kaleoClassPK the kaleo class pk
	 * @param kaleoInstanceTokenId the kaleo instance token ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching kaleo log
	 * @throws NoSuchLogException if a matching kaleo log could not be found
	 */
	@Override
	public KaleoLog findByKCN_KCPK_KITI_T_Last(
			String kaleoClassName, long kaleoClassPK, long kaleoInstanceTokenId,
			String type, OrderByComparator<KaleoLog> orderByComparator)
		throws NoSuchLogException {

		KaleoLog kaleoLog = fetchByKCN_KCPK_KITI_T_Last(
			kaleoClassName, kaleoClassPK, kaleoInstanceTokenId, type,
			orderByComparator);

		if (kaleoLog != null) {
			return kaleoLog;
		}

		StringBundler sb = new StringBundler(10);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("kaleoClassName=");
		sb.append(kaleoClassName);

		sb.append(", kaleoClassPK=");
		sb.append(kaleoClassPK);

		sb.append(", kaleoInstanceTokenId=");
		sb.append(kaleoInstanceTokenId);

		sb.append(", type=");
		sb.append(type);

		sb.append("}");

		throw new NoSuchLogException(sb.toString());
	}

	/**
	 * Returns the last kaleo log in the ordered set where kaleoClassName = &#63; and kaleoClassPK = &#63; and kaleoInstanceTokenId = &#63; and type = &#63;.
	 *
	 * @param kaleoClassName the kaleo class name
	 * @param kaleoClassPK the kaleo class pk
	 * @param kaleoInstanceTokenId the kaleo instance token ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching kaleo log, or <code>null</code> if a matching kaleo log could not be found
	 */
	@Override
	public KaleoLog fetchByKCN_KCPK_KITI_T_Last(
		String kaleoClassName, long kaleoClassPK, long kaleoInstanceTokenId,
		String type, OrderByComparator<KaleoLog> orderByComparator) {

		int count = countByKCN_KCPK_KITI_T(
			kaleoClassName, kaleoClassPK, kaleoInstanceTokenId, type);

		if (count == 0) {
			return null;
		}

		List<KaleoLog> list = findByKCN_KCPK_KITI_T(
			kaleoClassName, kaleoClassPK, kaleoInstanceTokenId, type, count - 1,
			count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the kaleo logs before and after the current kaleo log in the ordered set where kaleoClassName = &#63; and kaleoClassPK = &#63; and kaleoInstanceTokenId = &#63; and type = &#63;.
	 *
	 * @param kaleoLogId the primary key of the current kaleo log
	 * @param kaleoClassName the kaleo class name
	 * @param kaleoClassPK the kaleo class pk
	 * @param kaleoInstanceTokenId the kaleo instance token ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next kaleo log
	 * @throws NoSuchLogException if a kaleo log with the primary key could not be found
	 */
	@Override
	public KaleoLog[] findByKCN_KCPK_KITI_T_PrevAndNext(
			long kaleoLogId, String kaleoClassName, long kaleoClassPK,
			long kaleoInstanceTokenId, String type,
			OrderByComparator<KaleoLog> orderByComparator)
		throws NoSuchLogException {

		kaleoClassName = Objects.toString(kaleoClassName, "");
		type = Objects.toString(type, "");

		KaleoLog kaleoLog = findByPrimaryKey(kaleoLogId);

		Session session = null;

		try {
			session = openSession();

			KaleoLog[] array = new KaleoLogImpl[3];

			array[0] = getByKCN_KCPK_KITI_T_PrevAndNext(
				session, kaleoLog, kaleoClassName, kaleoClassPK,
				kaleoInstanceTokenId, type, orderByComparator, true);

			array[1] = kaleoLog;

			array[2] = getByKCN_KCPK_KITI_T_PrevAndNext(
				session, kaleoLog, kaleoClassName, kaleoClassPK,
				kaleoInstanceTokenId, type, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected KaleoLog getByKCN_KCPK_KITI_T_PrevAndNext(
		Session session, KaleoLog kaleoLog, String kaleoClassName,
		long kaleoClassPK, long kaleoInstanceTokenId, String type,
		OrderByComparator<KaleoLog> orderByComparator, boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				7 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(6);
		}

		sb.append(_SQL_SELECT_KALEOLOG_WHERE);

		boolean bindKaleoClassName = false;

		if (kaleoClassName.isEmpty()) {
			sb.append(_FINDER_COLUMN_KCN_KCPK_KITI_T_KALEOCLASSNAME_3);
		}
		else {
			bindKaleoClassName = true;

			sb.append(_FINDER_COLUMN_KCN_KCPK_KITI_T_KALEOCLASSNAME_2);
		}

		sb.append(_FINDER_COLUMN_KCN_KCPK_KITI_T_KALEOCLASSPK_2);

		sb.append(_FINDER_COLUMN_KCN_KCPK_KITI_T_KALEOINSTANCETOKENID_2);

		boolean bindType = false;

		if (type.isEmpty()) {
			sb.append(_FINDER_COLUMN_KCN_KCPK_KITI_T_TYPE_3);
		}
		else {
			bindType = true;

			sb.append(_FINDER_COLUMN_KCN_KCPK_KITI_T_TYPE_2);
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
			sb.append(KaleoLogModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		if (bindKaleoClassName) {
			queryPos.add(kaleoClassName);
		}

		queryPos.add(kaleoClassPK);

		queryPos.add(kaleoInstanceTokenId);

		if (bindType) {
			queryPos.add(type);
		}

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(kaleoLog)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<KaleoLog> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the kaleo logs where kaleoClassName = &#63; and kaleoClassPK = &#63; and kaleoInstanceTokenId = &#63; and type = &#63; from the database.
	 *
	 * @param kaleoClassName the kaleo class name
	 * @param kaleoClassPK the kaleo class pk
	 * @param kaleoInstanceTokenId the kaleo instance token ID
	 * @param type the type
	 */
	@Override
	public void removeByKCN_KCPK_KITI_T(
		String kaleoClassName, long kaleoClassPK, long kaleoInstanceTokenId,
		String type) {

		for (KaleoLog kaleoLog :
				findByKCN_KCPK_KITI_T(
					kaleoClassName, kaleoClassPK, kaleoInstanceTokenId, type,
					QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(kaleoLog);
		}
	}

	/**
	 * Returns the number of kaleo logs where kaleoClassName = &#63; and kaleoClassPK = &#63; and kaleoInstanceTokenId = &#63; and type = &#63;.
	 *
	 * @param kaleoClassName the kaleo class name
	 * @param kaleoClassPK the kaleo class pk
	 * @param kaleoInstanceTokenId the kaleo instance token ID
	 * @param type the type
	 * @return the number of matching kaleo logs
	 */
	@Override
	public int countByKCN_KCPK_KITI_T(
		String kaleoClassName, long kaleoClassPK, long kaleoInstanceTokenId,
		String type) {

		kaleoClassName = Objects.toString(kaleoClassName, "");
		type = Objects.toString(type, "");

		FinderPath finderPath = _finderPathCountByKCN_KCPK_KITI_T;

		Object[] finderArgs = new Object[] {
			kaleoClassName, kaleoClassPK, kaleoInstanceTokenId, type
		};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(5);

			sb.append(_SQL_COUNT_KALEOLOG_WHERE);

			boolean bindKaleoClassName = false;

			if (kaleoClassName.isEmpty()) {
				sb.append(_FINDER_COLUMN_KCN_KCPK_KITI_T_KALEOCLASSNAME_3);
			}
			else {
				bindKaleoClassName = true;

				sb.append(_FINDER_COLUMN_KCN_KCPK_KITI_T_KALEOCLASSNAME_2);
			}

			sb.append(_FINDER_COLUMN_KCN_KCPK_KITI_T_KALEOCLASSPK_2);

			sb.append(_FINDER_COLUMN_KCN_KCPK_KITI_T_KALEOINSTANCETOKENID_2);

			boolean bindType = false;

			if (type.isEmpty()) {
				sb.append(_FINDER_COLUMN_KCN_KCPK_KITI_T_TYPE_3);
			}
			else {
				bindType = true;

				sb.append(_FINDER_COLUMN_KCN_KCPK_KITI_T_TYPE_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindKaleoClassName) {
					queryPos.add(kaleoClassName);
				}

				queryPos.add(kaleoClassPK);

				queryPos.add(kaleoInstanceTokenId);

				if (bindType) {
					queryPos.add(type);
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
		_FINDER_COLUMN_KCN_KCPK_KITI_T_KALEOCLASSNAME_2 =
			"kaleoLog.kaleoClassName = ? AND ";

	private static final String
		_FINDER_COLUMN_KCN_KCPK_KITI_T_KALEOCLASSNAME_3 =
			"(kaleoLog.kaleoClassName IS NULL OR kaleoLog.kaleoClassName = '') AND ";

	private static final String _FINDER_COLUMN_KCN_KCPK_KITI_T_KALEOCLASSPK_2 =
		"kaleoLog.kaleoClassPK = ? AND ";

	private static final String
		_FINDER_COLUMN_KCN_KCPK_KITI_T_KALEOINSTANCETOKENID_2 =
			"kaleoLog.kaleoInstanceTokenId = ? AND ";

	private static final String _FINDER_COLUMN_KCN_KCPK_KITI_T_TYPE_2 =
		"kaleoLog.type = ?";

	private static final String _FINDER_COLUMN_KCN_KCPK_KITI_T_TYPE_3 =
		"(kaleoLog.type IS NULL OR kaleoLog.type = '')";

	public KaleoLogPersistenceImpl() {
		Map<String, String> dbColumnNames = new HashMap<String, String>();

		dbColumnNames.put("type", "type_");
		dbColumnNames.put("comment", "comment_");

		setDBColumnNames(dbColumnNames);

		setModelClass(KaleoLog.class);

		setModelImplClass(KaleoLogImpl.class);
		setModelPKClass(long.class);

		setTable(KaleoLogTable.INSTANCE);
	}

	/**
	 * Caches the kaleo log in the entity cache if it is enabled.
	 *
	 * @param kaleoLog the kaleo log
	 */
	@Override
	public void cacheResult(KaleoLog kaleoLog) {
		entityCache.putResult(
			KaleoLogImpl.class, kaleoLog.getPrimaryKey(), kaleoLog);
	}

	private int _valueObjectFinderCacheListThreshold;

	/**
	 * Caches the kaleo logs in the entity cache if it is enabled.
	 *
	 * @param kaleoLogs the kaleo logs
	 */
	@Override
	public void cacheResult(List<KaleoLog> kaleoLogs) {
		if ((_valueObjectFinderCacheListThreshold == 0) ||
			((_valueObjectFinderCacheListThreshold > 0) &&
			 (kaleoLogs.size() > _valueObjectFinderCacheListThreshold))) {

			return;
		}

		for (KaleoLog kaleoLog : kaleoLogs) {
			if (entityCache.getResult(
					KaleoLogImpl.class, kaleoLog.getPrimaryKey()) == null) {

				cacheResult(kaleoLog);
			}
		}
	}

	/**
	 * Clears the cache for all kaleo logs.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(KaleoLogImpl.class);

		finderCache.clearCache(KaleoLogImpl.class);
	}

	/**
	 * Clears the cache for the kaleo log.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(KaleoLog kaleoLog) {
		entityCache.removeResult(KaleoLogImpl.class, kaleoLog);
	}

	@Override
	public void clearCache(List<KaleoLog> kaleoLogs) {
		for (KaleoLog kaleoLog : kaleoLogs) {
			entityCache.removeResult(KaleoLogImpl.class, kaleoLog);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(KaleoLogImpl.class);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(KaleoLogImpl.class, primaryKey);
		}
	}

	/**
	 * Creates a new kaleo log with the primary key. Does not add the kaleo log to the database.
	 *
	 * @param kaleoLogId the primary key for the new kaleo log
	 * @return the new kaleo log
	 */
	@Override
	public KaleoLog create(long kaleoLogId) {
		KaleoLog kaleoLog = new KaleoLogImpl();

		kaleoLog.setNew(true);
		kaleoLog.setPrimaryKey(kaleoLogId);

		kaleoLog.setCompanyId(CompanyThreadLocal.getCompanyId());

		return kaleoLog;
	}

	/**
	 * Removes the kaleo log with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param kaleoLogId the primary key of the kaleo log
	 * @return the kaleo log that was removed
	 * @throws NoSuchLogException if a kaleo log with the primary key could not be found
	 */
	@Override
	public KaleoLog remove(long kaleoLogId) throws NoSuchLogException {
		return remove((Serializable)kaleoLogId);
	}

	/**
	 * Removes the kaleo log with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the kaleo log
	 * @return the kaleo log that was removed
	 * @throws NoSuchLogException if a kaleo log with the primary key could not be found
	 */
	@Override
	public KaleoLog remove(Serializable primaryKey) throws NoSuchLogException {
		Session session = null;

		try {
			session = openSession();

			KaleoLog kaleoLog = (KaleoLog)session.get(
				KaleoLogImpl.class, primaryKey);

			if (kaleoLog == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchLogException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(kaleoLog);
		}
		catch (NoSuchLogException noSuchEntityException) {
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
	protected KaleoLog removeImpl(KaleoLog kaleoLog) {
		Session session = null;

		try {
			session = openSession();

			if (!session.contains(kaleoLog)) {
				kaleoLog = (KaleoLog)session.get(
					KaleoLogImpl.class, kaleoLog.getPrimaryKeyObj());
			}

			if (kaleoLog != null) {
				session.delete(kaleoLog);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (kaleoLog != null) {
			clearCache(kaleoLog);
		}

		return kaleoLog;
	}

	@Override
	public KaleoLog updateImpl(KaleoLog kaleoLog) {
		boolean isNew = kaleoLog.isNew();

		if (!(kaleoLog instanceof KaleoLogModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(kaleoLog.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(kaleoLog);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in kaleoLog proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom KaleoLog implementation " +
					kaleoLog.getClass());
		}

		KaleoLogModelImpl kaleoLogModelImpl = (KaleoLogModelImpl)kaleoLog;

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Date date = new Date();

		if (isNew && (kaleoLog.getCreateDate() == null)) {
			if (serviceContext == null) {
				kaleoLog.setCreateDate(date);
			}
			else {
				kaleoLog.setCreateDate(serviceContext.getCreateDate(date));
			}
		}

		if (!kaleoLogModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				kaleoLog.setModifiedDate(date);
			}
			else {
				kaleoLog.setModifiedDate(serviceContext.getModifiedDate(date));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (isNew) {
				session.save(kaleoLog);
			}
			else {
				kaleoLog = (KaleoLog)session.merge(kaleoLog);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		entityCache.putResult(
			KaleoLogImpl.class, kaleoLogModelImpl, false, true);

		if (isNew) {
			kaleoLog.setNew(false);
		}

		kaleoLog.resetOriginalValues();

		return kaleoLog;
	}

	/**
	 * Returns the kaleo log with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the kaleo log
	 * @return the kaleo log
	 * @throws NoSuchLogException if a kaleo log with the primary key could not be found
	 */
	@Override
	public KaleoLog findByPrimaryKey(Serializable primaryKey)
		throws NoSuchLogException {

		KaleoLog kaleoLog = fetchByPrimaryKey(primaryKey);

		if (kaleoLog == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchLogException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return kaleoLog;
	}

	/**
	 * Returns the kaleo log with the primary key or throws a <code>NoSuchLogException</code> if it could not be found.
	 *
	 * @param kaleoLogId the primary key of the kaleo log
	 * @return the kaleo log
	 * @throws NoSuchLogException if a kaleo log with the primary key could not be found
	 */
	@Override
	public KaleoLog findByPrimaryKey(long kaleoLogId)
		throws NoSuchLogException {

		return findByPrimaryKey((Serializable)kaleoLogId);
	}

	/**
	 * Returns the kaleo log with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param kaleoLogId the primary key of the kaleo log
	 * @return the kaleo log, or <code>null</code> if a kaleo log with the primary key could not be found
	 */
	@Override
	public KaleoLog fetchByPrimaryKey(long kaleoLogId) {
		return fetchByPrimaryKey((Serializable)kaleoLogId);
	}

	/**
	 * Returns all the kaleo logs.
	 *
	 * @return the kaleo logs
	 */
	@Override
	public List<KaleoLog> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the kaleo logs.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoLogModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of kaleo logs
	 * @param end the upper bound of the range of kaleo logs (not inclusive)
	 * @return the range of kaleo logs
	 */
	@Override
	public List<KaleoLog> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the kaleo logs.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoLogModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of kaleo logs
	 * @param end the upper bound of the range of kaleo logs (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of kaleo logs
	 */
	@Override
	public List<KaleoLog> findAll(
		int start, int end, OrderByComparator<KaleoLog> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the kaleo logs.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoLogModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of kaleo logs
	 * @param end the upper bound of the range of kaleo logs (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of kaleo logs
	 */
	@Override
	public List<KaleoLog> findAll(
		int start, int end, OrderByComparator<KaleoLog> orderByComparator,
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

		List<KaleoLog> list = null;

		if (useFinderCache) {
			list = (List<KaleoLog>)finderCache.getResult(
				finderPath, finderArgs);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_KALEOLOG);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_KALEOLOG;

				sql = sql.concat(KaleoLogModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<KaleoLog>)QueryUtil.list(
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
	 * Removes all the kaleo logs from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (KaleoLog kaleoLog : findAll()) {
			remove(kaleoLog);
		}
	}

	/**
	 * Returns the number of kaleo logs.
	 *
	 * @return the number of kaleo logs
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(
			_finderPathCountAll, FINDER_ARGS_EMPTY);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(_SQL_COUNT_KALEOLOG);

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
		return "kaleoLogId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_KALEOLOG;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return KaleoLogModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the kaleo log persistence.
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

		_finderPathWithPaginationFindByKaleoDefinitionVersionId =
			new FinderPath(
				FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
				"findByKaleoDefinitionVersionId",
				new String[] {
					Long.class.getName(), Integer.class.getName(),
					Integer.class.getName(), OrderByComparator.class.getName()
				},
				new String[] {"kaleoDefinitionVersionId"}, true);

		_finderPathWithoutPaginationFindByKaleoDefinitionVersionId =
			new FinderPath(
				FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
				"findByKaleoDefinitionVersionId",
				new String[] {Long.class.getName()},
				new String[] {"kaleoDefinitionVersionId"}, true);

		_finderPathCountByKaleoDefinitionVersionId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByKaleoDefinitionVersionId",
			new String[] {Long.class.getName()},
			new String[] {"kaleoDefinitionVersionId"}, false);

		_finderPathWithPaginationFindByKaleoInstanceId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByKaleoInstanceId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"kaleoInstanceId"}, true);

		_finderPathWithoutPaginationFindByKaleoInstanceId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByKaleoInstanceId",
			new String[] {Long.class.getName()},
			new String[] {"kaleoInstanceId"}, true);

		_finderPathCountByKaleoInstanceId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByKaleoInstanceId",
			new String[] {Long.class.getName()},
			new String[] {"kaleoInstanceId"}, false);

		_finderPathWithPaginationFindByKaleoTaskInstanceTokenId =
			new FinderPath(
				FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
				"findByKaleoTaskInstanceTokenId",
				new String[] {
					Long.class.getName(), Integer.class.getName(),
					Integer.class.getName(), OrderByComparator.class.getName()
				},
				new String[] {"kaleoTaskInstanceTokenId"}, true);

		_finderPathWithoutPaginationFindByKaleoTaskInstanceTokenId =
			new FinderPath(
				FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
				"findByKaleoTaskInstanceTokenId",
				new String[] {Long.class.getName()},
				new String[] {"kaleoTaskInstanceTokenId"}, true);

		_finderPathCountByKaleoTaskInstanceTokenId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByKaleoTaskInstanceTokenId",
			new String[] {Long.class.getName()},
			new String[] {"kaleoTaskInstanceTokenId"}, false);

		_finderPathWithPaginationFindByKITI_T = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByKITI_T",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {"kaleoInstanceTokenId", "type_"}, true);

		_finderPathWithoutPaginationFindByKITI_T = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByKITI_T",
			new String[] {Long.class.getName(), String.class.getName()},
			new String[] {"kaleoInstanceTokenId", "type_"}, true);

		_finderPathCountByKITI_T = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByKITI_T",
			new String[] {Long.class.getName(), String.class.getName()},
			new String[] {"kaleoInstanceTokenId", "type_"}, false);

		_finderPathWithPaginationFindByKCN_KCPK_KITI_T = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByKCN_KCPK_KITI_T",
			new String[] {
				String.class.getName(), Long.class.getName(),
				Long.class.getName(), String.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {
				"kaleoClassName", "kaleoClassPK", "kaleoInstanceTokenId",
				"type_"
			},
			true);

		_finderPathWithoutPaginationFindByKCN_KCPK_KITI_T = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByKCN_KCPK_KITI_T",
			new String[] {
				String.class.getName(), Long.class.getName(),
				Long.class.getName(), String.class.getName()
			},
			new String[] {
				"kaleoClassName", "kaleoClassPK", "kaleoInstanceTokenId",
				"type_"
			},
			true);

		_finderPathCountByKCN_KCPK_KITI_T = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByKCN_KCPK_KITI_T",
			new String[] {
				String.class.getName(), Long.class.getName(),
				Long.class.getName(), String.class.getName()
			},
			new String[] {
				"kaleoClassName", "kaleoClassPK", "kaleoInstanceTokenId",
				"type_"
			},
			false);
	}

	@Deactivate
	public void deactivate() {
		entityCache.removeCache(KaleoLogImpl.class.getName());
	}

	@Override
	@Reference(
		target = KaleoPersistenceConstants.SERVICE_CONFIGURATION_FILTER,
		unbind = "-"
	)
	public void setConfiguration(Configuration configuration) {
	}

	@Override
	@Reference(
		target = KaleoPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setDataSource(DataSource dataSource) {
		super.setDataSource(dataSource);
	}

	@Override
	@Reference(
		target = KaleoPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setSessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	@Reference
	protected EntityCache entityCache;

	@Reference
	protected FinderCache finderCache;

	private static final String _SQL_SELECT_KALEOLOG =
		"SELECT kaleoLog FROM KaleoLog kaleoLog";

	private static final String _SQL_SELECT_KALEOLOG_WHERE =
		"SELECT kaleoLog FROM KaleoLog kaleoLog WHERE ";

	private static final String _SQL_COUNT_KALEOLOG =
		"SELECT COUNT(kaleoLog) FROM KaleoLog kaleoLog";

	private static final String _SQL_COUNT_KALEOLOG_WHERE =
		"SELECT COUNT(kaleoLog) FROM KaleoLog kaleoLog WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS = "kaleoLog.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No KaleoLog exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No KaleoLog exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		KaleoLogPersistenceImpl.class);

	private static final Set<String> _badColumnNames = SetUtil.fromArray(
		new String[] {"type", "comment"});

	@Override
	protected FinderCache getFinderCache() {
		return finderCache;
	}

	@Reference
	private KaleoLogModelArgumentsResolver _kaleoLogModelArgumentsResolver;

}