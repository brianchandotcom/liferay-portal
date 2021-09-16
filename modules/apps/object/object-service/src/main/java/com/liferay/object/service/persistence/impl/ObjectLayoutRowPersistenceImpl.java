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

import com.liferay.object.exception.NoSuchObjectLayoutRowException;
import com.liferay.object.model.ObjectLayoutRow;
import com.liferay.object.model.ObjectLayoutRowTable;
import com.liferay.object.model.impl.ObjectLayoutRowImpl;
import com.liferay.object.model.impl.ObjectLayoutRowModelImpl;
import com.liferay.object.service.persistence.ObjectLayoutRowPersistence;
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
 * The persistence implementation for the object layout row service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Marco Leo
 * @generated
 */
@Component(service = {ObjectLayoutRowPersistence.class, BasePersistence.class})
public class ObjectLayoutRowPersistenceImpl
	extends BasePersistenceImpl<ObjectLayoutRow>
	implements ObjectLayoutRowPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>ObjectLayoutRowUtil</code> to access the object layout row persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		ObjectLayoutRowImpl.class.getName();

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
	 * Returns all the object layout rows where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching object layout rows
	 */
	@Override
	public List<ObjectLayoutRow> findByUuid(String uuid) {
		return findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the object layout rows where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectLayoutRowModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of object layout rows
	 * @param end the upper bound of the range of object layout rows (not inclusive)
	 * @return the range of matching object layout rows
	 */
	@Override
	public List<ObjectLayoutRow> findByUuid(String uuid, int start, int end) {
		return findByUuid(uuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the object layout rows where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectLayoutRowModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of object layout rows
	 * @param end the upper bound of the range of object layout rows (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching object layout rows
	 */
	@Override
	public List<ObjectLayoutRow> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<ObjectLayoutRow> orderByComparator) {

		return findByUuid(uuid, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the object layout rows where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectLayoutRowModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of object layout rows
	 * @param end the upper bound of the range of object layout rows (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching object layout rows
	 */
	@Override
	public List<ObjectLayoutRow> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<ObjectLayoutRow> orderByComparator,
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

		List<ObjectLayoutRow> list = null;

		if (useFinderCache) {
			list = (List<ObjectLayoutRow>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (ObjectLayoutRow objectLayoutRow : list) {
					if (!uuid.equals(objectLayoutRow.getUuid())) {
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

			sb.append(_SQL_SELECT_OBJECTLAYOUTROW_WHERE);

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
				sb.append(ObjectLayoutRowModelImpl.ORDER_BY_JPQL);
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

				list = (List<ObjectLayoutRow>)QueryUtil.list(
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
	 * Returns the first object layout row in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object layout row
	 * @throws NoSuchObjectLayoutRowException if a matching object layout row could not be found
	 */
	@Override
	public ObjectLayoutRow findByUuid_First(
			String uuid, OrderByComparator<ObjectLayoutRow> orderByComparator)
		throws NoSuchObjectLayoutRowException {

		ObjectLayoutRow objectLayoutRow = fetchByUuid_First(
			uuid, orderByComparator);

		if (objectLayoutRow != null) {
			return objectLayoutRow;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append("}");

		throw new NoSuchObjectLayoutRowException(sb.toString());
	}

	/**
	 * Returns the first object layout row in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object layout row, or <code>null</code> if a matching object layout row could not be found
	 */
	@Override
	public ObjectLayoutRow fetchByUuid_First(
		String uuid, OrderByComparator<ObjectLayoutRow> orderByComparator) {

		List<ObjectLayoutRow> list = findByUuid(uuid, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last object layout row in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object layout row
	 * @throws NoSuchObjectLayoutRowException if a matching object layout row could not be found
	 */
	@Override
	public ObjectLayoutRow findByUuid_Last(
			String uuid, OrderByComparator<ObjectLayoutRow> orderByComparator)
		throws NoSuchObjectLayoutRowException {

		ObjectLayoutRow objectLayoutRow = fetchByUuid_Last(
			uuid, orderByComparator);

		if (objectLayoutRow != null) {
			return objectLayoutRow;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append("}");

		throw new NoSuchObjectLayoutRowException(sb.toString());
	}

	/**
	 * Returns the last object layout row in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object layout row, or <code>null</code> if a matching object layout row could not be found
	 */
	@Override
	public ObjectLayoutRow fetchByUuid_Last(
		String uuid, OrderByComparator<ObjectLayoutRow> orderByComparator) {

		int count = countByUuid(uuid);

		if (count == 0) {
			return null;
		}

		List<ObjectLayoutRow> list = findByUuid(
			uuid, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the object layout rows before and after the current object layout row in the ordered set where uuid = &#63;.
	 *
	 * @param objectLayoutRowId the primary key of the current object layout row
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next object layout row
	 * @throws NoSuchObjectLayoutRowException if a object layout row with the primary key could not be found
	 */
	@Override
	public ObjectLayoutRow[] findByUuid_PrevAndNext(
			long objectLayoutRowId, String uuid,
			OrderByComparator<ObjectLayoutRow> orderByComparator)
		throws NoSuchObjectLayoutRowException {

		uuid = Objects.toString(uuid, "");

		ObjectLayoutRow objectLayoutRow = findByPrimaryKey(objectLayoutRowId);

		Session session = null;

		try {
			session = openSession();

			ObjectLayoutRow[] array = new ObjectLayoutRowImpl[3];

			array[0] = getByUuid_PrevAndNext(
				session, objectLayoutRow, uuid, orderByComparator, true);

			array[1] = objectLayoutRow;

			array[2] = getByUuid_PrevAndNext(
				session, objectLayoutRow, uuid, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected ObjectLayoutRow getByUuid_PrevAndNext(
		Session session, ObjectLayoutRow objectLayoutRow, String uuid,
		OrderByComparator<ObjectLayoutRow> orderByComparator,
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

		sb.append(_SQL_SELECT_OBJECTLAYOUTROW_WHERE);

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
			sb.append(ObjectLayoutRowModelImpl.ORDER_BY_JPQL);
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
						objectLayoutRow)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<ObjectLayoutRow> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the object layout rows where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	@Override
	public void removeByUuid(String uuid) {
		for (ObjectLayoutRow objectLayoutRow :
				findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(objectLayoutRow);
		}
	}

	/**
	 * Returns the number of object layout rows where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching object layout rows
	 */
	@Override
	public int countByUuid(String uuid) {
		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = _finderPathCountByUuid;

		Object[] finderArgs = new Object[] {uuid};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_OBJECTLAYOUTROW_WHERE);

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
		"objectLayoutRow.uuid = ?";

	private static final String _FINDER_COLUMN_UUID_UUID_3 =
		"(objectLayoutRow.uuid IS NULL OR objectLayoutRow.uuid = '')";

	private FinderPath _finderPathWithPaginationFindByUuid_C;
	private FinderPath _finderPathWithoutPaginationFindByUuid_C;
	private FinderPath _finderPathCountByUuid_C;

	/**
	 * Returns all the object layout rows where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching object layout rows
	 */
	@Override
	public List<ObjectLayoutRow> findByUuid_C(String uuid, long companyId) {
		return findByUuid_C(
			uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the object layout rows where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectLayoutRowModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of object layout rows
	 * @param end the upper bound of the range of object layout rows (not inclusive)
	 * @return the range of matching object layout rows
	 */
	@Override
	public List<ObjectLayoutRow> findByUuid_C(
		String uuid, long companyId, int start, int end) {

		return findByUuid_C(uuid, companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the object layout rows where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectLayoutRowModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of object layout rows
	 * @param end the upper bound of the range of object layout rows (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching object layout rows
	 */
	@Override
	public List<ObjectLayoutRow> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<ObjectLayoutRow> orderByComparator) {

		return findByUuid_C(
			uuid, companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the object layout rows where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectLayoutRowModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of object layout rows
	 * @param end the upper bound of the range of object layout rows (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching object layout rows
	 */
	@Override
	public List<ObjectLayoutRow> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<ObjectLayoutRow> orderByComparator,
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

		List<ObjectLayoutRow> list = null;

		if (useFinderCache) {
			list = (List<ObjectLayoutRow>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (ObjectLayoutRow objectLayoutRow : list) {
					if (!uuid.equals(objectLayoutRow.getUuid()) ||
						(companyId != objectLayoutRow.getCompanyId())) {

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

			sb.append(_SQL_SELECT_OBJECTLAYOUTROW_WHERE);

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
				sb.append(ObjectLayoutRowModelImpl.ORDER_BY_JPQL);
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

				list = (List<ObjectLayoutRow>)QueryUtil.list(
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
	 * Returns the first object layout row in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object layout row
	 * @throws NoSuchObjectLayoutRowException if a matching object layout row could not be found
	 */
	@Override
	public ObjectLayoutRow findByUuid_C_First(
			String uuid, long companyId,
			OrderByComparator<ObjectLayoutRow> orderByComparator)
		throws NoSuchObjectLayoutRowException {

		ObjectLayoutRow objectLayoutRow = fetchByUuid_C_First(
			uuid, companyId, orderByComparator);

		if (objectLayoutRow != null) {
			return objectLayoutRow;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append(", companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchObjectLayoutRowException(sb.toString());
	}

	/**
	 * Returns the first object layout row in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object layout row, or <code>null</code> if a matching object layout row could not be found
	 */
	@Override
	public ObjectLayoutRow fetchByUuid_C_First(
		String uuid, long companyId,
		OrderByComparator<ObjectLayoutRow> orderByComparator) {

		List<ObjectLayoutRow> list = findByUuid_C(
			uuid, companyId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last object layout row in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object layout row
	 * @throws NoSuchObjectLayoutRowException if a matching object layout row could not be found
	 */
	@Override
	public ObjectLayoutRow findByUuid_C_Last(
			String uuid, long companyId,
			OrderByComparator<ObjectLayoutRow> orderByComparator)
		throws NoSuchObjectLayoutRowException {

		ObjectLayoutRow objectLayoutRow = fetchByUuid_C_Last(
			uuid, companyId, orderByComparator);

		if (objectLayoutRow != null) {
			return objectLayoutRow;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append(", companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchObjectLayoutRowException(sb.toString());
	}

	/**
	 * Returns the last object layout row in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object layout row, or <code>null</code> if a matching object layout row could not be found
	 */
	@Override
	public ObjectLayoutRow fetchByUuid_C_Last(
		String uuid, long companyId,
		OrderByComparator<ObjectLayoutRow> orderByComparator) {

		int count = countByUuid_C(uuid, companyId);

		if (count == 0) {
			return null;
		}

		List<ObjectLayoutRow> list = findByUuid_C(
			uuid, companyId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the object layout rows before and after the current object layout row in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param objectLayoutRowId the primary key of the current object layout row
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next object layout row
	 * @throws NoSuchObjectLayoutRowException if a object layout row with the primary key could not be found
	 */
	@Override
	public ObjectLayoutRow[] findByUuid_C_PrevAndNext(
			long objectLayoutRowId, String uuid, long companyId,
			OrderByComparator<ObjectLayoutRow> orderByComparator)
		throws NoSuchObjectLayoutRowException {

		uuid = Objects.toString(uuid, "");

		ObjectLayoutRow objectLayoutRow = findByPrimaryKey(objectLayoutRowId);

		Session session = null;

		try {
			session = openSession();

			ObjectLayoutRow[] array = new ObjectLayoutRowImpl[3];

			array[0] = getByUuid_C_PrevAndNext(
				session, objectLayoutRow, uuid, companyId, orderByComparator,
				true);

			array[1] = objectLayoutRow;

			array[2] = getByUuid_C_PrevAndNext(
				session, objectLayoutRow, uuid, companyId, orderByComparator,
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

	protected ObjectLayoutRow getByUuid_C_PrevAndNext(
		Session session, ObjectLayoutRow objectLayoutRow, String uuid,
		long companyId, OrderByComparator<ObjectLayoutRow> orderByComparator,
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

		sb.append(_SQL_SELECT_OBJECTLAYOUTROW_WHERE);

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
			sb.append(ObjectLayoutRowModelImpl.ORDER_BY_JPQL);
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
						objectLayoutRow)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<ObjectLayoutRow> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the object layout rows where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	@Override
	public void removeByUuid_C(String uuid, long companyId) {
		for (ObjectLayoutRow objectLayoutRow :
				findByUuid_C(
					uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(objectLayoutRow);
		}
	}

	/**
	 * Returns the number of object layout rows where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching object layout rows
	 */
	@Override
	public int countByUuid_C(String uuid, long companyId) {
		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = _finderPathCountByUuid_C;

		Object[] finderArgs = new Object[] {uuid, companyId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_OBJECTLAYOUTROW_WHERE);

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
		"objectLayoutRow.uuid = ? AND ";

	private static final String _FINDER_COLUMN_UUID_C_UUID_3 =
		"(objectLayoutRow.uuid IS NULL OR objectLayoutRow.uuid = '') AND ";

	private static final String _FINDER_COLUMN_UUID_C_COMPANYID_2 =
		"objectLayoutRow.companyId = ?";

	private FinderPath _finderPathWithPaginationFindByObjectLayoutBoxId;
	private FinderPath _finderPathWithoutPaginationFindByObjectLayoutBoxId;
	private FinderPath _finderPathCountByObjectLayoutBoxId;

	/**
	 * Returns all the object layout rows where objectLayoutBoxId = &#63;.
	 *
	 * @param objectLayoutBoxId the object layout box ID
	 * @return the matching object layout rows
	 */
	@Override
	public List<ObjectLayoutRow> findByObjectLayoutBoxId(
		long objectLayoutBoxId) {

		return findByObjectLayoutBoxId(
			objectLayoutBoxId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the object layout rows where objectLayoutBoxId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectLayoutRowModelImpl</code>.
	 * </p>
	 *
	 * @param objectLayoutBoxId the object layout box ID
	 * @param start the lower bound of the range of object layout rows
	 * @param end the upper bound of the range of object layout rows (not inclusive)
	 * @return the range of matching object layout rows
	 */
	@Override
	public List<ObjectLayoutRow> findByObjectLayoutBoxId(
		long objectLayoutBoxId, int start, int end) {

		return findByObjectLayoutBoxId(objectLayoutBoxId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the object layout rows where objectLayoutBoxId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectLayoutRowModelImpl</code>.
	 * </p>
	 *
	 * @param objectLayoutBoxId the object layout box ID
	 * @param start the lower bound of the range of object layout rows
	 * @param end the upper bound of the range of object layout rows (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching object layout rows
	 */
	@Override
	public List<ObjectLayoutRow> findByObjectLayoutBoxId(
		long objectLayoutBoxId, int start, int end,
		OrderByComparator<ObjectLayoutRow> orderByComparator) {

		return findByObjectLayoutBoxId(
			objectLayoutBoxId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the object layout rows where objectLayoutBoxId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectLayoutRowModelImpl</code>.
	 * </p>
	 *
	 * @param objectLayoutBoxId the object layout box ID
	 * @param start the lower bound of the range of object layout rows
	 * @param end the upper bound of the range of object layout rows (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching object layout rows
	 */
	@Override
	public List<ObjectLayoutRow> findByObjectLayoutBoxId(
		long objectLayoutBoxId, int start, int end,
		OrderByComparator<ObjectLayoutRow> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath =
					_finderPathWithoutPaginationFindByObjectLayoutBoxId;
				finderArgs = new Object[] {objectLayoutBoxId};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByObjectLayoutBoxId;
			finderArgs = new Object[] {
				objectLayoutBoxId, start, end, orderByComparator
			};
		}

		List<ObjectLayoutRow> list = null;

		if (useFinderCache) {
			list = (List<ObjectLayoutRow>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (ObjectLayoutRow objectLayoutRow : list) {
					if (objectLayoutBoxId !=
							objectLayoutRow.getObjectLayoutBoxId()) {

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

			sb.append(_SQL_SELECT_OBJECTLAYOUTROW_WHERE);

			sb.append(_FINDER_COLUMN_OBJECTLAYOUTBOXID_OBJECTLAYOUTBOXID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(ObjectLayoutRowModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(objectLayoutBoxId);

				list = (List<ObjectLayoutRow>)QueryUtil.list(
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
	 * Returns the first object layout row in the ordered set where objectLayoutBoxId = &#63;.
	 *
	 * @param objectLayoutBoxId the object layout box ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object layout row
	 * @throws NoSuchObjectLayoutRowException if a matching object layout row could not be found
	 */
	@Override
	public ObjectLayoutRow findByObjectLayoutBoxId_First(
			long objectLayoutBoxId,
			OrderByComparator<ObjectLayoutRow> orderByComparator)
		throws NoSuchObjectLayoutRowException {

		ObjectLayoutRow objectLayoutRow = fetchByObjectLayoutBoxId_First(
			objectLayoutBoxId, orderByComparator);

		if (objectLayoutRow != null) {
			return objectLayoutRow;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("objectLayoutBoxId=");
		sb.append(objectLayoutBoxId);

		sb.append("}");

		throw new NoSuchObjectLayoutRowException(sb.toString());
	}

	/**
	 * Returns the first object layout row in the ordered set where objectLayoutBoxId = &#63;.
	 *
	 * @param objectLayoutBoxId the object layout box ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object layout row, or <code>null</code> if a matching object layout row could not be found
	 */
	@Override
	public ObjectLayoutRow fetchByObjectLayoutBoxId_First(
		long objectLayoutBoxId,
		OrderByComparator<ObjectLayoutRow> orderByComparator) {

		List<ObjectLayoutRow> list = findByObjectLayoutBoxId(
			objectLayoutBoxId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last object layout row in the ordered set where objectLayoutBoxId = &#63;.
	 *
	 * @param objectLayoutBoxId the object layout box ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object layout row
	 * @throws NoSuchObjectLayoutRowException if a matching object layout row could not be found
	 */
	@Override
	public ObjectLayoutRow findByObjectLayoutBoxId_Last(
			long objectLayoutBoxId,
			OrderByComparator<ObjectLayoutRow> orderByComparator)
		throws NoSuchObjectLayoutRowException {

		ObjectLayoutRow objectLayoutRow = fetchByObjectLayoutBoxId_Last(
			objectLayoutBoxId, orderByComparator);

		if (objectLayoutRow != null) {
			return objectLayoutRow;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("objectLayoutBoxId=");
		sb.append(objectLayoutBoxId);

		sb.append("}");

		throw new NoSuchObjectLayoutRowException(sb.toString());
	}

	/**
	 * Returns the last object layout row in the ordered set where objectLayoutBoxId = &#63;.
	 *
	 * @param objectLayoutBoxId the object layout box ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object layout row, or <code>null</code> if a matching object layout row could not be found
	 */
	@Override
	public ObjectLayoutRow fetchByObjectLayoutBoxId_Last(
		long objectLayoutBoxId,
		OrderByComparator<ObjectLayoutRow> orderByComparator) {

		int count = countByObjectLayoutBoxId(objectLayoutBoxId);

		if (count == 0) {
			return null;
		}

		List<ObjectLayoutRow> list = findByObjectLayoutBoxId(
			objectLayoutBoxId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the object layout rows before and after the current object layout row in the ordered set where objectLayoutBoxId = &#63;.
	 *
	 * @param objectLayoutRowId the primary key of the current object layout row
	 * @param objectLayoutBoxId the object layout box ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next object layout row
	 * @throws NoSuchObjectLayoutRowException if a object layout row with the primary key could not be found
	 */
	@Override
	public ObjectLayoutRow[] findByObjectLayoutBoxId_PrevAndNext(
			long objectLayoutRowId, long objectLayoutBoxId,
			OrderByComparator<ObjectLayoutRow> orderByComparator)
		throws NoSuchObjectLayoutRowException {

		ObjectLayoutRow objectLayoutRow = findByPrimaryKey(objectLayoutRowId);

		Session session = null;

		try {
			session = openSession();

			ObjectLayoutRow[] array = new ObjectLayoutRowImpl[3];

			array[0] = getByObjectLayoutBoxId_PrevAndNext(
				session, objectLayoutRow, objectLayoutBoxId, orderByComparator,
				true);

			array[1] = objectLayoutRow;

			array[2] = getByObjectLayoutBoxId_PrevAndNext(
				session, objectLayoutRow, objectLayoutBoxId, orderByComparator,
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

	protected ObjectLayoutRow getByObjectLayoutBoxId_PrevAndNext(
		Session session, ObjectLayoutRow objectLayoutRow,
		long objectLayoutBoxId,
		OrderByComparator<ObjectLayoutRow> orderByComparator,
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

		sb.append(_SQL_SELECT_OBJECTLAYOUTROW_WHERE);

		sb.append(_FINDER_COLUMN_OBJECTLAYOUTBOXID_OBJECTLAYOUTBOXID_2);

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
			sb.append(ObjectLayoutRowModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(objectLayoutBoxId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						objectLayoutRow)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<ObjectLayoutRow> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the object layout rows where objectLayoutBoxId = &#63; from the database.
	 *
	 * @param objectLayoutBoxId the object layout box ID
	 */
	@Override
	public void removeByObjectLayoutBoxId(long objectLayoutBoxId) {
		for (ObjectLayoutRow objectLayoutRow :
				findByObjectLayoutBoxId(
					objectLayoutBoxId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(objectLayoutRow);
		}
	}

	/**
	 * Returns the number of object layout rows where objectLayoutBoxId = &#63;.
	 *
	 * @param objectLayoutBoxId the object layout box ID
	 * @return the number of matching object layout rows
	 */
	@Override
	public int countByObjectLayoutBoxId(long objectLayoutBoxId) {
		FinderPath finderPath = _finderPathCountByObjectLayoutBoxId;

		Object[] finderArgs = new Object[] {objectLayoutBoxId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_OBJECTLAYOUTROW_WHERE);

			sb.append(_FINDER_COLUMN_OBJECTLAYOUTBOXID_OBJECTLAYOUTBOXID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(objectLayoutBoxId);

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
		_FINDER_COLUMN_OBJECTLAYOUTBOXID_OBJECTLAYOUTBOXID_2 =
			"objectLayoutRow.objectLayoutBoxId = ?";

	public ObjectLayoutRowPersistenceImpl() {
		Map<String, String> dbColumnNames = new HashMap<String, String>();

		dbColumnNames.put("uuid", "uuid_");

		setDBColumnNames(dbColumnNames);

		setModelClass(ObjectLayoutRow.class);

		setModelImplClass(ObjectLayoutRowImpl.class);
		setModelPKClass(long.class);

		setTable(ObjectLayoutRowTable.INSTANCE);
	}

	/**
	 * Caches the object layout row in the entity cache if it is enabled.
	 *
	 * @param objectLayoutRow the object layout row
	 */
	@Override
	public void cacheResult(ObjectLayoutRow objectLayoutRow) {
		entityCache.putResult(
			ObjectLayoutRowImpl.class, objectLayoutRow.getPrimaryKey(),
			objectLayoutRow);
	}

	private int _valueObjectFinderCacheListThreshold;

	/**
	 * Caches the object layout rows in the entity cache if it is enabled.
	 *
	 * @param objectLayoutRows the object layout rows
	 */
	@Override
	public void cacheResult(List<ObjectLayoutRow> objectLayoutRows) {
		if ((_valueObjectFinderCacheListThreshold == 0) ||
			((_valueObjectFinderCacheListThreshold > 0) &&
			 (objectLayoutRows.size() >
				 _valueObjectFinderCacheListThreshold))) {

			return;
		}

		for (ObjectLayoutRow objectLayoutRow : objectLayoutRows) {
			if (entityCache.getResult(
					ObjectLayoutRowImpl.class,
					objectLayoutRow.getPrimaryKey()) == null) {

				cacheResult(objectLayoutRow);
			}
		}
	}

	/**
	 * Clears the cache for all object layout rows.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(ObjectLayoutRowImpl.class);

		finderCache.clearCache(ObjectLayoutRowImpl.class);
	}

	/**
	 * Clears the cache for the object layout row.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(ObjectLayoutRow objectLayoutRow) {
		entityCache.removeResult(ObjectLayoutRowImpl.class, objectLayoutRow);
	}

	@Override
	public void clearCache(List<ObjectLayoutRow> objectLayoutRows) {
		for (ObjectLayoutRow objectLayoutRow : objectLayoutRows) {
			entityCache.removeResult(
				ObjectLayoutRowImpl.class, objectLayoutRow);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(ObjectLayoutRowImpl.class);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(ObjectLayoutRowImpl.class, primaryKey);
		}
	}

	/**
	 * Creates a new object layout row with the primary key. Does not add the object layout row to the database.
	 *
	 * @param objectLayoutRowId the primary key for the new object layout row
	 * @return the new object layout row
	 */
	@Override
	public ObjectLayoutRow create(long objectLayoutRowId) {
		ObjectLayoutRow objectLayoutRow = new ObjectLayoutRowImpl();

		objectLayoutRow.setNew(true);
		objectLayoutRow.setPrimaryKey(objectLayoutRowId);

		String uuid = PortalUUIDUtil.generate();

		objectLayoutRow.setUuid(uuid);

		objectLayoutRow.setCompanyId(CompanyThreadLocal.getCompanyId());

		return objectLayoutRow;
	}

	/**
	 * Removes the object layout row with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param objectLayoutRowId the primary key of the object layout row
	 * @return the object layout row that was removed
	 * @throws NoSuchObjectLayoutRowException if a object layout row with the primary key could not be found
	 */
	@Override
	public ObjectLayoutRow remove(long objectLayoutRowId)
		throws NoSuchObjectLayoutRowException {

		return remove((Serializable)objectLayoutRowId);
	}

	/**
	 * Removes the object layout row with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the object layout row
	 * @return the object layout row that was removed
	 * @throws NoSuchObjectLayoutRowException if a object layout row with the primary key could not be found
	 */
	@Override
	public ObjectLayoutRow remove(Serializable primaryKey)
		throws NoSuchObjectLayoutRowException {

		Session session = null;

		try {
			session = openSession();

			ObjectLayoutRow objectLayoutRow = (ObjectLayoutRow)session.get(
				ObjectLayoutRowImpl.class, primaryKey);

			if (objectLayoutRow == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchObjectLayoutRowException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(objectLayoutRow);
		}
		catch (NoSuchObjectLayoutRowException noSuchEntityException) {
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
	protected ObjectLayoutRow removeImpl(ObjectLayoutRow objectLayoutRow) {
		Session session = null;

		try {
			session = openSession();

			if (!session.contains(objectLayoutRow)) {
				objectLayoutRow = (ObjectLayoutRow)session.get(
					ObjectLayoutRowImpl.class,
					objectLayoutRow.getPrimaryKeyObj());
			}

			if (objectLayoutRow != null) {
				session.delete(objectLayoutRow);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (objectLayoutRow != null) {
			clearCache(objectLayoutRow);
		}

		return objectLayoutRow;
	}

	@Override
	public ObjectLayoutRow updateImpl(ObjectLayoutRow objectLayoutRow) {
		boolean isNew = objectLayoutRow.isNew();

		if (!(objectLayoutRow instanceof ObjectLayoutRowModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(objectLayoutRow.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					objectLayoutRow);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in objectLayoutRow proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom ObjectLayoutRow implementation " +
					objectLayoutRow.getClass());
		}

		ObjectLayoutRowModelImpl objectLayoutRowModelImpl =
			(ObjectLayoutRowModelImpl)objectLayoutRow;

		if (Validator.isNull(objectLayoutRow.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			objectLayoutRow.setUuid(uuid);
		}

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Date date = new Date();

		if (isNew && (objectLayoutRow.getCreateDate() == null)) {
			if (serviceContext == null) {
				objectLayoutRow.setCreateDate(date);
			}
			else {
				objectLayoutRow.setCreateDate(
					serviceContext.getCreateDate(date));
			}
		}

		if (!objectLayoutRowModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				objectLayoutRow.setModifiedDate(date);
			}
			else {
				objectLayoutRow.setModifiedDate(
					serviceContext.getModifiedDate(date));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (isNew) {
				session.save(objectLayoutRow);
			}
			else {
				objectLayoutRow = (ObjectLayoutRow)session.merge(
					objectLayoutRow);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		entityCache.putResult(
			ObjectLayoutRowImpl.class, objectLayoutRowModelImpl, false, true);

		if (isNew) {
			objectLayoutRow.setNew(false);
		}

		objectLayoutRow.resetOriginalValues();

		return objectLayoutRow;
	}

	/**
	 * Returns the object layout row with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the object layout row
	 * @return the object layout row
	 * @throws NoSuchObjectLayoutRowException if a object layout row with the primary key could not be found
	 */
	@Override
	public ObjectLayoutRow findByPrimaryKey(Serializable primaryKey)
		throws NoSuchObjectLayoutRowException {

		ObjectLayoutRow objectLayoutRow = fetchByPrimaryKey(primaryKey);

		if (objectLayoutRow == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchObjectLayoutRowException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return objectLayoutRow;
	}

	/**
	 * Returns the object layout row with the primary key or throws a <code>NoSuchObjectLayoutRowException</code> if it could not be found.
	 *
	 * @param objectLayoutRowId the primary key of the object layout row
	 * @return the object layout row
	 * @throws NoSuchObjectLayoutRowException if a object layout row with the primary key could not be found
	 */
	@Override
	public ObjectLayoutRow findByPrimaryKey(long objectLayoutRowId)
		throws NoSuchObjectLayoutRowException {

		return findByPrimaryKey((Serializable)objectLayoutRowId);
	}

	/**
	 * Returns the object layout row with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param objectLayoutRowId the primary key of the object layout row
	 * @return the object layout row, or <code>null</code> if a object layout row with the primary key could not be found
	 */
	@Override
	public ObjectLayoutRow fetchByPrimaryKey(long objectLayoutRowId) {
		return fetchByPrimaryKey((Serializable)objectLayoutRowId);
	}

	/**
	 * Returns all the object layout rows.
	 *
	 * @return the object layout rows
	 */
	@Override
	public List<ObjectLayoutRow> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the object layout rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectLayoutRowModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of object layout rows
	 * @param end the upper bound of the range of object layout rows (not inclusive)
	 * @return the range of object layout rows
	 */
	@Override
	public List<ObjectLayoutRow> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the object layout rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectLayoutRowModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of object layout rows
	 * @param end the upper bound of the range of object layout rows (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of object layout rows
	 */
	@Override
	public List<ObjectLayoutRow> findAll(
		int start, int end,
		OrderByComparator<ObjectLayoutRow> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the object layout rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectLayoutRowModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of object layout rows
	 * @param end the upper bound of the range of object layout rows (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of object layout rows
	 */
	@Override
	public List<ObjectLayoutRow> findAll(
		int start, int end,
		OrderByComparator<ObjectLayoutRow> orderByComparator,
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

		List<ObjectLayoutRow> list = null;

		if (useFinderCache) {
			list = (List<ObjectLayoutRow>)finderCache.getResult(
				finderPath, finderArgs);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_OBJECTLAYOUTROW);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_OBJECTLAYOUTROW;

				sql = sql.concat(ObjectLayoutRowModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<ObjectLayoutRow>)QueryUtil.list(
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
	 * Removes all the object layout rows from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (ObjectLayoutRow objectLayoutRow : findAll()) {
			remove(objectLayoutRow);
		}
	}

	/**
	 * Returns the number of object layout rows.
	 *
	 * @return the number of object layout rows
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(
			_finderPathCountAll, FINDER_ARGS_EMPTY);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(_SQL_COUNT_OBJECTLAYOUTROW);

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
		return "objectLayoutRowId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_OBJECTLAYOUTROW;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return ObjectLayoutRowModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the object layout row persistence.
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

		_finderPathWithPaginationFindByObjectLayoutBoxId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByObjectLayoutBoxId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"objectLayoutBoxId"}, true);

		_finderPathWithoutPaginationFindByObjectLayoutBoxId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByObjectLayoutBoxId", new String[] {Long.class.getName()},
			new String[] {"objectLayoutBoxId"}, true);

		_finderPathCountByObjectLayoutBoxId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByObjectLayoutBoxId", new String[] {Long.class.getName()},
			new String[] {"objectLayoutBoxId"}, false);
	}

	@Deactivate
	public void deactivate() {
		entityCache.removeCache(ObjectLayoutRowImpl.class.getName());
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

	private static final String _SQL_SELECT_OBJECTLAYOUTROW =
		"SELECT objectLayoutRow FROM ObjectLayoutRow objectLayoutRow";

	private static final String _SQL_SELECT_OBJECTLAYOUTROW_WHERE =
		"SELECT objectLayoutRow FROM ObjectLayoutRow objectLayoutRow WHERE ";

	private static final String _SQL_COUNT_OBJECTLAYOUTROW =
		"SELECT COUNT(objectLayoutRow) FROM ObjectLayoutRow objectLayoutRow";

	private static final String _SQL_COUNT_OBJECTLAYOUTROW_WHERE =
		"SELECT COUNT(objectLayoutRow) FROM ObjectLayoutRow objectLayoutRow WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS = "objectLayoutRow.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No ObjectLayoutRow exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No ObjectLayoutRow exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		ObjectLayoutRowPersistenceImpl.class);

	private static final Set<String> _badColumnNames = SetUtil.fromArray(
		new String[] {"uuid"});

	@Override
	protected FinderCache getFinderCache() {
		return finderCache;
	}

	@Reference
	private ObjectLayoutRowModelArgumentsResolver
		_objectLayoutRowModelArgumentsResolver;

}