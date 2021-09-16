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

import com.liferay.object.exception.NoSuchObjectLayoutTabException;
import com.liferay.object.model.ObjectLayoutTab;
import com.liferay.object.model.ObjectLayoutTabTable;
import com.liferay.object.model.impl.ObjectLayoutTabImpl;
import com.liferay.object.model.impl.ObjectLayoutTabModelImpl;
import com.liferay.object.service.persistence.ObjectLayoutTabPersistence;
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
 * The persistence implementation for the object layout tab service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Marco Leo
 * @generated
 */
@Component(service = {ObjectLayoutTabPersistence.class, BasePersistence.class})
public class ObjectLayoutTabPersistenceImpl
	extends BasePersistenceImpl<ObjectLayoutTab>
	implements ObjectLayoutTabPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>ObjectLayoutTabUtil</code> to access the object layout tab persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		ObjectLayoutTabImpl.class.getName();

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
	 * Returns all the object layout tabs where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching object layout tabs
	 */
	@Override
	public List<ObjectLayoutTab> findByUuid(String uuid) {
		return findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the object layout tabs where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectLayoutTabModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of object layout tabs
	 * @param end the upper bound of the range of object layout tabs (not inclusive)
	 * @return the range of matching object layout tabs
	 */
	@Override
	public List<ObjectLayoutTab> findByUuid(String uuid, int start, int end) {
		return findByUuid(uuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the object layout tabs where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectLayoutTabModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of object layout tabs
	 * @param end the upper bound of the range of object layout tabs (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching object layout tabs
	 */
	@Override
	public List<ObjectLayoutTab> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<ObjectLayoutTab> orderByComparator) {

		return findByUuid(uuid, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the object layout tabs where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectLayoutTabModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of object layout tabs
	 * @param end the upper bound of the range of object layout tabs (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching object layout tabs
	 */
	@Override
	public List<ObjectLayoutTab> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<ObjectLayoutTab> orderByComparator,
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

		List<ObjectLayoutTab> list = null;

		if (useFinderCache) {
			list = (List<ObjectLayoutTab>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (ObjectLayoutTab objectLayoutTab : list) {
					if (!uuid.equals(objectLayoutTab.getUuid())) {
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

			sb.append(_SQL_SELECT_OBJECTLAYOUTTAB_WHERE);

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
				sb.append(ObjectLayoutTabModelImpl.ORDER_BY_JPQL);
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

				list = (List<ObjectLayoutTab>)QueryUtil.list(
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
	 * Returns the first object layout tab in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object layout tab
	 * @throws NoSuchObjectLayoutTabException if a matching object layout tab could not be found
	 */
	@Override
	public ObjectLayoutTab findByUuid_First(
			String uuid, OrderByComparator<ObjectLayoutTab> orderByComparator)
		throws NoSuchObjectLayoutTabException {

		ObjectLayoutTab objectLayoutTab = fetchByUuid_First(
			uuid, orderByComparator);

		if (objectLayoutTab != null) {
			return objectLayoutTab;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append("}");

		throw new NoSuchObjectLayoutTabException(sb.toString());
	}

	/**
	 * Returns the first object layout tab in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object layout tab, or <code>null</code> if a matching object layout tab could not be found
	 */
	@Override
	public ObjectLayoutTab fetchByUuid_First(
		String uuid, OrderByComparator<ObjectLayoutTab> orderByComparator) {

		List<ObjectLayoutTab> list = findByUuid(uuid, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last object layout tab in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object layout tab
	 * @throws NoSuchObjectLayoutTabException if a matching object layout tab could not be found
	 */
	@Override
	public ObjectLayoutTab findByUuid_Last(
			String uuid, OrderByComparator<ObjectLayoutTab> orderByComparator)
		throws NoSuchObjectLayoutTabException {

		ObjectLayoutTab objectLayoutTab = fetchByUuid_Last(
			uuid, orderByComparator);

		if (objectLayoutTab != null) {
			return objectLayoutTab;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append("}");

		throw new NoSuchObjectLayoutTabException(sb.toString());
	}

	/**
	 * Returns the last object layout tab in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object layout tab, or <code>null</code> if a matching object layout tab could not be found
	 */
	@Override
	public ObjectLayoutTab fetchByUuid_Last(
		String uuid, OrderByComparator<ObjectLayoutTab> orderByComparator) {

		int count = countByUuid(uuid);

		if (count == 0) {
			return null;
		}

		List<ObjectLayoutTab> list = findByUuid(
			uuid, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the object layout tabs before and after the current object layout tab in the ordered set where uuid = &#63;.
	 *
	 * @param objectLayoutTabId the primary key of the current object layout tab
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next object layout tab
	 * @throws NoSuchObjectLayoutTabException if a object layout tab with the primary key could not be found
	 */
	@Override
	public ObjectLayoutTab[] findByUuid_PrevAndNext(
			long objectLayoutTabId, String uuid,
			OrderByComparator<ObjectLayoutTab> orderByComparator)
		throws NoSuchObjectLayoutTabException {

		uuid = Objects.toString(uuid, "");

		ObjectLayoutTab objectLayoutTab = findByPrimaryKey(objectLayoutTabId);

		Session session = null;

		try {
			session = openSession();

			ObjectLayoutTab[] array = new ObjectLayoutTabImpl[3];

			array[0] = getByUuid_PrevAndNext(
				session, objectLayoutTab, uuid, orderByComparator, true);

			array[1] = objectLayoutTab;

			array[2] = getByUuid_PrevAndNext(
				session, objectLayoutTab, uuid, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected ObjectLayoutTab getByUuid_PrevAndNext(
		Session session, ObjectLayoutTab objectLayoutTab, String uuid,
		OrderByComparator<ObjectLayoutTab> orderByComparator,
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

		sb.append(_SQL_SELECT_OBJECTLAYOUTTAB_WHERE);

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
			sb.append(ObjectLayoutTabModelImpl.ORDER_BY_JPQL);
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
						objectLayoutTab)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<ObjectLayoutTab> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the object layout tabs where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	@Override
	public void removeByUuid(String uuid) {
		for (ObjectLayoutTab objectLayoutTab :
				findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(objectLayoutTab);
		}
	}

	/**
	 * Returns the number of object layout tabs where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching object layout tabs
	 */
	@Override
	public int countByUuid(String uuid) {
		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = _finderPathCountByUuid;

		Object[] finderArgs = new Object[] {uuid};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_OBJECTLAYOUTTAB_WHERE);

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
		"objectLayoutTab.uuid = ?";

	private static final String _FINDER_COLUMN_UUID_UUID_3 =
		"(objectLayoutTab.uuid IS NULL OR objectLayoutTab.uuid = '')";

	private FinderPath _finderPathWithPaginationFindByUuid_C;
	private FinderPath _finderPathWithoutPaginationFindByUuid_C;
	private FinderPath _finderPathCountByUuid_C;

	/**
	 * Returns all the object layout tabs where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching object layout tabs
	 */
	@Override
	public List<ObjectLayoutTab> findByUuid_C(String uuid, long companyId) {
		return findByUuid_C(
			uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the object layout tabs where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectLayoutTabModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of object layout tabs
	 * @param end the upper bound of the range of object layout tabs (not inclusive)
	 * @return the range of matching object layout tabs
	 */
	@Override
	public List<ObjectLayoutTab> findByUuid_C(
		String uuid, long companyId, int start, int end) {

		return findByUuid_C(uuid, companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the object layout tabs where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectLayoutTabModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of object layout tabs
	 * @param end the upper bound of the range of object layout tabs (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching object layout tabs
	 */
	@Override
	public List<ObjectLayoutTab> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<ObjectLayoutTab> orderByComparator) {

		return findByUuid_C(
			uuid, companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the object layout tabs where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectLayoutTabModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of object layout tabs
	 * @param end the upper bound of the range of object layout tabs (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching object layout tabs
	 */
	@Override
	public List<ObjectLayoutTab> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<ObjectLayoutTab> orderByComparator,
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

		List<ObjectLayoutTab> list = null;

		if (useFinderCache) {
			list = (List<ObjectLayoutTab>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (ObjectLayoutTab objectLayoutTab : list) {
					if (!uuid.equals(objectLayoutTab.getUuid()) ||
						(companyId != objectLayoutTab.getCompanyId())) {

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

			sb.append(_SQL_SELECT_OBJECTLAYOUTTAB_WHERE);

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
				sb.append(ObjectLayoutTabModelImpl.ORDER_BY_JPQL);
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

				list = (List<ObjectLayoutTab>)QueryUtil.list(
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
	 * Returns the first object layout tab in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object layout tab
	 * @throws NoSuchObjectLayoutTabException if a matching object layout tab could not be found
	 */
	@Override
	public ObjectLayoutTab findByUuid_C_First(
			String uuid, long companyId,
			OrderByComparator<ObjectLayoutTab> orderByComparator)
		throws NoSuchObjectLayoutTabException {

		ObjectLayoutTab objectLayoutTab = fetchByUuid_C_First(
			uuid, companyId, orderByComparator);

		if (objectLayoutTab != null) {
			return objectLayoutTab;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append(", companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchObjectLayoutTabException(sb.toString());
	}

	/**
	 * Returns the first object layout tab in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object layout tab, or <code>null</code> if a matching object layout tab could not be found
	 */
	@Override
	public ObjectLayoutTab fetchByUuid_C_First(
		String uuid, long companyId,
		OrderByComparator<ObjectLayoutTab> orderByComparator) {

		List<ObjectLayoutTab> list = findByUuid_C(
			uuid, companyId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last object layout tab in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object layout tab
	 * @throws NoSuchObjectLayoutTabException if a matching object layout tab could not be found
	 */
	@Override
	public ObjectLayoutTab findByUuid_C_Last(
			String uuid, long companyId,
			OrderByComparator<ObjectLayoutTab> orderByComparator)
		throws NoSuchObjectLayoutTabException {

		ObjectLayoutTab objectLayoutTab = fetchByUuid_C_Last(
			uuid, companyId, orderByComparator);

		if (objectLayoutTab != null) {
			return objectLayoutTab;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append(", companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchObjectLayoutTabException(sb.toString());
	}

	/**
	 * Returns the last object layout tab in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object layout tab, or <code>null</code> if a matching object layout tab could not be found
	 */
	@Override
	public ObjectLayoutTab fetchByUuid_C_Last(
		String uuid, long companyId,
		OrderByComparator<ObjectLayoutTab> orderByComparator) {

		int count = countByUuid_C(uuid, companyId);

		if (count == 0) {
			return null;
		}

		List<ObjectLayoutTab> list = findByUuid_C(
			uuid, companyId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the object layout tabs before and after the current object layout tab in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param objectLayoutTabId the primary key of the current object layout tab
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next object layout tab
	 * @throws NoSuchObjectLayoutTabException if a object layout tab with the primary key could not be found
	 */
	@Override
	public ObjectLayoutTab[] findByUuid_C_PrevAndNext(
			long objectLayoutTabId, String uuid, long companyId,
			OrderByComparator<ObjectLayoutTab> orderByComparator)
		throws NoSuchObjectLayoutTabException {

		uuid = Objects.toString(uuid, "");

		ObjectLayoutTab objectLayoutTab = findByPrimaryKey(objectLayoutTabId);

		Session session = null;

		try {
			session = openSession();

			ObjectLayoutTab[] array = new ObjectLayoutTabImpl[3];

			array[0] = getByUuid_C_PrevAndNext(
				session, objectLayoutTab, uuid, companyId, orderByComparator,
				true);

			array[1] = objectLayoutTab;

			array[2] = getByUuid_C_PrevAndNext(
				session, objectLayoutTab, uuid, companyId, orderByComparator,
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

	protected ObjectLayoutTab getByUuid_C_PrevAndNext(
		Session session, ObjectLayoutTab objectLayoutTab, String uuid,
		long companyId, OrderByComparator<ObjectLayoutTab> orderByComparator,
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

		sb.append(_SQL_SELECT_OBJECTLAYOUTTAB_WHERE);

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
			sb.append(ObjectLayoutTabModelImpl.ORDER_BY_JPQL);
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
						objectLayoutTab)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<ObjectLayoutTab> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the object layout tabs where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	@Override
	public void removeByUuid_C(String uuid, long companyId) {
		for (ObjectLayoutTab objectLayoutTab :
				findByUuid_C(
					uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(objectLayoutTab);
		}
	}

	/**
	 * Returns the number of object layout tabs where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching object layout tabs
	 */
	@Override
	public int countByUuid_C(String uuid, long companyId) {
		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = _finderPathCountByUuid_C;

		Object[] finderArgs = new Object[] {uuid, companyId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_OBJECTLAYOUTTAB_WHERE);

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
		"objectLayoutTab.uuid = ? AND ";

	private static final String _FINDER_COLUMN_UUID_C_UUID_3 =
		"(objectLayoutTab.uuid IS NULL OR objectLayoutTab.uuid = '') AND ";

	private static final String _FINDER_COLUMN_UUID_C_COMPANYID_2 =
		"objectLayoutTab.companyId = ?";

	private FinderPath _finderPathWithPaginationFindByObjectLayoutId;
	private FinderPath _finderPathWithoutPaginationFindByObjectLayoutId;
	private FinderPath _finderPathCountByObjectLayoutId;

	/**
	 * Returns all the object layout tabs where objectLayoutId = &#63;.
	 *
	 * @param objectLayoutId the object layout ID
	 * @return the matching object layout tabs
	 */
	@Override
	public List<ObjectLayoutTab> findByObjectLayoutId(long objectLayoutId) {
		return findByObjectLayoutId(
			objectLayoutId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the object layout tabs where objectLayoutId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectLayoutTabModelImpl</code>.
	 * </p>
	 *
	 * @param objectLayoutId the object layout ID
	 * @param start the lower bound of the range of object layout tabs
	 * @param end the upper bound of the range of object layout tabs (not inclusive)
	 * @return the range of matching object layout tabs
	 */
	@Override
	public List<ObjectLayoutTab> findByObjectLayoutId(
		long objectLayoutId, int start, int end) {

		return findByObjectLayoutId(objectLayoutId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the object layout tabs where objectLayoutId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectLayoutTabModelImpl</code>.
	 * </p>
	 *
	 * @param objectLayoutId the object layout ID
	 * @param start the lower bound of the range of object layout tabs
	 * @param end the upper bound of the range of object layout tabs (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching object layout tabs
	 */
	@Override
	public List<ObjectLayoutTab> findByObjectLayoutId(
		long objectLayoutId, int start, int end,
		OrderByComparator<ObjectLayoutTab> orderByComparator) {

		return findByObjectLayoutId(
			objectLayoutId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the object layout tabs where objectLayoutId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectLayoutTabModelImpl</code>.
	 * </p>
	 *
	 * @param objectLayoutId the object layout ID
	 * @param start the lower bound of the range of object layout tabs
	 * @param end the upper bound of the range of object layout tabs (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching object layout tabs
	 */
	@Override
	public List<ObjectLayoutTab> findByObjectLayoutId(
		long objectLayoutId, int start, int end,
		OrderByComparator<ObjectLayoutTab> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByObjectLayoutId;
				finderArgs = new Object[] {objectLayoutId};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByObjectLayoutId;
			finderArgs = new Object[] {
				objectLayoutId, start, end, orderByComparator
			};
		}

		List<ObjectLayoutTab> list = null;

		if (useFinderCache) {
			list = (List<ObjectLayoutTab>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (ObjectLayoutTab objectLayoutTab : list) {
					if (objectLayoutId != objectLayoutTab.getObjectLayoutId()) {
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

			sb.append(_SQL_SELECT_OBJECTLAYOUTTAB_WHERE);

			sb.append(_FINDER_COLUMN_OBJECTLAYOUTID_OBJECTLAYOUTID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(ObjectLayoutTabModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(objectLayoutId);

				list = (List<ObjectLayoutTab>)QueryUtil.list(
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
	 * Returns the first object layout tab in the ordered set where objectLayoutId = &#63;.
	 *
	 * @param objectLayoutId the object layout ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object layout tab
	 * @throws NoSuchObjectLayoutTabException if a matching object layout tab could not be found
	 */
	@Override
	public ObjectLayoutTab findByObjectLayoutId_First(
			long objectLayoutId,
			OrderByComparator<ObjectLayoutTab> orderByComparator)
		throws NoSuchObjectLayoutTabException {

		ObjectLayoutTab objectLayoutTab = fetchByObjectLayoutId_First(
			objectLayoutId, orderByComparator);

		if (objectLayoutTab != null) {
			return objectLayoutTab;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("objectLayoutId=");
		sb.append(objectLayoutId);

		sb.append("}");

		throw new NoSuchObjectLayoutTabException(sb.toString());
	}

	/**
	 * Returns the first object layout tab in the ordered set where objectLayoutId = &#63;.
	 *
	 * @param objectLayoutId the object layout ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object layout tab, or <code>null</code> if a matching object layout tab could not be found
	 */
	@Override
	public ObjectLayoutTab fetchByObjectLayoutId_First(
		long objectLayoutId,
		OrderByComparator<ObjectLayoutTab> orderByComparator) {

		List<ObjectLayoutTab> list = findByObjectLayoutId(
			objectLayoutId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last object layout tab in the ordered set where objectLayoutId = &#63;.
	 *
	 * @param objectLayoutId the object layout ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object layout tab
	 * @throws NoSuchObjectLayoutTabException if a matching object layout tab could not be found
	 */
	@Override
	public ObjectLayoutTab findByObjectLayoutId_Last(
			long objectLayoutId,
			OrderByComparator<ObjectLayoutTab> orderByComparator)
		throws NoSuchObjectLayoutTabException {

		ObjectLayoutTab objectLayoutTab = fetchByObjectLayoutId_Last(
			objectLayoutId, orderByComparator);

		if (objectLayoutTab != null) {
			return objectLayoutTab;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("objectLayoutId=");
		sb.append(objectLayoutId);

		sb.append("}");

		throw new NoSuchObjectLayoutTabException(sb.toString());
	}

	/**
	 * Returns the last object layout tab in the ordered set where objectLayoutId = &#63;.
	 *
	 * @param objectLayoutId the object layout ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object layout tab, or <code>null</code> if a matching object layout tab could not be found
	 */
	@Override
	public ObjectLayoutTab fetchByObjectLayoutId_Last(
		long objectLayoutId,
		OrderByComparator<ObjectLayoutTab> orderByComparator) {

		int count = countByObjectLayoutId(objectLayoutId);

		if (count == 0) {
			return null;
		}

		List<ObjectLayoutTab> list = findByObjectLayoutId(
			objectLayoutId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the object layout tabs before and after the current object layout tab in the ordered set where objectLayoutId = &#63;.
	 *
	 * @param objectLayoutTabId the primary key of the current object layout tab
	 * @param objectLayoutId the object layout ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next object layout tab
	 * @throws NoSuchObjectLayoutTabException if a object layout tab with the primary key could not be found
	 */
	@Override
	public ObjectLayoutTab[] findByObjectLayoutId_PrevAndNext(
			long objectLayoutTabId, long objectLayoutId,
			OrderByComparator<ObjectLayoutTab> orderByComparator)
		throws NoSuchObjectLayoutTabException {

		ObjectLayoutTab objectLayoutTab = findByPrimaryKey(objectLayoutTabId);

		Session session = null;

		try {
			session = openSession();

			ObjectLayoutTab[] array = new ObjectLayoutTabImpl[3];

			array[0] = getByObjectLayoutId_PrevAndNext(
				session, objectLayoutTab, objectLayoutId, orderByComparator,
				true);

			array[1] = objectLayoutTab;

			array[2] = getByObjectLayoutId_PrevAndNext(
				session, objectLayoutTab, objectLayoutId, orderByComparator,
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

	protected ObjectLayoutTab getByObjectLayoutId_PrevAndNext(
		Session session, ObjectLayoutTab objectLayoutTab, long objectLayoutId,
		OrderByComparator<ObjectLayoutTab> orderByComparator,
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

		sb.append(_SQL_SELECT_OBJECTLAYOUTTAB_WHERE);

		sb.append(_FINDER_COLUMN_OBJECTLAYOUTID_OBJECTLAYOUTID_2);

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
			sb.append(ObjectLayoutTabModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(objectLayoutId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						objectLayoutTab)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<ObjectLayoutTab> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the object layout tabs where objectLayoutId = &#63; from the database.
	 *
	 * @param objectLayoutId the object layout ID
	 */
	@Override
	public void removeByObjectLayoutId(long objectLayoutId) {
		for (ObjectLayoutTab objectLayoutTab :
				findByObjectLayoutId(
					objectLayoutId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(objectLayoutTab);
		}
	}

	/**
	 * Returns the number of object layout tabs where objectLayoutId = &#63;.
	 *
	 * @param objectLayoutId the object layout ID
	 * @return the number of matching object layout tabs
	 */
	@Override
	public int countByObjectLayoutId(long objectLayoutId) {
		FinderPath finderPath = _finderPathCountByObjectLayoutId;

		Object[] finderArgs = new Object[] {objectLayoutId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_OBJECTLAYOUTTAB_WHERE);

			sb.append(_FINDER_COLUMN_OBJECTLAYOUTID_OBJECTLAYOUTID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(objectLayoutId);

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

	private static final String _FINDER_COLUMN_OBJECTLAYOUTID_OBJECTLAYOUTID_2 =
		"objectLayoutTab.objectLayoutId = ?";

	public ObjectLayoutTabPersistenceImpl() {
		Map<String, String> dbColumnNames = new HashMap<String, String>();

		dbColumnNames.put("uuid", "uuid_");

		setDBColumnNames(dbColumnNames);

		setModelClass(ObjectLayoutTab.class);

		setModelImplClass(ObjectLayoutTabImpl.class);
		setModelPKClass(long.class);

		setTable(ObjectLayoutTabTable.INSTANCE);
	}

	/**
	 * Caches the object layout tab in the entity cache if it is enabled.
	 *
	 * @param objectLayoutTab the object layout tab
	 */
	@Override
	public void cacheResult(ObjectLayoutTab objectLayoutTab) {
		entityCache.putResult(
			ObjectLayoutTabImpl.class, objectLayoutTab.getPrimaryKey(),
			objectLayoutTab);
	}

	private int _valueObjectFinderCacheListThreshold;

	/**
	 * Caches the object layout tabs in the entity cache if it is enabled.
	 *
	 * @param objectLayoutTabs the object layout tabs
	 */
	@Override
	public void cacheResult(List<ObjectLayoutTab> objectLayoutTabs) {
		if ((_valueObjectFinderCacheListThreshold == 0) ||
			((_valueObjectFinderCacheListThreshold > 0) &&
			 (objectLayoutTabs.size() >
				 _valueObjectFinderCacheListThreshold))) {

			return;
		}

		for (ObjectLayoutTab objectLayoutTab : objectLayoutTabs) {
			if (entityCache.getResult(
					ObjectLayoutTabImpl.class,
					objectLayoutTab.getPrimaryKey()) == null) {

				cacheResult(objectLayoutTab);
			}
		}
	}

	/**
	 * Clears the cache for all object layout tabs.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(ObjectLayoutTabImpl.class);

		finderCache.clearCache(ObjectLayoutTabImpl.class);
	}

	/**
	 * Clears the cache for the object layout tab.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(ObjectLayoutTab objectLayoutTab) {
		entityCache.removeResult(ObjectLayoutTabImpl.class, objectLayoutTab);
	}

	@Override
	public void clearCache(List<ObjectLayoutTab> objectLayoutTabs) {
		for (ObjectLayoutTab objectLayoutTab : objectLayoutTabs) {
			entityCache.removeResult(
				ObjectLayoutTabImpl.class, objectLayoutTab);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(ObjectLayoutTabImpl.class);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(ObjectLayoutTabImpl.class, primaryKey);
		}
	}

	/**
	 * Creates a new object layout tab with the primary key. Does not add the object layout tab to the database.
	 *
	 * @param objectLayoutTabId the primary key for the new object layout tab
	 * @return the new object layout tab
	 */
	@Override
	public ObjectLayoutTab create(long objectLayoutTabId) {
		ObjectLayoutTab objectLayoutTab = new ObjectLayoutTabImpl();

		objectLayoutTab.setNew(true);
		objectLayoutTab.setPrimaryKey(objectLayoutTabId);

		String uuid = PortalUUIDUtil.generate();

		objectLayoutTab.setUuid(uuid);

		objectLayoutTab.setCompanyId(CompanyThreadLocal.getCompanyId());

		return objectLayoutTab;
	}

	/**
	 * Removes the object layout tab with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param objectLayoutTabId the primary key of the object layout tab
	 * @return the object layout tab that was removed
	 * @throws NoSuchObjectLayoutTabException if a object layout tab with the primary key could not be found
	 */
	@Override
	public ObjectLayoutTab remove(long objectLayoutTabId)
		throws NoSuchObjectLayoutTabException {

		return remove((Serializable)objectLayoutTabId);
	}

	/**
	 * Removes the object layout tab with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the object layout tab
	 * @return the object layout tab that was removed
	 * @throws NoSuchObjectLayoutTabException if a object layout tab with the primary key could not be found
	 */
	@Override
	public ObjectLayoutTab remove(Serializable primaryKey)
		throws NoSuchObjectLayoutTabException {

		Session session = null;

		try {
			session = openSession();

			ObjectLayoutTab objectLayoutTab = (ObjectLayoutTab)session.get(
				ObjectLayoutTabImpl.class, primaryKey);

			if (objectLayoutTab == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchObjectLayoutTabException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(objectLayoutTab);
		}
		catch (NoSuchObjectLayoutTabException noSuchEntityException) {
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
	protected ObjectLayoutTab removeImpl(ObjectLayoutTab objectLayoutTab) {
		Session session = null;

		try {
			session = openSession();

			if (!session.contains(objectLayoutTab)) {
				objectLayoutTab = (ObjectLayoutTab)session.get(
					ObjectLayoutTabImpl.class,
					objectLayoutTab.getPrimaryKeyObj());
			}

			if (objectLayoutTab != null) {
				session.delete(objectLayoutTab);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (objectLayoutTab != null) {
			clearCache(objectLayoutTab);
		}

		return objectLayoutTab;
	}

	@Override
	public ObjectLayoutTab updateImpl(ObjectLayoutTab objectLayoutTab) {
		boolean isNew = objectLayoutTab.isNew();

		if (!(objectLayoutTab instanceof ObjectLayoutTabModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(objectLayoutTab.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					objectLayoutTab);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in objectLayoutTab proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom ObjectLayoutTab implementation " +
					objectLayoutTab.getClass());
		}

		ObjectLayoutTabModelImpl objectLayoutTabModelImpl =
			(ObjectLayoutTabModelImpl)objectLayoutTab;

		if (Validator.isNull(objectLayoutTab.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			objectLayoutTab.setUuid(uuid);
		}

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Date date = new Date();

		if (isNew && (objectLayoutTab.getCreateDate() == null)) {
			if (serviceContext == null) {
				objectLayoutTab.setCreateDate(date);
			}
			else {
				objectLayoutTab.setCreateDate(
					serviceContext.getCreateDate(date));
			}
		}

		if (!objectLayoutTabModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				objectLayoutTab.setModifiedDate(date);
			}
			else {
				objectLayoutTab.setModifiedDate(
					serviceContext.getModifiedDate(date));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (isNew) {
				session.save(objectLayoutTab);
			}
			else {
				objectLayoutTab = (ObjectLayoutTab)session.merge(
					objectLayoutTab);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		entityCache.putResult(
			ObjectLayoutTabImpl.class, objectLayoutTabModelImpl, false, true);

		if (isNew) {
			objectLayoutTab.setNew(false);
		}

		objectLayoutTab.resetOriginalValues();

		return objectLayoutTab;
	}

	/**
	 * Returns the object layout tab with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the object layout tab
	 * @return the object layout tab
	 * @throws NoSuchObjectLayoutTabException if a object layout tab with the primary key could not be found
	 */
	@Override
	public ObjectLayoutTab findByPrimaryKey(Serializable primaryKey)
		throws NoSuchObjectLayoutTabException {

		ObjectLayoutTab objectLayoutTab = fetchByPrimaryKey(primaryKey);

		if (objectLayoutTab == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchObjectLayoutTabException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return objectLayoutTab;
	}

	/**
	 * Returns the object layout tab with the primary key or throws a <code>NoSuchObjectLayoutTabException</code> if it could not be found.
	 *
	 * @param objectLayoutTabId the primary key of the object layout tab
	 * @return the object layout tab
	 * @throws NoSuchObjectLayoutTabException if a object layout tab with the primary key could not be found
	 */
	@Override
	public ObjectLayoutTab findByPrimaryKey(long objectLayoutTabId)
		throws NoSuchObjectLayoutTabException {

		return findByPrimaryKey((Serializable)objectLayoutTabId);
	}

	/**
	 * Returns the object layout tab with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param objectLayoutTabId the primary key of the object layout tab
	 * @return the object layout tab, or <code>null</code> if a object layout tab with the primary key could not be found
	 */
	@Override
	public ObjectLayoutTab fetchByPrimaryKey(long objectLayoutTabId) {
		return fetchByPrimaryKey((Serializable)objectLayoutTabId);
	}

	/**
	 * Returns all the object layout tabs.
	 *
	 * @return the object layout tabs
	 */
	@Override
	public List<ObjectLayoutTab> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the object layout tabs.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectLayoutTabModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of object layout tabs
	 * @param end the upper bound of the range of object layout tabs (not inclusive)
	 * @return the range of object layout tabs
	 */
	@Override
	public List<ObjectLayoutTab> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the object layout tabs.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectLayoutTabModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of object layout tabs
	 * @param end the upper bound of the range of object layout tabs (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of object layout tabs
	 */
	@Override
	public List<ObjectLayoutTab> findAll(
		int start, int end,
		OrderByComparator<ObjectLayoutTab> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the object layout tabs.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectLayoutTabModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of object layout tabs
	 * @param end the upper bound of the range of object layout tabs (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of object layout tabs
	 */
	@Override
	public List<ObjectLayoutTab> findAll(
		int start, int end,
		OrderByComparator<ObjectLayoutTab> orderByComparator,
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

		List<ObjectLayoutTab> list = null;

		if (useFinderCache) {
			list = (List<ObjectLayoutTab>)finderCache.getResult(
				finderPath, finderArgs);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_OBJECTLAYOUTTAB);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_OBJECTLAYOUTTAB;

				sql = sql.concat(ObjectLayoutTabModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<ObjectLayoutTab>)QueryUtil.list(
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
	 * Removes all the object layout tabs from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (ObjectLayoutTab objectLayoutTab : findAll()) {
			remove(objectLayoutTab);
		}
	}

	/**
	 * Returns the number of object layout tabs.
	 *
	 * @return the number of object layout tabs
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(
			_finderPathCountAll, FINDER_ARGS_EMPTY);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(_SQL_COUNT_OBJECTLAYOUTTAB);

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
		return "objectLayoutTabId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_OBJECTLAYOUTTAB;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return ObjectLayoutTabModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the object layout tab persistence.
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

		_finderPathWithPaginationFindByObjectLayoutId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByObjectLayoutId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"objectLayoutId"}, true);

		_finderPathWithoutPaginationFindByObjectLayoutId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByObjectLayoutId",
			new String[] {Long.class.getName()},
			new String[] {"objectLayoutId"}, true);

		_finderPathCountByObjectLayoutId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByObjectLayoutId",
			new String[] {Long.class.getName()},
			new String[] {"objectLayoutId"}, false);
	}

	@Deactivate
	public void deactivate() {
		entityCache.removeCache(ObjectLayoutTabImpl.class.getName());
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

	private static final String _SQL_SELECT_OBJECTLAYOUTTAB =
		"SELECT objectLayoutTab FROM ObjectLayoutTab objectLayoutTab";

	private static final String _SQL_SELECT_OBJECTLAYOUTTAB_WHERE =
		"SELECT objectLayoutTab FROM ObjectLayoutTab objectLayoutTab WHERE ";

	private static final String _SQL_COUNT_OBJECTLAYOUTTAB =
		"SELECT COUNT(objectLayoutTab) FROM ObjectLayoutTab objectLayoutTab";

	private static final String _SQL_COUNT_OBJECTLAYOUTTAB_WHERE =
		"SELECT COUNT(objectLayoutTab) FROM ObjectLayoutTab objectLayoutTab WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS = "objectLayoutTab.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No ObjectLayoutTab exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No ObjectLayoutTab exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		ObjectLayoutTabPersistenceImpl.class);

	private static final Set<String> _badColumnNames = SetUtil.fromArray(
		new String[] {"uuid"});

	@Override
	protected FinderCache getFinderCache() {
		return finderCache;
	}

	@Reference
	private ObjectLayoutTabModelArgumentsResolver
		_objectLayoutTabModelArgumentsResolver;

}