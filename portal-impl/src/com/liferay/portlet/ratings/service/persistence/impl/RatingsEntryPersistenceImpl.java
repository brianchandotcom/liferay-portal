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

package com.liferay.portlet.ratings.service.persistence.impl;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.change.tracking.CTColumnResolutionType;
import com.liferay.portal.kernel.dao.orm.EntityCache;
import com.liferay.portal.kernel.dao.orm.EntityCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderCacheUtil;
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
import com.liferay.portal.kernel.service.persistence.change.tracking.helper.CTPersistenceHelperUtil;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;
import com.liferay.portlet.ratings.model.impl.RatingsEntryImpl;
import com.liferay.portlet.ratings.model.impl.RatingsEntryModelImpl;
import com.liferay.ratings.kernel.exception.NoSuchEntryException;
import com.liferay.ratings.kernel.model.RatingsEntry;
import com.liferay.ratings.kernel.model.RatingsEntryTable;
import com.liferay.ratings.kernel.service.persistence.RatingsEntryPersistence;

import java.io.Serializable;

import java.lang.reflect.InvocationHandler;

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
 * The persistence implementation for the ratings entry service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class RatingsEntryPersistenceImpl
	extends BasePersistenceImpl<RatingsEntry>
	implements RatingsEntryPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>RatingsEntryUtil</code> to access the ratings entry persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		RatingsEntryImpl.class.getName();

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
	 * Returns all the ratings entries where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching ratings entries
	 */
	@Override
	public List<RatingsEntry> findByUuid(String uuid) {
		return findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the ratings entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RatingsEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of ratings entries
	 * @param end the upper bound of the range of ratings entries (not inclusive)
	 * @return the range of matching ratings entries
	 */
	@Override
	public List<RatingsEntry> findByUuid(String uuid, int start, int end) {
		return findByUuid(uuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the ratings entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RatingsEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of ratings entries
	 * @param end the upper bound of the range of ratings entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching ratings entries
	 */
	@Override
	public List<RatingsEntry> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<RatingsEntry> orderByComparator) {

		return findByUuid(uuid, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the ratings entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RatingsEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of ratings entries
	 * @param end the upper bound of the range of ratings entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching ratings entries
	 */
	@Override
	public List<RatingsEntry> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<RatingsEntry> orderByComparator,
		boolean useFinderCache) {

		uuid = Objects.toString(uuid, "");

		boolean productionMode = CTPersistenceHelperUtil.isProductionMode(
			RatingsEntry.class);

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

		List<RatingsEntry> list = null;

		if (useFinderCache && productionMode) {
			list = (List<RatingsEntry>)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (RatingsEntry ratingsEntry : list) {
					if (!uuid.equals(ratingsEntry.getUuid())) {
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

			sb.append(_SQL_SELECT_RATINGSENTRY_WHERE);

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
				sb.append(RatingsEntryModelImpl.ORDER_BY_JPQL);
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

				list = (List<RatingsEntry>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache && productionMode) {
					FinderCacheUtil.putResult(finderPath, finderArgs, list);
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
	 * Returns the first ratings entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ratings entry
	 * @throws NoSuchEntryException if a matching ratings entry could not be found
	 */
	@Override
	public RatingsEntry findByUuid_First(
			String uuid, OrderByComparator<RatingsEntry> orderByComparator)
		throws NoSuchEntryException {

		RatingsEntry ratingsEntry = fetchByUuid_First(uuid, orderByComparator);

		if (ratingsEntry != null) {
			return ratingsEntry;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append("}");

		throw new NoSuchEntryException(sb.toString());
	}

	/**
	 * Returns the first ratings entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ratings entry, or <code>null</code> if a matching ratings entry could not be found
	 */
	@Override
	public RatingsEntry fetchByUuid_First(
		String uuid, OrderByComparator<RatingsEntry> orderByComparator) {

		List<RatingsEntry> list = findByUuid(uuid, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last ratings entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ratings entry
	 * @throws NoSuchEntryException if a matching ratings entry could not be found
	 */
	@Override
	public RatingsEntry findByUuid_Last(
			String uuid, OrderByComparator<RatingsEntry> orderByComparator)
		throws NoSuchEntryException {

		RatingsEntry ratingsEntry = fetchByUuid_Last(uuid, orderByComparator);

		if (ratingsEntry != null) {
			return ratingsEntry;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append("}");

		throw new NoSuchEntryException(sb.toString());
	}

	/**
	 * Returns the last ratings entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ratings entry, or <code>null</code> if a matching ratings entry could not be found
	 */
	@Override
	public RatingsEntry fetchByUuid_Last(
		String uuid, OrderByComparator<RatingsEntry> orderByComparator) {

		int count = countByUuid(uuid);

		if (count == 0) {
			return null;
		}

		List<RatingsEntry> list = findByUuid(
			uuid, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the ratings entries before and after the current ratings entry in the ordered set where uuid = &#63;.
	 *
	 * @param entryId the primary key of the current ratings entry
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next ratings entry
	 * @throws NoSuchEntryException if a ratings entry with the primary key could not be found
	 */
	@Override
	public RatingsEntry[] findByUuid_PrevAndNext(
			long entryId, String uuid,
			OrderByComparator<RatingsEntry> orderByComparator)
		throws NoSuchEntryException {

		uuid = Objects.toString(uuid, "");

		RatingsEntry ratingsEntry = findByPrimaryKey(entryId);

		Session session = null;

		try {
			session = openSession();

			RatingsEntry[] array = new RatingsEntryImpl[3];

			array[0] = getByUuid_PrevAndNext(
				session, ratingsEntry, uuid, orderByComparator, true);

			array[1] = ratingsEntry;

			array[2] = getByUuid_PrevAndNext(
				session, ratingsEntry, uuid, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected RatingsEntry getByUuid_PrevAndNext(
		Session session, RatingsEntry ratingsEntry, String uuid,
		OrderByComparator<RatingsEntry> orderByComparator, boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(3);
		}

		sb.append(_SQL_SELECT_RATINGSENTRY_WHERE);

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
			sb.append(RatingsEntryModelImpl.ORDER_BY_JPQL);
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
					orderByComparator.getOrderByConditionValues(ratingsEntry)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<RatingsEntry> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the ratings entries where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	@Override
	public void removeByUuid(String uuid) {
		for (RatingsEntry ratingsEntry :
				findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(ratingsEntry);
		}
	}

	/**
	 * Returns the number of ratings entries where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching ratings entries
	 */
	@Override
	public int countByUuid(String uuid) {
		uuid = Objects.toString(uuid, "");

		boolean productionMode = CTPersistenceHelperUtil.isProductionMode(
			RatingsEntry.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByUuid;

			finderArgs = new Object[] {uuid};

			count = (Long)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_RATINGSENTRY_WHERE);

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
					FinderCacheUtil.putResult(finderPath, finderArgs, count);
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
		"ratingsEntry.uuid = ?";

	private static final String _FINDER_COLUMN_UUID_UUID_3 =
		"(ratingsEntry.uuid IS NULL OR ratingsEntry.uuid = '')";

	private FinderPath _finderPathWithPaginationFindByUuid_C;
	private FinderPath _finderPathWithoutPaginationFindByUuid_C;
	private FinderPath _finderPathCountByUuid_C;

	/**
	 * Returns all the ratings entries where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching ratings entries
	 */
	@Override
	public List<RatingsEntry> findByUuid_C(String uuid, long companyId) {
		return findByUuid_C(
			uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the ratings entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RatingsEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of ratings entries
	 * @param end the upper bound of the range of ratings entries (not inclusive)
	 * @return the range of matching ratings entries
	 */
	@Override
	public List<RatingsEntry> findByUuid_C(
		String uuid, long companyId, int start, int end) {

		return findByUuid_C(uuid, companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the ratings entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RatingsEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of ratings entries
	 * @param end the upper bound of the range of ratings entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching ratings entries
	 */
	@Override
	public List<RatingsEntry> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<RatingsEntry> orderByComparator) {

		return findByUuid_C(
			uuid, companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the ratings entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RatingsEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of ratings entries
	 * @param end the upper bound of the range of ratings entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching ratings entries
	 */
	@Override
	public List<RatingsEntry> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<RatingsEntry> orderByComparator,
		boolean useFinderCache) {

		uuid = Objects.toString(uuid, "");

		boolean productionMode = CTPersistenceHelperUtil.isProductionMode(
			RatingsEntry.class);

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

		List<RatingsEntry> list = null;

		if (useFinderCache && productionMode) {
			list = (List<RatingsEntry>)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (RatingsEntry ratingsEntry : list) {
					if (!uuid.equals(ratingsEntry.getUuid()) ||
						(companyId != ratingsEntry.getCompanyId())) {

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

			sb.append(_SQL_SELECT_RATINGSENTRY_WHERE);

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
				sb.append(RatingsEntryModelImpl.ORDER_BY_JPQL);
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

				list = (List<RatingsEntry>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache && productionMode) {
					FinderCacheUtil.putResult(finderPath, finderArgs, list);
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
	 * Returns the first ratings entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ratings entry
	 * @throws NoSuchEntryException if a matching ratings entry could not be found
	 */
	@Override
	public RatingsEntry findByUuid_C_First(
			String uuid, long companyId,
			OrderByComparator<RatingsEntry> orderByComparator)
		throws NoSuchEntryException {

		RatingsEntry ratingsEntry = fetchByUuid_C_First(
			uuid, companyId, orderByComparator);

		if (ratingsEntry != null) {
			return ratingsEntry;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append(", companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchEntryException(sb.toString());
	}

	/**
	 * Returns the first ratings entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ratings entry, or <code>null</code> if a matching ratings entry could not be found
	 */
	@Override
	public RatingsEntry fetchByUuid_C_First(
		String uuid, long companyId,
		OrderByComparator<RatingsEntry> orderByComparator) {

		List<RatingsEntry> list = findByUuid_C(
			uuid, companyId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last ratings entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ratings entry
	 * @throws NoSuchEntryException if a matching ratings entry could not be found
	 */
	@Override
	public RatingsEntry findByUuid_C_Last(
			String uuid, long companyId,
			OrderByComparator<RatingsEntry> orderByComparator)
		throws NoSuchEntryException {

		RatingsEntry ratingsEntry = fetchByUuid_C_Last(
			uuid, companyId, orderByComparator);

		if (ratingsEntry != null) {
			return ratingsEntry;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append(", companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchEntryException(sb.toString());
	}

	/**
	 * Returns the last ratings entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ratings entry, or <code>null</code> if a matching ratings entry could not be found
	 */
	@Override
	public RatingsEntry fetchByUuid_C_Last(
		String uuid, long companyId,
		OrderByComparator<RatingsEntry> orderByComparator) {

		int count = countByUuid_C(uuid, companyId);

		if (count == 0) {
			return null;
		}

		List<RatingsEntry> list = findByUuid_C(
			uuid, companyId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the ratings entries before and after the current ratings entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param entryId the primary key of the current ratings entry
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next ratings entry
	 * @throws NoSuchEntryException if a ratings entry with the primary key could not be found
	 */
	@Override
	public RatingsEntry[] findByUuid_C_PrevAndNext(
			long entryId, String uuid, long companyId,
			OrderByComparator<RatingsEntry> orderByComparator)
		throws NoSuchEntryException {

		uuid = Objects.toString(uuid, "");

		RatingsEntry ratingsEntry = findByPrimaryKey(entryId);

		Session session = null;

		try {
			session = openSession();

			RatingsEntry[] array = new RatingsEntryImpl[3];

			array[0] = getByUuid_C_PrevAndNext(
				session, ratingsEntry, uuid, companyId, orderByComparator,
				true);

			array[1] = ratingsEntry;

			array[2] = getByUuid_C_PrevAndNext(
				session, ratingsEntry, uuid, companyId, orderByComparator,
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

	protected RatingsEntry getByUuid_C_PrevAndNext(
		Session session, RatingsEntry ratingsEntry, String uuid, long companyId,
		OrderByComparator<RatingsEntry> orderByComparator, boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				5 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(4);
		}

		sb.append(_SQL_SELECT_RATINGSENTRY_WHERE);

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
			sb.append(RatingsEntryModelImpl.ORDER_BY_JPQL);
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
					orderByComparator.getOrderByConditionValues(ratingsEntry)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<RatingsEntry> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the ratings entries where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	@Override
	public void removeByUuid_C(String uuid, long companyId) {
		for (RatingsEntry ratingsEntry :
				findByUuid_C(
					uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(ratingsEntry);
		}
	}

	/**
	 * Returns the number of ratings entries where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching ratings entries
	 */
	@Override
	public int countByUuid_C(String uuid, long companyId) {
		uuid = Objects.toString(uuid, "");

		boolean productionMode = CTPersistenceHelperUtil.isProductionMode(
			RatingsEntry.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByUuid_C;

			finderArgs = new Object[] {uuid, companyId};

			count = (Long)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_RATINGSENTRY_WHERE);

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
					FinderCacheUtil.putResult(finderPath, finderArgs, count);
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
		"ratingsEntry.uuid = ? AND ";

	private static final String _FINDER_COLUMN_UUID_C_UUID_3 =
		"(ratingsEntry.uuid IS NULL OR ratingsEntry.uuid = '') AND ";

	private static final String _FINDER_COLUMN_UUID_C_COMPANYID_2 =
		"ratingsEntry.companyId = ?";

	private FinderPath _finderPathWithPaginationFindByC_C;
	private FinderPath _finderPathWithoutPaginationFindByC_C;
	private FinderPath _finderPathCountByC_C;

	/**
	 * Returns all the ratings entries where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the matching ratings entries
	 */
	@Override
	public List<RatingsEntry> findByC_C(long classNameId, long classPK) {
		return findByC_C(
			classNameId, classPK, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the ratings entries where classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RatingsEntryModelImpl</code>.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param start the lower bound of the range of ratings entries
	 * @param end the upper bound of the range of ratings entries (not inclusive)
	 * @return the range of matching ratings entries
	 */
	@Override
	public List<RatingsEntry> findByC_C(
		long classNameId, long classPK, int start, int end) {

		return findByC_C(classNameId, classPK, start, end, null);
	}

	/**
	 * Returns an ordered range of all the ratings entries where classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RatingsEntryModelImpl</code>.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param start the lower bound of the range of ratings entries
	 * @param end the upper bound of the range of ratings entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching ratings entries
	 */
	@Override
	public List<RatingsEntry> findByC_C(
		long classNameId, long classPK, int start, int end,
		OrderByComparator<RatingsEntry> orderByComparator) {

		return findByC_C(
			classNameId, classPK, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the ratings entries where classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RatingsEntryModelImpl</code>.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param start the lower bound of the range of ratings entries
	 * @param end the upper bound of the range of ratings entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching ratings entries
	 */
	@Override
	public List<RatingsEntry> findByC_C(
		long classNameId, long classPK, int start, int end,
		OrderByComparator<RatingsEntry> orderByComparator,
		boolean useFinderCache) {

		boolean productionMode = CTPersistenceHelperUtil.isProductionMode(
			RatingsEntry.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache && productionMode) {
				finderPath = _finderPathWithoutPaginationFindByC_C;
				finderArgs = new Object[] {classNameId, classPK};
			}
		}
		else if (useFinderCache && productionMode) {
			finderPath = _finderPathWithPaginationFindByC_C;
			finderArgs = new Object[] {
				classNameId, classPK, start, end, orderByComparator
			};
		}

		List<RatingsEntry> list = null;

		if (useFinderCache && productionMode) {
			list = (List<RatingsEntry>)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (RatingsEntry ratingsEntry : list) {
					if ((classNameId != ratingsEntry.getClassNameId()) ||
						(classPK != ratingsEntry.getClassPK())) {

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

			sb.append(_SQL_SELECT_RATINGSENTRY_WHERE);

			sb.append(_FINDER_COLUMN_C_C_CLASSNAMEID_2);

			sb.append(_FINDER_COLUMN_C_C_CLASSPK_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(RatingsEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(classNameId);

				queryPos.add(classPK);

				list = (List<RatingsEntry>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache && productionMode) {
					FinderCacheUtil.putResult(finderPath, finderArgs, list);
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
	 * Returns the first ratings entry in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ratings entry
	 * @throws NoSuchEntryException if a matching ratings entry could not be found
	 */
	@Override
	public RatingsEntry findByC_C_First(
			long classNameId, long classPK,
			OrderByComparator<RatingsEntry> orderByComparator)
		throws NoSuchEntryException {

		RatingsEntry ratingsEntry = fetchByC_C_First(
			classNameId, classPK, orderByComparator);

		if (ratingsEntry != null) {
			return ratingsEntry;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("classNameId=");
		sb.append(classNameId);

		sb.append(", classPK=");
		sb.append(classPK);

		sb.append("}");

		throw new NoSuchEntryException(sb.toString());
	}

	/**
	 * Returns the first ratings entry in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ratings entry, or <code>null</code> if a matching ratings entry could not be found
	 */
	@Override
	public RatingsEntry fetchByC_C_First(
		long classNameId, long classPK,
		OrderByComparator<RatingsEntry> orderByComparator) {

		List<RatingsEntry> list = findByC_C(
			classNameId, classPK, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last ratings entry in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ratings entry
	 * @throws NoSuchEntryException if a matching ratings entry could not be found
	 */
	@Override
	public RatingsEntry findByC_C_Last(
			long classNameId, long classPK,
			OrderByComparator<RatingsEntry> orderByComparator)
		throws NoSuchEntryException {

		RatingsEntry ratingsEntry = fetchByC_C_Last(
			classNameId, classPK, orderByComparator);

		if (ratingsEntry != null) {
			return ratingsEntry;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("classNameId=");
		sb.append(classNameId);

		sb.append(", classPK=");
		sb.append(classPK);

		sb.append("}");

		throw new NoSuchEntryException(sb.toString());
	}

	/**
	 * Returns the last ratings entry in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ratings entry, or <code>null</code> if a matching ratings entry could not be found
	 */
	@Override
	public RatingsEntry fetchByC_C_Last(
		long classNameId, long classPK,
		OrderByComparator<RatingsEntry> orderByComparator) {

		int count = countByC_C(classNameId, classPK);

		if (count == 0) {
			return null;
		}

		List<RatingsEntry> list = findByC_C(
			classNameId, classPK, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the ratings entries before and after the current ratings entry in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param entryId the primary key of the current ratings entry
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next ratings entry
	 * @throws NoSuchEntryException if a ratings entry with the primary key could not be found
	 */
	@Override
	public RatingsEntry[] findByC_C_PrevAndNext(
			long entryId, long classNameId, long classPK,
			OrderByComparator<RatingsEntry> orderByComparator)
		throws NoSuchEntryException {

		RatingsEntry ratingsEntry = findByPrimaryKey(entryId);

		Session session = null;

		try {
			session = openSession();

			RatingsEntry[] array = new RatingsEntryImpl[3];

			array[0] = getByC_C_PrevAndNext(
				session, ratingsEntry, classNameId, classPK, orderByComparator,
				true);

			array[1] = ratingsEntry;

			array[2] = getByC_C_PrevAndNext(
				session, ratingsEntry, classNameId, classPK, orderByComparator,
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

	protected RatingsEntry getByC_C_PrevAndNext(
		Session session, RatingsEntry ratingsEntry, long classNameId,
		long classPK, OrderByComparator<RatingsEntry> orderByComparator,
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

		sb.append(_SQL_SELECT_RATINGSENTRY_WHERE);

		sb.append(_FINDER_COLUMN_C_C_CLASSNAMEID_2);

		sb.append(_FINDER_COLUMN_C_C_CLASSPK_2);

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
			sb.append(RatingsEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(classNameId);

		queryPos.add(classPK);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(ratingsEntry)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<RatingsEntry> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the ratings entries where classNameId = &#63; and classPK = &#63; from the database.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 */
	@Override
	public void removeByC_C(long classNameId, long classPK) {
		for (RatingsEntry ratingsEntry :
				findByC_C(
					classNameId, classPK, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(ratingsEntry);
		}
	}

	/**
	 * Returns the number of ratings entries where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the number of matching ratings entries
	 */
	@Override
	public int countByC_C(long classNameId, long classPK) {
		boolean productionMode = CTPersistenceHelperUtil.isProductionMode(
			RatingsEntry.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByC_C;

			finderArgs = new Object[] {classNameId, classPK};

			count = (Long)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_RATINGSENTRY_WHERE);

			sb.append(_FINDER_COLUMN_C_C_CLASSNAMEID_2);

			sb.append(_FINDER_COLUMN_C_C_CLASSPK_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(classNameId);

				queryPos.add(classPK);

				count = (Long)query.uniqueResult();

				if (productionMode) {
					FinderCacheUtil.putResult(finderPath, finderArgs, count);
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

	private static final String _FINDER_COLUMN_C_C_CLASSNAMEID_2 =
		"ratingsEntry.classNameId = ? AND ";

	private static final String _FINDER_COLUMN_C_C_CLASSPK_2 =
		"ratingsEntry.classPK = ?";

	private FinderPath _finderPathWithPaginationFindByU_C_C;
	private FinderPath _finderPathWithoutPaginationFindByU_C_C;
	private FinderPath _finderPathFetchByU_C_C;
	private FinderPath _finderPathCountByU_C_C;
	private FinderPath _finderPathWithPaginationCountByU_C_C;

	/**
	 * Returns all the ratings entries where userId = &#63; and classNameId = &#63; and classPK = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RatingsEntryModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param classNameId the class name ID
	 * @param classPKs the class pks
	 * @return the matching ratings entries
	 */
	@Override
	public List<RatingsEntry> findByU_C_C(
		long userId, long classNameId, long[] classPKs) {

		return findByU_C_C(
			userId, classNameId, classPKs, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the ratings entries where userId = &#63; and classNameId = &#63; and classPK = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RatingsEntryModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param classNameId the class name ID
	 * @param classPKs the class pks
	 * @param start the lower bound of the range of ratings entries
	 * @param end the upper bound of the range of ratings entries (not inclusive)
	 * @return the range of matching ratings entries
	 */
	@Override
	public List<RatingsEntry> findByU_C_C(
		long userId, long classNameId, long[] classPKs, int start, int end) {

		return findByU_C_C(userId, classNameId, classPKs, start, end, null);
	}

	/**
	 * Returns an ordered range of all the ratings entries where userId = &#63; and classNameId = &#63; and classPK = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RatingsEntryModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param classNameId the class name ID
	 * @param classPKs the class pks
	 * @param start the lower bound of the range of ratings entries
	 * @param end the upper bound of the range of ratings entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching ratings entries
	 */
	@Override
	public List<RatingsEntry> findByU_C_C(
		long userId, long classNameId, long[] classPKs, int start, int end,
		OrderByComparator<RatingsEntry> orderByComparator) {

		return findByU_C_C(
			userId, classNameId, classPKs, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the ratings entries where userId = &#63; and classNameId = &#63; and classPK = &#63;, optionally using the finder cache.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RatingsEntryModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param start the lower bound of the range of ratings entries
	 * @param end the upper bound of the range of ratings entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching ratings entries
	 */
	@Override
	public List<RatingsEntry> findByU_C_C(
		long userId, long classNameId, long[] classPKs, int start, int end,
		OrderByComparator<RatingsEntry> orderByComparator,
		boolean useFinderCache) {

		if (classPKs == null) {
			classPKs = new long[0];
		}
		else if (classPKs.length > 1) {
			classPKs = ArrayUtil.sortedUnique(classPKs);
		}

		if (classPKs.length == 1) {
			RatingsEntry ratingsEntry = fetchByU_C_C(
				userId, classNameId, classPKs[0]);

			if (ratingsEntry == null) {
				return Collections.emptyList();
			}
			else {
				List<RatingsEntry> list = new ArrayList<RatingsEntry>(1);

				list.add(ratingsEntry);

				return list;
			}
		}

		boolean productionMode = CTPersistenceHelperUtil.isProductionMode(
			RatingsEntry.class);

		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache && productionMode) {
				finderArgs = new Object[] {
					userId, classNameId, StringUtil.merge(classPKs)
				};
			}
		}
		else if (useFinderCache && productionMode) {
			finderArgs = new Object[] {
				userId, classNameId, StringUtil.merge(classPKs), start, end,
				orderByComparator
			};
		}

		List<RatingsEntry> list = null;

		if (useFinderCache && productionMode) {
			list = (List<RatingsEntry>)FinderCacheUtil.getResult(
				_finderPathWithPaginationFindByU_C_C, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (RatingsEntry ratingsEntry : list) {
					if ((userId != ratingsEntry.getUserId()) ||
						(classNameId != ratingsEntry.getClassNameId()) ||
						!ArrayUtil.contains(
							classPKs, ratingsEntry.getClassPK())) {

						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler sb = new StringBundler();

			sb.append(_SQL_SELECT_RATINGSENTRY_WHERE);

			sb.append(_FINDER_COLUMN_U_C_C_USERID_2);

			sb.append(_FINDER_COLUMN_U_C_C_CLASSNAMEID_2);

			if (classPKs.length > 0) {
				sb.append("(");

				sb.append(_FINDER_COLUMN_U_C_C_CLASSPK_7);

				sb.append(StringUtil.merge(classPKs));

				sb.append(")");

				sb.append(")");
			}

			sb.setStringAt(
				removeConjunction(sb.stringAt(sb.index() - 1)), sb.index() - 1);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(RatingsEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(userId);

				queryPos.add(classNameId);

				list = (List<RatingsEntry>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache && productionMode) {
					FinderCacheUtil.putResult(
						_finderPathWithPaginationFindByU_C_C, finderArgs, list);
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
	 * Returns the ratings entry where userId = &#63; and classNameId = &#63; and classPK = &#63; or throws a <code>NoSuchEntryException</code> if it could not be found.
	 *
	 * @param userId the user ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the matching ratings entry
	 * @throws NoSuchEntryException if a matching ratings entry could not be found
	 */
	@Override
	public RatingsEntry findByU_C_C(long userId, long classNameId, long classPK)
		throws NoSuchEntryException {

		RatingsEntry ratingsEntry = fetchByU_C_C(userId, classNameId, classPK);

		if (ratingsEntry == null) {
			StringBundler sb = new StringBundler(8);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("userId=");
			sb.append(userId);

			sb.append(", classNameId=");
			sb.append(classNameId);

			sb.append(", classPK=");
			sb.append(classPK);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchEntryException(sb.toString());
		}

		return ratingsEntry;
	}

	/**
	 * Returns the ratings entry where userId = &#63; and classNameId = &#63; and classPK = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param userId the user ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the matching ratings entry, or <code>null</code> if a matching ratings entry could not be found
	 */
	@Override
	public RatingsEntry fetchByU_C_C(
		long userId, long classNameId, long classPK) {

		return fetchByU_C_C(userId, classNameId, classPK, true);
	}

	/**
	 * Returns the ratings entry where userId = &#63; and classNameId = &#63; and classPK = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param userId the user ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching ratings entry, or <code>null</code> if a matching ratings entry could not be found
	 */
	@Override
	public RatingsEntry fetchByU_C_C(
		long userId, long classNameId, long classPK, boolean useFinderCache) {

		boolean productionMode = CTPersistenceHelperUtil.isProductionMode(
			RatingsEntry.class);

		Object[] finderArgs = null;

		if (useFinderCache && productionMode) {
			finderArgs = new Object[] {userId, classNameId, classPK};
		}

		Object result = null;

		if (useFinderCache && productionMode) {
			result = FinderCacheUtil.getResult(
				_finderPathFetchByU_C_C, finderArgs, this);
		}

		if (result instanceof RatingsEntry) {
			RatingsEntry ratingsEntry = (RatingsEntry)result;

			if ((userId != ratingsEntry.getUserId()) ||
				(classNameId != ratingsEntry.getClassNameId()) ||
				(classPK != ratingsEntry.getClassPK())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(5);

			sb.append(_SQL_SELECT_RATINGSENTRY_WHERE);

			sb.append(_FINDER_COLUMN_U_C_C_USERID_2);

			sb.append(_FINDER_COLUMN_U_C_C_CLASSNAMEID_2);

			sb.append(_FINDER_COLUMN_U_C_C_CLASSPK_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(userId);

				queryPos.add(classNameId);

				queryPos.add(classPK);

				List<RatingsEntry> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache && productionMode) {
						FinderCacheUtil.putResult(
							_finderPathFetchByU_C_C, finderArgs, list);
					}
				}
				else {
					RatingsEntry ratingsEntry = list.get(0);

					result = ratingsEntry;

					cacheResult(ratingsEntry);
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
			return (RatingsEntry)result;
		}
	}

	/**
	 * Removes the ratings entry where userId = &#63; and classNameId = &#63; and classPK = &#63; from the database.
	 *
	 * @param userId the user ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the ratings entry that was removed
	 */
	@Override
	public RatingsEntry removeByU_C_C(
			long userId, long classNameId, long classPK)
		throws NoSuchEntryException {

		RatingsEntry ratingsEntry = findByU_C_C(userId, classNameId, classPK);

		return remove(ratingsEntry);
	}

	/**
	 * Returns the number of ratings entries where userId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param userId the user ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the number of matching ratings entries
	 */
	@Override
	public int countByU_C_C(long userId, long classNameId, long classPK) {
		boolean productionMode = CTPersistenceHelperUtil.isProductionMode(
			RatingsEntry.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByU_C_C;

			finderArgs = new Object[] {userId, classNameId, classPK};

			count = (Long)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_COUNT_RATINGSENTRY_WHERE);

			sb.append(_FINDER_COLUMN_U_C_C_USERID_2);

			sb.append(_FINDER_COLUMN_U_C_C_CLASSNAMEID_2);

			sb.append(_FINDER_COLUMN_U_C_C_CLASSPK_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(userId);

				queryPos.add(classNameId);

				queryPos.add(classPK);

				count = (Long)query.uniqueResult();

				if (productionMode) {
					FinderCacheUtil.putResult(finderPath, finderArgs, count);
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

	/**
	 * Returns the number of ratings entries where userId = &#63; and classNameId = &#63; and classPK = any &#63;.
	 *
	 * @param userId the user ID
	 * @param classNameId the class name ID
	 * @param classPKs the class pks
	 * @return the number of matching ratings entries
	 */
	@Override
	public int countByU_C_C(long userId, long classNameId, long[] classPKs) {
		if (classPKs == null) {
			classPKs = new long[0];
		}
		else if (classPKs.length > 1) {
			classPKs = ArrayUtil.sortedUnique(classPKs);
		}

		boolean productionMode = CTPersistenceHelperUtil.isProductionMode(
			RatingsEntry.class);

		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderArgs = new Object[] {
				userId, classNameId, StringUtil.merge(classPKs)
			};

			count = (Long)FinderCacheUtil.getResult(
				_finderPathWithPaginationCountByU_C_C, finderArgs, this);
		}

		if (count == null) {
			StringBundler sb = new StringBundler();

			sb.append(_SQL_COUNT_RATINGSENTRY_WHERE);

			sb.append(_FINDER_COLUMN_U_C_C_USERID_2);

			sb.append(_FINDER_COLUMN_U_C_C_CLASSNAMEID_2);

			if (classPKs.length > 0) {
				sb.append("(");

				sb.append(_FINDER_COLUMN_U_C_C_CLASSPK_7);

				sb.append(StringUtil.merge(classPKs));

				sb.append(")");

				sb.append(")");
			}

			sb.setStringAt(
				removeConjunction(sb.stringAt(sb.index() - 1)), sb.index() - 1);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(userId);

				queryPos.add(classNameId);

				count = (Long)query.uniqueResult();

				if (productionMode) {
					FinderCacheUtil.putResult(
						_finderPathWithPaginationCountByU_C_C, finderArgs,
						count);
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

	private static final String _FINDER_COLUMN_U_C_C_USERID_2 =
		"ratingsEntry.userId = ? AND ";

	private static final String _FINDER_COLUMN_U_C_C_CLASSNAMEID_2 =
		"ratingsEntry.classNameId = ? AND ";

	private static final String _FINDER_COLUMN_U_C_C_CLASSPK_2 =
		"ratingsEntry.classPK = ?";

	private static final String _FINDER_COLUMN_U_C_C_CLASSPK_7 =
		"ratingsEntry.classPK IN (";

	private FinderPath _finderPathWithPaginationFindByC_C_S;
	private FinderPath _finderPathWithoutPaginationFindByC_C_S;
	private FinderPath _finderPathCountByC_C_S;

	/**
	 * Returns all the ratings entries where classNameId = &#63; and classPK = &#63; and score = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param score the score
	 * @return the matching ratings entries
	 */
	@Override
	public List<RatingsEntry> findByC_C_S(
		long classNameId, long classPK, double score) {

		return findByC_C_S(
			classNameId, classPK, score, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the ratings entries where classNameId = &#63; and classPK = &#63; and score = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RatingsEntryModelImpl</code>.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param score the score
	 * @param start the lower bound of the range of ratings entries
	 * @param end the upper bound of the range of ratings entries (not inclusive)
	 * @return the range of matching ratings entries
	 */
	@Override
	public List<RatingsEntry> findByC_C_S(
		long classNameId, long classPK, double score, int start, int end) {

		return findByC_C_S(classNameId, classPK, score, start, end, null);
	}

	/**
	 * Returns an ordered range of all the ratings entries where classNameId = &#63; and classPK = &#63; and score = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RatingsEntryModelImpl</code>.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param score the score
	 * @param start the lower bound of the range of ratings entries
	 * @param end the upper bound of the range of ratings entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching ratings entries
	 */
	@Override
	public List<RatingsEntry> findByC_C_S(
		long classNameId, long classPK, double score, int start, int end,
		OrderByComparator<RatingsEntry> orderByComparator) {

		return findByC_C_S(
			classNameId, classPK, score, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the ratings entries where classNameId = &#63; and classPK = &#63; and score = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RatingsEntryModelImpl</code>.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param score the score
	 * @param start the lower bound of the range of ratings entries
	 * @param end the upper bound of the range of ratings entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching ratings entries
	 */
	@Override
	public List<RatingsEntry> findByC_C_S(
		long classNameId, long classPK, double score, int start, int end,
		OrderByComparator<RatingsEntry> orderByComparator,
		boolean useFinderCache) {

		boolean productionMode = CTPersistenceHelperUtil.isProductionMode(
			RatingsEntry.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache && productionMode) {
				finderPath = _finderPathWithoutPaginationFindByC_C_S;
				finderArgs = new Object[] {classNameId, classPK, score};
			}
		}
		else if (useFinderCache && productionMode) {
			finderPath = _finderPathWithPaginationFindByC_C_S;
			finderArgs = new Object[] {
				classNameId, classPK, score, start, end, orderByComparator
			};
		}

		List<RatingsEntry> list = null;

		if (useFinderCache && productionMode) {
			list = (List<RatingsEntry>)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (RatingsEntry ratingsEntry : list) {
					if ((classNameId != ratingsEntry.getClassNameId()) ||
						(classPK != ratingsEntry.getClassPK()) ||
						(score != ratingsEntry.getScore())) {

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

			sb.append(_SQL_SELECT_RATINGSENTRY_WHERE);

			sb.append(_FINDER_COLUMN_C_C_S_CLASSNAMEID_2);

			sb.append(_FINDER_COLUMN_C_C_S_CLASSPK_2);

			sb.append(_FINDER_COLUMN_C_C_S_SCORE_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(RatingsEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(classNameId);

				queryPos.add(classPK);

				queryPos.add(score);

				list = (List<RatingsEntry>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache && productionMode) {
					FinderCacheUtil.putResult(finderPath, finderArgs, list);
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
	 * Returns the first ratings entry in the ordered set where classNameId = &#63; and classPK = &#63; and score = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param score the score
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ratings entry
	 * @throws NoSuchEntryException if a matching ratings entry could not be found
	 */
	@Override
	public RatingsEntry findByC_C_S_First(
			long classNameId, long classPK, double score,
			OrderByComparator<RatingsEntry> orderByComparator)
		throws NoSuchEntryException {

		RatingsEntry ratingsEntry = fetchByC_C_S_First(
			classNameId, classPK, score, orderByComparator);

		if (ratingsEntry != null) {
			return ratingsEntry;
		}

		StringBundler sb = new StringBundler(8);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("classNameId=");
		sb.append(classNameId);

		sb.append(", classPK=");
		sb.append(classPK);

		sb.append(", score=");
		sb.append(score);

		sb.append("}");

		throw new NoSuchEntryException(sb.toString());
	}

	/**
	 * Returns the first ratings entry in the ordered set where classNameId = &#63; and classPK = &#63; and score = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param score the score
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ratings entry, or <code>null</code> if a matching ratings entry could not be found
	 */
	@Override
	public RatingsEntry fetchByC_C_S_First(
		long classNameId, long classPK, double score,
		OrderByComparator<RatingsEntry> orderByComparator) {

		List<RatingsEntry> list = findByC_C_S(
			classNameId, classPK, score, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last ratings entry in the ordered set where classNameId = &#63; and classPK = &#63; and score = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param score the score
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ratings entry
	 * @throws NoSuchEntryException if a matching ratings entry could not be found
	 */
	@Override
	public RatingsEntry findByC_C_S_Last(
			long classNameId, long classPK, double score,
			OrderByComparator<RatingsEntry> orderByComparator)
		throws NoSuchEntryException {

		RatingsEntry ratingsEntry = fetchByC_C_S_Last(
			classNameId, classPK, score, orderByComparator);

		if (ratingsEntry != null) {
			return ratingsEntry;
		}

		StringBundler sb = new StringBundler(8);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("classNameId=");
		sb.append(classNameId);

		sb.append(", classPK=");
		sb.append(classPK);

		sb.append(", score=");
		sb.append(score);

		sb.append("}");

		throw new NoSuchEntryException(sb.toString());
	}

	/**
	 * Returns the last ratings entry in the ordered set where classNameId = &#63; and classPK = &#63; and score = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param score the score
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ratings entry, or <code>null</code> if a matching ratings entry could not be found
	 */
	@Override
	public RatingsEntry fetchByC_C_S_Last(
		long classNameId, long classPK, double score,
		OrderByComparator<RatingsEntry> orderByComparator) {

		int count = countByC_C_S(classNameId, classPK, score);

		if (count == 0) {
			return null;
		}

		List<RatingsEntry> list = findByC_C_S(
			classNameId, classPK, score, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the ratings entries before and after the current ratings entry in the ordered set where classNameId = &#63; and classPK = &#63; and score = &#63;.
	 *
	 * @param entryId the primary key of the current ratings entry
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param score the score
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next ratings entry
	 * @throws NoSuchEntryException if a ratings entry with the primary key could not be found
	 */
	@Override
	public RatingsEntry[] findByC_C_S_PrevAndNext(
			long entryId, long classNameId, long classPK, double score,
			OrderByComparator<RatingsEntry> orderByComparator)
		throws NoSuchEntryException {

		RatingsEntry ratingsEntry = findByPrimaryKey(entryId);

		Session session = null;

		try {
			session = openSession();

			RatingsEntry[] array = new RatingsEntryImpl[3];

			array[0] = getByC_C_S_PrevAndNext(
				session, ratingsEntry, classNameId, classPK, score,
				orderByComparator, true);

			array[1] = ratingsEntry;

			array[2] = getByC_C_S_PrevAndNext(
				session, ratingsEntry, classNameId, classPK, score,
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

	protected RatingsEntry getByC_C_S_PrevAndNext(
		Session session, RatingsEntry ratingsEntry, long classNameId,
		long classPK, double score,
		OrderByComparator<RatingsEntry> orderByComparator, boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				6 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(5);
		}

		sb.append(_SQL_SELECT_RATINGSENTRY_WHERE);

		sb.append(_FINDER_COLUMN_C_C_S_CLASSNAMEID_2);

		sb.append(_FINDER_COLUMN_C_C_S_CLASSPK_2);

		sb.append(_FINDER_COLUMN_C_C_S_SCORE_2);

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
			sb.append(RatingsEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(classNameId);

		queryPos.add(classPK);

		queryPos.add(score);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(ratingsEntry)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<RatingsEntry> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the ratings entries where classNameId = &#63; and classPK = &#63; and score = &#63; from the database.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param score the score
	 */
	@Override
	public void removeByC_C_S(long classNameId, long classPK, double score) {
		for (RatingsEntry ratingsEntry :
				findByC_C_S(
					classNameId, classPK, score, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			remove(ratingsEntry);
		}
	}

	/**
	 * Returns the number of ratings entries where classNameId = &#63; and classPK = &#63; and score = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param score the score
	 * @return the number of matching ratings entries
	 */
	@Override
	public int countByC_C_S(long classNameId, long classPK, double score) {
		boolean productionMode = CTPersistenceHelperUtil.isProductionMode(
			RatingsEntry.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByC_C_S;

			finderArgs = new Object[] {classNameId, classPK, score};

			count = (Long)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_COUNT_RATINGSENTRY_WHERE);

			sb.append(_FINDER_COLUMN_C_C_S_CLASSNAMEID_2);

			sb.append(_FINDER_COLUMN_C_C_S_CLASSPK_2);

			sb.append(_FINDER_COLUMN_C_C_S_SCORE_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(classNameId);

				queryPos.add(classPK);

				queryPos.add(score);

				count = (Long)query.uniqueResult();

				if (productionMode) {
					FinderCacheUtil.putResult(finderPath, finderArgs, count);
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

	private static final String _FINDER_COLUMN_C_C_S_CLASSNAMEID_2 =
		"ratingsEntry.classNameId = ? AND ";

	private static final String _FINDER_COLUMN_C_C_S_CLASSPK_2 =
		"ratingsEntry.classPK = ? AND ";

	private static final String _FINDER_COLUMN_C_C_S_SCORE_2 =
		"ratingsEntry.score = ?";

	public RatingsEntryPersistenceImpl() {
		Map<String, String> dbColumnNames = new HashMap<String, String>();

		dbColumnNames.put("uuid", "uuid_");

		setDBColumnNames(dbColumnNames);

		setModelClass(RatingsEntry.class);

		setModelImplClass(RatingsEntryImpl.class);
		setModelPKClass(long.class);

		setTable(RatingsEntryTable.INSTANCE);
	}

	/**
	 * Caches the ratings entry in the entity cache if it is enabled.
	 *
	 * @param ratingsEntry the ratings entry
	 */
	@Override
	public void cacheResult(RatingsEntry ratingsEntry) {
		if (ratingsEntry.getCtCollectionId() != 0) {
			ratingsEntry.resetOriginalValues();

			return;
		}

		EntityCacheUtil.putResult(
			RatingsEntryImpl.class, ratingsEntry.getPrimaryKey(), ratingsEntry);

		FinderCacheUtil.putResult(
			_finderPathFetchByU_C_C,
			new Object[] {
				ratingsEntry.getUserId(), ratingsEntry.getClassNameId(),
				ratingsEntry.getClassPK()
			},
			ratingsEntry);

		ratingsEntry.resetOriginalValues();
	}

	/**
	 * Caches the ratings entries in the entity cache if it is enabled.
	 *
	 * @param ratingsEntries the ratings entries
	 */
	@Override
	public void cacheResult(List<RatingsEntry> ratingsEntries) {
		for (RatingsEntry ratingsEntry : ratingsEntries) {
			if (ratingsEntry.getCtCollectionId() != 0) {
				ratingsEntry.resetOriginalValues();

				continue;
			}

			if (EntityCacheUtil.getResult(
					RatingsEntryImpl.class, ratingsEntry.getPrimaryKey()) ==
						null) {

				cacheResult(ratingsEntry);
			}
			else {
				ratingsEntry.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all ratings entries.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>com.liferay.portal.kernel.dao.orm.FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		EntityCacheUtil.clearCache(RatingsEntryImpl.class);

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the ratings entry.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>com.liferay.portal.kernel.dao.orm.FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(RatingsEntry ratingsEntry) {
		EntityCacheUtil.removeResult(
			RatingsEntryImpl.class, ratingsEntry.getPrimaryKey());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache((RatingsEntryModelImpl)ratingsEntry, true);
	}

	@Override
	public void clearCache(List<RatingsEntry> ratingsEntries) {
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (RatingsEntry ratingsEntry : ratingsEntries) {
			EntityCacheUtil.removeResult(
				RatingsEntryImpl.class, ratingsEntry.getPrimaryKey());

			clearUniqueFindersCache((RatingsEntryModelImpl)ratingsEntry, true);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (Serializable primaryKey : primaryKeys) {
			EntityCacheUtil.removeResult(RatingsEntryImpl.class, primaryKey);
		}
	}

	protected void cacheUniqueFindersCache(
		RatingsEntryModelImpl ratingsEntryModelImpl) {

		Object[] args = new Object[] {
			ratingsEntryModelImpl.getUserId(),
			ratingsEntryModelImpl.getClassNameId(),
			ratingsEntryModelImpl.getClassPK()
		};

		FinderCacheUtil.putResult(
			_finderPathCountByU_C_C, args, Long.valueOf(1), false);
		FinderCacheUtil.putResult(
			_finderPathFetchByU_C_C, args, ratingsEntryModelImpl, false);
	}

	protected void clearUniqueFindersCache(
		RatingsEntryModelImpl ratingsEntryModelImpl, boolean clearCurrent) {

		if (clearCurrent) {
			Object[] args = new Object[] {
				ratingsEntryModelImpl.getUserId(),
				ratingsEntryModelImpl.getClassNameId(),
				ratingsEntryModelImpl.getClassPK()
			};

			FinderCacheUtil.removeResult(_finderPathCountByU_C_C, args);
			FinderCacheUtil.removeResult(_finderPathFetchByU_C_C, args);
		}

		if ((ratingsEntryModelImpl.getColumnBitmask() &
			 _finderPathFetchByU_C_C.getColumnBitmask()) != 0) {

			Object[] args = new Object[] {
				ratingsEntryModelImpl.getOriginalUserId(),
				ratingsEntryModelImpl.getOriginalClassNameId(),
				ratingsEntryModelImpl.getOriginalClassPK()
			};

			FinderCacheUtil.removeResult(_finderPathCountByU_C_C, args);
			FinderCacheUtil.removeResult(_finderPathFetchByU_C_C, args);
		}
	}

	/**
	 * Creates a new ratings entry with the primary key. Does not add the ratings entry to the database.
	 *
	 * @param entryId the primary key for the new ratings entry
	 * @return the new ratings entry
	 */
	@Override
	public RatingsEntry create(long entryId) {
		RatingsEntry ratingsEntry = new RatingsEntryImpl();

		ratingsEntry.setNew(true);
		ratingsEntry.setPrimaryKey(entryId);

		String uuid = PortalUUIDUtil.generate();

		ratingsEntry.setUuid(uuid);

		ratingsEntry.setCompanyId(CompanyThreadLocal.getCompanyId());

		return ratingsEntry;
	}

	/**
	 * Removes the ratings entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param entryId the primary key of the ratings entry
	 * @return the ratings entry that was removed
	 * @throws NoSuchEntryException if a ratings entry with the primary key could not be found
	 */
	@Override
	public RatingsEntry remove(long entryId) throws NoSuchEntryException {
		return remove((Serializable)entryId);
	}

	/**
	 * Removes the ratings entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the ratings entry
	 * @return the ratings entry that was removed
	 * @throws NoSuchEntryException if a ratings entry with the primary key could not be found
	 */
	@Override
	public RatingsEntry remove(Serializable primaryKey)
		throws NoSuchEntryException {

		Session session = null;

		try {
			session = openSession();

			RatingsEntry ratingsEntry = (RatingsEntry)session.get(
				RatingsEntryImpl.class, primaryKey);

			if (ratingsEntry == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchEntryException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(ratingsEntry);
		}
		catch (NoSuchEntryException noSuchEntityException) {
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
	protected RatingsEntry removeImpl(RatingsEntry ratingsEntry) {
		if (!CTPersistenceHelperUtil.isRemove(ratingsEntry)) {
			return ratingsEntry;
		}

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(ratingsEntry)) {
				ratingsEntry = (RatingsEntry)session.get(
					RatingsEntryImpl.class, ratingsEntry.getPrimaryKeyObj());
			}

			if (ratingsEntry != null) {
				session.delete(ratingsEntry);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (ratingsEntry != null) {
			clearCache(ratingsEntry);
		}

		return ratingsEntry;
	}

	@Override
	public RatingsEntry updateImpl(RatingsEntry ratingsEntry) {
		boolean isNew = ratingsEntry.isNew();

		if (!(ratingsEntry instanceof RatingsEntryModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(ratingsEntry.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					ratingsEntry);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in ratingsEntry proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom RatingsEntry implementation " +
					ratingsEntry.getClass());
		}

		RatingsEntryModelImpl ratingsEntryModelImpl =
			(RatingsEntryModelImpl)ratingsEntry;

		if (Validator.isNull(ratingsEntry.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			ratingsEntry.setUuid(uuid);
		}

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Date now = new Date();

		if (isNew && (ratingsEntry.getCreateDate() == null)) {
			if (serviceContext == null) {
				ratingsEntry.setCreateDate(now);
			}
			else {
				ratingsEntry.setCreateDate(serviceContext.getCreateDate(now));
			}
		}

		if (!ratingsEntryModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				ratingsEntry.setModifiedDate(now);
			}
			else {
				ratingsEntry.setModifiedDate(
					serviceContext.getModifiedDate(now));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (CTPersistenceHelperUtil.isInsert(ratingsEntry)) {
				if (!isNew) {
					session.evict(
						RatingsEntryImpl.class,
						ratingsEntry.getPrimaryKeyObj());
				}

				session.save(ratingsEntry);

				ratingsEntry.setNew(false);
			}
			else {
				ratingsEntry = (RatingsEntry)session.merge(ratingsEntry);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (ratingsEntry.getCtCollectionId() != 0) {
			ratingsEntry.resetOriginalValues();

			return ratingsEntry;
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (isNew) {
			Object[] args = new Object[] {ratingsEntryModelImpl.getUuid()};

			FinderCacheUtil.removeResult(_finderPathCountByUuid, args);
			FinderCacheUtil.removeResult(
				_finderPathWithoutPaginationFindByUuid, args);

			args = new Object[] {
				ratingsEntryModelImpl.getUuid(),
				ratingsEntryModelImpl.getCompanyId()
			};

			FinderCacheUtil.removeResult(_finderPathCountByUuid_C, args);
			FinderCacheUtil.removeResult(
				_finderPathWithoutPaginationFindByUuid_C, args);

			args = new Object[] {
				ratingsEntryModelImpl.getClassNameId(),
				ratingsEntryModelImpl.getClassPK()
			};

			FinderCacheUtil.removeResult(_finderPathCountByC_C, args);
			FinderCacheUtil.removeResult(
				_finderPathWithoutPaginationFindByC_C, args);

			args = new Object[] {
				ratingsEntryModelImpl.getUserId(),
				ratingsEntryModelImpl.getClassNameId(),
				ratingsEntryModelImpl.getClassPK()
			};

			FinderCacheUtil.removeResult(_finderPathCountByU_C_C, args);
			FinderCacheUtil.removeResult(
				_finderPathWithoutPaginationFindByU_C_C, args);

			args = new Object[] {
				ratingsEntryModelImpl.getClassNameId(),
				ratingsEntryModelImpl.getClassPK(),
				ratingsEntryModelImpl.getScore()
			};

			FinderCacheUtil.removeResult(_finderPathCountByC_C_S, args);
			FinderCacheUtil.removeResult(
				_finderPathWithoutPaginationFindByC_C_S, args);

			FinderCacheUtil.removeResult(
				_finderPathCountAll, FINDER_ARGS_EMPTY);
			FinderCacheUtil.removeResult(
				_finderPathWithoutPaginationFindAll, FINDER_ARGS_EMPTY);
		}
		else {
			if ((ratingsEntryModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByUuid.getColumnBitmask()) !=
					 0) {

				Object[] args = new Object[] {
					ratingsEntryModelImpl.getOriginalUuid()
				};

				FinderCacheUtil.removeResult(_finderPathCountByUuid, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByUuid, args);

				args = new Object[] {ratingsEntryModelImpl.getUuid()};

				FinderCacheUtil.removeResult(_finderPathCountByUuid, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByUuid, args);
			}

			if ((ratingsEntryModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByUuid_C.getColumnBitmask()) !=
					 0) {

				Object[] args = new Object[] {
					ratingsEntryModelImpl.getOriginalUuid(),
					ratingsEntryModelImpl.getOriginalCompanyId()
				};

				FinderCacheUtil.removeResult(_finderPathCountByUuid_C, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByUuid_C, args);

				args = new Object[] {
					ratingsEntryModelImpl.getUuid(),
					ratingsEntryModelImpl.getCompanyId()
				};

				FinderCacheUtil.removeResult(_finderPathCountByUuid_C, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByUuid_C, args);
			}

			if ((ratingsEntryModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByC_C.getColumnBitmask()) !=
					 0) {

				Object[] args = new Object[] {
					ratingsEntryModelImpl.getOriginalClassNameId(),
					ratingsEntryModelImpl.getOriginalClassPK()
				};

				FinderCacheUtil.removeResult(_finderPathCountByC_C, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByC_C, args);

				args = new Object[] {
					ratingsEntryModelImpl.getClassNameId(),
					ratingsEntryModelImpl.getClassPK()
				};

				FinderCacheUtil.removeResult(_finderPathCountByC_C, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByC_C, args);
			}

			if ((ratingsEntryModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByU_C_C.getColumnBitmask()) !=
					 0) {

				Object[] args = new Object[] {
					ratingsEntryModelImpl.getOriginalUserId(),
					ratingsEntryModelImpl.getOriginalClassNameId(),
					ratingsEntryModelImpl.getOriginalClassPK()
				};

				FinderCacheUtil.removeResult(_finderPathCountByU_C_C, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByU_C_C, args);

				args = new Object[] {
					ratingsEntryModelImpl.getUserId(),
					ratingsEntryModelImpl.getClassNameId(),
					ratingsEntryModelImpl.getClassPK()
				};

				FinderCacheUtil.removeResult(_finderPathCountByU_C_C, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByU_C_C, args);
			}

			if ((ratingsEntryModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByC_C_S.getColumnBitmask()) !=
					 0) {

				Object[] args = new Object[] {
					ratingsEntryModelImpl.getOriginalClassNameId(),
					ratingsEntryModelImpl.getOriginalClassPK(),
					ratingsEntryModelImpl.getOriginalScore()
				};

				FinderCacheUtil.removeResult(_finderPathCountByC_C_S, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByC_C_S, args);

				args = new Object[] {
					ratingsEntryModelImpl.getClassNameId(),
					ratingsEntryModelImpl.getClassPK(),
					ratingsEntryModelImpl.getScore()
				};

				FinderCacheUtil.removeResult(_finderPathCountByC_C_S, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByC_C_S, args);
			}
		}

		EntityCacheUtil.putResult(
			RatingsEntryImpl.class, ratingsEntry.getPrimaryKey(), ratingsEntry,
			false);

		clearUniqueFindersCache(ratingsEntryModelImpl, false);
		cacheUniqueFindersCache(ratingsEntryModelImpl);

		ratingsEntry.resetOriginalValues();

		return ratingsEntry;
	}

	/**
	 * Returns the ratings entry with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the ratings entry
	 * @return the ratings entry
	 * @throws NoSuchEntryException if a ratings entry with the primary key could not be found
	 */
	@Override
	public RatingsEntry findByPrimaryKey(Serializable primaryKey)
		throws NoSuchEntryException {

		RatingsEntry ratingsEntry = fetchByPrimaryKey(primaryKey);

		if (ratingsEntry == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchEntryException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return ratingsEntry;
	}

	/**
	 * Returns the ratings entry with the primary key or throws a <code>NoSuchEntryException</code> if it could not be found.
	 *
	 * @param entryId the primary key of the ratings entry
	 * @return the ratings entry
	 * @throws NoSuchEntryException if a ratings entry with the primary key could not be found
	 */
	@Override
	public RatingsEntry findByPrimaryKey(long entryId)
		throws NoSuchEntryException {

		return findByPrimaryKey((Serializable)entryId);
	}

	/**
	 * Returns the ratings entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the ratings entry
	 * @return the ratings entry, or <code>null</code> if a ratings entry with the primary key could not be found
	 */
	@Override
	public RatingsEntry fetchByPrimaryKey(Serializable primaryKey) {
		if (CTPersistenceHelperUtil.isProductionMode(RatingsEntry.class)) {
			return super.fetchByPrimaryKey(primaryKey);
		}

		RatingsEntry ratingsEntry = null;

		Session session = null;

		try {
			session = openSession();

			ratingsEntry = (RatingsEntry)session.get(
				RatingsEntryImpl.class, primaryKey);

			if (ratingsEntry != null) {
				cacheResult(ratingsEntry);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		return ratingsEntry;
	}

	/**
	 * Returns the ratings entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param entryId the primary key of the ratings entry
	 * @return the ratings entry, or <code>null</code> if a ratings entry with the primary key could not be found
	 */
	@Override
	public RatingsEntry fetchByPrimaryKey(long entryId) {
		return fetchByPrimaryKey((Serializable)entryId);
	}

	@Override
	public Map<Serializable, RatingsEntry> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		if (CTPersistenceHelperUtil.isProductionMode(RatingsEntry.class)) {
			return super.fetchByPrimaryKeys(primaryKeys);
		}

		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, RatingsEntry> map =
			new HashMap<Serializable, RatingsEntry>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			RatingsEntry ratingsEntry = fetchByPrimaryKey(primaryKey);

			if (ratingsEntry != null) {
				map.put(primaryKey, ratingsEntry);
			}

			return map;
		}

		StringBundler sb = new StringBundler(primaryKeys.size() * 2 + 1);

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

			for (RatingsEntry ratingsEntry : (List<RatingsEntry>)query.list()) {
				map.put(ratingsEntry.getPrimaryKeyObj(), ratingsEntry);

				cacheResult(ratingsEntry);
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
	 * Returns all the ratings entries.
	 *
	 * @return the ratings entries
	 */
	@Override
	public List<RatingsEntry> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the ratings entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RatingsEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of ratings entries
	 * @param end the upper bound of the range of ratings entries (not inclusive)
	 * @return the range of ratings entries
	 */
	@Override
	public List<RatingsEntry> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the ratings entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RatingsEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of ratings entries
	 * @param end the upper bound of the range of ratings entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of ratings entries
	 */
	@Override
	public List<RatingsEntry> findAll(
		int start, int end, OrderByComparator<RatingsEntry> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the ratings entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>RatingsEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of ratings entries
	 * @param end the upper bound of the range of ratings entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of ratings entries
	 */
	@Override
	public List<RatingsEntry> findAll(
		int start, int end, OrderByComparator<RatingsEntry> orderByComparator,
		boolean useFinderCache) {

		boolean productionMode = CTPersistenceHelperUtil.isProductionMode(
			RatingsEntry.class);

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

		List<RatingsEntry> list = null;

		if (useFinderCache && productionMode) {
			list = (List<RatingsEntry>)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_RATINGSENTRY);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_RATINGSENTRY;

				sql = sql.concat(RatingsEntryModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<RatingsEntry>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache && productionMode) {
					FinderCacheUtil.putResult(finderPath, finderArgs, list);
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
	 * Removes all the ratings entries from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (RatingsEntry ratingsEntry : findAll()) {
			remove(ratingsEntry);
		}
	}

	/**
	 * Returns the number of ratings entries.
	 *
	 * @return the number of ratings entries
	 */
	@Override
	public int countAll() {
		boolean productionMode = CTPersistenceHelperUtil.isProductionMode(
			RatingsEntry.class);

		Long count = null;

		if (productionMode) {
			count = (Long)FinderCacheUtil.getResult(
				_finderPathCountAll, FINDER_ARGS_EMPTY, this);
		}

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(_SQL_COUNT_RATINGSENTRY);

				count = (Long)query.uniqueResult();

				if (productionMode) {
					FinderCacheUtil.putResult(
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
		return EntityCacheUtil.getEntityCache();
	}

	@Override
	protected String getPKDBName() {
		return "entryId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_RATINGSENTRY;
	}

	@Override
	public Set<String> getCTColumnNames(
		CTColumnResolutionType ctColumnResolutionType) {

		return _ctColumnNamesMap.get(ctColumnResolutionType);
	}

	@Override
	public List<String> getMappingTableNames() {
		return _mappingTableNames;
	}

	@Override
	public Map<String, Integer> getTableColumnsMap() {
		return RatingsEntryModelImpl.TABLE_COLUMNS_MAP;
	}

	@Override
	public String getTableName() {
		return "RatingsEntry";
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
		Set<String> ctMergeColumnNames = new HashSet<String>();
		Set<String> ctStrictColumnNames = new HashSet<String>();

		ctControlColumnNames.add("mvccVersion");
		ctControlColumnNames.add("ctCollectionId");
		ctStrictColumnNames.add("uuid_");
		ctStrictColumnNames.add("companyId");
		ctStrictColumnNames.add("userId");
		ctStrictColumnNames.add("userName");
		ctStrictColumnNames.add("createDate");
		ctIgnoreColumnNames.add("modifiedDate");
		ctStrictColumnNames.add("classNameId");
		ctStrictColumnNames.add("classPK");
		ctStrictColumnNames.add("score");

		_ctColumnNamesMap.put(
			CTColumnResolutionType.CONTROL, ctControlColumnNames);
		_ctColumnNamesMap.put(
			CTColumnResolutionType.IGNORE, ctIgnoreColumnNames);
		_ctColumnNamesMap.put(CTColumnResolutionType.MERGE, ctMergeColumnNames);
		_ctColumnNamesMap.put(
			CTColumnResolutionType.PK, Collections.singleton("entryId"));
		_ctColumnNamesMap.put(
			CTColumnResolutionType.STRICT, ctStrictColumnNames);

		_uniqueIndexColumnNames.add(
			new String[] {"userId", "classNameId", "classPK"});
	}

	/**
	 * Initializes the ratings entry persistence.
	 */
	public void afterPropertiesSet() {
		_finderPathWithPaginationFindAll = new FinderPath(
			RatingsEntryImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findAll", new String[0]);

		_finderPathWithoutPaginationFindAll = new FinderPath(
			RatingsEntryImpl.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findAll", new String[0]);

		_finderPathCountAll = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll",
			new String[0]);

		_finderPathWithPaginationFindByUuid = new FinderPath(
			RatingsEntryImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByUuid",
			new String[] {
				String.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByUuid = new FinderPath(
			RatingsEntryImpl.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByUuid", new String[] {String.class.getName()},
			RatingsEntryModelImpl.UUID_COLUMN_BITMASK);

		_finderPathCountByUuid = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByUuid", new String[] {String.class.getName()});

		_finderPathWithPaginationFindByUuid_C = new FinderPath(
			RatingsEntryImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByUuid_C",
			new String[] {
				String.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByUuid_C = new FinderPath(
			RatingsEntryImpl.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByUuid_C",
			new String[] {String.class.getName(), Long.class.getName()},
			RatingsEntryModelImpl.UUID_COLUMN_BITMASK |
			RatingsEntryModelImpl.COMPANYID_COLUMN_BITMASK);

		_finderPathCountByUuid_C = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByUuid_C",
			new String[] {String.class.getName(), Long.class.getName()});

		_finderPathWithPaginationFindByC_C = new FinderPath(
			RatingsEntryImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByC_C",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByC_C = new FinderPath(
			RatingsEntryImpl.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByC_C",
			new String[] {Long.class.getName(), Long.class.getName()},
			RatingsEntryModelImpl.CLASSNAMEID_COLUMN_BITMASK |
			RatingsEntryModelImpl.CLASSPK_COLUMN_BITMASK);

		_finderPathCountByC_C = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_C",
			new String[] {Long.class.getName(), Long.class.getName()});

		_finderPathWithPaginationFindByU_C_C = new FinderPath(
			RatingsEntryImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByU_C_C",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByU_C_C = new FinderPath(
			RatingsEntryImpl.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByU_C_C",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName()
			},
			RatingsEntryModelImpl.USERID_COLUMN_BITMASK |
			RatingsEntryModelImpl.CLASSNAMEID_COLUMN_BITMASK |
			RatingsEntryModelImpl.CLASSPK_COLUMN_BITMASK);

		_finderPathFetchByU_C_C = new FinderPath(
			RatingsEntryImpl.class, FINDER_CLASS_NAME_ENTITY, "fetchByU_C_C",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName()
			},
			RatingsEntryModelImpl.USERID_COLUMN_BITMASK |
			RatingsEntryModelImpl.CLASSNAMEID_COLUMN_BITMASK |
			RatingsEntryModelImpl.CLASSPK_COLUMN_BITMASK);

		_finderPathCountByU_C_C = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByU_C_C",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName()
			});

		_finderPathWithPaginationCountByU_C_C = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "countByU_C_C",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName()
			});

		_finderPathWithPaginationFindByC_C_S = new FinderPath(
			RatingsEntryImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByC_C_S",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Double.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByC_C_S = new FinderPath(
			RatingsEntryImpl.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByC_C_S",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Double.class.getName()
			},
			RatingsEntryModelImpl.CLASSNAMEID_COLUMN_BITMASK |
			RatingsEntryModelImpl.CLASSPK_COLUMN_BITMASK |
			RatingsEntryModelImpl.SCORE_COLUMN_BITMASK);

		_finderPathCountByC_C_S = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByC_C_S",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Double.class.getName()
			});
	}

	public void destroy() {
		EntityCacheUtil.removeCache(RatingsEntryImpl.class.getName());
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	private static final String _SQL_SELECT_RATINGSENTRY =
		"SELECT ratingsEntry FROM RatingsEntry ratingsEntry";

	private static final String _SQL_SELECT_RATINGSENTRY_WHERE =
		"SELECT ratingsEntry FROM RatingsEntry ratingsEntry WHERE ";

	private static final String _SQL_COUNT_RATINGSENTRY =
		"SELECT COUNT(ratingsEntry) FROM RatingsEntry ratingsEntry";

	private static final String _SQL_COUNT_RATINGSENTRY_WHERE =
		"SELECT COUNT(ratingsEntry) FROM RatingsEntry ratingsEntry WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS = "ratingsEntry.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No RatingsEntry exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No RatingsEntry exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		RatingsEntryPersistenceImpl.class);

	private static final Set<String> _badColumnNames = SetUtil.fromArray(
		new String[] {"uuid"});

}