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

import com.liferay.frontend.view.state.exception.NoSuchCustomEntryException;
import com.liferay.frontend.view.state.model.FVSCustomEntry;
import com.liferay.frontend.view.state.model.FVSCustomEntryTable;
import com.liferay.frontend.view.state.model.impl.FVSCustomEntryImpl;
import com.liferay.frontend.view.state.model.impl.FVSCustomEntryModelImpl;
import com.liferay.frontend.view.state.service.persistence.FVSCustomEntryPersistence;
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
 * The persistence implementation for the fvs custom entry service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
@Component(service = {FVSCustomEntryPersistence.class, BasePersistence.class})
public class FVSCustomEntryPersistenceImpl
	extends BasePersistenceImpl<FVSCustomEntry>
	implements FVSCustomEntryPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>FVSCustomEntryUtil</code> to access the fvs custom entry persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		FVSCustomEntryImpl.class.getName();

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
	 * Returns all the fvs custom entries where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching fvs custom entries
	 */
	@Override
	public List<FVSCustomEntry> findByUuid(String uuid) {
		return findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the fvs custom entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FVSCustomEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of fvs custom entries
	 * @param end the upper bound of the range of fvs custom entries (not inclusive)
	 * @return the range of matching fvs custom entries
	 */
	@Override
	public List<FVSCustomEntry> findByUuid(String uuid, int start, int end) {
		return findByUuid(uuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the fvs custom entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FVSCustomEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of fvs custom entries
	 * @param end the upper bound of the range of fvs custom entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching fvs custom entries
	 */
	@Override
	public List<FVSCustomEntry> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<FVSCustomEntry> orderByComparator) {

		return findByUuid(uuid, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the fvs custom entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FVSCustomEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of fvs custom entries
	 * @param end the upper bound of the range of fvs custom entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching fvs custom entries
	 */
	@Override
	public List<FVSCustomEntry> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<FVSCustomEntry> orderByComparator,
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

		List<FVSCustomEntry> list = null;

		if (useFinderCache) {
			list = (List<FVSCustomEntry>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (FVSCustomEntry fvsCustomEntry : list) {
					if (!uuid.equals(fvsCustomEntry.getUuid())) {
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

			sb.append(_SQL_SELECT_FVSCUSTOMENTRY_WHERE);

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
				sb.append(FVSCustomEntryModelImpl.ORDER_BY_JPQL);
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

				list = (List<FVSCustomEntry>)QueryUtil.list(
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
	 * Returns the first fvs custom entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fvs custom entry
	 * @throws NoSuchCustomEntryException if a matching fvs custom entry could not be found
	 */
	@Override
	public FVSCustomEntry findByUuid_First(
			String uuid, OrderByComparator<FVSCustomEntry> orderByComparator)
		throws NoSuchCustomEntryException {

		FVSCustomEntry fvsCustomEntry = fetchByUuid_First(
			uuid, orderByComparator);

		if (fvsCustomEntry != null) {
			return fvsCustomEntry;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append("}");

		throw new NoSuchCustomEntryException(sb.toString());
	}

	/**
	 * Returns the first fvs custom entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fvs custom entry, or <code>null</code> if a matching fvs custom entry could not be found
	 */
	@Override
	public FVSCustomEntry fetchByUuid_First(
		String uuid, OrderByComparator<FVSCustomEntry> orderByComparator) {

		List<FVSCustomEntry> list = findByUuid(uuid, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last fvs custom entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching fvs custom entry
	 * @throws NoSuchCustomEntryException if a matching fvs custom entry could not be found
	 */
	@Override
	public FVSCustomEntry findByUuid_Last(
			String uuid, OrderByComparator<FVSCustomEntry> orderByComparator)
		throws NoSuchCustomEntryException {

		FVSCustomEntry fvsCustomEntry = fetchByUuid_Last(
			uuid, orderByComparator);

		if (fvsCustomEntry != null) {
			return fvsCustomEntry;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append("}");

		throw new NoSuchCustomEntryException(sb.toString());
	}

	/**
	 * Returns the last fvs custom entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching fvs custom entry, or <code>null</code> if a matching fvs custom entry could not be found
	 */
	@Override
	public FVSCustomEntry fetchByUuid_Last(
		String uuid, OrderByComparator<FVSCustomEntry> orderByComparator) {

		int count = countByUuid(uuid);

		if (count == 0) {
			return null;
		}

		List<FVSCustomEntry> list = findByUuid(
			uuid, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the fvs custom entries before and after the current fvs custom entry in the ordered set where uuid = &#63;.
	 *
	 * @param fvsCustomEntryId the primary key of the current fvs custom entry
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next fvs custom entry
	 * @throws NoSuchCustomEntryException if a fvs custom entry with the primary key could not be found
	 */
	@Override
	public FVSCustomEntry[] findByUuid_PrevAndNext(
			long fvsCustomEntryId, String uuid,
			OrderByComparator<FVSCustomEntry> orderByComparator)
		throws NoSuchCustomEntryException {

		uuid = Objects.toString(uuid, "");

		FVSCustomEntry fvsCustomEntry = findByPrimaryKey(fvsCustomEntryId);

		Session session = null;

		try {
			session = openSession();

			FVSCustomEntry[] array = new FVSCustomEntryImpl[3];

			array[0] = getByUuid_PrevAndNext(
				session, fvsCustomEntry, uuid, orderByComparator, true);

			array[1] = fvsCustomEntry;

			array[2] = getByUuid_PrevAndNext(
				session, fvsCustomEntry, uuid, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected FVSCustomEntry getByUuid_PrevAndNext(
		Session session, FVSCustomEntry fvsCustomEntry, String uuid,
		OrderByComparator<FVSCustomEntry> orderByComparator, boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(3);
		}

		sb.append(_SQL_SELECT_FVSCUSTOMENTRY_WHERE);

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
			sb.append(FVSCustomEntryModelImpl.ORDER_BY_JPQL);
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
						fvsCustomEntry)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<FVSCustomEntry> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the fvs custom entries where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	@Override
	public void removeByUuid(String uuid) {
		for (FVSCustomEntry fvsCustomEntry :
				findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(fvsCustomEntry);
		}
	}

	/**
	 * Returns the number of fvs custom entries where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching fvs custom entries
	 */
	@Override
	public int countByUuid(String uuid) {
		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = _finderPathCountByUuid;

		Object[] finderArgs = new Object[] {uuid};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_FVSCUSTOMENTRY_WHERE);

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
		"fvsCustomEntry.uuid = ?";

	private static final String _FINDER_COLUMN_UUID_UUID_3 =
		"(fvsCustomEntry.uuid IS NULL OR fvsCustomEntry.uuid = '')";

	private FinderPath _finderPathWithPaginationFindByUuid_C;
	private FinderPath _finderPathWithoutPaginationFindByUuid_C;
	private FinderPath _finderPathCountByUuid_C;

	/**
	 * Returns all the fvs custom entries where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching fvs custom entries
	 */
	@Override
	public List<FVSCustomEntry> findByUuid_C(String uuid, long companyId) {
		return findByUuid_C(
			uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the fvs custom entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FVSCustomEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of fvs custom entries
	 * @param end the upper bound of the range of fvs custom entries (not inclusive)
	 * @return the range of matching fvs custom entries
	 */
	@Override
	public List<FVSCustomEntry> findByUuid_C(
		String uuid, long companyId, int start, int end) {

		return findByUuid_C(uuid, companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the fvs custom entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FVSCustomEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of fvs custom entries
	 * @param end the upper bound of the range of fvs custom entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching fvs custom entries
	 */
	@Override
	public List<FVSCustomEntry> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<FVSCustomEntry> orderByComparator) {

		return findByUuid_C(
			uuid, companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the fvs custom entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FVSCustomEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of fvs custom entries
	 * @param end the upper bound of the range of fvs custom entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching fvs custom entries
	 */
	@Override
	public List<FVSCustomEntry> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<FVSCustomEntry> orderByComparator,
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

		List<FVSCustomEntry> list = null;

		if (useFinderCache) {
			list = (List<FVSCustomEntry>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (FVSCustomEntry fvsCustomEntry : list) {
					if (!uuid.equals(fvsCustomEntry.getUuid()) ||
						(companyId != fvsCustomEntry.getCompanyId())) {

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

			sb.append(_SQL_SELECT_FVSCUSTOMENTRY_WHERE);

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
				sb.append(FVSCustomEntryModelImpl.ORDER_BY_JPQL);
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

				list = (List<FVSCustomEntry>)QueryUtil.list(
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
	 * Returns the first fvs custom entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fvs custom entry
	 * @throws NoSuchCustomEntryException if a matching fvs custom entry could not be found
	 */
	@Override
	public FVSCustomEntry findByUuid_C_First(
			String uuid, long companyId,
			OrderByComparator<FVSCustomEntry> orderByComparator)
		throws NoSuchCustomEntryException {

		FVSCustomEntry fvsCustomEntry = fetchByUuid_C_First(
			uuid, companyId, orderByComparator);

		if (fvsCustomEntry != null) {
			return fvsCustomEntry;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append(", companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchCustomEntryException(sb.toString());
	}

	/**
	 * Returns the first fvs custom entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fvs custom entry, or <code>null</code> if a matching fvs custom entry could not be found
	 */
	@Override
	public FVSCustomEntry fetchByUuid_C_First(
		String uuid, long companyId,
		OrderByComparator<FVSCustomEntry> orderByComparator) {

		List<FVSCustomEntry> list = findByUuid_C(
			uuid, companyId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last fvs custom entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching fvs custom entry
	 * @throws NoSuchCustomEntryException if a matching fvs custom entry could not be found
	 */
	@Override
	public FVSCustomEntry findByUuid_C_Last(
			String uuid, long companyId,
			OrderByComparator<FVSCustomEntry> orderByComparator)
		throws NoSuchCustomEntryException {

		FVSCustomEntry fvsCustomEntry = fetchByUuid_C_Last(
			uuid, companyId, orderByComparator);

		if (fvsCustomEntry != null) {
			return fvsCustomEntry;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append(", companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchCustomEntryException(sb.toString());
	}

	/**
	 * Returns the last fvs custom entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching fvs custom entry, or <code>null</code> if a matching fvs custom entry could not be found
	 */
	@Override
	public FVSCustomEntry fetchByUuid_C_Last(
		String uuid, long companyId,
		OrderByComparator<FVSCustomEntry> orderByComparator) {

		int count = countByUuid_C(uuid, companyId);

		if (count == 0) {
			return null;
		}

		List<FVSCustomEntry> list = findByUuid_C(
			uuid, companyId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the fvs custom entries before and after the current fvs custom entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param fvsCustomEntryId the primary key of the current fvs custom entry
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next fvs custom entry
	 * @throws NoSuchCustomEntryException if a fvs custom entry with the primary key could not be found
	 */
	@Override
	public FVSCustomEntry[] findByUuid_C_PrevAndNext(
			long fvsCustomEntryId, String uuid, long companyId,
			OrderByComparator<FVSCustomEntry> orderByComparator)
		throws NoSuchCustomEntryException {

		uuid = Objects.toString(uuid, "");

		FVSCustomEntry fvsCustomEntry = findByPrimaryKey(fvsCustomEntryId);

		Session session = null;

		try {
			session = openSession();

			FVSCustomEntry[] array = new FVSCustomEntryImpl[3];

			array[0] = getByUuid_C_PrevAndNext(
				session, fvsCustomEntry, uuid, companyId, orderByComparator,
				true);

			array[1] = fvsCustomEntry;

			array[2] = getByUuid_C_PrevAndNext(
				session, fvsCustomEntry, uuid, companyId, orderByComparator,
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

	protected FVSCustomEntry getByUuid_C_PrevAndNext(
		Session session, FVSCustomEntry fvsCustomEntry, String uuid,
		long companyId, OrderByComparator<FVSCustomEntry> orderByComparator,
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

		sb.append(_SQL_SELECT_FVSCUSTOMENTRY_WHERE);

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
			sb.append(FVSCustomEntryModelImpl.ORDER_BY_JPQL);
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
						fvsCustomEntry)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<FVSCustomEntry> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the fvs custom entries where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	@Override
	public void removeByUuid_C(String uuid, long companyId) {
		for (FVSCustomEntry fvsCustomEntry :
				findByUuid_C(
					uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(fvsCustomEntry);
		}
	}

	/**
	 * Returns the number of fvs custom entries where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching fvs custom entries
	 */
	@Override
	public int countByUuid_C(String uuid, long companyId) {
		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = _finderPathCountByUuid_C;

		Object[] finderArgs = new Object[] {uuid, companyId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_FVSCUSTOMENTRY_WHERE);

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
		"fvsCustomEntry.uuid = ? AND ";

	private static final String _FINDER_COLUMN_UUID_C_UUID_3 =
		"(fvsCustomEntry.uuid IS NULL OR fvsCustomEntry.uuid = '') AND ";

	private static final String _FINDER_COLUMN_UUID_C_COMPANYID_2 =
		"fvsCustomEntry.companyId = ?";

	public FVSCustomEntryPersistenceImpl() {
		Map<String, String> dbColumnNames = new HashMap<String, String>();

		dbColumnNames.put("uuid", "uuid_");

		setDBColumnNames(dbColumnNames);

		setModelClass(FVSCustomEntry.class);

		setModelImplClass(FVSCustomEntryImpl.class);
		setModelPKClass(long.class);

		setTable(FVSCustomEntryTable.INSTANCE);
	}

	/**
	 * Caches the fvs custom entry in the entity cache if it is enabled.
	 *
	 * @param fvsCustomEntry the fvs custom entry
	 */
	@Override
	public void cacheResult(FVSCustomEntry fvsCustomEntry) {
		entityCache.putResult(
			FVSCustomEntryImpl.class, fvsCustomEntry.getPrimaryKey(),
			fvsCustomEntry);
	}

	private int _valueObjectFinderCacheListThreshold;

	/**
	 * Caches the fvs custom entries in the entity cache if it is enabled.
	 *
	 * @param fvsCustomEntries the fvs custom entries
	 */
	@Override
	public void cacheResult(List<FVSCustomEntry> fvsCustomEntries) {
		if ((_valueObjectFinderCacheListThreshold == 0) ||
			((_valueObjectFinderCacheListThreshold > 0) &&
			 (fvsCustomEntries.size() >
				 _valueObjectFinderCacheListThreshold))) {

			return;
		}

		for (FVSCustomEntry fvsCustomEntry : fvsCustomEntries) {
			if (entityCache.getResult(
					FVSCustomEntryImpl.class, fvsCustomEntry.getPrimaryKey()) ==
						null) {

				cacheResult(fvsCustomEntry);
			}
		}
	}

	/**
	 * Clears the cache for all fvs custom entries.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(FVSCustomEntryImpl.class);

		finderCache.clearCache(FVSCustomEntryImpl.class);
	}

	/**
	 * Clears the cache for the fvs custom entry.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(FVSCustomEntry fvsCustomEntry) {
		entityCache.removeResult(FVSCustomEntryImpl.class, fvsCustomEntry);
	}

	@Override
	public void clearCache(List<FVSCustomEntry> fvsCustomEntries) {
		for (FVSCustomEntry fvsCustomEntry : fvsCustomEntries) {
			entityCache.removeResult(FVSCustomEntryImpl.class, fvsCustomEntry);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(FVSCustomEntryImpl.class);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(FVSCustomEntryImpl.class, primaryKey);
		}
	}

	/**
	 * Creates a new fvs custom entry with the primary key. Does not add the fvs custom entry to the database.
	 *
	 * @param fvsCustomEntryId the primary key for the new fvs custom entry
	 * @return the new fvs custom entry
	 */
	@Override
	public FVSCustomEntry create(long fvsCustomEntryId) {
		FVSCustomEntry fvsCustomEntry = new FVSCustomEntryImpl();

		fvsCustomEntry.setNew(true);
		fvsCustomEntry.setPrimaryKey(fvsCustomEntryId);

		String uuid = PortalUUIDUtil.generate();

		fvsCustomEntry.setUuid(uuid);

		fvsCustomEntry.setCompanyId(CompanyThreadLocal.getCompanyId());

		return fvsCustomEntry;
	}

	/**
	 * Removes the fvs custom entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param fvsCustomEntryId the primary key of the fvs custom entry
	 * @return the fvs custom entry that was removed
	 * @throws NoSuchCustomEntryException if a fvs custom entry with the primary key could not be found
	 */
	@Override
	public FVSCustomEntry remove(long fvsCustomEntryId)
		throws NoSuchCustomEntryException {

		return remove((Serializable)fvsCustomEntryId);
	}

	/**
	 * Removes the fvs custom entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the fvs custom entry
	 * @return the fvs custom entry that was removed
	 * @throws NoSuchCustomEntryException if a fvs custom entry with the primary key could not be found
	 */
	@Override
	public FVSCustomEntry remove(Serializable primaryKey)
		throws NoSuchCustomEntryException {

		Session session = null;

		try {
			session = openSession();

			FVSCustomEntry fvsCustomEntry = (FVSCustomEntry)session.get(
				FVSCustomEntryImpl.class, primaryKey);

			if (fvsCustomEntry == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchCustomEntryException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(fvsCustomEntry);
		}
		catch (NoSuchCustomEntryException noSuchEntityException) {
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
	protected FVSCustomEntry removeImpl(FVSCustomEntry fvsCustomEntry) {
		Session session = null;

		try {
			session = openSession();

			if (!session.contains(fvsCustomEntry)) {
				fvsCustomEntry = (FVSCustomEntry)session.get(
					FVSCustomEntryImpl.class,
					fvsCustomEntry.getPrimaryKeyObj());
			}

			if (fvsCustomEntry != null) {
				session.delete(fvsCustomEntry);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (fvsCustomEntry != null) {
			clearCache(fvsCustomEntry);
		}

		return fvsCustomEntry;
	}

	@Override
	public FVSCustomEntry updateImpl(FVSCustomEntry fvsCustomEntry) {
		boolean isNew = fvsCustomEntry.isNew();

		if (!(fvsCustomEntry instanceof FVSCustomEntryModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(fvsCustomEntry.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					fvsCustomEntry);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in fvsCustomEntry proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom FVSCustomEntry implementation " +
					fvsCustomEntry.getClass());
		}

		FVSCustomEntryModelImpl fvsCustomEntryModelImpl =
			(FVSCustomEntryModelImpl)fvsCustomEntry;

		if (Validator.isNull(fvsCustomEntry.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			fvsCustomEntry.setUuid(uuid);
		}

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Date date = new Date();

		if (isNew && (fvsCustomEntry.getCreateDate() == null)) {
			if (serviceContext == null) {
				fvsCustomEntry.setCreateDate(date);
			}
			else {
				fvsCustomEntry.setCreateDate(
					serviceContext.getCreateDate(date));
			}
		}

		if (!fvsCustomEntryModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				fvsCustomEntry.setModifiedDate(date);
			}
			else {
				fvsCustomEntry.setModifiedDate(
					serviceContext.getModifiedDate(date));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (isNew) {
				session.save(fvsCustomEntry);
			}
			else {
				fvsCustomEntry = (FVSCustomEntry)session.merge(fvsCustomEntry);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		entityCache.putResult(
			FVSCustomEntryImpl.class, fvsCustomEntryModelImpl, false, true);

		if (isNew) {
			fvsCustomEntry.setNew(false);
		}

		fvsCustomEntry.resetOriginalValues();

		return fvsCustomEntry;
	}

	/**
	 * Returns the fvs custom entry with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the fvs custom entry
	 * @return the fvs custom entry
	 * @throws NoSuchCustomEntryException if a fvs custom entry with the primary key could not be found
	 */
	@Override
	public FVSCustomEntry findByPrimaryKey(Serializable primaryKey)
		throws NoSuchCustomEntryException {

		FVSCustomEntry fvsCustomEntry = fetchByPrimaryKey(primaryKey);

		if (fvsCustomEntry == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchCustomEntryException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return fvsCustomEntry;
	}

	/**
	 * Returns the fvs custom entry with the primary key or throws a <code>NoSuchCustomEntryException</code> if it could not be found.
	 *
	 * @param fvsCustomEntryId the primary key of the fvs custom entry
	 * @return the fvs custom entry
	 * @throws NoSuchCustomEntryException if a fvs custom entry with the primary key could not be found
	 */
	@Override
	public FVSCustomEntry findByPrimaryKey(long fvsCustomEntryId)
		throws NoSuchCustomEntryException {

		return findByPrimaryKey((Serializable)fvsCustomEntryId);
	}

	/**
	 * Returns the fvs custom entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param fvsCustomEntryId the primary key of the fvs custom entry
	 * @return the fvs custom entry, or <code>null</code> if a fvs custom entry with the primary key could not be found
	 */
	@Override
	public FVSCustomEntry fetchByPrimaryKey(long fvsCustomEntryId) {
		return fetchByPrimaryKey((Serializable)fvsCustomEntryId);
	}

	/**
	 * Returns all the fvs custom entries.
	 *
	 * @return the fvs custom entries
	 */
	@Override
	public List<FVSCustomEntry> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the fvs custom entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FVSCustomEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of fvs custom entries
	 * @param end the upper bound of the range of fvs custom entries (not inclusive)
	 * @return the range of fvs custom entries
	 */
	@Override
	public List<FVSCustomEntry> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the fvs custom entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FVSCustomEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of fvs custom entries
	 * @param end the upper bound of the range of fvs custom entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of fvs custom entries
	 */
	@Override
	public List<FVSCustomEntry> findAll(
		int start, int end,
		OrderByComparator<FVSCustomEntry> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the fvs custom entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FVSCustomEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of fvs custom entries
	 * @param end the upper bound of the range of fvs custom entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of fvs custom entries
	 */
	@Override
	public List<FVSCustomEntry> findAll(
		int start, int end, OrderByComparator<FVSCustomEntry> orderByComparator,
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

		List<FVSCustomEntry> list = null;

		if (useFinderCache) {
			list = (List<FVSCustomEntry>)finderCache.getResult(
				finderPath, finderArgs);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_FVSCUSTOMENTRY);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_FVSCUSTOMENTRY;

				sql = sql.concat(FVSCustomEntryModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<FVSCustomEntry>)QueryUtil.list(
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
	 * Removes all the fvs custom entries from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (FVSCustomEntry fvsCustomEntry : findAll()) {
			remove(fvsCustomEntry);
		}
	}

	/**
	 * Returns the number of fvs custom entries.
	 *
	 * @return the number of fvs custom entries
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(
			_finderPathCountAll, FINDER_ARGS_EMPTY);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(_SQL_COUNT_FVSCUSTOMENTRY);

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
		return "fvsCustomEntryId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_FVSCUSTOMENTRY;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return FVSCustomEntryModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the fvs custom entry persistence.
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
	}

	@Deactivate
	public void deactivate() {
		entityCache.removeCache(FVSCustomEntryImpl.class.getName());
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

	private static final String _SQL_SELECT_FVSCUSTOMENTRY =
		"SELECT fvsCustomEntry FROM FVSCustomEntry fvsCustomEntry";

	private static final String _SQL_SELECT_FVSCUSTOMENTRY_WHERE =
		"SELECT fvsCustomEntry FROM FVSCustomEntry fvsCustomEntry WHERE ";

	private static final String _SQL_COUNT_FVSCUSTOMENTRY =
		"SELECT COUNT(fvsCustomEntry) FROM FVSCustomEntry fvsCustomEntry";

	private static final String _SQL_COUNT_FVSCUSTOMENTRY_WHERE =
		"SELECT COUNT(fvsCustomEntry) FROM FVSCustomEntry fvsCustomEntry WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS = "fvsCustomEntry.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No FVSCustomEntry exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No FVSCustomEntry exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		FVSCustomEntryPersistenceImpl.class);

	private static final Set<String> _badColumnNames = SetUtil.fromArray(
		new String[] {"uuid"});

	@Override
	protected FinderCache getFinderCache() {
		return finderCache;
	}

	@Reference
	private FVSCustomEntryModelArgumentsResolver
		_fvsCustomEntryModelArgumentsResolver;

}