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

import com.liferay.commerce.exception.NoSuchRegionException;
import com.liferay.commerce.model.CommerceRegion;
import com.liferay.commerce.model.CommerceRegionTable;
import com.liferay.commerce.model.impl.CommerceRegionImpl;
import com.liferay.commerce.model.impl.CommerceRegionModelImpl;
import com.liferay.commerce.service.persistence.CommerceRegionPersistence;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.orm.ArgumentsResolver;
import com.liferay.portal.kernel.dao.orm.EntityCache;
import com.liferay.portal.kernel.dao.orm.FinderCache;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;
import com.liferay.portal.spring.extender.service.ServiceReference;

import java.io.Serializable;

import java.lang.reflect.InvocationHandler;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceRegistration;

/**
 * The persistence implementation for the commerce region service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Alessio Antonio Rendina
 * @generated
 */
public class CommerceRegionPersistenceImpl
	extends BasePersistenceImpl<CommerceRegion>
	implements CommerceRegionPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>CommerceRegionUtil</code> to access the commerce region persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		CommerceRegionImpl.class.getName();

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
	 * Returns all the commerce regions where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching commerce regions
	 */
	@Override
	public List<CommerceRegion> findByUuid(String uuid) {
		return findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce regions where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceRegionModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of commerce regions
	 * @param end the upper bound of the range of commerce regions (not inclusive)
	 * @return the range of matching commerce regions
	 */
	@Override
	public List<CommerceRegion> findByUuid(String uuid, int start, int end) {
		return findByUuid(uuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce regions where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceRegionModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of commerce regions
	 * @param end the upper bound of the range of commerce regions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce regions
	 */
	@Override
	public List<CommerceRegion> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<CommerceRegion> orderByComparator) {

		return findByUuid(uuid, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce regions where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceRegionModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of commerce regions
	 * @param end the upper bound of the range of commerce regions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce regions
	 */
	@Override
	public List<CommerceRegion> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<CommerceRegion> orderByComparator,
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

		List<CommerceRegion> list = null;

		if (useFinderCache) {
			list = (List<CommerceRegion>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (CommerceRegion commerceRegion : list) {
					if (!uuid.equals(commerceRegion.getUuid())) {
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

			sb.append(_SQL_SELECT_COMMERCEREGION_WHERE);

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
				sb.append(CommerceRegionModelImpl.ORDER_BY_JPQL);
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

				list = (List<CommerceRegion>)QueryUtil.list(
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
	 * Returns the first commerce region in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce region
	 * @throws NoSuchRegionException if a matching commerce region could not be found
	 */
	@Override
	public CommerceRegion findByUuid_First(
			String uuid, OrderByComparator<CommerceRegion> orderByComparator)
		throws NoSuchRegionException {

		CommerceRegion commerceRegion = fetchByUuid_First(
			uuid, orderByComparator);

		if (commerceRegion != null) {
			return commerceRegion;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append("}");

		throw new NoSuchRegionException(sb.toString());
	}

	/**
	 * Returns the first commerce region in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce region, or <code>null</code> if a matching commerce region could not be found
	 */
	@Override
	public CommerceRegion fetchByUuid_First(
		String uuid, OrderByComparator<CommerceRegion> orderByComparator) {

		List<CommerceRegion> list = findByUuid(uuid, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last commerce region in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce region
	 * @throws NoSuchRegionException if a matching commerce region could not be found
	 */
	@Override
	public CommerceRegion findByUuid_Last(
			String uuid, OrderByComparator<CommerceRegion> orderByComparator)
		throws NoSuchRegionException {

		CommerceRegion commerceRegion = fetchByUuid_Last(
			uuid, orderByComparator);

		if (commerceRegion != null) {
			return commerceRegion;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append("}");

		throw new NoSuchRegionException(sb.toString());
	}

	/**
	 * Returns the last commerce region in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce region, or <code>null</code> if a matching commerce region could not be found
	 */
	@Override
	public CommerceRegion fetchByUuid_Last(
		String uuid, OrderByComparator<CommerceRegion> orderByComparator) {

		int count = countByUuid(uuid);

		if (count == 0) {
			return null;
		}

		List<CommerceRegion> list = findByUuid(
			uuid, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the commerce regions before and after the current commerce region in the ordered set where uuid = &#63;.
	 *
	 * @param commerceRegionId the primary key of the current commerce region
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce region
	 * @throws NoSuchRegionException if a commerce region with the primary key could not be found
	 */
	@Override
	public CommerceRegion[] findByUuid_PrevAndNext(
			long commerceRegionId, String uuid,
			OrderByComparator<CommerceRegion> orderByComparator)
		throws NoSuchRegionException {

		uuid = Objects.toString(uuid, "");

		CommerceRegion commerceRegion = findByPrimaryKey(commerceRegionId);

		Session session = null;

		try {
			session = openSession();

			CommerceRegion[] array = new CommerceRegionImpl[3];

			array[0] = getByUuid_PrevAndNext(
				session, commerceRegion, uuid, orderByComparator, true);

			array[1] = commerceRegion;

			array[2] = getByUuid_PrevAndNext(
				session, commerceRegion, uuid, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected CommerceRegion getByUuid_PrevAndNext(
		Session session, CommerceRegion commerceRegion, String uuid,
		OrderByComparator<CommerceRegion> orderByComparator, boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(3);
		}

		sb.append(_SQL_SELECT_COMMERCEREGION_WHERE);

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
			sb.append(CommerceRegionModelImpl.ORDER_BY_JPQL);
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
						commerceRegion)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<CommerceRegion> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the commerce regions where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	@Override
	public void removeByUuid(String uuid) {
		for (CommerceRegion commerceRegion :
				findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(commerceRegion);
		}
	}

	/**
	 * Returns the number of commerce regions where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching commerce regions
	 */
	@Override
	public int countByUuid(String uuid) {
		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = _finderPathCountByUuid;

		Object[] finderArgs = new Object[] {uuid};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_COMMERCEREGION_WHERE);

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
		"commerceRegion.uuid = ?";

	private static final String _FINDER_COLUMN_UUID_UUID_3 =
		"(commerceRegion.uuid IS NULL OR commerceRegion.uuid = '')";

	private FinderPath _finderPathWithPaginationFindByUuid_C;
	private FinderPath _finderPathWithoutPaginationFindByUuid_C;
	private FinderPath _finderPathCountByUuid_C;

	/**
	 * Returns all the commerce regions where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching commerce regions
	 */
	@Override
	public List<CommerceRegion> findByUuid_C(String uuid, long companyId) {
		return findByUuid_C(
			uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce regions where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceRegionModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce regions
	 * @param end the upper bound of the range of commerce regions (not inclusive)
	 * @return the range of matching commerce regions
	 */
	@Override
	public List<CommerceRegion> findByUuid_C(
		String uuid, long companyId, int start, int end) {

		return findByUuid_C(uuid, companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce regions where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceRegionModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce regions
	 * @param end the upper bound of the range of commerce regions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce regions
	 */
	@Override
	public List<CommerceRegion> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<CommerceRegion> orderByComparator) {

		return findByUuid_C(
			uuid, companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce regions where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceRegionModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce regions
	 * @param end the upper bound of the range of commerce regions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce regions
	 */
	@Override
	public List<CommerceRegion> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<CommerceRegion> orderByComparator,
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

		List<CommerceRegion> list = null;

		if (useFinderCache) {
			list = (List<CommerceRegion>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (CommerceRegion commerceRegion : list) {
					if (!uuid.equals(commerceRegion.getUuid()) ||
						(companyId != commerceRegion.getCompanyId())) {

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

			sb.append(_SQL_SELECT_COMMERCEREGION_WHERE);

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
				sb.append(CommerceRegionModelImpl.ORDER_BY_JPQL);
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

				list = (List<CommerceRegion>)QueryUtil.list(
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
	 * Returns the first commerce region in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce region
	 * @throws NoSuchRegionException if a matching commerce region could not be found
	 */
	@Override
	public CommerceRegion findByUuid_C_First(
			String uuid, long companyId,
			OrderByComparator<CommerceRegion> orderByComparator)
		throws NoSuchRegionException {

		CommerceRegion commerceRegion = fetchByUuid_C_First(
			uuid, companyId, orderByComparator);

		if (commerceRegion != null) {
			return commerceRegion;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append(", companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchRegionException(sb.toString());
	}

	/**
	 * Returns the first commerce region in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce region, or <code>null</code> if a matching commerce region could not be found
	 */
	@Override
	public CommerceRegion fetchByUuid_C_First(
		String uuid, long companyId,
		OrderByComparator<CommerceRegion> orderByComparator) {

		List<CommerceRegion> list = findByUuid_C(
			uuid, companyId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last commerce region in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce region
	 * @throws NoSuchRegionException if a matching commerce region could not be found
	 */
	@Override
	public CommerceRegion findByUuid_C_Last(
			String uuid, long companyId,
			OrderByComparator<CommerceRegion> orderByComparator)
		throws NoSuchRegionException {

		CommerceRegion commerceRegion = fetchByUuid_C_Last(
			uuid, companyId, orderByComparator);

		if (commerceRegion != null) {
			return commerceRegion;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append(", companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchRegionException(sb.toString());
	}

	/**
	 * Returns the last commerce region in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce region, or <code>null</code> if a matching commerce region could not be found
	 */
	@Override
	public CommerceRegion fetchByUuid_C_Last(
		String uuid, long companyId,
		OrderByComparator<CommerceRegion> orderByComparator) {

		int count = countByUuid_C(uuid, companyId);

		if (count == 0) {
			return null;
		}

		List<CommerceRegion> list = findByUuid_C(
			uuid, companyId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the commerce regions before and after the current commerce region in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param commerceRegionId the primary key of the current commerce region
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce region
	 * @throws NoSuchRegionException if a commerce region with the primary key could not be found
	 */
	@Override
	public CommerceRegion[] findByUuid_C_PrevAndNext(
			long commerceRegionId, String uuid, long companyId,
			OrderByComparator<CommerceRegion> orderByComparator)
		throws NoSuchRegionException {

		uuid = Objects.toString(uuid, "");

		CommerceRegion commerceRegion = findByPrimaryKey(commerceRegionId);

		Session session = null;

		try {
			session = openSession();

			CommerceRegion[] array = new CommerceRegionImpl[3];

			array[0] = getByUuid_C_PrevAndNext(
				session, commerceRegion, uuid, companyId, orderByComparator,
				true);

			array[1] = commerceRegion;

			array[2] = getByUuid_C_PrevAndNext(
				session, commerceRegion, uuid, companyId, orderByComparator,
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

	protected CommerceRegion getByUuid_C_PrevAndNext(
		Session session, CommerceRegion commerceRegion, String uuid,
		long companyId, OrderByComparator<CommerceRegion> orderByComparator,
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

		sb.append(_SQL_SELECT_COMMERCEREGION_WHERE);

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
			sb.append(CommerceRegionModelImpl.ORDER_BY_JPQL);
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
						commerceRegion)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<CommerceRegion> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the commerce regions where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	@Override
	public void removeByUuid_C(String uuid, long companyId) {
		for (CommerceRegion commerceRegion :
				findByUuid_C(
					uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(commerceRegion);
		}
	}

	/**
	 * Returns the number of commerce regions where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching commerce regions
	 */
	@Override
	public int countByUuid_C(String uuid, long companyId) {
		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = _finderPathCountByUuid_C;

		Object[] finderArgs = new Object[] {uuid, companyId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_COMMERCEREGION_WHERE);

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
		"commerceRegion.uuid = ? AND ";

	private static final String _FINDER_COLUMN_UUID_C_UUID_3 =
		"(commerceRegion.uuid IS NULL OR commerceRegion.uuid = '') AND ";

	private static final String _FINDER_COLUMN_UUID_C_COMPANYID_2 =
		"commerceRegion.companyId = ?";

	private FinderPath _finderPathWithPaginationFindByCommerceCountryId;
	private FinderPath _finderPathWithoutPaginationFindByCommerceCountryId;
	private FinderPath _finderPathCountByCommerceCountryId;

	/**
	 * Returns all the commerce regions where commerceCountryId = &#63;.
	 *
	 * @param commerceCountryId the commerce country ID
	 * @return the matching commerce regions
	 */
	@Override
	public List<CommerceRegion> findByCommerceCountryId(
		long commerceCountryId) {

		return findByCommerceCountryId(
			commerceCountryId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce regions where commerceCountryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceRegionModelImpl</code>.
	 * </p>
	 *
	 * @param commerceCountryId the commerce country ID
	 * @param start the lower bound of the range of commerce regions
	 * @param end the upper bound of the range of commerce regions (not inclusive)
	 * @return the range of matching commerce regions
	 */
	@Override
	public List<CommerceRegion> findByCommerceCountryId(
		long commerceCountryId, int start, int end) {

		return findByCommerceCountryId(commerceCountryId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce regions where commerceCountryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceRegionModelImpl</code>.
	 * </p>
	 *
	 * @param commerceCountryId the commerce country ID
	 * @param start the lower bound of the range of commerce regions
	 * @param end the upper bound of the range of commerce regions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce regions
	 */
	@Override
	public List<CommerceRegion> findByCommerceCountryId(
		long commerceCountryId, int start, int end,
		OrderByComparator<CommerceRegion> orderByComparator) {

		return findByCommerceCountryId(
			commerceCountryId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce regions where commerceCountryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceRegionModelImpl</code>.
	 * </p>
	 *
	 * @param commerceCountryId the commerce country ID
	 * @param start the lower bound of the range of commerce regions
	 * @param end the upper bound of the range of commerce regions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce regions
	 */
	@Override
	public List<CommerceRegion> findByCommerceCountryId(
		long commerceCountryId, int start, int end,
		OrderByComparator<CommerceRegion> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath =
					_finderPathWithoutPaginationFindByCommerceCountryId;
				finderArgs = new Object[] {commerceCountryId};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByCommerceCountryId;
			finderArgs = new Object[] {
				commerceCountryId, start, end, orderByComparator
			};
		}

		List<CommerceRegion> list = null;

		if (useFinderCache) {
			list = (List<CommerceRegion>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (CommerceRegion commerceRegion : list) {
					if (commerceCountryId !=
							commerceRegion.getCommerceCountryId()) {

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

			sb.append(_SQL_SELECT_COMMERCEREGION_WHERE);

			sb.append(_FINDER_COLUMN_COMMERCECOUNTRYID_COMMERCECOUNTRYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(CommerceRegionModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(commerceCountryId);

				list = (List<CommerceRegion>)QueryUtil.list(
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
	 * Returns the first commerce region in the ordered set where commerceCountryId = &#63;.
	 *
	 * @param commerceCountryId the commerce country ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce region
	 * @throws NoSuchRegionException if a matching commerce region could not be found
	 */
	@Override
	public CommerceRegion findByCommerceCountryId_First(
			long commerceCountryId,
			OrderByComparator<CommerceRegion> orderByComparator)
		throws NoSuchRegionException {

		CommerceRegion commerceRegion = fetchByCommerceCountryId_First(
			commerceCountryId, orderByComparator);

		if (commerceRegion != null) {
			return commerceRegion;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("commerceCountryId=");
		sb.append(commerceCountryId);

		sb.append("}");

		throw new NoSuchRegionException(sb.toString());
	}

	/**
	 * Returns the first commerce region in the ordered set where commerceCountryId = &#63;.
	 *
	 * @param commerceCountryId the commerce country ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce region, or <code>null</code> if a matching commerce region could not be found
	 */
	@Override
	public CommerceRegion fetchByCommerceCountryId_First(
		long commerceCountryId,
		OrderByComparator<CommerceRegion> orderByComparator) {

		List<CommerceRegion> list = findByCommerceCountryId(
			commerceCountryId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last commerce region in the ordered set where commerceCountryId = &#63;.
	 *
	 * @param commerceCountryId the commerce country ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce region
	 * @throws NoSuchRegionException if a matching commerce region could not be found
	 */
	@Override
	public CommerceRegion findByCommerceCountryId_Last(
			long commerceCountryId,
			OrderByComparator<CommerceRegion> orderByComparator)
		throws NoSuchRegionException {

		CommerceRegion commerceRegion = fetchByCommerceCountryId_Last(
			commerceCountryId, orderByComparator);

		if (commerceRegion != null) {
			return commerceRegion;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("commerceCountryId=");
		sb.append(commerceCountryId);

		sb.append("}");

		throw new NoSuchRegionException(sb.toString());
	}

	/**
	 * Returns the last commerce region in the ordered set where commerceCountryId = &#63;.
	 *
	 * @param commerceCountryId the commerce country ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce region, or <code>null</code> if a matching commerce region could not be found
	 */
	@Override
	public CommerceRegion fetchByCommerceCountryId_Last(
		long commerceCountryId,
		OrderByComparator<CommerceRegion> orderByComparator) {

		int count = countByCommerceCountryId(commerceCountryId);

		if (count == 0) {
			return null;
		}

		List<CommerceRegion> list = findByCommerceCountryId(
			commerceCountryId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the commerce regions before and after the current commerce region in the ordered set where commerceCountryId = &#63;.
	 *
	 * @param commerceRegionId the primary key of the current commerce region
	 * @param commerceCountryId the commerce country ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce region
	 * @throws NoSuchRegionException if a commerce region with the primary key could not be found
	 */
	@Override
	public CommerceRegion[] findByCommerceCountryId_PrevAndNext(
			long commerceRegionId, long commerceCountryId,
			OrderByComparator<CommerceRegion> orderByComparator)
		throws NoSuchRegionException {

		CommerceRegion commerceRegion = findByPrimaryKey(commerceRegionId);

		Session session = null;

		try {
			session = openSession();

			CommerceRegion[] array = new CommerceRegionImpl[3];

			array[0] = getByCommerceCountryId_PrevAndNext(
				session, commerceRegion, commerceCountryId, orderByComparator,
				true);

			array[1] = commerceRegion;

			array[2] = getByCommerceCountryId_PrevAndNext(
				session, commerceRegion, commerceCountryId, orderByComparator,
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

	protected CommerceRegion getByCommerceCountryId_PrevAndNext(
		Session session, CommerceRegion commerceRegion, long commerceCountryId,
		OrderByComparator<CommerceRegion> orderByComparator, boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(3);
		}

		sb.append(_SQL_SELECT_COMMERCEREGION_WHERE);

		sb.append(_FINDER_COLUMN_COMMERCECOUNTRYID_COMMERCECOUNTRYID_2);

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
			sb.append(CommerceRegionModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(commerceCountryId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						commerceRegion)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<CommerceRegion> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the commerce regions where commerceCountryId = &#63; from the database.
	 *
	 * @param commerceCountryId the commerce country ID
	 */
	@Override
	public void removeByCommerceCountryId(long commerceCountryId) {
		for (CommerceRegion commerceRegion :
				findByCommerceCountryId(
					commerceCountryId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(commerceRegion);
		}
	}

	/**
	 * Returns the number of commerce regions where commerceCountryId = &#63;.
	 *
	 * @param commerceCountryId the commerce country ID
	 * @return the number of matching commerce regions
	 */
	@Override
	public int countByCommerceCountryId(long commerceCountryId) {
		FinderPath finderPath = _finderPathCountByCommerceCountryId;

		Object[] finderArgs = new Object[] {commerceCountryId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_COMMERCEREGION_WHERE);

			sb.append(_FINDER_COLUMN_COMMERCECOUNTRYID_COMMERCECOUNTRYID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(commerceCountryId);

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
		_FINDER_COLUMN_COMMERCECOUNTRYID_COMMERCECOUNTRYID_2 =
			"commerceRegion.commerceCountryId = ?";

	private FinderPath _finderPathFetchByC_C;
	private FinderPath _finderPathCountByC_C;

	/**
	 * Returns the commerce region where commerceCountryId = &#63; and code = &#63; or throws a <code>NoSuchRegionException</code> if it could not be found.
	 *
	 * @param commerceCountryId the commerce country ID
	 * @param code the code
	 * @return the matching commerce region
	 * @throws NoSuchRegionException if a matching commerce region could not be found
	 */
	@Override
	public CommerceRegion findByC_C(long commerceCountryId, String code)
		throws NoSuchRegionException {

		CommerceRegion commerceRegion = fetchByC_C(commerceCountryId, code);

		if (commerceRegion == null) {
			StringBundler sb = new StringBundler(6);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("commerceCountryId=");
			sb.append(commerceCountryId);

			sb.append(", code=");
			sb.append(code);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchRegionException(sb.toString());
		}

		return commerceRegion;
	}

	/**
	 * Returns the commerce region where commerceCountryId = &#63; and code = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param commerceCountryId the commerce country ID
	 * @param code the code
	 * @return the matching commerce region, or <code>null</code> if a matching commerce region could not be found
	 */
	@Override
	public CommerceRegion fetchByC_C(long commerceCountryId, String code) {
		return fetchByC_C(commerceCountryId, code, true);
	}

	/**
	 * Returns the commerce region where commerceCountryId = &#63; and code = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param commerceCountryId the commerce country ID
	 * @param code the code
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching commerce region, or <code>null</code> if a matching commerce region could not be found
	 */
	@Override
	public CommerceRegion fetchByC_C(
		long commerceCountryId, String code, boolean useFinderCache) {

		code = Objects.toString(code, "");

		Object[] finderArgs = null;

		if (useFinderCache) {
			finderArgs = new Object[] {commerceCountryId, code};
		}

		Object result = null;

		if (useFinderCache) {
			result = finderCache.getResult(
				_finderPathFetchByC_C, finderArgs, this);
		}

		if (result instanceof CommerceRegion) {
			CommerceRegion commerceRegion = (CommerceRegion)result;

			if ((commerceCountryId != commerceRegion.getCommerceCountryId()) ||
				!Objects.equals(code, commerceRegion.getCode())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_SELECT_COMMERCEREGION_WHERE);

			sb.append(_FINDER_COLUMN_C_C_COMMERCECOUNTRYID_2);

			boolean bindCode = false;

			if (code.isEmpty()) {
				sb.append(_FINDER_COLUMN_C_C_CODE_3);
			}
			else {
				bindCode = true;

				sb.append(_FINDER_COLUMN_C_C_CODE_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(commerceCountryId);

				if (bindCode) {
					queryPos.add(code);
				}

				List<CommerceRegion> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache) {
						finderCache.putResult(
							_finderPathFetchByC_C, finderArgs, list);
					}
				}
				else {
					CommerceRegion commerceRegion = list.get(0);

					result = commerceRegion;

					cacheResult(commerceRegion);
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
			return (CommerceRegion)result;
		}
	}

	/**
	 * Removes the commerce region where commerceCountryId = &#63; and code = &#63; from the database.
	 *
	 * @param commerceCountryId the commerce country ID
	 * @param code the code
	 * @return the commerce region that was removed
	 */
	@Override
	public CommerceRegion removeByC_C(long commerceCountryId, String code)
		throws NoSuchRegionException {

		CommerceRegion commerceRegion = findByC_C(commerceCountryId, code);

		return remove(commerceRegion);
	}

	/**
	 * Returns the number of commerce regions where commerceCountryId = &#63; and code = &#63;.
	 *
	 * @param commerceCountryId the commerce country ID
	 * @param code the code
	 * @return the number of matching commerce regions
	 */
	@Override
	public int countByC_C(long commerceCountryId, String code) {
		code = Objects.toString(code, "");

		FinderPath finderPath = _finderPathCountByC_C;

		Object[] finderArgs = new Object[] {commerceCountryId, code};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_COMMERCEREGION_WHERE);

			sb.append(_FINDER_COLUMN_C_C_COMMERCECOUNTRYID_2);

			boolean bindCode = false;

			if (code.isEmpty()) {
				sb.append(_FINDER_COLUMN_C_C_CODE_3);
			}
			else {
				bindCode = true;

				sb.append(_FINDER_COLUMN_C_C_CODE_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(commerceCountryId);

				if (bindCode) {
					queryPos.add(code);
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

	private static final String _FINDER_COLUMN_C_C_COMMERCECOUNTRYID_2 =
		"commerceRegion.commerceCountryId = ? AND ";

	private static final String _FINDER_COLUMN_C_C_CODE_2 =
		"commerceRegion.code = ?";

	private static final String _FINDER_COLUMN_C_C_CODE_3 =
		"(commerceRegion.code IS NULL OR commerceRegion.code = '')";

	private FinderPath _finderPathWithPaginationFindByC_A;
	private FinderPath _finderPathWithoutPaginationFindByC_A;
	private FinderPath _finderPathCountByC_A;

	/**
	 * Returns all the commerce regions where commerceCountryId = &#63; and active = &#63;.
	 *
	 * @param commerceCountryId the commerce country ID
	 * @param active the active
	 * @return the matching commerce regions
	 */
	@Override
	public List<CommerceRegion> findByC_A(
		long commerceCountryId, boolean active) {

		return findByC_A(
			commerceCountryId, active, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the commerce regions where commerceCountryId = &#63; and active = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceRegionModelImpl</code>.
	 * </p>
	 *
	 * @param commerceCountryId the commerce country ID
	 * @param active the active
	 * @param start the lower bound of the range of commerce regions
	 * @param end the upper bound of the range of commerce regions (not inclusive)
	 * @return the range of matching commerce regions
	 */
	@Override
	public List<CommerceRegion> findByC_A(
		long commerceCountryId, boolean active, int start, int end) {

		return findByC_A(commerceCountryId, active, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce regions where commerceCountryId = &#63; and active = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceRegionModelImpl</code>.
	 * </p>
	 *
	 * @param commerceCountryId the commerce country ID
	 * @param active the active
	 * @param start the lower bound of the range of commerce regions
	 * @param end the upper bound of the range of commerce regions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce regions
	 */
	@Override
	public List<CommerceRegion> findByC_A(
		long commerceCountryId, boolean active, int start, int end,
		OrderByComparator<CommerceRegion> orderByComparator) {

		return findByC_A(
			commerceCountryId, active, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce regions where commerceCountryId = &#63; and active = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceRegionModelImpl</code>.
	 * </p>
	 *
	 * @param commerceCountryId the commerce country ID
	 * @param active the active
	 * @param start the lower bound of the range of commerce regions
	 * @param end the upper bound of the range of commerce regions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce regions
	 */
	@Override
	public List<CommerceRegion> findByC_A(
		long commerceCountryId, boolean active, int start, int end,
		OrderByComparator<CommerceRegion> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByC_A;
				finderArgs = new Object[] {commerceCountryId, active};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByC_A;
			finderArgs = new Object[] {
				commerceCountryId, active, start, end, orderByComparator
			};
		}

		List<CommerceRegion> list = null;

		if (useFinderCache) {
			list = (List<CommerceRegion>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (CommerceRegion commerceRegion : list) {
					if ((commerceCountryId !=
							commerceRegion.getCommerceCountryId()) ||
						(active != commerceRegion.isActive())) {

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

			sb.append(_SQL_SELECT_COMMERCEREGION_WHERE);

			sb.append(_FINDER_COLUMN_C_A_COMMERCECOUNTRYID_2);

			sb.append(_FINDER_COLUMN_C_A_ACTIVE_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(CommerceRegionModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(commerceCountryId);

				queryPos.add(active);

				list = (List<CommerceRegion>)QueryUtil.list(
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
	 * Returns the first commerce region in the ordered set where commerceCountryId = &#63; and active = &#63;.
	 *
	 * @param commerceCountryId the commerce country ID
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce region
	 * @throws NoSuchRegionException if a matching commerce region could not be found
	 */
	@Override
	public CommerceRegion findByC_A_First(
			long commerceCountryId, boolean active,
			OrderByComparator<CommerceRegion> orderByComparator)
		throws NoSuchRegionException {

		CommerceRegion commerceRegion = fetchByC_A_First(
			commerceCountryId, active, orderByComparator);

		if (commerceRegion != null) {
			return commerceRegion;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("commerceCountryId=");
		sb.append(commerceCountryId);

		sb.append(", active=");
		sb.append(active);

		sb.append("}");

		throw new NoSuchRegionException(sb.toString());
	}

	/**
	 * Returns the first commerce region in the ordered set where commerceCountryId = &#63; and active = &#63;.
	 *
	 * @param commerceCountryId the commerce country ID
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce region, or <code>null</code> if a matching commerce region could not be found
	 */
	@Override
	public CommerceRegion fetchByC_A_First(
		long commerceCountryId, boolean active,
		OrderByComparator<CommerceRegion> orderByComparator) {

		List<CommerceRegion> list = findByC_A(
			commerceCountryId, active, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last commerce region in the ordered set where commerceCountryId = &#63; and active = &#63;.
	 *
	 * @param commerceCountryId the commerce country ID
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce region
	 * @throws NoSuchRegionException if a matching commerce region could not be found
	 */
	@Override
	public CommerceRegion findByC_A_Last(
			long commerceCountryId, boolean active,
			OrderByComparator<CommerceRegion> orderByComparator)
		throws NoSuchRegionException {

		CommerceRegion commerceRegion = fetchByC_A_Last(
			commerceCountryId, active, orderByComparator);

		if (commerceRegion != null) {
			return commerceRegion;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("commerceCountryId=");
		sb.append(commerceCountryId);

		sb.append(", active=");
		sb.append(active);

		sb.append("}");

		throw new NoSuchRegionException(sb.toString());
	}

	/**
	 * Returns the last commerce region in the ordered set where commerceCountryId = &#63; and active = &#63;.
	 *
	 * @param commerceCountryId the commerce country ID
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce region, or <code>null</code> if a matching commerce region could not be found
	 */
	@Override
	public CommerceRegion fetchByC_A_Last(
		long commerceCountryId, boolean active,
		OrderByComparator<CommerceRegion> orderByComparator) {

		int count = countByC_A(commerceCountryId, active);

		if (count == 0) {
			return null;
		}

		List<CommerceRegion> list = findByC_A(
			commerceCountryId, active, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the commerce regions before and after the current commerce region in the ordered set where commerceCountryId = &#63; and active = &#63;.
	 *
	 * @param commerceRegionId the primary key of the current commerce region
	 * @param commerceCountryId the commerce country ID
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce region
	 * @throws NoSuchRegionException if a commerce region with the primary key could not be found
	 */
	@Override
	public CommerceRegion[] findByC_A_PrevAndNext(
			long commerceRegionId, long commerceCountryId, boolean active,
			OrderByComparator<CommerceRegion> orderByComparator)
		throws NoSuchRegionException {

		CommerceRegion commerceRegion = findByPrimaryKey(commerceRegionId);

		Session session = null;

		try {
			session = openSession();

			CommerceRegion[] array = new CommerceRegionImpl[3];

			array[0] = getByC_A_PrevAndNext(
				session, commerceRegion, commerceCountryId, active,
				orderByComparator, true);

			array[1] = commerceRegion;

			array[2] = getByC_A_PrevAndNext(
				session, commerceRegion, commerceCountryId, active,
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

	protected CommerceRegion getByC_A_PrevAndNext(
		Session session, CommerceRegion commerceRegion, long commerceCountryId,
		boolean active, OrderByComparator<CommerceRegion> orderByComparator,
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

		sb.append(_SQL_SELECT_COMMERCEREGION_WHERE);

		sb.append(_FINDER_COLUMN_C_A_COMMERCECOUNTRYID_2);

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
			sb.append(CommerceRegionModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(commerceCountryId);

		queryPos.add(active);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						commerceRegion)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<CommerceRegion> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the commerce regions where commerceCountryId = &#63; and active = &#63; from the database.
	 *
	 * @param commerceCountryId the commerce country ID
	 * @param active the active
	 */
	@Override
	public void removeByC_A(long commerceCountryId, boolean active) {
		for (CommerceRegion commerceRegion :
				findByC_A(
					commerceCountryId, active, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			remove(commerceRegion);
		}
	}

	/**
	 * Returns the number of commerce regions where commerceCountryId = &#63; and active = &#63;.
	 *
	 * @param commerceCountryId the commerce country ID
	 * @param active the active
	 * @return the number of matching commerce regions
	 */
	@Override
	public int countByC_A(long commerceCountryId, boolean active) {
		FinderPath finderPath = _finderPathCountByC_A;

		Object[] finderArgs = new Object[] {commerceCountryId, active};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_COMMERCEREGION_WHERE);

			sb.append(_FINDER_COLUMN_C_A_COMMERCECOUNTRYID_2);

			sb.append(_FINDER_COLUMN_C_A_ACTIVE_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(commerceCountryId);

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

	private static final String _FINDER_COLUMN_C_A_COMMERCECOUNTRYID_2 =
		"commerceRegion.commerceCountryId = ? AND ";

	private static final String _FINDER_COLUMN_C_A_ACTIVE_2 =
		"commerceRegion.active = ?";

	public CommerceRegionPersistenceImpl() {
		Map<String, String> dbColumnNames = new HashMap<String, String>();

		dbColumnNames.put("uuid", "uuid_");
		dbColumnNames.put("code", "code_");
		dbColumnNames.put("active", "active_");

		setDBColumnNames(dbColumnNames);

		setModelClass(CommerceRegion.class);

		setModelImplClass(CommerceRegionImpl.class);
		setModelPKClass(long.class);

		setTable(CommerceRegionTable.INSTANCE);
	}

	/**
	 * Caches the commerce region in the entity cache if it is enabled.
	 *
	 * @param commerceRegion the commerce region
	 */
	@Override
	public void cacheResult(CommerceRegion commerceRegion) {
		entityCache.putResult(
			CommerceRegionImpl.class, commerceRegion.getPrimaryKey(),
			commerceRegion);

		finderCache.putResult(
			_finderPathFetchByC_C,
			new Object[] {
				commerceRegion.getCommerceCountryId(), commerceRegion.getCode()
			},
			commerceRegion);
	}

	/**
	 * Caches the commerce regions in the entity cache if it is enabled.
	 *
	 * @param commerceRegions the commerce regions
	 */
	@Override
	public void cacheResult(List<CommerceRegion> commerceRegions) {
		for (CommerceRegion commerceRegion : commerceRegions) {
			if (entityCache.getResult(
					CommerceRegionImpl.class, commerceRegion.getPrimaryKey()) ==
						null) {

				cacheResult(commerceRegion);
			}
		}
	}

	/**
	 * Clears the cache for all commerce regions.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(CommerceRegionImpl.class);

		finderCache.clearCache(CommerceRegionImpl.class);
	}

	/**
	 * Clears the cache for the commerce region.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(CommerceRegion commerceRegion) {
		entityCache.removeResult(CommerceRegionImpl.class, commerceRegion);
	}

	@Override
	public void clearCache(List<CommerceRegion> commerceRegions) {
		for (CommerceRegion commerceRegion : commerceRegions) {
			entityCache.removeResult(CommerceRegionImpl.class, commerceRegion);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(CommerceRegionImpl.class);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(CommerceRegionImpl.class, primaryKey);
		}
	}

	protected void cacheUniqueFindersCache(
		CommerceRegionModelImpl commerceRegionModelImpl) {

		Object[] args = new Object[] {
			commerceRegionModelImpl.getCommerceCountryId(),
			commerceRegionModelImpl.getCode()
		};

		finderCache.putResult(
			_finderPathCountByC_C, args, Long.valueOf(1), false);
		finderCache.putResult(
			_finderPathFetchByC_C, args, commerceRegionModelImpl, false);
	}

	/**
	 * Creates a new commerce region with the primary key. Does not add the commerce region to the database.
	 *
	 * @param commerceRegionId the primary key for the new commerce region
	 * @return the new commerce region
	 */
	@Override
	public CommerceRegion create(long commerceRegionId) {
		CommerceRegion commerceRegion = new CommerceRegionImpl();

		commerceRegion.setNew(true);
		commerceRegion.setPrimaryKey(commerceRegionId);

		String uuid = PortalUUIDUtil.generate();

		commerceRegion.setUuid(uuid);

		commerceRegion.setCompanyId(CompanyThreadLocal.getCompanyId());

		return commerceRegion;
	}

	/**
	 * Removes the commerce region with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param commerceRegionId the primary key of the commerce region
	 * @return the commerce region that was removed
	 * @throws NoSuchRegionException if a commerce region with the primary key could not be found
	 */
	@Override
	public CommerceRegion remove(long commerceRegionId)
		throws NoSuchRegionException {

		return remove((Serializable)commerceRegionId);
	}

	/**
	 * Removes the commerce region with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the commerce region
	 * @return the commerce region that was removed
	 * @throws NoSuchRegionException if a commerce region with the primary key could not be found
	 */
	@Override
	public CommerceRegion remove(Serializable primaryKey)
		throws NoSuchRegionException {

		Session session = null;

		try {
			session = openSession();

			CommerceRegion commerceRegion = (CommerceRegion)session.get(
				CommerceRegionImpl.class, primaryKey);

			if (commerceRegion == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchRegionException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(commerceRegion);
		}
		catch (NoSuchRegionException noSuchEntityException) {
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
	protected CommerceRegion removeImpl(CommerceRegion commerceRegion) {
		Session session = null;

		try {
			session = openSession();

			if (!session.contains(commerceRegion)) {
				commerceRegion = (CommerceRegion)session.get(
					CommerceRegionImpl.class,
					commerceRegion.getPrimaryKeyObj());
			}

			if (commerceRegion != null) {
				session.delete(commerceRegion);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (commerceRegion != null) {
			clearCache(commerceRegion);
		}

		return commerceRegion;
	}

	@Override
	public CommerceRegion updateImpl(CommerceRegion commerceRegion) {
		boolean isNew = commerceRegion.isNew();

		if (!(commerceRegion instanceof CommerceRegionModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(commerceRegion.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					commerceRegion);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in commerceRegion proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom CommerceRegion implementation " +
					commerceRegion.getClass());
		}

		CommerceRegionModelImpl commerceRegionModelImpl =
			(CommerceRegionModelImpl)commerceRegion;

		if (Validator.isNull(commerceRegion.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			commerceRegion.setUuid(uuid);
		}

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Date now = new Date();

		if (isNew && (commerceRegion.getCreateDate() == null)) {
			if (serviceContext == null) {
				commerceRegion.setCreateDate(now);
			}
			else {
				commerceRegion.setCreateDate(serviceContext.getCreateDate(now));
			}
		}

		if (!commerceRegionModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				commerceRegion.setModifiedDate(now);
			}
			else {
				commerceRegion.setModifiedDate(
					serviceContext.getModifiedDate(now));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (isNew) {
				session.save(commerceRegion);
			}
			else {
				commerceRegion = (CommerceRegion)session.merge(commerceRegion);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		entityCache.putResult(
			CommerceRegionImpl.class, commerceRegionModelImpl, false, true);

		cacheUniqueFindersCache(commerceRegionModelImpl);

		if (isNew) {
			commerceRegion.setNew(false);
		}

		commerceRegion.resetOriginalValues();

		return commerceRegion;
	}

	/**
	 * Returns the commerce region with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the commerce region
	 * @return the commerce region
	 * @throws NoSuchRegionException if a commerce region with the primary key could not be found
	 */
	@Override
	public CommerceRegion findByPrimaryKey(Serializable primaryKey)
		throws NoSuchRegionException {

		CommerceRegion commerceRegion = fetchByPrimaryKey(primaryKey);

		if (commerceRegion == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchRegionException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return commerceRegion;
	}

	/**
	 * Returns the commerce region with the primary key or throws a <code>NoSuchRegionException</code> if it could not be found.
	 *
	 * @param commerceRegionId the primary key of the commerce region
	 * @return the commerce region
	 * @throws NoSuchRegionException if a commerce region with the primary key could not be found
	 */
	@Override
	public CommerceRegion findByPrimaryKey(long commerceRegionId)
		throws NoSuchRegionException {

		return findByPrimaryKey((Serializable)commerceRegionId);
	}

	/**
	 * Returns the commerce region with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param commerceRegionId the primary key of the commerce region
	 * @return the commerce region, or <code>null</code> if a commerce region with the primary key could not be found
	 */
	@Override
	public CommerceRegion fetchByPrimaryKey(long commerceRegionId) {
		return fetchByPrimaryKey((Serializable)commerceRegionId);
	}

	/**
	 * Returns all the commerce regions.
	 *
	 * @return the commerce regions
	 */
	@Override
	public List<CommerceRegion> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce regions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceRegionModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce regions
	 * @param end the upper bound of the range of commerce regions (not inclusive)
	 * @return the range of commerce regions
	 */
	@Override
	public List<CommerceRegion> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce regions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceRegionModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce regions
	 * @param end the upper bound of the range of commerce regions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of commerce regions
	 */
	@Override
	public List<CommerceRegion> findAll(
		int start, int end,
		OrderByComparator<CommerceRegion> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce regions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceRegionModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce regions
	 * @param end the upper bound of the range of commerce regions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of commerce regions
	 */
	@Override
	public List<CommerceRegion> findAll(
		int start, int end, OrderByComparator<CommerceRegion> orderByComparator,
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

		List<CommerceRegion> list = null;

		if (useFinderCache) {
			list = (List<CommerceRegion>)finderCache.getResult(
				finderPath, finderArgs, this);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_COMMERCEREGION);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_COMMERCEREGION;

				sql = sql.concat(CommerceRegionModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<CommerceRegion>)QueryUtil.list(
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
	 * Removes all the commerce regions from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (CommerceRegion commerceRegion : findAll()) {
			remove(commerceRegion);
		}
	}

	/**
	 * Returns the number of commerce regions.
	 *
	 * @return the number of commerce regions
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(
			_finderPathCountAll, FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(_SQL_COUNT_COMMERCEREGION);

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
		return "commerceRegionId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_COMMERCEREGION;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return CommerceRegionModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the commerce region persistence.
	 */
	public void afterPropertiesSet() {
		Bundle bundle = FrameworkUtil.getBundle(
			CommerceRegionPersistenceImpl.class);

		_bundleContext = bundle.getBundleContext();

		_argumentsResolverServiceRegistration = _bundleContext.registerService(
			ArgumentsResolver.class, new CommerceRegionModelArgumentsResolver(),
			MapUtil.singletonDictionary(
				"model.class.name", CommerceRegion.class.getName()));

		_finderPathWithPaginationFindAll = _createFinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0],
			new String[0], true);

		_finderPathWithoutPaginationFindAll = _createFinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0],
			new String[0], true);

		_finderPathCountAll = _createFinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll",
			new String[0], new String[0], false);

		_finderPathWithPaginationFindByUuid = _createFinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUuid",
			new String[] {
				String.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"uuid_"}, true);

		_finderPathWithoutPaginationFindByUuid = _createFinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid",
			new String[] {String.class.getName()}, new String[] {"uuid_"},
			true);

		_finderPathCountByUuid = _createFinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUuid",
			new String[] {String.class.getName()}, new String[] {"uuid_"},
			false);

		_finderPathWithPaginationFindByUuid_C = _createFinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUuid_C",
			new String[] {
				String.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {"uuid_", "companyId"}, true);

		_finderPathWithoutPaginationFindByUuid_C = _createFinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid_C",
			new String[] {String.class.getName(), Long.class.getName()},
			new String[] {"uuid_", "companyId"}, true);

		_finderPathCountByUuid_C = _createFinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUuid_C",
			new String[] {String.class.getName(), Long.class.getName()},
			new String[] {"uuid_", "companyId"}, false);

		_finderPathWithPaginationFindByCommerceCountryId = _createFinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByCommerceCountryId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"commerceCountryId"}, true);

		_finderPathWithoutPaginationFindByCommerceCountryId = _createFinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByCommerceCountryId", new String[] {Long.class.getName()},
			new String[] {"commerceCountryId"}, true);

		_finderPathCountByCommerceCountryId = _createFinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByCommerceCountryId", new String[] {Long.class.getName()},
			new String[] {"commerceCountryId"}, false);

		_finderPathFetchByC_C = _createFinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByC_C",
			new String[] {Long.class.getName(), String.class.getName()},
			new String[] {"commerceCountryId", "code_"}, true);

		_finderPathCountByC_C = _createFinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_C",
			new String[] {Long.class.getName(), String.class.getName()},
			new String[] {"commerceCountryId", "code_"}, false);

		_finderPathWithPaginationFindByC_A = _createFinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByC_A",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {"commerceCountryId", "active_"}, true);

		_finderPathWithoutPaginationFindByC_A = _createFinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByC_A",
			new String[] {Long.class.getName(), Boolean.class.getName()},
			new String[] {"commerceCountryId", "active_"}, true);

		_finderPathCountByC_A = _createFinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_A",
			new String[] {Long.class.getName(), Boolean.class.getName()},
			new String[] {"commerceCountryId", "active_"}, false);
	}

	public void destroy() {
		entityCache.removeCache(CommerceRegionImpl.class.getName());

		_argumentsResolverServiceRegistration.unregister();

		for (ServiceRegistration<FinderPath> serviceRegistration :
				_serviceRegistrations) {

			serviceRegistration.unregister();
		}
	}

	private BundleContext _bundleContext;

	@ServiceReference(type = EntityCache.class)
	protected EntityCache entityCache;

	@ServiceReference(type = FinderCache.class)
	protected FinderCache finderCache;

	private static final String _SQL_SELECT_COMMERCEREGION =
		"SELECT commerceRegion FROM CommerceRegion commerceRegion";

	private static final String _SQL_SELECT_COMMERCEREGION_WHERE =
		"SELECT commerceRegion FROM CommerceRegion commerceRegion WHERE ";

	private static final String _SQL_COUNT_COMMERCEREGION =
		"SELECT COUNT(commerceRegion) FROM CommerceRegion commerceRegion";

	private static final String _SQL_COUNT_COMMERCEREGION_WHERE =
		"SELECT COUNT(commerceRegion) FROM CommerceRegion commerceRegion WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS = "commerceRegion.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No CommerceRegion exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No CommerceRegion exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		CommerceRegionPersistenceImpl.class);

	private static final Set<String> _badColumnNames = SetUtil.fromArray(
		new String[] {"uuid", "code", "active"});

	private FinderPath _createFinderPath(
		String cacheName, String methodName, String[] params,
		String[] columnNames, boolean baseModelResult) {

		FinderPath finderPath = new FinderPath(
			cacheName, methodName, params, columnNames, baseModelResult);

		if (!cacheName.equals(FINDER_CLASS_NAME_LIST_WITH_PAGINATION)) {
			_serviceRegistrations.add(
				_bundleContext.registerService(
					FinderPath.class, finderPath,
					MapUtil.singletonDictionary("cache.name", cacheName)));
		}

		return finderPath;
	}

	private ServiceRegistration<ArgumentsResolver>
		_argumentsResolverServiceRegistration;
	private Set<ServiceRegistration<FinderPath>> _serviceRegistrations =
		new HashSet<>();

	private static class CommerceRegionModelArgumentsResolver
		implements ArgumentsResolver {

		@Override
		public Object[] getArguments(
			FinderPath finderPath, BaseModel<?> baseModel, boolean checkColumn,
			boolean original) {

			String[] columnNames = finderPath.getColumnNames();

			if ((columnNames == null) || (columnNames.length == 0)) {
				if (baseModel.isNew()) {
					return FINDER_ARGS_EMPTY;
				}

				return null;
			}

			CommerceRegionModelImpl commerceRegionModelImpl =
				(CommerceRegionModelImpl)baseModel;

			long columnBitmask = commerceRegionModelImpl.getColumnBitmask();

			if (!checkColumn || (columnBitmask == 0)) {
				return _getValue(
					commerceRegionModelImpl, columnNames, original);
			}

			Long finderPathColumnBitmask = _finderPathColumnBitmasksCache.get(
				finderPath);

			if (finderPathColumnBitmask == null) {
				finderPathColumnBitmask = 0L;

				for (String columnName : columnNames) {
					finderPathColumnBitmask |=
						commerceRegionModelImpl.getColumnBitmask(columnName);
				}

				_finderPathColumnBitmasksCache.put(
					finderPath, finderPathColumnBitmask);
			}

			if ((columnBitmask & finderPathColumnBitmask) != 0) {
				return _getValue(
					commerceRegionModelImpl, columnNames, original);
			}

			return null;
		}

		private Object[] _getValue(
			CommerceRegionModelImpl commerceRegionModelImpl,
			String[] columnNames, boolean original) {

			Object[] arguments = new Object[columnNames.length];

			for (int i = 0; i < arguments.length; i++) {
				String columnName = columnNames[i];

				if (original) {
					arguments[i] =
						commerceRegionModelImpl.getColumnOriginalValue(
							columnName);
				}
				else {
					arguments[i] = commerceRegionModelImpl.getColumnValue(
						columnName);
				}
			}

			return arguments;
		}

		private static Map<FinderPath, Long> _finderPathColumnBitmasksCache =
			new ConcurrentHashMap<>();

	}

}