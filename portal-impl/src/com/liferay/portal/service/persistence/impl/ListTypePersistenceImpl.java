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

package com.liferay.portal.service.persistence.impl;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.orm.ArgumentsResolver;
import com.liferay.portal.kernel.dao.orm.EntityCache;
import com.liferay.portal.kernel.dao.orm.EntityCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderCache;
import com.liferay.portal.kernel.dao.orm.FinderCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.exception.NoSuchListTypeException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.model.ListType;
import com.liferay.portal.kernel.model.ListTypeTable;
import com.liferay.portal.kernel.service.persistence.ListTypePersistence;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.model.impl.ListTypeImpl;
import com.liferay.portal.model.impl.ListTypeModelImpl;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;
import com.liferay.registry.ServiceRegistration;

import java.io.Serializable;

import java.lang.reflect.InvocationHandler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * The persistence implementation for the list type service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class ListTypePersistenceImpl
	extends BasePersistenceImpl<ListType> implements ListTypePersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>ListTypeUtil</code> to access the list type persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		ListTypeImpl.class.getName();

	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List1";

	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List2";

	private FinderPath _finderPathWithPaginationFindAll;
	private FinderPath _finderPathWithoutPaginationFindAll;
	private FinderPath _finderPathCountAll;
	private FinderPath _finderPathWithPaginationFindByType;
	private FinderPath _finderPathWithoutPaginationFindByType;
	private FinderPath _finderPathCountByType;

	/**
	 * Returns all the list types where type = &#63;.
	 *
	 * @param type the type
	 * @return the matching list types
	 */
	@Override
	public List<ListType> findByType(String type) {
		return findByType(type, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the list types where type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ListTypeModelImpl</code>.
	 * </p>
	 *
	 * @param type the type
	 * @param start the lower bound of the range of list types
	 * @param end the upper bound of the range of list types (not inclusive)
	 * @return the range of matching list types
	 */
	@Override
	public List<ListType> findByType(String type, int start, int end) {
		return findByType(type, start, end, null);
	}

	/**
	 * Returns an ordered range of all the list types where type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ListTypeModelImpl</code>.
	 * </p>
	 *
	 * @param type the type
	 * @param start the lower bound of the range of list types
	 * @param end the upper bound of the range of list types (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching list types
	 */
	@Override
	public List<ListType> findByType(
		String type, int start, int end,
		OrderByComparator<ListType> orderByComparator) {

		return findByType(type, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the list types where type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ListTypeModelImpl</code>.
	 * </p>
	 *
	 * @param type the type
	 * @param start the lower bound of the range of list types
	 * @param end the upper bound of the range of list types (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching list types
	 */
	@Override
	public List<ListType> findByType(
		String type, int start, int end,
		OrderByComparator<ListType> orderByComparator, boolean useFinderCache) {

		type = Objects.toString(type, "");

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByType;
				finderArgs = new Object[] {type};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByType;
			finderArgs = new Object[] {type, start, end, orderByComparator};
		}

		List<ListType> list = null;

		if (useFinderCache) {
			list = (List<ListType>)FinderCacheUtil.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (ListType listType : list) {
					if (!type.equals(listType.getType())) {
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

			sb.append(_SQL_SELECT_LISTTYPE_WHERE);

			boolean bindType = false;

			if (type.isEmpty()) {
				sb.append(_FINDER_COLUMN_TYPE_TYPE_3);
			}
			else {
				bindType = true;

				sb.append(_FINDER_COLUMN_TYPE_TYPE_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(ListTypeModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindType) {
					queryPos.add(type);
				}

				list = (List<ListType>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					FinderCacheUtil.putResult(finderPath, finderArgs, list);
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
	 * Returns the first list type in the ordered set where type = &#63;.
	 *
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching list type
	 * @throws NoSuchListTypeException if a matching list type could not be found
	 */
	@Override
	public ListType findByType_First(
			String type, OrderByComparator<ListType> orderByComparator)
		throws NoSuchListTypeException {

		ListType listType = fetchByType_First(type, orderByComparator);

		if (listType != null) {
			return listType;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("type=");
		sb.append(type);

		sb.append("}");

		throw new NoSuchListTypeException(sb.toString());
	}

	/**
	 * Returns the first list type in the ordered set where type = &#63;.
	 *
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching list type, or <code>null</code> if a matching list type could not be found
	 */
	@Override
	public ListType fetchByType_First(
		String type, OrderByComparator<ListType> orderByComparator) {

		List<ListType> list = findByType(type, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last list type in the ordered set where type = &#63;.
	 *
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching list type
	 * @throws NoSuchListTypeException if a matching list type could not be found
	 */
	@Override
	public ListType findByType_Last(
			String type, OrderByComparator<ListType> orderByComparator)
		throws NoSuchListTypeException {

		ListType listType = fetchByType_Last(type, orderByComparator);

		if (listType != null) {
			return listType;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("type=");
		sb.append(type);

		sb.append("}");

		throw new NoSuchListTypeException(sb.toString());
	}

	/**
	 * Returns the last list type in the ordered set where type = &#63;.
	 *
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching list type, or <code>null</code> if a matching list type could not be found
	 */
	@Override
	public ListType fetchByType_Last(
		String type, OrderByComparator<ListType> orderByComparator) {

		int count = countByType(type);

		if (count == 0) {
			return null;
		}

		List<ListType> list = findByType(
			type, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the list types before and after the current list type in the ordered set where type = &#63;.
	 *
	 * @param listTypeId the primary key of the current list type
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next list type
	 * @throws NoSuchListTypeException if a list type with the primary key could not be found
	 */
	@Override
	public ListType[] findByType_PrevAndNext(
			long listTypeId, String type,
			OrderByComparator<ListType> orderByComparator)
		throws NoSuchListTypeException {

		type = Objects.toString(type, "");

		ListType listType = findByPrimaryKey(listTypeId);

		Session session = null;

		try {
			session = openSession();

			ListType[] array = new ListTypeImpl[3];

			array[0] = getByType_PrevAndNext(
				session, listType, type, orderByComparator, true);

			array[1] = listType;

			array[2] = getByType_PrevAndNext(
				session, listType, type, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected ListType getByType_PrevAndNext(
		Session session, ListType listType, String type,
		OrderByComparator<ListType> orderByComparator, boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(3);
		}

		sb.append(_SQL_SELECT_LISTTYPE_WHERE);

		boolean bindType = false;

		if (type.isEmpty()) {
			sb.append(_FINDER_COLUMN_TYPE_TYPE_3);
		}
		else {
			bindType = true;

			sb.append(_FINDER_COLUMN_TYPE_TYPE_2);
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
			sb.append(ListTypeModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		if (bindType) {
			queryPos.add(type);
		}

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(listType)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<ListType> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the list types where type = &#63; from the database.
	 *
	 * @param type the type
	 */
	@Override
	public void removeByType(String type) {
		for (ListType listType :
				findByType(type, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(listType);
		}
	}

	/**
	 * Returns the number of list types where type = &#63;.
	 *
	 * @param type the type
	 * @return the number of matching list types
	 */
	@Override
	public int countByType(String type) {
		type = Objects.toString(type, "");

		FinderPath finderPath = _finderPathCountByType;

		Object[] finderArgs = new Object[] {type};

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_LISTTYPE_WHERE);

			boolean bindType = false;

			if (type.isEmpty()) {
				sb.append(_FINDER_COLUMN_TYPE_TYPE_3);
			}
			else {
				bindType = true;

				sb.append(_FINDER_COLUMN_TYPE_TYPE_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindType) {
					queryPos.add(type);
				}

				count = (Long)query.uniqueResult();

				FinderCacheUtil.putResult(finderPath, finderArgs, count);
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

	private static final String _FINDER_COLUMN_TYPE_TYPE_2 =
		"listType.type = ?";

	private static final String _FINDER_COLUMN_TYPE_TYPE_3 =
		"(listType.type IS NULL OR listType.type = '')";

	private FinderPath _finderPathFetchByN_T;
	private FinderPath _finderPathCountByN_T;

	/**
	 * Returns the list type where name = &#63; and type = &#63; or throws a <code>NoSuchListTypeException</code> if it could not be found.
	 *
	 * @param name the name
	 * @param type the type
	 * @return the matching list type
	 * @throws NoSuchListTypeException if a matching list type could not be found
	 */
	@Override
	public ListType findByN_T(String name, String type)
		throws NoSuchListTypeException {

		ListType listType = fetchByN_T(name, type);

		if (listType == null) {
			StringBundler sb = new StringBundler(6);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("name=");
			sb.append(name);

			sb.append(", type=");
			sb.append(type);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchListTypeException(sb.toString());
		}

		return listType;
	}

	/**
	 * Returns the list type where name = &#63; and type = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param name the name
	 * @param type the type
	 * @return the matching list type, or <code>null</code> if a matching list type could not be found
	 */
	@Override
	public ListType fetchByN_T(String name, String type) {
		return fetchByN_T(name, type, true);
	}

	/**
	 * Returns the list type where name = &#63; and type = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param name the name
	 * @param type the type
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching list type, or <code>null</code> if a matching list type could not be found
	 */
	@Override
	public ListType fetchByN_T(
		String name, String type, boolean useFinderCache) {

		name = Objects.toString(name, "");
		type = Objects.toString(type, "");

		Object[] finderArgs = null;

		if (useFinderCache) {
			finderArgs = new Object[] {name, type};
		}

		Object result = null;

		if (useFinderCache) {
			result = FinderCacheUtil.getResult(
				_finderPathFetchByN_T, finderArgs);
		}

		if (result instanceof ListType) {
			ListType listType = (ListType)result;

			if (!Objects.equals(name, listType.getName()) ||
				!Objects.equals(type, listType.getType())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_SELECT_LISTTYPE_WHERE);

			boolean bindName = false;

			if (name.isEmpty()) {
				sb.append(_FINDER_COLUMN_N_T_NAME_3);
			}
			else {
				bindName = true;

				sb.append(_FINDER_COLUMN_N_T_NAME_2);
			}

			boolean bindType = false;

			if (type.isEmpty()) {
				sb.append(_FINDER_COLUMN_N_T_TYPE_3);
			}
			else {
				bindType = true;

				sb.append(_FINDER_COLUMN_N_T_TYPE_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindName) {
					queryPos.add(name);
				}

				if (bindType) {
					queryPos.add(type);
				}

				List<ListType> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache) {
						FinderCacheUtil.putResult(
							_finderPathFetchByN_T, finderArgs, list);
					}
				}
				else {
					ListType listType = list.get(0);

					result = listType;

					cacheResult(listType);
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
			return (ListType)result;
		}
	}

	/**
	 * Removes the list type where name = &#63; and type = &#63; from the database.
	 *
	 * @param name the name
	 * @param type the type
	 * @return the list type that was removed
	 */
	@Override
	public ListType removeByN_T(String name, String type)
		throws NoSuchListTypeException {

		ListType listType = findByN_T(name, type);

		return remove(listType);
	}

	/**
	 * Returns the number of list types where name = &#63; and type = &#63;.
	 *
	 * @param name the name
	 * @param type the type
	 * @return the number of matching list types
	 */
	@Override
	public int countByN_T(String name, String type) {
		name = Objects.toString(name, "");
		type = Objects.toString(type, "");

		FinderPath finderPath = _finderPathCountByN_T;

		Object[] finderArgs = new Object[] {name, type};

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_LISTTYPE_WHERE);

			boolean bindName = false;

			if (name.isEmpty()) {
				sb.append(_FINDER_COLUMN_N_T_NAME_3);
			}
			else {
				bindName = true;

				sb.append(_FINDER_COLUMN_N_T_NAME_2);
			}

			boolean bindType = false;

			if (type.isEmpty()) {
				sb.append(_FINDER_COLUMN_N_T_TYPE_3);
			}
			else {
				bindType = true;

				sb.append(_FINDER_COLUMN_N_T_TYPE_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindName) {
					queryPos.add(name);
				}

				if (bindType) {
					queryPos.add(type);
				}

				count = (Long)query.uniqueResult();

				FinderCacheUtil.putResult(finderPath, finderArgs, count);
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

	private static final String _FINDER_COLUMN_N_T_NAME_2 =
		"listType.name = ? AND ";

	private static final String _FINDER_COLUMN_N_T_NAME_3 =
		"(listType.name IS NULL OR listType.name = '') AND ";

	private static final String _FINDER_COLUMN_N_T_TYPE_2 = "listType.type = ?";

	private static final String _FINDER_COLUMN_N_T_TYPE_3 =
		"(listType.type IS NULL OR listType.type = '')";

	public ListTypePersistenceImpl() {
		Map<String, String> dbColumnNames = new HashMap<String, String>();

		dbColumnNames.put("type", "type_");

		setDBColumnNames(dbColumnNames);

		setModelClass(ListType.class);

		setModelImplClass(ListTypeImpl.class);
		setModelPKClass(long.class);

		setTable(ListTypeTable.INSTANCE);
	}

	/**
	 * Caches the list type in the entity cache if it is enabled.
	 *
	 * @param listType the list type
	 */
	@Override
	public void cacheResult(ListType listType) {
		EntityCacheUtil.putResult(
			ListTypeImpl.class, listType.getPrimaryKey(), listType);

		FinderCacheUtil.putResult(
			_finderPathFetchByN_T,
			new Object[] {listType.getName(), listType.getType()}, listType);
	}

	/**
	 * Caches the list types in the entity cache if it is enabled.
	 *
	 * @param listTypes the list types
	 */
	@Override
	public void cacheResult(List<ListType> listTypes) {
		for (ListType listType : listTypes) {
			if (EntityCacheUtil.getResult(
					ListTypeImpl.class, listType.getPrimaryKey()) == null) {

				cacheResult(listType);
			}
		}
	}

	/**
	 * Clears the cache for all list types.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		EntityCacheUtil.clearCache(ListTypeImpl.class);

		FinderCacheUtil.clearCache(ListTypeImpl.class);
	}

	/**
	 * Clears the cache for the list type.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(ListType listType) {
		EntityCacheUtil.removeResult(ListTypeImpl.class, listType);
	}

	@Override
	public void clearCache(List<ListType> listTypes) {
		for (ListType listType : listTypes) {
			EntityCacheUtil.removeResult(ListTypeImpl.class, listType);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		FinderCacheUtil.clearCache(ListTypeImpl.class);

		for (Serializable primaryKey : primaryKeys) {
			EntityCacheUtil.removeResult(ListTypeImpl.class, primaryKey);
		}
	}

	protected void cacheUniqueFindersCache(
		ListTypeModelImpl listTypeModelImpl) {

		Object[] args = new Object[] {
			listTypeModelImpl.getName(), listTypeModelImpl.getType()
		};

		FinderCacheUtil.putResult(_finderPathCountByN_T, args, Long.valueOf(1));
		FinderCacheUtil.putResult(
			_finderPathFetchByN_T, args, listTypeModelImpl);
	}

	/**
	 * Creates a new list type with the primary key. Does not add the list type to the database.
	 *
	 * @param listTypeId the primary key for the new list type
	 * @return the new list type
	 */
	@Override
	public ListType create(long listTypeId) {
		ListType listType = new ListTypeImpl();

		listType.setNew(true);
		listType.setPrimaryKey(listTypeId);

		return listType;
	}

	/**
	 * Removes the list type with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param listTypeId the primary key of the list type
	 * @return the list type that was removed
	 * @throws NoSuchListTypeException if a list type with the primary key could not be found
	 */
	@Override
	public ListType remove(long listTypeId) throws NoSuchListTypeException {
		return remove((Serializable)listTypeId);
	}

	/**
	 * Removes the list type with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the list type
	 * @return the list type that was removed
	 * @throws NoSuchListTypeException if a list type with the primary key could not be found
	 */
	@Override
	public ListType remove(Serializable primaryKey)
		throws NoSuchListTypeException {

		Session session = null;

		try {
			session = openSession();

			ListType listType = (ListType)session.get(
				ListTypeImpl.class, primaryKey);

			if (listType == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchListTypeException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(listType);
		}
		catch (NoSuchListTypeException noSuchEntityException) {
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
	protected ListType removeImpl(ListType listType) {
		Session session = null;

		try {
			session = openSession();

			if (!session.contains(listType)) {
				listType = (ListType)session.get(
					ListTypeImpl.class, listType.getPrimaryKeyObj());
			}

			if (listType != null) {
				session.delete(listType);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (listType != null) {
			clearCache(listType);
		}

		return listType;
	}

	@Override
	public ListType updateImpl(ListType listType) {
		boolean isNew = listType.isNew();

		if (!(listType instanceof ListTypeModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(listType.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(listType);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in listType proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom ListType implementation " +
					listType.getClass());
		}

		ListTypeModelImpl listTypeModelImpl = (ListTypeModelImpl)listType;

		Session session = null;

		try {
			session = openSession();

			if (isNew) {
				session.save(listType);
			}
			else {
				listType = (ListType)session.merge(listType);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		EntityCacheUtil.putResult(
			ListTypeImpl.class, listTypeModelImpl, false, true);

		cacheUniqueFindersCache(listTypeModelImpl);

		if (isNew) {
			listType.setNew(false);
		}

		listType.resetOriginalValues();

		return listType;
	}

	/**
	 * Returns the list type with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the list type
	 * @return the list type
	 * @throws NoSuchListTypeException if a list type with the primary key could not be found
	 */
	@Override
	public ListType findByPrimaryKey(Serializable primaryKey)
		throws NoSuchListTypeException {

		ListType listType = fetchByPrimaryKey(primaryKey);

		if (listType == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchListTypeException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return listType;
	}

	/**
	 * Returns the list type with the primary key or throws a <code>NoSuchListTypeException</code> if it could not be found.
	 *
	 * @param listTypeId the primary key of the list type
	 * @return the list type
	 * @throws NoSuchListTypeException if a list type with the primary key could not be found
	 */
	@Override
	public ListType findByPrimaryKey(long listTypeId)
		throws NoSuchListTypeException {

		return findByPrimaryKey((Serializable)listTypeId);
	}

	/**
	 * Returns the list type with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param listTypeId the primary key of the list type
	 * @return the list type, or <code>null</code> if a list type with the primary key could not be found
	 */
	@Override
	public ListType fetchByPrimaryKey(long listTypeId) {
		return fetchByPrimaryKey((Serializable)listTypeId);
	}

	/**
	 * Returns all the list types.
	 *
	 * @return the list types
	 */
	@Override
	public List<ListType> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the list types.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ListTypeModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of list types
	 * @param end the upper bound of the range of list types (not inclusive)
	 * @return the range of list types
	 */
	@Override
	public List<ListType> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the list types.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ListTypeModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of list types
	 * @param end the upper bound of the range of list types (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of list types
	 */
	@Override
	public List<ListType> findAll(
		int start, int end, OrderByComparator<ListType> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the list types.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ListTypeModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of list types
	 * @param end the upper bound of the range of list types (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of list types
	 */
	@Override
	public List<ListType> findAll(
		int start, int end, OrderByComparator<ListType> orderByComparator,
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

		List<ListType> list = null;

		if (useFinderCache) {
			list = (List<ListType>)FinderCacheUtil.getResult(
				finderPath, finderArgs);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_LISTTYPE);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_LISTTYPE;

				sql = sql.concat(ListTypeModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<ListType>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					FinderCacheUtil.putResult(finderPath, finderArgs, list);
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
	 * Removes all the list types from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (ListType listType : findAll()) {
			remove(listType);
		}
	}

	/**
	 * Returns the number of list types.
	 *
	 * @return the number of list types
	 */
	@Override
	public int countAll() {
		Long count = (Long)FinderCacheUtil.getResult(
			_finderPathCountAll, FINDER_ARGS_EMPTY);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(_SQL_COUNT_LISTTYPE);

				count = (Long)query.uniqueResult();

				FinderCacheUtil.putResult(
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
		return EntityCacheUtil.getEntityCache();
	}

	@Override
	protected String getPKDBName() {
		return "listTypeId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_LISTTYPE;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return ListTypeModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the list type persistence.
	 */
	public void afterPropertiesSet() {
		Registry registry = RegistryUtil.getRegistry();

		_argumentsResolverServiceRegistration = registry.registerService(
			ArgumentsResolver.class, new ListTypeModelArgumentsResolver());

		_finderPathWithPaginationFindAll = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0],
			new String[0], true);

		_finderPathWithoutPaginationFindAll = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0],
			new String[0], true);

		_finderPathCountAll = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll",
			new String[0], new String[0], false);

		_finderPathWithPaginationFindByType = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByType",
			new String[] {
				String.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"type_"}, true);

		_finderPathWithoutPaginationFindByType = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByType",
			new String[] {String.class.getName()}, new String[] {"type_"},
			true);

		_finderPathCountByType = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByType",
			new String[] {String.class.getName()}, new String[] {"type_"},
			false);

		_finderPathFetchByN_T = new FinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByN_T",
			new String[] {String.class.getName(), String.class.getName()},
			new String[] {"name", "type_"}, true);

		_finderPathCountByN_T = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByN_T",
			new String[] {String.class.getName(), String.class.getName()},
			new String[] {"name", "type_"}, false);
	}

	public void destroy() {
		EntityCacheUtil.removeCache(ListTypeImpl.class.getName());

		_argumentsResolverServiceRegistration.unregister();
	}

	private static final String _SQL_SELECT_LISTTYPE =
		"SELECT listType FROM ListType listType";

	private static final String _SQL_SELECT_LISTTYPE_WHERE =
		"SELECT listType FROM ListType listType WHERE ";

	private static final String _SQL_COUNT_LISTTYPE =
		"SELECT COUNT(listType) FROM ListType listType";

	private static final String _SQL_COUNT_LISTTYPE_WHERE =
		"SELECT COUNT(listType) FROM ListType listType WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS = "listType.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No ListType exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No ListType exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		ListTypePersistenceImpl.class);

	private static final Set<String> _badColumnNames = SetUtil.fromArray(
		new String[] {"type"});

	@Override
	protected FinderCache getFinderCache() {
		return FinderCacheUtil.getFinderCache();
	}

	private ServiceRegistration<ArgumentsResolver>
		_argumentsResolverServiceRegistration;

	private static class ListTypeModelArgumentsResolver
		implements ArgumentsResolver {

		@Override
		public Object[] getArguments(
			FinderPath finderPath, BaseModel<?> baseModel, boolean checkColumn,
			boolean original) {

			String[] columnNames = finderPath.getColumnNames();

			if ((columnNames == null) || (columnNames.length == 0)) {
				if (baseModel.isNew()) {
					return FINDER_ARGS_EMPTY;
				}

				return null;
			}

			ListTypeModelImpl listTypeModelImpl = (ListTypeModelImpl)baseModel;

			long columnBitmask = listTypeModelImpl.getColumnBitmask();

			if (!checkColumn || (columnBitmask == 0)) {
				return _getValue(listTypeModelImpl, columnNames, original);
			}

			Long finderPathColumnBitmask = _finderPathColumnBitmasksCache.get(
				finderPath);

			if (finderPathColumnBitmask == null) {
				finderPathColumnBitmask = 0L;

				for (String columnName : columnNames) {
					finderPathColumnBitmask |=
						listTypeModelImpl.getColumnBitmask(columnName);
				}

				if (finderPath.isBaseModelResult() &&
					(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION ==
						finderPath.getCacheName())) {

					finderPathColumnBitmask |= _ORDER_BY_COLUMNS_BITMASK;
				}

				_finderPathColumnBitmasksCache.put(
					finderPath, finderPathColumnBitmask);
			}

			if ((columnBitmask & finderPathColumnBitmask) != 0) {
				return _getValue(listTypeModelImpl, columnNames, original);
			}

			return null;
		}

		@Override
		public String getClassName() {
			return ListTypeImpl.class.getName();
		}

		@Override
		public String getTableName() {
			return ListTypeTable.INSTANCE.getTableName();
		}

		private static Object[] _getValue(
			ListTypeModelImpl listTypeModelImpl, String[] columnNames,
			boolean original) {

			Object[] arguments = new Object[columnNames.length];

			for (int i = 0; i < arguments.length; i++) {
				String columnName = columnNames[i];

				if (original) {
					arguments[i] = listTypeModelImpl.getColumnOriginalValue(
						columnName);
				}
				else {
					arguments[i] = listTypeModelImpl.getColumnValue(columnName);
				}
			}

			return arguments;
		}

		private static final Map<FinderPath, Long>
			_finderPathColumnBitmasksCache = new ConcurrentHashMap<>();

		private static final long _ORDER_BY_COLUMNS_BITMASK;

		static {
			long orderByColumnsBitmask = 0;

			orderByColumnsBitmask |= ListTypeModelImpl.getColumnBitmask("name");

			_ORDER_BY_COLUMNS_BITMASK = orderByColumnsBitmask;
		}

	}

}