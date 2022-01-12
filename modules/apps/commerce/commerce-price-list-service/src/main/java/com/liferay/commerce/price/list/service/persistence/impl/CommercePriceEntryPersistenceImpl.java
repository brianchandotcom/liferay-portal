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

package com.liferay.commerce.price.list.service.persistence.impl;

import com.liferay.commerce.price.list.exception.NoSuchPriceEntryException;
import com.liferay.commerce.price.list.model.CommercePriceEntry;
import com.liferay.commerce.price.list.model.CommercePriceEntryTable;
import com.liferay.commerce.price.list.model.impl.CommercePriceEntryImpl;
import com.liferay.commerce.price.list.model.impl.CommercePriceEntryModelImpl;
import com.liferay.commerce.price.list.service.persistence.CommercePriceEntryPersistence;
import com.liferay.commerce.price.list.service.persistence.CommercePriceEntryUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.change.tracking.CTColumnResolutionType;
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
import com.liferay.portal.kernel.service.persistence.change.tracking.helper.CTPersistenceHelper;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;
import com.liferay.portal.spring.extender.service.ServiceReference;

import java.io.Serializable;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;

import java.sql.Timestamp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * The persistence implementation for the commerce price entry service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Alessio Antonio Rendina
 * @generated
 */
public class CommercePriceEntryPersistenceImpl
	extends BasePersistenceImpl<CommercePriceEntry>
	implements CommercePriceEntryPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>CommercePriceEntryUtil</code> to access the commerce price entry persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		CommercePriceEntryImpl.class.getName();

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
	 * Returns all the commerce price entries where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching commerce price entries
	 */
	@Override
	public List<CommercePriceEntry> findByUuid(String uuid) {
		return findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce price entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of commerce price entries
	 * @param end the upper bound of the range of commerce price entries (not inclusive)
	 * @return the range of matching commerce price entries
	 */
	@Override
	public List<CommercePriceEntry> findByUuid(
		String uuid, int start, int end) {

		return findByUuid(uuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce price entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of commerce price entries
	 * @param end the upper bound of the range of commerce price entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce price entries
	 */
	@Override
	public List<CommercePriceEntry> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<CommercePriceEntry> orderByComparator) {

		return findByUuid(uuid, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce price entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of commerce price entries
	 * @param end the upper bound of the range of commerce price entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce price entries
	 */
	@Override
	public List<CommercePriceEntry> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<CommercePriceEntry> orderByComparator,
		boolean useFinderCache) {

		uuid = Objects.toString(uuid, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			CommercePriceEntry.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache && productionMode) {
				finderPath = _finderPathWithoutPaginationFindByUuid;
				finderArgs = new Object[] {uuid};
			}
		}
		else if (useFinderCache && productionMode) {
			finderPath = _finderPathWithPaginationFindByUuid;
			finderArgs = new Object[] {uuid, start, end, orderByComparator};
		}

		List<CommercePriceEntry> list = null;

		if (useFinderCache && productionMode) {
			list = (List<CommercePriceEntry>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (CommercePriceEntry commercePriceEntry : list) {
					if (!uuid.equals(commercePriceEntry.getUuid())) {
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

			sb.append(_SQL_SELECT_COMMERCEPRICEENTRY_WHERE);

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
				sb.append(CommercePriceEntryModelImpl.ORDER_BY_JPQL);
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

				list = (List<CommercePriceEntry>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache && productionMode) {
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
	 * Returns the first commerce price entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce price entry
	 * @throws NoSuchPriceEntryException if a matching commerce price entry could not be found
	 */
	@Override
	public CommercePriceEntry findByUuid_First(
			String uuid,
			OrderByComparator<CommercePriceEntry> orderByComparator)
		throws NoSuchPriceEntryException {

		CommercePriceEntry commercePriceEntry = fetchByUuid_First(
			uuid, orderByComparator);

		if (commercePriceEntry != null) {
			return commercePriceEntry;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append("}");

		throw new NoSuchPriceEntryException(sb.toString());
	}

	/**
	 * Returns the first commerce price entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce price entry, or <code>null</code> if a matching commerce price entry could not be found
	 */
	@Override
	public CommercePriceEntry fetchByUuid_First(
		String uuid, OrderByComparator<CommercePriceEntry> orderByComparator) {

		List<CommercePriceEntry> list = findByUuid(
			uuid, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last commerce price entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce price entry
	 * @throws NoSuchPriceEntryException if a matching commerce price entry could not be found
	 */
	@Override
	public CommercePriceEntry findByUuid_Last(
			String uuid,
			OrderByComparator<CommercePriceEntry> orderByComparator)
		throws NoSuchPriceEntryException {

		CommercePriceEntry commercePriceEntry = fetchByUuid_Last(
			uuid, orderByComparator);

		if (commercePriceEntry != null) {
			return commercePriceEntry;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append("}");

		throw new NoSuchPriceEntryException(sb.toString());
	}

	/**
	 * Returns the last commerce price entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce price entry, or <code>null</code> if a matching commerce price entry could not be found
	 */
	@Override
	public CommercePriceEntry fetchByUuid_Last(
		String uuid, OrderByComparator<CommercePriceEntry> orderByComparator) {

		int count = countByUuid(uuid);

		if (count == 0) {
			return null;
		}

		List<CommercePriceEntry> list = findByUuid(
			uuid, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the commerce price entries before and after the current commerce price entry in the ordered set where uuid = &#63;.
	 *
	 * @param commercePriceEntryId the primary key of the current commerce price entry
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce price entry
	 * @throws NoSuchPriceEntryException if a commerce price entry with the primary key could not be found
	 */
	@Override
	public CommercePriceEntry[] findByUuid_PrevAndNext(
			long commercePriceEntryId, String uuid,
			OrderByComparator<CommercePriceEntry> orderByComparator)
		throws NoSuchPriceEntryException {

		uuid = Objects.toString(uuid, "");

		CommercePriceEntry commercePriceEntry = findByPrimaryKey(
			commercePriceEntryId);

		Session session = null;

		try {
			session = openSession();

			CommercePriceEntry[] array = new CommercePriceEntryImpl[3];

			array[0] = getByUuid_PrevAndNext(
				session, commercePriceEntry, uuid, orderByComparator, true);

			array[1] = commercePriceEntry;

			array[2] = getByUuid_PrevAndNext(
				session, commercePriceEntry, uuid, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected CommercePriceEntry getByUuid_PrevAndNext(
		Session session, CommercePriceEntry commercePriceEntry, String uuid,
		OrderByComparator<CommercePriceEntry> orderByComparator,
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

		sb.append(_SQL_SELECT_COMMERCEPRICEENTRY_WHERE);

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
			sb.append(CommercePriceEntryModelImpl.ORDER_BY_JPQL);
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
						commercePriceEntry)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<CommercePriceEntry> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the commerce price entries where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	@Override
	public void removeByUuid(String uuid) {
		for (CommercePriceEntry commercePriceEntry :
				findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(commercePriceEntry);
		}
	}

	/**
	 * Returns the number of commerce price entries where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching commerce price entries
	 */
	@Override
	public int countByUuid(String uuid) {
		uuid = Objects.toString(uuid, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			CommercePriceEntry.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByUuid;

			finderArgs = new Object[] {uuid};

			count = (Long)finderCache.getResult(finderPath, finderArgs);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_COMMERCEPRICEENTRY_WHERE);

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

				if (productionMode) {
					finderCache.putResult(finderPath, finderArgs, count);
				}
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
		"commercePriceEntry.uuid = ?";

	private static final String _FINDER_COLUMN_UUID_UUID_3 =
		"(commercePriceEntry.uuid IS NULL OR commercePriceEntry.uuid = '')";

	private FinderPath _finderPathWithPaginationFindByUuid_C;
	private FinderPath _finderPathWithoutPaginationFindByUuid_C;
	private FinderPath _finderPathCountByUuid_C;

	/**
	 * Returns all the commerce price entries where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching commerce price entries
	 */
	@Override
	public List<CommercePriceEntry> findByUuid_C(String uuid, long companyId) {
		return findByUuid_C(
			uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce price entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce price entries
	 * @param end the upper bound of the range of commerce price entries (not inclusive)
	 * @return the range of matching commerce price entries
	 */
	@Override
	public List<CommercePriceEntry> findByUuid_C(
		String uuid, long companyId, int start, int end) {

		return findByUuid_C(uuid, companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce price entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce price entries
	 * @param end the upper bound of the range of commerce price entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce price entries
	 */
	@Override
	public List<CommercePriceEntry> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<CommercePriceEntry> orderByComparator) {

		return findByUuid_C(
			uuid, companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce price entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce price entries
	 * @param end the upper bound of the range of commerce price entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce price entries
	 */
	@Override
	public List<CommercePriceEntry> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<CommercePriceEntry> orderByComparator,
		boolean useFinderCache) {

		uuid = Objects.toString(uuid, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			CommercePriceEntry.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache && productionMode) {
				finderPath = _finderPathWithoutPaginationFindByUuid_C;
				finderArgs = new Object[] {uuid, companyId};
			}
		}
		else if (useFinderCache && productionMode) {
			finderPath = _finderPathWithPaginationFindByUuid_C;
			finderArgs = new Object[] {
				uuid, companyId, start, end, orderByComparator
			};
		}

		List<CommercePriceEntry> list = null;

		if (useFinderCache && productionMode) {
			list = (List<CommercePriceEntry>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (CommercePriceEntry commercePriceEntry : list) {
					if (!uuid.equals(commercePriceEntry.getUuid()) ||
						(companyId != commercePriceEntry.getCompanyId())) {

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

			sb.append(_SQL_SELECT_COMMERCEPRICEENTRY_WHERE);

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
				sb.append(CommercePriceEntryModelImpl.ORDER_BY_JPQL);
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

				list = (List<CommercePriceEntry>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache && productionMode) {
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
	 * Returns the first commerce price entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce price entry
	 * @throws NoSuchPriceEntryException if a matching commerce price entry could not be found
	 */
	@Override
	public CommercePriceEntry findByUuid_C_First(
			String uuid, long companyId,
			OrderByComparator<CommercePriceEntry> orderByComparator)
		throws NoSuchPriceEntryException {

		CommercePriceEntry commercePriceEntry = fetchByUuid_C_First(
			uuid, companyId, orderByComparator);

		if (commercePriceEntry != null) {
			return commercePriceEntry;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append(", companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchPriceEntryException(sb.toString());
	}

	/**
	 * Returns the first commerce price entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce price entry, or <code>null</code> if a matching commerce price entry could not be found
	 */
	@Override
	public CommercePriceEntry fetchByUuid_C_First(
		String uuid, long companyId,
		OrderByComparator<CommercePriceEntry> orderByComparator) {

		List<CommercePriceEntry> list = findByUuid_C(
			uuid, companyId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last commerce price entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce price entry
	 * @throws NoSuchPriceEntryException if a matching commerce price entry could not be found
	 */
	@Override
	public CommercePriceEntry findByUuid_C_Last(
			String uuid, long companyId,
			OrderByComparator<CommercePriceEntry> orderByComparator)
		throws NoSuchPriceEntryException {

		CommercePriceEntry commercePriceEntry = fetchByUuid_C_Last(
			uuid, companyId, orderByComparator);

		if (commercePriceEntry != null) {
			return commercePriceEntry;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append(", companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchPriceEntryException(sb.toString());
	}

	/**
	 * Returns the last commerce price entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce price entry, or <code>null</code> if a matching commerce price entry could not be found
	 */
	@Override
	public CommercePriceEntry fetchByUuid_C_Last(
		String uuid, long companyId,
		OrderByComparator<CommercePriceEntry> orderByComparator) {

		int count = countByUuid_C(uuid, companyId);

		if (count == 0) {
			return null;
		}

		List<CommercePriceEntry> list = findByUuid_C(
			uuid, companyId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the commerce price entries before and after the current commerce price entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param commercePriceEntryId the primary key of the current commerce price entry
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce price entry
	 * @throws NoSuchPriceEntryException if a commerce price entry with the primary key could not be found
	 */
	@Override
	public CommercePriceEntry[] findByUuid_C_PrevAndNext(
			long commercePriceEntryId, String uuid, long companyId,
			OrderByComparator<CommercePriceEntry> orderByComparator)
		throws NoSuchPriceEntryException {

		uuid = Objects.toString(uuid, "");

		CommercePriceEntry commercePriceEntry = findByPrimaryKey(
			commercePriceEntryId);

		Session session = null;

		try {
			session = openSession();

			CommercePriceEntry[] array = new CommercePriceEntryImpl[3];

			array[0] = getByUuid_C_PrevAndNext(
				session, commercePriceEntry, uuid, companyId, orderByComparator,
				true);

			array[1] = commercePriceEntry;

			array[2] = getByUuid_C_PrevAndNext(
				session, commercePriceEntry, uuid, companyId, orderByComparator,
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

	protected CommercePriceEntry getByUuid_C_PrevAndNext(
		Session session, CommercePriceEntry commercePriceEntry, String uuid,
		long companyId, OrderByComparator<CommercePriceEntry> orderByComparator,
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

		sb.append(_SQL_SELECT_COMMERCEPRICEENTRY_WHERE);

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
			sb.append(CommercePriceEntryModelImpl.ORDER_BY_JPQL);
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
						commercePriceEntry)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<CommercePriceEntry> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the commerce price entries where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	@Override
	public void removeByUuid_C(String uuid, long companyId) {
		for (CommercePriceEntry commercePriceEntry :
				findByUuid_C(
					uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(commercePriceEntry);
		}
	}

	/**
	 * Returns the number of commerce price entries where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching commerce price entries
	 */
	@Override
	public int countByUuid_C(String uuid, long companyId) {
		uuid = Objects.toString(uuid, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			CommercePriceEntry.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByUuid_C;

			finderArgs = new Object[] {uuid, companyId};

			count = (Long)finderCache.getResult(finderPath, finderArgs);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_COMMERCEPRICEENTRY_WHERE);

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

				if (productionMode) {
					finderCache.putResult(finderPath, finderArgs, count);
				}
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
		"commercePriceEntry.uuid = ? AND ";

	private static final String _FINDER_COLUMN_UUID_C_UUID_3 =
		"(commercePriceEntry.uuid IS NULL OR commercePriceEntry.uuid = '') AND ";

	private static final String _FINDER_COLUMN_UUID_C_COMPANYID_2 =
		"commercePriceEntry.companyId = ?";

	private FinderPath _finderPathWithPaginationFindByCompanyId;
	private FinderPath _finderPathWithoutPaginationFindByCompanyId;
	private FinderPath _finderPathCountByCompanyId;

	/**
	 * Returns all the commerce price entries where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching commerce price entries
	 */
	@Override
	public List<CommercePriceEntry> findByCompanyId(long companyId) {
		return findByCompanyId(
			companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce price entries where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce price entries
	 * @param end the upper bound of the range of commerce price entries (not inclusive)
	 * @return the range of matching commerce price entries
	 */
	@Override
	public List<CommercePriceEntry> findByCompanyId(
		long companyId, int start, int end) {

		return findByCompanyId(companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce price entries where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce price entries
	 * @param end the upper bound of the range of commerce price entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce price entries
	 */
	@Override
	public List<CommercePriceEntry> findByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<CommercePriceEntry> orderByComparator) {

		return findByCompanyId(companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce price entries where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce price entries
	 * @param end the upper bound of the range of commerce price entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce price entries
	 */
	@Override
	public List<CommercePriceEntry> findByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<CommercePriceEntry> orderByComparator,
		boolean useFinderCache) {

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			CommercePriceEntry.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache && productionMode) {
				finderPath = _finderPathWithoutPaginationFindByCompanyId;
				finderArgs = new Object[] {companyId};
			}
		}
		else if (useFinderCache && productionMode) {
			finderPath = _finderPathWithPaginationFindByCompanyId;
			finderArgs = new Object[] {
				companyId, start, end, orderByComparator
			};
		}

		List<CommercePriceEntry> list = null;

		if (useFinderCache && productionMode) {
			list = (List<CommercePriceEntry>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (CommercePriceEntry commercePriceEntry : list) {
					if (companyId != commercePriceEntry.getCompanyId()) {
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

			sb.append(_SQL_SELECT_COMMERCEPRICEENTRY_WHERE);

			sb.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(CommercePriceEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(companyId);

				list = (List<CommercePriceEntry>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache && productionMode) {
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
	 * Returns the first commerce price entry in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce price entry
	 * @throws NoSuchPriceEntryException if a matching commerce price entry could not be found
	 */
	@Override
	public CommercePriceEntry findByCompanyId_First(
			long companyId,
			OrderByComparator<CommercePriceEntry> orderByComparator)
		throws NoSuchPriceEntryException {

		CommercePriceEntry commercePriceEntry = fetchByCompanyId_First(
			companyId, orderByComparator);

		if (commercePriceEntry != null) {
			return commercePriceEntry;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchPriceEntryException(sb.toString());
	}

	/**
	 * Returns the first commerce price entry in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce price entry, or <code>null</code> if a matching commerce price entry could not be found
	 */
	@Override
	public CommercePriceEntry fetchByCompanyId_First(
		long companyId,
		OrderByComparator<CommercePriceEntry> orderByComparator) {

		List<CommercePriceEntry> list = findByCompanyId(
			companyId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last commerce price entry in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce price entry
	 * @throws NoSuchPriceEntryException if a matching commerce price entry could not be found
	 */
	@Override
	public CommercePriceEntry findByCompanyId_Last(
			long companyId,
			OrderByComparator<CommercePriceEntry> orderByComparator)
		throws NoSuchPriceEntryException {

		CommercePriceEntry commercePriceEntry = fetchByCompanyId_Last(
			companyId, orderByComparator);

		if (commercePriceEntry != null) {
			return commercePriceEntry;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchPriceEntryException(sb.toString());
	}

	/**
	 * Returns the last commerce price entry in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce price entry, or <code>null</code> if a matching commerce price entry could not be found
	 */
	@Override
	public CommercePriceEntry fetchByCompanyId_Last(
		long companyId,
		OrderByComparator<CommercePriceEntry> orderByComparator) {

		int count = countByCompanyId(companyId);

		if (count == 0) {
			return null;
		}

		List<CommercePriceEntry> list = findByCompanyId(
			companyId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the commerce price entries before and after the current commerce price entry in the ordered set where companyId = &#63;.
	 *
	 * @param commercePriceEntryId the primary key of the current commerce price entry
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce price entry
	 * @throws NoSuchPriceEntryException if a commerce price entry with the primary key could not be found
	 */
	@Override
	public CommercePriceEntry[] findByCompanyId_PrevAndNext(
			long commercePriceEntryId, long companyId,
			OrderByComparator<CommercePriceEntry> orderByComparator)
		throws NoSuchPriceEntryException {

		CommercePriceEntry commercePriceEntry = findByPrimaryKey(
			commercePriceEntryId);

		Session session = null;

		try {
			session = openSession();

			CommercePriceEntry[] array = new CommercePriceEntryImpl[3];

			array[0] = getByCompanyId_PrevAndNext(
				session, commercePriceEntry, companyId, orderByComparator,
				true);

			array[1] = commercePriceEntry;

			array[2] = getByCompanyId_PrevAndNext(
				session, commercePriceEntry, companyId, orderByComparator,
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

	protected CommercePriceEntry getByCompanyId_PrevAndNext(
		Session session, CommercePriceEntry commercePriceEntry, long companyId,
		OrderByComparator<CommercePriceEntry> orderByComparator,
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

		sb.append(_SQL_SELECT_COMMERCEPRICEENTRY_WHERE);

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
			sb.append(CommercePriceEntryModelImpl.ORDER_BY_JPQL);
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
						commercePriceEntry)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<CommercePriceEntry> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the commerce price entries where companyId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 */
	@Override
	public void removeByCompanyId(long companyId) {
		for (CommercePriceEntry commercePriceEntry :
				findByCompanyId(
					companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(commercePriceEntry);
		}
	}

	/**
	 * Returns the number of commerce price entries where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching commerce price entries
	 */
	@Override
	public int countByCompanyId(long companyId) {
		boolean productionMode = ctPersistenceHelper.isProductionMode(
			CommercePriceEntry.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByCompanyId;

			finderArgs = new Object[] {companyId};

			count = (Long)finderCache.getResult(finderPath, finderArgs);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_COMMERCEPRICEENTRY_WHERE);

			sb.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(companyId);

				count = (Long)query.uniqueResult();

				if (productionMode) {
					finderCache.putResult(finderPath, finderArgs, count);
				}
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
		"commercePriceEntry.companyId = ?";

	private FinderPath _finderPathWithPaginationFindByCommercePriceListId;
	private FinderPath _finderPathWithoutPaginationFindByCommercePriceListId;
	private FinderPath _finderPathCountByCommercePriceListId;

	/**
	 * Returns all the commerce price entries where commercePriceListId = &#63;.
	 *
	 * @param commercePriceListId the commerce price list ID
	 * @return the matching commerce price entries
	 */
	@Override
	public List<CommercePriceEntry> findByCommercePriceListId(
		long commercePriceListId) {

		return findByCommercePriceListId(
			commercePriceListId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce price entries where commercePriceListId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceEntryModelImpl</code>.
	 * </p>
	 *
	 * @param commercePriceListId the commerce price list ID
	 * @param start the lower bound of the range of commerce price entries
	 * @param end the upper bound of the range of commerce price entries (not inclusive)
	 * @return the range of matching commerce price entries
	 */
	@Override
	public List<CommercePriceEntry> findByCommercePriceListId(
		long commercePriceListId, int start, int end) {

		return findByCommercePriceListId(commercePriceListId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce price entries where commercePriceListId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceEntryModelImpl</code>.
	 * </p>
	 *
	 * @param commercePriceListId the commerce price list ID
	 * @param start the lower bound of the range of commerce price entries
	 * @param end the upper bound of the range of commerce price entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce price entries
	 */
	@Override
	public List<CommercePriceEntry> findByCommercePriceListId(
		long commercePriceListId, int start, int end,
		OrderByComparator<CommercePriceEntry> orderByComparator) {

		return findByCommercePriceListId(
			commercePriceListId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce price entries where commercePriceListId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceEntryModelImpl</code>.
	 * </p>
	 *
	 * @param commercePriceListId the commerce price list ID
	 * @param start the lower bound of the range of commerce price entries
	 * @param end the upper bound of the range of commerce price entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce price entries
	 */
	@Override
	public List<CommercePriceEntry> findByCommercePriceListId(
		long commercePriceListId, int start, int end,
		OrderByComparator<CommercePriceEntry> orderByComparator,
		boolean useFinderCache) {

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			CommercePriceEntry.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache && productionMode) {
				finderPath =
					_finderPathWithoutPaginationFindByCommercePriceListId;
				finderArgs = new Object[] {commercePriceListId};
			}
		}
		else if (useFinderCache && productionMode) {
			finderPath = _finderPathWithPaginationFindByCommercePriceListId;
			finderArgs = new Object[] {
				commercePriceListId, start, end, orderByComparator
			};
		}

		List<CommercePriceEntry> list = null;

		if (useFinderCache && productionMode) {
			list = (List<CommercePriceEntry>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (CommercePriceEntry commercePriceEntry : list) {
					if (commercePriceListId !=
							commercePriceEntry.getCommercePriceListId()) {

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

			sb.append(_SQL_SELECT_COMMERCEPRICEENTRY_WHERE);

			sb.append(_FINDER_COLUMN_COMMERCEPRICELISTID_COMMERCEPRICELISTID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(CommercePriceEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(commercePriceListId);

				list = (List<CommercePriceEntry>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache && productionMode) {
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
	 * Returns the first commerce price entry in the ordered set where commercePriceListId = &#63;.
	 *
	 * @param commercePriceListId the commerce price list ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce price entry
	 * @throws NoSuchPriceEntryException if a matching commerce price entry could not be found
	 */
	@Override
	public CommercePriceEntry findByCommercePriceListId_First(
			long commercePriceListId,
			OrderByComparator<CommercePriceEntry> orderByComparator)
		throws NoSuchPriceEntryException {

		CommercePriceEntry commercePriceEntry =
			fetchByCommercePriceListId_First(
				commercePriceListId, orderByComparator);

		if (commercePriceEntry != null) {
			return commercePriceEntry;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("commercePriceListId=");
		sb.append(commercePriceListId);

		sb.append("}");

		throw new NoSuchPriceEntryException(sb.toString());
	}

	/**
	 * Returns the first commerce price entry in the ordered set where commercePriceListId = &#63;.
	 *
	 * @param commercePriceListId the commerce price list ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce price entry, or <code>null</code> if a matching commerce price entry could not be found
	 */
	@Override
	public CommercePriceEntry fetchByCommercePriceListId_First(
		long commercePriceListId,
		OrderByComparator<CommercePriceEntry> orderByComparator) {

		List<CommercePriceEntry> list = findByCommercePriceListId(
			commercePriceListId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last commerce price entry in the ordered set where commercePriceListId = &#63;.
	 *
	 * @param commercePriceListId the commerce price list ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce price entry
	 * @throws NoSuchPriceEntryException if a matching commerce price entry could not be found
	 */
	@Override
	public CommercePriceEntry findByCommercePriceListId_Last(
			long commercePriceListId,
			OrderByComparator<CommercePriceEntry> orderByComparator)
		throws NoSuchPriceEntryException {

		CommercePriceEntry commercePriceEntry = fetchByCommercePriceListId_Last(
			commercePriceListId, orderByComparator);

		if (commercePriceEntry != null) {
			return commercePriceEntry;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("commercePriceListId=");
		sb.append(commercePriceListId);

		sb.append("}");

		throw new NoSuchPriceEntryException(sb.toString());
	}

	/**
	 * Returns the last commerce price entry in the ordered set where commercePriceListId = &#63;.
	 *
	 * @param commercePriceListId the commerce price list ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce price entry, or <code>null</code> if a matching commerce price entry could not be found
	 */
	@Override
	public CommercePriceEntry fetchByCommercePriceListId_Last(
		long commercePriceListId,
		OrderByComparator<CommercePriceEntry> orderByComparator) {

		int count = countByCommercePriceListId(commercePriceListId);

		if (count == 0) {
			return null;
		}

		List<CommercePriceEntry> list = findByCommercePriceListId(
			commercePriceListId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the commerce price entries before and after the current commerce price entry in the ordered set where commercePriceListId = &#63;.
	 *
	 * @param commercePriceEntryId the primary key of the current commerce price entry
	 * @param commercePriceListId the commerce price list ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce price entry
	 * @throws NoSuchPriceEntryException if a commerce price entry with the primary key could not be found
	 */
	@Override
	public CommercePriceEntry[] findByCommercePriceListId_PrevAndNext(
			long commercePriceEntryId, long commercePriceListId,
			OrderByComparator<CommercePriceEntry> orderByComparator)
		throws NoSuchPriceEntryException {

		CommercePriceEntry commercePriceEntry = findByPrimaryKey(
			commercePriceEntryId);

		Session session = null;

		try {
			session = openSession();

			CommercePriceEntry[] array = new CommercePriceEntryImpl[3];

			array[0] = getByCommercePriceListId_PrevAndNext(
				session, commercePriceEntry, commercePriceListId,
				orderByComparator, true);

			array[1] = commercePriceEntry;

			array[2] = getByCommercePriceListId_PrevAndNext(
				session, commercePriceEntry, commercePriceListId,
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

	protected CommercePriceEntry getByCommercePriceListId_PrevAndNext(
		Session session, CommercePriceEntry commercePriceEntry,
		long commercePriceListId,
		OrderByComparator<CommercePriceEntry> orderByComparator,
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

		sb.append(_SQL_SELECT_COMMERCEPRICEENTRY_WHERE);

		sb.append(_FINDER_COLUMN_COMMERCEPRICELISTID_COMMERCEPRICELISTID_2);

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
			sb.append(CommercePriceEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(commercePriceListId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						commercePriceEntry)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<CommercePriceEntry> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the commerce price entries where commercePriceListId = &#63; from the database.
	 *
	 * @param commercePriceListId the commerce price list ID
	 */
	@Override
	public void removeByCommercePriceListId(long commercePriceListId) {
		for (CommercePriceEntry commercePriceEntry :
				findByCommercePriceListId(
					commercePriceListId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(commercePriceEntry);
		}
	}

	/**
	 * Returns the number of commerce price entries where commercePriceListId = &#63;.
	 *
	 * @param commercePriceListId the commerce price list ID
	 * @return the number of matching commerce price entries
	 */
	@Override
	public int countByCommercePriceListId(long commercePriceListId) {
		boolean productionMode = ctPersistenceHelper.isProductionMode(
			CommercePriceEntry.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByCommercePriceListId;

			finderArgs = new Object[] {commercePriceListId};

			count = (Long)finderCache.getResult(finderPath, finderArgs);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_COMMERCEPRICEENTRY_WHERE);

			sb.append(_FINDER_COLUMN_COMMERCEPRICELISTID_COMMERCEPRICELISTID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(commercePriceListId);

				count = (Long)query.uniqueResult();

				if (productionMode) {
					finderCache.putResult(finderPath, finderArgs, count);
				}
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
		_FINDER_COLUMN_COMMERCEPRICELISTID_COMMERCEPRICELISTID_2 =
			"commercePriceEntry.commercePriceListId = ?";

	private FinderPath _finderPathWithPaginationFindByCPInstanceUuid;
	private FinderPath _finderPathWithoutPaginationFindByCPInstanceUuid;
	private FinderPath _finderPathCountByCPInstanceUuid;

	/**
	 * Returns all the commerce price entries where CPInstanceUuid = &#63;.
	 *
	 * @param CPInstanceUuid the cp instance uuid
	 * @return the matching commerce price entries
	 */
	@Override
	public List<CommercePriceEntry> findByCPInstanceUuid(
		String CPInstanceUuid) {

		return findByCPInstanceUuid(
			CPInstanceUuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce price entries where CPInstanceUuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceEntryModelImpl</code>.
	 * </p>
	 *
	 * @param CPInstanceUuid the cp instance uuid
	 * @param start the lower bound of the range of commerce price entries
	 * @param end the upper bound of the range of commerce price entries (not inclusive)
	 * @return the range of matching commerce price entries
	 */
	@Override
	public List<CommercePriceEntry> findByCPInstanceUuid(
		String CPInstanceUuid, int start, int end) {

		return findByCPInstanceUuid(CPInstanceUuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce price entries where CPInstanceUuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceEntryModelImpl</code>.
	 * </p>
	 *
	 * @param CPInstanceUuid the cp instance uuid
	 * @param start the lower bound of the range of commerce price entries
	 * @param end the upper bound of the range of commerce price entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce price entries
	 */
	@Override
	public List<CommercePriceEntry> findByCPInstanceUuid(
		String CPInstanceUuid, int start, int end,
		OrderByComparator<CommercePriceEntry> orderByComparator) {

		return findByCPInstanceUuid(
			CPInstanceUuid, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce price entries where CPInstanceUuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceEntryModelImpl</code>.
	 * </p>
	 *
	 * @param CPInstanceUuid the cp instance uuid
	 * @param start the lower bound of the range of commerce price entries
	 * @param end the upper bound of the range of commerce price entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce price entries
	 */
	@Override
	public List<CommercePriceEntry> findByCPInstanceUuid(
		String CPInstanceUuid, int start, int end,
		OrderByComparator<CommercePriceEntry> orderByComparator,
		boolean useFinderCache) {

		CPInstanceUuid = Objects.toString(CPInstanceUuid, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			CommercePriceEntry.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache && productionMode) {
				finderPath = _finderPathWithoutPaginationFindByCPInstanceUuid;
				finderArgs = new Object[] {CPInstanceUuid};
			}
		}
		else if (useFinderCache && productionMode) {
			finderPath = _finderPathWithPaginationFindByCPInstanceUuid;
			finderArgs = new Object[] {
				CPInstanceUuid, start, end, orderByComparator
			};
		}

		List<CommercePriceEntry> list = null;

		if (useFinderCache && productionMode) {
			list = (List<CommercePriceEntry>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (CommercePriceEntry commercePriceEntry : list) {
					if (!CPInstanceUuid.equals(
							commercePriceEntry.getCPInstanceUuid())) {

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

			sb.append(_SQL_SELECT_COMMERCEPRICEENTRY_WHERE);

			boolean bindCPInstanceUuid = false;

			if (CPInstanceUuid.isEmpty()) {
				sb.append(_FINDER_COLUMN_CPINSTANCEUUID_CPINSTANCEUUID_3);
			}
			else {
				bindCPInstanceUuid = true;

				sb.append(_FINDER_COLUMN_CPINSTANCEUUID_CPINSTANCEUUID_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(CommercePriceEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindCPInstanceUuid) {
					queryPos.add(CPInstanceUuid);
				}

				list = (List<CommercePriceEntry>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache && productionMode) {
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
	 * Returns the first commerce price entry in the ordered set where CPInstanceUuid = &#63;.
	 *
	 * @param CPInstanceUuid the cp instance uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce price entry
	 * @throws NoSuchPriceEntryException if a matching commerce price entry could not be found
	 */
	@Override
	public CommercePriceEntry findByCPInstanceUuid_First(
			String CPInstanceUuid,
			OrderByComparator<CommercePriceEntry> orderByComparator)
		throws NoSuchPriceEntryException {

		CommercePriceEntry commercePriceEntry = fetchByCPInstanceUuid_First(
			CPInstanceUuid, orderByComparator);

		if (commercePriceEntry != null) {
			return commercePriceEntry;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("CPInstanceUuid=");
		sb.append(CPInstanceUuid);

		sb.append("}");

		throw new NoSuchPriceEntryException(sb.toString());
	}

	/**
	 * Returns the first commerce price entry in the ordered set where CPInstanceUuid = &#63;.
	 *
	 * @param CPInstanceUuid the cp instance uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce price entry, or <code>null</code> if a matching commerce price entry could not be found
	 */
	@Override
	public CommercePriceEntry fetchByCPInstanceUuid_First(
		String CPInstanceUuid,
		OrderByComparator<CommercePriceEntry> orderByComparator) {

		List<CommercePriceEntry> list = findByCPInstanceUuid(
			CPInstanceUuid, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last commerce price entry in the ordered set where CPInstanceUuid = &#63;.
	 *
	 * @param CPInstanceUuid the cp instance uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce price entry
	 * @throws NoSuchPriceEntryException if a matching commerce price entry could not be found
	 */
	@Override
	public CommercePriceEntry findByCPInstanceUuid_Last(
			String CPInstanceUuid,
			OrderByComparator<CommercePriceEntry> orderByComparator)
		throws NoSuchPriceEntryException {

		CommercePriceEntry commercePriceEntry = fetchByCPInstanceUuid_Last(
			CPInstanceUuid, orderByComparator);

		if (commercePriceEntry != null) {
			return commercePriceEntry;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("CPInstanceUuid=");
		sb.append(CPInstanceUuid);

		sb.append("}");

		throw new NoSuchPriceEntryException(sb.toString());
	}

	/**
	 * Returns the last commerce price entry in the ordered set where CPInstanceUuid = &#63;.
	 *
	 * @param CPInstanceUuid the cp instance uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce price entry, or <code>null</code> if a matching commerce price entry could not be found
	 */
	@Override
	public CommercePriceEntry fetchByCPInstanceUuid_Last(
		String CPInstanceUuid,
		OrderByComparator<CommercePriceEntry> orderByComparator) {

		int count = countByCPInstanceUuid(CPInstanceUuid);

		if (count == 0) {
			return null;
		}

		List<CommercePriceEntry> list = findByCPInstanceUuid(
			CPInstanceUuid, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the commerce price entries before and after the current commerce price entry in the ordered set where CPInstanceUuid = &#63;.
	 *
	 * @param commercePriceEntryId the primary key of the current commerce price entry
	 * @param CPInstanceUuid the cp instance uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce price entry
	 * @throws NoSuchPriceEntryException if a commerce price entry with the primary key could not be found
	 */
	@Override
	public CommercePriceEntry[] findByCPInstanceUuid_PrevAndNext(
			long commercePriceEntryId, String CPInstanceUuid,
			OrderByComparator<CommercePriceEntry> orderByComparator)
		throws NoSuchPriceEntryException {

		CPInstanceUuid = Objects.toString(CPInstanceUuid, "");

		CommercePriceEntry commercePriceEntry = findByPrimaryKey(
			commercePriceEntryId);

		Session session = null;

		try {
			session = openSession();

			CommercePriceEntry[] array = new CommercePriceEntryImpl[3];

			array[0] = getByCPInstanceUuid_PrevAndNext(
				session, commercePriceEntry, CPInstanceUuid, orderByComparator,
				true);

			array[1] = commercePriceEntry;

			array[2] = getByCPInstanceUuid_PrevAndNext(
				session, commercePriceEntry, CPInstanceUuid, orderByComparator,
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

	protected CommercePriceEntry getByCPInstanceUuid_PrevAndNext(
		Session session, CommercePriceEntry commercePriceEntry,
		String CPInstanceUuid,
		OrderByComparator<CommercePriceEntry> orderByComparator,
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

		sb.append(_SQL_SELECT_COMMERCEPRICEENTRY_WHERE);

		boolean bindCPInstanceUuid = false;

		if (CPInstanceUuid.isEmpty()) {
			sb.append(_FINDER_COLUMN_CPINSTANCEUUID_CPINSTANCEUUID_3);
		}
		else {
			bindCPInstanceUuid = true;

			sb.append(_FINDER_COLUMN_CPINSTANCEUUID_CPINSTANCEUUID_2);
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
			sb.append(CommercePriceEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		if (bindCPInstanceUuid) {
			queryPos.add(CPInstanceUuid);
		}

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						commercePriceEntry)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<CommercePriceEntry> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the commerce price entries where CPInstanceUuid = &#63; from the database.
	 *
	 * @param CPInstanceUuid the cp instance uuid
	 */
	@Override
	public void removeByCPInstanceUuid(String CPInstanceUuid) {
		for (CommercePriceEntry commercePriceEntry :
				findByCPInstanceUuid(
					CPInstanceUuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(commercePriceEntry);
		}
	}

	/**
	 * Returns the number of commerce price entries where CPInstanceUuid = &#63;.
	 *
	 * @param CPInstanceUuid the cp instance uuid
	 * @return the number of matching commerce price entries
	 */
	@Override
	public int countByCPInstanceUuid(String CPInstanceUuid) {
		CPInstanceUuid = Objects.toString(CPInstanceUuid, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			CommercePriceEntry.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByCPInstanceUuid;

			finderArgs = new Object[] {CPInstanceUuid};

			count = (Long)finderCache.getResult(finderPath, finderArgs);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_COMMERCEPRICEENTRY_WHERE);

			boolean bindCPInstanceUuid = false;

			if (CPInstanceUuid.isEmpty()) {
				sb.append(_FINDER_COLUMN_CPINSTANCEUUID_CPINSTANCEUUID_3);
			}
			else {
				bindCPInstanceUuid = true;

				sb.append(_FINDER_COLUMN_CPINSTANCEUUID_CPINSTANCEUUID_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindCPInstanceUuid) {
					queryPos.add(CPInstanceUuid);
				}

				count = (Long)query.uniqueResult();

				if (productionMode) {
					finderCache.putResult(finderPath, finderArgs, count);
				}
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

	private static final String _FINDER_COLUMN_CPINSTANCEUUID_CPINSTANCEUUID_2 =
		"commercePriceEntry.CPInstanceUuid = ?";

	private static final String _FINDER_COLUMN_CPINSTANCEUUID_CPINSTANCEUUID_3 =
		"(commercePriceEntry.CPInstanceUuid IS NULL OR commercePriceEntry.CPInstanceUuid = '')";

	private FinderPath _finderPathWithPaginationFindByC_C;
	private FinderPath _finderPathWithoutPaginationFindByC_C;
	private FinderPath _finderPathCountByC_C;

	/**
	 * Returns all the commerce price entries where commercePriceListId = &#63; and CPInstanceUuid = &#63;.
	 *
	 * @param commercePriceListId the commerce price list ID
	 * @param CPInstanceUuid the cp instance uuid
	 * @return the matching commerce price entries
	 */
	@Override
	public List<CommercePriceEntry> findByC_C(
		long commercePriceListId, String CPInstanceUuid) {

		return findByC_C(
			commercePriceListId, CPInstanceUuid, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce price entries where commercePriceListId = &#63; and CPInstanceUuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceEntryModelImpl</code>.
	 * </p>
	 *
	 * @param commercePriceListId the commerce price list ID
	 * @param CPInstanceUuid the cp instance uuid
	 * @param start the lower bound of the range of commerce price entries
	 * @param end the upper bound of the range of commerce price entries (not inclusive)
	 * @return the range of matching commerce price entries
	 */
	@Override
	public List<CommercePriceEntry> findByC_C(
		long commercePriceListId, String CPInstanceUuid, int start, int end) {

		return findByC_C(commercePriceListId, CPInstanceUuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce price entries where commercePriceListId = &#63; and CPInstanceUuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceEntryModelImpl</code>.
	 * </p>
	 *
	 * @param commercePriceListId the commerce price list ID
	 * @param CPInstanceUuid the cp instance uuid
	 * @param start the lower bound of the range of commerce price entries
	 * @param end the upper bound of the range of commerce price entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce price entries
	 */
	@Override
	public List<CommercePriceEntry> findByC_C(
		long commercePriceListId, String CPInstanceUuid, int start, int end,
		OrderByComparator<CommercePriceEntry> orderByComparator) {

		return findByC_C(
			commercePriceListId, CPInstanceUuid, start, end, orderByComparator,
			true);
	}

	/**
	 * Returns an ordered range of all the commerce price entries where commercePriceListId = &#63; and CPInstanceUuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceEntryModelImpl</code>.
	 * </p>
	 *
	 * @param commercePriceListId the commerce price list ID
	 * @param CPInstanceUuid the cp instance uuid
	 * @param start the lower bound of the range of commerce price entries
	 * @param end the upper bound of the range of commerce price entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce price entries
	 */
	@Override
	public List<CommercePriceEntry> findByC_C(
		long commercePriceListId, String CPInstanceUuid, int start, int end,
		OrderByComparator<CommercePriceEntry> orderByComparator,
		boolean useFinderCache) {

		CPInstanceUuid = Objects.toString(CPInstanceUuid, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			CommercePriceEntry.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache && productionMode) {
				finderPath = _finderPathWithoutPaginationFindByC_C;
				finderArgs = new Object[] {commercePriceListId, CPInstanceUuid};
			}
		}
		else if (useFinderCache && productionMode) {
			finderPath = _finderPathWithPaginationFindByC_C;
			finderArgs = new Object[] {
				commercePriceListId, CPInstanceUuid, start, end,
				orderByComparator
			};
		}

		List<CommercePriceEntry> list = null;

		if (useFinderCache && productionMode) {
			list = (List<CommercePriceEntry>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (CommercePriceEntry commercePriceEntry : list) {
					if ((commercePriceListId !=
							commercePriceEntry.getCommercePriceListId()) ||
						!CPInstanceUuid.equals(
							commercePriceEntry.getCPInstanceUuid())) {

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

			sb.append(_SQL_SELECT_COMMERCEPRICEENTRY_WHERE);

			sb.append(_FINDER_COLUMN_C_C_COMMERCEPRICELISTID_2);

			boolean bindCPInstanceUuid = false;

			if (CPInstanceUuid.isEmpty()) {
				sb.append(_FINDER_COLUMN_C_C_CPINSTANCEUUID_3);
			}
			else {
				bindCPInstanceUuid = true;

				sb.append(_FINDER_COLUMN_C_C_CPINSTANCEUUID_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(CommercePriceEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(commercePriceListId);

				if (bindCPInstanceUuid) {
					queryPos.add(CPInstanceUuid);
				}

				list = (List<CommercePriceEntry>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache && productionMode) {
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
	 * Returns the first commerce price entry in the ordered set where commercePriceListId = &#63; and CPInstanceUuid = &#63;.
	 *
	 * @param commercePriceListId the commerce price list ID
	 * @param CPInstanceUuid the cp instance uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce price entry
	 * @throws NoSuchPriceEntryException if a matching commerce price entry could not be found
	 */
	@Override
	public CommercePriceEntry findByC_C_First(
			long commercePriceListId, String CPInstanceUuid,
			OrderByComparator<CommercePriceEntry> orderByComparator)
		throws NoSuchPriceEntryException {

		CommercePriceEntry commercePriceEntry = fetchByC_C_First(
			commercePriceListId, CPInstanceUuid, orderByComparator);

		if (commercePriceEntry != null) {
			return commercePriceEntry;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("commercePriceListId=");
		sb.append(commercePriceListId);

		sb.append(", CPInstanceUuid=");
		sb.append(CPInstanceUuid);

		sb.append("}");

		throw new NoSuchPriceEntryException(sb.toString());
	}

	/**
	 * Returns the first commerce price entry in the ordered set where commercePriceListId = &#63; and CPInstanceUuid = &#63;.
	 *
	 * @param commercePriceListId the commerce price list ID
	 * @param CPInstanceUuid the cp instance uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce price entry, or <code>null</code> if a matching commerce price entry could not be found
	 */
	@Override
	public CommercePriceEntry fetchByC_C_First(
		long commercePriceListId, String CPInstanceUuid,
		OrderByComparator<CommercePriceEntry> orderByComparator) {

		List<CommercePriceEntry> list = findByC_C(
			commercePriceListId, CPInstanceUuid, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last commerce price entry in the ordered set where commercePriceListId = &#63; and CPInstanceUuid = &#63;.
	 *
	 * @param commercePriceListId the commerce price list ID
	 * @param CPInstanceUuid the cp instance uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce price entry
	 * @throws NoSuchPriceEntryException if a matching commerce price entry could not be found
	 */
	@Override
	public CommercePriceEntry findByC_C_Last(
			long commercePriceListId, String CPInstanceUuid,
			OrderByComparator<CommercePriceEntry> orderByComparator)
		throws NoSuchPriceEntryException {

		CommercePriceEntry commercePriceEntry = fetchByC_C_Last(
			commercePriceListId, CPInstanceUuid, orderByComparator);

		if (commercePriceEntry != null) {
			return commercePriceEntry;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("commercePriceListId=");
		sb.append(commercePriceListId);

		sb.append(", CPInstanceUuid=");
		sb.append(CPInstanceUuid);

		sb.append("}");

		throw new NoSuchPriceEntryException(sb.toString());
	}

	/**
	 * Returns the last commerce price entry in the ordered set where commercePriceListId = &#63; and CPInstanceUuid = &#63;.
	 *
	 * @param commercePriceListId the commerce price list ID
	 * @param CPInstanceUuid the cp instance uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce price entry, or <code>null</code> if a matching commerce price entry could not be found
	 */
	@Override
	public CommercePriceEntry fetchByC_C_Last(
		long commercePriceListId, String CPInstanceUuid,
		OrderByComparator<CommercePriceEntry> orderByComparator) {

		int count = countByC_C(commercePriceListId, CPInstanceUuid);

		if (count == 0) {
			return null;
		}

		List<CommercePriceEntry> list = findByC_C(
			commercePriceListId, CPInstanceUuid, count - 1, count,
			orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the commerce price entries before and after the current commerce price entry in the ordered set where commercePriceListId = &#63; and CPInstanceUuid = &#63;.
	 *
	 * @param commercePriceEntryId the primary key of the current commerce price entry
	 * @param commercePriceListId the commerce price list ID
	 * @param CPInstanceUuid the cp instance uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce price entry
	 * @throws NoSuchPriceEntryException if a commerce price entry with the primary key could not be found
	 */
	@Override
	public CommercePriceEntry[] findByC_C_PrevAndNext(
			long commercePriceEntryId, long commercePriceListId,
			String CPInstanceUuid,
			OrderByComparator<CommercePriceEntry> orderByComparator)
		throws NoSuchPriceEntryException {

		CPInstanceUuid = Objects.toString(CPInstanceUuid, "");

		CommercePriceEntry commercePriceEntry = findByPrimaryKey(
			commercePriceEntryId);

		Session session = null;

		try {
			session = openSession();

			CommercePriceEntry[] array = new CommercePriceEntryImpl[3];

			array[0] = getByC_C_PrevAndNext(
				session, commercePriceEntry, commercePriceListId,
				CPInstanceUuid, orderByComparator, true);

			array[1] = commercePriceEntry;

			array[2] = getByC_C_PrevAndNext(
				session, commercePriceEntry, commercePriceListId,
				CPInstanceUuid, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected CommercePriceEntry getByC_C_PrevAndNext(
		Session session, CommercePriceEntry commercePriceEntry,
		long commercePriceListId, String CPInstanceUuid,
		OrderByComparator<CommercePriceEntry> orderByComparator,
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

		sb.append(_SQL_SELECT_COMMERCEPRICEENTRY_WHERE);

		sb.append(_FINDER_COLUMN_C_C_COMMERCEPRICELISTID_2);

		boolean bindCPInstanceUuid = false;

		if (CPInstanceUuid.isEmpty()) {
			sb.append(_FINDER_COLUMN_C_C_CPINSTANCEUUID_3);
		}
		else {
			bindCPInstanceUuid = true;

			sb.append(_FINDER_COLUMN_C_C_CPINSTANCEUUID_2);
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
			sb.append(CommercePriceEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(commercePriceListId);

		if (bindCPInstanceUuid) {
			queryPos.add(CPInstanceUuid);
		}

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						commercePriceEntry)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<CommercePriceEntry> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the commerce price entries where commercePriceListId = &#63; and CPInstanceUuid = &#63; from the database.
	 *
	 * @param commercePriceListId the commerce price list ID
	 * @param CPInstanceUuid the cp instance uuid
	 */
	@Override
	public void removeByC_C(long commercePriceListId, String CPInstanceUuid) {
		for (CommercePriceEntry commercePriceEntry :
				findByC_C(
					commercePriceListId, CPInstanceUuid, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			remove(commercePriceEntry);
		}
	}

	/**
	 * Returns the number of commerce price entries where commercePriceListId = &#63; and CPInstanceUuid = &#63;.
	 *
	 * @param commercePriceListId the commerce price list ID
	 * @param CPInstanceUuid the cp instance uuid
	 * @return the number of matching commerce price entries
	 */
	@Override
	public int countByC_C(long commercePriceListId, String CPInstanceUuid) {
		CPInstanceUuid = Objects.toString(CPInstanceUuid, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			CommercePriceEntry.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByC_C;

			finderArgs = new Object[] {commercePriceListId, CPInstanceUuid};

			count = (Long)finderCache.getResult(finderPath, finderArgs);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_COMMERCEPRICEENTRY_WHERE);

			sb.append(_FINDER_COLUMN_C_C_COMMERCEPRICELISTID_2);

			boolean bindCPInstanceUuid = false;

			if (CPInstanceUuid.isEmpty()) {
				sb.append(_FINDER_COLUMN_C_C_CPINSTANCEUUID_3);
			}
			else {
				bindCPInstanceUuid = true;

				sb.append(_FINDER_COLUMN_C_C_CPINSTANCEUUID_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(commercePriceListId);

				if (bindCPInstanceUuid) {
					queryPos.add(CPInstanceUuid);
				}

				count = (Long)query.uniqueResult();

				if (productionMode) {
					finderCache.putResult(finderPath, finderArgs, count);
				}
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

	private static final String _FINDER_COLUMN_C_C_COMMERCEPRICELISTID_2 =
		"commercePriceEntry.commercePriceListId = ? AND ";

	private static final String _FINDER_COLUMN_C_C_CPINSTANCEUUID_2 =
		"commercePriceEntry.CPInstanceUuid = ?";

	private static final String _FINDER_COLUMN_C_C_CPINSTANCEUUID_3 =
		"(commercePriceEntry.CPInstanceUuid IS NULL OR commercePriceEntry.CPInstanceUuid = '')";

	private FinderPath _finderPathWithPaginationFindByLtD_S;
	private FinderPath _finderPathWithPaginationCountByLtD_S;

	/**
	 * Returns all the commerce price entries where displayDate &lt; &#63; and status = &#63;.
	 *
	 * @param displayDate the display date
	 * @param status the status
	 * @return the matching commerce price entries
	 */
	@Override
	public List<CommercePriceEntry> findByLtD_S(Date displayDate, int status) {
		return findByLtD_S(
			displayDate, status, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce price entries where displayDate &lt; &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceEntryModelImpl</code>.
	 * </p>
	 *
	 * @param displayDate the display date
	 * @param status the status
	 * @param start the lower bound of the range of commerce price entries
	 * @param end the upper bound of the range of commerce price entries (not inclusive)
	 * @return the range of matching commerce price entries
	 */
	@Override
	public List<CommercePriceEntry> findByLtD_S(
		Date displayDate, int status, int start, int end) {

		return findByLtD_S(displayDate, status, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce price entries where displayDate &lt; &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceEntryModelImpl</code>.
	 * </p>
	 *
	 * @param displayDate the display date
	 * @param status the status
	 * @param start the lower bound of the range of commerce price entries
	 * @param end the upper bound of the range of commerce price entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce price entries
	 */
	@Override
	public List<CommercePriceEntry> findByLtD_S(
		Date displayDate, int status, int start, int end,
		OrderByComparator<CommercePriceEntry> orderByComparator) {

		return findByLtD_S(
			displayDate, status, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce price entries where displayDate &lt; &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceEntryModelImpl</code>.
	 * </p>
	 *
	 * @param displayDate the display date
	 * @param status the status
	 * @param start the lower bound of the range of commerce price entries
	 * @param end the upper bound of the range of commerce price entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce price entries
	 */
	@Override
	public List<CommercePriceEntry> findByLtD_S(
		Date displayDate, int status, int start, int end,
		OrderByComparator<CommercePriceEntry> orderByComparator,
		boolean useFinderCache) {

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			CommercePriceEntry.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		finderPath = _finderPathWithPaginationFindByLtD_S;
		finderArgs = new Object[] {
			_getTime(displayDate), status, start, end, orderByComparator
		};

		List<CommercePriceEntry> list = null;

		if (useFinderCache && productionMode) {
			list = (List<CommercePriceEntry>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (CommercePriceEntry commercePriceEntry : list) {
					if ((displayDate.getTime() <=
							commercePriceEntry.getDisplayDate(
							).getTime()) ||
						(status != commercePriceEntry.getStatus())) {

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

			sb.append(_SQL_SELECT_COMMERCEPRICEENTRY_WHERE);

			boolean bindDisplayDate = false;

			if (displayDate == null) {
				sb.append(_FINDER_COLUMN_LTD_S_DISPLAYDATE_1);
			}
			else {
				bindDisplayDate = true;

				sb.append(_FINDER_COLUMN_LTD_S_DISPLAYDATE_2);
			}

			sb.append(_FINDER_COLUMN_LTD_S_STATUS_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(CommercePriceEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindDisplayDate) {
					queryPos.add(new Timestamp(displayDate.getTime()));
				}

				queryPos.add(status);

				list = (List<CommercePriceEntry>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache && productionMode) {
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
	 * Returns the first commerce price entry in the ordered set where displayDate &lt; &#63; and status = &#63;.
	 *
	 * @param displayDate the display date
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce price entry
	 * @throws NoSuchPriceEntryException if a matching commerce price entry could not be found
	 */
	@Override
	public CommercePriceEntry findByLtD_S_First(
			Date displayDate, int status,
			OrderByComparator<CommercePriceEntry> orderByComparator)
		throws NoSuchPriceEntryException {

		CommercePriceEntry commercePriceEntry = fetchByLtD_S_First(
			displayDate, status, orderByComparator);

		if (commercePriceEntry != null) {
			return commercePriceEntry;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("displayDate<");
		sb.append(displayDate);

		sb.append(", status=");
		sb.append(status);

		sb.append("}");

		throw new NoSuchPriceEntryException(sb.toString());
	}

	/**
	 * Returns the first commerce price entry in the ordered set where displayDate &lt; &#63; and status = &#63;.
	 *
	 * @param displayDate the display date
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce price entry, or <code>null</code> if a matching commerce price entry could not be found
	 */
	@Override
	public CommercePriceEntry fetchByLtD_S_First(
		Date displayDate, int status,
		OrderByComparator<CommercePriceEntry> orderByComparator) {

		List<CommercePriceEntry> list = findByLtD_S(
			displayDate, status, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last commerce price entry in the ordered set where displayDate &lt; &#63; and status = &#63;.
	 *
	 * @param displayDate the display date
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce price entry
	 * @throws NoSuchPriceEntryException if a matching commerce price entry could not be found
	 */
	@Override
	public CommercePriceEntry findByLtD_S_Last(
			Date displayDate, int status,
			OrderByComparator<CommercePriceEntry> orderByComparator)
		throws NoSuchPriceEntryException {

		CommercePriceEntry commercePriceEntry = fetchByLtD_S_Last(
			displayDate, status, orderByComparator);

		if (commercePriceEntry != null) {
			return commercePriceEntry;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("displayDate<");
		sb.append(displayDate);

		sb.append(", status=");
		sb.append(status);

		sb.append("}");

		throw new NoSuchPriceEntryException(sb.toString());
	}

	/**
	 * Returns the last commerce price entry in the ordered set where displayDate &lt; &#63; and status = &#63;.
	 *
	 * @param displayDate the display date
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce price entry, or <code>null</code> if a matching commerce price entry could not be found
	 */
	@Override
	public CommercePriceEntry fetchByLtD_S_Last(
		Date displayDate, int status,
		OrderByComparator<CommercePriceEntry> orderByComparator) {

		int count = countByLtD_S(displayDate, status);

		if (count == 0) {
			return null;
		}

		List<CommercePriceEntry> list = findByLtD_S(
			displayDate, status, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the commerce price entries before and after the current commerce price entry in the ordered set where displayDate &lt; &#63; and status = &#63;.
	 *
	 * @param commercePriceEntryId the primary key of the current commerce price entry
	 * @param displayDate the display date
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce price entry
	 * @throws NoSuchPriceEntryException if a commerce price entry with the primary key could not be found
	 */
	@Override
	public CommercePriceEntry[] findByLtD_S_PrevAndNext(
			long commercePriceEntryId, Date displayDate, int status,
			OrderByComparator<CommercePriceEntry> orderByComparator)
		throws NoSuchPriceEntryException {

		CommercePriceEntry commercePriceEntry = findByPrimaryKey(
			commercePriceEntryId);

		Session session = null;

		try {
			session = openSession();

			CommercePriceEntry[] array = new CommercePriceEntryImpl[3];

			array[0] = getByLtD_S_PrevAndNext(
				session, commercePriceEntry, displayDate, status,
				orderByComparator, true);

			array[1] = commercePriceEntry;

			array[2] = getByLtD_S_PrevAndNext(
				session, commercePriceEntry, displayDate, status,
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

	protected CommercePriceEntry getByLtD_S_PrevAndNext(
		Session session, CommercePriceEntry commercePriceEntry,
		Date displayDate, int status,
		OrderByComparator<CommercePriceEntry> orderByComparator,
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

		sb.append(_SQL_SELECT_COMMERCEPRICEENTRY_WHERE);

		boolean bindDisplayDate = false;

		if (displayDate == null) {
			sb.append(_FINDER_COLUMN_LTD_S_DISPLAYDATE_1);
		}
		else {
			bindDisplayDate = true;

			sb.append(_FINDER_COLUMN_LTD_S_DISPLAYDATE_2);
		}

		sb.append(_FINDER_COLUMN_LTD_S_STATUS_2);

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
			sb.append(CommercePriceEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		if (bindDisplayDate) {
			queryPos.add(new Timestamp(displayDate.getTime()));
		}

		queryPos.add(status);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						commercePriceEntry)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<CommercePriceEntry> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the commerce price entries where displayDate &lt; &#63; and status = &#63; from the database.
	 *
	 * @param displayDate the display date
	 * @param status the status
	 */
	@Override
	public void removeByLtD_S(Date displayDate, int status) {
		for (CommercePriceEntry commercePriceEntry :
				findByLtD_S(
					displayDate, status, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(commercePriceEntry);
		}
	}

	/**
	 * Returns the number of commerce price entries where displayDate &lt; &#63; and status = &#63;.
	 *
	 * @param displayDate the display date
	 * @param status the status
	 * @return the number of matching commerce price entries
	 */
	@Override
	public int countByLtD_S(Date displayDate, int status) {
		boolean productionMode = ctPersistenceHelper.isProductionMode(
			CommercePriceEntry.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathWithPaginationCountByLtD_S;

			finderArgs = new Object[] {_getTime(displayDate), status};

			count = (Long)finderCache.getResult(finderPath, finderArgs);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_COMMERCEPRICEENTRY_WHERE);

			boolean bindDisplayDate = false;

			if (displayDate == null) {
				sb.append(_FINDER_COLUMN_LTD_S_DISPLAYDATE_1);
			}
			else {
				bindDisplayDate = true;

				sb.append(_FINDER_COLUMN_LTD_S_DISPLAYDATE_2);
			}

			sb.append(_FINDER_COLUMN_LTD_S_STATUS_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindDisplayDate) {
					queryPos.add(new Timestamp(displayDate.getTime()));
				}

				queryPos.add(status);

				count = (Long)query.uniqueResult();

				if (productionMode) {
					finderCache.putResult(finderPath, finderArgs, count);
				}
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

	private static final String _FINDER_COLUMN_LTD_S_DISPLAYDATE_1 =
		"commercePriceEntry.displayDate IS NULL AND ";

	private static final String _FINDER_COLUMN_LTD_S_DISPLAYDATE_2 =
		"commercePriceEntry.displayDate < ? AND ";

	private static final String _FINDER_COLUMN_LTD_S_STATUS_2 =
		"commercePriceEntry.status = ?";

	private FinderPath _finderPathWithPaginationFindByLtE_S;
	private FinderPath _finderPathWithPaginationCountByLtE_S;

	/**
	 * Returns all the commerce price entries where expirationDate &lt; &#63; and status = &#63;.
	 *
	 * @param expirationDate the expiration date
	 * @param status the status
	 * @return the matching commerce price entries
	 */
	@Override
	public List<CommercePriceEntry> findByLtE_S(
		Date expirationDate, int status) {

		return findByLtE_S(
			expirationDate, status, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce price entries where expirationDate &lt; &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceEntryModelImpl</code>.
	 * </p>
	 *
	 * @param expirationDate the expiration date
	 * @param status the status
	 * @param start the lower bound of the range of commerce price entries
	 * @param end the upper bound of the range of commerce price entries (not inclusive)
	 * @return the range of matching commerce price entries
	 */
	@Override
	public List<CommercePriceEntry> findByLtE_S(
		Date expirationDate, int status, int start, int end) {

		return findByLtE_S(expirationDate, status, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce price entries where expirationDate &lt; &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceEntryModelImpl</code>.
	 * </p>
	 *
	 * @param expirationDate the expiration date
	 * @param status the status
	 * @param start the lower bound of the range of commerce price entries
	 * @param end the upper bound of the range of commerce price entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce price entries
	 */
	@Override
	public List<CommercePriceEntry> findByLtE_S(
		Date expirationDate, int status, int start, int end,
		OrderByComparator<CommercePriceEntry> orderByComparator) {

		return findByLtE_S(
			expirationDate, status, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce price entries where expirationDate &lt; &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceEntryModelImpl</code>.
	 * </p>
	 *
	 * @param expirationDate the expiration date
	 * @param status the status
	 * @param start the lower bound of the range of commerce price entries
	 * @param end the upper bound of the range of commerce price entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce price entries
	 */
	@Override
	public List<CommercePriceEntry> findByLtE_S(
		Date expirationDate, int status, int start, int end,
		OrderByComparator<CommercePriceEntry> orderByComparator,
		boolean useFinderCache) {

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			CommercePriceEntry.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		finderPath = _finderPathWithPaginationFindByLtE_S;
		finderArgs = new Object[] {
			_getTime(expirationDate), status, start, end, orderByComparator
		};

		List<CommercePriceEntry> list = null;

		if (useFinderCache && productionMode) {
			list = (List<CommercePriceEntry>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (CommercePriceEntry commercePriceEntry : list) {
					if ((expirationDate.getTime() <=
							commercePriceEntry.getExpirationDate(
							).getTime()) ||
						(status != commercePriceEntry.getStatus())) {

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

			sb.append(_SQL_SELECT_COMMERCEPRICEENTRY_WHERE);

			boolean bindExpirationDate = false;

			if (expirationDate == null) {
				sb.append(_FINDER_COLUMN_LTE_S_EXPIRATIONDATE_1);
			}
			else {
				bindExpirationDate = true;

				sb.append(_FINDER_COLUMN_LTE_S_EXPIRATIONDATE_2);
			}

			sb.append(_FINDER_COLUMN_LTE_S_STATUS_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(CommercePriceEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindExpirationDate) {
					queryPos.add(new Timestamp(expirationDate.getTime()));
				}

				queryPos.add(status);

				list = (List<CommercePriceEntry>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache && productionMode) {
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
	 * Returns the first commerce price entry in the ordered set where expirationDate &lt; &#63; and status = &#63;.
	 *
	 * @param expirationDate the expiration date
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce price entry
	 * @throws NoSuchPriceEntryException if a matching commerce price entry could not be found
	 */
	@Override
	public CommercePriceEntry findByLtE_S_First(
			Date expirationDate, int status,
			OrderByComparator<CommercePriceEntry> orderByComparator)
		throws NoSuchPriceEntryException {

		CommercePriceEntry commercePriceEntry = fetchByLtE_S_First(
			expirationDate, status, orderByComparator);

		if (commercePriceEntry != null) {
			return commercePriceEntry;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("expirationDate<");
		sb.append(expirationDate);

		sb.append(", status=");
		sb.append(status);

		sb.append("}");

		throw new NoSuchPriceEntryException(sb.toString());
	}

	/**
	 * Returns the first commerce price entry in the ordered set where expirationDate &lt; &#63; and status = &#63;.
	 *
	 * @param expirationDate the expiration date
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce price entry, or <code>null</code> if a matching commerce price entry could not be found
	 */
	@Override
	public CommercePriceEntry fetchByLtE_S_First(
		Date expirationDate, int status,
		OrderByComparator<CommercePriceEntry> orderByComparator) {

		List<CommercePriceEntry> list = findByLtE_S(
			expirationDate, status, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last commerce price entry in the ordered set where expirationDate &lt; &#63; and status = &#63;.
	 *
	 * @param expirationDate the expiration date
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce price entry
	 * @throws NoSuchPriceEntryException if a matching commerce price entry could not be found
	 */
	@Override
	public CommercePriceEntry findByLtE_S_Last(
			Date expirationDate, int status,
			OrderByComparator<CommercePriceEntry> orderByComparator)
		throws NoSuchPriceEntryException {

		CommercePriceEntry commercePriceEntry = fetchByLtE_S_Last(
			expirationDate, status, orderByComparator);

		if (commercePriceEntry != null) {
			return commercePriceEntry;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("expirationDate<");
		sb.append(expirationDate);

		sb.append(", status=");
		sb.append(status);

		sb.append("}");

		throw new NoSuchPriceEntryException(sb.toString());
	}

	/**
	 * Returns the last commerce price entry in the ordered set where expirationDate &lt; &#63; and status = &#63;.
	 *
	 * @param expirationDate the expiration date
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce price entry, or <code>null</code> if a matching commerce price entry could not be found
	 */
	@Override
	public CommercePriceEntry fetchByLtE_S_Last(
		Date expirationDate, int status,
		OrderByComparator<CommercePriceEntry> orderByComparator) {

		int count = countByLtE_S(expirationDate, status);

		if (count == 0) {
			return null;
		}

		List<CommercePriceEntry> list = findByLtE_S(
			expirationDate, status, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the commerce price entries before and after the current commerce price entry in the ordered set where expirationDate &lt; &#63; and status = &#63;.
	 *
	 * @param commercePriceEntryId the primary key of the current commerce price entry
	 * @param expirationDate the expiration date
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce price entry
	 * @throws NoSuchPriceEntryException if a commerce price entry with the primary key could not be found
	 */
	@Override
	public CommercePriceEntry[] findByLtE_S_PrevAndNext(
			long commercePriceEntryId, Date expirationDate, int status,
			OrderByComparator<CommercePriceEntry> orderByComparator)
		throws NoSuchPriceEntryException {

		CommercePriceEntry commercePriceEntry = findByPrimaryKey(
			commercePriceEntryId);

		Session session = null;

		try {
			session = openSession();

			CommercePriceEntry[] array = new CommercePriceEntryImpl[3];

			array[0] = getByLtE_S_PrevAndNext(
				session, commercePriceEntry, expirationDate, status,
				orderByComparator, true);

			array[1] = commercePriceEntry;

			array[2] = getByLtE_S_PrevAndNext(
				session, commercePriceEntry, expirationDate, status,
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

	protected CommercePriceEntry getByLtE_S_PrevAndNext(
		Session session, CommercePriceEntry commercePriceEntry,
		Date expirationDate, int status,
		OrderByComparator<CommercePriceEntry> orderByComparator,
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

		sb.append(_SQL_SELECT_COMMERCEPRICEENTRY_WHERE);

		boolean bindExpirationDate = false;

		if (expirationDate == null) {
			sb.append(_FINDER_COLUMN_LTE_S_EXPIRATIONDATE_1);
		}
		else {
			bindExpirationDate = true;

			sb.append(_FINDER_COLUMN_LTE_S_EXPIRATIONDATE_2);
		}

		sb.append(_FINDER_COLUMN_LTE_S_STATUS_2);

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
			sb.append(CommercePriceEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		if (bindExpirationDate) {
			queryPos.add(new Timestamp(expirationDate.getTime()));
		}

		queryPos.add(status);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						commercePriceEntry)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<CommercePriceEntry> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the commerce price entries where expirationDate &lt; &#63; and status = &#63; from the database.
	 *
	 * @param expirationDate the expiration date
	 * @param status the status
	 */
	@Override
	public void removeByLtE_S(Date expirationDate, int status) {
		for (CommercePriceEntry commercePriceEntry :
				findByLtE_S(
					expirationDate, status, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			remove(commercePriceEntry);
		}
	}

	/**
	 * Returns the number of commerce price entries where expirationDate &lt; &#63; and status = &#63;.
	 *
	 * @param expirationDate the expiration date
	 * @param status the status
	 * @return the number of matching commerce price entries
	 */
	@Override
	public int countByLtE_S(Date expirationDate, int status) {
		boolean productionMode = ctPersistenceHelper.isProductionMode(
			CommercePriceEntry.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathWithPaginationCountByLtE_S;

			finderArgs = new Object[] {_getTime(expirationDate), status};

			count = (Long)finderCache.getResult(finderPath, finderArgs);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_COMMERCEPRICEENTRY_WHERE);

			boolean bindExpirationDate = false;

			if (expirationDate == null) {
				sb.append(_FINDER_COLUMN_LTE_S_EXPIRATIONDATE_1);
			}
			else {
				bindExpirationDate = true;

				sb.append(_FINDER_COLUMN_LTE_S_EXPIRATIONDATE_2);
			}

			sb.append(_FINDER_COLUMN_LTE_S_STATUS_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindExpirationDate) {
					queryPos.add(new Timestamp(expirationDate.getTime()));
				}

				queryPos.add(status);

				count = (Long)query.uniqueResult();

				if (productionMode) {
					finderCache.putResult(finderPath, finderArgs, count);
				}
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

	private static final String _FINDER_COLUMN_LTE_S_EXPIRATIONDATE_1 =
		"commercePriceEntry.expirationDate IS NULL AND ";

	private static final String _FINDER_COLUMN_LTE_S_EXPIRATIONDATE_2 =
		"commercePriceEntry.expirationDate < ? AND ";

	private static final String _FINDER_COLUMN_LTE_S_STATUS_2 =
		"commercePriceEntry.status = ?";

	private FinderPath _finderPathWithPaginationFindByC_C_S;
	private FinderPath _finderPathWithoutPaginationFindByC_C_S;
	private FinderPath _finderPathCountByC_C_S;

	/**
	 * Returns all the commerce price entries where commercePriceListId = &#63; and CPInstanceUuid = &#63; and status = &#63;.
	 *
	 * @param commercePriceListId the commerce price list ID
	 * @param CPInstanceUuid the cp instance uuid
	 * @param status the status
	 * @return the matching commerce price entries
	 */
	@Override
	public List<CommercePriceEntry> findByC_C_S(
		long commercePriceListId, String CPInstanceUuid, int status) {

		return findByC_C_S(
			commercePriceListId, CPInstanceUuid, status, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce price entries where commercePriceListId = &#63; and CPInstanceUuid = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceEntryModelImpl</code>.
	 * </p>
	 *
	 * @param commercePriceListId the commerce price list ID
	 * @param CPInstanceUuid the cp instance uuid
	 * @param status the status
	 * @param start the lower bound of the range of commerce price entries
	 * @param end the upper bound of the range of commerce price entries (not inclusive)
	 * @return the range of matching commerce price entries
	 */
	@Override
	public List<CommercePriceEntry> findByC_C_S(
		long commercePriceListId, String CPInstanceUuid, int status, int start,
		int end) {

		return findByC_C_S(
			commercePriceListId, CPInstanceUuid, status, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce price entries where commercePriceListId = &#63; and CPInstanceUuid = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceEntryModelImpl</code>.
	 * </p>
	 *
	 * @param commercePriceListId the commerce price list ID
	 * @param CPInstanceUuid the cp instance uuid
	 * @param status the status
	 * @param start the lower bound of the range of commerce price entries
	 * @param end the upper bound of the range of commerce price entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce price entries
	 */
	@Override
	public List<CommercePriceEntry> findByC_C_S(
		long commercePriceListId, String CPInstanceUuid, int status, int start,
		int end, OrderByComparator<CommercePriceEntry> orderByComparator) {

		return findByC_C_S(
			commercePriceListId, CPInstanceUuid, status, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce price entries where commercePriceListId = &#63; and CPInstanceUuid = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceEntryModelImpl</code>.
	 * </p>
	 *
	 * @param commercePriceListId the commerce price list ID
	 * @param CPInstanceUuid the cp instance uuid
	 * @param status the status
	 * @param start the lower bound of the range of commerce price entries
	 * @param end the upper bound of the range of commerce price entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce price entries
	 */
	@Override
	public List<CommercePriceEntry> findByC_C_S(
		long commercePriceListId, String CPInstanceUuid, int status, int start,
		int end, OrderByComparator<CommercePriceEntry> orderByComparator,
		boolean useFinderCache) {

		CPInstanceUuid = Objects.toString(CPInstanceUuid, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			CommercePriceEntry.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache && productionMode) {
				finderPath = _finderPathWithoutPaginationFindByC_C_S;
				finderArgs = new Object[] {
					commercePriceListId, CPInstanceUuid, status
				};
			}
		}
		else if (useFinderCache && productionMode) {
			finderPath = _finderPathWithPaginationFindByC_C_S;
			finderArgs = new Object[] {
				commercePriceListId, CPInstanceUuid, status, start, end,
				orderByComparator
			};
		}

		List<CommercePriceEntry> list = null;

		if (useFinderCache && productionMode) {
			list = (List<CommercePriceEntry>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (CommercePriceEntry commercePriceEntry : list) {
					if ((commercePriceListId !=
							commercePriceEntry.getCommercePriceListId()) ||
						!CPInstanceUuid.equals(
							commercePriceEntry.getCPInstanceUuid()) ||
						(status != commercePriceEntry.getStatus())) {

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

			sb.append(_SQL_SELECT_COMMERCEPRICEENTRY_WHERE);

			sb.append(_FINDER_COLUMN_C_C_S_COMMERCEPRICELISTID_2);

			boolean bindCPInstanceUuid = false;

			if (CPInstanceUuid.isEmpty()) {
				sb.append(_FINDER_COLUMN_C_C_S_CPINSTANCEUUID_3);
			}
			else {
				bindCPInstanceUuid = true;

				sb.append(_FINDER_COLUMN_C_C_S_CPINSTANCEUUID_2);
			}

			sb.append(_FINDER_COLUMN_C_C_S_STATUS_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(CommercePriceEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(commercePriceListId);

				if (bindCPInstanceUuid) {
					queryPos.add(CPInstanceUuid);
				}

				queryPos.add(status);

				list = (List<CommercePriceEntry>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache && productionMode) {
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
	 * Returns the first commerce price entry in the ordered set where commercePriceListId = &#63; and CPInstanceUuid = &#63; and status = &#63;.
	 *
	 * @param commercePriceListId the commerce price list ID
	 * @param CPInstanceUuid the cp instance uuid
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce price entry
	 * @throws NoSuchPriceEntryException if a matching commerce price entry could not be found
	 */
	@Override
	public CommercePriceEntry findByC_C_S_First(
			long commercePriceListId, String CPInstanceUuid, int status,
			OrderByComparator<CommercePriceEntry> orderByComparator)
		throws NoSuchPriceEntryException {

		CommercePriceEntry commercePriceEntry = fetchByC_C_S_First(
			commercePriceListId, CPInstanceUuid, status, orderByComparator);

		if (commercePriceEntry != null) {
			return commercePriceEntry;
		}

		StringBundler sb = new StringBundler(8);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("commercePriceListId=");
		sb.append(commercePriceListId);

		sb.append(", CPInstanceUuid=");
		sb.append(CPInstanceUuid);

		sb.append(", status=");
		sb.append(status);

		sb.append("}");

		throw new NoSuchPriceEntryException(sb.toString());
	}

	/**
	 * Returns the first commerce price entry in the ordered set where commercePriceListId = &#63; and CPInstanceUuid = &#63; and status = &#63;.
	 *
	 * @param commercePriceListId the commerce price list ID
	 * @param CPInstanceUuid the cp instance uuid
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce price entry, or <code>null</code> if a matching commerce price entry could not be found
	 */
	@Override
	public CommercePriceEntry fetchByC_C_S_First(
		long commercePriceListId, String CPInstanceUuid, int status,
		OrderByComparator<CommercePriceEntry> orderByComparator) {

		List<CommercePriceEntry> list = findByC_C_S(
			commercePriceListId, CPInstanceUuid, status, 0, 1,
			orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last commerce price entry in the ordered set where commercePriceListId = &#63; and CPInstanceUuid = &#63; and status = &#63;.
	 *
	 * @param commercePriceListId the commerce price list ID
	 * @param CPInstanceUuid the cp instance uuid
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce price entry
	 * @throws NoSuchPriceEntryException if a matching commerce price entry could not be found
	 */
	@Override
	public CommercePriceEntry findByC_C_S_Last(
			long commercePriceListId, String CPInstanceUuid, int status,
			OrderByComparator<CommercePriceEntry> orderByComparator)
		throws NoSuchPriceEntryException {

		CommercePriceEntry commercePriceEntry = fetchByC_C_S_Last(
			commercePriceListId, CPInstanceUuid, status, orderByComparator);

		if (commercePriceEntry != null) {
			return commercePriceEntry;
		}

		StringBundler sb = new StringBundler(8);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("commercePriceListId=");
		sb.append(commercePriceListId);

		sb.append(", CPInstanceUuid=");
		sb.append(CPInstanceUuid);

		sb.append(", status=");
		sb.append(status);

		sb.append("}");

		throw new NoSuchPriceEntryException(sb.toString());
	}

	/**
	 * Returns the last commerce price entry in the ordered set where commercePriceListId = &#63; and CPInstanceUuid = &#63; and status = &#63;.
	 *
	 * @param commercePriceListId the commerce price list ID
	 * @param CPInstanceUuid the cp instance uuid
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce price entry, or <code>null</code> if a matching commerce price entry could not be found
	 */
	@Override
	public CommercePriceEntry fetchByC_C_S_Last(
		long commercePriceListId, String CPInstanceUuid, int status,
		OrderByComparator<CommercePriceEntry> orderByComparator) {

		int count = countByC_C_S(commercePriceListId, CPInstanceUuid, status);

		if (count == 0) {
			return null;
		}

		List<CommercePriceEntry> list = findByC_C_S(
			commercePriceListId, CPInstanceUuid, status, count - 1, count,
			orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the commerce price entries before and after the current commerce price entry in the ordered set where commercePriceListId = &#63; and CPInstanceUuid = &#63; and status = &#63;.
	 *
	 * @param commercePriceEntryId the primary key of the current commerce price entry
	 * @param commercePriceListId the commerce price list ID
	 * @param CPInstanceUuid the cp instance uuid
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce price entry
	 * @throws NoSuchPriceEntryException if a commerce price entry with the primary key could not be found
	 */
	@Override
	public CommercePriceEntry[] findByC_C_S_PrevAndNext(
			long commercePriceEntryId, long commercePriceListId,
			String CPInstanceUuid, int status,
			OrderByComparator<CommercePriceEntry> orderByComparator)
		throws NoSuchPriceEntryException {

		CPInstanceUuid = Objects.toString(CPInstanceUuid, "");

		CommercePriceEntry commercePriceEntry = findByPrimaryKey(
			commercePriceEntryId);

		Session session = null;

		try {
			session = openSession();

			CommercePriceEntry[] array = new CommercePriceEntryImpl[3];

			array[0] = getByC_C_S_PrevAndNext(
				session, commercePriceEntry, commercePriceListId,
				CPInstanceUuid, status, orderByComparator, true);

			array[1] = commercePriceEntry;

			array[2] = getByC_C_S_PrevAndNext(
				session, commercePriceEntry, commercePriceListId,
				CPInstanceUuid, status, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected CommercePriceEntry getByC_C_S_PrevAndNext(
		Session session, CommercePriceEntry commercePriceEntry,
		long commercePriceListId, String CPInstanceUuid, int status,
		OrderByComparator<CommercePriceEntry> orderByComparator,
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

		sb.append(_SQL_SELECT_COMMERCEPRICEENTRY_WHERE);

		sb.append(_FINDER_COLUMN_C_C_S_COMMERCEPRICELISTID_2);

		boolean bindCPInstanceUuid = false;

		if (CPInstanceUuid.isEmpty()) {
			sb.append(_FINDER_COLUMN_C_C_S_CPINSTANCEUUID_3);
		}
		else {
			bindCPInstanceUuid = true;

			sb.append(_FINDER_COLUMN_C_C_S_CPINSTANCEUUID_2);
		}

		sb.append(_FINDER_COLUMN_C_C_S_STATUS_2);

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
			sb.append(CommercePriceEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(commercePriceListId);

		if (bindCPInstanceUuid) {
			queryPos.add(CPInstanceUuid);
		}

		queryPos.add(status);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						commercePriceEntry)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<CommercePriceEntry> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the commerce price entries where commercePriceListId = &#63; and CPInstanceUuid = &#63; and status = &#63; from the database.
	 *
	 * @param commercePriceListId the commerce price list ID
	 * @param CPInstanceUuid the cp instance uuid
	 * @param status the status
	 */
	@Override
	public void removeByC_C_S(
		long commercePriceListId, String CPInstanceUuid, int status) {

		for (CommercePriceEntry commercePriceEntry :
				findByC_C_S(
					commercePriceListId, CPInstanceUuid, status,
					QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(commercePriceEntry);
		}
	}

	/**
	 * Returns the number of commerce price entries where commercePriceListId = &#63; and CPInstanceUuid = &#63; and status = &#63;.
	 *
	 * @param commercePriceListId the commerce price list ID
	 * @param CPInstanceUuid the cp instance uuid
	 * @param status the status
	 * @return the number of matching commerce price entries
	 */
	@Override
	public int countByC_C_S(
		long commercePriceListId, String CPInstanceUuid, int status) {

		CPInstanceUuid = Objects.toString(CPInstanceUuid, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			CommercePriceEntry.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByC_C_S;

			finderArgs = new Object[] {
				commercePriceListId, CPInstanceUuid, status
			};

			count = (Long)finderCache.getResult(finderPath, finderArgs);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_COUNT_COMMERCEPRICEENTRY_WHERE);

			sb.append(_FINDER_COLUMN_C_C_S_COMMERCEPRICELISTID_2);

			boolean bindCPInstanceUuid = false;

			if (CPInstanceUuid.isEmpty()) {
				sb.append(_FINDER_COLUMN_C_C_S_CPINSTANCEUUID_3);
			}
			else {
				bindCPInstanceUuid = true;

				sb.append(_FINDER_COLUMN_C_C_S_CPINSTANCEUUID_2);
			}

			sb.append(_FINDER_COLUMN_C_C_S_STATUS_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(commercePriceListId);

				if (bindCPInstanceUuid) {
					queryPos.add(CPInstanceUuid);
				}

				queryPos.add(status);

				count = (Long)query.uniqueResult();

				if (productionMode) {
					finderCache.putResult(finderPath, finderArgs, count);
				}
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

	private static final String _FINDER_COLUMN_C_C_S_COMMERCEPRICELISTID_2 =
		"commercePriceEntry.commercePriceListId = ? AND ";

	private static final String _FINDER_COLUMN_C_C_S_CPINSTANCEUUID_2 =
		"commercePriceEntry.CPInstanceUuid = ? AND ";

	private static final String _FINDER_COLUMN_C_C_S_CPINSTANCEUUID_3 =
		"(commercePriceEntry.CPInstanceUuid IS NULL OR commercePriceEntry.CPInstanceUuid = '') AND ";

	private static final String _FINDER_COLUMN_C_C_S_STATUS_2 =
		"commercePriceEntry.status = ?";

	private FinderPath _finderPathFetchByC_ERC;
	private FinderPath _finderPathCountByC_ERC;

	/**
	 * Returns the commerce price entry where companyId = &#63; and externalReferenceCode = &#63; or throws a <code>NoSuchPriceEntryException</code> if it could not be found.
	 *
	 * @param companyId the company ID
	 * @param externalReferenceCode the external reference code
	 * @return the matching commerce price entry
	 * @throws NoSuchPriceEntryException if a matching commerce price entry could not be found
	 */
	@Override
	public CommercePriceEntry findByC_ERC(
			long companyId, String externalReferenceCode)
		throws NoSuchPriceEntryException {

		CommercePriceEntry commercePriceEntry = fetchByC_ERC(
			companyId, externalReferenceCode);

		if (commercePriceEntry == null) {
			StringBundler sb = new StringBundler(6);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("companyId=");
			sb.append(companyId);

			sb.append(", externalReferenceCode=");
			sb.append(externalReferenceCode);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchPriceEntryException(sb.toString());
		}

		return commercePriceEntry;
	}

	/**
	 * Returns the commerce price entry where companyId = &#63; and externalReferenceCode = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param companyId the company ID
	 * @param externalReferenceCode the external reference code
	 * @return the matching commerce price entry, or <code>null</code> if a matching commerce price entry could not be found
	 */
	@Override
	public CommercePriceEntry fetchByC_ERC(
		long companyId, String externalReferenceCode) {

		return fetchByC_ERC(companyId, externalReferenceCode, true);
	}

	/**
	 * Returns the commerce price entry where companyId = &#63; and externalReferenceCode = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param companyId the company ID
	 * @param externalReferenceCode the external reference code
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching commerce price entry, or <code>null</code> if a matching commerce price entry could not be found
	 */
	@Override
	public CommercePriceEntry fetchByC_ERC(
		long companyId, String externalReferenceCode, boolean useFinderCache) {

		externalReferenceCode = Objects.toString(externalReferenceCode, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			CommercePriceEntry.class);

		Object[] finderArgs = null;

		if (useFinderCache && productionMode) {
			finderArgs = new Object[] {companyId, externalReferenceCode};
		}

		Object result = null;

		if (useFinderCache && productionMode) {
			result = finderCache.getResult(_finderPathFetchByC_ERC, finderArgs);
		}

		if (result instanceof CommercePriceEntry) {
			CommercePriceEntry commercePriceEntry = (CommercePriceEntry)result;

			if ((companyId != commercePriceEntry.getCompanyId()) ||
				!Objects.equals(
					externalReferenceCode,
					commercePriceEntry.getExternalReferenceCode())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_SELECT_COMMERCEPRICEENTRY_WHERE);

			sb.append(_FINDER_COLUMN_C_ERC_COMPANYID_2);

			boolean bindExternalReferenceCode = false;

			if (externalReferenceCode.isEmpty()) {
				sb.append(_FINDER_COLUMN_C_ERC_EXTERNALREFERENCECODE_3);
			}
			else {
				bindExternalReferenceCode = true;

				sb.append(_FINDER_COLUMN_C_ERC_EXTERNALREFERENCECODE_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(companyId);

				if (bindExternalReferenceCode) {
					queryPos.add(externalReferenceCode);
				}

				List<CommercePriceEntry> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache && productionMode) {
						finderCache.putResult(
							_finderPathFetchByC_ERC, finderArgs, list);
					}
				}
				else {
					if (list.size() > 1) {
						Collections.sort(list, Collections.reverseOrder());

						if (_log.isWarnEnabled()) {
							if (!productionMode || !useFinderCache) {
								finderArgs = new Object[] {
									companyId, externalReferenceCode
								};
							}

							_log.warn(
								"CommercePriceEntryPersistenceImpl.fetchByC_ERC(long, String, boolean) with parameters (" +
									StringUtil.merge(finderArgs) +
										") yields a result set with more than 1 result. This violates the logical unique restriction. There is no order guarantee on which result is returned by this finder.");
						}
					}

					CommercePriceEntry commercePriceEntry = list.get(0);

					result = commercePriceEntry;

					cacheResult(commercePriceEntry);
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
			return (CommercePriceEntry)result;
		}
	}

	/**
	 * Removes the commerce price entry where companyId = &#63; and externalReferenceCode = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param externalReferenceCode the external reference code
	 * @return the commerce price entry that was removed
	 */
	@Override
	public CommercePriceEntry removeByC_ERC(
			long companyId, String externalReferenceCode)
		throws NoSuchPriceEntryException {

		CommercePriceEntry commercePriceEntry = findByC_ERC(
			companyId, externalReferenceCode);

		return remove(commercePriceEntry);
	}

	/**
	 * Returns the number of commerce price entries where companyId = &#63; and externalReferenceCode = &#63;.
	 *
	 * @param companyId the company ID
	 * @param externalReferenceCode the external reference code
	 * @return the number of matching commerce price entries
	 */
	@Override
	public int countByC_ERC(long companyId, String externalReferenceCode) {
		externalReferenceCode = Objects.toString(externalReferenceCode, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			CommercePriceEntry.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByC_ERC;

			finderArgs = new Object[] {companyId, externalReferenceCode};

			count = (Long)finderCache.getResult(finderPath, finderArgs);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_COMMERCEPRICEENTRY_WHERE);

			sb.append(_FINDER_COLUMN_C_ERC_COMPANYID_2);

			boolean bindExternalReferenceCode = false;

			if (externalReferenceCode.isEmpty()) {
				sb.append(_FINDER_COLUMN_C_ERC_EXTERNALREFERENCECODE_3);
			}
			else {
				bindExternalReferenceCode = true;

				sb.append(_FINDER_COLUMN_C_ERC_EXTERNALREFERENCECODE_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(companyId);

				if (bindExternalReferenceCode) {
					queryPos.add(externalReferenceCode);
				}

				count = (Long)query.uniqueResult();

				if (productionMode) {
					finderCache.putResult(finderPath, finderArgs, count);
				}
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

	private static final String _FINDER_COLUMN_C_ERC_COMPANYID_2 =
		"commercePriceEntry.companyId = ? AND ";

	private static final String _FINDER_COLUMN_C_ERC_EXTERNALREFERENCECODE_2 =
		"commercePriceEntry.externalReferenceCode = ?";

	private static final String _FINDER_COLUMN_C_ERC_EXTERNALREFERENCECODE_3 =
		"(commercePriceEntry.externalReferenceCode IS NULL OR commercePriceEntry.externalReferenceCode = '')";

	public CommercePriceEntryPersistenceImpl() {
		Map<String, String> dbColumnNames = new HashMap<String, String>();

		dbColumnNames.put("uuid", "uuid_");

		setDBColumnNames(dbColumnNames);

		setModelClass(CommercePriceEntry.class);

		setModelImplClass(CommercePriceEntryImpl.class);
		setModelPKClass(long.class);

		setTable(CommercePriceEntryTable.INSTANCE);
	}

	/**
	 * Caches the commerce price entry in the entity cache if it is enabled.
	 *
	 * @param commercePriceEntry the commerce price entry
	 */
	@Override
	public void cacheResult(CommercePriceEntry commercePriceEntry) {
		if (commercePriceEntry.getCtCollectionId() != 0) {
			return;
		}

		entityCache.putResult(
			CommercePriceEntryImpl.class, commercePriceEntry.getPrimaryKey(),
			commercePriceEntry);

		finderCache.putResult(
			_finderPathFetchByC_ERC,
			new Object[] {
				commercePriceEntry.getCompanyId(),
				commercePriceEntry.getExternalReferenceCode()
			},
			commercePriceEntry);
	}

	private int _valueObjectFinderCacheListThreshold;

	/**
	 * Caches the commerce price entries in the entity cache if it is enabled.
	 *
	 * @param commercePriceEntries the commerce price entries
	 */
	@Override
	public void cacheResult(List<CommercePriceEntry> commercePriceEntries) {
		if ((_valueObjectFinderCacheListThreshold == 0) ||
			((_valueObjectFinderCacheListThreshold > 0) &&
			 (commercePriceEntries.size() >
				 _valueObjectFinderCacheListThreshold))) {

			return;
		}

		for (CommercePriceEntry commercePriceEntry : commercePriceEntries) {
			if (commercePriceEntry.getCtCollectionId() != 0) {
				continue;
			}

			if (entityCache.getResult(
					CommercePriceEntryImpl.class,
					commercePriceEntry.getPrimaryKey()) == null) {

				cacheResult(commercePriceEntry);
			}
		}
	}

	/**
	 * Clears the cache for all commerce price entries.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(CommercePriceEntryImpl.class);

		finderCache.clearCache(CommercePriceEntryImpl.class);
	}

	/**
	 * Clears the cache for the commerce price entry.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(CommercePriceEntry commercePriceEntry) {
		entityCache.removeResult(
			CommercePriceEntryImpl.class, commercePriceEntry);
	}

	@Override
	public void clearCache(List<CommercePriceEntry> commercePriceEntries) {
		for (CommercePriceEntry commercePriceEntry : commercePriceEntries) {
			entityCache.removeResult(
				CommercePriceEntryImpl.class, commercePriceEntry);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(CommercePriceEntryImpl.class);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(CommercePriceEntryImpl.class, primaryKey);
		}
	}

	protected void cacheUniqueFindersCache(
		CommercePriceEntryModelImpl commercePriceEntryModelImpl) {

		Object[] args = new Object[] {
			commercePriceEntryModelImpl.getCompanyId(),
			commercePriceEntryModelImpl.getExternalReferenceCode()
		};

		finderCache.putResult(_finderPathCountByC_ERC, args, Long.valueOf(1));
		finderCache.putResult(
			_finderPathFetchByC_ERC, args, commercePriceEntryModelImpl);
	}

	/**
	 * Creates a new commerce price entry with the primary key. Does not add the commerce price entry to the database.
	 *
	 * @param commercePriceEntryId the primary key for the new commerce price entry
	 * @return the new commerce price entry
	 */
	@Override
	public CommercePriceEntry create(long commercePriceEntryId) {
		CommercePriceEntry commercePriceEntry = new CommercePriceEntryImpl();

		commercePriceEntry.setNew(true);
		commercePriceEntry.setPrimaryKey(commercePriceEntryId);

		String uuid = PortalUUIDUtil.generate();

		commercePriceEntry.setUuid(uuid);

		commercePriceEntry.setCompanyId(CompanyThreadLocal.getCompanyId());

		return commercePriceEntry;
	}

	/**
	 * Removes the commerce price entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param commercePriceEntryId the primary key of the commerce price entry
	 * @return the commerce price entry that was removed
	 * @throws NoSuchPriceEntryException if a commerce price entry with the primary key could not be found
	 */
	@Override
	public CommercePriceEntry remove(long commercePriceEntryId)
		throws NoSuchPriceEntryException {

		return remove((Serializable)commercePriceEntryId);
	}

	/**
	 * Removes the commerce price entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the commerce price entry
	 * @return the commerce price entry that was removed
	 * @throws NoSuchPriceEntryException if a commerce price entry with the primary key could not be found
	 */
	@Override
	public CommercePriceEntry remove(Serializable primaryKey)
		throws NoSuchPriceEntryException {

		Session session = null;

		try {
			session = openSession();

			CommercePriceEntry commercePriceEntry =
				(CommercePriceEntry)session.get(
					CommercePriceEntryImpl.class, primaryKey);

			if (commercePriceEntry == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchPriceEntryException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(commercePriceEntry);
		}
		catch (NoSuchPriceEntryException noSuchEntityException) {
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
	protected CommercePriceEntry removeImpl(
		CommercePriceEntry commercePriceEntry) {

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(commercePriceEntry)) {
				commercePriceEntry = (CommercePriceEntry)session.get(
					CommercePriceEntryImpl.class,
					commercePriceEntry.getPrimaryKeyObj());
			}

			if ((commercePriceEntry != null) &&
				ctPersistenceHelper.isRemove(commercePriceEntry)) {

				session.delete(commercePriceEntry);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (commercePriceEntry != null) {
			clearCache(commercePriceEntry);
		}

		return commercePriceEntry;
	}

	@Override
	public CommercePriceEntry updateImpl(
		CommercePriceEntry commercePriceEntry) {

		boolean isNew = commercePriceEntry.isNew();

		if (!(commercePriceEntry instanceof CommercePriceEntryModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(commercePriceEntry.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					commercePriceEntry);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in commercePriceEntry proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom CommercePriceEntry implementation " +
					commercePriceEntry.getClass());
		}

		CommercePriceEntryModelImpl commercePriceEntryModelImpl =
			(CommercePriceEntryModelImpl)commercePriceEntry;

		if (Validator.isNull(commercePriceEntry.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			commercePriceEntry.setUuid(uuid);
		}

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Date date = new Date();

		if (isNew && (commercePriceEntry.getCreateDate() == null)) {
			if (serviceContext == null) {
				commercePriceEntry.setCreateDate(date);
			}
			else {
				commercePriceEntry.setCreateDate(
					serviceContext.getCreateDate(date));
			}
		}

		if (!commercePriceEntryModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				commercePriceEntry.setModifiedDate(date);
			}
			else {
				commercePriceEntry.setModifiedDate(
					serviceContext.getModifiedDate(date));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (ctPersistenceHelper.isInsert(commercePriceEntry)) {
				if (!isNew) {
					session.evict(
						CommercePriceEntryImpl.class,
						commercePriceEntry.getPrimaryKeyObj());
				}

				session.save(commercePriceEntry);
			}
			else {
				commercePriceEntry = (CommercePriceEntry)session.merge(
					commercePriceEntry);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (commercePriceEntry.getCtCollectionId() != 0) {
			if (isNew) {
				commercePriceEntry.setNew(false);
			}

			commercePriceEntry.resetOriginalValues();

			return commercePriceEntry;
		}

		entityCache.putResult(
			CommercePriceEntryImpl.class, commercePriceEntryModelImpl, false,
			true);

		cacheUniqueFindersCache(commercePriceEntryModelImpl);

		if (isNew) {
			commercePriceEntry.setNew(false);
		}

		commercePriceEntry.resetOriginalValues();

		return commercePriceEntry;
	}

	/**
	 * Returns the commerce price entry with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the commerce price entry
	 * @return the commerce price entry
	 * @throws NoSuchPriceEntryException if a commerce price entry with the primary key could not be found
	 */
	@Override
	public CommercePriceEntry findByPrimaryKey(Serializable primaryKey)
		throws NoSuchPriceEntryException {

		CommercePriceEntry commercePriceEntry = fetchByPrimaryKey(primaryKey);

		if (commercePriceEntry == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchPriceEntryException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return commercePriceEntry;
	}

	/**
	 * Returns the commerce price entry with the primary key or throws a <code>NoSuchPriceEntryException</code> if it could not be found.
	 *
	 * @param commercePriceEntryId the primary key of the commerce price entry
	 * @return the commerce price entry
	 * @throws NoSuchPriceEntryException if a commerce price entry with the primary key could not be found
	 */
	@Override
	public CommercePriceEntry findByPrimaryKey(long commercePriceEntryId)
		throws NoSuchPriceEntryException {

		return findByPrimaryKey((Serializable)commercePriceEntryId);
	}

	/**
	 * Returns the commerce price entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the commerce price entry
	 * @return the commerce price entry, or <code>null</code> if a commerce price entry with the primary key could not be found
	 */
	@Override
	public CommercePriceEntry fetchByPrimaryKey(Serializable primaryKey) {
		if (ctPersistenceHelper.isProductionMode(CommercePriceEntry.class)) {
			return super.fetchByPrimaryKey(primaryKey);
		}

		CommercePriceEntry commercePriceEntry = null;

		Session session = null;

		try {
			session = openSession();

			commercePriceEntry = (CommercePriceEntry)session.get(
				CommercePriceEntryImpl.class, primaryKey);

			if (commercePriceEntry != null) {
				cacheResult(commercePriceEntry);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		return commercePriceEntry;
	}

	/**
	 * Returns the commerce price entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param commercePriceEntryId the primary key of the commerce price entry
	 * @return the commerce price entry, or <code>null</code> if a commerce price entry with the primary key could not be found
	 */
	@Override
	public CommercePriceEntry fetchByPrimaryKey(long commercePriceEntryId) {
		return fetchByPrimaryKey((Serializable)commercePriceEntryId);
	}

	@Override
	public Map<Serializable, CommercePriceEntry> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		if (ctPersistenceHelper.isProductionMode(CommercePriceEntry.class)) {
			return super.fetchByPrimaryKeys(primaryKeys);
		}

		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, CommercePriceEntry> map =
			new HashMap<Serializable, CommercePriceEntry>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			CommercePriceEntry commercePriceEntry = fetchByPrimaryKey(
				primaryKey);

			if (commercePriceEntry != null) {
				map.put(primaryKey, commercePriceEntry);
			}

			return map;
		}

		if ((databaseInMaxParameters > 0) &&
			(primaryKeys.size() > databaseInMaxParameters)) {

			Iterator<Serializable> iterator = primaryKeys.iterator();

			while (iterator.hasNext()) {
				Set<Serializable> page = new HashSet<>();

				for (int i = 0;
					 (i < databaseInMaxParameters) && iterator.hasNext(); i++) {

					page.add(iterator.next());
				}

				map.putAll(fetchByPrimaryKeys(page));
			}

			return map;
		}

		StringBundler sb = new StringBundler((primaryKeys.size() * 2) + 1);

		sb.append(getSelectSQL());
		sb.append(" WHERE ");
		sb.append(getPKDBName());
		sb.append(" IN (");

		for (Serializable primaryKey : primaryKeys) {
			sb.append((long)primaryKey);

			sb.append(",");
		}

		sb.setIndex(sb.index() - 1);

		sb.append(")");

		String sql = sb.toString();

		Session session = null;

		try {
			session = openSession();

			Query query = session.createQuery(sql);

			for (CommercePriceEntry commercePriceEntry :
					(List<CommercePriceEntry>)query.list()) {

				map.put(
					commercePriceEntry.getPrimaryKeyObj(), commercePriceEntry);

				cacheResult(commercePriceEntry);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		return map;
	}

	/**
	 * Returns all the commerce price entries.
	 *
	 * @return the commerce price entries
	 */
	@Override
	public List<CommercePriceEntry> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce price entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce price entries
	 * @param end the upper bound of the range of commerce price entries (not inclusive)
	 * @return the range of commerce price entries
	 */
	@Override
	public List<CommercePriceEntry> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce price entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce price entries
	 * @param end the upper bound of the range of commerce price entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of commerce price entries
	 */
	@Override
	public List<CommercePriceEntry> findAll(
		int start, int end,
		OrderByComparator<CommercePriceEntry> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce price entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce price entries
	 * @param end the upper bound of the range of commerce price entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of commerce price entries
	 */
	@Override
	public List<CommercePriceEntry> findAll(
		int start, int end,
		OrderByComparator<CommercePriceEntry> orderByComparator,
		boolean useFinderCache) {

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			CommercePriceEntry.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache && productionMode) {
				finderPath = _finderPathWithoutPaginationFindAll;
				finderArgs = FINDER_ARGS_EMPTY;
			}
		}
		else if (useFinderCache && productionMode) {
			finderPath = _finderPathWithPaginationFindAll;
			finderArgs = new Object[] {start, end, orderByComparator};
		}

		List<CommercePriceEntry> list = null;

		if (useFinderCache && productionMode) {
			list = (List<CommercePriceEntry>)finderCache.getResult(
				finderPath, finderArgs);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_COMMERCEPRICEENTRY);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_COMMERCEPRICEENTRY;

				sql = sql.concat(CommercePriceEntryModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<CommercePriceEntry>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache && productionMode) {
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
	 * Removes all the commerce price entries from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (CommercePriceEntry commercePriceEntry : findAll()) {
			remove(commercePriceEntry);
		}
	}

	/**
	 * Returns the number of commerce price entries.
	 *
	 * @return the number of commerce price entries
	 */
	@Override
	public int countAll() {
		boolean productionMode = ctPersistenceHelper.isProductionMode(
			CommercePriceEntry.class);

		Long count = null;

		if (productionMode) {
			count = (Long)finderCache.getResult(
				_finderPathCountAll, FINDER_ARGS_EMPTY);
		}

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(
					_SQL_COUNT_COMMERCEPRICEENTRY);

				count = (Long)query.uniqueResult();

				if (productionMode) {
					finderCache.putResult(
						_finderPathCountAll, FINDER_ARGS_EMPTY, count);
				}
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
		return "commercePriceEntryId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_COMMERCEPRICEENTRY;
	}

	@Override
	public Set<String> getCTColumnNames(
		CTColumnResolutionType ctColumnResolutionType) {

		return _ctColumnNamesMap.getOrDefault(
			ctColumnResolutionType, Collections.emptySet());
	}

	@Override
	public List<String> getMappingTableNames() {
		return _mappingTableNames;
	}

	@Override
	public Map<String, Integer> getTableColumnsMap() {
		return CommercePriceEntryModelImpl.TABLE_COLUMNS_MAP;
	}

	@Override
	public String getTableName() {
		return "CommercePriceEntry";
	}

	@Override
	public List<String[]> getUniqueIndexColumnNames() {
		return _uniqueIndexColumnNames;
	}

	private static final Map<CTColumnResolutionType, Set<String>>
		_ctColumnNamesMap = new EnumMap<CTColumnResolutionType, Set<String>>(
			CTColumnResolutionType.class);
	private static final List<String> _mappingTableNames =
		new ArrayList<String>();
	private static final List<String[]> _uniqueIndexColumnNames =
		new ArrayList<String[]>();

	static {
		Set<String> ctControlColumnNames = new HashSet<String>();
		Set<String> ctIgnoreColumnNames = new HashSet<String>();
		Set<String> ctStrictColumnNames = new HashSet<String>();

		ctControlColumnNames.add("mvccVersion");
		ctControlColumnNames.add("ctCollectionId");
		ctStrictColumnNames.add("uuid_");
		ctStrictColumnNames.add("externalReferenceCode");
		ctStrictColumnNames.add("companyId");
		ctStrictColumnNames.add("userId");
		ctStrictColumnNames.add("userName");
		ctStrictColumnNames.add("createDate");
		ctIgnoreColumnNames.add("modifiedDate");
		ctStrictColumnNames.add("commercePriceListId");
		ctStrictColumnNames.add("CPInstanceUuid");
		ctStrictColumnNames.add("CProductId");
		ctStrictColumnNames.add("price");
		ctStrictColumnNames.add("promoPrice");
		ctStrictColumnNames.add("discountDiscovery");
		ctStrictColumnNames.add("discountLevel1");
		ctStrictColumnNames.add("discountLevel2");
		ctStrictColumnNames.add("discountLevel3");
		ctStrictColumnNames.add("discountLevel4");
		ctStrictColumnNames.add("hasTierPrice");
		ctStrictColumnNames.add("bulkPricing");
		ctStrictColumnNames.add("displayDate");
		ctStrictColumnNames.add("expirationDate");
		ctStrictColumnNames.add("lastPublishDate");
		ctStrictColumnNames.add("status");
		ctStrictColumnNames.add("statusByUserId");
		ctStrictColumnNames.add("statusByUserName");
		ctStrictColumnNames.add("statusDate");

		_ctColumnNamesMap.put(
			CTColumnResolutionType.CONTROL, ctControlColumnNames);
		_ctColumnNamesMap.put(
			CTColumnResolutionType.IGNORE, ctIgnoreColumnNames);
		_ctColumnNamesMap.put(
			CTColumnResolutionType.PK,
			Collections.singleton("commercePriceEntryId"));
		_ctColumnNamesMap.put(
			CTColumnResolutionType.STRICT, ctStrictColumnNames);
	}

	/**
	 * Initializes the commerce price entry persistence.
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

		_finderPathWithPaginationFindByCommercePriceListId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByCommercePriceListId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"commercePriceListId"}, true);

		_finderPathWithoutPaginationFindByCommercePriceListId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByCommercePriceListId", new String[] {Long.class.getName()},
			new String[] {"commercePriceListId"}, true);

		_finderPathCountByCommercePriceListId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByCommercePriceListId", new String[] {Long.class.getName()},
			new String[] {"commercePriceListId"}, false);

		_finderPathWithPaginationFindByCPInstanceUuid = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByCPInstanceUuid",
			new String[] {
				String.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"CPInstanceUuid"}, true);

		_finderPathWithoutPaginationFindByCPInstanceUuid = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByCPInstanceUuid",
			new String[] {String.class.getName()},
			new String[] {"CPInstanceUuid"}, true);

		_finderPathCountByCPInstanceUuid = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByCPInstanceUuid",
			new String[] {String.class.getName()},
			new String[] {"CPInstanceUuid"}, false);

		_finderPathWithPaginationFindByC_C = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByC_C",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {"commercePriceListId", "CPInstanceUuid"}, true);

		_finderPathWithoutPaginationFindByC_C = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByC_C",
			new String[] {Long.class.getName(), String.class.getName()},
			new String[] {"commercePriceListId", "CPInstanceUuid"}, true);

		_finderPathCountByC_C = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_C",
			new String[] {Long.class.getName(), String.class.getName()},
			new String[] {"commercePriceListId", "CPInstanceUuid"}, false);

		_finderPathWithPaginationFindByLtD_S = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByLtD_S",
			new String[] {
				Date.class.getName(), Integer.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {"displayDate", "status"}, true);

		_finderPathWithPaginationCountByLtD_S = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "countByLtD_S",
			new String[] {Date.class.getName(), Integer.class.getName()},
			new String[] {"displayDate", "status"}, false);

		_finderPathWithPaginationFindByLtE_S = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByLtE_S",
			new String[] {
				Date.class.getName(), Integer.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {"expirationDate", "status"}, true);

		_finderPathWithPaginationCountByLtE_S = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "countByLtE_S",
			new String[] {Date.class.getName(), Integer.class.getName()},
			new String[] {"expirationDate", "status"}, false);

		_finderPathWithPaginationFindByC_C_S = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByC_C_S",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"commercePriceListId", "CPInstanceUuid", "status"},
			true);

		_finderPathWithoutPaginationFindByC_C_S = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByC_C_S",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Integer.class.getName()
			},
			new String[] {"commercePriceListId", "CPInstanceUuid", "status"},
			true);

		_finderPathCountByC_C_S = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_C_S",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Integer.class.getName()
			},
			new String[] {"commercePriceListId", "CPInstanceUuid", "status"},
			false);

		_finderPathFetchByC_ERC = new FinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByC_ERC",
			new String[] {Long.class.getName(), String.class.getName()},
			new String[] {"companyId", "externalReferenceCode"}, true);

		_finderPathCountByC_ERC = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_ERC",
			new String[] {Long.class.getName(), String.class.getName()},
			new String[] {"companyId", "externalReferenceCode"}, false);

		_setCommercePriceEntryUtilPersistence(this);
	}

	public void destroy() {
		_setCommercePriceEntryUtilPersistence(null);

		entityCache.removeCache(CommercePriceEntryImpl.class.getName());
	}

	private void _setCommercePriceEntryUtilPersistence(
		CommercePriceEntryPersistence commercePriceEntryPersistence) {

		try {
			Field field = CommercePriceEntryUtil.class.getDeclaredField(
				"_persistence");

			field.setAccessible(true);

			field.set(null, commercePriceEntryPersistence);
		}
		catch (ReflectiveOperationException reflectiveOperationException) {
			throw new RuntimeException(reflectiveOperationException);
		}
	}

	@ServiceReference(type = CTPersistenceHelper.class)
	protected CTPersistenceHelper ctPersistenceHelper;

	@ServiceReference(type = EntityCache.class)
	protected EntityCache entityCache;

	@ServiceReference(type = FinderCache.class)
	protected FinderCache finderCache;

	private static Long _getTime(Date date) {
		if (date == null) {
			return null;
		}

		return date.getTime();
	}

	private static final String _SQL_SELECT_COMMERCEPRICEENTRY =
		"SELECT commercePriceEntry FROM CommercePriceEntry commercePriceEntry";

	private static final String _SQL_SELECT_COMMERCEPRICEENTRY_WHERE =
		"SELECT commercePriceEntry FROM CommercePriceEntry commercePriceEntry WHERE ";

	private static final String _SQL_COUNT_COMMERCEPRICEENTRY =
		"SELECT COUNT(commercePriceEntry) FROM CommercePriceEntry commercePriceEntry";

	private static final String _SQL_COUNT_COMMERCEPRICEENTRY_WHERE =
		"SELECT COUNT(commercePriceEntry) FROM CommercePriceEntry commercePriceEntry WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS = "commercePriceEntry.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No CommercePriceEntry exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No CommercePriceEntry exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		CommercePriceEntryPersistenceImpl.class);

	private static final Set<String> _badColumnNames = SetUtil.fromArray(
		new String[] {"uuid"});

	@Override
	protected FinderCache getFinderCache() {
		return finderCache;
	}

}