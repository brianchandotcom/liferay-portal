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

package com.liferay.commerce.product.service.persistence.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.commerce.product.exception.NoSuchProductInstanceException;
import com.liferay.commerce.product.model.CommerceProductInstance;
import com.liferay.commerce.product.model.impl.CommerceProductInstanceImpl;
import com.liferay.commerce.product.model.impl.CommerceProductInstanceModelImpl;
import com.liferay.commerce.product.service.persistence.CommerceProductInstancePersistence;

import com.liferay.portal.kernel.dao.orm.EntityCache;
import com.liferay.portal.kernel.dao.orm.FinderCache;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.service.persistence.CompanyProvider;
import com.liferay.portal.kernel.service.persistence.CompanyProviderWrapper;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;
import com.liferay.portal.spring.extender.service.ServiceReference;

import java.io.Serializable;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * The persistence implementation for the commerce product instance service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Marco Leo
 * @see CommerceProductInstancePersistence
 * @see com.liferay.commerce.product.service.persistence.CommerceProductInstanceUtil
 * @generated
 */
@ProviderType
public class CommerceProductInstancePersistenceImpl extends BasePersistenceImpl<CommerceProductInstance>
	implements CommerceProductInstancePersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link CommerceProductInstanceUtil} to access the commerce product instance persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = CommerceProductInstanceImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(CommerceProductInstanceModelImpl.ENTITY_CACHE_ENABLED,
			CommerceProductInstanceModelImpl.FINDER_CACHE_ENABLED,
			CommerceProductInstanceImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(CommerceProductInstanceModelImpl.ENTITY_CACHE_ENABLED,
			CommerceProductInstanceModelImpl.FINDER_CACHE_ENABLED,
			CommerceProductInstanceImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(CommerceProductInstanceModelImpl.ENTITY_CACHE_ENABLED,
			CommerceProductInstanceModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_UUID = new FinderPath(CommerceProductInstanceModelImpl.ENTITY_CACHE_ENABLED,
			CommerceProductInstanceModelImpl.FINDER_CACHE_ENABLED,
			CommerceProductInstanceImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUuid",
			new String[] {
				String.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID = new FinderPath(CommerceProductInstanceModelImpl.ENTITY_CACHE_ENABLED,
			CommerceProductInstanceModelImpl.FINDER_CACHE_ENABLED,
			CommerceProductInstanceImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid",
			new String[] { String.class.getName() },
			CommerceProductInstanceModelImpl.UUID_COLUMN_BITMASK |
			CommerceProductInstanceModelImpl.DISPLAYDATE_COLUMN_BITMASK |
			CommerceProductInstanceModelImpl.CREATEDATE_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID = new FinderPath(CommerceProductInstanceModelImpl.ENTITY_CACHE_ENABLED,
			CommerceProductInstanceModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUuid",
			new String[] { String.class.getName() });

	/**
	 * Returns all the commerce product instances where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching commerce product instances
	 */
	@Override
	public List<CommerceProductInstance> findByUuid(String uuid) {
		return findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce product instances where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceProductInstanceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of commerce product instances
	 * @param end the upper bound of the range of commerce product instances (not inclusive)
	 * @return the range of matching commerce product instances
	 */
	@Override
	public List<CommerceProductInstance> findByUuid(String uuid, int start,
		int end) {
		return findByUuid(uuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce product instances where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceProductInstanceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of commerce product instances
	 * @param end the upper bound of the range of commerce product instances (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce product instances
	 */
	@Override
	public List<CommerceProductInstance> findByUuid(String uuid, int start,
		int end, OrderByComparator<CommerceProductInstance> orderByComparator) {
		return findByUuid(uuid, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce product instances where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceProductInstanceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of commerce product instances
	 * @param end the upper bound of the range of commerce product instances (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching commerce product instances
	 */
	@Override
	public List<CommerceProductInstance> findByUuid(String uuid, int start,
		int end, OrderByComparator<CommerceProductInstance> orderByComparator,
		boolean retrieveFromCache) {
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

		List<CommerceProductInstance> list = null;

		if (retrieveFromCache) {
			list = (List<CommerceProductInstance>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (CommerceProductInstance commerceProductInstance : list) {
					if (!Objects.equals(uuid, commerceProductInstance.getUuid())) {
						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(3 +
						(orderByComparator.getOrderByFields().length * 2));
			}
			else {
				query = new StringBundler(3);
			}

			query.append(_SQL_SELECT_COMMERCEPRODUCTINSTANCE_WHERE);

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
				query.append(CommerceProductInstanceModelImpl.ORDER_BY_JPQL);
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
					list = (List<CommerceProductInstance>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<CommerceProductInstance>)QueryUtil.list(q,
							getDialect(), start, end);
				}

				cacheResult(list);

				finderCache.putResult(finderPath, finderArgs, list);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first commerce product instance in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce product instance
	 * @throws NoSuchProductInstanceException if a matching commerce product instance could not be found
	 */
	@Override
	public CommerceProductInstance findByUuid_First(String uuid,
		OrderByComparator<CommerceProductInstance> orderByComparator)
		throws NoSuchProductInstanceException {
		CommerceProductInstance commerceProductInstance = fetchByUuid_First(uuid,
				orderByComparator);

		if (commerceProductInstance != null) {
			return commerceProductInstance;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchProductInstanceException(msg.toString());
	}

	/**
	 * Returns the first commerce product instance in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce product instance, or <code>null</code> if a matching commerce product instance could not be found
	 */
	@Override
	public CommerceProductInstance fetchByUuid_First(String uuid,
		OrderByComparator<CommerceProductInstance> orderByComparator) {
		List<CommerceProductInstance> list = findByUuid(uuid, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last commerce product instance in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce product instance
	 * @throws NoSuchProductInstanceException if a matching commerce product instance could not be found
	 */
	@Override
	public CommerceProductInstance findByUuid_Last(String uuid,
		OrderByComparator<CommerceProductInstance> orderByComparator)
		throws NoSuchProductInstanceException {
		CommerceProductInstance commerceProductInstance = fetchByUuid_Last(uuid,
				orderByComparator);

		if (commerceProductInstance != null) {
			return commerceProductInstance;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchProductInstanceException(msg.toString());
	}

	/**
	 * Returns the last commerce product instance in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce product instance, or <code>null</code> if a matching commerce product instance could not be found
	 */
	@Override
	public CommerceProductInstance fetchByUuid_Last(String uuid,
		OrderByComparator<CommerceProductInstance> orderByComparator) {
		int count = countByUuid(uuid);

		if (count == 0) {
			return null;
		}

		List<CommerceProductInstance> list = findByUuid(uuid, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the commerce product instances before and after the current commerce product instance in the ordered set where uuid = &#63;.
	 *
	 * @param commerceProductInstanceId the primary key of the current commerce product instance
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce product instance
	 * @throws NoSuchProductInstanceException if a commerce product instance with the primary key could not be found
	 */
	@Override
	public CommerceProductInstance[] findByUuid_PrevAndNext(
		long commerceProductInstanceId, String uuid,
		OrderByComparator<CommerceProductInstance> orderByComparator)
		throws NoSuchProductInstanceException {
		CommerceProductInstance commerceProductInstance = findByPrimaryKey(commerceProductInstanceId);

		Session session = null;

		try {
			session = openSession();

			CommerceProductInstance[] array = new CommerceProductInstanceImpl[3];

			array[0] = getByUuid_PrevAndNext(session, commerceProductInstance,
					uuid, orderByComparator, true);

			array[1] = commerceProductInstance;

			array[2] = getByUuid_PrevAndNext(session, commerceProductInstance,
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

	protected CommerceProductInstance getByUuid_PrevAndNext(Session session,
		CommerceProductInstance commerceProductInstance, String uuid,
		OrderByComparator<CommerceProductInstance> orderByComparator,
		boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(4 +
					(orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_COMMERCEPRODUCTINSTANCE_WHERE);

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
			query.append(CommerceProductInstanceModelImpl.ORDER_BY_JPQL);
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
			Object[] values = orderByComparator.getOrderByConditionValues(commerceProductInstance);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<CommerceProductInstance> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the commerce product instances where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	@Override
	public void removeByUuid(String uuid) {
		for (CommerceProductInstance commerceProductInstance : findByUuid(
				uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(commerceProductInstance);
		}
	}

	/**
	 * Returns the number of commerce product instances where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching commerce product instances
	 */
	@Override
	public int countByUuid(String uuid) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_UUID;

		Object[] finderArgs = new Object[] { uuid };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_COMMERCEPRODUCTINSTANCE_WHERE);

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

				finderCache.putResult(finderPath, finderArgs, count);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_UUID_UUID_1 = "commerceProductInstance.uuid IS NULL";
	private static final String _FINDER_COLUMN_UUID_UUID_2 = "commerceProductInstance.uuid = ?";
	private static final String _FINDER_COLUMN_UUID_UUID_3 = "(commerceProductInstance.uuid IS NULL OR commerceProductInstance.uuid = '')";
	public static final FinderPath FINDER_PATH_FETCH_BY_UUID_G = new FinderPath(CommerceProductInstanceModelImpl.ENTITY_CACHE_ENABLED,
			CommerceProductInstanceModelImpl.FINDER_CACHE_ENABLED,
			CommerceProductInstanceImpl.class, FINDER_CLASS_NAME_ENTITY,
			"fetchByUUID_G",
			new String[] { String.class.getName(), Long.class.getName() },
			CommerceProductInstanceModelImpl.UUID_COLUMN_BITMASK |
			CommerceProductInstanceModelImpl.GROUPID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID_G = new FinderPath(CommerceProductInstanceModelImpl.ENTITY_CACHE_ENABLED,
			CommerceProductInstanceModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUUID_G",
			new String[] { String.class.getName(), Long.class.getName() });

	/**
	 * Returns the commerce product instance where uuid = &#63; and groupId = &#63; or throws a {@link NoSuchProductInstanceException} if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching commerce product instance
	 * @throws NoSuchProductInstanceException if a matching commerce product instance could not be found
	 */
	@Override
	public CommerceProductInstance findByUUID_G(String uuid, long groupId)
		throws NoSuchProductInstanceException {
		CommerceProductInstance commerceProductInstance = fetchByUUID_G(uuid,
				groupId);

		if (commerceProductInstance == null) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("uuid=");
			msg.append(uuid);

			msg.append(", groupId=");
			msg.append(groupId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isDebugEnabled()) {
				_log.debug(msg.toString());
			}

			throw new NoSuchProductInstanceException(msg.toString());
		}

		return commerceProductInstance;
	}

	/**
	 * Returns the commerce product instance where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching commerce product instance, or <code>null</code> if a matching commerce product instance could not be found
	 */
	@Override
	public CommerceProductInstance fetchByUUID_G(String uuid, long groupId) {
		return fetchByUUID_G(uuid, groupId, true);
	}

	/**
	 * Returns the commerce product instance where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the matching commerce product instance, or <code>null</code> if a matching commerce product instance could not be found
	 */
	@Override
	public CommerceProductInstance fetchByUUID_G(String uuid, long groupId,
		boolean retrieveFromCache) {
		Object[] finderArgs = new Object[] { uuid, groupId };

		Object result = null;

		if (retrieveFromCache) {
			result = finderCache.getResult(FINDER_PATH_FETCH_BY_UUID_G,
					finderArgs, this);
		}

		if (result instanceof CommerceProductInstance) {
			CommerceProductInstance commerceProductInstance = (CommerceProductInstance)result;

			if (!Objects.equals(uuid, commerceProductInstance.getUuid()) ||
					(groupId != commerceProductInstance.getGroupId())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_SELECT_COMMERCEPRODUCTINSTANCE_WHERE);

			boolean bindUuid = false;

			if (uuid == null) {
				query.append(_FINDER_COLUMN_UUID_G_UUID_1);
			}
			else if (uuid.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_UUID_G_UUID_3);
			}
			else {
				bindUuid = true;

				query.append(_FINDER_COLUMN_UUID_G_UUID_2);
			}

			query.append(_FINDER_COLUMN_UUID_G_GROUPID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindUuid) {
					qPos.add(uuid);
				}

				qPos.add(groupId);

				List<CommerceProductInstance> list = q.list();

				if (list.isEmpty()) {
					finderCache.putResult(FINDER_PATH_FETCH_BY_UUID_G,
						finderArgs, list);
				}
				else {
					CommerceProductInstance commerceProductInstance = list.get(0);

					result = commerceProductInstance;

					cacheResult(commerceProductInstance);

					if ((commerceProductInstance.getUuid() == null) ||
							!commerceProductInstance.getUuid().equals(uuid) ||
							(commerceProductInstance.getGroupId() != groupId)) {
						finderCache.putResult(FINDER_PATH_FETCH_BY_UUID_G,
							finderArgs, commerceProductInstance);
					}
				}
			}
			catch (Exception e) {
				finderCache.removeResult(FINDER_PATH_FETCH_BY_UUID_G, finderArgs);

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
			return (CommerceProductInstance)result;
		}
	}

	/**
	 * Removes the commerce product instance where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the commerce product instance that was removed
	 */
	@Override
	public CommerceProductInstance removeByUUID_G(String uuid, long groupId)
		throws NoSuchProductInstanceException {
		CommerceProductInstance commerceProductInstance = findByUUID_G(uuid,
				groupId);

		return remove(commerceProductInstance);
	}

	/**
	 * Returns the number of commerce product instances where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching commerce product instances
	 */
	@Override
	public int countByUUID_G(String uuid, long groupId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_UUID_G;

		Object[] finderArgs = new Object[] { uuid, groupId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_COMMERCEPRODUCTINSTANCE_WHERE);

			boolean bindUuid = false;

			if (uuid == null) {
				query.append(_FINDER_COLUMN_UUID_G_UUID_1);
			}
			else if (uuid.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_UUID_G_UUID_3);
			}
			else {
				bindUuid = true;

				query.append(_FINDER_COLUMN_UUID_G_UUID_2);
			}

			query.append(_FINDER_COLUMN_UUID_G_GROUPID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindUuid) {
					qPos.add(uuid);
				}

				qPos.add(groupId);

				count = (Long)q.uniqueResult();

				finderCache.putResult(finderPath, finderArgs, count);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_UUID_G_UUID_1 = "commerceProductInstance.uuid IS NULL AND ";
	private static final String _FINDER_COLUMN_UUID_G_UUID_2 = "commerceProductInstance.uuid = ? AND ";
	private static final String _FINDER_COLUMN_UUID_G_UUID_3 = "(commerceProductInstance.uuid IS NULL OR commerceProductInstance.uuid = '') AND ";
	private static final String _FINDER_COLUMN_UUID_G_GROUPID_2 = "commerceProductInstance.groupId = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_UUID_C = new FinderPath(CommerceProductInstanceModelImpl.ENTITY_CACHE_ENABLED,
			CommerceProductInstanceModelImpl.FINDER_CACHE_ENABLED,
			CommerceProductInstanceImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUuid_C",
			new String[] {
				String.class.getName(), Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C =
		new FinderPath(CommerceProductInstanceModelImpl.ENTITY_CACHE_ENABLED,
			CommerceProductInstanceModelImpl.FINDER_CACHE_ENABLED,
			CommerceProductInstanceImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid_C",
			new String[] { String.class.getName(), Long.class.getName() },
			CommerceProductInstanceModelImpl.UUID_COLUMN_BITMASK |
			CommerceProductInstanceModelImpl.COMPANYID_COLUMN_BITMASK |
			CommerceProductInstanceModelImpl.DISPLAYDATE_COLUMN_BITMASK |
			CommerceProductInstanceModelImpl.CREATEDATE_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID_C = new FinderPath(CommerceProductInstanceModelImpl.ENTITY_CACHE_ENABLED,
			CommerceProductInstanceModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUuid_C",
			new String[] { String.class.getName(), Long.class.getName() });

	/**
	 * Returns all the commerce product instances where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching commerce product instances
	 */
	@Override
	public List<CommerceProductInstance> findByUuid_C(String uuid,
		long companyId) {
		return findByUuid_C(uuid, companyId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce product instances where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceProductInstanceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce product instances
	 * @param end the upper bound of the range of commerce product instances (not inclusive)
	 * @return the range of matching commerce product instances
	 */
	@Override
	public List<CommerceProductInstance> findByUuid_C(String uuid,
		long companyId, int start, int end) {
		return findByUuid_C(uuid, companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce product instances where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceProductInstanceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce product instances
	 * @param end the upper bound of the range of commerce product instances (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce product instances
	 */
	@Override
	public List<CommerceProductInstance> findByUuid_C(String uuid,
		long companyId, int start, int end,
		OrderByComparator<CommerceProductInstance> orderByComparator) {
		return findByUuid_C(uuid, companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce product instances where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceProductInstanceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce product instances
	 * @param end the upper bound of the range of commerce product instances (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching commerce product instances
	 */
	@Override
	public List<CommerceProductInstance> findByUuid_C(String uuid,
		long companyId, int start, int end,
		OrderByComparator<CommerceProductInstance> orderByComparator,
		boolean retrieveFromCache) {
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

		List<CommerceProductInstance> list = null;

		if (retrieveFromCache) {
			list = (List<CommerceProductInstance>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (CommerceProductInstance commerceProductInstance : list) {
					if (!Objects.equals(uuid, commerceProductInstance.getUuid()) ||
							(companyId != commerceProductInstance.getCompanyId())) {
						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(4 +
						(orderByComparator.getOrderByFields().length * 2));
			}
			else {
				query = new StringBundler(4);
			}

			query.append(_SQL_SELECT_COMMERCEPRODUCTINSTANCE_WHERE);

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
				query.append(CommerceProductInstanceModelImpl.ORDER_BY_JPQL);
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
					list = (List<CommerceProductInstance>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<CommerceProductInstance>)QueryUtil.list(q,
							getDialect(), start, end);
				}

				cacheResult(list);

				finderCache.putResult(finderPath, finderArgs, list);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first commerce product instance in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce product instance
	 * @throws NoSuchProductInstanceException if a matching commerce product instance could not be found
	 */
	@Override
	public CommerceProductInstance findByUuid_C_First(String uuid,
		long companyId,
		OrderByComparator<CommerceProductInstance> orderByComparator)
		throws NoSuchProductInstanceException {
		CommerceProductInstance commerceProductInstance = fetchByUuid_C_First(uuid,
				companyId, orderByComparator);

		if (commerceProductInstance != null) {
			return commerceProductInstance;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(", companyId=");
		msg.append(companyId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchProductInstanceException(msg.toString());
	}

	/**
	 * Returns the first commerce product instance in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce product instance, or <code>null</code> if a matching commerce product instance could not be found
	 */
	@Override
	public CommerceProductInstance fetchByUuid_C_First(String uuid,
		long companyId,
		OrderByComparator<CommerceProductInstance> orderByComparator) {
		List<CommerceProductInstance> list = findByUuid_C(uuid, companyId, 0,
				1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last commerce product instance in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce product instance
	 * @throws NoSuchProductInstanceException if a matching commerce product instance could not be found
	 */
	@Override
	public CommerceProductInstance findByUuid_C_Last(String uuid,
		long companyId,
		OrderByComparator<CommerceProductInstance> orderByComparator)
		throws NoSuchProductInstanceException {
		CommerceProductInstance commerceProductInstance = fetchByUuid_C_Last(uuid,
				companyId, orderByComparator);

		if (commerceProductInstance != null) {
			return commerceProductInstance;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(", companyId=");
		msg.append(companyId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchProductInstanceException(msg.toString());
	}

	/**
	 * Returns the last commerce product instance in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce product instance, or <code>null</code> if a matching commerce product instance could not be found
	 */
	@Override
	public CommerceProductInstance fetchByUuid_C_Last(String uuid,
		long companyId,
		OrderByComparator<CommerceProductInstance> orderByComparator) {
		int count = countByUuid_C(uuid, companyId);

		if (count == 0) {
			return null;
		}

		List<CommerceProductInstance> list = findByUuid_C(uuid, companyId,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the commerce product instances before and after the current commerce product instance in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param commerceProductInstanceId the primary key of the current commerce product instance
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce product instance
	 * @throws NoSuchProductInstanceException if a commerce product instance with the primary key could not be found
	 */
	@Override
	public CommerceProductInstance[] findByUuid_C_PrevAndNext(
		long commerceProductInstanceId, String uuid, long companyId,
		OrderByComparator<CommerceProductInstance> orderByComparator)
		throws NoSuchProductInstanceException {
		CommerceProductInstance commerceProductInstance = findByPrimaryKey(commerceProductInstanceId);

		Session session = null;

		try {
			session = openSession();

			CommerceProductInstance[] array = new CommerceProductInstanceImpl[3];

			array[0] = getByUuid_C_PrevAndNext(session,
					commerceProductInstance, uuid, companyId,
					orderByComparator, true);

			array[1] = commerceProductInstance;

			array[2] = getByUuid_C_PrevAndNext(session,
					commerceProductInstance, uuid, companyId,
					orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected CommerceProductInstance getByUuid_C_PrevAndNext(Session session,
		CommerceProductInstance commerceProductInstance, String uuid,
		long companyId,
		OrderByComparator<CommerceProductInstance> orderByComparator,
		boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(5 +
					(orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(4);
		}

		query.append(_SQL_SELECT_COMMERCEPRODUCTINSTANCE_WHERE);

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
			query.append(CommerceProductInstanceModelImpl.ORDER_BY_JPQL);
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
			Object[] values = orderByComparator.getOrderByConditionValues(commerceProductInstance);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<CommerceProductInstance> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the commerce product instances where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	@Override
	public void removeByUuid_C(String uuid, long companyId) {
		for (CommerceProductInstance commerceProductInstance : findByUuid_C(
				uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(commerceProductInstance);
		}
	}

	/**
	 * Returns the number of commerce product instances where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching commerce product instances
	 */
	@Override
	public int countByUuid_C(String uuid, long companyId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_UUID_C;

		Object[] finderArgs = new Object[] { uuid, companyId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_COMMERCEPRODUCTINSTANCE_WHERE);

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

				finderCache.putResult(finderPath, finderArgs, count);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_UUID_C_UUID_1 = "commerceProductInstance.uuid IS NULL AND ";
	private static final String _FINDER_COLUMN_UUID_C_UUID_2 = "commerceProductInstance.uuid = ? AND ";
	private static final String _FINDER_COLUMN_UUID_C_UUID_3 = "(commerceProductInstance.uuid IS NULL OR commerceProductInstance.uuid = '') AND ";
	private static final String _FINDER_COLUMN_UUID_C_COMPANYID_2 = "commerceProductInstance.companyId = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_GROUPID = new FinderPath(CommerceProductInstanceModelImpl.ENTITY_CACHE_ENABLED,
			CommerceProductInstanceModelImpl.FINDER_CACHE_ENABLED,
			CommerceProductInstanceImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByGroupId",
			new String[] {
				Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID =
		new FinderPath(CommerceProductInstanceModelImpl.ENTITY_CACHE_ENABLED,
			CommerceProductInstanceModelImpl.FINDER_CACHE_ENABLED,
			CommerceProductInstanceImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByGroupId",
			new String[] { Long.class.getName() },
			CommerceProductInstanceModelImpl.GROUPID_COLUMN_BITMASK |
			CommerceProductInstanceModelImpl.DISPLAYDATE_COLUMN_BITMASK |
			CommerceProductInstanceModelImpl.CREATEDATE_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_GROUPID = new FinderPath(CommerceProductInstanceModelImpl.ENTITY_CACHE_ENABLED,
			CommerceProductInstanceModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByGroupId",
			new String[] { Long.class.getName() });

	/**
	 * Returns all the commerce product instances where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching commerce product instances
	 */
	@Override
	public List<CommerceProductInstance> findByGroupId(long groupId) {
		return findByGroupId(groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce product instances where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceProductInstanceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of commerce product instances
	 * @param end the upper bound of the range of commerce product instances (not inclusive)
	 * @return the range of matching commerce product instances
	 */
	@Override
	public List<CommerceProductInstance> findByGroupId(long groupId, int start,
		int end) {
		return findByGroupId(groupId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce product instances where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceProductInstanceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of commerce product instances
	 * @param end the upper bound of the range of commerce product instances (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce product instances
	 */
	@Override
	public List<CommerceProductInstance> findByGroupId(long groupId, int start,
		int end, OrderByComparator<CommerceProductInstance> orderByComparator) {
		return findByGroupId(groupId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce product instances where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceProductInstanceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of commerce product instances
	 * @param end the upper bound of the range of commerce product instances (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching commerce product instances
	 */
	@Override
	public List<CommerceProductInstance> findByGroupId(long groupId, int start,
		int end, OrderByComparator<CommerceProductInstance> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID;
			finderArgs = new Object[] { groupId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_GROUPID;
			finderArgs = new Object[] { groupId, start, end, orderByComparator };
		}

		List<CommerceProductInstance> list = null;

		if (retrieveFromCache) {
			list = (List<CommerceProductInstance>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (CommerceProductInstance commerceProductInstance : list) {
					if ((groupId != commerceProductInstance.getGroupId())) {
						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(3 +
						(orderByComparator.getOrderByFields().length * 2));
			}
			else {
				query = new StringBundler(3);
			}

			query.append(_SQL_SELECT_COMMERCEPRODUCTINSTANCE_WHERE);

			query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(CommerceProductInstanceModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (!pagination) {
					list = (List<CommerceProductInstance>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<CommerceProductInstance>)QueryUtil.list(q,
							getDialect(), start, end);
				}

				cacheResult(list);

				finderCache.putResult(finderPath, finderArgs, list);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first commerce product instance in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce product instance
	 * @throws NoSuchProductInstanceException if a matching commerce product instance could not be found
	 */
	@Override
	public CommerceProductInstance findByGroupId_First(long groupId,
		OrderByComparator<CommerceProductInstance> orderByComparator)
		throws NoSuchProductInstanceException {
		CommerceProductInstance commerceProductInstance = fetchByGroupId_First(groupId,
				orderByComparator);

		if (commerceProductInstance != null) {
			return commerceProductInstance;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchProductInstanceException(msg.toString());
	}

	/**
	 * Returns the first commerce product instance in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce product instance, or <code>null</code> if a matching commerce product instance could not be found
	 */
	@Override
	public CommerceProductInstance fetchByGroupId_First(long groupId,
		OrderByComparator<CommerceProductInstance> orderByComparator) {
		List<CommerceProductInstance> list = findByGroupId(groupId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last commerce product instance in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce product instance
	 * @throws NoSuchProductInstanceException if a matching commerce product instance could not be found
	 */
	@Override
	public CommerceProductInstance findByGroupId_Last(long groupId,
		OrderByComparator<CommerceProductInstance> orderByComparator)
		throws NoSuchProductInstanceException {
		CommerceProductInstance commerceProductInstance = fetchByGroupId_Last(groupId,
				orderByComparator);

		if (commerceProductInstance != null) {
			return commerceProductInstance;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchProductInstanceException(msg.toString());
	}

	/**
	 * Returns the last commerce product instance in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce product instance, or <code>null</code> if a matching commerce product instance could not be found
	 */
	@Override
	public CommerceProductInstance fetchByGroupId_Last(long groupId,
		OrderByComparator<CommerceProductInstance> orderByComparator) {
		int count = countByGroupId(groupId);

		if (count == 0) {
			return null;
		}

		List<CommerceProductInstance> list = findByGroupId(groupId, count - 1,
				count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the commerce product instances before and after the current commerce product instance in the ordered set where groupId = &#63;.
	 *
	 * @param commerceProductInstanceId the primary key of the current commerce product instance
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce product instance
	 * @throws NoSuchProductInstanceException if a commerce product instance with the primary key could not be found
	 */
	@Override
	public CommerceProductInstance[] findByGroupId_PrevAndNext(
		long commerceProductInstanceId, long groupId,
		OrderByComparator<CommerceProductInstance> orderByComparator)
		throws NoSuchProductInstanceException {
		CommerceProductInstance commerceProductInstance = findByPrimaryKey(commerceProductInstanceId);

		Session session = null;

		try {
			session = openSession();

			CommerceProductInstance[] array = new CommerceProductInstanceImpl[3];

			array[0] = getByGroupId_PrevAndNext(session,
					commerceProductInstance, groupId, orderByComparator, true);

			array[1] = commerceProductInstance;

			array[2] = getByGroupId_PrevAndNext(session,
					commerceProductInstance, groupId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected CommerceProductInstance getByGroupId_PrevAndNext(
		Session session, CommerceProductInstance commerceProductInstance,
		long groupId,
		OrderByComparator<CommerceProductInstance> orderByComparator,
		boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(4 +
					(orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_COMMERCEPRODUCTINSTANCE_WHERE);

		query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

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
			query.append(CommerceProductInstanceModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(commerceProductInstance);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<CommerceProductInstance> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the commerce product instances where groupId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 */
	@Override
	public void removeByGroupId(long groupId) {
		for (CommerceProductInstance commerceProductInstance : findByGroupId(
				groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(commerceProductInstance);
		}
	}

	/**
	 * Returns the number of commerce product instances where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching commerce product instances
	 */
	@Override
	public int countByGroupId(long groupId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_GROUPID;

		Object[] finderArgs = new Object[] { groupId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_COMMERCEPRODUCTINSTANCE_WHERE);

			query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				count = (Long)q.uniqueResult();

				finderCache.putResult(finderPath, finderArgs, count);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_GROUPID_GROUPID_2 = "commerceProductInstance.groupId = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_COMPANYID =
		new FinderPath(CommerceProductInstanceModelImpl.ENTITY_CACHE_ENABLED,
			CommerceProductInstanceModelImpl.FINDER_CACHE_ENABLED,
			CommerceProductInstanceImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByCompanyId",
			new String[] {
				Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID =
		new FinderPath(CommerceProductInstanceModelImpl.ENTITY_CACHE_ENABLED,
			CommerceProductInstanceModelImpl.FINDER_CACHE_ENABLED,
			CommerceProductInstanceImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByCompanyId",
			new String[] { Long.class.getName() },
			CommerceProductInstanceModelImpl.COMPANYID_COLUMN_BITMASK |
			CommerceProductInstanceModelImpl.DISPLAYDATE_COLUMN_BITMASK |
			CommerceProductInstanceModelImpl.CREATEDATE_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_COMPANYID = new FinderPath(CommerceProductInstanceModelImpl.ENTITY_CACHE_ENABLED,
			CommerceProductInstanceModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByCompanyId",
			new String[] { Long.class.getName() });

	/**
	 * Returns all the commerce product instances where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching commerce product instances
	 */
	@Override
	public List<CommerceProductInstance> findByCompanyId(long companyId) {
		return findByCompanyId(companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the commerce product instances where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceProductInstanceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce product instances
	 * @param end the upper bound of the range of commerce product instances (not inclusive)
	 * @return the range of matching commerce product instances
	 */
	@Override
	public List<CommerceProductInstance> findByCompanyId(long companyId,
		int start, int end) {
		return findByCompanyId(companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce product instances where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceProductInstanceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce product instances
	 * @param end the upper bound of the range of commerce product instances (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce product instances
	 */
	@Override
	public List<CommerceProductInstance> findByCompanyId(long companyId,
		int start, int end,
		OrderByComparator<CommerceProductInstance> orderByComparator) {
		return findByCompanyId(companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce product instances where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceProductInstanceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce product instances
	 * @param end the upper bound of the range of commerce product instances (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching commerce product instances
	 */
	@Override
	public List<CommerceProductInstance> findByCompanyId(long companyId,
		int start, int end,
		OrderByComparator<CommerceProductInstance> orderByComparator,
		boolean retrieveFromCache) {
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

		List<CommerceProductInstance> list = null;

		if (retrieveFromCache) {
			list = (List<CommerceProductInstance>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (CommerceProductInstance commerceProductInstance : list) {
					if ((companyId != commerceProductInstance.getCompanyId())) {
						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(3 +
						(orderByComparator.getOrderByFields().length * 2));
			}
			else {
				query = new StringBundler(3);
			}

			query.append(_SQL_SELECT_COMMERCEPRODUCTINSTANCE_WHERE);

			query.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(CommerceProductInstanceModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				if (!pagination) {
					list = (List<CommerceProductInstance>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<CommerceProductInstance>)QueryUtil.list(q,
							getDialect(), start, end);
				}

				cacheResult(list);

				finderCache.putResult(finderPath, finderArgs, list);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first commerce product instance in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce product instance
	 * @throws NoSuchProductInstanceException if a matching commerce product instance could not be found
	 */
	@Override
	public CommerceProductInstance findByCompanyId_First(long companyId,
		OrderByComparator<CommerceProductInstance> orderByComparator)
		throws NoSuchProductInstanceException {
		CommerceProductInstance commerceProductInstance = fetchByCompanyId_First(companyId,
				orderByComparator);

		if (commerceProductInstance != null) {
			return commerceProductInstance;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("companyId=");
		msg.append(companyId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchProductInstanceException(msg.toString());
	}

	/**
	 * Returns the first commerce product instance in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce product instance, or <code>null</code> if a matching commerce product instance could not be found
	 */
	@Override
	public CommerceProductInstance fetchByCompanyId_First(long companyId,
		OrderByComparator<CommerceProductInstance> orderByComparator) {
		List<CommerceProductInstance> list = findByCompanyId(companyId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last commerce product instance in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce product instance
	 * @throws NoSuchProductInstanceException if a matching commerce product instance could not be found
	 */
	@Override
	public CommerceProductInstance findByCompanyId_Last(long companyId,
		OrderByComparator<CommerceProductInstance> orderByComparator)
		throws NoSuchProductInstanceException {
		CommerceProductInstance commerceProductInstance = fetchByCompanyId_Last(companyId,
				orderByComparator);

		if (commerceProductInstance != null) {
			return commerceProductInstance;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("companyId=");
		msg.append(companyId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchProductInstanceException(msg.toString());
	}

	/**
	 * Returns the last commerce product instance in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce product instance, or <code>null</code> if a matching commerce product instance could not be found
	 */
	@Override
	public CommerceProductInstance fetchByCompanyId_Last(long companyId,
		OrderByComparator<CommerceProductInstance> orderByComparator) {
		int count = countByCompanyId(companyId);

		if (count == 0) {
			return null;
		}

		List<CommerceProductInstance> list = findByCompanyId(companyId,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the commerce product instances before and after the current commerce product instance in the ordered set where companyId = &#63;.
	 *
	 * @param commerceProductInstanceId the primary key of the current commerce product instance
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce product instance
	 * @throws NoSuchProductInstanceException if a commerce product instance with the primary key could not be found
	 */
	@Override
	public CommerceProductInstance[] findByCompanyId_PrevAndNext(
		long commerceProductInstanceId, long companyId,
		OrderByComparator<CommerceProductInstance> orderByComparator)
		throws NoSuchProductInstanceException {
		CommerceProductInstance commerceProductInstance = findByPrimaryKey(commerceProductInstanceId);

		Session session = null;

		try {
			session = openSession();

			CommerceProductInstance[] array = new CommerceProductInstanceImpl[3];

			array[0] = getByCompanyId_PrevAndNext(session,
					commerceProductInstance, companyId, orderByComparator, true);

			array[1] = commerceProductInstance;

			array[2] = getByCompanyId_PrevAndNext(session,
					commerceProductInstance, companyId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected CommerceProductInstance getByCompanyId_PrevAndNext(
		Session session, CommerceProductInstance commerceProductInstance,
		long companyId,
		OrderByComparator<CommerceProductInstance> orderByComparator,
		boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(4 +
					(orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_COMMERCEPRODUCTINSTANCE_WHERE);

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
			query.append(CommerceProductInstanceModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(companyId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(commerceProductInstance);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<CommerceProductInstance> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the commerce product instances where companyId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 */
	@Override
	public void removeByCompanyId(long companyId) {
		for (CommerceProductInstance commerceProductInstance : findByCompanyId(
				companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(commerceProductInstance);
		}
	}

	/**
	 * Returns the number of commerce product instances where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching commerce product instances
	 */
	@Override
	public int countByCompanyId(long companyId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_COMPANYID;

		Object[] finderArgs = new Object[] { companyId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_COMMERCEPRODUCTINSTANCE_WHERE);

			query.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				count = (Long)q.uniqueResult();

				finderCache.putResult(finderPath, finderArgs, count);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_COMPANYID_COMPANYID_2 = "commerceProductInstance.companyId = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_COMMERCEPRODUCTDEFINITIONID =
		new FinderPath(CommerceProductInstanceModelImpl.ENTITY_CACHE_ENABLED,
			CommerceProductInstanceModelImpl.FINDER_CACHE_ENABLED,
			CommerceProductInstanceImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByCommerceProductDefinitionId",
			new String[] {
				Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMMERCEPRODUCTDEFINITIONID =
		new FinderPath(CommerceProductInstanceModelImpl.ENTITY_CACHE_ENABLED,
			CommerceProductInstanceModelImpl.FINDER_CACHE_ENABLED,
			CommerceProductInstanceImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByCommerceProductDefinitionId",
			new String[] { Long.class.getName() },
			CommerceProductInstanceModelImpl.COMMERCEPRODUCTDEFINITIONID_COLUMN_BITMASK |
			CommerceProductInstanceModelImpl.DISPLAYDATE_COLUMN_BITMASK |
			CommerceProductInstanceModelImpl.CREATEDATE_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_COMMERCEPRODUCTDEFINITIONID =
		new FinderPath(CommerceProductInstanceModelImpl.ENTITY_CACHE_ENABLED,
			CommerceProductInstanceModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByCommerceProductDefinitionId",
			new String[] { Long.class.getName() });

	/**
	 * Returns all the commerce product instances where commerceProductDefinitionId = &#63;.
	 *
	 * @param commerceProductDefinitionId the commerce product definition ID
	 * @return the matching commerce product instances
	 */
	@Override
	public List<CommerceProductInstance> findByCommerceProductDefinitionId(
		long commerceProductDefinitionId) {
		return findByCommerceProductDefinitionId(commerceProductDefinitionId,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce product instances where commerceProductDefinitionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceProductInstanceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param commerceProductDefinitionId the commerce product definition ID
	 * @param start the lower bound of the range of commerce product instances
	 * @param end the upper bound of the range of commerce product instances (not inclusive)
	 * @return the range of matching commerce product instances
	 */
	@Override
	public List<CommerceProductInstance> findByCommerceProductDefinitionId(
		long commerceProductDefinitionId, int start, int end) {
		return findByCommerceProductDefinitionId(commerceProductDefinitionId,
			start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce product instances where commerceProductDefinitionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceProductInstanceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param commerceProductDefinitionId the commerce product definition ID
	 * @param start the lower bound of the range of commerce product instances
	 * @param end the upper bound of the range of commerce product instances (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce product instances
	 */
	@Override
	public List<CommerceProductInstance> findByCommerceProductDefinitionId(
		long commerceProductDefinitionId, int start, int end,
		OrderByComparator<CommerceProductInstance> orderByComparator) {
		return findByCommerceProductDefinitionId(commerceProductDefinitionId,
			start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce product instances where commerceProductDefinitionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceProductInstanceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param commerceProductDefinitionId the commerce product definition ID
	 * @param start the lower bound of the range of commerce product instances
	 * @param end the upper bound of the range of commerce product instances (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching commerce product instances
	 */
	@Override
	public List<CommerceProductInstance> findByCommerceProductDefinitionId(
		long commerceProductDefinitionId, int start, int end,
		OrderByComparator<CommerceProductInstance> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMMERCEPRODUCTDEFINITIONID;
			finderArgs = new Object[] { commerceProductDefinitionId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_COMMERCEPRODUCTDEFINITIONID;
			finderArgs = new Object[] {
					commerceProductDefinitionId,
					
					start, end, orderByComparator
				};
		}

		List<CommerceProductInstance> list = null;

		if (retrieveFromCache) {
			list = (List<CommerceProductInstance>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (CommerceProductInstance commerceProductInstance : list) {
					if ((commerceProductDefinitionId != commerceProductInstance.getCommerceProductDefinitionId())) {
						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(3 +
						(orderByComparator.getOrderByFields().length * 2));
			}
			else {
				query = new StringBundler(3);
			}

			query.append(_SQL_SELECT_COMMERCEPRODUCTINSTANCE_WHERE);

			query.append(_FINDER_COLUMN_COMMERCEPRODUCTDEFINITIONID_COMMERCEPRODUCTDEFINITIONID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(CommerceProductInstanceModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(commerceProductDefinitionId);

				if (!pagination) {
					list = (List<CommerceProductInstance>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<CommerceProductInstance>)QueryUtil.list(q,
							getDialect(), start, end);
				}

				cacheResult(list);

				finderCache.putResult(finderPath, finderArgs, list);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first commerce product instance in the ordered set where commerceProductDefinitionId = &#63;.
	 *
	 * @param commerceProductDefinitionId the commerce product definition ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce product instance
	 * @throws NoSuchProductInstanceException if a matching commerce product instance could not be found
	 */
	@Override
	public CommerceProductInstance findByCommerceProductDefinitionId_First(
		long commerceProductDefinitionId,
		OrderByComparator<CommerceProductInstance> orderByComparator)
		throws NoSuchProductInstanceException {
		CommerceProductInstance commerceProductInstance = fetchByCommerceProductDefinitionId_First(commerceProductDefinitionId,
				orderByComparator);

		if (commerceProductInstance != null) {
			return commerceProductInstance;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("commerceProductDefinitionId=");
		msg.append(commerceProductDefinitionId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchProductInstanceException(msg.toString());
	}

	/**
	 * Returns the first commerce product instance in the ordered set where commerceProductDefinitionId = &#63;.
	 *
	 * @param commerceProductDefinitionId the commerce product definition ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce product instance, or <code>null</code> if a matching commerce product instance could not be found
	 */
	@Override
	public CommerceProductInstance fetchByCommerceProductDefinitionId_First(
		long commerceProductDefinitionId,
		OrderByComparator<CommerceProductInstance> orderByComparator) {
		List<CommerceProductInstance> list = findByCommerceProductDefinitionId(commerceProductDefinitionId,
				0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last commerce product instance in the ordered set where commerceProductDefinitionId = &#63;.
	 *
	 * @param commerceProductDefinitionId the commerce product definition ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce product instance
	 * @throws NoSuchProductInstanceException if a matching commerce product instance could not be found
	 */
	@Override
	public CommerceProductInstance findByCommerceProductDefinitionId_Last(
		long commerceProductDefinitionId,
		OrderByComparator<CommerceProductInstance> orderByComparator)
		throws NoSuchProductInstanceException {
		CommerceProductInstance commerceProductInstance = fetchByCommerceProductDefinitionId_Last(commerceProductDefinitionId,
				orderByComparator);

		if (commerceProductInstance != null) {
			return commerceProductInstance;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("commerceProductDefinitionId=");
		msg.append(commerceProductDefinitionId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchProductInstanceException(msg.toString());
	}

	/**
	 * Returns the last commerce product instance in the ordered set where commerceProductDefinitionId = &#63;.
	 *
	 * @param commerceProductDefinitionId the commerce product definition ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce product instance, or <code>null</code> if a matching commerce product instance could not be found
	 */
	@Override
	public CommerceProductInstance fetchByCommerceProductDefinitionId_Last(
		long commerceProductDefinitionId,
		OrderByComparator<CommerceProductInstance> orderByComparator) {
		int count = countByCommerceProductDefinitionId(commerceProductDefinitionId);

		if (count == 0) {
			return null;
		}

		List<CommerceProductInstance> list = findByCommerceProductDefinitionId(commerceProductDefinitionId,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the commerce product instances before and after the current commerce product instance in the ordered set where commerceProductDefinitionId = &#63;.
	 *
	 * @param commerceProductInstanceId the primary key of the current commerce product instance
	 * @param commerceProductDefinitionId the commerce product definition ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce product instance
	 * @throws NoSuchProductInstanceException if a commerce product instance with the primary key could not be found
	 */
	@Override
	public CommerceProductInstance[] findByCommerceProductDefinitionId_PrevAndNext(
		long commerceProductInstanceId, long commerceProductDefinitionId,
		OrderByComparator<CommerceProductInstance> orderByComparator)
		throws NoSuchProductInstanceException {
		CommerceProductInstance commerceProductInstance = findByPrimaryKey(commerceProductInstanceId);

		Session session = null;

		try {
			session = openSession();

			CommerceProductInstance[] array = new CommerceProductInstanceImpl[3];

			array[0] = getByCommerceProductDefinitionId_PrevAndNext(session,
					commerceProductInstance, commerceProductDefinitionId,
					orderByComparator, true);

			array[1] = commerceProductInstance;

			array[2] = getByCommerceProductDefinitionId_PrevAndNext(session,
					commerceProductInstance, commerceProductDefinitionId,
					orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected CommerceProductInstance getByCommerceProductDefinitionId_PrevAndNext(
		Session session, CommerceProductInstance commerceProductInstance,
		long commerceProductDefinitionId,
		OrderByComparator<CommerceProductInstance> orderByComparator,
		boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(4 +
					(orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_COMMERCEPRODUCTINSTANCE_WHERE);

		query.append(_FINDER_COLUMN_COMMERCEPRODUCTDEFINITIONID_COMMERCEPRODUCTDEFINITIONID_2);

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
			query.append(CommerceProductInstanceModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(commerceProductDefinitionId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(commerceProductInstance);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<CommerceProductInstance> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the commerce product instances where commerceProductDefinitionId = &#63; from the database.
	 *
	 * @param commerceProductDefinitionId the commerce product definition ID
	 */
	@Override
	public void removeByCommerceProductDefinitionId(
		long commerceProductDefinitionId) {
		for (CommerceProductInstance commerceProductInstance : findByCommerceProductDefinitionId(
				commerceProductDefinitionId, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS, null)) {
			remove(commerceProductInstance);
		}
	}

	/**
	 * Returns the number of commerce product instances where commerceProductDefinitionId = &#63;.
	 *
	 * @param commerceProductDefinitionId the commerce product definition ID
	 * @return the number of matching commerce product instances
	 */
	@Override
	public int countByCommerceProductDefinitionId(
		long commerceProductDefinitionId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_COMMERCEPRODUCTDEFINITIONID;

		Object[] finderArgs = new Object[] { commerceProductDefinitionId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_COMMERCEPRODUCTINSTANCE_WHERE);

			query.append(_FINDER_COLUMN_COMMERCEPRODUCTDEFINITIONID_COMMERCEPRODUCTDEFINITIONID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(commerceProductDefinitionId);

				count = (Long)q.uniqueResult();

				finderCache.putResult(finderPath, finderArgs, count);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_COMMERCEPRODUCTDEFINITIONID_COMMERCEPRODUCTDEFINITIONID_2 =
		"commerceProductInstance.commerceProductDefinitionId = ?";
	public static final FinderPath FINDER_PATH_FETCH_BY_C_S = new FinderPath(CommerceProductInstanceModelImpl.ENTITY_CACHE_ENABLED,
			CommerceProductInstanceModelImpl.FINDER_CACHE_ENABLED,
			CommerceProductInstanceImpl.class, FINDER_CLASS_NAME_ENTITY,
			"fetchByC_S",
			new String[] { Long.class.getName(), String.class.getName() },
			CommerceProductInstanceModelImpl.COMMERCEPRODUCTDEFINITIONID_COLUMN_BITMASK |
			CommerceProductInstanceModelImpl.SKU_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_C_S = new FinderPath(CommerceProductInstanceModelImpl.ENTITY_CACHE_ENABLED,
			CommerceProductInstanceModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_S",
			new String[] { Long.class.getName(), String.class.getName() });

	/**
	 * Returns the commerce product instance where commerceProductDefinitionId = &#63; and sku = &#63; or throws a {@link NoSuchProductInstanceException} if it could not be found.
	 *
	 * @param commerceProductDefinitionId the commerce product definition ID
	 * @param sku the sku
	 * @return the matching commerce product instance
	 * @throws NoSuchProductInstanceException if a matching commerce product instance could not be found
	 */
	@Override
	public CommerceProductInstance findByC_S(long commerceProductDefinitionId,
		String sku) throws NoSuchProductInstanceException {
		CommerceProductInstance commerceProductInstance = fetchByC_S(commerceProductDefinitionId,
				sku);

		if (commerceProductInstance == null) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("commerceProductDefinitionId=");
			msg.append(commerceProductDefinitionId);

			msg.append(", sku=");
			msg.append(sku);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isDebugEnabled()) {
				_log.debug(msg.toString());
			}

			throw new NoSuchProductInstanceException(msg.toString());
		}

		return commerceProductInstance;
	}

	/**
	 * Returns the commerce product instance where commerceProductDefinitionId = &#63; and sku = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param commerceProductDefinitionId the commerce product definition ID
	 * @param sku the sku
	 * @return the matching commerce product instance, or <code>null</code> if a matching commerce product instance could not be found
	 */
	@Override
	public CommerceProductInstance fetchByC_S(
		long commerceProductDefinitionId, String sku) {
		return fetchByC_S(commerceProductDefinitionId, sku, true);
	}

	/**
	 * Returns the commerce product instance where commerceProductDefinitionId = &#63; and sku = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param commerceProductDefinitionId the commerce product definition ID
	 * @param sku the sku
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the matching commerce product instance, or <code>null</code> if a matching commerce product instance could not be found
	 */
	@Override
	public CommerceProductInstance fetchByC_S(
		long commerceProductDefinitionId, String sku, boolean retrieveFromCache) {
		Object[] finderArgs = new Object[] { commerceProductDefinitionId, sku };

		Object result = null;

		if (retrieveFromCache) {
			result = finderCache.getResult(FINDER_PATH_FETCH_BY_C_S,
					finderArgs, this);
		}

		if (result instanceof CommerceProductInstance) {
			CommerceProductInstance commerceProductInstance = (CommerceProductInstance)result;

			if ((commerceProductDefinitionId != commerceProductInstance.getCommerceProductDefinitionId()) ||
					!Objects.equals(sku, commerceProductInstance.getSku())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_SELECT_COMMERCEPRODUCTINSTANCE_WHERE);

			query.append(_FINDER_COLUMN_C_S_COMMERCEPRODUCTDEFINITIONID_2);

			boolean bindSku = false;

			if (sku == null) {
				query.append(_FINDER_COLUMN_C_S_SKU_1);
			}
			else if (sku.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_C_S_SKU_3);
			}
			else {
				bindSku = true;

				query.append(_FINDER_COLUMN_C_S_SKU_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(commerceProductDefinitionId);

				if (bindSku) {
					qPos.add(sku);
				}

				List<CommerceProductInstance> list = q.list();

				if (list.isEmpty()) {
					finderCache.putResult(FINDER_PATH_FETCH_BY_C_S, finderArgs,
						list);
				}
				else {
					CommerceProductInstance commerceProductInstance = list.get(0);

					result = commerceProductInstance;

					cacheResult(commerceProductInstance);

					if ((commerceProductInstance.getCommerceProductDefinitionId() != commerceProductDefinitionId) ||
							(commerceProductInstance.getSku() == null) ||
							!commerceProductInstance.getSku().equals(sku)) {
						finderCache.putResult(FINDER_PATH_FETCH_BY_C_S,
							finderArgs, commerceProductInstance);
					}
				}
			}
			catch (Exception e) {
				finderCache.removeResult(FINDER_PATH_FETCH_BY_C_S, finderArgs);

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
			return (CommerceProductInstance)result;
		}
	}

	/**
	 * Removes the commerce product instance where commerceProductDefinitionId = &#63; and sku = &#63; from the database.
	 *
	 * @param commerceProductDefinitionId the commerce product definition ID
	 * @param sku the sku
	 * @return the commerce product instance that was removed
	 */
	@Override
	public CommerceProductInstance removeByC_S(
		long commerceProductDefinitionId, String sku)
		throws NoSuchProductInstanceException {
		CommerceProductInstance commerceProductInstance = findByC_S(commerceProductDefinitionId,
				sku);

		return remove(commerceProductInstance);
	}

	/**
	 * Returns the number of commerce product instances where commerceProductDefinitionId = &#63; and sku = &#63;.
	 *
	 * @param commerceProductDefinitionId the commerce product definition ID
	 * @param sku the sku
	 * @return the number of matching commerce product instances
	 */
	@Override
	public int countByC_S(long commerceProductDefinitionId, String sku) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_C_S;

		Object[] finderArgs = new Object[] { commerceProductDefinitionId, sku };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_COMMERCEPRODUCTINSTANCE_WHERE);

			query.append(_FINDER_COLUMN_C_S_COMMERCEPRODUCTDEFINITIONID_2);

			boolean bindSku = false;

			if (sku == null) {
				query.append(_FINDER_COLUMN_C_S_SKU_1);
			}
			else if (sku.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_C_S_SKU_3);
			}
			else {
				bindSku = true;

				query.append(_FINDER_COLUMN_C_S_SKU_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(commerceProductDefinitionId);

				if (bindSku) {
					qPos.add(sku);
				}

				count = (Long)q.uniqueResult();

				finderCache.putResult(finderPath, finderArgs, count);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_C_S_COMMERCEPRODUCTDEFINITIONID_2 =
		"commerceProductInstance.commerceProductDefinitionId = ? AND ";
	private static final String _FINDER_COLUMN_C_S_SKU_1 = "commerceProductInstance.sku IS NULL";
	private static final String _FINDER_COLUMN_C_S_SKU_2 = "commerceProductInstance.sku = ?";
	private static final String _FINDER_COLUMN_C_S_SKU_3 = "(commerceProductInstance.sku IS NULL OR commerceProductInstance.sku = '')";

	public CommerceProductInstancePersistenceImpl() {
		setModelClass(CommerceProductInstance.class);
	}

	/**
	 * Caches the commerce product instance in the entity cache if it is enabled.
	 *
	 * @param commerceProductInstance the commerce product instance
	 */
	@Override
	public void cacheResult(CommerceProductInstance commerceProductInstance) {
		entityCache.putResult(CommerceProductInstanceModelImpl.ENTITY_CACHE_ENABLED,
			CommerceProductInstanceImpl.class,
			commerceProductInstance.getPrimaryKey(), commerceProductInstance);

		finderCache.putResult(FINDER_PATH_FETCH_BY_UUID_G,
			new Object[] {
				commerceProductInstance.getUuid(),
				commerceProductInstance.getGroupId()
			}, commerceProductInstance);

		finderCache.putResult(FINDER_PATH_FETCH_BY_C_S,
			new Object[] {
				commerceProductInstance.getCommerceProductDefinitionId(),
				commerceProductInstance.getSku()
			}, commerceProductInstance);

		commerceProductInstance.resetOriginalValues();
	}

	/**
	 * Caches the commerce product instances in the entity cache if it is enabled.
	 *
	 * @param commerceProductInstances the commerce product instances
	 */
	@Override
	public void cacheResult(
		List<CommerceProductInstance> commerceProductInstances) {
		for (CommerceProductInstance commerceProductInstance : commerceProductInstances) {
			if (entityCache.getResult(
						CommerceProductInstanceModelImpl.ENTITY_CACHE_ENABLED,
						CommerceProductInstanceImpl.class,
						commerceProductInstance.getPrimaryKey()) == null) {
				cacheResult(commerceProductInstance);
			}
			else {
				commerceProductInstance.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all commerce product instances.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(CommerceProductInstanceImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the commerce product instance.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(CommerceProductInstance commerceProductInstance) {
		entityCache.removeResult(CommerceProductInstanceModelImpl.ENTITY_CACHE_ENABLED,
			CommerceProductInstanceImpl.class,
			commerceProductInstance.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache((CommerceProductInstanceModelImpl)commerceProductInstance,
			true);
	}

	@Override
	public void clearCache(
		List<CommerceProductInstance> commerceProductInstances) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (CommerceProductInstance commerceProductInstance : commerceProductInstances) {
			entityCache.removeResult(CommerceProductInstanceModelImpl.ENTITY_CACHE_ENABLED,
				CommerceProductInstanceImpl.class,
				commerceProductInstance.getPrimaryKey());

			clearUniqueFindersCache((CommerceProductInstanceModelImpl)commerceProductInstance,
				true);
		}
	}

	protected void cacheUniqueFindersCache(
		CommerceProductInstanceModelImpl commerceProductInstanceModelImpl) {
		Object[] args = new Object[] {
				commerceProductInstanceModelImpl.getUuid(),
				commerceProductInstanceModelImpl.getGroupId()
			};

		finderCache.putResult(FINDER_PATH_COUNT_BY_UUID_G, args,
			Long.valueOf(1), false);
		finderCache.putResult(FINDER_PATH_FETCH_BY_UUID_G, args,
			commerceProductInstanceModelImpl, false);

		args = new Object[] {
				commerceProductInstanceModelImpl.getCommerceProductDefinitionId(),
				commerceProductInstanceModelImpl.getSku()
			};

		finderCache.putResult(FINDER_PATH_COUNT_BY_C_S, args, Long.valueOf(1),
			false);
		finderCache.putResult(FINDER_PATH_FETCH_BY_C_S, args,
			commerceProductInstanceModelImpl, false);
	}

	protected void clearUniqueFindersCache(
		CommerceProductInstanceModelImpl commerceProductInstanceModelImpl,
		boolean clearCurrent) {
		if (clearCurrent) {
			Object[] args = new Object[] {
					commerceProductInstanceModelImpl.getUuid(),
					commerceProductInstanceModelImpl.getGroupId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID_G, args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_UUID_G, args);
		}

		if ((commerceProductInstanceModelImpl.getColumnBitmask() &
				FINDER_PATH_FETCH_BY_UUID_G.getColumnBitmask()) != 0) {
			Object[] args = new Object[] {
					commerceProductInstanceModelImpl.getOriginalUuid(),
					commerceProductInstanceModelImpl.getOriginalGroupId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID_G, args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_UUID_G, args);
		}

		if (clearCurrent) {
			Object[] args = new Object[] {
					commerceProductInstanceModelImpl.getCommerceProductDefinitionId(),
					commerceProductInstanceModelImpl.getSku()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_C_S, args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_C_S, args);
		}

		if ((commerceProductInstanceModelImpl.getColumnBitmask() &
				FINDER_PATH_FETCH_BY_C_S.getColumnBitmask()) != 0) {
			Object[] args = new Object[] {
					commerceProductInstanceModelImpl.getOriginalCommerceProductDefinitionId(),
					commerceProductInstanceModelImpl.getOriginalSku()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_C_S, args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_C_S, args);
		}
	}

	/**
	 * Creates a new commerce product instance with the primary key. Does not add the commerce product instance to the database.
	 *
	 * @param commerceProductInstanceId the primary key for the new commerce product instance
	 * @return the new commerce product instance
	 */
	@Override
	public CommerceProductInstance create(long commerceProductInstanceId) {
		CommerceProductInstance commerceProductInstance = new CommerceProductInstanceImpl();

		commerceProductInstance.setNew(true);
		commerceProductInstance.setPrimaryKey(commerceProductInstanceId);

		String uuid = PortalUUIDUtil.generate();

		commerceProductInstance.setUuid(uuid);

		commerceProductInstance.setCompanyId(companyProvider.getCompanyId());

		return commerceProductInstance;
	}

	/**
	 * Removes the commerce product instance with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param commerceProductInstanceId the primary key of the commerce product instance
	 * @return the commerce product instance that was removed
	 * @throws NoSuchProductInstanceException if a commerce product instance with the primary key could not be found
	 */
	@Override
	public CommerceProductInstance remove(long commerceProductInstanceId)
		throws NoSuchProductInstanceException {
		return remove((Serializable)commerceProductInstanceId);
	}

	/**
	 * Removes the commerce product instance with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the commerce product instance
	 * @return the commerce product instance that was removed
	 * @throws NoSuchProductInstanceException if a commerce product instance with the primary key could not be found
	 */
	@Override
	public CommerceProductInstance remove(Serializable primaryKey)
		throws NoSuchProductInstanceException {
		Session session = null;

		try {
			session = openSession();

			CommerceProductInstance commerceProductInstance = (CommerceProductInstance)session.get(CommerceProductInstanceImpl.class,
					primaryKey);

			if (commerceProductInstance == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchProductInstanceException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(commerceProductInstance);
		}
		catch (NoSuchProductInstanceException nsee) {
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
	protected CommerceProductInstance removeImpl(
		CommerceProductInstance commerceProductInstance) {
		commerceProductInstance = toUnwrappedModel(commerceProductInstance);

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(commerceProductInstance)) {
				commerceProductInstance = (CommerceProductInstance)session.get(CommerceProductInstanceImpl.class,
						commerceProductInstance.getPrimaryKeyObj());
			}

			if (commerceProductInstance != null) {
				session.delete(commerceProductInstance);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (commerceProductInstance != null) {
			clearCache(commerceProductInstance);
		}

		return commerceProductInstance;
	}

	@Override
	public CommerceProductInstance updateImpl(
		CommerceProductInstance commerceProductInstance) {
		commerceProductInstance = toUnwrappedModel(commerceProductInstance);

		boolean isNew = commerceProductInstance.isNew();

		CommerceProductInstanceModelImpl commerceProductInstanceModelImpl = (CommerceProductInstanceModelImpl)commerceProductInstance;

		if (Validator.isNull(commerceProductInstance.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			commerceProductInstance.setUuid(uuid);
		}

		ServiceContext serviceContext = ServiceContextThreadLocal.getServiceContext();

		Date now = new Date();

		if (isNew && (commerceProductInstance.getCreateDate() == null)) {
			if (serviceContext == null) {
				commerceProductInstance.setCreateDate(now);
			}
			else {
				commerceProductInstance.setCreateDate(serviceContext.getCreateDate(
						now));
			}
		}

		if (!commerceProductInstanceModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				commerceProductInstance.setModifiedDate(now);
			}
			else {
				commerceProductInstance.setModifiedDate(serviceContext.getModifiedDate(
						now));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (commerceProductInstance.isNew()) {
				session.save(commerceProductInstance);

				commerceProductInstance.setNew(false);
			}
			else {
				commerceProductInstance = (CommerceProductInstance)session.merge(commerceProductInstance);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (!CommerceProductInstanceModelImpl.COLUMN_BITMASK_ENABLED) {
			finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}
		else
		 if (isNew) {
			Object[] args = new Object[] {
					commerceProductInstanceModelImpl.getUuid()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID, args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID,
				args);

			args = new Object[] {
					commerceProductInstanceModelImpl.getUuid(),
					commerceProductInstanceModelImpl.getCompanyId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID_C, args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C,
				args);

			args = new Object[] { commerceProductInstanceModelImpl.getGroupId() };

			finderCache.removeResult(FINDER_PATH_COUNT_BY_GROUPID, args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID,
				args);

			args = new Object[] { commerceProductInstanceModelImpl.getCompanyId() };

			finderCache.removeResult(FINDER_PATH_COUNT_BY_COMPANYID, args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID,
				args);

			args = new Object[] {
					commerceProductInstanceModelImpl.getCommerceProductDefinitionId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_COMMERCEPRODUCTDEFINITIONID,
				args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMMERCEPRODUCTDEFINITIONID,
				args);

			finderCache.removeResult(FINDER_PATH_COUNT_ALL, FINDER_ARGS_EMPTY);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL,
				FINDER_ARGS_EMPTY);
		}

		else {
			if ((commerceProductInstanceModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						commerceProductInstanceModelImpl.getOriginalUuid()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID,
					args);

				args = new Object[] { commerceProductInstanceModelImpl.getUuid() };

				finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID,
					args);
			}

			if ((commerceProductInstanceModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						commerceProductInstanceModelImpl.getOriginalUuid(),
						commerceProductInstanceModelImpl.getOriginalCompanyId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID_C, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C,
					args);

				args = new Object[] {
						commerceProductInstanceModelImpl.getUuid(),
						commerceProductInstanceModelImpl.getCompanyId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID_C, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C,
					args);
			}

			if ((commerceProductInstanceModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						commerceProductInstanceModelImpl.getOriginalGroupId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_GROUPID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID,
					args);

				args = new Object[] {
						commerceProductInstanceModelImpl.getGroupId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_GROUPID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID,
					args);
			}

			if ((commerceProductInstanceModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						commerceProductInstanceModelImpl.getOriginalCompanyId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_COMPANYID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID,
					args);

				args = new Object[] {
						commerceProductInstanceModelImpl.getCompanyId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_COMPANYID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID,
					args);
			}

			if ((commerceProductInstanceModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMMERCEPRODUCTDEFINITIONID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						commerceProductInstanceModelImpl.getOriginalCommerceProductDefinitionId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_COMMERCEPRODUCTDEFINITIONID,
					args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMMERCEPRODUCTDEFINITIONID,
					args);

				args = new Object[] {
						commerceProductInstanceModelImpl.getCommerceProductDefinitionId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_COMMERCEPRODUCTDEFINITIONID,
					args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMMERCEPRODUCTDEFINITIONID,
					args);
			}
		}

		entityCache.putResult(CommerceProductInstanceModelImpl.ENTITY_CACHE_ENABLED,
			CommerceProductInstanceImpl.class,
			commerceProductInstance.getPrimaryKey(), commerceProductInstance,
			false);

		clearUniqueFindersCache(commerceProductInstanceModelImpl, false);
		cacheUniqueFindersCache(commerceProductInstanceModelImpl);

		commerceProductInstance.resetOriginalValues();

		return commerceProductInstance;
	}

	protected CommerceProductInstance toUnwrappedModel(
		CommerceProductInstance commerceProductInstance) {
		if (commerceProductInstance instanceof CommerceProductInstanceImpl) {
			return commerceProductInstance;
		}

		CommerceProductInstanceImpl commerceProductInstanceImpl = new CommerceProductInstanceImpl();

		commerceProductInstanceImpl.setNew(commerceProductInstance.isNew());
		commerceProductInstanceImpl.setPrimaryKey(commerceProductInstance.getPrimaryKey());

		commerceProductInstanceImpl.setUuid(commerceProductInstance.getUuid());
		commerceProductInstanceImpl.setCommerceProductInstanceId(commerceProductInstance.getCommerceProductInstanceId());
		commerceProductInstanceImpl.setGroupId(commerceProductInstance.getGroupId());
		commerceProductInstanceImpl.setCompanyId(commerceProductInstance.getCompanyId());
		commerceProductInstanceImpl.setUserId(commerceProductInstance.getUserId());
		commerceProductInstanceImpl.setUserName(commerceProductInstance.getUserName());
		commerceProductInstanceImpl.setCreateDate(commerceProductInstance.getCreateDate());
		commerceProductInstanceImpl.setModifiedDate(commerceProductInstance.getModifiedDate());
		commerceProductInstanceImpl.setCommerceProductDefinitionId(commerceProductInstance.getCommerceProductDefinitionId());
		commerceProductInstanceImpl.setSku(commerceProductInstance.getSku());
		commerceProductInstanceImpl.setLSIN(commerceProductInstance.getLSIN());
		commerceProductInstanceImpl.setDDMContent(commerceProductInstance.getDDMContent());
		commerceProductInstanceImpl.setDisplayDate(commerceProductInstance.getDisplayDate());
		commerceProductInstanceImpl.setExpirationDate(commerceProductInstance.getExpirationDate());
		commerceProductInstanceImpl.setLastPublishDate(commerceProductInstance.getLastPublishDate());
		commerceProductInstanceImpl.setStatus(commerceProductInstance.getStatus());
		commerceProductInstanceImpl.setStatusByUserId(commerceProductInstance.getStatusByUserId());
		commerceProductInstanceImpl.setStatusByUserName(commerceProductInstance.getStatusByUserName());
		commerceProductInstanceImpl.setStatusDate(commerceProductInstance.getStatusDate());

		return commerceProductInstanceImpl;
	}

	/**
	 * Returns the commerce product instance with the primary key or throws a {@link com.liferay.portal.kernel.exception.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the commerce product instance
	 * @return the commerce product instance
	 * @throws NoSuchProductInstanceException if a commerce product instance with the primary key could not be found
	 */
	@Override
	public CommerceProductInstance findByPrimaryKey(Serializable primaryKey)
		throws NoSuchProductInstanceException {
		CommerceProductInstance commerceProductInstance = fetchByPrimaryKey(primaryKey);

		if (commerceProductInstance == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchProductInstanceException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				primaryKey);
		}

		return commerceProductInstance;
	}

	/**
	 * Returns the commerce product instance with the primary key or throws a {@link NoSuchProductInstanceException} if it could not be found.
	 *
	 * @param commerceProductInstanceId the primary key of the commerce product instance
	 * @return the commerce product instance
	 * @throws NoSuchProductInstanceException if a commerce product instance with the primary key could not be found
	 */
	@Override
	public CommerceProductInstance findByPrimaryKey(
		long commerceProductInstanceId) throws NoSuchProductInstanceException {
		return findByPrimaryKey((Serializable)commerceProductInstanceId);
	}

	/**
	 * Returns the commerce product instance with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the commerce product instance
	 * @return the commerce product instance, or <code>null</code> if a commerce product instance with the primary key could not be found
	 */
	@Override
	public CommerceProductInstance fetchByPrimaryKey(Serializable primaryKey) {
		Serializable serializable = entityCache.getResult(CommerceProductInstanceModelImpl.ENTITY_CACHE_ENABLED,
				CommerceProductInstanceImpl.class, primaryKey);

		if (serializable == nullModel) {
			return null;
		}

		CommerceProductInstance commerceProductInstance = (CommerceProductInstance)serializable;

		if (commerceProductInstance == null) {
			Session session = null;

			try {
				session = openSession();

				commerceProductInstance = (CommerceProductInstance)session.get(CommerceProductInstanceImpl.class,
						primaryKey);

				if (commerceProductInstance != null) {
					cacheResult(commerceProductInstance);
				}
				else {
					entityCache.putResult(CommerceProductInstanceModelImpl.ENTITY_CACHE_ENABLED,
						CommerceProductInstanceImpl.class, primaryKey, nullModel);
				}
			}
			catch (Exception e) {
				entityCache.removeResult(CommerceProductInstanceModelImpl.ENTITY_CACHE_ENABLED,
					CommerceProductInstanceImpl.class, primaryKey);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return commerceProductInstance;
	}

	/**
	 * Returns the commerce product instance with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param commerceProductInstanceId the primary key of the commerce product instance
	 * @return the commerce product instance, or <code>null</code> if a commerce product instance with the primary key could not be found
	 */
	@Override
	public CommerceProductInstance fetchByPrimaryKey(
		long commerceProductInstanceId) {
		return fetchByPrimaryKey((Serializable)commerceProductInstanceId);
	}

	@Override
	public Map<Serializable, CommerceProductInstance> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {
		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, CommerceProductInstance> map = new HashMap<Serializable, CommerceProductInstance>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			CommerceProductInstance commerceProductInstance = fetchByPrimaryKey(primaryKey);

			if (commerceProductInstance != null) {
				map.put(primaryKey, commerceProductInstance);
			}

			return map;
		}

		Set<Serializable> uncachedPrimaryKeys = null;

		for (Serializable primaryKey : primaryKeys) {
			Serializable serializable = entityCache.getResult(CommerceProductInstanceModelImpl.ENTITY_CACHE_ENABLED,
					CommerceProductInstanceImpl.class, primaryKey);

			if (serializable != nullModel) {
				if (serializable == null) {
					if (uncachedPrimaryKeys == null) {
						uncachedPrimaryKeys = new HashSet<Serializable>();
					}

					uncachedPrimaryKeys.add(primaryKey);
				}
				else {
					map.put(primaryKey, (CommerceProductInstance)serializable);
				}
			}
		}

		if (uncachedPrimaryKeys == null) {
			return map;
		}

		StringBundler query = new StringBundler((uncachedPrimaryKeys.size() * 2) +
				1);

		query.append(_SQL_SELECT_COMMERCEPRODUCTINSTANCE_WHERE_PKS_IN);

		for (Serializable primaryKey : uncachedPrimaryKeys) {
			query.append((long)primaryKey);

			query.append(StringPool.COMMA);
		}

		query.setIndex(query.index() - 1);

		query.append(StringPool.CLOSE_PARENTHESIS);

		String sql = query.toString();

		Session session = null;

		try {
			session = openSession();

			Query q = session.createQuery(sql);

			for (CommerceProductInstance commerceProductInstance : (List<CommerceProductInstance>)q.list()) {
				map.put(commerceProductInstance.getPrimaryKeyObj(),
					commerceProductInstance);

				cacheResult(commerceProductInstance);

				uncachedPrimaryKeys.remove(commerceProductInstance.getPrimaryKeyObj());
			}

			for (Serializable primaryKey : uncachedPrimaryKeys) {
				entityCache.putResult(CommerceProductInstanceModelImpl.ENTITY_CACHE_ENABLED,
					CommerceProductInstanceImpl.class, primaryKey, nullModel);
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
	 * Returns all the commerce product instances.
	 *
	 * @return the commerce product instances
	 */
	@Override
	public List<CommerceProductInstance> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce product instances.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceProductInstanceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce product instances
	 * @param end the upper bound of the range of commerce product instances (not inclusive)
	 * @return the range of commerce product instances
	 */
	@Override
	public List<CommerceProductInstance> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce product instances.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceProductInstanceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce product instances
	 * @param end the upper bound of the range of commerce product instances (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of commerce product instances
	 */
	@Override
	public List<CommerceProductInstance> findAll(int start, int end,
		OrderByComparator<CommerceProductInstance> orderByComparator) {
		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce product instances.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceProductInstanceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce product instances
	 * @param end the upper bound of the range of commerce product instances (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of commerce product instances
	 */
	@Override
	public List<CommerceProductInstance> findAll(int start, int end,
		OrderByComparator<CommerceProductInstance> orderByComparator,
		boolean retrieveFromCache) {
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

		List<CommerceProductInstance> list = null;

		if (retrieveFromCache) {
			list = (List<CommerceProductInstance>)finderCache.getResult(finderPath,
					finderArgs, this);
		}

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 2));

				query.append(_SQL_SELECT_COMMERCEPRODUCTINSTANCE);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_COMMERCEPRODUCTINSTANCE;

				if (pagination) {
					sql = sql.concat(CommerceProductInstanceModelImpl.ORDER_BY_JPQL);
				}
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (!pagination) {
					list = (List<CommerceProductInstance>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<CommerceProductInstance>)QueryUtil.list(q,
							getDialect(), start, end);
				}

				cacheResult(list);

				finderCache.putResult(finderPath, finderArgs, list);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Removes all the commerce product instances from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (CommerceProductInstance commerceProductInstance : findAll()) {
			remove(commerceProductInstance);
		}
	}

	/**
	 * Returns the number of commerce product instances.
	 *
	 * @return the number of commerce product instances
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_COMMERCEPRODUCTINSTANCE);

				count = (Long)q.uniqueResult();

				finderCache.putResult(FINDER_PATH_COUNT_ALL, FINDER_ARGS_EMPTY,
					count);
			}
			catch (Exception e) {
				finderCache.removeResult(FINDER_PATH_COUNT_ALL,
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
	public Set<String> getBadColumnNames() {
		return _badColumnNames;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return CommerceProductInstanceModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the commerce product instance persistence.
	 */
	public void afterPropertiesSet() {
	}

	public void destroy() {
		entityCache.removeCache(CommerceProductInstanceImpl.class.getName());
		finderCache.removeCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@ServiceReference(type = CompanyProviderWrapper.class)
	protected CompanyProvider companyProvider;
	@ServiceReference(type = EntityCache.class)
	protected EntityCache entityCache;
	@ServiceReference(type = FinderCache.class)
	protected FinderCache finderCache;
	private static final String _SQL_SELECT_COMMERCEPRODUCTINSTANCE = "SELECT commerceProductInstance FROM CommerceProductInstance commerceProductInstance";
	private static final String _SQL_SELECT_COMMERCEPRODUCTINSTANCE_WHERE_PKS_IN =
		"SELECT commerceProductInstance FROM CommerceProductInstance commerceProductInstance WHERE commerceProductInstanceId IN (";
	private static final String _SQL_SELECT_COMMERCEPRODUCTINSTANCE_WHERE = "SELECT commerceProductInstance FROM CommerceProductInstance commerceProductInstance WHERE ";
	private static final String _SQL_COUNT_COMMERCEPRODUCTINSTANCE = "SELECT COUNT(commerceProductInstance) FROM CommerceProductInstance commerceProductInstance";
	private static final String _SQL_COUNT_COMMERCEPRODUCTINSTANCE_WHERE = "SELECT COUNT(commerceProductInstance) FROM CommerceProductInstance commerceProductInstance WHERE ";
	private static final String _ORDER_BY_ENTITY_ALIAS = "commerceProductInstance.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No CommerceProductInstance exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No CommerceProductInstance exists with the key {";
	private static final Log _log = LogFactoryUtil.getLog(CommerceProductInstancePersistenceImpl.class);
	private static final Set<String> _badColumnNames = SetUtil.fromArray(new String[] {
				"uuid"
			});
}