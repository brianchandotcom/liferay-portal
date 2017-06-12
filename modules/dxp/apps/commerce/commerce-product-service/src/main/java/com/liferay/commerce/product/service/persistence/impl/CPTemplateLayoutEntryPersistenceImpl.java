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

import com.liferay.commerce.product.exception.NoSuchCPTemplateLayoutEntryException;
import com.liferay.commerce.product.model.CPTemplateLayoutEntry;
import com.liferay.commerce.product.model.impl.CPTemplateLayoutEntryImpl;
import com.liferay.commerce.product.model.impl.CPTemplateLayoutEntryModelImpl;
import com.liferay.commerce.product.service.persistence.CPTemplateLayoutEntryPersistence;

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
 * The persistence implementation for the cp template layout entry service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Marco Leo
 * @see CPTemplateLayoutEntryPersistence
 * @see com.liferay.commerce.product.service.persistence.CPTemplateLayoutEntryUtil
 * @generated
 */
@ProviderType
public class CPTemplateLayoutEntryPersistenceImpl extends BasePersistenceImpl<CPTemplateLayoutEntry>
	implements CPTemplateLayoutEntryPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link CPTemplateLayoutEntryUtil} to access the cp template layout entry persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = CPTemplateLayoutEntryImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(CPTemplateLayoutEntryModelImpl.ENTITY_CACHE_ENABLED,
			CPTemplateLayoutEntryModelImpl.FINDER_CACHE_ENABLED,
			CPTemplateLayoutEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(CPTemplateLayoutEntryModelImpl.ENTITY_CACHE_ENABLED,
			CPTemplateLayoutEntryModelImpl.FINDER_CACHE_ENABLED,
			CPTemplateLayoutEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(CPTemplateLayoutEntryModelImpl.ENTITY_CACHE_ENABLED,
			CPTemplateLayoutEntryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_UUID = new FinderPath(CPTemplateLayoutEntryModelImpl.ENTITY_CACHE_ENABLED,
			CPTemplateLayoutEntryModelImpl.FINDER_CACHE_ENABLED,
			CPTemplateLayoutEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUuid",
			new String[] {
				String.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID = new FinderPath(CPTemplateLayoutEntryModelImpl.ENTITY_CACHE_ENABLED,
			CPTemplateLayoutEntryModelImpl.FINDER_CACHE_ENABLED,
			CPTemplateLayoutEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid",
			new String[] { String.class.getName() },
			CPTemplateLayoutEntryModelImpl.UUID_COLUMN_BITMASK |
			CPTemplateLayoutEntryModelImpl.CLASSNAMEID_COLUMN_BITMASK |
			CPTemplateLayoutEntryModelImpl.CLASSPK_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID = new FinderPath(CPTemplateLayoutEntryModelImpl.ENTITY_CACHE_ENABLED,
			CPTemplateLayoutEntryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUuid",
			new String[] { String.class.getName() });

	/**
	 * Returns all the cp template layout entries where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching cp template layout entries
	 */
	@Override
	public List<CPTemplateLayoutEntry> findByUuid(String uuid) {
		return findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the cp template layout entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPTemplateLayoutEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of cp template layout entries
	 * @param end the upper bound of the range of cp template layout entries (not inclusive)
	 * @return the range of matching cp template layout entries
	 */
	@Override
	public List<CPTemplateLayoutEntry> findByUuid(String uuid, int start,
		int end) {
		return findByUuid(uuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the cp template layout entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPTemplateLayoutEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of cp template layout entries
	 * @param end the upper bound of the range of cp template layout entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cp template layout entries
	 */
	@Override
	public List<CPTemplateLayoutEntry> findByUuid(String uuid, int start,
		int end, OrderByComparator<CPTemplateLayoutEntry> orderByComparator) {
		return findByUuid(uuid, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the cp template layout entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPTemplateLayoutEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of cp template layout entries
	 * @param end the upper bound of the range of cp template layout entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching cp template layout entries
	 */
	@Override
	public List<CPTemplateLayoutEntry> findByUuid(String uuid, int start,
		int end, OrderByComparator<CPTemplateLayoutEntry> orderByComparator,
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

		List<CPTemplateLayoutEntry> list = null;

		if (retrieveFromCache) {
			list = (List<CPTemplateLayoutEntry>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (CPTemplateLayoutEntry cpTemplateLayoutEntry : list) {
					if (!Objects.equals(uuid, cpTemplateLayoutEntry.getUuid())) {
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

			query.append(_SQL_SELECT_CPTEMPLATELAYOUTENTRY_WHERE);

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
				query.append(CPTemplateLayoutEntryModelImpl.ORDER_BY_JPQL);
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
					list = (List<CPTemplateLayoutEntry>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<CPTemplateLayoutEntry>)QueryUtil.list(q,
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
	 * Returns the first cp template layout entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp template layout entry
	 * @throws NoSuchCPTemplateLayoutEntryException if a matching cp template layout entry could not be found
	 */
	@Override
	public CPTemplateLayoutEntry findByUuid_First(String uuid,
		OrderByComparator<CPTemplateLayoutEntry> orderByComparator)
		throws NoSuchCPTemplateLayoutEntryException {
		CPTemplateLayoutEntry cpTemplateLayoutEntry = fetchByUuid_First(uuid,
				orderByComparator);

		if (cpTemplateLayoutEntry != null) {
			return cpTemplateLayoutEntry;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchCPTemplateLayoutEntryException(msg.toString());
	}

	/**
	 * Returns the first cp template layout entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp template layout entry, or <code>null</code> if a matching cp template layout entry could not be found
	 */
	@Override
	public CPTemplateLayoutEntry fetchByUuid_First(String uuid,
		OrderByComparator<CPTemplateLayoutEntry> orderByComparator) {
		List<CPTemplateLayoutEntry> list = findByUuid(uuid, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last cp template layout entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp template layout entry
	 * @throws NoSuchCPTemplateLayoutEntryException if a matching cp template layout entry could not be found
	 */
	@Override
	public CPTemplateLayoutEntry findByUuid_Last(String uuid,
		OrderByComparator<CPTemplateLayoutEntry> orderByComparator)
		throws NoSuchCPTemplateLayoutEntryException {
		CPTemplateLayoutEntry cpTemplateLayoutEntry = fetchByUuid_Last(uuid,
				orderByComparator);

		if (cpTemplateLayoutEntry != null) {
			return cpTemplateLayoutEntry;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchCPTemplateLayoutEntryException(msg.toString());
	}

	/**
	 * Returns the last cp template layout entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp template layout entry, or <code>null</code> if a matching cp template layout entry could not be found
	 */
	@Override
	public CPTemplateLayoutEntry fetchByUuid_Last(String uuid,
		OrderByComparator<CPTemplateLayoutEntry> orderByComparator) {
		int count = countByUuid(uuid);

		if (count == 0) {
			return null;
		}

		List<CPTemplateLayoutEntry> list = findByUuid(uuid, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the cp template layout entries before and after the current cp template layout entry in the ordered set where uuid = &#63;.
	 *
	 * @param CPFriendlyUrlEntryId the primary key of the current cp template layout entry
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next cp template layout entry
	 * @throws NoSuchCPTemplateLayoutEntryException if a cp template layout entry with the primary key could not be found
	 */
	@Override
	public CPTemplateLayoutEntry[] findByUuid_PrevAndNext(
		long CPFriendlyUrlEntryId, String uuid,
		OrderByComparator<CPTemplateLayoutEntry> orderByComparator)
		throws NoSuchCPTemplateLayoutEntryException {
		CPTemplateLayoutEntry cpTemplateLayoutEntry = findByPrimaryKey(CPFriendlyUrlEntryId);

		Session session = null;

		try {
			session = openSession();

			CPTemplateLayoutEntry[] array = new CPTemplateLayoutEntryImpl[3];

			array[0] = getByUuid_PrevAndNext(session, cpTemplateLayoutEntry,
					uuid, orderByComparator, true);

			array[1] = cpTemplateLayoutEntry;

			array[2] = getByUuid_PrevAndNext(session, cpTemplateLayoutEntry,
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

	protected CPTemplateLayoutEntry getByUuid_PrevAndNext(Session session,
		CPTemplateLayoutEntry cpTemplateLayoutEntry, String uuid,
		OrderByComparator<CPTemplateLayoutEntry> orderByComparator,
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

		query.append(_SQL_SELECT_CPTEMPLATELAYOUTENTRY_WHERE);

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
			query.append(CPTemplateLayoutEntryModelImpl.ORDER_BY_JPQL);
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
			Object[] values = orderByComparator.getOrderByConditionValues(cpTemplateLayoutEntry);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<CPTemplateLayoutEntry> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the cp template layout entries where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	@Override
	public void removeByUuid(String uuid) {
		for (CPTemplateLayoutEntry cpTemplateLayoutEntry : findByUuid(uuid,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(cpTemplateLayoutEntry);
		}
	}

	/**
	 * Returns the number of cp template layout entries where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching cp template layout entries
	 */
	@Override
	public int countByUuid(String uuid) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_UUID;

		Object[] finderArgs = new Object[] { uuid };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_CPTEMPLATELAYOUTENTRY_WHERE);

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

	private static final String _FINDER_COLUMN_UUID_UUID_1 = "cpTemplateLayoutEntry.uuid IS NULL";
	private static final String _FINDER_COLUMN_UUID_UUID_2 = "cpTemplateLayoutEntry.uuid = ?";
	private static final String _FINDER_COLUMN_UUID_UUID_3 = "(cpTemplateLayoutEntry.uuid IS NULL OR cpTemplateLayoutEntry.uuid = '')";
	public static final FinderPath FINDER_PATH_FETCH_BY_UUID_G = new FinderPath(CPTemplateLayoutEntryModelImpl.ENTITY_CACHE_ENABLED,
			CPTemplateLayoutEntryModelImpl.FINDER_CACHE_ENABLED,
			CPTemplateLayoutEntryImpl.class, FINDER_CLASS_NAME_ENTITY,
			"fetchByUUID_G",
			new String[] { String.class.getName(), Long.class.getName() },
			CPTemplateLayoutEntryModelImpl.UUID_COLUMN_BITMASK |
			CPTemplateLayoutEntryModelImpl.GROUPID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID_G = new FinderPath(CPTemplateLayoutEntryModelImpl.ENTITY_CACHE_ENABLED,
			CPTemplateLayoutEntryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUUID_G",
			new String[] { String.class.getName(), Long.class.getName() });

	/**
	 * Returns the cp template layout entry where uuid = &#63; and groupId = &#63; or throws a {@link NoSuchCPTemplateLayoutEntryException} if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching cp template layout entry
	 * @throws NoSuchCPTemplateLayoutEntryException if a matching cp template layout entry could not be found
	 */
	@Override
	public CPTemplateLayoutEntry findByUUID_G(String uuid, long groupId)
		throws NoSuchCPTemplateLayoutEntryException {
		CPTemplateLayoutEntry cpTemplateLayoutEntry = fetchByUUID_G(uuid,
				groupId);

		if (cpTemplateLayoutEntry == null) {
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

			throw new NoSuchCPTemplateLayoutEntryException(msg.toString());
		}

		return cpTemplateLayoutEntry;
	}

	/**
	 * Returns the cp template layout entry where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching cp template layout entry, or <code>null</code> if a matching cp template layout entry could not be found
	 */
	@Override
	public CPTemplateLayoutEntry fetchByUUID_G(String uuid, long groupId) {
		return fetchByUUID_G(uuid, groupId, true);
	}

	/**
	 * Returns the cp template layout entry where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the matching cp template layout entry, or <code>null</code> if a matching cp template layout entry could not be found
	 */
	@Override
	public CPTemplateLayoutEntry fetchByUUID_G(String uuid, long groupId,
		boolean retrieveFromCache) {
		Object[] finderArgs = new Object[] { uuid, groupId };

		Object result = null;

		if (retrieveFromCache) {
			result = finderCache.getResult(FINDER_PATH_FETCH_BY_UUID_G,
					finderArgs, this);
		}

		if (result instanceof CPTemplateLayoutEntry) {
			CPTemplateLayoutEntry cpTemplateLayoutEntry = (CPTemplateLayoutEntry)result;

			if (!Objects.equals(uuid, cpTemplateLayoutEntry.getUuid()) ||
					(groupId != cpTemplateLayoutEntry.getGroupId())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_SELECT_CPTEMPLATELAYOUTENTRY_WHERE);

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

				List<CPTemplateLayoutEntry> list = q.list();

				if (list.isEmpty()) {
					finderCache.putResult(FINDER_PATH_FETCH_BY_UUID_G,
						finderArgs, list);
				}
				else {
					CPTemplateLayoutEntry cpTemplateLayoutEntry = list.get(0);

					result = cpTemplateLayoutEntry;

					cacheResult(cpTemplateLayoutEntry);

					if ((cpTemplateLayoutEntry.getUuid() == null) ||
							!cpTemplateLayoutEntry.getUuid().equals(uuid) ||
							(cpTemplateLayoutEntry.getGroupId() != groupId)) {
						finderCache.putResult(FINDER_PATH_FETCH_BY_UUID_G,
							finderArgs, cpTemplateLayoutEntry);
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
			return (CPTemplateLayoutEntry)result;
		}
	}

	/**
	 * Removes the cp template layout entry where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the cp template layout entry that was removed
	 */
	@Override
	public CPTemplateLayoutEntry removeByUUID_G(String uuid, long groupId)
		throws NoSuchCPTemplateLayoutEntryException {
		CPTemplateLayoutEntry cpTemplateLayoutEntry = findByUUID_G(uuid, groupId);

		return remove(cpTemplateLayoutEntry);
	}

	/**
	 * Returns the number of cp template layout entries where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching cp template layout entries
	 */
	@Override
	public int countByUUID_G(String uuid, long groupId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_UUID_G;

		Object[] finderArgs = new Object[] { uuid, groupId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_CPTEMPLATELAYOUTENTRY_WHERE);

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

	private static final String _FINDER_COLUMN_UUID_G_UUID_1 = "cpTemplateLayoutEntry.uuid IS NULL AND ";
	private static final String _FINDER_COLUMN_UUID_G_UUID_2 = "cpTemplateLayoutEntry.uuid = ? AND ";
	private static final String _FINDER_COLUMN_UUID_G_UUID_3 = "(cpTemplateLayoutEntry.uuid IS NULL OR cpTemplateLayoutEntry.uuid = '') AND ";
	private static final String _FINDER_COLUMN_UUID_G_GROUPID_2 = "cpTemplateLayoutEntry.groupId = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_UUID_C = new FinderPath(CPTemplateLayoutEntryModelImpl.ENTITY_CACHE_ENABLED,
			CPTemplateLayoutEntryModelImpl.FINDER_CACHE_ENABLED,
			CPTemplateLayoutEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUuid_C",
			new String[] {
				String.class.getName(), Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C =
		new FinderPath(CPTemplateLayoutEntryModelImpl.ENTITY_CACHE_ENABLED,
			CPTemplateLayoutEntryModelImpl.FINDER_CACHE_ENABLED,
			CPTemplateLayoutEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid_C",
			new String[] { String.class.getName(), Long.class.getName() },
			CPTemplateLayoutEntryModelImpl.UUID_COLUMN_BITMASK |
			CPTemplateLayoutEntryModelImpl.COMPANYID_COLUMN_BITMASK |
			CPTemplateLayoutEntryModelImpl.CLASSNAMEID_COLUMN_BITMASK |
			CPTemplateLayoutEntryModelImpl.CLASSPK_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID_C = new FinderPath(CPTemplateLayoutEntryModelImpl.ENTITY_CACHE_ENABLED,
			CPTemplateLayoutEntryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUuid_C",
			new String[] { String.class.getName(), Long.class.getName() });

	/**
	 * Returns all the cp template layout entries where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching cp template layout entries
	 */
	@Override
	public List<CPTemplateLayoutEntry> findByUuid_C(String uuid, long companyId) {
		return findByUuid_C(uuid, companyId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the cp template layout entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPTemplateLayoutEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of cp template layout entries
	 * @param end the upper bound of the range of cp template layout entries (not inclusive)
	 * @return the range of matching cp template layout entries
	 */
	@Override
	public List<CPTemplateLayoutEntry> findByUuid_C(String uuid,
		long companyId, int start, int end) {
		return findByUuid_C(uuid, companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the cp template layout entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPTemplateLayoutEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of cp template layout entries
	 * @param end the upper bound of the range of cp template layout entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cp template layout entries
	 */
	@Override
	public List<CPTemplateLayoutEntry> findByUuid_C(String uuid,
		long companyId, int start, int end,
		OrderByComparator<CPTemplateLayoutEntry> orderByComparator) {
		return findByUuid_C(uuid, companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the cp template layout entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPTemplateLayoutEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of cp template layout entries
	 * @param end the upper bound of the range of cp template layout entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching cp template layout entries
	 */
	@Override
	public List<CPTemplateLayoutEntry> findByUuid_C(String uuid,
		long companyId, int start, int end,
		OrderByComparator<CPTemplateLayoutEntry> orderByComparator,
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

		List<CPTemplateLayoutEntry> list = null;

		if (retrieveFromCache) {
			list = (List<CPTemplateLayoutEntry>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (CPTemplateLayoutEntry cpTemplateLayoutEntry : list) {
					if (!Objects.equals(uuid, cpTemplateLayoutEntry.getUuid()) ||
							(companyId != cpTemplateLayoutEntry.getCompanyId())) {
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

			query.append(_SQL_SELECT_CPTEMPLATELAYOUTENTRY_WHERE);

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
				query.append(CPTemplateLayoutEntryModelImpl.ORDER_BY_JPQL);
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
					list = (List<CPTemplateLayoutEntry>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<CPTemplateLayoutEntry>)QueryUtil.list(q,
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
	 * Returns the first cp template layout entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp template layout entry
	 * @throws NoSuchCPTemplateLayoutEntryException if a matching cp template layout entry could not be found
	 */
	@Override
	public CPTemplateLayoutEntry findByUuid_C_First(String uuid,
		long companyId,
		OrderByComparator<CPTemplateLayoutEntry> orderByComparator)
		throws NoSuchCPTemplateLayoutEntryException {
		CPTemplateLayoutEntry cpTemplateLayoutEntry = fetchByUuid_C_First(uuid,
				companyId, orderByComparator);

		if (cpTemplateLayoutEntry != null) {
			return cpTemplateLayoutEntry;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(", companyId=");
		msg.append(companyId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchCPTemplateLayoutEntryException(msg.toString());
	}

	/**
	 * Returns the first cp template layout entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp template layout entry, or <code>null</code> if a matching cp template layout entry could not be found
	 */
	@Override
	public CPTemplateLayoutEntry fetchByUuid_C_First(String uuid,
		long companyId,
		OrderByComparator<CPTemplateLayoutEntry> orderByComparator) {
		List<CPTemplateLayoutEntry> list = findByUuid_C(uuid, companyId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last cp template layout entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp template layout entry
	 * @throws NoSuchCPTemplateLayoutEntryException if a matching cp template layout entry could not be found
	 */
	@Override
	public CPTemplateLayoutEntry findByUuid_C_Last(String uuid, long companyId,
		OrderByComparator<CPTemplateLayoutEntry> orderByComparator)
		throws NoSuchCPTemplateLayoutEntryException {
		CPTemplateLayoutEntry cpTemplateLayoutEntry = fetchByUuid_C_Last(uuid,
				companyId, orderByComparator);

		if (cpTemplateLayoutEntry != null) {
			return cpTemplateLayoutEntry;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(", companyId=");
		msg.append(companyId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchCPTemplateLayoutEntryException(msg.toString());
	}

	/**
	 * Returns the last cp template layout entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp template layout entry, or <code>null</code> if a matching cp template layout entry could not be found
	 */
	@Override
	public CPTemplateLayoutEntry fetchByUuid_C_Last(String uuid,
		long companyId,
		OrderByComparator<CPTemplateLayoutEntry> orderByComparator) {
		int count = countByUuid_C(uuid, companyId);

		if (count == 0) {
			return null;
		}

		List<CPTemplateLayoutEntry> list = findByUuid_C(uuid, companyId,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the cp template layout entries before and after the current cp template layout entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param CPFriendlyUrlEntryId the primary key of the current cp template layout entry
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next cp template layout entry
	 * @throws NoSuchCPTemplateLayoutEntryException if a cp template layout entry with the primary key could not be found
	 */
	@Override
	public CPTemplateLayoutEntry[] findByUuid_C_PrevAndNext(
		long CPFriendlyUrlEntryId, String uuid, long companyId,
		OrderByComparator<CPTemplateLayoutEntry> orderByComparator)
		throws NoSuchCPTemplateLayoutEntryException {
		CPTemplateLayoutEntry cpTemplateLayoutEntry = findByPrimaryKey(CPFriendlyUrlEntryId);

		Session session = null;

		try {
			session = openSession();

			CPTemplateLayoutEntry[] array = new CPTemplateLayoutEntryImpl[3];

			array[0] = getByUuid_C_PrevAndNext(session, cpTemplateLayoutEntry,
					uuid, companyId, orderByComparator, true);

			array[1] = cpTemplateLayoutEntry;

			array[2] = getByUuid_C_PrevAndNext(session, cpTemplateLayoutEntry,
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

	protected CPTemplateLayoutEntry getByUuid_C_PrevAndNext(Session session,
		CPTemplateLayoutEntry cpTemplateLayoutEntry, String uuid,
		long companyId,
		OrderByComparator<CPTemplateLayoutEntry> orderByComparator,
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

		query.append(_SQL_SELECT_CPTEMPLATELAYOUTENTRY_WHERE);

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
			query.append(CPTemplateLayoutEntryModelImpl.ORDER_BY_JPQL);
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
			Object[] values = orderByComparator.getOrderByConditionValues(cpTemplateLayoutEntry);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<CPTemplateLayoutEntry> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the cp template layout entries where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	@Override
	public void removeByUuid_C(String uuid, long companyId) {
		for (CPTemplateLayoutEntry cpTemplateLayoutEntry : findByUuid_C(uuid,
				companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(cpTemplateLayoutEntry);
		}
	}

	/**
	 * Returns the number of cp template layout entries where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching cp template layout entries
	 */
	@Override
	public int countByUuid_C(String uuid, long companyId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_UUID_C;

		Object[] finderArgs = new Object[] { uuid, companyId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_CPTEMPLATELAYOUTENTRY_WHERE);

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

	private static final String _FINDER_COLUMN_UUID_C_UUID_1 = "cpTemplateLayoutEntry.uuid IS NULL AND ";
	private static final String _FINDER_COLUMN_UUID_C_UUID_2 = "cpTemplateLayoutEntry.uuid = ? AND ";
	private static final String _FINDER_COLUMN_UUID_C_UUID_3 = "(cpTemplateLayoutEntry.uuid IS NULL OR cpTemplateLayoutEntry.uuid = '') AND ";
	private static final String _FINDER_COLUMN_UUID_C_COMPANYID_2 = "cpTemplateLayoutEntry.companyId = ?";
	public static final FinderPath FINDER_PATH_FETCH_BY_G_C_C = new FinderPath(CPTemplateLayoutEntryModelImpl.ENTITY_CACHE_ENABLED,
			CPTemplateLayoutEntryModelImpl.FINDER_CACHE_ENABLED,
			CPTemplateLayoutEntryImpl.class, FINDER_CLASS_NAME_ENTITY,
			"fetchByG_C_C",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName()
			},
			CPTemplateLayoutEntryModelImpl.GROUPID_COLUMN_BITMASK |
			CPTemplateLayoutEntryModelImpl.CLASSNAMEID_COLUMN_BITMASK |
			CPTemplateLayoutEntryModelImpl.CLASSPK_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_G_C_C = new FinderPath(CPTemplateLayoutEntryModelImpl.ENTITY_CACHE_ENABLED,
			CPTemplateLayoutEntryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_C_C",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName()
			});

	/**
	 * Returns the cp template layout entry where groupId = &#63; and classNameId = &#63; and classPK = &#63; or throws a {@link NoSuchCPTemplateLayoutEntryException} if it could not be found.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the matching cp template layout entry
	 * @throws NoSuchCPTemplateLayoutEntryException if a matching cp template layout entry could not be found
	 */
	@Override
	public CPTemplateLayoutEntry findByG_C_C(long groupId, long classNameId,
		long classPK) throws NoSuchCPTemplateLayoutEntryException {
		CPTemplateLayoutEntry cpTemplateLayoutEntry = fetchByG_C_C(groupId,
				classNameId, classPK);

		if (cpTemplateLayoutEntry == null) {
			StringBundler msg = new StringBundler(8);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("groupId=");
			msg.append(groupId);

			msg.append(", classNameId=");
			msg.append(classNameId);

			msg.append(", classPK=");
			msg.append(classPK);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isDebugEnabled()) {
				_log.debug(msg.toString());
			}

			throw new NoSuchCPTemplateLayoutEntryException(msg.toString());
		}

		return cpTemplateLayoutEntry;
	}

	/**
	 * Returns the cp template layout entry where groupId = &#63; and classNameId = &#63; and classPK = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the matching cp template layout entry, or <code>null</code> if a matching cp template layout entry could not be found
	 */
	@Override
	public CPTemplateLayoutEntry fetchByG_C_C(long groupId, long classNameId,
		long classPK) {
		return fetchByG_C_C(groupId, classNameId, classPK, true);
	}

	/**
	 * Returns the cp template layout entry where groupId = &#63; and classNameId = &#63; and classPK = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the matching cp template layout entry, or <code>null</code> if a matching cp template layout entry could not be found
	 */
	@Override
	public CPTemplateLayoutEntry fetchByG_C_C(long groupId, long classNameId,
		long classPK, boolean retrieveFromCache) {
		Object[] finderArgs = new Object[] { groupId, classNameId, classPK };

		Object result = null;

		if (retrieveFromCache) {
			result = finderCache.getResult(FINDER_PATH_FETCH_BY_G_C_C,
					finderArgs, this);
		}

		if (result instanceof CPTemplateLayoutEntry) {
			CPTemplateLayoutEntry cpTemplateLayoutEntry = (CPTemplateLayoutEntry)result;

			if ((groupId != cpTemplateLayoutEntry.getGroupId()) ||
					(classNameId != cpTemplateLayoutEntry.getClassNameId()) ||
					(classPK != cpTemplateLayoutEntry.getClassPK())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(5);

			query.append(_SQL_SELECT_CPTEMPLATELAYOUTENTRY_WHERE);

			query.append(_FINDER_COLUMN_G_C_C_GROUPID_2);

			query.append(_FINDER_COLUMN_G_C_C_CLASSNAMEID_2);

			query.append(_FINDER_COLUMN_G_C_C_CLASSPK_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(classNameId);

				qPos.add(classPK);

				List<CPTemplateLayoutEntry> list = q.list();

				if (list.isEmpty()) {
					finderCache.putResult(FINDER_PATH_FETCH_BY_G_C_C,
						finderArgs, list);
				}
				else {
					CPTemplateLayoutEntry cpTemplateLayoutEntry = list.get(0);

					result = cpTemplateLayoutEntry;

					cacheResult(cpTemplateLayoutEntry);

					if ((cpTemplateLayoutEntry.getGroupId() != groupId) ||
							(cpTemplateLayoutEntry.getClassNameId() != classNameId) ||
							(cpTemplateLayoutEntry.getClassPK() != classPK)) {
						finderCache.putResult(FINDER_PATH_FETCH_BY_G_C_C,
							finderArgs, cpTemplateLayoutEntry);
					}
				}
			}
			catch (Exception e) {
				finderCache.removeResult(FINDER_PATH_FETCH_BY_G_C_C, finderArgs);

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
			return (CPTemplateLayoutEntry)result;
		}
	}

	/**
	 * Removes the cp template layout entry where groupId = &#63; and classNameId = &#63; and classPK = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the cp template layout entry that was removed
	 */
	@Override
	public CPTemplateLayoutEntry removeByG_C_C(long groupId, long classNameId,
		long classPK) throws NoSuchCPTemplateLayoutEntryException {
		CPTemplateLayoutEntry cpTemplateLayoutEntry = findByG_C_C(groupId,
				classNameId, classPK);

		return remove(cpTemplateLayoutEntry);
	}

	/**
	 * Returns the number of cp template layout entries where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the number of matching cp template layout entries
	 */
	@Override
	public int countByG_C_C(long groupId, long classNameId, long classPK) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_G_C_C;

		Object[] finderArgs = new Object[] { groupId, classNameId, classPK };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_COUNT_CPTEMPLATELAYOUTENTRY_WHERE);

			query.append(_FINDER_COLUMN_G_C_C_GROUPID_2);

			query.append(_FINDER_COLUMN_G_C_C_CLASSNAMEID_2);

			query.append(_FINDER_COLUMN_G_C_C_CLASSPK_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

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

	private static final String _FINDER_COLUMN_G_C_C_GROUPID_2 = "cpTemplateLayoutEntry.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_C_C_CLASSNAMEID_2 = "cpTemplateLayoutEntry.classNameId = ? AND ";
	private static final String _FINDER_COLUMN_G_C_C_CLASSPK_2 = "cpTemplateLayoutEntry.classPK = ?";

	public CPTemplateLayoutEntryPersistenceImpl() {
		setModelClass(CPTemplateLayoutEntry.class);

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
	 * Caches the cp template layout entry in the entity cache if it is enabled.
	 *
	 * @param cpTemplateLayoutEntry the cp template layout entry
	 */
	@Override
	public void cacheResult(CPTemplateLayoutEntry cpTemplateLayoutEntry) {
		entityCache.putResult(CPTemplateLayoutEntryModelImpl.ENTITY_CACHE_ENABLED,
			CPTemplateLayoutEntryImpl.class,
			cpTemplateLayoutEntry.getPrimaryKey(), cpTemplateLayoutEntry);

		finderCache.putResult(FINDER_PATH_FETCH_BY_UUID_G,
			new Object[] {
				cpTemplateLayoutEntry.getUuid(),
				cpTemplateLayoutEntry.getGroupId()
			}, cpTemplateLayoutEntry);

		finderCache.putResult(FINDER_PATH_FETCH_BY_G_C_C,
			new Object[] {
				cpTemplateLayoutEntry.getGroupId(),
				cpTemplateLayoutEntry.getClassNameId(),
				cpTemplateLayoutEntry.getClassPK()
			}, cpTemplateLayoutEntry);

		cpTemplateLayoutEntry.resetOriginalValues();
	}

	/**
	 * Caches the cp template layout entries in the entity cache if it is enabled.
	 *
	 * @param cpTemplateLayoutEntries the cp template layout entries
	 */
	@Override
	public void cacheResult(List<CPTemplateLayoutEntry> cpTemplateLayoutEntries) {
		for (CPTemplateLayoutEntry cpTemplateLayoutEntry : cpTemplateLayoutEntries) {
			if (entityCache.getResult(
						CPTemplateLayoutEntryModelImpl.ENTITY_CACHE_ENABLED,
						CPTemplateLayoutEntryImpl.class,
						cpTemplateLayoutEntry.getPrimaryKey()) == null) {
				cacheResult(cpTemplateLayoutEntry);
			}
			else {
				cpTemplateLayoutEntry.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all cp template layout entries.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(CPTemplateLayoutEntryImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the cp template layout entry.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(CPTemplateLayoutEntry cpTemplateLayoutEntry) {
		entityCache.removeResult(CPTemplateLayoutEntryModelImpl.ENTITY_CACHE_ENABLED,
			CPTemplateLayoutEntryImpl.class,
			cpTemplateLayoutEntry.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache((CPTemplateLayoutEntryModelImpl)cpTemplateLayoutEntry,
			true);
	}

	@Override
	public void clearCache(List<CPTemplateLayoutEntry> cpTemplateLayoutEntries) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (CPTemplateLayoutEntry cpTemplateLayoutEntry : cpTemplateLayoutEntries) {
			entityCache.removeResult(CPTemplateLayoutEntryModelImpl.ENTITY_CACHE_ENABLED,
				CPTemplateLayoutEntryImpl.class,
				cpTemplateLayoutEntry.getPrimaryKey());

			clearUniqueFindersCache((CPTemplateLayoutEntryModelImpl)cpTemplateLayoutEntry,
				true);
		}
	}

	protected void cacheUniqueFindersCache(
		CPTemplateLayoutEntryModelImpl cpTemplateLayoutEntryModelImpl) {
		Object[] args = new Object[] {
				cpTemplateLayoutEntryModelImpl.getUuid(),
				cpTemplateLayoutEntryModelImpl.getGroupId()
			};

		finderCache.putResult(FINDER_PATH_COUNT_BY_UUID_G, args,
			Long.valueOf(1), false);
		finderCache.putResult(FINDER_PATH_FETCH_BY_UUID_G, args,
			cpTemplateLayoutEntryModelImpl, false);

		args = new Object[] {
				cpTemplateLayoutEntryModelImpl.getGroupId(),
				cpTemplateLayoutEntryModelImpl.getClassNameId(),
				cpTemplateLayoutEntryModelImpl.getClassPK()
			};

		finderCache.putResult(FINDER_PATH_COUNT_BY_G_C_C, args,
			Long.valueOf(1), false);
		finderCache.putResult(FINDER_PATH_FETCH_BY_G_C_C, args,
			cpTemplateLayoutEntryModelImpl, false);
	}

	protected void clearUniqueFindersCache(
		CPTemplateLayoutEntryModelImpl cpTemplateLayoutEntryModelImpl,
		boolean clearCurrent) {
		if (clearCurrent) {
			Object[] args = new Object[] {
					cpTemplateLayoutEntryModelImpl.getUuid(),
					cpTemplateLayoutEntryModelImpl.getGroupId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID_G, args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_UUID_G, args);
		}

		if ((cpTemplateLayoutEntryModelImpl.getColumnBitmask() &
				FINDER_PATH_FETCH_BY_UUID_G.getColumnBitmask()) != 0) {
			Object[] args = new Object[] {
					cpTemplateLayoutEntryModelImpl.getOriginalUuid(),
					cpTemplateLayoutEntryModelImpl.getOriginalGroupId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID_G, args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_UUID_G, args);
		}

		if (clearCurrent) {
			Object[] args = new Object[] {
					cpTemplateLayoutEntryModelImpl.getGroupId(),
					cpTemplateLayoutEntryModelImpl.getClassNameId(),
					cpTemplateLayoutEntryModelImpl.getClassPK()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_G_C_C, args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_G_C_C, args);
		}

		if ((cpTemplateLayoutEntryModelImpl.getColumnBitmask() &
				FINDER_PATH_FETCH_BY_G_C_C.getColumnBitmask()) != 0) {
			Object[] args = new Object[] {
					cpTemplateLayoutEntryModelImpl.getOriginalGroupId(),
					cpTemplateLayoutEntryModelImpl.getOriginalClassNameId(),
					cpTemplateLayoutEntryModelImpl.getOriginalClassPK()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_G_C_C, args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_G_C_C, args);
		}
	}

	/**
	 * Creates a new cp template layout entry with the primary key. Does not add the cp template layout entry to the database.
	 *
	 * @param CPFriendlyUrlEntryId the primary key for the new cp template layout entry
	 * @return the new cp template layout entry
	 */
	@Override
	public CPTemplateLayoutEntry create(long CPFriendlyUrlEntryId) {
		CPTemplateLayoutEntry cpTemplateLayoutEntry = new CPTemplateLayoutEntryImpl();

		cpTemplateLayoutEntry.setNew(true);
		cpTemplateLayoutEntry.setPrimaryKey(CPFriendlyUrlEntryId);

		String uuid = PortalUUIDUtil.generate();

		cpTemplateLayoutEntry.setUuid(uuid);

		cpTemplateLayoutEntry.setCompanyId(companyProvider.getCompanyId());

		return cpTemplateLayoutEntry;
	}

	/**
	 * Removes the cp template layout entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param CPFriendlyUrlEntryId the primary key of the cp template layout entry
	 * @return the cp template layout entry that was removed
	 * @throws NoSuchCPTemplateLayoutEntryException if a cp template layout entry with the primary key could not be found
	 */
	@Override
	public CPTemplateLayoutEntry remove(long CPFriendlyUrlEntryId)
		throws NoSuchCPTemplateLayoutEntryException {
		return remove((Serializable)CPFriendlyUrlEntryId);
	}

	/**
	 * Removes the cp template layout entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the cp template layout entry
	 * @return the cp template layout entry that was removed
	 * @throws NoSuchCPTemplateLayoutEntryException if a cp template layout entry with the primary key could not be found
	 */
	@Override
	public CPTemplateLayoutEntry remove(Serializable primaryKey)
		throws NoSuchCPTemplateLayoutEntryException {
		Session session = null;

		try {
			session = openSession();

			CPTemplateLayoutEntry cpTemplateLayoutEntry = (CPTemplateLayoutEntry)session.get(CPTemplateLayoutEntryImpl.class,
					primaryKey);

			if (cpTemplateLayoutEntry == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchCPTemplateLayoutEntryException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(cpTemplateLayoutEntry);
		}
		catch (NoSuchCPTemplateLayoutEntryException nsee) {
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
	protected CPTemplateLayoutEntry removeImpl(
		CPTemplateLayoutEntry cpTemplateLayoutEntry) {
		cpTemplateLayoutEntry = toUnwrappedModel(cpTemplateLayoutEntry);

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(cpTemplateLayoutEntry)) {
				cpTemplateLayoutEntry = (CPTemplateLayoutEntry)session.get(CPTemplateLayoutEntryImpl.class,
						cpTemplateLayoutEntry.getPrimaryKeyObj());
			}

			if (cpTemplateLayoutEntry != null) {
				session.delete(cpTemplateLayoutEntry);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (cpTemplateLayoutEntry != null) {
			clearCache(cpTemplateLayoutEntry);
		}

		return cpTemplateLayoutEntry;
	}

	@Override
	public CPTemplateLayoutEntry updateImpl(
		CPTemplateLayoutEntry cpTemplateLayoutEntry) {
		cpTemplateLayoutEntry = toUnwrappedModel(cpTemplateLayoutEntry);

		boolean isNew = cpTemplateLayoutEntry.isNew();

		CPTemplateLayoutEntryModelImpl cpTemplateLayoutEntryModelImpl = (CPTemplateLayoutEntryModelImpl)cpTemplateLayoutEntry;

		if (Validator.isNull(cpTemplateLayoutEntry.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			cpTemplateLayoutEntry.setUuid(uuid);
		}

		ServiceContext serviceContext = ServiceContextThreadLocal.getServiceContext();

		Date now = new Date();

		if (isNew && (cpTemplateLayoutEntry.getCreateDate() == null)) {
			if (serviceContext == null) {
				cpTemplateLayoutEntry.setCreateDate(now);
			}
			else {
				cpTemplateLayoutEntry.setCreateDate(serviceContext.getCreateDate(
						now));
			}
		}

		if (!cpTemplateLayoutEntryModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				cpTemplateLayoutEntry.setModifiedDate(now);
			}
			else {
				cpTemplateLayoutEntry.setModifiedDate(serviceContext.getModifiedDate(
						now));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (cpTemplateLayoutEntry.isNew()) {
				session.save(cpTemplateLayoutEntry);

				cpTemplateLayoutEntry.setNew(false);
			}
			else {
				cpTemplateLayoutEntry = (CPTemplateLayoutEntry)session.merge(cpTemplateLayoutEntry);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (!CPTemplateLayoutEntryModelImpl.COLUMN_BITMASK_ENABLED) {
			finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}
		else
		 if (isNew) {
			Object[] args = new Object[] {
					cpTemplateLayoutEntryModelImpl.getUuid()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID, args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID,
				args);

			args = new Object[] {
					cpTemplateLayoutEntryModelImpl.getUuid(),
					cpTemplateLayoutEntryModelImpl.getCompanyId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID_C, args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C,
				args);

			finderCache.removeResult(FINDER_PATH_COUNT_ALL, FINDER_ARGS_EMPTY);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL,
				FINDER_ARGS_EMPTY);
		}

		else {
			if ((cpTemplateLayoutEntryModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						cpTemplateLayoutEntryModelImpl.getOriginalUuid()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID,
					args);

				args = new Object[] { cpTemplateLayoutEntryModelImpl.getUuid() };

				finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID,
					args);
			}

			if ((cpTemplateLayoutEntryModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						cpTemplateLayoutEntryModelImpl.getOriginalUuid(),
						cpTemplateLayoutEntryModelImpl.getOriginalCompanyId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID_C, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C,
					args);

				args = new Object[] {
						cpTemplateLayoutEntryModelImpl.getUuid(),
						cpTemplateLayoutEntryModelImpl.getCompanyId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID_C, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C,
					args);
			}
		}

		entityCache.putResult(CPTemplateLayoutEntryModelImpl.ENTITY_CACHE_ENABLED,
			CPTemplateLayoutEntryImpl.class,
			cpTemplateLayoutEntry.getPrimaryKey(), cpTemplateLayoutEntry, false);

		clearUniqueFindersCache(cpTemplateLayoutEntryModelImpl, false);
		cacheUniqueFindersCache(cpTemplateLayoutEntryModelImpl);

		cpTemplateLayoutEntry.resetOriginalValues();

		return cpTemplateLayoutEntry;
	}

	protected CPTemplateLayoutEntry toUnwrappedModel(
		CPTemplateLayoutEntry cpTemplateLayoutEntry) {
		if (cpTemplateLayoutEntry instanceof CPTemplateLayoutEntryImpl) {
			return cpTemplateLayoutEntry;
		}

		CPTemplateLayoutEntryImpl cpTemplateLayoutEntryImpl = new CPTemplateLayoutEntryImpl();

		cpTemplateLayoutEntryImpl.setNew(cpTemplateLayoutEntry.isNew());
		cpTemplateLayoutEntryImpl.setPrimaryKey(cpTemplateLayoutEntry.getPrimaryKey());

		cpTemplateLayoutEntryImpl.setUuid(cpTemplateLayoutEntry.getUuid());
		cpTemplateLayoutEntryImpl.setCPFriendlyUrlEntryId(cpTemplateLayoutEntry.getCPFriendlyUrlEntryId());
		cpTemplateLayoutEntryImpl.setGroupId(cpTemplateLayoutEntry.getGroupId());
		cpTemplateLayoutEntryImpl.setCompanyId(cpTemplateLayoutEntry.getCompanyId());
		cpTemplateLayoutEntryImpl.setUserId(cpTemplateLayoutEntry.getUserId());
		cpTemplateLayoutEntryImpl.setUserName(cpTemplateLayoutEntry.getUserName());
		cpTemplateLayoutEntryImpl.setCreateDate(cpTemplateLayoutEntry.getCreateDate());
		cpTemplateLayoutEntryImpl.setModifiedDate(cpTemplateLayoutEntry.getModifiedDate());
		cpTemplateLayoutEntryImpl.setClassNameId(cpTemplateLayoutEntry.getClassNameId());
		cpTemplateLayoutEntryImpl.setClassPK(cpTemplateLayoutEntry.getClassPK());
		cpTemplateLayoutEntryImpl.setLayoutUuid(cpTemplateLayoutEntry.getLayoutUuid());

		return cpTemplateLayoutEntryImpl;
	}

	/**
	 * Returns the cp template layout entry with the primary key or throws a {@link com.liferay.portal.kernel.exception.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the cp template layout entry
	 * @return the cp template layout entry
	 * @throws NoSuchCPTemplateLayoutEntryException if a cp template layout entry with the primary key could not be found
	 */
	@Override
	public CPTemplateLayoutEntry findByPrimaryKey(Serializable primaryKey)
		throws NoSuchCPTemplateLayoutEntryException {
		CPTemplateLayoutEntry cpTemplateLayoutEntry = fetchByPrimaryKey(primaryKey);

		if (cpTemplateLayoutEntry == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchCPTemplateLayoutEntryException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				primaryKey);
		}

		return cpTemplateLayoutEntry;
	}

	/**
	 * Returns the cp template layout entry with the primary key or throws a {@link NoSuchCPTemplateLayoutEntryException} if it could not be found.
	 *
	 * @param CPFriendlyUrlEntryId the primary key of the cp template layout entry
	 * @return the cp template layout entry
	 * @throws NoSuchCPTemplateLayoutEntryException if a cp template layout entry with the primary key could not be found
	 */
	@Override
	public CPTemplateLayoutEntry findByPrimaryKey(long CPFriendlyUrlEntryId)
		throws NoSuchCPTemplateLayoutEntryException {
		return findByPrimaryKey((Serializable)CPFriendlyUrlEntryId);
	}

	/**
	 * Returns the cp template layout entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the cp template layout entry
	 * @return the cp template layout entry, or <code>null</code> if a cp template layout entry with the primary key could not be found
	 */
	@Override
	public CPTemplateLayoutEntry fetchByPrimaryKey(Serializable primaryKey) {
		Serializable serializable = entityCache.getResult(CPTemplateLayoutEntryModelImpl.ENTITY_CACHE_ENABLED,
				CPTemplateLayoutEntryImpl.class, primaryKey);

		if (serializable == nullModel) {
			return null;
		}

		CPTemplateLayoutEntry cpTemplateLayoutEntry = (CPTemplateLayoutEntry)serializable;

		if (cpTemplateLayoutEntry == null) {
			Session session = null;

			try {
				session = openSession();

				cpTemplateLayoutEntry = (CPTemplateLayoutEntry)session.get(CPTemplateLayoutEntryImpl.class,
						primaryKey);

				if (cpTemplateLayoutEntry != null) {
					cacheResult(cpTemplateLayoutEntry);
				}
				else {
					entityCache.putResult(CPTemplateLayoutEntryModelImpl.ENTITY_CACHE_ENABLED,
						CPTemplateLayoutEntryImpl.class, primaryKey, nullModel);
				}
			}
			catch (Exception e) {
				entityCache.removeResult(CPTemplateLayoutEntryModelImpl.ENTITY_CACHE_ENABLED,
					CPTemplateLayoutEntryImpl.class, primaryKey);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return cpTemplateLayoutEntry;
	}

	/**
	 * Returns the cp template layout entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param CPFriendlyUrlEntryId the primary key of the cp template layout entry
	 * @return the cp template layout entry, or <code>null</code> if a cp template layout entry with the primary key could not be found
	 */
	@Override
	public CPTemplateLayoutEntry fetchByPrimaryKey(long CPFriendlyUrlEntryId) {
		return fetchByPrimaryKey((Serializable)CPFriendlyUrlEntryId);
	}

	@Override
	public Map<Serializable, CPTemplateLayoutEntry> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {
		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, CPTemplateLayoutEntry> map = new HashMap<Serializable, CPTemplateLayoutEntry>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			CPTemplateLayoutEntry cpTemplateLayoutEntry = fetchByPrimaryKey(primaryKey);

			if (cpTemplateLayoutEntry != null) {
				map.put(primaryKey, cpTemplateLayoutEntry);
			}

			return map;
		}

		Set<Serializable> uncachedPrimaryKeys = null;

		for (Serializable primaryKey : primaryKeys) {
			Serializable serializable = entityCache.getResult(CPTemplateLayoutEntryModelImpl.ENTITY_CACHE_ENABLED,
					CPTemplateLayoutEntryImpl.class, primaryKey);

			if (serializable != nullModel) {
				if (serializable == null) {
					if (uncachedPrimaryKeys == null) {
						uncachedPrimaryKeys = new HashSet<Serializable>();
					}

					uncachedPrimaryKeys.add(primaryKey);
				}
				else {
					map.put(primaryKey, (CPTemplateLayoutEntry)serializable);
				}
			}
		}

		if (uncachedPrimaryKeys == null) {
			return map;
		}

		StringBundler query = new StringBundler((uncachedPrimaryKeys.size() * 2) +
				1);

		query.append(_SQL_SELECT_CPTEMPLATELAYOUTENTRY_WHERE_PKS_IN);

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

			for (CPTemplateLayoutEntry cpTemplateLayoutEntry : (List<CPTemplateLayoutEntry>)q.list()) {
				map.put(cpTemplateLayoutEntry.getPrimaryKeyObj(),
					cpTemplateLayoutEntry);

				cacheResult(cpTemplateLayoutEntry);

				uncachedPrimaryKeys.remove(cpTemplateLayoutEntry.getPrimaryKeyObj());
			}

			for (Serializable primaryKey : uncachedPrimaryKeys) {
				entityCache.putResult(CPTemplateLayoutEntryModelImpl.ENTITY_CACHE_ENABLED,
					CPTemplateLayoutEntryImpl.class, primaryKey, nullModel);
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
	 * Returns all the cp template layout entries.
	 *
	 * @return the cp template layout entries
	 */
	@Override
	public List<CPTemplateLayoutEntry> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the cp template layout entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPTemplateLayoutEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of cp template layout entries
	 * @param end the upper bound of the range of cp template layout entries (not inclusive)
	 * @return the range of cp template layout entries
	 */
	@Override
	public List<CPTemplateLayoutEntry> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the cp template layout entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPTemplateLayoutEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of cp template layout entries
	 * @param end the upper bound of the range of cp template layout entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of cp template layout entries
	 */
	@Override
	public List<CPTemplateLayoutEntry> findAll(int start, int end,
		OrderByComparator<CPTemplateLayoutEntry> orderByComparator) {
		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the cp template layout entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CPTemplateLayoutEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of cp template layout entries
	 * @param end the upper bound of the range of cp template layout entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of cp template layout entries
	 */
	@Override
	public List<CPTemplateLayoutEntry> findAll(int start, int end,
		OrderByComparator<CPTemplateLayoutEntry> orderByComparator,
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

		List<CPTemplateLayoutEntry> list = null;

		if (retrieveFromCache) {
			list = (List<CPTemplateLayoutEntry>)finderCache.getResult(finderPath,
					finderArgs, this);
		}

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 2));

				query.append(_SQL_SELECT_CPTEMPLATELAYOUTENTRY);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_CPTEMPLATELAYOUTENTRY;

				if (pagination) {
					sql = sql.concat(CPTemplateLayoutEntryModelImpl.ORDER_BY_JPQL);
				}
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (!pagination) {
					list = (List<CPTemplateLayoutEntry>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<CPTemplateLayoutEntry>)QueryUtil.list(q,
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
	 * Removes all the cp template layout entries from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (CPTemplateLayoutEntry cpTemplateLayoutEntry : findAll()) {
			remove(cpTemplateLayoutEntry);
		}
	}

	/**
	 * Returns the number of cp template layout entries.
	 *
	 * @return the number of cp template layout entries
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_CPTEMPLATELAYOUTENTRY);

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
		return CPTemplateLayoutEntryModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the cp template layout entry persistence.
	 */
	public void afterPropertiesSet() {
	}

	public void destroy() {
		entityCache.removeCache(CPTemplateLayoutEntryImpl.class.getName());
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
	private static final String _SQL_SELECT_CPTEMPLATELAYOUTENTRY = "SELECT cpTemplateLayoutEntry FROM CPTemplateLayoutEntry cpTemplateLayoutEntry";
	private static final String _SQL_SELECT_CPTEMPLATELAYOUTENTRY_WHERE_PKS_IN = "SELECT cpTemplateLayoutEntry FROM CPTemplateLayoutEntry cpTemplateLayoutEntry WHERE CPFriendlyUrlEntryId IN (";
	private static final String _SQL_SELECT_CPTEMPLATELAYOUTENTRY_WHERE = "SELECT cpTemplateLayoutEntry FROM CPTemplateLayoutEntry cpTemplateLayoutEntry WHERE ";
	private static final String _SQL_COUNT_CPTEMPLATELAYOUTENTRY = "SELECT COUNT(cpTemplateLayoutEntry) FROM CPTemplateLayoutEntry cpTemplateLayoutEntry";
	private static final String _SQL_COUNT_CPTEMPLATELAYOUTENTRY_WHERE = "SELECT COUNT(cpTemplateLayoutEntry) FROM CPTemplateLayoutEntry cpTemplateLayoutEntry WHERE ";
	private static final String _ORDER_BY_ENTITY_ALIAS = "cpTemplateLayoutEntry.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No CPTemplateLayoutEntry exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No CPTemplateLayoutEntry exists with the key {";
	private static final Log _log = LogFactoryUtil.getLog(CPTemplateLayoutEntryPersistenceImpl.class);
	private static final Set<String> _badColumnNames = SetUtil.fromArray(new String[] {
				"uuid"
			});
}