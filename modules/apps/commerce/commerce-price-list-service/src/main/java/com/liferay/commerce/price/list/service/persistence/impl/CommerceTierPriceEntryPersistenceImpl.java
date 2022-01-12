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

import com.liferay.commerce.price.list.exception.NoSuchTierPriceEntryException;
import com.liferay.commerce.price.list.model.CommerceTierPriceEntry;
import com.liferay.commerce.price.list.model.CommerceTierPriceEntryTable;
import com.liferay.commerce.price.list.model.impl.CommerceTierPriceEntryImpl;
import com.liferay.commerce.price.list.model.impl.CommerceTierPriceEntryModelImpl;
import com.liferay.commerce.price.list.service.persistence.CommerceTierPriceEntryPersistence;
import com.liferay.commerce.price.list.service.persistence.CommerceTierPriceEntryUtil;
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
 * The persistence implementation for the commerce tier price entry service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Alessio Antonio Rendina
 * @generated
 */
public class CommerceTierPriceEntryPersistenceImpl
	extends BasePersistenceImpl<CommerceTierPriceEntry>
	implements CommerceTierPriceEntryPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>CommerceTierPriceEntryUtil</code> to access the commerce tier price entry persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		CommerceTierPriceEntryImpl.class.getName();

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
	 * Returns all the commerce tier price entries where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching commerce tier price entries
	 */
	@Override
	public List<CommerceTierPriceEntry> findByUuid(String uuid) {
		return findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce tier price entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceTierPriceEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of commerce tier price entries
	 * @param end the upper bound of the range of commerce tier price entries (not inclusive)
	 * @return the range of matching commerce tier price entries
	 */
	@Override
	public List<CommerceTierPriceEntry> findByUuid(
		String uuid, int start, int end) {

		return findByUuid(uuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce tier price entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceTierPriceEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of commerce tier price entries
	 * @param end the upper bound of the range of commerce tier price entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce tier price entries
	 */
	@Override
	public List<CommerceTierPriceEntry> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<CommerceTierPriceEntry> orderByComparator) {

		return findByUuid(uuid, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce tier price entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceTierPriceEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of commerce tier price entries
	 * @param end the upper bound of the range of commerce tier price entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce tier price entries
	 */
	@Override
	public List<CommerceTierPriceEntry> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<CommerceTierPriceEntry> orderByComparator,
		boolean useFinderCache) {

		uuid = Objects.toString(uuid, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			CommerceTierPriceEntry.class);

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

		List<CommerceTierPriceEntry> list = null;

		if (useFinderCache && productionMode) {
			list = (List<CommerceTierPriceEntry>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (CommerceTierPriceEntry commerceTierPriceEntry : list) {
					if (!uuid.equals(commerceTierPriceEntry.getUuid())) {
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

			sb.append(_SQL_SELECT_COMMERCETIERPRICEENTRY_WHERE);

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
				sb.append(CommerceTierPriceEntryModelImpl.ORDER_BY_JPQL);
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

				list = (List<CommerceTierPriceEntry>)QueryUtil.list(
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
	 * Returns the first commerce tier price entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce tier price entry
	 * @throws NoSuchTierPriceEntryException if a matching commerce tier price entry could not be found
	 */
	@Override
	public CommerceTierPriceEntry findByUuid_First(
			String uuid,
			OrderByComparator<CommerceTierPriceEntry> orderByComparator)
		throws NoSuchTierPriceEntryException {

		CommerceTierPriceEntry commerceTierPriceEntry = fetchByUuid_First(
			uuid, orderByComparator);

		if (commerceTierPriceEntry != null) {
			return commerceTierPriceEntry;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append("}");

		throw new NoSuchTierPriceEntryException(sb.toString());
	}

	/**
	 * Returns the first commerce tier price entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce tier price entry, or <code>null</code> if a matching commerce tier price entry could not be found
	 */
	@Override
	public CommerceTierPriceEntry fetchByUuid_First(
		String uuid,
		OrderByComparator<CommerceTierPriceEntry> orderByComparator) {

		List<CommerceTierPriceEntry> list = findByUuid(
			uuid, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last commerce tier price entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce tier price entry
	 * @throws NoSuchTierPriceEntryException if a matching commerce tier price entry could not be found
	 */
	@Override
	public CommerceTierPriceEntry findByUuid_Last(
			String uuid,
			OrderByComparator<CommerceTierPriceEntry> orderByComparator)
		throws NoSuchTierPriceEntryException {

		CommerceTierPriceEntry commerceTierPriceEntry = fetchByUuid_Last(
			uuid, orderByComparator);

		if (commerceTierPriceEntry != null) {
			return commerceTierPriceEntry;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append("}");

		throw new NoSuchTierPriceEntryException(sb.toString());
	}

	/**
	 * Returns the last commerce tier price entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce tier price entry, or <code>null</code> if a matching commerce tier price entry could not be found
	 */
	@Override
	public CommerceTierPriceEntry fetchByUuid_Last(
		String uuid,
		OrderByComparator<CommerceTierPriceEntry> orderByComparator) {

		int count = countByUuid(uuid);

		if (count == 0) {
			return null;
		}

		List<CommerceTierPriceEntry> list = findByUuid(
			uuid, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the commerce tier price entries before and after the current commerce tier price entry in the ordered set where uuid = &#63;.
	 *
	 * @param commerceTierPriceEntryId the primary key of the current commerce tier price entry
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce tier price entry
	 * @throws NoSuchTierPriceEntryException if a commerce tier price entry with the primary key could not be found
	 */
	@Override
	public CommerceTierPriceEntry[] findByUuid_PrevAndNext(
			long commerceTierPriceEntryId, String uuid,
			OrderByComparator<CommerceTierPriceEntry> orderByComparator)
		throws NoSuchTierPriceEntryException {

		uuid = Objects.toString(uuid, "");

		CommerceTierPriceEntry commerceTierPriceEntry = findByPrimaryKey(
			commerceTierPriceEntryId);

		Session session = null;

		try {
			session = openSession();

			CommerceTierPriceEntry[] array = new CommerceTierPriceEntryImpl[3];

			array[0] = getByUuid_PrevAndNext(
				session, commerceTierPriceEntry, uuid, orderByComparator, true);

			array[1] = commerceTierPriceEntry;

			array[2] = getByUuid_PrevAndNext(
				session, commerceTierPriceEntry, uuid, orderByComparator,
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

	protected CommerceTierPriceEntry getByUuid_PrevAndNext(
		Session session, CommerceTierPriceEntry commerceTierPriceEntry,
		String uuid,
		OrderByComparator<CommerceTierPriceEntry> orderByComparator,
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

		sb.append(_SQL_SELECT_COMMERCETIERPRICEENTRY_WHERE);

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
			sb.append(CommerceTierPriceEntryModelImpl.ORDER_BY_JPQL);
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
						commerceTierPriceEntry)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<CommerceTierPriceEntry> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the commerce tier price entries where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	@Override
	public void removeByUuid(String uuid) {
		for (CommerceTierPriceEntry commerceTierPriceEntry :
				findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(commerceTierPriceEntry);
		}
	}

	/**
	 * Returns the number of commerce tier price entries where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching commerce tier price entries
	 */
	@Override
	public int countByUuid(String uuid) {
		uuid = Objects.toString(uuid, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			CommerceTierPriceEntry.class);

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

			sb.append(_SQL_COUNT_COMMERCETIERPRICEENTRY_WHERE);

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
		"commerceTierPriceEntry.uuid = ?";

	private static final String _FINDER_COLUMN_UUID_UUID_3 =
		"(commerceTierPriceEntry.uuid IS NULL OR commerceTierPriceEntry.uuid = '')";

	private FinderPath _finderPathWithPaginationFindByUuid_C;
	private FinderPath _finderPathWithoutPaginationFindByUuid_C;
	private FinderPath _finderPathCountByUuid_C;

	/**
	 * Returns all the commerce tier price entries where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching commerce tier price entries
	 */
	@Override
	public List<CommerceTierPriceEntry> findByUuid_C(
		String uuid, long companyId) {

		return findByUuid_C(
			uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce tier price entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceTierPriceEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce tier price entries
	 * @param end the upper bound of the range of commerce tier price entries (not inclusive)
	 * @return the range of matching commerce tier price entries
	 */
	@Override
	public List<CommerceTierPriceEntry> findByUuid_C(
		String uuid, long companyId, int start, int end) {

		return findByUuid_C(uuid, companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce tier price entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceTierPriceEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce tier price entries
	 * @param end the upper bound of the range of commerce tier price entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce tier price entries
	 */
	@Override
	public List<CommerceTierPriceEntry> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<CommerceTierPriceEntry> orderByComparator) {

		return findByUuid_C(
			uuid, companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce tier price entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceTierPriceEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce tier price entries
	 * @param end the upper bound of the range of commerce tier price entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce tier price entries
	 */
	@Override
	public List<CommerceTierPriceEntry> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<CommerceTierPriceEntry> orderByComparator,
		boolean useFinderCache) {

		uuid = Objects.toString(uuid, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			CommerceTierPriceEntry.class);

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

		List<CommerceTierPriceEntry> list = null;

		if (useFinderCache && productionMode) {
			list = (List<CommerceTierPriceEntry>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (CommerceTierPriceEntry commerceTierPriceEntry : list) {
					if (!uuid.equals(commerceTierPriceEntry.getUuid()) ||
						(companyId != commerceTierPriceEntry.getCompanyId())) {

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

			sb.append(_SQL_SELECT_COMMERCETIERPRICEENTRY_WHERE);

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
				sb.append(CommerceTierPriceEntryModelImpl.ORDER_BY_JPQL);
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

				list = (List<CommerceTierPriceEntry>)QueryUtil.list(
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
	 * Returns the first commerce tier price entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce tier price entry
	 * @throws NoSuchTierPriceEntryException if a matching commerce tier price entry could not be found
	 */
	@Override
	public CommerceTierPriceEntry findByUuid_C_First(
			String uuid, long companyId,
			OrderByComparator<CommerceTierPriceEntry> orderByComparator)
		throws NoSuchTierPriceEntryException {

		CommerceTierPriceEntry commerceTierPriceEntry = fetchByUuid_C_First(
			uuid, companyId, orderByComparator);

		if (commerceTierPriceEntry != null) {
			return commerceTierPriceEntry;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append(", companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchTierPriceEntryException(sb.toString());
	}

	/**
	 * Returns the first commerce tier price entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce tier price entry, or <code>null</code> if a matching commerce tier price entry could not be found
	 */
	@Override
	public CommerceTierPriceEntry fetchByUuid_C_First(
		String uuid, long companyId,
		OrderByComparator<CommerceTierPriceEntry> orderByComparator) {

		List<CommerceTierPriceEntry> list = findByUuid_C(
			uuid, companyId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last commerce tier price entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce tier price entry
	 * @throws NoSuchTierPriceEntryException if a matching commerce tier price entry could not be found
	 */
	@Override
	public CommerceTierPriceEntry findByUuid_C_Last(
			String uuid, long companyId,
			OrderByComparator<CommerceTierPriceEntry> orderByComparator)
		throws NoSuchTierPriceEntryException {

		CommerceTierPriceEntry commerceTierPriceEntry = fetchByUuid_C_Last(
			uuid, companyId, orderByComparator);

		if (commerceTierPriceEntry != null) {
			return commerceTierPriceEntry;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append(", companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchTierPriceEntryException(sb.toString());
	}

	/**
	 * Returns the last commerce tier price entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce tier price entry, or <code>null</code> if a matching commerce tier price entry could not be found
	 */
	@Override
	public CommerceTierPriceEntry fetchByUuid_C_Last(
		String uuid, long companyId,
		OrderByComparator<CommerceTierPriceEntry> orderByComparator) {

		int count = countByUuid_C(uuid, companyId);

		if (count == 0) {
			return null;
		}

		List<CommerceTierPriceEntry> list = findByUuid_C(
			uuid, companyId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the commerce tier price entries before and after the current commerce tier price entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param commerceTierPriceEntryId the primary key of the current commerce tier price entry
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce tier price entry
	 * @throws NoSuchTierPriceEntryException if a commerce tier price entry with the primary key could not be found
	 */
	@Override
	public CommerceTierPriceEntry[] findByUuid_C_PrevAndNext(
			long commerceTierPriceEntryId, String uuid, long companyId,
			OrderByComparator<CommerceTierPriceEntry> orderByComparator)
		throws NoSuchTierPriceEntryException {

		uuid = Objects.toString(uuid, "");

		CommerceTierPriceEntry commerceTierPriceEntry = findByPrimaryKey(
			commerceTierPriceEntryId);

		Session session = null;

		try {
			session = openSession();

			CommerceTierPriceEntry[] array = new CommerceTierPriceEntryImpl[3];

			array[0] = getByUuid_C_PrevAndNext(
				session, commerceTierPriceEntry, uuid, companyId,
				orderByComparator, true);

			array[1] = commerceTierPriceEntry;

			array[2] = getByUuid_C_PrevAndNext(
				session, commerceTierPriceEntry, uuid, companyId,
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

	protected CommerceTierPriceEntry getByUuid_C_PrevAndNext(
		Session session, CommerceTierPriceEntry commerceTierPriceEntry,
		String uuid, long companyId,
		OrderByComparator<CommerceTierPriceEntry> orderByComparator,
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

		sb.append(_SQL_SELECT_COMMERCETIERPRICEENTRY_WHERE);

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
			sb.append(CommerceTierPriceEntryModelImpl.ORDER_BY_JPQL);
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
						commerceTierPriceEntry)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<CommerceTierPriceEntry> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the commerce tier price entries where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	@Override
	public void removeByUuid_C(String uuid, long companyId) {
		for (CommerceTierPriceEntry commerceTierPriceEntry :
				findByUuid_C(
					uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(commerceTierPriceEntry);
		}
	}

	/**
	 * Returns the number of commerce tier price entries where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching commerce tier price entries
	 */
	@Override
	public int countByUuid_C(String uuid, long companyId) {
		uuid = Objects.toString(uuid, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			CommerceTierPriceEntry.class);

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

			sb.append(_SQL_COUNT_COMMERCETIERPRICEENTRY_WHERE);

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
		"commerceTierPriceEntry.uuid = ? AND ";

	private static final String _FINDER_COLUMN_UUID_C_UUID_3 =
		"(commerceTierPriceEntry.uuid IS NULL OR commerceTierPriceEntry.uuid = '') AND ";

	private static final String _FINDER_COLUMN_UUID_C_COMPANYID_2 =
		"commerceTierPriceEntry.companyId = ?";

	private FinderPath _finderPathWithPaginationFindByCompanyId;
	private FinderPath _finderPathWithoutPaginationFindByCompanyId;
	private FinderPath _finderPathCountByCompanyId;

	/**
	 * Returns all the commerce tier price entries where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching commerce tier price entries
	 */
	@Override
	public List<CommerceTierPriceEntry> findByCompanyId(long companyId) {
		return findByCompanyId(
			companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce tier price entries where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceTierPriceEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce tier price entries
	 * @param end the upper bound of the range of commerce tier price entries (not inclusive)
	 * @return the range of matching commerce tier price entries
	 */
	@Override
	public List<CommerceTierPriceEntry> findByCompanyId(
		long companyId, int start, int end) {

		return findByCompanyId(companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce tier price entries where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceTierPriceEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce tier price entries
	 * @param end the upper bound of the range of commerce tier price entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce tier price entries
	 */
	@Override
	public List<CommerceTierPriceEntry> findByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<CommerceTierPriceEntry> orderByComparator) {

		return findByCompanyId(companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce tier price entries where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceTierPriceEntryModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce tier price entries
	 * @param end the upper bound of the range of commerce tier price entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce tier price entries
	 */
	@Override
	public List<CommerceTierPriceEntry> findByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<CommerceTierPriceEntry> orderByComparator,
		boolean useFinderCache) {

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			CommerceTierPriceEntry.class);

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

		List<CommerceTierPriceEntry> list = null;

		if (useFinderCache && productionMode) {
			list = (List<CommerceTierPriceEntry>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (CommerceTierPriceEntry commerceTierPriceEntry : list) {
					if (companyId != commerceTierPriceEntry.getCompanyId()) {
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

			sb.append(_SQL_SELECT_COMMERCETIERPRICEENTRY_WHERE);

			sb.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(CommerceTierPriceEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(companyId);

				list = (List<CommerceTierPriceEntry>)QueryUtil.list(
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
	 * Returns the first commerce tier price entry in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce tier price entry
	 * @throws NoSuchTierPriceEntryException if a matching commerce tier price entry could not be found
	 */
	@Override
	public CommerceTierPriceEntry findByCompanyId_First(
			long companyId,
			OrderByComparator<CommerceTierPriceEntry> orderByComparator)
		throws NoSuchTierPriceEntryException {

		CommerceTierPriceEntry commerceTierPriceEntry = fetchByCompanyId_First(
			companyId, orderByComparator);

		if (commerceTierPriceEntry != null) {
			return commerceTierPriceEntry;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchTierPriceEntryException(sb.toString());
	}

	/**
	 * Returns the first commerce tier price entry in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce tier price entry, or <code>null</code> if a matching commerce tier price entry could not be found
	 */
	@Override
	public CommerceTierPriceEntry fetchByCompanyId_First(
		long companyId,
		OrderByComparator<CommerceTierPriceEntry> orderByComparator) {

		List<CommerceTierPriceEntry> list = findByCompanyId(
			companyId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last commerce tier price entry in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce tier price entry
	 * @throws NoSuchTierPriceEntryException if a matching commerce tier price entry could not be found
	 */
	@Override
	public CommerceTierPriceEntry findByCompanyId_Last(
			long companyId,
			OrderByComparator<CommerceTierPriceEntry> orderByComparator)
		throws NoSuchTierPriceEntryException {

		CommerceTierPriceEntry commerceTierPriceEntry = fetchByCompanyId_Last(
			companyId, orderByComparator);

		if (commerceTierPriceEntry != null) {
			return commerceTierPriceEntry;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchTierPriceEntryException(sb.toString());
	}

	/**
	 * Returns the last commerce tier price entry in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce tier price entry, or <code>null</code> if a matching commerce tier price entry could not be found
	 */
	@Override
	public CommerceTierPriceEntry fetchByCompanyId_Last(
		long companyId,
		OrderByComparator<CommerceTierPriceEntry> orderByComparator) {

		int count = countByCompanyId(companyId);

		if (count == 0) {
			return null;
		}

		List<CommerceTierPriceEntry> list = findByCompanyId(
			companyId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the commerce tier price entries before and after the current commerce tier price entry in the ordered set where companyId = &#63;.
	 *
	 * @param commerceTierPriceEntryId the primary key of the current commerce tier price entry
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce tier price entry
	 * @throws NoSuchTierPriceEntryException if a commerce tier price entry with the primary key could not be found
	 */
	@Override
	public CommerceTierPriceEntry[] findByCompanyId_PrevAndNext(
			long commerceTierPriceEntryId, long companyId,
			OrderByComparator<CommerceTierPriceEntry> orderByComparator)
		throws NoSuchTierPriceEntryException {

		CommerceTierPriceEntry commerceTierPriceEntry = findByPrimaryKey(
			commerceTierPriceEntryId);

		Session session = null;

		try {
			session = openSession();

			CommerceTierPriceEntry[] array = new CommerceTierPriceEntryImpl[3];

			array[0] = getByCompanyId_PrevAndNext(
				session, commerceTierPriceEntry, companyId, orderByComparator,
				true);

			array[1] = commerceTierPriceEntry;

			array[2] = getByCompanyId_PrevAndNext(
				session, commerceTierPriceEntry, companyId, orderByComparator,
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

	protected CommerceTierPriceEntry getByCompanyId_PrevAndNext(
		Session session, CommerceTierPriceEntry commerceTierPriceEntry,
		long companyId,
		OrderByComparator<CommerceTierPriceEntry> orderByComparator,
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

		sb.append(_SQL_SELECT_COMMERCETIERPRICEENTRY_WHERE);

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
			sb.append(CommerceTierPriceEntryModelImpl.ORDER_BY_JPQL);
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
						commerceTierPriceEntry)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<CommerceTierPriceEntry> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the commerce tier price entries where companyId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 */
	@Override
	public void removeByCompanyId(long companyId) {
		for (CommerceTierPriceEntry commerceTierPriceEntry :
				findByCompanyId(
					companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(commerceTierPriceEntry);
		}
	}

	/**
	 * Returns the number of commerce tier price entries where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching commerce tier price entries
	 */
	@Override
	public int countByCompanyId(long companyId) {
		boolean productionMode = ctPersistenceHelper.isProductionMode(
			CommerceTierPriceEntry.class);

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

			sb.append(_SQL_COUNT_COMMERCETIERPRICEENTRY_WHERE);

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
		"commerceTierPriceEntry.companyId = ?";

	private FinderPath _finderPathWithPaginationFindByCommercePriceEntryId;
	private FinderPath _finderPathWithoutPaginationFindByCommercePriceEntryId;
	private FinderPath _finderPathCountByCommercePriceEntryId;

	/**
	 * Returns all the commerce tier price entries where commercePriceEntryId = &#63;.
	 *
	 * @param commercePriceEntryId the commerce price entry ID
	 * @return the matching commerce tier price entries
	 */
	@Override
	public List<CommerceTierPriceEntry> findByCommercePriceEntryId(
		long commercePriceEntryId) {

		return findByCommercePriceEntryId(
			commercePriceEntryId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce tier price entries where commercePriceEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceTierPriceEntryModelImpl</code>.
	 * </p>
	 *
	 * @param commercePriceEntryId the commerce price entry ID
	 * @param start the lower bound of the range of commerce tier price entries
	 * @param end the upper bound of the range of commerce tier price entries (not inclusive)
	 * @return the range of matching commerce tier price entries
	 */
	@Override
	public List<CommerceTierPriceEntry> findByCommercePriceEntryId(
		long commercePriceEntryId, int start, int end) {

		return findByCommercePriceEntryId(
			commercePriceEntryId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce tier price entries where commercePriceEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceTierPriceEntryModelImpl</code>.
	 * </p>
	 *
	 * @param commercePriceEntryId the commerce price entry ID
	 * @param start the lower bound of the range of commerce tier price entries
	 * @param end the upper bound of the range of commerce tier price entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce tier price entries
	 */
	@Override
	public List<CommerceTierPriceEntry> findByCommercePriceEntryId(
		long commercePriceEntryId, int start, int end,
		OrderByComparator<CommerceTierPriceEntry> orderByComparator) {

		return findByCommercePriceEntryId(
			commercePriceEntryId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce tier price entries where commercePriceEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceTierPriceEntryModelImpl</code>.
	 * </p>
	 *
	 * @param commercePriceEntryId the commerce price entry ID
	 * @param start the lower bound of the range of commerce tier price entries
	 * @param end the upper bound of the range of commerce tier price entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce tier price entries
	 */
	@Override
	public List<CommerceTierPriceEntry> findByCommercePriceEntryId(
		long commercePriceEntryId, int start, int end,
		OrderByComparator<CommerceTierPriceEntry> orderByComparator,
		boolean useFinderCache) {

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			CommerceTierPriceEntry.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache && productionMode) {
				finderPath =
					_finderPathWithoutPaginationFindByCommercePriceEntryId;
				finderArgs = new Object[] {commercePriceEntryId};
			}
		}
		else if (useFinderCache && productionMode) {
			finderPath = _finderPathWithPaginationFindByCommercePriceEntryId;
			finderArgs = new Object[] {
				commercePriceEntryId, start, end, orderByComparator
			};
		}

		List<CommerceTierPriceEntry> list = null;

		if (useFinderCache && productionMode) {
			list = (List<CommerceTierPriceEntry>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (CommerceTierPriceEntry commerceTierPriceEntry : list) {
					if (commercePriceEntryId !=
							commerceTierPriceEntry.getCommercePriceEntryId()) {

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

			sb.append(_SQL_SELECT_COMMERCETIERPRICEENTRY_WHERE);

			sb.append(
				_FINDER_COLUMN_COMMERCEPRICEENTRYID_COMMERCEPRICEENTRYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(CommerceTierPriceEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(commercePriceEntryId);

				list = (List<CommerceTierPriceEntry>)QueryUtil.list(
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
	 * Returns the first commerce tier price entry in the ordered set where commercePriceEntryId = &#63;.
	 *
	 * @param commercePriceEntryId the commerce price entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce tier price entry
	 * @throws NoSuchTierPriceEntryException if a matching commerce tier price entry could not be found
	 */
	@Override
	public CommerceTierPriceEntry findByCommercePriceEntryId_First(
			long commercePriceEntryId,
			OrderByComparator<CommerceTierPriceEntry> orderByComparator)
		throws NoSuchTierPriceEntryException {

		CommerceTierPriceEntry commerceTierPriceEntry =
			fetchByCommercePriceEntryId_First(
				commercePriceEntryId, orderByComparator);

		if (commerceTierPriceEntry != null) {
			return commerceTierPriceEntry;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("commercePriceEntryId=");
		sb.append(commercePriceEntryId);

		sb.append("}");

		throw new NoSuchTierPriceEntryException(sb.toString());
	}

	/**
	 * Returns the first commerce tier price entry in the ordered set where commercePriceEntryId = &#63;.
	 *
	 * @param commercePriceEntryId the commerce price entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce tier price entry, or <code>null</code> if a matching commerce tier price entry could not be found
	 */
	@Override
	public CommerceTierPriceEntry fetchByCommercePriceEntryId_First(
		long commercePriceEntryId,
		OrderByComparator<CommerceTierPriceEntry> orderByComparator) {

		List<CommerceTierPriceEntry> list = findByCommercePriceEntryId(
			commercePriceEntryId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last commerce tier price entry in the ordered set where commercePriceEntryId = &#63;.
	 *
	 * @param commercePriceEntryId the commerce price entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce tier price entry
	 * @throws NoSuchTierPriceEntryException if a matching commerce tier price entry could not be found
	 */
	@Override
	public CommerceTierPriceEntry findByCommercePriceEntryId_Last(
			long commercePriceEntryId,
			OrderByComparator<CommerceTierPriceEntry> orderByComparator)
		throws NoSuchTierPriceEntryException {

		CommerceTierPriceEntry commerceTierPriceEntry =
			fetchByCommercePriceEntryId_Last(
				commercePriceEntryId, orderByComparator);

		if (commerceTierPriceEntry != null) {
			return commerceTierPriceEntry;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("commercePriceEntryId=");
		sb.append(commercePriceEntryId);

		sb.append("}");

		throw new NoSuchTierPriceEntryException(sb.toString());
	}

	/**
	 * Returns the last commerce tier price entry in the ordered set where commercePriceEntryId = &#63;.
	 *
	 * @param commercePriceEntryId the commerce price entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce tier price entry, or <code>null</code> if a matching commerce tier price entry could not be found
	 */
	@Override
	public CommerceTierPriceEntry fetchByCommercePriceEntryId_Last(
		long commercePriceEntryId,
		OrderByComparator<CommerceTierPriceEntry> orderByComparator) {

		int count = countByCommercePriceEntryId(commercePriceEntryId);

		if (count == 0) {
			return null;
		}

		List<CommerceTierPriceEntry> list = findByCommercePriceEntryId(
			commercePriceEntryId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the commerce tier price entries before and after the current commerce tier price entry in the ordered set where commercePriceEntryId = &#63;.
	 *
	 * @param commerceTierPriceEntryId the primary key of the current commerce tier price entry
	 * @param commercePriceEntryId the commerce price entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce tier price entry
	 * @throws NoSuchTierPriceEntryException if a commerce tier price entry with the primary key could not be found
	 */
	@Override
	public CommerceTierPriceEntry[] findByCommercePriceEntryId_PrevAndNext(
			long commerceTierPriceEntryId, long commercePriceEntryId,
			OrderByComparator<CommerceTierPriceEntry> orderByComparator)
		throws NoSuchTierPriceEntryException {

		CommerceTierPriceEntry commerceTierPriceEntry = findByPrimaryKey(
			commerceTierPriceEntryId);

		Session session = null;

		try {
			session = openSession();

			CommerceTierPriceEntry[] array = new CommerceTierPriceEntryImpl[3];

			array[0] = getByCommercePriceEntryId_PrevAndNext(
				session, commerceTierPriceEntry, commercePriceEntryId,
				orderByComparator, true);

			array[1] = commerceTierPriceEntry;

			array[2] = getByCommercePriceEntryId_PrevAndNext(
				session, commerceTierPriceEntry, commercePriceEntryId,
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

	protected CommerceTierPriceEntry getByCommercePriceEntryId_PrevAndNext(
		Session session, CommerceTierPriceEntry commerceTierPriceEntry,
		long commercePriceEntryId,
		OrderByComparator<CommerceTierPriceEntry> orderByComparator,
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

		sb.append(_SQL_SELECT_COMMERCETIERPRICEENTRY_WHERE);

		sb.append(_FINDER_COLUMN_COMMERCEPRICEENTRYID_COMMERCEPRICEENTRYID_2);

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
			sb.append(CommerceTierPriceEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(commercePriceEntryId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						commerceTierPriceEntry)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<CommerceTierPriceEntry> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the commerce tier price entries where commercePriceEntryId = &#63; from the database.
	 *
	 * @param commercePriceEntryId the commerce price entry ID
	 */
	@Override
	public void removeByCommercePriceEntryId(long commercePriceEntryId) {
		for (CommerceTierPriceEntry commerceTierPriceEntry :
				findByCommercePriceEntryId(
					commercePriceEntryId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(commerceTierPriceEntry);
		}
	}

	/**
	 * Returns the number of commerce tier price entries where commercePriceEntryId = &#63;.
	 *
	 * @param commercePriceEntryId the commerce price entry ID
	 * @return the number of matching commerce tier price entries
	 */
	@Override
	public int countByCommercePriceEntryId(long commercePriceEntryId) {
		boolean productionMode = ctPersistenceHelper.isProductionMode(
			CommerceTierPriceEntry.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByCommercePriceEntryId;

			finderArgs = new Object[] {commercePriceEntryId};

			count = (Long)finderCache.getResult(finderPath, finderArgs);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_COMMERCETIERPRICEENTRY_WHERE);

			sb.append(
				_FINDER_COLUMN_COMMERCEPRICEENTRYID_COMMERCEPRICEENTRYID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(commercePriceEntryId);

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
		_FINDER_COLUMN_COMMERCEPRICEENTRYID_COMMERCEPRICEENTRYID_2 =
			"commerceTierPriceEntry.commercePriceEntryId = ?";

	private FinderPath _finderPathFetchByC_M;
	private FinderPath _finderPathCountByC_M;

	/**
	 * Returns the commerce tier price entry where commercePriceEntryId = &#63; and minQuantity = &#63; or throws a <code>NoSuchTierPriceEntryException</code> if it could not be found.
	 *
	 * @param commercePriceEntryId the commerce price entry ID
	 * @param minQuantity the min quantity
	 * @return the matching commerce tier price entry
	 * @throws NoSuchTierPriceEntryException if a matching commerce tier price entry could not be found
	 */
	@Override
	public CommerceTierPriceEntry findByC_M(
			long commercePriceEntryId, int minQuantity)
		throws NoSuchTierPriceEntryException {

		CommerceTierPriceEntry commerceTierPriceEntry = fetchByC_M(
			commercePriceEntryId, minQuantity);

		if (commerceTierPriceEntry == null) {
			StringBundler sb = new StringBundler(6);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("commercePriceEntryId=");
			sb.append(commercePriceEntryId);

			sb.append(", minQuantity=");
			sb.append(minQuantity);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchTierPriceEntryException(sb.toString());
		}

		return commerceTierPriceEntry;
	}

	/**
	 * Returns the commerce tier price entry where commercePriceEntryId = &#63; and minQuantity = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param commercePriceEntryId the commerce price entry ID
	 * @param minQuantity the min quantity
	 * @return the matching commerce tier price entry, or <code>null</code> if a matching commerce tier price entry could not be found
	 */
	@Override
	public CommerceTierPriceEntry fetchByC_M(
		long commercePriceEntryId, int minQuantity) {

		return fetchByC_M(commercePriceEntryId, minQuantity, true);
	}

	/**
	 * Returns the commerce tier price entry where commercePriceEntryId = &#63; and minQuantity = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param commercePriceEntryId the commerce price entry ID
	 * @param minQuantity the min quantity
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching commerce tier price entry, or <code>null</code> if a matching commerce tier price entry could not be found
	 */
	@Override
	public CommerceTierPriceEntry fetchByC_M(
		long commercePriceEntryId, int minQuantity, boolean useFinderCache) {

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			CommerceTierPriceEntry.class);

		Object[] finderArgs = null;

		if (useFinderCache && productionMode) {
			finderArgs = new Object[] {commercePriceEntryId, minQuantity};
		}

		Object result = null;

		if (useFinderCache && productionMode) {
			result = finderCache.getResult(_finderPathFetchByC_M, finderArgs);
		}

		if (result instanceof CommerceTierPriceEntry) {
			CommerceTierPriceEntry commerceTierPriceEntry =
				(CommerceTierPriceEntry)result;

			if ((commercePriceEntryId !=
					commerceTierPriceEntry.getCommercePriceEntryId()) ||
				(minQuantity != commerceTierPriceEntry.getMinQuantity())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_SELECT_COMMERCETIERPRICEENTRY_WHERE);

			sb.append(_FINDER_COLUMN_C_M_COMMERCEPRICEENTRYID_2);

			sb.append(_FINDER_COLUMN_C_M_MINQUANTITY_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(commercePriceEntryId);

				queryPos.add(minQuantity);

				List<CommerceTierPriceEntry> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache && productionMode) {
						finderCache.putResult(
							_finderPathFetchByC_M, finderArgs, list);
					}
				}
				else {
					CommerceTierPriceEntry commerceTierPriceEntry = list.get(0);

					result = commerceTierPriceEntry;

					cacheResult(commerceTierPriceEntry);
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
			return (CommerceTierPriceEntry)result;
		}
	}

	/**
	 * Removes the commerce tier price entry where commercePriceEntryId = &#63; and minQuantity = &#63; from the database.
	 *
	 * @param commercePriceEntryId the commerce price entry ID
	 * @param minQuantity the min quantity
	 * @return the commerce tier price entry that was removed
	 */
	@Override
	public CommerceTierPriceEntry removeByC_M(
			long commercePriceEntryId, int minQuantity)
		throws NoSuchTierPriceEntryException {

		CommerceTierPriceEntry commerceTierPriceEntry = findByC_M(
			commercePriceEntryId, minQuantity);

		return remove(commerceTierPriceEntry);
	}

	/**
	 * Returns the number of commerce tier price entries where commercePriceEntryId = &#63; and minQuantity = &#63;.
	 *
	 * @param commercePriceEntryId the commerce price entry ID
	 * @param minQuantity the min quantity
	 * @return the number of matching commerce tier price entries
	 */
	@Override
	public int countByC_M(long commercePriceEntryId, int minQuantity) {
		boolean productionMode = ctPersistenceHelper.isProductionMode(
			CommerceTierPriceEntry.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByC_M;

			finderArgs = new Object[] {commercePriceEntryId, minQuantity};

			count = (Long)finderCache.getResult(finderPath, finderArgs);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_COMMERCETIERPRICEENTRY_WHERE);

			sb.append(_FINDER_COLUMN_C_M_COMMERCEPRICEENTRYID_2);

			sb.append(_FINDER_COLUMN_C_M_MINQUANTITY_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(commercePriceEntryId);

				queryPos.add(minQuantity);

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

	private static final String _FINDER_COLUMN_C_M_COMMERCEPRICEENTRYID_2 =
		"commerceTierPriceEntry.commercePriceEntryId = ? AND ";

	private static final String _FINDER_COLUMN_C_M_MINQUANTITY_2 =
		"commerceTierPriceEntry.minQuantity = ?";

	private FinderPath _finderPathWithPaginationFindByC_LteM;
	private FinderPath _finderPathWithPaginationCountByC_LteM;

	/**
	 * Returns all the commerce tier price entries where commercePriceEntryId = &#63; and minQuantity &le; &#63;.
	 *
	 * @param commercePriceEntryId the commerce price entry ID
	 * @param minQuantity the min quantity
	 * @return the matching commerce tier price entries
	 */
	@Override
	public List<CommerceTierPriceEntry> findByC_LteM(
		long commercePriceEntryId, int minQuantity) {

		return findByC_LteM(
			commercePriceEntryId, minQuantity, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce tier price entries where commercePriceEntryId = &#63; and minQuantity &le; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceTierPriceEntryModelImpl</code>.
	 * </p>
	 *
	 * @param commercePriceEntryId the commerce price entry ID
	 * @param minQuantity the min quantity
	 * @param start the lower bound of the range of commerce tier price entries
	 * @param end the upper bound of the range of commerce tier price entries (not inclusive)
	 * @return the range of matching commerce tier price entries
	 */
	@Override
	public List<CommerceTierPriceEntry> findByC_LteM(
		long commercePriceEntryId, int minQuantity, int start, int end) {

		return findByC_LteM(
			commercePriceEntryId, minQuantity, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce tier price entries where commercePriceEntryId = &#63; and minQuantity &le; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceTierPriceEntryModelImpl</code>.
	 * </p>
	 *
	 * @param commercePriceEntryId the commerce price entry ID
	 * @param minQuantity the min quantity
	 * @param start the lower bound of the range of commerce tier price entries
	 * @param end the upper bound of the range of commerce tier price entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce tier price entries
	 */
	@Override
	public List<CommerceTierPriceEntry> findByC_LteM(
		long commercePriceEntryId, int minQuantity, int start, int end,
		OrderByComparator<CommerceTierPriceEntry> orderByComparator) {

		return findByC_LteM(
			commercePriceEntryId, minQuantity, start, end, orderByComparator,
			true);
	}

	/**
	 * Returns an ordered range of all the commerce tier price entries where commercePriceEntryId = &#63; and minQuantity &le; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceTierPriceEntryModelImpl</code>.
	 * </p>
	 *
	 * @param commercePriceEntryId the commerce price entry ID
	 * @param minQuantity the min quantity
	 * @param start the lower bound of the range of commerce tier price entries
	 * @param end the upper bound of the range of commerce tier price entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce tier price entries
	 */
	@Override
	public List<CommerceTierPriceEntry> findByC_LteM(
		long commercePriceEntryId, int minQuantity, int start, int end,
		OrderByComparator<CommerceTierPriceEntry> orderByComparator,
		boolean useFinderCache) {

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			CommerceTierPriceEntry.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		finderPath = _finderPathWithPaginationFindByC_LteM;
		finderArgs = new Object[] {
			commercePriceEntryId, minQuantity, start, end, orderByComparator
		};

		List<CommerceTierPriceEntry> list = null;

		if (useFinderCache && productionMode) {
			list = (List<CommerceTierPriceEntry>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (CommerceTierPriceEntry commerceTierPriceEntry : list) {
					if ((commercePriceEntryId !=
							commerceTierPriceEntry.getCommercePriceEntryId()) ||
						(minQuantity <
							commerceTierPriceEntry.getMinQuantity())) {

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

			sb.append(_SQL_SELECT_COMMERCETIERPRICEENTRY_WHERE);

			sb.append(_FINDER_COLUMN_C_LTEM_COMMERCEPRICEENTRYID_2);

			sb.append(_FINDER_COLUMN_C_LTEM_MINQUANTITY_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(CommerceTierPriceEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(commercePriceEntryId);

				queryPos.add(minQuantity);

				list = (List<CommerceTierPriceEntry>)QueryUtil.list(
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
	 * Returns the first commerce tier price entry in the ordered set where commercePriceEntryId = &#63; and minQuantity &le; &#63;.
	 *
	 * @param commercePriceEntryId the commerce price entry ID
	 * @param minQuantity the min quantity
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce tier price entry
	 * @throws NoSuchTierPriceEntryException if a matching commerce tier price entry could not be found
	 */
	@Override
	public CommerceTierPriceEntry findByC_LteM_First(
			long commercePriceEntryId, int minQuantity,
			OrderByComparator<CommerceTierPriceEntry> orderByComparator)
		throws NoSuchTierPriceEntryException {

		CommerceTierPriceEntry commerceTierPriceEntry = fetchByC_LteM_First(
			commercePriceEntryId, minQuantity, orderByComparator);

		if (commerceTierPriceEntry != null) {
			return commerceTierPriceEntry;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("commercePriceEntryId=");
		sb.append(commercePriceEntryId);

		sb.append(", minQuantity<=");
		sb.append(minQuantity);

		sb.append("}");

		throw new NoSuchTierPriceEntryException(sb.toString());
	}

	/**
	 * Returns the first commerce tier price entry in the ordered set where commercePriceEntryId = &#63; and minQuantity &le; &#63;.
	 *
	 * @param commercePriceEntryId the commerce price entry ID
	 * @param minQuantity the min quantity
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce tier price entry, or <code>null</code> if a matching commerce tier price entry could not be found
	 */
	@Override
	public CommerceTierPriceEntry fetchByC_LteM_First(
		long commercePriceEntryId, int minQuantity,
		OrderByComparator<CommerceTierPriceEntry> orderByComparator) {

		List<CommerceTierPriceEntry> list = findByC_LteM(
			commercePriceEntryId, minQuantity, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last commerce tier price entry in the ordered set where commercePriceEntryId = &#63; and minQuantity &le; &#63;.
	 *
	 * @param commercePriceEntryId the commerce price entry ID
	 * @param minQuantity the min quantity
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce tier price entry
	 * @throws NoSuchTierPriceEntryException if a matching commerce tier price entry could not be found
	 */
	@Override
	public CommerceTierPriceEntry findByC_LteM_Last(
			long commercePriceEntryId, int minQuantity,
			OrderByComparator<CommerceTierPriceEntry> orderByComparator)
		throws NoSuchTierPriceEntryException {

		CommerceTierPriceEntry commerceTierPriceEntry = fetchByC_LteM_Last(
			commercePriceEntryId, minQuantity, orderByComparator);

		if (commerceTierPriceEntry != null) {
			return commerceTierPriceEntry;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("commercePriceEntryId=");
		sb.append(commercePriceEntryId);

		sb.append(", minQuantity<=");
		sb.append(minQuantity);

		sb.append("}");

		throw new NoSuchTierPriceEntryException(sb.toString());
	}

	/**
	 * Returns the last commerce tier price entry in the ordered set where commercePriceEntryId = &#63; and minQuantity &le; &#63;.
	 *
	 * @param commercePriceEntryId the commerce price entry ID
	 * @param minQuantity the min quantity
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce tier price entry, or <code>null</code> if a matching commerce tier price entry could not be found
	 */
	@Override
	public CommerceTierPriceEntry fetchByC_LteM_Last(
		long commercePriceEntryId, int minQuantity,
		OrderByComparator<CommerceTierPriceEntry> orderByComparator) {

		int count = countByC_LteM(commercePriceEntryId, minQuantity);

		if (count == 0) {
			return null;
		}

		List<CommerceTierPriceEntry> list = findByC_LteM(
			commercePriceEntryId, minQuantity, count - 1, count,
			orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the commerce tier price entries before and after the current commerce tier price entry in the ordered set where commercePriceEntryId = &#63; and minQuantity &le; &#63;.
	 *
	 * @param commerceTierPriceEntryId the primary key of the current commerce tier price entry
	 * @param commercePriceEntryId the commerce price entry ID
	 * @param minQuantity the min quantity
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce tier price entry
	 * @throws NoSuchTierPriceEntryException if a commerce tier price entry with the primary key could not be found
	 */
	@Override
	public CommerceTierPriceEntry[] findByC_LteM_PrevAndNext(
			long commerceTierPriceEntryId, long commercePriceEntryId,
			int minQuantity,
			OrderByComparator<CommerceTierPriceEntry> orderByComparator)
		throws NoSuchTierPriceEntryException {

		CommerceTierPriceEntry commerceTierPriceEntry = findByPrimaryKey(
			commerceTierPriceEntryId);

		Session session = null;

		try {
			session = openSession();

			CommerceTierPriceEntry[] array = new CommerceTierPriceEntryImpl[3];

			array[0] = getByC_LteM_PrevAndNext(
				session, commerceTierPriceEntry, commercePriceEntryId,
				minQuantity, orderByComparator, true);

			array[1] = commerceTierPriceEntry;

			array[2] = getByC_LteM_PrevAndNext(
				session, commerceTierPriceEntry, commercePriceEntryId,
				minQuantity, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected CommerceTierPriceEntry getByC_LteM_PrevAndNext(
		Session session, CommerceTierPriceEntry commerceTierPriceEntry,
		long commercePriceEntryId, int minQuantity,
		OrderByComparator<CommerceTierPriceEntry> orderByComparator,
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

		sb.append(_SQL_SELECT_COMMERCETIERPRICEENTRY_WHERE);

		sb.append(_FINDER_COLUMN_C_LTEM_COMMERCEPRICEENTRYID_2);

		sb.append(_FINDER_COLUMN_C_LTEM_MINQUANTITY_2);

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
			sb.append(CommerceTierPriceEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(commercePriceEntryId);

		queryPos.add(minQuantity);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						commerceTierPriceEntry)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<CommerceTierPriceEntry> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the commerce tier price entries where commercePriceEntryId = &#63; and minQuantity &le; &#63; from the database.
	 *
	 * @param commercePriceEntryId the commerce price entry ID
	 * @param minQuantity the min quantity
	 */
	@Override
	public void removeByC_LteM(long commercePriceEntryId, int minQuantity) {
		for (CommerceTierPriceEntry commerceTierPriceEntry :
				findByC_LteM(
					commercePriceEntryId, minQuantity, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			remove(commerceTierPriceEntry);
		}
	}

	/**
	 * Returns the number of commerce tier price entries where commercePriceEntryId = &#63; and minQuantity &le; &#63;.
	 *
	 * @param commercePriceEntryId the commerce price entry ID
	 * @param minQuantity the min quantity
	 * @return the number of matching commerce tier price entries
	 */
	@Override
	public int countByC_LteM(long commercePriceEntryId, int minQuantity) {
		boolean productionMode = ctPersistenceHelper.isProductionMode(
			CommerceTierPriceEntry.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathWithPaginationCountByC_LteM;

			finderArgs = new Object[] {commercePriceEntryId, minQuantity};

			count = (Long)finderCache.getResult(finderPath, finderArgs);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_COMMERCETIERPRICEENTRY_WHERE);

			sb.append(_FINDER_COLUMN_C_LTEM_COMMERCEPRICEENTRYID_2);

			sb.append(_FINDER_COLUMN_C_LTEM_MINQUANTITY_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(commercePriceEntryId);

				queryPos.add(minQuantity);

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

	private static final String _FINDER_COLUMN_C_LTEM_COMMERCEPRICEENTRYID_2 =
		"commerceTierPriceEntry.commercePriceEntryId = ? AND ";

	private static final String _FINDER_COLUMN_C_LTEM_MINQUANTITY_2 =
		"commerceTierPriceEntry.minQuantity <= ?";

	private FinderPath _finderPathWithPaginationFindByLtD_S;
	private FinderPath _finderPathWithPaginationCountByLtD_S;

	/**
	 * Returns all the commerce tier price entries where displayDate &lt; &#63; and status = &#63;.
	 *
	 * @param displayDate the display date
	 * @param status the status
	 * @return the matching commerce tier price entries
	 */
	@Override
	public List<CommerceTierPriceEntry> findByLtD_S(
		Date displayDate, int status) {

		return findByLtD_S(
			displayDate, status, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce tier price entries where displayDate &lt; &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceTierPriceEntryModelImpl</code>.
	 * </p>
	 *
	 * @param displayDate the display date
	 * @param status the status
	 * @param start the lower bound of the range of commerce tier price entries
	 * @param end the upper bound of the range of commerce tier price entries (not inclusive)
	 * @return the range of matching commerce tier price entries
	 */
	@Override
	public List<CommerceTierPriceEntry> findByLtD_S(
		Date displayDate, int status, int start, int end) {

		return findByLtD_S(displayDate, status, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce tier price entries where displayDate &lt; &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceTierPriceEntryModelImpl</code>.
	 * </p>
	 *
	 * @param displayDate the display date
	 * @param status the status
	 * @param start the lower bound of the range of commerce tier price entries
	 * @param end the upper bound of the range of commerce tier price entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce tier price entries
	 */
	@Override
	public List<CommerceTierPriceEntry> findByLtD_S(
		Date displayDate, int status, int start, int end,
		OrderByComparator<CommerceTierPriceEntry> orderByComparator) {

		return findByLtD_S(
			displayDate, status, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce tier price entries where displayDate &lt; &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceTierPriceEntryModelImpl</code>.
	 * </p>
	 *
	 * @param displayDate the display date
	 * @param status the status
	 * @param start the lower bound of the range of commerce tier price entries
	 * @param end the upper bound of the range of commerce tier price entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce tier price entries
	 */
	@Override
	public List<CommerceTierPriceEntry> findByLtD_S(
		Date displayDate, int status, int start, int end,
		OrderByComparator<CommerceTierPriceEntry> orderByComparator,
		boolean useFinderCache) {

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			CommerceTierPriceEntry.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		finderPath = _finderPathWithPaginationFindByLtD_S;
		finderArgs = new Object[] {
			_getTime(displayDate), status, start, end, orderByComparator
		};

		List<CommerceTierPriceEntry> list = null;

		if (useFinderCache && productionMode) {
			list = (List<CommerceTierPriceEntry>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (CommerceTierPriceEntry commerceTierPriceEntry : list) {
					if ((displayDate.getTime() <=
							commerceTierPriceEntry.getDisplayDate(
							).getTime()) ||
						(status != commerceTierPriceEntry.getStatus())) {

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

			sb.append(_SQL_SELECT_COMMERCETIERPRICEENTRY_WHERE);

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
				sb.append(CommerceTierPriceEntryModelImpl.ORDER_BY_JPQL);
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

				list = (List<CommerceTierPriceEntry>)QueryUtil.list(
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
	 * Returns the first commerce tier price entry in the ordered set where displayDate &lt; &#63; and status = &#63;.
	 *
	 * @param displayDate the display date
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce tier price entry
	 * @throws NoSuchTierPriceEntryException if a matching commerce tier price entry could not be found
	 */
	@Override
	public CommerceTierPriceEntry findByLtD_S_First(
			Date displayDate, int status,
			OrderByComparator<CommerceTierPriceEntry> orderByComparator)
		throws NoSuchTierPriceEntryException {

		CommerceTierPriceEntry commerceTierPriceEntry = fetchByLtD_S_First(
			displayDate, status, orderByComparator);

		if (commerceTierPriceEntry != null) {
			return commerceTierPriceEntry;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("displayDate<");
		sb.append(displayDate);

		sb.append(", status=");
		sb.append(status);

		sb.append("}");

		throw new NoSuchTierPriceEntryException(sb.toString());
	}

	/**
	 * Returns the first commerce tier price entry in the ordered set where displayDate &lt; &#63; and status = &#63;.
	 *
	 * @param displayDate the display date
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce tier price entry, or <code>null</code> if a matching commerce tier price entry could not be found
	 */
	@Override
	public CommerceTierPriceEntry fetchByLtD_S_First(
		Date displayDate, int status,
		OrderByComparator<CommerceTierPriceEntry> orderByComparator) {

		List<CommerceTierPriceEntry> list = findByLtD_S(
			displayDate, status, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last commerce tier price entry in the ordered set where displayDate &lt; &#63; and status = &#63;.
	 *
	 * @param displayDate the display date
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce tier price entry
	 * @throws NoSuchTierPriceEntryException if a matching commerce tier price entry could not be found
	 */
	@Override
	public CommerceTierPriceEntry findByLtD_S_Last(
			Date displayDate, int status,
			OrderByComparator<CommerceTierPriceEntry> orderByComparator)
		throws NoSuchTierPriceEntryException {

		CommerceTierPriceEntry commerceTierPriceEntry = fetchByLtD_S_Last(
			displayDate, status, orderByComparator);

		if (commerceTierPriceEntry != null) {
			return commerceTierPriceEntry;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("displayDate<");
		sb.append(displayDate);

		sb.append(", status=");
		sb.append(status);

		sb.append("}");

		throw new NoSuchTierPriceEntryException(sb.toString());
	}

	/**
	 * Returns the last commerce tier price entry in the ordered set where displayDate &lt; &#63; and status = &#63;.
	 *
	 * @param displayDate the display date
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce tier price entry, or <code>null</code> if a matching commerce tier price entry could not be found
	 */
	@Override
	public CommerceTierPriceEntry fetchByLtD_S_Last(
		Date displayDate, int status,
		OrderByComparator<CommerceTierPriceEntry> orderByComparator) {

		int count = countByLtD_S(displayDate, status);

		if (count == 0) {
			return null;
		}

		List<CommerceTierPriceEntry> list = findByLtD_S(
			displayDate, status, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the commerce tier price entries before and after the current commerce tier price entry in the ordered set where displayDate &lt; &#63; and status = &#63;.
	 *
	 * @param commerceTierPriceEntryId the primary key of the current commerce tier price entry
	 * @param displayDate the display date
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce tier price entry
	 * @throws NoSuchTierPriceEntryException if a commerce tier price entry with the primary key could not be found
	 */
	@Override
	public CommerceTierPriceEntry[] findByLtD_S_PrevAndNext(
			long commerceTierPriceEntryId, Date displayDate, int status,
			OrderByComparator<CommerceTierPriceEntry> orderByComparator)
		throws NoSuchTierPriceEntryException {

		CommerceTierPriceEntry commerceTierPriceEntry = findByPrimaryKey(
			commerceTierPriceEntryId);

		Session session = null;

		try {
			session = openSession();

			CommerceTierPriceEntry[] array = new CommerceTierPriceEntryImpl[3];

			array[0] = getByLtD_S_PrevAndNext(
				session, commerceTierPriceEntry, displayDate, status,
				orderByComparator, true);

			array[1] = commerceTierPriceEntry;

			array[2] = getByLtD_S_PrevAndNext(
				session, commerceTierPriceEntry, displayDate, status,
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

	protected CommerceTierPriceEntry getByLtD_S_PrevAndNext(
		Session session, CommerceTierPriceEntry commerceTierPriceEntry,
		Date displayDate, int status,
		OrderByComparator<CommerceTierPriceEntry> orderByComparator,
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

		sb.append(_SQL_SELECT_COMMERCETIERPRICEENTRY_WHERE);

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
			sb.append(CommerceTierPriceEntryModelImpl.ORDER_BY_JPQL);
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
						commerceTierPriceEntry)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<CommerceTierPriceEntry> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the commerce tier price entries where displayDate &lt; &#63; and status = &#63; from the database.
	 *
	 * @param displayDate the display date
	 * @param status the status
	 */
	@Override
	public void removeByLtD_S(Date displayDate, int status) {
		for (CommerceTierPriceEntry commerceTierPriceEntry :
				findByLtD_S(
					displayDate, status, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(commerceTierPriceEntry);
		}
	}

	/**
	 * Returns the number of commerce tier price entries where displayDate &lt; &#63; and status = &#63;.
	 *
	 * @param displayDate the display date
	 * @param status the status
	 * @return the number of matching commerce tier price entries
	 */
	@Override
	public int countByLtD_S(Date displayDate, int status) {
		boolean productionMode = ctPersistenceHelper.isProductionMode(
			CommerceTierPriceEntry.class);

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

			sb.append(_SQL_COUNT_COMMERCETIERPRICEENTRY_WHERE);

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
		"commerceTierPriceEntry.displayDate IS NULL AND ";

	private static final String _FINDER_COLUMN_LTD_S_DISPLAYDATE_2 =
		"commerceTierPriceEntry.displayDate < ? AND ";

	private static final String _FINDER_COLUMN_LTD_S_STATUS_2 =
		"commerceTierPriceEntry.status = ?";

	private FinderPath _finderPathWithPaginationFindByLtE_S;
	private FinderPath _finderPathWithPaginationCountByLtE_S;

	/**
	 * Returns all the commerce tier price entries where expirationDate &lt; &#63; and status = &#63;.
	 *
	 * @param expirationDate the expiration date
	 * @param status the status
	 * @return the matching commerce tier price entries
	 */
	@Override
	public List<CommerceTierPriceEntry> findByLtE_S(
		Date expirationDate, int status) {

		return findByLtE_S(
			expirationDate, status, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce tier price entries where expirationDate &lt; &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceTierPriceEntryModelImpl</code>.
	 * </p>
	 *
	 * @param expirationDate the expiration date
	 * @param status the status
	 * @param start the lower bound of the range of commerce tier price entries
	 * @param end the upper bound of the range of commerce tier price entries (not inclusive)
	 * @return the range of matching commerce tier price entries
	 */
	@Override
	public List<CommerceTierPriceEntry> findByLtE_S(
		Date expirationDate, int status, int start, int end) {

		return findByLtE_S(expirationDate, status, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce tier price entries where expirationDate &lt; &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceTierPriceEntryModelImpl</code>.
	 * </p>
	 *
	 * @param expirationDate the expiration date
	 * @param status the status
	 * @param start the lower bound of the range of commerce tier price entries
	 * @param end the upper bound of the range of commerce tier price entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce tier price entries
	 */
	@Override
	public List<CommerceTierPriceEntry> findByLtE_S(
		Date expirationDate, int status, int start, int end,
		OrderByComparator<CommerceTierPriceEntry> orderByComparator) {

		return findByLtE_S(
			expirationDate, status, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce tier price entries where expirationDate &lt; &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceTierPriceEntryModelImpl</code>.
	 * </p>
	 *
	 * @param expirationDate the expiration date
	 * @param status the status
	 * @param start the lower bound of the range of commerce tier price entries
	 * @param end the upper bound of the range of commerce tier price entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce tier price entries
	 */
	@Override
	public List<CommerceTierPriceEntry> findByLtE_S(
		Date expirationDate, int status, int start, int end,
		OrderByComparator<CommerceTierPriceEntry> orderByComparator,
		boolean useFinderCache) {

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			CommerceTierPriceEntry.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		finderPath = _finderPathWithPaginationFindByLtE_S;
		finderArgs = new Object[] {
			_getTime(expirationDate), status, start, end, orderByComparator
		};

		List<CommerceTierPriceEntry> list = null;

		if (useFinderCache && productionMode) {
			list = (List<CommerceTierPriceEntry>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (CommerceTierPriceEntry commerceTierPriceEntry : list) {
					if ((expirationDate.getTime() <=
							commerceTierPriceEntry.getExpirationDate(
							).getTime()) ||
						(status != commerceTierPriceEntry.getStatus())) {

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

			sb.append(_SQL_SELECT_COMMERCETIERPRICEENTRY_WHERE);

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
				sb.append(CommerceTierPriceEntryModelImpl.ORDER_BY_JPQL);
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

				list = (List<CommerceTierPriceEntry>)QueryUtil.list(
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
	 * Returns the first commerce tier price entry in the ordered set where expirationDate &lt; &#63; and status = &#63;.
	 *
	 * @param expirationDate the expiration date
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce tier price entry
	 * @throws NoSuchTierPriceEntryException if a matching commerce tier price entry could not be found
	 */
	@Override
	public CommerceTierPriceEntry findByLtE_S_First(
			Date expirationDate, int status,
			OrderByComparator<CommerceTierPriceEntry> orderByComparator)
		throws NoSuchTierPriceEntryException {

		CommerceTierPriceEntry commerceTierPriceEntry = fetchByLtE_S_First(
			expirationDate, status, orderByComparator);

		if (commerceTierPriceEntry != null) {
			return commerceTierPriceEntry;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("expirationDate<");
		sb.append(expirationDate);

		sb.append(", status=");
		sb.append(status);

		sb.append("}");

		throw new NoSuchTierPriceEntryException(sb.toString());
	}

	/**
	 * Returns the first commerce tier price entry in the ordered set where expirationDate &lt; &#63; and status = &#63;.
	 *
	 * @param expirationDate the expiration date
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce tier price entry, or <code>null</code> if a matching commerce tier price entry could not be found
	 */
	@Override
	public CommerceTierPriceEntry fetchByLtE_S_First(
		Date expirationDate, int status,
		OrderByComparator<CommerceTierPriceEntry> orderByComparator) {

		List<CommerceTierPriceEntry> list = findByLtE_S(
			expirationDate, status, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last commerce tier price entry in the ordered set where expirationDate &lt; &#63; and status = &#63;.
	 *
	 * @param expirationDate the expiration date
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce tier price entry
	 * @throws NoSuchTierPriceEntryException if a matching commerce tier price entry could not be found
	 */
	@Override
	public CommerceTierPriceEntry findByLtE_S_Last(
			Date expirationDate, int status,
			OrderByComparator<CommerceTierPriceEntry> orderByComparator)
		throws NoSuchTierPriceEntryException {

		CommerceTierPriceEntry commerceTierPriceEntry = fetchByLtE_S_Last(
			expirationDate, status, orderByComparator);

		if (commerceTierPriceEntry != null) {
			return commerceTierPriceEntry;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("expirationDate<");
		sb.append(expirationDate);

		sb.append(", status=");
		sb.append(status);

		sb.append("}");

		throw new NoSuchTierPriceEntryException(sb.toString());
	}

	/**
	 * Returns the last commerce tier price entry in the ordered set where expirationDate &lt; &#63; and status = &#63;.
	 *
	 * @param expirationDate the expiration date
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce tier price entry, or <code>null</code> if a matching commerce tier price entry could not be found
	 */
	@Override
	public CommerceTierPriceEntry fetchByLtE_S_Last(
		Date expirationDate, int status,
		OrderByComparator<CommerceTierPriceEntry> orderByComparator) {

		int count = countByLtE_S(expirationDate, status);

		if (count == 0) {
			return null;
		}

		List<CommerceTierPriceEntry> list = findByLtE_S(
			expirationDate, status, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the commerce tier price entries before and after the current commerce tier price entry in the ordered set where expirationDate &lt; &#63; and status = &#63;.
	 *
	 * @param commerceTierPriceEntryId the primary key of the current commerce tier price entry
	 * @param expirationDate the expiration date
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce tier price entry
	 * @throws NoSuchTierPriceEntryException if a commerce tier price entry with the primary key could not be found
	 */
	@Override
	public CommerceTierPriceEntry[] findByLtE_S_PrevAndNext(
			long commerceTierPriceEntryId, Date expirationDate, int status,
			OrderByComparator<CommerceTierPriceEntry> orderByComparator)
		throws NoSuchTierPriceEntryException {

		CommerceTierPriceEntry commerceTierPriceEntry = findByPrimaryKey(
			commerceTierPriceEntryId);

		Session session = null;

		try {
			session = openSession();

			CommerceTierPriceEntry[] array = new CommerceTierPriceEntryImpl[3];

			array[0] = getByLtE_S_PrevAndNext(
				session, commerceTierPriceEntry, expirationDate, status,
				orderByComparator, true);

			array[1] = commerceTierPriceEntry;

			array[2] = getByLtE_S_PrevAndNext(
				session, commerceTierPriceEntry, expirationDate, status,
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

	protected CommerceTierPriceEntry getByLtE_S_PrevAndNext(
		Session session, CommerceTierPriceEntry commerceTierPriceEntry,
		Date expirationDate, int status,
		OrderByComparator<CommerceTierPriceEntry> orderByComparator,
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

		sb.append(_SQL_SELECT_COMMERCETIERPRICEENTRY_WHERE);

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
			sb.append(CommerceTierPriceEntryModelImpl.ORDER_BY_JPQL);
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
						commerceTierPriceEntry)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<CommerceTierPriceEntry> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the commerce tier price entries where expirationDate &lt; &#63; and status = &#63; from the database.
	 *
	 * @param expirationDate the expiration date
	 * @param status the status
	 */
	@Override
	public void removeByLtE_S(Date expirationDate, int status) {
		for (CommerceTierPriceEntry commerceTierPriceEntry :
				findByLtE_S(
					expirationDate, status, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			remove(commerceTierPriceEntry);
		}
	}

	/**
	 * Returns the number of commerce tier price entries where expirationDate &lt; &#63; and status = &#63;.
	 *
	 * @param expirationDate the expiration date
	 * @param status the status
	 * @return the number of matching commerce tier price entries
	 */
	@Override
	public int countByLtE_S(Date expirationDate, int status) {
		boolean productionMode = ctPersistenceHelper.isProductionMode(
			CommerceTierPriceEntry.class);

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

			sb.append(_SQL_COUNT_COMMERCETIERPRICEENTRY_WHERE);

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
		"commerceTierPriceEntry.expirationDate IS NULL AND ";

	private static final String _FINDER_COLUMN_LTE_S_EXPIRATIONDATE_2 =
		"commerceTierPriceEntry.expirationDate < ? AND ";

	private static final String _FINDER_COLUMN_LTE_S_STATUS_2 =
		"commerceTierPriceEntry.status = ?";

	private FinderPath _finderPathWithPaginationFindByC_LteM_S;
	private FinderPath _finderPathWithPaginationCountByC_LteM_S;

	/**
	 * Returns all the commerce tier price entries where commercePriceEntryId = &#63; and minQuantity &le; &#63; and status = &#63;.
	 *
	 * @param commercePriceEntryId the commerce price entry ID
	 * @param minQuantity the min quantity
	 * @param status the status
	 * @return the matching commerce tier price entries
	 */
	@Override
	public List<CommerceTierPriceEntry> findByC_LteM_S(
		long commercePriceEntryId, int minQuantity, int status) {

		return findByC_LteM_S(
			commercePriceEntryId, minQuantity, status, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce tier price entries where commercePriceEntryId = &#63; and minQuantity &le; &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceTierPriceEntryModelImpl</code>.
	 * </p>
	 *
	 * @param commercePriceEntryId the commerce price entry ID
	 * @param minQuantity the min quantity
	 * @param status the status
	 * @param start the lower bound of the range of commerce tier price entries
	 * @param end the upper bound of the range of commerce tier price entries (not inclusive)
	 * @return the range of matching commerce tier price entries
	 */
	@Override
	public List<CommerceTierPriceEntry> findByC_LteM_S(
		long commercePriceEntryId, int minQuantity, int status, int start,
		int end) {

		return findByC_LteM_S(
			commercePriceEntryId, minQuantity, status, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce tier price entries where commercePriceEntryId = &#63; and minQuantity &le; &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceTierPriceEntryModelImpl</code>.
	 * </p>
	 *
	 * @param commercePriceEntryId the commerce price entry ID
	 * @param minQuantity the min quantity
	 * @param status the status
	 * @param start the lower bound of the range of commerce tier price entries
	 * @param end the upper bound of the range of commerce tier price entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce tier price entries
	 */
	@Override
	public List<CommerceTierPriceEntry> findByC_LteM_S(
		long commercePriceEntryId, int minQuantity, int status, int start,
		int end, OrderByComparator<CommerceTierPriceEntry> orderByComparator) {

		return findByC_LteM_S(
			commercePriceEntryId, minQuantity, status, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce tier price entries where commercePriceEntryId = &#63; and minQuantity &le; &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceTierPriceEntryModelImpl</code>.
	 * </p>
	 *
	 * @param commercePriceEntryId the commerce price entry ID
	 * @param minQuantity the min quantity
	 * @param status the status
	 * @param start the lower bound of the range of commerce tier price entries
	 * @param end the upper bound of the range of commerce tier price entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce tier price entries
	 */
	@Override
	public List<CommerceTierPriceEntry> findByC_LteM_S(
		long commercePriceEntryId, int minQuantity, int status, int start,
		int end, OrderByComparator<CommerceTierPriceEntry> orderByComparator,
		boolean useFinderCache) {

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			CommerceTierPriceEntry.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		finderPath = _finderPathWithPaginationFindByC_LteM_S;
		finderArgs = new Object[] {
			commercePriceEntryId, minQuantity, status, start, end,
			orderByComparator
		};

		List<CommerceTierPriceEntry> list = null;

		if (useFinderCache && productionMode) {
			list = (List<CommerceTierPriceEntry>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (CommerceTierPriceEntry commerceTierPriceEntry : list) {
					if ((commercePriceEntryId !=
							commerceTierPriceEntry.getCommercePriceEntryId()) ||
						(minQuantity <
							commerceTierPriceEntry.getMinQuantity()) ||
						(status != commerceTierPriceEntry.getStatus())) {

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

			sb.append(_SQL_SELECT_COMMERCETIERPRICEENTRY_WHERE);

			sb.append(_FINDER_COLUMN_C_LTEM_S_COMMERCEPRICEENTRYID_2);

			sb.append(_FINDER_COLUMN_C_LTEM_S_MINQUANTITY_2);

			sb.append(_FINDER_COLUMN_C_LTEM_S_STATUS_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(CommerceTierPriceEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(commercePriceEntryId);

				queryPos.add(minQuantity);

				queryPos.add(status);

				list = (List<CommerceTierPriceEntry>)QueryUtil.list(
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
	 * Returns the first commerce tier price entry in the ordered set where commercePriceEntryId = &#63; and minQuantity &le; &#63; and status = &#63;.
	 *
	 * @param commercePriceEntryId the commerce price entry ID
	 * @param minQuantity the min quantity
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce tier price entry
	 * @throws NoSuchTierPriceEntryException if a matching commerce tier price entry could not be found
	 */
	@Override
	public CommerceTierPriceEntry findByC_LteM_S_First(
			long commercePriceEntryId, int minQuantity, int status,
			OrderByComparator<CommerceTierPriceEntry> orderByComparator)
		throws NoSuchTierPriceEntryException {

		CommerceTierPriceEntry commerceTierPriceEntry = fetchByC_LteM_S_First(
			commercePriceEntryId, minQuantity, status, orderByComparator);

		if (commerceTierPriceEntry != null) {
			return commerceTierPriceEntry;
		}

		StringBundler sb = new StringBundler(8);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("commercePriceEntryId=");
		sb.append(commercePriceEntryId);

		sb.append(", minQuantity<=");
		sb.append(minQuantity);

		sb.append(", status=");
		sb.append(status);

		sb.append("}");

		throw new NoSuchTierPriceEntryException(sb.toString());
	}

	/**
	 * Returns the first commerce tier price entry in the ordered set where commercePriceEntryId = &#63; and minQuantity &le; &#63; and status = &#63;.
	 *
	 * @param commercePriceEntryId the commerce price entry ID
	 * @param minQuantity the min quantity
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce tier price entry, or <code>null</code> if a matching commerce tier price entry could not be found
	 */
	@Override
	public CommerceTierPriceEntry fetchByC_LteM_S_First(
		long commercePriceEntryId, int minQuantity, int status,
		OrderByComparator<CommerceTierPriceEntry> orderByComparator) {

		List<CommerceTierPriceEntry> list = findByC_LteM_S(
			commercePriceEntryId, minQuantity, status, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last commerce tier price entry in the ordered set where commercePriceEntryId = &#63; and minQuantity &le; &#63; and status = &#63;.
	 *
	 * @param commercePriceEntryId the commerce price entry ID
	 * @param minQuantity the min quantity
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce tier price entry
	 * @throws NoSuchTierPriceEntryException if a matching commerce tier price entry could not be found
	 */
	@Override
	public CommerceTierPriceEntry findByC_LteM_S_Last(
			long commercePriceEntryId, int minQuantity, int status,
			OrderByComparator<CommerceTierPriceEntry> orderByComparator)
		throws NoSuchTierPriceEntryException {

		CommerceTierPriceEntry commerceTierPriceEntry = fetchByC_LteM_S_Last(
			commercePriceEntryId, minQuantity, status, orderByComparator);

		if (commerceTierPriceEntry != null) {
			return commerceTierPriceEntry;
		}

		StringBundler sb = new StringBundler(8);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("commercePriceEntryId=");
		sb.append(commercePriceEntryId);

		sb.append(", minQuantity<=");
		sb.append(minQuantity);

		sb.append(", status=");
		sb.append(status);

		sb.append("}");

		throw new NoSuchTierPriceEntryException(sb.toString());
	}

	/**
	 * Returns the last commerce tier price entry in the ordered set where commercePriceEntryId = &#63; and minQuantity &le; &#63; and status = &#63;.
	 *
	 * @param commercePriceEntryId the commerce price entry ID
	 * @param minQuantity the min quantity
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce tier price entry, or <code>null</code> if a matching commerce tier price entry could not be found
	 */
	@Override
	public CommerceTierPriceEntry fetchByC_LteM_S_Last(
		long commercePriceEntryId, int minQuantity, int status,
		OrderByComparator<CommerceTierPriceEntry> orderByComparator) {

		int count = countByC_LteM_S(commercePriceEntryId, minQuantity, status);

		if (count == 0) {
			return null;
		}

		List<CommerceTierPriceEntry> list = findByC_LteM_S(
			commercePriceEntryId, minQuantity, status, count - 1, count,
			orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the commerce tier price entries before and after the current commerce tier price entry in the ordered set where commercePriceEntryId = &#63; and minQuantity &le; &#63; and status = &#63;.
	 *
	 * @param commerceTierPriceEntryId the primary key of the current commerce tier price entry
	 * @param commercePriceEntryId the commerce price entry ID
	 * @param minQuantity the min quantity
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce tier price entry
	 * @throws NoSuchTierPriceEntryException if a commerce tier price entry with the primary key could not be found
	 */
	@Override
	public CommerceTierPriceEntry[] findByC_LteM_S_PrevAndNext(
			long commerceTierPriceEntryId, long commercePriceEntryId,
			int minQuantity, int status,
			OrderByComparator<CommerceTierPriceEntry> orderByComparator)
		throws NoSuchTierPriceEntryException {

		CommerceTierPriceEntry commerceTierPriceEntry = findByPrimaryKey(
			commerceTierPriceEntryId);

		Session session = null;

		try {
			session = openSession();

			CommerceTierPriceEntry[] array = new CommerceTierPriceEntryImpl[3];

			array[0] = getByC_LteM_S_PrevAndNext(
				session, commerceTierPriceEntry, commercePriceEntryId,
				minQuantity, status, orderByComparator, true);

			array[1] = commerceTierPriceEntry;

			array[2] = getByC_LteM_S_PrevAndNext(
				session, commerceTierPriceEntry, commercePriceEntryId,
				minQuantity, status, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected CommerceTierPriceEntry getByC_LteM_S_PrevAndNext(
		Session session, CommerceTierPriceEntry commerceTierPriceEntry,
		long commercePriceEntryId, int minQuantity, int status,
		OrderByComparator<CommerceTierPriceEntry> orderByComparator,
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

		sb.append(_SQL_SELECT_COMMERCETIERPRICEENTRY_WHERE);

		sb.append(_FINDER_COLUMN_C_LTEM_S_COMMERCEPRICEENTRYID_2);

		sb.append(_FINDER_COLUMN_C_LTEM_S_MINQUANTITY_2);

		sb.append(_FINDER_COLUMN_C_LTEM_S_STATUS_2);

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
			sb.append(CommerceTierPriceEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(commercePriceEntryId);

		queryPos.add(minQuantity);

		queryPos.add(status);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						commerceTierPriceEntry)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<CommerceTierPriceEntry> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the commerce tier price entries where commercePriceEntryId = &#63; and minQuantity &le; &#63; and status = &#63; from the database.
	 *
	 * @param commercePriceEntryId the commerce price entry ID
	 * @param minQuantity the min quantity
	 * @param status the status
	 */
	@Override
	public void removeByC_LteM_S(
		long commercePriceEntryId, int minQuantity, int status) {

		for (CommerceTierPriceEntry commerceTierPriceEntry :
				findByC_LteM_S(
					commercePriceEntryId, minQuantity, status,
					QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(commerceTierPriceEntry);
		}
	}

	/**
	 * Returns the number of commerce tier price entries where commercePriceEntryId = &#63; and minQuantity &le; &#63; and status = &#63;.
	 *
	 * @param commercePriceEntryId the commerce price entry ID
	 * @param minQuantity the min quantity
	 * @param status the status
	 * @return the number of matching commerce tier price entries
	 */
	@Override
	public int countByC_LteM_S(
		long commercePriceEntryId, int minQuantity, int status) {

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			CommerceTierPriceEntry.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathWithPaginationCountByC_LteM_S;

			finderArgs = new Object[] {
				commercePriceEntryId, minQuantity, status
			};

			count = (Long)finderCache.getResult(finderPath, finderArgs);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_COUNT_COMMERCETIERPRICEENTRY_WHERE);

			sb.append(_FINDER_COLUMN_C_LTEM_S_COMMERCEPRICEENTRYID_2);

			sb.append(_FINDER_COLUMN_C_LTEM_S_MINQUANTITY_2);

			sb.append(_FINDER_COLUMN_C_LTEM_S_STATUS_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(commercePriceEntryId);

				queryPos.add(minQuantity);

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

	private static final String _FINDER_COLUMN_C_LTEM_S_COMMERCEPRICEENTRYID_2 =
		"commerceTierPriceEntry.commercePriceEntryId = ? AND ";

	private static final String _FINDER_COLUMN_C_LTEM_S_MINQUANTITY_2 =
		"commerceTierPriceEntry.minQuantity <= ? AND ";

	private static final String _FINDER_COLUMN_C_LTEM_S_STATUS_2 =
		"commerceTierPriceEntry.status = ?";

	private FinderPath _finderPathFetchByC_ERC;
	private FinderPath _finderPathCountByC_ERC;

	/**
	 * Returns the commerce tier price entry where companyId = &#63; and externalReferenceCode = &#63; or throws a <code>NoSuchTierPriceEntryException</code> if it could not be found.
	 *
	 * @param companyId the company ID
	 * @param externalReferenceCode the external reference code
	 * @return the matching commerce tier price entry
	 * @throws NoSuchTierPriceEntryException if a matching commerce tier price entry could not be found
	 */
	@Override
	public CommerceTierPriceEntry findByC_ERC(
			long companyId, String externalReferenceCode)
		throws NoSuchTierPriceEntryException {

		CommerceTierPriceEntry commerceTierPriceEntry = fetchByC_ERC(
			companyId, externalReferenceCode);

		if (commerceTierPriceEntry == null) {
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

			throw new NoSuchTierPriceEntryException(sb.toString());
		}

		return commerceTierPriceEntry;
	}

	/**
	 * Returns the commerce tier price entry where companyId = &#63; and externalReferenceCode = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param companyId the company ID
	 * @param externalReferenceCode the external reference code
	 * @return the matching commerce tier price entry, or <code>null</code> if a matching commerce tier price entry could not be found
	 */
	@Override
	public CommerceTierPriceEntry fetchByC_ERC(
		long companyId, String externalReferenceCode) {

		return fetchByC_ERC(companyId, externalReferenceCode, true);
	}

	/**
	 * Returns the commerce tier price entry where companyId = &#63; and externalReferenceCode = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param companyId the company ID
	 * @param externalReferenceCode the external reference code
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching commerce tier price entry, or <code>null</code> if a matching commerce tier price entry could not be found
	 */
	@Override
	public CommerceTierPriceEntry fetchByC_ERC(
		long companyId, String externalReferenceCode, boolean useFinderCache) {

		externalReferenceCode = Objects.toString(externalReferenceCode, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			CommerceTierPriceEntry.class);

		Object[] finderArgs = null;

		if (useFinderCache && productionMode) {
			finderArgs = new Object[] {companyId, externalReferenceCode};
		}

		Object result = null;

		if (useFinderCache && productionMode) {
			result = finderCache.getResult(_finderPathFetchByC_ERC, finderArgs);
		}

		if (result instanceof CommerceTierPriceEntry) {
			CommerceTierPriceEntry commerceTierPriceEntry =
				(CommerceTierPriceEntry)result;

			if ((companyId != commerceTierPriceEntry.getCompanyId()) ||
				!Objects.equals(
					externalReferenceCode,
					commerceTierPriceEntry.getExternalReferenceCode())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_SELECT_COMMERCETIERPRICEENTRY_WHERE);

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

				List<CommerceTierPriceEntry> list = query.list();

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
								"CommerceTierPriceEntryPersistenceImpl.fetchByC_ERC(long, String, boolean) with parameters (" +
									StringUtil.merge(finderArgs) +
										") yields a result set with more than 1 result. This violates the logical unique restriction. There is no order guarantee on which result is returned by this finder.");
						}
					}

					CommerceTierPriceEntry commerceTierPriceEntry = list.get(0);

					result = commerceTierPriceEntry;

					cacheResult(commerceTierPriceEntry);
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
			return (CommerceTierPriceEntry)result;
		}
	}

	/**
	 * Removes the commerce tier price entry where companyId = &#63; and externalReferenceCode = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param externalReferenceCode the external reference code
	 * @return the commerce tier price entry that was removed
	 */
	@Override
	public CommerceTierPriceEntry removeByC_ERC(
			long companyId, String externalReferenceCode)
		throws NoSuchTierPriceEntryException {

		CommerceTierPriceEntry commerceTierPriceEntry = findByC_ERC(
			companyId, externalReferenceCode);

		return remove(commerceTierPriceEntry);
	}

	/**
	 * Returns the number of commerce tier price entries where companyId = &#63; and externalReferenceCode = &#63;.
	 *
	 * @param companyId the company ID
	 * @param externalReferenceCode the external reference code
	 * @return the number of matching commerce tier price entries
	 */
	@Override
	public int countByC_ERC(long companyId, String externalReferenceCode) {
		externalReferenceCode = Objects.toString(externalReferenceCode, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			CommerceTierPriceEntry.class);

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

			sb.append(_SQL_COUNT_COMMERCETIERPRICEENTRY_WHERE);

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
		"commerceTierPriceEntry.companyId = ? AND ";

	private static final String _FINDER_COLUMN_C_ERC_EXTERNALREFERENCECODE_2 =
		"commerceTierPriceEntry.externalReferenceCode = ?";

	private static final String _FINDER_COLUMN_C_ERC_EXTERNALREFERENCECODE_3 =
		"(commerceTierPriceEntry.externalReferenceCode IS NULL OR commerceTierPriceEntry.externalReferenceCode = '')";

	public CommerceTierPriceEntryPersistenceImpl() {
		Map<String, String> dbColumnNames = new HashMap<String, String>();

		dbColumnNames.put("uuid", "uuid_");

		setDBColumnNames(dbColumnNames);

		setModelClass(CommerceTierPriceEntry.class);

		setModelImplClass(CommerceTierPriceEntryImpl.class);
		setModelPKClass(long.class);

		setTable(CommerceTierPriceEntryTable.INSTANCE);
	}

	/**
	 * Caches the commerce tier price entry in the entity cache if it is enabled.
	 *
	 * @param commerceTierPriceEntry the commerce tier price entry
	 */
	@Override
	public void cacheResult(CommerceTierPriceEntry commerceTierPriceEntry) {
		if (commerceTierPriceEntry.getCtCollectionId() != 0) {
			return;
		}

		entityCache.putResult(
			CommerceTierPriceEntryImpl.class,
			commerceTierPriceEntry.getPrimaryKey(), commerceTierPriceEntry);

		finderCache.putResult(
			_finderPathFetchByC_M,
			new Object[] {
				commerceTierPriceEntry.getCommercePriceEntryId(),
				commerceTierPriceEntry.getMinQuantity()
			},
			commerceTierPriceEntry);

		finderCache.putResult(
			_finderPathFetchByC_ERC,
			new Object[] {
				commerceTierPriceEntry.getCompanyId(),
				commerceTierPriceEntry.getExternalReferenceCode()
			},
			commerceTierPriceEntry);
	}

	private int _valueObjectFinderCacheListThreshold;

	/**
	 * Caches the commerce tier price entries in the entity cache if it is enabled.
	 *
	 * @param commerceTierPriceEntries the commerce tier price entries
	 */
	@Override
	public void cacheResult(
		List<CommerceTierPriceEntry> commerceTierPriceEntries) {

		if ((_valueObjectFinderCacheListThreshold == 0) ||
			((_valueObjectFinderCacheListThreshold > 0) &&
			 (commerceTierPriceEntries.size() >
				 _valueObjectFinderCacheListThreshold))) {

			return;
		}

		for (CommerceTierPriceEntry commerceTierPriceEntry :
				commerceTierPriceEntries) {

			if (commerceTierPriceEntry.getCtCollectionId() != 0) {
				continue;
			}

			if (entityCache.getResult(
					CommerceTierPriceEntryImpl.class,
					commerceTierPriceEntry.getPrimaryKey()) == null) {

				cacheResult(commerceTierPriceEntry);
			}
		}
	}

	/**
	 * Clears the cache for all commerce tier price entries.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(CommerceTierPriceEntryImpl.class);

		finderCache.clearCache(CommerceTierPriceEntryImpl.class);
	}

	/**
	 * Clears the cache for the commerce tier price entry.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(CommerceTierPriceEntry commerceTierPriceEntry) {
		entityCache.removeResult(
			CommerceTierPriceEntryImpl.class, commerceTierPriceEntry);
	}

	@Override
	public void clearCache(
		List<CommerceTierPriceEntry> commerceTierPriceEntries) {

		for (CommerceTierPriceEntry commerceTierPriceEntry :
				commerceTierPriceEntries) {

			entityCache.removeResult(
				CommerceTierPriceEntryImpl.class, commerceTierPriceEntry);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(CommerceTierPriceEntryImpl.class);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(
				CommerceTierPriceEntryImpl.class, primaryKey);
		}
	}

	protected void cacheUniqueFindersCache(
		CommerceTierPriceEntryModelImpl commerceTierPriceEntryModelImpl) {

		Object[] args = new Object[] {
			commerceTierPriceEntryModelImpl.getCommercePriceEntryId(),
			commerceTierPriceEntryModelImpl.getMinQuantity()
		};

		finderCache.putResult(_finderPathCountByC_M, args, Long.valueOf(1));
		finderCache.putResult(
			_finderPathFetchByC_M, args, commerceTierPriceEntryModelImpl);

		args = new Object[] {
			commerceTierPriceEntryModelImpl.getCompanyId(),
			commerceTierPriceEntryModelImpl.getExternalReferenceCode()
		};

		finderCache.putResult(_finderPathCountByC_ERC, args, Long.valueOf(1));
		finderCache.putResult(
			_finderPathFetchByC_ERC, args, commerceTierPriceEntryModelImpl);
	}

	/**
	 * Creates a new commerce tier price entry with the primary key. Does not add the commerce tier price entry to the database.
	 *
	 * @param commerceTierPriceEntryId the primary key for the new commerce tier price entry
	 * @return the new commerce tier price entry
	 */
	@Override
	public CommerceTierPriceEntry create(long commerceTierPriceEntryId) {
		CommerceTierPriceEntry commerceTierPriceEntry =
			new CommerceTierPriceEntryImpl();

		commerceTierPriceEntry.setNew(true);
		commerceTierPriceEntry.setPrimaryKey(commerceTierPriceEntryId);

		String uuid = PortalUUIDUtil.generate();

		commerceTierPriceEntry.setUuid(uuid);

		commerceTierPriceEntry.setCompanyId(CompanyThreadLocal.getCompanyId());

		return commerceTierPriceEntry;
	}

	/**
	 * Removes the commerce tier price entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param commerceTierPriceEntryId the primary key of the commerce tier price entry
	 * @return the commerce tier price entry that was removed
	 * @throws NoSuchTierPriceEntryException if a commerce tier price entry with the primary key could not be found
	 */
	@Override
	public CommerceTierPriceEntry remove(long commerceTierPriceEntryId)
		throws NoSuchTierPriceEntryException {

		return remove((Serializable)commerceTierPriceEntryId);
	}

	/**
	 * Removes the commerce tier price entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the commerce tier price entry
	 * @return the commerce tier price entry that was removed
	 * @throws NoSuchTierPriceEntryException if a commerce tier price entry with the primary key could not be found
	 */
	@Override
	public CommerceTierPriceEntry remove(Serializable primaryKey)
		throws NoSuchTierPriceEntryException {

		Session session = null;

		try {
			session = openSession();

			CommerceTierPriceEntry commerceTierPriceEntry =
				(CommerceTierPriceEntry)session.get(
					CommerceTierPriceEntryImpl.class, primaryKey);

			if (commerceTierPriceEntry == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchTierPriceEntryException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(commerceTierPriceEntry);
		}
		catch (NoSuchTierPriceEntryException noSuchEntityException) {
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
	protected CommerceTierPriceEntry removeImpl(
		CommerceTierPriceEntry commerceTierPriceEntry) {

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(commerceTierPriceEntry)) {
				commerceTierPriceEntry = (CommerceTierPriceEntry)session.get(
					CommerceTierPriceEntryImpl.class,
					commerceTierPriceEntry.getPrimaryKeyObj());
			}

			if ((commerceTierPriceEntry != null) &&
				ctPersistenceHelper.isRemove(commerceTierPriceEntry)) {

				session.delete(commerceTierPriceEntry);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (commerceTierPriceEntry != null) {
			clearCache(commerceTierPriceEntry);
		}

		return commerceTierPriceEntry;
	}

	@Override
	public CommerceTierPriceEntry updateImpl(
		CommerceTierPriceEntry commerceTierPriceEntry) {

		boolean isNew = commerceTierPriceEntry.isNew();

		if (!(commerceTierPriceEntry instanceof
				CommerceTierPriceEntryModelImpl)) {

			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(commerceTierPriceEntry.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					commerceTierPriceEntry);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in commerceTierPriceEntry proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom CommerceTierPriceEntry implementation " +
					commerceTierPriceEntry.getClass());
		}

		CommerceTierPriceEntryModelImpl commerceTierPriceEntryModelImpl =
			(CommerceTierPriceEntryModelImpl)commerceTierPriceEntry;

		if (Validator.isNull(commerceTierPriceEntry.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			commerceTierPriceEntry.setUuid(uuid);
		}

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Date date = new Date();

		if (isNew && (commerceTierPriceEntry.getCreateDate() == null)) {
			if (serviceContext == null) {
				commerceTierPriceEntry.setCreateDate(date);
			}
			else {
				commerceTierPriceEntry.setCreateDate(
					serviceContext.getCreateDate(date));
			}
		}

		if (!commerceTierPriceEntryModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				commerceTierPriceEntry.setModifiedDate(date);
			}
			else {
				commerceTierPriceEntry.setModifiedDate(
					serviceContext.getModifiedDate(date));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (ctPersistenceHelper.isInsert(commerceTierPriceEntry)) {
				if (!isNew) {
					session.evict(
						CommerceTierPriceEntryImpl.class,
						commerceTierPriceEntry.getPrimaryKeyObj());
				}

				session.save(commerceTierPriceEntry);
			}
			else {
				commerceTierPriceEntry = (CommerceTierPriceEntry)session.merge(
					commerceTierPriceEntry);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (commerceTierPriceEntry.getCtCollectionId() != 0) {
			if (isNew) {
				commerceTierPriceEntry.setNew(false);
			}

			commerceTierPriceEntry.resetOriginalValues();

			return commerceTierPriceEntry;
		}

		entityCache.putResult(
			CommerceTierPriceEntryImpl.class, commerceTierPriceEntryModelImpl,
			false, true);

		cacheUniqueFindersCache(commerceTierPriceEntryModelImpl);

		if (isNew) {
			commerceTierPriceEntry.setNew(false);
		}

		commerceTierPriceEntry.resetOriginalValues();

		return commerceTierPriceEntry;
	}

	/**
	 * Returns the commerce tier price entry with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the commerce tier price entry
	 * @return the commerce tier price entry
	 * @throws NoSuchTierPriceEntryException if a commerce tier price entry with the primary key could not be found
	 */
	@Override
	public CommerceTierPriceEntry findByPrimaryKey(Serializable primaryKey)
		throws NoSuchTierPriceEntryException {

		CommerceTierPriceEntry commerceTierPriceEntry = fetchByPrimaryKey(
			primaryKey);

		if (commerceTierPriceEntry == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchTierPriceEntryException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return commerceTierPriceEntry;
	}

	/**
	 * Returns the commerce tier price entry with the primary key or throws a <code>NoSuchTierPriceEntryException</code> if it could not be found.
	 *
	 * @param commerceTierPriceEntryId the primary key of the commerce tier price entry
	 * @return the commerce tier price entry
	 * @throws NoSuchTierPriceEntryException if a commerce tier price entry with the primary key could not be found
	 */
	@Override
	public CommerceTierPriceEntry findByPrimaryKey(
			long commerceTierPriceEntryId)
		throws NoSuchTierPriceEntryException {

		return findByPrimaryKey((Serializable)commerceTierPriceEntryId);
	}

	/**
	 * Returns the commerce tier price entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the commerce tier price entry
	 * @return the commerce tier price entry, or <code>null</code> if a commerce tier price entry with the primary key could not be found
	 */
	@Override
	public CommerceTierPriceEntry fetchByPrimaryKey(Serializable primaryKey) {
		if (ctPersistenceHelper.isProductionMode(
				CommerceTierPriceEntry.class)) {

			return super.fetchByPrimaryKey(primaryKey);
		}

		CommerceTierPriceEntry commerceTierPriceEntry = null;

		Session session = null;

		try {
			session = openSession();

			commerceTierPriceEntry = (CommerceTierPriceEntry)session.get(
				CommerceTierPriceEntryImpl.class, primaryKey);

			if (commerceTierPriceEntry != null) {
				cacheResult(commerceTierPriceEntry);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		return commerceTierPriceEntry;
	}

	/**
	 * Returns the commerce tier price entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param commerceTierPriceEntryId the primary key of the commerce tier price entry
	 * @return the commerce tier price entry, or <code>null</code> if a commerce tier price entry with the primary key could not be found
	 */
	@Override
	public CommerceTierPriceEntry fetchByPrimaryKey(
		long commerceTierPriceEntryId) {

		return fetchByPrimaryKey((Serializable)commerceTierPriceEntryId);
	}

	@Override
	public Map<Serializable, CommerceTierPriceEntry> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		if (ctPersistenceHelper.isProductionMode(
				CommerceTierPriceEntry.class)) {

			return super.fetchByPrimaryKeys(primaryKeys);
		}

		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, CommerceTierPriceEntry> map =
			new HashMap<Serializable, CommerceTierPriceEntry>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			CommerceTierPriceEntry commerceTierPriceEntry = fetchByPrimaryKey(
				primaryKey);

			if (commerceTierPriceEntry != null) {
				map.put(primaryKey, commerceTierPriceEntry);
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

			for (CommerceTierPriceEntry commerceTierPriceEntry :
					(List<CommerceTierPriceEntry>)query.list()) {

				map.put(
					commerceTierPriceEntry.getPrimaryKeyObj(),
					commerceTierPriceEntry);

				cacheResult(commerceTierPriceEntry);
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
	 * Returns all the commerce tier price entries.
	 *
	 * @return the commerce tier price entries
	 */
	@Override
	public List<CommerceTierPriceEntry> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce tier price entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceTierPriceEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce tier price entries
	 * @param end the upper bound of the range of commerce tier price entries (not inclusive)
	 * @return the range of commerce tier price entries
	 */
	@Override
	public List<CommerceTierPriceEntry> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce tier price entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceTierPriceEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce tier price entries
	 * @param end the upper bound of the range of commerce tier price entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of commerce tier price entries
	 */
	@Override
	public List<CommerceTierPriceEntry> findAll(
		int start, int end,
		OrderByComparator<CommerceTierPriceEntry> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce tier price entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceTierPriceEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce tier price entries
	 * @param end the upper bound of the range of commerce tier price entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of commerce tier price entries
	 */
	@Override
	public List<CommerceTierPriceEntry> findAll(
		int start, int end,
		OrderByComparator<CommerceTierPriceEntry> orderByComparator,
		boolean useFinderCache) {

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			CommerceTierPriceEntry.class);

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

		List<CommerceTierPriceEntry> list = null;

		if (useFinderCache && productionMode) {
			list = (List<CommerceTierPriceEntry>)finderCache.getResult(
				finderPath, finderArgs);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_COMMERCETIERPRICEENTRY);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_COMMERCETIERPRICEENTRY;

				sql = sql.concat(CommerceTierPriceEntryModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<CommerceTierPriceEntry>)QueryUtil.list(
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
	 * Removes all the commerce tier price entries from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (CommerceTierPriceEntry commerceTierPriceEntry : findAll()) {
			remove(commerceTierPriceEntry);
		}
	}

	/**
	 * Returns the number of commerce tier price entries.
	 *
	 * @return the number of commerce tier price entries
	 */
	@Override
	public int countAll() {
		boolean productionMode = ctPersistenceHelper.isProductionMode(
			CommerceTierPriceEntry.class);

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
					_SQL_COUNT_COMMERCETIERPRICEENTRY);

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
		return "commerceTierPriceEntryId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_COMMERCETIERPRICEENTRY;
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
		return CommerceTierPriceEntryModelImpl.TABLE_COLUMNS_MAP;
	}

	@Override
	public String getTableName() {
		return "CommerceTierPriceEntry";
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
		ctStrictColumnNames.add("commercePriceEntryId");
		ctStrictColumnNames.add("price");
		ctStrictColumnNames.add("promoPrice");
		ctStrictColumnNames.add("discountDiscovery");
		ctStrictColumnNames.add("discountLevel1");
		ctStrictColumnNames.add("discountLevel2");
		ctStrictColumnNames.add("discountLevel3");
		ctStrictColumnNames.add("discountLevel4");
		ctStrictColumnNames.add("minQuantity");
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
			Collections.singleton("commerceTierPriceEntryId"));
		_ctColumnNamesMap.put(
			CTColumnResolutionType.STRICT, ctStrictColumnNames);

		_uniqueIndexColumnNames.add(
			new String[] {"commercePriceEntryId", "minQuantity"});
	}

	/**
	 * Initializes the commerce tier price entry persistence.
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

		_finderPathWithPaginationFindByCommercePriceEntryId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByCommercePriceEntryId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"commercePriceEntryId"}, true);

		_finderPathWithoutPaginationFindByCommercePriceEntryId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByCommercePriceEntryId", new String[] {Long.class.getName()},
			new String[] {"commercePriceEntryId"}, true);

		_finderPathCountByCommercePriceEntryId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByCommercePriceEntryId", new String[] {Long.class.getName()},
			new String[] {"commercePriceEntryId"}, false);

		_finderPathFetchByC_M = new FinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByC_M",
			new String[] {Long.class.getName(), Integer.class.getName()},
			new String[] {"commercePriceEntryId", "minQuantity"}, true);

		_finderPathCountByC_M = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_M",
			new String[] {Long.class.getName(), Integer.class.getName()},
			new String[] {"commercePriceEntryId", "minQuantity"}, false);

		_finderPathWithPaginationFindByC_LteM = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByC_LteM",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {"commercePriceEntryId", "minQuantity"}, true);

		_finderPathWithPaginationCountByC_LteM = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "countByC_LteM",
			new String[] {Long.class.getName(), Integer.class.getName()},
			new String[] {"commercePriceEntryId", "minQuantity"}, false);

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

		_finderPathWithPaginationFindByC_LteM_S = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByC_LteM_S",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"commercePriceEntryId", "minQuantity", "status"},
			true);

		_finderPathWithPaginationCountByC_LteM_S = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "countByC_LteM_S",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName()
			},
			new String[] {"commercePriceEntryId", "minQuantity", "status"},
			false);

		_finderPathFetchByC_ERC = new FinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByC_ERC",
			new String[] {Long.class.getName(), String.class.getName()},
			new String[] {"companyId", "externalReferenceCode"}, true);

		_finderPathCountByC_ERC = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_ERC",
			new String[] {Long.class.getName(), String.class.getName()},
			new String[] {"companyId", "externalReferenceCode"}, false);

		_setCommerceTierPriceEntryUtilPersistence(this);
	}

	public void destroy() {
		_setCommerceTierPriceEntryUtilPersistence(null);

		entityCache.removeCache(CommerceTierPriceEntryImpl.class.getName());
	}

	private void _setCommerceTierPriceEntryUtilPersistence(
		CommerceTierPriceEntryPersistence commerceTierPriceEntryPersistence) {

		try {
			Field field = CommerceTierPriceEntryUtil.class.getDeclaredField(
				"_persistence");

			field.setAccessible(true);

			field.set(null, commerceTierPriceEntryPersistence);
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

	private static final String _SQL_SELECT_COMMERCETIERPRICEENTRY =
		"SELECT commerceTierPriceEntry FROM CommerceTierPriceEntry commerceTierPriceEntry";

	private static final String _SQL_SELECT_COMMERCETIERPRICEENTRY_WHERE =
		"SELECT commerceTierPriceEntry FROM CommerceTierPriceEntry commerceTierPriceEntry WHERE ";

	private static final String _SQL_COUNT_COMMERCETIERPRICEENTRY =
		"SELECT COUNT(commerceTierPriceEntry) FROM CommerceTierPriceEntry commerceTierPriceEntry";

	private static final String _SQL_COUNT_COMMERCETIERPRICEENTRY_WHERE =
		"SELECT COUNT(commerceTierPriceEntry) FROM CommerceTierPriceEntry commerceTierPriceEntry WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS =
		"commerceTierPriceEntry.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No CommerceTierPriceEntry exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No CommerceTierPriceEntry exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		CommerceTierPriceEntryPersistenceImpl.class);

	private static final Set<String> _badColumnNames = SetUtil.fromArray(
		new String[] {"uuid"});

	@Override
	protected FinderCache getFinderCache() {
		return finderCache;
	}

}