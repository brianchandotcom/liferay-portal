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

package com.liferay.frontend.view.state.service.persistence.impl;

import com.liferay.frontend.view.state.exception.NoSuchActiveEntryException;
import com.liferay.frontend.view.state.model.FVSActiveEntry;
import com.liferay.frontend.view.state.model.FVSActiveEntryTable;
import com.liferay.frontend.view.state.model.impl.FVSActiveEntryImpl;
import com.liferay.frontend.view.state.model.impl.FVSActiveEntryModelImpl;
import com.liferay.frontend.view.state.service.persistence.FVSActiveEntryPersistence;
import com.liferay.frontend.view.state.service.persistence.impl.constants.FVSPersistenceConstants;
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
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;

import java.io.Serializable;

import java.lang.reflect.InvocationHandler;

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
 * The persistence implementation for the fvs active entry service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
@Component(service = {FVSActiveEntryPersistence.class, BasePersistence.class})
public class FVSActiveEntryPersistenceImpl
	extends BasePersistenceImpl<FVSActiveEntry>
	implements FVSActiveEntryPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>FVSActiveEntryUtil</code> to access the fvs active entry persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		FVSActiveEntryImpl.class.getName();

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
	 * Returns all the fvs active entries where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching fvs active entries
	 */
	@Override
	public List<FVSActiveEntry> findByUuid(String uuid) {
		return findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the fvs active entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FVSActiveEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of fvs active entries
	 * @param end the upper bound of the range of fvs active entries (not inclusive)
	 * @return the range of matching fvs active entries
	 */
	@Override
	public List<FVSActiveEntry> findByUuid(String uuid, int start, int end) {
		return findByUuid(uuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the fvs active entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FVSActiveEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of fvs active entries
	 * @param end the upper bound of the range of fvs active entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching fvs active entries
	 */
	@Override
	public List<FVSActiveEntry> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<FVSActiveEntry> orderByComparator) {

		return findByUuid(uuid, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the fvs active entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FVSActiveEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of fvs active entries
	 * @param end the upper bound of the range of fvs active entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching fvs active entries
	 */
	@Override
	public List<FVSActiveEntry> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<FVSActiveEntry> orderByComparator,
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

		List<FVSActiveEntry> list = null;

		if (useFinderCache) {
			list = (List<FVSActiveEntry>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (FVSActiveEntry fvsActiveEntry : list) {
					if (!uuid.equals(fvsActiveEntry.getUuid())) {
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

			sb.append(_SQL_SELECT_FVSACTIVEENTRY_WHERE);

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
				sb.append(FVSActiveEntryModelImpl.ORDER_BY_JPQL);
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

				list = (List<FVSActiveEntry>)QueryUtil.list(
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
	 * Returns the first fvs active entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fvs active entry
	 * @throws NoSuchActiveEntryException if a matching fvs active entry could not be found
	 */
	@Override
	public FVSActiveEntry findByUuid_First(
			String uuid, OrderByComparator<FVSActiveEntry> orderByComparator)
		throws NoSuchActiveEntryException {

		FVSActiveEntry fvsActiveEntry = fetchByUuid_First(
			uuid, orderByComparator);

		if (fvsActiveEntry != null) {
			return fvsActiveEntry;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append("}");

		throw new NoSuchActiveEntryException(sb.toString());
	}

	/**
	 * Returns the first fvs active entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fvs active entry, or <code>null</code> if a matching fvs active entry could not be found
	 */
	@Override
	public FVSActiveEntry fetchByUuid_First(
		String uuid, OrderByComparator<FVSActiveEntry> orderByComparator) {

		List<FVSActiveEntry> list = findByUuid(uuid, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last fvs active entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching fvs active entry
	 * @throws NoSuchActiveEntryException if a matching fvs active entry could not be found
	 */
	@Override
	public FVSActiveEntry findByUuid_Last(
			String uuid, OrderByComparator<FVSActiveEntry> orderByComparator)
		throws NoSuchActiveEntryException {

		FVSActiveEntry fvsActiveEntry = fetchByUuid_Last(
			uuid, orderByComparator);

		if (fvsActiveEntry != null) {
			return fvsActiveEntry;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append("}");

		throw new NoSuchActiveEntryException(sb.toString());
	}

	/**
	 * Returns the last fvs active entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching fvs active entry, or <code>null</code> if a matching fvs active entry could not be found
	 */
	@Override
	public FVSActiveEntry fetchByUuid_Last(
		String uuid, OrderByComparator<FVSActiveEntry> orderByComparator) {

		int count = countByUuid(uuid);

		if (count == 0) {
			return null;
		}

		List<FVSActiveEntry> list = findByUuid(
			uuid, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the fvs active entries before and after the current fvs active entry in the ordered set where uuid = &#63;.
	 *
	 * @param fvsActiveEntryId the primary key of the current fvs active entry
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next fvs active entry
	 * @throws NoSuchActiveEntryException if a fvs active entry with the primary key could not be found
	 */
	@Override
	public FVSActiveEntry[] findByUuid_PrevAndNext(
			long fvsActiveEntryId, String uuid,
			OrderByComparator<FVSActiveEntry> orderByComparator)
		throws NoSuchActiveEntryException {

		uuid = Objects.toString(uuid, "");

		FVSActiveEntry fvsActiveEntry = findByPrimaryKey(fvsActiveEntryId);

		Session session = null;

		try {
			session = openSession();

			FVSActiveEntry[] array = new FVSActiveEntryImpl[3];

			array[0] = getByUuid_PrevAndNext(
				session, fvsActiveEntry, uuid, orderByComparator, true);

			array[1] = fvsActiveEntry;

			array[2] = getByUuid_PrevAndNext(
				session, fvsActiveEntry, uuid, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected FVSActiveEntry getByUuid_PrevAndNext(
		Session session, FVSActiveEntry fvsActiveEntry, String uuid,
		OrderByComparator<FVSActiveEntry> orderByComparator, boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(3);
		}

		sb.append(_SQL_SELECT_FVSACTIVEENTRY_WHERE);

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
			sb.append(FVSActiveEntryModelImpl.ORDER_BY_JPQL);
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
						fvsActiveEntry)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<FVSActiveEntry> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the fvs active entries where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	@Override
	public void removeByUuid(String uuid) {
		for (FVSActiveEntry fvsActiveEntry :
				findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(fvsActiveEntry);
		}
	}

	/**
	 * Returns the number of fvs active entries where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching fvs active entries
	 */
	@Override
	public int countByUuid(String uuid) {
		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = _finderPathCountByUuid;

		Object[] finderArgs = new Object[] {uuid};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_FVSACTIVEENTRY_WHERE);

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
		"fvsActiveEntry.uuid = ?";

	private static final String _FINDER_COLUMN_UUID_UUID_3 =
		"(fvsActiveEntry.uuid IS NULL OR fvsActiveEntry.uuid = '')";

	private FinderPath _finderPathWithPaginationFindByUuid_C;
	private FinderPath _finderPathWithoutPaginationFindByUuid_C;
	private FinderPath _finderPathCountByUuid_C;

	/**
	 * Returns all the fvs active entries where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching fvs active entries
	 */
	@Override
	public List<FVSActiveEntry> findByUuid_C(String uuid, long companyId) {
		return findByUuid_C(
			uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the fvs active entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FVSActiveEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of fvs active entries
	 * @param end the upper bound of the range of fvs active entries (not inclusive)
	 * @return the range of matching fvs active entries
	 */
	@Override
	public List<FVSActiveEntry> findByUuid_C(
		String uuid, long companyId, int start, int end) {

		return findByUuid_C(uuid, companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the fvs active entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FVSActiveEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of fvs active entries
	 * @param end the upper bound of the range of fvs active entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching fvs active entries
	 */
	@Override
	public List<FVSActiveEntry> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<FVSActiveEntry> orderByComparator) {

		return findByUuid_C(
			uuid, companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the fvs active entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FVSActiveEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of fvs active entries
	 * @param end the upper bound of the range of fvs active entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching fvs active entries
	 */
	@Override
	public List<FVSActiveEntry> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<FVSActiveEntry> orderByComparator,
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

		List<FVSActiveEntry> list = null;

		if (useFinderCache) {
			list = (List<FVSActiveEntry>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (FVSActiveEntry fvsActiveEntry : list) {
					if (!uuid.equals(fvsActiveEntry.getUuid()) ||
						(companyId != fvsActiveEntry.getCompanyId())) {

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

			sb.append(_SQL_SELECT_FVSACTIVEENTRY_WHERE);

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
				sb.append(FVSActiveEntryModelImpl.ORDER_BY_JPQL);
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

				list = (List<FVSActiveEntry>)QueryUtil.list(
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
	 * Returns the first fvs active entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fvs active entry
	 * @throws NoSuchActiveEntryException if a matching fvs active entry could not be found
	 */
	@Override
	public FVSActiveEntry findByUuid_C_First(
			String uuid, long companyId,
			OrderByComparator<FVSActiveEntry> orderByComparator)
		throws NoSuchActiveEntryException {

		FVSActiveEntry fvsActiveEntry = fetchByUuid_C_First(
			uuid, companyId, orderByComparator);

		if (fvsActiveEntry != null) {
			return fvsActiveEntry;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append(", companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchActiveEntryException(sb.toString());
	}

	/**
	 * Returns the first fvs active entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fvs active entry, or <code>null</code> if a matching fvs active entry could not be found
	 */
	@Override
	public FVSActiveEntry fetchByUuid_C_First(
		String uuid, long companyId,
		OrderByComparator<FVSActiveEntry> orderByComparator) {

		List<FVSActiveEntry> list = findByUuid_C(
			uuid, companyId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last fvs active entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching fvs active entry
	 * @throws NoSuchActiveEntryException if a matching fvs active entry could not be found
	 */
	@Override
	public FVSActiveEntry findByUuid_C_Last(
			String uuid, long companyId,
			OrderByComparator<FVSActiveEntry> orderByComparator)
		throws NoSuchActiveEntryException {

		FVSActiveEntry fvsActiveEntry = fetchByUuid_C_Last(
			uuid, companyId, orderByComparator);

		if (fvsActiveEntry != null) {
			return fvsActiveEntry;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append(", companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchActiveEntryException(sb.toString());
	}

	/**
	 * Returns the last fvs active entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching fvs active entry, or <code>null</code> if a matching fvs active entry could not be found
	 */
	@Override
	public FVSActiveEntry fetchByUuid_C_Last(
		String uuid, long companyId,
		OrderByComparator<FVSActiveEntry> orderByComparator) {

		int count = countByUuid_C(uuid, companyId);

		if (count == 0) {
			return null;
		}

		List<FVSActiveEntry> list = findByUuid_C(
			uuid, companyId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the fvs active entries before and after the current fvs active entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param fvsActiveEntryId the primary key of the current fvs active entry
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next fvs active entry
	 * @throws NoSuchActiveEntryException if a fvs active entry with the primary key could not be found
	 */
	@Override
	public FVSActiveEntry[] findByUuid_C_PrevAndNext(
			long fvsActiveEntryId, String uuid, long companyId,
			OrderByComparator<FVSActiveEntry> orderByComparator)
		throws NoSuchActiveEntryException {

		uuid = Objects.toString(uuid, "");

		FVSActiveEntry fvsActiveEntry = findByPrimaryKey(fvsActiveEntryId);

		Session session = null;

		try {
			session = openSession();

			FVSActiveEntry[] array = new FVSActiveEntryImpl[3];

			array[0] = getByUuid_C_PrevAndNext(
				session, fvsActiveEntry, uuid, companyId, orderByComparator,
				true);

			array[1] = fvsActiveEntry;

			array[2] = getByUuid_C_PrevAndNext(
				session, fvsActiveEntry, uuid, companyId, orderByComparator,
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

	protected FVSActiveEntry getByUuid_C_PrevAndNext(
		Session session, FVSActiveEntry fvsActiveEntry, String uuid,
		long companyId, OrderByComparator<FVSActiveEntry> orderByComparator,
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

		sb.append(_SQL_SELECT_FVSACTIVEENTRY_WHERE);

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
			sb.append(FVSActiveEntryModelImpl.ORDER_BY_JPQL);
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
						fvsActiveEntry)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<FVSActiveEntry> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the fvs active entries where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	@Override
	public void removeByUuid_C(String uuid, long companyId) {
		for (FVSActiveEntry fvsActiveEntry :
				findByUuid_C(
					uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(fvsActiveEntry);
		}
	}

	/**
	 * Returns the number of fvs active entries where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching fvs active entries
	 */
	@Override
	public int countByUuid_C(String uuid, long companyId) {
		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = _finderPathCountByUuid_C;

		Object[] finderArgs = new Object[] {uuid, companyId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_FVSACTIVEENTRY_WHERE);

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
		"fvsActiveEntry.uuid = ? AND ";

	private static final String _FINDER_COLUMN_UUID_C_UUID_3 =
		"(fvsActiveEntry.uuid IS NULL OR fvsActiveEntry.uuid = '') AND ";

	private static final String _FINDER_COLUMN_UUID_C_COMPANYID_2 =
		"fvsActiveEntry.companyId = ?";

	private FinderPath _finderPathFetchByU_CDSDI_P_P;
	private FinderPath _finderPathCountByU_CDSDI_P_P;

	/**
	 * Returns the fvs active entry where userId = &#63; and clayDataSetDisplayId = &#63; and plid = &#63; and portletId = &#63; or throws a <code>NoSuchActiveEntryException</code> if it could not be found.
	 *
	 * @param userId the user ID
	 * @param clayDataSetDisplayId the clay data set display ID
	 * @param plid the plid
	 * @param portletId the portlet ID
	 * @return the matching fvs active entry
	 * @throws NoSuchActiveEntryException if a matching fvs active entry could not be found
	 */
	@Override
	public FVSActiveEntry findByU_CDSDI_P_P(
			long userId, String clayDataSetDisplayId, long plid,
			String portletId)
		throws NoSuchActiveEntryException {

		FVSActiveEntry fvsActiveEntry = fetchByU_CDSDI_P_P(
			userId, clayDataSetDisplayId, plid, portletId);

		if (fvsActiveEntry == null) {
			StringBundler sb = new StringBundler(10);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("userId=");
			sb.append(userId);

			sb.append(", clayDataSetDisplayId=");
			sb.append(clayDataSetDisplayId);

			sb.append(", plid=");
			sb.append(plid);

			sb.append(", portletId=");
			sb.append(portletId);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchActiveEntryException(sb.toString());
		}

		return fvsActiveEntry;
	}

	/**
	 * Returns the fvs active entry where userId = &#63; and clayDataSetDisplayId = &#63; and plid = &#63; and portletId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param userId the user ID
	 * @param clayDataSetDisplayId the clay data set display ID
	 * @param plid the plid
	 * @param portletId the portlet ID
	 * @return the matching fvs active entry, or <code>null</code> if a matching fvs active entry could not be found
	 */
	@Override
	public FVSActiveEntry fetchByU_CDSDI_P_P(
		long userId, String clayDataSetDisplayId, long plid, String portletId) {

		return fetchByU_CDSDI_P_P(
			userId, clayDataSetDisplayId, plid, portletId, true);
	}

	/**
	 * Returns the fvs active entry where userId = &#63; and clayDataSetDisplayId = &#63; and plid = &#63; and portletId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param userId the user ID
	 * @param clayDataSetDisplayId the clay data set display ID
	 * @param plid the plid
	 * @param portletId the portlet ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching fvs active entry, or <code>null</code> if a matching fvs active entry could not be found
	 */
	@Override
	public FVSActiveEntry fetchByU_CDSDI_P_P(
		long userId, String clayDataSetDisplayId, long plid, String portletId,
		boolean useFinderCache) {

		clayDataSetDisplayId = Objects.toString(clayDataSetDisplayId, "");
		portletId = Objects.toString(portletId, "");

		Object[] finderArgs = null;

		if (useFinderCache) {
			finderArgs = new Object[] {
				userId, clayDataSetDisplayId, plid, portletId
			};
		}

		Object result = null;

		if (useFinderCache) {
			result = finderCache.getResult(
				_finderPathFetchByU_CDSDI_P_P, finderArgs);
		}

		if (result instanceof FVSActiveEntry) {
			FVSActiveEntry fvsActiveEntry = (FVSActiveEntry)result;

			if ((userId != fvsActiveEntry.getUserId()) ||
				!Objects.equals(
					clayDataSetDisplayId,
					fvsActiveEntry.getClayDataSetDisplayId()) ||
				(plid != fvsActiveEntry.getPlid()) ||
				!Objects.equals(portletId, fvsActiveEntry.getPortletId())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(6);

			sb.append(_SQL_SELECT_FVSACTIVEENTRY_WHERE);

			sb.append(_FINDER_COLUMN_U_CDSDI_P_P_USERID_2);

			boolean bindClayDataSetDisplayId = false;

			if (clayDataSetDisplayId.isEmpty()) {
				sb.append(_FINDER_COLUMN_U_CDSDI_P_P_CLAYDATASETDISPLAYID_3);
			}
			else {
				bindClayDataSetDisplayId = true;

				sb.append(_FINDER_COLUMN_U_CDSDI_P_P_CLAYDATASETDISPLAYID_2);
			}

			sb.append(_FINDER_COLUMN_U_CDSDI_P_P_PLID_2);

			boolean bindPortletId = false;

			if (portletId.isEmpty()) {
				sb.append(_FINDER_COLUMN_U_CDSDI_P_P_PORTLETID_3);
			}
			else {
				bindPortletId = true;

				sb.append(_FINDER_COLUMN_U_CDSDI_P_P_PORTLETID_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(userId);

				if (bindClayDataSetDisplayId) {
					queryPos.add(clayDataSetDisplayId);
				}

				queryPos.add(plid);

				if (bindPortletId) {
					queryPos.add(portletId);
				}

				List<FVSActiveEntry> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache) {
						finderCache.putResult(
							_finderPathFetchByU_CDSDI_P_P, finderArgs, list);
					}
				}
				else {
					FVSActiveEntry fvsActiveEntry = list.get(0);

					result = fvsActiveEntry;

					cacheResult(fvsActiveEntry);
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
			return (FVSActiveEntry)result;
		}
	}

	/**
	 * Removes the fvs active entry where userId = &#63; and clayDataSetDisplayId = &#63; and plid = &#63; and portletId = &#63; from the database.
	 *
	 * @param userId the user ID
	 * @param clayDataSetDisplayId the clay data set display ID
	 * @param plid the plid
	 * @param portletId the portlet ID
	 * @return the fvs active entry that was removed
	 */
	@Override
	public FVSActiveEntry removeByU_CDSDI_P_P(
			long userId, String clayDataSetDisplayId, long plid,
			String portletId)
		throws NoSuchActiveEntryException {

		FVSActiveEntry fvsActiveEntry = findByU_CDSDI_P_P(
			userId, clayDataSetDisplayId, plid, portletId);

		return remove(fvsActiveEntry);
	}

	/**
	 * Returns the number of fvs active entries where userId = &#63; and clayDataSetDisplayId = &#63; and plid = &#63; and portletId = &#63;.
	 *
	 * @param userId the user ID
	 * @param clayDataSetDisplayId the clay data set display ID
	 * @param plid the plid
	 * @param portletId the portlet ID
	 * @return the number of matching fvs active entries
	 */
	@Override
	public int countByU_CDSDI_P_P(
		long userId, String clayDataSetDisplayId, long plid, String portletId) {

		clayDataSetDisplayId = Objects.toString(clayDataSetDisplayId, "");
		portletId = Objects.toString(portletId, "");

		FinderPath finderPath = _finderPathCountByU_CDSDI_P_P;

		Object[] finderArgs = new Object[] {
			userId, clayDataSetDisplayId, plid, portletId
		};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(5);

			sb.append(_SQL_COUNT_FVSACTIVEENTRY_WHERE);

			sb.append(_FINDER_COLUMN_U_CDSDI_P_P_USERID_2);

			boolean bindClayDataSetDisplayId = false;

			if (clayDataSetDisplayId.isEmpty()) {
				sb.append(_FINDER_COLUMN_U_CDSDI_P_P_CLAYDATASETDISPLAYID_3);
			}
			else {
				bindClayDataSetDisplayId = true;

				sb.append(_FINDER_COLUMN_U_CDSDI_P_P_CLAYDATASETDISPLAYID_2);
			}

			sb.append(_FINDER_COLUMN_U_CDSDI_P_P_PLID_2);

			boolean bindPortletId = false;

			if (portletId.isEmpty()) {
				sb.append(_FINDER_COLUMN_U_CDSDI_P_P_PORTLETID_3);
			}
			else {
				bindPortletId = true;

				sb.append(_FINDER_COLUMN_U_CDSDI_P_P_PORTLETID_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(userId);

				if (bindClayDataSetDisplayId) {
					queryPos.add(clayDataSetDisplayId);
				}

				queryPos.add(plid);

				if (bindPortletId) {
					queryPos.add(portletId);
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

	private static final String _FINDER_COLUMN_U_CDSDI_P_P_USERID_2 =
		"fvsActiveEntry.userId = ? AND ";

	private static final String
		_FINDER_COLUMN_U_CDSDI_P_P_CLAYDATASETDISPLAYID_2 =
			"fvsActiveEntry.clayDataSetDisplayId = ? AND ";

	private static final String
		_FINDER_COLUMN_U_CDSDI_P_P_CLAYDATASETDISPLAYID_3 =
			"(fvsActiveEntry.clayDataSetDisplayId IS NULL OR fvsActiveEntry.clayDataSetDisplayId = '') AND ";

	private static final String _FINDER_COLUMN_U_CDSDI_P_P_PLID_2 =
		"fvsActiveEntry.plid = ? AND ";

	private static final String _FINDER_COLUMN_U_CDSDI_P_P_PORTLETID_2 =
		"fvsActiveEntry.portletId = ?";

	private static final String _FINDER_COLUMN_U_CDSDI_P_P_PORTLETID_3 =
		"(fvsActiveEntry.portletId IS NULL OR fvsActiveEntry.portletId = '')";

	public FVSActiveEntryPersistenceImpl() {
		Map<String, String> dbColumnNames = new HashMap<String, String>();

		dbColumnNames.put("uuid", "uuid_");

		setDBColumnNames(dbColumnNames);

		setModelClass(FVSActiveEntry.class);

		setModelImplClass(FVSActiveEntryImpl.class);
		setModelPKClass(long.class);

		setTable(FVSActiveEntryTable.INSTANCE);
	}

	/**
	 * Caches the fvs active entry in the entity cache if it is enabled.
	 *
	 * @param fvsActiveEntry the fvs active entry
	 */
	@Override
	public void cacheResult(FVSActiveEntry fvsActiveEntry) {
		entityCache.putResult(
			FVSActiveEntryImpl.class, fvsActiveEntry.getPrimaryKey(),
			fvsActiveEntry);

		finderCache.putResult(
			_finderPathFetchByU_CDSDI_P_P,
			new Object[] {
				fvsActiveEntry.getUserId(),
				fvsActiveEntry.getClayDataSetDisplayId(),
				fvsActiveEntry.getPlid(), fvsActiveEntry.getPortletId()
			},
			fvsActiveEntry);
	}

	private int _valueObjectFinderCacheListThreshold;

	/**
	 * Caches the fvs active entries in the entity cache if it is enabled.
	 *
	 * @param fvsActiveEntries the fvs active entries
	 */
	@Override
	public void cacheResult(List<FVSActiveEntry> fvsActiveEntries) {
		if ((_valueObjectFinderCacheListThreshold == 0) ||
			((_valueObjectFinderCacheListThreshold > 0) &&
			 (fvsActiveEntries.size() >
				 _valueObjectFinderCacheListThreshold))) {

			return;
		}

		for (FVSActiveEntry fvsActiveEntry : fvsActiveEntries) {
			if (entityCache.getResult(
					FVSActiveEntryImpl.class, fvsActiveEntry.getPrimaryKey()) ==
						null) {

				cacheResult(fvsActiveEntry);
			}
		}
	}

	/**
	 * Clears the cache for all fvs active entries.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(FVSActiveEntryImpl.class);

		finderCache.clearCache(FVSActiveEntryImpl.class);
	}

	/**
	 * Clears the cache for the fvs active entry.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(FVSActiveEntry fvsActiveEntry) {
		entityCache.removeResult(FVSActiveEntryImpl.class, fvsActiveEntry);
	}

	@Override
	public void clearCache(List<FVSActiveEntry> fvsActiveEntries) {
		for (FVSActiveEntry fvsActiveEntry : fvsActiveEntries) {
			entityCache.removeResult(FVSActiveEntryImpl.class, fvsActiveEntry);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(FVSActiveEntryImpl.class);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(FVSActiveEntryImpl.class, primaryKey);
		}
	}

	protected void cacheUniqueFindersCache(
		FVSActiveEntryModelImpl fvsActiveEntryModelImpl) {

		Object[] args = new Object[] {
			fvsActiveEntryModelImpl.getUserId(),
			fvsActiveEntryModelImpl.getClayDataSetDisplayId(),
			fvsActiveEntryModelImpl.getPlid(),
			fvsActiveEntryModelImpl.getPortletId()
		};

		finderCache.putResult(
			_finderPathCountByU_CDSDI_P_P, args, Long.valueOf(1));
		finderCache.putResult(
			_finderPathFetchByU_CDSDI_P_P, args, fvsActiveEntryModelImpl);
	}

	/**
	 * Creates a new fvs active entry with the primary key. Does not add the fvs active entry to the database.
	 *
	 * @param fvsActiveEntryId the primary key for the new fvs active entry
	 * @return the new fvs active entry
	 */
	@Override
	public FVSActiveEntry create(long fvsActiveEntryId) {
		FVSActiveEntry fvsActiveEntry = new FVSActiveEntryImpl();

		fvsActiveEntry.setNew(true);
		fvsActiveEntry.setPrimaryKey(fvsActiveEntryId);

		String uuid = PortalUUIDUtil.generate();

		fvsActiveEntry.setUuid(uuid);

		fvsActiveEntry.setCompanyId(CompanyThreadLocal.getCompanyId());

		return fvsActiveEntry;
	}

	/**
	 * Removes the fvs active entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param fvsActiveEntryId the primary key of the fvs active entry
	 * @return the fvs active entry that was removed
	 * @throws NoSuchActiveEntryException if a fvs active entry with the primary key could not be found
	 */
	@Override
	public FVSActiveEntry remove(long fvsActiveEntryId)
		throws NoSuchActiveEntryException {

		return remove((Serializable)fvsActiveEntryId);
	}

	/**
	 * Removes the fvs active entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the fvs active entry
	 * @return the fvs active entry that was removed
	 * @throws NoSuchActiveEntryException if a fvs active entry with the primary key could not be found
	 */
	@Override
	public FVSActiveEntry remove(Serializable primaryKey)
		throws NoSuchActiveEntryException {

		Session session = null;

		try {
			session = openSession();

			FVSActiveEntry fvsActiveEntry = (FVSActiveEntry)session.get(
				FVSActiveEntryImpl.class, primaryKey);

			if (fvsActiveEntry == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchActiveEntryException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(fvsActiveEntry);
		}
		catch (NoSuchActiveEntryException noSuchEntityException) {
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
	protected FVSActiveEntry removeImpl(FVSActiveEntry fvsActiveEntry) {
		Session session = null;

		try {
			session = openSession();

			if (!session.contains(fvsActiveEntry)) {
				fvsActiveEntry = (FVSActiveEntry)session.get(
					FVSActiveEntryImpl.class,
					fvsActiveEntry.getPrimaryKeyObj());
			}

			if (fvsActiveEntry != null) {
				session.delete(fvsActiveEntry);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (fvsActiveEntry != null) {
			clearCache(fvsActiveEntry);
		}

		return fvsActiveEntry;
	}

	@Override
	public FVSActiveEntry updateImpl(FVSActiveEntry fvsActiveEntry) {
		boolean isNew = fvsActiveEntry.isNew();

		if (!(fvsActiveEntry instanceof FVSActiveEntryModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(fvsActiveEntry.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					fvsActiveEntry);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in fvsActiveEntry proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom FVSActiveEntry implementation " +
					fvsActiveEntry.getClass());
		}

		FVSActiveEntryModelImpl fvsActiveEntryModelImpl =
			(FVSActiveEntryModelImpl)fvsActiveEntry;

		if (Validator.isNull(fvsActiveEntry.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			fvsActiveEntry.setUuid(uuid);
		}

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Date date = new Date();

		if (isNew && (fvsActiveEntry.getCreateDate() == null)) {
			if (serviceContext == null) {
				fvsActiveEntry.setCreateDate(date);
			}
			else {
				fvsActiveEntry.setCreateDate(
					serviceContext.getCreateDate(date));
			}
		}

		if (!fvsActiveEntryModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				fvsActiveEntry.setModifiedDate(date);
			}
			else {
				fvsActiveEntry.setModifiedDate(
					serviceContext.getModifiedDate(date));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (isNew) {
				session.save(fvsActiveEntry);
			}
			else {
				fvsActiveEntry = (FVSActiveEntry)session.merge(fvsActiveEntry);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		entityCache.putResult(
			FVSActiveEntryImpl.class, fvsActiveEntryModelImpl, false, true);

		cacheUniqueFindersCache(fvsActiveEntryModelImpl);

		if (isNew) {
			fvsActiveEntry.setNew(false);
		}

		fvsActiveEntry.resetOriginalValues();

		return fvsActiveEntry;
	}

	/**
	 * Returns the fvs active entry with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the fvs active entry
	 * @return the fvs active entry
	 * @throws NoSuchActiveEntryException if a fvs active entry with the primary key could not be found
	 */
	@Override
	public FVSActiveEntry findByPrimaryKey(Serializable primaryKey)
		throws NoSuchActiveEntryException {

		FVSActiveEntry fvsActiveEntry = fetchByPrimaryKey(primaryKey);

		if (fvsActiveEntry == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchActiveEntryException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return fvsActiveEntry;
	}

	/**
	 * Returns the fvs active entry with the primary key or throws a <code>NoSuchActiveEntryException</code> if it could not be found.
	 *
	 * @param fvsActiveEntryId the primary key of the fvs active entry
	 * @return the fvs active entry
	 * @throws NoSuchActiveEntryException if a fvs active entry with the primary key could not be found
	 */
	@Override
	public FVSActiveEntry findByPrimaryKey(long fvsActiveEntryId)
		throws NoSuchActiveEntryException {

		return findByPrimaryKey((Serializable)fvsActiveEntryId);
	}

	/**
	 * Returns the fvs active entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param fvsActiveEntryId the primary key of the fvs active entry
	 * @return the fvs active entry, or <code>null</code> if a fvs active entry with the primary key could not be found
	 */
	@Override
	public FVSActiveEntry fetchByPrimaryKey(long fvsActiveEntryId) {
		return fetchByPrimaryKey((Serializable)fvsActiveEntryId);
	}

	/**
	 * Returns all the fvs active entries.
	 *
	 * @return the fvs active entries
	 */
	@Override
	public List<FVSActiveEntry> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the fvs active entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FVSActiveEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of fvs active entries
	 * @param end the upper bound of the range of fvs active entries (not inclusive)
	 * @return the range of fvs active entries
	 */
	@Override
	public List<FVSActiveEntry> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the fvs active entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FVSActiveEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of fvs active entries
	 * @param end the upper bound of the range of fvs active entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of fvs active entries
	 */
	@Override
	public List<FVSActiveEntry> findAll(
		int start, int end,
		OrderByComparator<FVSActiveEntry> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the fvs active entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FVSActiveEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of fvs active entries
	 * @param end the upper bound of the range of fvs active entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of fvs active entries
	 */
	@Override
	public List<FVSActiveEntry> findAll(
		int start, int end, OrderByComparator<FVSActiveEntry> orderByComparator,
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

		List<FVSActiveEntry> list = null;

		if (useFinderCache) {
			list = (List<FVSActiveEntry>)finderCache.getResult(
				finderPath, finderArgs);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_FVSACTIVEENTRY);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_FVSACTIVEENTRY;

				sql = sql.concat(FVSActiveEntryModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<FVSActiveEntry>)QueryUtil.list(
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
	 * Removes all the fvs active entries from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (FVSActiveEntry fvsActiveEntry : findAll()) {
			remove(fvsActiveEntry);
		}
	}

	/**
	 * Returns the number of fvs active entries.
	 *
	 * @return the number of fvs active entries
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(
			_finderPathCountAll, FINDER_ARGS_EMPTY);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(_SQL_COUNT_FVSACTIVEENTRY);

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
		return "fvsActiveEntryId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_FVSACTIVEENTRY;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return FVSActiveEntryModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the fvs active entry persistence.
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

		_finderPathFetchByU_CDSDI_P_P = new FinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByU_CDSDI_P_P",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Long.class.getName(), String.class.getName()
			},
			new String[] {
				"userId", "clayDataSetDisplayId", "plid", "portletId"
			},
			true);

		_finderPathCountByU_CDSDI_P_P = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByU_CDSDI_P_P",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Long.class.getName(), String.class.getName()
			},
			new String[] {
				"userId", "clayDataSetDisplayId", "plid", "portletId"
			},
			false);
	}

	@Deactivate
	public void deactivate() {
		entityCache.removeCache(FVSActiveEntryImpl.class.getName());
	}

	@Override
	@Reference(
		target = FVSPersistenceConstants.SERVICE_CONFIGURATION_FILTER,
		unbind = "-"
	)
	public void setConfiguration(Configuration configuration) {
	}

	@Override
	@Reference(
		target = FVSPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setDataSource(DataSource dataSource) {
		super.setDataSource(dataSource);
	}

	@Override
	@Reference(
		target = FVSPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setSessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	@Reference
	protected EntityCache entityCache;

	@Reference
	protected FinderCache finderCache;

	private static final String _SQL_SELECT_FVSACTIVEENTRY =
		"SELECT fvsActiveEntry FROM FVSActiveEntry fvsActiveEntry";

	private static final String _SQL_SELECT_FVSACTIVEENTRY_WHERE =
		"SELECT fvsActiveEntry FROM FVSActiveEntry fvsActiveEntry WHERE ";

	private static final String _SQL_COUNT_FVSACTIVEENTRY =
		"SELECT COUNT(fvsActiveEntry) FROM FVSActiveEntry fvsActiveEntry";

	private static final String _SQL_COUNT_FVSACTIVEENTRY_WHERE =
		"SELECT COUNT(fvsActiveEntry) FROM FVSActiveEntry fvsActiveEntry WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS = "fvsActiveEntry.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No FVSActiveEntry exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No FVSActiveEntry exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		FVSActiveEntryPersistenceImpl.class);

	private static final Set<String> _badColumnNames = SetUtil.fromArray(
		new String[] {"uuid"});

	@Override
	protected FinderCache getFinderCache() {
		return finderCache;
	}

	@Reference
	private FVSActiveEntryModelArgumentsResolver
		_fvsActiveEntryModelArgumentsResolver;

}