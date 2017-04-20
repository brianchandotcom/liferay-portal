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

import com.liferay.commerce.product.exception.NoSuchProductOptionValueException;
import com.liferay.commerce.product.model.CommerceProductOptionValue;
import com.liferay.commerce.product.model.impl.CommerceProductOptionValueImpl;
import com.liferay.commerce.product.model.impl.CommerceProductOptionValueModelImpl;
import com.liferay.commerce.product.service.persistence.CommerceProductOptionValuePersistence;

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
import com.liferay.portal.kernel.util.ReflectionUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;
import com.liferay.portal.spring.extender.service.ServiceReference;

import java.io.Serializable;

import java.lang.reflect.Field;

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
 * The persistence implementation for the commerce product option value service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Marco Leo
 * @see CommerceProductOptionValuePersistence
 * @see com.liferay.commerce.product.service.persistence.CommerceProductOptionValueUtil
 * @generated
 */
@ProviderType
public class CommerceProductOptionValuePersistenceImpl
	extends BasePersistenceImpl<CommerceProductOptionValue>
	implements CommerceProductOptionValuePersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link CommerceProductOptionValueUtil} to access the commerce product option value persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = CommerceProductOptionValueImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(CommerceProductOptionValueModelImpl.ENTITY_CACHE_ENABLED,
			CommerceProductOptionValueModelImpl.FINDER_CACHE_ENABLED,
			CommerceProductOptionValueImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(CommerceProductOptionValueModelImpl.ENTITY_CACHE_ENABLED,
			CommerceProductOptionValueModelImpl.FINDER_CACHE_ENABLED,
			CommerceProductOptionValueImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(CommerceProductOptionValueModelImpl.ENTITY_CACHE_ENABLED,
			CommerceProductOptionValueModelImpl.FINDER_CACHE_ENABLED,
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll",
			new String[0]);
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_UUID = new FinderPath(CommerceProductOptionValueModelImpl.ENTITY_CACHE_ENABLED,
			CommerceProductOptionValueModelImpl.FINDER_CACHE_ENABLED,
			CommerceProductOptionValueImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUuid",
			new String[] {
				String.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID = new FinderPath(CommerceProductOptionValueModelImpl.ENTITY_CACHE_ENABLED,
			CommerceProductOptionValueModelImpl.FINDER_CACHE_ENABLED,
			CommerceProductOptionValueImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid",
			new String[] { String.class.getName() },
			CommerceProductOptionValueModelImpl.UUID_COLUMN_BITMASK |
			CommerceProductOptionValueModelImpl.TITLE_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID = new FinderPath(CommerceProductOptionValueModelImpl.ENTITY_CACHE_ENABLED,
			CommerceProductOptionValueModelImpl.FINDER_CACHE_ENABLED,
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByUuid", new String[] { String.class.getName() });

	/**
	 * Returns all the commerce product option values where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching commerce product option values
	 */
	@Override
	public List<CommerceProductOptionValue> findByUuid(String uuid) {
		return findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce product option values where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceProductOptionValueModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of commerce product option values
	 * @param end the upper bound of the range of commerce product option values (not inclusive)
	 * @return the range of matching commerce product option values
	 */
	@Override
	public List<CommerceProductOptionValue> findByUuid(String uuid, int start,
		int end) {
		return findByUuid(uuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce product option values where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceProductOptionValueModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of commerce product option values
	 * @param end the upper bound of the range of commerce product option values (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce product option values
	 */
	@Override
	public List<CommerceProductOptionValue> findByUuid(String uuid, int start,
		int end, OrderByComparator<CommerceProductOptionValue> orderByComparator) {
		return findByUuid(uuid, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce product option values where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceProductOptionValueModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of commerce product option values
	 * @param end the upper bound of the range of commerce product option values (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching commerce product option values
	 */
	@Override
	public List<CommerceProductOptionValue> findByUuid(String uuid, int start,
		int end,
		OrderByComparator<CommerceProductOptionValue> orderByComparator,
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

		List<CommerceProductOptionValue> list = null;

		if (retrieveFromCache) {
			list = (List<CommerceProductOptionValue>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (CommerceProductOptionValue commerceProductOptionValue : list) {
					if (!Objects.equals(uuid,
								commerceProductOptionValue.getUuid())) {
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

			query.append(_SQL_SELECT_COMMERCEPRODUCTOPTIONVALUE_WHERE);

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
				query.append(CommerceProductOptionValueModelImpl.ORDER_BY_JPQL);
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
					list = (List<CommerceProductOptionValue>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<CommerceProductOptionValue>)QueryUtil.list(q,
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
	 * Returns the first commerce product option value in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce product option value
	 * @throws NoSuchProductOptionValueException if a matching commerce product option value could not be found
	 */
	@Override
	public CommerceProductOptionValue findByUuid_First(String uuid,
		OrderByComparator<CommerceProductOptionValue> orderByComparator)
		throws NoSuchProductOptionValueException {
		CommerceProductOptionValue commerceProductOptionValue = fetchByUuid_First(uuid,
				orderByComparator);

		if (commerceProductOptionValue != null) {
			return commerceProductOptionValue;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchProductOptionValueException(msg.toString());
	}

	/**
	 * Returns the first commerce product option value in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce product option value, or <code>null</code> if a matching commerce product option value could not be found
	 */
	@Override
	public CommerceProductOptionValue fetchByUuid_First(String uuid,
		OrderByComparator<CommerceProductOptionValue> orderByComparator) {
		List<CommerceProductOptionValue> list = findByUuid(uuid, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last commerce product option value in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce product option value
	 * @throws NoSuchProductOptionValueException if a matching commerce product option value could not be found
	 */
	@Override
	public CommerceProductOptionValue findByUuid_Last(String uuid,
		OrderByComparator<CommerceProductOptionValue> orderByComparator)
		throws NoSuchProductOptionValueException {
		CommerceProductOptionValue commerceProductOptionValue = fetchByUuid_Last(uuid,
				orderByComparator);

		if (commerceProductOptionValue != null) {
			return commerceProductOptionValue;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchProductOptionValueException(msg.toString());
	}

	/**
	 * Returns the last commerce product option value in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce product option value, or <code>null</code> if a matching commerce product option value could not be found
	 */
	@Override
	public CommerceProductOptionValue fetchByUuid_Last(String uuid,
		OrderByComparator<CommerceProductOptionValue> orderByComparator) {
		int count = countByUuid(uuid);

		if (count == 0) {
			return null;
		}

		List<CommerceProductOptionValue> list = findByUuid(uuid, count - 1,
				count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the commerce product option values before and after the current commerce product option value in the ordered set where uuid = &#63;.
	 *
	 * @param commerceProductOptionValueId the primary key of the current commerce product option value
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce product option value
	 * @throws NoSuchProductOptionValueException if a commerce product option value with the primary key could not be found
	 */
	@Override
	public CommerceProductOptionValue[] findByUuid_PrevAndNext(
		long commerceProductOptionValueId, String uuid,
		OrderByComparator<CommerceProductOptionValue> orderByComparator)
		throws NoSuchProductOptionValueException {
		CommerceProductOptionValue commerceProductOptionValue = findByPrimaryKey(commerceProductOptionValueId);

		Session session = null;

		try {
			session = openSession();

			CommerceProductOptionValue[] array = new CommerceProductOptionValueImpl[3];

			array[0] = getByUuid_PrevAndNext(session,
					commerceProductOptionValue, uuid, orderByComparator, true);

			array[1] = commerceProductOptionValue;

			array[2] = getByUuid_PrevAndNext(session,
					commerceProductOptionValue, uuid, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected CommerceProductOptionValue getByUuid_PrevAndNext(
		Session session, CommerceProductOptionValue commerceProductOptionValue,
		String uuid,
		OrderByComparator<CommerceProductOptionValue> orderByComparator,
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

		query.append(_SQL_SELECT_COMMERCEPRODUCTOPTIONVALUE_WHERE);

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
			query.append(CommerceProductOptionValueModelImpl.ORDER_BY_JPQL);
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
			Object[] values = orderByComparator.getOrderByConditionValues(commerceProductOptionValue);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<CommerceProductOptionValue> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the commerce product option values where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	@Override
	public void removeByUuid(String uuid) {
		for (CommerceProductOptionValue commerceProductOptionValue : findByUuid(
				uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(commerceProductOptionValue);
		}
	}

	/**
	 * Returns the number of commerce product option values where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching commerce product option values
	 */
	@Override
	public int countByUuid(String uuid) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_UUID;

		Object[] finderArgs = new Object[] { uuid };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_COMMERCEPRODUCTOPTIONVALUE_WHERE);

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

	private static final String _FINDER_COLUMN_UUID_UUID_1 = "commerceProductOptionValue.uuid IS NULL";
	private static final String _FINDER_COLUMN_UUID_UUID_2 = "commerceProductOptionValue.uuid = ?";
	private static final String _FINDER_COLUMN_UUID_UUID_3 = "(commerceProductOptionValue.uuid IS NULL OR commerceProductOptionValue.uuid = '')";
	public static final FinderPath FINDER_PATH_FETCH_BY_UUID_G = new FinderPath(CommerceProductOptionValueModelImpl.ENTITY_CACHE_ENABLED,
			CommerceProductOptionValueModelImpl.FINDER_CACHE_ENABLED,
			CommerceProductOptionValueImpl.class, FINDER_CLASS_NAME_ENTITY,
			"fetchByUUID_G",
			new String[] { String.class.getName(), Long.class.getName() },
			CommerceProductOptionValueModelImpl.UUID_COLUMN_BITMASK |
			CommerceProductOptionValueModelImpl.GROUPID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID_G = new FinderPath(CommerceProductOptionValueModelImpl.ENTITY_CACHE_ENABLED,
			CommerceProductOptionValueModelImpl.FINDER_CACHE_ENABLED,
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByUUID_G",
			new String[] { String.class.getName(), Long.class.getName() });

	/**
	 * Returns the commerce product option value where uuid = &#63; and groupId = &#63; or throws a {@link NoSuchProductOptionValueException} if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching commerce product option value
	 * @throws NoSuchProductOptionValueException if a matching commerce product option value could not be found
	 */
	@Override
	public CommerceProductOptionValue findByUUID_G(String uuid, long groupId)
		throws NoSuchProductOptionValueException {
		CommerceProductOptionValue commerceProductOptionValue = fetchByUUID_G(uuid,
				groupId);

		if (commerceProductOptionValue == null) {
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

			throw new NoSuchProductOptionValueException(msg.toString());
		}

		return commerceProductOptionValue;
	}

	/**
	 * Returns the commerce product option value where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching commerce product option value, or <code>null</code> if a matching commerce product option value could not be found
	 */
	@Override
	public CommerceProductOptionValue fetchByUUID_G(String uuid, long groupId) {
		return fetchByUUID_G(uuid, groupId, true);
	}

	/**
	 * Returns the commerce product option value where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the matching commerce product option value, or <code>null</code> if a matching commerce product option value could not be found
	 */
	@Override
	public CommerceProductOptionValue fetchByUUID_G(String uuid, long groupId,
		boolean retrieveFromCache) {
		Object[] finderArgs = new Object[] { uuid, groupId };

		Object result = null;

		if (retrieveFromCache) {
			result = finderCache.getResult(FINDER_PATH_FETCH_BY_UUID_G,
					finderArgs, this);
		}

		if (result instanceof CommerceProductOptionValue) {
			CommerceProductOptionValue commerceProductOptionValue = (CommerceProductOptionValue)result;

			if (!Objects.equals(uuid, commerceProductOptionValue.getUuid()) ||
					(groupId != commerceProductOptionValue.getGroupId())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_SELECT_COMMERCEPRODUCTOPTIONVALUE_WHERE);

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

				List<CommerceProductOptionValue> list = q.list();

				if (list.isEmpty()) {
					finderCache.putResult(FINDER_PATH_FETCH_BY_UUID_G,
						finderArgs, list);
				}
				else {
					CommerceProductOptionValue commerceProductOptionValue = list.get(0);

					result = commerceProductOptionValue;

					cacheResult(commerceProductOptionValue);

					if ((commerceProductOptionValue.getUuid() == null) ||
							!commerceProductOptionValue.getUuid().equals(uuid) ||
							(commerceProductOptionValue.getGroupId() != groupId)) {
						finderCache.putResult(FINDER_PATH_FETCH_BY_UUID_G,
							finderArgs, commerceProductOptionValue);
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
			return (CommerceProductOptionValue)result;
		}
	}

	/**
	 * Removes the commerce product option value where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the commerce product option value that was removed
	 */
	@Override
	public CommerceProductOptionValue removeByUUID_G(String uuid, long groupId)
		throws NoSuchProductOptionValueException {
		CommerceProductOptionValue commerceProductOptionValue = findByUUID_G(uuid,
				groupId);

		return remove(commerceProductOptionValue);
	}

	/**
	 * Returns the number of commerce product option values where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching commerce product option values
	 */
	@Override
	public int countByUUID_G(String uuid, long groupId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_UUID_G;

		Object[] finderArgs = new Object[] { uuid, groupId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_COMMERCEPRODUCTOPTIONVALUE_WHERE);

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

	private static final String _FINDER_COLUMN_UUID_G_UUID_1 = "commerceProductOptionValue.uuid IS NULL AND ";
	private static final String _FINDER_COLUMN_UUID_G_UUID_2 = "commerceProductOptionValue.uuid = ? AND ";
	private static final String _FINDER_COLUMN_UUID_G_UUID_3 = "(commerceProductOptionValue.uuid IS NULL OR commerceProductOptionValue.uuid = '') AND ";
	private static final String _FINDER_COLUMN_UUID_G_GROUPID_2 = "commerceProductOptionValue.groupId = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_UUID_C = new FinderPath(CommerceProductOptionValueModelImpl.ENTITY_CACHE_ENABLED,
			CommerceProductOptionValueModelImpl.FINDER_CACHE_ENABLED,
			CommerceProductOptionValueImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUuid_C",
			new String[] {
				String.class.getName(), Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C =
		new FinderPath(CommerceProductOptionValueModelImpl.ENTITY_CACHE_ENABLED,
			CommerceProductOptionValueModelImpl.FINDER_CACHE_ENABLED,
			CommerceProductOptionValueImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid_C",
			new String[] { String.class.getName(), Long.class.getName() },
			CommerceProductOptionValueModelImpl.UUID_COLUMN_BITMASK |
			CommerceProductOptionValueModelImpl.COMPANYID_COLUMN_BITMASK |
			CommerceProductOptionValueModelImpl.TITLE_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID_C = new FinderPath(CommerceProductOptionValueModelImpl.ENTITY_CACHE_ENABLED,
			CommerceProductOptionValueModelImpl.FINDER_CACHE_ENABLED,
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByUuid_C",
			new String[] { String.class.getName(), Long.class.getName() });

	/**
	 * Returns all the commerce product option values where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching commerce product option values
	 */
	@Override
	public List<CommerceProductOptionValue> findByUuid_C(String uuid,
		long companyId) {
		return findByUuid_C(uuid, companyId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce product option values where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceProductOptionValueModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce product option values
	 * @param end the upper bound of the range of commerce product option values (not inclusive)
	 * @return the range of matching commerce product option values
	 */
	@Override
	public List<CommerceProductOptionValue> findByUuid_C(String uuid,
		long companyId, int start, int end) {
		return findByUuid_C(uuid, companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce product option values where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceProductOptionValueModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce product option values
	 * @param end the upper bound of the range of commerce product option values (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce product option values
	 */
	@Override
	public List<CommerceProductOptionValue> findByUuid_C(String uuid,
		long companyId, int start, int end,
		OrderByComparator<CommerceProductOptionValue> orderByComparator) {
		return findByUuid_C(uuid, companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce product option values where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceProductOptionValueModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce product option values
	 * @param end the upper bound of the range of commerce product option values (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching commerce product option values
	 */
	@Override
	public List<CommerceProductOptionValue> findByUuid_C(String uuid,
		long companyId, int start, int end,
		OrderByComparator<CommerceProductOptionValue> orderByComparator,
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

		List<CommerceProductOptionValue> list = null;

		if (retrieveFromCache) {
			list = (List<CommerceProductOptionValue>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (CommerceProductOptionValue commerceProductOptionValue : list) {
					if (!Objects.equals(uuid,
								commerceProductOptionValue.getUuid()) ||
							(companyId != commerceProductOptionValue.getCompanyId())) {
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

			query.append(_SQL_SELECT_COMMERCEPRODUCTOPTIONVALUE_WHERE);

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
				query.append(CommerceProductOptionValueModelImpl.ORDER_BY_JPQL);
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
					list = (List<CommerceProductOptionValue>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<CommerceProductOptionValue>)QueryUtil.list(q,
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
	 * Returns the first commerce product option value in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce product option value
	 * @throws NoSuchProductOptionValueException if a matching commerce product option value could not be found
	 */
	@Override
	public CommerceProductOptionValue findByUuid_C_First(String uuid,
		long companyId,
		OrderByComparator<CommerceProductOptionValue> orderByComparator)
		throws NoSuchProductOptionValueException {
		CommerceProductOptionValue commerceProductOptionValue = fetchByUuid_C_First(uuid,
				companyId, orderByComparator);

		if (commerceProductOptionValue != null) {
			return commerceProductOptionValue;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(", companyId=");
		msg.append(companyId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchProductOptionValueException(msg.toString());
	}

	/**
	 * Returns the first commerce product option value in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce product option value, or <code>null</code> if a matching commerce product option value could not be found
	 */
	@Override
	public CommerceProductOptionValue fetchByUuid_C_First(String uuid,
		long companyId,
		OrderByComparator<CommerceProductOptionValue> orderByComparator) {
		List<CommerceProductOptionValue> list = findByUuid_C(uuid, companyId,
				0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last commerce product option value in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce product option value
	 * @throws NoSuchProductOptionValueException if a matching commerce product option value could not be found
	 */
	@Override
	public CommerceProductOptionValue findByUuid_C_Last(String uuid,
		long companyId,
		OrderByComparator<CommerceProductOptionValue> orderByComparator)
		throws NoSuchProductOptionValueException {
		CommerceProductOptionValue commerceProductOptionValue = fetchByUuid_C_Last(uuid,
				companyId, orderByComparator);

		if (commerceProductOptionValue != null) {
			return commerceProductOptionValue;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(", companyId=");
		msg.append(companyId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchProductOptionValueException(msg.toString());
	}

	/**
	 * Returns the last commerce product option value in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce product option value, or <code>null</code> if a matching commerce product option value could not be found
	 */
	@Override
	public CommerceProductOptionValue fetchByUuid_C_Last(String uuid,
		long companyId,
		OrderByComparator<CommerceProductOptionValue> orderByComparator) {
		int count = countByUuid_C(uuid, companyId);

		if (count == 0) {
			return null;
		}

		List<CommerceProductOptionValue> list = findByUuid_C(uuid, companyId,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the commerce product option values before and after the current commerce product option value in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param commerceProductOptionValueId the primary key of the current commerce product option value
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce product option value
	 * @throws NoSuchProductOptionValueException if a commerce product option value with the primary key could not be found
	 */
	@Override
	public CommerceProductOptionValue[] findByUuid_C_PrevAndNext(
		long commerceProductOptionValueId, String uuid, long companyId,
		OrderByComparator<CommerceProductOptionValue> orderByComparator)
		throws NoSuchProductOptionValueException {
		CommerceProductOptionValue commerceProductOptionValue = findByPrimaryKey(commerceProductOptionValueId);

		Session session = null;

		try {
			session = openSession();

			CommerceProductOptionValue[] array = new CommerceProductOptionValueImpl[3];

			array[0] = getByUuid_C_PrevAndNext(session,
					commerceProductOptionValue, uuid, companyId,
					orderByComparator, true);

			array[1] = commerceProductOptionValue;

			array[2] = getByUuid_C_PrevAndNext(session,
					commerceProductOptionValue, uuid, companyId,
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

	protected CommerceProductOptionValue getByUuid_C_PrevAndNext(
		Session session, CommerceProductOptionValue commerceProductOptionValue,
		String uuid, long companyId,
		OrderByComparator<CommerceProductOptionValue> orderByComparator,
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

		query.append(_SQL_SELECT_COMMERCEPRODUCTOPTIONVALUE_WHERE);

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
			query.append(CommerceProductOptionValueModelImpl.ORDER_BY_JPQL);
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
			Object[] values = orderByComparator.getOrderByConditionValues(commerceProductOptionValue);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<CommerceProductOptionValue> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the commerce product option values where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	@Override
	public void removeByUuid_C(String uuid, long companyId) {
		for (CommerceProductOptionValue commerceProductOptionValue : findByUuid_C(
				uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(commerceProductOptionValue);
		}
	}

	/**
	 * Returns the number of commerce product option values where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching commerce product option values
	 */
	@Override
	public int countByUuid_C(String uuid, long companyId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_UUID_C;

		Object[] finderArgs = new Object[] { uuid, companyId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_COMMERCEPRODUCTOPTIONVALUE_WHERE);

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

	private static final String _FINDER_COLUMN_UUID_C_UUID_1 = "commerceProductOptionValue.uuid IS NULL AND ";
	private static final String _FINDER_COLUMN_UUID_C_UUID_2 = "commerceProductOptionValue.uuid = ? AND ";
	private static final String _FINDER_COLUMN_UUID_C_UUID_3 = "(commerceProductOptionValue.uuid IS NULL OR commerceProductOptionValue.uuid = '') AND ";
	private static final String _FINDER_COLUMN_UUID_C_COMPANYID_2 = "commerceProductOptionValue.companyId = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_GROUPID = new FinderPath(CommerceProductOptionValueModelImpl.ENTITY_CACHE_ENABLED,
			CommerceProductOptionValueModelImpl.FINDER_CACHE_ENABLED,
			CommerceProductOptionValueImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByGroupId",
			new String[] {
				Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID =
		new FinderPath(CommerceProductOptionValueModelImpl.ENTITY_CACHE_ENABLED,
			CommerceProductOptionValueModelImpl.FINDER_CACHE_ENABLED,
			CommerceProductOptionValueImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByGroupId",
			new String[] { Long.class.getName() },
			CommerceProductOptionValueModelImpl.GROUPID_COLUMN_BITMASK |
			CommerceProductOptionValueModelImpl.TITLE_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_GROUPID = new FinderPath(CommerceProductOptionValueModelImpl.ENTITY_CACHE_ENABLED,
			CommerceProductOptionValueModelImpl.FINDER_CACHE_ENABLED,
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByGroupId", new String[] { Long.class.getName() });

	/**
	 * Returns all the commerce product option values where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching commerce product option values
	 */
	@Override
	public List<CommerceProductOptionValue> findByGroupId(long groupId) {
		return findByGroupId(groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce product option values where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceProductOptionValueModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of commerce product option values
	 * @param end the upper bound of the range of commerce product option values (not inclusive)
	 * @return the range of matching commerce product option values
	 */
	@Override
	public List<CommerceProductOptionValue> findByGroupId(long groupId,
		int start, int end) {
		return findByGroupId(groupId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce product option values where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceProductOptionValueModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of commerce product option values
	 * @param end the upper bound of the range of commerce product option values (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce product option values
	 */
	@Override
	public List<CommerceProductOptionValue> findByGroupId(long groupId,
		int start, int end,
		OrderByComparator<CommerceProductOptionValue> orderByComparator) {
		return findByGroupId(groupId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce product option values where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceProductOptionValueModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of commerce product option values
	 * @param end the upper bound of the range of commerce product option values (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching commerce product option values
	 */
	@Override
	public List<CommerceProductOptionValue> findByGroupId(long groupId,
		int start, int end,
		OrderByComparator<CommerceProductOptionValue> orderByComparator,
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

		List<CommerceProductOptionValue> list = null;

		if (retrieveFromCache) {
			list = (List<CommerceProductOptionValue>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (CommerceProductOptionValue commerceProductOptionValue : list) {
					if ((groupId != commerceProductOptionValue.getGroupId())) {
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

			query.append(_SQL_SELECT_COMMERCEPRODUCTOPTIONVALUE_WHERE);

			query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(CommerceProductOptionValueModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (!pagination) {
					list = (List<CommerceProductOptionValue>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<CommerceProductOptionValue>)QueryUtil.list(q,
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
	 * Returns the first commerce product option value in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce product option value
	 * @throws NoSuchProductOptionValueException if a matching commerce product option value could not be found
	 */
	@Override
	public CommerceProductOptionValue findByGroupId_First(long groupId,
		OrderByComparator<CommerceProductOptionValue> orderByComparator)
		throws NoSuchProductOptionValueException {
		CommerceProductOptionValue commerceProductOptionValue = fetchByGroupId_First(groupId,
				orderByComparator);

		if (commerceProductOptionValue != null) {
			return commerceProductOptionValue;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchProductOptionValueException(msg.toString());
	}

	/**
	 * Returns the first commerce product option value in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce product option value, or <code>null</code> if a matching commerce product option value could not be found
	 */
	@Override
	public CommerceProductOptionValue fetchByGroupId_First(long groupId,
		OrderByComparator<CommerceProductOptionValue> orderByComparator) {
		List<CommerceProductOptionValue> list = findByGroupId(groupId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last commerce product option value in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce product option value
	 * @throws NoSuchProductOptionValueException if a matching commerce product option value could not be found
	 */
	@Override
	public CommerceProductOptionValue findByGroupId_Last(long groupId,
		OrderByComparator<CommerceProductOptionValue> orderByComparator)
		throws NoSuchProductOptionValueException {
		CommerceProductOptionValue commerceProductOptionValue = fetchByGroupId_Last(groupId,
				orderByComparator);

		if (commerceProductOptionValue != null) {
			return commerceProductOptionValue;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchProductOptionValueException(msg.toString());
	}

	/**
	 * Returns the last commerce product option value in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce product option value, or <code>null</code> if a matching commerce product option value could not be found
	 */
	@Override
	public CommerceProductOptionValue fetchByGroupId_Last(long groupId,
		OrderByComparator<CommerceProductOptionValue> orderByComparator) {
		int count = countByGroupId(groupId);

		if (count == 0) {
			return null;
		}

		List<CommerceProductOptionValue> list = findByGroupId(groupId,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the commerce product option values before and after the current commerce product option value in the ordered set where groupId = &#63;.
	 *
	 * @param commerceProductOptionValueId the primary key of the current commerce product option value
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce product option value
	 * @throws NoSuchProductOptionValueException if a commerce product option value with the primary key could not be found
	 */
	@Override
	public CommerceProductOptionValue[] findByGroupId_PrevAndNext(
		long commerceProductOptionValueId, long groupId,
		OrderByComparator<CommerceProductOptionValue> orderByComparator)
		throws NoSuchProductOptionValueException {
		CommerceProductOptionValue commerceProductOptionValue = findByPrimaryKey(commerceProductOptionValueId);

		Session session = null;

		try {
			session = openSession();

			CommerceProductOptionValue[] array = new CommerceProductOptionValueImpl[3];

			array[0] = getByGroupId_PrevAndNext(session,
					commerceProductOptionValue, groupId, orderByComparator, true);

			array[1] = commerceProductOptionValue;

			array[2] = getByGroupId_PrevAndNext(session,
					commerceProductOptionValue, groupId, orderByComparator,
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

	protected CommerceProductOptionValue getByGroupId_PrevAndNext(
		Session session, CommerceProductOptionValue commerceProductOptionValue,
		long groupId,
		OrderByComparator<CommerceProductOptionValue> orderByComparator,
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

		query.append(_SQL_SELECT_COMMERCEPRODUCTOPTIONVALUE_WHERE);

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
			query.append(CommerceProductOptionValueModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(commerceProductOptionValue);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<CommerceProductOptionValue> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the commerce product option values where groupId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 */
	@Override
	public void removeByGroupId(long groupId) {
		for (CommerceProductOptionValue commerceProductOptionValue : findByGroupId(
				groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(commerceProductOptionValue);
		}
	}

	/**
	 * Returns the number of commerce product option values where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching commerce product option values
	 */
	@Override
	public int countByGroupId(long groupId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_GROUPID;

		Object[] finderArgs = new Object[] { groupId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_COMMERCEPRODUCTOPTIONVALUE_WHERE);

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

	private static final String _FINDER_COLUMN_GROUPID_GROUPID_2 = "commerceProductOptionValue.groupId = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_COMPANYID =
		new FinderPath(CommerceProductOptionValueModelImpl.ENTITY_CACHE_ENABLED,
			CommerceProductOptionValueModelImpl.FINDER_CACHE_ENABLED,
			CommerceProductOptionValueImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByCompanyId",
			new String[] {
				Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID =
		new FinderPath(CommerceProductOptionValueModelImpl.ENTITY_CACHE_ENABLED,
			CommerceProductOptionValueModelImpl.FINDER_CACHE_ENABLED,
			CommerceProductOptionValueImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByCompanyId",
			new String[] { Long.class.getName() },
			CommerceProductOptionValueModelImpl.COMPANYID_COLUMN_BITMASK |
			CommerceProductOptionValueModelImpl.TITLE_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_COMPANYID = new FinderPath(CommerceProductOptionValueModelImpl.ENTITY_CACHE_ENABLED,
			CommerceProductOptionValueModelImpl.FINDER_CACHE_ENABLED,
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByCompanyId", new String[] { Long.class.getName() });

	/**
	 * Returns all the commerce product option values where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching commerce product option values
	 */
	@Override
	public List<CommerceProductOptionValue> findByCompanyId(long companyId) {
		return findByCompanyId(companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the commerce product option values where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceProductOptionValueModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce product option values
	 * @param end the upper bound of the range of commerce product option values (not inclusive)
	 * @return the range of matching commerce product option values
	 */
	@Override
	public List<CommerceProductOptionValue> findByCompanyId(long companyId,
		int start, int end) {
		return findByCompanyId(companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce product option values where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceProductOptionValueModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce product option values
	 * @param end the upper bound of the range of commerce product option values (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce product option values
	 */
	@Override
	public List<CommerceProductOptionValue> findByCompanyId(long companyId,
		int start, int end,
		OrderByComparator<CommerceProductOptionValue> orderByComparator) {
		return findByCompanyId(companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce product option values where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceProductOptionValueModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce product option values
	 * @param end the upper bound of the range of commerce product option values (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching commerce product option values
	 */
	@Override
	public List<CommerceProductOptionValue> findByCompanyId(long companyId,
		int start, int end,
		OrderByComparator<CommerceProductOptionValue> orderByComparator,
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

		List<CommerceProductOptionValue> list = null;

		if (retrieveFromCache) {
			list = (List<CommerceProductOptionValue>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (CommerceProductOptionValue commerceProductOptionValue : list) {
					if ((companyId != commerceProductOptionValue.getCompanyId())) {
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

			query.append(_SQL_SELECT_COMMERCEPRODUCTOPTIONVALUE_WHERE);

			query.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(CommerceProductOptionValueModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				if (!pagination) {
					list = (List<CommerceProductOptionValue>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<CommerceProductOptionValue>)QueryUtil.list(q,
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
	 * Returns the first commerce product option value in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce product option value
	 * @throws NoSuchProductOptionValueException if a matching commerce product option value could not be found
	 */
	@Override
	public CommerceProductOptionValue findByCompanyId_First(long companyId,
		OrderByComparator<CommerceProductOptionValue> orderByComparator)
		throws NoSuchProductOptionValueException {
		CommerceProductOptionValue commerceProductOptionValue = fetchByCompanyId_First(companyId,
				orderByComparator);

		if (commerceProductOptionValue != null) {
			return commerceProductOptionValue;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("companyId=");
		msg.append(companyId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchProductOptionValueException(msg.toString());
	}

	/**
	 * Returns the first commerce product option value in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce product option value, or <code>null</code> if a matching commerce product option value could not be found
	 */
	@Override
	public CommerceProductOptionValue fetchByCompanyId_First(long companyId,
		OrderByComparator<CommerceProductOptionValue> orderByComparator) {
		List<CommerceProductOptionValue> list = findByCompanyId(companyId, 0,
				1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last commerce product option value in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce product option value
	 * @throws NoSuchProductOptionValueException if a matching commerce product option value could not be found
	 */
	@Override
	public CommerceProductOptionValue findByCompanyId_Last(long companyId,
		OrderByComparator<CommerceProductOptionValue> orderByComparator)
		throws NoSuchProductOptionValueException {
		CommerceProductOptionValue commerceProductOptionValue = fetchByCompanyId_Last(companyId,
				orderByComparator);

		if (commerceProductOptionValue != null) {
			return commerceProductOptionValue;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("companyId=");
		msg.append(companyId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchProductOptionValueException(msg.toString());
	}

	/**
	 * Returns the last commerce product option value in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce product option value, or <code>null</code> if a matching commerce product option value could not be found
	 */
	@Override
	public CommerceProductOptionValue fetchByCompanyId_Last(long companyId,
		OrderByComparator<CommerceProductOptionValue> orderByComparator) {
		int count = countByCompanyId(companyId);

		if (count == 0) {
			return null;
		}

		List<CommerceProductOptionValue> list = findByCompanyId(companyId,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the commerce product option values before and after the current commerce product option value in the ordered set where companyId = &#63;.
	 *
	 * @param commerceProductOptionValueId the primary key of the current commerce product option value
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce product option value
	 * @throws NoSuchProductOptionValueException if a commerce product option value with the primary key could not be found
	 */
	@Override
	public CommerceProductOptionValue[] findByCompanyId_PrevAndNext(
		long commerceProductOptionValueId, long companyId,
		OrderByComparator<CommerceProductOptionValue> orderByComparator)
		throws NoSuchProductOptionValueException {
		CommerceProductOptionValue commerceProductOptionValue = findByPrimaryKey(commerceProductOptionValueId);

		Session session = null;

		try {
			session = openSession();

			CommerceProductOptionValue[] array = new CommerceProductOptionValueImpl[3];

			array[0] = getByCompanyId_PrevAndNext(session,
					commerceProductOptionValue, companyId, orderByComparator,
					true);

			array[1] = commerceProductOptionValue;

			array[2] = getByCompanyId_PrevAndNext(session,
					commerceProductOptionValue, companyId, orderByComparator,
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

	protected CommerceProductOptionValue getByCompanyId_PrevAndNext(
		Session session, CommerceProductOptionValue commerceProductOptionValue,
		long companyId,
		OrderByComparator<CommerceProductOptionValue> orderByComparator,
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

		query.append(_SQL_SELECT_COMMERCEPRODUCTOPTIONVALUE_WHERE);

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
			query.append(CommerceProductOptionValueModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(companyId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(commerceProductOptionValue);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<CommerceProductOptionValue> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the commerce product option values where companyId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 */
	@Override
	public void removeByCompanyId(long companyId) {
		for (CommerceProductOptionValue commerceProductOptionValue : findByCompanyId(
				companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(commerceProductOptionValue);
		}
	}

	/**
	 * Returns the number of commerce product option values where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching commerce product option values
	 */
	@Override
	public int countByCompanyId(long companyId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_COMPANYID;

		Object[] finderArgs = new Object[] { companyId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_COMMERCEPRODUCTOPTIONVALUE_WHERE);

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

	private static final String _FINDER_COLUMN_COMPANYID_COMPANYID_2 = "commerceProductOptionValue.companyId = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_COMMERCEPRODUCTOPTIONID =
		new FinderPath(CommerceProductOptionValueModelImpl.ENTITY_CACHE_ENABLED,
			CommerceProductOptionValueModelImpl.FINDER_CACHE_ENABLED,
			CommerceProductOptionValueImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByCommerceProductOptionId",
			new String[] {
				Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMMERCEPRODUCTOPTIONID =
		new FinderPath(CommerceProductOptionValueModelImpl.ENTITY_CACHE_ENABLED,
			CommerceProductOptionValueModelImpl.FINDER_CACHE_ENABLED,
			CommerceProductOptionValueImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByCommerceProductOptionId",
			new String[] { Long.class.getName() },
			CommerceProductOptionValueModelImpl.COMMERCEPRODUCTOPTIONID_COLUMN_BITMASK |
			CommerceProductOptionValueModelImpl.TITLE_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_COMMERCEPRODUCTOPTIONID = new FinderPath(CommerceProductOptionValueModelImpl.ENTITY_CACHE_ENABLED,
			CommerceProductOptionValueModelImpl.FINDER_CACHE_ENABLED,
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByCommerceProductOptionId",
			new String[] { Long.class.getName() });

	/**
	 * Returns all the commerce product option values where commerceProductOptionId = &#63;.
	 *
	 * @param commerceProductOptionId the commerce product option ID
	 * @return the matching commerce product option values
	 */
	@Override
	public List<CommerceProductOptionValue> findByCommerceProductOptionId(
		long commerceProductOptionId) {
		return findByCommerceProductOptionId(commerceProductOptionId,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce product option values where commerceProductOptionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceProductOptionValueModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param commerceProductOptionId the commerce product option ID
	 * @param start the lower bound of the range of commerce product option values
	 * @param end the upper bound of the range of commerce product option values (not inclusive)
	 * @return the range of matching commerce product option values
	 */
	@Override
	public List<CommerceProductOptionValue> findByCommerceProductOptionId(
		long commerceProductOptionId, int start, int end) {
		return findByCommerceProductOptionId(commerceProductOptionId, start,
			end, null);
	}

	/**
	 * Returns an ordered range of all the commerce product option values where commerceProductOptionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceProductOptionValueModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param commerceProductOptionId the commerce product option ID
	 * @param start the lower bound of the range of commerce product option values
	 * @param end the upper bound of the range of commerce product option values (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce product option values
	 */
	@Override
	public List<CommerceProductOptionValue> findByCommerceProductOptionId(
		long commerceProductOptionId, int start, int end,
		OrderByComparator<CommerceProductOptionValue> orderByComparator) {
		return findByCommerceProductOptionId(commerceProductOptionId, start,
			end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce product option values where commerceProductOptionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceProductOptionValueModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param commerceProductOptionId the commerce product option ID
	 * @param start the lower bound of the range of commerce product option values
	 * @param end the upper bound of the range of commerce product option values (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching commerce product option values
	 */
	@Override
	public List<CommerceProductOptionValue> findByCommerceProductOptionId(
		long commerceProductOptionId, int start, int end,
		OrderByComparator<CommerceProductOptionValue> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMMERCEPRODUCTOPTIONID;
			finderArgs = new Object[] { commerceProductOptionId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_COMMERCEPRODUCTOPTIONID;
			finderArgs = new Object[] {
					commerceProductOptionId,
					
					start, end, orderByComparator
				};
		}

		List<CommerceProductOptionValue> list = null;

		if (retrieveFromCache) {
			list = (List<CommerceProductOptionValue>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (CommerceProductOptionValue commerceProductOptionValue : list) {
					if ((commerceProductOptionId != commerceProductOptionValue.getCommerceProductOptionId())) {
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

			query.append(_SQL_SELECT_COMMERCEPRODUCTOPTIONVALUE_WHERE);

			query.append(_FINDER_COLUMN_COMMERCEPRODUCTOPTIONID_COMMERCEPRODUCTOPTIONID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(CommerceProductOptionValueModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(commerceProductOptionId);

				if (!pagination) {
					list = (List<CommerceProductOptionValue>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<CommerceProductOptionValue>)QueryUtil.list(q,
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
	 * Returns the first commerce product option value in the ordered set where commerceProductOptionId = &#63;.
	 *
	 * @param commerceProductOptionId the commerce product option ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce product option value
	 * @throws NoSuchProductOptionValueException if a matching commerce product option value could not be found
	 */
	@Override
	public CommerceProductOptionValue findByCommerceProductOptionId_First(
		long commerceProductOptionId,
		OrderByComparator<CommerceProductOptionValue> orderByComparator)
		throws NoSuchProductOptionValueException {
		CommerceProductOptionValue commerceProductOptionValue = fetchByCommerceProductOptionId_First(commerceProductOptionId,
				orderByComparator);

		if (commerceProductOptionValue != null) {
			return commerceProductOptionValue;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("commerceProductOptionId=");
		msg.append(commerceProductOptionId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchProductOptionValueException(msg.toString());
	}

	/**
	 * Returns the first commerce product option value in the ordered set where commerceProductOptionId = &#63;.
	 *
	 * @param commerceProductOptionId the commerce product option ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce product option value, or <code>null</code> if a matching commerce product option value could not be found
	 */
	@Override
	public CommerceProductOptionValue fetchByCommerceProductOptionId_First(
		long commerceProductOptionId,
		OrderByComparator<CommerceProductOptionValue> orderByComparator) {
		List<CommerceProductOptionValue> list = findByCommerceProductOptionId(commerceProductOptionId,
				0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last commerce product option value in the ordered set where commerceProductOptionId = &#63;.
	 *
	 * @param commerceProductOptionId the commerce product option ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce product option value
	 * @throws NoSuchProductOptionValueException if a matching commerce product option value could not be found
	 */
	@Override
	public CommerceProductOptionValue findByCommerceProductOptionId_Last(
		long commerceProductOptionId,
		OrderByComparator<CommerceProductOptionValue> orderByComparator)
		throws NoSuchProductOptionValueException {
		CommerceProductOptionValue commerceProductOptionValue = fetchByCommerceProductOptionId_Last(commerceProductOptionId,
				orderByComparator);

		if (commerceProductOptionValue != null) {
			return commerceProductOptionValue;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("commerceProductOptionId=");
		msg.append(commerceProductOptionId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchProductOptionValueException(msg.toString());
	}

	/**
	 * Returns the last commerce product option value in the ordered set where commerceProductOptionId = &#63;.
	 *
	 * @param commerceProductOptionId the commerce product option ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce product option value, or <code>null</code> if a matching commerce product option value could not be found
	 */
	@Override
	public CommerceProductOptionValue fetchByCommerceProductOptionId_Last(
		long commerceProductOptionId,
		OrderByComparator<CommerceProductOptionValue> orderByComparator) {
		int count = countByCommerceProductOptionId(commerceProductOptionId);

		if (count == 0) {
			return null;
		}

		List<CommerceProductOptionValue> list = findByCommerceProductOptionId(commerceProductOptionId,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the commerce product option values before and after the current commerce product option value in the ordered set where commerceProductOptionId = &#63;.
	 *
	 * @param commerceProductOptionValueId the primary key of the current commerce product option value
	 * @param commerceProductOptionId the commerce product option ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce product option value
	 * @throws NoSuchProductOptionValueException if a commerce product option value with the primary key could not be found
	 */
	@Override
	public CommerceProductOptionValue[] findByCommerceProductOptionId_PrevAndNext(
		long commerceProductOptionValueId, long commerceProductOptionId,
		OrderByComparator<CommerceProductOptionValue> orderByComparator)
		throws NoSuchProductOptionValueException {
		CommerceProductOptionValue commerceProductOptionValue = findByPrimaryKey(commerceProductOptionValueId);

		Session session = null;

		try {
			session = openSession();

			CommerceProductOptionValue[] array = new CommerceProductOptionValueImpl[3];

			array[0] = getByCommerceProductOptionId_PrevAndNext(session,
					commerceProductOptionValue, commerceProductOptionId,
					orderByComparator, true);

			array[1] = commerceProductOptionValue;

			array[2] = getByCommerceProductOptionId_PrevAndNext(session,
					commerceProductOptionValue, commerceProductOptionId,
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

	protected CommerceProductOptionValue getByCommerceProductOptionId_PrevAndNext(
		Session session, CommerceProductOptionValue commerceProductOptionValue,
		long commerceProductOptionId,
		OrderByComparator<CommerceProductOptionValue> orderByComparator,
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

		query.append(_SQL_SELECT_COMMERCEPRODUCTOPTIONVALUE_WHERE);

		query.append(_FINDER_COLUMN_COMMERCEPRODUCTOPTIONID_COMMERCEPRODUCTOPTIONID_2);

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
			query.append(CommerceProductOptionValueModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(commerceProductOptionId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(commerceProductOptionValue);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<CommerceProductOptionValue> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the commerce product option values where commerceProductOptionId = &#63; from the database.
	 *
	 * @param commerceProductOptionId the commerce product option ID
	 */
	@Override
	public void removeByCommerceProductOptionId(long commerceProductOptionId) {
		for (CommerceProductOptionValue commerceProductOptionValue : findByCommerceProductOptionId(
				commerceProductOptionId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
				null)) {
			remove(commerceProductOptionValue);
		}
	}

	/**
	 * Returns the number of commerce product option values where commerceProductOptionId = &#63;.
	 *
	 * @param commerceProductOptionId the commerce product option ID
	 * @return the number of matching commerce product option values
	 */
	@Override
	public int countByCommerceProductOptionId(long commerceProductOptionId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_COMMERCEPRODUCTOPTIONID;

		Object[] finderArgs = new Object[] { commerceProductOptionId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_COMMERCEPRODUCTOPTIONVALUE_WHERE);

			query.append(_FINDER_COLUMN_COMMERCEPRODUCTOPTIONID_COMMERCEPRODUCTOPTIONID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(commerceProductOptionId);

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

	private static final String _FINDER_COLUMN_COMMERCEPRODUCTOPTIONID_COMMERCEPRODUCTOPTIONID_2 =
		"commerceProductOptionValue.commerceProductOptionId = ?";

	public CommerceProductOptionValuePersistenceImpl() {
		setModelClass(CommerceProductOptionValue.class);

		try {
			Field field = ReflectionUtil.getDeclaredField(BasePersistenceImpl.class,
					"_dbColumnNames");

			Map<String, String> dbColumnNames = new HashMap<String, String>();

			dbColumnNames.put("uuid", "uuid_");

			field.set(this, dbColumnNames);
		}
		catch (Exception e) {
			if (_log.isDebugEnabled()) {
				_log.debug(e, e);
			}
		}
	}

	/**
	 * Caches the commerce product option value in the entity cache if it is enabled.
	 *
	 * @param commerceProductOptionValue the commerce product option value
	 */
	@Override
	public void cacheResult(
		CommerceProductOptionValue commerceProductOptionValue) {
		entityCache.putResult(CommerceProductOptionValueModelImpl.ENTITY_CACHE_ENABLED,
			CommerceProductOptionValueImpl.class,
			commerceProductOptionValue.getPrimaryKey(),
			commerceProductOptionValue);

		finderCache.putResult(FINDER_PATH_FETCH_BY_UUID_G,
			new Object[] {
				commerceProductOptionValue.getUuid(),
				commerceProductOptionValue.getGroupId()
			}, commerceProductOptionValue);

		commerceProductOptionValue.resetOriginalValues();
	}

	/**
	 * Caches the commerce product option values in the entity cache if it is enabled.
	 *
	 * @param commerceProductOptionValues the commerce product option values
	 */
	@Override
	public void cacheResult(
		List<CommerceProductOptionValue> commerceProductOptionValues) {
		for (CommerceProductOptionValue commerceProductOptionValue : commerceProductOptionValues) {
			if (entityCache.getResult(
						CommerceProductOptionValueModelImpl.ENTITY_CACHE_ENABLED,
						CommerceProductOptionValueImpl.class,
						commerceProductOptionValue.getPrimaryKey()) == null) {
				cacheResult(commerceProductOptionValue);
			}
			else {
				commerceProductOptionValue.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all commerce product option values.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(CommerceProductOptionValueImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the commerce product option value.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(
		CommerceProductOptionValue commerceProductOptionValue) {
		entityCache.removeResult(CommerceProductOptionValueModelImpl.ENTITY_CACHE_ENABLED,
			CommerceProductOptionValueImpl.class,
			commerceProductOptionValue.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache((CommerceProductOptionValueModelImpl)commerceProductOptionValue,
			true);
	}

	@Override
	public void clearCache(
		List<CommerceProductOptionValue> commerceProductOptionValues) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (CommerceProductOptionValue commerceProductOptionValue : commerceProductOptionValues) {
			entityCache.removeResult(CommerceProductOptionValueModelImpl.ENTITY_CACHE_ENABLED,
				CommerceProductOptionValueImpl.class,
				commerceProductOptionValue.getPrimaryKey());

			clearUniqueFindersCache((CommerceProductOptionValueModelImpl)commerceProductOptionValue,
				true);
		}
	}

	protected void cacheUniqueFindersCache(
		CommerceProductOptionValueModelImpl commerceProductOptionValueModelImpl) {
		Object[] args = new Object[] {
				commerceProductOptionValueModelImpl.getUuid(),
				commerceProductOptionValueModelImpl.getGroupId()
			};

		finderCache.putResult(FINDER_PATH_COUNT_BY_UUID_G, args,
			Long.valueOf(1), false);
		finderCache.putResult(FINDER_PATH_FETCH_BY_UUID_G, args,
			commerceProductOptionValueModelImpl, false);
	}

	protected void clearUniqueFindersCache(
		CommerceProductOptionValueModelImpl commerceProductOptionValueModelImpl,
		boolean clearCurrent) {
		if (clearCurrent) {
			Object[] args = new Object[] {
					commerceProductOptionValueModelImpl.getUuid(),
					commerceProductOptionValueModelImpl.getGroupId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID_G, args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_UUID_G, args);
		}

		if ((commerceProductOptionValueModelImpl.getColumnBitmask() &
				FINDER_PATH_FETCH_BY_UUID_G.getColumnBitmask()) != 0) {
			Object[] args = new Object[] {
					commerceProductOptionValueModelImpl.getOriginalUuid(),
					commerceProductOptionValueModelImpl.getOriginalGroupId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID_G, args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_UUID_G, args);
		}
	}

	/**
	 * Creates a new commerce product option value with the primary key. Does not add the commerce product option value to the database.
	 *
	 * @param commerceProductOptionValueId the primary key for the new commerce product option value
	 * @return the new commerce product option value
	 */
	@Override
	public CommerceProductOptionValue create(long commerceProductOptionValueId) {
		CommerceProductOptionValue commerceProductOptionValue = new CommerceProductOptionValueImpl();

		commerceProductOptionValue.setNew(true);
		commerceProductOptionValue.setPrimaryKey(commerceProductOptionValueId);

		String uuid = PortalUUIDUtil.generate();

		commerceProductOptionValue.setUuid(uuid);

		commerceProductOptionValue.setCompanyId(companyProvider.getCompanyId());

		return commerceProductOptionValue;
	}

	/**
	 * Removes the commerce product option value with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param commerceProductOptionValueId the primary key of the commerce product option value
	 * @return the commerce product option value that was removed
	 * @throws NoSuchProductOptionValueException if a commerce product option value with the primary key could not be found
	 */
	@Override
	public CommerceProductOptionValue remove(long commerceProductOptionValueId)
		throws NoSuchProductOptionValueException {
		return remove((Serializable)commerceProductOptionValueId);
	}

	/**
	 * Removes the commerce product option value with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the commerce product option value
	 * @return the commerce product option value that was removed
	 * @throws NoSuchProductOptionValueException if a commerce product option value with the primary key could not be found
	 */
	@Override
	public CommerceProductOptionValue remove(Serializable primaryKey)
		throws NoSuchProductOptionValueException {
		Session session = null;

		try {
			session = openSession();

			CommerceProductOptionValue commerceProductOptionValue = (CommerceProductOptionValue)session.get(CommerceProductOptionValueImpl.class,
					primaryKey);

			if (commerceProductOptionValue == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchProductOptionValueException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(commerceProductOptionValue);
		}
		catch (NoSuchProductOptionValueException nsee) {
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
	protected CommerceProductOptionValue removeImpl(
		CommerceProductOptionValue commerceProductOptionValue) {
		commerceProductOptionValue = toUnwrappedModel(commerceProductOptionValue);

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(commerceProductOptionValue)) {
				commerceProductOptionValue = (CommerceProductOptionValue)session.get(CommerceProductOptionValueImpl.class,
						commerceProductOptionValue.getPrimaryKeyObj());
			}

			if (commerceProductOptionValue != null) {
				session.delete(commerceProductOptionValue);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (commerceProductOptionValue != null) {
			clearCache(commerceProductOptionValue);
		}

		return commerceProductOptionValue;
	}

	@Override
	public CommerceProductOptionValue updateImpl(
		CommerceProductOptionValue commerceProductOptionValue) {
		commerceProductOptionValue = toUnwrappedModel(commerceProductOptionValue);

		boolean isNew = commerceProductOptionValue.isNew();

		CommerceProductOptionValueModelImpl commerceProductOptionValueModelImpl = (CommerceProductOptionValueModelImpl)commerceProductOptionValue;

		if (Validator.isNull(commerceProductOptionValue.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			commerceProductOptionValue.setUuid(uuid);
		}

		ServiceContext serviceContext = ServiceContextThreadLocal.getServiceContext();

		Date now = new Date();

		if (isNew && (commerceProductOptionValue.getCreateDate() == null)) {
			if (serviceContext == null) {
				commerceProductOptionValue.setCreateDate(now);
			}
			else {
				commerceProductOptionValue.setCreateDate(serviceContext.getCreateDate(
						now));
			}
		}

		if (!commerceProductOptionValueModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				commerceProductOptionValue.setModifiedDate(now);
			}
			else {
				commerceProductOptionValue.setModifiedDate(serviceContext.getModifiedDate(
						now));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (commerceProductOptionValue.isNew()) {
				session.save(commerceProductOptionValue);

				commerceProductOptionValue.setNew(false);
			}
			else {
				commerceProductOptionValue = (CommerceProductOptionValue)session.merge(commerceProductOptionValue);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (!CommerceProductOptionValueModelImpl.COLUMN_BITMASK_ENABLED) {
			finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}
		else
		 if (isNew) {
			Object[] args = new Object[] {
					commerceProductOptionValueModelImpl.getUuid()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID, args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID,
				args);

			args = new Object[] {
					commerceProductOptionValueModelImpl.getUuid(),
					commerceProductOptionValueModelImpl.getCompanyId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID_C, args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C,
				args);

			args = new Object[] { commerceProductOptionValueModelImpl.getGroupId() };

			finderCache.removeResult(FINDER_PATH_COUNT_BY_GROUPID, args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID,
				args);

			args = new Object[] {
					commerceProductOptionValueModelImpl.getCompanyId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_COMPANYID, args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID,
				args);

			args = new Object[] {
					commerceProductOptionValueModelImpl.getCommerceProductOptionId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_COMMERCEPRODUCTOPTIONID,
				args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMMERCEPRODUCTOPTIONID,
				args);

			finderCache.removeResult(FINDER_PATH_COUNT_ALL, FINDER_ARGS_EMPTY);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL,
				FINDER_ARGS_EMPTY);
		}

		else {
			if ((commerceProductOptionValueModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						commerceProductOptionValueModelImpl.getOriginalUuid()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID,
					args);

				args = new Object[] {
						commerceProductOptionValueModelImpl.getUuid()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID,
					args);
			}

			if ((commerceProductOptionValueModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						commerceProductOptionValueModelImpl.getOriginalUuid(),
						commerceProductOptionValueModelImpl.getOriginalCompanyId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID_C, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C,
					args);

				args = new Object[] {
						commerceProductOptionValueModelImpl.getUuid(),
						commerceProductOptionValueModelImpl.getCompanyId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID_C, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C,
					args);
			}

			if ((commerceProductOptionValueModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						commerceProductOptionValueModelImpl.getOriginalGroupId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_GROUPID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID,
					args);

				args = new Object[] {
						commerceProductOptionValueModelImpl.getGroupId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_GROUPID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID,
					args);
			}

			if ((commerceProductOptionValueModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						commerceProductOptionValueModelImpl.getOriginalCompanyId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_COMPANYID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID,
					args);

				args = new Object[] {
						commerceProductOptionValueModelImpl.getCompanyId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_COMPANYID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID,
					args);
			}

			if ((commerceProductOptionValueModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMMERCEPRODUCTOPTIONID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						commerceProductOptionValueModelImpl.getOriginalCommerceProductOptionId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_COMMERCEPRODUCTOPTIONID,
					args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMMERCEPRODUCTOPTIONID,
					args);

				args = new Object[] {
						commerceProductOptionValueModelImpl.getCommerceProductOptionId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_COMMERCEPRODUCTOPTIONID,
					args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMMERCEPRODUCTOPTIONID,
					args);
			}
		}

		entityCache.putResult(CommerceProductOptionValueModelImpl.ENTITY_CACHE_ENABLED,
			CommerceProductOptionValueImpl.class,
			commerceProductOptionValue.getPrimaryKey(),
			commerceProductOptionValue, false);

		clearUniqueFindersCache(commerceProductOptionValueModelImpl, false);
		cacheUniqueFindersCache(commerceProductOptionValueModelImpl);

		commerceProductOptionValue.resetOriginalValues();

		return commerceProductOptionValue;
	}

	protected CommerceProductOptionValue toUnwrappedModel(
		CommerceProductOptionValue commerceProductOptionValue) {
		if (commerceProductOptionValue instanceof CommerceProductOptionValueImpl) {
			return commerceProductOptionValue;
		}

		CommerceProductOptionValueImpl commerceProductOptionValueImpl = new CommerceProductOptionValueImpl();

		commerceProductOptionValueImpl.setNew(commerceProductOptionValue.isNew());
		commerceProductOptionValueImpl.setPrimaryKey(commerceProductOptionValue.getPrimaryKey());

		commerceProductOptionValueImpl.setUuid(commerceProductOptionValue.getUuid());
		commerceProductOptionValueImpl.setCommerceProductOptionValueId(commerceProductOptionValue.getCommerceProductOptionValueId());
		commerceProductOptionValueImpl.setGroupId(commerceProductOptionValue.getGroupId());
		commerceProductOptionValueImpl.setCompanyId(commerceProductOptionValue.getCompanyId());
		commerceProductOptionValueImpl.setUserId(commerceProductOptionValue.getUserId());
		commerceProductOptionValueImpl.setUserName(commerceProductOptionValue.getUserName());
		commerceProductOptionValueImpl.setCreateDate(commerceProductOptionValue.getCreateDate());
		commerceProductOptionValueImpl.setModifiedDate(commerceProductOptionValue.getModifiedDate());
		commerceProductOptionValueImpl.setCommerceProductOptionId(commerceProductOptionValue.getCommerceProductOptionId());
		commerceProductOptionValueImpl.setTitle(commerceProductOptionValue.getTitle());
		commerceProductOptionValueImpl.setPriority(commerceProductOptionValue.getPriority());

		return commerceProductOptionValueImpl;
	}

	/**
	 * Returns the commerce product option value with the primary key or throws a {@link com.liferay.portal.kernel.exception.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the commerce product option value
	 * @return the commerce product option value
	 * @throws NoSuchProductOptionValueException if a commerce product option value with the primary key could not be found
	 */
	@Override
	public CommerceProductOptionValue findByPrimaryKey(Serializable primaryKey)
		throws NoSuchProductOptionValueException {
		CommerceProductOptionValue commerceProductOptionValue = fetchByPrimaryKey(primaryKey);

		if (commerceProductOptionValue == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchProductOptionValueException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				primaryKey);
		}

		return commerceProductOptionValue;
	}

	/**
	 * Returns the commerce product option value with the primary key or throws a {@link NoSuchProductOptionValueException} if it could not be found.
	 *
	 * @param commerceProductOptionValueId the primary key of the commerce product option value
	 * @return the commerce product option value
	 * @throws NoSuchProductOptionValueException if a commerce product option value with the primary key could not be found
	 */
	@Override
	public CommerceProductOptionValue findByPrimaryKey(
		long commerceProductOptionValueId)
		throws NoSuchProductOptionValueException {
		return findByPrimaryKey((Serializable)commerceProductOptionValueId);
	}

	/**
	 * Returns the commerce product option value with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the commerce product option value
	 * @return the commerce product option value, or <code>null</code> if a commerce product option value with the primary key could not be found
	 */
	@Override
	public CommerceProductOptionValue fetchByPrimaryKey(Serializable primaryKey) {
		Serializable serializable = entityCache.getResult(CommerceProductOptionValueModelImpl.ENTITY_CACHE_ENABLED,
				CommerceProductOptionValueImpl.class, primaryKey);

		if (serializable == nullModel) {
			return null;
		}

		CommerceProductOptionValue commerceProductOptionValue = (CommerceProductOptionValue)serializable;

		if (commerceProductOptionValue == null) {
			Session session = null;

			try {
				session = openSession();

				commerceProductOptionValue = (CommerceProductOptionValue)session.get(CommerceProductOptionValueImpl.class,
						primaryKey);

				if (commerceProductOptionValue != null) {
					cacheResult(commerceProductOptionValue);
				}
				else {
					entityCache.putResult(CommerceProductOptionValueModelImpl.ENTITY_CACHE_ENABLED,
						CommerceProductOptionValueImpl.class, primaryKey,
						nullModel);
				}
			}
			catch (Exception e) {
				entityCache.removeResult(CommerceProductOptionValueModelImpl.ENTITY_CACHE_ENABLED,
					CommerceProductOptionValueImpl.class, primaryKey);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return commerceProductOptionValue;
	}

	/**
	 * Returns the commerce product option value with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param commerceProductOptionValueId the primary key of the commerce product option value
	 * @return the commerce product option value, or <code>null</code> if a commerce product option value with the primary key could not be found
	 */
	@Override
	public CommerceProductOptionValue fetchByPrimaryKey(
		long commerceProductOptionValueId) {
		return fetchByPrimaryKey((Serializable)commerceProductOptionValueId);
	}

	@Override
	public Map<Serializable, CommerceProductOptionValue> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {
		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, CommerceProductOptionValue> map = new HashMap<Serializable, CommerceProductOptionValue>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			CommerceProductOptionValue commerceProductOptionValue = fetchByPrimaryKey(primaryKey);

			if (commerceProductOptionValue != null) {
				map.put(primaryKey, commerceProductOptionValue);
			}

			return map;
		}

		Set<Serializable> uncachedPrimaryKeys = null;

		for (Serializable primaryKey : primaryKeys) {
			Serializable serializable = entityCache.getResult(CommerceProductOptionValueModelImpl.ENTITY_CACHE_ENABLED,
					CommerceProductOptionValueImpl.class, primaryKey);

			if (serializable != nullModel) {
				if (serializable == null) {
					if (uncachedPrimaryKeys == null) {
						uncachedPrimaryKeys = new HashSet<Serializable>();
					}

					uncachedPrimaryKeys.add(primaryKey);
				}
				else {
					map.put(primaryKey, (CommerceProductOptionValue)serializable);
				}
			}
		}

		if (uncachedPrimaryKeys == null) {
			return map;
		}

		StringBundler query = new StringBundler((uncachedPrimaryKeys.size() * 2) +
				1);

		query.append(_SQL_SELECT_COMMERCEPRODUCTOPTIONVALUE_WHERE_PKS_IN);

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

			for (CommerceProductOptionValue commerceProductOptionValue : (List<CommerceProductOptionValue>)q.list()) {
				map.put(commerceProductOptionValue.getPrimaryKeyObj(),
					commerceProductOptionValue);

				cacheResult(commerceProductOptionValue);

				uncachedPrimaryKeys.remove(commerceProductOptionValue.getPrimaryKeyObj());
			}

			for (Serializable primaryKey : uncachedPrimaryKeys) {
				entityCache.putResult(CommerceProductOptionValueModelImpl.ENTITY_CACHE_ENABLED,
					CommerceProductOptionValueImpl.class, primaryKey, nullModel);
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
	 * Returns all the commerce product option values.
	 *
	 * @return the commerce product option values
	 */
	@Override
	public List<CommerceProductOptionValue> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce product option values.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceProductOptionValueModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce product option values
	 * @param end the upper bound of the range of commerce product option values (not inclusive)
	 * @return the range of commerce product option values
	 */
	@Override
	public List<CommerceProductOptionValue> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce product option values.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceProductOptionValueModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce product option values
	 * @param end the upper bound of the range of commerce product option values (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of commerce product option values
	 */
	@Override
	public List<CommerceProductOptionValue> findAll(int start, int end,
		OrderByComparator<CommerceProductOptionValue> orderByComparator) {
		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce product option values.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceProductOptionValueModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce product option values
	 * @param end the upper bound of the range of commerce product option values (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of commerce product option values
	 */
	@Override
	public List<CommerceProductOptionValue> findAll(int start, int end,
		OrderByComparator<CommerceProductOptionValue> orderByComparator,
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

		List<CommerceProductOptionValue> list = null;

		if (retrieveFromCache) {
			list = (List<CommerceProductOptionValue>)finderCache.getResult(finderPath,
					finderArgs, this);
		}

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 2));

				query.append(_SQL_SELECT_COMMERCEPRODUCTOPTIONVALUE);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_COMMERCEPRODUCTOPTIONVALUE;

				if (pagination) {
					sql = sql.concat(CommerceProductOptionValueModelImpl.ORDER_BY_JPQL);
				}
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (!pagination) {
					list = (List<CommerceProductOptionValue>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<CommerceProductOptionValue>)QueryUtil.list(q,
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
	 * Removes all the commerce product option values from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (CommerceProductOptionValue commerceProductOptionValue : findAll()) {
			remove(commerceProductOptionValue);
		}
	}

	/**
	 * Returns the number of commerce product option values.
	 *
	 * @return the number of commerce product option values
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_COMMERCEPRODUCTOPTIONVALUE);

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
		return CommerceProductOptionValueModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the commerce product option value persistence.
	 */
	public void afterPropertiesSet() {
	}

	public void destroy() {
		entityCache.removeCache(CommerceProductOptionValueImpl.class.getName());
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
	private static final String _SQL_SELECT_COMMERCEPRODUCTOPTIONVALUE = "SELECT commerceProductOptionValue FROM CommerceProductOptionValue commerceProductOptionValue";
	private static final String _SQL_SELECT_COMMERCEPRODUCTOPTIONVALUE_WHERE_PKS_IN =
		"SELECT commerceProductOptionValue FROM CommerceProductOptionValue commerceProductOptionValue WHERE commerceProductOptionValueId IN (";
	private static final String _SQL_SELECT_COMMERCEPRODUCTOPTIONVALUE_WHERE = "SELECT commerceProductOptionValue FROM CommerceProductOptionValue commerceProductOptionValue WHERE ";
	private static final String _SQL_COUNT_COMMERCEPRODUCTOPTIONVALUE = "SELECT COUNT(commerceProductOptionValue) FROM CommerceProductOptionValue commerceProductOptionValue";
	private static final String _SQL_COUNT_COMMERCEPRODUCTOPTIONVALUE_WHERE = "SELECT COUNT(commerceProductOptionValue) FROM CommerceProductOptionValue commerceProductOptionValue WHERE ";
	private static final String _ORDER_BY_ENTITY_ALIAS = "commerceProductOptionValue.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No CommerceProductOptionValue exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No CommerceProductOptionValue exists with the key {";
	private static final Log _log = LogFactoryUtil.getLog(CommerceProductOptionValuePersistenceImpl.class);
	private static final Set<String> _badColumnNames = SetUtil.fromArray(new String[] {
				"uuid"
			});
}