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

import com.liferay.object.exception.NoSuchObjectLayoutBoxException;
import com.liferay.object.model.ObjectLayoutBox;
import com.liferay.object.model.ObjectLayoutBoxTable;
import com.liferay.object.model.impl.ObjectLayoutBoxImpl;
import com.liferay.object.model.impl.ObjectLayoutBoxModelImpl;
import com.liferay.object.service.persistence.ObjectLayoutBoxPersistence;
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
 * The persistence implementation for the object layout box service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Marco Leo
 * @generated
 */
@Component(service = {ObjectLayoutBoxPersistence.class, BasePersistence.class})
public class ObjectLayoutBoxPersistenceImpl
	extends BasePersistenceImpl<ObjectLayoutBox>
	implements ObjectLayoutBoxPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>ObjectLayoutBoxUtil</code> to access the object layout box persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		ObjectLayoutBoxImpl.class.getName();

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
	 * Returns all the object layout boxes where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching object layout boxes
	 */
	@Override
	public List<ObjectLayoutBox> findByUuid(String uuid) {
		return findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the object layout boxes where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectLayoutBoxModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of object layout boxes
	 * @param end the upper bound of the range of object layout boxes (not inclusive)
	 * @return the range of matching object layout boxes
	 */
	@Override
	public List<ObjectLayoutBox> findByUuid(String uuid, int start, int end) {
		return findByUuid(uuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the object layout boxes where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectLayoutBoxModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of object layout boxes
	 * @param end the upper bound of the range of object layout boxes (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching object layout boxes
	 */
	@Override
	public List<ObjectLayoutBox> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<ObjectLayoutBox> orderByComparator) {

		return findByUuid(uuid, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the object layout boxes where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectLayoutBoxModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of object layout boxes
	 * @param end the upper bound of the range of object layout boxes (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching object layout boxes
	 */
	@Override
	public List<ObjectLayoutBox> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<ObjectLayoutBox> orderByComparator,
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

		List<ObjectLayoutBox> list = null;

		if (useFinderCache) {
			list = (List<ObjectLayoutBox>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (ObjectLayoutBox objectLayoutBox : list) {
					if (!uuid.equals(objectLayoutBox.getUuid())) {
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

			sb.append(_SQL_SELECT_OBJECTLAYOUTBOX_WHERE);

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
				sb.append(ObjectLayoutBoxModelImpl.ORDER_BY_JPQL);
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

				list = (List<ObjectLayoutBox>)QueryUtil.list(
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
	 * Returns the first object layout box in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object layout box
	 * @throws NoSuchObjectLayoutBoxException if a matching object layout box could not be found
	 */
	@Override
	public ObjectLayoutBox findByUuid_First(
			String uuid, OrderByComparator<ObjectLayoutBox> orderByComparator)
		throws NoSuchObjectLayoutBoxException {

		ObjectLayoutBox objectLayoutBox = fetchByUuid_First(
			uuid, orderByComparator);

		if (objectLayoutBox != null) {
			return objectLayoutBox;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append("}");

		throw new NoSuchObjectLayoutBoxException(sb.toString());
	}

	/**
	 * Returns the first object layout box in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object layout box, or <code>null</code> if a matching object layout box could not be found
	 */
	@Override
	public ObjectLayoutBox fetchByUuid_First(
		String uuid, OrderByComparator<ObjectLayoutBox> orderByComparator) {

		List<ObjectLayoutBox> list = findByUuid(uuid, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last object layout box in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object layout box
	 * @throws NoSuchObjectLayoutBoxException if a matching object layout box could not be found
	 */
	@Override
	public ObjectLayoutBox findByUuid_Last(
			String uuid, OrderByComparator<ObjectLayoutBox> orderByComparator)
		throws NoSuchObjectLayoutBoxException {

		ObjectLayoutBox objectLayoutBox = fetchByUuid_Last(
			uuid, orderByComparator);

		if (objectLayoutBox != null) {
			return objectLayoutBox;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append("}");

		throw new NoSuchObjectLayoutBoxException(sb.toString());
	}

	/**
	 * Returns the last object layout box in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object layout box, or <code>null</code> if a matching object layout box could not be found
	 */
	@Override
	public ObjectLayoutBox fetchByUuid_Last(
		String uuid, OrderByComparator<ObjectLayoutBox> orderByComparator) {

		int count = countByUuid(uuid);

		if (count == 0) {
			return null;
		}

		List<ObjectLayoutBox> list = findByUuid(
			uuid, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the object layout boxes before and after the current object layout box in the ordered set where uuid = &#63;.
	 *
	 * @param objectLayoutBoxId the primary key of the current object layout box
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next object layout box
	 * @throws NoSuchObjectLayoutBoxException if a object layout box with the primary key could not be found
	 */
	@Override
	public ObjectLayoutBox[] findByUuid_PrevAndNext(
			long objectLayoutBoxId, String uuid,
			OrderByComparator<ObjectLayoutBox> orderByComparator)
		throws NoSuchObjectLayoutBoxException {

		uuid = Objects.toString(uuid, "");

		ObjectLayoutBox objectLayoutBox = findByPrimaryKey(objectLayoutBoxId);

		Session session = null;

		try {
			session = openSession();

			ObjectLayoutBox[] array = new ObjectLayoutBoxImpl[3];

			array[0] = getByUuid_PrevAndNext(
				session, objectLayoutBox, uuid, orderByComparator, true);

			array[1] = objectLayoutBox;

			array[2] = getByUuid_PrevAndNext(
				session, objectLayoutBox, uuid, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected ObjectLayoutBox getByUuid_PrevAndNext(
		Session session, ObjectLayoutBox objectLayoutBox, String uuid,
		OrderByComparator<ObjectLayoutBox> orderByComparator,
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

		sb.append(_SQL_SELECT_OBJECTLAYOUTBOX_WHERE);

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
			sb.append(ObjectLayoutBoxModelImpl.ORDER_BY_JPQL);
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
						objectLayoutBox)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<ObjectLayoutBox> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the object layout boxes where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	@Override
	public void removeByUuid(String uuid) {
		for (ObjectLayoutBox objectLayoutBox :
				findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(objectLayoutBox);
		}
	}

	/**
	 * Returns the number of object layout boxes where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching object layout boxes
	 */
	@Override
	public int countByUuid(String uuid) {
		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = _finderPathCountByUuid;

		Object[] finderArgs = new Object[] {uuid};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_OBJECTLAYOUTBOX_WHERE);

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
		"objectLayoutBox.uuid = ?";

	private static final String _FINDER_COLUMN_UUID_UUID_3 =
		"(objectLayoutBox.uuid IS NULL OR objectLayoutBox.uuid = '')";

	private FinderPath _finderPathWithPaginationFindByUuid_C;
	private FinderPath _finderPathWithoutPaginationFindByUuid_C;
	private FinderPath _finderPathCountByUuid_C;

	/**
	 * Returns all the object layout boxes where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching object layout boxes
	 */
	@Override
	public List<ObjectLayoutBox> findByUuid_C(String uuid, long companyId) {
		return findByUuid_C(
			uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the object layout boxes where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectLayoutBoxModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of object layout boxes
	 * @param end the upper bound of the range of object layout boxes (not inclusive)
	 * @return the range of matching object layout boxes
	 */
	@Override
	public List<ObjectLayoutBox> findByUuid_C(
		String uuid, long companyId, int start, int end) {

		return findByUuid_C(uuid, companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the object layout boxes where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectLayoutBoxModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of object layout boxes
	 * @param end the upper bound of the range of object layout boxes (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching object layout boxes
	 */
	@Override
	public List<ObjectLayoutBox> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<ObjectLayoutBox> orderByComparator) {

		return findByUuid_C(
			uuid, companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the object layout boxes where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectLayoutBoxModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of object layout boxes
	 * @param end the upper bound of the range of object layout boxes (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching object layout boxes
	 */
	@Override
	public List<ObjectLayoutBox> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<ObjectLayoutBox> orderByComparator,
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

		List<ObjectLayoutBox> list = null;

		if (useFinderCache) {
			list = (List<ObjectLayoutBox>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (ObjectLayoutBox objectLayoutBox : list) {
					if (!uuid.equals(objectLayoutBox.getUuid()) ||
						(companyId != objectLayoutBox.getCompanyId())) {

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

			sb.append(_SQL_SELECT_OBJECTLAYOUTBOX_WHERE);

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
				sb.append(ObjectLayoutBoxModelImpl.ORDER_BY_JPQL);
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

				list = (List<ObjectLayoutBox>)QueryUtil.list(
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
	 * Returns the first object layout box in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object layout box
	 * @throws NoSuchObjectLayoutBoxException if a matching object layout box could not be found
	 */
	@Override
	public ObjectLayoutBox findByUuid_C_First(
			String uuid, long companyId,
			OrderByComparator<ObjectLayoutBox> orderByComparator)
		throws NoSuchObjectLayoutBoxException {

		ObjectLayoutBox objectLayoutBox = fetchByUuid_C_First(
			uuid, companyId, orderByComparator);

		if (objectLayoutBox != null) {
			return objectLayoutBox;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append(", companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchObjectLayoutBoxException(sb.toString());
	}

	/**
	 * Returns the first object layout box in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object layout box, or <code>null</code> if a matching object layout box could not be found
	 */
	@Override
	public ObjectLayoutBox fetchByUuid_C_First(
		String uuid, long companyId,
		OrderByComparator<ObjectLayoutBox> orderByComparator) {

		List<ObjectLayoutBox> list = findByUuid_C(
			uuid, companyId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last object layout box in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object layout box
	 * @throws NoSuchObjectLayoutBoxException if a matching object layout box could not be found
	 */
	@Override
	public ObjectLayoutBox findByUuid_C_Last(
			String uuid, long companyId,
			OrderByComparator<ObjectLayoutBox> orderByComparator)
		throws NoSuchObjectLayoutBoxException {

		ObjectLayoutBox objectLayoutBox = fetchByUuid_C_Last(
			uuid, companyId, orderByComparator);

		if (objectLayoutBox != null) {
			return objectLayoutBox;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append(", companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchObjectLayoutBoxException(sb.toString());
	}

	/**
	 * Returns the last object layout box in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object layout box, or <code>null</code> if a matching object layout box could not be found
	 */
	@Override
	public ObjectLayoutBox fetchByUuid_C_Last(
		String uuid, long companyId,
		OrderByComparator<ObjectLayoutBox> orderByComparator) {

		int count = countByUuid_C(uuid, companyId);

		if (count == 0) {
			return null;
		}

		List<ObjectLayoutBox> list = findByUuid_C(
			uuid, companyId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the object layout boxes before and after the current object layout box in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param objectLayoutBoxId the primary key of the current object layout box
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next object layout box
	 * @throws NoSuchObjectLayoutBoxException if a object layout box with the primary key could not be found
	 */
	@Override
	public ObjectLayoutBox[] findByUuid_C_PrevAndNext(
			long objectLayoutBoxId, String uuid, long companyId,
			OrderByComparator<ObjectLayoutBox> orderByComparator)
		throws NoSuchObjectLayoutBoxException {

		uuid = Objects.toString(uuid, "");

		ObjectLayoutBox objectLayoutBox = findByPrimaryKey(objectLayoutBoxId);

		Session session = null;

		try {
			session = openSession();

			ObjectLayoutBox[] array = new ObjectLayoutBoxImpl[3];

			array[0] = getByUuid_C_PrevAndNext(
				session, objectLayoutBox, uuid, companyId, orderByComparator,
				true);

			array[1] = objectLayoutBox;

			array[2] = getByUuid_C_PrevAndNext(
				session, objectLayoutBox, uuid, companyId, orderByComparator,
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

	protected ObjectLayoutBox getByUuid_C_PrevAndNext(
		Session session, ObjectLayoutBox objectLayoutBox, String uuid,
		long companyId, OrderByComparator<ObjectLayoutBox> orderByComparator,
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

		sb.append(_SQL_SELECT_OBJECTLAYOUTBOX_WHERE);

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
			sb.append(ObjectLayoutBoxModelImpl.ORDER_BY_JPQL);
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
						objectLayoutBox)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<ObjectLayoutBox> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the object layout boxes where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	@Override
	public void removeByUuid_C(String uuid, long companyId) {
		for (ObjectLayoutBox objectLayoutBox :
				findByUuid_C(
					uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(objectLayoutBox);
		}
	}

	/**
	 * Returns the number of object layout boxes where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching object layout boxes
	 */
	@Override
	public int countByUuid_C(String uuid, long companyId) {
		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = _finderPathCountByUuid_C;

		Object[] finderArgs = new Object[] {uuid, companyId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_OBJECTLAYOUTBOX_WHERE);

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
		"objectLayoutBox.uuid = ? AND ";

	private static final String _FINDER_COLUMN_UUID_C_UUID_3 =
		"(objectLayoutBox.uuid IS NULL OR objectLayoutBox.uuid = '') AND ";

	private static final String _FINDER_COLUMN_UUID_C_COMPANYID_2 =
		"objectLayoutBox.companyId = ?";

	private FinderPath _finderPathWithPaginationFindByObjectLayoutTabId;
	private FinderPath _finderPathWithoutPaginationFindByObjectLayoutTabId;
	private FinderPath _finderPathCountByObjectLayoutTabId;

	/**
	 * Returns all the object layout boxes where objectLayoutTabId = &#63;.
	 *
	 * @param objectLayoutTabId the object layout tab ID
	 * @return the matching object layout boxes
	 */
	@Override
	public List<ObjectLayoutBox> findByObjectLayoutTabId(
		long objectLayoutTabId) {

		return findByObjectLayoutTabId(
			objectLayoutTabId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the object layout boxes where objectLayoutTabId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectLayoutBoxModelImpl</code>.
	 * </p>
	 *
	 * @param objectLayoutTabId the object layout tab ID
	 * @param start the lower bound of the range of object layout boxes
	 * @param end the upper bound of the range of object layout boxes (not inclusive)
	 * @return the range of matching object layout boxes
	 */
	@Override
	public List<ObjectLayoutBox> findByObjectLayoutTabId(
		long objectLayoutTabId, int start, int end) {

		return findByObjectLayoutTabId(objectLayoutTabId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the object layout boxes where objectLayoutTabId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectLayoutBoxModelImpl</code>.
	 * </p>
	 *
	 * @param objectLayoutTabId the object layout tab ID
	 * @param start the lower bound of the range of object layout boxes
	 * @param end the upper bound of the range of object layout boxes (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching object layout boxes
	 */
	@Override
	public List<ObjectLayoutBox> findByObjectLayoutTabId(
		long objectLayoutTabId, int start, int end,
		OrderByComparator<ObjectLayoutBox> orderByComparator) {

		return findByObjectLayoutTabId(
			objectLayoutTabId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the object layout boxes where objectLayoutTabId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectLayoutBoxModelImpl</code>.
	 * </p>
	 *
	 * @param objectLayoutTabId the object layout tab ID
	 * @param start the lower bound of the range of object layout boxes
	 * @param end the upper bound of the range of object layout boxes (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching object layout boxes
	 */
	@Override
	public List<ObjectLayoutBox> findByObjectLayoutTabId(
		long objectLayoutTabId, int start, int end,
		OrderByComparator<ObjectLayoutBox> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath =
					_finderPathWithoutPaginationFindByObjectLayoutTabId;
				finderArgs = new Object[] {objectLayoutTabId};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByObjectLayoutTabId;
			finderArgs = new Object[] {
				objectLayoutTabId, start, end, orderByComparator
			};
		}

		List<ObjectLayoutBox> list = null;

		if (useFinderCache) {
			list = (List<ObjectLayoutBox>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (ObjectLayoutBox objectLayoutBox : list) {
					if (objectLayoutTabId !=
							objectLayoutBox.getObjectLayoutTabId()) {

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

			sb.append(_SQL_SELECT_OBJECTLAYOUTBOX_WHERE);

			sb.append(_FINDER_COLUMN_OBJECTLAYOUTTABID_OBJECTLAYOUTTABID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(ObjectLayoutBoxModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(objectLayoutTabId);

				list = (List<ObjectLayoutBox>)QueryUtil.list(
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
	 * Returns the first object layout box in the ordered set where objectLayoutTabId = &#63;.
	 *
	 * @param objectLayoutTabId the object layout tab ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object layout box
	 * @throws NoSuchObjectLayoutBoxException if a matching object layout box could not be found
	 */
	@Override
	public ObjectLayoutBox findByObjectLayoutTabId_First(
			long objectLayoutTabId,
			OrderByComparator<ObjectLayoutBox> orderByComparator)
		throws NoSuchObjectLayoutBoxException {

		ObjectLayoutBox objectLayoutBox = fetchByObjectLayoutTabId_First(
			objectLayoutTabId, orderByComparator);

		if (objectLayoutBox != null) {
			return objectLayoutBox;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("objectLayoutTabId=");
		sb.append(objectLayoutTabId);

		sb.append("}");

		throw new NoSuchObjectLayoutBoxException(sb.toString());
	}

	/**
	 * Returns the first object layout box in the ordered set where objectLayoutTabId = &#63;.
	 *
	 * @param objectLayoutTabId the object layout tab ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching object layout box, or <code>null</code> if a matching object layout box could not be found
	 */
	@Override
	public ObjectLayoutBox fetchByObjectLayoutTabId_First(
		long objectLayoutTabId,
		OrderByComparator<ObjectLayoutBox> orderByComparator) {

		List<ObjectLayoutBox> list = findByObjectLayoutTabId(
			objectLayoutTabId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last object layout box in the ordered set where objectLayoutTabId = &#63;.
	 *
	 * @param objectLayoutTabId the object layout tab ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object layout box
	 * @throws NoSuchObjectLayoutBoxException if a matching object layout box could not be found
	 */
	@Override
	public ObjectLayoutBox findByObjectLayoutTabId_Last(
			long objectLayoutTabId,
			OrderByComparator<ObjectLayoutBox> orderByComparator)
		throws NoSuchObjectLayoutBoxException {

		ObjectLayoutBox objectLayoutBox = fetchByObjectLayoutTabId_Last(
			objectLayoutTabId, orderByComparator);

		if (objectLayoutBox != null) {
			return objectLayoutBox;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("objectLayoutTabId=");
		sb.append(objectLayoutTabId);

		sb.append("}");

		throw new NoSuchObjectLayoutBoxException(sb.toString());
	}

	/**
	 * Returns the last object layout box in the ordered set where objectLayoutTabId = &#63;.
	 *
	 * @param objectLayoutTabId the object layout tab ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching object layout box, or <code>null</code> if a matching object layout box could not be found
	 */
	@Override
	public ObjectLayoutBox fetchByObjectLayoutTabId_Last(
		long objectLayoutTabId,
		OrderByComparator<ObjectLayoutBox> orderByComparator) {

		int count = countByObjectLayoutTabId(objectLayoutTabId);

		if (count == 0) {
			return null;
		}

		List<ObjectLayoutBox> list = findByObjectLayoutTabId(
			objectLayoutTabId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the object layout boxes before and after the current object layout box in the ordered set where objectLayoutTabId = &#63;.
	 *
	 * @param objectLayoutBoxId the primary key of the current object layout box
	 * @param objectLayoutTabId the object layout tab ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next object layout box
	 * @throws NoSuchObjectLayoutBoxException if a object layout box with the primary key could not be found
	 */
	@Override
	public ObjectLayoutBox[] findByObjectLayoutTabId_PrevAndNext(
			long objectLayoutBoxId, long objectLayoutTabId,
			OrderByComparator<ObjectLayoutBox> orderByComparator)
		throws NoSuchObjectLayoutBoxException {

		ObjectLayoutBox objectLayoutBox = findByPrimaryKey(objectLayoutBoxId);

		Session session = null;

		try {
			session = openSession();

			ObjectLayoutBox[] array = new ObjectLayoutBoxImpl[3];

			array[0] = getByObjectLayoutTabId_PrevAndNext(
				session, objectLayoutBox, objectLayoutTabId, orderByComparator,
				true);

			array[1] = objectLayoutBox;

			array[2] = getByObjectLayoutTabId_PrevAndNext(
				session, objectLayoutBox, objectLayoutTabId, orderByComparator,
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

	protected ObjectLayoutBox getByObjectLayoutTabId_PrevAndNext(
		Session session, ObjectLayoutBox objectLayoutBox,
		long objectLayoutTabId,
		OrderByComparator<ObjectLayoutBox> orderByComparator,
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

		sb.append(_SQL_SELECT_OBJECTLAYOUTBOX_WHERE);

		sb.append(_FINDER_COLUMN_OBJECTLAYOUTTABID_OBJECTLAYOUTTABID_2);

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
			sb.append(ObjectLayoutBoxModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(objectLayoutTabId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						objectLayoutBox)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<ObjectLayoutBox> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the object layout boxes where objectLayoutTabId = &#63; from the database.
	 *
	 * @param objectLayoutTabId the object layout tab ID
	 */
	@Override
	public void removeByObjectLayoutTabId(long objectLayoutTabId) {
		for (ObjectLayoutBox objectLayoutBox :
				findByObjectLayoutTabId(
					objectLayoutTabId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(objectLayoutBox);
		}
	}

	/**
	 * Returns the number of object layout boxes where objectLayoutTabId = &#63;.
	 *
	 * @param objectLayoutTabId the object layout tab ID
	 * @return the number of matching object layout boxes
	 */
	@Override
	public int countByObjectLayoutTabId(long objectLayoutTabId) {
		FinderPath finderPath = _finderPathCountByObjectLayoutTabId;

		Object[] finderArgs = new Object[] {objectLayoutTabId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_OBJECTLAYOUTBOX_WHERE);

			sb.append(_FINDER_COLUMN_OBJECTLAYOUTTABID_OBJECTLAYOUTTABID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(objectLayoutTabId);

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
		_FINDER_COLUMN_OBJECTLAYOUTTABID_OBJECTLAYOUTTABID_2 =
			"objectLayoutBox.objectLayoutTabId = ?";

	public ObjectLayoutBoxPersistenceImpl() {
		Map<String, String> dbColumnNames = new HashMap<String, String>();

		dbColumnNames.put("uuid", "uuid_");

		setDBColumnNames(dbColumnNames);

		setModelClass(ObjectLayoutBox.class);

		setModelImplClass(ObjectLayoutBoxImpl.class);
		setModelPKClass(long.class);

		setTable(ObjectLayoutBoxTable.INSTANCE);
	}

	/**
	 * Caches the object layout box in the entity cache if it is enabled.
	 *
	 * @param objectLayoutBox the object layout box
	 */
	@Override
	public void cacheResult(ObjectLayoutBox objectLayoutBox) {
		entityCache.putResult(
			ObjectLayoutBoxImpl.class, objectLayoutBox.getPrimaryKey(),
			objectLayoutBox);
	}

	private int _valueObjectFinderCacheListThreshold;

	/**
	 * Caches the object layout boxes in the entity cache if it is enabled.
	 *
	 * @param objectLayoutBoxes the object layout boxes
	 */
	@Override
	public void cacheResult(List<ObjectLayoutBox> objectLayoutBoxes) {
		if ((_valueObjectFinderCacheListThreshold == 0) ||
			((_valueObjectFinderCacheListThreshold > 0) &&
			 (objectLayoutBoxes.size() >
				 _valueObjectFinderCacheListThreshold))) {

			return;
		}

		for (ObjectLayoutBox objectLayoutBox : objectLayoutBoxes) {
			if (entityCache.getResult(
					ObjectLayoutBoxImpl.class,
					objectLayoutBox.getPrimaryKey()) == null) {

				cacheResult(objectLayoutBox);
			}
		}
	}

	/**
	 * Clears the cache for all object layout boxes.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(ObjectLayoutBoxImpl.class);

		finderCache.clearCache(ObjectLayoutBoxImpl.class);
	}

	/**
	 * Clears the cache for the object layout box.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(ObjectLayoutBox objectLayoutBox) {
		entityCache.removeResult(ObjectLayoutBoxImpl.class, objectLayoutBox);
	}

	@Override
	public void clearCache(List<ObjectLayoutBox> objectLayoutBoxes) {
		for (ObjectLayoutBox objectLayoutBox : objectLayoutBoxes) {
			entityCache.removeResult(
				ObjectLayoutBoxImpl.class, objectLayoutBox);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(ObjectLayoutBoxImpl.class);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(ObjectLayoutBoxImpl.class, primaryKey);
		}
	}

	/**
	 * Creates a new object layout box with the primary key. Does not add the object layout box to the database.
	 *
	 * @param objectLayoutBoxId the primary key for the new object layout box
	 * @return the new object layout box
	 */
	@Override
	public ObjectLayoutBox create(long objectLayoutBoxId) {
		ObjectLayoutBox objectLayoutBox = new ObjectLayoutBoxImpl();

		objectLayoutBox.setNew(true);
		objectLayoutBox.setPrimaryKey(objectLayoutBoxId);

		String uuid = PortalUUIDUtil.generate();

		objectLayoutBox.setUuid(uuid);

		objectLayoutBox.setCompanyId(CompanyThreadLocal.getCompanyId());

		return objectLayoutBox;
	}

	/**
	 * Removes the object layout box with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param objectLayoutBoxId the primary key of the object layout box
	 * @return the object layout box that was removed
	 * @throws NoSuchObjectLayoutBoxException if a object layout box with the primary key could not be found
	 */
	@Override
	public ObjectLayoutBox remove(long objectLayoutBoxId)
		throws NoSuchObjectLayoutBoxException {

		return remove((Serializable)objectLayoutBoxId);
	}

	/**
	 * Removes the object layout box with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the object layout box
	 * @return the object layout box that was removed
	 * @throws NoSuchObjectLayoutBoxException if a object layout box with the primary key could not be found
	 */
	@Override
	public ObjectLayoutBox remove(Serializable primaryKey)
		throws NoSuchObjectLayoutBoxException {

		Session session = null;

		try {
			session = openSession();

			ObjectLayoutBox objectLayoutBox = (ObjectLayoutBox)session.get(
				ObjectLayoutBoxImpl.class, primaryKey);

			if (objectLayoutBox == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchObjectLayoutBoxException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(objectLayoutBox);
		}
		catch (NoSuchObjectLayoutBoxException noSuchEntityException) {
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
	protected ObjectLayoutBox removeImpl(ObjectLayoutBox objectLayoutBox) {
		Session session = null;

		try {
			session = openSession();

			if (!session.contains(objectLayoutBox)) {
				objectLayoutBox = (ObjectLayoutBox)session.get(
					ObjectLayoutBoxImpl.class,
					objectLayoutBox.getPrimaryKeyObj());
			}

			if (objectLayoutBox != null) {
				session.delete(objectLayoutBox);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (objectLayoutBox != null) {
			clearCache(objectLayoutBox);
		}

		return objectLayoutBox;
	}

	@Override
	public ObjectLayoutBox updateImpl(ObjectLayoutBox objectLayoutBox) {
		boolean isNew = objectLayoutBox.isNew();

		if (!(objectLayoutBox instanceof ObjectLayoutBoxModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(objectLayoutBox.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					objectLayoutBox);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in objectLayoutBox proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom ObjectLayoutBox implementation " +
					objectLayoutBox.getClass());
		}

		ObjectLayoutBoxModelImpl objectLayoutBoxModelImpl =
			(ObjectLayoutBoxModelImpl)objectLayoutBox;

		if (Validator.isNull(objectLayoutBox.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			objectLayoutBox.setUuid(uuid);
		}

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Date date = new Date();

		if (isNew && (objectLayoutBox.getCreateDate() == null)) {
			if (serviceContext == null) {
				objectLayoutBox.setCreateDate(date);
			}
			else {
				objectLayoutBox.setCreateDate(
					serviceContext.getCreateDate(date));
			}
		}

		if (!objectLayoutBoxModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				objectLayoutBox.setModifiedDate(date);
			}
			else {
				objectLayoutBox.setModifiedDate(
					serviceContext.getModifiedDate(date));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (isNew) {
				session.save(objectLayoutBox);
			}
			else {
				objectLayoutBox = (ObjectLayoutBox)session.merge(
					objectLayoutBox);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		entityCache.putResult(
			ObjectLayoutBoxImpl.class, objectLayoutBoxModelImpl, false, true);

		if (isNew) {
			objectLayoutBox.setNew(false);
		}

		objectLayoutBox.resetOriginalValues();

		return objectLayoutBox;
	}

	/**
	 * Returns the object layout box with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the object layout box
	 * @return the object layout box
	 * @throws NoSuchObjectLayoutBoxException if a object layout box with the primary key could not be found
	 */
	@Override
	public ObjectLayoutBox findByPrimaryKey(Serializable primaryKey)
		throws NoSuchObjectLayoutBoxException {

		ObjectLayoutBox objectLayoutBox = fetchByPrimaryKey(primaryKey);

		if (objectLayoutBox == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchObjectLayoutBoxException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return objectLayoutBox;
	}

	/**
	 * Returns the object layout box with the primary key or throws a <code>NoSuchObjectLayoutBoxException</code> if it could not be found.
	 *
	 * @param objectLayoutBoxId the primary key of the object layout box
	 * @return the object layout box
	 * @throws NoSuchObjectLayoutBoxException if a object layout box with the primary key could not be found
	 */
	@Override
	public ObjectLayoutBox findByPrimaryKey(long objectLayoutBoxId)
		throws NoSuchObjectLayoutBoxException {

		return findByPrimaryKey((Serializable)objectLayoutBoxId);
	}

	/**
	 * Returns the object layout box with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param objectLayoutBoxId the primary key of the object layout box
	 * @return the object layout box, or <code>null</code> if a object layout box with the primary key could not be found
	 */
	@Override
	public ObjectLayoutBox fetchByPrimaryKey(long objectLayoutBoxId) {
		return fetchByPrimaryKey((Serializable)objectLayoutBoxId);
	}

	/**
	 * Returns all the object layout boxes.
	 *
	 * @return the object layout boxes
	 */
	@Override
	public List<ObjectLayoutBox> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the object layout boxes.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectLayoutBoxModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of object layout boxes
	 * @param end the upper bound of the range of object layout boxes (not inclusive)
	 * @return the range of object layout boxes
	 */
	@Override
	public List<ObjectLayoutBox> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the object layout boxes.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectLayoutBoxModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of object layout boxes
	 * @param end the upper bound of the range of object layout boxes (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of object layout boxes
	 */
	@Override
	public List<ObjectLayoutBox> findAll(
		int start, int end,
		OrderByComparator<ObjectLayoutBox> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the object layout boxes.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>ObjectLayoutBoxModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of object layout boxes
	 * @param end the upper bound of the range of object layout boxes (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of object layout boxes
	 */
	@Override
	public List<ObjectLayoutBox> findAll(
		int start, int end,
		OrderByComparator<ObjectLayoutBox> orderByComparator,
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

		List<ObjectLayoutBox> list = null;

		if (useFinderCache) {
			list = (List<ObjectLayoutBox>)finderCache.getResult(
				finderPath, finderArgs);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_OBJECTLAYOUTBOX);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_OBJECTLAYOUTBOX;

				sql = sql.concat(ObjectLayoutBoxModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<ObjectLayoutBox>)QueryUtil.list(
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
	 * Removes all the object layout boxes from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (ObjectLayoutBox objectLayoutBox : findAll()) {
			remove(objectLayoutBox);
		}
	}

	/**
	 * Returns the number of object layout boxes.
	 *
	 * @return the number of object layout boxes
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(
			_finderPathCountAll, FINDER_ARGS_EMPTY);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(_SQL_COUNT_OBJECTLAYOUTBOX);

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
		return "objectLayoutBoxId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_OBJECTLAYOUTBOX;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return ObjectLayoutBoxModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the object layout box persistence.
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

		_finderPathWithPaginationFindByObjectLayoutTabId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByObjectLayoutTabId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"objectLayoutTabId"}, true);

		_finderPathWithoutPaginationFindByObjectLayoutTabId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByObjectLayoutTabId", new String[] {Long.class.getName()},
			new String[] {"objectLayoutTabId"}, true);

		_finderPathCountByObjectLayoutTabId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByObjectLayoutTabId", new String[] {Long.class.getName()},
			new String[] {"objectLayoutTabId"}, false);
	}

	@Deactivate
	public void deactivate() {
		entityCache.removeCache(ObjectLayoutBoxImpl.class.getName());
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

	private static final String _SQL_SELECT_OBJECTLAYOUTBOX =
		"SELECT objectLayoutBox FROM ObjectLayoutBox objectLayoutBox";

	private static final String _SQL_SELECT_OBJECTLAYOUTBOX_WHERE =
		"SELECT objectLayoutBox FROM ObjectLayoutBox objectLayoutBox WHERE ";

	private static final String _SQL_COUNT_OBJECTLAYOUTBOX =
		"SELECT COUNT(objectLayoutBox) FROM ObjectLayoutBox objectLayoutBox";

	private static final String _SQL_COUNT_OBJECTLAYOUTBOX_WHERE =
		"SELECT COUNT(objectLayoutBox) FROM ObjectLayoutBox objectLayoutBox WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS = "objectLayoutBox.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No ObjectLayoutBox exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No ObjectLayoutBox exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		ObjectLayoutBoxPersistenceImpl.class);

	private static final Set<String> _badColumnNames = SetUtil.fromArray(
		new String[] {"uuid"});

	@Override
	protected FinderCache getFinderCache() {
		return finderCache;
	}

	@Reference
	private ObjectLayoutBoxModelArgumentsResolver
		_objectLayoutBoxModelArgumentsResolver;

}