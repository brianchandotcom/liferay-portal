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

import com.liferay.object.exception.NoSuchObjectFieldSettingException;
import com.liferay.object.model.ObjectFieldSetting;
import com.liferay.object.model.ObjectFieldSettingTable;
import com.liferay.object.model.impl.ObjectFieldSettingImpl;
import com.liferay.object.model.impl.ObjectFieldSettingModelImpl;
import com.liferay.object.service.persistence.ObjectFieldSettingPersistence;
import com.liferay.object.service.persistence.ObjectFieldSettingUtil;
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
 * The persistence implementation for the object field setting service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Marco Leo
 * @generated
 */
@Component(service = ObjectFieldSettingPersistence.class)
public class ObjectFieldSettingPersistenceImpl
	extends BasePersistenceImpl<ObjectFieldSetting>
	implements ObjectFieldSettingPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>ObjectFieldSettingUtil</code> to access the object field setting persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		ObjectFieldSettingImpl.class.getName();

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
	 * Returns all the object field settings where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching object field settings
	 */
	@Override
	public List<ObjectFieldSetting> findByUuid(String uuid) {
		return findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the object field settings where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectFieldSettingModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of object field settings
	 * @param end the upper bound of the range of object field settings (not inclusive)
	 * @return the range of matching object field settings
	 */
	@Override
	public List<ObjectFieldSetting> findByUuid(
		String uuid, int start, int end) {

		return findByUuid(uuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the object field settings where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectFieldSettingModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of object field settings
	 * @param end the upper bound of the range of object field settings (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching object field settings
	 */
	@Override
	public List<ObjectFieldSetting> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<ObjectFieldSetting> orderByComparator) {

		return findByUuid(uuid, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the object field settings where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectFieldSettingModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of object field settings
	 * @param end the upper bound of the range of object field settings (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching object field settings
	 */
	@Override
	public List<ObjectFieldSetting> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<ObjectFieldSetting> orderByComparator,
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

		List<ObjectFieldSetting> list = null;

		if (useFinderCache) {
			list = (List<ObjectFieldSetting>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (ObjectFieldSetting objectFieldSetting : list) {
					if (!uuid.equals(objectFieldSetting.getUuid())) {
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

			sb.append(_SQL_SELECT_OBJECTFIELDSETTING_WHERE);

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
				sb.append(ObjectFieldSettingModelImpl.ORDER_BY_JPQL);
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

				list = (List<ObjectFieldSetting>)QueryUtil.list(
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
	 * Returns the first object field setting in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object field setting
	 * @throws NoSuchObjectFieldSettingException if a matching object field setting could not be found
	 */
	@Override
	public ObjectFieldSetting findByUuid_First(
			String uuid,
			OrderByComparator<ObjectFieldSetting> orderByComparator)
		throws NoSuchObjectFieldSettingException {

		ObjectFieldSetting objectFieldSetting = fetchByUuid_First(
			uuid, orderByComparator);

		if (objectFieldSetting != null) {
			return objectFieldSetting;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append("}");

		throw new NoSuchObjectFieldSettingException(sb.toString());
	}

	/**
	 * Returns the first object field setting in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object field setting, or <code>null</code> if a matching object field setting could not be found
	 */
	@Override
	public ObjectFieldSetting fetchByUuid_First(
		String uuid, OrderByComparator<ObjectFieldSetting> orderByComparator) {

		List<ObjectFieldSetting> list = findByUuid(
			uuid, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last object field setting in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object field setting
	 * @throws NoSuchObjectFieldSettingException if a matching object field setting could not be found
	 */
	@Override
	public ObjectFieldSetting findByUuid_Last(
			String uuid,
			OrderByComparator<ObjectFieldSetting> orderByComparator)
		throws NoSuchObjectFieldSettingException {

		ObjectFieldSetting objectFieldSetting = fetchByUuid_Last(
			uuid, orderByComparator);

		if (objectFieldSetting != null) {
			return objectFieldSetting;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append("}");

		throw new NoSuchObjectFieldSettingException(sb.toString());
	}

	/**
	 * Returns the last object field setting in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object field setting, or <code>null</code> if a matching object field setting could not be found
	 */
	@Override
	public ObjectFieldSetting fetchByUuid_Last(
		String uuid, OrderByComparator<ObjectFieldSetting> orderByComparator) {

		int count = countByUuid(uuid);

		if (count == 0) {
			return null;
		}

		List<ObjectFieldSetting> list = findByUuid(
			uuid, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the object field settings before and after the current object field setting in the ordered set where uuid = &#63;.
	 *
	 * @param objectFieldSettingId the primary key of the current object field setting
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next object field setting
	 * @throws NoSuchObjectFieldSettingException if a object field setting with the primary key could not be found
	 */
	@Override
	public ObjectFieldSetting[] findByUuid_PrevAndNext(
			long objectFieldSettingId, String uuid,
			OrderByComparator<ObjectFieldSetting> orderByComparator)
		throws NoSuchObjectFieldSettingException {

		uuid = Objects.toString(uuid, "");

		ObjectFieldSetting objectFieldSetting = findByPrimaryKey(
			objectFieldSettingId);

		Session session = null;

		try {
			session = openSession();

			ObjectFieldSetting[] array = new ObjectFieldSettingImpl[3];

			array[0] = getByUuid_PrevAndNext(
				session, objectFieldSetting, uuid, orderByComparator, true);

			array[1] = objectFieldSetting;

			array[2] = getByUuid_PrevAndNext(
				session, objectFieldSetting, uuid, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected ObjectFieldSetting getByUuid_PrevAndNext(
		Session session, ObjectFieldSetting objectFieldSetting, String uuid,
		OrderByComparator<ObjectFieldSetting> orderByComparator,
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

		sb.append(_SQL_SELECT_OBJECTFIELDSETTING_WHERE);

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
			sb.append(ObjectFieldSettingModelImpl.ORDER_BY_JPQL);
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
						objectFieldSetting)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<ObjectFieldSetting> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the object field settings where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	@Override
	public void removeByUuid(String uuid) {
		for (ObjectFieldSetting objectFieldSetting :
				findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(objectFieldSetting);
		}
	}

	/**
	 * Returns the number of object field settings where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching object field settings
	 */
	@Override
	public int countByUuid(String uuid) {
		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = _finderPathCountByUuid;

		Object[] finderArgs = new Object[] {uuid};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_OBJECTFIELDSETTING_WHERE);

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
		"objectFieldSetting.uuid = ?";

	private static final String _FINDER_COLUMN_UUID_UUID_3 =
		"(objectFieldSetting.uuid IS NULL OR objectFieldSetting.uuid = '')";

	private FinderPath _finderPathWithPaginationFindByUuid_C;
	private FinderPath _finderPathWithoutPaginationFindByUuid_C;
	private FinderPath _finderPathCountByUuid_C;

	/**
	 * Returns all the object field settings where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching object field settings
	 */
	@Override
	public List<ObjectFieldSetting> findByUuid_C(String uuid, long companyId) {
		return findByUuid_C(
			uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the object field settings where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectFieldSettingModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of object field settings
	 * @param end the upper bound of the range of object field settings (not inclusive)
	 * @return the range of matching object field settings
	 */
	@Override
	public List<ObjectFieldSetting> findByUuid_C(
		String uuid, long companyId, int start, int end) {

		return findByUuid_C(uuid, companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the object field settings where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectFieldSettingModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of object field settings
	 * @param end the upper bound of the range of object field settings (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching object field settings
	 */
	@Override
	public List<ObjectFieldSetting> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<ObjectFieldSetting> orderByComparator) {

		return findByUuid_C(
			uuid, companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the object field settings where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectFieldSettingModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of object field settings
	 * @param end the upper bound of the range of object field settings (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching object field settings
	 */
	@Override
	public List<ObjectFieldSetting> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<ObjectFieldSetting> orderByComparator,
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

		List<ObjectFieldSetting> list = null;

		if (useFinderCache) {
			list = (List<ObjectFieldSetting>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (ObjectFieldSetting objectFieldSetting : list) {
					if (!uuid.equals(objectFieldSetting.getUuid()) ||
						(companyId != objectFieldSetting.getCompanyId())) {

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

			sb.append(_SQL_SELECT_OBJECTFIELDSETTING_WHERE);

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
				sb.append(ObjectFieldSettingModelImpl.ORDER_BY_JPQL);
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

				list = (List<ObjectFieldSetting>)QueryUtil.list(
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
	 * Returns the first object field setting in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object field setting
	 * @throws NoSuchObjectFieldSettingException if a matching object field setting could not be found
	 */
	@Override
	public ObjectFieldSetting findByUuid_C_First(
			String uuid, long companyId,
			OrderByComparator<ObjectFieldSetting> orderByComparator)
		throws NoSuchObjectFieldSettingException {

		ObjectFieldSetting objectFieldSetting = fetchByUuid_C_First(
			uuid, companyId, orderByComparator);

		if (objectFieldSetting != null) {
			return objectFieldSetting;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append(", companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchObjectFieldSettingException(sb.toString());
	}

	/**
	 * Returns the first object field setting in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object field setting, or <code>null</code> if a matching object field setting could not be found
	 */
	@Override
	public ObjectFieldSetting fetchByUuid_C_First(
		String uuid, long companyId,
		OrderByComparator<ObjectFieldSetting> orderByComparator) {

		List<ObjectFieldSetting> list = findByUuid_C(
			uuid, companyId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last object field setting in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object field setting
	 * @throws NoSuchObjectFieldSettingException if a matching object field setting could not be found
	 */
	@Override
	public ObjectFieldSetting findByUuid_C_Last(
			String uuid, long companyId,
			OrderByComparator<ObjectFieldSetting> orderByComparator)
		throws NoSuchObjectFieldSettingException {

		ObjectFieldSetting objectFieldSetting = fetchByUuid_C_Last(
			uuid, companyId, orderByComparator);

		if (objectFieldSetting != null) {
			return objectFieldSetting;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append(", companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchObjectFieldSettingException(sb.toString());
	}

	/**
	 * Returns the last object field setting in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object field setting, or <code>null</code> if a matching object field setting could not be found
	 */
	@Override
	public ObjectFieldSetting fetchByUuid_C_Last(
		String uuid, long companyId,
		OrderByComparator<ObjectFieldSetting> orderByComparator) {

		int count = countByUuid_C(uuid, companyId);

		if (count == 0) {
			return null;
		}

		List<ObjectFieldSetting> list = findByUuid_C(
			uuid, companyId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the object field settings before and after the current object field setting in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param objectFieldSettingId the primary key of the current object field setting
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next object field setting
	 * @throws NoSuchObjectFieldSettingException if a object field setting with the primary key could not be found
	 */
	@Override
	public ObjectFieldSetting[] findByUuid_C_PrevAndNext(
			long objectFieldSettingId, String uuid, long companyId,
			OrderByComparator<ObjectFieldSetting> orderByComparator)
		throws NoSuchObjectFieldSettingException {

		uuid = Objects.toString(uuid, "");

		ObjectFieldSetting objectFieldSetting = findByPrimaryKey(
			objectFieldSettingId);

		Session session = null;

		try {
			session = openSession();

			ObjectFieldSetting[] array = new ObjectFieldSettingImpl[3];

			array[0] = getByUuid_C_PrevAndNext(
				session, objectFieldSetting, uuid, companyId, orderByComparator,
				true);

			array[1] = objectFieldSetting;

			array[2] = getByUuid_C_PrevAndNext(
				session, objectFieldSetting, uuid, companyId, orderByComparator,
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

	protected ObjectFieldSetting getByUuid_C_PrevAndNext(
		Session session, ObjectFieldSetting objectFieldSetting, String uuid,
		long companyId, OrderByComparator<ObjectFieldSetting> orderByComparator,
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

		sb.append(_SQL_SELECT_OBJECTFIELDSETTING_WHERE);

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
			sb.append(ObjectFieldSettingModelImpl.ORDER_BY_JPQL);
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
						objectFieldSetting)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<ObjectFieldSetting> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the object field settings where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	@Override
	public void removeByUuid_C(String uuid, long companyId) {
		for (ObjectFieldSetting objectFieldSetting :
				findByUuid_C(
					uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(objectFieldSetting);
		}
	}

	/**
	 * Returns the number of object field settings where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching object field settings
	 */
	@Override
	public int countByUuid_C(String uuid, long companyId) {
		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = _finderPathCountByUuid_C;

		Object[] finderArgs = new Object[] {uuid, companyId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_OBJECTFIELDSETTING_WHERE);

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
		"objectFieldSetting.uuid = ? AND ";

	private static final String _FINDER_COLUMN_UUID_C_UUID_3 =
		"(objectFieldSetting.uuid IS NULL OR objectFieldSetting.uuid = '') AND ";

	private static final String _FINDER_COLUMN_UUID_C_COMPANYID_2 =
		"objectFieldSetting.companyId = ?";

	private FinderPath _finderPathWithPaginationFindByObjectFieldId;
	private FinderPath _finderPathWithoutPaginationFindByObjectFieldId;
	private FinderPath _finderPathCountByObjectFieldId;

	/**
	 * Returns all the object field settings where objectFieldId = &#63;.
	 *
	 * @param objectFieldId the object field ID
	 * @return the matching object field settings
	 */
	@Override
	public List<ObjectFieldSetting> findByObjectFieldId(long objectFieldId) {
		return findByObjectFieldId(
			objectFieldId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the object field settings where objectFieldId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectFieldSettingModelImpl</code>.
	 * </p>
	 *
	 * @param objectFieldId the object field ID
	 * @param start the lower bound of the range of object field settings
	 * @param end the upper bound of the range of object field settings (not inclusive)
	 * @return the range of matching object field settings
	 */
	@Override
	public List<ObjectFieldSetting> findByObjectFieldId(
		long objectFieldId, int start, int end) {

		return findByObjectFieldId(objectFieldId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the object field settings where objectFieldId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectFieldSettingModelImpl</code>.
	 * </p>
	 *
	 * @param objectFieldId the object field ID
	 * @param start the lower bound of the range of object field settings
	 * @param end the upper bound of the range of object field settings (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching object field settings
	 */
	@Override
	public List<ObjectFieldSetting> findByObjectFieldId(
		long objectFieldId, int start, int end,
		OrderByComparator<ObjectFieldSetting> orderByComparator) {

		return findByObjectFieldId(
			objectFieldId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the object field settings where objectFieldId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectFieldSettingModelImpl</code>.
	 * </p>
	 *
	 * @param objectFieldId the object field ID
	 * @param start the lower bound of the range of object field settings
	 * @param end the upper bound of the range of object field settings (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching object field settings
	 */
	@Override
	public List<ObjectFieldSetting> findByObjectFieldId(
		long objectFieldId, int start, int end,
		OrderByComparator<ObjectFieldSetting> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByObjectFieldId;
				finderArgs = new Object[] {objectFieldId};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByObjectFieldId;
			finderArgs = new Object[] {
				objectFieldId, start, end, orderByComparator
			};
		}

		List<ObjectFieldSetting> list = null;

		if (useFinderCache) {
			list = (List<ObjectFieldSetting>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (ObjectFieldSetting objectFieldSetting : list) {
					if (objectFieldId !=
							objectFieldSetting.getObjectFieldId()) {

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

			sb.append(_SQL_SELECT_OBJECTFIELDSETTING_WHERE);

			sb.append(_FINDER_COLUMN_OBJECTFIELDID_OBJECTFIELDID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(ObjectFieldSettingModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(objectFieldId);

				list = (List<ObjectFieldSetting>)QueryUtil.list(
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
	 * Returns the first object field setting in the ordered set where objectFieldId = &#63;.
	 *
	 * @param objectFieldId the object field ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object field setting
	 * @throws NoSuchObjectFieldSettingException if a matching object field setting could not be found
	 */
	@Override
	public ObjectFieldSetting findByObjectFieldId_First(
			long objectFieldId,
			OrderByComparator<ObjectFieldSetting> orderByComparator)
		throws NoSuchObjectFieldSettingException {

		ObjectFieldSetting objectFieldSetting = fetchByObjectFieldId_First(
			objectFieldId, orderByComparator);

		if (objectFieldSetting != null) {
			return objectFieldSetting;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("objectFieldId=");
		sb.append(objectFieldId);

		sb.append("}");

		throw new NoSuchObjectFieldSettingException(sb.toString());
	}

	/**
	 * Returns the first object field setting in the ordered set where objectFieldId = &#63;.
	 *
	 * @param objectFieldId the object field ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object field setting, or <code>null</code> if a matching object field setting could not be found
	 */
	@Override
	public ObjectFieldSetting fetchByObjectFieldId_First(
		long objectFieldId,
		OrderByComparator<ObjectFieldSetting> orderByComparator) {

		List<ObjectFieldSetting> list = findByObjectFieldId(
			objectFieldId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last object field setting in the ordered set where objectFieldId = &#63;.
	 *
	 * @param objectFieldId the object field ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object field setting
	 * @throws NoSuchObjectFieldSettingException if a matching object field setting could not be found
	 */
	@Override
	public ObjectFieldSetting findByObjectFieldId_Last(
			long objectFieldId,
			OrderByComparator<ObjectFieldSetting> orderByComparator)
		throws NoSuchObjectFieldSettingException {

		ObjectFieldSetting objectFieldSetting = fetchByObjectFieldId_Last(
			objectFieldId, orderByComparator);

		if (objectFieldSetting != null) {
			return objectFieldSetting;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("objectFieldId=");
		sb.append(objectFieldId);

		sb.append("}");

		throw new NoSuchObjectFieldSettingException(sb.toString());
	}

	/**
	 * Returns the last object field setting in the ordered set where objectFieldId = &#63;.
	 *
	 * @param objectFieldId the object field ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object field setting, or <code>null</code> if a matching object field setting could not be found
	 */
	@Override
	public ObjectFieldSetting fetchByObjectFieldId_Last(
		long objectFieldId,
		OrderByComparator<ObjectFieldSetting> orderByComparator) {

		int count = countByObjectFieldId(objectFieldId);

		if (count == 0) {
			return null;
		}

		List<ObjectFieldSetting> list = findByObjectFieldId(
			objectFieldId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the object field settings before and after the current object field setting in the ordered set where objectFieldId = &#63;.
	 *
	 * @param objectFieldSettingId the primary key of the current object field setting
	 * @param objectFieldId the object field ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next object field setting
	 * @throws NoSuchObjectFieldSettingException if a object field setting with the primary key could not be found
	 */
	@Override
	public ObjectFieldSetting[] findByObjectFieldId_PrevAndNext(
			long objectFieldSettingId, long objectFieldId,
			OrderByComparator<ObjectFieldSetting> orderByComparator)
		throws NoSuchObjectFieldSettingException {

		ObjectFieldSetting objectFieldSetting = findByPrimaryKey(
			objectFieldSettingId);

		Session session = null;

		try {
			session = openSession();

			ObjectFieldSetting[] array = new ObjectFieldSettingImpl[3];

			array[0] = getByObjectFieldId_PrevAndNext(
				session, objectFieldSetting, objectFieldId, orderByComparator,
				true);

			array[1] = objectFieldSetting;

			array[2] = getByObjectFieldId_PrevAndNext(
				session, objectFieldSetting, objectFieldId, orderByComparator,
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

	protected ObjectFieldSetting getByObjectFieldId_PrevAndNext(
		Session session, ObjectFieldSetting objectFieldSetting,
		long objectFieldId,
		OrderByComparator<ObjectFieldSetting> orderByComparator,
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

		sb.append(_SQL_SELECT_OBJECTFIELDSETTING_WHERE);

		sb.append(_FINDER_COLUMN_OBJECTFIELDID_OBJECTFIELDID_2);

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
			sb.append(ObjectFieldSettingModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(objectFieldId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						objectFieldSetting)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<ObjectFieldSetting> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the object field settings where objectFieldId = &#63; from the database.
	 *
	 * @param objectFieldId the object field ID
	 */
	@Override
	public void removeByObjectFieldId(long objectFieldId) {
		for (ObjectFieldSetting objectFieldSetting :
				findByObjectFieldId(
					objectFieldId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(objectFieldSetting);
		}
	}

	/**
	 * Returns the number of object field settings where objectFieldId = &#63;.
	 *
	 * @param objectFieldId the object field ID
	 * @return the number of matching object field settings
	 */
	@Override
	public int countByObjectFieldId(long objectFieldId) {
		FinderPath finderPath = _finderPathCountByObjectFieldId;

		Object[] finderArgs = new Object[] {objectFieldId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_OBJECTFIELDSETTING_WHERE);

			sb.append(_FINDER_COLUMN_OBJECTFIELDID_OBJECTFIELDID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(objectFieldId);

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

	private static final String _FINDER_COLUMN_OBJECTFIELDID_OBJECTFIELDID_2 =
		"objectFieldSetting.objectFieldId = ?";

	private FinderPath _finderPathFetchByOFI_N;
	private FinderPath _finderPathCountByOFI_N;

	/**
	 * Returns the object field setting where objectFieldId = &#63; and name = &#63; or throws a <code>NoSuchObjectFieldSettingException</code> if it could not be found.
	 *
	 * @param objectFieldId the object field ID
	 * @param name the name
	 * @return the matching object field setting
	 * @throws NoSuchObjectFieldSettingException if a matching object field setting could not be found
	 */
	@Override
	public ObjectFieldSetting findByOFI_N(long objectFieldId, String name)
		throws NoSuchObjectFieldSettingException {

		ObjectFieldSetting objectFieldSetting = fetchByOFI_N(
			objectFieldId, name);

		if (objectFieldSetting == null) {
			StringBundler sb = new StringBundler(6);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("objectFieldId=");
			sb.append(objectFieldId);

			sb.append(", name=");
			sb.append(name);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchObjectFieldSettingException(sb.toString());
		}

		return objectFieldSetting;
	}

	/**
	 * Returns the object field setting where objectFieldId = &#63; and name = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param objectFieldId the object field ID
	 * @param name the name
	 * @return the matching object field setting, or <code>null</code> if a matching object field setting could not be found
	 */
	@Override
	public ObjectFieldSetting fetchByOFI_N(long objectFieldId, String name) {
		return fetchByOFI_N(objectFieldId, name, true);
	}

	/**
	 * Returns the object field setting where objectFieldId = &#63; and name = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param objectFieldId the object field ID
	 * @param name the name
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching object field setting, or <code>null</code> if a matching object field setting could not be found
	 */
	@Override
	public ObjectFieldSetting fetchByOFI_N(
		long objectFieldId, String name, boolean useFinderCache) {

		name = Objects.toString(name, "");

		Object[] finderArgs = null;

		if (useFinderCache) {
			finderArgs = new Object[] {objectFieldId, name};
		}

		Object result = null;

		if (useFinderCache) {
			result = finderCache.getResult(
				_finderPathFetchByOFI_N, finderArgs, this);
		}

		if (result instanceof ObjectFieldSetting) {
			ObjectFieldSetting objectFieldSetting = (ObjectFieldSetting)result;

			if ((objectFieldId != objectFieldSetting.getObjectFieldId()) ||
				!Objects.equals(name, objectFieldSetting.getName())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_SELECT_OBJECTFIELDSETTING_WHERE);

			sb.append(_FINDER_COLUMN_OFI_N_OBJECTFIELDID_2);

			boolean bindName = false;

			if (name.isEmpty()) {
				sb.append(_FINDER_COLUMN_OFI_N_NAME_3);
			}
			else {
				bindName = true;

				sb.append(_FINDER_COLUMN_OFI_N_NAME_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(objectFieldId);

				if (bindName) {
					queryPos.add(name);
				}

				List<ObjectFieldSetting> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache) {
						finderCache.putResult(
							_finderPathFetchByOFI_N, finderArgs, list);
					}
				}
				else {
					ObjectFieldSetting objectFieldSetting = list.get(0);

					result = objectFieldSetting;

					cacheResult(objectFieldSetting);
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
			return (ObjectFieldSetting)result;
		}
	}

	/**
	 * Removes the object field setting where objectFieldId = &#63; and name = &#63; from the database.
	 *
	 * @param objectFieldId the object field ID
	 * @param name the name
	 * @return the object field setting that was removed
	 */
	@Override
	public ObjectFieldSetting removeByOFI_N(long objectFieldId, String name)
		throws NoSuchObjectFieldSettingException {

		ObjectFieldSetting objectFieldSetting = findByOFI_N(
			objectFieldId, name);

		return remove(objectFieldSetting);
	}

	/**
	 * Returns the number of object field settings where objectFieldId = &#63; and name = &#63;.
	 *
	 * @param objectFieldId the object field ID
	 * @param name the name
	 * @return the number of matching object field settings
	 */
	@Override
	public int countByOFI_N(long objectFieldId, String name) {
		name = Objects.toString(name, "");

		FinderPath finderPath = _finderPathCountByOFI_N;

		Object[] finderArgs = new Object[] {objectFieldId, name};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_OBJECTFIELDSETTING_WHERE);

			sb.append(_FINDER_COLUMN_OFI_N_OBJECTFIELDID_2);

			boolean bindName = false;

			if (name.isEmpty()) {
				sb.append(_FINDER_COLUMN_OFI_N_NAME_3);
			}
			else {
				bindName = true;

				sb.append(_FINDER_COLUMN_OFI_N_NAME_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(objectFieldId);

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

	private static final String _FINDER_COLUMN_OFI_N_OBJECTFIELDID_2 =
		"objectFieldSetting.objectFieldId = ? AND ";

	private static final String _FINDER_COLUMN_OFI_N_NAME_2 =
		"objectFieldSetting.name = ?";

	private static final String _FINDER_COLUMN_OFI_N_NAME_3 =
		"(objectFieldSetting.name IS NULL OR objectFieldSetting.name = '')";

	public ObjectFieldSettingPersistenceImpl() {
		Map<String, String> dbColumnNames = new HashMap<String, String>();

		dbColumnNames.put("uuid", "uuid_");

		setDBColumnNames(dbColumnNames);

		setModelClass(ObjectFieldSetting.class);

		setModelImplClass(ObjectFieldSettingImpl.class);
		setModelPKClass(long.class);

		setTable(ObjectFieldSettingTable.INSTANCE);
	}

	/**
	 * Caches the object field setting in the entity cache if it is enabled.
	 *
	 * @param objectFieldSetting the object field setting
	 */
	@Override
	public void cacheResult(ObjectFieldSetting objectFieldSetting) {
		entityCache.putResult(
			ObjectFieldSettingImpl.class, objectFieldSetting.getPrimaryKey(),
			objectFieldSetting);

		finderCache.putResult(
			_finderPathFetchByOFI_N,
			new Object[] {
				objectFieldSetting.getObjectFieldId(),
				objectFieldSetting.getName()
			},
			objectFieldSetting);
	}

	private int _valueObjectFinderCacheListThreshold;

	/**
	 * Caches the object field settings in the entity cache if it is enabled.
	 *
	 * @param objectFieldSettings the object field settings
	 */
	@Override
	public void cacheResult(List<ObjectFieldSetting> objectFieldSettings) {
		if ((_valueObjectFinderCacheListThreshold == 0) ||
			((_valueObjectFinderCacheListThreshold > 0) &&
			 (objectFieldSettings.size() >
				 _valueObjectFinderCacheListThreshold))) {

			return;
		}

		for (ObjectFieldSetting objectFieldSetting : objectFieldSettings) {
			if (entityCache.getResult(
					ObjectFieldSettingImpl.class,
					objectFieldSetting.getPrimaryKey()) == null) {

				cacheResult(objectFieldSetting);
			}
		}
	}

	/**
	 * Clears the cache for all object field settings.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(ObjectFieldSettingImpl.class);

		finderCache.clearCache(ObjectFieldSettingImpl.class);
	}

	/**
	 * Clears the cache for the object field setting.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(ObjectFieldSetting objectFieldSetting) {
		entityCache.removeResult(
			ObjectFieldSettingImpl.class, objectFieldSetting);
	}

	@Override
	public void clearCache(List<ObjectFieldSetting> objectFieldSettings) {
		for (ObjectFieldSetting objectFieldSetting : objectFieldSettings) {
			entityCache.removeResult(
				ObjectFieldSettingImpl.class, objectFieldSetting);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(ObjectFieldSettingImpl.class);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(ObjectFieldSettingImpl.class, primaryKey);
		}
	}

	protected void cacheUniqueFindersCache(
		ObjectFieldSettingModelImpl objectFieldSettingModelImpl) {

		Object[] args = new Object[] {
			objectFieldSettingModelImpl.getObjectFieldId(),
			objectFieldSettingModelImpl.getName()
		};

		finderCache.putResult(_finderPathCountByOFI_N, args, Long.valueOf(1));
		finderCache.putResult(
			_finderPathFetchByOFI_N, args, objectFieldSettingModelImpl);
	}

	/**
	 * Creates a new object field setting with the primary key. Does not add the object field setting to the database.
	 *
	 * @param objectFieldSettingId the primary key for the new object field setting
	 * @return the new object field setting
	 */
	@Override
	public ObjectFieldSetting create(long objectFieldSettingId) {
		ObjectFieldSetting objectFieldSetting = new ObjectFieldSettingImpl();

		objectFieldSetting.setNew(true);
		objectFieldSetting.setPrimaryKey(objectFieldSettingId);

		String uuid = _portalUUID.generate();

		objectFieldSetting.setUuid(uuid);

		objectFieldSetting.setCompanyId(CompanyThreadLocal.getCompanyId());

		return objectFieldSetting;
	}

	/**
	 * Removes the object field setting with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param objectFieldSettingId the primary key of the object field setting
	 * @return the object field setting that was removed
	 * @throws NoSuchObjectFieldSettingException if a object field setting with the primary key could not be found
	 */
	@Override
	public ObjectFieldSetting remove(long objectFieldSettingId)
		throws NoSuchObjectFieldSettingException {

		return remove((Serializable)objectFieldSettingId);
	}

	/**
	 * Removes the object field setting with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the object field setting
	 * @return the object field setting that was removed
	 * @throws NoSuchObjectFieldSettingException if a object field setting with the primary key could not be found
	 */
	@Override
	public ObjectFieldSetting remove(Serializable primaryKey)
		throws NoSuchObjectFieldSettingException {

		Session session = null;

		try {
			session = openSession();

			ObjectFieldSetting objectFieldSetting =
				(ObjectFieldSetting)session.get(
					ObjectFieldSettingImpl.class, primaryKey);

			if (objectFieldSetting == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchObjectFieldSettingException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(objectFieldSetting);
		}
		catch (NoSuchObjectFieldSettingException noSuchEntityException) {
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
	protected ObjectFieldSetting removeImpl(
		ObjectFieldSetting objectFieldSetting) {

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(objectFieldSetting)) {
				objectFieldSetting = (ObjectFieldSetting)session.get(
					ObjectFieldSettingImpl.class,
					objectFieldSetting.getPrimaryKeyObj());
			}

			if (objectFieldSetting != null) {
				session.delete(objectFieldSetting);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (objectFieldSetting != null) {
			clearCache(objectFieldSetting);
		}

		return objectFieldSetting;
	}

	@Override
	public ObjectFieldSetting updateImpl(
		ObjectFieldSetting objectFieldSetting) {

		boolean isNew = objectFieldSetting.isNew();

		if (!(objectFieldSetting instanceof ObjectFieldSettingModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(objectFieldSetting.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					objectFieldSetting);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in objectFieldSetting proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom ObjectFieldSetting implementation " +
					objectFieldSetting.getClass());
		}

		ObjectFieldSettingModelImpl objectFieldSettingModelImpl =
			(ObjectFieldSettingModelImpl)objectFieldSetting;

		if (Validator.isNull(objectFieldSetting.getUuid())) {
			String uuid = _portalUUID.generate();

			objectFieldSetting.setUuid(uuid);
		}

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Date date = new Date();

		if (isNew && (objectFieldSetting.getCreateDate() == null)) {
			if (serviceContext == null) {
				objectFieldSetting.setCreateDate(date);
			}
			else {
				objectFieldSetting.setCreateDate(
					serviceContext.getCreateDate(date));
			}
		}

		if (!objectFieldSettingModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				objectFieldSetting.setModifiedDate(date);
			}
			else {
				objectFieldSetting.setModifiedDate(
					serviceContext.getModifiedDate(date));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (isNew) {
				session.save(objectFieldSetting);
			}
			else {
				objectFieldSetting = (ObjectFieldSetting)session.merge(
					objectFieldSetting);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		entityCache.putResult(
			ObjectFieldSettingImpl.class, objectFieldSettingModelImpl, false,
			true);

		cacheUniqueFindersCache(objectFieldSettingModelImpl);

		if (isNew) {
			objectFieldSetting.setNew(false);
		}

		objectFieldSetting.resetOriginalValues();

		return objectFieldSetting;
	}

	/**
	 * Returns the object field setting with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the object field setting
	 * @return the object field setting
	 * @throws NoSuchObjectFieldSettingException if a object field setting with the primary key could not be found
	 */
	@Override
	public ObjectFieldSetting findByPrimaryKey(Serializable primaryKey)
		throws NoSuchObjectFieldSettingException {

		ObjectFieldSetting objectFieldSetting = fetchByPrimaryKey(primaryKey);

		if (objectFieldSetting == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchObjectFieldSettingException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return objectFieldSetting;
	}

	/**
	 * Returns the object field setting with the primary key or throws a <code>NoSuchObjectFieldSettingException</code> if it could not be found.
	 *
	 * @param objectFieldSettingId the primary key of the object field setting
	 * @return the object field setting
	 * @throws NoSuchObjectFieldSettingException if a object field setting with the primary key could not be found
	 */
	@Override
	public ObjectFieldSetting findByPrimaryKey(long objectFieldSettingId)
		throws NoSuchObjectFieldSettingException {

		return findByPrimaryKey((Serializable)objectFieldSettingId);
	}

	/**
	 * Returns the object field setting with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param objectFieldSettingId the primary key of the object field setting
	 * @return the object field setting, or <code>null</code> if a object field setting with the primary key could not be found
	 */
	@Override
	public ObjectFieldSetting fetchByPrimaryKey(long objectFieldSettingId) {
		return fetchByPrimaryKey((Serializable)objectFieldSettingId);
	}

	/**
	 * Returns all the object field settings.
	 *
	 * @return the object field settings
	 */
	@Override
	public List<ObjectFieldSetting> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the object field settings.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectFieldSettingModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of object field settings
	 * @param end the upper bound of the range of object field settings (not inclusive)
	 * @return the range of object field settings
	 */
	@Override
	public List<ObjectFieldSetting> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the object field settings.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectFieldSettingModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of object field settings
	 * @param end the upper bound of the range of object field settings (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of object field settings
	 */
	@Override
	public List<ObjectFieldSetting> findAll(
		int start, int end,
		OrderByComparator<ObjectFieldSetting> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the object field settings.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectFieldSettingModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of object field settings
	 * @param end the upper bound of the range of object field settings (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of object field settings
	 */
	@Override
	public List<ObjectFieldSetting> findAll(
		int start, int end,
		OrderByComparator<ObjectFieldSetting> orderByComparator,
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

		List<ObjectFieldSetting> list = null;

		if (useFinderCache) {
			list = (List<ObjectFieldSetting>)finderCache.getResult(
				finderPath, finderArgs, this);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_OBJECTFIELDSETTING);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_OBJECTFIELDSETTING;

				sql = sql.concat(ObjectFieldSettingModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<ObjectFieldSetting>)QueryUtil.list(
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
	 * Removes all the object field settings from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (ObjectFieldSetting objectFieldSetting : findAll()) {
			remove(objectFieldSetting);
		}
	}

	/**
	 * Returns the number of object field settings.
	 *
	 * @return the number of object field settings
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(
			_finderPathCountAll, FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(
					_SQL_COUNT_OBJECTFIELDSETTING);

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
		return "objectFieldSettingId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_OBJECTFIELDSETTING;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return ObjectFieldSettingModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the object field setting persistence.
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

		_finderPathWithPaginationFindByObjectFieldId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByObjectFieldId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"objectFieldId"}, true);

		_finderPathWithoutPaginationFindByObjectFieldId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByObjectFieldId",
			new String[] {Long.class.getName()}, new String[] {"objectFieldId"},
			true);

		_finderPathCountByObjectFieldId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByObjectFieldId",
			new String[] {Long.class.getName()}, new String[] {"objectFieldId"},
			false);

		_finderPathFetchByOFI_N = new FinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByOFI_N",
			new String[] {Long.class.getName(), String.class.getName()},
			new String[] {"objectFieldId", "name"}, true);

		_finderPathCountByOFI_N = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByOFI_N",
			new String[] {Long.class.getName(), String.class.getName()},
			new String[] {"objectFieldId", "name"}, false);

		ObjectFieldSettingUtil.setPersistence(this);
	}

	@Deactivate
	public void deactivate() {
		ObjectFieldSettingUtil.setPersistence(null);

		entityCache.removeCache(ObjectFieldSettingImpl.class.getName());
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

	private static final String _SQL_SELECT_OBJECTFIELDSETTING =
		"SELECT objectFieldSetting FROM ObjectFieldSetting objectFieldSetting";

	private static final String _SQL_SELECT_OBJECTFIELDSETTING_WHERE =
		"SELECT objectFieldSetting FROM ObjectFieldSetting objectFieldSetting WHERE ";

	private static final String _SQL_COUNT_OBJECTFIELDSETTING =
		"SELECT COUNT(objectFieldSetting) FROM ObjectFieldSetting objectFieldSetting";

	private static final String _SQL_COUNT_OBJECTFIELDSETTING_WHERE =
		"SELECT COUNT(objectFieldSetting) FROM ObjectFieldSetting objectFieldSetting WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS = "objectFieldSetting.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No ObjectFieldSetting exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No ObjectFieldSetting exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		ObjectFieldSettingPersistenceImpl.class);

	private static final Set<String> _badColumnNames = SetUtil.fromArray(
		new String[] {"uuid"});

	@Override
	protected FinderCache getFinderCache() {
		return finderCache;
	}

	@Reference
	private PortalUUID _portalUUID;

}