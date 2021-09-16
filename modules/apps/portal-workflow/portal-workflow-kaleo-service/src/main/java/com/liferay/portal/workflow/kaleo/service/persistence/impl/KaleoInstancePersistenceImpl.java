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
import com.liferay.portal.workflow.kaleo.exception.NoSuchInstanceException;
import com.liferay.portal.workflow.kaleo.model.KaleoInstance;
import com.liferay.portal.workflow.kaleo.model.KaleoInstanceTable;
import com.liferay.portal.workflow.kaleo.model.impl.KaleoInstanceImpl;
import com.liferay.portal.workflow.kaleo.model.impl.KaleoInstanceModelImpl;
import com.liferay.portal.workflow.kaleo.service.persistence.KaleoInstancePersistence;
import com.liferay.portal.workflow.kaleo.service.persistence.impl.constants.KaleoPersistenceConstants;

import java.io.Serializable;

import java.lang.reflect.InvocationHandler;

import java.sql.Timestamp;

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
 * The persistence implementation for the kaleo instance service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
@Component(service = {KaleoInstancePersistence.class, BasePersistence.class})
public class KaleoInstancePersistenceImpl
	extends BasePersistenceImpl<KaleoInstance>
	implements KaleoInstancePersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>KaleoInstanceUtil</code> to access the kaleo instance persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		KaleoInstanceImpl.class.getName();

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
	 * Returns all the kaleo instances where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching kaleo instances
	 */
	@Override
	public List<KaleoInstance> findByCompanyId(long companyId) {
		return findByCompanyId(
			companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the kaleo instances where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoInstanceModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of kaleo instances
	 * @param end the upper bound of the range of kaleo instances (not inclusive)
	 * @return the range of matching kaleo instances
	 */
	@Override
	public List<KaleoInstance> findByCompanyId(
		long companyId, int start, int end) {

		return findByCompanyId(companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the kaleo instances where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoInstanceModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of kaleo instances
	 * @param end the upper bound of the range of kaleo instances (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching kaleo instances
	 */
	@Override
	public List<KaleoInstance> findByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<KaleoInstance> orderByComparator) {

		return findByCompanyId(companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the kaleo instances where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoInstanceModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of kaleo instances
	 * @param end the upper bound of the range of kaleo instances (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching kaleo instances
	 */
	@Override
	public List<KaleoInstance> findByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<KaleoInstance> orderByComparator,
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

		List<KaleoInstance> list = null;

		if (useFinderCache) {
			list = (List<KaleoInstance>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (KaleoInstance kaleoInstance : list) {
					if (companyId != kaleoInstance.getCompanyId()) {
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

			sb.append(_SQL_SELECT_KALEOINSTANCE_WHERE);

			sb.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(KaleoInstanceModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(companyId);

				list = (List<KaleoInstance>)QueryUtil.list(
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
	 * Returns the first kaleo instance in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching kaleo instance
	 * @throws NoSuchInstanceException if a matching kaleo instance could not be found
	 */
	@Override
	public KaleoInstance findByCompanyId_First(
			long companyId, OrderByComparator<KaleoInstance> orderByComparator)
		throws NoSuchInstanceException {

		KaleoInstance kaleoInstance = fetchByCompanyId_First(
			companyId, orderByComparator);

		if (kaleoInstance != null) {
			return kaleoInstance;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchInstanceException(sb.toString());
	}

	/**
	 * Returns the first kaleo instance in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching kaleo instance, or <code>null</code> if a matching kaleo instance could not be found
	 */
	@Override
	public KaleoInstance fetchByCompanyId_First(
		long companyId, OrderByComparator<KaleoInstance> orderByComparator) {

		List<KaleoInstance> list = findByCompanyId(
			companyId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last kaleo instance in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching kaleo instance
	 * @throws NoSuchInstanceException if a matching kaleo instance could not be found
	 */
	@Override
	public KaleoInstance findByCompanyId_Last(
			long companyId, OrderByComparator<KaleoInstance> orderByComparator)
		throws NoSuchInstanceException {

		KaleoInstance kaleoInstance = fetchByCompanyId_Last(
			companyId, orderByComparator);

		if (kaleoInstance != null) {
			return kaleoInstance;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchInstanceException(sb.toString());
	}

	/**
	 * Returns the last kaleo instance in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching kaleo instance, or <code>null</code> if a matching kaleo instance could not be found
	 */
	@Override
	public KaleoInstance fetchByCompanyId_Last(
		long companyId, OrderByComparator<KaleoInstance> orderByComparator) {

		int count = countByCompanyId(companyId);

		if (count == 0) {
			return null;
		}

		List<KaleoInstance> list = findByCompanyId(
			companyId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the kaleo instances before and after the current kaleo instance in the ordered set where companyId = &#63;.
	 *
	 * @param kaleoInstanceId the primary key of the current kaleo instance
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next kaleo instance
	 * @throws NoSuchInstanceException if a kaleo instance with the primary key could not be found
	 */
	@Override
	public KaleoInstance[] findByCompanyId_PrevAndNext(
			long kaleoInstanceId, long companyId,
			OrderByComparator<KaleoInstance> orderByComparator)
		throws NoSuchInstanceException {

		KaleoInstance kaleoInstance = findByPrimaryKey(kaleoInstanceId);

		Session session = null;

		try {
			session = openSession();

			KaleoInstance[] array = new KaleoInstanceImpl[3];

			array[0] = getByCompanyId_PrevAndNext(
				session, kaleoInstance, companyId, orderByComparator, true);

			array[1] = kaleoInstance;

			array[2] = getByCompanyId_PrevAndNext(
				session, kaleoInstance, companyId, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected KaleoInstance getByCompanyId_PrevAndNext(
		Session session, KaleoInstance kaleoInstance, long companyId,
		OrderByComparator<KaleoInstance> orderByComparator, boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(3);
		}

		sb.append(_SQL_SELECT_KALEOINSTANCE_WHERE);

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
			sb.append(KaleoInstanceModelImpl.ORDER_BY_JPQL);
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
						kaleoInstance)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<KaleoInstance> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the kaleo instances where companyId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 */
	@Override
	public void removeByCompanyId(long companyId) {
		for (KaleoInstance kaleoInstance :
				findByCompanyId(
					companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(kaleoInstance);
		}
	}

	/**
	 * Returns the number of kaleo instances where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching kaleo instances
	 */
	@Override
	public int countByCompanyId(long companyId) {
		FinderPath finderPath = _finderPathCountByCompanyId;

		Object[] finderArgs = new Object[] {companyId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_KALEOINSTANCE_WHERE);

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
		"kaleoInstance.companyId = ?";

	private FinderPath _finderPathWithPaginationFindByKaleoDefinitionVersionId;
	private FinderPath
		_finderPathWithoutPaginationFindByKaleoDefinitionVersionId;
	private FinderPath _finderPathCountByKaleoDefinitionVersionId;

	/**
	 * Returns all the kaleo instances where kaleoDefinitionVersionId = &#63;.
	 *
	 * @param kaleoDefinitionVersionId the kaleo definition version ID
	 * @return the matching kaleo instances
	 */
	@Override
	public List<KaleoInstance> findByKaleoDefinitionVersionId(
		long kaleoDefinitionVersionId) {

		return findByKaleoDefinitionVersionId(
			kaleoDefinitionVersionId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the kaleo instances where kaleoDefinitionVersionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoInstanceModelImpl</code>.
	 * </p>
	 *
	 * @param kaleoDefinitionVersionId the kaleo definition version ID
	 * @param start the lower bound of the range of kaleo instances
	 * @param end the upper bound of the range of kaleo instances (not inclusive)
	 * @return the range of matching kaleo instances
	 */
	@Override
	public List<KaleoInstance> findByKaleoDefinitionVersionId(
		long kaleoDefinitionVersionId, int start, int end) {

		return findByKaleoDefinitionVersionId(
			kaleoDefinitionVersionId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the kaleo instances where kaleoDefinitionVersionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoInstanceModelImpl</code>.
	 * </p>
	 *
	 * @param kaleoDefinitionVersionId the kaleo definition version ID
	 * @param start the lower bound of the range of kaleo instances
	 * @param end the upper bound of the range of kaleo instances (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching kaleo instances
	 */
	@Override
	public List<KaleoInstance> findByKaleoDefinitionVersionId(
		long kaleoDefinitionVersionId, int start, int end,
		OrderByComparator<KaleoInstance> orderByComparator) {

		return findByKaleoDefinitionVersionId(
			kaleoDefinitionVersionId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the kaleo instances where kaleoDefinitionVersionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoInstanceModelImpl</code>.
	 * </p>
	 *
	 * @param kaleoDefinitionVersionId the kaleo definition version ID
	 * @param start the lower bound of the range of kaleo instances
	 * @param end the upper bound of the range of kaleo instances (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching kaleo instances
	 */
	@Override
	public List<KaleoInstance> findByKaleoDefinitionVersionId(
		long kaleoDefinitionVersionId, int start, int end,
		OrderByComparator<KaleoInstance> orderByComparator,
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

		List<KaleoInstance> list = null;

		if (useFinderCache) {
			list = (List<KaleoInstance>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (KaleoInstance kaleoInstance : list) {
					if (kaleoDefinitionVersionId !=
							kaleoInstance.getKaleoDefinitionVersionId()) {

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

			sb.append(_SQL_SELECT_KALEOINSTANCE_WHERE);

			sb.append(
				_FINDER_COLUMN_KALEODEFINITIONVERSIONID_KALEODEFINITIONVERSIONID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(KaleoInstanceModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(kaleoDefinitionVersionId);

				list = (List<KaleoInstance>)QueryUtil.list(
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
	 * Returns the first kaleo instance in the ordered set where kaleoDefinitionVersionId = &#63;.
	 *
	 * @param kaleoDefinitionVersionId the kaleo definition version ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching kaleo instance
	 * @throws NoSuchInstanceException if a matching kaleo instance could not be found
	 */
	@Override
	public KaleoInstance findByKaleoDefinitionVersionId_First(
			long kaleoDefinitionVersionId,
			OrderByComparator<KaleoInstance> orderByComparator)
		throws NoSuchInstanceException {

		KaleoInstance kaleoInstance = fetchByKaleoDefinitionVersionId_First(
			kaleoDefinitionVersionId, orderByComparator);

		if (kaleoInstance != null) {
			return kaleoInstance;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("kaleoDefinitionVersionId=");
		sb.append(kaleoDefinitionVersionId);

		sb.append("}");

		throw new NoSuchInstanceException(sb.toString());
	}

	/**
	 * Returns the first kaleo instance in the ordered set where kaleoDefinitionVersionId = &#63;.
	 *
	 * @param kaleoDefinitionVersionId the kaleo definition version ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching kaleo instance, or <code>null</code> if a matching kaleo instance could not be found
	 */
	@Override
	public KaleoInstance fetchByKaleoDefinitionVersionId_First(
		long kaleoDefinitionVersionId,
		OrderByComparator<KaleoInstance> orderByComparator) {

		List<KaleoInstance> list = findByKaleoDefinitionVersionId(
			kaleoDefinitionVersionId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last kaleo instance in the ordered set where kaleoDefinitionVersionId = &#63;.
	 *
	 * @param kaleoDefinitionVersionId the kaleo definition version ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching kaleo instance
	 * @throws NoSuchInstanceException if a matching kaleo instance could not be found
	 */
	@Override
	public KaleoInstance findByKaleoDefinitionVersionId_Last(
			long kaleoDefinitionVersionId,
			OrderByComparator<KaleoInstance> orderByComparator)
		throws NoSuchInstanceException {

		KaleoInstance kaleoInstance = fetchByKaleoDefinitionVersionId_Last(
			kaleoDefinitionVersionId, orderByComparator);

		if (kaleoInstance != null) {
			return kaleoInstance;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("kaleoDefinitionVersionId=");
		sb.append(kaleoDefinitionVersionId);

		sb.append("}");

		throw new NoSuchInstanceException(sb.toString());
	}

	/**
	 * Returns the last kaleo instance in the ordered set where kaleoDefinitionVersionId = &#63;.
	 *
	 * @param kaleoDefinitionVersionId the kaleo definition version ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching kaleo instance, or <code>null</code> if a matching kaleo instance could not be found
	 */
	@Override
	public KaleoInstance fetchByKaleoDefinitionVersionId_Last(
		long kaleoDefinitionVersionId,
		OrderByComparator<KaleoInstance> orderByComparator) {

		int count = countByKaleoDefinitionVersionId(kaleoDefinitionVersionId);

		if (count == 0) {
			return null;
		}

		List<KaleoInstance> list = findByKaleoDefinitionVersionId(
			kaleoDefinitionVersionId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the kaleo instances before and after the current kaleo instance in the ordered set where kaleoDefinitionVersionId = &#63;.
	 *
	 * @param kaleoInstanceId the primary key of the current kaleo instance
	 * @param kaleoDefinitionVersionId the kaleo definition version ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next kaleo instance
	 * @throws NoSuchInstanceException if a kaleo instance with the primary key could not be found
	 */
	@Override
	public KaleoInstance[] findByKaleoDefinitionVersionId_PrevAndNext(
			long kaleoInstanceId, long kaleoDefinitionVersionId,
			OrderByComparator<KaleoInstance> orderByComparator)
		throws NoSuchInstanceException {

		KaleoInstance kaleoInstance = findByPrimaryKey(kaleoInstanceId);

		Session session = null;

		try {
			session = openSession();

			KaleoInstance[] array = new KaleoInstanceImpl[3];

			array[0] = getByKaleoDefinitionVersionId_PrevAndNext(
				session, kaleoInstance, kaleoDefinitionVersionId,
				orderByComparator, true);

			array[1] = kaleoInstance;

			array[2] = getByKaleoDefinitionVersionId_PrevAndNext(
				session, kaleoInstance, kaleoDefinitionVersionId,
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

	protected KaleoInstance getByKaleoDefinitionVersionId_PrevAndNext(
		Session session, KaleoInstance kaleoInstance,
		long kaleoDefinitionVersionId,
		OrderByComparator<KaleoInstance> orderByComparator, boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(3);
		}

		sb.append(_SQL_SELECT_KALEOINSTANCE_WHERE);

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
			sb.append(KaleoInstanceModelImpl.ORDER_BY_JPQL);
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
						kaleoInstance)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<KaleoInstance> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the kaleo instances where kaleoDefinitionVersionId = &#63; from the database.
	 *
	 * @param kaleoDefinitionVersionId the kaleo definition version ID
	 */
	@Override
	public void removeByKaleoDefinitionVersionId(
		long kaleoDefinitionVersionId) {

		for (KaleoInstance kaleoInstance :
				findByKaleoDefinitionVersionId(
					kaleoDefinitionVersionId, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			remove(kaleoInstance);
		}
	}

	/**
	 * Returns the number of kaleo instances where kaleoDefinitionVersionId = &#63;.
	 *
	 * @param kaleoDefinitionVersionId the kaleo definition version ID
	 * @return the number of matching kaleo instances
	 */
	@Override
	public int countByKaleoDefinitionVersionId(long kaleoDefinitionVersionId) {
		FinderPath finderPath = _finderPathCountByKaleoDefinitionVersionId;

		Object[] finderArgs = new Object[] {kaleoDefinitionVersionId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_KALEOINSTANCE_WHERE);

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
			"kaleoInstance.kaleoDefinitionVersionId = ?";

	private FinderPath _finderPathWithPaginationFindByC_U;
	private FinderPath _finderPathWithoutPaginationFindByC_U;
	private FinderPath _finderPathCountByC_U;

	/**
	 * Returns all the kaleo instances where companyId = &#63; and userId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param userId the user ID
	 * @return the matching kaleo instances
	 */
	@Override
	public List<KaleoInstance> findByC_U(long companyId, long userId) {
		return findByC_U(
			companyId, userId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the kaleo instances where companyId = &#63; and userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoInstanceModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param userId the user ID
	 * @param start the lower bound of the range of kaleo instances
	 * @param end the upper bound of the range of kaleo instances (not inclusive)
	 * @return the range of matching kaleo instances
	 */
	@Override
	public List<KaleoInstance> findByC_U(
		long companyId, long userId, int start, int end) {

		return findByC_U(companyId, userId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the kaleo instances where companyId = &#63; and userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoInstanceModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param userId the user ID
	 * @param start the lower bound of the range of kaleo instances
	 * @param end the upper bound of the range of kaleo instances (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching kaleo instances
	 */
	@Override
	public List<KaleoInstance> findByC_U(
		long companyId, long userId, int start, int end,
		OrderByComparator<KaleoInstance> orderByComparator) {

		return findByC_U(
			companyId, userId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the kaleo instances where companyId = &#63; and userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoInstanceModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param userId the user ID
	 * @param start the lower bound of the range of kaleo instances
	 * @param end the upper bound of the range of kaleo instances (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching kaleo instances
	 */
	@Override
	public List<KaleoInstance> findByC_U(
		long companyId, long userId, int start, int end,
		OrderByComparator<KaleoInstance> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByC_U;
				finderArgs = new Object[] {companyId, userId};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByC_U;
			finderArgs = new Object[] {
				companyId, userId, start, end, orderByComparator
			};
		}

		List<KaleoInstance> list = null;

		if (useFinderCache) {
			list = (List<KaleoInstance>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (KaleoInstance kaleoInstance : list) {
					if ((companyId != kaleoInstance.getCompanyId()) ||
						(userId != kaleoInstance.getUserId())) {

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

			sb.append(_SQL_SELECT_KALEOINSTANCE_WHERE);

			sb.append(_FINDER_COLUMN_C_U_COMPANYID_2);

			sb.append(_FINDER_COLUMN_C_U_USERID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(KaleoInstanceModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(companyId);

				queryPos.add(userId);

				list = (List<KaleoInstance>)QueryUtil.list(
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
	 * Returns the first kaleo instance in the ordered set where companyId = &#63; and userId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching kaleo instance
	 * @throws NoSuchInstanceException if a matching kaleo instance could not be found
	 */
	@Override
	public KaleoInstance findByC_U_First(
			long companyId, long userId,
			OrderByComparator<KaleoInstance> orderByComparator)
		throws NoSuchInstanceException {

		KaleoInstance kaleoInstance = fetchByC_U_First(
			companyId, userId, orderByComparator);

		if (kaleoInstance != null) {
			return kaleoInstance;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("companyId=");
		sb.append(companyId);

		sb.append(", userId=");
		sb.append(userId);

		sb.append("}");

		throw new NoSuchInstanceException(sb.toString());
	}

	/**
	 * Returns the first kaleo instance in the ordered set where companyId = &#63; and userId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching kaleo instance, or <code>null</code> if a matching kaleo instance could not be found
	 */
	@Override
	public KaleoInstance fetchByC_U_First(
		long companyId, long userId,
		OrderByComparator<KaleoInstance> orderByComparator) {

		List<KaleoInstance> list = findByC_U(
			companyId, userId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last kaleo instance in the ordered set where companyId = &#63; and userId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching kaleo instance
	 * @throws NoSuchInstanceException if a matching kaleo instance could not be found
	 */
	@Override
	public KaleoInstance findByC_U_Last(
			long companyId, long userId,
			OrderByComparator<KaleoInstance> orderByComparator)
		throws NoSuchInstanceException {

		KaleoInstance kaleoInstance = fetchByC_U_Last(
			companyId, userId, orderByComparator);

		if (kaleoInstance != null) {
			return kaleoInstance;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("companyId=");
		sb.append(companyId);

		sb.append(", userId=");
		sb.append(userId);

		sb.append("}");

		throw new NoSuchInstanceException(sb.toString());
	}

	/**
	 * Returns the last kaleo instance in the ordered set where companyId = &#63; and userId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching kaleo instance, or <code>null</code> if a matching kaleo instance could not be found
	 */
	@Override
	public KaleoInstance fetchByC_U_Last(
		long companyId, long userId,
		OrderByComparator<KaleoInstance> orderByComparator) {

		int count = countByC_U(companyId, userId);

		if (count == 0) {
			return null;
		}

		List<KaleoInstance> list = findByC_U(
			companyId, userId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the kaleo instances before and after the current kaleo instance in the ordered set where companyId = &#63; and userId = &#63;.
	 *
	 * @param kaleoInstanceId the primary key of the current kaleo instance
	 * @param companyId the company ID
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next kaleo instance
	 * @throws NoSuchInstanceException if a kaleo instance with the primary key could not be found
	 */
	@Override
	public KaleoInstance[] findByC_U_PrevAndNext(
			long kaleoInstanceId, long companyId, long userId,
			OrderByComparator<KaleoInstance> orderByComparator)
		throws NoSuchInstanceException {

		KaleoInstance kaleoInstance = findByPrimaryKey(kaleoInstanceId);

		Session session = null;

		try {
			session = openSession();

			KaleoInstance[] array = new KaleoInstanceImpl[3];

			array[0] = getByC_U_PrevAndNext(
				session, kaleoInstance, companyId, userId, orderByComparator,
				true);

			array[1] = kaleoInstance;

			array[2] = getByC_U_PrevAndNext(
				session, kaleoInstance, companyId, userId, orderByComparator,
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

	protected KaleoInstance getByC_U_PrevAndNext(
		Session session, KaleoInstance kaleoInstance, long companyId,
		long userId, OrderByComparator<KaleoInstance> orderByComparator,
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

		sb.append(_SQL_SELECT_KALEOINSTANCE_WHERE);

		sb.append(_FINDER_COLUMN_C_U_COMPANYID_2);

		sb.append(_FINDER_COLUMN_C_U_USERID_2);

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
			sb.append(KaleoInstanceModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(companyId);

		queryPos.add(userId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						kaleoInstance)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<KaleoInstance> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the kaleo instances where companyId = &#63; and userId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param userId the user ID
	 */
	@Override
	public void removeByC_U(long companyId, long userId) {
		for (KaleoInstance kaleoInstance :
				findByC_U(
					companyId, userId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(kaleoInstance);
		}
	}

	/**
	 * Returns the number of kaleo instances where companyId = &#63; and userId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param userId the user ID
	 * @return the number of matching kaleo instances
	 */
	@Override
	public int countByC_U(long companyId, long userId) {
		FinderPath finderPath = _finderPathCountByC_U;

		Object[] finderArgs = new Object[] {companyId, userId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_KALEOINSTANCE_WHERE);

			sb.append(_FINDER_COLUMN_C_U_COMPANYID_2);

			sb.append(_FINDER_COLUMN_C_U_USERID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(companyId);

				queryPos.add(userId);

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

	private static final String _FINDER_COLUMN_C_U_COMPANYID_2 =
		"kaleoInstance.companyId = ? AND ";

	private static final String _FINDER_COLUMN_C_U_USERID_2 =
		"kaleoInstance.userId = ?";

	private FinderPath _finderPathWithPaginationFindByKDI_C;
	private FinderPath _finderPathWithoutPaginationFindByKDI_C;
	private FinderPath _finderPathCountByKDI_C;

	/**
	 * Returns all the kaleo instances where kaleoDefinitionId = &#63; and completed = &#63;.
	 *
	 * @param kaleoDefinitionId the kaleo definition ID
	 * @param completed the completed
	 * @return the matching kaleo instances
	 */
	@Override
	public List<KaleoInstance> findByKDI_C(
		long kaleoDefinitionId, boolean completed) {

		return findByKDI_C(
			kaleoDefinitionId, completed, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the kaleo instances where kaleoDefinitionId = &#63; and completed = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoInstanceModelImpl</code>.
	 * </p>
	 *
	 * @param kaleoDefinitionId the kaleo definition ID
	 * @param completed the completed
	 * @param start the lower bound of the range of kaleo instances
	 * @param end the upper bound of the range of kaleo instances (not inclusive)
	 * @return the range of matching kaleo instances
	 */
	@Override
	public List<KaleoInstance> findByKDI_C(
		long kaleoDefinitionId, boolean completed, int start, int end) {

		return findByKDI_C(kaleoDefinitionId, completed, start, end, null);
	}

	/**
	 * Returns an ordered range of all the kaleo instances where kaleoDefinitionId = &#63; and completed = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoInstanceModelImpl</code>.
	 * </p>
	 *
	 * @param kaleoDefinitionId the kaleo definition ID
	 * @param completed the completed
	 * @param start the lower bound of the range of kaleo instances
	 * @param end the upper bound of the range of kaleo instances (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching kaleo instances
	 */
	@Override
	public List<KaleoInstance> findByKDI_C(
		long kaleoDefinitionId, boolean completed, int start, int end,
		OrderByComparator<KaleoInstance> orderByComparator) {

		return findByKDI_C(
			kaleoDefinitionId, completed, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the kaleo instances where kaleoDefinitionId = &#63; and completed = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoInstanceModelImpl</code>.
	 * </p>
	 *
	 * @param kaleoDefinitionId the kaleo definition ID
	 * @param completed the completed
	 * @param start the lower bound of the range of kaleo instances
	 * @param end the upper bound of the range of kaleo instances (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching kaleo instances
	 */
	@Override
	public List<KaleoInstance> findByKDI_C(
		long kaleoDefinitionId, boolean completed, int start, int end,
		OrderByComparator<KaleoInstance> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByKDI_C;
				finderArgs = new Object[] {kaleoDefinitionId, completed};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByKDI_C;
			finderArgs = new Object[] {
				kaleoDefinitionId, completed, start, end, orderByComparator
			};
		}

		List<KaleoInstance> list = null;

		if (useFinderCache) {
			list = (List<KaleoInstance>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (KaleoInstance kaleoInstance : list) {
					if ((kaleoDefinitionId !=
							kaleoInstance.getKaleoDefinitionId()) ||
						(completed != kaleoInstance.isCompleted())) {

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

			sb.append(_SQL_SELECT_KALEOINSTANCE_WHERE);

			sb.append(_FINDER_COLUMN_KDI_C_KALEODEFINITIONID_2);

			sb.append(_FINDER_COLUMN_KDI_C_COMPLETED_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(KaleoInstanceModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(kaleoDefinitionId);

				queryPos.add(completed);

				list = (List<KaleoInstance>)QueryUtil.list(
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
	 * Returns the first kaleo instance in the ordered set where kaleoDefinitionId = &#63; and completed = &#63;.
	 *
	 * @param kaleoDefinitionId the kaleo definition ID
	 * @param completed the completed
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching kaleo instance
	 * @throws NoSuchInstanceException if a matching kaleo instance could not be found
	 */
	@Override
	public KaleoInstance findByKDI_C_First(
			long kaleoDefinitionId, boolean completed,
			OrderByComparator<KaleoInstance> orderByComparator)
		throws NoSuchInstanceException {

		KaleoInstance kaleoInstance = fetchByKDI_C_First(
			kaleoDefinitionId, completed, orderByComparator);

		if (kaleoInstance != null) {
			return kaleoInstance;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("kaleoDefinitionId=");
		sb.append(kaleoDefinitionId);

		sb.append(", completed=");
		sb.append(completed);

		sb.append("}");

		throw new NoSuchInstanceException(sb.toString());
	}

	/**
	 * Returns the first kaleo instance in the ordered set where kaleoDefinitionId = &#63; and completed = &#63;.
	 *
	 * @param kaleoDefinitionId the kaleo definition ID
	 * @param completed the completed
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching kaleo instance, or <code>null</code> if a matching kaleo instance could not be found
	 */
	@Override
	public KaleoInstance fetchByKDI_C_First(
		long kaleoDefinitionId, boolean completed,
		OrderByComparator<KaleoInstance> orderByComparator) {

		List<KaleoInstance> list = findByKDI_C(
			kaleoDefinitionId, completed, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last kaleo instance in the ordered set where kaleoDefinitionId = &#63; and completed = &#63;.
	 *
	 * @param kaleoDefinitionId the kaleo definition ID
	 * @param completed the completed
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching kaleo instance
	 * @throws NoSuchInstanceException if a matching kaleo instance could not be found
	 */
	@Override
	public KaleoInstance findByKDI_C_Last(
			long kaleoDefinitionId, boolean completed,
			OrderByComparator<KaleoInstance> orderByComparator)
		throws NoSuchInstanceException {

		KaleoInstance kaleoInstance = fetchByKDI_C_Last(
			kaleoDefinitionId, completed, orderByComparator);

		if (kaleoInstance != null) {
			return kaleoInstance;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("kaleoDefinitionId=");
		sb.append(kaleoDefinitionId);

		sb.append(", completed=");
		sb.append(completed);

		sb.append("}");

		throw new NoSuchInstanceException(sb.toString());
	}

	/**
	 * Returns the last kaleo instance in the ordered set where kaleoDefinitionId = &#63; and completed = &#63;.
	 *
	 * @param kaleoDefinitionId the kaleo definition ID
	 * @param completed the completed
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching kaleo instance, or <code>null</code> if a matching kaleo instance could not be found
	 */
	@Override
	public KaleoInstance fetchByKDI_C_Last(
		long kaleoDefinitionId, boolean completed,
		OrderByComparator<KaleoInstance> orderByComparator) {

		int count = countByKDI_C(kaleoDefinitionId, completed);

		if (count == 0) {
			return null;
		}

		List<KaleoInstance> list = findByKDI_C(
			kaleoDefinitionId, completed, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the kaleo instances before and after the current kaleo instance in the ordered set where kaleoDefinitionId = &#63; and completed = &#63;.
	 *
	 * @param kaleoInstanceId the primary key of the current kaleo instance
	 * @param kaleoDefinitionId the kaleo definition ID
	 * @param completed the completed
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next kaleo instance
	 * @throws NoSuchInstanceException if a kaleo instance with the primary key could not be found
	 */
	@Override
	public KaleoInstance[] findByKDI_C_PrevAndNext(
			long kaleoInstanceId, long kaleoDefinitionId, boolean completed,
			OrderByComparator<KaleoInstance> orderByComparator)
		throws NoSuchInstanceException {

		KaleoInstance kaleoInstance = findByPrimaryKey(kaleoInstanceId);

		Session session = null;

		try {
			session = openSession();

			KaleoInstance[] array = new KaleoInstanceImpl[3];

			array[0] = getByKDI_C_PrevAndNext(
				session, kaleoInstance, kaleoDefinitionId, completed,
				orderByComparator, true);

			array[1] = kaleoInstance;

			array[2] = getByKDI_C_PrevAndNext(
				session, kaleoInstance, kaleoDefinitionId, completed,
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

	protected KaleoInstance getByKDI_C_PrevAndNext(
		Session session, KaleoInstance kaleoInstance, long kaleoDefinitionId,
		boolean completed, OrderByComparator<KaleoInstance> orderByComparator,
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

		sb.append(_SQL_SELECT_KALEOINSTANCE_WHERE);

		sb.append(_FINDER_COLUMN_KDI_C_KALEODEFINITIONID_2);

		sb.append(_FINDER_COLUMN_KDI_C_COMPLETED_2);

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
			sb.append(KaleoInstanceModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(kaleoDefinitionId);

		queryPos.add(completed);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						kaleoInstance)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<KaleoInstance> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the kaleo instances where kaleoDefinitionId = &#63; and completed = &#63; from the database.
	 *
	 * @param kaleoDefinitionId the kaleo definition ID
	 * @param completed the completed
	 */
	@Override
	public void removeByKDI_C(long kaleoDefinitionId, boolean completed) {
		for (KaleoInstance kaleoInstance :
				findByKDI_C(
					kaleoDefinitionId, completed, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			remove(kaleoInstance);
		}
	}

	/**
	 * Returns the number of kaleo instances where kaleoDefinitionId = &#63; and completed = &#63;.
	 *
	 * @param kaleoDefinitionId the kaleo definition ID
	 * @param completed the completed
	 * @return the number of matching kaleo instances
	 */
	@Override
	public int countByKDI_C(long kaleoDefinitionId, boolean completed) {
		FinderPath finderPath = _finderPathCountByKDI_C;

		Object[] finderArgs = new Object[] {kaleoDefinitionId, completed};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_KALEOINSTANCE_WHERE);

			sb.append(_FINDER_COLUMN_KDI_C_KALEODEFINITIONID_2);

			sb.append(_FINDER_COLUMN_KDI_C_COMPLETED_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(kaleoDefinitionId);

				queryPos.add(completed);

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

	private static final String _FINDER_COLUMN_KDI_C_KALEODEFINITIONID_2 =
		"kaleoInstance.kaleoDefinitionId = ? AND ";

	private static final String _FINDER_COLUMN_KDI_C_COMPLETED_2 =
		"kaleoInstance.completed = ?";

	private FinderPath _finderPathWithPaginationFindByKDVI_C;
	private FinderPath _finderPathWithoutPaginationFindByKDVI_C;
	private FinderPath _finderPathCountByKDVI_C;

	/**
	 * Returns all the kaleo instances where kaleoDefinitionVersionId = &#63; and completed = &#63;.
	 *
	 * @param kaleoDefinitionVersionId the kaleo definition version ID
	 * @param completed the completed
	 * @return the matching kaleo instances
	 */
	@Override
	public List<KaleoInstance> findByKDVI_C(
		long kaleoDefinitionVersionId, boolean completed) {

		return findByKDVI_C(
			kaleoDefinitionVersionId, completed, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the kaleo instances where kaleoDefinitionVersionId = &#63; and completed = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoInstanceModelImpl</code>.
	 * </p>
	 *
	 * @param kaleoDefinitionVersionId the kaleo definition version ID
	 * @param completed the completed
	 * @param start the lower bound of the range of kaleo instances
	 * @param end the upper bound of the range of kaleo instances (not inclusive)
	 * @return the range of matching kaleo instances
	 */
	@Override
	public List<KaleoInstance> findByKDVI_C(
		long kaleoDefinitionVersionId, boolean completed, int start, int end) {

		return findByKDVI_C(
			kaleoDefinitionVersionId, completed, start, end, null);
	}

	/**
	 * Returns an ordered range of all the kaleo instances where kaleoDefinitionVersionId = &#63; and completed = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoInstanceModelImpl</code>.
	 * </p>
	 *
	 * @param kaleoDefinitionVersionId the kaleo definition version ID
	 * @param completed the completed
	 * @param start the lower bound of the range of kaleo instances
	 * @param end the upper bound of the range of kaleo instances (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching kaleo instances
	 */
	@Override
	public List<KaleoInstance> findByKDVI_C(
		long kaleoDefinitionVersionId, boolean completed, int start, int end,
		OrderByComparator<KaleoInstance> orderByComparator) {

		return findByKDVI_C(
			kaleoDefinitionVersionId, completed, start, end, orderByComparator,
			true);
	}

	/**
	 * Returns an ordered range of all the kaleo instances where kaleoDefinitionVersionId = &#63; and completed = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoInstanceModelImpl</code>.
	 * </p>
	 *
	 * @param kaleoDefinitionVersionId the kaleo definition version ID
	 * @param completed the completed
	 * @param start the lower bound of the range of kaleo instances
	 * @param end the upper bound of the range of kaleo instances (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching kaleo instances
	 */
	@Override
	public List<KaleoInstance> findByKDVI_C(
		long kaleoDefinitionVersionId, boolean completed, int start, int end,
		OrderByComparator<KaleoInstance> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByKDVI_C;
				finderArgs = new Object[] {kaleoDefinitionVersionId, completed};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByKDVI_C;
			finderArgs = new Object[] {
				kaleoDefinitionVersionId, completed, start, end,
				orderByComparator
			};
		}

		List<KaleoInstance> list = null;

		if (useFinderCache) {
			list = (List<KaleoInstance>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (KaleoInstance kaleoInstance : list) {
					if ((kaleoDefinitionVersionId !=
							kaleoInstance.getKaleoDefinitionVersionId()) ||
						(completed != kaleoInstance.isCompleted())) {

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

			sb.append(_SQL_SELECT_KALEOINSTANCE_WHERE);

			sb.append(_FINDER_COLUMN_KDVI_C_KALEODEFINITIONVERSIONID_2);

			sb.append(_FINDER_COLUMN_KDVI_C_COMPLETED_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(KaleoInstanceModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(kaleoDefinitionVersionId);

				queryPos.add(completed);

				list = (List<KaleoInstance>)QueryUtil.list(
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
	 * Returns the first kaleo instance in the ordered set where kaleoDefinitionVersionId = &#63; and completed = &#63;.
	 *
	 * @param kaleoDefinitionVersionId the kaleo definition version ID
	 * @param completed the completed
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching kaleo instance
	 * @throws NoSuchInstanceException if a matching kaleo instance could not be found
	 */
	@Override
	public KaleoInstance findByKDVI_C_First(
			long kaleoDefinitionVersionId, boolean completed,
			OrderByComparator<KaleoInstance> orderByComparator)
		throws NoSuchInstanceException {

		KaleoInstance kaleoInstance = fetchByKDVI_C_First(
			kaleoDefinitionVersionId, completed, orderByComparator);

		if (kaleoInstance != null) {
			return kaleoInstance;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("kaleoDefinitionVersionId=");
		sb.append(kaleoDefinitionVersionId);

		sb.append(", completed=");
		sb.append(completed);

		sb.append("}");

		throw new NoSuchInstanceException(sb.toString());
	}

	/**
	 * Returns the first kaleo instance in the ordered set where kaleoDefinitionVersionId = &#63; and completed = &#63;.
	 *
	 * @param kaleoDefinitionVersionId the kaleo definition version ID
	 * @param completed the completed
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching kaleo instance, or <code>null</code> if a matching kaleo instance could not be found
	 */
	@Override
	public KaleoInstance fetchByKDVI_C_First(
		long kaleoDefinitionVersionId, boolean completed,
		OrderByComparator<KaleoInstance> orderByComparator) {

		List<KaleoInstance> list = findByKDVI_C(
			kaleoDefinitionVersionId, completed, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last kaleo instance in the ordered set where kaleoDefinitionVersionId = &#63; and completed = &#63;.
	 *
	 * @param kaleoDefinitionVersionId the kaleo definition version ID
	 * @param completed the completed
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching kaleo instance
	 * @throws NoSuchInstanceException if a matching kaleo instance could not be found
	 */
	@Override
	public KaleoInstance findByKDVI_C_Last(
			long kaleoDefinitionVersionId, boolean completed,
			OrderByComparator<KaleoInstance> orderByComparator)
		throws NoSuchInstanceException {

		KaleoInstance kaleoInstance = fetchByKDVI_C_Last(
			kaleoDefinitionVersionId, completed, orderByComparator);

		if (kaleoInstance != null) {
			return kaleoInstance;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("kaleoDefinitionVersionId=");
		sb.append(kaleoDefinitionVersionId);

		sb.append(", completed=");
		sb.append(completed);

		sb.append("}");

		throw new NoSuchInstanceException(sb.toString());
	}

	/**
	 * Returns the last kaleo instance in the ordered set where kaleoDefinitionVersionId = &#63; and completed = &#63;.
	 *
	 * @param kaleoDefinitionVersionId the kaleo definition version ID
	 * @param completed the completed
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching kaleo instance, or <code>null</code> if a matching kaleo instance could not be found
	 */
	@Override
	public KaleoInstance fetchByKDVI_C_Last(
		long kaleoDefinitionVersionId, boolean completed,
		OrderByComparator<KaleoInstance> orderByComparator) {

		int count = countByKDVI_C(kaleoDefinitionVersionId, completed);

		if (count == 0) {
			return null;
		}

		List<KaleoInstance> list = findByKDVI_C(
			kaleoDefinitionVersionId, completed, count - 1, count,
			orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the kaleo instances before and after the current kaleo instance in the ordered set where kaleoDefinitionVersionId = &#63; and completed = &#63;.
	 *
	 * @param kaleoInstanceId the primary key of the current kaleo instance
	 * @param kaleoDefinitionVersionId the kaleo definition version ID
	 * @param completed the completed
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next kaleo instance
	 * @throws NoSuchInstanceException if a kaleo instance with the primary key could not be found
	 */
	@Override
	public KaleoInstance[] findByKDVI_C_PrevAndNext(
			long kaleoInstanceId, long kaleoDefinitionVersionId,
			boolean completed,
			OrderByComparator<KaleoInstance> orderByComparator)
		throws NoSuchInstanceException {

		KaleoInstance kaleoInstance = findByPrimaryKey(kaleoInstanceId);

		Session session = null;

		try {
			session = openSession();

			KaleoInstance[] array = new KaleoInstanceImpl[3];

			array[0] = getByKDVI_C_PrevAndNext(
				session, kaleoInstance, kaleoDefinitionVersionId, completed,
				orderByComparator, true);

			array[1] = kaleoInstance;

			array[2] = getByKDVI_C_PrevAndNext(
				session, kaleoInstance, kaleoDefinitionVersionId, completed,
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

	protected KaleoInstance getByKDVI_C_PrevAndNext(
		Session session, KaleoInstance kaleoInstance,
		long kaleoDefinitionVersionId, boolean completed,
		OrderByComparator<KaleoInstance> orderByComparator, boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				5 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(4);
		}

		sb.append(_SQL_SELECT_KALEOINSTANCE_WHERE);

		sb.append(_FINDER_COLUMN_KDVI_C_KALEODEFINITIONVERSIONID_2);

		sb.append(_FINDER_COLUMN_KDVI_C_COMPLETED_2);

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
			sb.append(KaleoInstanceModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(kaleoDefinitionVersionId);

		queryPos.add(completed);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						kaleoInstance)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<KaleoInstance> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the kaleo instances where kaleoDefinitionVersionId = &#63; and completed = &#63; from the database.
	 *
	 * @param kaleoDefinitionVersionId the kaleo definition version ID
	 * @param completed the completed
	 */
	@Override
	public void removeByKDVI_C(
		long kaleoDefinitionVersionId, boolean completed) {

		for (KaleoInstance kaleoInstance :
				findByKDVI_C(
					kaleoDefinitionVersionId, completed, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			remove(kaleoInstance);
		}
	}

	/**
	 * Returns the number of kaleo instances where kaleoDefinitionVersionId = &#63; and completed = &#63;.
	 *
	 * @param kaleoDefinitionVersionId the kaleo definition version ID
	 * @param completed the completed
	 * @return the number of matching kaleo instances
	 */
	@Override
	public int countByKDVI_C(long kaleoDefinitionVersionId, boolean completed) {
		FinderPath finderPath = _finderPathCountByKDVI_C;

		Object[] finderArgs = new Object[] {
			kaleoDefinitionVersionId, completed
		};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_KALEOINSTANCE_WHERE);

			sb.append(_FINDER_COLUMN_KDVI_C_KALEODEFINITIONVERSIONID_2);

			sb.append(_FINDER_COLUMN_KDVI_C_COMPLETED_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(kaleoDefinitionVersionId);

				queryPos.add(completed);

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
		_FINDER_COLUMN_KDVI_C_KALEODEFINITIONVERSIONID_2 =
			"kaleoInstance.kaleoDefinitionVersionId = ? AND ";

	private static final String _FINDER_COLUMN_KDVI_C_COMPLETED_2 =
		"kaleoInstance.completed = ?";

	private FinderPath _finderPathWithPaginationFindByCN_CPK;
	private FinderPath _finderPathWithoutPaginationFindByCN_CPK;
	private FinderPath _finderPathCountByCN_CPK;

	/**
	 * Returns all the kaleo instances where className = &#63; and classPK = &#63;.
	 *
	 * @param className the class name
	 * @param classPK the class pk
	 * @return the matching kaleo instances
	 */
	@Override
	public List<KaleoInstance> findByCN_CPK(String className, long classPK) {
		return findByCN_CPK(
			className, classPK, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the kaleo instances where className = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoInstanceModelImpl</code>.
	 * </p>
	 *
	 * @param className the class name
	 * @param classPK the class pk
	 * @param start the lower bound of the range of kaleo instances
	 * @param end the upper bound of the range of kaleo instances (not inclusive)
	 * @return the range of matching kaleo instances
	 */
	@Override
	public List<KaleoInstance> findByCN_CPK(
		String className, long classPK, int start, int end) {

		return findByCN_CPK(className, classPK, start, end, null);
	}

	/**
	 * Returns an ordered range of all the kaleo instances where className = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoInstanceModelImpl</code>.
	 * </p>
	 *
	 * @param className the class name
	 * @param classPK the class pk
	 * @param start the lower bound of the range of kaleo instances
	 * @param end the upper bound of the range of kaleo instances (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching kaleo instances
	 */
	@Override
	public List<KaleoInstance> findByCN_CPK(
		String className, long classPK, int start, int end,
		OrderByComparator<KaleoInstance> orderByComparator) {

		return findByCN_CPK(
			className, classPK, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the kaleo instances where className = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoInstanceModelImpl</code>.
	 * </p>
	 *
	 * @param className the class name
	 * @param classPK the class pk
	 * @param start the lower bound of the range of kaleo instances
	 * @param end the upper bound of the range of kaleo instances (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching kaleo instances
	 */
	@Override
	public List<KaleoInstance> findByCN_CPK(
		String className, long classPK, int start, int end,
		OrderByComparator<KaleoInstance> orderByComparator,
		boolean useFinderCache) {

		className = Objects.toString(className, "");

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByCN_CPK;
				finderArgs = new Object[] {className, classPK};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByCN_CPK;
			finderArgs = new Object[] {
				className, classPK, start, end, orderByComparator
			};
		}

		List<KaleoInstance> list = null;

		if (useFinderCache) {
			list = (List<KaleoInstance>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (KaleoInstance kaleoInstance : list) {
					if (!className.equals(kaleoInstance.getClassName()) ||
						(classPK != kaleoInstance.getClassPK())) {

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

			sb.append(_SQL_SELECT_KALEOINSTANCE_WHERE);

			boolean bindClassName = false;

			if (className.isEmpty()) {
				sb.append(_FINDER_COLUMN_CN_CPK_CLASSNAME_3);
			}
			else {
				bindClassName = true;

				sb.append(_FINDER_COLUMN_CN_CPK_CLASSNAME_2);
			}

			sb.append(_FINDER_COLUMN_CN_CPK_CLASSPK_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(KaleoInstanceModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindClassName) {
					queryPos.add(className);
				}

				queryPos.add(classPK);

				list = (List<KaleoInstance>)QueryUtil.list(
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
	 * Returns the first kaleo instance in the ordered set where className = &#63; and classPK = &#63;.
	 *
	 * @param className the class name
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching kaleo instance
	 * @throws NoSuchInstanceException if a matching kaleo instance could not be found
	 */
	@Override
	public KaleoInstance findByCN_CPK_First(
			String className, long classPK,
			OrderByComparator<KaleoInstance> orderByComparator)
		throws NoSuchInstanceException {

		KaleoInstance kaleoInstance = fetchByCN_CPK_First(
			className, classPK, orderByComparator);

		if (kaleoInstance != null) {
			return kaleoInstance;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("className=");
		sb.append(className);

		sb.append(", classPK=");
		sb.append(classPK);

		sb.append("}");

		throw new NoSuchInstanceException(sb.toString());
	}

	/**
	 * Returns the first kaleo instance in the ordered set where className = &#63; and classPK = &#63;.
	 *
	 * @param className the class name
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching kaleo instance, or <code>null</code> if a matching kaleo instance could not be found
	 */
	@Override
	public KaleoInstance fetchByCN_CPK_First(
		String className, long classPK,
		OrderByComparator<KaleoInstance> orderByComparator) {

		List<KaleoInstance> list = findByCN_CPK(
			className, classPK, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last kaleo instance in the ordered set where className = &#63; and classPK = &#63;.
	 *
	 * @param className the class name
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching kaleo instance
	 * @throws NoSuchInstanceException if a matching kaleo instance could not be found
	 */
	@Override
	public KaleoInstance findByCN_CPK_Last(
			String className, long classPK,
			OrderByComparator<KaleoInstance> orderByComparator)
		throws NoSuchInstanceException {

		KaleoInstance kaleoInstance = fetchByCN_CPK_Last(
			className, classPK, orderByComparator);

		if (kaleoInstance != null) {
			return kaleoInstance;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("className=");
		sb.append(className);

		sb.append(", classPK=");
		sb.append(classPK);

		sb.append("}");

		throw new NoSuchInstanceException(sb.toString());
	}

	/**
	 * Returns the last kaleo instance in the ordered set where className = &#63; and classPK = &#63;.
	 *
	 * @param className the class name
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching kaleo instance, or <code>null</code> if a matching kaleo instance could not be found
	 */
	@Override
	public KaleoInstance fetchByCN_CPK_Last(
		String className, long classPK,
		OrderByComparator<KaleoInstance> orderByComparator) {

		int count = countByCN_CPK(className, classPK);

		if (count == 0) {
			return null;
		}

		List<KaleoInstance> list = findByCN_CPK(
			className, classPK, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the kaleo instances before and after the current kaleo instance in the ordered set where className = &#63; and classPK = &#63;.
	 *
	 * @param kaleoInstanceId the primary key of the current kaleo instance
	 * @param className the class name
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next kaleo instance
	 * @throws NoSuchInstanceException if a kaleo instance with the primary key could not be found
	 */
	@Override
	public KaleoInstance[] findByCN_CPK_PrevAndNext(
			long kaleoInstanceId, String className, long classPK,
			OrderByComparator<KaleoInstance> orderByComparator)
		throws NoSuchInstanceException {

		className = Objects.toString(className, "");

		KaleoInstance kaleoInstance = findByPrimaryKey(kaleoInstanceId);

		Session session = null;

		try {
			session = openSession();

			KaleoInstance[] array = new KaleoInstanceImpl[3];

			array[0] = getByCN_CPK_PrevAndNext(
				session, kaleoInstance, className, classPK, orderByComparator,
				true);

			array[1] = kaleoInstance;

			array[2] = getByCN_CPK_PrevAndNext(
				session, kaleoInstance, className, classPK, orderByComparator,
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

	protected KaleoInstance getByCN_CPK_PrevAndNext(
		Session session, KaleoInstance kaleoInstance, String className,
		long classPK, OrderByComparator<KaleoInstance> orderByComparator,
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

		sb.append(_SQL_SELECT_KALEOINSTANCE_WHERE);

		boolean bindClassName = false;

		if (className.isEmpty()) {
			sb.append(_FINDER_COLUMN_CN_CPK_CLASSNAME_3);
		}
		else {
			bindClassName = true;

			sb.append(_FINDER_COLUMN_CN_CPK_CLASSNAME_2);
		}

		sb.append(_FINDER_COLUMN_CN_CPK_CLASSPK_2);

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
			sb.append(KaleoInstanceModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		if (bindClassName) {
			queryPos.add(className);
		}

		queryPos.add(classPK);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						kaleoInstance)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<KaleoInstance> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the kaleo instances where className = &#63; and classPK = &#63; from the database.
	 *
	 * @param className the class name
	 * @param classPK the class pk
	 */
	@Override
	public void removeByCN_CPK(String className, long classPK) {
		for (KaleoInstance kaleoInstance :
				findByCN_CPK(
					className, classPK, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(kaleoInstance);
		}
	}

	/**
	 * Returns the number of kaleo instances where className = &#63; and classPK = &#63;.
	 *
	 * @param className the class name
	 * @param classPK the class pk
	 * @return the number of matching kaleo instances
	 */
	@Override
	public int countByCN_CPK(String className, long classPK) {
		className = Objects.toString(className, "");

		FinderPath finderPath = _finderPathCountByCN_CPK;

		Object[] finderArgs = new Object[] {className, classPK};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_KALEOINSTANCE_WHERE);

			boolean bindClassName = false;

			if (className.isEmpty()) {
				sb.append(_FINDER_COLUMN_CN_CPK_CLASSNAME_3);
			}
			else {
				bindClassName = true;

				sb.append(_FINDER_COLUMN_CN_CPK_CLASSNAME_2);
			}

			sb.append(_FINDER_COLUMN_CN_CPK_CLASSPK_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindClassName) {
					queryPos.add(className);
				}

				queryPos.add(classPK);

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

	private static final String _FINDER_COLUMN_CN_CPK_CLASSNAME_2 =
		"kaleoInstance.className = ? AND ";

	private static final String _FINDER_COLUMN_CN_CPK_CLASSNAME_3 =
		"(kaleoInstance.className IS NULL OR kaleoInstance.className = '') AND ";

	private static final String _FINDER_COLUMN_CN_CPK_CLASSPK_2 =
		"kaleoInstance.classPK = ?";

	private FinderPath _finderPathFetchByKII_C_U;
	private FinderPath _finderPathCountByKII_C_U;

	/**
	 * Returns the kaleo instance where kaleoInstanceId = &#63; and companyId = &#63; and userId = &#63; or throws a <code>NoSuchInstanceException</code> if it could not be found.
	 *
	 * @param kaleoInstanceId the kaleo instance ID
	 * @param companyId the company ID
	 * @param userId the user ID
	 * @return the matching kaleo instance
	 * @throws NoSuchInstanceException if a matching kaleo instance could not be found
	 */
	@Override
	public KaleoInstance findByKII_C_U(
			long kaleoInstanceId, long companyId, long userId)
		throws NoSuchInstanceException {

		KaleoInstance kaleoInstance = fetchByKII_C_U(
			kaleoInstanceId, companyId, userId);

		if (kaleoInstance == null) {
			StringBundler sb = new StringBundler(8);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("kaleoInstanceId=");
			sb.append(kaleoInstanceId);

			sb.append(", companyId=");
			sb.append(companyId);

			sb.append(", userId=");
			sb.append(userId);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchInstanceException(sb.toString());
		}

		return kaleoInstance;
	}

	/**
	 * Returns the kaleo instance where kaleoInstanceId = &#63; and companyId = &#63; and userId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param kaleoInstanceId the kaleo instance ID
	 * @param companyId the company ID
	 * @param userId the user ID
	 * @return the matching kaleo instance, or <code>null</code> if a matching kaleo instance could not be found
	 */
	@Override
	public KaleoInstance fetchByKII_C_U(
		long kaleoInstanceId, long companyId, long userId) {

		return fetchByKII_C_U(kaleoInstanceId, companyId, userId, true);
	}

	/**
	 * Returns the kaleo instance where kaleoInstanceId = &#63; and companyId = &#63; and userId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param kaleoInstanceId the kaleo instance ID
	 * @param companyId the company ID
	 * @param userId the user ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching kaleo instance, or <code>null</code> if a matching kaleo instance could not be found
	 */
	@Override
	public KaleoInstance fetchByKII_C_U(
		long kaleoInstanceId, long companyId, long userId,
		boolean useFinderCache) {

		Object[] finderArgs = null;

		if (useFinderCache) {
			finderArgs = new Object[] {kaleoInstanceId, companyId, userId};
		}

		Object result = null;

		if (useFinderCache) {
			result = finderCache.getResult(
				_finderPathFetchByKII_C_U, finderArgs);
		}

		if (result instanceof KaleoInstance) {
			KaleoInstance kaleoInstance = (KaleoInstance)result;

			if ((kaleoInstanceId != kaleoInstance.getKaleoInstanceId()) ||
				(companyId != kaleoInstance.getCompanyId()) ||
				(userId != kaleoInstance.getUserId())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(5);

			sb.append(_SQL_SELECT_KALEOINSTANCE_WHERE);

			sb.append(_FINDER_COLUMN_KII_C_U_KALEOINSTANCEID_2);

			sb.append(_FINDER_COLUMN_KII_C_U_COMPANYID_2);

			sb.append(_FINDER_COLUMN_KII_C_U_USERID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(kaleoInstanceId);

				queryPos.add(companyId);

				queryPos.add(userId);

				List<KaleoInstance> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache) {
						finderCache.putResult(
							_finderPathFetchByKII_C_U, finderArgs, list);
					}
				}
				else {
					if (list.size() > 1) {
						Collections.sort(list, Collections.reverseOrder());

						if (_log.isWarnEnabled()) {
							if (!useFinderCache) {
								finderArgs = new Object[] {
									kaleoInstanceId, companyId, userId
								};
							}

							_log.warn(
								"KaleoInstancePersistenceImpl.fetchByKII_C_U(long, long, long, boolean) with parameters (" +
									StringUtil.merge(finderArgs) +
										") yields a result set with more than 1 result. This violates the logical unique restriction. There is no order guarantee on which result is returned by this finder.");
						}
					}

					KaleoInstance kaleoInstance = list.get(0);

					result = kaleoInstance;

					cacheResult(kaleoInstance);
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
			return (KaleoInstance)result;
		}
	}

	/**
	 * Removes the kaleo instance where kaleoInstanceId = &#63; and companyId = &#63; and userId = &#63; from the database.
	 *
	 * @param kaleoInstanceId the kaleo instance ID
	 * @param companyId the company ID
	 * @param userId the user ID
	 * @return the kaleo instance that was removed
	 */
	@Override
	public KaleoInstance removeByKII_C_U(
			long kaleoInstanceId, long companyId, long userId)
		throws NoSuchInstanceException {

		KaleoInstance kaleoInstance = findByKII_C_U(
			kaleoInstanceId, companyId, userId);

		return remove(kaleoInstance);
	}

	/**
	 * Returns the number of kaleo instances where kaleoInstanceId = &#63; and companyId = &#63; and userId = &#63;.
	 *
	 * @param kaleoInstanceId the kaleo instance ID
	 * @param companyId the company ID
	 * @param userId the user ID
	 * @return the number of matching kaleo instances
	 */
	@Override
	public int countByKII_C_U(
		long kaleoInstanceId, long companyId, long userId) {

		FinderPath finderPath = _finderPathCountByKII_C_U;

		Object[] finderArgs = new Object[] {kaleoInstanceId, companyId, userId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_COUNT_KALEOINSTANCE_WHERE);

			sb.append(_FINDER_COLUMN_KII_C_U_KALEOINSTANCEID_2);

			sb.append(_FINDER_COLUMN_KII_C_U_COMPANYID_2);

			sb.append(_FINDER_COLUMN_KII_C_U_USERID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(kaleoInstanceId);

				queryPos.add(companyId);

				queryPos.add(userId);

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

	private static final String _FINDER_COLUMN_KII_C_U_KALEOINSTANCEID_2 =
		"kaleoInstance.kaleoInstanceId = ? AND ";

	private static final String _FINDER_COLUMN_KII_C_U_COMPANYID_2 =
		"kaleoInstance.companyId = ? AND ";

	private static final String _FINDER_COLUMN_KII_C_U_USERID_2 =
		"kaleoInstance.userId = ?";

	private FinderPath _finderPathWithPaginationFindByC_KDN_KDV_CD;
	private FinderPath _finderPathWithoutPaginationFindByC_KDN_KDV_CD;
	private FinderPath _finderPathCountByC_KDN_KDV_CD;

	/**
	 * Returns all the kaleo instances where companyId = &#63; and kaleoDefinitionName = &#63; and kaleoDefinitionVersion = &#63; and completionDate = &#63;.
	 *
	 * @param companyId the company ID
	 * @param kaleoDefinitionName the kaleo definition name
	 * @param kaleoDefinitionVersion the kaleo definition version
	 * @param completionDate the completion date
	 * @return the matching kaleo instances
	 */
	@Override
	public List<KaleoInstance> findByC_KDN_KDV_CD(
		long companyId, String kaleoDefinitionName, int kaleoDefinitionVersion,
		Date completionDate) {

		return findByC_KDN_KDV_CD(
			companyId, kaleoDefinitionName, kaleoDefinitionVersion,
			completionDate, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the kaleo instances where companyId = &#63; and kaleoDefinitionName = &#63; and kaleoDefinitionVersion = &#63; and completionDate = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoInstanceModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param kaleoDefinitionName the kaleo definition name
	 * @param kaleoDefinitionVersion the kaleo definition version
	 * @param completionDate the completion date
	 * @param start the lower bound of the range of kaleo instances
	 * @param end the upper bound of the range of kaleo instances (not inclusive)
	 * @return the range of matching kaleo instances
	 */
	@Override
	public List<KaleoInstance> findByC_KDN_KDV_CD(
		long companyId, String kaleoDefinitionName, int kaleoDefinitionVersion,
		Date completionDate, int start, int end) {

		return findByC_KDN_KDV_CD(
			companyId, kaleoDefinitionName, kaleoDefinitionVersion,
			completionDate, start, end, null);
	}

	/**
	 * Returns an ordered range of all the kaleo instances where companyId = &#63; and kaleoDefinitionName = &#63; and kaleoDefinitionVersion = &#63; and completionDate = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoInstanceModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param kaleoDefinitionName the kaleo definition name
	 * @param kaleoDefinitionVersion the kaleo definition version
	 * @param completionDate the completion date
	 * @param start the lower bound of the range of kaleo instances
	 * @param end the upper bound of the range of kaleo instances (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching kaleo instances
	 */
	@Override
	public List<KaleoInstance> findByC_KDN_KDV_CD(
		long companyId, String kaleoDefinitionName, int kaleoDefinitionVersion,
		Date completionDate, int start, int end,
		OrderByComparator<KaleoInstance> orderByComparator) {

		return findByC_KDN_KDV_CD(
			companyId, kaleoDefinitionName, kaleoDefinitionVersion,
			completionDate, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the kaleo instances where companyId = &#63; and kaleoDefinitionName = &#63; and kaleoDefinitionVersion = &#63; and completionDate = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoInstanceModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param kaleoDefinitionName the kaleo definition name
	 * @param kaleoDefinitionVersion the kaleo definition version
	 * @param completionDate the completion date
	 * @param start the lower bound of the range of kaleo instances
	 * @param end the upper bound of the range of kaleo instances (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching kaleo instances
	 */
	@Override
	public List<KaleoInstance> findByC_KDN_KDV_CD(
		long companyId, String kaleoDefinitionName, int kaleoDefinitionVersion,
		Date completionDate, int start, int end,
		OrderByComparator<KaleoInstance> orderByComparator,
		boolean useFinderCache) {

		kaleoDefinitionName = Objects.toString(kaleoDefinitionName, "");

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByC_KDN_KDV_CD;
				finderArgs = new Object[] {
					companyId, kaleoDefinitionName, kaleoDefinitionVersion,
					_getTime(completionDate)
				};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByC_KDN_KDV_CD;
			finderArgs = new Object[] {
				companyId, kaleoDefinitionName, kaleoDefinitionVersion,
				_getTime(completionDate), start, end, orderByComparator
			};
		}

		List<KaleoInstance> list = null;

		if (useFinderCache) {
			list = (List<KaleoInstance>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (KaleoInstance kaleoInstance : list) {
					if ((companyId != kaleoInstance.getCompanyId()) ||
						!kaleoDefinitionName.equals(
							kaleoInstance.getKaleoDefinitionName()) ||
						(kaleoDefinitionVersion !=
							kaleoInstance.getKaleoDefinitionVersion()) ||
						!Objects.equals(
							completionDate,
							kaleoInstance.getCompletionDate())) {

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

			sb.append(_SQL_SELECT_KALEOINSTANCE_WHERE);

			sb.append(_FINDER_COLUMN_C_KDN_KDV_CD_COMPANYID_2);

			boolean bindKaleoDefinitionName = false;

			if (kaleoDefinitionName.isEmpty()) {
				sb.append(_FINDER_COLUMN_C_KDN_KDV_CD_KALEODEFINITIONNAME_3);
			}
			else {
				bindKaleoDefinitionName = true;

				sb.append(_FINDER_COLUMN_C_KDN_KDV_CD_KALEODEFINITIONNAME_2);
			}

			sb.append(_FINDER_COLUMN_C_KDN_KDV_CD_KALEODEFINITIONVERSION_2);

			boolean bindCompletionDate = false;

			if (completionDate == null) {
				sb.append(_FINDER_COLUMN_C_KDN_KDV_CD_COMPLETIONDATE_1);
			}
			else {
				bindCompletionDate = true;

				sb.append(_FINDER_COLUMN_C_KDN_KDV_CD_COMPLETIONDATE_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(KaleoInstanceModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(companyId);

				if (bindKaleoDefinitionName) {
					queryPos.add(kaleoDefinitionName);
				}

				queryPos.add(kaleoDefinitionVersion);

				if (bindCompletionDate) {
					queryPos.add(new Timestamp(completionDate.getTime()));
				}

				list = (List<KaleoInstance>)QueryUtil.list(
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
	 * Returns the first kaleo instance in the ordered set where companyId = &#63; and kaleoDefinitionName = &#63; and kaleoDefinitionVersion = &#63; and completionDate = &#63;.
	 *
	 * @param companyId the company ID
	 * @param kaleoDefinitionName the kaleo definition name
	 * @param kaleoDefinitionVersion the kaleo definition version
	 * @param completionDate the completion date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching kaleo instance
	 * @throws NoSuchInstanceException if a matching kaleo instance could not be found
	 */
	@Override
	public KaleoInstance findByC_KDN_KDV_CD_First(
			long companyId, String kaleoDefinitionName,
			int kaleoDefinitionVersion, Date completionDate,
			OrderByComparator<KaleoInstance> orderByComparator)
		throws NoSuchInstanceException {

		KaleoInstance kaleoInstance = fetchByC_KDN_KDV_CD_First(
			companyId, kaleoDefinitionName, kaleoDefinitionVersion,
			completionDate, orderByComparator);

		if (kaleoInstance != null) {
			return kaleoInstance;
		}

		StringBundler sb = new StringBundler(10);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("companyId=");
		sb.append(companyId);

		sb.append(", kaleoDefinitionName=");
		sb.append(kaleoDefinitionName);

		sb.append(", kaleoDefinitionVersion=");
		sb.append(kaleoDefinitionVersion);

		sb.append(", completionDate=");
		sb.append(completionDate);

		sb.append("}");

		throw new NoSuchInstanceException(sb.toString());
	}

	/**
	 * Returns the first kaleo instance in the ordered set where companyId = &#63; and kaleoDefinitionName = &#63; and kaleoDefinitionVersion = &#63; and completionDate = &#63;.
	 *
	 * @param companyId the company ID
	 * @param kaleoDefinitionName the kaleo definition name
	 * @param kaleoDefinitionVersion the kaleo definition version
	 * @param completionDate the completion date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching kaleo instance, or <code>null</code> if a matching kaleo instance could not be found
	 */
	@Override
	public KaleoInstance fetchByC_KDN_KDV_CD_First(
		long companyId, String kaleoDefinitionName, int kaleoDefinitionVersion,
		Date completionDate,
		OrderByComparator<KaleoInstance> orderByComparator) {

		List<KaleoInstance> list = findByC_KDN_KDV_CD(
			companyId, kaleoDefinitionName, kaleoDefinitionVersion,
			completionDate, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last kaleo instance in the ordered set where companyId = &#63; and kaleoDefinitionName = &#63; and kaleoDefinitionVersion = &#63; and completionDate = &#63;.
	 *
	 * @param companyId the company ID
	 * @param kaleoDefinitionName the kaleo definition name
	 * @param kaleoDefinitionVersion the kaleo definition version
	 * @param completionDate the completion date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching kaleo instance
	 * @throws NoSuchInstanceException if a matching kaleo instance could not be found
	 */
	@Override
	public KaleoInstance findByC_KDN_KDV_CD_Last(
			long companyId, String kaleoDefinitionName,
			int kaleoDefinitionVersion, Date completionDate,
			OrderByComparator<KaleoInstance> orderByComparator)
		throws NoSuchInstanceException {

		KaleoInstance kaleoInstance = fetchByC_KDN_KDV_CD_Last(
			companyId, kaleoDefinitionName, kaleoDefinitionVersion,
			completionDate, orderByComparator);

		if (kaleoInstance != null) {
			return kaleoInstance;
		}

		StringBundler sb = new StringBundler(10);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("companyId=");
		sb.append(companyId);

		sb.append(", kaleoDefinitionName=");
		sb.append(kaleoDefinitionName);

		sb.append(", kaleoDefinitionVersion=");
		sb.append(kaleoDefinitionVersion);

		sb.append(", completionDate=");
		sb.append(completionDate);

		sb.append("}");

		throw new NoSuchInstanceException(sb.toString());
	}

	/**
	 * Returns the last kaleo instance in the ordered set where companyId = &#63; and kaleoDefinitionName = &#63; and kaleoDefinitionVersion = &#63; and completionDate = &#63;.
	 *
	 * @param companyId the company ID
	 * @param kaleoDefinitionName the kaleo definition name
	 * @param kaleoDefinitionVersion the kaleo definition version
	 * @param completionDate the completion date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching kaleo instance, or <code>null</code> if a matching kaleo instance could not be found
	 */
	@Override
	public KaleoInstance fetchByC_KDN_KDV_CD_Last(
		long companyId, String kaleoDefinitionName, int kaleoDefinitionVersion,
		Date completionDate,
		OrderByComparator<KaleoInstance> orderByComparator) {

		int count = countByC_KDN_KDV_CD(
			companyId, kaleoDefinitionName, kaleoDefinitionVersion,
			completionDate);

		if (count == 0) {
			return null;
		}

		List<KaleoInstance> list = findByC_KDN_KDV_CD(
			companyId, kaleoDefinitionName, kaleoDefinitionVersion,
			completionDate, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the kaleo instances before and after the current kaleo instance in the ordered set where companyId = &#63; and kaleoDefinitionName = &#63; and kaleoDefinitionVersion = &#63; and completionDate = &#63;.
	 *
	 * @param kaleoInstanceId the primary key of the current kaleo instance
	 * @param companyId the company ID
	 * @param kaleoDefinitionName the kaleo definition name
	 * @param kaleoDefinitionVersion the kaleo definition version
	 * @param completionDate the completion date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next kaleo instance
	 * @throws NoSuchInstanceException if a kaleo instance with the primary key could not be found
	 */
	@Override
	public KaleoInstance[] findByC_KDN_KDV_CD_PrevAndNext(
			long kaleoInstanceId, long companyId, String kaleoDefinitionName,
			int kaleoDefinitionVersion, Date completionDate,
			OrderByComparator<KaleoInstance> orderByComparator)
		throws NoSuchInstanceException {

		kaleoDefinitionName = Objects.toString(kaleoDefinitionName, "");

		KaleoInstance kaleoInstance = findByPrimaryKey(kaleoInstanceId);

		Session session = null;

		try {
			session = openSession();

			KaleoInstance[] array = new KaleoInstanceImpl[3];

			array[0] = getByC_KDN_KDV_CD_PrevAndNext(
				session, kaleoInstance, companyId, kaleoDefinitionName,
				kaleoDefinitionVersion, completionDate, orderByComparator,
				true);

			array[1] = kaleoInstance;

			array[2] = getByC_KDN_KDV_CD_PrevAndNext(
				session, kaleoInstance, companyId, kaleoDefinitionName,
				kaleoDefinitionVersion, completionDate, orderByComparator,
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

	protected KaleoInstance getByC_KDN_KDV_CD_PrevAndNext(
		Session session, KaleoInstance kaleoInstance, long companyId,
		String kaleoDefinitionName, int kaleoDefinitionVersion,
		Date completionDate, OrderByComparator<KaleoInstance> orderByComparator,
		boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				7 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(6);
		}

		sb.append(_SQL_SELECT_KALEOINSTANCE_WHERE);

		sb.append(_FINDER_COLUMN_C_KDN_KDV_CD_COMPANYID_2);

		boolean bindKaleoDefinitionName = false;

		if (kaleoDefinitionName.isEmpty()) {
			sb.append(_FINDER_COLUMN_C_KDN_KDV_CD_KALEODEFINITIONNAME_3);
		}
		else {
			bindKaleoDefinitionName = true;

			sb.append(_FINDER_COLUMN_C_KDN_KDV_CD_KALEODEFINITIONNAME_2);
		}

		sb.append(_FINDER_COLUMN_C_KDN_KDV_CD_KALEODEFINITIONVERSION_2);

		boolean bindCompletionDate = false;

		if (completionDate == null) {
			sb.append(_FINDER_COLUMN_C_KDN_KDV_CD_COMPLETIONDATE_1);
		}
		else {
			bindCompletionDate = true;

			sb.append(_FINDER_COLUMN_C_KDN_KDV_CD_COMPLETIONDATE_2);
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
			sb.append(KaleoInstanceModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(companyId);

		if (bindKaleoDefinitionName) {
			queryPos.add(kaleoDefinitionName);
		}

		queryPos.add(kaleoDefinitionVersion);

		if (bindCompletionDate) {
			queryPos.add(new Timestamp(completionDate.getTime()));
		}

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						kaleoInstance)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<KaleoInstance> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the kaleo instances where companyId = &#63; and kaleoDefinitionName = &#63; and kaleoDefinitionVersion = &#63; and completionDate = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param kaleoDefinitionName the kaleo definition name
	 * @param kaleoDefinitionVersion the kaleo definition version
	 * @param completionDate the completion date
	 */
	@Override
	public void removeByC_KDN_KDV_CD(
		long companyId, String kaleoDefinitionName, int kaleoDefinitionVersion,
		Date completionDate) {

		for (KaleoInstance kaleoInstance :
				findByC_KDN_KDV_CD(
					companyId, kaleoDefinitionName, kaleoDefinitionVersion,
					completionDate, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(kaleoInstance);
		}
	}

	/**
	 * Returns the number of kaleo instances where companyId = &#63; and kaleoDefinitionName = &#63; and kaleoDefinitionVersion = &#63; and completionDate = &#63;.
	 *
	 * @param companyId the company ID
	 * @param kaleoDefinitionName the kaleo definition name
	 * @param kaleoDefinitionVersion the kaleo definition version
	 * @param completionDate the completion date
	 * @return the number of matching kaleo instances
	 */
	@Override
	public int countByC_KDN_KDV_CD(
		long companyId, String kaleoDefinitionName, int kaleoDefinitionVersion,
		Date completionDate) {

		kaleoDefinitionName = Objects.toString(kaleoDefinitionName, "");

		FinderPath finderPath = _finderPathCountByC_KDN_KDV_CD;

		Object[] finderArgs = new Object[] {
			companyId, kaleoDefinitionName, kaleoDefinitionVersion,
			_getTime(completionDate)
		};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(5);

			sb.append(_SQL_COUNT_KALEOINSTANCE_WHERE);

			sb.append(_FINDER_COLUMN_C_KDN_KDV_CD_COMPANYID_2);

			boolean bindKaleoDefinitionName = false;

			if (kaleoDefinitionName.isEmpty()) {
				sb.append(_FINDER_COLUMN_C_KDN_KDV_CD_KALEODEFINITIONNAME_3);
			}
			else {
				bindKaleoDefinitionName = true;

				sb.append(_FINDER_COLUMN_C_KDN_KDV_CD_KALEODEFINITIONNAME_2);
			}

			sb.append(_FINDER_COLUMN_C_KDN_KDV_CD_KALEODEFINITIONVERSION_2);

			boolean bindCompletionDate = false;

			if (completionDate == null) {
				sb.append(_FINDER_COLUMN_C_KDN_KDV_CD_COMPLETIONDATE_1);
			}
			else {
				bindCompletionDate = true;

				sb.append(_FINDER_COLUMN_C_KDN_KDV_CD_COMPLETIONDATE_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(companyId);

				if (bindKaleoDefinitionName) {
					queryPos.add(kaleoDefinitionName);
				}

				queryPos.add(kaleoDefinitionVersion);

				if (bindCompletionDate) {
					queryPos.add(new Timestamp(completionDate.getTime()));
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

	private static final String _FINDER_COLUMN_C_KDN_KDV_CD_COMPANYID_2 =
		"kaleoInstance.companyId = ? AND ";

	private static final String
		_FINDER_COLUMN_C_KDN_KDV_CD_KALEODEFINITIONNAME_2 =
			"kaleoInstance.kaleoDefinitionName = ? AND ";

	private static final String
		_FINDER_COLUMN_C_KDN_KDV_CD_KALEODEFINITIONNAME_3 =
			"(kaleoInstance.kaleoDefinitionName IS NULL OR kaleoInstance.kaleoDefinitionName = '') AND ";

	private static final String
		_FINDER_COLUMN_C_KDN_KDV_CD_KALEODEFINITIONVERSION_2 =
			"kaleoInstance.kaleoDefinitionVersion = ? AND ";

	private static final String _FINDER_COLUMN_C_KDN_KDV_CD_COMPLETIONDATE_1 =
		"kaleoInstance.completionDate IS NULL";

	private static final String _FINDER_COLUMN_C_KDN_KDV_CD_COMPLETIONDATE_2 =
		"kaleoInstance.completionDate = ?";

	public KaleoInstancePersistenceImpl() {
		setModelClass(KaleoInstance.class);

		setModelImplClass(KaleoInstanceImpl.class);
		setModelPKClass(long.class);

		setTable(KaleoInstanceTable.INSTANCE);
	}

	/**
	 * Caches the kaleo instance in the entity cache if it is enabled.
	 *
	 * @param kaleoInstance the kaleo instance
	 */
	@Override
	public void cacheResult(KaleoInstance kaleoInstance) {
		entityCache.putResult(
			KaleoInstanceImpl.class, kaleoInstance.getPrimaryKey(),
			kaleoInstance);

		finderCache.putResult(
			_finderPathFetchByKII_C_U,
			new Object[] {
				kaleoInstance.getKaleoInstanceId(),
				kaleoInstance.getCompanyId(), kaleoInstance.getUserId()
			},
			kaleoInstance);
	}

	private int _valueObjectFinderCacheListThreshold;

	/**
	 * Caches the kaleo instances in the entity cache if it is enabled.
	 *
	 * @param kaleoInstances the kaleo instances
	 */
	@Override
	public void cacheResult(List<KaleoInstance> kaleoInstances) {
		if ((_valueObjectFinderCacheListThreshold == 0) ||
			((_valueObjectFinderCacheListThreshold > 0) &&
			 (kaleoInstances.size() > _valueObjectFinderCacheListThreshold))) {

			return;
		}

		for (KaleoInstance kaleoInstance : kaleoInstances) {
			if (entityCache.getResult(
					KaleoInstanceImpl.class, kaleoInstance.getPrimaryKey()) ==
						null) {

				cacheResult(kaleoInstance);
			}
		}
	}

	/**
	 * Clears the cache for all kaleo instances.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(KaleoInstanceImpl.class);

		finderCache.clearCache(KaleoInstanceImpl.class);
	}

	/**
	 * Clears the cache for the kaleo instance.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(KaleoInstance kaleoInstance) {
		entityCache.removeResult(KaleoInstanceImpl.class, kaleoInstance);
	}

	@Override
	public void clearCache(List<KaleoInstance> kaleoInstances) {
		for (KaleoInstance kaleoInstance : kaleoInstances) {
			entityCache.removeResult(KaleoInstanceImpl.class, kaleoInstance);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(KaleoInstanceImpl.class);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(KaleoInstanceImpl.class, primaryKey);
		}
	}

	protected void cacheUniqueFindersCache(
		KaleoInstanceModelImpl kaleoInstanceModelImpl) {

		Object[] args = new Object[] {
			kaleoInstanceModelImpl.getKaleoInstanceId(),
			kaleoInstanceModelImpl.getCompanyId(),
			kaleoInstanceModelImpl.getUserId()
		};

		finderCache.putResult(_finderPathCountByKII_C_U, args, Long.valueOf(1));
		finderCache.putResult(
			_finderPathFetchByKII_C_U, args, kaleoInstanceModelImpl);
	}

	/**
	 * Creates a new kaleo instance with the primary key. Does not add the kaleo instance to the database.
	 *
	 * @param kaleoInstanceId the primary key for the new kaleo instance
	 * @return the new kaleo instance
	 */
	@Override
	public KaleoInstance create(long kaleoInstanceId) {
		KaleoInstance kaleoInstance = new KaleoInstanceImpl();

		kaleoInstance.setNew(true);
		kaleoInstance.setPrimaryKey(kaleoInstanceId);

		kaleoInstance.setCompanyId(CompanyThreadLocal.getCompanyId());

		return kaleoInstance;
	}

	/**
	 * Removes the kaleo instance with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param kaleoInstanceId the primary key of the kaleo instance
	 * @return the kaleo instance that was removed
	 * @throws NoSuchInstanceException if a kaleo instance with the primary key could not be found
	 */
	@Override
	public KaleoInstance remove(long kaleoInstanceId)
		throws NoSuchInstanceException {

		return remove((Serializable)kaleoInstanceId);
	}

	/**
	 * Removes the kaleo instance with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the kaleo instance
	 * @return the kaleo instance that was removed
	 * @throws NoSuchInstanceException if a kaleo instance with the primary key could not be found
	 */
	@Override
	public KaleoInstance remove(Serializable primaryKey)
		throws NoSuchInstanceException {

		Session session = null;

		try {
			session = openSession();

			KaleoInstance kaleoInstance = (KaleoInstance)session.get(
				KaleoInstanceImpl.class, primaryKey);

			if (kaleoInstance == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchInstanceException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(kaleoInstance);
		}
		catch (NoSuchInstanceException noSuchEntityException) {
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
	protected KaleoInstance removeImpl(KaleoInstance kaleoInstance) {
		Session session = null;

		try {
			session = openSession();

			if (!session.contains(kaleoInstance)) {
				kaleoInstance = (KaleoInstance)session.get(
					KaleoInstanceImpl.class, kaleoInstance.getPrimaryKeyObj());
			}

			if (kaleoInstance != null) {
				session.delete(kaleoInstance);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (kaleoInstance != null) {
			clearCache(kaleoInstance);
		}

		return kaleoInstance;
	}

	@Override
	public KaleoInstance updateImpl(KaleoInstance kaleoInstance) {
		boolean isNew = kaleoInstance.isNew();

		if (!(kaleoInstance instanceof KaleoInstanceModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(kaleoInstance.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					kaleoInstance);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in kaleoInstance proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom KaleoInstance implementation " +
					kaleoInstance.getClass());
		}

		KaleoInstanceModelImpl kaleoInstanceModelImpl =
			(KaleoInstanceModelImpl)kaleoInstance;

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Date date = new Date();

		if (isNew && (kaleoInstance.getCreateDate() == null)) {
			if (serviceContext == null) {
				kaleoInstance.setCreateDate(date);
			}
			else {
				kaleoInstance.setCreateDate(serviceContext.getCreateDate(date));
			}
		}

		if (!kaleoInstanceModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				kaleoInstance.setModifiedDate(date);
			}
			else {
				kaleoInstance.setModifiedDate(
					serviceContext.getModifiedDate(date));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (isNew) {
				session.save(kaleoInstance);
			}
			else {
				kaleoInstance = (KaleoInstance)session.merge(kaleoInstance);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		entityCache.putResult(
			KaleoInstanceImpl.class, kaleoInstanceModelImpl, false, true);

		cacheUniqueFindersCache(kaleoInstanceModelImpl);

		if (isNew) {
			kaleoInstance.setNew(false);
		}

		kaleoInstance.resetOriginalValues();

		return kaleoInstance;
	}

	/**
	 * Returns the kaleo instance with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the kaleo instance
	 * @return the kaleo instance
	 * @throws NoSuchInstanceException if a kaleo instance with the primary key could not be found
	 */
	@Override
	public KaleoInstance findByPrimaryKey(Serializable primaryKey)
		throws NoSuchInstanceException {

		KaleoInstance kaleoInstance = fetchByPrimaryKey(primaryKey);

		if (kaleoInstance == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchInstanceException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return kaleoInstance;
	}

	/**
	 * Returns the kaleo instance with the primary key or throws a <code>NoSuchInstanceException</code> if it could not be found.
	 *
	 * @param kaleoInstanceId the primary key of the kaleo instance
	 * @return the kaleo instance
	 * @throws NoSuchInstanceException if a kaleo instance with the primary key could not be found
	 */
	@Override
	public KaleoInstance findByPrimaryKey(long kaleoInstanceId)
		throws NoSuchInstanceException {

		return findByPrimaryKey((Serializable)kaleoInstanceId);
	}

	/**
	 * Returns the kaleo instance with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param kaleoInstanceId the primary key of the kaleo instance
	 * @return the kaleo instance, or <code>null</code> if a kaleo instance with the primary key could not be found
	 */
	@Override
	public KaleoInstance fetchByPrimaryKey(long kaleoInstanceId) {
		return fetchByPrimaryKey((Serializable)kaleoInstanceId);
	}

	/**
	 * Returns all the kaleo instances.
	 *
	 * @return the kaleo instances
	 */
	@Override
	public List<KaleoInstance> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the kaleo instances.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoInstanceModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of kaleo instances
	 * @param end the upper bound of the range of kaleo instances (not inclusive)
	 * @return the range of kaleo instances
	 */
	@Override
	public List<KaleoInstance> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the kaleo instances.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoInstanceModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of kaleo instances
	 * @param end the upper bound of the range of kaleo instances (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of kaleo instances
	 */
	@Override
	public List<KaleoInstance> findAll(
		int start, int end,
		OrderByComparator<KaleoInstance> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the kaleo instances.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoInstanceModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of kaleo instances
	 * @param end the upper bound of the range of kaleo instances (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of kaleo instances
	 */
	@Override
	public List<KaleoInstance> findAll(
		int start, int end, OrderByComparator<KaleoInstance> orderByComparator,
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

		List<KaleoInstance> list = null;

		if (useFinderCache) {
			list = (List<KaleoInstance>)finderCache.getResult(
				finderPath, finderArgs);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_KALEOINSTANCE);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_KALEOINSTANCE;

				sql = sql.concat(KaleoInstanceModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<KaleoInstance>)QueryUtil.list(
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
	 * Removes all the kaleo instances from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (KaleoInstance kaleoInstance : findAll()) {
			remove(kaleoInstance);
		}
	}

	/**
	 * Returns the number of kaleo instances.
	 *
	 * @return the number of kaleo instances
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(
			_finderPathCountAll, FINDER_ARGS_EMPTY);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(_SQL_COUNT_KALEOINSTANCE);

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
		return "kaleoInstanceId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_KALEOINSTANCE;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return KaleoInstanceModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the kaleo instance persistence.
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

		_finderPathWithPaginationFindByC_U = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByC_U",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {"companyId", "userId"}, true);

		_finderPathWithoutPaginationFindByC_U = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByC_U",
			new String[] {Long.class.getName(), Long.class.getName()},
			new String[] {"companyId", "userId"}, true);

		_finderPathCountByC_U = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_U",
			new String[] {Long.class.getName(), Long.class.getName()},
			new String[] {"companyId", "userId"}, false);

		_finderPathWithPaginationFindByKDI_C = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByKDI_C",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {"kaleoDefinitionId", "completed"}, true);

		_finderPathWithoutPaginationFindByKDI_C = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByKDI_C",
			new String[] {Long.class.getName(), Boolean.class.getName()},
			new String[] {"kaleoDefinitionId", "completed"}, true);

		_finderPathCountByKDI_C = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByKDI_C",
			new String[] {Long.class.getName(), Boolean.class.getName()},
			new String[] {"kaleoDefinitionId", "completed"}, false);

		_finderPathWithPaginationFindByKDVI_C = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByKDVI_C",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {"kaleoDefinitionVersionId", "completed"}, true);

		_finderPathWithoutPaginationFindByKDVI_C = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByKDVI_C",
			new String[] {Long.class.getName(), Boolean.class.getName()},
			new String[] {"kaleoDefinitionVersionId", "completed"}, true);

		_finderPathCountByKDVI_C = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByKDVI_C",
			new String[] {Long.class.getName(), Boolean.class.getName()},
			new String[] {"kaleoDefinitionVersionId", "completed"}, false);

		_finderPathWithPaginationFindByCN_CPK = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByCN_CPK",
			new String[] {
				String.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {"className", "classPK"}, true);

		_finderPathWithoutPaginationFindByCN_CPK = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByCN_CPK",
			new String[] {String.class.getName(), Long.class.getName()},
			new String[] {"className", "classPK"}, true);

		_finderPathCountByCN_CPK = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByCN_CPK",
			new String[] {String.class.getName(), Long.class.getName()},
			new String[] {"className", "classPK"}, false);

		_finderPathFetchByKII_C_U = new FinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByKII_C_U",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName()
			},
			new String[] {"kaleoInstanceId", "companyId", "userId"}, true);

		_finderPathCountByKII_C_U = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByKII_C_U",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName()
			},
			new String[] {"kaleoInstanceId", "companyId", "userId"}, false);

		_finderPathWithPaginationFindByC_KDN_KDV_CD = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByC_KDN_KDV_CD",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Integer.class.getName(), Date.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {
				"companyId", "kaleoDefinitionName", "kaleoDefinitionVersion",
				"completionDate"
			},
			true);

		_finderPathWithoutPaginationFindByC_KDN_KDV_CD = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByC_KDN_KDV_CD",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Integer.class.getName(), Date.class.getName()
			},
			new String[] {
				"companyId", "kaleoDefinitionName", "kaleoDefinitionVersion",
				"completionDate"
			},
			true);

		_finderPathCountByC_KDN_KDV_CD = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_KDN_KDV_CD",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Integer.class.getName(), Date.class.getName()
			},
			new String[] {
				"companyId", "kaleoDefinitionName", "kaleoDefinitionVersion",
				"completionDate"
			},
			false);
	}

	@Deactivate
	public void deactivate() {
		entityCache.removeCache(KaleoInstanceImpl.class.getName());
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

	private static Long _getTime(Date date) {
		if (date == null) {
			return null;
		}

		return date.getTime();
	}

	private static final String _SQL_SELECT_KALEOINSTANCE =
		"SELECT kaleoInstance FROM KaleoInstance kaleoInstance";

	private static final String _SQL_SELECT_KALEOINSTANCE_WHERE =
		"SELECT kaleoInstance FROM KaleoInstance kaleoInstance WHERE ";

	private static final String _SQL_COUNT_KALEOINSTANCE =
		"SELECT COUNT(kaleoInstance) FROM KaleoInstance kaleoInstance";

	private static final String _SQL_COUNT_KALEOINSTANCE_WHERE =
		"SELECT COUNT(kaleoInstance) FROM KaleoInstance kaleoInstance WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS = "kaleoInstance.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No KaleoInstance exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No KaleoInstance exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		KaleoInstancePersistenceImpl.class);

	@Override
	protected FinderCache getFinderCache() {
		return finderCache;
	}

	@Reference
	private KaleoInstanceModelArgumentsResolver
		_kaleoInstanceModelArgumentsResolver;

}