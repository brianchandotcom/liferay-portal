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

package com.liferay.portal.tools.service.builder.test.service.persistence.impl;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.bean.BeanReference;
import com.liferay.portal.kernel.dao.orm.EntityCache;
import com.liferay.portal.kernel.dao.orm.FinderCache;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.service.persistence.impl.TableMapper;
import com.liferay.portal.kernel.service.persistence.impl.TableMapperFactory;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;
import com.liferay.portal.spring.extender.service.ServiceReference;
import com.liferay.portal.tools.service.builder.test.exception.NoSuchLVEntryException;
import com.liferay.portal.tools.service.builder.test.model.LVEntry;
import com.liferay.portal.tools.service.builder.test.model.impl.LVEntryImpl;
import com.liferay.portal.tools.service.builder.test.model.impl.LVEntryModelImpl;
import com.liferay.portal.tools.service.builder.test.service.persistence.BigDecimalEntryPersistence;
import com.liferay.portal.tools.service.builder.test.service.persistence.LVEntryPersistence;

import java.io.Serializable;

import java.lang.reflect.InvocationHandler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * The persistence implementation for the lv entry service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class LVEntryPersistenceImpl
	extends BasePersistenceImpl<LVEntry> implements LVEntryPersistence {

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>LVEntryUtil</code> to access the lv entry persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		LVEntryImpl.class.getName();

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
	 * Returns all the lv entries where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching lv entries
	 */
	@Override
	public List<LVEntry> findByUuid(String uuid) {
		return findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the lv entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LVEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of lv entries
	 * @param end the upper bound of the range of lv entries (not inclusive)
	 * @return the range of matching lv entries
	 */
	@Override
	public List<LVEntry> findByUuid(String uuid, int start, int end) {
		return findByUuid(uuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the lv entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LVEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of lv entries
	 * @param end the upper bound of the range of lv entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching lv entries
	 */
	@Override
	public List<LVEntry> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<LVEntry> orderByComparator) {

		return findByUuid(uuid, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the lv entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LVEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of lv entries
	 * @param end the upper bound of the range of lv entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching lv entries
	 */
	@Override
	public List<LVEntry> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<LVEntry> orderByComparator, boolean useFinderCache) {

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

		List<LVEntry> list = null;

		if (useFinderCache) {
			list = (List<LVEntry>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (LVEntry lvEntry : list) {
					if (!uuid.equals(lvEntry.getUuid())) {
						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(
					3 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				query = new StringBundler(3);
			}

			query.append(_SQL_SELECT_LVENTRY_WHERE);

			boolean bindUuid = false;

			if (uuid.isEmpty()) {
				query.append(_FINDER_COLUMN_UUID_UUID_3);
			}
			else {
				bindUuid = true;

				query.append(_FINDER_COLUMN_UUID_UUID_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				query.append(LVEntryModelImpl.ORDER_BY_JPQL);
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

				list = (List<LVEntry>)QueryUtil.list(
					q, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					finderCache.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception e) {
				if (useFinderCache) {
					finderCache.removeResult(finderPath, finderArgs);
				}

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first lv entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching lv entry
	 * @throws NoSuchLVEntryException if a matching lv entry could not be found
	 */
	@Override
	public LVEntry findByUuid_First(
			String uuid, OrderByComparator<LVEntry> orderByComparator)
		throws NoSuchLVEntryException {

		LVEntry lvEntry = fetchByUuid_First(uuid, orderByComparator);

		if (lvEntry != null) {
			return lvEntry;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append("}");

		throw new NoSuchLVEntryException(msg.toString());
	}

	/**
	 * Returns the first lv entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching lv entry, or <code>null</code> if a matching lv entry could not be found
	 */
	@Override
	public LVEntry fetchByUuid_First(
		String uuid, OrderByComparator<LVEntry> orderByComparator) {

		List<LVEntry> list = findByUuid(uuid, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last lv entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching lv entry
	 * @throws NoSuchLVEntryException if a matching lv entry could not be found
	 */
	@Override
	public LVEntry findByUuid_Last(
			String uuid, OrderByComparator<LVEntry> orderByComparator)
		throws NoSuchLVEntryException {

		LVEntry lvEntry = fetchByUuid_Last(uuid, orderByComparator);

		if (lvEntry != null) {
			return lvEntry;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append("}");

		throw new NoSuchLVEntryException(msg.toString());
	}

	/**
	 * Returns the last lv entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching lv entry, or <code>null</code> if a matching lv entry could not be found
	 */
	@Override
	public LVEntry fetchByUuid_Last(
		String uuid, OrderByComparator<LVEntry> orderByComparator) {

		int count = countByUuid(uuid);

		if (count == 0) {
			return null;
		}

		List<LVEntry> list = findByUuid(
			uuid, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the lv entries before and after the current lv entry in the ordered set where uuid = &#63;.
	 *
	 * @param lvEntryId the primary key of the current lv entry
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next lv entry
	 * @throws NoSuchLVEntryException if a lv entry with the primary key could not be found
	 */
	@Override
	public LVEntry[] findByUuid_PrevAndNext(
			long lvEntryId, String uuid,
			OrderByComparator<LVEntry> orderByComparator)
		throws NoSuchLVEntryException {

		uuid = Objects.toString(uuid, "");

		LVEntry lvEntry = findByPrimaryKey(lvEntryId);

		Session session = null;

		try {
			session = openSession();

			LVEntry[] array = new LVEntryImpl[3];

			array[0] = getByUuid_PrevAndNext(
				session, lvEntry, uuid, orderByComparator, true);

			array[1] = lvEntry;

			array[2] = getByUuid_PrevAndNext(
				session, lvEntry, uuid, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected LVEntry getByUuid_PrevAndNext(
		Session session, LVEntry lvEntry, String uuid,
		OrderByComparator<LVEntry> orderByComparator, boolean previous) {

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_LVENTRY_WHERE);

		boolean bindUuid = false;

		if (uuid.isEmpty()) {
			query.append(_FINDER_COLUMN_UUID_UUID_3);
		}
		else {
			bindUuid = true;

			query.append(_FINDER_COLUMN_UUID_UUID_2);
		}

		if (orderByComparator != null) {
			String[] orderByConditionFields =
				orderByComparator.getOrderByConditionFields();

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
			query.append(LVEntryModelImpl.ORDER_BY_JPQL);
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
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(lvEntry)) {

				qPos.add(orderByConditionValue);
			}
		}

		List<LVEntry> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the lv entries where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	@Override
	public void removeByUuid(String uuid) {
		for (LVEntry lvEntry :
				findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(lvEntry);
		}
	}

	/**
	 * Returns the number of lv entries where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching lv entries
	 */
	@Override
	public int countByUuid(String uuid) {
		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = _finderPathCountByUuid;

		Object[] finderArgs = new Object[] {uuid};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_LVENTRY_WHERE);

			boolean bindUuid = false;

			if (uuid.isEmpty()) {
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

	private static final String _FINDER_COLUMN_UUID_UUID_2 = "lvEntry.uuid = ?";

	private static final String _FINDER_COLUMN_UUID_UUID_3 =
		"(lvEntry.uuid IS NULL OR lvEntry.uuid = '')";

	private FinderPath _finderPathWithPaginationFindByUuid_Head;
	private FinderPath _finderPathWithoutPaginationFindByUuid_Head;
	private FinderPath _finderPathCountByUuid_Head;

	/**
	 * Returns all the lv entries where uuid = &#63; and head = &#63;.
	 *
	 * @param uuid the uuid
	 * @param head the head
	 * @return the matching lv entries
	 */
	@Override
	public List<LVEntry> findByUuid_Head(String uuid, boolean head) {
		return findByUuid_Head(
			uuid, head, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the lv entries where uuid = &#63; and head = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LVEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param head the head
	 * @param start the lower bound of the range of lv entries
	 * @param end the upper bound of the range of lv entries (not inclusive)
	 * @return the range of matching lv entries
	 */
	@Override
	public List<LVEntry> findByUuid_Head(
		String uuid, boolean head, int start, int end) {

		return findByUuid_Head(uuid, head, start, end, null);
	}

	/**
	 * Returns an ordered range of all the lv entries where uuid = &#63; and head = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LVEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param head the head
	 * @param start the lower bound of the range of lv entries
	 * @param end the upper bound of the range of lv entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching lv entries
	 */
	@Override
	public List<LVEntry> findByUuid_Head(
		String uuid, boolean head, int start, int end,
		OrderByComparator<LVEntry> orderByComparator) {

		return findByUuid_Head(uuid, head, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the lv entries where uuid = &#63; and head = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LVEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param head the head
	 * @param start the lower bound of the range of lv entries
	 * @param end the upper bound of the range of lv entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching lv entries
	 */
	@Override
	public List<LVEntry> findByUuid_Head(
		String uuid, boolean head, int start, int end,
		OrderByComparator<LVEntry> orderByComparator, boolean useFinderCache) {

		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByUuid_Head;
				finderArgs = new Object[] {uuid, head};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByUuid_Head;
			finderArgs = new Object[] {
				uuid, head, start, end, orderByComparator
			};
		}

		List<LVEntry> list = null;

		if (useFinderCache) {
			list = (List<LVEntry>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (LVEntry lvEntry : list) {
					if (!uuid.equals(lvEntry.getUuid()) ||
						(head != lvEntry.isHead())) {

						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(
					4 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				query = new StringBundler(4);
			}

			query.append(_SQL_SELECT_LVENTRY_WHERE);

			boolean bindUuid = false;

			if (uuid.isEmpty()) {
				query.append(_FINDER_COLUMN_UUID_HEAD_UUID_3);
			}
			else {
				bindUuid = true;

				query.append(_FINDER_COLUMN_UUID_HEAD_UUID_2);
			}

			query.append(_FINDER_COLUMN_UUID_HEAD_HEAD_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				query.append(LVEntryModelImpl.ORDER_BY_JPQL);
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

				qPos.add(head);

				list = (List<LVEntry>)QueryUtil.list(
					q, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					finderCache.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception e) {
				if (useFinderCache) {
					finderCache.removeResult(finderPath, finderArgs);
				}

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first lv entry in the ordered set where uuid = &#63; and head = &#63;.
	 *
	 * @param uuid the uuid
	 * @param head the head
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching lv entry
	 * @throws NoSuchLVEntryException if a matching lv entry could not be found
	 */
	@Override
	public LVEntry findByUuid_Head_First(
			String uuid, boolean head,
			OrderByComparator<LVEntry> orderByComparator)
		throws NoSuchLVEntryException {

		LVEntry lvEntry = fetchByUuid_Head_First(uuid, head, orderByComparator);

		if (lvEntry != null) {
			return lvEntry;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(", head=");
		msg.append(head);

		msg.append("}");

		throw new NoSuchLVEntryException(msg.toString());
	}

	/**
	 * Returns the first lv entry in the ordered set where uuid = &#63; and head = &#63;.
	 *
	 * @param uuid the uuid
	 * @param head the head
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching lv entry, or <code>null</code> if a matching lv entry could not be found
	 */
	@Override
	public LVEntry fetchByUuid_Head_First(
		String uuid, boolean head,
		OrderByComparator<LVEntry> orderByComparator) {

		List<LVEntry> list = findByUuid_Head(
			uuid, head, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last lv entry in the ordered set where uuid = &#63; and head = &#63;.
	 *
	 * @param uuid the uuid
	 * @param head the head
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching lv entry
	 * @throws NoSuchLVEntryException if a matching lv entry could not be found
	 */
	@Override
	public LVEntry findByUuid_Head_Last(
			String uuid, boolean head,
			OrderByComparator<LVEntry> orderByComparator)
		throws NoSuchLVEntryException {

		LVEntry lvEntry = fetchByUuid_Head_Last(uuid, head, orderByComparator);

		if (lvEntry != null) {
			return lvEntry;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(", head=");
		msg.append(head);

		msg.append("}");

		throw new NoSuchLVEntryException(msg.toString());
	}

	/**
	 * Returns the last lv entry in the ordered set where uuid = &#63; and head = &#63;.
	 *
	 * @param uuid the uuid
	 * @param head the head
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching lv entry, or <code>null</code> if a matching lv entry could not be found
	 */
	@Override
	public LVEntry fetchByUuid_Head_Last(
		String uuid, boolean head,
		OrderByComparator<LVEntry> orderByComparator) {

		int count = countByUuid_Head(uuid, head);

		if (count == 0) {
			return null;
		}

		List<LVEntry> list = findByUuid_Head(
			uuid, head, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the lv entries before and after the current lv entry in the ordered set where uuid = &#63; and head = &#63;.
	 *
	 * @param lvEntryId the primary key of the current lv entry
	 * @param uuid the uuid
	 * @param head the head
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next lv entry
	 * @throws NoSuchLVEntryException if a lv entry with the primary key could not be found
	 */
	@Override
	public LVEntry[] findByUuid_Head_PrevAndNext(
			long lvEntryId, String uuid, boolean head,
			OrderByComparator<LVEntry> orderByComparator)
		throws NoSuchLVEntryException {

		uuid = Objects.toString(uuid, "");

		LVEntry lvEntry = findByPrimaryKey(lvEntryId);

		Session session = null;

		try {
			session = openSession();

			LVEntry[] array = new LVEntryImpl[3];

			array[0] = getByUuid_Head_PrevAndNext(
				session, lvEntry, uuid, head, orderByComparator, true);

			array[1] = lvEntry;

			array[2] = getByUuid_Head_PrevAndNext(
				session, lvEntry, uuid, head, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected LVEntry getByUuid_Head_PrevAndNext(
		Session session, LVEntry lvEntry, String uuid, boolean head,
		OrderByComparator<LVEntry> orderByComparator, boolean previous) {

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(
				5 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(4);
		}

		query.append(_SQL_SELECT_LVENTRY_WHERE);

		boolean bindUuid = false;

		if (uuid.isEmpty()) {
			query.append(_FINDER_COLUMN_UUID_HEAD_UUID_3);
		}
		else {
			bindUuid = true;

			query.append(_FINDER_COLUMN_UUID_HEAD_UUID_2);
		}

		query.append(_FINDER_COLUMN_UUID_HEAD_HEAD_2);

		if (orderByComparator != null) {
			String[] orderByConditionFields =
				orderByComparator.getOrderByConditionFields();

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
			query.append(LVEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		if (bindUuid) {
			qPos.add(uuid);
		}

		qPos.add(head);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(lvEntry)) {

				qPos.add(orderByConditionValue);
			}
		}

		List<LVEntry> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the lv entries where uuid = &#63; and head = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param head the head
	 */
	@Override
	public void removeByUuid_Head(String uuid, boolean head) {
		for (LVEntry lvEntry :
				findByUuid_Head(
					uuid, head, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(lvEntry);
		}
	}

	/**
	 * Returns the number of lv entries where uuid = &#63; and head = &#63;.
	 *
	 * @param uuid the uuid
	 * @param head the head
	 * @return the number of matching lv entries
	 */
	@Override
	public int countByUuid_Head(String uuid, boolean head) {
		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = _finderPathCountByUuid_Head;

		Object[] finderArgs = new Object[] {uuid, head};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_LVENTRY_WHERE);

			boolean bindUuid = false;

			if (uuid.isEmpty()) {
				query.append(_FINDER_COLUMN_UUID_HEAD_UUID_3);
			}
			else {
				bindUuid = true;

				query.append(_FINDER_COLUMN_UUID_HEAD_UUID_2);
			}

			query.append(_FINDER_COLUMN_UUID_HEAD_HEAD_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindUuid) {
					qPos.add(uuid);
				}

				qPos.add(head);

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

	private static final String _FINDER_COLUMN_UUID_HEAD_UUID_2 =
		"lvEntry.uuid = ? AND ";

	private static final String _FINDER_COLUMN_UUID_HEAD_UUID_3 =
		"(lvEntry.uuid IS NULL OR lvEntry.uuid = '') AND ";

	private static final String _FINDER_COLUMN_UUID_HEAD_HEAD_2 =
		"lvEntry.head = ?";

	private FinderPath _finderPathWithPaginationFindByUUID_G;
	private FinderPath _finderPathWithoutPaginationFindByUUID_G;
	private FinderPath _finderPathCountByUUID_G;

	/**
	 * Returns all the lv entries where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching lv entries
	 */
	@Override
	public List<LVEntry> findByUUID_G(String uuid, long groupId) {
		return findByUUID_G(
			uuid, groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the lv entries where uuid = &#63; and groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LVEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param start the lower bound of the range of lv entries
	 * @param end the upper bound of the range of lv entries (not inclusive)
	 * @return the range of matching lv entries
	 */
	@Override
	public List<LVEntry> findByUUID_G(
		String uuid, long groupId, int start, int end) {

		return findByUUID_G(uuid, groupId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the lv entries where uuid = &#63; and groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LVEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param start the lower bound of the range of lv entries
	 * @param end the upper bound of the range of lv entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching lv entries
	 */
	@Override
	public List<LVEntry> findByUUID_G(
		String uuid, long groupId, int start, int end,
		OrderByComparator<LVEntry> orderByComparator) {

		return findByUUID_G(uuid, groupId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the lv entries where uuid = &#63; and groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LVEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param start the lower bound of the range of lv entries
	 * @param end the upper bound of the range of lv entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching lv entries
	 */
	@Override
	public List<LVEntry> findByUUID_G(
		String uuid, long groupId, int start, int end,
		OrderByComparator<LVEntry> orderByComparator, boolean useFinderCache) {

		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByUUID_G;
				finderArgs = new Object[] {uuid, groupId};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByUUID_G;
			finderArgs = new Object[] {
				uuid, groupId, start, end, orderByComparator
			};
		}

		List<LVEntry> list = null;

		if (useFinderCache) {
			list = (List<LVEntry>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (LVEntry lvEntry : list) {
					if (!uuid.equals(lvEntry.getUuid()) ||
						(groupId != lvEntry.getGroupId())) {

						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(
					4 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				query = new StringBundler(4);
			}

			query.append(_SQL_SELECT_LVENTRY_WHERE);

			boolean bindUuid = false;

			if (uuid.isEmpty()) {
				query.append(_FINDER_COLUMN_UUID_G_UUID_3);
			}
			else {
				bindUuid = true;

				query.append(_FINDER_COLUMN_UUID_G_UUID_2);
			}

			query.append(_FINDER_COLUMN_UUID_G_GROUPID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				query.append(LVEntryModelImpl.ORDER_BY_JPQL);
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

				qPos.add(groupId);

				list = (List<LVEntry>)QueryUtil.list(
					q, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					finderCache.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception e) {
				if (useFinderCache) {
					finderCache.removeResult(finderPath, finderArgs);
				}

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first lv entry in the ordered set where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching lv entry
	 * @throws NoSuchLVEntryException if a matching lv entry could not be found
	 */
	@Override
	public LVEntry findByUUID_G_First(
			String uuid, long groupId,
			OrderByComparator<LVEntry> orderByComparator)
		throws NoSuchLVEntryException {

		LVEntry lvEntry = fetchByUUID_G_First(uuid, groupId, orderByComparator);

		if (lvEntry != null) {
			return lvEntry;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(", groupId=");
		msg.append(groupId);

		msg.append("}");

		throw new NoSuchLVEntryException(msg.toString());
	}

	/**
	 * Returns the first lv entry in the ordered set where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching lv entry, or <code>null</code> if a matching lv entry could not be found
	 */
	@Override
	public LVEntry fetchByUUID_G_First(
		String uuid, long groupId,
		OrderByComparator<LVEntry> orderByComparator) {

		List<LVEntry> list = findByUUID_G(
			uuid, groupId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last lv entry in the ordered set where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching lv entry
	 * @throws NoSuchLVEntryException if a matching lv entry could not be found
	 */
	@Override
	public LVEntry findByUUID_G_Last(
			String uuid, long groupId,
			OrderByComparator<LVEntry> orderByComparator)
		throws NoSuchLVEntryException {

		LVEntry lvEntry = fetchByUUID_G_Last(uuid, groupId, orderByComparator);

		if (lvEntry != null) {
			return lvEntry;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(", groupId=");
		msg.append(groupId);

		msg.append("}");

		throw new NoSuchLVEntryException(msg.toString());
	}

	/**
	 * Returns the last lv entry in the ordered set where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching lv entry, or <code>null</code> if a matching lv entry could not be found
	 */
	@Override
	public LVEntry fetchByUUID_G_Last(
		String uuid, long groupId,
		OrderByComparator<LVEntry> orderByComparator) {

		int count = countByUUID_G(uuid, groupId);

		if (count == 0) {
			return null;
		}

		List<LVEntry> list = findByUUID_G(
			uuid, groupId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the lv entries before and after the current lv entry in the ordered set where uuid = &#63; and groupId = &#63;.
	 *
	 * @param lvEntryId the primary key of the current lv entry
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next lv entry
	 * @throws NoSuchLVEntryException if a lv entry with the primary key could not be found
	 */
	@Override
	public LVEntry[] findByUUID_G_PrevAndNext(
			long lvEntryId, String uuid, long groupId,
			OrderByComparator<LVEntry> orderByComparator)
		throws NoSuchLVEntryException {

		uuid = Objects.toString(uuid, "");

		LVEntry lvEntry = findByPrimaryKey(lvEntryId);

		Session session = null;

		try {
			session = openSession();

			LVEntry[] array = new LVEntryImpl[3];

			array[0] = getByUUID_G_PrevAndNext(
				session, lvEntry, uuid, groupId, orderByComparator, true);

			array[1] = lvEntry;

			array[2] = getByUUID_G_PrevAndNext(
				session, lvEntry, uuid, groupId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected LVEntry getByUUID_G_PrevAndNext(
		Session session, LVEntry lvEntry, String uuid, long groupId,
		OrderByComparator<LVEntry> orderByComparator, boolean previous) {

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(
				5 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(4);
		}

		query.append(_SQL_SELECT_LVENTRY_WHERE);

		boolean bindUuid = false;

		if (uuid.isEmpty()) {
			query.append(_FINDER_COLUMN_UUID_G_UUID_3);
		}
		else {
			bindUuid = true;

			query.append(_FINDER_COLUMN_UUID_G_UUID_2);
		}

		query.append(_FINDER_COLUMN_UUID_G_GROUPID_2);

		if (orderByComparator != null) {
			String[] orderByConditionFields =
				orderByComparator.getOrderByConditionFields();

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
			query.append(LVEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		if (bindUuid) {
			qPos.add(uuid);
		}

		qPos.add(groupId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(lvEntry)) {

				qPos.add(orderByConditionValue);
			}
		}

		List<LVEntry> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the lv entries where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 */
	@Override
	public void removeByUUID_G(String uuid, long groupId) {
		for (LVEntry lvEntry :
				findByUUID_G(
					uuid, groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(lvEntry);
		}
	}

	/**
	 * Returns the number of lv entries where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching lv entries
	 */
	@Override
	public int countByUUID_G(String uuid, long groupId) {
		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = _finderPathCountByUUID_G;

		Object[] finderArgs = new Object[] {uuid, groupId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_LVENTRY_WHERE);

			boolean bindUuid = false;

			if (uuid.isEmpty()) {
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

	private static final String _FINDER_COLUMN_UUID_G_UUID_2 =
		"lvEntry.uuid = ? AND ";

	private static final String _FINDER_COLUMN_UUID_G_UUID_3 =
		"(lvEntry.uuid IS NULL OR lvEntry.uuid = '') AND ";

	private static final String _FINDER_COLUMN_UUID_G_GROUPID_2 =
		"lvEntry.groupId = ?";

	private FinderPath _finderPathFetchByUUID_G_Head;
	private FinderPath _finderPathCountByUUID_G_Head;

	/**
	 * Returns the lv entry where uuid = &#63; and groupId = &#63; and head = &#63; or throws a <code>NoSuchLVEntryException</code> if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param head the head
	 * @return the matching lv entry
	 * @throws NoSuchLVEntryException if a matching lv entry could not be found
	 */
	@Override
	public LVEntry findByUUID_G_Head(String uuid, long groupId, boolean head)
		throws NoSuchLVEntryException {

		LVEntry lvEntry = fetchByUUID_G_Head(uuid, groupId, head);

		if (lvEntry == null) {
			StringBundler msg = new StringBundler(8);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("uuid=");
			msg.append(uuid);

			msg.append(", groupId=");
			msg.append(groupId);

			msg.append(", head=");
			msg.append(head);

			msg.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(msg.toString());
			}

			throw new NoSuchLVEntryException(msg.toString());
		}

		return lvEntry;
	}

	/**
	 * Returns the lv entry where uuid = &#63; and groupId = &#63; and head = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param head the head
	 * @return the matching lv entry, or <code>null</code> if a matching lv entry could not be found
	 */
	@Override
	public LVEntry fetchByUUID_G_Head(String uuid, long groupId, boolean head) {
		return fetchByUUID_G_Head(uuid, groupId, head, true);
	}

	/**
	 * Returns the lv entry where uuid = &#63; and groupId = &#63; and head = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param head the head
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching lv entry, or <code>null</code> if a matching lv entry could not be found
	 */
	@Override
	public LVEntry fetchByUUID_G_Head(
		String uuid, long groupId, boolean head, boolean useFinderCache) {

		uuid = Objects.toString(uuid, "");

		Object[] finderArgs = null;

		if (useFinderCache) {
			finderArgs = new Object[] {uuid, groupId, head};
		}

		Object result = null;

		if (useFinderCache) {
			result = finderCache.getResult(
				_finderPathFetchByUUID_G_Head, finderArgs, this);
		}

		if (result instanceof LVEntry) {
			LVEntry lvEntry = (LVEntry)result;

			if (!Objects.equals(uuid, lvEntry.getUuid()) ||
				(groupId != lvEntry.getGroupId()) ||
				(head != lvEntry.isHead())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(5);

			query.append(_SQL_SELECT_LVENTRY_WHERE);

			boolean bindUuid = false;

			if (uuid.isEmpty()) {
				query.append(_FINDER_COLUMN_UUID_G_HEAD_UUID_3);
			}
			else {
				bindUuid = true;

				query.append(_FINDER_COLUMN_UUID_G_HEAD_UUID_2);
			}

			query.append(_FINDER_COLUMN_UUID_G_HEAD_GROUPID_2);

			query.append(_FINDER_COLUMN_UUID_G_HEAD_HEAD_2);

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

				qPos.add(head);

				List<LVEntry> list = q.list();

				if (list.isEmpty()) {
					if (useFinderCache) {
						finderCache.putResult(
							_finderPathFetchByUUID_G_Head, finderArgs, list);
					}
				}
				else {
					LVEntry lvEntry = list.get(0);

					result = lvEntry;

					cacheResult(lvEntry);
				}
			}
			catch (Exception e) {
				if (useFinderCache) {
					finderCache.removeResult(
						_finderPathFetchByUUID_G_Head, finderArgs);
				}

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
			return (LVEntry)result;
		}
	}

	/**
	 * Removes the lv entry where uuid = &#63; and groupId = &#63; and head = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param head the head
	 * @return the lv entry that was removed
	 */
	@Override
	public LVEntry removeByUUID_G_Head(String uuid, long groupId, boolean head)
		throws NoSuchLVEntryException {

		LVEntry lvEntry = findByUUID_G_Head(uuid, groupId, head);

		return remove(lvEntry);
	}

	/**
	 * Returns the number of lv entries where uuid = &#63; and groupId = &#63; and head = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param head the head
	 * @return the number of matching lv entries
	 */
	@Override
	public int countByUUID_G_Head(String uuid, long groupId, boolean head) {
		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = _finderPathCountByUUID_G_Head;

		Object[] finderArgs = new Object[] {uuid, groupId, head};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_COUNT_LVENTRY_WHERE);

			boolean bindUuid = false;

			if (uuid.isEmpty()) {
				query.append(_FINDER_COLUMN_UUID_G_HEAD_UUID_3);
			}
			else {
				bindUuid = true;

				query.append(_FINDER_COLUMN_UUID_G_HEAD_UUID_2);
			}

			query.append(_FINDER_COLUMN_UUID_G_HEAD_GROUPID_2);

			query.append(_FINDER_COLUMN_UUID_G_HEAD_HEAD_2);

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

				qPos.add(head);

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

	private static final String _FINDER_COLUMN_UUID_G_HEAD_UUID_2 =
		"lvEntry.uuid = ? AND ";

	private static final String _FINDER_COLUMN_UUID_G_HEAD_UUID_3 =
		"(lvEntry.uuid IS NULL OR lvEntry.uuid = '') AND ";

	private static final String _FINDER_COLUMN_UUID_G_HEAD_GROUPID_2 =
		"lvEntry.groupId = ? AND ";

	private static final String _FINDER_COLUMN_UUID_G_HEAD_HEAD_2 =
		"lvEntry.head = ?";

	private FinderPath _finderPathWithPaginationFindByUuid_C;
	private FinderPath _finderPathWithoutPaginationFindByUuid_C;
	private FinderPath _finderPathCountByUuid_C;

	/**
	 * Returns all the lv entries where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching lv entries
	 */
	@Override
	public List<LVEntry> findByUuid_C(String uuid, long companyId) {
		return findByUuid_C(
			uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the lv entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LVEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of lv entries
	 * @param end the upper bound of the range of lv entries (not inclusive)
	 * @return the range of matching lv entries
	 */
	@Override
	public List<LVEntry> findByUuid_C(
		String uuid, long companyId, int start, int end) {

		return findByUuid_C(uuid, companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the lv entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LVEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of lv entries
	 * @param end the upper bound of the range of lv entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching lv entries
	 */
	@Override
	public List<LVEntry> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<LVEntry> orderByComparator) {

		return findByUuid_C(
			uuid, companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the lv entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LVEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of lv entries
	 * @param end the upper bound of the range of lv entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching lv entries
	 */
	@Override
	public List<LVEntry> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<LVEntry> orderByComparator, boolean useFinderCache) {

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

		List<LVEntry> list = null;

		if (useFinderCache) {
			list = (List<LVEntry>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (LVEntry lvEntry : list) {
					if (!uuid.equals(lvEntry.getUuid()) ||
						(companyId != lvEntry.getCompanyId())) {

						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(
					4 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				query = new StringBundler(4);
			}

			query.append(_SQL_SELECT_LVENTRY_WHERE);

			boolean bindUuid = false;

			if (uuid.isEmpty()) {
				query.append(_FINDER_COLUMN_UUID_C_UUID_3);
			}
			else {
				bindUuid = true;

				query.append(_FINDER_COLUMN_UUID_C_UUID_2);
			}

			query.append(_FINDER_COLUMN_UUID_C_COMPANYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				query.append(LVEntryModelImpl.ORDER_BY_JPQL);
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

				list = (List<LVEntry>)QueryUtil.list(
					q, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					finderCache.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception e) {
				if (useFinderCache) {
					finderCache.removeResult(finderPath, finderArgs);
				}

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first lv entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching lv entry
	 * @throws NoSuchLVEntryException if a matching lv entry could not be found
	 */
	@Override
	public LVEntry findByUuid_C_First(
			String uuid, long companyId,
			OrderByComparator<LVEntry> orderByComparator)
		throws NoSuchLVEntryException {

		LVEntry lvEntry = fetchByUuid_C_First(
			uuid, companyId, orderByComparator);

		if (lvEntry != null) {
			return lvEntry;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(", companyId=");
		msg.append(companyId);

		msg.append("}");

		throw new NoSuchLVEntryException(msg.toString());
	}

	/**
	 * Returns the first lv entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching lv entry, or <code>null</code> if a matching lv entry could not be found
	 */
	@Override
	public LVEntry fetchByUuid_C_First(
		String uuid, long companyId,
		OrderByComparator<LVEntry> orderByComparator) {

		List<LVEntry> list = findByUuid_C(
			uuid, companyId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last lv entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching lv entry
	 * @throws NoSuchLVEntryException if a matching lv entry could not be found
	 */
	@Override
	public LVEntry findByUuid_C_Last(
			String uuid, long companyId,
			OrderByComparator<LVEntry> orderByComparator)
		throws NoSuchLVEntryException {

		LVEntry lvEntry = fetchByUuid_C_Last(
			uuid, companyId, orderByComparator);

		if (lvEntry != null) {
			return lvEntry;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(", companyId=");
		msg.append(companyId);

		msg.append("}");

		throw new NoSuchLVEntryException(msg.toString());
	}

	/**
	 * Returns the last lv entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching lv entry, or <code>null</code> if a matching lv entry could not be found
	 */
	@Override
	public LVEntry fetchByUuid_C_Last(
		String uuid, long companyId,
		OrderByComparator<LVEntry> orderByComparator) {

		int count = countByUuid_C(uuid, companyId);

		if (count == 0) {
			return null;
		}

		List<LVEntry> list = findByUuid_C(
			uuid, companyId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the lv entries before and after the current lv entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param lvEntryId the primary key of the current lv entry
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next lv entry
	 * @throws NoSuchLVEntryException if a lv entry with the primary key could not be found
	 */
	@Override
	public LVEntry[] findByUuid_C_PrevAndNext(
			long lvEntryId, String uuid, long companyId,
			OrderByComparator<LVEntry> orderByComparator)
		throws NoSuchLVEntryException {

		uuid = Objects.toString(uuid, "");

		LVEntry lvEntry = findByPrimaryKey(lvEntryId);

		Session session = null;

		try {
			session = openSession();

			LVEntry[] array = new LVEntryImpl[3];

			array[0] = getByUuid_C_PrevAndNext(
				session, lvEntry, uuid, companyId, orderByComparator, true);

			array[1] = lvEntry;

			array[2] = getByUuid_C_PrevAndNext(
				session, lvEntry, uuid, companyId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected LVEntry getByUuid_C_PrevAndNext(
		Session session, LVEntry lvEntry, String uuid, long companyId,
		OrderByComparator<LVEntry> orderByComparator, boolean previous) {

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(
				5 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(4);
		}

		query.append(_SQL_SELECT_LVENTRY_WHERE);

		boolean bindUuid = false;

		if (uuid.isEmpty()) {
			query.append(_FINDER_COLUMN_UUID_C_UUID_3);
		}
		else {
			bindUuid = true;

			query.append(_FINDER_COLUMN_UUID_C_UUID_2);
		}

		query.append(_FINDER_COLUMN_UUID_C_COMPANYID_2);

		if (orderByComparator != null) {
			String[] orderByConditionFields =
				orderByComparator.getOrderByConditionFields();

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
			query.append(LVEntryModelImpl.ORDER_BY_JPQL);
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
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(lvEntry)) {

				qPos.add(orderByConditionValue);
			}
		}

		List<LVEntry> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the lv entries where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	@Override
	public void removeByUuid_C(String uuid, long companyId) {
		for (LVEntry lvEntry :
				findByUuid_C(
					uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(lvEntry);
		}
	}

	/**
	 * Returns the number of lv entries where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching lv entries
	 */
	@Override
	public int countByUuid_C(String uuid, long companyId) {
		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = _finderPathCountByUuid_C;

		Object[] finderArgs = new Object[] {uuid, companyId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_LVENTRY_WHERE);

			boolean bindUuid = false;

			if (uuid.isEmpty()) {
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

	private static final String _FINDER_COLUMN_UUID_C_UUID_2 =
		"lvEntry.uuid = ? AND ";

	private static final String _FINDER_COLUMN_UUID_C_UUID_3 =
		"(lvEntry.uuid IS NULL OR lvEntry.uuid = '') AND ";

	private static final String _FINDER_COLUMN_UUID_C_COMPANYID_2 =
		"lvEntry.companyId = ?";

	private FinderPath _finderPathWithPaginationFindByUuid_C_Head;
	private FinderPath _finderPathWithoutPaginationFindByUuid_C_Head;
	private FinderPath _finderPathCountByUuid_C_Head;

	/**
	 * Returns all the lv entries where uuid = &#63; and companyId = &#63; and head = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param head the head
	 * @return the matching lv entries
	 */
	@Override
	public List<LVEntry> findByUuid_C_Head(
		String uuid, long companyId, boolean head) {

		return findByUuid_C_Head(
			uuid, companyId, head, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the lv entries where uuid = &#63; and companyId = &#63; and head = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LVEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param head the head
	 * @param start the lower bound of the range of lv entries
	 * @param end the upper bound of the range of lv entries (not inclusive)
	 * @return the range of matching lv entries
	 */
	@Override
	public List<LVEntry> findByUuid_C_Head(
		String uuid, long companyId, boolean head, int start, int end) {

		return findByUuid_C_Head(uuid, companyId, head, start, end, null);
	}

	/**
	 * Returns an ordered range of all the lv entries where uuid = &#63; and companyId = &#63; and head = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LVEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param head the head
	 * @param start the lower bound of the range of lv entries
	 * @param end the upper bound of the range of lv entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching lv entries
	 */
	@Override
	public List<LVEntry> findByUuid_C_Head(
		String uuid, long companyId, boolean head, int start, int end,
		OrderByComparator<LVEntry> orderByComparator) {

		return findByUuid_C_Head(
			uuid, companyId, head, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the lv entries where uuid = &#63; and companyId = &#63; and head = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LVEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param head the head
	 * @param start the lower bound of the range of lv entries
	 * @param end the upper bound of the range of lv entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching lv entries
	 */
	@Override
	public List<LVEntry> findByUuid_C_Head(
		String uuid, long companyId, boolean head, int start, int end,
		OrderByComparator<LVEntry> orderByComparator, boolean useFinderCache) {

		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByUuid_C_Head;
				finderArgs = new Object[] {uuid, companyId, head};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByUuid_C_Head;
			finderArgs = new Object[] {
				uuid, companyId, head, start, end, orderByComparator
			};
		}

		List<LVEntry> list = null;

		if (useFinderCache) {
			list = (List<LVEntry>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (LVEntry lvEntry : list) {
					if (!uuid.equals(lvEntry.getUuid()) ||
						(companyId != lvEntry.getCompanyId()) ||
						(head != lvEntry.isHead())) {

						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(
					5 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				query = new StringBundler(5);
			}

			query.append(_SQL_SELECT_LVENTRY_WHERE);

			boolean bindUuid = false;

			if (uuid.isEmpty()) {
				query.append(_FINDER_COLUMN_UUID_C_HEAD_UUID_3);
			}
			else {
				bindUuid = true;

				query.append(_FINDER_COLUMN_UUID_C_HEAD_UUID_2);
			}

			query.append(_FINDER_COLUMN_UUID_C_HEAD_COMPANYID_2);

			query.append(_FINDER_COLUMN_UUID_C_HEAD_HEAD_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				query.append(LVEntryModelImpl.ORDER_BY_JPQL);
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

				qPos.add(head);

				list = (List<LVEntry>)QueryUtil.list(
					q, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					finderCache.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception e) {
				if (useFinderCache) {
					finderCache.removeResult(finderPath, finderArgs);
				}

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first lv entry in the ordered set where uuid = &#63; and companyId = &#63; and head = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param head the head
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching lv entry
	 * @throws NoSuchLVEntryException if a matching lv entry could not be found
	 */
	@Override
	public LVEntry findByUuid_C_Head_First(
			String uuid, long companyId, boolean head,
			OrderByComparator<LVEntry> orderByComparator)
		throws NoSuchLVEntryException {

		LVEntry lvEntry = fetchByUuid_C_Head_First(
			uuid, companyId, head, orderByComparator);

		if (lvEntry != null) {
			return lvEntry;
		}

		StringBundler msg = new StringBundler(8);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(", companyId=");
		msg.append(companyId);

		msg.append(", head=");
		msg.append(head);

		msg.append("}");

		throw new NoSuchLVEntryException(msg.toString());
	}

	/**
	 * Returns the first lv entry in the ordered set where uuid = &#63; and companyId = &#63; and head = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param head the head
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching lv entry, or <code>null</code> if a matching lv entry could not be found
	 */
	@Override
	public LVEntry fetchByUuid_C_Head_First(
		String uuid, long companyId, boolean head,
		OrderByComparator<LVEntry> orderByComparator) {

		List<LVEntry> list = findByUuid_C_Head(
			uuid, companyId, head, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last lv entry in the ordered set where uuid = &#63; and companyId = &#63; and head = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param head the head
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching lv entry
	 * @throws NoSuchLVEntryException if a matching lv entry could not be found
	 */
	@Override
	public LVEntry findByUuid_C_Head_Last(
			String uuid, long companyId, boolean head,
			OrderByComparator<LVEntry> orderByComparator)
		throws NoSuchLVEntryException {

		LVEntry lvEntry = fetchByUuid_C_Head_Last(
			uuid, companyId, head, orderByComparator);

		if (lvEntry != null) {
			return lvEntry;
		}

		StringBundler msg = new StringBundler(8);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(", companyId=");
		msg.append(companyId);

		msg.append(", head=");
		msg.append(head);

		msg.append("}");

		throw new NoSuchLVEntryException(msg.toString());
	}

	/**
	 * Returns the last lv entry in the ordered set where uuid = &#63; and companyId = &#63; and head = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param head the head
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching lv entry, or <code>null</code> if a matching lv entry could not be found
	 */
	@Override
	public LVEntry fetchByUuid_C_Head_Last(
		String uuid, long companyId, boolean head,
		OrderByComparator<LVEntry> orderByComparator) {

		int count = countByUuid_C_Head(uuid, companyId, head);

		if (count == 0) {
			return null;
		}

		List<LVEntry> list = findByUuid_C_Head(
			uuid, companyId, head, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the lv entries before and after the current lv entry in the ordered set where uuid = &#63; and companyId = &#63; and head = &#63;.
	 *
	 * @param lvEntryId the primary key of the current lv entry
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param head the head
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next lv entry
	 * @throws NoSuchLVEntryException if a lv entry with the primary key could not be found
	 */
	@Override
	public LVEntry[] findByUuid_C_Head_PrevAndNext(
			long lvEntryId, String uuid, long companyId, boolean head,
			OrderByComparator<LVEntry> orderByComparator)
		throws NoSuchLVEntryException {

		uuid = Objects.toString(uuid, "");

		LVEntry lvEntry = findByPrimaryKey(lvEntryId);

		Session session = null;

		try {
			session = openSession();

			LVEntry[] array = new LVEntryImpl[3];

			array[0] = getByUuid_C_Head_PrevAndNext(
				session, lvEntry, uuid, companyId, head, orderByComparator,
				true);

			array[1] = lvEntry;

			array[2] = getByUuid_C_Head_PrevAndNext(
				session, lvEntry, uuid, companyId, head, orderByComparator,
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

	protected LVEntry getByUuid_C_Head_PrevAndNext(
		Session session, LVEntry lvEntry, String uuid, long companyId,
		boolean head, OrderByComparator<LVEntry> orderByComparator,
		boolean previous) {

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(
				6 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(5);
		}

		query.append(_SQL_SELECT_LVENTRY_WHERE);

		boolean bindUuid = false;

		if (uuid.isEmpty()) {
			query.append(_FINDER_COLUMN_UUID_C_HEAD_UUID_3);
		}
		else {
			bindUuid = true;

			query.append(_FINDER_COLUMN_UUID_C_HEAD_UUID_2);
		}

		query.append(_FINDER_COLUMN_UUID_C_HEAD_COMPANYID_2);

		query.append(_FINDER_COLUMN_UUID_C_HEAD_HEAD_2);

		if (orderByComparator != null) {
			String[] orderByConditionFields =
				orderByComparator.getOrderByConditionFields();

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
			query.append(LVEntryModelImpl.ORDER_BY_JPQL);
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

		qPos.add(head);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(lvEntry)) {

				qPos.add(orderByConditionValue);
			}
		}

		List<LVEntry> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the lv entries where uuid = &#63; and companyId = &#63; and head = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param head the head
	 */
	@Override
	public void removeByUuid_C_Head(String uuid, long companyId, boolean head) {
		for (LVEntry lvEntry :
				findByUuid_C_Head(
					uuid, companyId, head, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(lvEntry);
		}
	}

	/**
	 * Returns the number of lv entries where uuid = &#63; and companyId = &#63; and head = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param head the head
	 * @return the number of matching lv entries
	 */
	@Override
	public int countByUuid_C_Head(String uuid, long companyId, boolean head) {
		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = _finderPathCountByUuid_C_Head;

		Object[] finderArgs = new Object[] {uuid, companyId, head};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_COUNT_LVENTRY_WHERE);

			boolean bindUuid = false;

			if (uuid.isEmpty()) {
				query.append(_FINDER_COLUMN_UUID_C_HEAD_UUID_3);
			}
			else {
				bindUuid = true;

				query.append(_FINDER_COLUMN_UUID_C_HEAD_UUID_2);
			}

			query.append(_FINDER_COLUMN_UUID_C_HEAD_COMPANYID_2);

			query.append(_FINDER_COLUMN_UUID_C_HEAD_HEAD_2);

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

				qPos.add(head);

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

	private static final String _FINDER_COLUMN_UUID_C_HEAD_UUID_2 =
		"lvEntry.uuid = ? AND ";

	private static final String _FINDER_COLUMN_UUID_C_HEAD_UUID_3 =
		"(lvEntry.uuid IS NULL OR lvEntry.uuid = '') AND ";

	private static final String _FINDER_COLUMN_UUID_C_HEAD_COMPANYID_2 =
		"lvEntry.companyId = ? AND ";

	private static final String _FINDER_COLUMN_UUID_C_HEAD_HEAD_2 =
		"lvEntry.head = ?";

	private FinderPath _finderPathWithPaginationFindByGroupId;
	private FinderPath _finderPathWithoutPaginationFindByGroupId;
	private FinderPath _finderPathCountByGroupId;
	private FinderPath _finderPathWithPaginationCountByGroupId;

	/**
	 * Returns all the lv entries where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching lv entries
	 */
	@Override
	public List<LVEntry> findByGroupId(long groupId) {
		return findByGroupId(
			groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the lv entries where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LVEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of lv entries
	 * @param end the upper bound of the range of lv entries (not inclusive)
	 * @return the range of matching lv entries
	 */
	@Override
	public List<LVEntry> findByGroupId(long groupId, int start, int end) {
		return findByGroupId(groupId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the lv entries where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LVEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of lv entries
	 * @param end the upper bound of the range of lv entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching lv entries
	 */
	@Override
	public List<LVEntry> findByGroupId(
		long groupId, int start, int end,
		OrderByComparator<LVEntry> orderByComparator) {

		return findByGroupId(groupId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the lv entries where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LVEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of lv entries
	 * @param end the upper bound of the range of lv entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching lv entries
	 */
	@Override
	public List<LVEntry> findByGroupId(
		long groupId, int start, int end,
		OrderByComparator<LVEntry> orderByComparator, boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByGroupId;
				finderArgs = new Object[] {groupId};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByGroupId;
			finderArgs = new Object[] {groupId, start, end, orderByComparator};
		}

		List<LVEntry> list = null;

		if (useFinderCache) {
			list = (List<LVEntry>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (LVEntry lvEntry : list) {
					if (groupId != lvEntry.getGroupId()) {
						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(
					3 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				query = new StringBundler(3);
			}

			query.append(_SQL_SELECT_LVENTRY_WHERE);

			query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				query.append(LVEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				list = (List<LVEntry>)QueryUtil.list(
					q, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					finderCache.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception e) {
				if (useFinderCache) {
					finderCache.removeResult(finderPath, finderArgs);
				}

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first lv entry in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching lv entry
	 * @throws NoSuchLVEntryException if a matching lv entry could not be found
	 */
	@Override
	public LVEntry findByGroupId_First(
			long groupId, OrderByComparator<LVEntry> orderByComparator)
		throws NoSuchLVEntryException {

		LVEntry lvEntry = fetchByGroupId_First(groupId, orderByComparator);

		if (lvEntry != null) {
			return lvEntry;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append("}");

		throw new NoSuchLVEntryException(msg.toString());
	}

	/**
	 * Returns the first lv entry in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching lv entry, or <code>null</code> if a matching lv entry could not be found
	 */
	@Override
	public LVEntry fetchByGroupId_First(
		long groupId, OrderByComparator<LVEntry> orderByComparator) {

		List<LVEntry> list = findByGroupId(groupId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last lv entry in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching lv entry
	 * @throws NoSuchLVEntryException if a matching lv entry could not be found
	 */
	@Override
	public LVEntry findByGroupId_Last(
			long groupId, OrderByComparator<LVEntry> orderByComparator)
		throws NoSuchLVEntryException {

		LVEntry lvEntry = fetchByGroupId_Last(groupId, orderByComparator);

		if (lvEntry != null) {
			return lvEntry;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append("}");

		throw new NoSuchLVEntryException(msg.toString());
	}

	/**
	 * Returns the last lv entry in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching lv entry, or <code>null</code> if a matching lv entry could not be found
	 */
	@Override
	public LVEntry fetchByGroupId_Last(
		long groupId, OrderByComparator<LVEntry> orderByComparator) {

		int count = countByGroupId(groupId);

		if (count == 0) {
			return null;
		}

		List<LVEntry> list = findByGroupId(
			groupId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the lv entries before and after the current lv entry in the ordered set where groupId = &#63;.
	 *
	 * @param lvEntryId the primary key of the current lv entry
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next lv entry
	 * @throws NoSuchLVEntryException if a lv entry with the primary key could not be found
	 */
	@Override
	public LVEntry[] findByGroupId_PrevAndNext(
			long lvEntryId, long groupId,
			OrderByComparator<LVEntry> orderByComparator)
		throws NoSuchLVEntryException {

		LVEntry lvEntry = findByPrimaryKey(lvEntryId);

		Session session = null;

		try {
			session = openSession();

			LVEntry[] array = new LVEntryImpl[3];

			array[0] = getByGroupId_PrevAndNext(
				session, lvEntry, groupId, orderByComparator, true);

			array[1] = lvEntry;

			array[2] = getByGroupId_PrevAndNext(
				session, lvEntry, groupId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected LVEntry getByGroupId_PrevAndNext(
		Session session, LVEntry lvEntry, long groupId,
		OrderByComparator<LVEntry> orderByComparator, boolean previous) {

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_LVENTRY_WHERE);

		query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

		if (orderByComparator != null) {
			String[] orderByConditionFields =
				orderByComparator.getOrderByConditionFields();

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
			query.append(LVEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(lvEntry)) {

				qPos.add(orderByConditionValue);
			}
		}

		List<LVEntry> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the lv entries where groupId = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LVEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupIds the group IDs
	 * @return the matching lv entries
	 */
	@Override
	public List<LVEntry> findByGroupId(long[] groupIds) {
		return findByGroupId(
			groupIds, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the lv entries where groupId = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LVEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupIds the group IDs
	 * @param start the lower bound of the range of lv entries
	 * @param end the upper bound of the range of lv entries (not inclusive)
	 * @return the range of matching lv entries
	 */
	@Override
	public List<LVEntry> findByGroupId(long[] groupIds, int start, int end) {
		return findByGroupId(groupIds, start, end, null);
	}

	/**
	 * Returns an ordered range of all the lv entries where groupId = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LVEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupIds the group IDs
	 * @param start the lower bound of the range of lv entries
	 * @param end the upper bound of the range of lv entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching lv entries
	 */
	@Override
	public List<LVEntry> findByGroupId(
		long[] groupIds, int start, int end,
		OrderByComparator<LVEntry> orderByComparator) {

		return findByGroupId(groupIds, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the lv entries where groupId = &#63;, optionally using the finder cache.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LVEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of lv entries
	 * @param end the upper bound of the range of lv entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching lv entries
	 */
	@Override
	public List<LVEntry> findByGroupId(
		long[] groupIds, int start, int end,
		OrderByComparator<LVEntry> orderByComparator, boolean useFinderCache) {

		if (groupIds == null) {
			groupIds = new long[0];
		}
		else if (groupIds.length > 1) {
			groupIds = ArrayUtil.sortedUnique(groupIds);
		}

		if (groupIds.length == 1) {
			return findByGroupId(groupIds[0], start, end, orderByComparator);
		}

		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderArgs = new Object[] {StringUtil.merge(groupIds)};
			}
		}
		else if (useFinderCache) {
			finderArgs = new Object[] {
				StringUtil.merge(groupIds), start, end, orderByComparator
			};
		}

		List<LVEntry> list = null;

		if (useFinderCache) {
			list = (List<LVEntry>)finderCache.getResult(
				_finderPathWithPaginationFindByGroupId, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (LVEntry lvEntry : list) {
					if (!ArrayUtil.contains(groupIds, lvEntry.getGroupId())) {
						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			try {
				if ((start == QueryUtil.ALL_POS) &&
					(end == QueryUtil.ALL_POS) &&
					(databaseInMaxParameters > 0) &&
					(groupIds.length > databaseInMaxParameters)) {

					list = new ArrayList<LVEntry>();

					long[][] groupIdsPages = (long[][])ArrayUtil.split(
						groupIds, databaseInMaxParameters);

					for (long[] groupIdsPage : groupIdsPages) {
						list.addAll(
							_findByGroupId(
								groupIdsPage, start, end, orderByComparator));
					}

					Collections.sort(list, orderByComparator);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = _findByGroupId(
						groupIds, start, end, orderByComparator);
				}

				cacheResult(list);

				if (useFinderCache) {
					finderCache.putResult(
						_finderPathWithPaginationFindByGroupId, finderArgs,
						list);
				}
			}
			catch (Exception e) {
				if (useFinderCache) {
					finderCache.removeResult(
						_finderPathWithPaginationFindByGroupId, finderArgs);
				}

				throw processException(e);
			}
		}

		return list;
	}

	private List<LVEntry> _findByGroupId(
		long[] groupIds, int start, int end,
		OrderByComparator<LVEntry> orderByComparator) {

		List<LVEntry> list = null;

		StringBundler query = new StringBundler();

		query.append(_SQL_SELECT_LVENTRY_WHERE);

		if (groupIds.length > 0) {
			query.append("(");

			query.append(_FINDER_COLUMN_GROUPID_GROUPID_7);

			query.append(StringUtil.merge(groupIds));

			query.append(")");

			query.append(")");
		}

		query.setStringAt(
			removeConjunction(query.stringAt(query.index() - 1)),
			query.index() - 1);

		query.append(" AND lvEntry.lvEntryId > 0");

		if (orderByComparator != null) {
			appendOrderByComparator(
				query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
		}
		else {
			query.append(LVEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Session session = null;

		try {
			session = openSession();

			Query q = session.createQuery(sql);

			list = (List<LVEntry>)QueryUtil.list(q, getDialect(), start, end);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		return list;
	}

	/**
	 * Removes all the lv entries where groupId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 */
	@Override
	public void removeByGroupId(long groupId) {
		for (LVEntry lvEntry :
				findByGroupId(
					groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(lvEntry);
		}
	}

	/**
	 * Returns the number of lv entries where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching lv entries
	 */
	@Override
	public int countByGroupId(long groupId) {
		FinderPath finderPath = _finderPathCountByGroupId;

		Object[] finderArgs = new Object[] {groupId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_LVENTRY_WHERE);

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

	/**
	 * Returns the number of lv entries where groupId = any &#63;.
	 *
	 * @param groupIds the group IDs
	 * @return the number of matching lv entries
	 */
	@Override
	public int countByGroupId(long[] groupIds) {
		if (groupIds == null) {
			groupIds = new long[0];
		}
		else if (groupIds.length > 1) {
			groupIds = ArrayUtil.sortedUnique(groupIds);
		}

		Object[] finderArgs = new Object[] {StringUtil.merge(groupIds)};

		Long count = (Long)finderCache.getResult(
			_finderPathWithPaginationCountByGroupId, finderArgs, this);

		if (count == null) {
			try {
				if ((databaseInMaxParameters > 0) &&
					(groupIds.length > databaseInMaxParameters)) {

					count = Long.valueOf(0);

					long[][] groupIdsPages = (long[][])ArrayUtil.split(
						groupIds, databaseInMaxParameters);

					for (long[] groupIdsPage : groupIdsPages) {
						count += Long.valueOf(_countByGroupId(groupIdsPage));
					}
				}
				else {
					count = Long.valueOf(_countByGroupId(groupIds));
				}

				finderCache.putResult(
					_finderPathWithPaginationCountByGroupId, finderArgs, count);
			}
			catch (Exception e) {
				finderCache.removeResult(
					_finderPathWithPaginationCountByGroupId, finderArgs);

				throw processException(e);
			}
		}

		return count.intValue();
	}

	private int _countByGroupId(long[] groupIds) {
		Long count = null;

		StringBundler query = new StringBundler();

		query.append(_SQL_COUNT_LVENTRY_WHERE);

		if (groupIds.length > 0) {
			query.append("(");

			query.append(_FINDER_COLUMN_GROUPID_GROUPID_7);

			query.append(StringUtil.merge(groupIds));

			query.append(")");

			query.append(")");
		}

		query.setStringAt(
			removeConjunction(query.stringAt(query.index() - 1)),
			query.index() - 1);

		query.append(" AND lvEntry.lvEntryId > 0");

		String sql = query.toString();

		Session session = null;

		try {
			session = openSession();

			Query q = session.createQuery(sql);

			count = (Long)q.uniqueResult();
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_GROUPID_GROUPID_2 =
		"lvEntry.groupId = ? AND lvEntry.lvEntryId > 0";

	private static final String _FINDER_COLUMN_GROUPID_GROUPID_7 =
		"lvEntry.groupId IN (";

	private FinderPath _finderPathWithPaginationFindByGroupId_Head;
	private FinderPath _finderPathWithoutPaginationFindByGroupId_Head;
	private FinderPath _finderPathCountByGroupId_Head;
	private FinderPath _finderPathWithPaginationCountByGroupId_Head;

	/**
	 * Returns all the lv entries where groupId = &#63; and head = &#63;.
	 *
	 * @param groupId the group ID
	 * @param head the head
	 * @return the matching lv entries
	 */
	@Override
	public List<LVEntry> findByGroupId_Head(long groupId, boolean head) {
		return findByGroupId_Head(
			groupId, head, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the lv entries where groupId = &#63; and head = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LVEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param head the head
	 * @param start the lower bound of the range of lv entries
	 * @param end the upper bound of the range of lv entries (not inclusive)
	 * @return the range of matching lv entries
	 */
	@Override
	public List<LVEntry> findByGroupId_Head(
		long groupId, boolean head, int start, int end) {

		return findByGroupId_Head(groupId, head, start, end, null);
	}

	/**
	 * Returns an ordered range of all the lv entries where groupId = &#63; and head = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LVEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param head the head
	 * @param start the lower bound of the range of lv entries
	 * @param end the upper bound of the range of lv entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching lv entries
	 */
	@Override
	public List<LVEntry> findByGroupId_Head(
		long groupId, boolean head, int start, int end,
		OrderByComparator<LVEntry> orderByComparator) {

		return findByGroupId_Head(
			groupId, head, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the lv entries where groupId = &#63; and head = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LVEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param head the head
	 * @param start the lower bound of the range of lv entries
	 * @param end the upper bound of the range of lv entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching lv entries
	 */
	@Override
	public List<LVEntry> findByGroupId_Head(
		long groupId, boolean head, int start, int end,
		OrderByComparator<LVEntry> orderByComparator, boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByGroupId_Head;
				finderArgs = new Object[] {groupId, head};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByGroupId_Head;
			finderArgs = new Object[] {
				groupId, head, start, end, orderByComparator
			};
		}

		List<LVEntry> list = null;

		if (useFinderCache) {
			list = (List<LVEntry>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (LVEntry lvEntry : list) {
					if ((groupId != lvEntry.getGroupId()) ||
						(head != lvEntry.isHead())) {

						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(
					4 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				query = new StringBundler(4);
			}

			query.append(_SQL_SELECT_LVENTRY_WHERE);

			query.append(_FINDER_COLUMN_GROUPID_HEAD_GROUPID_2);

			query.append(_FINDER_COLUMN_GROUPID_HEAD_HEAD_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				query.append(LVEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(head);

				list = (List<LVEntry>)QueryUtil.list(
					q, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					finderCache.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception e) {
				if (useFinderCache) {
					finderCache.removeResult(finderPath, finderArgs);
				}

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first lv entry in the ordered set where groupId = &#63; and head = &#63;.
	 *
	 * @param groupId the group ID
	 * @param head the head
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching lv entry
	 * @throws NoSuchLVEntryException if a matching lv entry could not be found
	 */
	@Override
	public LVEntry findByGroupId_Head_First(
			long groupId, boolean head,
			OrderByComparator<LVEntry> orderByComparator)
		throws NoSuchLVEntryException {

		LVEntry lvEntry = fetchByGroupId_Head_First(
			groupId, head, orderByComparator);

		if (lvEntry != null) {
			return lvEntry;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", head=");
		msg.append(head);

		msg.append("}");

		throw new NoSuchLVEntryException(msg.toString());
	}

	/**
	 * Returns the first lv entry in the ordered set where groupId = &#63; and head = &#63;.
	 *
	 * @param groupId the group ID
	 * @param head the head
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching lv entry, or <code>null</code> if a matching lv entry could not be found
	 */
	@Override
	public LVEntry fetchByGroupId_Head_First(
		long groupId, boolean head,
		OrderByComparator<LVEntry> orderByComparator) {

		List<LVEntry> list = findByGroupId_Head(
			groupId, head, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last lv entry in the ordered set where groupId = &#63; and head = &#63;.
	 *
	 * @param groupId the group ID
	 * @param head the head
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching lv entry
	 * @throws NoSuchLVEntryException if a matching lv entry could not be found
	 */
	@Override
	public LVEntry findByGroupId_Head_Last(
			long groupId, boolean head,
			OrderByComparator<LVEntry> orderByComparator)
		throws NoSuchLVEntryException {

		LVEntry lvEntry = fetchByGroupId_Head_Last(
			groupId, head, orderByComparator);

		if (lvEntry != null) {
			return lvEntry;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", head=");
		msg.append(head);

		msg.append("}");

		throw new NoSuchLVEntryException(msg.toString());
	}

	/**
	 * Returns the last lv entry in the ordered set where groupId = &#63; and head = &#63;.
	 *
	 * @param groupId the group ID
	 * @param head the head
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching lv entry, or <code>null</code> if a matching lv entry could not be found
	 */
	@Override
	public LVEntry fetchByGroupId_Head_Last(
		long groupId, boolean head,
		OrderByComparator<LVEntry> orderByComparator) {

		int count = countByGroupId_Head(groupId, head);

		if (count == 0) {
			return null;
		}

		List<LVEntry> list = findByGroupId_Head(
			groupId, head, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the lv entries before and after the current lv entry in the ordered set where groupId = &#63; and head = &#63;.
	 *
	 * @param lvEntryId the primary key of the current lv entry
	 * @param groupId the group ID
	 * @param head the head
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next lv entry
	 * @throws NoSuchLVEntryException if a lv entry with the primary key could not be found
	 */
	@Override
	public LVEntry[] findByGroupId_Head_PrevAndNext(
			long lvEntryId, long groupId, boolean head,
			OrderByComparator<LVEntry> orderByComparator)
		throws NoSuchLVEntryException {

		LVEntry lvEntry = findByPrimaryKey(lvEntryId);

		Session session = null;

		try {
			session = openSession();

			LVEntry[] array = new LVEntryImpl[3];

			array[0] = getByGroupId_Head_PrevAndNext(
				session, lvEntry, groupId, head, orderByComparator, true);

			array[1] = lvEntry;

			array[2] = getByGroupId_Head_PrevAndNext(
				session, lvEntry, groupId, head, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected LVEntry getByGroupId_Head_PrevAndNext(
		Session session, LVEntry lvEntry, long groupId, boolean head,
		OrderByComparator<LVEntry> orderByComparator, boolean previous) {

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(
				5 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(4);
		}

		query.append(_SQL_SELECT_LVENTRY_WHERE);

		query.append(_FINDER_COLUMN_GROUPID_HEAD_GROUPID_2);

		query.append(_FINDER_COLUMN_GROUPID_HEAD_HEAD_2);

		if (orderByComparator != null) {
			String[] orderByConditionFields =
				orderByComparator.getOrderByConditionFields();

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
			query.append(LVEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		qPos.add(head);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(lvEntry)) {

				qPos.add(orderByConditionValue);
			}
		}

		List<LVEntry> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the lv entries where groupId = any &#63; and head = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LVEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupIds the group IDs
	 * @param head the head
	 * @return the matching lv entries
	 */
	@Override
	public List<LVEntry> findByGroupId_Head(long[] groupIds, boolean head) {
		return findByGroupId_Head(
			groupIds, head, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the lv entries where groupId = any &#63; and head = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LVEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupIds the group IDs
	 * @param head the head
	 * @param start the lower bound of the range of lv entries
	 * @param end the upper bound of the range of lv entries (not inclusive)
	 * @return the range of matching lv entries
	 */
	@Override
	public List<LVEntry> findByGroupId_Head(
		long[] groupIds, boolean head, int start, int end) {

		return findByGroupId_Head(groupIds, head, start, end, null);
	}

	/**
	 * Returns an ordered range of all the lv entries where groupId = any &#63; and head = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LVEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupIds the group IDs
	 * @param head the head
	 * @param start the lower bound of the range of lv entries
	 * @param end the upper bound of the range of lv entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching lv entries
	 */
	@Override
	public List<LVEntry> findByGroupId_Head(
		long[] groupIds, boolean head, int start, int end,
		OrderByComparator<LVEntry> orderByComparator) {

		return findByGroupId_Head(
			groupIds, head, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the lv entries where groupId = &#63; and head = &#63;, optionally using the finder cache.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LVEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param head the head
	 * @param start the lower bound of the range of lv entries
	 * @param end the upper bound of the range of lv entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching lv entries
	 */
	@Override
	public List<LVEntry> findByGroupId_Head(
		long[] groupIds, boolean head, int start, int end,
		OrderByComparator<LVEntry> orderByComparator, boolean useFinderCache) {

		if (groupIds == null) {
			groupIds = new long[0];
		}
		else if (groupIds.length > 1) {
			groupIds = ArrayUtil.sortedUnique(groupIds);
		}

		if (groupIds.length == 1) {
			return findByGroupId_Head(
				groupIds[0], head, start, end, orderByComparator);
		}

		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderArgs = new Object[] {StringUtil.merge(groupIds), head};
			}
		}
		else if (useFinderCache) {
			finderArgs = new Object[] {
				StringUtil.merge(groupIds), head, start, end, orderByComparator
			};
		}

		List<LVEntry> list = null;

		if (useFinderCache) {
			list = (List<LVEntry>)finderCache.getResult(
				_finderPathWithPaginationFindByGroupId_Head, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (LVEntry lvEntry : list) {
					if (!ArrayUtil.contains(groupIds, lvEntry.getGroupId()) ||
						(head != lvEntry.isHead())) {

						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			try {
				if ((start == QueryUtil.ALL_POS) &&
					(end == QueryUtil.ALL_POS) &&
					(databaseInMaxParameters > 0) &&
					(groupIds.length > databaseInMaxParameters)) {

					list = new ArrayList<LVEntry>();

					long[][] groupIdsPages = (long[][])ArrayUtil.split(
						groupIds, databaseInMaxParameters);

					for (long[] groupIdsPage : groupIdsPages) {
						list.addAll(
							_findByGroupId_Head(
								groupIdsPage, head, start, end,
								orderByComparator));
					}

					Collections.sort(list, orderByComparator);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = _findByGroupId_Head(
						groupIds, head, start, end, orderByComparator);
				}

				cacheResult(list);

				if (useFinderCache) {
					finderCache.putResult(
						_finderPathWithPaginationFindByGroupId_Head, finderArgs,
						list);
				}
			}
			catch (Exception e) {
				if (useFinderCache) {
					finderCache.removeResult(
						_finderPathWithPaginationFindByGroupId_Head,
						finderArgs);
				}

				throw processException(e);
			}
		}

		return list;
	}

	private List<LVEntry> _findByGroupId_Head(
		long[] groupIds, boolean head, int start, int end,
		OrderByComparator<LVEntry> orderByComparator) {

		List<LVEntry> list = null;

		StringBundler query = new StringBundler();

		query.append(_SQL_SELECT_LVENTRY_WHERE);

		if (groupIds.length > 0) {
			query.append("(");

			query.append(_FINDER_COLUMN_GROUPID_HEAD_GROUPID_7);

			query.append(StringUtil.merge(groupIds));

			query.append(")");

			query.append(")");

			query.append(WHERE_AND);
		}

		query.append(_FINDER_COLUMN_GROUPID_HEAD_HEAD_2);

		query.setStringAt(
			removeConjunction(query.stringAt(query.index() - 1)),
			query.index() - 1);

		query.append(" AND lvEntry.lvEntryId > 0");

		if (orderByComparator != null) {
			appendOrderByComparator(
				query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
		}
		else {
			query.append(LVEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Session session = null;

		try {
			session = openSession();

			Query q = session.createQuery(sql);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(head);

			list = (List<LVEntry>)QueryUtil.list(q, getDialect(), start, end);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		return list;
	}

	/**
	 * Removes all the lv entries where groupId = &#63; and head = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param head the head
	 */
	@Override
	public void removeByGroupId_Head(long groupId, boolean head) {
		for (LVEntry lvEntry :
				findByGroupId_Head(
					groupId, head, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(lvEntry);
		}
	}

	/**
	 * Returns the number of lv entries where groupId = &#63; and head = &#63;.
	 *
	 * @param groupId the group ID
	 * @param head the head
	 * @return the number of matching lv entries
	 */
	@Override
	public int countByGroupId_Head(long groupId, boolean head) {
		FinderPath finderPath = _finderPathCountByGroupId_Head;

		Object[] finderArgs = new Object[] {groupId, head};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_LVENTRY_WHERE);

			query.append(_FINDER_COLUMN_GROUPID_HEAD_GROUPID_2);

			query.append(_FINDER_COLUMN_GROUPID_HEAD_HEAD_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(head);

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

	/**
	 * Returns the number of lv entries where groupId = any &#63; and head = &#63;.
	 *
	 * @param groupIds the group IDs
	 * @param head the head
	 * @return the number of matching lv entries
	 */
	@Override
	public int countByGroupId_Head(long[] groupIds, boolean head) {
		if (groupIds == null) {
			groupIds = new long[0];
		}
		else if (groupIds.length > 1) {
			groupIds = ArrayUtil.sortedUnique(groupIds);
		}

		Object[] finderArgs = new Object[] {StringUtil.merge(groupIds), head};

		Long count = (Long)finderCache.getResult(
			_finderPathWithPaginationCountByGroupId_Head, finderArgs, this);

		if (count == null) {
			try {
				if ((databaseInMaxParameters > 0) &&
					(groupIds.length > databaseInMaxParameters)) {

					count = Long.valueOf(0);

					long[][] groupIdsPages = (long[][])ArrayUtil.split(
						groupIds, databaseInMaxParameters);

					for (long[] groupIdsPage : groupIdsPages) {
						count += Long.valueOf(
							_countByGroupId_Head(groupIdsPage, head));
					}
				}
				else {
					count = Long.valueOf(_countByGroupId_Head(groupIds, head));
				}

				finderCache.putResult(
					_finderPathWithPaginationCountByGroupId_Head, finderArgs,
					count);
			}
			catch (Exception e) {
				finderCache.removeResult(
					_finderPathWithPaginationCountByGroupId_Head, finderArgs);

				throw processException(e);
			}
		}

		return count.intValue();
	}

	private int _countByGroupId_Head(long[] groupIds, boolean head) {
		Long count = null;

		StringBundler query = new StringBundler();

		query.append(_SQL_COUNT_LVENTRY_WHERE);

		if (groupIds.length > 0) {
			query.append("(");

			query.append(_FINDER_COLUMN_GROUPID_HEAD_GROUPID_7);

			query.append(StringUtil.merge(groupIds));

			query.append(")");

			query.append(")");

			query.append(WHERE_AND);
		}

		query.append(_FINDER_COLUMN_GROUPID_HEAD_HEAD_2);

		query.setStringAt(
			removeConjunction(query.stringAt(query.index() - 1)),
			query.index() - 1);

		query.append(" AND lvEntry.lvEntryId > 0");

		String sql = query.toString();

		Session session = null;

		try {
			session = openSession();

			Query q = session.createQuery(sql);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(head);

			count = (Long)q.uniqueResult();
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_GROUPID_HEAD_GROUPID_2 =
		"lvEntry.groupId = ? AND ";

	private static final String _FINDER_COLUMN_GROUPID_HEAD_GROUPID_7 =
		"lvEntry.groupId IN (";

	private static final String _FINDER_COLUMN_GROUPID_HEAD_HEAD_2 =
		"lvEntry.head = ? AND lvEntry.lvEntryId > 0";

	private FinderPath _finderPathWithPaginationFindByG_UGK;
	private FinderPath _finderPathWithoutPaginationFindByG_UGK;
	private FinderPath _finderPathCountByG_UGK;

	/**
	 * Returns all the lv entries where groupId = &#63; and uniqueGroupKey = &#63;.
	 *
	 * @param groupId the group ID
	 * @param uniqueGroupKey the unique group key
	 * @return the matching lv entries
	 */
	@Override
	public List<LVEntry> findByG_UGK(long groupId, String uniqueGroupKey) {
		return findByG_UGK(
			groupId, uniqueGroupKey, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the lv entries where groupId = &#63; and uniqueGroupKey = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LVEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param uniqueGroupKey the unique group key
	 * @param start the lower bound of the range of lv entries
	 * @param end the upper bound of the range of lv entries (not inclusive)
	 * @return the range of matching lv entries
	 */
	@Override
	public List<LVEntry> findByG_UGK(
		long groupId, String uniqueGroupKey, int start, int end) {

		return findByG_UGK(groupId, uniqueGroupKey, start, end, null);
	}

	/**
	 * Returns an ordered range of all the lv entries where groupId = &#63; and uniqueGroupKey = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LVEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param uniqueGroupKey the unique group key
	 * @param start the lower bound of the range of lv entries
	 * @param end the upper bound of the range of lv entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching lv entries
	 */
	@Override
	public List<LVEntry> findByG_UGK(
		long groupId, String uniqueGroupKey, int start, int end,
		OrderByComparator<LVEntry> orderByComparator) {

		return findByG_UGK(
			groupId, uniqueGroupKey, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the lv entries where groupId = &#63; and uniqueGroupKey = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LVEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param uniqueGroupKey the unique group key
	 * @param start the lower bound of the range of lv entries
	 * @param end the upper bound of the range of lv entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching lv entries
	 */
	@Override
	public List<LVEntry> findByG_UGK(
		long groupId, String uniqueGroupKey, int start, int end,
		OrderByComparator<LVEntry> orderByComparator, boolean useFinderCache) {

		uniqueGroupKey = Objects.toString(uniqueGroupKey, "");

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByG_UGK;
				finderArgs = new Object[] {groupId, uniqueGroupKey};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByG_UGK;
			finderArgs = new Object[] {
				groupId, uniqueGroupKey, start, end, orderByComparator
			};
		}

		List<LVEntry> list = null;

		if (useFinderCache) {
			list = (List<LVEntry>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (LVEntry lvEntry : list) {
					if ((groupId != lvEntry.getGroupId()) ||
						!uniqueGroupKey.equals(lvEntry.getUniqueGroupKey())) {

						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(
					4 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				query = new StringBundler(4);
			}

			query.append(_SQL_SELECT_LVENTRY_WHERE);

			query.append(_FINDER_COLUMN_G_UGK_GROUPID_2);

			boolean bindUniqueGroupKey = false;

			if (uniqueGroupKey.isEmpty()) {
				query.append(_FINDER_COLUMN_G_UGK_UNIQUEGROUPKEY_3);
			}
			else {
				bindUniqueGroupKey = true;

				query.append(_FINDER_COLUMN_G_UGK_UNIQUEGROUPKEY_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				query.append(LVEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (bindUniqueGroupKey) {
					qPos.add(uniqueGroupKey);
				}

				list = (List<LVEntry>)QueryUtil.list(
					q, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					finderCache.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception e) {
				if (useFinderCache) {
					finderCache.removeResult(finderPath, finderArgs);
				}

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first lv entry in the ordered set where groupId = &#63; and uniqueGroupKey = &#63;.
	 *
	 * @param groupId the group ID
	 * @param uniqueGroupKey the unique group key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching lv entry
	 * @throws NoSuchLVEntryException if a matching lv entry could not be found
	 */
	@Override
	public LVEntry findByG_UGK_First(
			long groupId, String uniqueGroupKey,
			OrderByComparator<LVEntry> orderByComparator)
		throws NoSuchLVEntryException {

		LVEntry lvEntry = fetchByG_UGK_First(
			groupId, uniqueGroupKey, orderByComparator);

		if (lvEntry != null) {
			return lvEntry;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", uniqueGroupKey=");
		msg.append(uniqueGroupKey);

		msg.append("}");

		throw new NoSuchLVEntryException(msg.toString());
	}

	/**
	 * Returns the first lv entry in the ordered set where groupId = &#63; and uniqueGroupKey = &#63;.
	 *
	 * @param groupId the group ID
	 * @param uniqueGroupKey the unique group key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching lv entry, or <code>null</code> if a matching lv entry could not be found
	 */
	@Override
	public LVEntry fetchByG_UGK_First(
		long groupId, String uniqueGroupKey,
		OrderByComparator<LVEntry> orderByComparator) {

		List<LVEntry> list = findByG_UGK(
			groupId, uniqueGroupKey, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last lv entry in the ordered set where groupId = &#63; and uniqueGroupKey = &#63;.
	 *
	 * @param groupId the group ID
	 * @param uniqueGroupKey the unique group key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching lv entry
	 * @throws NoSuchLVEntryException if a matching lv entry could not be found
	 */
	@Override
	public LVEntry findByG_UGK_Last(
			long groupId, String uniqueGroupKey,
			OrderByComparator<LVEntry> orderByComparator)
		throws NoSuchLVEntryException {

		LVEntry lvEntry = fetchByG_UGK_Last(
			groupId, uniqueGroupKey, orderByComparator);

		if (lvEntry != null) {
			return lvEntry;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", uniqueGroupKey=");
		msg.append(uniqueGroupKey);

		msg.append("}");

		throw new NoSuchLVEntryException(msg.toString());
	}

	/**
	 * Returns the last lv entry in the ordered set where groupId = &#63; and uniqueGroupKey = &#63;.
	 *
	 * @param groupId the group ID
	 * @param uniqueGroupKey the unique group key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching lv entry, or <code>null</code> if a matching lv entry could not be found
	 */
	@Override
	public LVEntry fetchByG_UGK_Last(
		long groupId, String uniqueGroupKey,
		OrderByComparator<LVEntry> orderByComparator) {

		int count = countByG_UGK(groupId, uniqueGroupKey);

		if (count == 0) {
			return null;
		}

		List<LVEntry> list = findByG_UGK(
			groupId, uniqueGroupKey, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the lv entries before and after the current lv entry in the ordered set where groupId = &#63; and uniqueGroupKey = &#63;.
	 *
	 * @param lvEntryId the primary key of the current lv entry
	 * @param groupId the group ID
	 * @param uniqueGroupKey the unique group key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next lv entry
	 * @throws NoSuchLVEntryException if a lv entry with the primary key could not be found
	 */
	@Override
	public LVEntry[] findByG_UGK_PrevAndNext(
			long lvEntryId, long groupId, String uniqueGroupKey,
			OrderByComparator<LVEntry> orderByComparator)
		throws NoSuchLVEntryException {

		uniqueGroupKey = Objects.toString(uniqueGroupKey, "");

		LVEntry lvEntry = findByPrimaryKey(lvEntryId);

		Session session = null;

		try {
			session = openSession();

			LVEntry[] array = new LVEntryImpl[3];

			array[0] = getByG_UGK_PrevAndNext(
				session, lvEntry, groupId, uniqueGroupKey, orderByComparator,
				true);

			array[1] = lvEntry;

			array[2] = getByG_UGK_PrevAndNext(
				session, lvEntry, groupId, uniqueGroupKey, orderByComparator,
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

	protected LVEntry getByG_UGK_PrevAndNext(
		Session session, LVEntry lvEntry, long groupId, String uniqueGroupKey,
		OrderByComparator<LVEntry> orderByComparator, boolean previous) {

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(
				5 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(4);
		}

		query.append(_SQL_SELECT_LVENTRY_WHERE);

		query.append(_FINDER_COLUMN_G_UGK_GROUPID_2);

		boolean bindUniqueGroupKey = false;

		if (uniqueGroupKey.isEmpty()) {
			query.append(_FINDER_COLUMN_G_UGK_UNIQUEGROUPKEY_3);
		}
		else {
			bindUniqueGroupKey = true;

			query.append(_FINDER_COLUMN_G_UGK_UNIQUEGROUPKEY_2);
		}

		if (orderByComparator != null) {
			String[] orderByConditionFields =
				orderByComparator.getOrderByConditionFields();

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
			query.append(LVEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		if (bindUniqueGroupKey) {
			qPos.add(uniqueGroupKey);
		}

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(lvEntry)) {

				qPos.add(orderByConditionValue);
			}
		}

		List<LVEntry> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the lv entries where groupId = &#63; and uniqueGroupKey = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param uniqueGroupKey the unique group key
	 */
	@Override
	public void removeByG_UGK(long groupId, String uniqueGroupKey) {
		for (LVEntry lvEntry :
				findByG_UGK(
					groupId, uniqueGroupKey, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			remove(lvEntry);
		}
	}

	/**
	 * Returns the number of lv entries where groupId = &#63; and uniqueGroupKey = &#63;.
	 *
	 * @param groupId the group ID
	 * @param uniqueGroupKey the unique group key
	 * @return the number of matching lv entries
	 */
	@Override
	public int countByG_UGK(long groupId, String uniqueGroupKey) {
		uniqueGroupKey = Objects.toString(uniqueGroupKey, "");

		FinderPath finderPath = _finderPathCountByG_UGK;

		Object[] finderArgs = new Object[] {groupId, uniqueGroupKey};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_LVENTRY_WHERE);

			query.append(_FINDER_COLUMN_G_UGK_GROUPID_2);

			boolean bindUniqueGroupKey = false;

			if (uniqueGroupKey.isEmpty()) {
				query.append(_FINDER_COLUMN_G_UGK_UNIQUEGROUPKEY_3);
			}
			else {
				bindUniqueGroupKey = true;

				query.append(_FINDER_COLUMN_G_UGK_UNIQUEGROUPKEY_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (bindUniqueGroupKey) {
					qPos.add(uniqueGroupKey);
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

	private static final String _FINDER_COLUMN_G_UGK_GROUPID_2 =
		"lvEntry.groupId = ? AND ";

	private static final String _FINDER_COLUMN_G_UGK_UNIQUEGROUPKEY_2 =
		"lvEntry.uniqueGroupKey = ?";

	private static final String _FINDER_COLUMN_G_UGK_UNIQUEGROUPKEY_3 =
		"(lvEntry.uniqueGroupKey IS NULL OR lvEntry.uniqueGroupKey = '')";

	private FinderPath _finderPathFetchByG_UGK_Head;
	private FinderPath _finderPathCountByG_UGK_Head;

	/**
	 * Returns the lv entry where groupId = &#63; and uniqueGroupKey = &#63; and head = &#63; or throws a <code>NoSuchLVEntryException</code> if it could not be found.
	 *
	 * @param groupId the group ID
	 * @param uniqueGroupKey the unique group key
	 * @param head the head
	 * @return the matching lv entry
	 * @throws NoSuchLVEntryException if a matching lv entry could not be found
	 */
	@Override
	public LVEntry findByG_UGK_Head(
			long groupId, String uniqueGroupKey, boolean head)
		throws NoSuchLVEntryException {

		LVEntry lvEntry = fetchByG_UGK_Head(groupId, uniqueGroupKey, head);

		if (lvEntry == null) {
			StringBundler msg = new StringBundler(8);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("groupId=");
			msg.append(groupId);

			msg.append(", uniqueGroupKey=");
			msg.append(uniqueGroupKey);

			msg.append(", head=");
			msg.append(head);

			msg.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(msg.toString());
			}

			throw new NoSuchLVEntryException(msg.toString());
		}

		return lvEntry;
	}

	/**
	 * Returns the lv entry where groupId = &#63; and uniqueGroupKey = &#63; and head = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param groupId the group ID
	 * @param uniqueGroupKey the unique group key
	 * @param head the head
	 * @return the matching lv entry, or <code>null</code> if a matching lv entry could not be found
	 */
	@Override
	public LVEntry fetchByG_UGK_Head(
		long groupId, String uniqueGroupKey, boolean head) {

		return fetchByG_UGK_Head(groupId, uniqueGroupKey, head, true);
	}

	/**
	 * Returns the lv entry where groupId = &#63; and uniqueGroupKey = &#63; and head = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param groupId the group ID
	 * @param uniqueGroupKey the unique group key
	 * @param head the head
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching lv entry, or <code>null</code> if a matching lv entry could not be found
	 */
	@Override
	public LVEntry fetchByG_UGK_Head(
		long groupId, String uniqueGroupKey, boolean head,
		boolean useFinderCache) {

		uniqueGroupKey = Objects.toString(uniqueGroupKey, "");

		Object[] finderArgs = null;

		if (useFinderCache) {
			finderArgs = new Object[] {groupId, uniqueGroupKey, head};
		}

		Object result = null;

		if (useFinderCache) {
			result = finderCache.getResult(
				_finderPathFetchByG_UGK_Head, finderArgs, this);
		}

		if (result instanceof LVEntry) {
			LVEntry lvEntry = (LVEntry)result;

			if ((groupId != lvEntry.getGroupId()) ||
				!Objects.equals(uniqueGroupKey, lvEntry.getUniqueGroupKey()) ||
				(head != lvEntry.isHead())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(5);

			query.append(_SQL_SELECT_LVENTRY_WHERE);

			query.append(_FINDER_COLUMN_G_UGK_HEAD_GROUPID_2);

			boolean bindUniqueGroupKey = false;

			if (uniqueGroupKey.isEmpty()) {
				query.append(_FINDER_COLUMN_G_UGK_HEAD_UNIQUEGROUPKEY_3);
			}
			else {
				bindUniqueGroupKey = true;

				query.append(_FINDER_COLUMN_G_UGK_HEAD_UNIQUEGROUPKEY_2);
			}

			query.append(_FINDER_COLUMN_G_UGK_HEAD_HEAD_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (bindUniqueGroupKey) {
					qPos.add(uniqueGroupKey);
				}

				qPos.add(head);

				List<LVEntry> list = q.list();

				if (list.isEmpty()) {
					if (useFinderCache) {
						finderCache.putResult(
							_finderPathFetchByG_UGK_Head, finderArgs, list);
					}
				}
				else {
					LVEntry lvEntry = list.get(0);

					result = lvEntry;

					cacheResult(lvEntry);
				}
			}
			catch (Exception e) {
				if (useFinderCache) {
					finderCache.removeResult(
						_finderPathFetchByG_UGK_Head, finderArgs);
				}

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
			return (LVEntry)result;
		}
	}

	/**
	 * Removes the lv entry where groupId = &#63; and uniqueGroupKey = &#63; and head = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param uniqueGroupKey the unique group key
	 * @param head the head
	 * @return the lv entry that was removed
	 */
	@Override
	public LVEntry removeByG_UGK_Head(
			long groupId, String uniqueGroupKey, boolean head)
		throws NoSuchLVEntryException {

		LVEntry lvEntry = findByG_UGK_Head(groupId, uniqueGroupKey, head);

		return remove(lvEntry);
	}

	/**
	 * Returns the number of lv entries where groupId = &#63; and uniqueGroupKey = &#63; and head = &#63;.
	 *
	 * @param groupId the group ID
	 * @param uniqueGroupKey the unique group key
	 * @param head the head
	 * @return the number of matching lv entries
	 */
	@Override
	public int countByG_UGK_Head(
		long groupId, String uniqueGroupKey, boolean head) {

		uniqueGroupKey = Objects.toString(uniqueGroupKey, "");

		FinderPath finderPath = _finderPathCountByG_UGK_Head;

		Object[] finderArgs = new Object[] {groupId, uniqueGroupKey, head};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_COUNT_LVENTRY_WHERE);

			query.append(_FINDER_COLUMN_G_UGK_HEAD_GROUPID_2);

			boolean bindUniqueGroupKey = false;

			if (uniqueGroupKey.isEmpty()) {
				query.append(_FINDER_COLUMN_G_UGK_HEAD_UNIQUEGROUPKEY_3);
			}
			else {
				bindUniqueGroupKey = true;

				query.append(_FINDER_COLUMN_G_UGK_HEAD_UNIQUEGROUPKEY_2);
			}

			query.append(_FINDER_COLUMN_G_UGK_HEAD_HEAD_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (bindUniqueGroupKey) {
					qPos.add(uniqueGroupKey);
				}

				qPos.add(head);

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

	private static final String _FINDER_COLUMN_G_UGK_HEAD_GROUPID_2 =
		"lvEntry.groupId = ? AND ";

	private static final String _FINDER_COLUMN_G_UGK_HEAD_UNIQUEGROUPKEY_2 =
		"lvEntry.uniqueGroupKey = ? AND ";

	private static final String _FINDER_COLUMN_G_UGK_HEAD_UNIQUEGROUPKEY_3 =
		"(lvEntry.uniqueGroupKey IS NULL OR lvEntry.uniqueGroupKey = '') AND ";

	private static final String _FINDER_COLUMN_G_UGK_HEAD_HEAD_2 =
		"lvEntry.head = ?";

	private FinderPath _finderPathFetchByHeadId;
	private FinderPath _finderPathCountByHeadId;

	/**
	 * Returns the lv entry where headId = &#63; or throws a <code>NoSuchLVEntryException</code> if it could not be found.
	 *
	 * @param headId the head ID
	 * @return the matching lv entry
	 * @throws NoSuchLVEntryException if a matching lv entry could not be found
	 */
	@Override
	public LVEntry findByHeadId(long headId) throws NoSuchLVEntryException {
		LVEntry lvEntry = fetchByHeadId(headId);

		if (lvEntry == null) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("headId=");
			msg.append(headId);

			msg.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(msg.toString());
			}

			throw new NoSuchLVEntryException(msg.toString());
		}

		return lvEntry;
	}

	/**
	 * Returns the lv entry where headId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param headId the head ID
	 * @return the matching lv entry, or <code>null</code> if a matching lv entry could not be found
	 */
	@Override
	public LVEntry fetchByHeadId(long headId) {
		return fetchByHeadId(headId, true);
	}

	/**
	 * Returns the lv entry where headId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param headId the head ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching lv entry, or <code>null</code> if a matching lv entry could not be found
	 */
	@Override
	public LVEntry fetchByHeadId(long headId, boolean useFinderCache) {
		Object[] finderArgs = null;

		if (useFinderCache) {
			finderArgs = new Object[] {headId};
		}

		Object result = null;

		if (useFinderCache) {
			result = finderCache.getResult(
				_finderPathFetchByHeadId, finderArgs, this);
		}

		if (result instanceof LVEntry) {
			LVEntry lvEntry = (LVEntry)result;

			if (headId != lvEntry.getHeadId()) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_SELECT_LVENTRY_WHERE);

			query.append(_FINDER_COLUMN_HEADID_HEADID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(headId);

				List<LVEntry> list = q.list();

				if (list.isEmpty()) {
					if (useFinderCache) {
						finderCache.putResult(
							_finderPathFetchByHeadId, finderArgs, list);
					}
				}
				else {
					LVEntry lvEntry = list.get(0);

					result = lvEntry;

					cacheResult(lvEntry);
				}
			}
			catch (Exception e) {
				if (useFinderCache) {
					finderCache.removeResult(
						_finderPathFetchByHeadId, finderArgs);
				}

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
			return (LVEntry)result;
		}
	}

	/**
	 * Removes the lv entry where headId = &#63; from the database.
	 *
	 * @param headId the head ID
	 * @return the lv entry that was removed
	 */
	@Override
	public LVEntry removeByHeadId(long headId) throws NoSuchLVEntryException {
		LVEntry lvEntry = findByHeadId(headId);

		return remove(lvEntry);
	}

	/**
	 * Returns the number of lv entries where headId = &#63;.
	 *
	 * @param headId the head ID
	 * @return the number of matching lv entries
	 */
	@Override
	public int countByHeadId(long headId) {
		FinderPath finderPath = _finderPathCountByHeadId;

		Object[] finderArgs = new Object[] {headId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_LVENTRY_WHERE);

			query.append(_FINDER_COLUMN_HEADID_HEADID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(headId);

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

	private static final String _FINDER_COLUMN_HEADID_HEADID_2 =
		"lvEntry.headId = ?";

	public LVEntryPersistenceImpl() {
		setModelClass(LVEntry.class);

		setModelImplClass(LVEntryImpl.class);
		setModelPKClass(long.class);
		setEntityCacheEnabled(LVEntryModelImpl.ENTITY_CACHE_ENABLED);

		Map<String, String> dbColumnNames = new HashMap<String, String>();

		dbColumnNames.put("uuid", "uuid_");

		setDBColumnNames(dbColumnNames);
	}

	/**
	 * Caches the lv entry in the entity cache if it is enabled.
	 *
	 * @param lvEntry the lv entry
	 */
	@Override
	public void cacheResult(LVEntry lvEntry) {
		entityCache.putResult(
			LVEntryModelImpl.ENTITY_CACHE_ENABLED, LVEntryImpl.class,
			lvEntry.getPrimaryKey(), lvEntry);

		finderCache.putResult(
			_finderPathFetchByUUID_G_Head,
			new Object[] {
				lvEntry.getUuid(), lvEntry.getGroupId(), lvEntry.isHead()
			},
			lvEntry);

		finderCache.putResult(
			_finderPathFetchByG_UGK_Head,
			new Object[] {
				lvEntry.getGroupId(), lvEntry.getUniqueGroupKey(),
				lvEntry.isHead()
			},
			lvEntry);

		finderCache.putResult(
			_finderPathFetchByHeadId, new Object[] {lvEntry.getHeadId()},
			lvEntry);

		lvEntry.resetOriginalValues();
	}

	/**
	 * Caches the lv entries in the entity cache if it is enabled.
	 *
	 * @param lvEntries the lv entries
	 */
	@Override
	public void cacheResult(List<LVEntry> lvEntries) {
		for (LVEntry lvEntry : lvEntries) {
			if (entityCache.getResult(
					LVEntryModelImpl.ENTITY_CACHE_ENABLED, LVEntryImpl.class,
					lvEntry.getPrimaryKey()) == null) {

				cacheResult(lvEntry);
			}
			else {
				lvEntry.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all lv entries.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(LVEntryImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the lv entry.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(LVEntry lvEntry) {
		entityCache.removeResult(
			LVEntryModelImpl.ENTITY_CACHE_ENABLED, LVEntryImpl.class,
			lvEntry.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache((LVEntryModelImpl)lvEntry, true);
	}

	@Override
	public void clearCache(List<LVEntry> lvEntries) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (LVEntry lvEntry : lvEntries) {
			entityCache.removeResult(
				LVEntryModelImpl.ENTITY_CACHE_ENABLED, LVEntryImpl.class,
				lvEntry.getPrimaryKey());

			clearUniqueFindersCache((LVEntryModelImpl)lvEntry, true);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(
				LVEntryModelImpl.ENTITY_CACHE_ENABLED, LVEntryImpl.class,
				primaryKey);
		}
	}

	protected void cacheUniqueFindersCache(LVEntryModelImpl lvEntryModelImpl) {
		Object[] args = new Object[] {
			lvEntryModelImpl.getUuid(), lvEntryModelImpl.getGroupId(),
			lvEntryModelImpl.isHead()
		};

		finderCache.putResult(
			_finderPathCountByUUID_G_Head, args, Long.valueOf(1), false);
		finderCache.putResult(
			_finderPathFetchByUUID_G_Head, args, lvEntryModelImpl, false);

		args = new Object[] {
			lvEntryModelImpl.getGroupId(), lvEntryModelImpl.getUniqueGroupKey(),
			lvEntryModelImpl.isHead()
		};

		finderCache.putResult(
			_finderPathCountByG_UGK_Head, args, Long.valueOf(1), false);
		finderCache.putResult(
			_finderPathFetchByG_UGK_Head, args, lvEntryModelImpl, false);

		args = new Object[] {lvEntryModelImpl.getHeadId()};

		finderCache.putResult(
			_finderPathCountByHeadId, args, Long.valueOf(1), false);
		finderCache.putResult(
			_finderPathFetchByHeadId, args, lvEntryModelImpl, false);
	}

	protected void clearUniqueFindersCache(
		LVEntryModelImpl lvEntryModelImpl, boolean clearCurrent) {

		if (clearCurrent) {
			Object[] args = new Object[] {
				lvEntryModelImpl.getUuid(), lvEntryModelImpl.getGroupId(),
				lvEntryModelImpl.isHead()
			};

			finderCache.removeResult(_finderPathCountByUUID_G_Head, args);
			finderCache.removeResult(_finderPathFetchByUUID_G_Head, args);
		}

		if ((lvEntryModelImpl.getColumnBitmask() &
			 _finderPathFetchByUUID_G_Head.getColumnBitmask()) != 0) {

			Object[] args = new Object[] {
				lvEntryModelImpl.getOriginalUuid(),
				lvEntryModelImpl.getOriginalGroupId(),
				lvEntryModelImpl.getOriginalHead()
			};

			finderCache.removeResult(_finderPathCountByUUID_G_Head, args);
			finderCache.removeResult(_finderPathFetchByUUID_G_Head, args);
		}

		if (clearCurrent) {
			Object[] args = new Object[] {
				lvEntryModelImpl.getGroupId(),
				lvEntryModelImpl.getUniqueGroupKey(), lvEntryModelImpl.isHead()
			};

			finderCache.removeResult(_finderPathCountByG_UGK_Head, args);
			finderCache.removeResult(_finderPathFetchByG_UGK_Head, args);
		}

		if ((lvEntryModelImpl.getColumnBitmask() &
			 _finderPathFetchByG_UGK_Head.getColumnBitmask()) != 0) {

			Object[] args = new Object[] {
				lvEntryModelImpl.getOriginalGroupId(),
				lvEntryModelImpl.getOriginalUniqueGroupKey(),
				lvEntryModelImpl.getOriginalHead()
			};

			finderCache.removeResult(_finderPathCountByG_UGK_Head, args);
			finderCache.removeResult(_finderPathFetchByG_UGK_Head, args);
		}

		if (clearCurrent) {
			Object[] args = new Object[] {lvEntryModelImpl.getHeadId()};

			finderCache.removeResult(_finderPathCountByHeadId, args);
			finderCache.removeResult(_finderPathFetchByHeadId, args);
		}

		if ((lvEntryModelImpl.getColumnBitmask() &
			 _finderPathFetchByHeadId.getColumnBitmask()) != 0) {

			Object[] args = new Object[] {lvEntryModelImpl.getOriginalHeadId()};

			finderCache.removeResult(_finderPathCountByHeadId, args);
			finderCache.removeResult(_finderPathFetchByHeadId, args);
		}
	}

	/**
	 * Creates a new lv entry with the primary key. Does not add the lv entry to the database.
	 *
	 * @param lvEntryId the primary key for the new lv entry
	 * @return the new lv entry
	 */
	@Override
	public LVEntry create(long lvEntryId) {
		LVEntry lvEntry = new LVEntryImpl();

		lvEntry.setNew(true);
		lvEntry.setPrimaryKey(lvEntryId);

		String uuid = PortalUUIDUtil.generate();

		lvEntry.setUuid(uuid);

		lvEntry.setCompanyId(CompanyThreadLocal.getCompanyId());

		return lvEntry;
	}

	/**
	 * Removes the lv entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param lvEntryId the primary key of the lv entry
	 * @return the lv entry that was removed
	 * @throws NoSuchLVEntryException if a lv entry with the primary key could not be found
	 */
	@Override
	public LVEntry remove(long lvEntryId) throws NoSuchLVEntryException {
		return remove((Serializable)lvEntryId);
	}

	/**
	 * Removes the lv entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the lv entry
	 * @return the lv entry that was removed
	 * @throws NoSuchLVEntryException if a lv entry with the primary key could not be found
	 */
	@Override
	public LVEntry remove(Serializable primaryKey)
		throws NoSuchLVEntryException {

		Session session = null;

		try {
			session = openSession();

			LVEntry lvEntry = (LVEntry)session.get(
				LVEntryImpl.class, primaryKey);

			if (lvEntry == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchLVEntryException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(lvEntry);
		}
		catch (NoSuchLVEntryException nsee) {
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
	protected LVEntry removeImpl(LVEntry lvEntry) {
		lvEntryToBigDecimalEntryTableMapper.deleteLeftPrimaryKeyTableMappings(
			lvEntry.getPrimaryKey());

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(lvEntry)) {
				lvEntry = (LVEntry)session.get(
					LVEntryImpl.class, lvEntry.getPrimaryKeyObj());
			}

			if (lvEntry != null) {
				session.delete(lvEntry);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (lvEntry != null) {
			clearCache(lvEntry);
		}

		return lvEntry;
	}

	@Override
	public LVEntry updateImpl(LVEntry lvEntry) {
		boolean isNew = lvEntry.isNew();

		if (!(lvEntry instanceof LVEntryModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(lvEntry.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(lvEntry);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in lvEntry proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom LVEntry implementation " +
					lvEntry.getClass());
		}

		LVEntryModelImpl lvEntryModelImpl = (LVEntryModelImpl)lvEntry;

		if (Validator.isNull(lvEntry.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			lvEntry.setUuid(uuid);
		}

		Session session = null;

		try {
			session = openSession();

			if (lvEntry.isNew()) {
				session.save(lvEntry);

				lvEntry.setNew(false);
			}
			else {
				lvEntry = (LVEntry)session.merge(lvEntry);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (!LVEntryModelImpl.COLUMN_BITMASK_ENABLED) {
			finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}
		else if (isNew) {
			Object[] args = new Object[] {lvEntryModelImpl.getUuid()};

			finderCache.removeResult(_finderPathCountByUuid, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByUuid, args);

			args = new Object[] {
				lvEntryModelImpl.getUuid(), lvEntryModelImpl.isHead()
			};

			finderCache.removeResult(_finderPathCountByUuid_Head, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByUuid_Head, args);

			args = new Object[] {
				lvEntryModelImpl.getUuid(), lvEntryModelImpl.getGroupId()
			};

			finderCache.removeResult(_finderPathCountByUUID_G, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByUUID_G, args);

			args = new Object[] {
				lvEntryModelImpl.getUuid(), lvEntryModelImpl.getCompanyId()
			};

			finderCache.removeResult(_finderPathCountByUuid_C, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByUuid_C, args);

			args = new Object[] {
				lvEntryModelImpl.getUuid(), lvEntryModelImpl.getCompanyId(),
				lvEntryModelImpl.isHead()
			};

			finderCache.removeResult(_finderPathCountByUuid_C_Head, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByUuid_C_Head, args);

			args = new Object[] {lvEntryModelImpl.getGroupId()};

			finderCache.removeResult(_finderPathCountByGroupId, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByGroupId, args);

			args = new Object[] {
				lvEntryModelImpl.getGroupId(), lvEntryModelImpl.isHead()
			};

			finderCache.removeResult(_finderPathCountByGroupId_Head, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByGroupId_Head, args);

			args = new Object[] {
				lvEntryModelImpl.getGroupId(),
				lvEntryModelImpl.getUniqueGroupKey()
			};

			finderCache.removeResult(_finderPathCountByG_UGK, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByG_UGK, args);

			finderCache.removeResult(_finderPathCountAll, FINDER_ARGS_EMPTY);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindAll, FINDER_ARGS_EMPTY);
		}
		else {
			if ((lvEntryModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByUuid.getColumnBitmask()) !=
					 0) {

				Object[] args = new Object[] {
					lvEntryModelImpl.getOriginalUuid()
				};

				finderCache.removeResult(_finderPathCountByUuid, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByUuid, args);

				args = new Object[] {lvEntryModelImpl.getUuid()};

				finderCache.removeResult(_finderPathCountByUuid, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByUuid, args);
			}

			if ((lvEntryModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByUuid_Head.
					 getColumnBitmask()) != 0) {

				Object[] args = new Object[] {
					lvEntryModelImpl.getOriginalUuid(),
					lvEntryModelImpl.getOriginalHead()
				};

				finderCache.removeResult(_finderPathCountByUuid_Head, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByUuid_Head, args);

				args = new Object[] {
					lvEntryModelImpl.getUuid(), lvEntryModelImpl.isHead()
				};

				finderCache.removeResult(_finderPathCountByUuid_Head, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByUuid_Head, args);
			}

			if ((lvEntryModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByUUID_G.getColumnBitmask()) !=
					 0) {

				Object[] args = new Object[] {
					lvEntryModelImpl.getOriginalUuid(),
					lvEntryModelImpl.getOriginalGroupId()
				};

				finderCache.removeResult(_finderPathCountByUUID_G, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByUUID_G, args);

				args = new Object[] {
					lvEntryModelImpl.getUuid(), lvEntryModelImpl.getGroupId()
				};

				finderCache.removeResult(_finderPathCountByUUID_G, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByUUID_G, args);
			}

			if ((lvEntryModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByUuid_C.getColumnBitmask()) !=
					 0) {

				Object[] args = new Object[] {
					lvEntryModelImpl.getOriginalUuid(),
					lvEntryModelImpl.getOriginalCompanyId()
				};

				finderCache.removeResult(_finderPathCountByUuid_C, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByUuid_C, args);

				args = new Object[] {
					lvEntryModelImpl.getUuid(), lvEntryModelImpl.getCompanyId()
				};

				finderCache.removeResult(_finderPathCountByUuid_C, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByUuid_C, args);
			}

			if ((lvEntryModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByUuid_C_Head.
					 getColumnBitmask()) != 0) {

				Object[] args = new Object[] {
					lvEntryModelImpl.getOriginalUuid(),
					lvEntryModelImpl.getOriginalCompanyId(),
					lvEntryModelImpl.getOriginalHead()
				};

				finderCache.removeResult(_finderPathCountByUuid_C_Head, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByUuid_C_Head, args);

				args = new Object[] {
					lvEntryModelImpl.getUuid(), lvEntryModelImpl.getCompanyId(),
					lvEntryModelImpl.isHead()
				};

				finderCache.removeResult(_finderPathCountByUuid_C_Head, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByUuid_C_Head, args);
			}

			if ((lvEntryModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByGroupId.
					 getColumnBitmask()) != 0) {

				Object[] args = new Object[] {
					lvEntryModelImpl.getOriginalGroupId()
				};

				finderCache.removeResult(_finderPathCountByGroupId, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByGroupId, args);

				args = new Object[] {lvEntryModelImpl.getGroupId()};

				finderCache.removeResult(_finderPathCountByGroupId, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByGroupId, args);
			}

			if ((lvEntryModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByGroupId_Head.
					 getColumnBitmask()) != 0) {

				Object[] args = new Object[] {
					lvEntryModelImpl.getOriginalGroupId(),
					lvEntryModelImpl.getOriginalHead()
				};

				finderCache.removeResult(_finderPathCountByGroupId_Head, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByGroupId_Head, args);

				args = new Object[] {
					lvEntryModelImpl.getGroupId(), lvEntryModelImpl.isHead()
				};

				finderCache.removeResult(_finderPathCountByGroupId_Head, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByGroupId_Head, args);
			}

			if ((lvEntryModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByG_UGK.getColumnBitmask()) !=
					 0) {

				Object[] args = new Object[] {
					lvEntryModelImpl.getOriginalGroupId(),
					lvEntryModelImpl.getOriginalUniqueGroupKey()
				};

				finderCache.removeResult(_finderPathCountByG_UGK, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByG_UGK, args);

				args = new Object[] {
					lvEntryModelImpl.getGroupId(),
					lvEntryModelImpl.getUniqueGroupKey()
				};

				finderCache.removeResult(_finderPathCountByG_UGK, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByG_UGK, args);
			}
		}

		entityCache.putResult(
			LVEntryModelImpl.ENTITY_CACHE_ENABLED, LVEntryImpl.class,
			lvEntry.getPrimaryKey(), lvEntry, false);

		clearUniqueFindersCache(lvEntryModelImpl, false);
		cacheUniqueFindersCache(lvEntryModelImpl);

		lvEntry.resetOriginalValues();

		return lvEntry;
	}

	/**
	 * Returns the lv entry with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the lv entry
	 * @return the lv entry
	 * @throws NoSuchLVEntryException if a lv entry with the primary key could not be found
	 */
	@Override
	public LVEntry findByPrimaryKey(Serializable primaryKey)
		throws NoSuchLVEntryException {

		LVEntry lvEntry = fetchByPrimaryKey(primaryKey);

		if (lvEntry == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchLVEntryException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return lvEntry;
	}

	/**
	 * Returns the lv entry with the primary key or throws a <code>NoSuchLVEntryException</code> if it could not be found.
	 *
	 * @param lvEntryId the primary key of the lv entry
	 * @return the lv entry
	 * @throws NoSuchLVEntryException if a lv entry with the primary key could not be found
	 */
	@Override
	public LVEntry findByPrimaryKey(long lvEntryId)
		throws NoSuchLVEntryException {

		return findByPrimaryKey((Serializable)lvEntryId);
	}

	/**
	 * Returns the lv entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param lvEntryId the primary key of the lv entry
	 * @return the lv entry, or <code>null</code> if a lv entry with the primary key could not be found
	 */
	@Override
	public LVEntry fetchByPrimaryKey(long lvEntryId) {
		return fetchByPrimaryKey((Serializable)lvEntryId);
	}

	/**
	 * Returns all the lv entries.
	 *
	 * @return the lv entries
	 */
	@Override
	public List<LVEntry> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the lv entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LVEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of lv entries
	 * @param end the upper bound of the range of lv entries (not inclusive)
	 * @return the range of lv entries
	 */
	@Override
	public List<LVEntry> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the lv entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LVEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of lv entries
	 * @param end the upper bound of the range of lv entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of lv entries
	 */
	@Override
	public List<LVEntry> findAll(
		int start, int end, OrderByComparator<LVEntry> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the lv entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LVEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of lv entries
	 * @param end the upper bound of the range of lv entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of lv entries
	 */
	@Override
	public List<LVEntry> findAll(
		int start, int end, OrderByComparator<LVEntry> orderByComparator,
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

		List<LVEntry> list = null;

		if (useFinderCache) {
			list = (List<LVEntry>)finderCache.getResult(
				finderPath, finderArgs, this);
		}

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				query.append(_SQL_SELECT_LVENTRY);

				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_LVENTRY;

				sql = sql.concat(LVEntryModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				list = (List<LVEntry>)QueryUtil.list(
					q, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					finderCache.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception e) {
				if (useFinderCache) {
					finderCache.removeResult(finderPath, finderArgs);
				}

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Removes all the lv entries from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (LVEntry lvEntry : findAll()) {
			remove(lvEntry);
		}
	}

	/**
	 * Returns the number of lv entries.
	 *
	 * @return the number of lv entries
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(
			_finderPathCountAll, FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_LVENTRY);

				count = (Long)q.uniqueResult();

				finderCache.putResult(
					_finderPathCountAll, FINDER_ARGS_EMPTY, count);
			}
			catch (Exception e) {
				finderCache.removeResult(
					_finderPathCountAll, FINDER_ARGS_EMPTY);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the primaryKeys of big decimal entries associated with the lv entry.
	 *
	 * @param pk the primary key of the lv entry
	 * @return long[] of the primaryKeys of big decimal entries associated with the lv entry
	 */
	@Override
	public long[] getBigDecimalEntryPrimaryKeys(long pk) {
		long[] pks = lvEntryToBigDecimalEntryTableMapper.getRightPrimaryKeys(
			pk);

		return pks.clone();
	}

	/**
	 * Returns all the big decimal entries associated with the lv entry.
	 *
	 * @param pk the primary key of the lv entry
	 * @return the big decimal entries associated with the lv entry
	 */
	@Override
	public List
		<com.liferay.portal.tools.service.builder.test.model.BigDecimalEntry>
			getBigDecimalEntries(long pk) {

		return getBigDecimalEntries(pk, QueryUtil.ALL_POS, QueryUtil.ALL_POS);
	}

	/**
	 * Returns a range of all the big decimal entries associated with the lv entry.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LVEntryModelImpl</code>.
	 * </p>
	 *
	 * @param pk the primary key of the lv entry
	 * @param start the lower bound of the range of lv entries
	 * @param end the upper bound of the range of lv entries (not inclusive)
	 * @return the range of big decimal entries associated with the lv entry
	 */
	@Override
	public List
		<com.liferay.portal.tools.service.builder.test.model.BigDecimalEntry>
			getBigDecimalEntries(long pk, int start, int end) {

		return getBigDecimalEntries(pk, start, end, null);
	}

	/**
	 * Returns an ordered range of all the big decimal entries associated with the lv entry.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LVEntryModelImpl</code>.
	 * </p>
	 *
	 * @param pk the primary key of the lv entry
	 * @param start the lower bound of the range of lv entries
	 * @param end the upper bound of the range of lv entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of big decimal entries associated with the lv entry
	 */
	@Override
	public List
		<com.liferay.portal.tools.service.builder.test.model.BigDecimalEntry>
			getBigDecimalEntries(
				long pk, int start, int end,
				OrderByComparator
					<com.liferay.portal.tools.service.builder.test.model.
						BigDecimalEntry> orderByComparator) {

		return lvEntryToBigDecimalEntryTableMapper.getRightBaseModels(
			pk, start, end, orderByComparator);
	}

	/**
	 * Returns the number of big decimal entries associated with the lv entry.
	 *
	 * @param pk the primary key of the lv entry
	 * @return the number of big decimal entries associated with the lv entry
	 */
	@Override
	public int getBigDecimalEntriesSize(long pk) {
		long[] pks = lvEntryToBigDecimalEntryTableMapper.getRightPrimaryKeys(
			pk);

		return pks.length;
	}

	/**
	 * Returns <code>true</code> if the big decimal entry is associated with the lv entry.
	 *
	 * @param pk the primary key of the lv entry
	 * @param bigDecimalEntryPK the primary key of the big decimal entry
	 * @return <code>true</code> if the big decimal entry is associated with the lv entry; <code>false</code> otherwise
	 */
	@Override
	public boolean containsBigDecimalEntry(long pk, long bigDecimalEntryPK) {
		return lvEntryToBigDecimalEntryTableMapper.containsTableMapping(
			pk, bigDecimalEntryPK);
	}

	/**
	 * Returns <code>true</code> if the lv entry has any big decimal entries associated with it.
	 *
	 * @param pk the primary key of the lv entry to check for associations with big decimal entries
	 * @return <code>true</code> if the lv entry has any big decimal entries associated with it; <code>false</code> otherwise
	 */
	@Override
	public boolean containsBigDecimalEntries(long pk) {
		if (getBigDecimalEntriesSize(pk) > 0) {
			return true;
		}
		else {
			return false;
		}
	}

	/**
	 * Adds an association between the lv entry and the big decimal entry. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the lv entry
	 * @param bigDecimalEntryPK the primary key of the big decimal entry
	 */
	@Override
	public void addBigDecimalEntry(long pk, long bigDecimalEntryPK) {
		LVEntry lvEntry = fetchByPrimaryKey(pk);

		if (lvEntry == null) {
			lvEntryToBigDecimalEntryTableMapper.addTableMapping(
				CompanyThreadLocal.getCompanyId(), pk, bigDecimalEntryPK);
		}
		else {
			lvEntryToBigDecimalEntryTableMapper.addTableMapping(
				lvEntry.getCompanyId(), pk, bigDecimalEntryPK);
		}
	}

	/**
	 * Adds an association between the lv entry and the big decimal entry. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the lv entry
	 * @param bigDecimalEntry the big decimal entry
	 */
	@Override
	public void addBigDecimalEntry(
		long pk,
		com.liferay.portal.tools.service.builder.test.model.BigDecimalEntry
			bigDecimalEntry) {

		LVEntry lvEntry = fetchByPrimaryKey(pk);

		if (lvEntry == null) {
			lvEntryToBigDecimalEntryTableMapper.addTableMapping(
				CompanyThreadLocal.getCompanyId(), pk,
				bigDecimalEntry.getPrimaryKey());
		}
		else {
			lvEntryToBigDecimalEntryTableMapper.addTableMapping(
				lvEntry.getCompanyId(), pk, bigDecimalEntry.getPrimaryKey());
		}
	}

	/**
	 * Adds an association between the lv entry and the big decimal entries. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the lv entry
	 * @param bigDecimalEntryPKs the primary keys of the big decimal entries
	 */
	@Override
	public void addBigDecimalEntries(long pk, long[] bigDecimalEntryPKs) {
		long companyId = 0;

		LVEntry lvEntry = fetchByPrimaryKey(pk);

		if (lvEntry == null) {
			companyId = CompanyThreadLocal.getCompanyId();
		}
		else {
			companyId = lvEntry.getCompanyId();
		}

		lvEntryToBigDecimalEntryTableMapper.addTableMappings(
			companyId, pk, bigDecimalEntryPKs);
	}

	/**
	 * Adds an association between the lv entry and the big decimal entries. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the lv entry
	 * @param bigDecimalEntries the big decimal entries
	 */
	@Override
	public void addBigDecimalEntries(
		long pk,
		List
			<com.liferay.portal.tools.service.builder.test.model.
				BigDecimalEntry> bigDecimalEntries) {

		addBigDecimalEntries(
			pk,
			ListUtil.toLongArray(
				bigDecimalEntries,
				com.liferay.portal.tools.service.builder.test.model.
					BigDecimalEntry.BIG_DECIMAL_ENTRY_ID_ACCESSOR));
	}

	/**
	 * Clears all associations between the lv entry and its big decimal entries. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the lv entry to clear the associated big decimal entries from
	 */
	@Override
	public void clearBigDecimalEntries(long pk) {
		lvEntryToBigDecimalEntryTableMapper.deleteLeftPrimaryKeyTableMappings(
			pk);
	}

	/**
	 * Removes the association between the lv entry and the big decimal entry. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the lv entry
	 * @param bigDecimalEntryPK the primary key of the big decimal entry
	 */
	@Override
	public void removeBigDecimalEntry(long pk, long bigDecimalEntryPK) {
		lvEntryToBigDecimalEntryTableMapper.deleteTableMapping(
			pk, bigDecimalEntryPK);
	}

	/**
	 * Removes the association between the lv entry and the big decimal entry. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the lv entry
	 * @param bigDecimalEntry the big decimal entry
	 */
	@Override
	public void removeBigDecimalEntry(
		long pk,
		com.liferay.portal.tools.service.builder.test.model.BigDecimalEntry
			bigDecimalEntry) {

		lvEntryToBigDecimalEntryTableMapper.deleteTableMapping(
			pk, bigDecimalEntry.getPrimaryKey());
	}

	/**
	 * Removes the association between the lv entry and the big decimal entries. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the lv entry
	 * @param bigDecimalEntryPKs the primary keys of the big decimal entries
	 */
	@Override
	public void removeBigDecimalEntries(long pk, long[] bigDecimalEntryPKs) {
		lvEntryToBigDecimalEntryTableMapper.deleteTableMappings(
			pk, bigDecimalEntryPKs);
	}

	/**
	 * Removes the association between the lv entry and the big decimal entries. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the lv entry
	 * @param bigDecimalEntries the big decimal entries
	 */
	@Override
	public void removeBigDecimalEntries(
		long pk,
		List
			<com.liferay.portal.tools.service.builder.test.model.
				BigDecimalEntry> bigDecimalEntries) {

		removeBigDecimalEntries(
			pk,
			ListUtil.toLongArray(
				bigDecimalEntries,
				com.liferay.portal.tools.service.builder.test.model.
					BigDecimalEntry.BIG_DECIMAL_ENTRY_ID_ACCESSOR));
	}

	/**
	 * Sets the big decimal entries associated with the lv entry, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the lv entry
	 * @param bigDecimalEntryPKs the primary keys of the big decimal entries to be associated with the lv entry
	 */
	@Override
	public void setBigDecimalEntries(long pk, long[] bigDecimalEntryPKs) {
		Set<Long> newBigDecimalEntryPKsSet = SetUtil.fromArray(
			bigDecimalEntryPKs);
		Set<Long> oldBigDecimalEntryPKsSet = SetUtil.fromArray(
			lvEntryToBigDecimalEntryTableMapper.getRightPrimaryKeys(pk));

		Set<Long> removeBigDecimalEntryPKsSet = new HashSet<Long>(
			oldBigDecimalEntryPKsSet);

		removeBigDecimalEntryPKsSet.removeAll(newBigDecimalEntryPKsSet);

		lvEntryToBigDecimalEntryTableMapper.deleteTableMappings(
			pk, ArrayUtil.toLongArray(removeBigDecimalEntryPKsSet));

		newBigDecimalEntryPKsSet.removeAll(oldBigDecimalEntryPKsSet);

		long companyId = 0;

		LVEntry lvEntry = fetchByPrimaryKey(pk);

		if (lvEntry == null) {
			companyId = CompanyThreadLocal.getCompanyId();
		}
		else {
			companyId = lvEntry.getCompanyId();
		}

		lvEntryToBigDecimalEntryTableMapper.addTableMappings(
			companyId, pk, ArrayUtil.toLongArray(newBigDecimalEntryPKsSet));
	}

	/**
	 * Sets the big decimal entries associated with the lv entry, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the lv entry
	 * @param bigDecimalEntries the big decimal entries to be associated with the lv entry
	 */
	@Override
	public void setBigDecimalEntries(
		long pk,
		List
			<com.liferay.portal.tools.service.builder.test.model.
				BigDecimalEntry> bigDecimalEntries) {

		try {
			long[] bigDecimalEntryPKs = new long[bigDecimalEntries.size()];

			for (int i = 0; i < bigDecimalEntries.size(); i++) {
				com.liferay.portal.tools.service.builder.test.model.
					BigDecimalEntry bigDecimalEntry = bigDecimalEntries.get(i);

				bigDecimalEntryPKs[i] = bigDecimalEntry.getPrimaryKey();
			}

			setBigDecimalEntries(pk, bigDecimalEntryPKs);
		}
		catch (Exception e) {
			throw processException(e);
		}
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
		return "lvEntryId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_LVENTRY;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return LVEntryModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the lv entry persistence.
	 */
	public void afterPropertiesSet() {
		lvEntryToBigDecimalEntryTableMapper = TableMapperFactory.getTableMapper(
			"BigDecimalEntries_LVEntries", "companyId", "lvEntryId",
			"bigDecimalEntryId", this, bigDecimalEntryPersistence);

		_finderPathWithPaginationFindAll = new FinderPath(
			LVEntryModelImpl.ENTITY_CACHE_ENABLED,
			LVEntryModelImpl.FINDER_CACHE_ENABLED, LVEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);

		_finderPathWithoutPaginationFindAll = new FinderPath(
			LVEntryModelImpl.ENTITY_CACHE_ENABLED,
			LVEntryModelImpl.FINDER_CACHE_ENABLED, LVEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll",
			new String[0]);

		_finderPathCountAll = new FinderPath(
			LVEntryModelImpl.ENTITY_CACHE_ENABLED,
			LVEntryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll",
			new String[0]);

		_finderPathWithPaginationFindByUuid = new FinderPath(
			LVEntryModelImpl.ENTITY_CACHE_ENABLED,
			LVEntryModelImpl.FINDER_CACHE_ENABLED, LVEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUuid",
			new String[] {
				String.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByUuid = new FinderPath(
			LVEntryModelImpl.ENTITY_CACHE_ENABLED,
			LVEntryModelImpl.FINDER_CACHE_ENABLED, LVEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid",
			new String[] {String.class.getName()},
			LVEntryModelImpl.UUID_COLUMN_BITMASK);

		_finderPathCountByUuid = new FinderPath(
			LVEntryModelImpl.ENTITY_CACHE_ENABLED,
			LVEntryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUuid",
			new String[] {String.class.getName()});

		_finderPathWithPaginationFindByUuid_Head = new FinderPath(
			LVEntryModelImpl.ENTITY_CACHE_ENABLED,
			LVEntryModelImpl.FINDER_CACHE_ENABLED, LVEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUuid_Head",
			new String[] {
				String.class.getName(), Boolean.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByUuid_Head = new FinderPath(
			LVEntryModelImpl.ENTITY_CACHE_ENABLED,
			LVEntryModelImpl.FINDER_CACHE_ENABLED, LVEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid_Head",
			new String[] {String.class.getName(), Boolean.class.getName()},
			LVEntryModelImpl.UUID_COLUMN_BITMASK |
			LVEntryModelImpl.HEAD_COLUMN_BITMASK);

		_finderPathCountByUuid_Head = new FinderPath(
			LVEntryModelImpl.ENTITY_CACHE_ENABLED,
			LVEntryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUuid_Head",
			new String[] {String.class.getName(), Boolean.class.getName()});

		_finderPathWithPaginationFindByUUID_G = new FinderPath(
			LVEntryModelImpl.ENTITY_CACHE_ENABLED,
			LVEntryModelImpl.FINDER_CACHE_ENABLED, LVEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUUID_G",
			new String[] {
				String.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByUUID_G = new FinderPath(
			LVEntryModelImpl.ENTITY_CACHE_ENABLED,
			LVEntryModelImpl.FINDER_CACHE_ENABLED, LVEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUUID_G",
			new String[] {String.class.getName(), Long.class.getName()},
			LVEntryModelImpl.UUID_COLUMN_BITMASK |
			LVEntryModelImpl.GROUPID_COLUMN_BITMASK);

		_finderPathCountByUUID_G = new FinderPath(
			LVEntryModelImpl.ENTITY_CACHE_ENABLED,
			LVEntryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUUID_G",
			new String[] {String.class.getName(), Long.class.getName()});

		_finderPathFetchByUUID_G_Head = new FinderPath(
			LVEntryModelImpl.ENTITY_CACHE_ENABLED,
			LVEntryModelImpl.FINDER_CACHE_ENABLED, LVEntryImpl.class,
			FINDER_CLASS_NAME_ENTITY, "fetchByUUID_G_Head",
			new String[] {
				String.class.getName(), Long.class.getName(),
				Boolean.class.getName()
			},
			LVEntryModelImpl.UUID_COLUMN_BITMASK |
			LVEntryModelImpl.GROUPID_COLUMN_BITMASK |
			LVEntryModelImpl.HEAD_COLUMN_BITMASK);

		_finderPathCountByUUID_G_Head = new FinderPath(
			LVEntryModelImpl.ENTITY_CACHE_ENABLED,
			LVEntryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUUID_G_Head",
			new String[] {
				String.class.getName(), Long.class.getName(),
				Boolean.class.getName()
			});

		_finderPathWithPaginationFindByUuid_C = new FinderPath(
			LVEntryModelImpl.ENTITY_CACHE_ENABLED,
			LVEntryModelImpl.FINDER_CACHE_ENABLED, LVEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUuid_C",
			new String[] {
				String.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByUuid_C = new FinderPath(
			LVEntryModelImpl.ENTITY_CACHE_ENABLED,
			LVEntryModelImpl.FINDER_CACHE_ENABLED, LVEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid_C",
			new String[] {String.class.getName(), Long.class.getName()},
			LVEntryModelImpl.UUID_COLUMN_BITMASK |
			LVEntryModelImpl.COMPANYID_COLUMN_BITMASK);

		_finderPathCountByUuid_C = new FinderPath(
			LVEntryModelImpl.ENTITY_CACHE_ENABLED,
			LVEntryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUuid_C",
			new String[] {String.class.getName(), Long.class.getName()});

		_finderPathWithPaginationFindByUuid_C_Head = new FinderPath(
			LVEntryModelImpl.ENTITY_CACHE_ENABLED,
			LVEntryModelImpl.FINDER_CACHE_ENABLED, LVEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUuid_C_Head",
			new String[] {
				String.class.getName(), Long.class.getName(),
				Boolean.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByUuid_C_Head = new FinderPath(
			LVEntryModelImpl.ENTITY_CACHE_ENABLED,
			LVEntryModelImpl.FINDER_CACHE_ENABLED, LVEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid_C_Head",
			new String[] {
				String.class.getName(), Long.class.getName(),
				Boolean.class.getName()
			},
			LVEntryModelImpl.UUID_COLUMN_BITMASK |
			LVEntryModelImpl.COMPANYID_COLUMN_BITMASK |
			LVEntryModelImpl.HEAD_COLUMN_BITMASK);

		_finderPathCountByUuid_C_Head = new FinderPath(
			LVEntryModelImpl.ENTITY_CACHE_ENABLED,
			LVEntryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUuid_C_Head",
			new String[] {
				String.class.getName(), Long.class.getName(),
				Boolean.class.getName()
			});

		_finderPathWithPaginationFindByGroupId = new FinderPath(
			LVEntryModelImpl.ENTITY_CACHE_ENABLED,
			LVEntryModelImpl.FINDER_CACHE_ENABLED, LVEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByGroupId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByGroupId = new FinderPath(
			LVEntryModelImpl.ENTITY_CACHE_ENABLED,
			LVEntryModelImpl.FINDER_CACHE_ENABLED, LVEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByGroupId",
			new String[] {Long.class.getName()},
			LVEntryModelImpl.GROUPID_COLUMN_BITMASK);

		_finderPathCountByGroupId = new FinderPath(
			LVEntryModelImpl.ENTITY_CACHE_ENABLED,
			LVEntryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByGroupId",
			new String[] {Long.class.getName()});

		_finderPathWithPaginationCountByGroupId = new FinderPath(
			LVEntryModelImpl.ENTITY_CACHE_ENABLED,
			LVEntryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "countByGroupId",
			new String[] {Long.class.getName()});

		_finderPathWithPaginationFindByGroupId_Head = new FinderPath(
			LVEntryModelImpl.ENTITY_CACHE_ENABLED,
			LVEntryModelImpl.FINDER_CACHE_ENABLED, LVEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByGroupId_Head",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByGroupId_Head = new FinderPath(
			LVEntryModelImpl.ENTITY_CACHE_ENABLED,
			LVEntryModelImpl.FINDER_CACHE_ENABLED, LVEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByGroupId_Head",
			new String[] {Long.class.getName(), Boolean.class.getName()},
			LVEntryModelImpl.GROUPID_COLUMN_BITMASK |
			LVEntryModelImpl.HEAD_COLUMN_BITMASK);

		_finderPathCountByGroupId_Head = new FinderPath(
			LVEntryModelImpl.ENTITY_CACHE_ENABLED,
			LVEntryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByGroupId_Head",
			new String[] {Long.class.getName(), Boolean.class.getName()});

		_finderPathWithPaginationCountByGroupId_Head = new FinderPath(
			LVEntryModelImpl.ENTITY_CACHE_ENABLED,
			LVEntryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "countByGroupId_Head",
			new String[] {Long.class.getName(), Boolean.class.getName()});

		_finderPathWithPaginationFindByG_UGK = new FinderPath(
			LVEntryModelImpl.ENTITY_CACHE_ENABLED,
			LVEntryModelImpl.FINDER_CACHE_ENABLED, LVEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_UGK",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByG_UGK = new FinderPath(
			LVEntryModelImpl.ENTITY_CACHE_ENABLED,
			LVEntryModelImpl.FINDER_CACHE_ENABLED, LVEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByG_UGK",
			new String[] {Long.class.getName(), String.class.getName()},
			LVEntryModelImpl.GROUPID_COLUMN_BITMASK |
			LVEntryModelImpl.UNIQUEGROUPKEY_COLUMN_BITMASK);

		_finderPathCountByG_UGK = new FinderPath(
			LVEntryModelImpl.ENTITY_CACHE_ENABLED,
			LVEntryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_UGK",
			new String[] {Long.class.getName(), String.class.getName()});

		_finderPathFetchByG_UGK_Head = new FinderPath(
			LVEntryModelImpl.ENTITY_CACHE_ENABLED,
			LVEntryModelImpl.FINDER_CACHE_ENABLED, LVEntryImpl.class,
			FINDER_CLASS_NAME_ENTITY, "fetchByG_UGK_Head",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Boolean.class.getName()
			},
			LVEntryModelImpl.GROUPID_COLUMN_BITMASK |
			LVEntryModelImpl.UNIQUEGROUPKEY_COLUMN_BITMASK |
			LVEntryModelImpl.HEAD_COLUMN_BITMASK);

		_finderPathCountByG_UGK_Head = new FinderPath(
			LVEntryModelImpl.ENTITY_CACHE_ENABLED,
			LVEntryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_UGK_Head",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Boolean.class.getName()
			});

		_finderPathFetchByHeadId = new FinderPath(
			LVEntryModelImpl.ENTITY_CACHE_ENABLED,
			LVEntryModelImpl.FINDER_CACHE_ENABLED, LVEntryImpl.class,
			FINDER_CLASS_NAME_ENTITY, "fetchByHeadId",
			new String[] {Long.class.getName()},
			LVEntryModelImpl.HEADID_COLUMN_BITMASK);

		_finderPathCountByHeadId = new FinderPath(
			LVEntryModelImpl.ENTITY_CACHE_ENABLED,
			LVEntryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByHeadId",
			new String[] {Long.class.getName()});
	}

	public void destroy() {
		entityCache.removeCache(LVEntryImpl.class.getName());
		finderCache.removeCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		TableMapperFactory.removeTableMapper("BigDecimalEntries_LVEntries");
	}

	@ServiceReference(type = EntityCache.class)
	protected EntityCache entityCache;

	@ServiceReference(type = FinderCache.class)
	protected FinderCache finderCache;

	@BeanReference(type = BigDecimalEntryPersistence.class)
	protected BigDecimalEntryPersistence bigDecimalEntryPersistence;

	protected TableMapper
		<LVEntry,
		 com.liferay.portal.tools.service.builder.test.model.BigDecimalEntry>
			lvEntryToBigDecimalEntryTableMapper;

	private static final String _SQL_SELECT_LVENTRY =
		"SELECT lvEntry FROM LVEntry lvEntry";

	private static final String _SQL_SELECT_LVENTRY_WHERE =
		"SELECT lvEntry FROM LVEntry lvEntry WHERE ";

	private static final String _SQL_COUNT_LVENTRY =
		"SELECT COUNT(lvEntry) FROM LVEntry lvEntry";

	private static final String _SQL_COUNT_LVENTRY_WHERE =
		"SELECT COUNT(lvEntry) FROM LVEntry lvEntry WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS = "lvEntry.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No LVEntry exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No LVEntry exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		LVEntryPersistenceImpl.class);

	private static final Set<String> _badColumnNames = SetUtil.fromArray(
		new String[] {"uuid"});

}