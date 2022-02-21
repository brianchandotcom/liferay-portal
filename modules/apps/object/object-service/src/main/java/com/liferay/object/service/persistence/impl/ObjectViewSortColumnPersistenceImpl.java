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

import com.liferay.object.exception.NoSuchObjectViewSortColumnException;
import com.liferay.object.model.ObjectViewSortColumn;
import com.liferay.object.model.ObjectViewSortColumnTable;
import com.liferay.object.model.impl.ObjectViewSortColumnImpl;
import com.liferay.object.model.impl.ObjectViewSortColumnModelImpl;
import com.liferay.object.service.persistence.ObjectViewSortColumnPersistence;
import com.liferay.object.service.persistence.ObjectViewSortColumnUtil;
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
 * The persistence implementation for the object view sort column service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Marco Leo
 * @generated
 */
@Component(
	service = {ObjectViewSortColumnPersistence.class, BasePersistence.class}
)
public class ObjectViewSortColumnPersistenceImpl
	extends BasePersistenceImpl<ObjectViewSortColumn>
	implements ObjectViewSortColumnPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>ObjectViewSortColumnUtil</code> to access the object view sort column persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		ObjectViewSortColumnImpl.class.getName();

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
	 * Returns all the object view sort columns where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching object view sort columns
	 */
	@Override
	public List<ObjectViewSortColumn> findByUuid(String uuid) {
		return findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the object view sort columns where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectViewSortColumnModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of object view sort columns
	 * @param end the upper bound of the range of object view sort columns (not inclusive)
	 * @return the range of matching object view sort columns
	 */
	@Override
	public List<ObjectViewSortColumn> findByUuid(
		String uuid, int start, int end) {

		return findByUuid(uuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the object view sort columns where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectViewSortColumnModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of object view sort columns
	 * @param end the upper bound of the range of object view sort columns (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching object view sort columns
	 */
	@Override
	public List<ObjectViewSortColumn> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<ObjectViewSortColumn> orderByComparator) {

		return findByUuid(uuid, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the object view sort columns where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectViewSortColumnModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of object view sort columns
	 * @param end the upper bound of the range of object view sort columns (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching object view sort columns
	 */
	@Override
	public List<ObjectViewSortColumn> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<ObjectViewSortColumn> orderByComparator,
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

		List<ObjectViewSortColumn> list = null;

		if (useFinderCache) {
			list = (List<ObjectViewSortColumn>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (ObjectViewSortColumn objectViewSortColumn : list) {
					if (!uuid.equals(objectViewSortColumn.getUuid())) {
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

			sb.append(_SQL_SELECT_OBJECTVIEWSORTCOLUMN_WHERE);

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
				sb.append(ObjectViewSortColumnModelImpl.ORDER_BY_JPQL);
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

				list = (List<ObjectViewSortColumn>)QueryUtil.list(
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
	 * Returns the first object view sort column in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object view sort column
	 * @throws NoSuchObjectViewSortColumnException if a matching object view sort column could not be found
	 */
	@Override
	public ObjectViewSortColumn findByUuid_First(
			String uuid,
			OrderByComparator<ObjectViewSortColumn> orderByComparator)
		throws NoSuchObjectViewSortColumnException {

		ObjectViewSortColumn objectViewSortColumn = fetchByUuid_First(
			uuid, orderByComparator);

		if (objectViewSortColumn != null) {
			return objectViewSortColumn;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append("}");

		throw new NoSuchObjectViewSortColumnException(sb.toString());
	}

	/**
	 * Returns the first object view sort column in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object view sort column, or <code>null</code> if a matching object view sort column could not be found
	 */
	@Override
	public ObjectViewSortColumn fetchByUuid_First(
		String uuid,
		OrderByComparator<ObjectViewSortColumn> orderByComparator) {

		List<ObjectViewSortColumn> list = findByUuid(
			uuid, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last object view sort column in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object view sort column
	 * @throws NoSuchObjectViewSortColumnException if a matching object view sort column could not be found
	 */
	@Override
	public ObjectViewSortColumn findByUuid_Last(
			String uuid,
			OrderByComparator<ObjectViewSortColumn> orderByComparator)
		throws NoSuchObjectViewSortColumnException {

		ObjectViewSortColumn objectViewSortColumn = fetchByUuid_Last(
			uuid, orderByComparator);

		if (objectViewSortColumn != null) {
			return objectViewSortColumn;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append("}");

		throw new NoSuchObjectViewSortColumnException(sb.toString());
	}

	/**
	 * Returns the last object view sort column in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object view sort column, or <code>null</code> if a matching object view sort column could not be found
	 */
	@Override
	public ObjectViewSortColumn fetchByUuid_Last(
		String uuid,
		OrderByComparator<ObjectViewSortColumn> orderByComparator) {

		int count = countByUuid(uuid);

		if (count == 0) {
			return null;
		}

		List<ObjectViewSortColumn> list = findByUuid(
			uuid, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the object view sort columns before and after the current object view sort column in the ordered set where uuid = &#63;.
	 *
	 * @param objectViewSortColumnId the primary key of the current object view sort column
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next object view sort column
	 * @throws NoSuchObjectViewSortColumnException if a object view sort column with the primary key could not be found
	 */
	@Override
	public ObjectViewSortColumn[] findByUuid_PrevAndNext(
			long objectViewSortColumnId, String uuid,
			OrderByComparator<ObjectViewSortColumn> orderByComparator)
		throws NoSuchObjectViewSortColumnException {

		uuid = Objects.toString(uuid, "");

		ObjectViewSortColumn objectViewSortColumn = findByPrimaryKey(
			objectViewSortColumnId);

		Session session = null;

		try {
			session = openSession();

			ObjectViewSortColumn[] array = new ObjectViewSortColumnImpl[3];

			array[0] = getByUuid_PrevAndNext(
				session, objectViewSortColumn, uuid, orderByComparator, true);

			array[1] = objectViewSortColumn;

			array[2] = getByUuid_PrevAndNext(
				session, objectViewSortColumn, uuid, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected ObjectViewSortColumn getByUuid_PrevAndNext(
		Session session, ObjectViewSortColumn objectViewSortColumn, String uuid,
		OrderByComparator<ObjectViewSortColumn> orderByComparator,
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

		sb.append(_SQL_SELECT_OBJECTVIEWSORTCOLUMN_WHERE);

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
			sb.append(ObjectViewSortColumnModelImpl.ORDER_BY_JPQL);
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
						objectViewSortColumn)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<ObjectViewSortColumn> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the object view sort columns where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	@Override
	public void removeByUuid(String uuid) {
		for (ObjectViewSortColumn objectViewSortColumn :
				findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(objectViewSortColumn);
		}
	}

	/**
	 * Returns the number of object view sort columns where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching object view sort columns
	 */
	@Override
	public int countByUuid(String uuid) {
		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = _finderPathCountByUuid;

		Object[] finderArgs = new Object[] {uuid};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_OBJECTVIEWSORTCOLUMN_WHERE);

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
		"objectViewSortColumn.uuid = ?";

	private static final String _FINDER_COLUMN_UUID_UUID_3 =
		"(objectViewSortColumn.uuid IS NULL OR objectViewSortColumn.uuid = '')";

	private FinderPath _finderPathWithPaginationFindByUuid_C;
	private FinderPath _finderPathWithoutPaginationFindByUuid_C;
	private FinderPath _finderPathCountByUuid_C;

	/**
	 * Returns all the object view sort columns where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching object view sort columns
	 */
	@Override
	public List<ObjectViewSortColumn> findByUuid_C(
		String uuid, long companyId) {

		return findByUuid_C(
			uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the object view sort columns where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectViewSortColumnModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of object view sort columns
	 * @param end the upper bound of the range of object view sort columns (not inclusive)
	 * @return the range of matching object view sort columns
	 */
	@Override
	public List<ObjectViewSortColumn> findByUuid_C(
		String uuid, long companyId, int start, int end) {

		return findByUuid_C(uuid, companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the object view sort columns where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectViewSortColumnModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of object view sort columns
	 * @param end the upper bound of the range of object view sort columns (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching object view sort columns
	 */
	@Override
	public List<ObjectViewSortColumn> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<ObjectViewSortColumn> orderByComparator) {

		return findByUuid_C(
			uuid, companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the object view sort columns where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectViewSortColumnModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of object view sort columns
	 * @param end the upper bound of the range of object view sort columns (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching object view sort columns
	 */
	@Override
	public List<ObjectViewSortColumn> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<ObjectViewSortColumn> orderByComparator,
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

		List<ObjectViewSortColumn> list = null;

		if (useFinderCache) {
			list = (List<ObjectViewSortColumn>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (ObjectViewSortColumn objectViewSortColumn : list) {
					if (!uuid.equals(objectViewSortColumn.getUuid()) ||
						(companyId != objectViewSortColumn.getCompanyId())) {

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

			sb.append(_SQL_SELECT_OBJECTVIEWSORTCOLUMN_WHERE);

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
				sb.append(ObjectViewSortColumnModelImpl.ORDER_BY_JPQL);
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

				list = (List<ObjectViewSortColumn>)QueryUtil.list(
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
	 * Returns the first object view sort column in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object view sort column
	 * @throws NoSuchObjectViewSortColumnException if a matching object view sort column could not be found
	 */
	@Override
	public ObjectViewSortColumn findByUuid_C_First(
			String uuid, long companyId,
			OrderByComparator<ObjectViewSortColumn> orderByComparator)
		throws NoSuchObjectViewSortColumnException {

		ObjectViewSortColumn objectViewSortColumn = fetchByUuid_C_First(
			uuid, companyId, orderByComparator);

		if (objectViewSortColumn != null) {
			return objectViewSortColumn;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append(", companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchObjectViewSortColumnException(sb.toString());
	}

	/**
	 * Returns the first object view sort column in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object view sort column, or <code>null</code> if a matching object view sort column could not be found
	 */
	@Override
	public ObjectViewSortColumn fetchByUuid_C_First(
		String uuid, long companyId,
		OrderByComparator<ObjectViewSortColumn> orderByComparator) {

		List<ObjectViewSortColumn> list = findByUuid_C(
			uuid, companyId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last object view sort column in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object view sort column
	 * @throws NoSuchObjectViewSortColumnException if a matching object view sort column could not be found
	 */
	@Override
	public ObjectViewSortColumn findByUuid_C_Last(
			String uuid, long companyId,
			OrderByComparator<ObjectViewSortColumn> orderByComparator)
		throws NoSuchObjectViewSortColumnException {

		ObjectViewSortColumn objectViewSortColumn = fetchByUuid_C_Last(
			uuid, companyId, orderByComparator);

		if (objectViewSortColumn != null) {
			return objectViewSortColumn;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append(", companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchObjectViewSortColumnException(sb.toString());
	}

	/**
	 * Returns the last object view sort column in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object view sort column, or <code>null</code> if a matching object view sort column could not be found
	 */
	@Override
	public ObjectViewSortColumn fetchByUuid_C_Last(
		String uuid, long companyId,
		OrderByComparator<ObjectViewSortColumn> orderByComparator) {

		int count = countByUuid_C(uuid, companyId);

		if (count == 0) {
			return null;
		}

		List<ObjectViewSortColumn> list = findByUuid_C(
			uuid, companyId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the object view sort columns before and after the current object view sort column in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param objectViewSortColumnId the primary key of the current object view sort column
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next object view sort column
	 * @throws NoSuchObjectViewSortColumnException if a object view sort column with the primary key could not be found
	 */
	@Override
	public ObjectViewSortColumn[] findByUuid_C_PrevAndNext(
			long objectViewSortColumnId, String uuid, long companyId,
			OrderByComparator<ObjectViewSortColumn> orderByComparator)
		throws NoSuchObjectViewSortColumnException {

		uuid = Objects.toString(uuid, "");

		ObjectViewSortColumn objectViewSortColumn = findByPrimaryKey(
			objectViewSortColumnId);

		Session session = null;

		try {
			session = openSession();

			ObjectViewSortColumn[] array = new ObjectViewSortColumnImpl[3];

			array[0] = getByUuid_C_PrevAndNext(
				session, objectViewSortColumn, uuid, companyId,
				orderByComparator, true);

			array[1] = objectViewSortColumn;

			array[2] = getByUuid_C_PrevAndNext(
				session, objectViewSortColumn, uuid, companyId,
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

	protected ObjectViewSortColumn getByUuid_C_PrevAndNext(
		Session session, ObjectViewSortColumn objectViewSortColumn, String uuid,
		long companyId,
		OrderByComparator<ObjectViewSortColumn> orderByComparator,
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

		sb.append(_SQL_SELECT_OBJECTVIEWSORTCOLUMN_WHERE);

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
			sb.append(ObjectViewSortColumnModelImpl.ORDER_BY_JPQL);
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
						objectViewSortColumn)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<ObjectViewSortColumn> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the object view sort columns where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	@Override
	public void removeByUuid_C(String uuid, long companyId) {
		for (ObjectViewSortColumn objectViewSortColumn :
				findByUuid_C(
					uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(objectViewSortColumn);
		}
	}

	/**
	 * Returns the number of object view sort columns where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching object view sort columns
	 */
	@Override
	public int countByUuid_C(String uuid, long companyId) {
		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = _finderPathCountByUuid_C;

		Object[] finderArgs = new Object[] {uuid, companyId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_OBJECTVIEWSORTCOLUMN_WHERE);

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
		"objectViewSortColumn.uuid = ? AND ";

	private static final String _FINDER_COLUMN_UUID_C_UUID_3 =
		"(objectViewSortColumn.uuid IS NULL OR objectViewSortColumn.uuid = '') AND ";

	private static final String _FINDER_COLUMN_UUID_C_COMPANYID_2 =
		"objectViewSortColumn.companyId = ?";

	private FinderPath _finderPathWithPaginationFindByObjectViewId;
	private FinderPath _finderPathWithoutPaginationFindByObjectViewId;
	private FinderPath _finderPathCountByObjectViewId;

	/**
	 * Returns all the object view sort columns where objectViewId = &#63;.
	 *
	 * @param objectViewId the object view ID
	 * @return the matching object view sort columns
	 */
	@Override
	public List<ObjectViewSortColumn> findByObjectViewId(long objectViewId) {
		return findByObjectViewId(
			objectViewId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the object view sort columns where objectViewId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectViewSortColumnModelImpl</code>.
	 * </p>
	 *
	 * @param objectViewId the object view ID
	 * @param start the lower bound of the range of object view sort columns
	 * @param end the upper bound of the range of object view sort columns (not inclusive)
	 * @return the range of matching object view sort columns
	 */
	@Override
	public List<ObjectViewSortColumn> findByObjectViewId(
		long objectViewId, int start, int end) {

		return findByObjectViewId(objectViewId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the object view sort columns where objectViewId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectViewSortColumnModelImpl</code>.
	 * </p>
	 *
	 * @param objectViewId the object view ID
	 * @param start the lower bound of the range of object view sort columns
	 * @param end the upper bound of the range of object view sort columns (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching object view sort columns
	 */
	@Override
	public List<ObjectViewSortColumn> findByObjectViewId(
		long objectViewId, int start, int end,
		OrderByComparator<ObjectViewSortColumn> orderByComparator) {

		return findByObjectViewId(
			objectViewId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the object view sort columns where objectViewId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectViewSortColumnModelImpl</code>.
	 * </p>
	 *
	 * @param objectViewId the object view ID
	 * @param start the lower bound of the range of object view sort columns
	 * @param end the upper bound of the range of object view sort columns (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching object view sort columns
	 */
	@Override
	public List<ObjectViewSortColumn> findByObjectViewId(
		long objectViewId, int start, int end,
		OrderByComparator<ObjectViewSortColumn> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByObjectViewId;
				finderArgs = new Object[] {objectViewId};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByObjectViewId;
			finderArgs = new Object[] {
				objectViewId, start, end, orderByComparator
			};
		}

		List<ObjectViewSortColumn> list = null;

		if (useFinderCache) {
			list = (List<ObjectViewSortColumn>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (ObjectViewSortColumn objectViewSortColumn : list) {
					if (objectViewId !=
							objectViewSortColumn.getObjectViewId()) {

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

			sb.append(_SQL_SELECT_OBJECTVIEWSORTCOLUMN_WHERE);

			sb.append(_FINDER_COLUMN_OBJECTVIEWID_OBJECTVIEWID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(ObjectViewSortColumnModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(objectViewId);

				list = (List<ObjectViewSortColumn>)QueryUtil.list(
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
	 * Returns the first object view sort column in the ordered set where objectViewId = &#63;.
	 *
	 * @param objectViewId the object view ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object view sort column
	 * @throws NoSuchObjectViewSortColumnException if a matching object view sort column could not be found
	 */
	@Override
	public ObjectViewSortColumn findByObjectViewId_First(
			long objectViewId,
			OrderByComparator<ObjectViewSortColumn> orderByComparator)
		throws NoSuchObjectViewSortColumnException {

		ObjectViewSortColumn objectViewSortColumn = fetchByObjectViewId_First(
			objectViewId, orderByComparator);

		if (objectViewSortColumn != null) {
			return objectViewSortColumn;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("objectViewId=");
		sb.append(objectViewId);

		sb.append("}");

		throw new NoSuchObjectViewSortColumnException(sb.toString());
	}

	/**
	 * Returns the first object view sort column in the ordered set where objectViewId = &#63;.
	 *
	 * @param objectViewId the object view ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object view sort column, or <code>null</code> if a matching object view sort column could not be found
	 */
	@Override
	public ObjectViewSortColumn fetchByObjectViewId_First(
		long objectViewId,
		OrderByComparator<ObjectViewSortColumn> orderByComparator) {

		List<ObjectViewSortColumn> list = findByObjectViewId(
			objectViewId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last object view sort column in the ordered set where objectViewId = &#63;.
	 *
	 * @param objectViewId the object view ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object view sort column
	 * @throws NoSuchObjectViewSortColumnException if a matching object view sort column could not be found
	 */
	@Override
	public ObjectViewSortColumn findByObjectViewId_Last(
			long objectViewId,
			OrderByComparator<ObjectViewSortColumn> orderByComparator)
		throws NoSuchObjectViewSortColumnException {

		ObjectViewSortColumn objectViewSortColumn = fetchByObjectViewId_Last(
			objectViewId, orderByComparator);

		if (objectViewSortColumn != null) {
			return objectViewSortColumn;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("objectViewId=");
		sb.append(objectViewId);

		sb.append("}");

		throw new NoSuchObjectViewSortColumnException(sb.toString());
	}

	/**
	 * Returns the last object view sort column in the ordered set where objectViewId = &#63;.
	 *
	 * @param objectViewId the object view ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object view sort column, or <code>null</code> if a matching object view sort column could not be found
	 */
	@Override
	public ObjectViewSortColumn fetchByObjectViewId_Last(
		long objectViewId,
		OrderByComparator<ObjectViewSortColumn> orderByComparator) {

		int count = countByObjectViewId(objectViewId);

		if (count == 0) {
			return null;
		}

		List<ObjectViewSortColumn> list = findByObjectViewId(
			objectViewId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the object view sort columns before and after the current object view sort column in the ordered set where objectViewId = &#63;.
	 *
	 * @param objectViewSortColumnId the primary key of the current object view sort column
	 * @param objectViewId the object view ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next object view sort column
	 * @throws NoSuchObjectViewSortColumnException if a object view sort column with the primary key could not be found
	 */
	@Override
	public ObjectViewSortColumn[] findByObjectViewId_PrevAndNext(
			long objectViewSortColumnId, long objectViewId,
			OrderByComparator<ObjectViewSortColumn> orderByComparator)
		throws NoSuchObjectViewSortColumnException {

		ObjectViewSortColumn objectViewSortColumn = findByPrimaryKey(
			objectViewSortColumnId);

		Session session = null;

		try {
			session = openSession();

			ObjectViewSortColumn[] array = new ObjectViewSortColumnImpl[3];

			array[0] = getByObjectViewId_PrevAndNext(
				session, objectViewSortColumn, objectViewId, orderByComparator,
				true);

			array[1] = objectViewSortColumn;

			array[2] = getByObjectViewId_PrevAndNext(
				session, objectViewSortColumn, objectViewId, orderByComparator,
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

	protected ObjectViewSortColumn getByObjectViewId_PrevAndNext(
		Session session, ObjectViewSortColumn objectViewSortColumn,
		long objectViewId,
		OrderByComparator<ObjectViewSortColumn> orderByComparator,
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

		sb.append(_SQL_SELECT_OBJECTVIEWSORTCOLUMN_WHERE);

		sb.append(_FINDER_COLUMN_OBJECTVIEWID_OBJECTVIEWID_2);

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
			sb.append(ObjectViewSortColumnModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(objectViewId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						objectViewSortColumn)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<ObjectViewSortColumn> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the object view sort columns where objectViewId = &#63; from the database.
	 *
	 * @param objectViewId the object view ID
	 */
	@Override
	public void removeByObjectViewId(long objectViewId) {
		for (ObjectViewSortColumn objectViewSortColumn :
				findByObjectViewId(
					objectViewId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(objectViewSortColumn);
		}
	}

	/**
	 * Returns the number of object view sort columns where objectViewId = &#63;.
	 *
	 * @param objectViewId the object view ID
	 * @return the number of matching object view sort columns
	 */
	@Override
	public int countByObjectViewId(long objectViewId) {
		FinderPath finderPath = _finderPathCountByObjectViewId;

		Object[] finderArgs = new Object[] {objectViewId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_OBJECTVIEWSORTCOLUMN_WHERE);

			sb.append(_FINDER_COLUMN_OBJECTVIEWID_OBJECTVIEWID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(objectViewId);

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

	private static final String _FINDER_COLUMN_OBJECTVIEWID_OBJECTVIEWID_2 =
		"objectViewSortColumn.objectViewId = ?";

	private FinderPath _finderPathWithPaginationFindByOVI_OFN;
	private FinderPath _finderPathWithoutPaginationFindByOVI_OFN;
	private FinderPath _finderPathCountByOVI_OFN;

	/**
	 * Returns all the object view sort columns where objectViewId = &#63; and objectFieldName = &#63;.
	 *
	 * @param objectViewId the object view ID
	 * @param objectFieldName the object field name
	 * @return the matching object view sort columns
	 */
	@Override
	public List<ObjectViewSortColumn> findByOVI_OFN(
		long objectViewId, String objectFieldName) {

		return findByOVI_OFN(
			objectViewId, objectFieldName, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the object view sort columns where objectViewId = &#63; and objectFieldName = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectViewSortColumnModelImpl</code>.
	 * </p>
	 *
	 * @param objectViewId the object view ID
	 * @param objectFieldName the object field name
	 * @param start the lower bound of the range of object view sort columns
	 * @param end the upper bound of the range of object view sort columns (not inclusive)
	 * @return the range of matching object view sort columns
	 */
	@Override
	public List<ObjectViewSortColumn> findByOVI_OFN(
		long objectViewId, String objectFieldName, int start, int end) {

		return findByOVI_OFN(objectViewId, objectFieldName, start, end, null);
	}

	/**
	 * Returns an ordered range of all the object view sort columns where objectViewId = &#63; and objectFieldName = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectViewSortColumnModelImpl</code>.
	 * </p>
	 *
	 * @param objectViewId the object view ID
	 * @param objectFieldName the object field name
	 * @param start the lower bound of the range of object view sort columns
	 * @param end the upper bound of the range of object view sort columns (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching object view sort columns
	 */
	@Override
	public List<ObjectViewSortColumn> findByOVI_OFN(
		long objectViewId, String objectFieldName, int start, int end,
		OrderByComparator<ObjectViewSortColumn> orderByComparator) {

		return findByOVI_OFN(
			objectViewId, objectFieldName, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the object view sort columns where objectViewId = &#63; and objectFieldName = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectViewSortColumnModelImpl</code>.
	 * </p>
	 *
	 * @param objectViewId the object view ID
	 * @param objectFieldName the object field name
	 * @param start the lower bound of the range of object view sort columns
	 * @param end the upper bound of the range of object view sort columns (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching object view sort columns
	 */
	@Override
	public List<ObjectViewSortColumn> findByOVI_OFN(
		long objectViewId, String objectFieldName, int start, int end,
		OrderByComparator<ObjectViewSortColumn> orderByComparator,
		boolean useFinderCache) {

		objectFieldName = Objects.toString(objectFieldName, "");

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByOVI_OFN;
				finderArgs = new Object[] {objectViewId, objectFieldName};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByOVI_OFN;
			finderArgs = new Object[] {
				objectViewId, objectFieldName, start, end, orderByComparator
			};
		}

		List<ObjectViewSortColumn> list = null;

		if (useFinderCache) {
			list = (List<ObjectViewSortColumn>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (ObjectViewSortColumn objectViewSortColumn : list) {
					if ((objectViewId !=
							objectViewSortColumn.getObjectViewId()) ||
						!objectFieldName.equals(
							objectViewSortColumn.getObjectFieldName())) {

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

			sb.append(_SQL_SELECT_OBJECTVIEWSORTCOLUMN_WHERE);

			sb.append(_FINDER_COLUMN_OVI_OFN_OBJECTVIEWID_2);

			boolean bindObjectFieldName = false;

			if (objectFieldName.isEmpty()) {
				sb.append(_FINDER_COLUMN_OVI_OFN_OBJECTFIELDNAME_3);
			}
			else {
				bindObjectFieldName = true;

				sb.append(_FINDER_COLUMN_OVI_OFN_OBJECTFIELDNAME_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(ObjectViewSortColumnModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(objectViewId);

				if (bindObjectFieldName) {
					queryPos.add(objectFieldName);
				}

				list = (List<ObjectViewSortColumn>)QueryUtil.list(
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
	 * Returns the first object view sort column in the ordered set where objectViewId = &#63; and objectFieldName = &#63;.
	 *
	 * @param objectViewId the object view ID
	 * @param objectFieldName the object field name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object view sort column
	 * @throws NoSuchObjectViewSortColumnException if a matching object view sort column could not be found
	 */
	@Override
	public ObjectViewSortColumn findByOVI_OFN_First(
			long objectViewId, String objectFieldName,
			OrderByComparator<ObjectViewSortColumn> orderByComparator)
		throws NoSuchObjectViewSortColumnException {

		ObjectViewSortColumn objectViewSortColumn = fetchByOVI_OFN_First(
			objectViewId, objectFieldName, orderByComparator);

		if (objectViewSortColumn != null) {
			return objectViewSortColumn;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("objectViewId=");
		sb.append(objectViewId);

		sb.append(", objectFieldName=");
		sb.append(objectFieldName);

		sb.append("}");

		throw new NoSuchObjectViewSortColumnException(sb.toString());
	}

	/**
	 * Returns the first object view sort column in the ordered set where objectViewId = &#63; and objectFieldName = &#63;.
	 *
	 * @param objectViewId the object view ID
	 * @param objectFieldName the object field name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object view sort column, or <code>null</code> if a matching object view sort column could not be found
	 */
	@Override
	public ObjectViewSortColumn fetchByOVI_OFN_First(
		long objectViewId, String objectFieldName,
		OrderByComparator<ObjectViewSortColumn> orderByComparator) {

		List<ObjectViewSortColumn> list = findByOVI_OFN(
			objectViewId, objectFieldName, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last object view sort column in the ordered set where objectViewId = &#63; and objectFieldName = &#63;.
	 *
	 * @param objectViewId the object view ID
	 * @param objectFieldName the object field name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object view sort column
	 * @throws NoSuchObjectViewSortColumnException if a matching object view sort column could not be found
	 */
	@Override
	public ObjectViewSortColumn findByOVI_OFN_Last(
			long objectViewId, String objectFieldName,
			OrderByComparator<ObjectViewSortColumn> orderByComparator)
		throws NoSuchObjectViewSortColumnException {

		ObjectViewSortColumn objectViewSortColumn = fetchByOVI_OFN_Last(
			objectViewId, objectFieldName, orderByComparator);

		if (objectViewSortColumn != null) {
			return objectViewSortColumn;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("objectViewId=");
		sb.append(objectViewId);

		sb.append(", objectFieldName=");
		sb.append(objectFieldName);

		sb.append("}");

		throw new NoSuchObjectViewSortColumnException(sb.toString());
	}

	/**
	 * Returns the last object view sort column in the ordered set where objectViewId = &#63; and objectFieldName = &#63;.
	 *
	 * @param objectViewId the object view ID
	 * @param objectFieldName the object field name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object view sort column, or <code>null</code> if a matching object view sort column could not be found
	 */
	@Override
	public ObjectViewSortColumn fetchByOVI_OFN_Last(
		long objectViewId, String objectFieldName,
		OrderByComparator<ObjectViewSortColumn> orderByComparator) {

		int count = countByOVI_OFN(objectViewId, objectFieldName);

		if (count == 0) {
			return null;
		}

		List<ObjectViewSortColumn> list = findByOVI_OFN(
			objectViewId, objectFieldName, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the object view sort columns before and after the current object view sort column in the ordered set where objectViewId = &#63; and objectFieldName = &#63;.
	 *
	 * @param objectViewSortColumnId the primary key of the current object view sort column
	 * @param objectViewId the object view ID
	 * @param objectFieldName the object field name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next object view sort column
	 * @throws NoSuchObjectViewSortColumnException if a object view sort column with the primary key could not be found
	 */
	@Override
	public ObjectViewSortColumn[] findByOVI_OFN_PrevAndNext(
			long objectViewSortColumnId, long objectViewId,
			String objectFieldName,
			OrderByComparator<ObjectViewSortColumn> orderByComparator)
		throws NoSuchObjectViewSortColumnException {

		objectFieldName = Objects.toString(objectFieldName, "");

		ObjectViewSortColumn objectViewSortColumn = findByPrimaryKey(
			objectViewSortColumnId);

		Session session = null;

		try {
			session = openSession();

			ObjectViewSortColumn[] array = new ObjectViewSortColumnImpl[3];

			array[0] = getByOVI_OFN_PrevAndNext(
				session, objectViewSortColumn, objectViewId, objectFieldName,
				orderByComparator, true);

			array[1] = objectViewSortColumn;

			array[2] = getByOVI_OFN_PrevAndNext(
				session, objectViewSortColumn, objectViewId, objectFieldName,
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

	protected ObjectViewSortColumn getByOVI_OFN_PrevAndNext(
		Session session, ObjectViewSortColumn objectViewSortColumn,
		long objectViewId, String objectFieldName,
		OrderByComparator<ObjectViewSortColumn> orderByComparator,
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

		sb.append(_SQL_SELECT_OBJECTVIEWSORTCOLUMN_WHERE);

		sb.append(_FINDER_COLUMN_OVI_OFN_OBJECTVIEWID_2);

		boolean bindObjectFieldName = false;

		if (objectFieldName.isEmpty()) {
			sb.append(_FINDER_COLUMN_OVI_OFN_OBJECTFIELDNAME_3);
		}
		else {
			bindObjectFieldName = true;

			sb.append(_FINDER_COLUMN_OVI_OFN_OBJECTFIELDNAME_2);
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
			sb.append(ObjectViewSortColumnModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(objectViewId);

		if (bindObjectFieldName) {
			queryPos.add(objectFieldName);
		}

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						objectViewSortColumn)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<ObjectViewSortColumn> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the object view sort columns where objectViewId = &#63; and objectFieldName = &#63; from the database.
	 *
	 * @param objectViewId the object view ID
	 * @param objectFieldName the object field name
	 */
	@Override
	public void removeByOVI_OFN(long objectViewId, String objectFieldName) {
		for (ObjectViewSortColumn objectViewSortColumn :
				findByOVI_OFN(
					objectViewId, objectFieldName, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			remove(objectViewSortColumn);
		}
	}

	/**
	 * Returns the number of object view sort columns where objectViewId = &#63; and objectFieldName = &#63;.
	 *
	 * @param objectViewId the object view ID
	 * @param objectFieldName the object field name
	 * @return the number of matching object view sort columns
	 */
	@Override
	public int countByOVI_OFN(long objectViewId, String objectFieldName) {
		objectFieldName = Objects.toString(objectFieldName, "");

		FinderPath finderPath = _finderPathCountByOVI_OFN;

		Object[] finderArgs = new Object[] {objectViewId, objectFieldName};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_OBJECTVIEWSORTCOLUMN_WHERE);

			sb.append(_FINDER_COLUMN_OVI_OFN_OBJECTVIEWID_2);

			boolean bindObjectFieldName = false;

			if (objectFieldName.isEmpty()) {
				sb.append(_FINDER_COLUMN_OVI_OFN_OBJECTFIELDNAME_3);
			}
			else {
				bindObjectFieldName = true;

				sb.append(_FINDER_COLUMN_OVI_OFN_OBJECTFIELDNAME_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(objectViewId);

				if (bindObjectFieldName) {
					queryPos.add(objectFieldName);
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

	private static final String _FINDER_COLUMN_OVI_OFN_OBJECTVIEWID_2 =
		"objectViewSortColumn.objectViewId = ? AND ";

	private static final String _FINDER_COLUMN_OVI_OFN_OBJECTFIELDNAME_2 =
		"objectViewSortColumn.objectFieldName = ?";

	private static final String _FINDER_COLUMN_OVI_OFN_OBJECTFIELDNAME_3 =
		"(objectViewSortColumn.objectFieldName IS NULL OR objectViewSortColumn.objectFieldName = '')";

	public ObjectViewSortColumnPersistenceImpl() {
		Map<String, String> dbColumnNames = new HashMap<String, String>();

		dbColumnNames.put("uuid", "uuid_");

		setDBColumnNames(dbColumnNames);

		setModelClass(ObjectViewSortColumn.class);

		setModelImplClass(ObjectViewSortColumnImpl.class);
		setModelPKClass(long.class);

		setTable(ObjectViewSortColumnTable.INSTANCE);
	}

	/**
	 * Caches the object view sort column in the entity cache if it is enabled.
	 *
	 * @param objectViewSortColumn the object view sort column
	 */
	@Override
	public void cacheResult(ObjectViewSortColumn objectViewSortColumn) {
		entityCache.putResult(
			ObjectViewSortColumnImpl.class,
			objectViewSortColumn.getPrimaryKey(), objectViewSortColumn);
	}

	private int _valueObjectFinderCacheListThreshold;

	/**
	 * Caches the object view sort columns in the entity cache if it is enabled.
	 *
	 * @param objectViewSortColumns the object view sort columns
	 */
	@Override
	public void cacheResult(List<ObjectViewSortColumn> objectViewSortColumns) {
		if ((_valueObjectFinderCacheListThreshold == 0) ||
			((_valueObjectFinderCacheListThreshold > 0) &&
			 (objectViewSortColumns.size() >
				 _valueObjectFinderCacheListThreshold))) {

			return;
		}

		for (ObjectViewSortColumn objectViewSortColumn :
				objectViewSortColumns) {

			if (entityCache.getResult(
					ObjectViewSortColumnImpl.class,
					objectViewSortColumn.getPrimaryKey()) == null) {

				cacheResult(objectViewSortColumn);
			}
		}
	}

	/**
	 * Clears the cache for all object view sort columns.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(ObjectViewSortColumnImpl.class);

		finderCache.clearCache(ObjectViewSortColumnImpl.class);
	}

	/**
	 * Clears the cache for the object view sort column.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(ObjectViewSortColumn objectViewSortColumn) {
		entityCache.removeResult(
			ObjectViewSortColumnImpl.class, objectViewSortColumn);
	}

	@Override
	public void clearCache(List<ObjectViewSortColumn> objectViewSortColumns) {
		for (ObjectViewSortColumn objectViewSortColumn :
				objectViewSortColumns) {

			entityCache.removeResult(
				ObjectViewSortColumnImpl.class, objectViewSortColumn);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(ObjectViewSortColumnImpl.class);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(
				ObjectViewSortColumnImpl.class, primaryKey);
		}
	}

	/**
	 * Creates a new object view sort column with the primary key. Does not add the object view sort column to the database.
	 *
	 * @param objectViewSortColumnId the primary key for the new object view sort column
	 * @return the new object view sort column
	 */
	@Override
	public ObjectViewSortColumn create(long objectViewSortColumnId) {
		ObjectViewSortColumn objectViewSortColumn =
			new ObjectViewSortColumnImpl();

		objectViewSortColumn.setNew(true);
		objectViewSortColumn.setPrimaryKey(objectViewSortColumnId);

		String uuid = PortalUUIDUtil.generate();

		objectViewSortColumn.setUuid(uuid);

		objectViewSortColumn.setCompanyId(CompanyThreadLocal.getCompanyId());

		return objectViewSortColumn;
	}

	/**
	 * Removes the object view sort column with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param objectViewSortColumnId the primary key of the object view sort column
	 * @return the object view sort column that was removed
	 * @throws NoSuchObjectViewSortColumnException if a object view sort column with the primary key could not be found
	 */
	@Override
	public ObjectViewSortColumn remove(long objectViewSortColumnId)
		throws NoSuchObjectViewSortColumnException {

		return remove((Serializable)objectViewSortColumnId);
	}

	/**
	 * Removes the object view sort column with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the object view sort column
	 * @return the object view sort column that was removed
	 * @throws NoSuchObjectViewSortColumnException if a object view sort column with the primary key could not be found
	 */
	@Override
	public ObjectViewSortColumn remove(Serializable primaryKey)
		throws NoSuchObjectViewSortColumnException {

		Session session = null;

		try {
			session = openSession();

			ObjectViewSortColumn objectViewSortColumn =
				(ObjectViewSortColumn)session.get(
					ObjectViewSortColumnImpl.class, primaryKey);

			if (objectViewSortColumn == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchObjectViewSortColumnException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(objectViewSortColumn);
		}
		catch (NoSuchObjectViewSortColumnException noSuchEntityException) {
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
	protected ObjectViewSortColumn removeImpl(
		ObjectViewSortColumn objectViewSortColumn) {

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(objectViewSortColumn)) {
				objectViewSortColumn = (ObjectViewSortColumn)session.get(
					ObjectViewSortColumnImpl.class,
					objectViewSortColumn.getPrimaryKeyObj());
			}

			if (objectViewSortColumn != null) {
				session.delete(objectViewSortColumn);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (objectViewSortColumn != null) {
			clearCache(objectViewSortColumn);
		}

		return objectViewSortColumn;
	}

	@Override
	public ObjectViewSortColumn updateImpl(
		ObjectViewSortColumn objectViewSortColumn) {

		boolean isNew = objectViewSortColumn.isNew();

		if (!(objectViewSortColumn instanceof ObjectViewSortColumnModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(objectViewSortColumn.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					objectViewSortColumn);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in objectViewSortColumn proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom ObjectViewSortColumn implementation " +
					objectViewSortColumn.getClass());
		}

		ObjectViewSortColumnModelImpl objectViewSortColumnModelImpl =
			(ObjectViewSortColumnModelImpl)objectViewSortColumn;

		if (Validator.isNull(objectViewSortColumn.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			objectViewSortColumn.setUuid(uuid);
		}

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Date date = new Date();

		if (isNew && (objectViewSortColumn.getCreateDate() == null)) {
			if (serviceContext == null) {
				objectViewSortColumn.setCreateDate(date);
			}
			else {
				objectViewSortColumn.setCreateDate(
					serviceContext.getCreateDate(date));
			}
		}

		if (!objectViewSortColumnModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				objectViewSortColumn.setModifiedDate(date);
			}
			else {
				objectViewSortColumn.setModifiedDate(
					serviceContext.getModifiedDate(date));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (isNew) {
				session.save(objectViewSortColumn);
			}
			else {
				objectViewSortColumn = (ObjectViewSortColumn)session.merge(
					objectViewSortColumn);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		entityCache.putResult(
			ObjectViewSortColumnImpl.class, objectViewSortColumnModelImpl,
			false, true);

		if (isNew) {
			objectViewSortColumn.setNew(false);
		}

		objectViewSortColumn.resetOriginalValues();

		return objectViewSortColumn;
	}

	/**
	 * Returns the object view sort column with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the object view sort column
	 * @return the object view sort column
	 * @throws NoSuchObjectViewSortColumnException if a object view sort column with the primary key could not be found
	 */
	@Override
	public ObjectViewSortColumn findByPrimaryKey(Serializable primaryKey)
		throws NoSuchObjectViewSortColumnException {

		ObjectViewSortColumn objectViewSortColumn = fetchByPrimaryKey(
			primaryKey);

		if (objectViewSortColumn == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchObjectViewSortColumnException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return objectViewSortColumn;
	}

	/**
	 * Returns the object view sort column with the primary key or throws a <code>NoSuchObjectViewSortColumnException</code> if it could not be found.
	 *
	 * @param objectViewSortColumnId the primary key of the object view sort column
	 * @return the object view sort column
	 * @throws NoSuchObjectViewSortColumnException if a object view sort column with the primary key could not be found
	 */
	@Override
	public ObjectViewSortColumn findByPrimaryKey(long objectViewSortColumnId)
		throws NoSuchObjectViewSortColumnException {

		return findByPrimaryKey((Serializable)objectViewSortColumnId);
	}

	/**
	 * Returns the object view sort column with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param objectViewSortColumnId the primary key of the object view sort column
	 * @return the object view sort column, or <code>null</code> if a object view sort column with the primary key could not be found
	 */
	@Override
	public ObjectViewSortColumn fetchByPrimaryKey(long objectViewSortColumnId) {
		return fetchByPrimaryKey((Serializable)objectViewSortColumnId);
	}

	/**
	 * Returns all the object view sort columns.
	 *
	 * @return the object view sort columns
	 */
	@Override
	public List<ObjectViewSortColumn> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the object view sort columns.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectViewSortColumnModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of object view sort columns
	 * @param end the upper bound of the range of object view sort columns (not inclusive)
	 * @return the range of object view sort columns
	 */
	@Override
	public List<ObjectViewSortColumn> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the object view sort columns.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectViewSortColumnModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of object view sort columns
	 * @param end the upper bound of the range of object view sort columns (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of object view sort columns
	 */
	@Override
	public List<ObjectViewSortColumn> findAll(
		int start, int end,
		OrderByComparator<ObjectViewSortColumn> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the object view sort columns.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectViewSortColumnModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of object view sort columns
	 * @param end the upper bound of the range of object view sort columns (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of object view sort columns
	 */
	@Override
	public List<ObjectViewSortColumn> findAll(
		int start, int end,
		OrderByComparator<ObjectViewSortColumn> orderByComparator,
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

		List<ObjectViewSortColumn> list = null;

		if (useFinderCache) {
			list = (List<ObjectViewSortColumn>)finderCache.getResult(
				finderPath, finderArgs);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_OBJECTVIEWSORTCOLUMN);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_OBJECTVIEWSORTCOLUMN;

				sql = sql.concat(ObjectViewSortColumnModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<ObjectViewSortColumn>)QueryUtil.list(
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
	 * Removes all the object view sort columns from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (ObjectViewSortColumn objectViewSortColumn : findAll()) {
			remove(objectViewSortColumn);
		}
	}

	/**
	 * Returns the number of object view sort columns.
	 *
	 * @return the number of object view sort columns
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
					_SQL_COUNT_OBJECTVIEWSORTCOLUMN);

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
		return "objectViewSortColumnId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_OBJECTVIEWSORTCOLUMN;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return ObjectViewSortColumnModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the object view sort column persistence.
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

		_finderPathWithPaginationFindByObjectViewId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByObjectViewId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"objectViewId"}, true);

		_finderPathWithoutPaginationFindByObjectViewId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByObjectViewId",
			new String[] {Long.class.getName()}, new String[] {"objectViewId"},
			true);

		_finderPathCountByObjectViewId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByObjectViewId",
			new String[] {Long.class.getName()}, new String[] {"objectViewId"},
			false);

		_finderPathWithPaginationFindByOVI_OFN = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByOVI_OFN",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {"objectViewId", "objectFieldName"}, true);

		_finderPathWithoutPaginationFindByOVI_OFN = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByOVI_OFN",
			new String[] {Long.class.getName(), String.class.getName()},
			new String[] {"objectViewId", "objectFieldName"}, true);

		_finderPathCountByOVI_OFN = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByOVI_OFN",
			new String[] {Long.class.getName(), String.class.getName()},
			new String[] {"objectViewId", "objectFieldName"}, false);

		_setObjectViewSortColumnUtilPersistence(this);
	}

	@Deactivate
	public void deactivate() {
		_setObjectViewSortColumnUtilPersistence(null);

		entityCache.removeCache(ObjectViewSortColumnImpl.class.getName());
	}

	private void _setObjectViewSortColumnUtilPersistence(
		ObjectViewSortColumnPersistence objectViewSortColumnPersistence) {

		try {
			Field field = ObjectViewSortColumnUtil.class.getDeclaredField(
				"_persistence");

			field.setAccessible(true);

			field.set(null, objectViewSortColumnPersistence);
		}
		catch (ReflectiveOperationException reflectiveOperationException) {
			throw new RuntimeException(reflectiveOperationException);
		}
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

	private static final String _SQL_SELECT_OBJECTVIEWSORTCOLUMN =
		"SELECT objectViewSortColumn FROM ObjectViewSortColumn objectViewSortColumn";

	private static final String _SQL_SELECT_OBJECTVIEWSORTCOLUMN_WHERE =
		"SELECT objectViewSortColumn FROM ObjectViewSortColumn objectViewSortColumn WHERE ";

	private static final String _SQL_COUNT_OBJECTVIEWSORTCOLUMN =
		"SELECT COUNT(objectViewSortColumn) FROM ObjectViewSortColumn objectViewSortColumn";

	private static final String _SQL_COUNT_OBJECTVIEWSORTCOLUMN_WHERE =
		"SELECT COUNT(objectViewSortColumn) FROM ObjectViewSortColumn objectViewSortColumn WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS =
		"objectViewSortColumn.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No ObjectViewSortColumn exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No ObjectViewSortColumn exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		ObjectViewSortColumnPersistenceImpl.class);

	private static final Set<String> _badColumnNames = SetUtil.fromArray(
		new String[] {"uuid"});

	@Override
	protected FinderCache getFinderCache() {
		return finderCache;
	}

	@Reference
	private ObjectViewSortColumnModelArgumentsResolver
		_objectViewSortColumnModelArgumentsResolver;

}