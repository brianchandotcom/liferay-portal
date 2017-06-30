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

package com.liferay.portlet.documentlibrary.service.persistence.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.document.library.kernel.exception.NoSuchFileEntryException;
import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.service.persistence.DLFileEntryPersistence;

import com.liferay.portal.kernel.bean.BeanReference;
import com.liferay.portal.kernel.dao.orm.EntityCache;
import com.liferay.portal.kernel.dao.orm.EntityCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderCache;
import com.liferay.portal.kernel.dao.orm.FinderCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.SQLQuery;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.permission.InlineSQLHelperUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.service.persistence.CompanyProvider;
import com.liferay.portal.kernel.service.persistence.CompanyProviderWrapper;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ReflectionUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;

import com.liferay.portlet.documentlibrary.model.impl.DLFileEntryImpl;
import com.liferay.portlet.documentlibrary.model.impl.DLFileEntryModelImpl;

import java.io.Serializable;

import java.lang.reflect.Field;

import java.util.Arrays;
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
 * The persistence implementation for the document library file entry service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see DLFileEntryPersistence
 * @see com.liferay.document.library.kernel.service.persistence.DLFileEntryUtil
 * @generated
 */
@ProviderType
public class DLFileEntryPersistenceImpl extends BasePersistenceImpl<DLFileEntry>
	implements DLFileEntryPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link DLFileEntryUtil} to access the document library file entry persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = DLFileEntryImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(DLFileEntryModelImpl.ENTITY_CACHE_ENABLED,
			DLFileEntryModelImpl.FINDER_CACHE_ENABLED, DLFileEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(DLFileEntryModelImpl.ENTITY_CACHE_ENABLED,
			DLFileEntryModelImpl.FINDER_CACHE_ENABLED, DLFileEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(DLFileEntryModelImpl.ENTITY_CACHE_ENABLED,
			DLFileEntryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_UUID = new FinderPath(DLFileEntryModelImpl.ENTITY_CACHE_ENABLED,
			DLFileEntryModelImpl.FINDER_CACHE_ENABLED, DLFileEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUuid",
			new String[] {
				String.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID = new FinderPath(DLFileEntryModelImpl.ENTITY_CACHE_ENABLED,
			DLFileEntryModelImpl.FINDER_CACHE_ENABLED, DLFileEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid",
			new String[] { String.class.getName() },
			DLFileEntryModelImpl.UUID_COLUMN_BITMASK |
			DLFileEntryModelImpl.FOLDERID_COLUMN_BITMASK |
			DLFileEntryModelImpl.NAME_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID = new FinderPath(DLFileEntryModelImpl.ENTITY_CACHE_ENABLED,
			DLFileEntryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUuid",
			new String[] { String.class.getName() });

	/**
	 * Returns all the document library file entries where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching document library file entries
	 */
	@Override
	public List<DLFileEntry> findByUuid(String uuid) {
		return findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the document library file entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFileEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of document library file entries
	 * @param end the upper bound of the range of document library file entries (not inclusive)
	 * @return the range of matching document library file entries
	 */
	@Override
	public List<DLFileEntry> findByUuid(String uuid, int start, int end) {
		return findByUuid(uuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the document library file entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFileEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of document library file entries
	 * @param end the upper bound of the range of document library file entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching document library file entries
	 */
	@Override
	public List<DLFileEntry> findByUuid(String uuid, int start, int end,
		OrderByComparator<DLFileEntry> orderByComparator) {
		return findByUuid(uuid, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the document library file entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFileEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of document library file entries
	 * @param end the upper bound of the range of document library file entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching document library file entries
	 */
	@Override
	public List<DLFileEntry> findByUuid(String uuid, int start, int end,
		OrderByComparator<DLFileEntry> orderByComparator,
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

		List<DLFileEntry> list = null;

		if (retrieveFromCache) {
			list = (List<DLFileEntry>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (DLFileEntry dlFileEntry : list) {
					if (!Objects.equals(uuid, dlFileEntry.getUuid())) {
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

			query.append(_SQL_SELECT_DLFILEENTRY_WHERE);

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
				query.append(DLFileEntryModelImpl.ORDER_BY_JPQL);
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
					list = (List<DLFileEntry>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<DLFileEntry>)QueryUtil.list(q, getDialect(),
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
	 * Returns the first document library file entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching document library file entry
	 * @throws NoSuchFileEntryException if a matching document library file entry could not be found
	 */
	@Override
	public DLFileEntry findByUuid_First(String uuid,
		OrderByComparator<DLFileEntry> orderByComparator)
		throws NoSuchFileEntryException {
		DLFileEntry dlFileEntry = fetchByUuid_First(uuid, orderByComparator);

		if (dlFileEntry != null) {
			return dlFileEntry;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchFileEntryException(msg.toString());
	}

	/**
	 * Returns the first document library file entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching document library file entry, or <code>null</code> if a matching document library file entry could not be found
	 */
	@Override
	public DLFileEntry fetchByUuid_First(String uuid,
		OrderByComparator<DLFileEntry> orderByComparator) {
		List<DLFileEntry> list = findByUuid(uuid, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last document library file entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching document library file entry
	 * @throws NoSuchFileEntryException if a matching document library file entry could not be found
	 */
	@Override
	public DLFileEntry findByUuid_Last(String uuid,
		OrderByComparator<DLFileEntry> orderByComparator)
		throws NoSuchFileEntryException {
		DLFileEntry dlFileEntry = fetchByUuid_Last(uuid, orderByComparator);

		if (dlFileEntry != null) {
			return dlFileEntry;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchFileEntryException(msg.toString());
	}

	/**
	 * Returns the last document library file entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching document library file entry, or <code>null</code> if a matching document library file entry could not be found
	 */
	@Override
	public DLFileEntry fetchByUuid_Last(String uuid,
		OrderByComparator<DLFileEntry> orderByComparator) {
		int count = countByUuid(uuid);

		if (count == 0) {
			return null;
		}

		List<DLFileEntry> list = findByUuid(uuid, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the document library file entries before and after the current document library file entry in the ordered set where uuid = &#63;.
	 *
	 * @param fileEntryId the primary key of the current document library file entry
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next document library file entry
	 * @throws NoSuchFileEntryException if a document library file entry with the primary key could not be found
	 */
	@Override
	public DLFileEntry[] findByUuid_PrevAndNext(long fileEntryId, String uuid,
		OrderByComparator<DLFileEntry> orderByComparator)
		throws NoSuchFileEntryException {
		DLFileEntry dlFileEntry = findByPrimaryKey(fileEntryId);

		Session session = null;

		try {
			session = openSession();

			DLFileEntry[] array = new DLFileEntryImpl[3];

			array[0] = getByUuid_PrevAndNext(session, dlFileEntry, uuid,
					orderByComparator, true);

			array[1] = dlFileEntry;

			array[2] = getByUuid_PrevAndNext(session, dlFileEntry, uuid,
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

	protected DLFileEntry getByUuid_PrevAndNext(Session session,
		DLFileEntry dlFileEntry, String uuid,
		OrderByComparator<DLFileEntry> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(4 +
					(orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_DLFILEENTRY_WHERE);

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
			query.append(DLFileEntryModelImpl.ORDER_BY_JPQL);
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
			Object[] values = orderByComparator.getOrderByConditionValues(dlFileEntry);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<DLFileEntry> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the document library file entries where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	@Override
	public void removeByUuid(String uuid) {
		for (DLFileEntry dlFileEntry : findByUuid(uuid, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS, null)) {
			remove(dlFileEntry);
		}
	}

	/**
	 * Returns the number of document library file entries where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching document library file entries
	 */
	@Override
	public int countByUuid(String uuid) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_UUID;

		Object[] finderArgs = new Object[] { uuid };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_DLFILEENTRY_WHERE);

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

	private static final String _FINDER_COLUMN_UUID_UUID_1 = "dlFileEntry.uuid IS NULL";
	private static final String _FINDER_COLUMN_UUID_UUID_2 = "dlFileEntry.uuid = ?";
	private static final String _FINDER_COLUMN_UUID_UUID_3 = "(dlFileEntry.uuid IS NULL OR dlFileEntry.uuid = '')";
	public static final FinderPath FINDER_PATH_FETCH_BY_UUID_G = new FinderPath(DLFileEntryModelImpl.ENTITY_CACHE_ENABLED,
			DLFileEntryModelImpl.FINDER_CACHE_ENABLED, DLFileEntryImpl.class,
			FINDER_CLASS_NAME_ENTITY, "fetchByUUID_G",
			new String[] { String.class.getName(), Long.class.getName() },
			DLFileEntryModelImpl.UUID_COLUMN_BITMASK |
			DLFileEntryModelImpl.GROUPID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID_G = new FinderPath(DLFileEntryModelImpl.ENTITY_CACHE_ENABLED,
			DLFileEntryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUUID_G",
			new String[] { String.class.getName(), Long.class.getName() });

	/**
	 * Returns the document library file entry where uuid = &#63; and groupId = &#63; or throws a {@link NoSuchFileEntryException} if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching document library file entry
	 * @throws NoSuchFileEntryException if a matching document library file entry could not be found
	 */
	@Override
	public DLFileEntry findByUUID_G(String uuid, long groupId)
		throws NoSuchFileEntryException {
		DLFileEntry dlFileEntry = fetchByUUID_G(uuid, groupId);

		if (dlFileEntry == null) {
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

			throw new NoSuchFileEntryException(msg.toString());
		}

		return dlFileEntry;
	}

	/**
	 * Returns the document library file entry where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching document library file entry, or <code>null</code> if a matching document library file entry could not be found
	 */
	@Override
	public DLFileEntry fetchByUUID_G(String uuid, long groupId) {
		return fetchByUUID_G(uuid, groupId, true);
	}

	/**
	 * Returns the document library file entry where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the matching document library file entry, or <code>null</code> if a matching document library file entry could not be found
	 */
	@Override
	public DLFileEntry fetchByUUID_G(String uuid, long groupId,
		boolean retrieveFromCache) {
		Object[] finderArgs = new Object[] { uuid, groupId };

		Object result = null;

		if (retrieveFromCache) {
			result = finderCache.getResult(FINDER_PATH_FETCH_BY_UUID_G,
					finderArgs, this);
		}

		if (result instanceof DLFileEntry) {
			DLFileEntry dlFileEntry = (DLFileEntry)result;

			if (!Objects.equals(uuid, dlFileEntry.getUuid()) ||
					(groupId != dlFileEntry.getGroupId())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_SELECT_DLFILEENTRY_WHERE);

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

				List<DLFileEntry> list = q.list();

				if (list.isEmpty()) {
					finderCache.putResult(FINDER_PATH_FETCH_BY_UUID_G,
						finderArgs, list);
				}
				else {
					DLFileEntry dlFileEntry = list.get(0);

					result = dlFileEntry;

					cacheResult(dlFileEntry);

					if ((dlFileEntry.getUuid() == null) ||
							!dlFileEntry.getUuid().equals(uuid) ||
							(dlFileEntry.getGroupId() != groupId)) {
						finderCache.putResult(FINDER_PATH_FETCH_BY_UUID_G,
							finderArgs, dlFileEntry);
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
			return (DLFileEntry)result;
		}
	}

	/**
	 * Removes the document library file entry where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the document library file entry that was removed
	 */
	@Override
	public DLFileEntry removeByUUID_G(String uuid, long groupId)
		throws NoSuchFileEntryException {
		DLFileEntry dlFileEntry = findByUUID_G(uuid, groupId);

		return remove(dlFileEntry);
	}

	/**
	 * Returns the number of document library file entries where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching document library file entries
	 */
	@Override
	public int countByUUID_G(String uuid, long groupId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_UUID_G;

		Object[] finderArgs = new Object[] { uuid, groupId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_DLFILEENTRY_WHERE);

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

	private static final String _FINDER_COLUMN_UUID_G_UUID_1 = "dlFileEntry.uuid IS NULL AND ";
	private static final String _FINDER_COLUMN_UUID_G_UUID_2 = "dlFileEntry.uuid = ? AND ";
	private static final String _FINDER_COLUMN_UUID_G_UUID_3 = "(dlFileEntry.uuid IS NULL OR dlFileEntry.uuid = '') AND ";
	private static final String _FINDER_COLUMN_UUID_G_GROUPID_2 = "dlFileEntry.groupId = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_UUID_C = new FinderPath(DLFileEntryModelImpl.ENTITY_CACHE_ENABLED,
			DLFileEntryModelImpl.FINDER_CACHE_ENABLED, DLFileEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUuid_C",
			new String[] {
				String.class.getName(), Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C =
		new FinderPath(DLFileEntryModelImpl.ENTITY_CACHE_ENABLED,
			DLFileEntryModelImpl.FINDER_CACHE_ENABLED, DLFileEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid_C",
			new String[] { String.class.getName(), Long.class.getName() },
			DLFileEntryModelImpl.UUID_COLUMN_BITMASK |
			DLFileEntryModelImpl.COMPANYID_COLUMN_BITMASK |
			DLFileEntryModelImpl.FOLDERID_COLUMN_BITMASK |
			DLFileEntryModelImpl.NAME_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID_C = new FinderPath(DLFileEntryModelImpl.ENTITY_CACHE_ENABLED,
			DLFileEntryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUuid_C",
			new String[] { String.class.getName(), Long.class.getName() });

	/**
	 * Returns all the document library file entries where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching document library file entries
	 */
	@Override
	public List<DLFileEntry> findByUuid_C(String uuid, long companyId) {
		return findByUuid_C(uuid, companyId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the document library file entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFileEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of document library file entries
	 * @param end the upper bound of the range of document library file entries (not inclusive)
	 * @return the range of matching document library file entries
	 */
	@Override
	public List<DLFileEntry> findByUuid_C(String uuid, long companyId,
		int start, int end) {
		return findByUuid_C(uuid, companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the document library file entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFileEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of document library file entries
	 * @param end the upper bound of the range of document library file entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching document library file entries
	 */
	@Override
	public List<DLFileEntry> findByUuid_C(String uuid, long companyId,
		int start, int end, OrderByComparator<DLFileEntry> orderByComparator) {
		return findByUuid_C(uuid, companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the document library file entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFileEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of document library file entries
	 * @param end the upper bound of the range of document library file entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching document library file entries
	 */
	@Override
	public List<DLFileEntry> findByUuid_C(String uuid, long companyId,
		int start, int end, OrderByComparator<DLFileEntry> orderByComparator,
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

		List<DLFileEntry> list = null;

		if (retrieveFromCache) {
			list = (List<DLFileEntry>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (DLFileEntry dlFileEntry : list) {
					if (!Objects.equals(uuid, dlFileEntry.getUuid()) ||
							(companyId != dlFileEntry.getCompanyId())) {
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

			query.append(_SQL_SELECT_DLFILEENTRY_WHERE);

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
				query.append(DLFileEntryModelImpl.ORDER_BY_JPQL);
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
					list = (List<DLFileEntry>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<DLFileEntry>)QueryUtil.list(q, getDialect(),
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
	 * Returns the first document library file entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching document library file entry
	 * @throws NoSuchFileEntryException if a matching document library file entry could not be found
	 */
	@Override
	public DLFileEntry findByUuid_C_First(String uuid, long companyId,
		OrderByComparator<DLFileEntry> orderByComparator)
		throws NoSuchFileEntryException {
		DLFileEntry dlFileEntry = fetchByUuid_C_First(uuid, companyId,
				orderByComparator);

		if (dlFileEntry != null) {
			return dlFileEntry;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(", companyId=");
		msg.append(companyId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchFileEntryException(msg.toString());
	}

	/**
	 * Returns the first document library file entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching document library file entry, or <code>null</code> if a matching document library file entry could not be found
	 */
	@Override
	public DLFileEntry fetchByUuid_C_First(String uuid, long companyId,
		OrderByComparator<DLFileEntry> orderByComparator) {
		List<DLFileEntry> list = findByUuid_C(uuid, companyId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last document library file entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching document library file entry
	 * @throws NoSuchFileEntryException if a matching document library file entry could not be found
	 */
	@Override
	public DLFileEntry findByUuid_C_Last(String uuid, long companyId,
		OrderByComparator<DLFileEntry> orderByComparator)
		throws NoSuchFileEntryException {
		DLFileEntry dlFileEntry = fetchByUuid_C_Last(uuid, companyId,
				orderByComparator);

		if (dlFileEntry != null) {
			return dlFileEntry;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(", companyId=");
		msg.append(companyId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchFileEntryException(msg.toString());
	}

	/**
	 * Returns the last document library file entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching document library file entry, or <code>null</code> if a matching document library file entry could not be found
	 */
	@Override
	public DLFileEntry fetchByUuid_C_Last(String uuid, long companyId,
		OrderByComparator<DLFileEntry> orderByComparator) {
		int count = countByUuid_C(uuid, companyId);

		if (count == 0) {
			return null;
		}

		List<DLFileEntry> list = findByUuid_C(uuid, companyId, count - 1,
				count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the document library file entries before and after the current document library file entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param fileEntryId the primary key of the current document library file entry
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next document library file entry
	 * @throws NoSuchFileEntryException if a document library file entry with the primary key could not be found
	 */
	@Override
	public DLFileEntry[] findByUuid_C_PrevAndNext(long fileEntryId,
		String uuid, long companyId,
		OrderByComparator<DLFileEntry> orderByComparator)
		throws NoSuchFileEntryException {
		DLFileEntry dlFileEntry = findByPrimaryKey(fileEntryId);

		Session session = null;

		try {
			session = openSession();

			DLFileEntry[] array = new DLFileEntryImpl[3];

			array[0] = getByUuid_C_PrevAndNext(session, dlFileEntry, uuid,
					companyId, orderByComparator, true);

			array[1] = dlFileEntry;

			array[2] = getByUuid_C_PrevAndNext(session, dlFileEntry, uuid,
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

	protected DLFileEntry getByUuid_C_PrevAndNext(Session session,
		DLFileEntry dlFileEntry, String uuid, long companyId,
		OrderByComparator<DLFileEntry> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(5 +
					(orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(4);
		}

		query.append(_SQL_SELECT_DLFILEENTRY_WHERE);

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
			query.append(DLFileEntryModelImpl.ORDER_BY_JPQL);
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
			Object[] values = orderByComparator.getOrderByConditionValues(dlFileEntry);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<DLFileEntry> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the document library file entries where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	@Override
	public void removeByUuid_C(String uuid, long companyId) {
		for (DLFileEntry dlFileEntry : findByUuid_C(uuid, companyId,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(dlFileEntry);
		}
	}

	/**
	 * Returns the number of document library file entries where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching document library file entries
	 */
	@Override
	public int countByUuid_C(String uuid, long companyId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_UUID_C;

		Object[] finderArgs = new Object[] { uuid, companyId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_DLFILEENTRY_WHERE);

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

	private static final String _FINDER_COLUMN_UUID_C_UUID_1 = "dlFileEntry.uuid IS NULL AND ";
	private static final String _FINDER_COLUMN_UUID_C_UUID_2 = "dlFileEntry.uuid = ? AND ";
	private static final String _FINDER_COLUMN_UUID_C_UUID_3 = "(dlFileEntry.uuid IS NULL OR dlFileEntry.uuid = '') AND ";
	private static final String _FINDER_COLUMN_UUID_C_COMPANYID_2 = "dlFileEntry.companyId = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_GROUPID = new FinderPath(DLFileEntryModelImpl.ENTITY_CACHE_ENABLED,
			DLFileEntryModelImpl.FINDER_CACHE_ENABLED, DLFileEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByGroupId",
			new String[] {
				Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID =
		new FinderPath(DLFileEntryModelImpl.ENTITY_CACHE_ENABLED,
			DLFileEntryModelImpl.FINDER_CACHE_ENABLED, DLFileEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByGroupId",
			new String[] { Long.class.getName() },
			DLFileEntryModelImpl.GROUPID_COLUMN_BITMASK |
			DLFileEntryModelImpl.FOLDERID_COLUMN_BITMASK |
			DLFileEntryModelImpl.NAME_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_GROUPID = new FinderPath(DLFileEntryModelImpl.ENTITY_CACHE_ENABLED,
			DLFileEntryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByGroupId",
			new String[] { Long.class.getName() });

	/**
	 * Returns all the document library file entries where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching document library file entries
	 */
	@Override
	public List<DLFileEntry> findByGroupId(long groupId) {
		return findByGroupId(groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the document library file entries where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFileEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of document library file entries
	 * @param end the upper bound of the range of document library file entries (not inclusive)
	 * @return the range of matching document library file entries
	 */
	@Override
	public List<DLFileEntry> findByGroupId(long groupId, int start, int end) {
		return findByGroupId(groupId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the document library file entries where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFileEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of document library file entries
	 * @param end the upper bound of the range of document library file entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching document library file entries
	 */
	@Override
	public List<DLFileEntry> findByGroupId(long groupId, int start, int end,
		OrderByComparator<DLFileEntry> orderByComparator) {
		return findByGroupId(groupId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the document library file entries where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFileEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of document library file entries
	 * @param end the upper bound of the range of document library file entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching document library file entries
	 */
	@Override
	public List<DLFileEntry> findByGroupId(long groupId, int start, int end,
		OrderByComparator<DLFileEntry> orderByComparator,
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

		List<DLFileEntry> list = null;

		if (retrieveFromCache) {
			list = (List<DLFileEntry>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (DLFileEntry dlFileEntry : list) {
					if ((groupId != dlFileEntry.getGroupId())) {
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

			query.append(_SQL_SELECT_DLFILEENTRY_WHERE);

			query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(DLFileEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (!pagination) {
					list = (List<DLFileEntry>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<DLFileEntry>)QueryUtil.list(q, getDialect(),
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
	 * Returns the first document library file entry in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching document library file entry
	 * @throws NoSuchFileEntryException if a matching document library file entry could not be found
	 */
	@Override
	public DLFileEntry findByGroupId_First(long groupId,
		OrderByComparator<DLFileEntry> orderByComparator)
		throws NoSuchFileEntryException {
		DLFileEntry dlFileEntry = fetchByGroupId_First(groupId,
				orderByComparator);

		if (dlFileEntry != null) {
			return dlFileEntry;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchFileEntryException(msg.toString());
	}

	/**
	 * Returns the first document library file entry in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching document library file entry, or <code>null</code> if a matching document library file entry could not be found
	 */
	@Override
	public DLFileEntry fetchByGroupId_First(long groupId,
		OrderByComparator<DLFileEntry> orderByComparator) {
		List<DLFileEntry> list = findByGroupId(groupId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last document library file entry in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching document library file entry
	 * @throws NoSuchFileEntryException if a matching document library file entry could not be found
	 */
	@Override
	public DLFileEntry findByGroupId_Last(long groupId,
		OrderByComparator<DLFileEntry> orderByComparator)
		throws NoSuchFileEntryException {
		DLFileEntry dlFileEntry = fetchByGroupId_Last(groupId, orderByComparator);

		if (dlFileEntry != null) {
			return dlFileEntry;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchFileEntryException(msg.toString());
	}

	/**
	 * Returns the last document library file entry in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching document library file entry, or <code>null</code> if a matching document library file entry could not be found
	 */
	@Override
	public DLFileEntry fetchByGroupId_Last(long groupId,
		OrderByComparator<DLFileEntry> orderByComparator) {
		int count = countByGroupId(groupId);

		if (count == 0) {
			return null;
		}

		List<DLFileEntry> list = findByGroupId(groupId, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the document library file entries before and after the current document library file entry in the ordered set where groupId = &#63;.
	 *
	 * @param fileEntryId the primary key of the current document library file entry
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next document library file entry
	 * @throws NoSuchFileEntryException if a document library file entry with the primary key could not be found
	 */
	@Override
	public DLFileEntry[] findByGroupId_PrevAndNext(long fileEntryId,
		long groupId, OrderByComparator<DLFileEntry> orderByComparator)
		throws NoSuchFileEntryException {
		DLFileEntry dlFileEntry = findByPrimaryKey(fileEntryId);

		Session session = null;

		try {
			session = openSession();

			DLFileEntry[] array = new DLFileEntryImpl[3];

			array[0] = getByGroupId_PrevAndNext(session, dlFileEntry, groupId,
					orderByComparator, true);

			array[1] = dlFileEntry;

			array[2] = getByGroupId_PrevAndNext(session, dlFileEntry, groupId,
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

	protected DLFileEntry getByGroupId_PrevAndNext(Session session,
		DLFileEntry dlFileEntry, long groupId,
		OrderByComparator<DLFileEntry> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(4 +
					(orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_DLFILEENTRY_WHERE);

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
			query.append(DLFileEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(dlFileEntry);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<DLFileEntry> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the document library file entries that the user has permission to view where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching document library file entries that the user has permission to view
	 */
	@Override
	public List<DLFileEntry> filterFindByGroupId(long groupId) {
		return filterFindByGroupId(groupId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the document library file entries that the user has permission to view where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFileEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of document library file entries
	 * @param end the upper bound of the range of document library file entries (not inclusive)
	 * @return the range of matching document library file entries that the user has permission to view
	 */
	@Override
	public List<DLFileEntry> filterFindByGroupId(long groupId, int start,
		int end) {
		return filterFindByGroupId(groupId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the document library file entries that the user has permissions to view where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFileEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of document library file entries
	 * @param end the upper bound of the range of document library file entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching document library file entries that the user has permission to view
	 */
	@Override
	public List<DLFileEntry> filterFindByGroupId(long groupId, int start,
		int end, OrderByComparator<DLFileEntry> orderByComparator) {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByGroupId(groupId, start, end, orderByComparator);
		}

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(3 +
					(orderByComparator.getOrderByFields().length * 2));
		}
		else {
			query = new StringBundler(4);
		}

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_DLFILEENTRY_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_DLFILEENTRY_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_DLFILEENTRY_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			if (getDB().isSupportsInlineDistinct()) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator, true);
			}
			else {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_TABLE,
					orderByComparator, true);
			}
		}
		else {
			if (getDB().isSupportsInlineDistinct()) {
				query.append(DLFileEntryModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(DLFileEntryModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				DLFileEntry.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				q.addEntity(_FILTER_ENTITY_ALIAS, DLFileEntryImpl.class);
			}
			else {
				q.addEntity(_FILTER_ENTITY_TABLE, DLFileEntryImpl.class);
			}

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			return (List<DLFileEntry>)QueryUtil.list(q, getDialect(), start, end);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Returns the document library file entries before and after the current document library file entry in the ordered set of document library file entries that the user has permission to view where groupId = &#63;.
	 *
	 * @param fileEntryId the primary key of the current document library file entry
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next document library file entry
	 * @throws NoSuchFileEntryException if a document library file entry with the primary key could not be found
	 */
	@Override
	public DLFileEntry[] filterFindByGroupId_PrevAndNext(long fileEntryId,
		long groupId, OrderByComparator<DLFileEntry> orderByComparator)
		throws NoSuchFileEntryException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByGroupId_PrevAndNext(fileEntryId, groupId,
				orderByComparator);
		}

		DLFileEntry dlFileEntry = findByPrimaryKey(fileEntryId);

		Session session = null;

		try {
			session = openSession();

			DLFileEntry[] array = new DLFileEntryImpl[3];

			array[0] = filterGetByGroupId_PrevAndNext(session, dlFileEntry,
					groupId, orderByComparator, true);

			array[1] = dlFileEntry;

			array[2] = filterGetByGroupId_PrevAndNext(session, dlFileEntry,
					groupId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected DLFileEntry filterGetByGroupId_PrevAndNext(Session session,
		DLFileEntry dlFileEntry, long groupId,
		OrderByComparator<DLFileEntry> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(5 +
					(orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(4);
		}

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_DLFILEENTRY_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_DLFILEENTRY_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_DLFILEENTRY_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			String[] orderByConditionFields = orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				query.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				if (getDB().isSupportsInlineDistinct()) {
					query.append(_ORDER_BY_ENTITY_ALIAS);
				}
				else {
					query.append(_ORDER_BY_ENTITY_TABLE);
				}

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
				if (getDB().isSupportsInlineDistinct()) {
					query.append(_ORDER_BY_ENTITY_ALIAS);
				}
				else {
					query.append(_ORDER_BY_ENTITY_TABLE);
				}

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
			if (getDB().isSupportsInlineDistinct()) {
				query.append(DLFileEntryModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(DLFileEntryModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				DLFileEntry.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		SQLQuery q = session.createSynchronizedSQLQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		if (getDB().isSupportsInlineDistinct()) {
			q.addEntity(_FILTER_ENTITY_ALIAS, DLFileEntryImpl.class);
		}
		else {
			q.addEntity(_FILTER_ENTITY_TABLE, DLFileEntryImpl.class);
		}

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(dlFileEntry);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<DLFileEntry> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the document library file entries where groupId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 */
	@Override
	public void removeByGroupId(long groupId) {
		for (DLFileEntry dlFileEntry : findByGroupId(groupId,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(dlFileEntry);
		}
	}

	/**
	 * Returns the number of document library file entries where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching document library file entries
	 */
	@Override
	public int countByGroupId(long groupId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_GROUPID;

		Object[] finderArgs = new Object[] { groupId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_DLFILEENTRY_WHERE);

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
	 * Returns the number of document library file entries that the user has permission to view where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching document library file entries that the user has permission to view
	 */
	@Override
	public int filterCountByGroupId(long groupId) {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByGroupId(groupId);
		}

		StringBundler query = new StringBundler(2);

		query.append(_FILTER_SQL_COUNT_DLFILEENTRY_WHERE);

		query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				DLFileEntry.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME,
				com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			Long count = (Long)q.uniqueResult();

			return count.intValue();
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	private static final String _FINDER_COLUMN_GROUPID_GROUPID_2 = "dlFileEntry.groupId = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_COMPANYID =
		new FinderPath(DLFileEntryModelImpl.ENTITY_CACHE_ENABLED,
			DLFileEntryModelImpl.FINDER_CACHE_ENABLED, DLFileEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByCompanyId",
			new String[] {
				Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID =
		new FinderPath(DLFileEntryModelImpl.ENTITY_CACHE_ENABLED,
			DLFileEntryModelImpl.FINDER_CACHE_ENABLED, DLFileEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByCompanyId",
			new String[] { Long.class.getName() },
			DLFileEntryModelImpl.COMPANYID_COLUMN_BITMASK |
			DLFileEntryModelImpl.FOLDERID_COLUMN_BITMASK |
			DLFileEntryModelImpl.NAME_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_COMPANYID = new FinderPath(DLFileEntryModelImpl.ENTITY_CACHE_ENABLED,
			DLFileEntryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByCompanyId",
			new String[] { Long.class.getName() });

	/**
	 * Returns all the document library file entries where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching document library file entries
	 */
	@Override
	public List<DLFileEntry> findByCompanyId(long companyId) {
		return findByCompanyId(companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the document library file entries where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFileEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of document library file entries
	 * @param end the upper bound of the range of document library file entries (not inclusive)
	 * @return the range of matching document library file entries
	 */
	@Override
	public List<DLFileEntry> findByCompanyId(long companyId, int start, int end) {
		return findByCompanyId(companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the document library file entries where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFileEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of document library file entries
	 * @param end the upper bound of the range of document library file entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching document library file entries
	 */
	@Override
	public List<DLFileEntry> findByCompanyId(long companyId, int start,
		int end, OrderByComparator<DLFileEntry> orderByComparator) {
		return findByCompanyId(companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the document library file entries where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFileEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of document library file entries
	 * @param end the upper bound of the range of document library file entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching document library file entries
	 */
	@Override
	public List<DLFileEntry> findByCompanyId(long companyId, int start,
		int end, OrderByComparator<DLFileEntry> orderByComparator,
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

		List<DLFileEntry> list = null;

		if (retrieveFromCache) {
			list = (List<DLFileEntry>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (DLFileEntry dlFileEntry : list) {
					if ((companyId != dlFileEntry.getCompanyId())) {
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

			query.append(_SQL_SELECT_DLFILEENTRY_WHERE);

			query.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(DLFileEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				if (!pagination) {
					list = (List<DLFileEntry>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<DLFileEntry>)QueryUtil.list(q, getDialect(),
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
	 * Returns the first document library file entry in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching document library file entry
	 * @throws NoSuchFileEntryException if a matching document library file entry could not be found
	 */
	@Override
	public DLFileEntry findByCompanyId_First(long companyId,
		OrderByComparator<DLFileEntry> orderByComparator)
		throws NoSuchFileEntryException {
		DLFileEntry dlFileEntry = fetchByCompanyId_First(companyId,
				orderByComparator);

		if (dlFileEntry != null) {
			return dlFileEntry;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("companyId=");
		msg.append(companyId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchFileEntryException(msg.toString());
	}

	/**
	 * Returns the first document library file entry in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching document library file entry, or <code>null</code> if a matching document library file entry could not be found
	 */
	@Override
	public DLFileEntry fetchByCompanyId_First(long companyId,
		OrderByComparator<DLFileEntry> orderByComparator) {
		List<DLFileEntry> list = findByCompanyId(companyId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last document library file entry in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching document library file entry
	 * @throws NoSuchFileEntryException if a matching document library file entry could not be found
	 */
	@Override
	public DLFileEntry findByCompanyId_Last(long companyId,
		OrderByComparator<DLFileEntry> orderByComparator)
		throws NoSuchFileEntryException {
		DLFileEntry dlFileEntry = fetchByCompanyId_Last(companyId,
				orderByComparator);

		if (dlFileEntry != null) {
			return dlFileEntry;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("companyId=");
		msg.append(companyId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchFileEntryException(msg.toString());
	}

	/**
	 * Returns the last document library file entry in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching document library file entry, or <code>null</code> if a matching document library file entry could not be found
	 */
	@Override
	public DLFileEntry fetchByCompanyId_Last(long companyId,
		OrderByComparator<DLFileEntry> orderByComparator) {
		int count = countByCompanyId(companyId);

		if (count == 0) {
			return null;
		}

		List<DLFileEntry> list = findByCompanyId(companyId, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the document library file entries before and after the current document library file entry in the ordered set where companyId = &#63;.
	 *
	 * @param fileEntryId the primary key of the current document library file entry
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next document library file entry
	 * @throws NoSuchFileEntryException if a document library file entry with the primary key could not be found
	 */
	@Override
	public DLFileEntry[] findByCompanyId_PrevAndNext(long fileEntryId,
		long companyId, OrderByComparator<DLFileEntry> orderByComparator)
		throws NoSuchFileEntryException {
		DLFileEntry dlFileEntry = findByPrimaryKey(fileEntryId);

		Session session = null;

		try {
			session = openSession();

			DLFileEntry[] array = new DLFileEntryImpl[3];

			array[0] = getByCompanyId_PrevAndNext(session, dlFileEntry,
					companyId, orderByComparator, true);

			array[1] = dlFileEntry;

			array[2] = getByCompanyId_PrevAndNext(session, dlFileEntry,
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

	protected DLFileEntry getByCompanyId_PrevAndNext(Session session,
		DLFileEntry dlFileEntry, long companyId,
		OrderByComparator<DLFileEntry> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(4 +
					(orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_DLFILEENTRY_WHERE);

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
			query.append(DLFileEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(companyId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(dlFileEntry);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<DLFileEntry> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the document library file entries where companyId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 */
	@Override
	public void removeByCompanyId(long companyId) {
		for (DLFileEntry dlFileEntry : findByCompanyId(companyId,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(dlFileEntry);
		}
	}

	/**
	 * Returns the number of document library file entries where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching document library file entries
	 */
	@Override
	public int countByCompanyId(long companyId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_COMPANYID;

		Object[] finderArgs = new Object[] { companyId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_DLFILEENTRY_WHERE);

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

	private static final String _FINDER_COLUMN_COMPANYID_COMPANYID_2 = "dlFileEntry.companyId = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_REPOSITORYID =
		new FinderPath(DLFileEntryModelImpl.ENTITY_CACHE_ENABLED,
			DLFileEntryModelImpl.FINDER_CACHE_ENABLED, DLFileEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByRepositoryId",
			new String[] {
				Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_REPOSITORYID =
		new FinderPath(DLFileEntryModelImpl.ENTITY_CACHE_ENABLED,
			DLFileEntryModelImpl.FINDER_CACHE_ENABLED, DLFileEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByRepositoryId",
			new String[] { Long.class.getName() },
			DLFileEntryModelImpl.REPOSITORYID_COLUMN_BITMASK |
			DLFileEntryModelImpl.FOLDERID_COLUMN_BITMASK |
			DLFileEntryModelImpl.NAME_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_REPOSITORYID = new FinderPath(DLFileEntryModelImpl.ENTITY_CACHE_ENABLED,
			DLFileEntryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByRepositoryId",
			new String[] { Long.class.getName() });

	/**
	 * Returns all the document library file entries where repositoryId = &#63;.
	 *
	 * @param repositoryId the repository ID
	 * @return the matching document library file entries
	 */
	@Override
	public List<DLFileEntry> findByRepositoryId(long repositoryId) {
		return findByRepositoryId(repositoryId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the document library file entries where repositoryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFileEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param repositoryId the repository ID
	 * @param start the lower bound of the range of document library file entries
	 * @param end the upper bound of the range of document library file entries (not inclusive)
	 * @return the range of matching document library file entries
	 */
	@Override
	public List<DLFileEntry> findByRepositoryId(long repositoryId, int start,
		int end) {
		return findByRepositoryId(repositoryId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the document library file entries where repositoryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFileEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param repositoryId the repository ID
	 * @param start the lower bound of the range of document library file entries
	 * @param end the upper bound of the range of document library file entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching document library file entries
	 */
	@Override
	public List<DLFileEntry> findByRepositoryId(long repositoryId, int start,
		int end, OrderByComparator<DLFileEntry> orderByComparator) {
		return findByRepositoryId(repositoryId, start, end, orderByComparator,
			true);
	}

	/**
	 * Returns an ordered range of all the document library file entries where repositoryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFileEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param repositoryId the repository ID
	 * @param start the lower bound of the range of document library file entries
	 * @param end the upper bound of the range of document library file entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching document library file entries
	 */
	@Override
	public List<DLFileEntry> findByRepositoryId(long repositoryId, int start,
		int end, OrderByComparator<DLFileEntry> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_REPOSITORYID;
			finderArgs = new Object[] { repositoryId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_REPOSITORYID;
			finderArgs = new Object[] {
					repositoryId,
					
					start, end, orderByComparator
				};
		}

		List<DLFileEntry> list = null;

		if (retrieveFromCache) {
			list = (List<DLFileEntry>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (DLFileEntry dlFileEntry : list) {
					if ((repositoryId != dlFileEntry.getRepositoryId())) {
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

			query.append(_SQL_SELECT_DLFILEENTRY_WHERE);

			query.append(_FINDER_COLUMN_REPOSITORYID_REPOSITORYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(DLFileEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(repositoryId);

				if (!pagination) {
					list = (List<DLFileEntry>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<DLFileEntry>)QueryUtil.list(q, getDialect(),
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
	 * Returns the first document library file entry in the ordered set where repositoryId = &#63;.
	 *
	 * @param repositoryId the repository ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching document library file entry
	 * @throws NoSuchFileEntryException if a matching document library file entry could not be found
	 */
	@Override
	public DLFileEntry findByRepositoryId_First(long repositoryId,
		OrderByComparator<DLFileEntry> orderByComparator)
		throws NoSuchFileEntryException {
		DLFileEntry dlFileEntry = fetchByRepositoryId_First(repositoryId,
				orderByComparator);

		if (dlFileEntry != null) {
			return dlFileEntry;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("repositoryId=");
		msg.append(repositoryId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchFileEntryException(msg.toString());
	}

	/**
	 * Returns the first document library file entry in the ordered set where repositoryId = &#63;.
	 *
	 * @param repositoryId the repository ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching document library file entry, or <code>null</code> if a matching document library file entry could not be found
	 */
	@Override
	public DLFileEntry fetchByRepositoryId_First(long repositoryId,
		OrderByComparator<DLFileEntry> orderByComparator) {
		List<DLFileEntry> list = findByRepositoryId(repositoryId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last document library file entry in the ordered set where repositoryId = &#63;.
	 *
	 * @param repositoryId the repository ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching document library file entry
	 * @throws NoSuchFileEntryException if a matching document library file entry could not be found
	 */
	@Override
	public DLFileEntry findByRepositoryId_Last(long repositoryId,
		OrderByComparator<DLFileEntry> orderByComparator)
		throws NoSuchFileEntryException {
		DLFileEntry dlFileEntry = fetchByRepositoryId_Last(repositoryId,
				orderByComparator);

		if (dlFileEntry != null) {
			return dlFileEntry;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("repositoryId=");
		msg.append(repositoryId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchFileEntryException(msg.toString());
	}

	/**
	 * Returns the last document library file entry in the ordered set where repositoryId = &#63;.
	 *
	 * @param repositoryId the repository ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching document library file entry, or <code>null</code> if a matching document library file entry could not be found
	 */
	@Override
	public DLFileEntry fetchByRepositoryId_Last(long repositoryId,
		OrderByComparator<DLFileEntry> orderByComparator) {
		int count = countByRepositoryId(repositoryId);

		if (count == 0) {
			return null;
		}

		List<DLFileEntry> list = findByRepositoryId(repositoryId, count - 1,
				count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the document library file entries before and after the current document library file entry in the ordered set where repositoryId = &#63;.
	 *
	 * @param fileEntryId the primary key of the current document library file entry
	 * @param repositoryId the repository ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next document library file entry
	 * @throws NoSuchFileEntryException if a document library file entry with the primary key could not be found
	 */
	@Override
	public DLFileEntry[] findByRepositoryId_PrevAndNext(long fileEntryId,
		long repositoryId, OrderByComparator<DLFileEntry> orderByComparator)
		throws NoSuchFileEntryException {
		DLFileEntry dlFileEntry = findByPrimaryKey(fileEntryId);

		Session session = null;

		try {
			session = openSession();

			DLFileEntry[] array = new DLFileEntryImpl[3];

			array[0] = getByRepositoryId_PrevAndNext(session, dlFileEntry,
					repositoryId, orderByComparator, true);

			array[1] = dlFileEntry;

			array[2] = getByRepositoryId_PrevAndNext(session, dlFileEntry,
					repositoryId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected DLFileEntry getByRepositoryId_PrevAndNext(Session session,
		DLFileEntry dlFileEntry, long repositoryId,
		OrderByComparator<DLFileEntry> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(4 +
					(orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_DLFILEENTRY_WHERE);

		query.append(_FINDER_COLUMN_REPOSITORYID_REPOSITORYID_2);

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
			query.append(DLFileEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(repositoryId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(dlFileEntry);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<DLFileEntry> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the document library file entries where repositoryId = &#63; from the database.
	 *
	 * @param repositoryId the repository ID
	 */
	@Override
	public void removeByRepositoryId(long repositoryId) {
		for (DLFileEntry dlFileEntry : findByRepositoryId(repositoryId,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(dlFileEntry);
		}
	}

	/**
	 * Returns the number of document library file entries where repositoryId = &#63;.
	 *
	 * @param repositoryId the repository ID
	 * @return the number of matching document library file entries
	 */
	@Override
	public int countByRepositoryId(long repositoryId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_REPOSITORYID;

		Object[] finderArgs = new Object[] { repositoryId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_DLFILEENTRY_WHERE);

			query.append(_FINDER_COLUMN_REPOSITORYID_REPOSITORYID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(repositoryId);

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

	private static final String _FINDER_COLUMN_REPOSITORYID_REPOSITORYID_2 = "dlFileEntry.repositoryId = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_MIMETYPE = new FinderPath(DLFileEntryModelImpl.ENTITY_CACHE_ENABLED,
			DLFileEntryModelImpl.FINDER_CACHE_ENABLED, DLFileEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByMimeType",
			new String[] {
				String.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_MIMETYPE =
		new FinderPath(DLFileEntryModelImpl.ENTITY_CACHE_ENABLED,
			DLFileEntryModelImpl.FINDER_CACHE_ENABLED, DLFileEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByMimeType",
			new String[] { String.class.getName() },
			DLFileEntryModelImpl.MIMETYPE_COLUMN_BITMASK |
			DLFileEntryModelImpl.FOLDERID_COLUMN_BITMASK |
			DLFileEntryModelImpl.NAME_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_MIMETYPE = new FinderPath(DLFileEntryModelImpl.ENTITY_CACHE_ENABLED,
			DLFileEntryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByMimeType",
			new String[] { String.class.getName() });

	/**
	 * Returns all the document library file entries where mimeType = &#63;.
	 *
	 * @param mimeType the mime type
	 * @return the matching document library file entries
	 */
	@Override
	public List<DLFileEntry> findByMimeType(String mimeType) {
		return findByMimeType(mimeType, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the document library file entries where mimeType = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFileEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param mimeType the mime type
	 * @param start the lower bound of the range of document library file entries
	 * @param end the upper bound of the range of document library file entries (not inclusive)
	 * @return the range of matching document library file entries
	 */
	@Override
	public List<DLFileEntry> findByMimeType(String mimeType, int start, int end) {
		return findByMimeType(mimeType, start, end, null);
	}

	/**
	 * Returns an ordered range of all the document library file entries where mimeType = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFileEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param mimeType the mime type
	 * @param start the lower bound of the range of document library file entries
	 * @param end the upper bound of the range of document library file entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching document library file entries
	 */
	@Override
	public List<DLFileEntry> findByMimeType(String mimeType, int start,
		int end, OrderByComparator<DLFileEntry> orderByComparator) {
		return findByMimeType(mimeType, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the document library file entries where mimeType = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFileEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param mimeType the mime type
	 * @param start the lower bound of the range of document library file entries
	 * @param end the upper bound of the range of document library file entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching document library file entries
	 */
	@Override
	public List<DLFileEntry> findByMimeType(String mimeType, int start,
		int end, OrderByComparator<DLFileEntry> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_MIMETYPE;
			finderArgs = new Object[] { mimeType };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_MIMETYPE;
			finderArgs = new Object[] { mimeType, start, end, orderByComparator };
		}

		List<DLFileEntry> list = null;

		if (retrieveFromCache) {
			list = (List<DLFileEntry>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (DLFileEntry dlFileEntry : list) {
					if (!Objects.equals(mimeType, dlFileEntry.getMimeType())) {
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

			query.append(_SQL_SELECT_DLFILEENTRY_WHERE);

			boolean bindMimeType = false;

			if (mimeType == null) {
				query.append(_FINDER_COLUMN_MIMETYPE_MIMETYPE_1);
			}
			else if (mimeType.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_MIMETYPE_MIMETYPE_3);
			}
			else {
				bindMimeType = true;

				query.append(_FINDER_COLUMN_MIMETYPE_MIMETYPE_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(DLFileEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindMimeType) {
					qPos.add(mimeType);
				}

				if (!pagination) {
					list = (List<DLFileEntry>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<DLFileEntry>)QueryUtil.list(q, getDialect(),
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
	 * Returns the first document library file entry in the ordered set where mimeType = &#63;.
	 *
	 * @param mimeType the mime type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching document library file entry
	 * @throws NoSuchFileEntryException if a matching document library file entry could not be found
	 */
	@Override
	public DLFileEntry findByMimeType_First(String mimeType,
		OrderByComparator<DLFileEntry> orderByComparator)
		throws NoSuchFileEntryException {
		DLFileEntry dlFileEntry = fetchByMimeType_First(mimeType,
				orderByComparator);

		if (dlFileEntry != null) {
			return dlFileEntry;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("mimeType=");
		msg.append(mimeType);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchFileEntryException(msg.toString());
	}

	/**
	 * Returns the first document library file entry in the ordered set where mimeType = &#63;.
	 *
	 * @param mimeType the mime type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching document library file entry, or <code>null</code> if a matching document library file entry could not be found
	 */
	@Override
	public DLFileEntry fetchByMimeType_First(String mimeType,
		OrderByComparator<DLFileEntry> orderByComparator) {
		List<DLFileEntry> list = findByMimeType(mimeType, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last document library file entry in the ordered set where mimeType = &#63;.
	 *
	 * @param mimeType the mime type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching document library file entry
	 * @throws NoSuchFileEntryException if a matching document library file entry could not be found
	 */
	@Override
	public DLFileEntry findByMimeType_Last(String mimeType,
		OrderByComparator<DLFileEntry> orderByComparator)
		throws NoSuchFileEntryException {
		DLFileEntry dlFileEntry = fetchByMimeType_Last(mimeType,
				orderByComparator);

		if (dlFileEntry != null) {
			return dlFileEntry;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("mimeType=");
		msg.append(mimeType);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchFileEntryException(msg.toString());
	}

	/**
	 * Returns the last document library file entry in the ordered set where mimeType = &#63;.
	 *
	 * @param mimeType the mime type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching document library file entry, or <code>null</code> if a matching document library file entry could not be found
	 */
	@Override
	public DLFileEntry fetchByMimeType_Last(String mimeType,
		OrderByComparator<DLFileEntry> orderByComparator) {
		int count = countByMimeType(mimeType);

		if (count == 0) {
			return null;
		}

		List<DLFileEntry> list = findByMimeType(mimeType, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the document library file entries before and after the current document library file entry in the ordered set where mimeType = &#63;.
	 *
	 * @param fileEntryId the primary key of the current document library file entry
	 * @param mimeType the mime type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next document library file entry
	 * @throws NoSuchFileEntryException if a document library file entry with the primary key could not be found
	 */
	@Override
	public DLFileEntry[] findByMimeType_PrevAndNext(long fileEntryId,
		String mimeType, OrderByComparator<DLFileEntry> orderByComparator)
		throws NoSuchFileEntryException {
		DLFileEntry dlFileEntry = findByPrimaryKey(fileEntryId);

		Session session = null;

		try {
			session = openSession();

			DLFileEntry[] array = new DLFileEntryImpl[3];

			array[0] = getByMimeType_PrevAndNext(session, dlFileEntry,
					mimeType, orderByComparator, true);

			array[1] = dlFileEntry;

			array[2] = getByMimeType_PrevAndNext(session, dlFileEntry,
					mimeType, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected DLFileEntry getByMimeType_PrevAndNext(Session session,
		DLFileEntry dlFileEntry, String mimeType,
		OrderByComparator<DLFileEntry> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(4 +
					(orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_DLFILEENTRY_WHERE);

		boolean bindMimeType = false;

		if (mimeType == null) {
			query.append(_FINDER_COLUMN_MIMETYPE_MIMETYPE_1);
		}
		else if (mimeType.equals(StringPool.BLANK)) {
			query.append(_FINDER_COLUMN_MIMETYPE_MIMETYPE_3);
		}
		else {
			bindMimeType = true;

			query.append(_FINDER_COLUMN_MIMETYPE_MIMETYPE_2);
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
			query.append(DLFileEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		if (bindMimeType) {
			qPos.add(mimeType);
		}

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(dlFileEntry);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<DLFileEntry> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the document library file entries where mimeType = &#63; from the database.
	 *
	 * @param mimeType the mime type
	 */
	@Override
	public void removeByMimeType(String mimeType) {
		for (DLFileEntry dlFileEntry : findByMimeType(mimeType,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(dlFileEntry);
		}
	}

	/**
	 * Returns the number of document library file entries where mimeType = &#63;.
	 *
	 * @param mimeType the mime type
	 * @return the number of matching document library file entries
	 */
	@Override
	public int countByMimeType(String mimeType) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_MIMETYPE;

		Object[] finderArgs = new Object[] { mimeType };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_DLFILEENTRY_WHERE);

			boolean bindMimeType = false;

			if (mimeType == null) {
				query.append(_FINDER_COLUMN_MIMETYPE_MIMETYPE_1);
			}
			else if (mimeType.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_MIMETYPE_MIMETYPE_3);
			}
			else {
				bindMimeType = true;

				query.append(_FINDER_COLUMN_MIMETYPE_MIMETYPE_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindMimeType) {
					qPos.add(mimeType);
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

	private static final String _FINDER_COLUMN_MIMETYPE_MIMETYPE_1 = "dlFileEntry.mimeType IS NULL";
	private static final String _FINDER_COLUMN_MIMETYPE_MIMETYPE_2 = "dlFileEntry.mimeType = ?";
	private static final String _FINDER_COLUMN_MIMETYPE_MIMETYPE_3 = "(dlFileEntry.mimeType IS NULL OR dlFileEntry.mimeType = '')";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_FILEENTRYTYPEID =
		new FinderPath(DLFileEntryModelImpl.ENTITY_CACHE_ENABLED,
			DLFileEntryModelImpl.FINDER_CACHE_ENABLED, DLFileEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByFileEntryTypeId",
			new String[] {
				Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_FILEENTRYTYPEID =
		new FinderPath(DLFileEntryModelImpl.ENTITY_CACHE_ENABLED,
			DLFileEntryModelImpl.FINDER_CACHE_ENABLED, DLFileEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByFileEntryTypeId",
			new String[] { Long.class.getName() },
			DLFileEntryModelImpl.FILEENTRYTYPEID_COLUMN_BITMASK |
			DLFileEntryModelImpl.FOLDERID_COLUMN_BITMASK |
			DLFileEntryModelImpl.NAME_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_FILEENTRYTYPEID = new FinderPath(DLFileEntryModelImpl.ENTITY_CACHE_ENABLED,
			DLFileEntryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByFileEntryTypeId", new String[] { Long.class.getName() });

	/**
	 * Returns all the document library file entries where fileEntryTypeId = &#63;.
	 *
	 * @param fileEntryTypeId the file entry type ID
	 * @return the matching document library file entries
	 */
	@Override
	public List<DLFileEntry> findByFileEntryTypeId(long fileEntryTypeId) {
		return findByFileEntryTypeId(fileEntryTypeId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the document library file entries where fileEntryTypeId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFileEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param fileEntryTypeId the file entry type ID
	 * @param start the lower bound of the range of document library file entries
	 * @param end the upper bound of the range of document library file entries (not inclusive)
	 * @return the range of matching document library file entries
	 */
	@Override
	public List<DLFileEntry> findByFileEntryTypeId(long fileEntryTypeId,
		int start, int end) {
		return findByFileEntryTypeId(fileEntryTypeId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the document library file entries where fileEntryTypeId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFileEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param fileEntryTypeId the file entry type ID
	 * @param start the lower bound of the range of document library file entries
	 * @param end the upper bound of the range of document library file entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching document library file entries
	 */
	@Override
	public List<DLFileEntry> findByFileEntryTypeId(long fileEntryTypeId,
		int start, int end, OrderByComparator<DLFileEntry> orderByComparator) {
		return findByFileEntryTypeId(fileEntryTypeId, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the document library file entries where fileEntryTypeId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFileEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param fileEntryTypeId the file entry type ID
	 * @param start the lower bound of the range of document library file entries
	 * @param end the upper bound of the range of document library file entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching document library file entries
	 */
	@Override
	public List<DLFileEntry> findByFileEntryTypeId(long fileEntryTypeId,
		int start, int end, OrderByComparator<DLFileEntry> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_FILEENTRYTYPEID;
			finderArgs = new Object[] { fileEntryTypeId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_FILEENTRYTYPEID;
			finderArgs = new Object[] {
					fileEntryTypeId,
					
					start, end, orderByComparator
				};
		}

		List<DLFileEntry> list = null;

		if (retrieveFromCache) {
			list = (List<DLFileEntry>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (DLFileEntry dlFileEntry : list) {
					if ((fileEntryTypeId != dlFileEntry.getFileEntryTypeId())) {
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

			query.append(_SQL_SELECT_DLFILEENTRY_WHERE);

			query.append(_FINDER_COLUMN_FILEENTRYTYPEID_FILEENTRYTYPEID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(DLFileEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(fileEntryTypeId);

				if (!pagination) {
					list = (List<DLFileEntry>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<DLFileEntry>)QueryUtil.list(q, getDialect(),
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
	 * Returns the first document library file entry in the ordered set where fileEntryTypeId = &#63;.
	 *
	 * @param fileEntryTypeId the file entry type ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching document library file entry
	 * @throws NoSuchFileEntryException if a matching document library file entry could not be found
	 */
	@Override
	public DLFileEntry findByFileEntryTypeId_First(long fileEntryTypeId,
		OrderByComparator<DLFileEntry> orderByComparator)
		throws NoSuchFileEntryException {
		DLFileEntry dlFileEntry = fetchByFileEntryTypeId_First(fileEntryTypeId,
				orderByComparator);

		if (dlFileEntry != null) {
			return dlFileEntry;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("fileEntryTypeId=");
		msg.append(fileEntryTypeId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchFileEntryException(msg.toString());
	}

	/**
	 * Returns the first document library file entry in the ordered set where fileEntryTypeId = &#63;.
	 *
	 * @param fileEntryTypeId the file entry type ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching document library file entry, or <code>null</code> if a matching document library file entry could not be found
	 */
	@Override
	public DLFileEntry fetchByFileEntryTypeId_First(long fileEntryTypeId,
		OrderByComparator<DLFileEntry> orderByComparator) {
		List<DLFileEntry> list = findByFileEntryTypeId(fileEntryTypeId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last document library file entry in the ordered set where fileEntryTypeId = &#63;.
	 *
	 * @param fileEntryTypeId the file entry type ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching document library file entry
	 * @throws NoSuchFileEntryException if a matching document library file entry could not be found
	 */
	@Override
	public DLFileEntry findByFileEntryTypeId_Last(long fileEntryTypeId,
		OrderByComparator<DLFileEntry> orderByComparator)
		throws NoSuchFileEntryException {
		DLFileEntry dlFileEntry = fetchByFileEntryTypeId_Last(fileEntryTypeId,
				orderByComparator);

		if (dlFileEntry != null) {
			return dlFileEntry;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("fileEntryTypeId=");
		msg.append(fileEntryTypeId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchFileEntryException(msg.toString());
	}

	/**
	 * Returns the last document library file entry in the ordered set where fileEntryTypeId = &#63;.
	 *
	 * @param fileEntryTypeId the file entry type ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching document library file entry, or <code>null</code> if a matching document library file entry could not be found
	 */
	@Override
	public DLFileEntry fetchByFileEntryTypeId_Last(long fileEntryTypeId,
		OrderByComparator<DLFileEntry> orderByComparator) {
		int count = countByFileEntryTypeId(fileEntryTypeId);

		if (count == 0) {
			return null;
		}

		List<DLFileEntry> list = findByFileEntryTypeId(fileEntryTypeId,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the document library file entries before and after the current document library file entry in the ordered set where fileEntryTypeId = &#63;.
	 *
	 * @param fileEntryId the primary key of the current document library file entry
	 * @param fileEntryTypeId the file entry type ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next document library file entry
	 * @throws NoSuchFileEntryException if a document library file entry with the primary key could not be found
	 */
	@Override
	public DLFileEntry[] findByFileEntryTypeId_PrevAndNext(long fileEntryId,
		long fileEntryTypeId, OrderByComparator<DLFileEntry> orderByComparator)
		throws NoSuchFileEntryException {
		DLFileEntry dlFileEntry = findByPrimaryKey(fileEntryId);

		Session session = null;

		try {
			session = openSession();

			DLFileEntry[] array = new DLFileEntryImpl[3];

			array[0] = getByFileEntryTypeId_PrevAndNext(session, dlFileEntry,
					fileEntryTypeId, orderByComparator, true);

			array[1] = dlFileEntry;

			array[2] = getByFileEntryTypeId_PrevAndNext(session, dlFileEntry,
					fileEntryTypeId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected DLFileEntry getByFileEntryTypeId_PrevAndNext(Session session,
		DLFileEntry dlFileEntry, long fileEntryTypeId,
		OrderByComparator<DLFileEntry> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(4 +
					(orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_DLFILEENTRY_WHERE);

		query.append(_FINDER_COLUMN_FILEENTRYTYPEID_FILEENTRYTYPEID_2);

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
			query.append(DLFileEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(fileEntryTypeId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(dlFileEntry);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<DLFileEntry> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the document library file entries where fileEntryTypeId = &#63; from the database.
	 *
	 * @param fileEntryTypeId the file entry type ID
	 */
	@Override
	public void removeByFileEntryTypeId(long fileEntryTypeId) {
		for (DLFileEntry dlFileEntry : findByFileEntryTypeId(fileEntryTypeId,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(dlFileEntry);
		}
	}

	/**
	 * Returns the number of document library file entries where fileEntryTypeId = &#63;.
	 *
	 * @param fileEntryTypeId the file entry type ID
	 * @return the number of matching document library file entries
	 */
	@Override
	public int countByFileEntryTypeId(long fileEntryTypeId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_FILEENTRYTYPEID;

		Object[] finderArgs = new Object[] { fileEntryTypeId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_DLFILEENTRY_WHERE);

			query.append(_FINDER_COLUMN_FILEENTRYTYPEID_FILEENTRYTYPEID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(fileEntryTypeId);

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

	private static final String _FINDER_COLUMN_FILEENTRYTYPEID_FILEENTRYTYPEID_2 =
		"dlFileEntry.fileEntryTypeId = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_SMALLIMAGEID =
		new FinderPath(DLFileEntryModelImpl.ENTITY_CACHE_ENABLED,
			DLFileEntryModelImpl.FINDER_CACHE_ENABLED, DLFileEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findBySmallImageId",
			new String[] {
				Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_SMALLIMAGEID =
		new FinderPath(DLFileEntryModelImpl.ENTITY_CACHE_ENABLED,
			DLFileEntryModelImpl.FINDER_CACHE_ENABLED, DLFileEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findBySmallImageId",
			new String[] { Long.class.getName() },
			DLFileEntryModelImpl.SMALLIMAGEID_COLUMN_BITMASK |
			DLFileEntryModelImpl.FOLDERID_COLUMN_BITMASK |
			DLFileEntryModelImpl.NAME_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_SMALLIMAGEID = new FinderPath(DLFileEntryModelImpl.ENTITY_CACHE_ENABLED,
			DLFileEntryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countBySmallImageId",
			new String[] { Long.class.getName() });

	/**
	 * Returns all the document library file entries where smallImageId = &#63;.
	 *
	 * @param smallImageId the small image ID
	 * @return the matching document library file entries
	 */
	@Override
	public List<DLFileEntry> findBySmallImageId(long smallImageId) {
		return findBySmallImageId(smallImageId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the document library file entries where smallImageId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFileEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param smallImageId the small image ID
	 * @param start the lower bound of the range of document library file entries
	 * @param end the upper bound of the range of document library file entries (not inclusive)
	 * @return the range of matching document library file entries
	 */
	@Override
	public List<DLFileEntry> findBySmallImageId(long smallImageId, int start,
		int end) {
		return findBySmallImageId(smallImageId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the document library file entries where smallImageId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFileEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param smallImageId the small image ID
	 * @param start the lower bound of the range of document library file entries
	 * @param end the upper bound of the range of document library file entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching document library file entries
	 */
	@Override
	public List<DLFileEntry> findBySmallImageId(long smallImageId, int start,
		int end, OrderByComparator<DLFileEntry> orderByComparator) {
		return findBySmallImageId(smallImageId, start, end, orderByComparator,
			true);
	}

	/**
	 * Returns an ordered range of all the document library file entries where smallImageId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFileEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param smallImageId the small image ID
	 * @param start the lower bound of the range of document library file entries
	 * @param end the upper bound of the range of document library file entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching document library file entries
	 */
	@Override
	public List<DLFileEntry> findBySmallImageId(long smallImageId, int start,
		int end, OrderByComparator<DLFileEntry> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_SMALLIMAGEID;
			finderArgs = new Object[] { smallImageId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_SMALLIMAGEID;
			finderArgs = new Object[] {
					smallImageId,
					
					start, end, orderByComparator
				};
		}

		List<DLFileEntry> list = null;

		if (retrieveFromCache) {
			list = (List<DLFileEntry>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (DLFileEntry dlFileEntry : list) {
					if ((smallImageId != dlFileEntry.getSmallImageId())) {
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

			query.append(_SQL_SELECT_DLFILEENTRY_WHERE);

			query.append(_FINDER_COLUMN_SMALLIMAGEID_SMALLIMAGEID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(DLFileEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(smallImageId);

				if (!pagination) {
					list = (List<DLFileEntry>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<DLFileEntry>)QueryUtil.list(q, getDialect(),
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
	 * Returns the first document library file entry in the ordered set where smallImageId = &#63;.
	 *
	 * @param smallImageId the small image ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching document library file entry
	 * @throws NoSuchFileEntryException if a matching document library file entry could not be found
	 */
	@Override
	public DLFileEntry findBySmallImageId_First(long smallImageId,
		OrderByComparator<DLFileEntry> orderByComparator)
		throws NoSuchFileEntryException {
		DLFileEntry dlFileEntry = fetchBySmallImageId_First(smallImageId,
				orderByComparator);

		if (dlFileEntry != null) {
			return dlFileEntry;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("smallImageId=");
		msg.append(smallImageId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchFileEntryException(msg.toString());
	}

	/**
	 * Returns the first document library file entry in the ordered set where smallImageId = &#63;.
	 *
	 * @param smallImageId the small image ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching document library file entry, or <code>null</code> if a matching document library file entry could not be found
	 */
	@Override
	public DLFileEntry fetchBySmallImageId_First(long smallImageId,
		OrderByComparator<DLFileEntry> orderByComparator) {
		List<DLFileEntry> list = findBySmallImageId(smallImageId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last document library file entry in the ordered set where smallImageId = &#63;.
	 *
	 * @param smallImageId the small image ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching document library file entry
	 * @throws NoSuchFileEntryException if a matching document library file entry could not be found
	 */
	@Override
	public DLFileEntry findBySmallImageId_Last(long smallImageId,
		OrderByComparator<DLFileEntry> orderByComparator)
		throws NoSuchFileEntryException {
		DLFileEntry dlFileEntry = fetchBySmallImageId_Last(smallImageId,
				orderByComparator);

		if (dlFileEntry != null) {
			return dlFileEntry;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("smallImageId=");
		msg.append(smallImageId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchFileEntryException(msg.toString());
	}

	/**
	 * Returns the last document library file entry in the ordered set where smallImageId = &#63;.
	 *
	 * @param smallImageId the small image ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching document library file entry, or <code>null</code> if a matching document library file entry could not be found
	 */
	@Override
	public DLFileEntry fetchBySmallImageId_Last(long smallImageId,
		OrderByComparator<DLFileEntry> orderByComparator) {
		int count = countBySmallImageId(smallImageId);

		if (count == 0) {
			return null;
		}

		List<DLFileEntry> list = findBySmallImageId(smallImageId, count - 1,
				count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the document library file entries before and after the current document library file entry in the ordered set where smallImageId = &#63;.
	 *
	 * @param fileEntryId the primary key of the current document library file entry
	 * @param smallImageId the small image ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next document library file entry
	 * @throws NoSuchFileEntryException if a document library file entry with the primary key could not be found
	 */
	@Override
	public DLFileEntry[] findBySmallImageId_PrevAndNext(long fileEntryId,
		long smallImageId, OrderByComparator<DLFileEntry> orderByComparator)
		throws NoSuchFileEntryException {
		DLFileEntry dlFileEntry = findByPrimaryKey(fileEntryId);

		Session session = null;

		try {
			session = openSession();

			DLFileEntry[] array = new DLFileEntryImpl[3];

			array[0] = getBySmallImageId_PrevAndNext(session, dlFileEntry,
					smallImageId, orderByComparator, true);

			array[1] = dlFileEntry;

			array[2] = getBySmallImageId_PrevAndNext(session, dlFileEntry,
					smallImageId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected DLFileEntry getBySmallImageId_PrevAndNext(Session session,
		DLFileEntry dlFileEntry, long smallImageId,
		OrderByComparator<DLFileEntry> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(4 +
					(orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_DLFILEENTRY_WHERE);

		query.append(_FINDER_COLUMN_SMALLIMAGEID_SMALLIMAGEID_2);

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
			query.append(DLFileEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(smallImageId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(dlFileEntry);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<DLFileEntry> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the document library file entries where smallImageId = &#63; from the database.
	 *
	 * @param smallImageId the small image ID
	 */
	@Override
	public void removeBySmallImageId(long smallImageId) {
		for (DLFileEntry dlFileEntry : findBySmallImageId(smallImageId,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(dlFileEntry);
		}
	}

	/**
	 * Returns the number of document library file entries where smallImageId = &#63;.
	 *
	 * @param smallImageId the small image ID
	 * @return the number of matching document library file entries
	 */
	@Override
	public int countBySmallImageId(long smallImageId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_SMALLIMAGEID;

		Object[] finderArgs = new Object[] { smallImageId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_DLFILEENTRY_WHERE);

			query.append(_FINDER_COLUMN_SMALLIMAGEID_SMALLIMAGEID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(smallImageId);

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

	private static final String _FINDER_COLUMN_SMALLIMAGEID_SMALLIMAGEID_2 = "dlFileEntry.smallImageId = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_LARGEIMAGEID =
		new FinderPath(DLFileEntryModelImpl.ENTITY_CACHE_ENABLED,
			DLFileEntryModelImpl.FINDER_CACHE_ENABLED, DLFileEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByLargeImageId",
			new String[] {
				Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_LARGEIMAGEID =
		new FinderPath(DLFileEntryModelImpl.ENTITY_CACHE_ENABLED,
			DLFileEntryModelImpl.FINDER_CACHE_ENABLED, DLFileEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByLargeImageId",
			new String[] { Long.class.getName() },
			DLFileEntryModelImpl.LARGEIMAGEID_COLUMN_BITMASK |
			DLFileEntryModelImpl.FOLDERID_COLUMN_BITMASK |
			DLFileEntryModelImpl.NAME_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_LARGEIMAGEID = new FinderPath(DLFileEntryModelImpl.ENTITY_CACHE_ENABLED,
			DLFileEntryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByLargeImageId",
			new String[] { Long.class.getName() });

	/**
	 * Returns all the document library file entries where largeImageId = &#63;.
	 *
	 * @param largeImageId the large image ID
	 * @return the matching document library file entries
	 */
	@Override
	public List<DLFileEntry> findByLargeImageId(long largeImageId) {
		return findByLargeImageId(largeImageId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the document library file entries where largeImageId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFileEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param largeImageId the large image ID
	 * @param start the lower bound of the range of document library file entries
	 * @param end the upper bound of the range of document library file entries (not inclusive)
	 * @return the range of matching document library file entries
	 */
	@Override
	public List<DLFileEntry> findByLargeImageId(long largeImageId, int start,
		int end) {
		return findByLargeImageId(largeImageId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the document library file entries where largeImageId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFileEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param largeImageId the large image ID
	 * @param start the lower bound of the range of document library file entries
	 * @param end the upper bound of the range of document library file entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching document library file entries
	 */
	@Override
	public List<DLFileEntry> findByLargeImageId(long largeImageId, int start,
		int end, OrderByComparator<DLFileEntry> orderByComparator) {
		return findByLargeImageId(largeImageId, start, end, orderByComparator,
			true);
	}

	/**
	 * Returns an ordered range of all the document library file entries where largeImageId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFileEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param largeImageId the large image ID
	 * @param start the lower bound of the range of document library file entries
	 * @param end the upper bound of the range of document library file entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching document library file entries
	 */
	@Override
	public List<DLFileEntry> findByLargeImageId(long largeImageId, int start,
		int end, OrderByComparator<DLFileEntry> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_LARGEIMAGEID;
			finderArgs = new Object[] { largeImageId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_LARGEIMAGEID;
			finderArgs = new Object[] {
					largeImageId,
					
					start, end, orderByComparator
				};
		}

		List<DLFileEntry> list = null;

		if (retrieveFromCache) {
			list = (List<DLFileEntry>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (DLFileEntry dlFileEntry : list) {
					if ((largeImageId != dlFileEntry.getLargeImageId())) {
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

			query.append(_SQL_SELECT_DLFILEENTRY_WHERE);

			query.append(_FINDER_COLUMN_LARGEIMAGEID_LARGEIMAGEID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(DLFileEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(largeImageId);

				if (!pagination) {
					list = (List<DLFileEntry>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<DLFileEntry>)QueryUtil.list(q, getDialect(),
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
	 * Returns the first document library file entry in the ordered set where largeImageId = &#63;.
	 *
	 * @param largeImageId the large image ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching document library file entry
	 * @throws NoSuchFileEntryException if a matching document library file entry could not be found
	 */
	@Override
	public DLFileEntry findByLargeImageId_First(long largeImageId,
		OrderByComparator<DLFileEntry> orderByComparator)
		throws NoSuchFileEntryException {
		DLFileEntry dlFileEntry = fetchByLargeImageId_First(largeImageId,
				orderByComparator);

		if (dlFileEntry != null) {
			return dlFileEntry;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("largeImageId=");
		msg.append(largeImageId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchFileEntryException(msg.toString());
	}

	/**
	 * Returns the first document library file entry in the ordered set where largeImageId = &#63;.
	 *
	 * @param largeImageId the large image ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching document library file entry, or <code>null</code> if a matching document library file entry could not be found
	 */
	@Override
	public DLFileEntry fetchByLargeImageId_First(long largeImageId,
		OrderByComparator<DLFileEntry> orderByComparator) {
		List<DLFileEntry> list = findByLargeImageId(largeImageId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last document library file entry in the ordered set where largeImageId = &#63;.
	 *
	 * @param largeImageId the large image ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching document library file entry
	 * @throws NoSuchFileEntryException if a matching document library file entry could not be found
	 */
	@Override
	public DLFileEntry findByLargeImageId_Last(long largeImageId,
		OrderByComparator<DLFileEntry> orderByComparator)
		throws NoSuchFileEntryException {
		DLFileEntry dlFileEntry = fetchByLargeImageId_Last(largeImageId,
				orderByComparator);

		if (dlFileEntry != null) {
			return dlFileEntry;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("largeImageId=");
		msg.append(largeImageId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchFileEntryException(msg.toString());
	}

	/**
	 * Returns the last document library file entry in the ordered set where largeImageId = &#63;.
	 *
	 * @param largeImageId the large image ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching document library file entry, or <code>null</code> if a matching document library file entry could not be found
	 */
	@Override
	public DLFileEntry fetchByLargeImageId_Last(long largeImageId,
		OrderByComparator<DLFileEntry> orderByComparator) {
		int count = countByLargeImageId(largeImageId);

		if (count == 0) {
			return null;
		}

		List<DLFileEntry> list = findByLargeImageId(largeImageId, count - 1,
				count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the document library file entries before and after the current document library file entry in the ordered set where largeImageId = &#63;.
	 *
	 * @param fileEntryId the primary key of the current document library file entry
	 * @param largeImageId the large image ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next document library file entry
	 * @throws NoSuchFileEntryException if a document library file entry with the primary key could not be found
	 */
	@Override
	public DLFileEntry[] findByLargeImageId_PrevAndNext(long fileEntryId,
		long largeImageId, OrderByComparator<DLFileEntry> orderByComparator)
		throws NoSuchFileEntryException {
		DLFileEntry dlFileEntry = findByPrimaryKey(fileEntryId);

		Session session = null;

		try {
			session = openSession();

			DLFileEntry[] array = new DLFileEntryImpl[3];

			array[0] = getByLargeImageId_PrevAndNext(session, dlFileEntry,
					largeImageId, orderByComparator, true);

			array[1] = dlFileEntry;

			array[2] = getByLargeImageId_PrevAndNext(session, dlFileEntry,
					largeImageId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected DLFileEntry getByLargeImageId_PrevAndNext(Session session,
		DLFileEntry dlFileEntry, long largeImageId,
		OrderByComparator<DLFileEntry> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(4 +
					(orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_DLFILEENTRY_WHERE);

		query.append(_FINDER_COLUMN_LARGEIMAGEID_LARGEIMAGEID_2);

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
			query.append(DLFileEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(largeImageId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(dlFileEntry);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<DLFileEntry> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the document library file entries where largeImageId = &#63; from the database.
	 *
	 * @param largeImageId the large image ID
	 */
	@Override
	public void removeByLargeImageId(long largeImageId) {
		for (DLFileEntry dlFileEntry : findByLargeImageId(largeImageId,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(dlFileEntry);
		}
	}

	/**
	 * Returns the number of document library file entries where largeImageId = &#63;.
	 *
	 * @param largeImageId the large image ID
	 * @return the number of matching document library file entries
	 */
	@Override
	public int countByLargeImageId(long largeImageId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_LARGEIMAGEID;

		Object[] finderArgs = new Object[] { largeImageId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_DLFILEENTRY_WHERE);

			query.append(_FINDER_COLUMN_LARGEIMAGEID_LARGEIMAGEID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(largeImageId);

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

	private static final String _FINDER_COLUMN_LARGEIMAGEID_LARGEIMAGEID_2 = "dlFileEntry.largeImageId = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_CUSTOM1IMAGEID =
		new FinderPath(DLFileEntryModelImpl.ENTITY_CACHE_ENABLED,
			DLFileEntryModelImpl.FINDER_CACHE_ENABLED, DLFileEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByCustom1ImageId",
			new String[] {
				Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_CUSTOM1IMAGEID =
		new FinderPath(DLFileEntryModelImpl.ENTITY_CACHE_ENABLED,
			DLFileEntryModelImpl.FINDER_CACHE_ENABLED, DLFileEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByCustom1ImageId",
			new String[] { Long.class.getName() },
			DLFileEntryModelImpl.CUSTOM1IMAGEID_COLUMN_BITMASK |
			DLFileEntryModelImpl.FOLDERID_COLUMN_BITMASK |
			DLFileEntryModelImpl.NAME_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_CUSTOM1IMAGEID = new FinderPath(DLFileEntryModelImpl.ENTITY_CACHE_ENABLED,
			DLFileEntryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByCustom1ImageId",
			new String[] { Long.class.getName() });

	/**
	 * Returns all the document library file entries where custom1ImageId = &#63;.
	 *
	 * @param custom1ImageId the custom1 image ID
	 * @return the matching document library file entries
	 */
	@Override
	public List<DLFileEntry> findByCustom1ImageId(long custom1ImageId) {
		return findByCustom1ImageId(custom1ImageId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the document library file entries where custom1ImageId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFileEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param custom1ImageId the custom1 image ID
	 * @param start the lower bound of the range of document library file entries
	 * @param end the upper bound of the range of document library file entries (not inclusive)
	 * @return the range of matching document library file entries
	 */
	@Override
	public List<DLFileEntry> findByCustom1ImageId(long custom1ImageId,
		int start, int end) {
		return findByCustom1ImageId(custom1ImageId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the document library file entries where custom1ImageId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFileEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param custom1ImageId the custom1 image ID
	 * @param start the lower bound of the range of document library file entries
	 * @param end the upper bound of the range of document library file entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching document library file entries
	 */
	@Override
	public List<DLFileEntry> findByCustom1ImageId(long custom1ImageId,
		int start, int end, OrderByComparator<DLFileEntry> orderByComparator) {
		return findByCustom1ImageId(custom1ImageId, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the document library file entries where custom1ImageId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFileEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param custom1ImageId the custom1 image ID
	 * @param start the lower bound of the range of document library file entries
	 * @param end the upper bound of the range of document library file entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching document library file entries
	 */
	@Override
	public List<DLFileEntry> findByCustom1ImageId(long custom1ImageId,
		int start, int end, OrderByComparator<DLFileEntry> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_CUSTOM1IMAGEID;
			finderArgs = new Object[] { custom1ImageId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_CUSTOM1IMAGEID;
			finderArgs = new Object[] {
					custom1ImageId,
					
					start, end, orderByComparator
				};
		}

		List<DLFileEntry> list = null;

		if (retrieveFromCache) {
			list = (List<DLFileEntry>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (DLFileEntry dlFileEntry : list) {
					if ((custom1ImageId != dlFileEntry.getCustom1ImageId())) {
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

			query.append(_SQL_SELECT_DLFILEENTRY_WHERE);

			query.append(_FINDER_COLUMN_CUSTOM1IMAGEID_CUSTOM1IMAGEID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(DLFileEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(custom1ImageId);

				if (!pagination) {
					list = (List<DLFileEntry>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<DLFileEntry>)QueryUtil.list(q, getDialect(),
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
	 * Returns the first document library file entry in the ordered set where custom1ImageId = &#63;.
	 *
	 * @param custom1ImageId the custom1 image ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching document library file entry
	 * @throws NoSuchFileEntryException if a matching document library file entry could not be found
	 */
	@Override
	public DLFileEntry findByCustom1ImageId_First(long custom1ImageId,
		OrderByComparator<DLFileEntry> orderByComparator)
		throws NoSuchFileEntryException {
		DLFileEntry dlFileEntry = fetchByCustom1ImageId_First(custom1ImageId,
				orderByComparator);

		if (dlFileEntry != null) {
			return dlFileEntry;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("custom1ImageId=");
		msg.append(custom1ImageId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchFileEntryException(msg.toString());
	}

	/**
	 * Returns the first document library file entry in the ordered set where custom1ImageId = &#63;.
	 *
	 * @param custom1ImageId the custom1 image ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching document library file entry, or <code>null</code> if a matching document library file entry could not be found
	 */
	@Override
	public DLFileEntry fetchByCustom1ImageId_First(long custom1ImageId,
		OrderByComparator<DLFileEntry> orderByComparator) {
		List<DLFileEntry> list = findByCustom1ImageId(custom1ImageId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last document library file entry in the ordered set where custom1ImageId = &#63;.
	 *
	 * @param custom1ImageId the custom1 image ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching document library file entry
	 * @throws NoSuchFileEntryException if a matching document library file entry could not be found
	 */
	@Override
	public DLFileEntry findByCustom1ImageId_Last(long custom1ImageId,
		OrderByComparator<DLFileEntry> orderByComparator)
		throws NoSuchFileEntryException {
		DLFileEntry dlFileEntry = fetchByCustom1ImageId_Last(custom1ImageId,
				orderByComparator);

		if (dlFileEntry != null) {
			return dlFileEntry;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("custom1ImageId=");
		msg.append(custom1ImageId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchFileEntryException(msg.toString());
	}

	/**
	 * Returns the last document library file entry in the ordered set where custom1ImageId = &#63;.
	 *
	 * @param custom1ImageId the custom1 image ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching document library file entry, or <code>null</code> if a matching document library file entry could not be found
	 */
	@Override
	public DLFileEntry fetchByCustom1ImageId_Last(long custom1ImageId,
		OrderByComparator<DLFileEntry> orderByComparator) {
		int count = countByCustom1ImageId(custom1ImageId);

		if (count == 0) {
			return null;
		}

		List<DLFileEntry> list = findByCustom1ImageId(custom1ImageId,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the document library file entries before and after the current document library file entry in the ordered set where custom1ImageId = &#63;.
	 *
	 * @param fileEntryId the primary key of the current document library file entry
	 * @param custom1ImageId the custom1 image ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next document library file entry
	 * @throws NoSuchFileEntryException if a document library file entry with the primary key could not be found
	 */
	@Override
	public DLFileEntry[] findByCustom1ImageId_PrevAndNext(long fileEntryId,
		long custom1ImageId, OrderByComparator<DLFileEntry> orderByComparator)
		throws NoSuchFileEntryException {
		DLFileEntry dlFileEntry = findByPrimaryKey(fileEntryId);

		Session session = null;

		try {
			session = openSession();

			DLFileEntry[] array = new DLFileEntryImpl[3];

			array[0] = getByCustom1ImageId_PrevAndNext(session, dlFileEntry,
					custom1ImageId, orderByComparator, true);

			array[1] = dlFileEntry;

			array[2] = getByCustom1ImageId_PrevAndNext(session, dlFileEntry,
					custom1ImageId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected DLFileEntry getByCustom1ImageId_PrevAndNext(Session session,
		DLFileEntry dlFileEntry, long custom1ImageId,
		OrderByComparator<DLFileEntry> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(4 +
					(orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_DLFILEENTRY_WHERE);

		query.append(_FINDER_COLUMN_CUSTOM1IMAGEID_CUSTOM1IMAGEID_2);

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
			query.append(DLFileEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(custom1ImageId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(dlFileEntry);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<DLFileEntry> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the document library file entries where custom1ImageId = &#63; from the database.
	 *
	 * @param custom1ImageId the custom1 image ID
	 */
	@Override
	public void removeByCustom1ImageId(long custom1ImageId) {
		for (DLFileEntry dlFileEntry : findByCustom1ImageId(custom1ImageId,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(dlFileEntry);
		}
	}

	/**
	 * Returns the number of document library file entries where custom1ImageId = &#63;.
	 *
	 * @param custom1ImageId the custom1 image ID
	 * @return the number of matching document library file entries
	 */
	@Override
	public int countByCustom1ImageId(long custom1ImageId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_CUSTOM1IMAGEID;

		Object[] finderArgs = new Object[] { custom1ImageId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_DLFILEENTRY_WHERE);

			query.append(_FINDER_COLUMN_CUSTOM1IMAGEID_CUSTOM1IMAGEID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(custom1ImageId);

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

	private static final String _FINDER_COLUMN_CUSTOM1IMAGEID_CUSTOM1IMAGEID_2 = "dlFileEntry.custom1ImageId = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_CUSTOM2IMAGEID =
		new FinderPath(DLFileEntryModelImpl.ENTITY_CACHE_ENABLED,
			DLFileEntryModelImpl.FINDER_CACHE_ENABLED, DLFileEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByCustom2ImageId",
			new String[] {
				Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_CUSTOM2IMAGEID =
		new FinderPath(DLFileEntryModelImpl.ENTITY_CACHE_ENABLED,
			DLFileEntryModelImpl.FINDER_CACHE_ENABLED, DLFileEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByCustom2ImageId",
			new String[] { Long.class.getName() },
			DLFileEntryModelImpl.CUSTOM2IMAGEID_COLUMN_BITMASK |
			DLFileEntryModelImpl.FOLDERID_COLUMN_BITMASK |
			DLFileEntryModelImpl.NAME_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_CUSTOM2IMAGEID = new FinderPath(DLFileEntryModelImpl.ENTITY_CACHE_ENABLED,
			DLFileEntryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByCustom2ImageId",
			new String[] { Long.class.getName() });

	/**
	 * Returns all the document library file entries where custom2ImageId = &#63;.
	 *
	 * @param custom2ImageId the custom2 image ID
	 * @return the matching document library file entries
	 */
	@Override
	public List<DLFileEntry> findByCustom2ImageId(long custom2ImageId) {
		return findByCustom2ImageId(custom2ImageId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the document library file entries where custom2ImageId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFileEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param custom2ImageId the custom2 image ID
	 * @param start the lower bound of the range of document library file entries
	 * @param end the upper bound of the range of document library file entries (not inclusive)
	 * @return the range of matching document library file entries
	 */
	@Override
	public List<DLFileEntry> findByCustom2ImageId(long custom2ImageId,
		int start, int end) {
		return findByCustom2ImageId(custom2ImageId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the document library file entries where custom2ImageId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFileEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param custom2ImageId the custom2 image ID
	 * @param start the lower bound of the range of document library file entries
	 * @param end the upper bound of the range of document library file entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching document library file entries
	 */
	@Override
	public List<DLFileEntry> findByCustom2ImageId(long custom2ImageId,
		int start, int end, OrderByComparator<DLFileEntry> orderByComparator) {
		return findByCustom2ImageId(custom2ImageId, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the document library file entries where custom2ImageId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFileEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param custom2ImageId the custom2 image ID
	 * @param start the lower bound of the range of document library file entries
	 * @param end the upper bound of the range of document library file entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching document library file entries
	 */
	@Override
	public List<DLFileEntry> findByCustom2ImageId(long custom2ImageId,
		int start, int end, OrderByComparator<DLFileEntry> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_CUSTOM2IMAGEID;
			finderArgs = new Object[] { custom2ImageId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_CUSTOM2IMAGEID;
			finderArgs = new Object[] {
					custom2ImageId,
					
					start, end, orderByComparator
				};
		}

		List<DLFileEntry> list = null;

		if (retrieveFromCache) {
			list = (List<DLFileEntry>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (DLFileEntry dlFileEntry : list) {
					if ((custom2ImageId != dlFileEntry.getCustom2ImageId())) {
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

			query.append(_SQL_SELECT_DLFILEENTRY_WHERE);

			query.append(_FINDER_COLUMN_CUSTOM2IMAGEID_CUSTOM2IMAGEID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(DLFileEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(custom2ImageId);

				if (!pagination) {
					list = (List<DLFileEntry>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<DLFileEntry>)QueryUtil.list(q, getDialect(),
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
	 * Returns the first document library file entry in the ordered set where custom2ImageId = &#63;.
	 *
	 * @param custom2ImageId the custom2 image ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching document library file entry
	 * @throws NoSuchFileEntryException if a matching document library file entry could not be found
	 */
	@Override
	public DLFileEntry findByCustom2ImageId_First(long custom2ImageId,
		OrderByComparator<DLFileEntry> orderByComparator)
		throws NoSuchFileEntryException {
		DLFileEntry dlFileEntry = fetchByCustom2ImageId_First(custom2ImageId,
				orderByComparator);

		if (dlFileEntry != null) {
			return dlFileEntry;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("custom2ImageId=");
		msg.append(custom2ImageId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchFileEntryException(msg.toString());
	}

	/**
	 * Returns the first document library file entry in the ordered set where custom2ImageId = &#63;.
	 *
	 * @param custom2ImageId the custom2 image ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching document library file entry, or <code>null</code> if a matching document library file entry could not be found
	 */
	@Override
	public DLFileEntry fetchByCustom2ImageId_First(long custom2ImageId,
		OrderByComparator<DLFileEntry> orderByComparator) {
		List<DLFileEntry> list = findByCustom2ImageId(custom2ImageId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last document library file entry in the ordered set where custom2ImageId = &#63;.
	 *
	 * @param custom2ImageId the custom2 image ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching document library file entry
	 * @throws NoSuchFileEntryException if a matching document library file entry could not be found
	 */
	@Override
	public DLFileEntry findByCustom2ImageId_Last(long custom2ImageId,
		OrderByComparator<DLFileEntry> orderByComparator)
		throws NoSuchFileEntryException {
		DLFileEntry dlFileEntry = fetchByCustom2ImageId_Last(custom2ImageId,
				orderByComparator);

		if (dlFileEntry != null) {
			return dlFileEntry;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("custom2ImageId=");
		msg.append(custom2ImageId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchFileEntryException(msg.toString());
	}

	/**
	 * Returns the last document library file entry in the ordered set where custom2ImageId = &#63;.
	 *
	 * @param custom2ImageId the custom2 image ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching document library file entry, or <code>null</code> if a matching document library file entry could not be found
	 */
	@Override
	public DLFileEntry fetchByCustom2ImageId_Last(long custom2ImageId,
		OrderByComparator<DLFileEntry> orderByComparator) {
		int count = countByCustom2ImageId(custom2ImageId);

		if (count == 0) {
			return null;
		}

		List<DLFileEntry> list = findByCustom2ImageId(custom2ImageId,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the document library file entries before and after the current document library file entry in the ordered set where custom2ImageId = &#63;.
	 *
	 * @param fileEntryId the primary key of the current document library file entry
	 * @param custom2ImageId the custom2 image ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next document library file entry
	 * @throws NoSuchFileEntryException if a document library file entry with the primary key could not be found
	 */
	@Override
	public DLFileEntry[] findByCustom2ImageId_PrevAndNext(long fileEntryId,
		long custom2ImageId, OrderByComparator<DLFileEntry> orderByComparator)
		throws NoSuchFileEntryException {
		DLFileEntry dlFileEntry = findByPrimaryKey(fileEntryId);

		Session session = null;

		try {
			session = openSession();

			DLFileEntry[] array = new DLFileEntryImpl[3];

			array[0] = getByCustom2ImageId_PrevAndNext(session, dlFileEntry,
					custom2ImageId, orderByComparator, true);

			array[1] = dlFileEntry;

			array[2] = getByCustom2ImageId_PrevAndNext(session, dlFileEntry,
					custom2ImageId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected DLFileEntry getByCustom2ImageId_PrevAndNext(Session session,
		DLFileEntry dlFileEntry, long custom2ImageId,
		OrderByComparator<DLFileEntry> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(4 +
					(orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_DLFILEENTRY_WHERE);

		query.append(_FINDER_COLUMN_CUSTOM2IMAGEID_CUSTOM2IMAGEID_2);

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
			query.append(DLFileEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(custom2ImageId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(dlFileEntry);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<DLFileEntry> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the document library file entries where custom2ImageId = &#63; from the database.
	 *
	 * @param custom2ImageId the custom2 image ID
	 */
	@Override
	public void removeByCustom2ImageId(long custom2ImageId) {
		for (DLFileEntry dlFileEntry : findByCustom2ImageId(custom2ImageId,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(dlFileEntry);
		}
	}

	/**
	 * Returns the number of document library file entries where custom2ImageId = &#63;.
	 *
	 * @param custom2ImageId the custom2 image ID
	 * @return the number of matching document library file entries
	 */
	@Override
	public int countByCustom2ImageId(long custom2ImageId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_CUSTOM2IMAGEID;

		Object[] finderArgs = new Object[] { custom2ImageId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_DLFILEENTRY_WHERE);

			query.append(_FINDER_COLUMN_CUSTOM2IMAGEID_CUSTOM2IMAGEID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(custom2ImageId);

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

	private static final String _FINDER_COLUMN_CUSTOM2IMAGEID_CUSTOM2IMAGEID_2 = "dlFileEntry.custom2ImageId = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_G_U = new FinderPath(DLFileEntryModelImpl.ENTITY_CACHE_ENABLED,
			DLFileEntryModelImpl.FINDER_CACHE_ENABLED, DLFileEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_U",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_U = new FinderPath(DLFileEntryModelImpl.ENTITY_CACHE_ENABLED,
			DLFileEntryModelImpl.FINDER_CACHE_ENABLED, DLFileEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByG_U",
			new String[] { Long.class.getName(), Long.class.getName() },
			DLFileEntryModelImpl.GROUPID_COLUMN_BITMASK |
			DLFileEntryModelImpl.USERID_COLUMN_BITMASK |
			DLFileEntryModelImpl.FOLDERID_COLUMN_BITMASK |
			DLFileEntryModelImpl.NAME_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_G_U = new FinderPath(DLFileEntryModelImpl.ENTITY_CACHE_ENABLED,
			DLFileEntryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_U",
			new String[] { Long.class.getName(), Long.class.getName() });

	/**
	 * Returns all the document library file entries where groupId = &#63; and userId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @return the matching document library file entries
	 */
	@Override
	public List<DLFileEntry> findByG_U(long groupId, long userId) {
		return findByG_U(groupId, userId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the document library file entries where groupId = &#63; and userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFileEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param start the lower bound of the range of document library file entries
	 * @param end the upper bound of the range of document library file entries (not inclusive)
	 * @return the range of matching document library file entries
	 */
	@Override
	public List<DLFileEntry> findByG_U(long groupId, long userId, int start,
		int end) {
		return findByG_U(groupId, userId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the document library file entries where groupId = &#63; and userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFileEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param start the lower bound of the range of document library file entries
	 * @param end the upper bound of the range of document library file entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching document library file entries
	 */
	@Override
	public List<DLFileEntry> findByG_U(long groupId, long userId, int start,
		int end, OrderByComparator<DLFileEntry> orderByComparator) {
		return findByG_U(groupId, userId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the document library file entries where groupId = &#63; and userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFileEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param start the lower bound of the range of document library file entries
	 * @param end the upper bound of the range of document library file entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching document library file entries
	 */
	@Override
	public List<DLFileEntry> findByG_U(long groupId, long userId, int start,
		int end, OrderByComparator<DLFileEntry> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_U;
			finderArgs = new Object[] { groupId, userId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_G_U;
			finderArgs = new Object[] {
					groupId, userId,
					
					start, end, orderByComparator
				};
		}

		List<DLFileEntry> list = null;

		if (retrieveFromCache) {
			list = (List<DLFileEntry>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (DLFileEntry dlFileEntry : list) {
					if ((groupId != dlFileEntry.getGroupId()) ||
							(userId != dlFileEntry.getUserId())) {
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

			query.append(_SQL_SELECT_DLFILEENTRY_WHERE);

			query.append(_FINDER_COLUMN_G_U_GROUPID_2);

			query.append(_FINDER_COLUMN_G_U_USERID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(DLFileEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(userId);

				if (!pagination) {
					list = (List<DLFileEntry>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<DLFileEntry>)QueryUtil.list(q, getDialect(),
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
	 * Returns the first document library file entry in the ordered set where groupId = &#63; and userId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching document library file entry
	 * @throws NoSuchFileEntryException if a matching document library file entry could not be found
	 */
	@Override
	public DLFileEntry findByG_U_First(long groupId, long userId,
		OrderByComparator<DLFileEntry> orderByComparator)
		throws NoSuchFileEntryException {
		DLFileEntry dlFileEntry = fetchByG_U_First(groupId, userId,
				orderByComparator);

		if (dlFileEntry != null) {
			return dlFileEntry;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", userId=");
		msg.append(userId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchFileEntryException(msg.toString());
	}

	/**
	 * Returns the first document library file entry in the ordered set where groupId = &#63; and userId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching document library file entry, or <code>null</code> if a matching document library file entry could not be found
	 */
	@Override
	public DLFileEntry fetchByG_U_First(long groupId, long userId,
		OrderByComparator<DLFileEntry> orderByComparator) {
		List<DLFileEntry> list = findByG_U(groupId, userId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last document library file entry in the ordered set where groupId = &#63; and userId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching document library file entry
	 * @throws NoSuchFileEntryException if a matching document library file entry could not be found
	 */
	@Override
	public DLFileEntry findByG_U_Last(long groupId, long userId,
		OrderByComparator<DLFileEntry> orderByComparator)
		throws NoSuchFileEntryException {
		DLFileEntry dlFileEntry = fetchByG_U_Last(groupId, userId,
				orderByComparator);

		if (dlFileEntry != null) {
			return dlFileEntry;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", userId=");
		msg.append(userId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchFileEntryException(msg.toString());
	}

	/**
	 * Returns the last document library file entry in the ordered set where groupId = &#63; and userId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching document library file entry, or <code>null</code> if a matching document library file entry could not be found
	 */
	@Override
	public DLFileEntry fetchByG_U_Last(long groupId, long userId,
		OrderByComparator<DLFileEntry> orderByComparator) {
		int count = countByG_U(groupId, userId);

		if (count == 0) {
			return null;
		}

		List<DLFileEntry> list = findByG_U(groupId, userId, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the document library file entries before and after the current document library file entry in the ordered set where groupId = &#63; and userId = &#63;.
	 *
	 * @param fileEntryId the primary key of the current document library file entry
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next document library file entry
	 * @throws NoSuchFileEntryException if a document library file entry with the primary key could not be found
	 */
	@Override
	public DLFileEntry[] findByG_U_PrevAndNext(long fileEntryId, long groupId,
		long userId, OrderByComparator<DLFileEntry> orderByComparator)
		throws NoSuchFileEntryException {
		DLFileEntry dlFileEntry = findByPrimaryKey(fileEntryId);

		Session session = null;

		try {
			session = openSession();

			DLFileEntry[] array = new DLFileEntryImpl[3];

			array[0] = getByG_U_PrevAndNext(session, dlFileEntry, groupId,
					userId, orderByComparator, true);

			array[1] = dlFileEntry;

			array[2] = getByG_U_PrevAndNext(session, dlFileEntry, groupId,
					userId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected DLFileEntry getByG_U_PrevAndNext(Session session,
		DLFileEntry dlFileEntry, long groupId, long userId,
		OrderByComparator<DLFileEntry> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(5 +
					(orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(4);
		}

		query.append(_SQL_SELECT_DLFILEENTRY_WHERE);

		query.append(_FINDER_COLUMN_G_U_GROUPID_2);

		query.append(_FINDER_COLUMN_G_U_USERID_2);

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
			query.append(DLFileEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		qPos.add(userId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(dlFileEntry);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<DLFileEntry> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the document library file entries that the user has permission to view where groupId = &#63; and userId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @return the matching document library file entries that the user has permission to view
	 */
	@Override
	public List<DLFileEntry> filterFindByG_U(long groupId, long userId) {
		return filterFindByG_U(groupId, userId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the document library file entries that the user has permission to view where groupId = &#63; and userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFileEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param start the lower bound of the range of document library file entries
	 * @param end the upper bound of the range of document library file entries (not inclusive)
	 * @return the range of matching document library file entries that the user has permission to view
	 */
	@Override
	public List<DLFileEntry> filterFindByG_U(long groupId, long userId,
		int start, int end) {
		return filterFindByG_U(groupId, userId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the document library file entries that the user has permissions to view where groupId = &#63; and userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFileEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param start the lower bound of the range of document library file entries
	 * @param end the upper bound of the range of document library file entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching document library file entries that the user has permission to view
	 */
	@Override
	public List<DLFileEntry> filterFindByG_U(long groupId, long userId,
		int start, int end, OrderByComparator<DLFileEntry> orderByComparator) {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_U(groupId, userId, start, end, orderByComparator);
		}

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(4 +
					(orderByComparator.getOrderByFields().length * 2));
		}
		else {
			query = new StringBundler(5);
		}

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_DLFILEENTRY_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_DLFILEENTRY_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_G_U_GROUPID_2);

		query.append(_FINDER_COLUMN_G_U_USERID_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_DLFILEENTRY_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			if (getDB().isSupportsInlineDistinct()) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator, true);
			}
			else {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_TABLE,
					orderByComparator, true);
			}
		}
		else {
			if (getDB().isSupportsInlineDistinct()) {
				query.append(DLFileEntryModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(DLFileEntryModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				DLFileEntry.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				q.addEntity(_FILTER_ENTITY_ALIAS, DLFileEntryImpl.class);
			}
			else {
				q.addEntity(_FILTER_ENTITY_TABLE, DLFileEntryImpl.class);
			}

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			qPos.add(userId);

			return (List<DLFileEntry>)QueryUtil.list(q, getDialect(), start, end);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Returns the document library file entries before and after the current document library file entry in the ordered set of document library file entries that the user has permission to view where groupId = &#63; and userId = &#63;.
	 *
	 * @param fileEntryId the primary key of the current document library file entry
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next document library file entry
	 * @throws NoSuchFileEntryException if a document library file entry with the primary key could not be found
	 */
	@Override
	public DLFileEntry[] filterFindByG_U_PrevAndNext(long fileEntryId,
		long groupId, long userId,
		OrderByComparator<DLFileEntry> orderByComparator)
		throws NoSuchFileEntryException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_U_PrevAndNext(fileEntryId, groupId, userId,
				orderByComparator);
		}

		DLFileEntry dlFileEntry = findByPrimaryKey(fileEntryId);

		Session session = null;

		try {
			session = openSession();

			DLFileEntry[] array = new DLFileEntryImpl[3];

			array[0] = filterGetByG_U_PrevAndNext(session, dlFileEntry,
					groupId, userId, orderByComparator, true);

			array[1] = dlFileEntry;

			array[2] = filterGetByG_U_PrevAndNext(session, dlFileEntry,
					groupId, userId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected DLFileEntry filterGetByG_U_PrevAndNext(Session session,
		DLFileEntry dlFileEntry, long groupId, long userId,
		OrderByComparator<DLFileEntry> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(5);
		}

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_DLFILEENTRY_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_DLFILEENTRY_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_G_U_GROUPID_2);

		query.append(_FINDER_COLUMN_G_U_USERID_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_DLFILEENTRY_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			String[] orderByConditionFields = orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				query.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				if (getDB().isSupportsInlineDistinct()) {
					query.append(_ORDER_BY_ENTITY_ALIAS);
				}
				else {
					query.append(_ORDER_BY_ENTITY_TABLE);
				}

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
				if (getDB().isSupportsInlineDistinct()) {
					query.append(_ORDER_BY_ENTITY_ALIAS);
				}
				else {
					query.append(_ORDER_BY_ENTITY_TABLE);
				}

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
			if (getDB().isSupportsInlineDistinct()) {
				query.append(DLFileEntryModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(DLFileEntryModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				DLFileEntry.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		SQLQuery q = session.createSynchronizedSQLQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		if (getDB().isSupportsInlineDistinct()) {
			q.addEntity(_FILTER_ENTITY_ALIAS, DLFileEntryImpl.class);
		}
		else {
			q.addEntity(_FILTER_ENTITY_TABLE, DLFileEntryImpl.class);
		}

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		qPos.add(userId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(dlFileEntry);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<DLFileEntry> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the document library file entries where groupId = &#63; and userId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 */
	@Override
	public void removeByG_U(long groupId, long userId) {
		for (DLFileEntry dlFileEntry : findByG_U(groupId, userId,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(dlFileEntry);
		}
	}

	/**
	 * Returns the number of document library file entries where groupId = &#63; and userId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @return the number of matching document library file entries
	 */
	@Override
	public int countByG_U(long groupId, long userId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_G_U;

		Object[] finderArgs = new Object[] { groupId, userId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_DLFILEENTRY_WHERE);

			query.append(_FINDER_COLUMN_G_U_GROUPID_2);

			query.append(_FINDER_COLUMN_G_U_USERID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(userId);

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
	 * Returns the number of document library file entries that the user has permission to view where groupId = &#63; and userId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @return the number of matching document library file entries that the user has permission to view
	 */
	@Override
	public int filterCountByG_U(long groupId, long userId) {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByG_U(groupId, userId);
		}

		StringBundler query = new StringBundler(3);

		query.append(_FILTER_SQL_COUNT_DLFILEENTRY_WHERE);

		query.append(_FINDER_COLUMN_G_U_GROUPID_2);

		query.append(_FINDER_COLUMN_G_U_USERID_2);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				DLFileEntry.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME,
				com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			qPos.add(userId);

			Long count = (Long)q.uniqueResult();

			return count.intValue();
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	private static final String _FINDER_COLUMN_G_U_GROUPID_2 = "dlFileEntry.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_U_USERID_2 = "dlFileEntry.userId = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_G_F = new FinderPath(DLFileEntryModelImpl.ENTITY_CACHE_ENABLED,
			DLFileEntryModelImpl.FINDER_CACHE_ENABLED, DLFileEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_F",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_F = new FinderPath(DLFileEntryModelImpl.ENTITY_CACHE_ENABLED,
			DLFileEntryModelImpl.FINDER_CACHE_ENABLED, DLFileEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByG_F",
			new String[] { Long.class.getName(), Long.class.getName() },
			DLFileEntryModelImpl.GROUPID_COLUMN_BITMASK |
			DLFileEntryModelImpl.FOLDERID_COLUMN_BITMASK |
			DLFileEntryModelImpl.NAME_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_G_F = new FinderPath(DLFileEntryModelImpl.ENTITY_CACHE_ENABLED,
			DLFileEntryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_F",
			new String[] { Long.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_COUNT_BY_G_F = new FinderPath(DLFileEntryModelImpl.ENTITY_CACHE_ENABLED,
			DLFileEntryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "countByG_F",
			new String[] { Long.class.getName(), Long.class.getName() });

	/**
	 * Returns all the document library file entries where groupId = &#63; and folderId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @return the matching document library file entries
	 */
	@Override
	public List<DLFileEntry> findByG_F(long groupId, long folderId) {
		return findByG_F(groupId, folderId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the document library file entries where groupId = &#63; and folderId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFileEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param start the lower bound of the range of document library file entries
	 * @param end the upper bound of the range of document library file entries (not inclusive)
	 * @return the range of matching document library file entries
	 */
	@Override
	public List<DLFileEntry> findByG_F(long groupId, long folderId, int start,
		int end) {
		return findByG_F(groupId, folderId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the document library file entries where groupId = &#63; and folderId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFileEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param start the lower bound of the range of document library file entries
	 * @param end the upper bound of the range of document library file entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching document library file entries
	 */
	@Override
	public List<DLFileEntry> findByG_F(long groupId, long folderId, int start,
		int end, OrderByComparator<DLFileEntry> orderByComparator) {
		return findByG_F(groupId, folderId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the document library file entries where groupId = &#63; and folderId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFileEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param start the lower bound of the range of document library file entries
	 * @param end the upper bound of the range of document library file entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching document library file entries
	 */
	@Override
	public List<DLFileEntry> findByG_F(long groupId, long folderId, int start,
		int end, OrderByComparator<DLFileEntry> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_F;
			finderArgs = new Object[] { groupId, folderId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_G_F;
			finderArgs = new Object[] {
					groupId, folderId,
					
					start, end, orderByComparator
				};
		}

		List<DLFileEntry> list = null;

		if (retrieveFromCache) {
			list = (List<DLFileEntry>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (DLFileEntry dlFileEntry : list) {
					if ((groupId != dlFileEntry.getGroupId()) ||
							(folderId != dlFileEntry.getFolderId())) {
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

			query.append(_SQL_SELECT_DLFILEENTRY_WHERE);

			query.append(_FINDER_COLUMN_G_F_GROUPID_2);

			query.append(_FINDER_COLUMN_G_F_FOLDERID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(DLFileEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(folderId);

				if (!pagination) {
					list = (List<DLFileEntry>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<DLFileEntry>)QueryUtil.list(q, getDialect(),
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
	 * Returns the first document library file entry in the ordered set where groupId = &#63; and folderId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching document library file entry
	 * @throws NoSuchFileEntryException if a matching document library file entry could not be found
	 */
	@Override
	public DLFileEntry findByG_F_First(long groupId, long folderId,
		OrderByComparator<DLFileEntry> orderByComparator)
		throws NoSuchFileEntryException {
		DLFileEntry dlFileEntry = fetchByG_F_First(groupId, folderId,
				orderByComparator);

		if (dlFileEntry != null) {
			return dlFileEntry;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", folderId=");
		msg.append(folderId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchFileEntryException(msg.toString());
	}

	/**
	 * Returns the first document library file entry in the ordered set where groupId = &#63; and folderId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching document library file entry, or <code>null</code> if a matching document library file entry could not be found
	 */
	@Override
	public DLFileEntry fetchByG_F_First(long groupId, long folderId,
		OrderByComparator<DLFileEntry> orderByComparator) {
		List<DLFileEntry> list = findByG_F(groupId, folderId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last document library file entry in the ordered set where groupId = &#63; and folderId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching document library file entry
	 * @throws NoSuchFileEntryException if a matching document library file entry could not be found
	 */
	@Override
	public DLFileEntry findByG_F_Last(long groupId, long folderId,
		OrderByComparator<DLFileEntry> orderByComparator)
		throws NoSuchFileEntryException {
		DLFileEntry dlFileEntry = fetchByG_F_Last(groupId, folderId,
				orderByComparator);

		if (dlFileEntry != null) {
			return dlFileEntry;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", folderId=");
		msg.append(folderId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchFileEntryException(msg.toString());
	}

	/**
	 * Returns the last document library file entry in the ordered set where groupId = &#63; and folderId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching document library file entry, or <code>null</code> if a matching document library file entry could not be found
	 */
	@Override
	public DLFileEntry fetchByG_F_Last(long groupId, long folderId,
		OrderByComparator<DLFileEntry> orderByComparator) {
		int count = countByG_F(groupId, folderId);

		if (count == 0) {
			return null;
		}

		List<DLFileEntry> list = findByG_F(groupId, folderId, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the document library file entries before and after the current document library file entry in the ordered set where groupId = &#63; and folderId = &#63;.
	 *
	 * @param fileEntryId the primary key of the current document library file entry
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next document library file entry
	 * @throws NoSuchFileEntryException if a document library file entry with the primary key could not be found
	 */
	@Override
	public DLFileEntry[] findByG_F_PrevAndNext(long fileEntryId, long groupId,
		long folderId, OrderByComparator<DLFileEntry> orderByComparator)
		throws NoSuchFileEntryException {
		DLFileEntry dlFileEntry = findByPrimaryKey(fileEntryId);

		Session session = null;

		try {
			session = openSession();

			DLFileEntry[] array = new DLFileEntryImpl[3];

			array[0] = getByG_F_PrevAndNext(session, dlFileEntry, groupId,
					folderId, orderByComparator, true);

			array[1] = dlFileEntry;

			array[2] = getByG_F_PrevAndNext(session, dlFileEntry, groupId,
					folderId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected DLFileEntry getByG_F_PrevAndNext(Session session,
		DLFileEntry dlFileEntry, long groupId, long folderId,
		OrderByComparator<DLFileEntry> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(5 +
					(orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(4);
		}

		query.append(_SQL_SELECT_DLFILEENTRY_WHERE);

		query.append(_FINDER_COLUMN_G_F_GROUPID_2);

		query.append(_FINDER_COLUMN_G_F_FOLDERID_2);

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
			query.append(DLFileEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		qPos.add(folderId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(dlFileEntry);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<DLFileEntry> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the document library file entries that the user has permission to view where groupId = &#63; and folderId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @return the matching document library file entries that the user has permission to view
	 */
	@Override
	public List<DLFileEntry> filterFindByG_F(long groupId, long folderId) {
		return filterFindByG_F(groupId, folderId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the document library file entries that the user has permission to view where groupId = &#63; and folderId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFileEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param start the lower bound of the range of document library file entries
	 * @param end the upper bound of the range of document library file entries (not inclusive)
	 * @return the range of matching document library file entries that the user has permission to view
	 */
	@Override
	public List<DLFileEntry> filterFindByG_F(long groupId, long folderId,
		int start, int end) {
		return filterFindByG_F(groupId, folderId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the document library file entries that the user has permissions to view where groupId = &#63; and folderId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFileEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param start the lower bound of the range of document library file entries
	 * @param end the upper bound of the range of document library file entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching document library file entries that the user has permission to view
	 */
	@Override
	public List<DLFileEntry> filterFindByG_F(long groupId, long folderId,
		int start, int end, OrderByComparator<DLFileEntry> orderByComparator) {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_F(groupId, folderId, start, end, orderByComparator);
		}

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(4 +
					(orderByComparator.getOrderByFields().length * 2));
		}
		else {
			query = new StringBundler(5);
		}

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_DLFILEENTRY_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_DLFILEENTRY_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_G_F_GROUPID_2);

		query.append(_FINDER_COLUMN_G_F_FOLDERID_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_DLFILEENTRY_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			if (getDB().isSupportsInlineDistinct()) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator, true);
			}
			else {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_TABLE,
					orderByComparator, true);
			}
		}
		else {
			if (getDB().isSupportsInlineDistinct()) {
				query.append(DLFileEntryModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(DLFileEntryModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				DLFileEntry.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				q.addEntity(_FILTER_ENTITY_ALIAS, DLFileEntryImpl.class);
			}
			else {
				q.addEntity(_FILTER_ENTITY_TABLE, DLFileEntryImpl.class);
			}

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			qPos.add(folderId);

			return (List<DLFileEntry>)QueryUtil.list(q, getDialect(), start, end);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Returns the document library file entries before and after the current document library file entry in the ordered set of document library file entries that the user has permission to view where groupId = &#63; and folderId = &#63;.
	 *
	 * @param fileEntryId the primary key of the current document library file entry
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next document library file entry
	 * @throws NoSuchFileEntryException if a document library file entry with the primary key could not be found
	 */
	@Override
	public DLFileEntry[] filterFindByG_F_PrevAndNext(long fileEntryId,
		long groupId, long folderId,
		OrderByComparator<DLFileEntry> orderByComparator)
		throws NoSuchFileEntryException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_F_PrevAndNext(fileEntryId, groupId, folderId,
				orderByComparator);
		}

		DLFileEntry dlFileEntry = findByPrimaryKey(fileEntryId);

		Session session = null;

		try {
			session = openSession();

			DLFileEntry[] array = new DLFileEntryImpl[3];

			array[0] = filterGetByG_F_PrevAndNext(session, dlFileEntry,
					groupId, folderId, orderByComparator, true);

			array[1] = dlFileEntry;

			array[2] = filterGetByG_F_PrevAndNext(session, dlFileEntry,
					groupId, folderId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected DLFileEntry filterGetByG_F_PrevAndNext(Session session,
		DLFileEntry dlFileEntry, long groupId, long folderId,
		OrderByComparator<DLFileEntry> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(5);
		}

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_DLFILEENTRY_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_DLFILEENTRY_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_G_F_GROUPID_2);

		query.append(_FINDER_COLUMN_G_F_FOLDERID_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_DLFILEENTRY_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			String[] orderByConditionFields = orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				query.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				if (getDB().isSupportsInlineDistinct()) {
					query.append(_ORDER_BY_ENTITY_ALIAS);
				}
				else {
					query.append(_ORDER_BY_ENTITY_TABLE);
				}

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
				if (getDB().isSupportsInlineDistinct()) {
					query.append(_ORDER_BY_ENTITY_ALIAS);
				}
				else {
					query.append(_ORDER_BY_ENTITY_TABLE);
				}

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
			if (getDB().isSupportsInlineDistinct()) {
				query.append(DLFileEntryModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(DLFileEntryModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				DLFileEntry.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		SQLQuery q = session.createSynchronizedSQLQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		if (getDB().isSupportsInlineDistinct()) {
			q.addEntity(_FILTER_ENTITY_ALIAS, DLFileEntryImpl.class);
		}
		else {
			q.addEntity(_FILTER_ENTITY_TABLE, DLFileEntryImpl.class);
		}

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		qPos.add(folderId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(dlFileEntry);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<DLFileEntry> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the document library file entries that the user has permission to view where groupId = &#63; and folderId = any &#63;.
	 *
	 * @param groupId the group ID
	 * @param folderIds the folder IDs
	 * @return the matching document library file entries that the user has permission to view
	 */
	@Override
	public List<DLFileEntry> filterFindByG_F(long groupId, long[] folderIds) {
		return filterFindByG_F(groupId, folderIds, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the document library file entries that the user has permission to view where groupId = &#63; and folderId = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFileEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param folderIds the folder IDs
	 * @param start the lower bound of the range of document library file entries
	 * @param end the upper bound of the range of document library file entries (not inclusive)
	 * @return the range of matching document library file entries that the user has permission to view
	 */
	@Override
	public List<DLFileEntry> filterFindByG_F(long groupId, long[] folderIds,
		int start, int end) {
		return filterFindByG_F(groupId, folderIds, start, end, null);
	}

	/**
	 * Returns an ordered range of all the document library file entries that the user has permission to view where groupId = &#63; and folderId = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFileEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param folderIds the folder IDs
	 * @param start the lower bound of the range of document library file entries
	 * @param end the upper bound of the range of document library file entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching document library file entries that the user has permission to view
	 */
	@Override
	public List<DLFileEntry> filterFindByG_F(long groupId, long[] folderIds,
		int start, int end, OrderByComparator<DLFileEntry> orderByComparator) {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_F(groupId, folderIds, start, end, orderByComparator);
		}

		if (folderIds == null) {
			folderIds = new long[0];
		}
		else if (folderIds.length > 1) {
			folderIds = ArrayUtil.unique(folderIds);

			Arrays.sort(folderIds);
		}

		StringBundler query = new StringBundler();

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_DLFILEENTRY_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_DLFILEENTRY_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_G_F_GROUPID_2);

		if (folderIds.length > 0) {
			query.append(StringPool.OPEN_PARENTHESIS);

			query.append(_FINDER_COLUMN_G_F_FOLDERID_7);

			query.append(StringUtil.merge(folderIds));

			query.append(StringPool.CLOSE_PARENTHESIS);

			query.append(StringPool.CLOSE_PARENTHESIS);
		}

		query.setStringAt(removeConjunction(query.stringAt(query.index() - 1)),
			query.index() - 1);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_DLFILEENTRY_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			if (getDB().isSupportsInlineDistinct()) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator, true);
			}
			else {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_TABLE,
					orderByComparator, true);
			}
		}
		else {
			if (getDB().isSupportsInlineDistinct()) {
				query.append(DLFileEntryModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(DLFileEntryModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				DLFileEntry.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				q.addEntity(_FILTER_ENTITY_ALIAS, DLFileEntryImpl.class);
			}
			else {
				q.addEntity(_FILTER_ENTITY_TABLE, DLFileEntryImpl.class);
			}

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			return (List<DLFileEntry>)QueryUtil.list(q, getDialect(), start, end);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Returns all the document library file entries where groupId = &#63; and folderId = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFileEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param folderIds the folder IDs
	 * @return the matching document library file entries
	 */
	@Override
	public List<DLFileEntry> findByG_F(long groupId, long[] folderIds) {
		return findByG_F(groupId, folderIds, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the document library file entries where groupId = &#63; and folderId = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFileEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param folderIds the folder IDs
	 * @param start the lower bound of the range of document library file entries
	 * @param end the upper bound of the range of document library file entries (not inclusive)
	 * @return the range of matching document library file entries
	 */
	@Override
	public List<DLFileEntry> findByG_F(long groupId, long[] folderIds,
		int start, int end) {
		return findByG_F(groupId, folderIds, start, end, null);
	}

	/**
	 * Returns an ordered range of all the document library file entries where groupId = &#63; and folderId = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFileEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param folderIds the folder IDs
	 * @param start the lower bound of the range of document library file entries
	 * @param end the upper bound of the range of document library file entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching document library file entries
	 */
	@Override
	public List<DLFileEntry> findByG_F(long groupId, long[] folderIds,
		int start, int end, OrderByComparator<DLFileEntry> orderByComparator) {
		return findByG_F(groupId, folderIds, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the document library file entries where groupId = &#63; and folderId = &#63;, optionally using the finder cache.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFileEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param start the lower bound of the range of document library file entries
	 * @param end the upper bound of the range of document library file entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching document library file entries
	 */
	@Override
	public List<DLFileEntry> findByG_F(long groupId, long[] folderIds,
		int start, int end, OrderByComparator<DLFileEntry> orderByComparator,
		boolean retrieveFromCache) {
		if (folderIds == null) {
			folderIds = new long[0];
		}
		else if (folderIds.length > 1) {
			folderIds = ArrayUtil.unique(folderIds);

			Arrays.sort(folderIds);
		}

		if (folderIds.length == 1) {
			return findByG_F(groupId, folderIds[0], start, end,
				orderByComparator);
		}

		boolean pagination = true;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderArgs = new Object[] { groupId, StringUtil.merge(folderIds) };
		}
		else {
			finderArgs = new Object[] {
					groupId, StringUtil.merge(folderIds),
					
					start, end, orderByComparator
				};
		}

		List<DLFileEntry> list = null;

		if (retrieveFromCache) {
			list = (List<DLFileEntry>)finderCache.getResult(FINDER_PATH_WITH_PAGINATION_FIND_BY_G_F,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (DLFileEntry dlFileEntry : list) {
					if ((groupId != dlFileEntry.getGroupId()) ||
							!ArrayUtil.contains(folderIds,
								dlFileEntry.getFolderId())) {
						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler query = new StringBundler();

			query.append(_SQL_SELECT_DLFILEENTRY_WHERE);

			query.append(_FINDER_COLUMN_G_F_GROUPID_2);

			if (folderIds.length > 0) {
				query.append(StringPool.OPEN_PARENTHESIS);

				query.append(_FINDER_COLUMN_G_F_FOLDERID_7);

				query.append(StringUtil.merge(folderIds));

				query.append(StringPool.CLOSE_PARENTHESIS);

				query.append(StringPool.CLOSE_PARENTHESIS);
			}

			query.setStringAt(removeConjunction(query.stringAt(query.index() -
						1)), query.index() - 1);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(DLFileEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (!pagination) {
					list = (List<DLFileEntry>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<DLFileEntry>)QueryUtil.list(q, getDialect(),
							start, end);
				}

				cacheResult(list);

				finderCache.putResult(FINDER_PATH_WITH_PAGINATION_FIND_BY_G_F,
					finderArgs, list);
			}
			catch (Exception e) {
				finderCache.removeResult(FINDER_PATH_WITH_PAGINATION_FIND_BY_G_F,
					finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Removes all the document library file entries where groupId = &#63; and folderId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 */
	@Override
	public void removeByG_F(long groupId, long folderId) {
		for (DLFileEntry dlFileEntry : findByG_F(groupId, folderId,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(dlFileEntry);
		}
	}

	/**
	 * Returns the number of document library file entries where groupId = &#63; and folderId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @return the number of matching document library file entries
	 */
	@Override
	public int countByG_F(long groupId, long folderId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_G_F;

		Object[] finderArgs = new Object[] { groupId, folderId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_DLFILEENTRY_WHERE);

			query.append(_FINDER_COLUMN_G_F_GROUPID_2);

			query.append(_FINDER_COLUMN_G_F_FOLDERID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(folderId);

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
	 * Returns the number of document library file entries where groupId = &#63; and folderId = any &#63;.
	 *
	 * @param groupId the group ID
	 * @param folderIds the folder IDs
	 * @return the number of matching document library file entries
	 */
	@Override
	public int countByG_F(long groupId, long[] folderIds) {
		if (folderIds == null) {
			folderIds = new long[0];
		}
		else if (folderIds.length > 1) {
			folderIds = ArrayUtil.unique(folderIds);

			Arrays.sort(folderIds);
		}

		Object[] finderArgs = new Object[] { groupId, StringUtil.merge(folderIds) };

		Long count = (Long)finderCache.getResult(FINDER_PATH_WITH_PAGINATION_COUNT_BY_G_F,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler();

			query.append(_SQL_COUNT_DLFILEENTRY_WHERE);

			query.append(_FINDER_COLUMN_G_F_GROUPID_2);

			if (folderIds.length > 0) {
				query.append(StringPool.OPEN_PARENTHESIS);

				query.append(_FINDER_COLUMN_G_F_FOLDERID_7);

				query.append(StringUtil.merge(folderIds));

				query.append(StringPool.CLOSE_PARENTHESIS);

				query.append(StringPool.CLOSE_PARENTHESIS);
			}

			query.setStringAt(removeConjunction(query.stringAt(query.index() -
						1)), query.index() - 1);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				count = (Long)q.uniqueResult();

				finderCache.putResult(FINDER_PATH_WITH_PAGINATION_COUNT_BY_G_F,
					finderArgs, count);
			}
			catch (Exception e) {
				finderCache.removeResult(FINDER_PATH_WITH_PAGINATION_COUNT_BY_G_F,
					finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of document library file entries that the user has permission to view where groupId = &#63; and folderId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @return the number of matching document library file entries that the user has permission to view
	 */
	@Override
	public int filterCountByG_F(long groupId, long folderId) {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByG_F(groupId, folderId);
		}

		StringBundler query = new StringBundler(3);

		query.append(_FILTER_SQL_COUNT_DLFILEENTRY_WHERE);

		query.append(_FINDER_COLUMN_G_F_GROUPID_2);

		query.append(_FINDER_COLUMN_G_F_FOLDERID_2);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				DLFileEntry.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME,
				com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			qPos.add(folderId);

			Long count = (Long)q.uniqueResult();

			return count.intValue();
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Returns the number of document library file entries that the user has permission to view where groupId = &#63; and folderId = any &#63;.
	 *
	 * @param groupId the group ID
	 * @param folderIds the folder IDs
	 * @return the number of matching document library file entries that the user has permission to view
	 */
	@Override
	public int filterCountByG_F(long groupId, long[] folderIds) {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByG_F(groupId, folderIds);
		}

		if (folderIds == null) {
			folderIds = new long[0];
		}
		else if (folderIds.length > 1) {
			folderIds = ArrayUtil.unique(folderIds);

			Arrays.sort(folderIds);
		}

		StringBundler query = new StringBundler();

		query.append(_FILTER_SQL_COUNT_DLFILEENTRY_WHERE);

		query.append(_FINDER_COLUMN_G_F_GROUPID_2);

		if (folderIds.length > 0) {
			query.append(StringPool.OPEN_PARENTHESIS);

			query.append(_FINDER_COLUMN_G_F_FOLDERID_7);

			query.append(StringUtil.merge(folderIds));

			query.append(StringPool.CLOSE_PARENTHESIS);

			query.append(StringPool.CLOSE_PARENTHESIS);
		}

		query.setStringAt(removeConjunction(query.stringAt(query.index() - 1)),
			query.index() - 1);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				DLFileEntry.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME,
				com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			Long count = (Long)q.uniqueResult();

			return count.intValue();
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	private static final String _FINDER_COLUMN_G_F_GROUPID_2 = "dlFileEntry.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_F_FOLDERID_2 = "dlFileEntry.folderId = ?";
	private static final String _FINDER_COLUMN_G_F_FOLDERID_7 = "dlFileEntry.folderId IN (";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_R_F = new FinderPath(DLFileEntryModelImpl.ENTITY_CACHE_ENABLED,
			DLFileEntryModelImpl.FINDER_CACHE_ENABLED, DLFileEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByR_F",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_R_F = new FinderPath(DLFileEntryModelImpl.ENTITY_CACHE_ENABLED,
			DLFileEntryModelImpl.FINDER_CACHE_ENABLED, DLFileEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByR_F",
			new String[] { Long.class.getName(), Long.class.getName() },
			DLFileEntryModelImpl.REPOSITORYID_COLUMN_BITMASK |
			DLFileEntryModelImpl.FOLDERID_COLUMN_BITMASK |
			DLFileEntryModelImpl.NAME_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_R_F = new FinderPath(DLFileEntryModelImpl.ENTITY_CACHE_ENABLED,
			DLFileEntryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByR_F",
			new String[] { Long.class.getName(), Long.class.getName() });

	/**
	 * Returns all the document library file entries where repositoryId = &#63; and folderId = &#63;.
	 *
	 * @param repositoryId the repository ID
	 * @param folderId the folder ID
	 * @return the matching document library file entries
	 */
	@Override
	public List<DLFileEntry> findByR_F(long repositoryId, long folderId) {
		return findByR_F(repositoryId, folderId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the document library file entries where repositoryId = &#63; and folderId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFileEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param repositoryId the repository ID
	 * @param folderId the folder ID
	 * @param start the lower bound of the range of document library file entries
	 * @param end the upper bound of the range of document library file entries (not inclusive)
	 * @return the range of matching document library file entries
	 */
	@Override
	public List<DLFileEntry> findByR_F(long repositoryId, long folderId,
		int start, int end) {
		return findByR_F(repositoryId, folderId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the document library file entries where repositoryId = &#63; and folderId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFileEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param repositoryId the repository ID
	 * @param folderId the folder ID
	 * @param start the lower bound of the range of document library file entries
	 * @param end the upper bound of the range of document library file entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching document library file entries
	 */
	@Override
	public List<DLFileEntry> findByR_F(long repositoryId, long folderId,
		int start, int end, OrderByComparator<DLFileEntry> orderByComparator) {
		return findByR_F(repositoryId, folderId, start, end, orderByComparator,
			true);
	}

	/**
	 * Returns an ordered range of all the document library file entries where repositoryId = &#63; and folderId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFileEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param repositoryId the repository ID
	 * @param folderId the folder ID
	 * @param start the lower bound of the range of document library file entries
	 * @param end the upper bound of the range of document library file entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching document library file entries
	 */
	@Override
	public List<DLFileEntry> findByR_F(long repositoryId, long folderId,
		int start, int end, OrderByComparator<DLFileEntry> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_R_F;
			finderArgs = new Object[] { repositoryId, folderId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_R_F;
			finderArgs = new Object[] {
					repositoryId, folderId,
					
					start, end, orderByComparator
				};
		}

		List<DLFileEntry> list = null;

		if (retrieveFromCache) {
			list = (List<DLFileEntry>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (DLFileEntry dlFileEntry : list) {
					if ((repositoryId != dlFileEntry.getRepositoryId()) ||
							(folderId != dlFileEntry.getFolderId())) {
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

			query.append(_SQL_SELECT_DLFILEENTRY_WHERE);

			query.append(_FINDER_COLUMN_R_F_REPOSITORYID_2);

			query.append(_FINDER_COLUMN_R_F_FOLDERID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(DLFileEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(repositoryId);

				qPos.add(folderId);

				if (!pagination) {
					list = (List<DLFileEntry>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<DLFileEntry>)QueryUtil.list(q, getDialect(),
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
	 * Returns the first document library file entry in the ordered set where repositoryId = &#63; and folderId = &#63;.
	 *
	 * @param repositoryId the repository ID
	 * @param folderId the folder ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching document library file entry
	 * @throws NoSuchFileEntryException if a matching document library file entry could not be found
	 */
	@Override
	public DLFileEntry findByR_F_First(long repositoryId, long folderId,
		OrderByComparator<DLFileEntry> orderByComparator)
		throws NoSuchFileEntryException {
		DLFileEntry dlFileEntry = fetchByR_F_First(repositoryId, folderId,
				orderByComparator);

		if (dlFileEntry != null) {
			return dlFileEntry;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("repositoryId=");
		msg.append(repositoryId);

		msg.append(", folderId=");
		msg.append(folderId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchFileEntryException(msg.toString());
	}

	/**
	 * Returns the first document library file entry in the ordered set where repositoryId = &#63; and folderId = &#63;.
	 *
	 * @param repositoryId the repository ID
	 * @param folderId the folder ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching document library file entry, or <code>null</code> if a matching document library file entry could not be found
	 */
	@Override
	public DLFileEntry fetchByR_F_First(long repositoryId, long folderId,
		OrderByComparator<DLFileEntry> orderByComparator) {
		List<DLFileEntry> list = findByR_F(repositoryId, folderId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last document library file entry in the ordered set where repositoryId = &#63; and folderId = &#63;.
	 *
	 * @param repositoryId the repository ID
	 * @param folderId the folder ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching document library file entry
	 * @throws NoSuchFileEntryException if a matching document library file entry could not be found
	 */
	@Override
	public DLFileEntry findByR_F_Last(long repositoryId, long folderId,
		OrderByComparator<DLFileEntry> orderByComparator)
		throws NoSuchFileEntryException {
		DLFileEntry dlFileEntry = fetchByR_F_Last(repositoryId, folderId,
				orderByComparator);

		if (dlFileEntry != null) {
			return dlFileEntry;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("repositoryId=");
		msg.append(repositoryId);

		msg.append(", folderId=");
		msg.append(folderId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchFileEntryException(msg.toString());
	}

	/**
	 * Returns the last document library file entry in the ordered set where repositoryId = &#63; and folderId = &#63;.
	 *
	 * @param repositoryId the repository ID
	 * @param folderId the folder ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching document library file entry, or <code>null</code> if a matching document library file entry could not be found
	 */
	@Override
	public DLFileEntry fetchByR_F_Last(long repositoryId, long folderId,
		OrderByComparator<DLFileEntry> orderByComparator) {
		int count = countByR_F(repositoryId, folderId);

		if (count == 0) {
			return null;
		}

		List<DLFileEntry> list = findByR_F(repositoryId, folderId, count - 1,
				count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the document library file entries before and after the current document library file entry in the ordered set where repositoryId = &#63; and folderId = &#63;.
	 *
	 * @param fileEntryId the primary key of the current document library file entry
	 * @param repositoryId the repository ID
	 * @param folderId the folder ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next document library file entry
	 * @throws NoSuchFileEntryException if a document library file entry with the primary key could not be found
	 */
	@Override
	public DLFileEntry[] findByR_F_PrevAndNext(long fileEntryId,
		long repositoryId, long folderId,
		OrderByComparator<DLFileEntry> orderByComparator)
		throws NoSuchFileEntryException {
		DLFileEntry dlFileEntry = findByPrimaryKey(fileEntryId);

		Session session = null;

		try {
			session = openSession();

			DLFileEntry[] array = new DLFileEntryImpl[3];

			array[0] = getByR_F_PrevAndNext(session, dlFileEntry, repositoryId,
					folderId, orderByComparator, true);

			array[1] = dlFileEntry;

			array[2] = getByR_F_PrevAndNext(session, dlFileEntry, repositoryId,
					folderId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected DLFileEntry getByR_F_PrevAndNext(Session session,
		DLFileEntry dlFileEntry, long repositoryId, long folderId,
		OrderByComparator<DLFileEntry> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(5 +
					(orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(4);
		}

		query.append(_SQL_SELECT_DLFILEENTRY_WHERE);

		query.append(_FINDER_COLUMN_R_F_REPOSITORYID_2);

		query.append(_FINDER_COLUMN_R_F_FOLDERID_2);

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
			query.append(DLFileEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(repositoryId);

		qPos.add(folderId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(dlFileEntry);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<DLFileEntry> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the document library file entries where repositoryId = &#63; and folderId = &#63; from the database.
	 *
	 * @param repositoryId the repository ID
	 * @param folderId the folder ID
	 */
	@Override
	public void removeByR_F(long repositoryId, long folderId) {
		for (DLFileEntry dlFileEntry : findByR_F(repositoryId, folderId,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(dlFileEntry);
		}
	}

	/**
	 * Returns the number of document library file entries where repositoryId = &#63; and folderId = &#63;.
	 *
	 * @param repositoryId the repository ID
	 * @param folderId the folder ID
	 * @return the number of matching document library file entries
	 */
	@Override
	public int countByR_F(long repositoryId, long folderId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_R_F;

		Object[] finderArgs = new Object[] { repositoryId, folderId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_DLFILEENTRY_WHERE);

			query.append(_FINDER_COLUMN_R_F_REPOSITORYID_2);

			query.append(_FINDER_COLUMN_R_F_FOLDERID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(repositoryId);

				qPos.add(folderId);

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

	private static final String _FINDER_COLUMN_R_F_REPOSITORYID_2 = "dlFileEntry.repositoryId = ? AND ";
	private static final String _FINDER_COLUMN_R_F_FOLDERID_2 = "dlFileEntry.folderId = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_F_N = new FinderPath(DLFileEntryModelImpl.ENTITY_CACHE_ENABLED,
			DLFileEntryModelImpl.FINDER_CACHE_ENABLED, DLFileEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByF_N",
			new String[] {
				Long.class.getName(), String.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_F_N = new FinderPath(DLFileEntryModelImpl.ENTITY_CACHE_ENABLED,
			DLFileEntryModelImpl.FINDER_CACHE_ENABLED, DLFileEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByF_N",
			new String[] { Long.class.getName(), String.class.getName() },
			DLFileEntryModelImpl.FOLDERID_COLUMN_BITMASK |
			DLFileEntryModelImpl.NAME_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_F_N = new FinderPath(DLFileEntryModelImpl.ENTITY_CACHE_ENABLED,
			DLFileEntryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByF_N",
			new String[] { Long.class.getName(), String.class.getName() });

	/**
	 * Returns all the document library file entries where folderId = &#63; and name = &#63;.
	 *
	 * @param folderId the folder ID
	 * @param name the name
	 * @return the matching document library file entries
	 */
	@Override
	public List<DLFileEntry> findByF_N(long folderId, String name) {
		return findByF_N(folderId, name, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the document library file entries where folderId = &#63; and name = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFileEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param folderId the folder ID
	 * @param name the name
	 * @param start the lower bound of the range of document library file entries
	 * @param end the upper bound of the range of document library file entries (not inclusive)
	 * @return the range of matching document library file entries
	 */
	@Override
	public List<DLFileEntry> findByF_N(long folderId, String name, int start,
		int end) {
		return findByF_N(folderId, name, start, end, null);
	}

	/**
	 * Returns an ordered range of all the document library file entries where folderId = &#63; and name = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFileEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param folderId the folder ID
	 * @param name the name
	 * @param start the lower bound of the range of document library file entries
	 * @param end the upper bound of the range of document library file entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching document library file entries
	 */
	@Override
	public List<DLFileEntry> findByF_N(long folderId, String name, int start,
		int end, OrderByComparator<DLFileEntry> orderByComparator) {
		return findByF_N(folderId, name, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the document library file entries where folderId = &#63; and name = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFileEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param folderId the folder ID
	 * @param name the name
	 * @param start the lower bound of the range of document library file entries
	 * @param end the upper bound of the range of document library file entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching document library file entries
	 */
	@Override
	public List<DLFileEntry> findByF_N(long folderId, String name, int start,
		int end, OrderByComparator<DLFileEntry> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_F_N;
			finderArgs = new Object[] { folderId, name };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_F_N;
			finderArgs = new Object[] {
					folderId, name,
					
					start, end, orderByComparator
				};
		}

		List<DLFileEntry> list = null;

		if (retrieveFromCache) {
			list = (List<DLFileEntry>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (DLFileEntry dlFileEntry : list) {
					if ((folderId != dlFileEntry.getFolderId()) ||
							!Objects.equals(name, dlFileEntry.getName())) {
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

			query.append(_SQL_SELECT_DLFILEENTRY_WHERE);

			query.append(_FINDER_COLUMN_F_N_FOLDERID_2);

			boolean bindName = false;

			if (name == null) {
				query.append(_FINDER_COLUMN_F_N_NAME_1);
			}
			else if (name.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_F_N_NAME_3);
			}
			else {
				bindName = true;

				query.append(_FINDER_COLUMN_F_N_NAME_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(DLFileEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(folderId);

				if (bindName) {
					qPos.add(name);
				}

				if (!pagination) {
					list = (List<DLFileEntry>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<DLFileEntry>)QueryUtil.list(q, getDialect(),
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
	 * Returns the first document library file entry in the ordered set where folderId = &#63; and name = &#63;.
	 *
	 * @param folderId the folder ID
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching document library file entry
	 * @throws NoSuchFileEntryException if a matching document library file entry could not be found
	 */
	@Override
	public DLFileEntry findByF_N_First(long folderId, String name,
		OrderByComparator<DLFileEntry> orderByComparator)
		throws NoSuchFileEntryException {
		DLFileEntry dlFileEntry = fetchByF_N_First(folderId, name,
				orderByComparator);

		if (dlFileEntry != null) {
			return dlFileEntry;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("folderId=");
		msg.append(folderId);

		msg.append(", name=");
		msg.append(name);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchFileEntryException(msg.toString());
	}

	/**
	 * Returns the first document library file entry in the ordered set where folderId = &#63; and name = &#63;.
	 *
	 * @param folderId the folder ID
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching document library file entry, or <code>null</code> if a matching document library file entry could not be found
	 */
	@Override
	public DLFileEntry fetchByF_N_First(long folderId, String name,
		OrderByComparator<DLFileEntry> orderByComparator) {
		List<DLFileEntry> list = findByF_N(folderId, name, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last document library file entry in the ordered set where folderId = &#63; and name = &#63;.
	 *
	 * @param folderId the folder ID
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching document library file entry
	 * @throws NoSuchFileEntryException if a matching document library file entry could not be found
	 */
	@Override
	public DLFileEntry findByF_N_Last(long folderId, String name,
		OrderByComparator<DLFileEntry> orderByComparator)
		throws NoSuchFileEntryException {
		DLFileEntry dlFileEntry = fetchByF_N_Last(folderId, name,
				orderByComparator);

		if (dlFileEntry != null) {
			return dlFileEntry;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("folderId=");
		msg.append(folderId);

		msg.append(", name=");
		msg.append(name);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchFileEntryException(msg.toString());
	}

	/**
	 * Returns the last document library file entry in the ordered set where folderId = &#63; and name = &#63;.
	 *
	 * @param folderId the folder ID
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching document library file entry, or <code>null</code> if a matching document library file entry could not be found
	 */
	@Override
	public DLFileEntry fetchByF_N_Last(long folderId, String name,
		OrderByComparator<DLFileEntry> orderByComparator) {
		int count = countByF_N(folderId, name);

		if (count == 0) {
			return null;
		}

		List<DLFileEntry> list = findByF_N(folderId, name, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the document library file entries before and after the current document library file entry in the ordered set where folderId = &#63; and name = &#63;.
	 *
	 * @param fileEntryId the primary key of the current document library file entry
	 * @param folderId the folder ID
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next document library file entry
	 * @throws NoSuchFileEntryException if a document library file entry with the primary key could not be found
	 */
	@Override
	public DLFileEntry[] findByF_N_PrevAndNext(long fileEntryId, long folderId,
		String name, OrderByComparator<DLFileEntry> orderByComparator)
		throws NoSuchFileEntryException {
		DLFileEntry dlFileEntry = findByPrimaryKey(fileEntryId);

		Session session = null;

		try {
			session = openSession();

			DLFileEntry[] array = new DLFileEntryImpl[3];

			array[0] = getByF_N_PrevAndNext(session, dlFileEntry, folderId,
					name, orderByComparator, true);

			array[1] = dlFileEntry;

			array[2] = getByF_N_PrevAndNext(session, dlFileEntry, folderId,
					name, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected DLFileEntry getByF_N_PrevAndNext(Session session,
		DLFileEntry dlFileEntry, long folderId, String name,
		OrderByComparator<DLFileEntry> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(5 +
					(orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(4);
		}

		query.append(_SQL_SELECT_DLFILEENTRY_WHERE);

		query.append(_FINDER_COLUMN_F_N_FOLDERID_2);

		boolean bindName = false;

		if (name == null) {
			query.append(_FINDER_COLUMN_F_N_NAME_1);
		}
		else if (name.equals(StringPool.BLANK)) {
			query.append(_FINDER_COLUMN_F_N_NAME_3);
		}
		else {
			bindName = true;

			query.append(_FINDER_COLUMN_F_N_NAME_2);
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
			query.append(DLFileEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(folderId);

		if (bindName) {
			qPos.add(name);
		}

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(dlFileEntry);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<DLFileEntry> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the document library file entries where folderId = &#63; and name = &#63; from the database.
	 *
	 * @param folderId the folder ID
	 * @param name the name
	 */
	@Override
	public void removeByF_N(long folderId, String name) {
		for (DLFileEntry dlFileEntry : findByF_N(folderId, name,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(dlFileEntry);
		}
	}

	/**
	 * Returns the number of document library file entries where folderId = &#63; and name = &#63;.
	 *
	 * @param folderId the folder ID
	 * @param name the name
	 * @return the number of matching document library file entries
	 */
	@Override
	public int countByF_N(long folderId, String name) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_F_N;

		Object[] finderArgs = new Object[] { folderId, name };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_DLFILEENTRY_WHERE);

			query.append(_FINDER_COLUMN_F_N_FOLDERID_2);

			boolean bindName = false;

			if (name == null) {
				query.append(_FINDER_COLUMN_F_N_NAME_1);
			}
			else if (name.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_F_N_NAME_3);
			}
			else {
				bindName = true;

				query.append(_FINDER_COLUMN_F_N_NAME_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(folderId);

				if (bindName) {
					qPos.add(name);
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

	private static final String _FINDER_COLUMN_F_N_FOLDERID_2 = "dlFileEntry.folderId = ? AND ";
	private static final String _FINDER_COLUMN_F_N_NAME_1 = "dlFileEntry.name IS NULL";
	private static final String _FINDER_COLUMN_F_N_NAME_2 = "dlFileEntry.name = ?";
	private static final String _FINDER_COLUMN_F_N_NAME_3 = "(dlFileEntry.name IS NULL OR dlFileEntry.name = '')";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_G_U_F = new FinderPath(DLFileEntryModelImpl.ENTITY_CACHE_ENABLED,
			DLFileEntryModelImpl.FINDER_CACHE_ENABLED, DLFileEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_U_F",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_U_F = new FinderPath(DLFileEntryModelImpl.ENTITY_CACHE_ENABLED,
			DLFileEntryModelImpl.FINDER_CACHE_ENABLED, DLFileEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByG_U_F",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName()
			},
			DLFileEntryModelImpl.GROUPID_COLUMN_BITMASK |
			DLFileEntryModelImpl.USERID_COLUMN_BITMASK |
			DLFileEntryModelImpl.FOLDERID_COLUMN_BITMASK |
			DLFileEntryModelImpl.NAME_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_G_U_F = new FinderPath(DLFileEntryModelImpl.ENTITY_CACHE_ENABLED,
			DLFileEntryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_U_F",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_COUNT_BY_G_U_F = new FinderPath(DLFileEntryModelImpl.ENTITY_CACHE_ENABLED,
			DLFileEntryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "countByG_U_F",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName()
			});

	/**
	 * Returns all the document library file entries where groupId = &#63; and userId = &#63; and folderId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param folderId the folder ID
	 * @return the matching document library file entries
	 */
	@Override
	public List<DLFileEntry> findByG_U_F(long groupId, long userId,
		long folderId) {
		return findByG_U_F(groupId, userId, folderId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the document library file entries where groupId = &#63; and userId = &#63; and folderId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFileEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param folderId the folder ID
	 * @param start the lower bound of the range of document library file entries
	 * @param end the upper bound of the range of document library file entries (not inclusive)
	 * @return the range of matching document library file entries
	 */
	@Override
	public List<DLFileEntry> findByG_U_F(long groupId, long userId,
		long folderId, int start, int end) {
		return findByG_U_F(groupId, userId, folderId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the document library file entries where groupId = &#63; and userId = &#63; and folderId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFileEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param folderId the folder ID
	 * @param start the lower bound of the range of document library file entries
	 * @param end the upper bound of the range of document library file entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching document library file entries
	 */
	@Override
	public List<DLFileEntry> findByG_U_F(long groupId, long userId,
		long folderId, int start, int end,
		OrderByComparator<DLFileEntry> orderByComparator) {
		return findByG_U_F(groupId, userId, folderId, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the document library file entries where groupId = &#63; and userId = &#63; and folderId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFileEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param folderId the folder ID
	 * @param start the lower bound of the range of document library file entries
	 * @param end the upper bound of the range of document library file entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching document library file entries
	 */
	@Override
	public List<DLFileEntry> findByG_U_F(long groupId, long userId,
		long folderId, int start, int end,
		OrderByComparator<DLFileEntry> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_U_F;
			finderArgs = new Object[] { groupId, userId, folderId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_G_U_F;
			finderArgs = new Object[] {
					groupId, userId, folderId,
					
					start, end, orderByComparator
				};
		}

		List<DLFileEntry> list = null;

		if (retrieveFromCache) {
			list = (List<DLFileEntry>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (DLFileEntry dlFileEntry : list) {
					if ((groupId != dlFileEntry.getGroupId()) ||
							(userId != dlFileEntry.getUserId()) ||
							(folderId != dlFileEntry.getFolderId())) {
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

			query.append(_SQL_SELECT_DLFILEENTRY_WHERE);

			query.append(_FINDER_COLUMN_G_U_F_GROUPID_2);

			query.append(_FINDER_COLUMN_G_U_F_USERID_2);

			query.append(_FINDER_COLUMN_G_U_F_FOLDERID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(DLFileEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(userId);

				qPos.add(folderId);

				if (!pagination) {
					list = (List<DLFileEntry>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<DLFileEntry>)QueryUtil.list(q, getDialect(),
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
	 * Returns the first document library file entry in the ordered set where groupId = &#63; and userId = &#63; and folderId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param folderId the folder ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching document library file entry
	 * @throws NoSuchFileEntryException if a matching document library file entry could not be found
	 */
	@Override
	public DLFileEntry findByG_U_F_First(long groupId, long userId,
		long folderId, OrderByComparator<DLFileEntry> orderByComparator)
		throws NoSuchFileEntryException {
		DLFileEntry dlFileEntry = fetchByG_U_F_First(groupId, userId, folderId,
				orderByComparator);

		if (dlFileEntry != null) {
			return dlFileEntry;
		}

		StringBundler msg = new StringBundler(8);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", userId=");
		msg.append(userId);

		msg.append(", folderId=");
		msg.append(folderId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchFileEntryException(msg.toString());
	}

	/**
	 * Returns the first document library file entry in the ordered set where groupId = &#63; and userId = &#63; and folderId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param folderId the folder ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching document library file entry, or <code>null</code> if a matching document library file entry could not be found
	 */
	@Override
	public DLFileEntry fetchByG_U_F_First(long groupId, long userId,
		long folderId, OrderByComparator<DLFileEntry> orderByComparator) {
		List<DLFileEntry> list = findByG_U_F(groupId, userId, folderId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last document library file entry in the ordered set where groupId = &#63; and userId = &#63; and folderId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param folderId the folder ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching document library file entry
	 * @throws NoSuchFileEntryException if a matching document library file entry could not be found
	 */
	@Override
	public DLFileEntry findByG_U_F_Last(long groupId, long userId,
		long folderId, OrderByComparator<DLFileEntry> orderByComparator)
		throws NoSuchFileEntryException {
		DLFileEntry dlFileEntry = fetchByG_U_F_Last(groupId, userId, folderId,
				orderByComparator);

		if (dlFileEntry != null) {
			return dlFileEntry;
		}

		StringBundler msg = new StringBundler(8);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", userId=");
		msg.append(userId);

		msg.append(", folderId=");
		msg.append(folderId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchFileEntryException(msg.toString());
	}

	/**
	 * Returns the last document library file entry in the ordered set where groupId = &#63; and userId = &#63; and folderId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param folderId the folder ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching document library file entry, or <code>null</code> if a matching document library file entry could not be found
	 */
	@Override
	public DLFileEntry fetchByG_U_F_Last(long groupId, long userId,
		long folderId, OrderByComparator<DLFileEntry> orderByComparator) {
		int count = countByG_U_F(groupId, userId, folderId);

		if (count == 0) {
			return null;
		}

		List<DLFileEntry> list = findByG_U_F(groupId, userId, folderId,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the document library file entries before and after the current document library file entry in the ordered set where groupId = &#63; and userId = &#63; and folderId = &#63;.
	 *
	 * @param fileEntryId the primary key of the current document library file entry
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param folderId the folder ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next document library file entry
	 * @throws NoSuchFileEntryException if a document library file entry with the primary key could not be found
	 */
	@Override
	public DLFileEntry[] findByG_U_F_PrevAndNext(long fileEntryId,
		long groupId, long userId, long folderId,
		OrderByComparator<DLFileEntry> orderByComparator)
		throws NoSuchFileEntryException {
		DLFileEntry dlFileEntry = findByPrimaryKey(fileEntryId);

		Session session = null;

		try {
			session = openSession();

			DLFileEntry[] array = new DLFileEntryImpl[3];

			array[0] = getByG_U_F_PrevAndNext(session, dlFileEntry, groupId,
					userId, folderId, orderByComparator, true);

			array[1] = dlFileEntry;

			array[2] = getByG_U_F_PrevAndNext(session, dlFileEntry, groupId,
					userId, folderId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected DLFileEntry getByG_U_F_PrevAndNext(Session session,
		DLFileEntry dlFileEntry, long groupId, long userId, long folderId,
		OrderByComparator<DLFileEntry> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(5);
		}

		query.append(_SQL_SELECT_DLFILEENTRY_WHERE);

		query.append(_FINDER_COLUMN_G_U_F_GROUPID_2);

		query.append(_FINDER_COLUMN_G_U_F_USERID_2);

		query.append(_FINDER_COLUMN_G_U_F_FOLDERID_2);

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
			query.append(DLFileEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		qPos.add(userId);

		qPos.add(folderId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(dlFileEntry);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<DLFileEntry> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the document library file entries that the user has permission to view where groupId = &#63; and userId = &#63; and folderId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param folderId the folder ID
	 * @return the matching document library file entries that the user has permission to view
	 */
	@Override
	public List<DLFileEntry> filterFindByG_U_F(long groupId, long userId,
		long folderId) {
		return filterFindByG_U_F(groupId, userId, folderId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the document library file entries that the user has permission to view where groupId = &#63; and userId = &#63; and folderId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFileEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param folderId the folder ID
	 * @param start the lower bound of the range of document library file entries
	 * @param end the upper bound of the range of document library file entries (not inclusive)
	 * @return the range of matching document library file entries that the user has permission to view
	 */
	@Override
	public List<DLFileEntry> filterFindByG_U_F(long groupId, long userId,
		long folderId, int start, int end) {
		return filterFindByG_U_F(groupId, userId, folderId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the document library file entries that the user has permissions to view where groupId = &#63; and userId = &#63; and folderId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFileEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param folderId the folder ID
	 * @param start the lower bound of the range of document library file entries
	 * @param end the upper bound of the range of document library file entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching document library file entries that the user has permission to view
	 */
	@Override
	public List<DLFileEntry> filterFindByG_U_F(long groupId, long userId,
		long folderId, int start, int end,
		OrderByComparator<DLFileEntry> orderByComparator) {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_U_F(groupId, userId, folderId, start, end,
				orderByComparator);
		}

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(5 +
					(orderByComparator.getOrderByFields().length * 2));
		}
		else {
			query = new StringBundler(6);
		}

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_DLFILEENTRY_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_DLFILEENTRY_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_G_U_F_GROUPID_2);

		query.append(_FINDER_COLUMN_G_U_F_USERID_2);

		query.append(_FINDER_COLUMN_G_U_F_FOLDERID_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_DLFILEENTRY_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			if (getDB().isSupportsInlineDistinct()) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator, true);
			}
			else {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_TABLE,
					orderByComparator, true);
			}
		}
		else {
			if (getDB().isSupportsInlineDistinct()) {
				query.append(DLFileEntryModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(DLFileEntryModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				DLFileEntry.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				q.addEntity(_FILTER_ENTITY_ALIAS, DLFileEntryImpl.class);
			}
			else {
				q.addEntity(_FILTER_ENTITY_TABLE, DLFileEntryImpl.class);
			}

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			qPos.add(userId);

			qPos.add(folderId);

			return (List<DLFileEntry>)QueryUtil.list(q, getDialect(), start, end);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Returns the document library file entries before and after the current document library file entry in the ordered set of document library file entries that the user has permission to view where groupId = &#63; and userId = &#63; and folderId = &#63;.
	 *
	 * @param fileEntryId the primary key of the current document library file entry
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param folderId the folder ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next document library file entry
	 * @throws NoSuchFileEntryException if a document library file entry with the primary key could not be found
	 */
	@Override
	public DLFileEntry[] filterFindByG_U_F_PrevAndNext(long fileEntryId,
		long groupId, long userId, long folderId,
		OrderByComparator<DLFileEntry> orderByComparator)
		throws NoSuchFileEntryException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_U_F_PrevAndNext(fileEntryId, groupId, userId,
				folderId, orderByComparator);
		}

		DLFileEntry dlFileEntry = findByPrimaryKey(fileEntryId);

		Session session = null;

		try {
			session = openSession();

			DLFileEntry[] array = new DLFileEntryImpl[3];

			array[0] = filterGetByG_U_F_PrevAndNext(session, dlFileEntry,
					groupId, userId, folderId, orderByComparator, true);

			array[1] = dlFileEntry;

			array[2] = filterGetByG_U_F_PrevAndNext(session, dlFileEntry,
					groupId, userId, folderId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected DLFileEntry filterGetByG_U_F_PrevAndNext(Session session,
		DLFileEntry dlFileEntry, long groupId, long userId, long folderId,
		OrderByComparator<DLFileEntry> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(7 +
					(orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(6);
		}

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_DLFILEENTRY_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_DLFILEENTRY_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_G_U_F_GROUPID_2);

		query.append(_FINDER_COLUMN_G_U_F_USERID_2);

		query.append(_FINDER_COLUMN_G_U_F_FOLDERID_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_DLFILEENTRY_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			String[] orderByConditionFields = orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				query.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				if (getDB().isSupportsInlineDistinct()) {
					query.append(_ORDER_BY_ENTITY_ALIAS);
				}
				else {
					query.append(_ORDER_BY_ENTITY_TABLE);
				}

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
				if (getDB().isSupportsInlineDistinct()) {
					query.append(_ORDER_BY_ENTITY_ALIAS);
				}
				else {
					query.append(_ORDER_BY_ENTITY_TABLE);
				}

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
			if (getDB().isSupportsInlineDistinct()) {
				query.append(DLFileEntryModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(DLFileEntryModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				DLFileEntry.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		SQLQuery q = session.createSynchronizedSQLQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		if (getDB().isSupportsInlineDistinct()) {
			q.addEntity(_FILTER_ENTITY_ALIAS, DLFileEntryImpl.class);
		}
		else {
			q.addEntity(_FILTER_ENTITY_TABLE, DLFileEntryImpl.class);
		}

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		qPos.add(userId);

		qPos.add(folderId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(dlFileEntry);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<DLFileEntry> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the document library file entries that the user has permission to view where groupId = &#63; and userId = &#63; and folderId = any &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param folderIds the folder IDs
	 * @return the matching document library file entries that the user has permission to view
	 */
	@Override
	public List<DLFileEntry> filterFindByG_U_F(long groupId, long userId,
		long[] folderIds) {
		return filterFindByG_U_F(groupId, userId, folderIds, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the document library file entries that the user has permission to view where groupId = &#63; and userId = &#63; and folderId = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFileEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param folderIds the folder IDs
	 * @param start the lower bound of the range of document library file entries
	 * @param end the upper bound of the range of document library file entries (not inclusive)
	 * @return the range of matching document library file entries that the user has permission to view
	 */
	@Override
	public List<DLFileEntry> filterFindByG_U_F(long groupId, long userId,
		long[] folderIds, int start, int end) {
		return filterFindByG_U_F(groupId, userId, folderIds, start, end, null);
	}

	/**
	 * Returns an ordered range of all the document library file entries that the user has permission to view where groupId = &#63; and userId = &#63; and folderId = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFileEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param folderIds the folder IDs
	 * @param start the lower bound of the range of document library file entries
	 * @param end the upper bound of the range of document library file entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching document library file entries that the user has permission to view
	 */
	@Override
	public List<DLFileEntry> filterFindByG_U_F(long groupId, long userId,
		long[] folderIds, int start, int end,
		OrderByComparator<DLFileEntry> orderByComparator) {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_U_F(groupId, userId, folderIds, start, end,
				orderByComparator);
		}

		if (folderIds == null) {
			folderIds = new long[0];
		}
		else if (folderIds.length > 1) {
			folderIds = ArrayUtil.unique(folderIds);

			Arrays.sort(folderIds);
		}

		StringBundler query = new StringBundler();

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_DLFILEENTRY_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_DLFILEENTRY_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_G_U_F_GROUPID_2);

		query.append(_FINDER_COLUMN_G_U_F_USERID_2);

		if (folderIds.length > 0) {
			query.append(StringPool.OPEN_PARENTHESIS);

			query.append(_FINDER_COLUMN_G_U_F_FOLDERID_7);

			query.append(StringUtil.merge(folderIds));

			query.append(StringPool.CLOSE_PARENTHESIS);

			query.append(StringPool.CLOSE_PARENTHESIS);
		}

		query.setStringAt(removeConjunction(query.stringAt(query.index() - 1)),
			query.index() - 1);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_DLFILEENTRY_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			if (getDB().isSupportsInlineDistinct()) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator, true);
			}
			else {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_TABLE,
					orderByComparator, true);
			}
		}
		else {
			if (getDB().isSupportsInlineDistinct()) {
				query.append(DLFileEntryModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(DLFileEntryModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				DLFileEntry.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				q.addEntity(_FILTER_ENTITY_ALIAS, DLFileEntryImpl.class);
			}
			else {
				q.addEntity(_FILTER_ENTITY_TABLE, DLFileEntryImpl.class);
			}

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			qPos.add(userId);

			return (List<DLFileEntry>)QueryUtil.list(q, getDialect(), start, end);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Returns all the document library file entries where groupId = &#63; and userId = &#63; and folderId = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFileEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param folderIds the folder IDs
	 * @return the matching document library file entries
	 */
	@Override
	public List<DLFileEntry> findByG_U_F(long groupId, long userId,
		long[] folderIds) {
		return findByG_U_F(groupId, userId, folderIds, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the document library file entries where groupId = &#63; and userId = &#63; and folderId = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFileEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param folderIds the folder IDs
	 * @param start the lower bound of the range of document library file entries
	 * @param end the upper bound of the range of document library file entries (not inclusive)
	 * @return the range of matching document library file entries
	 */
	@Override
	public List<DLFileEntry> findByG_U_F(long groupId, long userId,
		long[] folderIds, int start, int end) {
		return findByG_U_F(groupId, userId, folderIds, start, end, null);
	}

	/**
	 * Returns an ordered range of all the document library file entries where groupId = &#63; and userId = &#63; and folderId = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFileEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param folderIds the folder IDs
	 * @param start the lower bound of the range of document library file entries
	 * @param end the upper bound of the range of document library file entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching document library file entries
	 */
	@Override
	public List<DLFileEntry> findByG_U_F(long groupId, long userId,
		long[] folderIds, int start, int end,
		OrderByComparator<DLFileEntry> orderByComparator) {
		return findByG_U_F(groupId, userId, folderIds, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the document library file entries where groupId = &#63; and userId = &#63; and folderId = &#63;, optionally using the finder cache.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFileEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param folderId the folder ID
	 * @param start the lower bound of the range of document library file entries
	 * @param end the upper bound of the range of document library file entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching document library file entries
	 */
	@Override
	public List<DLFileEntry> findByG_U_F(long groupId, long userId,
		long[] folderIds, int start, int end,
		OrderByComparator<DLFileEntry> orderByComparator,
		boolean retrieveFromCache) {
		if (folderIds == null) {
			folderIds = new long[0];
		}
		else if (folderIds.length > 1) {
			folderIds = ArrayUtil.unique(folderIds);

			Arrays.sort(folderIds);
		}

		if (folderIds.length == 1) {
			return findByG_U_F(groupId, userId, folderIds[0], start, end,
				orderByComparator);
		}

		boolean pagination = true;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderArgs = new Object[] {
					groupId, userId, StringUtil.merge(folderIds)
				};
		}
		else {
			finderArgs = new Object[] {
					groupId, userId, StringUtil.merge(folderIds),
					
					start, end, orderByComparator
				};
		}

		List<DLFileEntry> list = null;

		if (retrieveFromCache) {
			list = (List<DLFileEntry>)finderCache.getResult(FINDER_PATH_WITH_PAGINATION_FIND_BY_G_U_F,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (DLFileEntry dlFileEntry : list) {
					if ((groupId != dlFileEntry.getGroupId()) ||
							(userId != dlFileEntry.getUserId()) ||
							!ArrayUtil.contains(folderIds,
								dlFileEntry.getFolderId())) {
						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler query = new StringBundler();

			query.append(_SQL_SELECT_DLFILEENTRY_WHERE);

			query.append(_FINDER_COLUMN_G_U_F_GROUPID_2);

			query.append(_FINDER_COLUMN_G_U_F_USERID_2);

			if (folderIds.length > 0) {
				query.append(StringPool.OPEN_PARENTHESIS);

				query.append(_FINDER_COLUMN_G_U_F_FOLDERID_7);

				query.append(StringUtil.merge(folderIds));

				query.append(StringPool.CLOSE_PARENTHESIS);

				query.append(StringPool.CLOSE_PARENTHESIS);
			}

			query.setStringAt(removeConjunction(query.stringAt(query.index() -
						1)), query.index() - 1);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(DLFileEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(userId);

				if (!pagination) {
					list = (List<DLFileEntry>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<DLFileEntry>)QueryUtil.list(q, getDialect(),
							start, end);
				}

				cacheResult(list);

				finderCache.putResult(FINDER_PATH_WITH_PAGINATION_FIND_BY_G_U_F,
					finderArgs, list);
			}
			catch (Exception e) {
				finderCache.removeResult(FINDER_PATH_WITH_PAGINATION_FIND_BY_G_U_F,
					finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Removes all the document library file entries where groupId = &#63; and userId = &#63; and folderId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param folderId the folder ID
	 */
	@Override
	public void removeByG_U_F(long groupId, long userId, long folderId) {
		for (DLFileEntry dlFileEntry : findByG_U_F(groupId, userId, folderId,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(dlFileEntry);
		}
	}

	/**
	 * Returns the number of document library file entries where groupId = &#63; and userId = &#63; and folderId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param folderId the folder ID
	 * @return the number of matching document library file entries
	 */
	@Override
	public int countByG_U_F(long groupId, long userId, long folderId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_G_U_F;

		Object[] finderArgs = new Object[] { groupId, userId, folderId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_COUNT_DLFILEENTRY_WHERE);

			query.append(_FINDER_COLUMN_G_U_F_GROUPID_2);

			query.append(_FINDER_COLUMN_G_U_F_USERID_2);

			query.append(_FINDER_COLUMN_G_U_F_FOLDERID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(userId);

				qPos.add(folderId);

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
	 * Returns the number of document library file entries where groupId = &#63; and userId = &#63; and folderId = any &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param folderIds the folder IDs
	 * @return the number of matching document library file entries
	 */
	@Override
	public int countByG_U_F(long groupId, long userId, long[] folderIds) {
		if (folderIds == null) {
			folderIds = new long[0];
		}
		else if (folderIds.length > 1) {
			folderIds = ArrayUtil.unique(folderIds);

			Arrays.sort(folderIds);
		}

		Object[] finderArgs = new Object[] {
				groupId, userId, StringUtil.merge(folderIds)
			};

		Long count = (Long)finderCache.getResult(FINDER_PATH_WITH_PAGINATION_COUNT_BY_G_U_F,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler();

			query.append(_SQL_COUNT_DLFILEENTRY_WHERE);

			query.append(_FINDER_COLUMN_G_U_F_GROUPID_2);

			query.append(_FINDER_COLUMN_G_U_F_USERID_2);

			if (folderIds.length > 0) {
				query.append(StringPool.OPEN_PARENTHESIS);

				query.append(_FINDER_COLUMN_G_U_F_FOLDERID_7);

				query.append(StringUtil.merge(folderIds));

				query.append(StringPool.CLOSE_PARENTHESIS);

				query.append(StringPool.CLOSE_PARENTHESIS);
			}

			query.setStringAt(removeConjunction(query.stringAt(query.index() -
						1)), query.index() - 1);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(userId);

				count = (Long)q.uniqueResult();

				finderCache.putResult(FINDER_PATH_WITH_PAGINATION_COUNT_BY_G_U_F,
					finderArgs, count);
			}
			catch (Exception e) {
				finderCache.removeResult(FINDER_PATH_WITH_PAGINATION_COUNT_BY_G_U_F,
					finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of document library file entries that the user has permission to view where groupId = &#63; and userId = &#63; and folderId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param folderId the folder ID
	 * @return the number of matching document library file entries that the user has permission to view
	 */
	@Override
	public int filterCountByG_U_F(long groupId, long userId, long folderId) {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByG_U_F(groupId, userId, folderId);
		}

		StringBundler query = new StringBundler(4);

		query.append(_FILTER_SQL_COUNT_DLFILEENTRY_WHERE);

		query.append(_FINDER_COLUMN_G_U_F_GROUPID_2);

		query.append(_FINDER_COLUMN_G_U_F_USERID_2);

		query.append(_FINDER_COLUMN_G_U_F_FOLDERID_2);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				DLFileEntry.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME,
				com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			qPos.add(userId);

			qPos.add(folderId);

			Long count = (Long)q.uniqueResult();

			return count.intValue();
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Returns the number of document library file entries that the user has permission to view where groupId = &#63; and userId = &#63; and folderId = any &#63;.
	 *
	 * @param groupId the group ID
	 * @param userId the user ID
	 * @param folderIds the folder IDs
	 * @return the number of matching document library file entries that the user has permission to view
	 */
	@Override
	public int filterCountByG_U_F(long groupId, long userId, long[] folderIds) {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByG_U_F(groupId, userId, folderIds);
		}

		if (folderIds == null) {
			folderIds = new long[0];
		}
		else if (folderIds.length > 1) {
			folderIds = ArrayUtil.unique(folderIds);

			Arrays.sort(folderIds);
		}

		StringBundler query = new StringBundler();

		query.append(_FILTER_SQL_COUNT_DLFILEENTRY_WHERE);

		query.append(_FINDER_COLUMN_G_U_F_GROUPID_2);

		query.append(_FINDER_COLUMN_G_U_F_USERID_2);

		if (folderIds.length > 0) {
			query.append(StringPool.OPEN_PARENTHESIS);

			query.append(_FINDER_COLUMN_G_U_F_FOLDERID_7);

			query.append(StringUtil.merge(folderIds));

			query.append(StringPool.CLOSE_PARENTHESIS);

			query.append(StringPool.CLOSE_PARENTHESIS);
		}

		query.setStringAt(removeConjunction(query.stringAt(query.index() - 1)),
			query.index() - 1);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				DLFileEntry.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME,
				com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			qPos.add(userId);

			Long count = (Long)q.uniqueResult();

			return count.intValue();
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	private static final String _FINDER_COLUMN_G_U_F_GROUPID_2 = "dlFileEntry.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_U_F_USERID_2 = "dlFileEntry.userId = ? AND ";
	private static final String _FINDER_COLUMN_G_U_F_FOLDERID_2 = "dlFileEntry.folderId = ?";
	private static final String _FINDER_COLUMN_G_U_F_FOLDERID_7 = "dlFileEntry.folderId IN (";
	public static final FinderPath FINDER_PATH_FETCH_BY_G_F_N = new FinderPath(DLFileEntryModelImpl.ENTITY_CACHE_ENABLED,
			DLFileEntryModelImpl.FINDER_CACHE_ENABLED, DLFileEntryImpl.class,
			FINDER_CLASS_NAME_ENTITY, "fetchByG_F_N",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				String.class.getName()
			},
			DLFileEntryModelImpl.GROUPID_COLUMN_BITMASK |
			DLFileEntryModelImpl.FOLDERID_COLUMN_BITMASK |
			DLFileEntryModelImpl.NAME_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_G_F_N = new FinderPath(DLFileEntryModelImpl.ENTITY_CACHE_ENABLED,
			DLFileEntryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_F_N",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				String.class.getName()
			});

	/**
	 * Returns the document library file entry where groupId = &#63; and folderId = &#63; and name = &#63; or throws a {@link NoSuchFileEntryException} if it could not be found.
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param name the name
	 * @return the matching document library file entry
	 * @throws NoSuchFileEntryException if a matching document library file entry could not be found
	 */
	@Override
	public DLFileEntry findByG_F_N(long groupId, long folderId, String name)
		throws NoSuchFileEntryException {
		DLFileEntry dlFileEntry = fetchByG_F_N(groupId, folderId, name);

		if (dlFileEntry == null) {
			StringBundler msg = new StringBundler(8);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("groupId=");
			msg.append(groupId);

			msg.append(", folderId=");
			msg.append(folderId);

			msg.append(", name=");
			msg.append(name);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isDebugEnabled()) {
				_log.debug(msg.toString());
			}

			throw new NoSuchFileEntryException(msg.toString());
		}

		return dlFileEntry;
	}

	/**
	 * Returns the document library file entry where groupId = &#63; and folderId = &#63; and name = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param name the name
	 * @return the matching document library file entry, or <code>null</code> if a matching document library file entry could not be found
	 */
	@Override
	public DLFileEntry fetchByG_F_N(long groupId, long folderId, String name) {
		return fetchByG_F_N(groupId, folderId, name, true);
	}

	/**
	 * Returns the document library file entry where groupId = &#63; and folderId = &#63; and name = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param name the name
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the matching document library file entry, or <code>null</code> if a matching document library file entry could not be found
	 */
	@Override
	public DLFileEntry fetchByG_F_N(long groupId, long folderId, String name,
		boolean retrieveFromCache) {
		Object[] finderArgs = new Object[] { groupId, folderId, name };

		Object result = null;

		if (retrieveFromCache) {
			result = finderCache.getResult(FINDER_PATH_FETCH_BY_G_F_N,
					finderArgs, this);
		}

		if (result instanceof DLFileEntry) {
			DLFileEntry dlFileEntry = (DLFileEntry)result;

			if ((groupId != dlFileEntry.getGroupId()) ||
					(folderId != dlFileEntry.getFolderId()) ||
					!Objects.equals(name, dlFileEntry.getName())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(5);

			query.append(_SQL_SELECT_DLFILEENTRY_WHERE);

			query.append(_FINDER_COLUMN_G_F_N_GROUPID_2);

			query.append(_FINDER_COLUMN_G_F_N_FOLDERID_2);

			boolean bindName = false;

			if (name == null) {
				query.append(_FINDER_COLUMN_G_F_N_NAME_1);
			}
			else if (name.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_G_F_N_NAME_3);
			}
			else {
				bindName = true;

				query.append(_FINDER_COLUMN_G_F_N_NAME_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(folderId);

				if (bindName) {
					qPos.add(name);
				}

				List<DLFileEntry> list = q.list();

				if (list.isEmpty()) {
					finderCache.putResult(FINDER_PATH_FETCH_BY_G_F_N,
						finderArgs, list);
				}
				else {
					DLFileEntry dlFileEntry = list.get(0);

					result = dlFileEntry;

					cacheResult(dlFileEntry);

					if ((dlFileEntry.getGroupId() != groupId) ||
							(dlFileEntry.getFolderId() != folderId) ||
							(dlFileEntry.getName() == null) ||
							!dlFileEntry.getName().equals(name)) {
						finderCache.putResult(FINDER_PATH_FETCH_BY_G_F_N,
							finderArgs, dlFileEntry);
					}
				}
			}
			catch (Exception e) {
				finderCache.removeResult(FINDER_PATH_FETCH_BY_G_F_N, finderArgs);

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
			return (DLFileEntry)result;
		}
	}

	/**
	 * Removes the document library file entry where groupId = &#63; and folderId = &#63; and name = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param name the name
	 * @return the document library file entry that was removed
	 */
	@Override
	public DLFileEntry removeByG_F_N(long groupId, long folderId, String name)
		throws NoSuchFileEntryException {
		DLFileEntry dlFileEntry = findByG_F_N(groupId, folderId, name);

		return remove(dlFileEntry);
	}

	/**
	 * Returns the number of document library file entries where groupId = &#63; and folderId = &#63; and name = &#63;.
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param name the name
	 * @return the number of matching document library file entries
	 */
	@Override
	public int countByG_F_N(long groupId, long folderId, String name) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_G_F_N;

		Object[] finderArgs = new Object[] { groupId, folderId, name };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_COUNT_DLFILEENTRY_WHERE);

			query.append(_FINDER_COLUMN_G_F_N_GROUPID_2);

			query.append(_FINDER_COLUMN_G_F_N_FOLDERID_2);

			boolean bindName = false;

			if (name == null) {
				query.append(_FINDER_COLUMN_G_F_N_NAME_1);
			}
			else if (name.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_G_F_N_NAME_3);
			}
			else {
				bindName = true;

				query.append(_FINDER_COLUMN_G_F_N_NAME_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(folderId);

				if (bindName) {
					qPos.add(name);
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

	private static final String _FINDER_COLUMN_G_F_N_GROUPID_2 = "dlFileEntry.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_F_N_FOLDERID_2 = "dlFileEntry.folderId = ? AND ";
	private static final String _FINDER_COLUMN_G_F_N_NAME_1 = "dlFileEntry.name IS NULL";
	private static final String _FINDER_COLUMN_G_F_N_NAME_2 = "dlFileEntry.name = ?";
	private static final String _FINDER_COLUMN_G_F_N_NAME_3 = "(dlFileEntry.name IS NULL OR dlFileEntry.name = '')";
	public static final FinderPath FINDER_PATH_FETCH_BY_G_F_FN = new FinderPath(DLFileEntryModelImpl.ENTITY_CACHE_ENABLED,
			DLFileEntryModelImpl.FINDER_CACHE_ENABLED, DLFileEntryImpl.class,
			FINDER_CLASS_NAME_ENTITY, "fetchByG_F_FN",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				String.class.getName()
			},
			DLFileEntryModelImpl.GROUPID_COLUMN_BITMASK |
			DLFileEntryModelImpl.FOLDERID_COLUMN_BITMASK |
			DLFileEntryModelImpl.FILENAME_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_G_F_FN = new FinderPath(DLFileEntryModelImpl.ENTITY_CACHE_ENABLED,
			DLFileEntryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_F_FN",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				String.class.getName()
			});

	/**
	 * Returns the document library file entry where groupId = &#63; and folderId = &#63; and fileName = &#63; or throws a {@link NoSuchFileEntryException} if it could not be found.
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param fileName the file name
	 * @return the matching document library file entry
	 * @throws NoSuchFileEntryException if a matching document library file entry could not be found
	 */
	@Override
	public DLFileEntry findByG_F_FN(long groupId, long folderId, String fileName)
		throws NoSuchFileEntryException {
		DLFileEntry dlFileEntry = fetchByG_F_FN(groupId, folderId, fileName);

		if (dlFileEntry == null) {
			StringBundler msg = new StringBundler(8);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("groupId=");
			msg.append(groupId);

			msg.append(", folderId=");
			msg.append(folderId);

			msg.append(", fileName=");
			msg.append(fileName);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isDebugEnabled()) {
				_log.debug(msg.toString());
			}

			throw new NoSuchFileEntryException(msg.toString());
		}

		return dlFileEntry;
	}

	/**
	 * Returns the document library file entry where groupId = &#63; and folderId = &#63; and fileName = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param fileName the file name
	 * @return the matching document library file entry, or <code>null</code> if a matching document library file entry could not be found
	 */
	@Override
	public DLFileEntry fetchByG_F_FN(long groupId, long folderId,
		String fileName) {
		return fetchByG_F_FN(groupId, folderId, fileName, true);
	}

	/**
	 * Returns the document library file entry where groupId = &#63; and folderId = &#63; and fileName = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param fileName the file name
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the matching document library file entry, or <code>null</code> if a matching document library file entry could not be found
	 */
	@Override
	public DLFileEntry fetchByG_F_FN(long groupId, long folderId,
		String fileName, boolean retrieveFromCache) {
		Object[] finderArgs = new Object[] { groupId, folderId, fileName };

		Object result = null;

		if (retrieveFromCache) {
			result = finderCache.getResult(FINDER_PATH_FETCH_BY_G_F_FN,
					finderArgs, this);
		}

		if (result instanceof DLFileEntry) {
			DLFileEntry dlFileEntry = (DLFileEntry)result;

			if ((groupId != dlFileEntry.getGroupId()) ||
					(folderId != dlFileEntry.getFolderId()) ||
					!Objects.equals(fileName, dlFileEntry.getFileName())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(5);

			query.append(_SQL_SELECT_DLFILEENTRY_WHERE);

			query.append(_FINDER_COLUMN_G_F_FN_GROUPID_2);

			query.append(_FINDER_COLUMN_G_F_FN_FOLDERID_2);

			boolean bindFileName = false;

			if (fileName == null) {
				query.append(_FINDER_COLUMN_G_F_FN_FILENAME_1);
			}
			else if (fileName.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_G_F_FN_FILENAME_3);
			}
			else {
				bindFileName = true;

				query.append(_FINDER_COLUMN_G_F_FN_FILENAME_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(folderId);

				if (bindFileName) {
					qPos.add(fileName);
				}

				List<DLFileEntry> list = q.list();

				if (list.isEmpty()) {
					finderCache.putResult(FINDER_PATH_FETCH_BY_G_F_FN,
						finderArgs, list);
				}
				else {
					DLFileEntry dlFileEntry = list.get(0);

					result = dlFileEntry;

					cacheResult(dlFileEntry);

					if ((dlFileEntry.getGroupId() != groupId) ||
							(dlFileEntry.getFolderId() != folderId) ||
							(dlFileEntry.getFileName() == null) ||
							!dlFileEntry.getFileName().equals(fileName)) {
						finderCache.putResult(FINDER_PATH_FETCH_BY_G_F_FN,
							finderArgs, dlFileEntry);
					}
				}
			}
			catch (Exception e) {
				finderCache.removeResult(FINDER_PATH_FETCH_BY_G_F_FN, finderArgs);

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
			return (DLFileEntry)result;
		}
	}

	/**
	 * Removes the document library file entry where groupId = &#63; and folderId = &#63; and fileName = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param fileName the file name
	 * @return the document library file entry that was removed
	 */
	@Override
	public DLFileEntry removeByG_F_FN(long groupId, long folderId,
		String fileName) throws NoSuchFileEntryException {
		DLFileEntry dlFileEntry = findByG_F_FN(groupId, folderId, fileName);

		return remove(dlFileEntry);
	}

	/**
	 * Returns the number of document library file entries where groupId = &#63; and folderId = &#63; and fileName = &#63;.
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param fileName the file name
	 * @return the number of matching document library file entries
	 */
	@Override
	public int countByG_F_FN(long groupId, long folderId, String fileName) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_G_F_FN;

		Object[] finderArgs = new Object[] { groupId, folderId, fileName };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_COUNT_DLFILEENTRY_WHERE);

			query.append(_FINDER_COLUMN_G_F_FN_GROUPID_2);

			query.append(_FINDER_COLUMN_G_F_FN_FOLDERID_2);

			boolean bindFileName = false;

			if (fileName == null) {
				query.append(_FINDER_COLUMN_G_F_FN_FILENAME_1);
			}
			else if (fileName.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_G_F_FN_FILENAME_3);
			}
			else {
				bindFileName = true;

				query.append(_FINDER_COLUMN_G_F_FN_FILENAME_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(folderId);

				if (bindFileName) {
					qPos.add(fileName);
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

	private static final String _FINDER_COLUMN_G_F_FN_GROUPID_2 = "dlFileEntry.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_F_FN_FOLDERID_2 = "dlFileEntry.folderId = ? AND ";
	private static final String _FINDER_COLUMN_G_F_FN_FILENAME_1 = "dlFileEntry.fileName IS NULL";
	private static final String _FINDER_COLUMN_G_F_FN_FILENAME_2 = "dlFileEntry.fileName = ?";
	private static final String _FINDER_COLUMN_G_F_FN_FILENAME_3 = "(dlFileEntry.fileName IS NULL OR dlFileEntry.fileName = '')";
	public static final FinderPath FINDER_PATH_FETCH_BY_G_F_T = new FinderPath(DLFileEntryModelImpl.ENTITY_CACHE_ENABLED,
			DLFileEntryModelImpl.FINDER_CACHE_ENABLED, DLFileEntryImpl.class,
			FINDER_CLASS_NAME_ENTITY, "fetchByG_F_T",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				String.class.getName()
			},
			DLFileEntryModelImpl.GROUPID_COLUMN_BITMASK |
			DLFileEntryModelImpl.FOLDERID_COLUMN_BITMASK |
			DLFileEntryModelImpl.TITLE_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_G_F_T = new FinderPath(DLFileEntryModelImpl.ENTITY_CACHE_ENABLED,
			DLFileEntryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_F_T",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				String.class.getName()
			});

	/**
	 * Returns the document library file entry where groupId = &#63; and folderId = &#63; and title = &#63; or throws a {@link NoSuchFileEntryException} if it could not be found.
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param title the title
	 * @return the matching document library file entry
	 * @throws NoSuchFileEntryException if a matching document library file entry could not be found
	 */
	@Override
	public DLFileEntry findByG_F_T(long groupId, long folderId, String title)
		throws NoSuchFileEntryException {
		DLFileEntry dlFileEntry = fetchByG_F_T(groupId, folderId, title);

		if (dlFileEntry == null) {
			StringBundler msg = new StringBundler(8);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("groupId=");
			msg.append(groupId);

			msg.append(", folderId=");
			msg.append(folderId);

			msg.append(", title=");
			msg.append(title);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isDebugEnabled()) {
				_log.debug(msg.toString());
			}

			throw new NoSuchFileEntryException(msg.toString());
		}

		return dlFileEntry;
	}

	/**
	 * Returns the document library file entry where groupId = &#63; and folderId = &#63; and title = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param title the title
	 * @return the matching document library file entry, or <code>null</code> if a matching document library file entry could not be found
	 */
	@Override
	public DLFileEntry fetchByG_F_T(long groupId, long folderId, String title) {
		return fetchByG_F_T(groupId, folderId, title, true);
	}

	/**
	 * Returns the document library file entry where groupId = &#63; and folderId = &#63; and title = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param title the title
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the matching document library file entry, or <code>null</code> if a matching document library file entry could not be found
	 */
	@Override
	public DLFileEntry fetchByG_F_T(long groupId, long folderId, String title,
		boolean retrieveFromCache) {
		Object[] finderArgs = new Object[] { groupId, folderId, title };

		Object result = null;

		if (retrieveFromCache) {
			result = finderCache.getResult(FINDER_PATH_FETCH_BY_G_F_T,
					finderArgs, this);
		}

		if (result instanceof DLFileEntry) {
			DLFileEntry dlFileEntry = (DLFileEntry)result;

			if ((groupId != dlFileEntry.getGroupId()) ||
					(folderId != dlFileEntry.getFolderId()) ||
					!Objects.equals(title, dlFileEntry.getTitle())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(5);

			query.append(_SQL_SELECT_DLFILEENTRY_WHERE);

			query.append(_FINDER_COLUMN_G_F_T_GROUPID_2);

			query.append(_FINDER_COLUMN_G_F_T_FOLDERID_2);

			boolean bindTitle = false;

			if (title == null) {
				query.append(_FINDER_COLUMN_G_F_T_TITLE_1);
			}
			else if (title.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_G_F_T_TITLE_3);
			}
			else {
				bindTitle = true;

				query.append(_FINDER_COLUMN_G_F_T_TITLE_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(folderId);

				if (bindTitle) {
					qPos.add(title);
				}

				List<DLFileEntry> list = q.list();

				if (list.isEmpty()) {
					finderCache.putResult(FINDER_PATH_FETCH_BY_G_F_T,
						finderArgs, list);
				}
				else {
					DLFileEntry dlFileEntry = list.get(0);

					result = dlFileEntry;

					cacheResult(dlFileEntry);

					if ((dlFileEntry.getGroupId() != groupId) ||
							(dlFileEntry.getFolderId() != folderId) ||
							(dlFileEntry.getTitle() == null) ||
							!dlFileEntry.getTitle().equals(title)) {
						finderCache.putResult(FINDER_PATH_FETCH_BY_G_F_T,
							finderArgs, dlFileEntry);
					}
				}
			}
			catch (Exception e) {
				finderCache.removeResult(FINDER_PATH_FETCH_BY_G_F_T, finderArgs);

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
			return (DLFileEntry)result;
		}
	}

	/**
	 * Removes the document library file entry where groupId = &#63; and folderId = &#63; and title = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param title the title
	 * @return the document library file entry that was removed
	 */
	@Override
	public DLFileEntry removeByG_F_T(long groupId, long folderId, String title)
		throws NoSuchFileEntryException {
		DLFileEntry dlFileEntry = findByG_F_T(groupId, folderId, title);

		return remove(dlFileEntry);
	}

	/**
	 * Returns the number of document library file entries where groupId = &#63; and folderId = &#63; and title = &#63;.
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param title the title
	 * @return the number of matching document library file entries
	 */
	@Override
	public int countByG_F_T(long groupId, long folderId, String title) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_G_F_T;

		Object[] finderArgs = new Object[] { groupId, folderId, title };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_COUNT_DLFILEENTRY_WHERE);

			query.append(_FINDER_COLUMN_G_F_T_GROUPID_2);

			query.append(_FINDER_COLUMN_G_F_T_FOLDERID_2);

			boolean bindTitle = false;

			if (title == null) {
				query.append(_FINDER_COLUMN_G_F_T_TITLE_1);
			}
			else if (title.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_G_F_T_TITLE_3);
			}
			else {
				bindTitle = true;

				query.append(_FINDER_COLUMN_G_F_T_TITLE_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(folderId);

				if (bindTitle) {
					qPos.add(title);
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

	private static final String _FINDER_COLUMN_G_F_T_GROUPID_2 = "dlFileEntry.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_F_T_FOLDERID_2 = "dlFileEntry.folderId = ? AND ";
	private static final String _FINDER_COLUMN_G_F_T_TITLE_1 = "dlFileEntry.title IS NULL";
	private static final String _FINDER_COLUMN_G_F_T_TITLE_2 = "dlFileEntry.title = ?";
	private static final String _FINDER_COLUMN_G_F_T_TITLE_3 = "(dlFileEntry.title IS NULL OR dlFileEntry.title = '')";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_G_F_F = new FinderPath(DLFileEntryModelImpl.ENTITY_CACHE_ENABLED,
			DLFileEntryModelImpl.FINDER_CACHE_ENABLED, DLFileEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_F_F",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_F_F = new FinderPath(DLFileEntryModelImpl.ENTITY_CACHE_ENABLED,
			DLFileEntryModelImpl.FINDER_CACHE_ENABLED, DLFileEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByG_F_F",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName()
			},
			DLFileEntryModelImpl.GROUPID_COLUMN_BITMASK |
			DLFileEntryModelImpl.FOLDERID_COLUMN_BITMASK |
			DLFileEntryModelImpl.FILEENTRYTYPEID_COLUMN_BITMASK |
			DLFileEntryModelImpl.NAME_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_G_F_F = new FinderPath(DLFileEntryModelImpl.ENTITY_CACHE_ENABLED,
			DLFileEntryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_F_F",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_COUNT_BY_G_F_F = new FinderPath(DLFileEntryModelImpl.ENTITY_CACHE_ENABLED,
			DLFileEntryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "countByG_F_F",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName()
			});

	/**
	 * Returns all the document library file entries where groupId = &#63; and folderId = &#63; and fileEntryTypeId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param fileEntryTypeId the file entry type ID
	 * @return the matching document library file entries
	 */
	@Override
	public List<DLFileEntry> findByG_F_F(long groupId, long folderId,
		long fileEntryTypeId) {
		return findByG_F_F(groupId, folderId, fileEntryTypeId,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the document library file entries where groupId = &#63; and folderId = &#63; and fileEntryTypeId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFileEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param fileEntryTypeId the file entry type ID
	 * @param start the lower bound of the range of document library file entries
	 * @param end the upper bound of the range of document library file entries (not inclusive)
	 * @return the range of matching document library file entries
	 */
	@Override
	public List<DLFileEntry> findByG_F_F(long groupId, long folderId,
		long fileEntryTypeId, int start, int end) {
		return findByG_F_F(groupId, folderId, fileEntryTypeId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the document library file entries where groupId = &#63; and folderId = &#63; and fileEntryTypeId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFileEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param fileEntryTypeId the file entry type ID
	 * @param start the lower bound of the range of document library file entries
	 * @param end the upper bound of the range of document library file entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching document library file entries
	 */
	@Override
	public List<DLFileEntry> findByG_F_F(long groupId, long folderId,
		long fileEntryTypeId, int start, int end,
		OrderByComparator<DLFileEntry> orderByComparator) {
		return findByG_F_F(groupId, folderId, fileEntryTypeId, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the document library file entries where groupId = &#63; and folderId = &#63; and fileEntryTypeId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFileEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param fileEntryTypeId the file entry type ID
	 * @param start the lower bound of the range of document library file entries
	 * @param end the upper bound of the range of document library file entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching document library file entries
	 */
	@Override
	public List<DLFileEntry> findByG_F_F(long groupId, long folderId,
		long fileEntryTypeId, int start, int end,
		OrderByComparator<DLFileEntry> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_F_F;
			finderArgs = new Object[] { groupId, folderId, fileEntryTypeId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_G_F_F;
			finderArgs = new Object[] {
					groupId, folderId, fileEntryTypeId,
					
					start, end, orderByComparator
				};
		}

		List<DLFileEntry> list = null;

		if (retrieveFromCache) {
			list = (List<DLFileEntry>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (DLFileEntry dlFileEntry : list) {
					if ((groupId != dlFileEntry.getGroupId()) ||
							(folderId != dlFileEntry.getFolderId()) ||
							(fileEntryTypeId != dlFileEntry.getFileEntryTypeId())) {
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

			query.append(_SQL_SELECT_DLFILEENTRY_WHERE);

			query.append(_FINDER_COLUMN_G_F_F_GROUPID_2);

			query.append(_FINDER_COLUMN_G_F_F_FOLDERID_2);

			query.append(_FINDER_COLUMN_G_F_F_FILEENTRYTYPEID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(DLFileEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(folderId);

				qPos.add(fileEntryTypeId);

				if (!pagination) {
					list = (List<DLFileEntry>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<DLFileEntry>)QueryUtil.list(q, getDialect(),
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
	 * Returns the first document library file entry in the ordered set where groupId = &#63; and folderId = &#63; and fileEntryTypeId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param fileEntryTypeId the file entry type ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching document library file entry
	 * @throws NoSuchFileEntryException if a matching document library file entry could not be found
	 */
	@Override
	public DLFileEntry findByG_F_F_First(long groupId, long folderId,
		long fileEntryTypeId, OrderByComparator<DLFileEntry> orderByComparator)
		throws NoSuchFileEntryException {
		DLFileEntry dlFileEntry = fetchByG_F_F_First(groupId, folderId,
				fileEntryTypeId, orderByComparator);

		if (dlFileEntry != null) {
			return dlFileEntry;
		}

		StringBundler msg = new StringBundler(8);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", folderId=");
		msg.append(folderId);

		msg.append(", fileEntryTypeId=");
		msg.append(fileEntryTypeId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchFileEntryException(msg.toString());
	}

	/**
	 * Returns the first document library file entry in the ordered set where groupId = &#63; and folderId = &#63; and fileEntryTypeId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param fileEntryTypeId the file entry type ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching document library file entry, or <code>null</code> if a matching document library file entry could not be found
	 */
	@Override
	public DLFileEntry fetchByG_F_F_First(long groupId, long folderId,
		long fileEntryTypeId, OrderByComparator<DLFileEntry> orderByComparator) {
		List<DLFileEntry> list = findByG_F_F(groupId, folderId,
				fileEntryTypeId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last document library file entry in the ordered set where groupId = &#63; and folderId = &#63; and fileEntryTypeId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param fileEntryTypeId the file entry type ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching document library file entry
	 * @throws NoSuchFileEntryException if a matching document library file entry could not be found
	 */
	@Override
	public DLFileEntry findByG_F_F_Last(long groupId, long folderId,
		long fileEntryTypeId, OrderByComparator<DLFileEntry> orderByComparator)
		throws NoSuchFileEntryException {
		DLFileEntry dlFileEntry = fetchByG_F_F_Last(groupId, folderId,
				fileEntryTypeId, orderByComparator);

		if (dlFileEntry != null) {
			return dlFileEntry;
		}

		StringBundler msg = new StringBundler(8);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", folderId=");
		msg.append(folderId);

		msg.append(", fileEntryTypeId=");
		msg.append(fileEntryTypeId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchFileEntryException(msg.toString());
	}

	/**
	 * Returns the last document library file entry in the ordered set where groupId = &#63; and folderId = &#63; and fileEntryTypeId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param fileEntryTypeId the file entry type ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching document library file entry, or <code>null</code> if a matching document library file entry could not be found
	 */
	@Override
	public DLFileEntry fetchByG_F_F_Last(long groupId, long folderId,
		long fileEntryTypeId, OrderByComparator<DLFileEntry> orderByComparator) {
		int count = countByG_F_F(groupId, folderId, fileEntryTypeId);

		if (count == 0) {
			return null;
		}

		List<DLFileEntry> list = findByG_F_F(groupId, folderId,
				fileEntryTypeId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the document library file entries before and after the current document library file entry in the ordered set where groupId = &#63; and folderId = &#63; and fileEntryTypeId = &#63;.
	 *
	 * @param fileEntryId the primary key of the current document library file entry
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param fileEntryTypeId the file entry type ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next document library file entry
	 * @throws NoSuchFileEntryException if a document library file entry with the primary key could not be found
	 */
	@Override
	public DLFileEntry[] findByG_F_F_PrevAndNext(long fileEntryId,
		long groupId, long folderId, long fileEntryTypeId,
		OrderByComparator<DLFileEntry> orderByComparator)
		throws NoSuchFileEntryException {
		DLFileEntry dlFileEntry = findByPrimaryKey(fileEntryId);

		Session session = null;

		try {
			session = openSession();

			DLFileEntry[] array = new DLFileEntryImpl[3];

			array[0] = getByG_F_F_PrevAndNext(session, dlFileEntry, groupId,
					folderId, fileEntryTypeId, orderByComparator, true);

			array[1] = dlFileEntry;

			array[2] = getByG_F_F_PrevAndNext(session, dlFileEntry, groupId,
					folderId, fileEntryTypeId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected DLFileEntry getByG_F_F_PrevAndNext(Session session,
		DLFileEntry dlFileEntry, long groupId, long folderId,
		long fileEntryTypeId, OrderByComparator<DLFileEntry> orderByComparator,
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

		query.append(_SQL_SELECT_DLFILEENTRY_WHERE);

		query.append(_FINDER_COLUMN_G_F_F_GROUPID_2);

		query.append(_FINDER_COLUMN_G_F_F_FOLDERID_2);

		query.append(_FINDER_COLUMN_G_F_F_FILEENTRYTYPEID_2);

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
			query.append(DLFileEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		qPos.add(folderId);

		qPos.add(fileEntryTypeId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(dlFileEntry);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<DLFileEntry> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the document library file entries that the user has permission to view where groupId = &#63; and folderId = &#63; and fileEntryTypeId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param fileEntryTypeId the file entry type ID
	 * @return the matching document library file entries that the user has permission to view
	 */
	@Override
	public List<DLFileEntry> filterFindByG_F_F(long groupId, long folderId,
		long fileEntryTypeId) {
		return filterFindByG_F_F(groupId, folderId, fileEntryTypeId,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the document library file entries that the user has permission to view where groupId = &#63; and folderId = &#63; and fileEntryTypeId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFileEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param fileEntryTypeId the file entry type ID
	 * @param start the lower bound of the range of document library file entries
	 * @param end the upper bound of the range of document library file entries (not inclusive)
	 * @return the range of matching document library file entries that the user has permission to view
	 */
	@Override
	public List<DLFileEntry> filterFindByG_F_F(long groupId, long folderId,
		long fileEntryTypeId, int start, int end) {
		return filterFindByG_F_F(groupId, folderId, fileEntryTypeId, start,
			end, null);
	}

	/**
	 * Returns an ordered range of all the document library file entries that the user has permissions to view where groupId = &#63; and folderId = &#63; and fileEntryTypeId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFileEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param fileEntryTypeId the file entry type ID
	 * @param start the lower bound of the range of document library file entries
	 * @param end the upper bound of the range of document library file entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching document library file entries that the user has permission to view
	 */
	@Override
	public List<DLFileEntry> filterFindByG_F_F(long groupId, long folderId,
		long fileEntryTypeId, int start, int end,
		OrderByComparator<DLFileEntry> orderByComparator) {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_F_F(groupId, folderId, fileEntryTypeId, start, end,
				orderByComparator);
		}

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(5 +
					(orderByComparator.getOrderByFields().length * 2));
		}
		else {
			query = new StringBundler(6);
		}

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_DLFILEENTRY_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_DLFILEENTRY_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_G_F_F_GROUPID_2);

		query.append(_FINDER_COLUMN_G_F_F_FOLDERID_2);

		query.append(_FINDER_COLUMN_G_F_F_FILEENTRYTYPEID_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_DLFILEENTRY_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			if (getDB().isSupportsInlineDistinct()) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator, true);
			}
			else {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_TABLE,
					orderByComparator, true);
			}
		}
		else {
			if (getDB().isSupportsInlineDistinct()) {
				query.append(DLFileEntryModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(DLFileEntryModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				DLFileEntry.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				q.addEntity(_FILTER_ENTITY_ALIAS, DLFileEntryImpl.class);
			}
			else {
				q.addEntity(_FILTER_ENTITY_TABLE, DLFileEntryImpl.class);
			}

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			qPos.add(folderId);

			qPos.add(fileEntryTypeId);

			return (List<DLFileEntry>)QueryUtil.list(q, getDialect(), start, end);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Returns the document library file entries before and after the current document library file entry in the ordered set of document library file entries that the user has permission to view where groupId = &#63; and folderId = &#63; and fileEntryTypeId = &#63;.
	 *
	 * @param fileEntryId the primary key of the current document library file entry
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param fileEntryTypeId the file entry type ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next document library file entry
	 * @throws NoSuchFileEntryException if a document library file entry with the primary key could not be found
	 */
	@Override
	public DLFileEntry[] filterFindByG_F_F_PrevAndNext(long fileEntryId,
		long groupId, long folderId, long fileEntryTypeId,
		OrderByComparator<DLFileEntry> orderByComparator)
		throws NoSuchFileEntryException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_F_F_PrevAndNext(fileEntryId, groupId, folderId,
				fileEntryTypeId, orderByComparator);
		}

		DLFileEntry dlFileEntry = findByPrimaryKey(fileEntryId);

		Session session = null;

		try {
			session = openSession();

			DLFileEntry[] array = new DLFileEntryImpl[3];

			array[0] = filterGetByG_F_F_PrevAndNext(session, dlFileEntry,
					groupId, folderId, fileEntryTypeId, orderByComparator, true);

			array[1] = dlFileEntry;

			array[2] = filterGetByG_F_F_PrevAndNext(session, dlFileEntry,
					groupId, folderId, fileEntryTypeId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected DLFileEntry filterGetByG_F_F_PrevAndNext(Session session,
		DLFileEntry dlFileEntry, long groupId, long folderId,
		long fileEntryTypeId, OrderByComparator<DLFileEntry> orderByComparator,
		boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(7 +
					(orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(6);
		}

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_DLFILEENTRY_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_DLFILEENTRY_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_G_F_F_GROUPID_2);

		query.append(_FINDER_COLUMN_G_F_F_FOLDERID_2);

		query.append(_FINDER_COLUMN_G_F_F_FILEENTRYTYPEID_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_DLFILEENTRY_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			String[] orderByConditionFields = orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				query.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				if (getDB().isSupportsInlineDistinct()) {
					query.append(_ORDER_BY_ENTITY_ALIAS);
				}
				else {
					query.append(_ORDER_BY_ENTITY_TABLE);
				}

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
				if (getDB().isSupportsInlineDistinct()) {
					query.append(_ORDER_BY_ENTITY_ALIAS);
				}
				else {
					query.append(_ORDER_BY_ENTITY_TABLE);
				}

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
			if (getDB().isSupportsInlineDistinct()) {
				query.append(DLFileEntryModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(DLFileEntryModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				DLFileEntry.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		SQLQuery q = session.createSynchronizedSQLQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		if (getDB().isSupportsInlineDistinct()) {
			q.addEntity(_FILTER_ENTITY_ALIAS, DLFileEntryImpl.class);
		}
		else {
			q.addEntity(_FILTER_ENTITY_TABLE, DLFileEntryImpl.class);
		}

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		qPos.add(folderId);

		qPos.add(fileEntryTypeId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(dlFileEntry);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<DLFileEntry> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the document library file entries that the user has permission to view where groupId = &#63; and folderId = any &#63; and fileEntryTypeId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param folderIds the folder IDs
	 * @param fileEntryTypeId the file entry type ID
	 * @return the matching document library file entries that the user has permission to view
	 */
	@Override
	public List<DLFileEntry> filterFindByG_F_F(long groupId, long[] folderIds,
		long fileEntryTypeId) {
		return filterFindByG_F_F(groupId, folderIds, fileEntryTypeId,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the document library file entries that the user has permission to view where groupId = &#63; and folderId = any &#63; and fileEntryTypeId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFileEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param folderIds the folder IDs
	 * @param fileEntryTypeId the file entry type ID
	 * @param start the lower bound of the range of document library file entries
	 * @param end the upper bound of the range of document library file entries (not inclusive)
	 * @return the range of matching document library file entries that the user has permission to view
	 */
	@Override
	public List<DLFileEntry> filterFindByG_F_F(long groupId, long[] folderIds,
		long fileEntryTypeId, int start, int end) {
		return filterFindByG_F_F(groupId, folderIds, fileEntryTypeId, start,
			end, null);
	}

	/**
	 * Returns an ordered range of all the document library file entries that the user has permission to view where groupId = &#63; and folderId = any &#63; and fileEntryTypeId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFileEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param folderIds the folder IDs
	 * @param fileEntryTypeId the file entry type ID
	 * @param start the lower bound of the range of document library file entries
	 * @param end the upper bound of the range of document library file entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching document library file entries that the user has permission to view
	 */
	@Override
	public List<DLFileEntry> filterFindByG_F_F(long groupId, long[] folderIds,
		long fileEntryTypeId, int start, int end,
		OrderByComparator<DLFileEntry> orderByComparator) {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_F_F(groupId, folderIds, fileEntryTypeId, start, end,
				orderByComparator);
		}

		if (folderIds == null) {
			folderIds = new long[0];
		}
		else if (folderIds.length > 1) {
			folderIds = ArrayUtil.unique(folderIds);

			Arrays.sort(folderIds);
		}

		StringBundler query = new StringBundler();

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_DLFILEENTRY_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_DLFILEENTRY_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_G_F_F_GROUPID_2);

		if (folderIds.length > 0) {
			query.append(StringPool.OPEN_PARENTHESIS);

			query.append(_FINDER_COLUMN_G_F_F_FOLDERID_7);

			query.append(StringUtil.merge(folderIds));

			query.append(StringPool.CLOSE_PARENTHESIS);

			query.append(StringPool.CLOSE_PARENTHESIS);

			query.append(WHERE_AND);
		}

		query.append(_FINDER_COLUMN_G_F_F_FILEENTRYTYPEID_2);

		query.setStringAt(removeConjunction(query.stringAt(query.index() - 1)),
			query.index() - 1);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_DLFILEENTRY_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			if (getDB().isSupportsInlineDistinct()) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator, true);
			}
			else {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_TABLE,
					orderByComparator, true);
			}
		}
		else {
			if (getDB().isSupportsInlineDistinct()) {
				query.append(DLFileEntryModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(DLFileEntryModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				DLFileEntry.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				q.addEntity(_FILTER_ENTITY_ALIAS, DLFileEntryImpl.class);
			}
			else {
				q.addEntity(_FILTER_ENTITY_TABLE, DLFileEntryImpl.class);
			}

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			qPos.add(fileEntryTypeId);

			return (List<DLFileEntry>)QueryUtil.list(q, getDialect(), start, end);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Returns all the document library file entries where groupId = &#63; and folderId = any &#63; and fileEntryTypeId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFileEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param folderIds the folder IDs
	 * @param fileEntryTypeId the file entry type ID
	 * @return the matching document library file entries
	 */
	@Override
	public List<DLFileEntry> findByG_F_F(long groupId, long[] folderIds,
		long fileEntryTypeId) {
		return findByG_F_F(groupId, folderIds, fileEntryTypeId,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the document library file entries where groupId = &#63; and folderId = any &#63; and fileEntryTypeId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFileEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param folderIds the folder IDs
	 * @param fileEntryTypeId the file entry type ID
	 * @param start the lower bound of the range of document library file entries
	 * @param end the upper bound of the range of document library file entries (not inclusive)
	 * @return the range of matching document library file entries
	 */
	@Override
	public List<DLFileEntry> findByG_F_F(long groupId, long[] folderIds,
		long fileEntryTypeId, int start, int end) {
		return findByG_F_F(groupId, folderIds, fileEntryTypeId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the document library file entries where groupId = &#63; and folderId = any &#63; and fileEntryTypeId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFileEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param folderIds the folder IDs
	 * @param fileEntryTypeId the file entry type ID
	 * @param start the lower bound of the range of document library file entries
	 * @param end the upper bound of the range of document library file entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching document library file entries
	 */
	@Override
	public List<DLFileEntry> findByG_F_F(long groupId, long[] folderIds,
		long fileEntryTypeId, int start, int end,
		OrderByComparator<DLFileEntry> orderByComparator) {
		return findByG_F_F(groupId, folderIds, fileEntryTypeId, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the document library file entries where groupId = &#63; and folderId = &#63; and fileEntryTypeId = &#63;, optionally using the finder cache.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFileEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param fileEntryTypeId the file entry type ID
	 * @param start the lower bound of the range of document library file entries
	 * @param end the upper bound of the range of document library file entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching document library file entries
	 */
	@Override
	public List<DLFileEntry> findByG_F_F(long groupId, long[] folderIds,
		long fileEntryTypeId, int start, int end,
		OrderByComparator<DLFileEntry> orderByComparator,
		boolean retrieveFromCache) {
		if (folderIds == null) {
			folderIds = new long[0];
		}
		else if (folderIds.length > 1) {
			folderIds = ArrayUtil.unique(folderIds);

			Arrays.sort(folderIds);
		}

		if (folderIds.length == 1) {
			return findByG_F_F(groupId, folderIds[0], fileEntryTypeId, start,
				end, orderByComparator);
		}

		boolean pagination = true;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderArgs = new Object[] {
					groupId, StringUtil.merge(folderIds), fileEntryTypeId
				};
		}
		else {
			finderArgs = new Object[] {
					groupId, StringUtil.merge(folderIds), fileEntryTypeId,
					
					start, end, orderByComparator
				};
		}

		List<DLFileEntry> list = null;

		if (retrieveFromCache) {
			list = (List<DLFileEntry>)finderCache.getResult(FINDER_PATH_WITH_PAGINATION_FIND_BY_G_F_F,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (DLFileEntry dlFileEntry : list) {
					if ((groupId != dlFileEntry.getGroupId()) ||
							!ArrayUtil.contains(folderIds,
								dlFileEntry.getFolderId()) ||
							(fileEntryTypeId != dlFileEntry.getFileEntryTypeId())) {
						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler query = new StringBundler();

			query.append(_SQL_SELECT_DLFILEENTRY_WHERE);

			query.append(_FINDER_COLUMN_G_F_F_GROUPID_2);

			if (folderIds.length > 0) {
				query.append(StringPool.OPEN_PARENTHESIS);

				query.append(_FINDER_COLUMN_G_F_F_FOLDERID_7);

				query.append(StringUtil.merge(folderIds));

				query.append(StringPool.CLOSE_PARENTHESIS);

				query.append(StringPool.CLOSE_PARENTHESIS);

				query.append(WHERE_AND);
			}

			query.append(_FINDER_COLUMN_G_F_F_FILEENTRYTYPEID_2);

			query.setStringAt(removeConjunction(query.stringAt(query.index() -
						1)), query.index() - 1);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(DLFileEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(fileEntryTypeId);

				if (!pagination) {
					list = (List<DLFileEntry>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<DLFileEntry>)QueryUtil.list(q, getDialect(),
							start, end);
				}

				cacheResult(list);

				finderCache.putResult(FINDER_PATH_WITH_PAGINATION_FIND_BY_G_F_F,
					finderArgs, list);
			}
			catch (Exception e) {
				finderCache.removeResult(FINDER_PATH_WITH_PAGINATION_FIND_BY_G_F_F,
					finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Removes all the document library file entries where groupId = &#63; and folderId = &#63; and fileEntryTypeId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param fileEntryTypeId the file entry type ID
	 */
	@Override
	public void removeByG_F_F(long groupId, long folderId, long fileEntryTypeId) {
		for (DLFileEntry dlFileEntry : findByG_F_F(groupId, folderId,
				fileEntryTypeId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(dlFileEntry);
		}
	}

	/**
	 * Returns the number of document library file entries where groupId = &#63; and folderId = &#63; and fileEntryTypeId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param fileEntryTypeId the file entry type ID
	 * @return the number of matching document library file entries
	 */
	@Override
	public int countByG_F_F(long groupId, long folderId, long fileEntryTypeId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_G_F_F;

		Object[] finderArgs = new Object[] { groupId, folderId, fileEntryTypeId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_COUNT_DLFILEENTRY_WHERE);

			query.append(_FINDER_COLUMN_G_F_F_GROUPID_2);

			query.append(_FINDER_COLUMN_G_F_F_FOLDERID_2);

			query.append(_FINDER_COLUMN_G_F_F_FILEENTRYTYPEID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(folderId);

				qPos.add(fileEntryTypeId);

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
	 * Returns the number of document library file entries where groupId = &#63; and folderId = any &#63; and fileEntryTypeId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param folderIds the folder IDs
	 * @param fileEntryTypeId the file entry type ID
	 * @return the number of matching document library file entries
	 */
	@Override
	public int countByG_F_F(long groupId, long[] folderIds, long fileEntryTypeId) {
		if (folderIds == null) {
			folderIds = new long[0];
		}
		else if (folderIds.length > 1) {
			folderIds = ArrayUtil.unique(folderIds);

			Arrays.sort(folderIds);
		}

		Object[] finderArgs = new Object[] {
				groupId, StringUtil.merge(folderIds), fileEntryTypeId
			};

		Long count = (Long)finderCache.getResult(FINDER_PATH_WITH_PAGINATION_COUNT_BY_G_F_F,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler();

			query.append(_SQL_COUNT_DLFILEENTRY_WHERE);

			query.append(_FINDER_COLUMN_G_F_F_GROUPID_2);

			if (folderIds.length > 0) {
				query.append(StringPool.OPEN_PARENTHESIS);

				query.append(_FINDER_COLUMN_G_F_F_FOLDERID_7);

				query.append(StringUtil.merge(folderIds));

				query.append(StringPool.CLOSE_PARENTHESIS);

				query.append(StringPool.CLOSE_PARENTHESIS);

				query.append(WHERE_AND);
			}

			query.append(_FINDER_COLUMN_G_F_F_FILEENTRYTYPEID_2);

			query.setStringAt(removeConjunction(query.stringAt(query.index() -
						1)), query.index() - 1);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(fileEntryTypeId);

				count = (Long)q.uniqueResult();

				finderCache.putResult(FINDER_PATH_WITH_PAGINATION_COUNT_BY_G_F_F,
					finderArgs, count);
			}
			catch (Exception e) {
				finderCache.removeResult(FINDER_PATH_WITH_PAGINATION_COUNT_BY_G_F_F,
					finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of document library file entries that the user has permission to view where groupId = &#63; and folderId = &#63; and fileEntryTypeId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param fileEntryTypeId the file entry type ID
	 * @return the number of matching document library file entries that the user has permission to view
	 */
	@Override
	public int filterCountByG_F_F(long groupId, long folderId,
		long fileEntryTypeId) {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByG_F_F(groupId, folderId, fileEntryTypeId);
		}

		StringBundler query = new StringBundler(4);

		query.append(_FILTER_SQL_COUNT_DLFILEENTRY_WHERE);

		query.append(_FINDER_COLUMN_G_F_F_GROUPID_2);

		query.append(_FINDER_COLUMN_G_F_F_FOLDERID_2);

		query.append(_FINDER_COLUMN_G_F_F_FILEENTRYTYPEID_2);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				DLFileEntry.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME,
				com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			qPos.add(folderId);

			qPos.add(fileEntryTypeId);

			Long count = (Long)q.uniqueResult();

			return count.intValue();
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Returns the number of document library file entries that the user has permission to view where groupId = &#63; and folderId = any &#63; and fileEntryTypeId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param folderIds the folder IDs
	 * @param fileEntryTypeId the file entry type ID
	 * @return the number of matching document library file entries that the user has permission to view
	 */
	@Override
	public int filterCountByG_F_F(long groupId, long[] folderIds,
		long fileEntryTypeId) {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByG_F_F(groupId, folderIds, fileEntryTypeId);
		}

		if (folderIds == null) {
			folderIds = new long[0];
		}
		else if (folderIds.length > 1) {
			folderIds = ArrayUtil.unique(folderIds);

			Arrays.sort(folderIds);
		}

		StringBundler query = new StringBundler();

		query.append(_FILTER_SQL_COUNT_DLFILEENTRY_WHERE);

		query.append(_FINDER_COLUMN_G_F_F_GROUPID_2);

		if (folderIds.length > 0) {
			query.append(StringPool.OPEN_PARENTHESIS);

			query.append(_FINDER_COLUMN_G_F_F_FOLDERID_7);

			query.append(StringUtil.merge(folderIds));

			query.append(StringPool.CLOSE_PARENTHESIS);

			query.append(StringPool.CLOSE_PARENTHESIS);

			query.append(WHERE_AND);
		}

		query.append(_FINDER_COLUMN_G_F_F_FILEENTRYTYPEID_2);

		query.setStringAt(removeConjunction(query.stringAt(query.index() - 1)),
			query.index() - 1);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				DLFileEntry.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME,
				com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			qPos.add(fileEntryTypeId);

			Long count = (Long)q.uniqueResult();

			return count.intValue();
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	private static final String _FINDER_COLUMN_G_F_F_GROUPID_2 = "dlFileEntry.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_F_F_FOLDERID_2 = "dlFileEntry.folderId = ? AND ";
	private static final String _FINDER_COLUMN_G_F_F_FOLDERID_7 = "dlFileEntry.folderId IN (";
	private static final String _FINDER_COLUMN_G_F_F_FILEENTRYTYPEID_2 = "dlFileEntry.fileEntryTypeId = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_S_L_C1_C2 =
		new FinderPath(DLFileEntryModelImpl.ENTITY_CACHE_ENABLED,
			DLFileEntryModelImpl.FINDER_CACHE_ENABLED, DLFileEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByS_L_C1_C2",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName(),
				Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_S_L_C1_C2 =
		new FinderPath(DLFileEntryModelImpl.ENTITY_CACHE_ENABLED,
			DLFileEntryModelImpl.FINDER_CACHE_ENABLED, DLFileEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByS_L_C1_C2",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName(),
				Long.class.getName()
			},
			DLFileEntryModelImpl.SMALLIMAGEID_COLUMN_BITMASK |
			DLFileEntryModelImpl.LARGEIMAGEID_COLUMN_BITMASK |
			DLFileEntryModelImpl.CUSTOM1IMAGEID_COLUMN_BITMASK |
			DLFileEntryModelImpl.CUSTOM2IMAGEID_COLUMN_BITMASK |
			DLFileEntryModelImpl.FOLDERID_COLUMN_BITMASK |
			DLFileEntryModelImpl.NAME_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_S_L_C1_C2 = new FinderPath(DLFileEntryModelImpl.ENTITY_CACHE_ENABLED,
			DLFileEntryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByS_L_C1_C2",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName(),
				Long.class.getName()
			});

	/**
	 * Returns all the document library file entries where smallImageId = &#63; and largeImageId = &#63; and custom1ImageId = &#63; and custom2ImageId = &#63;.
	 *
	 * @param smallImageId the small image ID
	 * @param largeImageId the large image ID
	 * @param custom1ImageId the custom1 image ID
	 * @param custom2ImageId the custom2 image ID
	 * @return the matching document library file entries
	 */
	@Override
	public List<DLFileEntry> findByS_L_C1_C2(long smallImageId,
		long largeImageId, long custom1ImageId, long custom2ImageId) {
		return findByS_L_C1_C2(smallImageId, largeImageId, custom1ImageId,
			custom2ImageId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the document library file entries where smallImageId = &#63; and largeImageId = &#63; and custom1ImageId = &#63; and custom2ImageId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFileEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param smallImageId the small image ID
	 * @param largeImageId the large image ID
	 * @param custom1ImageId the custom1 image ID
	 * @param custom2ImageId the custom2 image ID
	 * @param start the lower bound of the range of document library file entries
	 * @param end the upper bound of the range of document library file entries (not inclusive)
	 * @return the range of matching document library file entries
	 */
	@Override
	public List<DLFileEntry> findByS_L_C1_C2(long smallImageId,
		long largeImageId, long custom1ImageId, long custom2ImageId, int start,
		int end) {
		return findByS_L_C1_C2(smallImageId, largeImageId, custom1ImageId,
			custom2ImageId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the document library file entries where smallImageId = &#63; and largeImageId = &#63; and custom1ImageId = &#63; and custom2ImageId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFileEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param smallImageId the small image ID
	 * @param largeImageId the large image ID
	 * @param custom1ImageId the custom1 image ID
	 * @param custom2ImageId the custom2 image ID
	 * @param start the lower bound of the range of document library file entries
	 * @param end the upper bound of the range of document library file entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching document library file entries
	 */
	@Override
	public List<DLFileEntry> findByS_L_C1_C2(long smallImageId,
		long largeImageId, long custom1ImageId, long custom2ImageId, int start,
		int end, OrderByComparator<DLFileEntry> orderByComparator) {
		return findByS_L_C1_C2(smallImageId, largeImageId, custom1ImageId,
			custom2ImageId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the document library file entries where smallImageId = &#63; and largeImageId = &#63; and custom1ImageId = &#63; and custom2ImageId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFileEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param smallImageId the small image ID
	 * @param largeImageId the large image ID
	 * @param custom1ImageId the custom1 image ID
	 * @param custom2ImageId the custom2 image ID
	 * @param start the lower bound of the range of document library file entries
	 * @param end the upper bound of the range of document library file entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching document library file entries
	 */
	@Override
	public List<DLFileEntry> findByS_L_C1_C2(long smallImageId,
		long largeImageId, long custom1ImageId, long custom2ImageId, int start,
		int end, OrderByComparator<DLFileEntry> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_S_L_C1_C2;
			finderArgs = new Object[] {
					smallImageId, largeImageId, custom1ImageId, custom2ImageId
				};
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_S_L_C1_C2;
			finderArgs = new Object[] {
					smallImageId, largeImageId, custom1ImageId, custom2ImageId,
					
					start, end, orderByComparator
				};
		}

		List<DLFileEntry> list = null;

		if (retrieveFromCache) {
			list = (List<DLFileEntry>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (DLFileEntry dlFileEntry : list) {
					if ((smallImageId != dlFileEntry.getSmallImageId()) ||
							(largeImageId != dlFileEntry.getLargeImageId()) ||
							(custom1ImageId != dlFileEntry.getCustom1ImageId()) ||
							(custom2ImageId != dlFileEntry.getCustom2ImageId())) {
						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(6 +
						(orderByComparator.getOrderByFields().length * 2));
			}
			else {
				query = new StringBundler(6);
			}

			query.append(_SQL_SELECT_DLFILEENTRY_WHERE);

			query.append(_FINDER_COLUMN_S_L_C1_C2_SMALLIMAGEID_2);

			query.append(_FINDER_COLUMN_S_L_C1_C2_LARGEIMAGEID_2);

			query.append(_FINDER_COLUMN_S_L_C1_C2_CUSTOM1IMAGEID_2);

			query.append(_FINDER_COLUMN_S_L_C1_C2_CUSTOM2IMAGEID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(DLFileEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(smallImageId);

				qPos.add(largeImageId);

				qPos.add(custom1ImageId);

				qPos.add(custom2ImageId);

				if (!pagination) {
					list = (List<DLFileEntry>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<DLFileEntry>)QueryUtil.list(q, getDialect(),
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
	 * Returns the first document library file entry in the ordered set where smallImageId = &#63; and largeImageId = &#63; and custom1ImageId = &#63; and custom2ImageId = &#63;.
	 *
	 * @param smallImageId the small image ID
	 * @param largeImageId the large image ID
	 * @param custom1ImageId the custom1 image ID
	 * @param custom2ImageId the custom2 image ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching document library file entry
	 * @throws NoSuchFileEntryException if a matching document library file entry could not be found
	 */
	@Override
	public DLFileEntry findByS_L_C1_C2_First(long smallImageId,
		long largeImageId, long custom1ImageId, long custom2ImageId,
		OrderByComparator<DLFileEntry> orderByComparator)
		throws NoSuchFileEntryException {
		DLFileEntry dlFileEntry = fetchByS_L_C1_C2_First(smallImageId,
				largeImageId, custom1ImageId, custom2ImageId, orderByComparator);

		if (dlFileEntry != null) {
			return dlFileEntry;
		}

		StringBundler msg = new StringBundler(10);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("smallImageId=");
		msg.append(smallImageId);

		msg.append(", largeImageId=");
		msg.append(largeImageId);

		msg.append(", custom1ImageId=");
		msg.append(custom1ImageId);

		msg.append(", custom2ImageId=");
		msg.append(custom2ImageId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchFileEntryException(msg.toString());
	}

	/**
	 * Returns the first document library file entry in the ordered set where smallImageId = &#63; and largeImageId = &#63; and custom1ImageId = &#63; and custom2ImageId = &#63;.
	 *
	 * @param smallImageId the small image ID
	 * @param largeImageId the large image ID
	 * @param custom1ImageId the custom1 image ID
	 * @param custom2ImageId the custom2 image ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching document library file entry, or <code>null</code> if a matching document library file entry could not be found
	 */
	@Override
	public DLFileEntry fetchByS_L_C1_C2_First(long smallImageId,
		long largeImageId, long custom1ImageId, long custom2ImageId,
		OrderByComparator<DLFileEntry> orderByComparator) {
		List<DLFileEntry> list = findByS_L_C1_C2(smallImageId, largeImageId,
				custom1ImageId, custom2ImageId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last document library file entry in the ordered set where smallImageId = &#63; and largeImageId = &#63; and custom1ImageId = &#63; and custom2ImageId = &#63;.
	 *
	 * @param smallImageId the small image ID
	 * @param largeImageId the large image ID
	 * @param custom1ImageId the custom1 image ID
	 * @param custom2ImageId the custom2 image ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching document library file entry
	 * @throws NoSuchFileEntryException if a matching document library file entry could not be found
	 */
	@Override
	public DLFileEntry findByS_L_C1_C2_Last(long smallImageId,
		long largeImageId, long custom1ImageId, long custom2ImageId,
		OrderByComparator<DLFileEntry> orderByComparator)
		throws NoSuchFileEntryException {
		DLFileEntry dlFileEntry = fetchByS_L_C1_C2_Last(smallImageId,
				largeImageId, custom1ImageId, custom2ImageId, orderByComparator);

		if (dlFileEntry != null) {
			return dlFileEntry;
		}

		StringBundler msg = new StringBundler(10);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("smallImageId=");
		msg.append(smallImageId);

		msg.append(", largeImageId=");
		msg.append(largeImageId);

		msg.append(", custom1ImageId=");
		msg.append(custom1ImageId);

		msg.append(", custom2ImageId=");
		msg.append(custom2ImageId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchFileEntryException(msg.toString());
	}

	/**
	 * Returns the last document library file entry in the ordered set where smallImageId = &#63; and largeImageId = &#63; and custom1ImageId = &#63; and custom2ImageId = &#63;.
	 *
	 * @param smallImageId the small image ID
	 * @param largeImageId the large image ID
	 * @param custom1ImageId the custom1 image ID
	 * @param custom2ImageId the custom2 image ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching document library file entry, or <code>null</code> if a matching document library file entry could not be found
	 */
	@Override
	public DLFileEntry fetchByS_L_C1_C2_Last(long smallImageId,
		long largeImageId, long custom1ImageId, long custom2ImageId,
		OrderByComparator<DLFileEntry> orderByComparator) {
		int count = countByS_L_C1_C2(smallImageId, largeImageId,
				custom1ImageId, custom2ImageId);

		if (count == 0) {
			return null;
		}

		List<DLFileEntry> list = findByS_L_C1_C2(smallImageId, largeImageId,
				custom1ImageId, custom2ImageId, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the document library file entries before and after the current document library file entry in the ordered set where smallImageId = &#63; and largeImageId = &#63; and custom1ImageId = &#63; and custom2ImageId = &#63;.
	 *
	 * @param fileEntryId the primary key of the current document library file entry
	 * @param smallImageId the small image ID
	 * @param largeImageId the large image ID
	 * @param custom1ImageId the custom1 image ID
	 * @param custom2ImageId the custom2 image ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next document library file entry
	 * @throws NoSuchFileEntryException if a document library file entry with the primary key could not be found
	 */
	@Override
	public DLFileEntry[] findByS_L_C1_C2_PrevAndNext(long fileEntryId,
		long smallImageId, long largeImageId, long custom1ImageId,
		long custom2ImageId, OrderByComparator<DLFileEntry> orderByComparator)
		throws NoSuchFileEntryException {
		DLFileEntry dlFileEntry = findByPrimaryKey(fileEntryId);

		Session session = null;

		try {
			session = openSession();

			DLFileEntry[] array = new DLFileEntryImpl[3];

			array[0] = getByS_L_C1_C2_PrevAndNext(session, dlFileEntry,
					smallImageId, largeImageId, custom1ImageId, custom2ImageId,
					orderByComparator, true);

			array[1] = dlFileEntry;

			array[2] = getByS_L_C1_C2_PrevAndNext(session, dlFileEntry,
					smallImageId, largeImageId, custom1ImageId, custom2ImageId,
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

	protected DLFileEntry getByS_L_C1_C2_PrevAndNext(Session session,
		DLFileEntry dlFileEntry, long smallImageId, long largeImageId,
		long custom1ImageId, long custom2ImageId,
		OrderByComparator<DLFileEntry> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(7 +
					(orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(6);
		}

		query.append(_SQL_SELECT_DLFILEENTRY_WHERE);

		query.append(_FINDER_COLUMN_S_L_C1_C2_SMALLIMAGEID_2);

		query.append(_FINDER_COLUMN_S_L_C1_C2_LARGEIMAGEID_2);

		query.append(_FINDER_COLUMN_S_L_C1_C2_CUSTOM1IMAGEID_2);

		query.append(_FINDER_COLUMN_S_L_C1_C2_CUSTOM2IMAGEID_2);

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
			query.append(DLFileEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(smallImageId);

		qPos.add(largeImageId);

		qPos.add(custom1ImageId);

		qPos.add(custom2ImageId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(dlFileEntry);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<DLFileEntry> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the document library file entries where smallImageId = &#63; and largeImageId = &#63; and custom1ImageId = &#63; and custom2ImageId = &#63; from the database.
	 *
	 * @param smallImageId the small image ID
	 * @param largeImageId the large image ID
	 * @param custom1ImageId the custom1 image ID
	 * @param custom2ImageId the custom2 image ID
	 */
	@Override
	public void removeByS_L_C1_C2(long smallImageId, long largeImageId,
		long custom1ImageId, long custom2ImageId) {
		for (DLFileEntry dlFileEntry : findByS_L_C1_C2(smallImageId,
				largeImageId, custom1ImageId, custom2ImageId,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(dlFileEntry);
		}
	}

	/**
	 * Returns the number of document library file entries where smallImageId = &#63; and largeImageId = &#63; and custom1ImageId = &#63; and custom2ImageId = &#63;.
	 *
	 * @param smallImageId the small image ID
	 * @param largeImageId the large image ID
	 * @param custom1ImageId the custom1 image ID
	 * @param custom2ImageId the custom2 image ID
	 * @return the number of matching document library file entries
	 */
	@Override
	public int countByS_L_C1_C2(long smallImageId, long largeImageId,
		long custom1ImageId, long custom2ImageId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_S_L_C1_C2;

		Object[] finderArgs = new Object[] {
				smallImageId, largeImageId, custom1ImageId, custom2ImageId
			};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(5);

			query.append(_SQL_COUNT_DLFILEENTRY_WHERE);

			query.append(_FINDER_COLUMN_S_L_C1_C2_SMALLIMAGEID_2);

			query.append(_FINDER_COLUMN_S_L_C1_C2_LARGEIMAGEID_2);

			query.append(_FINDER_COLUMN_S_L_C1_C2_CUSTOM1IMAGEID_2);

			query.append(_FINDER_COLUMN_S_L_C1_C2_CUSTOM2IMAGEID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(smallImageId);

				qPos.add(largeImageId);

				qPos.add(custom1ImageId);

				qPos.add(custom2ImageId);

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

	private static final String _FINDER_COLUMN_S_L_C1_C2_SMALLIMAGEID_2 = "dlFileEntry.smallImageId = ? AND ";
	private static final String _FINDER_COLUMN_S_L_C1_C2_LARGEIMAGEID_2 = "dlFileEntry.largeImageId = ? AND ";
	private static final String _FINDER_COLUMN_S_L_C1_C2_CUSTOM1IMAGEID_2 = "dlFileEntry.custom1ImageId = ? AND ";
	private static final String _FINDER_COLUMN_S_L_C1_C2_CUSTOM2IMAGEID_2 = "dlFileEntry.custom2ImageId = ?";

	public DLFileEntryPersistenceImpl() {
		setModelClass(DLFileEntry.class);

		try {
			Field field = ReflectionUtil.getDeclaredField(BasePersistenceImpl.class,
					"_dbColumnNames");

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
	 * Caches the document library file entry in the entity cache if it is enabled.
	 *
	 * @param dlFileEntry the document library file entry
	 */
	@Override
	public void cacheResult(DLFileEntry dlFileEntry) {
		entityCache.putResult(DLFileEntryModelImpl.ENTITY_CACHE_ENABLED,
			DLFileEntryImpl.class, dlFileEntry.getPrimaryKey(), dlFileEntry);

		finderCache.putResult(FINDER_PATH_FETCH_BY_UUID_G,
			new Object[] { dlFileEntry.getUuid(), dlFileEntry.getGroupId() },
			dlFileEntry);

		finderCache.putResult(FINDER_PATH_FETCH_BY_G_F_N,
			new Object[] {
				dlFileEntry.getGroupId(), dlFileEntry.getFolderId(),
				dlFileEntry.getName()
			}, dlFileEntry);

		finderCache.putResult(FINDER_PATH_FETCH_BY_G_F_FN,
			new Object[] {
				dlFileEntry.getGroupId(), dlFileEntry.getFolderId(),
				dlFileEntry.getFileName()
			}, dlFileEntry);

		finderCache.putResult(FINDER_PATH_FETCH_BY_G_F_T,
			new Object[] {
				dlFileEntry.getGroupId(), dlFileEntry.getFolderId(),
				dlFileEntry.getTitle()
			}, dlFileEntry);

		dlFileEntry.resetOriginalValues();
	}

	/**
	 * Caches the document library file entries in the entity cache if it is enabled.
	 *
	 * @param dlFileEntries the document library file entries
	 */
	@Override
	public void cacheResult(List<DLFileEntry> dlFileEntries) {
		for (DLFileEntry dlFileEntry : dlFileEntries) {
			if (entityCache.getResult(
						DLFileEntryModelImpl.ENTITY_CACHE_ENABLED,
						DLFileEntryImpl.class, dlFileEntry.getPrimaryKey()) == null) {
				cacheResult(dlFileEntry);
			}
			else {
				dlFileEntry.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all document library file entries.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(DLFileEntryImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the document library file entry.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(DLFileEntry dlFileEntry) {
		entityCache.removeResult(DLFileEntryModelImpl.ENTITY_CACHE_ENABLED,
			DLFileEntryImpl.class, dlFileEntry.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache((DLFileEntryModelImpl)dlFileEntry, true);
	}

	@Override
	public void clearCache(List<DLFileEntry> dlFileEntries) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (DLFileEntry dlFileEntry : dlFileEntries) {
			entityCache.removeResult(DLFileEntryModelImpl.ENTITY_CACHE_ENABLED,
				DLFileEntryImpl.class, dlFileEntry.getPrimaryKey());

			clearUniqueFindersCache((DLFileEntryModelImpl)dlFileEntry, true);
		}
	}

	protected void cacheUniqueFindersCache(
		DLFileEntryModelImpl dlFileEntryModelImpl) {
		Object[] args = new Object[] {
				dlFileEntryModelImpl.getUuid(),
				dlFileEntryModelImpl.getGroupId()
			};

		finderCache.putResult(FINDER_PATH_COUNT_BY_UUID_G, args,
			Long.valueOf(1), false);
		finderCache.putResult(FINDER_PATH_FETCH_BY_UUID_G, args,
			dlFileEntryModelImpl, false);

		args = new Object[] {
				dlFileEntryModelImpl.getGroupId(),
				dlFileEntryModelImpl.getFolderId(),
				dlFileEntryModelImpl.getName()
			};

		finderCache.putResult(FINDER_PATH_COUNT_BY_G_F_N, args,
			Long.valueOf(1), false);
		finderCache.putResult(FINDER_PATH_FETCH_BY_G_F_N, args,
			dlFileEntryModelImpl, false);

		args = new Object[] {
				dlFileEntryModelImpl.getGroupId(),
				dlFileEntryModelImpl.getFolderId(),
				dlFileEntryModelImpl.getFileName()
			};

		finderCache.putResult(FINDER_PATH_COUNT_BY_G_F_FN, args,
			Long.valueOf(1), false);
		finderCache.putResult(FINDER_PATH_FETCH_BY_G_F_FN, args,
			dlFileEntryModelImpl, false);

		args = new Object[] {
				dlFileEntryModelImpl.getGroupId(),
				dlFileEntryModelImpl.getFolderId(),
				dlFileEntryModelImpl.getTitle()
			};

		finderCache.putResult(FINDER_PATH_COUNT_BY_G_F_T, args,
			Long.valueOf(1), false);
		finderCache.putResult(FINDER_PATH_FETCH_BY_G_F_T, args,
			dlFileEntryModelImpl, false);
	}

	protected void clearUniqueFindersCache(
		DLFileEntryModelImpl dlFileEntryModelImpl, boolean clearCurrent) {
		if (clearCurrent) {
			Object[] args = new Object[] {
					dlFileEntryModelImpl.getUuid(),
					dlFileEntryModelImpl.getGroupId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID_G, args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_UUID_G, args);
		}

		if ((dlFileEntryModelImpl.getColumnBitmask() &
				FINDER_PATH_FETCH_BY_UUID_G.getColumnBitmask()) != 0) {
			Object[] args = new Object[] {
					dlFileEntryModelImpl.getOriginalUuid(),
					dlFileEntryModelImpl.getOriginalGroupId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID_G, args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_UUID_G, args);
		}

		if (clearCurrent) {
			Object[] args = new Object[] {
					dlFileEntryModelImpl.getGroupId(),
					dlFileEntryModelImpl.getFolderId(),
					dlFileEntryModelImpl.getName()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_G_F_N, args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_G_F_N, args);
		}

		if ((dlFileEntryModelImpl.getColumnBitmask() &
				FINDER_PATH_FETCH_BY_G_F_N.getColumnBitmask()) != 0) {
			Object[] args = new Object[] {
					dlFileEntryModelImpl.getOriginalGroupId(),
					dlFileEntryModelImpl.getOriginalFolderId(),
					dlFileEntryModelImpl.getOriginalName()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_G_F_N, args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_G_F_N, args);
		}

		if (clearCurrent) {
			Object[] args = new Object[] {
					dlFileEntryModelImpl.getGroupId(),
					dlFileEntryModelImpl.getFolderId(),
					dlFileEntryModelImpl.getFileName()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_G_F_FN, args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_G_F_FN, args);
		}

		if ((dlFileEntryModelImpl.getColumnBitmask() &
				FINDER_PATH_FETCH_BY_G_F_FN.getColumnBitmask()) != 0) {
			Object[] args = new Object[] {
					dlFileEntryModelImpl.getOriginalGroupId(),
					dlFileEntryModelImpl.getOriginalFolderId(),
					dlFileEntryModelImpl.getOriginalFileName()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_G_F_FN, args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_G_F_FN, args);
		}

		if (clearCurrent) {
			Object[] args = new Object[] {
					dlFileEntryModelImpl.getGroupId(),
					dlFileEntryModelImpl.getFolderId(),
					dlFileEntryModelImpl.getTitle()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_G_F_T, args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_G_F_T, args);
		}

		if ((dlFileEntryModelImpl.getColumnBitmask() &
				FINDER_PATH_FETCH_BY_G_F_T.getColumnBitmask()) != 0) {
			Object[] args = new Object[] {
					dlFileEntryModelImpl.getOriginalGroupId(),
					dlFileEntryModelImpl.getOriginalFolderId(),
					dlFileEntryModelImpl.getOriginalTitle()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_G_F_T, args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_G_F_T, args);
		}
	}

	/**
	 * Creates a new document library file entry with the primary key. Does not add the document library file entry to the database.
	 *
	 * @param fileEntryId the primary key for the new document library file entry
	 * @return the new document library file entry
	 */
	@Override
	public DLFileEntry create(long fileEntryId) {
		DLFileEntry dlFileEntry = new DLFileEntryImpl();

		dlFileEntry.setNew(true);
		dlFileEntry.setPrimaryKey(fileEntryId);

		String uuid = PortalUUIDUtil.generate();

		dlFileEntry.setUuid(uuid);

		dlFileEntry.setCompanyId(companyProvider.getCompanyId());

		return dlFileEntry;
	}

	/**
	 * Removes the document library file entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param fileEntryId the primary key of the document library file entry
	 * @return the document library file entry that was removed
	 * @throws NoSuchFileEntryException if a document library file entry with the primary key could not be found
	 */
	@Override
	public DLFileEntry remove(long fileEntryId) throws NoSuchFileEntryException {
		return remove((Serializable)fileEntryId);
	}

	/**
	 * Removes the document library file entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the document library file entry
	 * @return the document library file entry that was removed
	 * @throws NoSuchFileEntryException if a document library file entry with the primary key could not be found
	 */
	@Override
	public DLFileEntry remove(Serializable primaryKey)
		throws NoSuchFileEntryException {
		Session session = null;

		try {
			session = openSession();

			DLFileEntry dlFileEntry = (DLFileEntry)session.get(DLFileEntryImpl.class,
					primaryKey);

			if (dlFileEntry == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchFileEntryException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(dlFileEntry);
		}
		catch (NoSuchFileEntryException nsee) {
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
	protected DLFileEntry removeImpl(DLFileEntry dlFileEntry) {
		dlFileEntry = toUnwrappedModel(dlFileEntry);

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(dlFileEntry)) {
				dlFileEntry = (DLFileEntry)session.get(DLFileEntryImpl.class,
						dlFileEntry.getPrimaryKeyObj());
			}

			if (dlFileEntry != null) {
				session.delete(dlFileEntry);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (dlFileEntry != null) {
			clearCache(dlFileEntry);
		}

		return dlFileEntry;
	}

	@Override
	public DLFileEntry updateImpl(DLFileEntry dlFileEntry) {
		dlFileEntry = toUnwrappedModel(dlFileEntry);

		boolean isNew = dlFileEntry.isNew();

		DLFileEntryModelImpl dlFileEntryModelImpl = (DLFileEntryModelImpl)dlFileEntry;

		if (Validator.isNull(dlFileEntry.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			dlFileEntry.setUuid(uuid);
		}

		ServiceContext serviceContext = ServiceContextThreadLocal.getServiceContext();

		Date now = new Date();

		if (isNew && (dlFileEntry.getCreateDate() == null)) {
			if (serviceContext == null) {
				dlFileEntry.setCreateDate(now);
			}
			else {
				dlFileEntry.setCreateDate(serviceContext.getCreateDate(now));
			}
		}

		if (!dlFileEntryModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				dlFileEntry.setModifiedDate(now);
			}
			else {
				dlFileEntry.setModifiedDate(serviceContext.getModifiedDate(now));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (dlFileEntry.isNew()) {
				session.save(dlFileEntry);

				dlFileEntry.setNew(false);
			}
			else {
				dlFileEntry = (DLFileEntry)session.merge(dlFileEntry);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (!DLFileEntryModelImpl.COLUMN_BITMASK_ENABLED) {
			finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}
		else
		 if (isNew) {
			Object[] args = new Object[] { dlFileEntryModelImpl.getUuid() };

			finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID, args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID,
				args);

			args = new Object[] {
					dlFileEntryModelImpl.getUuid(),
					dlFileEntryModelImpl.getCompanyId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID_C, args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C,
				args);

			args = new Object[] { dlFileEntryModelImpl.getGroupId() };

			finderCache.removeResult(FINDER_PATH_COUNT_BY_GROUPID, args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID,
				args);

			args = new Object[] { dlFileEntryModelImpl.getCompanyId() };

			finderCache.removeResult(FINDER_PATH_COUNT_BY_COMPANYID, args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID,
				args);

			args = new Object[] { dlFileEntryModelImpl.getRepositoryId() };

			finderCache.removeResult(FINDER_PATH_COUNT_BY_REPOSITORYID, args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_REPOSITORYID,
				args);

			args = new Object[] { dlFileEntryModelImpl.getMimeType() };

			finderCache.removeResult(FINDER_PATH_COUNT_BY_MIMETYPE, args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_MIMETYPE,
				args);

			args = new Object[] { dlFileEntryModelImpl.getFileEntryTypeId() };

			finderCache.removeResult(FINDER_PATH_COUNT_BY_FILEENTRYTYPEID, args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_FILEENTRYTYPEID,
				args);

			args = new Object[] { dlFileEntryModelImpl.getSmallImageId() };

			finderCache.removeResult(FINDER_PATH_COUNT_BY_SMALLIMAGEID, args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_SMALLIMAGEID,
				args);

			args = new Object[] { dlFileEntryModelImpl.getLargeImageId() };

			finderCache.removeResult(FINDER_PATH_COUNT_BY_LARGEIMAGEID, args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_LARGEIMAGEID,
				args);

			args = new Object[] { dlFileEntryModelImpl.getCustom1ImageId() };

			finderCache.removeResult(FINDER_PATH_COUNT_BY_CUSTOM1IMAGEID, args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_CUSTOM1IMAGEID,
				args);

			args = new Object[] { dlFileEntryModelImpl.getCustom2ImageId() };

			finderCache.removeResult(FINDER_PATH_COUNT_BY_CUSTOM2IMAGEID, args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_CUSTOM2IMAGEID,
				args);

			args = new Object[] {
					dlFileEntryModelImpl.getGroupId(),
					dlFileEntryModelImpl.getUserId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_G_U, args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_U,
				args);

			args = new Object[] {
					dlFileEntryModelImpl.getGroupId(),
					dlFileEntryModelImpl.getFolderId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_G_F, args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_F,
				args);

			args = new Object[] {
					dlFileEntryModelImpl.getRepositoryId(),
					dlFileEntryModelImpl.getFolderId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_R_F, args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_R_F,
				args);

			args = new Object[] {
					dlFileEntryModelImpl.getFolderId(),
					dlFileEntryModelImpl.getName()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_F_N, args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_F_N,
				args);

			args = new Object[] {
					dlFileEntryModelImpl.getGroupId(),
					dlFileEntryModelImpl.getUserId(),
					dlFileEntryModelImpl.getFolderId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_G_U_F, args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_U_F,
				args);

			args = new Object[] {
					dlFileEntryModelImpl.getGroupId(),
					dlFileEntryModelImpl.getFolderId(),
					dlFileEntryModelImpl.getFileEntryTypeId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_G_F_F, args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_F_F,
				args);

			args = new Object[] {
					dlFileEntryModelImpl.getSmallImageId(),
					dlFileEntryModelImpl.getLargeImageId(),
					dlFileEntryModelImpl.getCustom1ImageId(),
					dlFileEntryModelImpl.getCustom2ImageId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_S_L_C1_C2, args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_S_L_C1_C2,
				args);

			finderCache.removeResult(FINDER_PATH_COUNT_ALL, FINDER_ARGS_EMPTY);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL,
				FINDER_ARGS_EMPTY);
		}

		else {
			if ((dlFileEntryModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						dlFileEntryModelImpl.getOriginalUuid()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID,
					args);

				args = new Object[] { dlFileEntryModelImpl.getUuid() };

				finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID,
					args);
			}

			if ((dlFileEntryModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						dlFileEntryModelImpl.getOriginalUuid(),
						dlFileEntryModelImpl.getOriginalCompanyId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID_C, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C,
					args);

				args = new Object[] {
						dlFileEntryModelImpl.getUuid(),
						dlFileEntryModelImpl.getCompanyId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID_C, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C,
					args);
			}

			if ((dlFileEntryModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						dlFileEntryModelImpl.getOriginalGroupId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_GROUPID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID,
					args);

				args = new Object[] { dlFileEntryModelImpl.getGroupId() };

				finderCache.removeResult(FINDER_PATH_COUNT_BY_GROUPID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID,
					args);
			}

			if ((dlFileEntryModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						dlFileEntryModelImpl.getOriginalCompanyId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_COMPANYID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID,
					args);

				args = new Object[] { dlFileEntryModelImpl.getCompanyId() };

				finderCache.removeResult(FINDER_PATH_COUNT_BY_COMPANYID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID,
					args);
			}

			if ((dlFileEntryModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_REPOSITORYID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						dlFileEntryModelImpl.getOriginalRepositoryId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_REPOSITORYID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_REPOSITORYID,
					args);

				args = new Object[] { dlFileEntryModelImpl.getRepositoryId() };

				finderCache.removeResult(FINDER_PATH_COUNT_BY_REPOSITORYID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_REPOSITORYID,
					args);
			}

			if ((dlFileEntryModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_MIMETYPE.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						dlFileEntryModelImpl.getOriginalMimeType()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_MIMETYPE, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_MIMETYPE,
					args);

				args = new Object[] { dlFileEntryModelImpl.getMimeType() };

				finderCache.removeResult(FINDER_PATH_COUNT_BY_MIMETYPE, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_MIMETYPE,
					args);
			}

			if ((dlFileEntryModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_FILEENTRYTYPEID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						dlFileEntryModelImpl.getOriginalFileEntryTypeId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_FILEENTRYTYPEID,
					args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_FILEENTRYTYPEID,
					args);

				args = new Object[] { dlFileEntryModelImpl.getFileEntryTypeId() };

				finderCache.removeResult(FINDER_PATH_COUNT_BY_FILEENTRYTYPEID,
					args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_FILEENTRYTYPEID,
					args);
			}

			if ((dlFileEntryModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_SMALLIMAGEID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						dlFileEntryModelImpl.getOriginalSmallImageId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_SMALLIMAGEID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_SMALLIMAGEID,
					args);

				args = new Object[] { dlFileEntryModelImpl.getSmallImageId() };

				finderCache.removeResult(FINDER_PATH_COUNT_BY_SMALLIMAGEID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_SMALLIMAGEID,
					args);
			}

			if ((dlFileEntryModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_LARGEIMAGEID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						dlFileEntryModelImpl.getOriginalLargeImageId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_LARGEIMAGEID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_LARGEIMAGEID,
					args);

				args = new Object[] { dlFileEntryModelImpl.getLargeImageId() };

				finderCache.removeResult(FINDER_PATH_COUNT_BY_LARGEIMAGEID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_LARGEIMAGEID,
					args);
			}

			if ((dlFileEntryModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_CUSTOM1IMAGEID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						dlFileEntryModelImpl.getOriginalCustom1ImageId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_CUSTOM1IMAGEID,
					args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_CUSTOM1IMAGEID,
					args);

				args = new Object[] { dlFileEntryModelImpl.getCustom1ImageId() };

				finderCache.removeResult(FINDER_PATH_COUNT_BY_CUSTOM1IMAGEID,
					args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_CUSTOM1IMAGEID,
					args);
			}

			if ((dlFileEntryModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_CUSTOM2IMAGEID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						dlFileEntryModelImpl.getOriginalCustom2ImageId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_CUSTOM2IMAGEID,
					args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_CUSTOM2IMAGEID,
					args);

				args = new Object[] { dlFileEntryModelImpl.getCustom2ImageId() };

				finderCache.removeResult(FINDER_PATH_COUNT_BY_CUSTOM2IMAGEID,
					args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_CUSTOM2IMAGEID,
					args);
			}

			if ((dlFileEntryModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_U.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						dlFileEntryModelImpl.getOriginalGroupId(),
						dlFileEntryModelImpl.getOriginalUserId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_G_U, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_U,
					args);

				args = new Object[] {
						dlFileEntryModelImpl.getGroupId(),
						dlFileEntryModelImpl.getUserId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_G_U, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_U,
					args);
			}

			if ((dlFileEntryModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_F.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						dlFileEntryModelImpl.getOriginalGroupId(),
						dlFileEntryModelImpl.getOriginalFolderId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_G_F, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_F,
					args);

				args = new Object[] {
						dlFileEntryModelImpl.getGroupId(),
						dlFileEntryModelImpl.getFolderId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_G_F, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_F,
					args);
			}

			if ((dlFileEntryModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_R_F.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						dlFileEntryModelImpl.getOriginalRepositoryId(),
						dlFileEntryModelImpl.getOriginalFolderId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_R_F, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_R_F,
					args);

				args = new Object[] {
						dlFileEntryModelImpl.getRepositoryId(),
						dlFileEntryModelImpl.getFolderId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_R_F, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_R_F,
					args);
			}

			if ((dlFileEntryModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_F_N.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						dlFileEntryModelImpl.getOriginalFolderId(),
						dlFileEntryModelImpl.getOriginalName()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_F_N, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_F_N,
					args);

				args = new Object[] {
						dlFileEntryModelImpl.getFolderId(),
						dlFileEntryModelImpl.getName()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_F_N, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_F_N,
					args);
			}

			if ((dlFileEntryModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_U_F.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						dlFileEntryModelImpl.getOriginalGroupId(),
						dlFileEntryModelImpl.getOriginalUserId(),
						dlFileEntryModelImpl.getOriginalFolderId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_G_U_F, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_U_F,
					args);

				args = new Object[] {
						dlFileEntryModelImpl.getGroupId(),
						dlFileEntryModelImpl.getUserId(),
						dlFileEntryModelImpl.getFolderId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_G_U_F, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_U_F,
					args);
			}

			if ((dlFileEntryModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_F_F.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						dlFileEntryModelImpl.getOriginalGroupId(),
						dlFileEntryModelImpl.getOriginalFolderId(),
						dlFileEntryModelImpl.getOriginalFileEntryTypeId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_G_F_F, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_F_F,
					args);

				args = new Object[] {
						dlFileEntryModelImpl.getGroupId(),
						dlFileEntryModelImpl.getFolderId(),
						dlFileEntryModelImpl.getFileEntryTypeId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_G_F_F, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_F_F,
					args);
			}

			if ((dlFileEntryModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_S_L_C1_C2.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						dlFileEntryModelImpl.getOriginalSmallImageId(),
						dlFileEntryModelImpl.getOriginalLargeImageId(),
						dlFileEntryModelImpl.getOriginalCustom1ImageId(),
						dlFileEntryModelImpl.getOriginalCustom2ImageId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_S_L_C1_C2, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_S_L_C1_C2,
					args);

				args = new Object[] {
						dlFileEntryModelImpl.getSmallImageId(),
						dlFileEntryModelImpl.getLargeImageId(),
						dlFileEntryModelImpl.getCustom1ImageId(),
						dlFileEntryModelImpl.getCustom2ImageId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_S_L_C1_C2, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_S_L_C1_C2,
					args);
			}
		}

		entityCache.putResult(DLFileEntryModelImpl.ENTITY_CACHE_ENABLED,
			DLFileEntryImpl.class, dlFileEntry.getPrimaryKey(), dlFileEntry,
			false);

		clearUniqueFindersCache(dlFileEntryModelImpl, false);
		cacheUniqueFindersCache(dlFileEntryModelImpl);

		dlFileEntry.resetOriginalValues();

		return dlFileEntry;
	}

	protected DLFileEntry toUnwrappedModel(DLFileEntry dlFileEntry) {
		if (dlFileEntry instanceof DLFileEntryImpl) {
			return dlFileEntry;
		}

		DLFileEntryImpl dlFileEntryImpl = new DLFileEntryImpl();

		dlFileEntryImpl.setNew(dlFileEntry.isNew());
		dlFileEntryImpl.setPrimaryKey(dlFileEntry.getPrimaryKey());

		dlFileEntryImpl.setUuid(dlFileEntry.getUuid());
		dlFileEntryImpl.setFileEntryId(dlFileEntry.getFileEntryId());
		dlFileEntryImpl.setGroupId(dlFileEntry.getGroupId());
		dlFileEntryImpl.setCompanyId(dlFileEntry.getCompanyId());
		dlFileEntryImpl.setUserId(dlFileEntry.getUserId());
		dlFileEntryImpl.setUserName(dlFileEntry.getUserName());
		dlFileEntryImpl.setCreateDate(dlFileEntry.getCreateDate());
		dlFileEntryImpl.setModifiedDate(dlFileEntry.getModifiedDate());
		dlFileEntryImpl.setClassNameId(dlFileEntry.getClassNameId());
		dlFileEntryImpl.setClassPK(dlFileEntry.getClassPK());
		dlFileEntryImpl.setRepositoryId(dlFileEntry.getRepositoryId());
		dlFileEntryImpl.setFolderId(dlFileEntry.getFolderId());
		dlFileEntryImpl.setTreePath(dlFileEntry.getTreePath());
		dlFileEntryImpl.setName(dlFileEntry.getName());
		dlFileEntryImpl.setFileName(dlFileEntry.getFileName());
		dlFileEntryImpl.setExtension(dlFileEntry.getExtension());
		dlFileEntryImpl.setMimeType(dlFileEntry.getMimeType());
		dlFileEntryImpl.setTitle(dlFileEntry.getTitle());
		dlFileEntryImpl.setDescription(dlFileEntry.getDescription());
		dlFileEntryImpl.setExtraSettings(dlFileEntry.getExtraSettings());
		dlFileEntryImpl.setFileEntryTypeId(dlFileEntry.getFileEntryTypeId());
		dlFileEntryImpl.setVersion(dlFileEntry.getVersion());
		dlFileEntryImpl.setSize(dlFileEntry.getSize());
		dlFileEntryImpl.setReadCount(dlFileEntry.getReadCount());
		dlFileEntryImpl.setSmallImageId(dlFileEntry.getSmallImageId());
		dlFileEntryImpl.setLargeImageId(dlFileEntry.getLargeImageId());
		dlFileEntryImpl.setCustom1ImageId(dlFileEntry.getCustom1ImageId());
		dlFileEntryImpl.setCustom2ImageId(dlFileEntry.getCustom2ImageId());
		dlFileEntryImpl.setManualCheckInRequired(dlFileEntry.isManualCheckInRequired());
		dlFileEntryImpl.setLastPublishDate(dlFileEntry.getLastPublishDate());

		return dlFileEntryImpl;
	}

	/**
	 * Returns the document library file entry with the primary key or throws a {@link com.liferay.portal.kernel.exception.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the document library file entry
	 * @return the document library file entry
	 * @throws NoSuchFileEntryException if a document library file entry with the primary key could not be found
	 */
	@Override
	public DLFileEntry findByPrimaryKey(Serializable primaryKey)
		throws NoSuchFileEntryException {
		DLFileEntry dlFileEntry = fetchByPrimaryKey(primaryKey);

		if (dlFileEntry == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchFileEntryException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				primaryKey);
		}

		return dlFileEntry;
	}

	/**
	 * Returns the document library file entry with the primary key or throws a {@link NoSuchFileEntryException} if it could not be found.
	 *
	 * @param fileEntryId the primary key of the document library file entry
	 * @return the document library file entry
	 * @throws NoSuchFileEntryException if a document library file entry with the primary key could not be found
	 */
	@Override
	public DLFileEntry findByPrimaryKey(long fileEntryId)
		throws NoSuchFileEntryException {
		return findByPrimaryKey((Serializable)fileEntryId);
	}

	/**
	 * Returns the document library file entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the document library file entry
	 * @return the document library file entry, or <code>null</code> if a document library file entry with the primary key could not be found
	 */
	@Override
	public DLFileEntry fetchByPrimaryKey(Serializable primaryKey) {
		Serializable serializable = entityCache.getResult(DLFileEntryModelImpl.ENTITY_CACHE_ENABLED,
				DLFileEntryImpl.class, primaryKey);

		if (serializable == nullModel) {
			return null;
		}

		DLFileEntry dlFileEntry = (DLFileEntry)serializable;

		if (dlFileEntry == null) {
			Session session = null;

			try {
				session = openSession();

				dlFileEntry = (DLFileEntry)session.get(DLFileEntryImpl.class,
						primaryKey);

				if (dlFileEntry != null) {
					cacheResult(dlFileEntry);
				}
				else {
					entityCache.putResult(DLFileEntryModelImpl.ENTITY_CACHE_ENABLED,
						DLFileEntryImpl.class, primaryKey, nullModel);
				}
			}
			catch (Exception e) {
				entityCache.removeResult(DLFileEntryModelImpl.ENTITY_CACHE_ENABLED,
					DLFileEntryImpl.class, primaryKey);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return dlFileEntry;
	}

	/**
	 * Returns the document library file entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param fileEntryId the primary key of the document library file entry
	 * @return the document library file entry, or <code>null</code> if a document library file entry with the primary key could not be found
	 */
	@Override
	public DLFileEntry fetchByPrimaryKey(long fileEntryId) {
		return fetchByPrimaryKey((Serializable)fileEntryId);
	}

	@Override
	public Map<Serializable, DLFileEntry> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {
		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, DLFileEntry> map = new HashMap<Serializable, DLFileEntry>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			DLFileEntry dlFileEntry = fetchByPrimaryKey(primaryKey);

			if (dlFileEntry != null) {
				map.put(primaryKey, dlFileEntry);
			}

			return map;
		}

		Set<Serializable> uncachedPrimaryKeys = null;

		for (Serializable primaryKey : primaryKeys) {
			Serializable serializable = entityCache.getResult(DLFileEntryModelImpl.ENTITY_CACHE_ENABLED,
					DLFileEntryImpl.class, primaryKey);

			if (serializable != nullModel) {
				if (serializable == null) {
					if (uncachedPrimaryKeys == null) {
						uncachedPrimaryKeys = new HashSet<Serializable>();
					}

					uncachedPrimaryKeys.add(primaryKey);
				}
				else {
					map.put(primaryKey, (DLFileEntry)serializable);
				}
			}
		}

		if (uncachedPrimaryKeys == null) {
			return map;
		}

		StringBundler query = new StringBundler((uncachedPrimaryKeys.size() * 2) +
				1);

		query.append(_SQL_SELECT_DLFILEENTRY_WHERE_PKS_IN);

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

			for (DLFileEntry dlFileEntry : (List<DLFileEntry>)q.list()) {
				map.put(dlFileEntry.getPrimaryKeyObj(), dlFileEntry);

				cacheResult(dlFileEntry);

				uncachedPrimaryKeys.remove(dlFileEntry.getPrimaryKeyObj());
			}

			for (Serializable primaryKey : uncachedPrimaryKeys) {
				entityCache.putResult(DLFileEntryModelImpl.ENTITY_CACHE_ENABLED,
					DLFileEntryImpl.class, primaryKey, nullModel);
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
	 * Returns all the document library file entries.
	 *
	 * @return the document library file entries
	 */
	@Override
	public List<DLFileEntry> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the document library file entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFileEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of document library file entries
	 * @param end the upper bound of the range of document library file entries (not inclusive)
	 * @return the range of document library file entries
	 */
	@Override
	public List<DLFileEntry> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the document library file entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFileEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of document library file entries
	 * @param end the upper bound of the range of document library file entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of document library file entries
	 */
	@Override
	public List<DLFileEntry> findAll(int start, int end,
		OrderByComparator<DLFileEntry> orderByComparator) {
		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the document library file entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFileEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of document library file entries
	 * @param end the upper bound of the range of document library file entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of document library file entries
	 */
	@Override
	public List<DLFileEntry> findAll(int start, int end,
		OrderByComparator<DLFileEntry> orderByComparator,
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

		List<DLFileEntry> list = null;

		if (retrieveFromCache) {
			list = (List<DLFileEntry>)finderCache.getResult(finderPath,
					finderArgs, this);
		}

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 2));

				query.append(_SQL_SELECT_DLFILEENTRY);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_DLFILEENTRY;

				if (pagination) {
					sql = sql.concat(DLFileEntryModelImpl.ORDER_BY_JPQL);
				}
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (!pagination) {
					list = (List<DLFileEntry>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<DLFileEntry>)QueryUtil.list(q, getDialect(),
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
	 * Removes all the document library file entries from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (DLFileEntry dlFileEntry : findAll()) {
			remove(dlFileEntry);
		}
	}

	/**
	 * Returns the number of document library file entries.
	 *
	 * @return the number of document library file entries
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_DLFILEENTRY);

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
		return DLFileEntryModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the document library file entry persistence.
	 */
	public void afterPropertiesSet() {
	}

	public void destroy() {
		entityCache.removeCache(DLFileEntryImpl.class.getName());
		finderCache.removeCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@BeanReference(type = CompanyProviderWrapper.class)
	protected CompanyProvider companyProvider;
	protected EntityCache entityCache = EntityCacheUtil.getEntityCache();
	protected FinderCache finderCache = FinderCacheUtil.getFinderCache();
	private static final String _SQL_SELECT_DLFILEENTRY = "SELECT dlFileEntry FROM DLFileEntry dlFileEntry";
	private static final String _SQL_SELECT_DLFILEENTRY_WHERE_PKS_IN = "SELECT dlFileEntry FROM DLFileEntry dlFileEntry WHERE fileEntryId IN (";
	private static final String _SQL_SELECT_DLFILEENTRY_WHERE = "SELECT dlFileEntry FROM DLFileEntry dlFileEntry WHERE ";
	private static final String _SQL_COUNT_DLFILEENTRY = "SELECT COUNT(dlFileEntry) FROM DLFileEntry dlFileEntry";
	private static final String _SQL_COUNT_DLFILEENTRY_WHERE = "SELECT COUNT(dlFileEntry) FROM DLFileEntry dlFileEntry WHERE ";
	private static final String _FILTER_ENTITY_TABLE_FILTER_PK_COLUMN = "dlFileEntry.fileEntryId";
	private static final String _FILTER_SQL_SELECT_DLFILEENTRY_WHERE = "SELECT DISTINCT {dlFileEntry.*} FROM DLFileEntry dlFileEntry WHERE ";
	private static final String _FILTER_SQL_SELECT_DLFILEENTRY_NO_INLINE_DISTINCT_WHERE_1 =
		"SELECT {DLFileEntry.*} FROM (SELECT DISTINCT dlFileEntry.fileEntryId FROM DLFileEntry dlFileEntry WHERE ";
	private static final String _FILTER_SQL_SELECT_DLFILEENTRY_NO_INLINE_DISTINCT_WHERE_2 =
		") TEMP_TABLE INNER JOIN DLFileEntry ON TEMP_TABLE.fileEntryId = DLFileEntry.fileEntryId";
	private static final String _FILTER_SQL_COUNT_DLFILEENTRY_WHERE = "SELECT COUNT(DISTINCT dlFileEntry.fileEntryId) AS COUNT_VALUE FROM DLFileEntry dlFileEntry WHERE ";
	private static final String _FILTER_ENTITY_ALIAS = "dlFileEntry";
	private static final String _FILTER_ENTITY_TABLE = "DLFileEntry";
	private static final String _ORDER_BY_ENTITY_ALIAS = "dlFileEntry.";
	private static final String _ORDER_BY_ENTITY_TABLE = "DLFileEntry.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No DLFileEntry exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No DLFileEntry exists with the key {";
	private static final Log _log = LogFactoryUtil.getLog(DLFileEntryPersistenceImpl.class);
	private static final Set<String> _badColumnNames = SetUtil.fromArray(new String[] {
				"uuid", "size"
			});
}