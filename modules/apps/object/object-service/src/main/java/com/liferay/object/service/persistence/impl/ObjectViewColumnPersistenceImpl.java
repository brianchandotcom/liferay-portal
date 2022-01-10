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

import com.liferay.object.exception.NoSuchObjectViewColumnException;
import com.liferay.object.model.ObjectViewColumn;
import com.liferay.object.model.ObjectViewColumnTable;
import com.liferay.object.model.impl.ObjectViewColumnImpl;
import com.liferay.object.model.impl.ObjectViewColumnModelImpl;
import com.liferay.object.service.persistence.ObjectViewColumnPersistence;
import com.liferay.object.service.persistence.ObjectViewColumnUtil;
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
 * The persistence implementation for the object view column service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Marco Leo
 * @generated
 */
@Component(service = {ObjectViewColumnPersistence.class, BasePersistence.class})
public class ObjectViewColumnPersistenceImpl
	extends BasePersistenceImpl<ObjectViewColumn>
	implements ObjectViewColumnPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>ObjectViewColumnUtil</code> to access the object view column persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		ObjectViewColumnImpl.class.getName();

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
	 * Returns all the object view columns where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching object view columns
	 */
	@Override
	public List<ObjectViewColumn> findByUuid(String uuid) {
		return findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the object view columns where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectViewColumnModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of object view columns
	 * @param end the upper bound of the range of object view columns (not inclusive)
	 * @return the range of matching object view columns
	 */
	@Override
	public List<ObjectViewColumn> findByUuid(String uuid, int start, int end) {
		return findByUuid(uuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the object view columns where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectViewColumnModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of object view columns
	 * @param end the upper bound of the range of object view columns (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching object view columns
	 */
	@Override
	public List<ObjectViewColumn> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<ObjectViewColumn> orderByComparator) {

		return findByUuid(uuid, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the object view columns where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectViewColumnModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of object view columns
	 * @param end the upper bound of the range of object view columns (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching object view columns
	 */
	@Override
	public List<ObjectViewColumn> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<ObjectViewColumn> orderByComparator,
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

		List<ObjectViewColumn> list = null;

		if (useFinderCache) {
			list = (List<ObjectViewColumn>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (ObjectViewColumn objectViewColumn : list) {
					if (!uuid.equals(objectViewColumn.getUuid())) {
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

			sb.append(_SQL_SELECT_OBJECTVIEWCOLUMN_WHERE);

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
				sb.append(ObjectViewColumnModelImpl.ORDER_BY_JPQL);
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

				list = (List<ObjectViewColumn>)QueryUtil.list(
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
	 * Returns the first object view column in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object view column
	 * @throws NoSuchObjectViewColumnException if a matching object view column could not be found
	 */
	@Override
	public ObjectViewColumn findByUuid_First(
			String uuid, OrderByComparator<ObjectViewColumn> orderByComparator)
		throws NoSuchObjectViewColumnException {

		ObjectViewColumn objectViewColumn = fetchByUuid_First(
			uuid, orderByComparator);

		if (objectViewColumn != null) {
			return objectViewColumn;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append("}");

		throw new NoSuchObjectViewColumnException(sb.toString());
	}

	/**
	 * Returns the first object view column in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object view column, or <code>null</code> if a matching object view column could not be found
	 */
	@Override
	public ObjectViewColumn fetchByUuid_First(
		String uuid, OrderByComparator<ObjectViewColumn> orderByComparator) {

		List<ObjectViewColumn> list = findByUuid(uuid, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last object view column in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object view column
	 * @throws NoSuchObjectViewColumnException if a matching object view column could not be found
	 */
	@Override
	public ObjectViewColumn findByUuid_Last(
			String uuid, OrderByComparator<ObjectViewColumn> orderByComparator)
		throws NoSuchObjectViewColumnException {

		ObjectViewColumn objectViewColumn = fetchByUuid_Last(
			uuid, orderByComparator);

		if (objectViewColumn != null) {
			return objectViewColumn;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append("}");

		throw new NoSuchObjectViewColumnException(sb.toString());
	}

	/**
	 * Returns the last object view column in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object view column, or <code>null</code> if a matching object view column could not be found
	 */
	@Override
	public ObjectViewColumn fetchByUuid_Last(
		String uuid, OrderByComparator<ObjectViewColumn> orderByComparator) {

		int count = countByUuid(uuid);

		if (count == 0) {
			return null;
		}

		List<ObjectViewColumn> list = findByUuid(
			uuid, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the object view columns before and after the current object view column in the ordered set where uuid = &#63;.
	 *
	 * @param objectViewColumnId the primary key of the current object view column
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next object view column
	 * @throws NoSuchObjectViewColumnException if a object view column with the primary key could not be found
	 */
	@Override
	public ObjectViewColumn[] findByUuid_PrevAndNext(
			long objectViewColumnId, String uuid,
			OrderByComparator<ObjectViewColumn> orderByComparator)
		throws NoSuchObjectViewColumnException {

		uuid = Objects.toString(uuid, "");

		ObjectViewColumn objectViewColumn = findByPrimaryKey(
			objectViewColumnId);

		Session session = null;

		try {
			session = openSession();

			ObjectViewColumn[] array = new ObjectViewColumnImpl[3];

			array[0] = getByUuid_PrevAndNext(
				session, objectViewColumn, uuid, orderByComparator, true);

			array[1] = objectViewColumn;

			array[2] = getByUuid_PrevAndNext(
				session, objectViewColumn, uuid, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected ObjectViewColumn getByUuid_PrevAndNext(
		Session session, ObjectViewColumn objectViewColumn, String uuid,
		OrderByComparator<ObjectViewColumn> orderByComparator,
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

		sb.append(_SQL_SELECT_OBJECTVIEWCOLUMN_WHERE);

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
			sb.append(ObjectViewColumnModelImpl.ORDER_BY_JPQL);
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
						objectViewColumn)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<ObjectViewColumn> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the object view columns where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	@Override
	public void removeByUuid(String uuid) {
		for (ObjectViewColumn objectViewColumn :
				findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(objectViewColumn);
		}
	}

	/**
	 * Returns the number of object view columns where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching object view columns
	 */
	@Override
	public int countByUuid(String uuid) {
		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = _finderPathCountByUuid;

		Object[] finderArgs = new Object[] {uuid};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_OBJECTVIEWCOLUMN_WHERE);

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
		"objectViewColumn.uuid = ?";

	private static final String _FINDER_COLUMN_UUID_UUID_3 =
		"(objectViewColumn.uuid IS NULL OR objectViewColumn.uuid = '')";

	private FinderPath _finderPathWithPaginationFindByUuid_C;
	private FinderPath _finderPathWithoutPaginationFindByUuid_C;
	private FinderPath _finderPathCountByUuid_C;

	/**
	 * Returns all the object view columns where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching object view columns
	 */
	@Override
	public List<ObjectViewColumn> findByUuid_C(String uuid, long companyId) {
		return findByUuid_C(
			uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the object view columns where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectViewColumnModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of object view columns
	 * @param end the upper bound of the range of object view columns (not inclusive)
	 * @return the range of matching object view columns
	 */
	@Override
	public List<ObjectViewColumn> findByUuid_C(
		String uuid, long companyId, int start, int end) {

		return findByUuid_C(uuid, companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the object view columns where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectViewColumnModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of object view columns
	 * @param end the upper bound of the range of object view columns (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching object view columns
	 */
	@Override
	public List<ObjectViewColumn> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<ObjectViewColumn> orderByComparator) {

		return findByUuid_C(
			uuid, companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the object view columns where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectViewColumnModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of object view columns
	 * @param end the upper bound of the range of object view columns (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching object view columns
	 */
	@Override
	public List<ObjectViewColumn> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<ObjectViewColumn> orderByComparator,
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

		List<ObjectViewColumn> list = null;

		if (useFinderCache) {
			list = (List<ObjectViewColumn>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (ObjectViewColumn objectViewColumn : list) {
					if (!uuid.equals(objectViewColumn.getUuid()) ||
						(companyId != objectViewColumn.getCompanyId())) {

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

			sb.append(_SQL_SELECT_OBJECTVIEWCOLUMN_WHERE);

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
				sb.append(ObjectViewColumnModelImpl.ORDER_BY_JPQL);
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

				list = (List<ObjectViewColumn>)QueryUtil.list(
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
	 * Returns the first object view column in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object view column
	 * @throws NoSuchObjectViewColumnException if a matching object view column could not be found
	 */
	@Override
	public ObjectViewColumn findByUuid_C_First(
			String uuid, long companyId,
			OrderByComparator<ObjectViewColumn> orderByComparator)
		throws NoSuchObjectViewColumnException {

		ObjectViewColumn objectViewColumn = fetchByUuid_C_First(
			uuid, companyId, orderByComparator);

		if (objectViewColumn != null) {
			return objectViewColumn;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append(", companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchObjectViewColumnException(sb.toString());
	}

	/**
	 * Returns the first object view column in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object view column, or <code>null</code> if a matching object view column could not be found
	 */
	@Override
	public ObjectViewColumn fetchByUuid_C_First(
		String uuid, long companyId,
		OrderByComparator<ObjectViewColumn> orderByComparator) {

		List<ObjectViewColumn> list = findByUuid_C(
			uuid, companyId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last object view column in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object view column
	 * @throws NoSuchObjectViewColumnException if a matching object view column could not be found
	 */
	@Override
	public ObjectViewColumn findByUuid_C_Last(
			String uuid, long companyId,
			OrderByComparator<ObjectViewColumn> orderByComparator)
		throws NoSuchObjectViewColumnException {

		ObjectViewColumn objectViewColumn = fetchByUuid_C_Last(
			uuid, companyId, orderByComparator);

		if (objectViewColumn != null) {
			return objectViewColumn;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append(", companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchObjectViewColumnException(sb.toString());
	}

	/**
	 * Returns the last object view column in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object view column, or <code>null</code> if a matching object view column could not be found
	 */
	@Override
	public ObjectViewColumn fetchByUuid_C_Last(
		String uuid, long companyId,
		OrderByComparator<ObjectViewColumn> orderByComparator) {

		int count = countByUuid_C(uuid, companyId);

		if (count == 0) {
			return null;
		}

		List<ObjectViewColumn> list = findByUuid_C(
			uuid, companyId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the object view columns before and after the current object view column in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param objectViewColumnId the primary key of the current object view column
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next object view column
	 * @throws NoSuchObjectViewColumnException if a object view column with the primary key could not be found
	 */
	@Override
	public ObjectViewColumn[] findByUuid_C_PrevAndNext(
			long objectViewColumnId, String uuid, long companyId,
			OrderByComparator<ObjectViewColumn> orderByComparator)
		throws NoSuchObjectViewColumnException {

		uuid = Objects.toString(uuid, "");

		ObjectViewColumn objectViewColumn = findByPrimaryKey(
			objectViewColumnId);

		Session session = null;

		try {
			session = openSession();

			ObjectViewColumn[] array = new ObjectViewColumnImpl[3];

			array[0] = getByUuid_C_PrevAndNext(
				session, objectViewColumn, uuid, companyId, orderByComparator,
				true);

			array[1] = objectViewColumn;

			array[2] = getByUuid_C_PrevAndNext(
				session, objectViewColumn, uuid, companyId, orderByComparator,
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

	protected ObjectViewColumn getByUuid_C_PrevAndNext(
		Session session, ObjectViewColumn objectViewColumn, String uuid,
		long companyId, OrderByComparator<ObjectViewColumn> orderByComparator,
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

		sb.append(_SQL_SELECT_OBJECTVIEWCOLUMN_WHERE);

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
			sb.append(ObjectViewColumnModelImpl.ORDER_BY_JPQL);
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
						objectViewColumn)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<ObjectViewColumn> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the object view columns where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	@Override
	public void removeByUuid_C(String uuid, long companyId) {
		for (ObjectViewColumn objectViewColumn :
				findByUuid_C(
					uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(objectViewColumn);
		}
	}

	/**
	 * Returns the number of object view columns where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching object view columns
	 */
	@Override
	public int countByUuid_C(String uuid, long companyId) {
		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = _finderPathCountByUuid_C;

		Object[] finderArgs = new Object[] {uuid, companyId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_OBJECTVIEWCOLUMN_WHERE);

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
		"objectViewColumn.uuid = ? AND ";

	private static final String _FINDER_COLUMN_UUID_C_UUID_3 =
		"(objectViewColumn.uuid IS NULL OR objectViewColumn.uuid = '') AND ";

	private static final String _FINDER_COLUMN_UUID_C_COMPANYID_2 =
		"objectViewColumn.companyId = ?";

	private FinderPath _finderPathWithPaginationFindByObjectViewId;
	private FinderPath _finderPathWithoutPaginationFindByObjectViewId;
	private FinderPath _finderPathCountByObjectViewId;

	/**
	 * Returns all the object view columns where objectViewId = &#63;.
	 *
	 * @param objectViewId the object view ID
	 * @return the matching object view columns
	 */
	@Override
	public List<ObjectViewColumn> findByObjectViewId(long objectViewId) {
		return findByObjectViewId(
			objectViewId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the object view columns where objectViewId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectViewColumnModelImpl</code>.
	 * </p>
	 *
	 * @param objectViewId the object view ID
	 * @param start the lower bound of the range of object view columns
	 * @param end the upper bound of the range of object view columns (not inclusive)
	 * @return the range of matching object view columns
	 */
	@Override
	public List<ObjectViewColumn> findByObjectViewId(
		long objectViewId, int start, int end) {

		return findByObjectViewId(objectViewId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the object view columns where objectViewId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectViewColumnModelImpl</code>.
	 * </p>
	 *
	 * @param objectViewId the object view ID
	 * @param start the lower bound of the range of object view columns
	 * @param end the upper bound of the range of object view columns (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching object view columns
	 */
	@Override
	public List<ObjectViewColumn> findByObjectViewId(
		long objectViewId, int start, int end,
		OrderByComparator<ObjectViewColumn> orderByComparator) {

		return findByObjectViewId(
			objectViewId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the object view columns where objectViewId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectViewColumnModelImpl</code>.
	 * </p>
	 *
	 * @param objectViewId the object view ID
	 * @param start the lower bound of the range of object view columns
	 * @param end the upper bound of the range of object view columns (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching object view columns
	 */
	@Override
	public List<ObjectViewColumn> findByObjectViewId(
		long objectViewId, int start, int end,
		OrderByComparator<ObjectViewColumn> orderByComparator,
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

		List<ObjectViewColumn> list = null;

		if (useFinderCache) {
			list = (List<ObjectViewColumn>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (ObjectViewColumn objectViewColumn : list) {
					if (objectViewId != objectViewColumn.getObjectViewId()) {
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

			sb.append(_SQL_SELECT_OBJECTVIEWCOLUMN_WHERE);

			sb.append(_FINDER_COLUMN_OBJECTVIEWID_OBJECTVIEWID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(ObjectViewColumnModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(objectViewId);

				list = (List<ObjectViewColumn>)QueryUtil.list(
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
	 * Returns the first object view column in the ordered set where objectViewId = &#63;.
	 *
	 * @param objectViewId the object view ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object view column
	 * @throws NoSuchObjectViewColumnException if a matching object view column could not be found
	 */
	@Override
	public ObjectViewColumn findByObjectViewId_First(
			long objectViewId,
			OrderByComparator<ObjectViewColumn> orderByComparator)
		throws NoSuchObjectViewColumnException {

		ObjectViewColumn objectViewColumn = fetchByObjectViewId_First(
			objectViewId, orderByComparator);

		if (objectViewColumn != null) {
			return objectViewColumn;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("objectViewId=");
		sb.append(objectViewId);

		sb.append("}");

		throw new NoSuchObjectViewColumnException(sb.toString());
	}

	/**
	 * Returns the first object view column in the ordered set where objectViewId = &#63;.
	 *
	 * @param objectViewId the object view ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object view column, or <code>null</code> if a matching object view column could not be found
	 */
	@Override
	public ObjectViewColumn fetchByObjectViewId_First(
		long objectViewId,
		OrderByComparator<ObjectViewColumn> orderByComparator) {

		List<ObjectViewColumn> list = findByObjectViewId(
			objectViewId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last object view column in the ordered set where objectViewId = &#63;.
	 *
	 * @param objectViewId the object view ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object view column
	 * @throws NoSuchObjectViewColumnException if a matching object view column could not be found
	 */
	@Override
	public ObjectViewColumn findByObjectViewId_Last(
			long objectViewId,
			OrderByComparator<ObjectViewColumn> orderByComparator)
		throws NoSuchObjectViewColumnException {

		ObjectViewColumn objectViewColumn = fetchByObjectViewId_Last(
			objectViewId, orderByComparator);

		if (objectViewColumn != null) {
			return objectViewColumn;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("objectViewId=");
		sb.append(objectViewId);

		sb.append("}");

		throw new NoSuchObjectViewColumnException(sb.toString());
	}

	/**
	 * Returns the last object view column in the ordered set where objectViewId = &#63;.
	 *
	 * @param objectViewId the object view ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object view column, or <code>null</code> if a matching object view column could not be found
	 */
	@Override
	public ObjectViewColumn fetchByObjectViewId_Last(
		long objectViewId,
		OrderByComparator<ObjectViewColumn> orderByComparator) {

		int count = countByObjectViewId(objectViewId);

		if (count == 0) {
			return null;
		}

		List<ObjectViewColumn> list = findByObjectViewId(
			objectViewId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the object view columns before and after the current object view column in the ordered set where objectViewId = &#63;.
	 *
	 * @param objectViewColumnId the primary key of the current object view column
	 * @param objectViewId the object view ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next object view column
	 * @throws NoSuchObjectViewColumnException if a object view column with the primary key could not be found
	 */
	@Override
	public ObjectViewColumn[] findByObjectViewId_PrevAndNext(
			long objectViewColumnId, long objectViewId,
			OrderByComparator<ObjectViewColumn> orderByComparator)
		throws NoSuchObjectViewColumnException {

		ObjectViewColumn objectViewColumn = findByPrimaryKey(
			objectViewColumnId);

		Session session = null;

		try {
			session = openSession();

			ObjectViewColumn[] array = new ObjectViewColumnImpl[3];

			array[0] = getByObjectViewId_PrevAndNext(
				session, objectViewColumn, objectViewId, orderByComparator,
				true);

			array[1] = objectViewColumn;

			array[2] = getByObjectViewId_PrevAndNext(
				session, objectViewColumn, objectViewId, orderByComparator,
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

	protected ObjectViewColumn getByObjectViewId_PrevAndNext(
		Session session, ObjectViewColumn objectViewColumn, long objectViewId,
		OrderByComparator<ObjectViewColumn> orderByComparator,
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

		sb.append(_SQL_SELECT_OBJECTVIEWCOLUMN_WHERE);

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
			sb.append(ObjectViewColumnModelImpl.ORDER_BY_JPQL);
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
						objectViewColumn)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<ObjectViewColumn> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the object view columns where objectViewId = &#63; from the database.
	 *
	 * @param objectViewId the object view ID
	 */
	@Override
	public void removeByObjectViewId(long objectViewId) {
		for (ObjectViewColumn objectViewColumn :
				findByObjectViewId(
					objectViewId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(objectViewColumn);
		}
	}

	/**
	 * Returns the number of object view columns where objectViewId = &#63;.
	 *
	 * @param objectViewId the object view ID
	 * @return the number of matching object view columns
	 */
	@Override
	public int countByObjectViewId(long objectViewId) {
		FinderPath finderPath = _finderPathCountByObjectViewId;

		Object[] finderArgs = new Object[] {objectViewId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_OBJECTVIEWCOLUMN_WHERE);

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
		"objectViewColumn.objectViewId = ?";

	public ObjectViewColumnPersistenceImpl() {
		Map<String, String> dbColumnNames = new HashMap<String, String>();

		dbColumnNames.put("uuid", "uuid_");

		setDBColumnNames(dbColumnNames);

		setModelClass(ObjectViewColumn.class);

		setModelImplClass(ObjectViewColumnImpl.class);
		setModelPKClass(long.class);

		setTable(ObjectViewColumnTable.INSTANCE);
	}

	/**
	 * Caches the object view column in the entity cache if it is enabled.
	 *
	 * @param objectViewColumn the object view column
	 */
	@Override
	public void cacheResult(ObjectViewColumn objectViewColumn) {
		entityCache.putResult(
			ObjectViewColumnImpl.class, objectViewColumn.getPrimaryKey(),
			objectViewColumn);
	}

	private int _valueObjectFinderCacheListThreshold;

	/**
	 * Caches the object view columns in the entity cache if it is enabled.
	 *
	 * @param objectViewColumns the object view columns
	 */
	@Override
	public void cacheResult(List<ObjectViewColumn> objectViewColumns) {
		if ((_valueObjectFinderCacheListThreshold == 0) ||
			((_valueObjectFinderCacheListThreshold > 0) &&
			 (objectViewColumns.size() >
				 _valueObjectFinderCacheListThreshold))) {

			return;
		}

		for (ObjectViewColumn objectViewColumn : objectViewColumns) {
			if (entityCache.getResult(
					ObjectViewColumnImpl.class,
					objectViewColumn.getPrimaryKey()) == null) {

				cacheResult(objectViewColumn);
			}
		}
	}

	/**
	 * Clears the cache for all object view columns.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(ObjectViewColumnImpl.class);

		finderCache.clearCache(ObjectViewColumnImpl.class);
	}

	/**
	 * Clears the cache for the object view column.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(ObjectViewColumn objectViewColumn) {
		entityCache.removeResult(ObjectViewColumnImpl.class, objectViewColumn);
	}

	@Override
	public void clearCache(List<ObjectViewColumn> objectViewColumns) {
		for (ObjectViewColumn objectViewColumn : objectViewColumns) {
			entityCache.removeResult(
				ObjectViewColumnImpl.class, objectViewColumn);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(ObjectViewColumnImpl.class);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(ObjectViewColumnImpl.class, primaryKey);
		}
	}

	/**
	 * Creates a new object view column with the primary key. Does not add the object view column to the database.
	 *
	 * @param objectViewColumnId the primary key for the new object view column
	 * @return the new object view column
	 */
	@Override
	public ObjectViewColumn create(long objectViewColumnId) {
		ObjectViewColumn objectViewColumn = new ObjectViewColumnImpl();

		objectViewColumn.setNew(true);
		objectViewColumn.setPrimaryKey(objectViewColumnId);

		String uuid = PortalUUIDUtil.generate();

		objectViewColumn.setUuid(uuid);

		objectViewColumn.setCompanyId(CompanyThreadLocal.getCompanyId());

		return objectViewColumn;
	}

	/**
	 * Removes the object view column with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param objectViewColumnId the primary key of the object view column
	 * @return the object view column that was removed
	 * @throws NoSuchObjectViewColumnException if a object view column with the primary key could not be found
	 */
	@Override
	public ObjectViewColumn remove(long objectViewColumnId)
		throws NoSuchObjectViewColumnException {

		return remove((Serializable)objectViewColumnId);
	}

	/**
	 * Removes the object view column with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the object view column
	 * @return the object view column that was removed
	 * @throws NoSuchObjectViewColumnException if a object view column with the primary key could not be found
	 */
	@Override
	public ObjectViewColumn remove(Serializable primaryKey)
		throws NoSuchObjectViewColumnException {

		Session session = null;

		try {
			session = openSession();

			ObjectViewColumn objectViewColumn = (ObjectViewColumn)session.get(
				ObjectViewColumnImpl.class, primaryKey);

			if (objectViewColumn == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchObjectViewColumnException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(objectViewColumn);
		}
		catch (NoSuchObjectViewColumnException noSuchEntityException) {
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
	protected ObjectViewColumn removeImpl(ObjectViewColumn objectViewColumn) {
		Session session = null;

		try {
			session = openSession();

			if (!session.contains(objectViewColumn)) {
				objectViewColumn = (ObjectViewColumn)session.get(
					ObjectViewColumnImpl.class,
					objectViewColumn.getPrimaryKeyObj());
			}

			if (objectViewColumn != null) {
				session.delete(objectViewColumn);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (objectViewColumn != null) {
			clearCache(objectViewColumn);
		}

		return objectViewColumn;
	}

	@Override
	public ObjectViewColumn updateImpl(ObjectViewColumn objectViewColumn) {
		boolean isNew = objectViewColumn.isNew();

		if (!(objectViewColumn instanceof ObjectViewColumnModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(objectViewColumn.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					objectViewColumn);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in objectViewColumn proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom ObjectViewColumn implementation " +
					objectViewColumn.getClass());
		}

		ObjectViewColumnModelImpl objectViewColumnModelImpl =
			(ObjectViewColumnModelImpl)objectViewColumn;

		if (Validator.isNull(objectViewColumn.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			objectViewColumn.setUuid(uuid);
		}

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Date date = new Date();

		if (isNew && (objectViewColumn.getCreateDate() == null)) {
			if (serviceContext == null) {
				objectViewColumn.setCreateDate(date);
			}
			else {
				objectViewColumn.setCreateDate(
					serviceContext.getCreateDate(date));
			}
		}

		if (!objectViewColumnModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				objectViewColumn.setModifiedDate(date);
			}
			else {
				objectViewColumn.setModifiedDate(
					serviceContext.getModifiedDate(date));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (isNew) {
				session.save(objectViewColumn);
			}
			else {
				objectViewColumn = (ObjectViewColumn)session.merge(
					objectViewColumn);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		entityCache.putResult(
			ObjectViewColumnImpl.class, objectViewColumnModelImpl, false, true);

		if (isNew) {
			objectViewColumn.setNew(false);
		}

		objectViewColumn.resetOriginalValues();

		return objectViewColumn;
	}

	/**
	 * Returns the object view column with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the object view column
	 * @return the object view column
	 * @throws NoSuchObjectViewColumnException if a object view column with the primary key could not be found
	 */
	@Override
	public ObjectViewColumn findByPrimaryKey(Serializable primaryKey)
		throws NoSuchObjectViewColumnException {

		ObjectViewColumn objectViewColumn = fetchByPrimaryKey(primaryKey);

		if (objectViewColumn == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchObjectViewColumnException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return objectViewColumn;
	}

	/**
	 * Returns the object view column with the primary key or throws a <code>NoSuchObjectViewColumnException</code> if it could not be found.
	 *
	 * @param objectViewColumnId the primary key of the object view column
	 * @return the object view column
	 * @throws NoSuchObjectViewColumnException if a object view column with the primary key could not be found
	 */
	@Override
	public ObjectViewColumn findByPrimaryKey(long objectViewColumnId)
		throws NoSuchObjectViewColumnException {

		return findByPrimaryKey((Serializable)objectViewColumnId);
	}

	/**
	 * Returns the object view column with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param objectViewColumnId the primary key of the object view column
	 * @return the object view column, or <code>null</code> if a object view column with the primary key could not be found
	 */
	@Override
	public ObjectViewColumn fetchByPrimaryKey(long objectViewColumnId) {
		return fetchByPrimaryKey((Serializable)objectViewColumnId);
	}

	/**
	 * Returns all the object view columns.
	 *
	 * @return the object view columns
	 */
	@Override
	public List<ObjectViewColumn> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the object view columns.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectViewColumnModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of object view columns
	 * @param end the upper bound of the range of object view columns (not inclusive)
	 * @return the range of object view columns
	 */
	@Override
	public List<ObjectViewColumn> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the object view columns.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectViewColumnModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of object view columns
	 * @param end the upper bound of the range of object view columns (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of object view columns
	 */
	@Override
	public List<ObjectViewColumn> findAll(
		int start, int end,
		OrderByComparator<ObjectViewColumn> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the object view columns.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectViewColumnModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of object view columns
	 * @param end the upper bound of the range of object view columns (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of object view columns
	 */
	@Override
	public List<ObjectViewColumn> findAll(
		int start, int end,
		OrderByComparator<ObjectViewColumn> orderByComparator,
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

		List<ObjectViewColumn> list = null;

		if (useFinderCache) {
			list = (List<ObjectViewColumn>)finderCache.getResult(
				finderPath, finderArgs);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_OBJECTVIEWCOLUMN);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_OBJECTVIEWCOLUMN;

				sql = sql.concat(ObjectViewColumnModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<ObjectViewColumn>)QueryUtil.list(
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
	 * Removes all the object view columns from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (ObjectViewColumn objectViewColumn : findAll()) {
			remove(objectViewColumn);
		}
	}

	/**
	 * Returns the number of object view columns.
	 *
	 * @return the number of object view columns
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(
			_finderPathCountAll, FINDER_ARGS_EMPTY);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(_SQL_COUNT_OBJECTVIEWCOLUMN);

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
		return "objectViewColumnId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_OBJECTVIEWCOLUMN;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return ObjectViewColumnModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the object view column persistence.
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

		_setObjectViewColumnUtilPersistence(this);
	}

	@Deactivate
	public void deactivate() {
		_setObjectViewColumnUtilPersistence(null);

		entityCache.removeCache(ObjectViewColumnImpl.class.getName());
	}

	private void _setObjectViewColumnUtilPersistence(
		ObjectViewColumnPersistence objectViewColumnPersistence) {

		try {
			Field field = ObjectViewColumnUtil.class.getDeclaredField(
				"_persistence");

			field.setAccessible(true);

			field.set(null, objectViewColumnPersistence);
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

	private static final String _SQL_SELECT_OBJECTVIEWCOLUMN =
		"SELECT objectViewColumn FROM ObjectViewColumn objectViewColumn";

	private static final String _SQL_SELECT_OBJECTVIEWCOLUMN_WHERE =
		"SELECT objectViewColumn FROM ObjectViewColumn objectViewColumn WHERE ";

	private static final String _SQL_COUNT_OBJECTVIEWCOLUMN =
		"SELECT COUNT(objectViewColumn) FROM ObjectViewColumn objectViewColumn";

	private static final String _SQL_COUNT_OBJECTVIEWCOLUMN_WHERE =
		"SELECT COUNT(objectViewColumn) FROM ObjectViewColumn objectViewColumn WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS = "objectViewColumn.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No ObjectViewColumn exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No ObjectViewColumn exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		ObjectViewColumnPersistenceImpl.class);

	private static final Set<String> _badColumnNames = SetUtil.fromArray(
		new String[] {"uuid"});

	@Override
	protected FinderCache getFinderCache() {
		return finderCache;
	}

	@Reference
	private ObjectViewColumnModelArgumentsResolver
		_objectViewColumnModelArgumentsResolver;

}