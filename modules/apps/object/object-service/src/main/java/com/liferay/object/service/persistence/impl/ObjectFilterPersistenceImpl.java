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

package com.liferay.object.service.persistence.impl;

import com.liferay.object.exception.NoSuchObjectFilterException;
import com.liferay.object.model.ObjectFilter;
import com.liferay.object.model.ObjectFilterTable;
import com.liferay.object.model.impl.ObjectFilterImpl;
import com.liferay.object.model.impl.ObjectFilterModelImpl;
import com.liferay.object.service.persistence.ObjectFilterPersistence;
import com.liferay.object.service.persistence.ObjectFilterUtil;
import com.liferay.object.service.persistence.impl.constants.ObjectPersistenceConstants;
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
 * The persistence implementation for the object filter service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Marco Leo
 * @generated
 */
@Component(service = ObjectFilterPersistence.class)
public class ObjectFilterPersistenceImpl
	extends BasePersistenceImpl<ObjectFilter>
	implements ObjectFilterPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>ObjectFilterUtil</code> to access the object filter persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		ObjectFilterImpl.class.getName();

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
	 * Returns all the object filters where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching object filters
	 */
	@Override
	public List<ObjectFilter> findByUuid(String uuid) {
		return findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the object filters where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectFilterModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of object filters
	 * @param end the upper bound of the range of object filters (not inclusive)
	 * @return the range of matching object filters
	 */
	@Override
	public List<ObjectFilter> findByUuid(String uuid, int start, int end) {
		return findByUuid(uuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the object filters where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectFilterModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of object filters
	 * @param end the upper bound of the range of object filters (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching object filters
	 */
	@Override
	public List<ObjectFilter> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<ObjectFilter> orderByComparator) {

		return findByUuid(uuid, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the object filters where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectFilterModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of object filters
	 * @param end the upper bound of the range of object filters (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching object filters
	 */
	@Override
	public List<ObjectFilter> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<ObjectFilter> orderByComparator,
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

		List<ObjectFilter> list = null;

		if (useFinderCache) {
			list = (List<ObjectFilter>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (ObjectFilter objectFilter : list) {
					if (!uuid.equals(objectFilter.getUuid())) {
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

			sb.append(_SQL_SELECT_OBJECTFILTER_WHERE);

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
				sb.append(ObjectFilterModelImpl.ORDER_BY_JPQL);
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

				list = (List<ObjectFilter>)QueryUtil.list(
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
	 * Returns the first object filter in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object filter
	 * @throws NoSuchObjectFilterException if a matching object filter could not be found
	 */
	@Override
	public ObjectFilter findByUuid_First(
			String uuid, OrderByComparator<ObjectFilter> orderByComparator)
		throws NoSuchObjectFilterException {

		ObjectFilter objectFilter = fetchByUuid_First(uuid, orderByComparator);

		if (objectFilter != null) {
			return objectFilter;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append("}");

		throw new NoSuchObjectFilterException(sb.toString());
	}

	/**
	 * Returns the first object filter in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object filter, or <code>null</code> if a matching object filter could not be found
	 */
	@Override
	public ObjectFilter fetchByUuid_First(
		String uuid, OrderByComparator<ObjectFilter> orderByComparator) {

		List<ObjectFilter> list = findByUuid(uuid, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last object filter in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object filter
	 * @throws NoSuchObjectFilterException if a matching object filter could not be found
	 */
	@Override
	public ObjectFilter findByUuid_Last(
			String uuid, OrderByComparator<ObjectFilter> orderByComparator)
		throws NoSuchObjectFilterException {

		ObjectFilter objectFilter = fetchByUuid_Last(uuid, orderByComparator);

		if (objectFilter != null) {
			return objectFilter;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append("}");

		throw new NoSuchObjectFilterException(sb.toString());
	}

	/**
	 * Returns the last object filter in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object filter, or <code>null</code> if a matching object filter could not be found
	 */
	@Override
	public ObjectFilter fetchByUuid_Last(
		String uuid, OrderByComparator<ObjectFilter> orderByComparator) {

		int count = countByUuid(uuid);

		if (count == 0) {
			return null;
		}

		List<ObjectFilter> list = findByUuid(
			uuid, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the object filters before and after the current object filter in the ordered set where uuid = &#63;.
	 *
	 * @param objectFilterId the primary key of the current object filter
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next object filter
	 * @throws NoSuchObjectFilterException if a object filter with the primary key could not be found
	 */
	@Override
	public ObjectFilter[] findByUuid_PrevAndNext(
			long objectFilterId, String uuid,
			OrderByComparator<ObjectFilter> orderByComparator)
		throws NoSuchObjectFilterException {

		uuid = Objects.toString(uuid, "");

		ObjectFilter objectFilter = findByPrimaryKey(objectFilterId);

		Session session = null;

		try {
			session = openSession();

			ObjectFilter[] array = new ObjectFilterImpl[3];

			array[0] = getByUuid_PrevAndNext(
				session, objectFilter, uuid, orderByComparator, true);

			array[1] = objectFilter;

			array[2] = getByUuid_PrevAndNext(
				session, objectFilter, uuid, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected ObjectFilter getByUuid_PrevAndNext(
		Session session, ObjectFilter objectFilter, String uuid,
		OrderByComparator<ObjectFilter> orderByComparator, boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(3);
		}

		sb.append(_SQL_SELECT_OBJECTFILTER_WHERE);

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
			sb.append(ObjectFilterModelImpl.ORDER_BY_JPQL);
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
					orderByComparator.getOrderByConditionValues(objectFilter)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<ObjectFilter> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the object filters where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	@Override
	public void removeByUuid(String uuid) {
		for (ObjectFilter objectFilter :
				findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(objectFilter);
		}
	}

	/**
	 * Returns the number of object filters where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching object filters
	 */
	@Override
	public int countByUuid(String uuid) {
		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = _finderPathCountByUuid;

		Object[] finderArgs = new Object[] {uuid};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_OBJECTFILTER_WHERE);

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
		"objectFilter.uuid = ?";

	private static final String _FINDER_COLUMN_UUID_UUID_3 =
		"(objectFilter.uuid IS NULL OR objectFilter.uuid = '')";

	private FinderPath _finderPathWithPaginationFindByUuid_C;
	private FinderPath _finderPathWithoutPaginationFindByUuid_C;
	private FinderPath _finderPathCountByUuid_C;

	/**
	 * Returns all the object filters where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching object filters
	 */
	@Override
	public List<ObjectFilter> findByUuid_C(String uuid, long companyId) {
		return findByUuid_C(
			uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the object filters where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectFilterModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of object filters
	 * @param end the upper bound of the range of object filters (not inclusive)
	 * @return the range of matching object filters
	 */
	@Override
	public List<ObjectFilter> findByUuid_C(
		String uuid, long companyId, int start, int end) {

		return findByUuid_C(uuid, companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the object filters where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectFilterModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of object filters
	 * @param end the upper bound of the range of object filters (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching object filters
	 */
	@Override
	public List<ObjectFilter> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<ObjectFilter> orderByComparator) {

		return findByUuid_C(
			uuid, companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the object filters where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectFilterModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of object filters
	 * @param end the upper bound of the range of object filters (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching object filters
	 */
	@Override
	public List<ObjectFilter> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<ObjectFilter> orderByComparator,
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

		List<ObjectFilter> list = null;

		if (useFinderCache) {
			list = (List<ObjectFilter>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (ObjectFilter objectFilter : list) {
					if (!uuid.equals(objectFilter.getUuid()) ||
						(companyId != objectFilter.getCompanyId())) {

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

			sb.append(_SQL_SELECT_OBJECTFILTER_WHERE);

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
				sb.append(ObjectFilterModelImpl.ORDER_BY_JPQL);
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

				list = (List<ObjectFilter>)QueryUtil.list(
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
	 * Returns the first object filter in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object filter
	 * @throws NoSuchObjectFilterException if a matching object filter could not be found
	 */
	@Override
	public ObjectFilter findByUuid_C_First(
			String uuid, long companyId,
			OrderByComparator<ObjectFilter> orderByComparator)
		throws NoSuchObjectFilterException {

		ObjectFilter objectFilter = fetchByUuid_C_First(
			uuid, companyId, orderByComparator);

		if (objectFilter != null) {
			return objectFilter;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append(", companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchObjectFilterException(sb.toString());
	}

	/**
	 * Returns the first object filter in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object filter, or <code>null</code> if a matching object filter could not be found
	 */
	@Override
	public ObjectFilter fetchByUuid_C_First(
		String uuid, long companyId,
		OrderByComparator<ObjectFilter> orderByComparator) {

		List<ObjectFilter> list = findByUuid_C(
			uuid, companyId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last object filter in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object filter
	 * @throws NoSuchObjectFilterException if a matching object filter could not be found
	 */
	@Override
	public ObjectFilter findByUuid_C_Last(
			String uuid, long companyId,
			OrderByComparator<ObjectFilter> orderByComparator)
		throws NoSuchObjectFilterException {

		ObjectFilter objectFilter = fetchByUuid_C_Last(
			uuid, companyId, orderByComparator);

		if (objectFilter != null) {
			return objectFilter;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append(", companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchObjectFilterException(sb.toString());
	}

	/**
	 * Returns the last object filter in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object filter, or <code>null</code> if a matching object filter could not be found
	 */
	@Override
	public ObjectFilter fetchByUuid_C_Last(
		String uuid, long companyId,
		OrderByComparator<ObjectFilter> orderByComparator) {

		int count = countByUuid_C(uuid, companyId);

		if (count == 0) {
			return null;
		}

		List<ObjectFilter> list = findByUuid_C(
			uuid, companyId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the object filters before and after the current object filter in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param objectFilterId the primary key of the current object filter
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next object filter
	 * @throws NoSuchObjectFilterException if a object filter with the primary key could not be found
	 */
	@Override
	public ObjectFilter[] findByUuid_C_PrevAndNext(
			long objectFilterId, String uuid, long companyId,
			OrderByComparator<ObjectFilter> orderByComparator)
		throws NoSuchObjectFilterException {

		uuid = Objects.toString(uuid, "");

		ObjectFilter objectFilter = findByPrimaryKey(objectFilterId);

		Session session = null;

		try {
			session = openSession();

			ObjectFilter[] array = new ObjectFilterImpl[3];

			array[0] = getByUuid_C_PrevAndNext(
				session, objectFilter, uuid, companyId, orderByComparator,
				true);

			array[1] = objectFilter;

			array[2] = getByUuid_C_PrevAndNext(
				session, objectFilter, uuid, companyId, orderByComparator,
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

	protected ObjectFilter getByUuid_C_PrevAndNext(
		Session session, ObjectFilter objectFilter, String uuid, long companyId,
		OrderByComparator<ObjectFilter> orderByComparator, boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				5 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(4);
		}

		sb.append(_SQL_SELECT_OBJECTFILTER_WHERE);

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
			sb.append(ObjectFilterModelImpl.ORDER_BY_JPQL);
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
					orderByComparator.getOrderByConditionValues(objectFilter)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<ObjectFilter> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the object filters where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	@Override
	public void removeByUuid_C(String uuid, long companyId) {
		for (ObjectFilter objectFilter :
				findByUuid_C(
					uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(objectFilter);
		}
	}

	/**
	 * Returns the number of object filters where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching object filters
	 */
	@Override
	public int countByUuid_C(String uuid, long companyId) {
		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = _finderPathCountByUuid_C;

		Object[] finderArgs = new Object[] {uuid, companyId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_OBJECTFILTER_WHERE);

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
		"objectFilter.uuid = ? AND ";

	private static final String _FINDER_COLUMN_UUID_C_UUID_3 =
		"(objectFilter.uuid IS NULL OR objectFilter.uuid = '') AND ";

	private static final String _FINDER_COLUMN_UUID_C_COMPANYID_2 =
		"objectFilter.companyId = ?";

	private FinderPath _finderPathWithPaginationFindByObjectFieldId;
	private FinderPath _finderPathWithoutPaginationFindByObjectFieldId;
	private FinderPath _finderPathCountByObjectFieldId;

	/**
	 * Returns all the object filters where objectFieldId = &#63;.
	 *
	 * @param objectFieldId the object field ID
	 * @return the matching object filters
	 */
	@Override
	public List<ObjectFilter> findByObjectFieldId(long objectFieldId) {
		return findByObjectFieldId(
			objectFieldId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the object filters where objectFieldId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectFilterModelImpl</code>.
	 * </p>
	 *
	 * @param objectFieldId the object field ID
	 * @param start the lower bound of the range of object filters
	 * @param end the upper bound of the range of object filters (not inclusive)
	 * @return the range of matching object filters
	 */
	@Override
	public List<ObjectFilter> findByObjectFieldId(
		long objectFieldId, int start, int end) {

		return findByObjectFieldId(objectFieldId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the object filters where objectFieldId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectFilterModelImpl</code>.
	 * </p>
	 *
	 * @param objectFieldId the object field ID
	 * @param start the lower bound of the range of object filters
	 * @param end the upper bound of the range of object filters (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching object filters
	 */
	@Override
	public List<ObjectFilter> findByObjectFieldId(
		long objectFieldId, int start, int end,
		OrderByComparator<ObjectFilter> orderByComparator) {

		return findByObjectFieldId(
			objectFieldId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the object filters where objectFieldId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectFilterModelImpl</code>.
	 * </p>
	 *
	 * @param objectFieldId the object field ID
	 * @param start the lower bound of the range of object filters
	 * @param end the upper bound of the range of object filters (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching object filters
	 */
	@Override
	public List<ObjectFilter> findByObjectFieldId(
		long objectFieldId, int start, int end,
		OrderByComparator<ObjectFilter> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByObjectFieldId;
				finderArgs = new Object[] {objectFieldId};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByObjectFieldId;
			finderArgs = new Object[] {
				objectFieldId, start, end, orderByComparator
			};
		}

		List<ObjectFilter> list = null;

		if (useFinderCache) {
			list = (List<ObjectFilter>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (ObjectFilter objectFilter : list) {
					if (objectFieldId != objectFilter.getObjectFieldId()) {
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

			sb.append(_SQL_SELECT_OBJECTFILTER_WHERE);

			sb.append(_FINDER_COLUMN_OBJECTFIELDID_OBJECTFIELDID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(ObjectFilterModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(objectFieldId);

				list = (List<ObjectFilter>)QueryUtil.list(
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
	 * Returns the first object filter in the ordered set where objectFieldId = &#63;.
	 *
	 * @param objectFieldId the object field ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object filter
	 * @throws NoSuchObjectFilterException if a matching object filter could not be found
	 */
	@Override
	public ObjectFilter findByObjectFieldId_First(
			long objectFieldId,
			OrderByComparator<ObjectFilter> orderByComparator)
		throws NoSuchObjectFilterException {

		ObjectFilter objectFilter = fetchByObjectFieldId_First(
			objectFieldId, orderByComparator);

		if (objectFilter != null) {
			return objectFilter;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("objectFieldId=");
		sb.append(objectFieldId);

		sb.append("}");

		throw new NoSuchObjectFilterException(sb.toString());
	}

	/**
	 * Returns the first object filter in the ordered set where objectFieldId = &#63;.
	 *
	 * @param objectFieldId the object field ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object filter, or <code>null</code> if a matching object filter could not be found
	 */
	@Override
	public ObjectFilter fetchByObjectFieldId_First(
		long objectFieldId, OrderByComparator<ObjectFilter> orderByComparator) {

		List<ObjectFilter> list = findByObjectFieldId(
			objectFieldId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last object filter in the ordered set where objectFieldId = &#63;.
	 *
	 * @param objectFieldId the object field ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object filter
	 * @throws NoSuchObjectFilterException if a matching object filter could not be found
	 */
	@Override
	public ObjectFilter findByObjectFieldId_Last(
			long objectFieldId,
			OrderByComparator<ObjectFilter> orderByComparator)
		throws NoSuchObjectFilterException {

		ObjectFilter objectFilter = fetchByObjectFieldId_Last(
			objectFieldId, orderByComparator);

		if (objectFilter != null) {
			return objectFilter;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("objectFieldId=");
		sb.append(objectFieldId);

		sb.append("}");

		throw new NoSuchObjectFilterException(sb.toString());
	}

	/**
	 * Returns the last object filter in the ordered set where objectFieldId = &#63;.
	 *
	 * @param objectFieldId the object field ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object filter, or <code>null</code> if a matching object filter could not be found
	 */
	@Override
	public ObjectFilter fetchByObjectFieldId_Last(
		long objectFieldId, OrderByComparator<ObjectFilter> orderByComparator) {

		int count = countByObjectFieldId(objectFieldId);

		if (count == 0) {
			return null;
		}

		List<ObjectFilter> list = findByObjectFieldId(
			objectFieldId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the object filters before and after the current object filter in the ordered set where objectFieldId = &#63;.
	 *
	 * @param objectFilterId the primary key of the current object filter
	 * @param objectFieldId the object field ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next object filter
	 * @throws NoSuchObjectFilterException if a object filter with the primary key could not be found
	 */
	@Override
	public ObjectFilter[] findByObjectFieldId_PrevAndNext(
			long objectFilterId, long objectFieldId,
			OrderByComparator<ObjectFilter> orderByComparator)
		throws NoSuchObjectFilterException {

		ObjectFilter objectFilter = findByPrimaryKey(objectFilterId);

		Session session = null;

		try {
			session = openSession();

			ObjectFilter[] array = new ObjectFilterImpl[3];

			array[0] = getByObjectFieldId_PrevAndNext(
				session, objectFilter, objectFieldId, orderByComparator, true);

			array[1] = objectFilter;

			array[2] = getByObjectFieldId_PrevAndNext(
				session, objectFilter, objectFieldId, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected ObjectFilter getByObjectFieldId_PrevAndNext(
		Session session, ObjectFilter objectFilter, long objectFieldId,
		OrderByComparator<ObjectFilter> orderByComparator, boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(3);
		}

		sb.append(_SQL_SELECT_OBJECTFILTER_WHERE);

		sb.append(_FINDER_COLUMN_OBJECTFIELDID_OBJECTFIELDID_2);

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
			sb.append(ObjectFilterModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(objectFieldId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(objectFilter)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<ObjectFilter> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the object filters where objectFieldId = &#63; from the database.
	 *
	 * @param objectFieldId the object field ID
	 */
	@Override
	public void removeByObjectFieldId(long objectFieldId) {
		for (ObjectFilter objectFilter :
				findByObjectFieldId(
					objectFieldId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(objectFilter);
		}
	}

	/**
	 * Returns the number of object filters where objectFieldId = &#63;.
	 *
	 * @param objectFieldId the object field ID
	 * @return the number of matching object filters
	 */
	@Override
	public int countByObjectFieldId(long objectFieldId) {
		FinderPath finderPath = _finderPathCountByObjectFieldId;

		Object[] finderArgs = new Object[] {objectFieldId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_OBJECTFILTER_WHERE);

			sb.append(_FINDER_COLUMN_OBJECTFIELDID_OBJECTFIELDID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(objectFieldId);

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

	private static final String _FINDER_COLUMN_OBJECTFIELDID_OBJECTFIELDID_2 =
		"objectFilter.objectFieldId = ?";

	public ObjectFilterPersistenceImpl() {
		Map<String, String> dbColumnNames = new HashMap<String, String>();

		dbColumnNames.put("uuid", "uuid_");

		setDBColumnNames(dbColumnNames);

		setModelClass(ObjectFilter.class);

		setModelImplClass(ObjectFilterImpl.class);
		setModelPKClass(long.class);

		setTable(ObjectFilterTable.INSTANCE);
	}

	/**
	 * Caches the object filter in the entity cache if it is enabled.
	 *
	 * @param objectFilter the object filter
	 */
	@Override
	public void cacheResult(ObjectFilter objectFilter) {
		entityCache.putResult(
			ObjectFilterImpl.class, objectFilter.getPrimaryKey(), objectFilter);
	}

	private int _valueObjectFinderCacheListThreshold;

	/**
	 * Caches the object filters in the entity cache if it is enabled.
	 *
	 * @param objectFilters the object filters
	 */
	@Override
	public void cacheResult(List<ObjectFilter> objectFilters) {
		if ((_valueObjectFinderCacheListThreshold == 0) ||
			((_valueObjectFinderCacheListThreshold > 0) &&
			 (objectFilters.size() > _valueObjectFinderCacheListThreshold))) {

			return;
		}

		for (ObjectFilter objectFilter : objectFilters) {
			if (entityCache.getResult(
					ObjectFilterImpl.class, objectFilter.getPrimaryKey()) ==
						null) {

				cacheResult(objectFilter);
			}
		}
	}

	/**
	 * Clears the cache for all object filters.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(ObjectFilterImpl.class);

		finderCache.clearCache(ObjectFilterImpl.class);
	}

	/**
	 * Clears the cache for the object filter.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(ObjectFilter objectFilter) {
		entityCache.removeResult(ObjectFilterImpl.class, objectFilter);
	}

	@Override
	public void clearCache(List<ObjectFilter> objectFilters) {
		for (ObjectFilter objectFilter : objectFilters) {
			entityCache.removeResult(ObjectFilterImpl.class, objectFilter);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(ObjectFilterImpl.class);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(ObjectFilterImpl.class, primaryKey);
		}
	}

	/**
	 * Creates a new object filter with the primary key. Does not add the object filter to the database.
	 *
	 * @param objectFilterId the primary key for the new object filter
	 * @return the new object filter
	 */
	@Override
	public ObjectFilter create(long objectFilterId) {
		ObjectFilter objectFilter = new ObjectFilterImpl();

		objectFilter.setNew(true);
		objectFilter.setPrimaryKey(objectFilterId);

		String uuid = _portalUUID.generate();

		objectFilter.setUuid(uuid);

		objectFilter.setCompanyId(CompanyThreadLocal.getCompanyId());

		return objectFilter;
	}

	/**
	 * Removes the object filter with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param objectFilterId the primary key of the object filter
	 * @return the object filter that was removed
	 * @throws NoSuchObjectFilterException if a object filter with the primary key could not be found
	 */
	@Override
	public ObjectFilter remove(long objectFilterId)
		throws NoSuchObjectFilterException {

		return remove((Serializable)objectFilterId);
	}

	/**
	 * Removes the object filter with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the object filter
	 * @return the object filter that was removed
	 * @throws NoSuchObjectFilterException if a object filter with the primary key could not be found
	 */
	@Override
	public ObjectFilter remove(Serializable primaryKey)
		throws NoSuchObjectFilterException {

		Session session = null;

		try {
			session = openSession();

			ObjectFilter objectFilter = (ObjectFilter)session.get(
				ObjectFilterImpl.class, primaryKey);

			if (objectFilter == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchObjectFilterException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(objectFilter);
		}
		catch (NoSuchObjectFilterException noSuchEntityException) {
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
	protected ObjectFilter removeImpl(ObjectFilter objectFilter) {
		Session session = null;

		try {
			session = openSession();

			if (!session.contains(objectFilter)) {
				objectFilter = (ObjectFilter)session.get(
					ObjectFilterImpl.class, objectFilter.getPrimaryKeyObj());
			}

			if (objectFilter != null) {
				session.delete(objectFilter);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (objectFilter != null) {
			clearCache(objectFilter);
		}

		return objectFilter;
	}

	@Override
	public ObjectFilter updateImpl(ObjectFilter objectFilter) {
		boolean isNew = objectFilter.isNew();

		if (!(objectFilter instanceof ObjectFilterModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(objectFilter.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					objectFilter);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in objectFilter proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom ObjectFilter implementation " +
					objectFilter.getClass());
		}

		ObjectFilterModelImpl objectFilterModelImpl =
			(ObjectFilterModelImpl)objectFilter;

		if (Validator.isNull(objectFilter.getUuid())) {
			String uuid = _portalUUID.generate();

			objectFilter.setUuid(uuid);
		}

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Date date = new Date();

		if (isNew && (objectFilter.getCreateDate() == null)) {
			if (serviceContext == null) {
				objectFilter.setCreateDate(date);
			}
			else {
				objectFilter.setCreateDate(serviceContext.getCreateDate(date));
			}
		}

		if (!objectFilterModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				objectFilter.setModifiedDate(date);
			}
			else {
				objectFilter.setModifiedDate(
					serviceContext.getModifiedDate(date));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (isNew) {
				session.save(objectFilter);
			}
			else {
				objectFilter = (ObjectFilter)session.merge(objectFilter);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		entityCache.putResult(
			ObjectFilterImpl.class, objectFilterModelImpl, false, true);

		if (isNew) {
			objectFilter.setNew(false);
		}

		objectFilter.resetOriginalValues();

		return objectFilter;
	}

	/**
	 * Returns the object filter with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the object filter
	 * @return the object filter
	 * @throws NoSuchObjectFilterException if a object filter with the primary key could not be found
	 */
	@Override
	public ObjectFilter findByPrimaryKey(Serializable primaryKey)
		throws NoSuchObjectFilterException {

		ObjectFilter objectFilter = fetchByPrimaryKey(primaryKey);

		if (objectFilter == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchObjectFilterException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return objectFilter;
	}

	/**
	 * Returns the object filter with the primary key or throws a <code>NoSuchObjectFilterException</code> if it could not be found.
	 *
	 * @param objectFilterId the primary key of the object filter
	 * @return the object filter
	 * @throws NoSuchObjectFilterException if a object filter with the primary key could not be found
	 */
	@Override
	public ObjectFilter findByPrimaryKey(long objectFilterId)
		throws NoSuchObjectFilterException {

		return findByPrimaryKey((Serializable)objectFilterId);
	}

	/**
	 * Returns the object filter with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param objectFilterId the primary key of the object filter
	 * @return the object filter, or <code>null</code> if a object filter with the primary key could not be found
	 */
	@Override
	public ObjectFilter fetchByPrimaryKey(long objectFilterId) {
		return fetchByPrimaryKey((Serializable)objectFilterId);
	}

	/**
	 * Returns all the object filters.
	 *
	 * @return the object filters
	 */
	@Override
	public List<ObjectFilter> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the object filters.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectFilterModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of object filters
	 * @param end the upper bound of the range of object filters (not inclusive)
	 * @return the range of object filters
	 */
	@Override
	public List<ObjectFilter> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the object filters.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectFilterModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of object filters
	 * @param end the upper bound of the range of object filters (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of object filters
	 */
	@Override
	public List<ObjectFilter> findAll(
		int start, int end, OrderByComparator<ObjectFilter> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the object filters.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectFilterModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of object filters
	 * @param end the upper bound of the range of object filters (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of object filters
	 */
	@Override
	public List<ObjectFilter> findAll(
		int start, int end, OrderByComparator<ObjectFilter> orderByComparator,
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

		List<ObjectFilter> list = null;

		if (useFinderCache) {
			list = (List<ObjectFilter>)finderCache.getResult(
				finderPath, finderArgs, this);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_OBJECTFILTER);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_OBJECTFILTER;

				sql = sql.concat(ObjectFilterModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<ObjectFilter>)QueryUtil.list(
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
	 * Removes all the object filters from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (ObjectFilter objectFilter : findAll()) {
			remove(objectFilter);
		}
	}

	/**
	 * Returns the number of object filters.
	 *
	 * @return the number of object filters
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(
			_finderPathCountAll, FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(_SQL_COUNT_OBJECTFILTER);

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
		return "objectFilterId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_OBJECTFILTER;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return ObjectFilterModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the object filter persistence.
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

		_finderPathWithPaginationFindByObjectFieldId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByObjectFieldId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"objectFieldId"}, true);

		_finderPathWithoutPaginationFindByObjectFieldId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByObjectFieldId",
			new String[] {Long.class.getName()}, new String[] {"objectFieldId"},
			true);

		_finderPathCountByObjectFieldId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByObjectFieldId",
			new String[] {Long.class.getName()}, new String[] {"objectFieldId"},
			false);

		ObjectFilterUtil.setPersistence(this);
	}

	@Deactivate
	public void deactivate() {
		ObjectFilterUtil.setPersistence(null);

		entityCache.removeCache(ObjectFilterImpl.class.getName());
	}

	@Override
	@Reference(
		target = ObjectPersistenceConstants.SERVICE_CONFIGURATION_FILTER,
		unbind = "-"
	)
	public void setConfiguration(Configuration configuration) {
	}

	@Override
	@Reference(
		target = ObjectPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setDataSource(DataSource dataSource) {
		super.setDataSource(dataSource);
	}

	@Override
	@Reference(
		target = ObjectPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setSessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	@Reference
	protected EntityCache entityCache;

	@Reference
	protected FinderCache finderCache;

	private static final String _SQL_SELECT_OBJECTFILTER =
		"SELECT objectFilter FROM ObjectFilter objectFilter";

	private static final String _SQL_SELECT_OBJECTFILTER_WHERE =
		"SELECT objectFilter FROM ObjectFilter objectFilter WHERE ";

	private static final String _SQL_COUNT_OBJECTFILTER =
		"SELECT COUNT(objectFilter) FROM ObjectFilter objectFilter";

	private static final String _SQL_COUNT_OBJECTFILTER_WHERE =
		"SELECT COUNT(objectFilter) FROM ObjectFilter objectFilter WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS = "objectFilter.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No ObjectFilter exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No ObjectFilter exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		ObjectFilterPersistenceImpl.class);

	private static final Set<String> _badColumnNames = SetUtil.fromArray(
		new String[] {"uuid"});

	@Override
	protected FinderCache getFinderCache() {
		return finderCache;
	}

	@Reference
	private PortalUUID _portalUUID;

}