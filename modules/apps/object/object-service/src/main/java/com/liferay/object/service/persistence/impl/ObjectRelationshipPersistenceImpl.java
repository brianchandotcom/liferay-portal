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

import com.liferay.object.exception.NoSuchObjectRelationshipException;
import com.liferay.object.model.ObjectRelationship;
import com.liferay.object.model.ObjectRelationshipTable;
import com.liferay.object.model.impl.ObjectRelationshipImpl;
import com.liferay.object.model.impl.ObjectRelationshipModelImpl;
import com.liferay.object.service.persistence.ObjectRelationshipPersistence;
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
 * The persistence implementation for the object relationship service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Marco Leo
 * @generated
 */
@Component(
	service = {ObjectRelationshipPersistence.class, BasePersistence.class}
)
public class ObjectRelationshipPersistenceImpl
	extends BasePersistenceImpl<ObjectRelationship>
	implements ObjectRelationshipPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>ObjectRelationshipUtil</code> to access the object relationship persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		ObjectRelationshipImpl.class.getName();

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
	 * Returns all the object relationships where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching object relationships
	 */
	@Override
	public List<ObjectRelationship> findByUuid(String uuid) {
		return findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the object relationships where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectRelationshipModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of object relationships
	 * @param end the upper bound of the range of object relationships (not inclusive)
	 * @return the range of matching object relationships
	 */
	@Override
	public List<ObjectRelationship> findByUuid(
		String uuid, int start, int end) {

		return findByUuid(uuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the object relationships where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectRelationshipModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of object relationships
	 * @param end the upper bound of the range of object relationships (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching object relationships
	 */
	@Override
	public List<ObjectRelationship> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<ObjectRelationship> orderByComparator) {

		return findByUuid(uuid, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the object relationships where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectRelationshipModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of object relationships
	 * @param end the upper bound of the range of object relationships (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching object relationships
	 */
	@Override
	public List<ObjectRelationship> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<ObjectRelationship> orderByComparator,
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

		List<ObjectRelationship> list = null;

		if (useFinderCache) {
			list = (List<ObjectRelationship>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (ObjectRelationship objectRelationship : list) {
					if (!uuid.equals(objectRelationship.getUuid())) {
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

			sb.append(_SQL_SELECT_OBJECTRELATIONSHIP_WHERE);

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
				sb.append(ObjectRelationshipModelImpl.ORDER_BY_JPQL);
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

				list = (List<ObjectRelationship>)QueryUtil.list(
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
	 * Returns the first object relationship in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object relationship
	 * @throws NoSuchObjectRelationshipException if a matching object relationship could not be found
	 */
	@Override
	public ObjectRelationship findByUuid_First(
			String uuid,
			OrderByComparator<ObjectRelationship> orderByComparator)
		throws NoSuchObjectRelationshipException {

		ObjectRelationship objectRelationship = fetchByUuid_First(
			uuid, orderByComparator);

		if (objectRelationship != null) {
			return objectRelationship;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append("}");

		throw new NoSuchObjectRelationshipException(sb.toString());
	}

	/**
	 * Returns the first object relationship in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object relationship, or <code>null</code> if a matching object relationship could not be found
	 */
	@Override
	public ObjectRelationship fetchByUuid_First(
		String uuid, OrderByComparator<ObjectRelationship> orderByComparator) {

		List<ObjectRelationship> list = findByUuid(
			uuid, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last object relationship in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object relationship
	 * @throws NoSuchObjectRelationshipException if a matching object relationship could not be found
	 */
	@Override
	public ObjectRelationship findByUuid_Last(
			String uuid,
			OrderByComparator<ObjectRelationship> orderByComparator)
		throws NoSuchObjectRelationshipException {

		ObjectRelationship objectRelationship = fetchByUuid_Last(
			uuid, orderByComparator);

		if (objectRelationship != null) {
			return objectRelationship;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append("}");

		throw new NoSuchObjectRelationshipException(sb.toString());
	}

	/**
	 * Returns the last object relationship in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object relationship, or <code>null</code> if a matching object relationship could not be found
	 */
	@Override
	public ObjectRelationship fetchByUuid_Last(
		String uuid, OrderByComparator<ObjectRelationship> orderByComparator) {

		int count = countByUuid(uuid);

		if (count == 0) {
			return null;
		}

		List<ObjectRelationship> list = findByUuid(
			uuid, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the object relationships before and after the current object relationship in the ordered set where uuid = &#63;.
	 *
	 * @param objectRelationshipId the primary key of the current object relationship
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next object relationship
	 * @throws NoSuchObjectRelationshipException if a object relationship with the primary key could not be found
	 */
	@Override
	public ObjectRelationship[] findByUuid_PrevAndNext(
			long objectRelationshipId, String uuid,
			OrderByComparator<ObjectRelationship> orderByComparator)
		throws NoSuchObjectRelationshipException {

		uuid = Objects.toString(uuid, "");

		ObjectRelationship objectRelationship = findByPrimaryKey(
			objectRelationshipId);

		Session session = null;

		try {
			session = openSession();

			ObjectRelationship[] array = new ObjectRelationshipImpl[3];

			array[0] = getByUuid_PrevAndNext(
				session, objectRelationship, uuid, orderByComparator, true);

			array[1] = objectRelationship;

			array[2] = getByUuid_PrevAndNext(
				session, objectRelationship, uuid, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected ObjectRelationship getByUuid_PrevAndNext(
		Session session, ObjectRelationship objectRelationship, String uuid,
		OrderByComparator<ObjectRelationship> orderByComparator,
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

		sb.append(_SQL_SELECT_OBJECTRELATIONSHIP_WHERE);

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
			sb.append(ObjectRelationshipModelImpl.ORDER_BY_JPQL);
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
						objectRelationship)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<ObjectRelationship> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the object relationships where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	@Override
	public void removeByUuid(String uuid) {
		for (ObjectRelationship objectRelationship :
				findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(objectRelationship);
		}
	}

	/**
	 * Returns the number of object relationships where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching object relationships
	 */
	@Override
	public int countByUuid(String uuid) {
		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = _finderPathCountByUuid;

		Object[] finderArgs = new Object[] {uuid};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_OBJECTRELATIONSHIP_WHERE);

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
		"objectRelationship.uuid = ?";

	private static final String _FINDER_COLUMN_UUID_UUID_3 =
		"(objectRelationship.uuid IS NULL OR objectRelationship.uuid = '')";

	private FinderPath _finderPathWithPaginationFindByUuid_C;
	private FinderPath _finderPathWithoutPaginationFindByUuid_C;
	private FinderPath _finderPathCountByUuid_C;

	/**
	 * Returns all the object relationships where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching object relationships
	 */
	@Override
	public List<ObjectRelationship> findByUuid_C(String uuid, long companyId) {
		return findByUuid_C(
			uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the object relationships where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectRelationshipModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of object relationships
	 * @param end the upper bound of the range of object relationships (not inclusive)
	 * @return the range of matching object relationships
	 */
	@Override
	public List<ObjectRelationship> findByUuid_C(
		String uuid, long companyId, int start, int end) {

		return findByUuid_C(uuid, companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the object relationships where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectRelationshipModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of object relationships
	 * @param end the upper bound of the range of object relationships (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching object relationships
	 */
	@Override
	public List<ObjectRelationship> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<ObjectRelationship> orderByComparator) {

		return findByUuid_C(
			uuid, companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the object relationships where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectRelationshipModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of object relationships
	 * @param end the upper bound of the range of object relationships (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching object relationships
	 */
	@Override
	public List<ObjectRelationship> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<ObjectRelationship> orderByComparator,
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

		List<ObjectRelationship> list = null;

		if (useFinderCache) {
			list = (List<ObjectRelationship>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (ObjectRelationship objectRelationship : list) {
					if (!uuid.equals(objectRelationship.getUuid()) ||
						(companyId != objectRelationship.getCompanyId())) {

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

			sb.append(_SQL_SELECT_OBJECTRELATIONSHIP_WHERE);

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
				sb.append(ObjectRelationshipModelImpl.ORDER_BY_JPQL);
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

				list = (List<ObjectRelationship>)QueryUtil.list(
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
	 * Returns the first object relationship in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object relationship
	 * @throws NoSuchObjectRelationshipException if a matching object relationship could not be found
	 */
	@Override
	public ObjectRelationship findByUuid_C_First(
			String uuid, long companyId,
			OrderByComparator<ObjectRelationship> orderByComparator)
		throws NoSuchObjectRelationshipException {

		ObjectRelationship objectRelationship = fetchByUuid_C_First(
			uuid, companyId, orderByComparator);

		if (objectRelationship != null) {
			return objectRelationship;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append(", companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchObjectRelationshipException(sb.toString());
	}

	/**
	 * Returns the first object relationship in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object relationship, or <code>null</code> if a matching object relationship could not be found
	 */
	@Override
	public ObjectRelationship fetchByUuid_C_First(
		String uuid, long companyId,
		OrderByComparator<ObjectRelationship> orderByComparator) {

		List<ObjectRelationship> list = findByUuid_C(
			uuid, companyId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last object relationship in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object relationship
	 * @throws NoSuchObjectRelationshipException if a matching object relationship could not be found
	 */
	@Override
	public ObjectRelationship findByUuid_C_Last(
			String uuid, long companyId,
			OrderByComparator<ObjectRelationship> orderByComparator)
		throws NoSuchObjectRelationshipException {

		ObjectRelationship objectRelationship = fetchByUuid_C_Last(
			uuid, companyId, orderByComparator);

		if (objectRelationship != null) {
			return objectRelationship;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append(", companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchObjectRelationshipException(sb.toString());
	}

	/**
	 * Returns the last object relationship in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object relationship, or <code>null</code> if a matching object relationship could not be found
	 */
	@Override
	public ObjectRelationship fetchByUuid_C_Last(
		String uuid, long companyId,
		OrderByComparator<ObjectRelationship> orderByComparator) {

		int count = countByUuid_C(uuid, companyId);

		if (count == 0) {
			return null;
		}

		List<ObjectRelationship> list = findByUuid_C(
			uuid, companyId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the object relationships before and after the current object relationship in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param objectRelationshipId the primary key of the current object relationship
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next object relationship
	 * @throws NoSuchObjectRelationshipException if a object relationship with the primary key could not be found
	 */
	@Override
	public ObjectRelationship[] findByUuid_C_PrevAndNext(
			long objectRelationshipId, String uuid, long companyId,
			OrderByComparator<ObjectRelationship> orderByComparator)
		throws NoSuchObjectRelationshipException {

		uuid = Objects.toString(uuid, "");

		ObjectRelationship objectRelationship = findByPrimaryKey(
			objectRelationshipId);

		Session session = null;

		try {
			session = openSession();

			ObjectRelationship[] array = new ObjectRelationshipImpl[3];

			array[0] = getByUuid_C_PrevAndNext(
				session, objectRelationship, uuid, companyId, orderByComparator,
				true);

			array[1] = objectRelationship;

			array[2] = getByUuid_C_PrevAndNext(
				session, objectRelationship, uuid, companyId, orderByComparator,
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

	protected ObjectRelationship getByUuid_C_PrevAndNext(
		Session session, ObjectRelationship objectRelationship, String uuid,
		long companyId, OrderByComparator<ObjectRelationship> orderByComparator,
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

		sb.append(_SQL_SELECT_OBJECTRELATIONSHIP_WHERE);

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
			sb.append(ObjectRelationshipModelImpl.ORDER_BY_JPQL);
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
						objectRelationship)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<ObjectRelationship> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the object relationships where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	@Override
	public void removeByUuid_C(String uuid, long companyId) {
		for (ObjectRelationship objectRelationship :
				findByUuid_C(
					uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(objectRelationship);
		}
	}

	/**
	 * Returns the number of object relationships where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching object relationships
	 */
	@Override
	public int countByUuid_C(String uuid, long companyId) {
		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = _finderPathCountByUuid_C;

		Object[] finderArgs = new Object[] {uuid, companyId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_OBJECTRELATIONSHIP_WHERE);

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
		"objectRelationship.uuid = ? AND ";

	private static final String _FINDER_COLUMN_UUID_C_UUID_3 =
		"(objectRelationship.uuid IS NULL OR objectRelationship.uuid = '') AND ";

	private static final String _FINDER_COLUMN_UUID_C_COMPANYID_2 =
		"objectRelationship.companyId = ?";

	private FinderPath _finderPathWithPaginationFindByObjectDefinitionId1;
	private FinderPath _finderPathWithoutPaginationFindByObjectDefinitionId1;
	private FinderPath _finderPathCountByObjectDefinitionId1;

	/**
	 * Returns all the object relationships where objectDefinitionId1 = &#63;.
	 *
	 * @param objectDefinitionId1 the object definition id1
	 * @return the matching object relationships
	 */
	@Override
	public List<ObjectRelationship> findByObjectDefinitionId1(
		long objectDefinitionId1) {

		return findByObjectDefinitionId1(
			objectDefinitionId1, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the object relationships where objectDefinitionId1 = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectRelationshipModelImpl</code>.
	 * </p>
	 *
	 * @param objectDefinitionId1 the object definition id1
	 * @param start the lower bound of the range of object relationships
	 * @param end the upper bound of the range of object relationships (not inclusive)
	 * @return the range of matching object relationships
	 */
	@Override
	public List<ObjectRelationship> findByObjectDefinitionId1(
		long objectDefinitionId1, int start, int end) {

		return findByObjectDefinitionId1(objectDefinitionId1, start, end, null);
	}

	/**
	 * Returns an ordered range of all the object relationships where objectDefinitionId1 = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectRelationshipModelImpl</code>.
	 * </p>
	 *
	 * @param objectDefinitionId1 the object definition id1
	 * @param start the lower bound of the range of object relationships
	 * @param end the upper bound of the range of object relationships (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching object relationships
	 */
	@Override
	public List<ObjectRelationship> findByObjectDefinitionId1(
		long objectDefinitionId1, int start, int end,
		OrderByComparator<ObjectRelationship> orderByComparator) {

		return findByObjectDefinitionId1(
			objectDefinitionId1, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the object relationships where objectDefinitionId1 = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectRelationshipModelImpl</code>.
	 * </p>
	 *
	 * @param objectDefinitionId1 the object definition id1
	 * @param start the lower bound of the range of object relationships
	 * @param end the upper bound of the range of object relationships (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching object relationships
	 */
	@Override
	public List<ObjectRelationship> findByObjectDefinitionId1(
		long objectDefinitionId1, int start, int end,
		OrderByComparator<ObjectRelationship> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath =
					_finderPathWithoutPaginationFindByObjectDefinitionId1;
				finderArgs = new Object[] {objectDefinitionId1};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByObjectDefinitionId1;
			finderArgs = new Object[] {
				objectDefinitionId1, start, end, orderByComparator
			};
		}

		List<ObjectRelationship> list = null;

		if (useFinderCache) {
			list = (List<ObjectRelationship>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (ObjectRelationship objectRelationship : list) {
					if (objectDefinitionId1 !=
							objectRelationship.getObjectDefinitionId1()) {

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

			sb.append(_SQL_SELECT_OBJECTRELATIONSHIP_WHERE);

			sb.append(_FINDER_COLUMN_OBJECTDEFINITIONID1_OBJECTDEFINITIONID1_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(ObjectRelationshipModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(objectDefinitionId1);

				list = (List<ObjectRelationship>)QueryUtil.list(
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
	 * Returns the first object relationship in the ordered set where objectDefinitionId1 = &#63;.
	 *
	 * @param objectDefinitionId1 the object definition id1
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object relationship
	 * @throws NoSuchObjectRelationshipException if a matching object relationship could not be found
	 */
	@Override
	public ObjectRelationship findByObjectDefinitionId1_First(
			long objectDefinitionId1,
			OrderByComparator<ObjectRelationship> orderByComparator)
		throws NoSuchObjectRelationshipException {

		ObjectRelationship objectRelationship =
			fetchByObjectDefinitionId1_First(
				objectDefinitionId1, orderByComparator);

		if (objectRelationship != null) {
			return objectRelationship;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("objectDefinitionId1=");
		sb.append(objectDefinitionId1);

		sb.append("}");

		throw new NoSuchObjectRelationshipException(sb.toString());
	}

	/**
	 * Returns the first object relationship in the ordered set where objectDefinitionId1 = &#63;.
	 *
	 * @param objectDefinitionId1 the object definition id1
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object relationship, or <code>null</code> if a matching object relationship could not be found
	 */
	@Override
	public ObjectRelationship fetchByObjectDefinitionId1_First(
		long objectDefinitionId1,
		OrderByComparator<ObjectRelationship> orderByComparator) {

		List<ObjectRelationship> list = findByObjectDefinitionId1(
			objectDefinitionId1, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last object relationship in the ordered set where objectDefinitionId1 = &#63;.
	 *
	 * @param objectDefinitionId1 the object definition id1
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object relationship
	 * @throws NoSuchObjectRelationshipException if a matching object relationship could not be found
	 */
	@Override
	public ObjectRelationship findByObjectDefinitionId1_Last(
			long objectDefinitionId1,
			OrderByComparator<ObjectRelationship> orderByComparator)
		throws NoSuchObjectRelationshipException {

		ObjectRelationship objectRelationship = fetchByObjectDefinitionId1_Last(
			objectDefinitionId1, orderByComparator);

		if (objectRelationship != null) {
			return objectRelationship;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("objectDefinitionId1=");
		sb.append(objectDefinitionId1);

		sb.append("}");

		throw new NoSuchObjectRelationshipException(sb.toString());
	}

	/**
	 * Returns the last object relationship in the ordered set where objectDefinitionId1 = &#63;.
	 *
	 * @param objectDefinitionId1 the object definition id1
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object relationship, or <code>null</code> if a matching object relationship could not be found
	 */
	@Override
	public ObjectRelationship fetchByObjectDefinitionId1_Last(
		long objectDefinitionId1,
		OrderByComparator<ObjectRelationship> orderByComparator) {

		int count = countByObjectDefinitionId1(objectDefinitionId1);

		if (count == 0) {
			return null;
		}

		List<ObjectRelationship> list = findByObjectDefinitionId1(
			objectDefinitionId1, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the object relationships before and after the current object relationship in the ordered set where objectDefinitionId1 = &#63;.
	 *
	 * @param objectRelationshipId the primary key of the current object relationship
	 * @param objectDefinitionId1 the object definition id1
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next object relationship
	 * @throws NoSuchObjectRelationshipException if a object relationship with the primary key could not be found
	 */
	@Override
	public ObjectRelationship[] findByObjectDefinitionId1_PrevAndNext(
			long objectRelationshipId, long objectDefinitionId1,
			OrderByComparator<ObjectRelationship> orderByComparator)
		throws NoSuchObjectRelationshipException {

		ObjectRelationship objectRelationship = findByPrimaryKey(
			objectRelationshipId);

		Session session = null;

		try {
			session = openSession();

			ObjectRelationship[] array = new ObjectRelationshipImpl[3];

			array[0] = getByObjectDefinitionId1_PrevAndNext(
				session, objectRelationship, objectDefinitionId1,
				orderByComparator, true);

			array[1] = objectRelationship;

			array[2] = getByObjectDefinitionId1_PrevAndNext(
				session, objectRelationship, objectDefinitionId1,
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

	protected ObjectRelationship getByObjectDefinitionId1_PrevAndNext(
		Session session, ObjectRelationship objectRelationship,
		long objectDefinitionId1,
		OrderByComparator<ObjectRelationship> orderByComparator,
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

		sb.append(_SQL_SELECT_OBJECTRELATIONSHIP_WHERE);

		sb.append(_FINDER_COLUMN_OBJECTDEFINITIONID1_OBJECTDEFINITIONID1_2);

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
			sb.append(ObjectRelationshipModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(objectDefinitionId1);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						objectRelationship)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<ObjectRelationship> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the object relationships where objectDefinitionId1 = &#63; from the database.
	 *
	 * @param objectDefinitionId1 the object definition id1
	 */
	@Override
	public void removeByObjectDefinitionId1(long objectDefinitionId1) {
		for (ObjectRelationship objectRelationship :
				findByObjectDefinitionId1(
					objectDefinitionId1, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(objectRelationship);
		}
	}

	/**
	 * Returns the number of object relationships where objectDefinitionId1 = &#63;.
	 *
	 * @param objectDefinitionId1 the object definition id1
	 * @return the number of matching object relationships
	 */
	@Override
	public int countByObjectDefinitionId1(long objectDefinitionId1) {
		FinderPath finderPath = _finderPathCountByObjectDefinitionId1;

		Object[] finderArgs = new Object[] {objectDefinitionId1};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_OBJECTRELATIONSHIP_WHERE);

			sb.append(_FINDER_COLUMN_OBJECTDEFINITIONID1_OBJECTDEFINITIONID1_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(objectDefinitionId1);

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
		_FINDER_COLUMN_OBJECTDEFINITIONID1_OBJECTDEFINITIONID1_2 =
			"objectRelationship.objectDefinitionId1 = ?";

	private FinderPath _finderPathWithPaginationFindByObjectDefinitionId2;
	private FinderPath _finderPathWithoutPaginationFindByObjectDefinitionId2;
	private FinderPath _finderPathCountByObjectDefinitionId2;

	/**
	 * Returns all the object relationships where objectDefinitionId2 = &#63;.
	 *
	 * @param objectDefinitionId2 the object definition id2
	 * @return the matching object relationships
	 */
	@Override
	public List<ObjectRelationship> findByObjectDefinitionId2(
		long objectDefinitionId2) {

		return findByObjectDefinitionId2(
			objectDefinitionId2, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the object relationships where objectDefinitionId2 = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectRelationshipModelImpl</code>.
	 * </p>
	 *
	 * @param objectDefinitionId2 the object definition id2
	 * @param start the lower bound of the range of object relationships
	 * @param end the upper bound of the range of object relationships (not inclusive)
	 * @return the range of matching object relationships
	 */
	@Override
	public List<ObjectRelationship> findByObjectDefinitionId2(
		long objectDefinitionId2, int start, int end) {

		return findByObjectDefinitionId2(objectDefinitionId2, start, end, null);
	}

	/**
	 * Returns an ordered range of all the object relationships where objectDefinitionId2 = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectRelationshipModelImpl</code>.
	 * </p>
	 *
	 * @param objectDefinitionId2 the object definition id2
	 * @param start the lower bound of the range of object relationships
	 * @param end the upper bound of the range of object relationships (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching object relationships
	 */
	@Override
	public List<ObjectRelationship> findByObjectDefinitionId2(
		long objectDefinitionId2, int start, int end,
		OrderByComparator<ObjectRelationship> orderByComparator) {

		return findByObjectDefinitionId2(
			objectDefinitionId2, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the object relationships where objectDefinitionId2 = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectRelationshipModelImpl</code>.
	 * </p>
	 *
	 * @param objectDefinitionId2 the object definition id2
	 * @param start the lower bound of the range of object relationships
	 * @param end the upper bound of the range of object relationships (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching object relationships
	 */
	@Override
	public List<ObjectRelationship> findByObjectDefinitionId2(
		long objectDefinitionId2, int start, int end,
		OrderByComparator<ObjectRelationship> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath =
					_finderPathWithoutPaginationFindByObjectDefinitionId2;
				finderArgs = new Object[] {objectDefinitionId2};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByObjectDefinitionId2;
			finderArgs = new Object[] {
				objectDefinitionId2, start, end, orderByComparator
			};
		}

		List<ObjectRelationship> list = null;

		if (useFinderCache) {
			list = (List<ObjectRelationship>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (ObjectRelationship objectRelationship : list) {
					if (objectDefinitionId2 !=
							objectRelationship.getObjectDefinitionId2()) {

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

			sb.append(_SQL_SELECT_OBJECTRELATIONSHIP_WHERE);

			sb.append(_FINDER_COLUMN_OBJECTDEFINITIONID2_OBJECTDEFINITIONID2_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(ObjectRelationshipModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(objectDefinitionId2);

				list = (List<ObjectRelationship>)QueryUtil.list(
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
	 * Returns the first object relationship in the ordered set where objectDefinitionId2 = &#63;.
	 *
	 * @param objectDefinitionId2 the object definition id2
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object relationship
	 * @throws NoSuchObjectRelationshipException if a matching object relationship could not be found
	 */
	@Override
	public ObjectRelationship findByObjectDefinitionId2_First(
			long objectDefinitionId2,
			OrderByComparator<ObjectRelationship> orderByComparator)
		throws NoSuchObjectRelationshipException {

		ObjectRelationship objectRelationship =
			fetchByObjectDefinitionId2_First(
				objectDefinitionId2, orderByComparator);

		if (objectRelationship != null) {
			return objectRelationship;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("objectDefinitionId2=");
		sb.append(objectDefinitionId2);

		sb.append("}");

		throw new NoSuchObjectRelationshipException(sb.toString());
	}

	/**
	 * Returns the first object relationship in the ordered set where objectDefinitionId2 = &#63;.
	 *
	 * @param objectDefinitionId2 the object definition id2
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object relationship, or <code>null</code> if a matching object relationship could not be found
	 */
	@Override
	public ObjectRelationship fetchByObjectDefinitionId2_First(
		long objectDefinitionId2,
		OrderByComparator<ObjectRelationship> orderByComparator) {

		List<ObjectRelationship> list = findByObjectDefinitionId2(
			objectDefinitionId2, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last object relationship in the ordered set where objectDefinitionId2 = &#63;.
	 *
	 * @param objectDefinitionId2 the object definition id2
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object relationship
	 * @throws NoSuchObjectRelationshipException if a matching object relationship could not be found
	 */
	@Override
	public ObjectRelationship findByObjectDefinitionId2_Last(
			long objectDefinitionId2,
			OrderByComparator<ObjectRelationship> orderByComparator)
		throws NoSuchObjectRelationshipException {

		ObjectRelationship objectRelationship = fetchByObjectDefinitionId2_Last(
			objectDefinitionId2, orderByComparator);

		if (objectRelationship != null) {
			return objectRelationship;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("objectDefinitionId2=");
		sb.append(objectDefinitionId2);

		sb.append("}");

		throw new NoSuchObjectRelationshipException(sb.toString());
	}

	/**
	 * Returns the last object relationship in the ordered set where objectDefinitionId2 = &#63;.
	 *
	 * @param objectDefinitionId2 the object definition id2
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object relationship, or <code>null</code> if a matching object relationship could not be found
	 */
	@Override
	public ObjectRelationship fetchByObjectDefinitionId2_Last(
		long objectDefinitionId2,
		OrderByComparator<ObjectRelationship> orderByComparator) {

		int count = countByObjectDefinitionId2(objectDefinitionId2);

		if (count == 0) {
			return null;
		}

		List<ObjectRelationship> list = findByObjectDefinitionId2(
			objectDefinitionId2, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the object relationships before and after the current object relationship in the ordered set where objectDefinitionId2 = &#63;.
	 *
	 * @param objectRelationshipId the primary key of the current object relationship
	 * @param objectDefinitionId2 the object definition id2
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next object relationship
	 * @throws NoSuchObjectRelationshipException if a object relationship with the primary key could not be found
	 */
	@Override
	public ObjectRelationship[] findByObjectDefinitionId2_PrevAndNext(
			long objectRelationshipId, long objectDefinitionId2,
			OrderByComparator<ObjectRelationship> orderByComparator)
		throws NoSuchObjectRelationshipException {

		ObjectRelationship objectRelationship = findByPrimaryKey(
			objectRelationshipId);

		Session session = null;

		try {
			session = openSession();

			ObjectRelationship[] array = new ObjectRelationshipImpl[3];

			array[0] = getByObjectDefinitionId2_PrevAndNext(
				session, objectRelationship, objectDefinitionId2,
				orderByComparator, true);

			array[1] = objectRelationship;

			array[2] = getByObjectDefinitionId2_PrevAndNext(
				session, objectRelationship, objectDefinitionId2,
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

	protected ObjectRelationship getByObjectDefinitionId2_PrevAndNext(
		Session session, ObjectRelationship objectRelationship,
		long objectDefinitionId2,
		OrderByComparator<ObjectRelationship> orderByComparator,
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

		sb.append(_SQL_SELECT_OBJECTRELATIONSHIP_WHERE);

		sb.append(_FINDER_COLUMN_OBJECTDEFINITIONID2_OBJECTDEFINITIONID2_2);

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
			sb.append(ObjectRelationshipModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(objectDefinitionId2);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						objectRelationship)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<ObjectRelationship> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the object relationships where objectDefinitionId2 = &#63; from the database.
	 *
	 * @param objectDefinitionId2 the object definition id2
	 */
	@Override
	public void removeByObjectDefinitionId2(long objectDefinitionId2) {
		for (ObjectRelationship objectRelationship :
				findByObjectDefinitionId2(
					objectDefinitionId2, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(objectRelationship);
		}
	}

	/**
	 * Returns the number of object relationships where objectDefinitionId2 = &#63;.
	 *
	 * @param objectDefinitionId2 the object definition id2
	 * @return the number of matching object relationships
	 */
	@Override
	public int countByObjectDefinitionId2(long objectDefinitionId2) {
		FinderPath finderPath = _finderPathCountByObjectDefinitionId2;

		Object[] finderArgs = new Object[] {objectDefinitionId2};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_OBJECTRELATIONSHIP_WHERE);

			sb.append(_FINDER_COLUMN_OBJECTDEFINITIONID2_OBJECTDEFINITIONID2_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(objectDefinitionId2);

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
		_FINDER_COLUMN_OBJECTDEFINITIONID2_OBJECTDEFINITIONID2_2 =
			"objectRelationship.objectDefinitionId2 = ?";

	public ObjectRelationshipPersistenceImpl() {
		Map<String, String> dbColumnNames = new HashMap<String, String>();

		dbColumnNames.put("uuid", "uuid_");
		dbColumnNames.put("type", "type_");

		setDBColumnNames(dbColumnNames);

		setModelClass(ObjectRelationship.class);

		setModelImplClass(ObjectRelationshipImpl.class);
		setModelPKClass(long.class);

		setTable(ObjectRelationshipTable.INSTANCE);
	}

	/**
	 * Caches the object relationship in the entity cache if it is enabled.
	 *
	 * @param objectRelationship the object relationship
	 */
	@Override
	public void cacheResult(ObjectRelationship objectRelationship) {
		entityCache.putResult(
			ObjectRelationshipImpl.class, objectRelationship.getPrimaryKey(),
			objectRelationship);
	}

	/**
	 * Caches the object relationships in the entity cache if it is enabled.
	 *
	 * @param objectRelationships the object relationships
	 */
	@Override
	public void cacheResult(List<ObjectRelationship> objectRelationships) {
		for (ObjectRelationship objectRelationship : objectRelationships) {
			if (entityCache.getResult(
					ObjectRelationshipImpl.class,
					objectRelationship.getPrimaryKey()) == null) {

				cacheResult(objectRelationship);
			}
		}
	}

	/**
	 * Clears the cache for all object relationships.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(ObjectRelationshipImpl.class);

		finderCache.clearCache(ObjectRelationshipImpl.class);
	}

	/**
	 * Clears the cache for the object relationship.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(ObjectRelationship objectRelationship) {
		entityCache.removeResult(
			ObjectRelationshipImpl.class, objectRelationship);
	}

	@Override
	public void clearCache(List<ObjectRelationship> objectRelationships) {
		for (ObjectRelationship objectRelationship : objectRelationships) {
			entityCache.removeResult(
				ObjectRelationshipImpl.class, objectRelationship);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(ObjectRelationshipImpl.class);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(ObjectRelationshipImpl.class, primaryKey);
		}
	}

	/**
	 * Creates a new object relationship with the primary key. Does not add the object relationship to the database.
	 *
	 * @param objectRelationshipId the primary key for the new object relationship
	 * @return the new object relationship
	 */
	@Override
	public ObjectRelationship create(long objectRelationshipId) {
		ObjectRelationship objectRelationship = new ObjectRelationshipImpl();

		objectRelationship.setNew(true);
		objectRelationship.setPrimaryKey(objectRelationshipId);

		String uuid = PortalUUIDUtil.generate();

		objectRelationship.setUuid(uuid);

		objectRelationship.setCompanyId(CompanyThreadLocal.getCompanyId());

		return objectRelationship;
	}

	/**
	 * Removes the object relationship with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param objectRelationshipId the primary key of the object relationship
	 * @return the object relationship that was removed
	 * @throws NoSuchObjectRelationshipException if a object relationship with the primary key could not be found
	 */
	@Override
	public ObjectRelationship remove(long objectRelationshipId)
		throws NoSuchObjectRelationshipException {

		return remove((Serializable)objectRelationshipId);
	}

	/**
	 * Removes the object relationship with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the object relationship
	 * @return the object relationship that was removed
	 * @throws NoSuchObjectRelationshipException if a object relationship with the primary key could not be found
	 */
	@Override
	public ObjectRelationship remove(Serializable primaryKey)
		throws NoSuchObjectRelationshipException {

		Session session = null;

		try {
			session = openSession();

			ObjectRelationship objectRelationship =
				(ObjectRelationship)session.get(
					ObjectRelationshipImpl.class, primaryKey);

			if (objectRelationship == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchObjectRelationshipException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(objectRelationship);
		}
		catch (NoSuchObjectRelationshipException noSuchEntityException) {
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
	protected ObjectRelationship removeImpl(
		ObjectRelationship objectRelationship) {

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(objectRelationship)) {
				objectRelationship = (ObjectRelationship)session.get(
					ObjectRelationshipImpl.class,
					objectRelationship.getPrimaryKeyObj());
			}

			if (objectRelationship != null) {
				session.delete(objectRelationship);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (objectRelationship != null) {
			clearCache(objectRelationship);
		}

		return objectRelationship;
	}

	@Override
	public ObjectRelationship updateImpl(
		ObjectRelationship objectRelationship) {

		boolean isNew = objectRelationship.isNew();

		if (!(objectRelationship instanceof ObjectRelationshipModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(objectRelationship.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					objectRelationship);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in objectRelationship proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom ObjectRelationship implementation " +
					objectRelationship.getClass());
		}

		ObjectRelationshipModelImpl objectRelationshipModelImpl =
			(ObjectRelationshipModelImpl)objectRelationship;

		if (Validator.isNull(objectRelationship.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			objectRelationship.setUuid(uuid);
		}

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Date date = new Date();

		if (isNew && (objectRelationship.getCreateDate() == null)) {
			if (serviceContext == null) {
				objectRelationship.setCreateDate(date);
			}
			else {
				objectRelationship.setCreateDate(
					serviceContext.getCreateDate(date));
			}
		}

		if (!objectRelationshipModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				objectRelationship.setModifiedDate(date);
			}
			else {
				objectRelationship.setModifiedDate(
					serviceContext.getModifiedDate(date));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (isNew) {
				session.save(objectRelationship);
			}
			else {
				objectRelationship = (ObjectRelationship)session.merge(
					objectRelationship);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		entityCache.putResult(
			ObjectRelationshipImpl.class, objectRelationshipModelImpl, false,
			true);

		if (isNew) {
			objectRelationship.setNew(false);
		}

		objectRelationship.resetOriginalValues();

		return objectRelationship;
	}

	/**
	 * Returns the object relationship with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the object relationship
	 * @return the object relationship
	 * @throws NoSuchObjectRelationshipException if a object relationship with the primary key could not be found
	 */
	@Override
	public ObjectRelationship findByPrimaryKey(Serializable primaryKey)
		throws NoSuchObjectRelationshipException {

		ObjectRelationship objectRelationship = fetchByPrimaryKey(primaryKey);

		if (objectRelationship == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchObjectRelationshipException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return objectRelationship;
	}

	/**
	 * Returns the object relationship with the primary key or throws a <code>NoSuchObjectRelationshipException</code> if it could not be found.
	 *
	 * @param objectRelationshipId the primary key of the object relationship
	 * @return the object relationship
	 * @throws NoSuchObjectRelationshipException if a object relationship with the primary key could not be found
	 */
	@Override
	public ObjectRelationship findByPrimaryKey(long objectRelationshipId)
		throws NoSuchObjectRelationshipException {

		return findByPrimaryKey((Serializable)objectRelationshipId);
	}

	/**
	 * Returns the object relationship with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param objectRelationshipId the primary key of the object relationship
	 * @return the object relationship, or <code>null</code> if a object relationship with the primary key could not be found
	 */
	@Override
	public ObjectRelationship fetchByPrimaryKey(long objectRelationshipId) {
		return fetchByPrimaryKey((Serializable)objectRelationshipId);
	}

	/**
	 * Returns all the object relationships.
	 *
	 * @return the object relationships
	 */
	@Override
	public List<ObjectRelationship> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the object relationships.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectRelationshipModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of object relationships
	 * @param end the upper bound of the range of object relationships (not inclusive)
	 * @return the range of object relationships
	 */
	@Override
	public List<ObjectRelationship> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the object relationships.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectRelationshipModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of object relationships
	 * @param end the upper bound of the range of object relationships (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of object relationships
	 */
	@Override
	public List<ObjectRelationship> findAll(
		int start, int end,
		OrderByComparator<ObjectRelationship> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the object relationships.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectRelationshipModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of object relationships
	 * @param end the upper bound of the range of object relationships (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of object relationships
	 */
	@Override
	public List<ObjectRelationship> findAll(
		int start, int end,
		OrderByComparator<ObjectRelationship> orderByComparator,
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

		List<ObjectRelationship> list = null;

		if (useFinderCache) {
			list = (List<ObjectRelationship>)finderCache.getResult(
				finderPath, finderArgs);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_OBJECTRELATIONSHIP);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_OBJECTRELATIONSHIP;

				sql = sql.concat(ObjectRelationshipModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<ObjectRelationship>)QueryUtil.list(
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
	 * Removes all the object relationships from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (ObjectRelationship objectRelationship : findAll()) {
			remove(objectRelationship);
		}
	}

	/**
	 * Returns the number of object relationships.
	 *
	 * @return the number of object relationships
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
					_SQL_COUNT_OBJECTRELATIONSHIP);

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
		return "objectRelationshipId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_OBJECTRELATIONSHIP;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return ObjectRelationshipModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the object relationship persistence.
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

		_finderPathWithPaginationFindByObjectDefinitionId1 = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByObjectDefinitionId1",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"objectDefinitionId1"}, true);

		_finderPathWithoutPaginationFindByObjectDefinitionId1 = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByObjectDefinitionId1", new String[] {Long.class.getName()},
			new String[] {"objectDefinitionId1"}, true);

		_finderPathCountByObjectDefinitionId1 = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByObjectDefinitionId1", new String[] {Long.class.getName()},
			new String[] {"objectDefinitionId1"}, false);

		_finderPathWithPaginationFindByObjectDefinitionId2 = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByObjectDefinitionId2",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"objectDefinitionId2"}, true);

		_finderPathWithoutPaginationFindByObjectDefinitionId2 = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByObjectDefinitionId2", new String[] {Long.class.getName()},
			new String[] {"objectDefinitionId2"}, true);

		_finderPathCountByObjectDefinitionId2 = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByObjectDefinitionId2", new String[] {Long.class.getName()},
			new String[] {"objectDefinitionId2"}, false);
	}

	@Deactivate
	public void deactivate() {
		entityCache.removeCache(ObjectRelationshipImpl.class.getName());
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

	private static final String _SQL_SELECT_OBJECTRELATIONSHIP =
		"SELECT objectRelationship FROM ObjectRelationship objectRelationship";

	private static final String _SQL_SELECT_OBJECTRELATIONSHIP_WHERE =
		"SELECT objectRelationship FROM ObjectRelationship objectRelationship WHERE ";

	private static final String _SQL_COUNT_OBJECTRELATIONSHIP =
		"SELECT COUNT(objectRelationship) FROM ObjectRelationship objectRelationship";

	private static final String _SQL_COUNT_OBJECTRELATIONSHIP_WHERE =
		"SELECT COUNT(objectRelationship) FROM ObjectRelationship objectRelationship WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS = "objectRelationship.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No ObjectRelationship exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No ObjectRelationship exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		ObjectRelationshipPersistenceImpl.class);

	private static final Set<String> _badColumnNames = SetUtil.fromArray(
		new String[] {"uuid", "type"});

	@Override
	protected FinderCache getFinderCache() {
		return finderCache;
	}

	@Reference
	private ObjectRelationshipModelArgumentsResolver
		_objectRelationshipModelArgumentsResolver;

}