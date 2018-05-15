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

package com.liferay.commerce.price.list.service.persistence.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.commerce.price.list.exception.NoSuchTierPriceEntryException;
import com.liferay.commerce.price.list.model.CommerceTierPriceEntry;
import com.liferay.commerce.price.list.model.impl.CommerceTierPriceEntryImpl;
import com.liferay.commerce.price.list.model.impl.CommerceTierPriceEntryModelImpl;
import com.liferay.commerce.price.list.service.persistence.CommerceTierPriceEntryPersistence;

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
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;
import com.liferay.portal.spring.extender.service.ServiceReference;

import java.io.Serializable;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;

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
 * The persistence implementation for the commerce tier price entry service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Alessio Antonio Rendina
 * @see CommerceTierPriceEntryPersistence
 * @see com.liferay.commerce.price.list.service.persistence.CommerceTierPriceEntryUtil
 * @generated
 */
@ProviderType
public class CommerceTierPriceEntryPersistenceImpl extends BasePersistenceImpl<CommerceTierPriceEntry>
	implements CommerceTierPriceEntryPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link CommerceTierPriceEntryUtil} to access the commerce tier price entry persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = CommerceTierPriceEntryImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(CommerceTierPriceEntryModelImpl.ENTITY_CACHE_ENABLED,
			CommerceTierPriceEntryModelImpl.FINDER_CACHE_ENABLED,
			CommerceTierPriceEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(CommerceTierPriceEntryModelImpl.ENTITY_CACHE_ENABLED,
			CommerceTierPriceEntryModelImpl.FINDER_CACHE_ENABLED,
			CommerceTierPriceEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(CommerceTierPriceEntryModelImpl.ENTITY_CACHE_ENABLED,
			CommerceTierPriceEntryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_UUID = new FinderPath(CommerceTierPriceEntryModelImpl.ENTITY_CACHE_ENABLED,
			CommerceTierPriceEntryModelImpl.FINDER_CACHE_ENABLED,
			CommerceTierPriceEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUuid",
			new String[] {
				String.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID = new FinderPath(CommerceTierPriceEntryModelImpl.ENTITY_CACHE_ENABLED,
			CommerceTierPriceEntryModelImpl.FINDER_CACHE_ENABLED,
			CommerceTierPriceEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid",
			new String[] { String.class.getName() },
			CommerceTierPriceEntryModelImpl.UUID_COLUMN_BITMASK |
			CommerceTierPriceEntryModelImpl.MINQUANTITY_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID = new FinderPath(CommerceTierPriceEntryModelImpl.ENTITY_CACHE_ENABLED,
			CommerceTierPriceEntryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUuid",
			new String[] { String.class.getName() });

	/**
	 * Returns all the commerce tier price entries where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching commerce tier price entries
	 */
	@Override
	public List<CommerceTierPriceEntry> findByUuid(String uuid) {
		return findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce tier price entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceTierPriceEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of commerce tier price entries
	 * @param end the upper bound of the range of commerce tier price entries (not inclusive)
	 * @return the range of matching commerce tier price entries
	 */
	@Override
	public List<CommerceTierPriceEntry> findByUuid(String uuid, int start,
		int end) {
		return findByUuid(uuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce tier price entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceTierPriceEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of commerce tier price entries
	 * @param end the upper bound of the range of commerce tier price entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce tier price entries
	 */
	@Override
	public List<CommerceTierPriceEntry> findByUuid(String uuid, int start,
		int end, OrderByComparator<CommerceTierPriceEntry> orderByComparator) {
		return findByUuid(uuid, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce tier price entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceTierPriceEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of commerce tier price entries
	 * @param end the upper bound of the range of commerce tier price entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching commerce tier price entries
	 */
	@Override
	public List<CommerceTierPriceEntry> findByUuid(String uuid, int start,
		int end, OrderByComparator<CommerceTierPriceEntry> orderByComparator,
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

		List<CommerceTierPriceEntry> list = null;

		if (retrieveFromCache) {
			list = (List<CommerceTierPriceEntry>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (CommerceTierPriceEntry commerceTierPriceEntry : list) {
					if (!Objects.equals(uuid, commerceTierPriceEntry.getUuid())) {
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

			query.append(_SQL_SELECT_COMMERCETIERPRICEENTRY_WHERE);

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
				query.append(CommerceTierPriceEntryModelImpl.ORDER_BY_JPQL);
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
					list = (List<CommerceTierPriceEntry>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<CommerceTierPriceEntry>)QueryUtil.list(q,
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
	 * Returns the first commerce tier price entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce tier price entry
	 * @throws NoSuchTierPriceEntryException if a matching commerce tier price entry could not be found
	 */
	@Override
	public CommerceTierPriceEntry findByUuid_First(String uuid,
		OrderByComparator<CommerceTierPriceEntry> orderByComparator)
		throws NoSuchTierPriceEntryException {
		CommerceTierPriceEntry commerceTierPriceEntry = fetchByUuid_First(uuid,
				orderByComparator);

		if (commerceTierPriceEntry != null) {
			return commerceTierPriceEntry;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append("}");

		throw new NoSuchTierPriceEntryException(msg.toString());
	}

	/**
	 * Returns the first commerce tier price entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce tier price entry, or <code>null</code> if a matching commerce tier price entry could not be found
	 */
	@Override
	public CommerceTierPriceEntry fetchByUuid_First(String uuid,
		OrderByComparator<CommerceTierPriceEntry> orderByComparator) {
		List<CommerceTierPriceEntry> list = findByUuid(uuid, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last commerce tier price entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce tier price entry
	 * @throws NoSuchTierPriceEntryException if a matching commerce tier price entry could not be found
	 */
	@Override
	public CommerceTierPriceEntry findByUuid_Last(String uuid,
		OrderByComparator<CommerceTierPriceEntry> orderByComparator)
		throws NoSuchTierPriceEntryException {
		CommerceTierPriceEntry commerceTierPriceEntry = fetchByUuid_Last(uuid,
				orderByComparator);

		if (commerceTierPriceEntry != null) {
			return commerceTierPriceEntry;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append("}");

		throw new NoSuchTierPriceEntryException(msg.toString());
	}

	/**
	 * Returns the last commerce tier price entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce tier price entry, or <code>null</code> if a matching commerce tier price entry could not be found
	 */
	@Override
	public CommerceTierPriceEntry fetchByUuid_Last(String uuid,
		OrderByComparator<CommerceTierPriceEntry> orderByComparator) {
		int count = countByUuid(uuid);

		if (count == 0) {
			return null;
		}

		List<CommerceTierPriceEntry> list = findByUuid(uuid, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the commerce tier price entries before and after the current commerce tier price entry in the ordered set where uuid = &#63;.
	 *
	 * @param commerceTierPriceEntryId the primary key of the current commerce tier price entry
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce tier price entry
	 * @throws NoSuchTierPriceEntryException if a commerce tier price entry with the primary key could not be found
	 */
	@Override
	public CommerceTierPriceEntry[] findByUuid_PrevAndNext(
		long commerceTierPriceEntryId, String uuid,
		OrderByComparator<CommerceTierPriceEntry> orderByComparator)
		throws NoSuchTierPriceEntryException {
		CommerceTierPriceEntry commerceTierPriceEntry = findByPrimaryKey(commerceTierPriceEntryId);

		Session session = null;

		try {
			session = openSession();

			CommerceTierPriceEntry[] array = new CommerceTierPriceEntryImpl[3];

			array[0] = getByUuid_PrevAndNext(session, commerceTierPriceEntry,
					uuid, orderByComparator, true);

			array[1] = commerceTierPriceEntry;

			array[2] = getByUuid_PrevAndNext(session, commerceTierPriceEntry,
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

	protected CommerceTierPriceEntry getByUuid_PrevAndNext(Session session,
		CommerceTierPriceEntry commerceTierPriceEntry, String uuid,
		OrderByComparator<CommerceTierPriceEntry> orderByComparator,
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

		query.append(_SQL_SELECT_COMMERCETIERPRICEENTRY_WHERE);

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
			query.append(CommerceTierPriceEntryModelImpl.ORDER_BY_JPQL);
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
			Object[] values = orderByComparator.getOrderByConditionValues(commerceTierPriceEntry);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<CommerceTierPriceEntry> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the commerce tier price entries where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	@Override
	public void removeByUuid(String uuid) {
		for (CommerceTierPriceEntry commerceTierPriceEntry : findByUuid(uuid,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(commerceTierPriceEntry);
		}
	}

	/**
	 * Returns the number of commerce tier price entries where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching commerce tier price entries
	 */
	@Override
	public int countByUuid(String uuid) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_UUID;

		Object[] finderArgs = new Object[] { uuid };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_COMMERCETIERPRICEENTRY_WHERE);

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

	private static final String _FINDER_COLUMN_UUID_UUID_1 = "commerceTierPriceEntry.uuid IS NULL";
	private static final String _FINDER_COLUMN_UUID_UUID_2 = "commerceTierPriceEntry.uuid = ?";
	private static final String _FINDER_COLUMN_UUID_UUID_3 = "(commerceTierPriceEntry.uuid IS NULL OR commerceTierPriceEntry.uuid = '')";
	public static final FinderPath FINDER_PATH_FETCH_BY_UUID_G = new FinderPath(CommerceTierPriceEntryModelImpl.ENTITY_CACHE_ENABLED,
			CommerceTierPriceEntryModelImpl.FINDER_CACHE_ENABLED,
			CommerceTierPriceEntryImpl.class, FINDER_CLASS_NAME_ENTITY,
			"fetchByUUID_G",
			new String[] { String.class.getName(), Long.class.getName() },
			CommerceTierPriceEntryModelImpl.UUID_COLUMN_BITMASK |
			CommerceTierPriceEntryModelImpl.GROUPID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID_G = new FinderPath(CommerceTierPriceEntryModelImpl.ENTITY_CACHE_ENABLED,
			CommerceTierPriceEntryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUUID_G",
			new String[] { String.class.getName(), Long.class.getName() });

	/**
	 * Returns the commerce tier price entry where uuid = &#63; and groupId = &#63; or throws a {@link NoSuchTierPriceEntryException} if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching commerce tier price entry
	 * @throws NoSuchTierPriceEntryException if a matching commerce tier price entry could not be found
	 */
	@Override
	public CommerceTierPriceEntry findByUUID_G(String uuid, long groupId)
		throws NoSuchTierPriceEntryException {
		CommerceTierPriceEntry commerceTierPriceEntry = fetchByUUID_G(uuid,
				groupId);

		if (commerceTierPriceEntry == null) {
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

			throw new NoSuchTierPriceEntryException(msg.toString());
		}

		return commerceTierPriceEntry;
	}

	/**
	 * Returns the commerce tier price entry where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching commerce tier price entry, or <code>null</code> if a matching commerce tier price entry could not be found
	 */
	@Override
	public CommerceTierPriceEntry fetchByUUID_G(String uuid, long groupId) {
		return fetchByUUID_G(uuid, groupId, true);
	}

	/**
	 * Returns the commerce tier price entry where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the matching commerce tier price entry, or <code>null</code> if a matching commerce tier price entry could not be found
	 */
	@Override
	public CommerceTierPriceEntry fetchByUUID_G(String uuid, long groupId,
		boolean retrieveFromCache) {
		Object[] finderArgs = new Object[] { uuid, groupId };

		Object result = null;

		if (retrieveFromCache) {
			result = finderCache.getResult(FINDER_PATH_FETCH_BY_UUID_G,
					finderArgs, this);
		}

		if (result instanceof CommerceTierPriceEntry) {
			CommerceTierPriceEntry commerceTierPriceEntry = (CommerceTierPriceEntry)result;

			if (!Objects.equals(uuid, commerceTierPriceEntry.getUuid()) ||
					(groupId != commerceTierPriceEntry.getGroupId())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_SELECT_COMMERCETIERPRICEENTRY_WHERE);

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

				List<CommerceTierPriceEntry> list = q.list();

				if (list.isEmpty()) {
					finderCache.putResult(FINDER_PATH_FETCH_BY_UUID_G,
						finderArgs, list);
				}
				else {
					CommerceTierPriceEntry commerceTierPriceEntry = list.get(0);

					result = commerceTierPriceEntry;

					cacheResult(commerceTierPriceEntry);

					if ((commerceTierPriceEntry.getUuid() == null) ||
							!commerceTierPriceEntry.getUuid().equals(uuid) ||
							(commerceTierPriceEntry.getGroupId() != groupId)) {
						finderCache.putResult(FINDER_PATH_FETCH_BY_UUID_G,
							finderArgs, commerceTierPriceEntry);
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
			return (CommerceTierPriceEntry)result;
		}
	}

	/**
	 * Removes the commerce tier price entry where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the commerce tier price entry that was removed
	 */
	@Override
	public CommerceTierPriceEntry removeByUUID_G(String uuid, long groupId)
		throws NoSuchTierPriceEntryException {
		CommerceTierPriceEntry commerceTierPriceEntry = findByUUID_G(uuid,
				groupId);

		return remove(commerceTierPriceEntry);
	}

	/**
	 * Returns the number of commerce tier price entries where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching commerce tier price entries
	 */
	@Override
	public int countByUUID_G(String uuid, long groupId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_UUID_G;

		Object[] finderArgs = new Object[] { uuid, groupId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_COMMERCETIERPRICEENTRY_WHERE);

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

	private static final String _FINDER_COLUMN_UUID_G_UUID_1 = "commerceTierPriceEntry.uuid IS NULL AND ";
	private static final String _FINDER_COLUMN_UUID_G_UUID_2 = "commerceTierPriceEntry.uuid = ? AND ";
	private static final String _FINDER_COLUMN_UUID_G_UUID_3 = "(commerceTierPriceEntry.uuid IS NULL OR commerceTierPriceEntry.uuid = '') AND ";
	private static final String _FINDER_COLUMN_UUID_G_GROUPID_2 = "commerceTierPriceEntry.groupId = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_UUID_C = new FinderPath(CommerceTierPriceEntryModelImpl.ENTITY_CACHE_ENABLED,
			CommerceTierPriceEntryModelImpl.FINDER_CACHE_ENABLED,
			CommerceTierPriceEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUuid_C",
			new String[] {
				String.class.getName(), Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C =
		new FinderPath(CommerceTierPriceEntryModelImpl.ENTITY_CACHE_ENABLED,
			CommerceTierPriceEntryModelImpl.FINDER_CACHE_ENABLED,
			CommerceTierPriceEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid_C",
			new String[] { String.class.getName(), Long.class.getName() },
			CommerceTierPriceEntryModelImpl.UUID_COLUMN_BITMASK |
			CommerceTierPriceEntryModelImpl.COMPANYID_COLUMN_BITMASK |
			CommerceTierPriceEntryModelImpl.MINQUANTITY_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID_C = new FinderPath(CommerceTierPriceEntryModelImpl.ENTITY_CACHE_ENABLED,
			CommerceTierPriceEntryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUuid_C",
			new String[] { String.class.getName(), Long.class.getName() });

	/**
	 * Returns all the commerce tier price entries where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching commerce tier price entries
	 */
	@Override
	public List<CommerceTierPriceEntry> findByUuid_C(String uuid, long companyId) {
		return findByUuid_C(uuid, companyId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce tier price entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceTierPriceEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce tier price entries
	 * @param end the upper bound of the range of commerce tier price entries (not inclusive)
	 * @return the range of matching commerce tier price entries
	 */
	@Override
	public List<CommerceTierPriceEntry> findByUuid_C(String uuid,
		long companyId, int start, int end) {
		return findByUuid_C(uuid, companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce tier price entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceTierPriceEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce tier price entries
	 * @param end the upper bound of the range of commerce tier price entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce tier price entries
	 */
	@Override
	public List<CommerceTierPriceEntry> findByUuid_C(String uuid,
		long companyId, int start, int end,
		OrderByComparator<CommerceTierPriceEntry> orderByComparator) {
		return findByUuid_C(uuid, companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce tier price entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceTierPriceEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce tier price entries
	 * @param end the upper bound of the range of commerce tier price entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching commerce tier price entries
	 */
	@Override
	public List<CommerceTierPriceEntry> findByUuid_C(String uuid,
		long companyId, int start, int end,
		OrderByComparator<CommerceTierPriceEntry> orderByComparator,
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

		List<CommerceTierPriceEntry> list = null;

		if (retrieveFromCache) {
			list = (List<CommerceTierPriceEntry>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (CommerceTierPriceEntry commerceTierPriceEntry : list) {
					if (!Objects.equals(uuid, commerceTierPriceEntry.getUuid()) ||
							(companyId != commerceTierPriceEntry.getCompanyId())) {
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

			query.append(_SQL_SELECT_COMMERCETIERPRICEENTRY_WHERE);

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
				query.append(CommerceTierPriceEntryModelImpl.ORDER_BY_JPQL);
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
					list = (List<CommerceTierPriceEntry>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<CommerceTierPriceEntry>)QueryUtil.list(q,
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
	 * Returns the first commerce tier price entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce tier price entry
	 * @throws NoSuchTierPriceEntryException if a matching commerce tier price entry could not be found
	 */
	@Override
	public CommerceTierPriceEntry findByUuid_C_First(String uuid,
		long companyId,
		OrderByComparator<CommerceTierPriceEntry> orderByComparator)
		throws NoSuchTierPriceEntryException {
		CommerceTierPriceEntry commerceTierPriceEntry = fetchByUuid_C_First(uuid,
				companyId, orderByComparator);

		if (commerceTierPriceEntry != null) {
			return commerceTierPriceEntry;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(", companyId=");
		msg.append(companyId);

		msg.append("}");

		throw new NoSuchTierPriceEntryException(msg.toString());
	}

	/**
	 * Returns the first commerce tier price entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce tier price entry, or <code>null</code> if a matching commerce tier price entry could not be found
	 */
	@Override
	public CommerceTierPriceEntry fetchByUuid_C_First(String uuid,
		long companyId,
		OrderByComparator<CommerceTierPriceEntry> orderByComparator) {
		List<CommerceTierPriceEntry> list = findByUuid_C(uuid, companyId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last commerce tier price entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce tier price entry
	 * @throws NoSuchTierPriceEntryException if a matching commerce tier price entry could not be found
	 */
	@Override
	public CommerceTierPriceEntry findByUuid_C_Last(String uuid,
		long companyId,
		OrderByComparator<CommerceTierPriceEntry> orderByComparator)
		throws NoSuchTierPriceEntryException {
		CommerceTierPriceEntry commerceTierPriceEntry = fetchByUuid_C_Last(uuid,
				companyId, orderByComparator);

		if (commerceTierPriceEntry != null) {
			return commerceTierPriceEntry;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(", companyId=");
		msg.append(companyId);

		msg.append("}");

		throw new NoSuchTierPriceEntryException(msg.toString());
	}

	/**
	 * Returns the last commerce tier price entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce tier price entry, or <code>null</code> if a matching commerce tier price entry could not be found
	 */
	@Override
	public CommerceTierPriceEntry fetchByUuid_C_Last(String uuid,
		long companyId,
		OrderByComparator<CommerceTierPriceEntry> orderByComparator) {
		int count = countByUuid_C(uuid, companyId);

		if (count == 0) {
			return null;
		}

		List<CommerceTierPriceEntry> list = findByUuid_C(uuid, companyId,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the commerce tier price entries before and after the current commerce tier price entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param commerceTierPriceEntryId the primary key of the current commerce tier price entry
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce tier price entry
	 * @throws NoSuchTierPriceEntryException if a commerce tier price entry with the primary key could not be found
	 */
	@Override
	public CommerceTierPriceEntry[] findByUuid_C_PrevAndNext(
		long commerceTierPriceEntryId, String uuid, long companyId,
		OrderByComparator<CommerceTierPriceEntry> orderByComparator)
		throws NoSuchTierPriceEntryException {
		CommerceTierPriceEntry commerceTierPriceEntry = findByPrimaryKey(commerceTierPriceEntryId);

		Session session = null;

		try {
			session = openSession();

			CommerceTierPriceEntry[] array = new CommerceTierPriceEntryImpl[3];

			array[0] = getByUuid_C_PrevAndNext(session, commerceTierPriceEntry,
					uuid, companyId, orderByComparator, true);

			array[1] = commerceTierPriceEntry;

			array[2] = getByUuid_C_PrevAndNext(session, commerceTierPriceEntry,
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

	protected CommerceTierPriceEntry getByUuid_C_PrevAndNext(Session session,
		CommerceTierPriceEntry commerceTierPriceEntry, String uuid,
		long companyId,
		OrderByComparator<CommerceTierPriceEntry> orderByComparator,
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

		query.append(_SQL_SELECT_COMMERCETIERPRICEENTRY_WHERE);

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
			query.append(CommerceTierPriceEntryModelImpl.ORDER_BY_JPQL);
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
			Object[] values = orderByComparator.getOrderByConditionValues(commerceTierPriceEntry);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<CommerceTierPriceEntry> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the commerce tier price entries where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	@Override
	public void removeByUuid_C(String uuid, long companyId) {
		for (CommerceTierPriceEntry commerceTierPriceEntry : findByUuid_C(
				uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(commerceTierPriceEntry);
		}
	}

	/**
	 * Returns the number of commerce tier price entries where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching commerce tier price entries
	 */
	@Override
	public int countByUuid_C(String uuid, long companyId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_UUID_C;

		Object[] finderArgs = new Object[] { uuid, companyId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_COMMERCETIERPRICEENTRY_WHERE);

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

	private static final String _FINDER_COLUMN_UUID_C_UUID_1 = "commerceTierPriceEntry.uuid IS NULL AND ";
	private static final String _FINDER_COLUMN_UUID_C_UUID_2 = "commerceTierPriceEntry.uuid = ? AND ";
	private static final String _FINDER_COLUMN_UUID_C_UUID_3 = "(commerceTierPriceEntry.uuid IS NULL OR commerceTierPriceEntry.uuid = '') AND ";
	private static final String _FINDER_COLUMN_UUID_C_COMPANYID_2 = "commerceTierPriceEntry.companyId = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_GROUPID = new FinderPath(CommerceTierPriceEntryModelImpl.ENTITY_CACHE_ENABLED,
			CommerceTierPriceEntryModelImpl.FINDER_CACHE_ENABLED,
			CommerceTierPriceEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByGroupId",
			new String[] {
				Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID =
		new FinderPath(CommerceTierPriceEntryModelImpl.ENTITY_CACHE_ENABLED,
			CommerceTierPriceEntryModelImpl.FINDER_CACHE_ENABLED,
			CommerceTierPriceEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByGroupId",
			new String[] { Long.class.getName() },
			CommerceTierPriceEntryModelImpl.GROUPID_COLUMN_BITMASK |
			CommerceTierPriceEntryModelImpl.MINQUANTITY_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_GROUPID = new FinderPath(CommerceTierPriceEntryModelImpl.ENTITY_CACHE_ENABLED,
			CommerceTierPriceEntryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByGroupId",
			new String[] { Long.class.getName() });

	/**
	 * Returns all the commerce tier price entries where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching commerce tier price entries
	 */
	@Override
	public List<CommerceTierPriceEntry> findByGroupId(long groupId) {
		return findByGroupId(groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce tier price entries where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceTierPriceEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of commerce tier price entries
	 * @param end the upper bound of the range of commerce tier price entries (not inclusive)
	 * @return the range of matching commerce tier price entries
	 */
	@Override
	public List<CommerceTierPriceEntry> findByGroupId(long groupId, int start,
		int end) {
		return findByGroupId(groupId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce tier price entries where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceTierPriceEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of commerce tier price entries
	 * @param end the upper bound of the range of commerce tier price entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce tier price entries
	 */
	@Override
	public List<CommerceTierPriceEntry> findByGroupId(long groupId, int start,
		int end, OrderByComparator<CommerceTierPriceEntry> orderByComparator) {
		return findByGroupId(groupId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce tier price entries where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceTierPriceEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of commerce tier price entries
	 * @param end the upper bound of the range of commerce tier price entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching commerce tier price entries
	 */
	@Override
	public List<CommerceTierPriceEntry> findByGroupId(long groupId, int start,
		int end, OrderByComparator<CommerceTierPriceEntry> orderByComparator,
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

		List<CommerceTierPriceEntry> list = null;

		if (retrieveFromCache) {
			list = (List<CommerceTierPriceEntry>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (CommerceTierPriceEntry commerceTierPriceEntry : list) {
					if ((groupId != commerceTierPriceEntry.getGroupId())) {
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

			query.append(_SQL_SELECT_COMMERCETIERPRICEENTRY_WHERE);

			query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(CommerceTierPriceEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (!pagination) {
					list = (List<CommerceTierPriceEntry>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<CommerceTierPriceEntry>)QueryUtil.list(q,
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
	 * Returns the first commerce tier price entry in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce tier price entry
	 * @throws NoSuchTierPriceEntryException if a matching commerce tier price entry could not be found
	 */
	@Override
	public CommerceTierPriceEntry findByGroupId_First(long groupId,
		OrderByComparator<CommerceTierPriceEntry> orderByComparator)
		throws NoSuchTierPriceEntryException {
		CommerceTierPriceEntry commerceTierPriceEntry = fetchByGroupId_First(groupId,
				orderByComparator);

		if (commerceTierPriceEntry != null) {
			return commerceTierPriceEntry;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append("}");

		throw new NoSuchTierPriceEntryException(msg.toString());
	}

	/**
	 * Returns the first commerce tier price entry in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce tier price entry, or <code>null</code> if a matching commerce tier price entry could not be found
	 */
	@Override
	public CommerceTierPriceEntry fetchByGroupId_First(long groupId,
		OrderByComparator<CommerceTierPriceEntry> orderByComparator) {
		List<CommerceTierPriceEntry> list = findByGroupId(groupId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last commerce tier price entry in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce tier price entry
	 * @throws NoSuchTierPriceEntryException if a matching commerce tier price entry could not be found
	 */
	@Override
	public CommerceTierPriceEntry findByGroupId_Last(long groupId,
		OrderByComparator<CommerceTierPriceEntry> orderByComparator)
		throws NoSuchTierPriceEntryException {
		CommerceTierPriceEntry commerceTierPriceEntry = fetchByGroupId_Last(groupId,
				orderByComparator);

		if (commerceTierPriceEntry != null) {
			return commerceTierPriceEntry;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append("}");

		throw new NoSuchTierPriceEntryException(msg.toString());
	}

	/**
	 * Returns the last commerce tier price entry in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce tier price entry, or <code>null</code> if a matching commerce tier price entry could not be found
	 */
	@Override
	public CommerceTierPriceEntry fetchByGroupId_Last(long groupId,
		OrderByComparator<CommerceTierPriceEntry> orderByComparator) {
		int count = countByGroupId(groupId);

		if (count == 0) {
			return null;
		}

		List<CommerceTierPriceEntry> list = findByGroupId(groupId, count - 1,
				count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the commerce tier price entries before and after the current commerce tier price entry in the ordered set where groupId = &#63;.
	 *
	 * @param commerceTierPriceEntryId the primary key of the current commerce tier price entry
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce tier price entry
	 * @throws NoSuchTierPriceEntryException if a commerce tier price entry with the primary key could not be found
	 */
	@Override
	public CommerceTierPriceEntry[] findByGroupId_PrevAndNext(
		long commerceTierPriceEntryId, long groupId,
		OrderByComparator<CommerceTierPriceEntry> orderByComparator)
		throws NoSuchTierPriceEntryException {
		CommerceTierPriceEntry commerceTierPriceEntry = findByPrimaryKey(commerceTierPriceEntryId);

		Session session = null;

		try {
			session = openSession();

			CommerceTierPriceEntry[] array = new CommerceTierPriceEntryImpl[3];

			array[0] = getByGroupId_PrevAndNext(session,
					commerceTierPriceEntry, groupId, orderByComparator, true);

			array[1] = commerceTierPriceEntry;

			array[2] = getByGroupId_PrevAndNext(session,
					commerceTierPriceEntry, groupId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected CommerceTierPriceEntry getByGroupId_PrevAndNext(Session session,
		CommerceTierPriceEntry commerceTierPriceEntry, long groupId,
		OrderByComparator<CommerceTierPriceEntry> orderByComparator,
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

		query.append(_SQL_SELECT_COMMERCETIERPRICEENTRY_WHERE);

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
			query.append(CommerceTierPriceEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(commerceTierPriceEntry);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<CommerceTierPriceEntry> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the commerce tier price entries where groupId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 */
	@Override
	public void removeByGroupId(long groupId) {
		for (CommerceTierPriceEntry commerceTierPriceEntry : findByGroupId(
				groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(commerceTierPriceEntry);
		}
	}

	/**
	 * Returns the number of commerce tier price entries where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching commerce tier price entries
	 */
	@Override
	public int countByGroupId(long groupId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_GROUPID;

		Object[] finderArgs = new Object[] { groupId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_COMMERCETIERPRICEENTRY_WHERE);

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

	private static final String _FINDER_COLUMN_GROUPID_GROUPID_2 = "commerceTierPriceEntry.groupId = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_COMPANYID =
		new FinderPath(CommerceTierPriceEntryModelImpl.ENTITY_CACHE_ENABLED,
			CommerceTierPriceEntryModelImpl.FINDER_CACHE_ENABLED,
			CommerceTierPriceEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByCompanyId",
			new String[] {
				Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID =
		new FinderPath(CommerceTierPriceEntryModelImpl.ENTITY_CACHE_ENABLED,
			CommerceTierPriceEntryModelImpl.FINDER_CACHE_ENABLED,
			CommerceTierPriceEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByCompanyId",
			new String[] { Long.class.getName() },
			CommerceTierPriceEntryModelImpl.COMPANYID_COLUMN_BITMASK |
			CommerceTierPriceEntryModelImpl.MINQUANTITY_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_COMPANYID = new FinderPath(CommerceTierPriceEntryModelImpl.ENTITY_CACHE_ENABLED,
			CommerceTierPriceEntryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByCompanyId",
			new String[] { Long.class.getName() });

	/**
	 * Returns all the commerce tier price entries where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching commerce tier price entries
	 */
	@Override
	public List<CommerceTierPriceEntry> findByCompanyId(long companyId) {
		return findByCompanyId(companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the commerce tier price entries where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceTierPriceEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce tier price entries
	 * @param end the upper bound of the range of commerce tier price entries (not inclusive)
	 * @return the range of matching commerce tier price entries
	 */
	@Override
	public List<CommerceTierPriceEntry> findByCompanyId(long companyId,
		int start, int end) {
		return findByCompanyId(companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce tier price entries where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceTierPriceEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce tier price entries
	 * @param end the upper bound of the range of commerce tier price entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce tier price entries
	 */
	@Override
	public List<CommerceTierPriceEntry> findByCompanyId(long companyId,
		int start, int end,
		OrderByComparator<CommerceTierPriceEntry> orderByComparator) {
		return findByCompanyId(companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce tier price entries where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceTierPriceEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce tier price entries
	 * @param end the upper bound of the range of commerce tier price entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching commerce tier price entries
	 */
	@Override
	public List<CommerceTierPriceEntry> findByCompanyId(long companyId,
		int start, int end,
		OrderByComparator<CommerceTierPriceEntry> orderByComparator,
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

		List<CommerceTierPriceEntry> list = null;

		if (retrieveFromCache) {
			list = (List<CommerceTierPriceEntry>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (CommerceTierPriceEntry commerceTierPriceEntry : list) {
					if ((companyId != commerceTierPriceEntry.getCompanyId())) {
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

			query.append(_SQL_SELECT_COMMERCETIERPRICEENTRY_WHERE);

			query.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(CommerceTierPriceEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				if (!pagination) {
					list = (List<CommerceTierPriceEntry>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<CommerceTierPriceEntry>)QueryUtil.list(q,
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
	 * Returns the first commerce tier price entry in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce tier price entry
	 * @throws NoSuchTierPriceEntryException if a matching commerce tier price entry could not be found
	 */
	@Override
	public CommerceTierPriceEntry findByCompanyId_First(long companyId,
		OrderByComparator<CommerceTierPriceEntry> orderByComparator)
		throws NoSuchTierPriceEntryException {
		CommerceTierPriceEntry commerceTierPriceEntry = fetchByCompanyId_First(companyId,
				orderByComparator);

		if (commerceTierPriceEntry != null) {
			return commerceTierPriceEntry;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("companyId=");
		msg.append(companyId);

		msg.append("}");

		throw new NoSuchTierPriceEntryException(msg.toString());
	}

	/**
	 * Returns the first commerce tier price entry in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce tier price entry, or <code>null</code> if a matching commerce tier price entry could not be found
	 */
	@Override
	public CommerceTierPriceEntry fetchByCompanyId_First(long companyId,
		OrderByComparator<CommerceTierPriceEntry> orderByComparator) {
		List<CommerceTierPriceEntry> list = findByCompanyId(companyId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last commerce tier price entry in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce tier price entry
	 * @throws NoSuchTierPriceEntryException if a matching commerce tier price entry could not be found
	 */
	@Override
	public CommerceTierPriceEntry findByCompanyId_Last(long companyId,
		OrderByComparator<CommerceTierPriceEntry> orderByComparator)
		throws NoSuchTierPriceEntryException {
		CommerceTierPriceEntry commerceTierPriceEntry = fetchByCompanyId_Last(companyId,
				orderByComparator);

		if (commerceTierPriceEntry != null) {
			return commerceTierPriceEntry;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("companyId=");
		msg.append(companyId);

		msg.append("}");

		throw new NoSuchTierPriceEntryException(msg.toString());
	}

	/**
	 * Returns the last commerce tier price entry in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce tier price entry, or <code>null</code> if a matching commerce tier price entry could not be found
	 */
	@Override
	public CommerceTierPriceEntry fetchByCompanyId_Last(long companyId,
		OrderByComparator<CommerceTierPriceEntry> orderByComparator) {
		int count = countByCompanyId(companyId);

		if (count == 0) {
			return null;
		}

		List<CommerceTierPriceEntry> list = findByCompanyId(companyId,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the commerce tier price entries before and after the current commerce tier price entry in the ordered set where companyId = &#63;.
	 *
	 * @param commerceTierPriceEntryId the primary key of the current commerce tier price entry
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce tier price entry
	 * @throws NoSuchTierPriceEntryException if a commerce tier price entry with the primary key could not be found
	 */
	@Override
	public CommerceTierPriceEntry[] findByCompanyId_PrevAndNext(
		long commerceTierPriceEntryId, long companyId,
		OrderByComparator<CommerceTierPriceEntry> orderByComparator)
		throws NoSuchTierPriceEntryException {
		CommerceTierPriceEntry commerceTierPriceEntry = findByPrimaryKey(commerceTierPriceEntryId);

		Session session = null;

		try {
			session = openSession();

			CommerceTierPriceEntry[] array = new CommerceTierPriceEntryImpl[3];

			array[0] = getByCompanyId_PrevAndNext(session,
					commerceTierPriceEntry, companyId, orderByComparator, true);

			array[1] = commerceTierPriceEntry;

			array[2] = getByCompanyId_PrevAndNext(session,
					commerceTierPriceEntry, companyId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected CommerceTierPriceEntry getByCompanyId_PrevAndNext(
		Session session, CommerceTierPriceEntry commerceTierPriceEntry,
		long companyId,
		OrderByComparator<CommerceTierPriceEntry> orderByComparator,
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

		query.append(_SQL_SELECT_COMMERCETIERPRICEENTRY_WHERE);

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
			query.append(CommerceTierPriceEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(companyId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(commerceTierPriceEntry);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<CommerceTierPriceEntry> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the commerce tier price entries where companyId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 */
	@Override
	public void removeByCompanyId(long companyId) {
		for (CommerceTierPriceEntry commerceTierPriceEntry : findByCompanyId(
				companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(commerceTierPriceEntry);
		}
	}

	/**
	 * Returns the number of commerce tier price entries where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching commerce tier price entries
	 */
	@Override
	public int countByCompanyId(long companyId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_COMPANYID;

		Object[] finderArgs = new Object[] { companyId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_COMMERCETIERPRICEENTRY_WHERE);

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

	private static final String _FINDER_COLUMN_COMPANYID_COMPANYID_2 = "commerceTierPriceEntry.companyId = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_COMMERCEPRICEENTRYID =
		new FinderPath(CommerceTierPriceEntryModelImpl.ENTITY_CACHE_ENABLED,
			CommerceTierPriceEntryModelImpl.FINDER_CACHE_ENABLED,
			CommerceTierPriceEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByCommercePriceEntryId",
			new String[] {
				Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMMERCEPRICEENTRYID =
		new FinderPath(CommerceTierPriceEntryModelImpl.ENTITY_CACHE_ENABLED,
			CommerceTierPriceEntryModelImpl.FINDER_CACHE_ENABLED,
			CommerceTierPriceEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByCommercePriceEntryId",
			new String[] { Long.class.getName() },
			CommerceTierPriceEntryModelImpl.COMMERCEPRICEENTRYID_COLUMN_BITMASK |
			CommerceTierPriceEntryModelImpl.MINQUANTITY_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_COMMERCEPRICEENTRYID = new FinderPath(CommerceTierPriceEntryModelImpl.ENTITY_CACHE_ENABLED,
			CommerceTierPriceEntryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByCommercePriceEntryId", new String[] { Long.class.getName() });

	/**
	 * Returns all the commerce tier price entries where commercePriceEntryId = &#63;.
	 *
	 * @param commercePriceEntryId the commerce price entry ID
	 * @return the matching commerce tier price entries
	 */
	@Override
	public List<CommerceTierPriceEntry> findByCommercePriceEntryId(
		long commercePriceEntryId) {
		return findByCommercePriceEntryId(commercePriceEntryId,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce tier price entries where commercePriceEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceTierPriceEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param commercePriceEntryId the commerce price entry ID
	 * @param start the lower bound of the range of commerce tier price entries
	 * @param end the upper bound of the range of commerce tier price entries (not inclusive)
	 * @return the range of matching commerce tier price entries
	 */
	@Override
	public List<CommerceTierPriceEntry> findByCommercePriceEntryId(
		long commercePriceEntryId, int start, int end) {
		return findByCommercePriceEntryId(commercePriceEntryId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce tier price entries where commercePriceEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceTierPriceEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param commercePriceEntryId the commerce price entry ID
	 * @param start the lower bound of the range of commerce tier price entries
	 * @param end the upper bound of the range of commerce tier price entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce tier price entries
	 */
	@Override
	public List<CommerceTierPriceEntry> findByCommercePriceEntryId(
		long commercePriceEntryId, int start, int end,
		OrderByComparator<CommerceTierPriceEntry> orderByComparator) {
		return findByCommercePriceEntryId(commercePriceEntryId, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce tier price entries where commercePriceEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceTierPriceEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param commercePriceEntryId the commerce price entry ID
	 * @param start the lower bound of the range of commerce tier price entries
	 * @param end the upper bound of the range of commerce tier price entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching commerce tier price entries
	 */
	@Override
	public List<CommerceTierPriceEntry> findByCommercePriceEntryId(
		long commercePriceEntryId, int start, int end,
		OrderByComparator<CommerceTierPriceEntry> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMMERCEPRICEENTRYID;
			finderArgs = new Object[] { commercePriceEntryId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_COMMERCEPRICEENTRYID;
			finderArgs = new Object[] {
					commercePriceEntryId,
					
					start, end, orderByComparator
				};
		}

		List<CommerceTierPriceEntry> list = null;

		if (retrieveFromCache) {
			list = (List<CommerceTierPriceEntry>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (CommerceTierPriceEntry commerceTierPriceEntry : list) {
					if ((commercePriceEntryId != commerceTierPriceEntry.getCommercePriceEntryId())) {
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

			query.append(_SQL_SELECT_COMMERCETIERPRICEENTRY_WHERE);

			query.append(_FINDER_COLUMN_COMMERCEPRICEENTRYID_COMMERCEPRICEENTRYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(CommerceTierPriceEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(commercePriceEntryId);

				if (!pagination) {
					list = (List<CommerceTierPriceEntry>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<CommerceTierPriceEntry>)QueryUtil.list(q,
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
	 * Returns the first commerce tier price entry in the ordered set where commercePriceEntryId = &#63;.
	 *
	 * @param commercePriceEntryId the commerce price entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce tier price entry
	 * @throws NoSuchTierPriceEntryException if a matching commerce tier price entry could not be found
	 */
	@Override
	public CommerceTierPriceEntry findByCommercePriceEntryId_First(
		long commercePriceEntryId,
		OrderByComparator<CommerceTierPriceEntry> orderByComparator)
		throws NoSuchTierPriceEntryException {
		CommerceTierPriceEntry commerceTierPriceEntry = fetchByCommercePriceEntryId_First(commercePriceEntryId,
				orderByComparator);

		if (commerceTierPriceEntry != null) {
			return commerceTierPriceEntry;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("commercePriceEntryId=");
		msg.append(commercePriceEntryId);

		msg.append("}");

		throw new NoSuchTierPriceEntryException(msg.toString());
	}

	/**
	 * Returns the first commerce tier price entry in the ordered set where commercePriceEntryId = &#63;.
	 *
	 * @param commercePriceEntryId the commerce price entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce tier price entry, or <code>null</code> if a matching commerce tier price entry could not be found
	 */
	@Override
	public CommerceTierPriceEntry fetchByCommercePriceEntryId_First(
		long commercePriceEntryId,
		OrderByComparator<CommerceTierPriceEntry> orderByComparator) {
		List<CommerceTierPriceEntry> list = findByCommercePriceEntryId(commercePriceEntryId,
				0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last commerce tier price entry in the ordered set where commercePriceEntryId = &#63;.
	 *
	 * @param commercePriceEntryId the commerce price entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce tier price entry
	 * @throws NoSuchTierPriceEntryException if a matching commerce tier price entry could not be found
	 */
	@Override
	public CommerceTierPriceEntry findByCommercePriceEntryId_Last(
		long commercePriceEntryId,
		OrderByComparator<CommerceTierPriceEntry> orderByComparator)
		throws NoSuchTierPriceEntryException {
		CommerceTierPriceEntry commerceTierPriceEntry = fetchByCommercePriceEntryId_Last(commercePriceEntryId,
				orderByComparator);

		if (commerceTierPriceEntry != null) {
			return commerceTierPriceEntry;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("commercePriceEntryId=");
		msg.append(commercePriceEntryId);

		msg.append("}");

		throw new NoSuchTierPriceEntryException(msg.toString());
	}

	/**
	 * Returns the last commerce tier price entry in the ordered set where commercePriceEntryId = &#63;.
	 *
	 * @param commercePriceEntryId the commerce price entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce tier price entry, or <code>null</code> if a matching commerce tier price entry could not be found
	 */
	@Override
	public CommerceTierPriceEntry fetchByCommercePriceEntryId_Last(
		long commercePriceEntryId,
		OrderByComparator<CommerceTierPriceEntry> orderByComparator) {
		int count = countByCommercePriceEntryId(commercePriceEntryId);

		if (count == 0) {
			return null;
		}

		List<CommerceTierPriceEntry> list = findByCommercePriceEntryId(commercePriceEntryId,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the commerce tier price entries before and after the current commerce tier price entry in the ordered set where commercePriceEntryId = &#63;.
	 *
	 * @param commerceTierPriceEntryId the primary key of the current commerce tier price entry
	 * @param commercePriceEntryId the commerce price entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce tier price entry
	 * @throws NoSuchTierPriceEntryException if a commerce tier price entry with the primary key could not be found
	 */
	@Override
	public CommerceTierPriceEntry[] findByCommercePriceEntryId_PrevAndNext(
		long commerceTierPriceEntryId, long commercePriceEntryId,
		OrderByComparator<CommerceTierPriceEntry> orderByComparator)
		throws NoSuchTierPriceEntryException {
		CommerceTierPriceEntry commerceTierPriceEntry = findByPrimaryKey(commerceTierPriceEntryId);

		Session session = null;

		try {
			session = openSession();

			CommerceTierPriceEntry[] array = new CommerceTierPriceEntryImpl[3];

			array[0] = getByCommercePriceEntryId_PrevAndNext(session,
					commerceTierPriceEntry, commercePriceEntryId,
					orderByComparator, true);

			array[1] = commerceTierPriceEntry;

			array[2] = getByCommercePriceEntryId_PrevAndNext(session,
					commerceTierPriceEntry, commercePriceEntryId,
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

	protected CommerceTierPriceEntry getByCommercePriceEntryId_PrevAndNext(
		Session session, CommerceTierPriceEntry commerceTierPriceEntry,
		long commercePriceEntryId,
		OrderByComparator<CommerceTierPriceEntry> orderByComparator,
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

		query.append(_SQL_SELECT_COMMERCETIERPRICEENTRY_WHERE);

		query.append(_FINDER_COLUMN_COMMERCEPRICEENTRYID_COMMERCEPRICEENTRYID_2);

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
			query.append(CommerceTierPriceEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(commercePriceEntryId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(commerceTierPriceEntry);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<CommerceTierPriceEntry> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the commerce tier price entries where commercePriceEntryId = &#63; from the database.
	 *
	 * @param commercePriceEntryId the commerce price entry ID
	 */
	@Override
	public void removeByCommercePriceEntryId(long commercePriceEntryId) {
		for (CommerceTierPriceEntry commerceTierPriceEntry : findByCommercePriceEntryId(
				commercePriceEntryId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(commerceTierPriceEntry);
		}
	}

	/**
	 * Returns the number of commerce tier price entries where commercePriceEntryId = &#63;.
	 *
	 * @param commercePriceEntryId the commerce price entry ID
	 * @return the number of matching commerce tier price entries
	 */
	@Override
	public int countByCommercePriceEntryId(long commercePriceEntryId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_COMMERCEPRICEENTRYID;

		Object[] finderArgs = new Object[] { commercePriceEntryId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_COMMERCETIERPRICEENTRY_WHERE);

			query.append(_FINDER_COLUMN_COMMERCEPRICEENTRYID_COMMERCEPRICEENTRYID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(commercePriceEntryId);

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

	private static final String _FINDER_COLUMN_COMMERCEPRICEENTRYID_COMMERCEPRICEENTRYID_2 =
		"commerceTierPriceEntry.commercePriceEntryId = ?";
	public static final FinderPath FINDER_PATH_FETCH_BY_C_M = new FinderPath(CommerceTierPriceEntryModelImpl.ENTITY_CACHE_ENABLED,
			CommerceTierPriceEntryModelImpl.FINDER_CACHE_ENABLED,
			CommerceTierPriceEntryImpl.class, FINDER_CLASS_NAME_ENTITY,
			"fetchByC_M",
			new String[] { Long.class.getName(), Integer.class.getName() },
			CommerceTierPriceEntryModelImpl.COMMERCEPRICEENTRYID_COLUMN_BITMASK |
			CommerceTierPriceEntryModelImpl.MINQUANTITY_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_C_M = new FinderPath(CommerceTierPriceEntryModelImpl.ENTITY_CACHE_ENABLED,
			CommerceTierPriceEntryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_M",
			new String[] { Long.class.getName(), Integer.class.getName() });

	/**
	 * Returns the commerce tier price entry where commercePriceEntryId = &#63; and minQuantity = &#63; or throws a {@link NoSuchTierPriceEntryException} if it could not be found.
	 *
	 * @param commercePriceEntryId the commerce price entry ID
	 * @param minQuantity the min quantity
	 * @return the matching commerce tier price entry
	 * @throws NoSuchTierPriceEntryException if a matching commerce tier price entry could not be found
	 */
	@Override
	public CommerceTierPriceEntry findByC_M(long commercePriceEntryId,
		int minQuantity) throws NoSuchTierPriceEntryException {
		CommerceTierPriceEntry commerceTierPriceEntry = fetchByC_M(commercePriceEntryId,
				minQuantity);

		if (commerceTierPriceEntry == null) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("commercePriceEntryId=");
			msg.append(commercePriceEntryId);

			msg.append(", minQuantity=");
			msg.append(minQuantity);

			msg.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(msg.toString());
			}

			throw new NoSuchTierPriceEntryException(msg.toString());
		}

		return commerceTierPriceEntry;
	}

	/**
	 * Returns the commerce tier price entry where commercePriceEntryId = &#63; and minQuantity = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param commercePriceEntryId the commerce price entry ID
	 * @param minQuantity the min quantity
	 * @return the matching commerce tier price entry, or <code>null</code> if a matching commerce tier price entry could not be found
	 */
	@Override
	public CommerceTierPriceEntry fetchByC_M(long commercePriceEntryId,
		int minQuantity) {
		return fetchByC_M(commercePriceEntryId, minQuantity, true);
	}

	/**
	 * Returns the commerce tier price entry where commercePriceEntryId = &#63; and minQuantity = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param commercePriceEntryId the commerce price entry ID
	 * @param minQuantity the min quantity
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the matching commerce tier price entry, or <code>null</code> if a matching commerce tier price entry could not be found
	 */
	@Override
	public CommerceTierPriceEntry fetchByC_M(long commercePriceEntryId,
		int minQuantity, boolean retrieveFromCache) {
		Object[] finderArgs = new Object[] { commercePriceEntryId, minQuantity };

		Object result = null;

		if (retrieveFromCache) {
			result = finderCache.getResult(FINDER_PATH_FETCH_BY_C_M,
					finderArgs, this);
		}

		if (result instanceof CommerceTierPriceEntry) {
			CommerceTierPriceEntry commerceTierPriceEntry = (CommerceTierPriceEntry)result;

			if ((commercePriceEntryId != commerceTierPriceEntry.getCommercePriceEntryId()) ||
					(minQuantity != commerceTierPriceEntry.getMinQuantity())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_SELECT_COMMERCETIERPRICEENTRY_WHERE);

			query.append(_FINDER_COLUMN_C_M_COMMERCEPRICEENTRYID_2);

			query.append(_FINDER_COLUMN_C_M_MINQUANTITY_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(commercePriceEntryId);

				qPos.add(minQuantity);

				List<CommerceTierPriceEntry> list = q.list();

				if (list.isEmpty()) {
					finderCache.putResult(FINDER_PATH_FETCH_BY_C_M, finderArgs,
						list);
				}
				else {
					CommerceTierPriceEntry commerceTierPriceEntry = list.get(0);

					result = commerceTierPriceEntry;

					cacheResult(commerceTierPriceEntry);

					if ((commerceTierPriceEntry.getCommercePriceEntryId() != commercePriceEntryId) ||
							(commerceTierPriceEntry.getMinQuantity() != minQuantity)) {
						finderCache.putResult(FINDER_PATH_FETCH_BY_C_M,
							finderArgs, commerceTierPriceEntry);
					}
				}
			}
			catch (Exception e) {
				finderCache.removeResult(FINDER_PATH_FETCH_BY_C_M, finderArgs);

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
			return (CommerceTierPriceEntry)result;
		}
	}

	/**
	 * Removes the commerce tier price entry where commercePriceEntryId = &#63; and minQuantity = &#63; from the database.
	 *
	 * @param commercePriceEntryId the commerce price entry ID
	 * @param minQuantity the min quantity
	 * @return the commerce tier price entry that was removed
	 */
	@Override
	public CommerceTierPriceEntry removeByC_M(long commercePriceEntryId,
		int minQuantity) throws NoSuchTierPriceEntryException {
		CommerceTierPriceEntry commerceTierPriceEntry = findByC_M(commercePriceEntryId,
				minQuantity);

		return remove(commerceTierPriceEntry);
	}

	/**
	 * Returns the number of commerce tier price entries where commercePriceEntryId = &#63; and minQuantity = &#63;.
	 *
	 * @param commercePriceEntryId the commerce price entry ID
	 * @param minQuantity the min quantity
	 * @return the number of matching commerce tier price entries
	 */
	@Override
	public int countByC_M(long commercePriceEntryId, int minQuantity) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_C_M;

		Object[] finderArgs = new Object[] { commercePriceEntryId, minQuantity };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_COMMERCETIERPRICEENTRY_WHERE);

			query.append(_FINDER_COLUMN_C_M_COMMERCEPRICEENTRYID_2);

			query.append(_FINDER_COLUMN_C_M_MINQUANTITY_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(commercePriceEntryId);

				qPos.add(minQuantity);

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

	private static final String _FINDER_COLUMN_C_M_COMMERCEPRICEENTRYID_2 = "commerceTierPriceEntry.commercePriceEntryId = ? AND ";
	private static final String _FINDER_COLUMN_C_M_MINQUANTITY_2 = "commerceTierPriceEntry.minQuantity = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_C_M2 = new FinderPath(CommerceTierPriceEntryModelImpl.ENTITY_CACHE_ENABLED,
			CommerceTierPriceEntryModelImpl.FINDER_CACHE_ENABLED,
			CommerceTierPriceEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByC_M2",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_COUNT_BY_C_M2 = new FinderPath(CommerceTierPriceEntryModelImpl.ENTITY_CACHE_ENABLED,
			CommerceTierPriceEntryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "countByC_M2",
			new String[] { Long.class.getName(), Integer.class.getName() });

	/**
	 * Returns all the commerce tier price entries where commercePriceEntryId = &#63; and minQuantity &le; &#63;.
	 *
	 * @param commercePriceEntryId the commerce price entry ID
	 * @param minQuantity the min quantity
	 * @return the matching commerce tier price entries
	 */
	@Override
	public List<CommerceTierPriceEntry> findByC_M2(long commercePriceEntryId,
		int minQuantity) {
		return findByC_M2(commercePriceEntryId, minQuantity, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce tier price entries where commercePriceEntryId = &#63; and minQuantity &le; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceTierPriceEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param commercePriceEntryId the commerce price entry ID
	 * @param minQuantity the min quantity
	 * @param start the lower bound of the range of commerce tier price entries
	 * @param end the upper bound of the range of commerce tier price entries (not inclusive)
	 * @return the range of matching commerce tier price entries
	 */
	@Override
	public List<CommerceTierPriceEntry> findByC_M2(long commercePriceEntryId,
		int minQuantity, int start, int end) {
		return findByC_M2(commercePriceEntryId, minQuantity, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce tier price entries where commercePriceEntryId = &#63; and minQuantity &le; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceTierPriceEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param commercePriceEntryId the commerce price entry ID
	 * @param minQuantity the min quantity
	 * @param start the lower bound of the range of commerce tier price entries
	 * @param end the upper bound of the range of commerce tier price entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce tier price entries
	 */
	@Override
	public List<CommerceTierPriceEntry> findByC_M2(long commercePriceEntryId,
		int minQuantity, int start, int end,
		OrderByComparator<CommerceTierPriceEntry> orderByComparator) {
		return findByC_M2(commercePriceEntryId, minQuantity, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce tier price entries where commercePriceEntryId = &#63; and minQuantity &le; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceTierPriceEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param commercePriceEntryId the commerce price entry ID
	 * @param minQuantity the min quantity
	 * @param start the lower bound of the range of commerce tier price entries
	 * @param end the upper bound of the range of commerce tier price entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching commerce tier price entries
	 */
	@Override
	public List<CommerceTierPriceEntry> findByC_M2(long commercePriceEntryId,
		int minQuantity, int start, int end,
		OrderByComparator<CommerceTierPriceEntry> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_C_M2;
		finderArgs = new Object[] {
				commercePriceEntryId, minQuantity,
				
				start, end, orderByComparator
			};

		List<CommerceTierPriceEntry> list = null;

		if (retrieveFromCache) {
			list = (List<CommerceTierPriceEntry>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (CommerceTierPriceEntry commerceTierPriceEntry : list) {
					if ((commercePriceEntryId != commerceTierPriceEntry.getCommercePriceEntryId()) ||
							(minQuantity < commerceTierPriceEntry.getMinQuantity())) {
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

			query.append(_SQL_SELECT_COMMERCETIERPRICEENTRY_WHERE);

			query.append(_FINDER_COLUMN_C_M2_COMMERCEPRICEENTRYID_2);

			query.append(_FINDER_COLUMN_C_M2_MINQUANTITY_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(CommerceTierPriceEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(commercePriceEntryId);

				qPos.add(minQuantity);

				if (!pagination) {
					list = (List<CommerceTierPriceEntry>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<CommerceTierPriceEntry>)QueryUtil.list(q,
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
	 * Returns the first commerce tier price entry in the ordered set where commercePriceEntryId = &#63; and minQuantity &le; &#63;.
	 *
	 * @param commercePriceEntryId the commerce price entry ID
	 * @param minQuantity the min quantity
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce tier price entry
	 * @throws NoSuchTierPriceEntryException if a matching commerce tier price entry could not be found
	 */
	@Override
	public CommerceTierPriceEntry findByC_M2_First(long commercePriceEntryId,
		int minQuantity,
		OrderByComparator<CommerceTierPriceEntry> orderByComparator)
		throws NoSuchTierPriceEntryException {
		CommerceTierPriceEntry commerceTierPriceEntry = fetchByC_M2_First(commercePriceEntryId,
				minQuantity, orderByComparator);

		if (commerceTierPriceEntry != null) {
			return commerceTierPriceEntry;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("commercePriceEntryId=");
		msg.append(commercePriceEntryId);

		msg.append(", minQuantity=");
		msg.append(minQuantity);

		msg.append("}");

		throw new NoSuchTierPriceEntryException(msg.toString());
	}

	/**
	 * Returns the first commerce tier price entry in the ordered set where commercePriceEntryId = &#63; and minQuantity &le; &#63;.
	 *
	 * @param commercePriceEntryId the commerce price entry ID
	 * @param minQuantity the min quantity
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce tier price entry, or <code>null</code> if a matching commerce tier price entry could not be found
	 */
	@Override
	public CommerceTierPriceEntry fetchByC_M2_First(long commercePriceEntryId,
		int minQuantity,
		OrderByComparator<CommerceTierPriceEntry> orderByComparator) {
		List<CommerceTierPriceEntry> list = findByC_M2(commercePriceEntryId,
				minQuantity, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last commerce tier price entry in the ordered set where commercePriceEntryId = &#63; and minQuantity &le; &#63;.
	 *
	 * @param commercePriceEntryId the commerce price entry ID
	 * @param minQuantity the min quantity
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce tier price entry
	 * @throws NoSuchTierPriceEntryException if a matching commerce tier price entry could not be found
	 */
	@Override
	public CommerceTierPriceEntry findByC_M2_Last(long commercePriceEntryId,
		int minQuantity,
		OrderByComparator<CommerceTierPriceEntry> orderByComparator)
		throws NoSuchTierPriceEntryException {
		CommerceTierPriceEntry commerceTierPriceEntry = fetchByC_M2_Last(commercePriceEntryId,
				minQuantity, orderByComparator);

		if (commerceTierPriceEntry != null) {
			return commerceTierPriceEntry;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("commercePriceEntryId=");
		msg.append(commercePriceEntryId);

		msg.append(", minQuantity=");
		msg.append(minQuantity);

		msg.append("}");

		throw new NoSuchTierPriceEntryException(msg.toString());
	}

	/**
	 * Returns the last commerce tier price entry in the ordered set where commercePriceEntryId = &#63; and minQuantity &le; &#63;.
	 *
	 * @param commercePriceEntryId the commerce price entry ID
	 * @param minQuantity the min quantity
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce tier price entry, or <code>null</code> if a matching commerce tier price entry could not be found
	 */
	@Override
	public CommerceTierPriceEntry fetchByC_M2_Last(long commercePriceEntryId,
		int minQuantity,
		OrderByComparator<CommerceTierPriceEntry> orderByComparator) {
		int count = countByC_M2(commercePriceEntryId, minQuantity);

		if (count == 0) {
			return null;
		}

		List<CommerceTierPriceEntry> list = findByC_M2(commercePriceEntryId,
				minQuantity, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the commerce tier price entries before and after the current commerce tier price entry in the ordered set where commercePriceEntryId = &#63; and minQuantity &le; &#63;.
	 *
	 * @param commerceTierPriceEntryId the primary key of the current commerce tier price entry
	 * @param commercePriceEntryId the commerce price entry ID
	 * @param minQuantity the min quantity
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce tier price entry
	 * @throws NoSuchTierPriceEntryException if a commerce tier price entry with the primary key could not be found
	 */
	@Override
	public CommerceTierPriceEntry[] findByC_M2_PrevAndNext(
		long commerceTierPriceEntryId, long commercePriceEntryId,
		int minQuantity,
		OrderByComparator<CommerceTierPriceEntry> orderByComparator)
		throws NoSuchTierPriceEntryException {
		CommerceTierPriceEntry commerceTierPriceEntry = findByPrimaryKey(commerceTierPriceEntryId);

		Session session = null;

		try {
			session = openSession();

			CommerceTierPriceEntry[] array = new CommerceTierPriceEntryImpl[3];

			array[0] = getByC_M2_PrevAndNext(session, commerceTierPriceEntry,
					commercePriceEntryId, minQuantity, orderByComparator, true);

			array[1] = commerceTierPriceEntry;

			array[2] = getByC_M2_PrevAndNext(session, commerceTierPriceEntry,
					commercePriceEntryId, minQuantity, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected CommerceTierPriceEntry getByC_M2_PrevAndNext(Session session,
		CommerceTierPriceEntry commerceTierPriceEntry,
		long commercePriceEntryId, int minQuantity,
		OrderByComparator<CommerceTierPriceEntry> orderByComparator,
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

		query.append(_SQL_SELECT_COMMERCETIERPRICEENTRY_WHERE);

		query.append(_FINDER_COLUMN_C_M2_COMMERCEPRICEENTRYID_2);

		query.append(_FINDER_COLUMN_C_M2_MINQUANTITY_2);

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
			query.append(CommerceTierPriceEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(commercePriceEntryId);

		qPos.add(minQuantity);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(commerceTierPriceEntry);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<CommerceTierPriceEntry> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the commerce tier price entries where commercePriceEntryId = &#63; and minQuantity &le; &#63; from the database.
	 *
	 * @param commercePriceEntryId the commerce price entry ID
	 * @param minQuantity the min quantity
	 */
	@Override
	public void removeByC_M2(long commercePriceEntryId, int minQuantity) {
		for (CommerceTierPriceEntry commerceTierPriceEntry : findByC_M2(
				commercePriceEntryId, minQuantity, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS, null)) {
			remove(commerceTierPriceEntry);
		}
	}

	/**
	 * Returns the number of commerce tier price entries where commercePriceEntryId = &#63; and minQuantity &le; &#63;.
	 *
	 * @param commercePriceEntryId the commerce price entry ID
	 * @param minQuantity the min quantity
	 * @return the number of matching commerce tier price entries
	 */
	@Override
	public int countByC_M2(long commercePriceEntryId, int minQuantity) {
		FinderPath finderPath = FINDER_PATH_WITH_PAGINATION_COUNT_BY_C_M2;

		Object[] finderArgs = new Object[] { commercePriceEntryId, minQuantity };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_COMMERCETIERPRICEENTRY_WHERE);

			query.append(_FINDER_COLUMN_C_M2_COMMERCEPRICEENTRYID_2);

			query.append(_FINDER_COLUMN_C_M2_MINQUANTITY_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(commercePriceEntryId);

				qPos.add(minQuantity);

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

	private static final String _FINDER_COLUMN_C_M2_COMMERCEPRICEENTRYID_2 = "commerceTierPriceEntry.commercePriceEntryId = ? AND ";
	private static final String _FINDER_COLUMN_C_M2_MINQUANTITY_2 = "commerceTierPriceEntry.minQuantity <= ?";

	public CommerceTierPriceEntryPersistenceImpl() {
		setModelClass(CommerceTierPriceEntry.class);

		try {
			Field field = BasePersistenceImpl.class.getDeclaredField(
					"_dbColumnNames");

			field.setAccessible(true);

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
	 * Caches the commerce tier price entry in the entity cache if it is enabled.
	 *
	 * @param commerceTierPriceEntry the commerce tier price entry
	 */
	@Override
	public void cacheResult(CommerceTierPriceEntry commerceTierPriceEntry) {
		entityCache.putResult(CommerceTierPriceEntryModelImpl.ENTITY_CACHE_ENABLED,
			CommerceTierPriceEntryImpl.class,
			commerceTierPriceEntry.getPrimaryKey(), commerceTierPriceEntry);

		finderCache.putResult(FINDER_PATH_FETCH_BY_UUID_G,
			new Object[] {
				commerceTierPriceEntry.getUuid(),
				commerceTierPriceEntry.getGroupId()
			}, commerceTierPriceEntry);

		finderCache.putResult(FINDER_PATH_FETCH_BY_C_M,
			new Object[] {
				commerceTierPriceEntry.getCommercePriceEntryId(),
				commerceTierPriceEntry.getMinQuantity()
			}, commerceTierPriceEntry);

		commerceTierPriceEntry.resetOriginalValues();
	}

	/**
	 * Caches the commerce tier price entries in the entity cache if it is enabled.
	 *
	 * @param commerceTierPriceEntries the commerce tier price entries
	 */
	@Override
	public void cacheResult(
		List<CommerceTierPriceEntry> commerceTierPriceEntries) {
		for (CommerceTierPriceEntry commerceTierPriceEntry : commerceTierPriceEntries) {
			if (entityCache.getResult(
						CommerceTierPriceEntryModelImpl.ENTITY_CACHE_ENABLED,
						CommerceTierPriceEntryImpl.class,
						commerceTierPriceEntry.getPrimaryKey()) == null) {
				cacheResult(commerceTierPriceEntry);
			}
			else {
				commerceTierPriceEntry.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all commerce tier price entries.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(CommerceTierPriceEntryImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the commerce tier price entry.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(CommerceTierPriceEntry commerceTierPriceEntry) {
		entityCache.removeResult(CommerceTierPriceEntryModelImpl.ENTITY_CACHE_ENABLED,
			CommerceTierPriceEntryImpl.class,
			commerceTierPriceEntry.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache((CommerceTierPriceEntryModelImpl)commerceTierPriceEntry,
			true);
	}

	@Override
	public void clearCache(
		List<CommerceTierPriceEntry> commerceTierPriceEntries) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (CommerceTierPriceEntry commerceTierPriceEntry : commerceTierPriceEntries) {
			entityCache.removeResult(CommerceTierPriceEntryModelImpl.ENTITY_CACHE_ENABLED,
				CommerceTierPriceEntryImpl.class,
				commerceTierPriceEntry.getPrimaryKey());

			clearUniqueFindersCache((CommerceTierPriceEntryModelImpl)commerceTierPriceEntry,
				true);
		}
	}

	protected void cacheUniqueFindersCache(
		CommerceTierPriceEntryModelImpl commerceTierPriceEntryModelImpl) {
		Object[] args = new Object[] {
				commerceTierPriceEntryModelImpl.getUuid(),
				commerceTierPriceEntryModelImpl.getGroupId()
			};

		finderCache.putResult(FINDER_PATH_COUNT_BY_UUID_G, args,
			Long.valueOf(1), false);
		finderCache.putResult(FINDER_PATH_FETCH_BY_UUID_G, args,
			commerceTierPriceEntryModelImpl, false);

		args = new Object[] {
				commerceTierPriceEntryModelImpl.getCommercePriceEntryId(),
				commerceTierPriceEntryModelImpl.getMinQuantity()
			};

		finderCache.putResult(FINDER_PATH_COUNT_BY_C_M, args, Long.valueOf(1),
			false);
		finderCache.putResult(FINDER_PATH_FETCH_BY_C_M, args,
			commerceTierPriceEntryModelImpl, false);
	}

	protected void clearUniqueFindersCache(
		CommerceTierPriceEntryModelImpl commerceTierPriceEntryModelImpl,
		boolean clearCurrent) {
		if (clearCurrent) {
			Object[] args = new Object[] {
					commerceTierPriceEntryModelImpl.getUuid(),
					commerceTierPriceEntryModelImpl.getGroupId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID_G, args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_UUID_G, args);
		}

		if ((commerceTierPriceEntryModelImpl.getColumnBitmask() &
				FINDER_PATH_FETCH_BY_UUID_G.getColumnBitmask()) != 0) {
			Object[] args = new Object[] {
					commerceTierPriceEntryModelImpl.getOriginalUuid(),
					commerceTierPriceEntryModelImpl.getOriginalGroupId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID_G, args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_UUID_G, args);
		}

		if (clearCurrent) {
			Object[] args = new Object[] {
					commerceTierPriceEntryModelImpl.getCommercePriceEntryId(),
					commerceTierPriceEntryModelImpl.getMinQuantity()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_C_M, args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_C_M, args);
		}

		if ((commerceTierPriceEntryModelImpl.getColumnBitmask() &
				FINDER_PATH_FETCH_BY_C_M.getColumnBitmask()) != 0) {
			Object[] args = new Object[] {
					commerceTierPriceEntryModelImpl.getOriginalCommercePriceEntryId(),
					commerceTierPriceEntryModelImpl.getOriginalMinQuantity()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_C_M, args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_C_M, args);
		}
	}

	/**
	 * Creates a new commerce tier price entry with the primary key. Does not add the commerce tier price entry to the database.
	 *
	 * @param commerceTierPriceEntryId the primary key for the new commerce tier price entry
	 * @return the new commerce tier price entry
	 */
	@Override
	public CommerceTierPriceEntry create(long commerceTierPriceEntryId) {
		CommerceTierPriceEntry commerceTierPriceEntry = new CommerceTierPriceEntryImpl();

		commerceTierPriceEntry.setNew(true);
		commerceTierPriceEntry.setPrimaryKey(commerceTierPriceEntryId);

		String uuid = PortalUUIDUtil.generate();

		commerceTierPriceEntry.setUuid(uuid);

		commerceTierPriceEntry.setCompanyId(companyProvider.getCompanyId());

		return commerceTierPriceEntry;
	}

	/**
	 * Removes the commerce tier price entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param commerceTierPriceEntryId the primary key of the commerce tier price entry
	 * @return the commerce tier price entry that was removed
	 * @throws NoSuchTierPriceEntryException if a commerce tier price entry with the primary key could not be found
	 */
	@Override
	public CommerceTierPriceEntry remove(long commerceTierPriceEntryId)
		throws NoSuchTierPriceEntryException {
		return remove((Serializable)commerceTierPriceEntryId);
	}

	/**
	 * Removes the commerce tier price entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the commerce tier price entry
	 * @return the commerce tier price entry that was removed
	 * @throws NoSuchTierPriceEntryException if a commerce tier price entry with the primary key could not be found
	 */
	@Override
	public CommerceTierPriceEntry remove(Serializable primaryKey)
		throws NoSuchTierPriceEntryException {
		Session session = null;

		try {
			session = openSession();

			CommerceTierPriceEntry commerceTierPriceEntry = (CommerceTierPriceEntry)session.get(CommerceTierPriceEntryImpl.class,
					primaryKey);

			if (commerceTierPriceEntry == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchTierPriceEntryException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(commerceTierPriceEntry);
		}
		catch (NoSuchTierPriceEntryException nsee) {
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
	protected CommerceTierPriceEntry removeImpl(
		CommerceTierPriceEntry commerceTierPriceEntry) {
		Session session = null;

		try {
			session = openSession();

			if (!session.contains(commerceTierPriceEntry)) {
				commerceTierPriceEntry = (CommerceTierPriceEntry)session.get(CommerceTierPriceEntryImpl.class,
						commerceTierPriceEntry.getPrimaryKeyObj());
			}

			if (commerceTierPriceEntry != null) {
				session.delete(commerceTierPriceEntry);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (commerceTierPriceEntry != null) {
			clearCache(commerceTierPriceEntry);
		}

		return commerceTierPriceEntry;
	}

	@Override
	public CommerceTierPriceEntry updateImpl(
		CommerceTierPriceEntry commerceTierPriceEntry) {
		boolean isNew = commerceTierPriceEntry.isNew();

		if (!(commerceTierPriceEntry instanceof CommerceTierPriceEntryModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(commerceTierPriceEntry.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(commerceTierPriceEntry);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in commerceTierPriceEntry proxy " +
					invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom CommerceTierPriceEntry implementation " +
				commerceTierPriceEntry.getClass());
		}

		CommerceTierPriceEntryModelImpl commerceTierPriceEntryModelImpl = (CommerceTierPriceEntryModelImpl)commerceTierPriceEntry;

		if (Validator.isNull(commerceTierPriceEntry.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			commerceTierPriceEntry.setUuid(uuid);
		}

		ServiceContext serviceContext = ServiceContextThreadLocal.getServiceContext();

		Date now = new Date();

		if (isNew && (commerceTierPriceEntry.getCreateDate() == null)) {
			if (serviceContext == null) {
				commerceTierPriceEntry.setCreateDate(now);
			}
			else {
				commerceTierPriceEntry.setCreateDate(serviceContext.getCreateDate(
						now));
			}
		}

		if (!commerceTierPriceEntryModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				commerceTierPriceEntry.setModifiedDate(now);
			}
			else {
				commerceTierPriceEntry.setModifiedDate(serviceContext.getModifiedDate(
						now));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (commerceTierPriceEntry.isNew()) {
				session.save(commerceTierPriceEntry);

				commerceTierPriceEntry.setNew(false);
			}
			else {
				commerceTierPriceEntry = (CommerceTierPriceEntry)session.merge(commerceTierPriceEntry);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (!CommerceTierPriceEntryModelImpl.COLUMN_BITMASK_ENABLED) {
			finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}
		else
		 if (isNew) {
			Object[] args = new Object[] {
					commerceTierPriceEntryModelImpl.getUuid()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID, args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID,
				args);

			args = new Object[] {
					commerceTierPriceEntryModelImpl.getUuid(),
					commerceTierPriceEntryModelImpl.getCompanyId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID_C, args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C,
				args);

			args = new Object[] { commerceTierPriceEntryModelImpl.getGroupId() };

			finderCache.removeResult(FINDER_PATH_COUNT_BY_GROUPID, args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID,
				args);

			args = new Object[] { commerceTierPriceEntryModelImpl.getCompanyId() };

			finderCache.removeResult(FINDER_PATH_COUNT_BY_COMPANYID, args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID,
				args);

			args = new Object[] {
					commerceTierPriceEntryModelImpl.getCommercePriceEntryId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_COMMERCEPRICEENTRYID,
				args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMMERCEPRICEENTRYID,
				args);

			finderCache.removeResult(FINDER_PATH_COUNT_ALL, FINDER_ARGS_EMPTY);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL,
				FINDER_ARGS_EMPTY);
		}

		else {
			if ((commerceTierPriceEntryModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						commerceTierPriceEntryModelImpl.getOriginalUuid()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID,
					args);

				args = new Object[] { commerceTierPriceEntryModelImpl.getUuid() };

				finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID,
					args);
			}

			if ((commerceTierPriceEntryModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						commerceTierPriceEntryModelImpl.getOriginalUuid(),
						commerceTierPriceEntryModelImpl.getOriginalCompanyId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID_C, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C,
					args);

				args = new Object[] {
						commerceTierPriceEntryModelImpl.getUuid(),
						commerceTierPriceEntryModelImpl.getCompanyId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID_C, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C,
					args);
			}

			if ((commerceTierPriceEntryModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						commerceTierPriceEntryModelImpl.getOriginalGroupId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_GROUPID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID,
					args);

				args = new Object[] { commerceTierPriceEntryModelImpl.getGroupId() };

				finderCache.removeResult(FINDER_PATH_COUNT_BY_GROUPID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID,
					args);
			}

			if ((commerceTierPriceEntryModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						commerceTierPriceEntryModelImpl.getOriginalCompanyId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_COMPANYID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID,
					args);

				args = new Object[] {
						commerceTierPriceEntryModelImpl.getCompanyId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_COMPANYID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID,
					args);
			}

			if ((commerceTierPriceEntryModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMMERCEPRICEENTRYID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						commerceTierPriceEntryModelImpl.getOriginalCommercePriceEntryId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_COMMERCEPRICEENTRYID,
					args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMMERCEPRICEENTRYID,
					args);

				args = new Object[] {
						commerceTierPriceEntryModelImpl.getCommercePriceEntryId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_COMMERCEPRICEENTRYID,
					args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMMERCEPRICEENTRYID,
					args);
			}
		}

		entityCache.putResult(CommerceTierPriceEntryModelImpl.ENTITY_CACHE_ENABLED,
			CommerceTierPriceEntryImpl.class,
			commerceTierPriceEntry.getPrimaryKey(), commerceTierPriceEntry,
			false);

		clearUniqueFindersCache(commerceTierPriceEntryModelImpl, false);
		cacheUniqueFindersCache(commerceTierPriceEntryModelImpl);

		commerceTierPriceEntry.resetOriginalValues();

		return commerceTierPriceEntry;
	}

	/**
	 * Returns the commerce tier price entry with the primary key or throws a {@link com.liferay.portal.kernel.exception.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the commerce tier price entry
	 * @return the commerce tier price entry
	 * @throws NoSuchTierPriceEntryException if a commerce tier price entry with the primary key could not be found
	 */
	@Override
	public CommerceTierPriceEntry findByPrimaryKey(Serializable primaryKey)
		throws NoSuchTierPriceEntryException {
		CommerceTierPriceEntry commerceTierPriceEntry = fetchByPrimaryKey(primaryKey);

		if (commerceTierPriceEntry == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchTierPriceEntryException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				primaryKey);
		}

		return commerceTierPriceEntry;
	}

	/**
	 * Returns the commerce tier price entry with the primary key or throws a {@link NoSuchTierPriceEntryException} if it could not be found.
	 *
	 * @param commerceTierPriceEntryId the primary key of the commerce tier price entry
	 * @return the commerce tier price entry
	 * @throws NoSuchTierPriceEntryException if a commerce tier price entry with the primary key could not be found
	 */
	@Override
	public CommerceTierPriceEntry findByPrimaryKey(
		long commerceTierPriceEntryId) throws NoSuchTierPriceEntryException {
		return findByPrimaryKey((Serializable)commerceTierPriceEntryId);
	}

	/**
	 * Returns the commerce tier price entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the commerce tier price entry
	 * @return the commerce tier price entry, or <code>null</code> if a commerce tier price entry with the primary key could not be found
	 */
	@Override
	public CommerceTierPriceEntry fetchByPrimaryKey(Serializable primaryKey) {
		Serializable serializable = entityCache.getResult(CommerceTierPriceEntryModelImpl.ENTITY_CACHE_ENABLED,
				CommerceTierPriceEntryImpl.class, primaryKey);

		if (serializable == nullModel) {
			return null;
		}

		CommerceTierPriceEntry commerceTierPriceEntry = (CommerceTierPriceEntry)serializable;

		if (commerceTierPriceEntry == null) {
			Session session = null;

			try {
				session = openSession();

				commerceTierPriceEntry = (CommerceTierPriceEntry)session.get(CommerceTierPriceEntryImpl.class,
						primaryKey);

				if (commerceTierPriceEntry != null) {
					cacheResult(commerceTierPriceEntry);
				}
				else {
					entityCache.putResult(CommerceTierPriceEntryModelImpl.ENTITY_CACHE_ENABLED,
						CommerceTierPriceEntryImpl.class, primaryKey, nullModel);
				}
			}
			catch (Exception e) {
				entityCache.removeResult(CommerceTierPriceEntryModelImpl.ENTITY_CACHE_ENABLED,
					CommerceTierPriceEntryImpl.class, primaryKey);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return commerceTierPriceEntry;
	}

	/**
	 * Returns the commerce tier price entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param commerceTierPriceEntryId the primary key of the commerce tier price entry
	 * @return the commerce tier price entry, or <code>null</code> if a commerce tier price entry with the primary key could not be found
	 */
	@Override
	public CommerceTierPriceEntry fetchByPrimaryKey(
		long commerceTierPriceEntryId) {
		return fetchByPrimaryKey((Serializable)commerceTierPriceEntryId);
	}

	@Override
	public Map<Serializable, CommerceTierPriceEntry> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {
		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, CommerceTierPriceEntry> map = new HashMap<Serializable, CommerceTierPriceEntry>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			CommerceTierPriceEntry commerceTierPriceEntry = fetchByPrimaryKey(primaryKey);

			if (commerceTierPriceEntry != null) {
				map.put(primaryKey, commerceTierPriceEntry);
			}

			return map;
		}

		Set<Serializable> uncachedPrimaryKeys = null;

		for (Serializable primaryKey : primaryKeys) {
			Serializable serializable = entityCache.getResult(CommerceTierPriceEntryModelImpl.ENTITY_CACHE_ENABLED,
					CommerceTierPriceEntryImpl.class, primaryKey);

			if (serializable != nullModel) {
				if (serializable == null) {
					if (uncachedPrimaryKeys == null) {
						uncachedPrimaryKeys = new HashSet<Serializable>();
					}

					uncachedPrimaryKeys.add(primaryKey);
				}
				else {
					map.put(primaryKey, (CommerceTierPriceEntry)serializable);
				}
			}
		}

		if (uncachedPrimaryKeys == null) {
			return map;
		}

		StringBundler query = new StringBundler((uncachedPrimaryKeys.size() * 2) +
				1);

		query.append(_SQL_SELECT_COMMERCETIERPRICEENTRY_WHERE_PKS_IN);

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

			for (CommerceTierPriceEntry commerceTierPriceEntry : (List<CommerceTierPriceEntry>)q.list()) {
				map.put(commerceTierPriceEntry.getPrimaryKeyObj(),
					commerceTierPriceEntry);

				cacheResult(commerceTierPriceEntry);

				uncachedPrimaryKeys.remove(commerceTierPriceEntry.getPrimaryKeyObj());
			}

			for (Serializable primaryKey : uncachedPrimaryKeys) {
				entityCache.putResult(CommerceTierPriceEntryModelImpl.ENTITY_CACHE_ENABLED,
					CommerceTierPriceEntryImpl.class, primaryKey, nullModel);
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
	 * Returns all the commerce tier price entries.
	 *
	 * @return the commerce tier price entries
	 */
	@Override
	public List<CommerceTierPriceEntry> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce tier price entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceTierPriceEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce tier price entries
	 * @param end the upper bound of the range of commerce tier price entries (not inclusive)
	 * @return the range of commerce tier price entries
	 */
	@Override
	public List<CommerceTierPriceEntry> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce tier price entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceTierPriceEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce tier price entries
	 * @param end the upper bound of the range of commerce tier price entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of commerce tier price entries
	 */
	@Override
	public List<CommerceTierPriceEntry> findAll(int start, int end,
		OrderByComparator<CommerceTierPriceEntry> orderByComparator) {
		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce tier price entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceTierPriceEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce tier price entries
	 * @param end the upper bound of the range of commerce tier price entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of commerce tier price entries
	 */
	@Override
	public List<CommerceTierPriceEntry> findAll(int start, int end,
		OrderByComparator<CommerceTierPriceEntry> orderByComparator,
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

		List<CommerceTierPriceEntry> list = null;

		if (retrieveFromCache) {
			list = (List<CommerceTierPriceEntry>)finderCache.getResult(finderPath,
					finderArgs, this);
		}

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 2));

				query.append(_SQL_SELECT_COMMERCETIERPRICEENTRY);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_COMMERCETIERPRICEENTRY;

				if (pagination) {
					sql = sql.concat(CommerceTierPriceEntryModelImpl.ORDER_BY_JPQL);
				}
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (!pagination) {
					list = (List<CommerceTierPriceEntry>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<CommerceTierPriceEntry>)QueryUtil.list(q,
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
	 * Removes all the commerce tier price entries from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (CommerceTierPriceEntry commerceTierPriceEntry : findAll()) {
			remove(commerceTierPriceEntry);
		}
	}

	/**
	 * Returns the number of commerce tier price entries.
	 *
	 * @return the number of commerce tier price entries
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_COMMERCETIERPRICEENTRY);

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
		return CommerceTierPriceEntryModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the commerce tier price entry persistence.
	 */
	public void afterPropertiesSet() {
	}

	public void destroy() {
		entityCache.removeCache(CommerceTierPriceEntryImpl.class.getName());
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
	private static final String _SQL_SELECT_COMMERCETIERPRICEENTRY = "SELECT commerceTierPriceEntry FROM CommerceTierPriceEntry commerceTierPriceEntry";
	private static final String _SQL_SELECT_COMMERCETIERPRICEENTRY_WHERE_PKS_IN = "SELECT commerceTierPriceEntry FROM CommerceTierPriceEntry commerceTierPriceEntry WHERE commerceTierPriceEntryId IN (";
	private static final String _SQL_SELECT_COMMERCETIERPRICEENTRY_WHERE = "SELECT commerceTierPriceEntry FROM CommerceTierPriceEntry commerceTierPriceEntry WHERE ";
	private static final String _SQL_COUNT_COMMERCETIERPRICEENTRY = "SELECT COUNT(commerceTierPriceEntry) FROM CommerceTierPriceEntry commerceTierPriceEntry";
	private static final String _SQL_COUNT_COMMERCETIERPRICEENTRY_WHERE = "SELECT COUNT(commerceTierPriceEntry) FROM CommerceTierPriceEntry commerceTierPriceEntry WHERE ";
	private static final String _ORDER_BY_ENTITY_ALIAS = "commerceTierPriceEntry.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No CommerceTierPriceEntry exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No CommerceTierPriceEntry exists with the key {";
	private static final Log _log = LogFactoryUtil.getLog(CommerceTierPriceEntryPersistenceImpl.class);
	private static final Set<String> _badColumnNames = SetUtil.fromArray(new String[] {
				"uuid"
			});
}