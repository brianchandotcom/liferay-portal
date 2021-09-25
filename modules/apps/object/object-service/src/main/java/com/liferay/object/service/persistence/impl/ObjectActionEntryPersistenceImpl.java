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

import com.liferay.object.exception.NoSuchObjectActionEntryException;
import com.liferay.object.model.ObjectActionEntry;
import com.liferay.object.model.ObjectActionEntryTable;
import com.liferay.object.model.impl.ObjectActionEntryImpl;
import com.liferay.object.model.impl.ObjectActionEntryModelImpl;
import com.liferay.object.service.persistence.ObjectActionEntryPersistence;
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
 * The persistence implementation for the object action entry service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Marco Leo
 * @generated
 */
@Component(
	service = {ObjectActionEntryPersistence.class, BasePersistence.class}
)
public class ObjectActionEntryPersistenceImpl
	extends BasePersistenceImpl<ObjectActionEntry>
	implements ObjectActionEntryPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>ObjectActionEntryUtil</code> to access the object action entry persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		ObjectActionEntryImpl.class.getName();

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
	 * Returns all the object action entries where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching object action entries
	 */
	@Override
	public List<ObjectActionEntry> findByUuid(String uuid) {
		return findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the object action entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectActionEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of object action entries
	 * @param end the upper bound of the range of object action entries (not inclusive)
	 * @return the range of matching object action entries
	 */
	@Override
	public List<ObjectActionEntry> findByUuid(String uuid, int start, int end) {
		return findByUuid(uuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the object action entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectActionEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of object action entries
	 * @param end the upper bound of the range of object action entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching object action entries
	 */
	@Override
	public List<ObjectActionEntry> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<ObjectActionEntry> orderByComparator) {

		return findByUuid(uuid, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the object action entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectActionEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of object action entries
	 * @param end the upper bound of the range of object action entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching object action entries
	 */
	@Override
	public List<ObjectActionEntry> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<ObjectActionEntry> orderByComparator,
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

		List<ObjectActionEntry> list = null;

		if (useFinderCache) {
			list = (List<ObjectActionEntry>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (ObjectActionEntry objectActionEntry : list) {
					if (!uuid.equals(objectActionEntry.getUuid())) {
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

			sb.append(_SQL_SELECT_OBJECTACTIONENTRY_WHERE);

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
				sb.append(ObjectActionEntryModelImpl.ORDER_BY_JPQL);
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

				list = (List<ObjectActionEntry>)QueryUtil.list(
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
	 * Returns the first object action entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object action entry
	 * @throws NoSuchObjectActionEntryException if a matching object action entry could not be found
	 */
	@Override
	public ObjectActionEntry findByUuid_First(
			String uuid, OrderByComparator<ObjectActionEntry> orderByComparator)
		throws NoSuchObjectActionEntryException {

		ObjectActionEntry objectActionEntry = fetchByUuid_First(
			uuid, orderByComparator);

		if (objectActionEntry != null) {
			return objectActionEntry;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append("}");

		throw new NoSuchObjectActionEntryException(sb.toString());
	}

	/**
	 * Returns the first object action entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object action entry, or <code>null</code> if a matching object action entry could not be found
	 */
	@Override
	public ObjectActionEntry fetchByUuid_First(
		String uuid, OrderByComparator<ObjectActionEntry> orderByComparator) {

		List<ObjectActionEntry> list = findByUuid(
			uuid, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last object action entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object action entry
	 * @throws NoSuchObjectActionEntryException if a matching object action entry could not be found
	 */
	@Override
	public ObjectActionEntry findByUuid_Last(
			String uuid, OrderByComparator<ObjectActionEntry> orderByComparator)
		throws NoSuchObjectActionEntryException {

		ObjectActionEntry objectActionEntry = fetchByUuid_Last(
			uuid, orderByComparator);

		if (objectActionEntry != null) {
			return objectActionEntry;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append("}");

		throw new NoSuchObjectActionEntryException(sb.toString());
	}

	/**
	 * Returns the last object action entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object action entry, or <code>null</code> if a matching object action entry could not be found
	 */
	@Override
	public ObjectActionEntry fetchByUuid_Last(
		String uuid, OrderByComparator<ObjectActionEntry> orderByComparator) {

		int count = countByUuid(uuid);

		if (count == 0) {
			return null;
		}

		List<ObjectActionEntry> list = findByUuid(
			uuid, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the object action entries before and after the current object action entry in the ordered set where uuid = &#63;.
	 *
	 * @param objectActionEntryId the primary key of the current object action entry
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next object action entry
	 * @throws NoSuchObjectActionEntryException if a object action entry with the primary key could not be found
	 */
	@Override
	public ObjectActionEntry[] findByUuid_PrevAndNext(
			long objectActionEntryId, String uuid,
			OrderByComparator<ObjectActionEntry> orderByComparator)
		throws NoSuchObjectActionEntryException {

		uuid = Objects.toString(uuid, "");

		ObjectActionEntry objectActionEntry = findByPrimaryKey(
			objectActionEntryId);

		Session session = null;

		try {
			session = openSession();

			ObjectActionEntry[] array = new ObjectActionEntryImpl[3];

			array[0] = getByUuid_PrevAndNext(
				session, objectActionEntry, uuid, orderByComparator, true);

			array[1] = objectActionEntry;

			array[2] = getByUuid_PrevAndNext(
				session, objectActionEntry, uuid, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected ObjectActionEntry getByUuid_PrevAndNext(
		Session session, ObjectActionEntry objectActionEntry, String uuid,
		OrderByComparator<ObjectActionEntry> orderByComparator,
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

		sb.append(_SQL_SELECT_OBJECTACTIONENTRY_WHERE);

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
			sb.append(ObjectActionEntryModelImpl.ORDER_BY_JPQL);
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
						objectActionEntry)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<ObjectActionEntry> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the object action entries where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	@Override
	public void removeByUuid(String uuid) {
		for (ObjectActionEntry objectActionEntry :
				findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(objectActionEntry);
		}
	}

	/**
	 * Returns the number of object action entries where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching object action entries
	 */
	@Override
	public int countByUuid(String uuid) {
		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = _finderPathCountByUuid;

		Object[] finderArgs = new Object[] {uuid};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_OBJECTACTIONENTRY_WHERE);

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
		"objectActionEntry.uuid = ?";

	private static final String _FINDER_COLUMN_UUID_UUID_3 =
		"(objectActionEntry.uuid IS NULL OR objectActionEntry.uuid = '')";

	private FinderPath _finderPathWithPaginationFindByUuid_C;
	private FinderPath _finderPathWithoutPaginationFindByUuid_C;
	private FinderPath _finderPathCountByUuid_C;

	/**
	 * Returns all the object action entries where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching object action entries
	 */
	@Override
	public List<ObjectActionEntry> findByUuid_C(String uuid, long companyId) {
		return findByUuid_C(
			uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the object action entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectActionEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of object action entries
	 * @param end the upper bound of the range of object action entries (not inclusive)
	 * @return the range of matching object action entries
	 */
	@Override
	public List<ObjectActionEntry> findByUuid_C(
		String uuid, long companyId, int start, int end) {

		return findByUuid_C(uuid, companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the object action entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectActionEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of object action entries
	 * @param end the upper bound of the range of object action entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching object action entries
	 */
	@Override
	public List<ObjectActionEntry> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<ObjectActionEntry> orderByComparator) {

		return findByUuid_C(
			uuid, companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the object action entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectActionEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of object action entries
	 * @param end the upper bound of the range of object action entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching object action entries
	 */
	@Override
	public List<ObjectActionEntry> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<ObjectActionEntry> orderByComparator,
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

		List<ObjectActionEntry> list = null;

		if (useFinderCache) {
			list = (List<ObjectActionEntry>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (ObjectActionEntry objectActionEntry : list) {
					if (!uuid.equals(objectActionEntry.getUuid()) ||
						(companyId != objectActionEntry.getCompanyId())) {

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

			sb.append(_SQL_SELECT_OBJECTACTIONENTRY_WHERE);

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
				sb.append(ObjectActionEntryModelImpl.ORDER_BY_JPQL);
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

				list = (List<ObjectActionEntry>)QueryUtil.list(
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
	 * Returns the first object action entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object action entry
	 * @throws NoSuchObjectActionEntryException if a matching object action entry could not be found
	 */
	@Override
	public ObjectActionEntry findByUuid_C_First(
			String uuid, long companyId,
			OrderByComparator<ObjectActionEntry> orderByComparator)
		throws NoSuchObjectActionEntryException {

		ObjectActionEntry objectActionEntry = fetchByUuid_C_First(
			uuid, companyId, orderByComparator);

		if (objectActionEntry != null) {
			return objectActionEntry;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append(", companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchObjectActionEntryException(sb.toString());
	}

	/**
	 * Returns the first object action entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object action entry, or <code>null</code> if a matching object action entry could not be found
	 */
	@Override
	public ObjectActionEntry fetchByUuid_C_First(
		String uuid, long companyId,
		OrderByComparator<ObjectActionEntry> orderByComparator) {

		List<ObjectActionEntry> list = findByUuid_C(
			uuid, companyId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last object action entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object action entry
	 * @throws NoSuchObjectActionEntryException if a matching object action entry could not be found
	 */
	@Override
	public ObjectActionEntry findByUuid_C_Last(
			String uuid, long companyId,
			OrderByComparator<ObjectActionEntry> orderByComparator)
		throws NoSuchObjectActionEntryException {

		ObjectActionEntry objectActionEntry = fetchByUuid_C_Last(
			uuid, companyId, orderByComparator);

		if (objectActionEntry != null) {
			return objectActionEntry;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append(", companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchObjectActionEntryException(sb.toString());
	}

	/**
	 * Returns the last object action entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object action entry, or <code>null</code> if a matching object action entry could not be found
	 */
	@Override
	public ObjectActionEntry fetchByUuid_C_Last(
		String uuid, long companyId,
		OrderByComparator<ObjectActionEntry> orderByComparator) {

		int count = countByUuid_C(uuid, companyId);

		if (count == 0) {
			return null;
		}

		List<ObjectActionEntry> list = findByUuid_C(
			uuid, companyId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the object action entries before and after the current object action entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param objectActionEntryId the primary key of the current object action entry
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next object action entry
	 * @throws NoSuchObjectActionEntryException if a object action entry with the primary key could not be found
	 */
	@Override
	public ObjectActionEntry[] findByUuid_C_PrevAndNext(
			long objectActionEntryId, String uuid, long companyId,
			OrderByComparator<ObjectActionEntry> orderByComparator)
		throws NoSuchObjectActionEntryException {

		uuid = Objects.toString(uuid, "");

		ObjectActionEntry objectActionEntry = findByPrimaryKey(
			objectActionEntryId);

		Session session = null;

		try {
			session = openSession();

			ObjectActionEntry[] array = new ObjectActionEntryImpl[3];

			array[0] = getByUuid_C_PrevAndNext(
				session, objectActionEntry, uuid, companyId, orderByComparator,
				true);

			array[1] = objectActionEntry;

			array[2] = getByUuid_C_PrevAndNext(
				session, objectActionEntry, uuid, companyId, orderByComparator,
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

	protected ObjectActionEntry getByUuid_C_PrevAndNext(
		Session session, ObjectActionEntry objectActionEntry, String uuid,
		long companyId, OrderByComparator<ObjectActionEntry> orderByComparator,
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

		sb.append(_SQL_SELECT_OBJECTACTIONENTRY_WHERE);

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
			sb.append(ObjectActionEntryModelImpl.ORDER_BY_JPQL);
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
						objectActionEntry)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<ObjectActionEntry> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the object action entries where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	@Override
	public void removeByUuid_C(String uuid, long companyId) {
		for (ObjectActionEntry objectActionEntry :
				findByUuid_C(
					uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(objectActionEntry);
		}
	}

	/**
	 * Returns the number of object action entries where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching object action entries
	 */
	@Override
	public int countByUuid_C(String uuid, long companyId) {
		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = _finderPathCountByUuid_C;

		Object[] finderArgs = new Object[] {uuid, companyId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_OBJECTACTIONENTRY_WHERE);

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
		"objectActionEntry.uuid = ? AND ";

	private static final String _FINDER_COLUMN_UUID_C_UUID_3 =
		"(objectActionEntry.uuid IS NULL OR objectActionEntry.uuid = '') AND ";

	private static final String _FINDER_COLUMN_UUID_C_COMPANYID_2 =
		"objectActionEntry.companyId = ?";

	private FinderPath _finderPathWithPaginationFindByO_A_T;
	private FinderPath _finderPathWithoutPaginationFindByO_A_T;
	private FinderPath _finderPathCountByO_A_T;

	/**
	 * Returns all the object action entries where objectDefinitionId = &#63; and active = &#63; and triggerName = &#63;.
	 *
	 * @param objectDefinitionId the object definition ID
	 * @param active the active
	 * @param triggerName the trigger name
	 * @return the matching object action entries
	 */
	@Override
	public List<ObjectActionEntry> findByO_A_T(
		long objectDefinitionId, boolean active, String triggerName) {

		return findByO_A_T(
			objectDefinitionId, active, triggerName, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the object action entries where objectDefinitionId = &#63; and active = &#63; and triggerName = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectActionEntryModelImpl</code>.
	 * </p>
	 *
	 * @param objectDefinitionId the object definition ID
	 * @param active the active
	 * @param triggerName the trigger name
	 * @param start the lower bound of the range of object action entries
	 * @param end the upper bound of the range of object action entries (not inclusive)
	 * @return the range of matching object action entries
	 */
	@Override
	public List<ObjectActionEntry> findByO_A_T(
		long objectDefinitionId, boolean active, String triggerName, int start,
		int end) {

		return findByO_A_T(
			objectDefinitionId, active, triggerName, start, end, null);
	}

	/**
	 * Returns an ordered range of all the object action entries where objectDefinitionId = &#63; and active = &#63; and triggerName = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectActionEntryModelImpl</code>.
	 * </p>
	 *
	 * @param objectDefinitionId the object definition ID
	 * @param active the active
	 * @param triggerName the trigger name
	 * @param start the lower bound of the range of object action entries
	 * @param end the upper bound of the range of object action entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching object action entries
	 */
	@Override
	public List<ObjectActionEntry> findByO_A_T(
		long objectDefinitionId, boolean active, String triggerName, int start,
		int end, OrderByComparator<ObjectActionEntry> orderByComparator) {

		return findByO_A_T(
			objectDefinitionId, active, triggerName, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the object action entries where objectDefinitionId = &#63; and active = &#63; and triggerName = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectActionEntryModelImpl</code>.
	 * </p>
	 *
	 * @param objectDefinitionId the object definition ID
	 * @param active the active
	 * @param triggerName the trigger name
	 * @param start the lower bound of the range of object action entries
	 * @param end the upper bound of the range of object action entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching object action entries
	 */
	@Override
	public List<ObjectActionEntry> findByO_A_T(
		long objectDefinitionId, boolean active, String triggerName, int start,
		int end, OrderByComparator<ObjectActionEntry> orderByComparator,
		boolean useFinderCache) {

		triggerName = Objects.toString(triggerName, "");

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByO_A_T;
				finderArgs = new Object[] {
					objectDefinitionId, active, triggerName
				};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByO_A_T;
			finderArgs = new Object[] {
				objectDefinitionId, active, triggerName, start, end,
				orderByComparator
			};
		}

		List<ObjectActionEntry> list = null;

		if (useFinderCache) {
			list = (List<ObjectActionEntry>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (ObjectActionEntry objectActionEntry : list) {
					if ((objectDefinitionId !=
							objectActionEntry.getObjectDefinitionId()) ||
						(active != objectActionEntry.isActive()) ||
						!triggerName.equals(
							objectActionEntry.getTriggerName())) {

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
					5 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				sb = new StringBundler(5);
			}

			sb.append(_SQL_SELECT_OBJECTACTIONENTRY_WHERE);

			sb.append(_FINDER_COLUMN_O_A_T_OBJECTDEFINITIONID_2);

			sb.append(_FINDER_COLUMN_O_A_T_ACTIVE_2);

			boolean bindTriggerName = false;

			if (triggerName.isEmpty()) {
				sb.append(_FINDER_COLUMN_O_A_T_TRIGGERNAME_3);
			}
			else {
				bindTriggerName = true;

				sb.append(_FINDER_COLUMN_O_A_T_TRIGGERNAME_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(ObjectActionEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(objectDefinitionId);

				queryPos.add(active);

				if (bindTriggerName) {
					queryPos.add(triggerName);
				}

				list = (List<ObjectActionEntry>)QueryUtil.list(
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
	 * Returns the first object action entry in the ordered set where objectDefinitionId = &#63; and active = &#63; and triggerName = &#63;.
	 *
	 * @param objectDefinitionId the object definition ID
	 * @param active the active
	 * @param triggerName the trigger name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object action entry
	 * @throws NoSuchObjectActionEntryException if a matching object action entry could not be found
	 */
	@Override
	public ObjectActionEntry findByO_A_T_First(
			long objectDefinitionId, boolean active, String triggerName,
			OrderByComparator<ObjectActionEntry> orderByComparator)
		throws NoSuchObjectActionEntryException {

		ObjectActionEntry objectActionEntry = fetchByO_A_T_First(
			objectDefinitionId, active, triggerName, orderByComparator);

		if (objectActionEntry != null) {
			return objectActionEntry;
		}

		StringBundler sb = new StringBundler(8);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("objectDefinitionId=");
		sb.append(objectDefinitionId);

		sb.append(", active=");
		sb.append(active);

		sb.append(", triggerName=");
		sb.append(triggerName);

		sb.append("}");

		throw new NoSuchObjectActionEntryException(sb.toString());
	}

	/**
	 * Returns the first object action entry in the ordered set where objectDefinitionId = &#63; and active = &#63; and triggerName = &#63;.
	 *
	 * @param objectDefinitionId the object definition ID
	 * @param active the active
	 * @param triggerName the trigger name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object action entry, or <code>null</code> if a matching object action entry could not be found
	 */
	@Override
	public ObjectActionEntry fetchByO_A_T_First(
		long objectDefinitionId, boolean active, String triggerName,
		OrderByComparator<ObjectActionEntry> orderByComparator) {

		List<ObjectActionEntry> list = findByO_A_T(
			objectDefinitionId, active, triggerName, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last object action entry in the ordered set where objectDefinitionId = &#63; and active = &#63; and triggerName = &#63;.
	 *
	 * @param objectDefinitionId the object definition ID
	 * @param active the active
	 * @param triggerName the trigger name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object action entry
	 * @throws NoSuchObjectActionEntryException if a matching object action entry could not be found
	 */
	@Override
	public ObjectActionEntry findByO_A_T_Last(
			long objectDefinitionId, boolean active, String triggerName,
			OrderByComparator<ObjectActionEntry> orderByComparator)
		throws NoSuchObjectActionEntryException {

		ObjectActionEntry objectActionEntry = fetchByO_A_T_Last(
			objectDefinitionId, active, triggerName, orderByComparator);

		if (objectActionEntry != null) {
			return objectActionEntry;
		}

		StringBundler sb = new StringBundler(8);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("objectDefinitionId=");
		sb.append(objectDefinitionId);

		sb.append(", active=");
		sb.append(active);

		sb.append(", triggerName=");
		sb.append(triggerName);

		sb.append("}");

		throw new NoSuchObjectActionEntryException(sb.toString());
	}

	/**
	 * Returns the last object action entry in the ordered set where objectDefinitionId = &#63; and active = &#63; and triggerName = &#63;.
	 *
	 * @param objectDefinitionId the object definition ID
	 * @param active the active
	 * @param triggerName the trigger name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object action entry, or <code>null</code> if a matching object action entry could not be found
	 */
	@Override
	public ObjectActionEntry fetchByO_A_T_Last(
		long objectDefinitionId, boolean active, String triggerName,
		OrderByComparator<ObjectActionEntry> orderByComparator) {

		int count = countByO_A_T(objectDefinitionId, active, triggerName);

		if (count == 0) {
			return null;
		}

		List<ObjectActionEntry> list = findByO_A_T(
			objectDefinitionId, active, triggerName, count - 1, count,
			orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the object action entries before and after the current object action entry in the ordered set where objectDefinitionId = &#63; and active = &#63; and triggerName = &#63;.
	 *
	 * @param objectActionEntryId the primary key of the current object action entry
	 * @param objectDefinitionId the object definition ID
	 * @param active the active
	 * @param triggerName the trigger name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next object action entry
	 * @throws NoSuchObjectActionEntryException if a object action entry with the primary key could not be found
	 */
	@Override
	public ObjectActionEntry[] findByO_A_T_PrevAndNext(
			long objectActionEntryId, long objectDefinitionId, boolean active,
			String triggerName,
			OrderByComparator<ObjectActionEntry> orderByComparator)
		throws NoSuchObjectActionEntryException {

		triggerName = Objects.toString(triggerName, "");

		ObjectActionEntry objectActionEntry = findByPrimaryKey(
			objectActionEntryId);

		Session session = null;

		try {
			session = openSession();

			ObjectActionEntry[] array = new ObjectActionEntryImpl[3];

			array[0] = getByO_A_T_PrevAndNext(
				session, objectActionEntry, objectDefinitionId, active,
				triggerName, orderByComparator, true);

			array[1] = objectActionEntry;

			array[2] = getByO_A_T_PrevAndNext(
				session, objectActionEntry, objectDefinitionId, active,
				triggerName, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected ObjectActionEntry getByO_A_T_PrevAndNext(
		Session session, ObjectActionEntry objectActionEntry,
		long objectDefinitionId, boolean active, String triggerName,
		OrderByComparator<ObjectActionEntry> orderByComparator,
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

		sb.append(_SQL_SELECT_OBJECTACTIONENTRY_WHERE);

		sb.append(_FINDER_COLUMN_O_A_T_OBJECTDEFINITIONID_2);

		sb.append(_FINDER_COLUMN_O_A_T_ACTIVE_2);

		boolean bindTriggerName = false;

		if (triggerName.isEmpty()) {
			sb.append(_FINDER_COLUMN_O_A_T_TRIGGERNAME_3);
		}
		else {
			bindTriggerName = true;

			sb.append(_FINDER_COLUMN_O_A_T_TRIGGERNAME_2);
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
			sb.append(ObjectActionEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(objectDefinitionId);

		queryPos.add(active);

		if (bindTriggerName) {
			queryPos.add(triggerName);
		}

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						objectActionEntry)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<ObjectActionEntry> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the object action entries where objectDefinitionId = &#63; and active = &#63; and triggerName = &#63; from the database.
	 *
	 * @param objectDefinitionId the object definition ID
	 * @param active the active
	 * @param triggerName the trigger name
	 */
	@Override
	public void removeByO_A_T(
		long objectDefinitionId, boolean active, String triggerName) {

		for (ObjectActionEntry objectActionEntry :
				findByO_A_T(
					objectDefinitionId, active, triggerName, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			remove(objectActionEntry);
		}
	}

	/**
	 * Returns the number of object action entries where objectDefinitionId = &#63; and active = &#63; and triggerName = &#63;.
	 *
	 * @param objectDefinitionId the object definition ID
	 * @param active the active
	 * @param triggerName the trigger name
	 * @return the number of matching object action entries
	 */
	@Override
	public int countByO_A_T(
		long objectDefinitionId, boolean active, String triggerName) {

		triggerName = Objects.toString(triggerName, "");

		FinderPath finderPath = _finderPathCountByO_A_T;

		Object[] finderArgs = new Object[] {
			objectDefinitionId, active, triggerName
		};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_COUNT_OBJECTACTIONENTRY_WHERE);

			sb.append(_FINDER_COLUMN_O_A_T_OBJECTDEFINITIONID_2);

			sb.append(_FINDER_COLUMN_O_A_T_ACTIVE_2);

			boolean bindTriggerName = false;

			if (triggerName.isEmpty()) {
				sb.append(_FINDER_COLUMN_O_A_T_TRIGGERNAME_3);
			}
			else {
				bindTriggerName = true;

				sb.append(_FINDER_COLUMN_O_A_T_TRIGGERNAME_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(objectDefinitionId);

				queryPos.add(active);

				if (bindTriggerName) {
					queryPos.add(triggerName);
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

	private static final String _FINDER_COLUMN_O_A_T_OBJECTDEFINITIONID_2 =
		"objectActionEntry.objectDefinitionId = ? AND ";

	private static final String _FINDER_COLUMN_O_A_T_ACTIVE_2 =
		"objectActionEntry.active = ? AND ";

	private static final String _FINDER_COLUMN_O_A_T_TRIGGERNAME_2 =
		"objectActionEntry.triggerName = ?";

	private static final String _FINDER_COLUMN_O_A_T_TRIGGERNAME_3 =
		"(objectActionEntry.triggerName IS NULL OR objectActionEntry.triggerName = '')";

	public ObjectActionEntryPersistenceImpl() {
		Map<String, String> dbColumnNames = new HashMap<String, String>();

		dbColumnNames.put("uuid", "uuid_");
		dbColumnNames.put("active", "active_");
		dbColumnNames.put("settings", "settings_");

		setDBColumnNames(dbColumnNames);

		setModelClass(ObjectActionEntry.class);

		setModelImplClass(ObjectActionEntryImpl.class);
		setModelPKClass(long.class);

		setTable(ObjectActionEntryTable.INSTANCE);
	}

	/**
	 * Caches the object action entry in the entity cache if it is enabled.
	 *
	 * @param objectActionEntry the object action entry
	 */
	@Override
	public void cacheResult(ObjectActionEntry objectActionEntry) {
		entityCache.putResult(
			ObjectActionEntryImpl.class, objectActionEntry.getPrimaryKey(),
			objectActionEntry);
	}

	private int _valueObjectFinderCacheListThreshold;

	/**
	 * Caches the object action entries in the entity cache if it is enabled.
	 *
	 * @param objectActionEntries the object action entries
	 */
	@Override
	public void cacheResult(List<ObjectActionEntry> objectActionEntries) {
		if ((_valueObjectFinderCacheListThreshold == 0) ||
			((_valueObjectFinderCacheListThreshold > 0) &&
			 (objectActionEntries.size() >
				 _valueObjectFinderCacheListThreshold))) {

			return;
		}

		for (ObjectActionEntry objectActionEntry : objectActionEntries) {
			if (entityCache.getResult(
					ObjectActionEntryImpl.class,
					objectActionEntry.getPrimaryKey()) == null) {

				cacheResult(objectActionEntry);
			}
		}
	}

	/**
	 * Clears the cache for all object action entries.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(ObjectActionEntryImpl.class);

		finderCache.clearCache(ObjectActionEntryImpl.class);
	}

	/**
	 * Clears the cache for the object action entry.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(ObjectActionEntry objectActionEntry) {
		entityCache.removeResult(
			ObjectActionEntryImpl.class, objectActionEntry);
	}

	@Override
	public void clearCache(List<ObjectActionEntry> objectActionEntries) {
		for (ObjectActionEntry objectActionEntry : objectActionEntries) {
			entityCache.removeResult(
				ObjectActionEntryImpl.class, objectActionEntry);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(ObjectActionEntryImpl.class);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(ObjectActionEntryImpl.class, primaryKey);
		}
	}

	/**
	 * Creates a new object action entry with the primary key. Does not add the object action entry to the database.
	 *
	 * @param objectActionEntryId the primary key for the new object action entry
	 * @return the new object action entry
	 */
	@Override
	public ObjectActionEntry create(long objectActionEntryId) {
		ObjectActionEntry objectActionEntry = new ObjectActionEntryImpl();

		objectActionEntry.setNew(true);
		objectActionEntry.setPrimaryKey(objectActionEntryId);

		String uuid = PortalUUIDUtil.generate();

		objectActionEntry.setUuid(uuid);

		objectActionEntry.setCompanyId(CompanyThreadLocal.getCompanyId());

		return objectActionEntry;
	}

	/**
	 * Removes the object action entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param objectActionEntryId the primary key of the object action entry
	 * @return the object action entry that was removed
	 * @throws NoSuchObjectActionEntryException if a object action entry with the primary key could not be found
	 */
	@Override
	public ObjectActionEntry remove(long objectActionEntryId)
		throws NoSuchObjectActionEntryException {

		return remove((Serializable)objectActionEntryId);
	}

	/**
	 * Removes the object action entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the object action entry
	 * @return the object action entry that was removed
	 * @throws NoSuchObjectActionEntryException if a object action entry with the primary key could not be found
	 */
	@Override
	public ObjectActionEntry remove(Serializable primaryKey)
		throws NoSuchObjectActionEntryException {

		Session session = null;

		try {
			session = openSession();

			ObjectActionEntry objectActionEntry =
				(ObjectActionEntry)session.get(
					ObjectActionEntryImpl.class, primaryKey);

			if (objectActionEntry == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchObjectActionEntryException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(objectActionEntry);
		}
		catch (NoSuchObjectActionEntryException noSuchEntityException) {
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
	protected ObjectActionEntry removeImpl(
		ObjectActionEntry objectActionEntry) {

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(objectActionEntry)) {
				objectActionEntry = (ObjectActionEntry)session.get(
					ObjectActionEntryImpl.class,
					objectActionEntry.getPrimaryKeyObj());
			}

			if (objectActionEntry != null) {
				session.delete(objectActionEntry);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (objectActionEntry != null) {
			clearCache(objectActionEntry);
		}

		return objectActionEntry;
	}

	@Override
	public ObjectActionEntry updateImpl(ObjectActionEntry objectActionEntry) {
		boolean isNew = objectActionEntry.isNew();

		if (!(objectActionEntry instanceof ObjectActionEntryModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(objectActionEntry.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					objectActionEntry);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in objectActionEntry proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom ObjectActionEntry implementation " +
					objectActionEntry.getClass());
		}

		ObjectActionEntryModelImpl objectActionEntryModelImpl =
			(ObjectActionEntryModelImpl)objectActionEntry;

		if (Validator.isNull(objectActionEntry.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			objectActionEntry.setUuid(uuid);
		}

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Date date = new Date();

		if (isNew && (objectActionEntry.getCreateDate() == null)) {
			if (serviceContext == null) {
				objectActionEntry.setCreateDate(date);
			}
			else {
				objectActionEntry.setCreateDate(
					serviceContext.getCreateDate(date));
			}
		}

		if (!objectActionEntryModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				objectActionEntry.setModifiedDate(date);
			}
			else {
				objectActionEntry.setModifiedDate(
					serviceContext.getModifiedDate(date));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (isNew) {
				session.save(objectActionEntry);
			}
			else {
				objectActionEntry = (ObjectActionEntry)session.merge(
					objectActionEntry);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		entityCache.putResult(
			ObjectActionEntryImpl.class, objectActionEntryModelImpl, false,
			true);

		if (isNew) {
			objectActionEntry.setNew(false);
		}

		objectActionEntry.resetOriginalValues();

		return objectActionEntry;
	}

	/**
	 * Returns the object action entry with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the object action entry
	 * @return the object action entry
	 * @throws NoSuchObjectActionEntryException if a object action entry with the primary key could not be found
	 */
	@Override
	public ObjectActionEntry findByPrimaryKey(Serializable primaryKey)
		throws NoSuchObjectActionEntryException {

		ObjectActionEntry objectActionEntry = fetchByPrimaryKey(primaryKey);

		if (objectActionEntry == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchObjectActionEntryException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return objectActionEntry;
	}

	/**
	 * Returns the object action entry with the primary key or throws a <code>NoSuchObjectActionEntryException</code> if it could not be found.
	 *
	 * @param objectActionEntryId the primary key of the object action entry
	 * @return the object action entry
	 * @throws NoSuchObjectActionEntryException if a object action entry with the primary key could not be found
	 */
	@Override
	public ObjectActionEntry findByPrimaryKey(long objectActionEntryId)
		throws NoSuchObjectActionEntryException {

		return findByPrimaryKey((Serializable)objectActionEntryId);
	}

	/**
	 * Returns the object action entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param objectActionEntryId the primary key of the object action entry
	 * @return the object action entry, or <code>null</code> if a object action entry with the primary key could not be found
	 */
	@Override
	public ObjectActionEntry fetchByPrimaryKey(long objectActionEntryId) {
		return fetchByPrimaryKey((Serializable)objectActionEntryId);
	}

	/**
	 * Returns all the object action entries.
	 *
	 * @return the object action entries
	 */
	@Override
	public List<ObjectActionEntry> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the object action entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectActionEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of object action entries
	 * @param end the upper bound of the range of object action entries (not inclusive)
	 * @return the range of object action entries
	 */
	@Override
	public List<ObjectActionEntry> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the object action entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectActionEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of object action entries
	 * @param end the upper bound of the range of object action entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of object action entries
	 */
	@Override
	public List<ObjectActionEntry> findAll(
		int start, int end,
		OrderByComparator<ObjectActionEntry> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the object action entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectActionEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of object action entries
	 * @param end the upper bound of the range of object action entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of object action entries
	 */
	@Override
	public List<ObjectActionEntry> findAll(
		int start, int end,
		OrderByComparator<ObjectActionEntry> orderByComparator,
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

		List<ObjectActionEntry> list = null;

		if (useFinderCache) {
			list = (List<ObjectActionEntry>)finderCache.getResult(
				finderPath, finderArgs);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_OBJECTACTIONENTRY);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_OBJECTACTIONENTRY;

				sql = sql.concat(ObjectActionEntryModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<ObjectActionEntry>)QueryUtil.list(
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
	 * Removes all the object action entries from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (ObjectActionEntry objectActionEntry : findAll()) {
			remove(objectActionEntry);
		}
	}

	/**
	 * Returns the number of object action entries.
	 *
	 * @return the number of object action entries
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(
			_finderPathCountAll, FINDER_ARGS_EMPTY);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(_SQL_COUNT_OBJECTACTIONENTRY);

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
		return "objectActionEntryId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_OBJECTACTIONENTRY;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return ObjectActionEntryModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the object action entry persistence.
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

		_finderPathWithPaginationFindByO_A_T = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByO_A_T",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				String.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"objectDefinitionId", "active_", "triggerName"},
			true);

		_finderPathWithoutPaginationFindByO_A_T = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByO_A_T",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				String.class.getName()
			},
			new String[] {"objectDefinitionId", "active_", "triggerName"},
			true);

		_finderPathCountByO_A_T = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByO_A_T",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				String.class.getName()
			},
			new String[] {"objectDefinitionId", "active_", "triggerName"},
			false);
	}

	@Deactivate
	public void deactivate() {
		entityCache.removeCache(ObjectActionEntryImpl.class.getName());
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

	private static final String _SQL_SELECT_OBJECTACTIONENTRY =
		"SELECT objectActionEntry FROM ObjectActionEntry objectActionEntry";

	private static final String _SQL_SELECT_OBJECTACTIONENTRY_WHERE =
		"SELECT objectActionEntry FROM ObjectActionEntry objectActionEntry WHERE ";

	private static final String _SQL_COUNT_OBJECTACTIONENTRY =
		"SELECT COUNT(objectActionEntry) FROM ObjectActionEntry objectActionEntry";

	private static final String _SQL_COUNT_OBJECTACTIONENTRY_WHERE =
		"SELECT COUNT(objectActionEntry) FROM ObjectActionEntry objectActionEntry WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS = "objectActionEntry.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No ObjectActionEntry exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No ObjectActionEntry exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		ObjectActionEntryPersistenceImpl.class);

	private static final Set<String> _badColumnNames = SetUtil.fromArray(
		new String[] {"uuid", "active", "settings"});

	@Override
	protected FinderCache getFinderCache() {
		return finderCache;
	}

	@Reference
	private ObjectActionEntryModelArgumentsResolver
		_objectActionEntryModelArgumentsResolver;

}