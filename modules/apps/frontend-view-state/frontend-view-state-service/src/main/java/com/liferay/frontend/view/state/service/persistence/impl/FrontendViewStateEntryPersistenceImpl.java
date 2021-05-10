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

import com.liferay.frontend.view.state.exception.NoSuchEntryException;
import com.liferay.frontend.view.state.model.FrontendViewStateEntry;
import com.liferay.frontend.view.state.model.FrontendViewStateEntryTable;
import com.liferay.frontend.view.state.model.impl.FrontendViewStateEntryImpl;
import com.liferay.frontend.view.state.model.impl.FrontendViewStateEntryModelImpl;
import com.liferay.frontend.view.state.service.persistence.FrontendViewStateEntryPersistence;
import com.liferay.frontend.view.state.service.persistence.impl.constants.FrontendViewStatePersistenceConstants;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.configuration.Configuration;
import com.liferay.portal.kernel.dao.orm.ArgumentsResolver;
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
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.service.persistence.BasePersistence;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.portal.kernel.util.OrderByComparator;
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
import java.util.concurrent.ConcurrentHashMap;

import javax.sql.DataSource;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * The persistence implementation for the frontend view state entry service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
@Component(
	service = {FrontendViewStateEntryPersistence.class, BasePersistence.class}
)
public class FrontendViewStateEntryPersistenceImpl
	extends BasePersistenceImpl<FrontendViewStateEntry>
	implements FrontendViewStateEntryPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>FrontendViewStateEntryUtil</code> to access the frontend view state entry persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		FrontendViewStateEntryImpl.class.getName();

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
	 * Returns all the frontend view state entries where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching frontend view state entries
	 */
	@Override
	public List<FrontendViewStateEntry> findByUuid(String uuid) {
		return findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the frontend view state entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FrontendViewStateEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of frontend view state entries
	 * @param end the upper bound of the range of frontend view state entries (not inclusive)
	 * @return the range of matching frontend view state entries
	 */
	@Override
	public List<FrontendViewStateEntry> findByUuid(
		String uuid, int start, int end) {

		return findByUuid(uuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the frontend view state entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FrontendViewStateEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of frontend view state entries
	 * @param end the upper bound of the range of frontend view state entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching frontend view state entries
	 */
	@Override
	public List<FrontendViewStateEntry> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<FrontendViewStateEntry> orderByComparator) {

		return findByUuid(uuid, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the frontend view state entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FrontendViewStateEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of frontend view state entries
	 * @param end the upper bound of the range of frontend view state entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching frontend view state entries
	 */
	@Override
	public List<FrontendViewStateEntry> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<FrontendViewStateEntry> orderByComparator,
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

		List<FrontendViewStateEntry> list = null;

		if (useFinderCache) {
			list = (List<FrontendViewStateEntry>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (FrontendViewStateEntry frontendViewStateEntry : list) {
					if (!uuid.equals(frontendViewStateEntry.getUuid())) {
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

			sb.append(_SQL_SELECT_FRONTENDVIEWSTATEENTRY_WHERE);

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
				sb.append(FrontendViewStateEntryModelImpl.ORDER_BY_JPQL);
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

				list = (List<FrontendViewStateEntry>)QueryUtil.list(
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
	 * Returns the first frontend view state entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching frontend view state entry
	 * @throws NoSuchEntryException if a matching frontend view state entry could not be found
	 */
	@Override
	public FrontendViewStateEntry findByUuid_First(
			String uuid,
			OrderByComparator<FrontendViewStateEntry> orderByComparator)
		throws NoSuchEntryException {

		FrontendViewStateEntry frontendViewStateEntry = fetchByUuid_First(
			uuid, orderByComparator);

		if (frontendViewStateEntry != null) {
			return frontendViewStateEntry;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append("}");

		throw new NoSuchEntryException(sb.toString());
	}

	/**
	 * Returns the first frontend view state entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching frontend view state entry, or <code>null</code> if a matching frontend view state entry could not be found
	 */
	@Override
	public FrontendViewStateEntry fetchByUuid_First(
		String uuid,
		OrderByComparator<FrontendViewStateEntry> orderByComparator) {

		List<FrontendViewStateEntry> list = findByUuid(
			uuid, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last frontend view state entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching frontend view state entry
	 * @throws NoSuchEntryException if a matching frontend view state entry could not be found
	 */
	@Override
	public FrontendViewStateEntry findByUuid_Last(
			String uuid,
			OrderByComparator<FrontendViewStateEntry> orderByComparator)
		throws NoSuchEntryException {

		FrontendViewStateEntry frontendViewStateEntry = fetchByUuid_Last(
			uuid, orderByComparator);

		if (frontendViewStateEntry != null) {
			return frontendViewStateEntry;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append("}");

		throw new NoSuchEntryException(sb.toString());
	}

	/**
	 * Returns the last frontend view state entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching frontend view state entry, or <code>null</code> if a matching frontend view state entry could not be found
	 */
	@Override
	public FrontendViewStateEntry fetchByUuid_Last(
		String uuid,
		OrderByComparator<FrontendViewStateEntry> orderByComparator) {

		int count = countByUuid(uuid);

		if (count == 0) {
			return null;
		}

		List<FrontendViewStateEntry> list = findByUuid(
			uuid, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the frontend view state entries before and after the current frontend view state entry in the ordered set where uuid = &#63;.
	 *
	 * @param frontendViewStateEntryId the primary key of the current frontend view state entry
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next frontend view state entry
	 * @throws NoSuchEntryException if a frontend view state entry with the primary key could not be found
	 */
	@Override
	public FrontendViewStateEntry[] findByUuid_PrevAndNext(
			long frontendViewStateEntryId, String uuid,
			OrderByComparator<FrontendViewStateEntry> orderByComparator)
		throws NoSuchEntryException {

		uuid = Objects.toString(uuid, "");

		FrontendViewStateEntry frontendViewStateEntry = findByPrimaryKey(
			frontendViewStateEntryId);

		Session session = null;

		try {
			session = openSession();

			FrontendViewStateEntry[] array = new FrontendViewStateEntryImpl[3];

			array[0] = getByUuid_PrevAndNext(
				session, frontendViewStateEntry, uuid, orderByComparator, true);

			array[1] = frontendViewStateEntry;

			array[2] = getByUuid_PrevAndNext(
				session, frontendViewStateEntry, uuid, orderByComparator,
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

	protected FrontendViewStateEntry getByUuid_PrevAndNext(
		Session session, FrontendViewStateEntry frontendViewStateEntry,
		String uuid,
		OrderByComparator<FrontendViewStateEntry> orderByComparator,
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

		sb.append(_SQL_SELECT_FRONTENDVIEWSTATEENTRY_WHERE);

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
			sb.append(FrontendViewStateEntryModelImpl.ORDER_BY_JPQL);
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
						frontendViewStateEntry)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<FrontendViewStateEntry> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the frontend view state entries where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	@Override
	public void removeByUuid(String uuid) {
		for (FrontendViewStateEntry frontendViewStateEntry :
				findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(frontendViewStateEntry);
		}
	}

	/**
	 * Returns the number of frontend view state entries where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching frontend view state entries
	 */
	@Override
	public int countByUuid(String uuid) {
		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = _finderPathCountByUuid;

		Object[] finderArgs = new Object[] {uuid};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_FRONTENDVIEWSTATEENTRY_WHERE);

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
		"frontendViewStateEntry.uuid = ?";

	private static final String _FINDER_COLUMN_UUID_UUID_3 =
		"(frontendViewStateEntry.uuid IS NULL OR frontendViewStateEntry.uuid = '')";

	private FinderPath _finderPathWithPaginationFindByUuid_C;
	private FinderPath _finderPathWithoutPaginationFindByUuid_C;
	private FinderPath _finderPathCountByUuid_C;

	/**
	 * Returns all the frontend view state entries where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching frontend view state entries
	 */
	@Override
	public List<FrontendViewStateEntry> findByUuid_C(
		String uuid, long companyId) {

		return findByUuid_C(
			uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the frontend view state entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FrontendViewStateEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of frontend view state entries
	 * @param end the upper bound of the range of frontend view state entries (not inclusive)
	 * @return the range of matching frontend view state entries
	 */
	@Override
	public List<FrontendViewStateEntry> findByUuid_C(
		String uuid, long companyId, int start, int end) {

		return findByUuid_C(uuid, companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the frontend view state entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FrontendViewStateEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of frontend view state entries
	 * @param end the upper bound of the range of frontend view state entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching frontend view state entries
	 */
	@Override
	public List<FrontendViewStateEntry> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<FrontendViewStateEntry> orderByComparator) {

		return findByUuid_C(
			uuid, companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the frontend view state entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FrontendViewStateEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of frontend view state entries
	 * @param end the upper bound of the range of frontend view state entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching frontend view state entries
	 */
	@Override
	public List<FrontendViewStateEntry> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<FrontendViewStateEntry> orderByComparator,
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

		List<FrontendViewStateEntry> list = null;

		if (useFinderCache) {
			list = (List<FrontendViewStateEntry>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (FrontendViewStateEntry frontendViewStateEntry : list) {
					if (!uuid.equals(frontendViewStateEntry.getUuid()) ||
						(companyId != frontendViewStateEntry.getCompanyId())) {

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

			sb.append(_SQL_SELECT_FRONTENDVIEWSTATEENTRY_WHERE);

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
				sb.append(FrontendViewStateEntryModelImpl.ORDER_BY_JPQL);
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

				list = (List<FrontendViewStateEntry>)QueryUtil.list(
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
	 * Returns the first frontend view state entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching frontend view state entry
	 * @throws NoSuchEntryException if a matching frontend view state entry could not be found
	 */
	@Override
	public FrontendViewStateEntry findByUuid_C_First(
			String uuid, long companyId,
			OrderByComparator<FrontendViewStateEntry> orderByComparator)
		throws NoSuchEntryException {

		FrontendViewStateEntry frontendViewStateEntry = fetchByUuid_C_First(
			uuid, companyId, orderByComparator);

		if (frontendViewStateEntry != null) {
			return frontendViewStateEntry;
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
	 * Returns the first frontend view state entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching frontend view state entry, or <code>null</code> if a matching frontend view state entry could not be found
	 */
	@Override
	public FrontendViewStateEntry fetchByUuid_C_First(
		String uuid, long companyId,
		OrderByComparator<FrontendViewStateEntry> orderByComparator) {

		List<FrontendViewStateEntry> list = findByUuid_C(
			uuid, companyId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last frontend view state entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching frontend view state entry
	 * @throws NoSuchEntryException if a matching frontend view state entry could not be found
	 */
	@Override
	public FrontendViewStateEntry findByUuid_C_Last(
			String uuid, long companyId,
			OrderByComparator<FrontendViewStateEntry> orderByComparator)
		throws NoSuchEntryException {

		FrontendViewStateEntry frontendViewStateEntry = fetchByUuid_C_Last(
			uuid, companyId, orderByComparator);

		if (frontendViewStateEntry != null) {
			return frontendViewStateEntry;
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
	 * Returns the last frontend view state entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching frontend view state entry, or <code>null</code> if a matching frontend view state entry could not be found
	 */
	@Override
	public FrontendViewStateEntry fetchByUuid_C_Last(
		String uuid, long companyId,
		OrderByComparator<FrontendViewStateEntry> orderByComparator) {

		int count = countByUuid_C(uuid, companyId);

		if (count == 0) {
			return null;
		}

		List<FrontendViewStateEntry> list = findByUuid_C(
			uuid, companyId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the frontend view state entries before and after the current frontend view state entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param frontendViewStateEntryId the primary key of the current frontend view state entry
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next frontend view state entry
	 * @throws NoSuchEntryException if a frontend view state entry with the primary key could not be found
	 */
	@Override
	public FrontendViewStateEntry[] findByUuid_C_PrevAndNext(
			long frontendViewStateEntryId, String uuid, long companyId,
			OrderByComparator<FrontendViewStateEntry> orderByComparator)
		throws NoSuchEntryException {

		uuid = Objects.toString(uuid, "");

		FrontendViewStateEntry frontendViewStateEntry = findByPrimaryKey(
			frontendViewStateEntryId);

		Session session = null;

		try {
			session = openSession();

			FrontendViewStateEntry[] array = new FrontendViewStateEntryImpl[3];

			array[0] = getByUuid_C_PrevAndNext(
				session, frontendViewStateEntry, uuid, companyId,
				orderByComparator, true);

			array[1] = frontendViewStateEntry;

			array[2] = getByUuid_C_PrevAndNext(
				session, frontendViewStateEntry, uuid, companyId,
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

	protected FrontendViewStateEntry getByUuid_C_PrevAndNext(
		Session session, FrontendViewStateEntry frontendViewStateEntry,
		String uuid, long companyId,
		OrderByComparator<FrontendViewStateEntry> orderByComparator,
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

		sb.append(_SQL_SELECT_FRONTENDVIEWSTATEENTRY_WHERE);

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
			sb.append(FrontendViewStateEntryModelImpl.ORDER_BY_JPQL);
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
						frontendViewStateEntry)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<FrontendViewStateEntry> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the frontend view state entries where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	@Override
	public void removeByUuid_C(String uuid, long companyId) {
		for (FrontendViewStateEntry frontendViewStateEntry :
				findByUuid_C(
					uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(frontendViewStateEntry);
		}
	}

	/**
	 * Returns the number of frontend view state entries where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching frontend view state entries
	 */
	@Override
	public int countByUuid_C(String uuid, long companyId) {
		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = _finderPathCountByUuid_C;

		Object[] finderArgs = new Object[] {uuid, companyId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_FRONTENDVIEWSTATEENTRY_WHERE);

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
		"frontendViewStateEntry.uuid = ? AND ";

	private static final String _FINDER_COLUMN_UUID_C_UUID_3 =
		"(frontendViewStateEntry.uuid IS NULL OR frontendViewStateEntry.uuid = '') AND ";

	private static final String _FINDER_COLUMN_UUID_C_COMPANYID_2 =
		"frontendViewStateEntry.companyId = ?";

	public FrontendViewStateEntryPersistenceImpl() {
		Map<String, String> dbColumnNames = new HashMap<String, String>();

		dbColumnNames.put("uuid", "uuid_");

		setDBColumnNames(dbColumnNames);

		setModelClass(FrontendViewStateEntry.class);

		setModelImplClass(FrontendViewStateEntryImpl.class);
		setModelPKClass(long.class);

		setTable(FrontendViewStateEntryTable.INSTANCE);
	}

	/**
	 * Caches the frontend view state entry in the entity cache if it is enabled.
	 *
	 * @param frontendViewStateEntry the frontend view state entry
	 */
	@Override
	public void cacheResult(FrontendViewStateEntry frontendViewStateEntry) {
		entityCache.putResult(
			FrontendViewStateEntryImpl.class,
			frontendViewStateEntry.getPrimaryKey(), frontendViewStateEntry);
	}

	/**
	 * Caches the frontend view state entries in the entity cache if it is enabled.
	 *
	 * @param frontendViewStateEntries the frontend view state entries
	 */
	@Override
	public void cacheResult(
		List<FrontendViewStateEntry> frontendViewStateEntries) {

		for (FrontendViewStateEntry frontendViewStateEntry :
				frontendViewStateEntries) {

			if (entityCache.getResult(
					FrontendViewStateEntryImpl.class,
					frontendViewStateEntry.getPrimaryKey()) == null) {

				cacheResult(frontendViewStateEntry);
			}
		}
	}

	/**
	 * Clears the cache for all frontend view state entries.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(FrontendViewStateEntryImpl.class);

		finderCache.clearCache(FrontendViewStateEntryImpl.class);
	}

	/**
	 * Clears the cache for the frontend view state entry.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(FrontendViewStateEntry frontendViewStateEntry) {
		entityCache.removeResult(
			FrontendViewStateEntryImpl.class, frontendViewStateEntry);
	}

	@Override
	public void clearCache(
		List<FrontendViewStateEntry> frontendViewStateEntries) {

		for (FrontendViewStateEntry frontendViewStateEntry :
				frontendViewStateEntries) {

			entityCache.removeResult(
				FrontendViewStateEntryImpl.class, frontendViewStateEntry);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(FrontendViewStateEntryImpl.class);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(
				FrontendViewStateEntryImpl.class, primaryKey);
		}
	}

	/**
	 * Creates a new frontend view state entry with the primary key. Does not add the frontend view state entry to the database.
	 *
	 * @param frontendViewStateEntryId the primary key for the new frontend view state entry
	 * @return the new frontend view state entry
	 */
	@Override
	public FrontendViewStateEntry create(long frontendViewStateEntryId) {
		FrontendViewStateEntry frontendViewStateEntry =
			new FrontendViewStateEntryImpl();

		frontendViewStateEntry.setNew(true);
		frontendViewStateEntry.setPrimaryKey(frontendViewStateEntryId);

		String uuid = PortalUUIDUtil.generate();

		frontendViewStateEntry.setUuid(uuid);

		frontendViewStateEntry.setCompanyId(CompanyThreadLocal.getCompanyId());

		return frontendViewStateEntry;
	}

	/**
	 * Removes the frontend view state entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param frontendViewStateEntryId the primary key of the frontend view state entry
	 * @return the frontend view state entry that was removed
	 * @throws NoSuchEntryException if a frontend view state entry with the primary key could not be found
	 */
	@Override
	public FrontendViewStateEntry remove(long frontendViewStateEntryId)
		throws NoSuchEntryException {

		return remove((Serializable)frontendViewStateEntryId);
	}

	/**
	 * Removes the frontend view state entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the frontend view state entry
	 * @return the frontend view state entry that was removed
	 * @throws NoSuchEntryException if a frontend view state entry with the primary key could not be found
	 */
	@Override
	public FrontendViewStateEntry remove(Serializable primaryKey)
		throws NoSuchEntryException {

		Session session = null;

		try {
			session = openSession();

			FrontendViewStateEntry frontendViewStateEntry =
				(FrontendViewStateEntry)session.get(
					FrontendViewStateEntryImpl.class, primaryKey);

			if (frontendViewStateEntry == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchEntryException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(frontendViewStateEntry);
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
	protected FrontendViewStateEntry removeImpl(
		FrontendViewStateEntry frontendViewStateEntry) {

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(frontendViewStateEntry)) {
				frontendViewStateEntry = (FrontendViewStateEntry)session.get(
					FrontendViewStateEntryImpl.class,
					frontendViewStateEntry.getPrimaryKeyObj());
			}

			if (frontendViewStateEntry != null) {
				session.delete(frontendViewStateEntry);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (frontendViewStateEntry != null) {
			clearCache(frontendViewStateEntry);
		}

		return frontendViewStateEntry;
	}

	@Override
	public FrontendViewStateEntry updateImpl(
		FrontendViewStateEntry frontendViewStateEntry) {

		boolean isNew = frontendViewStateEntry.isNew();

		if (!(frontendViewStateEntry instanceof
				FrontendViewStateEntryModelImpl)) {

			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(frontendViewStateEntry.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					frontendViewStateEntry);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in frontendViewStateEntry proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom FrontendViewStateEntry implementation " +
					frontendViewStateEntry.getClass());
		}

		FrontendViewStateEntryModelImpl frontendViewStateEntryModelImpl =
			(FrontendViewStateEntryModelImpl)frontendViewStateEntry;

		if (Validator.isNull(frontendViewStateEntry.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			frontendViewStateEntry.setUuid(uuid);
		}

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Date now = new Date();

		if (isNew && (frontendViewStateEntry.getCreateDate() == null)) {
			if (serviceContext == null) {
				frontendViewStateEntry.setCreateDate(now);
			}
			else {
				frontendViewStateEntry.setCreateDate(
					serviceContext.getCreateDate(now));
			}
		}

		if (!frontendViewStateEntryModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				frontendViewStateEntry.setModifiedDate(now);
			}
			else {
				frontendViewStateEntry.setModifiedDate(
					serviceContext.getModifiedDate(now));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (isNew) {
				session.save(frontendViewStateEntry);
			}
			else {
				frontendViewStateEntry = (FrontendViewStateEntry)session.merge(
					frontendViewStateEntry);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		entityCache.putResult(
			FrontendViewStateEntryImpl.class, frontendViewStateEntryModelImpl,
			false, true);

		if (isNew) {
			frontendViewStateEntry.setNew(false);
		}

		frontendViewStateEntry.resetOriginalValues();

		return frontendViewStateEntry;
	}

	/**
	 * Returns the frontend view state entry with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the frontend view state entry
	 * @return the frontend view state entry
	 * @throws NoSuchEntryException if a frontend view state entry with the primary key could not be found
	 */
	@Override
	public FrontendViewStateEntry findByPrimaryKey(Serializable primaryKey)
		throws NoSuchEntryException {

		FrontendViewStateEntry frontendViewStateEntry = fetchByPrimaryKey(
			primaryKey);

		if (frontendViewStateEntry == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchEntryException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return frontendViewStateEntry;
	}

	/**
	 * Returns the frontend view state entry with the primary key or throws a <code>NoSuchEntryException</code> if it could not be found.
	 *
	 * @param frontendViewStateEntryId the primary key of the frontend view state entry
	 * @return the frontend view state entry
	 * @throws NoSuchEntryException if a frontend view state entry with the primary key could not be found
	 */
	@Override
	public FrontendViewStateEntry findByPrimaryKey(
			long frontendViewStateEntryId)
		throws NoSuchEntryException {

		return findByPrimaryKey((Serializable)frontendViewStateEntryId);
	}

	/**
	 * Returns the frontend view state entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param frontendViewStateEntryId the primary key of the frontend view state entry
	 * @return the frontend view state entry, or <code>null</code> if a frontend view state entry with the primary key could not be found
	 */
	@Override
	public FrontendViewStateEntry fetchByPrimaryKey(
		long frontendViewStateEntryId) {

		return fetchByPrimaryKey((Serializable)frontendViewStateEntryId);
	}

	/**
	 * Returns all the frontend view state entries.
	 *
	 * @return the frontend view state entries
	 */
	@Override
	public List<FrontendViewStateEntry> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the frontend view state entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FrontendViewStateEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of frontend view state entries
	 * @param end the upper bound of the range of frontend view state entries (not inclusive)
	 * @return the range of frontend view state entries
	 */
	@Override
	public List<FrontendViewStateEntry> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the frontend view state entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FrontendViewStateEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of frontend view state entries
	 * @param end the upper bound of the range of frontend view state entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of frontend view state entries
	 */
	@Override
	public List<FrontendViewStateEntry> findAll(
		int start, int end,
		OrderByComparator<FrontendViewStateEntry> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the frontend view state entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FrontendViewStateEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of frontend view state entries
	 * @param end the upper bound of the range of frontend view state entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of frontend view state entries
	 */
	@Override
	public List<FrontendViewStateEntry> findAll(
		int start, int end,
		OrderByComparator<FrontendViewStateEntry> orderByComparator,
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

		List<FrontendViewStateEntry> list = null;

		if (useFinderCache) {
			list = (List<FrontendViewStateEntry>)finderCache.getResult(
				finderPath, finderArgs);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_FRONTENDVIEWSTATEENTRY);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_FRONTENDVIEWSTATEENTRY;

				sql = sql.concat(FrontendViewStateEntryModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<FrontendViewStateEntry>)QueryUtil.list(
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
	 * Removes all the frontend view state entries from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (FrontendViewStateEntry frontendViewStateEntry : findAll()) {
			remove(frontendViewStateEntry);
		}
	}

	/**
	 * Returns the number of frontend view state entries.
	 *
	 * @return the number of frontend view state entries
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
					_SQL_COUNT_FRONTENDVIEWSTATEENTRY);

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
		return "frontendViewStateEntryId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_FRONTENDVIEWSTATEENTRY;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return FrontendViewStateEntryModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the frontend view state entry persistence.
	 */
	@Activate
	public void activate(BundleContext bundleContext) {
		_bundleContext = bundleContext;

		_argumentsResolverServiceRegistration = _bundleContext.registerService(
			ArgumentsResolver.class,
			new FrontendViewStateEntryModelArgumentsResolver(),
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
	}

	@Deactivate
	public void deactivate() {
		entityCache.removeCache(FrontendViewStateEntryImpl.class.getName());

		_argumentsResolverServiceRegistration.unregister();
	}

	@Override
	@Reference(
		target = FrontendViewStatePersistenceConstants.SERVICE_CONFIGURATION_FILTER,
		unbind = "-"
	)
	public void setConfiguration(Configuration configuration) {
	}

	@Override
	@Reference(
		target = FrontendViewStatePersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setDataSource(DataSource dataSource) {
		super.setDataSource(dataSource);
	}

	@Override
	@Reference(
		target = FrontendViewStatePersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setSessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	private BundleContext _bundleContext;

	@Reference
	protected EntityCache entityCache;

	@Reference
	protected FinderCache finderCache;

	private static final String _SQL_SELECT_FRONTENDVIEWSTATEENTRY =
		"SELECT frontendViewStateEntry FROM FrontendViewStateEntry frontendViewStateEntry";

	private static final String _SQL_SELECT_FRONTENDVIEWSTATEENTRY_WHERE =
		"SELECT frontendViewStateEntry FROM FrontendViewStateEntry frontendViewStateEntry WHERE ";

	private static final String _SQL_COUNT_FRONTENDVIEWSTATEENTRY =
		"SELECT COUNT(frontendViewStateEntry) FROM FrontendViewStateEntry frontendViewStateEntry";

	private static final String _SQL_COUNT_FRONTENDVIEWSTATEENTRY_WHERE =
		"SELECT COUNT(frontendViewStateEntry) FROM FrontendViewStateEntry frontendViewStateEntry WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS =
		"frontendViewStateEntry.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No FrontendViewStateEntry exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No FrontendViewStateEntry exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		FrontendViewStateEntryPersistenceImpl.class);

	private static final Set<String> _badColumnNames = SetUtil.fromArray(
		new String[] {"uuid"});

	@Override
	protected FinderCache getFinderCache() {
		return finderCache;
	}

	private ServiceRegistration<ArgumentsResolver>
		_argumentsResolverServiceRegistration;

	private static class FrontendViewStateEntryModelArgumentsResolver
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

			FrontendViewStateEntryModelImpl frontendViewStateEntryModelImpl =
				(FrontendViewStateEntryModelImpl)baseModel;

			long columnBitmask =
				frontendViewStateEntryModelImpl.getColumnBitmask();

			if (!checkColumn || (columnBitmask == 0)) {
				return _getValue(
					frontendViewStateEntryModelImpl, columnNames, original);
			}

			Long finderPathColumnBitmask = _finderPathColumnBitmasksCache.get(
				finderPath);

			if (finderPathColumnBitmask == null) {
				finderPathColumnBitmask = 0L;

				for (String columnName : columnNames) {
					finderPathColumnBitmask |=
						frontendViewStateEntryModelImpl.getColumnBitmask(
							columnName);
				}

				_finderPathColumnBitmasksCache.put(
					finderPath, finderPathColumnBitmask);
			}

			if ((columnBitmask & finderPathColumnBitmask) != 0) {
				return _getValue(
					frontendViewStateEntryModelImpl, columnNames, original);
			}

			return null;
		}

		@Override
		public String getClassName() {
			return FrontendViewStateEntryImpl.class.getName();
		}

		@Override
		public String getTableName() {
			return FrontendViewStateEntryTable.INSTANCE.getTableName();
		}

		private static Object[] _getValue(
			FrontendViewStateEntryModelImpl frontendViewStateEntryModelImpl,
			String[] columnNames, boolean original) {

			Object[] arguments = new Object[columnNames.length];

			for (int i = 0; i < arguments.length; i++) {
				String columnName = columnNames[i];

				if (original) {
					arguments[i] =
						frontendViewStateEntryModelImpl.getColumnOriginalValue(
							columnName);
				}
				else {
					arguments[i] =
						frontendViewStateEntryModelImpl.getColumnValue(
							columnName);
				}
			}

			return arguments;
		}

		private static final Map<FinderPath, Long>
			_finderPathColumnBitmasksCache = new ConcurrentHashMap<>();

	}

}