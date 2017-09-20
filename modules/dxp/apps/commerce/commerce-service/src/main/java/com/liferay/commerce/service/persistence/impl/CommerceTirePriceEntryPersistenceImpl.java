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

import com.liferay.commerce.exception.NoSuchTirePriceEntryException;
import com.liferay.commerce.model.CommerceTirePriceEntry;
import com.liferay.commerce.model.impl.CommerceTirePriceEntryImpl;
import com.liferay.commerce.model.impl.CommerceTirePriceEntryModelImpl;
import com.liferay.commerce.service.persistence.CommerceTirePriceEntryPersistence;

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
 * The persistence implementation for the commerce tire price entry service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Alessio Antonio Rendina
 * @see CommerceTirePriceEntryPersistence
 * @see com.liferay.commerce.service.persistence.CommerceTirePriceEntryUtil
 * @generated
 */
@ProviderType
public class CommerceTirePriceEntryPersistenceImpl extends BasePersistenceImpl<CommerceTirePriceEntry>
	implements CommerceTirePriceEntryPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link CommerceTirePriceEntryUtil} to access the commerce tire price entry persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = CommerceTirePriceEntryImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(CommerceTirePriceEntryModelImpl.ENTITY_CACHE_ENABLED,
			CommerceTirePriceEntryModelImpl.FINDER_CACHE_ENABLED,
			CommerceTirePriceEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(CommerceTirePriceEntryModelImpl.ENTITY_CACHE_ENABLED,
			CommerceTirePriceEntryModelImpl.FINDER_CACHE_ENABLED,
			CommerceTirePriceEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(CommerceTirePriceEntryModelImpl.ENTITY_CACHE_ENABLED,
			CommerceTirePriceEntryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_UUID = new FinderPath(CommerceTirePriceEntryModelImpl.ENTITY_CACHE_ENABLED,
			CommerceTirePriceEntryModelImpl.FINDER_CACHE_ENABLED,
			CommerceTirePriceEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUuid",
			new String[] {
				String.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID = new FinderPath(CommerceTirePriceEntryModelImpl.ENTITY_CACHE_ENABLED,
			CommerceTirePriceEntryModelImpl.FINDER_CACHE_ENABLED,
			CommerceTirePriceEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid",
			new String[] { String.class.getName() },
			CommerceTirePriceEntryModelImpl.UUID_COLUMN_BITMASK |
			CommerceTirePriceEntryModelImpl.CREATEDATE_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID = new FinderPath(CommerceTirePriceEntryModelImpl.ENTITY_CACHE_ENABLED,
			CommerceTirePriceEntryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUuid",
			new String[] { String.class.getName() });

	/**
	 * Returns all the commerce tire price entries where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching commerce tire price entries
	 */
	@Override
	public List<CommerceTirePriceEntry> findByUuid(String uuid) {
		return findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce tire price entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceTirePriceEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of commerce tire price entries
	 * @param end the upper bound of the range of commerce tire price entries (not inclusive)
	 * @return the range of matching commerce tire price entries
	 */
	@Override
	public List<CommerceTirePriceEntry> findByUuid(String uuid, int start,
		int end) {
		return findByUuid(uuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce tire price entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceTirePriceEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of commerce tire price entries
	 * @param end the upper bound of the range of commerce tire price entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce tire price entries
	 */
	@Override
	public List<CommerceTirePriceEntry> findByUuid(String uuid, int start,
		int end, OrderByComparator<CommerceTirePriceEntry> orderByComparator) {
		return findByUuid(uuid, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce tire price entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceTirePriceEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of commerce tire price entries
	 * @param end the upper bound of the range of commerce tire price entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching commerce tire price entries
	 */
	@Override
	public List<CommerceTirePriceEntry> findByUuid(String uuid, int start,
		int end, OrderByComparator<CommerceTirePriceEntry> orderByComparator,
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

		List<CommerceTirePriceEntry> list = null;

		if (retrieveFromCache) {
			list = (List<CommerceTirePriceEntry>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (CommerceTirePriceEntry commerceTirePriceEntry : list) {
					if (!Objects.equals(uuid, commerceTirePriceEntry.getUuid())) {
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

			query.append(_SQL_SELECT_COMMERCETIREPRICEENTRY_WHERE);

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
				query.append(CommerceTirePriceEntryModelImpl.ORDER_BY_JPQL);
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
					list = (List<CommerceTirePriceEntry>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<CommerceTirePriceEntry>)QueryUtil.list(q,
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
	 * Returns the first commerce tire price entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce tire price entry
	 * @throws NoSuchTirePriceEntryException if a matching commerce tire price entry could not be found
	 */
	@Override
	public CommerceTirePriceEntry findByUuid_First(String uuid,
		OrderByComparator<CommerceTirePriceEntry> orderByComparator)
		throws NoSuchTirePriceEntryException {
		CommerceTirePriceEntry commerceTirePriceEntry = fetchByUuid_First(uuid,
				orderByComparator);

		if (commerceTirePriceEntry != null) {
			return commerceTirePriceEntry;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchTirePriceEntryException(msg.toString());
	}

	/**
	 * Returns the first commerce tire price entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce tire price entry, or <code>null</code> if a matching commerce tire price entry could not be found
	 */
	@Override
	public CommerceTirePriceEntry fetchByUuid_First(String uuid,
		OrderByComparator<CommerceTirePriceEntry> orderByComparator) {
		List<CommerceTirePriceEntry> list = findByUuid(uuid, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last commerce tire price entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce tire price entry
	 * @throws NoSuchTirePriceEntryException if a matching commerce tire price entry could not be found
	 */
	@Override
	public CommerceTirePriceEntry findByUuid_Last(String uuid,
		OrderByComparator<CommerceTirePriceEntry> orderByComparator)
		throws NoSuchTirePriceEntryException {
		CommerceTirePriceEntry commerceTirePriceEntry = fetchByUuid_Last(uuid,
				orderByComparator);

		if (commerceTirePriceEntry != null) {
			return commerceTirePriceEntry;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchTirePriceEntryException(msg.toString());
	}

	/**
	 * Returns the last commerce tire price entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce tire price entry, or <code>null</code> if a matching commerce tire price entry could not be found
	 */
	@Override
	public CommerceTirePriceEntry fetchByUuid_Last(String uuid,
		OrderByComparator<CommerceTirePriceEntry> orderByComparator) {
		int count = countByUuid(uuid);

		if (count == 0) {
			return null;
		}

		List<CommerceTirePriceEntry> list = findByUuid(uuid, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the commerce tire price entries before and after the current commerce tire price entry in the ordered set where uuid = &#63;.
	 *
	 * @param CommerceTirePriceEntryId the primary key of the current commerce tire price entry
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce tire price entry
	 * @throws NoSuchTirePriceEntryException if a commerce tire price entry with the primary key could not be found
	 */
	@Override
	public CommerceTirePriceEntry[] findByUuid_PrevAndNext(
		long CommerceTirePriceEntryId, String uuid,
		OrderByComparator<CommerceTirePriceEntry> orderByComparator)
		throws NoSuchTirePriceEntryException {
		CommerceTirePriceEntry commerceTirePriceEntry = findByPrimaryKey(CommerceTirePriceEntryId);

		Session session = null;

		try {
			session = openSession();

			CommerceTirePriceEntry[] array = new CommerceTirePriceEntryImpl[3];

			array[0] = getByUuid_PrevAndNext(session, commerceTirePriceEntry,
					uuid, orderByComparator, true);

			array[1] = commerceTirePriceEntry;

			array[2] = getByUuid_PrevAndNext(session, commerceTirePriceEntry,
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

	protected CommerceTirePriceEntry getByUuid_PrevAndNext(Session session,
		CommerceTirePriceEntry commerceTirePriceEntry, String uuid,
		OrderByComparator<CommerceTirePriceEntry> orderByComparator,
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

		query.append(_SQL_SELECT_COMMERCETIREPRICEENTRY_WHERE);

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
			query.append(CommerceTirePriceEntryModelImpl.ORDER_BY_JPQL);
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
			Object[] values = orderByComparator.getOrderByConditionValues(commerceTirePriceEntry);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<CommerceTirePriceEntry> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the commerce tire price entries where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	@Override
	public void removeByUuid(String uuid) {
		for (CommerceTirePriceEntry commerceTirePriceEntry : findByUuid(uuid,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(commerceTirePriceEntry);
		}
	}

	/**
	 * Returns the number of commerce tire price entries where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching commerce tire price entries
	 */
	@Override
	public int countByUuid(String uuid) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_UUID;

		Object[] finderArgs = new Object[] { uuid };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_COMMERCETIREPRICEENTRY_WHERE);

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

	private static final String _FINDER_COLUMN_UUID_UUID_1 = "commerceTirePriceEntry.uuid IS NULL";
	private static final String _FINDER_COLUMN_UUID_UUID_2 = "commerceTirePriceEntry.uuid = ?";
	private static final String _FINDER_COLUMN_UUID_UUID_3 = "(commerceTirePriceEntry.uuid IS NULL OR commerceTirePriceEntry.uuid = '')";
	public static final FinderPath FINDER_PATH_FETCH_BY_UUID_G = new FinderPath(CommerceTirePriceEntryModelImpl.ENTITY_CACHE_ENABLED,
			CommerceTirePriceEntryModelImpl.FINDER_CACHE_ENABLED,
			CommerceTirePriceEntryImpl.class, FINDER_CLASS_NAME_ENTITY,
			"fetchByUUID_G",
			new String[] { String.class.getName(), Long.class.getName() },
			CommerceTirePriceEntryModelImpl.UUID_COLUMN_BITMASK |
			CommerceTirePriceEntryModelImpl.GROUPID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID_G = new FinderPath(CommerceTirePriceEntryModelImpl.ENTITY_CACHE_ENABLED,
			CommerceTirePriceEntryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUUID_G",
			new String[] { String.class.getName(), Long.class.getName() });

	/**
	 * Returns the commerce tire price entry where uuid = &#63; and groupId = &#63; or throws a {@link NoSuchTirePriceEntryException} if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching commerce tire price entry
	 * @throws NoSuchTirePriceEntryException if a matching commerce tire price entry could not be found
	 */
	@Override
	public CommerceTirePriceEntry findByUUID_G(String uuid, long groupId)
		throws NoSuchTirePriceEntryException {
		CommerceTirePriceEntry commerceTirePriceEntry = fetchByUUID_G(uuid,
				groupId);

		if (commerceTirePriceEntry == null) {
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

			throw new NoSuchTirePriceEntryException(msg.toString());
		}

		return commerceTirePriceEntry;
	}

	/**
	 * Returns the commerce tire price entry where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching commerce tire price entry, or <code>null</code> if a matching commerce tire price entry could not be found
	 */
	@Override
	public CommerceTirePriceEntry fetchByUUID_G(String uuid, long groupId) {
		return fetchByUUID_G(uuid, groupId, true);
	}

	/**
	 * Returns the commerce tire price entry where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the matching commerce tire price entry, or <code>null</code> if a matching commerce tire price entry could not be found
	 */
	@Override
	public CommerceTirePriceEntry fetchByUUID_G(String uuid, long groupId,
		boolean retrieveFromCache) {
		Object[] finderArgs = new Object[] { uuid, groupId };

		Object result = null;

		if (retrieveFromCache) {
			result = finderCache.getResult(FINDER_PATH_FETCH_BY_UUID_G,
					finderArgs, this);
		}

		if (result instanceof CommerceTirePriceEntry) {
			CommerceTirePriceEntry commerceTirePriceEntry = (CommerceTirePriceEntry)result;

			if (!Objects.equals(uuid, commerceTirePriceEntry.getUuid()) ||
					(groupId != commerceTirePriceEntry.getGroupId())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_SELECT_COMMERCETIREPRICEENTRY_WHERE);

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

				List<CommerceTirePriceEntry> list = q.list();

				if (list.isEmpty()) {
					finderCache.putResult(FINDER_PATH_FETCH_BY_UUID_G,
						finderArgs, list);
				}
				else {
					CommerceTirePriceEntry commerceTirePriceEntry = list.get(0);

					result = commerceTirePriceEntry;

					cacheResult(commerceTirePriceEntry);

					if ((commerceTirePriceEntry.getUuid() == null) ||
							!commerceTirePriceEntry.getUuid().equals(uuid) ||
							(commerceTirePriceEntry.getGroupId() != groupId)) {
						finderCache.putResult(FINDER_PATH_FETCH_BY_UUID_G,
							finderArgs, commerceTirePriceEntry);
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
			return (CommerceTirePriceEntry)result;
		}
	}

	/**
	 * Removes the commerce tire price entry where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the commerce tire price entry that was removed
	 */
	@Override
	public CommerceTirePriceEntry removeByUUID_G(String uuid, long groupId)
		throws NoSuchTirePriceEntryException {
		CommerceTirePriceEntry commerceTirePriceEntry = findByUUID_G(uuid,
				groupId);

		return remove(commerceTirePriceEntry);
	}

	/**
	 * Returns the number of commerce tire price entries where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching commerce tire price entries
	 */
	@Override
	public int countByUUID_G(String uuid, long groupId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_UUID_G;

		Object[] finderArgs = new Object[] { uuid, groupId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_COMMERCETIREPRICEENTRY_WHERE);

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

	private static final String _FINDER_COLUMN_UUID_G_UUID_1 = "commerceTirePriceEntry.uuid IS NULL AND ";
	private static final String _FINDER_COLUMN_UUID_G_UUID_2 = "commerceTirePriceEntry.uuid = ? AND ";
	private static final String _FINDER_COLUMN_UUID_G_UUID_3 = "(commerceTirePriceEntry.uuid IS NULL OR commerceTirePriceEntry.uuid = '') AND ";
	private static final String _FINDER_COLUMN_UUID_G_GROUPID_2 = "commerceTirePriceEntry.groupId = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_UUID_C = new FinderPath(CommerceTirePriceEntryModelImpl.ENTITY_CACHE_ENABLED,
			CommerceTirePriceEntryModelImpl.FINDER_CACHE_ENABLED,
			CommerceTirePriceEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUuid_C",
			new String[] {
				String.class.getName(), Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C =
		new FinderPath(CommerceTirePriceEntryModelImpl.ENTITY_CACHE_ENABLED,
			CommerceTirePriceEntryModelImpl.FINDER_CACHE_ENABLED,
			CommerceTirePriceEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid_C",
			new String[] { String.class.getName(), Long.class.getName() },
			CommerceTirePriceEntryModelImpl.UUID_COLUMN_BITMASK |
			CommerceTirePriceEntryModelImpl.COMPANYID_COLUMN_BITMASK |
			CommerceTirePriceEntryModelImpl.CREATEDATE_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID_C = new FinderPath(CommerceTirePriceEntryModelImpl.ENTITY_CACHE_ENABLED,
			CommerceTirePriceEntryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUuid_C",
			new String[] { String.class.getName(), Long.class.getName() });

	/**
	 * Returns all the commerce tire price entries where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching commerce tire price entries
	 */
	@Override
	public List<CommerceTirePriceEntry> findByUuid_C(String uuid, long companyId) {
		return findByUuid_C(uuid, companyId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce tire price entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceTirePriceEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce tire price entries
	 * @param end the upper bound of the range of commerce tire price entries (not inclusive)
	 * @return the range of matching commerce tire price entries
	 */
	@Override
	public List<CommerceTirePriceEntry> findByUuid_C(String uuid,
		long companyId, int start, int end) {
		return findByUuid_C(uuid, companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce tire price entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceTirePriceEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce tire price entries
	 * @param end the upper bound of the range of commerce tire price entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce tire price entries
	 */
	@Override
	public List<CommerceTirePriceEntry> findByUuid_C(String uuid,
		long companyId, int start, int end,
		OrderByComparator<CommerceTirePriceEntry> orderByComparator) {
		return findByUuid_C(uuid, companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce tire price entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceTirePriceEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce tire price entries
	 * @param end the upper bound of the range of commerce tire price entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching commerce tire price entries
	 */
	@Override
	public List<CommerceTirePriceEntry> findByUuid_C(String uuid,
		long companyId, int start, int end,
		OrderByComparator<CommerceTirePriceEntry> orderByComparator,
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

		List<CommerceTirePriceEntry> list = null;

		if (retrieveFromCache) {
			list = (List<CommerceTirePriceEntry>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (CommerceTirePriceEntry commerceTirePriceEntry : list) {
					if (!Objects.equals(uuid, commerceTirePriceEntry.getUuid()) ||
							(companyId != commerceTirePriceEntry.getCompanyId())) {
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

			query.append(_SQL_SELECT_COMMERCETIREPRICEENTRY_WHERE);

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
				query.append(CommerceTirePriceEntryModelImpl.ORDER_BY_JPQL);
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
					list = (List<CommerceTirePriceEntry>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<CommerceTirePriceEntry>)QueryUtil.list(q,
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
	 * Returns the first commerce tire price entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce tire price entry
	 * @throws NoSuchTirePriceEntryException if a matching commerce tire price entry could not be found
	 */
	@Override
	public CommerceTirePriceEntry findByUuid_C_First(String uuid,
		long companyId,
		OrderByComparator<CommerceTirePriceEntry> orderByComparator)
		throws NoSuchTirePriceEntryException {
		CommerceTirePriceEntry commerceTirePriceEntry = fetchByUuid_C_First(uuid,
				companyId, orderByComparator);

		if (commerceTirePriceEntry != null) {
			return commerceTirePriceEntry;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(", companyId=");
		msg.append(companyId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchTirePriceEntryException(msg.toString());
	}

	/**
	 * Returns the first commerce tire price entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce tire price entry, or <code>null</code> if a matching commerce tire price entry could not be found
	 */
	@Override
	public CommerceTirePriceEntry fetchByUuid_C_First(String uuid,
		long companyId,
		OrderByComparator<CommerceTirePriceEntry> orderByComparator) {
		List<CommerceTirePriceEntry> list = findByUuid_C(uuid, companyId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last commerce tire price entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce tire price entry
	 * @throws NoSuchTirePriceEntryException if a matching commerce tire price entry could not be found
	 */
	@Override
	public CommerceTirePriceEntry findByUuid_C_Last(String uuid,
		long companyId,
		OrderByComparator<CommerceTirePriceEntry> orderByComparator)
		throws NoSuchTirePriceEntryException {
		CommerceTirePriceEntry commerceTirePriceEntry = fetchByUuid_C_Last(uuid,
				companyId, orderByComparator);

		if (commerceTirePriceEntry != null) {
			return commerceTirePriceEntry;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(", companyId=");
		msg.append(companyId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchTirePriceEntryException(msg.toString());
	}

	/**
	 * Returns the last commerce tire price entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce tire price entry, or <code>null</code> if a matching commerce tire price entry could not be found
	 */
	@Override
	public CommerceTirePriceEntry fetchByUuid_C_Last(String uuid,
		long companyId,
		OrderByComparator<CommerceTirePriceEntry> orderByComparator) {
		int count = countByUuid_C(uuid, companyId);

		if (count == 0) {
			return null;
		}

		List<CommerceTirePriceEntry> list = findByUuid_C(uuid, companyId,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the commerce tire price entries before and after the current commerce tire price entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param CommerceTirePriceEntryId the primary key of the current commerce tire price entry
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce tire price entry
	 * @throws NoSuchTirePriceEntryException if a commerce tire price entry with the primary key could not be found
	 */
	@Override
	public CommerceTirePriceEntry[] findByUuid_C_PrevAndNext(
		long CommerceTirePriceEntryId, String uuid, long companyId,
		OrderByComparator<CommerceTirePriceEntry> orderByComparator)
		throws NoSuchTirePriceEntryException {
		CommerceTirePriceEntry commerceTirePriceEntry = findByPrimaryKey(CommerceTirePriceEntryId);

		Session session = null;

		try {
			session = openSession();

			CommerceTirePriceEntry[] array = new CommerceTirePriceEntryImpl[3];

			array[0] = getByUuid_C_PrevAndNext(session, commerceTirePriceEntry,
					uuid, companyId, orderByComparator, true);

			array[1] = commerceTirePriceEntry;

			array[2] = getByUuid_C_PrevAndNext(session, commerceTirePriceEntry,
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

	protected CommerceTirePriceEntry getByUuid_C_PrevAndNext(Session session,
		CommerceTirePriceEntry commerceTirePriceEntry, String uuid,
		long companyId,
		OrderByComparator<CommerceTirePriceEntry> orderByComparator,
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

		query.append(_SQL_SELECT_COMMERCETIREPRICEENTRY_WHERE);

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
			query.append(CommerceTirePriceEntryModelImpl.ORDER_BY_JPQL);
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
			Object[] values = orderByComparator.getOrderByConditionValues(commerceTirePriceEntry);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<CommerceTirePriceEntry> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the commerce tire price entries where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	@Override
	public void removeByUuid_C(String uuid, long companyId) {
		for (CommerceTirePriceEntry commerceTirePriceEntry : findByUuid_C(
				uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(commerceTirePriceEntry);
		}
	}

	/**
	 * Returns the number of commerce tire price entries where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching commerce tire price entries
	 */
	@Override
	public int countByUuid_C(String uuid, long companyId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_UUID_C;

		Object[] finderArgs = new Object[] { uuid, companyId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_COMMERCETIREPRICEENTRY_WHERE);

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

	private static final String _FINDER_COLUMN_UUID_C_UUID_1 = "commerceTirePriceEntry.uuid IS NULL AND ";
	private static final String _FINDER_COLUMN_UUID_C_UUID_2 = "commerceTirePriceEntry.uuid = ? AND ";
	private static final String _FINDER_COLUMN_UUID_C_UUID_3 = "(commerceTirePriceEntry.uuid IS NULL OR commerceTirePriceEntry.uuid = '') AND ";
	private static final String _FINDER_COLUMN_UUID_C_COMPANYID_2 = "commerceTirePriceEntry.companyId = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_GROUPID = new FinderPath(CommerceTirePriceEntryModelImpl.ENTITY_CACHE_ENABLED,
			CommerceTirePriceEntryModelImpl.FINDER_CACHE_ENABLED,
			CommerceTirePriceEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByGroupId",
			new String[] {
				Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID =
		new FinderPath(CommerceTirePriceEntryModelImpl.ENTITY_CACHE_ENABLED,
			CommerceTirePriceEntryModelImpl.FINDER_CACHE_ENABLED,
			CommerceTirePriceEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByGroupId",
			new String[] { Long.class.getName() },
			CommerceTirePriceEntryModelImpl.GROUPID_COLUMN_BITMASK |
			CommerceTirePriceEntryModelImpl.CREATEDATE_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_GROUPID = new FinderPath(CommerceTirePriceEntryModelImpl.ENTITY_CACHE_ENABLED,
			CommerceTirePriceEntryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByGroupId",
			new String[] { Long.class.getName() });

	/**
	 * Returns all the commerce tire price entries where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching commerce tire price entries
	 */
	@Override
	public List<CommerceTirePriceEntry> findByGroupId(long groupId) {
		return findByGroupId(groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce tire price entries where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceTirePriceEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of commerce tire price entries
	 * @param end the upper bound of the range of commerce tire price entries (not inclusive)
	 * @return the range of matching commerce tire price entries
	 */
	@Override
	public List<CommerceTirePriceEntry> findByGroupId(long groupId, int start,
		int end) {
		return findByGroupId(groupId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce tire price entries where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceTirePriceEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of commerce tire price entries
	 * @param end the upper bound of the range of commerce tire price entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce tire price entries
	 */
	@Override
	public List<CommerceTirePriceEntry> findByGroupId(long groupId, int start,
		int end, OrderByComparator<CommerceTirePriceEntry> orderByComparator) {
		return findByGroupId(groupId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce tire price entries where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceTirePriceEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of commerce tire price entries
	 * @param end the upper bound of the range of commerce tire price entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching commerce tire price entries
	 */
	@Override
	public List<CommerceTirePriceEntry> findByGroupId(long groupId, int start,
		int end, OrderByComparator<CommerceTirePriceEntry> orderByComparator,
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

		List<CommerceTirePriceEntry> list = null;

		if (retrieveFromCache) {
			list = (List<CommerceTirePriceEntry>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (CommerceTirePriceEntry commerceTirePriceEntry : list) {
					if ((groupId != commerceTirePriceEntry.getGroupId())) {
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

			query.append(_SQL_SELECT_COMMERCETIREPRICEENTRY_WHERE);

			query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(CommerceTirePriceEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (!pagination) {
					list = (List<CommerceTirePriceEntry>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<CommerceTirePriceEntry>)QueryUtil.list(q,
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
	 * Returns the first commerce tire price entry in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce tire price entry
	 * @throws NoSuchTirePriceEntryException if a matching commerce tire price entry could not be found
	 */
	@Override
	public CommerceTirePriceEntry findByGroupId_First(long groupId,
		OrderByComparator<CommerceTirePriceEntry> orderByComparator)
		throws NoSuchTirePriceEntryException {
		CommerceTirePriceEntry commerceTirePriceEntry = fetchByGroupId_First(groupId,
				orderByComparator);

		if (commerceTirePriceEntry != null) {
			return commerceTirePriceEntry;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchTirePriceEntryException(msg.toString());
	}

	/**
	 * Returns the first commerce tire price entry in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce tire price entry, or <code>null</code> if a matching commerce tire price entry could not be found
	 */
	@Override
	public CommerceTirePriceEntry fetchByGroupId_First(long groupId,
		OrderByComparator<CommerceTirePriceEntry> orderByComparator) {
		List<CommerceTirePriceEntry> list = findByGroupId(groupId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last commerce tire price entry in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce tire price entry
	 * @throws NoSuchTirePriceEntryException if a matching commerce tire price entry could not be found
	 */
	@Override
	public CommerceTirePriceEntry findByGroupId_Last(long groupId,
		OrderByComparator<CommerceTirePriceEntry> orderByComparator)
		throws NoSuchTirePriceEntryException {
		CommerceTirePriceEntry commerceTirePriceEntry = fetchByGroupId_Last(groupId,
				orderByComparator);

		if (commerceTirePriceEntry != null) {
			return commerceTirePriceEntry;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchTirePriceEntryException(msg.toString());
	}

	/**
	 * Returns the last commerce tire price entry in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce tire price entry, or <code>null</code> if a matching commerce tire price entry could not be found
	 */
	@Override
	public CommerceTirePriceEntry fetchByGroupId_Last(long groupId,
		OrderByComparator<CommerceTirePriceEntry> orderByComparator) {
		int count = countByGroupId(groupId);

		if (count == 0) {
			return null;
		}

		List<CommerceTirePriceEntry> list = findByGroupId(groupId, count - 1,
				count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the commerce tire price entries before and after the current commerce tire price entry in the ordered set where groupId = &#63;.
	 *
	 * @param CommerceTirePriceEntryId the primary key of the current commerce tire price entry
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce tire price entry
	 * @throws NoSuchTirePriceEntryException if a commerce tire price entry with the primary key could not be found
	 */
	@Override
	public CommerceTirePriceEntry[] findByGroupId_PrevAndNext(
		long CommerceTirePriceEntryId, long groupId,
		OrderByComparator<CommerceTirePriceEntry> orderByComparator)
		throws NoSuchTirePriceEntryException {
		CommerceTirePriceEntry commerceTirePriceEntry = findByPrimaryKey(CommerceTirePriceEntryId);

		Session session = null;

		try {
			session = openSession();

			CommerceTirePriceEntry[] array = new CommerceTirePriceEntryImpl[3];

			array[0] = getByGroupId_PrevAndNext(session,
					commerceTirePriceEntry, groupId, orderByComparator, true);

			array[1] = commerceTirePriceEntry;

			array[2] = getByGroupId_PrevAndNext(session,
					commerceTirePriceEntry, groupId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected CommerceTirePriceEntry getByGroupId_PrevAndNext(Session session,
		CommerceTirePriceEntry commerceTirePriceEntry, long groupId,
		OrderByComparator<CommerceTirePriceEntry> orderByComparator,
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

		query.append(_SQL_SELECT_COMMERCETIREPRICEENTRY_WHERE);

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
			query.append(CommerceTirePriceEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(commerceTirePriceEntry);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<CommerceTirePriceEntry> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the commerce tire price entries where groupId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 */
	@Override
	public void removeByGroupId(long groupId) {
		for (CommerceTirePriceEntry commerceTirePriceEntry : findByGroupId(
				groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(commerceTirePriceEntry);
		}
	}

	/**
	 * Returns the number of commerce tire price entries where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching commerce tire price entries
	 */
	@Override
	public int countByGroupId(long groupId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_GROUPID;

		Object[] finderArgs = new Object[] { groupId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_COMMERCETIREPRICEENTRY_WHERE);

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

	private static final String _FINDER_COLUMN_GROUPID_GROUPID_2 = "commerceTirePriceEntry.groupId = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_COMPANYID =
		new FinderPath(CommerceTirePriceEntryModelImpl.ENTITY_CACHE_ENABLED,
			CommerceTirePriceEntryModelImpl.FINDER_CACHE_ENABLED,
			CommerceTirePriceEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByCompanyId",
			new String[] {
				Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID =
		new FinderPath(CommerceTirePriceEntryModelImpl.ENTITY_CACHE_ENABLED,
			CommerceTirePriceEntryModelImpl.FINDER_CACHE_ENABLED,
			CommerceTirePriceEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByCompanyId",
			new String[] { Long.class.getName() },
			CommerceTirePriceEntryModelImpl.COMPANYID_COLUMN_BITMASK |
			CommerceTirePriceEntryModelImpl.CREATEDATE_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_COMPANYID = new FinderPath(CommerceTirePriceEntryModelImpl.ENTITY_CACHE_ENABLED,
			CommerceTirePriceEntryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByCompanyId",
			new String[] { Long.class.getName() });

	/**
	 * Returns all the commerce tire price entries where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching commerce tire price entries
	 */
	@Override
	public List<CommerceTirePriceEntry> findByCompanyId(long companyId) {
		return findByCompanyId(companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the commerce tire price entries where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceTirePriceEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce tire price entries
	 * @param end the upper bound of the range of commerce tire price entries (not inclusive)
	 * @return the range of matching commerce tire price entries
	 */
	@Override
	public List<CommerceTirePriceEntry> findByCompanyId(long companyId,
		int start, int end) {
		return findByCompanyId(companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce tire price entries where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceTirePriceEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce tire price entries
	 * @param end the upper bound of the range of commerce tire price entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce tire price entries
	 */
	@Override
	public List<CommerceTirePriceEntry> findByCompanyId(long companyId,
		int start, int end,
		OrderByComparator<CommerceTirePriceEntry> orderByComparator) {
		return findByCompanyId(companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce tire price entries where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceTirePriceEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce tire price entries
	 * @param end the upper bound of the range of commerce tire price entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching commerce tire price entries
	 */
	@Override
	public List<CommerceTirePriceEntry> findByCompanyId(long companyId,
		int start, int end,
		OrderByComparator<CommerceTirePriceEntry> orderByComparator,
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

		List<CommerceTirePriceEntry> list = null;

		if (retrieveFromCache) {
			list = (List<CommerceTirePriceEntry>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (CommerceTirePriceEntry commerceTirePriceEntry : list) {
					if ((companyId != commerceTirePriceEntry.getCompanyId())) {
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

			query.append(_SQL_SELECT_COMMERCETIREPRICEENTRY_WHERE);

			query.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(CommerceTirePriceEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				if (!pagination) {
					list = (List<CommerceTirePriceEntry>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<CommerceTirePriceEntry>)QueryUtil.list(q,
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
	 * Returns the first commerce tire price entry in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce tire price entry
	 * @throws NoSuchTirePriceEntryException if a matching commerce tire price entry could not be found
	 */
	@Override
	public CommerceTirePriceEntry findByCompanyId_First(long companyId,
		OrderByComparator<CommerceTirePriceEntry> orderByComparator)
		throws NoSuchTirePriceEntryException {
		CommerceTirePriceEntry commerceTirePriceEntry = fetchByCompanyId_First(companyId,
				orderByComparator);

		if (commerceTirePriceEntry != null) {
			return commerceTirePriceEntry;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("companyId=");
		msg.append(companyId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchTirePriceEntryException(msg.toString());
	}

	/**
	 * Returns the first commerce tire price entry in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce tire price entry, or <code>null</code> if a matching commerce tire price entry could not be found
	 */
	@Override
	public CommerceTirePriceEntry fetchByCompanyId_First(long companyId,
		OrderByComparator<CommerceTirePriceEntry> orderByComparator) {
		List<CommerceTirePriceEntry> list = findByCompanyId(companyId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last commerce tire price entry in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce tire price entry
	 * @throws NoSuchTirePriceEntryException if a matching commerce tire price entry could not be found
	 */
	@Override
	public CommerceTirePriceEntry findByCompanyId_Last(long companyId,
		OrderByComparator<CommerceTirePriceEntry> orderByComparator)
		throws NoSuchTirePriceEntryException {
		CommerceTirePriceEntry commerceTirePriceEntry = fetchByCompanyId_Last(companyId,
				orderByComparator);

		if (commerceTirePriceEntry != null) {
			return commerceTirePriceEntry;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("companyId=");
		msg.append(companyId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchTirePriceEntryException(msg.toString());
	}

	/**
	 * Returns the last commerce tire price entry in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce tire price entry, or <code>null</code> if a matching commerce tire price entry could not be found
	 */
	@Override
	public CommerceTirePriceEntry fetchByCompanyId_Last(long companyId,
		OrderByComparator<CommerceTirePriceEntry> orderByComparator) {
		int count = countByCompanyId(companyId);

		if (count == 0) {
			return null;
		}

		List<CommerceTirePriceEntry> list = findByCompanyId(companyId,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the commerce tire price entries before and after the current commerce tire price entry in the ordered set where companyId = &#63;.
	 *
	 * @param CommerceTirePriceEntryId the primary key of the current commerce tire price entry
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce tire price entry
	 * @throws NoSuchTirePriceEntryException if a commerce tire price entry with the primary key could not be found
	 */
	@Override
	public CommerceTirePriceEntry[] findByCompanyId_PrevAndNext(
		long CommerceTirePriceEntryId, long companyId,
		OrderByComparator<CommerceTirePriceEntry> orderByComparator)
		throws NoSuchTirePriceEntryException {
		CommerceTirePriceEntry commerceTirePriceEntry = findByPrimaryKey(CommerceTirePriceEntryId);

		Session session = null;

		try {
			session = openSession();

			CommerceTirePriceEntry[] array = new CommerceTirePriceEntryImpl[3];

			array[0] = getByCompanyId_PrevAndNext(session,
					commerceTirePriceEntry, companyId, orderByComparator, true);

			array[1] = commerceTirePriceEntry;

			array[2] = getByCompanyId_PrevAndNext(session,
					commerceTirePriceEntry, companyId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected CommerceTirePriceEntry getByCompanyId_PrevAndNext(
		Session session, CommerceTirePriceEntry commerceTirePriceEntry,
		long companyId,
		OrderByComparator<CommerceTirePriceEntry> orderByComparator,
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

		query.append(_SQL_SELECT_COMMERCETIREPRICEENTRY_WHERE);

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
			query.append(CommerceTirePriceEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(companyId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(commerceTirePriceEntry);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<CommerceTirePriceEntry> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the commerce tire price entries where companyId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 */
	@Override
	public void removeByCompanyId(long companyId) {
		for (CommerceTirePriceEntry commerceTirePriceEntry : findByCompanyId(
				companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(commerceTirePriceEntry);
		}
	}

	/**
	 * Returns the number of commerce tire price entries where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching commerce tire price entries
	 */
	@Override
	public int countByCompanyId(long companyId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_COMPANYID;

		Object[] finderArgs = new Object[] { companyId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_COMMERCETIREPRICEENTRY_WHERE);

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

	private static final String _FINDER_COLUMN_COMPANYID_COMPANYID_2 = "commerceTirePriceEntry.companyId = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_COMMERCEPRICEENTRYID =
		new FinderPath(CommerceTirePriceEntryModelImpl.ENTITY_CACHE_ENABLED,
			CommerceTirePriceEntryModelImpl.FINDER_CACHE_ENABLED,
			CommerceTirePriceEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByCommercePriceEntryId",
			new String[] {
				Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMMERCEPRICEENTRYID =
		new FinderPath(CommerceTirePriceEntryModelImpl.ENTITY_CACHE_ENABLED,
			CommerceTirePriceEntryModelImpl.FINDER_CACHE_ENABLED,
			CommerceTirePriceEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByCommercePriceEntryId",
			new String[] { Long.class.getName() },
			CommerceTirePriceEntryModelImpl.COMMERCEPRICEENTRYID_COLUMN_BITMASK |
			CommerceTirePriceEntryModelImpl.CREATEDATE_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_COMMERCEPRICEENTRYID = new FinderPath(CommerceTirePriceEntryModelImpl.ENTITY_CACHE_ENABLED,
			CommerceTirePriceEntryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByCommercePriceEntryId", new String[] { Long.class.getName() });

	/**
	 * Returns all the commerce tire price entries where commercePriceEntryId = &#63;.
	 *
	 * @param commercePriceEntryId the commerce price entry ID
	 * @return the matching commerce tire price entries
	 */
	@Override
	public List<CommerceTirePriceEntry> findByCommercePriceEntryId(
		long commercePriceEntryId) {
		return findByCommercePriceEntryId(commercePriceEntryId,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce tire price entries where commercePriceEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceTirePriceEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param commercePriceEntryId the commerce price entry ID
	 * @param start the lower bound of the range of commerce tire price entries
	 * @param end the upper bound of the range of commerce tire price entries (not inclusive)
	 * @return the range of matching commerce tire price entries
	 */
	@Override
	public List<CommerceTirePriceEntry> findByCommercePriceEntryId(
		long commercePriceEntryId, int start, int end) {
		return findByCommercePriceEntryId(commercePriceEntryId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce tire price entries where commercePriceEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceTirePriceEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param commercePriceEntryId the commerce price entry ID
	 * @param start the lower bound of the range of commerce tire price entries
	 * @param end the upper bound of the range of commerce tire price entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce tire price entries
	 */
	@Override
	public List<CommerceTirePriceEntry> findByCommercePriceEntryId(
		long commercePriceEntryId, int start, int end,
		OrderByComparator<CommerceTirePriceEntry> orderByComparator) {
		return findByCommercePriceEntryId(commercePriceEntryId, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce tire price entries where commercePriceEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceTirePriceEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param commercePriceEntryId the commerce price entry ID
	 * @param start the lower bound of the range of commerce tire price entries
	 * @param end the upper bound of the range of commerce tire price entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching commerce tire price entries
	 */
	@Override
	public List<CommerceTirePriceEntry> findByCommercePriceEntryId(
		long commercePriceEntryId, int start, int end,
		OrderByComparator<CommerceTirePriceEntry> orderByComparator,
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

		List<CommerceTirePriceEntry> list = null;

		if (retrieveFromCache) {
			list = (List<CommerceTirePriceEntry>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (CommerceTirePriceEntry commerceTirePriceEntry : list) {
					if ((commercePriceEntryId != commerceTirePriceEntry.getCommercePriceEntryId())) {
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

			query.append(_SQL_SELECT_COMMERCETIREPRICEENTRY_WHERE);

			query.append(_FINDER_COLUMN_COMMERCEPRICEENTRYID_COMMERCEPRICEENTRYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(CommerceTirePriceEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(commercePriceEntryId);

				if (!pagination) {
					list = (List<CommerceTirePriceEntry>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<CommerceTirePriceEntry>)QueryUtil.list(q,
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
	 * Returns the first commerce tire price entry in the ordered set where commercePriceEntryId = &#63;.
	 *
	 * @param commercePriceEntryId the commerce price entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce tire price entry
	 * @throws NoSuchTirePriceEntryException if a matching commerce tire price entry could not be found
	 */
	@Override
	public CommerceTirePriceEntry findByCommercePriceEntryId_First(
		long commercePriceEntryId,
		OrderByComparator<CommerceTirePriceEntry> orderByComparator)
		throws NoSuchTirePriceEntryException {
		CommerceTirePriceEntry commerceTirePriceEntry = fetchByCommercePriceEntryId_First(commercePriceEntryId,
				orderByComparator);

		if (commerceTirePriceEntry != null) {
			return commerceTirePriceEntry;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("commercePriceEntryId=");
		msg.append(commercePriceEntryId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchTirePriceEntryException(msg.toString());
	}

	/**
	 * Returns the first commerce tire price entry in the ordered set where commercePriceEntryId = &#63;.
	 *
	 * @param commercePriceEntryId the commerce price entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce tire price entry, or <code>null</code> if a matching commerce tire price entry could not be found
	 */
	@Override
	public CommerceTirePriceEntry fetchByCommercePriceEntryId_First(
		long commercePriceEntryId,
		OrderByComparator<CommerceTirePriceEntry> orderByComparator) {
		List<CommerceTirePriceEntry> list = findByCommercePriceEntryId(commercePriceEntryId,
				0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last commerce tire price entry in the ordered set where commercePriceEntryId = &#63;.
	 *
	 * @param commercePriceEntryId the commerce price entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce tire price entry
	 * @throws NoSuchTirePriceEntryException if a matching commerce tire price entry could not be found
	 */
	@Override
	public CommerceTirePriceEntry findByCommercePriceEntryId_Last(
		long commercePriceEntryId,
		OrderByComparator<CommerceTirePriceEntry> orderByComparator)
		throws NoSuchTirePriceEntryException {
		CommerceTirePriceEntry commerceTirePriceEntry = fetchByCommercePriceEntryId_Last(commercePriceEntryId,
				orderByComparator);

		if (commerceTirePriceEntry != null) {
			return commerceTirePriceEntry;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("commercePriceEntryId=");
		msg.append(commercePriceEntryId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchTirePriceEntryException(msg.toString());
	}

	/**
	 * Returns the last commerce tire price entry in the ordered set where commercePriceEntryId = &#63;.
	 *
	 * @param commercePriceEntryId the commerce price entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce tire price entry, or <code>null</code> if a matching commerce tire price entry could not be found
	 */
	@Override
	public CommerceTirePriceEntry fetchByCommercePriceEntryId_Last(
		long commercePriceEntryId,
		OrderByComparator<CommerceTirePriceEntry> orderByComparator) {
		int count = countByCommercePriceEntryId(commercePriceEntryId);

		if (count == 0) {
			return null;
		}

		List<CommerceTirePriceEntry> list = findByCommercePriceEntryId(commercePriceEntryId,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the commerce tire price entries before and after the current commerce tire price entry in the ordered set where commercePriceEntryId = &#63;.
	 *
	 * @param CommerceTirePriceEntryId the primary key of the current commerce tire price entry
	 * @param commercePriceEntryId the commerce price entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce tire price entry
	 * @throws NoSuchTirePriceEntryException if a commerce tire price entry with the primary key could not be found
	 */
	@Override
	public CommerceTirePriceEntry[] findByCommercePriceEntryId_PrevAndNext(
		long CommerceTirePriceEntryId, long commercePriceEntryId,
		OrderByComparator<CommerceTirePriceEntry> orderByComparator)
		throws NoSuchTirePriceEntryException {
		CommerceTirePriceEntry commerceTirePriceEntry = findByPrimaryKey(CommerceTirePriceEntryId);

		Session session = null;

		try {
			session = openSession();

			CommerceTirePriceEntry[] array = new CommerceTirePriceEntryImpl[3];

			array[0] = getByCommercePriceEntryId_PrevAndNext(session,
					commerceTirePriceEntry, commercePriceEntryId,
					orderByComparator, true);

			array[1] = commerceTirePriceEntry;

			array[2] = getByCommercePriceEntryId_PrevAndNext(session,
					commerceTirePriceEntry, commercePriceEntryId,
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

	protected CommerceTirePriceEntry getByCommercePriceEntryId_PrevAndNext(
		Session session, CommerceTirePriceEntry commerceTirePriceEntry,
		long commercePriceEntryId,
		OrderByComparator<CommerceTirePriceEntry> orderByComparator,
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

		query.append(_SQL_SELECT_COMMERCETIREPRICEENTRY_WHERE);

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
			query.append(CommerceTirePriceEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(commercePriceEntryId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(commerceTirePriceEntry);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<CommerceTirePriceEntry> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the commerce tire price entries where commercePriceEntryId = &#63; from the database.
	 *
	 * @param commercePriceEntryId the commerce price entry ID
	 */
	@Override
	public void removeByCommercePriceEntryId(long commercePriceEntryId) {
		for (CommerceTirePriceEntry commerceTirePriceEntry : findByCommercePriceEntryId(
				commercePriceEntryId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(commerceTirePriceEntry);
		}
	}

	/**
	 * Returns the number of commerce tire price entries where commercePriceEntryId = &#63;.
	 *
	 * @param commercePriceEntryId the commerce price entry ID
	 * @return the number of matching commerce tire price entries
	 */
	@Override
	public int countByCommercePriceEntryId(long commercePriceEntryId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_COMMERCEPRICEENTRYID;

		Object[] finderArgs = new Object[] { commercePriceEntryId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_COMMERCETIREPRICEENTRY_WHERE);

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
		"commerceTirePriceEntry.commercePriceEntryId = ?";

	public CommerceTirePriceEntryPersistenceImpl() {
		setModelClass(CommerceTirePriceEntry.class);

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
	 * Caches the commerce tire price entry in the entity cache if it is enabled.
	 *
	 * @param commerceTirePriceEntry the commerce tire price entry
	 */
	@Override
	public void cacheResult(CommerceTirePriceEntry commerceTirePriceEntry) {
		entityCache.putResult(CommerceTirePriceEntryModelImpl.ENTITY_CACHE_ENABLED,
			CommerceTirePriceEntryImpl.class,
			commerceTirePriceEntry.getPrimaryKey(), commerceTirePriceEntry);

		finderCache.putResult(FINDER_PATH_FETCH_BY_UUID_G,
			new Object[] {
				commerceTirePriceEntry.getUuid(),
				commerceTirePriceEntry.getGroupId()
			}, commerceTirePriceEntry);

		commerceTirePriceEntry.resetOriginalValues();
	}

	/**
	 * Caches the commerce tire price entries in the entity cache if it is enabled.
	 *
	 * @param commerceTirePriceEntries the commerce tire price entries
	 */
	@Override
	public void cacheResult(
		List<CommerceTirePriceEntry> commerceTirePriceEntries) {
		for (CommerceTirePriceEntry commerceTirePriceEntry : commerceTirePriceEntries) {
			if (entityCache.getResult(
						CommerceTirePriceEntryModelImpl.ENTITY_CACHE_ENABLED,
						CommerceTirePriceEntryImpl.class,
						commerceTirePriceEntry.getPrimaryKey()) == null) {
				cacheResult(commerceTirePriceEntry);
			}
			else {
				commerceTirePriceEntry.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all commerce tire price entries.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(CommerceTirePriceEntryImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the commerce tire price entry.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(CommerceTirePriceEntry commerceTirePriceEntry) {
		entityCache.removeResult(CommerceTirePriceEntryModelImpl.ENTITY_CACHE_ENABLED,
			CommerceTirePriceEntryImpl.class,
			commerceTirePriceEntry.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache((CommerceTirePriceEntryModelImpl)commerceTirePriceEntry,
			true);
	}

	@Override
	public void clearCache(
		List<CommerceTirePriceEntry> commerceTirePriceEntries) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (CommerceTirePriceEntry commerceTirePriceEntry : commerceTirePriceEntries) {
			entityCache.removeResult(CommerceTirePriceEntryModelImpl.ENTITY_CACHE_ENABLED,
				CommerceTirePriceEntryImpl.class,
				commerceTirePriceEntry.getPrimaryKey());

			clearUniqueFindersCache((CommerceTirePriceEntryModelImpl)commerceTirePriceEntry,
				true);
		}
	}

	protected void cacheUniqueFindersCache(
		CommerceTirePriceEntryModelImpl commerceTirePriceEntryModelImpl) {
		Object[] args = new Object[] {
				commerceTirePriceEntryModelImpl.getUuid(),
				commerceTirePriceEntryModelImpl.getGroupId()
			};

		finderCache.putResult(FINDER_PATH_COUNT_BY_UUID_G, args,
			Long.valueOf(1), false);
		finderCache.putResult(FINDER_PATH_FETCH_BY_UUID_G, args,
			commerceTirePriceEntryModelImpl, false);
	}

	protected void clearUniqueFindersCache(
		CommerceTirePriceEntryModelImpl commerceTirePriceEntryModelImpl,
		boolean clearCurrent) {
		if (clearCurrent) {
			Object[] args = new Object[] {
					commerceTirePriceEntryModelImpl.getUuid(),
					commerceTirePriceEntryModelImpl.getGroupId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID_G, args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_UUID_G, args);
		}

		if ((commerceTirePriceEntryModelImpl.getColumnBitmask() &
				FINDER_PATH_FETCH_BY_UUID_G.getColumnBitmask()) != 0) {
			Object[] args = new Object[] {
					commerceTirePriceEntryModelImpl.getOriginalUuid(),
					commerceTirePriceEntryModelImpl.getOriginalGroupId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID_G, args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_UUID_G, args);
		}
	}

	/**
	 * Creates a new commerce tire price entry with the primary key. Does not add the commerce tire price entry to the database.
	 *
	 * @param CommerceTirePriceEntryId the primary key for the new commerce tire price entry
	 * @return the new commerce tire price entry
	 */
	@Override
	public CommerceTirePriceEntry create(long CommerceTirePriceEntryId) {
		CommerceTirePriceEntry commerceTirePriceEntry = new CommerceTirePriceEntryImpl();

		commerceTirePriceEntry.setNew(true);
		commerceTirePriceEntry.setPrimaryKey(CommerceTirePriceEntryId);

		String uuid = PortalUUIDUtil.generate();

		commerceTirePriceEntry.setUuid(uuid);

		commerceTirePriceEntry.setCompanyId(companyProvider.getCompanyId());

		return commerceTirePriceEntry;
	}

	/**
	 * Removes the commerce tire price entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param CommerceTirePriceEntryId the primary key of the commerce tire price entry
	 * @return the commerce tire price entry that was removed
	 * @throws NoSuchTirePriceEntryException if a commerce tire price entry with the primary key could not be found
	 */
	@Override
	public CommerceTirePriceEntry remove(long CommerceTirePriceEntryId)
		throws NoSuchTirePriceEntryException {
		return remove((Serializable)CommerceTirePriceEntryId);
	}

	/**
	 * Removes the commerce tire price entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the commerce tire price entry
	 * @return the commerce tire price entry that was removed
	 * @throws NoSuchTirePriceEntryException if a commerce tire price entry with the primary key could not be found
	 */
	@Override
	public CommerceTirePriceEntry remove(Serializable primaryKey)
		throws NoSuchTirePriceEntryException {
		Session session = null;

		try {
			session = openSession();

			CommerceTirePriceEntry commerceTirePriceEntry = (CommerceTirePriceEntry)session.get(CommerceTirePriceEntryImpl.class,
					primaryKey);

			if (commerceTirePriceEntry == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchTirePriceEntryException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(commerceTirePriceEntry);
		}
		catch (NoSuchTirePriceEntryException nsee) {
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
	protected CommerceTirePriceEntry removeImpl(
		CommerceTirePriceEntry commerceTirePriceEntry) {
		commerceTirePriceEntry = toUnwrappedModel(commerceTirePriceEntry);

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(commerceTirePriceEntry)) {
				commerceTirePriceEntry = (CommerceTirePriceEntry)session.get(CommerceTirePriceEntryImpl.class,
						commerceTirePriceEntry.getPrimaryKeyObj());
			}

			if (commerceTirePriceEntry != null) {
				session.delete(commerceTirePriceEntry);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (commerceTirePriceEntry != null) {
			clearCache(commerceTirePriceEntry);
		}

		return commerceTirePriceEntry;
	}

	@Override
	public CommerceTirePriceEntry updateImpl(
		CommerceTirePriceEntry commerceTirePriceEntry) {
		commerceTirePriceEntry = toUnwrappedModel(commerceTirePriceEntry);

		boolean isNew = commerceTirePriceEntry.isNew();

		CommerceTirePriceEntryModelImpl commerceTirePriceEntryModelImpl = (CommerceTirePriceEntryModelImpl)commerceTirePriceEntry;

		if (Validator.isNull(commerceTirePriceEntry.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			commerceTirePriceEntry.setUuid(uuid);
		}

		ServiceContext serviceContext = ServiceContextThreadLocal.getServiceContext();

		Date now = new Date();

		if (isNew && (commerceTirePriceEntry.getCreateDate() == null)) {
			if (serviceContext == null) {
				commerceTirePriceEntry.setCreateDate(now);
			}
			else {
				commerceTirePriceEntry.setCreateDate(serviceContext.getCreateDate(
						now));
			}
		}

		if (!commerceTirePriceEntryModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				commerceTirePriceEntry.setModifiedDate(now);
			}
			else {
				commerceTirePriceEntry.setModifiedDate(serviceContext.getModifiedDate(
						now));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (commerceTirePriceEntry.isNew()) {
				session.save(commerceTirePriceEntry);

				commerceTirePriceEntry.setNew(false);
			}
			else {
				commerceTirePriceEntry = (CommerceTirePriceEntry)session.merge(commerceTirePriceEntry);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (!CommerceTirePriceEntryModelImpl.COLUMN_BITMASK_ENABLED) {
			finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}
		else
		 if (isNew) {
			Object[] args = new Object[] {
					commerceTirePriceEntryModelImpl.getUuid()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID, args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID,
				args);

			args = new Object[] {
					commerceTirePriceEntryModelImpl.getUuid(),
					commerceTirePriceEntryModelImpl.getCompanyId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID_C, args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C,
				args);

			args = new Object[] { commerceTirePriceEntryModelImpl.getGroupId() };

			finderCache.removeResult(FINDER_PATH_COUNT_BY_GROUPID, args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID,
				args);

			args = new Object[] { commerceTirePriceEntryModelImpl.getCompanyId() };

			finderCache.removeResult(FINDER_PATH_COUNT_BY_COMPANYID, args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID,
				args);

			args = new Object[] {
					commerceTirePriceEntryModelImpl.getCommercePriceEntryId()
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
			if ((commerceTirePriceEntryModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						commerceTirePriceEntryModelImpl.getOriginalUuid()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID,
					args);

				args = new Object[] { commerceTirePriceEntryModelImpl.getUuid() };

				finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID,
					args);
			}

			if ((commerceTirePriceEntryModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						commerceTirePriceEntryModelImpl.getOriginalUuid(),
						commerceTirePriceEntryModelImpl.getOriginalCompanyId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID_C, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C,
					args);

				args = new Object[] {
						commerceTirePriceEntryModelImpl.getUuid(),
						commerceTirePriceEntryModelImpl.getCompanyId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID_C, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C,
					args);
			}

			if ((commerceTirePriceEntryModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						commerceTirePriceEntryModelImpl.getOriginalGroupId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_GROUPID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID,
					args);

				args = new Object[] { commerceTirePriceEntryModelImpl.getGroupId() };

				finderCache.removeResult(FINDER_PATH_COUNT_BY_GROUPID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID,
					args);
			}

			if ((commerceTirePriceEntryModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						commerceTirePriceEntryModelImpl.getOriginalCompanyId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_COMPANYID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID,
					args);

				args = new Object[] {
						commerceTirePriceEntryModelImpl.getCompanyId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_COMPANYID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID,
					args);
			}

			if ((commerceTirePriceEntryModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMMERCEPRICEENTRYID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						commerceTirePriceEntryModelImpl.getOriginalCommercePriceEntryId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_COMMERCEPRICEENTRYID,
					args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMMERCEPRICEENTRYID,
					args);

				args = new Object[] {
						commerceTirePriceEntryModelImpl.getCommercePriceEntryId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_COMMERCEPRICEENTRYID,
					args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMMERCEPRICEENTRYID,
					args);
			}
		}

		entityCache.putResult(CommerceTirePriceEntryModelImpl.ENTITY_CACHE_ENABLED,
			CommerceTirePriceEntryImpl.class,
			commerceTirePriceEntry.getPrimaryKey(), commerceTirePriceEntry,
			false);

		clearUniqueFindersCache(commerceTirePriceEntryModelImpl, false);
		cacheUniqueFindersCache(commerceTirePriceEntryModelImpl);

		commerceTirePriceEntry.resetOriginalValues();

		return commerceTirePriceEntry;
	}

	protected CommerceTirePriceEntry toUnwrappedModel(
		CommerceTirePriceEntry commerceTirePriceEntry) {
		if (commerceTirePriceEntry instanceof CommerceTirePriceEntryImpl) {
			return commerceTirePriceEntry;
		}

		CommerceTirePriceEntryImpl commerceTirePriceEntryImpl = new CommerceTirePriceEntryImpl();

		commerceTirePriceEntryImpl.setNew(commerceTirePriceEntry.isNew());
		commerceTirePriceEntryImpl.setPrimaryKey(commerceTirePriceEntry.getPrimaryKey());

		commerceTirePriceEntryImpl.setUuid(commerceTirePriceEntry.getUuid());
		commerceTirePriceEntryImpl.setCommerceTirePriceEntryId(commerceTirePriceEntry.getCommerceTirePriceEntryId());
		commerceTirePriceEntryImpl.setGroupId(commerceTirePriceEntry.getGroupId());
		commerceTirePriceEntryImpl.setCompanyId(commerceTirePriceEntry.getCompanyId());
		commerceTirePriceEntryImpl.setUserId(commerceTirePriceEntry.getUserId());
		commerceTirePriceEntryImpl.setUserName(commerceTirePriceEntry.getUserName());
		commerceTirePriceEntryImpl.setCreateDate(commerceTirePriceEntry.getCreateDate());
		commerceTirePriceEntryImpl.setModifiedDate(commerceTirePriceEntry.getModifiedDate());
		commerceTirePriceEntryImpl.setCommercePriceEntryId(commerceTirePriceEntry.getCommercePriceEntryId());
		commerceTirePriceEntryImpl.setPrice(commerceTirePriceEntry.getPrice());
		commerceTirePriceEntryImpl.setMinQuantity(commerceTirePriceEntry.getMinQuantity());
		commerceTirePriceEntryImpl.setLastPublishDate(commerceTirePriceEntry.getLastPublishDate());

		return commerceTirePriceEntryImpl;
	}

	/**
	 * Returns the commerce tire price entry with the primary key or throws a {@link com.liferay.portal.kernel.exception.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the commerce tire price entry
	 * @return the commerce tire price entry
	 * @throws NoSuchTirePriceEntryException if a commerce tire price entry with the primary key could not be found
	 */
	@Override
	public CommerceTirePriceEntry findByPrimaryKey(Serializable primaryKey)
		throws NoSuchTirePriceEntryException {
		CommerceTirePriceEntry commerceTirePriceEntry = fetchByPrimaryKey(primaryKey);

		if (commerceTirePriceEntry == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchTirePriceEntryException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				primaryKey);
		}

		return commerceTirePriceEntry;
	}

	/**
	 * Returns the commerce tire price entry with the primary key or throws a {@link NoSuchTirePriceEntryException} if it could not be found.
	 *
	 * @param CommerceTirePriceEntryId the primary key of the commerce tire price entry
	 * @return the commerce tire price entry
	 * @throws NoSuchTirePriceEntryException if a commerce tire price entry with the primary key could not be found
	 */
	@Override
	public CommerceTirePriceEntry findByPrimaryKey(
		long CommerceTirePriceEntryId) throws NoSuchTirePriceEntryException {
		return findByPrimaryKey((Serializable)CommerceTirePriceEntryId);
	}

	/**
	 * Returns the commerce tire price entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the commerce tire price entry
	 * @return the commerce tire price entry, or <code>null</code> if a commerce tire price entry with the primary key could not be found
	 */
	@Override
	public CommerceTirePriceEntry fetchByPrimaryKey(Serializable primaryKey) {
		Serializable serializable = entityCache.getResult(CommerceTirePriceEntryModelImpl.ENTITY_CACHE_ENABLED,
				CommerceTirePriceEntryImpl.class, primaryKey);

		if (serializable == nullModel) {
			return null;
		}

		CommerceTirePriceEntry commerceTirePriceEntry = (CommerceTirePriceEntry)serializable;

		if (commerceTirePriceEntry == null) {
			Session session = null;

			try {
				session = openSession();

				commerceTirePriceEntry = (CommerceTirePriceEntry)session.get(CommerceTirePriceEntryImpl.class,
						primaryKey);

				if (commerceTirePriceEntry != null) {
					cacheResult(commerceTirePriceEntry);
				}
				else {
					entityCache.putResult(CommerceTirePriceEntryModelImpl.ENTITY_CACHE_ENABLED,
						CommerceTirePriceEntryImpl.class, primaryKey, nullModel);
				}
			}
			catch (Exception e) {
				entityCache.removeResult(CommerceTirePriceEntryModelImpl.ENTITY_CACHE_ENABLED,
					CommerceTirePriceEntryImpl.class, primaryKey);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return commerceTirePriceEntry;
	}

	/**
	 * Returns the commerce tire price entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param CommerceTirePriceEntryId the primary key of the commerce tire price entry
	 * @return the commerce tire price entry, or <code>null</code> if a commerce tire price entry with the primary key could not be found
	 */
	@Override
	public CommerceTirePriceEntry fetchByPrimaryKey(
		long CommerceTirePriceEntryId) {
		return fetchByPrimaryKey((Serializable)CommerceTirePriceEntryId);
	}

	@Override
	public Map<Serializable, CommerceTirePriceEntry> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {
		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, CommerceTirePriceEntry> map = new HashMap<Serializable, CommerceTirePriceEntry>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			CommerceTirePriceEntry commerceTirePriceEntry = fetchByPrimaryKey(primaryKey);

			if (commerceTirePriceEntry != null) {
				map.put(primaryKey, commerceTirePriceEntry);
			}

			return map;
		}

		Set<Serializable> uncachedPrimaryKeys = null;

		for (Serializable primaryKey : primaryKeys) {
			Serializable serializable = entityCache.getResult(CommerceTirePriceEntryModelImpl.ENTITY_CACHE_ENABLED,
					CommerceTirePriceEntryImpl.class, primaryKey);

			if (serializable != nullModel) {
				if (serializable == null) {
					if (uncachedPrimaryKeys == null) {
						uncachedPrimaryKeys = new HashSet<Serializable>();
					}

					uncachedPrimaryKeys.add(primaryKey);
				}
				else {
					map.put(primaryKey, (CommerceTirePriceEntry)serializable);
				}
			}
		}

		if (uncachedPrimaryKeys == null) {
			return map;
		}

		StringBundler query = new StringBundler((uncachedPrimaryKeys.size() * 2) +
				1);

		query.append(_SQL_SELECT_COMMERCETIREPRICEENTRY_WHERE_PKS_IN);

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

			for (CommerceTirePriceEntry commerceTirePriceEntry : (List<CommerceTirePriceEntry>)q.list()) {
				map.put(commerceTirePriceEntry.getPrimaryKeyObj(),
					commerceTirePriceEntry);

				cacheResult(commerceTirePriceEntry);

				uncachedPrimaryKeys.remove(commerceTirePriceEntry.getPrimaryKeyObj());
			}

			for (Serializable primaryKey : uncachedPrimaryKeys) {
				entityCache.putResult(CommerceTirePriceEntryModelImpl.ENTITY_CACHE_ENABLED,
					CommerceTirePriceEntryImpl.class, primaryKey, nullModel);
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
	 * Returns all the commerce tire price entries.
	 *
	 * @return the commerce tire price entries
	 */
	@Override
	public List<CommerceTirePriceEntry> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce tire price entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceTirePriceEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce tire price entries
	 * @param end the upper bound of the range of commerce tire price entries (not inclusive)
	 * @return the range of commerce tire price entries
	 */
	@Override
	public List<CommerceTirePriceEntry> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce tire price entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceTirePriceEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce tire price entries
	 * @param end the upper bound of the range of commerce tire price entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of commerce tire price entries
	 */
	@Override
	public List<CommerceTirePriceEntry> findAll(int start, int end,
		OrderByComparator<CommerceTirePriceEntry> orderByComparator) {
		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce tire price entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceTirePriceEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce tire price entries
	 * @param end the upper bound of the range of commerce tire price entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of commerce tire price entries
	 */
	@Override
	public List<CommerceTirePriceEntry> findAll(int start, int end,
		OrderByComparator<CommerceTirePriceEntry> orderByComparator,
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

		List<CommerceTirePriceEntry> list = null;

		if (retrieveFromCache) {
			list = (List<CommerceTirePriceEntry>)finderCache.getResult(finderPath,
					finderArgs, this);
		}

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 2));

				query.append(_SQL_SELECT_COMMERCETIREPRICEENTRY);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_COMMERCETIREPRICEENTRY;

				if (pagination) {
					sql = sql.concat(CommerceTirePriceEntryModelImpl.ORDER_BY_JPQL);
				}
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (!pagination) {
					list = (List<CommerceTirePriceEntry>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<CommerceTirePriceEntry>)QueryUtil.list(q,
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
	 * Removes all the commerce tire price entries from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (CommerceTirePriceEntry commerceTirePriceEntry : findAll()) {
			remove(commerceTirePriceEntry);
		}
	}

	/**
	 * Returns the number of commerce tire price entries.
	 *
	 * @return the number of commerce tire price entries
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_COMMERCETIREPRICEENTRY);

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
		return CommerceTirePriceEntryModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the commerce tire price entry persistence.
	 */
	public void afterPropertiesSet() {
	}

	public void destroy() {
		entityCache.removeCache(CommerceTirePriceEntryImpl.class.getName());
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
	private static final String _SQL_SELECT_COMMERCETIREPRICEENTRY = "SELECT commerceTirePriceEntry FROM CommerceTirePriceEntry commerceTirePriceEntry";
	private static final String _SQL_SELECT_COMMERCETIREPRICEENTRY_WHERE_PKS_IN = "SELECT commerceTirePriceEntry FROM CommerceTirePriceEntry commerceTirePriceEntry WHERE CommerceTirePriceEntryId IN (";
	private static final String _SQL_SELECT_COMMERCETIREPRICEENTRY_WHERE = "SELECT commerceTirePriceEntry FROM CommerceTirePriceEntry commerceTirePriceEntry WHERE ";
	private static final String _SQL_COUNT_COMMERCETIREPRICEENTRY = "SELECT COUNT(commerceTirePriceEntry) FROM CommerceTirePriceEntry commerceTirePriceEntry";
	private static final String _SQL_COUNT_COMMERCETIREPRICEENTRY_WHERE = "SELECT COUNT(commerceTirePriceEntry) FROM CommerceTirePriceEntry commerceTirePriceEntry WHERE ";
	private static final String _ORDER_BY_ENTITY_ALIAS = "commerceTirePriceEntry.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No CommerceTirePriceEntry exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No CommerceTirePriceEntry exists with the key {";
	private static final Log _log = LogFactoryUtil.getLog(CommerceTirePriceEntryPersistenceImpl.class);
	private static final Set<String> _badColumnNames = SetUtil.fromArray(new String[] {
				"uuid"
			});
}