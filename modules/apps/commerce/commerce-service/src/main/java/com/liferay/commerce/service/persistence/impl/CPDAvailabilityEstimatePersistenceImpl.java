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

package com.liferay.commerce.service.persistence.impl;

import com.liferay.commerce.exception.NoSuchCPDAvailabilityEstimateException;
import com.liferay.commerce.model.CPDAvailabilityEstimate;
import com.liferay.commerce.model.CPDAvailabilityEstimateTable;
import com.liferay.commerce.model.impl.CPDAvailabilityEstimateImpl;
import com.liferay.commerce.model.impl.CPDAvailabilityEstimateModelImpl;
import com.liferay.commerce.service.persistence.CPDAvailabilityEstimatePersistence;
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
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;
import com.liferay.portal.spring.extender.service.ServiceReference;

import java.io.Serializable;

import java.lang.reflect.InvocationHandler;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * The persistence implementation for the cpd availability estimate service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Alessio Antonio Rendina
 * @generated
 */
public class CPDAvailabilityEstimatePersistenceImpl
	extends BasePersistenceImpl<CPDAvailabilityEstimate>
	implements CPDAvailabilityEstimatePersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>CPDAvailabilityEstimateUtil</code> to access the cpd availability estimate persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		CPDAvailabilityEstimateImpl.class.getName();

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
	 * Returns all the cpd availability estimates where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching cpd availability estimates
	 */
	@Override
	public List<CPDAvailabilityEstimate> findByUuid(String uuid) {
		return findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the cpd availability estimates where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPDAvailabilityEstimateModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of cpd availability estimates
	 * @param end the upper bound of the range of cpd availability estimates (not inclusive)
	 * @return the range of matching cpd availability estimates
	 */
	@Override
	public List<CPDAvailabilityEstimate> findByUuid(
		String uuid, int start, int end) {

		return findByUuid(uuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the cpd availability estimates where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPDAvailabilityEstimateModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of cpd availability estimates
	 * @param end the upper bound of the range of cpd availability estimates (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cpd availability estimates
	 */
	@Override
	public List<CPDAvailabilityEstimate> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<CPDAvailabilityEstimate> orderByComparator) {

		return findByUuid(uuid, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the cpd availability estimates where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPDAvailabilityEstimateModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of cpd availability estimates
	 * @param end the upper bound of the range of cpd availability estimates (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching cpd availability estimates
	 */
	@Override
	public List<CPDAvailabilityEstimate> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<CPDAvailabilityEstimate> orderByComparator,
		boolean useFinderCache) {

		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByUuid;
				finderArgs = new Object[] {uuid};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByUuid;
			finderArgs = new Object[] {uuid, start, end, orderByComparator};
		}

		List<CPDAvailabilityEstimate> list = null;

		if (useFinderCache) {
			list = (List<CPDAvailabilityEstimate>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (CPDAvailabilityEstimate cpdAvailabilityEstimate : list) {
					if (!uuid.equals(cpdAvailabilityEstimate.getUuid())) {
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

			sb.append(_SQL_SELECT_CPDAVAILABILITYESTIMATE_WHERE);

			boolean bindUuid = false;

			if (uuid.isEmpty()) {
				sb.append(_FINDER_COLUMN_UUID_UUID_3);
			}
			else {
				bindUuid = true;

				sb.append(_FINDER_COLUMN_UUID_UUID_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(CPDAvailabilityEstimateModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindUuid) {
					queryPos.add(uuid);
				}

				list = (List<CPDAvailabilityEstimate>)QueryUtil.list(
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
	 * Returns the first cpd availability estimate in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cpd availability estimate
	 * @throws NoSuchCPDAvailabilityEstimateException if a matching cpd availability estimate could not be found
	 */
	@Override
	public CPDAvailabilityEstimate findByUuid_First(
			String uuid,
			OrderByComparator<CPDAvailabilityEstimate> orderByComparator)
		throws NoSuchCPDAvailabilityEstimateException {

		CPDAvailabilityEstimate cpdAvailabilityEstimate = fetchByUuid_First(
			uuid, orderByComparator);

		if (cpdAvailabilityEstimate != null) {
			return cpdAvailabilityEstimate;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append("}");

		throw new NoSuchCPDAvailabilityEstimateException(sb.toString());
	}

	/**
	 * Returns the first cpd availability estimate in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cpd availability estimate, or <code>null</code> if a matching cpd availability estimate could not be found
	 */
	@Override
	public CPDAvailabilityEstimate fetchByUuid_First(
		String uuid,
		OrderByComparator<CPDAvailabilityEstimate> orderByComparator) {

		List<CPDAvailabilityEstimate> list = findByUuid(
			uuid, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last cpd availability estimate in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cpd availability estimate
	 * @throws NoSuchCPDAvailabilityEstimateException if a matching cpd availability estimate could not be found
	 */
	@Override
	public CPDAvailabilityEstimate findByUuid_Last(
			String uuid,
			OrderByComparator<CPDAvailabilityEstimate> orderByComparator)
		throws NoSuchCPDAvailabilityEstimateException {

		CPDAvailabilityEstimate cpdAvailabilityEstimate = fetchByUuid_Last(
			uuid, orderByComparator);

		if (cpdAvailabilityEstimate != null) {
			return cpdAvailabilityEstimate;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append("}");

		throw new NoSuchCPDAvailabilityEstimateException(sb.toString());
	}

	/**
	 * Returns the last cpd availability estimate in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cpd availability estimate, or <code>null</code> if a matching cpd availability estimate could not be found
	 */
	@Override
	public CPDAvailabilityEstimate fetchByUuid_Last(
		String uuid,
		OrderByComparator<CPDAvailabilityEstimate> orderByComparator) {

		int count = countByUuid(uuid);

		if (count == 0) {
			return null;
		}

		List<CPDAvailabilityEstimate> list = findByUuid(
			uuid, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the cpd availability estimates before and after the current cpd availability estimate in the ordered set where uuid = &#63;.
	 *
	 * @param CPDAvailabilityEstimateId the primary key of the current cpd availability estimate
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next cpd availability estimate
	 * @throws NoSuchCPDAvailabilityEstimateException if a cpd availability estimate with the primary key could not be found
	 */
	@Override
	public CPDAvailabilityEstimate[] findByUuid_PrevAndNext(
			long CPDAvailabilityEstimateId, String uuid,
			OrderByComparator<CPDAvailabilityEstimate> orderByComparator)
		throws NoSuchCPDAvailabilityEstimateException {

		uuid = Objects.toString(uuid, "");

		CPDAvailabilityEstimate cpdAvailabilityEstimate = findByPrimaryKey(
			CPDAvailabilityEstimateId);

		Session session = null;

		try {
			session = openSession();

			CPDAvailabilityEstimate[] array =
				new CPDAvailabilityEstimateImpl[3];

			array[0] = getByUuid_PrevAndNext(
				session, cpdAvailabilityEstimate, uuid, orderByComparator,
				true);

			array[1] = cpdAvailabilityEstimate;

			array[2] = getByUuid_PrevAndNext(
				session, cpdAvailabilityEstimate, uuid, orderByComparator,
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

	protected CPDAvailabilityEstimate getByUuid_PrevAndNext(
		Session session, CPDAvailabilityEstimate cpdAvailabilityEstimate,
		String uuid,
		OrderByComparator<CPDAvailabilityEstimate> orderByComparator,
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

		sb.append(_SQL_SELECT_CPDAVAILABILITYESTIMATE_WHERE);

		boolean bindUuid = false;

		if (uuid.isEmpty()) {
			sb.append(_FINDER_COLUMN_UUID_UUID_3);
		}
		else {
			bindUuid = true;

			sb.append(_FINDER_COLUMN_UUID_UUID_2);
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
			sb.append(CPDAvailabilityEstimateModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		if (bindUuid) {
			queryPos.add(uuid);
		}

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						cpdAvailabilityEstimate)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<CPDAvailabilityEstimate> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the cpd availability estimates where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	@Override
	public void removeByUuid(String uuid) {
		for (CPDAvailabilityEstimate cpdAvailabilityEstimate :
				findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(cpdAvailabilityEstimate);
		}
	}

	/**
	 * Returns the number of cpd availability estimates where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching cpd availability estimates
	 */
	@Override
	public int countByUuid(String uuid) {
		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = _finderPathCountByUuid;

		Object[] finderArgs = new Object[] {uuid};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_CPDAVAILABILITYESTIMATE_WHERE);

			boolean bindUuid = false;

			if (uuid.isEmpty()) {
				sb.append(_FINDER_COLUMN_UUID_UUID_3);
			}
			else {
				bindUuid = true;

				sb.append(_FINDER_COLUMN_UUID_UUID_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindUuid) {
					queryPos.add(uuid);
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

	private static final String _FINDER_COLUMN_UUID_UUID_2 =
		"cpdAvailabilityEstimate.uuid = ?";

	private static final String _FINDER_COLUMN_UUID_UUID_3 =
		"(cpdAvailabilityEstimate.uuid IS NULL OR cpdAvailabilityEstimate.uuid = '')";

	private FinderPath _finderPathWithPaginationFindByUuid_C;
	private FinderPath _finderPathWithoutPaginationFindByUuid_C;
	private FinderPath _finderPathCountByUuid_C;

	/**
	 * Returns all the cpd availability estimates where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching cpd availability estimates
	 */
	@Override
	public List<CPDAvailabilityEstimate> findByUuid_C(
		String uuid, long companyId) {

		return findByUuid_C(
			uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the cpd availability estimates where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPDAvailabilityEstimateModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of cpd availability estimates
	 * @param end the upper bound of the range of cpd availability estimates (not inclusive)
	 * @return the range of matching cpd availability estimates
	 */
	@Override
	public List<CPDAvailabilityEstimate> findByUuid_C(
		String uuid, long companyId, int start, int end) {

		return findByUuid_C(uuid, companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the cpd availability estimates where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPDAvailabilityEstimateModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of cpd availability estimates
	 * @param end the upper bound of the range of cpd availability estimates (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cpd availability estimates
	 */
	@Override
	public List<CPDAvailabilityEstimate> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<CPDAvailabilityEstimate> orderByComparator) {

		return findByUuid_C(
			uuid, companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the cpd availability estimates where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPDAvailabilityEstimateModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of cpd availability estimates
	 * @param end the upper bound of the range of cpd availability estimates (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching cpd availability estimates
	 */
	@Override
	public List<CPDAvailabilityEstimate> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<CPDAvailabilityEstimate> orderByComparator,
		boolean useFinderCache) {

		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByUuid_C;
				finderArgs = new Object[] {uuid, companyId};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByUuid_C;
			finderArgs = new Object[] {
				uuid, companyId, start, end, orderByComparator
			};
		}

		List<CPDAvailabilityEstimate> list = null;

		if (useFinderCache) {
			list = (List<CPDAvailabilityEstimate>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (CPDAvailabilityEstimate cpdAvailabilityEstimate : list) {
					if (!uuid.equals(cpdAvailabilityEstimate.getUuid()) ||
						(companyId != cpdAvailabilityEstimate.getCompanyId())) {

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

			sb.append(_SQL_SELECT_CPDAVAILABILITYESTIMATE_WHERE);

			boolean bindUuid = false;

			if (uuid.isEmpty()) {
				sb.append(_FINDER_COLUMN_UUID_C_UUID_3);
			}
			else {
				bindUuid = true;

				sb.append(_FINDER_COLUMN_UUID_C_UUID_2);
			}

			sb.append(_FINDER_COLUMN_UUID_C_COMPANYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(CPDAvailabilityEstimateModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindUuid) {
					queryPos.add(uuid);
				}

				queryPos.add(companyId);

				list = (List<CPDAvailabilityEstimate>)QueryUtil.list(
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
	 * Returns the first cpd availability estimate in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cpd availability estimate
	 * @throws NoSuchCPDAvailabilityEstimateException if a matching cpd availability estimate could not be found
	 */
	@Override
	public CPDAvailabilityEstimate findByUuid_C_First(
			String uuid, long companyId,
			OrderByComparator<CPDAvailabilityEstimate> orderByComparator)
		throws NoSuchCPDAvailabilityEstimateException {

		CPDAvailabilityEstimate cpdAvailabilityEstimate = fetchByUuid_C_First(
			uuid, companyId, orderByComparator);

		if (cpdAvailabilityEstimate != null) {
			return cpdAvailabilityEstimate;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append(", companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchCPDAvailabilityEstimateException(sb.toString());
	}

	/**
	 * Returns the first cpd availability estimate in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cpd availability estimate, or <code>null</code> if a matching cpd availability estimate could not be found
	 */
	@Override
	public CPDAvailabilityEstimate fetchByUuid_C_First(
		String uuid, long companyId,
		OrderByComparator<CPDAvailabilityEstimate> orderByComparator) {

		List<CPDAvailabilityEstimate> list = findByUuid_C(
			uuid, companyId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last cpd availability estimate in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cpd availability estimate
	 * @throws NoSuchCPDAvailabilityEstimateException if a matching cpd availability estimate could not be found
	 */
	@Override
	public CPDAvailabilityEstimate findByUuid_C_Last(
			String uuid, long companyId,
			OrderByComparator<CPDAvailabilityEstimate> orderByComparator)
		throws NoSuchCPDAvailabilityEstimateException {

		CPDAvailabilityEstimate cpdAvailabilityEstimate = fetchByUuid_C_Last(
			uuid, companyId, orderByComparator);

		if (cpdAvailabilityEstimate != null) {
			return cpdAvailabilityEstimate;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append(", companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchCPDAvailabilityEstimateException(sb.toString());
	}

	/**
	 * Returns the last cpd availability estimate in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cpd availability estimate, or <code>null</code> if a matching cpd availability estimate could not be found
	 */
	@Override
	public CPDAvailabilityEstimate fetchByUuid_C_Last(
		String uuid, long companyId,
		OrderByComparator<CPDAvailabilityEstimate> orderByComparator) {

		int count = countByUuid_C(uuid, companyId);

		if (count == 0) {
			return null;
		}

		List<CPDAvailabilityEstimate> list = findByUuid_C(
			uuid, companyId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the cpd availability estimates before and after the current cpd availability estimate in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param CPDAvailabilityEstimateId the primary key of the current cpd availability estimate
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next cpd availability estimate
	 * @throws NoSuchCPDAvailabilityEstimateException if a cpd availability estimate with the primary key could not be found
	 */
	@Override
	public CPDAvailabilityEstimate[] findByUuid_C_PrevAndNext(
			long CPDAvailabilityEstimateId, String uuid, long companyId,
			OrderByComparator<CPDAvailabilityEstimate> orderByComparator)
		throws NoSuchCPDAvailabilityEstimateException {

		uuid = Objects.toString(uuid, "");

		CPDAvailabilityEstimate cpdAvailabilityEstimate = findByPrimaryKey(
			CPDAvailabilityEstimateId);

		Session session = null;

		try {
			session = openSession();

			CPDAvailabilityEstimate[] array =
				new CPDAvailabilityEstimateImpl[3];

			array[0] = getByUuid_C_PrevAndNext(
				session, cpdAvailabilityEstimate, uuid, companyId,
				orderByComparator, true);

			array[1] = cpdAvailabilityEstimate;

			array[2] = getByUuid_C_PrevAndNext(
				session, cpdAvailabilityEstimate, uuid, companyId,
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

	protected CPDAvailabilityEstimate getByUuid_C_PrevAndNext(
		Session session, CPDAvailabilityEstimate cpdAvailabilityEstimate,
		String uuid, long companyId,
		OrderByComparator<CPDAvailabilityEstimate> orderByComparator,
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

		sb.append(_SQL_SELECT_CPDAVAILABILITYESTIMATE_WHERE);

		boolean bindUuid = false;

		if (uuid.isEmpty()) {
			sb.append(_FINDER_COLUMN_UUID_C_UUID_3);
		}
		else {
			bindUuid = true;

			sb.append(_FINDER_COLUMN_UUID_C_UUID_2);
		}

		sb.append(_FINDER_COLUMN_UUID_C_COMPANYID_2);

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
			sb.append(CPDAvailabilityEstimateModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		if (bindUuid) {
			queryPos.add(uuid);
		}

		queryPos.add(companyId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						cpdAvailabilityEstimate)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<CPDAvailabilityEstimate> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the cpd availability estimates where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	@Override
	public void removeByUuid_C(String uuid, long companyId) {
		for (CPDAvailabilityEstimate cpdAvailabilityEstimate :
				findByUuid_C(
					uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(cpdAvailabilityEstimate);
		}
	}

	/**
	 * Returns the number of cpd availability estimates where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching cpd availability estimates
	 */
	@Override
	public int countByUuid_C(String uuid, long companyId) {
		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = _finderPathCountByUuid_C;

		Object[] finderArgs = new Object[] {uuid, companyId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_CPDAVAILABILITYESTIMATE_WHERE);

			boolean bindUuid = false;

			if (uuid.isEmpty()) {
				sb.append(_FINDER_COLUMN_UUID_C_UUID_3);
			}
			else {
				bindUuid = true;

				sb.append(_FINDER_COLUMN_UUID_C_UUID_2);
			}

			sb.append(_FINDER_COLUMN_UUID_C_COMPANYID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindUuid) {
					queryPos.add(uuid);
				}

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

	private static final String _FINDER_COLUMN_UUID_C_UUID_2 =
		"cpdAvailabilityEstimate.uuid = ? AND ";

	private static final String _FINDER_COLUMN_UUID_C_UUID_3 =
		"(cpdAvailabilityEstimate.uuid IS NULL OR cpdAvailabilityEstimate.uuid = '') AND ";

	private static final String _FINDER_COLUMN_UUID_C_COMPANYID_2 =
		"cpdAvailabilityEstimate.companyId = ?";

	private FinderPath
		_finderPathWithPaginationFindByCommerceAvailabilityEstimateId;
	private FinderPath
		_finderPathWithoutPaginationFindByCommerceAvailabilityEstimateId;
	private FinderPath _finderPathCountByCommerceAvailabilityEstimateId;

	/**
	 * Returns all the cpd availability estimates where commerceAvailabilityEstimateId = &#63;.
	 *
	 * @param commerceAvailabilityEstimateId the commerce availability estimate ID
	 * @return the matching cpd availability estimates
	 */
	@Override
	public List<CPDAvailabilityEstimate> findByCommerceAvailabilityEstimateId(
		long commerceAvailabilityEstimateId) {

		return findByCommerceAvailabilityEstimateId(
			commerceAvailabilityEstimateId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the cpd availability estimates where commerceAvailabilityEstimateId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPDAvailabilityEstimateModelImpl</code>.
	 * </p>
	 *
	 * @param commerceAvailabilityEstimateId the commerce availability estimate ID
	 * @param start the lower bound of the range of cpd availability estimates
	 * @param end the upper bound of the range of cpd availability estimates (not inclusive)
	 * @return the range of matching cpd availability estimates
	 */
	@Override
	public List<CPDAvailabilityEstimate> findByCommerceAvailabilityEstimateId(
		long commerceAvailabilityEstimateId, int start, int end) {

		return findByCommerceAvailabilityEstimateId(
			commerceAvailabilityEstimateId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the cpd availability estimates where commerceAvailabilityEstimateId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPDAvailabilityEstimateModelImpl</code>.
	 * </p>
	 *
	 * @param commerceAvailabilityEstimateId the commerce availability estimate ID
	 * @param start the lower bound of the range of cpd availability estimates
	 * @param end the upper bound of the range of cpd availability estimates (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cpd availability estimates
	 */
	@Override
	public List<CPDAvailabilityEstimate> findByCommerceAvailabilityEstimateId(
		long commerceAvailabilityEstimateId, int start, int end,
		OrderByComparator<CPDAvailabilityEstimate> orderByComparator) {

		return findByCommerceAvailabilityEstimateId(
			commerceAvailabilityEstimateId, start, end, orderByComparator,
			true);
	}

	/**
	 * Returns an ordered range of all the cpd availability estimates where commerceAvailabilityEstimateId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPDAvailabilityEstimateModelImpl</code>.
	 * </p>
	 *
	 * @param commerceAvailabilityEstimateId the commerce availability estimate ID
	 * @param start the lower bound of the range of cpd availability estimates
	 * @param end the upper bound of the range of cpd availability estimates (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching cpd availability estimates
	 */
	@Override
	public List<CPDAvailabilityEstimate> findByCommerceAvailabilityEstimateId(
		long commerceAvailabilityEstimateId, int start, int end,
		OrderByComparator<CPDAvailabilityEstimate> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath =
					_finderPathWithoutPaginationFindByCommerceAvailabilityEstimateId;
				finderArgs = new Object[] {commerceAvailabilityEstimateId};
			}
		}
		else if (useFinderCache) {
			finderPath =
				_finderPathWithPaginationFindByCommerceAvailabilityEstimateId;
			finderArgs = new Object[] {
				commerceAvailabilityEstimateId, start, end, orderByComparator
			};
		}

		List<CPDAvailabilityEstimate> list = null;

		if (useFinderCache) {
			list = (List<CPDAvailabilityEstimate>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (CPDAvailabilityEstimate cpdAvailabilityEstimate : list) {
					if (commerceAvailabilityEstimateId !=
							cpdAvailabilityEstimate.
								getCommerceAvailabilityEstimateId()) {

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

			sb.append(_SQL_SELECT_CPDAVAILABILITYESTIMATE_WHERE);

			sb.append(
				_FINDER_COLUMN_COMMERCEAVAILABILITYESTIMATEID_COMMERCEAVAILABILITYESTIMATEID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(CPDAvailabilityEstimateModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(commerceAvailabilityEstimateId);

				list = (List<CPDAvailabilityEstimate>)QueryUtil.list(
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
	 * Returns the first cpd availability estimate in the ordered set where commerceAvailabilityEstimateId = &#63;.
	 *
	 * @param commerceAvailabilityEstimateId the commerce availability estimate ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cpd availability estimate
	 * @throws NoSuchCPDAvailabilityEstimateException if a matching cpd availability estimate could not be found
	 */
	@Override
	public CPDAvailabilityEstimate findByCommerceAvailabilityEstimateId_First(
			long commerceAvailabilityEstimateId,
			OrderByComparator<CPDAvailabilityEstimate> orderByComparator)
		throws NoSuchCPDAvailabilityEstimateException {

		CPDAvailabilityEstimate cpdAvailabilityEstimate =
			fetchByCommerceAvailabilityEstimateId_First(
				commerceAvailabilityEstimateId, orderByComparator);

		if (cpdAvailabilityEstimate != null) {
			return cpdAvailabilityEstimate;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("commerceAvailabilityEstimateId=");
		sb.append(commerceAvailabilityEstimateId);

		sb.append("}");

		throw new NoSuchCPDAvailabilityEstimateException(sb.toString());
	}

	/**
	 * Returns the first cpd availability estimate in the ordered set where commerceAvailabilityEstimateId = &#63;.
	 *
	 * @param commerceAvailabilityEstimateId the commerce availability estimate ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cpd availability estimate, or <code>null</code> if a matching cpd availability estimate could not be found
	 */
	@Override
	public CPDAvailabilityEstimate fetchByCommerceAvailabilityEstimateId_First(
		long commerceAvailabilityEstimateId,
		OrderByComparator<CPDAvailabilityEstimate> orderByComparator) {

		List<CPDAvailabilityEstimate> list =
			findByCommerceAvailabilityEstimateId(
				commerceAvailabilityEstimateId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last cpd availability estimate in the ordered set where commerceAvailabilityEstimateId = &#63;.
	 *
	 * @param commerceAvailabilityEstimateId the commerce availability estimate ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cpd availability estimate
	 * @throws NoSuchCPDAvailabilityEstimateException if a matching cpd availability estimate could not be found
	 */
	@Override
	public CPDAvailabilityEstimate findByCommerceAvailabilityEstimateId_Last(
			long commerceAvailabilityEstimateId,
			OrderByComparator<CPDAvailabilityEstimate> orderByComparator)
		throws NoSuchCPDAvailabilityEstimateException {

		CPDAvailabilityEstimate cpdAvailabilityEstimate =
			fetchByCommerceAvailabilityEstimateId_Last(
				commerceAvailabilityEstimateId, orderByComparator);

		if (cpdAvailabilityEstimate != null) {
			return cpdAvailabilityEstimate;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("commerceAvailabilityEstimateId=");
		sb.append(commerceAvailabilityEstimateId);

		sb.append("}");

		throw new NoSuchCPDAvailabilityEstimateException(sb.toString());
	}

	/**
	 * Returns the last cpd availability estimate in the ordered set where commerceAvailabilityEstimateId = &#63;.
	 *
	 * @param commerceAvailabilityEstimateId the commerce availability estimate ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cpd availability estimate, or <code>null</code> if a matching cpd availability estimate could not be found
	 */
	@Override
	public CPDAvailabilityEstimate fetchByCommerceAvailabilityEstimateId_Last(
		long commerceAvailabilityEstimateId,
		OrderByComparator<CPDAvailabilityEstimate> orderByComparator) {

		int count = countByCommerceAvailabilityEstimateId(
			commerceAvailabilityEstimateId);

		if (count == 0) {
			return null;
		}

		List<CPDAvailabilityEstimate> list =
			findByCommerceAvailabilityEstimateId(
				commerceAvailabilityEstimateId, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the cpd availability estimates before and after the current cpd availability estimate in the ordered set where commerceAvailabilityEstimateId = &#63;.
	 *
	 * @param CPDAvailabilityEstimateId the primary key of the current cpd availability estimate
	 * @param commerceAvailabilityEstimateId the commerce availability estimate ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next cpd availability estimate
	 * @throws NoSuchCPDAvailabilityEstimateException if a cpd availability estimate with the primary key could not be found
	 */
	@Override
	public CPDAvailabilityEstimate[]
			findByCommerceAvailabilityEstimateId_PrevAndNext(
				long CPDAvailabilityEstimateId,
				long commerceAvailabilityEstimateId,
				OrderByComparator<CPDAvailabilityEstimate> orderByComparator)
		throws NoSuchCPDAvailabilityEstimateException {

		CPDAvailabilityEstimate cpdAvailabilityEstimate = findByPrimaryKey(
			CPDAvailabilityEstimateId);

		Session session = null;

		try {
			session = openSession();

			CPDAvailabilityEstimate[] array =
				new CPDAvailabilityEstimateImpl[3];

			array[0] = getByCommerceAvailabilityEstimateId_PrevAndNext(
				session, cpdAvailabilityEstimate,
				commerceAvailabilityEstimateId, orderByComparator, true);

			array[1] = cpdAvailabilityEstimate;

			array[2] = getByCommerceAvailabilityEstimateId_PrevAndNext(
				session, cpdAvailabilityEstimate,
				commerceAvailabilityEstimateId, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected CPDAvailabilityEstimate
		getByCommerceAvailabilityEstimateId_PrevAndNext(
			Session session, CPDAvailabilityEstimate cpdAvailabilityEstimate,
			long commerceAvailabilityEstimateId,
			OrderByComparator<CPDAvailabilityEstimate> orderByComparator,
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

		sb.append(_SQL_SELECT_CPDAVAILABILITYESTIMATE_WHERE);

		sb.append(
			_FINDER_COLUMN_COMMERCEAVAILABILITYESTIMATEID_COMMERCEAVAILABILITYESTIMATEID_2);

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
			sb.append(CPDAvailabilityEstimateModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(commerceAvailabilityEstimateId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						cpdAvailabilityEstimate)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<CPDAvailabilityEstimate> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the cpd availability estimates where commerceAvailabilityEstimateId = &#63; from the database.
	 *
	 * @param commerceAvailabilityEstimateId the commerce availability estimate ID
	 */
	@Override
	public void removeByCommerceAvailabilityEstimateId(
		long commerceAvailabilityEstimateId) {

		for (CPDAvailabilityEstimate cpdAvailabilityEstimate :
				findByCommerceAvailabilityEstimateId(
					commerceAvailabilityEstimateId, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			remove(cpdAvailabilityEstimate);
		}
	}

	/**
	 * Returns the number of cpd availability estimates where commerceAvailabilityEstimateId = &#63;.
	 *
	 * @param commerceAvailabilityEstimateId the commerce availability estimate ID
	 * @return the number of matching cpd availability estimates
	 */
	@Override
	public int countByCommerceAvailabilityEstimateId(
		long commerceAvailabilityEstimateId) {

		FinderPath finderPath =
			_finderPathCountByCommerceAvailabilityEstimateId;

		Object[] finderArgs = new Object[] {commerceAvailabilityEstimateId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_CPDAVAILABILITYESTIMATE_WHERE);

			sb.append(
				_FINDER_COLUMN_COMMERCEAVAILABILITYESTIMATEID_COMMERCEAVAILABILITYESTIMATEID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(commerceAvailabilityEstimateId);

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
		_FINDER_COLUMN_COMMERCEAVAILABILITYESTIMATEID_COMMERCEAVAILABILITYESTIMATEID_2 =
			"cpdAvailabilityEstimate.commerceAvailabilityEstimateId = ?";

	private FinderPath _finderPathFetchByCProductId;
	private FinderPath _finderPathCountByCProductId;

	/**
	 * Returns the cpd availability estimate where CProductId = &#63; or throws a <code>NoSuchCPDAvailabilityEstimateException</code> if it could not be found.
	 *
	 * @param CProductId the c product ID
	 * @return the matching cpd availability estimate
	 * @throws NoSuchCPDAvailabilityEstimateException if a matching cpd availability estimate could not be found
	 */
	@Override
	public CPDAvailabilityEstimate findByCProductId(long CProductId)
		throws NoSuchCPDAvailabilityEstimateException {

		CPDAvailabilityEstimate cpdAvailabilityEstimate = fetchByCProductId(
			CProductId);

		if (cpdAvailabilityEstimate == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("CProductId=");
			sb.append(CProductId);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchCPDAvailabilityEstimateException(sb.toString());
		}

		return cpdAvailabilityEstimate;
	}

	/**
	 * Returns the cpd availability estimate where CProductId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param CProductId the c product ID
	 * @return the matching cpd availability estimate, or <code>null</code> if a matching cpd availability estimate could not be found
	 */
	@Override
	public CPDAvailabilityEstimate fetchByCProductId(long CProductId) {
		return fetchByCProductId(CProductId, true);
	}

	/**
	 * Returns the cpd availability estimate where CProductId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param CProductId the c product ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching cpd availability estimate, or <code>null</code> if a matching cpd availability estimate could not be found
	 */
	@Override
	public CPDAvailabilityEstimate fetchByCProductId(
		long CProductId, boolean useFinderCache) {

		Object[] finderArgs = null;

		if (useFinderCache) {
			finderArgs = new Object[] {CProductId};
		}

		Object result = null;

		if (useFinderCache) {
			result = finderCache.getResult(
				_finderPathFetchByCProductId, finderArgs);
		}

		if (result instanceof CPDAvailabilityEstimate) {
			CPDAvailabilityEstimate cpdAvailabilityEstimate =
				(CPDAvailabilityEstimate)result;

			if (CProductId != cpdAvailabilityEstimate.getCProductId()) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_SELECT_CPDAVAILABILITYESTIMATE_WHERE);

			sb.append(_FINDER_COLUMN_CPRODUCTID_CPRODUCTID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(CProductId);

				List<CPDAvailabilityEstimate> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache) {
						finderCache.putResult(
							_finderPathFetchByCProductId, finderArgs, list);
					}
				}
				else {
					CPDAvailabilityEstimate cpdAvailabilityEstimate = list.get(
						0);

					result = cpdAvailabilityEstimate;

					cacheResult(cpdAvailabilityEstimate);
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
			return (CPDAvailabilityEstimate)result;
		}
	}

	/**
	 * Removes the cpd availability estimate where CProductId = &#63; from the database.
	 *
	 * @param CProductId the c product ID
	 * @return the cpd availability estimate that was removed
	 */
	@Override
	public CPDAvailabilityEstimate removeByCProductId(long CProductId)
		throws NoSuchCPDAvailabilityEstimateException {

		CPDAvailabilityEstimate cpdAvailabilityEstimate = findByCProductId(
			CProductId);

		return remove(cpdAvailabilityEstimate);
	}

	/**
	 * Returns the number of cpd availability estimates where CProductId = &#63;.
	 *
	 * @param CProductId the c product ID
	 * @return the number of matching cpd availability estimates
	 */
	@Override
	public int countByCProductId(long CProductId) {
		FinderPath finderPath = _finderPathCountByCProductId;

		Object[] finderArgs = new Object[] {CProductId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_CPDAVAILABILITYESTIMATE_WHERE);

			sb.append(_FINDER_COLUMN_CPRODUCTID_CPRODUCTID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(CProductId);

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

	private static final String _FINDER_COLUMN_CPRODUCTID_CPRODUCTID_2 =
		"cpdAvailabilityEstimate.CProductId = ?";

	public CPDAvailabilityEstimatePersistenceImpl() {
		Map<String, String> dbColumnNames = new HashMap<String, String>();

		dbColumnNames.put("uuid", "uuid_");

		setDBColumnNames(dbColumnNames);

		setModelClass(CPDAvailabilityEstimate.class);

		setModelImplClass(CPDAvailabilityEstimateImpl.class);
		setModelPKClass(long.class);

		setTable(CPDAvailabilityEstimateTable.INSTANCE);
	}

	/**
	 * Caches the cpd availability estimate in the entity cache if it is enabled.
	 *
	 * @param cpdAvailabilityEstimate the cpd availability estimate
	 */
	@Override
	public void cacheResult(CPDAvailabilityEstimate cpdAvailabilityEstimate) {
		entityCache.putResult(
			CPDAvailabilityEstimateImpl.class,
			cpdAvailabilityEstimate.getPrimaryKey(), cpdAvailabilityEstimate);

		finderCache.putResult(
			_finderPathFetchByCProductId,
			new Object[] {cpdAvailabilityEstimate.getCProductId()},
			cpdAvailabilityEstimate);
	}

	private int _valueObjectFinderCacheListThreshold;

	/**
	 * Caches the cpd availability estimates in the entity cache if it is enabled.
	 *
	 * @param cpdAvailabilityEstimates the cpd availability estimates
	 */
	@Override
	public void cacheResult(
		List<CPDAvailabilityEstimate> cpdAvailabilityEstimates) {

		if ((_valueObjectFinderCacheListThreshold == 0) ||
			((_valueObjectFinderCacheListThreshold > 0) &&
			 (cpdAvailabilityEstimates.size() >
				 _valueObjectFinderCacheListThreshold))) {

			return;
		}

		for (CPDAvailabilityEstimate cpdAvailabilityEstimate :
				cpdAvailabilityEstimates) {

			if (entityCache.getResult(
					CPDAvailabilityEstimateImpl.class,
					cpdAvailabilityEstimate.getPrimaryKey()) == null) {

				cacheResult(cpdAvailabilityEstimate);
			}
		}
	}

	/**
	 * Clears the cache for all cpd availability estimates.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(CPDAvailabilityEstimateImpl.class);

		finderCache.clearCache(CPDAvailabilityEstimateImpl.class);
	}

	/**
	 * Clears the cache for the cpd availability estimate.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(CPDAvailabilityEstimate cpdAvailabilityEstimate) {
		entityCache.removeResult(
			CPDAvailabilityEstimateImpl.class, cpdAvailabilityEstimate);
	}

	@Override
	public void clearCache(
		List<CPDAvailabilityEstimate> cpdAvailabilityEstimates) {

		for (CPDAvailabilityEstimate cpdAvailabilityEstimate :
				cpdAvailabilityEstimates) {

			entityCache.removeResult(
				CPDAvailabilityEstimateImpl.class, cpdAvailabilityEstimate);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(CPDAvailabilityEstimateImpl.class);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(
				CPDAvailabilityEstimateImpl.class, primaryKey);
		}
	}

	protected void cacheUniqueFindersCache(
		CPDAvailabilityEstimateModelImpl cpdAvailabilityEstimateModelImpl) {

		Object[] args = new Object[] {
			cpdAvailabilityEstimateModelImpl.getCProductId()
		};

		finderCache.putResult(
			_finderPathCountByCProductId, args, Long.valueOf(1));
		finderCache.putResult(
			_finderPathFetchByCProductId, args,
			cpdAvailabilityEstimateModelImpl);
	}

	/**
	 * Creates a new cpd availability estimate with the primary key. Does not add the cpd availability estimate to the database.
	 *
	 * @param CPDAvailabilityEstimateId the primary key for the new cpd availability estimate
	 * @return the new cpd availability estimate
	 */
	@Override
	public CPDAvailabilityEstimate create(long CPDAvailabilityEstimateId) {
		CPDAvailabilityEstimate cpdAvailabilityEstimate =
			new CPDAvailabilityEstimateImpl();

		cpdAvailabilityEstimate.setNew(true);
		cpdAvailabilityEstimate.setPrimaryKey(CPDAvailabilityEstimateId);

		String uuid = PortalUUIDUtil.generate();

		cpdAvailabilityEstimate.setUuid(uuid);

		cpdAvailabilityEstimate.setCompanyId(CompanyThreadLocal.getCompanyId());

		return cpdAvailabilityEstimate;
	}

	/**
	 * Removes the cpd availability estimate with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param CPDAvailabilityEstimateId the primary key of the cpd availability estimate
	 * @return the cpd availability estimate that was removed
	 * @throws NoSuchCPDAvailabilityEstimateException if a cpd availability estimate with the primary key could not be found
	 */
	@Override
	public CPDAvailabilityEstimate remove(long CPDAvailabilityEstimateId)
		throws NoSuchCPDAvailabilityEstimateException {

		return remove((Serializable)CPDAvailabilityEstimateId);
	}

	/**
	 * Removes the cpd availability estimate with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the cpd availability estimate
	 * @return the cpd availability estimate that was removed
	 * @throws NoSuchCPDAvailabilityEstimateException if a cpd availability estimate with the primary key could not be found
	 */
	@Override
	public CPDAvailabilityEstimate remove(Serializable primaryKey)
		throws NoSuchCPDAvailabilityEstimateException {

		Session session = null;

		try {
			session = openSession();

			CPDAvailabilityEstimate cpdAvailabilityEstimate =
				(CPDAvailabilityEstimate)session.get(
					CPDAvailabilityEstimateImpl.class, primaryKey);

			if (cpdAvailabilityEstimate == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchCPDAvailabilityEstimateException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(cpdAvailabilityEstimate);
		}
		catch (NoSuchCPDAvailabilityEstimateException noSuchEntityException) {
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
	protected CPDAvailabilityEstimate removeImpl(
		CPDAvailabilityEstimate cpdAvailabilityEstimate) {

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(cpdAvailabilityEstimate)) {
				cpdAvailabilityEstimate = (CPDAvailabilityEstimate)session.get(
					CPDAvailabilityEstimateImpl.class,
					cpdAvailabilityEstimate.getPrimaryKeyObj());
			}

			if (cpdAvailabilityEstimate != null) {
				session.delete(cpdAvailabilityEstimate);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (cpdAvailabilityEstimate != null) {
			clearCache(cpdAvailabilityEstimate);
		}

		return cpdAvailabilityEstimate;
	}

	@Override
	public CPDAvailabilityEstimate updateImpl(
		CPDAvailabilityEstimate cpdAvailabilityEstimate) {

		boolean isNew = cpdAvailabilityEstimate.isNew();

		if (!(cpdAvailabilityEstimate instanceof
				CPDAvailabilityEstimateModelImpl)) {

			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(cpdAvailabilityEstimate.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					cpdAvailabilityEstimate);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in cpdAvailabilityEstimate proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom CPDAvailabilityEstimate implementation " +
					cpdAvailabilityEstimate.getClass());
		}

		CPDAvailabilityEstimateModelImpl cpdAvailabilityEstimateModelImpl =
			(CPDAvailabilityEstimateModelImpl)cpdAvailabilityEstimate;

		if (Validator.isNull(cpdAvailabilityEstimate.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			cpdAvailabilityEstimate.setUuid(uuid);
		}

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Date date = new Date();

		if (isNew && (cpdAvailabilityEstimate.getCreateDate() == null)) {
			if (serviceContext == null) {
				cpdAvailabilityEstimate.setCreateDate(date);
			}
			else {
				cpdAvailabilityEstimate.setCreateDate(
					serviceContext.getCreateDate(date));
			}
		}

		if (!cpdAvailabilityEstimateModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				cpdAvailabilityEstimate.setModifiedDate(date);
			}
			else {
				cpdAvailabilityEstimate.setModifiedDate(
					serviceContext.getModifiedDate(date));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (isNew) {
				session.save(cpdAvailabilityEstimate);
			}
			else {
				cpdAvailabilityEstimate =
					(CPDAvailabilityEstimate)session.merge(
						cpdAvailabilityEstimate);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		entityCache.putResult(
			CPDAvailabilityEstimateImpl.class, cpdAvailabilityEstimateModelImpl,
			false, true);

		cacheUniqueFindersCache(cpdAvailabilityEstimateModelImpl);

		if (isNew) {
			cpdAvailabilityEstimate.setNew(false);
		}

		cpdAvailabilityEstimate.resetOriginalValues();

		return cpdAvailabilityEstimate;
	}

	/**
	 * Returns the cpd availability estimate with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the cpd availability estimate
	 * @return the cpd availability estimate
	 * @throws NoSuchCPDAvailabilityEstimateException if a cpd availability estimate with the primary key could not be found
	 */
	@Override
	public CPDAvailabilityEstimate findByPrimaryKey(Serializable primaryKey)
		throws NoSuchCPDAvailabilityEstimateException {

		CPDAvailabilityEstimate cpdAvailabilityEstimate = fetchByPrimaryKey(
			primaryKey);

		if (cpdAvailabilityEstimate == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchCPDAvailabilityEstimateException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return cpdAvailabilityEstimate;
	}

	/**
	 * Returns the cpd availability estimate with the primary key or throws a <code>NoSuchCPDAvailabilityEstimateException</code> if it could not be found.
	 *
	 * @param CPDAvailabilityEstimateId the primary key of the cpd availability estimate
	 * @return the cpd availability estimate
	 * @throws NoSuchCPDAvailabilityEstimateException if a cpd availability estimate with the primary key could not be found
	 */
	@Override
	public CPDAvailabilityEstimate findByPrimaryKey(
			long CPDAvailabilityEstimateId)
		throws NoSuchCPDAvailabilityEstimateException {

		return findByPrimaryKey((Serializable)CPDAvailabilityEstimateId);
	}

	/**
	 * Returns the cpd availability estimate with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param CPDAvailabilityEstimateId the primary key of the cpd availability estimate
	 * @return the cpd availability estimate, or <code>null</code> if a cpd availability estimate with the primary key could not be found
	 */
	@Override
	public CPDAvailabilityEstimate fetchByPrimaryKey(
		long CPDAvailabilityEstimateId) {

		return fetchByPrimaryKey((Serializable)CPDAvailabilityEstimateId);
	}

	/**
	 * Returns all the cpd availability estimates.
	 *
	 * @return the cpd availability estimates
	 */
	@Override
	public List<CPDAvailabilityEstimate> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the cpd availability estimates.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPDAvailabilityEstimateModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of cpd availability estimates
	 * @param end the upper bound of the range of cpd availability estimates (not inclusive)
	 * @return the range of cpd availability estimates
	 */
	@Override
	public List<CPDAvailabilityEstimate> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the cpd availability estimates.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPDAvailabilityEstimateModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of cpd availability estimates
	 * @param end the upper bound of the range of cpd availability estimates (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of cpd availability estimates
	 */
	@Override
	public List<CPDAvailabilityEstimate> findAll(
		int start, int end,
		OrderByComparator<CPDAvailabilityEstimate> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the cpd availability estimates.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPDAvailabilityEstimateModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of cpd availability estimates
	 * @param end the upper bound of the range of cpd availability estimates (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of cpd availability estimates
	 */
	@Override
	public List<CPDAvailabilityEstimate> findAll(
		int start, int end,
		OrderByComparator<CPDAvailabilityEstimate> orderByComparator,
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

		List<CPDAvailabilityEstimate> list = null;

		if (useFinderCache) {
			list = (List<CPDAvailabilityEstimate>)finderCache.getResult(
				finderPath, finderArgs);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_CPDAVAILABILITYESTIMATE);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_CPDAVAILABILITYESTIMATE;

				sql = sql.concat(
					CPDAvailabilityEstimateModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<CPDAvailabilityEstimate>)QueryUtil.list(
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
	 * Removes all the cpd availability estimates from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (CPDAvailabilityEstimate cpdAvailabilityEstimate : findAll()) {
			remove(cpdAvailabilityEstimate);
		}
	}

	/**
	 * Returns the number of cpd availability estimates.
	 *
	 * @return the number of cpd availability estimates
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
					_SQL_COUNT_CPDAVAILABILITYESTIMATE);

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
		return "CPDAvailabilityEstimateId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_CPDAVAILABILITYESTIMATE;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return CPDAvailabilityEstimateModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the cpd availability estimate persistence.
	 */
	public void afterPropertiesSet() {
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

		_finderPathWithPaginationFindByUuid = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUuid",
			new String[] {
				String.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"uuid_"}, true);

		_finderPathWithoutPaginationFindByUuid = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid",
			new String[] {String.class.getName()}, new String[] {"uuid_"},
			true);

		_finderPathCountByUuid = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUuid",
			new String[] {String.class.getName()}, new String[] {"uuid_"},
			false);

		_finderPathWithPaginationFindByUuid_C = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUuid_C",
			new String[] {
				String.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {"uuid_", "companyId"}, true);

		_finderPathWithoutPaginationFindByUuid_C = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid_C",
			new String[] {String.class.getName(), Long.class.getName()},
			new String[] {"uuid_", "companyId"}, true);

		_finderPathCountByUuid_C = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUuid_C",
			new String[] {String.class.getName(), Long.class.getName()},
			new String[] {"uuid_", "companyId"}, false);

		_finderPathWithPaginationFindByCommerceAvailabilityEstimateId =
			new FinderPath(
				FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
				"findByCommerceAvailabilityEstimateId",
				new String[] {
					Long.class.getName(), Integer.class.getName(),
					Integer.class.getName(), OrderByComparator.class.getName()
				},
				new String[] {"commerceAvailabilityEstimateId"}, true);

		_finderPathWithoutPaginationFindByCommerceAvailabilityEstimateId =
			new FinderPath(
				FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
				"findByCommerceAvailabilityEstimateId",
				new String[] {Long.class.getName()},
				new String[] {"commerceAvailabilityEstimateId"}, true);

		_finderPathCountByCommerceAvailabilityEstimateId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByCommerceAvailabilityEstimateId",
			new String[] {Long.class.getName()},
			new String[] {"commerceAvailabilityEstimateId"}, false);

		_finderPathFetchByCProductId = new FinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByCProductId",
			new String[] {Long.class.getName()}, new String[] {"CProductId"},
			true);

		_finderPathCountByCProductId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByCProductId",
			new String[] {Long.class.getName()}, new String[] {"CProductId"},
			false);
	}

	public void destroy() {
		entityCache.removeCache(CPDAvailabilityEstimateImpl.class.getName());
	}

	@ServiceReference(type = EntityCache.class)
	protected EntityCache entityCache;

	@ServiceReference(type = FinderCache.class)
	protected FinderCache finderCache;

	private static final String _SQL_SELECT_CPDAVAILABILITYESTIMATE =
		"SELECT cpdAvailabilityEstimate FROM CPDAvailabilityEstimate cpdAvailabilityEstimate";

	private static final String _SQL_SELECT_CPDAVAILABILITYESTIMATE_WHERE =
		"SELECT cpdAvailabilityEstimate FROM CPDAvailabilityEstimate cpdAvailabilityEstimate WHERE ";

	private static final String _SQL_COUNT_CPDAVAILABILITYESTIMATE =
		"SELECT COUNT(cpdAvailabilityEstimate) FROM CPDAvailabilityEstimate cpdAvailabilityEstimate";

	private static final String _SQL_COUNT_CPDAVAILABILITYESTIMATE_WHERE =
		"SELECT COUNT(cpdAvailabilityEstimate) FROM CPDAvailabilityEstimate cpdAvailabilityEstimate WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS =
		"cpdAvailabilityEstimate.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No CPDAvailabilityEstimate exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No CPDAvailabilityEstimate exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		CPDAvailabilityEstimatePersistenceImpl.class);

	private static final Set<String> _badColumnNames = SetUtil.fromArray(
		new String[] {"uuid"});

	@Override
	protected FinderCache getFinderCache() {
		return finderCache;
	}

}