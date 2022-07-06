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

import com.liferay.object.exception.NoSuchObjectStateException;
import com.liferay.object.model.ObjectState;
import com.liferay.object.model.ObjectStateTable;
import com.liferay.object.model.impl.ObjectStateImpl;
import com.liferay.object.model.impl.ObjectStateModelImpl;
import com.liferay.object.service.persistence.ObjectStatePersistence;
import com.liferay.object.service.persistence.ObjectStateUtil;
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
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.uuid.PortalUUID;

import java.io.Serializable;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;

import java.util.Collections;
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
 * The persistence implementation for the object state service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Marco Leo
 * @generated
 */
@Component(service = {ObjectStatePersistence.class, BasePersistence.class})
public class ObjectStatePersistenceImpl
	extends BasePersistenceImpl<ObjectState> implements ObjectStatePersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>ObjectStateUtil</code> to access the object state persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		ObjectStateImpl.class.getName();

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
	 * Returns all the object states where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching object states
	 */
	@Override
	public List<ObjectState> findByUuid(String uuid) {
		return findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the object states where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectStateModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of object states
	 * @param end the upper bound of the range of object states (not inclusive)
	 * @return the range of matching object states
	 */
	@Override
	public List<ObjectState> findByUuid(String uuid, int start, int end) {
		return findByUuid(uuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the object states where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectStateModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of object states
	 * @param end the upper bound of the range of object states (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching object states
	 */
	@Override
	public List<ObjectState> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<ObjectState> orderByComparator) {

		return findByUuid(uuid, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the object states where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectStateModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of object states
	 * @param end the upper bound of the range of object states (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching object states
	 */
	@Override
	public List<ObjectState> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<ObjectState> orderByComparator,
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

		List<ObjectState> list = null;

		if (useFinderCache) {
			list = (List<ObjectState>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (ObjectState objectState : list) {
					if (!uuid.equals(objectState.getUuid())) {
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

			sb.append(_SQL_SELECT_OBJECTSTATE_WHERE);

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
				sb.append(ObjectStateModelImpl.ORDER_BY_JPQL);
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

				list = (List<ObjectState>)QueryUtil.list(
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
	 * Returns the first object state in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object state
	 * @throws NoSuchObjectStateException if a matching object state could not be found
	 */
	@Override
	public ObjectState findByUuid_First(
			String uuid, OrderByComparator<ObjectState> orderByComparator)
		throws NoSuchObjectStateException {

		ObjectState objectState = fetchByUuid_First(uuid, orderByComparator);

		if (objectState != null) {
			return objectState;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append("}");

		throw new NoSuchObjectStateException(sb.toString());
	}

	/**
	 * Returns the first object state in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object state, or <code>null</code> if a matching object state could not be found
	 */
	@Override
	public ObjectState fetchByUuid_First(
		String uuid, OrderByComparator<ObjectState> orderByComparator) {

		List<ObjectState> list = findByUuid(uuid, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last object state in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object state
	 * @throws NoSuchObjectStateException if a matching object state could not be found
	 */
	@Override
	public ObjectState findByUuid_Last(
			String uuid, OrderByComparator<ObjectState> orderByComparator)
		throws NoSuchObjectStateException {

		ObjectState objectState = fetchByUuid_Last(uuid, orderByComparator);

		if (objectState != null) {
			return objectState;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append("}");

		throw new NoSuchObjectStateException(sb.toString());
	}

	/**
	 * Returns the last object state in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object state, or <code>null</code> if a matching object state could not be found
	 */
	@Override
	public ObjectState fetchByUuid_Last(
		String uuid, OrderByComparator<ObjectState> orderByComparator) {

		int count = countByUuid(uuid);

		if (count == 0) {
			return null;
		}

		List<ObjectState> list = findByUuid(
			uuid, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the object states before and after the current object state in the ordered set where uuid = &#63;.
	 *
	 * @param objectStateId the primary key of the current object state
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next object state
	 * @throws NoSuchObjectStateException if a object state with the primary key could not be found
	 */
	@Override
	public ObjectState[] findByUuid_PrevAndNext(
			long objectStateId, String uuid,
			OrderByComparator<ObjectState> orderByComparator)
		throws NoSuchObjectStateException {

		uuid = Objects.toString(uuid, "");

		ObjectState objectState = findByPrimaryKey(objectStateId);

		Session session = null;

		try {
			session = openSession();

			ObjectState[] array = new ObjectStateImpl[3];

			array[0] = getByUuid_PrevAndNext(
				session, objectState, uuid, orderByComparator, true);

			array[1] = objectState;

			array[2] = getByUuid_PrevAndNext(
				session, objectState, uuid, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected ObjectState getByUuid_PrevAndNext(
		Session session, ObjectState objectState, String uuid,
		OrderByComparator<ObjectState> orderByComparator, boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(3);
		}

		sb.append(_SQL_SELECT_OBJECTSTATE_WHERE);

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
			sb.append(ObjectStateModelImpl.ORDER_BY_JPQL);
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
					orderByComparator.getOrderByConditionValues(objectState)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<ObjectState> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the object states where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	@Override
	public void removeByUuid(String uuid) {
		for (ObjectState objectState :
				findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(objectState);
		}
	}

	/**
	 * Returns the number of object states where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching object states
	 */
	@Override
	public int countByUuid(String uuid) {
		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = _finderPathCountByUuid;

		Object[] finderArgs = new Object[] {uuid};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_OBJECTSTATE_WHERE);

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
		"objectState.uuid = ?";

	private static final String _FINDER_COLUMN_UUID_UUID_3 =
		"(objectState.uuid IS NULL OR objectState.uuid = '')";

	private FinderPath _finderPathWithPaginationFindByUuid_C;
	private FinderPath _finderPathWithoutPaginationFindByUuid_C;
	private FinderPath _finderPathCountByUuid_C;

	/**
	 * Returns all the object states where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching object states
	 */
	@Override
	public List<ObjectState> findByUuid_C(String uuid, long companyId) {
		return findByUuid_C(
			uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the object states where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectStateModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of object states
	 * @param end the upper bound of the range of object states (not inclusive)
	 * @return the range of matching object states
	 */
	@Override
	public List<ObjectState> findByUuid_C(
		String uuid, long companyId, int start, int end) {

		return findByUuid_C(uuid, companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the object states where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectStateModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of object states
	 * @param end the upper bound of the range of object states (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching object states
	 */
	@Override
	public List<ObjectState> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<ObjectState> orderByComparator) {

		return findByUuid_C(
			uuid, companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the object states where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectStateModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of object states
	 * @param end the upper bound of the range of object states (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching object states
	 */
	@Override
	public List<ObjectState> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<ObjectState> orderByComparator,
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

		List<ObjectState> list = null;

		if (useFinderCache) {
			list = (List<ObjectState>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (ObjectState objectState : list) {
					if (!uuid.equals(objectState.getUuid()) ||
						(companyId != objectState.getCompanyId())) {

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

			sb.append(_SQL_SELECT_OBJECTSTATE_WHERE);

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
				sb.append(ObjectStateModelImpl.ORDER_BY_JPQL);
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

				list = (List<ObjectState>)QueryUtil.list(
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
	 * Returns the first object state in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object state
	 * @throws NoSuchObjectStateException if a matching object state could not be found
	 */
	@Override
	public ObjectState findByUuid_C_First(
			String uuid, long companyId,
			OrderByComparator<ObjectState> orderByComparator)
		throws NoSuchObjectStateException {

		ObjectState objectState = fetchByUuid_C_First(
			uuid, companyId, orderByComparator);

		if (objectState != null) {
			return objectState;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append(", companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchObjectStateException(sb.toString());
	}

	/**
	 * Returns the first object state in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object state, or <code>null</code> if a matching object state could not be found
	 */
	@Override
	public ObjectState fetchByUuid_C_First(
		String uuid, long companyId,
		OrderByComparator<ObjectState> orderByComparator) {

		List<ObjectState> list = findByUuid_C(
			uuid, companyId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last object state in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object state
	 * @throws NoSuchObjectStateException if a matching object state could not be found
	 */
	@Override
	public ObjectState findByUuid_C_Last(
			String uuid, long companyId,
			OrderByComparator<ObjectState> orderByComparator)
		throws NoSuchObjectStateException {

		ObjectState objectState = fetchByUuid_C_Last(
			uuid, companyId, orderByComparator);

		if (objectState != null) {
			return objectState;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append(", companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchObjectStateException(sb.toString());
	}

	/**
	 * Returns the last object state in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object state, or <code>null</code> if a matching object state could not be found
	 */
	@Override
	public ObjectState fetchByUuid_C_Last(
		String uuid, long companyId,
		OrderByComparator<ObjectState> orderByComparator) {

		int count = countByUuid_C(uuid, companyId);

		if (count == 0) {
			return null;
		}

		List<ObjectState> list = findByUuid_C(
			uuid, companyId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the object states before and after the current object state in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param objectStateId the primary key of the current object state
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next object state
	 * @throws NoSuchObjectStateException if a object state with the primary key could not be found
	 */
	@Override
	public ObjectState[] findByUuid_C_PrevAndNext(
			long objectStateId, String uuid, long companyId,
			OrderByComparator<ObjectState> orderByComparator)
		throws NoSuchObjectStateException {

		uuid = Objects.toString(uuid, "");

		ObjectState objectState = findByPrimaryKey(objectStateId);

		Session session = null;

		try {
			session = openSession();

			ObjectState[] array = new ObjectStateImpl[3];

			array[0] = getByUuid_C_PrevAndNext(
				session, objectState, uuid, companyId, orderByComparator, true);

			array[1] = objectState;

			array[2] = getByUuid_C_PrevAndNext(
				session, objectState, uuid, companyId, orderByComparator,
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

	protected ObjectState getByUuid_C_PrevAndNext(
		Session session, ObjectState objectState, String uuid, long companyId,
		OrderByComparator<ObjectState> orderByComparator, boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				5 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(4);
		}

		sb.append(_SQL_SELECT_OBJECTSTATE_WHERE);

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
			sb.append(ObjectStateModelImpl.ORDER_BY_JPQL);
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
					orderByComparator.getOrderByConditionValues(objectState)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<ObjectState> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the object states where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	@Override
	public void removeByUuid_C(String uuid, long companyId) {
		for (ObjectState objectState :
				findByUuid_C(
					uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(objectState);
		}
	}

	/**
	 * Returns the number of object states where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching object states
	 */
	@Override
	public int countByUuid_C(String uuid, long companyId) {
		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = _finderPathCountByUuid_C;

		Object[] finderArgs = new Object[] {uuid, companyId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_OBJECTSTATE_WHERE);

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
		"objectState.uuid = ? AND ";

	private static final String _FINDER_COLUMN_UUID_C_UUID_3 =
		"(objectState.uuid IS NULL OR objectState.uuid = '') AND ";

	private static final String _FINDER_COLUMN_UUID_C_COMPANYID_2 =
		"objectState.companyId = ?";

	private FinderPath _finderPathWithPaginationFindByListTypeEntryId;
	private FinderPath _finderPathWithoutPaginationFindByListTypeEntryId;
	private FinderPath _finderPathCountByListTypeEntryId;

	/**
	 * Returns all the object states where listTypeEntryId = &#63;.
	 *
	 * @param listTypeEntryId the list type entry ID
	 * @return the matching object states
	 */
	@Override
	public List<ObjectState> findByListTypeEntryId(long listTypeEntryId) {
		return findByListTypeEntryId(
			listTypeEntryId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the object states where listTypeEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectStateModelImpl</code>.
	 * </p>
	 *
	 * @param listTypeEntryId the list type entry ID
	 * @param start the lower bound of the range of object states
	 * @param end the upper bound of the range of object states (not inclusive)
	 * @return the range of matching object states
	 */
	@Override
	public List<ObjectState> findByListTypeEntryId(
		long listTypeEntryId, int start, int end) {

		return findByListTypeEntryId(listTypeEntryId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the object states where listTypeEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectStateModelImpl</code>.
	 * </p>
	 *
	 * @param listTypeEntryId the list type entry ID
	 * @param start the lower bound of the range of object states
	 * @param end the upper bound of the range of object states (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching object states
	 */
	@Override
	public List<ObjectState> findByListTypeEntryId(
		long listTypeEntryId, int start, int end,
		OrderByComparator<ObjectState> orderByComparator) {

		return findByListTypeEntryId(
			listTypeEntryId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the object states where listTypeEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectStateModelImpl</code>.
	 * </p>
	 *
	 * @param listTypeEntryId the list type entry ID
	 * @param start the lower bound of the range of object states
	 * @param end the upper bound of the range of object states (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching object states
	 */
	@Override
	public List<ObjectState> findByListTypeEntryId(
		long listTypeEntryId, int start, int end,
		OrderByComparator<ObjectState> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByListTypeEntryId;
				finderArgs = new Object[] {listTypeEntryId};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByListTypeEntryId;
			finderArgs = new Object[] {
				listTypeEntryId, start, end, orderByComparator
			};
		}

		List<ObjectState> list = null;

		if (useFinderCache) {
			list = (List<ObjectState>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (ObjectState objectState : list) {
					if (listTypeEntryId != objectState.getListTypeEntryId()) {
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

			sb.append(_SQL_SELECT_OBJECTSTATE_WHERE);

			sb.append(_FINDER_COLUMN_LISTTYPEENTRYID_LISTTYPEENTRYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(ObjectStateModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(listTypeEntryId);

				list = (List<ObjectState>)QueryUtil.list(
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
	 * Returns the first object state in the ordered set where listTypeEntryId = &#63;.
	 *
	 * @param listTypeEntryId the list type entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object state
	 * @throws NoSuchObjectStateException if a matching object state could not be found
	 */
	@Override
	public ObjectState findByListTypeEntryId_First(
			long listTypeEntryId,
			OrderByComparator<ObjectState> orderByComparator)
		throws NoSuchObjectStateException {

		ObjectState objectState = fetchByListTypeEntryId_First(
			listTypeEntryId, orderByComparator);

		if (objectState != null) {
			return objectState;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("listTypeEntryId=");
		sb.append(listTypeEntryId);

		sb.append("}");

		throw new NoSuchObjectStateException(sb.toString());
	}

	/**
	 * Returns the first object state in the ordered set where listTypeEntryId = &#63;.
	 *
	 * @param listTypeEntryId the list type entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object state, or <code>null</code> if a matching object state could not be found
	 */
	@Override
	public ObjectState fetchByListTypeEntryId_First(
		long listTypeEntryId,
		OrderByComparator<ObjectState> orderByComparator) {

		List<ObjectState> list = findByListTypeEntryId(
			listTypeEntryId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last object state in the ordered set where listTypeEntryId = &#63;.
	 *
	 * @param listTypeEntryId the list type entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object state
	 * @throws NoSuchObjectStateException if a matching object state could not be found
	 */
	@Override
	public ObjectState findByListTypeEntryId_Last(
			long listTypeEntryId,
			OrderByComparator<ObjectState> orderByComparator)
		throws NoSuchObjectStateException {

		ObjectState objectState = fetchByListTypeEntryId_Last(
			listTypeEntryId, orderByComparator);

		if (objectState != null) {
			return objectState;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("listTypeEntryId=");
		sb.append(listTypeEntryId);

		sb.append("}");

		throw new NoSuchObjectStateException(sb.toString());
	}

	/**
	 * Returns the last object state in the ordered set where listTypeEntryId = &#63;.
	 *
	 * @param listTypeEntryId the list type entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object state, or <code>null</code> if a matching object state could not be found
	 */
	@Override
	public ObjectState fetchByListTypeEntryId_Last(
		long listTypeEntryId,
		OrderByComparator<ObjectState> orderByComparator) {

		int count = countByListTypeEntryId(listTypeEntryId);

		if (count == 0) {
			return null;
		}

		List<ObjectState> list = findByListTypeEntryId(
			listTypeEntryId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the object states before and after the current object state in the ordered set where listTypeEntryId = &#63;.
	 *
	 * @param objectStateId the primary key of the current object state
	 * @param listTypeEntryId the list type entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next object state
	 * @throws NoSuchObjectStateException if a object state with the primary key could not be found
	 */
	@Override
	public ObjectState[] findByListTypeEntryId_PrevAndNext(
			long objectStateId, long listTypeEntryId,
			OrderByComparator<ObjectState> orderByComparator)
		throws NoSuchObjectStateException {

		ObjectState objectState = findByPrimaryKey(objectStateId);

		Session session = null;

		try {
			session = openSession();

			ObjectState[] array = new ObjectStateImpl[3];

			array[0] = getByListTypeEntryId_PrevAndNext(
				session, objectState, listTypeEntryId, orderByComparator, true);

			array[1] = objectState;

			array[2] = getByListTypeEntryId_PrevAndNext(
				session, objectState, listTypeEntryId, orderByComparator,
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

	protected ObjectState getByListTypeEntryId_PrevAndNext(
		Session session, ObjectState objectState, long listTypeEntryId,
		OrderByComparator<ObjectState> orderByComparator, boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(3);
		}

		sb.append(_SQL_SELECT_OBJECTSTATE_WHERE);

		sb.append(_FINDER_COLUMN_LISTTYPEENTRYID_LISTTYPEENTRYID_2);

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
			sb.append(ObjectStateModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(listTypeEntryId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(objectState)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<ObjectState> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the object states where listTypeEntryId = &#63; from the database.
	 *
	 * @param listTypeEntryId the list type entry ID
	 */
	@Override
	public void removeByListTypeEntryId(long listTypeEntryId) {
		for (ObjectState objectState :
				findByListTypeEntryId(
					listTypeEntryId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(objectState);
		}
	}

	/**
	 * Returns the number of object states where listTypeEntryId = &#63;.
	 *
	 * @param listTypeEntryId the list type entry ID
	 * @return the number of matching object states
	 */
	@Override
	public int countByListTypeEntryId(long listTypeEntryId) {
		FinderPath finderPath = _finderPathCountByListTypeEntryId;

		Object[] finderArgs = new Object[] {listTypeEntryId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_OBJECTSTATE_WHERE);

			sb.append(_FINDER_COLUMN_LISTTYPEENTRYID_LISTTYPEENTRYID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(listTypeEntryId);

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
		_FINDER_COLUMN_LISTTYPEENTRYID_LISTTYPEENTRYID_2 =
			"objectState.listTypeEntryId = ?";

	private FinderPath _finderPathWithPaginationFindByObjectStateFlowId;
	private FinderPath _finderPathWithoutPaginationFindByObjectStateFlowId;
	private FinderPath _finderPathCountByObjectStateFlowId;

	/**
	 * Returns all the object states where objectStateFlowId = &#63;.
	 *
	 * @param objectStateFlowId the object state flow ID
	 * @return the matching object states
	 */
	@Override
	public List<ObjectState> findByObjectStateFlowId(long objectStateFlowId) {
		return findByObjectStateFlowId(
			objectStateFlowId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the object states where objectStateFlowId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectStateModelImpl</code>.
	 * </p>
	 *
	 * @param objectStateFlowId the object state flow ID
	 * @param start the lower bound of the range of object states
	 * @param end the upper bound of the range of object states (not inclusive)
	 * @return the range of matching object states
	 */
	@Override
	public List<ObjectState> findByObjectStateFlowId(
		long objectStateFlowId, int start, int end) {

		return findByObjectStateFlowId(objectStateFlowId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the object states where objectStateFlowId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectStateModelImpl</code>.
	 * </p>
	 *
	 * @param objectStateFlowId the object state flow ID
	 * @param start the lower bound of the range of object states
	 * @param end the upper bound of the range of object states (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching object states
	 */
	@Override
	public List<ObjectState> findByObjectStateFlowId(
		long objectStateFlowId, int start, int end,
		OrderByComparator<ObjectState> orderByComparator) {

		return findByObjectStateFlowId(
			objectStateFlowId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the object states where objectStateFlowId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectStateModelImpl</code>.
	 * </p>
	 *
	 * @param objectStateFlowId the object state flow ID
	 * @param start the lower bound of the range of object states
	 * @param end the upper bound of the range of object states (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching object states
	 */
	@Override
	public List<ObjectState> findByObjectStateFlowId(
		long objectStateFlowId, int start, int end,
		OrderByComparator<ObjectState> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath =
					_finderPathWithoutPaginationFindByObjectStateFlowId;
				finderArgs = new Object[] {objectStateFlowId};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByObjectStateFlowId;
			finderArgs = new Object[] {
				objectStateFlowId, start, end, orderByComparator
			};
		}

		List<ObjectState> list = null;

		if (useFinderCache) {
			list = (List<ObjectState>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (ObjectState objectState : list) {
					if (objectStateFlowId !=
							objectState.getObjectStateFlowId()) {

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

			sb.append(_SQL_SELECT_OBJECTSTATE_WHERE);

			sb.append(_FINDER_COLUMN_OBJECTSTATEFLOWID_OBJECTSTATEFLOWID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(ObjectStateModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(objectStateFlowId);

				list = (List<ObjectState>)QueryUtil.list(
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
	 * Returns the first object state in the ordered set where objectStateFlowId = &#63;.
	 *
	 * @param objectStateFlowId the object state flow ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object state
	 * @throws NoSuchObjectStateException if a matching object state could not be found
	 */
	@Override
	public ObjectState findByObjectStateFlowId_First(
			long objectStateFlowId,
			OrderByComparator<ObjectState> orderByComparator)
		throws NoSuchObjectStateException {

		ObjectState objectState = fetchByObjectStateFlowId_First(
			objectStateFlowId, orderByComparator);

		if (objectState != null) {
			return objectState;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("objectStateFlowId=");
		sb.append(objectStateFlowId);

		sb.append("}");

		throw new NoSuchObjectStateException(sb.toString());
	}

	/**
	 * Returns the first object state in the ordered set where objectStateFlowId = &#63;.
	 *
	 * @param objectStateFlowId the object state flow ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object state, or <code>null</code> if a matching object state could not be found
	 */
	@Override
	public ObjectState fetchByObjectStateFlowId_First(
		long objectStateFlowId,
		OrderByComparator<ObjectState> orderByComparator) {

		List<ObjectState> list = findByObjectStateFlowId(
			objectStateFlowId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last object state in the ordered set where objectStateFlowId = &#63;.
	 *
	 * @param objectStateFlowId the object state flow ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object state
	 * @throws NoSuchObjectStateException if a matching object state could not be found
	 */
	@Override
	public ObjectState findByObjectStateFlowId_Last(
			long objectStateFlowId,
			OrderByComparator<ObjectState> orderByComparator)
		throws NoSuchObjectStateException {

		ObjectState objectState = fetchByObjectStateFlowId_Last(
			objectStateFlowId, orderByComparator);

		if (objectState != null) {
			return objectState;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("objectStateFlowId=");
		sb.append(objectStateFlowId);

		sb.append("}");

		throw new NoSuchObjectStateException(sb.toString());
	}

	/**
	 * Returns the last object state in the ordered set where objectStateFlowId = &#63;.
	 *
	 * @param objectStateFlowId the object state flow ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object state, or <code>null</code> if a matching object state could not be found
	 */
	@Override
	public ObjectState fetchByObjectStateFlowId_Last(
		long objectStateFlowId,
		OrderByComparator<ObjectState> orderByComparator) {

		int count = countByObjectStateFlowId(objectStateFlowId);

		if (count == 0) {
			return null;
		}

		List<ObjectState> list = findByObjectStateFlowId(
			objectStateFlowId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the object states before and after the current object state in the ordered set where objectStateFlowId = &#63;.
	 *
	 * @param objectStateId the primary key of the current object state
	 * @param objectStateFlowId the object state flow ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next object state
	 * @throws NoSuchObjectStateException if a object state with the primary key could not be found
	 */
	@Override
	public ObjectState[] findByObjectStateFlowId_PrevAndNext(
			long objectStateId, long objectStateFlowId,
			OrderByComparator<ObjectState> orderByComparator)
		throws NoSuchObjectStateException {

		ObjectState objectState = findByPrimaryKey(objectStateId);

		Session session = null;

		try {
			session = openSession();

			ObjectState[] array = new ObjectStateImpl[3];

			array[0] = getByObjectStateFlowId_PrevAndNext(
				session, objectState, objectStateFlowId, orderByComparator,
				true);

			array[1] = objectState;

			array[2] = getByObjectStateFlowId_PrevAndNext(
				session, objectState, objectStateFlowId, orderByComparator,
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

	protected ObjectState getByObjectStateFlowId_PrevAndNext(
		Session session, ObjectState objectState, long objectStateFlowId,
		OrderByComparator<ObjectState> orderByComparator, boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(3);
		}

		sb.append(_SQL_SELECT_OBJECTSTATE_WHERE);

		sb.append(_FINDER_COLUMN_OBJECTSTATEFLOWID_OBJECTSTATEFLOWID_2);

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
			sb.append(ObjectStateModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(objectStateFlowId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(objectState)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<ObjectState> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the object states where objectStateFlowId = &#63; from the database.
	 *
	 * @param objectStateFlowId the object state flow ID
	 */
	@Override
	public void removeByObjectStateFlowId(long objectStateFlowId) {
		for (ObjectState objectState :
				findByObjectStateFlowId(
					objectStateFlowId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(objectState);
		}
	}

	/**
	 * Returns the number of object states where objectStateFlowId = &#63;.
	 *
	 * @param objectStateFlowId the object state flow ID
	 * @return the number of matching object states
	 */
	@Override
	public int countByObjectStateFlowId(long objectStateFlowId) {
		FinderPath finderPath = _finderPathCountByObjectStateFlowId;

		Object[] finderArgs = new Object[] {objectStateFlowId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_OBJECTSTATE_WHERE);

			sb.append(_FINDER_COLUMN_OBJECTSTATEFLOWID_OBJECTSTATEFLOWID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(objectStateFlowId);

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
		_FINDER_COLUMN_OBJECTSTATEFLOWID_OBJECTSTATEFLOWID_2 =
			"objectState.objectStateFlowId = ?";

	private FinderPath _finderPathFetchByLTEI_OSFI;
	private FinderPath _finderPathCountByLTEI_OSFI;

	/**
	 * Returns the object state where listTypeEntryId = &#63; and objectStateFlowId = &#63; or throws a <code>NoSuchObjectStateException</code> if it could not be found.
	 *
	 * @param listTypeEntryId the list type entry ID
	 * @param objectStateFlowId the object state flow ID
	 * @return the matching object state
	 * @throws NoSuchObjectStateException if a matching object state could not be found
	 */
	@Override
	public ObjectState findByLTEI_OSFI(
			long listTypeEntryId, long objectStateFlowId)
		throws NoSuchObjectStateException {

		ObjectState objectState = fetchByLTEI_OSFI(
			listTypeEntryId, objectStateFlowId);

		if (objectState == null) {
			StringBundler sb = new StringBundler(6);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("listTypeEntryId=");
			sb.append(listTypeEntryId);

			sb.append(", objectStateFlowId=");
			sb.append(objectStateFlowId);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchObjectStateException(sb.toString());
		}

		return objectState;
	}

	/**
	 * Returns the object state where listTypeEntryId = &#63; and objectStateFlowId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param listTypeEntryId the list type entry ID
	 * @param objectStateFlowId the object state flow ID
	 * @return the matching object state, or <code>null</code> if a matching object state could not be found
	 */
	@Override
	public ObjectState fetchByLTEI_OSFI(
		long listTypeEntryId, long objectStateFlowId) {

		return fetchByLTEI_OSFI(listTypeEntryId, objectStateFlowId, true);
	}

	/**
	 * Returns the object state where listTypeEntryId = &#63; and objectStateFlowId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param listTypeEntryId the list type entry ID
	 * @param objectStateFlowId the object state flow ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching object state, or <code>null</code> if a matching object state could not be found
	 */
	@Override
	public ObjectState fetchByLTEI_OSFI(
		long listTypeEntryId, long objectStateFlowId, boolean useFinderCache) {

		Object[] finderArgs = null;

		if (useFinderCache) {
			finderArgs = new Object[] {listTypeEntryId, objectStateFlowId};
		}

		Object result = null;

		if (useFinderCache) {
			result = finderCache.getResult(
				_finderPathFetchByLTEI_OSFI, finderArgs);
		}

		if (result instanceof ObjectState) {
			ObjectState objectState = (ObjectState)result;

			if ((listTypeEntryId != objectState.getListTypeEntryId()) ||
				(objectStateFlowId != objectState.getObjectStateFlowId())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_SELECT_OBJECTSTATE_WHERE);

			sb.append(_FINDER_COLUMN_LTEI_OSFI_LISTTYPEENTRYID_2);

			sb.append(_FINDER_COLUMN_LTEI_OSFI_OBJECTSTATEFLOWID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(listTypeEntryId);

				queryPos.add(objectStateFlowId);

				List<ObjectState> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache) {
						finderCache.putResult(
							_finderPathFetchByLTEI_OSFI, finderArgs, list);
					}
				}
				else {
					if (list.size() > 1) {
						Collections.sort(list, Collections.reverseOrder());

						if (_log.isWarnEnabled()) {
							if (!useFinderCache) {
								finderArgs = new Object[] {
									listTypeEntryId, objectStateFlowId
								};
							}

							_log.warn(
								"ObjectStatePersistenceImpl.fetchByLTEI_OSFI(long, long, boolean) with parameters (" +
									StringUtil.merge(finderArgs) +
										") yields a result set with more than 1 result. This violates the logical unique restriction. There is no order guarantee on which result is returned by this finder.");
						}
					}

					ObjectState objectState = list.get(0);

					result = objectState;

					cacheResult(objectState);
				}
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		if (result instanceof List<?>) {
			return null;
		}
		else {
			return (ObjectState)result;
		}
	}

	/**
	 * Removes the object state where listTypeEntryId = &#63; and objectStateFlowId = &#63; from the database.
	 *
	 * @param listTypeEntryId the list type entry ID
	 * @param objectStateFlowId the object state flow ID
	 * @return the object state that was removed
	 */
	@Override
	public ObjectState removeByLTEI_OSFI(
			long listTypeEntryId, long objectStateFlowId)
		throws NoSuchObjectStateException {

		ObjectState objectState = findByLTEI_OSFI(
			listTypeEntryId, objectStateFlowId);

		return remove(objectState);
	}

	/**
	 * Returns the number of object states where listTypeEntryId = &#63; and objectStateFlowId = &#63;.
	 *
	 * @param listTypeEntryId the list type entry ID
	 * @param objectStateFlowId the object state flow ID
	 * @return the number of matching object states
	 */
	@Override
	public int countByLTEI_OSFI(long listTypeEntryId, long objectStateFlowId) {
		FinderPath finderPath = _finderPathCountByLTEI_OSFI;

		Object[] finderArgs = new Object[] {listTypeEntryId, objectStateFlowId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_OBJECTSTATE_WHERE);

			sb.append(_FINDER_COLUMN_LTEI_OSFI_LISTTYPEENTRYID_2);

			sb.append(_FINDER_COLUMN_LTEI_OSFI_OBJECTSTATEFLOWID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(listTypeEntryId);

				queryPos.add(objectStateFlowId);

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

	private static final String _FINDER_COLUMN_LTEI_OSFI_LISTTYPEENTRYID_2 =
		"objectState.listTypeEntryId = ? AND ";

	private static final String _FINDER_COLUMN_LTEI_OSFI_OBJECTSTATEFLOWID_2 =
		"objectState.objectStateFlowId = ?";

	public ObjectStatePersistenceImpl() {
		Map<String, String> dbColumnNames = new HashMap<String, String>();

		dbColumnNames.put("uuid", "uuid_");

		setDBColumnNames(dbColumnNames);

		setModelClass(ObjectState.class);

		setModelImplClass(ObjectStateImpl.class);
		setModelPKClass(long.class);

		setTable(ObjectStateTable.INSTANCE);
	}

	/**
	 * Caches the object state in the entity cache if it is enabled.
	 *
	 * @param objectState the object state
	 */
	@Override
	public void cacheResult(ObjectState objectState) {
		entityCache.putResult(
			ObjectStateImpl.class, objectState.getPrimaryKey(), objectState);

		finderCache.putResult(
			_finderPathFetchByLTEI_OSFI,
			new Object[] {
				objectState.getListTypeEntryId(),
				objectState.getObjectStateFlowId()
			},
			objectState);
	}

	private int _valueObjectFinderCacheListThreshold;

	/**
	 * Caches the object states in the entity cache if it is enabled.
	 *
	 * @param objectStates the object states
	 */
	@Override
	public void cacheResult(List<ObjectState> objectStates) {
		if ((_valueObjectFinderCacheListThreshold == 0) ||
			((_valueObjectFinderCacheListThreshold > 0) &&
			 (objectStates.size() > _valueObjectFinderCacheListThreshold))) {

			return;
		}

		for (ObjectState objectState : objectStates) {
			if (entityCache.getResult(
					ObjectStateImpl.class, objectState.getPrimaryKey()) ==
						null) {

				cacheResult(objectState);
			}
		}
	}

	/**
	 * Clears the cache for all object states.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(ObjectStateImpl.class);

		finderCache.clearCache(ObjectStateImpl.class);
	}

	/**
	 * Clears the cache for the object state.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(ObjectState objectState) {
		entityCache.removeResult(ObjectStateImpl.class, objectState);
	}

	@Override
	public void clearCache(List<ObjectState> objectStates) {
		for (ObjectState objectState : objectStates) {
			entityCache.removeResult(ObjectStateImpl.class, objectState);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(ObjectStateImpl.class);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(ObjectStateImpl.class, primaryKey);
		}
	}

	protected void cacheUniqueFindersCache(
		ObjectStateModelImpl objectStateModelImpl) {

		Object[] args = new Object[] {
			objectStateModelImpl.getListTypeEntryId(),
			objectStateModelImpl.getObjectStateFlowId()
		};

		finderCache.putResult(
			_finderPathCountByLTEI_OSFI, args, Long.valueOf(1));
		finderCache.putResult(
			_finderPathFetchByLTEI_OSFI, args, objectStateModelImpl);
	}

	/**
	 * Creates a new object state with the primary key. Does not add the object state to the database.
	 *
	 * @param objectStateId the primary key for the new object state
	 * @return the new object state
	 */
	@Override
	public ObjectState create(long objectStateId) {
		ObjectState objectState = new ObjectStateImpl();

		objectState.setNew(true);
		objectState.setPrimaryKey(objectStateId);

		String uuid = _portalUUID.generate();

		objectState.setUuid(uuid);

		objectState.setCompanyId(CompanyThreadLocal.getCompanyId());

		return objectState;
	}

	/**
	 * Removes the object state with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param objectStateId the primary key of the object state
	 * @return the object state that was removed
	 * @throws NoSuchObjectStateException if a object state with the primary key could not be found
	 */
	@Override
	public ObjectState remove(long objectStateId)
		throws NoSuchObjectStateException {

		return remove((Serializable)objectStateId);
	}

	/**
	 * Removes the object state with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the object state
	 * @return the object state that was removed
	 * @throws NoSuchObjectStateException if a object state with the primary key could not be found
	 */
	@Override
	public ObjectState remove(Serializable primaryKey)
		throws NoSuchObjectStateException {

		Session session = null;

		try {
			session = openSession();

			ObjectState objectState = (ObjectState)session.get(
				ObjectStateImpl.class, primaryKey);

			if (objectState == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchObjectStateException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(objectState);
		}
		catch (NoSuchObjectStateException noSuchEntityException) {
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
	protected ObjectState removeImpl(ObjectState objectState) {
		Session session = null;

		try {
			session = openSession();

			if (!session.contains(objectState)) {
				objectState = (ObjectState)session.get(
					ObjectStateImpl.class, objectState.getPrimaryKeyObj());
			}

			if (objectState != null) {
				session.delete(objectState);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (objectState != null) {
			clearCache(objectState);
		}

		return objectState;
	}

	@Override
	public ObjectState updateImpl(ObjectState objectState) {
		boolean isNew = objectState.isNew();

		if (!(objectState instanceof ObjectStateModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(objectState.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(objectState);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in objectState proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom ObjectState implementation " +
					objectState.getClass());
		}

		ObjectStateModelImpl objectStateModelImpl =
			(ObjectStateModelImpl)objectState;

		if (Validator.isNull(objectState.getUuid())) {
			String uuid = _portalUUID.generate();

			objectState.setUuid(uuid);
		}

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Date date = new Date();

		if (isNew && (objectState.getCreateDate() == null)) {
			if (serviceContext == null) {
				objectState.setCreateDate(date);
			}
			else {
				objectState.setCreateDate(serviceContext.getCreateDate(date));
			}
		}

		if (!objectStateModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				objectState.setModifiedDate(date);
			}
			else {
				objectState.setModifiedDate(
					serviceContext.getModifiedDate(date));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (isNew) {
				session.save(objectState);
			}
			else {
				objectState = (ObjectState)session.merge(objectState);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		entityCache.putResult(
			ObjectStateImpl.class, objectStateModelImpl, false, true);

		cacheUniqueFindersCache(objectStateModelImpl);

		if (isNew) {
			objectState.setNew(false);
		}

		objectState.resetOriginalValues();

		return objectState;
	}

	/**
	 * Returns the object state with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the object state
	 * @return the object state
	 * @throws NoSuchObjectStateException if a object state with the primary key could not be found
	 */
	@Override
	public ObjectState findByPrimaryKey(Serializable primaryKey)
		throws NoSuchObjectStateException {

		ObjectState objectState = fetchByPrimaryKey(primaryKey);

		if (objectState == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchObjectStateException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return objectState;
	}

	/**
	 * Returns the object state with the primary key or throws a <code>NoSuchObjectStateException</code> if it could not be found.
	 *
	 * @param objectStateId the primary key of the object state
	 * @return the object state
	 * @throws NoSuchObjectStateException if a object state with the primary key could not be found
	 */
	@Override
	public ObjectState findByPrimaryKey(long objectStateId)
		throws NoSuchObjectStateException {

		return findByPrimaryKey((Serializable)objectStateId);
	}

	/**
	 * Returns the object state with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param objectStateId the primary key of the object state
	 * @return the object state, or <code>null</code> if a object state with the primary key could not be found
	 */
	@Override
	public ObjectState fetchByPrimaryKey(long objectStateId) {
		return fetchByPrimaryKey((Serializable)objectStateId);
	}

	/**
	 * Returns all the object states.
	 *
	 * @return the object states
	 */
	@Override
	public List<ObjectState> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the object states.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectStateModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of object states
	 * @param end the upper bound of the range of object states (not inclusive)
	 * @return the range of object states
	 */
	@Override
	public List<ObjectState> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the object states.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectStateModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of object states
	 * @param end the upper bound of the range of object states (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of object states
	 */
	@Override
	public List<ObjectState> findAll(
		int start, int end, OrderByComparator<ObjectState> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the object states.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectStateModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of object states
	 * @param end the upper bound of the range of object states (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of object states
	 */
	@Override
	public List<ObjectState> findAll(
		int start, int end, OrderByComparator<ObjectState> orderByComparator,
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

		List<ObjectState> list = null;

		if (useFinderCache) {
			list = (List<ObjectState>)finderCache.getResult(
				finderPath, finderArgs);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_OBJECTSTATE);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_OBJECTSTATE;

				sql = sql.concat(ObjectStateModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<ObjectState>)QueryUtil.list(
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
	 * Removes all the object states from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (ObjectState objectState : findAll()) {
			remove(objectState);
		}
	}

	/**
	 * Returns the number of object states.
	 *
	 * @return the number of object states
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(
			_finderPathCountAll, FINDER_ARGS_EMPTY);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(_SQL_COUNT_OBJECTSTATE);

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
		return "objectStateId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_OBJECTSTATE;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return ObjectStateModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the object state persistence.
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

		_finderPathWithPaginationFindByListTypeEntryId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByListTypeEntryId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"listTypeEntryId"}, true);

		_finderPathWithoutPaginationFindByListTypeEntryId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByListTypeEntryId",
			new String[] {Long.class.getName()},
			new String[] {"listTypeEntryId"}, true);

		_finderPathCountByListTypeEntryId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByListTypeEntryId",
			new String[] {Long.class.getName()},
			new String[] {"listTypeEntryId"}, false);

		_finderPathWithPaginationFindByObjectStateFlowId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByObjectStateFlowId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"objectStateFlowId"}, true);

		_finderPathWithoutPaginationFindByObjectStateFlowId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByObjectStateFlowId", new String[] {Long.class.getName()},
			new String[] {"objectStateFlowId"}, true);

		_finderPathCountByObjectStateFlowId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByObjectStateFlowId", new String[] {Long.class.getName()},
			new String[] {"objectStateFlowId"}, false);

		_finderPathFetchByLTEI_OSFI = new FinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByLTEI_OSFI",
			new String[] {Long.class.getName(), Long.class.getName()},
			new String[] {"listTypeEntryId", "objectStateFlowId"}, true);

		_finderPathCountByLTEI_OSFI = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByLTEI_OSFI",
			new String[] {Long.class.getName(), Long.class.getName()},
			new String[] {"listTypeEntryId", "objectStateFlowId"}, false);

		_setObjectStateUtilPersistence(this);
	}

	@Deactivate
	public void deactivate() {
		_setObjectStateUtilPersistence(null);

		entityCache.removeCache(ObjectStateImpl.class.getName());
	}

	private void _setObjectStateUtilPersistence(
		ObjectStatePersistence objectStatePersistence) {

		try {
			Field field = ObjectStateUtil.class.getDeclaredField(
				"_persistence");

			field.setAccessible(true);

			field.set(null, objectStatePersistence);
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

	private static final String _SQL_SELECT_OBJECTSTATE =
		"SELECT objectState FROM ObjectState objectState";

	private static final String _SQL_SELECT_OBJECTSTATE_WHERE =
		"SELECT objectState FROM ObjectState objectState WHERE ";

	private static final String _SQL_COUNT_OBJECTSTATE =
		"SELECT COUNT(objectState) FROM ObjectState objectState";

	private static final String _SQL_COUNT_OBJECTSTATE_WHERE =
		"SELECT COUNT(objectState) FROM ObjectState objectState WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS = "objectState.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No ObjectState exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No ObjectState exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		ObjectStatePersistenceImpl.class);

	private static final Set<String> _badColumnNames = SetUtil.fromArray(
		new String[] {"uuid"});

	@Override
	protected FinderCache getFinderCache() {
		return finderCache;
	}

	@Reference
	private PortalUUID _portalUUID;

	@Reference
	private ObjectStateModelArgumentsResolver
		_objectStateModelArgumentsResolver;

}