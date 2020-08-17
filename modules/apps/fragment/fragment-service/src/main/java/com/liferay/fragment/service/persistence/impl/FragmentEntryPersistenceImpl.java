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

package com.liferay.fragment.service.persistence.impl;

import com.liferay.fragment.exception.NoSuchEntryException;
import com.liferay.fragment.model.FragmentEntry;
import com.liferay.fragment.model.FragmentEntryTable;
import com.liferay.fragment.model.impl.FragmentEntryImpl;
import com.liferay.fragment.model.impl.FragmentEntryModelImpl;
import com.liferay.fragment.service.persistence.FragmentEntryPersistence;
import com.liferay.fragment.service.persistence.impl.constants.FragmentPersistenceConstants;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.change.tracking.CTColumnResolutionType;
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
import com.liferay.portal.kernel.service.persistence.change.tracking.helper.CTPersistenceHelper;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;

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

import javax.sql.DataSource;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * The persistence implementation for the fragment entry service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
@Component(service = FragmentEntryPersistence.class)
public class FragmentEntryPersistenceImpl
	extends BasePersistenceImpl<FragmentEntry>
	implements FragmentEntryPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>FragmentEntryUtil</code> to access the fragment entry persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		FragmentEntryImpl.class.getName();

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
	 * Returns all the fragment entries where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching fragment entries
	 */
	@Override
	public List<FragmentEntry> findByUuid(String uuid) {
		return findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the fragment entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of fragment entries
	 * @param end the upper bound of the range of fragment entries (not inclusive)
	 * @return the range of matching fragment entries
	 */
	@Override
	public List<FragmentEntry> findByUuid(String uuid, int start, int end) {
		return findByUuid(uuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the fragment entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of fragment entries
	 * @param end the upper bound of the range of fragment entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching fragment entries
	 */
	@Override
	public List<FragmentEntry> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<FragmentEntry> orderByComparator) {

		return findByUuid(uuid, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the fragment entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of fragment entries
	 * @param end the upper bound of the range of fragment entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching fragment entries
	 */
	@Override
	public List<FragmentEntry> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<FragmentEntry> orderByComparator,
		boolean useFinderCache) {

		uuid = Objects.toString(uuid, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			FragmentEntry.class);

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

		List<FragmentEntry> list = null;

		if (useFinderCache && productionMode) {
			list = (List<FragmentEntry>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (FragmentEntry fragmentEntry : list) {
					if (!uuid.equals(fragmentEntry.getUuid())) {
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

			sb.append(_SQL_SELECT_FRAGMENTENTRY_WHERE);

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
				sb.append(FragmentEntryModelImpl.ORDER_BY_JPQL);
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

				list = (List<FragmentEntry>)QueryUtil.list(
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
	 * Returns the first fragment entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment entry
	 * @throws NoSuchEntryException if a matching fragment entry could not be found
	 */
	@Override
	public FragmentEntry findByUuid_First(
			String uuid, OrderByComparator<FragmentEntry> orderByComparator)
		throws NoSuchEntryException {

		FragmentEntry fragmentEntry = fetchByUuid_First(
			uuid, orderByComparator);

		if (fragmentEntry != null) {
			return fragmentEntry;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append("}");

		throw new NoSuchEntryException(sb.toString());
	}

	/**
	 * Returns the first fragment entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment entry, or <code>null</code> if a matching fragment entry could not be found
	 */
	@Override
	public FragmentEntry fetchByUuid_First(
		String uuid, OrderByComparator<FragmentEntry> orderByComparator) {

		List<FragmentEntry> list = findByUuid(uuid, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last fragment entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching fragment entry
	 * @throws NoSuchEntryException if a matching fragment entry could not be found
	 */
	@Override
	public FragmentEntry findByUuid_Last(
			String uuid, OrderByComparator<FragmentEntry> orderByComparator)
		throws NoSuchEntryException {

		FragmentEntry fragmentEntry = fetchByUuid_Last(uuid, orderByComparator);

		if (fragmentEntry != null) {
			return fragmentEntry;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append("}");

		throw new NoSuchEntryException(sb.toString());
	}

	/**
	 * Returns the last fragment entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching fragment entry, or <code>null</code> if a matching fragment entry could not be found
	 */
	@Override
	public FragmentEntry fetchByUuid_Last(
		String uuid, OrderByComparator<FragmentEntry> orderByComparator) {

		int count = countByUuid(uuid);

		if (count == 0) {
			return null;
		}

		List<FragmentEntry> list = findByUuid(
			uuid, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the fragment entries before and after the current fragment entry in the ordered set where uuid = &#63;.
	 *
	 * @param fragmentEntryId the primary key of the current fragment entry
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next fragment entry
	 * @throws NoSuchEntryException if a fragment entry with the primary key could not be found
	 */
	@Override
	public FragmentEntry[] findByUuid_PrevAndNext(
			long fragmentEntryId, String uuid,
			OrderByComparator<FragmentEntry> orderByComparator)
		throws NoSuchEntryException {

		uuid = Objects.toString(uuid, "");

		FragmentEntry fragmentEntry = findByPrimaryKey(fragmentEntryId);

		Session session = null;

		try {
			session = openSession();

			FragmentEntry[] array = new FragmentEntryImpl[3];

			array[0] = getByUuid_PrevAndNext(
				session, fragmentEntry, uuid, orderByComparator, true);

			array[1] = fragmentEntry;

			array[2] = getByUuid_PrevAndNext(
				session, fragmentEntry, uuid, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected FragmentEntry getByUuid_PrevAndNext(
		Session session, FragmentEntry fragmentEntry, String uuid,
		OrderByComparator<FragmentEntry> orderByComparator, boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(3);
		}

		sb.append(_SQL_SELECT_FRAGMENTENTRY_WHERE);

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
			sb.append(FragmentEntryModelImpl.ORDER_BY_JPQL);
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
						fragmentEntry)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<FragmentEntry> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the fragment entries where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	@Override
	public void removeByUuid(String uuid) {
		for (FragmentEntry fragmentEntry :
				findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(fragmentEntry);
		}
	}

	/**
	 * Returns the number of fragment entries where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching fragment entries
	 */
	@Override
	public int countByUuid(String uuid) {
		uuid = Objects.toString(uuid, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			FragmentEntry.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByUuid;

			finderArgs = new Object[] {uuid};

			count = (Long)finderCache.getResult(finderPath, finderArgs, this);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_FRAGMENTENTRY_WHERE);

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
		"fragmentEntry.uuid = ?";

	private static final String _FINDER_COLUMN_UUID_UUID_3 =
		"(fragmentEntry.uuid IS NULL OR fragmentEntry.uuid = '')";

	private FinderPath _finderPathWithPaginationFindByUuid_Head;
	private FinderPath _finderPathWithoutPaginationFindByUuid_Head;
	private FinderPath _finderPathCountByUuid_Head;

	/**
	 * Returns all the fragment entries where uuid = &#63; and head = &#63;.
	 *
	 * @param uuid the uuid
	 * @param head the head
	 * @return the matching fragment entries
	 */
	@Override
	public List<FragmentEntry> findByUuid_Head(String uuid, boolean head) {
		return findByUuid_Head(
			uuid, head, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the fragment entries where uuid = &#63; and head = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param head the head
	 * @param start the lower bound of the range of fragment entries
	 * @param end the upper bound of the range of fragment entries (not inclusive)
	 * @return the range of matching fragment entries
	 */
	@Override
	public List<FragmentEntry> findByUuid_Head(
		String uuid, boolean head, int start, int end) {

		return findByUuid_Head(uuid, head, start, end, null);
	}

	/**
	 * Returns an ordered range of all the fragment entries where uuid = &#63; and head = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param head the head
	 * @param start the lower bound of the range of fragment entries
	 * @param end the upper bound of the range of fragment entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching fragment entries
	 */
	@Override
	public List<FragmentEntry> findByUuid_Head(
		String uuid, boolean head, int start, int end,
		OrderByComparator<FragmentEntry> orderByComparator) {

		return findByUuid_Head(uuid, head, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the fragment entries where uuid = &#63; and head = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param head the head
	 * @param start the lower bound of the range of fragment entries
	 * @param end the upper bound of the range of fragment entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching fragment entries
	 */
	@Override
	public List<FragmentEntry> findByUuid_Head(
		String uuid, boolean head, int start, int end,
		OrderByComparator<FragmentEntry> orderByComparator,
		boolean useFinderCache) {

		uuid = Objects.toString(uuid, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			FragmentEntry.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache && productionMode) {
				finderPath = _finderPathWithoutPaginationFindByUuid_Head;
				finderArgs = new Object[] {uuid, head};
			}
		}
		else if (useFinderCache && productionMode) {
			finderPath = _finderPathWithPaginationFindByUuid_Head;
			finderArgs = new Object[] {
				uuid, head, start, end, orderByComparator
			};
		}

		List<FragmentEntry> list = null;

		if (useFinderCache && productionMode) {
			list = (List<FragmentEntry>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (FragmentEntry fragmentEntry : list) {
					if (!uuid.equals(fragmentEntry.getUuid()) ||
						(head != fragmentEntry.isHead())) {

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

			sb.append(_SQL_SELECT_FRAGMENTENTRY_WHERE);

			boolean bindUuid = false;

			if (uuid.isEmpty()) {
				sb.append(_FINDER_COLUMN_UUID_HEAD_UUID_3);
			}
			else {
				bindUuid = true;

				sb.append(_FINDER_COLUMN_UUID_HEAD_UUID_2);
			}

			sb.append(_FINDER_COLUMN_UUID_HEAD_HEAD_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(FragmentEntryModelImpl.ORDER_BY_JPQL);
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

				queryPos.add(head);

				list = (List<FragmentEntry>)QueryUtil.list(
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
	 * Returns the first fragment entry in the ordered set where uuid = &#63; and head = &#63;.
	 *
	 * @param uuid the uuid
	 * @param head the head
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment entry
	 * @throws NoSuchEntryException if a matching fragment entry could not be found
	 */
	@Override
	public FragmentEntry findByUuid_Head_First(
			String uuid, boolean head,
			OrderByComparator<FragmentEntry> orderByComparator)
		throws NoSuchEntryException {

		FragmentEntry fragmentEntry = fetchByUuid_Head_First(
			uuid, head, orderByComparator);

		if (fragmentEntry != null) {
			return fragmentEntry;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append(", head=");
		sb.append(head);

		sb.append("}");

		throw new NoSuchEntryException(sb.toString());
	}

	/**
	 * Returns the first fragment entry in the ordered set where uuid = &#63; and head = &#63;.
	 *
	 * @param uuid the uuid
	 * @param head the head
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment entry, or <code>null</code> if a matching fragment entry could not be found
	 */
	@Override
	public FragmentEntry fetchByUuid_Head_First(
		String uuid, boolean head,
		OrderByComparator<FragmentEntry> orderByComparator) {

		List<FragmentEntry> list = findByUuid_Head(
			uuid, head, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last fragment entry in the ordered set where uuid = &#63; and head = &#63;.
	 *
	 * @param uuid the uuid
	 * @param head the head
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching fragment entry
	 * @throws NoSuchEntryException if a matching fragment entry could not be found
	 */
	@Override
	public FragmentEntry findByUuid_Head_Last(
			String uuid, boolean head,
			OrderByComparator<FragmentEntry> orderByComparator)
		throws NoSuchEntryException {

		FragmentEntry fragmentEntry = fetchByUuid_Head_Last(
			uuid, head, orderByComparator);

		if (fragmentEntry != null) {
			return fragmentEntry;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append(", head=");
		sb.append(head);

		sb.append("}");

		throw new NoSuchEntryException(sb.toString());
	}

	/**
	 * Returns the last fragment entry in the ordered set where uuid = &#63; and head = &#63;.
	 *
	 * @param uuid the uuid
	 * @param head the head
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching fragment entry, or <code>null</code> if a matching fragment entry could not be found
	 */
	@Override
	public FragmentEntry fetchByUuid_Head_Last(
		String uuid, boolean head,
		OrderByComparator<FragmentEntry> orderByComparator) {

		int count = countByUuid_Head(uuid, head);

		if (count == 0) {
			return null;
		}

		List<FragmentEntry> list = findByUuid_Head(
			uuid, head, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the fragment entries before and after the current fragment entry in the ordered set where uuid = &#63; and head = &#63;.
	 *
	 * @param fragmentEntryId the primary key of the current fragment entry
	 * @param uuid the uuid
	 * @param head the head
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next fragment entry
	 * @throws NoSuchEntryException if a fragment entry with the primary key could not be found
	 */
	@Override
	public FragmentEntry[] findByUuid_Head_PrevAndNext(
			long fragmentEntryId, String uuid, boolean head,
			OrderByComparator<FragmentEntry> orderByComparator)
		throws NoSuchEntryException {

		uuid = Objects.toString(uuid, "");

		FragmentEntry fragmentEntry = findByPrimaryKey(fragmentEntryId);

		Session session = null;

		try {
			session = openSession();

			FragmentEntry[] array = new FragmentEntryImpl[3];

			array[0] = getByUuid_Head_PrevAndNext(
				session, fragmentEntry, uuid, head, orderByComparator, true);

			array[1] = fragmentEntry;

			array[2] = getByUuid_Head_PrevAndNext(
				session, fragmentEntry, uuid, head, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected FragmentEntry getByUuid_Head_PrevAndNext(
		Session session, FragmentEntry fragmentEntry, String uuid, boolean head,
		OrderByComparator<FragmentEntry> orderByComparator, boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				5 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(4);
		}

		sb.append(_SQL_SELECT_FRAGMENTENTRY_WHERE);

		boolean bindUuid = false;

		if (uuid.isEmpty()) {
			sb.append(_FINDER_COLUMN_UUID_HEAD_UUID_3);
		}
		else {
			bindUuid = true;

			sb.append(_FINDER_COLUMN_UUID_HEAD_UUID_2);
		}

		sb.append(_FINDER_COLUMN_UUID_HEAD_HEAD_2);

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
			sb.append(FragmentEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		if (bindUuid) {
			queryPos.add(uuid);
		}

		queryPos.add(head);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						fragmentEntry)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<FragmentEntry> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the fragment entries where uuid = &#63; and head = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param head the head
	 */
	@Override
	public void removeByUuid_Head(String uuid, boolean head) {
		for (FragmentEntry fragmentEntry :
				findByUuid_Head(
					uuid, head, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(fragmentEntry);
		}
	}

	/**
	 * Returns the number of fragment entries where uuid = &#63; and head = &#63;.
	 *
	 * @param uuid the uuid
	 * @param head the head
	 * @return the number of matching fragment entries
	 */
	@Override
	public int countByUuid_Head(String uuid, boolean head) {
		uuid = Objects.toString(uuid, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			FragmentEntry.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByUuid_Head;

			finderArgs = new Object[] {uuid, head};

			count = (Long)finderCache.getResult(finderPath, finderArgs, this);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_FRAGMENTENTRY_WHERE);

			boolean bindUuid = false;

			if (uuid.isEmpty()) {
				sb.append(_FINDER_COLUMN_UUID_HEAD_UUID_3);
			}
			else {
				bindUuid = true;

				sb.append(_FINDER_COLUMN_UUID_HEAD_UUID_2);
			}

			sb.append(_FINDER_COLUMN_UUID_HEAD_HEAD_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindUuid) {
					queryPos.add(uuid);
				}

				queryPos.add(head);

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

	private static final String _FINDER_COLUMN_UUID_HEAD_UUID_2 =
		"fragmentEntry.uuid = ? AND ";

	private static final String _FINDER_COLUMN_UUID_HEAD_UUID_3 =
		"(fragmentEntry.uuid IS NULL OR fragmentEntry.uuid = '') AND ";

	private static final String _FINDER_COLUMN_UUID_HEAD_HEAD_2 =
		"fragmentEntry.head = ?";

	private FinderPath _finderPathWithPaginationFindByUUID_G;
	private FinderPath _finderPathWithoutPaginationFindByUUID_G;
	private FinderPath _finderPathCountByUUID_G;

	/**
	 * Returns all the fragment entries where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching fragment entries
	 */
	@Override
	public List<FragmentEntry> findByUUID_G(String uuid, long groupId) {
		return findByUUID_G(
			uuid, groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the fragment entries where uuid = &#63; and groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param start the lower bound of the range of fragment entries
	 * @param end the upper bound of the range of fragment entries (not inclusive)
	 * @return the range of matching fragment entries
	 */
	@Override
	public List<FragmentEntry> findByUUID_G(
		String uuid, long groupId, int start, int end) {

		return findByUUID_G(uuid, groupId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the fragment entries where uuid = &#63; and groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param start the lower bound of the range of fragment entries
	 * @param end the upper bound of the range of fragment entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching fragment entries
	 */
	@Override
	public List<FragmentEntry> findByUUID_G(
		String uuid, long groupId, int start, int end,
		OrderByComparator<FragmentEntry> orderByComparator) {

		return findByUUID_G(uuid, groupId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the fragment entries where uuid = &#63; and groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param start the lower bound of the range of fragment entries
	 * @param end the upper bound of the range of fragment entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching fragment entries
	 */
	@Override
	public List<FragmentEntry> findByUUID_G(
		String uuid, long groupId, int start, int end,
		OrderByComparator<FragmentEntry> orderByComparator,
		boolean useFinderCache) {

		uuid = Objects.toString(uuid, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			FragmentEntry.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache && productionMode) {
				finderPath = _finderPathWithoutPaginationFindByUUID_G;
				finderArgs = new Object[] {uuid, groupId};
			}
		}
		else if (useFinderCache && productionMode) {
			finderPath = _finderPathWithPaginationFindByUUID_G;
			finderArgs = new Object[] {
				uuid, groupId, start, end, orderByComparator
			};
		}

		List<FragmentEntry> list = null;

		if (useFinderCache && productionMode) {
			list = (List<FragmentEntry>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (FragmentEntry fragmentEntry : list) {
					if (!uuid.equals(fragmentEntry.getUuid()) ||
						(groupId != fragmentEntry.getGroupId())) {

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

			sb.append(_SQL_SELECT_FRAGMENTENTRY_WHERE);

			boolean bindUuid = false;

			if (uuid.isEmpty()) {
				sb.append(_FINDER_COLUMN_UUID_G_UUID_3);
			}
			else {
				bindUuid = true;

				sb.append(_FINDER_COLUMN_UUID_G_UUID_2);
			}

			sb.append(_FINDER_COLUMN_UUID_G_GROUPID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(FragmentEntryModelImpl.ORDER_BY_JPQL);
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

				queryPos.add(groupId);

				list = (List<FragmentEntry>)QueryUtil.list(
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
	 * Returns the first fragment entry in the ordered set where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment entry
	 * @throws NoSuchEntryException if a matching fragment entry could not be found
	 */
	@Override
	public FragmentEntry findByUUID_G_First(
			String uuid, long groupId,
			OrderByComparator<FragmentEntry> orderByComparator)
		throws NoSuchEntryException {

		FragmentEntry fragmentEntry = fetchByUUID_G_First(
			uuid, groupId, orderByComparator);

		if (fragmentEntry != null) {
			return fragmentEntry;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append(", groupId=");
		sb.append(groupId);

		sb.append("}");

		throw new NoSuchEntryException(sb.toString());
	}

	/**
	 * Returns the first fragment entry in the ordered set where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment entry, or <code>null</code> if a matching fragment entry could not be found
	 */
	@Override
	public FragmentEntry fetchByUUID_G_First(
		String uuid, long groupId,
		OrderByComparator<FragmentEntry> orderByComparator) {

		List<FragmentEntry> list = findByUUID_G(
			uuid, groupId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last fragment entry in the ordered set where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching fragment entry
	 * @throws NoSuchEntryException if a matching fragment entry could not be found
	 */
	@Override
	public FragmentEntry findByUUID_G_Last(
			String uuid, long groupId,
			OrderByComparator<FragmentEntry> orderByComparator)
		throws NoSuchEntryException {

		FragmentEntry fragmentEntry = fetchByUUID_G_Last(
			uuid, groupId, orderByComparator);

		if (fragmentEntry != null) {
			return fragmentEntry;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append(", groupId=");
		sb.append(groupId);

		sb.append("}");

		throw new NoSuchEntryException(sb.toString());
	}

	/**
	 * Returns the last fragment entry in the ordered set where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching fragment entry, or <code>null</code> if a matching fragment entry could not be found
	 */
	@Override
	public FragmentEntry fetchByUUID_G_Last(
		String uuid, long groupId,
		OrderByComparator<FragmentEntry> orderByComparator) {

		int count = countByUUID_G(uuid, groupId);

		if (count == 0) {
			return null;
		}

		List<FragmentEntry> list = findByUUID_G(
			uuid, groupId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the fragment entries before and after the current fragment entry in the ordered set where uuid = &#63; and groupId = &#63;.
	 *
	 * @param fragmentEntryId the primary key of the current fragment entry
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next fragment entry
	 * @throws NoSuchEntryException if a fragment entry with the primary key could not be found
	 */
	@Override
	public FragmentEntry[] findByUUID_G_PrevAndNext(
			long fragmentEntryId, String uuid, long groupId,
			OrderByComparator<FragmentEntry> orderByComparator)
		throws NoSuchEntryException {

		uuid = Objects.toString(uuid, "");

		FragmentEntry fragmentEntry = findByPrimaryKey(fragmentEntryId);

		Session session = null;

		try {
			session = openSession();

			FragmentEntry[] array = new FragmentEntryImpl[3];

			array[0] = getByUUID_G_PrevAndNext(
				session, fragmentEntry, uuid, groupId, orderByComparator, true);

			array[1] = fragmentEntry;

			array[2] = getByUUID_G_PrevAndNext(
				session, fragmentEntry, uuid, groupId, orderByComparator,
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

	protected FragmentEntry getByUUID_G_PrevAndNext(
		Session session, FragmentEntry fragmentEntry, String uuid, long groupId,
		OrderByComparator<FragmentEntry> orderByComparator, boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				5 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(4);
		}

		sb.append(_SQL_SELECT_FRAGMENTENTRY_WHERE);

		boolean bindUuid = false;

		if (uuid.isEmpty()) {
			sb.append(_FINDER_COLUMN_UUID_G_UUID_3);
		}
		else {
			bindUuid = true;

			sb.append(_FINDER_COLUMN_UUID_G_UUID_2);
		}

		sb.append(_FINDER_COLUMN_UUID_G_GROUPID_2);

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
			sb.append(FragmentEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		if (bindUuid) {
			queryPos.add(uuid);
		}

		queryPos.add(groupId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						fragmentEntry)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<FragmentEntry> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the fragment entries where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 */
	@Override
	public void removeByUUID_G(String uuid, long groupId) {
		for (FragmentEntry fragmentEntry :
				findByUUID_G(
					uuid, groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(fragmentEntry);
		}
	}

	/**
	 * Returns the number of fragment entries where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching fragment entries
	 */
	@Override
	public int countByUUID_G(String uuid, long groupId) {
		uuid = Objects.toString(uuid, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			FragmentEntry.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByUUID_G;

			finderArgs = new Object[] {uuid, groupId};

			count = (Long)finderCache.getResult(finderPath, finderArgs, this);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_FRAGMENTENTRY_WHERE);

			boolean bindUuid = false;

			if (uuid.isEmpty()) {
				sb.append(_FINDER_COLUMN_UUID_G_UUID_3);
			}
			else {
				bindUuid = true;

				sb.append(_FINDER_COLUMN_UUID_G_UUID_2);
			}

			sb.append(_FINDER_COLUMN_UUID_G_GROUPID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindUuid) {
					queryPos.add(uuid);
				}

				queryPos.add(groupId);

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

	private static final String _FINDER_COLUMN_UUID_G_UUID_2 =
		"fragmentEntry.uuid = ? AND ";

	private static final String _FINDER_COLUMN_UUID_G_UUID_3 =
		"(fragmentEntry.uuid IS NULL OR fragmentEntry.uuid = '') AND ";

	private static final String _FINDER_COLUMN_UUID_G_GROUPID_2 =
		"fragmentEntry.groupId = ?";

	private FinderPath _finderPathFetchByUUID_G_Head;
	private FinderPath _finderPathCountByUUID_G_Head;

	/**
	 * Returns the fragment entry where uuid = &#63; and groupId = &#63; and head = &#63; or throws a <code>NoSuchEntryException</code> if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param head the head
	 * @return the matching fragment entry
	 * @throws NoSuchEntryException if a matching fragment entry could not be found
	 */
	@Override
	public FragmentEntry findByUUID_G_Head(
			String uuid, long groupId, boolean head)
		throws NoSuchEntryException {

		FragmentEntry fragmentEntry = fetchByUUID_G_Head(uuid, groupId, head);

		if (fragmentEntry == null) {
			StringBundler sb = new StringBundler(8);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("uuid=");
			sb.append(uuid);

			sb.append(", groupId=");
			sb.append(groupId);

			sb.append(", head=");
			sb.append(head);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchEntryException(sb.toString());
		}

		return fragmentEntry;
	}

	/**
	 * Returns the fragment entry where uuid = &#63; and groupId = &#63; and head = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param head the head
	 * @return the matching fragment entry, or <code>null</code> if a matching fragment entry could not be found
	 */
	@Override
	public FragmentEntry fetchByUUID_G_Head(
		String uuid, long groupId, boolean head) {

		return fetchByUUID_G_Head(uuid, groupId, head, true);
	}

	/**
	 * Returns the fragment entry where uuid = &#63; and groupId = &#63; and head = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param head the head
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching fragment entry, or <code>null</code> if a matching fragment entry could not be found
	 */
	@Override
	public FragmentEntry fetchByUUID_G_Head(
		String uuid, long groupId, boolean head, boolean useFinderCache) {

		uuid = Objects.toString(uuid, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			FragmentEntry.class);

		Object[] finderArgs = null;

		if (useFinderCache && productionMode) {
			finderArgs = new Object[] {uuid, groupId, head};
		}

		Object result = null;

		if (useFinderCache && productionMode) {
			result = finderCache.getResult(
				_finderPathFetchByUUID_G_Head, finderArgs, this);
		}

		if (result instanceof FragmentEntry) {
			FragmentEntry fragmentEntry = (FragmentEntry)result;

			if (!Objects.equals(uuid, fragmentEntry.getUuid()) ||
				(groupId != fragmentEntry.getGroupId()) ||
				(head != fragmentEntry.isHead())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(5);

			sb.append(_SQL_SELECT_FRAGMENTENTRY_WHERE);

			boolean bindUuid = false;

			if (uuid.isEmpty()) {
				sb.append(_FINDER_COLUMN_UUID_G_HEAD_UUID_3);
			}
			else {
				bindUuid = true;

				sb.append(_FINDER_COLUMN_UUID_G_HEAD_UUID_2);
			}

			sb.append(_FINDER_COLUMN_UUID_G_HEAD_GROUPID_2);

			sb.append(_FINDER_COLUMN_UUID_G_HEAD_HEAD_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindUuid) {
					queryPos.add(uuid);
				}

				queryPos.add(groupId);

				queryPos.add(head);

				List<FragmentEntry> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache && productionMode) {
						finderCache.putResult(
							_finderPathFetchByUUID_G_Head, finderArgs, list);
					}
				}
				else {
					FragmentEntry fragmentEntry = list.get(0);

					result = fragmentEntry;

					cacheResult(fragmentEntry);
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
			return (FragmentEntry)result;
		}
	}

	/**
	 * Removes the fragment entry where uuid = &#63; and groupId = &#63; and head = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param head the head
	 * @return the fragment entry that was removed
	 */
	@Override
	public FragmentEntry removeByUUID_G_Head(
			String uuid, long groupId, boolean head)
		throws NoSuchEntryException {

		FragmentEntry fragmentEntry = findByUUID_G_Head(uuid, groupId, head);

		return remove(fragmentEntry);
	}

	/**
	 * Returns the number of fragment entries where uuid = &#63; and groupId = &#63; and head = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param head the head
	 * @return the number of matching fragment entries
	 */
	@Override
	public int countByUUID_G_Head(String uuid, long groupId, boolean head) {
		uuid = Objects.toString(uuid, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			FragmentEntry.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByUUID_G_Head;

			finderArgs = new Object[] {uuid, groupId, head};

			count = (Long)finderCache.getResult(finderPath, finderArgs, this);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_COUNT_FRAGMENTENTRY_WHERE);

			boolean bindUuid = false;

			if (uuid.isEmpty()) {
				sb.append(_FINDER_COLUMN_UUID_G_HEAD_UUID_3);
			}
			else {
				bindUuid = true;

				sb.append(_FINDER_COLUMN_UUID_G_HEAD_UUID_2);
			}

			sb.append(_FINDER_COLUMN_UUID_G_HEAD_GROUPID_2);

			sb.append(_FINDER_COLUMN_UUID_G_HEAD_HEAD_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindUuid) {
					queryPos.add(uuid);
				}

				queryPos.add(groupId);

				queryPos.add(head);

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

	private static final String _FINDER_COLUMN_UUID_G_HEAD_UUID_2 =
		"fragmentEntry.uuid = ? AND ";

	private static final String _FINDER_COLUMN_UUID_G_HEAD_UUID_3 =
		"(fragmentEntry.uuid IS NULL OR fragmentEntry.uuid = '') AND ";

	private static final String _FINDER_COLUMN_UUID_G_HEAD_GROUPID_2 =
		"fragmentEntry.groupId = ? AND ";

	private static final String _FINDER_COLUMN_UUID_G_HEAD_HEAD_2 =
		"fragmentEntry.head = ?";

	private FinderPath _finderPathWithPaginationFindByUuid_C;
	private FinderPath _finderPathWithoutPaginationFindByUuid_C;
	private FinderPath _finderPathCountByUuid_C;

	/**
	 * Returns all the fragment entries where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching fragment entries
	 */
	@Override
	public List<FragmentEntry> findByUuid_C(String uuid, long companyId) {
		return findByUuid_C(
			uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the fragment entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of fragment entries
	 * @param end the upper bound of the range of fragment entries (not inclusive)
	 * @return the range of matching fragment entries
	 */
	@Override
	public List<FragmentEntry> findByUuid_C(
		String uuid, long companyId, int start, int end) {

		return findByUuid_C(uuid, companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the fragment entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of fragment entries
	 * @param end the upper bound of the range of fragment entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching fragment entries
	 */
	@Override
	public List<FragmentEntry> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<FragmentEntry> orderByComparator) {

		return findByUuid_C(
			uuid, companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the fragment entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of fragment entries
	 * @param end the upper bound of the range of fragment entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching fragment entries
	 */
	@Override
	public List<FragmentEntry> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<FragmentEntry> orderByComparator,
		boolean useFinderCache) {

		uuid = Objects.toString(uuid, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			FragmentEntry.class);

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

		List<FragmentEntry> list = null;

		if (useFinderCache && productionMode) {
			list = (List<FragmentEntry>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (FragmentEntry fragmentEntry : list) {
					if (!uuid.equals(fragmentEntry.getUuid()) ||
						(companyId != fragmentEntry.getCompanyId())) {

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

			sb.append(_SQL_SELECT_FRAGMENTENTRY_WHERE);

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
				sb.append(FragmentEntryModelImpl.ORDER_BY_JPQL);
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

				list = (List<FragmentEntry>)QueryUtil.list(
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
	 * Returns the first fragment entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment entry
	 * @throws NoSuchEntryException if a matching fragment entry could not be found
	 */
	@Override
	public FragmentEntry findByUuid_C_First(
			String uuid, long companyId,
			OrderByComparator<FragmentEntry> orderByComparator)
		throws NoSuchEntryException {

		FragmentEntry fragmentEntry = fetchByUuid_C_First(
			uuid, companyId, orderByComparator);

		if (fragmentEntry != null) {
			return fragmentEntry;
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
	 * Returns the first fragment entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment entry, or <code>null</code> if a matching fragment entry could not be found
	 */
	@Override
	public FragmentEntry fetchByUuid_C_First(
		String uuid, long companyId,
		OrderByComparator<FragmentEntry> orderByComparator) {

		List<FragmentEntry> list = findByUuid_C(
			uuid, companyId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last fragment entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching fragment entry
	 * @throws NoSuchEntryException if a matching fragment entry could not be found
	 */
	@Override
	public FragmentEntry findByUuid_C_Last(
			String uuid, long companyId,
			OrderByComparator<FragmentEntry> orderByComparator)
		throws NoSuchEntryException {

		FragmentEntry fragmentEntry = fetchByUuid_C_Last(
			uuid, companyId, orderByComparator);

		if (fragmentEntry != null) {
			return fragmentEntry;
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
	 * Returns the last fragment entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching fragment entry, or <code>null</code> if a matching fragment entry could not be found
	 */
	@Override
	public FragmentEntry fetchByUuid_C_Last(
		String uuid, long companyId,
		OrderByComparator<FragmentEntry> orderByComparator) {

		int count = countByUuid_C(uuid, companyId);

		if (count == 0) {
			return null;
		}

		List<FragmentEntry> list = findByUuid_C(
			uuid, companyId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the fragment entries before and after the current fragment entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param fragmentEntryId the primary key of the current fragment entry
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next fragment entry
	 * @throws NoSuchEntryException if a fragment entry with the primary key could not be found
	 */
	@Override
	public FragmentEntry[] findByUuid_C_PrevAndNext(
			long fragmentEntryId, String uuid, long companyId,
			OrderByComparator<FragmentEntry> orderByComparator)
		throws NoSuchEntryException {

		uuid = Objects.toString(uuid, "");

		FragmentEntry fragmentEntry = findByPrimaryKey(fragmentEntryId);

		Session session = null;

		try {
			session = openSession();

			FragmentEntry[] array = new FragmentEntryImpl[3];

			array[0] = getByUuid_C_PrevAndNext(
				session, fragmentEntry, uuid, companyId, orderByComparator,
				true);

			array[1] = fragmentEntry;

			array[2] = getByUuid_C_PrevAndNext(
				session, fragmentEntry, uuid, companyId, orderByComparator,
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

	protected FragmentEntry getByUuid_C_PrevAndNext(
		Session session, FragmentEntry fragmentEntry, String uuid,
		long companyId, OrderByComparator<FragmentEntry> orderByComparator,
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

		sb.append(_SQL_SELECT_FRAGMENTENTRY_WHERE);

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
			sb.append(FragmentEntryModelImpl.ORDER_BY_JPQL);
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
						fragmentEntry)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<FragmentEntry> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the fragment entries where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	@Override
	public void removeByUuid_C(String uuid, long companyId) {
		for (FragmentEntry fragmentEntry :
				findByUuid_C(
					uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(fragmentEntry);
		}
	}

	/**
	 * Returns the number of fragment entries where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching fragment entries
	 */
	@Override
	public int countByUuid_C(String uuid, long companyId) {
		uuid = Objects.toString(uuid, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			FragmentEntry.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByUuid_C;

			finderArgs = new Object[] {uuid, companyId};

			count = (Long)finderCache.getResult(finderPath, finderArgs, this);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_FRAGMENTENTRY_WHERE);

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
		"fragmentEntry.uuid = ? AND ";

	private static final String _FINDER_COLUMN_UUID_C_UUID_3 =
		"(fragmentEntry.uuid IS NULL OR fragmentEntry.uuid = '') AND ";

	private static final String _FINDER_COLUMN_UUID_C_COMPANYID_2 =
		"fragmentEntry.companyId = ?";

	private FinderPath _finderPathWithPaginationFindByUuid_C_Head;
	private FinderPath _finderPathWithoutPaginationFindByUuid_C_Head;
	private FinderPath _finderPathCountByUuid_C_Head;

	/**
	 * Returns all the fragment entries where uuid = &#63; and companyId = &#63; and head = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param head the head
	 * @return the matching fragment entries
	 */
	@Override
	public List<FragmentEntry> findByUuid_C_Head(
		String uuid, long companyId, boolean head) {

		return findByUuid_C_Head(
			uuid, companyId, head, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the fragment entries where uuid = &#63; and companyId = &#63; and head = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param head the head
	 * @param start the lower bound of the range of fragment entries
	 * @param end the upper bound of the range of fragment entries (not inclusive)
	 * @return the range of matching fragment entries
	 */
	@Override
	public List<FragmentEntry> findByUuid_C_Head(
		String uuid, long companyId, boolean head, int start, int end) {

		return findByUuid_C_Head(uuid, companyId, head, start, end, null);
	}

	/**
	 * Returns an ordered range of all the fragment entries where uuid = &#63; and companyId = &#63; and head = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param head the head
	 * @param start the lower bound of the range of fragment entries
	 * @param end the upper bound of the range of fragment entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching fragment entries
	 */
	@Override
	public List<FragmentEntry> findByUuid_C_Head(
		String uuid, long companyId, boolean head, int start, int end,
		OrderByComparator<FragmentEntry> orderByComparator) {

		return findByUuid_C_Head(
			uuid, companyId, head, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the fragment entries where uuid = &#63; and companyId = &#63; and head = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param head the head
	 * @param start the lower bound of the range of fragment entries
	 * @param end the upper bound of the range of fragment entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching fragment entries
	 */
	@Override
	public List<FragmentEntry> findByUuid_C_Head(
		String uuid, long companyId, boolean head, int start, int end,
		OrderByComparator<FragmentEntry> orderByComparator,
		boolean useFinderCache) {

		uuid = Objects.toString(uuid, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			FragmentEntry.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache && productionMode) {
				finderPath = _finderPathWithoutPaginationFindByUuid_C_Head;
				finderArgs = new Object[] {uuid, companyId, head};
			}
		}
		else if (useFinderCache && productionMode) {
			finderPath = _finderPathWithPaginationFindByUuid_C_Head;
			finderArgs = new Object[] {
				uuid, companyId, head, start, end, orderByComparator
			};
		}

		List<FragmentEntry> list = null;

		if (useFinderCache && productionMode) {
			list = (List<FragmentEntry>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (FragmentEntry fragmentEntry : list) {
					if (!uuid.equals(fragmentEntry.getUuid()) ||
						(companyId != fragmentEntry.getCompanyId()) ||
						(head != fragmentEntry.isHead())) {

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

			sb.append(_SQL_SELECT_FRAGMENTENTRY_WHERE);

			boolean bindUuid = false;

			if (uuid.isEmpty()) {
				sb.append(_FINDER_COLUMN_UUID_C_HEAD_UUID_3);
			}
			else {
				bindUuid = true;

				sb.append(_FINDER_COLUMN_UUID_C_HEAD_UUID_2);
			}

			sb.append(_FINDER_COLUMN_UUID_C_HEAD_COMPANYID_2);

			sb.append(_FINDER_COLUMN_UUID_C_HEAD_HEAD_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(FragmentEntryModelImpl.ORDER_BY_JPQL);
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

				queryPos.add(head);

				list = (List<FragmentEntry>)QueryUtil.list(
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
	 * Returns the first fragment entry in the ordered set where uuid = &#63; and companyId = &#63; and head = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param head the head
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment entry
	 * @throws NoSuchEntryException if a matching fragment entry could not be found
	 */
	@Override
	public FragmentEntry findByUuid_C_Head_First(
			String uuid, long companyId, boolean head,
			OrderByComparator<FragmentEntry> orderByComparator)
		throws NoSuchEntryException {

		FragmentEntry fragmentEntry = fetchByUuid_C_Head_First(
			uuid, companyId, head, orderByComparator);

		if (fragmentEntry != null) {
			return fragmentEntry;
		}

		StringBundler sb = new StringBundler(8);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append(", companyId=");
		sb.append(companyId);

		sb.append(", head=");
		sb.append(head);

		sb.append("}");

		throw new NoSuchEntryException(sb.toString());
	}

	/**
	 * Returns the first fragment entry in the ordered set where uuid = &#63; and companyId = &#63; and head = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param head the head
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment entry, or <code>null</code> if a matching fragment entry could not be found
	 */
	@Override
	public FragmentEntry fetchByUuid_C_Head_First(
		String uuid, long companyId, boolean head,
		OrderByComparator<FragmentEntry> orderByComparator) {

		List<FragmentEntry> list = findByUuid_C_Head(
			uuid, companyId, head, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last fragment entry in the ordered set where uuid = &#63; and companyId = &#63; and head = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param head the head
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching fragment entry
	 * @throws NoSuchEntryException if a matching fragment entry could not be found
	 */
	@Override
	public FragmentEntry findByUuid_C_Head_Last(
			String uuid, long companyId, boolean head,
			OrderByComparator<FragmentEntry> orderByComparator)
		throws NoSuchEntryException {

		FragmentEntry fragmentEntry = fetchByUuid_C_Head_Last(
			uuid, companyId, head, orderByComparator);

		if (fragmentEntry != null) {
			return fragmentEntry;
		}

		StringBundler sb = new StringBundler(8);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append(", companyId=");
		sb.append(companyId);

		sb.append(", head=");
		sb.append(head);

		sb.append("}");

		throw new NoSuchEntryException(sb.toString());
	}

	/**
	 * Returns the last fragment entry in the ordered set where uuid = &#63; and companyId = &#63; and head = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param head the head
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching fragment entry, or <code>null</code> if a matching fragment entry could not be found
	 */
	@Override
	public FragmentEntry fetchByUuid_C_Head_Last(
		String uuid, long companyId, boolean head,
		OrderByComparator<FragmentEntry> orderByComparator) {

		int count = countByUuid_C_Head(uuid, companyId, head);

		if (count == 0) {
			return null;
		}

		List<FragmentEntry> list = findByUuid_C_Head(
			uuid, companyId, head, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the fragment entries before and after the current fragment entry in the ordered set where uuid = &#63; and companyId = &#63; and head = &#63;.
	 *
	 * @param fragmentEntryId the primary key of the current fragment entry
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param head the head
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next fragment entry
	 * @throws NoSuchEntryException if a fragment entry with the primary key could not be found
	 */
	@Override
	public FragmentEntry[] findByUuid_C_Head_PrevAndNext(
			long fragmentEntryId, String uuid, long companyId, boolean head,
			OrderByComparator<FragmentEntry> orderByComparator)
		throws NoSuchEntryException {

		uuid = Objects.toString(uuid, "");

		FragmentEntry fragmentEntry = findByPrimaryKey(fragmentEntryId);

		Session session = null;

		try {
			session = openSession();

			FragmentEntry[] array = new FragmentEntryImpl[3];

			array[0] = getByUuid_C_Head_PrevAndNext(
				session, fragmentEntry, uuid, companyId, head,
				orderByComparator, true);

			array[1] = fragmentEntry;

			array[2] = getByUuid_C_Head_PrevAndNext(
				session, fragmentEntry, uuid, companyId, head,
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

	protected FragmentEntry getByUuid_C_Head_PrevAndNext(
		Session session, FragmentEntry fragmentEntry, String uuid,
		long companyId, boolean head,
		OrderByComparator<FragmentEntry> orderByComparator, boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				6 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(5);
		}

		sb.append(_SQL_SELECT_FRAGMENTENTRY_WHERE);

		boolean bindUuid = false;

		if (uuid.isEmpty()) {
			sb.append(_FINDER_COLUMN_UUID_C_HEAD_UUID_3);
		}
		else {
			bindUuid = true;

			sb.append(_FINDER_COLUMN_UUID_C_HEAD_UUID_2);
		}

		sb.append(_FINDER_COLUMN_UUID_C_HEAD_COMPANYID_2);

		sb.append(_FINDER_COLUMN_UUID_C_HEAD_HEAD_2);

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
			sb.append(FragmentEntryModelImpl.ORDER_BY_JPQL);
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

		queryPos.add(head);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						fragmentEntry)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<FragmentEntry> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the fragment entries where uuid = &#63; and companyId = &#63; and head = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param head the head
	 */
	@Override
	public void removeByUuid_C_Head(String uuid, long companyId, boolean head) {
		for (FragmentEntry fragmentEntry :
				findByUuid_C_Head(
					uuid, companyId, head, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(fragmentEntry);
		}
	}

	/**
	 * Returns the number of fragment entries where uuid = &#63; and companyId = &#63; and head = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param head the head
	 * @return the number of matching fragment entries
	 */
	@Override
	public int countByUuid_C_Head(String uuid, long companyId, boolean head) {
		uuid = Objects.toString(uuid, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			FragmentEntry.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByUuid_C_Head;

			finderArgs = new Object[] {uuid, companyId, head};

			count = (Long)finderCache.getResult(finderPath, finderArgs, this);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_COUNT_FRAGMENTENTRY_WHERE);

			boolean bindUuid = false;

			if (uuid.isEmpty()) {
				sb.append(_FINDER_COLUMN_UUID_C_HEAD_UUID_3);
			}
			else {
				bindUuid = true;

				sb.append(_FINDER_COLUMN_UUID_C_HEAD_UUID_2);
			}

			sb.append(_FINDER_COLUMN_UUID_C_HEAD_COMPANYID_2);

			sb.append(_FINDER_COLUMN_UUID_C_HEAD_HEAD_2);

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

				queryPos.add(head);

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

	private static final String _FINDER_COLUMN_UUID_C_HEAD_UUID_2 =
		"fragmentEntry.uuid = ? AND ";

	private static final String _FINDER_COLUMN_UUID_C_HEAD_UUID_3 =
		"(fragmentEntry.uuid IS NULL OR fragmentEntry.uuid = '') AND ";

	private static final String _FINDER_COLUMN_UUID_C_HEAD_COMPANYID_2 =
		"fragmentEntry.companyId = ? AND ";

	private static final String _FINDER_COLUMN_UUID_C_HEAD_HEAD_2 =
		"fragmentEntry.head = ?";

	private FinderPath _finderPathWithPaginationFindByGroupId;
	private FinderPath _finderPathWithoutPaginationFindByGroupId;
	private FinderPath _finderPathCountByGroupId;

	/**
	 * Returns all the fragment entries where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching fragment entries
	 */
	@Override
	public List<FragmentEntry> findByGroupId(long groupId) {
		return findByGroupId(
			groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the fragment entries where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of fragment entries
	 * @param end the upper bound of the range of fragment entries (not inclusive)
	 * @return the range of matching fragment entries
	 */
	@Override
	public List<FragmentEntry> findByGroupId(long groupId, int start, int end) {
		return findByGroupId(groupId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the fragment entries where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of fragment entries
	 * @param end the upper bound of the range of fragment entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching fragment entries
	 */
	@Override
	public List<FragmentEntry> findByGroupId(
		long groupId, int start, int end,
		OrderByComparator<FragmentEntry> orderByComparator) {

		return findByGroupId(groupId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the fragment entries where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of fragment entries
	 * @param end the upper bound of the range of fragment entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching fragment entries
	 */
	@Override
	public List<FragmentEntry> findByGroupId(
		long groupId, int start, int end,
		OrderByComparator<FragmentEntry> orderByComparator,
		boolean useFinderCache) {

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			FragmentEntry.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache && productionMode) {
				finderPath = _finderPathWithoutPaginationFindByGroupId;
				finderArgs = new Object[] {groupId};
			}
		}
		else if (useFinderCache && productionMode) {
			finderPath = _finderPathWithPaginationFindByGroupId;
			finderArgs = new Object[] {groupId, start, end, orderByComparator};
		}

		List<FragmentEntry> list = null;

		if (useFinderCache && productionMode) {
			list = (List<FragmentEntry>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (FragmentEntry fragmentEntry : list) {
					if (groupId != fragmentEntry.getGroupId()) {
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

			sb.append(_SQL_SELECT_FRAGMENTENTRY_WHERE);

			sb.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(FragmentEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				list = (List<FragmentEntry>)QueryUtil.list(
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
	 * Returns the first fragment entry in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment entry
	 * @throws NoSuchEntryException if a matching fragment entry could not be found
	 */
	@Override
	public FragmentEntry findByGroupId_First(
			long groupId, OrderByComparator<FragmentEntry> orderByComparator)
		throws NoSuchEntryException {

		FragmentEntry fragmentEntry = fetchByGroupId_First(
			groupId, orderByComparator);

		if (fragmentEntry != null) {
			return fragmentEntry;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append("}");

		throw new NoSuchEntryException(sb.toString());
	}

	/**
	 * Returns the first fragment entry in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment entry, or <code>null</code> if a matching fragment entry could not be found
	 */
	@Override
	public FragmentEntry fetchByGroupId_First(
		long groupId, OrderByComparator<FragmentEntry> orderByComparator) {

		List<FragmentEntry> list = findByGroupId(
			groupId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last fragment entry in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching fragment entry
	 * @throws NoSuchEntryException if a matching fragment entry could not be found
	 */
	@Override
	public FragmentEntry findByGroupId_Last(
			long groupId, OrderByComparator<FragmentEntry> orderByComparator)
		throws NoSuchEntryException {

		FragmentEntry fragmentEntry = fetchByGroupId_Last(
			groupId, orderByComparator);

		if (fragmentEntry != null) {
			return fragmentEntry;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append("}");

		throw new NoSuchEntryException(sb.toString());
	}

	/**
	 * Returns the last fragment entry in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching fragment entry, or <code>null</code> if a matching fragment entry could not be found
	 */
	@Override
	public FragmentEntry fetchByGroupId_Last(
		long groupId, OrderByComparator<FragmentEntry> orderByComparator) {

		int count = countByGroupId(groupId);

		if (count == 0) {
			return null;
		}

		List<FragmentEntry> list = findByGroupId(
			groupId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the fragment entries before and after the current fragment entry in the ordered set where groupId = &#63;.
	 *
	 * @param fragmentEntryId the primary key of the current fragment entry
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next fragment entry
	 * @throws NoSuchEntryException if a fragment entry with the primary key could not be found
	 */
	@Override
	public FragmentEntry[] findByGroupId_PrevAndNext(
			long fragmentEntryId, long groupId,
			OrderByComparator<FragmentEntry> orderByComparator)
		throws NoSuchEntryException {

		FragmentEntry fragmentEntry = findByPrimaryKey(fragmentEntryId);

		Session session = null;

		try {
			session = openSession();

			FragmentEntry[] array = new FragmentEntryImpl[3];

			array[0] = getByGroupId_PrevAndNext(
				session, fragmentEntry, groupId, orderByComparator, true);

			array[1] = fragmentEntry;

			array[2] = getByGroupId_PrevAndNext(
				session, fragmentEntry, groupId, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected FragmentEntry getByGroupId_PrevAndNext(
		Session session, FragmentEntry fragmentEntry, long groupId,
		OrderByComparator<FragmentEntry> orderByComparator, boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(3);
		}

		sb.append(_SQL_SELECT_FRAGMENTENTRY_WHERE);

		sb.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

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
			sb.append(FragmentEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(groupId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						fragmentEntry)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<FragmentEntry> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the fragment entries where groupId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 */
	@Override
	public void removeByGroupId(long groupId) {
		for (FragmentEntry fragmentEntry :
				findByGroupId(
					groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(fragmentEntry);
		}
	}

	/**
	 * Returns the number of fragment entries where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching fragment entries
	 */
	@Override
	public int countByGroupId(long groupId) {
		boolean productionMode = ctPersistenceHelper.isProductionMode(
			FragmentEntry.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByGroupId;

			finderArgs = new Object[] {groupId};

			count = (Long)finderCache.getResult(finderPath, finderArgs, this);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_FRAGMENTENTRY_WHERE);

			sb.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

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

	private static final String _FINDER_COLUMN_GROUPID_GROUPID_2 =
		"fragmentEntry.groupId = ?";

	private FinderPath _finderPathWithPaginationFindByGroupId_Head;
	private FinderPath _finderPathWithoutPaginationFindByGroupId_Head;
	private FinderPath _finderPathCountByGroupId_Head;

	/**
	 * Returns all the fragment entries where groupId = &#63; and head = &#63;.
	 *
	 * @param groupId the group ID
	 * @param head the head
	 * @return the matching fragment entries
	 */
	@Override
	public List<FragmentEntry> findByGroupId_Head(long groupId, boolean head) {
		return findByGroupId_Head(
			groupId, head, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the fragment entries where groupId = &#63; and head = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param head the head
	 * @param start the lower bound of the range of fragment entries
	 * @param end the upper bound of the range of fragment entries (not inclusive)
	 * @return the range of matching fragment entries
	 */
	@Override
	public List<FragmentEntry> findByGroupId_Head(
		long groupId, boolean head, int start, int end) {

		return findByGroupId_Head(groupId, head, start, end, null);
	}

	/**
	 * Returns an ordered range of all the fragment entries where groupId = &#63; and head = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param head the head
	 * @param start the lower bound of the range of fragment entries
	 * @param end the upper bound of the range of fragment entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching fragment entries
	 */
	@Override
	public List<FragmentEntry> findByGroupId_Head(
		long groupId, boolean head, int start, int end,
		OrderByComparator<FragmentEntry> orderByComparator) {

		return findByGroupId_Head(
			groupId, head, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the fragment entries where groupId = &#63; and head = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param head the head
	 * @param start the lower bound of the range of fragment entries
	 * @param end the upper bound of the range of fragment entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching fragment entries
	 */
	@Override
	public List<FragmentEntry> findByGroupId_Head(
		long groupId, boolean head, int start, int end,
		OrderByComparator<FragmentEntry> orderByComparator,
		boolean useFinderCache) {

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			FragmentEntry.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache && productionMode) {
				finderPath = _finderPathWithoutPaginationFindByGroupId_Head;
				finderArgs = new Object[] {groupId, head};
			}
		}
		else if (useFinderCache && productionMode) {
			finderPath = _finderPathWithPaginationFindByGroupId_Head;
			finderArgs = new Object[] {
				groupId, head, start, end, orderByComparator
			};
		}

		List<FragmentEntry> list = null;

		if (useFinderCache && productionMode) {
			list = (List<FragmentEntry>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (FragmentEntry fragmentEntry : list) {
					if ((groupId != fragmentEntry.getGroupId()) ||
						(head != fragmentEntry.isHead())) {

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

			sb.append(_SQL_SELECT_FRAGMENTENTRY_WHERE);

			sb.append(_FINDER_COLUMN_GROUPID_HEAD_GROUPID_2);

			sb.append(_FINDER_COLUMN_GROUPID_HEAD_HEAD_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(FragmentEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				queryPos.add(head);

				list = (List<FragmentEntry>)QueryUtil.list(
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
	 * Returns the first fragment entry in the ordered set where groupId = &#63; and head = &#63;.
	 *
	 * @param groupId the group ID
	 * @param head the head
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment entry
	 * @throws NoSuchEntryException if a matching fragment entry could not be found
	 */
	@Override
	public FragmentEntry findByGroupId_Head_First(
			long groupId, boolean head,
			OrderByComparator<FragmentEntry> orderByComparator)
		throws NoSuchEntryException {

		FragmentEntry fragmentEntry = fetchByGroupId_Head_First(
			groupId, head, orderByComparator);

		if (fragmentEntry != null) {
			return fragmentEntry;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", head=");
		sb.append(head);

		sb.append("}");

		throw new NoSuchEntryException(sb.toString());
	}

	/**
	 * Returns the first fragment entry in the ordered set where groupId = &#63; and head = &#63;.
	 *
	 * @param groupId the group ID
	 * @param head the head
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment entry, or <code>null</code> if a matching fragment entry could not be found
	 */
	@Override
	public FragmentEntry fetchByGroupId_Head_First(
		long groupId, boolean head,
		OrderByComparator<FragmentEntry> orderByComparator) {

		List<FragmentEntry> list = findByGroupId_Head(
			groupId, head, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last fragment entry in the ordered set where groupId = &#63; and head = &#63;.
	 *
	 * @param groupId the group ID
	 * @param head the head
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching fragment entry
	 * @throws NoSuchEntryException if a matching fragment entry could not be found
	 */
	@Override
	public FragmentEntry findByGroupId_Head_Last(
			long groupId, boolean head,
			OrderByComparator<FragmentEntry> orderByComparator)
		throws NoSuchEntryException {

		FragmentEntry fragmentEntry = fetchByGroupId_Head_Last(
			groupId, head, orderByComparator);

		if (fragmentEntry != null) {
			return fragmentEntry;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", head=");
		sb.append(head);

		sb.append("}");

		throw new NoSuchEntryException(sb.toString());
	}

	/**
	 * Returns the last fragment entry in the ordered set where groupId = &#63; and head = &#63;.
	 *
	 * @param groupId the group ID
	 * @param head the head
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching fragment entry, or <code>null</code> if a matching fragment entry could not be found
	 */
	@Override
	public FragmentEntry fetchByGroupId_Head_Last(
		long groupId, boolean head,
		OrderByComparator<FragmentEntry> orderByComparator) {

		int count = countByGroupId_Head(groupId, head);

		if (count == 0) {
			return null;
		}

		List<FragmentEntry> list = findByGroupId_Head(
			groupId, head, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the fragment entries before and after the current fragment entry in the ordered set where groupId = &#63; and head = &#63;.
	 *
	 * @param fragmentEntryId the primary key of the current fragment entry
	 * @param groupId the group ID
	 * @param head the head
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next fragment entry
	 * @throws NoSuchEntryException if a fragment entry with the primary key could not be found
	 */
	@Override
	public FragmentEntry[] findByGroupId_Head_PrevAndNext(
			long fragmentEntryId, long groupId, boolean head,
			OrderByComparator<FragmentEntry> orderByComparator)
		throws NoSuchEntryException {

		FragmentEntry fragmentEntry = findByPrimaryKey(fragmentEntryId);

		Session session = null;

		try {
			session = openSession();

			FragmentEntry[] array = new FragmentEntryImpl[3];

			array[0] = getByGroupId_Head_PrevAndNext(
				session, fragmentEntry, groupId, head, orderByComparator, true);

			array[1] = fragmentEntry;

			array[2] = getByGroupId_Head_PrevAndNext(
				session, fragmentEntry, groupId, head, orderByComparator,
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

	protected FragmentEntry getByGroupId_Head_PrevAndNext(
		Session session, FragmentEntry fragmentEntry, long groupId,
		boolean head, OrderByComparator<FragmentEntry> orderByComparator,
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

		sb.append(_SQL_SELECT_FRAGMENTENTRY_WHERE);

		sb.append(_FINDER_COLUMN_GROUPID_HEAD_GROUPID_2);

		sb.append(_FINDER_COLUMN_GROUPID_HEAD_HEAD_2);

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
			sb.append(FragmentEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(groupId);

		queryPos.add(head);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						fragmentEntry)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<FragmentEntry> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the fragment entries where groupId = &#63; and head = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param head the head
	 */
	@Override
	public void removeByGroupId_Head(long groupId, boolean head) {
		for (FragmentEntry fragmentEntry :
				findByGroupId_Head(
					groupId, head, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(fragmentEntry);
		}
	}

	/**
	 * Returns the number of fragment entries where groupId = &#63; and head = &#63;.
	 *
	 * @param groupId the group ID
	 * @param head the head
	 * @return the number of matching fragment entries
	 */
	@Override
	public int countByGroupId_Head(long groupId, boolean head) {
		boolean productionMode = ctPersistenceHelper.isProductionMode(
			FragmentEntry.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByGroupId_Head;

			finderArgs = new Object[] {groupId, head};

			count = (Long)finderCache.getResult(finderPath, finderArgs, this);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_FRAGMENTENTRY_WHERE);

			sb.append(_FINDER_COLUMN_GROUPID_HEAD_GROUPID_2);

			sb.append(_FINDER_COLUMN_GROUPID_HEAD_HEAD_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				queryPos.add(head);

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

	private static final String _FINDER_COLUMN_GROUPID_HEAD_GROUPID_2 =
		"fragmentEntry.groupId = ? AND ";

	private static final String _FINDER_COLUMN_GROUPID_HEAD_HEAD_2 =
		"fragmentEntry.head = ?";

	private FinderPath _finderPathWithPaginationFindByFragmentCollectionId;
	private FinderPath _finderPathWithoutPaginationFindByFragmentCollectionId;
	private FinderPath _finderPathCountByFragmentCollectionId;

	/**
	 * Returns all the fragment entries where fragmentCollectionId = &#63;.
	 *
	 * @param fragmentCollectionId the fragment collection ID
	 * @return the matching fragment entries
	 */
	@Override
	public List<FragmentEntry> findByFragmentCollectionId(
		long fragmentCollectionId) {

		return findByFragmentCollectionId(
			fragmentCollectionId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the fragment entries where fragmentCollectionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryModelImpl</code>.
	 * </p>
	 *
	 * @param fragmentCollectionId the fragment collection ID
	 * @param start the lower bound of the range of fragment entries
	 * @param end the upper bound of the range of fragment entries (not inclusive)
	 * @return the range of matching fragment entries
	 */
	@Override
	public List<FragmentEntry> findByFragmentCollectionId(
		long fragmentCollectionId, int start, int end) {

		return findByFragmentCollectionId(
			fragmentCollectionId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the fragment entries where fragmentCollectionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryModelImpl</code>.
	 * </p>
	 *
	 * @param fragmentCollectionId the fragment collection ID
	 * @param start the lower bound of the range of fragment entries
	 * @param end the upper bound of the range of fragment entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching fragment entries
	 */
	@Override
	public List<FragmentEntry> findByFragmentCollectionId(
		long fragmentCollectionId, int start, int end,
		OrderByComparator<FragmentEntry> orderByComparator) {

		return findByFragmentCollectionId(
			fragmentCollectionId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the fragment entries where fragmentCollectionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryModelImpl</code>.
	 * </p>
	 *
	 * @param fragmentCollectionId the fragment collection ID
	 * @param start the lower bound of the range of fragment entries
	 * @param end the upper bound of the range of fragment entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching fragment entries
	 */
	@Override
	public List<FragmentEntry> findByFragmentCollectionId(
		long fragmentCollectionId, int start, int end,
		OrderByComparator<FragmentEntry> orderByComparator,
		boolean useFinderCache) {

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			FragmentEntry.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache && productionMode) {
				finderPath =
					_finderPathWithoutPaginationFindByFragmentCollectionId;
				finderArgs = new Object[] {fragmentCollectionId};
			}
		}
		else if (useFinderCache && productionMode) {
			finderPath = _finderPathWithPaginationFindByFragmentCollectionId;
			finderArgs = new Object[] {
				fragmentCollectionId, start, end, orderByComparator
			};
		}

		List<FragmentEntry> list = null;

		if (useFinderCache && productionMode) {
			list = (List<FragmentEntry>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (FragmentEntry fragmentEntry : list) {
					if (fragmentCollectionId !=
							fragmentEntry.getFragmentCollectionId()) {

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

			sb.append(_SQL_SELECT_FRAGMENTENTRY_WHERE);

			sb.append(
				_FINDER_COLUMN_FRAGMENTCOLLECTIONID_FRAGMENTCOLLECTIONID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(FragmentEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(fragmentCollectionId);

				list = (List<FragmentEntry>)QueryUtil.list(
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
	 * Returns the first fragment entry in the ordered set where fragmentCollectionId = &#63;.
	 *
	 * @param fragmentCollectionId the fragment collection ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment entry
	 * @throws NoSuchEntryException if a matching fragment entry could not be found
	 */
	@Override
	public FragmentEntry findByFragmentCollectionId_First(
			long fragmentCollectionId,
			OrderByComparator<FragmentEntry> orderByComparator)
		throws NoSuchEntryException {

		FragmentEntry fragmentEntry = fetchByFragmentCollectionId_First(
			fragmentCollectionId, orderByComparator);

		if (fragmentEntry != null) {
			return fragmentEntry;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("fragmentCollectionId=");
		sb.append(fragmentCollectionId);

		sb.append("}");

		throw new NoSuchEntryException(sb.toString());
	}

	/**
	 * Returns the first fragment entry in the ordered set where fragmentCollectionId = &#63;.
	 *
	 * @param fragmentCollectionId the fragment collection ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment entry, or <code>null</code> if a matching fragment entry could not be found
	 */
	@Override
	public FragmentEntry fetchByFragmentCollectionId_First(
		long fragmentCollectionId,
		OrderByComparator<FragmentEntry> orderByComparator) {

		List<FragmentEntry> list = findByFragmentCollectionId(
			fragmentCollectionId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last fragment entry in the ordered set where fragmentCollectionId = &#63;.
	 *
	 * @param fragmentCollectionId the fragment collection ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching fragment entry
	 * @throws NoSuchEntryException if a matching fragment entry could not be found
	 */
	@Override
	public FragmentEntry findByFragmentCollectionId_Last(
			long fragmentCollectionId,
			OrderByComparator<FragmentEntry> orderByComparator)
		throws NoSuchEntryException {

		FragmentEntry fragmentEntry = fetchByFragmentCollectionId_Last(
			fragmentCollectionId, orderByComparator);

		if (fragmentEntry != null) {
			return fragmentEntry;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("fragmentCollectionId=");
		sb.append(fragmentCollectionId);

		sb.append("}");

		throw new NoSuchEntryException(sb.toString());
	}

	/**
	 * Returns the last fragment entry in the ordered set where fragmentCollectionId = &#63;.
	 *
	 * @param fragmentCollectionId the fragment collection ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching fragment entry, or <code>null</code> if a matching fragment entry could not be found
	 */
	@Override
	public FragmentEntry fetchByFragmentCollectionId_Last(
		long fragmentCollectionId,
		OrderByComparator<FragmentEntry> orderByComparator) {

		int count = countByFragmentCollectionId(fragmentCollectionId);

		if (count == 0) {
			return null;
		}

		List<FragmentEntry> list = findByFragmentCollectionId(
			fragmentCollectionId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the fragment entries before and after the current fragment entry in the ordered set where fragmentCollectionId = &#63;.
	 *
	 * @param fragmentEntryId the primary key of the current fragment entry
	 * @param fragmentCollectionId the fragment collection ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next fragment entry
	 * @throws NoSuchEntryException if a fragment entry with the primary key could not be found
	 */
	@Override
	public FragmentEntry[] findByFragmentCollectionId_PrevAndNext(
			long fragmentEntryId, long fragmentCollectionId,
			OrderByComparator<FragmentEntry> orderByComparator)
		throws NoSuchEntryException {

		FragmentEntry fragmentEntry = findByPrimaryKey(fragmentEntryId);

		Session session = null;

		try {
			session = openSession();

			FragmentEntry[] array = new FragmentEntryImpl[3];

			array[0] = getByFragmentCollectionId_PrevAndNext(
				session, fragmentEntry, fragmentCollectionId, orderByComparator,
				true);

			array[1] = fragmentEntry;

			array[2] = getByFragmentCollectionId_PrevAndNext(
				session, fragmentEntry, fragmentCollectionId, orderByComparator,
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

	protected FragmentEntry getByFragmentCollectionId_PrevAndNext(
		Session session, FragmentEntry fragmentEntry, long fragmentCollectionId,
		OrderByComparator<FragmentEntry> orderByComparator, boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(3);
		}

		sb.append(_SQL_SELECT_FRAGMENTENTRY_WHERE);

		sb.append(_FINDER_COLUMN_FRAGMENTCOLLECTIONID_FRAGMENTCOLLECTIONID_2);

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
			sb.append(FragmentEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(fragmentCollectionId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						fragmentEntry)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<FragmentEntry> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the fragment entries where fragmentCollectionId = &#63; from the database.
	 *
	 * @param fragmentCollectionId the fragment collection ID
	 */
	@Override
	public void removeByFragmentCollectionId(long fragmentCollectionId) {
		for (FragmentEntry fragmentEntry :
				findByFragmentCollectionId(
					fragmentCollectionId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(fragmentEntry);
		}
	}

	/**
	 * Returns the number of fragment entries where fragmentCollectionId = &#63;.
	 *
	 * @param fragmentCollectionId the fragment collection ID
	 * @return the number of matching fragment entries
	 */
	@Override
	public int countByFragmentCollectionId(long fragmentCollectionId) {
		boolean productionMode = ctPersistenceHelper.isProductionMode(
			FragmentEntry.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByFragmentCollectionId;

			finderArgs = new Object[] {fragmentCollectionId};

			count = (Long)finderCache.getResult(finderPath, finderArgs, this);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_FRAGMENTENTRY_WHERE);

			sb.append(
				_FINDER_COLUMN_FRAGMENTCOLLECTIONID_FRAGMENTCOLLECTIONID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(fragmentCollectionId);

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
		_FINDER_COLUMN_FRAGMENTCOLLECTIONID_FRAGMENTCOLLECTIONID_2 =
			"fragmentEntry.fragmentCollectionId = ?";

	private FinderPath _finderPathWithPaginationFindByFragmentCollectionId_Head;
	private FinderPath
		_finderPathWithoutPaginationFindByFragmentCollectionId_Head;
	private FinderPath _finderPathCountByFragmentCollectionId_Head;

	/**
	 * Returns all the fragment entries where fragmentCollectionId = &#63; and head = &#63;.
	 *
	 * @param fragmentCollectionId the fragment collection ID
	 * @param head the head
	 * @return the matching fragment entries
	 */
	@Override
	public List<FragmentEntry> findByFragmentCollectionId_Head(
		long fragmentCollectionId, boolean head) {

		return findByFragmentCollectionId_Head(
			fragmentCollectionId, head, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the fragment entries where fragmentCollectionId = &#63; and head = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryModelImpl</code>.
	 * </p>
	 *
	 * @param fragmentCollectionId the fragment collection ID
	 * @param head the head
	 * @param start the lower bound of the range of fragment entries
	 * @param end the upper bound of the range of fragment entries (not inclusive)
	 * @return the range of matching fragment entries
	 */
	@Override
	public List<FragmentEntry> findByFragmentCollectionId_Head(
		long fragmentCollectionId, boolean head, int start, int end) {

		return findByFragmentCollectionId_Head(
			fragmentCollectionId, head, start, end, null);
	}

	/**
	 * Returns an ordered range of all the fragment entries where fragmentCollectionId = &#63; and head = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryModelImpl</code>.
	 * </p>
	 *
	 * @param fragmentCollectionId the fragment collection ID
	 * @param head the head
	 * @param start the lower bound of the range of fragment entries
	 * @param end the upper bound of the range of fragment entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching fragment entries
	 */
	@Override
	public List<FragmentEntry> findByFragmentCollectionId_Head(
		long fragmentCollectionId, boolean head, int start, int end,
		OrderByComparator<FragmentEntry> orderByComparator) {

		return findByFragmentCollectionId_Head(
			fragmentCollectionId, head, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the fragment entries where fragmentCollectionId = &#63; and head = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryModelImpl</code>.
	 * </p>
	 *
	 * @param fragmentCollectionId the fragment collection ID
	 * @param head the head
	 * @param start the lower bound of the range of fragment entries
	 * @param end the upper bound of the range of fragment entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching fragment entries
	 */
	@Override
	public List<FragmentEntry> findByFragmentCollectionId_Head(
		long fragmentCollectionId, boolean head, int start, int end,
		OrderByComparator<FragmentEntry> orderByComparator,
		boolean useFinderCache) {

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			FragmentEntry.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache && productionMode) {
				finderPath =
					_finderPathWithoutPaginationFindByFragmentCollectionId_Head;
				finderArgs = new Object[] {fragmentCollectionId, head};
			}
		}
		else if (useFinderCache && productionMode) {
			finderPath =
				_finderPathWithPaginationFindByFragmentCollectionId_Head;
			finderArgs = new Object[] {
				fragmentCollectionId, head, start, end, orderByComparator
			};
		}

		List<FragmentEntry> list = null;

		if (useFinderCache && productionMode) {
			list = (List<FragmentEntry>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (FragmentEntry fragmentEntry : list) {
					if ((fragmentCollectionId !=
							fragmentEntry.getFragmentCollectionId()) ||
						(head != fragmentEntry.isHead())) {

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

			sb.append(_SQL_SELECT_FRAGMENTENTRY_WHERE);

			sb.append(
				_FINDER_COLUMN_FRAGMENTCOLLECTIONID_HEAD_FRAGMENTCOLLECTIONID_2);

			sb.append(_FINDER_COLUMN_FRAGMENTCOLLECTIONID_HEAD_HEAD_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(FragmentEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(fragmentCollectionId);

				queryPos.add(head);

				list = (List<FragmentEntry>)QueryUtil.list(
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
	 * Returns the first fragment entry in the ordered set where fragmentCollectionId = &#63; and head = &#63;.
	 *
	 * @param fragmentCollectionId the fragment collection ID
	 * @param head the head
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment entry
	 * @throws NoSuchEntryException if a matching fragment entry could not be found
	 */
	@Override
	public FragmentEntry findByFragmentCollectionId_Head_First(
			long fragmentCollectionId, boolean head,
			OrderByComparator<FragmentEntry> orderByComparator)
		throws NoSuchEntryException {

		FragmentEntry fragmentEntry = fetchByFragmentCollectionId_Head_First(
			fragmentCollectionId, head, orderByComparator);

		if (fragmentEntry != null) {
			return fragmentEntry;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("fragmentCollectionId=");
		sb.append(fragmentCollectionId);

		sb.append(", head=");
		sb.append(head);

		sb.append("}");

		throw new NoSuchEntryException(sb.toString());
	}

	/**
	 * Returns the first fragment entry in the ordered set where fragmentCollectionId = &#63; and head = &#63;.
	 *
	 * @param fragmentCollectionId the fragment collection ID
	 * @param head the head
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment entry, or <code>null</code> if a matching fragment entry could not be found
	 */
	@Override
	public FragmentEntry fetchByFragmentCollectionId_Head_First(
		long fragmentCollectionId, boolean head,
		OrderByComparator<FragmentEntry> orderByComparator) {

		List<FragmentEntry> list = findByFragmentCollectionId_Head(
			fragmentCollectionId, head, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last fragment entry in the ordered set where fragmentCollectionId = &#63; and head = &#63;.
	 *
	 * @param fragmentCollectionId the fragment collection ID
	 * @param head the head
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching fragment entry
	 * @throws NoSuchEntryException if a matching fragment entry could not be found
	 */
	@Override
	public FragmentEntry findByFragmentCollectionId_Head_Last(
			long fragmentCollectionId, boolean head,
			OrderByComparator<FragmentEntry> orderByComparator)
		throws NoSuchEntryException {

		FragmentEntry fragmentEntry = fetchByFragmentCollectionId_Head_Last(
			fragmentCollectionId, head, orderByComparator);

		if (fragmentEntry != null) {
			return fragmentEntry;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("fragmentCollectionId=");
		sb.append(fragmentCollectionId);

		sb.append(", head=");
		sb.append(head);

		sb.append("}");

		throw new NoSuchEntryException(sb.toString());
	}

	/**
	 * Returns the last fragment entry in the ordered set where fragmentCollectionId = &#63; and head = &#63;.
	 *
	 * @param fragmentCollectionId the fragment collection ID
	 * @param head the head
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching fragment entry, or <code>null</code> if a matching fragment entry could not be found
	 */
	@Override
	public FragmentEntry fetchByFragmentCollectionId_Head_Last(
		long fragmentCollectionId, boolean head,
		OrderByComparator<FragmentEntry> orderByComparator) {

		int count = countByFragmentCollectionId_Head(
			fragmentCollectionId, head);

		if (count == 0) {
			return null;
		}

		List<FragmentEntry> list = findByFragmentCollectionId_Head(
			fragmentCollectionId, head, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the fragment entries before and after the current fragment entry in the ordered set where fragmentCollectionId = &#63; and head = &#63;.
	 *
	 * @param fragmentEntryId the primary key of the current fragment entry
	 * @param fragmentCollectionId the fragment collection ID
	 * @param head the head
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next fragment entry
	 * @throws NoSuchEntryException if a fragment entry with the primary key could not be found
	 */
	@Override
	public FragmentEntry[] findByFragmentCollectionId_Head_PrevAndNext(
			long fragmentEntryId, long fragmentCollectionId, boolean head,
			OrderByComparator<FragmentEntry> orderByComparator)
		throws NoSuchEntryException {

		FragmentEntry fragmentEntry = findByPrimaryKey(fragmentEntryId);

		Session session = null;

		try {
			session = openSession();

			FragmentEntry[] array = new FragmentEntryImpl[3];

			array[0] = getByFragmentCollectionId_Head_PrevAndNext(
				session, fragmentEntry, fragmentCollectionId, head,
				orderByComparator, true);

			array[1] = fragmentEntry;

			array[2] = getByFragmentCollectionId_Head_PrevAndNext(
				session, fragmentEntry, fragmentCollectionId, head,
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

	protected FragmentEntry getByFragmentCollectionId_Head_PrevAndNext(
		Session session, FragmentEntry fragmentEntry, long fragmentCollectionId,
		boolean head, OrderByComparator<FragmentEntry> orderByComparator,
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

		sb.append(_SQL_SELECT_FRAGMENTENTRY_WHERE);

		sb.append(
			_FINDER_COLUMN_FRAGMENTCOLLECTIONID_HEAD_FRAGMENTCOLLECTIONID_2);

		sb.append(_FINDER_COLUMN_FRAGMENTCOLLECTIONID_HEAD_HEAD_2);

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
			sb.append(FragmentEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(fragmentCollectionId);

		queryPos.add(head);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						fragmentEntry)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<FragmentEntry> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the fragment entries where fragmentCollectionId = &#63; and head = &#63; from the database.
	 *
	 * @param fragmentCollectionId the fragment collection ID
	 * @param head the head
	 */
	@Override
	public void removeByFragmentCollectionId_Head(
		long fragmentCollectionId, boolean head) {

		for (FragmentEntry fragmentEntry :
				findByFragmentCollectionId_Head(
					fragmentCollectionId, head, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			remove(fragmentEntry);
		}
	}

	/**
	 * Returns the number of fragment entries where fragmentCollectionId = &#63; and head = &#63;.
	 *
	 * @param fragmentCollectionId the fragment collection ID
	 * @param head the head
	 * @return the number of matching fragment entries
	 */
	@Override
	public int countByFragmentCollectionId_Head(
		long fragmentCollectionId, boolean head) {

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			FragmentEntry.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByFragmentCollectionId_Head;

			finderArgs = new Object[] {fragmentCollectionId, head};

			count = (Long)finderCache.getResult(finderPath, finderArgs, this);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_FRAGMENTENTRY_WHERE);

			sb.append(
				_FINDER_COLUMN_FRAGMENTCOLLECTIONID_HEAD_FRAGMENTCOLLECTIONID_2);

			sb.append(_FINDER_COLUMN_FRAGMENTCOLLECTIONID_HEAD_HEAD_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(fragmentCollectionId);

				queryPos.add(head);

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
		_FINDER_COLUMN_FRAGMENTCOLLECTIONID_HEAD_FRAGMENTCOLLECTIONID_2 =
			"fragmentEntry.fragmentCollectionId = ? AND ";

	private static final String
		_FINDER_COLUMN_FRAGMENTCOLLECTIONID_HEAD_HEAD_2 =
			"fragmentEntry.head = ?";

	private FinderPath _finderPathWithPaginationFindByG_FCI;
	private FinderPath _finderPathWithoutPaginationFindByG_FCI;
	private FinderPath _finderPathCountByG_FCI;

	/**
	 * Returns all the fragment entries where groupId = &#63; and fragmentCollectionId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @return the matching fragment entries
	 */
	@Override
	public List<FragmentEntry> findByG_FCI(
		long groupId, long fragmentCollectionId) {

		return findByG_FCI(
			groupId, fragmentCollectionId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the fragment entries where groupId = &#63; and fragmentCollectionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param start the lower bound of the range of fragment entries
	 * @param end the upper bound of the range of fragment entries (not inclusive)
	 * @return the range of matching fragment entries
	 */
	@Override
	public List<FragmentEntry> findByG_FCI(
		long groupId, long fragmentCollectionId, int start, int end) {

		return findByG_FCI(groupId, fragmentCollectionId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the fragment entries where groupId = &#63; and fragmentCollectionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param start the lower bound of the range of fragment entries
	 * @param end the upper bound of the range of fragment entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching fragment entries
	 */
	@Override
	public List<FragmentEntry> findByG_FCI(
		long groupId, long fragmentCollectionId, int start, int end,
		OrderByComparator<FragmentEntry> orderByComparator) {

		return findByG_FCI(
			groupId, fragmentCollectionId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the fragment entries where groupId = &#63; and fragmentCollectionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param start the lower bound of the range of fragment entries
	 * @param end the upper bound of the range of fragment entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching fragment entries
	 */
	@Override
	public List<FragmentEntry> findByG_FCI(
		long groupId, long fragmentCollectionId, int start, int end,
		OrderByComparator<FragmentEntry> orderByComparator,
		boolean useFinderCache) {

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			FragmentEntry.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache && productionMode) {
				finderPath = _finderPathWithoutPaginationFindByG_FCI;
				finderArgs = new Object[] {groupId, fragmentCollectionId};
			}
		}
		else if (useFinderCache && productionMode) {
			finderPath = _finderPathWithPaginationFindByG_FCI;
			finderArgs = new Object[] {
				groupId, fragmentCollectionId, start, end, orderByComparator
			};
		}

		List<FragmentEntry> list = null;

		if (useFinderCache && productionMode) {
			list = (List<FragmentEntry>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (FragmentEntry fragmentEntry : list) {
					if ((groupId != fragmentEntry.getGroupId()) ||
						(fragmentCollectionId !=
							fragmentEntry.getFragmentCollectionId())) {

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

			sb.append(_SQL_SELECT_FRAGMENTENTRY_WHERE);

			sb.append(_FINDER_COLUMN_G_FCI_GROUPID_2);

			sb.append(_FINDER_COLUMN_G_FCI_FRAGMENTCOLLECTIONID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(FragmentEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				queryPos.add(fragmentCollectionId);

				list = (List<FragmentEntry>)QueryUtil.list(
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
	 * Returns the first fragment entry in the ordered set where groupId = &#63; and fragmentCollectionId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment entry
	 * @throws NoSuchEntryException if a matching fragment entry could not be found
	 */
	@Override
	public FragmentEntry findByG_FCI_First(
			long groupId, long fragmentCollectionId,
			OrderByComparator<FragmentEntry> orderByComparator)
		throws NoSuchEntryException {

		FragmentEntry fragmentEntry = fetchByG_FCI_First(
			groupId, fragmentCollectionId, orderByComparator);

		if (fragmentEntry != null) {
			return fragmentEntry;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", fragmentCollectionId=");
		sb.append(fragmentCollectionId);

		sb.append("}");

		throw new NoSuchEntryException(sb.toString());
	}

	/**
	 * Returns the first fragment entry in the ordered set where groupId = &#63; and fragmentCollectionId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment entry, or <code>null</code> if a matching fragment entry could not be found
	 */
	@Override
	public FragmentEntry fetchByG_FCI_First(
		long groupId, long fragmentCollectionId,
		OrderByComparator<FragmentEntry> orderByComparator) {

		List<FragmentEntry> list = findByG_FCI(
			groupId, fragmentCollectionId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last fragment entry in the ordered set where groupId = &#63; and fragmentCollectionId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching fragment entry
	 * @throws NoSuchEntryException if a matching fragment entry could not be found
	 */
	@Override
	public FragmentEntry findByG_FCI_Last(
			long groupId, long fragmentCollectionId,
			OrderByComparator<FragmentEntry> orderByComparator)
		throws NoSuchEntryException {

		FragmentEntry fragmentEntry = fetchByG_FCI_Last(
			groupId, fragmentCollectionId, orderByComparator);

		if (fragmentEntry != null) {
			return fragmentEntry;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", fragmentCollectionId=");
		sb.append(fragmentCollectionId);

		sb.append("}");

		throw new NoSuchEntryException(sb.toString());
	}

	/**
	 * Returns the last fragment entry in the ordered set where groupId = &#63; and fragmentCollectionId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching fragment entry, or <code>null</code> if a matching fragment entry could not be found
	 */
	@Override
	public FragmentEntry fetchByG_FCI_Last(
		long groupId, long fragmentCollectionId,
		OrderByComparator<FragmentEntry> orderByComparator) {

		int count = countByG_FCI(groupId, fragmentCollectionId);

		if (count == 0) {
			return null;
		}

		List<FragmentEntry> list = findByG_FCI(
			groupId, fragmentCollectionId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the fragment entries before and after the current fragment entry in the ordered set where groupId = &#63; and fragmentCollectionId = &#63;.
	 *
	 * @param fragmentEntryId the primary key of the current fragment entry
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next fragment entry
	 * @throws NoSuchEntryException if a fragment entry with the primary key could not be found
	 */
	@Override
	public FragmentEntry[] findByG_FCI_PrevAndNext(
			long fragmentEntryId, long groupId, long fragmentCollectionId,
			OrderByComparator<FragmentEntry> orderByComparator)
		throws NoSuchEntryException {

		FragmentEntry fragmentEntry = findByPrimaryKey(fragmentEntryId);

		Session session = null;

		try {
			session = openSession();

			FragmentEntry[] array = new FragmentEntryImpl[3];

			array[0] = getByG_FCI_PrevAndNext(
				session, fragmentEntry, groupId, fragmentCollectionId,
				orderByComparator, true);

			array[1] = fragmentEntry;

			array[2] = getByG_FCI_PrevAndNext(
				session, fragmentEntry, groupId, fragmentCollectionId,
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

	protected FragmentEntry getByG_FCI_PrevAndNext(
		Session session, FragmentEntry fragmentEntry, long groupId,
		long fragmentCollectionId,
		OrderByComparator<FragmentEntry> orderByComparator, boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				5 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(4);
		}

		sb.append(_SQL_SELECT_FRAGMENTENTRY_WHERE);

		sb.append(_FINDER_COLUMN_G_FCI_GROUPID_2);

		sb.append(_FINDER_COLUMN_G_FCI_FRAGMENTCOLLECTIONID_2);

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
			sb.append(FragmentEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(groupId);

		queryPos.add(fragmentCollectionId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						fragmentEntry)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<FragmentEntry> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the fragment entries where groupId = &#63; and fragmentCollectionId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 */
	@Override
	public void removeByG_FCI(long groupId, long fragmentCollectionId) {
		for (FragmentEntry fragmentEntry :
				findByG_FCI(
					groupId, fragmentCollectionId, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			remove(fragmentEntry);
		}
	}

	/**
	 * Returns the number of fragment entries where groupId = &#63; and fragmentCollectionId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @return the number of matching fragment entries
	 */
	@Override
	public int countByG_FCI(long groupId, long fragmentCollectionId) {
		boolean productionMode = ctPersistenceHelper.isProductionMode(
			FragmentEntry.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByG_FCI;

			finderArgs = new Object[] {groupId, fragmentCollectionId};

			count = (Long)finderCache.getResult(finderPath, finderArgs, this);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_FRAGMENTENTRY_WHERE);

			sb.append(_FINDER_COLUMN_G_FCI_GROUPID_2);

			sb.append(_FINDER_COLUMN_G_FCI_FRAGMENTCOLLECTIONID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				queryPos.add(fragmentCollectionId);

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

	private static final String _FINDER_COLUMN_G_FCI_GROUPID_2 =
		"fragmentEntry.groupId = ? AND ";

	private static final String _FINDER_COLUMN_G_FCI_FRAGMENTCOLLECTIONID_2 =
		"fragmentEntry.fragmentCollectionId = ?";

	private FinderPath _finderPathWithPaginationFindByG_FCI_Head;
	private FinderPath _finderPathWithoutPaginationFindByG_FCI_Head;
	private FinderPath _finderPathCountByG_FCI_Head;

	/**
	 * Returns all the fragment entries where groupId = &#63; and fragmentCollectionId = &#63; and head = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param head the head
	 * @return the matching fragment entries
	 */
	@Override
	public List<FragmentEntry> findByG_FCI_Head(
		long groupId, long fragmentCollectionId, boolean head) {

		return findByG_FCI_Head(
			groupId, fragmentCollectionId, head, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the fragment entries where groupId = &#63; and fragmentCollectionId = &#63; and head = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param head the head
	 * @param start the lower bound of the range of fragment entries
	 * @param end the upper bound of the range of fragment entries (not inclusive)
	 * @return the range of matching fragment entries
	 */
	@Override
	public List<FragmentEntry> findByG_FCI_Head(
		long groupId, long fragmentCollectionId, boolean head, int start,
		int end) {

		return findByG_FCI_Head(
			groupId, fragmentCollectionId, head, start, end, null);
	}

	/**
	 * Returns an ordered range of all the fragment entries where groupId = &#63; and fragmentCollectionId = &#63; and head = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param head the head
	 * @param start the lower bound of the range of fragment entries
	 * @param end the upper bound of the range of fragment entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching fragment entries
	 */
	@Override
	public List<FragmentEntry> findByG_FCI_Head(
		long groupId, long fragmentCollectionId, boolean head, int start,
		int end, OrderByComparator<FragmentEntry> orderByComparator) {

		return findByG_FCI_Head(
			groupId, fragmentCollectionId, head, start, end, orderByComparator,
			true);
	}

	/**
	 * Returns an ordered range of all the fragment entries where groupId = &#63; and fragmentCollectionId = &#63; and head = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param head the head
	 * @param start the lower bound of the range of fragment entries
	 * @param end the upper bound of the range of fragment entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching fragment entries
	 */
	@Override
	public List<FragmentEntry> findByG_FCI_Head(
		long groupId, long fragmentCollectionId, boolean head, int start,
		int end, OrderByComparator<FragmentEntry> orderByComparator,
		boolean useFinderCache) {

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			FragmentEntry.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache && productionMode) {
				finderPath = _finderPathWithoutPaginationFindByG_FCI_Head;
				finderArgs = new Object[] {groupId, fragmentCollectionId, head};
			}
		}
		else if (useFinderCache && productionMode) {
			finderPath = _finderPathWithPaginationFindByG_FCI_Head;
			finderArgs = new Object[] {
				groupId, fragmentCollectionId, head, start, end,
				orderByComparator
			};
		}

		List<FragmentEntry> list = null;

		if (useFinderCache && productionMode) {
			list = (List<FragmentEntry>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (FragmentEntry fragmentEntry : list) {
					if ((groupId != fragmentEntry.getGroupId()) ||
						(fragmentCollectionId !=
							fragmentEntry.getFragmentCollectionId()) ||
						(head != fragmentEntry.isHead())) {

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

			sb.append(_SQL_SELECT_FRAGMENTENTRY_WHERE);

			sb.append(_FINDER_COLUMN_G_FCI_HEAD_GROUPID_2);

			sb.append(_FINDER_COLUMN_G_FCI_HEAD_FRAGMENTCOLLECTIONID_2);

			sb.append(_FINDER_COLUMN_G_FCI_HEAD_HEAD_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(FragmentEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				queryPos.add(fragmentCollectionId);

				queryPos.add(head);

				list = (List<FragmentEntry>)QueryUtil.list(
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
	 * Returns the first fragment entry in the ordered set where groupId = &#63; and fragmentCollectionId = &#63; and head = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param head the head
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment entry
	 * @throws NoSuchEntryException if a matching fragment entry could not be found
	 */
	@Override
	public FragmentEntry findByG_FCI_Head_First(
			long groupId, long fragmentCollectionId, boolean head,
			OrderByComparator<FragmentEntry> orderByComparator)
		throws NoSuchEntryException {

		FragmentEntry fragmentEntry = fetchByG_FCI_Head_First(
			groupId, fragmentCollectionId, head, orderByComparator);

		if (fragmentEntry != null) {
			return fragmentEntry;
		}

		StringBundler sb = new StringBundler(8);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", fragmentCollectionId=");
		sb.append(fragmentCollectionId);

		sb.append(", head=");
		sb.append(head);

		sb.append("}");

		throw new NoSuchEntryException(sb.toString());
	}

	/**
	 * Returns the first fragment entry in the ordered set where groupId = &#63; and fragmentCollectionId = &#63; and head = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param head the head
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment entry, or <code>null</code> if a matching fragment entry could not be found
	 */
	@Override
	public FragmentEntry fetchByG_FCI_Head_First(
		long groupId, long fragmentCollectionId, boolean head,
		OrderByComparator<FragmentEntry> orderByComparator) {

		List<FragmentEntry> list = findByG_FCI_Head(
			groupId, fragmentCollectionId, head, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last fragment entry in the ordered set where groupId = &#63; and fragmentCollectionId = &#63; and head = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param head the head
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching fragment entry
	 * @throws NoSuchEntryException if a matching fragment entry could not be found
	 */
	@Override
	public FragmentEntry findByG_FCI_Head_Last(
			long groupId, long fragmentCollectionId, boolean head,
			OrderByComparator<FragmentEntry> orderByComparator)
		throws NoSuchEntryException {

		FragmentEntry fragmentEntry = fetchByG_FCI_Head_Last(
			groupId, fragmentCollectionId, head, orderByComparator);

		if (fragmentEntry != null) {
			return fragmentEntry;
		}

		StringBundler sb = new StringBundler(8);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", fragmentCollectionId=");
		sb.append(fragmentCollectionId);

		sb.append(", head=");
		sb.append(head);

		sb.append("}");

		throw new NoSuchEntryException(sb.toString());
	}

	/**
	 * Returns the last fragment entry in the ordered set where groupId = &#63; and fragmentCollectionId = &#63; and head = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param head the head
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching fragment entry, or <code>null</code> if a matching fragment entry could not be found
	 */
	@Override
	public FragmentEntry fetchByG_FCI_Head_Last(
		long groupId, long fragmentCollectionId, boolean head,
		OrderByComparator<FragmentEntry> orderByComparator) {

		int count = countByG_FCI_Head(groupId, fragmentCollectionId, head);

		if (count == 0) {
			return null;
		}

		List<FragmentEntry> list = findByG_FCI_Head(
			groupId, fragmentCollectionId, head, count - 1, count,
			orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the fragment entries before and after the current fragment entry in the ordered set where groupId = &#63; and fragmentCollectionId = &#63; and head = &#63;.
	 *
	 * @param fragmentEntryId the primary key of the current fragment entry
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param head the head
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next fragment entry
	 * @throws NoSuchEntryException if a fragment entry with the primary key could not be found
	 */
	@Override
	public FragmentEntry[] findByG_FCI_Head_PrevAndNext(
			long fragmentEntryId, long groupId, long fragmentCollectionId,
			boolean head, OrderByComparator<FragmentEntry> orderByComparator)
		throws NoSuchEntryException {

		FragmentEntry fragmentEntry = findByPrimaryKey(fragmentEntryId);

		Session session = null;

		try {
			session = openSession();

			FragmentEntry[] array = new FragmentEntryImpl[3];

			array[0] = getByG_FCI_Head_PrevAndNext(
				session, fragmentEntry, groupId, fragmentCollectionId, head,
				orderByComparator, true);

			array[1] = fragmentEntry;

			array[2] = getByG_FCI_Head_PrevAndNext(
				session, fragmentEntry, groupId, fragmentCollectionId, head,
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

	protected FragmentEntry getByG_FCI_Head_PrevAndNext(
		Session session, FragmentEntry fragmentEntry, long groupId,
		long fragmentCollectionId, boolean head,
		OrderByComparator<FragmentEntry> orderByComparator, boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				6 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(5);
		}

		sb.append(_SQL_SELECT_FRAGMENTENTRY_WHERE);

		sb.append(_FINDER_COLUMN_G_FCI_HEAD_GROUPID_2);

		sb.append(_FINDER_COLUMN_G_FCI_HEAD_FRAGMENTCOLLECTIONID_2);

		sb.append(_FINDER_COLUMN_G_FCI_HEAD_HEAD_2);

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
			sb.append(FragmentEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(groupId);

		queryPos.add(fragmentCollectionId);

		queryPos.add(head);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						fragmentEntry)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<FragmentEntry> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the fragment entries where groupId = &#63; and fragmentCollectionId = &#63; and head = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param head the head
	 */
	@Override
	public void removeByG_FCI_Head(
		long groupId, long fragmentCollectionId, boolean head) {

		for (FragmentEntry fragmentEntry :
				findByG_FCI_Head(
					groupId, fragmentCollectionId, head, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			remove(fragmentEntry);
		}
	}

	/**
	 * Returns the number of fragment entries where groupId = &#63; and fragmentCollectionId = &#63; and head = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param head the head
	 * @return the number of matching fragment entries
	 */
	@Override
	public int countByG_FCI_Head(
		long groupId, long fragmentCollectionId, boolean head) {

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			FragmentEntry.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByG_FCI_Head;

			finderArgs = new Object[] {groupId, fragmentCollectionId, head};

			count = (Long)finderCache.getResult(finderPath, finderArgs, this);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_COUNT_FRAGMENTENTRY_WHERE);

			sb.append(_FINDER_COLUMN_G_FCI_HEAD_GROUPID_2);

			sb.append(_FINDER_COLUMN_G_FCI_HEAD_FRAGMENTCOLLECTIONID_2);

			sb.append(_FINDER_COLUMN_G_FCI_HEAD_HEAD_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				queryPos.add(fragmentCollectionId);

				queryPos.add(head);

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

	private static final String _FINDER_COLUMN_G_FCI_HEAD_GROUPID_2 =
		"fragmentEntry.groupId = ? AND ";

	private static final String
		_FINDER_COLUMN_G_FCI_HEAD_FRAGMENTCOLLECTIONID_2 =
			"fragmentEntry.fragmentCollectionId = ? AND ";

	private static final String _FINDER_COLUMN_G_FCI_HEAD_HEAD_2 =
		"fragmentEntry.head = ?";

	private FinderPath _finderPathWithPaginationFindByG_FEK;
	private FinderPath _finderPathWithoutPaginationFindByG_FEK;
	private FinderPath _finderPathCountByG_FEK;

	/**
	 * Returns all the fragment entries where groupId = &#63; and fragmentEntryKey = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentEntryKey the fragment entry key
	 * @return the matching fragment entries
	 */
	@Override
	public List<FragmentEntry> findByG_FEK(
		long groupId, String fragmentEntryKey) {

		return findByG_FEK(
			groupId, fragmentEntryKey, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the fragment entries where groupId = &#63; and fragmentEntryKey = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param fragmentEntryKey the fragment entry key
	 * @param start the lower bound of the range of fragment entries
	 * @param end the upper bound of the range of fragment entries (not inclusive)
	 * @return the range of matching fragment entries
	 */
	@Override
	public List<FragmentEntry> findByG_FEK(
		long groupId, String fragmentEntryKey, int start, int end) {

		return findByG_FEK(groupId, fragmentEntryKey, start, end, null);
	}

	/**
	 * Returns an ordered range of all the fragment entries where groupId = &#63; and fragmentEntryKey = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param fragmentEntryKey the fragment entry key
	 * @param start the lower bound of the range of fragment entries
	 * @param end the upper bound of the range of fragment entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching fragment entries
	 */
	@Override
	public List<FragmentEntry> findByG_FEK(
		long groupId, String fragmentEntryKey, int start, int end,
		OrderByComparator<FragmentEntry> orderByComparator) {

		return findByG_FEK(
			groupId, fragmentEntryKey, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the fragment entries where groupId = &#63; and fragmentEntryKey = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param fragmentEntryKey the fragment entry key
	 * @param start the lower bound of the range of fragment entries
	 * @param end the upper bound of the range of fragment entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching fragment entries
	 */
	@Override
	public List<FragmentEntry> findByG_FEK(
		long groupId, String fragmentEntryKey, int start, int end,
		OrderByComparator<FragmentEntry> orderByComparator,
		boolean useFinderCache) {

		fragmentEntryKey = Objects.toString(fragmentEntryKey, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			FragmentEntry.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache && productionMode) {
				finderPath = _finderPathWithoutPaginationFindByG_FEK;
				finderArgs = new Object[] {groupId, fragmentEntryKey};
			}
		}
		else if (useFinderCache && productionMode) {
			finderPath = _finderPathWithPaginationFindByG_FEK;
			finderArgs = new Object[] {
				groupId, fragmentEntryKey, start, end, orderByComparator
			};
		}

		List<FragmentEntry> list = null;

		if (useFinderCache && productionMode) {
			list = (List<FragmentEntry>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (FragmentEntry fragmentEntry : list) {
					if ((groupId != fragmentEntry.getGroupId()) ||
						!fragmentEntryKey.equals(
							fragmentEntry.getFragmentEntryKey())) {

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

			sb.append(_SQL_SELECT_FRAGMENTENTRY_WHERE);

			sb.append(_FINDER_COLUMN_G_FEK_GROUPID_2);

			boolean bindFragmentEntryKey = false;

			if (fragmentEntryKey.isEmpty()) {
				sb.append(_FINDER_COLUMN_G_FEK_FRAGMENTENTRYKEY_3);
			}
			else {
				bindFragmentEntryKey = true;

				sb.append(_FINDER_COLUMN_G_FEK_FRAGMENTENTRYKEY_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(FragmentEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				if (bindFragmentEntryKey) {
					queryPos.add(fragmentEntryKey);
				}

				list = (List<FragmentEntry>)QueryUtil.list(
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
	 * Returns the first fragment entry in the ordered set where groupId = &#63; and fragmentEntryKey = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentEntryKey the fragment entry key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment entry
	 * @throws NoSuchEntryException if a matching fragment entry could not be found
	 */
	@Override
	public FragmentEntry findByG_FEK_First(
			long groupId, String fragmentEntryKey,
			OrderByComparator<FragmentEntry> orderByComparator)
		throws NoSuchEntryException {

		FragmentEntry fragmentEntry = fetchByG_FEK_First(
			groupId, fragmentEntryKey, orderByComparator);

		if (fragmentEntry != null) {
			return fragmentEntry;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", fragmentEntryKey=");
		sb.append(fragmentEntryKey);

		sb.append("}");

		throw new NoSuchEntryException(sb.toString());
	}

	/**
	 * Returns the first fragment entry in the ordered set where groupId = &#63; and fragmentEntryKey = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentEntryKey the fragment entry key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment entry, or <code>null</code> if a matching fragment entry could not be found
	 */
	@Override
	public FragmentEntry fetchByG_FEK_First(
		long groupId, String fragmentEntryKey,
		OrderByComparator<FragmentEntry> orderByComparator) {

		List<FragmentEntry> list = findByG_FEK(
			groupId, fragmentEntryKey, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last fragment entry in the ordered set where groupId = &#63; and fragmentEntryKey = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentEntryKey the fragment entry key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching fragment entry
	 * @throws NoSuchEntryException if a matching fragment entry could not be found
	 */
	@Override
	public FragmentEntry findByG_FEK_Last(
			long groupId, String fragmentEntryKey,
			OrderByComparator<FragmentEntry> orderByComparator)
		throws NoSuchEntryException {

		FragmentEntry fragmentEntry = fetchByG_FEK_Last(
			groupId, fragmentEntryKey, orderByComparator);

		if (fragmentEntry != null) {
			return fragmentEntry;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", fragmentEntryKey=");
		sb.append(fragmentEntryKey);

		sb.append("}");

		throw new NoSuchEntryException(sb.toString());
	}

	/**
	 * Returns the last fragment entry in the ordered set where groupId = &#63; and fragmentEntryKey = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentEntryKey the fragment entry key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching fragment entry, or <code>null</code> if a matching fragment entry could not be found
	 */
	@Override
	public FragmentEntry fetchByG_FEK_Last(
		long groupId, String fragmentEntryKey,
		OrderByComparator<FragmentEntry> orderByComparator) {

		int count = countByG_FEK(groupId, fragmentEntryKey);

		if (count == 0) {
			return null;
		}

		List<FragmentEntry> list = findByG_FEK(
			groupId, fragmentEntryKey, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the fragment entries before and after the current fragment entry in the ordered set where groupId = &#63; and fragmentEntryKey = &#63;.
	 *
	 * @param fragmentEntryId the primary key of the current fragment entry
	 * @param groupId the group ID
	 * @param fragmentEntryKey the fragment entry key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next fragment entry
	 * @throws NoSuchEntryException if a fragment entry with the primary key could not be found
	 */
	@Override
	public FragmentEntry[] findByG_FEK_PrevAndNext(
			long fragmentEntryId, long groupId, String fragmentEntryKey,
			OrderByComparator<FragmentEntry> orderByComparator)
		throws NoSuchEntryException {

		fragmentEntryKey = Objects.toString(fragmentEntryKey, "");

		FragmentEntry fragmentEntry = findByPrimaryKey(fragmentEntryId);

		Session session = null;

		try {
			session = openSession();

			FragmentEntry[] array = new FragmentEntryImpl[3];

			array[0] = getByG_FEK_PrevAndNext(
				session, fragmentEntry, groupId, fragmentEntryKey,
				orderByComparator, true);

			array[1] = fragmentEntry;

			array[2] = getByG_FEK_PrevAndNext(
				session, fragmentEntry, groupId, fragmentEntryKey,
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

	protected FragmentEntry getByG_FEK_PrevAndNext(
		Session session, FragmentEntry fragmentEntry, long groupId,
		String fragmentEntryKey,
		OrderByComparator<FragmentEntry> orderByComparator, boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				5 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(4);
		}

		sb.append(_SQL_SELECT_FRAGMENTENTRY_WHERE);

		sb.append(_FINDER_COLUMN_G_FEK_GROUPID_2);

		boolean bindFragmentEntryKey = false;

		if (fragmentEntryKey.isEmpty()) {
			sb.append(_FINDER_COLUMN_G_FEK_FRAGMENTENTRYKEY_3);
		}
		else {
			bindFragmentEntryKey = true;

			sb.append(_FINDER_COLUMN_G_FEK_FRAGMENTENTRYKEY_2);
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
			sb.append(FragmentEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(groupId);

		if (bindFragmentEntryKey) {
			queryPos.add(fragmentEntryKey);
		}

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						fragmentEntry)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<FragmentEntry> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the fragment entries where groupId = &#63; and fragmentEntryKey = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param fragmentEntryKey the fragment entry key
	 */
	@Override
	public void removeByG_FEK(long groupId, String fragmentEntryKey) {
		for (FragmentEntry fragmentEntry :
				findByG_FEK(
					groupId, fragmentEntryKey, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			remove(fragmentEntry);
		}
	}

	/**
	 * Returns the number of fragment entries where groupId = &#63; and fragmentEntryKey = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentEntryKey the fragment entry key
	 * @return the number of matching fragment entries
	 */
	@Override
	public int countByG_FEK(long groupId, String fragmentEntryKey) {
		fragmentEntryKey = Objects.toString(fragmentEntryKey, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			FragmentEntry.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByG_FEK;

			finderArgs = new Object[] {groupId, fragmentEntryKey};

			count = (Long)finderCache.getResult(finderPath, finderArgs, this);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_FRAGMENTENTRY_WHERE);

			sb.append(_FINDER_COLUMN_G_FEK_GROUPID_2);

			boolean bindFragmentEntryKey = false;

			if (fragmentEntryKey.isEmpty()) {
				sb.append(_FINDER_COLUMN_G_FEK_FRAGMENTENTRYKEY_3);
			}
			else {
				bindFragmentEntryKey = true;

				sb.append(_FINDER_COLUMN_G_FEK_FRAGMENTENTRYKEY_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				if (bindFragmentEntryKey) {
					queryPos.add(fragmentEntryKey);
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

	private static final String _FINDER_COLUMN_G_FEK_GROUPID_2 =
		"fragmentEntry.groupId = ? AND ";

	private static final String _FINDER_COLUMN_G_FEK_FRAGMENTENTRYKEY_2 =
		"fragmentEntry.fragmentEntryKey = ?";

	private static final String _FINDER_COLUMN_G_FEK_FRAGMENTENTRYKEY_3 =
		"(fragmentEntry.fragmentEntryKey IS NULL OR fragmentEntry.fragmentEntryKey = '')";

	private FinderPath _finderPathFetchByG_FEK_Head;
	private FinderPath _finderPathCountByG_FEK_Head;

	/**
	 * Returns the fragment entry where groupId = &#63; and fragmentEntryKey = &#63; and head = &#63; or throws a <code>NoSuchEntryException</code> if it could not be found.
	 *
	 * @param groupId the group ID
	 * @param fragmentEntryKey the fragment entry key
	 * @param head the head
	 * @return the matching fragment entry
	 * @throws NoSuchEntryException if a matching fragment entry could not be found
	 */
	@Override
	public FragmentEntry findByG_FEK_Head(
			long groupId, String fragmentEntryKey, boolean head)
		throws NoSuchEntryException {

		FragmentEntry fragmentEntry = fetchByG_FEK_Head(
			groupId, fragmentEntryKey, head);

		if (fragmentEntry == null) {
			StringBundler sb = new StringBundler(8);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("groupId=");
			sb.append(groupId);

			sb.append(", fragmentEntryKey=");
			sb.append(fragmentEntryKey);

			sb.append(", head=");
			sb.append(head);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchEntryException(sb.toString());
		}

		return fragmentEntry;
	}

	/**
	 * Returns the fragment entry where groupId = &#63; and fragmentEntryKey = &#63; and head = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param groupId the group ID
	 * @param fragmentEntryKey the fragment entry key
	 * @param head the head
	 * @return the matching fragment entry, or <code>null</code> if a matching fragment entry could not be found
	 */
	@Override
	public FragmentEntry fetchByG_FEK_Head(
		long groupId, String fragmentEntryKey, boolean head) {

		return fetchByG_FEK_Head(groupId, fragmentEntryKey, head, true);
	}

	/**
	 * Returns the fragment entry where groupId = &#63; and fragmentEntryKey = &#63; and head = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param groupId the group ID
	 * @param fragmentEntryKey the fragment entry key
	 * @param head the head
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching fragment entry, or <code>null</code> if a matching fragment entry could not be found
	 */
	@Override
	public FragmentEntry fetchByG_FEK_Head(
		long groupId, String fragmentEntryKey, boolean head,
		boolean useFinderCache) {

		fragmentEntryKey = Objects.toString(fragmentEntryKey, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			FragmentEntry.class);

		Object[] finderArgs = null;

		if (useFinderCache && productionMode) {
			finderArgs = new Object[] {groupId, fragmentEntryKey, head};
		}

		Object result = null;

		if (useFinderCache && productionMode) {
			result = finderCache.getResult(
				_finderPathFetchByG_FEK_Head, finderArgs, this);
		}

		if (result instanceof FragmentEntry) {
			FragmentEntry fragmentEntry = (FragmentEntry)result;

			if ((groupId != fragmentEntry.getGroupId()) ||
				!Objects.equals(
					fragmentEntryKey, fragmentEntry.getFragmentEntryKey()) ||
				(head != fragmentEntry.isHead())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(5);

			sb.append(_SQL_SELECT_FRAGMENTENTRY_WHERE);

			sb.append(_FINDER_COLUMN_G_FEK_HEAD_GROUPID_2);

			boolean bindFragmentEntryKey = false;

			if (fragmentEntryKey.isEmpty()) {
				sb.append(_FINDER_COLUMN_G_FEK_HEAD_FRAGMENTENTRYKEY_3);
			}
			else {
				bindFragmentEntryKey = true;

				sb.append(_FINDER_COLUMN_G_FEK_HEAD_FRAGMENTENTRYKEY_2);
			}

			sb.append(_FINDER_COLUMN_G_FEK_HEAD_HEAD_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				if (bindFragmentEntryKey) {
					queryPos.add(fragmentEntryKey);
				}

				queryPos.add(head);

				List<FragmentEntry> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache && productionMode) {
						finderCache.putResult(
							_finderPathFetchByG_FEK_Head, finderArgs, list);
					}
				}
				else {
					FragmentEntry fragmentEntry = list.get(0);

					result = fragmentEntry;

					cacheResult(fragmentEntry);
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
			return (FragmentEntry)result;
		}
	}

	/**
	 * Removes the fragment entry where groupId = &#63; and fragmentEntryKey = &#63; and head = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param fragmentEntryKey the fragment entry key
	 * @param head the head
	 * @return the fragment entry that was removed
	 */
	@Override
	public FragmentEntry removeByG_FEK_Head(
			long groupId, String fragmentEntryKey, boolean head)
		throws NoSuchEntryException {

		FragmentEntry fragmentEntry = findByG_FEK_Head(
			groupId, fragmentEntryKey, head);

		return remove(fragmentEntry);
	}

	/**
	 * Returns the number of fragment entries where groupId = &#63; and fragmentEntryKey = &#63; and head = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentEntryKey the fragment entry key
	 * @param head the head
	 * @return the number of matching fragment entries
	 */
	@Override
	public int countByG_FEK_Head(
		long groupId, String fragmentEntryKey, boolean head) {

		fragmentEntryKey = Objects.toString(fragmentEntryKey, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			FragmentEntry.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByG_FEK_Head;

			finderArgs = new Object[] {groupId, fragmentEntryKey, head};

			count = (Long)finderCache.getResult(finderPath, finderArgs, this);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_COUNT_FRAGMENTENTRY_WHERE);

			sb.append(_FINDER_COLUMN_G_FEK_HEAD_GROUPID_2);

			boolean bindFragmentEntryKey = false;

			if (fragmentEntryKey.isEmpty()) {
				sb.append(_FINDER_COLUMN_G_FEK_HEAD_FRAGMENTENTRYKEY_3);
			}
			else {
				bindFragmentEntryKey = true;

				sb.append(_FINDER_COLUMN_G_FEK_HEAD_FRAGMENTENTRYKEY_2);
			}

			sb.append(_FINDER_COLUMN_G_FEK_HEAD_HEAD_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				if (bindFragmentEntryKey) {
					queryPos.add(fragmentEntryKey);
				}

				queryPos.add(head);

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

	private static final String _FINDER_COLUMN_G_FEK_HEAD_GROUPID_2 =
		"fragmentEntry.groupId = ? AND ";

	private static final String _FINDER_COLUMN_G_FEK_HEAD_FRAGMENTENTRYKEY_2 =
		"fragmentEntry.fragmentEntryKey = ? AND ";

	private static final String _FINDER_COLUMN_G_FEK_HEAD_FRAGMENTENTRYKEY_3 =
		"(fragmentEntry.fragmentEntryKey IS NULL OR fragmentEntry.fragmentEntryKey = '') AND ";

	private static final String _FINDER_COLUMN_G_FEK_HEAD_HEAD_2 =
		"fragmentEntry.head = ?";

	private FinderPath _finderPathWithPaginationFindByG_FCI_LikeN;
	private FinderPath _finderPathWithPaginationCountByG_FCI_LikeN;

	/**
	 * Returns all the fragment entries where groupId = &#63; and fragmentCollectionId = &#63; and name LIKE &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param name the name
	 * @return the matching fragment entries
	 */
	@Override
	public List<FragmentEntry> findByG_FCI_LikeN(
		long groupId, long fragmentCollectionId, String name) {

		return findByG_FCI_LikeN(
			groupId, fragmentCollectionId, name, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the fragment entries where groupId = &#63; and fragmentCollectionId = &#63; and name LIKE &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param name the name
	 * @param start the lower bound of the range of fragment entries
	 * @param end the upper bound of the range of fragment entries (not inclusive)
	 * @return the range of matching fragment entries
	 */
	@Override
	public List<FragmentEntry> findByG_FCI_LikeN(
		long groupId, long fragmentCollectionId, String name, int start,
		int end) {

		return findByG_FCI_LikeN(
			groupId, fragmentCollectionId, name, start, end, null);
	}

	/**
	 * Returns an ordered range of all the fragment entries where groupId = &#63; and fragmentCollectionId = &#63; and name LIKE &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param name the name
	 * @param start the lower bound of the range of fragment entries
	 * @param end the upper bound of the range of fragment entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching fragment entries
	 */
	@Override
	public List<FragmentEntry> findByG_FCI_LikeN(
		long groupId, long fragmentCollectionId, String name, int start,
		int end, OrderByComparator<FragmentEntry> orderByComparator) {

		return findByG_FCI_LikeN(
			groupId, fragmentCollectionId, name, start, end, orderByComparator,
			true);
	}

	/**
	 * Returns an ordered range of all the fragment entries where groupId = &#63; and fragmentCollectionId = &#63; and name LIKE &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param name the name
	 * @param start the lower bound of the range of fragment entries
	 * @param end the upper bound of the range of fragment entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching fragment entries
	 */
	@Override
	public List<FragmentEntry> findByG_FCI_LikeN(
		long groupId, long fragmentCollectionId, String name, int start,
		int end, OrderByComparator<FragmentEntry> orderByComparator,
		boolean useFinderCache) {

		name = Objects.toString(name, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			FragmentEntry.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		finderPath = _finderPathWithPaginationFindByG_FCI_LikeN;
		finderArgs = new Object[] {
			groupId, fragmentCollectionId, name, start, end, orderByComparator
		};

		List<FragmentEntry> list = null;

		if (useFinderCache && productionMode) {
			list = (List<FragmentEntry>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (FragmentEntry fragmentEntry : list) {
					if ((groupId != fragmentEntry.getGroupId()) ||
						(fragmentCollectionId !=
							fragmentEntry.getFragmentCollectionId()) ||
						!StringUtil.wildcardMatches(
							fragmentEntry.getName(), name, '_', '%', '\\',
							true)) {

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

			sb.append(_SQL_SELECT_FRAGMENTENTRY_WHERE);

			sb.append(_FINDER_COLUMN_G_FCI_LIKEN_GROUPID_2);

			sb.append(_FINDER_COLUMN_G_FCI_LIKEN_FRAGMENTCOLLECTIONID_2);

			boolean bindName = false;

			if (name.isEmpty()) {
				sb.append(_FINDER_COLUMN_G_FCI_LIKEN_NAME_3);
			}
			else {
				bindName = true;

				sb.append(_FINDER_COLUMN_G_FCI_LIKEN_NAME_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(FragmentEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				queryPos.add(fragmentCollectionId);

				if (bindName) {
					queryPos.add(name);
				}

				list = (List<FragmentEntry>)QueryUtil.list(
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
	 * Returns the first fragment entry in the ordered set where groupId = &#63; and fragmentCollectionId = &#63; and name LIKE &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment entry
	 * @throws NoSuchEntryException if a matching fragment entry could not be found
	 */
	@Override
	public FragmentEntry findByG_FCI_LikeN_First(
			long groupId, long fragmentCollectionId, String name,
			OrderByComparator<FragmentEntry> orderByComparator)
		throws NoSuchEntryException {

		FragmentEntry fragmentEntry = fetchByG_FCI_LikeN_First(
			groupId, fragmentCollectionId, name, orderByComparator);

		if (fragmentEntry != null) {
			return fragmentEntry;
		}

		StringBundler sb = new StringBundler(8);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", fragmentCollectionId=");
		sb.append(fragmentCollectionId);

		sb.append(", nameLIKE");
		sb.append(name);

		sb.append("}");

		throw new NoSuchEntryException(sb.toString());
	}

	/**
	 * Returns the first fragment entry in the ordered set where groupId = &#63; and fragmentCollectionId = &#63; and name LIKE &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment entry, or <code>null</code> if a matching fragment entry could not be found
	 */
	@Override
	public FragmentEntry fetchByG_FCI_LikeN_First(
		long groupId, long fragmentCollectionId, String name,
		OrderByComparator<FragmentEntry> orderByComparator) {

		List<FragmentEntry> list = findByG_FCI_LikeN(
			groupId, fragmentCollectionId, name, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last fragment entry in the ordered set where groupId = &#63; and fragmentCollectionId = &#63; and name LIKE &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching fragment entry
	 * @throws NoSuchEntryException if a matching fragment entry could not be found
	 */
	@Override
	public FragmentEntry findByG_FCI_LikeN_Last(
			long groupId, long fragmentCollectionId, String name,
			OrderByComparator<FragmentEntry> orderByComparator)
		throws NoSuchEntryException {

		FragmentEntry fragmentEntry = fetchByG_FCI_LikeN_Last(
			groupId, fragmentCollectionId, name, orderByComparator);

		if (fragmentEntry != null) {
			return fragmentEntry;
		}

		StringBundler sb = new StringBundler(8);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", fragmentCollectionId=");
		sb.append(fragmentCollectionId);

		sb.append(", nameLIKE");
		sb.append(name);

		sb.append("}");

		throw new NoSuchEntryException(sb.toString());
	}

	/**
	 * Returns the last fragment entry in the ordered set where groupId = &#63; and fragmentCollectionId = &#63; and name LIKE &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching fragment entry, or <code>null</code> if a matching fragment entry could not be found
	 */
	@Override
	public FragmentEntry fetchByG_FCI_LikeN_Last(
		long groupId, long fragmentCollectionId, String name,
		OrderByComparator<FragmentEntry> orderByComparator) {

		int count = countByG_FCI_LikeN(groupId, fragmentCollectionId, name);

		if (count == 0) {
			return null;
		}

		List<FragmentEntry> list = findByG_FCI_LikeN(
			groupId, fragmentCollectionId, name, count - 1, count,
			orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the fragment entries before and after the current fragment entry in the ordered set where groupId = &#63; and fragmentCollectionId = &#63; and name LIKE &#63;.
	 *
	 * @param fragmentEntryId the primary key of the current fragment entry
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next fragment entry
	 * @throws NoSuchEntryException if a fragment entry with the primary key could not be found
	 */
	@Override
	public FragmentEntry[] findByG_FCI_LikeN_PrevAndNext(
			long fragmentEntryId, long groupId, long fragmentCollectionId,
			String name, OrderByComparator<FragmentEntry> orderByComparator)
		throws NoSuchEntryException {

		name = Objects.toString(name, "");

		FragmentEntry fragmentEntry = findByPrimaryKey(fragmentEntryId);

		Session session = null;

		try {
			session = openSession();

			FragmentEntry[] array = new FragmentEntryImpl[3];

			array[0] = getByG_FCI_LikeN_PrevAndNext(
				session, fragmentEntry, groupId, fragmentCollectionId, name,
				orderByComparator, true);

			array[1] = fragmentEntry;

			array[2] = getByG_FCI_LikeN_PrevAndNext(
				session, fragmentEntry, groupId, fragmentCollectionId, name,
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

	protected FragmentEntry getByG_FCI_LikeN_PrevAndNext(
		Session session, FragmentEntry fragmentEntry, long groupId,
		long fragmentCollectionId, String name,
		OrderByComparator<FragmentEntry> orderByComparator, boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				6 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(5);
		}

		sb.append(_SQL_SELECT_FRAGMENTENTRY_WHERE);

		sb.append(_FINDER_COLUMN_G_FCI_LIKEN_GROUPID_2);

		sb.append(_FINDER_COLUMN_G_FCI_LIKEN_FRAGMENTCOLLECTIONID_2);

		boolean bindName = false;

		if (name.isEmpty()) {
			sb.append(_FINDER_COLUMN_G_FCI_LIKEN_NAME_3);
		}
		else {
			bindName = true;

			sb.append(_FINDER_COLUMN_G_FCI_LIKEN_NAME_2);
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
			sb.append(FragmentEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(groupId);

		queryPos.add(fragmentCollectionId);

		if (bindName) {
			queryPos.add(name);
		}

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						fragmentEntry)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<FragmentEntry> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the fragment entries where groupId = &#63; and fragmentCollectionId = &#63; and name LIKE &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param name the name
	 */
	@Override
	public void removeByG_FCI_LikeN(
		long groupId, long fragmentCollectionId, String name) {

		for (FragmentEntry fragmentEntry :
				findByG_FCI_LikeN(
					groupId, fragmentCollectionId, name, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			remove(fragmentEntry);
		}
	}

	/**
	 * Returns the number of fragment entries where groupId = &#63; and fragmentCollectionId = &#63; and name LIKE &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param name the name
	 * @return the number of matching fragment entries
	 */
	@Override
	public int countByG_FCI_LikeN(
		long groupId, long fragmentCollectionId, String name) {

		name = Objects.toString(name, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			FragmentEntry.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathWithPaginationCountByG_FCI_LikeN;

			finderArgs = new Object[] {groupId, fragmentCollectionId, name};

			count = (Long)finderCache.getResult(finderPath, finderArgs, this);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_COUNT_FRAGMENTENTRY_WHERE);

			sb.append(_FINDER_COLUMN_G_FCI_LIKEN_GROUPID_2);

			sb.append(_FINDER_COLUMN_G_FCI_LIKEN_FRAGMENTCOLLECTIONID_2);

			boolean bindName = false;

			if (name.isEmpty()) {
				sb.append(_FINDER_COLUMN_G_FCI_LIKEN_NAME_3);
			}
			else {
				bindName = true;

				sb.append(_FINDER_COLUMN_G_FCI_LIKEN_NAME_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				queryPos.add(fragmentCollectionId);

				if (bindName) {
					queryPos.add(name);
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

	private static final String _FINDER_COLUMN_G_FCI_LIKEN_GROUPID_2 =
		"fragmentEntry.groupId = ? AND ";

	private static final String
		_FINDER_COLUMN_G_FCI_LIKEN_FRAGMENTCOLLECTIONID_2 =
			"fragmentEntry.fragmentCollectionId = ? AND ";

	private static final String _FINDER_COLUMN_G_FCI_LIKEN_NAME_2 =
		"fragmentEntry.name LIKE ?";

	private static final String _FINDER_COLUMN_G_FCI_LIKEN_NAME_3 =
		"(fragmentEntry.name IS NULL OR fragmentEntry.name LIKE '')";

	private FinderPath _finderPathWithPaginationFindByG_FCI_LikeN_Head;
	private FinderPath _finderPathWithPaginationCountByG_FCI_LikeN_Head;

	/**
	 * Returns all the fragment entries where groupId = &#63; and fragmentCollectionId = &#63; and name LIKE &#63; and head = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param name the name
	 * @param head the head
	 * @return the matching fragment entries
	 */
	@Override
	public List<FragmentEntry> findByG_FCI_LikeN_Head(
		long groupId, long fragmentCollectionId, String name, boolean head) {

		return findByG_FCI_LikeN_Head(
			groupId, fragmentCollectionId, name, head, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the fragment entries where groupId = &#63; and fragmentCollectionId = &#63; and name LIKE &#63; and head = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param name the name
	 * @param head the head
	 * @param start the lower bound of the range of fragment entries
	 * @param end the upper bound of the range of fragment entries (not inclusive)
	 * @return the range of matching fragment entries
	 */
	@Override
	public List<FragmentEntry> findByG_FCI_LikeN_Head(
		long groupId, long fragmentCollectionId, String name, boolean head,
		int start, int end) {

		return findByG_FCI_LikeN_Head(
			groupId, fragmentCollectionId, name, head, start, end, null);
	}

	/**
	 * Returns an ordered range of all the fragment entries where groupId = &#63; and fragmentCollectionId = &#63; and name LIKE &#63; and head = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param name the name
	 * @param head the head
	 * @param start the lower bound of the range of fragment entries
	 * @param end the upper bound of the range of fragment entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching fragment entries
	 */
	@Override
	public List<FragmentEntry> findByG_FCI_LikeN_Head(
		long groupId, long fragmentCollectionId, String name, boolean head,
		int start, int end,
		OrderByComparator<FragmentEntry> orderByComparator) {

		return findByG_FCI_LikeN_Head(
			groupId, fragmentCollectionId, name, head, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the fragment entries where groupId = &#63; and fragmentCollectionId = &#63; and name LIKE &#63; and head = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param name the name
	 * @param head the head
	 * @param start the lower bound of the range of fragment entries
	 * @param end the upper bound of the range of fragment entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching fragment entries
	 */
	@Override
	public List<FragmentEntry> findByG_FCI_LikeN_Head(
		long groupId, long fragmentCollectionId, String name, boolean head,
		int start, int end, OrderByComparator<FragmentEntry> orderByComparator,
		boolean useFinderCache) {

		name = Objects.toString(name, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			FragmentEntry.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		finderPath = _finderPathWithPaginationFindByG_FCI_LikeN_Head;
		finderArgs = new Object[] {
			groupId, fragmentCollectionId, name, head, start, end,
			orderByComparator
		};

		List<FragmentEntry> list = null;

		if (useFinderCache && productionMode) {
			list = (List<FragmentEntry>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (FragmentEntry fragmentEntry : list) {
					if ((groupId != fragmentEntry.getGroupId()) ||
						(fragmentCollectionId !=
							fragmentEntry.getFragmentCollectionId()) ||
						!StringUtil.wildcardMatches(
							fragmentEntry.getName(), name, '_', '%', '\\',
							true) ||
						(head != fragmentEntry.isHead())) {

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

			sb.append(_SQL_SELECT_FRAGMENTENTRY_WHERE);

			sb.append(_FINDER_COLUMN_G_FCI_LIKEN_HEAD_GROUPID_2);

			sb.append(_FINDER_COLUMN_G_FCI_LIKEN_HEAD_FRAGMENTCOLLECTIONID_2);

			boolean bindName = false;

			if (name.isEmpty()) {
				sb.append(_FINDER_COLUMN_G_FCI_LIKEN_HEAD_NAME_3);
			}
			else {
				bindName = true;

				sb.append(_FINDER_COLUMN_G_FCI_LIKEN_HEAD_NAME_2);
			}

			sb.append(_FINDER_COLUMN_G_FCI_LIKEN_HEAD_HEAD_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(FragmentEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				queryPos.add(fragmentCollectionId);

				if (bindName) {
					queryPos.add(name);
				}

				queryPos.add(head);

				list = (List<FragmentEntry>)QueryUtil.list(
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
	 * Returns the first fragment entry in the ordered set where groupId = &#63; and fragmentCollectionId = &#63; and name LIKE &#63; and head = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param name the name
	 * @param head the head
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment entry
	 * @throws NoSuchEntryException if a matching fragment entry could not be found
	 */
	@Override
	public FragmentEntry findByG_FCI_LikeN_Head_First(
			long groupId, long fragmentCollectionId, String name, boolean head,
			OrderByComparator<FragmentEntry> orderByComparator)
		throws NoSuchEntryException {

		FragmentEntry fragmentEntry = fetchByG_FCI_LikeN_Head_First(
			groupId, fragmentCollectionId, name, head, orderByComparator);

		if (fragmentEntry != null) {
			return fragmentEntry;
		}

		StringBundler sb = new StringBundler(10);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", fragmentCollectionId=");
		sb.append(fragmentCollectionId);

		sb.append(", nameLIKE");
		sb.append(name);

		sb.append(", head=");
		sb.append(head);

		sb.append("}");

		throw new NoSuchEntryException(sb.toString());
	}

	/**
	 * Returns the first fragment entry in the ordered set where groupId = &#63; and fragmentCollectionId = &#63; and name LIKE &#63; and head = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param name the name
	 * @param head the head
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment entry, or <code>null</code> if a matching fragment entry could not be found
	 */
	@Override
	public FragmentEntry fetchByG_FCI_LikeN_Head_First(
		long groupId, long fragmentCollectionId, String name, boolean head,
		OrderByComparator<FragmentEntry> orderByComparator) {

		List<FragmentEntry> list = findByG_FCI_LikeN_Head(
			groupId, fragmentCollectionId, name, head, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last fragment entry in the ordered set where groupId = &#63; and fragmentCollectionId = &#63; and name LIKE &#63; and head = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param name the name
	 * @param head the head
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching fragment entry
	 * @throws NoSuchEntryException if a matching fragment entry could not be found
	 */
	@Override
	public FragmentEntry findByG_FCI_LikeN_Head_Last(
			long groupId, long fragmentCollectionId, String name, boolean head,
			OrderByComparator<FragmentEntry> orderByComparator)
		throws NoSuchEntryException {

		FragmentEntry fragmentEntry = fetchByG_FCI_LikeN_Head_Last(
			groupId, fragmentCollectionId, name, head, orderByComparator);

		if (fragmentEntry != null) {
			return fragmentEntry;
		}

		StringBundler sb = new StringBundler(10);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", fragmentCollectionId=");
		sb.append(fragmentCollectionId);

		sb.append(", nameLIKE");
		sb.append(name);

		sb.append(", head=");
		sb.append(head);

		sb.append("}");

		throw new NoSuchEntryException(sb.toString());
	}

	/**
	 * Returns the last fragment entry in the ordered set where groupId = &#63; and fragmentCollectionId = &#63; and name LIKE &#63; and head = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param name the name
	 * @param head the head
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching fragment entry, or <code>null</code> if a matching fragment entry could not be found
	 */
	@Override
	public FragmentEntry fetchByG_FCI_LikeN_Head_Last(
		long groupId, long fragmentCollectionId, String name, boolean head,
		OrderByComparator<FragmentEntry> orderByComparator) {

		int count = countByG_FCI_LikeN_Head(
			groupId, fragmentCollectionId, name, head);

		if (count == 0) {
			return null;
		}

		List<FragmentEntry> list = findByG_FCI_LikeN_Head(
			groupId, fragmentCollectionId, name, head, count - 1, count,
			orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the fragment entries before and after the current fragment entry in the ordered set where groupId = &#63; and fragmentCollectionId = &#63; and name LIKE &#63; and head = &#63;.
	 *
	 * @param fragmentEntryId the primary key of the current fragment entry
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param name the name
	 * @param head the head
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next fragment entry
	 * @throws NoSuchEntryException if a fragment entry with the primary key could not be found
	 */
	@Override
	public FragmentEntry[] findByG_FCI_LikeN_Head_PrevAndNext(
			long fragmentEntryId, long groupId, long fragmentCollectionId,
			String name, boolean head,
			OrderByComparator<FragmentEntry> orderByComparator)
		throws NoSuchEntryException {

		name = Objects.toString(name, "");

		FragmentEntry fragmentEntry = findByPrimaryKey(fragmentEntryId);

		Session session = null;

		try {
			session = openSession();

			FragmentEntry[] array = new FragmentEntryImpl[3];

			array[0] = getByG_FCI_LikeN_Head_PrevAndNext(
				session, fragmentEntry, groupId, fragmentCollectionId, name,
				head, orderByComparator, true);

			array[1] = fragmentEntry;

			array[2] = getByG_FCI_LikeN_Head_PrevAndNext(
				session, fragmentEntry, groupId, fragmentCollectionId, name,
				head, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected FragmentEntry getByG_FCI_LikeN_Head_PrevAndNext(
		Session session, FragmentEntry fragmentEntry, long groupId,
		long fragmentCollectionId, String name, boolean head,
		OrderByComparator<FragmentEntry> orderByComparator, boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				7 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(6);
		}

		sb.append(_SQL_SELECT_FRAGMENTENTRY_WHERE);

		sb.append(_FINDER_COLUMN_G_FCI_LIKEN_HEAD_GROUPID_2);

		sb.append(_FINDER_COLUMN_G_FCI_LIKEN_HEAD_FRAGMENTCOLLECTIONID_2);

		boolean bindName = false;

		if (name.isEmpty()) {
			sb.append(_FINDER_COLUMN_G_FCI_LIKEN_HEAD_NAME_3);
		}
		else {
			bindName = true;

			sb.append(_FINDER_COLUMN_G_FCI_LIKEN_HEAD_NAME_2);
		}

		sb.append(_FINDER_COLUMN_G_FCI_LIKEN_HEAD_HEAD_2);

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
			sb.append(FragmentEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(groupId);

		queryPos.add(fragmentCollectionId);

		if (bindName) {
			queryPos.add(name);
		}

		queryPos.add(head);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						fragmentEntry)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<FragmentEntry> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the fragment entries where groupId = &#63; and fragmentCollectionId = &#63; and name LIKE &#63; and head = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param name the name
	 * @param head the head
	 */
	@Override
	public void removeByG_FCI_LikeN_Head(
		long groupId, long fragmentCollectionId, String name, boolean head) {

		for (FragmentEntry fragmentEntry :
				findByG_FCI_LikeN_Head(
					groupId, fragmentCollectionId, name, head,
					QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(fragmentEntry);
		}
	}

	/**
	 * Returns the number of fragment entries where groupId = &#63; and fragmentCollectionId = &#63; and name LIKE &#63; and head = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param name the name
	 * @param head the head
	 * @return the number of matching fragment entries
	 */
	@Override
	public int countByG_FCI_LikeN_Head(
		long groupId, long fragmentCollectionId, String name, boolean head) {

		name = Objects.toString(name, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			FragmentEntry.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathWithPaginationCountByG_FCI_LikeN_Head;

			finderArgs = new Object[] {
				groupId, fragmentCollectionId, name, head
			};

			count = (Long)finderCache.getResult(finderPath, finderArgs, this);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(5);

			sb.append(_SQL_COUNT_FRAGMENTENTRY_WHERE);

			sb.append(_FINDER_COLUMN_G_FCI_LIKEN_HEAD_GROUPID_2);

			sb.append(_FINDER_COLUMN_G_FCI_LIKEN_HEAD_FRAGMENTCOLLECTIONID_2);

			boolean bindName = false;

			if (name.isEmpty()) {
				sb.append(_FINDER_COLUMN_G_FCI_LIKEN_HEAD_NAME_3);
			}
			else {
				bindName = true;

				sb.append(_FINDER_COLUMN_G_FCI_LIKEN_HEAD_NAME_2);
			}

			sb.append(_FINDER_COLUMN_G_FCI_LIKEN_HEAD_HEAD_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				queryPos.add(fragmentCollectionId);

				if (bindName) {
					queryPos.add(name);
				}

				queryPos.add(head);

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

	private static final String _FINDER_COLUMN_G_FCI_LIKEN_HEAD_GROUPID_2 =
		"fragmentEntry.groupId = ? AND ";

	private static final String
		_FINDER_COLUMN_G_FCI_LIKEN_HEAD_FRAGMENTCOLLECTIONID_2 =
			"fragmentEntry.fragmentCollectionId = ? AND ";

	private static final String _FINDER_COLUMN_G_FCI_LIKEN_HEAD_NAME_2 =
		"fragmentEntry.name LIKE ? AND ";

	private static final String _FINDER_COLUMN_G_FCI_LIKEN_HEAD_NAME_3 =
		"(fragmentEntry.name IS NULL OR fragmentEntry.name LIKE '') AND ";

	private static final String _FINDER_COLUMN_G_FCI_LIKEN_HEAD_HEAD_2 =
		"fragmentEntry.head = ?";

	private FinderPath _finderPathWithPaginationFindByG_FCI_T;
	private FinderPath _finderPathWithoutPaginationFindByG_FCI_T;
	private FinderPath _finderPathCountByG_FCI_T;

	/**
	 * Returns all the fragment entries where groupId = &#63; and fragmentCollectionId = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param type the type
	 * @return the matching fragment entries
	 */
	@Override
	public List<FragmentEntry> findByG_FCI_T(
		long groupId, long fragmentCollectionId, int type) {

		return findByG_FCI_T(
			groupId, fragmentCollectionId, type, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the fragment entries where groupId = &#63; and fragmentCollectionId = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param type the type
	 * @param start the lower bound of the range of fragment entries
	 * @param end the upper bound of the range of fragment entries (not inclusive)
	 * @return the range of matching fragment entries
	 */
	@Override
	public List<FragmentEntry> findByG_FCI_T(
		long groupId, long fragmentCollectionId, int type, int start, int end) {

		return findByG_FCI_T(
			groupId, fragmentCollectionId, type, start, end, null);
	}

	/**
	 * Returns an ordered range of all the fragment entries where groupId = &#63; and fragmentCollectionId = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param type the type
	 * @param start the lower bound of the range of fragment entries
	 * @param end the upper bound of the range of fragment entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching fragment entries
	 */
	@Override
	public List<FragmentEntry> findByG_FCI_T(
		long groupId, long fragmentCollectionId, int type, int start, int end,
		OrderByComparator<FragmentEntry> orderByComparator) {

		return findByG_FCI_T(
			groupId, fragmentCollectionId, type, start, end, orderByComparator,
			true);
	}

	/**
	 * Returns an ordered range of all the fragment entries where groupId = &#63; and fragmentCollectionId = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param type the type
	 * @param start the lower bound of the range of fragment entries
	 * @param end the upper bound of the range of fragment entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching fragment entries
	 */
	@Override
	public List<FragmentEntry> findByG_FCI_T(
		long groupId, long fragmentCollectionId, int type, int start, int end,
		OrderByComparator<FragmentEntry> orderByComparator,
		boolean useFinderCache) {

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			FragmentEntry.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache && productionMode) {
				finderPath = _finderPathWithoutPaginationFindByG_FCI_T;
				finderArgs = new Object[] {groupId, fragmentCollectionId, type};
			}
		}
		else if (useFinderCache && productionMode) {
			finderPath = _finderPathWithPaginationFindByG_FCI_T;
			finderArgs = new Object[] {
				groupId, fragmentCollectionId, type, start, end,
				orderByComparator
			};
		}

		List<FragmentEntry> list = null;

		if (useFinderCache && productionMode) {
			list = (List<FragmentEntry>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (FragmentEntry fragmentEntry : list) {
					if ((groupId != fragmentEntry.getGroupId()) ||
						(fragmentCollectionId !=
							fragmentEntry.getFragmentCollectionId()) ||
						(type != fragmentEntry.getType())) {

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

			sb.append(_SQL_SELECT_FRAGMENTENTRY_WHERE);

			sb.append(_FINDER_COLUMN_G_FCI_T_GROUPID_2);

			sb.append(_FINDER_COLUMN_G_FCI_T_FRAGMENTCOLLECTIONID_2);

			sb.append(_FINDER_COLUMN_G_FCI_T_TYPE_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(FragmentEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				queryPos.add(fragmentCollectionId);

				queryPos.add(type);

				list = (List<FragmentEntry>)QueryUtil.list(
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
	 * Returns the first fragment entry in the ordered set where groupId = &#63; and fragmentCollectionId = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment entry
	 * @throws NoSuchEntryException if a matching fragment entry could not be found
	 */
	@Override
	public FragmentEntry findByG_FCI_T_First(
			long groupId, long fragmentCollectionId, int type,
			OrderByComparator<FragmentEntry> orderByComparator)
		throws NoSuchEntryException {

		FragmentEntry fragmentEntry = fetchByG_FCI_T_First(
			groupId, fragmentCollectionId, type, orderByComparator);

		if (fragmentEntry != null) {
			return fragmentEntry;
		}

		StringBundler sb = new StringBundler(8);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", fragmentCollectionId=");
		sb.append(fragmentCollectionId);

		sb.append(", type=");
		sb.append(type);

		sb.append("}");

		throw new NoSuchEntryException(sb.toString());
	}

	/**
	 * Returns the first fragment entry in the ordered set where groupId = &#63; and fragmentCollectionId = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment entry, or <code>null</code> if a matching fragment entry could not be found
	 */
	@Override
	public FragmentEntry fetchByG_FCI_T_First(
		long groupId, long fragmentCollectionId, int type,
		OrderByComparator<FragmentEntry> orderByComparator) {

		List<FragmentEntry> list = findByG_FCI_T(
			groupId, fragmentCollectionId, type, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last fragment entry in the ordered set where groupId = &#63; and fragmentCollectionId = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching fragment entry
	 * @throws NoSuchEntryException if a matching fragment entry could not be found
	 */
	@Override
	public FragmentEntry findByG_FCI_T_Last(
			long groupId, long fragmentCollectionId, int type,
			OrderByComparator<FragmentEntry> orderByComparator)
		throws NoSuchEntryException {

		FragmentEntry fragmentEntry = fetchByG_FCI_T_Last(
			groupId, fragmentCollectionId, type, orderByComparator);

		if (fragmentEntry != null) {
			return fragmentEntry;
		}

		StringBundler sb = new StringBundler(8);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", fragmentCollectionId=");
		sb.append(fragmentCollectionId);

		sb.append(", type=");
		sb.append(type);

		sb.append("}");

		throw new NoSuchEntryException(sb.toString());
	}

	/**
	 * Returns the last fragment entry in the ordered set where groupId = &#63; and fragmentCollectionId = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching fragment entry, or <code>null</code> if a matching fragment entry could not be found
	 */
	@Override
	public FragmentEntry fetchByG_FCI_T_Last(
		long groupId, long fragmentCollectionId, int type,
		OrderByComparator<FragmentEntry> orderByComparator) {

		int count = countByG_FCI_T(groupId, fragmentCollectionId, type);

		if (count == 0) {
			return null;
		}

		List<FragmentEntry> list = findByG_FCI_T(
			groupId, fragmentCollectionId, type, count - 1, count,
			orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the fragment entries before and after the current fragment entry in the ordered set where groupId = &#63; and fragmentCollectionId = &#63; and type = &#63;.
	 *
	 * @param fragmentEntryId the primary key of the current fragment entry
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next fragment entry
	 * @throws NoSuchEntryException if a fragment entry with the primary key could not be found
	 */
	@Override
	public FragmentEntry[] findByG_FCI_T_PrevAndNext(
			long fragmentEntryId, long groupId, long fragmentCollectionId,
			int type, OrderByComparator<FragmentEntry> orderByComparator)
		throws NoSuchEntryException {

		FragmentEntry fragmentEntry = findByPrimaryKey(fragmentEntryId);

		Session session = null;

		try {
			session = openSession();

			FragmentEntry[] array = new FragmentEntryImpl[3];

			array[0] = getByG_FCI_T_PrevAndNext(
				session, fragmentEntry, groupId, fragmentCollectionId, type,
				orderByComparator, true);

			array[1] = fragmentEntry;

			array[2] = getByG_FCI_T_PrevAndNext(
				session, fragmentEntry, groupId, fragmentCollectionId, type,
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

	protected FragmentEntry getByG_FCI_T_PrevAndNext(
		Session session, FragmentEntry fragmentEntry, long groupId,
		long fragmentCollectionId, int type,
		OrderByComparator<FragmentEntry> orderByComparator, boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				6 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(5);
		}

		sb.append(_SQL_SELECT_FRAGMENTENTRY_WHERE);

		sb.append(_FINDER_COLUMN_G_FCI_T_GROUPID_2);

		sb.append(_FINDER_COLUMN_G_FCI_T_FRAGMENTCOLLECTIONID_2);

		sb.append(_FINDER_COLUMN_G_FCI_T_TYPE_2);

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
			sb.append(FragmentEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(groupId);

		queryPos.add(fragmentCollectionId);

		queryPos.add(type);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						fragmentEntry)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<FragmentEntry> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the fragment entries where groupId = &#63; and fragmentCollectionId = &#63; and type = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param type the type
	 */
	@Override
	public void removeByG_FCI_T(
		long groupId, long fragmentCollectionId, int type) {

		for (FragmentEntry fragmentEntry :
				findByG_FCI_T(
					groupId, fragmentCollectionId, type, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			remove(fragmentEntry);
		}
	}

	/**
	 * Returns the number of fragment entries where groupId = &#63; and fragmentCollectionId = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param type the type
	 * @return the number of matching fragment entries
	 */
	@Override
	public int countByG_FCI_T(
		long groupId, long fragmentCollectionId, int type) {

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			FragmentEntry.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByG_FCI_T;

			finderArgs = new Object[] {groupId, fragmentCollectionId, type};

			count = (Long)finderCache.getResult(finderPath, finderArgs, this);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_COUNT_FRAGMENTENTRY_WHERE);

			sb.append(_FINDER_COLUMN_G_FCI_T_GROUPID_2);

			sb.append(_FINDER_COLUMN_G_FCI_T_FRAGMENTCOLLECTIONID_2);

			sb.append(_FINDER_COLUMN_G_FCI_T_TYPE_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				queryPos.add(fragmentCollectionId);

				queryPos.add(type);

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

	private static final String _FINDER_COLUMN_G_FCI_T_GROUPID_2 =
		"fragmentEntry.groupId = ? AND ";

	private static final String _FINDER_COLUMN_G_FCI_T_FRAGMENTCOLLECTIONID_2 =
		"fragmentEntry.fragmentCollectionId = ? AND ";

	private static final String _FINDER_COLUMN_G_FCI_T_TYPE_2 =
		"fragmentEntry.type = ?";

	private FinderPath _finderPathWithPaginationFindByG_FCI_T_Head;
	private FinderPath _finderPathWithoutPaginationFindByG_FCI_T_Head;
	private FinderPath _finderPathCountByG_FCI_T_Head;

	/**
	 * Returns all the fragment entries where groupId = &#63; and fragmentCollectionId = &#63; and type = &#63; and head = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param type the type
	 * @param head the head
	 * @return the matching fragment entries
	 */
	@Override
	public List<FragmentEntry> findByG_FCI_T_Head(
		long groupId, long fragmentCollectionId, int type, boolean head) {

		return findByG_FCI_T_Head(
			groupId, fragmentCollectionId, type, head, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the fragment entries where groupId = &#63; and fragmentCollectionId = &#63; and type = &#63; and head = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param type the type
	 * @param head the head
	 * @param start the lower bound of the range of fragment entries
	 * @param end the upper bound of the range of fragment entries (not inclusive)
	 * @return the range of matching fragment entries
	 */
	@Override
	public List<FragmentEntry> findByG_FCI_T_Head(
		long groupId, long fragmentCollectionId, int type, boolean head,
		int start, int end) {

		return findByG_FCI_T_Head(
			groupId, fragmentCollectionId, type, head, start, end, null);
	}

	/**
	 * Returns an ordered range of all the fragment entries where groupId = &#63; and fragmentCollectionId = &#63; and type = &#63; and head = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param type the type
	 * @param head the head
	 * @param start the lower bound of the range of fragment entries
	 * @param end the upper bound of the range of fragment entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching fragment entries
	 */
	@Override
	public List<FragmentEntry> findByG_FCI_T_Head(
		long groupId, long fragmentCollectionId, int type, boolean head,
		int start, int end,
		OrderByComparator<FragmentEntry> orderByComparator) {

		return findByG_FCI_T_Head(
			groupId, fragmentCollectionId, type, head, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the fragment entries where groupId = &#63; and fragmentCollectionId = &#63; and type = &#63; and head = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param type the type
	 * @param head the head
	 * @param start the lower bound of the range of fragment entries
	 * @param end the upper bound of the range of fragment entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching fragment entries
	 */
	@Override
	public List<FragmentEntry> findByG_FCI_T_Head(
		long groupId, long fragmentCollectionId, int type, boolean head,
		int start, int end, OrderByComparator<FragmentEntry> orderByComparator,
		boolean useFinderCache) {

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			FragmentEntry.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache && productionMode) {
				finderPath = _finderPathWithoutPaginationFindByG_FCI_T_Head;
				finderArgs = new Object[] {
					groupId, fragmentCollectionId, type, head
				};
			}
		}
		else if (useFinderCache && productionMode) {
			finderPath = _finderPathWithPaginationFindByG_FCI_T_Head;
			finderArgs = new Object[] {
				groupId, fragmentCollectionId, type, head, start, end,
				orderByComparator
			};
		}

		List<FragmentEntry> list = null;

		if (useFinderCache && productionMode) {
			list = (List<FragmentEntry>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (FragmentEntry fragmentEntry : list) {
					if ((groupId != fragmentEntry.getGroupId()) ||
						(fragmentCollectionId !=
							fragmentEntry.getFragmentCollectionId()) ||
						(type != fragmentEntry.getType()) ||
						(head != fragmentEntry.isHead())) {

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

			sb.append(_SQL_SELECT_FRAGMENTENTRY_WHERE);

			sb.append(_FINDER_COLUMN_G_FCI_T_HEAD_GROUPID_2);

			sb.append(_FINDER_COLUMN_G_FCI_T_HEAD_FRAGMENTCOLLECTIONID_2);

			sb.append(_FINDER_COLUMN_G_FCI_T_HEAD_TYPE_2);

			sb.append(_FINDER_COLUMN_G_FCI_T_HEAD_HEAD_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(FragmentEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				queryPos.add(fragmentCollectionId);

				queryPos.add(type);

				queryPos.add(head);

				list = (List<FragmentEntry>)QueryUtil.list(
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
	 * Returns the first fragment entry in the ordered set where groupId = &#63; and fragmentCollectionId = &#63; and type = &#63; and head = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param type the type
	 * @param head the head
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment entry
	 * @throws NoSuchEntryException if a matching fragment entry could not be found
	 */
	@Override
	public FragmentEntry findByG_FCI_T_Head_First(
			long groupId, long fragmentCollectionId, int type, boolean head,
			OrderByComparator<FragmentEntry> orderByComparator)
		throws NoSuchEntryException {

		FragmentEntry fragmentEntry = fetchByG_FCI_T_Head_First(
			groupId, fragmentCollectionId, type, head, orderByComparator);

		if (fragmentEntry != null) {
			return fragmentEntry;
		}

		StringBundler sb = new StringBundler(10);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", fragmentCollectionId=");
		sb.append(fragmentCollectionId);

		sb.append(", type=");
		sb.append(type);

		sb.append(", head=");
		sb.append(head);

		sb.append("}");

		throw new NoSuchEntryException(sb.toString());
	}

	/**
	 * Returns the first fragment entry in the ordered set where groupId = &#63; and fragmentCollectionId = &#63; and type = &#63; and head = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param type the type
	 * @param head the head
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment entry, or <code>null</code> if a matching fragment entry could not be found
	 */
	@Override
	public FragmentEntry fetchByG_FCI_T_Head_First(
		long groupId, long fragmentCollectionId, int type, boolean head,
		OrderByComparator<FragmentEntry> orderByComparator) {

		List<FragmentEntry> list = findByG_FCI_T_Head(
			groupId, fragmentCollectionId, type, head, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last fragment entry in the ordered set where groupId = &#63; and fragmentCollectionId = &#63; and type = &#63; and head = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param type the type
	 * @param head the head
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching fragment entry
	 * @throws NoSuchEntryException if a matching fragment entry could not be found
	 */
	@Override
	public FragmentEntry findByG_FCI_T_Head_Last(
			long groupId, long fragmentCollectionId, int type, boolean head,
			OrderByComparator<FragmentEntry> orderByComparator)
		throws NoSuchEntryException {

		FragmentEntry fragmentEntry = fetchByG_FCI_T_Head_Last(
			groupId, fragmentCollectionId, type, head, orderByComparator);

		if (fragmentEntry != null) {
			return fragmentEntry;
		}

		StringBundler sb = new StringBundler(10);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", fragmentCollectionId=");
		sb.append(fragmentCollectionId);

		sb.append(", type=");
		sb.append(type);

		sb.append(", head=");
		sb.append(head);

		sb.append("}");

		throw new NoSuchEntryException(sb.toString());
	}

	/**
	 * Returns the last fragment entry in the ordered set where groupId = &#63; and fragmentCollectionId = &#63; and type = &#63; and head = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param type the type
	 * @param head the head
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching fragment entry, or <code>null</code> if a matching fragment entry could not be found
	 */
	@Override
	public FragmentEntry fetchByG_FCI_T_Head_Last(
		long groupId, long fragmentCollectionId, int type, boolean head,
		OrderByComparator<FragmentEntry> orderByComparator) {

		int count = countByG_FCI_T_Head(
			groupId, fragmentCollectionId, type, head);

		if (count == 0) {
			return null;
		}

		List<FragmentEntry> list = findByG_FCI_T_Head(
			groupId, fragmentCollectionId, type, head, count - 1, count,
			orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the fragment entries before and after the current fragment entry in the ordered set where groupId = &#63; and fragmentCollectionId = &#63; and type = &#63; and head = &#63;.
	 *
	 * @param fragmentEntryId the primary key of the current fragment entry
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param type the type
	 * @param head the head
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next fragment entry
	 * @throws NoSuchEntryException if a fragment entry with the primary key could not be found
	 */
	@Override
	public FragmentEntry[] findByG_FCI_T_Head_PrevAndNext(
			long fragmentEntryId, long groupId, long fragmentCollectionId,
			int type, boolean head,
			OrderByComparator<FragmentEntry> orderByComparator)
		throws NoSuchEntryException {

		FragmentEntry fragmentEntry = findByPrimaryKey(fragmentEntryId);

		Session session = null;

		try {
			session = openSession();

			FragmentEntry[] array = new FragmentEntryImpl[3];

			array[0] = getByG_FCI_T_Head_PrevAndNext(
				session, fragmentEntry, groupId, fragmentCollectionId, type,
				head, orderByComparator, true);

			array[1] = fragmentEntry;

			array[2] = getByG_FCI_T_Head_PrevAndNext(
				session, fragmentEntry, groupId, fragmentCollectionId, type,
				head, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected FragmentEntry getByG_FCI_T_Head_PrevAndNext(
		Session session, FragmentEntry fragmentEntry, long groupId,
		long fragmentCollectionId, int type, boolean head,
		OrderByComparator<FragmentEntry> orderByComparator, boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				7 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(6);
		}

		sb.append(_SQL_SELECT_FRAGMENTENTRY_WHERE);

		sb.append(_FINDER_COLUMN_G_FCI_T_HEAD_GROUPID_2);

		sb.append(_FINDER_COLUMN_G_FCI_T_HEAD_FRAGMENTCOLLECTIONID_2);

		sb.append(_FINDER_COLUMN_G_FCI_T_HEAD_TYPE_2);

		sb.append(_FINDER_COLUMN_G_FCI_T_HEAD_HEAD_2);

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
			sb.append(FragmentEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(groupId);

		queryPos.add(fragmentCollectionId);

		queryPos.add(type);

		queryPos.add(head);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						fragmentEntry)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<FragmentEntry> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the fragment entries where groupId = &#63; and fragmentCollectionId = &#63; and type = &#63; and head = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param type the type
	 * @param head the head
	 */
	@Override
	public void removeByG_FCI_T_Head(
		long groupId, long fragmentCollectionId, int type, boolean head) {

		for (FragmentEntry fragmentEntry :
				findByG_FCI_T_Head(
					groupId, fragmentCollectionId, type, head,
					QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(fragmentEntry);
		}
	}

	/**
	 * Returns the number of fragment entries where groupId = &#63; and fragmentCollectionId = &#63; and type = &#63; and head = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param type the type
	 * @param head the head
	 * @return the number of matching fragment entries
	 */
	@Override
	public int countByG_FCI_T_Head(
		long groupId, long fragmentCollectionId, int type, boolean head) {

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			FragmentEntry.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByG_FCI_T_Head;

			finderArgs = new Object[] {
				groupId, fragmentCollectionId, type, head
			};

			count = (Long)finderCache.getResult(finderPath, finderArgs, this);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(5);

			sb.append(_SQL_COUNT_FRAGMENTENTRY_WHERE);

			sb.append(_FINDER_COLUMN_G_FCI_T_HEAD_GROUPID_2);

			sb.append(_FINDER_COLUMN_G_FCI_T_HEAD_FRAGMENTCOLLECTIONID_2);

			sb.append(_FINDER_COLUMN_G_FCI_T_HEAD_TYPE_2);

			sb.append(_FINDER_COLUMN_G_FCI_T_HEAD_HEAD_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				queryPos.add(fragmentCollectionId);

				queryPos.add(type);

				queryPos.add(head);

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

	private static final String _FINDER_COLUMN_G_FCI_T_HEAD_GROUPID_2 =
		"fragmentEntry.groupId = ? AND ";

	private static final String
		_FINDER_COLUMN_G_FCI_T_HEAD_FRAGMENTCOLLECTIONID_2 =
			"fragmentEntry.fragmentCollectionId = ? AND ";

	private static final String _FINDER_COLUMN_G_FCI_T_HEAD_TYPE_2 =
		"fragmentEntry.type = ? AND ";

	private static final String _FINDER_COLUMN_G_FCI_T_HEAD_HEAD_2 =
		"fragmentEntry.head = ?";

	private FinderPath _finderPathWithPaginationFindByG_FCI_S;
	private FinderPath _finderPathWithoutPaginationFindByG_FCI_S;
	private FinderPath _finderPathCountByG_FCI_S;

	/**
	 * Returns all the fragment entries where groupId = &#63; and fragmentCollectionId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param status the status
	 * @return the matching fragment entries
	 */
	@Override
	public List<FragmentEntry> findByG_FCI_S(
		long groupId, long fragmentCollectionId, int status) {

		return findByG_FCI_S(
			groupId, fragmentCollectionId, status, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the fragment entries where groupId = &#63; and fragmentCollectionId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param status the status
	 * @param start the lower bound of the range of fragment entries
	 * @param end the upper bound of the range of fragment entries (not inclusive)
	 * @return the range of matching fragment entries
	 */
	@Override
	public List<FragmentEntry> findByG_FCI_S(
		long groupId, long fragmentCollectionId, int status, int start,
		int end) {

		return findByG_FCI_S(
			groupId, fragmentCollectionId, status, start, end, null);
	}

	/**
	 * Returns an ordered range of all the fragment entries where groupId = &#63; and fragmentCollectionId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param status the status
	 * @param start the lower bound of the range of fragment entries
	 * @param end the upper bound of the range of fragment entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching fragment entries
	 */
	@Override
	public List<FragmentEntry> findByG_FCI_S(
		long groupId, long fragmentCollectionId, int status, int start, int end,
		OrderByComparator<FragmentEntry> orderByComparator) {

		return findByG_FCI_S(
			groupId, fragmentCollectionId, status, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the fragment entries where groupId = &#63; and fragmentCollectionId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param status the status
	 * @param start the lower bound of the range of fragment entries
	 * @param end the upper bound of the range of fragment entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching fragment entries
	 */
	@Override
	public List<FragmentEntry> findByG_FCI_S(
		long groupId, long fragmentCollectionId, int status, int start, int end,
		OrderByComparator<FragmentEntry> orderByComparator,
		boolean useFinderCache) {

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			FragmentEntry.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache && productionMode) {
				finderPath = _finderPathWithoutPaginationFindByG_FCI_S;
				finderArgs = new Object[] {
					groupId, fragmentCollectionId, status
				};
			}
		}
		else if (useFinderCache && productionMode) {
			finderPath = _finderPathWithPaginationFindByG_FCI_S;
			finderArgs = new Object[] {
				groupId, fragmentCollectionId, status, start, end,
				orderByComparator
			};
		}

		List<FragmentEntry> list = null;

		if (useFinderCache && productionMode) {
			list = (List<FragmentEntry>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (FragmentEntry fragmentEntry : list) {
					if ((groupId != fragmentEntry.getGroupId()) ||
						(fragmentCollectionId !=
							fragmentEntry.getFragmentCollectionId()) ||
						(status != fragmentEntry.getStatus())) {

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

			sb.append(_SQL_SELECT_FRAGMENTENTRY_WHERE);

			sb.append(_FINDER_COLUMN_G_FCI_S_GROUPID_2);

			sb.append(_FINDER_COLUMN_G_FCI_S_FRAGMENTCOLLECTIONID_2);

			sb.append(_FINDER_COLUMN_G_FCI_S_STATUS_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(FragmentEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				queryPos.add(fragmentCollectionId);

				queryPos.add(status);

				list = (List<FragmentEntry>)QueryUtil.list(
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
	 * Returns the first fragment entry in the ordered set where groupId = &#63; and fragmentCollectionId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment entry
	 * @throws NoSuchEntryException if a matching fragment entry could not be found
	 */
	@Override
	public FragmentEntry findByG_FCI_S_First(
			long groupId, long fragmentCollectionId, int status,
			OrderByComparator<FragmentEntry> orderByComparator)
		throws NoSuchEntryException {

		FragmentEntry fragmentEntry = fetchByG_FCI_S_First(
			groupId, fragmentCollectionId, status, orderByComparator);

		if (fragmentEntry != null) {
			return fragmentEntry;
		}

		StringBundler sb = new StringBundler(8);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", fragmentCollectionId=");
		sb.append(fragmentCollectionId);

		sb.append(", status=");
		sb.append(status);

		sb.append("}");

		throw new NoSuchEntryException(sb.toString());
	}

	/**
	 * Returns the first fragment entry in the ordered set where groupId = &#63; and fragmentCollectionId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment entry, or <code>null</code> if a matching fragment entry could not be found
	 */
	@Override
	public FragmentEntry fetchByG_FCI_S_First(
		long groupId, long fragmentCollectionId, int status,
		OrderByComparator<FragmentEntry> orderByComparator) {

		List<FragmentEntry> list = findByG_FCI_S(
			groupId, fragmentCollectionId, status, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last fragment entry in the ordered set where groupId = &#63; and fragmentCollectionId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching fragment entry
	 * @throws NoSuchEntryException if a matching fragment entry could not be found
	 */
	@Override
	public FragmentEntry findByG_FCI_S_Last(
			long groupId, long fragmentCollectionId, int status,
			OrderByComparator<FragmentEntry> orderByComparator)
		throws NoSuchEntryException {

		FragmentEntry fragmentEntry = fetchByG_FCI_S_Last(
			groupId, fragmentCollectionId, status, orderByComparator);

		if (fragmentEntry != null) {
			return fragmentEntry;
		}

		StringBundler sb = new StringBundler(8);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", fragmentCollectionId=");
		sb.append(fragmentCollectionId);

		sb.append(", status=");
		sb.append(status);

		sb.append("}");

		throw new NoSuchEntryException(sb.toString());
	}

	/**
	 * Returns the last fragment entry in the ordered set where groupId = &#63; and fragmentCollectionId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching fragment entry, or <code>null</code> if a matching fragment entry could not be found
	 */
	@Override
	public FragmentEntry fetchByG_FCI_S_Last(
		long groupId, long fragmentCollectionId, int status,
		OrderByComparator<FragmentEntry> orderByComparator) {

		int count = countByG_FCI_S(groupId, fragmentCollectionId, status);

		if (count == 0) {
			return null;
		}

		List<FragmentEntry> list = findByG_FCI_S(
			groupId, fragmentCollectionId, status, count - 1, count,
			orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the fragment entries before and after the current fragment entry in the ordered set where groupId = &#63; and fragmentCollectionId = &#63; and status = &#63;.
	 *
	 * @param fragmentEntryId the primary key of the current fragment entry
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next fragment entry
	 * @throws NoSuchEntryException if a fragment entry with the primary key could not be found
	 */
	@Override
	public FragmentEntry[] findByG_FCI_S_PrevAndNext(
			long fragmentEntryId, long groupId, long fragmentCollectionId,
			int status, OrderByComparator<FragmentEntry> orderByComparator)
		throws NoSuchEntryException {

		FragmentEntry fragmentEntry = findByPrimaryKey(fragmentEntryId);

		Session session = null;

		try {
			session = openSession();

			FragmentEntry[] array = new FragmentEntryImpl[3];

			array[0] = getByG_FCI_S_PrevAndNext(
				session, fragmentEntry, groupId, fragmentCollectionId, status,
				orderByComparator, true);

			array[1] = fragmentEntry;

			array[2] = getByG_FCI_S_PrevAndNext(
				session, fragmentEntry, groupId, fragmentCollectionId, status,
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

	protected FragmentEntry getByG_FCI_S_PrevAndNext(
		Session session, FragmentEntry fragmentEntry, long groupId,
		long fragmentCollectionId, int status,
		OrderByComparator<FragmentEntry> orderByComparator, boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				6 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(5);
		}

		sb.append(_SQL_SELECT_FRAGMENTENTRY_WHERE);

		sb.append(_FINDER_COLUMN_G_FCI_S_GROUPID_2);

		sb.append(_FINDER_COLUMN_G_FCI_S_FRAGMENTCOLLECTIONID_2);

		sb.append(_FINDER_COLUMN_G_FCI_S_STATUS_2);

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
			sb.append(FragmentEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(groupId);

		queryPos.add(fragmentCollectionId);

		queryPos.add(status);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						fragmentEntry)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<FragmentEntry> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the fragment entries where groupId = &#63; and fragmentCollectionId = &#63; and status = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param status the status
	 */
	@Override
	public void removeByG_FCI_S(
		long groupId, long fragmentCollectionId, int status) {

		for (FragmentEntry fragmentEntry :
				findByG_FCI_S(
					groupId, fragmentCollectionId, status, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			remove(fragmentEntry);
		}
	}

	/**
	 * Returns the number of fragment entries where groupId = &#63; and fragmentCollectionId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param status the status
	 * @return the number of matching fragment entries
	 */
	@Override
	public int countByG_FCI_S(
		long groupId, long fragmentCollectionId, int status) {

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			FragmentEntry.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByG_FCI_S;

			finderArgs = new Object[] {groupId, fragmentCollectionId, status};

			count = (Long)finderCache.getResult(finderPath, finderArgs, this);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_COUNT_FRAGMENTENTRY_WHERE);

			sb.append(_FINDER_COLUMN_G_FCI_S_GROUPID_2);

			sb.append(_FINDER_COLUMN_G_FCI_S_FRAGMENTCOLLECTIONID_2);

			sb.append(_FINDER_COLUMN_G_FCI_S_STATUS_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				queryPos.add(fragmentCollectionId);

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

	private static final String _FINDER_COLUMN_G_FCI_S_GROUPID_2 =
		"fragmentEntry.groupId = ? AND ";

	private static final String _FINDER_COLUMN_G_FCI_S_FRAGMENTCOLLECTIONID_2 =
		"fragmentEntry.fragmentCollectionId = ? AND ";

	private static final String _FINDER_COLUMN_G_FCI_S_STATUS_2 =
		"fragmentEntry.status = ?";

	private FinderPath _finderPathWithPaginationFindByG_FCI_S_Head;
	private FinderPath _finderPathWithoutPaginationFindByG_FCI_S_Head;
	private FinderPath _finderPathCountByG_FCI_S_Head;

	/**
	 * Returns all the fragment entries where groupId = &#63; and fragmentCollectionId = &#63; and status = &#63; and head = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param status the status
	 * @param head the head
	 * @return the matching fragment entries
	 */
	@Override
	public List<FragmentEntry> findByG_FCI_S_Head(
		long groupId, long fragmentCollectionId, int status, boolean head) {

		return findByG_FCI_S_Head(
			groupId, fragmentCollectionId, status, head, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the fragment entries where groupId = &#63; and fragmentCollectionId = &#63; and status = &#63; and head = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param status the status
	 * @param head the head
	 * @param start the lower bound of the range of fragment entries
	 * @param end the upper bound of the range of fragment entries (not inclusive)
	 * @return the range of matching fragment entries
	 */
	@Override
	public List<FragmentEntry> findByG_FCI_S_Head(
		long groupId, long fragmentCollectionId, int status, boolean head,
		int start, int end) {

		return findByG_FCI_S_Head(
			groupId, fragmentCollectionId, status, head, start, end, null);
	}

	/**
	 * Returns an ordered range of all the fragment entries where groupId = &#63; and fragmentCollectionId = &#63; and status = &#63; and head = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param status the status
	 * @param head the head
	 * @param start the lower bound of the range of fragment entries
	 * @param end the upper bound of the range of fragment entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching fragment entries
	 */
	@Override
	public List<FragmentEntry> findByG_FCI_S_Head(
		long groupId, long fragmentCollectionId, int status, boolean head,
		int start, int end,
		OrderByComparator<FragmentEntry> orderByComparator) {

		return findByG_FCI_S_Head(
			groupId, fragmentCollectionId, status, head, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the fragment entries where groupId = &#63; and fragmentCollectionId = &#63; and status = &#63; and head = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param status the status
	 * @param head the head
	 * @param start the lower bound of the range of fragment entries
	 * @param end the upper bound of the range of fragment entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching fragment entries
	 */
	@Override
	public List<FragmentEntry> findByG_FCI_S_Head(
		long groupId, long fragmentCollectionId, int status, boolean head,
		int start, int end, OrderByComparator<FragmentEntry> orderByComparator,
		boolean useFinderCache) {

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			FragmentEntry.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache && productionMode) {
				finderPath = _finderPathWithoutPaginationFindByG_FCI_S_Head;
				finderArgs = new Object[] {
					groupId, fragmentCollectionId, status, head
				};
			}
		}
		else if (useFinderCache && productionMode) {
			finderPath = _finderPathWithPaginationFindByG_FCI_S_Head;
			finderArgs = new Object[] {
				groupId, fragmentCollectionId, status, head, start, end,
				orderByComparator
			};
		}

		List<FragmentEntry> list = null;

		if (useFinderCache && productionMode) {
			list = (List<FragmentEntry>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (FragmentEntry fragmentEntry : list) {
					if ((groupId != fragmentEntry.getGroupId()) ||
						(fragmentCollectionId !=
							fragmentEntry.getFragmentCollectionId()) ||
						(status != fragmentEntry.getStatus()) ||
						(head != fragmentEntry.isHead())) {

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

			sb.append(_SQL_SELECT_FRAGMENTENTRY_WHERE);

			sb.append(_FINDER_COLUMN_G_FCI_S_HEAD_GROUPID_2);

			sb.append(_FINDER_COLUMN_G_FCI_S_HEAD_FRAGMENTCOLLECTIONID_2);

			sb.append(_FINDER_COLUMN_G_FCI_S_HEAD_STATUS_2);

			sb.append(_FINDER_COLUMN_G_FCI_S_HEAD_HEAD_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(FragmentEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				queryPos.add(fragmentCollectionId);

				queryPos.add(status);

				queryPos.add(head);

				list = (List<FragmentEntry>)QueryUtil.list(
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
	 * Returns the first fragment entry in the ordered set where groupId = &#63; and fragmentCollectionId = &#63; and status = &#63; and head = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param status the status
	 * @param head the head
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment entry
	 * @throws NoSuchEntryException if a matching fragment entry could not be found
	 */
	@Override
	public FragmentEntry findByG_FCI_S_Head_First(
			long groupId, long fragmentCollectionId, int status, boolean head,
			OrderByComparator<FragmentEntry> orderByComparator)
		throws NoSuchEntryException {

		FragmentEntry fragmentEntry = fetchByG_FCI_S_Head_First(
			groupId, fragmentCollectionId, status, head, orderByComparator);

		if (fragmentEntry != null) {
			return fragmentEntry;
		}

		StringBundler sb = new StringBundler(10);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", fragmentCollectionId=");
		sb.append(fragmentCollectionId);

		sb.append(", status=");
		sb.append(status);

		sb.append(", head=");
		sb.append(head);

		sb.append("}");

		throw new NoSuchEntryException(sb.toString());
	}

	/**
	 * Returns the first fragment entry in the ordered set where groupId = &#63; and fragmentCollectionId = &#63; and status = &#63; and head = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param status the status
	 * @param head the head
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment entry, or <code>null</code> if a matching fragment entry could not be found
	 */
	@Override
	public FragmentEntry fetchByG_FCI_S_Head_First(
		long groupId, long fragmentCollectionId, int status, boolean head,
		OrderByComparator<FragmentEntry> orderByComparator) {

		List<FragmentEntry> list = findByG_FCI_S_Head(
			groupId, fragmentCollectionId, status, head, 0, 1,
			orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last fragment entry in the ordered set where groupId = &#63; and fragmentCollectionId = &#63; and status = &#63; and head = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param status the status
	 * @param head the head
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching fragment entry
	 * @throws NoSuchEntryException if a matching fragment entry could not be found
	 */
	@Override
	public FragmentEntry findByG_FCI_S_Head_Last(
			long groupId, long fragmentCollectionId, int status, boolean head,
			OrderByComparator<FragmentEntry> orderByComparator)
		throws NoSuchEntryException {

		FragmentEntry fragmentEntry = fetchByG_FCI_S_Head_Last(
			groupId, fragmentCollectionId, status, head, orderByComparator);

		if (fragmentEntry != null) {
			return fragmentEntry;
		}

		StringBundler sb = new StringBundler(10);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", fragmentCollectionId=");
		sb.append(fragmentCollectionId);

		sb.append(", status=");
		sb.append(status);

		sb.append(", head=");
		sb.append(head);

		sb.append("}");

		throw new NoSuchEntryException(sb.toString());
	}

	/**
	 * Returns the last fragment entry in the ordered set where groupId = &#63; and fragmentCollectionId = &#63; and status = &#63; and head = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param status the status
	 * @param head the head
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching fragment entry, or <code>null</code> if a matching fragment entry could not be found
	 */
	@Override
	public FragmentEntry fetchByG_FCI_S_Head_Last(
		long groupId, long fragmentCollectionId, int status, boolean head,
		OrderByComparator<FragmentEntry> orderByComparator) {

		int count = countByG_FCI_S_Head(
			groupId, fragmentCollectionId, status, head);

		if (count == 0) {
			return null;
		}

		List<FragmentEntry> list = findByG_FCI_S_Head(
			groupId, fragmentCollectionId, status, head, count - 1, count,
			orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the fragment entries before and after the current fragment entry in the ordered set where groupId = &#63; and fragmentCollectionId = &#63; and status = &#63; and head = &#63;.
	 *
	 * @param fragmentEntryId the primary key of the current fragment entry
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param status the status
	 * @param head the head
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next fragment entry
	 * @throws NoSuchEntryException if a fragment entry with the primary key could not be found
	 */
	@Override
	public FragmentEntry[] findByG_FCI_S_Head_PrevAndNext(
			long fragmentEntryId, long groupId, long fragmentCollectionId,
			int status, boolean head,
			OrderByComparator<FragmentEntry> orderByComparator)
		throws NoSuchEntryException {

		FragmentEntry fragmentEntry = findByPrimaryKey(fragmentEntryId);

		Session session = null;

		try {
			session = openSession();

			FragmentEntry[] array = new FragmentEntryImpl[3];

			array[0] = getByG_FCI_S_Head_PrevAndNext(
				session, fragmentEntry, groupId, fragmentCollectionId, status,
				head, orderByComparator, true);

			array[1] = fragmentEntry;

			array[2] = getByG_FCI_S_Head_PrevAndNext(
				session, fragmentEntry, groupId, fragmentCollectionId, status,
				head, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected FragmentEntry getByG_FCI_S_Head_PrevAndNext(
		Session session, FragmentEntry fragmentEntry, long groupId,
		long fragmentCollectionId, int status, boolean head,
		OrderByComparator<FragmentEntry> orderByComparator, boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				7 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(6);
		}

		sb.append(_SQL_SELECT_FRAGMENTENTRY_WHERE);

		sb.append(_FINDER_COLUMN_G_FCI_S_HEAD_GROUPID_2);

		sb.append(_FINDER_COLUMN_G_FCI_S_HEAD_FRAGMENTCOLLECTIONID_2);

		sb.append(_FINDER_COLUMN_G_FCI_S_HEAD_STATUS_2);

		sb.append(_FINDER_COLUMN_G_FCI_S_HEAD_HEAD_2);

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
			sb.append(FragmentEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(groupId);

		queryPos.add(fragmentCollectionId);

		queryPos.add(status);

		queryPos.add(head);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						fragmentEntry)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<FragmentEntry> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the fragment entries where groupId = &#63; and fragmentCollectionId = &#63; and status = &#63; and head = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param status the status
	 * @param head the head
	 */
	@Override
	public void removeByG_FCI_S_Head(
		long groupId, long fragmentCollectionId, int status, boolean head) {

		for (FragmentEntry fragmentEntry :
				findByG_FCI_S_Head(
					groupId, fragmentCollectionId, status, head,
					QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(fragmentEntry);
		}
	}

	/**
	 * Returns the number of fragment entries where groupId = &#63; and fragmentCollectionId = &#63; and status = &#63; and head = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param status the status
	 * @param head the head
	 * @return the number of matching fragment entries
	 */
	@Override
	public int countByG_FCI_S_Head(
		long groupId, long fragmentCollectionId, int status, boolean head) {

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			FragmentEntry.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByG_FCI_S_Head;

			finderArgs = new Object[] {
				groupId, fragmentCollectionId, status, head
			};

			count = (Long)finderCache.getResult(finderPath, finderArgs, this);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(5);

			sb.append(_SQL_COUNT_FRAGMENTENTRY_WHERE);

			sb.append(_FINDER_COLUMN_G_FCI_S_HEAD_GROUPID_2);

			sb.append(_FINDER_COLUMN_G_FCI_S_HEAD_FRAGMENTCOLLECTIONID_2);

			sb.append(_FINDER_COLUMN_G_FCI_S_HEAD_STATUS_2);

			sb.append(_FINDER_COLUMN_G_FCI_S_HEAD_HEAD_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				queryPos.add(fragmentCollectionId);

				queryPos.add(status);

				queryPos.add(head);

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

	private static final String _FINDER_COLUMN_G_FCI_S_HEAD_GROUPID_2 =
		"fragmentEntry.groupId = ? AND ";

	private static final String
		_FINDER_COLUMN_G_FCI_S_HEAD_FRAGMENTCOLLECTIONID_2 =
			"fragmentEntry.fragmentCollectionId = ? AND ";

	private static final String _FINDER_COLUMN_G_FCI_S_HEAD_STATUS_2 =
		"fragmentEntry.status = ? AND ";

	private static final String _FINDER_COLUMN_G_FCI_S_HEAD_HEAD_2 =
		"fragmentEntry.head = ?";

	private FinderPath _finderPathWithPaginationFindByG_FCI_LikeN_S;
	private FinderPath _finderPathWithPaginationCountByG_FCI_LikeN_S;

	/**
	 * Returns all the fragment entries where groupId = &#63; and fragmentCollectionId = &#63; and name LIKE &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param name the name
	 * @param status the status
	 * @return the matching fragment entries
	 */
	@Override
	public List<FragmentEntry> findByG_FCI_LikeN_S(
		long groupId, long fragmentCollectionId, String name, int status) {

		return findByG_FCI_LikeN_S(
			groupId, fragmentCollectionId, name, status, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the fragment entries where groupId = &#63; and fragmentCollectionId = &#63; and name LIKE &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param name the name
	 * @param status the status
	 * @param start the lower bound of the range of fragment entries
	 * @param end the upper bound of the range of fragment entries (not inclusive)
	 * @return the range of matching fragment entries
	 */
	@Override
	public List<FragmentEntry> findByG_FCI_LikeN_S(
		long groupId, long fragmentCollectionId, String name, int status,
		int start, int end) {

		return findByG_FCI_LikeN_S(
			groupId, fragmentCollectionId, name, status, start, end, null);
	}

	/**
	 * Returns an ordered range of all the fragment entries where groupId = &#63; and fragmentCollectionId = &#63; and name LIKE &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param name the name
	 * @param status the status
	 * @param start the lower bound of the range of fragment entries
	 * @param end the upper bound of the range of fragment entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching fragment entries
	 */
	@Override
	public List<FragmentEntry> findByG_FCI_LikeN_S(
		long groupId, long fragmentCollectionId, String name, int status,
		int start, int end,
		OrderByComparator<FragmentEntry> orderByComparator) {

		return findByG_FCI_LikeN_S(
			groupId, fragmentCollectionId, name, status, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the fragment entries where groupId = &#63; and fragmentCollectionId = &#63; and name LIKE &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param name the name
	 * @param status the status
	 * @param start the lower bound of the range of fragment entries
	 * @param end the upper bound of the range of fragment entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching fragment entries
	 */
	@Override
	public List<FragmentEntry> findByG_FCI_LikeN_S(
		long groupId, long fragmentCollectionId, String name, int status,
		int start, int end, OrderByComparator<FragmentEntry> orderByComparator,
		boolean useFinderCache) {

		name = Objects.toString(name, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			FragmentEntry.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		finderPath = _finderPathWithPaginationFindByG_FCI_LikeN_S;
		finderArgs = new Object[] {
			groupId, fragmentCollectionId, name, status, start, end,
			orderByComparator
		};

		List<FragmentEntry> list = null;

		if (useFinderCache && productionMode) {
			list = (List<FragmentEntry>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (FragmentEntry fragmentEntry : list) {
					if ((groupId != fragmentEntry.getGroupId()) ||
						(fragmentCollectionId !=
							fragmentEntry.getFragmentCollectionId()) ||
						!StringUtil.wildcardMatches(
							fragmentEntry.getName(), name, '_', '%', '\\',
							true) ||
						(status != fragmentEntry.getStatus())) {

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

			sb.append(_SQL_SELECT_FRAGMENTENTRY_WHERE);

			sb.append(_FINDER_COLUMN_G_FCI_LIKEN_S_GROUPID_2);

			sb.append(_FINDER_COLUMN_G_FCI_LIKEN_S_FRAGMENTCOLLECTIONID_2);

			boolean bindName = false;

			if (name.isEmpty()) {
				sb.append(_FINDER_COLUMN_G_FCI_LIKEN_S_NAME_3);
			}
			else {
				bindName = true;

				sb.append(_FINDER_COLUMN_G_FCI_LIKEN_S_NAME_2);
			}

			sb.append(_FINDER_COLUMN_G_FCI_LIKEN_S_STATUS_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(FragmentEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				queryPos.add(fragmentCollectionId);

				if (bindName) {
					queryPos.add(name);
				}

				queryPos.add(status);

				list = (List<FragmentEntry>)QueryUtil.list(
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
	 * Returns the first fragment entry in the ordered set where groupId = &#63; and fragmentCollectionId = &#63; and name LIKE &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param name the name
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment entry
	 * @throws NoSuchEntryException if a matching fragment entry could not be found
	 */
	@Override
	public FragmentEntry findByG_FCI_LikeN_S_First(
			long groupId, long fragmentCollectionId, String name, int status,
			OrderByComparator<FragmentEntry> orderByComparator)
		throws NoSuchEntryException {

		FragmentEntry fragmentEntry = fetchByG_FCI_LikeN_S_First(
			groupId, fragmentCollectionId, name, status, orderByComparator);

		if (fragmentEntry != null) {
			return fragmentEntry;
		}

		StringBundler sb = new StringBundler(10);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", fragmentCollectionId=");
		sb.append(fragmentCollectionId);

		sb.append(", nameLIKE");
		sb.append(name);

		sb.append(", status=");
		sb.append(status);

		sb.append("}");

		throw new NoSuchEntryException(sb.toString());
	}

	/**
	 * Returns the first fragment entry in the ordered set where groupId = &#63; and fragmentCollectionId = &#63; and name LIKE &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param name the name
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment entry, or <code>null</code> if a matching fragment entry could not be found
	 */
	@Override
	public FragmentEntry fetchByG_FCI_LikeN_S_First(
		long groupId, long fragmentCollectionId, String name, int status,
		OrderByComparator<FragmentEntry> orderByComparator) {

		List<FragmentEntry> list = findByG_FCI_LikeN_S(
			groupId, fragmentCollectionId, name, status, 0, 1,
			orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last fragment entry in the ordered set where groupId = &#63; and fragmentCollectionId = &#63; and name LIKE &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param name the name
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching fragment entry
	 * @throws NoSuchEntryException if a matching fragment entry could not be found
	 */
	@Override
	public FragmentEntry findByG_FCI_LikeN_S_Last(
			long groupId, long fragmentCollectionId, String name, int status,
			OrderByComparator<FragmentEntry> orderByComparator)
		throws NoSuchEntryException {

		FragmentEntry fragmentEntry = fetchByG_FCI_LikeN_S_Last(
			groupId, fragmentCollectionId, name, status, orderByComparator);

		if (fragmentEntry != null) {
			return fragmentEntry;
		}

		StringBundler sb = new StringBundler(10);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", fragmentCollectionId=");
		sb.append(fragmentCollectionId);

		sb.append(", nameLIKE");
		sb.append(name);

		sb.append(", status=");
		sb.append(status);

		sb.append("}");

		throw new NoSuchEntryException(sb.toString());
	}

	/**
	 * Returns the last fragment entry in the ordered set where groupId = &#63; and fragmentCollectionId = &#63; and name LIKE &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param name the name
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching fragment entry, or <code>null</code> if a matching fragment entry could not be found
	 */
	@Override
	public FragmentEntry fetchByG_FCI_LikeN_S_Last(
		long groupId, long fragmentCollectionId, String name, int status,
		OrderByComparator<FragmentEntry> orderByComparator) {

		int count = countByG_FCI_LikeN_S(
			groupId, fragmentCollectionId, name, status);

		if (count == 0) {
			return null;
		}

		List<FragmentEntry> list = findByG_FCI_LikeN_S(
			groupId, fragmentCollectionId, name, status, count - 1, count,
			orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the fragment entries before and after the current fragment entry in the ordered set where groupId = &#63; and fragmentCollectionId = &#63; and name LIKE &#63; and status = &#63;.
	 *
	 * @param fragmentEntryId the primary key of the current fragment entry
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param name the name
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next fragment entry
	 * @throws NoSuchEntryException if a fragment entry with the primary key could not be found
	 */
	@Override
	public FragmentEntry[] findByG_FCI_LikeN_S_PrevAndNext(
			long fragmentEntryId, long groupId, long fragmentCollectionId,
			String name, int status,
			OrderByComparator<FragmentEntry> orderByComparator)
		throws NoSuchEntryException {

		name = Objects.toString(name, "");

		FragmentEntry fragmentEntry = findByPrimaryKey(fragmentEntryId);

		Session session = null;

		try {
			session = openSession();

			FragmentEntry[] array = new FragmentEntryImpl[3];

			array[0] = getByG_FCI_LikeN_S_PrevAndNext(
				session, fragmentEntry, groupId, fragmentCollectionId, name,
				status, orderByComparator, true);

			array[1] = fragmentEntry;

			array[2] = getByG_FCI_LikeN_S_PrevAndNext(
				session, fragmentEntry, groupId, fragmentCollectionId, name,
				status, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected FragmentEntry getByG_FCI_LikeN_S_PrevAndNext(
		Session session, FragmentEntry fragmentEntry, long groupId,
		long fragmentCollectionId, String name, int status,
		OrderByComparator<FragmentEntry> orderByComparator, boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				7 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(6);
		}

		sb.append(_SQL_SELECT_FRAGMENTENTRY_WHERE);

		sb.append(_FINDER_COLUMN_G_FCI_LIKEN_S_GROUPID_2);

		sb.append(_FINDER_COLUMN_G_FCI_LIKEN_S_FRAGMENTCOLLECTIONID_2);

		boolean bindName = false;

		if (name.isEmpty()) {
			sb.append(_FINDER_COLUMN_G_FCI_LIKEN_S_NAME_3);
		}
		else {
			bindName = true;

			sb.append(_FINDER_COLUMN_G_FCI_LIKEN_S_NAME_2);
		}

		sb.append(_FINDER_COLUMN_G_FCI_LIKEN_S_STATUS_2);

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
			sb.append(FragmentEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(groupId);

		queryPos.add(fragmentCollectionId);

		if (bindName) {
			queryPos.add(name);
		}

		queryPos.add(status);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						fragmentEntry)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<FragmentEntry> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the fragment entries where groupId = &#63; and fragmentCollectionId = &#63; and name LIKE &#63; and status = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param name the name
	 * @param status the status
	 */
	@Override
	public void removeByG_FCI_LikeN_S(
		long groupId, long fragmentCollectionId, String name, int status) {

		for (FragmentEntry fragmentEntry :
				findByG_FCI_LikeN_S(
					groupId, fragmentCollectionId, name, status,
					QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(fragmentEntry);
		}
	}

	/**
	 * Returns the number of fragment entries where groupId = &#63; and fragmentCollectionId = &#63; and name LIKE &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param name the name
	 * @param status the status
	 * @return the number of matching fragment entries
	 */
	@Override
	public int countByG_FCI_LikeN_S(
		long groupId, long fragmentCollectionId, String name, int status) {

		name = Objects.toString(name, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			FragmentEntry.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathWithPaginationCountByG_FCI_LikeN_S;

			finderArgs = new Object[] {
				groupId, fragmentCollectionId, name, status
			};

			count = (Long)finderCache.getResult(finderPath, finderArgs, this);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(5);

			sb.append(_SQL_COUNT_FRAGMENTENTRY_WHERE);

			sb.append(_FINDER_COLUMN_G_FCI_LIKEN_S_GROUPID_2);

			sb.append(_FINDER_COLUMN_G_FCI_LIKEN_S_FRAGMENTCOLLECTIONID_2);

			boolean bindName = false;

			if (name.isEmpty()) {
				sb.append(_FINDER_COLUMN_G_FCI_LIKEN_S_NAME_3);
			}
			else {
				bindName = true;

				sb.append(_FINDER_COLUMN_G_FCI_LIKEN_S_NAME_2);
			}

			sb.append(_FINDER_COLUMN_G_FCI_LIKEN_S_STATUS_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				queryPos.add(fragmentCollectionId);

				if (bindName) {
					queryPos.add(name);
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

	private static final String _FINDER_COLUMN_G_FCI_LIKEN_S_GROUPID_2 =
		"fragmentEntry.groupId = ? AND ";

	private static final String
		_FINDER_COLUMN_G_FCI_LIKEN_S_FRAGMENTCOLLECTIONID_2 =
			"fragmentEntry.fragmentCollectionId = ? AND ";

	private static final String _FINDER_COLUMN_G_FCI_LIKEN_S_NAME_2 =
		"fragmentEntry.name LIKE ? AND ";

	private static final String _FINDER_COLUMN_G_FCI_LIKEN_S_NAME_3 =
		"(fragmentEntry.name IS NULL OR fragmentEntry.name LIKE '') AND ";

	private static final String _FINDER_COLUMN_G_FCI_LIKEN_S_STATUS_2 =
		"fragmentEntry.status = ?";

	private FinderPath _finderPathWithPaginationFindByG_FCI_LikeN_S_Head;
	private FinderPath _finderPathWithPaginationCountByG_FCI_LikeN_S_Head;

	/**
	 * Returns all the fragment entries where groupId = &#63; and fragmentCollectionId = &#63; and name LIKE &#63; and status = &#63; and head = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param name the name
	 * @param status the status
	 * @param head the head
	 * @return the matching fragment entries
	 */
	@Override
	public List<FragmentEntry> findByG_FCI_LikeN_S_Head(
		long groupId, long fragmentCollectionId, String name, int status,
		boolean head) {

		return findByG_FCI_LikeN_S_Head(
			groupId, fragmentCollectionId, name, status, head,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the fragment entries where groupId = &#63; and fragmentCollectionId = &#63; and name LIKE &#63; and status = &#63; and head = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param name the name
	 * @param status the status
	 * @param head the head
	 * @param start the lower bound of the range of fragment entries
	 * @param end the upper bound of the range of fragment entries (not inclusive)
	 * @return the range of matching fragment entries
	 */
	@Override
	public List<FragmentEntry> findByG_FCI_LikeN_S_Head(
		long groupId, long fragmentCollectionId, String name, int status,
		boolean head, int start, int end) {

		return findByG_FCI_LikeN_S_Head(
			groupId, fragmentCollectionId, name, status, head, start, end,
			null);
	}

	/**
	 * Returns an ordered range of all the fragment entries where groupId = &#63; and fragmentCollectionId = &#63; and name LIKE &#63; and status = &#63; and head = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param name the name
	 * @param status the status
	 * @param head the head
	 * @param start the lower bound of the range of fragment entries
	 * @param end the upper bound of the range of fragment entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching fragment entries
	 */
	@Override
	public List<FragmentEntry> findByG_FCI_LikeN_S_Head(
		long groupId, long fragmentCollectionId, String name, int status,
		boolean head, int start, int end,
		OrderByComparator<FragmentEntry> orderByComparator) {

		return findByG_FCI_LikeN_S_Head(
			groupId, fragmentCollectionId, name, status, head, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the fragment entries where groupId = &#63; and fragmentCollectionId = &#63; and name LIKE &#63; and status = &#63; and head = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param name the name
	 * @param status the status
	 * @param head the head
	 * @param start the lower bound of the range of fragment entries
	 * @param end the upper bound of the range of fragment entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching fragment entries
	 */
	@Override
	public List<FragmentEntry> findByG_FCI_LikeN_S_Head(
		long groupId, long fragmentCollectionId, String name, int status,
		boolean head, int start, int end,
		OrderByComparator<FragmentEntry> orderByComparator,
		boolean useFinderCache) {

		name = Objects.toString(name, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			FragmentEntry.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		finderPath = _finderPathWithPaginationFindByG_FCI_LikeN_S_Head;
		finderArgs = new Object[] {
			groupId, fragmentCollectionId, name, status, head, start, end,
			orderByComparator
		};

		List<FragmentEntry> list = null;

		if (useFinderCache && productionMode) {
			list = (List<FragmentEntry>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (FragmentEntry fragmentEntry : list) {
					if ((groupId != fragmentEntry.getGroupId()) ||
						(fragmentCollectionId !=
							fragmentEntry.getFragmentCollectionId()) ||
						!StringUtil.wildcardMatches(
							fragmentEntry.getName(), name, '_', '%', '\\',
							true) ||
						(status != fragmentEntry.getStatus()) ||
						(head != fragmentEntry.isHead())) {

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
					7 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				sb = new StringBundler(7);
			}

			sb.append(_SQL_SELECT_FRAGMENTENTRY_WHERE);

			sb.append(_FINDER_COLUMN_G_FCI_LIKEN_S_HEAD_GROUPID_2);

			sb.append(_FINDER_COLUMN_G_FCI_LIKEN_S_HEAD_FRAGMENTCOLLECTIONID_2);

			boolean bindName = false;

			if (name.isEmpty()) {
				sb.append(_FINDER_COLUMN_G_FCI_LIKEN_S_HEAD_NAME_3);
			}
			else {
				bindName = true;

				sb.append(_FINDER_COLUMN_G_FCI_LIKEN_S_HEAD_NAME_2);
			}

			sb.append(_FINDER_COLUMN_G_FCI_LIKEN_S_HEAD_STATUS_2);

			sb.append(_FINDER_COLUMN_G_FCI_LIKEN_S_HEAD_HEAD_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(FragmentEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				queryPos.add(fragmentCollectionId);

				if (bindName) {
					queryPos.add(name);
				}

				queryPos.add(status);

				queryPos.add(head);

				list = (List<FragmentEntry>)QueryUtil.list(
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
	 * Returns the first fragment entry in the ordered set where groupId = &#63; and fragmentCollectionId = &#63; and name LIKE &#63; and status = &#63; and head = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param name the name
	 * @param status the status
	 * @param head the head
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment entry
	 * @throws NoSuchEntryException if a matching fragment entry could not be found
	 */
	@Override
	public FragmentEntry findByG_FCI_LikeN_S_Head_First(
			long groupId, long fragmentCollectionId, String name, int status,
			boolean head, OrderByComparator<FragmentEntry> orderByComparator)
		throws NoSuchEntryException {

		FragmentEntry fragmentEntry = fetchByG_FCI_LikeN_S_Head_First(
			groupId, fragmentCollectionId, name, status, head,
			orderByComparator);

		if (fragmentEntry != null) {
			return fragmentEntry;
		}

		StringBundler sb = new StringBundler(12);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", fragmentCollectionId=");
		sb.append(fragmentCollectionId);

		sb.append(", nameLIKE");
		sb.append(name);

		sb.append(", status=");
		sb.append(status);

		sb.append(", head=");
		sb.append(head);

		sb.append("}");

		throw new NoSuchEntryException(sb.toString());
	}

	/**
	 * Returns the first fragment entry in the ordered set where groupId = &#63; and fragmentCollectionId = &#63; and name LIKE &#63; and status = &#63; and head = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param name the name
	 * @param status the status
	 * @param head the head
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment entry, or <code>null</code> if a matching fragment entry could not be found
	 */
	@Override
	public FragmentEntry fetchByG_FCI_LikeN_S_Head_First(
		long groupId, long fragmentCollectionId, String name, int status,
		boolean head, OrderByComparator<FragmentEntry> orderByComparator) {

		List<FragmentEntry> list = findByG_FCI_LikeN_S_Head(
			groupId, fragmentCollectionId, name, status, head, 0, 1,
			orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last fragment entry in the ordered set where groupId = &#63; and fragmentCollectionId = &#63; and name LIKE &#63; and status = &#63; and head = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param name the name
	 * @param status the status
	 * @param head the head
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching fragment entry
	 * @throws NoSuchEntryException if a matching fragment entry could not be found
	 */
	@Override
	public FragmentEntry findByG_FCI_LikeN_S_Head_Last(
			long groupId, long fragmentCollectionId, String name, int status,
			boolean head, OrderByComparator<FragmentEntry> orderByComparator)
		throws NoSuchEntryException {

		FragmentEntry fragmentEntry = fetchByG_FCI_LikeN_S_Head_Last(
			groupId, fragmentCollectionId, name, status, head,
			orderByComparator);

		if (fragmentEntry != null) {
			return fragmentEntry;
		}

		StringBundler sb = new StringBundler(12);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", fragmentCollectionId=");
		sb.append(fragmentCollectionId);

		sb.append(", nameLIKE");
		sb.append(name);

		sb.append(", status=");
		sb.append(status);

		sb.append(", head=");
		sb.append(head);

		sb.append("}");

		throw new NoSuchEntryException(sb.toString());
	}

	/**
	 * Returns the last fragment entry in the ordered set where groupId = &#63; and fragmentCollectionId = &#63; and name LIKE &#63; and status = &#63; and head = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param name the name
	 * @param status the status
	 * @param head the head
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching fragment entry, or <code>null</code> if a matching fragment entry could not be found
	 */
	@Override
	public FragmentEntry fetchByG_FCI_LikeN_S_Head_Last(
		long groupId, long fragmentCollectionId, String name, int status,
		boolean head, OrderByComparator<FragmentEntry> orderByComparator) {

		int count = countByG_FCI_LikeN_S_Head(
			groupId, fragmentCollectionId, name, status, head);

		if (count == 0) {
			return null;
		}

		List<FragmentEntry> list = findByG_FCI_LikeN_S_Head(
			groupId, fragmentCollectionId, name, status, head, count - 1, count,
			orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the fragment entries before and after the current fragment entry in the ordered set where groupId = &#63; and fragmentCollectionId = &#63; and name LIKE &#63; and status = &#63; and head = &#63;.
	 *
	 * @param fragmentEntryId the primary key of the current fragment entry
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param name the name
	 * @param status the status
	 * @param head the head
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next fragment entry
	 * @throws NoSuchEntryException if a fragment entry with the primary key could not be found
	 */
	@Override
	public FragmentEntry[] findByG_FCI_LikeN_S_Head_PrevAndNext(
			long fragmentEntryId, long groupId, long fragmentCollectionId,
			String name, int status, boolean head,
			OrderByComparator<FragmentEntry> orderByComparator)
		throws NoSuchEntryException {

		name = Objects.toString(name, "");

		FragmentEntry fragmentEntry = findByPrimaryKey(fragmentEntryId);

		Session session = null;

		try {
			session = openSession();

			FragmentEntry[] array = new FragmentEntryImpl[3];

			array[0] = getByG_FCI_LikeN_S_Head_PrevAndNext(
				session, fragmentEntry, groupId, fragmentCollectionId, name,
				status, head, orderByComparator, true);

			array[1] = fragmentEntry;

			array[2] = getByG_FCI_LikeN_S_Head_PrevAndNext(
				session, fragmentEntry, groupId, fragmentCollectionId, name,
				status, head, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected FragmentEntry getByG_FCI_LikeN_S_Head_PrevAndNext(
		Session session, FragmentEntry fragmentEntry, long groupId,
		long fragmentCollectionId, String name, int status, boolean head,
		OrderByComparator<FragmentEntry> orderByComparator, boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				8 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(7);
		}

		sb.append(_SQL_SELECT_FRAGMENTENTRY_WHERE);

		sb.append(_FINDER_COLUMN_G_FCI_LIKEN_S_HEAD_GROUPID_2);

		sb.append(_FINDER_COLUMN_G_FCI_LIKEN_S_HEAD_FRAGMENTCOLLECTIONID_2);

		boolean bindName = false;

		if (name.isEmpty()) {
			sb.append(_FINDER_COLUMN_G_FCI_LIKEN_S_HEAD_NAME_3);
		}
		else {
			bindName = true;

			sb.append(_FINDER_COLUMN_G_FCI_LIKEN_S_HEAD_NAME_2);
		}

		sb.append(_FINDER_COLUMN_G_FCI_LIKEN_S_HEAD_STATUS_2);

		sb.append(_FINDER_COLUMN_G_FCI_LIKEN_S_HEAD_HEAD_2);

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
			sb.append(FragmentEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(groupId);

		queryPos.add(fragmentCollectionId);

		if (bindName) {
			queryPos.add(name);
		}

		queryPos.add(status);

		queryPos.add(head);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						fragmentEntry)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<FragmentEntry> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the fragment entries where groupId = &#63; and fragmentCollectionId = &#63; and name LIKE &#63; and status = &#63; and head = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param name the name
	 * @param status the status
	 * @param head the head
	 */
	@Override
	public void removeByG_FCI_LikeN_S_Head(
		long groupId, long fragmentCollectionId, String name, int status,
		boolean head) {

		for (FragmentEntry fragmentEntry :
				findByG_FCI_LikeN_S_Head(
					groupId, fragmentCollectionId, name, status, head,
					QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(fragmentEntry);
		}
	}

	/**
	 * Returns the number of fragment entries where groupId = &#63; and fragmentCollectionId = &#63; and name LIKE &#63; and status = &#63; and head = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param name the name
	 * @param status the status
	 * @param head the head
	 * @return the number of matching fragment entries
	 */
	@Override
	public int countByG_FCI_LikeN_S_Head(
		long groupId, long fragmentCollectionId, String name, int status,
		boolean head) {

		name = Objects.toString(name, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			FragmentEntry.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathWithPaginationCountByG_FCI_LikeN_S_Head;

			finderArgs = new Object[] {
				groupId, fragmentCollectionId, name, status, head
			};

			count = (Long)finderCache.getResult(finderPath, finderArgs, this);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(6);

			sb.append(_SQL_COUNT_FRAGMENTENTRY_WHERE);

			sb.append(_FINDER_COLUMN_G_FCI_LIKEN_S_HEAD_GROUPID_2);

			sb.append(_FINDER_COLUMN_G_FCI_LIKEN_S_HEAD_FRAGMENTCOLLECTIONID_2);

			boolean bindName = false;

			if (name.isEmpty()) {
				sb.append(_FINDER_COLUMN_G_FCI_LIKEN_S_HEAD_NAME_3);
			}
			else {
				bindName = true;

				sb.append(_FINDER_COLUMN_G_FCI_LIKEN_S_HEAD_NAME_2);
			}

			sb.append(_FINDER_COLUMN_G_FCI_LIKEN_S_HEAD_STATUS_2);

			sb.append(_FINDER_COLUMN_G_FCI_LIKEN_S_HEAD_HEAD_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				queryPos.add(fragmentCollectionId);

				if (bindName) {
					queryPos.add(name);
				}

				queryPos.add(status);

				queryPos.add(head);

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

	private static final String _FINDER_COLUMN_G_FCI_LIKEN_S_HEAD_GROUPID_2 =
		"fragmentEntry.groupId = ? AND ";

	private static final String
		_FINDER_COLUMN_G_FCI_LIKEN_S_HEAD_FRAGMENTCOLLECTIONID_2 =
			"fragmentEntry.fragmentCollectionId = ? AND ";

	private static final String _FINDER_COLUMN_G_FCI_LIKEN_S_HEAD_NAME_2 =
		"fragmentEntry.name LIKE ? AND ";

	private static final String _FINDER_COLUMN_G_FCI_LIKEN_S_HEAD_NAME_3 =
		"(fragmentEntry.name IS NULL OR fragmentEntry.name LIKE '') AND ";

	private static final String _FINDER_COLUMN_G_FCI_LIKEN_S_HEAD_STATUS_2 =
		"fragmentEntry.status = ? AND ";

	private static final String _FINDER_COLUMN_G_FCI_LIKEN_S_HEAD_HEAD_2 =
		"fragmentEntry.head = ?";

	private FinderPath _finderPathWithPaginationFindByG_FCI_T_S;
	private FinderPath _finderPathWithoutPaginationFindByG_FCI_T_S;
	private FinderPath _finderPathCountByG_FCI_T_S;

	/**
	 * Returns all the fragment entries where groupId = &#63; and fragmentCollectionId = &#63; and type = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param type the type
	 * @param status the status
	 * @return the matching fragment entries
	 */
	@Override
	public List<FragmentEntry> findByG_FCI_T_S(
		long groupId, long fragmentCollectionId, int type, int status) {

		return findByG_FCI_T_S(
			groupId, fragmentCollectionId, type, status, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the fragment entries where groupId = &#63; and fragmentCollectionId = &#63; and type = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param type the type
	 * @param status the status
	 * @param start the lower bound of the range of fragment entries
	 * @param end the upper bound of the range of fragment entries (not inclusive)
	 * @return the range of matching fragment entries
	 */
	@Override
	public List<FragmentEntry> findByG_FCI_T_S(
		long groupId, long fragmentCollectionId, int type, int status,
		int start, int end) {

		return findByG_FCI_T_S(
			groupId, fragmentCollectionId, type, status, start, end, null);
	}

	/**
	 * Returns an ordered range of all the fragment entries where groupId = &#63; and fragmentCollectionId = &#63; and type = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param type the type
	 * @param status the status
	 * @param start the lower bound of the range of fragment entries
	 * @param end the upper bound of the range of fragment entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching fragment entries
	 */
	@Override
	public List<FragmentEntry> findByG_FCI_T_S(
		long groupId, long fragmentCollectionId, int type, int status,
		int start, int end,
		OrderByComparator<FragmentEntry> orderByComparator) {

		return findByG_FCI_T_S(
			groupId, fragmentCollectionId, type, status, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the fragment entries where groupId = &#63; and fragmentCollectionId = &#63; and type = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param type the type
	 * @param status the status
	 * @param start the lower bound of the range of fragment entries
	 * @param end the upper bound of the range of fragment entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching fragment entries
	 */
	@Override
	public List<FragmentEntry> findByG_FCI_T_S(
		long groupId, long fragmentCollectionId, int type, int status,
		int start, int end, OrderByComparator<FragmentEntry> orderByComparator,
		boolean useFinderCache) {

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			FragmentEntry.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache && productionMode) {
				finderPath = _finderPathWithoutPaginationFindByG_FCI_T_S;
				finderArgs = new Object[] {
					groupId, fragmentCollectionId, type, status
				};
			}
		}
		else if (useFinderCache && productionMode) {
			finderPath = _finderPathWithPaginationFindByG_FCI_T_S;
			finderArgs = new Object[] {
				groupId, fragmentCollectionId, type, status, start, end,
				orderByComparator
			};
		}

		List<FragmentEntry> list = null;

		if (useFinderCache && productionMode) {
			list = (List<FragmentEntry>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (FragmentEntry fragmentEntry : list) {
					if ((groupId != fragmentEntry.getGroupId()) ||
						(fragmentCollectionId !=
							fragmentEntry.getFragmentCollectionId()) ||
						(type != fragmentEntry.getType()) ||
						(status != fragmentEntry.getStatus())) {

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

			sb.append(_SQL_SELECT_FRAGMENTENTRY_WHERE);

			sb.append(_FINDER_COLUMN_G_FCI_T_S_GROUPID_2);

			sb.append(_FINDER_COLUMN_G_FCI_T_S_FRAGMENTCOLLECTIONID_2);

			sb.append(_FINDER_COLUMN_G_FCI_T_S_TYPE_2);

			sb.append(_FINDER_COLUMN_G_FCI_T_S_STATUS_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(FragmentEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				queryPos.add(fragmentCollectionId);

				queryPos.add(type);

				queryPos.add(status);

				list = (List<FragmentEntry>)QueryUtil.list(
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
	 * Returns the first fragment entry in the ordered set where groupId = &#63; and fragmentCollectionId = &#63; and type = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param type the type
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment entry
	 * @throws NoSuchEntryException if a matching fragment entry could not be found
	 */
	@Override
	public FragmentEntry findByG_FCI_T_S_First(
			long groupId, long fragmentCollectionId, int type, int status,
			OrderByComparator<FragmentEntry> orderByComparator)
		throws NoSuchEntryException {

		FragmentEntry fragmentEntry = fetchByG_FCI_T_S_First(
			groupId, fragmentCollectionId, type, status, orderByComparator);

		if (fragmentEntry != null) {
			return fragmentEntry;
		}

		StringBundler sb = new StringBundler(10);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", fragmentCollectionId=");
		sb.append(fragmentCollectionId);

		sb.append(", type=");
		sb.append(type);

		sb.append(", status=");
		sb.append(status);

		sb.append("}");

		throw new NoSuchEntryException(sb.toString());
	}

	/**
	 * Returns the first fragment entry in the ordered set where groupId = &#63; and fragmentCollectionId = &#63; and type = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param type the type
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment entry, or <code>null</code> if a matching fragment entry could not be found
	 */
	@Override
	public FragmentEntry fetchByG_FCI_T_S_First(
		long groupId, long fragmentCollectionId, int type, int status,
		OrderByComparator<FragmentEntry> orderByComparator) {

		List<FragmentEntry> list = findByG_FCI_T_S(
			groupId, fragmentCollectionId, type, status, 0, 1,
			orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last fragment entry in the ordered set where groupId = &#63; and fragmentCollectionId = &#63; and type = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param type the type
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching fragment entry
	 * @throws NoSuchEntryException if a matching fragment entry could not be found
	 */
	@Override
	public FragmentEntry findByG_FCI_T_S_Last(
			long groupId, long fragmentCollectionId, int type, int status,
			OrderByComparator<FragmentEntry> orderByComparator)
		throws NoSuchEntryException {

		FragmentEntry fragmentEntry = fetchByG_FCI_T_S_Last(
			groupId, fragmentCollectionId, type, status, orderByComparator);

		if (fragmentEntry != null) {
			return fragmentEntry;
		}

		StringBundler sb = new StringBundler(10);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", fragmentCollectionId=");
		sb.append(fragmentCollectionId);

		sb.append(", type=");
		sb.append(type);

		sb.append(", status=");
		sb.append(status);

		sb.append("}");

		throw new NoSuchEntryException(sb.toString());
	}

	/**
	 * Returns the last fragment entry in the ordered set where groupId = &#63; and fragmentCollectionId = &#63; and type = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param type the type
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching fragment entry, or <code>null</code> if a matching fragment entry could not be found
	 */
	@Override
	public FragmentEntry fetchByG_FCI_T_S_Last(
		long groupId, long fragmentCollectionId, int type, int status,
		OrderByComparator<FragmentEntry> orderByComparator) {

		int count = countByG_FCI_T_S(
			groupId, fragmentCollectionId, type, status);

		if (count == 0) {
			return null;
		}

		List<FragmentEntry> list = findByG_FCI_T_S(
			groupId, fragmentCollectionId, type, status, count - 1, count,
			orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the fragment entries before and after the current fragment entry in the ordered set where groupId = &#63; and fragmentCollectionId = &#63; and type = &#63; and status = &#63;.
	 *
	 * @param fragmentEntryId the primary key of the current fragment entry
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param type the type
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next fragment entry
	 * @throws NoSuchEntryException if a fragment entry with the primary key could not be found
	 */
	@Override
	public FragmentEntry[] findByG_FCI_T_S_PrevAndNext(
			long fragmentEntryId, long groupId, long fragmentCollectionId,
			int type, int status,
			OrderByComparator<FragmentEntry> orderByComparator)
		throws NoSuchEntryException {

		FragmentEntry fragmentEntry = findByPrimaryKey(fragmentEntryId);

		Session session = null;

		try {
			session = openSession();

			FragmentEntry[] array = new FragmentEntryImpl[3];

			array[0] = getByG_FCI_T_S_PrevAndNext(
				session, fragmentEntry, groupId, fragmentCollectionId, type,
				status, orderByComparator, true);

			array[1] = fragmentEntry;

			array[2] = getByG_FCI_T_S_PrevAndNext(
				session, fragmentEntry, groupId, fragmentCollectionId, type,
				status, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected FragmentEntry getByG_FCI_T_S_PrevAndNext(
		Session session, FragmentEntry fragmentEntry, long groupId,
		long fragmentCollectionId, int type, int status,
		OrderByComparator<FragmentEntry> orderByComparator, boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				7 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(6);
		}

		sb.append(_SQL_SELECT_FRAGMENTENTRY_WHERE);

		sb.append(_FINDER_COLUMN_G_FCI_T_S_GROUPID_2);

		sb.append(_FINDER_COLUMN_G_FCI_T_S_FRAGMENTCOLLECTIONID_2);

		sb.append(_FINDER_COLUMN_G_FCI_T_S_TYPE_2);

		sb.append(_FINDER_COLUMN_G_FCI_T_S_STATUS_2);

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
			sb.append(FragmentEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(groupId);

		queryPos.add(fragmentCollectionId);

		queryPos.add(type);

		queryPos.add(status);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						fragmentEntry)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<FragmentEntry> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the fragment entries where groupId = &#63; and fragmentCollectionId = &#63; and type = &#63; and status = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param type the type
	 * @param status the status
	 */
	@Override
	public void removeByG_FCI_T_S(
		long groupId, long fragmentCollectionId, int type, int status) {

		for (FragmentEntry fragmentEntry :
				findByG_FCI_T_S(
					groupId, fragmentCollectionId, type, status,
					QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(fragmentEntry);
		}
	}

	/**
	 * Returns the number of fragment entries where groupId = &#63; and fragmentCollectionId = &#63; and type = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param type the type
	 * @param status the status
	 * @return the number of matching fragment entries
	 */
	@Override
	public int countByG_FCI_T_S(
		long groupId, long fragmentCollectionId, int type, int status) {

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			FragmentEntry.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByG_FCI_T_S;

			finderArgs = new Object[] {
				groupId, fragmentCollectionId, type, status
			};

			count = (Long)finderCache.getResult(finderPath, finderArgs, this);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(5);

			sb.append(_SQL_COUNT_FRAGMENTENTRY_WHERE);

			sb.append(_FINDER_COLUMN_G_FCI_T_S_GROUPID_2);

			sb.append(_FINDER_COLUMN_G_FCI_T_S_FRAGMENTCOLLECTIONID_2);

			sb.append(_FINDER_COLUMN_G_FCI_T_S_TYPE_2);

			sb.append(_FINDER_COLUMN_G_FCI_T_S_STATUS_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				queryPos.add(fragmentCollectionId);

				queryPos.add(type);

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

	private static final String _FINDER_COLUMN_G_FCI_T_S_GROUPID_2 =
		"fragmentEntry.groupId = ? AND ";

	private static final String
		_FINDER_COLUMN_G_FCI_T_S_FRAGMENTCOLLECTIONID_2 =
			"fragmentEntry.fragmentCollectionId = ? AND ";

	private static final String _FINDER_COLUMN_G_FCI_T_S_TYPE_2 =
		"fragmentEntry.type = ? AND ";

	private static final String _FINDER_COLUMN_G_FCI_T_S_STATUS_2 =
		"fragmentEntry.status = ?";

	private FinderPath _finderPathWithPaginationFindByG_FCI_T_S_Head;
	private FinderPath _finderPathWithoutPaginationFindByG_FCI_T_S_Head;
	private FinderPath _finderPathCountByG_FCI_T_S_Head;

	/**
	 * Returns all the fragment entries where groupId = &#63; and fragmentCollectionId = &#63; and type = &#63; and status = &#63; and head = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param type the type
	 * @param status the status
	 * @param head the head
	 * @return the matching fragment entries
	 */
	@Override
	public List<FragmentEntry> findByG_FCI_T_S_Head(
		long groupId, long fragmentCollectionId, int type, int status,
		boolean head) {

		return findByG_FCI_T_S_Head(
			groupId, fragmentCollectionId, type, status, head,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the fragment entries where groupId = &#63; and fragmentCollectionId = &#63; and type = &#63; and status = &#63; and head = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param type the type
	 * @param status the status
	 * @param head the head
	 * @param start the lower bound of the range of fragment entries
	 * @param end the upper bound of the range of fragment entries (not inclusive)
	 * @return the range of matching fragment entries
	 */
	@Override
	public List<FragmentEntry> findByG_FCI_T_S_Head(
		long groupId, long fragmentCollectionId, int type, int status,
		boolean head, int start, int end) {

		return findByG_FCI_T_S_Head(
			groupId, fragmentCollectionId, type, status, head, start, end,
			null);
	}

	/**
	 * Returns an ordered range of all the fragment entries where groupId = &#63; and fragmentCollectionId = &#63; and type = &#63; and status = &#63; and head = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param type the type
	 * @param status the status
	 * @param head the head
	 * @param start the lower bound of the range of fragment entries
	 * @param end the upper bound of the range of fragment entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching fragment entries
	 */
	@Override
	public List<FragmentEntry> findByG_FCI_T_S_Head(
		long groupId, long fragmentCollectionId, int type, int status,
		boolean head, int start, int end,
		OrderByComparator<FragmentEntry> orderByComparator) {

		return findByG_FCI_T_S_Head(
			groupId, fragmentCollectionId, type, status, head, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the fragment entries where groupId = &#63; and fragmentCollectionId = &#63; and type = &#63; and status = &#63; and head = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param type the type
	 * @param status the status
	 * @param head the head
	 * @param start the lower bound of the range of fragment entries
	 * @param end the upper bound of the range of fragment entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching fragment entries
	 */
	@Override
	public List<FragmentEntry> findByG_FCI_T_S_Head(
		long groupId, long fragmentCollectionId, int type, int status,
		boolean head, int start, int end,
		OrderByComparator<FragmentEntry> orderByComparator,
		boolean useFinderCache) {

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			FragmentEntry.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache && productionMode) {
				finderPath = _finderPathWithoutPaginationFindByG_FCI_T_S_Head;
				finderArgs = new Object[] {
					groupId, fragmentCollectionId, type, status, head
				};
			}
		}
		else if (useFinderCache && productionMode) {
			finderPath = _finderPathWithPaginationFindByG_FCI_T_S_Head;
			finderArgs = new Object[] {
				groupId, fragmentCollectionId, type, status, head, start, end,
				orderByComparator
			};
		}

		List<FragmentEntry> list = null;

		if (useFinderCache && productionMode) {
			list = (List<FragmentEntry>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (FragmentEntry fragmentEntry : list) {
					if ((groupId != fragmentEntry.getGroupId()) ||
						(fragmentCollectionId !=
							fragmentEntry.getFragmentCollectionId()) ||
						(type != fragmentEntry.getType()) ||
						(status != fragmentEntry.getStatus()) ||
						(head != fragmentEntry.isHead())) {

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
					7 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				sb = new StringBundler(7);
			}

			sb.append(_SQL_SELECT_FRAGMENTENTRY_WHERE);

			sb.append(_FINDER_COLUMN_G_FCI_T_S_HEAD_GROUPID_2);

			sb.append(_FINDER_COLUMN_G_FCI_T_S_HEAD_FRAGMENTCOLLECTIONID_2);

			sb.append(_FINDER_COLUMN_G_FCI_T_S_HEAD_TYPE_2);

			sb.append(_FINDER_COLUMN_G_FCI_T_S_HEAD_STATUS_2);

			sb.append(_FINDER_COLUMN_G_FCI_T_S_HEAD_HEAD_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(FragmentEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				queryPos.add(fragmentCollectionId);

				queryPos.add(type);

				queryPos.add(status);

				queryPos.add(head);

				list = (List<FragmentEntry>)QueryUtil.list(
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
	 * Returns the first fragment entry in the ordered set where groupId = &#63; and fragmentCollectionId = &#63; and type = &#63; and status = &#63; and head = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param type the type
	 * @param status the status
	 * @param head the head
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment entry
	 * @throws NoSuchEntryException if a matching fragment entry could not be found
	 */
	@Override
	public FragmentEntry findByG_FCI_T_S_Head_First(
			long groupId, long fragmentCollectionId, int type, int status,
			boolean head, OrderByComparator<FragmentEntry> orderByComparator)
		throws NoSuchEntryException {

		FragmentEntry fragmentEntry = fetchByG_FCI_T_S_Head_First(
			groupId, fragmentCollectionId, type, status, head,
			orderByComparator);

		if (fragmentEntry != null) {
			return fragmentEntry;
		}

		StringBundler sb = new StringBundler(12);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", fragmentCollectionId=");
		sb.append(fragmentCollectionId);

		sb.append(", type=");
		sb.append(type);

		sb.append(", status=");
		sb.append(status);

		sb.append(", head=");
		sb.append(head);

		sb.append("}");

		throw new NoSuchEntryException(sb.toString());
	}

	/**
	 * Returns the first fragment entry in the ordered set where groupId = &#63; and fragmentCollectionId = &#63; and type = &#63; and status = &#63; and head = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param type the type
	 * @param status the status
	 * @param head the head
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment entry, or <code>null</code> if a matching fragment entry could not be found
	 */
	@Override
	public FragmentEntry fetchByG_FCI_T_S_Head_First(
		long groupId, long fragmentCollectionId, int type, int status,
		boolean head, OrderByComparator<FragmentEntry> orderByComparator) {

		List<FragmentEntry> list = findByG_FCI_T_S_Head(
			groupId, fragmentCollectionId, type, status, head, 0, 1,
			orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last fragment entry in the ordered set where groupId = &#63; and fragmentCollectionId = &#63; and type = &#63; and status = &#63; and head = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param type the type
	 * @param status the status
	 * @param head the head
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching fragment entry
	 * @throws NoSuchEntryException if a matching fragment entry could not be found
	 */
	@Override
	public FragmentEntry findByG_FCI_T_S_Head_Last(
			long groupId, long fragmentCollectionId, int type, int status,
			boolean head, OrderByComparator<FragmentEntry> orderByComparator)
		throws NoSuchEntryException {

		FragmentEntry fragmentEntry = fetchByG_FCI_T_S_Head_Last(
			groupId, fragmentCollectionId, type, status, head,
			orderByComparator);

		if (fragmentEntry != null) {
			return fragmentEntry;
		}

		StringBundler sb = new StringBundler(12);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", fragmentCollectionId=");
		sb.append(fragmentCollectionId);

		sb.append(", type=");
		sb.append(type);

		sb.append(", status=");
		sb.append(status);

		sb.append(", head=");
		sb.append(head);

		sb.append("}");

		throw new NoSuchEntryException(sb.toString());
	}

	/**
	 * Returns the last fragment entry in the ordered set where groupId = &#63; and fragmentCollectionId = &#63; and type = &#63; and status = &#63; and head = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param type the type
	 * @param status the status
	 * @param head the head
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching fragment entry, or <code>null</code> if a matching fragment entry could not be found
	 */
	@Override
	public FragmentEntry fetchByG_FCI_T_S_Head_Last(
		long groupId, long fragmentCollectionId, int type, int status,
		boolean head, OrderByComparator<FragmentEntry> orderByComparator) {

		int count = countByG_FCI_T_S_Head(
			groupId, fragmentCollectionId, type, status, head);

		if (count == 0) {
			return null;
		}

		List<FragmentEntry> list = findByG_FCI_T_S_Head(
			groupId, fragmentCollectionId, type, status, head, count - 1, count,
			orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the fragment entries before and after the current fragment entry in the ordered set where groupId = &#63; and fragmentCollectionId = &#63; and type = &#63; and status = &#63; and head = &#63;.
	 *
	 * @param fragmentEntryId the primary key of the current fragment entry
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param type the type
	 * @param status the status
	 * @param head the head
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next fragment entry
	 * @throws NoSuchEntryException if a fragment entry with the primary key could not be found
	 */
	@Override
	public FragmentEntry[] findByG_FCI_T_S_Head_PrevAndNext(
			long fragmentEntryId, long groupId, long fragmentCollectionId,
			int type, int status, boolean head,
			OrderByComparator<FragmentEntry> orderByComparator)
		throws NoSuchEntryException {

		FragmentEntry fragmentEntry = findByPrimaryKey(fragmentEntryId);

		Session session = null;

		try {
			session = openSession();

			FragmentEntry[] array = new FragmentEntryImpl[3];

			array[0] = getByG_FCI_T_S_Head_PrevAndNext(
				session, fragmentEntry, groupId, fragmentCollectionId, type,
				status, head, orderByComparator, true);

			array[1] = fragmentEntry;

			array[2] = getByG_FCI_T_S_Head_PrevAndNext(
				session, fragmentEntry, groupId, fragmentCollectionId, type,
				status, head, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected FragmentEntry getByG_FCI_T_S_Head_PrevAndNext(
		Session session, FragmentEntry fragmentEntry, long groupId,
		long fragmentCollectionId, int type, int status, boolean head,
		OrderByComparator<FragmentEntry> orderByComparator, boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				8 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(7);
		}

		sb.append(_SQL_SELECT_FRAGMENTENTRY_WHERE);

		sb.append(_FINDER_COLUMN_G_FCI_T_S_HEAD_GROUPID_2);

		sb.append(_FINDER_COLUMN_G_FCI_T_S_HEAD_FRAGMENTCOLLECTIONID_2);

		sb.append(_FINDER_COLUMN_G_FCI_T_S_HEAD_TYPE_2);

		sb.append(_FINDER_COLUMN_G_FCI_T_S_HEAD_STATUS_2);

		sb.append(_FINDER_COLUMN_G_FCI_T_S_HEAD_HEAD_2);

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
			sb.append(FragmentEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(groupId);

		queryPos.add(fragmentCollectionId);

		queryPos.add(type);

		queryPos.add(status);

		queryPos.add(head);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						fragmentEntry)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<FragmentEntry> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the fragment entries where groupId = &#63; and fragmentCollectionId = &#63; and type = &#63; and status = &#63; and head = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param type the type
	 * @param status the status
	 * @param head the head
	 */
	@Override
	public void removeByG_FCI_T_S_Head(
		long groupId, long fragmentCollectionId, int type, int status,
		boolean head) {

		for (FragmentEntry fragmentEntry :
				findByG_FCI_T_S_Head(
					groupId, fragmentCollectionId, type, status, head,
					QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(fragmentEntry);
		}
	}

	/**
	 * Returns the number of fragment entries where groupId = &#63; and fragmentCollectionId = &#63; and type = &#63; and status = &#63; and head = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentCollectionId the fragment collection ID
	 * @param type the type
	 * @param status the status
	 * @param head the head
	 * @return the number of matching fragment entries
	 */
	@Override
	public int countByG_FCI_T_S_Head(
		long groupId, long fragmentCollectionId, int type, int status,
		boolean head) {

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			FragmentEntry.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByG_FCI_T_S_Head;

			finderArgs = new Object[] {
				groupId, fragmentCollectionId, type, status, head
			};

			count = (Long)finderCache.getResult(finderPath, finderArgs, this);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(6);

			sb.append(_SQL_COUNT_FRAGMENTENTRY_WHERE);

			sb.append(_FINDER_COLUMN_G_FCI_T_S_HEAD_GROUPID_2);

			sb.append(_FINDER_COLUMN_G_FCI_T_S_HEAD_FRAGMENTCOLLECTIONID_2);

			sb.append(_FINDER_COLUMN_G_FCI_T_S_HEAD_TYPE_2);

			sb.append(_FINDER_COLUMN_G_FCI_T_S_HEAD_STATUS_2);

			sb.append(_FINDER_COLUMN_G_FCI_T_S_HEAD_HEAD_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				queryPos.add(fragmentCollectionId);

				queryPos.add(type);

				queryPos.add(status);

				queryPos.add(head);

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

	private static final String _FINDER_COLUMN_G_FCI_T_S_HEAD_GROUPID_2 =
		"fragmentEntry.groupId = ? AND ";

	private static final String
		_FINDER_COLUMN_G_FCI_T_S_HEAD_FRAGMENTCOLLECTIONID_2 =
			"fragmentEntry.fragmentCollectionId = ? AND ";

	private static final String _FINDER_COLUMN_G_FCI_T_S_HEAD_TYPE_2 =
		"fragmentEntry.type = ? AND ";

	private static final String _FINDER_COLUMN_G_FCI_T_S_HEAD_STATUS_2 =
		"fragmentEntry.status = ? AND ";

	private static final String _FINDER_COLUMN_G_FCI_T_S_HEAD_HEAD_2 =
		"fragmentEntry.head = ?";

	private FinderPath _finderPathFetchByHeadId;
	private FinderPath _finderPathCountByHeadId;

	/**
	 * Returns the fragment entry where headId = &#63; or throws a <code>NoSuchEntryException</code> if it could not be found.
	 *
	 * @param headId the head ID
	 * @return the matching fragment entry
	 * @throws NoSuchEntryException if a matching fragment entry could not be found
	 */
	@Override
	public FragmentEntry findByHeadId(long headId) throws NoSuchEntryException {
		FragmentEntry fragmentEntry = fetchByHeadId(headId);

		if (fragmentEntry == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("headId=");
			sb.append(headId);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchEntryException(sb.toString());
		}

		return fragmentEntry;
	}

	/**
	 * Returns the fragment entry where headId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param headId the head ID
	 * @return the matching fragment entry, or <code>null</code> if a matching fragment entry could not be found
	 */
	@Override
	public FragmentEntry fetchByHeadId(long headId) {
		return fetchByHeadId(headId, true);
	}

	/**
	 * Returns the fragment entry where headId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param headId the head ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching fragment entry, or <code>null</code> if a matching fragment entry could not be found
	 */
	@Override
	public FragmentEntry fetchByHeadId(long headId, boolean useFinderCache) {
		boolean productionMode = ctPersistenceHelper.isProductionMode(
			FragmentEntry.class);

		Object[] finderArgs = null;

		if (useFinderCache && productionMode) {
			finderArgs = new Object[] {headId};
		}

		Object result = null;

		if (useFinderCache && productionMode) {
			result = finderCache.getResult(
				_finderPathFetchByHeadId, finderArgs, this);
		}

		if (result instanceof FragmentEntry) {
			FragmentEntry fragmentEntry = (FragmentEntry)result;

			if (headId != fragmentEntry.getHeadId()) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_SELECT_FRAGMENTENTRY_WHERE);

			sb.append(_FINDER_COLUMN_HEADID_HEADID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(headId);

				List<FragmentEntry> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache && productionMode) {
						finderCache.putResult(
							_finderPathFetchByHeadId, finderArgs, list);
					}
				}
				else {
					FragmentEntry fragmentEntry = list.get(0);

					result = fragmentEntry;

					cacheResult(fragmentEntry);
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
			return (FragmentEntry)result;
		}
	}

	/**
	 * Removes the fragment entry where headId = &#63; from the database.
	 *
	 * @param headId the head ID
	 * @return the fragment entry that was removed
	 */
	@Override
	public FragmentEntry removeByHeadId(long headId)
		throws NoSuchEntryException {

		FragmentEntry fragmentEntry = findByHeadId(headId);

		return remove(fragmentEntry);
	}

	/**
	 * Returns the number of fragment entries where headId = &#63;.
	 *
	 * @param headId the head ID
	 * @return the number of matching fragment entries
	 */
	@Override
	public int countByHeadId(long headId) {
		boolean productionMode = ctPersistenceHelper.isProductionMode(
			FragmentEntry.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByHeadId;

			finderArgs = new Object[] {headId};

			count = (Long)finderCache.getResult(finderPath, finderArgs, this);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_FRAGMENTENTRY_WHERE);

			sb.append(_FINDER_COLUMN_HEADID_HEADID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(headId);

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

	private static final String _FINDER_COLUMN_HEADID_HEADID_2 =
		"fragmentEntry.headId = ?";

	public FragmentEntryPersistenceImpl() {
		Map<String, String> dbColumnNames = new HashMap<String, String>();

		dbColumnNames.put("uuid", "uuid_");
		dbColumnNames.put("type", "type_");

		setDBColumnNames(dbColumnNames);

		setModelClass(FragmentEntry.class);

		setModelImplClass(FragmentEntryImpl.class);
		setModelPKClass(long.class);

		setTable(FragmentEntryTable.INSTANCE);
	}

	/**
	 * Caches the fragment entry in the entity cache if it is enabled.
	 *
	 * @param fragmentEntry the fragment entry
	 */
	@Override
	public void cacheResult(FragmentEntry fragmentEntry) {
		if (fragmentEntry.getCtCollectionId() != 0) {
			fragmentEntry.resetOriginalValues();

			return;
		}

		entityCache.putResult(
			FragmentEntryImpl.class, fragmentEntry.getPrimaryKey(),
			fragmentEntry);

		finderCache.putResult(
			_finderPathFetchByUUID_G_Head,
			new Object[] {
				fragmentEntry.getUuid(), fragmentEntry.getGroupId(),
				fragmentEntry.isHead()
			},
			fragmentEntry);

		finderCache.putResult(
			_finderPathFetchByG_FEK_Head,
			new Object[] {
				fragmentEntry.getGroupId(), fragmentEntry.getFragmentEntryKey(),
				fragmentEntry.isHead()
			},
			fragmentEntry);

		finderCache.putResult(
			_finderPathFetchByHeadId, new Object[] {fragmentEntry.getHeadId()},
			fragmentEntry);

		fragmentEntry.resetOriginalValues();
	}

	/**
	 * Caches the fragment entries in the entity cache if it is enabled.
	 *
	 * @param fragmentEntries the fragment entries
	 */
	@Override
	public void cacheResult(List<FragmentEntry> fragmentEntries) {
		for (FragmentEntry fragmentEntry : fragmentEntries) {
			if (fragmentEntry.getCtCollectionId() != 0) {
				fragmentEntry.resetOriginalValues();

				continue;
			}

			if (entityCache.getResult(
					FragmentEntryImpl.class, fragmentEntry.getPrimaryKey()) ==
						null) {

				cacheResult(fragmentEntry);
			}
			else {
				fragmentEntry.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all fragment entries.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(FragmentEntryImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the fragment entry.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(FragmentEntry fragmentEntry) {
		entityCache.removeResult(
			FragmentEntryImpl.class, fragmentEntry.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache((FragmentEntryModelImpl)fragmentEntry, true);
	}

	@Override
	public void clearCache(List<FragmentEntry> fragmentEntries) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (FragmentEntry fragmentEntry : fragmentEntries) {
			entityCache.removeResult(
				FragmentEntryImpl.class, fragmentEntry.getPrimaryKey());

			clearUniqueFindersCache(
				(FragmentEntryModelImpl)fragmentEntry, true);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(FragmentEntryImpl.class, primaryKey);
		}
	}

	protected void cacheUniqueFindersCache(
		FragmentEntryModelImpl fragmentEntryModelImpl) {

		Object[] args = new Object[] {
			fragmentEntryModelImpl.getUuid(),
			fragmentEntryModelImpl.getGroupId(), fragmentEntryModelImpl.isHead()
		};

		finderCache.putResult(
			_finderPathCountByUUID_G_Head, args, Long.valueOf(1), false);
		finderCache.putResult(
			_finderPathFetchByUUID_G_Head, args, fragmentEntryModelImpl, false);

		args = new Object[] {
			fragmentEntryModelImpl.getGroupId(),
			fragmentEntryModelImpl.getFragmentEntryKey(),
			fragmentEntryModelImpl.isHead()
		};

		finderCache.putResult(
			_finderPathCountByG_FEK_Head, args, Long.valueOf(1), false);
		finderCache.putResult(
			_finderPathFetchByG_FEK_Head, args, fragmentEntryModelImpl, false);

		args = new Object[] {fragmentEntryModelImpl.getHeadId()};

		finderCache.putResult(
			_finderPathCountByHeadId, args, Long.valueOf(1), false);
		finderCache.putResult(
			_finderPathFetchByHeadId, args, fragmentEntryModelImpl, false);
	}

	protected void clearUniqueFindersCache(
		FragmentEntryModelImpl fragmentEntryModelImpl, boolean clearCurrent) {

		if (clearCurrent) {
			Object[] args = new Object[] {
				fragmentEntryModelImpl.getUuid(),
				fragmentEntryModelImpl.getGroupId(),
				fragmentEntryModelImpl.isHead()
			};

			finderCache.removeResult(_finderPathCountByUUID_G_Head, args);
			finderCache.removeResult(_finderPathFetchByUUID_G_Head, args);
		}

		if ((fragmentEntryModelImpl.getColumnBitmask() &
			 _finderPathFetchByUUID_G_Head.getColumnBitmask()) != 0) {

			Object[] args = new Object[] {
				fragmentEntryModelImpl.getOriginalUuid(),
				fragmentEntryModelImpl.getOriginalGroupId(),
				fragmentEntryModelImpl.getOriginalHead()
			};

			finderCache.removeResult(_finderPathCountByUUID_G_Head, args);
			finderCache.removeResult(_finderPathFetchByUUID_G_Head, args);
		}

		if (clearCurrent) {
			Object[] args = new Object[] {
				fragmentEntryModelImpl.getGroupId(),
				fragmentEntryModelImpl.getFragmentEntryKey(),
				fragmentEntryModelImpl.isHead()
			};

			finderCache.removeResult(_finderPathCountByG_FEK_Head, args);
			finderCache.removeResult(_finderPathFetchByG_FEK_Head, args);
		}

		if ((fragmentEntryModelImpl.getColumnBitmask() &
			 _finderPathFetchByG_FEK_Head.getColumnBitmask()) != 0) {

			Object[] args = new Object[] {
				fragmentEntryModelImpl.getOriginalGroupId(),
				fragmentEntryModelImpl.getOriginalFragmentEntryKey(),
				fragmentEntryModelImpl.getOriginalHead()
			};

			finderCache.removeResult(_finderPathCountByG_FEK_Head, args);
			finderCache.removeResult(_finderPathFetchByG_FEK_Head, args);
		}

		if (clearCurrent) {
			Object[] args = new Object[] {fragmentEntryModelImpl.getHeadId()};

			finderCache.removeResult(_finderPathCountByHeadId, args);
			finderCache.removeResult(_finderPathFetchByHeadId, args);
		}

		if ((fragmentEntryModelImpl.getColumnBitmask() &
			 _finderPathFetchByHeadId.getColumnBitmask()) != 0) {

			Object[] args = new Object[] {
				fragmentEntryModelImpl.getOriginalHeadId()
			};

			finderCache.removeResult(_finderPathCountByHeadId, args);
			finderCache.removeResult(_finderPathFetchByHeadId, args);
		}
	}

	/**
	 * Creates a new fragment entry with the primary key. Does not add the fragment entry to the database.
	 *
	 * @param fragmentEntryId the primary key for the new fragment entry
	 * @return the new fragment entry
	 */
	@Override
	public FragmentEntry create(long fragmentEntryId) {
		FragmentEntry fragmentEntry = new FragmentEntryImpl();

		fragmentEntry.setNew(true);
		fragmentEntry.setPrimaryKey(fragmentEntryId);

		String uuid = PortalUUIDUtil.generate();

		fragmentEntry.setUuid(uuid);

		fragmentEntry.setCompanyId(CompanyThreadLocal.getCompanyId());

		return fragmentEntry;
	}

	/**
	 * Removes the fragment entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param fragmentEntryId the primary key of the fragment entry
	 * @return the fragment entry that was removed
	 * @throws NoSuchEntryException if a fragment entry with the primary key could not be found
	 */
	@Override
	public FragmentEntry remove(long fragmentEntryId)
		throws NoSuchEntryException {

		return remove((Serializable)fragmentEntryId);
	}

	/**
	 * Removes the fragment entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the fragment entry
	 * @return the fragment entry that was removed
	 * @throws NoSuchEntryException if a fragment entry with the primary key could not be found
	 */
	@Override
	public FragmentEntry remove(Serializable primaryKey)
		throws NoSuchEntryException {

		Session session = null;

		try {
			session = openSession();

			FragmentEntry fragmentEntry = (FragmentEntry)session.get(
				FragmentEntryImpl.class, primaryKey);

			if (fragmentEntry == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchEntryException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(fragmentEntry);
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
	protected FragmentEntry removeImpl(FragmentEntry fragmentEntry) {
		if (!ctPersistenceHelper.isRemove(fragmentEntry)) {
			return fragmentEntry;
		}

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(fragmentEntry)) {
				fragmentEntry = (FragmentEntry)session.get(
					FragmentEntryImpl.class, fragmentEntry.getPrimaryKeyObj());
			}

			if (fragmentEntry != null) {
				session.delete(fragmentEntry);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (fragmentEntry != null) {
			clearCache(fragmentEntry);
		}

		return fragmentEntry;
	}

	@Override
	public FragmentEntry updateImpl(FragmentEntry fragmentEntry) {
		boolean isNew = fragmentEntry.isNew();

		if (!(fragmentEntry instanceof FragmentEntryModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(fragmentEntry.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					fragmentEntry);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in fragmentEntry proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom FragmentEntry implementation " +
					fragmentEntry.getClass());
		}

		FragmentEntryModelImpl fragmentEntryModelImpl =
			(FragmentEntryModelImpl)fragmentEntry;

		if (Validator.isNull(fragmentEntry.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			fragmentEntry.setUuid(uuid);
		}

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Date now = new Date();

		if (isNew && (fragmentEntry.getCreateDate() == null)) {
			if (serviceContext == null) {
				fragmentEntry.setCreateDate(now);
			}
			else {
				fragmentEntry.setCreateDate(serviceContext.getCreateDate(now));
			}
		}

		if (!fragmentEntryModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				fragmentEntry.setModifiedDate(now);
			}
			else {
				fragmentEntry.setModifiedDate(
					serviceContext.getModifiedDate(now));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (ctPersistenceHelper.isInsert(fragmentEntry)) {
				if (!isNew) {
					session.evict(
						FragmentEntryImpl.class,
						fragmentEntry.getPrimaryKeyObj());
				}

				session.save(fragmentEntry);

				fragmentEntry.setNew(false);
			}
			else {
				fragmentEntry = (FragmentEntry)session.merge(fragmentEntry);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (fragmentEntry.getCtCollectionId() != 0) {
			fragmentEntry.resetOriginalValues();

			return fragmentEntry;
		}

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (isNew) {
			Object[] args = new Object[] {fragmentEntryModelImpl.getUuid()};

			finderCache.removeResult(_finderPathCountByUuid, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByUuid, args);

			args = new Object[] {
				fragmentEntryModelImpl.getUuid(),
				fragmentEntryModelImpl.isHead()
			};

			finderCache.removeResult(_finderPathCountByUuid_Head, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByUuid_Head, args);

			args = new Object[] {
				fragmentEntryModelImpl.getUuid(),
				fragmentEntryModelImpl.getGroupId()
			};

			finderCache.removeResult(_finderPathCountByUUID_G, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByUUID_G, args);

			args = new Object[] {
				fragmentEntryModelImpl.getUuid(),
				fragmentEntryModelImpl.getCompanyId()
			};

			finderCache.removeResult(_finderPathCountByUuid_C, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByUuid_C, args);

			args = new Object[] {
				fragmentEntryModelImpl.getUuid(),
				fragmentEntryModelImpl.getCompanyId(),
				fragmentEntryModelImpl.isHead()
			};

			finderCache.removeResult(_finderPathCountByUuid_C_Head, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByUuid_C_Head, args);

			args = new Object[] {fragmentEntryModelImpl.getGroupId()};

			finderCache.removeResult(_finderPathCountByGroupId, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByGroupId, args);

			args = new Object[] {
				fragmentEntryModelImpl.getGroupId(),
				fragmentEntryModelImpl.isHead()
			};

			finderCache.removeResult(_finderPathCountByGroupId_Head, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByGroupId_Head, args);

			args = new Object[] {
				fragmentEntryModelImpl.getFragmentCollectionId()
			};

			finderCache.removeResult(
				_finderPathCountByFragmentCollectionId, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByFragmentCollectionId, args);

			args = new Object[] {
				fragmentEntryModelImpl.getFragmentCollectionId(),
				fragmentEntryModelImpl.isHead()
			};

			finderCache.removeResult(
				_finderPathCountByFragmentCollectionId_Head, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByFragmentCollectionId_Head,
				args);

			args = new Object[] {
				fragmentEntryModelImpl.getGroupId(),
				fragmentEntryModelImpl.getFragmentCollectionId()
			};

			finderCache.removeResult(_finderPathCountByG_FCI, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByG_FCI, args);

			args = new Object[] {
				fragmentEntryModelImpl.getGroupId(),
				fragmentEntryModelImpl.getFragmentCollectionId(),
				fragmentEntryModelImpl.isHead()
			};

			finderCache.removeResult(_finderPathCountByG_FCI_Head, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByG_FCI_Head, args);

			args = new Object[] {
				fragmentEntryModelImpl.getGroupId(),
				fragmentEntryModelImpl.getFragmentEntryKey()
			};

			finderCache.removeResult(_finderPathCountByG_FEK, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByG_FEK, args);

			args = new Object[] {
				fragmentEntryModelImpl.getGroupId(),
				fragmentEntryModelImpl.getFragmentCollectionId(),
				fragmentEntryModelImpl.getType()
			};

			finderCache.removeResult(_finderPathCountByG_FCI_T, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByG_FCI_T, args);

			args = new Object[] {
				fragmentEntryModelImpl.getGroupId(),
				fragmentEntryModelImpl.getFragmentCollectionId(),
				fragmentEntryModelImpl.getType(),
				fragmentEntryModelImpl.isHead()
			};

			finderCache.removeResult(_finderPathCountByG_FCI_T_Head, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByG_FCI_T_Head, args);

			args = new Object[] {
				fragmentEntryModelImpl.getGroupId(),
				fragmentEntryModelImpl.getFragmentCollectionId(),
				fragmentEntryModelImpl.getStatus()
			};

			finderCache.removeResult(_finderPathCountByG_FCI_S, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByG_FCI_S, args);

			args = new Object[] {
				fragmentEntryModelImpl.getGroupId(),
				fragmentEntryModelImpl.getFragmentCollectionId(),
				fragmentEntryModelImpl.getStatus(),
				fragmentEntryModelImpl.isHead()
			};

			finderCache.removeResult(_finderPathCountByG_FCI_S_Head, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByG_FCI_S_Head, args);

			args = new Object[] {
				fragmentEntryModelImpl.getGroupId(),
				fragmentEntryModelImpl.getFragmentCollectionId(),
				fragmentEntryModelImpl.getType(),
				fragmentEntryModelImpl.getStatus()
			};

			finderCache.removeResult(_finderPathCountByG_FCI_T_S, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByG_FCI_T_S, args);

			args = new Object[] {
				fragmentEntryModelImpl.getGroupId(),
				fragmentEntryModelImpl.getFragmentCollectionId(),
				fragmentEntryModelImpl.getType(),
				fragmentEntryModelImpl.getStatus(),
				fragmentEntryModelImpl.isHead()
			};

			finderCache.removeResult(_finderPathCountByG_FCI_T_S_Head, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByG_FCI_T_S_Head, args);

			finderCache.removeResult(_finderPathCountAll, FINDER_ARGS_EMPTY);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindAll, FINDER_ARGS_EMPTY);
		}
		else {
			if ((fragmentEntryModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByUuid.getColumnBitmask()) !=
					 0) {

				Object[] args = new Object[] {
					fragmentEntryModelImpl.getOriginalUuid()
				};

				finderCache.removeResult(_finderPathCountByUuid, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByUuid, args);

				args = new Object[] {fragmentEntryModelImpl.getUuid()};

				finderCache.removeResult(_finderPathCountByUuid, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByUuid, args);
			}

			if ((fragmentEntryModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByUuid_Head.
					 getColumnBitmask()) != 0) {

				Object[] args = new Object[] {
					fragmentEntryModelImpl.getOriginalUuid(),
					fragmentEntryModelImpl.getOriginalHead()
				};

				finderCache.removeResult(_finderPathCountByUuid_Head, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByUuid_Head, args);

				args = new Object[] {
					fragmentEntryModelImpl.getUuid(),
					fragmentEntryModelImpl.isHead()
				};

				finderCache.removeResult(_finderPathCountByUuid_Head, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByUuid_Head, args);
			}

			if ((fragmentEntryModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByUUID_G.getColumnBitmask()) !=
					 0) {

				Object[] args = new Object[] {
					fragmentEntryModelImpl.getOriginalUuid(),
					fragmentEntryModelImpl.getOriginalGroupId()
				};

				finderCache.removeResult(_finderPathCountByUUID_G, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByUUID_G, args);

				args = new Object[] {
					fragmentEntryModelImpl.getUuid(),
					fragmentEntryModelImpl.getGroupId()
				};

				finderCache.removeResult(_finderPathCountByUUID_G, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByUUID_G, args);
			}

			if ((fragmentEntryModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByUuid_C.getColumnBitmask()) !=
					 0) {

				Object[] args = new Object[] {
					fragmentEntryModelImpl.getOriginalUuid(),
					fragmentEntryModelImpl.getOriginalCompanyId()
				};

				finderCache.removeResult(_finderPathCountByUuid_C, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByUuid_C, args);

				args = new Object[] {
					fragmentEntryModelImpl.getUuid(),
					fragmentEntryModelImpl.getCompanyId()
				};

				finderCache.removeResult(_finderPathCountByUuid_C, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByUuid_C, args);
			}

			if ((fragmentEntryModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByUuid_C_Head.
					 getColumnBitmask()) != 0) {

				Object[] args = new Object[] {
					fragmentEntryModelImpl.getOriginalUuid(),
					fragmentEntryModelImpl.getOriginalCompanyId(),
					fragmentEntryModelImpl.getOriginalHead()
				};

				finderCache.removeResult(_finderPathCountByUuid_C_Head, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByUuid_C_Head, args);

				args = new Object[] {
					fragmentEntryModelImpl.getUuid(),
					fragmentEntryModelImpl.getCompanyId(),
					fragmentEntryModelImpl.isHead()
				};

				finderCache.removeResult(_finderPathCountByUuid_C_Head, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByUuid_C_Head, args);
			}

			if ((fragmentEntryModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByGroupId.
					 getColumnBitmask()) != 0) {

				Object[] args = new Object[] {
					fragmentEntryModelImpl.getOriginalGroupId()
				};

				finderCache.removeResult(_finderPathCountByGroupId, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByGroupId, args);

				args = new Object[] {fragmentEntryModelImpl.getGroupId()};

				finderCache.removeResult(_finderPathCountByGroupId, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByGroupId, args);
			}

			if ((fragmentEntryModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByGroupId_Head.
					 getColumnBitmask()) != 0) {

				Object[] args = new Object[] {
					fragmentEntryModelImpl.getOriginalGroupId(),
					fragmentEntryModelImpl.getOriginalHead()
				};

				finderCache.removeResult(_finderPathCountByGroupId_Head, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByGroupId_Head, args);

				args = new Object[] {
					fragmentEntryModelImpl.getGroupId(),
					fragmentEntryModelImpl.isHead()
				};

				finderCache.removeResult(_finderPathCountByGroupId_Head, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByGroupId_Head, args);
			}

			if ((fragmentEntryModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByFragmentCollectionId.
					 getColumnBitmask()) != 0) {

				Object[] args = new Object[] {
					fragmentEntryModelImpl.getOriginalFragmentCollectionId()
				};

				finderCache.removeResult(
					_finderPathCountByFragmentCollectionId, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByFragmentCollectionId,
					args);

				args = new Object[] {
					fragmentEntryModelImpl.getFragmentCollectionId()
				};

				finderCache.removeResult(
					_finderPathCountByFragmentCollectionId, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByFragmentCollectionId,
					args);
			}

			if ((fragmentEntryModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByFragmentCollectionId_Head.
					 getColumnBitmask()) != 0) {

				Object[] args = new Object[] {
					fragmentEntryModelImpl.getOriginalFragmentCollectionId(),
					fragmentEntryModelImpl.getOriginalHead()
				};

				finderCache.removeResult(
					_finderPathCountByFragmentCollectionId_Head, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByFragmentCollectionId_Head,
					args);

				args = new Object[] {
					fragmentEntryModelImpl.getFragmentCollectionId(),
					fragmentEntryModelImpl.isHead()
				};

				finderCache.removeResult(
					_finderPathCountByFragmentCollectionId_Head, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByFragmentCollectionId_Head,
					args);
			}

			if ((fragmentEntryModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByG_FCI.getColumnBitmask()) !=
					 0) {

				Object[] args = new Object[] {
					fragmentEntryModelImpl.getOriginalGroupId(),
					fragmentEntryModelImpl.getOriginalFragmentCollectionId()
				};

				finderCache.removeResult(_finderPathCountByG_FCI, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByG_FCI, args);

				args = new Object[] {
					fragmentEntryModelImpl.getGroupId(),
					fragmentEntryModelImpl.getFragmentCollectionId()
				};

				finderCache.removeResult(_finderPathCountByG_FCI, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByG_FCI, args);
			}

			if ((fragmentEntryModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByG_FCI_Head.
					 getColumnBitmask()) != 0) {

				Object[] args = new Object[] {
					fragmentEntryModelImpl.getOriginalGroupId(),
					fragmentEntryModelImpl.getOriginalFragmentCollectionId(),
					fragmentEntryModelImpl.getOriginalHead()
				};

				finderCache.removeResult(_finderPathCountByG_FCI_Head, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByG_FCI_Head, args);

				args = new Object[] {
					fragmentEntryModelImpl.getGroupId(),
					fragmentEntryModelImpl.getFragmentCollectionId(),
					fragmentEntryModelImpl.isHead()
				};

				finderCache.removeResult(_finderPathCountByG_FCI_Head, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByG_FCI_Head, args);
			}

			if ((fragmentEntryModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByG_FEK.getColumnBitmask()) !=
					 0) {

				Object[] args = new Object[] {
					fragmentEntryModelImpl.getOriginalGroupId(),
					fragmentEntryModelImpl.getOriginalFragmentEntryKey()
				};

				finderCache.removeResult(_finderPathCountByG_FEK, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByG_FEK, args);

				args = new Object[] {
					fragmentEntryModelImpl.getGroupId(),
					fragmentEntryModelImpl.getFragmentEntryKey()
				};

				finderCache.removeResult(_finderPathCountByG_FEK, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByG_FEK, args);
			}

			if ((fragmentEntryModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByG_FCI_T.
					 getColumnBitmask()) != 0) {

				Object[] args = new Object[] {
					fragmentEntryModelImpl.getOriginalGroupId(),
					fragmentEntryModelImpl.getOriginalFragmentCollectionId(),
					fragmentEntryModelImpl.getOriginalType()
				};

				finderCache.removeResult(_finderPathCountByG_FCI_T, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByG_FCI_T, args);

				args = new Object[] {
					fragmentEntryModelImpl.getGroupId(),
					fragmentEntryModelImpl.getFragmentCollectionId(),
					fragmentEntryModelImpl.getType()
				};

				finderCache.removeResult(_finderPathCountByG_FCI_T, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByG_FCI_T, args);
			}

			if ((fragmentEntryModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByG_FCI_T_Head.
					 getColumnBitmask()) != 0) {

				Object[] args = new Object[] {
					fragmentEntryModelImpl.getOriginalGroupId(),
					fragmentEntryModelImpl.getOriginalFragmentCollectionId(),
					fragmentEntryModelImpl.getOriginalType(),
					fragmentEntryModelImpl.getOriginalHead()
				};

				finderCache.removeResult(_finderPathCountByG_FCI_T_Head, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByG_FCI_T_Head, args);

				args = new Object[] {
					fragmentEntryModelImpl.getGroupId(),
					fragmentEntryModelImpl.getFragmentCollectionId(),
					fragmentEntryModelImpl.getType(),
					fragmentEntryModelImpl.isHead()
				};

				finderCache.removeResult(_finderPathCountByG_FCI_T_Head, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByG_FCI_T_Head, args);
			}

			if ((fragmentEntryModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByG_FCI_S.
					 getColumnBitmask()) != 0) {

				Object[] args = new Object[] {
					fragmentEntryModelImpl.getOriginalGroupId(),
					fragmentEntryModelImpl.getOriginalFragmentCollectionId(),
					fragmentEntryModelImpl.getOriginalStatus()
				};

				finderCache.removeResult(_finderPathCountByG_FCI_S, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByG_FCI_S, args);

				args = new Object[] {
					fragmentEntryModelImpl.getGroupId(),
					fragmentEntryModelImpl.getFragmentCollectionId(),
					fragmentEntryModelImpl.getStatus()
				};

				finderCache.removeResult(_finderPathCountByG_FCI_S, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByG_FCI_S, args);
			}

			if ((fragmentEntryModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByG_FCI_S_Head.
					 getColumnBitmask()) != 0) {

				Object[] args = new Object[] {
					fragmentEntryModelImpl.getOriginalGroupId(),
					fragmentEntryModelImpl.getOriginalFragmentCollectionId(),
					fragmentEntryModelImpl.getOriginalStatus(),
					fragmentEntryModelImpl.getOriginalHead()
				};

				finderCache.removeResult(_finderPathCountByG_FCI_S_Head, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByG_FCI_S_Head, args);

				args = new Object[] {
					fragmentEntryModelImpl.getGroupId(),
					fragmentEntryModelImpl.getFragmentCollectionId(),
					fragmentEntryModelImpl.getStatus(),
					fragmentEntryModelImpl.isHead()
				};

				finderCache.removeResult(_finderPathCountByG_FCI_S_Head, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByG_FCI_S_Head, args);
			}

			if ((fragmentEntryModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByG_FCI_T_S.
					 getColumnBitmask()) != 0) {

				Object[] args = new Object[] {
					fragmentEntryModelImpl.getOriginalGroupId(),
					fragmentEntryModelImpl.getOriginalFragmentCollectionId(),
					fragmentEntryModelImpl.getOriginalType(),
					fragmentEntryModelImpl.getOriginalStatus()
				};

				finderCache.removeResult(_finderPathCountByG_FCI_T_S, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByG_FCI_T_S, args);

				args = new Object[] {
					fragmentEntryModelImpl.getGroupId(),
					fragmentEntryModelImpl.getFragmentCollectionId(),
					fragmentEntryModelImpl.getType(),
					fragmentEntryModelImpl.getStatus()
				};

				finderCache.removeResult(_finderPathCountByG_FCI_T_S, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByG_FCI_T_S, args);
			}

			if ((fragmentEntryModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByG_FCI_T_S_Head.
					 getColumnBitmask()) != 0) {

				Object[] args = new Object[] {
					fragmentEntryModelImpl.getOriginalGroupId(),
					fragmentEntryModelImpl.getOriginalFragmentCollectionId(),
					fragmentEntryModelImpl.getOriginalType(),
					fragmentEntryModelImpl.getOriginalStatus(),
					fragmentEntryModelImpl.getOriginalHead()
				};

				finderCache.removeResult(
					_finderPathCountByG_FCI_T_S_Head, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByG_FCI_T_S_Head, args);

				args = new Object[] {
					fragmentEntryModelImpl.getGroupId(),
					fragmentEntryModelImpl.getFragmentCollectionId(),
					fragmentEntryModelImpl.getType(),
					fragmentEntryModelImpl.getStatus(),
					fragmentEntryModelImpl.isHead()
				};

				finderCache.removeResult(
					_finderPathCountByG_FCI_T_S_Head, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByG_FCI_T_S_Head, args);
			}
		}

		entityCache.putResult(
			FragmentEntryImpl.class, fragmentEntry.getPrimaryKey(),
			fragmentEntry, false);

		clearUniqueFindersCache(fragmentEntryModelImpl, false);
		cacheUniqueFindersCache(fragmentEntryModelImpl);

		fragmentEntry.resetOriginalValues();

		return fragmentEntry;
	}

	/**
	 * Returns the fragment entry with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the fragment entry
	 * @return the fragment entry
	 * @throws NoSuchEntryException if a fragment entry with the primary key could not be found
	 */
	@Override
	public FragmentEntry findByPrimaryKey(Serializable primaryKey)
		throws NoSuchEntryException {

		FragmentEntry fragmentEntry = fetchByPrimaryKey(primaryKey);

		if (fragmentEntry == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchEntryException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return fragmentEntry;
	}

	/**
	 * Returns the fragment entry with the primary key or throws a <code>NoSuchEntryException</code> if it could not be found.
	 *
	 * @param fragmentEntryId the primary key of the fragment entry
	 * @return the fragment entry
	 * @throws NoSuchEntryException if a fragment entry with the primary key could not be found
	 */
	@Override
	public FragmentEntry findByPrimaryKey(long fragmentEntryId)
		throws NoSuchEntryException {

		return findByPrimaryKey((Serializable)fragmentEntryId);
	}

	/**
	 * Returns the fragment entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the fragment entry
	 * @return the fragment entry, or <code>null</code> if a fragment entry with the primary key could not be found
	 */
	@Override
	public FragmentEntry fetchByPrimaryKey(Serializable primaryKey) {
		if (ctPersistenceHelper.isProductionMode(FragmentEntry.class)) {
			return super.fetchByPrimaryKey(primaryKey);
		}

		FragmentEntry fragmentEntry = null;

		Session session = null;

		try {
			session = openSession();

			fragmentEntry = (FragmentEntry)session.get(
				FragmentEntryImpl.class, primaryKey);

			if (fragmentEntry != null) {
				cacheResult(fragmentEntry);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		return fragmentEntry;
	}

	/**
	 * Returns the fragment entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param fragmentEntryId the primary key of the fragment entry
	 * @return the fragment entry, or <code>null</code> if a fragment entry with the primary key could not be found
	 */
	@Override
	public FragmentEntry fetchByPrimaryKey(long fragmentEntryId) {
		return fetchByPrimaryKey((Serializable)fragmentEntryId);
	}

	@Override
	public Map<Serializable, FragmentEntry> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		if (ctPersistenceHelper.isProductionMode(FragmentEntry.class)) {
			return super.fetchByPrimaryKeys(primaryKeys);
		}

		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, FragmentEntry> map =
			new HashMap<Serializable, FragmentEntry>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			FragmentEntry fragmentEntry = fetchByPrimaryKey(primaryKey);

			if (fragmentEntry != null) {
				map.put(primaryKey, fragmentEntry);
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

			for (FragmentEntry fragmentEntry :
					(List<FragmentEntry>)query.list()) {

				map.put(fragmentEntry.getPrimaryKeyObj(), fragmentEntry);

				cacheResult(fragmentEntry);
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
	 * Returns all the fragment entries.
	 *
	 * @return the fragment entries
	 */
	@Override
	public List<FragmentEntry> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the fragment entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of fragment entries
	 * @param end the upper bound of the range of fragment entries (not inclusive)
	 * @return the range of fragment entries
	 */
	@Override
	public List<FragmentEntry> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the fragment entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of fragment entries
	 * @param end the upper bound of the range of fragment entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of fragment entries
	 */
	@Override
	public List<FragmentEntry> findAll(
		int start, int end,
		OrderByComparator<FragmentEntry> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the fragment entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of fragment entries
	 * @param end the upper bound of the range of fragment entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of fragment entries
	 */
	@Override
	public List<FragmentEntry> findAll(
		int start, int end, OrderByComparator<FragmentEntry> orderByComparator,
		boolean useFinderCache) {

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			FragmentEntry.class);

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

		List<FragmentEntry> list = null;

		if (useFinderCache && productionMode) {
			list = (List<FragmentEntry>)finderCache.getResult(
				finderPath, finderArgs, this);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_FRAGMENTENTRY);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_FRAGMENTENTRY;

				sql = sql.concat(FragmentEntryModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<FragmentEntry>)QueryUtil.list(
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
	 * Removes all the fragment entries from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (FragmentEntry fragmentEntry : findAll()) {
			remove(fragmentEntry);
		}
	}

	/**
	 * Returns the number of fragment entries.
	 *
	 * @return the number of fragment entries
	 */
	@Override
	public int countAll() {
		boolean productionMode = ctPersistenceHelper.isProductionMode(
			FragmentEntry.class);

		Long count = null;

		if (productionMode) {
			count = (Long)finderCache.getResult(
				_finderPathCountAll, FINDER_ARGS_EMPTY, this);
		}

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(_SQL_COUNT_FRAGMENTENTRY);

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
		return "fragmentEntryId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_FRAGMENTENTRY;
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
		return FragmentEntryModelImpl.TABLE_COLUMNS_MAP;
	}

	@Override
	public String getTableName() {
		return "FragmentEntry";
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
		ctStrictColumnNames.add("headId");
		ctStrictColumnNames.add("groupId");
		ctStrictColumnNames.add("companyId");
		ctStrictColumnNames.add("userId");
		ctStrictColumnNames.add("userName");
		ctStrictColumnNames.add("createDate");
		ctIgnoreColumnNames.add("modifiedDate");
		ctStrictColumnNames.add("fragmentCollectionId");
		ctStrictColumnNames.add("fragmentEntryKey");
		ctStrictColumnNames.add("name");
		ctStrictColumnNames.add("css");
		ctStrictColumnNames.add("html");
		ctStrictColumnNames.add("js");
		ctStrictColumnNames.add("cacheable");
		ctStrictColumnNames.add("configuration");
		ctStrictColumnNames.add("previewFileEntryId");
		ctStrictColumnNames.add("readOnly");
		ctStrictColumnNames.add("type_");
		ctStrictColumnNames.add("lastPublishDate");
		ctStrictColumnNames.add("status");
		ctStrictColumnNames.add("statusByUserId");
		ctStrictColumnNames.add("statusByUserName");
		ctStrictColumnNames.add("statusDate");

		_ctColumnNamesMap.put(
			CTColumnResolutionType.CONTROL, ctControlColumnNames);
		_ctColumnNamesMap.put(
			CTColumnResolutionType.IGNORE, ctIgnoreColumnNames);
		_ctColumnNamesMap.put(CTColumnResolutionType.MERGE, ctMergeColumnNames);
		_ctColumnNamesMap.put(
			CTColumnResolutionType.PK,
			Collections.singleton("fragmentEntryId"));
		_ctColumnNamesMap.put(
			CTColumnResolutionType.STRICT, ctStrictColumnNames);

		_uniqueIndexColumnNames.add(new String[] {"uuid_", "groupId", "head"});

		_uniqueIndexColumnNames.add(
			new String[] {"groupId", "fragmentEntryKey", "head"});

		_uniqueIndexColumnNames.add(new String[] {"headId"});
	}

	/**
	 * Initializes the fragment entry persistence.
	 */
	@Activate
	public void activate() {
		_finderPathWithPaginationFindAll = new FinderPath(
			FragmentEntryImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findAll", new String[0]);

		_finderPathWithoutPaginationFindAll = new FinderPath(
			FragmentEntryImpl.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findAll", new String[0]);

		_finderPathCountAll = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll",
			new String[0]);

		_finderPathWithPaginationFindByUuid = new FinderPath(
			FragmentEntryImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByUuid",
			new String[] {
				String.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByUuid = new FinderPath(
			FragmentEntryImpl.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByUuid", new String[] {String.class.getName()},
			FragmentEntryModelImpl.UUID_COLUMN_BITMASK |
			FragmentEntryModelImpl.NAME_COLUMN_BITMASK);

		_finderPathCountByUuid = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByUuid", new String[] {String.class.getName()});

		_finderPathWithPaginationFindByUuid_Head = new FinderPath(
			FragmentEntryImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByUuid_Head",
			new String[] {
				String.class.getName(), Boolean.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByUuid_Head = new FinderPath(
			FragmentEntryImpl.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByUuid_Head",
			new String[] {String.class.getName(), Boolean.class.getName()},
			FragmentEntryModelImpl.UUID_COLUMN_BITMASK |
			FragmentEntryModelImpl.HEAD_COLUMN_BITMASK |
			FragmentEntryModelImpl.NAME_COLUMN_BITMASK);

		_finderPathCountByUuid_Head = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByUuid_Head",
			new String[] {String.class.getName(), Boolean.class.getName()});

		_finderPathWithPaginationFindByUUID_G = new FinderPath(
			FragmentEntryImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByUUID_G",
			new String[] {
				String.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByUUID_G = new FinderPath(
			FragmentEntryImpl.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByUUID_G",
			new String[] {String.class.getName(), Long.class.getName()},
			FragmentEntryModelImpl.UUID_COLUMN_BITMASK |
			FragmentEntryModelImpl.GROUPID_COLUMN_BITMASK |
			FragmentEntryModelImpl.NAME_COLUMN_BITMASK);

		_finderPathCountByUUID_G = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByUUID_G",
			new String[] {String.class.getName(), Long.class.getName()});

		_finderPathFetchByUUID_G_Head = new FinderPath(
			FragmentEntryImpl.class, FINDER_CLASS_NAME_ENTITY,
			"fetchByUUID_G_Head",
			new String[] {
				String.class.getName(), Long.class.getName(),
				Boolean.class.getName()
			},
			FragmentEntryModelImpl.UUID_COLUMN_BITMASK |
			FragmentEntryModelImpl.GROUPID_COLUMN_BITMASK |
			FragmentEntryModelImpl.HEAD_COLUMN_BITMASK);

		_finderPathCountByUUID_G_Head = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByUUID_G_Head",
			new String[] {
				String.class.getName(), Long.class.getName(),
				Boolean.class.getName()
			});

		_finderPathWithPaginationFindByUuid_C = new FinderPath(
			FragmentEntryImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByUuid_C",
			new String[] {
				String.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByUuid_C = new FinderPath(
			FragmentEntryImpl.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByUuid_C",
			new String[] {String.class.getName(), Long.class.getName()},
			FragmentEntryModelImpl.UUID_COLUMN_BITMASK |
			FragmentEntryModelImpl.COMPANYID_COLUMN_BITMASK |
			FragmentEntryModelImpl.NAME_COLUMN_BITMASK);

		_finderPathCountByUuid_C = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByUuid_C",
			new String[] {String.class.getName(), Long.class.getName()});

		_finderPathWithPaginationFindByUuid_C_Head = new FinderPath(
			FragmentEntryImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByUuid_C_Head",
			new String[] {
				String.class.getName(), Long.class.getName(),
				Boolean.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByUuid_C_Head = new FinderPath(
			FragmentEntryImpl.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByUuid_C_Head",
			new String[] {
				String.class.getName(), Long.class.getName(),
				Boolean.class.getName()
			},
			FragmentEntryModelImpl.UUID_COLUMN_BITMASK |
			FragmentEntryModelImpl.COMPANYID_COLUMN_BITMASK |
			FragmentEntryModelImpl.HEAD_COLUMN_BITMASK |
			FragmentEntryModelImpl.NAME_COLUMN_BITMASK);

		_finderPathCountByUuid_C_Head = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByUuid_C_Head",
			new String[] {
				String.class.getName(), Long.class.getName(),
				Boolean.class.getName()
			});

		_finderPathWithPaginationFindByGroupId = new FinderPath(
			FragmentEntryImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByGroupId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByGroupId = new FinderPath(
			FragmentEntryImpl.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByGroupId", new String[] {Long.class.getName()},
			FragmentEntryModelImpl.GROUPID_COLUMN_BITMASK |
			FragmentEntryModelImpl.NAME_COLUMN_BITMASK);

		_finderPathCountByGroupId = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByGroupId", new String[] {Long.class.getName()});

		_finderPathWithPaginationFindByGroupId_Head = new FinderPath(
			FragmentEntryImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByGroupId_Head",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByGroupId_Head = new FinderPath(
			FragmentEntryImpl.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByGroupId_Head",
			new String[] {Long.class.getName(), Boolean.class.getName()},
			FragmentEntryModelImpl.GROUPID_COLUMN_BITMASK |
			FragmentEntryModelImpl.HEAD_COLUMN_BITMASK |
			FragmentEntryModelImpl.NAME_COLUMN_BITMASK);

		_finderPathCountByGroupId_Head = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByGroupId_Head",
			new String[] {Long.class.getName(), Boolean.class.getName()});

		_finderPathWithPaginationFindByFragmentCollectionId = new FinderPath(
			FragmentEntryImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByFragmentCollectionId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByFragmentCollectionId = new FinderPath(
			FragmentEntryImpl.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByFragmentCollectionId", new String[] {Long.class.getName()},
			FragmentEntryModelImpl.FRAGMENTCOLLECTIONID_COLUMN_BITMASK |
			FragmentEntryModelImpl.NAME_COLUMN_BITMASK);

		_finderPathCountByFragmentCollectionId = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByFragmentCollectionId", new String[] {Long.class.getName()});

		_finderPathWithPaginationFindByFragmentCollectionId_Head =
			new FinderPath(
				FragmentEntryImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
				"findByFragmentCollectionId_Head",
				new String[] {
					Long.class.getName(), Boolean.class.getName(),
					Integer.class.getName(), Integer.class.getName(),
					OrderByComparator.class.getName()
				});

		_finderPathWithoutPaginationFindByFragmentCollectionId_Head =
			new FinderPath(
				FragmentEntryImpl.class,
				FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
				"findByFragmentCollectionId_Head",
				new String[] {Long.class.getName(), Boolean.class.getName()},
				FragmentEntryModelImpl.FRAGMENTCOLLECTIONID_COLUMN_BITMASK |
				FragmentEntryModelImpl.HEAD_COLUMN_BITMASK |
				FragmentEntryModelImpl.NAME_COLUMN_BITMASK);

		_finderPathCountByFragmentCollectionId_Head = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByFragmentCollectionId_Head",
			new String[] {Long.class.getName(), Boolean.class.getName()});

		_finderPathWithPaginationFindByG_FCI = new FinderPath(
			FragmentEntryImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByG_FCI",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByG_FCI = new FinderPath(
			FragmentEntryImpl.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByG_FCI",
			new String[] {Long.class.getName(), Long.class.getName()},
			FragmentEntryModelImpl.GROUPID_COLUMN_BITMASK |
			FragmentEntryModelImpl.FRAGMENTCOLLECTIONID_COLUMN_BITMASK |
			FragmentEntryModelImpl.NAME_COLUMN_BITMASK);

		_finderPathCountByG_FCI = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByG_FCI",
			new String[] {Long.class.getName(), Long.class.getName()});

		_finderPathWithPaginationFindByG_FCI_Head = new FinderPath(
			FragmentEntryImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByG_FCI_Head",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Boolean.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByG_FCI_Head = new FinderPath(
			FragmentEntryImpl.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByG_FCI_Head",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Boolean.class.getName()
			},
			FragmentEntryModelImpl.GROUPID_COLUMN_BITMASK |
			FragmentEntryModelImpl.FRAGMENTCOLLECTIONID_COLUMN_BITMASK |
			FragmentEntryModelImpl.HEAD_COLUMN_BITMASK |
			FragmentEntryModelImpl.NAME_COLUMN_BITMASK);

		_finderPathCountByG_FCI_Head = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByG_FCI_Head",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Boolean.class.getName()
			});

		_finderPathWithPaginationFindByG_FEK = new FinderPath(
			FragmentEntryImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByG_FEK",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByG_FEK = new FinderPath(
			FragmentEntryImpl.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByG_FEK",
			new String[] {Long.class.getName(), String.class.getName()},
			FragmentEntryModelImpl.GROUPID_COLUMN_BITMASK |
			FragmentEntryModelImpl.FRAGMENTENTRYKEY_COLUMN_BITMASK |
			FragmentEntryModelImpl.NAME_COLUMN_BITMASK);

		_finderPathCountByG_FEK = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByG_FEK",
			new String[] {Long.class.getName(), String.class.getName()});

		_finderPathFetchByG_FEK_Head = new FinderPath(
			FragmentEntryImpl.class, FINDER_CLASS_NAME_ENTITY,
			"fetchByG_FEK_Head",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Boolean.class.getName()
			},
			FragmentEntryModelImpl.GROUPID_COLUMN_BITMASK |
			FragmentEntryModelImpl.FRAGMENTENTRYKEY_COLUMN_BITMASK |
			FragmentEntryModelImpl.HEAD_COLUMN_BITMASK);

		_finderPathCountByG_FEK_Head = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByG_FEK_Head",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Boolean.class.getName()
			});

		_finderPathWithPaginationFindByG_FCI_LikeN = new FinderPath(
			FragmentEntryImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByG_FCI_LikeN",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				String.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithPaginationCountByG_FCI_LikeN = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"countByG_FCI_LikeN",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				String.class.getName()
			});

		_finderPathWithPaginationFindByG_FCI_LikeN_Head = new FinderPath(
			FragmentEntryImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByG_FCI_LikeN_Head",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				String.class.getName(), Boolean.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});

		_finderPathWithPaginationCountByG_FCI_LikeN_Head = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"countByG_FCI_LikeN_Head",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				String.class.getName(), Boolean.class.getName()
			});

		_finderPathWithPaginationFindByG_FCI_T = new FinderPath(
			FragmentEntryImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByG_FCI_T",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByG_FCI_T = new FinderPath(
			FragmentEntryImpl.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByG_FCI_T",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName()
			},
			FragmentEntryModelImpl.GROUPID_COLUMN_BITMASK |
			FragmentEntryModelImpl.FRAGMENTCOLLECTIONID_COLUMN_BITMASK |
			FragmentEntryModelImpl.TYPE_COLUMN_BITMASK |
			FragmentEntryModelImpl.NAME_COLUMN_BITMASK);

		_finderPathCountByG_FCI_T = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByG_FCI_T",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName()
			});

		_finderPathWithPaginationFindByG_FCI_T_Head = new FinderPath(
			FragmentEntryImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByG_FCI_T_Head",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName(), Boolean.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByG_FCI_T_Head = new FinderPath(
			FragmentEntryImpl.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByG_FCI_T_Head",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName(), Boolean.class.getName()
			},
			FragmentEntryModelImpl.GROUPID_COLUMN_BITMASK |
			FragmentEntryModelImpl.FRAGMENTCOLLECTIONID_COLUMN_BITMASK |
			FragmentEntryModelImpl.TYPE_COLUMN_BITMASK |
			FragmentEntryModelImpl.HEAD_COLUMN_BITMASK |
			FragmentEntryModelImpl.NAME_COLUMN_BITMASK);

		_finderPathCountByG_FCI_T_Head = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByG_FCI_T_Head",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName(), Boolean.class.getName()
			});

		_finderPathWithPaginationFindByG_FCI_S = new FinderPath(
			FragmentEntryImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByG_FCI_S",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByG_FCI_S = new FinderPath(
			FragmentEntryImpl.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByG_FCI_S",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName()
			},
			FragmentEntryModelImpl.GROUPID_COLUMN_BITMASK |
			FragmentEntryModelImpl.FRAGMENTCOLLECTIONID_COLUMN_BITMASK |
			FragmentEntryModelImpl.STATUS_COLUMN_BITMASK |
			FragmentEntryModelImpl.NAME_COLUMN_BITMASK);

		_finderPathCountByG_FCI_S = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByG_FCI_S",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName()
			});

		_finderPathWithPaginationFindByG_FCI_S_Head = new FinderPath(
			FragmentEntryImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByG_FCI_S_Head",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName(), Boolean.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByG_FCI_S_Head = new FinderPath(
			FragmentEntryImpl.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByG_FCI_S_Head",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName(), Boolean.class.getName()
			},
			FragmentEntryModelImpl.GROUPID_COLUMN_BITMASK |
			FragmentEntryModelImpl.FRAGMENTCOLLECTIONID_COLUMN_BITMASK |
			FragmentEntryModelImpl.STATUS_COLUMN_BITMASK |
			FragmentEntryModelImpl.HEAD_COLUMN_BITMASK |
			FragmentEntryModelImpl.NAME_COLUMN_BITMASK);

		_finderPathCountByG_FCI_S_Head = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByG_FCI_S_Head",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName(), Boolean.class.getName()
			});

		_finderPathWithPaginationFindByG_FCI_LikeN_S = new FinderPath(
			FragmentEntryImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByG_FCI_LikeN_S",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				String.class.getName(), Integer.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});

		_finderPathWithPaginationCountByG_FCI_LikeN_S = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"countByG_FCI_LikeN_S",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				String.class.getName(), Integer.class.getName()
			});

		_finderPathWithPaginationFindByG_FCI_LikeN_S_Head = new FinderPath(
			FragmentEntryImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByG_FCI_LikeN_S_Head",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				String.class.getName(), Integer.class.getName(),
				Boolean.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithPaginationCountByG_FCI_LikeN_S_Head = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"countByG_FCI_LikeN_S_Head",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				String.class.getName(), Integer.class.getName(),
				Boolean.class.getName()
			});

		_finderPathWithPaginationFindByG_FCI_T_S = new FinderPath(
			FragmentEntryImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByG_FCI_T_S",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByG_FCI_T_S = new FinderPath(
			FragmentEntryImpl.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByG_FCI_T_S",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName()
			},
			FragmentEntryModelImpl.GROUPID_COLUMN_BITMASK |
			FragmentEntryModelImpl.FRAGMENTCOLLECTIONID_COLUMN_BITMASK |
			FragmentEntryModelImpl.TYPE_COLUMN_BITMASK |
			FragmentEntryModelImpl.STATUS_COLUMN_BITMASK |
			FragmentEntryModelImpl.NAME_COLUMN_BITMASK);

		_finderPathCountByG_FCI_T_S = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByG_FCI_T_S",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName()
			});

		_finderPathWithPaginationFindByG_FCI_T_S_Head = new FinderPath(
			FragmentEntryImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByG_FCI_T_S_Head",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				Boolean.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByG_FCI_T_S_Head = new FinderPath(
			FragmentEntryImpl.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByG_FCI_T_S_Head",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				Boolean.class.getName()
			},
			FragmentEntryModelImpl.GROUPID_COLUMN_BITMASK |
			FragmentEntryModelImpl.FRAGMENTCOLLECTIONID_COLUMN_BITMASK |
			FragmentEntryModelImpl.TYPE_COLUMN_BITMASK |
			FragmentEntryModelImpl.STATUS_COLUMN_BITMASK |
			FragmentEntryModelImpl.HEAD_COLUMN_BITMASK |
			FragmentEntryModelImpl.NAME_COLUMN_BITMASK);

		_finderPathCountByG_FCI_T_S_Head = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByG_FCI_T_S_Head",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				Boolean.class.getName()
			});

		_finderPathFetchByHeadId = new FinderPath(
			FragmentEntryImpl.class, FINDER_CLASS_NAME_ENTITY, "fetchByHeadId",
			new String[] {Long.class.getName()},
			FragmentEntryModelImpl.HEADID_COLUMN_BITMASK);

		_finderPathCountByHeadId = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByHeadId", new String[] {Long.class.getName()});
	}

	@Deactivate
	public void deactivate() {
		entityCache.removeCache(FragmentEntryImpl.class.getName());
		finderCache.removeCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@Override
	@Reference(
		target = FragmentPersistenceConstants.SERVICE_CONFIGURATION_FILTER,
		unbind = "-"
	)
	public void setConfiguration(Configuration configuration) {
	}

	@Override
	@Reference(
		target = FragmentPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setDataSource(DataSource dataSource) {
		super.setDataSource(dataSource);
	}

	@Override
	@Reference(
		target = FragmentPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setSessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	@Reference
	protected CTPersistenceHelper ctPersistenceHelper;

	@Reference
	protected EntityCache entityCache;

	@Reference
	protected FinderCache finderCache;

	private static final String _SQL_SELECT_FRAGMENTENTRY =
		"SELECT fragmentEntry FROM FragmentEntry fragmentEntry";

	private static final String _SQL_SELECT_FRAGMENTENTRY_WHERE =
		"SELECT fragmentEntry FROM FragmentEntry fragmentEntry WHERE ";

	private static final String _SQL_COUNT_FRAGMENTENTRY =
		"SELECT COUNT(fragmentEntry) FROM FragmentEntry fragmentEntry";

	private static final String _SQL_COUNT_FRAGMENTENTRY_WHERE =
		"SELECT COUNT(fragmentEntry) FROM FragmentEntry fragmentEntry WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS = "fragmentEntry.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No FragmentEntry exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No FragmentEntry exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		FragmentEntryPersistenceImpl.class);

	private static final Set<String> _badColumnNames = SetUtil.fromArray(
		new String[] {"uuid", "type"});

	static {
		try {
			Class.forName(FragmentPersistenceConstants.class.getName());
		}
		catch (ClassNotFoundException classNotFoundException) {
			throw new ExceptionInInitializerError(classNotFoundException);
		}
	}

}