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

package com.liferay.adaptive.media.image.service.persistence.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.adaptive.media.image.exception.NoSuchAMImageEntryException;
import com.liferay.adaptive.media.image.model.AMImageEntry;
import com.liferay.adaptive.media.image.model.impl.AMImageEntryImpl;
import com.liferay.adaptive.media.image.model.impl.AMImageEntryModelImpl;
import com.liferay.adaptive.media.image.service.persistence.AMImageEntryPersistence;

import com.liferay.portal.kernel.dao.orm.EntityCache;
import com.liferay.portal.kernel.dao.orm.FinderCache;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
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
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * The persistence implementation for the am image entry service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see AMImageEntryPersistence
 * @see com.liferay.adaptive.media.image.service.persistence.AMImageEntryUtil
 * @generated
 */
@ProviderType
public class AMImageEntryPersistenceImpl extends BasePersistenceImpl<AMImageEntry>
	implements AMImageEntryPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link AMImageEntryUtil} to access the am image entry persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = AMImageEntryImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(AMImageEntryModelImpl.ENTITY_CACHE_ENABLED,
			AMImageEntryModelImpl.FINDER_CACHE_ENABLED, AMImageEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(AMImageEntryModelImpl.ENTITY_CACHE_ENABLED,
			AMImageEntryModelImpl.FINDER_CACHE_ENABLED, AMImageEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(AMImageEntryModelImpl.ENTITY_CACHE_ENABLED,
			AMImageEntryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_UUID = new FinderPath(AMImageEntryModelImpl.ENTITY_CACHE_ENABLED,
			AMImageEntryModelImpl.FINDER_CACHE_ENABLED, AMImageEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUuid",
			new String[] {
				String.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID = new FinderPath(AMImageEntryModelImpl.ENTITY_CACHE_ENABLED,
			AMImageEntryModelImpl.FINDER_CACHE_ENABLED, AMImageEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid",
			new String[] { String.class.getName() },
			AMImageEntryModelImpl.UUID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID = new FinderPath(AMImageEntryModelImpl.ENTITY_CACHE_ENABLED,
			AMImageEntryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUuid",
			new String[] { String.class.getName() });

	/**
	 * Returns all the am image entries where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching am image entries
	 */
	@Override
	public List<AMImageEntry> findByUuid(String uuid) {
		return findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the am image entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AMImageEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of am image entries
	 * @param end the upper bound of the range of am image entries (not inclusive)
	 * @return the range of matching am image entries
	 */
	@Override
	public List<AMImageEntry> findByUuid(String uuid, int start, int end) {
		return findByUuid(uuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the am image entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AMImageEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of am image entries
	 * @param end the upper bound of the range of am image entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching am image entries
	 */
	@Override
	public List<AMImageEntry> findByUuid(String uuid, int start, int end,
		OrderByComparator<AMImageEntry> orderByComparator) {
		return findByUuid(uuid, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the am image entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AMImageEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of am image entries
	 * @param end the upper bound of the range of am image entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching am image entries
	 */
	@Override
	public List<AMImageEntry> findByUuid(String uuid, int start, int end,
		OrderByComparator<AMImageEntry> orderByComparator,
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

		List<AMImageEntry> list = null;

		if (retrieveFromCache) {
			list = (List<AMImageEntry>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (AMImageEntry amImageEntry : list) {
					if (!Objects.equals(uuid, amImageEntry.getUuid())) {
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

			query.append(_SQL_SELECT_AMIMAGEENTRY_WHERE);

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
				query.append(AMImageEntryModelImpl.ORDER_BY_JPQL);
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
					list = (List<AMImageEntry>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<AMImageEntry>)QueryUtil.list(q, getDialect(),
							start, end);
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
	 * Returns the first am image entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching am image entry
	 * @throws NoSuchAMImageEntryException if a matching am image entry could not be found
	 */
	@Override
	public AMImageEntry findByUuid_First(String uuid,
		OrderByComparator<AMImageEntry> orderByComparator)
		throws NoSuchAMImageEntryException {
		AMImageEntry amImageEntry = fetchByUuid_First(uuid, orderByComparator);

		if (amImageEntry != null) {
			return amImageEntry;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append("}");

		throw new NoSuchAMImageEntryException(msg.toString());
	}

	/**
	 * Returns the first am image entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching am image entry, or <code>null</code> if a matching am image entry could not be found
	 */
	@Override
	public AMImageEntry fetchByUuid_First(String uuid,
		OrderByComparator<AMImageEntry> orderByComparator) {
		List<AMImageEntry> list = findByUuid(uuid, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last am image entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching am image entry
	 * @throws NoSuchAMImageEntryException if a matching am image entry could not be found
	 */
	@Override
	public AMImageEntry findByUuid_Last(String uuid,
		OrderByComparator<AMImageEntry> orderByComparator)
		throws NoSuchAMImageEntryException {
		AMImageEntry amImageEntry = fetchByUuid_Last(uuid, orderByComparator);

		if (amImageEntry != null) {
			return amImageEntry;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append("}");

		throw new NoSuchAMImageEntryException(msg.toString());
	}

	/**
	 * Returns the last am image entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching am image entry, or <code>null</code> if a matching am image entry could not be found
	 */
	@Override
	public AMImageEntry fetchByUuid_Last(String uuid,
		OrderByComparator<AMImageEntry> orderByComparator) {
		int count = countByUuid(uuid);

		if (count == 0) {
			return null;
		}

		List<AMImageEntry> list = findByUuid(uuid, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the am image entries before and after the current am image entry in the ordered set where uuid = &#63;.
	 *
	 * @param amImageEntryId the primary key of the current am image entry
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next am image entry
	 * @throws NoSuchAMImageEntryException if a am image entry with the primary key could not be found
	 */
	@Override
	public AMImageEntry[] findByUuid_PrevAndNext(long amImageEntryId,
		String uuid, OrderByComparator<AMImageEntry> orderByComparator)
		throws NoSuchAMImageEntryException {
		AMImageEntry amImageEntry = findByPrimaryKey(amImageEntryId);

		Session session = null;

		try {
			session = openSession();

			AMImageEntry[] array = new AMImageEntryImpl[3];

			array[0] = getByUuid_PrevAndNext(session, amImageEntry, uuid,
					orderByComparator, true);

			array[1] = amImageEntry;

			array[2] = getByUuid_PrevAndNext(session, amImageEntry, uuid,
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

	protected AMImageEntry getByUuid_PrevAndNext(Session session,
		AMImageEntry amImageEntry, String uuid,
		OrderByComparator<AMImageEntry> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(4 +
					(orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_AMIMAGEENTRY_WHERE);

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
			query.append(AMImageEntryModelImpl.ORDER_BY_JPQL);
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
			Object[] values = orderByComparator.getOrderByConditionValues(amImageEntry);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<AMImageEntry> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the am image entries where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	@Override
	public void removeByUuid(String uuid) {
		for (AMImageEntry amImageEntry : findByUuid(uuid, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS, null)) {
			remove(amImageEntry);
		}
	}

	/**
	 * Returns the number of am image entries where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching am image entries
	 */
	@Override
	public int countByUuid(String uuid) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_UUID;

		Object[] finderArgs = new Object[] { uuid };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_AMIMAGEENTRY_WHERE);

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

	private static final String _FINDER_COLUMN_UUID_UUID_1 = "amImageEntry.uuid IS NULL";
	private static final String _FINDER_COLUMN_UUID_UUID_2 = "amImageEntry.uuid = ?";
	private static final String _FINDER_COLUMN_UUID_UUID_3 = "(amImageEntry.uuid IS NULL OR amImageEntry.uuid = '')";
	public static final FinderPath FINDER_PATH_FETCH_BY_UUID_G = new FinderPath(AMImageEntryModelImpl.ENTITY_CACHE_ENABLED,
			AMImageEntryModelImpl.FINDER_CACHE_ENABLED, AMImageEntryImpl.class,
			FINDER_CLASS_NAME_ENTITY, "fetchByUUID_G",
			new String[] { String.class.getName(), Long.class.getName() },
			AMImageEntryModelImpl.UUID_COLUMN_BITMASK |
			AMImageEntryModelImpl.GROUPID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID_G = new FinderPath(AMImageEntryModelImpl.ENTITY_CACHE_ENABLED,
			AMImageEntryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUUID_G",
			new String[] { String.class.getName(), Long.class.getName() });

	/**
	 * Returns the am image entry where uuid = &#63; and groupId = &#63; or throws a {@link NoSuchAMImageEntryException} if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching am image entry
	 * @throws NoSuchAMImageEntryException if a matching am image entry could not be found
	 */
	@Override
	public AMImageEntry findByUUID_G(String uuid, long groupId)
		throws NoSuchAMImageEntryException {
		AMImageEntry amImageEntry = fetchByUUID_G(uuid, groupId);

		if (amImageEntry == null) {
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

			throw new NoSuchAMImageEntryException(msg.toString());
		}

		return amImageEntry;
	}

	/**
	 * Returns the am image entry where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching am image entry, or <code>null</code> if a matching am image entry could not be found
	 */
	@Override
	public AMImageEntry fetchByUUID_G(String uuid, long groupId) {
		return fetchByUUID_G(uuid, groupId, true);
	}

	/**
	 * Returns the am image entry where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the matching am image entry, or <code>null</code> if a matching am image entry could not be found
	 */
	@Override
	public AMImageEntry fetchByUUID_G(String uuid, long groupId,
		boolean retrieveFromCache) {
		Object[] finderArgs = new Object[] { uuid, groupId };

		Object result = null;

		if (retrieveFromCache) {
			result = finderCache.getResult(FINDER_PATH_FETCH_BY_UUID_G,
					finderArgs, this);
		}

		if (result instanceof AMImageEntry) {
			AMImageEntry amImageEntry = (AMImageEntry)result;

			if (!Objects.equals(uuid, amImageEntry.getUuid()) ||
					(groupId != amImageEntry.getGroupId())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_SELECT_AMIMAGEENTRY_WHERE);

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

				List<AMImageEntry> list = q.list();

				if (list.isEmpty()) {
					finderCache.putResult(FINDER_PATH_FETCH_BY_UUID_G,
						finderArgs, list);
				}
				else {
					AMImageEntry amImageEntry = list.get(0);

					result = amImageEntry;

					cacheResult(amImageEntry);

					if ((amImageEntry.getUuid() == null) ||
							!amImageEntry.getUuid().equals(uuid) ||
							(amImageEntry.getGroupId() != groupId)) {
						finderCache.putResult(FINDER_PATH_FETCH_BY_UUID_G,
							finderArgs, amImageEntry);
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
			return (AMImageEntry)result;
		}
	}

	/**
	 * Removes the am image entry where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the am image entry that was removed
	 */
	@Override
	public AMImageEntry removeByUUID_G(String uuid, long groupId)
		throws NoSuchAMImageEntryException {
		AMImageEntry amImageEntry = findByUUID_G(uuid, groupId);

		return remove(amImageEntry);
	}

	/**
	 * Returns the number of am image entries where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching am image entries
	 */
	@Override
	public int countByUUID_G(String uuid, long groupId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_UUID_G;

		Object[] finderArgs = new Object[] { uuid, groupId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_AMIMAGEENTRY_WHERE);

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

	private static final String _FINDER_COLUMN_UUID_G_UUID_1 = "amImageEntry.uuid IS NULL AND ";
	private static final String _FINDER_COLUMN_UUID_G_UUID_2 = "amImageEntry.uuid = ? AND ";
	private static final String _FINDER_COLUMN_UUID_G_UUID_3 = "(amImageEntry.uuid IS NULL OR amImageEntry.uuid = '') AND ";
	private static final String _FINDER_COLUMN_UUID_G_GROUPID_2 = "amImageEntry.groupId = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_UUID_C = new FinderPath(AMImageEntryModelImpl.ENTITY_CACHE_ENABLED,
			AMImageEntryModelImpl.FINDER_CACHE_ENABLED, AMImageEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUuid_C",
			new String[] {
				String.class.getName(), Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C =
		new FinderPath(AMImageEntryModelImpl.ENTITY_CACHE_ENABLED,
			AMImageEntryModelImpl.FINDER_CACHE_ENABLED, AMImageEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid_C",
			new String[] { String.class.getName(), Long.class.getName() },
			AMImageEntryModelImpl.UUID_COLUMN_BITMASK |
			AMImageEntryModelImpl.COMPANYID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID_C = new FinderPath(AMImageEntryModelImpl.ENTITY_CACHE_ENABLED,
			AMImageEntryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUuid_C",
			new String[] { String.class.getName(), Long.class.getName() });

	/**
	 * Returns all the am image entries where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching am image entries
	 */
	@Override
	public List<AMImageEntry> findByUuid_C(String uuid, long companyId) {
		return findByUuid_C(uuid, companyId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the am image entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AMImageEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of am image entries
	 * @param end the upper bound of the range of am image entries (not inclusive)
	 * @return the range of matching am image entries
	 */
	@Override
	public List<AMImageEntry> findByUuid_C(String uuid, long companyId,
		int start, int end) {
		return findByUuid_C(uuid, companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the am image entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AMImageEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of am image entries
	 * @param end the upper bound of the range of am image entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching am image entries
	 */
	@Override
	public List<AMImageEntry> findByUuid_C(String uuid, long companyId,
		int start, int end, OrderByComparator<AMImageEntry> orderByComparator) {
		return findByUuid_C(uuid, companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the am image entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AMImageEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of am image entries
	 * @param end the upper bound of the range of am image entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching am image entries
	 */
	@Override
	public List<AMImageEntry> findByUuid_C(String uuid, long companyId,
		int start, int end, OrderByComparator<AMImageEntry> orderByComparator,
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

		List<AMImageEntry> list = null;

		if (retrieveFromCache) {
			list = (List<AMImageEntry>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (AMImageEntry amImageEntry : list) {
					if (!Objects.equals(uuid, amImageEntry.getUuid()) ||
							(companyId != amImageEntry.getCompanyId())) {
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

			query.append(_SQL_SELECT_AMIMAGEENTRY_WHERE);

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
				query.append(AMImageEntryModelImpl.ORDER_BY_JPQL);
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
					list = (List<AMImageEntry>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<AMImageEntry>)QueryUtil.list(q, getDialect(),
							start, end);
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
	 * Returns the first am image entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching am image entry
	 * @throws NoSuchAMImageEntryException if a matching am image entry could not be found
	 */
	@Override
	public AMImageEntry findByUuid_C_First(String uuid, long companyId,
		OrderByComparator<AMImageEntry> orderByComparator)
		throws NoSuchAMImageEntryException {
		AMImageEntry amImageEntry = fetchByUuid_C_First(uuid, companyId,
				orderByComparator);

		if (amImageEntry != null) {
			return amImageEntry;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(", companyId=");
		msg.append(companyId);

		msg.append("}");

		throw new NoSuchAMImageEntryException(msg.toString());
	}

	/**
	 * Returns the first am image entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching am image entry, or <code>null</code> if a matching am image entry could not be found
	 */
	@Override
	public AMImageEntry fetchByUuid_C_First(String uuid, long companyId,
		OrderByComparator<AMImageEntry> orderByComparator) {
		List<AMImageEntry> list = findByUuid_C(uuid, companyId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last am image entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching am image entry
	 * @throws NoSuchAMImageEntryException if a matching am image entry could not be found
	 */
	@Override
	public AMImageEntry findByUuid_C_Last(String uuid, long companyId,
		OrderByComparator<AMImageEntry> orderByComparator)
		throws NoSuchAMImageEntryException {
		AMImageEntry amImageEntry = fetchByUuid_C_Last(uuid, companyId,
				orderByComparator);

		if (amImageEntry != null) {
			return amImageEntry;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(", companyId=");
		msg.append(companyId);

		msg.append("}");

		throw new NoSuchAMImageEntryException(msg.toString());
	}

	/**
	 * Returns the last am image entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching am image entry, or <code>null</code> if a matching am image entry could not be found
	 */
	@Override
	public AMImageEntry fetchByUuid_C_Last(String uuid, long companyId,
		OrderByComparator<AMImageEntry> orderByComparator) {
		int count = countByUuid_C(uuid, companyId);

		if (count == 0) {
			return null;
		}

		List<AMImageEntry> list = findByUuid_C(uuid, companyId, count - 1,
				count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the am image entries before and after the current am image entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param amImageEntryId the primary key of the current am image entry
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next am image entry
	 * @throws NoSuchAMImageEntryException if a am image entry with the primary key could not be found
	 */
	@Override
	public AMImageEntry[] findByUuid_C_PrevAndNext(long amImageEntryId,
		String uuid, long companyId,
		OrderByComparator<AMImageEntry> orderByComparator)
		throws NoSuchAMImageEntryException {
		AMImageEntry amImageEntry = findByPrimaryKey(amImageEntryId);

		Session session = null;

		try {
			session = openSession();

			AMImageEntry[] array = new AMImageEntryImpl[3];

			array[0] = getByUuid_C_PrevAndNext(session, amImageEntry, uuid,
					companyId, orderByComparator, true);

			array[1] = amImageEntry;

			array[2] = getByUuid_C_PrevAndNext(session, amImageEntry, uuid,
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

	protected AMImageEntry getByUuid_C_PrevAndNext(Session session,
		AMImageEntry amImageEntry, String uuid, long companyId,
		OrderByComparator<AMImageEntry> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(5 +
					(orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(4);
		}

		query.append(_SQL_SELECT_AMIMAGEENTRY_WHERE);

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
			query.append(AMImageEntryModelImpl.ORDER_BY_JPQL);
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
			Object[] values = orderByComparator.getOrderByConditionValues(amImageEntry);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<AMImageEntry> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the am image entries where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	@Override
	public void removeByUuid_C(String uuid, long companyId) {
		for (AMImageEntry amImageEntry : findByUuid_C(uuid, companyId,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(amImageEntry);
		}
	}

	/**
	 * Returns the number of am image entries where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching am image entries
	 */
	@Override
	public int countByUuid_C(String uuid, long companyId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_UUID_C;

		Object[] finderArgs = new Object[] { uuid, companyId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_AMIMAGEENTRY_WHERE);

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

	private static final String _FINDER_COLUMN_UUID_C_UUID_1 = "amImageEntry.uuid IS NULL AND ";
	private static final String _FINDER_COLUMN_UUID_C_UUID_2 = "amImageEntry.uuid = ? AND ";
	private static final String _FINDER_COLUMN_UUID_C_UUID_3 = "(amImageEntry.uuid IS NULL OR amImageEntry.uuid = '') AND ";
	private static final String _FINDER_COLUMN_UUID_C_COMPANYID_2 = "amImageEntry.companyId = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_GROUPID = new FinderPath(AMImageEntryModelImpl.ENTITY_CACHE_ENABLED,
			AMImageEntryModelImpl.FINDER_CACHE_ENABLED, AMImageEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByGroupId",
			new String[] {
				Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID =
		new FinderPath(AMImageEntryModelImpl.ENTITY_CACHE_ENABLED,
			AMImageEntryModelImpl.FINDER_CACHE_ENABLED, AMImageEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByGroupId",
			new String[] { Long.class.getName() },
			AMImageEntryModelImpl.GROUPID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_GROUPID = new FinderPath(AMImageEntryModelImpl.ENTITY_CACHE_ENABLED,
			AMImageEntryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByGroupId",
			new String[] { Long.class.getName() });

	/**
	 * Returns all the am image entries where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching am image entries
	 */
	@Override
	public List<AMImageEntry> findByGroupId(long groupId) {
		return findByGroupId(groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the am image entries where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AMImageEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of am image entries
	 * @param end the upper bound of the range of am image entries (not inclusive)
	 * @return the range of matching am image entries
	 */
	@Override
	public List<AMImageEntry> findByGroupId(long groupId, int start, int end) {
		return findByGroupId(groupId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the am image entries where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AMImageEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of am image entries
	 * @param end the upper bound of the range of am image entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching am image entries
	 */
	@Override
	public List<AMImageEntry> findByGroupId(long groupId, int start, int end,
		OrderByComparator<AMImageEntry> orderByComparator) {
		return findByGroupId(groupId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the am image entries where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AMImageEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of am image entries
	 * @param end the upper bound of the range of am image entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching am image entries
	 */
	@Override
	public List<AMImageEntry> findByGroupId(long groupId, int start, int end,
		OrderByComparator<AMImageEntry> orderByComparator,
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

		List<AMImageEntry> list = null;

		if (retrieveFromCache) {
			list = (List<AMImageEntry>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (AMImageEntry amImageEntry : list) {
					if ((groupId != amImageEntry.getGroupId())) {
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

			query.append(_SQL_SELECT_AMIMAGEENTRY_WHERE);

			query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(AMImageEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (!pagination) {
					list = (List<AMImageEntry>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<AMImageEntry>)QueryUtil.list(q, getDialect(),
							start, end);
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
	 * Returns the first am image entry in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching am image entry
	 * @throws NoSuchAMImageEntryException if a matching am image entry could not be found
	 */
	@Override
	public AMImageEntry findByGroupId_First(long groupId,
		OrderByComparator<AMImageEntry> orderByComparator)
		throws NoSuchAMImageEntryException {
		AMImageEntry amImageEntry = fetchByGroupId_First(groupId,
				orderByComparator);

		if (amImageEntry != null) {
			return amImageEntry;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append("}");

		throw new NoSuchAMImageEntryException(msg.toString());
	}

	/**
	 * Returns the first am image entry in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching am image entry, or <code>null</code> if a matching am image entry could not be found
	 */
	@Override
	public AMImageEntry fetchByGroupId_First(long groupId,
		OrderByComparator<AMImageEntry> orderByComparator) {
		List<AMImageEntry> list = findByGroupId(groupId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last am image entry in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching am image entry
	 * @throws NoSuchAMImageEntryException if a matching am image entry could not be found
	 */
	@Override
	public AMImageEntry findByGroupId_Last(long groupId,
		OrderByComparator<AMImageEntry> orderByComparator)
		throws NoSuchAMImageEntryException {
		AMImageEntry amImageEntry = fetchByGroupId_Last(groupId,
				orderByComparator);

		if (amImageEntry != null) {
			return amImageEntry;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append("}");

		throw new NoSuchAMImageEntryException(msg.toString());
	}

	/**
	 * Returns the last am image entry in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching am image entry, or <code>null</code> if a matching am image entry could not be found
	 */
	@Override
	public AMImageEntry fetchByGroupId_Last(long groupId,
		OrderByComparator<AMImageEntry> orderByComparator) {
		int count = countByGroupId(groupId);

		if (count == 0) {
			return null;
		}

		List<AMImageEntry> list = findByGroupId(groupId, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the am image entries before and after the current am image entry in the ordered set where groupId = &#63;.
	 *
	 * @param amImageEntryId the primary key of the current am image entry
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next am image entry
	 * @throws NoSuchAMImageEntryException if a am image entry with the primary key could not be found
	 */
	@Override
	public AMImageEntry[] findByGroupId_PrevAndNext(long amImageEntryId,
		long groupId, OrderByComparator<AMImageEntry> orderByComparator)
		throws NoSuchAMImageEntryException {
		AMImageEntry amImageEntry = findByPrimaryKey(amImageEntryId);

		Session session = null;

		try {
			session = openSession();

			AMImageEntry[] array = new AMImageEntryImpl[3];

			array[0] = getByGroupId_PrevAndNext(session, amImageEntry, groupId,
					orderByComparator, true);

			array[1] = amImageEntry;

			array[2] = getByGroupId_PrevAndNext(session, amImageEntry, groupId,
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

	protected AMImageEntry getByGroupId_PrevAndNext(Session session,
		AMImageEntry amImageEntry, long groupId,
		OrderByComparator<AMImageEntry> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(4 +
					(orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_AMIMAGEENTRY_WHERE);

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
			query.append(AMImageEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(amImageEntry);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<AMImageEntry> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the am image entries where groupId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 */
	@Override
	public void removeByGroupId(long groupId) {
		for (AMImageEntry amImageEntry : findByGroupId(groupId,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(amImageEntry);
		}
	}

	/**
	 * Returns the number of am image entries where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching am image entries
	 */
	@Override
	public int countByGroupId(long groupId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_GROUPID;

		Object[] finderArgs = new Object[] { groupId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_AMIMAGEENTRY_WHERE);

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

	private static final String _FINDER_COLUMN_GROUPID_GROUPID_2 = "amImageEntry.groupId = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_COMPANYID =
		new FinderPath(AMImageEntryModelImpl.ENTITY_CACHE_ENABLED,
			AMImageEntryModelImpl.FINDER_CACHE_ENABLED, AMImageEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByCompanyId",
			new String[] {
				Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID =
		new FinderPath(AMImageEntryModelImpl.ENTITY_CACHE_ENABLED,
			AMImageEntryModelImpl.FINDER_CACHE_ENABLED, AMImageEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByCompanyId",
			new String[] { Long.class.getName() },
			AMImageEntryModelImpl.COMPANYID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_COMPANYID = new FinderPath(AMImageEntryModelImpl.ENTITY_CACHE_ENABLED,
			AMImageEntryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByCompanyId",
			new String[] { Long.class.getName() });

	/**
	 * Returns all the am image entries where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching am image entries
	 */
	@Override
	public List<AMImageEntry> findByCompanyId(long companyId) {
		return findByCompanyId(companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the am image entries where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AMImageEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of am image entries
	 * @param end the upper bound of the range of am image entries (not inclusive)
	 * @return the range of matching am image entries
	 */
	@Override
	public List<AMImageEntry> findByCompanyId(long companyId, int start, int end) {
		return findByCompanyId(companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the am image entries where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AMImageEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of am image entries
	 * @param end the upper bound of the range of am image entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching am image entries
	 */
	@Override
	public List<AMImageEntry> findByCompanyId(long companyId, int start,
		int end, OrderByComparator<AMImageEntry> orderByComparator) {
		return findByCompanyId(companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the am image entries where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AMImageEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of am image entries
	 * @param end the upper bound of the range of am image entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching am image entries
	 */
	@Override
	public List<AMImageEntry> findByCompanyId(long companyId, int start,
		int end, OrderByComparator<AMImageEntry> orderByComparator,
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

		List<AMImageEntry> list = null;

		if (retrieveFromCache) {
			list = (List<AMImageEntry>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (AMImageEntry amImageEntry : list) {
					if ((companyId != amImageEntry.getCompanyId())) {
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

			query.append(_SQL_SELECT_AMIMAGEENTRY_WHERE);

			query.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(AMImageEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				if (!pagination) {
					list = (List<AMImageEntry>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<AMImageEntry>)QueryUtil.list(q, getDialect(),
							start, end);
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
	 * Returns the first am image entry in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching am image entry
	 * @throws NoSuchAMImageEntryException if a matching am image entry could not be found
	 */
	@Override
	public AMImageEntry findByCompanyId_First(long companyId,
		OrderByComparator<AMImageEntry> orderByComparator)
		throws NoSuchAMImageEntryException {
		AMImageEntry amImageEntry = fetchByCompanyId_First(companyId,
				orderByComparator);

		if (amImageEntry != null) {
			return amImageEntry;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("companyId=");
		msg.append(companyId);

		msg.append("}");

		throw new NoSuchAMImageEntryException(msg.toString());
	}

	/**
	 * Returns the first am image entry in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching am image entry, or <code>null</code> if a matching am image entry could not be found
	 */
	@Override
	public AMImageEntry fetchByCompanyId_First(long companyId,
		OrderByComparator<AMImageEntry> orderByComparator) {
		List<AMImageEntry> list = findByCompanyId(companyId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last am image entry in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching am image entry
	 * @throws NoSuchAMImageEntryException if a matching am image entry could not be found
	 */
	@Override
	public AMImageEntry findByCompanyId_Last(long companyId,
		OrderByComparator<AMImageEntry> orderByComparator)
		throws NoSuchAMImageEntryException {
		AMImageEntry amImageEntry = fetchByCompanyId_Last(companyId,
				orderByComparator);

		if (amImageEntry != null) {
			return amImageEntry;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("companyId=");
		msg.append(companyId);

		msg.append("}");

		throw new NoSuchAMImageEntryException(msg.toString());
	}

	/**
	 * Returns the last am image entry in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching am image entry, or <code>null</code> if a matching am image entry could not be found
	 */
	@Override
	public AMImageEntry fetchByCompanyId_Last(long companyId,
		OrderByComparator<AMImageEntry> orderByComparator) {
		int count = countByCompanyId(companyId);

		if (count == 0) {
			return null;
		}

		List<AMImageEntry> list = findByCompanyId(companyId, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the am image entries before and after the current am image entry in the ordered set where companyId = &#63;.
	 *
	 * @param amImageEntryId the primary key of the current am image entry
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next am image entry
	 * @throws NoSuchAMImageEntryException if a am image entry with the primary key could not be found
	 */
	@Override
	public AMImageEntry[] findByCompanyId_PrevAndNext(long amImageEntryId,
		long companyId, OrderByComparator<AMImageEntry> orderByComparator)
		throws NoSuchAMImageEntryException {
		AMImageEntry amImageEntry = findByPrimaryKey(amImageEntryId);

		Session session = null;

		try {
			session = openSession();

			AMImageEntry[] array = new AMImageEntryImpl[3];

			array[0] = getByCompanyId_PrevAndNext(session, amImageEntry,
					companyId, orderByComparator, true);

			array[1] = amImageEntry;

			array[2] = getByCompanyId_PrevAndNext(session, amImageEntry,
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

	protected AMImageEntry getByCompanyId_PrevAndNext(Session session,
		AMImageEntry amImageEntry, long companyId,
		OrderByComparator<AMImageEntry> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(4 +
					(orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_AMIMAGEENTRY_WHERE);

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
			query.append(AMImageEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(companyId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(amImageEntry);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<AMImageEntry> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the am image entries where companyId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 */
	@Override
	public void removeByCompanyId(long companyId) {
		for (AMImageEntry amImageEntry : findByCompanyId(companyId,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(amImageEntry);
		}
	}

	/**
	 * Returns the number of am image entries where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching am image entries
	 */
	@Override
	public int countByCompanyId(long companyId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_COMPANYID;

		Object[] finderArgs = new Object[] { companyId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_AMIMAGEENTRY_WHERE);

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

	private static final String _FINDER_COLUMN_COMPANYID_COMPANYID_2 = "amImageEntry.companyId = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_CONFIGURATIONUUID =
		new FinderPath(AMImageEntryModelImpl.ENTITY_CACHE_ENABLED,
			AMImageEntryModelImpl.FINDER_CACHE_ENABLED, AMImageEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByConfigurationUuid",
			new String[] {
				String.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_CONFIGURATIONUUID =
		new FinderPath(AMImageEntryModelImpl.ENTITY_CACHE_ENABLED,
			AMImageEntryModelImpl.FINDER_CACHE_ENABLED, AMImageEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByConfigurationUuid", new String[] { String.class.getName() },
			AMImageEntryModelImpl.CONFIGURATIONUUID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_CONFIGURATIONUUID = new FinderPath(AMImageEntryModelImpl.ENTITY_CACHE_ENABLED,
			AMImageEntryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByConfigurationUuid", new String[] { String.class.getName() });

	/**
	 * Returns all the am image entries where configurationUuid = &#63;.
	 *
	 * @param configurationUuid the configuration uuid
	 * @return the matching am image entries
	 */
	@Override
	public List<AMImageEntry> findByConfigurationUuid(String configurationUuid) {
		return findByConfigurationUuid(configurationUuid, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the am image entries where configurationUuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AMImageEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param configurationUuid the configuration uuid
	 * @param start the lower bound of the range of am image entries
	 * @param end the upper bound of the range of am image entries (not inclusive)
	 * @return the range of matching am image entries
	 */
	@Override
	public List<AMImageEntry> findByConfigurationUuid(
		String configurationUuid, int start, int end) {
		return findByConfigurationUuid(configurationUuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the am image entries where configurationUuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AMImageEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param configurationUuid the configuration uuid
	 * @param start the lower bound of the range of am image entries
	 * @param end the upper bound of the range of am image entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching am image entries
	 */
	@Override
	public List<AMImageEntry> findByConfigurationUuid(
		String configurationUuid, int start, int end,
		OrderByComparator<AMImageEntry> orderByComparator) {
		return findByConfigurationUuid(configurationUuid, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the am image entries where configurationUuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AMImageEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param configurationUuid the configuration uuid
	 * @param start the lower bound of the range of am image entries
	 * @param end the upper bound of the range of am image entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching am image entries
	 */
	@Override
	public List<AMImageEntry> findByConfigurationUuid(
		String configurationUuid, int start, int end,
		OrderByComparator<AMImageEntry> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_CONFIGURATIONUUID;
			finderArgs = new Object[] { configurationUuid };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_CONFIGURATIONUUID;
			finderArgs = new Object[] {
					configurationUuid,
					
					start, end, orderByComparator
				};
		}

		List<AMImageEntry> list = null;

		if (retrieveFromCache) {
			list = (List<AMImageEntry>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (AMImageEntry amImageEntry : list) {
					if (!Objects.equals(configurationUuid,
								amImageEntry.getConfigurationUuid())) {
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

			query.append(_SQL_SELECT_AMIMAGEENTRY_WHERE);

			boolean bindConfigurationUuid = false;

			if (configurationUuid == null) {
				query.append(_FINDER_COLUMN_CONFIGURATIONUUID_CONFIGURATIONUUID_1);
			}
			else if (configurationUuid.equals("")) {
				query.append(_FINDER_COLUMN_CONFIGURATIONUUID_CONFIGURATIONUUID_3);
			}
			else {
				bindConfigurationUuid = true;

				query.append(_FINDER_COLUMN_CONFIGURATIONUUID_CONFIGURATIONUUID_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(AMImageEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindConfigurationUuid) {
					qPos.add(configurationUuid);
				}

				if (!pagination) {
					list = (List<AMImageEntry>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<AMImageEntry>)QueryUtil.list(q, getDialect(),
							start, end);
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
	 * Returns the first am image entry in the ordered set where configurationUuid = &#63;.
	 *
	 * @param configurationUuid the configuration uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching am image entry
	 * @throws NoSuchAMImageEntryException if a matching am image entry could not be found
	 */
	@Override
	public AMImageEntry findByConfigurationUuid_First(
		String configurationUuid,
		OrderByComparator<AMImageEntry> orderByComparator)
		throws NoSuchAMImageEntryException {
		AMImageEntry amImageEntry = fetchByConfigurationUuid_First(configurationUuid,
				orderByComparator);

		if (amImageEntry != null) {
			return amImageEntry;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("configurationUuid=");
		msg.append(configurationUuid);

		msg.append("}");

		throw new NoSuchAMImageEntryException(msg.toString());
	}

	/**
	 * Returns the first am image entry in the ordered set where configurationUuid = &#63;.
	 *
	 * @param configurationUuid the configuration uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching am image entry, or <code>null</code> if a matching am image entry could not be found
	 */
	@Override
	public AMImageEntry fetchByConfigurationUuid_First(
		String configurationUuid,
		OrderByComparator<AMImageEntry> orderByComparator) {
		List<AMImageEntry> list = findByConfigurationUuid(configurationUuid, 0,
				1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last am image entry in the ordered set where configurationUuid = &#63;.
	 *
	 * @param configurationUuid the configuration uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching am image entry
	 * @throws NoSuchAMImageEntryException if a matching am image entry could not be found
	 */
	@Override
	public AMImageEntry findByConfigurationUuid_Last(String configurationUuid,
		OrderByComparator<AMImageEntry> orderByComparator)
		throws NoSuchAMImageEntryException {
		AMImageEntry amImageEntry = fetchByConfigurationUuid_Last(configurationUuid,
				orderByComparator);

		if (amImageEntry != null) {
			return amImageEntry;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("configurationUuid=");
		msg.append(configurationUuid);

		msg.append("}");

		throw new NoSuchAMImageEntryException(msg.toString());
	}

	/**
	 * Returns the last am image entry in the ordered set where configurationUuid = &#63;.
	 *
	 * @param configurationUuid the configuration uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching am image entry, or <code>null</code> if a matching am image entry could not be found
	 */
	@Override
	public AMImageEntry fetchByConfigurationUuid_Last(
		String configurationUuid,
		OrderByComparator<AMImageEntry> orderByComparator) {
		int count = countByConfigurationUuid(configurationUuid);

		if (count == 0) {
			return null;
		}

		List<AMImageEntry> list = findByConfigurationUuid(configurationUuid,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the am image entries before and after the current am image entry in the ordered set where configurationUuid = &#63;.
	 *
	 * @param amImageEntryId the primary key of the current am image entry
	 * @param configurationUuid the configuration uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next am image entry
	 * @throws NoSuchAMImageEntryException if a am image entry with the primary key could not be found
	 */
	@Override
	public AMImageEntry[] findByConfigurationUuid_PrevAndNext(
		long amImageEntryId, String configurationUuid,
		OrderByComparator<AMImageEntry> orderByComparator)
		throws NoSuchAMImageEntryException {
		AMImageEntry amImageEntry = findByPrimaryKey(amImageEntryId);

		Session session = null;

		try {
			session = openSession();

			AMImageEntry[] array = new AMImageEntryImpl[3];

			array[0] = getByConfigurationUuid_PrevAndNext(session,
					amImageEntry, configurationUuid, orderByComparator, true);

			array[1] = amImageEntry;

			array[2] = getByConfigurationUuid_PrevAndNext(session,
					amImageEntry, configurationUuid, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected AMImageEntry getByConfigurationUuid_PrevAndNext(Session session,
		AMImageEntry amImageEntry, String configurationUuid,
		OrderByComparator<AMImageEntry> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(4 +
					(orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_AMIMAGEENTRY_WHERE);

		boolean bindConfigurationUuid = false;

		if (configurationUuid == null) {
			query.append(_FINDER_COLUMN_CONFIGURATIONUUID_CONFIGURATIONUUID_1);
		}
		else if (configurationUuid.equals("")) {
			query.append(_FINDER_COLUMN_CONFIGURATIONUUID_CONFIGURATIONUUID_3);
		}
		else {
			bindConfigurationUuid = true;

			query.append(_FINDER_COLUMN_CONFIGURATIONUUID_CONFIGURATIONUUID_2);
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
			query.append(AMImageEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		if (bindConfigurationUuid) {
			qPos.add(configurationUuid);
		}

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(amImageEntry);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<AMImageEntry> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the am image entries where configurationUuid = &#63; from the database.
	 *
	 * @param configurationUuid the configuration uuid
	 */
	@Override
	public void removeByConfigurationUuid(String configurationUuid) {
		for (AMImageEntry amImageEntry : findByConfigurationUuid(
				configurationUuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(amImageEntry);
		}
	}

	/**
	 * Returns the number of am image entries where configurationUuid = &#63;.
	 *
	 * @param configurationUuid the configuration uuid
	 * @return the number of matching am image entries
	 */
	@Override
	public int countByConfigurationUuid(String configurationUuid) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_CONFIGURATIONUUID;

		Object[] finderArgs = new Object[] { configurationUuid };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_AMIMAGEENTRY_WHERE);

			boolean bindConfigurationUuid = false;

			if (configurationUuid == null) {
				query.append(_FINDER_COLUMN_CONFIGURATIONUUID_CONFIGURATIONUUID_1);
			}
			else if (configurationUuid.equals("")) {
				query.append(_FINDER_COLUMN_CONFIGURATIONUUID_CONFIGURATIONUUID_3);
			}
			else {
				bindConfigurationUuid = true;

				query.append(_FINDER_COLUMN_CONFIGURATIONUUID_CONFIGURATIONUUID_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindConfigurationUuid) {
					qPos.add(configurationUuid);
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

	private static final String _FINDER_COLUMN_CONFIGURATIONUUID_CONFIGURATIONUUID_1 =
		"amImageEntry.configurationUuid IS NULL";
	private static final String _FINDER_COLUMN_CONFIGURATIONUUID_CONFIGURATIONUUID_2 =
		"amImageEntry.configurationUuid = ?";
	private static final String _FINDER_COLUMN_CONFIGURATIONUUID_CONFIGURATIONUUID_3 =
		"(amImageEntry.configurationUuid IS NULL OR amImageEntry.configurationUuid = '')";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_FILEVERSIONID =
		new FinderPath(AMImageEntryModelImpl.ENTITY_CACHE_ENABLED,
			AMImageEntryModelImpl.FINDER_CACHE_ENABLED, AMImageEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByFileVersionId",
			new String[] {
				Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_FILEVERSIONID =
		new FinderPath(AMImageEntryModelImpl.ENTITY_CACHE_ENABLED,
			AMImageEntryModelImpl.FINDER_CACHE_ENABLED, AMImageEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByFileVersionId",
			new String[] { Long.class.getName() },
			AMImageEntryModelImpl.FILEVERSIONID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_FILEVERSIONID = new FinderPath(AMImageEntryModelImpl.ENTITY_CACHE_ENABLED,
			AMImageEntryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByFileVersionId",
			new String[] { Long.class.getName() });

	/**
	 * Returns all the am image entries where fileVersionId = &#63;.
	 *
	 * @param fileVersionId the file version ID
	 * @return the matching am image entries
	 */
	@Override
	public List<AMImageEntry> findByFileVersionId(long fileVersionId) {
		return findByFileVersionId(fileVersionId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the am image entries where fileVersionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AMImageEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param fileVersionId the file version ID
	 * @param start the lower bound of the range of am image entries
	 * @param end the upper bound of the range of am image entries (not inclusive)
	 * @return the range of matching am image entries
	 */
	@Override
	public List<AMImageEntry> findByFileVersionId(long fileVersionId,
		int start, int end) {
		return findByFileVersionId(fileVersionId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the am image entries where fileVersionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AMImageEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param fileVersionId the file version ID
	 * @param start the lower bound of the range of am image entries
	 * @param end the upper bound of the range of am image entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching am image entries
	 */
	@Override
	public List<AMImageEntry> findByFileVersionId(long fileVersionId,
		int start, int end, OrderByComparator<AMImageEntry> orderByComparator) {
		return findByFileVersionId(fileVersionId, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the am image entries where fileVersionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AMImageEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param fileVersionId the file version ID
	 * @param start the lower bound of the range of am image entries
	 * @param end the upper bound of the range of am image entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching am image entries
	 */
	@Override
	public List<AMImageEntry> findByFileVersionId(long fileVersionId,
		int start, int end, OrderByComparator<AMImageEntry> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_FILEVERSIONID;
			finderArgs = new Object[] { fileVersionId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_FILEVERSIONID;
			finderArgs = new Object[] {
					fileVersionId,
					
					start, end, orderByComparator
				};
		}

		List<AMImageEntry> list = null;

		if (retrieveFromCache) {
			list = (List<AMImageEntry>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (AMImageEntry amImageEntry : list) {
					if ((fileVersionId != amImageEntry.getFileVersionId())) {
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

			query.append(_SQL_SELECT_AMIMAGEENTRY_WHERE);

			query.append(_FINDER_COLUMN_FILEVERSIONID_FILEVERSIONID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(AMImageEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(fileVersionId);

				if (!pagination) {
					list = (List<AMImageEntry>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<AMImageEntry>)QueryUtil.list(q, getDialect(),
							start, end);
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
	 * Returns the first am image entry in the ordered set where fileVersionId = &#63;.
	 *
	 * @param fileVersionId the file version ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching am image entry
	 * @throws NoSuchAMImageEntryException if a matching am image entry could not be found
	 */
	@Override
	public AMImageEntry findByFileVersionId_First(long fileVersionId,
		OrderByComparator<AMImageEntry> orderByComparator)
		throws NoSuchAMImageEntryException {
		AMImageEntry amImageEntry = fetchByFileVersionId_First(fileVersionId,
				orderByComparator);

		if (amImageEntry != null) {
			return amImageEntry;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("fileVersionId=");
		msg.append(fileVersionId);

		msg.append("}");

		throw new NoSuchAMImageEntryException(msg.toString());
	}

	/**
	 * Returns the first am image entry in the ordered set where fileVersionId = &#63;.
	 *
	 * @param fileVersionId the file version ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching am image entry, or <code>null</code> if a matching am image entry could not be found
	 */
	@Override
	public AMImageEntry fetchByFileVersionId_First(long fileVersionId,
		OrderByComparator<AMImageEntry> orderByComparator) {
		List<AMImageEntry> list = findByFileVersionId(fileVersionId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last am image entry in the ordered set where fileVersionId = &#63;.
	 *
	 * @param fileVersionId the file version ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching am image entry
	 * @throws NoSuchAMImageEntryException if a matching am image entry could not be found
	 */
	@Override
	public AMImageEntry findByFileVersionId_Last(long fileVersionId,
		OrderByComparator<AMImageEntry> orderByComparator)
		throws NoSuchAMImageEntryException {
		AMImageEntry amImageEntry = fetchByFileVersionId_Last(fileVersionId,
				orderByComparator);

		if (amImageEntry != null) {
			return amImageEntry;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("fileVersionId=");
		msg.append(fileVersionId);

		msg.append("}");

		throw new NoSuchAMImageEntryException(msg.toString());
	}

	/**
	 * Returns the last am image entry in the ordered set where fileVersionId = &#63;.
	 *
	 * @param fileVersionId the file version ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching am image entry, or <code>null</code> if a matching am image entry could not be found
	 */
	@Override
	public AMImageEntry fetchByFileVersionId_Last(long fileVersionId,
		OrderByComparator<AMImageEntry> orderByComparator) {
		int count = countByFileVersionId(fileVersionId);

		if (count == 0) {
			return null;
		}

		List<AMImageEntry> list = findByFileVersionId(fileVersionId, count - 1,
				count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the am image entries before and after the current am image entry in the ordered set where fileVersionId = &#63;.
	 *
	 * @param amImageEntryId the primary key of the current am image entry
	 * @param fileVersionId the file version ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next am image entry
	 * @throws NoSuchAMImageEntryException if a am image entry with the primary key could not be found
	 */
	@Override
	public AMImageEntry[] findByFileVersionId_PrevAndNext(long amImageEntryId,
		long fileVersionId, OrderByComparator<AMImageEntry> orderByComparator)
		throws NoSuchAMImageEntryException {
		AMImageEntry amImageEntry = findByPrimaryKey(amImageEntryId);

		Session session = null;

		try {
			session = openSession();

			AMImageEntry[] array = new AMImageEntryImpl[3];

			array[0] = getByFileVersionId_PrevAndNext(session, amImageEntry,
					fileVersionId, orderByComparator, true);

			array[1] = amImageEntry;

			array[2] = getByFileVersionId_PrevAndNext(session, amImageEntry,
					fileVersionId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected AMImageEntry getByFileVersionId_PrevAndNext(Session session,
		AMImageEntry amImageEntry, long fileVersionId,
		OrderByComparator<AMImageEntry> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(4 +
					(orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_AMIMAGEENTRY_WHERE);

		query.append(_FINDER_COLUMN_FILEVERSIONID_FILEVERSIONID_2);

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
			query.append(AMImageEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(fileVersionId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(amImageEntry);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<AMImageEntry> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the am image entries where fileVersionId = &#63; from the database.
	 *
	 * @param fileVersionId the file version ID
	 */
	@Override
	public void removeByFileVersionId(long fileVersionId) {
		for (AMImageEntry amImageEntry : findByFileVersionId(fileVersionId,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(amImageEntry);
		}
	}

	/**
	 * Returns the number of am image entries where fileVersionId = &#63;.
	 *
	 * @param fileVersionId the file version ID
	 * @return the number of matching am image entries
	 */
	@Override
	public int countByFileVersionId(long fileVersionId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_FILEVERSIONID;

		Object[] finderArgs = new Object[] { fileVersionId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_AMIMAGEENTRY_WHERE);

			query.append(_FINDER_COLUMN_FILEVERSIONID_FILEVERSIONID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(fileVersionId);

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

	private static final String _FINDER_COLUMN_FILEVERSIONID_FILEVERSIONID_2 = "amImageEntry.fileVersionId = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_C_C = new FinderPath(AMImageEntryModelImpl.ENTITY_CACHE_ENABLED,
			AMImageEntryModelImpl.FINDER_CACHE_ENABLED, AMImageEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByC_C",
			new String[] {
				Long.class.getName(), String.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_C = new FinderPath(AMImageEntryModelImpl.ENTITY_CACHE_ENABLED,
			AMImageEntryModelImpl.FINDER_CACHE_ENABLED, AMImageEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByC_C",
			new String[] { Long.class.getName(), String.class.getName() },
			AMImageEntryModelImpl.COMPANYID_COLUMN_BITMASK |
			AMImageEntryModelImpl.CONFIGURATIONUUID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_C_C = new FinderPath(AMImageEntryModelImpl.ENTITY_CACHE_ENABLED,
			AMImageEntryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_C",
			new String[] { Long.class.getName(), String.class.getName() });

	/**
	 * Returns all the am image entries where companyId = &#63; and configurationUuid = &#63;.
	 *
	 * @param companyId the company ID
	 * @param configurationUuid the configuration uuid
	 * @return the matching am image entries
	 */
	@Override
	public List<AMImageEntry> findByC_C(long companyId, String configurationUuid) {
		return findByC_C(companyId, configurationUuid, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the am image entries where companyId = &#63; and configurationUuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AMImageEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param configurationUuid the configuration uuid
	 * @param start the lower bound of the range of am image entries
	 * @param end the upper bound of the range of am image entries (not inclusive)
	 * @return the range of matching am image entries
	 */
	@Override
	public List<AMImageEntry> findByC_C(long companyId,
		String configurationUuid, int start, int end) {
		return findByC_C(companyId, configurationUuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the am image entries where companyId = &#63; and configurationUuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AMImageEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param configurationUuid the configuration uuid
	 * @param start the lower bound of the range of am image entries
	 * @param end the upper bound of the range of am image entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching am image entries
	 */
	@Override
	public List<AMImageEntry> findByC_C(long companyId,
		String configurationUuid, int start, int end,
		OrderByComparator<AMImageEntry> orderByComparator) {
		return findByC_C(companyId, configurationUuid, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the am image entries where companyId = &#63; and configurationUuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AMImageEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param configurationUuid the configuration uuid
	 * @param start the lower bound of the range of am image entries
	 * @param end the upper bound of the range of am image entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching am image entries
	 */
	@Override
	public List<AMImageEntry> findByC_C(long companyId,
		String configurationUuid, int start, int end,
		OrderByComparator<AMImageEntry> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_C;
			finderArgs = new Object[] { companyId, configurationUuid };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_C_C;
			finderArgs = new Object[] {
					companyId, configurationUuid,
					
					start, end, orderByComparator
				};
		}

		List<AMImageEntry> list = null;

		if (retrieveFromCache) {
			list = (List<AMImageEntry>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (AMImageEntry amImageEntry : list) {
					if ((companyId != amImageEntry.getCompanyId()) ||
							!Objects.equals(configurationUuid,
								amImageEntry.getConfigurationUuid())) {
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

			query.append(_SQL_SELECT_AMIMAGEENTRY_WHERE);

			query.append(_FINDER_COLUMN_C_C_COMPANYID_2);

			boolean bindConfigurationUuid = false;

			if (configurationUuid == null) {
				query.append(_FINDER_COLUMN_C_C_CONFIGURATIONUUID_1);
			}
			else if (configurationUuid.equals("")) {
				query.append(_FINDER_COLUMN_C_C_CONFIGURATIONUUID_3);
			}
			else {
				bindConfigurationUuid = true;

				query.append(_FINDER_COLUMN_C_C_CONFIGURATIONUUID_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(AMImageEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				if (bindConfigurationUuid) {
					qPos.add(configurationUuid);
				}

				if (!pagination) {
					list = (List<AMImageEntry>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<AMImageEntry>)QueryUtil.list(q, getDialect(),
							start, end);
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
	 * Returns the first am image entry in the ordered set where companyId = &#63; and configurationUuid = &#63;.
	 *
	 * @param companyId the company ID
	 * @param configurationUuid the configuration uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching am image entry
	 * @throws NoSuchAMImageEntryException if a matching am image entry could not be found
	 */
	@Override
	public AMImageEntry findByC_C_First(long companyId,
		String configurationUuid,
		OrderByComparator<AMImageEntry> orderByComparator)
		throws NoSuchAMImageEntryException {
		AMImageEntry amImageEntry = fetchByC_C_First(companyId,
				configurationUuid, orderByComparator);

		if (amImageEntry != null) {
			return amImageEntry;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("companyId=");
		msg.append(companyId);

		msg.append(", configurationUuid=");
		msg.append(configurationUuid);

		msg.append("}");

		throw new NoSuchAMImageEntryException(msg.toString());
	}

	/**
	 * Returns the first am image entry in the ordered set where companyId = &#63; and configurationUuid = &#63;.
	 *
	 * @param companyId the company ID
	 * @param configurationUuid the configuration uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching am image entry, or <code>null</code> if a matching am image entry could not be found
	 */
	@Override
	public AMImageEntry fetchByC_C_First(long companyId,
		String configurationUuid,
		OrderByComparator<AMImageEntry> orderByComparator) {
		List<AMImageEntry> list = findByC_C(companyId, configurationUuid, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last am image entry in the ordered set where companyId = &#63; and configurationUuid = &#63;.
	 *
	 * @param companyId the company ID
	 * @param configurationUuid the configuration uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching am image entry
	 * @throws NoSuchAMImageEntryException if a matching am image entry could not be found
	 */
	@Override
	public AMImageEntry findByC_C_Last(long companyId,
		String configurationUuid,
		OrderByComparator<AMImageEntry> orderByComparator)
		throws NoSuchAMImageEntryException {
		AMImageEntry amImageEntry = fetchByC_C_Last(companyId,
				configurationUuid, orderByComparator);

		if (amImageEntry != null) {
			return amImageEntry;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("companyId=");
		msg.append(companyId);

		msg.append(", configurationUuid=");
		msg.append(configurationUuid);

		msg.append("}");

		throw new NoSuchAMImageEntryException(msg.toString());
	}

	/**
	 * Returns the last am image entry in the ordered set where companyId = &#63; and configurationUuid = &#63;.
	 *
	 * @param companyId the company ID
	 * @param configurationUuid the configuration uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching am image entry, or <code>null</code> if a matching am image entry could not be found
	 */
	@Override
	public AMImageEntry fetchByC_C_Last(long companyId,
		String configurationUuid,
		OrderByComparator<AMImageEntry> orderByComparator) {
		int count = countByC_C(companyId, configurationUuid);

		if (count == 0) {
			return null;
		}

		List<AMImageEntry> list = findByC_C(companyId, configurationUuid,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the am image entries before and after the current am image entry in the ordered set where companyId = &#63; and configurationUuid = &#63;.
	 *
	 * @param amImageEntryId the primary key of the current am image entry
	 * @param companyId the company ID
	 * @param configurationUuid the configuration uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next am image entry
	 * @throws NoSuchAMImageEntryException if a am image entry with the primary key could not be found
	 */
	@Override
	public AMImageEntry[] findByC_C_PrevAndNext(long amImageEntryId,
		long companyId, String configurationUuid,
		OrderByComparator<AMImageEntry> orderByComparator)
		throws NoSuchAMImageEntryException {
		AMImageEntry amImageEntry = findByPrimaryKey(amImageEntryId);

		Session session = null;

		try {
			session = openSession();

			AMImageEntry[] array = new AMImageEntryImpl[3];

			array[0] = getByC_C_PrevAndNext(session, amImageEntry, companyId,
					configurationUuid, orderByComparator, true);

			array[1] = amImageEntry;

			array[2] = getByC_C_PrevAndNext(session, amImageEntry, companyId,
					configurationUuid, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected AMImageEntry getByC_C_PrevAndNext(Session session,
		AMImageEntry amImageEntry, long companyId, String configurationUuid,
		OrderByComparator<AMImageEntry> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(5 +
					(orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(4);
		}

		query.append(_SQL_SELECT_AMIMAGEENTRY_WHERE);

		query.append(_FINDER_COLUMN_C_C_COMPANYID_2);

		boolean bindConfigurationUuid = false;

		if (configurationUuid == null) {
			query.append(_FINDER_COLUMN_C_C_CONFIGURATIONUUID_1);
		}
		else if (configurationUuid.equals("")) {
			query.append(_FINDER_COLUMN_C_C_CONFIGURATIONUUID_3);
		}
		else {
			bindConfigurationUuid = true;

			query.append(_FINDER_COLUMN_C_C_CONFIGURATIONUUID_2);
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
			query.append(AMImageEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(companyId);

		if (bindConfigurationUuid) {
			qPos.add(configurationUuid);
		}

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(amImageEntry);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<AMImageEntry> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the am image entries where companyId = &#63; and configurationUuid = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param configurationUuid the configuration uuid
	 */
	@Override
	public void removeByC_C(long companyId, String configurationUuid) {
		for (AMImageEntry amImageEntry : findByC_C(companyId,
				configurationUuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(amImageEntry);
		}
	}

	/**
	 * Returns the number of am image entries where companyId = &#63; and configurationUuid = &#63;.
	 *
	 * @param companyId the company ID
	 * @param configurationUuid the configuration uuid
	 * @return the number of matching am image entries
	 */
	@Override
	public int countByC_C(long companyId, String configurationUuid) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_C_C;

		Object[] finderArgs = new Object[] { companyId, configurationUuid };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_AMIMAGEENTRY_WHERE);

			query.append(_FINDER_COLUMN_C_C_COMPANYID_2);

			boolean bindConfigurationUuid = false;

			if (configurationUuid == null) {
				query.append(_FINDER_COLUMN_C_C_CONFIGURATIONUUID_1);
			}
			else if (configurationUuid.equals("")) {
				query.append(_FINDER_COLUMN_C_C_CONFIGURATIONUUID_3);
			}
			else {
				bindConfigurationUuid = true;

				query.append(_FINDER_COLUMN_C_C_CONFIGURATIONUUID_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				if (bindConfigurationUuid) {
					qPos.add(configurationUuid);
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

	private static final String _FINDER_COLUMN_C_C_COMPANYID_2 = "amImageEntry.companyId = ? AND ";
	private static final String _FINDER_COLUMN_C_C_CONFIGURATIONUUID_1 = "amImageEntry.configurationUuid IS NULL";
	private static final String _FINDER_COLUMN_C_C_CONFIGURATIONUUID_2 = "amImageEntry.configurationUuid = ?";
	private static final String _FINDER_COLUMN_C_C_CONFIGURATIONUUID_3 = "(amImageEntry.configurationUuid IS NULL OR amImageEntry.configurationUuid = '')";
	public static final FinderPath FINDER_PATH_FETCH_BY_C_F = new FinderPath(AMImageEntryModelImpl.ENTITY_CACHE_ENABLED,
			AMImageEntryModelImpl.FINDER_CACHE_ENABLED, AMImageEntryImpl.class,
			FINDER_CLASS_NAME_ENTITY, "fetchByC_F",
			new String[] { String.class.getName(), Long.class.getName() },
			AMImageEntryModelImpl.CONFIGURATIONUUID_COLUMN_BITMASK |
			AMImageEntryModelImpl.FILEVERSIONID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_C_F = new FinderPath(AMImageEntryModelImpl.ENTITY_CACHE_ENABLED,
			AMImageEntryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_F",
			new String[] { String.class.getName(), Long.class.getName() });

	/**
	 * Returns the am image entry where configurationUuid = &#63; and fileVersionId = &#63; or throws a {@link NoSuchAMImageEntryException} if it could not be found.
	 *
	 * @param configurationUuid the configuration uuid
	 * @param fileVersionId the file version ID
	 * @return the matching am image entry
	 * @throws NoSuchAMImageEntryException if a matching am image entry could not be found
	 */
	@Override
	public AMImageEntry findByC_F(String configurationUuid, long fileVersionId)
		throws NoSuchAMImageEntryException {
		AMImageEntry amImageEntry = fetchByC_F(configurationUuid, fileVersionId);

		if (amImageEntry == null) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("configurationUuid=");
			msg.append(configurationUuid);

			msg.append(", fileVersionId=");
			msg.append(fileVersionId);

			msg.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(msg.toString());
			}

			throw new NoSuchAMImageEntryException(msg.toString());
		}

		return amImageEntry;
	}

	/**
	 * Returns the am image entry where configurationUuid = &#63; and fileVersionId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param configurationUuid the configuration uuid
	 * @param fileVersionId the file version ID
	 * @return the matching am image entry, or <code>null</code> if a matching am image entry could not be found
	 */
	@Override
	public AMImageEntry fetchByC_F(String configurationUuid, long fileVersionId) {
		return fetchByC_F(configurationUuid, fileVersionId, true);
	}

	/**
	 * Returns the am image entry where configurationUuid = &#63; and fileVersionId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param configurationUuid the configuration uuid
	 * @param fileVersionId the file version ID
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the matching am image entry, or <code>null</code> if a matching am image entry could not be found
	 */
	@Override
	public AMImageEntry fetchByC_F(String configurationUuid,
		long fileVersionId, boolean retrieveFromCache) {
		Object[] finderArgs = new Object[] { configurationUuid, fileVersionId };

		Object result = null;

		if (retrieveFromCache) {
			result = finderCache.getResult(FINDER_PATH_FETCH_BY_C_F,
					finderArgs, this);
		}

		if (result instanceof AMImageEntry) {
			AMImageEntry amImageEntry = (AMImageEntry)result;

			if (!Objects.equals(configurationUuid,
						amImageEntry.getConfigurationUuid()) ||
					(fileVersionId != amImageEntry.getFileVersionId())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_SELECT_AMIMAGEENTRY_WHERE);

			boolean bindConfigurationUuid = false;

			if (configurationUuid == null) {
				query.append(_FINDER_COLUMN_C_F_CONFIGURATIONUUID_1);
			}
			else if (configurationUuid.equals("")) {
				query.append(_FINDER_COLUMN_C_F_CONFIGURATIONUUID_3);
			}
			else {
				bindConfigurationUuid = true;

				query.append(_FINDER_COLUMN_C_F_CONFIGURATIONUUID_2);
			}

			query.append(_FINDER_COLUMN_C_F_FILEVERSIONID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindConfigurationUuid) {
					qPos.add(configurationUuid);
				}

				qPos.add(fileVersionId);

				List<AMImageEntry> list = q.list();

				if (list.isEmpty()) {
					finderCache.putResult(FINDER_PATH_FETCH_BY_C_F, finderArgs,
						list);
				}
				else {
					AMImageEntry amImageEntry = list.get(0);

					result = amImageEntry;

					cacheResult(amImageEntry);

					if ((amImageEntry.getConfigurationUuid() == null) ||
							!amImageEntry.getConfigurationUuid()
											 .equals(configurationUuid) ||
							(amImageEntry.getFileVersionId() != fileVersionId)) {
						finderCache.putResult(FINDER_PATH_FETCH_BY_C_F,
							finderArgs, amImageEntry);
					}
				}
			}
			catch (Exception e) {
				finderCache.removeResult(FINDER_PATH_FETCH_BY_C_F, finderArgs);

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
			return (AMImageEntry)result;
		}
	}

	/**
	 * Removes the am image entry where configurationUuid = &#63; and fileVersionId = &#63; from the database.
	 *
	 * @param configurationUuid the configuration uuid
	 * @param fileVersionId the file version ID
	 * @return the am image entry that was removed
	 */
	@Override
	public AMImageEntry removeByC_F(String configurationUuid, long fileVersionId)
		throws NoSuchAMImageEntryException {
		AMImageEntry amImageEntry = findByC_F(configurationUuid, fileVersionId);

		return remove(amImageEntry);
	}

	/**
	 * Returns the number of am image entries where configurationUuid = &#63; and fileVersionId = &#63;.
	 *
	 * @param configurationUuid the configuration uuid
	 * @param fileVersionId the file version ID
	 * @return the number of matching am image entries
	 */
	@Override
	public int countByC_F(String configurationUuid, long fileVersionId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_C_F;

		Object[] finderArgs = new Object[] { configurationUuid, fileVersionId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_AMIMAGEENTRY_WHERE);

			boolean bindConfigurationUuid = false;

			if (configurationUuid == null) {
				query.append(_FINDER_COLUMN_C_F_CONFIGURATIONUUID_1);
			}
			else if (configurationUuid.equals("")) {
				query.append(_FINDER_COLUMN_C_F_CONFIGURATIONUUID_3);
			}
			else {
				bindConfigurationUuid = true;

				query.append(_FINDER_COLUMN_C_F_CONFIGURATIONUUID_2);
			}

			query.append(_FINDER_COLUMN_C_F_FILEVERSIONID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindConfigurationUuid) {
					qPos.add(configurationUuid);
				}

				qPos.add(fileVersionId);

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

	private static final String _FINDER_COLUMN_C_F_CONFIGURATIONUUID_1 = "amImageEntry.configurationUuid IS NULL AND ";
	private static final String _FINDER_COLUMN_C_F_CONFIGURATIONUUID_2 = "amImageEntry.configurationUuid = ? AND ";
	private static final String _FINDER_COLUMN_C_F_CONFIGURATIONUUID_3 = "(amImageEntry.configurationUuid IS NULL OR amImageEntry.configurationUuid = '') AND ";
	private static final String _FINDER_COLUMN_C_F_FILEVERSIONID_2 = "amImageEntry.fileVersionId = ?";

	public AMImageEntryPersistenceImpl() {
		setModelClass(AMImageEntry.class);

		try {
			Field field = BasePersistenceImpl.class.getDeclaredField(
					"_dbColumnNames");

			field.setAccessible(true);

			Map<String, String> dbColumnNames = new HashMap<String, String>();

			dbColumnNames.put("uuid", "uuid_");
			dbColumnNames.put("size", "size_");

			field.set(this, dbColumnNames);
		}
		catch (Exception e) {
			if (_log.isDebugEnabled()) {
				_log.debug(e, e);
			}
		}
	}

	/**
	 * Caches the am image entry in the entity cache if it is enabled.
	 *
	 * @param amImageEntry the am image entry
	 */
	@Override
	public void cacheResult(AMImageEntry amImageEntry) {
		entityCache.putResult(AMImageEntryModelImpl.ENTITY_CACHE_ENABLED,
			AMImageEntryImpl.class, amImageEntry.getPrimaryKey(), amImageEntry);

		finderCache.putResult(FINDER_PATH_FETCH_BY_UUID_G,
			new Object[] { amImageEntry.getUuid(), amImageEntry.getGroupId() },
			amImageEntry);

		finderCache.putResult(FINDER_PATH_FETCH_BY_C_F,
			new Object[] {
				amImageEntry.getConfigurationUuid(),
				amImageEntry.getFileVersionId()
			}, amImageEntry);

		amImageEntry.resetOriginalValues();
	}

	/**
	 * Caches the am image entries in the entity cache if it is enabled.
	 *
	 * @param amImageEntries the am image entries
	 */
	@Override
	public void cacheResult(List<AMImageEntry> amImageEntries) {
		for (AMImageEntry amImageEntry : amImageEntries) {
			if (entityCache.getResult(
						AMImageEntryModelImpl.ENTITY_CACHE_ENABLED,
						AMImageEntryImpl.class, amImageEntry.getPrimaryKey()) == null) {
				cacheResult(amImageEntry);
			}
			else {
				amImageEntry.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all am image entries.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(AMImageEntryImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the am image entry.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(AMImageEntry amImageEntry) {
		entityCache.removeResult(AMImageEntryModelImpl.ENTITY_CACHE_ENABLED,
			AMImageEntryImpl.class, amImageEntry.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache((AMImageEntryModelImpl)amImageEntry, true);
	}

	@Override
	public void clearCache(List<AMImageEntry> amImageEntries) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (AMImageEntry amImageEntry : amImageEntries) {
			entityCache.removeResult(AMImageEntryModelImpl.ENTITY_CACHE_ENABLED,
				AMImageEntryImpl.class, amImageEntry.getPrimaryKey());

			clearUniqueFindersCache((AMImageEntryModelImpl)amImageEntry, true);
		}
	}

	protected void cacheUniqueFindersCache(
		AMImageEntryModelImpl amImageEntryModelImpl) {
		Object[] args = new Object[] {
				amImageEntryModelImpl.getUuid(),
				amImageEntryModelImpl.getGroupId()
			};

		finderCache.putResult(FINDER_PATH_COUNT_BY_UUID_G, args,
			Long.valueOf(1), false);
		finderCache.putResult(FINDER_PATH_FETCH_BY_UUID_G, args,
			amImageEntryModelImpl, false);

		args = new Object[] {
				amImageEntryModelImpl.getConfigurationUuid(),
				amImageEntryModelImpl.getFileVersionId()
			};

		finderCache.putResult(FINDER_PATH_COUNT_BY_C_F, args, Long.valueOf(1),
			false);
		finderCache.putResult(FINDER_PATH_FETCH_BY_C_F, args,
			amImageEntryModelImpl, false);
	}

	protected void clearUniqueFindersCache(
		AMImageEntryModelImpl amImageEntryModelImpl, boolean clearCurrent) {
		if (clearCurrent) {
			Object[] args = new Object[] {
					amImageEntryModelImpl.getUuid(),
					amImageEntryModelImpl.getGroupId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID_G, args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_UUID_G, args);
		}

		if ((amImageEntryModelImpl.getColumnBitmask() &
				FINDER_PATH_FETCH_BY_UUID_G.getColumnBitmask()) != 0) {
			Object[] args = new Object[] {
					amImageEntryModelImpl.getOriginalUuid(),
					amImageEntryModelImpl.getOriginalGroupId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID_G, args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_UUID_G, args);
		}

		if (clearCurrent) {
			Object[] args = new Object[] {
					amImageEntryModelImpl.getConfigurationUuid(),
					amImageEntryModelImpl.getFileVersionId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_C_F, args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_C_F, args);
		}

		if ((amImageEntryModelImpl.getColumnBitmask() &
				FINDER_PATH_FETCH_BY_C_F.getColumnBitmask()) != 0) {
			Object[] args = new Object[] {
					amImageEntryModelImpl.getOriginalConfigurationUuid(),
					amImageEntryModelImpl.getOriginalFileVersionId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_C_F, args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_C_F, args);
		}
	}

	/**
	 * Creates a new am image entry with the primary key. Does not add the am image entry to the database.
	 *
	 * @param amImageEntryId the primary key for the new am image entry
	 * @return the new am image entry
	 */
	@Override
	public AMImageEntry create(long amImageEntryId) {
		AMImageEntry amImageEntry = new AMImageEntryImpl();

		amImageEntry.setNew(true);
		amImageEntry.setPrimaryKey(amImageEntryId);

		String uuid = PortalUUIDUtil.generate();

		amImageEntry.setUuid(uuid);

		amImageEntry.setCompanyId(companyProvider.getCompanyId());

		return amImageEntry;
	}

	/**
	 * Removes the am image entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param amImageEntryId the primary key of the am image entry
	 * @return the am image entry that was removed
	 * @throws NoSuchAMImageEntryException if a am image entry with the primary key could not be found
	 */
	@Override
	public AMImageEntry remove(long amImageEntryId)
		throws NoSuchAMImageEntryException {
		return remove((Serializable)amImageEntryId);
	}

	/**
	 * Removes the am image entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the am image entry
	 * @return the am image entry that was removed
	 * @throws NoSuchAMImageEntryException if a am image entry with the primary key could not be found
	 */
	@Override
	public AMImageEntry remove(Serializable primaryKey)
		throws NoSuchAMImageEntryException {
		Session session = null;

		try {
			session = openSession();

			AMImageEntry amImageEntry = (AMImageEntry)session.get(AMImageEntryImpl.class,
					primaryKey);

			if (amImageEntry == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchAMImageEntryException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(amImageEntry);
		}
		catch (NoSuchAMImageEntryException nsee) {
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
	protected AMImageEntry removeImpl(AMImageEntry amImageEntry) {
		amImageEntry = toUnwrappedModel(amImageEntry);

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(amImageEntry)) {
				amImageEntry = (AMImageEntry)session.get(AMImageEntryImpl.class,
						amImageEntry.getPrimaryKeyObj());
			}

			if (amImageEntry != null) {
				session.delete(amImageEntry);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (amImageEntry != null) {
			clearCache(amImageEntry);
		}

		return amImageEntry;
	}

	@Override
	public AMImageEntry updateImpl(AMImageEntry amImageEntry) {
		amImageEntry = toUnwrappedModel(amImageEntry);

		boolean isNew = amImageEntry.isNew();

		AMImageEntryModelImpl amImageEntryModelImpl = (AMImageEntryModelImpl)amImageEntry;

		if (Validator.isNull(amImageEntry.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			amImageEntry.setUuid(uuid);
		}

		Session session = null;

		try {
			session = openSession();

			if (amImageEntry.isNew()) {
				session.save(amImageEntry);

				amImageEntry.setNew(false);
			}
			else {
				amImageEntry = (AMImageEntry)session.merge(amImageEntry);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (!AMImageEntryModelImpl.COLUMN_BITMASK_ENABLED) {
			finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}
		else
		 if (isNew) {
			Object[] args = new Object[] { amImageEntryModelImpl.getUuid() };

			finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID, args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID,
				args);

			args = new Object[] {
					amImageEntryModelImpl.getUuid(),
					amImageEntryModelImpl.getCompanyId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID_C, args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C,
				args);

			args = new Object[] { amImageEntryModelImpl.getGroupId() };

			finderCache.removeResult(FINDER_PATH_COUNT_BY_GROUPID, args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID,
				args);

			args = new Object[] { amImageEntryModelImpl.getCompanyId() };

			finderCache.removeResult(FINDER_PATH_COUNT_BY_COMPANYID, args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID,
				args);

			args = new Object[] { amImageEntryModelImpl.getConfigurationUuid() };

			finderCache.removeResult(FINDER_PATH_COUNT_BY_CONFIGURATIONUUID,
				args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_CONFIGURATIONUUID,
				args);

			args = new Object[] { amImageEntryModelImpl.getFileVersionId() };

			finderCache.removeResult(FINDER_PATH_COUNT_BY_FILEVERSIONID, args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_FILEVERSIONID,
				args);

			args = new Object[] {
					amImageEntryModelImpl.getCompanyId(),
					amImageEntryModelImpl.getConfigurationUuid()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_C_C, args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_C,
				args);

			finderCache.removeResult(FINDER_PATH_COUNT_ALL, FINDER_ARGS_EMPTY);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL,
				FINDER_ARGS_EMPTY);
		}

		else {
			if ((amImageEntryModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						amImageEntryModelImpl.getOriginalUuid()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID,
					args);

				args = new Object[] { amImageEntryModelImpl.getUuid() };

				finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID,
					args);
			}

			if ((amImageEntryModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						amImageEntryModelImpl.getOriginalUuid(),
						amImageEntryModelImpl.getOriginalCompanyId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID_C, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C,
					args);

				args = new Object[] {
						amImageEntryModelImpl.getUuid(),
						amImageEntryModelImpl.getCompanyId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID_C, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C,
					args);
			}

			if ((amImageEntryModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						amImageEntryModelImpl.getOriginalGroupId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_GROUPID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID,
					args);

				args = new Object[] { amImageEntryModelImpl.getGroupId() };

				finderCache.removeResult(FINDER_PATH_COUNT_BY_GROUPID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID,
					args);
			}

			if ((amImageEntryModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						amImageEntryModelImpl.getOriginalCompanyId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_COMPANYID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID,
					args);

				args = new Object[] { amImageEntryModelImpl.getCompanyId() };

				finderCache.removeResult(FINDER_PATH_COUNT_BY_COMPANYID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID,
					args);
			}

			if ((amImageEntryModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_CONFIGURATIONUUID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						amImageEntryModelImpl.getOriginalConfigurationUuid()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_CONFIGURATIONUUID,
					args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_CONFIGURATIONUUID,
					args);

				args = new Object[] { amImageEntryModelImpl.getConfigurationUuid() };

				finderCache.removeResult(FINDER_PATH_COUNT_BY_CONFIGURATIONUUID,
					args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_CONFIGURATIONUUID,
					args);
			}

			if ((amImageEntryModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_FILEVERSIONID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						amImageEntryModelImpl.getOriginalFileVersionId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_FILEVERSIONID,
					args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_FILEVERSIONID,
					args);

				args = new Object[] { amImageEntryModelImpl.getFileVersionId() };

				finderCache.removeResult(FINDER_PATH_COUNT_BY_FILEVERSIONID,
					args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_FILEVERSIONID,
					args);
			}

			if ((amImageEntryModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_C.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						amImageEntryModelImpl.getOriginalCompanyId(),
						amImageEntryModelImpl.getOriginalConfigurationUuid()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_C_C, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_C,
					args);

				args = new Object[] {
						amImageEntryModelImpl.getCompanyId(),
						amImageEntryModelImpl.getConfigurationUuid()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_C_C, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_C,
					args);
			}
		}

		entityCache.putResult(AMImageEntryModelImpl.ENTITY_CACHE_ENABLED,
			AMImageEntryImpl.class, amImageEntry.getPrimaryKey(), amImageEntry,
			false);

		clearUniqueFindersCache(amImageEntryModelImpl, false);
		cacheUniqueFindersCache(amImageEntryModelImpl);

		amImageEntry.resetOriginalValues();

		return amImageEntry;
	}

	protected AMImageEntry toUnwrappedModel(AMImageEntry amImageEntry) {
		if (amImageEntry instanceof AMImageEntryImpl) {
			return amImageEntry;
		}

		AMImageEntryImpl amImageEntryImpl = new AMImageEntryImpl();

		amImageEntryImpl.setNew(amImageEntry.isNew());
		amImageEntryImpl.setPrimaryKey(amImageEntry.getPrimaryKey());

		amImageEntryImpl.setUuid(amImageEntry.getUuid());
		amImageEntryImpl.setAmImageEntryId(amImageEntry.getAmImageEntryId());
		amImageEntryImpl.setGroupId(amImageEntry.getGroupId());
		amImageEntryImpl.setCompanyId(amImageEntry.getCompanyId());
		amImageEntryImpl.setCreateDate(amImageEntry.getCreateDate());
		amImageEntryImpl.setConfigurationUuid(amImageEntry.getConfigurationUuid());
		amImageEntryImpl.setFileVersionId(amImageEntry.getFileVersionId());
		amImageEntryImpl.setMimeType(amImageEntry.getMimeType());
		amImageEntryImpl.setHeight(amImageEntry.getHeight());
		amImageEntryImpl.setWidth(amImageEntry.getWidth());
		amImageEntryImpl.setSize(amImageEntry.getSize());

		return amImageEntryImpl;
	}

	/**
	 * Returns the am image entry with the primary key or throws a {@link com.liferay.portal.kernel.exception.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the am image entry
	 * @return the am image entry
	 * @throws NoSuchAMImageEntryException if a am image entry with the primary key could not be found
	 */
	@Override
	public AMImageEntry findByPrimaryKey(Serializable primaryKey)
		throws NoSuchAMImageEntryException {
		AMImageEntry amImageEntry = fetchByPrimaryKey(primaryKey);

		if (amImageEntry == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchAMImageEntryException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				primaryKey);
		}

		return amImageEntry;
	}

	/**
	 * Returns the am image entry with the primary key or throws a {@link NoSuchAMImageEntryException} if it could not be found.
	 *
	 * @param amImageEntryId the primary key of the am image entry
	 * @return the am image entry
	 * @throws NoSuchAMImageEntryException if a am image entry with the primary key could not be found
	 */
	@Override
	public AMImageEntry findByPrimaryKey(long amImageEntryId)
		throws NoSuchAMImageEntryException {
		return findByPrimaryKey((Serializable)amImageEntryId);
	}

	/**
	 * Returns the am image entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the am image entry
	 * @return the am image entry, or <code>null</code> if a am image entry with the primary key could not be found
	 */
	@Override
	public AMImageEntry fetchByPrimaryKey(Serializable primaryKey) {
		Serializable serializable = entityCache.getResult(AMImageEntryModelImpl.ENTITY_CACHE_ENABLED,
				AMImageEntryImpl.class, primaryKey);

		if (serializable == nullModel) {
			return null;
		}

		AMImageEntry amImageEntry = (AMImageEntry)serializable;

		if (amImageEntry == null) {
			Session session = null;

			try {
				session = openSession();

				amImageEntry = (AMImageEntry)session.get(AMImageEntryImpl.class,
						primaryKey);

				if (amImageEntry != null) {
					cacheResult(amImageEntry);
				}
				else {
					entityCache.putResult(AMImageEntryModelImpl.ENTITY_CACHE_ENABLED,
						AMImageEntryImpl.class, primaryKey, nullModel);
				}
			}
			catch (Exception e) {
				entityCache.removeResult(AMImageEntryModelImpl.ENTITY_CACHE_ENABLED,
					AMImageEntryImpl.class, primaryKey);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return amImageEntry;
	}

	/**
	 * Returns the am image entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param amImageEntryId the primary key of the am image entry
	 * @return the am image entry, or <code>null</code> if a am image entry with the primary key could not be found
	 */
	@Override
	public AMImageEntry fetchByPrimaryKey(long amImageEntryId) {
		return fetchByPrimaryKey((Serializable)amImageEntryId);
	}

	@Override
	public Map<Serializable, AMImageEntry> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {
		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, AMImageEntry> map = new HashMap<Serializable, AMImageEntry>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			AMImageEntry amImageEntry = fetchByPrimaryKey(primaryKey);

			if (amImageEntry != null) {
				map.put(primaryKey, amImageEntry);
			}

			return map;
		}

		Set<Serializable> uncachedPrimaryKeys = null;

		for (Serializable primaryKey : primaryKeys) {
			Serializable serializable = entityCache.getResult(AMImageEntryModelImpl.ENTITY_CACHE_ENABLED,
					AMImageEntryImpl.class, primaryKey);

			if (serializable != nullModel) {
				if (serializable == null) {
					if (uncachedPrimaryKeys == null) {
						uncachedPrimaryKeys = new HashSet<Serializable>();
					}

					uncachedPrimaryKeys.add(primaryKey);
				}
				else {
					map.put(primaryKey, (AMImageEntry)serializable);
				}
			}
		}

		if (uncachedPrimaryKeys == null) {
			return map;
		}

		StringBundler query = new StringBundler((uncachedPrimaryKeys.size() * 2) +
				1);

		query.append(_SQL_SELECT_AMIMAGEENTRY_WHERE_PKS_IN);

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

			for (AMImageEntry amImageEntry : (List<AMImageEntry>)q.list()) {
				map.put(amImageEntry.getPrimaryKeyObj(), amImageEntry);

				cacheResult(amImageEntry);

				uncachedPrimaryKeys.remove(amImageEntry.getPrimaryKeyObj());
			}

			for (Serializable primaryKey : uncachedPrimaryKeys) {
				entityCache.putResult(AMImageEntryModelImpl.ENTITY_CACHE_ENABLED,
					AMImageEntryImpl.class, primaryKey, nullModel);
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
	 * Returns all the am image entries.
	 *
	 * @return the am image entries
	 */
	@Override
	public List<AMImageEntry> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the am image entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AMImageEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of am image entries
	 * @param end the upper bound of the range of am image entries (not inclusive)
	 * @return the range of am image entries
	 */
	@Override
	public List<AMImageEntry> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the am image entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AMImageEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of am image entries
	 * @param end the upper bound of the range of am image entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of am image entries
	 */
	@Override
	public List<AMImageEntry> findAll(int start, int end,
		OrderByComparator<AMImageEntry> orderByComparator) {
		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the am image entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AMImageEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of am image entries
	 * @param end the upper bound of the range of am image entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of am image entries
	 */
	@Override
	public List<AMImageEntry> findAll(int start, int end,
		OrderByComparator<AMImageEntry> orderByComparator,
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

		List<AMImageEntry> list = null;

		if (retrieveFromCache) {
			list = (List<AMImageEntry>)finderCache.getResult(finderPath,
					finderArgs, this);
		}

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 2));

				query.append(_SQL_SELECT_AMIMAGEENTRY);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_AMIMAGEENTRY;

				if (pagination) {
					sql = sql.concat(AMImageEntryModelImpl.ORDER_BY_JPQL);
				}
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (!pagination) {
					list = (List<AMImageEntry>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<AMImageEntry>)QueryUtil.list(q, getDialect(),
							start, end);
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
	 * Removes all the am image entries from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (AMImageEntry amImageEntry : findAll()) {
			remove(amImageEntry);
		}
	}

	/**
	 * Returns the number of am image entries.
	 *
	 * @return the number of am image entries
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_AMIMAGEENTRY);

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
		return AMImageEntryModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the am image entry persistence.
	 */
	public void afterPropertiesSet() {
	}

	public void destroy() {
		entityCache.removeCache(AMImageEntryImpl.class.getName());
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
	private static final String _SQL_SELECT_AMIMAGEENTRY = "SELECT amImageEntry FROM AMImageEntry amImageEntry";
	private static final String _SQL_SELECT_AMIMAGEENTRY_WHERE_PKS_IN = "SELECT amImageEntry FROM AMImageEntry amImageEntry WHERE amImageEntryId IN (";
	private static final String _SQL_SELECT_AMIMAGEENTRY_WHERE = "SELECT amImageEntry FROM AMImageEntry amImageEntry WHERE ";
	private static final String _SQL_COUNT_AMIMAGEENTRY = "SELECT COUNT(amImageEntry) FROM AMImageEntry amImageEntry";
	private static final String _SQL_COUNT_AMIMAGEENTRY_WHERE = "SELECT COUNT(amImageEntry) FROM AMImageEntry amImageEntry WHERE ";
	private static final String _ORDER_BY_ENTITY_ALIAS = "amImageEntry.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No AMImageEntry exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No AMImageEntry exists with the key {";
	private static final Log _log = LogFactoryUtil.getLog(AMImageEntryPersistenceImpl.class);
	private static final Set<String> _badColumnNames = SetUtil.fromArray(new String[] {
				"uuid", "size"
			});
}