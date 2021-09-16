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
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.workflow.kaleo.exception.NoSuchTransitionException;
import com.liferay.portal.workflow.kaleo.model.KaleoTransition;
import com.liferay.portal.workflow.kaleo.model.KaleoTransitionTable;
import com.liferay.portal.workflow.kaleo.model.impl.KaleoTransitionImpl;
import com.liferay.portal.workflow.kaleo.model.impl.KaleoTransitionModelImpl;
import com.liferay.portal.workflow.kaleo.service.persistence.KaleoTransitionPersistence;
import com.liferay.portal.workflow.kaleo.service.persistence.impl.constants.KaleoPersistenceConstants;

import java.io.Serializable;

import java.lang.reflect.InvocationHandler;

import java.util.Collections;
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
 * The persistence implementation for the kaleo transition service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
@Component(service = {KaleoTransitionPersistence.class, BasePersistence.class})
public class KaleoTransitionPersistenceImpl
	extends BasePersistenceImpl<KaleoTransition>
	implements KaleoTransitionPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>KaleoTransitionUtil</code> to access the kaleo transition persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		KaleoTransitionImpl.class.getName();

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
	 * Returns all the kaleo transitions where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching kaleo transitions
	 */
	@Override
	public List<KaleoTransition> findByCompanyId(long companyId) {
		return findByCompanyId(
			companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the kaleo transitions where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoTransitionModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of kaleo transitions
	 * @param end the upper bound of the range of kaleo transitions (not inclusive)
	 * @return the range of matching kaleo transitions
	 */
	@Override
	public List<KaleoTransition> findByCompanyId(
		long companyId, int start, int end) {

		return findByCompanyId(companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the kaleo transitions where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoTransitionModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of kaleo transitions
	 * @param end the upper bound of the range of kaleo transitions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching kaleo transitions
	 */
	@Override
	public List<KaleoTransition> findByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<KaleoTransition> orderByComparator) {

		return findByCompanyId(companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the kaleo transitions where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoTransitionModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of kaleo transitions
	 * @param end the upper bound of the range of kaleo transitions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching kaleo transitions
	 */
	@Override
	public List<KaleoTransition> findByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<KaleoTransition> orderByComparator,
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

		List<KaleoTransition> list = null;

		if (useFinderCache) {
			list = (List<KaleoTransition>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (KaleoTransition kaleoTransition : list) {
					if (companyId != kaleoTransition.getCompanyId()) {
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

			sb.append(_SQL_SELECT_KALEOTRANSITION_WHERE);

			sb.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(KaleoTransitionModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(companyId);

				list = (List<KaleoTransition>)QueryUtil.list(
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
	 * Returns the first kaleo transition in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching kaleo transition
	 * @throws NoSuchTransitionException if a matching kaleo transition could not be found
	 */
	@Override
	public KaleoTransition findByCompanyId_First(
			long companyId,
			OrderByComparator<KaleoTransition> orderByComparator)
		throws NoSuchTransitionException {

		KaleoTransition kaleoTransition = fetchByCompanyId_First(
			companyId, orderByComparator);

		if (kaleoTransition != null) {
			return kaleoTransition;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchTransitionException(sb.toString());
	}

	/**
	 * Returns the first kaleo transition in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching kaleo transition, or <code>null</code> if a matching kaleo transition could not be found
	 */
	@Override
	public KaleoTransition fetchByCompanyId_First(
		long companyId, OrderByComparator<KaleoTransition> orderByComparator) {

		List<KaleoTransition> list = findByCompanyId(
			companyId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last kaleo transition in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching kaleo transition
	 * @throws NoSuchTransitionException if a matching kaleo transition could not be found
	 */
	@Override
	public KaleoTransition findByCompanyId_Last(
			long companyId,
			OrderByComparator<KaleoTransition> orderByComparator)
		throws NoSuchTransitionException {

		KaleoTransition kaleoTransition = fetchByCompanyId_Last(
			companyId, orderByComparator);

		if (kaleoTransition != null) {
			return kaleoTransition;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchTransitionException(sb.toString());
	}

	/**
	 * Returns the last kaleo transition in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching kaleo transition, or <code>null</code> if a matching kaleo transition could not be found
	 */
	@Override
	public KaleoTransition fetchByCompanyId_Last(
		long companyId, OrderByComparator<KaleoTransition> orderByComparator) {

		int count = countByCompanyId(companyId);

		if (count == 0) {
			return null;
		}

		List<KaleoTransition> list = findByCompanyId(
			companyId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the kaleo transitions before and after the current kaleo transition in the ordered set where companyId = &#63;.
	 *
	 * @param kaleoTransitionId the primary key of the current kaleo transition
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next kaleo transition
	 * @throws NoSuchTransitionException if a kaleo transition with the primary key could not be found
	 */
	@Override
	public KaleoTransition[] findByCompanyId_PrevAndNext(
			long kaleoTransitionId, long companyId,
			OrderByComparator<KaleoTransition> orderByComparator)
		throws NoSuchTransitionException {

		KaleoTransition kaleoTransition = findByPrimaryKey(kaleoTransitionId);

		Session session = null;

		try {
			session = openSession();

			KaleoTransition[] array = new KaleoTransitionImpl[3];

			array[0] = getByCompanyId_PrevAndNext(
				session, kaleoTransition, companyId, orderByComparator, true);

			array[1] = kaleoTransition;

			array[2] = getByCompanyId_PrevAndNext(
				session, kaleoTransition, companyId, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected KaleoTransition getByCompanyId_PrevAndNext(
		Session session, KaleoTransition kaleoTransition, long companyId,
		OrderByComparator<KaleoTransition> orderByComparator,
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

		sb.append(_SQL_SELECT_KALEOTRANSITION_WHERE);

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
			sb.append(KaleoTransitionModelImpl.ORDER_BY_JPQL);
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
						kaleoTransition)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<KaleoTransition> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the kaleo transitions where companyId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 */
	@Override
	public void removeByCompanyId(long companyId) {
		for (KaleoTransition kaleoTransition :
				findByCompanyId(
					companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(kaleoTransition);
		}
	}

	/**
	 * Returns the number of kaleo transitions where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching kaleo transitions
	 */
	@Override
	public int countByCompanyId(long companyId) {
		FinderPath finderPath = _finderPathCountByCompanyId;

		Object[] finderArgs = new Object[] {companyId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_KALEOTRANSITION_WHERE);

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
		"kaleoTransition.companyId = ?";

	private FinderPath _finderPathWithPaginationFindByKaleoDefinitionVersionId;
	private FinderPath
		_finderPathWithoutPaginationFindByKaleoDefinitionVersionId;
	private FinderPath _finderPathCountByKaleoDefinitionVersionId;

	/**
	 * Returns all the kaleo transitions where kaleoDefinitionVersionId = &#63;.
	 *
	 * @param kaleoDefinitionVersionId the kaleo definition version ID
	 * @return the matching kaleo transitions
	 */
	@Override
	public List<KaleoTransition> findByKaleoDefinitionVersionId(
		long kaleoDefinitionVersionId) {

		return findByKaleoDefinitionVersionId(
			kaleoDefinitionVersionId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the kaleo transitions where kaleoDefinitionVersionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoTransitionModelImpl</code>.
	 * </p>
	 *
	 * @param kaleoDefinitionVersionId the kaleo definition version ID
	 * @param start the lower bound of the range of kaleo transitions
	 * @param end the upper bound of the range of kaleo transitions (not inclusive)
	 * @return the range of matching kaleo transitions
	 */
	@Override
	public List<KaleoTransition> findByKaleoDefinitionVersionId(
		long kaleoDefinitionVersionId, int start, int end) {

		return findByKaleoDefinitionVersionId(
			kaleoDefinitionVersionId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the kaleo transitions where kaleoDefinitionVersionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoTransitionModelImpl</code>.
	 * </p>
	 *
	 * @param kaleoDefinitionVersionId the kaleo definition version ID
	 * @param start the lower bound of the range of kaleo transitions
	 * @param end the upper bound of the range of kaleo transitions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching kaleo transitions
	 */
	@Override
	public List<KaleoTransition> findByKaleoDefinitionVersionId(
		long kaleoDefinitionVersionId, int start, int end,
		OrderByComparator<KaleoTransition> orderByComparator) {

		return findByKaleoDefinitionVersionId(
			kaleoDefinitionVersionId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the kaleo transitions where kaleoDefinitionVersionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoTransitionModelImpl</code>.
	 * </p>
	 *
	 * @param kaleoDefinitionVersionId the kaleo definition version ID
	 * @param start the lower bound of the range of kaleo transitions
	 * @param end the upper bound of the range of kaleo transitions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching kaleo transitions
	 */
	@Override
	public List<KaleoTransition> findByKaleoDefinitionVersionId(
		long kaleoDefinitionVersionId, int start, int end,
		OrderByComparator<KaleoTransition> orderByComparator,
		boolean useFinderCache) {

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

		List<KaleoTransition> list = null;

		if (useFinderCache) {
			list = (List<KaleoTransition>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (KaleoTransition kaleoTransition : list) {
					if (kaleoDefinitionVersionId !=
							kaleoTransition.getKaleoDefinitionVersionId()) {

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

			sb.append(_SQL_SELECT_KALEOTRANSITION_WHERE);

			sb.append(
				_FINDER_COLUMN_KALEODEFINITIONVERSIONID_KALEODEFINITIONVERSIONID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(KaleoTransitionModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(kaleoDefinitionVersionId);

				list = (List<KaleoTransition>)QueryUtil.list(
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
	 * Returns the first kaleo transition in the ordered set where kaleoDefinitionVersionId = &#63;.
	 *
	 * @param kaleoDefinitionVersionId the kaleo definition version ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching kaleo transition
	 * @throws NoSuchTransitionException if a matching kaleo transition could not be found
	 */
	@Override
	public KaleoTransition findByKaleoDefinitionVersionId_First(
			long kaleoDefinitionVersionId,
			OrderByComparator<KaleoTransition> orderByComparator)
		throws NoSuchTransitionException {

		KaleoTransition kaleoTransition = fetchByKaleoDefinitionVersionId_First(
			kaleoDefinitionVersionId, orderByComparator);

		if (kaleoTransition != null) {
			return kaleoTransition;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("kaleoDefinitionVersionId=");
		sb.append(kaleoDefinitionVersionId);

		sb.append("}");

		throw new NoSuchTransitionException(sb.toString());
	}

	/**
	 * Returns the first kaleo transition in the ordered set where kaleoDefinitionVersionId = &#63;.
	 *
	 * @param kaleoDefinitionVersionId the kaleo definition version ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching kaleo transition, or <code>null</code> if a matching kaleo transition could not be found
	 */
	@Override
	public KaleoTransition fetchByKaleoDefinitionVersionId_First(
		long kaleoDefinitionVersionId,
		OrderByComparator<KaleoTransition> orderByComparator) {

		List<KaleoTransition> list = findByKaleoDefinitionVersionId(
			kaleoDefinitionVersionId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last kaleo transition in the ordered set where kaleoDefinitionVersionId = &#63;.
	 *
	 * @param kaleoDefinitionVersionId the kaleo definition version ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching kaleo transition
	 * @throws NoSuchTransitionException if a matching kaleo transition could not be found
	 */
	@Override
	public KaleoTransition findByKaleoDefinitionVersionId_Last(
			long kaleoDefinitionVersionId,
			OrderByComparator<KaleoTransition> orderByComparator)
		throws NoSuchTransitionException {

		KaleoTransition kaleoTransition = fetchByKaleoDefinitionVersionId_Last(
			kaleoDefinitionVersionId, orderByComparator);

		if (kaleoTransition != null) {
			return kaleoTransition;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("kaleoDefinitionVersionId=");
		sb.append(kaleoDefinitionVersionId);

		sb.append("}");

		throw new NoSuchTransitionException(sb.toString());
	}

	/**
	 * Returns the last kaleo transition in the ordered set where kaleoDefinitionVersionId = &#63;.
	 *
	 * @param kaleoDefinitionVersionId the kaleo definition version ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching kaleo transition, or <code>null</code> if a matching kaleo transition could not be found
	 */
	@Override
	public KaleoTransition fetchByKaleoDefinitionVersionId_Last(
		long kaleoDefinitionVersionId,
		OrderByComparator<KaleoTransition> orderByComparator) {

		int count = countByKaleoDefinitionVersionId(kaleoDefinitionVersionId);

		if (count == 0) {
			return null;
		}

		List<KaleoTransition> list = findByKaleoDefinitionVersionId(
			kaleoDefinitionVersionId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the kaleo transitions before and after the current kaleo transition in the ordered set where kaleoDefinitionVersionId = &#63;.
	 *
	 * @param kaleoTransitionId the primary key of the current kaleo transition
	 * @param kaleoDefinitionVersionId the kaleo definition version ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next kaleo transition
	 * @throws NoSuchTransitionException if a kaleo transition with the primary key could not be found
	 */
	@Override
	public KaleoTransition[] findByKaleoDefinitionVersionId_PrevAndNext(
			long kaleoTransitionId, long kaleoDefinitionVersionId,
			OrderByComparator<KaleoTransition> orderByComparator)
		throws NoSuchTransitionException {

		KaleoTransition kaleoTransition = findByPrimaryKey(kaleoTransitionId);

		Session session = null;

		try {
			session = openSession();

			KaleoTransition[] array = new KaleoTransitionImpl[3];

			array[0] = getByKaleoDefinitionVersionId_PrevAndNext(
				session, kaleoTransition, kaleoDefinitionVersionId,
				orderByComparator, true);

			array[1] = kaleoTransition;

			array[2] = getByKaleoDefinitionVersionId_PrevAndNext(
				session, kaleoTransition, kaleoDefinitionVersionId,
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

	protected KaleoTransition getByKaleoDefinitionVersionId_PrevAndNext(
		Session session, KaleoTransition kaleoTransition,
		long kaleoDefinitionVersionId,
		OrderByComparator<KaleoTransition> orderByComparator,
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

		sb.append(_SQL_SELECT_KALEOTRANSITION_WHERE);

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
			sb.append(KaleoTransitionModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(kaleoDefinitionVersionId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						kaleoTransition)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<KaleoTransition> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the kaleo transitions where kaleoDefinitionVersionId = &#63; from the database.
	 *
	 * @param kaleoDefinitionVersionId the kaleo definition version ID
	 */
	@Override
	public void removeByKaleoDefinitionVersionId(
		long kaleoDefinitionVersionId) {

		for (KaleoTransition kaleoTransition :
				findByKaleoDefinitionVersionId(
					kaleoDefinitionVersionId, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			remove(kaleoTransition);
		}
	}

	/**
	 * Returns the number of kaleo transitions where kaleoDefinitionVersionId = &#63;.
	 *
	 * @param kaleoDefinitionVersionId the kaleo definition version ID
	 * @return the number of matching kaleo transitions
	 */
	@Override
	public int countByKaleoDefinitionVersionId(long kaleoDefinitionVersionId) {
		FinderPath finderPath = _finderPathCountByKaleoDefinitionVersionId;

		Object[] finderArgs = new Object[] {kaleoDefinitionVersionId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_KALEOTRANSITION_WHERE);

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
			"kaleoTransition.kaleoDefinitionVersionId = ?";

	private FinderPath _finderPathWithPaginationFindByKaleoNodeId;
	private FinderPath _finderPathWithoutPaginationFindByKaleoNodeId;
	private FinderPath _finderPathCountByKaleoNodeId;

	/**
	 * Returns all the kaleo transitions where kaleoNodeId = &#63;.
	 *
	 * @param kaleoNodeId the kaleo node ID
	 * @return the matching kaleo transitions
	 */
	@Override
	public List<KaleoTransition> findByKaleoNodeId(long kaleoNodeId) {
		return findByKaleoNodeId(
			kaleoNodeId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the kaleo transitions where kaleoNodeId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoTransitionModelImpl</code>.
	 * </p>
	 *
	 * @param kaleoNodeId the kaleo node ID
	 * @param start the lower bound of the range of kaleo transitions
	 * @param end the upper bound of the range of kaleo transitions (not inclusive)
	 * @return the range of matching kaleo transitions
	 */
	@Override
	public List<KaleoTransition> findByKaleoNodeId(
		long kaleoNodeId, int start, int end) {

		return findByKaleoNodeId(kaleoNodeId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the kaleo transitions where kaleoNodeId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoTransitionModelImpl</code>.
	 * </p>
	 *
	 * @param kaleoNodeId the kaleo node ID
	 * @param start the lower bound of the range of kaleo transitions
	 * @param end the upper bound of the range of kaleo transitions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching kaleo transitions
	 */
	@Override
	public List<KaleoTransition> findByKaleoNodeId(
		long kaleoNodeId, int start, int end,
		OrderByComparator<KaleoTransition> orderByComparator) {

		return findByKaleoNodeId(
			kaleoNodeId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the kaleo transitions where kaleoNodeId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoTransitionModelImpl</code>.
	 * </p>
	 *
	 * @param kaleoNodeId the kaleo node ID
	 * @param start the lower bound of the range of kaleo transitions
	 * @param end the upper bound of the range of kaleo transitions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching kaleo transitions
	 */
	@Override
	public List<KaleoTransition> findByKaleoNodeId(
		long kaleoNodeId, int start, int end,
		OrderByComparator<KaleoTransition> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByKaleoNodeId;
				finderArgs = new Object[] {kaleoNodeId};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByKaleoNodeId;
			finderArgs = new Object[] {
				kaleoNodeId, start, end, orderByComparator
			};
		}

		List<KaleoTransition> list = null;

		if (useFinderCache) {
			list = (List<KaleoTransition>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (KaleoTransition kaleoTransition : list) {
					if (kaleoNodeId != kaleoTransition.getKaleoNodeId()) {
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

			sb.append(_SQL_SELECT_KALEOTRANSITION_WHERE);

			sb.append(_FINDER_COLUMN_KALEONODEID_KALEONODEID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(KaleoTransitionModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(kaleoNodeId);

				list = (List<KaleoTransition>)QueryUtil.list(
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
	 * Returns the first kaleo transition in the ordered set where kaleoNodeId = &#63;.
	 *
	 * @param kaleoNodeId the kaleo node ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching kaleo transition
	 * @throws NoSuchTransitionException if a matching kaleo transition could not be found
	 */
	@Override
	public KaleoTransition findByKaleoNodeId_First(
			long kaleoNodeId,
			OrderByComparator<KaleoTransition> orderByComparator)
		throws NoSuchTransitionException {

		KaleoTransition kaleoTransition = fetchByKaleoNodeId_First(
			kaleoNodeId, orderByComparator);

		if (kaleoTransition != null) {
			return kaleoTransition;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("kaleoNodeId=");
		sb.append(kaleoNodeId);

		sb.append("}");

		throw new NoSuchTransitionException(sb.toString());
	}

	/**
	 * Returns the first kaleo transition in the ordered set where kaleoNodeId = &#63;.
	 *
	 * @param kaleoNodeId the kaleo node ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching kaleo transition, or <code>null</code> if a matching kaleo transition could not be found
	 */
	@Override
	public KaleoTransition fetchByKaleoNodeId_First(
		long kaleoNodeId,
		OrderByComparator<KaleoTransition> orderByComparator) {

		List<KaleoTransition> list = findByKaleoNodeId(
			kaleoNodeId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last kaleo transition in the ordered set where kaleoNodeId = &#63;.
	 *
	 * @param kaleoNodeId the kaleo node ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching kaleo transition
	 * @throws NoSuchTransitionException if a matching kaleo transition could not be found
	 */
	@Override
	public KaleoTransition findByKaleoNodeId_Last(
			long kaleoNodeId,
			OrderByComparator<KaleoTransition> orderByComparator)
		throws NoSuchTransitionException {

		KaleoTransition kaleoTransition = fetchByKaleoNodeId_Last(
			kaleoNodeId, orderByComparator);

		if (kaleoTransition != null) {
			return kaleoTransition;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("kaleoNodeId=");
		sb.append(kaleoNodeId);

		sb.append("}");

		throw new NoSuchTransitionException(sb.toString());
	}

	/**
	 * Returns the last kaleo transition in the ordered set where kaleoNodeId = &#63;.
	 *
	 * @param kaleoNodeId the kaleo node ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching kaleo transition, or <code>null</code> if a matching kaleo transition could not be found
	 */
	@Override
	public KaleoTransition fetchByKaleoNodeId_Last(
		long kaleoNodeId,
		OrderByComparator<KaleoTransition> orderByComparator) {

		int count = countByKaleoNodeId(kaleoNodeId);

		if (count == 0) {
			return null;
		}

		List<KaleoTransition> list = findByKaleoNodeId(
			kaleoNodeId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the kaleo transitions before and after the current kaleo transition in the ordered set where kaleoNodeId = &#63;.
	 *
	 * @param kaleoTransitionId the primary key of the current kaleo transition
	 * @param kaleoNodeId the kaleo node ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next kaleo transition
	 * @throws NoSuchTransitionException if a kaleo transition with the primary key could not be found
	 */
	@Override
	public KaleoTransition[] findByKaleoNodeId_PrevAndNext(
			long kaleoTransitionId, long kaleoNodeId,
			OrderByComparator<KaleoTransition> orderByComparator)
		throws NoSuchTransitionException {

		KaleoTransition kaleoTransition = findByPrimaryKey(kaleoTransitionId);

		Session session = null;

		try {
			session = openSession();

			KaleoTransition[] array = new KaleoTransitionImpl[3];

			array[0] = getByKaleoNodeId_PrevAndNext(
				session, kaleoTransition, kaleoNodeId, orderByComparator, true);

			array[1] = kaleoTransition;

			array[2] = getByKaleoNodeId_PrevAndNext(
				session, kaleoTransition, kaleoNodeId, orderByComparator,
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

	protected KaleoTransition getByKaleoNodeId_PrevAndNext(
		Session session, KaleoTransition kaleoTransition, long kaleoNodeId,
		OrderByComparator<KaleoTransition> orderByComparator,
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

		sb.append(_SQL_SELECT_KALEOTRANSITION_WHERE);

		sb.append(_FINDER_COLUMN_KALEONODEID_KALEONODEID_2);

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
			sb.append(KaleoTransitionModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(kaleoNodeId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						kaleoTransition)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<KaleoTransition> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the kaleo transitions where kaleoNodeId = &#63; from the database.
	 *
	 * @param kaleoNodeId the kaleo node ID
	 */
	@Override
	public void removeByKaleoNodeId(long kaleoNodeId) {
		for (KaleoTransition kaleoTransition :
				findByKaleoNodeId(
					kaleoNodeId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(kaleoTransition);
		}
	}

	/**
	 * Returns the number of kaleo transitions where kaleoNodeId = &#63;.
	 *
	 * @param kaleoNodeId the kaleo node ID
	 * @return the number of matching kaleo transitions
	 */
	@Override
	public int countByKaleoNodeId(long kaleoNodeId) {
		FinderPath finderPath = _finderPathCountByKaleoNodeId;

		Object[] finderArgs = new Object[] {kaleoNodeId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_KALEOTRANSITION_WHERE);

			sb.append(_FINDER_COLUMN_KALEONODEID_KALEONODEID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(kaleoNodeId);

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

	private static final String _FINDER_COLUMN_KALEONODEID_KALEONODEID_2 =
		"kaleoTransition.kaleoNodeId = ?";

	private FinderPath _finderPathFetchByKNI_N;
	private FinderPath _finderPathCountByKNI_N;

	/**
	 * Returns the kaleo transition where kaleoNodeId = &#63; and name = &#63; or throws a <code>NoSuchTransitionException</code> if it could not be found.
	 *
	 * @param kaleoNodeId the kaleo node ID
	 * @param name the name
	 * @return the matching kaleo transition
	 * @throws NoSuchTransitionException if a matching kaleo transition could not be found
	 */
	@Override
	public KaleoTransition findByKNI_N(long kaleoNodeId, String name)
		throws NoSuchTransitionException {

		KaleoTransition kaleoTransition = fetchByKNI_N(kaleoNodeId, name);

		if (kaleoTransition == null) {
			StringBundler sb = new StringBundler(6);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("kaleoNodeId=");
			sb.append(kaleoNodeId);

			sb.append(", name=");
			sb.append(name);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchTransitionException(sb.toString());
		}

		return kaleoTransition;
	}

	/**
	 * Returns the kaleo transition where kaleoNodeId = &#63; and name = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param kaleoNodeId the kaleo node ID
	 * @param name the name
	 * @return the matching kaleo transition, or <code>null</code> if a matching kaleo transition could not be found
	 */
	@Override
	public KaleoTransition fetchByKNI_N(long kaleoNodeId, String name) {
		return fetchByKNI_N(kaleoNodeId, name, true);
	}

	/**
	 * Returns the kaleo transition where kaleoNodeId = &#63; and name = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param kaleoNodeId the kaleo node ID
	 * @param name the name
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching kaleo transition, or <code>null</code> if a matching kaleo transition could not be found
	 */
	@Override
	public KaleoTransition fetchByKNI_N(
		long kaleoNodeId, String name, boolean useFinderCache) {

		name = Objects.toString(name, "");

		Object[] finderArgs = null;

		if (useFinderCache) {
			finderArgs = new Object[] {kaleoNodeId, name};
		}

		Object result = null;

		if (useFinderCache) {
			result = finderCache.getResult(_finderPathFetchByKNI_N, finderArgs);
		}

		if (result instanceof KaleoTransition) {
			KaleoTransition kaleoTransition = (KaleoTransition)result;

			if ((kaleoNodeId != kaleoTransition.getKaleoNodeId()) ||
				!Objects.equals(name, kaleoTransition.getName())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_SELECT_KALEOTRANSITION_WHERE);

			sb.append(_FINDER_COLUMN_KNI_N_KALEONODEID_2);

			boolean bindName = false;

			if (name.isEmpty()) {
				sb.append(_FINDER_COLUMN_KNI_N_NAME_3);
			}
			else {
				bindName = true;

				sb.append(_FINDER_COLUMN_KNI_N_NAME_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(kaleoNodeId);

				if (bindName) {
					queryPos.add(name);
				}

				List<KaleoTransition> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache) {
						finderCache.putResult(
							_finderPathFetchByKNI_N, finderArgs, list);
					}
				}
				else {
					if (list.size() > 1) {
						Collections.sort(list, Collections.reverseOrder());

						if (_log.isWarnEnabled()) {
							if (!useFinderCache) {
								finderArgs = new Object[] {kaleoNodeId, name};
							}

							_log.warn(
								"KaleoTransitionPersistenceImpl.fetchByKNI_N(long, String, boolean) with parameters (" +
									StringUtil.merge(finderArgs) +
										") yields a result set with more than 1 result. This violates the logical unique restriction. There is no order guarantee on which result is returned by this finder.");
						}
					}

					KaleoTransition kaleoTransition = list.get(0);

					result = kaleoTransition;

					cacheResult(kaleoTransition);
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
			return (KaleoTransition)result;
		}
	}

	/**
	 * Removes the kaleo transition where kaleoNodeId = &#63; and name = &#63; from the database.
	 *
	 * @param kaleoNodeId the kaleo node ID
	 * @param name the name
	 * @return the kaleo transition that was removed
	 */
	@Override
	public KaleoTransition removeByKNI_N(long kaleoNodeId, String name)
		throws NoSuchTransitionException {

		KaleoTransition kaleoTransition = findByKNI_N(kaleoNodeId, name);

		return remove(kaleoTransition);
	}

	/**
	 * Returns the number of kaleo transitions where kaleoNodeId = &#63; and name = &#63;.
	 *
	 * @param kaleoNodeId the kaleo node ID
	 * @param name the name
	 * @return the number of matching kaleo transitions
	 */
	@Override
	public int countByKNI_N(long kaleoNodeId, String name) {
		name = Objects.toString(name, "");

		FinderPath finderPath = _finderPathCountByKNI_N;

		Object[] finderArgs = new Object[] {kaleoNodeId, name};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_KALEOTRANSITION_WHERE);

			sb.append(_FINDER_COLUMN_KNI_N_KALEONODEID_2);

			boolean bindName = false;

			if (name.isEmpty()) {
				sb.append(_FINDER_COLUMN_KNI_N_NAME_3);
			}
			else {
				bindName = true;

				sb.append(_FINDER_COLUMN_KNI_N_NAME_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(kaleoNodeId);

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

	private static final String _FINDER_COLUMN_KNI_N_KALEONODEID_2 =
		"kaleoTransition.kaleoNodeId = ? AND ";

	private static final String _FINDER_COLUMN_KNI_N_NAME_2 =
		"kaleoTransition.name = ?";

	private static final String _FINDER_COLUMN_KNI_N_NAME_3 =
		"(kaleoTransition.name IS NULL OR kaleoTransition.name = '')";

	private FinderPath _finderPathFetchByKNI_DT;
	private FinderPath _finderPathCountByKNI_DT;

	/**
	 * Returns the kaleo transition where kaleoNodeId = &#63; and defaultTransition = &#63; or throws a <code>NoSuchTransitionException</code> if it could not be found.
	 *
	 * @param kaleoNodeId the kaleo node ID
	 * @param defaultTransition the default transition
	 * @return the matching kaleo transition
	 * @throws NoSuchTransitionException if a matching kaleo transition could not be found
	 */
	@Override
	public KaleoTransition findByKNI_DT(
			long kaleoNodeId, boolean defaultTransition)
		throws NoSuchTransitionException {

		KaleoTransition kaleoTransition = fetchByKNI_DT(
			kaleoNodeId, defaultTransition);

		if (kaleoTransition == null) {
			StringBundler sb = new StringBundler(6);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("kaleoNodeId=");
			sb.append(kaleoNodeId);

			sb.append(", defaultTransition=");
			sb.append(defaultTransition);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchTransitionException(sb.toString());
		}

		return kaleoTransition;
	}

	/**
	 * Returns the kaleo transition where kaleoNodeId = &#63; and defaultTransition = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param kaleoNodeId the kaleo node ID
	 * @param defaultTransition the default transition
	 * @return the matching kaleo transition, or <code>null</code> if a matching kaleo transition could not be found
	 */
	@Override
	public KaleoTransition fetchByKNI_DT(
		long kaleoNodeId, boolean defaultTransition) {

		return fetchByKNI_DT(kaleoNodeId, defaultTransition, true);
	}

	/**
	 * Returns the kaleo transition where kaleoNodeId = &#63; and defaultTransition = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param kaleoNodeId the kaleo node ID
	 * @param defaultTransition the default transition
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching kaleo transition, or <code>null</code> if a matching kaleo transition could not be found
	 */
	@Override
	public KaleoTransition fetchByKNI_DT(
		long kaleoNodeId, boolean defaultTransition, boolean useFinderCache) {

		Object[] finderArgs = null;

		if (useFinderCache) {
			finderArgs = new Object[] {kaleoNodeId, defaultTransition};
		}

		Object result = null;

		if (useFinderCache) {
			result = finderCache.getResult(
				_finderPathFetchByKNI_DT, finderArgs);
		}

		if (result instanceof KaleoTransition) {
			KaleoTransition kaleoTransition = (KaleoTransition)result;

			if ((kaleoNodeId != kaleoTransition.getKaleoNodeId()) ||
				(defaultTransition != kaleoTransition.isDefaultTransition())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_SELECT_KALEOTRANSITION_WHERE);

			sb.append(_FINDER_COLUMN_KNI_DT_KALEONODEID_2);

			sb.append(_FINDER_COLUMN_KNI_DT_DEFAULTTRANSITION_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(kaleoNodeId);

				queryPos.add(defaultTransition);

				List<KaleoTransition> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache) {
						finderCache.putResult(
							_finderPathFetchByKNI_DT, finderArgs, list);
					}
				}
				else {
					if (list.size() > 1) {
						Collections.sort(list, Collections.reverseOrder());

						if (_log.isWarnEnabled()) {
							if (!useFinderCache) {
								finderArgs = new Object[] {
									kaleoNodeId, defaultTransition
								};
							}

							_log.warn(
								"KaleoTransitionPersistenceImpl.fetchByKNI_DT(long, boolean, boolean) with parameters (" +
									StringUtil.merge(finderArgs) +
										") yields a result set with more than 1 result. This violates the logical unique restriction. There is no order guarantee on which result is returned by this finder.");
						}
					}

					KaleoTransition kaleoTransition = list.get(0);

					result = kaleoTransition;

					cacheResult(kaleoTransition);
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
			return (KaleoTransition)result;
		}
	}

	/**
	 * Removes the kaleo transition where kaleoNodeId = &#63; and defaultTransition = &#63; from the database.
	 *
	 * @param kaleoNodeId the kaleo node ID
	 * @param defaultTransition the default transition
	 * @return the kaleo transition that was removed
	 */
	@Override
	public KaleoTransition removeByKNI_DT(
			long kaleoNodeId, boolean defaultTransition)
		throws NoSuchTransitionException {

		KaleoTransition kaleoTransition = findByKNI_DT(
			kaleoNodeId, defaultTransition);

		return remove(kaleoTransition);
	}

	/**
	 * Returns the number of kaleo transitions where kaleoNodeId = &#63; and defaultTransition = &#63;.
	 *
	 * @param kaleoNodeId the kaleo node ID
	 * @param defaultTransition the default transition
	 * @return the number of matching kaleo transitions
	 */
	@Override
	public int countByKNI_DT(long kaleoNodeId, boolean defaultTransition) {
		FinderPath finderPath = _finderPathCountByKNI_DT;

		Object[] finderArgs = new Object[] {kaleoNodeId, defaultTransition};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_KALEOTRANSITION_WHERE);

			sb.append(_FINDER_COLUMN_KNI_DT_KALEONODEID_2);

			sb.append(_FINDER_COLUMN_KNI_DT_DEFAULTTRANSITION_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(kaleoNodeId);

				queryPos.add(defaultTransition);

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

	private static final String _FINDER_COLUMN_KNI_DT_KALEONODEID_2 =
		"kaleoTransition.kaleoNodeId = ? AND ";

	private static final String _FINDER_COLUMN_KNI_DT_DEFAULTTRANSITION_2 =
		"kaleoTransition.defaultTransition = ?";

	public KaleoTransitionPersistenceImpl() {
		setModelClass(KaleoTransition.class);

		setModelImplClass(KaleoTransitionImpl.class);
		setModelPKClass(long.class);

		setTable(KaleoTransitionTable.INSTANCE);
	}

	/**
	 * Caches the kaleo transition in the entity cache if it is enabled.
	 *
	 * @param kaleoTransition the kaleo transition
	 */
	@Override
	public void cacheResult(KaleoTransition kaleoTransition) {
		entityCache.putResult(
			KaleoTransitionImpl.class, kaleoTransition.getPrimaryKey(),
			kaleoTransition);

		finderCache.putResult(
			_finderPathFetchByKNI_N,
			new Object[] {
				kaleoTransition.getKaleoNodeId(), kaleoTransition.getName()
			},
			kaleoTransition);

		finderCache.putResult(
			_finderPathFetchByKNI_DT,
			new Object[] {
				kaleoTransition.getKaleoNodeId(),
				kaleoTransition.isDefaultTransition()
			},
			kaleoTransition);
	}

	private int _valueObjectFinderCacheListThreshold;

	/**
	 * Caches the kaleo transitions in the entity cache if it is enabled.
	 *
	 * @param kaleoTransitions the kaleo transitions
	 */
	@Override
	public void cacheResult(List<KaleoTransition> kaleoTransitions) {
		if ((_valueObjectFinderCacheListThreshold == 0) ||
			((_valueObjectFinderCacheListThreshold > 0) &&
			 (kaleoTransitions.size() >
				 _valueObjectFinderCacheListThreshold))) {

			return;
		}

		for (KaleoTransition kaleoTransition : kaleoTransitions) {
			if (entityCache.getResult(
					KaleoTransitionImpl.class,
					kaleoTransition.getPrimaryKey()) == null) {

				cacheResult(kaleoTransition);
			}
		}
	}

	/**
	 * Clears the cache for all kaleo transitions.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(KaleoTransitionImpl.class);

		finderCache.clearCache(KaleoTransitionImpl.class);
	}

	/**
	 * Clears the cache for the kaleo transition.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(KaleoTransition kaleoTransition) {
		entityCache.removeResult(KaleoTransitionImpl.class, kaleoTransition);
	}

	@Override
	public void clearCache(List<KaleoTransition> kaleoTransitions) {
		for (KaleoTransition kaleoTransition : kaleoTransitions) {
			entityCache.removeResult(
				KaleoTransitionImpl.class, kaleoTransition);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(KaleoTransitionImpl.class);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(KaleoTransitionImpl.class, primaryKey);
		}
	}

	protected void cacheUniqueFindersCache(
		KaleoTransitionModelImpl kaleoTransitionModelImpl) {

		Object[] args = new Object[] {
			kaleoTransitionModelImpl.getKaleoNodeId(),
			kaleoTransitionModelImpl.getName()
		};

		finderCache.putResult(_finderPathCountByKNI_N, args, Long.valueOf(1));
		finderCache.putResult(
			_finderPathFetchByKNI_N, args, kaleoTransitionModelImpl);

		args = new Object[] {
			kaleoTransitionModelImpl.getKaleoNodeId(),
			kaleoTransitionModelImpl.isDefaultTransition()
		};

		finderCache.putResult(_finderPathCountByKNI_DT, args, Long.valueOf(1));
		finderCache.putResult(
			_finderPathFetchByKNI_DT, args, kaleoTransitionModelImpl);
	}

	/**
	 * Creates a new kaleo transition with the primary key. Does not add the kaleo transition to the database.
	 *
	 * @param kaleoTransitionId the primary key for the new kaleo transition
	 * @return the new kaleo transition
	 */
	@Override
	public KaleoTransition create(long kaleoTransitionId) {
		KaleoTransition kaleoTransition = new KaleoTransitionImpl();

		kaleoTransition.setNew(true);
		kaleoTransition.setPrimaryKey(kaleoTransitionId);

		kaleoTransition.setCompanyId(CompanyThreadLocal.getCompanyId());

		return kaleoTransition;
	}

	/**
	 * Removes the kaleo transition with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param kaleoTransitionId the primary key of the kaleo transition
	 * @return the kaleo transition that was removed
	 * @throws NoSuchTransitionException if a kaleo transition with the primary key could not be found
	 */
	@Override
	public KaleoTransition remove(long kaleoTransitionId)
		throws NoSuchTransitionException {

		return remove((Serializable)kaleoTransitionId);
	}

	/**
	 * Removes the kaleo transition with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the kaleo transition
	 * @return the kaleo transition that was removed
	 * @throws NoSuchTransitionException if a kaleo transition with the primary key could not be found
	 */
	@Override
	public KaleoTransition remove(Serializable primaryKey)
		throws NoSuchTransitionException {

		Session session = null;

		try {
			session = openSession();

			KaleoTransition kaleoTransition = (KaleoTransition)session.get(
				KaleoTransitionImpl.class, primaryKey);

			if (kaleoTransition == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchTransitionException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(kaleoTransition);
		}
		catch (NoSuchTransitionException noSuchEntityException) {
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
	protected KaleoTransition removeImpl(KaleoTransition kaleoTransition) {
		Session session = null;

		try {
			session = openSession();

			if (!session.contains(kaleoTransition)) {
				kaleoTransition = (KaleoTransition)session.get(
					KaleoTransitionImpl.class,
					kaleoTransition.getPrimaryKeyObj());
			}

			if (kaleoTransition != null) {
				session.delete(kaleoTransition);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (kaleoTransition != null) {
			clearCache(kaleoTransition);
		}

		return kaleoTransition;
	}

	@Override
	public KaleoTransition updateImpl(KaleoTransition kaleoTransition) {
		boolean isNew = kaleoTransition.isNew();

		if (!(kaleoTransition instanceof KaleoTransitionModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(kaleoTransition.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					kaleoTransition);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in kaleoTransition proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom KaleoTransition implementation " +
					kaleoTransition.getClass());
		}

		KaleoTransitionModelImpl kaleoTransitionModelImpl =
			(KaleoTransitionModelImpl)kaleoTransition;

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Date date = new Date();

		if (isNew && (kaleoTransition.getCreateDate() == null)) {
			if (serviceContext == null) {
				kaleoTransition.setCreateDate(date);
			}
			else {
				kaleoTransition.setCreateDate(
					serviceContext.getCreateDate(date));
			}
		}

		if (!kaleoTransitionModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				kaleoTransition.setModifiedDate(date);
			}
			else {
				kaleoTransition.setModifiedDate(
					serviceContext.getModifiedDate(date));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (isNew) {
				session.save(kaleoTransition);
			}
			else {
				kaleoTransition = (KaleoTransition)session.merge(
					kaleoTransition);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		entityCache.putResult(
			KaleoTransitionImpl.class, kaleoTransitionModelImpl, false, true);

		cacheUniqueFindersCache(kaleoTransitionModelImpl);

		if (isNew) {
			kaleoTransition.setNew(false);
		}

		kaleoTransition.resetOriginalValues();

		return kaleoTransition;
	}

	/**
	 * Returns the kaleo transition with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the kaleo transition
	 * @return the kaleo transition
	 * @throws NoSuchTransitionException if a kaleo transition with the primary key could not be found
	 */
	@Override
	public KaleoTransition findByPrimaryKey(Serializable primaryKey)
		throws NoSuchTransitionException {

		KaleoTransition kaleoTransition = fetchByPrimaryKey(primaryKey);

		if (kaleoTransition == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchTransitionException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return kaleoTransition;
	}

	/**
	 * Returns the kaleo transition with the primary key or throws a <code>NoSuchTransitionException</code> if it could not be found.
	 *
	 * @param kaleoTransitionId the primary key of the kaleo transition
	 * @return the kaleo transition
	 * @throws NoSuchTransitionException if a kaleo transition with the primary key could not be found
	 */
	@Override
	public KaleoTransition findByPrimaryKey(long kaleoTransitionId)
		throws NoSuchTransitionException {

		return findByPrimaryKey((Serializable)kaleoTransitionId);
	}

	/**
	 * Returns the kaleo transition with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param kaleoTransitionId the primary key of the kaleo transition
	 * @return the kaleo transition, or <code>null</code> if a kaleo transition with the primary key could not be found
	 */
	@Override
	public KaleoTransition fetchByPrimaryKey(long kaleoTransitionId) {
		return fetchByPrimaryKey((Serializable)kaleoTransitionId);
	}

	/**
	 * Returns all the kaleo transitions.
	 *
	 * @return the kaleo transitions
	 */
	@Override
	public List<KaleoTransition> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the kaleo transitions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoTransitionModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of kaleo transitions
	 * @param end the upper bound of the range of kaleo transitions (not inclusive)
	 * @return the range of kaleo transitions
	 */
	@Override
	public List<KaleoTransition> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the kaleo transitions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoTransitionModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of kaleo transitions
	 * @param end the upper bound of the range of kaleo transitions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of kaleo transitions
	 */
	@Override
	public List<KaleoTransition> findAll(
		int start, int end,
		OrderByComparator<KaleoTransition> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the kaleo transitions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoTransitionModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of kaleo transitions
	 * @param end the upper bound of the range of kaleo transitions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of kaleo transitions
	 */
	@Override
	public List<KaleoTransition> findAll(
		int start, int end,
		OrderByComparator<KaleoTransition> orderByComparator,
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

		List<KaleoTransition> list = null;

		if (useFinderCache) {
			list = (List<KaleoTransition>)finderCache.getResult(
				finderPath, finderArgs);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_KALEOTRANSITION);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_KALEOTRANSITION;

				sql = sql.concat(KaleoTransitionModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<KaleoTransition>)QueryUtil.list(
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
	 * Removes all the kaleo transitions from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (KaleoTransition kaleoTransition : findAll()) {
			remove(kaleoTransition);
		}
	}

	/**
	 * Returns the number of kaleo transitions.
	 *
	 * @return the number of kaleo transitions
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(
			_finderPathCountAll, FINDER_ARGS_EMPTY);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(_SQL_COUNT_KALEOTRANSITION);

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
		return "kaleoTransitionId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_KALEOTRANSITION;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return KaleoTransitionModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the kaleo transition persistence.
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

		_finderPathWithPaginationFindByKaleoNodeId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByKaleoNodeId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"kaleoNodeId"}, true);

		_finderPathWithoutPaginationFindByKaleoNodeId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByKaleoNodeId",
			new String[] {Long.class.getName()}, new String[] {"kaleoNodeId"},
			true);

		_finderPathCountByKaleoNodeId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByKaleoNodeId",
			new String[] {Long.class.getName()}, new String[] {"kaleoNodeId"},
			false);

		_finderPathFetchByKNI_N = new FinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByKNI_N",
			new String[] {Long.class.getName(), String.class.getName()},
			new String[] {"kaleoNodeId", "name"}, true);

		_finderPathCountByKNI_N = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByKNI_N",
			new String[] {Long.class.getName(), String.class.getName()},
			new String[] {"kaleoNodeId", "name"}, false);

		_finderPathFetchByKNI_DT = new FinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByKNI_DT",
			new String[] {Long.class.getName(), Boolean.class.getName()},
			new String[] {"kaleoNodeId", "defaultTransition"}, true);

		_finderPathCountByKNI_DT = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByKNI_DT",
			new String[] {Long.class.getName(), Boolean.class.getName()},
			new String[] {"kaleoNodeId", "defaultTransition"}, false);
	}

	@Deactivate
	public void deactivate() {
		entityCache.removeCache(KaleoTransitionImpl.class.getName());
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

	private static final String _SQL_SELECT_KALEOTRANSITION =
		"SELECT kaleoTransition FROM KaleoTransition kaleoTransition";

	private static final String _SQL_SELECT_KALEOTRANSITION_WHERE =
		"SELECT kaleoTransition FROM KaleoTransition kaleoTransition WHERE ";

	private static final String _SQL_COUNT_KALEOTRANSITION =
		"SELECT COUNT(kaleoTransition) FROM KaleoTransition kaleoTransition";

	private static final String _SQL_COUNT_KALEOTRANSITION_WHERE =
		"SELECT COUNT(kaleoTransition) FROM KaleoTransition kaleoTransition WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS = "kaleoTransition.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No KaleoTransition exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No KaleoTransition exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		KaleoTransitionPersistenceImpl.class);

	@Override
	protected FinderCache getFinderCache() {
		return finderCache;
	}

	@Reference
	private KaleoTransitionModelArgumentsResolver
		_kaleoTransitionModelArgumentsResolver;

}