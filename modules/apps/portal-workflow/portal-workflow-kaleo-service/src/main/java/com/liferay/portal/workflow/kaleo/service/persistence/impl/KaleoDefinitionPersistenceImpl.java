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
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.workflow.kaleo.exception.NoSuchDefinitionException;
import com.liferay.portal.workflow.kaleo.model.KaleoDefinition;
import com.liferay.portal.workflow.kaleo.model.KaleoDefinitionTable;
import com.liferay.portal.workflow.kaleo.model.impl.KaleoDefinitionImpl;
import com.liferay.portal.workflow.kaleo.model.impl.KaleoDefinitionModelImpl;
import com.liferay.portal.workflow.kaleo.service.persistence.KaleoDefinitionPersistence;
import com.liferay.portal.workflow.kaleo.service.persistence.impl.constants.KaleoPersistenceConstants;

import java.io.Serializable;

import java.lang.reflect.InvocationHandler;

import java.util.Collections;
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
 * The persistence implementation for the kaleo definition service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
@Component(service = {KaleoDefinitionPersistence.class, BasePersistence.class})
public class KaleoDefinitionPersistenceImpl
	extends BasePersistenceImpl<KaleoDefinition>
	implements KaleoDefinitionPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>KaleoDefinitionUtil</code> to access the kaleo definition persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		KaleoDefinitionImpl.class.getName();

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
	 * Returns all the kaleo definitions where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching kaleo definitions
	 */
	@Override
	public List<KaleoDefinition> findByCompanyId(long companyId) {
		return findByCompanyId(
			companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the kaleo definitions where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoDefinitionModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of kaleo definitions
	 * @param end the upper bound of the range of kaleo definitions (not inclusive)
	 * @return the range of matching kaleo definitions
	 */
	@Override
	public List<KaleoDefinition> findByCompanyId(
		long companyId, int start, int end) {

		return findByCompanyId(companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the kaleo definitions where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoDefinitionModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of kaleo definitions
	 * @param end the upper bound of the range of kaleo definitions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching kaleo definitions
	 */
	@Override
	public List<KaleoDefinition> findByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<KaleoDefinition> orderByComparator) {

		return findByCompanyId(companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the kaleo definitions where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoDefinitionModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of kaleo definitions
	 * @param end the upper bound of the range of kaleo definitions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching kaleo definitions
	 */
	@Override
	public List<KaleoDefinition> findByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<KaleoDefinition> orderByComparator,
		boolean useFinderCache) {

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

		List<KaleoDefinition> list = null;

		if (useFinderCache) {
			list = (List<KaleoDefinition>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (KaleoDefinition kaleoDefinition : list) {
					if (companyId != kaleoDefinition.getCompanyId()) {
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

			sb.append(_SQL_SELECT_KALEODEFINITION_WHERE);

			sb.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(KaleoDefinitionModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(companyId);

				list = (List<KaleoDefinition>)QueryUtil.list(
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
	 * Returns the first kaleo definition in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching kaleo definition
	 * @throws NoSuchDefinitionException if a matching kaleo definition could not be found
	 */
	@Override
	public KaleoDefinition findByCompanyId_First(
			long companyId,
			OrderByComparator<KaleoDefinition> orderByComparator)
		throws NoSuchDefinitionException {

		KaleoDefinition kaleoDefinition = fetchByCompanyId_First(
			companyId, orderByComparator);

		if (kaleoDefinition != null) {
			return kaleoDefinition;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchDefinitionException(sb.toString());
	}

	/**
	 * Returns the first kaleo definition in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching kaleo definition, or <code>null</code> if a matching kaleo definition could not be found
	 */
	@Override
	public KaleoDefinition fetchByCompanyId_First(
		long companyId, OrderByComparator<KaleoDefinition> orderByComparator) {

		List<KaleoDefinition> list = findByCompanyId(
			companyId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last kaleo definition in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching kaleo definition
	 * @throws NoSuchDefinitionException if a matching kaleo definition could not be found
	 */
	@Override
	public KaleoDefinition findByCompanyId_Last(
			long companyId,
			OrderByComparator<KaleoDefinition> orderByComparator)
		throws NoSuchDefinitionException {

		KaleoDefinition kaleoDefinition = fetchByCompanyId_Last(
			companyId, orderByComparator);

		if (kaleoDefinition != null) {
			return kaleoDefinition;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchDefinitionException(sb.toString());
	}

	/**
	 * Returns the last kaleo definition in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching kaleo definition, or <code>null</code> if a matching kaleo definition could not be found
	 */
	@Override
	public KaleoDefinition fetchByCompanyId_Last(
		long companyId, OrderByComparator<KaleoDefinition> orderByComparator) {

		int count = countByCompanyId(companyId);

		if (count == 0) {
			return null;
		}

		List<KaleoDefinition> list = findByCompanyId(
			companyId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the kaleo definitions before and after the current kaleo definition in the ordered set where companyId = &#63;.
	 *
	 * @param kaleoDefinitionId the primary key of the current kaleo definition
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next kaleo definition
	 * @throws NoSuchDefinitionException if a kaleo definition with the primary key could not be found
	 */
	@Override
	public KaleoDefinition[] findByCompanyId_PrevAndNext(
			long kaleoDefinitionId, long companyId,
			OrderByComparator<KaleoDefinition> orderByComparator)
		throws NoSuchDefinitionException {

		KaleoDefinition kaleoDefinition = findByPrimaryKey(kaleoDefinitionId);

		Session session = null;

		try {
			session = openSession();

			KaleoDefinition[] array = new KaleoDefinitionImpl[3];

			array[0] = getByCompanyId_PrevAndNext(
				session, kaleoDefinition, companyId, orderByComparator, true);

			array[1] = kaleoDefinition;

			array[2] = getByCompanyId_PrevAndNext(
				session, kaleoDefinition, companyId, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected KaleoDefinition getByCompanyId_PrevAndNext(
		Session session, KaleoDefinition kaleoDefinition, long companyId,
		OrderByComparator<KaleoDefinition> orderByComparator,
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

		sb.append(_SQL_SELECT_KALEODEFINITION_WHERE);

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
			sb.append(KaleoDefinitionModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(companyId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						kaleoDefinition)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<KaleoDefinition> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the kaleo definitions where companyId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 */
	@Override
	public void removeByCompanyId(long companyId) {
		for (KaleoDefinition kaleoDefinition :
				findByCompanyId(
					companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(kaleoDefinition);
		}
	}

	/**
	 * Returns the number of kaleo definitions where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching kaleo definitions
	 */
	@Override
	public int countByCompanyId(long companyId) {
		FinderPath finderPath = _finderPathCountByCompanyId;

		Object[] finderArgs = new Object[] {companyId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_KALEODEFINITION_WHERE);

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
		"kaleoDefinition.companyId = ?";

	private FinderPath _finderPathFetchByC_N;
	private FinderPath _finderPathCountByC_N;

	/**
	 * Returns the kaleo definition where companyId = &#63; and name = &#63; or throws a <code>NoSuchDefinitionException</code> if it could not be found.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @return the matching kaleo definition
	 * @throws NoSuchDefinitionException if a matching kaleo definition could not be found
	 */
	@Override
	public KaleoDefinition findByC_N(long companyId, String name)
		throws NoSuchDefinitionException {

		KaleoDefinition kaleoDefinition = fetchByC_N(companyId, name);

		if (kaleoDefinition == null) {
			StringBundler sb = new StringBundler(6);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("companyId=");
			sb.append(companyId);

			sb.append(", name=");
			sb.append(name);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchDefinitionException(sb.toString());
		}

		return kaleoDefinition;
	}

	/**
	 * Returns the kaleo definition where companyId = &#63; and name = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @return the matching kaleo definition, or <code>null</code> if a matching kaleo definition could not be found
	 */
	@Override
	public KaleoDefinition fetchByC_N(long companyId, String name) {
		return fetchByC_N(companyId, name, true);
	}

	/**
	 * Returns the kaleo definition where companyId = &#63; and name = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching kaleo definition, or <code>null</code> if a matching kaleo definition could not be found
	 */
	@Override
	public KaleoDefinition fetchByC_N(
		long companyId, String name, boolean useFinderCache) {

		name = Objects.toString(name, "");

		Object[] finderArgs = null;

		if (useFinderCache) {
			finderArgs = new Object[] {companyId, name};
		}

		Object result = null;

		if (useFinderCache) {
			result = finderCache.getResult(_finderPathFetchByC_N, finderArgs);
		}

		if (result instanceof KaleoDefinition) {
			KaleoDefinition kaleoDefinition = (KaleoDefinition)result;

			if ((companyId != kaleoDefinition.getCompanyId()) ||
				!Objects.equals(name, kaleoDefinition.getName())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_SELECT_KALEODEFINITION_WHERE);

			sb.append(_FINDER_COLUMN_C_N_COMPANYID_2);

			boolean bindName = false;

			if (name.isEmpty()) {
				sb.append(_FINDER_COLUMN_C_N_NAME_3);
			}
			else {
				bindName = true;

				sb.append(_FINDER_COLUMN_C_N_NAME_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(companyId);

				if (bindName) {
					queryPos.add(name);
				}

				List<KaleoDefinition> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache) {
						finderCache.putResult(
							_finderPathFetchByC_N, finderArgs, list);
					}
				}
				else {
					if (list.size() > 1) {
						Collections.sort(list, Collections.reverseOrder());

						if (_log.isWarnEnabled()) {
							if (!useFinderCache) {
								finderArgs = new Object[] {companyId, name};
							}

							_log.warn(
								"KaleoDefinitionPersistenceImpl.fetchByC_N(long, String, boolean) with parameters (" +
									StringUtil.merge(finderArgs) +
										") yields a result set with more than 1 result. This violates the logical unique restriction. There is no order guarantee on which result is returned by this finder.");
						}
					}

					KaleoDefinition kaleoDefinition = list.get(0);

					result = kaleoDefinition;

					cacheResult(kaleoDefinition);
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
			return (KaleoDefinition)result;
		}
	}

	/**
	 * Removes the kaleo definition where companyId = &#63; and name = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @return the kaleo definition that was removed
	 */
	@Override
	public KaleoDefinition removeByC_N(long companyId, String name)
		throws NoSuchDefinitionException {

		KaleoDefinition kaleoDefinition = findByC_N(companyId, name);

		return remove(kaleoDefinition);
	}

	/**
	 * Returns the number of kaleo definitions where companyId = &#63; and name = &#63;.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @return the number of matching kaleo definitions
	 */
	@Override
	public int countByC_N(long companyId, String name) {
		name = Objects.toString(name, "");

		FinderPath finderPath = _finderPathCountByC_N;

		Object[] finderArgs = new Object[] {companyId, name};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_KALEODEFINITION_WHERE);

			sb.append(_FINDER_COLUMN_C_N_COMPANYID_2);

			boolean bindName = false;

			if (name.isEmpty()) {
				sb.append(_FINDER_COLUMN_C_N_NAME_3);
			}
			else {
				bindName = true;

				sb.append(_FINDER_COLUMN_C_N_NAME_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(companyId);

				if (bindName) {
					queryPos.add(name);
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

	private static final String _FINDER_COLUMN_C_N_COMPANYID_2 =
		"kaleoDefinition.companyId = ? AND ";

	private static final String _FINDER_COLUMN_C_N_NAME_2 =
		"kaleoDefinition.name = ?";

	private static final String _FINDER_COLUMN_C_N_NAME_3 =
		"(kaleoDefinition.name IS NULL OR kaleoDefinition.name = '')";

	private FinderPath _finderPathWithPaginationFindByC_S;
	private FinderPath _finderPathWithoutPaginationFindByC_S;
	private FinderPath _finderPathCountByC_S;

	/**
	 * Returns all the kaleo definitions where companyId = &#63; and scope = &#63;.
	 *
	 * @param companyId the company ID
	 * @param scope the scope
	 * @return the matching kaleo definitions
	 */
	@Override
	public List<KaleoDefinition> findByC_S(long companyId, String scope) {
		return findByC_S(
			companyId, scope, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the kaleo definitions where companyId = &#63; and scope = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoDefinitionModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param scope the scope
	 * @param start the lower bound of the range of kaleo definitions
	 * @param end the upper bound of the range of kaleo definitions (not inclusive)
	 * @return the range of matching kaleo definitions
	 */
	@Override
	public List<KaleoDefinition> findByC_S(
		long companyId, String scope, int start, int end) {

		return findByC_S(companyId, scope, start, end, null);
	}

	/**
	 * Returns an ordered range of all the kaleo definitions where companyId = &#63; and scope = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoDefinitionModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param scope the scope
	 * @param start the lower bound of the range of kaleo definitions
	 * @param end the upper bound of the range of kaleo definitions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching kaleo definitions
	 */
	@Override
	public List<KaleoDefinition> findByC_S(
		long companyId, String scope, int start, int end,
		OrderByComparator<KaleoDefinition> orderByComparator) {

		return findByC_S(companyId, scope, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the kaleo definitions where companyId = &#63; and scope = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoDefinitionModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param scope the scope
	 * @param start the lower bound of the range of kaleo definitions
	 * @param end the upper bound of the range of kaleo definitions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching kaleo definitions
	 */
	@Override
	public List<KaleoDefinition> findByC_S(
		long companyId, String scope, int start, int end,
		OrderByComparator<KaleoDefinition> orderByComparator,
		boolean useFinderCache) {

		scope = Objects.toString(scope, "");

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByC_S;
				finderArgs = new Object[] {companyId, scope};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByC_S;
			finderArgs = new Object[] {
				companyId, scope, start, end, orderByComparator
			};
		}

		List<KaleoDefinition> list = null;

		if (useFinderCache) {
			list = (List<KaleoDefinition>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (KaleoDefinition kaleoDefinition : list) {
					if ((companyId != kaleoDefinition.getCompanyId()) ||
						!scope.equals(kaleoDefinition.getScope())) {

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

			sb.append(_SQL_SELECT_KALEODEFINITION_WHERE);

			sb.append(_FINDER_COLUMN_C_S_COMPANYID_2);

			boolean bindScope = false;

			if (scope.isEmpty()) {
				sb.append(_FINDER_COLUMN_C_S_SCOPE_3);
			}
			else {
				bindScope = true;

				sb.append(_FINDER_COLUMN_C_S_SCOPE_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(KaleoDefinitionModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(companyId);

				if (bindScope) {
					queryPos.add(scope);
				}

				list = (List<KaleoDefinition>)QueryUtil.list(
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
	 * Returns the first kaleo definition in the ordered set where companyId = &#63; and scope = &#63;.
	 *
	 * @param companyId the company ID
	 * @param scope the scope
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching kaleo definition
	 * @throws NoSuchDefinitionException if a matching kaleo definition could not be found
	 */
	@Override
	public KaleoDefinition findByC_S_First(
			long companyId, String scope,
			OrderByComparator<KaleoDefinition> orderByComparator)
		throws NoSuchDefinitionException {

		KaleoDefinition kaleoDefinition = fetchByC_S_First(
			companyId, scope, orderByComparator);

		if (kaleoDefinition != null) {
			return kaleoDefinition;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("companyId=");
		sb.append(companyId);

		sb.append(", scope=");
		sb.append(scope);

		sb.append("}");

		throw new NoSuchDefinitionException(sb.toString());
	}

	/**
	 * Returns the first kaleo definition in the ordered set where companyId = &#63; and scope = &#63;.
	 *
	 * @param companyId the company ID
	 * @param scope the scope
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching kaleo definition, or <code>null</code> if a matching kaleo definition could not be found
	 */
	@Override
	public KaleoDefinition fetchByC_S_First(
		long companyId, String scope,
		OrderByComparator<KaleoDefinition> orderByComparator) {

		List<KaleoDefinition> list = findByC_S(
			companyId, scope, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last kaleo definition in the ordered set where companyId = &#63; and scope = &#63;.
	 *
	 * @param companyId the company ID
	 * @param scope the scope
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching kaleo definition
	 * @throws NoSuchDefinitionException if a matching kaleo definition could not be found
	 */
	@Override
	public KaleoDefinition findByC_S_Last(
			long companyId, String scope,
			OrderByComparator<KaleoDefinition> orderByComparator)
		throws NoSuchDefinitionException {

		KaleoDefinition kaleoDefinition = fetchByC_S_Last(
			companyId, scope, orderByComparator);

		if (kaleoDefinition != null) {
			return kaleoDefinition;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("companyId=");
		sb.append(companyId);

		sb.append(", scope=");
		sb.append(scope);

		sb.append("}");

		throw new NoSuchDefinitionException(sb.toString());
	}

	/**
	 * Returns the last kaleo definition in the ordered set where companyId = &#63; and scope = &#63;.
	 *
	 * @param companyId the company ID
	 * @param scope the scope
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching kaleo definition, or <code>null</code> if a matching kaleo definition could not be found
	 */
	@Override
	public KaleoDefinition fetchByC_S_Last(
		long companyId, String scope,
		OrderByComparator<KaleoDefinition> orderByComparator) {

		int count = countByC_S(companyId, scope);

		if (count == 0) {
			return null;
		}

		List<KaleoDefinition> list = findByC_S(
			companyId, scope, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the kaleo definitions before and after the current kaleo definition in the ordered set where companyId = &#63; and scope = &#63;.
	 *
	 * @param kaleoDefinitionId the primary key of the current kaleo definition
	 * @param companyId the company ID
	 * @param scope the scope
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next kaleo definition
	 * @throws NoSuchDefinitionException if a kaleo definition with the primary key could not be found
	 */
	@Override
	public KaleoDefinition[] findByC_S_PrevAndNext(
			long kaleoDefinitionId, long companyId, String scope,
			OrderByComparator<KaleoDefinition> orderByComparator)
		throws NoSuchDefinitionException {

		scope = Objects.toString(scope, "");

		KaleoDefinition kaleoDefinition = findByPrimaryKey(kaleoDefinitionId);

		Session session = null;

		try {
			session = openSession();

			KaleoDefinition[] array = new KaleoDefinitionImpl[3];

			array[0] = getByC_S_PrevAndNext(
				session, kaleoDefinition, companyId, scope, orderByComparator,
				true);

			array[1] = kaleoDefinition;

			array[2] = getByC_S_PrevAndNext(
				session, kaleoDefinition, companyId, scope, orderByComparator,
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

	protected KaleoDefinition getByC_S_PrevAndNext(
		Session session, KaleoDefinition kaleoDefinition, long companyId,
		String scope, OrderByComparator<KaleoDefinition> orderByComparator,
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

		sb.append(_SQL_SELECT_KALEODEFINITION_WHERE);

		sb.append(_FINDER_COLUMN_C_S_COMPANYID_2);

		boolean bindScope = false;

		if (scope.isEmpty()) {
			sb.append(_FINDER_COLUMN_C_S_SCOPE_3);
		}
		else {
			bindScope = true;

			sb.append(_FINDER_COLUMN_C_S_SCOPE_2);
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
			sb.append(KaleoDefinitionModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(companyId);

		if (bindScope) {
			queryPos.add(scope);
		}

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						kaleoDefinition)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<KaleoDefinition> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the kaleo definitions where companyId = &#63; and scope = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param scope the scope
	 */
	@Override
	public void removeByC_S(long companyId, String scope) {
		for (KaleoDefinition kaleoDefinition :
				findByC_S(
					companyId, scope, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(kaleoDefinition);
		}
	}

	/**
	 * Returns the number of kaleo definitions where companyId = &#63; and scope = &#63;.
	 *
	 * @param companyId the company ID
	 * @param scope the scope
	 * @return the number of matching kaleo definitions
	 */
	@Override
	public int countByC_S(long companyId, String scope) {
		scope = Objects.toString(scope, "");

		FinderPath finderPath = _finderPathCountByC_S;

		Object[] finderArgs = new Object[] {companyId, scope};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_KALEODEFINITION_WHERE);

			sb.append(_FINDER_COLUMN_C_S_COMPANYID_2);

			boolean bindScope = false;

			if (scope.isEmpty()) {
				sb.append(_FINDER_COLUMN_C_S_SCOPE_3);
			}
			else {
				bindScope = true;

				sb.append(_FINDER_COLUMN_C_S_SCOPE_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(companyId);

				if (bindScope) {
					queryPos.add(scope);
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

	private static final String _FINDER_COLUMN_C_S_COMPANYID_2 =
		"kaleoDefinition.companyId = ? AND ";

	private static final String _FINDER_COLUMN_C_S_SCOPE_2 =
		"kaleoDefinition.scope = ?";

	private static final String _FINDER_COLUMN_C_S_SCOPE_3 =
		"(kaleoDefinition.scope IS NULL OR kaleoDefinition.scope = '')";

	private FinderPath _finderPathWithPaginationFindByC_A;
	private FinderPath _finderPathWithoutPaginationFindByC_A;
	private FinderPath _finderPathCountByC_A;

	/**
	 * Returns all the kaleo definitions where companyId = &#63; and active = &#63;.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @return the matching kaleo definitions
	 */
	@Override
	public List<KaleoDefinition> findByC_A(long companyId, boolean active) {
		return findByC_A(
			companyId, active, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the kaleo definitions where companyId = &#63; and active = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoDefinitionModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param start the lower bound of the range of kaleo definitions
	 * @param end the upper bound of the range of kaleo definitions (not inclusive)
	 * @return the range of matching kaleo definitions
	 */
	@Override
	public List<KaleoDefinition> findByC_A(
		long companyId, boolean active, int start, int end) {

		return findByC_A(companyId, active, start, end, null);
	}

	/**
	 * Returns an ordered range of all the kaleo definitions where companyId = &#63; and active = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoDefinitionModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param start the lower bound of the range of kaleo definitions
	 * @param end the upper bound of the range of kaleo definitions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching kaleo definitions
	 */
	@Override
	public List<KaleoDefinition> findByC_A(
		long companyId, boolean active, int start, int end,
		OrderByComparator<KaleoDefinition> orderByComparator) {

		return findByC_A(
			companyId, active, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the kaleo definitions where companyId = &#63; and active = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoDefinitionModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param start the lower bound of the range of kaleo definitions
	 * @param end the upper bound of the range of kaleo definitions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching kaleo definitions
	 */
	@Override
	public List<KaleoDefinition> findByC_A(
		long companyId, boolean active, int start, int end,
		OrderByComparator<KaleoDefinition> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByC_A;
				finderArgs = new Object[] {companyId, active};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByC_A;
			finderArgs = new Object[] {
				companyId, active, start, end, orderByComparator
			};
		}

		List<KaleoDefinition> list = null;

		if (useFinderCache) {
			list = (List<KaleoDefinition>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (KaleoDefinition kaleoDefinition : list) {
					if ((companyId != kaleoDefinition.getCompanyId()) ||
						(active != kaleoDefinition.isActive())) {

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

			sb.append(_SQL_SELECT_KALEODEFINITION_WHERE);

			sb.append(_FINDER_COLUMN_C_A_COMPANYID_2);

			sb.append(_FINDER_COLUMN_C_A_ACTIVE_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(KaleoDefinitionModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(companyId);

				queryPos.add(active);

				list = (List<KaleoDefinition>)QueryUtil.list(
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
	 * Returns the first kaleo definition in the ordered set where companyId = &#63; and active = &#63;.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching kaleo definition
	 * @throws NoSuchDefinitionException if a matching kaleo definition could not be found
	 */
	@Override
	public KaleoDefinition findByC_A_First(
			long companyId, boolean active,
			OrderByComparator<KaleoDefinition> orderByComparator)
		throws NoSuchDefinitionException {

		KaleoDefinition kaleoDefinition = fetchByC_A_First(
			companyId, active, orderByComparator);

		if (kaleoDefinition != null) {
			return kaleoDefinition;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("companyId=");
		sb.append(companyId);

		sb.append(", active=");
		sb.append(active);

		sb.append("}");

		throw new NoSuchDefinitionException(sb.toString());
	}

	/**
	 * Returns the first kaleo definition in the ordered set where companyId = &#63; and active = &#63;.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching kaleo definition, or <code>null</code> if a matching kaleo definition could not be found
	 */
	@Override
	public KaleoDefinition fetchByC_A_First(
		long companyId, boolean active,
		OrderByComparator<KaleoDefinition> orderByComparator) {

		List<KaleoDefinition> list = findByC_A(
			companyId, active, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last kaleo definition in the ordered set where companyId = &#63; and active = &#63;.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching kaleo definition
	 * @throws NoSuchDefinitionException if a matching kaleo definition could not be found
	 */
	@Override
	public KaleoDefinition findByC_A_Last(
			long companyId, boolean active,
			OrderByComparator<KaleoDefinition> orderByComparator)
		throws NoSuchDefinitionException {

		KaleoDefinition kaleoDefinition = fetchByC_A_Last(
			companyId, active, orderByComparator);

		if (kaleoDefinition != null) {
			return kaleoDefinition;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("companyId=");
		sb.append(companyId);

		sb.append(", active=");
		sb.append(active);

		sb.append("}");

		throw new NoSuchDefinitionException(sb.toString());
	}

	/**
	 * Returns the last kaleo definition in the ordered set where companyId = &#63; and active = &#63;.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching kaleo definition, or <code>null</code> if a matching kaleo definition could not be found
	 */
	@Override
	public KaleoDefinition fetchByC_A_Last(
		long companyId, boolean active,
		OrderByComparator<KaleoDefinition> orderByComparator) {

		int count = countByC_A(companyId, active);

		if (count == 0) {
			return null;
		}

		List<KaleoDefinition> list = findByC_A(
			companyId, active, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the kaleo definitions before and after the current kaleo definition in the ordered set where companyId = &#63; and active = &#63;.
	 *
	 * @param kaleoDefinitionId the primary key of the current kaleo definition
	 * @param companyId the company ID
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next kaleo definition
	 * @throws NoSuchDefinitionException if a kaleo definition with the primary key could not be found
	 */
	@Override
	public KaleoDefinition[] findByC_A_PrevAndNext(
			long kaleoDefinitionId, long companyId, boolean active,
			OrderByComparator<KaleoDefinition> orderByComparator)
		throws NoSuchDefinitionException {

		KaleoDefinition kaleoDefinition = findByPrimaryKey(kaleoDefinitionId);

		Session session = null;

		try {
			session = openSession();

			KaleoDefinition[] array = new KaleoDefinitionImpl[3];

			array[0] = getByC_A_PrevAndNext(
				session, kaleoDefinition, companyId, active, orderByComparator,
				true);

			array[1] = kaleoDefinition;

			array[2] = getByC_A_PrevAndNext(
				session, kaleoDefinition, companyId, active, orderByComparator,
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

	protected KaleoDefinition getByC_A_PrevAndNext(
		Session session, KaleoDefinition kaleoDefinition, long companyId,
		boolean active, OrderByComparator<KaleoDefinition> orderByComparator,
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

		sb.append(_SQL_SELECT_KALEODEFINITION_WHERE);

		sb.append(_FINDER_COLUMN_C_A_COMPANYID_2);

		sb.append(_FINDER_COLUMN_C_A_ACTIVE_2);

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
			sb.append(KaleoDefinitionModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(companyId);

		queryPos.add(active);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						kaleoDefinition)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<KaleoDefinition> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the kaleo definitions where companyId = &#63; and active = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 */
	@Override
	public void removeByC_A(long companyId, boolean active) {
		for (KaleoDefinition kaleoDefinition :
				findByC_A(
					companyId, active, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(kaleoDefinition);
		}
	}

	/**
	 * Returns the number of kaleo definitions where companyId = &#63; and active = &#63;.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @return the number of matching kaleo definitions
	 */
	@Override
	public int countByC_A(long companyId, boolean active) {
		FinderPath finderPath = _finderPathCountByC_A;

		Object[] finderArgs = new Object[] {companyId, active};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_KALEODEFINITION_WHERE);

			sb.append(_FINDER_COLUMN_C_A_COMPANYID_2);

			sb.append(_FINDER_COLUMN_C_A_ACTIVE_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(companyId);

				queryPos.add(active);

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

	private static final String _FINDER_COLUMN_C_A_COMPANYID_2 =
		"kaleoDefinition.companyId = ? AND ";

	private static final String _FINDER_COLUMN_C_A_ACTIVE_2 =
		"kaleoDefinition.active = ?";

	private FinderPath _finderPathFetchByC_N_V;
	private FinderPath _finderPathCountByC_N_V;

	/**
	 * Returns the kaleo definition where companyId = &#63; and name = &#63; and version = &#63; or throws a <code>NoSuchDefinitionException</code> if it could not be found.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @param version the version
	 * @return the matching kaleo definition
	 * @throws NoSuchDefinitionException if a matching kaleo definition could not be found
	 */
	@Override
	public KaleoDefinition findByC_N_V(long companyId, String name, int version)
		throws NoSuchDefinitionException {

		KaleoDefinition kaleoDefinition = fetchByC_N_V(
			companyId, name, version);

		if (kaleoDefinition == null) {
			StringBundler sb = new StringBundler(8);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("companyId=");
			sb.append(companyId);

			sb.append(", name=");
			sb.append(name);

			sb.append(", version=");
			sb.append(version);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchDefinitionException(sb.toString());
		}

		return kaleoDefinition;
	}

	/**
	 * Returns the kaleo definition where companyId = &#63; and name = &#63; and version = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @param version the version
	 * @return the matching kaleo definition, or <code>null</code> if a matching kaleo definition could not be found
	 */
	@Override
	public KaleoDefinition fetchByC_N_V(
		long companyId, String name, int version) {

		return fetchByC_N_V(companyId, name, version, true);
	}

	/**
	 * Returns the kaleo definition where companyId = &#63; and name = &#63; and version = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @param version the version
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching kaleo definition, or <code>null</code> if a matching kaleo definition could not be found
	 */
	@Override
	public KaleoDefinition fetchByC_N_V(
		long companyId, String name, int version, boolean useFinderCache) {

		name = Objects.toString(name, "");

		Object[] finderArgs = null;

		if (useFinderCache) {
			finderArgs = new Object[] {companyId, name, version};
		}

		Object result = null;

		if (useFinderCache) {
			result = finderCache.getResult(_finderPathFetchByC_N_V, finderArgs);
		}

		if (result instanceof KaleoDefinition) {
			KaleoDefinition kaleoDefinition = (KaleoDefinition)result;

			if ((companyId != kaleoDefinition.getCompanyId()) ||
				!Objects.equals(name, kaleoDefinition.getName()) ||
				(version != kaleoDefinition.getVersion())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(5);

			sb.append(_SQL_SELECT_KALEODEFINITION_WHERE);

			sb.append(_FINDER_COLUMN_C_N_V_COMPANYID_2);

			boolean bindName = false;

			if (name.isEmpty()) {
				sb.append(_FINDER_COLUMN_C_N_V_NAME_3);
			}
			else {
				bindName = true;

				sb.append(_FINDER_COLUMN_C_N_V_NAME_2);
			}

			sb.append(_FINDER_COLUMN_C_N_V_VERSION_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(companyId);

				if (bindName) {
					queryPos.add(name);
				}

				queryPos.add(version);

				List<KaleoDefinition> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache) {
						finderCache.putResult(
							_finderPathFetchByC_N_V, finderArgs, list);
					}
				}
				else {
					if (list.size() > 1) {
						Collections.sort(list, Collections.reverseOrder());

						if (_log.isWarnEnabled()) {
							if (!useFinderCache) {
								finderArgs = new Object[] {
									companyId, name, version
								};
							}

							_log.warn(
								"KaleoDefinitionPersistenceImpl.fetchByC_N_V(long, String, int, boolean) with parameters (" +
									StringUtil.merge(finderArgs) +
										") yields a result set with more than 1 result. This violates the logical unique restriction. There is no order guarantee on which result is returned by this finder.");
						}
					}

					KaleoDefinition kaleoDefinition = list.get(0);

					result = kaleoDefinition;

					cacheResult(kaleoDefinition);
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
			return (KaleoDefinition)result;
		}
	}

	/**
	 * Removes the kaleo definition where companyId = &#63; and name = &#63; and version = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @param version the version
	 * @return the kaleo definition that was removed
	 */
	@Override
	public KaleoDefinition removeByC_N_V(
			long companyId, String name, int version)
		throws NoSuchDefinitionException {

		KaleoDefinition kaleoDefinition = findByC_N_V(companyId, name, version);

		return remove(kaleoDefinition);
	}

	/**
	 * Returns the number of kaleo definitions where companyId = &#63; and name = &#63; and version = &#63;.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @param version the version
	 * @return the number of matching kaleo definitions
	 */
	@Override
	public int countByC_N_V(long companyId, String name, int version) {
		name = Objects.toString(name, "");

		FinderPath finderPath = _finderPathCountByC_N_V;

		Object[] finderArgs = new Object[] {companyId, name, version};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_COUNT_KALEODEFINITION_WHERE);

			sb.append(_FINDER_COLUMN_C_N_V_COMPANYID_2);

			boolean bindName = false;

			if (name.isEmpty()) {
				sb.append(_FINDER_COLUMN_C_N_V_NAME_3);
			}
			else {
				bindName = true;

				sb.append(_FINDER_COLUMN_C_N_V_NAME_2);
			}

			sb.append(_FINDER_COLUMN_C_N_V_VERSION_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(companyId);

				if (bindName) {
					queryPos.add(name);
				}

				queryPos.add(version);

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

	private static final String _FINDER_COLUMN_C_N_V_COMPANYID_2 =
		"kaleoDefinition.companyId = ? AND ";

	private static final String _FINDER_COLUMN_C_N_V_NAME_2 =
		"kaleoDefinition.name = ? AND ";

	private static final String _FINDER_COLUMN_C_N_V_NAME_3 =
		"(kaleoDefinition.name IS NULL OR kaleoDefinition.name = '') AND ";

	private static final String _FINDER_COLUMN_C_N_V_VERSION_2 =
		"kaleoDefinition.version = ?";

	private FinderPath _finderPathFetchByC_N_A;
	private FinderPath _finderPathCountByC_N_A;

	/**
	 * Returns the kaleo definition where companyId = &#63; and name = &#63; and active = &#63; or throws a <code>NoSuchDefinitionException</code> if it could not be found.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @param active the active
	 * @return the matching kaleo definition
	 * @throws NoSuchDefinitionException if a matching kaleo definition could not be found
	 */
	@Override
	public KaleoDefinition findByC_N_A(
			long companyId, String name, boolean active)
		throws NoSuchDefinitionException {

		KaleoDefinition kaleoDefinition = fetchByC_N_A(companyId, name, active);

		if (kaleoDefinition == null) {
			StringBundler sb = new StringBundler(8);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("companyId=");
			sb.append(companyId);

			sb.append(", name=");
			sb.append(name);

			sb.append(", active=");
			sb.append(active);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchDefinitionException(sb.toString());
		}

		return kaleoDefinition;
	}

	/**
	 * Returns the kaleo definition where companyId = &#63; and name = &#63; and active = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @param active the active
	 * @return the matching kaleo definition, or <code>null</code> if a matching kaleo definition could not be found
	 */
	@Override
	public KaleoDefinition fetchByC_N_A(
		long companyId, String name, boolean active) {

		return fetchByC_N_A(companyId, name, active, true);
	}

	/**
	 * Returns the kaleo definition where companyId = &#63; and name = &#63; and active = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @param active the active
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching kaleo definition, or <code>null</code> if a matching kaleo definition could not be found
	 */
	@Override
	public KaleoDefinition fetchByC_N_A(
		long companyId, String name, boolean active, boolean useFinderCache) {

		name = Objects.toString(name, "");

		Object[] finderArgs = null;

		if (useFinderCache) {
			finderArgs = new Object[] {companyId, name, active};
		}

		Object result = null;

		if (useFinderCache) {
			result = finderCache.getResult(_finderPathFetchByC_N_A, finderArgs);
		}

		if (result instanceof KaleoDefinition) {
			KaleoDefinition kaleoDefinition = (KaleoDefinition)result;

			if ((companyId != kaleoDefinition.getCompanyId()) ||
				!Objects.equals(name, kaleoDefinition.getName()) ||
				(active != kaleoDefinition.isActive())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(5);

			sb.append(_SQL_SELECT_KALEODEFINITION_WHERE);

			sb.append(_FINDER_COLUMN_C_N_A_COMPANYID_2);

			boolean bindName = false;

			if (name.isEmpty()) {
				sb.append(_FINDER_COLUMN_C_N_A_NAME_3);
			}
			else {
				bindName = true;

				sb.append(_FINDER_COLUMN_C_N_A_NAME_2);
			}

			sb.append(_FINDER_COLUMN_C_N_A_ACTIVE_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(companyId);

				if (bindName) {
					queryPos.add(name);
				}

				queryPos.add(active);

				List<KaleoDefinition> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache) {
						finderCache.putResult(
							_finderPathFetchByC_N_A, finderArgs, list);
					}
				}
				else {
					if (list.size() > 1) {
						Collections.sort(list, Collections.reverseOrder());

						if (_log.isWarnEnabled()) {
							if (!useFinderCache) {
								finderArgs = new Object[] {
									companyId, name, active
								};
							}

							_log.warn(
								"KaleoDefinitionPersistenceImpl.fetchByC_N_A(long, String, boolean, boolean) with parameters (" +
									StringUtil.merge(finderArgs) +
										") yields a result set with more than 1 result. This violates the logical unique restriction. There is no order guarantee on which result is returned by this finder.");
						}
					}

					KaleoDefinition kaleoDefinition = list.get(0);

					result = kaleoDefinition;

					cacheResult(kaleoDefinition);
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
			return (KaleoDefinition)result;
		}
	}

	/**
	 * Removes the kaleo definition where companyId = &#63; and name = &#63; and active = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @param active the active
	 * @return the kaleo definition that was removed
	 */
	@Override
	public KaleoDefinition removeByC_N_A(
			long companyId, String name, boolean active)
		throws NoSuchDefinitionException {

		KaleoDefinition kaleoDefinition = findByC_N_A(companyId, name, active);

		return remove(kaleoDefinition);
	}

	/**
	 * Returns the number of kaleo definitions where companyId = &#63; and name = &#63; and active = &#63;.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @param active the active
	 * @return the number of matching kaleo definitions
	 */
	@Override
	public int countByC_N_A(long companyId, String name, boolean active) {
		name = Objects.toString(name, "");

		FinderPath finderPath = _finderPathCountByC_N_A;

		Object[] finderArgs = new Object[] {companyId, name, active};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_COUNT_KALEODEFINITION_WHERE);

			sb.append(_FINDER_COLUMN_C_N_A_COMPANYID_2);

			boolean bindName = false;

			if (name.isEmpty()) {
				sb.append(_FINDER_COLUMN_C_N_A_NAME_3);
			}
			else {
				bindName = true;

				sb.append(_FINDER_COLUMN_C_N_A_NAME_2);
			}

			sb.append(_FINDER_COLUMN_C_N_A_ACTIVE_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(companyId);

				if (bindName) {
					queryPos.add(name);
				}

				queryPos.add(active);

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

	private static final String _FINDER_COLUMN_C_N_A_COMPANYID_2 =
		"kaleoDefinition.companyId = ? AND ";

	private static final String _FINDER_COLUMN_C_N_A_NAME_2 =
		"kaleoDefinition.name = ? AND ";

	private static final String _FINDER_COLUMN_C_N_A_NAME_3 =
		"(kaleoDefinition.name IS NULL OR kaleoDefinition.name = '') AND ";

	private static final String _FINDER_COLUMN_C_N_A_ACTIVE_2 =
		"kaleoDefinition.active = ?";

	private FinderPath _finderPathWithPaginationFindByC_S_A;
	private FinderPath _finderPathWithoutPaginationFindByC_S_A;
	private FinderPath _finderPathCountByC_S_A;

	/**
	 * Returns all the kaleo definitions where companyId = &#63; and scope = &#63; and active = &#63;.
	 *
	 * @param companyId the company ID
	 * @param scope the scope
	 * @param active the active
	 * @return the matching kaleo definitions
	 */
	@Override
	public List<KaleoDefinition> findByC_S_A(
		long companyId, String scope, boolean active) {

		return findByC_S_A(
			companyId, scope, active, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the kaleo definitions where companyId = &#63; and scope = &#63; and active = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoDefinitionModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param scope the scope
	 * @param active the active
	 * @param start the lower bound of the range of kaleo definitions
	 * @param end the upper bound of the range of kaleo definitions (not inclusive)
	 * @return the range of matching kaleo definitions
	 */
	@Override
	public List<KaleoDefinition> findByC_S_A(
		long companyId, String scope, boolean active, int start, int end) {

		return findByC_S_A(companyId, scope, active, start, end, null);
	}

	/**
	 * Returns an ordered range of all the kaleo definitions where companyId = &#63; and scope = &#63; and active = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoDefinitionModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param scope the scope
	 * @param active the active
	 * @param start the lower bound of the range of kaleo definitions
	 * @param end the upper bound of the range of kaleo definitions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching kaleo definitions
	 */
	@Override
	public List<KaleoDefinition> findByC_S_A(
		long companyId, String scope, boolean active, int start, int end,
		OrderByComparator<KaleoDefinition> orderByComparator) {

		return findByC_S_A(
			companyId, scope, active, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the kaleo definitions where companyId = &#63; and scope = &#63; and active = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoDefinitionModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param scope the scope
	 * @param active the active
	 * @param start the lower bound of the range of kaleo definitions
	 * @param end the upper bound of the range of kaleo definitions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching kaleo definitions
	 */
	@Override
	public List<KaleoDefinition> findByC_S_A(
		long companyId, String scope, boolean active, int start, int end,
		OrderByComparator<KaleoDefinition> orderByComparator,
		boolean useFinderCache) {

		scope = Objects.toString(scope, "");

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByC_S_A;
				finderArgs = new Object[] {companyId, scope, active};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByC_S_A;
			finderArgs = new Object[] {
				companyId, scope, active, start, end, orderByComparator
			};
		}

		List<KaleoDefinition> list = null;

		if (useFinderCache) {
			list = (List<KaleoDefinition>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (KaleoDefinition kaleoDefinition : list) {
					if ((companyId != kaleoDefinition.getCompanyId()) ||
						!scope.equals(kaleoDefinition.getScope()) ||
						(active != kaleoDefinition.isActive())) {

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
					5 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				sb = new StringBundler(5);
			}

			sb.append(_SQL_SELECT_KALEODEFINITION_WHERE);

			sb.append(_FINDER_COLUMN_C_S_A_COMPANYID_2);

			boolean bindScope = false;

			if (scope.isEmpty()) {
				sb.append(_FINDER_COLUMN_C_S_A_SCOPE_3);
			}
			else {
				bindScope = true;

				sb.append(_FINDER_COLUMN_C_S_A_SCOPE_2);
			}

			sb.append(_FINDER_COLUMN_C_S_A_ACTIVE_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(KaleoDefinitionModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(companyId);

				if (bindScope) {
					queryPos.add(scope);
				}

				queryPos.add(active);

				list = (List<KaleoDefinition>)QueryUtil.list(
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
	 * Returns the first kaleo definition in the ordered set where companyId = &#63; and scope = &#63; and active = &#63;.
	 *
	 * @param companyId the company ID
	 * @param scope the scope
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching kaleo definition
	 * @throws NoSuchDefinitionException if a matching kaleo definition could not be found
	 */
	@Override
	public KaleoDefinition findByC_S_A_First(
			long companyId, String scope, boolean active,
			OrderByComparator<KaleoDefinition> orderByComparator)
		throws NoSuchDefinitionException {

		KaleoDefinition kaleoDefinition = fetchByC_S_A_First(
			companyId, scope, active, orderByComparator);

		if (kaleoDefinition != null) {
			return kaleoDefinition;
		}

		StringBundler sb = new StringBundler(8);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("companyId=");
		sb.append(companyId);

		sb.append(", scope=");
		sb.append(scope);

		sb.append(", active=");
		sb.append(active);

		sb.append("}");

		throw new NoSuchDefinitionException(sb.toString());
	}

	/**
	 * Returns the first kaleo definition in the ordered set where companyId = &#63; and scope = &#63; and active = &#63;.
	 *
	 * @param companyId the company ID
	 * @param scope the scope
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching kaleo definition, or <code>null</code> if a matching kaleo definition could not be found
	 */
	@Override
	public KaleoDefinition fetchByC_S_A_First(
		long companyId, String scope, boolean active,
		OrderByComparator<KaleoDefinition> orderByComparator) {

		List<KaleoDefinition> list = findByC_S_A(
			companyId, scope, active, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last kaleo definition in the ordered set where companyId = &#63; and scope = &#63; and active = &#63;.
	 *
	 * @param companyId the company ID
	 * @param scope the scope
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching kaleo definition
	 * @throws NoSuchDefinitionException if a matching kaleo definition could not be found
	 */
	@Override
	public KaleoDefinition findByC_S_A_Last(
			long companyId, String scope, boolean active,
			OrderByComparator<KaleoDefinition> orderByComparator)
		throws NoSuchDefinitionException {

		KaleoDefinition kaleoDefinition = fetchByC_S_A_Last(
			companyId, scope, active, orderByComparator);

		if (kaleoDefinition != null) {
			return kaleoDefinition;
		}

		StringBundler sb = new StringBundler(8);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("companyId=");
		sb.append(companyId);

		sb.append(", scope=");
		sb.append(scope);

		sb.append(", active=");
		sb.append(active);

		sb.append("}");

		throw new NoSuchDefinitionException(sb.toString());
	}

	/**
	 * Returns the last kaleo definition in the ordered set where companyId = &#63; and scope = &#63; and active = &#63;.
	 *
	 * @param companyId the company ID
	 * @param scope the scope
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching kaleo definition, or <code>null</code> if a matching kaleo definition could not be found
	 */
	@Override
	public KaleoDefinition fetchByC_S_A_Last(
		long companyId, String scope, boolean active,
		OrderByComparator<KaleoDefinition> orderByComparator) {

		int count = countByC_S_A(companyId, scope, active);

		if (count == 0) {
			return null;
		}

		List<KaleoDefinition> list = findByC_S_A(
			companyId, scope, active, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the kaleo definitions before and after the current kaleo definition in the ordered set where companyId = &#63; and scope = &#63; and active = &#63;.
	 *
	 * @param kaleoDefinitionId the primary key of the current kaleo definition
	 * @param companyId the company ID
	 * @param scope the scope
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next kaleo definition
	 * @throws NoSuchDefinitionException if a kaleo definition with the primary key could not be found
	 */
	@Override
	public KaleoDefinition[] findByC_S_A_PrevAndNext(
			long kaleoDefinitionId, long companyId, String scope,
			boolean active,
			OrderByComparator<KaleoDefinition> orderByComparator)
		throws NoSuchDefinitionException {

		scope = Objects.toString(scope, "");

		KaleoDefinition kaleoDefinition = findByPrimaryKey(kaleoDefinitionId);

		Session session = null;

		try {
			session = openSession();

			KaleoDefinition[] array = new KaleoDefinitionImpl[3];

			array[0] = getByC_S_A_PrevAndNext(
				session, kaleoDefinition, companyId, scope, active,
				orderByComparator, true);

			array[1] = kaleoDefinition;

			array[2] = getByC_S_A_PrevAndNext(
				session, kaleoDefinition, companyId, scope, active,
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

	protected KaleoDefinition getByC_S_A_PrevAndNext(
		Session session, KaleoDefinition kaleoDefinition, long companyId,
		String scope, boolean active,
		OrderByComparator<KaleoDefinition> orderByComparator,
		boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				6 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(5);
		}

		sb.append(_SQL_SELECT_KALEODEFINITION_WHERE);

		sb.append(_FINDER_COLUMN_C_S_A_COMPANYID_2);

		boolean bindScope = false;

		if (scope.isEmpty()) {
			sb.append(_FINDER_COLUMN_C_S_A_SCOPE_3);
		}
		else {
			bindScope = true;

			sb.append(_FINDER_COLUMN_C_S_A_SCOPE_2);
		}

		sb.append(_FINDER_COLUMN_C_S_A_ACTIVE_2);

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
			sb.append(KaleoDefinitionModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(companyId);

		if (bindScope) {
			queryPos.add(scope);
		}

		queryPos.add(active);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						kaleoDefinition)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<KaleoDefinition> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the kaleo definitions where companyId = &#63; and scope = &#63; and active = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param scope the scope
	 * @param active the active
	 */
	@Override
	public void removeByC_S_A(long companyId, String scope, boolean active) {
		for (KaleoDefinition kaleoDefinition :
				findByC_S_A(
					companyId, scope, active, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			remove(kaleoDefinition);
		}
	}

	/**
	 * Returns the number of kaleo definitions where companyId = &#63; and scope = &#63; and active = &#63;.
	 *
	 * @param companyId the company ID
	 * @param scope the scope
	 * @param active the active
	 * @return the number of matching kaleo definitions
	 */
	@Override
	public int countByC_S_A(long companyId, String scope, boolean active) {
		scope = Objects.toString(scope, "");

		FinderPath finderPath = _finderPathCountByC_S_A;

		Object[] finderArgs = new Object[] {companyId, scope, active};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_COUNT_KALEODEFINITION_WHERE);

			sb.append(_FINDER_COLUMN_C_S_A_COMPANYID_2);

			boolean bindScope = false;

			if (scope.isEmpty()) {
				sb.append(_FINDER_COLUMN_C_S_A_SCOPE_3);
			}
			else {
				bindScope = true;

				sb.append(_FINDER_COLUMN_C_S_A_SCOPE_2);
			}

			sb.append(_FINDER_COLUMN_C_S_A_ACTIVE_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(companyId);

				if (bindScope) {
					queryPos.add(scope);
				}

				queryPos.add(active);

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

	private static final String _FINDER_COLUMN_C_S_A_COMPANYID_2 =
		"kaleoDefinition.companyId = ? AND ";

	private static final String _FINDER_COLUMN_C_S_A_SCOPE_2 =
		"kaleoDefinition.scope = ? AND ";

	private static final String _FINDER_COLUMN_C_S_A_SCOPE_3 =
		"(kaleoDefinition.scope IS NULL OR kaleoDefinition.scope = '') AND ";

	private static final String _FINDER_COLUMN_C_S_A_ACTIVE_2 =
		"kaleoDefinition.active = ?";

	public KaleoDefinitionPersistenceImpl() {
		Map<String, String> dbColumnNames = new HashMap<String, String>();

		dbColumnNames.put("active", "active_");

		setDBColumnNames(dbColumnNames);

		setModelClass(KaleoDefinition.class);

		setModelImplClass(KaleoDefinitionImpl.class);
		setModelPKClass(long.class);

		setTable(KaleoDefinitionTable.INSTANCE);
	}

	/**
	 * Caches the kaleo definition in the entity cache if it is enabled.
	 *
	 * @param kaleoDefinition the kaleo definition
	 */
	@Override
	public void cacheResult(KaleoDefinition kaleoDefinition) {
		entityCache.putResult(
			KaleoDefinitionImpl.class, kaleoDefinition.getPrimaryKey(),
			kaleoDefinition);

		finderCache.putResult(
			_finderPathFetchByC_N,
			new Object[] {
				kaleoDefinition.getCompanyId(), kaleoDefinition.getName()
			},
			kaleoDefinition);

		finderCache.putResult(
			_finderPathFetchByC_N_V,
			new Object[] {
				kaleoDefinition.getCompanyId(), kaleoDefinition.getName(),
				kaleoDefinition.getVersion()
			},
			kaleoDefinition);

		finderCache.putResult(
			_finderPathFetchByC_N_A,
			new Object[] {
				kaleoDefinition.getCompanyId(), kaleoDefinition.getName(),
				kaleoDefinition.isActive()
			},
			kaleoDefinition);
	}

	private int _valueObjectFinderCacheListThreshold;

	/**
	 * Caches the kaleo definitions in the entity cache if it is enabled.
	 *
	 * @param kaleoDefinitions the kaleo definitions
	 */
	@Override
	public void cacheResult(List<KaleoDefinition> kaleoDefinitions) {
		if ((_valueObjectFinderCacheListThreshold == 0) ||
			((_valueObjectFinderCacheListThreshold > 0) &&
			 (kaleoDefinitions.size() >
				 _valueObjectFinderCacheListThreshold))) {

			return;
		}

		for (KaleoDefinition kaleoDefinition : kaleoDefinitions) {
			if (entityCache.getResult(
					KaleoDefinitionImpl.class,
					kaleoDefinition.getPrimaryKey()) == null) {

				cacheResult(kaleoDefinition);
			}
		}
	}

	/**
	 * Clears the cache for all kaleo definitions.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(KaleoDefinitionImpl.class);

		finderCache.clearCache(KaleoDefinitionImpl.class);
	}

	/**
	 * Clears the cache for the kaleo definition.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(KaleoDefinition kaleoDefinition) {
		entityCache.removeResult(KaleoDefinitionImpl.class, kaleoDefinition);
	}

	@Override
	public void clearCache(List<KaleoDefinition> kaleoDefinitions) {
		for (KaleoDefinition kaleoDefinition : kaleoDefinitions) {
			entityCache.removeResult(
				KaleoDefinitionImpl.class, kaleoDefinition);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(KaleoDefinitionImpl.class);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(KaleoDefinitionImpl.class, primaryKey);
		}
	}

	protected void cacheUniqueFindersCache(
		KaleoDefinitionModelImpl kaleoDefinitionModelImpl) {

		Object[] args = new Object[] {
			kaleoDefinitionModelImpl.getCompanyId(),
			kaleoDefinitionModelImpl.getName()
		};

		finderCache.putResult(_finderPathCountByC_N, args, Long.valueOf(1));
		finderCache.putResult(
			_finderPathFetchByC_N, args, kaleoDefinitionModelImpl);

		args = new Object[] {
			kaleoDefinitionModelImpl.getCompanyId(),
			kaleoDefinitionModelImpl.getName(),
			kaleoDefinitionModelImpl.getVersion()
		};

		finderCache.putResult(_finderPathCountByC_N_V, args, Long.valueOf(1));
		finderCache.putResult(
			_finderPathFetchByC_N_V, args, kaleoDefinitionModelImpl);

		args = new Object[] {
			kaleoDefinitionModelImpl.getCompanyId(),
			kaleoDefinitionModelImpl.getName(),
			kaleoDefinitionModelImpl.isActive()
		};

		finderCache.putResult(_finderPathCountByC_N_A, args, Long.valueOf(1));
		finderCache.putResult(
			_finderPathFetchByC_N_A, args, kaleoDefinitionModelImpl);
	}

	/**
	 * Creates a new kaleo definition with the primary key. Does not add the kaleo definition to the database.
	 *
	 * @param kaleoDefinitionId the primary key for the new kaleo definition
	 * @return the new kaleo definition
	 */
	@Override
	public KaleoDefinition create(long kaleoDefinitionId) {
		KaleoDefinition kaleoDefinition = new KaleoDefinitionImpl();

		kaleoDefinition.setNew(true);
		kaleoDefinition.setPrimaryKey(kaleoDefinitionId);

		kaleoDefinition.setCompanyId(CompanyThreadLocal.getCompanyId());

		return kaleoDefinition;
	}

	/**
	 * Removes the kaleo definition with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param kaleoDefinitionId the primary key of the kaleo definition
	 * @return the kaleo definition that was removed
	 * @throws NoSuchDefinitionException if a kaleo definition with the primary key could not be found
	 */
	@Override
	public KaleoDefinition remove(long kaleoDefinitionId)
		throws NoSuchDefinitionException {

		return remove((Serializable)kaleoDefinitionId);
	}

	/**
	 * Removes the kaleo definition with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the kaleo definition
	 * @return the kaleo definition that was removed
	 * @throws NoSuchDefinitionException if a kaleo definition with the primary key could not be found
	 */
	@Override
	public KaleoDefinition remove(Serializable primaryKey)
		throws NoSuchDefinitionException {

		Session session = null;

		try {
			session = openSession();

			KaleoDefinition kaleoDefinition = (KaleoDefinition)session.get(
				KaleoDefinitionImpl.class, primaryKey);

			if (kaleoDefinition == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchDefinitionException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(kaleoDefinition);
		}
		catch (NoSuchDefinitionException noSuchEntityException) {
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
	protected KaleoDefinition removeImpl(KaleoDefinition kaleoDefinition) {
		Session session = null;

		try {
			session = openSession();

			if (!session.contains(kaleoDefinition)) {
				kaleoDefinition = (KaleoDefinition)session.get(
					KaleoDefinitionImpl.class,
					kaleoDefinition.getPrimaryKeyObj());
			}

			if (kaleoDefinition != null) {
				session.delete(kaleoDefinition);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (kaleoDefinition != null) {
			clearCache(kaleoDefinition);
		}

		return kaleoDefinition;
	}

	@Override
	public KaleoDefinition updateImpl(KaleoDefinition kaleoDefinition) {
		boolean isNew = kaleoDefinition.isNew();

		if (!(kaleoDefinition instanceof KaleoDefinitionModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(kaleoDefinition.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					kaleoDefinition);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in kaleoDefinition proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom KaleoDefinition implementation " +
					kaleoDefinition.getClass());
		}

		KaleoDefinitionModelImpl kaleoDefinitionModelImpl =
			(KaleoDefinitionModelImpl)kaleoDefinition;

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Date date = new Date();

		if (isNew && (kaleoDefinition.getCreateDate() == null)) {
			if (serviceContext == null) {
				kaleoDefinition.setCreateDate(date);
			}
			else {
				kaleoDefinition.setCreateDate(
					serviceContext.getCreateDate(date));
			}
		}

		if (!kaleoDefinitionModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				kaleoDefinition.setModifiedDate(date);
			}
			else {
				kaleoDefinition.setModifiedDate(
					serviceContext.getModifiedDate(date));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (isNew) {
				session.save(kaleoDefinition);
			}
			else {
				kaleoDefinition = (KaleoDefinition)session.merge(
					kaleoDefinition);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		entityCache.putResult(
			KaleoDefinitionImpl.class, kaleoDefinitionModelImpl, false, true);

		cacheUniqueFindersCache(kaleoDefinitionModelImpl);

		if (isNew) {
			kaleoDefinition.setNew(false);
		}

		kaleoDefinition.resetOriginalValues();

		return kaleoDefinition;
	}

	/**
	 * Returns the kaleo definition with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the kaleo definition
	 * @return the kaleo definition
	 * @throws NoSuchDefinitionException if a kaleo definition with the primary key could not be found
	 */
	@Override
	public KaleoDefinition findByPrimaryKey(Serializable primaryKey)
		throws NoSuchDefinitionException {

		KaleoDefinition kaleoDefinition = fetchByPrimaryKey(primaryKey);

		if (kaleoDefinition == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchDefinitionException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return kaleoDefinition;
	}

	/**
	 * Returns the kaleo definition with the primary key or throws a <code>NoSuchDefinitionException</code> if it could not be found.
	 *
	 * @param kaleoDefinitionId the primary key of the kaleo definition
	 * @return the kaleo definition
	 * @throws NoSuchDefinitionException if a kaleo definition with the primary key could not be found
	 */
	@Override
	public KaleoDefinition findByPrimaryKey(long kaleoDefinitionId)
		throws NoSuchDefinitionException {

		return findByPrimaryKey((Serializable)kaleoDefinitionId);
	}

	/**
	 * Returns the kaleo definition with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param kaleoDefinitionId the primary key of the kaleo definition
	 * @return the kaleo definition, or <code>null</code> if a kaleo definition with the primary key could not be found
	 */
	@Override
	public KaleoDefinition fetchByPrimaryKey(long kaleoDefinitionId) {
		return fetchByPrimaryKey((Serializable)kaleoDefinitionId);
	}

	/**
	 * Returns all the kaleo definitions.
	 *
	 * @return the kaleo definitions
	 */
	@Override
	public List<KaleoDefinition> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the kaleo definitions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoDefinitionModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of kaleo definitions
	 * @param end the upper bound of the range of kaleo definitions (not inclusive)
	 * @return the range of kaleo definitions
	 */
	@Override
	public List<KaleoDefinition> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the kaleo definitions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoDefinitionModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of kaleo definitions
	 * @param end the upper bound of the range of kaleo definitions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of kaleo definitions
	 */
	@Override
	public List<KaleoDefinition> findAll(
		int start, int end,
		OrderByComparator<KaleoDefinition> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the kaleo definitions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoDefinitionModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of kaleo definitions
	 * @param end the upper bound of the range of kaleo definitions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of kaleo definitions
	 */
	@Override
	public List<KaleoDefinition> findAll(
		int start, int end,
		OrderByComparator<KaleoDefinition> orderByComparator,
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

		List<KaleoDefinition> list = null;

		if (useFinderCache) {
			list = (List<KaleoDefinition>)finderCache.getResult(
				finderPath, finderArgs);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_KALEODEFINITION);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_KALEODEFINITION;

				sql = sql.concat(KaleoDefinitionModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<KaleoDefinition>)QueryUtil.list(
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
	 * Removes all the kaleo definitions from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (KaleoDefinition kaleoDefinition : findAll()) {
			remove(kaleoDefinition);
		}
	}

	/**
	 * Returns the number of kaleo definitions.
	 *
	 * @return the number of kaleo definitions
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(
			_finderPathCountAll, FINDER_ARGS_EMPTY);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(_SQL_COUNT_KALEODEFINITION);

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
		return "kaleoDefinitionId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_KALEODEFINITION;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return KaleoDefinitionModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the kaleo definition persistence.
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

		_finderPathFetchByC_N = new FinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByC_N",
			new String[] {Long.class.getName(), String.class.getName()},
			new String[] {"companyId", "name"}, true);

		_finderPathCountByC_N = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_N",
			new String[] {Long.class.getName(), String.class.getName()},
			new String[] {"companyId", "name"}, false);

		_finderPathWithPaginationFindByC_S = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByC_S",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {"companyId", "scope"}, true);

		_finderPathWithoutPaginationFindByC_S = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByC_S",
			new String[] {Long.class.getName(), String.class.getName()},
			new String[] {"companyId", "scope"}, true);

		_finderPathCountByC_S = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_S",
			new String[] {Long.class.getName(), String.class.getName()},
			new String[] {"companyId", "scope"}, false);

		_finderPathWithPaginationFindByC_A = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByC_A",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {"companyId", "active_"}, true);

		_finderPathWithoutPaginationFindByC_A = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByC_A",
			new String[] {Long.class.getName(), Boolean.class.getName()},
			new String[] {"companyId", "active_"}, true);

		_finderPathCountByC_A = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_A",
			new String[] {Long.class.getName(), Boolean.class.getName()},
			new String[] {"companyId", "active_"}, false);

		_finderPathFetchByC_N_V = new FinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByC_N_V",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Integer.class.getName()
			},
			new String[] {"companyId", "name", "version"}, true);

		_finderPathCountByC_N_V = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_N_V",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Integer.class.getName()
			},
			new String[] {"companyId", "name", "version"}, false);

		_finderPathFetchByC_N_A = new FinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByC_N_A",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Boolean.class.getName()
			},
			new String[] {"companyId", "name", "active_"}, true);

		_finderPathCountByC_N_A = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_N_A",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Boolean.class.getName()
			},
			new String[] {"companyId", "name", "active_"}, false);

		_finderPathWithPaginationFindByC_S_A = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByC_S_A",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Boolean.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"companyId", "scope", "active_"}, true);

		_finderPathWithoutPaginationFindByC_S_A = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByC_S_A",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Boolean.class.getName()
			},
			new String[] {"companyId", "scope", "active_"}, true);

		_finderPathCountByC_S_A = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_S_A",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Boolean.class.getName()
			},
			new String[] {"companyId", "scope", "active_"}, false);
	}

	@Deactivate
	public void deactivate() {
		entityCache.removeCache(KaleoDefinitionImpl.class.getName());
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

	private static final String _SQL_SELECT_KALEODEFINITION =
		"SELECT kaleoDefinition FROM KaleoDefinition kaleoDefinition";

	private static final String _SQL_SELECT_KALEODEFINITION_WHERE =
		"SELECT kaleoDefinition FROM KaleoDefinition kaleoDefinition WHERE ";

	private static final String _SQL_COUNT_KALEODEFINITION =
		"SELECT COUNT(kaleoDefinition) FROM KaleoDefinition kaleoDefinition";

	private static final String _SQL_COUNT_KALEODEFINITION_WHERE =
		"SELECT COUNT(kaleoDefinition) FROM KaleoDefinition kaleoDefinition WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS = "kaleoDefinition.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No KaleoDefinition exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No KaleoDefinition exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		KaleoDefinitionPersistenceImpl.class);

	private static final Set<String> _badColumnNames = SetUtil.fromArray(
		new String[] {"active"});

	@Override
	protected FinderCache getFinderCache() {
		return finderCache;
	}

	@Reference
	private KaleoDefinitionModelArgumentsResolver
		_kaleoDefinitionModelArgumentsResolver;

}