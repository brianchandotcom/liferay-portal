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

package com.liferay.commerce.price.list.qualification.type.service.persistence.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.commerce.price.list.qualification.type.exception.NoSuchPriceListUserRelException;
import com.liferay.commerce.price.list.qualification.type.model.CommercePriceListUserRel;
import com.liferay.commerce.price.list.qualification.type.model.impl.CommercePriceListUserRelImpl;
import com.liferay.commerce.price.list.qualification.type.model.impl.CommercePriceListUserRelModelImpl;
import com.liferay.commerce.price.list.qualification.type.service.persistence.CommercePriceListUserRelPersistence;

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
 * The persistence implementation for the commerce price list user rel service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Alessio Antonio Rendina
 * @see CommercePriceListUserRelPersistence
 * @see com.liferay.commerce.price.list.qualification.type.service.persistence.CommercePriceListUserRelUtil
 * @generated
 */
@ProviderType
public class CommercePriceListUserRelPersistenceImpl extends BasePersistenceImpl<CommercePriceListUserRel>
	implements CommercePriceListUserRelPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link CommercePriceListUserRelUtil} to access the commerce price list user rel persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = CommercePriceListUserRelImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(CommercePriceListUserRelModelImpl.ENTITY_CACHE_ENABLED,
			CommercePriceListUserRelModelImpl.FINDER_CACHE_ENABLED,
			CommercePriceListUserRelImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(CommercePriceListUserRelModelImpl.ENTITY_CACHE_ENABLED,
			CommercePriceListUserRelModelImpl.FINDER_CACHE_ENABLED,
			CommercePriceListUserRelImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(CommercePriceListUserRelModelImpl.ENTITY_CACHE_ENABLED,
			CommercePriceListUserRelModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_UUID = new FinderPath(CommercePriceListUserRelModelImpl.ENTITY_CACHE_ENABLED,
			CommercePriceListUserRelModelImpl.FINDER_CACHE_ENABLED,
			CommercePriceListUserRelImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUuid",
			new String[] {
				String.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID = new FinderPath(CommercePriceListUserRelModelImpl.ENTITY_CACHE_ENABLED,
			CommercePriceListUserRelModelImpl.FINDER_CACHE_ENABLED,
			CommercePriceListUserRelImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid",
			new String[] { String.class.getName() },
			CommercePriceListUserRelModelImpl.UUID_COLUMN_BITMASK |
			CommercePriceListUserRelModelImpl.CREATEDATE_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID = new FinderPath(CommercePriceListUserRelModelImpl.ENTITY_CACHE_ENABLED,
			CommercePriceListUserRelModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUuid",
			new String[] { String.class.getName() });

	/**
	 * Returns all the commerce price list user rels where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching commerce price list user rels
	 */
	@Override
	public List<CommercePriceListUserRel> findByUuid(String uuid) {
		return findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce price list user rels where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommercePriceListUserRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of commerce price list user rels
	 * @param end the upper bound of the range of commerce price list user rels (not inclusive)
	 * @return the range of matching commerce price list user rels
	 */
	@Override
	public List<CommercePriceListUserRel> findByUuid(String uuid, int start,
		int end) {
		return findByUuid(uuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce price list user rels where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommercePriceListUserRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of commerce price list user rels
	 * @param end the upper bound of the range of commerce price list user rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce price list user rels
	 */
	@Override
	public List<CommercePriceListUserRel> findByUuid(String uuid, int start,
		int end, OrderByComparator<CommercePriceListUserRel> orderByComparator) {
		return findByUuid(uuid, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce price list user rels where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommercePriceListUserRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of commerce price list user rels
	 * @param end the upper bound of the range of commerce price list user rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching commerce price list user rels
	 */
	@Override
	public List<CommercePriceListUserRel> findByUuid(String uuid, int start,
		int end, OrderByComparator<CommercePriceListUserRel> orderByComparator,
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

		List<CommercePriceListUserRel> list = null;

		if (retrieveFromCache) {
			list = (List<CommercePriceListUserRel>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (CommercePriceListUserRel commercePriceListUserRel : list) {
					if (!Objects.equals(uuid, commercePriceListUserRel.getUuid())) {
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

			query.append(_SQL_SELECT_COMMERCEPRICELISTUSERREL_WHERE);

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
				query.append(CommercePriceListUserRelModelImpl.ORDER_BY_JPQL);
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
					list = (List<CommercePriceListUserRel>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<CommercePriceListUserRel>)QueryUtil.list(q,
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
	 * Returns the first commerce price list user rel in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce price list user rel
	 * @throws NoSuchPriceListUserRelException if a matching commerce price list user rel could not be found
	 */
	@Override
	public CommercePriceListUserRel findByUuid_First(String uuid,
		OrderByComparator<CommercePriceListUserRel> orderByComparator)
		throws NoSuchPriceListUserRelException {
		CommercePriceListUserRel commercePriceListUserRel = fetchByUuid_First(uuid,
				orderByComparator);

		if (commercePriceListUserRel != null) {
			return commercePriceListUserRel;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchPriceListUserRelException(msg.toString());
	}

	/**
	 * Returns the first commerce price list user rel in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce price list user rel, or <code>null</code> if a matching commerce price list user rel could not be found
	 */
	@Override
	public CommercePriceListUserRel fetchByUuid_First(String uuid,
		OrderByComparator<CommercePriceListUserRel> orderByComparator) {
		List<CommercePriceListUserRel> list = findByUuid(uuid, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last commerce price list user rel in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce price list user rel
	 * @throws NoSuchPriceListUserRelException if a matching commerce price list user rel could not be found
	 */
	@Override
	public CommercePriceListUserRel findByUuid_Last(String uuid,
		OrderByComparator<CommercePriceListUserRel> orderByComparator)
		throws NoSuchPriceListUserRelException {
		CommercePriceListUserRel commercePriceListUserRel = fetchByUuid_Last(uuid,
				orderByComparator);

		if (commercePriceListUserRel != null) {
			return commercePriceListUserRel;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchPriceListUserRelException(msg.toString());
	}

	/**
	 * Returns the last commerce price list user rel in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce price list user rel, or <code>null</code> if a matching commerce price list user rel could not be found
	 */
	@Override
	public CommercePriceListUserRel fetchByUuid_Last(String uuid,
		OrderByComparator<CommercePriceListUserRel> orderByComparator) {
		int count = countByUuid(uuid);

		if (count == 0) {
			return null;
		}

		List<CommercePriceListUserRel> list = findByUuid(uuid, count - 1,
				count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the commerce price list user rels before and after the current commerce price list user rel in the ordered set where uuid = &#63;.
	 *
	 * @param commercePriceListUserRelId the primary key of the current commerce price list user rel
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce price list user rel
	 * @throws NoSuchPriceListUserRelException if a commerce price list user rel with the primary key could not be found
	 */
	@Override
	public CommercePriceListUserRel[] findByUuid_PrevAndNext(
		long commercePriceListUserRelId, String uuid,
		OrderByComparator<CommercePriceListUserRel> orderByComparator)
		throws NoSuchPriceListUserRelException {
		CommercePriceListUserRel commercePriceListUserRel = findByPrimaryKey(commercePriceListUserRelId);

		Session session = null;

		try {
			session = openSession();

			CommercePriceListUserRel[] array = new CommercePriceListUserRelImpl[3];

			array[0] = getByUuid_PrevAndNext(session, commercePriceListUserRel,
					uuid, orderByComparator, true);

			array[1] = commercePriceListUserRel;

			array[2] = getByUuid_PrevAndNext(session, commercePriceListUserRel,
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

	protected CommercePriceListUserRel getByUuid_PrevAndNext(Session session,
		CommercePriceListUserRel commercePriceListUserRel, String uuid,
		OrderByComparator<CommercePriceListUserRel> orderByComparator,
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

		query.append(_SQL_SELECT_COMMERCEPRICELISTUSERREL_WHERE);

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
			query.append(CommercePriceListUserRelModelImpl.ORDER_BY_JPQL);
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
			Object[] values = orderByComparator.getOrderByConditionValues(commercePriceListUserRel);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<CommercePriceListUserRel> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the commerce price list user rels where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	@Override
	public void removeByUuid(String uuid) {
		for (CommercePriceListUserRel commercePriceListUserRel : findByUuid(
				uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(commercePriceListUserRel);
		}
	}

	/**
	 * Returns the number of commerce price list user rels where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching commerce price list user rels
	 */
	@Override
	public int countByUuid(String uuid) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_UUID;

		Object[] finderArgs = new Object[] { uuid };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_COMMERCEPRICELISTUSERREL_WHERE);

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

	private static final String _FINDER_COLUMN_UUID_UUID_1 = "commercePriceListUserRel.uuid IS NULL";
	private static final String _FINDER_COLUMN_UUID_UUID_2 = "commercePriceListUserRel.uuid = ?";
	private static final String _FINDER_COLUMN_UUID_UUID_3 = "(commercePriceListUserRel.uuid IS NULL OR commercePriceListUserRel.uuid = '')";
	public static final FinderPath FINDER_PATH_FETCH_BY_UUID_G = new FinderPath(CommercePriceListUserRelModelImpl.ENTITY_CACHE_ENABLED,
			CommercePriceListUserRelModelImpl.FINDER_CACHE_ENABLED,
			CommercePriceListUserRelImpl.class, FINDER_CLASS_NAME_ENTITY,
			"fetchByUUID_G",
			new String[] { String.class.getName(), Long.class.getName() },
			CommercePriceListUserRelModelImpl.UUID_COLUMN_BITMASK |
			CommercePriceListUserRelModelImpl.GROUPID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID_G = new FinderPath(CommercePriceListUserRelModelImpl.ENTITY_CACHE_ENABLED,
			CommercePriceListUserRelModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUUID_G",
			new String[] { String.class.getName(), Long.class.getName() });

	/**
	 * Returns the commerce price list user rel where uuid = &#63; and groupId = &#63; or throws a {@link NoSuchPriceListUserRelException} if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching commerce price list user rel
	 * @throws NoSuchPriceListUserRelException if a matching commerce price list user rel could not be found
	 */
	@Override
	public CommercePriceListUserRel findByUUID_G(String uuid, long groupId)
		throws NoSuchPriceListUserRelException {
		CommercePriceListUserRel commercePriceListUserRel = fetchByUUID_G(uuid,
				groupId);

		if (commercePriceListUserRel == null) {
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

			throw new NoSuchPriceListUserRelException(msg.toString());
		}

		return commercePriceListUserRel;
	}

	/**
	 * Returns the commerce price list user rel where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching commerce price list user rel, or <code>null</code> if a matching commerce price list user rel could not be found
	 */
	@Override
	public CommercePriceListUserRel fetchByUUID_G(String uuid, long groupId) {
		return fetchByUUID_G(uuid, groupId, true);
	}

	/**
	 * Returns the commerce price list user rel where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the matching commerce price list user rel, or <code>null</code> if a matching commerce price list user rel could not be found
	 */
	@Override
	public CommercePriceListUserRel fetchByUUID_G(String uuid, long groupId,
		boolean retrieveFromCache) {
		Object[] finderArgs = new Object[] { uuid, groupId };

		Object result = null;

		if (retrieveFromCache) {
			result = finderCache.getResult(FINDER_PATH_FETCH_BY_UUID_G,
					finderArgs, this);
		}

		if (result instanceof CommercePriceListUserRel) {
			CommercePriceListUserRel commercePriceListUserRel = (CommercePriceListUserRel)result;

			if (!Objects.equals(uuid, commercePriceListUserRel.getUuid()) ||
					(groupId != commercePriceListUserRel.getGroupId())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_SELECT_COMMERCEPRICELISTUSERREL_WHERE);

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

				List<CommercePriceListUserRel> list = q.list();

				if (list.isEmpty()) {
					finderCache.putResult(FINDER_PATH_FETCH_BY_UUID_G,
						finderArgs, list);
				}
				else {
					CommercePriceListUserRel commercePriceListUserRel = list.get(0);

					result = commercePriceListUserRel;

					cacheResult(commercePriceListUserRel);

					if ((commercePriceListUserRel.getUuid() == null) ||
							!commercePriceListUserRel.getUuid().equals(uuid) ||
							(commercePriceListUserRel.getGroupId() != groupId)) {
						finderCache.putResult(FINDER_PATH_FETCH_BY_UUID_G,
							finderArgs, commercePriceListUserRel);
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
			return (CommercePriceListUserRel)result;
		}
	}

	/**
	 * Removes the commerce price list user rel where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the commerce price list user rel that was removed
	 */
	@Override
	public CommercePriceListUserRel removeByUUID_G(String uuid, long groupId)
		throws NoSuchPriceListUserRelException {
		CommercePriceListUserRel commercePriceListUserRel = findByUUID_G(uuid,
				groupId);

		return remove(commercePriceListUserRel);
	}

	/**
	 * Returns the number of commerce price list user rels where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching commerce price list user rels
	 */
	@Override
	public int countByUUID_G(String uuid, long groupId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_UUID_G;

		Object[] finderArgs = new Object[] { uuid, groupId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_COMMERCEPRICELISTUSERREL_WHERE);

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

	private static final String _FINDER_COLUMN_UUID_G_UUID_1 = "commercePriceListUserRel.uuid IS NULL AND ";
	private static final String _FINDER_COLUMN_UUID_G_UUID_2 = "commercePriceListUserRel.uuid = ? AND ";
	private static final String _FINDER_COLUMN_UUID_G_UUID_3 = "(commercePriceListUserRel.uuid IS NULL OR commercePriceListUserRel.uuid = '') AND ";
	private static final String _FINDER_COLUMN_UUID_G_GROUPID_2 = "commercePriceListUserRel.groupId = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_UUID_C = new FinderPath(CommercePriceListUserRelModelImpl.ENTITY_CACHE_ENABLED,
			CommercePriceListUserRelModelImpl.FINDER_CACHE_ENABLED,
			CommercePriceListUserRelImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUuid_C",
			new String[] {
				String.class.getName(), Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C =
		new FinderPath(CommercePriceListUserRelModelImpl.ENTITY_CACHE_ENABLED,
			CommercePriceListUserRelModelImpl.FINDER_CACHE_ENABLED,
			CommercePriceListUserRelImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid_C",
			new String[] { String.class.getName(), Long.class.getName() },
			CommercePriceListUserRelModelImpl.UUID_COLUMN_BITMASK |
			CommercePriceListUserRelModelImpl.COMPANYID_COLUMN_BITMASK |
			CommercePriceListUserRelModelImpl.CREATEDATE_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID_C = new FinderPath(CommercePriceListUserRelModelImpl.ENTITY_CACHE_ENABLED,
			CommercePriceListUserRelModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUuid_C",
			new String[] { String.class.getName(), Long.class.getName() });

	/**
	 * Returns all the commerce price list user rels where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching commerce price list user rels
	 */
	@Override
	public List<CommercePriceListUserRel> findByUuid_C(String uuid,
		long companyId) {
		return findByUuid_C(uuid, companyId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce price list user rels where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommercePriceListUserRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce price list user rels
	 * @param end the upper bound of the range of commerce price list user rels (not inclusive)
	 * @return the range of matching commerce price list user rels
	 */
	@Override
	public List<CommercePriceListUserRel> findByUuid_C(String uuid,
		long companyId, int start, int end) {
		return findByUuid_C(uuid, companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce price list user rels where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommercePriceListUserRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce price list user rels
	 * @param end the upper bound of the range of commerce price list user rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce price list user rels
	 */
	@Override
	public List<CommercePriceListUserRel> findByUuid_C(String uuid,
		long companyId, int start, int end,
		OrderByComparator<CommercePriceListUserRel> orderByComparator) {
		return findByUuid_C(uuid, companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce price list user rels where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommercePriceListUserRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce price list user rels
	 * @param end the upper bound of the range of commerce price list user rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching commerce price list user rels
	 */
	@Override
	public List<CommercePriceListUserRel> findByUuid_C(String uuid,
		long companyId, int start, int end,
		OrderByComparator<CommercePriceListUserRel> orderByComparator,
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

		List<CommercePriceListUserRel> list = null;

		if (retrieveFromCache) {
			list = (List<CommercePriceListUserRel>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (CommercePriceListUserRel commercePriceListUserRel : list) {
					if (!Objects.equals(uuid, commercePriceListUserRel.getUuid()) ||
							(companyId != commercePriceListUserRel.getCompanyId())) {
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

			query.append(_SQL_SELECT_COMMERCEPRICELISTUSERREL_WHERE);

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
				query.append(CommercePriceListUserRelModelImpl.ORDER_BY_JPQL);
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
					list = (List<CommercePriceListUserRel>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<CommercePriceListUserRel>)QueryUtil.list(q,
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
	 * Returns the first commerce price list user rel in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce price list user rel
	 * @throws NoSuchPriceListUserRelException if a matching commerce price list user rel could not be found
	 */
	@Override
	public CommercePriceListUserRel findByUuid_C_First(String uuid,
		long companyId,
		OrderByComparator<CommercePriceListUserRel> orderByComparator)
		throws NoSuchPriceListUserRelException {
		CommercePriceListUserRel commercePriceListUserRel = fetchByUuid_C_First(uuid,
				companyId, orderByComparator);

		if (commercePriceListUserRel != null) {
			return commercePriceListUserRel;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(", companyId=");
		msg.append(companyId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchPriceListUserRelException(msg.toString());
	}

	/**
	 * Returns the first commerce price list user rel in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce price list user rel, or <code>null</code> if a matching commerce price list user rel could not be found
	 */
	@Override
	public CommercePriceListUserRel fetchByUuid_C_First(String uuid,
		long companyId,
		OrderByComparator<CommercePriceListUserRel> orderByComparator) {
		List<CommercePriceListUserRel> list = findByUuid_C(uuid, companyId, 0,
				1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last commerce price list user rel in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce price list user rel
	 * @throws NoSuchPriceListUserRelException if a matching commerce price list user rel could not be found
	 */
	@Override
	public CommercePriceListUserRel findByUuid_C_Last(String uuid,
		long companyId,
		OrderByComparator<CommercePriceListUserRel> orderByComparator)
		throws NoSuchPriceListUserRelException {
		CommercePriceListUserRel commercePriceListUserRel = fetchByUuid_C_Last(uuid,
				companyId, orderByComparator);

		if (commercePriceListUserRel != null) {
			return commercePriceListUserRel;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(", companyId=");
		msg.append(companyId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchPriceListUserRelException(msg.toString());
	}

	/**
	 * Returns the last commerce price list user rel in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce price list user rel, or <code>null</code> if a matching commerce price list user rel could not be found
	 */
	@Override
	public CommercePriceListUserRel fetchByUuid_C_Last(String uuid,
		long companyId,
		OrderByComparator<CommercePriceListUserRel> orderByComparator) {
		int count = countByUuid_C(uuid, companyId);

		if (count == 0) {
			return null;
		}

		List<CommercePriceListUserRel> list = findByUuid_C(uuid, companyId,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the commerce price list user rels before and after the current commerce price list user rel in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param commercePriceListUserRelId the primary key of the current commerce price list user rel
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce price list user rel
	 * @throws NoSuchPriceListUserRelException if a commerce price list user rel with the primary key could not be found
	 */
	@Override
	public CommercePriceListUserRel[] findByUuid_C_PrevAndNext(
		long commercePriceListUserRelId, String uuid, long companyId,
		OrderByComparator<CommercePriceListUserRel> orderByComparator)
		throws NoSuchPriceListUserRelException {
		CommercePriceListUserRel commercePriceListUserRel = findByPrimaryKey(commercePriceListUserRelId);

		Session session = null;

		try {
			session = openSession();

			CommercePriceListUserRel[] array = new CommercePriceListUserRelImpl[3];

			array[0] = getByUuid_C_PrevAndNext(session,
					commercePriceListUserRel, uuid, companyId,
					orderByComparator, true);

			array[1] = commercePriceListUserRel;

			array[2] = getByUuid_C_PrevAndNext(session,
					commercePriceListUserRel, uuid, companyId,
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

	protected CommercePriceListUserRel getByUuid_C_PrevAndNext(
		Session session, CommercePriceListUserRel commercePriceListUserRel,
		String uuid, long companyId,
		OrderByComparator<CommercePriceListUserRel> orderByComparator,
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

		query.append(_SQL_SELECT_COMMERCEPRICELISTUSERREL_WHERE);

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
			query.append(CommercePriceListUserRelModelImpl.ORDER_BY_JPQL);
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
			Object[] values = orderByComparator.getOrderByConditionValues(commercePriceListUserRel);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<CommercePriceListUserRel> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the commerce price list user rels where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	@Override
	public void removeByUuid_C(String uuid, long companyId) {
		for (CommercePriceListUserRel commercePriceListUserRel : findByUuid_C(
				uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(commercePriceListUserRel);
		}
	}

	/**
	 * Returns the number of commerce price list user rels where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching commerce price list user rels
	 */
	@Override
	public int countByUuid_C(String uuid, long companyId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_UUID_C;

		Object[] finderArgs = new Object[] { uuid, companyId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_COMMERCEPRICELISTUSERREL_WHERE);

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

	private static final String _FINDER_COLUMN_UUID_C_UUID_1 = "commercePriceListUserRel.uuid IS NULL AND ";
	private static final String _FINDER_COLUMN_UUID_C_UUID_2 = "commercePriceListUserRel.uuid = ? AND ";
	private static final String _FINDER_COLUMN_UUID_C_UUID_3 = "(commercePriceListUserRel.uuid IS NULL OR commercePriceListUserRel.uuid = '') AND ";
	private static final String _FINDER_COLUMN_UUID_C_COMPANYID_2 = "commercePriceListUserRel.companyId = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_COMMERCEPRICELISTQUALIFICATIONTYPERELID =
		new FinderPath(CommercePriceListUserRelModelImpl.ENTITY_CACHE_ENABLED,
			CommercePriceListUserRelModelImpl.FINDER_CACHE_ENABLED,
			CommercePriceListUserRelImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByCommercePriceListQualificationTypeRelId",
			new String[] {
				Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMMERCEPRICELISTQUALIFICATIONTYPERELID =
		new FinderPath(CommercePriceListUserRelModelImpl.ENTITY_CACHE_ENABLED,
			CommercePriceListUserRelModelImpl.FINDER_CACHE_ENABLED,
			CommercePriceListUserRelImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByCommercePriceListQualificationTypeRelId",
			new String[] { Long.class.getName() },
			CommercePriceListUserRelModelImpl.COMMERCEPRICELISTQUALIFICATIONTYPERELID_COLUMN_BITMASK |
			CommercePriceListUserRelModelImpl.CREATEDATE_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_COMMERCEPRICELISTQUALIFICATIONTYPERELID =
		new FinderPath(CommercePriceListUserRelModelImpl.ENTITY_CACHE_ENABLED,
			CommercePriceListUserRelModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByCommercePriceListQualificationTypeRelId",
			new String[] { Long.class.getName() });

	/**
	 * Returns all the commerce price list user rels where commercePriceListQualificationTypeRelId = &#63;.
	 *
	 * @param commercePriceListQualificationTypeRelId the commerce price list qualification type rel ID
	 * @return the matching commerce price list user rels
	 */
	@Override
	public List<CommercePriceListUserRel> findByCommercePriceListQualificationTypeRelId(
		long commercePriceListQualificationTypeRelId) {
		return findByCommercePriceListQualificationTypeRelId(commercePriceListQualificationTypeRelId,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce price list user rels where commercePriceListQualificationTypeRelId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommercePriceListUserRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param commercePriceListQualificationTypeRelId the commerce price list qualification type rel ID
	 * @param start the lower bound of the range of commerce price list user rels
	 * @param end the upper bound of the range of commerce price list user rels (not inclusive)
	 * @return the range of matching commerce price list user rels
	 */
	@Override
	public List<CommercePriceListUserRel> findByCommercePriceListQualificationTypeRelId(
		long commercePriceListQualificationTypeRelId, int start, int end) {
		return findByCommercePriceListQualificationTypeRelId(commercePriceListQualificationTypeRelId,
			start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce price list user rels where commercePriceListQualificationTypeRelId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommercePriceListUserRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param commercePriceListQualificationTypeRelId the commerce price list qualification type rel ID
	 * @param start the lower bound of the range of commerce price list user rels
	 * @param end the upper bound of the range of commerce price list user rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce price list user rels
	 */
	@Override
	public List<CommercePriceListUserRel> findByCommercePriceListQualificationTypeRelId(
		long commercePriceListQualificationTypeRelId, int start, int end,
		OrderByComparator<CommercePriceListUserRel> orderByComparator) {
		return findByCommercePriceListQualificationTypeRelId(commercePriceListQualificationTypeRelId,
			start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce price list user rels where commercePriceListQualificationTypeRelId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommercePriceListUserRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param commercePriceListQualificationTypeRelId the commerce price list qualification type rel ID
	 * @param start the lower bound of the range of commerce price list user rels
	 * @param end the upper bound of the range of commerce price list user rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching commerce price list user rels
	 */
	@Override
	public List<CommercePriceListUserRel> findByCommercePriceListQualificationTypeRelId(
		long commercePriceListQualificationTypeRelId, int start, int end,
		OrderByComparator<CommercePriceListUserRel> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMMERCEPRICELISTQUALIFICATIONTYPERELID;
			finderArgs = new Object[] { commercePriceListQualificationTypeRelId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_COMMERCEPRICELISTQUALIFICATIONTYPERELID;
			finderArgs = new Object[] {
					commercePriceListQualificationTypeRelId,
					
					start, end, orderByComparator
				};
		}

		List<CommercePriceListUserRel> list = null;

		if (retrieveFromCache) {
			list = (List<CommercePriceListUserRel>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (CommercePriceListUserRel commercePriceListUserRel : list) {
					if ((commercePriceListQualificationTypeRelId != commercePriceListUserRel.getCommercePriceListQualificationTypeRelId())) {
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

			query.append(_SQL_SELECT_COMMERCEPRICELISTUSERREL_WHERE);

			query.append(_FINDER_COLUMN_COMMERCEPRICELISTQUALIFICATIONTYPERELID_COMMERCEPRICELISTQUALIFICATIONTYPERELID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(CommercePriceListUserRelModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(commercePriceListQualificationTypeRelId);

				if (!pagination) {
					list = (List<CommercePriceListUserRel>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<CommercePriceListUserRel>)QueryUtil.list(q,
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
	 * Returns the first commerce price list user rel in the ordered set where commercePriceListQualificationTypeRelId = &#63;.
	 *
	 * @param commercePriceListQualificationTypeRelId the commerce price list qualification type rel ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce price list user rel
	 * @throws NoSuchPriceListUserRelException if a matching commerce price list user rel could not be found
	 */
	@Override
	public CommercePriceListUserRel findByCommercePriceListQualificationTypeRelId_First(
		long commercePriceListQualificationTypeRelId,
		OrderByComparator<CommercePriceListUserRel> orderByComparator)
		throws NoSuchPriceListUserRelException {
		CommercePriceListUserRel commercePriceListUserRel = fetchByCommercePriceListQualificationTypeRelId_First(commercePriceListQualificationTypeRelId,
				orderByComparator);

		if (commercePriceListUserRel != null) {
			return commercePriceListUserRel;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("commercePriceListQualificationTypeRelId=");
		msg.append(commercePriceListQualificationTypeRelId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchPriceListUserRelException(msg.toString());
	}

	/**
	 * Returns the first commerce price list user rel in the ordered set where commercePriceListQualificationTypeRelId = &#63;.
	 *
	 * @param commercePriceListQualificationTypeRelId the commerce price list qualification type rel ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce price list user rel, or <code>null</code> if a matching commerce price list user rel could not be found
	 */
	@Override
	public CommercePriceListUserRel fetchByCommercePriceListQualificationTypeRelId_First(
		long commercePriceListQualificationTypeRelId,
		OrderByComparator<CommercePriceListUserRel> orderByComparator) {
		List<CommercePriceListUserRel> list = findByCommercePriceListQualificationTypeRelId(commercePriceListQualificationTypeRelId,
				0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last commerce price list user rel in the ordered set where commercePriceListQualificationTypeRelId = &#63;.
	 *
	 * @param commercePriceListQualificationTypeRelId the commerce price list qualification type rel ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce price list user rel
	 * @throws NoSuchPriceListUserRelException if a matching commerce price list user rel could not be found
	 */
	@Override
	public CommercePriceListUserRel findByCommercePriceListQualificationTypeRelId_Last(
		long commercePriceListQualificationTypeRelId,
		OrderByComparator<CommercePriceListUserRel> orderByComparator)
		throws NoSuchPriceListUserRelException {
		CommercePriceListUserRel commercePriceListUserRel = fetchByCommercePriceListQualificationTypeRelId_Last(commercePriceListQualificationTypeRelId,
				orderByComparator);

		if (commercePriceListUserRel != null) {
			return commercePriceListUserRel;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("commercePriceListQualificationTypeRelId=");
		msg.append(commercePriceListQualificationTypeRelId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchPriceListUserRelException(msg.toString());
	}

	/**
	 * Returns the last commerce price list user rel in the ordered set where commercePriceListQualificationTypeRelId = &#63;.
	 *
	 * @param commercePriceListQualificationTypeRelId the commerce price list qualification type rel ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce price list user rel, or <code>null</code> if a matching commerce price list user rel could not be found
	 */
	@Override
	public CommercePriceListUserRel fetchByCommercePriceListQualificationTypeRelId_Last(
		long commercePriceListQualificationTypeRelId,
		OrderByComparator<CommercePriceListUserRel> orderByComparator) {
		int count = countByCommercePriceListQualificationTypeRelId(commercePriceListQualificationTypeRelId);

		if (count == 0) {
			return null;
		}

		List<CommercePriceListUserRel> list = findByCommercePriceListQualificationTypeRelId(commercePriceListQualificationTypeRelId,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the commerce price list user rels before and after the current commerce price list user rel in the ordered set where commercePriceListQualificationTypeRelId = &#63;.
	 *
	 * @param commercePriceListUserRelId the primary key of the current commerce price list user rel
	 * @param commercePriceListQualificationTypeRelId the commerce price list qualification type rel ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce price list user rel
	 * @throws NoSuchPriceListUserRelException if a commerce price list user rel with the primary key could not be found
	 */
	@Override
	public CommercePriceListUserRel[] findByCommercePriceListQualificationTypeRelId_PrevAndNext(
		long commercePriceListUserRelId,
		long commercePriceListQualificationTypeRelId,
		OrderByComparator<CommercePriceListUserRel> orderByComparator)
		throws NoSuchPriceListUserRelException {
		CommercePriceListUserRel commercePriceListUserRel = findByPrimaryKey(commercePriceListUserRelId);

		Session session = null;

		try {
			session = openSession();

			CommercePriceListUserRel[] array = new CommercePriceListUserRelImpl[3];

			array[0] = getByCommercePriceListQualificationTypeRelId_PrevAndNext(session,
					commercePriceListUserRel,
					commercePriceListQualificationTypeRelId, orderByComparator,
					true);

			array[1] = commercePriceListUserRel;

			array[2] = getByCommercePriceListQualificationTypeRelId_PrevAndNext(session,
					commercePriceListUserRel,
					commercePriceListQualificationTypeRelId, orderByComparator,
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

	protected CommercePriceListUserRel getByCommercePriceListQualificationTypeRelId_PrevAndNext(
		Session session, CommercePriceListUserRel commercePriceListUserRel,
		long commercePriceListQualificationTypeRelId,
		OrderByComparator<CommercePriceListUserRel> orderByComparator,
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

		query.append(_SQL_SELECT_COMMERCEPRICELISTUSERREL_WHERE);

		query.append(_FINDER_COLUMN_COMMERCEPRICELISTQUALIFICATIONTYPERELID_COMMERCEPRICELISTQUALIFICATIONTYPERELID_2);

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
			query.append(CommercePriceListUserRelModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(commercePriceListQualificationTypeRelId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(commercePriceListUserRel);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<CommercePriceListUserRel> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the commerce price list user rels where commercePriceListQualificationTypeRelId = &#63; from the database.
	 *
	 * @param commercePriceListQualificationTypeRelId the commerce price list qualification type rel ID
	 */
	@Override
	public void removeByCommercePriceListQualificationTypeRelId(
		long commercePriceListQualificationTypeRelId) {
		for (CommercePriceListUserRel commercePriceListUserRel : findByCommercePriceListQualificationTypeRelId(
				commercePriceListQualificationTypeRelId, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS, null)) {
			remove(commercePriceListUserRel);
		}
	}

	/**
	 * Returns the number of commerce price list user rels where commercePriceListQualificationTypeRelId = &#63;.
	 *
	 * @param commercePriceListQualificationTypeRelId the commerce price list qualification type rel ID
	 * @return the number of matching commerce price list user rels
	 */
	@Override
	public int countByCommercePriceListQualificationTypeRelId(
		long commercePriceListQualificationTypeRelId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_COMMERCEPRICELISTQUALIFICATIONTYPERELID;

		Object[] finderArgs = new Object[] {
				commercePriceListQualificationTypeRelId
			};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_COMMERCEPRICELISTUSERREL_WHERE);

			query.append(_FINDER_COLUMN_COMMERCEPRICELISTQUALIFICATIONTYPERELID_COMMERCEPRICELISTQUALIFICATIONTYPERELID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(commercePriceListQualificationTypeRelId);

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

	private static final String _FINDER_COLUMN_COMMERCEPRICELISTQUALIFICATIONTYPERELID_COMMERCEPRICELISTQUALIFICATIONTYPERELID_2 =
		"commercePriceListUserRel.commercePriceListQualificationTypeRelId = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_C_C = new FinderPath(CommercePriceListUserRelModelImpl.ENTITY_CACHE_ENABLED,
			CommercePriceListUserRelModelImpl.FINDER_CACHE_ENABLED,
			CommercePriceListUserRelImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByC_C",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_C = new FinderPath(CommercePriceListUserRelModelImpl.ENTITY_CACHE_ENABLED,
			CommercePriceListUserRelModelImpl.FINDER_CACHE_ENABLED,
			CommercePriceListUserRelImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByC_C",
			new String[] { Long.class.getName(), Long.class.getName() },
			CommercePriceListUserRelModelImpl.COMMERCEPRICELISTQUALIFICATIONTYPERELID_COLUMN_BITMASK |
			CommercePriceListUserRelModelImpl.CLASSNAMEID_COLUMN_BITMASK |
			CommercePriceListUserRelModelImpl.CREATEDATE_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_C_C = new FinderPath(CommercePriceListUserRelModelImpl.ENTITY_CACHE_ENABLED,
			CommercePriceListUserRelModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_C",
			new String[] { Long.class.getName(), Long.class.getName() });

	/**
	 * Returns all the commerce price list user rels where commercePriceListQualificationTypeRelId = &#63; and classNameId = &#63;.
	 *
	 * @param commercePriceListQualificationTypeRelId the commerce price list qualification type rel ID
	 * @param classNameId the class name ID
	 * @return the matching commerce price list user rels
	 */
	@Override
	public List<CommercePriceListUserRel> findByC_C(
		long commercePriceListQualificationTypeRelId, long classNameId) {
		return findByC_C(commercePriceListQualificationTypeRelId, classNameId,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce price list user rels where commercePriceListQualificationTypeRelId = &#63; and classNameId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommercePriceListUserRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param commercePriceListQualificationTypeRelId the commerce price list qualification type rel ID
	 * @param classNameId the class name ID
	 * @param start the lower bound of the range of commerce price list user rels
	 * @param end the upper bound of the range of commerce price list user rels (not inclusive)
	 * @return the range of matching commerce price list user rels
	 */
	@Override
	public List<CommercePriceListUserRel> findByC_C(
		long commercePriceListQualificationTypeRelId, long classNameId,
		int start, int end) {
		return findByC_C(commercePriceListQualificationTypeRelId, classNameId,
			start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce price list user rels where commercePriceListQualificationTypeRelId = &#63; and classNameId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommercePriceListUserRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param commercePriceListQualificationTypeRelId the commerce price list qualification type rel ID
	 * @param classNameId the class name ID
	 * @param start the lower bound of the range of commerce price list user rels
	 * @param end the upper bound of the range of commerce price list user rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce price list user rels
	 */
	@Override
	public List<CommercePriceListUserRel> findByC_C(
		long commercePriceListQualificationTypeRelId, long classNameId,
		int start, int end,
		OrderByComparator<CommercePriceListUserRel> orderByComparator) {
		return findByC_C(commercePriceListQualificationTypeRelId, classNameId,
			start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce price list user rels where commercePriceListQualificationTypeRelId = &#63; and classNameId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommercePriceListUserRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param commercePriceListQualificationTypeRelId the commerce price list qualification type rel ID
	 * @param classNameId the class name ID
	 * @param start the lower bound of the range of commerce price list user rels
	 * @param end the upper bound of the range of commerce price list user rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching commerce price list user rels
	 */
	@Override
	public List<CommercePriceListUserRel> findByC_C(
		long commercePriceListQualificationTypeRelId, long classNameId,
		int start, int end,
		OrderByComparator<CommercePriceListUserRel> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_C;
			finderArgs = new Object[] {
					commercePriceListQualificationTypeRelId, classNameId
				};
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_C_C;
			finderArgs = new Object[] {
					commercePriceListQualificationTypeRelId, classNameId,
					
					start, end, orderByComparator
				};
		}

		List<CommercePriceListUserRel> list = null;

		if (retrieveFromCache) {
			list = (List<CommercePriceListUserRel>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (CommercePriceListUserRel commercePriceListUserRel : list) {
					if ((commercePriceListQualificationTypeRelId != commercePriceListUserRel.getCommercePriceListQualificationTypeRelId()) ||
							(classNameId != commercePriceListUserRel.getClassNameId())) {
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

			query.append(_SQL_SELECT_COMMERCEPRICELISTUSERREL_WHERE);

			query.append(_FINDER_COLUMN_C_C_COMMERCEPRICELISTQUALIFICATIONTYPERELID_2);

			query.append(_FINDER_COLUMN_C_C_CLASSNAMEID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(CommercePriceListUserRelModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(commercePriceListQualificationTypeRelId);

				qPos.add(classNameId);

				if (!pagination) {
					list = (List<CommercePriceListUserRel>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<CommercePriceListUserRel>)QueryUtil.list(q,
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
	 * Returns the first commerce price list user rel in the ordered set where commercePriceListQualificationTypeRelId = &#63; and classNameId = &#63;.
	 *
	 * @param commercePriceListQualificationTypeRelId the commerce price list qualification type rel ID
	 * @param classNameId the class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce price list user rel
	 * @throws NoSuchPriceListUserRelException if a matching commerce price list user rel could not be found
	 */
	@Override
	public CommercePriceListUserRel findByC_C_First(
		long commercePriceListQualificationTypeRelId, long classNameId,
		OrderByComparator<CommercePriceListUserRel> orderByComparator)
		throws NoSuchPriceListUserRelException {
		CommercePriceListUserRel commercePriceListUserRel = fetchByC_C_First(commercePriceListQualificationTypeRelId,
				classNameId, orderByComparator);

		if (commercePriceListUserRel != null) {
			return commercePriceListUserRel;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("commercePriceListQualificationTypeRelId=");
		msg.append(commercePriceListQualificationTypeRelId);

		msg.append(", classNameId=");
		msg.append(classNameId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchPriceListUserRelException(msg.toString());
	}

	/**
	 * Returns the first commerce price list user rel in the ordered set where commercePriceListQualificationTypeRelId = &#63; and classNameId = &#63;.
	 *
	 * @param commercePriceListQualificationTypeRelId the commerce price list qualification type rel ID
	 * @param classNameId the class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce price list user rel, or <code>null</code> if a matching commerce price list user rel could not be found
	 */
	@Override
	public CommercePriceListUserRel fetchByC_C_First(
		long commercePriceListQualificationTypeRelId, long classNameId,
		OrderByComparator<CommercePriceListUserRel> orderByComparator) {
		List<CommercePriceListUserRel> list = findByC_C(commercePriceListQualificationTypeRelId,
				classNameId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last commerce price list user rel in the ordered set where commercePriceListQualificationTypeRelId = &#63; and classNameId = &#63;.
	 *
	 * @param commercePriceListQualificationTypeRelId the commerce price list qualification type rel ID
	 * @param classNameId the class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce price list user rel
	 * @throws NoSuchPriceListUserRelException if a matching commerce price list user rel could not be found
	 */
	@Override
	public CommercePriceListUserRel findByC_C_Last(
		long commercePriceListQualificationTypeRelId, long classNameId,
		OrderByComparator<CommercePriceListUserRel> orderByComparator)
		throws NoSuchPriceListUserRelException {
		CommercePriceListUserRel commercePriceListUserRel = fetchByC_C_Last(commercePriceListQualificationTypeRelId,
				classNameId, orderByComparator);

		if (commercePriceListUserRel != null) {
			return commercePriceListUserRel;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("commercePriceListQualificationTypeRelId=");
		msg.append(commercePriceListQualificationTypeRelId);

		msg.append(", classNameId=");
		msg.append(classNameId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchPriceListUserRelException(msg.toString());
	}

	/**
	 * Returns the last commerce price list user rel in the ordered set where commercePriceListQualificationTypeRelId = &#63; and classNameId = &#63;.
	 *
	 * @param commercePriceListQualificationTypeRelId the commerce price list qualification type rel ID
	 * @param classNameId the class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce price list user rel, or <code>null</code> if a matching commerce price list user rel could not be found
	 */
	@Override
	public CommercePriceListUserRel fetchByC_C_Last(
		long commercePriceListQualificationTypeRelId, long classNameId,
		OrderByComparator<CommercePriceListUserRel> orderByComparator) {
		int count = countByC_C(commercePriceListQualificationTypeRelId,
				classNameId);

		if (count == 0) {
			return null;
		}

		List<CommercePriceListUserRel> list = findByC_C(commercePriceListQualificationTypeRelId,
				classNameId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the commerce price list user rels before and after the current commerce price list user rel in the ordered set where commercePriceListQualificationTypeRelId = &#63; and classNameId = &#63;.
	 *
	 * @param commercePriceListUserRelId the primary key of the current commerce price list user rel
	 * @param commercePriceListQualificationTypeRelId the commerce price list qualification type rel ID
	 * @param classNameId the class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce price list user rel
	 * @throws NoSuchPriceListUserRelException if a commerce price list user rel with the primary key could not be found
	 */
	@Override
	public CommercePriceListUserRel[] findByC_C_PrevAndNext(
		long commercePriceListUserRelId,
		long commercePriceListQualificationTypeRelId, long classNameId,
		OrderByComparator<CommercePriceListUserRel> orderByComparator)
		throws NoSuchPriceListUserRelException {
		CommercePriceListUserRel commercePriceListUserRel = findByPrimaryKey(commercePriceListUserRelId);

		Session session = null;

		try {
			session = openSession();

			CommercePriceListUserRel[] array = new CommercePriceListUserRelImpl[3];

			array[0] = getByC_C_PrevAndNext(session, commercePriceListUserRel,
					commercePriceListQualificationTypeRelId, classNameId,
					orderByComparator, true);

			array[1] = commercePriceListUserRel;

			array[2] = getByC_C_PrevAndNext(session, commercePriceListUserRel,
					commercePriceListQualificationTypeRelId, classNameId,
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

	protected CommercePriceListUserRel getByC_C_PrevAndNext(Session session,
		CommercePriceListUserRel commercePriceListUserRel,
		long commercePriceListQualificationTypeRelId, long classNameId,
		OrderByComparator<CommercePriceListUserRel> orderByComparator,
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

		query.append(_SQL_SELECT_COMMERCEPRICELISTUSERREL_WHERE);

		query.append(_FINDER_COLUMN_C_C_COMMERCEPRICELISTQUALIFICATIONTYPERELID_2);

		query.append(_FINDER_COLUMN_C_C_CLASSNAMEID_2);

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
			query.append(CommercePriceListUserRelModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(commercePriceListQualificationTypeRelId);

		qPos.add(classNameId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(commercePriceListUserRel);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<CommercePriceListUserRel> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the commerce price list user rels where commercePriceListQualificationTypeRelId = &#63; and classNameId = &#63; from the database.
	 *
	 * @param commercePriceListQualificationTypeRelId the commerce price list qualification type rel ID
	 * @param classNameId the class name ID
	 */
	@Override
	public void removeByC_C(long commercePriceListQualificationTypeRelId,
		long classNameId) {
		for (CommercePriceListUserRel commercePriceListUserRel : findByC_C(
				commercePriceListQualificationTypeRelId, classNameId,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(commercePriceListUserRel);
		}
	}

	/**
	 * Returns the number of commerce price list user rels where commercePriceListQualificationTypeRelId = &#63; and classNameId = &#63;.
	 *
	 * @param commercePriceListQualificationTypeRelId the commerce price list qualification type rel ID
	 * @param classNameId the class name ID
	 * @return the number of matching commerce price list user rels
	 */
	@Override
	public int countByC_C(long commercePriceListQualificationTypeRelId,
		long classNameId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_C_C;

		Object[] finderArgs = new Object[] {
				commercePriceListQualificationTypeRelId, classNameId
			};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_COMMERCEPRICELISTUSERREL_WHERE);

			query.append(_FINDER_COLUMN_C_C_COMMERCEPRICELISTQUALIFICATIONTYPERELID_2);

			query.append(_FINDER_COLUMN_C_C_CLASSNAMEID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(commercePriceListQualificationTypeRelId);

				qPos.add(classNameId);

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

	private static final String _FINDER_COLUMN_C_C_COMMERCEPRICELISTQUALIFICATIONTYPERELID_2 =
		"commercePriceListUserRel.commercePriceListQualificationTypeRelId = ? AND ";
	private static final String _FINDER_COLUMN_C_C_CLASSNAMEID_2 = "commercePriceListUserRel.classNameId = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_C_C_C = new FinderPath(CommercePriceListUserRelModelImpl.ENTITY_CACHE_ENABLED,
			CommercePriceListUserRelModelImpl.FINDER_CACHE_ENABLED,
			CommercePriceListUserRelImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByC_C_C",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_C_C = new FinderPath(CommercePriceListUserRelModelImpl.ENTITY_CACHE_ENABLED,
			CommercePriceListUserRelModelImpl.FINDER_CACHE_ENABLED,
			CommercePriceListUserRelImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByC_C_C",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName()
			},
			CommercePriceListUserRelModelImpl.COMMERCEPRICELISTQUALIFICATIONTYPERELID_COLUMN_BITMASK |
			CommercePriceListUserRelModelImpl.CLASSNAMEID_COLUMN_BITMASK |
			CommercePriceListUserRelModelImpl.CLASSPK_COLUMN_BITMASK |
			CommercePriceListUserRelModelImpl.CREATEDATE_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_C_C_C = new FinderPath(CommercePriceListUserRelModelImpl.ENTITY_CACHE_ENABLED,
			CommercePriceListUserRelModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_C_C",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName()
			});

	/**
	 * Returns all the commerce price list user rels where commercePriceListQualificationTypeRelId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param commercePriceListQualificationTypeRelId the commerce price list qualification type rel ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the matching commerce price list user rels
	 */
	@Override
	public List<CommercePriceListUserRel> findByC_C_C(
		long commercePriceListQualificationTypeRelId, long classNameId,
		long classPK) {
		return findByC_C_C(commercePriceListQualificationTypeRelId,
			classNameId, classPK, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce price list user rels where commercePriceListQualificationTypeRelId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommercePriceListUserRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param commercePriceListQualificationTypeRelId the commerce price list qualification type rel ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param start the lower bound of the range of commerce price list user rels
	 * @param end the upper bound of the range of commerce price list user rels (not inclusive)
	 * @return the range of matching commerce price list user rels
	 */
	@Override
	public List<CommercePriceListUserRel> findByC_C_C(
		long commercePriceListQualificationTypeRelId, long classNameId,
		long classPK, int start, int end) {
		return findByC_C_C(commercePriceListQualificationTypeRelId,
			classNameId, classPK, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce price list user rels where commercePriceListQualificationTypeRelId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommercePriceListUserRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param commercePriceListQualificationTypeRelId the commerce price list qualification type rel ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param start the lower bound of the range of commerce price list user rels
	 * @param end the upper bound of the range of commerce price list user rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce price list user rels
	 */
	@Override
	public List<CommercePriceListUserRel> findByC_C_C(
		long commercePriceListQualificationTypeRelId, long classNameId,
		long classPK, int start, int end,
		OrderByComparator<CommercePriceListUserRel> orderByComparator) {
		return findByC_C_C(commercePriceListQualificationTypeRelId,
			classNameId, classPK, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce price list user rels where commercePriceListQualificationTypeRelId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommercePriceListUserRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param commercePriceListQualificationTypeRelId the commerce price list qualification type rel ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param start the lower bound of the range of commerce price list user rels
	 * @param end the upper bound of the range of commerce price list user rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching commerce price list user rels
	 */
	@Override
	public List<CommercePriceListUserRel> findByC_C_C(
		long commercePriceListQualificationTypeRelId, long classNameId,
		long classPK, int start, int end,
		OrderByComparator<CommercePriceListUserRel> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_C_C;
			finderArgs = new Object[] {
					commercePriceListQualificationTypeRelId, classNameId,
					classPK
				};
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_C_C_C;
			finderArgs = new Object[] {
					commercePriceListQualificationTypeRelId, classNameId,
					classPK,
					
					start, end, orderByComparator
				};
		}

		List<CommercePriceListUserRel> list = null;

		if (retrieveFromCache) {
			list = (List<CommercePriceListUserRel>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (CommercePriceListUserRel commercePriceListUserRel : list) {
					if ((commercePriceListQualificationTypeRelId != commercePriceListUserRel.getCommercePriceListQualificationTypeRelId()) ||
							(classNameId != commercePriceListUserRel.getClassNameId()) ||
							(classPK != commercePriceListUserRel.getClassPK())) {
						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(5 +
						(orderByComparator.getOrderByFields().length * 2));
			}
			else {
				query = new StringBundler(5);
			}

			query.append(_SQL_SELECT_COMMERCEPRICELISTUSERREL_WHERE);

			query.append(_FINDER_COLUMN_C_C_C_COMMERCEPRICELISTQUALIFICATIONTYPERELID_2);

			query.append(_FINDER_COLUMN_C_C_C_CLASSNAMEID_2);

			query.append(_FINDER_COLUMN_C_C_C_CLASSPK_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(CommercePriceListUserRelModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(commercePriceListQualificationTypeRelId);

				qPos.add(classNameId);

				qPos.add(classPK);

				if (!pagination) {
					list = (List<CommercePriceListUserRel>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<CommercePriceListUserRel>)QueryUtil.list(q,
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
	 * Returns the first commerce price list user rel in the ordered set where commercePriceListQualificationTypeRelId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param commercePriceListQualificationTypeRelId the commerce price list qualification type rel ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce price list user rel
	 * @throws NoSuchPriceListUserRelException if a matching commerce price list user rel could not be found
	 */
	@Override
	public CommercePriceListUserRel findByC_C_C_First(
		long commercePriceListQualificationTypeRelId, long classNameId,
		long classPK,
		OrderByComparator<CommercePriceListUserRel> orderByComparator)
		throws NoSuchPriceListUserRelException {
		CommercePriceListUserRel commercePriceListUserRel = fetchByC_C_C_First(commercePriceListQualificationTypeRelId,
				classNameId, classPK, orderByComparator);

		if (commercePriceListUserRel != null) {
			return commercePriceListUserRel;
		}

		StringBundler msg = new StringBundler(8);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("commercePriceListQualificationTypeRelId=");
		msg.append(commercePriceListQualificationTypeRelId);

		msg.append(", classNameId=");
		msg.append(classNameId);

		msg.append(", classPK=");
		msg.append(classPK);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchPriceListUserRelException(msg.toString());
	}

	/**
	 * Returns the first commerce price list user rel in the ordered set where commercePriceListQualificationTypeRelId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param commercePriceListQualificationTypeRelId the commerce price list qualification type rel ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce price list user rel, or <code>null</code> if a matching commerce price list user rel could not be found
	 */
	@Override
	public CommercePriceListUserRel fetchByC_C_C_First(
		long commercePriceListQualificationTypeRelId, long classNameId,
		long classPK,
		OrderByComparator<CommercePriceListUserRel> orderByComparator) {
		List<CommercePriceListUserRel> list = findByC_C_C(commercePriceListQualificationTypeRelId,
				classNameId, classPK, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last commerce price list user rel in the ordered set where commercePriceListQualificationTypeRelId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param commercePriceListQualificationTypeRelId the commerce price list qualification type rel ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce price list user rel
	 * @throws NoSuchPriceListUserRelException if a matching commerce price list user rel could not be found
	 */
	@Override
	public CommercePriceListUserRel findByC_C_C_Last(
		long commercePriceListQualificationTypeRelId, long classNameId,
		long classPK,
		OrderByComparator<CommercePriceListUserRel> orderByComparator)
		throws NoSuchPriceListUserRelException {
		CommercePriceListUserRel commercePriceListUserRel = fetchByC_C_C_Last(commercePriceListQualificationTypeRelId,
				classNameId, classPK, orderByComparator);

		if (commercePriceListUserRel != null) {
			return commercePriceListUserRel;
		}

		StringBundler msg = new StringBundler(8);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("commercePriceListQualificationTypeRelId=");
		msg.append(commercePriceListQualificationTypeRelId);

		msg.append(", classNameId=");
		msg.append(classNameId);

		msg.append(", classPK=");
		msg.append(classPK);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchPriceListUserRelException(msg.toString());
	}

	/**
	 * Returns the last commerce price list user rel in the ordered set where commercePriceListQualificationTypeRelId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param commercePriceListQualificationTypeRelId the commerce price list qualification type rel ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce price list user rel, or <code>null</code> if a matching commerce price list user rel could not be found
	 */
	@Override
	public CommercePriceListUserRel fetchByC_C_C_Last(
		long commercePriceListQualificationTypeRelId, long classNameId,
		long classPK,
		OrderByComparator<CommercePriceListUserRel> orderByComparator) {
		int count = countByC_C_C(commercePriceListQualificationTypeRelId,
				classNameId, classPK);

		if (count == 0) {
			return null;
		}

		List<CommercePriceListUserRel> list = findByC_C_C(commercePriceListQualificationTypeRelId,
				classNameId, classPK, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the commerce price list user rels before and after the current commerce price list user rel in the ordered set where commercePriceListQualificationTypeRelId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param commercePriceListUserRelId the primary key of the current commerce price list user rel
	 * @param commercePriceListQualificationTypeRelId the commerce price list qualification type rel ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce price list user rel
	 * @throws NoSuchPriceListUserRelException if a commerce price list user rel with the primary key could not be found
	 */
	@Override
	public CommercePriceListUserRel[] findByC_C_C_PrevAndNext(
		long commercePriceListUserRelId,
		long commercePriceListQualificationTypeRelId, long classNameId,
		long classPK,
		OrderByComparator<CommercePriceListUserRel> orderByComparator)
		throws NoSuchPriceListUserRelException {
		CommercePriceListUserRel commercePriceListUserRel = findByPrimaryKey(commercePriceListUserRelId);

		Session session = null;

		try {
			session = openSession();

			CommercePriceListUserRel[] array = new CommercePriceListUserRelImpl[3];

			array[0] = getByC_C_C_PrevAndNext(session,
					commercePriceListUserRel,
					commercePriceListQualificationTypeRelId, classNameId,
					classPK, orderByComparator, true);

			array[1] = commercePriceListUserRel;

			array[2] = getByC_C_C_PrevAndNext(session,
					commercePriceListUserRel,
					commercePriceListQualificationTypeRelId, classNameId,
					classPK, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected CommercePriceListUserRel getByC_C_C_PrevAndNext(Session session,
		CommercePriceListUserRel commercePriceListUserRel,
		long commercePriceListQualificationTypeRelId, long classNameId,
		long classPK,
		OrderByComparator<CommercePriceListUserRel> orderByComparator,
		boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(5);
		}

		query.append(_SQL_SELECT_COMMERCEPRICELISTUSERREL_WHERE);

		query.append(_FINDER_COLUMN_C_C_C_COMMERCEPRICELISTQUALIFICATIONTYPERELID_2);

		query.append(_FINDER_COLUMN_C_C_C_CLASSNAMEID_2);

		query.append(_FINDER_COLUMN_C_C_C_CLASSPK_2);

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
			query.append(CommercePriceListUserRelModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(commercePriceListQualificationTypeRelId);

		qPos.add(classNameId);

		qPos.add(classPK);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(commercePriceListUserRel);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<CommercePriceListUserRel> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the commerce price list user rels where commercePriceListQualificationTypeRelId = &#63; and classNameId = &#63; and classPK = &#63; from the database.
	 *
	 * @param commercePriceListQualificationTypeRelId the commerce price list qualification type rel ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 */
	@Override
	public void removeByC_C_C(long commercePriceListQualificationTypeRelId,
		long classNameId, long classPK) {
		for (CommercePriceListUserRel commercePriceListUserRel : findByC_C_C(
				commercePriceListQualificationTypeRelId, classNameId, classPK,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(commercePriceListUserRel);
		}
	}

	/**
	 * Returns the number of commerce price list user rels where commercePriceListQualificationTypeRelId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param commercePriceListQualificationTypeRelId the commerce price list qualification type rel ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the number of matching commerce price list user rels
	 */
	@Override
	public int countByC_C_C(long commercePriceListQualificationTypeRelId,
		long classNameId, long classPK) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_C_C_C;

		Object[] finderArgs = new Object[] {
				commercePriceListQualificationTypeRelId, classNameId, classPK
			};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_COUNT_COMMERCEPRICELISTUSERREL_WHERE);

			query.append(_FINDER_COLUMN_C_C_C_COMMERCEPRICELISTQUALIFICATIONTYPERELID_2);

			query.append(_FINDER_COLUMN_C_C_C_CLASSNAMEID_2);

			query.append(_FINDER_COLUMN_C_C_C_CLASSPK_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(commercePriceListQualificationTypeRelId);

				qPos.add(classNameId);

				qPos.add(classPK);

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

	private static final String _FINDER_COLUMN_C_C_C_COMMERCEPRICELISTQUALIFICATIONTYPERELID_2 =
		"commercePriceListUserRel.commercePriceListQualificationTypeRelId = ? AND ";
	private static final String _FINDER_COLUMN_C_C_C_CLASSNAMEID_2 = "commercePriceListUserRel.classNameId = ? AND ";
	private static final String _FINDER_COLUMN_C_C_C_CLASSPK_2 = "commercePriceListUserRel.classPK = ?";

	public CommercePriceListUserRelPersistenceImpl() {
		setModelClass(CommercePriceListUserRel.class);

		try {
			Field field = ReflectionUtil.getDeclaredField(BasePersistenceImpl.class,
					"_dbColumnNames");

			Map<String, String> dbColumnNames = new HashMap<String, String>();

			dbColumnNames.put("uuid", "uuid_");
			dbColumnNames.put("commercePriceListQualificationTypeRelId",
				"CPLQualificationTypeRelId");

			field.set(this, dbColumnNames);
		}
		catch (Exception e) {
			if (_log.isDebugEnabled()) {
				_log.debug(e, e);
			}
		}
	}

	/**
	 * Caches the commerce price list user rel in the entity cache if it is enabled.
	 *
	 * @param commercePriceListUserRel the commerce price list user rel
	 */
	@Override
	public void cacheResult(CommercePriceListUserRel commercePriceListUserRel) {
		entityCache.putResult(CommercePriceListUserRelModelImpl.ENTITY_CACHE_ENABLED,
			CommercePriceListUserRelImpl.class,
			commercePriceListUserRel.getPrimaryKey(), commercePriceListUserRel);

		finderCache.putResult(FINDER_PATH_FETCH_BY_UUID_G,
			new Object[] {
				commercePriceListUserRel.getUuid(),
				commercePriceListUserRel.getGroupId()
			}, commercePriceListUserRel);

		commercePriceListUserRel.resetOriginalValues();
	}

	/**
	 * Caches the commerce price list user rels in the entity cache if it is enabled.
	 *
	 * @param commercePriceListUserRels the commerce price list user rels
	 */
	@Override
	public void cacheResult(
		List<CommercePriceListUserRel> commercePriceListUserRels) {
		for (CommercePriceListUserRel commercePriceListUserRel : commercePriceListUserRels) {
			if (entityCache.getResult(
						CommercePriceListUserRelModelImpl.ENTITY_CACHE_ENABLED,
						CommercePriceListUserRelImpl.class,
						commercePriceListUserRel.getPrimaryKey()) == null) {
				cacheResult(commercePriceListUserRel);
			}
			else {
				commercePriceListUserRel.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all commerce price list user rels.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(CommercePriceListUserRelImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the commerce price list user rel.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(CommercePriceListUserRel commercePriceListUserRel) {
		entityCache.removeResult(CommercePriceListUserRelModelImpl.ENTITY_CACHE_ENABLED,
			CommercePriceListUserRelImpl.class,
			commercePriceListUserRel.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache((CommercePriceListUserRelModelImpl)commercePriceListUserRel,
			true);
	}

	@Override
	public void clearCache(
		List<CommercePriceListUserRel> commercePriceListUserRels) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (CommercePriceListUserRel commercePriceListUserRel : commercePriceListUserRels) {
			entityCache.removeResult(CommercePriceListUserRelModelImpl.ENTITY_CACHE_ENABLED,
				CommercePriceListUserRelImpl.class,
				commercePriceListUserRel.getPrimaryKey());

			clearUniqueFindersCache((CommercePriceListUserRelModelImpl)commercePriceListUserRel,
				true);
		}
	}

	protected void cacheUniqueFindersCache(
		CommercePriceListUserRelModelImpl commercePriceListUserRelModelImpl) {
		Object[] args = new Object[] {
				commercePriceListUserRelModelImpl.getUuid(),
				commercePriceListUserRelModelImpl.getGroupId()
			};

		finderCache.putResult(FINDER_PATH_COUNT_BY_UUID_G, args,
			Long.valueOf(1), false);
		finderCache.putResult(FINDER_PATH_FETCH_BY_UUID_G, args,
			commercePriceListUserRelModelImpl, false);
	}

	protected void clearUniqueFindersCache(
		CommercePriceListUserRelModelImpl commercePriceListUserRelModelImpl,
		boolean clearCurrent) {
		if (clearCurrent) {
			Object[] args = new Object[] {
					commercePriceListUserRelModelImpl.getUuid(),
					commercePriceListUserRelModelImpl.getGroupId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID_G, args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_UUID_G, args);
		}

		if ((commercePriceListUserRelModelImpl.getColumnBitmask() &
				FINDER_PATH_FETCH_BY_UUID_G.getColumnBitmask()) != 0) {
			Object[] args = new Object[] {
					commercePriceListUserRelModelImpl.getOriginalUuid(),
					commercePriceListUserRelModelImpl.getOriginalGroupId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID_G, args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_UUID_G, args);
		}
	}

	/**
	 * Creates a new commerce price list user rel with the primary key. Does not add the commerce price list user rel to the database.
	 *
	 * @param commercePriceListUserRelId the primary key for the new commerce price list user rel
	 * @return the new commerce price list user rel
	 */
	@Override
	public CommercePriceListUserRel create(long commercePriceListUserRelId) {
		CommercePriceListUserRel commercePriceListUserRel = new CommercePriceListUserRelImpl();

		commercePriceListUserRel.setNew(true);
		commercePriceListUserRel.setPrimaryKey(commercePriceListUserRelId);

		String uuid = PortalUUIDUtil.generate();

		commercePriceListUserRel.setUuid(uuid);

		commercePriceListUserRel.setCompanyId(companyProvider.getCompanyId());

		return commercePriceListUserRel;
	}

	/**
	 * Removes the commerce price list user rel with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param commercePriceListUserRelId the primary key of the commerce price list user rel
	 * @return the commerce price list user rel that was removed
	 * @throws NoSuchPriceListUserRelException if a commerce price list user rel with the primary key could not be found
	 */
	@Override
	public CommercePriceListUserRel remove(long commercePriceListUserRelId)
		throws NoSuchPriceListUserRelException {
		return remove((Serializable)commercePriceListUserRelId);
	}

	/**
	 * Removes the commerce price list user rel with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the commerce price list user rel
	 * @return the commerce price list user rel that was removed
	 * @throws NoSuchPriceListUserRelException if a commerce price list user rel with the primary key could not be found
	 */
	@Override
	public CommercePriceListUserRel remove(Serializable primaryKey)
		throws NoSuchPriceListUserRelException {
		Session session = null;

		try {
			session = openSession();

			CommercePriceListUserRel commercePriceListUserRel = (CommercePriceListUserRel)session.get(CommercePriceListUserRelImpl.class,
					primaryKey);

			if (commercePriceListUserRel == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchPriceListUserRelException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(commercePriceListUserRel);
		}
		catch (NoSuchPriceListUserRelException nsee) {
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
	protected CommercePriceListUserRel removeImpl(
		CommercePriceListUserRel commercePriceListUserRel) {
		commercePriceListUserRel = toUnwrappedModel(commercePriceListUserRel);

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(commercePriceListUserRel)) {
				commercePriceListUserRel = (CommercePriceListUserRel)session.get(CommercePriceListUserRelImpl.class,
						commercePriceListUserRel.getPrimaryKeyObj());
			}

			if (commercePriceListUserRel != null) {
				session.delete(commercePriceListUserRel);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (commercePriceListUserRel != null) {
			clearCache(commercePriceListUserRel);
		}

		return commercePriceListUserRel;
	}

	@Override
	public CommercePriceListUserRel updateImpl(
		CommercePriceListUserRel commercePriceListUserRel) {
		commercePriceListUserRel = toUnwrappedModel(commercePriceListUserRel);

		boolean isNew = commercePriceListUserRel.isNew();

		CommercePriceListUserRelModelImpl commercePriceListUserRelModelImpl = (CommercePriceListUserRelModelImpl)commercePriceListUserRel;

		if (Validator.isNull(commercePriceListUserRel.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			commercePriceListUserRel.setUuid(uuid);
		}

		ServiceContext serviceContext = ServiceContextThreadLocal.getServiceContext();

		Date now = new Date();

		if (isNew && (commercePriceListUserRel.getCreateDate() == null)) {
			if (serviceContext == null) {
				commercePriceListUserRel.setCreateDate(now);
			}
			else {
				commercePriceListUserRel.setCreateDate(serviceContext.getCreateDate(
						now));
			}
		}

		if (!commercePriceListUserRelModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				commercePriceListUserRel.setModifiedDate(now);
			}
			else {
				commercePriceListUserRel.setModifiedDate(serviceContext.getModifiedDate(
						now));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (commercePriceListUserRel.isNew()) {
				session.save(commercePriceListUserRel);

				commercePriceListUserRel.setNew(false);
			}
			else {
				commercePriceListUserRel = (CommercePriceListUserRel)session.merge(commercePriceListUserRel);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (!CommercePriceListUserRelModelImpl.COLUMN_BITMASK_ENABLED) {
			finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}
		else
		 if (isNew) {
			Object[] args = new Object[] {
					commercePriceListUserRelModelImpl.getUuid()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID, args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID,
				args);

			args = new Object[] {
					commercePriceListUserRelModelImpl.getUuid(),
					commercePriceListUserRelModelImpl.getCompanyId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID_C, args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C,
				args);

			args = new Object[] {
					commercePriceListUserRelModelImpl.getCommercePriceListQualificationTypeRelId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_COMMERCEPRICELISTQUALIFICATIONTYPERELID,
				args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMMERCEPRICELISTQUALIFICATIONTYPERELID,
				args);

			args = new Object[] {
					commercePriceListUserRelModelImpl.getCommercePriceListQualificationTypeRelId(),
					commercePriceListUserRelModelImpl.getClassNameId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_C_C, args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_C,
				args);

			args = new Object[] {
					commercePriceListUserRelModelImpl.getCommercePriceListQualificationTypeRelId(),
					commercePriceListUserRelModelImpl.getClassNameId(),
					commercePriceListUserRelModelImpl.getClassPK()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_C_C_C, args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_C_C,
				args);

			finderCache.removeResult(FINDER_PATH_COUNT_ALL, FINDER_ARGS_EMPTY);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL,
				FINDER_ARGS_EMPTY);
		}

		else {
			if ((commercePriceListUserRelModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						commercePriceListUserRelModelImpl.getOriginalUuid()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID,
					args);

				args = new Object[] { commercePriceListUserRelModelImpl.getUuid() };

				finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID,
					args);
			}

			if ((commercePriceListUserRelModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						commercePriceListUserRelModelImpl.getOriginalUuid(),
						commercePriceListUserRelModelImpl.getOriginalCompanyId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID_C, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C,
					args);

				args = new Object[] {
						commercePriceListUserRelModelImpl.getUuid(),
						commercePriceListUserRelModelImpl.getCompanyId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID_C, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C,
					args);
			}

			if ((commercePriceListUserRelModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMMERCEPRICELISTQUALIFICATIONTYPERELID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						commercePriceListUserRelModelImpl.getOriginalCommercePriceListQualificationTypeRelId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_COMMERCEPRICELISTQUALIFICATIONTYPERELID,
					args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMMERCEPRICELISTQUALIFICATIONTYPERELID,
					args);

				args = new Object[] {
						commercePriceListUserRelModelImpl.getCommercePriceListQualificationTypeRelId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_COMMERCEPRICELISTQUALIFICATIONTYPERELID,
					args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMMERCEPRICELISTQUALIFICATIONTYPERELID,
					args);
			}

			if ((commercePriceListUserRelModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_C.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						commercePriceListUserRelModelImpl.getOriginalCommercePriceListQualificationTypeRelId(),
						commercePriceListUserRelModelImpl.getOriginalClassNameId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_C_C, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_C,
					args);

				args = new Object[] {
						commercePriceListUserRelModelImpl.getCommercePriceListQualificationTypeRelId(),
						commercePriceListUserRelModelImpl.getClassNameId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_C_C, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_C,
					args);
			}

			if ((commercePriceListUserRelModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_C_C.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						commercePriceListUserRelModelImpl.getOriginalCommercePriceListQualificationTypeRelId(),
						commercePriceListUserRelModelImpl.getOriginalClassNameId(),
						commercePriceListUserRelModelImpl.getOriginalClassPK()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_C_C_C, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_C_C,
					args);

				args = new Object[] {
						commercePriceListUserRelModelImpl.getCommercePriceListQualificationTypeRelId(),
						commercePriceListUserRelModelImpl.getClassNameId(),
						commercePriceListUserRelModelImpl.getClassPK()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_C_C_C, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_C_C,
					args);
			}
		}

		entityCache.putResult(CommercePriceListUserRelModelImpl.ENTITY_CACHE_ENABLED,
			CommercePriceListUserRelImpl.class,
			commercePriceListUserRel.getPrimaryKey(), commercePriceListUserRel,
			false);

		clearUniqueFindersCache(commercePriceListUserRelModelImpl, false);
		cacheUniqueFindersCache(commercePriceListUserRelModelImpl);

		commercePriceListUserRel.resetOriginalValues();

		return commercePriceListUserRel;
	}

	protected CommercePriceListUserRel toUnwrappedModel(
		CommercePriceListUserRel commercePriceListUserRel) {
		if (commercePriceListUserRel instanceof CommercePriceListUserRelImpl) {
			return commercePriceListUserRel;
		}

		CommercePriceListUserRelImpl commercePriceListUserRelImpl = new CommercePriceListUserRelImpl();

		commercePriceListUserRelImpl.setNew(commercePriceListUserRel.isNew());
		commercePriceListUserRelImpl.setPrimaryKey(commercePriceListUserRel.getPrimaryKey());

		commercePriceListUserRelImpl.setUuid(commercePriceListUserRel.getUuid());
		commercePriceListUserRelImpl.setCommercePriceListUserRelId(commercePriceListUserRel.getCommercePriceListUserRelId());
		commercePriceListUserRelImpl.setGroupId(commercePriceListUserRel.getGroupId());
		commercePriceListUserRelImpl.setCompanyId(commercePriceListUserRel.getCompanyId());
		commercePriceListUserRelImpl.setUserId(commercePriceListUserRel.getUserId());
		commercePriceListUserRelImpl.setUserName(commercePriceListUserRel.getUserName());
		commercePriceListUserRelImpl.setCreateDate(commercePriceListUserRel.getCreateDate());
		commercePriceListUserRelImpl.setModifiedDate(commercePriceListUserRel.getModifiedDate());
		commercePriceListUserRelImpl.setCommercePriceListQualificationTypeRelId(commercePriceListUserRel.getCommercePriceListQualificationTypeRelId());
		commercePriceListUserRelImpl.setClassNameId(commercePriceListUserRel.getClassNameId());
		commercePriceListUserRelImpl.setClassPK(commercePriceListUserRel.getClassPK());
		commercePriceListUserRelImpl.setLastPublishDate(commercePriceListUserRel.getLastPublishDate());

		return commercePriceListUserRelImpl;
	}

	/**
	 * Returns the commerce price list user rel with the primary key or throws a {@link com.liferay.portal.kernel.exception.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the commerce price list user rel
	 * @return the commerce price list user rel
	 * @throws NoSuchPriceListUserRelException if a commerce price list user rel with the primary key could not be found
	 */
	@Override
	public CommercePriceListUserRel findByPrimaryKey(Serializable primaryKey)
		throws NoSuchPriceListUserRelException {
		CommercePriceListUserRel commercePriceListUserRel = fetchByPrimaryKey(primaryKey);

		if (commercePriceListUserRel == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchPriceListUserRelException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				primaryKey);
		}

		return commercePriceListUserRel;
	}

	/**
	 * Returns the commerce price list user rel with the primary key or throws a {@link NoSuchPriceListUserRelException} if it could not be found.
	 *
	 * @param commercePriceListUserRelId the primary key of the commerce price list user rel
	 * @return the commerce price list user rel
	 * @throws NoSuchPriceListUserRelException if a commerce price list user rel with the primary key could not be found
	 */
	@Override
	public CommercePriceListUserRel findByPrimaryKey(
		long commercePriceListUserRelId) throws NoSuchPriceListUserRelException {
		return findByPrimaryKey((Serializable)commercePriceListUserRelId);
	}

	/**
	 * Returns the commerce price list user rel with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the commerce price list user rel
	 * @return the commerce price list user rel, or <code>null</code> if a commerce price list user rel with the primary key could not be found
	 */
	@Override
	public CommercePriceListUserRel fetchByPrimaryKey(Serializable primaryKey) {
		Serializable serializable = entityCache.getResult(CommercePriceListUserRelModelImpl.ENTITY_CACHE_ENABLED,
				CommercePriceListUserRelImpl.class, primaryKey);

		if (serializable == nullModel) {
			return null;
		}

		CommercePriceListUserRel commercePriceListUserRel = (CommercePriceListUserRel)serializable;

		if (commercePriceListUserRel == null) {
			Session session = null;

			try {
				session = openSession();

				commercePriceListUserRel = (CommercePriceListUserRel)session.get(CommercePriceListUserRelImpl.class,
						primaryKey);

				if (commercePriceListUserRel != null) {
					cacheResult(commercePriceListUserRel);
				}
				else {
					entityCache.putResult(CommercePriceListUserRelModelImpl.ENTITY_CACHE_ENABLED,
						CommercePriceListUserRelImpl.class, primaryKey,
						nullModel);
				}
			}
			catch (Exception e) {
				entityCache.removeResult(CommercePriceListUserRelModelImpl.ENTITY_CACHE_ENABLED,
					CommercePriceListUserRelImpl.class, primaryKey);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return commercePriceListUserRel;
	}

	/**
	 * Returns the commerce price list user rel with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param commercePriceListUserRelId the primary key of the commerce price list user rel
	 * @return the commerce price list user rel, or <code>null</code> if a commerce price list user rel with the primary key could not be found
	 */
	@Override
	public CommercePriceListUserRel fetchByPrimaryKey(
		long commercePriceListUserRelId) {
		return fetchByPrimaryKey((Serializable)commercePriceListUserRelId);
	}

	@Override
	public Map<Serializable, CommercePriceListUserRel> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {
		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, CommercePriceListUserRel> map = new HashMap<Serializable, CommercePriceListUserRel>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			CommercePriceListUserRel commercePriceListUserRel = fetchByPrimaryKey(primaryKey);

			if (commercePriceListUserRel != null) {
				map.put(primaryKey, commercePriceListUserRel);
			}

			return map;
		}

		Set<Serializable> uncachedPrimaryKeys = null;

		for (Serializable primaryKey : primaryKeys) {
			Serializable serializable = entityCache.getResult(CommercePriceListUserRelModelImpl.ENTITY_CACHE_ENABLED,
					CommercePriceListUserRelImpl.class, primaryKey);

			if (serializable != nullModel) {
				if (serializable == null) {
					if (uncachedPrimaryKeys == null) {
						uncachedPrimaryKeys = new HashSet<Serializable>();
					}

					uncachedPrimaryKeys.add(primaryKey);
				}
				else {
					map.put(primaryKey, (CommercePriceListUserRel)serializable);
				}
			}
		}

		if (uncachedPrimaryKeys == null) {
			return map;
		}

		StringBundler query = new StringBundler((uncachedPrimaryKeys.size() * 2) +
				1);

		query.append(_SQL_SELECT_COMMERCEPRICELISTUSERREL_WHERE_PKS_IN);

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

			for (CommercePriceListUserRel commercePriceListUserRel : (List<CommercePriceListUserRel>)q.list()) {
				map.put(commercePriceListUserRel.getPrimaryKeyObj(),
					commercePriceListUserRel);

				cacheResult(commercePriceListUserRel);

				uncachedPrimaryKeys.remove(commercePriceListUserRel.getPrimaryKeyObj());
			}

			for (Serializable primaryKey : uncachedPrimaryKeys) {
				entityCache.putResult(CommercePriceListUserRelModelImpl.ENTITY_CACHE_ENABLED,
					CommercePriceListUserRelImpl.class, primaryKey, nullModel);
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
	 * Returns all the commerce price list user rels.
	 *
	 * @return the commerce price list user rels
	 */
	@Override
	public List<CommercePriceListUserRel> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce price list user rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommercePriceListUserRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce price list user rels
	 * @param end the upper bound of the range of commerce price list user rels (not inclusive)
	 * @return the range of commerce price list user rels
	 */
	@Override
	public List<CommercePriceListUserRel> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce price list user rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommercePriceListUserRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce price list user rels
	 * @param end the upper bound of the range of commerce price list user rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of commerce price list user rels
	 */
	@Override
	public List<CommercePriceListUserRel> findAll(int start, int end,
		OrderByComparator<CommercePriceListUserRel> orderByComparator) {
		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce price list user rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommercePriceListUserRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce price list user rels
	 * @param end the upper bound of the range of commerce price list user rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of commerce price list user rels
	 */
	@Override
	public List<CommercePriceListUserRel> findAll(int start, int end,
		OrderByComparator<CommercePriceListUserRel> orderByComparator,
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

		List<CommercePriceListUserRel> list = null;

		if (retrieveFromCache) {
			list = (List<CommercePriceListUserRel>)finderCache.getResult(finderPath,
					finderArgs, this);
		}

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 2));

				query.append(_SQL_SELECT_COMMERCEPRICELISTUSERREL);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_COMMERCEPRICELISTUSERREL;

				if (pagination) {
					sql = sql.concat(CommercePriceListUserRelModelImpl.ORDER_BY_JPQL);
				}
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (!pagination) {
					list = (List<CommercePriceListUserRel>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<CommercePriceListUserRel>)QueryUtil.list(q,
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
	 * Removes all the commerce price list user rels from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (CommercePriceListUserRel commercePriceListUserRel : findAll()) {
			remove(commercePriceListUserRel);
		}
	}

	/**
	 * Returns the number of commerce price list user rels.
	 *
	 * @return the number of commerce price list user rels
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_COMMERCEPRICELISTUSERREL);

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
		return CommercePriceListUserRelModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the commerce price list user rel persistence.
	 */
	public void afterPropertiesSet() {
	}

	public void destroy() {
		entityCache.removeCache(CommercePriceListUserRelImpl.class.getName());
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
	private static final String _SQL_SELECT_COMMERCEPRICELISTUSERREL = "SELECT commercePriceListUserRel FROM CommercePriceListUserRel commercePriceListUserRel";
	private static final String _SQL_SELECT_COMMERCEPRICELISTUSERREL_WHERE_PKS_IN =
		"SELECT commercePriceListUserRel FROM CommercePriceListUserRel commercePriceListUserRel WHERE commercePriceListUserRelId IN (";
	private static final String _SQL_SELECT_COMMERCEPRICELISTUSERREL_WHERE = "SELECT commercePriceListUserRel FROM CommercePriceListUserRel commercePriceListUserRel WHERE ";
	private static final String _SQL_COUNT_COMMERCEPRICELISTUSERREL = "SELECT COUNT(commercePriceListUserRel) FROM CommercePriceListUserRel commercePriceListUserRel";
	private static final String _SQL_COUNT_COMMERCEPRICELISTUSERREL_WHERE = "SELECT COUNT(commercePriceListUserRel) FROM CommercePriceListUserRel commercePriceListUserRel WHERE ";
	private static final String _ORDER_BY_ENTITY_ALIAS = "commercePriceListUserRel.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No CommercePriceListUserRel exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No CommercePriceListUserRel exists with the key {";
	private static final Log _log = LogFactoryUtil.getLog(CommercePriceListUserRelPersistenceImpl.class);
	private static final Set<String> _badColumnNames = SetUtil.fromArray(new String[] {
				"uuid", "commercePriceListQualificationTypeRelId"
			});
}