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

package com.liferay.service.access.policy.service.persistence.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.dao.orm.EntityCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.SQLQuery;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;
import com.liferay.portal.model.CacheModel;
import com.liferay.portal.security.permission.InlineSQLHelperUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceContextThreadLocal;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;

import com.liferay.service.access.policy.exception.NoSuchServiceAccessPolicyException;
import com.liferay.service.access.policy.model.ServiceAccessPolicy;
import com.liferay.service.access.policy.model.impl.ServiceAccessPolicyImpl;
import com.liferay.service.access.policy.model.impl.ServiceAccessPolicyModelImpl;
import com.liferay.service.access.policy.service.persistence.ServiceAccessPolicyPersistence;

import java.io.Serializable;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The persistence implementation for the service access policy service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see ServiceAccessPolicyPersistence
 * @see com.liferay.service.access.policy.service.persistence.ServiceAccessPolicyUtil
 * @generated
 */
@ProviderType
public class ServiceAccessPolicyPersistenceImpl extends BasePersistenceImpl<ServiceAccessPolicy>
	implements ServiceAccessPolicyPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link ServiceAccessPolicyUtil} to access the service access policy persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = ServiceAccessPolicyImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(ServiceAccessPolicyModelImpl.ENTITY_CACHE_ENABLED,
			ServiceAccessPolicyModelImpl.FINDER_CACHE_ENABLED,
			ServiceAccessPolicyImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(ServiceAccessPolicyModelImpl.ENTITY_CACHE_ENABLED,
			ServiceAccessPolicyModelImpl.FINDER_CACHE_ENABLED,
			ServiceAccessPolicyImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(ServiceAccessPolicyModelImpl.ENTITY_CACHE_ENABLED,
			ServiceAccessPolicyModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_UUID = new FinderPath(ServiceAccessPolicyModelImpl.ENTITY_CACHE_ENABLED,
			ServiceAccessPolicyModelImpl.FINDER_CACHE_ENABLED,
			ServiceAccessPolicyImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUuid",
			new String[] {
				String.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID = new FinderPath(ServiceAccessPolicyModelImpl.ENTITY_CACHE_ENABLED,
			ServiceAccessPolicyModelImpl.FINDER_CACHE_ENABLED,
			ServiceAccessPolicyImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid",
			new String[] { String.class.getName() },
			ServiceAccessPolicyModelImpl.UUID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID = new FinderPath(ServiceAccessPolicyModelImpl.ENTITY_CACHE_ENABLED,
			ServiceAccessPolicyModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUuid",
			new String[] { String.class.getName() });

	/**
	 * Returns all the service access policies where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching service access policies
	 */
	@Override
	public List<ServiceAccessPolicy> findByUuid(String uuid) {
		return findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the service access policies where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.service.access.policy.model.impl.ServiceAccessPolicyModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of service access policies
	 * @param end the upper bound of the range of service access policies (not inclusive)
	 * @return the range of matching service access policies
	 */
	@Override
	public List<ServiceAccessPolicy> findByUuid(String uuid, int start, int end) {
		return findByUuid(uuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the service access policies where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.service.access.policy.model.impl.ServiceAccessPolicyModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of service access policies
	 * @param end the upper bound of the range of service access policies (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching service access policies
	 */
	@Override
	public List<ServiceAccessPolicy> findByUuid(String uuid, int start,
		int end, OrderByComparator<ServiceAccessPolicy> orderByComparator) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID;
			finderArgs = new Object[] { uuid };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_UUID;
			finderArgs = new Object[] { uuid, start, end, orderByComparator };
		}

		List<ServiceAccessPolicy> list = (List<ServiceAccessPolicy>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (ServiceAccessPolicy serviceAccessPolicy : list) {
				if (!Validator.equals(uuid, serviceAccessPolicy.getUuid())) {
					list = null;

					break;
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(3 +
						(orderByComparator.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(3);
			}

			query.append(_SQL_SELECT_SERVICEACCESSPOLICY_WHERE);

			boolean bindUuid = false;

			if (uuid == null) {
				query.append(_FINDER_COLUMN_UUID_UUID_1);
			}
			else if (uuid.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_UUID_UUID_3);
			}
			else {
				bindUuid = true;

				query.append(_FINDER_COLUMN_UUID_UUID_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(ServiceAccessPolicyModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindUuid) {
					qPos.add(uuid);
				}

				if (!pagination) {
					list = (List<ServiceAccessPolicy>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<ServiceAccessPolicy>)QueryUtil.list(q,
							getDialect(), start, end);
				}

				cacheResult(list);

				FinderCacheUtil.putResult(finderPath, finderArgs, list);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first service access policy in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching service access policy
	 * @throws com.liferay.service.access.policy.NoSuchServiceAccessPolicyException if a matching service access policy could not be found
	 */
	@Override
	public ServiceAccessPolicy findByUuid_First(String uuid,
		OrderByComparator<ServiceAccessPolicy> orderByComparator)
		throws NoSuchServiceAccessPolicyException {
		ServiceAccessPolicy serviceAccessPolicy = fetchByUuid_First(uuid,
				orderByComparator);

		if (serviceAccessPolicy != null) {
			return serviceAccessPolicy;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchServiceAccessPolicyException(msg.toString());
	}

	/**
	 * Returns the first service access policy in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching service access policy, or <code>null</code> if a matching service access policy could not be found
	 */
	@Override
	public ServiceAccessPolicy fetchByUuid_First(String uuid,
		OrderByComparator<ServiceAccessPolicy> orderByComparator) {
		List<ServiceAccessPolicy> list = findByUuid(uuid, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last service access policy in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching service access policy
	 * @throws com.liferay.service.access.policy.NoSuchServiceAccessPolicyException if a matching service access policy could not be found
	 */
	@Override
	public ServiceAccessPolicy findByUuid_Last(String uuid,
		OrderByComparator<ServiceAccessPolicy> orderByComparator)
		throws NoSuchServiceAccessPolicyException {
		ServiceAccessPolicy serviceAccessPolicy = fetchByUuid_Last(uuid,
				orderByComparator);

		if (serviceAccessPolicy != null) {
			return serviceAccessPolicy;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchServiceAccessPolicyException(msg.toString());
	}

	/**
	 * Returns the last service access policy in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching service access policy, or <code>null</code> if a matching service access policy could not be found
	 */
	@Override
	public ServiceAccessPolicy fetchByUuid_Last(String uuid,
		OrderByComparator<ServiceAccessPolicy> orderByComparator) {
		int count = countByUuid(uuid);

		if (count == 0) {
			return null;
		}

		List<ServiceAccessPolicy> list = findByUuid(uuid, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the service access policies before and after the current service access policy in the ordered set where uuid = &#63;.
	 *
	 * @param serviceAccessPolicyId the primary key of the current service access policy
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next service access policy
	 * @throws com.liferay.service.access.policy.NoSuchServiceAccessPolicyException if a service access policy with the primary key could not be found
	 */
	@Override
	public ServiceAccessPolicy[] findByUuid_PrevAndNext(
		long serviceAccessPolicyId, String uuid,
		OrderByComparator<ServiceAccessPolicy> orderByComparator)
		throws NoSuchServiceAccessPolicyException {
		ServiceAccessPolicy serviceAccessPolicy = findByPrimaryKey(serviceAccessPolicyId);

		Session session = null;

		try {
			session = openSession();

			ServiceAccessPolicy[] array = new ServiceAccessPolicyImpl[3];

			array[0] = getByUuid_PrevAndNext(session, serviceAccessPolicy,
					uuid, orderByComparator, true);

			array[1] = serviceAccessPolicy;

			array[2] = getByUuid_PrevAndNext(session, serviceAccessPolicy,
					uuid, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected ServiceAccessPolicy getByUuid_PrevAndNext(Session session,
		ServiceAccessPolicy serviceAccessPolicy, String uuid,
		OrderByComparator<ServiceAccessPolicy> orderByComparator,
		boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_SERVICEACCESSPOLICY_WHERE);

		boolean bindUuid = false;

		if (uuid == null) {
			query.append(_FINDER_COLUMN_UUID_UUID_1);
		}
		else if (uuid.equals(StringPool.BLANK)) {
			query.append(_FINDER_COLUMN_UUID_UUID_3);
		}
		else {
			bindUuid = true;

			query.append(_FINDER_COLUMN_UUID_UUID_2);
		}

		if (orderByComparator != null) {
			String[] orderByConditionFields = orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				query.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByConditionFields[i]);

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						query.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN);
					}
					else {
						query.append(WHERE_LESSER_THAN);
					}
				}
			}

			query.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						query.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC);
					}
					else {
						query.append(ORDER_BY_DESC);
					}
				}
			}
		}
		else {
			query.append(ServiceAccessPolicyModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		if (bindUuid) {
			qPos.add(uuid);
		}

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(serviceAccessPolicy);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<ServiceAccessPolicy> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the service access policies that the user has permission to view where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching service access policies that the user has permission to view
	 */
	@Override
	public List<ServiceAccessPolicy> filterFindByUuid(String uuid) {
		return filterFindByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the service access policies that the user has permission to view where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.service.access.policy.model.impl.ServiceAccessPolicyModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of service access policies
	 * @param end the upper bound of the range of service access policies (not inclusive)
	 * @return the range of matching service access policies that the user has permission to view
	 */
	@Override
	public List<ServiceAccessPolicy> filterFindByUuid(String uuid, int start,
		int end) {
		return filterFindByUuid(uuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the service access policies that the user has permissions to view where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.service.access.policy.model.impl.ServiceAccessPolicyModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of service access policies
	 * @param end the upper bound of the range of service access policies (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching service access policies that the user has permission to view
	 */
	@Override
	public List<ServiceAccessPolicy> filterFindByUuid(String uuid, int start,
		int end, OrderByComparator<ServiceAccessPolicy> orderByComparator) {
		if (!InlineSQLHelperUtil.isEnabled()) {
			return findByUuid(uuid, start, end, orderByComparator);
		}

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(3 +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(3);
		}

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_SERVICEACCESSPOLICY_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_SERVICEACCESSPOLICY_NO_INLINE_DISTINCT_WHERE_1);
		}

		boolean bindUuid = false;

		if (uuid == null) {
			query.append(_FINDER_COLUMN_UUID_UUID_1_SQL);
		}
		else if (uuid.equals(StringPool.BLANK)) {
			query.append(_FINDER_COLUMN_UUID_UUID_3_SQL);
		}
		else {
			bindUuid = true;

			query.append(_FINDER_COLUMN_UUID_UUID_2_SQL);
		}

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_SERVICEACCESSPOLICY_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			if (getDB().isSupportsInlineDistinct()) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator, true);
			}
			else {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_TABLE,
					orderByComparator, true);
			}
		}
		else {
			if (getDB().isSupportsInlineDistinct()) {
				query.append(ServiceAccessPolicyModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(ServiceAccessPolicyModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				ServiceAccessPolicy.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				q.addEntity(_FILTER_ENTITY_ALIAS, ServiceAccessPolicyImpl.class);
			}
			else {
				q.addEntity(_FILTER_ENTITY_TABLE, ServiceAccessPolicyImpl.class);
			}

			QueryPos qPos = QueryPos.getInstance(q);

			if (bindUuid) {
				qPos.add(uuid);
			}

			return (List<ServiceAccessPolicy>)QueryUtil.list(q, getDialect(),
				start, end);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Returns the service access policies before and after the current service access policy in the ordered set of service access policies that the user has permission to view where uuid = &#63;.
	 *
	 * @param serviceAccessPolicyId the primary key of the current service access policy
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next service access policy
	 * @throws com.liferay.service.access.policy.NoSuchServiceAccessPolicyException if a service access policy with the primary key could not be found
	 */
	@Override
	public ServiceAccessPolicy[] filterFindByUuid_PrevAndNext(
		long serviceAccessPolicyId, String uuid,
		OrderByComparator<ServiceAccessPolicy> orderByComparator)
		throws NoSuchServiceAccessPolicyException {
		if (!InlineSQLHelperUtil.isEnabled()) {
			return findByUuid_PrevAndNext(serviceAccessPolicyId, uuid,
				orderByComparator);
		}

		ServiceAccessPolicy serviceAccessPolicy = findByPrimaryKey(serviceAccessPolicyId);

		Session session = null;

		try {
			session = openSession();

			ServiceAccessPolicy[] array = new ServiceAccessPolicyImpl[3];

			array[0] = filterGetByUuid_PrevAndNext(session,
					serviceAccessPolicy, uuid, orderByComparator, true);

			array[1] = serviceAccessPolicy;

			array[2] = filterGetByUuid_PrevAndNext(session,
					serviceAccessPolicy, uuid, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected ServiceAccessPolicy filterGetByUuid_PrevAndNext(Session session,
		ServiceAccessPolicy serviceAccessPolicy, String uuid,
		OrderByComparator<ServiceAccessPolicy> orderByComparator,
		boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_SERVICEACCESSPOLICY_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_SERVICEACCESSPOLICY_NO_INLINE_DISTINCT_WHERE_1);
		}

		boolean bindUuid = false;

		if (uuid == null) {
			query.append(_FINDER_COLUMN_UUID_UUID_1_SQL);
		}
		else if (uuid.equals(StringPool.BLANK)) {
			query.append(_FINDER_COLUMN_UUID_UUID_3_SQL);
		}
		else {
			bindUuid = true;

			query.append(_FINDER_COLUMN_UUID_UUID_2_SQL);
		}

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_SERVICEACCESSPOLICY_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			String[] orderByConditionFields = orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				query.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				if (getDB().isSupportsInlineDistinct()) {
					query.append(_ORDER_BY_ENTITY_ALIAS);
				}
				else {
					query.append(_ORDER_BY_ENTITY_TABLE);
				}

				query.append(orderByConditionFields[i]);

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						query.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN);
					}
					else {
						query.append(WHERE_LESSER_THAN);
					}
				}
			}

			query.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				if (getDB().isSupportsInlineDistinct()) {
					query.append(_ORDER_BY_ENTITY_ALIAS);
				}
				else {
					query.append(_ORDER_BY_ENTITY_TABLE);
				}

				query.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						query.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC);
					}
					else {
						query.append(ORDER_BY_DESC);
					}
				}
			}
		}
		else {
			if (getDB().isSupportsInlineDistinct()) {
				query.append(ServiceAccessPolicyModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(ServiceAccessPolicyModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				ServiceAccessPolicy.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		SQLQuery q = session.createSynchronizedSQLQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		if (getDB().isSupportsInlineDistinct()) {
			q.addEntity(_FILTER_ENTITY_ALIAS, ServiceAccessPolicyImpl.class);
		}
		else {
			q.addEntity(_FILTER_ENTITY_TABLE, ServiceAccessPolicyImpl.class);
		}

		QueryPos qPos = QueryPos.getInstance(q);

		if (bindUuid) {
			qPos.add(uuid);
		}

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(serviceAccessPolicy);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<ServiceAccessPolicy> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the service access policies where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	@Override
	public void removeByUuid(String uuid) {
		for (ServiceAccessPolicy serviceAccessPolicy : findByUuid(uuid,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(serviceAccessPolicy);
		}
	}

	/**
	 * Returns the number of service access policies where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching service access policies
	 */
	@Override
	public int countByUuid(String uuid) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_UUID;

		Object[] finderArgs = new Object[] { uuid };

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs,
				this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_SERVICEACCESSPOLICY_WHERE);

			boolean bindUuid = false;

			if (uuid == null) {
				query.append(_FINDER_COLUMN_UUID_UUID_1);
			}
			else if (uuid.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_UUID_UUID_3);
			}
			else {
				bindUuid = true;

				query.append(_FINDER_COLUMN_UUID_UUID_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindUuid) {
					qPos.add(uuid);
				}

				count = (Long)q.uniqueResult();

				FinderCacheUtil.putResult(finderPath, finderArgs, count);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of service access policies that the user has permission to view where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching service access policies that the user has permission to view
	 */
	@Override
	public int filterCountByUuid(String uuid) {
		if (!InlineSQLHelperUtil.isEnabled()) {
			return countByUuid(uuid);
		}

		StringBundler query = new StringBundler(2);

		query.append(_FILTER_SQL_COUNT_SERVICEACCESSPOLICY_WHERE);

		boolean bindUuid = false;

		if (uuid == null) {
			query.append(_FINDER_COLUMN_UUID_UUID_1_SQL);
		}
		else if (uuid.equals(StringPool.BLANK)) {
			query.append(_FINDER_COLUMN_UUID_UUID_3_SQL);
		}
		else {
			bindUuid = true;

			query.append(_FINDER_COLUMN_UUID_UUID_2_SQL);
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				ServiceAccessPolicy.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME,
				com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			if (bindUuid) {
				qPos.add(uuid);
			}

			Long count = (Long)q.uniqueResult();

			return count.intValue();
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	private static final String _FINDER_COLUMN_UUID_UUID_1 = "serviceAccessPolicy.uuid IS NULL";
	private static final String _FINDER_COLUMN_UUID_UUID_2 = "serviceAccessPolicy.uuid = ?";
	private static final String _FINDER_COLUMN_UUID_UUID_3 = "(serviceAccessPolicy.uuid IS NULL OR serviceAccessPolicy.uuid = '')";
	private static final String _FINDER_COLUMN_UUID_UUID_1_SQL = "serviceAccessPolicy.uuid_ IS NULL";
	private static final String _FINDER_COLUMN_UUID_UUID_2_SQL = "serviceAccessPolicy.uuid_ = ?";
	private static final String _FINDER_COLUMN_UUID_UUID_3_SQL = "(serviceAccessPolicy.uuid_ IS NULL OR serviceAccessPolicy.uuid_ = '')";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_UUID_C = new FinderPath(ServiceAccessPolicyModelImpl.ENTITY_CACHE_ENABLED,
			ServiceAccessPolicyModelImpl.FINDER_CACHE_ENABLED,
			ServiceAccessPolicyImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUuid_C",
			new String[] {
				String.class.getName(), Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C =
		new FinderPath(ServiceAccessPolicyModelImpl.ENTITY_CACHE_ENABLED,
			ServiceAccessPolicyModelImpl.FINDER_CACHE_ENABLED,
			ServiceAccessPolicyImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid_C",
			new String[] { String.class.getName(), Long.class.getName() },
			ServiceAccessPolicyModelImpl.UUID_COLUMN_BITMASK |
			ServiceAccessPolicyModelImpl.COMPANYID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID_C = new FinderPath(ServiceAccessPolicyModelImpl.ENTITY_CACHE_ENABLED,
			ServiceAccessPolicyModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUuid_C",
			new String[] { String.class.getName(), Long.class.getName() });

	/**
	 * Returns all the service access policies where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching service access policies
	 */
	@Override
	public List<ServiceAccessPolicy> findByUuid_C(String uuid, long companyId) {
		return findByUuid_C(uuid, companyId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the service access policies where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.service.access.policy.model.impl.ServiceAccessPolicyModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of service access policies
	 * @param end the upper bound of the range of service access policies (not inclusive)
	 * @return the range of matching service access policies
	 */
	@Override
	public List<ServiceAccessPolicy> findByUuid_C(String uuid, long companyId,
		int start, int end) {
		return findByUuid_C(uuid, companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the service access policies where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.service.access.policy.model.impl.ServiceAccessPolicyModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of service access policies
	 * @param end the upper bound of the range of service access policies (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching service access policies
	 */
	@Override
	public List<ServiceAccessPolicy> findByUuid_C(String uuid, long companyId,
		int start, int end,
		OrderByComparator<ServiceAccessPolicy> orderByComparator) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C;
			finderArgs = new Object[] { uuid, companyId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_UUID_C;
			finderArgs = new Object[] {
					uuid, companyId,
					
					start, end, orderByComparator
				};
		}

		List<ServiceAccessPolicy> list = (List<ServiceAccessPolicy>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (ServiceAccessPolicy serviceAccessPolicy : list) {
				if (!Validator.equals(uuid, serviceAccessPolicy.getUuid()) ||
						(companyId != serviceAccessPolicy.getCompanyId())) {
					list = null;

					break;
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(4 +
						(orderByComparator.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(4);
			}

			query.append(_SQL_SELECT_SERVICEACCESSPOLICY_WHERE);

			boolean bindUuid = false;

			if (uuid == null) {
				query.append(_FINDER_COLUMN_UUID_C_UUID_1);
			}
			else if (uuid.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_UUID_C_UUID_3);
			}
			else {
				bindUuid = true;

				query.append(_FINDER_COLUMN_UUID_C_UUID_2);
			}

			query.append(_FINDER_COLUMN_UUID_C_COMPANYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(ServiceAccessPolicyModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindUuid) {
					qPos.add(uuid);
				}

				qPos.add(companyId);

				if (!pagination) {
					list = (List<ServiceAccessPolicy>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<ServiceAccessPolicy>)QueryUtil.list(q,
							getDialect(), start, end);
				}

				cacheResult(list);

				FinderCacheUtil.putResult(finderPath, finderArgs, list);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first service access policy in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching service access policy
	 * @throws com.liferay.service.access.policy.NoSuchServiceAccessPolicyException if a matching service access policy could not be found
	 */
	@Override
	public ServiceAccessPolicy findByUuid_C_First(String uuid, long companyId,
		OrderByComparator<ServiceAccessPolicy> orderByComparator)
		throws NoSuchServiceAccessPolicyException {
		ServiceAccessPolicy serviceAccessPolicy = fetchByUuid_C_First(uuid,
				companyId, orderByComparator);

		if (serviceAccessPolicy != null) {
			return serviceAccessPolicy;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(", companyId=");
		msg.append(companyId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchServiceAccessPolicyException(msg.toString());
	}

	/**
	 * Returns the first service access policy in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching service access policy, or <code>null</code> if a matching service access policy could not be found
	 */
	@Override
	public ServiceAccessPolicy fetchByUuid_C_First(String uuid, long companyId,
		OrderByComparator<ServiceAccessPolicy> orderByComparator) {
		List<ServiceAccessPolicy> list = findByUuid_C(uuid, companyId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last service access policy in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching service access policy
	 * @throws com.liferay.service.access.policy.NoSuchServiceAccessPolicyException if a matching service access policy could not be found
	 */
	@Override
	public ServiceAccessPolicy findByUuid_C_Last(String uuid, long companyId,
		OrderByComparator<ServiceAccessPolicy> orderByComparator)
		throws NoSuchServiceAccessPolicyException {
		ServiceAccessPolicy serviceAccessPolicy = fetchByUuid_C_Last(uuid,
				companyId, orderByComparator);

		if (serviceAccessPolicy != null) {
			return serviceAccessPolicy;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(", companyId=");
		msg.append(companyId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchServiceAccessPolicyException(msg.toString());
	}

	/**
	 * Returns the last service access policy in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching service access policy, or <code>null</code> if a matching service access policy could not be found
	 */
	@Override
	public ServiceAccessPolicy fetchByUuid_C_Last(String uuid, long companyId,
		OrderByComparator<ServiceAccessPolicy> orderByComparator) {
		int count = countByUuid_C(uuid, companyId);

		if (count == 0) {
			return null;
		}

		List<ServiceAccessPolicy> list = findByUuid_C(uuid, companyId,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the service access policies before and after the current service access policy in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param serviceAccessPolicyId the primary key of the current service access policy
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next service access policy
	 * @throws com.liferay.service.access.policy.NoSuchServiceAccessPolicyException if a service access policy with the primary key could not be found
	 */
	@Override
	public ServiceAccessPolicy[] findByUuid_C_PrevAndNext(
		long serviceAccessPolicyId, String uuid, long companyId,
		OrderByComparator<ServiceAccessPolicy> orderByComparator)
		throws NoSuchServiceAccessPolicyException {
		ServiceAccessPolicy serviceAccessPolicy = findByPrimaryKey(serviceAccessPolicyId);

		Session session = null;

		try {
			session = openSession();

			ServiceAccessPolicy[] array = new ServiceAccessPolicyImpl[3];

			array[0] = getByUuid_C_PrevAndNext(session, serviceAccessPolicy,
					uuid, companyId, orderByComparator, true);

			array[1] = serviceAccessPolicy;

			array[2] = getByUuid_C_PrevAndNext(session, serviceAccessPolicy,
					uuid, companyId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected ServiceAccessPolicy getByUuid_C_PrevAndNext(Session session,
		ServiceAccessPolicy serviceAccessPolicy, String uuid, long companyId,
		OrderByComparator<ServiceAccessPolicy> orderByComparator,
		boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_SERVICEACCESSPOLICY_WHERE);

		boolean bindUuid = false;

		if (uuid == null) {
			query.append(_FINDER_COLUMN_UUID_C_UUID_1);
		}
		else if (uuid.equals(StringPool.BLANK)) {
			query.append(_FINDER_COLUMN_UUID_C_UUID_3);
		}
		else {
			bindUuid = true;

			query.append(_FINDER_COLUMN_UUID_C_UUID_2);
		}

		query.append(_FINDER_COLUMN_UUID_C_COMPANYID_2);

		if (orderByComparator != null) {
			String[] orderByConditionFields = orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				query.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByConditionFields[i]);

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						query.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN);
					}
					else {
						query.append(WHERE_LESSER_THAN);
					}
				}
			}

			query.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						query.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC);
					}
					else {
						query.append(ORDER_BY_DESC);
					}
				}
			}
		}
		else {
			query.append(ServiceAccessPolicyModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		if (bindUuid) {
			qPos.add(uuid);
		}

		qPos.add(companyId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(serviceAccessPolicy);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<ServiceAccessPolicy> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the service access policies that the user has permission to view where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching service access policies that the user has permission to view
	 */
	@Override
	public List<ServiceAccessPolicy> filterFindByUuid_C(String uuid,
		long companyId) {
		return filterFindByUuid_C(uuid, companyId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the service access policies that the user has permission to view where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.service.access.policy.model.impl.ServiceAccessPolicyModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of service access policies
	 * @param end the upper bound of the range of service access policies (not inclusive)
	 * @return the range of matching service access policies that the user has permission to view
	 */
	@Override
	public List<ServiceAccessPolicy> filterFindByUuid_C(String uuid,
		long companyId, int start, int end) {
		return filterFindByUuid_C(uuid, companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the service access policies that the user has permissions to view where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.service.access.policy.model.impl.ServiceAccessPolicyModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of service access policies
	 * @param end the upper bound of the range of service access policies (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching service access policies that the user has permission to view
	 */
	@Override
	public List<ServiceAccessPolicy> filterFindByUuid_C(String uuid,
		long companyId, int start, int end,
		OrderByComparator<ServiceAccessPolicy> orderByComparator) {
		if (!InlineSQLHelperUtil.isEnabled()) {
			return findByUuid_C(uuid, companyId, start, end, orderByComparator);
		}

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(4 +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(4);
		}

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_SERVICEACCESSPOLICY_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_SERVICEACCESSPOLICY_NO_INLINE_DISTINCT_WHERE_1);
		}

		boolean bindUuid = false;

		if (uuid == null) {
			query.append(_FINDER_COLUMN_UUID_C_UUID_1_SQL);
		}
		else if (uuid.equals(StringPool.BLANK)) {
			query.append(_FINDER_COLUMN_UUID_C_UUID_3_SQL);
		}
		else {
			bindUuid = true;

			query.append(_FINDER_COLUMN_UUID_C_UUID_2_SQL);
		}

		query.append(_FINDER_COLUMN_UUID_C_COMPANYID_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_SERVICEACCESSPOLICY_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			if (getDB().isSupportsInlineDistinct()) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator, true);
			}
			else {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_TABLE,
					orderByComparator, true);
			}
		}
		else {
			if (getDB().isSupportsInlineDistinct()) {
				query.append(ServiceAccessPolicyModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(ServiceAccessPolicyModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				ServiceAccessPolicy.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				q.addEntity(_FILTER_ENTITY_ALIAS, ServiceAccessPolicyImpl.class);
			}
			else {
				q.addEntity(_FILTER_ENTITY_TABLE, ServiceAccessPolicyImpl.class);
			}

			QueryPos qPos = QueryPos.getInstance(q);

			if (bindUuid) {
				qPos.add(uuid);
			}

			qPos.add(companyId);

			return (List<ServiceAccessPolicy>)QueryUtil.list(q, getDialect(),
				start, end);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Returns the service access policies before and after the current service access policy in the ordered set of service access policies that the user has permission to view where uuid = &#63; and companyId = &#63;.
	 *
	 * @param serviceAccessPolicyId the primary key of the current service access policy
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next service access policy
	 * @throws com.liferay.service.access.policy.NoSuchServiceAccessPolicyException if a service access policy with the primary key could not be found
	 */
	@Override
	public ServiceAccessPolicy[] filterFindByUuid_C_PrevAndNext(
		long serviceAccessPolicyId, String uuid, long companyId,
		OrderByComparator<ServiceAccessPolicy> orderByComparator)
		throws NoSuchServiceAccessPolicyException {
		if (!InlineSQLHelperUtil.isEnabled()) {
			return findByUuid_C_PrevAndNext(serviceAccessPolicyId, uuid,
				companyId, orderByComparator);
		}

		ServiceAccessPolicy serviceAccessPolicy = findByPrimaryKey(serviceAccessPolicyId);

		Session session = null;

		try {
			session = openSession();

			ServiceAccessPolicy[] array = new ServiceAccessPolicyImpl[3];

			array[0] = filterGetByUuid_C_PrevAndNext(session,
					serviceAccessPolicy, uuid, companyId, orderByComparator,
					true);

			array[1] = serviceAccessPolicy;

			array[2] = filterGetByUuid_C_PrevAndNext(session,
					serviceAccessPolicy, uuid, companyId, orderByComparator,
					false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected ServiceAccessPolicy filterGetByUuid_C_PrevAndNext(
		Session session, ServiceAccessPolicy serviceAccessPolicy, String uuid,
		long companyId,
		OrderByComparator<ServiceAccessPolicy> orderByComparator,
		boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_SERVICEACCESSPOLICY_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_SERVICEACCESSPOLICY_NO_INLINE_DISTINCT_WHERE_1);
		}

		boolean bindUuid = false;

		if (uuid == null) {
			query.append(_FINDER_COLUMN_UUID_C_UUID_1_SQL);
		}
		else if (uuid.equals(StringPool.BLANK)) {
			query.append(_FINDER_COLUMN_UUID_C_UUID_3_SQL);
		}
		else {
			bindUuid = true;

			query.append(_FINDER_COLUMN_UUID_C_UUID_2_SQL);
		}

		query.append(_FINDER_COLUMN_UUID_C_COMPANYID_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_SERVICEACCESSPOLICY_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			String[] orderByConditionFields = orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				query.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				if (getDB().isSupportsInlineDistinct()) {
					query.append(_ORDER_BY_ENTITY_ALIAS);
				}
				else {
					query.append(_ORDER_BY_ENTITY_TABLE);
				}

				query.append(orderByConditionFields[i]);

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						query.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN);
					}
					else {
						query.append(WHERE_LESSER_THAN);
					}
				}
			}

			query.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				if (getDB().isSupportsInlineDistinct()) {
					query.append(_ORDER_BY_ENTITY_ALIAS);
				}
				else {
					query.append(_ORDER_BY_ENTITY_TABLE);
				}

				query.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						query.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC);
					}
					else {
						query.append(ORDER_BY_DESC);
					}
				}
			}
		}
		else {
			if (getDB().isSupportsInlineDistinct()) {
				query.append(ServiceAccessPolicyModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(ServiceAccessPolicyModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				ServiceAccessPolicy.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		SQLQuery q = session.createSynchronizedSQLQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		if (getDB().isSupportsInlineDistinct()) {
			q.addEntity(_FILTER_ENTITY_ALIAS, ServiceAccessPolicyImpl.class);
		}
		else {
			q.addEntity(_FILTER_ENTITY_TABLE, ServiceAccessPolicyImpl.class);
		}

		QueryPos qPos = QueryPos.getInstance(q);

		if (bindUuid) {
			qPos.add(uuid);
		}

		qPos.add(companyId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(serviceAccessPolicy);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<ServiceAccessPolicy> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the service access policies where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	@Override
	public void removeByUuid_C(String uuid, long companyId) {
		for (ServiceAccessPolicy serviceAccessPolicy : findByUuid_C(uuid,
				companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(serviceAccessPolicy);
		}
	}

	/**
	 * Returns the number of service access policies where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching service access policies
	 */
	@Override
	public int countByUuid_C(String uuid, long companyId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_UUID_C;

		Object[] finderArgs = new Object[] { uuid, companyId };

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs,
				this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_SERVICEACCESSPOLICY_WHERE);

			boolean bindUuid = false;

			if (uuid == null) {
				query.append(_FINDER_COLUMN_UUID_C_UUID_1);
			}
			else if (uuid.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_UUID_C_UUID_3);
			}
			else {
				bindUuid = true;

				query.append(_FINDER_COLUMN_UUID_C_UUID_2);
			}

			query.append(_FINDER_COLUMN_UUID_C_COMPANYID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindUuid) {
					qPos.add(uuid);
				}

				qPos.add(companyId);

				count = (Long)q.uniqueResult();

				FinderCacheUtil.putResult(finderPath, finderArgs, count);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of service access policies that the user has permission to view where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching service access policies that the user has permission to view
	 */
	@Override
	public int filterCountByUuid_C(String uuid, long companyId) {
		if (!InlineSQLHelperUtil.isEnabled()) {
			return countByUuid_C(uuid, companyId);
		}

		StringBundler query = new StringBundler(3);

		query.append(_FILTER_SQL_COUNT_SERVICEACCESSPOLICY_WHERE);

		boolean bindUuid = false;

		if (uuid == null) {
			query.append(_FINDER_COLUMN_UUID_C_UUID_1_SQL);
		}
		else if (uuid.equals(StringPool.BLANK)) {
			query.append(_FINDER_COLUMN_UUID_C_UUID_3_SQL);
		}
		else {
			bindUuid = true;

			query.append(_FINDER_COLUMN_UUID_C_UUID_2_SQL);
		}

		query.append(_FINDER_COLUMN_UUID_C_COMPANYID_2);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				ServiceAccessPolicy.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME,
				com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			if (bindUuid) {
				qPos.add(uuid);
			}

			qPos.add(companyId);

			Long count = (Long)q.uniqueResult();

			return count.intValue();
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	private static final String _FINDER_COLUMN_UUID_C_UUID_1 = "serviceAccessPolicy.uuid IS NULL AND ";
	private static final String _FINDER_COLUMN_UUID_C_UUID_2 = "serviceAccessPolicy.uuid = ? AND ";
	private static final String _FINDER_COLUMN_UUID_C_UUID_3 = "(serviceAccessPolicy.uuid IS NULL OR serviceAccessPolicy.uuid = '') AND ";
	private static final String _FINDER_COLUMN_UUID_C_UUID_1_SQL = "serviceAccessPolicy.uuid_ IS NULL AND ";
	private static final String _FINDER_COLUMN_UUID_C_UUID_2_SQL = "serviceAccessPolicy.uuid_ = ? AND ";
	private static final String _FINDER_COLUMN_UUID_C_UUID_3_SQL = "(serviceAccessPolicy.uuid_ IS NULL OR serviceAccessPolicy.uuid_ = '') AND ";
	private static final String _FINDER_COLUMN_UUID_C_COMPANYID_2 = "serviceAccessPolicy.companyId = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_COMPANYID =
		new FinderPath(ServiceAccessPolicyModelImpl.ENTITY_CACHE_ENABLED,
			ServiceAccessPolicyModelImpl.FINDER_CACHE_ENABLED,
			ServiceAccessPolicyImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByCompanyId",
			new String[] {
				Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID =
		new FinderPath(ServiceAccessPolicyModelImpl.ENTITY_CACHE_ENABLED,
			ServiceAccessPolicyModelImpl.FINDER_CACHE_ENABLED,
			ServiceAccessPolicyImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByCompanyId",
			new String[] { Long.class.getName() },
			ServiceAccessPolicyModelImpl.COMPANYID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_COMPANYID = new FinderPath(ServiceAccessPolicyModelImpl.ENTITY_CACHE_ENABLED,
			ServiceAccessPolicyModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByCompanyId",
			new String[] { Long.class.getName() });

	/**
	 * Returns all the service access policies where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching service access policies
	 */
	@Override
	public List<ServiceAccessPolicy> findByCompanyId(long companyId) {
		return findByCompanyId(companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the service access policies where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.service.access.policy.model.impl.ServiceAccessPolicyModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of service access policies
	 * @param end the upper bound of the range of service access policies (not inclusive)
	 * @return the range of matching service access policies
	 */
	@Override
	public List<ServiceAccessPolicy> findByCompanyId(long companyId, int start,
		int end) {
		return findByCompanyId(companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the service access policies where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.service.access.policy.model.impl.ServiceAccessPolicyModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of service access policies
	 * @param end the upper bound of the range of service access policies (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching service access policies
	 */
	@Override
	public List<ServiceAccessPolicy> findByCompanyId(long companyId, int start,
		int end, OrderByComparator<ServiceAccessPolicy> orderByComparator) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID;
			finderArgs = new Object[] { companyId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_COMPANYID;
			finderArgs = new Object[] { companyId, start, end, orderByComparator };
		}

		List<ServiceAccessPolicy> list = (List<ServiceAccessPolicy>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (ServiceAccessPolicy serviceAccessPolicy : list) {
				if ((companyId != serviceAccessPolicy.getCompanyId())) {
					list = null;

					break;
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(3 +
						(orderByComparator.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(3);
			}

			query.append(_SQL_SELECT_SERVICEACCESSPOLICY_WHERE);

			query.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(ServiceAccessPolicyModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				if (!pagination) {
					list = (List<ServiceAccessPolicy>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<ServiceAccessPolicy>)QueryUtil.list(q,
							getDialect(), start, end);
				}

				cacheResult(list);

				FinderCacheUtil.putResult(finderPath, finderArgs, list);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first service access policy in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching service access policy
	 * @throws com.liferay.service.access.policy.NoSuchServiceAccessPolicyException if a matching service access policy could not be found
	 */
	@Override
	public ServiceAccessPolicy findByCompanyId_First(long companyId,
		OrderByComparator<ServiceAccessPolicy> orderByComparator)
		throws NoSuchServiceAccessPolicyException {
		ServiceAccessPolicy serviceAccessPolicy = fetchByCompanyId_First(companyId,
				orderByComparator);

		if (serviceAccessPolicy != null) {
			return serviceAccessPolicy;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("companyId=");
		msg.append(companyId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchServiceAccessPolicyException(msg.toString());
	}

	/**
	 * Returns the first service access policy in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching service access policy, or <code>null</code> if a matching service access policy could not be found
	 */
	@Override
	public ServiceAccessPolicy fetchByCompanyId_First(long companyId,
		OrderByComparator<ServiceAccessPolicy> orderByComparator) {
		List<ServiceAccessPolicy> list = findByCompanyId(companyId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last service access policy in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching service access policy
	 * @throws com.liferay.service.access.policy.NoSuchServiceAccessPolicyException if a matching service access policy could not be found
	 */
	@Override
	public ServiceAccessPolicy findByCompanyId_Last(long companyId,
		OrderByComparator<ServiceAccessPolicy> orderByComparator)
		throws NoSuchServiceAccessPolicyException {
		ServiceAccessPolicy serviceAccessPolicy = fetchByCompanyId_Last(companyId,
				orderByComparator);

		if (serviceAccessPolicy != null) {
			return serviceAccessPolicy;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("companyId=");
		msg.append(companyId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchServiceAccessPolicyException(msg.toString());
	}

	/**
	 * Returns the last service access policy in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching service access policy, or <code>null</code> if a matching service access policy could not be found
	 */
	@Override
	public ServiceAccessPolicy fetchByCompanyId_Last(long companyId,
		OrderByComparator<ServiceAccessPolicy> orderByComparator) {
		int count = countByCompanyId(companyId);

		if (count == 0) {
			return null;
		}

		List<ServiceAccessPolicy> list = findByCompanyId(companyId, count - 1,
				count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the service access policies before and after the current service access policy in the ordered set where companyId = &#63;.
	 *
	 * @param serviceAccessPolicyId the primary key of the current service access policy
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next service access policy
	 * @throws com.liferay.service.access.policy.NoSuchServiceAccessPolicyException if a service access policy with the primary key could not be found
	 */
	@Override
	public ServiceAccessPolicy[] findByCompanyId_PrevAndNext(
		long serviceAccessPolicyId, long companyId,
		OrderByComparator<ServiceAccessPolicy> orderByComparator)
		throws NoSuchServiceAccessPolicyException {
		ServiceAccessPolicy serviceAccessPolicy = findByPrimaryKey(serviceAccessPolicyId);

		Session session = null;

		try {
			session = openSession();

			ServiceAccessPolicy[] array = new ServiceAccessPolicyImpl[3];

			array[0] = getByCompanyId_PrevAndNext(session, serviceAccessPolicy,
					companyId, orderByComparator, true);

			array[1] = serviceAccessPolicy;

			array[2] = getByCompanyId_PrevAndNext(session, serviceAccessPolicy,
					companyId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected ServiceAccessPolicy getByCompanyId_PrevAndNext(Session session,
		ServiceAccessPolicy serviceAccessPolicy, long companyId,
		OrderByComparator<ServiceAccessPolicy> orderByComparator,
		boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_SERVICEACCESSPOLICY_WHERE);

		query.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

		if (orderByComparator != null) {
			String[] orderByConditionFields = orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				query.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByConditionFields[i]);

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						query.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN);
					}
					else {
						query.append(WHERE_LESSER_THAN);
					}
				}
			}

			query.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						query.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC);
					}
					else {
						query.append(ORDER_BY_DESC);
					}
				}
			}
		}
		else {
			query.append(ServiceAccessPolicyModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(companyId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(serviceAccessPolicy);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<ServiceAccessPolicy> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the service access policies that the user has permission to view where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching service access policies that the user has permission to view
	 */
	@Override
	public List<ServiceAccessPolicy> filterFindByCompanyId(long companyId) {
		return filterFindByCompanyId(companyId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the service access policies that the user has permission to view where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.service.access.policy.model.impl.ServiceAccessPolicyModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of service access policies
	 * @param end the upper bound of the range of service access policies (not inclusive)
	 * @return the range of matching service access policies that the user has permission to view
	 */
	@Override
	public List<ServiceAccessPolicy> filterFindByCompanyId(long companyId,
		int start, int end) {
		return filterFindByCompanyId(companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the service access policies that the user has permissions to view where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.service.access.policy.model.impl.ServiceAccessPolicyModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of service access policies
	 * @param end the upper bound of the range of service access policies (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching service access policies that the user has permission to view
	 */
	@Override
	public List<ServiceAccessPolicy> filterFindByCompanyId(long companyId,
		int start, int end,
		OrderByComparator<ServiceAccessPolicy> orderByComparator) {
		if (!InlineSQLHelperUtil.isEnabled()) {
			return findByCompanyId(companyId, start, end, orderByComparator);
		}

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(3 +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(3);
		}

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_SERVICEACCESSPOLICY_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_SERVICEACCESSPOLICY_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_SERVICEACCESSPOLICY_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			if (getDB().isSupportsInlineDistinct()) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator, true);
			}
			else {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_TABLE,
					orderByComparator, true);
			}
		}
		else {
			if (getDB().isSupportsInlineDistinct()) {
				query.append(ServiceAccessPolicyModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(ServiceAccessPolicyModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				ServiceAccessPolicy.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				q.addEntity(_FILTER_ENTITY_ALIAS, ServiceAccessPolicyImpl.class);
			}
			else {
				q.addEntity(_FILTER_ENTITY_TABLE, ServiceAccessPolicyImpl.class);
			}

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(companyId);

			return (List<ServiceAccessPolicy>)QueryUtil.list(q, getDialect(),
				start, end);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Returns the service access policies before and after the current service access policy in the ordered set of service access policies that the user has permission to view where companyId = &#63;.
	 *
	 * @param serviceAccessPolicyId the primary key of the current service access policy
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next service access policy
	 * @throws com.liferay.service.access.policy.NoSuchServiceAccessPolicyException if a service access policy with the primary key could not be found
	 */
	@Override
	public ServiceAccessPolicy[] filterFindByCompanyId_PrevAndNext(
		long serviceAccessPolicyId, long companyId,
		OrderByComparator<ServiceAccessPolicy> orderByComparator)
		throws NoSuchServiceAccessPolicyException {
		if (!InlineSQLHelperUtil.isEnabled()) {
			return findByCompanyId_PrevAndNext(serviceAccessPolicyId,
				companyId, orderByComparator);
		}

		ServiceAccessPolicy serviceAccessPolicy = findByPrimaryKey(serviceAccessPolicyId);

		Session session = null;

		try {
			session = openSession();

			ServiceAccessPolicy[] array = new ServiceAccessPolicyImpl[3];

			array[0] = filterGetByCompanyId_PrevAndNext(session,
					serviceAccessPolicy, companyId, orderByComparator, true);

			array[1] = serviceAccessPolicy;

			array[2] = filterGetByCompanyId_PrevAndNext(session,
					serviceAccessPolicy, companyId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected ServiceAccessPolicy filterGetByCompanyId_PrevAndNext(
		Session session, ServiceAccessPolicy serviceAccessPolicy,
		long companyId,
		OrderByComparator<ServiceAccessPolicy> orderByComparator,
		boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_SERVICEACCESSPOLICY_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_SERVICEACCESSPOLICY_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_SERVICEACCESSPOLICY_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			String[] orderByConditionFields = orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				query.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				if (getDB().isSupportsInlineDistinct()) {
					query.append(_ORDER_BY_ENTITY_ALIAS);
				}
				else {
					query.append(_ORDER_BY_ENTITY_TABLE);
				}

				query.append(orderByConditionFields[i]);

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						query.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN);
					}
					else {
						query.append(WHERE_LESSER_THAN);
					}
				}
			}

			query.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				if (getDB().isSupportsInlineDistinct()) {
					query.append(_ORDER_BY_ENTITY_ALIAS);
				}
				else {
					query.append(_ORDER_BY_ENTITY_TABLE);
				}

				query.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						query.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC);
					}
					else {
						query.append(ORDER_BY_DESC);
					}
				}
			}
		}
		else {
			if (getDB().isSupportsInlineDistinct()) {
				query.append(ServiceAccessPolicyModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(ServiceAccessPolicyModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				ServiceAccessPolicy.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		SQLQuery q = session.createSynchronizedSQLQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		if (getDB().isSupportsInlineDistinct()) {
			q.addEntity(_FILTER_ENTITY_ALIAS, ServiceAccessPolicyImpl.class);
		}
		else {
			q.addEntity(_FILTER_ENTITY_TABLE, ServiceAccessPolicyImpl.class);
		}

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(companyId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(serviceAccessPolicy);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<ServiceAccessPolicy> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the service access policies where companyId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 */
	@Override
	public void removeByCompanyId(long companyId) {
		for (ServiceAccessPolicy serviceAccessPolicy : findByCompanyId(
				companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(serviceAccessPolicy);
		}
	}

	/**
	 * Returns the number of service access policies where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching service access policies
	 */
	@Override
	public int countByCompanyId(long companyId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_COMPANYID;

		Object[] finderArgs = new Object[] { companyId };

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs,
				this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_SERVICEACCESSPOLICY_WHERE);

			query.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				count = (Long)q.uniqueResult();

				FinderCacheUtil.putResult(finderPath, finderArgs, count);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of service access policies that the user has permission to view where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching service access policies that the user has permission to view
	 */
	@Override
	public int filterCountByCompanyId(long companyId) {
		if (!InlineSQLHelperUtil.isEnabled()) {
			return countByCompanyId(companyId);
		}

		StringBundler query = new StringBundler(2);

		query.append(_FILTER_SQL_COUNT_SERVICEACCESSPOLICY_WHERE);

		query.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				ServiceAccessPolicy.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME,
				com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(companyId);

			Long count = (Long)q.uniqueResult();

			return count.intValue();
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	private static final String _FINDER_COLUMN_COMPANYID_COMPANYID_2 = "serviceAccessPolicy.companyId = ?";
	public static final FinderPath FINDER_PATH_FETCH_BY_C_N = new FinderPath(ServiceAccessPolicyModelImpl.ENTITY_CACHE_ENABLED,
			ServiceAccessPolicyModelImpl.FINDER_CACHE_ENABLED,
			ServiceAccessPolicyImpl.class, FINDER_CLASS_NAME_ENTITY,
			"fetchByC_N",
			new String[] { Long.class.getName(), String.class.getName() },
			ServiceAccessPolicyModelImpl.COMPANYID_COLUMN_BITMASK |
			ServiceAccessPolicyModelImpl.NAME_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_C_N = new FinderPath(ServiceAccessPolicyModelImpl.ENTITY_CACHE_ENABLED,
			ServiceAccessPolicyModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_N",
			new String[] { Long.class.getName(), String.class.getName() });

	/**
	 * Returns the service access policy where companyId = &#63; and name = &#63; or throws a {@link com.liferay.service.access.policy.NoSuchServiceAccessPolicyException} if it could not be found.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @return the matching service access policy
	 * @throws com.liferay.service.access.policy.NoSuchServiceAccessPolicyException if a matching service access policy could not be found
	 */
	@Override
	public ServiceAccessPolicy findByC_N(long companyId, String name)
		throws NoSuchServiceAccessPolicyException {
		ServiceAccessPolicy serviceAccessPolicy = fetchByC_N(companyId, name);

		if (serviceAccessPolicy == null) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("companyId=");
			msg.append(companyId);

			msg.append(", name=");
			msg.append(name);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchServiceAccessPolicyException(msg.toString());
		}

		return serviceAccessPolicy;
	}

	/**
	 * Returns the service access policy where companyId = &#63; and name = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @return the matching service access policy, or <code>null</code> if a matching service access policy could not be found
	 */
	@Override
	public ServiceAccessPolicy fetchByC_N(long companyId, String name) {
		return fetchByC_N(companyId, name, true);
	}

	/**
	 * Returns the service access policy where companyId = &#63; and name = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @param retrieveFromCache whether to use the finder cache
	 * @return the matching service access policy, or <code>null</code> if a matching service access policy could not be found
	 */
	@Override
	public ServiceAccessPolicy fetchByC_N(long companyId, String name,
		boolean retrieveFromCache) {
		Object[] finderArgs = new Object[] { companyId, name };

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_C_N,
					finderArgs, this);
		}

		if (result instanceof ServiceAccessPolicy) {
			ServiceAccessPolicy serviceAccessPolicy = (ServiceAccessPolicy)result;

			if ((companyId != serviceAccessPolicy.getCompanyId()) ||
					!Validator.equals(name, serviceAccessPolicy.getName())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_SELECT_SERVICEACCESSPOLICY_WHERE);

			query.append(_FINDER_COLUMN_C_N_COMPANYID_2);

			boolean bindName = false;

			if (name == null) {
				query.append(_FINDER_COLUMN_C_N_NAME_1);
			}
			else if (name.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_C_N_NAME_3);
			}
			else {
				bindName = true;

				query.append(_FINDER_COLUMN_C_N_NAME_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				if (bindName) {
					qPos.add(StringUtil.toLowerCase(name));
				}

				List<ServiceAccessPolicy> list = q.list();

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_N,
						finderArgs, list);
				}
				else {
					if ((list.size() > 1) && _log.isWarnEnabled()) {
						_log.warn(
							"ServiceAccessPolicyPersistenceImpl.fetchByC_N(long, String, boolean) with parameters (" +
							StringUtil.merge(finderArgs) +
							") yields a result set with more than 1 result. This violates the logical unique restriction. There is no order guarantee on which result is returned by this finder.");
					}

					ServiceAccessPolicy serviceAccessPolicy = list.get(0);

					result = serviceAccessPolicy;

					cacheResult(serviceAccessPolicy);

					if ((serviceAccessPolicy.getCompanyId() != companyId) ||
							(serviceAccessPolicy.getName() == null) ||
							!serviceAccessPolicy.getName().equals(name)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_N,
							finderArgs, serviceAccessPolicy);
					}
				}
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_C_N,
					finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		if (result instanceof List<?>) {
			return null;
		}
		else {
			return (ServiceAccessPolicy)result;
		}
	}

	/**
	 * Removes the service access policy where companyId = &#63; and name = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @return the service access policy that was removed
	 */
	@Override
	public ServiceAccessPolicy removeByC_N(long companyId, String name)
		throws NoSuchServiceAccessPolicyException {
		ServiceAccessPolicy serviceAccessPolicy = findByC_N(companyId, name);

		return remove(serviceAccessPolicy);
	}

	/**
	 * Returns the number of service access policies where companyId = &#63; and name = &#63;.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @return the number of matching service access policies
	 */
	@Override
	public int countByC_N(long companyId, String name) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_C_N;

		Object[] finderArgs = new Object[] { companyId, name };

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs,
				this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_SERVICEACCESSPOLICY_WHERE);

			query.append(_FINDER_COLUMN_C_N_COMPANYID_2);

			boolean bindName = false;

			if (name == null) {
				query.append(_FINDER_COLUMN_C_N_NAME_1);
			}
			else if (name.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_C_N_NAME_3);
			}
			else {
				bindName = true;

				query.append(_FINDER_COLUMN_C_N_NAME_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				if (bindName) {
					qPos.add(StringUtil.toLowerCase(name));
				}

				count = (Long)q.uniqueResult();

				FinderCacheUtil.putResult(finderPath, finderArgs, count);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_C_N_COMPANYID_2 = "serviceAccessPolicy.companyId = ? AND ";
	private static final String _FINDER_COLUMN_C_N_NAME_1 = "serviceAccessPolicy.name IS NULL";
	private static final String _FINDER_COLUMN_C_N_NAME_2 = "lower(serviceAccessPolicy.name) = ?";
	private static final String _FINDER_COLUMN_C_N_NAME_3 = "(serviceAccessPolicy.name IS NULL OR serviceAccessPolicy.name = '')";

	public ServiceAccessPolicyPersistenceImpl() {
		setModelClass(ServiceAccessPolicy.class);
	}

	/**
	 * Caches the service access policy in the entity cache if it is enabled.
	 *
	 * @param serviceAccessPolicy the service access policy
	 */
	@Override
	public void cacheResult(ServiceAccessPolicy serviceAccessPolicy) {
		EntityCacheUtil.putResult(ServiceAccessPolicyModelImpl.ENTITY_CACHE_ENABLED,
			ServiceAccessPolicyImpl.class, serviceAccessPolicy.getPrimaryKey(),
			serviceAccessPolicy);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_N,
			new Object[] {
				serviceAccessPolicy.getCompanyId(),
				serviceAccessPolicy.getName()
			}, serviceAccessPolicy);

		serviceAccessPolicy.resetOriginalValues();
	}

	/**
	 * Caches the service access policies in the entity cache if it is enabled.
	 *
	 * @param serviceAccessPolicies the service access policies
	 */
	@Override
	public void cacheResult(List<ServiceAccessPolicy> serviceAccessPolicies) {
		for (ServiceAccessPolicy serviceAccessPolicy : serviceAccessPolicies) {
			if (EntityCacheUtil.getResult(
						ServiceAccessPolicyModelImpl.ENTITY_CACHE_ENABLED,
						ServiceAccessPolicyImpl.class,
						serviceAccessPolicy.getPrimaryKey()) == null) {
				cacheResult(serviceAccessPolicy);
			}
			else {
				serviceAccessPolicy.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all service access policies.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		EntityCacheUtil.clearCache(ServiceAccessPolicyImpl.class);

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the service access policy.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(ServiceAccessPolicy serviceAccessPolicy) {
		EntityCacheUtil.removeResult(ServiceAccessPolicyModelImpl.ENTITY_CACHE_ENABLED,
			ServiceAccessPolicyImpl.class, serviceAccessPolicy.getPrimaryKey());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache(serviceAccessPolicy);
	}

	@Override
	public void clearCache(List<ServiceAccessPolicy> serviceAccessPolicies) {
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (ServiceAccessPolicy serviceAccessPolicy : serviceAccessPolicies) {
			EntityCacheUtil.removeResult(ServiceAccessPolicyModelImpl.ENTITY_CACHE_ENABLED,
				ServiceAccessPolicyImpl.class,
				serviceAccessPolicy.getPrimaryKey());

			clearUniqueFindersCache(serviceAccessPolicy);
		}
	}

	protected void cacheUniqueFindersCache(
		ServiceAccessPolicy serviceAccessPolicy, boolean isNew) {
		if (isNew) {
			Object[] args = new Object[] {
					serviceAccessPolicy.getCompanyId(),
					serviceAccessPolicy.getName()
				};

			FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_C_N, args,
				Long.valueOf(1));
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_N, args,
				serviceAccessPolicy);
		}
		else {
			ServiceAccessPolicyModelImpl serviceAccessPolicyModelImpl = (ServiceAccessPolicyModelImpl)serviceAccessPolicy;

			if ((serviceAccessPolicyModelImpl.getColumnBitmask() &
					FINDER_PATH_FETCH_BY_C_N.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						serviceAccessPolicy.getCompanyId(),
						serviceAccessPolicy.getName()
					};

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_C_N, args,
					Long.valueOf(1));
				FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_N, args,
					serviceAccessPolicy);
			}
		}
	}

	protected void clearUniqueFindersCache(
		ServiceAccessPolicy serviceAccessPolicy) {
		ServiceAccessPolicyModelImpl serviceAccessPolicyModelImpl = (ServiceAccessPolicyModelImpl)serviceAccessPolicy;

		Object[] args = new Object[] {
				serviceAccessPolicy.getCompanyId(),
				serviceAccessPolicy.getName()
			};

		FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_C_N, args);
		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_C_N, args);

		if ((serviceAccessPolicyModelImpl.getColumnBitmask() &
				FINDER_PATH_FETCH_BY_C_N.getColumnBitmask()) != 0) {
			args = new Object[] {
					serviceAccessPolicyModelImpl.getOriginalCompanyId(),
					serviceAccessPolicyModelImpl.getOriginalName()
				};

			FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_C_N, args);
			FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_C_N, args);
		}
	}

	/**
	 * Creates a new service access policy with the primary key. Does not add the service access policy to the database.
	 *
	 * @param serviceAccessPolicyId the primary key for the new service access policy
	 * @return the new service access policy
	 */
	@Override
	public ServiceAccessPolicy create(long serviceAccessPolicyId) {
		ServiceAccessPolicy serviceAccessPolicy = new ServiceAccessPolicyImpl();

		serviceAccessPolicy.setNew(true);
		serviceAccessPolicy.setPrimaryKey(serviceAccessPolicyId);

		String uuid = PortalUUIDUtil.generate();

		serviceAccessPolicy.setUuid(uuid);

		return serviceAccessPolicy;
	}

	/**
	 * Removes the service access policy with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param serviceAccessPolicyId the primary key of the service access policy
	 * @return the service access policy that was removed
	 * @throws com.liferay.service.access.policy.NoSuchServiceAccessPolicyException if a service access policy with the primary key could not be found
	 */
	@Override
	public ServiceAccessPolicy remove(long serviceAccessPolicyId)
		throws NoSuchServiceAccessPolicyException {
		return remove((Serializable)serviceAccessPolicyId);
	}

	/**
	 * Removes the service access policy with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the service access policy
	 * @return the service access policy that was removed
	 * @throws com.liferay.service.access.policy.NoSuchServiceAccessPolicyException if a service access policy with the primary key could not be found
	 */
	@Override
	public ServiceAccessPolicy remove(Serializable primaryKey)
		throws NoSuchServiceAccessPolicyException {
		Session session = null;

		try {
			session = openSession();

			ServiceAccessPolicy serviceAccessPolicy = (ServiceAccessPolicy)session.get(ServiceAccessPolicyImpl.class,
					primaryKey);

			if (serviceAccessPolicy == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchServiceAccessPolicyException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(serviceAccessPolicy);
		}
		catch (NoSuchServiceAccessPolicyException nsee) {
			throw nsee;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	@Override
	protected ServiceAccessPolicy removeImpl(
		ServiceAccessPolicy serviceAccessPolicy) {
		serviceAccessPolicy = toUnwrappedModel(serviceAccessPolicy);

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(serviceAccessPolicy)) {
				serviceAccessPolicy = (ServiceAccessPolicy)session.get(ServiceAccessPolicyImpl.class,
						serviceAccessPolicy.getPrimaryKeyObj());
			}

			if (serviceAccessPolicy != null) {
				session.delete(serviceAccessPolicy);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (serviceAccessPolicy != null) {
			clearCache(serviceAccessPolicy);
		}

		return serviceAccessPolicy;
	}

	@Override
	public ServiceAccessPolicy updateImpl(
		com.liferay.service.access.policy.model.ServiceAccessPolicy serviceAccessPolicy) {
		serviceAccessPolicy = toUnwrappedModel(serviceAccessPolicy);

		boolean isNew = serviceAccessPolicy.isNew();

		ServiceAccessPolicyModelImpl serviceAccessPolicyModelImpl = (ServiceAccessPolicyModelImpl)serviceAccessPolicy;

		if (Validator.isNull(serviceAccessPolicy.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			serviceAccessPolicy.setUuid(uuid);
		}

		ServiceContext serviceContext = ServiceContextThreadLocal.getServiceContext();

		Date now = new Date();

		if (isNew && (serviceAccessPolicy.getCreateDate() == null)) {
			if (serviceContext == null) {
				serviceAccessPolicy.setCreateDate(now);
			}
			else {
				serviceAccessPolicy.setCreateDate(serviceContext.getCreateDate(
						now));
			}
		}

		if (!serviceAccessPolicyModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				serviceAccessPolicy.setModifiedDate(now);
			}
			else {
				serviceAccessPolicy.setModifiedDate(serviceContext.getModifiedDate(
						now));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (serviceAccessPolicy.isNew()) {
				session.save(serviceAccessPolicy);

				serviceAccessPolicy.setNew(false);
			}
			else {
				session.merge(serviceAccessPolicy);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (isNew || !ServiceAccessPolicyModelImpl.COLUMN_BITMASK_ENABLED) {
			FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}

		else {
			if ((serviceAccessPolicyModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						serviceAccessPolicyModelImpl.getOriginalUuid()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_UUID, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID,
					args);

				args = new Object[] { serviceAccessPolicyModelImpl.getUuid() };

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_UUID, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID,
					args);
			}

			if ((serviceAccessPolicyModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						serviceAccessPolicyModelImpl.getOriginalUuid(),
						serviceAccessPolicyModelImpl.getOriginalCompanyId()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_UUID_C, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C,
					args);

				args = new Object[] {
						serviceAccessPolicyModelImpl.getUuid(),
						serviceAccessPolicyModelImpl.getCompanyId()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_UUID_C, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C,
					args);
			}

			if ((serviceAccessPolicyModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						serviceAccessPolicyModelImpl.getOriginalCompanyId()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_COMPANYID,
					args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID,
					args);

				args = new Object[] { serviceAccessPolicyModelImpl.getCompanyId() };

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_COMPANYID,
					args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID,
					args);
			}
		}

		EntityCacheUtil.putResult(ServiceAccessPolicyModelImpl.ENTITY_CACHE_ENABLED,
			ServiceAccessPolicyImpl.class, serviceAccessPolicy.getPrimaryKey(),
			serviceAccessPolicy, false);

		clearUniqueFindersCache(serviceAccessPolicy);
		cacheUniqueFindersCache(serviceAccessPolicy, isNew);

		serviceAccessPolicy.resetOriginalValues();

		return serviceAccessPolicy;
	}

	protected ServiceAccessPolicy toUnwrappedModel(
		ServiceAccessPolicy serviceAccessPolicy) {
		if (serviceAccessPolicy instanceof ServiceAccessPolicyImpl) {
			return serviceAccessPolicy;
		}

		ServiceAccessPolicyImpl serviceAccessPolicyImpl = new ServiceAccessPolicyImpl();

		serviceAccessPolicyImpl.setNew(serviceAccessPolicy.isNew());
		serviceAccessPolicyImpl.setPrimaryKey(serviceAccessPolicy.getPrimaryKey());

		serviceAccessPolicyImpl.setUuid(serviceAccessPolicy.getUuid());
		serviceAccessPolicyImpl.setServiceAccessPolicyId(serviceAccessPolicy.getServiceAccessPolicyId());
		serviceAccessPolicyImpl.setCompanyId(serviceAccessPolicy.getCompanyId());
		serviceAccessPolicyImpl.setUserId(serviceAccessPolicy.getUserId());
		serviceAccessPolicyImpl.setUserName(serviceAccessPolicy.getUserName());
		serviceAccessPolicyImpl.setCreateDate(serviceAccessPolicy.getCreateDate());
		serviceAccessPolicyImpl.setModifiedDate(serviceAccessPolicy.getModifiedDate());
		serviceAccessPolicyImpl.setAllowedServiceSignatures(serviceAccessPolicy.getAllowedServiceSignatures());
		serviceAccessPolicyImpl.setDefaultServiceAccessPolicy(serviceAccessPolicy.isDefaultServiceAccessPolicy());
		serviceAccessPolicyImpl.setName(serviceAccessPolicy.getName());
		serviceAccessPolicyImpl.setTitle(serviceAccessPolicy.getTitle());

		return serviceAccessPolicyImpl;
	}

	/**
	 * Returns the service access policy with the primary key or throws a {@link com.liferay.portal.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the service access policy
	 * @return the service access policy
	 * @throws com.liferay.service.access.policy.NoSuchServiceAccessPolicyException if a service access policy with the primary key could not be found
	 */
	@Override
	public ServiceAccessPolicy findByPrimaryKey(Serializable primaryKey)
		throws NoSuchServiceAccessPolicyException {
		ServiceAccessPolicy serviceAccessPolicy = fetchByPrimaryKey(primaryKey);

		if (serviceAccessPolicy == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchServiceAccessPolicyException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				primaryKey);
		}

		return serviceAccessPolicy;
	}

	/**
	 * Returns the service access policy with the primary key or throws a {@link com.liferay.service.access.policy.NoSuchServiceAccessPolicyException} if it could not be found.
	 *
	 * @param serviceAccessPolicyId the primary key of the service access policy
	 * @return the service access policy
	 * @throws com.liferay.service.access.policy.NoSuchServiceAccessPolicyException if a service access policy with the primary key could not be found
	 */
	@Override
	public ServiceAccessPolicy findByPrimaryKey(long serviceAccessPolicyId)
		throws NoSuchServiceAccessPolicyException {
		return findByPrimaryKey((Serializable)serviceAccessPolicyId);
	}

	/**
	 * Returns the service access policy with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the service access policy
	 * @return the service access policy, or <code>null</code> if a service access policy with the primary key could not be found
	 */
	@Override
	public ServiceAccessPolicy fetchByPrimaryKey(Serializable primaryKey) {
		ServiceAccessPolicy serviceAccessPolicy = (ServiceAccessPolicy)EntityCacheUtil.getResult(ServiceAccessPolicyModelImpl.ENTITY_CACHE_ENABLED,
				ServiceAccessPolicyImpl.class, primaryKey);

		if (serviceAccessPolicy == _nullServiceAccessPolicy) {
			return null;
		}

		if (serviceAccessPolicy == null) {
			Session session = null;

			try {
				session = openSession();

				serviceAccessPolicy = (ServiceAccessPolicy)session.get(ServiceAccessPolicyImpl.class,
						primaryKey);

				if (serviceAccessPolicy != null) {
					cacheResult(serviceAccessPolicy);
				}
				else {
					EntityCacheUtil.putResult(ServiceAccessPolicyModelImpl.ENTITY_CACHE_ENABLED,
						ServiceAccessPolicyImpl.class, primaryKey,
						_nullServiceAccessPolicy);
				}
			}
			catch (Exception e) {
				EntityCacheUtil.removeResult(ServiceAccessPolicyModelImpl.ENTITY_CACHE_ENABLED,
					ServiceAccessPolicyImpl.class, primaryKey);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return serviceAccessPolicy;
	}

	/**
	 * Returns the service access policy with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param serviceAccessPolicyId the primary key of the service access policy
	 * @return the service access policy, or <code>null</code> if a service access policy with the primary key could not be found
	 */
	@Override
	public ServiceAccessPolicy fetchByPrimaryKey(long serviceAccessPolicyId) {
		return fetchByPrimaryKey((Serializable)serviceAccessPolicyId);
	}

	@Override
	public Map<Serializable, ServiceAccessPolicy> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {
		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, ServiceAccessPolicy> map = new HashMap<Serializable, ServiceAccessPolicy>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			ServiceAccessPolicy serviceAccessPolicy = fetchByPrimaryKey(primaryKey);

			if (serviceAccessPolicy != null) {
				map.put(primaryKey, serviceAccessPolicy);
			}

			return map;
		}

		Set<Serializable> uncachedPrimaryKeys = null;

		for (Serializable primaryKey : primaryKeys) {
			ServiceAccessPolicy serviceAccessPolicy = (ServiceAccessPolicy)EntityCacheUtil.getResult(ServiceAccessPolicyModelImpl.ENTITY_CACHE_ENABLED,
					ServiceAccessPolicyImpl.class, primaryKey);

			if (serviceAccessPolicy == null) {
				if (uncachedPrimaryKeys == null) {
					uncachedPrimaryKeys = new HashSet<Serializable>();
				}

				uncachedPrimaryKeys.add(primaryKey);
			}
			else {
				map.put(primaryKey, serviceAccessPolicy);
			}
		}

		if (uncachedPrimaryKeys == null) {
			return map;
		}

		StringBundler query = new StringBundler((uncachedPrimaryKeys.size() * 2) +
				1);

		query.append(_SQL_SELECT_SERVICEACCESSPOLICY_WHERE_PKS_IN);

		for (Serializable primaryKey : uncachedPrimaryKeys) {
			query.append(String.valueOf(primaryKey));

			query.append(StringPool.COMMA);
		}

		query.setIndex(query.index() - 1);

		query.append(StringPool.CLOSE_PARENTHESIS);

		String sql = query.toString();

		Session session = null;

		try {
			session = openSession();

			Query q = session.createQuery(sql);

			for (ServiceAccessPolicy serviceAccessPolicy : (List<ServiceAccessPolicy>)q.list()) {
				map.put(serviceAccessPolicy.getPrimaryKeyObj(),
					serviceAccessPolicy);

				cacheResult(serviceAccessPolicy);

				uncachedPrimaryKeys.remove(serviceAccessPolicy.getPrimaryKeyObj());
			}

			for (Serializable primaryKey : uncachedPrimaryKeys) {
				EntityCacheUtil.putResult(ServiceAccessPolicyModelImpl.ENTITY_CACHE_ENABLED,
					ServiceAccessPolicyImpl.class, primaryKey,
					_nullServiceAccessPolicy);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		return map;
	}

	/**
	 * Returns all the service access policies.
	 *
	 * @return the service access policies
	 */
	@Override
	public List<ServiceAccessPolicy> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the service access policies.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.service.access.policy.model.impl.ServiceAccessPolicyModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of service access policies
	 * @param end the upper bound of the range of service access policies (not inclusive)
	 * @return the range of service access policies
	 */
	@Override
	public List<ServiceAccessPolicy> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the service access policies.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.service.access.policy.model.impl.ServiceAccessPolicyModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of service access policies
	 * @param end the upper bound of the range of service access policies (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of service access policies
	 */
	@Override
	public List<ServiceAccessPolicy> findAll(int start, int end,
		OrderByComparator<ServiceAccessPolicy> orderByComparator) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL;
			finderArgs = FINDER_ARGS_EMPTY;
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_ALL;
			finderArgs = new Object[] { start, end, orderByComparator };
		}

		List<ServiceAccessPolicy> list = (List<ServiceAccessPolicy>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 3));

				query.append(_SQL_SELECT_SERVICEACCESSPOLICY);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_SERVICEACCESSPOLICY;

				if (pagination) {
					sql = sql.concat(ServiceAccessPolicyModelImpl.ORDER_BY_JPQL);
				}
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (!pagination) {
					list = (List<ServiceAccessPolicy>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<ServiceAccessPolicy>)QueryUtil.list(q,
							getDialect(), start, end);
				}

				cacheResult(list);

				FinderCacheUtil.putResult(finderPath, finderArgs, list);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Removes all the service access policies from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (ServiceAccessPolicy serviceAccessPolicy : findAll()) {
			remove(serviceAccessPolicy);
		}
	}

	/**
	 * Returns the number of service access policies.
	 *
	 * @return the number of service access policies
	 */
	@Override
	public int countAll() {
		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_SERVICEACCESSPOLICY);

				count = (Long)q.uniqueResult();

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_ALL,
					FINDER_ARGS_EMPTY, count);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_ALL,
					FINDER_ARGS_EMPTY);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	@Override
	protected Set<String> getBadColumnNames() {
		return _badColumnNames;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return ServiceAccessPolicyModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the service access policy persistence.
	 */
	public void afterPropertiesSet() {
	}

	public void destroy() {
		EntityCacheUtil.removeCache(ServiceAccessPolicyImpl.class.getName());
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	private static final String _SQL_SELECT_SERVICEACCESSPOLICY = "SELECT serviceAccessPolicy FROM ServiceAccessPolicy serviceAccessPolicy";
	private static final String _SQL_SELECT_SERVICEACCESSPOLICY_WHERE_PKS_IN = "SELECT serviceAccessPolicy FROM ServiceAccessPolicy serviceAccessPolicy WHERE serviceAccessPolicyId IN (";
	private static final String _SQL_SELECT_SERVICEACCESSPOLICY_WHERE = "SELECT serviceAccessPolicy FROM ServiceAccessPolicy serviceAccessPolicy WHERE ";
	private static final String _SQL_COUNT_SERVICEACCESSPOLICY = "SELECT COUNT(serviceAccessPolicy) FROM ServiceAccessPolicy serviceAccessPolicy";
	private static final String _SQL_COUNT_SERVICEACCESSPOLICY_WHERE = "SELECT COUNT(serviceAccessPolicy) FROM ServiceAccessPolicy serviceAccessPolicy WHERE ";
	private static final String _FILTER_ENTITY_TABLE_FILTER_PK_COLUMN = "serviceAccessPolicy.serviceAccessPolicyId";
	private static final String _FILTER_SQL_SELECT_SERVICEACCESSPOLICY_WHERE = "SELECT DISTINCT {serviceAccessPolicy.*} FROM ServiceAccessPolicy serviceAccessPolicy WHERE ";
	private static final String _FILTER_SQL_SELECT_SERVICEACCESSPOLICY_NO_INLINE_DISTINCT_WHERE_1 =
		"SELECT {ServiceAccessPolicy.*} FROM (SELECT DISTINCT serviceAccessPolicy.serviceAccessPolicyId FROM ServiceAccessPolicy serviceAccessPolicy WHERE ";
	private static final String _FILTER_SQL_SELECT_SERVICEACCESSPOLICY_NO_INLINE_DISTINCT_WHERE_2 =
		") TEMP_TABLE INNER JOIN ServiceAccessPolicy ON TEMP_TABLE.serviceAccessPolicyId = ServiceAccessPolicy.serviceAccessPolicyId";
	private static final String _FILTER_SQL_COUNT_SERVICEACCESSPOLICY_WHERE = "SELECT COUNT(DISTINCT serviceAccessPolicy.serviceAccessPolicyId) AS COUNT_VALUE FROM ServiceAccessPolicy serviceAccessPolicy WHERE ";
	private static final String _FILTER_ENTITY_ALIAS = "serviceAccessPolicy";
	private static final String _FILTER_ENTITY_TABLE = "ServiceAccessPolicy";
	private static final String _ORDER_BY_ENTITY_ALIAS = "serviceAccessPolicy.";
	private static final String _ORDER_BY_ENTITY_TABLE = "ServiceAccessPolicy.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No ServiceAccessPolicy exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No ServiceAccessPolicy exists with the key {";
	private static final Log _log = LogFactoryUtil.getLog(ServiceAccessPolicyPersistenceImpl.class);
	private static final Set<String> _badColumnNames = SetUtil.fromArray(new String[] {
				"uuid"
			});
	private static final ServiceAccessPolicy _nullServiceAccessPolicy = new ServiceAccessPolicyImpl() {
			@Override
			public Object clone() {
				return this;
			}

			@Override
			public CacheModel<ServiceAccessPolicy> toCacheModel() {
				return _nullServiceAccessPolicyCacheModel;
			}
		};

	private static final CacheModel<ServiceAccessPolicy> _nullServiceAccessPolicyCacheModel =
		new CacheModel<ServiceAccessPolicy>() {
			@Override
			public ServiceAccessPolicy toEntityModel() {
				return _nullServiceAccessPolicy;
			}
		};
}