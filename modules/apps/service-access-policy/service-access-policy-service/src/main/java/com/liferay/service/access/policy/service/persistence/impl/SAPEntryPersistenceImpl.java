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

package com.liferay.service.access.policy.service.persistence.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.dao.orm.EntityCache;
import com.liferay.portal.kernel.dao.orm.FinderCache;
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
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;
import com.liferay.portal.model.CacheModel;
import com.liferay.portal.security.permission.InlineSQLHelperUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceContextThreadLocal;
import com.liferay.portal.service.persistence.CompanyProvider;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.spring.extender.service.ServiceReference;

import com.liferay.service.access.policy.exception.NoSuchEntryException;
import com.liferay.service.access.policy.model.SAPEntry;
import com.liferay.service.access.policy.model.impl.SAPEntryImpl;
import com.liferay.service.access.policy.model.impl.SAPEntryModelImpl;
import com.liferay.service.access.policy.service.persistence.SAPEntryPersistence;

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
 * The persistence implementation for the s a p entry service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see SAPEntryPersistence
 * @see com.liferay.service.access.policy.service.persistence.SAPEntryUtil
 * @generated
 */
@ProviderType
public class SAPEntryPersistenceImpl extends BasePersistenceImpl<SAPEntry>
	implements SAPEntryPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link SAPEntryUtil} to access the s a p entry persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = SAPEntryImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(SAPEntryModelImpl.ENTITY_CACHE_ENABLED,
			SAPEntryModelImpl.FINDER_CACHE_ENABLED, SAPEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(SAPEntryModelImpl.ENTITY_CACHE_ENABLED,
			SAPEntryModelImpl.FINDER_CACHE_ENABLED, SAPEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(SAPEntryModelImpl.ENTITY_CACHE_ENABLED,
			SAPEntryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_UUID = new FinderPath(SAPEntryModelImpl.ENTITY_CACHE_ENABLED,
			SAPEntryModelImpl.FINDER_CACHE_ENABLED, SAPEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUuid",
			new String[] {
				String.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID = new FinderPath(SAPEntryModelImpl.ENTITY_CACHE_ENABLED,
			SAPEntryModelImpl.FINDER_CACHE_ENABLED, SAPEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid",
			new String[] { String.class.getName() },
			SAPEntryModelImpl.UUID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID = new FinderPath(SAPEntryModelImpl.ENTITY_CACHE_ENABLED,
			SAPEntryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUuid",
			new String[] { String.class.getName() });

	/**
	 * Returns all the s a p entries where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching s a p entries
	 */
	@Override
	public List<SAPEntry> findByUuid(String uuid) {
		return findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the s a p entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SAPEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of s a p entries
	 * @param end the upper bound of the range of s a p entries (not inclusive)
	 * @return the range of matching s a p entries
	 */
	@Override
	public List<SAPEntry> findByUuid(String uuid, int start, int end) {
		return findByUuid(uuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the s a p entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SAPEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of s a p entries
	 * @param end the upper bound of the range of s a p entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching s a p entries
	 */
	@Override
	public List<SAPEntry> findByUuid(String uuid, int start, int end,
		OrderByComparator<SAPEntry> orderByComparator) {
		return findByUuid(uuid, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the s a p entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SAPEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of s a p entries
	 * @param end the upper bound of the range of s a p entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching s a p entries
	 */
	@Override
	public List<SAPEntry> findByUuid(String uuid, int start, int end,
		OrderByComparator<SAPEntry> orderByComparator, boolean retrieveFromCache) {
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

		List<SAPEntry> list = null;

		if (retrieveFromCache) {
			list = (List<SAPEntry>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (SAPEntry sapEntry : list) {
					if (!Validator.equals(uuid, sapEntry.getUuid())) {
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

			query.append(_SQL_SELECT_SAPENTRY_WHERE);

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
				query.append(SAPEntryModelImpl.ORDER_BY_JPQL);
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
					list = (List<SAPEntry>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<SAPEntry>)QueryUtil.list(q, getDialect(),
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
	 * Returns the first s a p entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching s a p entry
	 * @throws com.liferay.service.access.policy.NoSuchEntryException if a matching s a p entry could not be found
	 */
	@Override
	public SAPEntry findByUuid_First(String uuid,
		OrderByComparator<SAPEntry> orderByComparator)
		throws NoSuchEntryException {
		SAPEntry sapEntry = fetchByUuid_First(uuid, orderByComparator);

		if (sapEntry != null) {
			return sapEntry;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchEntryException(msg.toString());
	}

	/**
	 * Returns the first s a p entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching s a p entry, or <code>null</code> if a matching s a p entry could not be found
	 */
	@Override
	public SAPEntry fetchByUuid_First(String uuid,
		OrderByComparator<SAPEntry> orderByComparator) {
		List<SAPEntry> list = findByUuid(uuid, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last s a p entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching s a p entry
	 * @throws com.liferay.service.access.policy.NoSuchEntryException if a matching s a p entry could not be found
	 */
	@Override
	public SAPEntry findByUuid_Last(String uuid,
		OrderByComparator<SAPEntry> orderByComparator)
		throws NoSuchEntryException {
		SAPEntry sapEntry = fetchByUuid_Last(uuid, orderByComparator);

		if (sapEntry != null) {
			return sapEntry;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchEntryException(msg.toString());
	}

	/**
	 * Returns the last s a p entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching s a p entry, or <code>null</code> if a matching s a p entry could not be found
	 */
	@Override
	public SAPEntry fetchByUuid_Last(String uuid,
		OrderByComparator<SAPEntry> orderByComparator) {
		int count = countByUuid(uuid);

		if (count == 0) {
			return null;
		}

		List<SAPEntry> list = findByUuid(uuid, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the s a p entries before and after the current s a p entry in the ordered set where uuid = &#63;.
	 *
	 * @param sapEntryId the primary key of the current s a p entry
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next s a p entry
	 * @throws com.liferay.service.access.policy.NoSuchEntryException if a s a p entry with the primary key could not be found
	 */
	@Override
	public SAPEntry[] findByUuid_PrevAndNext(long sapEntryId, String uuid,
		OrderByComparator<SAPEntry> orderByComparator)
		throws NoSuchEntryException {
		SAPEntry sapEntry = findByPrimaryKey(sapEntryId);

		Session session = null;

		try {
			session = openSession();

			SAPEntry[] array = new SAPEntryImpl[3];

			array[0] = getByUuid_PrevAndNext(session, sapEntry, uuid,
					orderByComparator, true);

			array[1] = sapEntry;

			array[2] = getByUuid_PrevAndNext(session, sapEntry, uuid,
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

	protected SAPEntry getByUuid_PrevAndNext(Session session,
		SAPEntry sapEntry, String uuid,
		OrderByComparator<SAPEntry> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_SAPENTRY_WHERE);

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
			query.append(SAPEntryModelImpl.ORDER_BY_JPQL);
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
			Object[] values = orderByComparator.getOrderByConditionValues(sapEntry);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<SAPEntry> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the s a p entries that the user has permission to view where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching s a p entries that the user has permission to view
	 */
	@Override
	public List<SAPEntry> filterFindByUuid(String uuid) {
		return filterFindByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the s a p entries that the user has permission to view where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SAPEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of s a p entries
	 * @param end the upper bound of the range of s a p entries (not inclusive)
	 * @return the range of matching s a p entries that the user has permission to view
	 */
	@Override
	public List<SAPEntry> filterFindByUuid(String uuid, int start, int end) {
		return filterFindByUuid(uuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the s a p entries that the user has permissions to view where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SAPEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of s a p entries
	 * @param end the upper bound of the range of s a p entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching s a p entries that the user has permission to view
	 */
	@Override
	public List<SAPEntry> filterFindByUuid(String uuid, int start, int end,
		OrderByComparator<SAPEntry> orderByComparator) {
		if (!InlineSQLHelperUtil.isEnabled()) {
			return findByUuid(uuid, start, end, orderByComparator);
		}

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(3 +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(3);
		}

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_SAPENTRY_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_SAPENTRY_NO_INLINE_DISTINCT_WHERE_1);
		}

		boolean bindUuid = false;

		if (uuid == null) {
			query.append(_FINDER_COLUMN_UUID_UUID_1_SQL);
		}
		else if (uuid.equals(StringPool.BLANK)) {
			query.append(_FINDER_COLUMN_UUID_UUID_3_SQL);
		}
		else {
			bindUuid = true;

			query.append(_FINDER_COLUMN_UUID_UUID_2_SQL);
		}

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_SAPENTRY_NO_INLINE_DISTINCT_WHERE_2);
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
				query.append(SAPEntryModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(SAPEntryModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				SAPEntry.class.getName(), _FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				q.addEntity(_FILTER_ENTITY_ALIAS, SAPEntryImpl.class);
			}
			else {
				q.addEntity(_FILTER_ENTITY_TABLE, SAPEntryImpl.class);
			}

			QueryPos qPos = QueryPos.getInstance(q);

			if (bindUuid) {
				qPos.add(uuid);
			}

			return (List<SAPEntry>)QueryUtil.list(q, getDialect(), start, end);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Returns the s a p entries before and after the current s a p entry in the ordered set of s a p entries that the user has permission to view where uuid = &#63;.
	 *
	 * @param sapEntryId the primary key of the current s a p entry
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next s a p entry
	 * @throws com.liferay.service.access.policy.NoSuchEntryException if a s a p entry with the primary key could not be found
	 */
	@Override
	public SAPEntry[] filterFindByUuid_PrevAndNext(long sapEntryId,
		String uuid, OrderByComparator<SAPEntry> orderByComparator)
		throws NoSuchEntryException {
		if (!InlineSQLHelperUtil.isEnabled()) {
			return findByUuid_PrevAndNext(sapEntryId, uuid, orderByComparator);
		}

		SAPEntry sapEntry = findByPrimaryKey(sapEntryId);

		Session session = null;

		try {
			session = openSession();

			SAPEntry[] array = new SAPEntryImpl[3];

			array[0] = filterGetByUuid_PrevAndNext(session, sapEntry, uuid,
					orderByComparator, true);

			array[1] = sapEntry;

			array[2] = filterGetByUuid_PrevAndNext(session, sapEntry, uuid,
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

	protected SAPEntry filterGetByUuid_PrevAndNext(Session session,
		SAPEntry sapEntry, String uuid,
		OrderByComparator<SAPEntry> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_SAPENTRY_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_SAPENTRY_NO_INLINE_DISTINCT_WHERE_1);
		}

		boolean bindUuid = false;

		if (uuid == null) {
			query.append(_FINDER_COLUMN_UUID_UUID_1_SQL);
		}
		else if (uuid.equals(StringPool.BLANK)) {
			query.append(_FINDER_COLUMN_UUID_UUID_3_SQL);
		}
		else {
			bindUuid = true;

			query.append(_FINDER_COLUMN_UUID_UUID_2_SQL);
		}

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_SAPENTRY_NO_INLINE_DISTINCT_WHERE_2);
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
				query.append(SAPEntryModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(SAPEntryModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				SAPEntry.class.getName(), _FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		SQLQuery q = session.createSynchronizedSQLQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		if (getDB().isSupportsInlineDistinct()) {
			q.addEntity(_FILTER_ENTITY_ALIAS, SAPEntryImpl.class);
		}
		else {
			q.addEntity(_FILTER_ENTITY_TABLE, SAPEntryImpl.class);
		}

		QueryPos qPos = QueryPos.getInstance(q);

		if (bindUuid) {
			qPos.add(uuid);
		}

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(sapEntry);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<SAPEntry> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the s a p entries where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	@Override
	public void removeByUuid(String uuid) {
		for (SAPEntry sapEntry : findByUuid(uuid, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS, null)) {
			remove(sapEntry);
		}
	}

	/**
	 * Returns the number of s a p entries where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching s a p entries
	 */
	@Override
	public int countByUuid(String uuid) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_UUID;

		Object[] finderArgs = new Object[] { uuid };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_SAPENTRY_WHERE);

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

	/**
	 * Returns the number of s a p entries that the user has permission to view where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching s a p entries that the user has permission to view
	 */
	@Override
	public int filterCountByUuid(String uuid) {
		if (!InlineSQLHelperUtil.isEnabled()) {
			return countByUuid(uuid);
		}

		StringBundler query = new StringBundler(2);

		query.append(_FILTER_SQL_COUNT_SAPENTRY_WHERE);

		boolean bindUuid = false;

		if (uuid == null) {
			query.append(_FINDER_COLUMN_UUID_UUID_1_SQL);
		}
		else if (uuid.equals(StringPool.BLANK)) {
			query.append(_FINDER_COLUMN_UUID_UUID_3_SQL);
		}
		else {
			bindUuid = true;

			query.append(_FINDER_COLUMN_UUID_UUID_2_SQL);
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				SAPEntry.class.getName(), _FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME,
				com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			if (bindUuid) {
				qPos.add(uuid);
			}

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

	private static final String _FINDER_COLUMN_UUID_UUID_1 = "sapEntry.uuid IS NULL";
	private static final String _FINDER_COLUMN_UUID_UUID_2 = "sapEntry.uuid = ?";
	private static final String _FINDER_COLUMN_UUID_UUID_3 = "(sapEntry.uuid IS NULL OR sapEntry.uuid = '')";
	private static final String _FINDER_COLUMN_UUID_UUID_1_SQL = "sapEntry.uuid_ IS NULL";
	private static final String _FINDER_COLUMN_UUID_UUID_2_SQL = "sapEntry.uuid_ = ?";
	private static final String _FINDER_COLUMN_UUID_UUID_3_SQL = "(sapEntry.uuid_ IS NULL OR sapEntry.uuid_ = '')";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_UUID_C = new FinderPath(SAPEntryModelImpl.ENTITY_CACHE_ENABLED,
			SAPEntryModelImpl.FINDER_CACHE_ENABLED, SAPEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUuid_C",
			new String[] {
				String.class.getName(), Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C =
		new FinderPath(SAPEntryModelImpl.ENTITY_CACHE_ENABLED,
			SAPEntryModelImpl.FINDER_CACHE_ENABLED, SAPEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid_C",
			new String[] { String.class.getName(), Long.class.getName() },
			SAPEntryModelImpl.UUID_COLUMN_BITMASK |
			SAPEntryModelImpl.COMPANYID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID_C = new FinderPath(SAPEntryModelImpl.ENTITY_CACHE_ENABLED,
			SAPEntryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUuid_C",
			new String[] { String.class.getName(), Long.class.getName() });

	/**
	 * Returns all the s a p entries where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching s a p entries
	 */
	@Override
	public List<SAPEntry> findByUuid_C(String uuid, long companyId) {
		return findByUuid_C(uuid, companyId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the s a p entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SAPEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of s a p entries
	 * @param end the upper bound of the range of s a p entries (not inclusive)
	 * @return the range of matching s a p entries
	 */
	@Override
	public List<SAPEntry> findByUuid_C(String uuid, long companyId, int start,
		int end) {
		return findByUuid_C(uuid, companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the s a p entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SAPEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of s a p entries
	 * @param end the upper bound of the range of s a p entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching s a p entries
	 */
	@Override
	public List<SAPEntry> findByUuid_C(String uuid, long companyId, int start,
		int end, OrderByComparator<SAPEntry> orderByComparator) {
		return findByUuid_C(uuid, companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the s a p entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SAPEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of s a p entries
	 * @param end the upper bound of the range of s a p entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching s a p entries
	 */
	@Override
	public List<SAPEntry> findByUuid_C(String uuid, long companyId, int start,
		int end, OrderByComparator<SAPEntry> orderByComparator,
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

		List<SAPEntry> list = null;

		if (retrieveFromCache) {
			list = (List<SAPEntry>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (SAPEntry sapEntry : list) {
					if (!Validator.equals(uuid, sapEntry.getUuid()) ||
							(companyId != sapEntry.getCompanyId())) {
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

			query.append(_SQL_SELECT_SAPENTRY_WHERE);

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
				query.append(SAPEntryModelImpl.ORDER_BY_JPQL);
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
					list = (List<SAPEntry>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<SAPEntry>)QueryUtil.list(q, getDialect(),
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
	 * Returns the first s a p entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching s a p entry
	 * @throws com.liferay.service.access.policy.NoSuchEntryException if a matching s a p entry could not be found
	 */
	@Override
	public SAPEntry findByUuid_C_First(String uuid, long companyId,
		OrderByComparator<SAPEntry> orderByComparator)
		throws NoSuchEntryException {
		SAPEntry sapEntry = fetchByUuid_C_First(uuid, companyId,
				orderByComparator);

		if (sapEntry != null) {
			return sapEntry;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(", companyId=");
		msg.append(companyId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchEntryException(msg.toString());
	}

	/**
	 * Returns the first s a p entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching s a p entry, or <code>null</code> if a matching s a p entry could not be found
	 */
	@Override
	public SAPEntry fetchByUuid_C_First(String uuid, long companyId,
		OrderByComparator<SAPEntry> orderByComparator) {
		List<SAPEntry> list = findByUuid_C(uuid, companyId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last s a p entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching s a p entry
	 * @throws com.liferay.service.access.policy.NoSuchEntryException if a matching s a p entry could not be found
	 */
	@Override
	public SAPEntry findByUuid_C_Last(String uuid, long companyId,
		OrderByComparator<SAPEntry> orderByComparator)
		throws NoSuchEntryException {
		SAPEntry sapEntry = fetchByUuid_C_Last(uuid, companyId,
				orderByComparator);

		if (sapEntry != null) {
			return sapEntry;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(", companyId=");
		msg.append(companyId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchEntryException(msg.toString());
	}

	/**
	 * Returns the last s a p entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching s a p entry, or <code>null</code> if a matching s a p entry could not be found
	 */
	@Override
	public SAPEntry fetchByUuid_C_Last(String uuid, long companyId,
		OrderByComparator<SAPEntry> orderByComparator) {
		int count = countByUuid_C(uuid, companyId);

		if (count == 0) {
			return null;
		}

		List<SAPEntry> list = findByUuid_C(uuid, companyId, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the s a p entries before and after the current s a p entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param sapEntryId the primary key of the current s a p entry
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next s a p entry
	 * @throws com.liferay.service.access.policy.NoSuchEntryException if a s a p entry with the primary key could not be found
	 */
	@Override
	public SAPEntry[] findByUuid_C_PrevAndNext(long sapEntryId, String uuid,
		long companyId, OrderByComparator<SAPEntry> orderByComparator)
		throws NoSuchEntryException {
		SAPEntry sapEntry = findByPrimaryKey(sapEntryId);

		Session session = null;

		try {
			session = openSession();

			SAPEntry[] array = new SAPEntryImpl[3];

			array[0] = getByUuid_C_PrevAndNext(session, sapEntry, uuid,
					companyId, orderByComparator, true);

			array[1] = sapEntry;

			array[2] = getByUuid_C_PrevAndNext(session, sapEntry, uuid,
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

	protected SAPEntry getByUuid_C_PrevAndNext(Session session,
		SAPEntry sapEntry, String uuid, long companyId,
		OrderByComparator<SAPEntry> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_SAPENTRY_WHERE);

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
			query.append(SAPEntryModelImpl.ORDER_BY_JPQL);
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
			Object[] values = orderByComparator.getOrderByConditionValues(sapEntry);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<SAPEntry> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the s a p entries that the user has permission to view where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching s a p entries that the user has permission to view
	 */
	@Override
	public List<SAPEntry> filterFindByUuid_C(String uuid, long companyId) {
		return filterFindByUuid_C(uuid, companyId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the s a p entries that the user has permission to view where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SAPEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of s a p entries
	 * @param end the upper bound of the range of s a p entries (not inclusive)
	 * @return the range of matching s a p entries that the user has permission to view
	 */
	@Override
	public List<SAPEntry> filterFindByUuid_C(String uuid, long companyId,
		int start, int end) {
		return filterFindByUuid_C(uuid, companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the s a p entries that the user has permissions to view where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SAPEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of s a p entries
	 * @param end the upper bound of the range of s a p entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching s a p entries that the user has permission to view
	 */
	@Override
	public List<SAPEntry> filterFindByUuid_C(String uuid, long companyId,
		int start, int end, OrderByComparator<SAPEntry> orderByComparator) {
		if (!InlineSQLHelperUtil.isEnabled()) {
			return findByUuid_C(uuid, companyId, start, end, orderByComparator);
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
			query.append(_FILTER_SQL_SELECT_SAPENTRY_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_SAPENTRY_NO_INLINE_DISTINCT_WHERE_1);
		}

		boolean bindUuid = false;

		if (uuid == null) {
			query.append(_FINDER_COLUMN_UUID_C_UUID_1_SQL);
		}
		else if (uuid.equals(StringPool.BLANK)) {
			query.append(_FINDER_COLUMN_UUID_C_UUID_3_SQL);
		}
		else {
			bindUuid = true;

			query.append(_FINDER_COLUMN_UUID_C_UUID_2_SQL);
		}

		query.append(_FINDER_COLUMN_UUID_C_COMPANYID_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_SAPENTRY_NO_INLINE_DISTINCT_WHERE_2);
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
				query.append(SAPEntryModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(SAPEntryModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				SAPEntry.class.getName(), _FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				q.addEntity(_FILTER_ENTITY_ALIAS, SAPEntryImpl.class);
			}
			else {
				q.addEntity(_FILTER_ENTITY_TABLE, SAPEntryImpl.class);
			}

			QueryPos qPos = QueryPos.getInstance(q);

			if (bindUuid) {
				qPos.add(uuid);
			}

			qPos.add(companyId);

			return (List<SAPEntry>)QueryUtil.list(q, getDialect(), start, end);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Returns the s a p entries before and after the current s a p entry in the ordered set of s a p entries that the user has permission to view where uuid = &#63; and companyId = &#63;.
	 *
	 * @param sapEntryId the primary key of the current s a p entry
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next s a p entry
	 * @throws com.liferay.service.access.policy.NoSuchEntryException if a s a p entry with the primary key could not be found
	 */
	@Override
	public SAPEntry[] filterFindByUuid_C_PrevAndNext(long sapEntryId,
		String uuid, long companyId,
		OrderByComparator<SAPEntry> orderByComparator)
		throws NoSuchEntryException {
		if (!InlineSQLHelperUtil.isEnabled()) {
			return findByUuid_C_PrevAndNext(sapEntryId, uuid, companyId,
				orderByComparator);
		}

		SAPEntry sapEntry = findByPrimaryKey(sapEntryId);

		Session session = null;

		try {
			session = openSession();

			SAPEntry[] array = new SAPEntryImpl[3];

			array[0] = filterGetByUuid_C_PrevAndNext(session, sapEntry, uuid,
					companyId, orderByComparator, true);

			array[1] = sapEntry;

			array[2] = filterGetByUuid_C_PrevAndNext(session, sapEntry, uuid,
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

	protected SAPEntry filterGetByUuid_C_PrevAndNext(Session session,
		SAPEntry sapEntry, String uuid, long companyId,
		OrderByComparator<SAPEntry> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_SAPENTRY_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_SAPENTRY_NO_INLINE_DISTINCT_WHERE_1);
		}

		boolean bindUuid = false;

		if (uuid == null) {
			query.append(_FINDER_COLUMN_UUID_C_UUID_1_SQL);
		}
		else if (uuid.equals(StringPool.BLANK)) {
			query.append(_FINDER_COLUMN_UUID_C_UUID_3_SQL);
		}
		else {
			bindUuid = true;

			query.append(_FINDER_COLUMN_UUID_C_UUID_2_SQL);
		}

		query.append(_FINDER_COLUMN_UUID_C_COMPANYID_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_SAPENTRY_NO_INLINE_DISTINCT_WHERE_2);
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
				query.append(SAPEntryModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(SAPEntryModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				SAPEntry.class.getName(), _FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		SQLQuery q = session.createSynchronizedSQLQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		if (getDB().isSupportsInlineDistinct()) {
			q.addEntity(_FILTER_ENTITY_ALIAS, SAPEntryImpl.class);
		}
		else {
			q.addEntity(_FILTER_ENTITY_TABLE, SAPEntryImpl.class);
		}

		QueryPos qPos = QueryPos.getInstance(q);

		if (bindUuid) {
			qPos.add(uuid);
		}

		qPos.add(companyId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(sapEntry);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<SAPEntry> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the s a p entries where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	@Override
	public void removeByUuid_C(String uuid, long companyId) {
		for (SAPEntry sapEntry : findByUuid_C(uuid, companyId,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(sapEntry);
		}
	}

	/**
	 * Returns the number of s a p entries where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching s a p entries
	 */
	@Override
	public int countByUuid_C(String uuid, long companyId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_UUID_C;

		Object[] finderArgs = new Object[] { uuid, companyId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_SAPENTRY_WHERE);

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

	/**
	 * Returns the number of s a p entries that the user has permission to view where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching s a p entries that the user has permission to view
	 */
	@Override
	public int filterCountByUuid_C(String uuid, long companyId) {
		if (!InlineSQLHelperUtil.isEnabled()) {
			return countByUuid_C(uuid, companyId);
		}

		StringBundler query = new StringBundler(3);

		query.append(_FILTER_SQL_COUNT_SAPENTRY_WHERE);

		boolean bindUuid = false;

		if (uuid == null) {
			query.append(_FINDER_COLUMN_UUID_C_UUID_1_SQL);
		}
		else if (uuid.equals(StringPool.BLANK)) {
			query.append(_FINDER_COLUMN_UUID_C_UUID_3_SQL);
		}
		else {
			bindUuid = true;

			query.append(_FINDER_COLUMN_UUID_C_UUID_2_SQL);
		}

		query.append(_FINDER_COLUMN_UUID_C_COMPANYID_2);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				SAPEntry.class.getName(), _FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME,
				com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			if (bindUuid) {
				qPos.add(uuid);
			}

			qPos.add(companyId);

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

	private static final String _FINDER_COLUMN_UUID_C_UUID_1 = "sapEntry.uuid IS NULL AND ";
	private static final String _FINDER_COLUMN_UUID_C_UUID_2 = "sapEntry.uuid = ? AND ";
	private static final String _FINDER_COLUMN_UUID_C_UUID_3 = "(sapEntry.uuid IS NULL OR sapEntry.uuid = '') AND ";
	private static final String _FINDER_COLUMN_UUID_C_UUID_1_SQL = "sapEntry.uuid_ IS NULL AND ";
	private static final String _FINDER_COLUMN_UUID_C_UUID_2_SQL = "sapEntry.uuid_ = ? AND ";
	private static final String _FINDER_COLUMN_UUID_C_UUID_3_SQL = "(sapEntry.uuid_ IS NULL OR sapEntry.uuid_ = '') AND ";
	private static final String _FINDER_COLUMN_UUID_C_COMPANYID_2 = "sapEntry.companyId = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_COMPANYID =
		new FinderPath(SAPEntryModelImpl.ENTITY_CACHE_ENABLED,
			SAPEntryModelImpl.FINDER_CACHE_ENABLED, SAPEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByCompanyId",
			new String[] {
				Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID =
		new FinderPath(SAPEntryModelImpl.ENTITY_CACHE_ENABLED,
			SAPEntryModelImpl.FINDER_CACHE_ENABLED, SAPEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByCompanyId",
			new String[] { Long.class.getName() },
			SAPEntryModelImpl.COMPANYID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_COMPANYID = new FinderPath(SAPEntryModelImpl.ENTITY_CACHE_ENABLED,
			SAPEntryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByCompanyId",
			new String[] { Long.class.getName() });

	/**
	 * Returns all the s a p entries where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching s a p entries
	 */
	@Override
	public List<SAPEntry> findByCompanyId(long companyId) {
		return findByCompanyId(companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the s a p entries where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SAPEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of s a p entries
	 * @param end the upper bound of the range of s a p entries (not inclusive)
	 * @return the range of matching s a p entries
	 */
	@Override
	public List<SAPEntry> findByCompanyId(long companyId, int start, int end) {
		return findByCompanyId(companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the s a p entries where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SAPEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of s a p entries
	 * @param end the upper bound of the range of s a p entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching s a p entries
	 */
	@Override
	public List<SAPEntry> findByCompanyId(long companyId, int start, int end,
		OrderByComparator<SAPEntry> orderByComparator) {
		return findByCompanyId(companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the s a p entries where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SAPEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of s a p entries
	 * @param end the upper bound of the range of s a p entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching s a p entries
	 */
	@Override
	public List<SAPEntry> findByCompanyId(long companyId, int start, int end,
		OrderByComparator<SAPEntry> orderByComparator, boolean retrieveFromCache) {
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

		List<SAPEntry> list = null;

		if (retrieveFromCache) {
			list = (List<SAPEntry>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (SAPEntry sapEntry : list) {
					if ((companyId != sapEntry.getCompanyId())) {
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

			query.append(_SQL_SELECT_SAPENTRY_WHERE);

			query.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(SAPEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				if (!pagination) {
					list = (List<SAPEntry>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<SAPEntry>)QueryUtil.list(q, getDialect(),
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
	 * Returns the first s a p entry in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching s a p entry
	 * @throws com.liferay.service.access.policy.NoSuchEntryException if a matching s a p entry could not be found
	 */
	@Override
	public SAPEntry findByCompanyId_First(long companyId,
		OrderByComparator<SAPEntry> orderByComparator)
		throws NoSuchEntryException {
		SAPEntry sapEntry = fetchByCompanyId_First(companyId, orderByComparator);

		if (sapEntry != null) {
			return sapEntry;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("companyId=");
		msg.append(companyId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchEntryException(msg.toString());
	}

	/**
	 * Returns the first s a p entry in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching s a p entry, or <code>null</code> if a matching s a p entry could not be found
	 */
	@Override
	public SAPEntry fetchByCompanyId_First(long companyId,
		OrderByComparator<SAPEntry> orderByComparator) {
		List<SAPEntry> list = findByCompanyId(companyId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last s a p entry in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching s a p entry
	 * @throws com.liferay.service.access.policy.NoSuchEntryException if a matching s a p entry could not be found
	 */
	@Override
	public SAPEntry findByCompanyId_Last(long companyId,
		OrderByComparator<SAPEntry> orderByComparator)
		throws NoSuchEntryException {
		SAPEntry sapEntry = fetchByCompanyId_Last(companyId, orderByComparator);

		if (sapEntry != null) {
			return sapEntry;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("companyId=");
		msg.append(companyId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchEntryException(msg.toString());
	}

	/**
	 * Returns the last s a p entry in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching s a p entry, or <code>null</code> if a matching s a p entry could not be found
	 */
	@Override
	public SAPEntry fetchByCompanyId_Last(long companyId,
		OrderByComparator<SAPEntry> orderByComparator) {
		int count = countByCompanyId(companyId);

		if (count == 0) {
			return null;
		}

		List<SAPEntry> list = findByCompanyId(companyId, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the s a p entries before and after the current s a p entry in the ordered set where companyId = &#63;.
	 *
	 * @param sapEntryId the primary key of the current s a p entry
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next s a p entry
	 * @throws com.liferay.service.access.policy.NoSuchEntryException if a s a p entry with the primary key could not be found
	 */
	@Override
	public SAPEntry[] findByCompanyId_PrevAndNext(long sapEntryId,
		long companyId, OrderByComparator<SAPEntry> orderByComparator)
		throws NoSuchEntryException {
		SAPEntry sapEntry = findByPrimaryKey(sapEntryId);

		Session session = null;

		try {
			session = openSession();

			SAPEntry[] array = new SAPEntryImpl[3];

			array[0] = getByCompanyId_PrevAndNext(session, sapEntry, companyId,
					orderByComparator, true);

			array[1] = sapEntry;

			array[2] = getByCompanyId_PrevAndNext(session, sapEntry, companyId,
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

	protected SAPEntry getByCompanyId_PrevAndNext(Session session,
		SAPEntry sapEntry, long companyId,
		OrderByComparator<SAPEntry> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_SAPENTRY_WHERE);

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
			query.append(SAPEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(companyId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(sapEntry);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<SAPEntry> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the s a p entries that the user has permission to view where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching s a p entries that the user has permission to view
	 */
	@Override
	public List<SAPEntry> filterFindByCompanyId(long companyId) {
		return filterFindByCompanyId(companyId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the s a p entries that the user has permission to view where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SAPEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of s a p entries
	 * @param end the upper bound of the range of s a p entries (not inclusive)
	 * @return the range of matching s a p entries that the user has permission to view
	 */
	@Override
	public List<SAPEntry> filterFindByCompanyId(long companyId, int start,
		int end) {
		return filterFindByCompanyId(companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the s a p entries that the user has permissions to view where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SAPEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of s a p entries
	 * @param end the upper bound of the range of s a p entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching s a p entries that the user has permission to view
	 */
	@Override
	public List<SAPEntry> filterFindByCompanyId(long companyId, int start,
		int end, OrderByComparator<SAPEntry> orderByComparator) {
		if (!InlineSQLHelperUtil.isEnabled()) {
			return findByCompanyId(companyId, start, end, orderByComparator);
		}

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(3 +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(3);
		}

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_SAPENTRY_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_SAPENTRY_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_SAPENTRY_NO_INLINE_DISTINCT_WHERE_2);
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
				query.append(SAPEntryModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(SAPEntryModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				SAPEntry.class.getName(), _FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				q.addEntity(_FILTER_ENTITY_ALIAS, SAPEntryImpl.class);
			}
			else {
				q.addEntity(_FILTER_ENTITY_TABLE, SAPEntryImpl.class);
			}

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(companyId);

			return (List<SAPEntry>)QueryUtil.list(q, getDialect(), start, end);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Returns the s a p entries before and after the current s a p entry in the ordered set of s a p entries that the user has permission to view where companyId = &#63;.
	 *
	 * @param sapEntryId the primary key of the current s a p entry
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next s a p entry
	 * @throws com.liferay.service.access.policy.NoSuchEntryException if a s a p entry with the primary key could not be found
	 */
	@Override
	public SAPEntry[] filterFindByCompanyId_PrevAndNext(long sapEntryId,
		long companyId, OrderByComparator<SAPEntry> orderByComparator)
		throws NoSuchEntryException {
		if (!InlineSQLHelperUtil.isEnabled()) {
			return findByCompanyId_PrevAndNext(sapEntryId, companyId,
				orderByComparator);
		}

		SAPEntry sapEntry = findByPrimaryKey(sapEntryId);

		Session session = null;

		try {
			session = openSession();

			SAPEntry[] array = new SAPEntryImpl[3];

			array[0] = filterGetByCompanyId_PrevAndNext(session, sapEntry,
					companyId, orderByComparator, true);

			array[1] = sapEntry;

			array[2] = filterGetByCompanyId_PrevAndNext(session, sapEntry,
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

	protected SAPEntry filterGetByCompanyId_PrevAndNext(Session session,
		SAPEntry sapEntry, long companyId,
		OrderByComparator<SAPEntry> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_SAPENTRY_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_SAPENTRY_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_SAPENTRY_NO_INLINE_DISTINCT_WHERE_2);
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
				query.append(SAPEntryModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(SAPEntryModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				SAPEntry.class.getName(), _FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		SQLQuery q = session.createSynchronizedSQLQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		if (getDB().isSupportsInlineDistinct()) {
			q.addEntity(_FILTER_ENTITY_ALIAS, SAPEntryImpl.class);
		}
		else {
			q.addEntity(_FILTER_ENTITY_TABLE, SAPEntryImpl.class);
		}

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(companyId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(sapEntry);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<SAPEntry> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the s a p entries where companyId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 */
	@Override
	public void removeByCompanyId(long companyId) {
		for (SAPEntry sapEntry : findByCompanyId(companyId, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS, null)) {
			remove(sapEntry);
		}
	}

	/**
	 * Returns the number of s a p entries where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching s a p entries
	 */
	@Override
	public int countByCompanyId(long companyId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_COMPANYID;

		Object[] finderArgs = new Object[] { companyId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_SAPENTRY_WHERE);

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

	/**
	 * Returns the number of s a p entries that the user has permission to view where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching s a p entries that the user has permission to view
	 */
	@Override
	public int filterCountByCompanyId(long companyId) {
		if (!InlineSQLHelperUtil.isEnabled()) {
			return countByCompanyId(companyId);
		}

		StringBundler query = new StringBundler(2);

		query.append(_FILTER_SQL_COUNT_SAPENTRY_WHERE);

		query.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				SAPEntry.class.getName(), _FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME,
				com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(companyId);

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

	private static final String _FINDER_COLUMN_COMPANYID_COMPANYID_2 = "sapEntry.companyId = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_C_D = new FinderPath(SAPEntryModelImpl.ENTITY_CACHE_ENABLED,
			SAPEntryModelImpl.FINDER_CACHE_ENABLED, SAPEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByC_D",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_D = new FinderPath(SAPEntryModelImpl.ENTITY_CACHE_ENABLED,
			SAPEntryModelImpl.FINDER_CACHE_ENABLED, SAPEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByC_D",
			new String[] { Long.class.getName(), Boolean.class.getName() },
			SAPEntryModelImpl.COMPANYID_COLUMN_BITMASK |
			SAPEntryModelImpl.DEFAULTSAPENTRY_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_C_D = new FinderPath(SAPEntryModelImpl.ENTITY_CACHE_ENABLED,
			SAPEntryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_D",
			new String[] { Long.class.getName(), Boolean.class.getName() });

	/**
	 * Returns all the s a p entries where companyId = &#63; and defaultSAPEntry = &#63;.
	 *
	 * @param companyId the company ID
	 * @param defaultSAPEntry the default s a p entry
	 * @return the matching s a p entries
	 */
	@Override
	public List<SAPEntry> findByC_D(long companyId, boolean defaultSAPEntry) {
		return findByC_D(companyId, defaultSAPEntry, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the s a p entries where companyId = &#63; and defaultSAPEntry = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SAPEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param defaultSAPEntry the default s a p entry
	 * @param start the lower bound of the range of s a p entries
	 * @param end the upper bound of the range of s a p entries (not inclusive)
	 * @return the range of matching s a p entries
	 */
	@Override
	public List<SAPEntry> findByC_D(long companyId, boolean defaultSAPEntry,
		int start, int end) {
		return findByC_D(companyId, defaultSAPEntry, start, end, null);
	}

	/**
	 * Returns an ordered range of all the s a p entries where companyId = &#63; and defaultSAPEntry = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SAPEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param defaultSAPEntry the default s a p entry
	 * @param start the lower bound of the range of s a p entries
	 * @param end the upper bound of the range of s a p entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching s a p entries
	 */
	@Override
	public List<SAPEntry> findByC_D(long companyId, boolean defaultSAPEntry,
		int start, int end, OrderByComparator<SAPEntry> orderByComparator) {
		return findByC_D(companyId, defaultSAPEntry, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the s a p entries where companyId = &#63; and defaultSAPEntry = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SAPEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param defaultSAPEntry the default s a p entry
	 * @param start the lower bound of the range of s a p entries
	 * @param end the upper bound of the range of s a p entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching s a p entries
	 */
	@Override
	public List<SAPEntry> findByC_D(long companyId, boolean defaultSAPEntry,
		int start, int end, OrderByComparator<SAPEntry> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_D;
			finderArgs = new Object[] { companyId, defaultSAPEntry };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_C_D;
			finderArgs = new Object[] {
					companyId, defaultSAPEntry,
					
					start, end, orderByComparator
				};
		}

		List<SAPEntry> list = null;

		if (retrieveFromCache) {
			list = (List<SAPEntry>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (SAPEntry sapEntry : list) {
					if ((companyId != sapEntry.getCompanyId()) ||
							(defaultSAPEntry != sapEntry.getDefaultSAPEntry())) {
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

			query.append(_SQL_SELECT_SAPENTRY_WHERE);

			query.append(_FINDER_COLUMN_C_D_COMPANYID_2);

			query.append(_FINDER_COLUMN_C_D_DEFAULTSAPENTRY_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(SAPEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				qPos.add(defaultSAPEntry);

				if (!pagination) {
					list = (List<SAPEntry>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<SAPEntry>)QueryUtil.list(q, getDialect(),
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
	 * Returns the first s a p entry in the ordered set where companyId = &#63; and defaultSAPEntry = &#63;.
	 *
	 * @param companyId the company ID
	 * @param defaultSAPEntry the default s a p entry
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching s a p entry
	 * @throws com.liferay.service.access.policy.NoSuchEntryException if a matching s a p entry could not be found
	 */
	@Override
	public SAPEntry findByC_D_First(long companyId, boolean defaultSAPEntry,
		OrderByComparator<SAPEntry> orderByComparator)
		throws NoSuchEntryException {
		SAPEntry sapEntry = fetchByC_D_First(companyId, defaultSAPEntry,
				orderByComparator);

		if (sapEntry != null) {
			return sapEntry;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("companyId=");
		msg.append(companyId);

		msg.append(", defaultSAPEntry=");
		msg.append(defaultSAPEntry);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchEntryException(msg.toString());
	}

	/**
	 * Returns the first s a p entry in the ordered set where companyId = &#63; and defaultSAPEntry = &#63;.
	 *
	 * @param companyId the company ID
	 * @param defaultSAPEntry the default s a p entry
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching s a p entry, or <code>null</code> if a matching s a p entry could not be found
	 */
	@Override
	public SAPEntry fetchByC_D_First(long companyId, boolean defaultSAPEntry,
		OrderByComparator<SAPEntry> orderByComparator) {
		List<SAPEntry> list = findByC_D(companyId, defaultSAPEntry, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last s a p entry in the ordered set where companyId = &#63; and defaultSAPEntry = &#63;.
	 *
	 * @param companyId the company ID
	 * @param defaultSAPEntry the default s a p entry
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching s a p entry
	 * @throws com.liferay.service.access.policy.NoSuchEntryException if a matching s a p entry could not be found
	 */
	@Override
	public SAPEntry findByC_D_Last(long companyId, boolean defaultSAPEntry,
		OrderByComparator<SAPEntry> orderByComparator)
		throws NoSuchEntryException {
		SAPEntry sapEntry = fetchByC_D_Last(companyId, defaultSAPEntry,
				orderByComparator);

		if (sapEntry != null) {
			return sapEntry;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("companyId=");
		msg.append(companyId);

		msg.append(", defaultSAPEntry=");
		msg.append(defaultSAPEntry);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchEntryException(msg.toString());
	}

	/**
	 * Returns the last s a p entry in the ordered set where companyId = &#63; and defaultSAPEntry = &#63;.
	 *
	 * @param companyId the company ID
	 * @param defaultSAPEntry the default s a p entry
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching s a p entry, or <code>null</code> if a matching s a p entry could not be found
	 */
	@Override
	public SAPEntry fetchByC_D_Last(long companyId, boolean defaultSAPEntry,
		OrderByComparator<SAPEntry> orderByComparator) {
		int count = countByC_D(companyId, defaultSAPEntry);

		if (count == 0) {
			return null;
		}

		List<SAPEntry> list = findByC_D(companyId, defaultSAPEntry, count - 1,
				count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the s a p entries before and after the current s a p entry in the ordered set where companyId = &#63; and defaultSAPEntry = &#63;.
	 *
	 * @param sapEntryId the primary key of the current s a p entry
	 * @param companyId the company ID
	 * @param defaultSAPEntry the default s a p entry
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next s a p entry
	 * @throws com.liferay.service.access.policy.NoSuchEntryException if a s a p entry with the primary key could not be found
	 */
	@Override
	public SAPEntry[] findByC_D_PrevAndNext(long sapEntryId, long companyId,
		boolean defaultSAPEntry, OrderByComparator<SAPEntry> orderByComparator)
		throws NoSuchEntryException {
		SAPEntry sapEntry = findByPrimaryKey(sapEntryId);

		Session session = null;

		try {
			session = openSession();

			SAPEntry[] array = new SAPEntryImpl[3];

			array[0] = getByC_D_PrevAndNext(session, sapEntry, companyId,
					defaultSAPEntry, orderByComparator, true);

			array[1] = sapEntry;

			array[2] = getByC_D_PrevAndNext(session, sapEntry, companyId,
					defaultSAPEntry, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected SAPEntry getByC_D_PrevAndNext(Session session, SAPEntry sapEntry,
		long companyId, boolean defaultSAPEntry,
		OrderByComparator<SAPEntry> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_SAPENTRY_WHERE);

		query.append(_FINDER_COLUMN_C_D_COMPANYID_2);

		query.append(_FINDER_COLUMN_C_D_DEFAULTSAPENTRY_2);

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
			query.append(SAPEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(companyId);

		qPos.add(defaultSAPEntry);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(sapEntry);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<SAPEntry> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the s a p entries that the user has permission to view where companyId = &#63; and defaultSAPEntry = &#63;.
	 *
	 * @param companyId the company ID
	 * @param defaultSAPEntry the default s a p entry
	 * @return the matching s a p entries that the user has permission to view
	 */
	@Override
	public List<SAPEntry> filterFindByC_D(long companyId,
		boolean defaultSAPEntry) {
		return filterFindByC_D(companyId, defaultSAPEntry, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the s a p entries that the user has permission to view where companyId = &#63; and defaultSAPEntry = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SAPEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param defaultSAPEntry the default s a p entry
	 * @param start the lower bound of the range of s a p entries
	 * @param end the upper bound of the range of s a p entries (not inclusive)
	 * @return the range of matching s a p entries that the user has permission to view
	 */
	@Override
	public List<SAPEntry> filterFindByC_D(long companyId,
		boolean defaultSAPEntry, int start, int end) {
		return filterFindByC_D(companyId, defaultSAPEntry, start, end, null);
	}

	/**
	 * Returns an ordered range of all the s a p entries that the user has permissions to view where companyId = &#63; and defaultSAPEntry = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SAPEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param defaultSAPEntry the default s a p entry
	 * @param start the lower bound of the range of s a p entries
	 * @param end the upper bound of the range of s a p entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching s a p entries that the user has permission to view
	 */
	@Override
	public List<SAPEntry> filterFindByC_D(long companyId,
		boolean defaultSAPEntry, int start, int end,
		OrderByComparator<SAPEntry> orderByComparator) {
		if (!InlineSQLHelperUtil.isEnabled()) {
			return findByC_D(companyId, defaultSAPEntry, start, end,
				orderByComparator);
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
			query.append(_FILTER_SQL_SELECT_SAPENTRY_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_SAPENTRY_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_C_D_COMPANYID_2);

		query.append(_FINDER_COLUMN_C_D_DEFAULTSAPENTRY_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_SAPENTRY_NO_INLINE_DISTINCT_WHERE_2);
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
				query.append(SAPEntryModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(SAPEntryModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				SAPEntry.class.getName(), _FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				q.addEntity(_FILTER_ENTITY_ALIAS, SAPEntryImpl.class);
			}
			else {
				q.addEntity(_FILTER_ENTITY_TABLE, SAPEntryImpl.class);
			}

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(companyId);

			qPos.add(defaultSAPEntry);

			return (List<SAPEntry>)QueryUtil.list(q, getDialect(), start, end);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Returns the s a p entries before and after the current s a p entry in the ordered set of s a p entries that the user has permission to view where companyId = &#63; and defaultSAPEntry = &#63;.
	 *
	 * @param sapEntryId the primary key of the current s a p entry
	 * @param companyId the company ID
	 * @param defaultSAPEntry the default s a p entry
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next s a p entry
	 * @throws com.liferay.service.access.policy.NoSuchEntryException if a s a p entry with the primary key could not be found
	 */
	@Override
	public SAPEntry[] filterFindByC_D_PrevAndNext(long sapEntryId,
		long companyId, boolean defaultSAPEntry,
		OrderByComparator<SAPEntry> orderByComparator)
		throws NoSuchEntryException {
		if (!InlineSQLHelperUtil.isEnabled()) {
			return findByC_D_PrevAndNext(sapEntryId, companyId,
				defaultSAPEntry, orderByComparator);
		}

		SAPEntry sapEntry = findByPrimaryKey(sapEntryId);

		Session session = null;

		try {
			session = openSession();

			SAPEntry[] array = new SAPEntryImpl[3];

			array[0] = filterGetByC_D_PrevAndNext(session, sapEntry, companyId,
					defaultSAPEntry, orderByComparator, true);

			array[1] = sapEntry;

			array[2] = filterGetByC_D_PrevAndNext(session, sapEntry, companyId,
					defaultSAPEntry, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected SAPEntry filterGetByC_D_PrevAndNext(Session session,
		SAPEntry sapEntry, long companyId, boolean defaultSAPEntry,
		OrderByComparator<SAPEntry> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_SAPENTRY_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_SAPENTRY_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_C_D_COMPANYID_2);

		query.append(_FINDER_COLUMN_C_D_DEFAULTSAPENTRY_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_SAPENTRY_NO_INLINE_DISTINCT_WHERE_2);
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
				query.append(SAPEntryModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(SAPEntryModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				SAPEntry.class.getName(), _FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		SQLQuery q = session.createSynchronizedSQLQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		if (getDB().isSupportsInlineDistinct()) {
			q.addEntity(_FILTER_ENTITY_ALIAS, SAPEntryImpl.class);
		}
		else {
			q.addEntity(_FILTER_ENTITY_TABLE, SAPEntryImpl.class);
		}

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(companyId);

		qPos.add(defaultSAPEntry);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(sapEntry);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<SAPEntry> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the s a p entries where companyId = &#63; and defaultSAPEntry = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param defaultSAPEntry the default s a p entry
	 */
	@Override
	public void removeByC_D(long companyId, boolean defaultSAPEntry) {
		for (SAPEntry sapEntry : findByC_D(companyId, defaultSAPEntry,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(sapEntry);
		}
	}

	/**
	 * Returns the number of s a p entries where companyId = &#63; and defaultSAPEntry = &#63;.
	 *
	 * @param companyId the company ID
	 * @param defaultSAPEntry the default s a p entry
	 * @return the number of matching s a p entries
	 */
	@Override
	public int countByC_D(long companyId, boolean defaultSAPEntry) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_C_D;

		Object[] finderArgs = new Object[] { companyId, defaultSAPEntry };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_SAPENTRY_WHERE);

			query.append(_FINDER_COLUMN_C_D_COMPANYID_2);

			query.append(_FINDER_COLUMN_C_D_DEFAULTSAPENTRY_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				qPos.add(defaultSAPEntry);

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
	 * Returns the number of s a p entries that the user has permission to view where companyId = &#63; and defaultSAPEntry = &#63;.
	 *
	 * @param companyId the company ID
	 * @param defaultSAPEntry the default s a p entry
	 * @return the number of matching s a p entries that the user has permission to view
	 */
	@Override
	public int filterCountByC_D(long companyId, boolean defaultSAPEntry) {
		if (!InlineSQLHelperUtil.isEnabled()) {
			return countByC_D(companyId, defaultSAPEntry);
		}

		StringBundler query = new StringBundler(3);

		query.append(_FILTER_SQL_COUNT_SAPENTRY_WHERE);

		query.append(_FINDER_COLUMN_C_D_COMPANYID_2);

		query.append(_FINDER_COLUMN_C_D_DEFAULTSAPENTRY_2);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				SAPEntry.class.getName(), _FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME,
				com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(companyId);

			qPos.add(defaultSAPEntry);

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

	private static final String _FINDER_COLUMN_C_D_COMPANYID_2 = "sapEntry.companyId = ? AND ";
	private static final String _FINDER_COLUMN_C_D_DEFAULTSAPENTRY_2 = "sapEntry.defaultSAPEntry = ?";
	public static final FinderPath FINDER_PATH_FETCH_BY_C_N = new FinderPath(SAPEntryModelImpl.ENTITY_CACHE_ENABLED,
			SAPEntryModelImpl.FINDER_CACHE_ENABLED, SAPEntryImpl.class,
			FINDER_CLASS_NAME_ENTITY, "fetchByC_N",
			new String[] { Long.class.getName(), String.class.getName() },
			SAPEntryModelImpl.COMPANYID_COLUMN_BITMASK |
			SAPEntryModelImpl.NAME_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_C_N = new FinderPath(SAPEntryModelImpl.ENTITY_CACHE_ENABLED,
			SAPEntryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_N",
			new String[] { Long.class.getName(), String.class.getName() });

	/**
	 * Returns the s a p entry where companyId = &#63; and name = &#63; or throws a {@link com.liferay.service.access.policy.NoSuchEntryException} if it could not be found.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @return the matching s a p entry
	 * @throws com.liferay.service.access.policy.NoSuchEntryException if a matching s a p entry could not be found
	 */
	@Override
	public SAPEntry findByC_N(long companyId, String name)
		throws NoSuchEntryException {
		SAPEntry sapEntry = fetchByC_N(companyId, name);

		if (sapEntry == null) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("companyId=");
			msg.append(companyId);

			msg.append(", name=");
			msg.append(name);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchEntryException(msg.toString());
		}

		return sapEntry;
	}

	/**
	 * Returns the s a p entry where companyId = &#63; and name = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @return the matching s a p entry, or <code>null</code> if a matching s a p entry could not be found
	 */
	@Override
	public SAPEntry fetchByC_N(long companyId, String name) {
		return fetchByC_N(companyId, name, true);
	}

	/**
	 * Returns the s a p entry where companyId = &#63; and name = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the matching s a p entry, or <code>null</code> if a matching s a p entry could not be found
	 */
	@Override
	public SAPEntry fetchByC_N(long companyId, String name,
		boolean retrieveFromCache) {
		Object[] finderArgs = new Object[] { companyId, name };

		Object result = null;

		if (retrieveFromCache) {
			result = finderCache.getResult(FINDER_PATH_FETCH_BY_C_N,
					finderArgs, this);
		}

		if (result instanceof SAPEntry) {
			SAPEntry sapEntry = (SAPEntry)result;

			if ((companyId != sapEntry.getCompanyId()) ||
					!Validator.equals(name, sapEntry.getName())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_SELECT_SAPENTRY_WHERE);

			query.append(_FINDER_COLUMN_C_N_COMPANYID_2);

			boolean bindName = false;

			if (name == null) {
				query.append(_FINDER_COLUMN_C_N_NAME_1);
			}
			else if (name.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_C_N_NAME_3);
			}
			else {
				bindName = true;

				query.append(_FINDER_COLUMN_C_N_NAME_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				if (bindName) {
					qPos.add(StringUtil.toLowerCase(name));
				}

				List<SAPEntry> list = q.list();

				if (list.isEmpty()) {
					finderCache.putResult(FINDER_PATH_FETCH_BY_C_N, finderArgs,
						list);
				}
				else {
					if ((list.size() > 1) && _log.isWarnEnabled()) {
						_log.warn(
							"SAPEntryPersistenceImpl.fetchByC_N(long, String, boolean) with parameters (" +
							StringUtil.merge(finderArgs) +
							") yields a result set with more than 1 result. This violates the logical unique restriction. There is no order guarantee on which result is returned by this finder.");
					}

					SAPEntry sapEntry = list.get(0);

					result = sapEntry;

					cacheResult(sapEntry);

					if ((sapEntry.getCompanyId() != companyId) ||
							(sapEntry.getName() == null) ||
							!sapEntry.getName().equals(name)) {
						finderCache.putResult(FINDER_PATH_FETCH_BY_C_N,
							finderArgs, sapEntry);
					}
				}
			}
			catch (Exception e) {
				finderCache.removeResult(FINDER_PATH_FETCH_BY_C_N, finderArgs);

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
			return (SAPEntry)result;
		}
	}

	/**
	 * Removes the s a p entry where companyId = &#63; and name = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @return the s a p entry that was removed
	 */
	@Override
	public SAPEntry removeByC_N(long companyId, String name)
		throws NoSuchEntryException {
		SAPEntry sapEntry = findByC_N(companyId, name);

		return remove(sapEntry);
	}

	/**
	 * Returns the number of s a p entries where companyId = &#63; and name = &#63;.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @return the number of matching s a p entries
	 */
	@Override
	public int countByC_N(long companyId, String name) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_C_N;

		Object[] finderArgs = new Object[] { companyId, name };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_SAPENTRY_WHERE);

			query.append(_FINDER_COLUMN_C_N_COMPANYID_2);

			boolean bindName = false;

			if (name == null) {
				query.append(_FINDER_COLUMN_C_N_NAME_1);
			}
			else if (name.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_C_N_NAME_3);
			}
			else {
				bindName = true;

				query.append(_FINDER_COLUMN_C_N_NAME_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				if (bindName) {
					qPos.add(StringUtil.toLowerCase(name));
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

	private static final String _FINDER_COLUMN_C_N_COMPANYID_2 = "sapEntry.companyId = ? AND ";
	private static final String _FINDER_COLUMN_C_N_NAME_1 = "sapEntry.name IS NULL";
	private static final String _FINDER_COLUMN_C_N_NAME_2 = "lower(sapEntry.name) = ?";
	private static final String _FINDER_COLUMN_C_N_NAME_3 = "(sapEntry.name IS NULL OR sapEntry.name = '')";

	public SAPEntryPersistenceImpl() {
		setModelClass(SAPEntry.class);
	}

	/**
	 * Caches the s a p entry in the entity cache if it is enabled.
	 *
	 * @param sapEntry the s a p entry
	 */
	@Override
	public void cacheResult(SAPEntry sapEntry) {
		entityCache.putResult(SAPEntryModelImpl.ENTITY_CACHE_ENABLED,
			SAPEntryImpl.class, sapEntry.getPrimaryKey(), sapEntry);

		finderCache.putResult(FINDER_PATH_FETCH_BY_C_N,
			new Object[] { sapEntry.getCompanyId(), sapEntry.getName() },
			sapEntry);

		sapEntry.resetOriginalValues();
	}

	/**
	 * Caches the s a p entries in the entity cache if it is enabled.
	 *
	 * @param sapEntries the s a p entries
	 */
	@Override
	public void cacheResult(List<SAPEntry> sapEntries) {
		for (SAPEntry sapEntry : sapEntries) {
			if (entityCache.getResult(SAPEntryModelImpl.ENTITY_CACHE_ENABLED,
						SAPEntryImpl.class, sapEntry.getPrimaryKey()) == null) {
				cacheResult(sapEntry);
			}
			else {
				sapEntry.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all s a p entries.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(SAPEntryImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the s a p entry.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(SAPEntry sapEntry) {
		entityCache.removeResult(SAPEntryModelImpl.ENTITY_CACHE_ENABLED,
			SAPEntryImpl.class, sapEntry.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache((SAPEntryModelImpl)sapEntry);
	}

	@Override
	public void clearCache(List<SAPEntry> sapEntries) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (SAPEntry sapEntry : sapEntries) {
			entityCache.removeResult(SAPEntryModelImpl.ENTITY_CACHE_ENABLED,
				SAPEntryImpl.class, sapEntry.getPrimaryKey());

			clearUniqueFindersCache((SAPEntryModelImpl)sapEntry);
		}
	}

	protected void cacheUniqueFindersCache(
		SAPEntryModelImpl sapEntryModelImpl, boolean isNew) {
		if (isNew) {
			Object[] args = new Object[] {
					sapEntryModelImpl.getCompanyId(),
					sapEntryModelImpl.getName()
				};

			finderCache.putResult(FINDER_PATH_COUNT_BY_C_N, args,
				Long.valueOf(1));
			finderCache.putResult(FINDER_PATH_FETCH_BY_C_N, args,
				sapEntryModelImpl);
		}
		else {
			if ((sapEntryModelImpl.getColumnBitmask() &
					FINDER_PATH_FETCH_BY_C_N.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						sapEntryModelImpl.getCompanyId(),
						sapEntryModelImpl.getName()
					};

				finderCache.putResult(FINDER_PATH_COUNT_BY_C_N, args,
					Long.valueOf(1));
				finderCache.putResult(FINDER_PATH_FETCH_BY_C_N, args,
					sapEntryModelImpl);
			}
		}
	}

	protected void clearUniqueFindersCache(SAPEntryModelImpl sapEntryModelImpl) {
		Object[] args = new Object[] {
				sapEntryModelImpl.getCompanyId(), sapEntryModelImpl.getName()
			};

		finderCache.removeResult(FINDER_PATH_COUNT_BY_C_N, args);
		finderCache.removeResult(FINDER_PATH_FETCH_BY_C_N, args);

		if ((sapEntryModelImpl.getColumnBitmask() &
				FINDER_PATH_FETCH_BY_C_N.getColumnBitmask()) != 0) {
			args = new Object[] {
					sapEntryModelImpl.getOriginalCompanyId(),
					sapEntryModelImpl.getOriginalName()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_C_N, args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_C_N, args);
		}
	}

	/**
	 * Creates a new s a p entry with the primary key. Does not add the s a p entry to the database.
	 *
	 * @param sapEntryId the primary key for the new s a p entry
	 * @return the new s a p entry
	 */
	@Override
	public SAPEntry create(long sapEntryId) {
		SAPEntry sapEntry = new SAPEntryImpl();

		sapEntry.setNew(true);
		sapEntry.setPrimaryKey(sapEntryId);

		String uuid = PortalUUIDUtil.generate();

		sapEntry.setUuid(uuid);

		sapEntry.setCompanyId(companyProvider.getCompanyId());

		return sapEntry;
	}

	/**
	 * Removes the s a p entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param sapEntryId the primary key of the s a p entry
	 * @return the s a p entry that was removed
	 * @throws com.liferay.service.access.policy.NoSuchEntryException if a s a p entry with the primary key could not be found
	 */
	@Override
	public SAPEntry remove(long sapEntryId) throws NoSuchEntryException {
		return remove((Serializable)sapEntryId);
	}

	/**
	 * Removes the s a p entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the s a p entry
	 * @return the s a p entry that was removed
	 * @throws com.liferay.service.access.policy.NoSuchEntryException if a s a p entry with the primary key could not be found
	 */
	@Override
	public SAPEntry remove(Serializable primaryKey) throws NoSuchEntryException {
		Session session = null;

		try {
			session = openSession();

			SAPEntry sapEntry = (SAPEntry)session.get(SAPEntryImpl.class,
					primaryKey);

			if (sapEntry == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchEntryException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(sapEntry);
		}
		catch (NoSuchEntryException nsee) {
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
	protected SAPEntry removeImpl(SAPEntry sapEntry) {
		sapEntry = toUnwrappedModel(sapEntry);

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(sapEntry)) {
				sapEntry = (SAPEntry)session.get(SAPEntryImpl.class,
						sapEntry.getPrimaryKeyObj());
			}

			if (sapEntry != null) {
				session.delete(sapEntry);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (sapEntry != null) {
			clearCache(sapEntry);
		}

		return sapEntry;
	}

	@Override
	public SAPEntry updateImpl(SAPEntry sapEntry) {
		sapEntry = toUnwrappedModel(sapEntry);

		boolean isNew = sapEntry.isNew();

		SAPEntryModelImpl sapEntryModelImpl = (SAPEntryModelImpl)sapEntry;

		if (Validator.isNull(sapEntry.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			sapEntry.setUuid(uuid);
		}

		ServiceContext serviceContext = ServiceContextThreadLocal.getServiceContext();

		Date now = new Date();

		if (isNew && (sapEntry.getCreateDate() == null)) {
			if (serviceContext == null) {
				sapEntry.setCreateDate(now);
			}
			else {
				sapEntry.setCreateDate(serviceContext.getCreateDate(now));
			}
		}

		if (!sapEntryModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				sapEntry.setModifiedDate(now);
			}
			else {
				sapEntry.setModifiedDate(serviceContext.getModifiedDate(now));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (sapEntry.isNew()) {
				session.save(sapEntry);

				sapEntry.setNew(false);
			}
			else {
				sapEntry = (SAPEntry)session.merge(sapEntry);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (isNew || !SAPEntryModelImpl.COLUMN_BITMASK_ENABLED) {
			finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}

		else {
			if ((sapEntryModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] { sapEntryModelImpl.getOriginalUuid() };

				finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID,
					args);

				args = new Object[] { sapEntryModelImpl.getUuid() };

				finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID,
					args);
			}

			if ((sapEntryModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						sapEntryModelImpl.getOriginalUuid(),
						sapEntryModelImpl.getOriginalCompanyId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID_C, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C,
					args);

				args = new Object[] {
						sapEntryModelImpl.getUuid(),
						sapEntryModelImpl.getCompanyId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID_C, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C,
					args);
			}

			if ((sapEntryModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						sapEntryModelImpl.getOriginalCompanyId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_COMPANYID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID,
					args);

				args = new Object[] { sapEntryModelImpl.getCompanyId() };

				finderCache.removeResult(FINDER_PATH_COUNT_BY_COMPANYID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID,
					args);
			}

			if ((sapEntryModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_D.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						sapEntryModelImpl.getOriginalCompanyId(),
						sapEntryModelImpl.getOriginalDefaultSAPEntry()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_C_D, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_D,
					args);

				args = new Object[] {
						sapEntryModelImpl.getCompanyId(),
						sapEntryModelImpl.getDefaultSAPEntry()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_C_D, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_D,
					args);
			}
		}

		entityCache.putResult(SAPEntryModelImpl.ENTITY_CACHE_ENABLED,
			SAPEntryImpl.class, sapEntry.getPrimaryKey(), sapEntry, false);

		clearUniqueFindersCache(sapEntryModelImpl);
		cacheUniqueFindersCache(sapEntryModelImpl, isNew);

		sapEntry.resetOriginalValues();

		return sapEntry;
	}

	protected SAPEntry toUnwrappedModel(SAPEntry sapEntry) {
		if (sapEntry instanceof SAPEntryImpl) {
			return sapEntry;
		}

		SAPEntryImpl sapEntryImpl = new SAPEntryImpl();

		sapEntryImpl.setNew(sapEntry.isNew());
		sapEntryImpl.setPrimaryKey(sapEntry.getPrimaryKey());

		sapEntryImpl.setUuid(sapEntry.getUuid());
		sapEntryImpl.setSapEntryId(sapEntry.getSapEntryId());
		sapEntryImpl.setCompanyId(sapEntry.getCompanyId());
		sapEntryImpl.setUserId(sapEntry.getUserId());
		sapEntryImpl.setUserName(sapEntry.getUserName());
		sapEntryImpl.setCreateDate(sapEntry.getCreateDate());
		sapEntryImpl.setModifiedDate(sapEntry.getModifiedDate());
		sapEntryImpl.setAllowedServiceSignatures(sapEntry.getAllowedServiceSignatures());
		sapEntryImpl.setDefaultSAPEntry(sapEntry.isDefaultSAPEntry());
		sapEntryImpl.setEnabled(sapEntry.isEnabled());
		sapEntryImpl.setName(sapEntry.getName());
		sapEntryImpl.setTitle(sapEntry.getTitle());

		return sapEntryImpl;
	}

	/**
	 * Returns the s a p entry with the primary key or throws a {@link com.liferay.portal.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the s a p entry
	 * @return the s a p entry
	 * @throws com.liferay.service.access.policy.NoSuchEntryException if a s a p entry with the primary key could not be found
	 */
	@Override
	public SAPEntry findByPrimaryKey(Serializable primaryKey)
		throws NoSuchEntryException {
		SAPEntry sapEntry = fetchByPrimaryKey(primaryKey);

		if (sapEntry == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchEntryException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				primaryKey);
		}

		return sapEntry;
	}

	/**
	 * Returns the s a p entry with the primary key or throws a {@link com.liferay.service.access.policy.NoSuchEntryException} if it could not be found.
	 *
	 * @param sapEntryId the primary key of the s a p entry
	 * @return the s a p entry
	 * @throws com.liferay.service.access.policy.NoSuchEntryException if a s a p entry with the primary key could not be found
	 */
	@Override
	public SAPEntry findByPrimaryKey(long sapEntryId)
		throws NoSuchEntryException {
		return findByPrimaryKey((Serializable)sapEntryId);
	}

	/**
	 * Returns the s a p entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the s a p entry
	 * @return the s a p entry, or <code>null</code> if a s a p entry with the primary key could not be found
	 */
	@Override
	public SAPEntry fetchByPrimaryKey(Serializable primaryKey) {
		SAPEntry sapEntry = (SAPEntry)entityCache.getResult(SAPEntryModelImpl.ENTITY_CACHE_ENABLED,
				SAPEntryImpl.class, primaryKey);

		if (sapEntry == _nullSAPEntry) {
			return null;
		}

		if (sapEntry == null) {
			Session session = null;

			try {
				session = openSession();

				sapEntry = (SAPEntry)session.get(SAPEntryImpl.class, primaryKey);

				if (sapEntry != null) {
					cacheResult(sapEntry);
				}
				else {
					entityCache.putResult(SAPEntryModelImpl.ENTITY_CACHE_ENABLED,
						SAPEntryImpl.class, primaryKey, _nullSAPEntry);
				}
			}
			catch (Exception e) {
				entityCache.removeResult(SAPEntryModelImpl.ENTITY_CACHE_ENABLED,
					SAPEntryImpl.class, primaryKey);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return sapEntry;
	}

	/**
	 * Returns the s a p entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param sapEntryId the primary key of the s a p entry
	 * @return the s a p entry, or <code>null</code> if a s a p entry with the primary key could not be found
	 */
	@Override
	public SAPEntry fetchByPrimaryKey(long sapEntryId) {
		return fetchByPrimaryKey((Serializable)sapEntryId);
	}

	@Override
	public Map<Serializable, SAPEntry> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {
		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, SAPEntry> map = new HashMap<Serializable, SAPEntry>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			SAPEntry sapEntry = fetchByPrimaryKey(primaryKey);

			if (sapEntry != null) {
				map.put(primaryKey, sapEntry);
			}

			return map;
		}

		Set<Serializable> uncachedPrimaryKeys = null;

		for (Serializable primaryKey : primaryKeys) {
			SAPEntry sapEntry = (SAPEntry)entityCache.getResult(SAPEntryModelImpl.ENTITY_CACHE_ENABLED,
					SAPEntryImpl.class, primaryKey);

			if (sapEntry == null) {
				if (uncachedPrimaryKeys == null) {
					uncachedPrimaryKeys = new HashSet<Serializable>();
				}

				uncachedPrimaryKeys.add(primaryKey);
			}
			else {
				map.put(primaryKey, sapEntry);
			}
		}

		if (uncachedPrimaryKeys == null) {
			return map;
		}

		StringBundler query = new StringBundler((uncachedPrimaryKeys.size() * 2) +
				1);

		query.append(_SQL_SELECT_SAPENTRY_WHERE_PKS_IN);

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

			for (SAPEntry sapEntry : (List<SAPEntry>)q.list()) {
				map.put(sapEntry.getPrimaryKeyObj(), sapEntry);

				cacheResult(sapEntry);

				uncachedPrimaryKeys.remove(sapEntry.getPrimaryKeyObj());
			}

			for (Serializable primaryKey : uncachedPrimaryKeys) {
				entityCache.putResult(SAPEntryModelImpl.ENTITY_CACHE_ENABLED,
					SAPEntryImpl.class, primaryKey, _nullSAPEntry);
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
	 * Returns all the s a p entries.
	 *
	 * @return the s a p entries
	 */
	@Override
	public List<SAPEntry> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the s a p entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SAPEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of s a p entries
	 * @param end the upper bound of the range of s a p entries (not inclusive)
	 * @return the range of s a p entries
	 */
	@Override
	public List<SAPEntry> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the s a p entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SAPEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of s a p entries
	 * @param end the upper bound of the range of s a p entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of s a p entries
	 */
	@Override
	public List<SAPEntry> findAll(int start, int end,
		OrderByComparator<SAPEntry> orderByComparator) {
		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the s a p entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link SAPEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of s a p entries
	 * @param end the upper bound of the range of s a p entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of s a p entries
	 */
	@Override
	public List<SAPEntry> findAll(int start, int end,
		OrderByComparator<SAPEntry> orderByComparator, boolean retrieveFromCache) {
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

		List<SAPEntry> list = null;

		if (retrieveFromCache) {
			list = (List<SAPEntry>)finderCache.getResult(finderPath,
					finderArgs, this);
		}

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 3));

				query.append(_SQL_SELECT_SAPENTRY);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_SAPENTRY;

				if (pagination) {
					sql = sql.concat(SAPEntryModelImpl.ORDER_BY_JPQL);
				}
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (!pagination) {
					list = (List<SAPEntry>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<SAPEntry>)QueryUtil.list(q, getDialect(),
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
	 * Removes all the s a p entries from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (SAPEntry sapEntry : findAll()) {
			remove(sapEntry);
		}
	}

	/**
	 * Returns the number of s a p entries.
	 *
	 * @return the number of s a p entries
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_SAPENTRY);

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
		return SAPEntryModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the s a p entry persistence.
	 */
	public void afterPropertiesSet() {
	}

	public void destroy() {
		entityCache.removeCache(SAPEntryImpl.class.getName());
		finderCache.removeCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@ServiceReference(type = CompanyProvider.class)
	protected CompanyProvider companyProvider;
	@ServiceReference(type = EntityCache.class)
	protected EntityCache entityCache;
	@ServiceReference(type = FinderCache.class)
	protected FinderCache finderCache;
	private static final String _SQL_SELECT_SAPENTRY = "SELECT sapEntry FROM SAPEntry sapEntry";
	private static final String _SQL_SELECT_SAPENTRY_WHERE_PKS_IN = "SELECT sapEntry FROM SAPEntry sapEntry WHERE sapEntryId IN (";
	private static final String _SQL_SELECT_SAPENTRY_WHERE = "SELECT sapEntry FROM SAPEntry sapEntry WHERE ";
	private static final String _SQL_COUNT_SAPENTRY = "SELECT COUNT(sapEntry) FROM SAPEntry sapEntry";
	private static final String _SQL_COUNT_SAPENTRY_WHERE = "SELECT COUNT(sapEntry) FROM SAPEntry sapEntry WHERE ";
	private static final String _FILTER_ENTITY_TABLE_FILTER_PK_COLUMN = "sapEntry.sapEntryId";
	private static final String _FILTER_SQL_SELECT_SAPENTRY_WHERE = "SELECT DISTINCT {sapEntry.*} FROM SAPEntry sapEntry WHERE ";
	private static final String _FILTER_SQL_SELECT_SAPENTRY_NO_INLINE_DISTINCT_WHERE_1 =
		"SELECT {SAPEntry.*} FROM (SELECT DISTINCT sapEntry.sapEntryId FROM SAPEntry sapEntry WHERE ";
	private static final String _FILTER_SQL_SELECT_SAPENTRY_NO_INLINE_DISTINCT_WHERE_2 =
		") TEMP_TABLE INNER JOIN SAPEntry ON TEMP_TABLE.sapEntryId = SAPEntry.sapEntryId";
	private static final String _FILTER_SQL_COUNT_SAPENTRY_WHERE = "SELECT COUNT(DISTINCT sapEntry.sapEntryId) AS COUNT_VALUE FROM SAPEntry sapEntry WHERE ";
	private static final String _FILTER_ENTITY_ALIAS = "sapEntry";
	private static final String _FILTER_ENTITY_TABLE = "SAPEntry";
	private static final String _ORDER_BY_ENTITY_ALIAS = "sapEntry.";
	private static final String _ORDER_BY_ENTITY_TABLE = "SAPEntry.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No SAPEntry exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No SAPEntry exists with the key {";
	private static final Log _log = LogFactoryUtil.getLog(SAPEntryPersistenceImpl.class);
	private static final Set<String> _badColumnNames = SetUtil.fromArray(new String[] {
				"uuid"
			});
	private static final SAPEntry _nullSAPEntry = new SAPEntryImpl() {
			@Override
			public Object clone() {
				return this;
			}

			@Override
			public CacheModel<SAPEntry> toCacheModel() {
				return _nullSAPEntryCacheModel;
			}
		};

	private static final CacheModel<SAPEntry> _nullSAPEntryCacheModel = new CacheModel<SAPEntry>() {
			@Override
			public SAPEntry toEntityModel() {
				return _nullSAPEntry;
			}
		};
}