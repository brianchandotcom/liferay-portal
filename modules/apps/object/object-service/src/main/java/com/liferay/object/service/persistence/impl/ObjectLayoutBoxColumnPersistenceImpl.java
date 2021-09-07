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

import com.liferay.object.exception.NoSuchObjectLayoutBoxColumnException;
import com.liferay.object.model.ObjectLayoutBoxColumn;
import com.liferay.object.model.ObjectLayoutBoxColumnTable;
import com.liferay.object.model.impl.ObjectLayoutBoxColumnImpl;
import com.liferay.object.model.impl.ObjectLayoutBoxColumnModelImpl;
import com.liferay.object.service.persistence.ObjectLayoutBoxColumnPersistence;
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

import javax.sql.DataSource;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * The persistence implementation for the object layout box column service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Marco Leo
 * @generated
 */
@Component(
	service = {ObjectLayoutBoxColumnPersistence.class, BasePersistence.class}
)
public class ObjectLayoutBoxColumnPersistenceImpl
	extends BasePersistenceImpl<ObjectLayoutBoxColumn>
	implements ObjectLayoutBoxColumnPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>ObjectLayoutBoxColumnUtil</code> to access the object layout box column persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		ObjectLayoutBoxColumnImpl.class.getName();

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
	 * Returns all the object layout box columns where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching object layout box columns
	 */
	@Override
	public List<ObjectLayoutBoxColumn> findByUuid(String uuid) {
		return findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the object layout box columns where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectLayoutBoxColumnModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of object layout box columns
	 * @param end the upper bound of the range of object layout box columns (not inclusive)
	 * @return the range of matching object layout box columns
	 */
	@Override
	public List<ObjectLayoutBoxColumn> findByUuid(
		String uuid, int start, int end) {

		return findByUuid(uuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the object layout box columns where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectLayoutBoxColumnModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of object layout box columns
	 * @param end the upper bound of the range of object layout box columns (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching object layout box columns
	 */
	@Override
	public List<ObjectLayoutBoxColumn> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<ObjectLayoutBoxColumn> orderByComparator) {

		return findByUuid(uuid, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the object layout box columns where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectLayoutBoxColumnModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of object layout box columns
	 * @param end the upper bound of the range of object layout box columns (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching object layout box columns
	 */
	@Override
	public List<ObjectLayoutBoxColumn> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<ObjectLayoutBoxColumn> orderByComparator,
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

		List<ObjectLayoutBoxColumn> list = null;

		if (useFinderCache) {
			list = (List<ObjectLayoutBoxColumn>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (ObjectLayoutBoxColumn objectLayoutBoxColumn : list) {
					if (!uuid.equals(objectLayoutBoxColumn.getUuid())) {
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

			sb.append(_SQL_SELECT_OBJECTLAYOUTBOXCOLUMN_WHERE);

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
				sb.append(ObjectLayoutBoxColumnModelImpl.ORDER_BY_JPQL);
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

				list = (List<ObjectLayoutBoxColumn>)QueryUtil.list(
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
	 * Returns the first object layout box column in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object layout box column
	 * @throws NoSuchObjectLayoutBoxColumnException if a matching object layout box column could not be found
	 */
	@Override
	public ObjectLayoutBoxColumn findByUuid_First(
			String uuid,
			OrderByComparator<ObjectLayoutBoxColumn> orderByComparator)
		throws NoSuchObjectLayoutBoxColumnException {

		ObjectLayoutBoxColumn objectLayoutBoxColumn = fetchByUuid_First(
			uuid, orderByComparator);

		if (objectLayoutBoxColumn != null) {
			return objectLayoutBoxColumn;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append("}");

		throw new NoSuchObjectLayoutBoxColumnException(sb.toString());
	}

	/**
	 * Returns the first object layout box column in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object layout box column, or <code>null</code> if a matching object layout box column could not be found
	 */
	@Override
	public ObjectLayoutBoxColumn fetchByUuid_First(
		String uuid,
		OrderByComparator<ObjectLayoutBoxColumn> orderByComparator) {

		List<ObjectLayoutBoxColumn> list = findByUuid(
			uuid, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last object layout box column in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object layout box column
	 * @throws NoSuchObjectLayoutBoxColumnException if a matching object layout box column could not be found
	 */
	@Override
	public ObjectLayoutBoxColumn findByUuid_Last(
			String uuid,
			OrderByComparator<ObjectLayoutBoxColumn> orderByComparator)
		throws NoSuchObjectLayoutBoxColumnException {

		ObjectLayoutBoxColumn objectLayoutBoxColumn = fetchByUuid_Last(
			uuid, orderByComparator);

		if (objectLayoutBoxColumn != null) {
			return objectLayoutBoxColumn;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append("}");

		throw new NoSuchObjectLayoutBoxColumnException(sb.toString());
	}

	/**
	 * Returns the last object layout box column in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object layout box column, or <code>null</code> if a matching object layout box column could not be found
	 */
	@Override
	public ObjectLayoutBoxColumn fetchByUuid_Last(
		String uuid,
		OrderByComparator<ObjectLayoutBoxColumn> orderByComparator) {

		int count = countByUuid(uuid);

		if (count == 0) {
			return null;
		}

		List<ObjectLayoutBoxColumn> list = findByUuid(
			uuid, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the object layout box columns before and after the current object layout box column in the ordered set where uuid = &#63;.
	 *
	 * @param objectLayoutBoxColumnId the primary key of the current object layout box column
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next object layout box column
	 * @throws NoSuchObjectLayoutBoxColumnException if a object layout box column with the primary key could not be found
	 */
	@Override
	public ObjectLayoutBoxColumn[] findByUuid_PrevAndNext(
			long objectLayoutBoxColumnId, String uuid,
			OrderByComparator<ObjectLayoutBoxColumn> orderByComparator)
		throws NoSuchObjectLayoutBoxColumnException {

		uuid = Objects.toString(uuid, "");

		ObjectLayoutBoxColumn objectLayoutBoxColumn = findByPrimaryKey(
			objectLayoutBoxColumnId);

		Session session = null;

		try {
			session = openSession();

			ObjectLayoutBoxColumn[] array = new ObjectLayoutBoxColumnImpl[3];

			array[0] = getByUuid_PrevAndNext(
				session, objectLayoutBoxColumn, uuid, orderByComparator, true);

			array[1] = objectLayoutBoxColumn;

			array[2] = getByUuid_PrevAndNext(
				session, objectLayoutBoxColumn, uuid, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected ObjectLayoutBoxColumn getByUuid_PrevAndNext(
		Session session, ObjectLayoutBoxColumn objectLayoutBoxColumn,
		String uuid, OrderByComparator<ObjectLayoutBoxColumn> orderByComparator,
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

		sb.append(_SQL_SELECT_OBJECTLAYOUTBOXCOLUMN_WHERE);

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
			sb.append(ObjectLayoutBoxColumnModelImpl.ORDER_BY_JPQL);
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
						objectLayoutBoxColumn)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<ObjectLayoutBoxColumn> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the object layout box columns where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	@Override
	public void removeByUuid(String uuid) {
		for (ObjectLayoutBoxColumn objectLayoutBoxColumn :
				findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(objectLayoutBoxColumn);
		}
	}

	/**
	 * Returns the number of object layout box columns where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching object layout box columns
	 */
	@Override
	public int countByUuid(String uuid) {
		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = _finderPathCountByUuid;

		Object[] finderArgs = new Object[] {uuid};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_OBJECTLAYOUTBOXCOLUMN_WHERE);

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
		"objectLayoutBoxColumn.uuid = ?";

	private static final String _FINDER_COLUMN_UUID_UUID_3 =
		"(objectLayoutBoxColumn.uuid IS NULL OR objectLayoutBoxColumn.uuid = '')";

	private FinderPath _finderPathWithPaginationFindByUuid_C;
	private FinderPath _finderPathWithoutPaginationFindByUuid_C;
	private FinderPath _finderPathCountByUuid_C;

	/**
	 * Returns all the object layout box columns where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching object layout box columns
	 */
	@Override
	public List<ObjectLayoutBoxColumn> findByUuid_C(
		String uuid, long companyId) {

		return findByUuid_C(
			uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the object layout box columns where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectLayoutBoxColumnModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of object layout box columns
	 * @param end the upper bound of the range of object layout box columns (not inclusive)
	 * @return the range of matching object layout box columns
	 */
	@Override
	public List<ObjectLayoutBoxColumn> findByUuid_C(
		String uuid, long companyId, int start, int end) {

		return findByUuid_C(uuid, companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the object layout box columns where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectLayoutBoxColumnModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of object layout box columns
	 * @param end the upper bound of the range of object layout box columns (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching object layout box columns
	 */
	@Override
	public List<ObjectLayoutBoxColumn> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<ObjectLayoutBoxColumn> orderByComparator) {

		return findByUuid_C(
			uuid, companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the object layout box columns where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectLayoutBoxColumnModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of object layout box columns
	 * @param end the upper bound of the range of object layout box columns (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching object layout box columns
	 */
	@Override
	public List<ObjectLayoutBoxColumn> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<ObjectLayoutBoxColumn> orderByComparator,
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

		List<ObjectLayoutBoxColumn> list = null;

		if (useFinderCache) {
			list = (List<ObjectLayoutBoxColumn>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (ObjectLayoutBoxColumn objectLayoutBoxColumn : list) {
					if (!uuid.equals(objectLayoutBoxColumn.getUuid()) ||
						(companyId != objectLayoutBoxColumn.getCompanyId())) {

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

			sb.append(_SQL_SELECT_OBJECTLAYOUTBOXCOLUMN_WHERE);

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
				sb.append(ObjectLayoutBoxColumnModelImpl.ORDER_BY_JPQL);
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

				list = (List<ObjectLayoutBoxColumn>)QueryUtil.list(
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
	 * Returns the first object layout box column in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object layout box column
	 * @throws NoSuchObjectLayoutBoxColumnException if a matching object layout box column could not be found
	 */
	@Override
	public ObjectLayoutBoxColumn findByUuid_C_First(
			String uuid, long companyId,
			OrderByComparator<ObjectLayoutBoxColumn> orderByComparator)
		throws NoSuchObjectLayoutBoxColumnException {

		ObjectLayoutBoxColumn objectLayoutBoxColumn = fetchByUuid_C_First(
			uuid, companyId, orderByComparator);

		if (objectLayoutBoxColumn != null) {
			return objectLayoutBoxColumn;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append(", companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchObjectLayoutBoxColumnException(sb.toString());
	}

	/**
	 * Returns the first object layout box column in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object layout box column, or <code>null</code> if a matching object layout box column could not be found
	 */
	@Override
	public ObjectLayoutBoxColumn fetchByUuid_C_First(
		String uuid, long companyId,
		OrderByComparator<ObjectLayoutBoxColumn> orderByComparator) {

		List<ObjectLayoutBoxColumn> list = findByUuid_C(
			uuid, companyId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last object layout box column in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object layout box column
	 * @throws NoSuchObjectLayoutBoxColumnException if a matching object layout box column could not be found
	 */
	@Override
	public ObjectLayoutBoxColumn findByUuid_C_Last(
			String uuid, long companyId,
			OrderByComparator<ObjectLayoutBoxColumn> orderByComparator)
		throws NoSuchObjectLayoutBoxColumnException {

		ObjectLayoutBoxColumn objectLayoutBoxColumn = fetchByUuid_C_Last(
			uuid, companyId, orderByComparator);

		if (objectLayoutBoxColumn != null) {
			return objectLayoutBoxColumn;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append(", companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchObjectLayoutBoxColumnException(sb.toString());
	}

	/**
	 * Returns the last object layout box column in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object layout box column, or <code>null</code> if a matching object layout box column could not be found
	 */
	@Override
	public ObjectLayoutBoxColumn fetchByUuid_C_Last(
		String uuid, long companyId,
		OrderByComparator<ObjectLayoutBoxColumn> orderByComparator) {

		int count = countByUuid_C(uuid, companyId);

		if (count == 0) {
			return null;
		}

		List<ObjectLayoutBoxColumn> list = findByUuid_C(
			uuid, companyId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the object layout box columns before and after the current object layout box column in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param objectLayoutBoxColumnId the primary key of the current object layout box column
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next object layout box column
	 * @throws NoSuchObjectLayoutBoxColumnException if a object layout box column with the primary key could not be found
	 */
	@Override
	public ObjectLayoutBoxColumn[] findByUuid_C_PrevAndNext(
			long objectLayoutBoxColumnId, String uuid, long companyId,
			OrderByComparator<ObjectLayoutBoxColumn> orderByComparator)
		throws NoSuchObjectLayoutBoxColumnException {

		uuid = Objects.toString(uuid, "");

		ObjectLayoutBoxColumn objectLayoutBoxColumn = findByPrimaryKey(
			objectLayoutBoxColumnId);

		Session session = null;

		try {
			session = openSession();

			ObjectLayoutBoxColumn[] array = new ObjectLayoutBoxColumnImpl[3];

			array[0] = getByUuid_C_PrevAndNext(
				session, objectLayoutBoxColumn, uuid, companyId,
				orderByComparator, true);

			array[1] = objectLayoutBoxColumn;

			array[2] = getByUuid_C_PrevAndNext(
				session, objectLayoutBoxColumn, uuid, companyId,
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

	protected ObjectLayoutBoxColumn getByUuid_C_PrevAndNext(
		Session session, ObjectLayoutBoxColumn objectLayoutBoxColumn,
		String uuid, long companyId,
		OrderByComparator<ObjectLayoutBoxColumn> orderByComparator,
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

		sb.append(_SQL_SELECT_OBJECTLAYOUTBOXCOLUMN_WHERE);

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
			sb.append(ObjectLayoutBoxColumnModelImpl.ORDER_BY_JPQL);
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
						objectLayoutBoxColumn)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<ObjectLayoutBoxColumn> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the object layout box columns where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	@Override
	public void removeByUuid_C(String uuid, long companyId) {
		for (ObjectLayoutBoxColumn objectLayoutBoxColumn :
				findByUuid_C(
					uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(objectLayoutBoxColumn);
		}
	}

	/**
	 * Returns the number of object layout box columns where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching object layout box columns
	 */
	@Override
	public int countByUuid_C(String uuid, long companyId) {
		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = _finderPathCountByUuid_C;

		Object[] finderArgs = new Object[] {uuid, companyId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_OBJECTLAYOUTBOXCOLUMN_WHERE);

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
		"objectLayoutBoxColumn.uuid = ? AND ";

	private static final String _FINDER_COLUMN_UUID_C_UUID_3 =
		"(objectLayoutBoxColumn.uuid IS NULL OR objectLayoutBoxColumn.uuid = '') AND ";

	private static final String _FINDER_COLUMN_UUID_C_COMPANYID_2 =
		"objectLayoutBoxColumn.companyId = ?";

	private FinderPath _finderPathWithPaginationFindByObjectLayoutBoxRowId;
	private FinderPath _finderPathWithoutPaginationFindByObjectLayoutBoxRowId;
	private FinderPath _finderPathCountByObjectLayoutBoxRowId;

	/**
	 * Returns all the object layout box columns where objectLayoutBoxRowId = &#63;.
	 *
	 * @param objectLayoutBoxRowId the object layout box row ID
	 * @return the matching object layout box columns
	 */
	@Override
	public List<ObjectLayoutBoxColumn> findByObjectLayoutBoxRowId(
		long objectLayoutBoxRowId) {

		return findByObjectLayoutBoxRowId(
			objectLayoutBoxRowId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the object layout box columns where objectLayoutBoxRowId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectLayoutBoxColumnModelImpl</code>.
	 * </p>
	 *
	 * @param objectLayoutBoxRowId the object layout box row ID
	 * @param start the lower bound of the range of object layout box columns
	 * @param end the upper bound of the range of object layout box columns (not inclusive)
	 * @return the range of matching object layout box columns
	 */
	@Override
	public List<ObjectLayoutBoxColumn> findByObjectLayoutBoxRowId(
		long objectLayoutBoxRowId, int start, int end) {

		return findByObjectLayoutBoxRowId(
			objectLayoutBoxRowId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the object layout box columns where objectLayoutBoxRowId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectLayoutBoxColumnModelImpl</code>.
	 * </p>
	 *
	 * @param objectLayoutBoxRowId the object layout box row ID
	 * @param start the lower bound of the range of object layout box columns
	 * @param end the upper bound of the range of object layout box columns (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching object layout box columns
	 */
	@Override
	public List<ObjectLayoutBoxColumn> findByObjectLayoutBoxRowId(
		long objectLayoutBoxRowId, int start, int end,
		OrderByComparator<ObjectLayoutBoxColumn> orderByComparator) {

		return findByObjectLayoutBoxRowId(
			objectLayoutBoxRowId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the object layout box columns where objectLayoutBoxRowId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectLayoutBoxColumnModelImpl</code>.
	 * </p>
	 *
	 * @param objectLayoutBoxRowId the object layout box row ID
	 * @param start the lower bound of the range of object layout box columns
	 * @param end the upper bound of the range of object layout box columns (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching object layout box columns
	 */
	@Override
	public List<ObjectLayoutBoxColumn> findByObjectLayoutBoxRowId(
		long objectLayoutBoxRowId, int start, int end,
		OrderByComparator<ObjectLayoutBoxColumn> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath =
					_finderPathWithoutPaginationFindByObjectLayoutBoxRowId;
				finderArgs = new Object[] {objectLayoutBoxRowId};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByObjectLayoutBoxRowId;
			finderArgs = new Object[] {
				objectLayoutBoxRowId, start, end, orderByComparator
			};
		}

		List<ObjectLayoutBoxColumn> list = null;

		if (useFinderCache) {
			list = (List<ObjectLayoutBoxColumn>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (ObjectLayoutBoxColumn objectLayoutBoxColumn : list) {
					if (objectLayoutBoxRowId !=
							objectLayoutBoxColumn.getObjectLayoutBoxRowId()) {

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

			sb.append(_SQL_SELECT_OBJECTLAYOUTBOXCOLUMN_WHERE);

			sb.append(
				_FINDER_COLUMN_OBJECTLAYOUTBOXROWID_OBJECTLAYOUTBOXROWID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(ObjectLayoutBoxColumnModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(objectLayoutBoxRowId);

				list = (List<ObjectLayoutBoxColumn>)QueryUtil.list(
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
	 * Returns the first object layout box column in the ordered set where objectLayoutBoxRowId = &#63;.
	 *
	 * @param objectLayoutBoxRowId the object layout box row ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object layout box column
	 * @throws NoSuchObjectLayoutBoxColumnException if a matching object layout box column could not be found
	 */
	@Override
	public ObjectLayoutBoxColumn findByObjectLayoutBoxRowId_First(
			long objectLayoutBoxRowId,
			OrderByComparator<ObjectLayoutBoxColumn> orderByComparator)
		throws NoSuchObjectLayoutBoxColumnException {

		ObjectLayoutBoxColumn objectLayoutBoxColumn =
			fetchByObjectLayoutBoxRowId_First(
				objectLayoutBoxRowId, orderByComparator);

		if (objectLayoutBoxColumn != null) {
			return objectLayoutBoxColumn;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("objectLayoutBoxRowId=");
		sb.append(objectLayoutBoxRowId);

		sb.append("}");

		throw new NoSuchObjectLayoutBoxColumnException(sb.toString());
	}

	/**
	 * Returns the first object layout box column in the ordered set where objectLayoutBoxRowId = &#63;.
	 *
	 * @param objectLayoutBoxRowId the object layout box row ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object layout box column, or <code>null</code> if a matching object layout box column could not be found
	 */
	@Override
	public ObjectLayoutBoxColumn fetchByObjectLayoutBoxRowId_First(
		long objectLayoutBoxRowId,
		OrderByComparator<ObjectLayoutBoxColumn> orderByComparator) {

		List<ObjectLayoutBoxColumn> list = findByObjectLayoutBoxRowId(
			objectLayoutBoxRowId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last object layout box column in the ordered set where objectLayoutBoxRowId = &#63;.
	 *
	 * @param objectLayoutBoxRowId the object layout box row ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object layout box column
	 * @throws NoSuchObjectLayoutBoxColumnException if a matching object layout box column could not be found
	 */
	@Override
	public ObjectLayoutBoxColumn findByObjectLayoutBoxRowId_Last(
			long objectLayoutBoxRowId,
			OrderByComparator<ObjectLayoutBoxColumn> orderByComparator)
		throws NoSuchObjectLayoutBoxColumnException {

		ObjectLayoutBoxColumn objectLayoutBoxColumn =
			fetchByObjectLayoutBoxRowId_Last(
				objectLayoutBoxRowId, orderByComparator);

		if (objectLayoutBoxColumn != null) {
			return objectLayoutBoxColumn;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("objectLayoutBoxRowId=");
		sb.append(objectLayoutBoxRowId);

		sb.append("}");

		throw new NoSuchObjectLayoutBoxColumnException(sb.toString());
	}

	/**
	 * Returns the last object layout box column in the ordered set where objectLayoutBoxRowId = &#63;.
	 *
	 * @param objectLayoutBoxRowId the object layout box row ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object layout box column, or <code>null</code> if a matching object layout box column could not be found
	 */
	@Override
	public ObjectLayoutBoxColumn fetchByObjectLayoutBoxRowId_Last(
		long objectLayoutBoxRowId,
		OrderByComparator<ObjectLayoutBoxColumn> orderByComparator) {

		int count = countByObjectLayoutBoxRowId(objectLayoutBoxRowId);

		if (count == 0) {
			return null;
		}

		List<ObjectLayoutBoxColumn> list = findByObjectLayoutBoxRowId(
			objectLayoutBoxRowId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the object layout box columns before and after the current object layout box column in the ordered set where objectLayoutBoxRowId = &#63;.
	 *
	 * @param objectLayoutBoxColumnId the primary key of the current object layout box column
	 * @param objectLayoutBoxRowId the object layout box row ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next object layout box column
	 * @throws NoSuchObjectLayoutBoxColumnException if a object layout box column with the primary key could not be found
	 */
	@Override
	public ObjectLayoutBoxColumn[] findByObjectLayoutBoxRowId_PrevAndNext(
			long objectLayoutBoxColumnId, long objectLayoutBoxRowId,
			OrderByComparator<ObjectLayoutBoxColumn> orderByComparator)
		throws NoSuchObjectLayoutBoxColumnException {

		ObjectLayoutBoxColumn objectLayoutBoxColumn = findByPrimaryKey(
			objectLayoutBoxColumnId);

		Session session = null;

		try {
			session = openSession();

			ObjectLayoutBoxColumn[] array = new ObjectLayoutBoxColumnImpl[3];

			array[0] = getByObjectLayoutBoxRowId_PrevAndNext(
				session, objectLayoutBoxColumn, objectLayoutBoxRowId,
				orderByComparator, true);

			array[1] = objectLayoutBoxColumn;

			array[2] = getByObjectLayoutBoxRowId_PrevAndNext(
				session, objectLayoutBoxColumn, objectLayoutBoxRowId,
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

	protected ObjectLayoutBoxColumn getByObjectLayoutBoxRowId_PrevAndNext(
		Session session, ObjectLayoutBoxColumn objectLayoutBoxColumn,
		long objectLayoutBoxRowId,
		OrderByComparator<ObjectLayoutBoxColumn> orderByComparator,
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

		sb.append(_SQL_SELECT_OBJECTLAYOUTBOXCOLUMN_WHERE);

		sb.append(_FINDER_COLUMN_OBJECTLAYOUTBOXROWID_OBJECTLAYOUTBOXROWID_2);

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
			sb.append(ObjectLayoutBoxColumnModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(objectLayoutBoxRowId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						objectLayoutBoxColumn)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<ObjectLayoutBoxColumn> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the object layout box columns where objectLayoutBoxRowId = &#63; from the database.
	 *
	 * @param objectLayoutBoxRowId the object layout box row ID
	 */
	@Override
	public void removeByObjectLayoutBoxRowId(long objectLayoutBoxRowId) {
		for (ObjectLayoutBoxColumn objectLayoutBoxColumn :
				findByObjectLayoutBoxRowId(
					objectLayoutBoxRowId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(objectLayoutBoxColumn);
		}
	}

	/**
	 * Returns the number of object layout box columns where objectLayoutBoxRowId = &#63;.
	 *
	 * @param objectLayoutBoxRowId the object layout box row ID
	 * @return the number of matching object layout box columns
	 */
	@Override
	public int countByObjectLayoutBoxRowId(long objectLayoutBoxRowId) {
		FinderPath finderPath = _finderPathCountByObjectLayoutBoxRowId;

		Object[] finderArgs = new Object[] {objectLayoutBoxRowId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_OBJECTLAYOUTBOXCOLUMN_WHERE);

			sb.append(
				_FINDER_COLUMN_OBJECTLAYOUTBOXROWID_OBJECTLAYOUTBOXROWID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(objectLayoutBoxRowId);

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
		_FINDER_COLUMN_OBJECTLAYOUTBOXROWID_OBJECTLAYOUTBOXROWID_2 =
			"objectLayoutBoxColumn.objectLayoutBoxRowId = ?";

	public ObjectLayoutBoxColumnPersistenceImpl() {
		Map<String, String> dbColumnNames = new HashMap<String, String>();

		dbColumnNames.put("uuid", "uuid_");

		setDBColumnNames(dbColumnNames);

		setModelClass(ObjectLayoutBoxColumn.class);

		setModelImplClass(ObjectLayoutBoxColumnImpl.class);
		setModelPKClass(long.class);

		setTable(ObjectLayoutBoxColumnTable.INSTANCE);
	}

	/**
	 * Caches the object layout box column in the entity cache if it is enabled.
	 *
	 * @param objectLayoutBoxColumn the object layout box column
	 */
	@Override
	public void cacheResult(ObjectLayoutBoxColumn objectLayoutBoxColumn) {
		entityCache.putResult(
			ObjectLayoutBoxColumnImpl.class,
			objectLayoutBoxColumn.getPrimaryKey(), objectLayoutBoxColumn);
	}

	/**
	 * Caches the object layout box columns in the entity cache if it is enabled.
	 *
	 * @param objectLayoutBoxColumns the object layout box columns
	 */
	@Override
	public void cacheResult(
		List<ObjectLayoutBoxColumn> objectLayoutBoxColumns) {

		for (ObjectLayoutBoxColumn objectLayoutBoxColumn :
				objectLayoutBoxColumns) {

			if (entityCache.getResult(
					ObjectLayoutBoxColumnImpl.class,
					objectLayoutBoxColumn.getPrimaryKey()) == null) {

				cacheResult(objectLayoutBoxColumn);
			}
		}
	}

	/**
	 * Clears the cache for all object layout box columns.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(ObjectLayoutBoxColumnImpl.class);

		finderCache.clearCache(ObjectLayoutBoxColumnImpl.class);
	}

	/**
	 * Clears the cache for the object layout box column.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(ObjectLayoutBoxColumn objectLayoutBoxColumn) {
		entityCache.removeResult(
			ObjectLayoutBoxColumnImpl.class, objectLayoutBoxColumn);
	}

	@Override
	public void clearCache(List<ObjectLayoutBoxColumn> objectLayoutBoxColumns) {
		for (ObjectLayoutBoxColumn objectLayoutBoxColumn :
				objectLayoutBoxColumns) {

			entityCache.removeResult(
				ObjectLayoutBoxColumnImpl.class, objectLayoutBoxColumn);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(ObjectLayoutBoxColumnImpl.class);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(
				ObjectLayoutBoxColumnImpl.class, primaryKey);
		}
	}

	/**
	 * Creates a new object layout box column with the primary key. Does not add the object layout box column to the database.
	 *
	 * @param objectLayoutBoxColumnId the primary key for the new object layout box column
	 * @return the new object layout box column
	 */
	@Override
	public ObjectLayoutBoxColumn create(long objectLayoutBoxColumnId) {
		ObjectLayoutBoxColumn objectLayoutBoxColumn =
			new ObjectLayoutBoxColumnImpl();

		objectLayoutBoxColumn.setNew(true);
		objectLayoutBoxColumn.setPrimaryKey(objectLayoutBoxColumnId);

		String uuid = PortalUUIDUtil.generate();

		objectLayoutBoxColumn.setUuid(uuid);

		objectLayoutBoxColumn.setCompanyId(CompanyThreadLocal.getCompanyId());

		return objectLayoutBoxColumn;
	}

	/**
	 * Removes the object layout box column with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param objectLayoutBoxColumnId the primary key of the object layout box column
	 * @return the object layout box column that was removed
	 * @throws NoSuchObjectLayoutBoxColumnException if a object layout box column with the primary key could not be found
	 */
	@Override
	public ObjectLayoutBoxColumn remove(long objectLayoutBoxColumnId)
		throws NoSuchObjectLayoutBoxColumnException {

		return remove((Serializable)objectLayoutBoxColumnId);
	}

	/**
	 * Removes the object layout box column with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the object layout box column
	 * @return the object layout box column that was removed
	 * @throws NoSuchObjectLayoutBoxColumnException if a object layout box column with the primary key could not be found
	 */
	@Override
	public ObjectLayoutBoxColumn remove(Serializable primaryKey)
		throws NoSuchObjectLayoutBoxColumnException {

		Session session = null;

		try {
			session = openSession();

			ObjectLayoutBoxColumn objectLayoutBoxColumn =
				(ObjectLayoutBoxColumn)session.get(
					ObjectLayoutBoxColumnImpl.class, primaryKey);

			if (objectLayoutBoxColumn == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchObjectLayoutBoxColumnException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(objectLayoutBoxColumn);
		}
		catch (NoSuchObjectLayoutBoxColumnException noSuchEntityException) {
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
	protected ObjectLayoutBoxColumn removeImpl(
		ObjectLayoutBoxColumn objectLayoutBoxColumn) {

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(objectLayoutBoxColumn)) {
				objectLayoutBoxColumn = (ObjectLayoutBoxColumn)session.get(
					ObjectLayoutBoxColumnImpl.class,
					objectLayoutBoxColumn.getPrimaryKeyObj());
			}

			if (objectLayoutBoxColumn != null) {
				session.delete(objectLayoutBoxColumn);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (objectLayoutBoxColumn != null) {
			clearCache(objectLayoutBoxColumn);
		}

		return objectLayoutBoxColumn;
	}

	@Override
	public ObjectLayoutBoxColumn updateImpl(
		ObjectLayoutBoxColumn objectLayoutBoxColumn) {

		boolean isNew = objectLayoutBoxColumn.isNew();

		if (!(objectLayoutBoxColumn instanceof
				ObjectLayoutBoxColumnModelImpl)) {

			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(objectLayoutBoxColumn.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					objectLayoutBoxColumn);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in objectLayoutBoxColumn proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom ObjectLayoutBoxColumn implementation " +
					objectLayoutBoxColumn.getClass());
		}

		ObjectLayoutBoxColumnModelImpl objectLayoutBoxColumnModelImpl =
			(ObjectLayoutBoxColumnModelImpl)objectLayoutBoxColumn;

		if (Validator.isNull(objectLayoutBoxColumn.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			objectLayoutBoxColumn.setUuid(uuid);
		}

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Date date = new Date();

		if (isNew && (objectLayoutBoxColumn.getCreateDate() == null)) {
			if (serviceContext == null) {
				objectLayoutBoxColumn.setCreateDate(date);
			}
			else {
				objectLayoutBoxColumn.setCreateDate(
					serviceContext.getCreateDate(date));
			}
		}

		if (!objectLayoutBoxColumnModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				objectLayoutBoxColumn.setModifiedDate(date);
			}
			else {
				objectLayoutBoxColumn.setModifiedDate(
					serviceContext.getModifiedDate(date));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (isNew) {
				session.save(objectLayoutBoxColumn);
			}
			else {
				objectLayoutBoxColumn = (ObjectLayoutBoxColumn)session.merge(
					objectLayoutBoxColumn);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		entityCache.putResult(
			ObjectLayoutBoxColumnImpl.class, objectLayoutBoxColumnModelImpl,
			false, true);

		if (isNew) {
			objectLayoutBoxColumn.setNew(false);
		}

		objectLayoutBoxColumn.resetOriginalValues();

		return objectLayoutBoxColumn;
	}

	/**
	 * Returns the object layout box column with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the object layout box column
	 * @return the object layout box column
	 * @throws NoSuchObjectLayoutBoxColumnException if a object layout box column with the primary key could not be found
	 */
	@Override
	public ObjectLayoutBoxColumn findByPrimaryKey(Serializable primaryKey)
		throws NoSuchObjectLayoutBoxColumnException {

		ObjectLayoutBoxColumn objectLayoutBoxColumn = fetchByPrimaryKey(
			primaryKey);

		if (objectLayoutBoxColumn == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchObjectLayoutBoxColumnException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return objectLayoutBoxColumn;
	}

	/**
	 * Returns the object layout box column with the primary key or throws a <code>NoSuchObjectLayoutBoxColumnException</code> if it could not be found.
	 *
	 * @param objectLayoutBoxColumnId the primary key of the object layout box column
	 * @return the object layout box column
	 * @throws NoSuchObjectLayoutBoxColumnException if a object layout box column with the primary key could not be found
	 */
	@Override
	public ObjectLayoutBoxColumn findByPrimaryKey(long objectLayoutBoxColumnId)
		throws NoSuchObjectLayoutBoxColumnException {

		return findByPrimaryKey((Serializable)objectLayoutBoxColumnId);
	}

	/**
	 * Returns the object layout box column with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param objectLayoutBoxColumnId the primary key of the object layout box column
	 * @return the object layout box column, or <code>null</code> if a object layout box column with the primary key could not be found
	 */
	@Override
	public ObjectLayoutBoxColumn fetchByPrimaryKey(
		long objectLayoutBoxColumnId) {

		return fetchByPrimaryKey((Serializable)objectLayoutBoxColumnId);
	}

	/**
	 * Returns all the object layout box columns.
	 *
	 * @return the object layout box columns
	 */
	@Override
	public List<ObjectLayoutBoxColumn> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the object layout box columns.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectLayoutBoxColumnModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of object layout box columns
	 * @param end the upper bound of the range of object layout box columns (not inclusive)
	 * @return the range of object layout box columns
	 */
	@Override
	public List<ObjectLayoutBoxColumn> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the object layout box columns.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectLayoutBoxColumnModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of object layout box columns
	 * @param end the upper bound of the range of object layout box columns (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of object layout box columns
	 */
	@Override
	public List<ObjectLayoutBoxColumn> findAll(
		int start, int end,
		OrderByComparator<ObjectLayoutBoxColumn> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the object layout box columns.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectLayoutBoxColumnModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of object layout box columns
	 * @param end the upper bound of the range of object layout box columns (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of object layout box columns
	 */
	@Override
	public List<ObjectLayoutBoxColumn> findAll(
		int start, int end,
		OrderByComparator<ObjectLayoutBoxColumn> orderByComparator,
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

		List<ObjectLayoutBoxColumn> list = null;

		if (useFinderCache) {
			list = (List<ObjectLayoutBoxColumn>)finderCache.getResult(
				finderPath, finderArgs);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_OBJECTLAYOUTBOXCOLUMN);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_OBJECTLAYOUTBOXCOLUMN;

				sql = sql.concat(ObjectLayoutBoxColumnModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<ObjectLayoutBoxColumn>)QueryUtil.list(
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
	 * Removes all the object layout box columns from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (ObjectLayoutBoxColumn objectLayoutBoxColumn : findAll()) {
			remove(objectLayoutBoxColumn);
		}
	}

	/**
	 * Returns the number of object layout box columns.
	 *
	 * @return the number of object layout box columns
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
					_SQL_COUNT_OBJECTLAYOUTBOXCOLUMN);

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
		return "objectLayoutBoxColumnId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_OBJECTLAYOUTBOXCOLUMN;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return ObjectLayoutBoxColumnModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the object layout box column persistence.
	 */
	@Activate
	public void activate() {
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

		_finderPathWithPaginationFindByObjectLayoutBoxRowId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByObjectLayoutBoxRowId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"objectLayoutBoxRowId"}, true);

		_finderPathWithoutPaginationFindByObjectLayoutBoxRowId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByObjectLayoutBoxRowId", new String[] {Long.class.getName()},
			new String[] {"objectLayoutBoxRowId"}, true);

		_finderPathCountByObjectLayoutBoxRowId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByObjectLayoutBoxRowId", new String[] {Long.class.getName()},
			new String[] {"objectLayoutBoxRowId"}, false);
	}

	@Deactivate
	public void deactivate() {
		entityCache.removeCache(ObjectLayoutBoxColumnImpl.class.getName());
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

	private static final String _SQL_SELECT_OBJECTLAYOUTBOXCOLUMN =
		"SELECT objectLayoutBoxColumn FROM ObjectLayoutBoxColumn objectLayoutBoxColumn";

	private static final String _SQL_SELECT_OBJECTLAYOUTBOXCOLUMN_WHERE =
		"SELECT objectLayoutBoxColumn FROM ObjectLayoutBoxColumn objectLayoutBoxColumn WHERE ";

	private static final String _SQL_COUNT_OBJECTLAYOUTBOXCOLUMN =
		"SELECT COUNT(objectLayoutBoxColumn) FROM ObjectLayoutBoxColumn objectLayoutBoxColumn";

	private static final String _SQL_COUNT_OBJECTLAYOUTBOXCOLUMN_WHERE =
		"SELECT COUNT(objectLayoutBoxColumn) FROM ObjectLayoutBoxColumn objectLayoutBoxColumn WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS =
		"objectLayoutBoxColumn.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No ObjectLayoutBoxColumn exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No ObjectLayoutBoxColumn exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		ObjectLayoutBoxColumnPersistenceImpl.class);

	private static final Set<String> _badColumnNames = SetUtil.fromArray(
		new String[] {"uuid"});

	@Override
	protected FinderCache getFinderCache() {
		return finderCache;
	}

	@Reference
	private ObjectLayoutBoxColumnModelArgumentsResolver
		_objectLayoutBoxColumnModelArgumentsResolver;

}