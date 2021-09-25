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

import com.liferay.object.exception.NoSuchObjectDefinitionException;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectDefinitionTable;
import com.liferay.object.model.impl.ObjectDefinitionImpl;
import com.liferay.object.model.impl.ObjectDefinitionModelImpl;
import com.liferay.object.service.persistence.ObjectDefinitionPersistence;
import com.liferay.object.service.persistence.impl.constants.ObjectPersistenceConstants;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.configuration.Configuration;
import com.liferay.portal.kernel.dao.orm.EntityCache;
import com.liferay.portal.kernel.dao.orm.FinderCache;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.SQLQuery;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.dao.orm.SessionFactory;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.security.permission.InlineSQLHelperUtil;
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
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;

import java.io.Serializable;

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
 * The persistence implementation for the object definition service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Marco Leo
 * @generated
 */
@Component(service = {ObjectDefinitionPersistence.class, BasePersistence.class})
public class ObjectDefinitionPersistenceImpl
	extends BasePersistenceImpl<ObjectDefinition>
	implements ObjectDefinitionPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>ObjectDefinitionUtil</code> to access the object definition persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		ObjectDefinitionImpl.class.getName();

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
	 * Returns all the object definitions where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching object definitions
	 */
	@Override
	public List<ObjectDefinition> findByUuid(String uuid) {
		return findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the object definitions where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectDefinitionModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of object definitions
	 * @param end the upper bound of the range of object definitions (not inclusive)
	 * @return the range of matching object definitions
	 */
	@Override
	public List<ObjectDefinition> findByUuid(String uuid, int start, int end) {
		return findByUuid(uuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the object definitions where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectDefinitionModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of object definitions
	 * @param end the upper bound of the range of object definitions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching object definitions
	 */
	@Override
	public List<ObjectDefinition> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<ObjectDefinition> orderByComparator) {

		return findByUuid(uuid, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the object definitions where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectDefinitionModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of object definitions
	 * @param end the upper bound of the range of object definitions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching object definitions
	 */
	@Override
	public List<ObjectDefinition> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<ObjectDefinition> orderByComparator,
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

		List<ObjectDefinition> list = null;

		if (useFinderCache) {
			list = (List<ObjectDefinition>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (ObjectDefinition objectDefinition : list) {
					if (!uuid.equals(objectDefinition.getUuid())) {
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

			sb.append(_SQL_SELECT_OBJECTDEFINITION_WHERE);

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
				sb.append(ObjectDefinitionModelImpl.ORDER_BY_JPQL);
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

				list = (List<ObjectDefinition>)QueryUtil.list(
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
	 * Returns the first object definition in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object definition
	 * @throws NoSuchObjectDefinitionException if a matching object definition could not be found
	 */
	@Override
	public ObjectDefinition findByUuid_First(
			String uuid, OrderByComparator<ObjectDefinition> orderByComparator)
		throws NoSuchObjectDefinitionException {

		ObjectDefinition objectDefinition = fetchByUuid_First(
			uuid, orderByComparator);

		if (objectDefinition != null) {
			return objectDefinition;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append("}");

		throw new NoSuchObjectDefinitionException(sb.toString());
	}

	/**
	 * Returns the first object definition in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object definition, or <code>null</code> if a matching object definition could not be found
	 */
	@Override
	public ObjectDefinition fetchByUuid_First(
		String uuid, OrderByComparator<ObjectDefinition> orderByComparator) {

		List<ObjectDefinition> list = findByUuid(uuid, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last object definition in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object definition
	 * @throws NoSuchObjectDefinitionException if a matching object definition could not be found
	 */
	@Override
	public ObjectDefinition findByUuid_Last(
			String uuid, OrderByComparator<ObjectDefinition> orderByComparator)
		throws NoSuchObjectDefinitionException {

		ObjectDefinition objectDefinition = fetchByUuid_Last(
			uuid, orderByComparator);

		if (objectDefinition != null) {
			return objectDefinition;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append("}");

		throw new NoSuchObjectDefinitionException(sb.toString());
	}

	/**
	 * Returns the last object definition in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object definition, or <code>null</code> if a matching object definition could not be found
	 */
	@Override
	public ObjectDefinition fetchByUuid_Last(
		String uuid, OrderByComparator<ObjectDefinition> orderByComparator) {

		int count = countByUuid(uuid);

		if (count == 0) {
			return null;
		}

		List<ObjectDefinition> list = findByUuid(
			uuid, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the object definitions before and after the current object definition in the ordered set where uuid = &#63;.
	 *
	 * @param objectDefinitionId the primary key of the current object definition
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next object definition
	 * @throws NoSuchObjectDefinitionException if a object definition with the primary key could not be found
	 */
	@Override
	public ObjectDefinition[] findByUuid_PrevAndNext(
			long objectDefinitionId, String uuid,
			OrderByComparator<ObjectDefinition> orderByComparator)
		throws NoSuchObjectDefinitionException {

		uuid = Objects.toString(uuid, "");

		ObjectDefinition objectDefinition = findByPrimaryKey(
			objectDefinitionId);

		Session session = null;

		try {
			session = openSession();

			ObjectDefinition[] array = new ObjectDefinitionImpl[3];

			array[0] = getByUuid_PrevAndNext(
				session, objectDefinition, uuid, orderByComparator, true);

			array[1] = objectDefinition;

			array[2] = getByUuid_PrevAndNext(
				session, objectDefinition, uuid, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected ObjectDefinition getByUuid_PrevAndNext(
		Session session, ObjectDefinition objectDefinition, String uuid,
		OrderByComparator<ObjectDefinition> orderByComparator,
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

		sb.append(_SQL_SELECT_OBJECTDEFINITION_WHERE);

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
			sb.append(ObjectDefinitionModelImpl.ORDER_BY_JPQL);
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
						objectDefinition)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<ObjectDefinition> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the object definitions that the user has permission to view where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching object definitions that the user has permission to view
	 */
	@Override
	public List<ObjectDefinition> filterFindByUuid(String uuid) {
		return filterFindByUuid(
			uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the object definitions that the user has permission to view where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectDefinitionModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of object definitions
	 * @param end the upper bound of the range of object definitions (not inclusive)
	 * @return the range of matching object definitions that the user has permission to view
	 */
	@Override
	public List<ObjectDefinition> filterFindByUuid(
		String uuid, int start, int end) {

		return filterFindByUuid(uuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the object definitions that the user has permissions to view where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectDefinitionModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of object definitions
	 * @param end the upper bound of the range of object definitions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching object definitions that the user has permission to view
	 */
	@Override
	public List<ObjectDefinition> filterFindByUuid(
		String uuid, int start, int end,
		OrderByComparator<ObjectDefinition> orderByComparator) {

		if (!InlineSQLHelperUtil.isEnabled()) {
			return findByUuid(uuid, start, end, orderByComparator);
		}

		uuid = Objects.toString(uuid, "");

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				3 + (orderByComparator.getOrderByFields().length * 2));
		}
		else {
			sb = new StringBundler(4);
		}

		if (getDB().isSupportsInlineDistinct()) {
			sb.append(_FILTER_SQL_SELECT_OBJECTDEFINITION_WHERE);
		}
		else {
			sb.append(
				_FILTER_SQL_SELECT_OBJECTDEFINITION_NO_INLINE_DISTINCT_WHERE_1);
		}

		boolean bindUuid = false;

		if (uuid.isEmpty()) {
			sb.append(_FINDER_COLUMN_UUID_UUID_3_SQL);
		}
		else {
			bindUuid = true;

			sb.append(_FINDER_COLUMN_UUID_UUID_2_SQL);
		}

		if (!getDB().isSupportsInlineDistinct()) {
			sb.append(
				_FILTER_SQL_SELECT_OBJECTDEFINITION_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			if (getDB().isSupportsInlineDistinct()) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator, true);
			}
			else {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_TABLE, orderByComparator, true);
			}
		}
		else {
			if (getDB().isSupportsInlineDistinct()) {
				sb.append(ObjectDefinitionModelImpl.ORDER_BY_JPQL);
			}
			else {
				sb.append(ObjectDefinitionModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), ObjectDefinition.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				sqlQuery.addEntity(
					_FILTER_ENTITY_ALIAS, ObjectDefinitionImpl.class);
			}
			else {
				sqlQuery.addEntity(
					_FILTER_ENTITY_TABLE, ObjectDefinitionImpl.class);
			}

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			if (bindUuid) {
				queryPos.add(uuid);
			}

			return (List<ObjectDefinition>)QueryUtil.list(
				sqlQuery, getDialect(), start, end);
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Returns the object definitions before and after the current object definition in the ordered set of object definitions that the user has permission to view where uuid = &#63;.
	 *
	 * @param objectDefinitionId the primary key of the current object definition
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next object definition
	 * @throws NoSuchObjectDefinitionException if a object definition with the primary key could not be found
	 */
	@Override
	public ObjectDefinition[] filterFindByUuid_PrevAndNext(
			long objectDefinitionId, String uuid,
			OrderByComparator<ObjectDefinition> orderByComparator)
		throws NoSuchObjectDefinitionException {

		if (!InlineSQLHelperUtil.isEnabled()) {
			return findByUuid_PrevAndNext(
				objectDefinitionId, uuid, orderByComparator);
		}

		uuid = Objects.toString(uuid, "");

		ObjectDefinition objectDefinition = findByPrimaryKey(
			objectDefinitionId);

		Session session = null;

		try {
			session = openSession();

			ObjectDefinition[] array = new ObjectDefinitionImpl[3];

			array[0] = filterGetByUuid_PrevAndNext(
				session, objectDefinition, uuid, orderByComparator, true);

			array[1] = objectDefinition;

			array[2] = filterGetByUuid_PrevAndNext(
				session, objectDefinition, uuid, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected ObjectDefinition filterGetByUuid_PrevAndNext(
		Session session, ObjectDefinition objectDefinition, String uuid,
		OrderByComparator<ObjectDefinition> orderByComparator,
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

		if (getDB().isSupportsInlineDistinct()) {
			sb.append(_FILTER_SQL_SELECT_OBJECTDEFINITION_WHERE);
		}
		else {
			sb.append(
				_FILTER_SQL_SELECT_OBJECTDEFINITION_NO_INLINE_DISTINCT_WHERE_1);
		}

		boolean bindUuid = false;

		if (uuid.isEmpty()) {
			sb.append(_FINDER_COLUMN_UUID_UUID_3_SQL);
		}
		else {
			bindUuid = true;

			sb.append(_FINDER_COLUMN_UUID_UUID_2_SQL);
		}

		if (!getDB().isSupportsInlineDistinct()) {
			sb.append(
				_FILTER_SQL_SELECT_OBJECTDEFINITION_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			String[] orderByConditionFields =
				orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				sb.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				if (getDB().isSupportsInlineDistinct()) {
					sb.append(
						getColumnName(
							_ORDER_BY_ENTITY_ALIAS, orderByConditionFields[i],
							true));
				}
				else {
					sb.append(
						getColumnName(
							_ORDER_BY_ENTITY_TABLE, orderByConditionFields[i],
							true));
				}

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
				if (getDB().isSupportsInlineDistinct()) {
					sb.append(
						getColumnName(
							_ORDER_BY_ENTITY_ALIAS, orderByFields[i], true));
				}
				else {
					sb.append(
						getColumnName(
							_ORDER_BY_ENTITY_TABLE, orderByFields[i], true));
				}

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
			if (getDB().isSupportsInlineDistinct()) {
				sb.append(ObjectDefinitionModelImpl.ORDER_BY_JPQL);
			}
			else {
				sb.append(ObjectDefinitionModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), ObjectDefinition.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

		sqlQuery.setFirstResult(0);
		sqlQuery.setMaxResults(2);

		if (getDB().isSupportsInlineDistinct()) {
			sqlQuery.addEntity(
				_FILTER_ENTITY_ALIAS, ObjectDefinitionImpl.class);
		}
		else {
			sqlQuery.addEntity(
				_FILTER_ENTITY_TABLE, ObjectDefinitionImpl.class);
		}

		QueryPos queryPos = QueryPos.getInstance(sqlQuery);

		if (bindUuid) {
			queryPos.add(uuid);
		}

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						objectDefinition)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<ObjectDefinition> list = sqlQuery.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the object definitions where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	@Override
	public void removeByUuid(String uuid) {
		for (ObjectDefinition objectDefinition :
				findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(objectDefinition);
		}
	}

	/**
	 * Returns the number of object definitions where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching object definitions
	 */
	@Override
	public int countByUuid(String uuid) {
		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = _finderPathCountByUuid;

		Object[] finderArgs = new Object[] {uuid};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_OBJECTDEFINITION_WHERE);

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

	/**
	 * Returns the number of object definitions that the user has permission to view where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching object definitions that the user has permission to view
	 */
	@Override
	public int filterCountByUuid(String uuid) {
		if (!InlineSQLHelperUtil.isEnabled()) {
			return countByUuid(uuid);
		}

		uuid = Objects.toString(uuid, "");

		StringBundler sb = new StringBundler(2);

		sb.append(_FILTER_SQL_COUNT_OBJECTDEFINITION_WHERE);

		boolean bindUuid = false;

		if (uuid.isEmpty()) {
			sb.append(_FINDER_COLUMN_UUID_UUID_3_SQL);
		}
		else {
			bindUuid = true;

			sb.append(_FINDER_COLUMN_UUID_UUID_2_SQL);
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), ObjectDefinition.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			sqlQuery.addScalar(
				COUNT_COLUMN_NAME, com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			if (bindUuid) {
				queryPos.add(uuid);
			}

			Long count = (Long)sqlQuery.uniqueResult();

			return count.intValue();
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	private static final String _FINDER_COLUMN_UUID_UUID_2 =
		"objectDefinition.uuid = ?";

	private static final String _FINDER_COLUMN_UUID_UUID_3 =
		"(objectDefinition.uuid IS NULL OR objectDefinition.uuid = '')";

	private static final String _FINDER_COLUMN_UUID_UUID_2_SQL =
		"objectDefinition.uuid_ = ?";

	private static final String _FINDER_COLUMN_UUID_UUID_3_SQL =
		"(objectDefinition.uuid_ IS NULL OR objectDefinition.uuid_ = '')";

	private FinderPath _finderPathWithPaginationFindByUuid_C;
	private FinderPath _finderPathWithoutPaginationFindByUuid_C;
	private FinderPath _finderPathCountByUuid_C;

	/**
	 * Returns all the object definitions where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching object definitions
	 */
	@Override
	public List<ObjectDefinition> findByUuid_C(String uuid, long companyId) {
		return findByUuid_C(
			uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the object definitions where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectDefinitionModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of object definitions
	 * @param end the upper bound of the range of object definitions (not inclusive)
	 * @return the range of matching object definitions
	 */
	@Override
	public List<ObjectDefinition> findByUuid_C(
		String uuid, long companyId, int start, int end) {

		return findByUuid_C(uuid, companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the object definitions where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectDefinitionModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of object definitions
	 * @param end the upper bound of the range of object definitions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching object definitions
	 */
	@Override
	public List<ObjectDefinition> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<ObjectDefinition> orderByComparator) {

		return findByUuid_C(
			uuid, companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the object definitions where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectDefinitionModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of object definitions
	 * @param end the upper bound of the range of object definitions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching object definitions
	 */
	@Override
	public List<ObjectDefinition> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<ObjectDefinition> orderByComparator,
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

		List<ObjectDefinition> list = null;

		if (useFinderCache) {
			list = (List<ObjectDefinition>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (ObjectDefinition objectDefinition : list) {
					if (!uuid.equals(objectDefinition.getUuid()) ||
						(companyId != objectDefinition.getCompanyId())) {

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

			sb.append(_SQL_SELECT_OBJECTDEFINITION_WHERE);

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
				sb.append(ObjectDefinitionModelImpl.ORDER_BY_JPQL);
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

				list = (List<ObjectDefinition>)QueryUtil.list(
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
	 * Returns the first object definition in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object definition
	 * @throws NoSuchObjectDefinitionException if a matching object definition could not be found
	 */
	@Override
	public ObjectDefinition findByUuid_C_First(
			String uuid, long companyId,
			OrderByComparator<ObjectDefinition> orderByComparator)
		throws NoSuchObjectDefinitionException {

		ObjectDefinition objectDefinition = fetchByUuid_C_First(
			uuid, companyId, orderByComparator);

		if (objectDefinition != null) {
			return objectDefinition;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append(", companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchObjectDefinitionException(sb.toString());
	}

	/**
	 * Returns the first object definition in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object definition, or <code>null</code> if a matching object definition could not be found
	 */
	@Override
	public ObjectDefinition fetchByUuid_C_First(
		String uuid, long companyId,
		OrderByComparator<ObjectDefinition> orderByComparator) {

		List<ObjectDefinition> list = findByUuid_C(
			uuid, companyId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last object definition in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object definition
	 * @throws NoSuchObjectDefinitionException if a matching object definition could not be found
	 */
	@Override
	public ObjectDefinition findByUuid_C_Last(
			String uuid, long companyId,
			OrderByComparator<ObjectDefinition> orderByComparator)
		throws NoSuchObjectDefinitionException {

		ObjectDefinition objectDefinition = fetchByUuid_C_Last(
			uuid, companyId, orderByComparator);

		if (objectDefinition != null) {
			return objectDefinition;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append(", companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchObjectDefinitionException(sb.toString());
	}

	/**
	 * Returns the last object definition in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object definition, or <code>null</code> if a matching object definition could not be found
	 */
	@Override
	public ObjectDefinition fetchByUuid_C_Last(
		String uuid, long companyId,
		OrderByComparator<ObjectDefinition> orderByComparator) {

		int count = countByUuid_C(uuid, companyId);

		if (count == 0) {
			return null;
		}

		List<ObjectDefinition> list = findByUuid_C(
			uuid, companyId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the object definitions before and after the current object definition in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param objectDefinitionId the primary key of the current object definition
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next object definition
	 * @throws NoSuchObjectDefinitionException if a object definition with the primary key could not be found
	 */
	@Override
	public ObjectDefinition[] findByUuid_C_PrevAndNext(
			long objectDefinitionId, String uuid, long companyId,
			OrderByComparator<ObjectDefinition> orderByComparator)
		throws NoSuchObjectDefinitionException {

		uuid = Objects.toString(uuid, "");

		ObjectDefinition objectDefinition = findByPrimaryKey(
			objectDefinitionId);

		Session session = null;

		try {
			session = openSession();

			ObjectDefinition[] array = new ObjectDefinitionImpl[3];

			array[0] = getByUuid_C_PrevAndNext(
				session, objectDefinition, uuid, companyId, orderByComparator,
				true);

			array[1] = objectDefinition;

			array[2] = getByUuid_C_PrevAndNext(
				session, objectDefinition, uuid, companyId, orderByComparator,
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

	protected ObjectDefinition getByUuid_C_PrevAndNext(
		Session session, ObjectDefinition objectDefinition, String uuid,
		long companyId, OrderByComparator<ObjectDefinition> orderByComparator,
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

		sb.append(_SQL_SELECT_OBJECTDEFINITION_WHERE);

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
			sb.append(ObjectDefinitionModelImpl.ORDER_BY_JPQL);
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
						objectDefinition)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<ObjectDefinition> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the object definitions that the user has permission to view where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching object definitions that the user has permission to view
	 */
	@Override
	public List<ObjectDefinition> filterFindByUuid_C(
		String uuid, long companyId) {

		return filterFindByUuid_C(
			uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the object definitions that the user has permission to view where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectDefinitionModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of object definitions
	 * @param end the upper bound of the range of object definitions (not inclusive)
	 * @return the range of matching object definitions that the user has permission to view
	 */
	@Override
	public List<ObjectDefinition> filterFindByUuid_C(
		String uuid, long companyId, int start, int end) {

		return filterFindByUuid_C(uuid, companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the object definitions that the user has permissions to view where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectDefinitionModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of object definitions
	 * @param end the upper bound of the range of object definitions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching object definitions that the user has permission to view
	 */
	@Override
	public List<ObjectDefinition> filterFindByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<ObjectDefinition> orderByComparator) {

		if (!InlineSQLHelperUtil.isEnabled(companyId, 0)) {
			return findByUuid_C(uuid, companyId, start, end, orderByComparator);
		}

		uuid = Objects.toString(uuid, "");

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				4 + (orderByComparator.getOrderByFields().length * 2));
		}
		else {
			sb = new StringBundler(5);
		}

		if (getDB().isSupportsInlineDistinct()) {
			sb.append(_FILTER_SQL_SELECT_OBJECTDEFINITION_WHERE);
		}
		else {
			sb.append(
				_FILTER_SQL_SELECT_OBJECTDEFINITION_NO_INLINE_DISTINCT_WHERE_1);
		}

		boolean bindUuid = false;

		if (uuid.isEmpty()) {
			sb.append(_FINDER_COLUMN_UUID_C_UUID_3_SQL);
		}
		else {
			bindUuid = true;

			sb.append(_FINDER_COLUMN_UUID_C_UUID_2_SQL);
		}

		sb.append(_FINDER_COLUMN_UUID_C_COMPANYID_2);

		if (!getDB().isSupportsInlineDistinct()) {
			sb.append(
				_FILTER_SQL_SELECT_OBJECTDEFINITION_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			if (getDB().isSupportsInlineDistinct()) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator, true);
			}
			else {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_TABLE, orderByComparator, true);
			}
		}
		else {
			if (getDB().isSupportsInlineDistinct()) {
				sb.append(ObjectDefinitionModelImpl.ORDER_BY_JPQL);
			}
			else {
				sb.append(ObjectDefinitionModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), ObjectDefinition.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				sqlQuery.addEntity(
					_FILTER_ENTITY_ALIAS, ObjectDefinitionImpl.class);
			}
			else {
				sqlQuery.addEntity(
					_FILTER_ENTITY_TABLE, ObjectDefinitionImpl.class);
			}

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			if (bindUuid) {
				queryPos.add(uuid);
			}

			queryPos.add(companyId);

			return (List<ObjectDefinition>)QueryUtil.list(
				sqlQuery, getDialect(), start, end);
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Returns the object definitions before and after the current object definition in the ordered set of object definitions that the user has permission to view where uuid = &#63; and companyId = &#63;.
	 *
	 * @param objectDefinitionId the primary key of the current object definition
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next object definition
	 * @throws NoSuchObjectDefinitionException if a object definition with the primary key could not be found
	 */
	@Override
	public ObjectDefinition[] filterFindByUuid_C_PrevAndNext(
			long objectDefinitionId, String uuid, long companyId,
			OrderByComparator<ObjectDefinition> orderByComparator)
		throws NoSuchObjectDefinitionException {

		if (!InlineSQLHelperUtil.isEnabled(companyId, 0)) {
			return findByUuid_C_PrevAndNext(
				objectDefinitionId, uuid, companyId, orderByComparator);
		}

		uuid = Objects.toString(uuid, "");

		ObjectDefinition objectDefinition = findByPrimaryKey(
			objectDefinitionId);

		Session session = null;

		try {
			session = openSession();

			ObjectDefinition[] array = new ObjectDefinitionImpl[3];

			array[0] = filterGetByUuid_C_PrevAndNext(
				session, objectDefinition, uuid, companyId, orderByComparator,
				true);

			array[1] = objectDefinition;

			array[2] = filterGetByUuid_C_PrevAndNext(
				session, objectDefinition, uuid, companyId, orderByComparator,
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

	protected ObjectDefinition filterGetByUuid_C_PrevAndNext(
		Session session, ObjectDefinition objectDefinition, String uuid,
		long companyId, OrderByComparator<ObjectDefinition> orderByComparator,
		boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				6 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(5);
		}

		if (getDB().isSupportsInlineDistinct()) {
			sb.append(_FILTER_SQL_SELECT_OBJECTDEFINITION_WHERE);
		}
		else {
			sb.append(
				_FILTER_SQL_SELECT_OBJECTDEFINITION_NO_INLINE_DISTINCT_WHERE_1);
		}

		boolean bindUuid = false;

		if (uuid.isEmpty()) {
			sb.append(_FINDER_COLUMN_UUID_C_UUID_3_SQL);
		}
		else {
			bindUuid = true;

			sb.append(_FINDER_COLUMN_UUID_C_UUID_2_SQL);
		}

		sb.append(_FINDER_COLUMN_UUID_C_COMPANYID_2);

		if (!getDB().isSupportsInlineDistinct()) {
			sb.append(
				_FILTER_SQL_SELECT_OBJECTDEFINITION_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			String[] orderByConditionFields =
				orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				sb.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				if (getDB().isSupportsInlineDistinct()) {
					sb.append(
						getColumnName(
							_ORDER_BY_ENTITY_ALIAS, orderByConditionFields[i],
							true));
				}
				else {
					sb.append(
						getColumnName(
							_ORDER_BY_ENTITY_TABLE, orderByConditionFields[i],
							true));
				}

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
				if (getDB().isSupportsInlineDistinct()) {
					sb.append(
						getColumnName(
							_ORDER_BY_ENTITY_ALIAS, orderByFields[i], true));
				}
				else {
					sb.append(
						getColumnName(
							_ORDER_BY_ENTITY_TABLE, orderByFields[i], true));
				}

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
			if (getDB().isSupportsInlineDistinct()) {
				sb.append(ObjectDefinitionModelImpl.ORDER_BY_JPQL);
			}
			else {
				sb.append(ObjectDefinitionModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), ObjectDefinition.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

		sqlQuery.setFirstResult(0);
		sqlQuery.setMaxResults(2);

		if (getDB().isSupportsInlineDistinct()) {
			sqlQuery.addEntity(
				_FILTER_ENTITY_ALIAS, ObjectDefinitionImpl.class);
		}
		else {
			sqlQuery.addEntity(
				_FILTER_ENTITY_TABLE, ObjectDefinitionImpl.class);
		}

		QueryPos queryPos = QueryPos.getInstance(sqlQuery);

		if (bindUuid) {
			queryPos.add(uuid);
		}

		queryPos.add(companyId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						objectDefinition)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<ObjectDefinition> list = sqlQuery.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the object definitions where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	@Override
	public void removeByUuid_C(String uuid, long companyId) {
		for (ObjectDefinition objectDefinition :
				findByUuid_C(
					uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(objectDefinition);
		}
	}

	/**
	 * Returns the number of object definitions where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching object definitions
	 */
	@Override
	public int countByUuid_C(String uuid, long companyId) {
		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = _finderPathCountByUuid_C;

		Object[] finderArgs = new Object[] {uuid, companyId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_OBJECTDEFINITION_WHERE);

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

	/**
	 * Returns the number of object definitions that the user has permission to view where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching object definitions that the user has permission to view
	 */
	@Override
	public int filterCountByUuid_C(String uuid, long companyId) {
		if (!InlineSQLHelperUtil.isEnabled(companyId, 0)) {
			return countByUuid_C(uuid, companyId);
		}

		uuid = Objects.toString(uuid, "");

		StringBundler sb = new StringBundler(3);

		sb.append(_FILTER_SQL_COUNT_OBJECTDEFINITION_WHERE);

		boolean bindUuid = false;

		if (uuid.isEmpty()) {
			sb.append(_FINDER_COLUMN_UUID_C_UUID_3_SQL);
		}
		else {
			bindUuid = true;

			sb.append(_FINDER_COLUMN_UUID_C_UUID_2_SQL);
		}

		sb.append(_FINDER_COLUMN_UUID_C_COMPANYID_2);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), ObjectDefinition.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			sqlQuery.addScalar(
				COUNT_COLUMN_NAME, com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			if (bindUuid) {
				queryPos.add(uuid);
			}

			queryPos.add(companyId);

			Long count = (Long)sqlQuery.uniqueResult();

			return count.intValue();
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	private static final String _FINDER_COLUMN_UUID_C_UUID_2 =
		"objectDefinition.uuid = ? AND ";

	private static final String _FINDER_COLUMN_UUID_C_UUID_3 =
		"(objectDefinition.uuid IS NULL OR objectDefinition.uuid = '') AND ";

	private static final String _FINDER_COLUMN_UUID_C_UUID_2_SQL =
		"objectDefinition.uuid_ = ? AND ";

	private static final String _FINDER_COLUMN_UUID_C_UUID_3_SQL =
		"(objectDefinition.uuid_ IS NULL OR objectDefinition.uuid_ = '') AND ";

	private static final String _FINDER_COLUMN_UUID_C_COMPANYID_2 =
		"objectDefinition.companyId = ?";

	private FinderPath _finderPathWithPaginationFindByCompanyId;
	private FinderPath _finderPathWithoutPaginationFindByCompanyId;
	private FinderPath _finderPathCountByCompanyId;

	/**
	 * Returns all the object definitions where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching object definitions
	 */
	@Override
	public List<ObjectDefinition> findByCompanyId(long companyId) {
		return findByCompanyId(
			companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the object definitions where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectDefinitionModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of object definitions
	 * @param end the upper bound of the range of object definitions (not inclusive)
	 * @return the range of matching object definitions
	 */
	@Override
	public List<ObjectDefinition> findByCompanyId(
		long companyId, int start, int end) {

		return findByCompanyId(companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the object definitions where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectDefinitionModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of object definitions
	 * @param end the upper bound of the range of object definitions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching object definitions
	 */
	@Override
	public List<ObjectDefinition> findByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<ObjectDefinition> orderByComparator) {

		return findByCompanyId(companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the object definitions where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectDefinitionModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of object definitions
	 * @param end the upper bound of the range of object definitions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching object definitions
	 */
	@Override
	public List<ObjectDefinition> findByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<ObjectDefinition> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByCompanyId;
				finderArgs = new Object[] {companyId};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByCompanyId;
			finderArgs = new Object[] {
				companyId, start, end, orderByComparator
			};
		}

		List<ObjectDefinition> list = null;

		if (useFinderCache) {
			list = (List<ObjectDefinition>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (ObjectDefinition objectDefinition : list) {
					if (companyId != objectDefinition.getCompanyId()) {
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

			sb.append(_SQL_SELECT_OBJECTDEFINITION_WHERE);

			sb.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(ObjectDefinitionModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(companyId);

				list = (List<ObjectDefinition>)QueryUtil.list(
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
	 * Returns the first object definition in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object definition
	 * @throws NoSuchObjectDefinitionException if a matching object definition could not be found
	 */
	@Override
	public ObjectDefinition findByCompanyId_First(
			long companyId,
			OrderByComparator<ObjectDefinition> orderByComparator)
		throws NoSuchObjectDefinitionException {

		ObjectDefinition objectDefinition = fetchByCompanyId_First(
			companyId, orderByComparator);

		if (objectDefinition != null) {
			return objectDefinition;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchObjectDefinitionException(sb.toString());
	}

	/**
	 * Returns the first object definition in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object definition, or <code>null</code> if a matching object definition could not be found
	 */
	@Override
	public ObjectDefinition fetchByCompanyId_First(
		long companyId, OrderByComparator<ObjectDefinition> orderByComparator) {

		List<ObjectDefinition> list = findByCompanyId(
			companyId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last object definition in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object definition
	 * @throws NoSuchObjectDefinitionException if a matching object definition could not be found
	 */
	@Override
	public ObjectDefinition findByCompanyId_Last(
			long companyId,
			OrderByComparator<ObjectDefinition> orderByComparator)
		throws NoSuchObjectDefinitionException {

		ObjectDefinition objectDefinition = fetchByCompanyId_Last(
			companyId, orderByComparator);

		if (objectDefinition != null) {
			return objectDefinition;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchObjectDefinitionException(sb.toString());
	}

	/**
	 * Returns the last object definition in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object definition, or <code>null</code> if a matching object definition could not be found
	 */
	@Override
	public ObjectDefinition fetchByCompanyId_Last(
		long companyId, OrderByComparator<ObjectDefinition> orderByComparator) {

		int count = countByCompanyId(companyId);

		if (count == 0) {
			return null;
		}

		List<ObjectDefinition> list = findByCompanyId(
			companyId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the object definitions before and after the current object definition in the ordered set where companyId = &#63;.
	 *
	 * @param objectDefinitionId the primary key of the current object definition
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next object definition
	 * @throws NoSuchObjectDefinitionException if a object definition with the primary key could not be found
	 */
	@Override
	public ObjectDefinition[] findByCompanyId_PrevAndNext(
			long objectDefinitionId, long companyId,
			OrderByComparator<ObjectDefinition> orderByComparator)
		throws NoSuchObjectDefinitionException {

		ObjectDefinition objectDefinition = findByPrimaryKey(
			objectDefinitionId);

		Session session = null;

		try {
			session = openSession();

			ObjectDefinition[] array = new ObjectDefinitionImpl[3];

			array[0] = getByCompanyId_PrevAndNext(
				session, objectDefinition, companyId, orderByComparator, true);

			array[1] = objectDefinition;

			array[2] = getByCompanyId_PrevAndNext(
				session, objectDefinition, companyId, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected ObjectDefinition getByCompanyId_PrevAndNext(
		Session session, ObjectDefinition objectDefinition, long companyId,
		OrderByComparator<ObjectDefinition> orderByComparator,
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

		sb.append(_SQL_SELECT_OBJECTDEFINITION_WHERE);

		sb.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

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
			sb.append(ObjectDefinitionModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(companyId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						objectDefinition)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<ObjectDefinition> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the object definitions that the user has permission to view where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching object definitions that the user has permission to view
	 */
	@Override
	public List<ObjectDefinition> filterFindByCompanyId(long companyId) {
		return filterFindByCompanyId(
			companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the object definitions that the user has permission to view where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectDefinitionModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of object definitions
	 * @param end the upper bound of the range of object definitions (not inclusive)
	 * @return the range of matching object definitions that the user has permission to view
	 */
	@Override
	public List<ObjectDefinition> filterFindByCompanyId(
		long companyId, int start, int end) {

		return filterFindByCompanyId(companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the object definitions that the user has permissions to view where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectDefinitionModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of object definitions
	 * @param end the upper bound of the range of object definitions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching object definitions that the user has permission to view
	 */
	@Override
	public List<ObjectDefinition> filterFindByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<ObjectDefinition> orderByComparator) {

		if (!InlineSQLHelperUtil.isEnabled(companyId, 0)) {
			return findByCompanyId(companyId, start, end, orderByComparator);
		}

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				3 + (orderByComparator.getOrderByFields().length * 2));
		}
		else {
			sb = new StringBundler(4);
		}

		if (getDB().isSupportsInlineDistinct()) {
			sb.append(_FILTER_SQL_SELECT_OBJECTDEFINITION_WHERE);
		}
		else {
			sb.append(
				_FILTER_SQL_SELECT_OBJECTDEFINITION_NO_INLINE_DISTINCT_WHERE_1);
		}

		sb.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

		if (!getDB().isSupportsInlineDistinct()) {
			sb.append(
				_FILTER_SQL_SELECT_OBJECTDEFINITION_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			if (getDB().isSupportsInlineDistinct()) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator, true);
			}
			else {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_TABLE, orderByComparator, true);
			}
		}
		else {
			if (getDB().isSupportsInlineDistinct()) {
				sb.append(ObjectDefinitionModelImpl.ORDER_BY_JPQL);
			}
			else {
				sb.append(ObjectDefinitionModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), ObjectDefinition.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				sqlQuery.addEntity(
					_FILTER_ENTITY_ALIAS, ObjectDefinitionImpl.class);
			}
			else {
				sqlQuery.addEntity(
					_FILTER_ENTITY_TABLE, ObjectDefinitionImpl.class);
			}

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(companyId);

			return (List<ObjectDefinition>)QueryUtil.list(
				sqlQuery, getDialect(), start, end);
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Returns the object definitions before and after the current object definition in the ordered set of object definitions that the user has permission to view where companyId = &#63;.
	 *
	 * @param objectDefinitionId the primary key of the current object definition
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next object definition
	 * @throws NoSuchObjectDefinitionException if a object definition with the primary key could not be found
	 */
	@Override
	public ObjectDefinition[] filterFindByCompanyId_PrevAndNext(
			long objectDefinitionId, long companyId,
			OrderByComparator<ObjectDefinition> orderByComparator)
		throws NoSuchObjectDefinitionException {

		if (!InlineSQLHelperUtil.isEnabled(companyId, 0)) {
			return findByCompanyId_PrevAndNext(
				objectDefinitionId, companyId, orderByComparator);
		}

		ObjectDefinition objectDefinition = findByPrimaryKey(
			objectDefinitionId);

		Session session = null;

		try {
			session = openSession();

			ObjectDefinition[] array = new ObjectDefinitionImpl[3];

			array[0] = filterGetByCompanyId_PrevAndNext(
				session, objectDefinition, companyId, orderByComparator, true);

			array[1] = objectDefinition;

			array[2] = filterGetByCompanyId_PrevAndNext(
				session, objectDefinition, companyId, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected ObjectDefinition filterGetByCompanyId_PrevAndNext(
		Session session, ObjectDefinition objectDefinition, long companyId,
		OrderByComparator<ObjectDefinition> orderByComparator,
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

		if (getDB().isSupportsInlineDistinct()) {
			sb.append(_FILTER_SQL_SELECT_OBJECTDEFINITION_WHERE);
		}
		else {
			sb.append(
				_FILTER_SQL_SELECT_OBJECTDEFINITION_NO_INLINE_DISTINCT_WHERE_1);
		}

		sb.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

		if (!getDB().isSupportsInlineDistinct()) {
			sb.append(
				_FILTER_SQL_SELECT_OBJECTDEFINITION_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			String[] orderByConditionFields =
				orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				sb.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				if (getDB().isSupportsInlineDistinct()) {
					sb.append(
						getColumnName(
							_ORDER_BY_ENTITY_ALIAS, orderByConditionFields[i],
							true));
				}
				else {
					sb.append(
						getColumnName(
							_ORDER_BY_ENTITY_TABLE, orderByConditionFields[i],
							true));
				}

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
				if (getDB().isSupportsInlineDistinct()) {
					sb.append(
						getColumnName(
							_ORDER_BY_ENTITY_ALIAS, orderByFields[i], true));
				}
				else {
					sb.append(
						getColumnName(
							_ORDER_BY_ENTITY_TABLE, orderByFields[i], true));
				}

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
			if (getDB().isSupportsInlineDistinct()) {
				sb.append(ObjectDefinitionModelImpl.ORDER_BY_JPQL);
			}
			else {
				sb.append(ObjectDefinitionModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), ObjectDefinition.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

		sqlQuery.setFirstResult(0);
		sqlQuery.setMaxResults(2);

		if (getDB().isSupportsInlineDistinct()) {
			sqlQuery.addEntity(
				_FILTER_ENTITY_ALIAS, ObjectDefinitionImpl.class);
		}
		else {
			sqlQuery.addEntity(
				_FILTER_ENTITY_TABLE, ObjectDefinitionImpl.class);
		}

		QueryPos queryPos = QueryPos.getInstance(sqlQuery);

		queryPos.add(companyId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						objectDefinition)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<ObjectDefinition> list = sqlQuery.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the object definitions where companyId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 */
	@Override
	public void removeByCompanyId(long companyId) {
		for (ObjectDefinition objectDefinition :
				findByCompanyId(
					companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(objectDefinition);
		}
	}

	/**
	 * Returns the number of object definitions where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching object definitions
	 */
	@Override
	public int countByCompanyId(long companyId) {
		FinderPath finderPath = _finderPathCountByCompanyId;

		Object[] finderArgs = new Object[] {companyId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_OBJECTDEFINITION_WHERE);

			sb.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

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

	/**
	 * Returns the number of object definitions that the user has permission to view where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching object definitions that the user has permission to view
	 */
	@Override
	public int filterCountByCompanyId(long companyId) {
		if (!InlineSQLHelperUtil.isEnabled(companyId, 0)) {
			return countByCompanyId(companyId);
		}

		StringBundler sb = new StringBundler(2);

		sb.append(_FILTER_SQL_COUNT_OBJECTDEFINITION_WHERE);

		sb.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), ObjectDefinition.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			sqlQuery.addScalar(
				COUNT_COLUMN_NAME, com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(companyId);

			Long count = (Long)sqlQuery.uniqueResult();

			return count.intValue();
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	private static final String _FINDER_COLUMN_COMPANYID_COMPANYID_2 =
		"objectDefinition.companyId = ?";

	private FinderPath _finderPathWithPaginationFindBySystem;
	private FinderPath _finderPathWithoutPaginationFindBySystem;
	private FinderPath _finderPathCountBySystem;

	/**
	 * Returns all the object definitions where system = &#63;.
	 *
	 * @param system the system
	 * @return the matching object definitions
	 */
	@Override
	public List<ObjectDefinition> findBySystem(boolean system) {
		return findBySystem(system, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the object definitions where system = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectDefinitionModelImpl</code>.
	 * </p>
	 *
	 * @param system the system
	 * @param start the lower bound of the range of object definitions
	 * @param end the upper bound of the range of object definitions (not inclusive)
	 * @return the range of matching object definitions
	 */
	@Override
	public List<ObjectDefinition> findBySystem(
		boolean system, int start, int end) {

		return findBySystem(system, start, end, null);
	}

	/**
	 * Returns an ordered range of all the object definitions where system = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectDefinitionModelImpl</code>.
	 * </p>
	 *
	 * @param system the system
	 * @param start the lower bound of the range of object definitions
	 * @param end the upper bound of the range of object definitions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching object definitions
	 */
	@Override
	public List<ObjectDefinition> findBySystem(
		boolean system, int start, int end,
		OrderByComparator<ObjectDefinition> orderByComparator) {

		return findBySystem(system, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the object definitions where system = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectDefinitionModelImpl</code>.
	 * </p>
	 *
	 * @param system the system
	 * @param start the lower bound of the range of object definitions
	 * @param end the upper bound of the range of object definitions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching object definitions
	 */
	@Override
	public List<ObjectDefinition> findBySystem(
		boolean system, int start, int end,
		OrderByComparator<ObjectDefinition> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindBySystem;
				finderArgs = new Object[] {system};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindBySystem;
			finderArgs = new Object[] {system, start, end, orderByComparator};
		}

		List<ObjectDefinition> list = null;

		if (useFinderCache) {
			list = (List<ObjectDefinition>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (ObjectDefinition objectDefinition : list) {
					if (system != objectDefinition.isSystem()) {
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

			sb.append(_SQL_SELECT_OBJECTDEFINITION_WHERE);

			sb.append(_FINDER_COLUMN_SYSTEM_SYSTEM_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(ObjectDefinitionModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(system);

				list = (List<ObjectDefinition>)QueryUtil.list(
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
	 * Returns the first object definition in the ordered set where system = &#63;.
	 *
	 * @param system the system
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object definition
	 * @throws NoSuchObjectDefinitionException if a matching object definition could not be found
	 */
	@Override
	public ObjectDefinition findBySystem_First(
			boolean system,
			OrderByComparator<ObjectDefinition> orderByComparator)
		throws NoSuchObjectDefinitionException {

		ObjectDefinition objectDefinition = fetchBySystem_First(
			system, orderByComparator);

		if (objectDefinition != null) {
			return objectDefinition;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("system=");
		sb.append(system);

		sb.append("}");

		throw new NoSuchObjectDefinitionException(sb.toString());
	}

	/**
	 * Returns the first object definition in the ordered set where system = &#63;.
	 *
	 * @param system the system
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object definition, or <code>null</code> if a matching object definition could not be found
	 */
	@Override
	public ObjectDefinition fetchBySystem_First(
		boolean system, OrderByComparator<ObjectDefinition> orderByComparator) {

		List<ObjectDefinition> list = findBySystem(
			system, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last object definition in the ordered set where system = &#63;.
	 *
	 * @param system the system
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object definition
	 * @throws NoSuchObjectDefinitionException if a matching object definition could not be found
	 */
	@Override
	public ObjectDefinition findBySystem_Last(
			boolean system,
			OrderByComparator<ObjectDefinition> orderByComparator)
		throws NoSuchObjectDefinitionException {

		ObjectDefinition objectDefinition = fetchBySystem_Last(
			system, orderByComparator);

		if (objectDefinition != null) {
			return objectDefinition;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("system=");
		sb.append(system);

		sb.append("}");

		throw new NoSuchObjectDefinitionException(sb.toString());
	}

	/**
	 * Returns the last object definition in the ordered set where system = &#63;.
	 *
	 * @param system the system
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object definition, or <code>null</code> if a matching object definition could not be found
	 */
	@Override
	public ObjectDefinition fetchBySystem_Last(
		boolean system, OrderByComparator<ObjectDefinition> orderByComparator) {

		int count = countBySystem(system);

		if (count == 0) {
			return null;
		}

		List<ObjectDefinition> list = findBySystem(
			system, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the object definitions before and after the current object definition in the ordered set where system = &#63;.
	 *
	 * @param objectDefinitionId the primary key of the current object definition
	 * @param system the system
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next object definition
	 * @throws NoSuchObjectDefinitionException if a object definition with the primary key could not be found
	 */
	@Override
	public ObjectDefinition[] findBySystem_PrevAndNext(
			long objectDefinitionId, boolean system,
			OrderByComparator<ObjectDefinition> orderByComparator)
		throws NoSuchObjectDefinitionException {

		ObjectDefinition objectDefinition = findByPrimaryKey(
			objectDefinitionId);

		Session session = null;

		try {
			session = openSession();

			ObjectDefinition[] array = new ObjectDefinitionImpl[3];

			array[0] = getBySystem_PrevAndNext(
				session, objectDefinition, system, orderByComparator, true);

			array[1] = objectDefinition;

			array[2] = getBySystem_PrevAndNext(
				session, objectDefinition, system, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected ObjectDefinition getBySystem_PrevAndNext(
		Session session, ObjectDefinition objectDefinition, boolean system,
		OrderByComparator<ObjectDefinition> orderByComparator,
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

		sb.append(_SQL_SELECT_OBJECTDEFINITION_WHERE);

		sb.append(_FINDER_COLUMN_SYSTEM_SYSTEM_2);

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
			sb.append(ObjectDefinitionModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(system);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						objectDefinition)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<ObjectDefinition> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the object definitions that the user has permission to view where system = &#63;.
	 *
	 * @param system the system
	 * @return the matching object definitions that the user has permission to view
	 */
	@Override
	public List<ObjectDefinition> filterFindBySystem(boolean system) {
		return filterFindBySystem(
			system, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the object definitions that the user has permission to view where system = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectDefinitionModelImpl</code>.
	 * </p>
	 *
	 * @param system the system
	 * @param start the lower bound of the range of object definitions
	 * @param end the upper bound of the range of object definitions (not inclusive)
	 * @return the range of matching object definitions that the user has permission to view
	 */
	@Override
	public List<ObjectDefinition> filterFindBySystem(
		boolean system, int start, int end) {

		return filterFindBySystem(system, start, end, null);
	}

	/**
	 * Returns an ordered range of all the object definitions that the user has permissions to view where system = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectDefinitionModelImpl</code>.
	 * </p>
	 *
	 * @param system the system
	 * @param start the lower bound of the range of object definitions
	 * @param end the upper bound of the range of object definitions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching object definitions that the user has permission to view
	 */
	@Override
	public List<ObjectDefinition> filterFindBySystem(
		boolean system, int start, int end,
		OrderByComparator<ObjectDefinition> orderByComparator) {

		if (!InlineSQLHelperUtil.isEnabled()) {
			return findBySystem(system, start, end, orderByComparator);
		}

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				3 + (orderByComparator.getOrderByFields().length * 2));
		}
		else {
			sb = new StringBundler(4);
		}

		if (getDB().isSupportsInlineDistinct()) {
			sb.append(_FILTER_SQL_SELECT_OBJECTDEFINITION_WHERE);
		}
		else {
			sb.append(
				_FILTER_SQL_SELECT_OBJECTDEFINITION_NO_INLINE_DISTINCT_WHERE_1);
		}

		sb.append(_FINDER_COLUMN_SYSTEM_SYSTEM_2_SQL);

		if (!getDB().isSupportsInlineDistinct()) {
			sb.append(
				_FILTER_SQL_SELECT_OBJECTDEFINITION_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			if (getDB().isSupportsInlineDistinct()) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator, true);
			}
			else {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_TABLE, orderByComparator, true);
			}
		}
		else {
			if (getDB().isSupportsInlineDistinct()) {
				sb.append(ObjectDefinitionModelImpl.ORDER_BY_JPQL);
			}
			else {
				sb.append(ObjectDefinitionModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), ObjectDefinition.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				sqlQuery.addEntity(
					_FILTER_ENTITY_ALIAS, ObjectDefinitionImpl.class);
			}
			else {
				sqlQuery.addEntity(
					_FILTER_ENTITY_TABLE, ObjectDefinitionImpl.class);
			}

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(system);

			return (List<ObjectDefinition>)QueryUtil.list(
				sqlQuery, getDialect(), start, end);
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Returns the object definitions before and after the current object definition in the ordered set of object definitions that the user has permission to view where system = &#63;.
	 *
	 * @param objectDefinitionId the primary key of the current object definition
	 * @param system the system
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next object definition
	 * @throws NoSuchObjectDefinitionException if a object definition with the primary key could not be found
	 */
	@Override
	public ObjectDefinition[] filterFindBySystem_PrevAndNext(
			long objectDefinitionId, boolean system,
			OrderByComparator<ObjectDefinition> orderByComparator)
		throws NoSuchObjectDefinitionException {

		if (!InlineSQLHelperUtil.isEnabled()) {
			return findBySystem_PrevAndNext(
				objectDefinitionId, system, orderByComparator);
		}

		ObjectDefinition objectDefinition = findByPrimaryKey(
			objectDefinitionId);

		Session session = null;

		try {
			session = openSession();

			ObjectDefinition[] array = new ObjectDefinitionImpl[3];

			array[0] = filterGetBySystem_PrevAndNext(
				session, objectDefinition, system, orderByComparator, true);

			array[1] = objectDefinition;

			array[2] = filterGetBySystem_PrevAndNext(
				session, objectDefinition, system, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected ObjectDefinition filterGetBySystem_PrevAndNext(
		Session session, ObjectDefinition objectDefinition, boolean system,
		OrderByComparator<ObjectDefinition> orderByComparator,
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

		if (getDB().isSupportsInlineDistinct()) {
			sb.append(_FILTER_SQL_SELECT_OBJECTDEFINITION_WHERE);
		}
		else {
			sb.append(
				_FILTER_SQL_SELECT_OBJECTDEFINITION_NO_INLINE_DISTINCT_WHERE_1);
		}

		sb.append(_FINDER_COLUMN_SYSTEM_SYSTEM_2_SQL);

		if (!getDB().isSupportsInlineDistinct()) {
			sb.append(
				_FILTER_SQL_SELECT_OBJECTDEFINITION_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			String[] orderByConditionFields =
				orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				sb.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				if (getDB().isSupportsInlineDistinct()) {
					sb.append(
						getColumnName(
							_ORDER_BY_ENTITY_ALIAS, orderByConditionFields[i],
							true));
				}
				else {
					sb.append(
						getColumnName(
							_ORDER_BY_ENTITY_TABLE, orderByConditionFields[i],
							true));
				}

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
				if (getDB().isSupportsInlineDistinct()) {
					sb.append(
						getColumnName(
							_ORDER_BY_ENTITY_ALIAS, orderByFields[i], true));
				}
				else {
					sb.append(
						getColumnName(
							_ORDER_BY_ENTITY_TABLE, orderByFields[i], true));
				}

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
			if (getDB().isSupportsInlineDistinct()) {
				sb.append(ObjectDefinitionModelImpl.ORDER_BY_JPQL);
			}
			else {
				sb.append(ObjectDefinitionModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), ObjectDefinition.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

		sqlQuery.setFirstResult(0);
		sqlQuery.setMaxResults(2);

		if (getDB().isSupportsInlineDistinct()) {
			sqlQuery.addEntity(
				_FILTER_ENTITY_ALIAS, ObjectDefinitionImpl.class);
		}
		else {
			sqlQuery.addEntity(
				_FILTER_ENTITY_TABLE, ObjectDefinitionImpl.class);
		}

		QueryPos queryPos = QueryPos.getInstance(sqlQuery);

		queryPos.add(system);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						objectDefinition)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<ObjectDefinition> list = sqlQuery.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the object definitions where system = &#63; from the database.
	 *
	 * @param system the system
	 */
	@Override
	public void removeBySystem(boolean system) {
		for (ObjectDefinition objectDefinition :
				findBySystem(
					system, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(objectDefinition);
		}
	}

	/**
	 * Returns the number of object definitions where system = &#63;.
	 *
	 * @param system the system
	 * @return the number of matching object definitions
	 */
	@Override
	public int countBySystem(boolean system) {
		FinderPath finderPath = _finderPathCountBySystem;

		Object[] finderArgs = new Object[] {system};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_OBJECTDEFINITION_WHERE);

			sb.append(_FINDER_COLUMN_SYSTEM_SYSTEM_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(system);

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

	/**
	 * Returns the number of object definitions that the user has permission to view where system = &#63;.
	 *
	 * @param system the system
	 * @return the number of matching object definitions that the user has permission to view
	 */
	@Override
	public int filterCountBySystem(boolean system) {
		if (!InlineSQLHelperUtil.isEnabled()) {
			return countBySystem(system);
		}

		StringBundler sb = new StringBundler(2);

		sb.append(_FILTER_SQL_COUNT_OBJECTDEFINITION_WHERE);

		sb.append(_FINDER_COLUMN_SYSTEM_SYSTEM_2_SQL);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), ObjectDefinition.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			sqlQuery.addScalar(
				COUNT_COLUMN_NAME, com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(system);

			Long count = (Long)sqlQuery.uniqueResult();

			return count.intValue();
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	private static final String _FINDER_COLUMN_SYSTEM_SYSTEM_2 =
		"objectDefinition.system = ?";

	private static final String _FINDER_COLUMN_SYSTEM_SYSTEM_2_SQL =
		"objectDefinition.system_ = ?";

	private FinderPath _finderPathFetchByC_C;
	private FinderPath _finderPathCountByC_C;

	/**
	 * Returns the object definition where companyId = &#63; and className = &#63; or throws a <code>NoSuchObjectDefinitionException</code> if it could not be found.
	 *
	 * @param companyId the company ID
	 * @param className the class name
	 * @return the matching object definition
	 * @throws NoSuchObjectDefinitionException if a matching object definition could not be found
	 */
	@Override
	public ObjectDefinition findByC_C(long companyId, String className)
		throws NoSuchObjectDefinitionException {

		ObjectDefinition objectDefinition = fetchByC_C(companyId, className);

		if (objectDefinition == null) {
			StringBundler sb = new StringBundler(6);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("companyId=");
			sb.append(companyId);

			sb.append(", className=");
			sb.append(className);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchObjectDefinitionException(sb.toString());
		}

		return objectDefinition;
	}

	/**
	 * Returns the object definition where companyId = &#63; and className = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param companyId the company ID
	 * @param className the class name
	 * @return the matching object definition, or <code>null</code> if a matching object definition could not be found
	 */
	@Override
	public ObjectDefinition fetchByC_C(long companyId, String className) {
		return fetchByC_C(companyId, className, true);
	}

	/**
	 * Returns the object definition where companyId = &#63; and className = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param companyId the company ID
	 * @param className the class name
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching object definition, or <code>null</code> if a matching object definition could not be found
	 */
	@Override
	public ObjectDefinition fetchByC_C(
		long companyId, String className, boolean useFinderCache) {

		className = Objects.toString(className, "");

		Object[] finderArgs = null;

		if (useFinderCache) {
			finderArgs = new Object[] {companyId, className};
		}

		Object result = null;

		if (useFinderCache) {
			result = finderCache.getResult(_finderPathFetchByC_C, finderArgs);
		}

		if (result instanceof ObjectDefinition) {
			ObjectDefinition objectDefinition = (ObjectDefinition)result;

			if ((companyId != objectDefinition.getCompanyId()) ||
				!Objects.equals(className, objectDefinition.getClassName())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_SELECT_OBJECTDEFINITION_WHERE);

			sb.append(_FINDER_COLUMN_C_C_COMPANYID_2);

			boolean bindClassName = false;

			if (className.isEmpty()) {
				sb.append(_FINDER_COLUMN_C_C_CLASSNAME_3);
			}
			else {
				bindClassName = true;

				sb.append(_FINDER_COLUMN_C_C_CLASSNAME_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(companyId);

				if (bindClassName) {
					queryPos.add(className);
				}

				List<ObjectDefinition> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache) {
						finderCache.putResult(
							_finderPathFetchByC_C, finderArgs, list);
					}
				}
				else {
					if (list.size() > 1) {
						Collections.sort(list, Collections.reverseOrder());

						if (_log.isWarnEnabled()) {
							if (!useFinderCache) {
								finderArgs = new Object[] {
									companyId, className
								};
							}

							_log.warn(
								"ObjectDefinitionPersistenceImpl.fetchByC_C(long, String, boolean) with parameters (" +
									StringUtil.merge(finderArgs) +
										") yields a result set with more than 1 result. This violates the logical unique restriction. There is no order guarantee on which result is returned by this finder.");
						}
					}

					ObjectDefinition objectDefinition = list.get(0);

					result = objectDefinition;

					cacheResult(objectDefinition);
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
			return (ObjectDefinition)result;
		}
	}

	/**
	 * Removes the object definition where companyId = &#63; and className = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param className the class name
	 * @return the object definition that was removed
	 */
	@Override
	public ObjectDefinition removeByC_C(long companyId, String className)
		throws NoSuchObjectDefinitionException {

		ObjectDefinition objectDefinition = findByC_C(companyId, className);

		return remove(objectDefinition);
	}

	/**
	 * Returns the number of object definitions where companyId = &#63; and className = &#63;.
	 *
	 * @param companyId the company ID
	 * @param className the class name
	 * @return the number of matching object definitions
	 */
	@Override
	public int countByC_C(long companyId, String className) {
		className = Objects.toString(className, "");

		FinderPath finderPath = _finderPathCountByC_C;

		Object[] finderArgs = new Object[] {companyId, className};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_OBJECTDEFINITION_WHERE);

			sb.append(_FINDER_COLUMN_C_C_COMPANYID_2);

			boolean bindClassName = false;

			if (className.isEmpty()) {
				sb.append(_FINDER_COLUMN_C_C_CLASSNAME_3);
			}
			else {
				bindClassName = true;

				sb.append(_FINDER_COLUMN_C_C_CLASSNAME_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(companyId);

				if (bindClassName) {
					queryPos.add(className);
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

	private static final String _FINDER_COLUMN_C_C_COMPANYID_2 =
		"objectDefinition.companyId = ? AND ";

	private static final String _FINDER_COLUMN_C_C_CLASSNAME_2 =
		"objectDefinition.className = ?";

	private static final String _FINDER_COLUMN_C_C_CLASSNAME_3 =
		"(objectDefinition.className IS NULL OR objectDefinition.className = '')";

	private FinderPath _finderPathFetchByC_N;
	private FinderPath _finderPathCountByC_N;

	/**
	 * Returns the object definition where companyId = &#63; and name = &#63; or throws a <code>NoSuchObjectDefinitionException</code> if it could not be found.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @return the matching object definition
	 * @throws NoSuchObjectDefinitionException if a matching object definition could not be found
	 */
	@Override
	public ObjectDefinition findByC_N(long companyId, String name)
		throws NoSuchObjectDefinitionException {

		ObjectDefinition objectDefinition = fetchByC_N(companyId, name);

		if (objectDefinition == null) {
			StringBundler sb = new StringBundler(6);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("companyId=");
			sb.append(companyId);

			sb.append(", name=");
			sb.append(name);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchObjectDefinitionException(sb.toString());
		}

		return objectDefinition;
	}

	/**
	 * Returns the object definition where companyId = &#63; and name = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @return the matching object definition, or <code>null</code> if a matching object definition could not be found
	 */
	@Override
	public ObjectDefinition fetchByC_N(long companyId, String name) {
		return fetchByC_N(companyId, name, true);
	}

	/**
	 * Returns the object definition where companyId = &#63; and name = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching object definition, or <code>null</code> if a matching object definition could not be found
	 */
	@Override
	public ObjectDefinition fetchByC_N(
		long companyId, String name, boolean useFinderCache) {

		name = Objects.toString(name, "");

		Object[] finderArgs = null;

		if (useFinderCache) {
			finderArgs = new Object[] {companyId, name};
		}

		Object result = null;

		if (useFinderCache) {
			result = finderCache.getResult(_finderPathFetchByC_N, finderArgs);
		}

		if (result instanceof ObjectDefinition) {
			ObjectDefinition objectDefinition = (ObjectDefinition)result;

			if ((companyId != objectDefinition.getCompanyId()) ||
				!Objects.equals(name, objectDefinition.getName())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_SELECT_OBJECTDEFINITION_WHERE);

			sb.append(_FINDER_COLUMN_C_N_COMPANYID_2);

			boolean bindName = false;

			if (name.isEmpty()) {
				sb.append(_FINDER_COLUMN_C_N_NAME_3);
			}
			else {
				bindName = true;

				sb.append(_FINDER_COLUMN_C_N_NAME_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(companyId);

				if (bindName) {
					queryPos.add(name);
				}

				List<ObjectDefinition> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache) {
						finderCache.putResult(
							_finderPathFetchByC_N, finderArgs, list);
					}
				}
				else {
					if (list.size() > 1) {
						Collections.sort(list, Collections.reverseOrder());

						if (_log.isWarnEnabled()) {
							if (!useFinderCache) {
								finderArgs = new Object[] {companyId, name};
							}

							_log.warn(
								"ObjectDefinitionPersistenceImpl.fetchByC_N(long, String, boolean) with parameters (" +
									StringUtil.merge(finderArgs) +
										") yields a result set with more than 1 result. This violates the logical unique restriction. There is no order guarantee on which result is returned by this finder.");
						}
					}

					ObjectDefinition objectDefinition = list.get(0);

					result = objectDefinition;

					cacheResult(objectDefinition);
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
			return (ObjectDefinition)result;
		}
	}

	/**
	 * Removes the object definition where companyId = &#63; and name = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @return the object definition that was removed
	 */
	@Override
	public ObjectDefinition removeByC_N(long companyId, String name)
		throws NoSuchObjectDefinitionException {

		ObjectDefinition objectDefinition = findByC_N(companyId, name);

		return remove(objectDefinition);
	}

	/**
	 * Returns the number of object definitions where companyId = &#63; and name = &#63;.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @return the number of matching object definitions
	 */
	@Override
	public int countByC_N(long companyId, String name) {
		name = Objects.toString(name, "");

		FinderPath finderPath = _finderPathCountByC_N;

		Object[] finderArgs = new Object[] {companyId, name};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_OBJECTDEFINITION_WHERE);

			sb.append(_FINDER_COLUMN_C_N_COMPANYID_2);

			boolean bindName = false;

			if (name.isEmpty()) {
				sb.append(_FINDER_COLUMN_C_N_NAME_3);
			}
			else {
				bindName = true;

				sb.append(_FINDER_COLUMN_C_N_NAME_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(companyId);

				if (bindName) {
					queryPos.add(name);
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

	private static final String _FINDER_COLUMN_C_N_COMPANYID_2 =
		"objectDefinition.companyId = ? AND ";

	private static final String _FINDER_COLUMN_C_N_NAME_2 =
		"objectDefinition.name = ?";

	private static final String _FINDER_COLUMN_C_N_NAME_3 =
		"(objectDefinition.name IS NULL OR objectDefinition.name = '')";

	private FinderPath _finderPathWithPaginationFindByS_S;
	private FinderPath _finderPathWithoutPaginationFindByS_S;
	private FinderPath _finderPathCountByS_S;

	/**
	 * Returns all the object definitions where system = &#63; and status = &#63;.
	 *
	 * @param system the system
	 * @param status the status
	 * @return the matching object definitions
	 */
	@Override
	public List<ObjectDefinition> findByS_S(boolean system, int status) {
		return findByS_S(
			system, status, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the object definitions where system = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectDefinitionModelImpl</code>.
	 * </p>
	 *
	 * @param system the system
	 * @param status the status
	 * @param start the lower bound of the range of object definitions
	 * @param end the upper bound of the range of object definitions (not inclusive)
	 * @return the range of matching object definitions
	 */
	@Override
	public List<ObjectDefinition> findByS_S(
		boolean system, int status, int start, int end) {

		return findByS_S(system, status, start, end, null);
	}

	/**
	 * Returns an ordered range of all the object definitions where system = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectDefinitionModelImpl</code>.
	 * </p>
	 *
	 * @param system the system
	 * @param status the status
	 * @param start the lower bound of the range of object definitions
	 * @param end the upper bound of the range of object definitions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching object definitions
	 */
	@Override
	public List<ObjectDefinition> findByS_S(
		boolean system, int status, int start, int end,
		OrderByComparator<ObjectDefinition> orderByComparator) {

		return findByS_S(system, status, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the object definitions where system = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectDefinitionModelImpl</code>.
	 * </p>
	 *
	 * @param system the system
	 * @param status the status
	 * @param start the lower bound of the range of object definitions
	 * @param end the upper bound of the range of object definitions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching object definitions
	 */
	@Override
	public List<ObjectDefinition> findByS_S(
		boolean system, int status, int start, int end,
		OrderByComparator<ObjectDefinition> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByS_S;
				finderArgs = new Object[] {system, status};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByS_S;
			finderArgs = new Object[] {
				system, status, start, end, orderByComparator
			};
		}

		List<ObjectDefinition> list = null;

		if (useFinderCache) {
			list = (List<ObjectDefinition>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (ObjectDefinition objectDefinition : list) {
					if ((system != objectDefinition.isSystem()) ||
						(status != objectDefinition.getStatus())) {

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

			sb.append(_SQL_SELECT_OBJECTDEFINITION_WHERE);

			sb.append(_FINDER_COLUMN_S_S_SYSTEM_2);

			sb.append(_FINDER_COLUMN_S_S_STATUS_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(ObjectDefinitionModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(system);

				queryPos.add(status);

				list = (List<ObjectDefinition>)QueryUtil.list(
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
	 * Returns the first object definition in the ordered set where system = &#63; and status = &#63;.
	 *
	 * @param system the system
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object definition
	 * @throws NoSuchObjectDefinitionException if a matching object definition could not be found
	 */
	@Override
	public ObjectDefinition findByS_S_First(
			boolean system, int status,
			OrderByComparator<ObjectDefinition> orderByComparator)
		throws NoSuchObjectDefinitionException {

		ObjectDefinition objectDefinition = fetchByS_S_First(
			system, status, orderByComparator);

		if (objectDefinition != null) {
			return objectDefinition;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("system=");
		sb.append(system);

		sb.append(", status=");
		sb.append(status);

		sb.append("}");

		throw new NoSuchObjectDefinitionException(sb.toString());
	}

	/**
	 * Returns the first object definition in the ordered set where system = &#63; and status = &#63;.
	 *
	 * @param system the system
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object definition, or <code>null</code> if a matching object definition could not be found
	 */
	@Override
	public ObjectDefinition fetchByS_S_First(
		boolean system, int status,
		OrderByComparator<ObjectDefinition> orderByComparator) {

		List<ObjectDefinition> list = findByS_S(
			system, status, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last object definition in the ordered set where system = &#63; and status = &#63;.
	 *
	 * @param system the system
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object definition
	 * @throws NoSuchObjectDefinitionException if a matching object definition could not be found
	 */
	@Override
	public ObjectDefinition findByS_S_Last(
			boolean system, int status,
			OrderByComparator<ObjectDefinition> orderByComparator)
		throws NoSuchObjectDefinitionException {

		ObjectDefinition objectDefinition = fetchByS_S_Last(
			system, status, orderByComparator);

		if (objectDefinition != null) {
			return objectDefinition;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("system=");
		sb.append(system);

		sb.append(", status=");
		sb.append(status);

		sb.append("}");

		throw new NoSuchObjectDefinitionException(sb.toString());
	}

	/**
	 * Returns the last object definition in the ordered set where system = &#63; and status = &#63;.
	 *
	 * @param system the system
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object definition, or <code>null</code> if a matching object definition could not be found
	 */
	@Override
	public ObjectDefinition fetchByS_S_Last(
		boolean system, int status,
		OrderByComparator<ObjectDefinition> orderByComparator) {

		int count = countByS_S(system, status);

		if (count == 0) {
			return null;
		}

		List<ObjectDefinition> list = findByS_S(
			system, status, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the object definitions before and after the current object definition in the ordered set where system = &#63; and status = &#63;.
	 *
	 * @param objectDefinitionId the primary key of the current object definition
	 * @param system the system
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next object definition
	 * @throws NoSuchObjectDefinitionException if a object definition with the primary key could not be found
	 */
	@Override
	public ObjectDefinition[] findByS_S_PrevAndNext(
			long objectDefinitionId, boolean system, int status,
			OrderByComparator<ObjectDefinition> orderByComparator)
		throws NoSuchObjectDefinitionException {

		ObjectDefinition objectDefinition = findByPrimaryKey(
			objectDefinitionId);

		Session session = null;

		try {
			session = openSession();

			ObjectDefinition[] array = new ObjectDefinitionImpl[3];

			array[0] = getByS_S_PrevAndNext(
				session, objectDefinition, system, status, orderByComparator,
				true);

			array[1] = objectDefinition;

			array[2] = getByS_S_PrevAndNext(
				session, objectDefinition, system, status, orderByComparator,
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

	protected ObjectDefinition getByS_S_PrevAndNext(
		Session session, ObjectDefinition objectDefinition, boolean system,
		int status, OrderByComparator<ObjectDefinition> orderByComparator,
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

		sb.append(_SQL_SELECT_OBJECTDEFINITION_WHERE);

		sb.append(_FINDER_COLUMN_S_S_SYSTEM_2);

		sb.append(_FINDER_COLUMN_S_S_STATUS_2);

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
			sb.append(ObjectDefinitionModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(system);

		queryPos.add(status);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						objectDefinition)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<ObjectDefinition> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the object definitions that the user has permission to view where system = &#63; and status = &#63;.
	 *
	 * @param system the system
	 * @param status the status
	 * @return the matching object definitions that the user has permission to view
	 */
	@Override
	public List<ObjectDefinition> filterFindByS_S(boolean system, int status) {
		return filterFindByS_S(
			system, status, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the object definitions that the user has permission to view where system = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectDefinitionModelImpl</code>.
	 * </p>
	 *
	 * @param system the system
	 * @param status the status
	 * @param start the lower bound of the range of object definitions
	 * @param end the upper bound of the range of object definitions (not inclusive)
	 * @return the range of matching object definitions that the user has permission to view
	 */
	@Override
	public List<ObjectDefinition> filterFindByS_S(
		boolean system, int status, int start, int end) {

		return filterFindByS_S(system, status, start, end, null);
	}

	/**
	 * Returns an ordered range of all the object definitions that the user has permissions to view where system = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectDefinitionModelImpl</code>.
	 * </p>
	 *
	 * @param system the system
	 * @param status the status
	 * @param start the lower bound of the range of object definitions
	 * @param end the upper bound of the range of object definitions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching object definitions that the user has permission to view
	 */
	@Override
	public List<ObjectDefinition> filterFindByS_S(
		boolean system, int status, int start, int end,
		OrderByComparator<ObjectDefinition> orderByComparator) {

		if (!InlineSQLHelperUtil.isEnabled()) {
			return findByS_S(system, status, start, end, orderByComparator);
		}

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				4 + (orderByComparator.getOrderByFields().length * 2));
		}
		else {
			sb = new StringBundler(5);
		}

		if (getDB().isSupportsInlineDistinct()) {
			sb.append(_FILTER_SQL_SELECT_OBJECTDEFINITION_WHERE);
		}
		else {
			sb.append(
				_FILTER_SQL_SELECT_OBJECTDEFINITION_NO_INLINE_DISTINCT_WHERE_1);
		}

		sb.append(_FINDER_COLUMN_S_S_SYSTEM_2_SQL);

		sb.append(_FINDER_COLUMN_S_S_STATUS_2);

		if (!getDB().isSupportsInlineDistinct()) {
			sb.append(
				_FILTER_SQL_SELECT_OBJECTDEFINITION_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			if (getDB().isSupportsInlineDistinct()) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator, true);
			}
			else {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_TABLE, orderByComparator, true);
			}
		}
		else {
			if (getDB().isSupportsInlineDistinct()) {
				sb.append(ObjectDefinitionModelImpl.ORDER_BY_JPQL);
			}
			else {
				sb.append(ObjectDefinitionModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), ObjectDefinition.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				sqlQuery.addEntity(
					_FILTER_ENTITY_ALIAS, ObjectDefinitionImpl.class);
			}
			else {
				sqlQuery.addEntity(
					_FILTER_ENTITY_TABLE, ObjectDefinitionImpl.class);
			}

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(system);

			queryPos.add(status);

			return (List<ObjectDefinition>)QueryUtil.list(
				sqlQuery, getDialect(), start, end);
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Returns the object definitions before and after the current object definition in the ordered set of object definitions that the user has permission to view where system = &#63; and status = &#63;.
	 *
	 * @param objectDefinitionId the primary key of the current object definition
	 * @param system the system
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next object definition
	 * @throws NoSuchObjectDefinitionException if a object definition with the primary key could not be found
	 */
	@Override
	public ObjectDefinition[] filterFindByS_S_PrevAndNext(
			long objectDefinitionId, boolean system, int status,
			OrderByComparator<ObjectDefinition> orderByComparator)
		throws NoSuchObjectDefinitionException {

		if (!InlineSQLHelperUtil.isEnabled()) {
			return findByS_S_PrevAndNext(
				objectDefinitionId, system, status, orderByComparator);
		}

		ObjectDefinition objectDefinition = findByPrimaryKey(
			objectDefinitionId);

		Session session = null;

		try {
			session = openSession();

			ObjectDefinition[] array = new ObjectDefinitionImpl[3];

			array[0] = filterGetByS_S_PrevAndNext(
				session, objectDefinition, system, status, orderByComparator,
				true);

			array[1] = objectDefinition;

			array[2] = filterGetByS_S_PrevAndNext(
				session, objectDefinition, system, status, orderByComparator,
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

	protected ObjectDefinition filterGetByS_S_PrevAndNext(
		Session session, ObjectDefinition objectDefinition, boolean system,
		int status, OrderByComparator<ObjectDefinition> orderByComparator,
		boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				6 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(5);
		}

		if (getDB().isSupportsInlineDistinct()) {
			sb.append(_FILTER_SQL_SELECT_OBJECTDEFINITION_WHERE);
		}
		else {
			sb.append(
				_FILTER_SQL_SELECT_OBJECTDEFINITION_NO_INLINE_DISTINCT_WHERE_1);
		}

		sb.append(_FINDER_COLUMN_S_S_SYSTEM_2_SQL);

		sb.append(_FINDER_COLUMN_S_S_STATUS_2);

		if (!getDB().isSupportsInlineDistinct()) {
			sb.append(
				_FILTER_SQL_SELECT_OBJECTDEFINITION_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			String[] orderByConditionFields =
				orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				sb.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				if (getDB().isSupportsInlineDistinct()) {
					sb.append(
						getColumnName(
							_ORDER_BY_ENTITY_ALIAS, orderByConditionFields[i],
							true));
				}
				else {
					sb.append(
						getColumnName(
							_ORDER_BY_ENTITY_TABLE, orderByConditionFields[i],
							true));
				}

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
				if (getDB().isSupportsInlineDistinct()) {
					sb.append(
						getColumnName(
							_ORDER_BY_ENTITY_ALIAS, orderByFields[i], true));
				}
				else {
					sb.append(
						getColumnName(
							_ORDER_BY_ENTITY_TABLE, orderByFields[i], true));
				}

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
			if (getDB().isSupportsInlineDistinct()) {
				sb.append(ObjectDefinitionModelImpl.ORDER_BY_JPQL);
			}
			else {
				sb.append(ObjectDefinitionModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), ObjectDefinition.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

		sqlQuery.setFirstResult(0);
		sqlQuery.setMaxResults(2);

		if (getDB().isSupportsInlineDistinct()) {
			sqlQuery.addEntity(
				_FILTER_ENTITY_ALIAS, ObjectDefinitionImpl.class);
		}
		else {
			sqlQuery.addEntity(
				_FILTER_ENTITY_TABLE, ObjectDefinitionImpl.class);
		}

		QueryPos queryPos = QueryPos.getInstance(sqlQuery);

		queryPos.add(system);

		queryPos.add(status);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						objectDefinition)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<ObjectDefinition> list = sqlQuery.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the object definitions where system = &#63; and status = &#63; from the database.
	 *
	 * @param system the system
	 * @param status the status
	 */
	@Override
	public void removeByS_S(boolean system, int status) {
		for (ObjectDefinition objectDefinition :
				findByS_S(
					system, status, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(objectDefinition);
		}
	}

	/**
	 * Returns the number of object definitions where system = &#63; and status = &#63;.
	 *
	 * @param system the system
	 * @param status the status
	 * @return the number of matching object definitions
	 */
	@Override
	public int countByS_S(boolean system, int status) {
		FinderPath finderPath = _finderPathCountByS_S;

		Object[] finderArgs = new Object[] {system, status};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_OBJECTDEFINITION_WHERE);

			sb.append(_FINDER_COLUMN_S_S_SYSTEM_2);

			sb.append(_FINDER_COLUMN_S_S_STATUS_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(system);

				queryPos.add(status);

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

	/**
	 * Returns the number of object definitions that the user has permission to view where system = &#63; and status = &#63;.
	 *
	 * @param system the system
	 * @param status the status
	 * @return the number of matching object definitions that the user has permission to view
	 */
	@Override
	public int filterCountByS_S(boolean system, int status) {
		if (!InlineSQLHelperUtil.isEnabled()) {
			return countByS_S(system, status);
		}

		StringBundler sb = new StringBundler(3);

		sb.append(_FILTER_SQL_COUNT_OBJECTDEFINITION_WHERE);

		sb.append(_FINDER_COLUMN_S_S_SYSTEM_2_SQL);

		sb.append(_FINDER_COLUMN_S_S_STATUS_2);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), ObjectDefinition.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			sqlQuery.addScalar(
				COUNT_COLUMN_NAME, com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(system);

			queryPos.add(status);

			Long count = (Long)sqlQuery.uniqueResult();

			return count.intValue();
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	private static final String _FINDER_COLUMN_S_S_SYSTEM_2 =
		"objectDefinition.system = ? AND ";

	private static final String _FINDER_COLUMN_S_S_SYSTEM_2_SQL =
		"objectDefinition.system_ = ? AND ";

	private static final String _FINDER_COLUMN_S_S_STATUS_2 =
		"objectDefinition.status = ?";

	private FinderPath _finderPathWithPaginationFindByC_A_S_S;
	private FinderPath _finderPathWithoutPaginationFindByC_A_S_S;
	private FinderPath _finderPathCountByC_A_S_S;

	/**
	 * Returns all the object definitions where companyId = &#63; and active = &#63; and system = &#63; and status = &#63;.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param system the system
	 * @param status the status
	 * @return the matching object definitions
	 */
	@Override
	public List<ObjectDefinition> findByC_A_S_S(
		long companyId, boolean active, boolean system, int status) {

		return findByC_A_S_S(
			companyId, active, system, status, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the object definitions where companyId = &#63; and active = &#63; and system = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectDefinitionModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param system the system
	 * @param status the status
	 * @param start the lower bound of the range of object definitions
	 * @param end the upper bound of the range of object definitions (not inclusive)
	 * @return the range of matching object definitions
	 */
	@Override
	public List<ObjectDefinition> findByC_A_S_S(
		long companyId, boolean active, boolean system, int status, int start,
		int end) {

		return findByC_A_S_S(
			companyId, active, system, status, start, end, null);
	}

	/**
	 * Returns an ordered range of all the object definitions where companyId = &#63; and active = &#63; and system = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectDefinitionModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param system the system
	 * @param status the status
	 * @param start the lower bound of the range of object definitions
	 * @param end the upper bound of the range of object definitions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching object definitions
	 */
	@Override
	public List<ObjectDefinition> findByC_A_S_S(
		long companyId, boolean active, boolean system, int status, int start,
		int end, OrderByComparator<ObjectDefinition> orderByComparator) {

		return findByC_A_S_S(
			companyId, active, system, status, start, end, orderByComparator,
			true);
	}

	/**
	 * Returns an ordered range of all the object definitions where companyId = &#63; and active = &#63; and system = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectDefinitionModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param system the system
	 * @param status the status
	 * @param start the lower bound of the range of object definitions
	 * @param end the upper bound of the range of object definitions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching object definitions
	 */
	@Override
	public List<ObjectDefinition> findByC_A_S_S(
		long companyId, boolean active, boolean system, int status, int start,
		int end, OrderByComparator<ObjectDefinition> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByC_A_S_S;
				finderArgs = new Object[] {companyId, active, system, status};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByC_A_S_S;
			finderArgs = new Object[] {
				companyId, active, system, status, start, end, orderByComparator
			};
		}

		List<ObjectDefinition> list = null;

		if (useFinderCache) {
			list = (List<ObjectDefinition>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (ObjectDefinition objectDefinition : list) {
					if ((companyId != objectDefinition.getCompanyId()) ||
						(active != objectDefinition.isActive()) ||
						(system != objectDefinition.isSystem()) ||
						(status != objectDefinition.getStatus())) {

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
					6 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				sb = new StringBundler(6);
			}

			sb.append(_SQL_SELECT_OBJECTDEFINITION_WHERE);

			sb.append(_FINDER_COLUMN_C_A_S_S_COMPANYID_2);

			sb.append(_FINDER_COLUMN_C_A_S_S_ACTIVE_2);

			sb.append(_FINDER_COLUMN_C_A_S_S_SYSTEM_2);

			sb.append(_FINDER_COLUMN_C_A_S_S_STATUS_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(ObjectDefinitionModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(companyId);

				queryPos.add(active);

				queryPos.add(system);

				queryPos.add(status);

				list = (List<ObjectDefinition>)QueryUtil.list(
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
	 * Returns the first object definition in the ordered set where companyId = &#63; and active = &#63; and system = &#63; and status = &#63;.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param system the system
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object definition
	 * @throws NoSuchObjectDefinitionException if a matching object definition could not be found
	 */
	@Override
	public ObjectDefinition findByC_A_S_S_First(
			long companyId, boolean active, boolean system, int status,
			OrderByComparator<ObjectDefinition> orderByComparator)
		throws NoSuchObjectDefinitionException {

		ObjectDefinition objectDefinition = fetchByC_A_S_S_First(
			companyId, active, system, status, orderByComparator);

		if (objectDefinition != null) {
			return objectDefinition;
		}

		StringBundler sb = new StringBundler(10);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("companyId=");
		sb.append(companyId);

		sb.append(", active=");
		sb.append(active);

		sb.append(", system=");
		sb.append(system);

		sb.append(", status=");
		sb.append(status);

		sb.append("}");

		throw new NoSuchObjectDefinitionException(sb.toString());
	}

	/**
	 * Returns the first object definition in the ordered set where companyId = &#63; and active = &#63; and system = &#63; and status = &#63;.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param system the system
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object definition, or <code>null</code> if a matching object definition could not be found
	 */
	@Override
	public ObjectDefinition fetchByC_A_S_S_First(
		long companyId, boolean active, boolean system, int status,
		OrderByComparator<ObjectDefinition> orderByComparator) {

		List<ObjectDefinition> list = findByC_A_S_S(
			companyId, active, system, status, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last object definition in the ordered set where companyId = &#63; and active = &#63; and system = &#63; and status = &#63;.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param system the system
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object definition
	 * @throws NoSuchObjectDefinitionException if a matching object definition could not be found
	 */
	@Override
	public ObjectDefinition findByC_A_S_S_Last(
			long companyId, boolean active, boolean system, int status,
			OrderByComparator<ObjectDefinition> orderByComparator)
		throws NoSuchObjectDefinitionException {

		ObjectDefinition objectDefinition = fetchByC_A_S_S_Last(
			companyId, active, system, status, orderByComparator);

		if (objectDefinition != null) {
			return objectDefinition;
		}

		StringBundler sb = new StringBundler(10);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("companyId=");
		sb.append(companyId);

		sb.append(", active=");
		sb.append(active);

		sb.append(", system=");
		sb.append(system);

		sb.append(", status=");
		sb.append(status);

		sb.append("}");

		throw new NoSuchObjectDefinitionException(sb.toString());
	}

	/**
	 * Returns the last object definition in the ordered set where companyId = &#63; and active = &#63; and system = &#63; and status = &#63;.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param system the system
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object definition, or <code>null</code> if a matching object definition could not be found
	 */
	@Override
	public ObjectDefinition fetchByC_A_S_S_Last(
		long companyId, boolean active, boolean system, int status,
		OrderByComparator<ObjectDefinition> orderByComparator) {

		int count = countByC_A_S_S(companyId, active, system, status);

		if (count == 0) {
			return null;
		}

		List<ObjectDefinition> list = findByC_A_S_S(
			companyId, active, system, status, count - 1, count,
			orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the object definitions before and after the current object definition in the ordered set where companyId = &#63; and active = &#63; and system = &#63; and status = &#63;.
	 *
	 * @param objectDefinitionId the primary key of the current object definition
	 * @param companyId the company ID
	 * @param active the active
	 * @param system the system
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next object definition
	 * @throws NoSuchObjectDefinitionException if a object definition with the primary key could not be found
	 */
	@Override
	public ObjectDefinition[] findByC_A_S_S_PrevAndNext(
			long objectDefinitionId, long companyId, boolean active,
			boolean system, int status,
			OrderByComparator<ObjectDefinition> orderByComparator)
		throws NoSuchObjectDefinitionException {

		ObjectDefinition objectDefinition = findByPrimaryKey(
			objectDefinitionId);

		Session session = null;

		try {
			session = openSession();

			ObjectDefinition[] array = new ObjectDefinitionImpl[3];

			array[0] = getByC_A_S_S_PrevAndNext(
				session, objectDefinition, companyId, active, system, status,
				orderByComparator, true);

			array[1] = objectDefinition;

			array[2] = getByC_A_S_S_PrevAndNext(
				session, objectDefinition, companyId, active, system, status,
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

	protected ObjectDefinition getByC_A_S_S_PrevAndNext(
		Session session, ObjectDefinition objectDefinition, long companyId,
		boolean active, boolean system, int status,
		OrderByComparator<ObjectDefinition> orderByComparator,
		boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				7 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(6);
		}

		sb.append(_SQL_SELECT_OBJECTDEFINITION_WHERE);

		sb.append(_FINDER_COLUMN_C_A_S_S_COMPANYID_2);

		sb.append(_FINDER_COLUMN_C_A_S_S_ACTIVE_2);

		sb.append(_FINDER_COLUMN_C_A_S_S_SYSTEM_2);

		sb.append(_FINDER_COLUMN_C_A_S_S_STATUS_2);

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
			sb.append(ObjectDefinitionModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(companyId);

		queryPos.add(active);

		queryPos.add(system);

		queryPos.add(status);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						objectDefinition)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<ObjectDefinition> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the object definitions that the user has permission to view where companyId = &#63; and active = &#63; and system = &#63; and status = &#63;.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param system the system
	 * @param status the status
	 * @return the matching object definitions that the user has permission to view
	 */
	@Override
	public List<ObjectDefinition> filterFindByC_A_S_S(
		long companyId, boolean active, boolean system, int status) {

		return filterFindByC_A_S_S(
			companyId, active, system, status, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the object definitions that the user has permission to view where companyId = &#63; and active = &#63; and system = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectDefinitionModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param system the system
	 * @param status the status
	 * @param start the lower bound of the range of object definitions
	 * @param end the upper bound of the range of object definitions (not inclusive)
	 * @return the range of matching object definitions that the user has permission to view
	 */
	@Override
	public List<ObjectDefinition> filterFindByC_A_S_S(
		long companyId, boolean active, boolean system, int status, int start,
		int end) {

		return filterFindByC_A_S_S(
			companyId, active, system, status, start, end, null);
	}

	/**
	 * Returns an ordered range of all the object definitions that the user has permissions to view where companyId = &#63; and active = &#63; and system = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectDefinitionModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param system the system
	 * @param status the status
	 * @param start the lower bound of the range of object definitions
	 * @param end the upper bound of the range of object definitions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching object definitions that the user has permission to view
	 */
	@Override
	public List<ObjectDefinition> filterFindByC_A_S_S(
		long companyId, boolean active, boolean system, int status, int start,
		int end, OrderByComparator<ObjectDefinition> orderByComparator) {

		if (!InlineSQLHelperUtil.isEnabled(companyId, 0)) {
			return findByC_A_S_S(
				companyId, active, system, status, start, end,
				orderByComparator);
		}

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				6 + (orderByComparator.getOrderByFields().length * 2));
		}
		else {
			sb = new StringBundler(7);
		}

		if (getDB().isSupportsInlineDistinct()) {
			sb.append(_FILTER_SQL_SELECT_OBJECTDEFINITION_WHERE);
		}
		else {
			sb.append(
				_FILTER_SQL_SELECT_OBJECTDEFINITION_NO_INLINE_DISTINCT_WHERE_1);
		}

		sb.append(_FINDER_COLUMN_C_A_S_S_COMPANYID_2);

		sb.append(_FINDER_COLUMN_C_A_S_S_ACTIVE_2_SQL);

		sb.append(_FINDER_COLUMN_C_A_S_S_SYSTEM_2_SQL);

		sb.append(_FINDER_COLUMN_C_A_S_S_STATUS_2);

		if (!getDB().isSupportsInlineDistinct()) {
			sb.append(
				_FILTER_SQL_SELECT_OBJECTDEFINITION_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			if (getDB().isSupportsInlineDistinct()) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator, true);
			}
			else {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_TABLE, orderByComparator, true);
			}
		}
		else {
			if (getDB().isSupportsInlineDistinct()) {
				sb.append(ObjectDefinitionModelImpl.ORDER_BY_JPQL);
			}
			else {
				sb.append(ObjectDefinitionModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), ObjectDefinition.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				sqlQuery.addEntity(
					_FILTER_ENTITY_ALIAS, ObjectDefinitionImpl.class);
			}
			else {
				sqlQuery.addEntity(
					_FILTER_ENTITY_TABLE, ObjectDefinitionImpl.class);
			}

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(companyId);

			queryPos.add(active);

			queryPos.add(system);

			queryPos.add(status);

			return (List<ObjectDefinition>)QueryUtil.list(
				sqlQuery, getDialect(), start, end);
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Returns the object definitions before and after the current object definition in the ordered set of object definitions that the user has permission to view where companyId = &#63; and active = &#63; and system = &#63; and status = &#63;.
	 *
	 * @param objectDefinitionId the primary key of the current object definition
	 * @param companyId the company ID
	 * @param active the active
	 * @param system the system
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next object definition
	 * @throws NoSuchObjectDefinitionException if a object definition with the primary key could not be found
	 */
	@Override
	public ObjectDefinition[] filterFindByC_A_S_S_PrevAndNext(
			long objectDefinitionId, long companyId, boolean active,
			boolean system, int status,
			OrderByComparator<ObjectDefinition> orderByComparator)
		throws NoSuchObjectDefinitionException {

		if (!InlineSQLHelperUtil.isEnabled(companyId, 0)) {
			return findByC_A_S_S_PrevAndNext(
				objectDefinitionId, companyId, active, system, status,
				orderByComparator);
		}

		ObjectDefinition objectDefinition = findByPrimaryKey(
			objectDefinitionId);

		Session session = null;

		try {
			session = openSession();

			ObjectDefinition[] array = new ObjectDefinitionImpl[3];

			array[0] = filterGetByC_A_S_S_PrevAndNext(
				session, objectDefinition, companyId, active, system, status,
				orderByComparator, true);

			array[1] = objectDefinition;

			array[2] = filterGetByC_A_S_S_PrevAndNext(
				session, objectDefinition, companyId, active, system, status,
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

	protected ObjectDefinition filterGetByC_A_S_S_PrevAndNext(
		Session session, ObjectDefinition objectDefinition, long companyId,
		boolean active, boolean system, int status,
		OrderByComparator<ObjectDefinition> orderByComparator,
		boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				8 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(7);
		}

		if (getDB().isSupportsInlineDistinct()) {
			sb.append(_FILTER_SQL_SELECT_OBJECTDEFINITION_WHERE);
		}
		else {
			sb.append(
				_FILTER_SQL_SELECT_OBJECTDEFINITION_NO_INLINE_DISTINCT_WHERE_1);
		}

		sb.append(_FINDER_COLUMN_C_A_S_S_COMPANYID_2);

		sb.append(_FINDER_COLUMN_C_A_S_S_ACTIVE_2_SQL);

		sb.append(_FINDER_COLUMN_C_A_S_S_SYSTEM_2_SQL);

		sb.append(_FINDER_COLUMN_C_A_S_S_STATUS_2);

		if (!getDB().isSupportsInlineDistinct()) {
			sb.append(
				_FILTER_SQL_SELECT_OBJECTDEFINITION_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			String[] orderByConditionFields =
				orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				sb.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				if (getDB().isSupportsInlineDistinct()) {
					sb.append(
						getColumnName(
							_ORDER_BY_ENTITY_ALIAS, orderByConditionFields[i],
							true));
				}
				else {
					sb.append(
						getColumnName(
							_ORDER_BY_ENTITY_TABLE, orderByConditionFields[i],
							true));
				}

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
				if (getDB().isSupportsInlineDistinct()) {
					sb.append(
						getColumnName(
							_ORDER_BY_ENTITY_ALIAS, orderByFields[i], true));
				}
				else {
					sb.append(
						getColumnName(
							_ORDER_BY_ENTITY_TABLE, orderByFields[i], true));
				}

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
			if (getDB().isSupportsInlineDistinct()) {
				sb.append(ObjectDefinitionModelImpl.ORDER_BY_JPQL);
			}
			else {
				sb.append(ObjectDefinitionModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), ObjectDefinition.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

		sqlQuery.setFirstResult(0);
		sqlQuery.setMaxResults(2);

		if (getDB().isSupportsInlineDistinct()) {
			sqlQuery.addEntity(
				_FILTER_ENTITY_ALIAS, ObjectDefinitionImpl.class);
		}
		else {
			sqlQuery.addEntity(
				_FILTER_ENTITY_TABLE, ObjectDefinitionImpl.class);
		}

		QueryPos queryPos = QueryPos.getInstance(sqlQuery);

		queryPos.add(companyId);

		queryPos.add(active);

		queryPos.add(system);

		queryPos.add(status);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						objectDefinition)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<ObjectDefinition> list = sqlQuery.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the object definitions where companyId = &#63; and active = &#63; and system = &#63; and status = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param system the system
	 * @param status the status
	 */
	@Override
	public void removeByC_A_S_S(
		long companyId, boolean active, boolean system, int status) {

		for (ObjectDefinition objectDefinition :
				findByC_A_S_S(
					companyId, active, system, status, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			remove(objectDefinition);
		}
	}

	/**
	 * Returns the number of object definitions where companyId = &#63; and active = &#63; and system = &#63; and status = &#63;.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param system the system
	 * @param status the status
	 * @return the number of matching object definitions
	 */
	@Override
	public int countByC_A_S_S(
		long companyId, boolean active, boolean system, int status) {

		FinderPath finderPath = _finderPathCountByC_A_S_S;

		Object[] finderArgs = new Object[] {companyId, active, system, status};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(5);

			sb.append(_SQL_COUNT_OBJECTDEFINITION_WHERE);

			sb.append(_FINDER_COLUMN_C_A_S_S_COMPANYID_2);

			sb.append(_FINDER_COLUMN_C_A_S_S_ACTIVE_2);

			sb.append(_FINDER_COLUMN_C_A_S_S_SYSTEM_2);

			sb.append(_FINDER_COLUMN_C_A_S_S_STATUS_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(companyId);

				queryPos.add(active);

				queryPos.add(system);

				queryPos.add(status);

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

	/**
	 * Returns the number of object definitions that the user has permission to view where companyId = &#63; and active = &#63; and system = &#63; and status = &#63;.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param system the system
	 * @param status the status
	 * @return the number of matching object definitions that the user has permission to view
	 */
	@Override
	public int filterCountByC_A_S_S(
		long companyId, boolean active, boolean system, int status) {

		if (!InlineSQLHelperUtil.isEnabled(companyId, 0)) {
			return countByC_A_S_S(companyId, active, system, status);
		}

		StringBundler sb = new StringBundler(5);

		sb.append(_FILTER_SQL_COUNT_OBJECTDEFINITION_WHERE);

		sb.append(_FINDER_COLUMN_C_A_S_S_COMPANYID_2);

		sb.append(_FINDER_COLUMN_C_A_S_S_ACTIVE_2_SQL);

		sb.append(_FINDER_COLUMN_C_A_S_S_SYSTEM_2_SQL);

		sb.append(_FINDER_COLUMN_C_A_S_S_STATUS_2);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(
			sb.toString(), ObjectDefinition.class.getName(),
			_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			sqlQuery.addScalar(
				COUNT_COLUMN_NAME, com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(companyId);

			queryPos.add(active);

			queryPos.add(system);

			queryPos.add(status);

			Long count = (Long)sqlQuery.uniqueResult();

			return count.intValue();
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	private static final String _FINDER_COLUMN_C_A_S_S_COMPANYID_2 =
		"objectDefinition.companyId = ? AND ";

	private static final String _FINDER_COLUMN_C_A_S_S_ACTIVE_2 =
		"objectDefinition.active = ? AND ";

	private static final String _FINDER_COLUMN_C_A_S_S_ACTIVE_2_SQL =
		"objectDefinition.active_ = ? AND ";

	private static final String _FINDER_COLUMN_C_A_S_S_SYSTEM_2 =
		"objectDefinition.system = ? AND ";

	private static final String _FINDER_COLUMN_C_A_S_S_SYSTEM_2_SQL =
		"objectDefinition.system_ = ? AND ";

	private static final String _FINDER_COLUMN_C_A_S_S_STATUS_2 =
		"objectDefinition.status = ?";

	public ObjectDefinitionPersistenceImpl() {
		Map<String, String> dbColumnNames = new HashMap<String, String>();

		dbColumnNames.put("uuid", "uuid_");
		dbColumnNames.put("active", "active_");
		dbColumnNames.put("system", "system_");

		setDBColumnNames(dbColumnNames);

		setModelClass(ObjectDefinition.class);

		setModelImplClass(ObjectDefinitionImpl.class);
		setModelPKClass(long.class);

		setTable(ObjectDefinitionTable.INSTANCE);
	}

	/**
	 * Caches the object definition in the entity cache if it is enabled.
	 *
	 * @param objectDefinition the object definition
	 */
	@Override
	public void cacheResult(ObjectDefinition objectDefinition) {
		entityCache.putResult(
			ObjectDefinitionImpl.class, objectDefinition.getPrimaryKey(),
			objectDefinition);

		finderCache.putResult(
			_finderPathFetchByC_C,
			new Object[] {
				objectDefinition.getCompanyId(), objectDefinition.getClassName()
			},
			objectDefinition);

		finderCache.putResult(
			_finderPathFetchByC_N,
			new Object[] {
				objectDefinition.getCompanyId(), objectDefinition.getName()
			},
			objectDefinition);
	}

	private int _valueObjectFinderCacheListThreshold;

	/**
	 * Caches the object definitions in the entity cache if it is enabled.
	 *
	 * @param objectDefinitions the object definitions
	 */
	@Override
	public void cacheResult(List<ObjectDefinition> objectDefinitions) {
		if ((_valueObjectFinderCacheListThreshold == 0) ||
			((_valueObjectFinderCacheListThreshold > 0) &&
			 (objectDefinitions.size() >
				 _valueObjectFinderCacheListThreshold))) {

			return;
		}

		for (ObjectDefinition objectDefinition : objectDefinitions) {
			if (entityCache.getResult(
					ObjectDefinitionImpl.class,
					objectDefinition.getPrimaryKey()) == null) {

				cacheResult(objectDefinition);
			}
		}
	}

	/**
	 * Clears the cache for all object definitions.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(ObjectDefinitionImpl.class);

		finderCache.clearCache(ObjectDefinitionImpl.class);
	}

	/**
	 * Clears the cache for the object definition.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(ObjectDefinition objectDefinition) {
		entityCache.removeResult(ObjectDefinitionImpl.class, objectDefinition);
	}

	@Override
	public void clearCache(List<ObjectDefinition> objectDefinitions) {
		for (ObjectDefinition objectDefinition : objectDefinitions) {
			entityCache.removeResult(
				ObjectDefinitionImpl.class, objectDefinition);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(ObjectDefinitionImpl.class);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(ObjectDefinitionImpl.class, primaryKey);
		}
	}

	protected void cacheUniqueFindersCache(
		ObjectDefinitionModelImpl objectDefinitionModelImpl) {

		Object[] args = new Object[] {
			objectDefinitionModelImpl.getCompanyId(),
			objectDefinitionModelImpl.getClassName()
		};

		finderCache.putResult(_finderPathCountByC_C, args, Long.valueOf(1));
		finderCache.putResult(
			_finderPathFetchByC_C, args, objectDefinitionModelImpl);

		args = new Object[] {
			objectDefinitionModelImpl.getCompanyId(),
			objectDefinitionModelImpl.getName()
		};

		finderCache.putResult(_finderPathCountByC_N, args, Long.valueOf(1));
		finderCache.putResult(
			_finderPathFetchByC_N, args, objectDefinitionModelImpl);
	}

	/**
	 * Creates a new object definition with the primary key. Does not add the object definition to the database.
	 *
	 * @param objectDefinitionId the primary key for the new object definition
	 * @return the new object definition
	 */
	@Override
	public ObjectDefinition create(long objectDefinitionId) {
		ObjectDefinition objectDefinition = new ObjectDefinitionImpl();

		objectDefinition.setNew(true);
		objectDefinition.setPrimaryKey(objectDefinitionId);

		String uuid = PortalUUIDUtil.generate();

		objectDefinition.setUuid(uuid);

		objectDefinition.setCompanyId(CompanyThreadLocal.getCompanyId());

		return objectDefinition;
	}

	/**
	 * Removes the object definition with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param objectDefinitionId the primary key of the object definition
	 * @return the object definition that was removed
	 * @throws NoSuchObjectDefinitionException if a object definition with the primary key could not be found
	 */
	@Override
	public ObjectDefinition remove(long objectDefinitionId)
		throws NoSuchObjectDefinitionException {

		return remove((Serializable)objectDefinitionId);
	}

	/**
	 * Removes the object definition with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the object definition
	 * @return the object definition that was removed
	 * @throws NoSuchObjectDefinitionException if a object definition with the primary key could not be found
	 */
	@Override
	public ObjectDefinition remove(Serializable primaryKey)
		throws NoSuchObjectDefinitionException {

		Session session = null;

		try {
			session = openSession();

			ObjectDefinition objectDefinition = (ObjectDefinition)session.get(
				ObjectDefinitionImpl.class, primaryKey);

			if (objectDefinition == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchObjectDefinitionException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(objectDefinition);
		}
		catch (NoSuchObjectDefinitionException noSuchEntityException) {
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
	protected ObjectDefinition removeImpl(ObjectDefinition objectDefinition) {
		Session session = null;

		try {
			session = openSession();

			if (!session.contains(objectDefinition)) {
				objectDefinition = (ObjectDefinition)session.get(
					ObjectDefinitionImpl.class,
					objectDefinition.getPrimaryKeyObj());
			}

			if (objectDefinition != null) {
				session.delete(objectDefinition);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (objectDefinition != null) {
			clearCache(objectDefinition);
		}

		return objectDefinition;
	}

	@Override
	public ObjectDefinition updateImpl(ObjectDefinition objectDefinition) {
		boolean isNew = objectDefinition.isNew();

		if (!(objectDefinition instanceof ObjectDefinitionModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(objectDefinition.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					objectDefinition);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in objectDefinition proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom ObjectDefinition implementation " +
					objectDefinition.getClass());
		}

		ObjectDefinitionModelImpl objectDefinitionModelImpl =
			(ObjectDefinitionModelImpl)objectDefinition;

		if (Validator.isNull(objectDefinition.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			objectDefinition.setUuid(uuid);
		}

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Date date = new Date();

		if (isNew && (objectDefinition.getCreateDate() == null)) {
			if (serviceContext == null) {
				objectDefinition.setCreateDate(date);
			}
			else {
				objectDefinition.setCreateDate(
					serviceContext.getCreateDate(date));
			}
		}

		if (!objectDefinitionModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				objectDefinition.setModifiedDate(date);
			}
			else {
				objectDefinition.setModifiedDate(
					serviceContext.getModifiedDate(date));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (isNew) {
				session.save(objectDefinition);
			}
			else {
				objectDefinition = (ObjectDefinition)session.merge(
					objectDefinition);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		entityCache.putResult(
			ObjectDefinitionImpl.class, objectDefinitionModelImpl, false, true);

		cacheUniqueFindersCache(objectDefinitionModelImpl);

		if (isNew) {
			objectDefinition.setNew(false);
		}

		objectDefinition.resetOriginalValues();

		return objectDefinition;
	}

	/**
	 * Returns the object definition with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the object definition
	 * @return the object definition
	 * @throws NoSuchObjectDefinitionException if a object definition with the primary key could not be found
	 */
	@Override
	public ObjectDefinition findByPrimaryKey(Serializable primaryKey)
		throws NoSuchObjectDefinitionException {

		ObjectDefinition objectDefinition = fetchByPrimaryKey(primaryKey);

		if (objectDefinition == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchObjectDefinitionException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return objectDefinition;
	}

	/**
	 * Returns the object definition with the primary key or throws a <code>NoSuchObjectDefinitionException</code> if it could not be found.
	 *
	 * @param objectDefinitionId the primary key of the object definition
	 * @return the object definition
	 * @throws NoSuchObjectDefinitionException if a object definition with the primary key could not be found
	 */
	@Override
	public ObjectDefinition findByPrimaryKey(long objectDefinitionId)
		throws NoSuchObjectDefinitionException {

		return findByPrimaryKey((Serializable)objectDefinitionId);
	}

	/**
	 * Returns the object definition with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param objectDefinitionId the primary key of the object definition
	 * @return the object definition, or <code>null</code> if a object definition with the primary key could not be found
	 */
	@Override
	public ObjectDefinition fetchByPrimaryKey(long objectDefinitionId) {
		return fetchByPrimaryKey((Serializable)objectDefinitionId);
	}

	/**
	 * Returns all the object definitions.
	 *
	 * @return the object definitions
	 */
	@Override
	public List<ObjectDefinition> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the object definitions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectDefinitionModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of object definitions
	 * @param end the upper bound of the range of object definitions (not inclusive)
	 * @return the range of object definitions
	 */
	@Override
	public List<ObjectDefinition> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the object definitions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectDefinitionModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of object definitions
	 * @param end the upper bound of the range of object definitions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of object definitions
	 */
	@Override
	public List<ObjectDefinition> findAll(
		int start, int end,
		OrderByComparator<ObjectDefinition> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the object definitions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectDefinitionModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of object definitions
	 * @param end the upper bound of the range of object definitions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of object definitions
	 */
	@Override
	public List<ObjectDefinition> findAll(
		int start, int end,
		OrderByComparator<ObjectDefinition> orderByComparator,
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

		List<ObjectDefinition> list = null;

		if (useFinderCache) {
			list = (List<ObjectDefinition>)finderCache.getResult(
				finderPath, finderArgs);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_OBJECTDEFINITION);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_OBJECTDEFINITION;

				sql = sql.concat(ObjectDefinitionModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<ObjectDefinition>)QueryUtil.list(
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
	 * Removes all the object definitions from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (ObjectDefinition objectDefinition : findAll()) {
			remove(objectDefinition);
		}
	}

	/**
	 * Returns the number of object definitions.
	 *
	 * @return the number of object definitions
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(
			_finderPathCountAll, FINDER_ARGS_EMPTY);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(_SQL_COUNT_OBJECTDEFINITION);

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
		return "objectDefinitionId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_OBJECTDEFINITION;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return ObjectDefinitionModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the object definition persistence.
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

		_finderPathWithPaginationFindByCompanyId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByCompanyId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"companyId"}, true);

		_finderPathWithoutPaginationFindByCompanyId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByCompanyId",
			new String[] {Long.class.getName()}, new String[] {"companyId"},
			true);

		_finderPathCountByCompanyId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByCompanyId",
			new String[] {Long.class.getName()}, new String[] {"companyId"},
			false);

		_finderPathWithPaginationFindBySystem = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findBySystem",
			new String[] {
				Boolean.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"system_"}, true);

		_finderPathWithoutPaginationFindBySystem = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findBySystem",
			new String[] {Boolean.class.getName()}, new String[] {"system_"},
			true);

		_finderPathCountBySystem = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countBySystem",
			new String[] {Boolean.class.getName()}, new String[] {"system_"},
			false);

		_finderPathFetchByC_C = new FinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByC_C",
			new String[] {Long.class.getName(), String.class.getName()},
			new String[] {"companyId", "className"}, true);

		_finderPathCountByC_C = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_C",
			new String[] {Long.class.getName(), String.class.getName()},
			new String[] {"companyId", "className"}, false);

		_finderPathFetchByC_N = new FinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByC_N",
			new String[] {Long.class.getName(), String.class.getName()},
			new String[] {"companyId", "name"}, true);

		_finderPathCountByC_N = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_N",
			new String[] {Long.class.getName(), String.class.getName()},
			new String[] {"companyId", "name"}, false);

		_finderPathWithPaginationFindByS_S = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByS_S",
			new String[] {
				Boolean.class.getName(), Integer.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {"system_", "status"}, true);

		_finderPathWithoutPaginationFindByS_S = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByS_S",
			new String[] {Boolean.class.getName(), Integer.class.getName()},
			new String[] {"system_", "status"}, true);

		_finderPathCountByS_S = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByS_S",
			new String[] {Boolean.class.getName(), Integer.class.getName()},
			new String[] {"system_", "status"}, false);

		_finderPathWithPaginationFindByC_A_S_S = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByC_A_S_S",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				Boolean.class.getName(), Integer.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {"companyId", "active_", "system_", "status"}, true);

		_finderPathWithoutPaginationFindByC_A_S_S = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByC_A_S_S",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				Boolean.class.getName(), Integer.class.getName()
			},
			new String[] {"companyId", "active_", "system_", "status"}, true);

		_finderPathCountByC_A_S_S = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_A_S_S",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				Boolean.class.getName(), Integer.class.getName()
			},
			new String[] {"companyId", "active_", "system_", "status"}, false);
	}

	@Deactivate
	public void deactivate() {
		entityCache.removeCache(ObjectDefinitionImpl.class.getName());
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

	private static final String _SQL_SELECT_OBJECTDEFINITION =
		"SELECT objectDefinition FROM ObjectDefinition objectDefinition";

	private static final String _SQL_SELECT_OBJECTDEFINITION_WHERE =
		"SELECT objectDefinition FROM ObjectDefinition objectDefinition WHERE ";

	private static final String _SQL_COUNT_OBJECTDEFINITION =
		"SELECT COUNT(objectDefinition) FROM ObjectDefinition objectDefinition";

	private static final String _SQL_COUNT_OBJECTDEFINITION_WHERE =
		"SELECT COUNT(objectDefinition) FROM ObjectDefinition objectDefinition WHERE ";

	private static final String _FILTER_ENTITY_TABLE_FILTER_PK_COLUMN =
		"objectDefinition.objectDefinitionId";

	private static final String _FILTER_SQL_SELECT_OBJECTDEFINITION_WHERE =
		"SELECT DISTINCT {objectDefinition.*} FROM ObjectDefinition objectDefinition WHERE ";

	private static final String
		_FILTER_SQL_SELECT_OBJECTDEFINITION_NO_INLINE_DISTINCT_WHERE_1 =
			"SELECT {ObjectDefinition.*} FROM (SELECT DISTINCT objectDefinition.objectDefinitionId FROM ObjectDefinition objectDefinition WHERE ";

	private static final String
		_FILTER_SQL_SELECT_OBJECTDEFINITION_NO_INLINE_DISTINCT_WHERE_2 =
			") TEMP_TABLE INNER JOIN ObjectDefinition ON TEMP_TABLE.objectDefinitionId = ObjectDefinition.objectDefinitionId";

	private static final String _FILTER_SQL_COUNT_OBJECTDEFINITION_WHERE =
		"SELECT COUNT(DISTINCT objectDefinition.objectDefinitionId) AS COUNT_VALUE FROM ObjectDefinition objectDefinition WHERE ";

	private static final String _FILTER_ENTITY_ALIAS = "objectDefinition";

	private static final String _FILTER_ENTITY_TABLE = "ObjectDefinition";

	private static final String _ORDER_BY_ENTITY_ALIAS = "objectDefinition.";

	private static final String _ORDER_BY_ENTITY_TABLE = "ObjectDefinition.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No ObjectDefinition exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No ObjectDefinition exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		ObjectDefinitionPersistenceImpl.class);

	private static final Set<String> _badColumnNames = SetUtil.fromArray(
		new String[] {"uuid", "active", "system"});

	@Override
	protected FinderCache getFinderCache() {
		return finderCache;
	}

	@Reference
	private ObjectDefinitionModelArgumentsResolver
		_objectDefinitionModelArgumentsResolver;

}