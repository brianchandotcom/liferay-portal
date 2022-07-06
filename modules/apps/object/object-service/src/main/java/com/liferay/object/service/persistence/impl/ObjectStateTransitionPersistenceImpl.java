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

import com.liferay.object.exception.NoSuchObjectStateTransitionException;
import com.liferay.object.model.ObjectStateTransition;
import com.liferay.object.model.ObjectStateTransitionTable;
import com.liferay.object.model.impl.ObjectStateTransitionImpl;
import com.liferay.object.model.impl.ObjectStateTransitionModelImpl;
import com.liferay.object.service.persistence.ObjectStateTransitionPersistence;
import com.liferay.object.service.persistence.ObjectStateTransitionUtil;
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
 * The persistence implementation for the object state transition service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Marco Leo
 * @generated
 */
@Component(
	service = {ObjectStateTransitionPersistence.class, BasePersistence.class}
)
public class ObjectStateTransitionPersistenceImpl
	extends BasePersistenceImpl<ObjectStateTransition>
	implements ObjectStateTransitionPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>ObjectStateTransitionUtil</code> to access the object state transition persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		ObjectStateTransitionImpl.class.getName();

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
	 * Returns all the object state transitions where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching object state transitions
	 */
	@Override
	public List<ObjectStateTransition> findByUuid(String uuid) {
		return findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the object state transitions where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectStateTransitionModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of object state transitions
	 * @param end the upper bound of the range of object state transitions (not inclusive)
	 * @return the range of matching object state transitions
	 */
	@Override
	public List<ObjectStateTransition> findByUuid(
		String uuid, int start, int end) {

		return findByUuid(uuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the object state transitions where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectStateTransitionModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of object state transitions
	 * @param end the upper bound of the range of object state transitions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching object state transitions
	 */
	@Override
	public List<ObjectStateTransition> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<ObjectStateTransition> orderByComparator) {

		return findByUuid(uuid, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the object state transitions where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectStateTransitionModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of object state transitions
	 * @param end the upper bound of the range of object state transitions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching object state transitions
	 */
	@Override
	public List<ObjectStateTransition> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<ObjectStateTransition> orderByComparator,
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

		List<ObjectStateTransition> list = null;

		if (useFinderCache) {
			list = (List<ObjectStateTransition>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (ObjectStateTransition objectStateTransition : list) {
					if (!uuid.equals(objectStateTransition.getUuid())) {
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

			sb.append(_SQL_SELECT_OBJECTSTATETRANSITION_WHERE);

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
				sb.append(ObjectStateTransitionModelImpl.ORDER_BY_JPQL);
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

				list = (List<ObjectStateTransition>)QueryUtil.list(
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
	 * Returns the first object state transition in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object state transition
	 * @throws NoSuchObjectStateTransitionException if a matching object state transition could not be found
	 */
	@Override
	public ObjectStateTransition findByUuid_First(
			String uuid,
			OrderByComparator<ObjectStateTransition> orderByComparator)
		throws NoSuchObjectStateTransitionException {

		ObjectStateTransition objectStateTransition = fetchByUuid_First(
			uuid, orderByComparator);

		if (objectStateTransition != null) {
			return objectStateTransition;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append("}");

		throw new NoSuchObjectStateTransitionException(sb.toString());
	}

	/**
	 * Returns the first object state transition in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object state transition, or <code>null</code> if a matching object state transition could not be found
	 */
	@Override
	public ObjectStateTransition fetchByUuid_First(
		String uuid,
		OrderByComparator<ObjectStateTransition> orderByComparator) {

		List<ObjectStateTransition> list = findByUuid(
			uuid, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last object state transition in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object state transition
	 * @throws NoSuchObjectStateTransitionException if a matching object state transition could not be found
	 */
	@Override
	public ObjectStateTransition findByUuid_Last(
			String uuid,
			OrderByComparator<ObjectStateTransition> orderByComparator)
		throws NoSuchObjectStateTransitionException {

		ObjectStateTransition objectStateTransition = fetchByUuid_Last(
			uuid, orderByComparator);

		if (objectStateTransition != null) {
			return objectStateTransition;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append("}");

		throw new NoSuchObjectStateTransitionException(sb.toString());
	}

	/**
	 * Returns the last object state transition in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object state transition, or <code>null</code> if a matching object state transition could not be found
	 */
	@Override
	public ObjectStateTransition fetchByUuid_Last(
		String uuid,
		OrderByComparator<ObjectStateTransition> orderByComparator) {

		int count = countByUuid(uuid);

		if (count == 0) {
			return null;
		}

		List<ObjectStateTransition> list = findByUuid(
			uuid, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the object state transitions before and after the current object state transition in the ordered set where uuid = &#63;.
	 *
	 * @param objectStateTransitionId the primary key of the current object state transition
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next object state transition
	 * @throws NoSuchObjectStateTransitionException if a object state transition with the primary key could not be found
	 */
	@Override
	public ObjectStateTransition[] findByUuid_PrevAndNext(
			long objectStateTransitionId, String uuid,
			OrderByComparator<ObjectStateTransition> orderByComparator)
		throws NoSuchObjectStateTransitionException {

		uuid = Objects.toString(uuid, "");

		ObjectStateTransition objectStateTransition = findByPrimaryKey(
			objectStateTransitionId);

		Session session = null;

		try {
			session = openSession();

			ObjectStateTransition[] array = new ObjectStateTransitionImpl[3];

			array[0] = getByUuid_PrevAndNext(
				session, objectStateTransition, uuid, orderByComparator, true);

			array[1] = objectStateTransition;

			array[2] = getByUuid_PrevAndNext(
				session, objectStateTransition, uuid, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected ObjectStateTransition getByUuid_PrevAndNext(
		Session session, ObjectStateTransition objectStateTransition,
		String uuid, OrderByComparator<ObjectStateTransition> orderByComparator,
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

		sb.append(_SQL_SELECT_OBJECTSTATETRANSITION_WHERE);

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
			sb.append(ObjectStateTransitionModelImpl.ORDER_BY_JPQL);
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
						objectStateTransition)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<ObjectStateTransition> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the object state transitions where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	@Override
	public void removeByUuid(String uuid) {
		for (ObjectStateTransition objectStateTransition :
				findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(objectStateTransition);
		}
	}

	/**
	 * Returns the number of object state transitions where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching object state transitions
	 */
	@Override
	public int countByUuid(String uuid) {
		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = _finderPathCountByUuid;

		Object[] finderArgs = new Object[] {uuid};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_OBJECTSTATETRANSITION_WHERE);

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
		"objectStateTransition.uuid = ?";

	private static final String _FINDER_COLUMN_UUID_UUID_3 =
		"(objectStateTransition.uuid IS NULL OR objectStateTransition.uuid = '')";

	private FinderPath _finderPathWithPaginationFindByUuid_C;
	private FinderPath _finderPathWithoutPaginationFindByUuid_C;
	private FinderPath _finderPathCountByUuid_C;

	/**
	 * Returns all the object state transitions where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching object state transitions
	 */
	@Override
	public List<ObjectStateTransition> findByUuid_C(
		String uuid, long companyId) {

		return findByUuid_C(
			uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the object state transitions where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectStateTransitionModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of object state transitions
	 * @param end the upper bound of the range of object state transitions (not inclusive)
	 * @return the range of matching object state transitions
	 */
	@Override
	public List<ObjectStateTransition> findByUuid_C(
		String uuid, long companyId, int start, int end) {

		return findByUuid_C(uuid, companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the object state transitions where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectStateTransitionModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of object state transitions
	 * @param end the upper bound of the range of object state transitions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching object state transitions
	 */
	@Override
	public List<ObjectStateTransition> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<ObjectStateTransition> orderByComparator) {

		return findByUuid_C(
			uuid, companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the object state transitions where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectStateTransitionModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of object state transitions
	 * @param end the upper bound of the range of object state transitions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching object state transitions
	 */
	@Override
	public List<ObjectStateTransition> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<ObjectStateTransition> orderByComparator,
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

		List<ObjectStateTransition> list = null;

		if (useFinderCache) {
			list = (List<ObjectStateTransition>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (ObjectStateTransition objectStateTransition : list) {
					if (!uuid.equals(objectStateTransition.getUuid()) ||
						(companyId != objectStateTransition.getCompanyId())) {

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

			sb.append(_SQL_SELECT_OBJECTSTATETRANSITION_WHERE);

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
				sb.append(ObjectStateTransitionModelImpl.ORDER_BY_JPQL);
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

				list = (List<ObjectStateTransition>)QueryUtil.list(
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
	 * Returns the first object state transition in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object state transition
	 * @throws NoSuchObjectStateTransitionException if a matching object state transition could not be found
	 */
	@Override
	public ObjectStateTransition findByUuid_C_First(
			String uuid, long companyId,
			OrderByComparator<ObjectStateTransition> orderByComparator)
		throws NoSuchObjectStateTransitionException {

		ObjectStateTransition objectStateTransition = fetchByUuid_C_First(
			uuid, companyId, orderByComparator);

		if (objectStateTransition != null) {
			return objectStateTransition;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append(", companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchObjectStateTransitionException(sb.toString());
	}

	/**
	 * Returns the first object state transition in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object state transition, or <code>null</code> if a matching object state transition could not be found
	 */
	@Override
	public ObjectStateTransition fetchByUuid_C_First(
		String uuid, long companyId,
		OrderByComparator<ObjectStateTransition> orderByComparator) {

		List<ObjectStateTransition> list = findByUuid_C(
			uuid, companyId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last object state transition in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object state transition
	 * @throws NoSuchObjectStateTransitionException if a matching object state transition could not be found
	 */
	@Override
	public ObjectStateTransition findByUuid_C_Last(
			String uuid, long companyId,
			OrderByComparator<ObjectStateTransition> orderByComparator)
		throws NoSuchObjectStateTransitionException {

		ObjectStateTransition objectStateTransition = fetchByUuid_C_Last(
			uuid, companyId, orderByComparator);

		if (objectStateTransition != null) {
			return objectStateTransition;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append(", companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchObjectStateTransitionException(sb.toString());
	}

	/**
	 * Returns the last object state transition in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object state transition, or <code>null</code> if a matching object state transition could not be found
	 */
	@Override
	public ObjectStateTransition fetchByUuid_C_Last(
		String uuid, long companyId,
		OrderByComparator<ObjectStateTransition> orderByComparator) {

		int count = countByUuid_C(uuid, companyId);

		if (count == 0) {
			return null;
		}

		List<ObjectStateTransition> list = findByUuid_C(
			uuid, companyId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the object state transitions before and after the current object state transition in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param objectStateTransitionId the primary key of the current object state transition
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next object state transition
	 * @throws NoSuchObjectStateTransitionException if a object state transition with the primary key could not be found
	 */
	@Override
	public ObjectStateTransition[] findByUuid_C_PrevAndNext(
			long objectStateTransitionId, String uuid, long companyId,
			OrderByComparator<ObjectStateTransition> orderByComparator)
		throws NoSuchObjectStateTransitionException {

		uuid = Objects.toString(uuid, "");

		ObjectStateTransition objectStateTransition = findByPrimaryKey(
			objectStateTransitionId);

		Session session = null;

		try {
			session = openSession();

			ObjectStateTransition[] array = new ObjectStateTransitionImpl[3];

			array[0] = getByUuid_C_PrevAndNext(
				session, objectStateTransition, uuid, companyId,
				orderByComparator, true);

			array[1] = objectStateTransition;

			array[2] = getByUuid_C_PrevAndNext(
				session, objectStateTransition, uuid, companyId,
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

	protected ObjectStateTransition getByUuid_C_PrevAndNext(
		Session session, ObjectStateTransition objectStateTransition,
		String uuid, long companyId,
		OrderByComparator<ObjectStateTransition> orderByComparator,
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

		sb.append(_SQL_SELECT_OBJECTSTATETRANSITION_WHERE);

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
			sb.append(ObjectStateTransitionModelImpl.ORDER_BY_JPQL);
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
						objectStateTransition)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<ObjectStateTransition> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the object state transitions where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	@Override
	public void removeByUuid_C(String uuid, long companyId) {
		for (ObjectStateTransition objectStateTransition :
				findByUuid_C(
					uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(objectStateTransition);
		}
	}

	/**
	 * Returns the number of object state transitions where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching object state transitions
	 */
	@Override
	public int countByUuid_C(String uuid, long companyId) {
		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = _finderPathCountByUuid_C;

		Object[] finderArgs = new Object[] {uuid, companyId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_OBJECTSTATETRANSITION_WHERE);

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
		"objectStateTransition.uuid = ? AND ";

	private static final String _FINDER_COLUMN_UUID_C_UUID_3 =
		"(objectStateTransition.uuid IS NULL OR objectStateTransition.uuid = '') AND ";

	private static final String _FINDER_COLUMN_UUID_C_COMPANYID_2 =
		"objectStateTransition.companyId = ?";

	private FinderPath _finderPathWithPaginationFindByObjectStateFlowId;
	private FinderPath _finderPathWithoutPaginationFindByObjectStateFlowId;
	private FinderPath _finderPathCountByObjectStateFlowId;

	/**
	 * Returns all the object state transitions where objectStateFlowId = &#63;.
	 *
	 * @param objectStateFlowId the object state flow ID
	 * @return the matching object state transitions
	 */
	@Override
	public List<ObjectStateTransition> findByObjectStateFlowId(
		long objectStateFlowId) {

		return findByObjectStateFlowId(
			objectStateFlowId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the object state transitions where objectStateFlowId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectStateTransitionModelImpl</code>.
	 * </p>
	 *
	 * @param objectStateFlowId the object state flow ID
	 * @param start the lower bound of the range of object state transitions
	 * @param end the upper bound of the range of object state transitions (not inclusive)
	 * @return the range of matching object state transitions
	 */
	@Override
	public List<ObjectStateTransition> findByObjectStateFlowId(
		long objectStateFlowId, int start, int end) {

		return findByObjectStateFlowId(objectStateFlowId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the object state transitions where objectStateFlowId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectStateTransitionModelImpl</code>.
	 * </p>
	 *
	 * @param objectStateFlowId the object state flow ID
	 * @param start the lower bound of the range of object state transitions
	 * @param end the upper bound of the range of object state transitions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching object state transitions
	 */
	@Override
	public List<ObjectStateTransition> findByObjectStateFlowId(
		long objectStateFlowId, int start, int end,
		OrderByComparator<ObjectStateTransition> orderByComparator) {

		return findByObjectStateFlowId(
			objectStateFlowId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the object state transitions where objectStateFlowId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectStateTransitionModelImpl</code>.
	 * </p>
	 *
	 * @param objectStateFlowId the object state flow ID
	 * @param start the lower bound of the range of object state transitions
	 * @param end the upper bound of the range of object state transitions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching object state transitions
	 */
	@Override
	public List<ObjectStateTransition> findByObjectStateFlowId(
		long objectStateFlowId, int start, int end,
		OrderByComparator<ObjectStateTransition> orderByComparator,
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

		List<ObjectStateTransition> list = null;

		if (useFinderCache) {
			list = (List<ObjectStateTransition>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (ObjectStateTransition objectStateTransition : list) {
					if (objectStateFlowId !=
							objectStateTransition.getObjectStateFlowId()) {

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

			sb.append(_SQL_SELECT_OBJECTSTATETRANSITION_WHERE);

			sb.append(_FINDER_COLUMN_OBJECTSTATEFLOWID_OBJECTSTATEFLOWID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(ObjectStateTransitionModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(objectStateFlowId);

				list = (List<ObjectStateTransition>)QueryUtil.list(
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
	 * Returns the first object state transition in the ordered set where objectStateFlowId = &#63;.
	 *
	 * @param objectStateFlowId the object state flow ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object state transition
	 * @throws NoSuchObjectStateTransitionException if a matching object state transition could not be found
	 */
	@Override
	public ObjectStateTransition findByObjectStateFlowId_First(
			long objectStateFlowId,
			OrderByComparator<ObjectStateTransition> orderByComparator)
		throws NoSuchObjectStateTransitionException {

		ObjectStateTransition objectStateTransition =
			fetchByObjectStateFlowId_First(
				objectStateFlowId, orderByComparator);

		if (objectStateTransition != null) {
			return objectStateTransition;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("objectStateFlowId=");
		sb.append(objectStateFlowId);

		sb.append("}");

		throw new NoSuchObjectStateTransitionException(sb.toString());
	}

	/**
	 * Returns the first object state transition in the ordered set where objectStateFlowId = &#63;.
	 *
	 * @param objectStateFlowId the object state flow ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object state transition, or <code>null</code> if a matching object state transition could not be found
	 */
	@Override
	public ObjectStateTransition fetchByObjectStateFlowId_First(
		long objectStateFlowId,
		OrderByComparator<ObjectStateTransition> orderByComparator) {

		List<ObjectStateTransition> list = findByObjectStateFlowId(
			objectStateFlowId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last object state transition in the ordered set where objectStateFlowId = &#63;.
	 *
	 * @param objectStateFlowId the object state flow ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object state transition
	 * @throws NoSuchObjectStateTransitionException if a matching object state transition could not be found
	 */
	@Override
	public ObjectStateTransition findByObjectStateFlowId_Last(
			long objectStateFlowId,
			OrderByComparator<ObjectStateTransition> orderByComparator)
		throws NoSuchObjectStateTransitionException {

		ObjectStateTransition objectStateTransition =
			fetchByObjectStateFlowId_Last(objectStateFlowId, orderByComparator);

		if (objectStateTransition != null) {
			return objectStateTransition;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("objectStateFlowId=");
		sb.append(objectStateFlowId);

		sb.append("}");

		throw new NoSuchObjectStateTransitionException(sb.toString());
	}

	/**
	 * Returns the last object state transition in the ordered set where objectStateFlowId = &#63;.
	 *
	 * @param objectStateFlowId the object state flow ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object state transition, or <code>null</code> if a matching object state transition could not be found
	 */
	@Override
	public ObjectStateTransition fetchByObjectStateFlowId_Last(
		long objectStateFlowId,
		OrderByComparator<ObjectStateTransition> orderByComparator) {

		int count = countByObjectStateFlowId(objectStateFlowId);

		if (count == 0) {
			return null;
		}

		List<ObjectStateTransition> list = findByObjectStateFlowId(
			objectStateFlowId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the object state transitions before and after the current object state transition in the ordered set where objectStateFlowId = &#63;.
	 *
	 * @param objectStateTransitionId the primary key of the current object state transition
	 * @param objectStateFlowId the object state flow ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next object state transition
	 * @throws NoSuchObjectStateTransitionException if a object state transition with the primary key could not be found
	 */
	@Override
	public ObjectStateTransition[] findByObjectStateFlowId_PrevAndNext(
			long objectStateTransitionId, long objectStateFlowId,
			OrderByComparator<ObjectStateTransition> orderByComparator)
		throws NoSuchObjectStateTransitionException {

		ObjectStateTransition objectStateTransition = findByPrimaryKey(
			objectStateTransitionId);

		Session session = null;

		try {
			session = openSession();

			ObjectStateTransition[] array = new ObjectStateTransitionImpl[3];

			array[0] = getByObjectStateFlowId_PrevAndNext(
				session, objectStateTransition, objectStateFlowId,
				orderByComparator, true);

			array[1] = objectStateTransition;

			array[2] = getByObjectStateFlowId_PrevAndNext(
				session, objectStateTransition, objectStateFlowId,
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

	protected ObjectStateTransition getByObjectStateFlowId_PrevAndNext(
		Session session, ObjectStateTransition objectStateTransition,
		long objectStateFlowId,
		OrderByComparator<ObjectStateTransition> orderByComparator,
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

		sb.append(_SQL_SELECT_OBJECTSTATETRANSITION_WHERE);

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
			sb.append(ObjectStateTransitionModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(objectStateFlowId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						objectStateTransition)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<ObjectStateTransition> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the object state transitions where objectStateFlowId = &#63; from the database.
	 *
	 * @param objectStateFlowId the object state flow ID
	 */
	@Override
	public void removeByObjectStateFlowId(long objectStateFlowId) {
		for (ObjectStateTransition objectStateTransition :
				findByObjectStateFlowId(
					objectStateFlowId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(objectStateTransition);
		}
	}

	/**
	 * Returns the number of object state transitions where objectStateFlowId = &#63;.
	 *
	 * @param objectStateFlowId the object state flow ID
	 * @return the number of matching object state transitions
	 */
	@Override
	public int countByObjectStateFlowId(long objectStateFlowId) {
		FinderPath finderPath = _finderPathCountByObjectStateFlowId;

		Object[] finderArgs = new Object[] {objectStateFlowId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_OBJECTSTATETRANSITION_WHERE);

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
			"objectStateTransition.objectStateFlowId = ?";

	private FinderPath _finderPathWithPaginationFindBySourceObjectStateId;
	private FinderPath _finderPathWithoutPaginationFindBySourceObjectStateId;
	private FinderPath _finderPathCountBySourceObjectStateId;

	/**
	 * Returns all the object state transitions where sourceObjectStateId = &#63;.
	 *
	 * @param sourceObjectStateId the source object state ID
	 * @return the matching object state transitions
	 */
	@Override
	public List<ObjectStateTransition> findBySourceObjectStateId(
		long sourceObjectStateId) {

		return findBySourceObjectStateId(
			sourceObjectStateId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the object state transitions where sourceObjectStateId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectStateTransitionModelImpl</code>.
	 * </p>
	 *
	 * @param sourceObjectStateId the source object state ID
	 * @param start the lower bound of the range of object state transitions
	 * @param end the upper bound of the range of object state transitions (not inclusive)
	 * @return the range of matching object state transitions
	 */
	@Override
	public List<ObjectStateTransition> findBySourceObjectStateId(
		long sourceObjectStateId, int start, int end) {

		return findBySourceObjectStateId(sourceObjectStateId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the object state transitions where sourceObjectStateId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectStateTransitionModelImpl</code>.
	 * </p>
	 *
	 * @param sourceObjectStateId the source object state ID
	 * @param start the lower bound of the range of object state transitions
	 * @param end the upper bound of the range of object state transitions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching object state transitions
	 */
	@Override
	public List<ObjectStateTransition> findBySourceObjectStateId(
		long sourceObjectStateId, int start, int end,
		OrderByComparator<ObjectStateTransition> orderByComparator) {

		return findBySourceObjectStateId(
			sourceObjectStateId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the object state transitions where sourceObjectStateId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectStateTransitionModelImpl</code>.
	 * </p>
	 *
	 * @param sourceObjectStateId the source object state ID
	 * @param start the lower bound of the range of object state transitions
	 * @param end the upper bound of the range of object state transitions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching object state transitions
	 */
	@Override
	public List<ObjectStateTransition> findBySourceObjectStateId(
		long sourceObjectStateId, int start, int end,
		OrderByComparator<ObjectStateTransition> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath =
					_finderPathWithoutPaginationFindBySourceObjectStateId;
				finderArgs = new Object[] {sourceObjectStateId};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindBySourceObjectStateId;
			finderArgs = new Object[] {
				sourceObjectStateId, start, end, orderByComparator
			};
		}

		List<ObjectStateTransition> list = null;

		if (useFinderCache) {
			list = (List<ObjectStateTransition>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (ObjectStateTransition objectStateTransition : list) {
					if (sourceObjectStateId !=
							objectStateTransition.getSourceObjectStateId()) {

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

			sb.append(_SQL_SELECT_OBJECTSTATETRANSITION_WHERE);

			sb.append(_FINDER_COLUMN_SOURCEOBJECTSTATEID_SOURCEOBJECTSTATEID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(ObjectStateTransitionModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(sourceObjectStateId);

				list = (List<ObjectStateTransition>)QueryUtil.list(
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
	 * Returns the first object state transition in the ordered set where sourceObjectStateId = &#63;.
	 *
	 * @param sourceObjectStateId the source object state ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object state transition
	 * @throws NoSuchObjectStateTransitionException if a matching object state transition could not be found
	 */
	@Override
	public ObjectStateTransition findBySourceObjectStateId_First(
			long sourceObjectStateId,
			OrderByComparator<ObjectStateTransition> orderByComparator)
		throws NoSuchObjectStateTransitionException {

		ObjectStateTransition objectStateTransition =
			fetchBySourceObjectStateId_First(
				sourceObjectStateId, orderByComparator);

		if (objectStateTransition != null) {
			return objectStateTransition;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("sourceObjectStateId=");
		sb.append(sourceObjectStateId);

		sb.append("}");

		throw new NoSuchObjectStateTransitionException(sb.toString());
	}

	/**
	 * Returns the first object state transition in the ordered set where sourceObjectStateId = &#63;.
	 *
	 * @param sourceObjectStateId the source object state ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object state transition, or <code>null</code> if a matching object state transition could not be found
	 */
	@Override
	public ObjectStateTransition fetchBySourceObjectStateId_First(
		long sourceObjectStateId,
		OrderByComparator<ObjectStateTransition> orderByComparator) {

		List<ObjectStateTransition> list = findBySourceObjectStateId(
			sourceObjectStateId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last object state transition in the ordered set where sourceObjectStateId = &#63;.
	 *
	 * @param sourceObjectStateId the source object state ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object state transition
	 * @throws NoSuchObjectStateTransitionException if a matching object state transition could not be found
	 */
	@Override
	public ObjectStateTransition findBySourceObjectStateId_Last(
			long sourceObjectStateId,
			OrderByComparator<ObjectStateTransition> orderByComparator)
		throws NoSuchObjectStateTransitionException {

		ObjectStateTransition objectStateTransition =
			fetchBySourceObjectStateId_Last(
				sourceObjectStateId, orderByComparator);

		if (objectStateTransition != null) {
			return objectStateTransition;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("sourceObjectStateId=");
		sb.append(sourceObjectStateId);

		sb.append("}");

		throw new NoSuchObjectStateTransitionException(sb.toString());
	}

	/**
	 * Returns the last object state transition in the ordered set where sourceObjectStateId = &#63;.
	 *
	 * @param sourceObjectStateId the source object state ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object state transition, or <code>null</code> if a matching object state transition could not be found
	 */
	@Override
	public ObjectStateTransition fetchBySourceObjectStateId_Last(
		long sourceObjectStateId,
		OrderByComparator<ObjectStateTransition> orderByComparator) {

		int count = countBySourceObjectStateId(sourceObjectStateId);

		if (count == 0) {
			return null;
		}

		List<ObjectStateTransition> list = findBySourceObjectStateId(
			sourceObjectStateId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the object state transitions before and after the current object state transition in the ordered set where sourceObjectStateId = &#63;.
	 *
	 * @param objectStateTransitionId the primary key of the current object state transition
	 * @param sourceObjectStateId the source object state ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next object state transition
	 * @throws NoSuchObjectStateTransitionException if a object state transition with the primary key could not be found
	 */
	@Override
	public ObjectStateTransition[] findBySourceObjectStateId_PrevAndNext(
			long objectStateTransitionId, long sourceObjectStateId,
			OrderByComparator<ObjectStateTransition> orderByComparator)
		throws NoSuchObjectStateTransitionException {

		ObjectStateTransition objectStateTransition = findByPrimaryKey(
			objectStateTransitionId);

		Session session = null;

		try {
			session = openSession();

			ObjectStateTransition[] array = new ObjectStateTransitionImpl[3];

			array[0] = getBySourceObjectStateId_PrevAndNext(
				session, objectStateTransition, sourceObjectStateId,
				orderByComparator, true);

			array[1] = objectStateTransition;

			array[2] = getBySourceObjectStateId_PrevAndNext(
				session, objectStateTransition, sourceObjectStateId,
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

	protected ObjectStateTransition getBySourceObjectStateId_PrevAndNext(
		Session session, ObjectStateTransition objectStateTransition,
		long sourceObjectStateId,
		OrderByComparator<ObjectStateTransition> orderByComparator,
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

		sb.append(_SQL_SELECT_OBJECTSTATETRANSITION_WHERE);

		sb.append(_FINDER_COLUMN_SOURCEOBJECTSTATEID_SOURCEOBJECTSTATEID_2);

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
			sb.append(ObjectStateTransitionModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(sourceObjectStateId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						objectStateTransition)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<ObjectStateTransition> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the object state transitions where sourceObjectStateId = &#63; from the database.
	 *
	 * @param sourceObjectStateId the source object state ID
	 */
	@Override
	public void removeBySourceObjectStateId(long sourceObjectStateId) {
		for (ObjectStateTransition objectStateTransition :
				findBySourceObjectStateId(
					sourceObjectStateId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(objectStateTransition);
		}
	}

	/**
	 * Returns the number of object state transitions where sourceObjectStateId = &#63;.
	 *
	 * @param sourceObjectStateId the source object state ID
	 * @return the number of matching object state transitions
	 */
	@Override
	public int countBySourceObjectStateId(long sourceObjectStateId) {
		FinderPath finderPath = _finderPathCountBySourceObjectStateId;

		Object[] finderArgs = new Object[] {sourceObjectStateId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_OBJECTSTATETRANSITION_WHERE);

			sb.append(_FINDER_COLUMN_SOURCEOBJECTSTATEID_SOURCEOBJECTSTATEID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(sourceObjectStateId);

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
		_FINDER_COLUMN_SOURCEOBJECTSTATEID_SOURCEOBJECTSTATEID_2 =
			"objectStateTransition.sourceObjectStateId = ?";

	private FinderPath _finderPathWithPaginationFindByTargetObjectStateId;
	private FinderPath _finderPathWithoutPaginationFindByTargetObjectStateId;
	private FinderPath _finderPathCountByTargetObjectStateId;

	/**
	 * Returns all the object state transitions where targetObjectStateId = &#63;.
	 *
	 * @param targetObjectStateId the target object state ID
	 * @return the matching object state transitions
	 */
	@Override
	public List<ObjectStateTransition> findByTargetObjectStateId(
		long targetObjectStateId) {

		return findByTargetObjectStateId(
			targetObjectStateId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the object state transitions where targetObjectStateId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectStateTransitionModelImpl</code>.
	 * </p>
	 *
	 * @param targetObjectStateId the target object state ID
	 * @param start the lower bound of the range of object state transitions
	 * @param end the upper bound of the range of object state transitions (not inclusive)
	 * @return the range of matching object state transitions
	 */
	@Override
	public List<ObjectStateTransition> findByTargetObjectStateId(
		long targetObjectStateId, int start, int end) {

		return findByTargetObjectStateId(targetObjectStateId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the object state transitions where targetObjectStateId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectStateTransitionModelImpl</code>.
	 * </p>
	 *
	 * @param targetObjectStateId the target object state ID
	 * @param start the lower bound of the range of object state transitions
	 * @param end the upper bound of the range of object state transitions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching object state transitions
	 */
	@Override
	public List<ObjectStateTransition> findByTargetObjectStateId(
		long targetObjectStateId, int start, int end,
		OrderByComparator<ObjectStateTransition> orderByComparator) {

		return findByTargetObjectStateId(
			targetObjectStateId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the object state transitions where targetObjectStateId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectStateTransitionModelImpl</code>.
	 * </p>
	 *
	 * @param targetObjectStateId the target object state ID
	 * @param start the lower bound of the range of object state transitions
	 * @param end the upper bound of the range of object state transitions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching object state transitions
	 */
	@Override
	public List<ObjectStateTransition> findByTargetObjectStateId(
		long targetObjectStateId, int start, int end,
		OrderByComparator<ObjectStateTransition> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath =
					_finderPathWithoutPaginationFindByTargetObjectStateId;
				finderArgs = new Object[] {targetObjectStateId};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByTargetObjectStateId;
			finderArgs = new Object[] {
				targetObjectStateId, start, end, orderByComparator
			};
		}

		List<ObjectStateTransition> list = null;

		if (useFinderCache) {
			list = (List<ObjectStateTransition>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (ObjectStateTransition objectStateTransition : list) {
					if (targetObjectStateId !=
							objectStateTransition.getTargetObjectStateId()) {

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

			sb.append(_SQL_SELECT_OBJECTSTATETRANSITION_WHERE);

			sb.append(_FINDER_COLUMN_TARGETOBJECTSTATEID_TARGETOBJECTSTATEID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(ObjectStateTransitionModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(targetObjectStateId);

				list = (List<ObjectStateTransition>)QueryUtil.list(
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
	 * Returns the first object state transition in the ordered set where targetObjectStateId = &#63;.
	 *
	 * @param targetObjectStateId the target object state ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object state transition
	 * @throws NoSuchObjectStateTransitionException if a matching object state transition could not be found
	 */
	@Override
	public ObjectStateTransition findByTargetObjectStateId_First(
			long targetObjectStateId,
			OrderByComparator<ObjectStateTransition> orderByComparator)
		throws NoSuchObjectStateTransitionException {

		ObjectStateTransition objectStateTransition =
			fetchByTargetObjectStateId_First(
				targetObjectStateId, orderByComparator);

		if (objectStateTransition != null) {
			return objectStateTransition;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("targetObjectStateId=");
		sb.append(targetObjectStateId);

		sb.append("}");

		throw new NoSuchObjectStateTransitionException(sb.toString());
	}

	/**
	 * Returns the first object state transition in the ordered set where targetObjectStateId = &#63;.
	 *
	 * @param targetObjectStateId the target object state ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object state transition, or <code>null</code> if a matching object state transition could not be found
	 */
	@Override
	public ObjectStateTransition fetchByTargetObjectStateId_First(
		long targetObjectStateId,
		OrderByComparator<ObjectStateTransition> orderByComparator) {

		List<ObjectStateTransition> list = findByTargetObjectStateId(
			targetObjectStateId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last object state transition in the ordered set where targetObjectStateId = &#63;.
	 *
	 * @param targetObjectStateId the target object state ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object state transition
	 * @throws NoSuchObjectStateTransitionException if a matching object state transition could not be found
	 */
	@Override
	public ObjectStateTransition findByTargetObjectStateId_Last(
			long targetObjectStateId,
			OrderByComparator<ObjectStateTransition> orderByComparator)
		throws NoSuchObjectStateTransitionException {

		ObjectStateTransition objectStateTransition =
			fetchByTargetObjectStateId_Last(
				targetObjectStateId, orderByComparator);

		if (objectStateTransition != null) {
			return objectStateTransition;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("targetObjectStateId=");
		sb.append(targetObjectStateId);

		sb.append("}");

		throw new NoSuchObjectStateTransitionException(sb.toString());
	}

	/**
	 * Returns the last object state transition in the ordered set where targetObjectStateId = &#63;.
	 *
	 * @param targetObjectStateId the target object state ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object state transition, or <code>null</code> if a matching object state transition could not be found
	 */
	@Override
	public ObjectStateTransition fetchByTargetObjectStateId_Last(
		long targetObjectStateId,
		OrderByComparator<ObjectStateTransition> orderByComparator) {

		int count = countByTargetObjectStateId(targetObjectStateId);

		if (count == 0) {
			return null;
		}

		List<ObjectStateTransition> list = findByTargetObjectStateId(
			targetObjectStateId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the object state transitions before and after the current object state transition in the ordered set where targetObjectStateId = &#63;.
	 *
	 * @param objectStateTransitionId the primary key of the current object state transition
	 * @param targetObjectStateId the target object state ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next object state transition
	 * @throws NoSuchObjectStateTransitionException if a object state transition with the primary key could not be found
	 */
	@Override
	public ObjectStateTransition[] findByTargetObjectStateId_PrevAndNext(
			long objectStateTransitionId, long targetObjectStateId,
			OrderByComparator<ObjectStateTransition> orderByComparator)
		throws NoSuchObjectStateTransitionException {

		ObjectStateTransition objectStateTransition = findByPrimaryKey(
			objectStateTransitionId);

		Session session = null;

		try {
			session = openSession();

			ObjectStateTransition[] array = new ObjectStateTransitionImpl[3];

			array[0] = getByTargetObjectStateId_PrevAndNext(
				session, objectStateTransition, targetObjectStateId,
				orderByComparator, true);

			array[1] = objectStateTransition;

			array[2] = getByTargetObjectStateId_PrevAndNext(
				session, objectStateTransition, targetObjectStateId,
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

	protected ObjectStateTransition getByTargetObjectStateId_PrevAndNext(
		Session session, ObjectStateTransition objectStateTransition,
		long targetObjectStateId,
		OrderByComparator<ObjectStateTransition> orderByComparator,
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

		sb.append(_SQL_SELECT_OBJECTSTATETRANSITION_WHERE);

		sb.append(_FINDER_COLUMN_TARGETOBJECTSTATEID_TARGETOBJECTSTATEID_2);

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
			sb.append(ObjectStateTransitionModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(targetObjectStateId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						objectStateTransition)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<ObjectStateTransition> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the object state transitions where targetObjectStateId = &#63; from the database.
	 *
	 * @param targetObjectStateId the target object state ID
	 */
	@Override
	public void removeByTargetObjectStateId(long targetObjectStateId) {
		for (ObjectStateTransition objectStateTransition :
				findByTargetObjectStateId(
					targetObjectStateId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(objectStateTransition);
		}
	}

	/**
	 * Returns the number of object state transitions where targetObjectStateId = &#63;.
	 *
	 * @param targetObjectStateId the target object state ID
	 * @return the number of matching object state transitions
	 */
	@Override
	public int countByTargetObjectStateId(long targetObjectStateId) {
		FinderPath finderPath = _finderPathCountByTargetObjectStateId;

		Object[] finderArgs = new Object[] {targetObjectStateId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_OBJECTSTATETRANSITION_WHERE);

			sb.append(_FINDER_COLUMN_TARGETOBJECTSTATEID_TARGETOBJECTSTATEID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(targetObjectStateId);

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
		_FINDER_COLUMN_TARGETOBJECTSTATEID_TARGETOBJECTSTATEID_2 =
			"objectStateTransition.targetObjectStateId = ?";

	public ObjectStateTransitionPersistenceImpl() {
		Map<String, String> dbColumnNames = new HashMap<String, String>();

		dbColumnNames.put("uuid", "uuid_");

		setDBColumnNames(dbColumnNames);

		setModelClass(ObjectStateTransition.class);

		setModelImplClass(ObjectStateTransitionImpl.class);
		setModelPKClass(long.class);

		setTable(ObjectStateTransitionTable.INSTANCE);
	}

	/**
	 * Caches the object state transition in the entity cache if it is enabled.
	 *
	 * @param objectStateTransition the object state transition
	 */
	@Override
	public void cacheResult(ObjectStateTransition objectStateTransition) {
		entityCache.putResult(
			ObjectStateTransitionImpl.class,
			objectStateTransition.getPrimaryKey(), objectStateTransition);
	}

	private int _valueObjectFinderCacheListThreshold;

	/**
	 * Caches the object state transitions in the entity cache if it is enabled.
	 *
	 * @param objectStateTransitions the object state transitions
	 */
	@Override
	public void cacheResult(
		List<ObjectStateTransition> objectStateTransitions) {

		if ((_valueObjectFinderCacheListThreshold == 0) ||
			((_valueObjectFinderCacheListThreshold > 0) &&
			 (objectStateTransitions.size() >
				 _valueObjectFinderCacheListThreshold))) {

			return;
		}

		for (ObjectStateTransition objectStateTransition :
				objectStateTransitions) {

			if (entityCache.getResult(
					ObjectStateTransitionImpl.class,
					objectStateTransition.getPrimaryKey()) == null) {

				cacheResult(objectStateTransition);
			}
		}
	}

	/**
	 * Clears the cache for all object state transitions.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(ObjectStateTransitionImpl.class);

		finderCache.clearCache(ObjectStateTransitionImpl.class);
	}

	/**
	 * Clears the cache for the object state transition.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(ObjectStateTransition objectStateTransition) {
		entityCache.removeResult(
			ObjectStateTransitionImpl.class, objectStateTransition);
	}

	@Override
	public void clearCache(List<ObjectStateTransition> objectStateTransitions) {
		for (ObjectStateTransition objectStateTransition :
				objectStateTransitions) {

			entityCache.removeResult(
				ObjectStateTransitionImpl.class, objectStateTransition);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(ObjectStateTransitionImpl.class);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(
				ObjectStateTransitionImpl.class, primaryKey);
		}
	}

	/**
	 * Creates a new object state transition with the primary key. Does not add the object state transition to the database.
	 *
	 * @param objectStateTransitionId the primary key for the new object state transition
	 * @return the new object state transition
	 */
	@Override
	public ObjectStateTransition create(long objectStateTransitionId) {
		ObjectStateTransition objectStateTransition =
			new ObjectStateTransitionImpl();

		objectStateTransition.setNew(true);
		objectStateTransition.setPrimaryKey(objectStateTransitionId);

		String uuid = _portalUUID.generate();

		objectStateTransition.setUuid(uuid);

		objectStateTransition.setCompanyId(CompanyThreadLocal.getCompanyId());

		return objectStateTransition;
	}

	/**
	 * Removes the object state transition with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param objectStateTransitionId the primary key of the object state transition
	 * @return the object state transition that was removed
	 * @throws NoSuchObjectStateTransitionException if a object state transition with the primary key could not be found
	 */
	@Override
	public ObjectStateTransition remove(long objectStateTransitionId)
		throws NoSuchObjectStateTransitionException {

		return remove((Serializable)objectStateTransitionId);
	}

	/**
	 * Removes the object state transition with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the object state transition
	 * @return the object state transition that was removed
	 * @throws NoSuchObjectStateTransitionException if a object state transition with the primary key could not be found
	 */
	@Override
	public ObjectStateTransition remove(Serializable primaryKey)
		throws NoSuchObjectStateTransitionException {

		Session session = null;

		try {
			session = openSession();

			ObjectStateTransition objectStateTransition =
				(ObjectStateTransition)session.get(
					ObjectStateTransitionImpl.class, primaryKey);

			if (objectStateTransition == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchObjectStateTransitionException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(objectStateTransition);
		}
		catch (NoSuchObjectStateTransitionException noSuchEntityException) {
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
	protected ObjectStateTransition removeImpl(
		ObjectStateTransition objectStateTransition) {

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(objectStateTransition)) {
				objectStateTransition = (ObjectStateTransition)session.get(
					ObjectStateTransitionImpl.class,
					objectStateTransition.getPrimaryKeyObj());
			}

			if (objectStateTransition != null) {
				session.delete(objectStateTransition);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (objectStateTransition != null) {
			clearCache(objectStateTransition);
		}

		return objectStateTransition;
	}

	@Override
	public ObjectStateTransition updateImpl(
		ObjectStateTransition objectStateTransition) {

		boolean isNew = objectStateTransition.isNew();

		if (!(objectStateTransition instanceof
				ObjectStateTransitionModelImpl)) {

			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(objectStateTransition.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					objectStateTransition);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in objectStateTransition proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom ObjectStateTransition implementation " +
					objectStateTransition.getClass());
		}

		ObjectStateTransitionModelImpl objectStateTransitionModelImpl =
			(ObjectStateTransitionModelImpl)objectStateTransition;

		if (Validator.isNull(objectStateTransition.getUuid())) {
			String uuid = _portalUUID.generate();

			objectStateTransition.setUuid(uuid);
		}

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Date date = new Date();

		if (isNew && (objectStateTransition.getCreateDate() == null)) {
			if (serviceContext == null) {
				objectStateTransition.setCreateDate(date);
			}
			else {
				objectStateTransition.setCreateDate(
					serviceContext.getCreateDate(date));
			}
		}

		if (!objectStateTransitionModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				objectStateTransition.setModifiedDate(date);
			}
			else {
				objectStateTransition.setModifiedDate(
					serviceContext.getModifiedDate(date));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (isNew) {
				session.save(objectStateTransition);
			}
			else {
				objectStateTransition = (ObjectStateTransition)session.merge(
					objectStateTransition);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		entityCache.putResult(
			ObjectStateTransitionImpl.class, objectStateTransitionModelImpl,
			false, true);

		if (isNew) {
			objectStateTransition.setNew(false);
		}

		objectStateTransition.resetOriginalValues();

		return objectStateTransition;
	}

	/**
	 * Returns the object state transition with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the object state transition
	 * @return the object state transition
	 * @throws NoSuchObjectStateTransitionException if a object state transition with the primary key could not be found
	 */
	@Override
	public ObjectStateTransition findByPrimaryKey(Serializable primaryKey)
		throws NoSuchObjectStateTransitionException {

		ObjectStateTransition objectStateTransition = fetchByPrimaryKey(
			primaryKey);

		if (objectStateTransition == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchObjectStateTransitionException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return objectStateTransition;
	}

	/**
	 * Returns the object state transition with the primary key or throws a <code>NoSuchObjectStateTransitionException</code> if it could not be found.
	 *
	 * @param objectStateTransitionId the primary key of the object state transition
	 * @return the object state transition
	 * @throws NoSuchObjectStateTransitionException if a object state transition with the primary key could not be found
	 */
	@Override
	public ObjectStateTransition findByPrimaryKey(long objectStateTransitionId)
		throws NoSuchObjectStateTransitionException {

		return findByPrimaryKey((Serializable)objectStateTransitionId);
	}

	/**
	 * Returns the object state transition with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param objectStateTransitionId the primary key of the object state transition
	 * @return the object state transition, or <code>null</code> if a object state transition with the primary key could not be found
	 */
	@Override
	public ObjectStateTransition fetchByPrimaryKey(
		long objectStateTransitionId) {

		return fetchByPrimaryKey((Serializable)objectStateTransitionId);
	}

	/**
	 * Returns all the object state transitions.
	 *
	 * @return the object state transitions
	 */
	@Override
	public List<ObjectStateTransition> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the object state transitions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectStateTransitionModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of object state transitions
	 * @param end the upper bound of the range of object state transitions (not inclusive)
	 * @return the range of object state transitions
	 */
	@Override
	public List<ObjectStateTransition> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the object state transitions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectStateTransitionModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of object state transitions
	 * @param end the upper bound of the range of object state transitions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of object state transitions
	 */
	@Override
	public List<ObjectStateTransition> findAll(
		int start, int end,
		OrderByComparator<ObjectStateTransition> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the object state transitions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectStateTransitionModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of object state transitions
	 * @param end the upper bound of the range of object state transitions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of object state transitions
	 */
	@Override
	public List<ObjectStateTransition> findAll(
		int start, int end,
		OrderByComparator<ObjectStateTransition> orderByComparator,
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

		List<ObjectStateTransition> list = null;

		if (useFinderCache) {
			list = (List<ObjectStateTransition>)finderCache.getResult(
				finderPath, finderArgs);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_OBJECTSTATETRANSITION);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_OBJECTSTATETRANSITION;

				sql = sql.concat(ObjectStateTransitionModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<ObjectStateTransition>)QueryUtil.list(
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
	 * Removes all the object state transitions from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (ObjectStateTransition objectStateTransition : findAll()) {
			remove(objectStateTransition);
		}
	}

	/**
	 * Returns the number of object state transitions.
	 *
	 * @return the number of object state transitions
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
					_SQL_COUNT_OBJECTSTATETRANSITION);

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
		return "objectStateTransitionId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_OBJECTSTATETRANSITION;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return ObjectStateTransitionModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the object state transition persistence.
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

		_finderPathWithPaginationFindBySourceObjectStateId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findBySourceObjectStateId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"sourceObjectStateId"}, true);

		_finderPathWithoutPaginationFindBySourceObjectStateId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findBySourceObjectStateId", new String[] {Long.class.getName()},
			new String[] {"sourceObjectStateId"}, true);

		_finderPathCountBySourceObjectStateId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countBySourceObjectStateId", new String[] {Long.class.getName()},
			new String[] {"sourceObjectStateId"}, false);

		_finderPathWithPaginationFindByTargetObjectStateId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByTargetObjectStateId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"targetObjectStateId"}, true);

		_finderPathWithoutPaginationFindByTargetObjectStateId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByTargetObjectStateId", new String[] {Long.class.getName()},
			new String[] {"targetObjectStateId"}, true);

		_finderPathCountByTargetObjectStateId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByTargetObjectStateId", new String[] {Long.class.getName()},
			new String[] {"targetObjectStateId"}, false);

		_setObjectStateTransitionUtilPersistence(this);
	}

	@Deactivate
	public void deactivate() {
		_setObjectStateTransitionUtilPersistence(null);

		entityCache.removeCache(ObjectStateTransitionImpl.class.getName());
	}

	private void _setObjectStateTransitionUtilPersistence(
		ObjectStateTransitionPersistence objectStateTransitionPersistence) {

		try {
			Field field = ObjectStateTransitionUtil.class.getDeclaredField(
				"_persistence");

			field.setAccessible(true);

			field.set(null, objectStateTransitionPersistence);
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

	private static final String _SQL_SELECT_OBJECTSTATETRANSITION =
		"SELECT objectStateTransition FROM ObjectStateTransition objectStateTransition";

	private static final String _SQL_SELECT_OBJECTSTATETRANSITION_WHERE =
		"SELECT objectStateTransition FROM ObjectStateTransition objectStateTransition WHERE ";

	private static final String _SQL_COUNT_OBJECTSTATETRANSITION =
		"SELECT COUNT(objectStateTransition) FROM ObjectStateTransition objectStateTransition";

	private static final String _SQL_COUNT_OBJECTSTATETRANSITION_WHERE =
		"SELECT COUNT(objectStateTransition) FROM ObjectStateTransition objectStateTransition WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS =
		"objectStateTransition.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No ObjectStateTransition exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No ObjectStateTransition exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		ObjectStateTransitionPersistenceImpl.class);

	private static final Set<String> _badColumnNames = SetUtil.fromArray(
		new String[] {"uuid"});

	@Override
	protected FinderCache getFinderCache() {
		return finderCache;
	}

	@Reference
	private PortalUUID _portalUUID;

	@Reference
	private ObjectStateTransitionModelArgumentsResolver
		_objectStateTransitionModelArgumentsResolver;

}