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

import com.liferay.commerce.exception.NoSuchPriceListQualificationTypeRelException;
import com.liferay.commerce.model.CommercePriceListQualificationTypeRel;
import com.liferay.commerce.model.impl.CommercePriceListQualificationTypeRelImpl;
import com.liferay.commerce.model.impl.CommercePriceListQualificationTypeRelModelImpl;
import com.liferay.commerce.service.persistence.CommercePriceListQualificationTypeRelPersistence;

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
 * The persistence implementation for the commerce price list qualification type rel service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Alessio Antonio Rendina
 * @see CommercePriceListQualificationTypeRelPersistence
 * @see com.liferay.commerce.service.persistence.CommercePriceListQualificationTypeRelUtil
 * @generated
 */
@ProviderType
public class CommercePriceListQualificationTypeRelPersistenceImpl
	extends BasePersistenceImpl<CommercePriceListQualificationTypeRel>
	implements CommercePriceListQualificationTypeRelPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link CommercePriceListQualificationTypeRelUtil} to access the commerce price list qualification type rel persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = CommercePriceListQualificationTypeRelImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(CommercePriceListQualificationTypeRelModelImpl.ENTITY_CACHE_ENABLED,
			CommercePriceListQualificationTypeRelModelImpl.FINDER_CACHE_ENABLED,
			CommercePriceListQualificationTypeRelImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(CommercePriceListQualificationTypeRelModelImpl.ENTITY_CACHE_ENABLED,
			CommercePriceListQualificationTypeRelModelImpl.FINDER_CACHE_ENABLED,
			CommercePriceListQualificationTypeRelImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(CommercePriceListQualificationTypeRelModelImpl.ENTITY_CACHE_ENABLED,
			CommercePriceListQualificationTypeRelModelImpl.FINDER_CACHE_ENABLED,
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll",
			new String[0]);
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_UUID = new FinderPath(CommercePriceListQualificationTypeRelModelImpl.ENTITY_CACHE_ENABLED,
			CommercePriceListQualificationTypeRelModelImpl.FINDER_CACHE_ENABLED,
			CommercePriceListQualificationTypeRelImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUuid",
			new String[] {
				String.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID = new FinderPath(CommercePriceListQualificationTypeRelModelImpl.ENTITY_CACHE_ENABLED,
			CommercePriceListQualificationTypeRelModelImpl.FINDER_CACHE_ENABLED,
			CommercePriceListQualificationTypeRelImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid",
			new String[] { String.class.getName() },
			CommercePriceListQualificationTypeRelModelImpl.UUID_COLUMN_BITMASK |
			CommercePriceListQualificationTypeRelModelImpl.ORDER_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID = new FinderPath(CommercePriceListQualificationTypeRelModelImpl.ENTITY_CACHE_ENABLED,
			CommercePriceListQualificationTypeRelModelImpl.FINDER_CACHE_ENABLED,
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByUuid", new String[] { String.class.getName() });

	/**
	 * Returns all the commerce price list qualification type rels where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching commerce price list qualification type rels
	 */
	@Override
	public List<CommercePriceListQualificationTypeRel> findByUuid(String uuid) {
		return findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce price list qualification type rels where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommercePriceListQualificationTypeRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of commerce price list qualification type rels
	 * @param end the upper bound of the range of commerce price list qualification type rels (not inclusive)
	 * @return the range of matching commerce price list qualification type rels
	 */
	@Override
	public List<CommercePriceListQualificationTypeRel> findByUuid(String uuid,
		int start, int end) {
		return findByUuid(uuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce price list qualification type rels where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommercePriceListQualificationTypeRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of commerce price list qualification type rels
	 * @param end the upper bound of the range of commerce price list qualification type rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce price list qualification type rels
	 */
	@Override
	public List<CommercePriceListQualificationTypeRel> findByUuid(String uuid,
		int start, int end,
		OrderByComparator<CommercePriceListQualificationTypeRel> orderByComparator) {
		return findByUuid(uuid, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce price list qualification type rels where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommercePriceListQualificationTypeRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of commerce price list qualification type rels
	 * @param end the upper bound of the range of commerce price list qualification type rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching commerce price list qualification type rels
	 */
	@Override
	public List<CommercePriceListQualificationTypeRel> findByUuid(String uuid,
		int start, int end,
		OrderByComparator<CommercePriceListQualificationTypeRel> orderByComparator,
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

		List<CommercePriceListQualificationTypeRel> list = null;

		if (retrieveFromCache) {
			list = (List<CommercePriceListQualificationTypeRel>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (CommercePriceListQualificationTypeRel commercePriceListQualificationTypeRel : list) {
					if (!Objects.equals(uuid,
								commercePriceListQualificationTypeRel.getUuid())) {
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

			query.append(_SQL_SELECT_COMMERCEPRICELISTQUALIFICATIONTYPEREL_WHERE);

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
				query.append(CommercePriceListQualificationTypeRelModelImpl.ORDER_BY_JPQL);
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
					list = (List<CommercePriceListQualificationTypeRel>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<CommercePriceListQualificationTypeRel>)QueryUtil.list(q,
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
	 * Returns the first commerce price list qualification type rel in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce price list qualification type rel
	 * @throws NoSuchPriceListQualificationTypeRelException if a matching commerce price list qualification type rel could not be found
	 */
	@Override
	public CommercePriceListQualificationTypeRel findByUuid_First(String uuid,
		OrderByComparator<CommercePriceListQualificationTypeRel> orderByComparator)
		throws NoSuchPriceListQualificationTypeRelException {
		CommercePriceListQualificationTypeRel commercePriceListQualificationTypeRel =
			fetchByUuid_First(uuid, orderByComparator);

		if (commercePriceListQualificationTypeRel != null) {
			return commercePriceListQualificationTypeRel;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append("}");

		throw new NoSuchPriceListQualificationTypeRelException(msg.toString());
	}

	/**
	 * Returns the first commerce price list qualification type rel in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce price list qualification type rel, or <code>null</code> if a matching commerce price list qualification type rel could not be found
	 */
	@Override
	public CommercePriceListQualificationTypeRel fetchByUuid_First(
		String uuid,
		OrderByComparator<CommercePriceListQualificationTypeRel> orderByComparator) {
		List<CommercePriceListQualificationTypeRel> list = findByUuid(uuid, 0,
				1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last commerce price list qualification type rel in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce price list qualification type rel
	 * @throws NoSuchPriceListQualificationTypeRelException if a matching commerce price list qualification type rel could not be found
	 */
	@Override
	public CommercePriceListQualificationTypeRel findByUuid_Last(String uuid,
		OrderByComparator<CommercePriceListQualificationTypeRel> orderByComparator)
		throws NoSuchPriceListQualificationTypeRelException {
		CommercePriceListQualificationTypeRel commercePriceListQualificationTypeRel =
			fetchByUuid_Last(uuid, orderByComparator);

		if (commercePriceListQualificationTypeRel != null) {
			return commercePriceListQualificationTypeRel;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append("}");

		throw new NoSuchPriceListQualificationTypeRelException(msg.toString());
	}

	/**
	 * Returns the last commerce price list qualification type rel in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce price list qualification type rel, or <code>null</code> if a matching commerce price list qualification type rel could not be found
	 */
	@Override
	public CommercePriceListQualificationTypeRel fetchByUuid_Last(String uuid,
		OrderByComparator<CommercePriceListQualificationTypeRel> orderByComparator) {
		int count = countByUuid(uuid);

		if (count == 0) {
			return null;
		}

		List<CommercePriceListQualificationTypeRel> list = findByUuid(uuid,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the commerce price list qualification type rels before and after the current commerce price list qualification type rel in the ordered set where uuid = &#63;.
	 *
	 * @param commercePriceListQualificationTypeRelId the primary key of the current commerce price list qualification type rel
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce price list qualification type rel
	 * @throws NoSuchPriceListQualificationTypeRelException if a commerce price list qualification type rel with the primary key could not be found
	 */
	@Override
	public CommercePriceListQualificationTypeRel[] findByUuid_PrevAndNext(
		long commercePriceListQualificationTypeRelId, String uuid,
		OrderByComparator<CommercePriceListQualificationTypeRel> orderByComparator)
		throws NoSuchPriceListQualificationTypeRelException {
		CommercePriceListQualificationTypeRel commercePriceListQualificationTypeRel =
			findByPrimaryKey(commercePriceListQualificationTypeRelId);

		Session session = null;

		try {
			session = openSession();

			CommercePriceListQualificationTypeRel[] array = new CommercePriceListQualificationTypeRelImpl[3];

			array[0] = getByUuid_PrevAndNext(session,
					commercePriceListQualificationTypeRel, uuid,
					orderByComparator, true);

			array[1] = commercePriceListQualificationTypeRel;

			array[2] = getByUuid_PrevAndNext(session,
					commercePriceListQualificationTypeRel, uuid,
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

	protected CommercePriceListQualificationTypeRel getByUuid_PrevAndNext(
		Session session,
		CommercePriceListQualificationTypeRel commercePriceListQualificationTypeRel,
		String uuid,
		OrderByComparator<CommercePriceListQualificationTypeRel> orderByComparator,
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

		query.append(_SQL_SELECT_COMMERCEPRICELISTQUALIFICATIONTYPEREL_WHERE);

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
			query.append(CommercePriceListQualificationTypeRelModelImpl.ORDER_BY_JPQL);
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
			Object[] values = orderByComparator.getOrderByConditionValues(commercePriceListQualificationTypeRel);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<CommercePriceListQualificationTypeRel> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the commerce price list qualification type rels where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	@Override
	public void removeByUuid(String uuid) {
		for (CommercePriceListQualificationTypeRel commercePriceListQualificationTypeRel : findByUuid(
				uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(commercePriceListQualificationTypeRel);
		}
	}

	/**
	 * Returns the number of commerce price list qualification type rels where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching commerce price list qualification type rels
	 */
	@Override
	public int countByUuid(String uuid) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_UUID;

		Object[] finderArgs = new Object[] { uuid };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_COMMERCEPRICELISTQUALIFICATIONTYPEREL_WHERE);

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

	private static final String _FINDER_COLUMN_UUID_UUID_1 = "commercePriceListQualificationTypeRel.uuid IS NULL";
	private static final String _FINDER_COLUMN_UUID_UUID_2 = "commercePriceListQualificationTypeRel.uuid = ?";
	private static final String _FINDER_COLUMN_UUID_UUID_3 = "(commercePriceListQualificationTypeRel.uuid IS NULL OR commercePriceListQualificationTypeRel.uuid = '')";
	public static final FinderPath FINDER_PATH_FETCH_BY_UUID_G = new FinderPath(CommercePriceListQualificationTypeRelModelImpl.ENTITY_CACHE_ENABLED,
			CommercePriceListQualificationTypeRelModelImpl.FINDER_CACHE_ENABLED,
			CommercePriceListQualificationTypeRelImpl.class,
			FINDER_CLASS_NAME_ENTITY, "fetchByUUID_G",
			new String[] { String.class.getName(), Long.class.getName() },
			CommercePriceListQualificationTypeRelModelImpl.UUID_COLUMN_BITMASK |
			CommercePriceListQualificationTypeRelModelImpl.GROUPID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID_G = new FinderPath(CommercePriceListQualificationTypeRelModelImpl.ENTITY_CACHE_ENABLED,
			CommercePriceListQualificationTypeRelModelImpl.FINDER_CACHE_ENABLED,
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByUUID_G",
			new String[] { String.class.getName(), Long.class.getName() });

	/**
	 * Returns the commerce price list qualification type rel where uuid = &#63; and groupId = &#63; or throws a {@link NoSuchPriceListQualificationTypeRelException} if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching commerce price list qualification type rel
	 * @throws NoSuchPriceListQualificationTypeRelException if a matching commerce price list qualification type rel could not be found
	 */
	@Override
	public CommercePriceListQualificationTypeRel findByUUID_G(String uuid,
		long groupId) throws NoSuchPriceListQualificationTypeRelException {
		CommercePriceListQualificationTypeRel commercePriceListQualificationTypeRel =
			fetchByUUID_G(uuid, groupId);

		if (commercePriceListQualificationTypeRel == null) {
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

			throw new NoSuchPriceListQualificationTypeRelException(msg.toString());
		}

		return commercePriceListQualificationTypeRel;
	}

	/**
	 * Returns the commerce price list qualification type rel where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching commerce price list qualification type rel, or <code>null</code> if a matching commerce price list qualification type rel could not be found
	 */
	@Override
	public CommercePriceListQualificationTypeRel fetchByUUID_G(String uuid,
		long groupId) {
		return fetchByUUID_G(uuid, groupId, true);
	}

	/**
	 * Returns the commerce price list qualification type rel where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the matching commerce price list qualification type rel, or <code>null</code> if a matching commerce price list qualification type rel could not be found
	 */
	@Override
	public CommercePriceListQualificationTypeRel fetchByUUID_G(String uuid,
		long groupId, boolean retrieveFromCache) {
		Object[] finderArgs = new Object[] { uuid, groupId };

		Object result = null;

		if (retrieveFromCache) {
			result = finderCache.getResult(FINDER_PATH_FETCH_BY_UUID_G,
					finderArgs, this);
		}

		if (result instanceof CommercePriceListQualificationTypeRel) {
			CommercePriceListQualificationTypeRel commercePriceListQualificationTypeRel =
				(CommercePriceListQualificationTypeRel)result;

			if (!Objects.equals(uuid,
						commercePriceListQualificationTypeRel.getUuid()) ||
					(groupId != commercePriceListQualificationTypeRel.getGroupId())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_SELECT_COMMERCEPRICELISTQUALIFICATIONTYPEREL_WHERE);

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

				List<CommercePriceListQualificationTypeRel> list = q.list();

				if (list.isEmpty()) {
					finderCache.putResult(FINDER_PATH_FETCH_BY_UUID_G,
						finderArgs, list);
				}
				else {
					CommercePriceListQualificationTypeRel commercePriceListQualificationTypeRel =
						list.get(0);

					result = commercePriceListQualificationTypeRel;

					cacheResult(commercePriceListQualificationTypeRel);

					if ((commercePriceListQualificationTypeRel.getUuid() == null) ||
							!commercePriceListQualificationTypeRel.getUuid()
																	  .equals(uuid) ||
							(commercePriceListQualificationTypeRel.getGroupId() != groupId)) {
						finderCache.putResult(FINDER_PATH_FETCH_BY_UUID_G,
							finderArgs, commercePriceListQualificationTypeRel);
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
			return (CommercePriceListQualificationTypeRel)result;
		}
	}

	/**
	 * Removes the commerce price list qualification type rel where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the commerce price list qualification type rel that was removed
	 */
	@Override
	public CommercePriceListQualificationTypeRel removeByUUID_G(String uuid,
		long groupId) throws NoSuchPriceListQualificationTypeRelException {
		CommercePriceListQualificationTypeRel commercePriceListQualificationTypeRel =
			findByUUID_G(uuid, groupId);

		return remove(commercePriceListQualificationTypeRel);
	}

	/**
	 * Returns the number of commerce price list qualification type rels where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching commerce price list qualification type rels
	 */
	@Override
	public int countByUUID_G(String uuid, long groupId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_UUID_G;

		Object[] finderArgs = new Object[] { uuid, groupId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_COMMERCEPRICELISTQUALIFICATIONTYPEREL_WHERE);

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

	private static final String _FINDER_COLUMN_UUID_G_UUID_1 = "commercePriceListQualificationTypeRel.uuid IS NULL AND ";
	private static final String _FINDER_COLUMN_UUID_G_UUID_2 = "commercePriceListQualificationTypeRel.uuid = ? AND ";
	private static final String _FINDER_COLUMN_UUID_G_UUID_3 = "(commercePriceListQualificationTypeRel.uuid IS NULL OR commercePriceListQualificationTypeRel.uuid = '') AND ";
	private static final String _FINDER_COLUMN_UUID_G_GROUPID_2 = "commercePriceListQualificationTypeRel.groupId = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_UUID_C = new FinderPath(CommercePriceListQualificationTypeRelModelImpl.ENTITY_CACHE_ENABLED,
			CommercePriceListQualificationTypeRelModelImpl.FINDER_CACHE_ENABLED,
			CommercePriceListQualificationTypeRelImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUuid_C",
			new String[] {
				String.class.getName(), Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C =
		new FinderPath(CommercePriceListQualificationTypeRelModelImpl.ENTITY_CACHE_ENABLED,
			CommercePriceListQualificationTypeRelModelImpl.FINDER_CACHE_ENABLED,
			CommercePriceListQualificationTypeRelImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid_C",
			new String[] { String.class.getName(), Long.class.getName() },
			CommercePriceListQualificationTypeRelModelImpl.UUID_COLUMN_BITMASK |
			CommercePriceListQualificationTypeRelModelImpl.COMPANYID_COLUMN_BITMASK |
			CommercePriceListQualificationTypeRelModelImpl.ORDER_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID_C = new FinderPath(CommercePriceListQualificationTypeRelModelImpl.ENTITY_CACHE_ENABLED,
			CommercePriceListQualificationTypeRelModelImpl.FINDER_CACHE_ENABLED,
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByUuid_C",
			new String[] { String.class.getName(), Long.class.getName() });

	/**
	 * Returns all the commerce price list qualification type rels where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching commerce price list qualification type rels
	 */
	@Override
	public List<CommercePriceListQualificationTypeRel> findByUuid_C(
		String uuid, long companyId) {
		return findByUuid_C(uuid, companyId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce price list qualification type rels where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommercePriceListQualificationTypeRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce price list qualification type rels
	 * @param end the upper bound of the range of commerce price list qualification type rels (not inclusive)
	 * @return the range of matching commerce price list qualification type rels
	 */
	@Override
	public List<CommercePriceListQualificationTypeRel> findByUuid_C(
		String uuid, long companyId, int start, int end) {
		return findByUuid_C(uuid, companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce price list qualification type rels where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommercePriceListQualificationTypeRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce price list qualification type rels
	 * @param end the upper bound of the range of commerce price list qualification type rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce price list qualification type rels
	 */
	@Override
	public List<CommercePriceListQualificationTypeRel> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<CommercePriceListQualificationTypeRel> orderByComparator) {
		return findByUuid_C(uuid, companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce price list qualification type rels where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommercePriceListQualificationTypeRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce price list qualification type rels
	 * @param end the upper bound of the range of commerce price list qualification type rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching commerce price list qualification type rels
	 */
	@Override
	public List<CommercePriceListQualificationTypeRel> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<CommercePriceListQualificationTypeRel> orderByComparator,
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

		List<CommercePriceListQualificationTypeRel> list = null;

		if (retrieveFromCache) {
			list = (List<CommercePriceListQualificationTypeRel>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (CommercePriceListQualificationTypeRel commercePriceListQualificationTypeRel : list) {
					if (!Objects.equals(uuid,
								commercePriceListQualificationTypeRel.getUuid()) ||
							(companyId != commercePriceListQualificationTypeRel.getCompanyId())) {
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

			query.append(_SQL_SELECT_COMMERCEPRICELISTQUALIFICATIONTYPEREL_WHERE);

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
				query.append(CommercePriceListQualificationTypeRelModelImpl.ORDER_BY_JPQL);
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
					list = (List<CommercePriceListQualificationTypeRel>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<CommercePriceListQualificationTypeRel>)QueryUtil.list(q,
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
	 * Returns the first commerce price list qualification type rel in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce price list qualification type rel
	 * @throws NoSuchPriceListQualificationTypeRelException if a matching commerce price list qualification type rel could not be found
	 */
	@Override
	public CommercePriceListQualificationTypeRel findByUuid_C_First(
		String uuid, long companyId,
		OrderByComparator<CommercePriceListQualificationTypeRel> orderByComparator)
		throws NoSuchPriceListQualificationTypeRelException {
		CommercePriceListQualificationTypeRel commercePriceListQualificationTypeRel =
			fetchByUuid_C_First(uuid, companyId, orderByComparator);

		if (commercePriceListQualificationTypeRel != null) {
			return commercePriceListQualificationTypeRel;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(", companyId=");
		msg.append(companyId);

		msg.append("}");

		throw new NoSuchPriceListQualificationTypeRelException(msg.toString());
	}

	/**
	 * Returns the first commerce price list qualification type rel in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce price list qualification type rel, or <code>null</code> if a matching commerce price list qualification type rel could not be found
	 */
	@Override
	public CommercePriceListQualificationTypeRel fetchByUuid_C_First(
		String uuid, long companyId,
		OrderByComparator<CommercePriceListQualificationTypeRel> orderByComparator) {
		List<CommercePriceListQualificationTypeRel> list = findByUuid_C(uuid,
				companyId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last commerce price list qualification type rel in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce price list qualification type rel
	 * @throws NoSuchPriceListQualificationTypeRelException if a matching commerce price list qualification type rel could not be found
	 */
	@Override
	public CommercePriceListQualificationTypeRel findByUuid_C_Last(
		String uuid, long companyId,
		OrderByComparator<CommercePriceListQualificationTypeRel> orderByComparator)
		throws NoSuchPriceListQualificationTypeRelException {
		CommercePriceListQualificationTypeRel commercePriceListQualificationTypeRel =
			fetchByUuid_C_Last(uuid, companyId, orderByComparator);

		if (commercePriceListQualificationTypeRel != null) {
			return commercePriceListQualificationTypeRel;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(", companyId=");
		msg.append(companyId);

		msg.append("}");

		throw new NoSuchPriceListQualificationTypeRelException(msg.toString());
	}

	/**
	 * Returns the last commerce price list qualification type rel in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce price list qualification type rel, or <code>null</code> if a matching commerce price list qualification type rel could not be found
	 */
	@Override
	public CommercePriceListQualificationTypeRel fetchByUuid_C_Last(
		String uuid, long companyId,
		OrderByComparator<CommercePriceListQualificationTypeRel> orderByComparator) {
		int count = countByUuid_C(uuid, companyId);

		if (count == 0) {
			return null;
		}

		List<CommercePriceListQualificationTypeRel> list = findByUuid_C(uuid,
				companyId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the commerce price list qualification type rels before and after the current commerce price list qualification type rel in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param commercePriceListQualificationTypeRelId the primary key of the current commerce price list qualification type rel
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce price list qualification type rel
	 * @throws NoSuchPriceListQualificationTypeRelException if a commerce price list qualification type rel with the primary key could not be found
	 */
	@Override
	public CommercePriceListQualificationTypeRel[] findByUuid_C_PrevAndNext(
		long commercePriceListQualificationTypeRelId, String uuid,
		long companyId,
		OrderByComparator<CommercePriceListQualificationTypeRel> orderByComparator)
		throws NoSuchPriceListQualificationTypeRelException {
		CommercePriceListQualificationTypeRel commercePriceListQualificationTypeRel =
			findByPrimaryKey(commercePriceListQualificationTypeRelId);

		Session session = null;

		try {
			session = openSession();

			CommercePriceListQualificationTypeRel[] array = new CommercePriceListQualificationTypeRelImpl[3];

			array[0] = getByUuid_C_PrevAndNext(session,
					commercePriceListQualificationTypeRel, uuid, companyId,
					orderByComparator, true);

			array[1] = commercePriceListQualificationTypeRel;

			array[2] = getByUuid_C_PrevAndNext(session,
					commercePriceListQualificationTypeRel, uuid, companyId,
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

	protected CommercePriceListQualificationTypeRel getByUuid_C_PrevAndNext(
		Session session,
		CommercePriceListQualificationTypeRel commercePriceListQualificationTypeRel,
		String uuid, long companyId,
		OrderByComparator<CommercePriceListQualificationTypeRel> orderByComparator,
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

		query.append(_SQL_SELECT_COMMERCEPRICELISTQUALIFICATIONTYPEREL_WHERE);

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
			query.append(CommercePriceListQualificationTypeRelModelImpl.ORDER_BY_JPQL);
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
			Object[] values = orderByComparator.getOrderByConditionValues(commercePriceListQualificationTypeRel);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<CommercePriceListQualificationTypeRel> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the commerce price list qualification type rels where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	@Override
	public void removeByUuid_C(String uuid, long companyId) {
		for (CommercePriceListQualificationTypeRel commercePriceListQualificationTypeRel : findByUuid_C(
				uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(commercePriceListQualificationTypeRel);
		}
	}

	/**
	 * Returns the number of commerce price list qualification type rels where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching commerce price list qualification type rels
	 */
	@Override
	public int countByUuid_C(String uuid, long companyId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_UUID_C;

		Object[] finderArgs = new Object[] { uuid, companyId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_COMMERCEPRICELISTQUALIFICATIONTYPEREL_WHERE);

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

	private static final String _FINDER_COLUMN_UUID_C_UUID_1 = "commercePriceListQualificationTypeRel.uuid IS NULL AND ";
	private static final String _FINDER_COLUMN_UUID_C_UUID_2 = "commercePriceListQualificationTypeRel.uuid = ? AND ";
	private static final String _FINDER_COLUMN_UUID_C_UUID_3 = "(commercePriceListQualificationTypeRel.uuid IS NULL OR commercePriceListQualificationTypeRel.uuid = '') AND ";
	private static final String _FINDER_COLUMN_UUID_C_COMPANYID_2 = "commercePriceListQualificationTypeRel.companyId = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_COMMERCEPRICELISTID =
		new FinderPath(CommercePriceListQualificationTypeRelModelImpl.ENTITY_CACHE_ENABLED,
			CommercePriceListQualificationTypeRelModelImpl.FINDER_CACHE_ENABLED,
			CommercePriceListQualificationTypeRelImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByCommercePriceListId",
			new String[] {
				Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMMERCEPRICELISTID =
		new FinderPath(CommercePriceListQualificationTypeRelModelImpl.ENTITY_CACHE_ENABLED,
			CommercePriceListQualificationTypeRelModelImpl.FINDER_CACHE_ENABLED,
			CommercePriceListQualificationTypeRelImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByCommercePriceListId", new String[] { Long.class.getName() },
			CommercePriceListQualificationTypeRelModelImpl.COMMERCEPRICELISTID_COLUMN_BITMASK |
			CommercePriceListQualificationTypeRelModelImpl.ORDER_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_COMMERCEPRICELISTID = new FinderPath(CommercePriceListQualificationTypeRelModelImpl.ENTITY_CACHE_ENABLED,
			CommercePriceListQualificationTypeRelModelImpl.FINDER_CACHE_ENABLED,
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByCommercePriceListId", new String[] { Long.class.getName() });

	/**
	 * Returns all the commerce price list qualification type rels where commercePriceListId = &#63;.
	 *
	 * @param commercePriceListId the commerce price list ID
	 * @return the matching commerce price list qualification type rels
	 */
	@Override
	public List<CommercePriceListQualificationTypeRel> findByCommercePriceListId(
		long commercePriceListId) {
		return findByCommercePriceListId(commercePriceListId,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce price list qualification type rels where commercePriceListId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommercePriceListQualificationTypeRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param commercePriceListId the commerce price list ID
	 * @param start the lower bound of the range of commerce price list qualification type rels
	 * @param end the upper bound of the range of commerce price list qualification type rels (not inclusive)
	 * @return the range of matching commerce price list qualification type rels
	 */
	@Override
	public List<CommercePriceListQualificationTypeRel> findByCommercePriceListId(
		long commercePriceListId, int start, int end) {
		return findByCommercePriceListId(commercePriceListId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce price list qualification type rels where commercePriceListId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommercePriceListQualificationTypeRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param commercePriceListId the commerce price list ID
	 * @param start the lower bound of the range of commerce price list qualification type rels
	 * @param end the upper bound of the range of commerce price list qualification type rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce price list qualification type rels
	 */
	@Override
	public List<CommercePriceListQualificationTypeRel> findByCommercePriceListId(
		long commercePriceListId, int start, int end,
		OrderByComparator<CommercePriceListQualificationTypeRel> orderByComparator) {
		return findByCommercePriceListId(commercePriceListId, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce price list qualification type rels where commercePriceListId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommercePriceListQualificationTypeRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param commercePriceListId the commerce price list ID
	 * @param start the lower bound of the range of commerce price list qualification type rels
	 * @param end the upper bound of the range of commerce price list qualification type rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching commerce price list qualification type rels
	 */
	@Override
	public List<CommercePriceListQualificationTypeRel> findByCommercePriceListId(
		long commercePriceListId, int start, int end,
		OrderByComparator<CommercePriceListQualificationTypeRel> orderByComparator,
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

		List<CommercePriceListQualificationTypeRel> list = null;

		if (retrieveFromCache) {
			list = (List<CommercePriceListQualificationTypeRel>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (CommercePriceListQualificationTypeRel commercePriceListQualificationTypeRel : list) {
					if ((commercePriceListId != commercePriceListQualificationTypeRel.getCommercePriceListId())) {
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

			query.append(_SQL_SELECT_COMMERCEPRICELISTQUALIFICATIONTYPEREL_WHERE);

			query.append(_FINDER_COLUMN_COMMERCEPRICELISTID_COMMERCEPRICELISTID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(CommercePriceListQualificationTypeRelModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(commercePriceListId);

				if (!pagination) {
					list = (List<CommercePriceListQualificationTypeRel>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<CommercePriceListQualificationTypeRel>)QueryUtil.list(q,
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
	 * Returns the first commerce price list qualification type rel in the ordered set where commercePriceListId = &#63;.
	 *
	 * @param commercePriceListId the commerce price list ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce price list qualification type rel
	 * @throws NoSuchPriceListQualificationTypeRelException if a matching commerce price list qualification type rel could not be found
	 */
	@Override
	public CommercePriceListQualificationTypeRel findByCommercePriceListId_First(
		long commercePriceListId,
		OrderByComparator<CommercePriceListQualificationTypeRel> orderByComparator)
		throws NoSuchPriceListQualificationTypeRelException {
		CommercePriceListQualificationTypeRel commercePriceListQualificationTypeRel =
			fetchByCommercePriceListId_First(commercePriceListId,
				orderByComparator);

		if (commercePriceListQualificationTypeRel != null) {
			return commercePriceListQualificationTypeRel;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("commercePriceListId=");
		msg.append(commercePriceListId);

		msg.append("}");

		throw new NoSuchPriceListQualificationTypeRelException(msg.toString());
	}

	/**
	 * Returns the first commerce price list qualification type rel in the ordered set where commercePriceListId = &#63;.
	 *
	 * @param commercePriceListId the commerce price list ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce price list qualification type rel, or <code>null</code> if a matching commerce price list qualification type rel could not be found
	 */
	@Override
	public CommercePriceListQualificationTypeRel fetchByCommercePriceListId_First(
		long commercePriceListId,
		OrderByComparator<CommercePriceListQualificationTypeRel> orderByComparator) {
		List<CommercePriceListQualificationTypeRel> list = findByCommercePriceListId(commercePriceListId,
				0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last commerce price list qualification type rel in the ordered set where commercePriceListId = &#63;.
	 *
	 * @param commercePriceListId the commerce price list ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce price list qualification type rel
	 * @throws NoSuchPriceListQualificationTypeRelException if a matching commerce price list qualification type rel could not be found
	 */
	@Override
	public CommercePriceListQualificationTypeRel findByCommercePriceListId_Last(
		long commercePriceListId,
		OrderByComparator<CommercePriceListQualificationTypeRel> orderByComparator)
		throws NoSuchPriceListQualificationTypeRelException {
		CommercePriceListQualificationTypeRel commercePriceListQualificationTypeRel =
			fetchByCommercePriceListId_Last(commercePriceListId,
				orderByComparator);

		if (commercePriceListQualificationTypeRel != null) {
			return commercePriceListQualificationTypeRel;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("commercePriceListId=");
		msg.append(commercePriceListId);

		msg.append("}");

		throw new NoSuchPriceListQualificationTypeRelException(msg.toString());
	}

	/**
	 * Returns the last commerce price list qualification type rel in the ordered set where commercePriceListId = &#63;.
	 *
	 * @param commercePriceListId the commerce price list ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce price list qualification type rel, or <code>null</code> if a matching commerce price list qualification type rel could not be found
	 */
	@Override
	public CommercePriceListQualificationTypeRel fetchByCommercePriceListId_Last(
		long commercePriceListId,
		OrderByComparator<CommercePriceListQualificationTypeRel> orderByComparator) {
		int count = countByCommercePriceListId(commercePriceListId);

		if (count == 0) {
			return null;
		}

		List<CommercePriceListQualificationTypeRel> list = findByCommercePriceListId(commercePriceListId,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the commerce price list qualification type rels before and after the current commerce price list qualification type rel in the ordered set where commercePriceListId = &#63;.
	 *
	 * @param commercePriceListQualificationTypeRelId the primary key of the current commerce price list qualification type rel
	 * @param commercePriceListId the commerce price list ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce price list qualification type rel
	 * @throws NoSuchPriceListQualificationTypeRelException if a commerce price list qualification type rel with the primary key could not be found
	 */
	@Override
	public CommercePriceListQualificationTypeRel[] findByCommercePriceListId_PrevAndNext(
		long commercePriceListQualificationTypeRelId, long commercePriceListId,
		OrderByComparator<CommercePriceListQualificationTypeRel> orderByComparator)
		throws NoSuchPriceListQualificationTypeRelException {
		CommercePriceListQualificationTypeRel commercePriceListQualificationTypeRel =
			findByPrimaryKey(commercePriceListQualificationTypeRelId);

		Session session = null;

		try {
			session = openSession();

			CommercePriceListQualificationTypeRel[] array = new CommercePriceListQualificationTypeRelImpl[3];

			array[0] = getByCommercePriceListId_PrevAndNext(session,
					commercePriceListQualificationTypeRel, commercePriceListId,
					orderByComparator, true);

			array[1] = commercePriceListQualificationTypeRel;

			array[2] = getByCommercePriceListId_PrevAndNext(session,
					commercePriceListQualificationTypeRel, commercePriceListId,
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

	protected CommercePriceListQualificationTypeRel getByCommercePriceListId_PrevAndNext(
		Session session,
		CommercePriceListQualificationTypeRel commercePriceListQualificationTypeRel,
		long commercePriceListId,
		OrderByComparator<CommercePriceListQualificationTypeRel> orderByComparator,
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

		query.append(_SQL_SELECT_COMMERCEPRICELISTQUALIFICATIONTYPEREL_WHERE);

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
			query.append(CommercePriceListQualificationTypeRelModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(commercePriceListId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(commercePriceListQualificationTypeRel);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<CommercePriceListQualificationTypeRel> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the commerce price list qualification type rels where commercePriceListId = &#63; from the database.
	 *
	 * @param commercePriceListId the commerce price list ID
	 */
	@Override
	public void removeByCommercePriceListId(long commercePriceListId) {
		for (CommercePriceListQualificationTypeRel commercePriceListQualificationTypeRel : findByCommercePriceListId(
				commercePriceListId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(commercePriceListQualificationTypeRel);
		}
	}

	/**
	 * Returns the number of commerce price list qualification type rels where commercePriceListId = &#63;.
	 *
	 * @param commercePriceListId the commerce price list ID
	 * @return the number of matching commerce price list qualification type rels
	 */
	@Override
	public int countByCommercePriceListId(long commercePriceListId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_COMMERCEPRICELISTID;

		Object[] finderArgs = new Object[] { commercePriceListId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_COMMERCEPRICELISTQUALIFICATIONTYPEREL_WHERE);

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
		"commercePriceListQualificationTypeRel.commercePriceListId = ?";
	public static final FinderPath FINDER_PATH_FETCH_BY_C_C = new FinderPath(CommercePriceListQualificationTypeRelModelImpl.ENTITY_CACHE_ENABLED,
			CommercePriceListQualificationTypeRelModelImpl.FINDER_CACHE_ENABLED,
			CommercePriceListQualificationTypeRelImpl.class,
			FINDER_CLASS_NAME_ENTITY, "fetchByC_C",
			new String[] { String.class.getName(), Long.class.getName() },
			CommercePriceListQualificationTypeRelModelImpl.COMMERCEPRICELISTQUALIFICATIONTYPE_COLUMN_BITMASK |
			CommercePriceListQualificationTypeRelModelImpl.COMMERCEPRICELISTID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_C_C = new FinderPath(CommercePriceListQualificationTypeRelModelImpl.ENTITY_CACHE_ENABLED,
			CommercePriceListQualificationTypeRelModelImpl.FINDER_CACHE_ENABLED,
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByC_C",
			new String[] { String.class.getName(), Long.class.getName() });

	/**
	 * Returns the commerce price list qualification type rel where commercePriceListQualificationType = &#63; and commercePriceListId = &#63; or throws a {@link NoSuchPriceListQualificationTypeRelException} if it could not be found.
	 *
	 * @param commercePriceListQualificationType the commerce price list qualification type
	 * @param commercePriceListId the commerce price list ID
	 * @return the matching commerce price list qualification type rel
	 * @throws NoSuchPriceListQualificationTypeRelException if a matching commerce price list qualification type rel could not be found
	 */
	@Override
	public CommercePriceListQualificationTypeRel findByC_C(
		String commercePriceListQualificationType, long commercePriceListId)
		throws NoSuchPriceListQualificationTypeRelException {
		CommercePriceListQualificationTypeRel commercePriceListQualificationTypeRel =
			fetchByC_C(commercePriceListQualificationType, commercePriceListId);

		if (commercePriceListQualificationTypeRel == null) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("commercePriceListQualificationType=");
			msg.append(commercePriceListQualificationType);

			msg.append(", commercePriceListId=");
			msg.append(commercePriceListId);

			msg.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(msg.toString());
			}

			throw new NoSuchPriceListQualificationTypeRelException(msg.toString());
		}

		return commercePriceListQualificationTypeRel;
	}

	/**
	 * Returns the commerce price list qualification type rel where commercePriceListQualificationType = &#63; and commercePriceListId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param commercePriceListQualificationType the commerce price list qualification type
	 * @param commercePriceListId the commerce price list ID
	 * @return the matching commerce price list qualification type rel, or <code>null</code> if a matching commerce price list qualification type rel could not be found
	 */
	@Override
	public CommercePriceListQualificationTypeRel fetchByC_C(
		String commercePriceListQualificationType, long commercePriceListId) {
		return fetchByC_C(commercePriceListQualificationType,
			commercePriceListId, true);
	}

	/**
	 * Returns the commerce price list qualification type rel where commercePriceListQualificationType = &#63; and commercePriceListId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param commercePriceListQualificationType the commerce price list qualification type
	 * @param commercePriceListId the commerce price list ID
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the matching commerce price list qualification type rel, or <code>null</code> if a matching commerce price list qualification type rel could not be found
	 */
	@Override
	public CommercePriceListQualificationTypeRel fetchByC_C(
		String commercePriceListQualificationType, long commercePriceListId,
		boolean retrieveFromCache) {
		Object[] finderArgs = new Object[] {
				commercePriceListQualificationType, commercePriceListId
			};

		Object result = null;

		if (retrieveFromCache) {
			result = finderCache.getResult(FINDER_PATH_FETCH_BY_C_C,
					finderArgs, this);
		}

		if (result instanceof CommercePriceListQualificationTypeRel) {
			CommercePriceListQualificationTypeRel commercePriceListQualificationTypeRel =
				(CommercePriceListQualificationTypeRel)result;

			if (!Objects.equals(commercePriceListQualificationType,
						commercePriceListQualificationTypeRel.getCommercePriceListQualificationType()) ||
					(commercePriceListId != commercePriceListQualificationTypeRel.getCommercePriceListId())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_SELECT_COMMERCEPRICELISTQUALIFICATIONTYPEREL_WHERE);

			boolean bindCommercePriceListQualificationType = false;

			if (commercePriceListQualificationType == null) {
				query.append(_FINDER_COLUMN_C_C_COMMERCEPRICELISTQUALIFICATIONTYPE_1);
			}
			else if (commercePriceListQualificationType.equals("")) {
				query.append(_FINDER_COLUMN_C_C_COMMERCEPRICELISTQUALIFICATIONTYPE_3);
			}
			else {
				bindCommercePriceListQualificationType = true;

				query.append(_FINDER_COLUMN_C_C_COMMERCEPRICELISTQUALIFICATIONTYPE_2);
			}

			query.append(_FINDER_COLUMN_C_C_COMMERCEPRICELISTID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindCommercePriceListQualificationType) {
					qPos.add(commercePriceListQualificationType);
				}

				qPos.add(commercePriceListId);

				List<CommercePriceListQualificationTypeRel> list = q.list();

				if (list.isEmpty()) {
					finderCache.putResult(FINDER_PATH_FETCH_BY_C_C, finderArgs,
						list);
				}
				else {
					CommercePriceListQualificationTypeRel commercePriceListQualificationTypeRel =
						list.get(0);

					result = commercePriceListQualificationTypeRel;

					cacheResult(commercePriceListQualificationTypeRel);

					if ((commercePriceListQualificationTypeRel.getCommercePriceListQualificationType() == null) ||
							!commercePriceListQualificationTypeRel.getCommercePriceListQualificationType()
																	  .equals(commercePriceListQualificationType) ||
							(commercePriceListQualificationTypeRel.getCommercePriceListId() != commercePriceListId)) {
						finderCache.putResult(FINDER_PATH_FETCH_BY_C_C,
							finderArgs, commercePriceListQualificationTypeRel);
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
			return (CommercePriceListQualificationTypeRel)result;
		}
	}

	/**
	 * Removes the commerce price list qualification type rel where commercePriceListQualificationType = &#63; and commercePriceListId = &#63; from the database.
	 *
	 * @param commercePriceListQualificationType the commerce price list qualification type
	 * @param commercePriceListId the commerce price list ID
	 * @return the commerce price list qualification type rel that was removed
	 */
	@Override
	public CommercePriceListQualificationTypeRel removeByC_C(
		String commercePriceListQualificationType, long commercePriceListId)
		throws NoSuchPriceListQualificationTypeRelException {
		CommercePriceListQualificationTypeRel commercePriceListQualificationTypeRel =
			findByC_C(commercePriceListQualificationType, commercePriceListId);

		return remove(commercePriceListQualificationTypeRel);
	}

	/**
	 * Returns the number of commerce price list qualification type rels where commercePriceListQualificationType = &#63; and commercePriceListId = &#63;.
	 *
	 * @param commercePriceListQualificationType the commerce price list qualification type
	 * @param commercePriceListId the commerce price list ID
	 * @return the number of matching commerce price list qualification type rels
	 */
	@Override
	public int countByC_C(String commercePriceListQualificationType,
		long commercePriceListId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_C_C;

		Object[] finderArgs = new Object[] {
				commercePriceListQualificationType, commercePriceListId
			};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_COMMERCEPRICELISTQUALIFICATIONTYPEREL_WHERE);

			boolean bindCommercePriceListQualificationType = false;

			if (commercePriceListQualificationType == null) {
				query.append(_FINDER_COLUMN_C_C_COMMERCEPRICELISTQUALIFICATIONTYPE_1);
			}
			else if (commercePriceListQualificationType.equals("")) {
				query.append(_FINDER_COLUMN_C_C_COMMERCEPRICELISTQUALIFICATIONTYPE_3);
			}
			else {
				bindCommercePriceListQualificationType = true;

				query.append(_FINDER_COLUMN_C_C_COMMERCEPRICELISTQUALIFICATIONTYPE_2);
			}

			query.append(_FINDER_COLUMN_C_C_COMMERCEPRICELISTID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindCommercePriceListQualificationType) {
					qPos.add(commercePriceListQualificationType);
				}

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

	private static final String _FINDER_COLUMN_C_C_COMMERCEPRICELISTQUALIFICATIONTYPE_1 =
		"commercePriceListQualificationTypeRel.commercePriceListQualificationType IS NULL AND ";
	private static final String _FINDER_COLUMN_C_C_COMMERCEPRICELISTQUALIFICATIONTYPE_2 =
		"commercePriceListQualificationTypeRel.commercePriceListQualificationType = ? AND ";
	private static final String _FINDER_COLUMN_C_C_COMMERCEPRICELISTQUALIFICATIONTYPE_3 =
		"(commercePriceListQualificationTypeRel.commercePriceListQualificationType IS NULL OR commercePriceListQualificationTypeRel.commercePriceListQualificationType = '') AND ";
	private static final String _FINDER_COLUMN_C_C_COMMERCEPRICELISTID_2 = "commercePriceListQualificationTypeRel.commercePriceListId = ?";

	public CommercePriceListQualificationTypeRelPersistenceImpl() {
		setModelClass(CommercePriceListQualificationTypeRel.class);

		try {
			Field field = BasePersistenceImpl.class.getDeclaredField(
					"_dbColumnNames");

			field.setAccessible(true);

			Map<String, String> dbColumnNames = new HashMap<String, String>();

			dbColumnNames.put("uuid", "uuid_");
			dbColumnNames.put("commercePriceListQualificationTypeRelId",
				"CPLQualificationTypeRelId");
			dbColumnNames.put("commercePriceListQualificationType",
				"CPriceListQualificationType");
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
	 * Caches the commerce price list qualification type rel in the entity cache if it is enabled.
	 *
	 * @param commercePriceListQualificationTypeRel the commerce price list qualification type rel
	 */
	@Override
	public void cacheResult(
		CommercePriceListQualificationTypeRel commercePriceListQualificationTypeRel) {
		entityCache.putResult(CommercePriceListQualificationTypeRelModelImpl.ENTITY_CACHE_ENABLED,
			CommercePriceListQualificationTypeRelImpl.class,
			commercePriceListQualificationTypeRel.getPrimaryKey(),
			commercePriceListQualificationTypeRel);

		finderCache.putResult(FINDER_PATH_FETCH_BY_UUID_G,
			new Object[] {
				commercePriceListQualificationTypeRel.getUuid(),
				commercePriceListQualificationTypeRel.getGroupId()
			}, commercePriceListQualificationTypeRel);

		finderCache.putResult(FINDER_PATH_FETCH_BY_C_C,
			new Object[] {
				commercePriceListQualificationTypeRel.getCommercePriceListQualificationType(),
				commercePriceListQualificationTypeRel.getCommercePriceListId()
			}, commercePriceListQualificationTypeRel);

		commercePriceListQualificationTypeRel.resetOriginalValues();
	}

	/**
	 * Caches the commerce price list qualification type rels in the entity cache if it is enabled.
	 *
	 * @param commercePriceListQualificationTypeRels the commerce price list qualification type rels
	 */
	@Override
	public void cacheResult(
		List<CommercePriceListQualificationTypeRel> commercePriceListQualificationTypeRels) {
		for (CommercePriceListQualificationTypeRel commercePriceListQualificationTypeRel : commercePriceListQualificationTypeRels) {
			if (entityCache.getResult(
						CommercePriceListQualificationTypeRelModelImpl.ENTITY_CACHE_ENABLED,
						CommercePriceListQualificationTypeRelImpl.class,
						commercePriceListQualificationTypeRel.getPrimaryKey()) == null) {
				cacheResult(commercePriceListQualificationTypeRel);
			}
			else {
				commercePriceListQualificationTypeRel.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all commerce price list qualification type rels.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(CommercePriceListQualificationTypeRelImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the commerce price list qualification type rel.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(
		CommercePriceListQualificationTypeRel commercePriceListQualificationTypeRel) {
		entityCache.removeResult(CommercePriceListQualificationTypeRelModelImpl.ENTITY_CACHE_ENABLED,
			CommercePriceListQualificationTypeRelImpl.class,
			commercePriceListQualificationTypeRel.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache((CommercePriceListQualificationTypeRelModelImpl)commercePriceListQualificationTypeRel,
			true);
	}

	@Override
	public void clearCache(
		List<CommercePriceListQualificationTypeRel> commercePriceListQualificationTypeRels) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (CommercePriceListQualificationTypeRel commercePriceListQualificationTypeRel : commercePriceListQualificationTypeRels) {
			entityCache.removeResult(CommercePriceListQualificationTypeRelModelImpl.ENTITY_CACHE_ENABLED,
				CommercePriceListQualificationTypeRelImpl.class,
				commercePriceListQualificationTypeRel.getPrimaryKey());

			clearUniqueFindersCache((CommercePriceListQualificationTypeRelModelImpl)commercePriceListQualificationTypeRel,
				true);
		}
	}

	protected void cacheUniqueFindersCache(
		CommercePriceListQualificationTypeRelModelImpl commercePriceListQualificationTypeRelModelImpl) {
		Object[] args = new Object[] {
				commercePriceListQualificationTypeRelModelImpl.getUuid(),
				commercePriceListQualificationTypeRelModelImpl.getGroupId()
			};

		finderCache.putResult(FINDER_PATH_COUNT_BY_UUID_G, args,
			Long.valueOf(1), false);
		finderCache.putResult(FINDER_PATH_FETCH_BY_UUID_G, args,
			commercePriceListQualificationTypeRelModelImpl, false);

		args = new Object[] {
				commercePriceListQualificationTypeRelModelImpl.getCommercePriceListQualificationType(),
				commercePriceListQualificationTypeRelModelImpl.getCommercePriceListId()
			};

		finderCache.putResult(FINDER_PATH_COUNT_BY_C_C, args, Long.valueOf(1),
			false);
		finderCache.putResult(FINDER_PATH_FETCH_BY_C_C, args,
			commercePriceListQualificationTypeRelModelImpl, false);
	}

	protected void clearUniqueFindersCache(
		CommercePriceListQualificationTypeRelModelImpl commercePriceListQualificationTypeRelModelImpl,
		boolean clearCurrent) {
		if (clearCurrent) {
			Object[] args = new Object[] {
					commercePriceListQualificationTypeRelModelImpl.getUuid(),
					commercePriceListQualificationTypeRelModelImpl.getGroupId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID_G, args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_UUID_G, args);
		}

		if ((commercePriceListQualificationTypeRelModelImpl.getColumnBitmask() &
				FINDER_PATH_FETCH_BY_UUID_G.getColumnBitmask()) != 0) {
			Object[] args = new Object[] {
					commercePriceListQualificationTypeRelModelImpl.getOriginalUuid(),
					commercePriceListQualificationTypeRelModelImpl.getOriginalGroupId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID_G, args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_UUID_G, args);
		}

		if (clearCurrent) {
			Object[] args = new Object[] {
					commercePriceListQualificationTypeRelModelImpl.getCommercePriceListQualificationType(),
					commercePriceListQualificationTypeRelModelImpl.getCommercePriceListId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_C_C, args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_C_C, args);
		}

		if ((commercePriceListQualificationTypeRelModelImpl.getColumnBitmask() &
				FINDER_PATH_FETCH_BY_C_C.getColumnBitmask()) != 0) {
			Object[] args = new Object[] {
					commercePriceListQualificationTypeRelModelImpl.getOriginalCommercePriceListQualificationType(),
					commercePriceListQualificationTypeRelModelImpl.getOriginalCommercePriceListId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_C_C, args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_C_C, args);
		}
	}

	/**
	 * Creates a new commerce price list qualification type rel with the primary key. Does not add the commerce price list qualification type rel to the database.
	 *
	 * @param commercePriceListQualificationTypeRelId the primary key for the new commerce price list qualification type rel
	 * @return the new commerce price list qualification type rel
	 */
	@Override
	public CommercePriceListQualificationTypeRel create(
		long commercePriceListQualificationTypeRelId) {
		CommercePriceListQualificationTypeRel commercePriceListQualificationTypeRel =
			new CommercePriceListQualificationTypeRelImpl();

		commercePriceListQualificationTypeRel.setNew(true);
		commercePriceListQualificationTypeRel.setPrimaryKey(commercePriceListQualificationTypeRelId);

		String uuid = PortalUUIDUtil.generate();

		commercePriceListQualificationTypeRel.setUuid(uuid);

		commercePriceListQualificationTypeRel.setCompanyId(companyProvider.getCompanyId());

		return commercePriceListQualificationTypeRel;
	}

	/**
	 * Removes the commerce price list qualification type rel with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param commercePriceListQualificationTypeRelId the primary key of the commerce price list qualification type rel
	 * @return the commerce price list qualification type rel that was removed
	 * @throws NoSuchPriceListQualificationTypeRelException if a commerce price list qualification type rel with the primary key could not be found
	 */
	@Override
	public CommercePriceListQualificationTypeRel remove(
		long commercePriceListQualificationTypeRelId)
		throws NoSuchPriceListQualificationTypeRelException {
		return remove((Serializable)commercePriceListQualificationTypeRelId);
	}

	/**
	 * Removes the commerce price list qualification type rel with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the commerce price list qualification type rel
	 * @return the commerce price list qualification type rel that was removed
	 * @throws NoSuchPriceListQualificationTypeRelException if a commerce price list qualification type rel with the primary key could not be found
	 */
	@Override
	public CommercePriceListQualificationTypeRel remove(Serializable primaryKey)
		throws NoSuchPriceListQualificationTypeRelException {
		Session session = null;

		try {
			session = openSession();

			CommercePriceListQualificationTypeRel commercePriceListQualificationTypeRel =
				(CommercePriceListQualificationTypeRel)session.get(CommercePriceListQualificationTypeRelImpl.class,
					primaryKey);

			if (commercePriceListQualificationTypeRel == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchPriceListQualificationTypeRelException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(commercePriceListQualificationTypeRel);
		}
		catch (NoSuchPriceListQualificationTypeRelException nsee) {
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
	protected CommercePriceListQualificationTypeRel removeImpl(
		CommercePriceListQualificationTypeRel commercePriceListQualificationTypeRel) {
		commercePriceListQualificationTypeRel = toUnwrappedModel(commercePriceListQualificationTypeRel);

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(commercePriceListQualificationTypeRel)) {
				commercePriceListQualificationTypeRel = (CommercePriceListQualificationTypeRel)session.get(CommercePriceListQualificationTypeRelImpl.class,
						commercePriceListQualificationTypeRel.getPrimaryKeyObj());
			}

			if (commercePriceListQualificationTypeRel != null) {
				session.delete(commercePriceListQualificationTypeRel);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (commercePriceListQualificationTypeRel != null) {
			clearCache(commercePriceListQualificationTypeRel);
		}

		return commercePriceListQualificationTypeRel;
	}

	@Override
	public CommercePriceListQualificationTypeRel updateImpl(
		CommercePriceListQualificationTypeRel commercePriceListQualificationTypeRel) {
		commercePriceListQualificationTypeRel = toUnwrappedModel(commercePriceListQualificationTypeRel);

		boolean isNew = commercePriceListQualificationTypeRel.isNew();

		CommercePriceListQualificationTypeRelModelImpl commercePriceListQualificationTypeRelModelImpl =
			(CommercePriceListQualificationTypeRelModelImpl)commercePriceListQualificationTypeRel;

		if (Validator.isNull(commercePriceListQualificationTypeRel.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			commercePriceListQualificationTypeRel.setUuid(uuid);
		}

		ServiceContext serviceContext = ServiceContextThreadLocal.getServiceContext();

		Date now = new Date();

		if (isNew &&
				(commercePriceListQualificationTypeRel.getCreateDate() == null)) {
			if (serviceContext == null) {
				commercePriceListQualificationTypeRel.setCreateDate(now);
			}
			else {
				commercePriceListQualificationTypeRel.setCreateDate(serviceContext.getCreateDate(
						now));
			}
		}

		if (!commercePriceListQualificationTypeRelModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				commercePriceListQualificationTypeRel.setModifiedDate(now);
			}
			else {
				commercePriceListQualificationTypeRel.setModifiedDate(serviceContext.getModifiedDate(
						now));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (commercePriceListQualificationTypeRel.isNew()) {
				session.save(commercePriceListQualificationTypeRel);

				commercePriceListQualificationTypeRel.setNew(false);
			}
			else {
				commercePriceListQualificationTypeRel = (CommercePriceListQualificationTypeRel)session.merge(commercePriceListQualificationTypeRel);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (!CommercePriceListQualificationTypeRelModelImpl.COLUMN_BITMASK_ENABLED) {
			finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}
		else
		 if (isNew) {
			Object[] args = new Object[] {
					commercePriceListQualificationTypeRelModelImpl.getUuid()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID, args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID,
				args);

			args = new Object[] {
					commercePriceListQualificationTypeRelModelImpl.getUuid(),
					commercePriceListQualificationTypeRelModelImpl.getCompanyId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID_C, args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C,
				args);

			args = new Object[] {
					commercePriceListQualificationTypeRelModelImpl.getCommercePriceListId()
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
			if ((commercePriceListQualificationTypeRelModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						commercePriceListQualificationTypeRelModelImpl.getOriginalUuid()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID,
					args);

				args = new Object[] {
						commercePriceListQualificationTypeRelModelImpl.getUuid()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID,
					args);
			}

			if ((commercePriceListQualificationTypeRelModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						commercePriceListQualificationTypeRelModelImpl.getOriginalUuid(),
						commercePriceListQualificationTypeRelModelImpl.getOriginalCompanyId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID_C, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C,
					args);

				args = new Object[] {
						commercePriceListQualificationTypeRelModelImpl.getUuid(),
						commercePriceListQualificationTypeRelModelImpl.getCompanyId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID_C, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C,
					args);
			}

			if ((commercePriceListQualificationTypeRelModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMMERCEPRICELISTID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						commercePriceListQualificationTypeRelModelImpl.getOriginalCommercePriceListId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_COMMERCEPRICELISTID,
					args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMMERCEPRICELISTID,
					args);

				args = new Object[] {
						commercePriceListQualificationTypeRelModelImpl.getCommercePriceListId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_COMMERCEPRICELISTID,
					args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMMERCEPRICELISTID,
					args);
			}
		}

		entityCache.putResult(CommercePriceListQualificationTypeRelModelImpl.ENTITY_CACHE_ENABLED,
			CommercePriceListQualificationTypeRelImpl.class,
			commercePriceListQualificationTypeRel.getPrimaryKey(),
			commercePriceListQualificationTypeRel, false);

		clearUniqueFindersCache(commercePriceListQualificationTypeRelModelImpl,
			false);
		cacheUniqueFindersCache(commercePriceListQualificationTypeRelModelImpl);

		commercePriceListQualificationTypeRel.resetOriginalValues();

		return commercePriceListQualificationTypeRel;
	}

	protected CommercePriceListQualificationTypeRel toUnwrappedModel(
		CommercePriceListQualificationTypeRel commercePriceListQualificationTypeRel) {
		if (commercePriceListQualificationTypeRel instanceof CommercePriceListQualificationTypeRelImpl) {
			return commercePriceListQualificationTypeRel;
		}

		CommercePriceListQualificationTypeRelImpl commercePriceListQualificationTypeRelImpl =
			new CommercePriceListQualificationTypeRelImpl();

		commercePriceListQualificationTypeRelImpl.setNew(commercePriceListQualificationTypeRel.isNew());
		commercePriceListQualificationTypeRelImpl.setPrimaryKey(commercePriceListQualificationTypeRel.getPrimaryKey());

		commercePriceListQualificationTypeRelImpl.setUuid(commercePriceListQualificationTypeRel.getUuid());
		commercePriceListQualificationTypeRelImpl.setCommercePriceListQualificationTypeRelId(commercePriceListQualificationTypeRel.getCommercePriceListQualificationTypeRelId());
		commercePriceListQualificationTypeRelImpl.setGroupId(commercePriceListQualificationTypeRel.getGroupId());
		commercePriceListQualificationTypeRelImpl.setCompanyId(commercePriceListQualificationTypeRel.getCompanyId());
		commercePriceListQualificationTypeRelImpl.setUserId(commercePriceListQualificationTypeRel.getUserId());
		commercePriceListQualificationTypeRelImpl.setUserName(commercePriceListQualificationTypeRel.getUserName());
		commercePriceListQualificationTypeRelImpl.setCreateDate(commercePriceListQualificationTypeRel.getCreateDate());
		commercePriceListQualificationTypeRelImpl.setModifiedDate(commercePriceListQualificationTypeRel.getModifiedDate());
		commercePriceListQualificationTypeRelImpl.setCommercePriceListId(commercePriceListQualificationTypeRel.getCommercePriceListId());
		commercePriceListQualificationTypeRelImpl.setCommercePriceListQualificationType(commercePriceListQualificationTypeRel.getCommercePriceListQualificationType());
		commercePriceListQualificationTypeRelImpl.setOrder(commercePriceListQualificationTypeRel.getOrder());
		commercePriceListQualificationTypeRelImpl.setLastPublishDate(commercePriceListQualificationTypeRel.getLastPublishDate());

		return commercePriceListQualificationTypeRelImpl;
	}

	/**
	 * Returns the commerce price list qualification type rel with the primary key or throws a {@link com.liferay.portal.kernel.exception.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the commerce price list qualification type rel
	 * @return the commerce price list qualification type rel
	 * @throws NoSuchPriceListQualificationTypeRelException if a commerce price list qualification type rel with the primary key could not be found
	 */
	@Override
	public CommercePriceListQualificationTypeRel findByPrimaryKey(
		Serializable primaryKey)
		throws NoSuchPriceListQualificationTypeRelException {
		CommercePriceListQualificationTypeRel commercePriceListQualificationTypeRel =
			fetchByPrimaryKey(primaryKey);

		if (commercePriceListQualificationTypeRel == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchPriceListQualificationTypeRelException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				primaryKey);
		}

		return commercePriceListQualificationTypeRel;
	}

	/**
	 * Returns the commerce price list qualification type rel with the primary key or throws a {@link NoSuchPriceListQualificationTypeRelException} if it could not be found.
	 *
	 * @param commercePriceListQualificationTypeRelId the primary key of the commerce price list qualification type rel
	 * @return the commerce price list qualification type rel
	 * @throws NoSuchPriceListQualificationTypeRelException if a commerce price list qualification type rel with the primary key could not be found
	 */
	@Override
	public CommercePriceListQualificationTypeRel findByPrimaryKey(
		long commercePriceListQualificationTypeRelId)
		throws NoSuchPriceListQualificationTypeRelException {
		return findByPrimaryKey((Serializable)commercePriceListQualificationTypeRelId);
	}

	/**
	 * Returns the commerce price list qualification type rel with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the commerce price list qualification type rel
	 * @return the commerce price list qualification type rel, or <code>null</code> if a commerce price list qualification type rel with the primary key could not be found
	 */
	@Override
	public CommercePriceListQualificationTypeRel fetchByPrimaryKey(
		Serializable primaryKey) {
		Serializable serializable = entityCache.getResult(CommercePriceListQualificationTypeRelModelImpl.ENTITY_CACHE_ENABLED,
				CommercePriceListQualificationTypeRelImpl.class, primaryKey);

		if (serializable == nullModel) {
			return null;
		}

		CommercePriceListQualificationTypeRel commercePriceListQualificationTypeRel =
			(CommercePriceListQualificationTypeRel)serializable;

		if (commercePriceListQualificationTypeRel == null) {
			Session session = null;

			try {
				session = openSession();

				commercePriceListQualificationTypeRel = (CommercePriceListQualificationTypeRel)session.get(CommercePriceListQualificationTypeRelImpl.class,
						primaryKey);

				if (commercePriceListQualificationTypeRel != null) {
					cacheResult(commercePriceListQualificationTypeRel);
				}
				else {
					entityCache.putResult(CommercePriceListQualificationTypeRelModelImpl.ENTITY_CACHE_ENABLED,
						CommercePriceListQualificationTypeRelImpl.class,
						primaryKey, nullModel);
				}
			}
			catch (Exception e) {
				entityCache.removeResult(CommercePriceListQualificationTypeRelModelImpl.ENTITY_CACHE_ENABLED,
					CommercePriceListQualificationTypeRelImpl.class, primaryKey);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return commercePriceListQualificationTypeRel;
	}

	/**
	 * Returns the commerce price list qualification type rel with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param commercePriceListQualificationTypeRelId the primary key of the commerce price list qualification type rel
	 * @return the commerce price list qualification type rel, or <code>null</code> if a commerce price list qualification type rel with the primary key could not be found
	 */
	@Override
	public CommercePriceListQualificationTypeRel fetchByPrimaryKey(
		long commercePriceListQualificationTypeRelId) {
		return fetchByPrimaryKey((Serializable)commercePriceListQualificationTypeRelId);
	}

	@Override
	public Map<Serializable, CommercePriceListQualificationTypeRel> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {
		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, CommercePriceListQualificationTypeRel> map = new HashMap<Serializable, CommercePriceListQualificationTypeRel>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			CommercePriceListQualificationTypeRel commercePriceListQualificationTypeRel =
				fetchByPrimaryKey(primaryKey);

			if (commercePriceListQualificationTypeRel != null) {
				map.put(primaryKey, commercePriceListQualificationTypeRel);
			}

			return map;
		}

		Set<Serializable> uncachedPrimaryKeys = null;

		for (Serializable primaryKey : primaryKeys) {
			Serializable serializable = entityCache.getResult(CommercePriceListQualificationTypeRelModelImpl.ENTITY_CACHE_ENABLED,
					CommercePriceListQualificationTypeRelImpl.class, primaryKey);

			if (serializable != nullModel) {
				if (serializable == null) {
					if (uncachedPrimaryKeys == null) {
						uncachedPrimaryKeys = new HashSet<Serializable>();
					}

					uncachedPrimaryKeys.add(primaryKey);
				}
				else {
					map.put(primaryKey,
						(CommercePriceListQualificationTypeRel)serializable);
				}
			}
		}

		if (uncachedPrimaryKeys == null) {
			return map;
		}

		StringBundler query = new StringBundler((uncachedPrimaryKeys.size() * 2) +
				1);

		query.append(_SQL_SELECT_COMMERCEPRICELISTQUALIFICATIONTYPEREL_WHERE_PKS_IN);

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

			for (CommercePriceListQualificationTypeRel commercePriceListQualificationTypeRel : (List<CommercePriceListQualificationTypeRel>)q.list()) {
				map.put(commercePriceListQualificationTypeRel.getPrimaryKeyObj(),
					commercePriceListQualificationTypeRel);

				cacheResult(commercePriceListQualificationTypeRel);

				uncachedPrimaryKeys.remove(commercePriceListQualificationTypeRel.getPrimaryKeyObj());
			}

			for (Serializable primaryKey : uncachedPrimaryKeys) {
				entityCache.putResult(CommercePriceListQualificationTypeRelModelImpl.ENTITY_CACHE_ENABLED,
					CommercePriceListQualificationTypeRelImpl.class,
					primaryKey, nullModel);
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
	 * Returns all the commerce price list qualification type rels.
	 *
	 * @return the commerce price list qualification type rels
	 */
	@Override
	public List<CommercePriceListQualificationTypeRel> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce price list qualification type rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommercePriceListQualificationTypeRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce price list qualification type rels
	 * @param end the upper bound of the range of commerce price list qualification type rels (not inclusive)
	 * @return the range of commerce price list qualification type rels
	 */
	@Override
	public List<CommercePriceListQualificationTypeRel> findAll(int start,
		int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce price list qualification type rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommercePriceListQualificationTypeRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce price list qualification type rels
	 * @param end the upper bound of the range of commerce price list qualification type rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of commerce price list qualification type rels
	 */
	@Override
	public List<CommercePriceListQualificationTypeRel> findAll(int start,
		int end,
		OrderByComparator<CommercePriceListQualificationTypeRel> orderByComparator) {
		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce price list qualification type rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommercePriceListQualificationTypeRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce price list qualification type rels
	 * @param end the upper bound of the range of commerce price list qualification type rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of commerce price list qualification type rels
	 */
	@Override
	public List<CommercePriceListQualificationTypeRel> findAll(int start,
		int end,
		OrderByComparator<CommercePriceListQualificationTypeRel> orderByComparator,
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

		List<CommercePriceListQualificationTypeRel> list = null;

		if (retrieveFromCache) {
			list = (List<CommercePriceListQualificationTypeRel>)finderCache.getResult(finderPath,
					finderArgs, this);
		}

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 2));

				query.append(_SQL_SELECT_COMMERCEPRICELISTQUALIFICATIONTYPEREL);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_COMMERCEPRICELISTQUALIFICATIONTYPEREL;

				if (pagination) {
					sql = sql.concat(CommercePriceListQualificationTypeRelModelImpl.ORDER_BY_JPQL);
				}
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (!pagination) {
					list = (List<CommercePriceListQualificationTypeRel>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<CommercePriceListQualificationTypeRel>)QueryUtil.list(q,
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
	 * Removes all the commerce price list qualification type rels from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (CommercePriceListQualificationTypeRel commercePriceListQualificationTypeRel : findAll()) {
			remove(commercePriceListQualificationTypeRel);
		}
	}

	/**
	 * Returns the number of commerce price list qualification type rels.
	 *
	 * @return the number of commerce price list qualification type rels
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_COMMERCEPRICELISTQUALIFICATIONTYPEREL);

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
		return CommercePriceListQualificationTypeRelModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the commerce price list qualification type rel persistence.
	 */
	public void afterPropertiesSet() {
	}

	public void destroy() {
		entityCache.removeCache(CommercePriceListQualificationTypeRelImpl.class.getName());
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
	private static final String _SQL_SELECT_COMMERCEPRICELISTQUALIFICATIONTYPEREL =
		"SELECT commercePriceListQualificationTypeRel FROM CommercePriceListQualificationTypeRel commercePriceListQualificationTypeRel";
	private static final String _SQL_SELECT_COMMERCEPRICELISTQUALIFICATIONTYPEREL_WHERE_PKS_IN =
		"SELECT commercePriceListQualificationTypeRel FROM CommercePriceListQualificationTypeRel commercePriceListQualificationTypeRel WHERE CPLQualificationTypeRelId IN (";
	private static final String _SQL_SELECT_COMMERCEPRICELISTQUALIFICATIONTYPEREL_WHERE =
		"SELECT commercePriceListQualificationTypeRel FROM CommercePriceListQualificationTypeRel commercePriceListQualificationTypeRel WHERE ";
	private static final String _SQL_COUNT_COMMERCEPRICELISTQUALIFICATIONTYPEREL =
		"SELECT COUNT(commercePriceListQualificationTypeRel) FROM CommercePriceListQualificationTypeRel commercePriceListQualificationTypeRel";
	private static final String _SQL_COUNT_COMMERCEPRICELISTQUALIFICATIONTYPEREL_WHERE =
		"SELECT COUNT(commercePriceListQualificationTypeRel) FROM CommercePriceListQualificationTypeRel commercePriceListQualificationTypeRel WHERE ";
	private static final String _ORDER_BY_ENTITY_ALIAS = "commercePriceListQualificationTypeRel.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No CommercePriceListQualificationTypeRel exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No CommercePriceListQualificationTypeRel exists with the key {";
	private static final Log _log = LogFactoryUtil.getLog(CommercePriceListQualificationTypeRelPersistenceImpl.class);
	private static final Set<String> _badColumnNames = SetUtil.fromArray(new String[] {
				"uuid", "commercePriceListQualificationTypeRelId",
				"commercePriceListQualificationType", "order"
			});
}