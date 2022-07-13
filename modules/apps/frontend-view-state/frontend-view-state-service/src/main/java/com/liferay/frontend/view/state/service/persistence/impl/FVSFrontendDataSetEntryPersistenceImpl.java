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

import com.liferay.frontend.view.state.exception.NoSuchFrontendDataSetEntryException;
import com.liferay.frontend.view.state.model.FVSFrontendDataSetEntry;
import com.liferay.frontend.view.state.model.FVSFrontendDataSetEntryTable;
import com.liferay.frontend.view.state.model.impl.FVSFrontendDataSetEntryImpl;
import com.liferay.frontend.view.state.model.impl.FVSFrontendDataSetEntryModelImpl;
import com.liferay.frontend.view.state.service.persistence.FVSFrontendDataSetEntryPersistence;
import com.liferay.frontend.view.state.service.persistence.FVSFrontendDataSetEntryUtil;
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
import com.liferay.portal.kernel.uuid.PortalUUID;

import java.io.Serializable;

import java.lang.reflect.Field;
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
 * The persistence implementation for the fvs frontend data set entry service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
@Component(
	service = {FVSFrontendDataSetEntryPersistence.class, BasePersistence.class}
)
public class FVSFrontendDataSetEntryPersistenceImpl
	extends BasePersistenceImpl<FVSFrontendDataSetEntry>
	implements FVSFrontendDataSetEntryPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>FVSFrontendDataSetEntryUtil</code> to access the fvs frontend data set entry persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		FVSFrontendDataSetEntryImpl.class.getName();

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
	 * Returns all the fvs frontend data set entries where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching fvs frontend data set entries
	 */
	@Override
	public List<FVSFrontendDataSetEntry> findByUuid(String uuid) {
		return findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the fvs frontend data set entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FVSFrontendDataSetEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of fvs frontend data set entries
	 * @param end the upper bound of the range of fvs frontend data set entries (not inclusive)
	 * @return the range of matching fvs frontend data set entries
	 */
	@Override
	public List<FVSFrontendDataSetEntry> findByUuid(
		String uuid, int start, int end) {

		return findByUuid(uuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the fvs frontend data set entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FVSFrontendDataSetEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of fvs frontend data set entries
	 * @param end the upper bound of the range of fvs frontend data set entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching fvs frontend data set entries
	 */
	@Override
	public List<FVSFrontendDataSetEntry> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<FVSFrontendDataSetEntry> orderByComparator) {

		return findByUuid(uuid, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the fvs frontend data set entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FVSFrontendDataSetEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of fvs frontend data set entries
	 * @param end the upper bound of the range of fvs frontend data set entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching fvs frontend data set entries
	 */
	@Override
	public List<FVSFrontendDataSetEntry> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<FVSFrontendDataSetEntry> orderByComparator,
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

		List<FVSFrontendDataSetEntry> list = null;

		if (useFinderCache) {
			list = (List<FVSFrontendDataSetEntry>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (FVSFrontendDataSetEntry fvsFrontendDataSetEntry : list) {
					if (!uuid.equals(fvsFrontendDataSetEntry.getUuid())) {
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

			sb.append(_SQL_SELECT_FVSFRONTENDDATASETENTRY_WHERE);

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
				sb.append(FVSFrontendDataSetEntryModelImpl.ORDER_BY_JPQL);
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

				list = (List<FVSFrontendDataSetEntry>)QueryUtil.list(
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
	 * Returns the first fvs frontend data set entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fvs frontend data set entry
	 * @throws NoSuchFrontendDataSetEntryException if a matching fvs frontend data set entry could not be found
	 */
	@Override
	public FVSFrontendDataSetEntry findByUuid_First(
			String uuid,
			OrderByComparator<FVSFrontendDataSetEntry> orderByComparator)
		throws NoSuchFrontendDataSetEntryException {

		FVSFrontendDataSetEntry fvsFrontendDataSetEntry = fetchByUuid_First(
			uuid, orderByComparator);

		if (fvsFrontendDataSetEntry != null) {
			return fvsFrontendDataSetEntry;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append("}");

		throw new NoSuchFrontendDataSetEntryException(sb.toString());
	}

	/**
	 * Returns the first fvs frontend data set entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fvs frontend data set entry, or <code>null</code> if a matching fvs frontend data set entry could not be found
	 */
	@Override
	public FVSFrontendDataSetEntry fetchByUuid_First(
		String uuid,
		OrderByComparator<FVSFrontendDataSetEntry> orderByComparator) {

		List<FVSFrontendDataSetEntry> list = findByUuid(
			uuid, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last fvs frontend data set entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching fvs frontend data set entry
	 * @throws NoSuchFrontendDataSetEntryException if a matching fvs frontend data set entry could not be found
	 */
	@Override
	public FVSFrontendDataSetEntry findByUuid_Last(
			String uuid,
			OrderByComparator<FVSFrontendDataSetEntry> orderByComparator)
		throws NoSuchFrontendDataSetEntryException {

		FVSFrontendDataSetEntry fvsFrontendDataSetEntry = fetchByUuid_Last(
			uuid, orderByComparator);

		if (fvsFrontendDataSetEntry != null) {
			return fvsFrontendDataSetEntry;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append("}");

		throw new NoSuchFrontendDataSetEntryException(sb.toString());
	}

	/**
	 * Returns the last fvs frontend data set entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching fvs frontend data set entry, or <code>null</code> if a matching fvs frontend data set entry could not be found
	 */
	@Override
	public FVSFrontendDataSetEntry fetchByUuid_Last(
		String uuid,
		OrderByComparator<FVSFrontendDataSetEntry> orderByComparator) {

		int count = countByUuid(uuid);

		if (count == 0) {
			return null;
		}

		List<FVSFrontendDataSetEntry> list = findByUuid(
			uuid, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the fvs frontend data set entries before and after the current fvs frontend data set entry in the ordered set where uuid = &#63;.
	 *
	 * @param fvsFrontendDataSetEntryId the primary key of the current fvs frontend data set entry
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next fvs frontend data set entry
	 * @throws NoSuchFrontendDataSetEntryException if a fvs frontend data set entry with the primary key could not be found
	 */
	@Override
	public FVSFrontendDataSetEntry[] findByUuid_PrevAndNext(
			long fvsFrontendDataSetEntryId, String uuid,
			OrderByComparator<FVSFrontendDataSetEntry> orderByComparator)
		throws NoSuchFrontendDataSetEntryException {

		uuid = Objects.toString(uuid, "");

		FVSFrontendDataSetEntry fvsFrontendDataSetEntry = findByPrimaryKey(
			fvsFrontendDataSetEntryId);

		Session session = null;

		try {
			session = openSession();

			FVSFrontendDataSetEntry[] array =
				new FVSFrontendDataSetEntryImpl[3];

			array[0] = getByUuid_PrevAndNext(
				session, fvsFrontendDataSetEntry, uuid, orderByComparator,
				true);

			array[1] = fvsFrontendDataSetEntry;

			array[2] = getByUuid_PrevAndNext(
				session, fvsFrontendDataSetEntry, uuid, orderByComparator,
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

	protected FVSFrontendDataSetEntry getByUuid_PrevAndNext(
		Session session, FVSFrontendDataSetEntry fvsFrontendDataSetEntry,
		String uuid,
		OrderByComparator<FVSFrontendDataSetEntry> orderByComparator,
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

		sb.append(_SQL_SELECT_FVSFRONTENDDATASETENTRY_WHERE);

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
			sb.append(FVSFrontendDataSetEntryModelImpl.ORDER_BY_JPQL);
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
						fvsFrontendDataSetEntry)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<FVSFrontendDataSetEntry> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the fvs frontend data set entries where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	@Override
	public void removeByUuid(String uuid) {
		for (FVSFrontendDataSetEntry fvsFrontendDataSetEntry :
				findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(fvsFrontendDataSetEntry);
		}
	}

	/**
	 * Returns the number of fvs frontend data set entries where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching fvs frontend data set entries
	 */
	@Override
	public int countByUuid(String uuid) {
		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = _finderPathCountByUuid;

		Object[] finderArgs = new Object[] {uuid};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_FVSFRONTENDDATASETENTRY_WHERE);

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
		"fvsFrontendDataSetEntry.uuid = ?";

	private static final String _FINDER_COLUMN_UUID_UUID_3 =
		"(fvsFrontendDataSetEntry.uuid IS NULL OR fvsFrontendDataSetEntry.uuid = '')";

	private FinderPath _finderPathWithPaginationFindByUuid_C;
	private FinderPath _finderPathWithoutPaginationFindByUuid_C;
	private FinderPath _finderPathCountByUuid_C;

	/**
	 * Returns all the fvs frontend data set entries where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching fvs frontend data set entries
	 */
	@Override
	public List<FVSFrontendDataSetEntry> findByUuid_C(
		String uuid, long companyId) {

		return findByUuid_C(
			uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the fvs frontend data set entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FVSFrontendDataSetEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of fvs frontend data set entries
	 * @param end the upper bound of the range of fvs frontend data set entries (not inclusive)
	 * @return the range of matching fvs frontend data set entries
	 */
	@Override
	public List<FVSFrontendDataSetEntry> findByUuid_C(
		String uuid, long companyId, int start, int end) {

		return findByUuid_C(uuid, companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the fvs frontend data set entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FVSFrontendDataSetEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of fvs frontend data set entries
	 * @param end the upper bound of the range of fvs frontend data set entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching fvs frontend data set entries
	 */
	@Override
	public List<FVSFrontendDataSetEntry> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<FVSFrontendDataSetEntry> orderByComparator) {

		return findByUuid_C(
			uuid, companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the fvs frontend data set entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FVSFrontendDataSetEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of fvs frontend data set entries
	 * @param end the upper bound of the range of fvs frontend data set entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching fvs frontend data set entries
	 */
	@Override
	public List<FVSFrontendDataSetEntry> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<FVSFrontendDataSetEntry> orderByComparator,
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

		List<FVSFrontendDataSetEntry> list = null;

		if (useFinderCache) {
			list = (List<FVSFrontendDataSetEntry>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (FVSFrontendDataSetEntry fvsFrontendDataSetEntry : list) {
					if (!uuid.equals(fvsFrontendDataSetEntry.getUuid()) ||
						(companyId != fvsFrontendDataSetEntry.getCompanyId())) {

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

			sb.append(_SQL_SELECT_FVSFRONTENDDATASETENTRY_WHERE);

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
				sb.append(FVSFrontendDataSetEntryModelImpl.ORDER_BY_JPQL);
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

				list = (List<FVSFrontendDataSetEntry>)QueryUtil.list(
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
	 * Returns the first fvs frontend data set entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fvs frontend data set entry
	 * @throws NoSuchFrontendDataSetEntryException if a matching fvs frontend data set entry could not be found
	 */
	@Override
	public FVSFrontendDataSetEntry findByUuid_C_First(
			String uuid, long companyId,
			OrderByComparator<FVSFrontendDataSetEntry> orderByComparator)
		throws NoSuchFrontendDataSetEntryException {

		FVSFrontendDataSetEntry fvsFrontendDataSetEntry = fetchByUuid_C_First(
			uuid, companyId, orderByComparator);

		if (fvsFrontendDataSetEntry != null) {
			return fvsFrontendDataSetEntry;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append(", companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchFrontendDataSetEntryException(sb.toString());
	}

	/**
	 * Returns the first fvs frontend data set entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fvs frontend data set entry, or <code>null</code> if a matching fvs frontend data set entry could not be found
	 */
	@Override
	public FVSFrontendDataSetEntry fetchByUuid_C_First(
		String uuid, long companyId,
		OrderByComparator<FVSFrontendDataSetEntry> orderByComparator) {

		List<FVSFrontendDataSetEntry> list = findByUuid_C(
			uuid, companyId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last fvs frontend data set entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching fvs frontend data set entry
	 * @throws NoSuchFrontendDataSetEntryException if a matching fvs frontend data set entry could not be found
	 */
	@Override
	public FVSFrontendDataSetEntry findByUuid_C_Last(
			String uuid, long companyId,
			OrderByComparator<FVSFrontendDataSetEntry> orderByComparator)
		throws NoSuchFrontendDataSetEntryException {

		FVSFrontendDataSetEntry fvsFrontendDataSetEntry = fetchByUuid_C_Last(
			uuid, companyId, orderByComparator);

		if (fvsFrontendDataSetEntry != null) {
			return fvsFrontendDataSetEntry;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append(", companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchFrontendDataSetEntryException(sb.toString());
	}

	/**
	 * Returns the last fvs frontend data set entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching fvs frontend data set entry, or <code>null</code> if a matching fvs frontend data set entry could not be found
	 */
	@Override
	public FVSFrontendDataSetEntry fetchByUuid_C_Last(
		String uuid, long companyId,
		OrderByComparator<FVSFrontendDataSetEntry> orderByComparator) {

		int count = countByUuid_C(uuid, companyId);

		if (count == 0) {
			return null;
		}

		List<FVSFrontendDataSetEntry> list = findByUuid_C(
			uuid, companyId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the fvs frontend data set entries before and after the current fvs frontend data set entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param fvsFrontendDataSetEntryId the primary key of the current fvs frontend data set entry
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next fvs frontend data set entry
	 * @throws NoSuchFrontendDataSetEntryException if a fvs frontend data set entry with the primary key could not be found
	 */
	@Override
	public FVSFrontendDataSetEntry[] findByUuid_C_PrevAndNext(
			long fvsFrontendDataSetEntryId, String uuid, long companyId,
			OrderByComparator<FVSFrontendDataSetEntry> orderByComparator)
		throws NoSuchFrontendDataSetEntryException {

		uuid = Objects.toString(uuid, "");

		FVSFrontendDataSetEntry fvsFrontendDataSetEntry = findByPrimaryKey(
			fvsFrontendDataSetEntryId);

		Session session = null;

		try {
			session = openSession();

			FVSFrontendDataSetEntry[] array =
				new FVSFrontendDataSetEntryImpl[3];

			array[0] = getByUuid_C_PrevAndNext(
				session, fvsFrontendDataSetEntry, uuid, companyId,
				orderByComparator, true);

			array[1] = fvsFrontendDataSetEntry;

			array[2] = getByUuid_C_PrevAndNext(
				session, fvsFrontendDataSetEntry, uuid, companyId,
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

	protected FVSFrontendDataSetEntry getByUuid_C_PrevAndNext(
		Session session, FVSFrontendDataSetEntry fvsFrontendDataSetEntry,
		String uuid, long companyId,
		OrderByComparator<FVSFrontendDataSetEntry> orderByComparator,
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

		sb.append(_SQL_SELECT_FVSFRONTENDDATASETENTRY_WHERE);

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
			sb.append(FVSFrontendDataSetEntryModelImpl.ORDER_BY_JPQL);
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
						fvsFrontendDataSetEntry)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<FVSFrontendDataSetEntry> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the fvs frontend data set entries where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	@Override
	public void removeByUuid_C(String uuid, long companyId) {
		for (FVSFrontendDataSetEntry fvsFrontendDataSetEntry :
				findByUuid_C(
					uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(fvsFrontendDataSetEntry);
		}
	}

	/**
	 * Returns the number of fvs frontend data set entries where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching fvs frontend data set entries
	 */
	@Override
	public int countByUuid_C(String uuid, long companyId) {
		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = _finderPathCountByUuid_C;

		Object[] finderArgs = new Object[] {uuid, companyId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_FVSFRONTENDDATASETENTRY_WHERE);

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
		"fvsFrontendDataSetEntry.uuid = ? AND ";

	private static final String _FINDER_COLUMN_UUID_C_UUID_3 =
		"(fvsFrontendDataSetEntry.uuid IS NULL OR fvsFrontendDataSetEntry.uuid = '') AND ";

	private static final String _FINDER_COLUMN_UUID_C_COMPANYID_2 =
		"fvsFrontendDataSetEntry.companyId = ?";

	private FinderPath _finderPathWithPaginationFindByU_F_P_P;
	private FinderPath _finderPathWithoutPaginationFindByU_F_P_P;
	private FinderPath _finderPathCountByU_F_P_P;

	/**
	 * Returns all the fvs frontend data set entries where userId = &#63; and fdsName = &#63; and plid = &#63; and portletId = &#63;.
	 *
	 * @param userId the user ID
	 * @param fdsName the fds name
	 * @param plid the plid
	 * @param portletId the portlet ID
	 * @return the matching fvs frontend data set entries
	 */
	@Override
	public List<FVSFrontendDataSetEntry> findByU_F_P_P(
		long userId, String fdsName, long plid, String portletId) {

		return findByU_F_P_P(
			userId, fdsName, plid, portletId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the fvs frontend data set entries where userId = &#63; and fdsName = &#63; and plid = &#63; and portletId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FVSFrontendDataSetEntryModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param fdsName the fds name
	 * @param plid the plid
	 * @param portletId the portlet ID
	 * @param start the lower bound of the range of fvs frontend data set entries
	 * @param end the upper bound of the range of fvs frontend data set entries (not inclusive)
	 * @return the range of matching fvs frontend data set entries
	 */
	@Override
	public List<FVSFrontendDataSetEntry> findByU_F_P_P(
		long userId, String fdsName, long plid, String portletId, int start,
		int end) {

		return findByU_F_P_P(
			userId, fdsName, plid, portletId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the fvs frontend data set entries where userId = &#63; and fdsName = &#63; and plid = &#63; and portletId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FVSFrontendDataSetEntryModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param fdsName the fds name
	 * @param plid the plid
	 * @param portletId the portlet ID
	 * @param start the lower bound of the range of fvs frontend data set entries
	 * @param end the upper bound of the range of fvs frontend data set entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching fvs frontend data set entries
	 */
	@Override
	public List<FVSFrontendDataSetEntry> findByU_F_P_P(
		long userId, String fdsName, long plid, String portletId, int start,
		int end, OrderByComparator<FVSFrontendDataSetEntry> orderByComparator) {

		return findByU_F_P_P(
			userId, fdsName, plid, portletId, start, end, orderByComparator,
			true);
	}

	/**
	 * Returns an ordered range of all the fvs frontend data set entries where userId = &#63; and fdsName = &#63; and plid = &#63; and portletId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FVSFrontendDataSetEntryModelImpl</code>.
	 * </p>
	 *
	 * @param userId the user ID
	 * @param fdsName the fds name
	 * @param plid the plid
	 * @param portletId the portlet ID
	 * @param start the lower bound of the range of fvs frontend data set entries
	 * @param end the upper bound of the range of fvs frontend data set entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching fvs frontend data set entries
	 */
	@Override
	public List<FVSFrontendDataSetEntry> findByU_F_P_P(
		long userId, String fdsName, long plid, String portletId, int start,
		int end, OrderByComparator<FVSFrontendDataSetEntry> orderByComparator,
		boolean useFinderCache) {

		fdsName = Objects.toString(fdsName, "");
		portletId = Objects.toString(portletId, "");

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByU_F_P_P;
				finderArgs = new Object[] {userId, fdsName, plid, portletId};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByU_F_P_P;
			finderArgs = new Object[] {
				userId, fdsName, plid, portletId, start, end, orderByComparator
			};
		}

		List<FVSFrontendDataSetEntry> list = null;

		if (useFinderCache) {
			list = (List<FVSFrontendDataSetEntry>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (FVSFrontendDataSetEntry fvsFrontendDataSetEntry : list) {
					if ((userId != fvsFrontendDataSetEntry.getUserId()) ||
						!fdsName.equals(fvsFrontendDataSetEntry.getFdsName()) ||
						(plid != fvsFrontendDataSetEntry.getPlid()) ||
						!portletId.equals(
							fvsFrontendDataSetEntry.getPortletId())) {

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

			sb.append(_SQL_SELECT_FVSFRONTENDDATASETENTRY_WHERE);

			sb.append(_FINDER_COLUMN_U_F_P_P_USERID_2);

			boolean bindFdsName = false;

			if (fdsName.isEmpty()) {
				sb.append(_FINDER_COLUMN_U_F_P_P_FDSNAME_3);
			}
			else {
				bindFdsName = true;

				sb.append(_FINDER_COLUMN_U_F_P_P_FDSNAME_2);
			}

			sb.append(_FINDER_COLUMN_U_F_P_P_PLID_2);

			boolean bindPortletId = false;

			if (portletId.isEmpty()) {
				sb.append(_FINDER_COLUMN_U_F_P_P_PORTLETID_3);
			}
			else {
				bindPortletId = true;

				sb.append(_FINDER_COLUMN_U_F_P_P_PORTLETID_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(FVSFrontendDataSetEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(userId);

				if (bindFdsName) {
					queryPos.add(fdsName);
				}

				queryPos.add(plid);

				if (bindPortletId) {
					queryPos.add(portletId);
				}

				list = (List<FVSFrontendDataSetEntry>)QueryUtil.list(
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
	 * Returns the first fvs frontend data set entry in the ordered set where userId = &#63; and fdsName = &#63; and plid = &#63; and portletId = &#63;.
	 *
	 * @param userId the user ID
	 * @param fdsName the fds name
	 * @param plid the plid
	 * @param portletId the portlet ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fvs frontend data set entry
	 * @throws NoSuchFrontendDataSetEntryException if a matching fvs frontend data set entry could not be found
	 */
	@Override
	public FVSFrontendDataSetEntry findByU_F_P_P_First(
			long userId, String fdsName, long plid, String portletId,
			OrderByComparator<FVSFrontendDataSetEntry> orderByComparator)
		throws NoSuchFrontendDataSetEntryException {

		FVSFrontendDataSetEntry fvsFrontendDataSetEntry = fetchByU_F_P_P_First(
			userId, fdsName, plid, portletId, orderByComparator);

		if (fvsFrontendDataSetEntry != null) {
			return fvsFrontendDataSetEntry;
		}

		StringBundler sb = new StringBundler(10);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("userId=");
		sb.append(userId);

		sb.append(", fdsName=");
		sb.append(fdsName);

		sb.append(", plid=");
		sb.append(plid);

		sb.append(", portletId=");
		sb.append(portletId);

		sb.append("}");

		throw new NoSuchFrontendDataSetEntryException(sb.toString());
	}

	/**
	 * Returns the first fvs frontend data set entry in the ordered set where userId = &#63; and fdsName = &#63; and plid = &#63; and portletId = &#63;.
	 *
	 * @param userId the user ID
	 * @param fdsName the fds name
	 * @param plid the plid
	 * @param portletId the portlet ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fvs frontend data set entry, or <code>null</code> if a matching fvs frontend data set entry could not be found
	 */
	@Override
	public FVSFrontendDataSetEntry fetchByU_F_P_P_First(
		long userId, String fdsName, long plid, String portletId,
		OrderByComparator<FVSFrontendDataSetEntry> orderByComparator) {

		List<FVSFrontendDataSetEntry> list = findByU_F_P_P(
			userId, fdsName, plid, portletId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last fvs frontend data set entry in the ordered set where userId = &#63; and fdsName = &#63; and plid = &#63; and portletId = &#63;.
	 *
	 * @param userId the user ID
	 * @param fdsName the fds name
	 * @param plid the plid
	 * @param portletId the portlet ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching fvs frontend data set entry
	 * @throws NoSuchFrontendDataSetEntryException if a matching fvs frontend data set entry could not be found
	 */
	@Override
	public FVSFrontendDataSetEntry findByU_F_P_P_Last(
			long userId, String fdsName, long plid, String portletId,
			OrderByComparator<FVSFrontendDataSetEntry> orderByComparator)
		throws NoSuchFrontendDataSetEntryException {

		FVSFrontendDataSetEntry fvsFrontendDataSetEntry = fetchByU_F_P_P_Last(
			userId, fdsName, plid, portletId, orderByComparator);

		if (fvsFrontendDataSetEntry != null) {
			return fvsFrontendDataSetEntry;
		}

		StringBundler sb = new StringBundler(10);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("userId=");
		sb.append(userId);

		sb.append(", fdsName=");
		sb.append(fdsName);

		sb.append(", plid=");
		sb.append(plid);

		sb.append(", portletId=");
		sb.append(portletId);

		sb.append("}");

		throw new NoSuchFrontendDataSetEntryException(sb.toString());
	}

	/**
	 * Returns the last fvs frontend data set entry in the ordered set where userId = &#63; and fdsName = &#63; and plid = &#63; and portletId = &#63;.
	 *
	 * @param userId the user ID
	 * @param fdsName the fds name
	 * @param plid the plid
	 * @param portletId the portlet ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching fvs frontend data set entry, or <code>null</code> if a matching fvs frontend data set entry could not be found
	 */
	@Override
	public FVSFrontendDataSetEntry fetchByU_F_P_P_Last(
		long userId, String fdsName, long plid, String portletId,
		OrderByComparator<FVSFrontendDataSetEntry> orderByComparator) {

		int count = countByU_F_P_P(userId, fdsName, plid, portletId);

		if (count == 0) {
			return null;
		}

		List<FVSFrontendDataSetEntry> list = findByU_F_P_P(
			userId, fdsName, plid, portletId, count - 1, count,
			orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the fvs frontend data set entries before and after the current fvs frontend data set entry in the ordered set where userId = &#63; and fdsName = &#63; and plid = &#63; and portletId = &#63;.
	 *
	 * @param fvsFrontendDataSetEntryId the primary key of the current fvs frontend data set entry
	 * @param userId the user ID
	 * @param fdsName the fds name
	 * @param plid the plid
	 * @param portletId the portlet ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next fvs frontend data set entry
	 * @throws NoSuchFrontendDataSetEntryException if a fvs frontend data set entry with the primary key could not be found
	 */
	@Override
	public FVSFrontendDataSetEntry[] findByU_F_P_P_PrevAndNext(
			long fvsFrontendDataSetEntryId, long userId, String fdsName,
			long plid, String portletId,
			OrderByComparator<FVSFrontendDataSetEntry> orderByComparator)
		throws NoSuchFrontendDataSetEntryException {

		fdsName = Objects.toString(fdsName, "");
		portletId = Objects.toString(portletId, "");

		FVSFrontendDataSetEntry fvsFrontendDataSetEntry = findByPrimaryKey(
			fvsFrontendDataSetEntryId);

		Session session = null;

		try {
			session = openSession();

			FVSFrontendDataSetEntry[] array =
				new FVSFrontendDataSetEntryImpl[3];

			array[0] = getByU_F_P_P_PrevAndNext(
				session, fvsFrontendDataSetEntry, userId, fdsName, plid,
				portletId, orderByComparator, true);

			array[1] = fvsFrontendDataSetEntry;

			array[2] = getByU_F_P_P_PrevAndNext(
				session, fvsFrontendDataSetEntry, userId, fdsName, plid,
				portletId, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected FVSFrontendDataSetEntry getByU_F_P_P_PrevAndNext(
		Session session, FVSFrontendDataSetEntry fvsFrontendDataSetEntry,
		long userId, String fdsName, long plid, String portletId,
		OrderByComparator<FVSFrontendDataSetEntry> orderByComparator,
		boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				7 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(6);
		}

		sb.append(_SQL_SELECT_FVSFRONTENDDATASETENTRY_WHERE);

		sb.append(_FINDER_COLUMN_U_F_P_P_USERID_2);

		boolean bindFdsName = false;

		if (fdsName.isEmpty()) {
			sb.append(_FINDER_COLUMN_U_F_P_P_FDSNAME_3);
		}
		else {
			bindFdsName = true;

			sb.append(_FINDER_COLUMN_U_F_P_P_FDSNAME_2);
		}

		sb.append(_FINDER_COLUMN_U_F_P_P_PLID_2);

		boolean bindPortletId = false;

		if (portletId.isEmpty()) {
			sb.append(_FINDER_COLUMN_U_F_P_P_PORTLETID_3);
		}
		else {
			bindPortletId = true;

			sb.append(_FINDER_COLUMN_U_F_P_P_PORTLETID_2);
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
			sb.append(FVSFrontendDataSetEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(userId);

		if (bindFdsName) {
			queryPos.add(fdsName);
		}

		queryPos.add(plid);

		if (bindPortletId) {
			queryPos.add(portletId);
		}

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						fvsFrontendDataSetEntry)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<FVSFrontendDataSetEntry> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the fvs frontend data set entries where userId = &#63; and fdsName = &#63; and plid = &#63; and portletId = &#63; from the database.
	 *
	 * @param userId the user ID
	 * @param fdsName the fds name
	 * @param plid the plid
	 * @param portletId the portlet ID
	 */
	@Override
	public void removeByU_F_P_P(
		long userId, String fdsName, long plid, String portletId) {

		for (FVSFrontendDataSetEntry fvsFrontendDataSetEntry :
				findByU_F_P_P(
					userId, fdsName, plid, portletId, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			remove(fvsFrontendDataSetEntry);
		}
	}

	/**
	 * Returns the number of fvs frontend data set entries where userId = &#63; and fdsName = &#63; and plid = &#63; and portletId = &#63;.
	 *
	 * @param userId the user ID
	 * @param fdsName the fds name
	 * @param plid the plid
	 * @param portletId the portlet ID
	 * @return the number of matching fvs frontend data set entries
	 */
	@Override
	public int countByU_F_P_P(
		long userId, String fdsName, long plid, String portletId) {

		fdsName = Objects.toString(fdsName, "");
		portletId = Objects.toString(portletId, "");

		FinderPath finderPath = _finderPathCountByU_F_P_P;

		Object[] finderArgs = new Object[] {userId, fdsName, plid, portletId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(5);

			sb.append(_SQL_COUNT_FVSFRONTENDDATASETENTRY_WHERE);

			sb.append(_FINDER_COLUMN_U_F_P_P_USERID_2);

			boolean bindFdsName = false;

			if (fdsName.isEmpty()) {
				sb.append(_FINDER_COLUMN_U_F_P_P_FDSNAME_3);
			}
			else {
				bindFdsName = true;

				sb.append(_FINDER_COLUMN_U_F_P_P_FDSNAME_2);
			}

			sb.append(_FINDER_COLUMN_U_F_P_P_PLID_2);

			boolean bindPortletId = false;

			if (portletId.isEmpty()) {
				sb.append(_FINDER_COLUMN_U_F_P_P_PORTLETID_3);
			}
			else {
				bindPortletId = true;

				sb.append(_FINDER_COLUMN_U_F_P_P_PORTLETID_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(userId);

				if (bindFdsName) {
					queryPos.add(fdsName);
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

	private static final String _FINDER_COLUMN_U_F_P_P_USERID_2 =
		"fvsFrontendDataSetEntry.userId = ? AND ";

	private static final String _FINDER_COLUMN_U_F_P_P_FDSNAME_2 =
		"fvsFrontendDataSetEntry.fdsName = ? AND ";

	private static final String _FINDER_COLUMN_U_F_P_P_FDSNAME_3 =
		"(fvsFrontendDataSetEntry.fdsName IS NULL OR fvsFrontendDataSetEntry.fdsName = '') AND ";

	private static final String _FINDER_COLUMN_U_F_P_P_PLID_2 =
		"fvsFrontendDataSetEntry.plid = ? AND ";

	private static final String _FINDER_COLUMN_U_F_P_P_PORTLETID_2 =
		"fvsFrontendDataSetEntry.portletId = ?";

	private static final String _FINDER_COLUMN_U_F_P_P_PORTLETID_3 =
		"(fvsFrontendDataSetEntry.portletId IS NULL OR fvsFrontendDataSetEntry.portletId = '')";

	public FVSFrontendDataSetEntryPersistenceImpl() {
		Map<String, String> dbColumnNames = new HashMap<String, String>();

		dbColumnNames.put("uuid", "uuid_");

		setDBColumnNames(dbColumnNames);

		setModelClass(FVSFrontendDataSetEntry.class);

		setModelImplClass(FVSFrontendDataSetEntryImpl.class);
		setModelPKClass(long.class);

		setTable(FVSFrontendDataSetEntryTable.INSTANCE);
	}

	/**
	 * Caches the fvs frontend data set entry in the entity cache if it is enabled.
	 *
	 * @param fvsFrontendDataSetEntry the fvs frontend data set entry
	 */
	@Override
	public void cacheResult(FVSFrontendDataSetEntry fvsFrontendDataSetEntry) {
		entityCache.putResult(
			FVSFrontendDataSetEntryImpl.class,
			fvsFrontendDataSetEntry.getPrimaryKey(), fvsFrontendDataSetEntry);
	}

	private int _valueObjectFinderCacheListThreshold;

	/**
	 * Caches the fvs frontend data set entries in the entity cache if it is enabled.
	 *
	 * @param fvsFrontendDataSetEntries the fvs frontend data set entries
	 */
	@Override
	public void cacheResult(
		List<FVSFrontendDataSetEntry> fvsFrontendDataSetEntries) {

		if ((_valueObjectFinderCacheListThreshold == 0) ||
			((_valueObjectFinderCacheListThreshold > 0) &&
			 (fvsFrontendDataSetEntries.size() >
				 _valueObjectFinderCacheListThreshold))) {

			return;
		}

		for (FVSFrontendDataSetEntry fvsFrontendDataSetEntry :
				fvsFrontendDataSetEntries) {

			if (entityCache.getResult(
					FVSFrontendDataSetEntryImpl.class,
					fvsFrontendDataSetEntry.getPrimaryKey()) == null) {

				cacheResult(fvsFrontendDataSetEntry);
			}
		}
	}

	/**
	 * Clears the cache for all fvs frontend data set entries.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(FVSFrontendDataSetEntryImpl.class);

		finderCache.clearCache(FVSFrontendDataSetEntryImpl.class);
	}

	/**
	 * Clears the cache for the fvs frontend data set entry.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(FVSFrontendDataSetEntry fvsFrontendDataSetEntry) {
		entityCache.removeResult(
			FVSFrontendDataSetEntryImpl.class, fvsFrontendDataSetEntry);
	}

	@Override
	public void clearCache(
		List<FVSFrontendDataSetEntry> fvsFrontendDataSetEntries) {

		for (FVSFrontendDataSetEntry fvsFrontendDataSetEntry :
				fvsFrontendDataSetEntries) {

			entityCache.removeResult(
				FVSFrontendDataSetEntryImpl.class, fvsFrontendDataSetEntry);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(FVSFrontendDataSetEntryImpl.class);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(
				FVSFrontendDataSetEntryImpl.class, primaryKey);
		}
	}

	/**
	 * Creates a new fvs frontend data set entry with the primary key. Does not add the fvs frontend data set entry to the database.
	 *
	 * @param fvsFrontendDataSetEntryId the primary key for the new fvs frontend data set entry
	 * @return the new fvs frontend data set entry
	 */
	@Override
	public FVSFrontendDataSetEntry create(long fvsFrontendDataSetEntryId) {
		FVSFrontendDataSetEntry fvsFrontendDataSetEntry =
			new FVSFrontendDataSetEntryImpl();

		fvsFrontendDataSetEntry.setNew(true);
		fvsFrontendDataSetEntry.setPrimaryKey(fvsFrontendDataSetEntryId);

		String uuid = _portalUUID.generate();

		fvsFrontendDataSetEntry.setUuid(uuid);

		fvsFrontendDataSetEntry.setCompanyId(CompanyThreadLocal.getCompanyId());

		return fvsFrontendDataSetEntry;
	}

	/**
	 * Removes the fvs frontend data set entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param fvsFrontendDataSetEntryId the primary key of the fvs frontend data set entry
	 * @return the fvs frontend data set entry that was removed
	 * @throws NoSuchFrontendDataSetEntryException if a fvs frontend data set entry with the primary key could not be found
	 */
	@Override
	public FVSFrontendDataSetEntry remove(long fvsFrontendDataSetEntryId)
		throws NoSuchFrontendDataSetEntryException {

		return remove((Serializable)fvsFrontendDataSetEntryId);
	}

	/**
	 * Removes the fvs frontend data set entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the fvs frontend data set entry
	 * @return the fvs frontend data set entry that was removed
	 * @throws NoSuchFrontendDataSetEntryException if a fvs frontend data set entry with the primary key could not be found
	 */
	@Override
	public FVSFrontendDataSetEntry remove(Serializable primaryKey)
		throws NoSuchFrontendDataSetEntryException {

		Session session = null;

		try {
			session = openSession();

			FVSFrontendDataSetEntry fvsFrontendDataSetEntry =
				(FVSFrontendDataSetEntry)session.get(
					FVSFrontendDataSetEntryImpl.class, primaryKey);

			if (fvsFrontendDataSetEntry == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchFrontendDataSetEntryException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(fvsFrontendDataSetEntry);
		}
		catch (NoSuchFrontendDataSetEntryException noSuchEntityException) {
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
	protected FVSFrontendDataSetEntry removeImpl(
		FVSFrontendDataSetEntry fvsFrontendDataSetEntry) {

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(fvsFrontendDataSetEntry)) {
				fvsFrontendDataSetEntry = (FVSFrontendDataSetEntry)session.get(
					FVSFrontendDataSetEntryImpl.class,
					fvsFrontendDataSetEntry.getPrimaryKeyObj());
			}

			if (fvsFrontendDataSetEntry != null) {
				session.delete(fvsFrontendDataSetEntry);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (fvsFrontendDataSetEntry != null) {
			clearCache(fvsFrontendDataSetEntry);
		}

		return fvsFrontendDataSetEntry;
	}

	@Override
	public FVSFrontendDataSetEntry updateImpl(
		FVSFrontendDataSetEntry fvsFrontendDataSetEntry) {

		boolean isNew = fvsFrontendDataSetEntry.isNew();

		if (!(fvsFrontendDataSetEntry instanceof
				FVSFrontendDataSetEntryModelImpl)) {

			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(fvsFrontendDataSetEntry.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					fvsFrontendDataSetEntry);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in fvsFrontendDataSetEntry proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom FVSFrontendDataSetEntry implementation " +
					fvsFrontendDataSetEntry.getClass());
		}

		FVSFrontendDataSetEntryModelImpl fvsFrontendDataSetEntryModelImpl =
			(FVSFrontendDataSetEntryModelImpl)fvsFrontendDataSetEntry;

		if (Validator.isNull(fvsFrontendDataSetEntry.getUuid())) {
			String uuid = _portalUUID.generate();

			fvsFrontendDataSetEntry.setUuid(uuid);
		}

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Date date = new Date();

		if (isNew && (fvsFrontendDataSetEntry.getCreateDate() == null)) {
			if (serviceContext == null) {
				fvsFrontendDataSetEntry.setCreateDate(date);
			}
			else {
				fvsFrontendDataSetEntry.setCreateDate(
					serviceContext.getCreateDate(date));
			}
		}

		if (!fvsFrontendDataSetEntryModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				fvsFrontendDataSetEntry.setModifiedDate(date);
			}
			else {
				fvsFrontendDataSetEntry.setModifiedDate(
					serviceContext.getModifiedDate(date));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (isNew) {
				session.save(fvsFrontendDataSetEntry);
			}
			else {
				fvsFrontendDataSetEntry =
					(FVSFrontendDataSetEntry)session.merge(
						fvsFrontendDataSetEntry);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		entityCache.putResult(
			FVSFrontendDataSetEntryImpl.class, fvsFrontendDataSetEntryModelImpl,
			false, true);

		if (isNew) {
			fvsFrontendDataSetEntry.setNew(false);
		}

		fvsFrontendDataSetEntry.resetOriginalValues();

		return fvsFrontendDataSetEntry;
	}

	/**
	 * Returns the fvs frontend data set entry with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the fvs frontend data set entry
	 * @return the fvs frontend data set entry
	 * @throws NoSuchFrontendDataSetEntryException if a fvs frontend data set entry with the primary key could not be found
	 */
	@Override
	public FVSFrontendDataSetEntry findByPrimaryKey(Serializable primaryKey)
		throws NoSuchFrontendDataSetEntryException {

		FVSFrontendDataSetEntry fvsFrontendDataSetEntry = fetchByPrimaryKey(
			primaryKey);

		if (fvsFrontendDataSetEntry == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchFrontendDataSetEntryException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return fvsFrontendDataSetEntry;
	}

	/**
	 * Returns the fvs frontend data set entry with the primary key or throws a <code>NoSuchFrontendDataSetEntryException</code> if it could not be found.
	 *
	 * @param fvsFrontendDataSetEntryId the primary key of the fvs frontend data set entry
	 * @return the fvs frontend data set entry
	 * @throws NoSuchFrontendDataSetEntryException if a fvs frontend data set entry with the primary key could not be found
	 */
	@Override
	public FVSFrontendDataSetEntry findByPrimaryKey(
			long fvsFrontendDataSetEntryId)
		throws NoSuchFrontendDataSetEntryException {

		return findByPrimaryKey((Serializable)fvsFrontendDataSetEntryId);
	}

	/**
	 * Returns the fvs frontend data set entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param fvsFrontendDataSetEntryId the primary key of the fvs frontend data set entry
	 * @return the fvs frontend data set entry, or <code>null</code> if a fvs frontend data set entry with the primary key could not be found
	 */
	@Override
	public FVSFrontendDataSetEntry fetchByPrimaryKey(
		long fvsFrontendDataSetEntryId) {

		return fetchByPrimaryKey((Serializable)fvsFrontendDataSetEntryId);
	}

	/**
	 * Returns all the fvs frontend data set entries.
	 *
	 * @return the fvs frontend data set entries
	 */
	@Override
	public List<FVSFrontendDataSetEntry> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the fvs frontend data set entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FVSFrontendDataSetEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of fvs frontend data set entries
	 * @param end the upper bound of the range of fvs frontend data set entries (not inclusive)
	 * @return the range of fvs frontend data set entries
	 */
	@Override
	public List<FVSFrontendDataSetEntry> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the fvs frontend data set entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FVSFrontendDataSetEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of fvs frontend data set entries
	 * @param end the upper bound of the range of fvs frontend data set entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of fvs frontend data set entries
	 */
	@Override
	public List<FVSFrontendDataSetEntry> findAll(
		int start, int end,
		OrderByComparator<FVSFrontendDataSetEntry> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the fvs frontend data set entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FVSFrontendDataSetEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of fvs frontend data set entries
	 * @param end the upper bound of the range of fvs frontend data set entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of fvs frontend data set entries
	 */
	@Override
	public List<FVSFrontendDataSetEntry> findAll(
		int start, int end,
		OrderByComparator<FVSFrontendDataSetEntry> orderByComparator,
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

		List<FVSFrontendDataSetEntry> list = null;

		if (useFinderCache) {
			list = (List<FVSFrontendDataSetEntry>)finderCache.getResult(
				finderPath, finderArgs);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_FVSFRONTENDDATASETENTRY);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_FVSFRONTENDDATASETENTRY;

				sql = sql.concat(
					FVSFrontendDataSetEntryModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<FVSFrontendDataSetEntry>)QueryUtil.list(
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
	 * Removes all the fvs frontend data set entries from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (FVSFrontendDataSetEntry fvsFrontendDataSetEntry : findAll()) {
			remove(fvsFrontendDataSetEntry);
		}
	}

	/**
	 * Returns the number of fvs frontend data set entries.
	 *
	 * @return the number of fvs frontend data set entries
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
					_SQL_COUNT_FVSFRONTENDDATASETENTRY);

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
		return "fvsFrontendDataSetEntryId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_FVSFRONTENDDATASETENTRY;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return FVSFrontendDataSetEntryModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the fvs frontend data set entry persistence.
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

		_finderPathWithPaginationFindByU_F_P_P = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByU_F_P_P",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Long.class.getName(), String.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {"userId", "fdsName", "plid", "portletId"}, true);

		_finderPathWithoutPaginationFindByU_F_P_P = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByU_F_P_P",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Long.class.getName(), String.class.getName()
			},
			new String[] {"userId", "fdsName", "plid", "portletId"}, true);

		_finderPathCountByU_F_P_P = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByU_F_P_P",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Long.class.getName(), String.class.getName()
			},
			new String[] {"userId", "fdsName", "plid", "portletId"}, false);

		_setFVSFrontendDataSetEntryUtilPersistence(this);
	}

	@Deactivate
	public void deactivate() {
		_setFVSFrontendDataSetEntryUtilPersistence(null);

		entityCache.removeCache(FVSFrontendDataSetEntryImpl.class.getName());
	}

	private void _setFVSFrontendDataSetEntryUtilPersistence(
		FVSFrontendDataSetEntryPersistence fvsFrontendDataSetEntryPersistence) {

		try {
			Field field = FVSFrontendDataSetEntryUtil.class.getDeclaredField(
				"_persistence");

			field.setAccessible(true);

			field.set(null, fvsFrontendDataSetEntryPersistence);
		}
		catch (ReflectiveOperationException reflectiveOperationException) {
			throw new RuntimeException(reflectiveOperationException);
		}
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

	private static final String _SQL_SELECT_FVSFRONTENDDATASETENTRY =
		"SELECT fvsFrontendDataSetEntry FROM FVSFrontendDataSetEntry fvsFrontendDataSetEntry";

	private static final String _SQL_SELECT_FVSFRONTENDDATASETENTRY_WHERE =
		"SELECT fvsFrontendDataSetEntry FROM FVSFrontendDataSetEntry fvsFrontendDataSetEntry WHERE ";

	private static final String _SQL_COUNT_FVSFRONTENDDATASETENTRY =
		"SELECT COUNT(fvsFrontendDataSetEntry) FROM FVSFrontendDataSetEntry fvsFrontendDataSetEntry";

	private static final String _SQL_COUNT_FVSFRONTENDDATASETENTRY_WHERE =
		"SELECT COUNT(fvsFrontendDataSetEntry) FROM FVSFrontendDataSetEntry fvsFrontendDataSetEntry WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS =
		"fvsFrontendDataSetEntry.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No FVSFrontendDataSetEntry exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No FVSFrontendDataSetEntry exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		FVSFrontendDataSetEntryPersistenceImpl.class);

	private static final Set<String> _badColumnNames = SetUtil.fromArray(
		new String[] {"uuid"});

	@Override
	protected FinderCache getFinderCache() {
		return finderCache;
	}

	@Reference
	private PortalUUID _portalUUID;

	@Reference
	private FVSFrontendDataSetEntryModelArgumentsResolver
		_fvsFrontendDataSetEntryModelArgumentsResolver;

}