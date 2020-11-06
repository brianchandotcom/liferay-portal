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

import com.liferay.commerce.exception.NoSuchCountryException;
import com.liferay.commerce.model.CommerceCountry;
import com.liferay.commerce.model.CommerceCountryTable;
import com.liferay.commerce.model.impl.CommerceCountryImpl;
import com.liferay.commerce.model.impl.CommerceCountryModelImpl;
import com.liferay.commerce.service.persistence.CommerceCountryPersistence;
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
import com.liferay.portal.kernel.util.HashMapDictionary;
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
 * The persistence implementation for the commerce country service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Alessio Antonio Rendina
 * @generated
 */
public class CommerceCountryPersistenceImpl
	extends BasePersistenceImpl<CommerceCountry>
	implements CommerceCountryPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>CommerceCountryUtil</code> to access the commerce country persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		CommerceCountryImpl.class.getName();

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
	 * Returns all the commerce countries where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching commerce countries
	 */
	@Override
	public List<CommerceCountry> findByUuid(String uuid) {
		return findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce countries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceCountryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of commerce countries
	 * @param end the upper bound of the range of commerce countries (not inclusive)
	 * @return the range of matching commerce countries
	 */
	@Override
	public List<CommerceCountry> findByUuid(String uuid, int start, int end) {
		return findByUuid(uuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce countries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceCountryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of commerce countries
	 * @param end the upper bound of the range of commerce countries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce countries
	 */
	@Override
	public List<CommerceCountry> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<CommerceCountry> orderByComparator) {

		return findByUuid(uuid, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce countries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceCountryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of commerce countries
	 * @param end the upper bound of the range of commerce countries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce countries
	 */
	@Override
	public List<CommerceCountry> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<CommerceCountry> orderByComparator,
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

		List<CommerceCountry> list = null;

		if (useFinderCache) {
			list = (List<CommerceCountry>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (CommerceCountry commerceCountry : list) {
					if (!uuid.equals(commerceCountry.getUuid())) {
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

			sb.append(_SQL_SELECT_COMMERCECOUNTRY_WHERE);

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
				sb.append(CommerceCountryModelImpl.ORDER_BY_JPQL);
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

				list = (List<CommerceCountry>)QueryUtil.list(
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
	 * Returns the first commerce country in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce country
	 * @throws NoSuchCountryException if a matching commerce country could not be found
	 */
	@Override
	public CommerceCountry findByUuid_First(
			String uuid, OrderByComparator<CommerceCountry> orderByComparator)
		throws NoSuchCountryException {

		CommerceCountry commerceCountry = fetchByUuid_First(
			uuid, orderByComparator);

		if (commerceCountry != null) {
			return commerceCountry;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append("}");

		throw new NoSuchCountryException(sb.toString());
	}

	/**
	 * Returns the first commerce country in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce country, or <code>null</code> if a matching commerce country could not be found
	 */
	@Override
	public CommerceCountry fetchByUuid_First(
		String uuid, OrderByComparator<CommerceCountry> orderByComparator) {

		List<CommerceCountry> list = findByUuid(uuid, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last commerce country in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce country
	 * @throws NoSuchCountryException if a matching commerce country could not be found
	 */
	@Override
	public CommerceCountry findByUuid_Last(
			String uuid, OrderByComparator<CommerceCountry> orderByComparator)
		throws NoSuchCountryException {

		CommerceCountry commerceCountry = fetchByUuid_Last(
			uuid, orderByComparator);

		if (commerceCountry != null) {
			return commerceCountry;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append("}");

		throw new NoSuchCountryException(sb.toString());
	}

	/**
	 * Returns the last commerce country in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce country, or <code>null</code> if a matching commerce country could not be found
	 */
	@Override
	public CommerceCountry fetchByUuid_Last(
		String uuid, OrderByComparator<CommerceCountry> orderByComparator) {

		int count = countByUuid(uuid);

		if (count == 0) {
			return null;
		}

		List<CommerceCountry> list = findByUuid(
			uuid, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the commerce countries before and after the current commerce country in the ordered set where uuid = &#63;.
	 *
	 * @param commerceCountryId the primary key of the current commerce country
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce country
	 * @throws NoSuchCountryException if a commerce country with the primary key could not be found
	 */
	@Override
	public CommerceCountry[] findByUuid_PrevAndNext(
			long commerceCountryId, String uuid,
			OrderByComparator<CommerceCountry> orderByComparator)
		throws NoSuchCountryException {

		uuid = Objects.toString(uuid, "");

		CommerceCountry commerceCountry = findByPrimaryKey(commerceCountryId);

		Session session = null;

		try {
			session = openSession();

			CommerceCountry[] array = new CommerceCountryImpl[3];

			array[0] = getByUuid_PrevAndNext(
				session, commerceCountry, uuid, orderByComparator, true);

			array[1] = commerceCountry;

			array[2] = getByUuid_PrevAndNext(
				session, commerceCountry, uuid, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected CommerceCountry getByUuid_PrevAndNext(
		Session session, CommerceCountry commerceCountry, String uuid,
		OrderByComparator<CommerceCountry> orderByComparator,
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

		sb.append(_SQL_SELECT_COMMERCECOUNTRY_WHERE);

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
			sb.append(CommerceCountryModelImpl.ORDER_BY_JPQL);
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
						commerceCountry)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<CommerceCountry> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the commerce countries where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	@Override
	public void removeByUuid(String uuid) {
		for (CommerceCountry commerceCountry :
				findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(commerceCountry);
		}
	}

	/**
	 * Returns the number of commerce countries where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching commerce countries
	 */
	@Override
	public int countByUuid(String uuid) {
		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = _finderPathCountByUuid;

		Object[] finderArgs = new Object[] {uuid};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_COMMERCECOUNTRY_WHERE);

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
		"commerceCountry.uuid = ?";

	private static final String _FINDER_COLUMN_UUID_UUID_3 =
		"(commerceCountry.uuid IS NULL OR commerceCountry.uuid = '')";

	private FinderPath _finderPathWithPaginationFindByUuid_C;
	private FinderPath _finderPathWithoutPaginationFindByUuid_C;
	private FinderPath _finderPathCountByUuid_C;

	/**
	 * Returns all the commerce countries where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching commerce countries
	 */
	@Override
	public List<CommerceCountry> findByUuid_C(String uuid, long companyId) {
		return findByUuid_C(
			uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce countries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceCountryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce countries
	 * @param end the upper bound of the range of commerce countries (not inclusive)
	 * @return the range of matching commerce countries
	 */
	@Override
	public List<CommerceCountry> findByUuid_C(
		String uuid, long companyId, int start, int end) {

		return findByUuid_C(uuid, companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce countries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceCountryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce countries
	 * @param end the upper bound of the range of commerce countries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce countries
	 */
	@Override
	public List<CommerceCountry> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<CommerceCountry> orderByComparator) {

		return findByUuid_C(
			uuid, companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce countries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceCountryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce countries
	 * @param end the upper bound of the range of commerce countries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce countries
	 */
	@Override
	public List<CommerceCountry> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<CommerceCountry> orderByComparator,
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

		List<CommerceCountry> list = null;

		if (useFinderCache) {
			list = (List<CommerceCountry>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (CommerceCountry commerceCountry : list) {
					if (!uuid.equals(commerceCountry.getUuid()) ||
						(companyId != commerceCountry.getCompanyId())) {

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

			sb.append(_SQL_SELECT_COMMERCECOUNTRY_WHERE);

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
				sb.append(CommerceCountryModelImpl.ORDER_BY_JPQL);
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

				list = (List<CommerceCountry>)QueryUtil.list(
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
	 * Returns the first commerce country in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce country
	 * @throws NoSuchCountryException if a matching commerce country could not be found
	 */
	@Override
	public CommerceCountry findByUuid_C_First(
			String uuid, long companyId,
			OrderByComparator<CommerceCountry> orderByComparator)
		throws NoSuchCountryException {

		CommerceCountry commerceCountry = fetchByUuid_C_First(
			uuid, companyId, orderByComparator);

		if (commerceCountry != null) {
			return commerceCountry;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append(", companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchCountryException(sb.toString());
	}

	/**
	 * Returns the first commerce country in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce country, or <code>null</code> if a matching commerce country could not be found
	 */
	@Override
	public CommerceCountry fetchByUuid_C_First(
		String uuid, long companyId,
		OrderByComparator<CommerceCountry> orderByComparator) {

		List<CommerceCountry> list = findByUuid_C(
			uuid, companyId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last commerce country in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce country
	 * @throws NoSuchCountryException if a matching commerce country could not be found
	 */
	@Override
	public CommerceCountry findByUuid_C_Last(
			String uuid, long companyId,
			OrderByComparator<CommerceCountry> orderByComparator)
		throws NoSuchCountryException {

		CommerceCountry commerceCountry = fetchByUuid_C_Last(
			uuid, companyId, orderByComparator);

		if (commerceCountry != null) {
			return commerceCountry;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append(", companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchCountryException(sb.toString());
	}

	/**
	 * Returns the last commerce country in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce country, or <code>null</code> if a matching commerce country could not be found
	 */
	@Override
	public CommerceCountry fetchByUuid_C_Last(
		String uuid, long companyId,
		OrderByComparator<CommerceCountry> orderByComparator) {

		int count = countByUuid_C(uuid, companyId);

		if (count == 0) {
			return null;
		}

		List<CommerceCountry> list = findByUuid_C(
			uuid, companyId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the commerce countries before and after the current commerce country in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param commerceCountryId the primary key of the current commerce country
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce country
	 * @throws NoSuchCountryException if a commerce country with the primary key could not be found
	 */
	@Override
	public CommerceCountry[] findByUuid_C_PrevAndNext(
			long commerceCountryId, String uuid, long companyId,
			OrderByComparator<CommerceCountry> orderByComparator)
		throws NoSuchCountryException {

		uuid = Objects.toString(uuid, "");

		CommerceCountry commerceCountry = findByPrimaryKey(commerceCountryId);

		Session session = null;

		try {
			session = openSession();

			CommerceCountry[] array = new CommerceCountryImpl[3];

			array[0] = getByUuid_C_PrevAndNext(
				session, commerceCountry, uuid, companyId, orderByComparator,
				true);

			array[1] = commerceCountry;

			array[2] = getByUuid_C_PrevAndNext(
				session, commerceCountry, uuid, companyId, orderByComparator,
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

	protected CommerceCountry getByUuid_C_PrevAndNext(
		Session session, CommerceCountry commerceCountry, String uuid,
		long companyId, OrderByComparator<CommerceCountry> orderByComparator,
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

		sb.append(_SQL_SELECT_COMMERCECOUNTRY_WHERE);

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
			sb.append(CommerceCountryModelImpl.ORDER_BY_JPQL);
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
						commerceCountry)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<CommerceCountry> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the commerce countries where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	@Override
	public void removeByUuid_C(String uuid, long companyId) {
		for (CommerceCountry commerceCountry :
				findByUuid_C(
					uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(commerceCountry);
		}
	}

	/**
	 * Returns the number of commerce countries where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching commerce countries
	 */
	@Override
	public int countByUuid_C(String uuid, long companyId) {
		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = _finderPathCountByUuid_C;

		Object[] finderArgs = new Object[] {uuid, companyId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_COMMERCECOUNTRY_WHERE);

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
		"commerceCountry.uuid = ? AND ";

	private static final String _FINDER_COLUMN_UUID_C_UUID_3 =
		"(commerceCountry.uuid IS NULL OR commerceCountry.uuid = '') AND ";

	private static final String _FINDER_COLUMN_UUID_C_COMPANYID_2 =
		"commerceCountry.companyId = ?";

	private FinderPath _finderPathWithPaginationFindByCompanyId;
	private FinderPath _finderPathWithoutPaginationFindByCompanyId;
	private FinderPath _finderPathCountByCompanyId;

	/**
	 * Returns all the commerce countries where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching commerce countries
	 */
	@Override
	public List<CommerceCountry> findByCompanyId(long companyId) {
		return findByCompanyId(
			companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce countries where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceCountryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce countries
	 * @param end the upper bound of the range of commerce countries (not inclusive)
	 * @return the range of matching commerce countries
	 */
	@Override
	public List<CommerceCountry> findByCompanyId(
		long companyId, int start, int end) {

		return findByCompanyId(companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce countries where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceCountryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce countries
	 * @param end the upper bound of the range of commerce countries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce countries
	 */
	@Override
	public List<CommerceCountry> findByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<CommerceCountry> orderByComparator) {

		return findByCompanyId(companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce countries where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceCountryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce countries
	 * @param end the upper bound of the range of commerce countries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce countries
	 */
	@Override
	public List<CommerceCountry> findByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<CommerceCountry> orderByComparator,
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

		List<CommerceCountry> list = null;

		if (useFinderCache) {
			list = (List<CommerceCountry>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (CommerceCountry commerceCountry : list) {
					if (companyId != commerceCountry.getCompanyId()) {
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

			sb.append(_SQL_SELECT_COMMERCECOUNTRY_WHERE);

			sb.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(CommerceCountryModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(companyId);

				list = (List<CommerceCountry>)QueryUtil.list(
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
	 * Returns the first commerce country in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce country
	 * @throws NoSuchCountryException if a matching commerce country could not be found
	 */
	@Override
	public CommerceCountry findByCompanyId_First(
			long companyId,
			OrderByComparator<CommerceCountry> orderByComparator)
		throws NoSuchCountryException {

		CommerceCountry commerceCountry = fetchByCompanyId_First(
			companyId, orderByComparator);

		if (commerceCountry != null) {
			return commerceCountry;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchCountryException(sb.toString());
	}

	/**
	 * Returns the first commerce country in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce country, or <code>null</code> if a matching commerce country could not be found
	 */
	@Override
	public CommerceCountry fetchByCompanyId_First(
		long companyId, OrderByComparator<CommerceCountry> orderByComparator) {

		List<CommerceCountry> list = findByCompanyId(
			companyId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last commerce country in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce country
	 * @throws NoSuchCountryException if a matching commerce country could not be found
	 */
	@Override
	public CommerceCountry findByCompanyId_Last(
			long companyId,
			OrderByComparator<CommerceCountry> orderByComparator)
		throws NoSuchCountryException {

		CommerceCountry commerceCountry = fetchByCompanyId_Last(
			companyId, orderByComparator);

		if (commerceCountry != null) {
			return commerceCountry;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchCountryException(sb.toString());
	}

	/**
	 * Returns the last commerce country in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce country, or <code>null</code> if a matching commerce country could not be found
	 */
	@Override
	public CommerceCountry fetchByCompanyId_Last(
		long companyId, OrderByComparator<CommerceCountry> orderByComparator) {

		int count = countByCompanyId(companyId);

		if (count == 0) {
			return null;
		}

		List<CommerceCountry> list = findByCompanyId(
			companyId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the commerce countries before and after the current commerce country in the ordered set where companyId = &#63;.
	 *
	 * @param commerceCountryId the primary key of the current commerce country
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce country
	 * @throws NoSuchCountryException if a commerce country with the primary key could not be found
	 */
	@Override
	public CommerceCountry[] findByCompanyId_PrevAndNext(
			long commerceCountryId, long companyId,
			OrderByComparator<CommerceCountry> orderByComparator)
		throws NoSuchCountryException {

		CommerceCountry commerceCountry = findByPrimaryKey(commerceCountryId);

		Session session = null;

		try {
			session = openSession();

			CommerceCountry[] array = new CommerceCountryImpl[3];

			array[0] = getByCompanyId_PrevAndNext(
				session, commerceCountry, companyId, orderByComparator, true);

			array[1] = commerceCountry;

			array[2] = getByCompanyId_PrevAndNext(
				session, commerceCountry, companyId, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected CommerceCountry getByCompanyId_PrevAndNext(
		Session session, CommerceCountry commerceCountry, long companyId,
		OrderByComparator<CommerceCountry> orderByComparator,
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

		sb.append(_SQL_SELECT_COMMERCECOUNTRY_WHERE);

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
			sb.append(CommerceCountryModelImpl.ORDER_BY_JPQL);
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
						commerceCountry)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<CommerceCountry> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the commerce countries where companyId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 */
	@Override
	public void removeByCompanyId(long companyId) {
		for (CommerceCountry commerceCountry :
				findByCompanyId(
					companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(commerceCountry);
		}
	}

	/**
	 * Returns the number of commerce countries where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching commerce countries
	 */
	@Override
	public int countByCompanyId(long companyId) {
		FinderPath finderPath = _finderPathCountByCompanyId;

		Object[] finderArgs = new Object[] {companyId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_COMMERCECOUNTRY_WHERE);

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
		"commerceCountry.companyId = ?";

	private FinderPath _finderPathFetchByC_Tw;
	private FinderPath _finderPathCountByC_Tw;

	/**
	 * Returns the commerce country where companyId = &#63; and twoLettersISOCode = &#63; or throws a <code>NoSuchCountryException</code> if it could not be found.
	 *
	 * @param companyId the company ID
	 * @param twoLettersISOCode the two letters iso code
	 * @return the matching commerce country
	 * @throws NoSuchCountryException if a matching commerce country could not be found
	 */
	@Override
	public CommerceCountry findByC_Tw(long companyId, String twoLettersISOCode)
		throws NoSuchCountryException {

		CommerceCountry commerceCountry = fetchByC_Tw(
			companyId, twoLettersISOCode);

		if (commerceCountry == null) {
			StringBundler sb = new StringBundler(6);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("companyId=");
			sb.append(companyId);

			sb.append(", twoLettersISOCode=");
			sb.append(twoLettersISOCode);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchCountryException(sb.toString());
		}

		return commerceCountry;
	}

	/**
	 * Returns the commerce country where companyId = &#63; and twoLettersISOCode = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param companyId the company ID
	 * @param twoLettersISOCode the two letters iso code
	 * @return the matching commerce country, or <code>null</code> if a matching commerce country could not be found
	 */
	@Override
	public CommerceCountry fetchByC_Tw(
		long companyId, String twoLettersISOCode) {

		return fetchByC_Tw(companyId, twoLettersISOCode, true);
	}

	/**
	 * Returns the commerce country where companyId = &#63; and twoLettersISOCode = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param companyId the company ID
	 * @param twoLettersISOCode the two letters iso code
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching commerce country, or <code>null</code> if a matching commerce country could not be found
	 */
	@Override
	public CommerceCountry fetchByC_Tw(
		long companyId, String twoLettersISOCode, boolean useFinderCache) {

		twoLettersISOCode = Objects.toString(twoLettersISOCode, "");

		Object[] finderArgs = null;

		if (useFinderCache) {
			finderArgs = new Object[] {companyId, twoLettersISOCode};
		}

		Object result = null;

		if (useFinderCache) {
			result = finderCache.getResult(_finderPathFetchByC_Tw, finderArgs);
		}

		if (result instanceof CommerceCountry) {
			CommerceCountry commerceCountry = (CommerceCountry)result;

			if ((companyId != commerceCountry.getCompanyId()) ||
				!Objects.equals(
					twoLettersISOCode,
					commerceCountry.getTwoLettersISOCode())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_SELECT_COMMERCECOUNTRY_WHERE);

			sb.append(_FINDER_COLUMN_C_TW_COMPANYID_2);

			boolean bindTwoLettersISOCode = false;

			if (twoLettersISOCode.isEmpty()) {
				sb.append(_FINDER_COLUMN_C_TW_TWOLETTERSISOCODE_3);
			}
			else {
				bindTwoLettersISOCode = true;

				sb.append(_FINDER_COLUMN_C_TW_TWOLETTERSISOCODE_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(companyId);

				if (bindTwoLettersISOCode) {
					queryPos.add(twoLettersISOCode);
				}

				List<CommerceCountry> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache) {
						finderCache.putResult(
							_finderPathFetchByC_Tw, finderArgs, list);
					}
				}
				else {
					CommerceCountry commerceCountry = list.get(0);

					result = commerceCountry;

					cacheResult(commerceCountry);
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
			return (CommerceCountry)result;
		}
	}

	/**
	 * Removes the commerce country where companyId = &#63; and twoLettersISOCode = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param twoLettersISOCode the two letters iso code
	 * @return the commerce country that was removed
	 */
	@Override
	public CommerceCountry removeByC_Tw(
			long companyId, String twoLettersISOCode)
		throws NoSuchCountryException {

		CommerceCountry commerceCountry = findByC_Tw(
			companyId, twoLettersISOCode);

		return remove(commerceCountry);
	}

	/**
	 * Returns the number of commerce countries where companyId = &#63; and twoLettersISOCode = &#63;.
	 *
	 * @param companyId the company ID
	 * @param twoLettersISOCode the two letters iso code
	 * @return the number of matching commerce countries
	 */
	@Override
	public int countByC_Tw(long companyId, String twoLettersISOCode) {
		twoLettersISOCode = Objects.toString(twoLettersISOCode, "");

		FinderPath finderPath = _finderPathCountByC_Tw;

		Object[] finderArgs = new Object[] {companyId, twoLettersISOCode};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_COMMERCECOUNTRY_WHERE);

			sb.append(_FINDER_COLUMN_C_TW_COMPANYID_2);

			boolean bindTwoLettersISOCode = false;

			if (twoLettersISOCode.isEmpty()) {
				sb.append(_FINDER_COLUMN_C_TW_TWOLETTERSISOCODE_3);
			}
			else {
				bindTwoLettersISOCode = true;

				sb.append(_FINDER_COLUMN_C_TW_TWOLETTERSISOCODE_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(companyId);

				if (bindTwoLettersISOCode) {
					queryPos.add(twoLettersISOCode);
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

	private static final String _FINDER_COLUMN_C_TW_COMPANYID_2 =
		"commerceCountry.companyId = ? AND ";

	private static final String _FINDER_COLUMN_C_TW_TWOLETTERSISOCODE_2 =
		"commerceCountry.twoLettersISOCode = ?";

	private static final String _FINDER_COLUMN_C_TW_TWOLETTERSISOCODE_3 =
		"(commerceCountry.twoLettersISOCode IS NULL OR commerceCountry.twoLettersISOCode = '')";

	private FinderPath _finderPathFetchByC_N;
	private FinderPath _finderPathCountByC_N;

	/**
	 * Returns the commerce country where companyId = &#63; and numericISOCode = &#63; or throws a <code>NoSuchCountryException</code> if it could not be found.
	 *
	 * @param companyId the company ID
	 * @param numericISOCode the numeric iso code
	 * @return the matching commerce country
	 * @throws NoSuchCountryException if a matching commerce country could not be found
	 */
	@Override
	public CommerceCountry findByC_N(long companyId, int numericISOCode)
		throws NoSuchCountryException {

		CommerceCountry commerceCountry = fetchByC_N(companyId, numericISOCode);

		if (commerceCountry == null) {
			StringBundler sb = new StringBundler(6);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("companyId=");
			sb.append(companyId);

			sb.append(", numericISOCode=");
			sb.append(numericISOCode);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchCountryException(sb.toString());
		}

		return commerceCountry;
	}

	/**
	 * Returns the commerce country where companyId = &#63; and numericISOCode = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param companyId the company ID
	 * @param numericISOCode the numeric iso code
	 * @return the matching commerce country, or <code>null</code> if a matching commerce country could not be found
	 */
	@Override
	public CommerceCountry fetchByC_N(long companyId, int numericISOCode) {
		return fetchByC_N(companyId, numericISOCode, true);
	}

	/**
	 * Returns the commerce country where companyId = &#63; and numericISOCode = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param companyId the company ID
	 * @param numericISOCode the numeric iso code
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching commerce country, or <code>null</code> if a matching commerce country could not be found
	 */
	@Override
	public CommerceCountry fetchByC_N(
		long companyId, int numericISOCode, boolean useFinderCache) {

		Object[] finderArgs = null;

		if (useFinderCache) {
			finderArgs = new Object[] {companyId, numericISOCode};
		}

		Object result = null;

		if (useFinderCache) {
			result = finderCache.getResult(_finderPathFetchByC_N, finderArgs);
		}

		if (result instanceof CommerceCountry) {
			CommerceCountry commerceCountry = (CommerceCountry)result;

			if ((companyId != commerceCountry.getCompanyId()) ||
				(numericISOCode != commerceCountry.getNumericISOCode())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_SELECT_COMMERCECOUNTRY_WHERE);

			sb.append(_FINDER_COLUMN_C_N_COMPANYID_2);

			sb.append(_FINDER_COLUMN_C_N_NUMERICISOCODE_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(companyId);

				queryPos.add(numericISOCode);

				List<CommerceCountry> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache) {
						finderCache.putResult(
							_finderPathFetchByC_N, finderArgs, list);
					}
				}
				else {
					CommerceCountry commerceCountry = list.get(0);

					result = commerceCountry;

					cacheResult(commerceCountry);
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
			return (CommerceCountry)result;
		}
	}

	/**
	 * Removes the commerce country where companyId = &#63; and numericISOCode = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param numericISOCode the numeric iso code
	 * @return the commerce country that was removed
	 */
	@Override
	public CommerceCountry removeByC_N(long companyId, int numericISOCode)
		throws NoSuchCountryException {

		CommerceCountry commerceCountry = findByC_N(companyId, numericISOCode);

		return remove(commerceCountry);
	}

	/**
	 * Returns the number of commerce countries where companyId = &#63; and numericISOCode = &#63;.
	 *
	 * @param companyId the company ID
	 * @param numericISOCode the numeric iso code
	 * @return the number of matching commerce countries
	 */
	@Override
	public int countByC_N(long companyId, int numericISOCode) {
		FinderPath finderPath = _finderPathCountByC_N;

		Object[] finderArgs = new Object[] {companyId, numericISOCode};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_COMMERCECOUNTRY_WHERE);

			sb.append(_FINDER_COLUMN_C_N_COMPANYID_2);

			sb.append(_FINDER_COLUMN_C_N_NUMERICISOCODE_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(companyId);

				queryPos.add(numericISOCode);

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
		"commerceCountry.companyId = ? AND ";

	private static final String _FINDER_COLUMN_C_N_NUMERICISOCODE_2 =
		"commerceCountry.numericISOCode = ?";

	private FinderPath _finderPathWithPaginationFindByC_A;
	private FinderPath _finderPathWithoutPaginationFindByC_A;
	private FinderPath _finderPathCountByC_A;

	/**
	 * Returns all the commerce countries where companyId = &#63; and active = &#63;.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @return the matching commerce countries
	 */
	@Override
	public List<CommerceCountry> findByC_A(long companyId, boolean active) {
		return findByC_A(
			companyId, active, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce countries where companyId = &#63; and active = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceCountryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param start the lower bound of the range of commerce countries
	 * @param end the upper bound of the range of commerce countries (not inclusive)
	 * @return the range of matching commerce countries
	 */
	@Override
	public List<CommerceCountry> findByC_A(
		long companyId, boolean active, int start, int end) {

		return findByC_A(companyId, active, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce countries where companyId = &#63; and active = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceCountryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param start the lower bound of the range of commerce countries
	 * @param end the upper bound of the range of commerce countries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce countries
	 */
	@Override
	public List<CommerceCountry> findByC_A(
		long companyId, boolean active, int start, int end,
		OrderByComparator<CommerceCountry> orderByComparator) {

		return findByC_A(
			companyId, active, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce countries where companyId = &#63; and active = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceCountryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param start the lower bound of the range of commerce countries
	 * @param end the upper bound of the range of commerce countries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce countries
	 */
	@Override
	public List<CommerceCountry> findByC_A(
		long companyId, boolean active, int start, int end,
		OrderByComparator<CommerceCountry> orderByComparator,
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

		List<CommerceCountry> list = null;

		if (useFinderCache) {
			list = (List<CommerceCountry>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (CommerceCountry commerceCountry : list) {
					if ((companyId != commerceCountry.getCompanyId()) ||
						(active != commerceCountry.isActive())) {

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

			sb.append(_SQL_SELECT_COMMERCECOUNTRY_WHERE);

			sb.append(_FINDER_COLUMN_C_A_COMPANYID_2);

			sb.append(_FINDER_COLUMN_C_A_ACTIVE_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(CommerceCountryModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(companyId);

				queryPos.add(active);

				list = (List<CommerceCountry>)QueryUtil.list(
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
	 * Returns the first commerce country in the ordered set where companyId = &#63; and active = &#63;.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce country
	 * @throws NoSuchCountryException if a matching commerce country could not be found
	 */
	@Override
	public CommerceCountry findByC_A_First(
			long companyId, boolean active,
			OrderByComparator<CommerceCountry> orderByComparator)
		throws NoSuchCountryException {

		CommerceCountry commerceCountry = fetchByC_A_First(
			companyId, active, orderByComparator);

		if (commerceCountry != null) {
			return commerceCountry;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("companyId=");
		sb.append(companyId);

		sb.append(", active=");
		sb.append(active);

		sb.append("}");

		throw new NoSuchCountryException(sb.toString());
	}

	/**
	 * Returns the first commerce country in the ordered set where companyId = &#63; and active = &#63;.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce country, or <code>null</code> if a matching commerce country could not be found
	 */
	@Override
	public CommerceCountry fetchByC_A_First(
		long companyId, boolean active,
		OrderByComparator<CommerceCountry> orderByComparator) {

		List<CommerceCountry> list = findByC_A(
			companyId, active, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last commerce country in the ordered set where companyId = &#63; and active = &#63;.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce country
	 * @throws NoSuchCountryException if a matching commerce country could not be found
	 */
	@Override
	public CommerceCountry findByC_A_Last(
			long companyId, boolean active,
			OrderByComparator<CommerceCountry> orderByComparator)
		throws NoSuchCountryException {

		CommerceCountry commerceCountry = fetchByC_A_Last(
			companyId, active, orderByComparator);

		if (commerceCountry != null) {
			return commerceCountry;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("companyId=");
		sb.append(companyId);

		sb.append(", active=");
		sb.append(active);

		sb.append("}");

		throw new NoSuchCountryException(sb.toString());
	}

	/**
	 * Returns the last commerce country in the ordered set where companyId = &#63; and active = &#63;.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce country, or <code>null</code> if a matching commerce country could not be found
	 */
	@Override
	public CommerceCountry fetchByC_A_Last(
		long companyId, boolean active,
		OrderByComparator<CommerceCountry> orderByComparator) {

		int count = countByC_A(companyId, active);

		if (count == 0) {
			return null;
		}

		List<CommerceCountry> list = findByC_A(
			companyId, active, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the commerce countries before and after the current commerce country in the ordered set where companyId = &#63; and active = &#63;.
	 *
	 * @param commerceCountryId the primary key of the current commerce country
	 * @param companyId the company ID
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce country
	 * @throws NoSuchCountryException if a commerce country with the primary key could not be found
	 */
	@Override
	public CommerceCountry[] findByC_A_PrevAndNext(
			long commerceCountryId, long companyId, boolean active,
			OrderByComparator<CommerceCountry> orderByComparator)
		throws NoSuchCountryException {

		CommerceCountry commerceCountry = findByPrimaryKey(commerceCountryId);

		Session session = null;

		try {
			session = openSession();

			CommerceCountry[] array = new CommerceCountryImpl[3];

			array[0] = getByC_A_PrevAndNext(
				session, commerceCountry, companyId, active, orderByComparator,
				true);

			array[1] = commerceCountry;

			array[2] = getByC_A_PrevAndNext(
				session, commerceCountry, companyId, active, orderByComparator,
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

	protected CommerceCountry getByC_A_PrevAndNext(
		Session session, CommerceCountry commerceCountry, long companyId,
		boolean active, OrderByComparator<CommerceCountry> orderByComparator,
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

		sb.append(_SQL_SELECT_COMMERCECOUNTRY_WHERE);

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
			sb.append(CommerceCountryModelImpl.ORDER_BY_JPQL);
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
						commerceCountry)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<CommerceCountry> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the commerce countries where companyId = &#63; and active = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 */
	@Override
	public void removeByC_A(long companyId, boolean active) {
		for (CommerceCountry commerceCountry :
				findByC_A(
					companyId, active, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(commerceCountry);
		}
	}

	/**
	 * Returns the number of commerce countries where companyId = &#63; and active = &#63;.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @return the number of matching commerce countries
	 */
	@Override
	public int countByC_A(long companyId, boolean active) {
		FinderPath finderPath = _finderPathCountByC_A;

		Object[] finderArgs = new Object[] {companyId, active};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_COMMERCECOUNTRY_WHERE);

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
		"commerceCountry.companyId = ? AND ";

	private static final String _FINDER_COLUMN_C_A_ACTIVE_2 =
		"commerceCountry.active = ?";

	private FinderPath _finderPathWithPaginationFindByC_B_A;
	private FinderPath _finderPathWithoutPaginationFindByC_B_A;
	private FinderPath _finderPathCountByC_B_A;

	/**
	 * Returns all the commerce countries where companyId = &#63; and billingAllowed = &#63; and active = &#63;.
	 *
	 * @param companyId the company ID
	 * @param billingAllowed the billing allowed
	 * @param active the active
	 * @return the matching commerce countries
	 */
	@Override
	public List<CommerceCountry> findByC_B_A(
		long companyId, boolean billingAllowed, boolean active) {

		return findByC_B_A(
			companyId, billingAllowed, active, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce countries where companyId = &#63; and billingAllowed = &#63; and active = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceCountryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param billingAllowed the billing allowed
	 * @param active the active
	 * @param start the lower bound of the range of commerce countries
	 * @param end the upper bound of the range of commerce countries (not inclusive)
	 * @return the range of matching commerce countries
	 */
	@Override
	public List<CommerceCountry> findByC_B_A(
		long companyId, boolean billingAllowed, boolean active, int start,
		int end) {

		return findByC_B_A(companyId, billingAllowed, active, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce countries where companyId = &#63; and billingAllowed = &#63; and active = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceCountryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param billingAllowed the billing allowed
	 * @param active the active
	 * @param start the lower bound of the range of commerce countries
	 * @param end the upper bound of the range of commerce countries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce countries
	 */
	@Override
	public List<CommerceCountry> findByC_B_A(
		long companyId, boolean billingAllowed, boolean active, int start,
		int end, OrderByComparator<CommerceCountry> orderByComparator) {

		return findByC_B_A(
			companyId, billingAllowed, active, start, end, orderByComparator,
			true);
	}

	/**
	 * Returns an ordered range of all the commerce countries where companyId = &#63; and billingAllowed = &#63; and active = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceCountryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param billingAllowed the billing allowed
	 * @param active the active
	 * @param start the lower bound of the range of commerce countries
	 * @param end the upper bound of the range of commerce countries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce countries
	 */
	@Override
	public List<CommerceCountry> findByC_B_A(
		long companyId, boolean billingAllowed, boolean active, int start,
		int end, OrderByComparator<CommerceCountry> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByC_B_A;
				finderArgs = new Object[] {companyId, billingAllowed, active};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByC_B_A;
			finderArgs = new Object[] {
				companyId, billingAllowed, active, start, end, orderByComparator
			};
		}

		List<CommerceCountry> list = null;

		if (useFinderCache) {
			list = (List<CommerceCountry>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (CommerceCountry commerceCountry : list) {
					if ((companyId != commerceCountry.getCompanyId()) ||
						(billingAllowed !=
							commerceCountry.isBillingAllowed()) ||
						(active != commerceCountry.isActive())) {

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

			sb.append(_SQL_SELECT_COMMERCECOUNTRY_WHERE);

			sb.append(_FINDER_COLUMN_C_B_A_COMPANYID_2);

			sb.append(_FINDER_COLUMN_C_B_A_BILLINGALLOWED_2);

			sb.append(_FINDER_COLUMN_C_B_A_ACTIVE_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(CommerceCountryModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(companyId);

				queryPos.add(billingAllowed);

				queryPos.add(active);

				list = (List<CommerceCountry>)QueryUtil.list(
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
	 * Returns the first commerce country in the ordered set where companyId = &#63; and billingAllowed = &#63; and active = &#63;.
	 *
	 * @param companyId the company ID
	 * @param billingAllowed the billing allowed
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce country
	 * @throws NoSuchCountryException if a matching commerce country could not be found
	 */
	@Override
	public CommerceCountry findByC_B_A_First(
			long companyId, boolean billingAllowed, boolean active,
			OrderByComparator<CommerceCountry> orderByComparator)
		throws NoSuchCountryException {

		CommerceCountry commerceCountry = fetchByC_B_A_First(
			companyId, billingAllowed, active, orderByComparator);

		if (commerceCountry != null) {
			return commerceCountry;
		}

		StringBundler sb = new StringBundler(8);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("companyId=");
		sb.append(companyId);

		sb.append(", billingAllowed=");
		sb.append(billingAllowed);

		sb.append(", active=");
		sb.append(active);

		sb.append("}");

		throw new NoSuchCountryException(sb.toString());
	}

	/**
	 * Returns the first commerce country in the ordered set where companyId = &#63; and billingAllowed = &#63; and active = &#63;.
	 *
	 * @param companyId the company ID
	 * @param billingAllowed the billing allowed
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce country, or <code>null</code> if a matching commerce country could not be found
	 */
	@Override
	public CommerceCountry fetchByC_B_A_First(
		long companyId, boolean billingAllowed, boolean active,
		OrderByComparator<CommerceCountry> orderByComparator) {

		List<CommerceCountry> list = findByC_B_A(
			companyId, billingAllowed, active, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last commerce country in the ordered set where companyId = &#63; and billingAllowed = &#63; and active = &#63;.
	 *
	 * @param companyId the company ID
	 * @param billingAllowed the billing allowed
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce country
	 * @throws NoSuchCountryException if a matching commerce country could not be found
	 */
	@Override
	public CommerceCountry findByC_B_A_Last(
			long companyId, boolean billingAllowed, boolean active,
			OrderByComparator<CommerceCountry> orderByComparator)
		throws NoSuchCountryException {

		CommerceCountry commerceCountry = fetchByC_B_A_Last(
			companyId, billingAllowed, active, orderByComparator);

		if (commerceCountry != null) {
			return commerceCountry;
		}

		StringBundler sb = new StringBundler(8);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("companyId=");
		sb.append(companyId);

		sb.append(", billingAllowed=");
		sb.append(billingAllowed);

		sb.append(", active=");
		sb.append(active);

		sb.append("}");

		throw new NoSuchCountryException(sb.toString());
	}

	/**
	 * Returns the last commerce country in the ordered set where companyId = &#63; and billingAllowed = &#63; and active = &#63;.
	 *
	 * @param companyId the company ID
	 * @param billingAllowed the billing allowed
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce country, or <code>null</code> if a matching commerce country could not be found
	 */
	@Override
	public CommerceCountry fetchByC_B_A_Last(
		long companyId, boolean billingAllowed, boolean active,
		OrderByComparator<CommerceCountry> orderByComparator) {

		int count = countByC_B_A(companyId, billingAllowed, active);

		if (count == 0) {
			return null;
		}

		List<CommerceCountry> list = findByC_B_A(
			companyId, billingAllowed, active, count - 1, count,
			orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the commerce countries before and after the current commerce country in the ordered set where companyId = &#63; and billingAllowed = &#63; and active = &#63;.
	 *
	 * @param commerceCountryId the primary key of the current commerce country
	 * @param companyId the company ID
	 * @param billingAllowed the billing allowed
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce country
	 * @throws NoSuchCountryException if a commerce country with the primary key could not be found
	 */
	@Override
	public CommerceCountry[] findByC_B_A_PrevAndNext(
			long commerceCountryId, long companyId, boolean billingAllowed,
			boolean active,
			OrderByComparator<CommerceCountry> orderByComparator)
		throws NoSuchCountryException {

		CommerceCountry commerceCountry = findByPrimaryKey(commerceCountryId);

		Session session = null;

		try {
			session = openSession();

			CommerceCountry[] array = new CommerceCountryImpl[3];

			array[0] = getByC_B_A_PrevAndNext(
				session, commerceCountry, companyId, billingAllowed, active,
				orderByComparator, true);

			array[1] = commerceCountry;

			array[2] = getByC_B_A_PrevAndNext(
				session, commerceCountry, companyId, billingAllowed, active,
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

	protected CommerceCountry getByC_B_A_PrevAndNext(
		Session session, CommerceCountry commerceCountry, long companyId,
		boolean billingAllowed, boolean active,
		OrderByComparator<CommerceCountry> orderByComparator,
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

		sb.append(_SQL_SELECT_COMMERCECOUNTRY_WHERE);

		sb.append(_FINDER_COLUMN_C_B_A_COMPANYID_2);

		sb.append(_FINDER_COLUMN_C_B_A_BILLINGALLOWED_2);

		sb.append(_FINDER_COLUMN_C_B_A_ACTIVE_2);

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
			sb.append(CommerceCountryModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(companyId);

		queryPos.add(billingAllowed);

		queryPos.add(active);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						commerceCountry)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<CommerceCountry> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the commerce countries where companyId = &#63; and billingAllowed = &#63; and active = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param billingAllowed the billing allowed
	 * @param active the active
	 */
	@Override
	public void removeByC_B_A(
		long companyId, boolean billingAllowed, boolean active) {

		for (CommerceCountry commerceCountry :
				findByC_B_A(
					companyId, billingAllowed, active, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			remove(commerceCountry);
		}
	}

	/**
	 * Returns the number of commerce countries where companyId = &#63; and billingAllowed = &#63; and active = &#63;.
	 *
	 * @param companyId the company ID
	 * @param billingAllowed the billing allowed
	 * @param active the active
	 * @return the number of matching commerce countries
	 */
	@Override
	public int countByC_B_A(
		long companyId, boolean billingAllowed, boolean active) {

		FinderPath finderPath = _finderPathCountByC_B_A;

		Object[] finderArgs = new Object[] {companyId, billingAllowed, active};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_COUNT_COMMERCECOUNTRY_WHERE);

			sb.append(_FINDER_COLUMN_C_B_A_COMPANYID_2);

			sb.append(_FINDER_COLUMN_C_B_A_BILLINGALLOWED_2);

			sb.append(_FINDER_COLUMN_C_B_A_ACTIVE_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(companyId);

				queryPos.add(billingAllowed);

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

	private static final String _FINDER_COLUMN_C_B_A_COMPANYID_2 =
		"commerceCountry.companyId = ? AND ";

	private static final String _FINDER_COLUMN_C_B_A_BILLINGALLOWED_2 =
		"commerceCountry.billingAllowed = ? AND ";

	private static final String _FINDER_COLUMN_C_B_A_ACTIVE_2 =
		"commerceCountry.active = ?";

	private FinderPath _finderPathWithPaginationFindByC_S_A;
	private FinderPath _finderPathWithoutPaginationFindByC_S_A;
	private FinderPath _finderPathCountByC_S_A;

	/**
	 * Returns all the commerce countries where companyId = &#63; and shippingAllowed = &#63; and active = &#63;.
	 *
	 * @param companyId the company ID
	 * @param shippingAllowed the shipping allowed
	 * @param active the active
	 * @return the matching commerce countries
	 */
	@Override
	public List<CommerceCountry> findByC_S_A(
		long companyId, boolean shippingAllowed, boolean active) {

		return findByC_S_A(
			companyId, shippingAllowed, active, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce countries where companyId = &#63; and shippingAllowed = &#63; and active = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceCountryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param shippingAllowed the shipping allowed
	 * @param active the active
	 * @param start the lower bound of the range of commerce countries
	 * @param end the upper bound of the range of commerce countries (not inclusive)
	 * @return the range of matching commerce countries
	 */
	@Override
	public List<CommerceCountry> findByC_S_A(
		long companyId, boolean shippingAllowed, boolean active, int start,
		int end) {

		return findByC_S_A(
			companyId, shippingAllowed, active, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce countries where companyId = &#63; and shippingAllowed = &#63; and active = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceCountryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param shippingAllowed the shipping allowed
	 * @param active the active
	 * @param start the lower bound of the range of commerce countries
	 * @param end the upper bound of the range of commerce countries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce countries
	 */
	@Override
	public List<CommerceCountry> findByC_S_A(
		long companyId, boolean shippingAllowed, boolean active, int start,
		int end, OrderByComparator<CommerceCountry> orderByComparator) {

		return findByC_S_A(
			companyId, shippingAllowed, active, start, end, orderByComparator,
			true);
	}

	/**
	 * Returns an ordered range of all the commerce countries where companyId = &#63; and shippingAllowed = &#63; and active = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceCountryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param shippingAllowed the shipping allowed
	 * @param active the active
	 * @param start the lower bound of the range of commerce countries
	 * @param end the upper bound of the range of commerce countries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce countries
	 */
	@Override
	public List<CommerceCountry> findByC_S_A(
		long companyId, boolean shippingAllowed, boolean active, int start,
		int end, OrderByComparator<CommerceCountry> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByC_S_A;
				finderArgs = new Object[] {companyId, shippingAllowed, active};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByC_S_A;
			finderArgs = new Object[] {
				companyId, shippingAllowed, active, start, end,
				orderByComparator
			};
		}

		List<CommerceCountry> list = null;

		if (useFinderCache) {
			list = (List<CommerceCountry>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (CommerceCountry commerceCountry : list) {
					if ((companyId != commerceCountry.getCompanyId()) ||
						(shippingAllowed !=
							commerceCountry.isShippingAllowed()) ||
						(active != commerceCountry.isActive())) {

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

			sb.append(_SQL_SELECT_COMMERCECOUNTRY_WHERE);

			sb.append(_FINDER_COLUMN_C_S_A_COMPANYID_2);

			sb.append(_FINDER_COLUMN_C_S_A_SHIPPINGALLOWED_2);

			sb.append(_FINDER_COLUMN_C_S_A_ACTIVE_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(CommerceCountryModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(companyId);

				queryPos.add(shippingAllowed);

				queryPos.add(active);

				list = (List<CommerceCountry>)QueryUtil.list(
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
	 * Returns the first commerce country in the ordered set where companyId = &#63; and shippingAllowed = &#63; and active = &#63;.
	 *
	 * @param companyId the company ID
	 * @param shippingAllowed the shipping allowed
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce country
	 * @throws NoSuchCountryException if a matching commerce country could not be found
	 */
	@Override
	public CommerceCountry findByC_S_A_First(
			long companyId, boolean shippingAllowed, boolean active,
			OrderByComparator<CommerceCountry> orderByComparator)
		throws NoSuchCountryException {

		CommerceCountry commerceCountry = fetchByC_S_A_First(
			companyId, shippingAllowed, active, orderByComparator);

		if (commerceCountry != null) {
			return commerceCountry;
		}

		StringBundler sb = new StringBundler(8);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("companyId=");
		sb.append(companyId);

		sb.append(", shippingAllowed=");
		sb.append(shippingAllowed);

		sb.append(", active=");
		sb.append(active);

		sb.append("}");

		throw new NoSuchCountryException(sb.toString());
	}

	/**
	 * Returns the first commerce country in the ordered set where companyId = &#63; and shippingAllowed = &#63; and active = &#63;.
	 *
	 * @param companyId the company ID
	 * @param shippingAllowed the shipping allowed
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce country, or <code>null</code> if a matching commerce country could not be found
	 */
	@Override
	public CommerceCountry fetchByC_S_A_First(
		long companyId, boolean shippingAllowed, boolean active,
		OrderByComparator<CommerceCountry> orderByComparator) {

		List<CommerceCountry> list = findByC_S_A(
			companyId, shippingAllowed, active, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last commerce country in the ordered set where companyId = &#63; and shippingAllowed = &#63; and active = &#63;.
	 *
	 * @param companyId the company ID
	 * @param shippingAllowed the shipping allowed
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce country
	 * @throws NoSuchCountryException if a matching commerce country could not be found
	 */
	@Override
	public CommerceCountry findByC_S_A_Last(
			long companyId, boolean shippingAllowed, boolean active,
			OrderByComparator<CommerceCountry> orderByComparator)
		throws NoSuchCountryException {

		CommerceCountry commerceCountry = fetchByC_S_A_Last(
			companyId, shippingAllowed, active, orderByComparator);

		if (commerceCountry != null) {
			return commerceCountry;
		}

		StringBundler sb = new StringBundler(8);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("companyId=");
		sb.append(companyId);

		sb.append(", shippingAllowed=");
		sb.append(shippingAllowed);

		sb.append(", active=");
		sb.append(active);

		sb.append("}");

		throw new NoSuchCountryException(sb.toString());
	}

	/**
	 * Returns the last commerce country in the ordered set where companyId = &#63; and shippingAllowed = &#63; and active = &#63;.
	 *
	 * @param companyId the company ID
	 * @param shippingAllowed the shipping allowed
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce country, or <code>null</code> if a matching commerce country could not be found
	 */
	@Override
	public CommerceCountry fetchByC_S_A_Last(
		long companyId, boolean shippingAllowed, boolean active,
		OrderByComparator<CommerceCountry> orderByComparator) {

		int count = countByC_S_A(companyId, shippingAllowed, active);

		if (count == 0) {
			return null;
		}

		List<CommerceCountry> list = findByC_S_A(
			companyId, shippingAllowed, active, count - 1, count,
			orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the commerce countries before and after the current commerce country in the ordered set where companyId = &#63; and shippingAllowed = &#63; and active = &#63;.
	 *
	 * @param commerceCountryId the primary key of the current commerce country
	 * @param companyId the company ID
	 * @param shippingAllowed the shipping allowed
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce country
	 * @throws NoSuchCountryException if a commerce country with the primary key could not be found
	 */
	@Override
	public CommerceCountry[] findByC_S_A_PrevAndNext(
			long commerceCountryId, long companyId, boolean shippingAllowed,
			boolean active,
			OrderByComparator<CommerceCountry> orderByComparator)
		throws NoSuchCountryException {

		CommerceCountry commerceCountry = findByPrimaryKey(commerceCountryId);

		Session session = null;

		try {
			session = openSession();

			CommerceCountry[] array = new CommerceCountryImpl[3];

			array[0] = getByC_S_A_PrevAndNext(
				session, commerceCountry, companyId, shippingAllowed, active,
				orderByComparator, true);

			array[1] = commerceCountry;

			array[2] = getByC_S_A_PrevAndNext(
				session, commerceCountry, companyId, shippingAllowed, active,
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

	protected CommerceCountry getByC_S_A_PrevAndNext(
		Session session, CommerceCountry commerceCountry, long companyId,
		boolean shippingAllowed, boolean active,
		OrderByComparator<CommerceCountry> orderByComparator,
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

		sb.append(_SQL_SELECT_COMMERCECOUNTRY_WHERE);

		sb.append(_FINDER_COLUMN_C_S_A_COMPANYID_2);

		sb.append(_FINDER_COLUMN_C_S_A_SHIPPINGALLOWED_2);

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
			sb.append(CommerceCountryModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(companyId);

		queryPos.add(shippingAllowed);

		queryPos.add(active);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						commerceCountry)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<CommerceCountry> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the commerce countries where companyId = &#63; and shippingAllowed = &#63; and active = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param shippingAllowed the shipping allowed
	 * @param active the active
	 */
	@Override
	public void removeByC_S_A(
		long companyId, boolean shippingAllowed, boolean active) {

		for (CommerceCountry commerceCountry :
				findByC_S_A(
					companyId, shippingAllowed, active, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			remove(commerceCountry);
		}
	}

	/**
	 * Returns the number of commerce countries where companyId = &#63; and shippingAllowed = &#63; and active = &#63;.
	 *
	 * @param companyId the company ID
	 * @param shippingAllowed the shipping allowed
	 * @param active the active
	 * @return the number of matching commerce countries
	 */
	@Override
	public int countByC_S_A(
		long companyId, boolean shippingAllowed, boolean active) {

		FinderPath finderPath = _finderPathCountByC_S_A;

		Object[] finderArgs = new Object[] {companyId, shippingAllowed, active};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_COUNT_COMMERCECOUNTRY_WHERE);

			sb.append(_FINDER_COLUMN_C_S_A_COMPANYID_2);

			sb.append(_FINDER_COLUMN_C_S_A_SHIPPINGALLOWED_2);

			sb.append(_FINDER_COLUMN_C_S_A_ACTIVE_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(companyId);

				queryPos.add(shippingAllowed);

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
		"commerceCountry.companyId = ? AND ";

	private static final String _FINDER_COLUMN_C_S_A_SHIPPINGALLOWED_2 =
		"commerceCountry.shippingAllowed = ? AND ";

	private static final String _FINDER_COLUMN_C_S_A_ACTIVE_2 =
		"commerceCountry.active = ?";

	public CommerceCountryPersistenceImpl() {
		Map<String, String> dbColumnNames = new HashMap<String, String>();

		dbColumnNames.put("uuid", "uuid_");
		dbColumnNames.put("active", "active_");

		setDBColumnNames(dbColumnNames);

		setModelClass(CommerceCountry.class);

		setModelImplClass(CommerceCountryImpl.class);
		setModelPKClass(long.class);

		setTable(CommerceCountryTable.INSTANCE);
	}

	/**
	 * Caches the commerce country in the entity cache if it is enabled.
	 *
	 * @param commerceCountry the commerce country
	 */
	@Override
	public void cacheResult(CommerceCountry commerceCountry) {
		entityCache.putResult(
			CommerceCountryImpl.class, commerceCountry.getPrimaryKey(),
			commerceCountry);

		finderCache.putResult(
			_finderPathFetchByC_Tw,
			new Object[] {
				commerceCountry.getCompanyId(),
				commerceCountry.getTwoLettersISOCode()
			},
			commerceCountry);

		finderCache.putResult(
			_finderPathFetchByC_N,
			new Object[] {
				commerceCountry.getCompanyId(),
				commerceCountry.getNumericISOCode()
			},
			commerceCountry);
	}

	/**
	 * Caches the commerce countries in the entity cache if it is enabled.
	 *
	 * @param commerceCountries the commerce countries
	 */
	@Override
	public void cacheResult(List<CommerceCountry> commerceCountries) {
		for (CommerceCountry commerceCountry : commerceCountries) {
			if (entityCache.getResult(
					CommerceCountryImpl.class,
					commerceCountry.getPrimaryKey()) == null) {

				cacheResult(commerceCountry);
			}
		}
	}

	/**
	 * Clears the cache for all commerce countries.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(CommerceCountryImpl.class);

		finderCache.clearCache(CommerceCountryImpl.class);
	}

	/**
	 * Clears the cache for the commerce country.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(CommerceCountry commerceCountry) {
		entityCache.removeResult(CommerceCountryImpl.class, commerceCountry);
	}

	@Override
	public void clearCache(List<CommerceCountry> commerceCountries) {
		for (CommerceCountry commerceCountry : commerceCountries) {
			entityCache.removeResult(
				CommerceCountryImpl.class, commerceCountry);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(CommerceCountryImpl.class);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(CommerceCountryImpl.class, primaryKey);
		}
	}

	protected void cacheUniqueFindersCache(
		CommerceCountryModelImpl commerceCountryModelImpl) {

		Object[] args = new Object[] {
			commerceCountryModelImpl.getCompanyId(),
			commerceCountryModelImpl.getTwoLettersISOCode()
		};

		finderCache.putResult(_finderPathCountByC_Tw, args, Long.valueOf(1));
		finderCache.putResult(
			_finderPathFetchByC_Tw, args, commerceCountryModelImpl);

		args = new Object[] {
			commerceCountryModelImpl.getCompanyId(),
			commerceCountryModelImpl.getNumericISOCode()
		};

		finderCache.putResult(_finderPathCountByC_N, args, Long.valueOf(1));
		finderCache.putResult(
			_finderPathFetchByC_N, args, commerceCountryModelImpl);
	}

	/**
	 * Creates a new commerce country with the primary key. Does not add the commerce country to the database.
	 *
	 * @param commerceCountryId the primary key for the new commerce country
	 * @return the new commerce country
	 */
	@Override
	public CommerceCountry create(long commerceCountryId) {
		CommerceCountry commerceCountry = new CommerceCountryImpl();

		commerceCountry.setNew(true);
		commerceCountry.setPrimaryKey(commerceCountryId);

		String uuid = PortalUUIDUtil.generate();

		commerceCountry.setUuid(uuid);

		commerceCountry.setCompanyId(CompanyThreadLocal.getCompanyId());

		return commerceCountry;
	}

	/**
	 * Removes the commerce country with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param commerceCountryId the primary key of the commerce country
	 * @return the commerce country that was removed
	 * @throws NoSuchCountryException if a commerce country with the primary key could not be found
	 */
	@Override
	public CommerceCountry remove(long commerceCountryId)
		throws NoSuchCountryException {

		return remove((Serializable)commerceCountryId);
	}

	/**
	 * Removes the commerce country with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the commerce country
	 * @return the commerce country that was removed
	 * @throws NoSuchCountryException if a commerce country with the primary key could not be found
	 */
	@Override
	public CommerceCountry remove(Serializable primaryKey)
		throws NoSuchCountryException {

		Session session = null;

		try {
			session = openSession();

			CommerceCountry commerceCountry = (CommerceCountry)session.get(
				CommerceCountryImpl.class, primaryKey);

			if (commerceCountry == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchCountryException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(commerceCountry);
		}
		catch (NoSuchCountryException noSuchEntityException) {
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
	protected CommerceCountry removeImpl(CommerceCountry commerceCountry) {
		Session session = null;

		try {
			session = openSession();

			if (!session.contains(commerceCountry)) {
				commerceCountry = (CommerceCountry)session.get(
					CommerceCountryImpl.class,
					commerceCountry.getPrimaryKeyObj());
			}

			if (commerceCountry != null) {
				session.delete(commerceCountry);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (commerceCountry != null) {
			clearCache(commerceCountry);
		}

		return commerceCountry;
	}

	@Override
	public CommerceCountry updateImpl(CommerceCountry commerceCountry) {
		boolean isNew = commerceCountry.isNew();

		if (!(commerceCountry instanceof CommerceCountryModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(commerceCountry.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					commerceCountry);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in commerceCountry proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom CommerceCountry implementation " +
					commerceCountry.getClass());
		}

		CommerceCountryModelImpl commerceCountryModelImpl =
			(CommerceCountryModelImpl)commerceCountry;

		if (Validator.isNull(commerceCountry.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			commerceCountry.setUuid(uuid);
		}

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Date now = new Date();

		if (isNew && (commerceCountry.getCreateDate() == null)) {
			if (serviceContext == null) {
				commerceCountry.setCreateDate(now);
			}
			else {
				commerceCountry.setCreateDate(
					serviceContext.getCreateDate(now));
			}
		}

		if (!commerceCountryModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				commerceCountry.setModifiedDate(now);
			}
			else {
				commerceCountry.setModifiedDate(
					serviceContext.getModifiedDate(now));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (isNew) {
				session.save(commerceCountry);
			}
			else {
				commerceCountry = (CommerceCountry)session.merge(
					commerceCountry);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		entityCache.putResult(
			CommerceCountryImpl.class, commerceCountryModelImpl, false, true);

		cacheUniqueFindersCache(commerceCountryModelImpl);

		if (isNew) {
			commerceCountry.setNew(false);
		}

		commerceCountry.resetOriginalValues();

		return commerceCountry;
	}

	/**
	 * Returns the commerce country with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the commerce country
	 * @return the commerce country
	 * @throws NoSuchCountryException if a commerce country with the primary key could not be found
	 */
	@Override
	public CommerceCountry findByPrimaryKey(Serializable primaryKey)
		throws NoSuchCountryException {

		CommerceCountry commerceCountry = fetchByPrimaryKey(primaryKey);

		if (commerceCountry == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchCountryException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return commerceCountry;
	}

	/**
	 * Returns the commerce country with the primary key or throws a <code>NoSuchCountryException</code> if it could not be found.
	 *
	 * @param commerceCountryId the primary key of the commerce country
	 * @return the commerce country
	 * @throws NoSuchCountryException if a commerce country with the primary key could not be found
	 */
	@Override
	public CommerceCountry findByPrimaryKey(long commerceCountryId)
		throws NoSuchCountryException {

		return findByPrimaryKey((Serializable)commerceCountryId);
	}

	/**
	 * Returns the commerce country with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param commerceCountryId the primary key of the commerce country
	 * @return the commerce country, or <code>null</code> if a commerce country with the primary key could not be found
	 */
	@Override
	public CommerceCountry fetchByPrimaryKey(long commerceCountryId) {
		return fetchByPrimaryKey((Serializable)commerceCountryId);
	}

	/**
	 * Returns all the commerce countries.
	 *
	 * @return the commerce countries
	 */
	@Override
	public List<CommerceCountry> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce countries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceCountryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce countries
	 * @param end the upper bound of the range of commerce countries (not inclusive)
	 * @return the range of commerce countries
	 */
	@Override
	public List<CommerceCountry> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce countries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceCountryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce countries
	 * @param end the upper bound of the range of commerce countries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of commerce countries
	 */
	@Override
	public List<CommerceCountry> findAll(
		int start, int end,
		OrderByComparator<CommerceCountry> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce countries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceCountryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce countries
	 * @param end the upper bound of the range of commerce countries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of commerce countries
	 */
	@Override
	public List<CommerceCountry> findAll(
		int start, int end,
		OrderByComparator<CommerceCountry> orderByComparator,
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

		List<CommerceCountry> list = null;

		if (useFinderCache) {
			list = (List<CommerceCountry>)finderCache.getResult(
				finderPath, finderArgs);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_COMMERCECOUNTRY);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_COMMERCECOUNTRY;

				sql = sql.concat(CommerceCountryModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<CommerceCountry>)QueryUtil.list(
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
	 * Removes all the commerce countries from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (CommerceCountry commerceCountry : findAll()) {
			remove(commerceCountry);
		}
	}

	/**
	 * Returns the number of commerce countries.
	 *
	 * @return the number of commerce countries
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(
			_finderPathCountAll, FINDER_ARGS_EMPTY);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(_SQL_COUNT_COMMERCECOUNTRY);

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
		return "commerceCountryId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_COMMERCECOUNTRY;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return CommerceCountryModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the commerce country persistence.
	 */
	public void afterPropertiesSet() {
		Bundle bundle = FrameworkUtil.getBundle(
			CommerceCountryPersistenceImpl.class);

		_bundleContext = bundle.getBundleContext();

		_argumentsResolverServiceRegistration = _bundleContext.registerService(
			ArgumentsResolver.class,
			new CommerceCountryModelArgumentsResolver(),
			new HashMapDictionary<>());

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

		_finderPathFetchByC_Tw = new FinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByC_Tw",
			new String[] {Long.class.getName(), String.class.getName()},
			new String[] {"companyId", "twoLettersISOCode"}, true);

		_finderPathCountByC_Tw = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_Tw",
			new String[] {Long.class.getName(), String.class.getName()},
			new String[] {"companyId", "twoLettersISOCode"}, false);

		_finderPathFetchByC_N = new FinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByC_N",
			new String[] {Long.class.getName(), Integer.class.getName()},
			new String[] {"companyId", "numericISOCode"}, true);

		_finderPathCountByC_N = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_N",
			new String[] {Long.class.getName(), Integer.class.getName()},
			new String[] {"companyId", "numericISOCode"}, false);

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

		_finderPathWithPaginationFindByC_B_A = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByC_B_A",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				Boolean.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"companyId", "billingAllowed", "active_"}, true);

		_finderPathWithoutPaginationFindByC_B_A = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByC_B_A",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				Boolean.class.getName()
			},
			new String[] {"companyId", "billingAllowed", "active_"}, true);

		_finderPathCountByC_B_A = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_B_A",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				Boolean.class.getName()
			},
			new String[] {"companyId", "billingAllowed", "active_"}, false);

		_finderPathWithPaginationFindByC_S_A = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByC_S_A",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				Boolean.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"companyId", "shippingAllowed", "active_"}, true);

		_finderPathWithoutPaginationFindByC_S_A = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByC_S_A",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				Boolean.class.getName()
			},
			new String[] {"companyId", "shippingAllowed", "active_"}, true);

		_finderPathCountByC_S_A = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_S_A",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				Boolean.class.getName()
			},
			new String[] {"companyId", "shippingAllowed", "active_"}, false);
	}

	public void destroy() {
		entityCache.removeCache(CommerceCountryImpl.class.getName());

		_argumentsResolverServiceRegistration.unregister();
	}

	private BundleContext _bundleContext;

	@ServiceReference(type = EntityCache.class)
	protected EntityCache entityCache;

	@ServiceReference(type = FinderCache.class)
	protected FinderCache finderCache;

	private static final String _SQL_SELECT_COMMERCECOUNTRY =
		"SELECT commerceCountry FROM CommerceCountry commerceCountry";

	private static final String _SQL_SELECT_COMMERCECOUNTRY_WHERE =
		"SELECT commerceCountry FROM CommerceCountry commerceCountry WHERE ";

	private static final String _SQL_COUNT_COMMERCECOUNTRY =
		"SELECT COUNT(commerceCountry) FROM CommerceCountry commerceCountry";

	private static final String _SQL_COUNT_COMMERCECOUNTRY_WHERE =
		"SELECT COUNT(commerceCountry) FROM CommerceCountry commerceCountry WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS = "commerceCountry.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No CommerceCountry exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No CommerceCountry exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		CommerceCountryPersistenceImpl.class);

	private static final Set<String> _badColumnNames = SetUtil.fromArray(
		new String[] {"uuid", "active"});

	@Override
	protected FinderCache getFinderCache() {
		return finderCache;
	}

	private ServiceRegistration<ArgumentsResolver>
		_argumentsResolverServiceRegistration;

	private static class CommerceCountryModelArgumentsResolver
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

			CommerceCountryModelImpl commerceCountryModelImpl =
				(CommerceCountryModelImpl)baseModel;

			long columnBitmask = commerceCountryModelImpl.getColumnBitmask();

			if (!checkColumn || (columnBitmask == 0)) {
				return _getValue(
					commerceCountryModelImpl, columnNames, original);
			}

			Long finderPathColumnBitmask = _finderPathColumnBitmasksCache.get(
				finderPath);

			if (finderPathColumnBitmask == null) {
				finderPathColumnBitmask = 0L;

				for (String columnName : columnNames) {
					finderPathColumnBitmask |=
						commerceCountryModelImpl.getColumnBitmask(columnName);
				}

				_finderPathColumnBitmasksCache.put(
					finderPath, finderPathColumnBitmask);
			}

			if ((columnBitmask & finderPathColumnBitmask) != 0) {
				return _getValue(
					commerceCountryModelImpl, columnNames, original);
			}

			return null;
		}

		@Override
		public String getClassName() {
			return CommerceCountryImpl.class.getName();
		}

		@Override
		public String getTableName() {
			return CommerceCountryTable.INSTANCE.getTableName();
		}

		private Object[] _getValue(
			CommerceCountryModelImpl commerceCountryModelImpl,
			String[] columnNames, boolean original) {

			Object[] arguments = new Object[columnNames.length];

			for (int i = 0; i < arguments.length; i++) {
				String columnName = columnNames[i];

				if (original) {
					arguments[i] =
						commerceCountryModelImpl.getColumnOriginalValue(
							columnName);
				}
				else {
					arguments[i] = commerceCountryModelImpl.getColumnValue(
						columnName);
				}
			}

			return arguments;
		}

		private static Map<FinderPath, Long> _finderPathColumnBitmasksCache =
			new ConcurrentHashMap<>();

	}

}