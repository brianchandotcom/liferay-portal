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

import com.liferay.object.exception.NoSuchObjectLayoutColumnException;
import com.liferay.object.model.ObjectLayoutColumn;
import com.liferay.object.model.ObjectLayoutColumnTable;
import com.liferay.object.model.impl.ObjectLayoutColumnImpl;
import com.liferay.object.model.impl.ObjectLayoutColumnModelImpl;
import com.liferay.object.service.persistence.ObjectLayoutColumnPersistence;
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
 * The persistence implementation for the object layout column service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Marco Leo
 * @generated
 */
@Component(
	service = {ObjectLayoutColumnPersistence.class, BasePersistence.class}
)
public class ObjectLayoutColumnPersistenceImpl
	extends BasePersistenceImpl<ObjectLayoutColumn>
	implements ObjectLayoutColumnPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>ObjectLayoutColumnUtil</code> to access the object layout column persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		ObjectLayoutColumnImpl.class.getName();

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
	 * Returns all the object layout columns where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching object layout columns
	 */
	@Override
	public List<ObjectLayoutColumn> findByUuid(String uuid) {
		return findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the object layout columns where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectLayoutColumnModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of object layout columns
	 * @param end the upper bound of the range of object layout columns (not inclusive)
	 * @return the range of matching object layout columns
	 */
	@Override
	public List<ObjectLayoutColumn> findByUuid(
		String uuid, int start, int end) {

		return findByUuid(uuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the object layout columns where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectLayoutColumnModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of object layout columns
	 * @param end the upper bound of the range of object layout columns (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching object layout columns
	 */
	@Override
	public List<ObjectLayoutColumn> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<ObjectLayoutColumn> orderByComparator) {

		return findByUuid(uuid, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the object layout columns where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectLayoutColumnModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of object layout columns
	 * @param end the upper bound of the range of object layout columns (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching object layout columns
	 */
	@Override
	public List<ObjectLayoutColumn> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<ObjectLayoutColumn> orderByComparator,
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

		List<ObjectLayoutColumn> list = null;

		if (useFinderCache) {
			list = (List<ObjectLayoutColumn>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (ObjectLayoutColumn objectLayoutColumn : list) {
					if (!uuid.equals(objectLayoutColumn.getUuid())) {
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

			sb.append(_SQL_SELECT_OBJECTLAYOUTCOLUMN_WHERE);

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
				sb.append(ObjectLayoutColumnModelImpl.ORDER_BY_JPQL);
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

				list = (List<ObjectLayoutColumn>)QueryUtil.list(
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
	 * Returns the first object layout column in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object layout column
	 * @throws NoSuchObjectLayoutColumnException if a matching object layout column could not be found
	 */
	@Override
	public ObjectLayoutColumn findByUuid_First(
			String uuid,
			OrderByComparator<ObjectLayoutColumn> orderByComparator)
		throws NoSuchObjectLayoutColumnException {

		ObjectLayoutColumn objectLayoutColumn = fetchByUuid_First(
			uuid, orderByComparator);

		if (objectLayoutColumn != null) {
			return objectLayoutColumn;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append("}");

		throw new NoSuchObjectLayoutColumnException(sb.toString());
	}

	/**
	 * Returns the first object layout column in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object layout column, or <code>null</code> if a matching object layout column could not be found
	 */
	@Override
	public ObjectLayoutColumn fetchByUuid_First(
		String uuid, OrderByComparator<ObjectLayoutColumn> orderByComparator) {

		List<ObjectLayoutColumn> list = findByUuid(
			uuid, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last object layout column in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object layout column
	 * @throws NoSuchObjectLayoutColumnException if a matching object layout column could not be found
	 */
	@Override
	public ObjectLayoutColumn findByUuid_Last(
			String uuid,
			OrderByComparator<ObjectLayoutColumn> orderByComparator)
		throws NoSuchObjectLayoutColumnException {

		ObjectLayoutColumn objectLayoutColumn = fetchByUuid_Last(
			uuid, orderByComparator);

		if (objectLayoutColumn != null) {
			return objectLayoutColumn;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append("}");

		throw new NoSuchObjectLayoutColumnException(sb.toString());
	}

	/**
	 * Returns the last object layout column in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object layout column, or <code>null</code> if a matching object layout column could not be found
	 */
	@Override
	public ObjectLayoutColumn fetchByUuid_Last(
		String uuid, OrderByComparator<ObjectLayoutColumn> orderByComparator) {

		int count = countByUuid(uuid);

		if (count == 0) {
			return null;
		}

		List<ObjectLayoutColumn> list = findByUuid(
			uuid, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the object layout columns before and after the current object layout column in the ordered set where uuid = &#63;.
	 *
	 * @param objectLayoutColumnId the primary key of the current object layout column
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next object layout column
	 * @throws NoSuchObjectLayoutColumnException if a object layout column with the primary key could not be found
	 */
	@Override
	public ObjectLayoutColumn[] findByUuid_PrevAndNext(
			long objectLayoutColumnId, String uuid,
			OrderByComparator<ObjectLayoutColumn> orderByComparator)
		throws NoSuchObjectLayoutColumnException {

		uuid = Objects.toString(uuid, "");

		ObjectLayoutColumn objectLayoutColumn = findByPrimaryKey(
			objectLayoutColumnId);

		Session session = null;

		try {
			session = openSession();

			ObjectLayoutColumn[] array = new ObjectLayoutColumnImpl[3];

			array[0] = getByUuid_PrevAndNext(
				session, objectLayoutColumn, uuid, orderByComparator, true);

			array[1] = objectLayoutColumn;

			array[2] = getByUuid_PrevAndNext(
				session, objectLayoutColumn, uuid, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected ObjectLayoutColumn getByUuid_PrevAndNext(
		Session session, ObjectLayoutColumn objectLayoutColumn, String uuid,
		OrderByComparator<ObjectLayoutColumn> orderByComparator,
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

		sb.append(_SQL_SELECT_OBJECTLAYOUTCOLUMN_WHERE);

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
			sb.append(ObjectLayoutColumnModelImpl.ORDER_BY_JPQL);
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
						objectLayoutColumn)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<ObjectLayoutColumn> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the object layout columns where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	@Override
	public void removeByUuid(String uuid) {
		for (ObjectLayoutColumn objectLayoutColumn :
				findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(objectLayoutColumn);
		}
	}

	/**
	 * Returns the number of object layout columns where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching object layout columns
	 */
	@Override
	public int countByUuid(String uuid) {
		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = _finderPathCountByUuid;

		Object[] finderArgs = new Object[] {uuid};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_OBJECTLAYOUTCOLUMN_WHERE);

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
		"objectLayoutColumn.uuid = ?";

	private static final String _FINDER_COLUMN_UUID_UUID_3 =
		"(objectLayoutColumn.uuid IS NULL OR objectLayoutColumn.uuid = '')";

	private FinderPath _finderPathWithPaginationFindByUuid_C;
	private FinderPath _finderPathWithoutPaginationFindByUuid_C;
	private FinderPath _finderPathCountByUuid_C;

	/**
	 * Returns all the object layout columns where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching object layout columns
	 */
	@Override
	public List<ObjectLayoutColumn> findByUuid_C(String uuid, long companyId) {
		return findByUuid_C(
			uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the object layout columns where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectLayoutColumnModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of object layout columns
	 * @param end the upper bound of the range of object layout columns (not inclusive)
	 * @return the range of matching object layout columns
	 */
	@Override
	public List<ObjectLayoutColumn> findByUuid_C(
		String uuid, long companyId, int start, int end) {

		return findByUuid_C(uuid, companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the object layout columns where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectLayoutColumnModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of object layout columns
	 * @param end the upper bound of the range of object layout columns (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching object layout columns
	 */
	@Override
	public List<ObjectLayoutColumn> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<ObjectLayoutColumn> orderByComparator) {

		return findByUuid_C(
			uuid, companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the object layout columns where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectLayoutColumnModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of object layout columns
	 * @param end the upper bound of the range of object layout columns (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching object layout columns
	 */
	@Override
	public List<ObjectLayoutColumn> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<ObjectLayoutColumn> orderByComparator,
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

		List<ObjectLayoutColumn> list = null;

		if (useFinderCache) {
			list = (List<ObjectLayoutColumn>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (ObjectLayoutColumn objectLayoutColumn : list) {
					if (!uuid.equals(objectLayoutColumn.getUuid()) ||
						(companyId != objectLayoutColumn.getCompanyId())) {

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

			sb.append(_SQL_SELECT_OBJECTLAYOUTCOLUMN_WHERE);

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
				sb.append(ObjectLayoutColumnModelImpl.ORDER_BY_JPQL);
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

				list = (List<ObjectLayoutColumn>)QueryUtil.list(
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
	 * Returns the first object layout column in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object layout column
	 * @throws NoSuchObjectLayoutColumnException if a matching object layout column could not be found
	 */
	@Override
	public ObjectLayoutColumn findByUuid_C_First(
			String uuid, long companyId,
			OrderByComparator<ObjectLayoutColumn> orderByComparator)
		throws NoSuchObjectLayoutColumnException {

		ObjectLayoutColumn objectLayoutColumn = fetchByUuid_C_First(
			uuid, companyId, orderByComparator);

		if (objectLayoutColumn != null) {
			return objectLayoutColumn;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append(", companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchObjectLayoutColumnException(sb.toString());
	}

	/**
	 * Returns the first object layout column in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object layout column, or <code>null</code> if a matching object layout column could not be found
	 */
	@Override
	public ObjectLayoutColumn fetchByUuid_C_First(
		String uuid, long companyId,
		OrderByComparator<ObjectLayoutColumn> orderByComparator) {

		List<ObjectLayoutColumn> list = findByUuid_C(
			uuid, companyId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last object layout column in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object layout column
	 * @throws NoSuchObjectLayoutColumnException if a matching object layout column could not be found
	 */
	@Override
	public ObjectLayoutColumn findByUuid_C_Last(
			String uuid, long companyId,
			OrderByComparator<ObjectLayoutColumn> orderByComparator)
		throws NoSuchObjectLayoutColumnException {

		ObjectLayoutColumn objectLayoutColumn = fetchByUuid_C_Last(
			uuid, companyId, orderByComparator);

		if (objectLayoutColumn != null) {
			return objectLayoutColumn;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append(", companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchObjectLayoutColumnException(sb.toString());
	}

	/**
	 * Returns the last object layout column in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object layout column, or <code>null</code> if a matching object layout column could not be found
	 */
	@Override
	public ObjectLayoutColumn fetchByUuid_C_Last(
		String uuid, long companyId,
		OrderByComparator<ObjectLayoutColumn> orderByComparator) {

		int count = countByUuid_C(uuid, companyId);

		if (count == 0) {
			return null;
		}

		List<ObjectLayoutColumn> list = findByUuid_C(
			uuid, companyId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the object layout columns before and after the current object layout column in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param objectLayoutColumnId the primary key of the current object layout column
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next object layout column
	 * @throws NoSuchObjectLayoutColumnException if a object layout column with the primary key could not be found
	 */
	@Override
	public ObjectLayoutColumn[] findByUuid_C_PrevAndNext(
			long objectLayoutColumnId, String uuid, long companyId,
			OrderByComparator<ObjectLayoutColumn> orderByComparator)
		throws NoSuchObjectLayoutColumnException {

		uuid = Objects.toString(uuid, "");

		ObjectLayoutColumn objectLayoutColumn = findByPrimaryKey(
			objectLayoutColumnId);

		Session session = null;

		try {
			session = openSession();

			ObjectLayoutColumn[] array = new ObjectLayoutColumnImpl[3];

			array[0] = getByUuid_C_PrevAndNext(
				session, objectLayoutColumn, uuid, companyId, orderByComparator,
				true);

			array[1] = objectLayoutColumn;

			array[2] = getByUuid_C_PrevAndNext(
				session, objectLayoutColumn, uuid, companyId, orderByComparator,
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

	protected ObjectLayoutColumn getByUuid_C_PrevAndNext(
		Session session, ObjectLayoutColumn objectLayoutColumn, String uuid,
		long companyId, OrderByComparator<ObjectLayoutColumn> orderByComparator,
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

		sb.append(_SQL_SELECT_OBJECTLAYOUTCOLUMN_WHERE);

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
			sb.append(ObjectLayoutColumnModelImpl.ORDER_BY_JPQL);
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
						objectLayoutColumn)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<ObjectLayoutColumn> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the object layout columns where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	@Override
	public void removeByUuid_C(String uuid, long companyId) {
		for (ObjectLayoutColumn objectLayoutColumn :
				findByUuid_C(
					uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(objectLayoutColumn);
		}
	}

	/**
	 * Returns the number of object layout columns where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching object layout columns
	 */
	@Override
	public int countByUuid_C(String uuid, long companyId) {
		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = _finderPathCountByUuid_C;

		Object[] finderArgs = new Object[] {uuid, companyId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_OBJECTLAYOUTCOLUMN_WHERE);

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
		"objectLayoutColumn.uuid = ? AND ";

	private static final String _FINDER_COLUMN_UUID_C_UUID_3 =
		"(objectLayoutColumn.uuid IS NULL OR objectLayoutColumn.uuid = '') AND ";

	private static final String _FINDER_COLUMN_UUID_C_COMPANYID_2 =
		"objectLayoutColumn.companyId = ?";

	private FinderPath _finderPathWithPaginationFindByObjectLayoutRowId;
	private FinderPath _finderPathWithoutPaginationFindByObjectLayoutRowId;
	private FinderPath _finderPathCountByObjectLayoutRowId;

	/**
	 * Returns all the object layout columns where objectLayoutRowId = &#63;.
	 *
	 * @param objectLayoutRowId the object layout row ID
	 * @return the matching object layout columns
	 */
	@Override
	public List<ObjectLayoutColumn> findByObjectLayoutRowId(
		long objectLayoutRowId) {

		return findByObjectLayoutRowId(
			objectLayoutRowId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the object layout columns where objectLayoutRowId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectLayoutColumnModelImpl</code>.
	 * </p>
	 *
	 * @param objectLayoutRowId the object layout row ID
	 * @param start the lower bound of the range of object layout columns
	 * @param end the upper bound of the range of object layout columns (not inclusive)
	 * @return the range of matching object layout columns
	 */
	@Override
	public List<ObjectLayoutColumn> findByObjectLayoutRowId(
		long objectLayoutRowId, int start, int end) {

		return findByObjectLayoutRowId(objectLayoutRowId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the object layout columns where objectLayoutRowId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectLayoutColumnModelImpl</code>.
	 * </p>
	 *
	 * @param objectLayoutRowId the object layout row ID
	 * @param start the lower bound of the range of object layout columns
	 * @param end the upper bound of the range of object layout columns (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching object layout columns
	 */
	@Override
	public List<ObjectLayoutColumn> findByObjectLayoutRowId(
		long objectLayoutRowId, int start, int end,
		OrderByComparator<ObjectLayoutColumn> orderByComparator) {

		return findByObjectLayoutRowId(
			objectLayoutRowId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the object layout columns where objectLayoutRowId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectLayoutColumnModelImpl</code>.
	 * </p>
	 *
	 * @param objectLayoutRowId the object layout row ID
	 * @param start the lower bound of the range of object layout columns
	 * @param end the upper bound of the range of object layout columns (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching object layout columns
	 */
	@Override
	public List<ObjectLayoutColumn> findByObjectLayoutRowId(
		long objectLayoutRowId, int start, int end,
		OrderByComparator<ObjectLayoutColumn> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath =
					_finderPathWithoutPaginationFindByObjectLayoutRowId;
				finderArgs = new Object[] {objectLayoutRowId};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByObjectLayoutRowId;
			finderArgs = new Object[] {
				objectLayoutRowId, start, end, orderByComparator
			};
		}

		List<ObjectLayoutColumn> list = null;

		if (useFinderCache) {
			list = (List<ObjectLayoutColumn>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (ObjectLayoutColumn objectLayoutColumn : list) {
					if (objectLayoutRowId !=
							objectLayoutColumn.getObjectLayoutRowId()) {

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

			sb.append(_SQL_SELECT_OBJECTLAYOUTCOLUMN_WHERE);

			sb.append(_FINDER_COLUMN_OBJECTLAYOUTROWID_OBJECTLAYOUTROWID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(ObjectLayoutColumnModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(objectLayoutRowId);

				list = (List<ObjectLayoutColumn>)QueryUtil.list(
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
	 * Returns the first object layout column in the ordered set where objectLayoutRowId = &#63;.
	 *
	 * @param objectLayoutRowId the object layout row ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object layout column
	 * @throws NoSuchObjectLayoutColumnException if a matching object layout column could not be found
	 */
	@Override
	public ObjectLayoutColumn findByObjectLayoutRowId_First(
			long objectLayoutRowId,
			OrderByComparator<ObjectLayoutColumn> orderByComparator)
		throws NoSuchObjectLayoutColumnException {

		ObjectLayoutColumn objectLayoutColumn = fetchByObjectLayoutRowId_First(
			objectLayoutRowId, orderByComparator);

		if (objectLayoutColumn != null) {
			return objectLayoutColumn;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("objectLayoutRowId=");
		sb.append(objectLayoutRowId);

		sb.append("}");

		throw new NoSuchObjectLayoutColumnException(sb.toString());
	}

	/**
	 * Returns the first object layout column in the ordered set where objectLayoutRowId = &#63;.
	 *
	 * @param objectLayoutRowId the object layout row ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object layout column, or <code>null</code> if a matching object layout column could not be found
	 */
	@Override
	public ObjectLayoutColumn fetchByObjectLayoutRowId_First(
		long objectLayoutRowId,
		OrderByComparator<ObjectLayoutColumn> orderByComparator) {

		List<ObjectLayoutColumn> list = findByObjectLayoutRowId(
			objectLayoutRowId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last object layout column in the ordered set where objectLayoutRowId = &#63;.
	 *
	 * @param objectLayoutRowId the object layout row ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object layout column
	 * @throws NoSuchObjectLayoutColumnException if a matching object layout column could not be found
	 */
	@Override
	public ObjectLayoutColumn findByObjectLayoutRowId_Last(
			long objectLayoutRowId,
			OrderByComparator<ObjectLayoutColumn> orderByComparator)
		throws NoSuchObjectLayoutColumnException {

		ObjectLayoutColumn objectLayoutColumn = fetchByObjectLayoutRowId_Last(
			objectLayoutRowId, orderByComparator);

		if (objectLayoutColumn != null) {
			return objectLayoutColumn;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("objectLayoutRowId=");
		sb.append(objectLayoutRowId);

		sb.append("}");

		throw new NoSuchObjectLayoutColumnException(sb.toString());
	}

	/**
	 * Returns the last object layout column in the ordered set where objectLayoutRowId = &#63;.
	 *
	 * @param objectLayoutRowId the object layout row ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object layout column, or <code>null</code> if a matching object layout column could not be found
	 */
	@Override
	public ObjectLayoutColumn fetchByObjectLayoutRowId_Last(
		long objectLayoutRowId,
		OrderByComparator<ObjectLayoutColumn> orderByComparator) {

		int count = countByObjectLayoutRowId(objectLayoutRowId);

		if (count == 0) {
			return null;
		}

		List<ObjectLayoutColumn> list = findByObjectLayoutRowId(
			objectLayoutRowId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the object layout columns before and after the current object layout column in the ordered set where objectLayoutRowId = &#63;.
	 *
	 * @param objectLayoutColumnId the primary key of the current object layout column
	 * @param objectLayoutRowId the object layout row ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next object layout column
	 * @throws NoSuchObjectLayoutColumnException if a object layout column with the primary key could not be found
	 */
	@Override
	public ObjectLayoutColumn[] findByObjectLayoutRowId_PrevAndNext(
			long objectLayoutColumnId, long objectLayoutRowId,
			OrderByComparator<ObjectLayoutColumn> orderByComparator)
		throws NoSuchObjectLayoutColumnException {

		ObjectLayoutColumn objectLayoutColumn = findByPrimaryKey(
			objectLayoutColumnId);

		Session session = null;

		try {
			session = openSession();

			ObjectLayoutColumn[] array = new ObjectLayoutColumnImpl[3];

			array[0] = getByObjectLayoutRowId_PrevAndNext(
				session, objectLayoutColumn, objectLayoutRowId,
				orderByComparator, true);

			array[1] = objectLayoutColumn;

			array[2] = getByObjectLayoutRowId_PrevAndNext(
				session, objectLayoutColumn, objectLayoutRowId,
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

	protected ObjectLayoutColumn getByObjectLayoutRowId_PrevAndNext(
		Session session, ObjectLayoutColumn objectLayoutColumn,
		long objectLayoutRowId,
		OrderByComparator<ObjectLayoutColumn> orderByComparator,
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

		sb.append(_SQL_SELECT_OBJECTLAYOUTCOLUMN_WHERE);

		sb.append(_FINDER_COLUMN_OBJECTLAYOUTROWID_OBJECTLAYOUTROWID_2);

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
			sb.append(ObjectLayoutColumnModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(objectLayoutRowId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						objectLayoutColumn)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<ObjectLayoutColumn> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the object layout columns where objectLayoutRowId = &#63; from the database.
	 *
	 * @param objectLayoutRowId the object layout row ID
	 */
	@Override
	public void removeByObjectLayoutRowId(long objectLayoutRowId) {
		for (ObjectLayoutColumn objectLayoutColumn :
				findByObjectLayoutRowId(
					objectLayoutRowId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(objectLayoutColumn);
		}
	}

	/**
	 * Returns the number of object layout columns where objectLayoutRowId = &#63;.
	 *
	 * @param objectLayoutRowId the object layout row ID
	 * @return the number of matching object layout columns
	 */
	@Override
	public int countByObjectLayoutRowId(long objectLayoutRowId) {
		FinderPath finderPath = _finderPathCountByObjectLayoutRowId;

		Object[] finderArgs = new Object[] {objectLayoutRowId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_OBJECTLAYOUTCOLUMN_WHERE);

			sb.append(_FINDER_COLUMN_OBJECTLAYOUTROWID_OBJECTLAYOUTROWID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(objectLayoutRowId);

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
		_FINDER_COLUMN_OBJECTLAYOUTROWID_OBJECTLAYOUTROWID_2 =
			"objectLayoutColumn.objectLayoutRowId = ?";

	public ObjectLayoutColumnPersistenceImpl() {
		Map<String, String> dbColumnNames = new HashMap<String, String>();

		dbColumnNames.put("uuid", "uuid_");
		dbColumnNames.put("size", "size_");

		setDBColumnNames(dbColumnNames);

		setModelClass(ObjectLayoutColumn.class);

		setModelImplClass(ObjectLayoutColumnImpl.class);
		setModelPKClass(long.class);

		setTable(ObjectLayoutColumnTable.INSTANCE);
	}

	/**
	 * Caches the object layout column in the entity cache if it is enabled.
	 *
	 * @param objectLayoutColumn the object layout column
	 */
	@Override
	public void cacheResult(ObjectLayoutColumn objectLayoutColumn) {
		entityCache.putResult(
			ObjectLayoutColumnImpl.class, objectLayoutColumn.getPrimaryKey(),
			objectLayoutColumn);
	}

	private int _valueObjectFinderCacheListThreshold;

	/**
	 * Caches the object layout columns in the entity cache if it is enabled.
	 *
	 * @param objectLayoutColumns the object layout columns
	 */
	@Override
	public void cacheResult(List<ObjectLayoutColumn> objectLayoutColumns) {
		if ((_valueObjectFinderCacheListThreshold == 0) ||
			((_valueObjectFinderCacheListThreshold > 0) &&
			 (objectLayoutColumns.size() >
				 _valueObjectFinderCacheListThreshold))) {

			return;
		}

		for (ObjectLayoutColumn objectLayoutColumn : objectLayoutColumns) {
			if (entityCache.getResult(
					ObjectLayoutColumnImpl.class,
					objectLayoutColumn.getPrimaryKey()) == null) {

				cacheResult(objectLayoutColumn);
			}
		}
	}

	/**
	 * Clears the cache for all object layout columns.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(ObjectLayoutColumnImpl.class);

		finderCache.clearCache(ObjectLayoutColumnImpl.class);
	}

	/**
	 * Clears the cache for the object layout column.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(ObjectLayoutColumn objectLayoutColumn) {
		entityCache.removeResult(
			ObjectLayoutColumnImpl.class, objectLayoutColumn);
	}

	@Override
	public void clearCache(List<ObjectLayoutColumn> objectLayoutColumns) {
		for (ObjectLayoutColumn objectLayoutColumn : objectLayoutColumns) {
			entityCache.removeResult(
				ObjectLayoutColumnImpl.class, objectLayoutColumn);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(ObjectLayoutColumnImpl.class);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(ObjectLayoutColumnImpl.class, primaryKey);
		}
	}

	/**
	 * Creates a new object layout column with the primary key. Does not add the object layout column to the database.
	 *
	 * @param objectLayoutColumnId the primary key for the new object layout column
	 * @return the new object layout column
	 */
	@Override
	public ObjectLayoutColumn create(long objectLayoutColumnId) {
		ObjectLayoutColumn objectLayoutColumn = new ObjectLayoutColumnImpl();

		objectLayoutColumn.setNew(true);
		objectLayoutColumn.setPrimaryKey(objectLayoutColumnId);

		String uuid = PortalUUIDUtil.generate();

		objectLayoutColumn.setUuid(uuid);

		objectLayoutColumn.setCompanyId(CompanyThreadLocal.getCompanyId());

		return objectLayoutColumn;
	}

	/**
	 * Removes the object layout column with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param objectLayoutColumnId the primary key of the object layout column
	 * @return the object layout column that was removed
	 * @throws NoSuchObjectLayoutColumnException if a object layout column with the primary key could not be found
	 */
	@Override
	public ObjectLayoutColumn remove(long objectLayoutColumnId)
		throws NoSuchObjectLayoutColumnException {

		return remove((Serializable)objectLayoutColumnId);
	}

	/**
	 * Removes the object layout column with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the object layout column
	 * @return the object layout column that was removed
	 * @throws NoSuchObjectLayoutColumnException if a object layout column with the primary key could not be found
	 */
	@Override
	public ObjectLayoutColumn remove(Serializable primaryKey)
		throws NoSuchObjectLayoutColumnException {

		Session session = null;

		try {
			session = openSession();

			ObjectLayoutColumn objectLayoutColumn =
				(ObjectLayoutColumn)session.get(
					ObjectLayoutColumnImpl.class, primaryKey);

			if (objectLayoutColumn == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchObjectLayoutColumnException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(objectLayoutColumn);
		}
		catch (NoSuchObjectLayoutColumnException noSuchEntityException) {
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
	protected ObjectLayoutColumn removeImpl(
		ObjectLayoutColumn objectLayoutColumn) {

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(objectLayoutColumn)) {
				objectLayoutColumn = (ObjectLayoutColumn)session.get(
					ObjectLayoutColumnImpl.class,
					objectLayoutColumn.getPrimaryKeyObj());
			}

			if (objectLayoutColumn != null) {
				session.delete(objectLayoutColumn);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (objectLayoutColumn != null) {
			clearCache(objectLayoutColumn);
		}

		return objectLayoutColumn;
	}

	@Override
	public ObjectLayoutColumn updateImpl(
		ObjectLayoutColumn objectLayoutColumn) {

		boolean isNew = objectLayoutColumn.isNew();

		if (!(objectLayoutColumn instanceof ObjectLayoutColumnModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(objectLayoutColumn.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					objectLayoutColumn);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in objectLayoutColumn proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom ObjectLayoutColumn implementation " +
					objectLayoutColumn.getClass());
		}

		ObjectLayoutColumnModelImpl objectLayoutColumnModelImpl =
			(ObjectLayoutColumnModelImpl)objectLayoutColumn;

		if (Validator.isNull(objectLayoutColumn.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			objectLayoutColumn.setUuid(uuid);
		}

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Date date = new Date();

		if (isNew && (objectLayoutColumn.getCreateDate() == null)) {
			if (serviceContext == null) {
				objectLayoutColumn.setCreateDate(date);
			}
			else {
				objectLayoutColumn.setCreateDate(
					serviceContext.getCreateDate(date));
			}
		}

		if (!objectLayoutColumnModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				objectLayoutColumn.setModifiedDate(date);
			}
			else {
				objectLayoutColumn.setModifiedDate(
					serviceContext.getModifiedDate(date));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (isNew) {
				session.save(objectLayoutColumn);
			}
			else {
				objectLayoutColumn = (ObjectLayoutColumn)session.merge(
					objectLayoutColumn);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		entityCache.putResult(
			ObjectLayoutColumnImpl.class, objectLayoutColumnModelImpl, false,
			true);

		if (isNew) {
			objectLayoutColumn.setNew(false);
		}

		objectLayoutColumn.resetOriginalValues();

		return objectLayoutColumn;
	}

	/**
	 * Returns the object layout column with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the object layout column
	 * @return the object layout column
	 * @throws NoSuchObjectLayoutColumnException if a object layout column with the primary key could not be found
	 */
	@Override
	public ObjectLayoutColumn findByPrimaryKey(Serializable primaryKey)
		throws NoSuchObjectLayoutColumnException {

		ObjectLayoutColumn objectLayoutColumn = fetchByPrimaryKey(primaryKey);

		if (objectLayoutColumn == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchObjectLayoutColumnException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return objectLayoutColumn;
	}

	/**
	 * Returns the object layout column with the primary key or throws a <code>NoSuchObjectLayoutColumnException</code> if it could not be found.
	 *
	 * @param objectLayoutColumnId the primary key of the object layout column
	 * @return the object layout column
	 * @throws NoSuchObjectLayoutColumnException if a object layout column with the primary key could not be found
	 */
	@Override
	public ObjectLayoutColumn findByPrimaryKey(long objectLayoutColumnId)
		throws NoSuchObjectLayoutColumnException {

		return findByPrimaryKey((Serializable)objectLayoutColumnId);
	}

	/**
	 * Returns the object layout column with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param objectLayoutColumnId the primary key of the object layout column
	 * @return the object layout column, or <code>null</code> if a object layout column with the primary key could not be found
	 */
	@Override
	public ObjectLayoutColumn fetchByPrimaryKey(long objectLayoutColumnId) {
		return fetchByPrimaryKey((Serializable)objectLayoutColumnId);
	}

	/**
	 * Returns all the object layout columns.
	 *
	 * @return the object layout columns
	 */
	@Override
	public List<ObjectLayoutColumn> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the object layout columns.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectLayoutColumnModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of object layout columns
	 * @param end the upper bound of the range of object layout columns (not inclusive)
	 * @return the range of object layout columns
	 */
	@Override
	public List<ObjectLayoutColumn> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the object layout columns.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectLayoutColumnModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of object layout columns
	 * @param end the upper bound of the range of object layout columns (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of object layout columns
	 */
	@Override
	public List<ObjectLayoutColumn> findAll(
		int start, int end,
		OrderByComparator<ObjectLayoutColumn> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the object layout columns.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectLayoutColumnModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of object layout columns
	 * @param end the upper bound of the range of object layout columns (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of object layout columns
	 */
	@Override
	public List<ObjectLayoutColumn> findAll(
		int start, int end,
		OrderByComparator<ObjectLayoutColumn> orderByComparator,
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

		List<ObjectLayoutColumn> list = null;

		if (useFinderCache) {
			list = (List<ObjectLayoutColumn>)finderCache.getResult(
				finderPath, finderArgs);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_OBJECTLAYOUTCOLUMN);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_OBJECTLAYOUTCOLUMN;

				sql = sql.concat(ObjectLayoutColumnModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<ObjectLayoutColumn>)QueryUtil.list(
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
	 * Removes all the object layout columns from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (ObjectLayoutColumn objectLayoutColumn : findAll()) {
			remove(objectLayoutColumn);
		}
	}

	/**
	 * Returns the number of object layout columns.
	 *
	 * @return the number of object layout columns
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
					_SQL_COUNT_OBJECTLAYOUTCOLUMN);

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
		return "objectLayoutColumnId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_OBJECTLAYOUTCOLUMN;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return ObjectLayoutColumnModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the object layout column persistence.
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

		_finderPathWithPaginationFindByObjectLayoutRowId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByObjectLayoutRowId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"objectLayoutRowId"}, true);

		_finderPathWithoutPaginationFindByObjectLayoutRowId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByObjectLayoutRowId", new String[] {Long.class.getName()},
			new String[] {"objectLayoutRowId"}, true);

		_finderPathCountByObjectLayoutRowId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByObjectLayoutRowId", new String[] {Long.class.getName()},
			new String[] {"objectLayoutRowId"}, false);
	}

	@Deactivate
	public void deactivate() {
		entityCache.removeCache(ObjectLayoutColumnImpl.class.getName());
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

	private static final String _SQL_SELECT_OBJECTLAYOUTCOLUMN =
		"SELECT objectLayoutColumn FROM ObjectLayoutColumn objectLayoutColumn";

	private static final String _SQL_SELECT_OBJECTLAYOUTCOLUMN_WHERE =
		"SELECT objectLayoutColumn FROM ObjectLayoutColumn objectLayoutColumn WHERE ";

	private static final String _SQL_COUNT_OBJECTLAYOUTCOLUMN =
		"SELECT COUNT(objectLayoutColumn) FROM ObjectLayoutColumn objectLayoutColumn";

	private static final String _SQL_COUNT_OBJECTLAYOUTCOLUMN_WHERE =
		"SELECT COUNT(objectLayoutColumn) FROM ObjectLayoutColumn objectLayoutColumn WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS = "objectLayoutColumn.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No ObjectLayoutColumn exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No ObjectLayoutColumn exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		ObjectLayoutColumnPersistenceImpl.class);

	private static final Set<String> _badColumnNames = SetUtil.fromArray(
		new String[] {"uuid", "size"});

	@Override
	protected FinderCache getFinderCache() {
		return finderCache;
	}

	@Reference
	private ObjectLayoutColumnModelArgumentsResolver
		_objectLayoutColumnModelArgumentsResolver;

}