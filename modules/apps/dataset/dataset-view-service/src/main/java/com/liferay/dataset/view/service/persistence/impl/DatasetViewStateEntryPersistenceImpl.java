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

package com.liferay.dataset.view.service.persistence.impl;

import com.liferay.dataset.view.exception.NoSuchStateEntryException;
import com.liferay.dataset.view.model.DatasetViewStateEntry;
import com.liferay.dataset.view.model.DatasetViewStateEntryTable;
import com.liferay.dataset.view.model.impl.DatasetViewStateEntryImpl;
import com.liferay.dataset.view.model.impl.DatasetViewStateEntryModelImpl;
import com.liferay.dataset.view.service.persistence.DatasetViewStateEntryPersistence;
import com.liferay.dataset.view.service.persistence.impl.constants.DatasetViewPersistenceConstants;
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
 * The persistence implementation for the dataset view state entry service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
@Component(
	service = {DatasetViewStateEntryPersistence.class, BasePersistence.class}
)
public class DatasetViewStateEntryPersistenceImpl
	extends BasePersistenceImpl<DatasetViewStateEntry>
	implements DatasetViewStateEntryPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>DatasetViewStateEntryUtil</code> to access the dataset view state entry persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		DatasetViewStateEntryImpl.class.getName();

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
	 * Returns all the dataset view state entries where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching dataset view state entries
	 */
	@Override
	public List<DatasetViewStateEntry> findByUuid(String uuid) {
		return findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the dataset view state entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DatasetViewStateEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of dataset view state entries
	 * @param end the upper bound of the range of dataset view state entries (not inclusive)
	 * @return the range of matching dataset view state entries
	 */
	@Override
	public List<DatasetViewStateEntry> findByUuid(
		String uuid, int start, int end) {

		return findByUuid(uuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the dataset view state entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DatasetViewStateEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of dataset view state entries
	 * @param end the upper bound of the range of dataset view state entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching dataset view state entries
	 */
	@Override
	public List<DatasetViewStateEntry> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<DatasetViewStateEntry> orderByComparator) {

		return findByUuid(uuid, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the dataset view state entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DatasetViewStateEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of dataset view state entries
	 * @param end the upper bound of the range of dataset view state entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching dataset view state entries
	 */
	@Override
	public List<DatasetViewStateEntry> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<DatasetViewStateEntry> orderByComparator,
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

		List<DatasetViewStateEntry> list = null;

		if (useFinderCache) {
			list = (List<DatasetViewStateEntry>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (DatasetViewStateEntry datasetViewStateEntry : list) {
					if (!uuid.equals(datasetViewStateEntry.getUuid())) {
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

			sb.append(_SQL_SELECT_DATASETVIEWSTATEENTRY_WHERE);

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
				sb.append(DatasetViewStateEntryModelImpl.ORDER_BY_JPQL);
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

				list = (List<DatasetViewStateEntry>)QueryUtil.list(
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
	 * Returns the first dataset view state entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching dataset view state entry
	 * @throws NoSuchStateEntryException if a matching dataset view state entry could not be found
	 */
	@Override
	public DatasetViewStateEntry findByUuid_First(
			String uuid,
			OrderByComparator<DatasetViewStateEntry> orderByComparator)
		throws NoSuchStateEntryException {

		DatasetViewStateEntry datasetViewStateEntry = fetchByUuid_First(
			uuid, orderByComparator);

		if (datasetViewStateEntry != null) {
			return datasetViewStateEntry;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append("}");

		throw new NoSuchStateEntryException(sb.toString());
	}

	/**
	 * Returns the first dataset view state entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching dataset view state entry, or <code>null</code> if a matching dataset view state entry could not be found
	 */
	@Override
	public DatasetViewStateEntry fetchByUuid_First(
		String uuid,
		OrderByComparator<DatasetViewStateEntry> orderByComparator) {

		List<DatasetViewStateEntry> list = findByUuid(
			uuid, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last dataset view state entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching dataset view state entry
	 * @throws NoSuchStateEntryException if a matching dataset view state entry could not be found
	 */
	@Override
	public DatasetViewStateEntry findByUuid_Last(
			String uuid,
			OrderByComparator<DatasetViewStateEntry> orderByComparator)
		throws NoSuchStateEntryException {

		DatasetViewStateEntry datasetViewStateEntry = fetchByUuid_Last(
			uuid, orderByComparator);

		if (datasetViewStateEntry != null) {
			return datasetViewStateEntry;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append("}");

		throw new NoSuchStateEntryException(sb.toString());
	}

	/**
	 * Returns the last dataset view state entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching dataset view state entry, or <code>null</code> if a matching dataset view state entry could not be found
	 */
	@Override
	public DatasetViewStateEntry fetchByUuid_Last(
		String uuid,
		OrderByComparator<DatasetViewStateEntry> orderByComparator) {

		int count = countByUuid(uuid);

		if (count == 0) {
			return null;
		}

		List<DatasetViewStateEntry> list = findByUuid(
			uuid, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the dataset view state entries before and after the current dataset view state entry in the ordered set where uuid = &#63;.
	 *
	 * @param datasetViewStateEntryId the primary key of the current dataset view state entry
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next dataset view state entry
	 * @throws NoSuchStateEntryException if a dataset view state entry with the primary key could not be found
	 */
	@Override
	public DatasetViewStateEntry[] findByUuid_PrevAndNext(
			long datasetViewStateEntryId, String uuid,
			OrderByComparator<DatasetViewStateEntry> orderByComparator)
		throws NoSuchStateEntryException {

		uuid = Objects.toString(uuid, "");

		DatasetViewStateEntry datasetViewStateEntry = findByPrimaryKey(
			datasetViewStateEntryId);

		Session session = null;

		try {
			session = openSession();

			DatasetViewStateEntry[] array = new DatasetViewStateEntryImpl[3];

			array[0] = getByUuid_PrevAndNext(
				session, datasetViewStateEntry, uuid, orderByComparator, true);

			array[1] = datasetViewStateEntry;

			array[2] = getByUuid_PrevAndNext(
				session, datasetViewStateEntry, uuid, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected DatasetViewStateEntry getByUuid_PrevAndNext(
		Session session, DatasetViewStateEntry datasetViewStateEntry,
		String uuid, OrderByComparator<DatasetViewStateEntry> orderByComparator,
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

		sb.append(_SQL_SELECT_DATASETVIEWSTATEENTRY_WHERE);

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
			sb.append(DatasetViewStateEntryModelImpl.ORDER_BY_JPQL);
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
						datasetViewStateEntry)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<DatasetViewStateEntry> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the dataset view state entries where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	@Override
	public void removeByUuid(String uuid) {
		for (DatasetViewStateEntry datasetViewStateEntry :
				findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(datasetViewStateEntry);
		}
	}

	/**
	 * Returns the number of dataset view state entries where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching dataset view state entries
	 */
	@Override
	public int countByUuid(String uuid) {
		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = _finderPathCountByUuid;

		Object[] finderArgs = new Object[] {uuid};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_DATASETVIEWSTATEENTRY_WHERE);

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
		"datasetViewStateEntry.uuid = ?";

	private static final String _FINDER_COLUMN_UUID_UUID_3 =
		"(datasetViewStateEntry.uuid IS NULL OR datasetViewStateEntry.uuid = '')";

	private FinderPath _finderPathWithPaginationFindByUuid_C;
	private FinderPath _finderPathWithoutPaginationFindByUuid_C;
	private FinderPath _finderPathCountByUuid_C;

	/**
	 * Returns all the dataset view state entries where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching dataset view state entries
	 */
	@Override
	public List<DatasetViewStateEntry> findByUuid_C(
		String uuid, long companyId) {

		return findByUuid_C(
			uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the dataset view state entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DatasetViewStateEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of dataset view state entries
	 * @param end the upper bound of the range of dataset view state entries (not inclusive)
	 * @return the range of matching dataset view state entries
	 */
	@Override
	public List<DatasetViewStateEntry> findByUuid_C(
		String uuid, long companyId, int start, int end) {

		return findByUuid_C(uuid, companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the dataset view state entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DatasetViewStateEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of dataset view state entries
	 * @param end the upper bound of the range of dataset view state entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching dataset view state entries
	 */
	@Override
	public List<DatasetViewStateEntry> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<DatasetViewStateEntry> orderByComparator) {

		return findByUuid_C(
			uuid, companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the dataset view state entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DatasetViewStateEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of dataset view state entries
	 * @param end the upper bound of the range of dataset view state entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching dataset view state entries
	 */
	@Override
	public List<DatasetViewStateEntry> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<DatasetViewStateEntry> orderByComparator,
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

		List<DatasetViewStateEntry> list = null;

		if (useFinderCache) {
			list = (List<DatasetViewStateEntry>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (DatasetViewStateEntry datasetViewStateEntry : list) {
					if (!uuid.equals(datasetViewStateEntry.getUuid()) ||
						(companyId != datasetViewStateEntry.getCompanyId())) {

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

			sb.append(_SQL_SELECT_DATASETVIEWSTATEENTRY_WHERE);

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
				sb.append(DatasetViewStateEntryModelImpl.ORDER_BY_JPQL);
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

				list = (List<DatasetViewStateEntry>)QueryUtil.list(
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
	 * Returns the first dataset view state entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching dataset view state entry
	 * @throws NoSuchStateEntryException if a matching dataset view state entry could not be found
	 */
	@Override
	public DatasetViewStateEntry findByUuid_C_First(
			String uuid, long companyId,
			OrderByComparator<DatasetViewStateEntry> orderByComparator)
		throws NoSuchStateEntryException {

		DatasetViewStateEntry datasetViewStateEntry = fetchByUuid_C_First(
			uuid, companyId, orderByComparator);

		if (datasetViewStateEntry != null) {
			return datasetViewStateEntry;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append(", companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchStateEntryException(sb.toString());
	}

	/**
	 * Returns the first dataset view state entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching dataset view state entry, or <code>null</code> if a matching dataset view state entry could not be found
	 */
	@Override
	public DatasetViewStateEntry fetchByUuid_C_First(
		String uuid, long companyId,
		OrderByComparator<DatasetViewStateEntry> orderByComparator) {

		List<DatasetViewStateEntry> list = findByUuid_C(
			uuid, companyId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last dataset view state entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching dataset view state entry
	 * @throws NoSuchStateEntryException if a matching dataset view state entry could not be found
	 */
	@Override
	public DatasetViewStateEntry findByUuid_C_Last(
			String uuid, long companyId,
			OrderByComparator<DatasetViewStateEntry> orderByComparator)
		throws NoSuchStateEntryException {

		DatasetViewStateEntry datasetViewStateEntry = fetchByUuid_C_Last(
			uuid, companyId, orderByComparator);

		if (datasetViewStateEntry != null) {
			return datasetViewStateEntry;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append(", companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchStateEntryException(sb.toString());
	}

	/**
	 * Returns the last dataset view state entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching dataset view state entry, or <code>null</code> if a matching dataset view state entry could not be found
	 */
	@Override
	public DatasetViewStateEntry fetchByUuid_C_Last(
		String uuid, long companyId,
		OrderByComparator<DatasetViewStateEntry> orderByComparator) {

		int count = countByUuid_C(uuid, companyId);

		if (count == 0) {
			return null;
		}

		List<DatasetViewStateEntry> list = findByUuid_C(
			uuid, companyId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the dataset view state entries before and after the current dataset view state entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param datasetViewStateEntryId the primary key of the current dataset view state entry
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next dataset view state entry
	 * @throws NoSuchStateEntryException if a dataset view state entry with the primary key could not be found
	 */
	@Override
	public DatasetViewStateEntry[] findByUuid_C_PrevAndNext(
			long datasetViewStateEntryId, String uuid, long companyId,
			OrderByComparator<DatasetViewStateEntry> orderByComparator)
		throws NoSuchStateEntryException {

		uuid = Objects.toString(uuid, "");

		DatasetViewStateEntry datasetViewStateEntry = findByPrimaryKey(
			datasetViewStateEntryId);

		Session session = null;

		try {
			session = openSession();

			DatasetViewStateEntry[] array = new DatasetViewStateEntryImpl[3];

			array[0] = getByUuid_C_PrevAndNext(
				session, datasetViewStateEntry, uuid, companyId,
				orderByComparator, true);

			array[1] = datasetViewStateEntry;

			array[2] = getByUuid_C_PrevAndNext(
				session, datasetViewStateEntry, uuid, companyId,
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

	protected DatasetViewStateEntry getByUuid_C_PrevAndNext(
		Session session, DatasetViewStateEntry datasetViewStateEntry,
		String uuid, long companyId,
		OrderByComparator<DatasetViewStateEntry> orderByComparator,
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

		sb.append(_SQL_SELECT_DATASETVIEWSTATEENTRY_WHERE);

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
			sb.append(DatasetViewStateEntryModelImpl.ORDER_BY_JPQL);
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
						datasetViewStateEntry)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<DatasetViewStateEntry> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the dataset view state entries where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	@Override
	public void removeByUuid_C(String uuid, long companyId) {
		for (DatasetViewStateEntry datasetViewStateEntry :
				findByUuid_C(
					uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(datasetViewStateEntry);
		}
	}

	/**
	 * Returns the number of dataset view state entries where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching dataset view state entries
	 */
	@Override
	public int countByUuid_C(String uuid, long companyId) {
		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = _finderPathCountByUuid_C;

		Object[] finderArgs = new Object[] {uuid, companyId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_DATASETVIEWSTATEENTRY_WHERE);

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
		"datasetViewStateEntry.uuid = ? AND ";

	private static final String _FINDER_COLUMN_UUID_C_UUID_3 =
		"(datasetViewStateEntry.uuid IS NULL OR datasetViewStateEntry.uuid = '') AND ";

	private static final String _FINDER_COLUMN_UUID_C_COMPANYID_2 =
		"datasetViewStateEntry.companyId = ?";

	public DatasetViewStateEntryPersistenceImpl() {
		Map<String, String> dbColumnNames = new HashMap<String, String>();

		dbColumnNames.put("uuid", "uuid_");

		setDBColumnNames(dbColumnNames);

		setModelClass(DatasetViewStateEntry.class);

		setModelImplClass(DatasetViewStateEntryImpl.class);
		setModelPKClass(long.class);

		setTable(DatasetViewStateEntryTable.INSTANCE);
	}

	/**
	 * Caches the dataset view state entry in the entity cache if it is enabled.
	 *
	 * @param datasetViewStateEntry the dataset view state entry
	 */
	@Override
	public void cacheResult(DatasetViewStateEntry datasetViewStateEntry) {
		entityCache.putResult(
			DatasetViewStateEntryImpl.class,
			datasetViewStateEntry.getPrimaryKey(), datasetViewStateEntry);
	}

	/**
	 * Caches the dataset view state entries in the entity cache if it is enabled.
	 *
	 * @param datasetViewStateEntries the dataset view state entries
	 */
	@Override
	public void cacheResult(
		List<DatasetViewStateEntry> datasetViewStateEntries) {

		for (DatasetViewStateEntry datasetViewStateEntry :
				datasetViewStateEntries) {

			if (entityCache.getResult(
					DatasetViewStateEntryImpl.class,
					datasetViewStateEntry.getPrimaryKey()) == null) {

				cacheResult(datasetViewStateEntry);
			}
		}
	}

	/**
	 * Clears the cache for all dataset view state entries.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(DatasetViewStateEntryImpl.class);

		finderCache.clearCache(DatasetViewStateEntryImpl.class);
	}

	/**
	 * Clears the cache for the dataset view state entry.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(DatasetViewStateEntry datasetViewStateEntry) {
		entityCache.removeResult(
			DatasetViewStateEntryImpl.class, datasetViewStateEntry);
	}

	@Override
	public void clearCache(
		List<DatasetViewStateEntry> datasetViewStateEntries) {

		for (DatasetViewStateEntry datasetViewStateEntry :
				datasetViewStateEntries) {

			entityCache.removeResult(
				DatasetViewStateEntryImpl.class, datasetViewStateEntry);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(DatasetViewStateEntryImpl.class);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(
				DatasetViewStateEntryImpl.class, primaryKey);
		}
	}

	/**
	 * Creates a new dataset view state entry with the primary key. Does not add the dataset view state entry to the database.
	 *
	 * @param datasetViewStateEntryId the primary key for the new dataset view state entry
	 * @return the new dataset view state entry
	 */
	@Override
	public DatasetViewStateEntry create(long datasetViewStateEntryId) {
		DatasetViewStateEntry datasetViewStateEntry =
			new DatasetViewStateEntryImpl();

		datasetViewStateEntry.setNew(true);
		datasetViewStateEntry.setPrimaryKey(datasetViewStateEntryId);

		String uuid = PortalUUIDUtil.generate();

		datasetViewStateEntry.setUuid(uuid);

		datasetViewStateEntry.setCompanyId(CompanyThreadLocal.getCompanyId());

		return datasetViewStateEntry;
	}

	/**
	 * Removes the dataset view state entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param datasetViewStateEntryId the primary key of the dataset view state entry
	 * @return the dataset view state entry that was removed
	 * @throws NoSuchStateEntryException if a dataset view state entry with the primary key could not be found
	 */
	@Override
	public DatasetViewStateEntry remove(long datasetViewStateEntryId)
		throws NoSuchStateEntryException {

		return remove((Serializable)datasetViewStateEntryId);
	}

	/**
	 * Removes the dataset view state entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the dataset view state entry
	 * @return the dataset view state entry that was removed
	 * @throws NoSuchStateEntryException if a dataset view state entry with the primary key could not be found
	 */
	@Override
	public DatasetViewStateEntry remove(Serializable primaryKey)
		throws NoSuchStateEntryException {

		Session session = null;

		try {
			session = openSession();

			DatasetViewStateEntry datasetViewStateEntry =
				(DatasetViewStateEntry)session.get(
					DatasetViewStateEntryImpl.class, primaryKey);

			if (datasetViewStateEntry == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchStateEntryException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(datasetViewStateEntry);
		}
		catch (NoSuchStateEntryException noSuchEntityException) {
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
	protected DatasetViewStateEntry removeImpl(
		DatasetViewStateEntry datasetViewStateEntry) {

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(datasetViewStateEntry)) {
				datasetViewStateEntry = (DatasetViewStateEntry)session.get(
					DatasetViewStateEntryImpl.class,
					datasetViewStateEntry.getPrimaryKeyObj());
			}

			if (datasetViewStateEntry != null) {
				session.delete(datasetViewStateEntry);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (datasetViewStateEntry != null) {
			clearCache(datasetViewStateEntry);
		}

		return datasetViewStateEntry;
	}

	@Override
	public DatasetViewStateEntry updateImpl(
		DatasetViewStateEntry datasetViewStateEntry) {

		boolean isNew = datasetViewStateEntry.isNew();

		if (!(datasetViewStateEntry instanceof
				DatasetViewStateEntryModelImpl)) {

			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(datasetViewStateEntry.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					datasetViewStateEntry);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in datasetViewStateEntry proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom DatasetViewStateEntry implementation " +
					datasetViewStateEntry.getClass());
		}

		DatasetViewStateEntryModelImpl datasetViewStateEntryModelImpl =
			(DatasetViewStateEntryModelImpl)datasetViewStateEntry;

		if (Validator.isNull(datasetViewStateEntry.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			datasetViewStateEntry.setUuid(uuid);
		}

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Date now = new Date();

		if (isNew && (datasetViewStateEntry.getCreateDate() == null)) {
			if (serviceContext == null) {
				datasetViewStateEntry.setCreateDate(now);
			}
			else {
				datasetViewStateEntry.setCreateDate(
					serviceContext.getCreateDate(now));
			}
		}

		if (!datasetViewStateEntryModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				datasetViewStateEntry.setModifiedDate(now);
			}
			else {
				datasetViewStateEntry.setModifiedDate(
					serviceContext.getModifiedDate(now));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (isNew) {
				session.save(datasetViewStateEntry);
			}
			else {
				datasetViewStateEntry = (DatasetViewStateEntry)session.merge(
					datasetViewStateEntry);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		entityCache.putResult(
			DatasetViewStateEntryImpl.class, datasetViewStateEntryModelImpl,
			false, true);

		if (isNew) {
			datasetViewStateEntry.setNew(false);
		}

		datasetViewStateEntry.resetOriginalValues();

		return datasetViewStateEntry;
	}

	/**
	 * Returns the dataset view state entry with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the dataset view state entry
	 * @return the dataset view state entry
	 * @throws NoSuchStateEntryException if a dataset view state entry with the primary key could not be found
	 */
	@Override
	public DatasetViewStateEntry findByPrimaryKey(Serializable primaryKey)
		throws NoSuchStateEntryException {

		DatasetViewStateEntry datasetViewStateEntry = fetchByPrimaryKey(
			primaryKey);

		if (datasetViewStateEntry == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchStateEntryException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return datasetViewStateEntry;
	}

	/**
	 * Returns the dataset view state entry with the primary key or throws a <code>NoSuchStateEntryException</code> if it could not be found.
	 *
	 * @param datasetViewStateEntryId the primary key of the dataset view state entry
	 * @return the dataset view state entry
	 * @throws NoSuchStateEntryException if a dataset view state entry with the primary key could not be found
	 */
	@Override
	public DatasetViewStateEntry findByPrimaryKey(long datasetViewStateEntryId)
		throws NoSuchStateEntryException {

		return findByPrimaryKey((Serializable)datasetViewStateEntryId);
	}

	/**
	 * Returns the dataset view state entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param datasetViewStateEntryId the primary key of the dataset view state entry
	 * @return the dataset view state entry, or <code>null</code> if a dataset view state entry with the primary key could not be found
	 */
	@Override
	public DatasetViewStateEntry fetchByPrimaryKey(
		long datasetViewStateEntryId) {

		return fetchByPrimaryKey((Serializable)datasetViewStateEntryId);
	}

	/**
	 * Returns all the dataset view state entries.
	 *
	 * @return the dataset view state entries
	 */
	@Override
	public List<DatasetViewStateEntry> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the dataset view state entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DatasetViewStateEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of dataset view state entries
	 * @param end the upper bound of the range of dataset view state entries (not inclusive)
	 * @return the range of dataset view state entries
	 */
	@Override
	public List<DatasetViewStateEntry> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the dataset view state entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DatasetViewStateEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of dataset view state entries
	 * @param end the upper bound of the range of dataset view state entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of dataset view state entries
	 */
	@Override
	public List<DatasetViewStateEntry> findAll(
		int start, int end,
		OrderByComparator<DatasetViewStateEntry> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the dataset view state entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DatasetViewStateEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of dataset view state entries
	 * @param end the upper bound of the range of dataset view state entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of dataset view state entries
	 */
	@Override
	public List<DatasetViewStateEntry> findAll(
		int start, int end,
		OrderByComparator<DatasetViewStateEntry> orderByComparator,
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

		List<DatasetViewStateEntry> list = null;

		if (useFinderCache) {
			list = (List<DatasetViewStateEntry>)finderCache.getResult(
				finderPath, finderArgs);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_DATASETVIEWSTATEENTRY);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_DATASETVIEWSTATEENTRY;

				sql = sql.concat(DatasetViewStateEntryModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<DatasetViewStateEntry>)QueryUtil.list(
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
	 * Removes all the dataset view state entries from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (DatasetViewStateEntry datasetViewStateEntry : findAll()) {
			remove(datasetViewStateEntry);
		}
	}

	/**
	 * Returns the number of dataset view state entries.
	 *
	 * @return the number of dataset view state entries
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
					_SQL_COUNT_DATASETVIEWSTATEENTRY);

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
		return "datasetViewStateEntryId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_DATASETVIEWSTATEENTRY;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return DatasetViewStateEntryModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the dataset view state entry persistence.
	 */
	@Activate
	public void activate(BundleContext bundleContext) {
		_bundleContext = bundleContext;

		_argumentsResolverServiceRegistration = _bundleContext.registerService(
			ArgumentsResolver.class,
			new DatasetViewStateEntryModelArgumentsResolver(),
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
		entityCache.removeCache(DatasetViewStateEntryImpl.class.getName());

		_argumentsResolverServiceRegistration.unregister();
	}

	@Override
	@Reference(
		target = DatasetViewPersistenceConstants.SERVICE_CONFIGURATION_FILTER,
		unbind = "-"
	)
	public void setConfiguration(Configuration configuration) {
	}

	@Override
	@Reference(
		target = DatasetViewPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setDataSource(DataSource dataSource) {
		super.setDataSource(dataSource);
	}

	@Override
	@Reference(
		target = DatasetViewPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
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

	private static final String _SQL_SELECT_DATASETVIEWSTATEENTRY =
		"SELECT datasetViewStateEntry FROM DatasetViewStateEntry datasetViewStateEntry";

	private static final String _SQL_SELECT_DATASETVIEWSTATEENTRY_WHERE =
		"SELECT datasetViewStateEntry FROM DatasetViewStateEntry datasetViewStateEntry WHERE ";

	private static final String _SQL_COUNT_DATASETVIEWSTATEENTRY =
		"SELECT COUNT(datasetViewStateEntry) FROM DatasetViewStateEntry datasetViewStateEntry";

	private static final String _SQL_COUNT_DATASETVIEWSTATEENTRY_WHERE =
		"SELECT COUNT(datasetViewStateEntry) FROM DatasetViewStateEntry datasetViewStateEntry WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS =
		"datasetViewStateEntry.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No DatasetViewStateEntry exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No DatasetViewStateEntry exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		DatasetViewStateEntryPersistenceImpl.class);

	private static final Set<String> _badColumnNames = SetUtil.fromArray(
		new String[] {"uuid"});

	@Override
	protected FinderCache getFinderCache() {
		return finderCache;
	}

	private ServiceRegistration<ArgumentsResolver>
		_argumentsResolverServiceRegistration;

	private static class DatasetViewStateEntryModelArgumentsResolver
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

			DatasetViewStateEntryModelImpl datasetViewStateEntryModelImpl =
				(DatasetViewStateEntryModelImpl)baseModel;

			long columnBitmask =
				datasetViewStateEntryModelImpl.getColumnBitmask();

			if (!checkColumn || (columnBitmask == 0)) {
				return _getValue(
					datasetViewStateEntryModelImpl, columnNames, original);
			}

			Long finderPathColumnBitmask = _finderPathColumnBitmasksCache.get(
				finderPath);

			if (finderPathColumnBitmask == null) {
				finderPathColumnBitmask = 0L;

				for (String columnName : columnNames) {
					finderPathColumnBitmask |=
						datasetViewStateEntryModelImpl.getColumnBitmask(
							columnName);
				}

				_finderPathColumnBitmasksCache.put(
					finderPath, finderPathColumnBitmask);
			}

			if ((columnBitmask & finderPathColumnBitmask) != 0) {
				return _getValue(
					datasetViewStateEntryModelImpl, columnNames, original);
			}

			return null;
		}

		@Override
		public String getClassName() {
			return DatasetViewStateEntryImpl.class.getName();
		}

		@Override
		public String getTableName() {
			return DatasetViewStateEntryTable.INSTANCE.getTableName();
		}

		private static Object[] _getValue(
			DatasetViewStateEntryModelImpl datasetViewStateEntryModelImpl,
			String[] columnNames, boolean original) {

			Object[] arguments = new Object[columnNames.length];

			for (int i = 0; i < arguments.length; i++) {
				String columnName = columnNames[i];

				if (original) {
					arguments[i] =
						datasetViewStateEntryModelImpl.getColumnOriginalValue(
							columnName);
				}
				else {
					arguments[i] =
						datasetViewStateEntryModelImpl.getColumnValue(
							columnName);
				}
			}

			return arguments;
		}

		private static final Map<FinderPath, Long>
			_finderPathColumnBitmasksCache = new ConcurrentHashMap<>();

	}

}