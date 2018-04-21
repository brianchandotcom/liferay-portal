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

package com.liferay.commerce.service.persistence.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.commerce.exception.NoSuchPriceListUserSegmentEntryRelException;
import com.liferay.commerce.model.CommercePriceListUserSegmentEntryRel;
import com.liferay.commerce.model.impl.CommercePriceListUserSegmentEntryRelImpl;
import com.liferay.commerce.model.impl.CommercePriceListUserSegmentEntryRelModelImpl;
import com.liferay.commerce.service.persistence.CommercePriceListUserSegmentEntryRelPersistence;

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
 * The persistence implementation for the commerce price list user segment entry rel service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Alessio Antonio Rendina
 * @see CommercePriceListUserSegmentEntryRelPersistence
 * @see com.liferay.commerce.service.persistence.CommercePriceListUserSegmentEntryRelUtil
 * @generated
 */
@ProviderType
public class CommercePriceListUserSegmentEntryRelPersistenceImpl
	extends BasePersistenceImpl<CommercePriceListUserSegmentEntryRel>
	implements CommercePriceListUserSegmentEntryRelPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link CommercePriceListUserSegmentEntryRelUtil} to access the commerce price list user segment entry rel persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = CommercePriceListUserSegmentEntryRelImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(CommercePriceListUserSegmentEntryRelModelImpl.ENTITY_CACHE_ENABLED,
			CommercePriceListUserSegmentEntryRelModelImpl.FINDER_CACHE_ENABLED,
			CommercePriceListUserSegmentEntryRelImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(CommercePriceListUserSegmentEntryRelModelImpl.ENTITY_CACHE_ENABLED,
			CommercePriceListUserSegmentEntryRelModelImpl.FINDER_CACHE_ENABLED,
			CommercePriceListUserSegmentEntryRelImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(CommercePriceListUserSegmentEntryRelModelImpl.ENTITY_CACHE_ENABLED,
			CommercePriceListUserSegmentEntryRelModelImpl.FINDER_CACHE_ENABLED,
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll",
			new String[0]);
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_UUID = new FinderPath(CommercePriceListUserSegmentEntryRelModelImpl.ENTITY_CACHE_ENABLED,
			CommercePriceListUserSegmentEntryRelModelImpl.FINDER_CACHE_ENABLED,
			CommercePriceListUserSegmentEntryRelImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUuid",
			new String[] {
				String.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID = new FinderPath(CommercePriceListUserSegmentEntryRelModelImpl.ENTITY_CACHE_ENABLED,
			CommercePriceListUserSegmentEntryRelModelImpl.FINDER_CACHE_ENABLED,
			CommercePriceListUserSegmentEntryRelImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid",
			new String[] { String.class.getName() },
			CommercePriceListUserSegmentEntryRelModelImpl.UUID_COLUMN_BITMASK |
			CommercePriceListUserSegmentEntryRelModelImpl.ORDER_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID = new FinderPath(CommercePriceListUserSegmentEntryRelModelImpl.ENTITY_CACHE_ENABLED,
			CommercePriceListUserSegmentEntryRelModelImpl.FINDER_CACHE_ENABLED,
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByUuid", new String[] { String.class.getName() });

	/**
	 * Returns all the commerce price list user segment entry rels where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching commerce price list user segment entry rels
	 */
	@Override
	public List<CommercePriceListUserSegmentEntryRel> findByUuid(String uuid) {
		return findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce price list user segment entry rels where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommercePriceListUserSegmentEntryRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of commerce price list user segment entry rels
	 * @param end the upper bound of the range of commerce price list user segment entry rels (not inclusive)
	 * @return the range of matching commerce price list user segment entry rels
	 */
	@Override
	public List<CommercePriceListUserSegmentEntryRel> findByUuid(String uuid,
		int start, int end) {
		return findByUuid(uuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce price list user segment entry rels where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommercePriceListUserSegmentEntryRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of commerce price list user segment entry rels
	 * @param end the upper bound of the range of commerce price list user segment entry rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce price list user segment entry rels
	 */
	@Override
	public List<CommercePriceListUserSegmentEntryRel> findByUuid(String uuid,
		int start, int end,
		OrderByComparator<CommercePriceListUserSegmentEntryRel> orderByComparator) {
		return findByUuid(uuid, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce price list user segment entry rels where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommercePriceListUserSegmentEntryRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of commerce price list user segment entry rels
	 * @param end the upper bound of the range of commerce price list user segment entry rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching commerce price list user segment entry rels
	 */
	@Override
	public List<CommercePriceListUserSegmentEntryRel> findByUuid(String uuid,
		int start, int end,
		OrderByComparator<CommercePriceListUserSegmentEntryRel> orderByComparator,
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

		List<CommercePriceListUserSegmentEntryRel> list = null;

		if (retrieveFromCache) {
			list = (List<CommercePriceListUserSegmentEntryRel>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (CommercePriceListUserSegmentEntryRel commercePriceListUserSegmentEntryRel : list) {
					if (!Objects.equals(uuid,
								commercePriceListUserSegmentEntryRel.getUuid())) {
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

			query.append(_SQL_SELECT_COMMERCEPRICELISTUSERSEGMENTENTRYREL_WHERE);

			boolean bindUuid = false;

			if (uuid == null) {
				query.append(_FINDER_COLUMN_UUID_UUID_1);
			}
			else if (uuid.equals("")) {
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
				query.append(CommercePriceListUserSegmentEntryRelModelImpl.ORDER_BY_JPQL);
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
					list = (List<CommercePriceListUserSegmentEntryRel>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<CommercePriceListUserSegmentEntryRel>)QueryUtil.list(q,
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
	 * Returns the first commerce price list user segment entry rel in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce price list user segment entry rel
	 * @throws NoSuchPriceListUserSegmentEntryRelException if a matching commerce price list user segment entry rel could not be found
	 */
	@Override
	public CommercePriceListUserSegmentEntryRel findByUuid_First(String uuid,
		OrderByComparator<CommercePriceListUserSegmentEntryRel> orderByComparator)
		throws NoSuchPriceListUserSegmentEntryRelException {
		CommercePriceListUserSegmentEntryRel commercePriceListUserSegmentEntryRel =
			fetchByUuid_First(uuid, orderByComparator);

		if (commercePriceListUserSegmentEntryRel != null) {
			return commercePriceListUserSegmentEntryRel;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append("}");

		throw new NoSuchPriceListUserSegmentEntryRelException(msg.toString());
	}

	/**
	 * Returns the first commerce price list user segment entry rel in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce price list user segment entry rel, or <code>null</code> if a matching commerce price list user segment entry rel could not be found
	 */
	@Override
	public CommercePriceListUserSegmentEntryRel fetchByUuid_First(String uuid,
		OrderByComparator<CommercePriceListUserSegmentEntryRel> orderByComparator) {
		List<CommercePriceListUserSegmentEntryRel> list = findByUuid(uuid, 0,
				1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last commerce price list user segment entry rel in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce price list user segment entry rel
	 * @throws NoSuchPriceListUserSegmentEntryRelException if a matching commerce price list user segment entry rel could not be found
	 */
	@Override
	public CommercePriceListUserSegmentEntryRel findByUuid_Last(String uuid,
		OrderByComparator<CommercePriceListUserSegmentEntryRel> orderByComparator)
		throws NoSuchPriceListUserSegmentEntryRelException {
		CommercePriceListUserSegmentEntryRel commercePriceListUserSegmentEntryRel =
			fetchByUuid_Last(uuid, orderByComparator);

		if (commercePriceListUserSegmentEntryRel != null) {
			return commercePriceListUserSegmentEntryRel;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append("}");

		throw new NoSuchPriceListUserSegmentEntryRelException(msg.toString());
	}

	/**
	 * Returns the last commerce price list user segment entry rel in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce price list user segment entry rel, or <code>null</code> if a matching commerce price list user segment entry rel could not be found
	 */
	@Override
	public CommercePriceListUserSegmentEntryRel fetchByUuid_Last(String uuid,
		OrderByComparator<CommercePriceListUserSegmentEntryRel> orderByComparator) {
		int count = countByUuid(uuid);

		if (count == 0) {
			return null;
		}

		List<CommercePriceListUserSegmentEntryRel> list = findByUuid(uuid,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the commerce price list user segment entry rels before and after the current commerce price list user segment entry rel in the ordered set where uuid = &#63;.
	 *
	 * @param commercePriceListUserSegmentEntryRelId the primary key of the current commerce price list user segment entry rel
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce price list user segment entry rel
	 * @throws NoSuchPriceListUserSegmentEntryRelException if a commerce price list user segment entry rel with the primary key could not be found
	 */
	@Override
	public CommercePriceListUserSegmentEntryRel[] findByUuid_PrevAndNext(
		long commercePriceListUserSegmentEntryRelId, String uuid,
		OrderByComparator<CommercePriceListUserSegmentEntryRel> orderByComparator)
		throws NoSuchPriceListUserSegmentEntryRelException {
		CommercePriceListUserSegmentEntryRel commercePriceListUserSegmentEntryRel =
			findByPrimaryKey(commercePriceListUserSegmentEntryRelId);

		Session session = null;

		try {
			session = openSession();

			CommercePriceListUserSegmentEntryRel[] array = new CommercePriceListUserSegmentEntryRelImpl[3];

			array[0] = getByUuid_PrevAndNext(session,
					commercePriceListUserSegmentEntryRel, uuid,
					orderByComparator, true);

			array[1] = commercePriceListUserSegmentEntryRel;

			array[2] = getByUuid_PrevAndNext(session,
					commercePriceListUserSegmentEntryRel, uuid,
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

	protected CommercePriceListUserSegmentEntryRel getByUuid_PrevAndNext(
		Session session,
		CommercePriceListUserSegmentEntryRel commercePriceListUserSegmentEntryRel,
		String uuid,
		OrderByComparator<CommercePriceListUserSegmentEntryRel> orderByComparator,
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

		query.append(_SQL_SELECT_COMMERCEPRICELISTUSERSEGMENTENTRYREL_WHERE);

		boolean bindUuid = false;

		if (uuid == null) {
			query.append(_FINDER_COLUMN_UUID_UUID_1);
		}
		else if (uuid.equals("")) {
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
			query.append(CommercePriceListUserSegmentEntryRelModelImpl.ORDER_BY_JPQL);
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
			Object[] values = orderByComparator.getOrderByConditionValues(commercePriceListUserSegmentEntryRel);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<CommercePriceListUserSegmentEntryRel> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the commerce price list user segment entry rels where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	@Override
	public void removeByUuid(String uuid) {
		for (CommercePriceListUserSegmentEntryRel commercePriceListUserSegmentEntryRel : findByUuid(
				uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(commercePriceListUserSegmentEntryRel);
		}
	}

	/**
	 * Returns the number of commerce price list user segment entry rels where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching commerce price list user segment entry rels
	 */
	@Override
	public int countByUuid(String uuid) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_UUID;

		Object[] finderArgs = new Object[] { uuid };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_COMMERCEPRICELISTUSERSEGMENTENTRYREL_WHERE);

			boolean bindUuid = false;

			if (uuid == null) {
				query.append(_FINDER_COLUMN_UUID_UUID_1);
			}
			else if (uuid.equals("")) {
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

	private static final String _FINDER_COLUMN_UUID_UUID_1 = "commercePriceListUserSegmentEntryRel.uuid IS NULL";
	private static final String _FINDER_COLUMN_UUID_UUID_2 = "commercePriceListUserSegmentEntryRel.uuid = ?";
	private static final String _FINDER_COLUMN_UUID_UUID_3 = "(commercePriceListUserSegmentEntryRel.uuid IS NULL OR commercePriceListUserSegmentEntryRel.uuid = '')";
	public static final FinderPath FINDER_PATH_FETCH_BY_UUID_G = new FinderPath(CommercePriceListUserSegmentEntryRelModelImpl.ENTITY_CACHE_ENABLED,
			CommercePriceListUserSegmentEntryRelModelImpl.FINDER_CACHE_ENABLED,
			CommercePriceListUserSegmentEntryRelImpl.class,
			FINDER_CLASS_NAME_ENTITY, "fetchByUUID_G",
			new String[] { String.class.getName(), Long.class.getName() },
			CommercePriceListUserSegmentEntryRelModelImpl.UUID_COLUMN_BITMASK |
			CommercePriceListUserSegmentEntryRelModelImpl.GROUPID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID_G = new FinderPath(CommercePriceListUserSegmentEntryRelModelImpl.ENTITY_CACHE_ENABLED,
			CommercePriceListUserSegmentEntryRelModelImpl.FINDER_CACHE_ENABLED,
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByUUID_G",
			new String[] { String.class.getName(), Long.class.getName() });

	/**
	 * Returns the commerce price list user segment entry rel where uuid = &#63; and groupId = &#63; or throws a {@link NoSuchPriceListUserSegmentEntryRelException} if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching commerce price list user segment entry rel
	 * @throws NoSuchPriceListUserSegmentEntryRelException if a matching commerce price list user segment entry rel could not be found
	 */
	@Override
	public CommercePriceListUserSegmentEntryRel findByUUID_G(String uuid,
		long groupId) throws NoSuchPriceListUserSegmentEntryRelException {
		CommercePriceListUserSegmentEntryRel commercePriceListUserSegmentEntryRel =
			fetchByUUID_G(uuid, groupId);

		if (commercePriceListUserSegmentEntryRel == null) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("uuid=");
			msg.append(uuid);

			msg.append(", groupId=");
			msg.append(groupId);

			msg.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(msg.toString());
			}

			throw new NoSuchPriceListUserSegmentEntryRelException(msg.toString());
		}

		return commercePriceListUserSegmentEntryRel;
	}

	/**
	 * Returns the commerce price list user segment entry rel where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching commerce price list user segment entry rel, or <code>null</code> if a matching commerce price list user segment entry rel could not be found
	 */
	@Override
	public CommercePriceListUserSegmentEntryRel fetchByUUID_G(String uuid,
		long groupId) {
		return fetchByUUID_G(uuid, groupId, true);
	}

	/**
	 * Returns the commerce price list user segment entry rel where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the matching commerce price list user segment entry rel, or <code>null</code> if a matching commerce price list user segment entry rel could not be found
	 */
	@Override
	public CommercePriceListUserSegmentEntryRel fetchByUUID_G(String uuid,
		long groupId, boolean retrieveFromCache) {
		Object[] finderArgs = new Object[] { uuid, groupId };

		Object result = null;

		if (retrieveFromCache) {
			result = finderCache.getResult(FINDER_PATH_FETCH_BY_UUID_G,
					finderArgs, this);
		}

		if (result instanceof CommercePriceListUserSegmentEntryRel) {
			CommercePriceListUserSegmentEntryRel commercePriceListUserSegmentEntryRel =
				(CommercePriceListUserSegmentEntryRel)result;

			if (!Objects.equals(uuid,
						commercePriceListUserSegmentEntryRel.getUuid()) ||
					(groupId != commercePriceListUserSegmentEntryRel.getGroupId())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_SELECT_COMMERCEPRICELISTUSERSEGMENTENTRYREL_WHERE);

			boolean bindUuid = false;

			if (uuid == null) {
				query.append(_FINDER_COLUMN_UUID_G_UUID_1);
			}
			else if (uuid.equals("")) {
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

				List<CommercePriceListUserSegmentEntryRel> list = q.list();

				if (list.isEmpty()) {
					finderCache.putResult(FINDER_PATH_FETCH_BY_UUID_G,
						finderArgs, list);
				}
				else {
					CommercePriceListUserSegmentEntryRel commercePriceListUserSegmentEntryRel =
						list.get(0);

					result = commercePriceListUserSegmentEntryRel;

					cacheResult(commercePriceListUserSegmentEntryRel);

					if ((commercePriceListUserSegmentEntryRel.getUuid() == null) ||
							!commercePriceListUserSegmentEntryRel.getUuid()
																	 .equals(uuid) ||
							(commercePriceListUserSegmentEntryRel.getGroupId() != groupId)) {
						finderCache.putResult(FINDER_PATH_FETCH_BY_UUID_G,
							finderArgs, commercePriceListUserSegmentEntryRel);
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
			return (CommercePriceListUserSegmentEntryRel)result;
		}
	}

	/**
	 * Removes the commerce price list user segment entry rel where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the commerce price list user segment entry rel that was removed
	 */
	@Override
	public CommercePriceListUserSegmentEntryRel removeByUUID_G(String uuid,
		long groupId) throws NoSuchPriceListUserSegmentEntryRelException {
		CommercePriceListUserSegmentEntryRel commercePriceListUserSegmentEntryRel =
			findByUUID_G(uuid, groupId);

		return remove(commercePriceListUserSegmentEntryRel);
	}

	/**
	 * Returns the number of commerce price list user segment entry rels where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching commerce price list user segment entry rels
	 */
	@Override
	public int countByUUID_G(String uuid, long groupId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_UUID_G;

		Object[] finderArgs = new Object[] { uuid, groupId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_COMMERCEPRICELISTUSERSEGMENTENTRYREL_WHERE);

			boolean bindUuid = false;

			if (uuid == null) {
				query.append(_FINDER_COLUMN_UUID_G_UUID_1);
			}
			else if (uuid.equals("")) {
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

	private static final String _FINDER_COLUMN_UUID_G_UUID_1 = "commercePriceListUserSegmentEntryRel.uuid IS NULL AND ";
	private static final String _FINDER_COLUMN_UUID_G_UUID_2 = "commercePriceListUserSegmentEntryRel.uuid = ? AND ";
	private static final String _FINDER_COLUMN_UUID_G_UUID_3 = "(commercePriceListUserSegmentEntryRel.uuid IS NULL OR commercePriceListUserSegmentEntryRel.uuid = '') AND ";
	private static final String _FINDER_COLUMN_UUID_G_GROUPID_2 = "commercePriceListUserSegmentEntryRel.groupId = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_UUID_C = new FinderPath(CommercePriceListUserSegmentEntryRelModelImpl.ENTITY_CACHE_ENABLED,
			CommercePriceListUserSegmentEntryRelModelImpl.FINDER_CACHE_ENABLED,
			CommercePriceListUserSegmentEntryRelImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUuid_C",
			new String[] {
				String.class.getName(), Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C =
		new FinderPath(CommercePriceListUserSegmentEntryRelModelImpl.ENTITY_CACHE_ENABLED,
			CommercePriceListUserSegmentEntryRelModelImpl.FINDER_CACHE_ENABLED,
			CommercePriceListUserSegmentEntryRelImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid_C",
			new String[] { String.class.getName(), Long.class.getName() },
			CommercePriceListUserSegmentEntryRelModelImpl.UUID_COLUMN_BITMASK |
			CommercePriceListUserSegmentEntryRelModelImpl.COMPANYID_COLUMN_BITMASK |
			CommercePriceListUserSegmentEntryRelModelImpl.ORDER_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID_C = new FinderPath(CommercePriceListUserSegmentEntryRelModelImpl.ENTITY_CACHE_ENABLED,
			CommercePriceListUserSegmentEntryRelModelImpl.FINDER_CACHE_ENABLED,
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByUuid_C",
			new String[] { String.class.getName(), Long.class.getName() });

	/**
	 * Returns all the commerce price list user segment entry rels where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching commerce price list user segment entry rels
	 */
	@Override
	public List<CommercePriceListUserSegmentEntryRel> findByUuid_C(
		String uuid, long companyId) {
		return findByUuid_C(uuid, companyId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce price list user segment entry rels where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommercePriceListUserSegmentEntryRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce price list user segment entry rels
	 * @param end the upper bound of the range of commerce price list user segment entry rels (not inclusive)
	 * @return the range of matching commerce price list user segment entry rels
	 */
	@Override
	public List<CommercePriceListUserSegmentEntryRel> findByUuid_C(
		String uuid, long companyId, int start, int end) {
		return findByUuid_C(uuid, companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce price list user segment entry rels where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommercePriceListUserSegmentEntryRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce price list user segment entry rels
	 * @param end the upper bound of the range of commerce price list user segment entry rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce price list user segment entry rels
	 */
	@Override
	public List<CommercePriceListUserSegmentEntryRel> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<CommercePriceListUserSegmentEntryRel> orderByComparator) {
		return findByUuid_C(uuid, companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce price list user segment entry rels where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommercePriceListUserSegmentEntryRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce price list user segment entry rels
	 * @param end the upper bound of the range of commerce price list user segment entry rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching commerce price list user segment entry rels
	 */
	@Override
	public List<CommercePriceListUserSegmentEntryRel> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<CommercePriceListUserSegmentEntryRel> orderByComparator,
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

		List<CommercePriceListUserSegmentEntryRel> list = null;

		if (retrieveFromCache) {
			list = (List<CommercePriceListUserSegmentEntryRel>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (CommercePriceListUserSegmentEntryRel commercePriceListUserSegmentEntryRel : list) {
					if (!Objects.equals(uuid,
								commercePriceListUserSegmentEntryRel.getUuid()) ||
							(companyId != commercePriceListUserSegmentEntryRel.getCompanyId())) {
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

			query.append(_SQL_SELECT_COMMERCEPRICELISTUSERSEGMENTENTRYREL_WHERE);

			boolean bindUuid = false;

			if (uuid == null) {
				query.append(_FINDER_COLUMN_UUID_C_UUID_1);
			}
			else if (uuid.equals("")) {
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
				query.append(CommercePriceListUserSegmentEntryRelModelImpl.ORDER_BY_JPQL);
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
					list = (List<CommercePriceListUserSegmentEntryRel>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<CommercePriceListUserSegmentEntryRel>)QueryUtil.list(q,
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
	 * Returns the first commerce price list user segment entry rel in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce price list user segment entry rel
	 * @throws NoSuchPriceListUserSegmentEntryRelException if a matching commerce price list user segment entry rel could not be found
	 */
	@Override
	public CommercePriceListUserSegmentEntryRel findByUuid_C_First(
		String uuid, long companyId,
		OrderByComparator<CommercePriceListUserSegmentEntryRel> orderByComparator)
		throws NoSuchPriceListUserSegmentEntryRelException {
		CommercePriceListUserSegmentEntryRel commercePriceListUserSegmentEntryRel =
			fetchByUuid_C_First(uuid, companyId, orderByComparator);

		if (commercePriceListUserSegmentEntryRel != null) {
			return commercePriceListUserSegmentEntryRel;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(", companyId=");
		msg.append(companyId);

		msg.append("}");

		throw new NoSuchPriceListUserSegmentEntryRelException(msg.toString());
	}

	/**
	 * Returns the first commerce price list user segment entry rel in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce price list user segment entry rel, or <code>null</code> if a matching commerce price list user segment entry rel could not be found
	 */
	@Override
	public CommercePriceListUserSegmentEntryRel fetchByUuid_C_First(
		String uuid, long companyId,
		OrderByComparator<CommercePriceListUserSegmentEntryRel> orderByComparator) {
		List<CommercePriceListUserSegmentEntryRel> list = findByUuid_C(uuid,
				companyId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last commerce price list user segment entry rel in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce price list user segment entry rel
	 * @throws NoSuchPriceListUserSegmentEntryRelException if a matching commerce price list user segment entry rel could not be found
	 */
	@Override
	public CommercePriceListUserSegmentEntryRel findByUuid_C_Last(String uuid,
		long companyId,
		OrderByComparator<CommercePriceListUserSegmentEntryRel> orderByComparator)
		throws NoSuchPriceListUserSegmentEntryRelException {
		CommercePriceListUserSegmentEntryRel commercePriceListUserSegmentEntryRel =
			fetchByUuid_C_Last(uuid, companyId, orderByComparator);

		if (commercePriceListUserSegmentEntryRel != null) {
			return commercePriceListUserSegmentEntryRel;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(", companyId=");
		msg.append(companyId);

		msg.append("}");

		throw new NoSuchPriceListUserSegmentEntryRelException(msg.toString());
	}

	/**
	 * Returns the last commerce price list user segment entry rel in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce price list user segment entry rel, or <code>null</code> if a matching commerce price list user segment entry rel could not be found
	 */
	@Override
	public CommercePriceListUserSegmentEntryRel fetchByUuid_C_Last(
		String uuid, long companyId,
		OrderByComparator<CommercePriceListUserSegmentEntryRel> orderByComparator) {
		int count = countByUuid_C(uuid, companyId);

		if (count == 0) {
			return null;
		}

		List<CommercePriceListUserSegmentEntryRel> list = findByUuid_C(uuid,
				companyId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the commerce price list user segment entry rels before and after the current commerce price list user segment entry rel in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param commercePriceListUserSegmentEntryRelId the primary key of the current commerce price list user segment entry rel
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce price list user segment entry rel
	 * @throws NoSuchPriceListUserSegmentEntryRelException if a commerce price list user segment entry rel with the primary key could not be found
	 */
	@Override
	public CommercePriceListUserSegmentEntryRel[] findByUuid_C_PrevAndNext(
		long commercePriceListUserSegmentEntryRelId, String uuid,
		long companyId,
		OrderByComparator<CommercePriceListUserSegmentEntryRel> orderByComparator)
		throws NoSuchPriceListUserSegmentEntryRelException {
		CommercePriceListUserSegmentEntryRel commercePriceListUserSegmentEntryRel =
			findByPrimaryKey(commercePriceListUserSegmentEntryRelId);

		Session session = null;

		try {
			session = openSession();

			CommercePriceListUserSegmentEntryRel[] array = new CommercePriceListUserSegmentEntryRelImpl[3];

			array[0] = getByUuid_C_PrevAndNext(session,
					commercePriceListUserSegmentEntryRel, uuid, companyId,
					orderByComparator, true);

			array[1] = commercePriceListUserSegmentEntryRel;

			array[2] = getByUuid_C_PrevAndNext(session,
					commercePriceListUserSegmentEntryRel, uuid, companyId,
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

	protected CommercePriceListUserSegmentEntryRel getByUuid_C_PrevAndNext(
		Session session,
		CommercePriceListUserSegmentEntryRel commercePriceListUserSegmentEntryRel,
		String uuid, long companyId,
		OrderByComparator<CommercePriceListUserSegmentEntryRel> orderByComparator,
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

		query.append(_SQL_SELECT_COMMERCEPRICELISTUSERSEGMENTENTRYREL_WHERE);

		boolean bindUuid = false;

		if (uuid == null) {
			query.append(_FINDER_COLUMN_UUID_C_UUID_1);
		}
		else if (uuid.equals("")) {
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
			query.append(CommercePriceListUserSegmentEntryRelModelImpl.ORDER_BY_JPQL);
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
			Object[] values = orderByComparator.getOrderByConditionValues(commercePriceListUserSegmentEntryRel);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<CommercePriceListUserSegmentEntryRel> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the commerce price list user segment entry rels where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	@Override
	public void removeByUuid_C(String uuid, long companyId) {
		for (CommercePriceListUserSegmentEntryRel commercePriceListUserSegmentEntryRel : findByUuid_C(
				uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(commercePriceListUserSegmentEntryRel);
		}
	}

	/**
	 * Returns the number of commerce price list user segment entry rels where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching commerce price list user segment entry rels
	 */
	@Override
	public int countByUuid_C(String uuid, long companyId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_UUID_C;

		Object[] finderArgs = new Object[] { uuid, companyId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_COMMERCEPRICELISTUSERSEGMENTENTRYREL_WHERE);

			boolean bindUuid = false;

			if (uuid == null) {
				query.append(_FINDER_COLUMN_UUID_C_UUID_1);
			}
			else if (uuid.equals("")) {
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

	private static final String _FINDER_COLUMN_UUID_C_UUID_1 = "commercePriceListUserSegmentEntryRel.uuid IS NULL AND ";
	private static final String _FINDER_COLUMN_UUID_C_UUID_2 = "commercePriceListUserSegmentEntryRel.uuid = ? AND ";
	private static final String _FINDER_COLUMN_UUID_C_UUID_3 = "(commercePriceListUserSegmentEntryRel.uuid IS NULL OR commercePriceListUserSegmentEntryRel.uuid = '') AND ";
	private static final String _FINDER_COLUMN_UUID_C_COMPANYID_2 = "commercePriceListUserSegmentEntryRel.companyId = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_COMMERCEPRICELISTID =
		new FinderPath(CommercePriceListUserSegmentEntryRelModelImpl.ENTITY_CACHE_ENABLED,
			CommercePriceListUserSegmentEntryRelModelImpl.FINDER_CACHE_ENABLED,
			CommercePriceListUserSegmentEntryRelImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByCommercePriceListId",
			new String[] {
				Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMMERCEPRICELISTID =
		new FinderPath(CommercePriceListUserSegmentEntryRelModelImpl.ENTITY_CACHE_ENABLED,
			CommercePriceListUserSegmentEntryRelModelImpl.FINDER_CACHE_ENABLED,
			CommercePriceListUserSegmentEntryRelImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByCommercePriceListId", new String[] { Long.class.getName() },
			CommercePriceListUserSegmentEntryRelModelImpl.COMMERCEPRICELISTID_COLUMN_BITMASK |
			CommercePriceListUserSegmentEntryRelModelImpl.ORDER_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_COMMERCEPRICELISTID = new FinderPath(CommercePriceListUserSegmentEntryRelModelImpl.ENTITY_CACHE_ENABLED,
			CommercePriceListUserSegmentEntryRelModelImpl.FINDER_CACHE_ENABLED,
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByCommercePriceListId", new String[] { Long.class.getName() });

	/**
	 * Returns all the commerce price list user segment entry rels where commercePriceListId = &#63;.
	 *
	 * @param commercePriceListId the commerce price list ID
	 * @return the matching commerce price list user segment entry rels
	 */
	@Override
	public List<CommercePriceListUserSegmentEntryRel> findByCommercePriceListId(
		long commercePriceListId) {
		return findByCommercePriceListId(commercePriceListId,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce price list user segment entry rels where commercePriceListId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommercePriceListUserSegmentEntryRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param commercePriceListId the commerce price list ID
	 * @param start the lower bound of the range of commerce price list user segment entry rels
	 * @param end the upper bound of the range of commerce price list user segment entry rels (not inclusive)
	 * @return the range of matching commerce price list user segment entry rels
	 */
	@Override
	public List<CommercePriceListUserSegmentEntryRel> findByCommercePriceListId(
		long commercePriceListId, int start, int end) {
		return findByCommercePriceListId(commercePriceListId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce price list user segment entry rels where commercePriceListId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommercePriceListUserSegmentEntryRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param commercePriceListId the commerce price list ID
	 * @param start the lower bound of the range of commerce price list user segment entry rels
	 * @param end the upper bound of the range of commerce price list user segment entry rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce price list user segment entry rels
	 */
	@Override
	public List<CommercePriceListUserSegmentEntryRel> findByCommercePriceListId(
		long commercePriceListId, int start, int end,
		OrderByComparator<CommercePriceListUserSegmentEntryRel> orderByComparator) {
		return findByCommercePriceListId(commercePriceListId, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce price list user segment entry rels where commercePriceListId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommercePriceListUserSegmentEntryRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param commercePriceListId the commerce price list ID
	 * @param start the lower bound of the range of commerce price list user segment entry rels
	 * @param end the upper bound of the range of commerce price list user segment entry rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching commerce price list user segment entry rels
	 */
	@Override
	public List<CommercePriceListUserSegmentEntryRel> findByCommercePriceListId(
		long commercePriceListId, int start, int end,
		OrderByComparator<CommercePriceListUserSegmentEntryRel> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMMERCEPRICELISTID;
			finderArgs = new Object[] { commercePriceListId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_COMMERCEPRICELISTID;
			finderArgs = new Object[] {
					commercePriceListId,
					
					start, end, orderByComparator
				};
		}

		List<CommercePriceListUserSegmentEntryRel> list = null;

		if (retrieveFromCache) {
			list = (List<CommercePriceListUserSegmentEntryRel>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (CommercePriceListUserSegmentEntryRel commercePriceListUserSegmentEntryRel : list) {
					if ((commercePriceListId != commercePriceListUserSegmentEntryRel.getCommercePriceListId())) {
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

			query.append(_SQL_SELECT_COMMERCEPRICELISTUSERSEGMENTENTRYREL_WHERE);

			query.append(_FINDER_COLUMN_COMMERCEPRICELISTID_COMMERCEPRICELISTID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(CommercePriceListUserSegmentEntryRelModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(commercePriceListId);

				if (!pagination) {
					list = (List<CommercePriceListUserSegmentEntryRel>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<CommercePriceListUserSegmentEntryRel>)QueryUtil.list(q,
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
	 * Returns the first commerce price list user segment entry rel in the ordered set where commercePriceListId = &#63;.
	 *
	 * @param commercePriceListId the commerce price list ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce price list user segment entry rel
	 * @throws NoSuchPriceListUserSegmentEntryRelException if a matching commerce price list user segment entry rel could not be found
	 */
	@Override
	public CommercePriceListUserSegmentEntryRel findByCommercePriceListId_First(
		long commercePriceListId,
		OrderByComparator<CommercePriceListUserSegmentEntryRel> orderByComparator)
		throws NoSuchPriceListUserSegmentEntryRelException {
		CommercePriceListUserSegmentEntryRel commercePriceListUserSegmentEntryRel =
			fetchByCommercePriceListId_First(commercePriceListId,
				orderByComparator);

		if (commercePriceListUserSegmentEntryRel != null) {
			return commercePriceListUserSegmentEntryRel;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("commercePriceListId=");
		msg.append(commercePriceListId);

		msg.append("}");

		throw new NoSuchPriceListUserSegmentEntryRelException(msg.toString());
	}

	/**
	 * Returns the first commerce price list user segment entry rel in the ordered set where commercePriceListId = &#63;.
	 *
	 * @param commercePriceListId the commerce price list ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce price list user segment entry rel, or <code>null</code> if a matching commerce price list user segment entry rel could not be found
	 */
	@Override
	public CommercePriceListUserSegmentEntryRel fetchByCommercePriceListId_First(
		long commercePriceListId,
		OrderByComparator<CommercePriceListUserSegmentEntryRel> orderByComparator) {
		List<CommercePriceListUserSegmentEntryRel> list = findByCommercePriceListId(commercePriceListId,
				0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last commerce price list user segment entry rel in the ordered set where commercePriceListId = &#63;.
	 *
	 * @param commercePriceListId the commerce price list ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce price list user segment entry rel
	 * @throws NoSuchPriceListUserSegmentEntryRelException if a matching commerce price list user segment entry rel could not be found
	 */
	@Override
	public CommercePriceListUserSegmentEntryRel findByCommercePriceListId_Last(
		long commercePriceListId,
		OrderByComparator<CommercePriceListUserSegmentEntryRel> orderByComparator)
		throws NoSuchPriceListUserSegmentEntryRelException {
		CommercePriceListUserSegmentEntryRel commercePriceListUserSegmentEntryRel =
			fetchByCommercePriceListId_Last(commercePriceListId,
				orderByComparator);

		if (commercePriceListUserSegmentEntryRel != null) {
			return commercePriceListUserSegmentEntryRel;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("commercePriceListId=");
		msg.append(commercePriceListId);

		msg.append("}");

		throw new NoSuchPriceListUserSegmentEntryRelException(msg.toString());
	}

	/**
	 * Returns the last commerce price list user segment entry rel in the ordered set where commercePriceListId = &#63;.
	 *
	 * @param commercePriceListId the commerce price list ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce price list user segment entry rel, or <code>null</code> if a matching commerce price list user segment entry rel could not be found
	 */
	@Override
	public CommercePriceListUserSegmentEntryRel fetchByCommercePriceListId_Last(
		long commercePriceListId,
		OrderByComparator<CommercePriceListUserSegmentEntryRel> orderByComparator) {
		int count = countByCommercePriceListId(commercePriceListId);

		if (count == 0) {
			return null;
		}

		List<CommercePriceListUserSegmentEntryRel> list = findByCommercePriceListId(commercePriceListId,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the commerce price list user segment entry rels before and after the current commerce price list user segment entry rel in the ordered set where commercePriceListId = &#63;.
	 *
	 * @param commercePriceListUserSegmentEntryRelId the primary key of the current commerce price list user segment entry rel
	 * @param commercePriceListId the commerce price list ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce price list user segment entry rel
	 * @throws NoSuchPriceListUserSegmentEntryRelException if a commerce price list user segment entry rel with the primary key could not be found
	 */
	@Override
	public CommercePriceListUserSegmentEntryRel[] findByCommercePriceListId_PrevAndNext(
		long commercePriceListUserSegmentEntryRelId, long commercePriceListId,
		OrderByComparator<CommercePriceListUserSegmentEntryRel> orderByComparator)
		throws NoSuchPriceListUserSegmentEntryRelException {
		CommercePriceListUserSegmentEntryRel commercePriceListUserSegmentEntryRel =
			findByPrimaryKey(commercePriceListUserSegmentEntryRelId);

		Session session = null;

		try {
			session = openSession();

			CommercePriceListUserSegmentEntryRel[] array = new CommercePriceListUserSegmentEntryRelImpl[3];

			array[0] = getByCommercePriceListId_PrevAndNext(session,
					commercePriceListUserSegmentEntryRel, commercePriceListId,
					orderByComparator, true);

			array[1] = commercePriceListUserSegmentEntryRel;

			array[2] = getByCommercePriceListId_PrevAndNext(session,
					commercePriceListUserSegmentEntryRel, commercePriceListId,
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

	protected CommercePriceListUserSegmentEntryRel getByCommercePriceListId_PrevAndNext(
		Session session,
		CommercePriceListUserSegmentEntryRel commercePriceListUserSegmentEntryRel,
		long commercePriceListId,
		OrderByComparator<CommercePriceListUserSegmentEntryRel> orderByComparator,
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

		query.append(_SQL_SELECT_COMMERCEPRICELISTUSERSEGMENTENTRYREL_WHERE);

		query.append(_FINDER_COLUMN_COMMERCEPRICELISTID_COMMERCEPRICELISTID_2);

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
			query.append(CommercePriceListUserSegmentEntryRelModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(commercePriceListId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(commercePriceListUserSegmentEntryRel);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<CommercePriceListUserSegmentEntryRel> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the commerce price list user segment entry rels where commercePriceListId = &#63; from the database.
	 *
	 * @param commercePriceListId the commerce price list ID
	 */
	@Override
	public void removeByCommercePriceListId(long commercePriceListId) {
		for (CommercePriceListUserSegmentEntryRel commercePriceListUserSegmentEntryRel : findByCommercePriceListId(
				commercePriceListId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(commercePriceListUserSegmentEntryRel);
		}
	}

	/**
	 * Returns the number of commerce price list user segment entry rels where commercePriceListId = &#63;.
	 *
	 * @param commercePriceListId the commerce price list ID
	 * @return the number of matching commerce price list user segment entry rels
	 */
	@Override
	public int countByCommercePriceListId(long commercePriceListId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_COMMERCEPRICELISTID;

		Object[] finderArgs = new Object[] { commercePriceListId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_COMMERCEPRICELISTUSERSEGMENTENTRYREL_WHERE);

			query.append(_FINDER_COLUMN_COMMERCEPRICELISTID_COMMERCEPRICELISTID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(commercePriceListId);

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

	private static final String _FINDER_COLUMN_COMMERCEPRICELISTID_COMMERCEPRICELISTID_2 =
		"commercePriceListUserSegmentEntryRel.commercePriceListId = ?";
	public static final FinderPath FINDER_PATH_FETCH_BY_C_C = new FinderPath(CommercePriceListUserSegmentEntryRelModelImpl.ENTITY_CACHE_ENABLED,
			CommercePriceListUserSegmentEntryRelModelImpl.FINDER_CACHE_ENABLED,
			CommercePriceListUserSegmentEntryRelImpl.class,
			FINDER_CLASS_NAME_ENTITY, "fetchByC_C",
			new String[] { Long.class.getName(), Long.class.getName() },
			CommercePriceListUserSegmentEntryRelModelImpl.COMMERCEPRICELISTID_COLUMN_BITMASK |
			CommercePriceListUserSegmentEntryRelModelImpl.COMMERCEUSERSEGMENTENTRYID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_C_C = new FinderPath(CommercePriceListUserSegmentEntryRelModelImpl.ENTITY_CACHE_ENABLED,
			CommercePriceListUserSegmentEntryRelModelImpl.FINDER_CACHE_ENABLED,
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByC_C",
			new String[] { Long.class.getName(), Long.class.getName() });

	/**
	 * Returns the commerce price list user segment entry rel where commercePriceListId = &#63; and commerceUserSegmentEntryId = &#63; or throws a {@link NoSuchPriceListUserSegmentEntryRelException} if it could not be found.
	 *
	 * @param commercePriceListId the commerce price list ID
	 * @param commerceUserSegmentEntryId the commerce user segment entry ID
	 * @return the matching commerce price list user segment entry rel
	 * @throws NoSuchPriceListUserSegmentEntryRelException if a matching commerce price list user segment entry rel could not be found
	 */
	@Override
	public CommercePriceListUserSegmentEntryRel findByC_C(
		long commercePriceListId, long commerceUserSegmentEntryId)
		throws NoSuchPriceListUserSegmentEntryRelException {
		CommercePriceListUserSegmentEntryRel commercePriceListUserSegmentEntryRel =
			fetchByC_C(commercePriceListId, commerceUserSegmentEntryId);

		if (commercePriceListUserSegmentEntryRel == null) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("commercePriceListId=");
			msg.append(commercePriceListId);

			msg.append(", commerceUserSegmentEntryId=");
			msg.append(commerceUserSegmentEntryId);

			msg.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(msg.toString());
			}

			throw new NoSuchPriceListUserSegmentEntryRelException(msg.toString());
		}

		return commercePriceListUserSegmentEntryRel;
	}

	/**
	 * Returns the commerce price list user segment entry rel where commercePriceListId = &#63; and commerceUserSegmentEntryId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param commercePriceListId the commerce price list ID
	 * @param commerceUserSegmentEntryId the commerce user segment entry ID
	 * @return the matching commerce price list user segment entry rel, or <code>null</code> if a matching commerce price list user segment entry rel could not be found
	 */
	@Override
	public CommercePriceListUserSegmentEntryRel fetchByC_C(
		long commercePriceListId, long commerceUserSegmentEntryId) {
		return fetchByC_C(commercePriceListId, commerceUserSegmentEntryId, true);
	}

	/**
	 * Returns the commerce price list user segment entry rel where commercePriceListId = &#63; and commerceUserSegmentEntryId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param commercePriceListId the commerce price list ID
	 * @param commerceUserSegmentEntryId the commerce user segment entry ID
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the matching commerce price list user segment entry rel, or <code>null</code> if a matching commerce price list user segment entry rel could not be found
	 */
	@Override
	public CommercePriceListUserSegmentEntryRel fetchByC_C(
		long commercePriceListId, long commerceUserSegmentEntryId,
		boolean retrieveFromCache) {
		Object[] finderArgs = new Object[] {
				commercePriceListId, commerceUserSegmentEntryId
			};

		Object result = null;

		if (retrieveFromCache) {
			result = finderCache.getResult(FINDER_PATH_FETCH_BY_C_C,
					finderArgs, this);
		}

		if (result instanceof CommercePriceListUserSegmentEntryRel) {
			CommercePriceListUserSegmentEntryRel commercePriceListUserSegmentEntryRel =
				(CommercePriceListUserSegmentEntryRel)result;

			if ((commercePriceListId != commercePriceListUserSegmentEntryRel.getCommercePriceListId()) ||
					(commerceUserSegmentEntryId != commercePriceListUserSegmentEntryRel.getCommerceUserSegmentEntryId())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_SELECT_COMMERCEPRICELISTUSERSEGMENTENTRYREL_WHERE);

			query.append(_FINDER_COLUMN_C_C_COMMERCEPRICELISTID_2);

			query.append(_FINDER_COLUMN_C_C_COMMERCEUSERSEGMENTENTRYID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(commercePriceListId);

				qPos.add(commerceUserSegmentEntryId);

				List<CommercePriceListUserSegmentEntryRel> list = q.list();

				if (list.isEmpty()) {
					finderCache.putResult(FINDER_PATH_FETCH_BY_C_C, finderArgs,
						list);
				}
				else {
					CommercePriceListUserSegmentEntryRel commercePriceListUserSegmentEntryRel =
						list.get(0);

					result = commercePriceListUserSegmentEntryRel;

					cacheResult(commercePriceListUserSegmentEntryRel);

					if ((commercePriceListUserSegmentEntryRel.getCommercePriceListId() != commercePriceListId) ||
							(commercePriceListUserSegmentEntryRel.getCommerceUserSegmentEntryId() != commerceUserSegmentEntryId)) {
						finderCache.putResult(FINDER_PATH_FETCH_BY_C_C,
							finderArgs, commercePriceListUserSegmentEntryRel);
					}
				}
			}
			catch (Exception e) {
				finderCache.removeResult(FINDER_PATH_FETCH_BY_C_C, finderArgs);

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
			return (CommercePriceListUserSegmentEntryRel)result;
		}
	}

	/**
	 * Removes the commerce price list user segment entry rel where commercePriceListId = &#63; and commerceUserSegmentEntryId = &#63; from the database.
	 *
	 * @param commercePriceListId the commerce price list ID
	 * @param commerceUserSegmentEntryId the commerce user segment entry ID
	 * @return the commerce price list user segment entry rel that was removed
	 */
	@Override
	public CommercePriceListUserSegmentEntryRel removeByC_C(
		long commercePriceListId, long commerceUserSegmentEntryId)
		throws NoSuchPriceListUserSegmentEntryRelException {
		CommercePriceListUserSegmentEntryRel commercePriceListUserSegmentEntryRel =
			findByC_C(commercePriceListId, commerceUserSegmentEntryId);

		return remove(commercePriceListUserSegmentEntryRel);
	}

	/**
	 * Returns the number of commerce price list user segment entry rels where commercePriceListId = &#63; and commerceUserSegmentEntryId = &#63;.
	 *
	 * @param commercePriceListId the commerce price list ID
	 * @param commerceUserSegmentEntryId the commerce user segment entry ID
	 * @return the number of matching commerce price list user segment entry rels
	 */
	@Override
	public int countByC_C(long commercePriceListId,
		long commerceUserSegmentEntryId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_C_C;

		Object[] finderArgs = new Object[] {
				commercePriceListId, commerceUserSegmentEntryId
			};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_COMMERCEPRICELISTUSERSEGMENTENTRYREL_WHERE);

			query.append(_FINDER_COLUMN_C_C_COMMERCEPRICELISTID_2);

			query.append(_FINDER_COLUMN_C_C_COMMERCEUSERSEGMENTENTRYID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(commercePriceListId);

				qPos.add(commerceUserSegmentEntryId);

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

	private static final String _FINDER_COLUMN_C_C_COMMERCEPRICELISTID_2 = "commercePriceListUserSegmentEntryRel.commercePriceListId = ? AND ";
	private static final String _FINDER_COLUMN_C_C_COMMERCEUSERSEGMENTENTRYID_2 = "commercePriceListUserSegmentEntryRel.commerceUserSegmentEntryId = ?";

	public CommercePriceListUserSegmentEntryRelPersistenceImpl() {
		setModelClass(CommercePriceListUserSegmentEntryRel.class);

		try {
			Field field = BasePersistenceImpl.class.getDeclaredField(
					"_dbColumnNames");

			field.setAccessible(true);

			Map<String, String> dbColumnNames = new HashMap<String, String>();

			dbColumnNames.put("uuid", "uuid_");
			dbColumnNames.put("commercePriceListUserSegmentEntryRelId",
				"CPLUserSegmentEntryRelId");
			dbColumnNames.put("order", "order_");

			field.set(this, dbColumnNames);
		}
		catch (Exception e) {
			if (_log.isDebugEnabled()) {
				_log.debug(e, e);
			}
		}
	}

	/**
	 * Caches the commerce price list user segment entry rel in the entity cache if it is enabled.
	 *
	 * @param commercePriceListUserSegmentEntryRel the commerce price list user segment entry rel
	 */
	@Override
	public void cacheResult(
		CommercePriceListUserSegmentEntryRel commercePriceListUserSegmentEntryRel) {
		entityCache.putResult(CommercePriceListUserSegmentEntryRelModelImpl.ENTITY_CACHE_ENABLED,
			CommercePriceListUserSegmentEntryRelImpl.class,
			commercePriceListUserSegmentEntryRel.getPrimaryKey(),
			commercePriceListUserSegmentEntryRel);

		finderCache.putResult(FINDER_PATH_FETCH_BY_UUID_G,
			new Object[] {
				commercePriceListUserSegmentEntryRel.getUuid(),
				commercePriceListUserSegmentEntryRel.getGroupId()
			}, commercePriceListUserSegmentEntryRel);

		finderCache.putResult(FINDER_PATH_FETCH_BY_C_C,
			new Object[] {
				commercePriceListUserSegmentEntryRel.getCommercePriceListId(),
				commercePriceListUserSegmentEntryRel.getCommerceUserSegmentEntryId()
			}, commercePriceListUserSegmentEntryRel);

		commercePriceListUserSegmentEntryRel.resetOriginalValues();
	}

	/**
	 * Caches the commerce price list user segment entry rels in the entity cache if it is enabled.
	 *
	 * @param commercePriceListUserSegmentEntryRels the commerce price list user segment entry rels
	 */
	@Override
	public void cacheResult(
		List<CommercePriceListUserSegmentEntryRel> commercePriceListUserSegmentEntryRels) {
		for (CommercePriceListUserSegmentEntryRel commercePriceListUserSegmentEntryRel : commercePriceListUserSegmentEntryRels) {
			if (entityCache.getResult(
						CommercePriceListUserSegmentEntryRelModelImpl.ENTITY_CACHE_ENABLED,
						CommercePriceListUserSegmentEntryRelImpl.class,
						commercePriceListUserSegmentEntryRel.getPrimaryKey()) == null) {
				cacheResult(commercePriceListUserSegmentEntryRel);
			}
			else {
				commercePriceListUserSegmentEntryRel.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all commerce price list user segment entry rels.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(CommercePriceListUserSegmentEntryRelImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the commerce price list user segment entry rel.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(
		CommercePriceListUserSegmentEntryRel commercePriceListUserSegmentEntryRel) {
		entityCache.removeResult(CommercePriceListUserSegmentEntryRelModelImpl.ENTITY_CACHE_ENABLED,
			CommercePriceListUserSegmentEntryRelImpl.class,
			commercePriceListUserSegmentEntryRel.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache((CommercePriceListUserSegmentEntryRelModelImpl)commercePriceListUserSegmentEntryRel,
			true);
	}

	@Override
	public void clearCache(
		List<CommercePriceListUserSegmentEntryRel> commercePriceListUserSegmentEntryRels) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (CommercePriceListUserSegmentEntryRel commercePriceListUserSegmentEntryRel : commercePriceListUserSegmentEntryRels) {
			entityCache.removeResult(CommercePriceListUserSegmentEntryRelModelImpl.ENTITY_CACHE_ENABLED,
				CommercePriceListUserSegmentEntryRelImpl.class,
				commercePriceListUserSegmentEntryRel.getPrimaryKey());

			clearUniqueFindersCache((CommercePriceListUserSegmentEntryRelModelImpl)commercePriceListUserSegmentEntryRel,
				true);
		}
	}

	protected void cacheUniqueFindersCache(
		CommercePriceListUserSegmentEntryRelModelImpl commercePriceListUserSegmentEntryRelModelImpl) {
		Object[] args = new Object[] {
				commercePriceListUserSegmentEntryRelModelImpl.getUuid(),
				commercePriceListUserSegmentEntryRelModelImpl.getGroupId()
			};

		finderCache.putResult(FINDER_PATH_COUNT_BY_UUID_G, args,
			Long.valueOf(1), false);
		finderCache.putResult(FINDER_PATH_FETCH_BY_UUID_G, args,
			commercePriceListUserSegmentEntryRelModelImpl, false);

		args = new Object[] {
				commercePriceListUserSegmentEntryRelModelImpl.getCommercePriceListId(),
				commercePriceListUserSegmentEntryRelModelImpl.getCommerceUserSegmentEntryId()
			};

		finderCache.putResult(FINDER_PATH_COUNT_BY_C_C, args, Long.valueOf(1),
			false);
		finderCache.putResult(FINDER_PATH_FETCH_BY_C_C, args,
			commercePriceListUserSegmentEntryRelModelImpl, false);
	}

	protected void clearUniqueFindersCache(
		CommercePriceListUserSegmentEntryRelModelImpl commercePriceListUserSegmentEntryRelModelImpl,
		boolean clearCurrent) {
		if (clearCurrent) {
			Object[] args = new Object[] {
					commercePriceListUserSegmentEntryRelModelImpl.getUuid(),
					commercePriceListUserSegmentEntryRelModelImpl.getGroupId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID_G, args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_UUID_G, args);
		}

		if ((commercePriceListUserSegmentEntryRelModelImpl.getColumnBitmask() &
				FINDER_PATH_FETCH_BY_UUID_G.getColumnBitmask()) != 0) {
			Object[] args = new Object[] {
					commercePriceListUserSegmentEntryRelModelImpl.getOriginalUuid(),
					commercePriceListUserSegmentEntryRelModelImpl.getOriginalGroupId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID_G, args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_UUID_G, args);
		}

		if (clearCurrent) {
			Object[] args = new Object[] {
					commercePriceListUserSegmentEntryRelModelImpl.getCommercePriceListId(),
					commercePriceListUserSegmentEntryRelModelImpl.getCommerceUserSegmentEntryId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_C_C, args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_C_C, args);
		}

		if ((commercePriceListUserSegmentEntryRelModelImpl.getColumnBitmask() &
				FINDER_PATH_FETCH_BY_C_C.getColumnBitmask()) != 0) {
			Object[] args = new Object[] {
					commercePriceListUserSegmentEntryRelModelImpl.getOriginalCommercePriceListId(),
					commercePriceListUserSegmentEntryRelModelImpl.getOriginalCommerceUserSegmentEntryId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_C_C, args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_C_C, args);
		}
	}

	/**
	 * Creates a new commerce price list user segment entry rel with the primary key. Does not add the commerce price list user segment entry rel to the database.
	 *
	 * @param commercePriceListUserSegmentEntryRelId the primary key for the new commerce price list user segment entry rel
	 * @return the new commerce price list user segment entry rel
	 */
	@Override
	public CommercePriceListUserSegmentEntryRel create(
		long commercePriceListUserSegmentEntryRelId) {
		CommercePriceListUserSegmentEntryRel commercePriceListUserSegmentEntryRel =
			new CommercePriceListUserSegmentEntryRelImpl();

		commercePriceListUserSegmentEntryRel.setNew(true);
		commercePriceListUserSegmentEntryRel.setPrimaryKey(commercePriceListUserSegmentEntryRelId);

		String uuid = PortalUUIDUtil.generate();

		commercePriceListUserSegmentEntryRel.setUuid(uuid);

		commercePriceListUserSegmentEntryRel.setCompanyId(companyProvider.getCompanyId());

		return commercePriceListUserSegmentEntryRel;
	}

	/**
	 * Removes the commerce price list user segment entry rel with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param commercePriceListUserSegmentEntryRelId the primary key of the commerce price list user segment entry rel
	 * @return the commerce price list user segment entry rel that was removed
	 * @throws NoSuchPriceListUserSegmentEntryRelException if a commerce price list user segment entry rel with the primary key could not be found
	 */
	@Override
	public CommercePriceListUserSegmentEntryRel remove(
		long commercePriceListUserSegmentEntryRelId)
		throws NoSuchPriceListUserSegmentEntryRelException {
		return remove((Serializable)commercePriceListUserSegmentEntryRelId);
	}

	/**
	 * Removes the commerce price list user segment entry rel with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the commerce price list user segment entry rel
	 * @return the commerce price list user segment entry rel that was removed
	 * @throws NoSuchPriceListUserSegmentEntryRelException if a commerce price list user segment entry rel with the primary key could not be found
	 */
	@Override
	public CommercePriceListUserSegmentEntryRel remove(Serializable primaryKey)
		throws NoSuchPriceListUserSegmentEntryRelException {
		Session session = null;

		try {
			session = openSession();

			CommercePriceListUserSegmentEntryRel commercePriceListUserSegmentEntryRel =
				(CommercePriceListUserSegmentEntryRel)session.get(CommercePriceListUserSegmentEntryRelImpl.class,
					primaryKey);

			if (commercePriceListUserSegmentEntryRel == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchPriceListUserSegmentEntryRelException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(commercePriceListUserSegmentEntryRel);
		}
		catch (NoSuchPriceListUserSegmentEntryRelException nsee) {
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
	protected CommercePriceListUserSegmentEntryRel removeImpl(
		CommercePriceListUserSegmentEntryRel commercePriceListUserSegmentEntryRel) {
		commercePriceListUserSegmentEntryRel = toUnwrappedModel(commercePriceListUserSegmentEntryRel);

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(commercePriceListUserSegmentEntryRel)) {
				commercePriceListUserSegmentEntryRel = (CommercePriceListUserSegmentEntryRel)session.get(CommercePriceListUserSegmentEntryRelImpl.class,
						commercePriceListUserSegmentEntryRel.getPrimaryKeyObj());
			}

			if (commercePriceListUserSegmentEntryRel != null) {
				session.delete(commercePriceListUserSegmentEntryRel);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (commercePriceListUserSegmentEntryRel != null) {
			clearCache(commercePriceListUserSegmentEntryRel);
		}

		return commercePriceListUserSegmentEntryRel;
	}

	@Override
	public CommercePriceListUserSegmentEntryRel updateImpl(
		CommercePriceListUserSegmentEntryRel commercePriceListUserSegmentEntryRel) {
		commercePriceListUserSegmentEntryRel = toUnwrappedModel(commercePriceListUserSegmentEntryRel);

		boolean isNew = commercePriceListUserSegmentEntryRel.isNew();

		CommercePriceListUserSegmentEntryRelModelImpl commercePriceListUserSegmentEntryRelModelImpl =
			(CommercePriceListUserSegmentEntryRelModelImpl)commercePriceListUserSegmentEntryRel;

		if (Validator.isNull(commercePriceListUserSegmentEntryRel.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			commercePriceListUserSegmentEntryRel.setUuid(uuid);
		}

		ServiceContext serviceContext = ServiceContextThreadLocal.getServiceContext();

		Date now = new Date();

		if (isNew &&
				(commercePriceListUserSegmentEntryRel.getCreateDate() == null)) {
			if (serviceContext == null) {
				commercePriceListUserSegmentEntryRel.setCreateDate(now);
			}
			else {
				commercePriceListUserSegmentEntryRel.setCreateDate(serviceContext.getCreateDate(
						now));
			}
		}

		if (!commercePriceListUserSegmentEntryRelModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				commercePriceListUserSegmentEntryRel.setModifiedDate(now);
			}
			else {
				commercePriceListUserSegmentEntryRel.setModifiedDate(serviceContext.getModifiedDate(
						now));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (commercePriceListUserSegmentEntryRel.isNew()) {
				session.save(commercePriceListUserSegmentEntryRel);

				commercePriceListUserSegmentEntryRel.setNew(false);
			}
			else {
				commercePriceListUserSegmentEntryRel = (CommercePriceListUserSegmentEntryRel)session.merge(commercePriceListUserSegmentEntryRel);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (!CommercePriceListUserSegmentEntryRelModelImpl.COLUMN_BITMASK_ENABLED) {
			finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}
		else
		 if (isNew) {
			Object[] args = new Object[] {
					commercePriceListUserSegmentEntryRelModelImpl.getUuid()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID, args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID,
				args);

			args = new Object[] {
					commercePriceListUserSegmentEntryRelModelImpl.getUuid(),
					commercePriceListUserSegmentEntryRelModelImpl.getCompanyId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID_C, args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C,
				args);

			args = new Object[] {
					commercePriceListUserSegmentEntryRelModelImpl.getCommercePriceListId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_COMMERCEPRICELISTID,
				args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMMERCEPRICELISTID,
				args);

			finderCache.removeResult(FINDER_PATH_COUNT_ALL, FINDER_ARGS_EMPTY);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL,
				FINDER_ARGS_EMPTY);
		}

		else {
			if ((commercePriceListUserSegmentEntryRelModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						commercePriceListUserSegmentEntryRelModelImpl.getOriginalUuid()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID,
					args);

				args = new Object[] {
						commercePriceListUserSegmentEntryRelModelImpl.getUuid()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID,
					args);
			}

			if ((commercePriceListUserSegmentEntryRelModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						commercePriceListUserSegmentEntryRelModelImpl.getOriginalUuid(),
						commercePriceListUserSegmentEntryRelModelImpl.getOriginalCompanyId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID_C, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C,
					args);

				args = new Object[] {
						commercePriceListUserSegmentEntryRelModelImpl.getUuid(),
						commercePriceListUserSegmentEntryRelModelImpl.getCompanyId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID_C, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C,
					args);
			}

			if ((commercePriceListUserSegmentEntryRelModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMMERCEPRICELISTID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						commercePriceListUserSegmentEntryRelModelImpl.getOriginalCommercePriceListId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_COMMERCEPRICELISTID,
					args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMMERCEPRICELISTID,
					args);

				args = new Object[] {
						commercePriceListUserSegmentEntryRelModelImpl.getCommercePriceListId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_COMMERCEPRICELISTID,
					args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMMERCEPRICELISTID,
					args);
			}
		}

		entityCache.putResult(CommercePriceListUserSegmentEntryRelModelImpl.ENTITY_CACHE_ENABLED,
			CommercePriceListUserSegmentEntryRelImpl.class,
			commercePriceListUserSegmentEntryRel.getPrimaryKey(),
			commercePriceListUserSegmentEntryRel, false);

		clearUniqueFindersCache(commercePriceListUserSegmentEntryRelModelImpl,
			false);
		cacheUniqueFindersCache(commercePriceListUserSegmentEntryRelModelImpl);

		commercePriceListUserSegmentEntryRel.resetOriginalValues();

		return commercePriceListUserSegmentEntryRel;
	}

	protected CommercePriceListUserSegmentEntryRel toUnwrappedModel(
		CommercePriceListUserSegmentEntryRel commercePriceListUserSegmentEntryRel) {
		if (commercePriceListUserSegmentEntryRel instanceof CommercePriceListUserSegmentEntryRelImpl) {
			return commercePriceListUserSegmentEntryRel;
		}

		CommercePriceListUserSegmentEntryRelImpl commercePriceListUserSegmentEntryRelImpl =
			new CommercePriceListUserSegmentEntryRelImpl();

		commercePriceListUserSegmentEntryRelImpl.setNew(commercePriceListUserSegmentEntryRel.isNew());
		commercePriceListUserSegmentEntryRelImpl.setPrimaryKey(commercePriceListUserSegmentEntryRel.getPrimaryKey());

		commercePriceListUserSegmentEntryRelImpl.setUuid(commercePriceListUserSegmentEntryRel.getUuid());
		commercePriceListUserSegmentEntryRelImpl.setCommercePriceListUserSegmentEntryRelId(commercePriceListUserSegmentEntryRel.getCommercePriceListUserSegmentEntryRelId());
		commercePriceListUserSegmentEntryRelImpl.setGroupId(commercePriceListUserSegmentEntryRel.getGroupId());
		commercePriceListUserSegmentEntryRelImpl.setCompanyId(commercePriceListUserSegmentEntryRel.getCompanyId());
		commercePriceListUserSegmentEntryRelImpl.setUserId(commercePriceListUserSegmentEntryRel.getUserId());
		commercePriceListUserSegmentEntryRelImpl.setUserName(commercePriceListUserSegmentEntryRel.getUserName());
		commercePriceListUserSegmentEntryRelImpl.setCreateDate(commercePriceListUserSegmentEntryRel.getCreateDate());
		commercePriceListUserSegmentEntryRelImpl.setModifiedDate(commercePriceListUserSegmentEntryRel.getModifiedDate());
		commercePriceListUserSegmentEntryRelImpl.setCommercePriceListId(commercePriceListUserSegmentEntryRel.getCommercePriceListId());
		commercePriceListUserSegmentEntryRelImpl.setCommerceUserSegmentEntryId(commercePriceListUserSegmentEntryRel.getCommerceUserSegmentEntryId());
		commercePriceListUserSegmentEntryRelImpl.setOrder(commercePriceListUserSegmentEntryRel.getOrder());
		commercePriceListUserSegmentEntryRelImpl.setLastPublishDate(commercePriceListUserSegmentEntryRel.getLastPublishDate());

		return commercePriceListUserSegmentEntryRelImpl;
	}

	/**
	 * Returns the commerce price list user segment entry rel with the primary key or throws a {@link com.liferay.portal.kernel.exception.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the commerce price list user segment entry rel
	 * @return the commerce price list user segment entry rel
	 * @throws NoSuchPriceListUserSegmentEntryRelException if a commerce price list user segment entry rel with the primary key could not be found
	 */
	@Override
	public CommercePriceListUserSegmentEntryRel findByPrimaryKey(
		Serializable primaryKey)
		throws NoSuchPriceListUserSegmentEntryRelException {
		CommercePriceListUserSegmentEntryRel commercePriceListUserSegmentEntryRel =
			fetchByPrimaryKey(primaryKey);

		if (commercePriceListUserSegmentEntryRel == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchPriceListUserSegmentEntryRelException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				primaryKey);
		}

		return commercePriceListUserSegmentEntryRel;
	}

	/**
	 * Returns the commerce price list user segment entry rel with the primary key or throws a {@link NoSuchPriceListUserSegmentEntryRelException} if it could not be found.
	 *
	 * @param commercePriceListUserSegmentEntryRelId the primary key of the commerce price list user segment entry rel
	 * @return the commerce price list user segment entry rel
	 * @throws NoSuchPriceListUserSegmentEntryRelException if a commerce price list user segment entry rel with the primary key could not be found
	 */
	@Override
	public CommercePriceListUserSegmentEntryRel findByPrimaryKey(
		long commercePriceListUserSegmentEntryRelId)
		throws NoSuchPriceListUserSegmentEntryRelException {
		return findByPrimaryKey((Serializable)commercePriceListUserSegmentEntryRelId);
	}

	/**
	 * Returns the commerce price list user segment entry rel with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the commerce price list user segment entry rel
	 * @return the commerce price list user segment entry rel, or <code>null</code> if a commerce price list user segment entry rel with the primary key could not be found
	 */
	@Override
	public CommercePriceListUserSegmentEntryRel fetchByPrimaryKey(
		Serializable primaryKey) {
		Serializable serializable = entityCache.getResult(CommercePriceListUserSegmentEntryRelModelImpl.ENTITY_CACHE_ENABLED,
				CommercePriceListUserSegmentEntryRelImpl.class, primaryKey);

		if (serializable == nullModel) {
			return null;
		}

		CommercePriceListUserSegmentEntryRel commercePriceListUserSegmentEntryRel =
			(CommercePriceListUserSegmentEntryRel)serializable;

		if (commercePriceListUserSegmentEntryRel == null) {
			Session session = null;

			try {
				session = openSession();

				commercePriceListUserSegmentEntryRel = (CommercePriceListUserSegmentEntryRel)session.get(CommercePriceListUserSegmentEntryRelImpl.class,
						primaryKey);

				if (commercePriceListUserSegmentEntryRel != null) {
					cacheResult(commercePriceListUserSegmentEntryRel);
				}
				else {
					entityCache.putResult(CommercePriceListUserSegmentEntryRelModelImpl.ENTITY_CACHE_ENABLED,
						CommercePriceListUserSegmentEntryRelImpl.class,
						primaryKey, nullModel);
				}
			}
			catch (Exception e) {
				entityCache.removeResult(CommercePriceListUserSegmentEntryRelModelImpl.ENTITY_CACHE_ENABLED,
					CommercePriceListUserSegmentEntryRelImpl.class, primaryKey);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return commercePriceListUserSegmentEntryRel;
	}

	/**
	 * Returns the commerce price list user segment entry rel with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param commercePriceListUserSegmentEntryRelId the primary key of the commerce price list user segment entry rel
	 * @return the commerce price list user segment entry rel, or <code>null</code> if a commerce price list user segment entry rel with the primary key could not be found
	 */
	@Override
	public CommercePriceListUserSegmentEntryRel fetchByPrimaryKey(
		long commercePriceListUserSegmentEntryRelId) {
		return fetchByPrimaryKey((Serializable)commercePriceListUserSegmentEntryRelId);
	}

	@Override
	public Map<Serializable, CommercePriceListUserSegmentEntryRel> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {
		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, CommercePriceListUserSegmentEntryRel> map = new HashMap<Serializable, CommercePriceListUserSegmentEntryRel>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			CommercePriceListUserSegmentEntryRel commercePriceListUserSegmentEntryRel =
				fetchByPrimaryKey(primaryKey);

			if (commercePriceListUserSegmentEntryRel != null) {
				map.put(primaryKey, commercePriceListUserSegmentEntryRel);
			}

			return map;
		}

		Set<Serializable> uncachedPrimaryKeys = null;

		for (Serializable primaryKey : primaryKeys) {
			Serializable serializable = entityCache.getResult(CommercePriceListUserSegmentEntryRelModelImpl.ENTITY_CACHE_ENABLED,
					CommercePriceListUserSegmentEntryRelImpl.class, primaryKey);

			if (serializable != nullModel) {
				if (serializable == null) {
					if (uncachedPrimaryKeys == null) {
						uncachedPrimaryKeys = new HashSet<Serializable>();
					}

					uncachedPrimaryKeys.add(primaryKey);
				}
				else {
					map.put(primaryKey,
						(CommercePriceListUserSegmentEntryRel)serializable);
				}
			}
		}

		if (uncachedPrimaryKeys == null) {
			return map;
		}

		StringBundler query = new StringBundler((uncachedPrimaryKeys.size() * 2) +
				1);

		query.append(_SQL_SELECT_COMMERCEPRICELISTUSERSEGMENTENTRYREL_WHERE_PKS_IN);

		for (Serializable primaryKey : uncachedPrimaryKeys) {
			query.append((long)primaryKey);

			query.append(",");
		}

		query.setIndex(query.index() - 1);

		query.append(")");

		String sql = query.toString();

		Session session = null;

		try {
			session = openSession();

			Query q = session.createQuery(sql);

			for (CommercePriceListUserSegmentEntryRel commercePriceListUserSegmentEntryRel : (List<CommercePriceListUserSegmentEntryRel>)q.list()) {
				map.put(commercePriceListUserSegmentEntryRel.getPrimaryKeyObj(),
					commercePriceListUserSegmentEntryRel);

				cacheResult(commercePriceListUserSegmentEntryRel);

				uncachedPrimaryKeys.remove(commercePriceListUserSegmentEntryRel.getPrimaryKeyObj());
			}

			for (Serializable primaryKey : uncachedPrimaryKeys) {
				entityCache.putResult(CommercePriceListUserSegmentEntryRelModelImpl.ENTITY_CACHE_ENABLED,
					CommercePriceListUserSegmentEntryRelImpl.class, primaryKey,
					nullModel);
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
	 * Returns all the commerce price list user segment entry rels.
	 *
	 * @return the commerce price list user segment entry rels
	 */
	@Override
	public List<CommercePriceListUserSegmentEntryRel> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce price list user segment entry rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommercePriceListUserSegmentEntryRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce price list user segment entry rels
	 * @param end the upper bound of the range of commerce price list user segment entry rels (not inclusive)
	 * @return the range of commerce price list user segment entry rels
	 */
	@Override
	public List<CommercePriceListUserSegmentEntryRel> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce price list user segment entry rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommercePriceListUserSegmentEntryRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce price list user segment entry rels
	 * @param end the upper bound of the range of commerce price list user segment entry rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of commerce price list user segment entry rels
	 */
	@Override
	public List<CommercePriceListUserSegmentEntryRel> findAll(int start,
		int end,
		OrderByComparator<CommercePriceListUserSegmentEntryRel> orderByComparator) {
		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce price list user segment entry rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommercePriceListUserSegmentEntryRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce price list user segment entry rels
	 * @param end the upper bound of the range of commerce price list user segment entry rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of commerce price list user segment entry rels
	 */
	@Override
	public List<CommercePriceListUserSegmentEntryRel> findAll(int start,
		int end,
		OrderByComparator<CommercePriceListUserSegmentEntryRel> orderByComparator,
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

		List<CommercePriceListUserSegmentEntryRel> list = null;

		if (retrieveFromCache) {
			list = (List<CommercePriceListUserSegmentEntryRel>)finderCache.getResult(finderPath,
					finderArgs, this);
		}

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 2));

				query.append(_SQL_SELECT_COMMERCEPRICELISTUSERSEGMENTENTRYREL);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_COMMERCEPRICELISTUSERSEGMENTENTRYREL;

				if (pagination) {
					sql = sql.concat(CommercePriceListUserSegmentEntryRelModelImpl.ORDER_BY_JPQL);
				}
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (!pagination) {
					list = (List<CommercePriceListUserSegmentEntryRel>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<CommercePriceListUserSegmentEntryRel>)QueryUtil.list(q,
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
	 * Removes all the commerce price list user segment entry rels from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (CommercePriceListUserSegmentEntryRel commercePriceListUserSegmentEntryRel : findAll()) {
			remove(commercePriceListUserSegmentEntryRel);
		}
	}

	/**
	 * Returns the number of commerce price list user segment entry rels.
	 *
	 * @return the number of commerce price list user segment entry rels
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_COMMERCEPRICELISTUSERSEGMENTENTRYREL);

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
		return CommercePriceListUserSegmentEntryRelModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the commerce price list user segment entry rel persistence.
	 */
	public void afterPropertiesSet() {
	}

	public void destroy() {
		entityCache.removeCache(CommercePriceListUserSegmentEntryRelImpl.class.getName());
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
	private static final String _SQL_SELECT_COMMERCEPRICELISTUSERSEGMENTENTRYREL =
		"SELECT commercePriceListUserSegmentEntryRel FROM CommercePriceListUserSegmentEntryRel commercePriceListUserSegmentEntryRel";
	private static final String _SQL_SELECT_COMMERCEPRICELISTUSERSEGMENTENTRYREL_WHERE_PKS_IN =
		"SELECT commercePriceListUserSegmentEntryRel FROM CommercePriceListUserSegmentEntryRel commercePriceListUserSegmentEntryRel WHERE CPLUserSegmentEntryRelId IN (";
	private static final String _SQL_SELECT_COMMERCEPRICELISTUSERSEGMENTENTRYREL_WHERE =
		"SELECT commercePriceListUserSegmentEntryRel FROM CommercePriceListUserSegmentEntryRel commercePriceListUserSegmentEntryRel WHERE ";
	private static final String _SQL_COUNT_COMMERCEPRICELISTUSERSEGMENTENTRYREL = "SELECT COUNT(commercePriceListUserSegmentEntryRel) FROM CommercePriceListUserSegmentEntryRel commercePriceListUserSegmentEntryRel";
	private static final String _SQL_COUNT_COMMERCEPRICELISTUSERSEGMENTENTRYREL_WHERE =
		"SELECT COUNT(commercePriceListUserSegmentEntryRel) FROM CommercePriceListUserSegmentEntryRel commercePriceListUserSegmentEntryRel WHERE ";
	private static final String _ORDER_BY_ENTITY_ALIAS = "commercePriceListUserSegmentEntryRel.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No CommercePriceListUserSegmentEntryRel exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No CommercePriceListUserSegmentEntryRel exists with the key {";
	private static final Log _log = LogFactoryUtil.getLog(CommercePriceListUserSegmentEntryRelPersistenceImpl.class);
	private static final Set<String> _badColumnNames = SetUtil.fromArray(new String[] {
				"uuid", "commercePriceListUserSegmentEntryRelId", "order"
			});
}