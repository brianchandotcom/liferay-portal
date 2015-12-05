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
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;
import com.liferay.portal.model.CacheModel;
import com.liferay.portal.security.permission.InlineSQLHelperUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceContextThreadLocal;
import com.liferay.portal.service.persistence.CompanyProvider;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;

import com.liferay.portlet.documentlibrary.NoSuchFileShortcutException;
import com.liferay.portlet.documentlibrary.model.DLFileShortcut;
import com.liferay.portlet.documentlibrary.model.impl.DLFileShortcutImpl;
import com.liferay.portlet.documentlibrary.model.impl.DLFileShortcutModelImpl;
import com.liferay.portlet.documentlibrary.service.persistence.DLFileShortcutPersistence;

import java.io.Serializable;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The persistence implementation for the document library file shortcut service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see DLFileShortcutPersistence
 * @see com.liferay.portlet.documentlibrary.service.persistence.DLFileShortcutUtil
 * @generated
 */
@ProviderType
public class DLFileShortcutPersistenceImpl extends BasePersistenceImpl<DLFileShortcut>
	implements DLFileShortcutPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link DLFileShortcutUtil} to access the document library file shortcut persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = DLFileShortcutImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(DLFileShortcutModelImpl.ENTITY_CACHE_ENABLED,
			DLFileShortcutModelImpl.FINDER_CACHE_ENABLED,
			DLFileShortcutImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(DLFileShortcutModelImpl.ENTITY_CACHE_ENABLED,
			DLFileShortcutModelImpl.FINDER_CACHE_ENABLED,
			DLFileShortcutImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(DLFileShortcutModelImpl.ENTITY_CACHE_ENABLED,
			DLFileShortcutModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_UUID = new FinderPath(DLFileShortcutModelImpl.ENTITY_CACHE_ENABLED,
			DLFileShortcutModelImpl.FINDER_CACHE_ENABLED,
			DLFileShortcutImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByUuid",
			new String[] {
				String.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID = new FinderPath(DLFileShortcutModelImpl.ENTITY_CACHE_ENABLED,
			DLFileShortcutModelImpl.FINDER_CACHE_ENABLED,
			DLFileShortcutImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid",
			new String[] { String.class.getName() },
			DLFileShortcutModelImpl.UUID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID = new FinderPath(DLFileShortcutModelImpl.ENTITY_CACHE_ENABLED,
			DLFileShortcutModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUuid",
			new String[] { String.class.getName() });

	/**
	 * Returns all the document library file shortcuts where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching document library file shortcuts
	 */
	@Override
	public List<DLFileShortcut> findByUuid(String uuid) {
		return findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the document library file shortcuts where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFileShortcutModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of document library file shortcuts
	 * @param end the upper bound of the range of document library file shortcuts (not inclusive)
	 * @return the range of matching document library file shortcuts
	 */
	@Override
	public List<DLFileShortcut> findByUuid(String uuid, int start, int end) {
		return findByUuid(uuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the document library file shortcuts where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFileShortcutModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of document library file shortcuts
	 * @param end the upper bound of the range of document library file shortcuts (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching document library file shortcuts
	 */
	@Override
	public List<DLFileShortcut> findByUuid(String uuid, int start, int end,
		OrderByComparator<DLFileShortcut> orderByComparator) {
		return findByUuid(uuid, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the document library file shortcuts where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFileShortcutModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of document library file shortcuts
	 * @param end the upper bound of the range of document library file shortcuts (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching document library file shortcuts
	 */
	@Override
	public List<DLFileShortcut> findByUuid(String uuid, int start, int end,
		OrderByComparator<DLFileShortcut> orderByComparator,
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

		List<DLFileShortcut> list = null;

		if (retrieveFromCache) {
			list = (List<DLFileShortcut>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (DLFileShortcut dlFileShortcut : list) {
					if (!Validator.equals(uuid, dlFileShortcut.getUuid())) {
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
						(orderByComparator.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(3);
			}

			query.append(_SQL_SELECT_DLFILESHORTCUT_WHERE);

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
				query.append(DLFileShortcutModelImpl.ORDER_BY_JPQL);
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
					list = (List<DLFileShortcut>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<DLFileShortcut>)QueryUtil.list(q,
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
	 * Returns the first document library file shortcut in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching document library file shortcut
	 * @throws NoSuchFileShortcutException if a matching document library file shortcut could not be found
	 */
	@Override
	public DLFileShortcut findByUuid_First(String uuid,
		OrderByComparator<DLFileShortcut> orderByComparator)
		throws NoSuchFileShortcutException {
		DLFileShortcut dlFileShortcut = fetchByUuid_First(uuid,
				orderByComparator);

		if (dlFileShortcut != null) {
			return dlFileShortcut;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchFileShortcutException(msg.toString());
	}

	/**
	 * Returns the first document library file shortcut in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching document library file shortcut, or <code>null</code> if a matching document library file shortcut could not be found
	 */
	@Override
	public DLFileShortcut fetchByUuid_First(String uuid,
		OrderByComparator<DLFileShortcut> orderByComparator) {
		List<DLFileShortcut> list = findByUuid(uuid, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last document library file shortcut in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching document library file shortcut
	 * @throws NoSuchFileShortcutException if a matching document library file shortcut could not be found
	 */
	@Override
	public DLFileShortcut findByUuid_Last(String uuid,
		OrderByComparator<DLFileShortcut> orderByComparator)
		throws NoSuchFileShortcutException {
		DLFileShortcut dlFileShortcut = fetchByUuid_Last(uuid, orderByComparator);

		if (dlFileShortcut != null) {
			return dlFileShortcut;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchFileShortcutException(msg.toString());
	}

	/**
	 * Returns the last document library file shortcut in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching document library file shortcut, or <code>null</code> if a matching document library file shortcut could not be found
	 */
	@Override
	public DLFileShortcut fetchByUuid_Last(String uuid,
		OrderByComparator<DLFileShortcut> orderByComparator) {
		int count = countByUuid(uuid);

		if (count == 0) {
			return null;
		}

		List<DLFileShortcut> list = findByUuid(uuid, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the document library file shortcuts before and after the current document library file shortcut in the ordered set where uuid = &#63;.
	 *
	 * @param fileShortcutId the primary key of the current document library file shortcut
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next document library file shortcut
	 * @throws NoSuchFileShortcutException if a document library file shortcut with the primary key could not be found
	 */
	@Override
	public DLFileShortcut[] findByUuid_PrevAndNext(long fileShortcutId,
		String uuid, OrderByComparator<DLFileShortcut> orderByComparator)
		throws NoSuchFileShortcutException {
		DLFileShortcut dlFileShortcut = findByPrimaryKey(fileShortcutId);

		Session session = null;

		try {
			session = openSession();

			DLFileShortcut[] array = new DLFileShortcutImpl[3];

			array[0] = getByUuid_PrevAndNext(session, dlFileShortcut, uuid,
					orderByComparator, true);

			array[1] = dlFileShortcut;

			array[2] = getByUuid_PrevAndNext(session, dlFileShortcut, uuid,
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

	protected DLFileShortcut getByUuid_PrevAndNext(Session session,
		DLFileShortcut dlFileShortcut, String uuid,
		OrderByComparator<DLFileShortcut> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_DLFILESHORTCUT_WHERE);

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
			query.append(DLFileShortcutModelImpl.ORDER_BY_JPQL);
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
			Object[] values = orderByComparator.getOrderByConditionValues(dlFileShortcut);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<DLFileShortcut> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the document library file shortcuts where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	@Override
	public void removeByUuid(String uuid) {
		for (DLFileShortcut dlFileShortcut : findByUuid(uuid,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(dlFileShortcut);
		}
	}

	/**
	 * Returns the number of document library file shortcuts where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching document library file shortcuts
	 */
	@Override
	public int countByUuid(String uuid) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_UUID;

		Object[] finderArgs = new Object[] { uuid };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_DLFILESHORTCUT_WHERE);

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

	private static final String _FINDER_COLUMN_UUID_UUID_1 = "dlFileShortcut.uuid IS NULL";
	private static final String _FINDER_COLUMN_UUID_UUID_2 = "dlFileShortcut.uuid = ?";
	private static final String _FINDER_COLUMN_UUID_UUID_3 = "(dlFileShortcut.uuid IS NULL OR dlFileShortcut.uuid = '')";
	public static final FinderPath FINDER_PATH_FETCH_BY_UUID_G = new FinderPath(DLFileShortcutModelImpl.ENTITY_CACHE_ENABLED,
			DLFileShortcutModelImpl.FINDER_CACHE_ENABLED,
			DLFileShortcutImpl.class, FINDER_CLASS_NAME_ENTITY,
			"fetchByUUID_G",
			new String[] { String.class.getName(), Long.class.getName() },
			DLFileShortcutModelImpl.UUID_COLUMN_BITMASK |
			DLFileShortcutModelImpl.GROUPID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID_G = new FinderPath(DLFileShortcutModelImpl.ENTITY_CACHE_ENABLED,
			DLFileShortcutModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUUID_G",
			new String[] { String.class.getName(), Long.class.getName() });

	/**
	 * Returns the document library file shortcut where uuid = &#63; and groupId = &#63; or throws a {@link NoSuchFileShortcutException} if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching document library file shortcut
	 * @throws NoSuchFileShortcutException if a matching document library file shortcut could not be found
	 */
	@Override
	public DLFileShortcut findByUUID_G(String uuid, long groupId)
		throws NoSuchFileShortcutException {
		DLFileShortcut dlFileShortcut = fetchByUUID_G(uuid, groupId);

		if (dlFileShortcut == null) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("uuid=");
			msg.append(uuid);

			msg.append(", groupId=");
			msg.append(groupId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchFileShortcutException(msg.toString());
		}

		return dlFileShortcut;
	}

	/**
	 * Returns the document library file shortcut where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching document library file shortcut, or <code>null</code> if a matching document library file shortcut could not be found
	 */
	@Override
	public DLFileShortcut fetchByUUID_G(String uuid, long groupId) {
		return fetchByUUID_G(uuid, groupId, true);
	}

	/**
	 * Returns the document library file shortcut where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the matching document library file shortcut, or <code>null</code> if a matching document library file shortcut could not be found
	 */
	@Override
	public DLFileShortcut fetchByUUID_G(String uuid, long groupId,
		boolean retrieveFromCache) {
		Object[] finderArgs = new Object[] { uuid, groupId };

		Object result = null;

		if (retrieveFromCache) {
			result = finderCache.getResult(FINDER_PATH_FETCH_BY_UUID_G,
					finderArgs, this);
		}

		if (result instanceof DLFileShortcut) {
			DLFileShortcut dlFileShortcut = (DLFileShortcut)result;

			if (!Validator.equals(uuid, dlFileShortcut.getUuid()) ||
					(groupId != dlFileShortcut.getGroupId())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_SELECT_DLFILESHORTCUT_WHERE);

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

				List<DLFileShortcut> list = q.list();

				if (list.isEmpty()) {
					finderCache.putResult(FINDER_PATH_FETCH_BY_UUID_G,
						finderArgs, list);
				}
				else {
					DLFileShortcut dlFileShortcut = list.get(0);

					result = dlFileShortcut;

					cacheResult(dlFileShortcut);

					if ((dlFileShortcut.getUuid() == null) ||
							!dlFileShortcut.getUuid().equals(uuid) ||
							(dlFileShortcut.getGroupId() != groupId)) {
						finderCache.putResult(FINDER_PATH_FETCH_BY_UUID_G,
							finderArgs, dlFileShortcut);
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
			return (DLFileShortcut)result;
		}
	}

	/**
	 * Removes the document library file shortcut where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the document library file shortcut that was removed
	 */
	@Override
	public DLFileShortcut removeByUUID_G(String uuid, long groupId)
		throws NoSuchFileShortcutException {
		DLFileShortcut dlFileShortcut = findByUUID_G(uuid, groupId);

		return remove(dlFileShortcut);
	}

	/**
	 * Returns the number of document library file shortcuts where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching document library file shortcuts
	 */
	@Override
	public int countByUUID_G(String uuid, long groupId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_UUID_G;

		Object[] finderArgs = new Object[] { uuid, groupId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_DLFILESHORTCUT_WHERE);

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

	private static final String _FINDER_COLUMN_UUID_G_UUID_1 = "dlFileShortcut.uuid IS NULL AND ";
	private static final String _FINDER_COLUMN_UUID_G_UUID_2 = "dlFileShortcut.uuid = ? AND ";
	private static final String _FINDER_COLUMN_UUID_G_UUID_3 = "(dlFileShortcut.uuid IS NULL OR dlFileShortcut.uuid = '') AND ";
	private static final String _FINDER_COLUMN_UUID_G_GROUPID_2 = "dlFileShortcut.groupId = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_UUID_C = new FinderPath(DLFileShortcutModelImpl.ENTITY_CACHE_ENABLED,
			DLFileShortcutModelImpl.FINDER_CACHE_ENABLED,
			DLFileShortcutImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByUuid_C",
			new String[] {
				String.class.getName(), Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C =
		new FinderPath(DLFileShortcutModelImpl.ENTITY_CACHE_ENABLED,
			DLFileShortcutModelImpl.FINDER_CACHE_ENABLED,
			DLFileShortcutImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid_C",
			new String[] { String.class.getName(), Long.class.getName() },
			DLFileShortcutModelImpl.UUID_COLUMN_BITMASK |
			DLFileShortcutModelImpl.COMPANYID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID_C = new FinderPath(DLFileShortcutModelImpl.ENTITY_CACHE_ENABLED,
			DLFileShortcutModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUuid_C",
			new String[] { String.class.getName(), Long.class.getName() });

	/**
	 * Returns all the document library file shortcuts where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching document library file shortcuts
	 */
	@Override
	public List<DLFileShortcut> findByUuid_C(String uuid, long companyId) {
		return findByUuid_C(uuid, companyId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the document library file shortcuts where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFileShortcutModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of document library file shortcuts
	 * @param end the upper bound of the range of document library file shortcuts (not inclusive)
	 * @return the range of matching document library file shortcuts
	 */
	@Override
	public List<DLFileShortcut> findByUuid_C(String uuid, long companyId,
		int start, int end) {
		return findByUuid_C(uuid, companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the document library file shortcuts where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFileShortcutModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of document library file shortcuts
	 * @param end the upper bound of the range of document library file shortcuts (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching document library file shortcuts
	 */
	@Override
	public List<DLFileShortcut> findByUuid_C(String uuid, long companyId,
		int start, int end, OrderByComparator<DLFileShortcut> orderByComparator) {
		return findByUuid_C(uuid, companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the document library file shortcuts where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFileShortcutModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of document library file shortcuts
	 * @param end the upper bound of the range of document library file shortcuts (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching document library file shortcuts
	 */
	@Override
	public List<DLFileShortcut> findByUuid_C(String uuid, long companyId,
		int start, int end,
		OrderByComparator<DLFileShortcut> orderByComparator,
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

		List<DLFileShortcut> list = null;

		if (retrieveFromCache) {
			list = (List<DLFileShortcut>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (DLFileShortcut dlFileShortcut : list) {
					if (!Validator.equals(uuid, dlFileShortcut.getUuid()) ||
							(companyId != dlFileShortcut.getCompanyId())) {
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
						(orderByComparator.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(4);
			}

			query.append(_SQL_SELECT_DLFILESHORTCUT_WHERE);

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
				query.append(DLFileShortcutModelImpl.ORDER_BY_JPQL);
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
					list = (List<DLFileShortcut>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<DLFileShortcut>)QueryUtil.list(q,
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
	 * Returns the first document library file shortcut in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching document library file shortcut
	 * @throws NoSuchFileShortcutException if a matching document library file shortcut could not be found
	 */
	@Override
	public DLFileShortcut findByUuid_C_First(String uuid, long companyId,
		OrderByComparator<DLFileShortcut> orderByComparator)
		throws NoSuchFileShortcutException {
		DLFileShortcut dlFileShortcut = fetchByUuid_C_First(uuid, companyId,
				orderByComparator);

		if (dlFileShortcut != null) {
			return dlFileShortcut;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(", companyId=");
		msg.append(companyId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchFileShortcutException(msg.toString());
	}

	/**
	 * Returns the first document library file shortcut in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching document library file shortcut, or <code>null</code> if a matching document library file shortcut could not be found
	 */
	@Override
	public DLFileShortcut fetchByUuid_C_First(String uuid, long companyId,
		OrderByComparator<DLFileShortcut> orderByComparator) {
		List<DLFileShortcut> list = findByUuid_C(uuid, companyId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last document library file shortcut in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching document library file shortcut
	 * @throws NoSuchFileShortcutException if a matching document library file shortcut could not be found
	 */
	@Override
	public DLFileShortcut findByUuid_C_Last(String uuid, long companyId,
		OrderByComparator<DLFileShortcut> orderByComparator)
		throws NoSuchFileShortcutException {
		DLFileShortcut dlFileShortcut = fetchByUuid_C_Last(uuid, companyId,
				orderByComparator);

		if (dlFileShortcut != null) {
			return dlFileShortcut;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(", companyId=");
		msg.append(companyId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchFileShortcutException(msg.toString());
	}

	/**
	 * Returns the last document library file shortcut in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching document library file shortcut, or <code>null</code> if a matching document library file shortcut could not be found
	 */
	@Override
	public DLFileShortcut fetchByUuid_C_Last(String uuid, long companyId,
		OrderByComparator<DLFileShortcut> orderByComparator) {
		int count = countByUuid_C(uuid, companyId);

		if (count == 0) {
			return null;
		}

		List<DLFileShortcut> list = findByUuid_C(uuid, companyId, count - 1,
				count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the document library file shortcuts before and after the current document library file shortcut in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param fileShortcutId the primary key of the current document library file shortcut
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next document library file shortcut
	 * @throws NoSuchFileShortcutException if a document library file shortcut with the primary key could not be found
	 */
	@Override
	public DLFileShortcut[] findByUuid_C_PrevAndNext(long fileShortcutId,
		String uuid, long companyId,
		OrderByComparator<DLFileShortcut> orderByComparator)
		throws NoSuchFileShortcutException {
		DLFileShortcut dlFileShortcut = findByPrimaryKey(fileShortcutId);

		Session session = null;

		try {
			session = openSession();

			DLFileShortcut[] array = new DLFileShortcutImpl[3];

			array[0] = getByUuid_C_PrevAndNext(session, dlFileShortcut, uuid,
					companyId, orderByComparator, true);

			array[1] = dlFileShortcut;

			array[2] = getByUuid_C_PrevAndNext(session, dlFileShortcut, uuid,
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

	protected DLFileShortcut getByUuid_C_PrevAndNext(Session session,
		DLFileShortcut dlFileShortcut, String uuid, long companyId,
		OrderByComparator<DLFileShortcut> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_DLFILESHORTCUT_WHERE);

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
			query.append(DLFileShortcutModelImpl.ORDER_BY_JPQL);
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
			Object[] values = orderByComparator.getOrderByConditionValues(dlFileShortcut);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<DLFileShortcut> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the document library file shortcuts where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	@Override
	public void removeByUuid_C(String uuid, long companyId) {
		for (DLFileShortcut dlFileShortcut : findByUuid_C(uuid, companyId,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(dlFileShortcut);
		}
	}

	/**
	 * Returns the number of document library file shortcuts where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching document library file shortcuts
	 */
	@Override
	public int countByUuid_C(String uuid, long companyId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_UUID_C;

		Object[] finderArgs = new Object[] { uuid, companyId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_DLFILESHORTCUT_WHERE);

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

	private static final String _FINDER_COLUMN_UUID_C_UUID_1 = "dlFileShortcut.uuid IS NULL AND ";
	private static final String _FINDER_COLUMN_UUID_C_UUID_2 = "dlFileShortcut.uuid = ? AND ";
	private static final String _FINDER_COLUMN_UUID_C_UUID_3 = "(dlFileShortcut.uuid IS NULL OR dlFileShortcut.uuid = '') AND ";
	private static final String _FINDER_COLUMN_UUID_C_COMPANYID_2 = "dlFileShortcut.companyId = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_COMPANYID =
		new FinderPath(DLFileShortcutModelImpl.ENTITY_CACHE_ENABLED,
			DLFileShortcutModelImpl.FINDER_CACHE_ENABLED,
			DLFileShortcutImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByCompanyId",
			new String[] {
				Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID =
		new FinderPath(DLFileShortcutModelImpl.ENTITY_CACHE_ENABLED,
			DLFileShortcutModelImpl.FINDER_CACHE_ENABLED,
			DLFileShortcutImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByCompanyId",
			new String[] { Long.class.getName() },
			DLFileShortcutModelImpl.COMPANYID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_COMPANYID = new FinderPath(DLFileShortcutModelImpl.ENTITY_CACHE_ENABLED,
			DLFileShortcutModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByCompanyId",
			new String[] { Long.class.getName() });

	/**
	 * Returns all the document library file shortcuts where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching document library file shortcuts
	 */
	@Override
	public List<DLFileShortcut> findByCompanyId(long companyId) {
		return findByCompanyId(companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the document library file shortcuts where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFileShortcutModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of document library file shortcuts
	 * @param end the upper bound of the range of document library file shortcuts (not inclusive)
	 * @return the range of matching document library file shortcuts
	 */
	@Override
	public List<DLFileShortcut> findByCompanyId(long companyId, int start,
		int end) {
		return findByCompanyId(companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the document library file shortcuts where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFileShortcutModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of document library file shortcuts
	 * @param end the upper bound of the range of document library file shortcuts (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching document library file shortcuts
	 */
	@Override
	public List<DLFileShortcut> findByCompanyId(long companyId, int start,
		int end, OrderByComparator<DLFileShortcut> orderByComparator) {
		return findByCompanyId(companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the document library file shortcuts where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFileShortcutModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of document library file shortcuts
	 * @param end the upper bound of the range of document library file shortcuts (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching document library file shortcuts
	 */
	@Override
	public List<DLFileShortcut> findByCompanyId(long companyId, int start,
		int end, OrderByComparator<DLFileShortcut> orderByComparator,
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

		List<DLFileShortcut> list = null;

		if (retrieveFromCache) {
			list = (List<DLFileShortcut>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (DLFileShortcut dlFileShortcut : list) {
					if ((companyId != dlFileShortcut.getCompanyId())) {
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
						(orderByComparator.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(3);
			}

			query.append(_SQL_SELECT_DLFILESHORTCUT_WHERE);

			query.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(DLFileShortcutModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				if (!pagination) {
					list = (List<DLFileShortcut>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<DLFileShortcut>)QueryUtil.list(q,
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
	 * Returns the first document library file shortcut in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching document library file shortcut
	 * @throws NoSuchFileShortcutException if a matching document library file shortcut could not be found
	 */
	@Override
	public DLFileShortcut findByCompanyId_First(long companyId,
		OrderByComparator<DLFileShortcut> orderByComparator)
		throws NoSuchFileShortcutException {
		DLFileShortcut dlFileShortcut = fetchByCompanyId_First(companyId,
				orderByComparator);

		if (dlFileShortcut != null) {
			return dlFileShortcut;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("companyId=");
		msg.append(companyId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchFileShortcutException(msg.toString());
	}

	/**
	 * Returns the first document library file shortcut in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching document library file shortcut, or <code>null</code> if a matching document library file shortcut could not be found
	 */
	@Override
	public DLFileShortcut fetchByCompanyId_First(long companyId,
		OrderByComparator<DLFileShortcut> orderByComparator) {
		List<DLFileShortcut> list = findByCompanyId(companyId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last document library file shortcut in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching document library file shortcut
	 * @throws NoSuchFileShortcutException if a matching document library file shortcut could not be found
	 */
	@Override
	public DLFileShortcut findByCompanyId_Last(long companyId,
		OrderByComparator<DLFileShortcut> orderByComparator)
		throws NoSuchFileShortcutException {
		DLFileShortcut dlFileShortcut = fetchByCompanyId_Last(companyId,
				orderByComparator);

		if (dlFileShortcut != null) {
			return dlFileShortcut;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("companyId=");
		msg.append(companyId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchFileShortcutException(msg.toString());
	}

	/**
	 * Returns the last document library file shortcut in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching document library file shortcut, or <code>null</code> if a matching document library file shortcut could not be found
	 */
	@Override
	public DLFileShortcut fetchByCompanyId_Last(long companyId,
		OrderByComparator<DLFileShortcut> orderByComparator) {
		int count = countByCompanyId(companyId);

		if (count == 0) {
			return null;
		}

		List<DLFileShortcut> list = findByCompanyId(companyId, count - 1,
				count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the document library file shortcuts before and after the current document library file shortcut in the ordered set where companyId = &#63;.
	 *
	 * @param fileShortcutId the primary key of the current document library file shortcut
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next document library file shortcut
	 * @throws NoSuchFileShortcutException if a document library file shortcut with the primary key could not be found
	 */
	@Override
	public DLFileShortcut[] findByCompanyId_PrevAndNext(long fileShortcutId,
		long companyId, OrderByComparator<DLFileShortcut> orderByComparator)
		throws NoSuchFileShortcutException {
		DLFileShortcut dlFileShortcut = findByPrimaryKey(fileShortcutId);

		Session session = null;

		try {
			session = openSession();

			DLFileShortcut[] array = new DLFileShortcutImpl[3];

			array[0] = getByCompanyId_PrevAndNext(session, dlFileShortcut,
					companyId, orderByComparator, true);

			array[1] = dlFileShortcut;

			array[2] = getByCompanyId_PrevAndNext(session, dlFileShortcut,
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

	protected DLFileShortcut getByCompanyId_PrevAndNext(Session session,
		DLFileShortcut dlFileShortcut, long companyId,
		OrderByComparator<DLFileShortcut> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_DLFILESHORTCUT_WHERE);

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
			query.append(DLFileShortcutModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(companyId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(dlFileShortcut);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<DLFileShortcut> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the document library file shortcuts where companyId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 */
	@Override
	public void removeByCompanyId(long companyId) {
		for (DLFileShortcut dlFileShortcut : findByCompanyId(companyId,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(dlFileShortcut);
		}
	}

	/**
	 * Returns the number of document library file shortcuts where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching document library file shortcuts
	 */
	@Override
	public int countByCompanyId(long companyId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_COMPANYID;

		Object[] finderArgs = new Object[] { companyId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_DLFILESHORTCUT_WHERE);

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

	private static final String _FINDER_COLUMN_COMPANYID_COMPANYID_2 = "dlFileShortcut.companyId = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_TOFILEENTRYID =
		new FinderPath(DLFileShortcutModelImpl.ENTITY_CACHE_ENABLED,
			DLFileShortcutModelImpl.FINDER_CACHE_ENABLED,
			DLFileShortcutImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByToFileEntryId",
			new String[] {
				Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_TOFILEENTRYID =
		new FinderPath(DLFileShortcutModelImpl.ENTITY_CACHE_ENABLED,
			DLFileShortcutModelImpl.FINDER_CACHE_ENABLED,
			DLFileShortcutImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByToFileEntryId",
			new String[] { Long.class.getName() },
			DLFileShortcutModelImpl.TOFILEENTRYID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_TOFILEENTRYID = new FinderPath(DLFileShortcutModelImpl.ENTITY_CACHE_ENABLED,
			DLFileShortcutModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByToFileEntryId",
			new String[] { Long.class.getName() });

	/**
	 * Returns all the document library file shortcuts where toFileEntryId = &#63;.
	 *
	 * @param toFileEntryId the to file entry ID
	 * @return the matching document library file shortcuts
	 */
	@Override
	public List<DLFileShortcut> findByToFileEntryId(long toFileEntryId) {
		return findByToFileEntryId(toFileEntryId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the document library file shortcuts where toFileEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFileShortcutModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param toFileEntryId the to file entry ID
	 * @param start the lower bound of the range of document library file shortcuts
	 * @param end the upper bound of the range of document library file shortcuts (not inclusive)
	 * @return the range of matching document library file shortcuts
	 */
	@Override
	public List<DLFileShortcut> findByToFileEntryId(long toFileEntryId,
		int start, int end) {
		return findByToFileEntryId(toFileEntryId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the document library file shortcuts where toFileEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFileShortcutModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param toFileEntryId the to file entry ID
	 * @param start the lower bound of the range of document library file shortcuts
	 * @param end the upper bound of the range of document library file shortcuts (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching document library file shortcuts
	 */
	@Override
	public List<DLFileShortcut> findByToFileEntryId(long toFileEntryId,
		int start, int end, OrderByComparator<DLFileShortcut> orderByComparator) {
		return findByToFileEntryId(toFileEntryId, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the document library file shortcuts where toFileEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFileShortcutModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param toFileEntryId the to file entry ID
	 * @param start the lower bound of the range of document library file shortcuts
	 * @param end the upper bound of the range of document library file shortcuts (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching document library file shortcuts
	 */
	@Override
	public List<DLFileShortcut> findByToFileEntryId(long toFileEntryId,
		int start, int end,
		OrderByComparator<DLFileShortcut> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_TOFILEENTRYID;
			finderArgs = new Object[] { toFileEntryId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_TOFILEENTRYID;
			finderArgs = new Object[] {
					toFileEntryId,
					
					start, end, orderByComparator
				};
		}

		List<DLFileShortcut> list = null;

		if (retrieveFromCache) {
			list = (List<DLFileShortcut>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (DLFileShortcut dlFileShortcut : list) {
					if ((toFileEntryId != dlFileShortcut.getToFileEntryId())) {
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
						(orderByComparator.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(3);
			}

			query.append(_SQL_SELECT_DLFILESHORTCUT_WHERE);

			query.append(_FINDER_COLUMN_TOFILEENTRYID_TOFILEENTRYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(DLFileShortcutModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(toFileEntryId);

				if (!pagination) {
					list = (List<DLFileShortcut>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<DLFileShortcut>)QueryUtil.list(q,
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
	 * Returns the first document library file shortcut in the ordered set where toFileEntryId = &#63;.
	 *
	 * @param toFileEntryId the to file entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching document library file shortcut
	 * @throws NoSuchFileShortcutException if a matching document library file shortcut could not be found
	 */
	@Override
	public DLFileShortcut findByToFileEntryId_First(long toFileEntryId,
		OrderByComparator<DLFileShortcut> orderByComparator)
		throws NoSuchFileShortcutException {
		DLFileShortcut dlFileShortcut = fetchByToFileEntryId_First(toFileEntryId,
				orderByComparator);

		if (dlFileShortcut != null) {
			return dlFileShortcut;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("toFileEntryId=");
		msg.append(toFileEntryId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchFileShortcutException(msg.toString());
	}

	/**
	 * Returns the first document library file shortcut in the ordered set where toFileEntryId = &#63;.
	 *
	 * @param toFileEntryId the to file entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching document library file shortcut, or <code>null</code> if a matching document library file shortcut could not be found
	 */
	@Override
	public DLFileShortcut fetchByToFileEntryId_First(long toFileEntryId,
		OrderByComparator<DLFileShortcut> orderByComparator) {
		List<DLFileShortcut> list = findByToFileEntryId(toFileEntryId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last document library file shortcut in the ordered set where toFileEntryId = &#63;.
	 *
	 * @param toFileEntryId the to file entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching document library file shortcut
	 * @throws NoSuchFileShortcutException if a matching document library file shortcut could not be found
	 */
	@Override
	public DLFileShortcut findByToFileEntryId_Last(long toFileEntryId,
		OrderByComparator<DLFileShortcut> orderByComparator)
		throws NoSuchFileShortcutException {
		DLFileShortcut dlFileShortcut = fetchByToFileEntryId_Last(toFileEntryId,
				orderByComparator);

		if (dlFileShortcut != null) {
			return dlFileShortcut;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("toFileEntryId=");
		msg.append(toFileEntryId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchFileShortcutException(msg.toString());
	}

	/**
	 * Returns the last document library file shortcut in the ordered set where toFileEntryId = &#63;.
	 *
	 * @param toFileEntryId the to file entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching document library file shortcut, or <code>null</code> if a matching document library file shortcut could not be found
	 */
	@Override
	public DLFileShortcut fetchByToFileEntryId_Last(long toFileEntryId,
		OrderByComparator<DLFileShortcut> orderByComparator) {
		int count = countByToFileEntryId(toFileEntryId);

		if (count == 0) {
			return null;
		}

		List<DLFileShortcut> list = findByToFileEntryId(toFileEntryId,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the document library file shortcuts before and after the current document library file shortcut in the ordered set where toFileEntryId = &#63;.
	 *
	 * @param fileShortcutId the primary key of the current document library file shortcut
	 * @param toFileEntryId the to file entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next document library file shortcut
	 * @throws NoSuchFileShortcutException if a document library file shortcut with the primary key could not be found
	 */
	@Override
	public DLFileShortcut[] findByToFileEntryId_PrevAndNext(
		long fileShortcutId, long toFileEntryId,
		OrderByComparator<DLFileShortcut> orderByComparator)
		throws NoSuchFileShortcutException {
		DLFileShortcut dlFileShortcut = findByPrimaryKey(fileShortcutId);

		Session session = null;

		try {
			session = openSession();

			DLFileShortcut[] array = new DLFileShortcutImpl[3];

			array[0] = getByToFileEntryId_PrevAndNext(session, dlFileShortcut,
					toFileEntryId, orderByComparator, true);

			array[1] = dlFileShortcut;

			array[2] = getByToFileEntryId_PrevAndNext(session, dlFileShortcut,
					toFileEntryId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected DLFileShortcut getByToFileEntryId_PrevAndNext(Session session,
		DLFileShortcut dlFileShortcut, long toFileEntryId,
		OrderByComparator<DLFileShortcut> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_DLFILESHORTCUT_WHERE);

		query.append(_FINDER_COLUMN_TOFILEENTRYID_TOFILEENTRYID_2);

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
			query.append(DLFileShortcutModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(toFileEntryId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(dlFileShortcut);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<DLFileShortcut> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the document library file shortcuts where toFileEntryId = &#63; from the database.
	 *
	 * @param toFileEntryId the to file entry ID
	 */
	@Override
	public void removeByToFileEntryId(long toFileEntryId) {
		for (DLFileShortcut dlFileShortcut : findByToFileEntryId(
				toFileEntryId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(dlFileShortcut);
		}
	}

	/**
	 * Returns the number of document library file shortcuts where toFileEntryId = &#63;.
	 *
	 * @param toFileEntryId the to file entry ID
	 * @return the number of matching document library file shortcuts
	 */
	@Override
	public int countByToFileEntryId(long toFileEntryId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_TOFILEENTRYID;

		Object[] finderArgs = new Object[] { toFileEntryId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_DLFILESHORTCUT_WHERE);

			query.append(_FINDER_COLUMN_TOFILEENTRYID_TOFILEENTRYID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(toFileEntryId);

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

	private static final String _FINDER_COLUMN_TOFILEENTRYID_TOFILEENTRYID_2 = "dlFileShortcut.toFileEntryId = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_G_F = new FinderPath(DLFileShortcutModelImpl.ENTITY_CACHE_ENABLED,
			DLFileShortcutModelImpl.FINDER_CACHE_ENABLED,
			DLFileShortcutImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByG_F",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_F = new FinderPath(DLFileShortcutModelImpl.ENTITY_CACHE_ENABLED,
			DLFileShortcutModelImpl.FINDER_CACHE_ENABLED,
			DLFileShortcutImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByG_F",
			new String[] { Long.class.getName(), Long.class.getName() },
			DLFileShortcutModelImpl.GROUPID_COLUMN_BITMASK |
			DLFileShortcutModelImpl.FOLDERID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_G_F = new FinderPath(DLFileShortcutModelImpl.ENTITY_CACHE_ENABLED,
			DLFileShortcutModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_F",
			new String[] { Long.class.getName(), Long.class.getName() });

	/**
	 * Returns all the document library file shortcuts where groupId = &#63; and folderId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @return the matching document library file shortcuts
	 */
	@Override
	public List<DLFileShortcut> findByG_F(long groupId, long folderId) {
		return findByG_F(groupId, folderId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the document library file shortcuts where groupId = &#63; and folderId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFileShortcutModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param start the lower bound of the range of document library file shortcuts
	 * @param end the upper bound of the range of document library file shortcuts (not inclusive)
	 * @return the range of matching document library file shortcuts
	 */
	@Override
	public List<DLFileShortcut> findByG_F(long groupId, long folderId,
		int start, int end) {
		return findByG_F(groupId, folderId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the document library file shortcuts where groupId = &#63; and folderId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFileShortcutModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param start the lower bound of the range of document library file shortcuts
	 * @param end the upper bound of the range of document library file shortcuts (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching document library file shortcuts
	 */
	@Override
	public List<DLFileShortcut> findByG_F(long groupId, long folderId,
		int start, int end, OrderByComparator<DLFileShortcut> orderByComparator) {
		return findByG_F(groupId, folderId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the document library file shortcuts where groupId = &#63; and folderId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFileShortcutModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param start the lower bound of the range of document library file shortcuts
	 * @param end the upper bound of the range of document library file shortcuts (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching document library file shortcuts
	 */
	@Override
	public List<DLFileShortcut> findByG_F(long groupId, long folderId,
		int start, int end,
		OrderByComparator<DLFileShortcut> orderByComparator,
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

		List<DLFileShortcut> list = null;

		if (retrieveFromCache) {
			list = (List<DLFileShortcut>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (DLFileShortcut dlFileShortcut : list) {
					if ((groupId != dlFileShortcut.getGroupId()) ||
							(folderId != dlFileShortcut.getFolderId())) {
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
						(orderByComparator.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(4);
			}

			query.append(_SQL_SELECT_DLFILESHORTCUT_WHERE);

			query.append(_FINDER_COLUMN_G_F_GROUPID_2);

			query.append(_FINDER_COLUMN_G_F_FOLDERID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(DLFileShortcutModelImpl.ORDER_BY_JPQL);
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
					list = (List<DLFileShortcut>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<DLFileShortcut>)QueryUtil.list(q,
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
	 * Returns the first document library file shortcut in the ordered set where groupId = &#63; and folderId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching document library file shortcut
	 * @throws NoSuchFileShortcutException if a matching document library file shortcut could not be found
	 */
	@Override
	public DLFileShortcut findByG_F_First(long groupId, long folderId,
		OrderByComparator<DLFileShortcut> orderByComparator)
		throws NoSuchFileShortcutException {
		DLFileShortcut dlFileShortcut = fetchByG_F_First(groupId, folderId,
				orderByComparator);

		if (dlFileShortcut != null) {
			return dlFileShortcut;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", folderId=");
		msg.append(folderId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchFileShortcutException(msg.toString());
	}

	/**
	 * Returns the first document library file shortcut in the ordered set where groupId = &#63; and folderId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching document library file shortcut, or <code>null</code> if a matching document library file shortcut could not be found
	 */
	@Override
	public DLFileShortcut fetchByG_F_First(long groupId, long folderId,
		OrderByComparator<DLFileShortcut> orderByComparator) {
		List<DLFileShortcut> list = findByG_F(groupId, folderId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last document library file shortcut in the ordered set where groupId = &#63; and folderId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching document library file shortcut
	 * @throws NoSuchFileShortcutException if a matching document library file shortcut could not be found
	 */
	@Override
	public DLFileShortcut findByG_F_Last(long groupId, long folderId,
		OrderByComparator<DLFileShortcut> orderByComparator)
		throws NoSuchFileShortcutException {
		DLFileShortcut dlFileShortcut = fetchByG_F_Last(groupId, folderId,
				orderByComparator);

		if (dlFileShortcut != null) {
			return dlFileShortcut;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", folderId=");
		msg.append(folderId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchFileShortcutException(msg.toString());
	}

	/**
	 * Returns the last document library file shortcut in the ordered set where groupId = &#63; and folderId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching document library file shortcut, or <code>null</code> if a matching document library file shortcut could not be found
	 */
	@Override
	public DLFileShortcut fetchByG_F_Last(long groupId, long folderId,
		OrderByComparator<DLFileShortcut> orderByComparator) {
		int count = countByG_F(groupId, folderId);

		if (count == 0) {
			return null;
		}

		List<DLFileShortcut> list = findByG_F(groupId, folderId, count - 1,
				count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the document library file shortcuts before and after the current document library file shortcut in the ordered set where groupId = &#63; and folderId = &#63;.
	 *
	 * @param fileShortcutId the primary key of the current document library file shortcut
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next document library file shortcut
	 * @throws NoSuchFileShortcutException if a document library file shortcut with the primary key could not be found
	 */
	@Override
	public DLFileShortcut[] findByG_F_PrevAndNext(long fileShortcutId,
		long groupId, long folderId,
		OrderByComparator<DLFileShortcut> orderByComparator)
		throws NoSuchFileShortcutException {
		DLFileShortcut dlFileShortcut = findByPrimaryKey(fileShortcutId);

		Session session = null;

		try {
			session = openSession();

			DLFileShortcut[] array = new DLFileShortcutImpl[3];

			array[0] = getByG_F_PrevAndNext(session, dlFileShortcut, groupId,
					folderId, orderByComparator, true);

			array[1] = dlFileShortcut;

			array[2] = getByG_F_PrevAndNext(session, dlFileShortcut, groupId,
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

	protected DLFileShortcut getByG_F_PrevAndNext(Session session,
		DLFileShortcut dlFileShortcut, long groupId, long folderId,
		OrderByComparator<DLFileShortcut> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_DLFILESHORTCUT_WHERE);

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
			query.append(DLFileShortcutModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		qPos.add(folderId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(dlFileShortcut);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<DLFileShortcut> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the document library file shortcuts that the user has permission to view where groupId = &#63; and folderId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @return the matching document library file shortcuts that the user has permission to view
	 */
	@Override
	public List<DLFileShortcut> filterFindByG_F(long groupId, long folderId) {
		return filterFindByG_F(groupId, folderId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the document library file shortcuts that the user has permission to view where groupId = &#63; and folderId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFileShortcutModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param start the lower bound of the range of document library file shortcuts
	 * @param end the upper bound of the range of document library file shortcuts (not inclusive)
	 * @return the range of matching document library file shortcuts that the user has permission to view
	 */
	@Override
	public List<DLFileShortcut> filterFindByG_F(long groupId, long folderId,
		int start, int end) {
		return filterFindByG_F(groupId, folderId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the document library file shortcuts that the user has permissions to view where groupId = &#63; and folderId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFileShortcutModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param start the lower bound of the range of document library file shortcuts
	 * @param end the upper bound of the range of document library file shortcuts (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching document library file shortcuts that the user has permission to view
	 */
	@Override
	public List<DLFileShortcut> filterFindByG_F(long groupId, long folderId,
		int start, int end, OrderByComparator<DLFileShortcut> orderByComparator) {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_F(groupId, folderId, start, end, orderByComparator);
		}

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(4 +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(4);
		}

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_DLFILESHORTCUT_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_DLFILESHORTCUT_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_G_F_GROUPID_2);

		query.append(_FINDER_COLUMN_G_F_FOLDERID_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_DLFILESHORTCUT_NO_INLINE_DISTINCT_WHERE_2);
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
				query.append(DLFileShortcutModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(DLFileShortcutModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				DLFileShortcut.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				q.addEntity(_FILTER_ENTITY_ALIAS, DLFileShortcutImpl.class);
			}
			else {
				q.addEntity(_FILTER_ENTITY_TABLE, DLFileShortcutImpl.class);
			}

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			qPos.add(folderId);

			return (List<DLFileShortcut>)QueryUtil.list(q, getDialect(), start,
				end);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Returns the document library file shortcuts before and after the current document library file shortcut in the ordered set of document library file shortcuts that the user has permission to view where groupId = &#63; and folderId = &#63;.
	 *
	 * @param fileShortcutId the primary key of the current document library file shortcut
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next document library file shortcut
	 * @throws NoSuchFileShortcutException if a document library file shortcut with the primary key could not be found
	 */
	@Override
	public DLFileShortcut[] filterFindByG_F_PrevAndNext(long fileShortcutId,
		long groupId, long folderId,
		OrderByComparator<DLFileShortcut> orderByComparator)
		throws NoSuchFileShortcutException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_F_PrevAndNext(fileShortcutId, groupId, folderId,
				orderByComparator);
		}

		DLFileShortcut dlFileShortcut = findByPrimaryKey(fileShortcutId);

		Session session = null;

		try {
			session = openSession();

			DLFileShortcut[] array = new DLFileShortcutImpl[3];

			array[0] = filterGetByG_F_PrevAndNext(session, dlFileShortcut,
					groupId, folderId, orderByComparator, true);

			array[1] = dlFileShortcut;

			array[2] = filterGetByG_F_PrevAndNext(session, dlFileShortcut,
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

	protected DLFileShortcut filterGetByG_F_PrevAndNext(Session session,
		DLFileShortcut dlFileShortcut, long groupId, long folderId,
		OrderByComparator<DLFileShortcut> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_DLFILESHORTCUT_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_DLFILESHORTCUT_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_G_F_GROUPID_2);

		query.append(_FINDER_COLUMN_G_F_FOLDERID_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_DLFILESHORTCUT_NO_INLINE_DISTINCT_WHERE_2);
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
				query.append(DLFileShortcutModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(DLFileShortcutModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				DLFileShortcut.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		SQLQuery q = session.createSynchronizedSQLQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		if (getDB().isSupportsInlineDistinct()) {
			q.addEntity(_FILTER_ENTITY_ALIAS, DLFileShortcutImpl.class);
		}
		else {
			q.addEntity(_FILTER_ENTITY_TABLE, DLFileShortcutImpl.class);
		}

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		qPos.add(folderId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(dlFileShortcut);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<DLFileShortcut> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the document library file shortcuts where groupId = &#63; and folderId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 */
	@Override
	public void removeByG_F(long groupId, long folderId) {
		for (DLFileShortcut dlFileShortcut : findByG_F(groupId, folderId,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(dlFileShortcut);
		}
	}

	/**
	 * Returns the number of document library file shortcuts where groupId = &#63; and folderId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @return the number of matching document library file shortcuts
	 */
	@Override
	public int countByG_F(long groupId, long folderId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_G_F;

		Object[] finderArgs = new Object[] { groupId, folderId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_DLFILESHORTCUT_WHERE);

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
	 * Returns the number of document library file shortcuts that the user has permission to view where groupId = &#63; and folderId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @return the number of matching document library file shortcuts that the user has permission to view
	 */
	@Override
	public int filterCountByG_F(long groupId, long folderId) {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByG_F(groupId, folderId);
		}

		StringBundler query = new StringBundler(3);

		query.append(_FILTER_SQL_COUNT_DLFILESHORTCUT_WHERE);

		query.append(_FINDER_COLUMN_G_F_GROUPID_2);

		query.append(_FINDER_COLUMN_G_F_FOLDERID_2);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				DLFileShortcut.class.getName(),
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

	private static final String _FINDER_COLUMN_G_F_GROUPID_2 = "dlFileShortcut.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_F_FOLDERID_2 = "dlFileShortcut.folderId = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_C_NOTS = new FinderPath(DLFileShortcutModelImpl.ENTITY_CACHE_ENABLED,
			DLFileShortcutModelImpl.FINDER_CACHE_ENABLED,
			DLFileShortcutImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByC_NotS",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_COUNT_BY_C_NOTS = new FinderPath(DLFileShortcutModelImpl.ENTITY_CACHE_ENABLED,
			DLFileShortcutModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "countByC_NotS",
			new String[] { Long.class.getName(), Integer.class.getName() });

	/**
	 * Returns all the document library file shortcuts where companyId = &#63; and status &ne; &#63;.
	 *
	 * @param companyId the company ID
	 * @param status the status
	 * @return the matching document library file shortcuts
	 */
	@Override
	public List<DLFileShortcut> findByC_NotS(long companyId, int status) {
		return findByC_NotS(companyId, status, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the document library file shortcuts where companyId = &#63; and status &ne; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFileShortcutModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param status the status
	 * @param start the lower bound of the range of document library file shortcuts
	 * @param end the upper bound of the range of document library file shortcuts (not inclusive)
	 * @return the range of matching document library file shortcuts
	 */
	@Override
	public List<DLFileShortcut> findByC_NotS(long companyId, int status,
		int start, int end) {
		return findByC_NotS(companyId, status, start, end, null);
	}

	/**
	 * Returns an ordered range of all the document library file shortcuts where companyId = &#63; and status &ne; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFileShortcutModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param status the status
	 * @param start the lower bound of the range of document library file shortcuts
	 * @param end the upper bound of the range of document library file shortcuts (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching document library file shortcuts
	 */
	@Override
	public List<DLFileShortcut> findByC_NotS(long companyId, int status,
		int start, int end, OrderByComparator<DLFileShortcut> orderByComparator) {
		return findByC_NotS(companyId, status, start, end, orderByComparator,
			true);
	}

	/**
	 * Returns an ordered range of all the document library file shortcuts where companyId = &#63; and status &ne; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFileShortcutModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param status the status
	 * @param start the lower bound of the range of document library file shortcuts
	 * @param end the upper bound of the range of document library file shortcuts (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching document library file shortcuts
	 */
	@Override
	public List<DLFileShortcut> findByC_NotS(long companyId, int status,
		int start, int end,
		OrderByComparator<DLFileShortcut> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_C_NOTS;
		finderArgs = new Object[] {
				companyId, status,
				
				start, end, orderByComparator
			};

		List<DLFileShortcut> list = null;

		if (retrieveFromCache) {
			list = (List<DLFileShortcut>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (DLFileShortcut dlFileShortcut : list) {
					if ((companyId != dlFileShortcut.getCompanyId()) ||
							(status == dlFileShortcut.getStatus())) {
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
						(orderByComparator.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(4);
			}

			query.append(_SQL_SELECT_DLFILESHORTCUT_WHERE);

			query.append(_FINDER_COLUMN_C_NOTS_COMPANYID_2);

			query.append(_FINDER_COLUMN_C_NOTS_STATUS_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(DLFileShortcutModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				qPos.add(status);

				if (!pagination) {
					list = (List<DLFileShortcut>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<DLFileShortcut>)QueryUtil.list(q,
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
	 * Returns the first document library file shortcut in the ordered set where companyId = &#63; and status &ne; &#63;.
	 *
	 * @param companyId the company ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching document library file shortcut
	 * @throws NoSuchFileShortcutException if a matching document library file shortcut could not be found
	 */
	@Override
	public DLFileShortcut findByC_NotS_First(long companyId, int status,
		OrderByComparator<DLFileShortcut> orderByComparator)
		throws NoSuchFileShortcutException {
		DLFileShortcut dlFileShortcut = fetchByC_NotS_First(companyId, status,
				orderByComparator);

		if (dlFileShortcut != null) {
			return dlFileShortcut;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("companyId=");
		msg.append(companyId);

		msg.append(", status=");
		msg.append(status);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchFileShortcutException(msg.toString());
	}

	/**
	 * Returns the first document library file shortcut in the ordered set where companyId = &#63; and status &ne; &#63;.
	 *
	 * @param companyId the company ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching document library file shortcut, or <code>null</code> if a matching document library file shortcut could not be found
	 */
	@Override
	public DLFileShortcut fetchByC_NotS_First(long companyId, int status,
		OrderByComparator<DLFileShortcut> orderByComparator) {
		List<DLFileShortcut> list = findByC_NotS(companyId, status, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last document library file shortcut in the ordered set where companyId = &#63; and status &ne; &#63;.
	 *
	 * @param companyId the company ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching document library file shortcut
	 * @throws NoSuchFileShortcutException if a matching document library file shortcut could not be found
	 */
	@Override
	public DLFileShortcut findByC_NotS_Last(long companyId, int status,
		OrderByComparator<DLFileShortcut> orderByComparator)
		throws NoSuchFileShortcutException {
		DLFileShortcut dlFileShortcut = fetchByC_NotS_Last(companyId, status,
				orderByComparator);

		if (dlFileShortcut != null) {
			return dlFileShortcut;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("companyId=");
		msg.append(companyId);

		msg.append(", status=");
		msg.append(status);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchFileShortcutException(msg.toString());
	}

	/**
	 * Returns the last document library file shortcut in the ordered set where companyId = &#63; and status &ne; &#63;.
	 *
	 * @param companyId the company ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching document library file shortcut, or <code>null</code> if a matching document library file shortcut could not be found
	 */
	@Override
	public DLFileShortcut fetchByC_NotS_Last(long companyId, int status,
		OrderByComparator<DLFileShortcut> orderByComparator) {
		int count = countByC_NotS(companyId, status);

		if (count == 0) {
			return null;
		}

		List<DLFileShortcut> list = findByC_NotS(companyId, status, count - 1,
				count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the document library file shortcuts before and after the current document library file shortcut in the ordered set where companyId = &#63; and status &ne; &#63;.
	 *
	 * @param fileShortcutId the primary key of the current document library file shortcut
	 * @param companyId the company ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next document library file shortcut
	 * @throws NoSuchFileShortcutException if a document library file shortcut with the primary key could not be found
	 */
	@Override
	public DLFileShortcut[] findByC_NotS_PrevAndNext(long fileShortcutId,
		long companyId, int status,
		OrderByComparator<DLFileShortcut> orderByComparator)
		throws NoSuchFileShortcutException {
		DLFileShortcut dlFileShortcut = findByPrimaryKey(fileShortcutId);

		Session session = null;

		try {
			session = openSession();

			DLFileShortcut[] array = new DLFileShortcutImpl[3];

			array[0] = getByC_NotS_PrevAndNext(session, dlFileShortcut,
					companyId, status, orderByComparator, true);

			array[1] = dlFileShortcut;

			array[2] = getByC_NotS_PrevAndNext(session, dlFileShortcut,
					companyId, status, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected DLFileShortcut getByC_NotS_PrevAndNext(Session session,
		DLFileShortcut dlFileShortcut, long companyId, int status,
		OrderByComparator<DLFileShortcut> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_DLFILESHORTCUT_WHERE);

		query.append(_FINDER_COLUMN_C_NOTS_COMPANYID_2);

		query.append(_FINDER_COLUMN_C_NOTS_STATUS_2);

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
			query.append(DLFileShortcutModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(companyId);

		qPos.add(status);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(dlFileShortcut);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<DLFileShortcut> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the document library file shortcuts where companyId = &#63; and status &ne; &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param status the status
	 */
	@Override
	public void removeByC_NotS(long companyId, int status) {
		for (DLFileShortcut dlFileShortcut : findByC_NotS(companyId, status,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(dlFileShortcut);
		}
	}

	/**
	 * Returns the number of document library file shortcuts where companyId = &#63; and status &ne; &#63;.
	 *
	 * @param companyId the company ID
	 * @param status the status
	 * @return the number of matching document library file shortcuts
	 */
	@Override
	public int countByC_NotS(long companyId, int status) {
		FinderPath finderPath = FINDER_PATH_WITH_PAGINATION_COUNT_BY_C_NOTS;

		Object[] finderArgs = new Object[] { companyId, status };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_DLFILESHORTCUT_WHERE);

			query.append(_FINDER_COLUMN_C_NOTS_COMPANYID_2);

			query.append(_FINDER_COLUMN_C_NOTS_STATUS_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				qPos.add(status);

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

	private static final String _FINDER_COLUMN_C_NOTS_COMPANYID_2 = "dlFileShortcut.companyId = ? AND ";
	private static final String _FINDER_COLUMN_C_NOTS_STATUS_2 = "dlFileShortcut.status != ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_G_F_A = new FinderPath(DLFileShortcutModelImpl.ENTITY_CACHE_ENABLED,
			DLFileShortcutModelImpl.FINDER_CACHE_ENABLED,
			DLFileShortcutImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByG_F_A",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Boolean.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_F_A = new FinderPath(DLFileShortcutModelImpl.ENTITY_CACHE_ENABLED,
			DLFileShortcutModelImpl.FINDER_CACHE_ENABLED,
			DLFileShortcutImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByG_F_A",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Boolean.class.getName()
			},
			DLFileShortcutModelImpl.GROUPID_COLUMN_BITMASK |
			DLFileShortcutModelImpl.FOLDERID_COLUMN_BITMASK |
			DLFileShortcutModelImpl.ACTIVE_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_G_F_A = new FinderPath(DLFileShortcutModelImpl.ENTITY_CACHE_ENABLED,
			DLFileShortcutModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_F_A",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Boolean.class.getName()
			});

	/**
	 * Returns all the document library file shortcuts where groupId = &#63; and folderId = &#63; and active = &#63;.
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param active the active
	 * @return the matching document library file shortcuts
	 */
	@Override
	public List<DLFileShortcut> findByG_F_A(long groupId, long folderId,
		boolean active) {
		return findByG_F_A(groupId, folderId, active, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the document library file shortcuts where groupId = &#63; and folderId = &#63; and active = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFileShortcutModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param active the active
	 * @param start the lower bound of the range of document library file shortcuts
	 * @param end the upper bound of the range of document library file shortcuts (not inclusive)
	 * @return the range of matching document library file shortcuts
	 */
	@Override
	public List<DLFileShortcut> findByG_F_A(long groupId, long folderId,
		boolean active, int start, int end) {
		return findByG_F_A(groupId, folderId, active, start, end, null);
	}

	/**
	 * Returns an ordered range of all the document library file shortcuts where groupId = &#63; and folderId = &#63; and active = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFileShortcutModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param active the active
	 * @param start the lower bound of the range of document library file shortcuts
	 * @param end the upper bound of the range of document library file shortcuts (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching document library file shortcuts
	 */
	@Override
	public List<DLFileShortcut> findByG_F_A(long groupId, long folderId,
		boolean active, int start, int end,
		OrderByComparator<DLFileShortcut> orderByComparator) {
		return findByG_F_A(groupId, folderId, active, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the document library file shortcuts where groupId = &#63; and folderId = &#63; and active = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFileShortcutModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param active the active
	 * @param start the lower bound of the range of document library file shortcuts
	 * @param end the upper bound of the range of document library file shortcuts (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching document library file shortcuts
	 */
	@Override
	public List<DLFileShortcut> findByG_F_A(long groupId, long folderId,
		boolean active, int start, int end,
		OrderByComparator<DLFileShortcut> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_F_A;
			finderArgs = new Object[] { groupId, folderId, active };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_G_F_A;
			finderArgs = new Object[] {
					groupId, folderId, active,
					
					start, end, orderByComparator
				};
		}

		List<DLFileShortcut> list = null;

		if (retrieveFromCache) {
			list = (List<DLFileShortcut>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (DLFileShortcut dlFileShortcut : list) {
					if ((groupId != dlFileShortcut.getGroupId()) ||
							(folderId != dlFileShortcut.getFolderId()) ||
							(active != dlFileShortcut.getActive())) {
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
						(orderByComparator.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(5);
			}

			query.append(_SQL_SELECT_DLFILESHORTCUT_WHERE);

			query.append(_FINDER_COLUMN_G_F_A_GROUPID_2);

			query.append(_FINDER_COLUMN_G_F_A_FOLDERID_2);

			query.append(_FINDER_COLUMN_G_F_A_ACTIVE_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(DLFileShortcutModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(folderId);

				qPos.add(active);

				if (!pagination) {
					list = (List<DLFileShortcut>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<DLFileShortcut>)QueryUtil.list(q,
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
	 * Returns the first document library file shortcut in the ordered set where groupId = &#63; and folderId = &#63; and active = &#63;.
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching document library file shortcut
	 * @throws NoSuchFileShortcutException if a matching document library file shortcut could not be found
	 */
	@Override
	public DLFileShortcut findByG_F_A_First(long groupId, long folderId,
		boolean active, OrderByComparator<DLFileShortcut> orderByComparator)
		throws NoSuchFileShortcutException {
		DLFileShortcut dlFileShortcut = fetchByG_F_A_First(groupId, folderId,
				active, orderByComparator);

		if (dlFileShortcut != null) {
			return dlFileShortcut;
		}

		StringBundler msg = new StringBundler(8);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", folderId=");
		msg.append(folderId);

		msg.append(", active=");
		msg.append(active);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchFileShortcutException(msg.toString());
	}

	/**
	 * Returns the first document library file shortcut in the ordered set where groupId = &#63; and folderId = &#63; and active = &#63;.
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching document library file shortcut, or <code>null</code> if a matching document library file shortcut could not be found
	 */
	@Override
	public DLFileShortcut fetchByG_F_A_First(long groupId, long folderId,
		boolean active, OrderByComparator<DLFileShortcut> orderByComparator) {
		List<DLFileShortcut> list = findByG_F_A(groupId, folderId, active, 0,
				1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last document library file shortcut in the ordered set where groupId = &#63; and folderId = &#63; and active = &#63;.
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching document library file shortcut
	 * @throws NoSuchFileShortcutException if a matching document library file shortcut could not be found
	 */
	@Override
	public DLFileShortcut findByG_F_A_Last(long groupId, long folderId,
		boolean active, OrderByComparator<DLFileShortcut> orderByComparator)
		throws NoSuchFileShortcutException {
		DLFileShortcut dlFileShortcut = fetchByG_F_A_Last(groupId, folderId,
				active, orderByComparator);

		if (dlFileShortcut != null) {
			return dlFileShortcut;
		}

		StringBundler msg = new StringBundler(8);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", folderId=");
		msg.append(folderId);

		msg.append(", active=");
		msg.append(active);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchFileShortcutException(msg.toString());
	}

	/**
	 * Returns the last document library file shortcut in the ordered set where groupId = &#63; and folderId = &#63; and active = &#63;.
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching document library file shortcut, or <code>null</code> if a matching document library file shortcut could not be found
	 */
	@Override
	public DLFileShortcut fetchByG_F_A_Last(long groupId, long folderId,
		boolean active, OrderByComparator<DLFileShortcut> orderByComparator) {
		int count = countByG_F_A(groupId, folderId, active);

		if (count == 0) {
			return null;
		}

		List<DLFileShortcut> list = findByG_F_A(groupId, folderId, active,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the document library file shortcuts before and after the current document library file shortcut in the ordered set where groupId = &#63; and folderId = &#63; and active = &#63;.
	 *
	 * @param fileShortcutId the primary key of the current document library file shortcut
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next document library file shortcut
	 * @throws NoSuchFileShortcutException if a document library file shortcut with the primary key could not be found
	 */
	@Override
	public DLFileShortcut[] findByG_F_A_PrevAndNext(long fileShortcutId,
		long groupId, long folderId, boolean active,
		OrderByComparator<DLFileShortcut> orderByComparator)
		throws NoSuchFileShortcutException {
		DLFileShortcut dlFileShortcut = findByPrimaryKey(fileShortcutId);

		Session session = null;

		try {
			session = openSession();

			DLFileShortcut[] array = new DLFileShortcutImpl[3];

			array[0] = getByG_F_A_PrevAndNext(session, dlFileShortcut, groupId,
					folderId, active, orderByComparator, true);

			array[1] = dlFileShortcut;

			array[2] = getByG_F_A_PrevAndNext(session, dlFileShortcut, groupId,
					folderId, active, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected DLFileShortcut getByG_F_A_PrevAndNext(Session session,
		DLFileShortcut dlFileShortcut, long groupId, long folderId,
		boolean active, OrderByComparator<DLFileShortcut> orderByComparator,
		boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_DLFILESHORTCUT_WHERE);

		query.append(_FINDER_COLUMN_G_F_A_GROUPID_2);

		query.append(_FINDER_COLUMN_G_F_A_FOLDERID_2);

		query.append(_FINDER_COLUMN_G_F_A_ACTIVE_2);

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
			query.append(DLFileShortcutModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		qPos.add(folderId);

		qPos.add(active);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(dlFileShortcut);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<DLFileShortcut> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the document library file shortcuts that the user has permission to view where groupId = &#63; and folderId = &#63; and active = &#63;.
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param active the active
	 * @return the matching document library file shortcuts that the user has permission to view
	 */
	@Override
	public List<DLFileShortcut> filterFindByG_F_A(long groupId, long folderId,
		boolean active) {
		return filterFindByG_F_A(groupId, folderId, active, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the document library file shortcuts that the user has permission to view where groupId = &#63; and folderId = &#63; and active = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFileShortcutModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param active the active
	 * @param start the lower bound of the range of document library file shortcuts
	 * @param end the upper bound of the range of document library file shortcuts (not inclusive)
	 * @return the range of matching document library file shortcuts that the user has permission to view
	 */
	@Override
	public List<DLFileShortcut> filterFindByG_F_A(long groupId, long folderId,
		boolean active, int start, int end) {
		return filterFindByG_F_A(groupId, folderId, active, start, end, null);
	}

	/**
	 * Returns an ordered range of all the document library file shortcuts that the user has permissions to view where groupId = &#63; and folderId = &#63; and active = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFileShortcutModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param active the active
	 * @param start the lower bound of the range of document library file shortcuts
	 * @param end the upper bound of the range of document library file shortcuts (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching document library file shortcuts that the user has permission to view
	 */
	@Override
	public List<DLFileShortcut> filterFindByG_F_A(long groupId, long folderId,
		boolean active, int start, int end,
		OrderByComparator<DLFileShortcut> orderByComparator) {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_F_A(groupId, folderId, active, start, end,
				orderByComparator);
		}

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(5 +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(5);
		}

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_DLFILESHORTCUT_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_DLFILESHORTCUT_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_G_F_A_GROUPID_2);

		query.append(_FINDER_COLUMN_G_F_A_FOLDERID_2);

		query.append(_FINDER_COLUMN_G_F_A_ACTIVE_2_SQL);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_DLFILESHORTCUT_NO_INLINE_DISTINCT_WHERE_2);
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
				query.append(DLFileShortcutModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(DLFileShortcutModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				DLFileShortcut.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				q.addEntity(_FILTER_ENTITY_ALIAS, DLFileShortcutImpl.class);
			}
			else {
				q.addEntity(_FILTER_ENTITY_TABLE, DLFileShortcutImpl.class);
			}

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			qPos.add(folderId);

			qPos.add(active);

			return (List<DLFileShortcut>)QueryUtil.list(q, getDialect(), start,
				end);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Returns the document library file shortcuts before and after the current document library file shortcut in the ordered set of document library file shortcuts that the user has permission to view where groupId = &#63; and folderId = &#63; and active = &#63;.
	 *
	 * @param fileShortcutId the primary key of the current document library file shortcut
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next document library file shortcut
	 * @throws NoSuchFileShortcutException if a document library file shortcut with the primary key could not be found
	 */
	@Override
	public DLFileShortcut[] filterFindByG_F_A_PrevAndNext(long fileShortcutId,
		long groupId, long folderId, boolean active,
		OrderByComparator<DLFileShortcut> orderByComparator)
		throws NoSuchFileShortcutException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_F_A_PrevAndNext(fileShortcutId, groupId, folderId,
				active, orderByComparator);
		}

		DLFileShortcut dlFileShortcut = findByPrimaryKey(fileShortcutId);

		Session session = null;

		try {
			session = openSession();

			DLFileShortcut[] array = new DLFileShortcutImpl[3];

			array[0] = filterGetByG_F_A_PrevAndNext(session, dlFileShortcut,
					groupId, folderId, active, orderByComparator, true);

			array[1] = dlFileShortcut;

			array[2] = filterGetByG_F_A_PrevAndNext(session, dlFileShortcut,
					groupId, folderId, active, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected DLFileShortcut filterGetByG_F_A_PrevAndNext(Session session,
		DLFileShortcut dlFileShortcut, long groupId, long folderId,
		boolean active, OrderByComparator<DLFileShortcut> orderByComparator,
		boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_DLFILESHORTCUT_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_DLFILESHORTCUT_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_G_F_A_GROUPID_2);

		query.append(_FINDER_COLUMN_G_F_A_FOLDERID_2);

		query.append(_FINDER_COLUMN_G_F_A_ACTIVE_2_SQL);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_DLFILESHORTCUT_NO_INLINE_DISTINCT_WHERE_2);
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
				query.append(DLFileShortcutModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(DLFileShortcutModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				DLFileShortcut.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		SQLQuery q = session.createSynchronizedSQLQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		if (getDB().isSupportsInlineDistinct()) {
			q.addEntity(_FILTER_ENTITY_ALIAS, DLFileShortcutImpl.class);
		}
		else {
			q.addEntity(_FILTER_ENTITY_TABLE, DLFileShortcutImpl.class);
		}

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		qPos.add(folderId);

		qPos.add(active);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(dlFileShortcut);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<DLFileShortcut> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the document library file shortcuts where groupId = &#63; and folderId = &#63; and active = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param active the active
	 */
	@Override
	public void removeByG_F_A(long groupId, long folderId, boolean active) {
		for (DLFileShortcut dlFileShortcut : findByG_F_A(groupId, folderId,
				active, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(dlFileShortcut);
		}
	}

	/**
	 * Returns the number of document library file shortcuts where groupId = &#63; and folderId = &#63; and active = &#63;.
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param active the active
	 * @return the number of matching document library file shortcuts
	 */
	@Override
	public int countByG_F_A(long groupId, long folderId, boolean active) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_G_F_A;

		Object[] finderArgs = new Object[] { groupId, folderId, active };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_COUNT_DLFILESHORTCUT_WHERE);

			query.append(_FINDER_COLUMN_G_F_A_GROUPID_2);

			query.append(_FINDER_COLUMN_G_F_A_FOLDERID_2);

			query.append(_FINDER_COLUMN_G_F_A_ACTIVE_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(folderId);

				qPos.add(active);

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
	 * Returns the number of document library file shortcuts that the user has permission to view where groupId = &#63; and folderId = &#63; and active = &#63;.
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param active the active
	 * @return the number of matching document library file shortcuts that the user has permission to view
	 */
	@Override
	public int filterCountByG_F_A(long groupId, long folderId, boolean active) {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByG_F_A(groupId, folderId, active);
		}

		StringBundler query = new StringBundler(4);

		query.append(_FILTER_SQL_COUNT_DLFILESHORTCUT_WHERE);

		query.append(_FINDER_COLUMN_G_F_A_GROUPID_2);

		query.append(_FINDER_COLUMN_G_F_A_FOLDERID_2);

		query.append(_FINDER_COLUMN_G_F_A_ACTIVE_2_SQL);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				DLFileShortcut.class.getName(),
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

			qPos.add(active);

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

	private static final String _FINDER_COLUMN_G_F_A_GROUPID_2 = "dlFileShortcut.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_F_A_FOLDERID_2 = "dlFileShortcut.folderId = ? AND ";
	private static final String _FINDER_COLUMN_G_F_A_ACTIVE_2 = "dlFileShortcut.active = ?";
	private static final String _FINDER_COLUMN_G_F_A_ACTIVE_2_SQL = "dlFileShortcut.active_ = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_G_F_A_S = new FinderPath(DLFileShortcutModelImpl.ENTITY_CACHE_ENABLED,
			DLFileShortcutModelImpl.FINDER_CACHE_ENABLED,
			DLFileShortcutImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByG_F_A_S",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Boolean.class.getName(), Integer.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_F_A_S =
		new FinderPath(DLFileShortcutModelImpl.ENTITY_CACHE_ENABLED,
			DLFileShortcutModelImpl.FINDER_CACHE_ENABLED,
			DLFileShortcutImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByG_F_A_S",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Boolean.class.getName(), Integer.class.getName()
			},
			DLFileShortcutModelImpl.GROUPID_COLUMN_BITMASK |
			DLFileShortcutModelImpl.FOLDERID_COLUMN_BITMASK |
			DLFileShortcutModelImpl.ACTIVE_COLUMN_BITMASK |
			DLFileShortcutModelImpl.STATUS_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_G_F_A_S = new FinderPath(DLFileShortcutModelImpl.ENTITY_CACHE_ENABLED,
			DLFileShortcutModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_F_A_S",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Boolean.class.getName(), Integer.class.getName()
			});

	/**
	 * Returns all the document library file shortcuts where groupId = &#63; and folderId = &#63; and active = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param active the active
	 * @param status the status
	 * @return the matching document library file shortcuts
	 */
	@Override
	public List<DLFileShortcut> findByG_F_A_S(long groupId, long folderId,
		boolean active, int status) {
		return findByG_F_A_S(groupId, folderId, active, status,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the document library file shortcuts where groupId = &#63; and folderId = &#63; and active = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFileShortcutModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param active the active
	 * @param status the status
	 * @param start the lower bound of the range of document library file shortcuts
	 * @param end the upper bound of the range of document library file shortcuts (not inclusive)
	 * @return the range of matching document library file shortcuts
	 */
	@Override
	public List<DLFileShortcut> findByG_F_A_S(long groupId, long folderId,
		boolean active, int status, int start, int end) {
		return findByG_F_A_S(groupId, folderId, active, status, start, end, null);
	}

	/**
	 * Returns an ordered range of all the document library file shortcuts where groupId = &#63; and folderId = &#63; and active = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFileShortcutModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param active the active
	 * @param status the status
	 * @param start the lower bound of the range of document library file shortcuts
	 * @param end the upper bound of the range of document library file shortcuts (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching document library file shortcuts
	 */
	@Override
	public List<DLFileShortcut> findByG_F_A_S(long groupId, long folderId,
		boolean active, int status, int start, int end,
		OrderByComparator<DLFileShortcut> orderByComparator) {
		return findByG_F_A_S(groupId, folderId, active, status, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the document library file shortcuts where groupId = &#63; and folderId = &#63; and active = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFileShortcutModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param active the active
	 * @param status the status
	 * @param start the lower bound of the range of document library file shortcuts
	 * @param end the upper bound of the range of document library file shortcuts (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching document library file shortcuts
	 */
	@Override
	public List<DLFileShortcut> findByG_F_A_S(long groupId, long folderId,
		boolean active, int status, int start, int end,
		OrderByComparator<DLFileShortcut> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_F_A_S;
			finderArgs = new Object[] { groupId, folderId, active, status };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_G_F_A_S;
			finderArgs = new Object[] {
					groupId, folderId, active, status,
					
					start, end, orderByComparator
				};
		}

		List<DLFileShortcut> list = null;

		if (retrieveFromCache) {
			list = (List<DLFileShortcut>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (DLFileShortcut dlFileShortcut : list) {
					if ((groupId != dlFileShortcut.getGroupId()) ||
							(folderId != dlFileShortcut.getFolderId()) ||
							(active != dlFileShortcut.getActive()) ||
							(status != dlFileShortcut.getStatus())) {
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
						(orderByComparator.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(6);
			}

			query.append(_SQL_SELECT_DLFILESHORTCUT_WHERE);

			query.append(_FINDER_COLUMN_G_F_A_S_GROUPID_2);

			query.append(_FINDER_COLUMN_G_F_A_S_FOLDERID_2);

			query.append(_FINDER_COLUMN_G_F_A_S_ACTIVE_2);

			query.append(_FINDER_COLUMN_G_F_A_S_STATUS_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(DLFileShortcutModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(folderId);

				qPos.add(active);

				qPos.add(status);

				if (!pagination) {
					list = (List<DLFileShortcut>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<DLFileShortcut>)QueryUtil.list(q,
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
	 * Returns the first document library file shortcut in the ordered set where groupId = &#63; and folderId = &#63; and active = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param active the active
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching document library file shortcut
	 * @throws NoSuchFileShortcutException if a matching document library file shortcut could not be found
	 */
	@Override
	public DLFileShortcut findByG_F_A_S_First(long groupId, long folderId,
		boolean active, int status,
		OrderByComparator<DLFileShortcut> orderByComparator)
		throws NoSuchFileShortcutException {
		DLFileShortcut dlFileShortcut = fetchByG_F_A_S_First(groupId, folderId,
				active, status, orderByComparator);

		if (dlFileShortcut != null) {
			return dlFileShortcut;
		}

		StringBundler msg = new StringBundler(10);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", folderId=");
		msg.append(folderId);

		msg.append(", active=");
		msg.append(active);

		msg.append(", status=");
		msg.append(status);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchFileShortcutException(msg.toString());
	}

	/**
	 * Returns the first document library file shortcut in the ordered set where groupId = &#63; and folderId = &#63; and active = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param active the active
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching document library file shortcut, or <code>null</code> if a matching document library file shortcut could not be found
	 */
	@Override
	public DLFileShortcut fetchByG_F_A_S_First(long groupId, long folderId,
		boolean active, int status,
		OrderByComparator<DLFileShortcut> orderByComparator) {
		List<DLFileShortcut> list = findByG_F_A_S(groupId, folderId, active,
				status, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last document library file shortcut in the ordered set where groupId = &#63; and folderId = &#63; and active = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param active the active
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching document library file shortcut
	 * @throws NoSuchFileShortcutException if a matching document library file shortcut could not be found
	 */
	@Override
	public DLFileShortcut findByG_F_A_S_Last(long groupId, long folderId,
		boolean active, int status,
		OrderByComparator<DLFileShortcut> orderByComparator)
		throws NoSuchFileShortcutException {
		DLFileShortcut dlFileShortcut = fetchByG_F_A_S_Last(groupId, folderId,
				active, status, orderByComparator);

		if (dlFileShortcut != null) {
			return dlFileShortcut;
		}

		StringBundler msg = new StringBundler(10);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", folderId=");
		msg.append(folderId);

		msg.append(", active=");
		msg.append(active);

		msg.append(", status=");
		msg.append(status);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchFileShortcutException(msg.toString());
	}

	/**
	 * Returns the last document library file shortcut in the ordered set where groupId = &#63; and folderId = &#63; and active = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param active the active
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching document library file shortcut, or <code>null</code> if a matching document library file shortcut could not be found
	 */
	@Override
	public DLFileShortcut fetchByG_F_A_S_Last(long groupId, long folderId,
		boolean active, int status,
		OrderByComparator<DLFileShortcut> orderByComparator) {
		int count = countByG_F_A_S(groupId, folderId, active, status);

		if (count == 0) {
			return null;
		}

		List<DLFileShortcut> list = findByG_F_A_S(groupId, folderId, active,
				status, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the document library file shortcuts before and after the current document library file shortcut in the ordered set where groupId = &#63; and folderId = &#63; and active = &#63; and status = &#63;.
	 *
	 * @param fileShortcutId the primary key of the current document library file shortcut
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param active the active
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next document library file shortcut
	 * @throws NoSuchFileShortcutException if a document library file shortcut with the primary key could not be found
	 */
	@Override
	public DLFileShortcut[] findByG_F_A_S_PrevAndNext(long fileShortcutId,
		long groupId, long folderId, boolean active, int status,
		OrderByComparator<DLFileShortcut> orderByComparator)
		throws NoSuchFileShortcutException {
		DLFileShortcut dlFileShortcut = findByPrimaryKey(fileShortcutId);

		Session session = null;

		try {
			session = openSession();

			DLFileShortcut[] array = new DLFileShortcutImpl[3];

			array[0] = getByG_F_A_S_PrevAndNext(session, dlFileShortcut,
					groupId, folderId, active, status, orderByComparator, true);

			array[1] = dlFileShortcut;

			array[2] = getByG_F_A_S_PrevAndNext(session, dlFileShortcut,
					groupId, folderId, active, status, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected DLFileShortcut getByG_F_A_S_PrevAndNext(Session session,
		DLFileShortcut dlFileShortcut, long groupId, long folderId,
		boolean active, int status,
		OrderByComparator<DLFileShortcut> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_DLFILESHORTCUT_WHERE);

		query.append(_FINDER_COLUMN_G_F_A_S_GROUPID_2);

		query.append(_FINDER_COLUMN_G_F_A_S_FOLDERID_2);

		query.append(_FINDER_COLUMN_G_F_A_S_ACTIVE_2);

		query.append(_FINDER_COLUMN_G_F_A_S_STATUS_2);

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
			query.append(DLFileShortcutModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		qPos.add(folderId);

		qPos.add(active);

		qPos.add(status);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(dlFileShortcut);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<DLFileShortcut> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the document library file shortcuts that the user has permission to view where groupId = &#63; and folderId = &#63; and active = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param active the active
	 * @param status the status
	 * @return the matching document library file shortcuts that the user has permission to view
	 */
	@Override
	public List<DLFileShortcut> filterFindByG_F_A_S(long groupId,
		long folderId, boolean active, int status) {
		return filterFindByG_F_A_S(groupId, folderId, active, status,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the document library file shortcuts that the user has permission to view where groupId = &#63; and folderId = &#63; and active = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFileShortcutModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param active the active
	 * @param status the status
	 * @param start the lower bound of the range of document library file shortcuts
	 * @param end the upper bound of the range of document library file shortcuts (not inclusive)
	 * @return the range of matching document library file shortcuts that the user has permission to view
	 */
	@Override
	public List<DLFileShortcut> filterFindByG_F_A_S(long groupId,
		long folderId, boolean active, int status, int start, int end) {
		return filterFindByG_F_A_S(groupId, folderId, active, status, start,
			end, null);
	}

	/**
	 * Returns an ordered range of all the document library file shortcuts that the user has permissions to view where groupId = &#63; and folderId = &#63; and active = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFileShortcutModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param active the active
	 * @param status the status
	 * @param start the lower bound of the range of document library file shortcuts
	 * @param end the upper bound of the range of document library file shortcuts (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching document library file shortcuts that the user has permission to view
	 */
	@Override
	public List<DLFileShortcut> filterFindByG_F_A_S(long groupId,
		long folderId, boolean active, int status, int start, int end,
		OrderByComparator<DLFileShortcut> orderByComparator) {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_F_A_S(groupId, folderId, active, status, start, end,
				orderByComparator);
		}

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(6);
		}

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_DLFILESHORTCUT_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_DLFILESHORTCUT_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_G_F_A_S_GROUPID_2);

		query.append(_FINDER_COLUMN_G_F_A_S_FOLDERID_2);

		query.append(_FINDER_COLUMN_G_F_A_S_ACTIVE_2_SQL);

		query.append(_FINDER_COLUMN_G_F_A_S_STATUS_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_DLFILESHORTCUT_NO_INLINE_DISTINCT_WHERE_2);
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
				query.append(DLFileShortcutModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(DLFileShortcutModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				DLFileShortcut.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				q.addEntity(_FILTER_ENTITY_ALIAS, DLFileShortcutImpl.class);
			}
			else {
				q.addEntity(_FILTER_ENTITY_TABLE, DLFileShortcutImpl.class);
			}

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			qPos.add(folderId);

			qPos.add(active);

			qPos.add(status);

			return (List<DLFileShortcut>)QueryUtil.list(q, getDialect(), start,
				end);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Returns the document library file shortcuts before and after the current document library file shortcut in the ordered set of document library file shortcuts that the user has permission to view where groupId = &#63; and folderId = &#63; and active = &#63; and status = &#63;.
	 *
	 * @param fileShortcutId the primary key of the current document library file shortcut
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param active the active
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next document library file shortcut
	 * @throws NoSuchFileShortcutException if a document library file shortcut with the primary key could not be found
	 */
	@Override
	public DLFileShortcut[] filterFindByG_F_A_S_PrevAndNext(
		long fileShortcutId, long groupId, long folderId, boolean active,
		int status, OrderByComparator<DLFileShortcut> orderByComparator)
		throws NoSuchFileShortcutException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_F_A_S_PrevAndNext(fileShortcutId, groupId, folderId,
				active, status, orderByComparator);
		}

		DLFileShortcut dlFileShortcut = findByPrimaryKey(fileShortcutId);

		Session session = null;

		try {
			session = openSession();

			DLFileShortcut[] array = new DLFileShortcutImpl[3];

			array[0] = filterGetByG_F_A_S_PrevAndNext(session, dlFileShortcut,
					groupId, folderId, active, status, orderByComparator, true);

			array[1] = dlFileShortcut;

			array[2] = filterGetByG_F_A_S_PrevAndNext(session, dlFileShortcut,
					groupId, folderId, active, status, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected DLFileShortcut filterGetByG_F_A_S_PrevAndNext(Session session,
		DLFileShortcut dlFileShortcut, long groupId, long folderId,
		boolean active, int status,
		OrderByComparator<DLFileShortcut> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_DLFILESHORTCUT_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_DLFILESHORTCUT_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_G_F_A_S_GROUPID_2);

		query.append(_FINDER_COLUMN_G_F_A_S_FOLDERID_2);

		query.append(_FINDER_COLUMN_G_F_A_S_ACTIVE_2_SQL);

		query.append(_FINDER_COLUMN_G_F_A_S_STATUS_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_DLFILESHORTCUT_NO_INLINE_DISTINCT_WHERE_2);
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
				query.append(DLFileShortcutModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(DLFileShortcutModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				DLFileShortcut.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		SQLQuery q = session.createSynchronizedSQLQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		if (getDB().isSupportsInlineDistinct()) {
			q.addEntity(_FILTER_ENTITY_ALIAS, DLFileShortcutImpl.class);
		}
		else {
			q.addEntity(_FILTER_ENTITY_TABLE, DLFileShortcutImpl.class);
		}

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		qPos.add(folderId);

		qPos.add(active);

		qPos.add(status);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(dlFileShortcut);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<DLFileShortcut> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the document library file shortcuts where groupId = &#63; and folderId = &#63; and active = &#63; and status = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param active the active
	 * @param status the status
	 */
	@Override
	public void removeByG_F_A_S(long groupId, long folderId, boolean active,
		int status) {
		for (DLFileShortcut dlFileShortcut : findByG_F_A_S(groupId, folderId,
				active, status, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(dlFileShortcut);
		}
	}

	/**
	 * Returns the number of document library file shortcuts where groupId = &#63; and folderId = &#63; and active = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param active the active
	 * @param status the status
	 * @return the number of matching document library file shortcuts
	 */
	@Override
	public int countByG_F_A_S(long groupId, long folderId, boolean active,
		int status) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_G_F_A_S;

		Object[] finderArgs = new Object[] { groupId, folderId, active, status };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(5);

			query.append(_SQL_COUNT_DLFILESHORTCUT_WHERE);

			query.append(_FINDER_COLUMN_G_F_A_S_GROUPID_2);

			query.append(_FINDER_COLUMN_G_F_A_S_FOLDERID_2);

			query.append(_FINDER_COLUMN_G_F_A_S_ACTIVE_2);

			query.append(_FINDER_COLUMN_G_F_A_S_STATUS_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(folderId);

				qPos.add(active);

				qPos.add(status);

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
	 * Returns the number of document library file shortcuts that the user has permission to view where groupId = &#63; and folderId = &#63; and active = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param folderId the folder ID
	 * @param active the active
	 * @param status the status
	 * @return the number of matching document library file shortcuts that the user has permission to view
	 */
	@Override
	public int filterCountByG_F_A_S(long groupId, long folderId,
		boolean active, int status) {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByG_F_A_S(groupId, folderId, active, status);
		}

		StringBundler query = new StringBundler(5);

		query.append(_FILTER_SQL_COUNT_DLFILESHORTCUT_WHERE);

		query.append(_FINDER_COLUMN_G_F_A_S_GROUPID_2);

		query.append(_FINDER_COLUMN_G_F_A_S_FOLDERID_2);

		query.append(_FINDER_COLUMN_G_F_A_S_ACTIVE_2_SQL);

		query.append(_FINDER_COLUMN_G_F_A_S_STATUS_2);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				DLFileShortcut.class.getName(),
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

			qPos.add(active);

			qPos.add(status);

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

	private static final String _FINDER_COLUMN_G_F_A_S_GROUPID_2 = "dlFileShortcut.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_F_A_S_FOLDERID_2 = "dlFileShortcut.folderId = ? AND ";
	private static final String _FINDER_COLUMN_G_F_A_S_ACTIVE_2 = "dlFileShortcut.active = ? AND ";
	private static final String _FINDER_COLUMN_G_F_A_S_ACTIVE_2_SQL = "dlFileShortcut.active_ = ? AND ";
	private static final String _FINDER_COLUMN_G_F_A_S_STATUS_2 = "dlFileShortcut.status = ?";

	public DLFileShortcutPersistenceImpl() {
		setModelClass(DLFileShortcut.class);
	}

	/**
	 * Caches the document library file shortcut in the entity cache if it is enabled.
	 *
	 * @param dlFileShortcut the document library file shortcut
	 */
	@Override
	public void cacheResult(DLFileShortcut dlFileShortcut) {
		entityCache.putResult(DLFileShortcutModelImpl.ENTITY_CACHE_ENABLED,
			DLFileShortcutImpl.class, dlFileShortcut.getPrimaryKey(),
			dlFileShortcut);

		finderCache.putResult(FINDER_PATH_FETCH_BY_UUID_G,
			new Object[] { dlFileShortcut.getUuid(), dlFileShortcut.getGroupId() },
			dlFileShortcut);

		dlFileShortcut.resetOriginalValues();
	}

	/**
	 * Caches the document library file shortcuts in the entity cache if it is enabled.
	 *
	 * @param dlFileShortcuts the document library file shortcuts
	 */
	@Override
	public void cacheResult(List<DLFileShortcut> dlFileShortcuts) {
		for (DLFileShortcut dlFileShortcut : dlFileShortcuts) {
			if (entityCache.getResult(
						DLFileShortcutModelImpl.ENTITY_CACHE_ENABLED,
						DLFileShortcutImpl.class, dlFileShortcut.getPrimaryKey()) == null) {
				cacheResult(dlFileShortcut);
			}
			else {
				dlFileShortcut.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all document library file shortcuts.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(DLFileShortcutImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the document library file shortcut.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(DLFileShortcut dlFileShortcut) {
		entityCache.removeResult(DLFileShortcutModelImpl.ENTITY_CACHE_ENABLED,
			DLFileShortcutImpl.class, dlFileShortcut.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache((DLFileShortcutModelImpl)dlFileShortcut);
	}

	@Override
	public void clearCache(List<DLFileShortcut> dlFileShortcuts) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (DLFileShortcut dlFileShortcut : dlFileShortcuts) {
			entityCache.removeResult(DLFileShortcutModelImpl.ENTITY_CACHE_ENABLED,
				DLFileShortcutImpl.class, dlFileShortcut.getPrimaryKey());

			clearUniqueFindersCache((DLFileShortcutModelImpl)dlFileShortcut);
		}
	}

	protected void cacheUniqueFindersCache(
		DLFileShortcutModelImpl dlFileShortcutModelImpl, boolean isNew) {
		if (isNew) {
			Object[] args = new Object[] {
					dlFileShortcutModelImpl.getUuid(),
					dlFileShortcutModelImpl.getGroupId()
				};

			finderCache.putResult(FINDER_PATH_COUNT_BY_UUID_G, args,
				Long.valueOf(1));
			finderCache.putResult(FINDER_PATH_FETCH_BY_UUID_G, args,
				dlFileShortcutModelImpl);
		}
		else {
			if ((dlFileShortcutModelImpl.getColumnBitmask() &
					FINDER_PATH_FETCH_BY_UUID_G.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						dlFileShortcutModelImpl.getUuid(),
						dlFileShortcutModelImpl.getGroupId()
					};

				finderCache.putResult(FINDER_PATH_COUNT_BY_UUID_G, args,
					Long.valueOf(1));
				finderCache.putResult(FINDER_PATH_FETCH_BY_UUID_G, args,
					dlFileShortcutModelImpl);
			}
		}
	}

	protected void clearUniqueFindersCache(
		DLFileShortcutModelImpl dlFileShortcutModelImpl) {
		Object[] args = new Object[] {
				dlFileShortcutModelImpl.getUuid(),
				dlFileShortcutModelImpl.getGroupId()
			};

		finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID_G, args);
		finderCache.removeResult(FINDER_PATH_FETCH_BY_UUID_G, args);

		if ((dlFileShortcutModelImpl.getColumnBitmask() &
				FINDER_PATH_FETCH_BY_UUID_G.getColumnBitmask()) != 0) {
			args = new Object[] {
					dlFileShortcutModelImpl.getOriginalUuid(),
					dlFileShortcutModelImpl.getOriginalGroupId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID_G, args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_UUID_G, args);
		}
	}

	/**
	 * Creates a new document library file shortcut with the primary key. Does not add the document library file shortcut to the database.
	 *
	 * @param fileShortcutId the primary key for the new document library file shortcut
	 * @return the new document library file shortcut
	 */
	@Override
	public DLFileShortcut create(long fileShortcutId) {
		DLFileShortcut dlFileShortcut = new DLFileShortcutImpl();

		dlFileShortcut.setNew(true);
		dlFileShortcut.setPrimaryKey(fileShortcutId);

		String uuid = PortalUUIDUtil.generate();

		dlFileShortcut.setUuid(uuid);

		return dlFileShortcut;
	}

	/**
	 * Removes the document library file shortcut with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param fileShortcutId the primary key of the document library file shortcut
	 * @return the document library file shortcut that was removed
	 * @throws NoSuchFileShortcutException if a document library file shortcut with the primary key could not be found
	 */
	@Override
	public DLFileShortcut remove(long fileShortcutId)
		throws NoSuchFileShortcutException {
		return remove((Serializable)fileShortcutId);
	}

	/**
	 * Removes the document library file shortcut with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the document library file shortcut
	 * @return the document library file shortcut that was removed
	 * @throws NoSuchFileShortcutException if a document library file shortcut with the primary key could not be found
	 */
	@Override
	public DLFileShortcut remove(Serializable primaryKey)
		throws NoSuchFileShortcutException {
		Session session = null;

		try {
			session = openSession();

			DLFileShortcut dlFileShortcut = (DLFileShortcut)session.get(DLFileShortcutImpl.class,
					primaryKey);

			if (dlFileShortcut == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchFileShortcutException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(dlFileShortcut);
		}
		catch (NoSuchFileShortcutException nsee) {
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
	protected DLFileShortcut removeImpl(DLFileShortcut dlFileShortcut) {
		dlFileShortcut = toUnwrappedModel(dlFileShortcut);

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(dlFileShortcut)) {
				dlFileShortcut = (DLFileShortcut)session.get(DLFileShortcutImpl.class,
						dlFileShortcut.getPrimaryKeyObj());
			}

			if (dlFileShortcut != null) {
				session.delete(dlFileShortcut);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (dlFileShortcut != null) {
			clearCache(dlFileShortcut);
		}

		return dlFileShortcut;
	}

	@Override
	public DLFileShortcut updateImpl(DLFileShortcut dlFileShortcut) {
		dlFileShortcut = toUnwrappedModel(dlFileShortcut);

		boolean isNew = dlFileShortcut.isNew();

		DLFileShortcutModelImpl dlFileShortcutModelImpl = (DLFileShortcutModelImpl)dlFileShortcut;

		if (Validator.isNull(dlFileShortcut.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			dlFileShortcut.setUuid(uuid);
		}

		ServiceContext serviceContext = ServiceContextThreadLocal.getServiceContext();

		Date now = new Date();

		if (isNew && (dlFileShortcut.getCreateDate() == null)) {
			if (serviceContext == null) {
				dlFileShortcut.setCreateDate(now);
			}
			else {
				dlFileShortcut.setCreateDate(serviceContext.getCreateDate(now));
			}
		}

		if (!dlFileShortcutModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				dlFileShortcut.setModifiedDate(now);
			}
			else {
				dlFileShortcut.setModifiedDate(serviceContext.getModifiedDate(
						now));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (dlFileShortcut.isNew()) {
				session.save(dlFileShortcut);

				dlFileShortcut.setNew(false);
			}
			else {
				dlFileShortcut = (DLFileShortcut)session.merge(dlFileShortcut);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (isNew || !DLFileShortcutModelImpl.COLUMN_BITMASK_ENABLED) {
			finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}

		else {
			if ((dlFileShortcutModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						dlFileShortcutModelImpl.getOriginalUuid()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID,
					args);

				args = new Object[] { dlFileShortcutModelImpl.getUuid() };

				finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID,
					args);
			}

			if ((dlFileShortcutModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						dlFileShortcutModelImpl.getOriginalUuid(),
						dlFileShortcutModelImpl.getOriginalCompanyId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID_C, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C,
					args);

				args = new Object[] {
						dlFileShortcutModelImpl.getUuid(),
						dlFileShortcutModelImpl.getCompanyId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID_C, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C,
					args);
			}

			if ((dlFileShortcutModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						dlFileShortcutModelImpl.getOriginalCompanyId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_COMPANYID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID,
					args);

				args = new Object[] { dlFileShortcutModelImpl.getCompanyId() };

				finderCache.removeResult(FINDER_PATH_COUNT_BY_COMPANYID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID,
					args);
			}

			if ((dlFileShortcutModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_TOFILEENTRYID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						dlFileShortcutModelImpl.getOriginalToFileEntryId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_TOFILEENTRYID,
					args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_TOFILEENTRYID,
					args);

				args = new Object[] { dlFileShortcutModelImpl.getToFileEntryId() };

				finderCache.removeResult(FINDER_PATH_COUNT_BY_TOFILEENTRYID,
					args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_TOFILEENTRYID,
					args);
			}

			if ((dlFileShortcutModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_F.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						dlFileShortcutModelImpl.getOriginalGroupId(),
						dlFileShortcutModelImpl.getOriginalFolderId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_G_F, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_F,
					args);

				args = new Object[] {
						dlFileShortcutModelImpl.getGroupId(),
						dlFileShortcutModelImpl.getFolderId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_G_F, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_F,
					args);
			}

			if ((dlFileShortcutModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_F_A.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						dlFileShortcutModelImpl.getOriginalGroupId(),
						dlFileShortcutModelImpl.getOriginalFolderId(),
						dlFileShortcutModelImpl.getOriginalActive()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_G_F_A, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_F_A,
					args);

				args = new Object[] {
						dlFileShortcutModelImpl.getGroupId(),
						dlFileShortcutModelImpl.getFolderId(),
						dlFileShortcutModelImpl.getActive()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_G_F_A, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_F_A,
					args);
			}

			if ((dlFileShortcutModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_F_A_S.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						dlFileShortcutModelImpl.getOriginalGroupId(),
						dlFileShortcutModelImpl.getOriginalFolderId(),
						dlFileShortcutModelImpl.getOriginalActive(),
						dlFileShortcutModelImpl.getOriginalStatus()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_G_F_A_S, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_F_A_S,
					args);

				args = new Object[] {
						dlFileShortcutModelImpl.getGroupId(),
						dlFileShortcutModelImpl.getFolderId(),
						dlFileShortcutModelImpl.getActive(),
						dlFileShortcutModelImpl.getStatus()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_G_F_A_S, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_F_A_S,
					args);
			}
		}

		entityCache.putResult(DLFileShortcutModelImpl.ENTITY_CACHE_ENABLED,
			DLFileShortcutImpl.class, dlFileShortcut.getPrimaryKey(),
			dlFileShortcut, false);

		clearUniqueFindersCache(dlFileShortcutModelImpl);
		cacheUniqueFindersCache(dlFileShortcutModelImpl, isNew);

		dlFileShortcut.resetOriginalValues();

		return dlFileShortcut;
	}

	protected DLFileShortcut toUnwrappedModel(DLFileShortcut dlFileShortcut) {
		if (dlFileShortcut instanceof DLFileShortcutImpl) {
			return dlFileShortcut;
		}

		DLFileShortcutImpl dlFileShortcutImpl = new DLFileShortcutImpl();

		dlFileShortcutImpl.setNew(dlFileShortcut.isNew());
		dlFileShortcutImpl.setPrimaryKey(dlFileShortcut.getPrimaryKey());

		dlFileShortcutImpl.setUuid(dlFileShortcut.getUuid());
		dlFileShortcutImpl.setFileShortcutId(dlFileShortcut.getFileShortcutId());
		dlFileShortcutImpl.setGroupId(dlFileShortcut.getGroupId());
		dlFileShortcutImpl.setCompanyId(dlFileShortcut.getCompanyId());
		dlFileShortcutImpl.setUserId(dlFileShortcut.getUserId());
		dlFileShortcutImpl.setUserName(dlFileShortcut.getUserName());
		dlFileShortcutImpl.setCreateDate(dlFileShortcut.getCreateDate());
		dlFileShortcutImpl.setModifiedDate(dlFileShortcut.getModifiedDate());
		dlFileShortcutImpl.setRepositoryId(dlFileShortcut.getRepositoryId());
		dlFileShortcutImpl.setFolderId(dlFileShortcut.getFolderId());
		dlFileShortcutImpl.setToFileEntryId(dlFileShortcut.getToFileEntryId());
		dlFileShortcutImpl.setTreePath(dlFileShortcut.getTreePath());
		dlFileShortcutImpl.setActive(dlFileShortcut.isActive());
		dlFileShortcutImpl.setLastPublishDate(dlFileShortcut.getLastPublishDate());
		dlFileShortcutImpl.setStatus(dlFileShortcut.getStatus());
		dlFileShortcutImpl.setStatusByUserId(dlFileShortcut.getStatusByUserId());
		dlFileShortcutImpl.setStatusByUserName(dlFileShortcut.getStatusByUserName());
		dlFileShortcutImpl.setStatusDate(dlFileShortcut.getStatusDate());

		return dlFileShortcutImpl;
	}

	/**
	 * Returns the document library file shortcut with the primary key or throws a {@link com.liferay.portal.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the document library file shortcut
	 * @return the document library file shortcut
	 * @throws NoSuchFileShortcutException if a document library file shortcut with the primary key could not be found
	 */
	@Override
	public DLFileShortcut findByPrimaryKey(Serializable primaryKey)
		throws NoSuchFileShortcutException {
		DLFileShortcut dlFileShortcut = fetchByPrimaryKey(primaryKey);

		if (dlFileShortcut == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchFileShortcutException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				primaryKey);
		}

		return dlFileShortcut;
	}

	/**
	 * Returns the document library file shortcut with the primary key or throws a {@link NoSuchFileShortcutException} if it could not be found.
	 *
	 * @param fileShortcutId the primary key of the document library file shortcut
	 * @return the document library file shortcut
	 * @throws NoSuchFileShortcutException if a document library file shortcut with the primary key could not be found
	 */
	@Override
	public DLFileShortcut findByPrimaryKey(long fileShortcutId)
		throws NoSuchFileShortcutException {
		return findByPrimaryKey((Serializable)fileShortcutId);
	}

	/**
	 * Returns the document library file shortcut with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the document library file shortcut
	 * @return the document library file shortcut, or <code>null</code> if a document library file shortcut with the primary key could not be found
	 */
	@Override
	public DLFileShortcut fetchByPrimaryKey(Serializable primaryKey) {
		DLFileShortcut dlFileShortcut = (DLFileShortcut)entityCache.getResult(DLFileShortcutModelImpl.ENTITY_CACHE_ENABLED,
				DLFileShortcutImpl.class, primaryKey);

		if (dlFileShortcut == _nullDLFileShortcut) {
			return null;
		}

		if (dlFileShortcut == null) {
			Session session = null;

			try {
				session = openSession();

				dlFileShortcut = (DLFileShortcut)session.get(DLFileShortcutImpl.class,
						primaryKey);

				if (dlFileShortcut != null) {
					cacheResult(dlFileShortcut);
				}
				else {
					entityCache.putResult(DLFileShortcutModelImpl.ENTITY_CACHE_ENABLED,
						DLFileShortcutImpl.class, primaryKey,
						_nullDLFileShortcut);
				}
			}
			catch (Exception e) {
				entityCache.removeResult(DLFileShortcutModelImpl.ENTITY_CACHE_ENABLED,
					DLFileShortcutImpl.class, primaryKey);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return dlFileShortcut;
	}

	/**
	 * Returns the document library file shortcut with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param fileShortcutId the primary key of the document library file shortcut
	 * @return the document library file shortcut, or <code>null</code> if a document library file shortcut with the primary key could not be found
	 */
	@Override
	public DLFileShortcut fetchByPrimaryKey(long fileShortcutId) {
		return fetchByPrimaryKey((Serializable)fileShortcutId);
	}

	@Override
	public Map<Serializable, DLFileShortcut> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {
		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, DLFileShortcut> map = new HashMap<Serializable, DLFileShortcut>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			DLFileShortcut dlFileShortcut = fetchByPrimaryKey(primaryKey);

			if (dlFileShortcut != null) {
				map.put(primaryKey, dlFileShortcut);
			}

			return map;
		}

		Set<Serializable> uncachedPrimaryKeys = null;

		for (Serializable primaryKey : primaryKeys) {
			DLFileShortcut dlFileShortcut = (DLFileShortcut)entityCache.getResult(DLFileShortcutModelImpl.ENTITY_CACHE_ENABLED,
					DLFileShortcutImpl.class, primaryKey);

			if (dlFileShortcut == null) {
				if (uncachedPrimaryKeys == null) {
					uncachedPrimaryKeys = new HashSet<Serializable>();
				}

				uncachedPrimaryKeys.add(primaryKey);
			}
			else {
				map.put(primaryKey, dlFileShortcut);
			}
		}

		if (uncachedPrimaryKeys == null) {
			return map;
		}

		StringBundler query = new StringBundler((uncachedPrimaryKeys.size() * 2) +
				1);

		query.append(_SQL_SELECT_DLFILESHORTCUT_WHERE_PKS_IN);

		for (Serializable primaryKey : uncachedPrimaryKeys) {
			query.append(String.valueOf(primaryKey));

			query.append(StringPool.COMMA);
		}

		query.setIndex(query.index() - 1);

		query.append(StringPool.CLOSE_PARENTHESIS);

		String sql = query.toString();

		Session session = null;

		try {
			session = openSession();

			Query q = session.createQuery(sql);

			for (DLFileShortcut dlFileShortcut : (List<DLFileShortcut>)q.list()) {
				map.put(dlFileShortcut.getPrimaryKeyObj(), dlFileShortcut);

				cacheResult(dlFileShortcut);

				uncachedPrimaryKeys.remove(dlFileShortcut.getPrimaryKeyObj());
			}

			for (Serializable primaryKey : uncachedPrimaryKeys) {
				entityCache.putResult(DLFileShortcutModelImpl.ENTITY_CACHE_ENABLED,
					DLFileShortcutImpl.class, primaryKey, _nullDLFileShortcut);
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
	 * Returns all the document library file shortcuts.
	 *
	 * @return the document library file shortcuts
	 */
	@Override
	public List<DLFileShortcut> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the document library file shortcuts.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFileShortcutModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of document library file shortcuts
	 * @param end the upper bound of the range of document library file shortcuts (not inclusive)
	 * @return the range of document library file shortcuts
	 */
	@Override
	public List<DLFileShortcut> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the document library file shortcuts.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFileShortcutModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of document library file shortcuts
	 * @param end the upper bound of the range of document library file shortcuts (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of document library file shortcuts
	 */
	@Override
	public List<DLFileShortcut> findAll(int start, int end,
		OrderByComparator<DLFileShortcut> orderByComparator) {
		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the document library file shortcuts.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DLFileShortcutModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of document library file shortcuts
	 * @param end the upper bound of the range of document library file shortcuts (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of document library file shortcuts
	 */
	@Override
	public List<DLFileShortcut> findAll(int start, int end,
		OrderByComparator<DLFileShortcut> orderByComparator,
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

		List<DLFileShortcut> list = null;

		if (retrieveFromCache) {
			list = (List<DLFileShortcut>)finderCache.getResult(finderPath,
					finderArgs, this);
		}

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 3));

				query.append(_SQL_SELECT_DLFILESHORTCUT);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_DLFILESHORTCUT;

				if (pagination) {
					sql = sql.concat(DLFileShortcutModelImpl.ORDER_BY_JPQL);
				}
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (!pagination) {
					list = (List<DLFileShortcut>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<DLFileShortcut>)QueryUtil.list(q,
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
	 * Removes all the document library file shortcuts from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (DLFileShortcut dlFileShortcut : findAll()) {
			remove(dlFileShortcut);
		}
	}

	/**
	 * Returns the number of document library file shortcuts.
	 *
	 * @return the number of document library file shortcuts
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_DLFILESHORTCUT);

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
		return DLFileShortcutModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the document library file shortcut persistence.
	 */
	public void afterPropertiesSet() {
	}

	public void destroy() {
		entityCache.removeCache(DLFileShortcutImpl.class.getName());
		finderCache.removeCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@BeanReference(type = CompanyProvider.class)
	protected CompanyProvider companyProvider;
	protected EntityCache entityCache = EntityCacheUtil.getEntityCache();
	protected FinderCache finderCache = FinderCacheUtil.getFinderCache();
	private static final String _SQL_SELECT_DLFILESHORTCUT = "SELECT dlFileShortcut FROM DLFileShortcut dlFileShortcut";
	private static final String _SQL_SELECT_DLFILESHORTCUT_WHERE_PKS_IN = "SELECT dlFileShortcut FROM DLFileShortcut dlFileShortcut WHERE fileShortcutId IN (";
	private static final String _SQL_SELECT_DLFILESHORTCUT_WHERE = "SELECT dlFileShortcut FROM DLFileShortcut dlFileShortcut WHERE ";
	private static final String _SQL_COUNT_DLFILESHORTCUT = "SELECT COUNT(dlFileShortcut) FROM DLFileShortcut dlFileShortcut";
	private static final String _SQL_COUNT_DLFILESHORTCUT_WHERE = "SELECT COUNT(dlFileShortcut) FROM DLFileShortcut dlFileShortcut WHERE ";
	private static final String _FILTER_ENTITY_TABLE_FILTER_PK_COLUMN = "dlFileShortcut.fileShortcutId";
	private static final String _FILTER_SQL_SELECT_DLFILESHORTCUT_WHERE = "SELECT DISTINCT {dlFileShortcut.*} FROM DLFileShortcut dlFileShortcut WHERE ";
	private static final String _FILTER_SQL_SELECT_DLFILESHORTCUT_NO_INLINE_DISTINCT_WHERE_1 =
		"SELECT {DLFileShortcut.*} FROM (SELECT DISTINCT dlFileShortcut.fileShortcutId FROM DLFileShortcut dlFileShortcut WHERE ";
	private static final String _FILTER_SQL_SELECT_DLFILESHORTCUT_NO_INLINE_DISTINCT_WHERE_2 =
		") TEMP_TABLE INNER JOIN DLFileShortcut ON TEMP_TABLE.fileShortcutId = DLFileShortcut.fileShortcutId";
	private static final String _FILTER_SQL_COUNT_DLFILESHORTCUT_WHERE = "SELECT COUNT(DISTINCT dlFileShortcut.fileShortcutId) AS COUNT_VALUE FROM DLFileShortcut dlFileShortcut WHERE ";
	private static final String _FILTER_ENTITY_ALIAS = "dlFileShortcut";
	private static final String _FILTER_ENTITY_TABLE = "DLFileShortcut";
	private static final String _ORDER_BY_ENTITY_ALIAS = "dlFileShortcut.";
	private static final String _ORDER_BY_ENTITY_TABLE = "DLFileShortcut.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No DLFileShortcut exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No DLFileShortcut exists with the key {";
	private static final Log _log = LogFactoryUtil.getLog(DLFileShortcutPersistenceImpl.class);
	private static final Set<String> _badColumnNames = SetUtil.fromArray(new String[] {
				"uuid", "active"
			});
	private static final DLFileShortcut _nullDLFileShortcut = new DLFileShortcutImpl() {
			@Override
			public Object clone() {
				return this;
			}

			@Override
			public CacheModel<DLFileShortcut> toCacheModel() {
				return _nullDLFileShortcutCacheModel;
			}
		};

	private static final CacheModel<DLFileShortcut> _nullDLFileShortcutCacheModel =
		new CacheModel<DLFileShortcut>() {
			@Override
			public DLFileShortcut toEntityModel() {
				return _nullDLFileShortcut;
			}
		};
}