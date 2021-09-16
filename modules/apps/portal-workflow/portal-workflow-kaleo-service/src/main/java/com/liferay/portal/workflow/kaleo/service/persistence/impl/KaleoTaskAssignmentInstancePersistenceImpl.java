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
import com.liferay.portal.workflow.kaleo.exception.NoSuchTaskAssignmentInstanceException;
import com.liferay.portal.workflow.kaleo.model.KaleoTaskAssignmentInstance;
import com.liferay.portal.workflow.kaleo.model.KaleoTaskAssignmentInstanceTable;
import com.liferay.portal.workflow.kaleo.model.impl.KaleoTaskAssignmentInstanceImpl;
import com.liferay.portal.workflow.kaleo.model.impl.KaleoTaskAssignmentInstanceModelImpl;
import com.liferay.portal.workflow.kaleo.service.persistence.KaleoTaskAssignmentInstancePersistence;
import com.liferay.portal.workflow.kaleo.service.persistence.impl.constants.KaleoPersistenceConstants;

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
 * The persistence implementation for the kaleo task assignment instance service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
@Component(
	service = {
		KaleoTaskAssignmentInstancePersistence.class, BasePersistence.class
	}
)
public class KaleoTaskAssignmentInstancePersistenceImpl
	extends BasePersistenceImpl<KaleoTaskAssignmentInstance>
	implements KaleoTaskAssignmentInstancePersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>KaleoTaskAssignmentInstanceUtil</code> to access the kaleo task assignment instance persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		KaleoTaskAssignmentInstanceImpl.class.getName();

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
	 * Returns all the kaleo task assignment instances where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching kaleo task assignment instances
	 */
	@Override
	public List<KaleoTaskAssignmentInstance> findByCompanyId(long companyId) {
		return findByCompanyId(
			companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the kaleo task assignment instances where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoTaskAssignmentInstanceModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of kaleo task assignment instances
	 * @param end the upper bound of the range of kaleo task assignment instances (not inclusive)
	 * @return the range of matching kaleo task assignment instances
	 */
	@Override
	public List<KaleoTaskAssignmentInstance> findByCompanyId(
		long companyId, int start, int end) {

		return findByCompanyId(companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the kaleo task assignment instances where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoTaskAssignmentInstanceModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of kaleo task assignment instances
	 * @param end the upper bound of the range of kaleo task assignment instances (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching kaleo task assignment instances
	 */
	@Override
	public List<KaleoTaskAssignmentInstance> findByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<KaleoTaskAssignmentInstance> orderByComparator) {

		return findByCompanyId(companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the kaleo task assignment instances where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoTaskAssignmentInstanceModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of kaleo task assignment instances
	 * @param end the upper bound of the range of kaleo task assignment instances (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching kaleo task assignment instances
	 */
	@Override
	public List<KaleoTaskAssignmentInstance> findByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<KaleoTaskAssignmentInstance> orderByComparator,
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

		List<KaleoTaskAssignmentInstance> list = null;

		if (useFinderCache) {
			list = (List<KaleoTaskAssignmentInstance>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (KaleoTaskAssignmentInstance kaleoTaskAssignmentInstance :
						list) {

					if (companyId !=
							kaleoTaskAssignmentInstance.getCompanyId()) {

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

			sb.append(_SQL_SELECT_KALEOTASKASSIGNMENTINSTANCE_WHERE);

			sb.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(KaleoTaskAssignmentInstanceModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(companyId);

				list = (List<KaleoTaskAssignmentInstance>)QueryUtil.list(
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
	 * Returns the first kaleo task assignment instance in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching kaleo task assignment instance
	 * @throws NoSuchTaskAssignmentInstanceException if a matching kaleo task assignment instance could not be found
	 */
	@Override
	public KaleoTaskAssignmentInstance findByCompanyId_First(
			long companyId,
			OrderByComparator<KaleoTaskAssignmentInstance> orderByComparator)
		throws NoSuchTaskAssignmentInstanceException {

		KaleoTaskAssignmentInstance kaleoTaskAssignmentInstance =
			fetchByCompanyId_First(companyId, orderByComparator);

		if (kaleoTaskAssignmentInstance != null) {
			return kaleoTaskAssignmentInstance;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchTaskAssignmentInstanceException(sb.toString());
	}

	/**
	 * Returns the first kaleo task assignment instance in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching kaleo task assignment instance, or <code>null</code> if a matching kaleo task assignment instance could not be found
	 */
	@Override
	public KaleoTaskAssignmentInstance fetchByCompanyId_First(
		long companyId,
		OrderByComparator<KaleoTaskAssignmentInstance> orderByComparator) {

		List<KaleoTaskAssignmentInstance> list = findByCompanyId(
			companyId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last kaleo task assignment instance in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching kaleo task assignment instance
	 * @throws NoSuchTaskAssignmentInstanceException if a matching kaleo task assignment instance could not be found
	 */
	@Override
	public KaleoTaskAssignmentInstance findByCompanyId_Last(
			long companyId,
			OrderByComparator<KaleoTaskAssignmentInstance> orderByComparator)
		throws NoSuchTaskAssignmentInstanceException {

		KaleoTaskAssignmentInstance kaleoTaskAssignmentInstance =
			fetchByCompanyId_Last(companyId, orderByComparator);

		if (kaleoTaskAssignmentInstance != null) {
			return kaleoTaskAssignmentInstance;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchTaskAssignmentInstanceException(sb.toString());
	}

	/**
	 * Returns the last kaleo task assignment instance in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching kaleo task assignment instance, or <code>null</code> if a matching kaleo task assignment instance could not be found
	 */
	@Override
	public KaleoTaskAssignmentInstance fetchByCompanyId_Last(
		long companyId,
		OrderByComparator<KaleoTaskAssignmentInstance> orderByComparator) {

		int count = countByCompanyId(companyId);

		if (count == 0) {
			return null;
		}

		List<KaleoTaskAssignmentInstance> list = findByCompanyId(
			companyId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the kaleo task assignment instances before and after the current kaleo task assignment instance in the ordered set where companyId = &#63;.
	 *
	 * @param kaleoTaskAssignmentInstanceId the primary key of the current kaleo task assignment instance
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next kaleo task assignment instance
	 * @throws NoSuchTaskAssignmentInstanceException if a kaleo task assignment instance with the primary key could not be found
	 */
	@Override
	public KaleoTaskAssignmentInstance[] findByCompanyId_PrevAndNext(
			long kaleoTaskAssignmentInstanceId, long companyId,
			OrderByComparator<KaleoTaskAssignmentInstance> orderByComparator)
		throws NoSuchTaskAssignmentInstanceException {

		KaleoTaskAssignmentInstance kaleoTaskAssignmentInstance =
			findByPrimaryKey(kaleoTaskAssignmentInstanceId);

		Session session = null;

		try {
			session = openSession();

			KaleoTaskAssignmentInstance[] array =
				new KaleoTaskAssignmentInstanceImpl[3];

			array[0] = getByCompanyId_PrevAndNext(
				session, kaleoTaskAssignmentInstance, companyId,
				orderByComparator, true);

			array[1] = kaleoTaskAssignmentInstance;

			array[2] = getByCompanyId_PrevAndNext(
				session, kaleoTaskAssignmentInstance, companyId,
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

	protected KaleoTaskAssignmentInstance getByCompanyId_PrevAndNext(
		Session session,
		KaleoTaskAssignmentInstance kaleoTaskAssignmentInstance, long companyId,
		OrderByComparator<KaleoTaskAssignmentInstance> orderByComparator,
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

		sb.append(_SQL_SELECT_KALEOTASKASSIGNMENTINSTANCE_WHERE);

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
			sb.append(KaleoTaskAssignmentInstanceModelImpl.ORDER_BY_JPQL);
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
						kaleoTaskAssignmentInstance)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<KaleoTaskAssignmentInstance> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the kaleo task assignment instances where companyId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 */
	@Override
	public void removeByCompanyId(long companyId) {
		for (KaleoTaskAssignmentInstance kaleoTaskAssignmentInstance :
				findByCompanyId(
					companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(kaleoTaskAssignmentInstance);
		}
	}

	/**
	 * Returns the number of kaleo task assignment instances where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching kaleo task assignment instances
	 */
	@Override
	public int countByCompanyId(long companyId) {
		FinderPath finderPath = _finderPathCountByCompanyId;

		Object[] finderArgs = new Object[] {companyId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_KALEOTASKASSIGNMENTINSTANCE_WHERE);

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
		"kaleoTaskAssignmentInstance.companyId = ?";

	private FinderPath _finderPathWithPaginationFindByKaleoDefinitionVersionId;
	private FinderPath
		_finderPathWithoutPaginationFindByKaleoDefinitionVersionId;
	private FinderPath _finderPathCountByKaleoDefinitionVersionId;

	/**
	 * Returns all the kaleo task assignment instances where kaleoDefinitionVersionId = &#63;.
	 *
	 * @param kaleoDefinitionVersionId the kaleo definition version ID
	 * @return the matching kaleo task assignment instances
	 */
	@Override
	public List<KaleoTaskAssignmentInstance> findByKaleoDefinitionVersionId(
		long kaleoDefinitionVersionId) {

		return findByKaleoDefinitionVersionId(
			kaleoDefinitionVersionId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the kaleo task assignment instances where kaleoDefinitionVersionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoTaskAssignmentInstanceModelImpl</code>.
	 * </p>
	 *
	 * @param kaleoDefinitionVersionId the kaleo definition version ID
	 * @param start the lower bound of the range of kaleo task assignment instances
	 * @param end the upper bound of the range of kaleo task assignment instances (not inclusive)
	 * @return the range of matching kaleo task assignment instances
	 */
	@Override
	public List<KaleoTaskAssignmentInstance> findByKaleoDefinitionVersionId(
		long kaleoDefinitionVersionId, int start, int end) {

		return findByKaleoDefinitionVersionId(
			kaleoDefinitionVersionId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the kaleo task assignment instances where kaleoDefinitionVersionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoTaskAssignmentInstanceModelImpl</code>.
	 * </p>
	 *
	 * @param kaleoDefinitionVersionId the kaleo definition version ID
	 * @param start the lower bound of the range of kaleo task assignment instances
	 * @param end the upper bound of the range of kaleo task assignment instances (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching kaleo task assignment instances
	 */
	@Override
	public List<KaleoTaskAssignmentInstance> findByKaleoDefinitionVersionId(
		long kaleoDefinitionVersionId, int start, int end,
		OrderByComparator<KaleoTaskAssignmentInstance> orderByComparator) {

		return findByKaleoDefinitionVersionId(
			kaleoDefinitionVersionId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the kaleo task assignment instances where kaleoDefinitionVersionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoTaskAssignmentInstanceModelImpl</code>.
	 * </p>
	 *
	 * @param kaleoDefinitionVersionId the kaleo definition version ID
	 * @param start the lower bound of the range of kaleo task assignment instances
	 * @param end the upper bound of the range of kaleo task assignment instances (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching kaleo task assignment instances
	 */
	@Override
	public List<KaleoTaskAssignmentInstance> findByKaleoDefinitionVersionId(
		long kaleoDefinitionVersionId, int start, int end,
		OrderByComparator<KaleoTaskAssignmentInstance> orderByComparator,
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

		List<KaleoTaskAssignmentInstance> list = null;

		if (useFinderCache) {
			list = (List<KaleoTaskAssignmentInstance>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (KaleoTaskAssignmentInstance kaleoTaskAssignmentInstance :
						list) {

					if (kaleoDefinitionVersionId !=
							kaleoTaskAssignmentInstance.
								getKaleoDefinitionVersionId()) {

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

			sb.append(_SQL_SELECT_KALEOTASKASSIGNMENTINSTANCE_WHERE);

			sb.append(
				_FINDER_COLUMN_KALEODEFINITIONVERSIONID_KALEODEFINITIONVERSIONID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(KaleoTaskAssignmentInstanceModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(kaleoDefinitionVersionId);

				list = (List<KaleoTaskAssignmentInstance>)QueryUtil.list(
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
	 * Returns the first kaleo task assignment instance in the ordered set where kaleoDefinitionVersionId = &#63;.
	 *
	 * @param kaleoDefinitionVersionId the kaleo definition version ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching kaleo task assignment instance
	 * @throws NoSuchTaskAssignmentInstanceException if a matching kaleo task assignment instance could not be found
	 */
	@Override
	public KaleoTaskAssignmentInstance findByKaleoDefinitionVersionId_First(
			long kaleoDefinitionVersionId,
			OrderByComparator<KaleoTaskAssignmentInstance> orderByComparator)
		throws NoSuchTaskAssignmentInstanceException {

		KaleoTaskAssignmentInstance kaleoTaskAssignmentInstance =
			fetchByKaleoDefinitionVersionId_First(
				kaleoDefinitionVersionId, orderByComparator);

		if (kaleoTaskAssignmentInstance != null) {
			return kaleoTaskAssignmentInstance;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("kaleoDefinitionVersionId=");
		sb.append(kaleoDefinitionVersionId);

		sb.append("}");

		throw new NoSuchTaskAssignmentInstanceException(sb.toString());
	}

	/**
	 * Returns the first kaleo task assignment instance in the ordered set where kaleoDefinitionVersionId = &#63;.
	 *
	 * @param kaleoDefinitionVersionId the kaleo definition version ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching kaleo task assignment instance, or <code>null</code> if a matching kaleo task assignment instance could not be found
	 */
	@Override
	public KaleoTaskAssignmentInstance fetchByKaleoDefinitionVersionId_First(
		long kaleoDefinitionVersionId,
		OrderByComparator<KaleoTaskAssignmentInstance> orderByComparator) {

		List<KaleoTaskAssignmentInstance> list = findByKaleoDefinitionVersionId(
			kaleoDefinitionVersionId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last kaleo task assignment instance in the ordered set where kaleoDefinitionVersionId = &#63;.
	 *
	 * @param kaleoDefinitionVersionId the kaleo definition version ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching kaleo task assignment instance
	 * @throws NoSuchTaskAssignmentInstanceException if a matching kaleo task assignment instance could not be found
	 */
	@Override
	public KaleoTaskAssignmentInstance findByKaleoDefinitionVersionId_Last(
			long kaleoDefinitionVersionId,
			OrderByComparator<KaleoTaskAssignmentInstance> orderByComparator)
		throws NoSuchTaskAssignmentInstanceException {

		KaleoTaskAssignmentInstance kaleoTaskAssignmentInstance =
			fetchByKaleoDefinitionVersionId_Last(
				kaleoDefinitionVersionId, orderByComparator);

		if (kaleoTaskAssignmentInstance != null) {
			return kaleoTaskAssignmentInstance;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("kaleoDefinitionVersionId=");
		sb.append(kaleoDefinitionVersionId);

		sb.append("}");

		throw new NoSuchTaskAssignmentInstanceException(sb.toString());
	}

	/**
	 * Returns the last kaleo task assignment instance in the ordered set where kaleoDefinitionVersionId = &#63;.
	 *
	 * @param kaleoDefinitionVersionId the kaleo definition version ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching kaleo task assignment instance, or <code>null</code> if a matching kaleo task assignment instance could not be found
	 */
	@Override
	public KaleoTaskAssignmentInstance fetchByKaleoDefinitionVersionId_Last(
		long kaleoDefinitionVersionId,
		OrderByComparator<KaleoTaskAssignmentInstance> orderByComparator) {

		int count = countByKaleoDefinitionVersionId(kaleoDefinitionVersionId);

		if (count == 0) {
			return null;
		}

		List<KaleoTaskAssignmentInstance> list = findByKaleoDefinitionVersionId(
			kaleoDefinitionVersionId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the kaleo task assignment instances before and after the current kaleo task assignment instance in the ordered set where kaleoDefinitionVersionId = &#63;.
	 *
	 * @param kaleoTaskAssignmentInstanceId the primary key of the current kaleo task assignment instance
	 * @param kaleoDefinitionVersionId the kaleo definition version ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next kaleo task assignment instance
	 * @throws NoSuchTaskAssignmentInstanceException if a kaleo task assignment instance with the primary key could not be found
	 */
	@Override
	public KaleoTaskAssignmentInstance[]
			findByKaleoDefinitionVersionId_PrevAndNext(
				long kaleoTaskAssignmentInstanceId,
				long kaleoDefinitionVersionId,
				OrderByComparator<KaleoTaskAssignmentInstance>
					orderByComparator)
		throws NoSuchTaskAssignmentInstanceException {

		KaleoTaskAssignmentInstance kaleoTaskAssignmentInstance =
			findByPrimaryKey(kaleoTaskAssignmentInstanceId);

		Session session = null;

		try {
			session = openSession();

			KaleoTaskAssignmentInstance[] array =
				new KaleoTaskAssignmentInstanceImpl[3];

			array[0] = getByKaleoDefinitionVersionId_PrevAndNext(
				session, kaleoTaskAssignmentInstance, kaleoDefinitionVersionId,
				orderByComparator, true);

			array[1] = kaleoTaskAssignmentInstance;

			array[2] = getByKaleoDefinitionVersionId_PrevAndNext(
				session, kaleoTaskAssignmentInstance, kaleoDefinitionVersionId,
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

	protected KaleoTaskAssignmentInstance
		getByKaleoDefinitionVersionId_PrevAndNext(
			Session session,
			KaleoTaskAssignmentInstance kaleoTaskAssignmentInstance,
			long kaleoDefinitionVersionId,
			OrderByComparator<KaleoTaskAssignmentInstance> orderByComparator,
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

		sb.append(_SQL_SELECT_KALEOTASKASSIGNMENTINSTANCE_WHERE);

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
			sb.append(KaleoTaskAssignmentInstanceModelImpl.ORDER_BY_JPQL);
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
						kaleoTaskAssignmentInstance)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<KaleoTaskAssignmentInstance> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the kaleo task assignment instances where kaleoDefinitionVersionId = &#63; from the database.
	 *
	 * @param kaleoDefinitionVersionId the kaleo definition version ID
	 */
	@Override
	public void removeByKaleoDefinitionVersionId(
		long kaleoDefinitionVersionId) {

		for (KaleoTaskAssignmentInstance kaleoTaskAssignmentInstance :
				findByKaleoDefinitionVersionId(
					kaleoDefinitionVersionId, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			remove(kaleoTaskAssignmentInstance);
		}
	}

	/**
	 * Returns the number of kaleo task assignment instances where kaleoDefinitionVersionId = &#63;.
	 *
	 * @param kaleoDefinitionVersionId the kaleo definition version ID
	 * @return the number of matching kaleo task assignment instances
	 */
	@Override
	public int countByKaleoDefinitionVersionId(long kaleoDefinitionVersionId) {
		FinderPath finderPath = _finderPathCountByKaleoDefinitionVersionId;

		Object[] finderArgs = new Object[] {kaleoDefinitionVersionId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_KALEOTASKASSIGNMENTINSTANCE_WHERE);

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
			"kaleoTaskAssignmentInstance.kaleoDefinitionVersionId = ?";

	private FinderPath _finderPathWithPaginationFindByKaleoInstanceId;
	private FinderPath _finderPathWithoutPaginationFindByKaleoInstanceId;
	private FinderPath _finderPathCountByKaleoInstanceId;

	/**
	 * Returns all the kaleo task assignment instances where kaleoInstanceId = &#63;.
	 *
	 * @param kaleoInstanceId the kaleo instance ID
	 * @return the matching kaleo task assignment instances
	 */
	@Override
	public List<KaleoTaskAssignmentInstance> findByKaleoInstanceId(
		long kaleoInstanceId) {

		return findByKaleoInstanceId(
			kaleoInstanceId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the kaleo task assignment instances where kaleoInstanceId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoTaskAssignmentInstanceModelImpl</code>.
	 * </p>
	 *
	 * @param kaleoInstanceId the kaleo instance ID
	 * @param start the lower bound of the range of kaleo task assignment instances
	 * @param end the upper bound of the range of kaleo task assignment instances (not inclusive)
	 * @return the range of matching kaleo task assignment instances
	 */
	@Override
	public List<KaleoTaskAssignmentInstance> findByKaleoInstanceId(
		long kaleoInstanceId, int start, int end) {

		return findByKaleoInstanceId(kaleoInstanceId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the kaleo task assignment instances where kaleoInstanceId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoTaskAssignmentInstanceModelImpl</code>.
	 * </p>
	 *
	 * @param kaleoInstanceId the kaleo instance ID
	 * @param start the lower bound of the range of kaleo task assignment instances
	 * @param end the upper bound of the range of kaleo task assignment instances (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching kaleo task assignment instances
	 */
	@Override
	public List<KaleoTaskAssignmentInstance> findByKaleoInstanceId(
		long kaleoInstanceId, int start, int end,
		OrderByComparator<KaleoTaskAssignmentInstance> orderByComparator) {

		return findByKaleoInstanceId(
			kaleoInstanceId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the kaleo task assignment instances where kaleoInstanceId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoTaskAssignmentInstanceModelImpl</code>.
	 * </p>
	 *
	 * @param kaleoInstanceId the kaleo instance ID
	 * @param start the lower bound of the range of kaleo task assignment instances
	 * @param end the upper bound of the range of kaleo task assignment instances (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching kaleo task assignment instances
	 */
	@Override
	public List<KaleoTaskAssignmentInstance> findByKaleoInstanceId(
		long kaleoInstanceId, int start, int end,
		OrderByComparator<KaleoTaskAssignmentInstance> orderByComparator,
		boolean useFinderCache) {

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

		List<KaleoTaskAssignmentInstance> list = null;

		if (useFinderCache) {
			list = (List<KaleoTaskAssignmentInstance>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (KaleoTaskAssignmentInstance kaleoTaskAssignmentInstance :
						list) {

					if (kaleoInstanceId !=
							kaleoTaskAssignmentInstance.getKaleoInstanceId()) {

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

			sb.append(_SQL_SELECT_KALEOTASKASSIGNMENTINSTANCE_WHERE);

			sb.append(_FINDER_COLUMN_KALEOINSTANCEID_KALEOINSTANCEID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(KaleoTaskAssignmentInstanceModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(kaleoInstanceId);

				list = (List<KaleoTaskAssignmentInstance>)QueryUtil.list(
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
	 * Returns the first kaleo task assignment instance in the ordered set where kaleoInstanceId = &#63;.
	 *
	 * @param kaleoInstanceId the kaleo instance ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching kaleo task assignment instance
	 * @throws NoSuchTaskAssignmentInstanceException if a matching kaleo task assignment instance could not be found
	 */
	@Override
	public KaleoTaskAssignmentInstance findByKaleoInstanceId_First(
			long kaleoInstanceId,
			OrderByComparator<KaleoTaskAssignmentInstance> orderByComparator)
		throws NoSuchTaskAssignmentInstanceException {

		KaleoTaskAssignmentInstance kaleoTaskAssignmentInstance =
			fetchByKaleoInstanceId_First(kaleoInstanceId, orderByComparator);

		if (kaleoTaskAssignmentInstance != null) {
			return kaleoTaskAssignmentInstance;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("kaleoInstanceId=");
		sb.append(kaleoInstanceId);

		sb.append("}");

		throw new NoSuchTaskAssignmentInstanceException(sb.toString());
	}

	/**
	 * Returns the first kaleo task assignment instance in the ordered set where kaleoInstanceId = &#63;.
	 *
	 * @param kaleoInstanceId the kaleo instance ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching kaleo task assignment instance, or <code>null</code> if a matching kaleo task assignment instance could not be found
	 */
	@Override
	public KaleoTaskAssignmentInstance fetchByKaleoInstanceId_First(
		long kaleoInstanceId,
		OrderByComparator<KaleoTaskAssignmentInstance> orderByComparator) {

		List<KaleoTaskAssignmentInstance> list = findByKaleoInstanceId(
			kaleoInstanceId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last kaleo task assignment instance in the ordered set where kaleoInstanceId = &#63;.
	 *
	 * @param kaleoInstanceId the kaleo instance ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching kaleo task assignment instance
	 * @throws NoSuchTaskAssignmentInstanceException if a matching kaleo task assignment instance could not be found
	 */
	@Override
	public KaleoTaskAssignmentInstance findByKaleoInstanceId_Last(
			long kaleoInstanceId,
			OrderByComparator<KaleoTaskAssignmentInstance> orderByComparator)
		throws NoSuchTaskAssignmentInstanceException {

		KaleoTaskAssignmentInstance kaleoTaskAssignmentInstance =
			fetchByKaleoInstanceId_Last(kaleoInstanceId, orderByComparator);

		if (kaleoTaskAssignmentInstance != null) {
			return kaleoTaskAssignmentInstance;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("kaleoInstanceId=");
		sb.append(kaleoInstanceId);

		sb.append("}");

		throw new NoSuchTaskAssignmentInstanceException(sb.toString());
	}

	/**
	 * Returns the last kaleo task assignment instance in the ordered set where kaleoInstanceId = &#63;.
	 *
	 * @param kaleoInstanceId the kaleo instance ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching kaleo task assignment instance, or <code>null</code> if a matching kaleo task assignment instance could not be found
	 */
	@Override
	public KaleoTaskAssignmentInstance fetchByKaleoInstanceId_Last(
		long kaleoInstanceId,
		OrderByComparator<KaleoTaskAssignmentInstance> orderByComparator) {

		int count = countByKaleoInstanceId(kaleoInstanceId);

		if (count == 0) {
			return null;
		}

		List<KaleoTaskAssignmentInstance> list = findByKaleoInstanceId(
			kaleoInstanceId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the kaleo task assignment instances before and after the current kaleo task assignment instance in the ordered set where kaleoInstanceId = &#63;.
	 *
	 * @param kaleoTaskAssignmentInstanceId the primary key of the current kaleo task assignment instance
	 * @param kaleoInstanceId the kaleo instance ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next kaleo task assignment instance
	 * @throws NoSuchTaskAssignmentInstanceException if a kaleo task assignment instance with the primary key could not be found
	 */
	@Override
	public KaleoTaskAssignmentInstance[] findByKaleoInstanceId_PrevAndNext(
			long kaleoTaskAssignmentInstanceId, long kaleoInstanceId,
			OrderByComparator<KaleoTaskAssignmentInstance> orderByComparator)
		throws NoSuchTaskAssignmentInstanceException {

		KaleoTaskAssignmentInstance kaleoTaskAssignmentInstance =
			findByPrimaryKey(kaleoTaskAssignmentInstanceId);

		Session session = null;

		try {
			session = openSession();

			KaleoTaskAssignmentInstance[] array =
				new KaleoTaskAssignmentInstanceImpl[3];

			array[0] = getByKaleoInstanceId_PrevAndNext(
				session, kaleoTaskAssignmentInstance, kaleoInstanceId,
				orderByComparator, true);

			array[1] = kaleoTaskAssignmentInstance;

			array[2] = getByKaleoInstanceId_PrevAndNext(
				session, kaleoTaskAssignmentInstance, kaleoInstanceId,
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

	protected KaleoTaskAssignmentInstance getByKaleoInstanceId_PrevAndNext(
		Session session,
		KaleoTaskAssignmentInstance kaleoTaskAssignmentInstance,
		long kaleoInstanceId,
		OrderByComparator<KaleoTaskAssignmentInstance> orderByComparator,
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

		sb.append(_SQL_SELECT_KALEOTASKASSIGNMENTINSTANCE_WHERE);

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
			sb.append(KaleoTaskAssignmentInstanceModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(kaleoInstanceId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						kaleoTaskAssignmentInstance)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<KaleoTaskAssignmentInstance> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the kaleo task assignment instances where kaleoInstanceId = &#63; from the database.
	 *
	 * @param kaleoInstanceId the kaleo instance ID
	 */
	@Override
	public void removeByKaleoInstanceId(long kaleoInstanceId) {
		for (KaleoTaskAssignmentInstance kaleoTaskAssignmentInstance :
				findByKaleoInstanceId(
					kaleoInstanceId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(kaleoTaskAssignmentInstance);
		}
	}

	/**
	 * Returns the number of kaleo task assignment instances where kaleoInstanceId = &#63;.
	 *
	 * @param kaleoInstanceId the kaleo instance ID
	 * @return the number of matching kaleo task assignment instances
	 */
	@Override
	public int countByKaleoInstanceId(long kaleoInstanceId) {
		FinderPath finderPath = _finderPathCountByKaleoInstanceId;

		Object[] finderArgs = new Object[] {kaleoInstanceId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_KALEOTASKASSIGNMENTINSTANCE_WHERE);

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
			"kaleoTaskAssignmentInstance.kaleoInstanceId = ?";

	private FinderPath _finderPathWithPaginationFindByKaleoTaskInstanceTokenId;
	private FinderPath
		_finderPathWithoutPaginationFindByKaleoTaskInstanceTokenId;
	private FinderPath _finderPathCountByKaleoTaskInstanceTokenId;

	/**
	 * Returns all the kaleo task assignment instances where kaleoTaskInstanceTokenId = &#63;.
	 *
	 * @param kaleoTaskInstanceTokenId the kaleo task instance token ID
	 * @return the matching kaleo task assignment instances
	 */
	@Override
	public List<KaleoTaskAssignmentInstance> findByKaleoTaskInstanceTokenId(
		long kaleoTaskInstanceTokenId) {

		return findByKaleoTaskInstanceTokenId(
			kaleoTaskInstanceTokenId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the kaleo task assignment instances where kaleoTaskInstanceTokenId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoTaskAssignmentInstanceModelImpl</code>.
	 * </p>
	 *
	 * @param kaleoTaskInstanceTokenId the kaleo task instance token ID
	 * @param start the lower bound of the range of kaleo task assignment instances
	 * @param end the upper bound of the range of kaleo task assignment instances (not inclusive)
	 * @return the range of matching kaleo task assignment instances
	 */
	@Override
	public List<KaleoTaskAssignmentInstance> findByKaleoTaskInstanceTokenId(
		long kaleoTaskInstanceTokenId, int start, int end) {

		return findByKaleoTaskInstanceTokenId(
			kaleoTaskInstanceTokenId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the kaleo task assignment instances where kaleoTaskInstanceTokenId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoTaskAssignmentInstanceModelImpl</code>.
	 * </p>
	 *
	 * @param kaleoTaskInstanceTokenId the kaleo task instance token ID
	 * @param start the lower bound of the range of kaleo task assignment instances
	 * @param end the upper bound of the range of kaleo task assignment instances (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching kaleo task assignment instances
	 */
	@Override
	public List<KaleoTaskAssignmentInstance> findByKaleoTaskInstanceTokenId(
		long kaleoTaskInstanceTokenId, int start, int end,
		OrderByComparator<KaleoTaskAssignmentInstance> orderByComparator) {

		return findByKaleoTaskInstanceTokenId(
			kaleoTaskInstanceTokenId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the kaleo task assignment instances where kaleoTaskInstanceTokenId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoTaskAssignmentInstanceModelImpl</code>.
	 * </p>
	 *
	 * @param kaleoTaskInstanceTokenId the kaleo task instance token ID
	 * @param start the lower bound of the range of kaleo task assignment instances
	 * @param end the upper bound of the range of kaleo task assignment instances (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching kaleo task assignment instances
	 */
	@Override
	public List<KaleoTaskAssignmentInstance> findByKaleoTaskInstanceTokenId(
		long kaleoTaskInstanceTokenId, int start, int end,
		OrderByComparator<KaleoTaskAssignmentInstance> orderByComparator,
		boolean useFinderCache) {

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

		List<KaleoTaskAssignmentInstance> list = null;

		if (useFinderCache) {
			list = (List<KaleoTaskAssignmentInstance>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (KaleoTaskAssignmentInstance kaleoTaskAssignmentInstance :
						list) {

					if (kaleoTaskInstanceTokenId !=
							kaleoTaskAssignmentInstance.
								getKaleoTaskInstanceTokenId()) {

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

			sb.append(_SQL_SELECT_KALEOTASKASSIGNMENTINSTANCE_WHERE);

			sb.append(
				_FINDER_COLUMN_KALEOTASKINSTANCETOKENID_KALEOTASKINSTANCETOKENID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(KaleoTaskAssignmentInstanceModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(kaleoTaskInstanceTokenId);

				list = (List<KaleoTaskAssignmentInstance>)QueryUtil.list(
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
	 * Returns the first kaleo task assignment instance in the ordered set where kaleoTaskInstanceTokenId = &#63;.
	 *
	 * @param kaleoTaskInstanceTokenId the kaleo task instance token ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching kaleo task assignment instance
	 * @throws NoSuchTaskAssignmentInstanceException if a matching kaleo task assignment instance could not be found
	 */
	@Override
	public KaleoTaskAssignmentInstance findByKaleoTaskInstanceTokenId_First(
			long kaleoTaskInstanceTokenId,
			OrderByComparator<KaleoTaskAssignmentInstance> orderByComparator)
		throws NoSuchTaskAssignmentInstanceException {

		KaleoTaskAssignmentInstance kaleoTaskAssignmentInstance =
			fetchByKaleoTaskInstanceTokenId_First(
				kaleoTaskInstanceTokenId, orderByComparator);

		if (kaleoTaskAssignmentInstance != null) {
			return kaleoTaskAssignmentInstance;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("kaleoTaskInstanceTokenId=");
		sb.append(kaleoTaskInstanceTokenId);

		sb.append("}");

		throw new NoSuchTaskAssignmentInstanceException(sb.toString());
	}

	/**
	 * Returns the first kaleo task assignment instance in the ordered set where kaleoTaskInstanceTokenId = &#63;.
	 *
	 * @param kaleoTaskInstanceTokenId the kaleo task instance token ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching kaleo task assignment instance, or <code>null</code> if a matching kaleo task assignment instance could not be found
	 */
	@Override
	public KaleoTaskAssignmentInstance fetchByKaleoTaskInstanceTokenId_First(
		long kaleoTaskInstanceTokenId,
		OrderByComparator<KaleoTaskAssignmentInstance> orderByComparator) {

		List<KaleoTaskAssignmentInstance> list = findByKaleoTaskInstanceTokenId(
			kaleoTaskInstanceTokenId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last kaleo task assignment instance in the ordered set where kaleoTaskInstanceTokenId = &#63;.
	 *
	 * @param kaleoTaskInstanceTokenId the kaleo task instance token ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching kaleo task assignment instance
	 * @throws NoSuchTaskAssignmentInstanceException if a matching kaleo task assignment instance could not be found
	 */
	@Override
	public KaleoTaskAssignmentInstance findByKaleoTaskInstanceTokenId_Last(
			long kaleoTaskInstanceTokenId,
			OrderByComparator<KaleoTaskAssignmentInstance> orderByComparator)
		throws NoSuchTaskAssignmentInstanceException {

		KaleoTaskAssignmentInstance kaleoTaskAssignmentInstance =
			fetchByKaleoTaskInstanceTokenId_Last(
				kaleoTaskInstanceTokenId, orderByComparator);

		if (kaleoTaskAssignmentInstance != null) {
			return kaleoTaskAssignmentInstance;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("kaleoTaskInstanceTokenId=");
		sb.append(kaleoTaskInstanceTokenId);

		sb.append("}");

		throw new NoSuchTaskAssignmentInstanceException(sb.toString());
	}

	/**
	 * Returns the last kaleo task assignment instance in the ordered set where kaleoTaskInstanceTokenId = &#63;.
	 *
	 * @param kaleoTaskInstanceTokenId the kaleo task instance token ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching kaleo task assignment instance, or <code>null</code> if a matching kaleo task assignment instance could not be found
	 */
	@Override
	public KaleoTaskAssignmentInstance fetchByKaleoTaskInstanceTokenId_Last(
		long kaleoTaskInstanceTokenId,
		OrderByComparator<KaleoTaskAssignmentInstance> orderByComparator) {

		int count = countByKaleoTaskInstanceTokenId(kaleoTaskInstanceTokenId);

		if (count == 0) {
			return null;
		}

		List<KaleoTaskAssignmentInstance> list = findByKaleoTaskInstanceTokenId(
			kaleoTaskInstanceTokenId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the kaleo task assignment instances before and after the current kaleo task assignment instance in the ordered set where kaleoTaskInstanceTokenId = &#63;.
	 *
	 * @param kaleoTaskAssignmentInstanceId the primary key of the current kaleo task assignment instance
	 * @param kaleoTaskInstanceTokenId the kaleo task instance token ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next kaleo task assignment instance
	 * @throws NoSuchTaskAssignmentInstanceException if a kaleo task assignment instance with the primary key could not be found
	 */
	@Override
	public KaleoTaskAssignmentInstance[]
			findByKaleoTaskInstanceTokenId_PrevAndNext(
				long kaleoTaskAssignmentInstanceId,
				long kaleoTaskInstanceTokenId,
				OrderByComparator<KaleoTaskAssignmentInstance>
					orderByComparator)
		throws NoSuchTaskAssignmentInstanceException {

		KaleoTaskAssignmentInstance kaleoTaskAssignmentInstance =
			findByPrimaryKey(kaleoTaskAssignmentInstanceId);

		Session session = null;

		try {
			session = openSession();

			KaleoTaskAssignmentInstance[] array =
				new KaleoTaskAssignmentInstanceImpl[3];

			array[0] = getByKaleoTaskInstanceTokenId_PrevAndNext(
				session, kaleoTaskAssignmentInstance, kaleoTaskInstanceTokenId,
				orderByComparator, true);

			array[1] = kaleoTaskAssignmentInstance;

			array[2] = getByKaleoTaskInstanceTokenId_PrevAndNext(
				session, kaleoTaskAssignmentInstance, kaleoTaskInstanceTokenId,
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

	protected KaleoTaskAssignmentInstance
		getByKaleoTaskInstanceTokenId_PrevAndNext(
			Session session,
			KaleoTaskAssignmentInstance kaleoTaskAssignmentInstance,
			long kaleoTaskInstanceTokenId,
			OrderByComparator<KaleoTaskAssignmentInstance> orderByComparator,
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

		sb.append(_SQL_SELECT_KALEOTASKASSIGNMENTINSTANCE_WHERE);

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
			sb.append(KaleoTaskAssignmentInstanceModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(kaleoTaskInstanceTokenId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						kaleoTaskAssignmentInstance)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<KaleoTaskAssignmentInstance> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the kaleo task assignment instances where kaleoTaskInstanceTokenId = &#63; from the database.
	 *
	 * @param kaleoTaskInstanceTokenId the kaleo task instance token ID
	 */
	@Override
	public void removeByKaleoTaskInstanceTokenId(
		long kaleoTaskInstanceTokenId) {

		for (KaleoTaskAssignmentInstance kaleoTaskAssignmentInstance :
				findByKaleoTaskInstanceTokenId(
					kaleoTaskInstanceTokenId, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			remove(kaleoTaskAssignmentInstance);
		}
	}

	/**
	 * Returns the number of kaleo task assignment instances where kaleoTaskInstanceTokenId = &#63;.
	 *
	 * @param kaleoTaskInstanceTokenId the kaleo task instance token ID
	 * @return the number of matching kaleo task assignment instances
	 */
	@Override
	public int countByKaleoTaskInstanceTokenId(long kaleoTaskInstanceTokenId) {
		FinderPath finderPath = _finderPathCountByKaleoTaskInstanceTokenId;

		Object[] finderArgs = new Object[] {kaleoTaskInstanceTokenId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_KALEOTASKASSIGNMENTINSTANCE_WHERE);

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
			"kaleoTaskAssignmentInstance.kaleoTaskInstanceTokenId = ?";

	private FinderPath _finderPathWithPaginationFindByAssigneeClassName;
	private FinderPath _finderPathWithoutPaginationFindByAssigneeClassName;
	private FinderPath _finderPathCountByAssigneeClassName;

	/**
	 * Returns all the kaleo task assignment instances where assigneeClassName = &#63;.
	 *
	 * @param assigneeClassName the assignee class name
	 * @return the matching kaleo task assignment instances
	 */
	@Override
	public List<KaleoTaskAssignmentInstance> findByAssigneeClassName(
		String assigneeClassName) {

		return findByAssigneeClassName(
			assigneeClassName, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the kaleo task assignment instances where assigneeClassName = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoTaskAssignmentInstanceModelImpl</code>.
	 * </p>
	 *
	 * @param assigneeClassName the assignee class name
	 * @param start the lower bound of the range of kaleo task assignment instances
	 * @param end the upper bound of the range of kaleo task assignment instances (not inclusive)
	 * @return the range of matching kaleo task assignment instances
	 */
	@Override
	public List<KaleoTaskAssignmentInstance> findByAssigneeClassName(
		String assigneeClassName, int start, int end) {

		return findByAssigneeClassName(assigneeClassName, start, end, null);
	}

	/**
	 * Returns an ordered range of all the kaleo task assignment instances where assigneeClassName = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoTaskAssignmentInstanceModelImpl</code>.
	 * </p>
	 *
	 * @param assigneeClassName the assignee class name
	 * @param start the lower bound of the range of kaleo task assignment instances
	 * @param end the upper bound of the range of kaleo task assignment instances (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching kaleo task assignment instances
	 */
	@Override
	public List<KaleoTaskAssignmentInstance> findByAssigneeClassName(
		String assigneeClassName, int start, int end,
		OrderByComparator<KaleoTaskAssignmentInstance> orderByComparator) {

		return findByAssigneeClassName(
			assigneeClassName, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the kaleo task assignment instances where assigneeClassName = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoTaskAssignmentInstanceModelImpl</code>.
	 * </p>
	 *
	 * @param assigneeClassName the assignee class name
	 * @param start the lower bound of the range of kaleo task assignment instances
	 * @param end the upper bound of the range of kaleo task assignment instances (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching kaleo task assignment instances
	 */
	@Override
	public List<KaleoTaskAssignmentInstance> findByAssigneeClassName(
		String assigneeClassName, int start, int end,
		OrderByComparator<KaleoTaskAssignmentInstance> orderByComparator,
		boolean useFinderCache) {

		assigneeClassName = Objects.toString(assigneeClassName, "");

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath =
					_finderPathWithoutPaginationFindByAssigneeClassName;
				finderArgs = new Object[] {assigneeClassName};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByAssigneeClassName;
			finderArgs = new Object[] {
				assigneeClassName, start, end, orderByComparator
			};
		}

		List<KaleoTaskAssignmentInstance> list = null;

		if (useFinderCache) {
			list = (List<KaleoTaskAssignmentInstance>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (KaleoTaskAssignmentInstance kaleoTaskAssignmentInstance :
						list) {

					if (!assigneeClassName.equals(
							kaleoTaskAssignmentInstance.
								getAssigneeClassName())) {

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

			sb.append(_SQL_SELECT_KALEOTASKASSIGNMENTINSTANCE_WHERE);

			boolean bindAssigneeClassName = false;

			if (assigneeClassName.isEmpty()) {
				sb.append(_FINDER_COLUMN_ASSIGNEECLASSNAME_ASSIGNEECLASSNAME_3);
			}
			else {
				bindAssigneeClassName = true;

				sb.append(_FINDER_COLUMN_ASSIGNEECLASSNAME_ASSIGNEECLASSNAME_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(KaleoTaskAssignmentInstanceModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindAssigneeClassName) {
					queryPos.add(assigneeClassName);
				}

				list = (List<KaleoTaskAssignmentInstance>)QueryUtil.list(
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
	 * Returns the first kaleo task assignment instance in the ordered set where assigneeClassName = &#63;.
	 *
	 * @param assigneeClassName the assignee class name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching kaleo task assignment instance
	 * @throws NoSuchTaskAssignmentInstanceException if a matching kaleo task assignment instance could not be found
	 */
	@Override
	public KaleoTaskAssignmentInstance findByAssigneeClassName_First(
			String assigneeClassName,
			OrderByComparator<KaleoTaskAssignmentInstance> orderByComparator)
		throws NoSuchTaskAssignmentInstanceException {

		KaleoTaskAssignmentInstance kaleoTaskAssignmentInstance =
			fetchByAssigneeClassName_First(
				assigneeClassName, orderByComparator);

		if (kaleoTaskAssignmentInstance != null) {
			return kaleoTaskAssignmentInstance;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("assigneeClassName=");
		sb.append(assigneeClassName);

		sb.append("}");

		throw new NoSuchTaskAssignmentInstanceException(sb.toString());
	}

	/**
	 * Returns the first kaleo task assignment instance in the ordered set where assigneeClassName = &#63;.
	 *
	 * @param assigneeClassName the assignee class name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching kaleo task assignment instance, or <code>null</code> if a matching kaleo task assignment instance could not be found
	 */
	@Override
	public KaleoTaskAssignmentInstance fetchByAssigneeClassName_First(
		String assigneeClassName,
		OrderByComparator<KaleoTaskAssignmentInstance> orderByComparator) {

		List<KaleoTaskAssignmentInstance> list = findByAssigneeClassName(
			assigneeClassName, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last kaleo task assignment instance in the ordered set where assigneeClassName = &#63;.
	 *
	 * @param assigneeClassName the assignee class name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching kaleo task assignment instance
	 * @throws NoSuchTaskAssignmentInstanceException if a matching kaleo task assignment instance could not be found
	 */
	@Override
	public KaleoTaskAssignmentInstance findByAssigneeClassName_Last(
			String assigneeClassName,
			OrderByComparator<KaleoTaskAssignmentInstance> orderByComparator)
		throws NoSuchTaskAssignmentInstanceException {

		KaleoTaskAssignmentInstance kaleoTaskAssignmentInstance =
			fetchByAssigneeClassName_Last(assigneeClassName, orderByComparator);

		if (kaleoTaskAssignmentInstance != null) {
			return kaleoTaskAssignmentInstance;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("assigneeClassName=");
		sb.append(assigneeClassName);

		sb.append("}");

		throw new NoSuchTaskAssignmentInstanceException(sb.toString());
	}

	/**
	 * Returns the last kaleo task assignment instance in the ordered set where assigneeClassName = &#63;.
	 *
	 * @param assigneeClassName the assignee class name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching kaleo task assignment instance, or <code>null</code> if a matching kaleo task assignment instance could not be found
	 */
	@Override
	public KaleoTaskAssignmentInstance fetchByAssigneeClassName_Last(
		String assigneeClassName,
		OrderByComparator<KaleoTaskAssignmentInstance> orderByComparator) {

		int count = countByAssigneeClassName(assigneeClassName);

		if (count == 0) {
			return null;
		}

		List<KaleoTaskAssignmentInstance> list = findByAssigneeClassName(
			assigneeClassName, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the kaleo task assignment instances before and after the current kaleo task assignment instance in the ordered set where assigneeClassName = &#63;.
	 *
	 * @param kaleoTaskAssignmentInstanceId the primary key of the current kaleo task assignment instance
	 * @param assigneeClassName the assignee class name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next kaleo task assignment instance
	 * @throws NoSuchTaskAssignmentInstanceException if a kaleo task assignment instance with the primary key could not be found
	 */
	@Override
	public KaleoTaskAssignmentInstance[] findByAssigneeClassName_PrevAndNext(
			long kaleoTaskAssignmentInstanceId, String assigneeClassName,
			OrderByComparator<KaleoTaskAssignmentInstance> orderByComparator)
		throws NoSuchTaskAssignmentInstanceException {

		assigneeClassName = Objects.toString(assigneeClassName, "");

		KaleoTaskAssignmentInstance kaleoTaskAssignmentInstance =
			findByPrimaryKey(kaleoTaskAssignmentInstanceId);

		Session session = null;

		try {
			session = openSession();

			KaleoTaskAssignmentInstance[] array =
				new KaleoTaskAssignmentInstanceImpl[3];

			array[0] = getByAssigneeClassName_PrevAndNext(
				session, kaleoTaskAssignmentInstance, assigneeClassName,
				orderByComparator, true);

			array[1] = kaleoTaskAssignmentInstance;

			array[2] = getByAssigneeClassName_PrevAndNext(
				session, kaleoTaskAssignmentInstance, assigneeClassName,
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

	protected KaleoTaskAssignmentInstance getByAssigneeClassName_PrevAndNext(
		Session session,
		KaleoTaskAssignmentInstance kaleoTaskAssignmentInstance,
		String assigneeClassName,
		OrderByComparator<KaleoTaskAssignmentInstance> orderByComparator,
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

		sb.append(_SQL_SELECT_KALEOTASKASSIGNMENTINSTANCE_WHERE);

		boolean bindAssigneeClassName = false;

		if (assigneeClassName.isEmpty()) {
			sb.append(_FINDER_COLUMN_ASSIGNEECLASSNAME_ASSIGNEECLASSNAME_3);
		}
		else {
			bindAssigneeClassName = true;

			sb.append(_FINDER_COLUMN_ASSIGNEECLASSNAME_ASSIGNEECLASSNAME_2);
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
			sb.append(KaleoTaskAssignmentInstanceModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		if (bindAssigneeClassName) {
			queryPos.add(assigneeClassName);
		}

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						kaleoTaskAssignmentInstance)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<KaleoTaskAssignmentInstance> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the kaleo task assignment instances where assigneeClassName = &#63; from the database.
	 *
	 * @param assigneeClassName the assignee class name
	 */
	@Override
	public void removeByAssigneeClassName(String assigneeClassName) {
		for (KaleoTaskAssignmentInstance kaleoTaskAssignmentInstance :
				findByAssigneeClassName(
					assigneeClassName, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(kaleoTaskAssignmentInstance);
		}
	}

	/**
	 * Returns the number of kaleo task assignment instances where assigneeClassName = &#63;.
	 *
	 * @param assigneeClassName the assignee class name
	 * @return the number of matching kaleo task assignment instances
	 */
	@Override
	public int countByAssigneeClassName(String assigneeClassName) {
		assigneeClassName = Objects.toString(assigneeClassName, "");

		FinderPath finderPath = _finderPathCountByAssigneeClassName;

		Object[] finderArgs = new Object[] {assigneeClassName};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_KALEOTASKASSIGNMENTINSTANCE_WHERE);

			boolean bindAssigneeClassName = false;

			if (assigneeClassName.isEmpty()) {
				sb.append(_FINDER_COLUMN_ASSIGNEECLASSNAME_ASSIGNEECLASSNAME_3);
			}
			else {
				bindAssigneeClassName = true;

				sb.append(_FINDER_COLUMN_ASSIGNEECLASSNAME_ASSIGNEECLASSNAME_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindAssigneeClassName) {
					queryPos.add(assigneeClassName);
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
		_FINDER_COLUMN_ASSIGNEECLASSNAME_ASSIGNEECLASSNAME_2 =
			"kaleoTaskAssignmentInstance.assigneeClassName = ?";

	private static final String
		_FINDER_COLUMN_ASSIGNEECLASSNAME_ASSIGNEECLASSNAME_3 =
			"(kaleoTaskAssignmentInstance.assigneeClassName IS NULL OR kaleoTaskAssignmentInstance.assigneeClassName = '')";

	private FinderPath _finderPathWithPaginationFindByG_ACPK;
	private FinderPath _finderPathWithoutPaginationFindByG_ACPK;
	private FinderPath _finderPathCountByG_ACPK;

	/**
	 * Returns all the kaleo task assignment instances where groupId = &#63; and assigneeClassPK = &#63;.
	 *
	 * @param groupId the group ID
	 * @param assigneeClassPK the assignee class pk
	 * @return the matching kaleo task assignment instances
	 */
	@Override
	public List<KaleoTaskAssignmentInstance> findByG_ACPK(
		long groupId, long assigneeClassPK) {

		return findByG_ACPK(
			groupId, assigneeClassPK, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the kaleo task assignment instances where groupId = &#63; and assigneeClassPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoTaskAssignmentInstanceModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param assigneeClassPK the assignee class pk
	 * @param start the lower bound of the range of kaleo task assignment instances
	 * @param end the upper bound of the range of kaleo task assignment instances (not inclusive)
	 * @return the range of matching kaleo task assignment instances
	 */
	@Override
	public List<KaleoTaskAssignmentInstance> findByG_ACPK(
		long groupId, long assigneeClassPK, int start, int end) {

		return findByG_ACPK(groupId, assigneeClassPK, start, end, null);
	}

	/**
	 * Returns an ordered range of all the kaleo task assignment instances where groupId = &#63; and assigneeClassPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoTaskAssignmentInstanceModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param assigneeClassPK the assignee class pk
	 * @param start the lower bound of the range of kaleo task assignment instances
	 * @param end the upper bound of the range of kaleo task assignment instances (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching kaleo task assignment instances
	 */
	@Override
	public List<KaleoTaskAssignmentInstance> findByG_ACPK(
		long groupId, long assigneeClassPK, int start, int end,
		OrderByComparator<KaleoTaskAssignmentInstance> orderByComparator) {

		return findByG_ACPK(
			groupId, assigneeClassPK, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the kaleo task assignment instances where groupId = &#63; and assigneeClassPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoTaskAssignmentInstanceModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param assigneeClassPK the assignee class pk
	 * @param start the lower bound of the range of kaleo task assignment instances
	 * @param end the upper bound of the range of kaleo task assignment instances (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching kaleo task assignment instances
	 */
	@Override
	public List<KaleoTaskAssignmentInstance> findByG_ACPK(
		long groupId, long assigneeClassPK, int start, int end,
		OrderByComparator<KaleoTaskAssignmentInstance> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByG_ACPK;
				finderArgs = new Object[] {groupId, assigneeClassPK};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByG_ACPK;
			finderArgs = new Object[] {
				groupId, assigneeClassPK, start, end, orderByComparator
			};
		}

		List<KaleoTaskAssignmentInstance> list = null;

		if (useFinderCache) {
			list = (List<KaleoTaskAssignmentInstance>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (KaleoTaskAssignmentInstance kaleoTaskAssignmentInstance :
						list) {

					if ((groupId != kaleoTaskAssignmentInstance.getGroupId()) ||
						(assigneeClassPK !=
							kaleoTaskAssignmentInstance.getAssigneeClassPK())) {

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

			sb.append(_SQL_SELECT_KALEOTASKASSIGNMENTINSTANCE_WHERE);

			sb.append(_FINDER_COLUMN_G_ACPK_GROUPID_2);

			sb.append(_FINDER_COLUMN_G_ACPK_ASSIGNEECLASSPK_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(KaleoTaskAssignmentInstanceModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				queryPos.add(assigneeClassPK);

				list = (List<KaleoTaskAssignmentInstance>)QueryUtil.list(
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
	 * Returns the first kaleo task assignment instance in the ordered set where groupId = &#63; and assigneeClassPK = &#63;.
	 *
	 * @param groupId the group ID
	 * @param assigneeClassPK the assignee class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching kaleo task assignment instance
	 * @throws NoSuchTaskAssignmentInstanceException if a matching kaleo task assignment instance could not be found
	 */
	@Override
	public KaleoTaskAssignmentInstance findByG_ACPK_First(
			long groupId, long assigneeClassPK,
			OrderByComparator<KaleoTaskAssignmentInstance> orderByComparator)
		throws NoSuchTaskAssignmentInstanceException {

		KaleoTaskAssignmentInstance kaleoTaskAssignmentInstance =
			fetchByG_ACPK_First(groupId, assigneeClassPK, orderByComparator);

		if (kaleoTaskAssignmentInstance != null) {
			return kaleoTaskAssignmentInstance;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", assigneeClassPK=");
		sb.append(assigneeClassPK);

		sb.append("}");

		throw new NoSuchTaskAssignmentInstanceException(sb.toString());
	}

	/**
	 * Returns the first kaleo task assignment instance in the ordered set where groupId = &#63; and assigneeClassPK = &#63;.
	 *
	 * @param groupId the group ID
	 * @param assigneeClassPK the assignee class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching kaleo task assignment instance, or <code>null</code> if a matching kaleo task assignment instance could not be found
	 */
	@Override
	public KaleoTaskAssignmentInstance fetchByG_ACPK_First(
		long groupId, long assigneeClassPK,
		OrderByComparator<KaleoTaskAssignmentInstance> orderByComparator) {

		List<KaleoTaskAssignmentInstance> list = findByG_ACPK(
			groupId, assigneeClassPK, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last kaleo task assignment instance in the ordered set where groupId = &#63; and assigneeClassPK = &#63;.
	 *
	 * @param groupId the group ID
	 * @param assigneeClassPK the assignee class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching kaleo task assignment instance
	 * @throws NoSuchTaskAssignmentInstanceException if a matching kaleo task assignment instance could not be found
	 */
	@Override
	public KaleoTaskAssignmentInstance findByG_ACPK_Last(
			long groupId, long assigneeClassPK,
			OrderByComparator<KaleoTaskAssignmentInstance> orderByComparator)
		throws NoSuchTaskAssignmentInstanceException {

		KaleoTaskAssignmentInstance kaleoTaskAssignmentInstance =
			fetchByG_ACPK_Last(groupId, assigneeClassPK, orderByComparator);

		if (kaleoTaskAssignmentInstance != null) {
			return kaleoTaskAssignmentInstance;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", assigneeClassPK=");
		sb.append(assigneeClassPK);

		sb.append("}");

		throw new NoSuchTaskAssignmentInstanceException(sb.toString());
	}

	/**
	 * Returns the last kaleo task assignment instance in the ordered set where groupId = &#63; and assigneeClassPK = &#63;.
	 *
	 * @param groupId the group ID
	 * @param assigneeClassPK the assignee class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching kaleo task assignment instance, or <code>null</code> if a matching kaleo task assignment instance could not be found
	 */
	@Override
	public KaleoTaskAssignmentInstance fetchByG_ACPK_Last(
		long groupId, long assigneeClassPK,
		OrderByComparator<KaleoTaskAssignmentInstance> orderByComparator) {

		int count = countByG_ACPK(groupId, assigneeClassPK);

		if (count == 0) {
			return null;
		}

		List<KaleoTaskAssignmentInstance> list = findByG_ACPK(
			groupId, assigneeClassPK, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the kaleo task assignment instances before and after the current kaleo task assignment instance in the ordered set where groupId = &#63; and assigneeClassPK = &#63;.
	 *
	 * @param kaleoTaskAssignmentInstanceId the primary key of the current kaleo task assignment instance
	 * @param groupId the group ID
	 * @param assigneeClassPK the assignee class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next kaleo task assignment instance
	 * @throws NoSuchTaskAssignmentInstanceException if a kaleo task assignment instance with the primary key could not be found
	 */
	@Override
	public KaleoTaskAssignmentInstance[] findByG_ACPK_PrevAndNext(
			long kaleoTaskAssignmentInstanceId, long groupId,
			long assigneeClassPK,
			OrderByComparator<KaleoTaskAssignmentInstance> orderByComparator)
		throws NoSuchTaskAssignmentInstanceException {

		KaleoTaskAssignmentInstance kaleoTaskAssignmentInstance =
			findByPrimaryKey(kaleoTaskAssignmentInstanceId);

		Session session = null;

		try {
			session = openSession();

			KaleoTaskAssignmentInstance[] array =
				new KaleoTaskAssignmentInstanceImpl[3];

			array[0] = getByG_ACPK_PrevAndNext(
				session, kaleoTaskAssignmentInstance, groupId, assigneeClassPK,
				orderByComparator, true);

			array[1] = kaleoTaskAssignmentInstance;

			array[2] = getByG_ACPK_PrevAndNext(
				session, kaleoTaskAssignmentInstance, groupId, assigneeClassPK,
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

	protected KaleoTaskAssignmentInstance getByG_ACPK_PrevAndNext(
		Session session,
		KaleoTaskAssignmentInstance kaleoTaskAssignmentInstance, long groupId,
		long assigneeClassPK,
		OrderByComparator<KaleoTaskAssignmentInstance> orderByComparator,
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

		sb.append(_SQL_SELECT_KALEOTASKASSIGNMENTINSTANCE_WHERE);

		sb.append(_FINDER_COLUMN_G_ACPK_GROUPID_2);

		sb.append(_FINDER_COLUMN_G_ACPK_ASSIGNEECLASSPK_2);

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
			sb.append(KaleoTaskAssignmentInstanceModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(groupId);

		queryPos.add(assigneeClassPK);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						kaleoTaskAssignmentInstance)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<KaleoTaskAssignmentInstance> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the kaleo task assignment instances where groupId = &#63; and assigneeClassPK = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param assigneeClassPK the assignee class pk
	 */
	@Override
	public void removeByG_ACPK(long groupId, long assigneeClassPK) {
		for (KaleoTaskAssignmentInstance kaleoTaskAssignmentInstance :
				findByG_ACPK(
					groupId, assigneeClassPK, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			remove(kaleoTaskAssignmentInstance);
		}
	}

	/**
	 * Returns the number of kaleo task assignment instances where groupId = &#63; and assigneeClassPK = &#63;.
	 *
	 * @param groupId the group ID
	 * @param assigneeClassPK the assignee class pk
	 * @return the number of matching kaleo task assignment instances
	 */
	@Override
	public int countByG_ACPK(long groupId, long assigneeClassPK) {
		FinderPath finderPath = _finderPathCountByG_ACPK;

		Object[] finderArgs = new Object[] {groupId, assigneeClassPK};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_KALEOTASKASSIGNMENTINSTANCE_WHERE);

			sb.append(_FINDER_COLUMN_G_ACPK_GROUPID_2);

			sb.append(_FINDER_COLUMN_G_ACPK_ASSIGNEECLASSPK_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				queryPos.add(assigneeClassPK);

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

	private static final String _FINDER_COLUMN_G_ACPK_GROUPID_2 =
		"kaleoTaskAssignmentInstance.groupId = ? AND ";

	private static final String _FINDER_COLUMN_G_ACPK_ASSIGNEECLASSPK_2 =
		"kaleoTaskAssignmentInstance.assigneeClassPK = ?";

	private FinderPath _finderPathWithPaginationFindByKTITI_ACN;
	private FinderPath _finderPathWithoutPaginationFindByKTITI_ACN;
	private FinderPath _finderPathCountByKTITI_ACN;

	/**
	 * Returns all the kaleo task assignment instances where kaleoTaskInstanceTokenId = &#63; and assigneeClassName = &#63;.
	 *
	 * @param kaleoTaskInstanceTokenId the kaleo task instance token ID
	 * @param assigneeClassName the assignee class name
	 * @return the matching kaleo task assignment instances
	 */
	@Override
	public List<KaleoTaskAssignmentInstance> findByKTITI_ACN(
		long kaleoTaskInstanceTokenId, String assigneeClassName) {

		return findByKTITI_ACN(
			kaleoTaskInstanceTokenId, assigneeClassName, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the kaleo task assignment instances where kaleoTaskInstanceTokenId = &#63; and assigneeClassName = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoTaskAssignmentInstanceModelImpl</code>.
	 * </p>
	 *
	 * @param kaleoTaskInstanceTokenId the kaleo task instance token ID
	 * @param assigneeClassName the assignee class name
	 * @param start the lower bound of the range of kaleo task assignment instances
	 * @param end the upper bound of the range of kaleo task assignment instances (not inclusive)
	 * @return the range of matching kaleo task assignment instances
	 */
	@Override
	public List<KaleoTaskAssignmentInstance> findByKTITI_ACN(
		long kaleoTaskInstanceTokenId, String assigneeClassName, int start,
		int end) {

		return findByKTITI_ACN(
			kaleoTaskInstanceTokenId, assigneeClassName, start, end, null);
	}

	/**
	 * Returns an ordered range of all the kaleo task assignment instances where kaleoTaskInstanceTokenId = &#63; and assigneeClassName = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoTaskAssignmentInstanceModelImpl</code>.
	 * </p>
	 *
	 * @param kaleoTaskInstanceTokenId the kaleo task instance token ID
	 * @param assigneeClassName the assignee class name
	 * @param start the lower bound of the range of kaleo task assignment instances
	 * @param end the upper bound of the range of kaleo task assignment instances (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching kaleo task assignment instances
	 */
	@Override
	public List<KaleoTaskAssignmentInstance> findByKTITI_ACN(
		long kaleoTaskInstanceTokenId, String assigneeClassName, int start,
		int end,
		OrderByComparator<KaleoTaskAssignmentInstance> orderByComparator) {

		return findByKTITI_ACN(
			kaleoTaskInstanceTokenId, assigneeClassName, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the kaleo task assignment instances where kaleoTaskInstanceTokenId = &#63; and assigneeClassName = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoTaskAssignmentInstanceModelImpl</code>.
	 * </p>
	 *
	 * @param kaleoTaskInstanceTokenId the kaleo task instance token ID
	 * @param assigneeClassName the assignee class name
	 * @param start the lower bound of the range of kaleo task assignment instances
	 * @param end the upper bound of the range of kaleo task assignment instances (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching kaleo task assignment instances
	 */
	@Override
	public List<KaleoTaskAssignmentInstance> findByKTITI_ACN(
		long kaleoTaskInstanceTokenId, String assigneeClassName, int start,
		int end,
		OrderByComparator<KaleoTaskAssignmentInstance> orderByComparator,
		boolean useFinderCache) {

		assigneeClassName = Objects.toString(assigneeClassName, "");

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByKTITI_ACN;
				finderArgs = new Object[] {
					kaleoTaskInstanceTokenId, assigneeClassName
				};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByKTITI_ACN;
			finderArgs = new Object[] {
				kaleoTaskInstanceTokenId, assigneeClassName, start, end,
				orderByComparator
			};
		}

		List<KaleoTaskAssignmentInstance> list = null;

		if (useFinderCache) {
			list = (List<KaleoTaskAssignmentInstance>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (KaleoTaskAssignmentInstance kaleoTaskAssignmentInstance :
						list) {

					if ((kaleoTaskInstanceTokenId !=
							kaleoTaskAssignmentInstance.
								getKaleoTaskInstanceTokenId()) ||
						!assigneeClassName.equals(
							kaleoTaskAssignmentInstance.
								getAssigneeClassName())) {

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

			sb.append(_SQL_SELECT_KALEOTASKASSIGNMENTINSTANCE_WHERE);

			sb.append(_FINDER_COLUMN_KTITI_ACN_KALEOTASKINSTANCETOKENID_2);

			boolean bindAssigneeClassName = false;

			if (assigneeClassName.isEmpty()) {
				sb.append(_FINDER_COLUMN_KTITI_ACN_ASSIGNEECLASSNAME_3);
			}
			else {
				bindAssigneeClassName = true;

				sb.append(_FINDER_COLUMN_KTITI_ACN_ASSIGNEECLASSNAME_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(KaleoTaskAssignmentInstanceModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(kaleoTaskInstanceTokenId);

				if (bindAssigneeClassName) {
					queryPos.add(assigneeClassName);
				}

				list = (List<KaleoTaskAssignmentInstance>)QueryUtil.list(
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
	 * Returns the first kaleo task assignment instance in the ordered set where kaleoTaskInstanceTokenId = &#63; and assigneeClassName = &#63;.
	 *
	 * @param kaleoTaskInstanceTokenId the kaleo task instance token ID
	 * @param assigneeClassName the assignee class name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching kaleo task assignment instance
	 * @throws NoSuchTaskAssignmentInstanceException if a matching kaleo task assignment instance could not be found
	 */
	@Override
	public KaleoTaskAssignmentInstance findByKTITI_ACN_First(
			long kaleoTaskInstanceTokenId, String assigneeClassName,
			OrderByComparator<KaleoTaskAssignmentInstance> orderByComparator)
		throws NoSuchTaskAssignmentInstanceException {

		KaleoTaskAssignmentInstance kaleoTaskAssignmentInstance =
			fetchByKTITI_ACN_First(
				kaleoTaskInstanceTokenId, assigneeClassName, orderByComparator);

		if (kaleoTaskAssignmentInstance != null) {
			return kaleoTaskAssignmentInstance;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("kaleoTaskInstanceTokenId=");
		sb.append(kaleoTaskInstanceTokenId);

		sb.append(", assigneeClassName=");
		sb.append(assigneeClassName);

		sb.append("}");

		throw new NoSuchTaskAssignmentInstanceException(sb.toString());
	}

	/**
	 * Returns the first kaleo task assignment instance in the ordered set where kaleoTaskInstanceTokenId = &#63; and assigneeClassName = &#63;.
	 *
	 * @param kaleoTaskInstanceTokenId the kaleo task instance token ID
	 * @param assigneeClassName the assignee class name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching kaleo task assignment instance, or <code>null</code> if a matching kaleo task assignment instance could not be found
	 */
	@Override
	public KaleoTaskAssignmentInstance fetchByKTITI_ACN_First(
		long kaleoTaskInstanceTokenId, String assigneeClassName,
		OrderByComparator<KaleoTaskAssignmentInstance> orderByComparator) {

		List<KaleoTaskAssignmentInstance> list = findByKTITI_ACN(
			kaleoTaskInstanceTokenId, assigneeClassName, 0, 1,
			orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last kaleo task assignment instance in the ordered set where kaleoTaskInstanceTokenId = &#63; and assigneeClassName = &#63;.
	 *
	 * @param kaleoTaskInstanceTokenId the kaleo task instance token ID
	 * @param assigneeClassName the assignee class name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching kaleo task assignment instance
	 * @throws NoSuchTaskAssignmentInstanceException if a matching kaleo task assignment instance could not be found
	 */
	@Override
	public KaleoTaskAssignmentInstance findByKTITI_ACN_Last(
			long kaleoTaskInstanceTokenId, String assigneeClassName,
			OrderByComparator<KaleoTaskAssignmentInstance> orderByComparator)
		throws NoSuchTaskAssignmentInstanceException {

		KaleoTaskAssignmentInstance kaleoTaskAssignmentInstance =
			fetchByKTITI_ACN_Last(
				kaleoTaskInstanceTokenId, assigneeClassName, orderByComparator);

		if (kaleoTaskAssignmentInstance != null) {
			return kaleoTaskAssignmentInstance;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("kaleoTaskInstanceTokenId=");
		sb.append(kaleoTaskInstanceTokenId);

		sb.append(", assigneeClassName=");
		sb.append(assigneeClassName);

		sb.append("}");

		throw new NoSuchTaskAssignmentInstanceException(sb.toString());
	}

	/**
	 * Returns the last kaleo task assignment instance in the ordered set where kaleoTaskInstanceTokenId = &#63; and assigneeClassName = &#63;.
	 *
	 * @param kaleoTaskInstanceTokenId the kaleo task instance token ID
	 * @param assigneeClassName the assignee class name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching kaleo task assignment instance, or <code>null</code> if a matching kaleo task assignment instance could not be found
	 */
	@Override
	public KaleoTaskAssignmentInstance fetchByKTITI_ACN_Last(
		long kaleoTaskInstanceTokenId, String assigneeClassName,
		OrderByComparator<KaleoTaskAssignmentInstance> orderByComparator) {

		int count = countByKTITI_ACN(
			kaleoTaskInstanceTokenId, assigneeClassName);

		if (count == 0) {
			return null;
		}

		List<KaleoTaskAssignmentInstance> list = findByKTITI_ACN(
			kaleoTaskInstanceTokenId, assigneeClassName, count - 1, count,
			orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the kaleo task assignment instances before and after the current kaleo task assignment instance in the ordered set where kaleoTaskInstanceTokenId = &#63; and assigneeClassName = &#63;.
	 *
	 * @param kaleoTaskAssignmentInstanceId the primary key of the current kaleo task assignment instance
	 * @param kaleoTaskInstanceTokenId the kaleo task instance token ID
	 * @param assigneeClassName the assignee class name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next kaleo task assignment instance
	 * @throws NoSuchTaskAssignmentInstanceException if a kaleo task assignment instance with the primary key could not be found
	 */
	@Override
	public KaleoTaskAssignmentInstance[] findByKTITI_ACN_PrevAndNext(
			long kaleoTaskAssignmentInstanceId, long kaleoTaskInstanceTokenId,
			String assigneeClassName,
			OrderByComparator<KaleoTaskAssignmentInstance> orderByComparator)
		throws NoSuchTaskAssignmentInstanceException {

		assigneeClassName = Objects.toString(assigneeClassName, "");

		KaleoTaskAssignmentInstance kaleoTaskAssignmentInstance =
			findByPrimaryKey(kaleoTaskAssignmentInstanceId);

		Session session = null;

		try {
			session = openSession();

			KaleoTaskAssignmentInstance[] array =
				new KaleoTaskAssignmentInstanceImpl[3];

			array[0] = getByKTITI_ACN_PrevAndNext(
				session, kaleoTaskAssignmentInstance, kaleoTaskInstanceTokenId,
				assigneeClassName, orderByComparator, true);

			array[1] = kaleoTaskAssignmentInstance;

			array[2] = getByKTITI_ACN_PrevAndNext(
				session, kaleoTaskAssignmentInstance, kaleoTaskInstanceTokenId,
				assigneeClassName, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected KaleoTaskAssignmentInstance getByKTITI_ACN_PrevAndNext(
		Session session,
		KaleoTaskAssignmentInstance kaleoTaskAssignmentInstance,
		long kaleoTaskInstanceTokenId, String assigneeClassName,
		OrderByComparator<KaleoTaskAssignmentInstance> orderByComparator,
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

		sb.append(_SQL_SELECT_KALEOTASKASSIGNMENTINSTANCE_WHERE);

		sb.append(_FINDER_COLUMN_KTITI_ACN_KALEOTASKINSTANCETOKENID_2);

		boolean bindAssigneeClassName = false;

		if (assigneeClassName.isEmpty()) {
			sb.append(_FINDER_COLUMN_KTITI_ACN_ASSIGNEECLASSNAME_3);
		}
		else {
			bindAssigneeClassName = true;

			sb.append(_FINDER_COLUMN_KTITI_ACN_ASSIGNEECLASSNAME_2);
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
			sb.append(KaleoTaskAssignmentInstanceModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(kaleoTaskInstanceTokenId);

		if (bindAssigneeClassName) {
			queryPos.add(assigneeClassName);
		}

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						kaleoTaskAssignmentInstance)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<KaleoTaskAssignmentInstance> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the kaleo task assignment instances where kaleoTaskInstanceTokenId = &#63; and assigneeClassName = &#63; from the database.
	 *
	 * @param kaleoTaskInstanceTokenId the kaleo task instance token ID
	 * @param assigneeClassName the assignee class name
	 */
	@Override
	public void removeByKTITI_ACN(
		long kaleoTaskInstanceTokenId, String assigneeClassName) {

		for (KaleoTaskAssignmentInstance kaleoTaskAssignmentInstance :
				findByKTITI_ACN(
					kaleoTaskInstanceTokenId, assigneeClassName,
					QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(kaleoTaskAssignmentInstance);
		}
	}

	/**
	 * Returns the number of kaleo task assignment instances where kaleoTaskInstanceTokenId = &#63; and assigneeClassName = &#63;.
	 *
	 * @param kaleoTaskInstanceTokenId the kaleo task instance token ID
	 * @param assigneeClassName the assignee class name
	 * @return the number of matching kaleo task assignment instances
	 */
	@Override
	public int countByKTITI_ACN(
		long kaleoTaskInstanceTokenId, String assigneeClassName) {

		assigneeClassName = Objects.toString(assigneeClassName, "");

		FinderPath finderPath = _finderPathCountByKTITI_ACN;

		Object[] finderArgs = new Object[] {
			kaleoTaskInstanceTokenId, assigneeClassName
		};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_KALEOTASKASSIGNMENTINSTANCE_WHERE);

			sb.append(_FINDER_COLUMN_KTITI_ACN_KALEOTASKINSTANCETOKENID_2);

			boolean bindAssigneeClassName = false;

			if (assigneeClassName.isEmpty()) {
				sb.append(_FINDER_COLUMN_KTITI_ACN_ASSIGNEECLASSNAME_3);
			}
			else {
				bindAssigneeClassName = true;

				sb.append(_FINDER_COLUMN_KTITI_ACN_ASSIGNEECLASSNAME_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(kaleoTaskInstanceTokenId);

				if (bindAssigneeClassName) {
					queryPos.add(assigneeClassName);
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
		_FINDER_COLUMN_KTITI_ACN_KALEOTASKINSTANCETOKENID_2 =
			"kaleoTaskAssignmentInstance.kaleoTaskInstanceTokenId = ? AND ";

	private static final String _FINDER_COLUMN_KTITI_ACN_ASSIGNEECLASSNAME_2 =
		"kaleoTaskAssignmentInstance.assigneeClassName = ?";

	private static final String _FINDER_COLUMN_KTITI_ACN_ASSIGNEECLASSNAME_3 =
		"(kaleoTaskAssignmentInstance.assigneeClassName IS NULL OR kaleoTaskAssignmentInstance.assigneeClassName = '')";

	private FinderPath _finderPathWithPaginationFindByACN_ACPK;
	private FinderPath _finderPathWithoutPaginationFindByACN_ACPK;
	private FinderPath _finderPathCountByACN_ACPK;

	/**
	 * Returns all the kaleo task assignment instances where assigneeClassName = &#63; and assigneeClassPK = &#63;.
	 *
	 * @param assigneeClassName the assignee class name
	 * @param assigneeClassPK the assignee class pk
	 * @return the matching kaleo task assignment instances
	 */
	@Override
	public List<KaleoTaskAssignmentInstance> findByACN_ACPK(
		String assigneeClassName, long assigneeClassPK) {

		return findByACN_ACPK(
			assigneeClassName, assigneeClassPK, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the kaleo task assignment instances where assigneeClassName = &#63; and assigneeClassPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoTaskAssignmentInstanceModelImpl</code>.
	 * </p>
	 *
	 * @param assigneeClassName the assignee class name
	 * @param assigneeClassPK the assignee class pk
	 * @param start the lower bound of the range of kaleo task assignment instances
	 * @param end the upper bound of the range of kaleo task assignment instances (not inclusive)
	 * @return the range of matching kaleo task assignment instances
	 */
	@Override
	public List<KaleoTaskAssignmentInstance> findByACN_ACPK(
		String assigneeClassName, long assigneeClassPK, int start, int end) {

		return findByACN_ACPK(
			assigneeClassName, assigneeClassPK, start, end, null);
	}

	/**
	 * Returns an ordered range of all the kaleo task assignment instances where assigneeClassName = &#63; and assigneeClassPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoTaskAssignmentInstanceModelImpl</code>.
	 * </p>
	 *
	 * @param assigneeClassName the assignee class name
	 * @param assigneeClassPK the assignee class pk
	 * @param start the lower bound of the range of kaleo task assignment instances
	 * @param end the upper bound of the range of kaleo task assignment instances (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching kaleo task assignment instances
	 */
	@Override
	public List<KaleoTaskAssignmentInstance> findByACN_ACPK(
		String assigneeClassName, long assigneeClassPK, int start, int end,
		OrderByComparator<KaleoTaskAssignmentInstance> orderByComparator) {

		return findByACN_ACPK(
			assigneeClassName, assigneeClassPK, start, end, orderByComparator,
			true);
	}

	/**
	 * Returns an ordered range of all the kaleo task assignment instances where assigneeClassName = &#63; and assigneeClassPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoTaskAssignmentInstanceModelImpl</code>.
	 * </p>
	 *
	 * @param assigneeClassName the assignee class name
	 * @param assigneeClassPK the assignee class pk
	 * @param start the lower bound of the range of kaleo task assignment instances
	 * @param end the upper bound of the range of kaleo task assignment instances (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching kaleo task assignment instances
	 */
	@Override
	public List<KaleoTaskAssignmentInstance> findByACN_ACPK(
		String assigneeClassName, long assigneeClassPK, int start, int end,
		OrderByComparator<KaleoTaskAssignmentInstance> orderByComparator,
		boolean useFinderCache) {

		assigneeClassName = Objects.toString(assigneeClassName, "");

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByACN_ACPK;
				finderArgs = new Object[] {assigneeClassName, assigneeClassPK};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByACN_ACPK;
			finderArgs = new Object[] {
				assigneeClassName, assigneeClassPK, start, end,
				orderByComparator
			};
		}

		List<KaleoTaskAssignmentInstance> list = null;

		if (useFinderCache) {
			list = (List<KaleoTaskAssignmentInstance>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (KaleoTaskAssignmentInstance kaleoTaskAssignmentInstance :
						list) {

					if (!assigneeClassName.equals(
							kaleoTaskAssignmentInstance.
								getAssigneeClassName()) ||
						(assigneeClassPK !=
							kaleoTaskAssignmentInstance.getAssigneeClassPK())) {

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

			sb.append(_SQL_SELECT_KALEOTASKASSIGNMENTINSTANCE_WHERE);

			boolean bindAssigneeClassName = false;

			if (assigneeClassName.isEmpty()) {
				sb.append(_FINDER_COLUMN_ACN_ACPK_ASSIGNEECLASSNAME_3);
			}
			else {
				bindAssigneeClassName = true;

				sb.append(_FINDER_COLUMN_ACN_ACPK_ASSIGNEECLASSNAME_2);
			}

			sb.append(_FINDER_COLUMN_ACN_ACPK_ASSIGNEECLASSPK_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(KaleoTaskAssignmentInstanceModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindAssigneeClassName) {
					queryPos.add(assigneeClassName);
				}

				queryPos.add(assigneeClassPK);

				list = (List<KaleoTaskAssignmentInstance>)QueryUtil.list(
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
	 * Returns the first kaleo task assignment instance in the ordered set where assigneeClassName = &#63; and assigneeClassPK = &#63;.
	 *
	 * @param assigneeClassName the assignee class name
	 * @param assigneeClassPK the assignee class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching kaleo task assignment instance
	 * @throws NoSuchTaskAssignmentInstanceException if a matching kaleo task assignment instance could not be found
	 */
	@Override
	public KaleoTaskAssignmentInstance findByACN_ACPK_First(
			String assigneeClassName, long assigneeClassPK,
			OrderByComparator<KaleoTaskAssignmentInstance> orderByComparator)
		throws NoSuchTaskAssignmentInstanceException {

		KaleoTaskAssignmentInstance kaleoTaskAssignmentInstance =
			fetchByACN_ACPK_First(
				assigneeClassName, assigneeClassPK, orderByComparator);

		if (kaleoTaskAssignmentInstance != null) {
			return kaleoTaskAssignmentInstance;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("assigneeClassName=");
		sb.append(assigneeClassName);

		sb.append(", assigneeClassPK=");
		sb.append(assigneeClassPK);

		sb.append("}");

		throw new NoSuchTaskAssignmentInstanceException(sb.toString());
	}

	/**
	 * Returns the first kaleo task assignment instance in the ordered set where assigneeClassName = &#63; and assigneeClassPK = &#63;.
	 *
	 * @param assigneeClassName the assignee class name
	 * @param assigneeClassPK the assignee class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching kaleo task assignment instance, or <code>null</code> if a matching kaleo task assignment instance could not be found
	 */
	@Override
	public KaleoTaskAssignmentInstance fetchByACN_ACPK_First(
		String assigneeClassName, long assigneeClassPK,
		OrderByComparator<KaleoTaskAssignmentInstance> orderByComparator) {

		List<KaleoTaskAssignmentInstance> list = findByACN_ACPK(
			assigneeClassName, assigneeClassPK, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last kaleo task assignment instance in the ordered set where assigneeClassName = &#63; and assigneeClassPK = &#63;.
	 *
	 * @param assigneeClassName the assignee class name
	 * @param assigneeClassPK the assignee class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching kaleo task assignment instance
	 * @throws NoSuchTaskAssignmentInstanceException if a matching kaleo task assignment instance could not be found
	 */
	@Override
	public KaleoTaskAssignmentInstance findByACN_ACPK_Last(
			String assigneeClassName, long assigneeClassPK,
			OrderByComparator<KaleoTaskAssignmentInstance> orderByComparator)
		throws NoSuchTaskAssignmentInstanceException {

		KaleoTaskAssignmentInstance kaleoTaskAssignmentInstance =
			fetchByACN_ACPK_Last(
				assigneeClassName, assigneeClassPK, orderByComparator);

		if (kaleoTaskAssignmentInstance != null) {
			return kaleoTaskAssignmentInstance;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("assigneeClassName=");
		sb.append(assigneeClassName);

		sb.append(", assigneeClassPK=");
		sb.append(assigneeClassPK);

		sb.append("}");

		throw new NoSuchTaskAssignmentInstanceException(sb.toString());
	}

	/**
	 * Returns the last kaleo task assignment instance in the ordered set where assigneeClassName = &#63; and assigneeClassPK = &#63;.
	 *
	 * @param assigneeClassName the assignee class name
	 * @param assigneeClassPK the assignee class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching kaleo task assignment instance, or <code>null</code> if a matching kaleo task assignment instance could not be found
	 */
	@Override
	public KaleoTaskAssignmentInstance fetchByACN_ACPK_Last(
		String assigneeClassName, long assigneeClassPK,
		OrderByComparator<KaleoTaskAssignmentInstance> orderByComparator) {

		int count = countByACN_ACPK(assigneeClassName, assigneeClassPK);

		if (count == 0) {
			return null;
		}

		List<KaleoTaskAssignmentInstance> list = findByACN_ACPK(
			assigneeClassName, assigneeClassPK, count - 1, count,
			orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the kaleo task assignment instances before and after the current kaleo task assignment instance in the ordered set where assigneeClassName = &#63; and assigneeClassPK = &#63;.
	 *
	 * @param kaleoTaskAssignmentInstanceId the primary key of the current kaleo task assignment instance
	 * @param assigneeClassName the assignee class name
	 * @param assigneeClassPK the assignee class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next kaleo task assignment instance
	 * @throws NoSuchTaskAssignmentInstanceException if a kaleo task assignment instance with the primary key could not be found
	 */
	@Override
	public KaleoTaskAssignmentInstance[] findByACN_ACPK_PrevAndNext(
			long kaleoTaskAssignmentInstanceId, String assigneeClassName,
			long assigneeClassPK,
			OrderByComparator<KaleoTaskAssignmentInstance> orderByComparator)
		throws NoSuchTaskAssignmentInstanceException {

		assigneeClassName = Objects.toString(assigneeClassName, "");

		KaleoTaskAssignmentInstance kaleoTaskAssignmentInstance =
			findByPrimaryKey(kaleoTaskAssignmentInstanceId);

		Session session = null;

		try {
			session = openSession();

			KaleoTaskAssignmentInstance[] array =
				new KaleoTaskAssignmentInstanceImpl[3];

			array[0] = getByACN_ACPK_PrevAndNext(
				session, kaleoTaskAssignmentInstance, assigneeClassName,
				assigneeClassPK, orderByComparator, true);

			array[1] = kaleoTaskAssignmentInstance;

			array[2] = getByACN_ACPK_PrevAndNext(
				session, kaleoTaskAssignmentInstance, assigneeClassName,
				assigneeClassPK, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected KaleoTaskAssignmentInstance getByACN_ACPK_PrevAndNext(
		Session session,
		KaleoTaskAssignmentInstance kaleoTaskAssignmentInstance,
		String assigneeClassName, long assigneeClassPK,
		OrderByComparator<KaleoTaskAssignmentInstance> orderByComparator,
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

		sb.append(_SQL_SELECT_KALEOTASKASSIGNMENTINSTANCE_WHERE);

		boolean bindAssigneeClassName = false;

		if (assigneeClassName.isEmpty()) {
			sb.append(_FINDER_COLUMN_ACN_ACPK_ASSIGNEECLASSNAME_3);
		}
		else {
			bindAssigneeClassName = true;

			sb.append(_FINDER_COLUMN_ACN_ACPK_ASSIGNEECLASSNAME_2);
		}

		sb.append(_FINDER_COLUMN_ACN_ACPK_ASSIGNEECLASSPK_2);

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
			sb.append(KaleoTaskAssignmentInstanceModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		if (bindAssigneeClassName) {
			queryPos.add(assigneeClassName);
		}

		queryPos.add(assigneeClassPK);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						kaleoTaskAssignmentInstance)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<KaleoTaskAssignmentInstance> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the kaleo task assignment instances where assigneeClassName = &#63; and assigneeClassPK = &#63; from the database.
	 *
	 * @param assigneeClassName the assignee class name
	 * @param assigneeClassPK the assignee class pk
	 */
	@Override
	public void removeByACN_ACPK(
		String assigneeClassName, long assigneeClassPK) {

		for (KaleoTaskAssignmentInstance kaleoTaskAssignmentInstance :
				findByACN_ACPK(
					assigneeClassName, assigneeClassPK, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			remove(kaleoTaskAssignmentInstance);
		}
	}

	/**
	 * Returns the number of kaleo task assignment instances where assigneeClassName = &#63; and assigneeClassPK = &#63;.
	 *
	 * @param assigneeClassName the assignee class name
	 * @param assigneeClassPK the assignee class pk
	 * @return the number of matching kaleo task assignment instances
	 */
	@Override
	public int countByACN_ACPK(String assigneeClassName, long assigneeClassPK) {
		assigneeClassName = Objects.toString(assigneeClassName, "");

		FinderPath finderPath = _finderPathCountByACN_ACPK;

		Object[] finderArgs = new Object[] {assigneeClassName, assigneeClassPK};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_KALEOTASKASSIGNMENTINSTANCE_WHERE);

			boolean bindAssigneeClassName = false;

			if (assigneeClassName.isEmpty()) {
				sb.append(_FINDER_COLUMN_ACN_ACPK_ASSIGNEECLASSNAME_3);
			}
			else {
				bindAssigneeClassName = true;

				sb.append(_FINDER_COLUMN_ACN_ACPK_ASSIGNEECLASSNAME_2);
			}

			sb.append(_FINDER_COLUMN_ACN_ACPK_ASSIGNEECLASSPK_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindAssigneeClassName) {
					queryPos.add(assigneeClassName);
				}

				queryPos.add(assigneeClassPK);

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

	private static final String _FINDER_COLUMN_ACN_ACPK_ASSIGNEECLASSNAME_2 =
		"kaleoTaskAssignmentInstance.assigneeClassName = ? AND ";

	private static final String _FINDER_COLUMN_ACN_ACPK_ASSIGNEECLASSNAME_3 =
		"(kaleoTaskAssignmentInstance.assigneeClassName IS NULL OR kaleoTaskAssignmentInstance.assigneeClassName = '') AND ";

	private static final String _FINDER_COLUMN_ACN_ACPK_ASSIGNEECLASSPK_2 =
		"kaleoTaskAssignmentInstance.assigneeClassPK = ?";

	public KaleoTaskAssignmentInstancePersistenceImpl() {
		setModelClass(KaleoTaskAssignmentInstance.class);

		setModelImplClass(KaleoTaskAssignmentInstanceImpl.class);
		setModelPKClass(long.class);

		setTable(KaleoTaskAssignmentInstanceTable.INSTANCE);
	}

	/**
	 * Caches the kaleo task assignment instance in the entity cache if it is enabled.
	 *
	 * @param kaleoTaskAssignmentInstance the kaleo task assignment instance
	 */
	@Override
	public void cacheResult(
		KaleoTaskAssignmentInstance kaleoTaskAssignmentInstance) {

		entityCache.putResult(
			KaleoTaskAssignmentInstanceImpl.class,
			kaleoTaskAssignmentInstance.getPrimaryKey(),
			kaleoTaskAssignmentInstance);
	}

	private int _valueObjectFinderCacheListThreshold;

	/**
	 * Caches the kaleo task assignment instances in the entity cache if it is enabled.
	 *
	 * @param kaleoTaskAssignmentInstances the kaleo task assignment instances
	 */
	@Override
	public void cacheResult(
		List<KaleoTaskAssignmentInstance> kaleoTaskAssignmentInstances) {

		if ((_valueObjectFinderCacheListThreshold == 0) ||
			((_valueObjectFinderCacheListThreshold > 0) &&
			 (kaleoTaskAssignmentInstances.size() >
				 _valueObjectFinderCacheListThreshold))) {

			return;
		}

		for (KaleoTaskAssignmentInstance kaleoTaskAssignmentInstance :
				kaleoTaskAssignmentInstances) {

			if (entityCache.getResult(
					KaleoTaskAssignmentInstanceImpl.class,
					kaleoTaskAssignmentInstance.getPrimaryKey()) == null) {

				cacheResult(kaleoTaskAssignmentInstance);
			}
		}
	}

	/**
	 * Clears the cache for all kaleo task assignment instances.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(KaleoTaskAssignmentInstanceImpl.class);

		finderCache.clearCache(KaleoTaskAssignmentInstanceImpl.class);
	}

	/**
	 * Clears the cache for the kaleo task assignment instance.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(
		KaleoTaskAssignmentInstance kaleoTaskAssignmentInstance) {

		entityCache.removeResult(
			KaleoTaskAssignmentInstanceImpl.class, kaleoTaskAssignmentInstance);
	}

	@Override
	public void clearCache(
		List<KaleoTaskAssignmentInstance> kaleoTaskAssignmentInstances) {

		for (KaleoTaskAssignmentInstance kaleoTaskAssignmentInstance :
				kaleoTaskAssignmentInstances) {

			entityCache.removeResult(
				KaleoTaskAssignmentInstanceImpl.class,
				kaleoTaskAssignmentInstance);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(KaleoTaskAssignmentInstanceImpl.class);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(
				KaleoTaskAssignmentInstanceImpl.class, primaryKey);
		}
	}

	/**
	 * Creates a new kaleo task assignment instance with the primary key. Does not add the kaleo task assignment instance to the database.
	 *
	 * @param kaleoTaskAssignmentInstanceId the primary key for the new kaleo task assignment instance
	 * @return the new kaleo task assignment instance
	 */
	@Override
	public KaleoTaskAssignmentInstance create(
		long kaleoTaskAssignmentInstanceId) {

		KaleoTaskAssignmentInstance kaleoTaskAssignmentInstance =
			new KaleoTaskAssignmentInstanceImpl();

		kaleoTaskAssignmentInstance.setNew(true);
		kaleoTaskAssignmentInstance.setPrimaryKey(
			kaleoTaskAssignmentInstanceId);

		kaleoTaskAssignmentInstance.setCompanyId(
			CompanyThreadLocal.getCompanyId());

		return kaleoTaskAssignmentInstance;
	}

	/**
	 * Removes the kaleo task assignment instance with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param kaleoTaskAssignmentInstanceId the primary key of the kaleo task assignment instance
	 * @return the kaleo task assignment instance that was removed
	 * @throws NoSuchTaskAssignmentInstanceException if a kaleo task assignment instance with the primary key could not be found
	 */
	@Override
	public KaleoTaskAssignmentInstance remove(
			long kaleoTaskAssignmentInstanceId)
		throws NoSuchTaskAssignmentInstanceException {

		return remove((Serializable)kaleoTaskAssignmentInstanceId);
	}

	/**
	 * Removes the kaleo task assignment instance with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the kaleo task assignment instance
	 * @return the kaleo task assignment instance that was removed
	 * @throws NoSuchTaskAssignmentInstanceException if a kaleo task assignment instance with the primary key could not be found
	 */
	@Override
	public KaleoTaskAssignmentInstance remove(Serializable primaryKey)
		throws NoSuchTaskAssignmentInstanceException {

		Session session = null;

		try {
			session = openSession();

			KaleoTaskAssignmentInstance kaleoTaskAssignmentInstance =
				(KaleoTaskAssignmentInstance)session.get(
					KaleoTaskAssignmentInstanceImpl.class, primaryKey);

			if (kaleoTaskAssignmentInstance == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchTaskAssignmentInstanceException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(kaleoTaskAssignmentInstance);
		}
		catch (NoSuchTaskAssignmentInstanceException noSuchEntityException) {
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
	protected KaleoTaskAssignmentInstance removeImpl(
		KaleoTaskAssignmentInstance kaleoTaskAssignmentInstance) {

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(kaleoTaskAssignmentInstance)) {
				kaleoTaskAssignmentInstance =
					(KaleoTaskAssignmentInstance)session.get(
						KaleoTaskAssignmentInstanceImpl.class,
						kaleoTaskAssignmentInstance.getPrimaryKeyObj());
			}

			if (kaleoTaskAssignmentInstance != null) {
				session.delete(kaleoTaskAssignmentInstance);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (kaleoTaskAssignmentInstance != null) {
			clearCache(kaleoTaskAssignmentInstance);
		}

		return kaleoTaskAssignmentInstance;
	}

	@Override
	public KaleoTaskAssignmentInstance updateImpl(
		KaleoTaskAssignmentInstance kaleoTaskAssignmentInstance) {

		boolean isNew = kaleoTaskAssignmentInstance.isNew();

		if (!(kaleoTaskAssignmentInstance instanceof
				KaleoTaskAssignmentInstanceModelImpl)) {

			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(
					kaleoTaskAssignmentInstance.getClass())) {

				invocationHandler = ProxyUtil.getInvocationHandler(
					kaleoTaskAssignmentInstance);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in kaleoTaskAssignmentInstance proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom KaleoTaskAssignmentInstance implementation " +
					kaleoTaskAssignmentInstance.getClass());
		}

		KaleoTaskAssignmentInstanceModelImpl
			kaleoTaskAssignmentInstanceModelImpl =
				(KaleoTaskAssignmentInstanceModelImpl)
					kaleoTaskAssignmentInstance;

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Date date = new Date();

		if (isNew && (kaleoTaskAssignmentInstance.getCreateDate() == null)) {
			if (serviceContext == null) {
				kaleoTaskAssignmentInstance.setCreateDate(date);
			}
			else {
				kaleoTaskAssignmentInstance.setCreateDate(
					serviceContext.getCreateDate(date));
			}
		}

		if (!kaleoTaskAssignmentInstanceModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				kaleoTaskAssignmentInstance.setModifiedDate(date);
			}
			else {
				kaleoTaskAssignmentInstance.setModifiedDate(
					serviceContext.getModifiedDate(date));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (isNew) {
				session.save(kaleoTaskAssignmentInstance);
			}
			else {
				kaleoTaskAssignmentInstance =
					(KaleoTaskAssignmentInstance)session.merge(
						kaleoTaskAssignmentInstance);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		entityCache.putResult(
			KaleoTaskAssignmentInstanceImpl.class,
			kaleoTaskAssignmentInstanceModelImpl, false, true);

		if (isNew) {
			kaleoTaskAssignmentInstance.setNew(false);
		}

		kaleoTaskAssignmentInstance.resetOriginalValues();

		return kaleoTaskAssignmentInstance;
	}

	/**
	 * Returns the kaleo task assignment instance with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the kaleo task assignment instance
	 * @return the kaleo task assignment instance
	 * @throws NoSuchTaskAssignmentInstanceException if a kaleo task assignment instance with the primary key could not be found
	 */
	@Override
	public KaleoTaskAssignmentInstance findByPrimaryKey(Serializable primaryKey)
		throws NoSuchTaskAssignmentInstanceException {

		KaleoTaskAssignmentInstance kaleoTaskAssignmentInstance =
			fetchByPrimaryKey(primaryKey);

		if (kaleoTaskAssignmentInstance == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchTaskAssignmentInstanceException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return kaleoTaskAssignmentInstance;
	}

	/**
	 * Returns the kaleo task assignment instance with the primary key or throws a <code>NoSuchTaskAssignmentInstanceException</code> if it could not be found.
	 *
	 * @param kaleoTaskAssignmentInstanceId the primary key of the kaleo task assignment instance
	 * @return the kaleo task assignment instance
	 * @throws NoSuchTaskAssignmentInstanceException if a kaleo task assignment instance with the primary key could not be found
	 */
	@Override
	public KaleoTaskAssignmentInstance findByPrimaryKey(
			long kaleoTaskAssignmentInstanceId)
		throws NoSuchTaskAssignmentInstanceException {

		return findByPrimaryKey((Serializable)kaleoTaskAssignmentInstanceId);
	}

	/**
	 * Returns the kaleo task assignment instance with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param kaleoTaskAssignmentInstanceId the primary key of the kaleo task assignment instance
	 * @return the kaleo task assignment instance, or <code>null</code> if a kaleo task assignment instance with the primary key could not be found
	 */
	@Override
	public KaleoTaskAssignmentInstance fetchByPrimaryKey(
		long kaleoTaskAssignmentInstanceId) {

		return fetchByPrimaryKey((Serializable)kaleoTaskAssignmentInstanceId);
	}

	/**
	 * Returns all the kaleo task assignment instances.
	 *
	 * @return the kaleo task assignment instances
	 */
	@Override
	public List<KaleoTaskAssignmentInstance> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the kaleo task assignment instances.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoTaskAssignmentInstanceModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of kaleo task assignment instances
	 * @param end the upper bound of the range of kaleo task assignment instances (not inclusive)
	 * @return the range of kaleo task assignment instances
	 */
	@Override
	public List<KaleoTaskAssignmentInstance> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the kaleo task assignment instances.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoTaskAssignmentInstanceModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of kaleo task assignment instances
	 * @param end the upper bound of the range of kaleo task assignment instances (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of kaleo task assignment instances
	 */
	@Override
	public List<KaleoTaskAssignmentInstance> findAll(
		int start, int end,
		OrderByComparator<KaleoTaskAssignmentInstance> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the kaleo task assignment instances.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoTaskAssignmentInstanceModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of kaleo task assignment instances
	 * @param end the upper bound of the range of kaleo task assignment instances (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of kaleo task assignment instances
	 */
	@Override
	public List<KaleoTaskAssignmentInstance> findAll(
		int start, int end,
		OrderByComparator<KaleoTaskAssignmentInstance> orderByComparator,
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

		List<KaleoTaskAssignmentInstance> list = null;

		if (useFinderCache) {
			list = (List<KaleoTaskAssignmentInstance>)finderCache.getResult(
				finderPath, finderArgs);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_KALEOTASKASSIGNMENTINSTANCE);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_KALEOTASKASSIGNMENTINSTANCE;

				sql = sql.concat(
					KaleoTaskAssignmentInstanceModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<KaleoTaskAssignmentInstance>)QueryUtil.list(
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
	 * Removes all the kaleo task assignment instances from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (KaleoTaskAssignmentInstance kaleoTaskAssignmentInstance :
				findAll()) {

			remove(kaleoTaskAssignmentInstance);
		}
	}

	/**
	 * Returns the number of kaleo task assignment instances.
	 *
	 * @return the number of kaleo task assignment instances
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
					_SQL_COUNT_KALEOTASKASSIGNMENTINSTANCE);

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
		return "kaleoTaskAssignmentInstanceId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_KALEOTASKASSIGNMENTINSTANCE;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return KaleoTaskAssignmentInstanceModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the kaleo task assignment instance persistence.
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

		_finderPathWithPaginationFindByAssigneeClassName = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByAssigneeClassName",
			new String[] {
				String.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"assigneeClassName"}, true);

		_finderPathWithoutPaginationFindByAssigneeClassName = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByAssigneeClassName", new String[] {String.class.getName()},
			new String[] {"assigneeClassName"}, true);

		_finderPathCountByAssigneeClassName = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByAssigneeClassName", new String[] {String.class.getName()},
			new String[] {"assigneeClassName"}, false);

		_finderPathWithPaginationFindByG_ACPK = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_ACPK",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {"groupId", "assigneeClassPK"}, true);

		_finderPathWithoutPaginationFindByG_ACPK = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByG_ACPK",
			new String[] {Long.class.getName(), Long.class.getName()},
			new String[] {"groupId", "assigneeClassPK"}, true);

		_finderPathCountByG_ACPK = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_ACPK",
			new String[] {Long.class.getName(), Long.class.getName()},
			new String[] {"groupId", "assigneeClassPK"}, false);

		_finderPathWithPaginationFindByKTITI_ACN = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByKTITI_ACN",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {"kaleoTaskInstanceTokenId", "assigneeClassName"},
			true);

		_finderPathWithoutPaginationFindByKTITI_ACN = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByKTITI_ACN",
			new String[] {Long.class.getName(), String.class.getName()},
			new String[] {"kaleoTaskInstanceTokenId", "assigneeClassName"},
			true);

		_finderPathCountByKTITI_ACN = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByKTITI_ACN",
			new String[] {Long.class.getName(), String.class.getName()},
			new String[] {"kaleoTaskInstanceTokenId", "assigneeClassName"},
			false);

		_finderPathWithPaginationFindByACN_ACPK = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByACN_ACPK",
			new String[] {
				String.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {"assigneeClassName", "assigneeClassPK"}, true);

		_finderPathWithoutPaginationFindByACN_ACPK = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByACN_ACPK",
			new String[] {String.class.getName(), Long.class.getName()},
			new String[] {"assigneeClassName", "assigneeClassPK"}, true);

		_finderPathCountByACN_ACPK = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByACN_ACPK",
			new String[] {String.class.getName(), Long.class.getName()},
			new String[] {"assigneeClassName", "assigneeClassPK"}, false);
	}

	@Deactivate
	public void deactivate() {
		entityCache.removeCache(
			KaleoTaskAssignmentInstanceImpl.class.getName());
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

	private static final String _SQL_SELECT_KALEOTASKASSIGNMENTINSTANCE =
		"SELECT kaleoTaskAssignmentInstance FROM KaleoTaskAssignmentInstance kaleoTaskAssignmentInstance";

	private static final String _SQL_SELECT_KALEOTASKASSIGNMENTINSTANCE_WHERE =
		"SELECT kaleoTaskAssignmentInstance FROM KaleoTaskAssignmentInstance kaleoTaskAssignmentInstance WHERE ";

	private static final String _SQL_COUNT_KALEOTASKASSIGNMENTINSTANCE =
		"SELECT COUNT(kaleoTaskAssignmentInstance) FROM KaleoTaskAssignmentInstance kaleoTaskAssignmentInstance";

	private static final String _SQL_COUNT_KALEOTASKASSIGNMENTINSTANCE_WHERE =
		"SELECT COUNT(kaleoTaskAssignmentInstance) FROM KaleoTaskAssignmentInstance kaleoTaskAssignmentInstance WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS =
		"kaleoTaskAssignmentInstance.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No KaleoTaskAssignmentInstance exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No KaleoTaskAssignmentInstance exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		KaleoTaskAssignmentInstancePersistenceImpl.class);

	@Override
	protected FinderCache getFinderCache() {
		return finderCache;
	}

	@Reference
	private KaleoTaskAssignmentInstanceModelArgumentsResolver
		_kaleoTaskAssignmentInstanceModelArgumentsResolver;

}