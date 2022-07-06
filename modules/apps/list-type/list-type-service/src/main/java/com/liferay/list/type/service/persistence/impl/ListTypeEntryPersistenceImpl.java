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

package com.liferay.list.type.service.persistence.impl;

import com.liferay.list.type.exception.NoSuchListTypeEntryException;
import com.liferay.list.type.model.ListTypeEntry;
import com.liferay.list.type.model.ListTypeEntryTable;
import com.liferay.list.type.model.impl.ListTypeEntryImpl;
import com.liferay.list.type.model.impl.ListTypeEntryModelImpl;
import com.liferay.list.type.service.persistence.ListTypeEntryPersistence;
import com.liferay.list.type.service.persistence.ListTypeEntryUtil;
import com.liferay.list.type.service.persistence.impl.constants.ListTypePersistenceConstants;
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
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.uuid.PortalUUID;

import java.io.Serializable;

import java.lang.reflect.Field;
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
 * The persistence implementation for the list type entry service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Gabriel Albuquerque
 * @generated
 */
@Component(service = {ListTypeEntryPersistence.class, BasePersistence.class})
public class ListTypeEntryPersistenceImpl
	extends BasePersistenceImpl<ListTypeEntry>
	implements ListTypeEntryPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>ListTypeEntryUtil</code> to access the list type entry persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		ListTypeEntryImpl.class.getName();

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
	 * Returns all the list type entries where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching list type entries
	 */
	@Override
	public List<ListTypeEntry> findByUuid(String uuid) {
		return findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the list type entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ListTypeEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of list type entries
	 * @param end the upper bound of the range of list type entries (not inclusive)
	 * @return the range of matching list type entries
	 */
	@Override
	public List<ListTypeEntry> findByUuid(String uuid, int start, int end) {
		return findByUuid(uuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the list type entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ListTypeEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of list type entries
	 * @param end the upper bound of the range of list type entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching list type entries
	 */
	@Override
	public List<ListTypeEntry> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<ListTypeEntry> orderByComparator) {

		return findByUuid(uuid, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the list type entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ListTypeEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of list type entries
	 * @param end the upper bound of the range of list type entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching list type entries
	 */
	@Override
	public List<ListTypeEntry> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<ListTypeEntry> orderByComparator,
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

		List<ListTypeEntry> list = null;

		if (useFinderCache) {
			list = (List<ListTypeEntry>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (ListTypeEntry listTypeEntry : list) {
					if (!uuid.equals(listTypeEntry.getUuid())) {
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

			sb.append(_SQL_SELECT_LISTTYPEENTRY_WHERE);

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
				sb.append(ListTypeEntryModelImpl.ORDER_BY_JPQL);
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

				list = (List<ListTypeEntry>)QueryUtil.list(
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
	 * Returns the first list type entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching list type entry
	 * @throws NoSuchListTypeEntryException if a matching list type entry could not be found
	 */
	@Override
	public ListTypeEntry findByUuid_First(
			String uuid, OrderByComparator<ListTypeEntry> orderByComparator)
		throws NoSuchListTypeEntryException {

		ListTypeEntry listTypeEntry = fetchByUuid_First(
			uuid, orderByComparator);

		if (listTypeEntry != null) {
			return listTypeEntry;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append("}");

		throw new NoSuchListTypeEntryException(sb.toString());
	}

	/**
	 * Returns the first list type entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching list type entry, or <code>null</code> if a matching list type entry could not be found
	 */
	@Override
	public ListTypeEntry fetchByUuid_First(
		String uuid, OrderByComparator<ListTypeEntry> orderByComparator) {

		List<ListTypeEntry> list = findByUuid(uuid, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last list type entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching list type entry
	 * @throws NoSuchListTypeEntryException if a matching list type entry could not be found
	 */
	@Override
	public ListTypeEntry findByUuid_Last(
			String uuid, OrderByComparator<ListTypeEntry> orderByComparator)
		throws NoSuchListTypeEntryException {

		ListTypeEntry listTypeEntry = fetchByUuid_Last(uuid, orderByComparator);

		if (listTypeEntry != null) {
			return listTypeEntry;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append("}");

		throw new NoSuchListTypeEntryException(sb.toString());
	}

	/**
	 * Returns the last list type entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching list type entry, or <code>null</code> if a matching list type entry could not be found
	 */
	@Override
	public ListTypeEntry fetchByUuid_Last(
		String uuid, OrderByComparator<ListTypeEntry> orderByComparator) {

		int count = countByUuid(uuid);

		if (count == 0) {
			return null;
		}

		List<ListTypeEntry> list = findByUuid(
			uuid, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the list type entries before and after the current list type entry in the ordered set where uuid = &#63;.
	 *
	 * @param listTypeEntryId the primary key of the current list type entry
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next list type entry
	 * @throws NoSuchListTypeEntryException if a list type entry with the primary key could not be found
	 */
	@Override
	public ListTypeEntry[] findByUuid_PrevAndNext(
			long listTypeEntryId, String uuid,
			OrderByComparator<ListTypeEntry> orderByComparator)
		throws NoSuchListTypeEntryException {

		uuid = Objects.toString(uuid, "");

		ListTypeEntry listTypeEntry = findByPrimaryKey(listTypeEntryId);

		Session session = null;

		try {
			session = openSession();

			ListTypeEntry[] array = new ListTypeEntryImpl[3];

			array[0] = getByUuid_PrevAndNext(
				session, listTypeEntry, uuid, orderByComparator, true);

			array[1] = listTypeEntry;

			array[2] = getByUuid_PrevAndNext(
				session, listTypeEntry, uuid, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected ListTypeEntry getByUuid_PrevAndNext(
		Session session, ListTypeEntry listTypeEntry, String uuid,
		OrderByComparator<ListTypeEntry> orderByComparator, boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(3);
		}

		sb.append(_SQL_SELECT_LISTTYPEENTRY_WHERE);

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
			sb.append(ListTypeEntryModelImpl.ORDER_BY_JPQL);
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
						listTypeEntry)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<ListTypeEntry> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the list type entries where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	@Override
	public void removeByUuid(String uuid) {
		for (ListTypeEntry listTypeEntry :
				findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(listTypeEntry);
		}
	}

	/**
	 * Returns the number of list type entries where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching list type entries
	 */
	@Override
	public int countByUuid(String uuid) {
		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = _finderPathCountByUuid;

		Object[] finderArgs = new Object[] {uuid};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_LISTTYPEENTRY_WHERE);

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
		"listTypeEntry.uuid = ?";

	private static final String _FINDER_COLUMN_UUID_UUID_3 =
		"(listTypeEntry.uuid IS NULL OR listTypeEntry.uuid = '')";

	private FinderPath _finderPathWithPaginationFindByUuid_C;
	private FinderPath _finderPathWithoutPaginationFindByUuid_C;
	private FinderPath _finderPathCountByUuid_C;

	/**
	 * Returns all the list type entries where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching list type entries
	 */
	@Override
	public List<ListTypeEntry> findByUuid_C(String uuid, long companyId) {
		return findByUuid_C(
			uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the list type entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ListTypeEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of list type entries
	 * @param end the upper bound of the range of list type entries (not inclusive)
	 * @return the range of matching list type entries
	 */
	@Override
	public List<ListTypeEntry> findByUuid_C(
		String uuid, long companyId, int start, int end) {

		return findByUuid_C(uuid, companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the list type entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ListTypeEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of list type entries
	 * @param end the upper bound of the range of list type entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching list type entries
	 */
	@Override
	public List<ListTypeEntry> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<ListTypeEntry> orderByComparator) {

		return findByUuid_C(
			uuid, companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the list type entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ListTypeEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of list type entries
	 * @param end the upper bound of the range of list type entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching list type entries
	 */
	@Override
	public List<ListTypeEntry> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<ListTypeEntry> orderByComparator,
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

		List<ListTypeEntry> list = null;

		if (useFinderCache) {
			list = (List<ListTypeEntry>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (ListTypeEntry listTypeEntry : list) {
					if (!uuid.equals(listTypeEntry.getUuid()) ||
						(companyId != listTypeEntry.getCompanyId())) {

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

			sb.append(_SQL_SELECT_LISTTYPEENTRY_WHERE);

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
				sb.append(ListTypeEntryModelImpl.ORDER_BY_JPQL);
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

				list = (List<ListTypeEntry>)QueryUtil.list(
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
	 * Returns the first list type entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching list type entry
	 * @throws NoSuchListTypeEntryException if a matching list type entry could not be found
	 */
	@Override
	public ListTypeEntry findByUuid_C_First(
			String uuid, long companyId,
			OrderByComparator<ListTypeEntry> orderByComparator)
		throws NoSuchListTypeEntryException {

		ListTypeEntry listTypeEntry = fetchByUuid_C_First(
			uuid, companyId, orderByComparator);

		if (listTypeEntry != null) {
			return listTypeEntry;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append(", companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchListTypeEntryException(sb.toString());
	}

	/**
	 * Returns the first list type entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching list type entry, or <code>null</code> if a matching list type entry could not be found
	 */
	@Override
	public ListTypeEntry fetchByUuid_C_First(
		String uuid, long companyId,
		OrderByComparator<ListTypeEntry> orderByComparator) {

		List<ListTypeEntry> list = findByUuid_C(
			uuid, companyId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last list type entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching list type entry
	 * @throws NoSuchListTypeEntryException if a matching list type entry could not be found
	 */
	@Override
	public ListTypeEntry findByUuid_C_Last(
			String uuid, long companyId,
			OrderByComparator<ListTypeEntry> orderByComparator)
		throws NoSuchListTypeEntryException {

		ListTypeEntry listTypeEntry = fetchByUuid_C_Last(
			uuid, companyId, orderByComparator);

		if (listTypeEntry != null) {
			return listTypeEntry;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append(", companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchListTypeEntryException(sb.toString());
	}

	/**
	 * Returns the last list type entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching list type entry, or <code>null</code> if a matching list type entry could not be found
	 */
	@Override
	public ListTypeEntry fetchByUuid_C_Last(
		String uuid, long companyId,
		OrderByComparator<ListTypeEntry> orderByComparator) {

		int count = countByUuid_C(uuid, companyId);

		if (count == 0) {
			return null;
		}

		List<ListTypeEntry> list = findByUuid_C(
			uuid, companyId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the list type entries before and after the current list type entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param listTypeEntryId the primary key of the current list type entry
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next list type entry
	 * @throws NoSuchListTypeEntryException if a list type entry with the primary key could not be found
	 */
	@Override
	public ListTypeEntry[] findByUuid_C_PrevAndNext(
			long listTypeEntryId, String uuid, long companyId,
			OrderByComparator<ListTypeEntry> orderByComparator)
		throws NoSuchListTypeEntryException {

		uuid = Objects.toString(uuid, "");

		ListTypeEntry listTypeEntry = findByPrimaryKey(listTypeEntryId);

		Session session = null;

		try {
			session = openSession();

			ListTypeEntry[] array = new ListTypeEntryImpl[3];

			array[0] = getByUuid_C_PrevAndNext(
				session, listTypeEntry, uuid, companyId, orderByComparator,
				true);

			array[1] = listTypeEntry;

			array[2] = getByUuid_C_PrevAndNext(
				session, listTypeEntry, uuid, companyId, orderByComparator,
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

	protected ListTypeEntry getByUuid_C_PrevAndNext(
		Session session, ListTypeEntry listTypeEntry, String uuid,
		long companyId, OrderByComparator<ListTypeEntry> orderByComparator,
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

		sb.append(_SQL_SELECT_LISTTYPEENTRY_WHERE);

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
			sb.append(ListTypeEntryModelImpl.ORDER_BY_JPQL);
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
						listTypeEntry)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<ListTypeEntry> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the list type entries where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	@Override
	public void removeByUuid_C(String uuid, long companyId) {
		for (ListTypeEntry listTypeEntry :
				findByUuid_C(
					uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(listTypeEntry);
		}
	}

	/**
	 * Returns the number of list type entries where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching list type entries
	 */
	@Override
	public int countByUuid_C(String uuid, long companyId) {
		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = _finderPathCountByUuid_C;

		Object[] finderArgs = new Object[] {uuid, companyId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_LISTTYPEENTRY_WHERE);

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
		"listTypeEntry.uuid = ? AND ";

	private static final String _FINDER_COLUMN_UUID_C_UUID_3 =
		"(listTypeEntry.uuid IS NULL OR listTypeEntry.uuid = '') AND ";

	private static final String _FINDER_COLUMN_UUID_C_COMPANYID_2 =
		"listTypeEntry.companyId = ?";

	private FinderPath _finderPathWithPaginationFindByListTypeEntryId;
	private FinderPath _finderPathWithoutPaginationFindByListTypeEntryId;
	private FinderPath _finderPathCountByListTypeEntryId;
	private FinderPath _finderPathWithPaginationCountByListTypeEntryId;

	/**
	 * Returns all the list type entries where listTypeEntryId = &#63;.
	 *
	 * @param listTypeEntryId the list type entry ID
	 * @return the matching list type entries
	 */
	@Override
	public List<ListTypeEntry> findByListTypeEntryId(long listTypeEntryId) {
		return findByListTypeEntryId(
			listTypeEntryId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the list type entries where listTypeEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ListTypeEntryModelImpl</code>.
	 * </p>
	 *
	 * @param listTypeEntryId the list type entry ID
	 * @param start the lower bound of the range of list type entries
	 * @param end the upper bound of the range of list type entries (not inclusive)
	 * @return the range of matching list type entries
	 */
	@Override
	public List<ListTypeEntry> findByListTypeEntryId(
		long listTypeEntryId, int start, int end) {

		return findByListTypeEntryId(listTypeEntryId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the list type entries where listTypeEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ListTypeEntryModelImpl</code>.
	 * </p>
	 *
	 * @param listTypeEntryId the list type entry ID
	 * @param start the lower bound of the range of list type entries
	 * @param end the upper bound of the range of list type entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching list type entries
	 */
	@Override
	public List<ListTypeEntry> findByListTypeEntryId(
		long listTypeEntryId, int start, int end,
		OrderByComparator<ListTypeEntry> orderByComparator) {

		return findByListTypeEntryId(
			listTypeEntryId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the list type entries where listTypeEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ListTypeEntryModelImpl</code>.
	 * </p>
	 *
	 * @param listTypeEntryId the list type entry ID
	 * @param start the lower bound of the range of list type entries
	 * @param end the upper bound of the range of list type entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching list type entries
	 */
	@Override
	public List<ListTypeEntry> findByListTypeEntryId(
		long listTypeEntryId, int start, int end,
		OrderByComparator<ListTypeEntry> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByListTypeEntryId;
				finderArgs = new Object[] {listTypeEntryId};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByListTypeEntryId;
			finderArgs = new Object[] {
				listTypeEntryId, start, end, orderByComparator
			};
		}

		List<ListTypeEntry> list = null;

		if (useFinderCache) {
			list = (List<ListTypeEntry>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (ListTypeEntry listTypeEntry : list) {
					if (listTypeEntryId != listTypeEntry.getListTypeEntryId()) {
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

			sb.append(_SQL_SELECT_LISTTYPEENTRY_WHERE);

			sb.append(_FINDER_COLUMN_LISTTYPEENTRYID_LISTTYPEENTRYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(ListTypeEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(listTypeEntryId);

				list = (List<ListTypeEntry>)QueryUtil.list(
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
	 * Returns the first list type entry in the ordered set where listTypeEntryId = &#63;.
	 *
	 * @param listTypeEntryId the list type entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching list type entry
	 * @throws NoSuchListTypeEntryException if a matching list type entry could not be found
	 */
	@Override
	public ListTypeEntry findByListTypeEntryId_First(
			long listTypeEntryId,
			OrderByComparator<ListTypeEntry> orderByComparator)
		throws NoSuchListTypeEntryException {

		ListTypeEntry listTypeEntry = fetchByListTypeEntryId_First(
			listTypeEntryId, orderByComparator);

		if (listTypeEntry != null) {
			return listTypeEntry;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("listTypeEntryId=");
		sb.append(listTypeEntryId);

		sb.append("}");

		throw new NoSuchListTypeEntryException(sb.toString());
	}

	/**
	 * Returns the first list type entry in the ordered set where listTypeEntryId = &#63;.
	 *
	 * @param listTypeEntryId the list type entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching list type entry, or <code>null</code> if a matching list type entry could not be found
	 */
	@Override
	public ListTypeEntry fetchByListTypeEntryId_First(
		long listTypeEntryId,
		OrderByComparator<ListTypeEntry> orderByComparator) {

		List<ListTypeEntry> list = findByListTypeEntryId(
			listTypeEntryId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last list type entry in the ordered set where listTypeEntryId = &#63;.
	 *
	 * @param listTypeEntryId the list type entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching list type entry
	 * @throws NoSuchListTypeEntryException if a matching list type entry could not be found
	 */
	@Override
	public ListTypeEntry findByListTypeEntryId_Last(
			long listTypeEntryId,
			OrderByComparator<ListTypeEntry> orderByComparator)
		throws NoSuchListTypeEntryException {

		ListTypeEntry listTypeEntry = fetchByListTypeEntryId_Last(
			listTypeEntryId, orderByComparator);

		if (listTypeEntry != null) {
			return listTypeEntry;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("listTypeEntryId=");
		sb.append(listTypeEntryId);

		sb.append("}");

		throw new NoSuchListTypeEntryException(sb.toString());
	}

	/**
	 * Returns the last list type entry in the ordered set where listTypeEntryId = &#63;.
	 *
	 * @param listTypeEntryId the list type entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching list type entry, or <code>null</code> if a matching list type entry could not be found
	 */
	@Override
	public ListTypeEntry fetchByListTypeEntryId_Last(
		long listTypeEntryId,
		OrderByComparator<ListTypeEntry> orderByComparator) {

		int count = countByListTypeEntryId(listTypeEntryId);

		if (count == 0) {
			return null;
		}

		List<ListTypeEntry> list = findByListTypeEntryId(
			listTypeEntryId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns all the list type entries where listTypeEntryId = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ListTypeEntryModelImpl</code>.
	 * </p>
	 *
	 * @param listTypeEntryIds the list type entry IDs
	 * @return the matching list type entries
	 */
	@Override
	public List<ListTypeEntry> findByListTypeEntryId(long[] listTypeEntryIds) {
		return findByListTypeEntryId(
			listTypeEntryIds, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the list type entries where listTypeEntryId = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ListTypeEntryModelImpl</code>.
	 * </p>
	 *
	 * @param listTypeEntryIds the list type entry IDs
	 * @param start the lower bound of the range of list type entries
	 * @param end the upper bound of the range of list type entries (not inclusive)
	 * @return the range of matching list type entries
	 */
	@Override
	public List<ListTypeEntry> findByListTypeEntryId(
		long[] listTypeEntryIds, int start, int end) {

		return findByListTypeEntryId(listTypeEntryIds, start, end, null);
	}

	/**
	 * Returns an ordered range of all the list type entries where listTypeEntryId = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ListTypeEntryModelImpl</code>.
	 * </p>
	 *
	 * @param listTypeEntryIds the list type entry IDs
	 * @param start the lower bound of the range of list type entries
	 * @param end the upper bound of the range of list type entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching list type entries
	 */
	@Override
	public List<ListTypeEntry> findByListTypeEntryId(
		long[] listTypeEntryIds, int start, int end,
		OrderByComparator<ListTypeEntry> orderByComparator) {

		return findByListTypeEntryId(
			listTypeEntryIds, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the list type entries where listTypeEntryId = &#63;, optionally using the finder cache.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ListTypeEntryModelImpl</code>.
	 * </p>
	 *
	 * @param listTypeEntryId the list type entry ID
	 * @param start the lower bound of the range of list type entries
	 * @param end the upper bound of the range of list type entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching list type entries
	 */
	@Override
	public List<ListTypeEntry> findByListTypeEntryId(
		long[] listTypeEntryIds, int start, int end,
		OrderByComparator<ListTypeEntry> orderByComparator,
		boolean useFinderCache) {

		if (listTypeEntryIds == null) {
			listTypeEntryIds = new long[0];
		}
		else if (listTypeEntryIds.length > 1) {
			listTypeEntryIds = ArrayUtil.sortedUnique(listTypeEntryIds);
		}

		if (listTypeEntryIds.length == 1) {
			return findByListTypeEntryId(
				listTypeEntryIds[0], start, end, orderByComparator);
		}

		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderArgs = new Object[] {StringUtil.merge(listTypeEntryIds)};
			}
		}
		else if (useFinderCache) {
			finderArgs = new Object[] {
				StringUtil.merge(listTypeEntryIds), start, end,
				orderByComparator
			};
		}

		List<ListTypeEntry> list = null;

		if (useFinderCache) {
			list = (List<ListTypeEntry>)finderCache.getResult(
				_finderPathWithPaginationFindByListTypeEntryId, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (ListTypeEntry listTypeEntry : list) {
					if (!ArrayUtil.contains(
							listTypeEntryIds,
							listTypeEntry.getListTypeEntryId())) {

						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler sb = new StringBundler();

			sb.append(_SQL_SELECT_LISTTYPEENTRY_WHERE);

			if (listTypeEntryIds.length > 0) {
				sb.append("(");

				sb.append(_FINDER_COLUMN_LISTTYPEENTRYID_LISTTYPEENTRYID_7);

				sb.append(StringUtil.merge(listTypeEntryIds));

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
				sb.append(ListTypeEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<ListTypeEntry>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					finderCache.putResult(
						_finderPathWithPaginationFindByListTypeEntryId,
						finderArgs, list);
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
	 * Removes all the list type entries where listTypeEntryId = &#63; from the database.
	 *
	 * @param listTypeEntryId the list type entry ID
	 */
	@Override
	public void removeByListTypeEntryId(long listTypeEntryId) {
		for (ListTypeEntry listTypeEntry :
				findByListTypeEntryId(
					listTypeEntryId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(listTypeEntry);
		}
	}

	/**
	 * Returns the number of list type entries where listTypeEntryId = &#63;.
	 *
	 * @param listTypeEntryId the list type entry ID
	 * @return the number of matching list type entries
	 */
	@Override
	public int countByListTypeEntryId(long listTypeEntryId) {
		FinderPath finderPath = _finderPathCountByListTypeEntryId;

		Object[] finderArgs = new Object[] {listTypeEntryId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_LISTTYPEENTRY_WHERE);

			sb.append(_FINDER_COLUMN_LISTTYPEENTRYID_LISTTYPEENTRYID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(listTypeEntryId);

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

	/**
	 * Returns the number of list type entries where listTypeEntryId = any &#63;.
	 *
	 * @param listTypeEntryIds the list type entry IDs
	 * @return the number of matching list type entries
	 */
	@Override
	public int countByListTypeEntryId(long[] listTypeEntryIds) {
		if (listTypeEntryIds == null) {
			listTypeEntryIds = new long[0];
		}
		else if (listTypeEntryIds.length > 1) {
			listTypeEntryIds = ArrayUtil.sortedUnique(listTypeEntryIds);
		}

		Object[] finderArgs = new Object[] {StringUtil.merge(listTypeEntryIds)};

		Long count = (Long)finderCache.getResult(
			_finderPathWithPaginationCountByListTypeEntryId, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler();

			sb.append(_SQL_COUNT_LISTTYPEENTRY_WHERE);

			if (listTypeEntryIds.length > 0) {
				sb.append("(");

				sb.append(_FINDER_COLUMN_LISTTYPEENTRYID_LISTTYPEENTRYID_7);

				sb.append(StringUtil.merge(listTypeEntryIds));

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

				count = (Long)query.uniqueResult();

				finderCache.putResult(
					_finderPathWithPaginationCountByListTypeEntryId, finderArgs,
					count);
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
		_FINDER_COLUMN_LISTTYPEENTRYID_LISTTYPEENTRYID_2 =
			"listTypeEntry.listTypeEntryId = ?";

	private static final String
		_FINDER_COLUMN_LISTTYPEENTRYID_LISTTYPEENTRYID_7 =
			"listTypeEntry.listTypeEntryId IN (";

	private FinderPath _finderPathWithPaginationFindByListTypeDefinitionId;
	private FinderPath _finderPathWithoutPaginationFindByListTypeDefinitionId;
	private FinderPath _finderPathCountByListTypeDefinitionId;

	/**
	 * Returns all the list type entries where listTypeDefinitionId = &#63;.
	 *
	 * @param listTypeDefinitionId the list type definition ID
	 * @return the matching list type entries
	 */
	@Override
	public List<ListTypeEntry> findByListTypeDefinitionId(
		long listTypeDefinitionId) {

		return findByListTypeDefinitionId(
			listTypeDefinitionId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the list type entries where listTypeDefinitionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ListTypeEntryModelImpl</code>.
	 * </p>
	 *
	 * @param listTypeDefinitionId the list type definition ID
	 * @param start the lower bound of the range of list type entries
	 * @param end the upper bound of the range of list type entries (not inclusive)
	 * @return the range of matching list type entries
	 */
	@Override
	public List<ListTypeEntry> findByListTypeDefinitionId(
		long listTypeDefinitionId, int start, int end) {

		return findByListTypeDefinitionId(
			listTypeDefinitionId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the list type entries where listTypeDefinitionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ListTypeEntryModelImpl</code>.
	 * </p>
	 *
	 * @param listTypeDefinitionId the list type definition ID
	 * @param start the lower bound of the range of list type entries
	 * @param end the upper bound of the range of list type entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching list type entries
	 */
	@Override
	public List<ListTypeEntry> findByListTypeDefinitionId(
		long listTypeDefinitionId, int start, int end,
		OrderByComparator<ListTypeEntry> orderByComparator) {

		return findByListTypeDefinitionId(
			listTypeDefinitionId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the list type entries where listTypeDefinitionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ListTypeEntryModelImpl</code>.
	 * </p>
	 *
	 * @param listTypeDefinitionId the list type definition ID
	 * @param start the lower bound of the range of list type entries
	 * @param end the upper bound of the range of list type entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching list type entries
	 */
	@Override
	public List<ListTypeEntry> findByListTypeDefinitionId(
		long listTypeDefinitionId, int start, int end,
		OrderByComparator<ListTypeEntry> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath =
					_finderPathWithoutPaginationFindByListTypeDefinitionId;
				finderArgs = new Object[] {listTypeDefinitionId};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByListTypeDefinitionId;
			finderArgs = new Object[] {
				listTypeDefinitionId, start, end, orderByComparator
			};
		}

		List<ListTypeEntry> list = null;

		if (useFinderCache) {
			list = (List<ListTypeEntry>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (ListTypeEntry listTypeEntry : list) {
					if (listTypeDefinitionId !=
							listTypeEntry.getListTypeDefinitionId()) {

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

			sb.append(_SQL_SELECT_LISTTYPEENTRY_WHERE);

			sb.append(
				_FINDER_COLUMN_LISTTYPEDEFINITIONID_LISTTYPEDEFINITIONID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(ListTypeEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(listTypeDefinitionId);

				list = (List<ListTypeEntry>)QueryUtil.list(
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
	 * Returns the first list type entry in the ordered set where listTypeDefinitionId = &#63;.
	 *
	 * @param listTypeDefinitionId the list type definition ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching list type entry
	 * @throws NoSuchListTypeEntryException if a matching list type entry could not be found
	 */
	@Override
	public ListTypeEntry findByListTypeDefinitionId_First(
			long listTypeDefinitionId,
			OrderByComparator<ListTypeEntry> orderByComparator)
		throws NoSuchListTypeEntryException {

		ListTypeEntry listTypeEntry = fetchByListTypeDefinitionId_First(
			listTypeDefinitionId, orderByComparator);

		if (listTypeEntry != null) {
			return listTypeEntry;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("listTypeDefinitionId=");
		sb.append(listTypeDefinitionId);

		sb.append("}");

		throw new NoSuchListTypeEntryException(sb.toString());
	}

	/**
	 * Returns the first list type entry in the ordered set where listTypeDefinitionId = &#63;.
	 *
	 * @param listTypeDefinitionId the list type definition ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching list type entry, or <code>null</code> if a matching list type entry could not be found
	 */
	@Override
	public ListTypeEntry fetchByListTypeDefinitionId_First(
		long listTypeDefinitionId,
		OrderByComparator<ListTypeEntry> orderByComparator) {

		List<ListTypeEntry> list = findByListTypeDefinitionId(
			listTypeDefinitionId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last list type entry in the ordered set where listTypeDefinitionId = &#63;.
	 *
	 * @param listTypeDefinitionId the list type definition ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching list type entry
	 * @throws NoSuchListTypeEntryException if a matching list type entry could not be found
	 */
	@Override
	public ListTypeEntry findByListTypeDefinitionId_Last(
			long listTypeDefinitionId,
			OrderByComparator<ListTypeEntry> orderByComparator)
		throws NoSuchListTypeEntryException {

		ListTypeEntry listTypeEntry = fetchByListTypeDefinitionId_Last(
			listTypeDefinitionId, orderByComparator);

		if (listTypeEntry != null) {
			return listTypeEntry;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("listTypeDefinitionId=");
		sb.append(listTypeDefinitionId);

		sb.append("}");

		throw new NoSuchListTypeEntryException(sb.toString());
	}

	/**
	 * Returns the last list type entry in the ordered set where listTypeDefinitionId = &#63;.
	 *
	 * @param listTypeDefinitionId the list type definition ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching list type entry, or <code>null</code> if a matching list type entry could not be found
	 */
	@Override
	public ListTypeEntry fetchByListTypeDefinitionId_Last(
		long listTypeDefinitionId,
		OrderByComparator<ListTypeEntry> orderByComparator) {

		int count = countByListTypeDefinitionId(listTypeDefinitionId);

		if (count == 0) {
			return null;
		}

		List<ListTypeEntry> list = findByListTypeDefinitionId(
			listTypeDefinitionId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the list type entries before and after the current list type entry in the ordered set where listTypeDefinitionId = &#63;.
	 *
	 * @param listTypeEntryId the primary key of the current list type entry
	 * @param listTypeDefinitionId the list type definition ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next list type entry
	 * @throws NoSuchListTypeEntryException if a list type entry with the primary key could not be found
	 */
	@Override
	public ListTypeEntry[] findByListTypeDefinitionId_PrevAndNext(
			long listTypeEntryId, long listTypeDefinitionId,
			OrderByComparator<ListTypeEntry> orderByComparator)
		throws NoSuchListTypeEntryException {

		ListTypeEntry listTypeEntry = findByPrimaryKey(listTypeEntryId);

		Session session = null;

		try {
			session = openSession();

			ListTypeEntry[] array = new ListTypeEntryImpl[3];

			array[0] = getByListTypeDefinitionId_PrevAndNext(
				session, listTypeEntry, listTypeDefinitionId, orderByComparator,
				true);

			array[1] = listTypeEntry;

			array[2] = getByListTypeDefinitionId_PrevAndNext(
				session, listTypeEntry, listTypeDefinitionId, orderByComparator,
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

	protected ListTypeEntry getByListTypeDefinitionId_PrevAndNext(
		Session session, ListTypeEntry listTypeEntry, long listTypeDefinitionId,
		OrderByComparator<ListTypeEntry> orderByComparator, boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(3);
		}

		sb.append(_SQL_SELECT_LISTTYPEENTRY_WHERE);

		sb.append(_FINDER_COLUMN_LISTTYPEDEFINITIONID_LISTTYPEDEFINITIONID_2);

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
			sb.append(ListTypeEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(listTypeDefinitionId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						listTypeEntry)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<ListTypeEntry> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the list type entries where listTypeDefinitionId = &#63; from the database.
	 *
	 * @param listTypeDefinitionId the list type definition ID
	 */
	@Override
	public void removeByListTypeDefinitionId(long listTypeDefinitionId) {
		for (ListTypeEntry listTypeEntry :
				findByListTypeDefinitionId(
					listTypeDefinitionId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(listTypeEntry);
		}
	}

	/**
	 * Returns the number of list type entries where listTypeDefinitionId = &#63;.
	 *
	 * @param listTypeDefinitionId the list type definition ID
	 * @return the number of matching list type entries
	 */
	@Override
	public int countByListTypeDefinitionId(long listTypeDefinitionId) {
		FinderPath finderPath = _finderPathCountByListTypeDefinitionId;

		Object[] finderArgs = new Object[] {listTypeDefinitionId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_LISTTYPEENTRY_WHERE);

			sb.append(
				_FINDER_COLUMN_LISTTYPEDEFINITIONID_LISTTYPEDEFINITIONID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(listTypeDefinitionId);

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
		_FINDER_COLUMN_LISTTYPEDEFINITIONID_LISTTYPEDEFINITIONID_2 =
			"listTypeEntry.listTypeDefinitionId = ?";

	private FinderPath _finderPathFetchByLTDI_K;
	private FinderPath _finderPathCountByLTDI_K;

	/**
	 * Returns the list type entry where listTypeDefinitionId = &#63; and key = &#63; or throws a <code>NoSuchListTypeEntryException</code> if it could not be found.
	 *
	 * @param listTypeDefinitionId the list type definition ID
	 * @param key the key
	 * @return the matching list type entry
	 * @throws NoSuchListTypeEntryException if a matching list type entry could not be found
	 */
	@Override
	public ListTypeEntry findByLTDI_K(long listTypeDefinitionId, String key)
		throws NoSuchListTypeEntryException {

		ListTypeEntry listTypeEntry = fetchByLTDI_K(listTypeDefinitionId, key);

		if (listTypeEntry == null) {
			StringBundler sb = new StringBundler(6);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("listTypeDefinitionId=");
			sb.append(listTypeDefinitionId);

			sb.append(", key=");
			sb.append(key);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchListTypeEntryException(sb.toString());
		}

		return listTypeEntry;
	}

	/**
	 * Returns the list type entry where listTypeDefinitionId = &#63; and key = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param listTypeDefinitionId the list type definition ID
	 * @param key the key
	 * @return the matching list type entry, or <code>null</code> if a matching list type entry could not be found
	 */
	@Override
	public ListTypeEntry fetchByLTDI_K(long listTypeDefinitionId, String key) {
		return fetchByLTDI_K(listTypeDefinitionId, key, true);
	}

	/**
	 * Returns the list type entry where listTypeDefinitionId = &#63; and key = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param listTypeDefinitionId the list type definition ID
	 * @param key the key
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching list type entry, or <code>null</code> if a matching list type entry could not be found
	 */
	@Override
	public ListTypeEntry fetchByLTDI_K(
		long listTypeDefinitionId, String key, boolean useFinderCache) {

		key = Objects.toString(key, "");

		Object[] finderArgs = null;

		if (useFinderCache) {
			finderArgs = new Object[] {listTypeDefinitionId, key};
		}

		Object result = null;

		if (useFinderCache) {
			result = finderCache.getResult(
				_finderPathFetchByLTDI_K, finderArgs);
		}

		if (result instanceof ListTypeEntry) {
			ListTypeEntry listTypeEntry = (ListTypeEntry)result;

			if ((listTypeDefinitionId !=
					listTypeEntry.getListTypeDefinitionId()) ||
				!Objects.equals(key, listTypeEntry.getKey())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_SELECT_LISTTYPEENTRY_WHERE);

			sb.append(_FINDER_COLUMN_LTDI_K_LISTTYPEDEFINITIONID_2);

			boolean bindKey = false;

			if (key.isEmpty()) {
				sb.append(_FINDER_COLUMN_LTDI_K_KEY_3);
			}
			else {
				bindKey = true;

				sb.append(_FINDER_COLUMN_LTDI_K_KEY_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(listTypeDefinitionId);

				if (bindKey) {
					queryPos.add(key);
				}

				List<ListTypeEntry> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache) {
						finderCache.putResult(
							_finderPathFetchByLTDI_K, finderArgs, list);
					}
				}
				else {
					if (list.size() > 1) {
						Collections.sort(list, Collections.reverseOrder());

						if (_log.isWarnEnabled()) {
							if (!useFinderCache) {
								finderArgs = new Object[] {
									listTypeDefinitionId, key
								};
							}

							_log.warn(
								"ListTypeEntryPersistenceImpl.fetchByLTDI_K(long, String, boolean) with parameters (" +
									StringUtil.merge(finderArgs) +
										") yields a result set with more than 1 result. This violates the logical unique restriction. There is no order guarantee on which result is returned by this finder.");
						}
					}

					ListTypeEntry listTypeEntry = list.get(0);

					result = listTypeEntry;

					cacheResult(listTypeEntry);
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
			return (ListTypeEntry)result;
		}
	}

	/**
	 * Removes the list type entry where listTypeDefinitionId = &#63; and key = &#63; from the database.
	 *
	 * @param listTypeDefinitionId the list type definition ID
	 * @param key the key
	 * @return the list type entry that was removed
	 */
	@Override
	public ListTypeEntry removeByLTDI_K(long listTypeDefinitionId, String key)
		throws NoSuchListTypeEntryException {

		ListTypeEntry listTypeEntry = findByLTDI_K(listTypeDefinitionId, key);

		return remove(listTypeEntry);
	}

	/**
	 * Returns the number of list type entries where listTypeDefinitionId = &#63; and key = &#63;.
	 *
	 * @param listTypeDefinitionId the list type definition ID
	 * @param key the key
	 * @return the number of matching list type entries
	 */
	@Override
	public int countByLTDI_K(long listTypeDefinitionId, String key) {
		key = Objects.toString(key, "");

		FinderPath finderPath = _finderPathCountByLTDI_K;

		Object[] finderArgs = new Object[] {listTypeDefinitionId, key};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_LISTTYPEENTRY_WHERE);

			sb.append(_FINDER_COLUMN_LTDI_K_LISTTYPEDEFINITIONID_2);

			boolean bindKey = false;

			if (key.isEmpty()) {
				sb.append(_FINDER_COLUMN_LTDI_K_KEY_3);
			}
			else {
				bindKey = true;

				sb.append(_FINDER_COLUMN_LTDI_K_KEY_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(listTypeDefinitionId);

				if (bindKey) {
					queryPos.add(key);
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

	private static final String _FINDER_COLUMN_LTDI_K_LISTTYPEDEFINITIONID_2 =
		"listTypeEntry.listTypeDefinitionId = ? AND ";

	private static final String _FINDER_COLUMN_LTDI_K_KEY_2 =
		"listTypeEntry.key = ?";

	private static final String _FINDER_COLUMN_LTDI_K_KEY_3 =
		"(listTypeEntry.key IS NULL OR listTypeEntry.key = '')";

	public ListTypeEntryPersistenceImpl() {
		Map<String, String> dbColumnNames = new HashMap<String, String>();

		dbColumnNames.put("uuid", "uuid_");
		dbColumnNames.put("key", "key_");
		dbColumnNames.put("type", "type_");

		setDBColumnNames(dbColumnNames);

		setModelClass(ListTypeEntry.class);

		setModelImplClass(ListTypeEntryImpl.class);
		setModelPKClass(long.class);

		setTable(ListTypeEntryTable.INSTANCE);
	}

	/**
	 * Caches the list type entry in the entity cache if it is enabled.
	 *
	 * @param listTypeEntry the list type entry
	 */
	@Override
	public void cacheResult(ListTypeEntry listTypeEntry) {
		entityCache.putResult(
			ListTypeEntryImpl.class, listTypeEntry.getPrimaryKey(),
			listTypeEntry);

		finderCache.putResult(
			_finderPathFetchByLTDI_K,
			new Object[] {
				listTypeEntry.getListTypeDefinitionId(), listTypeEntry.getKey()
			},
			listTypeEntry);
	}

	private int _valueObjectFinderCacheListThreshold;

	/**
	 * Caches the list type entries in the entity cache if it is enabled.
	 *
	 * @param listTypeEntries the list type entries
	 */
	@Override
	public void cacheResult(List<ListTypeEntry> listTypeEntries) {
		if ((_valueObjectFinderCacheListThreshold == 0) ||
			((_valueObjectFinderCacheListThreshold > 0) &&
			 (listTypeEntries.size() > _valueObjectFinderCacheListThreshold))) {

			return;
		}

		for (ListTypeEntry listTypeEntry : listTypeEntries) {
			if (entityCache.getResult(
					ListTypeEntryImpl.class, listTypeEntry.getPrimaryKey()) ==
						null) {

				cacheResult(listTypeEntry);
			}
		}
	}

	/**
	 * Clears the cache for all list type entries.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(ListTypeEntryImpl.class);

		finderCache.clearCache(ListTypeEntryImpl.class);
	}

	/**
	 * Clears the cache for the list type entry.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(ListTypeEntry listTypeEntry) {
		entityCache.removeResult(ListTypeEntryImpl.class, listTypeEntry);
	}

	@Override
	public void clearCache(List<ListTypeEntry> listTypeEntries) {
		for (ListTypeEntry listTypeEntry : listTypeEntries) {
			entityCache.removeResult(ListTypeEntryImpl.class, listTypeEntry);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(ListTypeEntryImpl.class);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(ListTypeEntryImpl.class, primaryKey);
		}
	}

	protected void cacheUniqueFindersCache(
		ListTypeEntryModelImpl listTypeEntryModelImpl) {

		Object[] args = new Object[] {
			listTypeEntryModelImpl.getListTypeDefinitionId(),
			listTypeEntryModelImpl.getKey()
		};

		finderCache.putResult(_finderPathCountByLTDI_K, args, Long.valueOf(1));
		finderCache.putResult(
			_finderPathFetchByLTDI_K, args, listTypeEntryModelImpl);
	}

	/**
	 * Creates a new list type entry with the primary key. Does not add the list type entry to the database.
	 *
	 * @param listTypeEntryId the primary key for the new list type entry
	 * @return the new list type entry
	 */
	@Override
	public ListTypeEntry create(long listTypeEntryId) {
		ListTypeEntry listTypeEntry = new ListTypeEntryImpl();

		listTypeEntry.setNew(true);
		listTypeEntry.setPrimaryKey(listTypeEntryId);

		String uuid = _portalUUID.generate();

		listTypeEntry.setUuid(uuid);

		listTypeEntry.setCompanyId(CompanyThreadLocal.getCompanyId());

		return listTypeEntry;
	}

	/**
	 * Removes the list type entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param listTypeEntryId the primary key of the list type entry
	 * @return the list type entry that was removed
	 * @throws NoSuchListTypeEntryException if a list type entry with the primary key could not be found
	 */
	@Override
	public ListTypeEntry remove(long listTypeEntryId)
		throws NoSuchListTypeEntryException {

		return remove((Serializable)listTypeEntryId);
	}

	/**
	 * Removes the list type entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the list type entry
	 * @return the list type entry that was removed
	 * @throws NoSuchListTypeEntryException if a list type entry with the primary key could not be found
	 */
	@Override
	public ListTypeEntry remove(Serializable primaryKey)
		throws NoSuchListTypeEntryException {

		Session session = null;

		try {
			session = openSession();

			ListTypeEntry listTypeEntry = (ListTypeEntry)session.get(
				ListTypeEntryImpl.class, primaryKey);

			if (listTypeEntry == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchListTypeEntryException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(listTypeEntry);
		}
		catch (NoSuchListTypeEntryException noSuchEntityException) {
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
	protected ListTypeEntry removeImpl(ListTypeEntry listTypeEntry) {
		Session session = null;

		try {
			session = openSession();

			if (!session.contains(listTypeEntry)) {
				listTypeEntry = (ListTypeEntry)session.get(
					ListTypeEntryImpl.class, listTypeEntry.getPrimaryKeyObj());
			}

			if (listTypeEntry != null) {
				session.delete(listTypeEntry);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (listTypeEntry != null) {
			clearCache(listTypeEntry);
		}

		return listTypeEntry;
	}

	@Override
	public ListTypeEntry updateImpl(ListTypeEntry listTypeEntry) {
		boolean isNew = listTypeEntry.isNew();

		if (!(listTypeEntry instanceof ListTypeEntryModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(listTypeEntry.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					listTypeEntry);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in listTypeEntry proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom ListTypeEntry implementation " +
					listTypeEntry.getClass());
		}

		ListTypeEntryModelImpl listTypeEntryModelImpl =
			(ListTypeEntryModelImpl)listTypeEntry;

		if (Validator.isNull(listTypeEntry.getUuid())) {
			String uuid = _portalUUID.generate();

			listTypeEntry.setUuid(uuid);
		}

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Date date = new Date();

		if (isNew && (listTypeEntry.getCreateDate() == null)) {
			if (serviceContext == null) {
				listTypeEntry.setCreateDate(date);
			}
			else {
				listTypeEntry.setCreateDate(serviceContext.getCreateDate(date));
			}
		}

		if (!listTypeEntryModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				listTypeEntry.setModifiedDate(date);
			}
			else {
				listTypeEntry.setModifiedDate(
					serviceContext.getModifiedDate(date));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (isNew) {
				session.save(listTypeEntry);
			}
			else {
				listTypeEntry = (ListTypeEntry)session.merge(listTypeEntry);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		entityCache.putResult(
			ListTypeEntryImpl.class, listTypeEntryModelImpl, false, true);

		cacheUniqueFindersCache(listTypeEntryModelImpl);

		if (isNew) {
			listTypeEntry.setNew(false);
		}

		listTypeEntry.resetOriginalValues();

		return listTypeEntry;
	}

	/**
	 * Returns the list type entry with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the list type entry
	 * @return the list type entry
	 * @throws NoSuchListTypeEntryException if a list type entry with the primary key could not be found
	 */
	@Override
	public ListTypeEntry findByPrimaryKey(Serializable primaryKey)
		throws NoSuchListTypeEntryException {

		ListTypeEntry listTypeEntry = fetchByPrimaryKey(primaryKey);

		if (listTypeEntry == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchListTypeEntryException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return listTypeEntry;
	}

	/**
	 * Returns the list type entry with the primary key or throws a <code>NoSuchListTypeEntryException</code> if it could not be found.
	 *
	 * @param listTypeEntryId the primary key of the list type entry
	 * @return the list type entry
	 * @throws NoSuchListTypeEntryException if a list type entry with the primary key could not be found
	 */
	@Override
	public ListTypeEntry findByPrimaryKey(long listTypeEntryId)
		throws NoSuchListTypeEntryException {

		return findByPrimaryKey((Serializable)listTypeEntryId);
	}

	/**
	 * Returns the list type entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param listTypeEntryId the primary key of the list type entry
	 * @return the list type entry, or <code>null</code> if a list type entry with the primary key could not be found
	 */
	@Override
	public ListTypeEntry fetchByPrimaryKey(long listTypeEntryId) {
		return fetchByPrimaryKey((Serializable)listTypeEntryId);
	}

	/**
	 * Returns all the list type entries.
	 *
	 * @return the list type entries
	 */
	@Override
	public List<ListTypeEntry> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the list type entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ListTypeEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of list type entries
	 * @param end the upper bound of the range of list type entries (not inclusive)
	 * @return the range of list type entries
	 */
	@Override
	public List<ListTypeEntry> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the list type entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ListTypeEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of list type entries
	 * @param end the upper bound of the range of list type entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of list type entries
	 */
	@Override
	public List<ListTypeEntry> findAll(
		int start, int end,
		OrderByComparator<ListTypeEntry> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the list type entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ListTypeEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of list type entries
	 * @param end the upper bound of the range of list type entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of list type entries
	 */
	@Override
	public List<ListTypeEntry> findAll(
		int start, int end, OrderByComparator<ListTypeEntry> orderByComparator,
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

		List<ListTypeEntry> list = null;

		if (useFinderCache) {
			list = (List<ListTypeEntry>)finderCache.getResult(
				finderPath, finderArgs);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_LISTTYPEENTRY);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_LISTTYPEENTRY;

				sql = sql.concat(ListTypeEntryModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<ListTypeEntry>)QueryUtil.list(
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
	 * Removes all the list type entries from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (ListTypeEntry listTypeEntry : findAll()) {
			remove(listTypeEntry);
		}
	}

	/**
	 * Returns the number of list type entries.
	 *
	 * @return the number of list type entries
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(
			_finderPathCountAll, FINDER_ARGS_EMPTY);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(_SQL_COUNT_LISTTYPEENTRY);

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
		return "listTypeEntryId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_LISTTYPEENTRY;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return ListTypeEntryModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the list type entry persistence.
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

		_finderPathWithPaginationFindByListTypeEntryId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByListTypeEntryId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"listTypeEntryId"}, true);

		_finderPathWithoutPaginationFindByListTypeEntryId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByListTypeEntryId",
			new String[] {Long.class.getName()},
			new String[] {"listTypeEntryId"}, true);

		_finderPathCountByListTypeEntryId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByListTypeEntryId",
			new String[] {Long.class.getName()},
			new String[] {"listTypeEntryId"}, false);

		_finderPathWithPaginationCountByListTypeEntryId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "countByListTypeEntryId",
			new String[] {Long.class.getName()},
			new String[] {"listTypeEntryId"}, false);

		_finderPathWithPaginationFindByListTypeDefinitionId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByListTypeDefinitionId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"listTypeDefinitionId"}, true);

		_finderPathWithoutPaginationFindByListTypeDefinitionId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByListTypeDefinitionId", new String[] {Long.class.getName()},
			new String[] {"listTypeDefinitionId"}, true);

		_finderPathCountByListTypeDefinitionId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByListTypeDefinitionId", new String[] {Long.class.getName()},
			new String[] {"listTypeDefinitionId"}, false);

		_finderPathFetchByLTDI_K = new FinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByLTDI_K",
			new String[] {Long.class.getName(), String.class.getName()},
			new String[] {"listTypeDefinitionId", "key_"}, true);

		_finderPathCountByLTDI_K = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByLTDI_K",
			new String[] {Long.class.getName(), String.class.getName()},
			new String[] {"listTypeDefinitionId", "key_"}, false);

		_setListTypeEntryUtilPersistence(this);
	}

	@Deactivate
	public void deactivate() {
		_setListTypeEntryUtilPersistence(null);

		entityCache.removeCache(ListTypeEntryImpl.class.getName());
	}

	private void _setListTypeEntryUtilPersistence(
		ListTypeEntryPersistence listTypeEntryPersistence) {

		try {
			Field field = ListTypeEntryUtil.class.getDeclaredField(
				"_persistence");

			field.setAccessible(true);

			field.set(null, listTypeEntryPersistence);
		}
		catch (ReflectiveOperationException reflectiveOperationException) {
			throw new RuntimeException(reflectiveOperationException);
		}
	}

	@Override
	@Reference(
		target = ListTypePersistenceConstants.SERVICE_CONFIGURATION_FILTER,
		unbind = "-"
	)
	public void setConfiguration(Configuration configuration) {
	}

	@Override
	@Reference(
		target = ListTypePersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setDataSource(DataSource dataSource) {
		super.setDataSource(dataSource);
	}

	@Override
	@Reference(
		target = ListTypePersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setSessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	@Reference
	protected EntityCache entityCache;

	@Reference
	protected FinderCache finderCache;

	private static final String _SQL_SELECT_LISTTYPEENTRY =
		"SELECT listTypeEntry FROM ListTypeEntry listTypeEntry";

	private static final String _SQL_SELECT_LISTTYPEENTRY_WHERE =
		"SELECT listTypeEntry FROM ListTypeEntry listTypeEntry WHERE ";

	private static final String _SQL_COUNT_LISTTYPEENTRY =
		"SELECT COUNT(listTypeEntry) FROM ListTypeEntry listTypeEntry";

	private static final String _SQL_COUNT_LISTTYPEENTRY_WHERE =
		"SELECT COUNT(listTypeEntry) FROM ListTypeEntry listTypeEntry WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS = "listTypeEntry.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No ListTypeEntry exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No ListTypeEntry exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		ListTypeEntryPersistenceImpl.class);

	private static final Set<String> _badColumnNames = SetUtil.fromArray(
		new String[] {"uuid", "key", "type"});

	@Override
	protected FinderCache getFinderCache() {
		return finderCache;
	}

	@Reference
	private PortalUUID _portalUUID;

	@Reference
	private ListTypeEntryModelArgumentsResolver
		_listTypeEntryModelArgumentsResolver;

}