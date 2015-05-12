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

package com.liferay.portlet.dynamicdatamapping.service.persistence.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.bean.BeanReference;
import com.liferay.portal.kernel.dao.orm.EntityCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.SQLQuery;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;
import com.liferay.portal.model.CacheModel;
import com.liferay.portal.security.permission.InlineSQLHelperUtil;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.service.persistence.impl.TableMapper;
import com.liferay.portal.service.persistence.impl.TableMapperFactory;

import com.liferay.portlet.documentlibrary.service.persistence.DLFileEntryTypePersistence;
import com.liferay.portlet.dynamicdatamapping.NoSuchStructureException;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructure;
import com.liferay.portlet.dynamicdatamapping.model.impl.DDMStructureImpl;
import com.liferay.portlet.dynamicdatamapping.model.impl.DDMStructureModelImpl;
import com.liferay.portlet.dynamicdatamapping.service.persistence.DDMStructurePersistence;

import java.io.Serializable;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The persistence implementation for the d d m structure service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see DDMStructurePersistence
 * @see com.liferay.portlet.dynamicdatamapping.service.persistence.DDMStructureUtil
 * @generated
 */
@ProviderType
public class DDMStructurePersistenceImpl extends BasePersistenceImpl<DDMStructure>
	implements DDMStructurePersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link DDMStructureUtil} to access the d d m structure persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = DDMStructureImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(DDMStructureModelImpl.ENTITY_CACHE_ENABLED,
			DDMStructureModelImpl.FINDER_CACHE_ENABLED, DDMStructureImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(DDMStructureModelImpl.ENTITY_CACHE_ENABLED,
			DDMStructureModelImpl.FINDER_CACHE_ENABLED, DDMStructureImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(DDMStructureModelImpl.ENTITY_CACHE_ENABLED,
			DDMStructureModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_UUID = new FinderPath(DDMStructureModelImpl.ENTITY_CACHE_ENABLED,
			DDMStructureModelImpl.FINDER_CACHE_ENABLED, DDMStructureImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUuid",
			new String[] {
				String.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID = new FinderPath(DDMStructureModelImpl.ENTITY_CACHE_ENABLED,
			DDMStructureModelImpl.FINDER_CACHE_ENABLED, DDMStructureImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid",
			new String[] { String.class.getName() },
			DDMStructureModelImpl.UUID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID = new FinderPath(DDMStructureModelImpl.ENTITY_CACHE_ENABLED,
			DDMStructureModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUuid",
			new String[] { String.class.getName() });

	/**
	 * Returns all the d d m structures where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching d d m structures
	 */
	@Override
	public List<DDMStructure> findByUuid(String uuid) {
		return findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the d d m structures where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMStructureModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of d d m structures
	 * @param end the upper bound of the range of d d m structures (not inclusive)
	 * @return the range of matching d d m structures
	 */
	@Override
	public List<DDMStructure> findByUuid(String uuid, int start, int end) {
		return findByUuid(uuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the d d m structures where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMStructureModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of d d m structures
	 * @param end the upper bound of the range of d d m structures (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching d d m structures
	 */
	@Override
	public List<DDMStructure> findByUuid(String uuid, int start, int end,
		OrderByComparator<DDMStructure> orderByComparator) {
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

		List<DDMStructure> list = (List<DDMStructure>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (DDMStructure ddmStructure : list) {
				if (!Validator.equals(uuid, ddmStructure.getUuid())) {
					list = null;

					break;
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

			query.append(_SQL_SELECT_DDMSTRUCTURE_WHERE);

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
				query.append(DDMStructureModelImpl.ORDER_BY_JPQL);
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
					list = (List<DDMStructure>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<DDMStructure>)QueryUtil.list(q, getDialect(),
							start, end);
				}

				cacheResult(list);

				FinderCacheUtil.putResult(finderPath, finderArgs, list);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first d d m structure in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching d d m structure
	 * @throws NoSuchStructureException if a matching d d m structure could not be found
	 */
	@Override
	public DDMStructure findByUuid_First(String uuid,
		OrderByComparator<DDMStructure> orderByComparator)
		throws NoSuchStructureException {
		DDMStructure ddmStructure = fetchByUuid_First(uuid, orderByComparator);

		if (ddmStructure != null) {
			return ddmStructure;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchStructureException(msg.toString());
	}

	/**
	 * Returns the first d d m structure in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching d d m structure, or <code>null</code> if a matching d d m structure could not be found
	 */
	@Override
	public DDMStructure fetchByUuid_First(String uuid,
		OrderByComparator<DDMStructure> orderByComparator) {
		List<DDMStructure> list = findByUuid(uuid, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last d d m structure in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching d d m structure
	 * @throws NoSuchStructureException if a matching d d m structure could not be found
	 */
	@Override
	public DDMStructure findByUuid_Last(String uuid,
		OrderByComparator<DDMStructure> orderByComparator)
		throws NoSuchStructureException {
		DDMStructure ddmStructure = fetchByUuid_Last(uuid, orderByComparator);

		if (ddmStructure != null) {
			return ddmStructure;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchStructureException(msg.toString());
	}

	/**
	 * Returns the last d d m structure in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching d d m structure, or <code>null</code> if a matching d d m structure could not be found
	 */
	@Override
	public DDMStructure fetchByUuid_Last(String uuid,
		OrderByComparator<DDMStructure> orderByComparator) {
		int count = countByUuid(uuid);

		if (count == 0) {
			return null;
		}

		List<DDMStructure> list = findByUuid(uuid, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the d d m structures before and after the current d d m structure in the ordered set where uuid = &#63;.
	 *
	 * @param structureId the primary key of the current d d m structure
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next d d m structure
	 * @throws NoSuchStructureException if a d d m structure with the primary key could not be found
	 */
	@Override
	public DDMStructure[] findByUuid_PrevAndNext(long structureId, String uuid,
		OrderByComparator<DDMStructure> orderByComparator)
		throws NoSuchStructureException {
		DDMStructure ddmStructure = findByPrimaryKey(structureId);

		Session session = null;

		try {
			session = openSession();

			DDMStructure[] array = new DDMStructureImpl[3];

			array[0] = getByUuid_PrevAndNext(session, ddmStructure, uuid,
					orderByComparator, true);

			array[1] = ddmStructure;

			array[2] = getByUuid_PrevAndNext(session, ddmStructure, uuid,
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

	protected DDMStructure getByUuid_PrevAndNext(Session session,
		DDMStructure ddmStructure, String uuid,
		OrderByComparator<DDMStructure> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_DDMSTRUCTURE_WHERE);

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
			query.append(DDMStructureModelImpl.ORDER_BY_JPQL);
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
			Object[] values = orderByComparator.getOrderByConditionValues(ddmStructure);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<DDMStructure> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the d d m structures where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	@Override
	public void removeByUuid(String uuid) {
		for (DDMStructure ddmStructure : findByUuid(uuid, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS, null)) {
			remove(ddmStructure);
		}
	}

	/**
	 * Returns the number of d d m structures where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching d d m structures
	 */
	@Override
	public int countByUuid(String uuid) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_UUID;

		Object[] finderArgs = new Object[] { uuid };

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs,
				this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_DDMSTRUCTURE_WHERE);

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

				FinderCacheUtil.putResult(finderPath, finderArgs, count);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_UUID_UUID_1 = "ddmStructure.uuid IS NULL";
	private static final String _FINDER_COLUMN_UUID_UUID_2 = "ddmStructure.uuid = ?";
	private static final String _FINDER_COLUMN_UUID_UUID_3 = "(ddmStructure.uuid IS NULL OR ddmStructure.uuid = '')";
	public static final FinderPath FINDER_PATH_FETCH_BY_UUID_G = new FinderPath(DDMStructureModelImpl.ENTITY_CACHE_ENABLED,
			DDMStructureModelImpl.FINDER_CACHE_ENABLED, DDMStructureImpl.class,
			FINDER_CLASS_NAME_ENTITY, "fetchByUUID_G",
			new String[] { String.class.getName(), Long.class.getName() },
			DDMStructureModelImpl.UUID_COLUMN_BITMASK |
			DDMStructureModelImpl.GROUPID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID_G = new FinderPath(DDMStructureModelImpl.ENTITY_CACHE_ENABLED,
			DDMStructureModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUUID_G",
			new String[] { String.class.getName(), Long.class.getName() });

	/**
	 * Returns the d d m structure where uuid = &#63; and groupId = &#63; or throws a {@link NoSuchStructureException} if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching d d m structure
	 * @throws NoSuchStructureException if a matching d d m structure could not be found
	 */
	@Override
	public DDMStructure findByUUID_G(String uuid, long groupId)
		throws NoSuchStructureException {
		DDMStructure ddmStructure = fetchByUUID_G(uuid, groupId);

		if (ddmStructure == null) {
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

			throw new NoSuchStructureException(msg.toString());
		}

		return ddmStructure;
	}

	/**
	 * Returns the d d m structure where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching d d m structure, or <code>null</code> if a matching d d m structure could not be found
	 */
	@Override
	public DDMStructure fetchByUUID_G(String uuid, long groupId) {
		return fetchByUUID_G(uuid, groupId, true);
	}

	/**
	 * Returns the d d m structure where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param retrieveFromCache whether to use the finder cache
	 * @return the matching d d m structure, or <code>null</code> if a matching d d m structure could not be found
	 */
	@Override
	public DDMStructure fetchByUUID_G(String uuid, long groupId,
		boolean retrieveFromCache) {
		Object[] finderArgs = new Object[] { uuid, groupId };

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_UUID_G,
					finderArgs, this);
		}

		if (result instanceof DDMStructure) {
			DDMStructure ddmStructure = (DDMStructure)result;

			if (!Validator.equals(uuid, ddmStructure.getUuid()) ||
					(groupId != ddmStructure.getGroupId())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_SELECT_DDMSTRUCTURE_WHERE);

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

				List<DDMStructure> list = q.list();

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_UUID_G,
						finderArgs, list);
				}
				else {
					DDMStructure ddmStructure = list.get(0);

					result = ddmStructure;

					cacheResult(ddmStructure);

					if ((ddmStructure.getUuid() == null) ||
							!ddmStructure.getUuid().equals(uuid) ||
							(ddmStructure.getGroupId() != groupId)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_UUID_G,
							finderArgs, ddmStructure);
					}
				}
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_UUID_G,
					finderArgs);

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
			return (DDMStructure)result;
		}
	}

	/**
	 * Removes the d d m structure where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the d d m structure that was removed
	 */
	@Override
	public DDMStructure removeByUUID_G(String uuid, long groupId)
		throws NoSuchStructureException {
		DDMStructure ddmStructure = findByUUID_G(uuid, groupId);

		return remove(ddmStructure);
	}

	/**
	 * Returns the number of d d m structures where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching d d m structures
	 */
	@Override
	public int countByUUID_G(String uuid, long groupId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_UUID_G;

		Object[] finderArgs = new Object[] { uuid, groupId };

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs,
				this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_DDMSTRUCTURE_WHERE);

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

				FinderCacheUtil.putResult(finderPath, finderArgs, count);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_UUID_G_UUID_1 = "ddmStructure.uuid IS NULL AND ";
	private static final String _FINDER_COLUMN_UUID_G_UUID_2 = "ddmStructure.uuid = ? AND ";
	private static final String _FINDER_COLUMN_UUID_G_UUID_3 = "(ddmStructure.uuid IS NULL OR ddmStructure.uuid = '') AND ";
	private static final String _FINDER_COLUMN_UUID_G_GROUPID_2 = "ddmStructure.groupId = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_UUID_C = new FinderPath(DDMStructureModelImpl.ENTITY_CACHE_ENABLED,
			DDMStructureModelImpl.FINDER_CACHE_ENABLED, DDMStructureImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUuid_C",
			new String[] {
				String.class.getName(), Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C =
		new FinderPath(DDMStructureModelImpl.ENTITY_CACHE_ENABLED,
			DDMStructureModelImpl.FINDER_CACHE_ENABLED, DDMStructureImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid_C",
			new String[] { String.class.getName(), Long.class.getName() },
			DDMStructureModelImpl.UUID_COLUMN_BITMASK |
			DDMStructureModelImpl.COMPANYID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID_C = new FinderPath(DDMStructureModelImpl.ENTITY_CACHE_ENABLED,
			DDMStructureModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUuid_C",
			new String[] { String.class.getName(), Long.class.getName() });

	/**
	 * Returns all the d d m structures where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching d d m structures
	 */
	@Override
	public List<DDMStructure> findByUuid_C(String uuid, long companyId) {
		return findByUuid_C(uuid, companyId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the d d m structures where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMStructureModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of d d m structures
	 * @param end the upper bound of the range of d d m structures (not inclusive)
	 * @return the range of matching d d m structures
	 */
	@Override
	public List<DDMStructure> findByUuid_C(String uuid, long companyId,
		int start, int end) {
		return findByUuid_C(uuid, companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the d d m structures where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMStructureModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of d d m structures
	 * @param end the upper bound of the range of d d m structures (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching d d m structures
	 */
	@Override
	public List<DDMStructure> findByUuid_C(String uuid, long companyId,
		int start, int end, OrderByComparator<DDMStructure> orderByComparator) {
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

		List<DDMStructure> list = (List<DDMStructure>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (DDMStructure ddmStructure : list) {
				if (!Validator.equals(uuid, ddmStructure.getUuid()) ||
						(companyId != ddmStructure.getCompanyId())) {
					list = null;

					break;
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

			query.append(_SQL_SELECT_DDMSTRUCTURE_WHERE);

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
				query.append(DDMStructureModelImpl.ORDER_BY_JPQL);
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
					list = (List<DDMStructure>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<DDMStructure>)QueryUtil.list(q, getDialect(),
							start, end);
				}

				cacheResult(list);

				FinderCacheUtil.putResult(finderPath, finderArgs, list);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first d d m structure in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching d d m structure
	 * @throws NoSuchStructureException if a matching d d m structure could not be found
	 */
	@Override
	public DDMStructure findByUuid_C_First(String uuid, long companyId,
		OrderByComparator<DDMStructure> orderByComparator)
		throws NoSuchStructureException {
		DDMStructure ddmStructure = fetchByUuid_C_First(uuid, companyId,
				orderByComparator);

		if (ddmStructure != null) {
			return ddmStructure;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(", companyId=");
		msg.append(companyId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchStructureException(msg.toString());
	}

	/**
	 * Returns the first d d m structure in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching d d m structure, or <code>null</code> if a matching d d m structure could not be found
	 */
	@Override
	public DDMStructure fetchByUuid_C_First(String uuid, long companyId,
		OrderByComparator<DDMStructure> orderByComparator) {
		List<DDMStructure> list = findByUuid_C(uuid, companyId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last d d m structure in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching d d m structure
	 * @throws NoSuchStructureException if a matching d d m structure could not be found
	 */
	@Override
	public DDMStructure findByUuid_C_Last(String uuid, long companyId,
		OrderByComparator<DDMStructure> orderByComparator)
		throws NoSuchStructureException {
		DDMStructure ddmStructure = fetchByUuid_C_Last(uuid, companyId,
				orderByComparator);

		if (ddmStructure != null) {
			return ddmStructure;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(", companyId=");
		msg.append(companyId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchStructureException(msg.toString());
	}

	/**
	 * Returns the last d d m structure in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching d d m structure, or <code>null</code> if a matching d d m structure could not be found
	 */
	@Override
	public DDMStructure fetchByUuid_C_Last(String uuid, long companyId,
		OrderByComparator<DDMStructure> orderByComparator) {
		int count = countByUuid_C(uuid, companyId);

		if (count == 0) {
			return null;
		}

		List<DDMStructure> list = findByUuid_C(uuid, companyId, count - 1,
				count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the d d m structures before and after the current d d m structure in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param structureId the primary key of the current d d m structure
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next d d m structure
	 * @throws NoSuchStructureException if a d d m structure with the primary key could not be found
	 */
	@Override
	public DDMStructure[] findByUuid_C_PrevAndNext(long structureId,
		String uuid, long companyId,
		OrderByComparator<DDMStructure> orderByComparator)
		throws NoSuchStructureException {
		DDMStructure ddmStructure = findByPrimaryKey(structureId);

		Session session = null;

		try {
			session = openSession();

			DDMStructure[] array = new DDMStructureImpl[3];

			array[0] = getByUuid_C_PrevAndNext(session, ddmStructure, uuid,
					companyId, orderByComparator, true);

			array[1] = ddmStructure;

			array[2] = getByUuid_C_PrevAndNext(session, ddmStructure, uuid,
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

	protected DDMStructure getByUuid_C_PrevAndNext(Session session,
		DDMStructure ddmStructure, String uuid, long companyId,
		OrderByComparator<DDMStructure> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_DDMSTRUCTURE_WHERE);

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
			query.append(DDMStructureModelImpl.ORDER_BY_JPQL);
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
			Object[] values = orderByComparator.getOrderByConditionValues(ddmStructure);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<DDMStructure> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the d d m structures where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	@Override
	public void removeByUuid_C(String uuid, long companyId) {
		for (DDMStructure ddmStructure : findByUuid_C(uuid, companyId,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(ddmStructure);
		}
	}

	/**
	 * Returns the number of d d m structures where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching d d m structures
	 */
	@Override
	public int countByUuid_C(String uuid, long companyId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_UUID_C;

		Object[] finderArgs = new Object[] { uuid, companyId };

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs,
				this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_DDMSTRUCTURE_WHERE);

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

				FinderCacheUtil.putResult(finderPath, finderArgs, count);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_UUID_C_UUID_1 = "ddmStructure.uuid IS NULL AND ";
	private static final String _FINDER_COLUMN_UUID_C_UUID_2 = "ddmStructure.uuid = ? AND ";
	private static final String _FINDER_COLUMN_UUID_C_UUID_3 = "(ddmStructure.uuid IS NULL OR ddmStructure.uuid = '') AND ";
	private static final String _FINDER_COLUMN_UUID_C_COMPANYID_2 = "ddmStructure.companyId = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_GROUPID = new FinderPath(DDMStructureModelImpl.ENTITY_CACHE_ENABLED,
			DDMStructureModelImpl.FINDER_CACHE_ENABLED, DDMStructureImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByGroupId",
			new String[] {
				Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID =
		new FinderPath(DDMStructureModelImpl.ENTITY_CACHE_ENABLED,
			DDMStructureModelImpl.FINDER_CACHE_ENABLED, DDMStructureImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByGroupId",
			new String[] { Long.class.getName() },
			DDMStructureModelImpl.GROUPID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_GROUPID = new FinderPath(DDMStructureModelImpl.ENTITY_CACHE_ENABLED,
			DDMStructureModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByGroupId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_COUNT_BY_GROUPID = new FinderPath(DDMStructureModelImpl.ENTITY_CACHE_ENABLED,
			DDMStructureModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "countByGroupId",
			new String[] { Long.class.getName() });

	/**
	 * Returns all the d d m structures where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching d d m structures
	 */
	@Override
	public List<DDMStructure> findByGroupId(long groupId) {
		return findByGroupId(groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the d d m structures where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMStructureModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of d d m structures
	 * @param end the upper bound of the range of d d m structures (not inclusive)
	 * @return the range of matching d d m structures
	 */
	@Override
	public List<DDMStructure> findByGroupId(long groupId, int start, int end) {
		return findByGroupId(groupId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the d d m structures where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMStructureModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of d d m structures
	 * @param end the upper bound of the range of d d m structures (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching d d m structures
	 */
	@Override
	public List<DDMStructure> findByGroupId(long groupId, int start, int end,
		OrderByComparator<DDMStructure> orderByComparator) {
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

		List<DDMStructure> list = (List<DDMStructure>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (DDMStructure ddmStructure : list) {
				if ((groupId != ddmStructure.getGroupId())) {
					list = null;

					break;
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

			query.append(_SQL_SELECT_DDMSTRUCTURE_WHERE);

			query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(DDMStructureModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (!pagination) {
					list = (List<DDMStructure>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<DDMStructure>)QueryUtil.list(q, getDialect(),
							start, end);
				}

				cacheResult(list);

				FinderCacheUtil.putResult(finderPath, finderArgs, list);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first d d m structure in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching d d m structure
	 * @throws NoSuchStructureException if a matching d d m structure could not be found
	 */
	@Override
	public DDMStructure findByGroupId_First(long groupId,
		OrderByComparator<DDMStructure> orderByComparator)
		throws NoSuchStructureException {
		DDMStructure ddmStructure = fetchByGroupId_First(groupId,
				orderByComparator);

		if (ddmStructure != null) {
			return ddmStructure;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchStructureException(msg.toString());
	}

	/**
	 * Returns the first d d m structure in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching d d m structure, or <code>null</code> if a matching d d m structure could not be found
	 */
	@Override
	public DDMStructure fetchByGroupId_First(long groupId,
		OrderByComparator<DDMStructure> orderByComparator) {
		List<DDMStructure> list = findByGroupId(groupId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last d d m structure in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching d d m structure
	 * @throws NoSuchStructureException if a matching d d m structure could not be found
	 */
	@Override
	public DDMStructure findByGroupId_Last(long groupId,
		OrderByComparator<DDMStructure> orderByComparator)
		throws NoSuchStructureException {
		DDMStructure ddmStructure = fetchByGroupId_Last(groupId,
				orderByComparator);

		if (ddmStructure != null) {
			return ddmStructure;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchStructureException(msg.toString());
	}

	/**
	 * Returns the last d d m structure in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching d d m structure, or <code>null</code> if a matching d d m structure could not be found
	 */
	@Override
	public DDMStructure fetchByGroupId_Last(long groupId,
		OrderByComparator<DDMStructure> orderByComparator) {
		int count = countByGroupId(groupId);

		if (count == 0) {
			return null;
		}

		List<DDMStructure> list = findByGroupId(groupId, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the d d m structures before and after the current d d m structure in the ordered set where groupId = &#63;.
	 *
	 * @param structureId the primary key of the current d d m structure
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next d d m structure
	 * @throws NoSuchStructureException if a d d m structure with the primary key could not be found
	 */
	@Override
	public DDMStructure[] findByGroupId_PrevAndNext(long structureId,
		long groupId, OrderByComparator<DDMStructure> orderByComparator)
		throws NoSuchStructureException {
		DDMStructure ddmStructure = findByPrimaryKey(structureId);

		Session session = null;

		try {
			session = openSession();

			DDMStructure[] array = new DDMStructureImpl[3];

			array[0] = getByGroupId_PrevAndNext(session, ddmStructure, groupId,
					orderByComparator, true);

			array[1] = ddmStructure;

			array[2] = getByGroupId_PrevAndNext(session, ddmStructure, groupId,
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

	protected DDMStructure getByGroupId_PrevAndNext(Session session,
		DDMStructure ddmStructure, long groupId,
		OrderByComparator<DDMStructure> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_DDMSTRUCTURE_WHERE);

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
			query.append(DDMStructureModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(ddmStructure);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<DDMStructure> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the d d m structures that the user has permission to view where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching d d m structures that the user has permission to view
	 */
	@Override
	public List<DDMStructure> filterFindByGroupId(long groupId) {
		return filterFindByGroupId(groupId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the d d m structures that the user has permission to view where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMStructureModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of d d m structures
	 * @param end the upper bound of the range of d d m structures (not inclusive)
	 * @return the range of matching d d m structures that the user has permission to view
	 */
	@Override
	public List<DDMStructure> filterFindByGroupId(long groupId, int start,
		int end) {
		return filterFindByGroupId(groupId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the d d m structures that the user has permissions to view where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMStructureModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of d d m structures
	 * @param end the upper bound of the range of d d m structures (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching d d m structures that the user has permission to view
	 */
	@Override
	public List<DDMStructure> filterFindByGroupId(long groupId, int start,
		int end, OrderByComparator<DDMStructure> orderByComparator) {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByGroupId(groupId, start, end, orderByComparator);
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
			query.append(_FILTER_SQL_SELECT_DDMSTRUCTURE_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_DDMSTRUCTURE_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_DDMSTRUCTURE_NO_INLINE_DISTINCT_WHERE_2);
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
				query.append(DDMStructureModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(DDMStructureModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				DDMStructure.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				q.addEntity(_FILTER_ENTITY_ALIAS, DDMStructureImpl.class);
			}
			else {
				q.addEntity(_FILTER_ENTITY_TABLE, DDMStructureImpl.class);
			}

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			return (List<DDMStructure>)QueryUtil.list(q, getDialect(), start,
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
	 * Returns the d d m structures before and after the current d d m structure in the ordered set of d d m structures that the user has permission to view where groupId = &#63;.
	 *
	 * @param structureId the primary key of the current d d m structure
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next d d m structure
	 * @throws NoSuchStructureException if a d d m structure with the primary key could not be found
	 */
	@Override
	public DDMStructure[] filterFindByGroupId_PrevAndNext(long structureId,
		long groupId, OrderByComparator<DDMStructure> orderByComparator)
		throws NoSuchStructureException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByGroupId_PrevAndNext(structureId, groupId,
				orderByComparator);
		}

		DDMStructure ddmStructure = findByPrimaryKey(structureId);

		Session session = null;

		try {
			session = openSession();

			DDMStructure[] array = new DDMStructureImpl[3];

			array[0] = filterGetByGroupId_PrevAndNext(session, ddmStructure,
					groupId, orderByComparator, true);

			array[1] = ddmStructure;

			array[2] = filterGetByGroupId_PrevAndNext(session, ddmStructure,
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

	protected DDMStructure filterGetByGroupId_PrevAndNext(Session session,
		DDMStructure ddmStructure, long groupId,
		OrderByComparator<DDMStructure> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_DDMSTRUCTURE_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_DDMSTRUCTURE_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_DDMSTRUCTURE_NO_INLINE_DISTINCT_WHERE_2);
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
				query.append(DDMStructureModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(DDMStructureModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				DDMStructure.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		SQLQuery q = session.createSynchronizedSQLQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		if (getDB().isSupportsInlineDistinct()) {
			q.addEntity(_FILTER_ENTITY_ALIAS, DDMStructureImpl.class);
		}
		else {
			q.addEntity(_FILTER_ENTITY_TABLE, DDMStructureImpl.class);
		}

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(ddmStructure);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<DDMStructure> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the d d m structures that the user has permission to view where groupId = any &#63;.
	 *
	 * @param groupIds the group IDs
	 * @return the matching d d m structures that the user has permission to view
	 */
	@Override
	public List<DDMStructure> filterFindByGroupId(long[] groupIds) {
		return filterFindByGroupId(groupIds, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the d d m structures that the user has permission to view where groupId = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMStructureModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupIds the group IDs
	 * @param start the lower bound of the range of d d m structures
	 * @param end the upper bound of the range of d d m structures (not inclusive)
	 * @return the range of matching d d m structures that the user has permission to view
	 */
	@Override
	public List<DDMStructure> filterFindByGroupId(long[] groupIds, int start,
		int end) {
		return filterFindByGroupId(groupIds, start, end, null);
	}

	/**
	 * Returns an ordered range of all the d d m structures that the user has permission to view where groupId = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMStructureModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupIds the group IDs
	 * @param start the lower bound of the range of d d m structures
	 * @param end the upper bound of the range of d d m structures (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching d d m structures that the user has permission to view
	 */
	@Override
	public List<DDMStructure> filterFindByGroupId(long[] groupIds, int start,
		int end, OrderByComparator<DDMStructure> orderByComparator) {
		if (!InlineSQLHelperUtil.isEnabled(groupIds)) {
			return findByGroupId(groupIds, start, end, orderByComparator);
		}

		if (groupIds == null) {
			groupIds = new long[0];
		}
		else {
			groupIds = ArrayUtil.unique(groupIds);
		}

		StringBundler query = new StringBundler();

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_DDMSTRUCTURE_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_DDMSTRUCTURE_NO_INLINE_DISTINCT_WHERE_1);
		}

		if (groupIds.length > 0) {
			query.append(StringPool.OPEN_PARENTHESIS);

			query.append(_FINDER_COLUMN_GROUPID_GROUPID_7);

			query.append(StringUtil.merge(groupIds));

			query.append(StringPool.CLOSE_PARENTHESIS);

			query.append(StringPool.CLOSE_PARENTHESIS);
		}

		query.setStringAt(removeConjunction(query.stringAt(query.index() - 1)),
			query.index() - 1);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_DDMSTRUCTURE_NO_INLINE_DISTINCT_WHERE_2);
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
				query.append(DDMStructureModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(DDMStructureModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				DDMStructure.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupIds);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				q.addEntity(_FILTER_ENTITY_ALIAS, DDMStructureImpl.class);
			}
			else {
				q.addEntity(_FILTER_ENTITY_TABLE, DDMStructureImpl.class);
			}

			return (List<DDMStructure>)QueryUtil.list(q, getDialect(), start,
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
	 * Returns all the d d m structures where groupId = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMStructureModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupIds the group IDs
	 * @return the matching d d m structures
	 */
	@Override
	public List<DDMStructure> findByGroupId(long[] groupIds) {
		return findByGroupId(groupIds, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the d d m structures where groupId = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMStructureModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupIds the group IDs
	 * @param start the lower bound of the range of d d m structures
	 * @param end the upper bound of the range of d d m structures (not inclusive)
	 * @return the range of matching d d m structures
	 */
	@Override
	public List<DDMStructure> findByGroupId(long[] groupIds, int start, int end) {
		return findByGroupId(groupIds, start, end, null);
	}

	/**
	 * Returns an ordered range of all the d d m structures where groupId = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMStructureModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupIds the group IDs
	 * @param start the lower bound of the range of d d m structures
	 * @param end the upper bound of the range of d d m structures (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching d d m structures
	 */
	@Override
	public List<DDMStructure> findByGroupId(long[] groupIds, int start,
		int end, OrderByComparator<DDMStructure> orderByComparator) {
		if (groupIds == null) {
			groupIds = new long[0];
		}
		else {
			groupIds = ArrayUtil.unique(groupIds);
		}

		if (groupIds.length == 1) {
			return findByGroupId(groupIds[0], start, end, orderByComparator);
		}

		boolean pagination = true;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderArgs = new Object[] { StringUtil.merge(groupIds) };
		}
		else {
			finderArgs = new Object[] {
					StringUtil.merge(groupIds),
					
					start, end, orderByComparator
				};
		}

		List<DDMStructure> list = (List<DDMStructure>)FinderCacheUtil.getResult(FINDER_PATH_WITH_PAGINATION_FIND_BY_GROUPID,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (DDMStructure ddmStructure : list) {
				if (!ArrayUtil.contains(groupIds, ddmStructure.getGroupId())) {
					list = null;

					break;
				}
			}
		}

		if (list == null) {
			StringBundler query = new StringBundler();

			query.append(_SQL_SELECT_DDMSTRUCTURE_WHERE);

			if (groupIds.length > 0) {
				query.append(StringPool.OPEN_PARENTHESIS);

				query.append(_FINDER_COLUMN_GROUPID_GROUPID_7);

				query.append(StringUtil.merge(groupIds));

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
				query.append(DDMStructureModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (!pagination) {
					list = (List<DDMStructure>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<DDMStructure>)QueryUtil.list(q, getDialect(),
							start, end);
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_WITH_PAGINATION_FIND_BY_GROUPID,
					finderArgs, list);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(FINDER_PATH_WITH_PAGINATION_FIND_BY_GROUPID,
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
	 * Removes all the d d m structures where groupId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 */
	@Override
	public void removeByGroupId(long groupId) {
		for (DDMStructure ddmStructure : findByGroupId(groupId,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(ddmStructure);
		}
	}

	/**
	 * Returns the number of d d m structures where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching d d m structures
	 */
	@Override
	public int countByGroupId(long groupId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_GROUPID;

		Object[] finderArgs = new Object[] { groupId };

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs,
				this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_DDMSTRUCTURE_WHERE);

			query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				count = (Long)q.uniqueResult();

				FinderCacheUtil.putResult(finderPath, finderArgs, count);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of d d m structures where groupId = any &#63;.
	 *
	 * @param groupIds the group IDs
	 * @return the number of matching d d m structures
	 */
	@Override
	public int countByGroupId(long[] groupIds) {
		if (groupIds == null) {
			groupIds = new long[0];
		}
		else {
			groupIds = ArrayUtil.unique(groupIds);
		}

		Object[] finderArgs = new Object[] { StringUtil.merge(groupIds) };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_WITH_PAGINATION_COUNT_BY_GROUPID,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler();

			query.append(_SQL_COUNT_DDMSTRUCTURE_WHERE);

			if (groupIds.length > 0) {
				query.append(StringPool.OPEN_PARENTHESIS);

				query.append(_FINDER_COLUMN_GROUPID_GROUPID_7);

				query.append(StringUtil.merge(groupIds));

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

				count = (Long)q.uniqueResult();

				FinderCacheUtil.putResult(FINDER_PATH_WITH_PAGINATION_COUNT_BY_GROUPID,
					finderArgs, count);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(FINDER_PATH_WITH_PAGINATION_COUNT_BY_GROUPID,
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
	 * Returns the number of d d m structures that the user has permission to view where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching d d m structures that the user has permission to view
	 */
	@Override
	public int filterCountByGroupId(long groupId) {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByGroupId(groupId);
		}

		StringBundler query = new StringBundler(2);

		query.append(_FILTER_SQL_COUNT_DDMSTRUCTURE_WHERE);

		query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				DDMStructure.class.getName(),
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

	/**
	 * Returns the number of d d m structures that the user has permission to view where groupId = any &#63;.
	 *
	 * @param groupIds the group IDs
	 * @return the number of matching d d m structures that the user has permission to view
	 */
	@Override
	public int filterCountByGroupId(long[] groupIds) {
		if (!InlineSQLHelperUtil.isEnabled(groupIds)) {
			return countByGroupId(groupIds);
		}

		if (groupIds == null) {
			groupIds = new long[0];
		}
		else {
			groupIds = ArrayUtil.unique(groupIds);
		}

		StringBundler query = new StringBundler();

		query.append(_FILTER_SQL_COUNT_DDMSTRUCTURE_WHERE);

		if (groupIds.length > 0) {
			query.append(StringPool.OPEN_PARENTHESIS);

			query.append(_FINDER_COLUMN_GROUPID_GROUPID_7);

			query.append(StringUtil.merge(groupIds));

			query.append(StringPool.CLOSE_PARENTHESIS);

			query.append(StringPool.CLOSE_PARENTHESIS);
		}

		query.setStringAt(removeConjunction(query.stringAt(query.index() - 1)),
			query.index() - 1);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				DDMStructure.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupIds);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME,
				com.liferay.portal.kernel.dao.orm.Type.LONG);

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

	private static final String _FINDER_COLUMN_GROUPID_GROUPID_2 = "ddmStructure.groupId = ?";
	private static final String _FINDER_COLUMN_GROUPID_GROUPID_7 = "ddmStructure.groupId IN (";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_PARENTSTRUCTUREID =
		new FinderPath(DDMStructureModelImpl.ENTITY_CACHE_ENABLED,
			DDMStructureModelImpl.FINDER_CACHE_ENABLED, DDMStructureImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByParentStructureId",
			new String[] {
				Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_PARENTSTRUCTUREID =
		new FinderPath(DDMStructureModelImpl.ENTITY_CACHE_ENABLED,
			DDMStructureModelImpl.FINDER_CACHE_ENABLED, DDMStructureImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByParentStructureId", new String[] { Long.class.getName() },
			DDMStructureModelImpl.PARENTSTRUCTUREID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_PARENTSTRUCTUREID = new FinderPath(DDMStructureModelImpl.ENTITY_CACHE_ENABLED,
			DDMStructureModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByParentStructureId", new String[] { Long.class.getName() });

	/**
	 * Returns all the d d m structures where parentStructureId = &#63;.
	 *
	 * @param parentStructureId the parent structure ID
	 * @return the matching d d m structures
	 */
	@Override
	public List<DDMStructure> findByParentStructureId(long parentStructureId) {
		return findByParentStructureId(parentStructureId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the d d m structures where parentStructureId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMStructureModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param parentStructureId the parent structure ID
	 * @param start the lower bound of the range of d d m structures
	 * @param end the upper bound of the range of d d m structures (not inclusive)
	 * @return the range of matching d d m structures
	 */
	@Override
	public List<DDMStructure> findByParentStructureId(long parentStructureId,
		int start, int end) {
		return findByParentStructureId(parentStructureId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the d d m structures where parentStructureId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMStructureModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param parentStructureId the parent structure ID
	 * @param start the lower bound of the range of d d m structures
	 * @param end the upper bound of the range of d d m structures (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching d d m structures
	 */
	@Override
	public List<DDMStructure> findByParentStructureId(long parentStructureId,
		int start, int end, OrderByComparator<DDMStructure> orderByComparator) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_PARENTSTRUCTUREID;
			finderArgs = new Object[] { parentStructureId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_PARENTSTRUCTUREID;
			finderArgs = new Object[] {
					parentStructureId,
					
					start, end, orderByComparator
				};
		}

		List<DDMStructure> list = (List<DDMStructure>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (DDMStructure ddmStructure : list) {
				if ((parentStructureId != ddmStructure.getParentStructureId())) {
					list = null;

					break;
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

			query.append(_SQL_SELECT_DDMSTRUCTURE_WHERE);

			query.append(_FINDER_COLUMN_PARENTSTRUCTUREID_PARENTSTRUCTUREID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(DDMStructureModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(parentStructureId);

				if (!pagination) {
					list = (List<DDMStructure>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<DDMStructure>)QueryUtil.list(q, getDialect(),
							start, end);
				}

				cacheResult(list);

				FinderCacheUtil.putResult(finderPath, finderArgs, list);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first d d m structure in the ordered set where parentStructureId = &#63;.
	 *
	 * @param parentStructureId the parent structure ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching d d m structure
	 * @throws NoSuchStructureException if a matching d d m structure could not be found
	 */
	@Override
	public DDMStructure findByParentStructureId_First(long parentStructureId,
		OrderByComparator<DDMStructure> orderByComparator)
		throws NoSuchStructureException {
		DDMStructure ddmStructure = fetchByParentStructureId_First(parentStructureId,
				orderByComparator);

		if (ddmStructure != null) {
			return ddmStructure;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("parentStructureId=");
		msg.append(parentStructureId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchStructureException(msg.toString());
	}

	/**
	 * Returns the first d d m structure in the ordered set where parentStructureId = &#63;.
	 *
	 * @param parentStructureId the parent structure ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching d d m structure, or <code>null</code> if a matching d d m structure could not be found
	 */
	@Override
	public DDMStructure fetchByParentStructureId_First(long parentStructureId,
		OrderByComparator<DDMStructure> orderByComparator) {
		List<DDMStructure> list = findByParentStructureId(parentStructureId, 0,
				1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last d d m structure in the ordered set where parentStructureId = &#63;.
	 *
	 * @param parentStructureId the parent structure ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching d d m structure
	 * @throws NoSuchStructureException if a matching d d m structure could not be found
	 */
	@Override
	public DDMStructure findByParentStructureId_Last(long parentStructureId,
		OrderByComparator<DDMStructure> orderByComparator)
		throws NoSuchStructureException {
		DDMStructure ddmStructure = fetchByParentStructureId_Last(parentStructureId,
				orderByComparator);

		if (ddmStructure != null) {
			return ddmStructure;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("parentStructureId=");
		msg.append(parentStructureId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchStructureException(msg.toString());
	}

	/**
	 * Returns the last d d m structure in the ordered set where parentStructureId = &#63;.
	 *
	 * @param parentStructureId the parent structure ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching d d m structure, or <code>null</code> if a matching d d m structure could not be found
	 */
	@Override
	public DDMStructure fetchByParentStructureId_Last(long parentStructureId,
		OrderByComparator<DDMStructure> orderByComparator) {
		int count = countByParentStructureId(parentStructureId);

		if (count == 0) {
			return null;
		}

		List<DDMStructure> list = findByParentStructureId(parentStructureId,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the d d m structures before and after the current d d m structure in the ordered set where parentStructureId = &#63;.
	 *
	 * @param structureId the primary key of the current d d m structure
	 * @param parentStructureId the parent structure ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next d d m structure
	 * @throws NoSuchStructureException if a d d m structure with the primary key could not be found
	 */
	@Override
	public DDMStructure[] findByParentStructureId_PrevAndNext(
		long structureId, long parentStructureId,
		OrderByComparator<DDMStructure> orderByComparator)
		throws NoSuchStructureException {
		DDMStructure ddmStructure = findByPrimaryKey(structureId);

		Session session = null;

		try {
			session = openSession();

			DDMStructure[] array = new DDMStructureImpl[3];

			array[0] = getByParentStructureId_PrevAndNext(session,
					ddmStructure, parentStructureId, orderByComparator, true);

			array[1] = ddmStructure;

			array[2] = getByParentStructureId_PrevAndNext(session,
					ddmStructure, parentStructureId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected DDMStructure getByParentStructureId_PrevAndNext(Session session,
		DDMStructure ddmStructure, long parentStructureId,
		OrderByComparator<DDMStructure> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_DDMSTRUCTURE_WHERE);

		query.append(_FINDER_COLUMN_PARENTSTRUCTUREID_PARENTSTRUCTUREID_2);

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
			query.append(DDMStructureModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(parentStructureId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(ddmStructure);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<DDMStructure> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the d d m structures where parentStructureId = &#63; from the database.
	 *
	 * @param parentStructureId the parent structure ID
	 */
	@Override
	public void removeByParentStructureId(long parentStructureId) {
		for (DDMStructure ddmStructure : findByParentStructureId(
				parentStructureId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(ddmStructure);
		}
	}

	/**
	 * Returns the number of d d m structures where parentStructureId = &#63;.
	 *
	 * @param parentStructureId the parent structure ID
	 * @return the number of matching d d m structures
	 */
	@Override
	public int countByParentStructureId(long parentStructureId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_PARENTSTRUCTUREID;

		Object[] finderArgs = new Object[] { parentStructureId };

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs,
				this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_DDMSTRUCTURE_WHERE);

			query.append(_FINDER_COLUMN_PARENTSTRUCTUREID_PARENTSTRUCTUREID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(parentStructureId);

				count = (Long)q.uniqueResult();

				FinderCacheUtil.putResult(finderPath, finderArgs, count);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_PARENTSTRUCTUREID_PARENTSTRUCTUREID_2 =
		"ddmStructure.parentStructureId = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_CLASSNAMEID =
		new FinderPath(DDMStructureModelImpl.ENTITY_CACHE_ENABLED,
			DDMStructureModelImpl.FINDER_CACHE_ENABLED, DDMStructureImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByClassNameId",
			new String[] {
				Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_CLASSNAMEID =
		new FinderPath(DDMStructureModelImpl.ENTITY_CACHE_ENABLED,
			DDMStructureModelImpl.FINDER_CACHE_ENABLED, DDMStructureImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByClassNameId",
			new String[] { Long.class.getName() },
			DDMStructureModelImpl.CLASSNAMEID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_CLASSNAMEID = new FinderPath(DDMStructureModelImpl.ENTITY_CACHE_ENABLED,
			DDMStructureModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByClassNameId",
			new String[] { Long.class.getName() });

	/**
	 * Returns all the d d m structures where classNameId = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @return the matching d d m structures
	 */
	@Override
	public List<DDMStructure> findByClassNameId(long classNameId) {
		return findByClassNameId(classNameId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the d d m structures where classNameId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMStructureModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param start the lower bound of the range of d d m structures
	 * @param end the upper bound of the range of d d m structures (not inclusive)
	 * @return the range of matching d d m structures
	 */
	@Override
	public List<DDMStructure> findByClassNameId(long classNameId, int start,
		int end) {
		return findByClassNameId(classNameId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the d d m structures where classNameId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMStructureModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param start the lower bound of the range of d d m structures
	 * @param end the upper bound of the range of d d m structures (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching d d m structures
	 */
	@Override
	public List<DDMStructure> findByClassNameId(long classNameId, int start,
		int end, OrderByComparator<DDMStructure> orderByComparator) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_CLASSNAMEID;
			finderArgs = new Object[] { classNameId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_CLASSNAMEID;
			finderArgs = new Object[] { classNameId, start, end, orderByComparator };
		}

		List<DDMStructure> list = (List<DDMStructure>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (DDMStructure ddmStructure : list) {
				if ((classNameId != ddmStructure.getClassNameId())) {
					list = null;

					break;
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

			query.append(_SQL_SELECT_DDMSTRUCTURE_WHERE);

			query.append(_FINDER_COLUMN_CLASSNAMEID_CLASSNAMEID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(DDMStructureModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(classNameId);

				if (!pagination) {
					list = (List<DDMStructure>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<DDMStructure>)QueryUtil.list(q, getDialect(),
							start, end);
				}

				cacheResult(list);

				FinderCacheUtil.putResult(finderPath, finderArgs, list);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first d d m structure in the ordered set where classNameId = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching d d m structure
	 * @throws NoSuchStructureException if a matching d d m structure could not be found
	 */
	@Override
	public DDMStructure findByClassNameId_First(long classNameId,
		OrderByComparator<DDMStructure> orderByComparator)
		throws NoSuchStructureException {
		DDMStructure ddmStructure = fetchByClassNameId_First(classNameId,
				orderByComparator);

		if (ddmStructure != null) {
			return ddmStructure;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("classNameId=");
		msg.append(classNameId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchStructureException(msg.toString());
	}

	/**
	 * Returns the first d d m structure in the ordered set where classNameId = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching d d m structure, or <code>null</code> if a matching d d m structure could not be found
	 */
	@Override
	public DDMStructure fetchByClassNameId_First(long classNameId,
		OrderByComparator<DDMStructure> orderByComparator) {
		List<DDMStructure> list = findByClassNameId(classNameId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last d d m structure in the ordered set where classNameId = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching d d m structure
	 * @throws NoSuchStructureException if a matching d d m structure could not be found
	 */
	@Override
	public DDMStructure findByClassNameId_Last(long classNameId,
		OrderByComparator<DDMStructure> orderByComparator)
		throws NoSuchStructureException {
		DDMStructure ddmStructure = fetchByClassNameId_Last(classNameId,
				orderByComparator);

		if (ddmStructure != null) {
			return ddmStructure;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("classNameId=");
		msg.append(classNameId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchStructureException(msg.toString());
	}

	/**
	 * Returns the last d d m structure in the ordered set where classNameId = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching d d m structure, or <code>null</code> if a matching d d m structure could not be found
	 */
	@Override
	public DDMStructure fetchByClassNameId_Last(long classNameId,
		OrderByComparator<DDMStructure> orderByComparator) {
		int count = countByClassNameId(classNameId);

		if (count == 0) {
			return null;
		}

		List<DDMStructure> list = findByClassNameId(classNameId, count - 1,
				count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the d d m structures before and after the current d d m structure in the ordered set where classNameId = &#63;.
	 *
	 * @param structureId the primary key of the current d d m structure
	 * @param classNameId the class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next d d m structure
	 * @throws NoSuchStructureException if a d d m structure with the primary key could not be found
	 */
	@Override
	public DDMStructure[] findByClassNameId_PrevAndNext(long structureId,
		long classNameId, OrderByComparator<DDMStructure> orderByComparator)
		throws NoSuchStructureException {
		DDMStructure ddmStructure = findByPrimaryKey(structureId);

		Session session = null;

		try {
			session = openSession();

			DDMStructure[] array = new DDMStructureImpl[3];

			array[0] = getByClassNameId_PrevAndNext(session, ddmStructure,
					classNameId, orderByComparator, true);

			array[1] = ddmStructure;

			array[2] = getByClassNameId_PrevAndNext(session, ddmStructure,
					classNameId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected DDMStructure getByClassNameId_PrevAndNext(Session session,
		DDMStructure ddmStructure, long classNameId,
		OrderByComparator<DDMStructure> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_DDMSTRUCTURE_WHERE);

		query.append(_FINDER_COLUMN_CLASSNAMEID_CLASSNAMEID_2);

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
			query.append(DDMStructureModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(classNameId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(ddmStructure);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<DDMStructure> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the d d m structures where classNameId = &#63; from the database.
	 *
	 * @param classNameId the class name ID
	 */
	@Override
	public void removeByClassNameId(long classNameId) {
		for (DDMStructure ddmStructure : findByClassNameId(classNameId,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(ddmStructure);
		}
	}

	/**
	 * Returns the number of d d m structures where classNameId = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @return the number of matching d d m structures
	 */
	@Override
	public int countByClassNameId(long classNameId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_CLASSNAMEID;

		Object[] finderArgs = new Object[] { classNameId };

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs,
				this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_DDMSTRUCTURE_WHERE);

			query.append(_FINDER_COLUMN_CLASSNAMEID_CLASSNAMEID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(classNameId);

				count = (Long)q.uniqueResult();

				FinderCacheUtil.putResult(finderPath, finderArgs, count);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_CLASSNAMEID_CLASSNAMEID_2 = "ddmStructure.classNameId = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_STRUCTUREKEY =
		new FinderPath(DDMStructureModelImpl.ENTITY_CACHE_ENABLED,
			DDMStructureModelImpl.FINDER_CACHE_ENABLED, DDMStructureImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByStructureKey",
			new String[] {
				String.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_STRUCTUREKEY =
		new FinderPath(DDMStructureModelImpl.ENTITY_CACHE_ENABLED,
			DDMStructureModelImpl.FINDER_CACHE_ENABLED, DDMStructureImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByStructureKey",
			new String[] { String.class.getName() },
			DDMStructureModelImpl.STRUCTUREKEY_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_STRUCTUREKEY = new FinderPath(DDMStructureModelImpl.ENTITY_CACHE_ENABLED,
			DDMStructureModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByStructureKey",
			new String[] { String.class.getName() });

	/**
	 * Returns all the d d m structures where structureKey = &#63;.
	 *
	 * @param structureKey the structure key
	 * @return the matching d d m structures
	 */
	@Override
	public List<DDMStructure> findByStructureKey(String structureKey) {
		return findByStructureKey(structureKey, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the d d m structures where structureKey = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMStructureModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param structureKey the structure key
	 * @param start the lower bound of the range of d d m structures
	 * @param end the upper bound of the range of d d m structures (not inclusive)
	 * @return the range of matching d d m structures
	 */
	@Override
	public List<DDMStructure> findByStructureKey(String structureKey,
		int start, int end) {
		return findByStructureKey(structureKey, start, end, null);
	}

	/**
	 * Returns an ordered range of all the d d m structures where structureKey = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMStructureModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param structureKey the structure key
	 * @param start the lower bound of the range of d d m structures
	 * @param end the upper bound of the range of d d m structures (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching d d m structures
	 */
	@Override
	public List<DDMStructure> findByStructureKey(String structureKey,
		int start, int end, OrderByComparator<DDMStructure> orderByComparator) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_STRUCTUREKEY;
			finderArgs = new Object[] { structureKey };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_STRUCTUREKEY;
			finderArgs = new Object[] {
					structureKey,
					
					start, end, orderByComparator
				};
		}

		List<DDMStructure> list = (List<DDMStructure>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (DDMStructure ddmStructure : list) {
				if (!Validator.equals(structureKey,
							ddmStructure.getStructureKey())) {
					list = null;

					break;
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

			query.append(_SQL_SELECT_DDMSTRUCTURE_WHERE);

			boolean bindStructureKey = false;

			if (structureKey == null) {
				query.append(_FINDER_COLUMN_STRUCTUREKEY_STRUCTUREKEY_1);
			}
			else if (structureKey.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_STRUCTUREKEY_STRUCTUREKEY_3);
			}
			else {
				bindStructureKey = true;

				query.append(_FINDER_COLUMN_STRUCTUREKEY_STRUCTUREKEY_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(DDMStructureModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindStructureKey) {
					qPos.add(structureKey);
				}

				if (!pagination) {
					list = (List<DDMStructure>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<DDMStructure>)QueryUtil.list(q, getDialect(),
							start, end);
				}

				cacheResult(list);

				FinderCacheUtil.putResult(finderPath, finderArgs, list);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first d d m structure in the ordered set where structureKey = &#63;.
	 *
	 * @param structureKey the structure key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching d d m structure
	 * @throws NoSuchStructureException if a matching d d m structure could not be found
	 */
	@Override
	public DDMStructure findByStructureKey_First(String structureKey,
		OrderByComparator<DDMStructure> orderByComparator)
		throws NoSuchStructureException {
		DDMStructure ddmStructure = fetchByStructureKey_First(structureKey,
				orderByComparator);

		if (ddmStructure != null) {
			return ddmStructure;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("structureKey=");
		msg.append(structureKey);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchStructureException(msg.toString());
	}

	/**
	 * Returns the first d d m structure in the ordered set where structureKey = &#63;.
	 *
	 * @param structureKey the structure key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching d d m structure, or <code>null</code> if a matching d d m structure could not be found
	 */
	@Override
	public DDMStructure fetchByStructureKey_First(String structureKey,
		OrderByComparator<DDMStructure> orderByComparator) {
		List<DDMStructure> list = findByStructureKey(structureKey, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last d d m structure in the ordered set where structureKey = &#63;.
	 *
	 * @param structureKey the structure key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching d d m structure
	 * @throws NoSuchStructureException if a matching d d m structure could not be found
	 */
	@Override
	public DDMStructure findByStructureKey_Last(String structureKey,
		OrderByComparator<DDMStructure> orderByComparator)
		throws NoSuchStructureException {
		DDMStructure ddmStructure = fetchByStructureKey_Last(structureKey,
				orderByComparator);

		if (ddmStructure != null) {
			return ddmStructure;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("structureKey=");
		msg.append(structureKey);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchStructureException(msg.toString());
	}

	/**
	 * Returns the last d d m structure in the ordered set where structureKey = &#63;.
	 *
	 * @param structureKey the structure key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching d d m structure, or <code>null</code> if a matching d d m structure could not be found
	 */
	@Override
	public DDMStructure fetchByStructureKey_Last(String structureKey,
		OrderByComparator<DDMStructure> orderByComparator) {
		int count = countByStructureKey(structureKey);

		if (count == 0) {
			return null;
		}

		List<DDMStructure> list = findByStructureKey(structureKey, count - 1,
				count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the d d m structures before and after the current d d m structure in the ordered set where structureKey = &#63;.
	 *
	 * @param structureId the primary key of the current d d m structure
	 * @param structureKey the structure key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next d d m structure
	 * @throws NoSuchStructureException if a d d m structure with the primary key could not be found
	 */
	@Override
	public DDMStructure[] findByStructureKey_PrevAndNext(long structureId,
		String structureKey, OrderByComparator<DDMStructure> orderByComparator)
		throws NoSuchStructureException {
		DDMStructure ddmStructure = findByPrimaryKey(structureId);

		Session session = null;

		try {
			session = openSession();

			DDMStructure[] array = new DDMStructureImpl[3];

			array[0] = getByStructureKey_PrevAndNext(session, ddmStructure,
					structureKey, orderByComparator, true);

			array[1] = ddmStructure;

			array[2] = getByStructureKey_PrevAndNext(session, ddmStructure,
					structureKey, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected DDMStructure getByStructureKey_PrevAndNext(Session session,
		DDMStructure ddmStructure, String structureKey,
		OrderByComparator<DDMStructure> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_DDMSTRUCTURE_WHERE);

		boolean bindStructureKey = false;

		if (structureKey == null) {
			query.append(_FINDER_COLUMN_STRUCTUREKEY_STRUCTUREKEY_1);
		}
		else if (structureKey.equals(StringPool.BLANK)) {
			query.append(_FINDER_COLUMN_STRUCTUREKEY_STRUCTUREKEY_3);
		}
		else {
			bindStructureKey = true;

			query.append(_FINDER_COLUMN_STRUCTUREKEY_STRUCTUREKEY_2);
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
			query.append(DDMStructureModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		if (bindStructureKey) {
			qPos.add(structureKey);
		}

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(ddmStructure);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<DDMStructure> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the d d m structures where structureKey = &#63; from the database.
	 *
	 * @param structureKey the structure key
	 */
	@Override
	public void removeByStructureKey(String structureKey) {
		for (DDMStructure ddmStructure : findByStructureKey(structureKey,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(ddmStructure);
		}
	}

	/**
	 * Returns the number of d d m structures where structureKey = &#63;.
	 *
	 * @param structureKey the structure key
	 * @return the number of matching d d m structures
	 */
	@Override
	public int countByStructureKey(String structureKey) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_STRUCTUREKEY;

		Object[] finderArgs = new Object[] { structureKey };

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs,
				this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_DDMSTRUCTURE_WHERE);

			boolean bindStructureKey = false;

			if (structureKey == null) {
				query.append(_FINDER_COLUMN_STRUCTUREKEY_STRUCTUREKEY_1);
			}
			else if (structureKey.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_STRUCTUREKEY_STRUCTUREKEY_3);
			}
			else {
				bindStructureKey = true;

				query.append(_FINDER_COLUMN_STRUCTUREKEY_STRUCTUREKEY_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindStructureKey) {
					qPos.add(structureKey);
				}

				count = (Long)q.uniqueResult();

				FinderCacheUtil.putResult(finderPath, finderArgs, count);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_STRUCTUREKEY_STRUCTUREKEY_1 = "ddmStructure.structureKey IS NULL";
	private static final String _FINDER_COLUMN_STRUCTUREKEY_STRUCTUREKEY_2 = "ddmStructure.structureKey = ?";
	private static final String _FINDER_COLUMN_STRUCTUREKEY_STRUCTUREKEY_3 = "(ddmStructure.structureKey IS NULL OR ddmStructure.structureKey = '')";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_G_P = new FinderPath(DDMStructureModelImpl.ENTITY_CACHE_ENABLED,
			DDMStructureModelImpl.FINDER_CACHE_ENABLED, DDMStructureImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_P",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_P = new FinderPath(DDMStructureModelImpl.ENTITY_CACHE_ENABLED,
			DDMStructureModelImpl.FINDER_CACHE_ENABLED, DDMStructureImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByG_P",
			new String[] { Long.class.getName(), Long.class.getName() },
			DDMStructureModelImpl.GROUPID_COLUMN_BITMASK |
			DDMStructureModelImpl.PARENTSTRUCTUREID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_G_P = new FinderPath(DDMStructureModelImpl.ENTITY_CACHE_ENABLED,
			DDMStructureModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_P",
			new String[] { Long.class.getName(), Long.class.getName() });

	/**
	 * Returns all the d d m structures where groupId = &#63; and parentStructureId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param parentStructureId the parent structure ID
	 * @return the matching d d m structures
	 */
	@Override
	public List<DDMStructure> findByG_P(long groupId, long parentStructureId) {
		return findByG_P(groupId, parentStructureId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the d d m structures where groupId = &#63; and parentStructureId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMStructureModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param parentStructureId the parent structure ID
	 * @param start the lower bound of the range of d d m structures
	 * @param end the upper bound of the range of d d m structures (not inclusive)
	 * @return the range of matching d d m structures
	 */
	@Override
	public List<DDMStructure> findByG_P(long groupId, long parentStructureId,
		int start, int end) {
		return findByG_P(groupId, parentStructureId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the d d m structures where groupId = &#63; and parentStructureId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMStructureModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param parentStructureId the parent structure ID
	 * @param start the lower bound of the range of d d m structures
	 * @param end the upper bound of the range of d d m structures (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching d d m structures
	 */
	@Override
	public List<DDMStructure> findByG_P(long groupId, long parentStructureId,
		int start, int end, OrderByComparator<DDMStructure> orderByComparator) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_P;
			finderArgs = new Object[] { groupId, parentStructureId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_G_P;
			finderArgs = new Object[] {
					groupId, parentStructureId,
					
					start, end, orderByComparator
				};
		}

		List<DDMStructure> list = (List<DDMStructure>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (DDMStructure ddmStructure : list) {
				if ((groupId != ddmStructure.getGroupId()) ||
						(parentStructureId != ddmStructure.getParentStructureId())) {
					list = null;

					break;
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

			query.append(_SQL_SELECT_DDMSTRUCTURE_WHERE);

			query.append(_FINDER_COLUMN_G_P_GROUPID_2);

			query.append(_FINDER_COLUMN_G_P_PARENTSTRUCTUREID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(DDMStructureModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(parentStructureId);

				if (!pagination) {
					list = (List<DDMStructure>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<DDMStructure>)QueryUtil.list(q, getDialect(),
							start, end);
				}

				cacheResult(list);

				FinderCacheUtil.putResult(finderPath, finderArgs, list);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first d d m structure in the ordered set where groupId = &#63; and parentStructureId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param parentStructureId the parent structure ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching d d m structure
	 * @throws NoSuchStructureException if a matching d d m structure could not be found
	 */
	@Override
	public DDMStructure findByG_P_First(long groupId, long parentStructureId,
		OrderByComparator<DDMStructure> orderByComparator)
		throws NoSuchStructureException {
		DDMStructure ddmStructure = fetchByG_P_First(groupId,
				parentStructureId, orderByComparator);

		if (ddmStructure != null) {
			return ddmStructure;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", parentStructureId=");
		msg.append(parentStructureId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchStructureException(msg.toString());
	}

	/**
	 * Returns the first d d m structure in the ordered set where groupId = &#63; and parentStructureId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param parentStructureId the parent structure ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching d d m structure, or <code>null</code> if a matching d d m structure could not be found
	 */
	@Override
	public DDMStructure fetchByG_P_First(long groupId, long parentStructureId,
		OrderByComparator<DDMStructure> orderByComparator) {
		List<DDMStructure> list = findByG_P(groupId, parentStructureId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last d d m structure in the ordered set where groupId = &#63; and parentStructureId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param parentStructureId the parent structure ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching d d m structure
	 * @throws NoSuchStructureException if a matching d d m structure could not be found
	 */
	@Override
	public DDMStructure findByG_P_Last(long groupId, long parentStructureId,
		OrderByComparator<DDMStructure> orderByComparator)
		throws NoSuchStructureException {
		DDMStructure ddmStructure = fetchByG_P_Last(groupId, parentStructureId,
				orderByComparator);

		if (ddmStructure != null) {
			return ddmStructure;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", parentStructureId=");
		msg.append(parentStructureId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchStructureException(msg.toString());
	}

	/**
	 * Returns the last d d m structure in the ordered set where groupId = &#63; and parentStructureId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param parentStructureId the parent structure ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching d d m structure, or <code>null</code> if a matching d d m structure could not be found
	 */
	@Override
	public DDMStructure fetchByG_P_Last(long groupId, long parentStructureId,
		OrderByComparator<DDMStructure> orderByComparator) {
		int count = countByG_P(groupId, parentStructureId);

		if (count == 0) {
			return null;
		}

		List<DDMStructure> list = findByG_P(groupId, parentStructureId,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the d d m structures before and after the current d d m structure in the ordered set where groupId = &#63; and parentStructureId = &#63;.
	 *
	 * @param structureId the primary key of the current d d m structure
	 * @param groupId the group ID
	 * @param parentStructureId the parent structure ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next d d m structure
	 * @throws NoSuchStructureException if a d d m structure with the primary key could not be found
	 */
	@Override
	public DDMStructure[] findByG_P_PrevAndNext(long structureId, long groupId,
		long parentStructureId,
		OrderByComparator<DDMStructure> orderByComparator)
		throws NoSuchStructureException {
		DDMStructure ddmStructure = findByPrimaryKey(structureId);

		Session session = null;

		try {
			session = openSession();

			DDMStructure[] array = new DDMStructureImpl[3];

			array[0] = getByG_P_PrevAndNext(session, ddmStructure, groupId,
					parentStructureId, orderByComparator, true);

			array[1] = ddmStructure;

			array[2] = getByG_P_PrevAndNext(session, ddmStructure, groupId,
					parentStructureId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected DDMStructure getByG_P_PrevAndNext(Session session,
		DDMStructure ddmStructure, long groupId, long parentStructureId,
		OrderByComparator<DDMStructure> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_DDMSTRUCTURE_WHERE);

		query.append(_FINDER_COLUMN_G_P_GROUPID_2);

		query.append(_FINDER_COLUMN_G_P_PARENTSTRUCTUREID_2);

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
			query.append(DDMStructureModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		qPos.add(parentStructureId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(ddmStructure);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<DDMStructure> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the d d m structures that the user has permission to view where groupId = &#63; and parentStructureId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param parentStructureId the parent structure ID
	 * @return the matching d d m structures that the user has permission to view
	 */
	@Override
	public List<DDMStructure> filterFindByG_P(long groupId,
		long parentStructureId) {
		return filterFindByG_P(groupId, parentStructureId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the d d m structures that the user has permission to view where groupId = &#63; and parentStructureId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMStructureModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param parentStructureId the parent structure ID
	 * @param start the lower bound of the range of d d m structures
	 * @param end the upper bound of the range of d d m structures (not inclusive)
	 * @return the range of matching d d m structures that the user has permission to view
	 */
	@Override
	public List<DDMStructure> filterFindByG_P(long groupId,
		long parentStructureId, int start, int end) {
		return filterFindByG_P(groupId, parentStructureId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the d d m structures that the user has permissions to view where groupId = &#63; and parentStructureId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMStructureModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param parentStructureId the parent structure ID
	 * @param start the lower bound of the range of d d m structures
	 * @param end the upper bound of the range of d d m structures (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching d d m structures that the user has permission to view
	 */
	@Override
	public List<DDMStructure> filterFindByG_P(long groupId,
		long parentStructureId, int start, int end,
		OrderByComparator<DDMStructure> orderByComparator) {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_P(groupId, parentStructureId, start, end,
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
			query.append(_FILTER_SQL_SELECT_DDMSTRUCTURE_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_DDMSTRUCTURE_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_G_P_GROUPID_2);

		query.append(_FINDER_COLUMN_G_P_PARENTSTRUCTUREID_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_DDMSTRUCTURE_NO_INLINE_DISTINCT_WHERE_2);
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
				query.append(DDMStructureModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(DDMStructureModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				DDMStructure.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				q.addEntity(_FILTER_ENTITY_ALIAS, DDMStructureImpl.class);
			}
			else {
				q.addEntity(_FILTER_ENTITY_TABLE, DDMStructureImpl.class);
			}

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			qPos.add(parentStructureId);

			return (List<DDMStructure>)QueryUtil.list(q, getDialect(), start,
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
	 * Returns the d d m structures before and after the current d d m structure in the ordered set of d d m structures that the user has permission to view where groupId = &#63; and parentStructureId = &#63;.
	 *
	 * @param structureId the primary key of the current d d m structure
	 * @param groupId the group ID
	 * @param parentStructureId the parent structure ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next d d m structure
	 * @throws NoSuchStructureException if a d d m structure with the primary key could not be found
	 */
	@Override
	public DDMStructure[] filterFindByG_P_PrevAndNext(long structureId,
		long groupId, long parentStructureId,
		OrderByComparator<DDMStructure> orderByComparator)
		throws NoSuchStructureException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_P_PrevAndNext(structureId, groupId,
				parentStructureId, orderByComparator);
		}

		DDMStructure ddmStructure = findByPrimaryKey(structureId);

		Session session = null;

		try {
			session = openSession();

			DDMStructure[] array = new DDMStructureImpl[3];

			array[0] = filterGetByG_P_PrevAndNext(session, ddmStructure,
					groupId, parentStructureId, orderByComparator, true);

			array[1] = ddmStructure;

			array[2] = filterGetByG_P_PrevAndNext(session, ddmStructure,
					groupId, parentStructureId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected DDMStructure filterGetByG_P_PrevAndNext(Session session,
		DDMStructure ddmStructure, long groupId, long parentStructureId,
		OrderByComparator<DDMStructure> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_DDMSTRUCTURE_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_DDMSTRUCTURE_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_G_P_GROUPID_2);

		query.append(_FINDER_COLUMN_G_P_PARENTSTRUCTUREID_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_DDMSTRUCTURE_NO_INLINE_DISTINCT_WHERE_2);
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
				query.append(DDMStructureModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(DDMStructureModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				DDMStructure.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		SQLQuery q = session.createSynchronizedSQLQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		if (getDB().isSupportsInlineDistinct()) {
			q.addEntity(_FILTER_ENTITY_ALIAS, DDMStructureImpl.class);
		}
		else {
			q.addEntity(_FILTER_ENTITY_TABLE, DDMStructureImpl.class);
		}

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		qPos.add(parentStructureId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(ddmStructure);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<DDMStructure> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the d d m structures where groupId = &#63; and parentStructureId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param parentStructureId the parent structure ID
	 */
	@Override
	public void removeByG_P(long groupId, long parentStructureId) {
		for (DDMStructure ddmStructure : findByG_P(groupId, parentStructureId,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(ddmStructure);
		}
	}

	/**
	 * Returns the number of d d m structures where groupId = &#63; and parentStructureId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param parentStructureId the parent structure ID
	 * @return the number of matching d d m structures
	 */
	@Override
	public int countByG_P(long groupId, long parentStructureId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_G_P;

		Object[] finderArgs = new Object[] { groupId, parentStructureId };

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs,
				this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_DDMSTRUCTURE_WHERE);

			query.append(_FINDER_COLUMN_G_P_GROUPID_2);

			query.append(_FINDER_COLUMN_G_P_PARENTSTRUCTUREID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(parentStructureId);

				count = (Long)q.uniqueResult();

				FinderCacheUtil.putResult(finderPath, finderArgs, count);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of d d m structures that the user has permission to view where groupId = &#63; and parentStructureId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param parentStructureId the parent structure ID
	 * @return the number of matching d d m structures that the user has permission to view
	 */
	@Override
	public int filterCountByG_P(long groupId, long parentStructureId) {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByG_P(groupId, parentStructureId);
		}

		StringBundler query = new StringBundler(3);

		query.append(_FILTER_SQL_COUNT_DDMSTRUCTURE_WHERE);

		query.append(_FINDER_COLUMN_G_P_GROUPID_2);

		query.append(_FINDER_COLUMN_G_P_PARENTSTRUCTUREID_2);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				DDMStructure.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME,
				com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			qPos.add(parentStructureId);

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

	private static final String _FINDER_COLUMN_G_P_GROUPID_2 = "ddmStructure.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_P_PARENTSTRUCTUREID_2 = "ddmStructure.parentStructureId = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_G_C = new FinderPath(DDMStructureModelImpl.ENTITY_CACHE_ENABLED,
			DDMStructureModelImpl.FINDER_CACHE_ENABLED, DDMStructureImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_C",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_C = new FinderPath(DDMStructureModelImpl.ENTITY_CACHE_ENABLED,
			DDMStructureModelImpl.FINDER_CACHE_ENABLED, DDMStructureImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByG_C",
			new String[] { Long.class.getName(), Long.class.getName() },
			DDMStructureModelImpl.GROUPID_COLUMN_BITMASK |
			DDMStructureModelImpl.CLASSNAMEID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_G_C = new FinderPath(DDMStructureModelImpl.ENTITY_CACHE_ENABLED,
			DDMStructureModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_C",
			new String[] { Long.class.getName(), Long.class.getName() });
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_COUNT_BY_G_C = new FinderPath(DDMStructureModelImpl.ENTITY_CACHE_ENABLED,
			DDMStructureModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "countByG_C",
			new String[] { Long.class.getName(), Long.class.getName() });

	/**
	 * Returns all the d d m structures where groupId = &#63; and classNameId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @return the matching d d m structures
	 */
	@Override
	public List<DDMStructure> findByG_C(long groupId, long classNameId) {
		return findByG_C(groupId, classNameId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the d d m structures where groupId = &#63; and classNameId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMStructureModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param start the lower bound of the range of d d m structures
	 * @param end the upper bound of the range of d d m structures (not inclusive)
	 * @return the range of matching d d m structures
	 */
	@Override
	public List<DDMStructure> findByG_C(long groupId, long classNameId,
		int start, int end) {
		return findByG_C(groupId, classNameId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the d d m structures where groupId = &#63; and classNameId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMStructureModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param start the lower bound of the range of d d m structures
	 * @param end the upper bound of the range of d d m structures (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching d d m structures
	 */
	@Override
	public List<DDMStructure> findByG_C(long groupId, long classNameId,
		int start, int end, OrderByComparator<DDMStructure> orderByComparator) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_C;
			finderArgs = new Object[] { groupId, classNameId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_G_C;
			finderArgs = new Object[] {
					groupId, classNameId,
					
					start, end, orderByComparator
				};
		}

		List<DDMStructure> list = (List<DDMStructure>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (DDMStructure ddmStructure : list) {
				if ((groupId != ddmStructure.getGroupId()) ||
						(classNameId != ddmStructure.getClassNameId())) {
					list = null;

					break;
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

			query.append(_SQL_SELECT_DDMSTRUCTURE_WHERE);

			query.append(_FINDER_COLUMN_G_C_GROUPID_2);

			query.append(_FINDER_COLUMN_G_C_CLASSNAMEID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(DDMStructureModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(classNameId);

				if (!pagination) {
					list = (List<DDMStructure>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<DDMStructure>)QueryUtil.list(q, getDialect(),
							start, end);
				}

				cacheResult(list);

				FinderCacheUtil.putResult(finderPath, finderArgs, list);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first d d m structure in the ordered set where groupId = &#63; and classNameId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching d d m structure
	 * @throws NoSuchStructureException if a matching d d m structure could not be found
	 */
	@Override
	public DDMStructure findByG_C_First(long groupId, long classNameId,
		OrderByComparator<DDMStructure> orderByComparator)
		throws NoSuchStructureException {
		DDMStructure ddmStructure = fetchByG_C_First(groupId, classNameId,
				orderByComparator);

		if (ddmStructure != null) {
			return ddmStructure;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", classNameId=");
		msg.append(classNameId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchStructureException(msg.toString());
	}

	/**
	 * Returns the first d d m structure in the ordered set where groupId = &#63; and classNameId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching d d m structure, or <code>null</code> if a matching d d m structure could not be found
	 */
	@Override
	public DDMStructure fetchByG_C_First(long groupId, long classNameId,
		OrderByComparator<DDMStructure> orderByComparator) {
		List<DDMStructure> list = findByG_C(groupId, classNameId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last d d m structure in the ordered set where groupId = &#63; and classNameId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching d d m structure
	 * @throws NoSuchStructureException if a matching d d m structure could not be found
	 */
	@Override
	public DDMStructure findByG_C_Last(long groupId, long classNameId,
		OrderByComparator<DDMStructure> orderByComparator)
		throws NoSuchStructureException {
		DDMStructure ddmStructure = fetchByG_C_Last(groupId, classNameId,
				orderByComparator);

		if (ddmStructure != null) {
			return ddmStructure;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", classNameId=");
		msg.append(classNameId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchStructureException(msg.toString());
	}

	/**
	 * Returns the last d d m structure in the ordered set where groupId = &#63; and classNameId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching d d m structure, or <code>null</code> if a matching d d m structure could not be found
	 */
	@Override
	public DDMStructure fetchByG_C_Last(long groupId, long classNameId,
		OrderByComparator<DDMStructure> orderByComparator) {
		int count = countByG_C(groupId, classNameId);

		if (count == 0) {
			return null;
		}

		List<DDMStructure> list = findByG_C(groupId, classNameId, count - 1,
				count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the d d m structures before and after the current d d m structure in the ordered set where groupId = &#63; and classNameId = &#63;.
	 *
	 * @param structureId the primary key of the current d d m structure
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next d d m structure
	 * @throws NoSuchStructureException if a d d m structure with the primary key could not be found
	 */
	@Override
	public DDMStructure[] findByG_C_PrevAndNext(long structureId, long groupId,
		long classNameId, OrderByComparator<DDMStructure> orderByComparator)
		throws NoSuchStructureException {
		DDMStructure ddmStructure = findByPrimaryKey(structureId);

		Session session = null;

		try {
			session = openSession();

			DDMStructure[] array = new DDMStructureImpl[3];

			array[0] = getByG_C_PrevAndNext(session, ddmStructure, groupId,
					classNameId, orderByComparator, true);

			array[1] = ddmStructure;

			array[2] = getByG_C_PrevAndNext(session, ddmStructure, groupId,
					classNameId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected DDMStructure getByG_C_PrevAndNext(Session session,
		DDMStructure ddmStructure, long groupId, long classNameId,
		OrderByComparator<DDMStructure> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_DDMSTRUCTURE_WHERE);

		query.append(_FINDER_COLUMN_G_C_GROUPID_2);

		query.append(_FINDER_COLUMN_G_C_CLASSNAMEID_2);

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
			query.append(DDMStructureModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		qPos.add(classNameId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(ddmStructure);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<DDMStructure> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the d d m structures that the user has permission to view where groupId = &#63; and classNameId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @return the matching d d m structures that the user has permission to view
	 */
	@Override
	public List<DDMStructure> filterFindByG_C(long groupId, long classNameId) {
		return filterFindByG_C(groupId, classNameId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the d d m structures that the user has permission to view where groupId = &#63; and classNameId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMStructureModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param start the lower bound of the range of d d m structures
	 * @param end the upper bound of the range of d d m structures (not inclusive)
	 * @return the range of matching d d m structures that the user has permission to view
	 */
	@Override
	public List<DDMStructure> filterFindByG_C(long groupId, long classNameId,
		int start, int end) {
		return filterFindByG_C(groupId, classNameId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the d d m structures that the user has permissions to view where groupId = &#63; and classNameId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMStructureModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param start the lower bound of the range of d d m structures
	 * @param end the upper bound of the range of d d m structures (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching d d m structures that the user has permission to view
	 */
	@Override
	public List<DDMStructure> filterFindByG_C(long groupId, long classNameId,
		int start, int end, OrderByComparator<DDMStructure> orderByComparator) {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_C(groupId, classNameId, start, end, orderByComparator);
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
			query.append(_FILTER_SQL_SELECT_DDMSTRUCTURE_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_DDMSTRUCTURE_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_G_C_GROUPID_2);

		query.append(_FINDER_COLUMN_G_C_CLASSNAMEID_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_DDMSTRUCTURE_NO_INLINE_DISTINCT_WHERE_2);
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
				query.append(DDMStructureModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(DDMStructureModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				DDMStructure.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				q.addEntity(_FILTER_ENTITY_ALIAS, DDMStructureImpl.class);
			}
			else {
				q.addEntity(_FILTER_ENTITY_TABLE, DDMStructureImpl.class);
			}

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			qPos.add(classNameId);

			return (List<DDMStructure>)QueryUtil.list(q, getDialect(), start,
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
	 * Returns the d d m structures before and after the current d d m structure in the ordered set of d d m structures that the user has permission to view where groupId = &#63; and classNameId = &#63;.
	 *
	 * @param structureId the primary key of the current d d m structure
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next d d m structure
	 * @throws NoSuchStructureException if a d d m structure with the primary key could not be found
	 */
	@Override
	public DDMStructure[] filterFindByG_C_PrevAndNext(long structureId,
		long groupId, long classNameId,
		OrderByComparator<DDMStructure> orderByComparator)
		throws NoSuchStructureException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_C_PrevAndNext(structureId, groupId, classNameId,
				orderByComparator);
		}

		DDMStructure ddmStructure = findByPrimaryKey(structureId);

		Session session = null;

		try {
			session = openSession();

			DDMStructure[] array = new DDMStructureImpl[3];

			array[0] = filterGetByG_C_PrevAndNext(session, ddmStructure,
					groupId, classNameId, orderByComparator, true);

			array[1] = ddmStructure;

			array[2] = filterGetByG_C_PrevAndNext(session, ddmStructure,
					groupId, classNameId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected DDMStructure filterGetByG_C_PrevAndNext(Session session,
		DDMStructure ddmStructure, long groupId, long classNameId,
		OrderByComparator<DDMStructure> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_DDMSTRUCTURE_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_DDMSTRUCTURE_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_G_C_GROUPID_2);

		query.append(_FINDER_COLUMN_G_C_CLASSNAMEID_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_DDMSTRUCTURE_NO_INLINE_DISTINCT_WHERE_2);
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
				query.append(DDMStructureModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(DDMStructureModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				DDMStructure.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		SQLQuery q = session.createSynchronizedSQLQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		if (getDB().isSupportsInlineDistinct()) {
			q.addEntity(_FILTER_ENTITY_ALIAS, DDMStructureImpl.class);
		}
		else {
			q.addEntity(_FILTER_ENTITY_TABLE, DDMStructureImpl.class);
		}

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		qPos.add(classNameId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(ddmStructure);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<DDMStructure> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the d d m structures that the user has permission to view where groupId = any &#63; and classNameId = &#63;.
	 *
	 * @param groupIds the group IDs
	 * @param classNameId the class name ID
	 * @return the matching d d m structures that the user has permission to view
	 */
	@Override
	public List<DDMStructure> filterFindByG_C(long[] groupIds, long classNameId) {
		return filterFindByG_C(groupIds, classNameId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the d d m structures that the user has permission to view where groupId = any &#63; and classNameId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMStructureModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupIds the group IDs
	 * @param classNameId the class name ID
	 * @param start the lower bound of the range of d d m structures
	 * @param end the upper bound of the range of d d m structures (not inclusive)
	 * @return the range of matching d d m structures that the user has permission to view
	 */
	@Override
	public List<DDMStructure> filterFindByG_C(long[] groupIds,
		long classNameId, int start, int end) {
		return filterFindByG_C(groupIds, classNameId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the d d m structures that the user has permission to view where groupId = any &#63; and classNameId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMStructureModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupIds the group IDs
	 * @param classNameId the class name ID
	 * @param start the lower bound of the range of d d m structures
	 * @param end the upper bound of the range of d d m structures (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching d d m structures that the user has permission to view
	 */
	@Override
	public List<DDMStructure> filterFindByG_C(long[] groupIds,
		long classNameId, int start, int end,
		OrderByComparator<DDMStructure> orderByComparator) {
		if (!InlineSQLHelperUtil.isEnabled(groupIds)) {
			return findByG_C(groupIds, classNameId, start, end,
				orderByComparator);
		}

		if (groupIds == null) {
			groupIds = new long[0];
		}
		else {
			groupIds = ArrayUtil.unique(groupIds);
		}

		StringBundler query = new StringBundler();

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_DDMSTRUCTURE_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_DDMSTRUCTURE_NO_INLINE_DISTINCT_WHERE_1);
		}

		if (groupIds.length > 0) {
			query.append(StringPool.OPEN_PARENTHESIS);

			query.append(_FINDER_COLUMN_G_C_GROUPID_7);

			query.append(StringUtil.merge(groupIds));

			query.append(StringPool.CLOSE_PARENTHESIS);

			query.append(StringPool.CLOSE_PARENTHESIS);

			query.append(WHERE_AND);
		}

		query.append(_FINDER_COLUMN_G_C_CLASSNAMEID_2);

		query.setStringAt(removeConjunction(query.stringAt(query.index() - 1)),
			query.index() - 1);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_DDMSTRUCTURE_NO_INLINE_DISTINCT_WHERE_2);
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
				query.append(DDMStructureModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(DDMStructureModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				DDMStructure.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupIds);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				q.addEntity(_FILTER_ENTITY_ALIAS, DDMStructureImpl.class);
			}
			else {
				q.addEntity(_FILTER_ENTITY_TABLE, DDMStructureImpl.class);
			}

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(classNameId);

			return (List<DDMStructure>)QueryUtil.list(q, getDialect(), start,
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
	 * Returns all the d d m structures where groupId = any &#63; and classNameId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMStructureModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupIds the group IDs
	 * @param classNameId the class name ID
	 * @return the matching d d m structures
	 */
	@Override
	public List<DDMStructure> findByG_C(long[] groupIds, long classNameId) {
		return findByG_C(groupIds, classNameId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the d d m structures where groupId = any &#63; and classNameId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMStructureModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupIds the group IDs
	 * @param classNameId the class name ID
	 * @param start the lower bound of the range of d d m structures
	 * @param end the upper bound of the range of d d m structures (not inclusive)
	 * @return the range of matching d d m structures
	 */
	@Override
	public List<DDMStructure> findByG_C(long[] groupIds, long classNameId,
		int start, int end) {
		return findByG_C(groupIds, classNameId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the d d m structures where groupId = any &#63; and classNameId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMStructureModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupIds the group IDs
	 * @param classNameId the class name ID
	 * @param start the lower bound of the range of d d m structures
	 * @param end the upper bound of the range of d d m structures (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching d d m structures
	 */
	@Override
	public List<DDMStructure> findByG_C(long[] groupIds, long classNameId,
		int start, int end, OrderByComparator<DDMStructure> orderByComparator) {
		if (groupIds == null) {
			groupIds = new long[0];
		}
		else {
			groupIds = ArrayUtil.unique(groupIds);
		}

		if (groupIds.length == 1) {
			return findByG_C(groupIds[0], classNameId, start, end,
				orderByComparator);
		}

		boolean pagination = true;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderArgs = new Object[] { StringUtil.merge(groupIds), classNameId };
		}
		else {
			finderArgs = new Object[] {
					StringUtil.merge(groupIds), classNameId,
					
					start, end, orderByComparator
				};
		}

		List<DDMStructure> list = (List<DDMStructure>)FinderCacheUtil.getResult(FINDER_PATH_WITH_PAGINATION_FIND_BY_G_C,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (DDMStructure ddmStructure : list) {
				if (!ArrayUtil.contains(groupIds, ddmStructure.getGroupId()) ||
						(classNameId != ddmStructure.getClassNameId())) {
					list = null;

					break;
				}
			}
		}

		if (list == null) {
			StringBundler query = new StringBundler();

			query.append(_SQL_SELECT_DDMSTRUCTURE_WHERE);

			if (groupIds.length > 0) {
				query.append(StringPool.OPEN_PARENTHESIS);

				query.append(_FINDER_COLUMN_G_C_GROUPID_7);

				query.append(StringUtil.merge(groupIds));

				query.append(StringPool.CLOSE_PARENTHESIS);

				query.append(StringPool.CLOSE_PARENTHESIS);

				query.append(WHERE_AND);
			}

			query.append(_FINDER_COLUMN_G_C_CLASSNAMEID_2);

			query.setStringAt(removeConjunction(query.stringAt(query.index() -
						1)), query.index() - 1);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(DDMStructureModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(classNameId);

				if (!pagination) {
					list = (List<DDMStructure>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<DDMStructure>)QueryUtil.list(q, getDialect(),
							start, end);
				}

				cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_WITH_PAGINATION_FIND_BY_G_C,
					finderArgs, list);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(FINDER_PATH_WITH_PAGINATION_FIND_BY_G_C,
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
	 * Removes all the d d m structures where groupId = &#63; and classNameId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 */
	@Override
	public void removeByG_C(long groupId, long classNameId) {
		for (DDMStructure ddmStructure : findByG_C(groupId, classNameId,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(ddmStructure);
		}
	}

	/**
	 * Returns the number of d d m structures where groupId = &#63; and classNameId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @return the number of matching d d m structures
	 */
	@Override
	public int countByG_C(long groupId, long classNameId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_G_C;

		Object[] finderArgs = new Object[] { groupId, classNameId };

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs,
				this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_DDMSTRUCTURE_WHERE);

			query.append(_FINDER_COLUMN_G_C_GROUPID_2);

			query.append(_FINDER_COLUMN_G_C_CLASSNAMEID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(classNameId);

				count = (Long)q.uniqueResult();

				FinderCacheUtil.putResult(finderPath, finderArgs, count);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of d d m structures where groupId = any &#63; and classNameId = &#63;.
	 *
	 * @param groupIds the group IDs
	 * @param classNameId the class name ID
	 * @return the number of matching d d m structures
	 */
	@Override
	public int countByG_C(long[] groupIds, long classNameId) {
		if (groupIds == null) {
			groupIds = new long[0];
		}
		else {
			groupIds = ArrayUtil.unique(groupIds);
		}

		Object[] finderArgs = new Object[] {
				StringUtil.merge(groupIds), classNameId
			};

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_WITH_PAGINATION_COUNT_BY_G_C,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler();

			query.append(_SQL_COUNT_DDMSTRUCTURE_WHERE);

			if (groupIds.length > 0) {
				query.append(StringPool.OPEN_PARENTHESIS);

				query.append(_FINDER_COLUMN_G_C_GROUPID_7);

				query.append(StringUtil.merge(groupIds));

				query.append(StringPool.CLOSE_PARENTHESIS);

				query.append(StringPool.CLOSE_PARENTHESIS);

				query.append(WHERE_AND);
			}

			query.append(_FINDER_COLUMN_G_C_CLASSNAMEID_2);

			query.setStringAt(removeConjunction(query.stringAt(query.index() -
						1)), query.index() - 1);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(classNameId);

				count = (Long)q.uniqueResult();

				FinderCacheUtil.putResult(FINDER_PATH_WITH_PAGINATION_COUNT_BY_G_C,
					finderArgs, count);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(FINDER_PATH_WITH_PAGINATION_COUNT_BY_G_C,
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
	 * Returns the number of d d m structures that the user has permission to view where groupId = &#63; and classNameId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @return the number of matching d d m structures that the user has permission to view
	 */
	@Override
	public int filterCountByG_C(long groupId, long classNameId) {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByG_C(groupId, classNameId);
		}

		StringBundler query = new StringBundler(3);

		query.append(_FILTER_SQL_COUNT_DDMSTRUCTURE_WHERE);

		query.append(_FINDER_COLUMN_G_C_GROUPID_2);

		query.append(_FINDER_COLUMN_G_C_CLASSNAMEID_2);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				DDMStructure.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME,
				com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			qPos.add(classNameId);

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
	 * Returns the number of d d m structures that the user has permission to view where groupId = any &#63; and classNameId = &#63;.
	 *
	 * @param groupIds the group IDs
	 * @param classNameId the class name ID
	 * @return the number of matching d d m structures that the user has permission to view
	 */
	@Override
	public int filterCountByG_C(long[] groupIds, long classNameId) {
		if (!InlineSQLHelperUtil.isEnabled(groupIds)) {
			return countByG_C(groupIds, classNameId);
		}

		if (groupIds == null) {
			groupIds = new long[0];
		}
		else {
			groupIds = ArrayUtil.unique(groupIds);
		}

		StringBundler query = new StringBundler();

		query.append(_FILTER_SQL_COUNT_DDMSTRUCTURE_WHERE);

		if (groupIds.length > 0) {
			query.append(StringPool.OPEN_PARENTHESIS);

			query.append(_FINDER_COLUMN_G_C_GROUPID_7);

			query.append(StringUtil.merge(groupIds));

			query.append(StringPool.CLOSE_PARENTHESIS);

			query.append(StringPool.CLOSE_PARENTHESIS);

			query.append(WHERE_AND);
		}

		query.append(_FINDER_COLUMN_G_C_CLASSNAMEID_2);

		query.setStringAt(removeConjunction(query.stringAt(query.index() - 1)),
			query.index() - 1);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				DDMStructure.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupIds);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME,
				com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(classNameId);

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

	private static final String _FINDER_COLUMN_G_C_GROUPID_2 = "ddmStructure.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_C_GROUPID_7 = "ddmStructure.groupId IN (";
	private static final String _FINDER_COLUMN_G_C_CLASSNAMEID_2 = "ddmStructure.classNameId = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_C_C = new FinderPath(DDMStructureModelImpl.ENTITY_CACHE_ENABLED,
			DDMStructureModelImpl.FINDER_CACHE_ENABLED, DDMStructureImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByC_C",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_C = new FinderPath(DDMStructureModelImpl.ENTITY_CACHE_ENABLED,
			DDMStructureModelImpl.FINDER_CACHE_ENABLED, DDMStructureImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByC_C",
			new String[] { Long.class.getName(), Long.class.getName() },
			DDMStructureModelImpl.COMPANYID_COLUMN_BITMASK |
			DDMStructureModelImpl.CLASSNAMEID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_C_C = new FinderPath(DDMStructureModelImpl.ENTITY_CACHE_ENABLED,
			DDMStructureModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_C",
			new String[] { Long.class.getName(), Long.class.getName() });

	/**
	 * Returns all the d d m structures where companyId = &#63; and classNameId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @return the matching d d m structures
	 */
	@Override
	public List<DDMStructure> findByC_C(long companyId, long classNameId) {
		return findByC_C(companyId, classNameId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the d d m structures where companyId = &#63; and classNameId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMStructureModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param start the lower bound of the range of d d m structures
	 * @param end the upper bound of the range of d d m structures (not inclusive)
	 * @return the range of matching d d m structures
	 */
	@Override
	public List<DDMStructure> findByC_C(long companyId, long classNameId,
		int start, int end) {
		return findByC_C(companyId, classNameId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the d d m structures where companyId = &#63; and classNameId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMStructureModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param start the lower bound of the range of d d m structures
	 * @param end the upper bound of the range of d d m structures (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching d d m structures
	 */
	@Override
	public List<DDMStructure> findByC_C(long companyId, long classNameId,
		int start, int end, OrderByComparator<DDMStructure> orderByComparator) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_C;
			finderArgs = new Object[] { companyId, classNameId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_C_C;
			finderArgs = new Object[] {
					companyId, classNameId,
					
					start, end, orderByComparator
				};
		}

		List<DDMStructure> list = (List<DDMStructure>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (DDMStructure ddmStructure : list) {
				if ((companyId != ddmStructure.getCompanyId()) ||
						(classNameId != ddmStructure.getClassNameId())) {
					list = null;

					break;
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

			query.append(_SQL_SELECT_DDMSTRUCTURE_WHERE);

			query.append(_FINDER_COLUMN_C_C_COMPANYID_2);

			query.append(_FINDER_COLUMN_C_C_CLASSNAMEID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(DDMStructureModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				qPos.add(classNameId);

				if (!pagination) {
					list = (List<DDMStructure>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<DDMStructure>)QueryUtil.list(q, getDialect(),
							start, end);
				}

				cacheResult(list);

				FinderCacheUtil.putResult(finderPath, finderArgs, list);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first d d m structure in the ordered set where companyId = &#63; and classNameId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching d d m structure
	 * @throws NoSuchStructureException if a matching d d m structure could not be found
	 */
	@Override
	public DDMStructure findByC_C_First(long companyId, long classNameId,
		OrderByComparator<DDMStructure> orderByComparator)
		throws NoSuchStructureException {
		DDMStructure ddmStructure = fetchByC_C_First(companyId, classNameId,
				orderByComparator);

		if (ddmStructure != null) {
			return ddmStructure;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("companyId=");
		msg.append(companyId);

		msg.append(", classNameId=");
		msg.append(classNameId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchStructureException(msg.toString());
	}

	/**
	 * Returns the first d d m structure in the ordered set where companyId = &#63; and classNameId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching d d m structure, or <code>null</code> if a matching d d m structure could not be found
	 */
	@Override
	public DDMStructure fetchByC_C_First(long companyId, long classNameId,
		OrderByComparator<DDMStructure> orderByComparator) {
		List<DDMStructure> list = findByC_C(companyId, classNameId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last d d m structure in the ordered set where companyId = &#63; and classNameId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching d d m structure
	 * @throws NoSuchStructureException if a matching d d m structure could not be found
	 */
	@Override
	public DDMStructure findByC_C_Last(long companyId, long classNameId,
		OrderByComparator<DDMStructure> orderByComparator)
		throws NoSuchStructureException {
		DDMStructure ddmStructure = fetchByC_C_Last(companyId, classNameId,
				orderByComparator);

		if (ddmStructure != null) {
			return ddmStructure;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("companyId=");
		msg.append(companyId);

		msg.append(", classNameId=");
		msg.append(classNameId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchStructureException(msg.toString());
	}

	/**
	 * Returns the last d d m structure in the ordered set where companyId = &#63; and classNameId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching d d m structure, or <code>null</code> if a matching d d m structure could not be found
	 */
	@Override
	public DDMStructure fetchByC_C_Last(long companyId, long classNameId,
		OrderByComparator<DDMStructure> orderByComparator) {
		int count = countByC_C(companyId, classNameId);

		if (count == 0) {
			return null;
		}

		List<DDMStructure> list = findByC_C(companyId, classNameId, count - 1,
				count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the d d m structures before and after the current d d m structure in the ordered set where companyId = &#63; and classNameId = &#63;.
	 *
	 * @param structureId the primary key of the current d d m structure
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next d d m structure
	 * @throws NoSuchStructureException if a d d m structure with the primary key could not be found
	 */
	@Override
	public DDMStructure[] findByC_C_PrevAndNext(long structureId,
		long companyId, long classNameId,
		OrderByComparator<DDMStructure> orderByComparator)
		throws NoSuchStructureException {
		DDMStructure ddmStructure = findByPrimaryKey(structureId);

		Session session = null;

		try {
			session = openSession();

			DDMStructure[] array = new DDMStructureImpl[3];

			array[0] = getByC_C_PrevAndNext(session, ddmStructure, companyId,
					classNameId, orderByComparator, true);

			array[1] = ddmStructure;

			array[2] = getByC_C_PrevAndNext(session, ddmStructure, companyId,
					classNameId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected DDMStructure getByC_C_PrevAndNext(Session session,
		DDMStructure ddmStructure, long companyId, long classNameId,
		OrderByComparator<DDMStructure> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_DDMSTRUCTURE_WHERE);

		query.append(_FINDER_COLUMN_C_C_COMPANYID_2);

		query.append(_FINDER_COLUMN_C_C_CLASSNAMEID_2);

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
			query.append(DDMStructureModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(companyId);

		qPos.add(classNameId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(ddmStructure);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<DDMStructure> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the d d m structures where companyId = &#63; and classNameId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 */
	@Override
	public void removeByC_C(long companyId, long classNameId) {
		for (DDMStructure ddmStructure : findByC_C(companyId, classNameId,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(ddmStructure);
		}
	}

	/**
	 * Returns the number of d d m structures where companyId = &#63; and classNameId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @return the number of matching d d m structures
	 */
	@Override
	public int countByC_C(long companyId, long classNameId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_C_C;

		Object[] finderArgs = new Object[] { companyId, classNameId };

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs,
				this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_DDMSTRUCTURE_WHERE);

			query.append(_FINDER_COLUMN_C_C_COMPANYID_2);

			query.append(_FINDER_COLUMN_C_C_CLASSNAMEID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				qPos.add(classNameId);

				count = (Long)q.uniqueResult();

				FinderCacheUtil.putResult(finderPath, finderArgs, count);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_C_C_COMPANYID_2 = "ddmStructure.companyId = ? AND ";
	private static final String _FINDER_COLUMN_C_C_CLASSNAMEID_2 = "ddmStructure.classNameId = ?";
	public static final FinderPath FINDER_PATH_FETCH_BY_G_C_S = new FinderPath(DDMStructureModelImpl.ENTITY_CACHE_ENABLED,
			DDMStructureModelImpl.FINDER_CACHE_ENABLED, DDMStructureImpl.class,
			FINDER_CLASS_NAME_ENTITY, "fetchByG_C_S",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				String.class.getName()
			},
			DDMStructureModelImpl.GROUPID_COLUMN_BITMASK |
			DDMStructureModelImpl.CLASSNAMEID_COLUMN_BITMASK |
			DDMStructureModelImpl.STRUCTUREKEY_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_G_C_S = new FinderPath(DDMStructureModelImpl.ENTITY_CACHE_ENABLED,
			DDMStructureModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_C_S",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				String.class.getName()
			});

	/**
	 * Returns the d d m structure where groupId = &#63; and classNameId = &#63; and structureKey = &#63; or throws a {@link NoSuchStructureException} if it could not be found.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param structureKey the structure key
	 * @return the matching d d m structure
	 * @throws NoSuchStructureException if a matching d d m structure could not be found
	 */
	@Override
	public DDMStructure findByG_C_S(long groupId, long classNameId,
		String structureKey) throws NoSuchStructureException {
		DDMStructure ddmStructure = fetchByG_C_S(groupId, classNameId,
				structureKey);

		if (ddmStructure == null) {
			StringBundler msg = new StringBundler(8);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("groupId=");
			msg.append(groupId);

			msg.append(", classNameId=");
			msg.append(classNameId);

			msg.append(", structureKey=");
			msg.append(structureKey);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchStructureException(msg.toString());
		}

		return ddmStructure;
	}

	/**
	 * Returns the d d m structure where groupId = &#63; and classNameId = &#63; and structureKey = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param structureKey the structure key
	 * @return the matching d d m structure, or <code>null</code> if a matching d d m structure could not be found
	 */
	@Override
	public DDMStructure fetchByG_C_S(long groupId, long classNameId,
		String structureKey) {
		return fetchByG_C_S(groupId, classNameId, structureKey, true);
	}

	/**
	 * Returns the d d m structure where groupId = &#63; and classNameId = &#63; and structureKey = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param structureKey the structure key
	 * @param retrieveFromCache whether to use the finder cache
	 * @return the matching d d m structure, or <code>null</code> if a matching d d m structure could not be found
	 */
	@Override
	public DDMStructure fetchByG_C_S(long groupId, long classNameId,
		String structureKey, boolean retrieveFromCache) {
		Object[] finderArgs = new Object[] { groupId, classNameId, structureKey };

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_G_C_S,
					finderArgs, this);
		}

		if (result instanceof DDMStructure) {
			DDMStructure ddmStructure = (DDMStructure)result;

			if ((groupId != ddmStructure.getGroupId()) ||
					(classNameId != ddmStructure.getClassNameId()) ||
					!Validator.equals(structureKey,
						ddmStructure.getStructureKey())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(5);

			query.append(_SQL_SELECT_DDMSTRUCTURE_WHERE);

			query.append(_FINDER_COLUMN_G_C_S_GROUPID_2);

			query.append(_FINDER_COLUMN_G_C_S_CLASSNAMEID_2);

			boolean bindStructureKey = false;

			if (structureKey == null) {
				query.append(_FINDER_COLUMN_G_C_S_STRUCTUREKEY_1);
			}
			else if (structureKey.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_G_C_S_STRUCTUREKEY_3);
			}
			else {
				bindStructureKey = true;

				query.append(_FINDER_COLUMN_G_C_S_STRUCTUREKEY_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(classNameId);

				if (bindStructureKey) {
					qPos.add(structureKey);
				}

				List<DDMStructure> list = q.list();

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_C_S,
						finderArgs, list);
				}
				else {
					DDMStructure ddmStructure = list.get(0);

					result = ddmStructure;

					cacheResult(ddmStructure);

					if ((ddmStructure.getGroupId() != groupId) ||
							(ddmStructure.getClassNameId() != classNameId) ||
							(ddmStructure.getStructureKey() == null) ||
							!ddmStructure.getStructureKey().equals(structureKey)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_C_S,
							finderArgs, ddmStructure);
					}
				}
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_G_C_S,
					finderArgs);

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
			return (DDMStructure)result;
		}
	}

	/**
	 * Removes the d d m structure where groupId = &#63; and classNameId = &#63; and structureKey = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param structureKey the structure key
	 * @return the d d m structure that was removed
	 */
	@Override
	public DDMStructure removeByG_C_S(long groupId, long classNameId,
		String structureKey) throws NoSuchStructureException {
		DDMStructure ddmStructure = findByG_C_S(groupId, classNameId,
				structureKey);

		return remove(ddmStructure);
	}

	/**
	 * Returns the number of d d m structures where groupId = &#63; and classNameId = &#63; and structureKey = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param structureKey the structure key
	 * @return the number of matching d d m structures
	 */
	@Override
	public int countByG_C_S(long groupId, long classNameId, String structureKey) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_G_C_S;

		Object[] finderArgs = new Object[] { groupId, classNameId, structureKey };

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs,
				this);

		if (count == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_COUNT_DDMSTRUCTURE_WHERE);

			query.append(_FINDER_COLUMN_G_C_S_GROUPID_2);

			query.append(_FINDER_COLUMN_G_C_S_CLASSNAMEID_2);

			boolean bindStructureKey = false;

			if (structureKey == null) {
				query.append(_FINDER_COLUMN_G_C_S_STRUCTUREKEY_1);
			}
			else if (structureKey.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_G_C_S_STRUCTUREKEY_3);
			}
			else {
				bindStructureKey = true;

				query.append(_FINDER_COLUMN_G_C_S_STRUCTUREKEY_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(classNameId);

				if (bindStructureKey) {
					qPos.add(structureKey);
				}

				count = (Long)q.uniqueResult();

				FinderCacheUtil.putResult(finderPath, finderArgs, count);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_G_C_S_GROUPID_2 = "ddmStructure.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_C_S_CLASSNAMEID_2 = "ddmStructure.classNameId = ? AND ";
	private static final String _FINDER_COLUMN_G_C_S_STRUCTUREKEY_1 = "ddmStructure.structureKey IS NULL";
	private static final String _FINDER_COLUMN_G_C_S_STRUCTUREKEY_2 = "ddmStructure.structureKey = ?";
	private static final String _FINDER_COLUMN_G_C_S_STRUCTUREKEY_3 = "(ddmStructure.structureKey IS NULL OR ddmStructure.structureKey = '')";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_G_N_D = new FinderPath(DDMStructureModelImpl.ENTITY_CACHE_ENABLED,
			DDMStructureModelImpl.FINDER_CACHE_ENABLED, DDMStructureImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_N_D",
			new String[] {
				Long.class.getName(), String.class.getName(),
				String.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_N_D = new FinderPath(DDMStructureModelImpl.ENTITY_CACHE_ENABLED,
			DDMStructureModelImpl.FINDER_CACHE_ENABLED, DDMStructureImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByG_N_D",
			new String[] {
				Long.class.getName(), String.class.getName(),
				String.class.getName()
			},
			DDMStructureModelImpl.GROUPID_COLUMN_BITMASK |
			DDMStructureModelImpl.NAME_COLUMN_BITMASK |
			DDMStructureModelImpl.DESCRIPTION_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_G_N_D = new FinderPath(DDMStructureModelImpl.ENTITY_CACHE_ENABLED,
			DDMStructureModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_N_D",
			new String[] {
				Long.class.getName(), String.class.getName(),
				String.class.getName()
			});

	/**
	 * Returns all the d d m structures where groupId = &#63; and name = &#63; and description = &#63;.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param description the description
	 * @return the matching d d m structures
	 */
	@Override
	public List<DDMStructure> findByG_N_D(long groupId, String name,
		String description) {
		return findByG_N_D(groupId, name, description, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the d d m structures where groupId = &#63; and name = &#63; and description = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMStructureModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param description the description
	 * @param start the lower bound of the range of d d m structures
	 * @param end the upper bound of the range of d d m structures (not inclusive)
	 * @return the range of matching d d m structures
	 */
	@Override
	public List<DDMStructure> findByG_N_D(long groupId, String name,
		String description, int start, int end) {
		return findByG_N_D(groupId, name, description, start, end, null);
	}

	/**
	 * Returns an ordered range of all the d d m structures where groupId = &#63; and name = &#63; and description = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMStructureModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param description the description
	 * @param start the lower bound of the range of d d m structures
	 * @param end the upper bound of the range of d d m structures (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching d d m structures
	 */
	@Override
	public List<DDMStructure> findByG_N_D(long groupId, String name,
		String description, int start, int end,
		OrderByComparator<DDMStructure> orderByComparator) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_N_D;
			finderArgs = new Object[] { groupId, name, description };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_G_N_D;
			finderArgs = new Object[] {
					groupId, name, description,
					
					start, end, orderByComparator
				};
		}

		List<DDMStructure> list = (List<DDMStructure>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (DDMStructure ddmStructure : list) {
				if ((groupId != ddmStructure.getGroupId()) ||
						!Validator.equals(name, ddmStructure.getName()) ||
						!Validator.equals(description,
							ddmStructure.getDescription())) {
					list = null;

					break;
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

			query.append(_SQL_SELECT_DDMSTRUCTURE_WHERE);

			query.append(_FINDER_COLUMN_G_N_D_GROUPID_2);

			boolean bindName = false;

			if (name == null) {
				query.append(_FINDER_COLUMN_G_N_D_NAME_1);
			}
			else if (name.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_G_N_D_NAME_3);
			}
			else {
				bindName = true;

				query.append(_FINDER_COLUMN_G_N_D_NAME_2);
			}

			boolean bindDescription = false;

			if (description == null) {
				query.append(_FINDER_COLUMN_G_N_D_DESCRIPTION_1);
			}
			else if (description.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_G_N_D_DESCRIPTION_3);
			}
			else {
				bindDescription = true;

				query.append(_FINDER_COLUMN_G_N_D_DESCRIPTION_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(DDMStructureModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (bindName) {
					qPos.add(name);
				}

				if (bindDescription) {
					qPos.add(description);
				}

				if (!pagination) {
					list = (List<DDMStructure>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<DDMStructure>)QueryUtil.list(q, getDialect(),
							start, end);
				}

				cacheResult(list);

				FinderCacheUtil.putResult(finderPath, finderArgs, list);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first d d m structure in the ordered set where groupId = &#63; and name = &#63; and description = &#63;.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param description the description
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching d d m structure
	 * @throws NoSuchStructureException if a matching d d m structure could not be found
	 */
	@Override
	public DDMStructure findByG_N_D_First(long groupId, String name,
		String description, OrderByComparator<DDMStructure> orderByComparator)
		throws NoSuchStructureException {
		DDMStructure ddmStructure = fetchByG_N_D_First(groupId, name,
				description, orderByComparator);

		if (ddmStructure != null) {
			return ddmStructure;
		}

		StringBundler msg = new StringBundler(8);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", name=");
		msg.append(name);

		msg.append(", description=");
		msg.append(description);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchStructureException(msg.toString());
	}

	/**
	 * Returns the first d d m structure in the ordered set where groupId = &#63; and name = &#63; and description = &#63;.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param description the description
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching d d m structure, or <code>null</code> if a matching d d m structure could not be found
	 */
	@Override
	public DDMStructure fetchByG_N_D_First(long groupId, String name,
		String description, OrderByComparator<DDMStructure> orderByComparator) {
		List<DDMStructure> list = findByG_N_D(groupId, name, description, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last d d m structure in the ordered set where groupId = &#63; and name = &#63; and description = &#63;.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param description the description
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching d d m structure
	 * @throws NoSuchStructureException if a matching d d m structure could not be found
	 */
	@Override
	public DDMStructure findByG_N_D_Last(long groupId, String name,
		String description, OrderByComparator<DDMStructure> orderByComparator)
		throws NoSuchStructureException {
		DDMStructure ddmStructure = fetchByG_N_D_Last(groupId, name,
				description, orderByComparator);

		if (ddmStructure != null) {
			return ddmStructure;
		}

		StringBundler msg = new StringBundler(8);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", name=");
		msg.append(name);

		msg.append(", description=");
		msg.append(description);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchStructureException(msg.toString());
	}

	/**
	 * Returns the last d d m structure in the ordered set where groupId = &#63; and name = &#63; and description = &#63;.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param description the description
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching d d m structure, or <code>null</code> if a matching d d m structure could not be found
	 */
	@Override
	public DDMStructure fetchByG_N_D_Last(long groupId, String name,
		String description, OrderByComparator<DDMStructure> orderByComparator) {
		int count = countByG_N_D(groupId, name, description);

		if (count == 0) {
			return null;
		}

		List<DDMStructure> list = findByG_N_D(groupId, name, description,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the d d m structures before and after the current d d m structure in the ordered set where groupId = &#63; and name = &#63; and description = &#63;.
	 *
	 * @param structureId the primary key of the current d d m structure
	 * @param groupId the group ID
	 * @param name the name
	 * @param description the description
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next d d m structure
	 * @throws NoSuchStructureException if a d d m structure with the primary key could not be found
	 */
	@Override
	public DDMStructure[] findByG_N_D_PrevAndNext(long structureId,
		long groupId, String name, String description,
		OrderByComparator<DDMStructure> orderByComparator)
		throws NoSuchStructureException {
		DDMStructure ddmStructure = findByPrimaryKey(structureId);

		Session session = null;

		try {
			session = openSession();

			DDMStructure[] array = new DDMStructureImpl[3];

			array[0] = getByG_N_D_PrevAndNext(session, ddmStructure, groupId,
					name, description, orderByComparator, true);

			array[1] = ddmStructure;

			array[2] = getByG_N_D_PrevAndNext(session, ddmStructure, groupId,
					name, description, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected DDMStructure getByG_N_D_PrevAndNext(Session session,
		DDMStructure ddmStructure, long groupId, String name,
		String description, OrderByComparator<DDMStructure> orderByComparator,
		boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_DDMSTRUCTURE_WHERE);

		query.append(_FINDER_COLUMN_G_N_D_GROUPID_2);

		boolean bindName = false;

		if (name == null) {
			query.append(_FINDER_COLUMN_G_N_D_NAME_1);
		}
		else if (name.equals(StringPool.BLANK)) {
			query.append(_FINDER_COLUMN_G_N_D_NAME_3);
		}
		else {
			bindName = true;

			query.append(_FINDER_COLUMN_G_N_D_NAME_2);
		}

		boolean bindDescription = false;

		if (description == null) {
			query.append(_FINDER_COLUMN_G_N_D_DESCRIPTION_1);
		}
		else if (description.equals(StringPool.BLANK)) {
			query.append(_FINDER_COLUMN_G_N_D_DESCRIPTION_3);
		}
		else {
			bindDescription = true;

			query.append(_FINDER_COLUMN_G_N_D_DESCRIPTION_2);
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
			query.append(DDMStructureModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		if (bindName) {
			qPos.add(name);
		}

		if (bindDescription) {
			qPos.add(description);
		}

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(ddmStructure);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<DDMStructure> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the d d m structures that the user has permission to view where groupId = &#63; and name = &#63; and description = &#63;.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param description the description
	 * @return the matching d d m structures that the user has permission to view
	 */
	@Override
	public List<DDMStructure> filterFindByG_N_D(long groupId, String name,
		String description) {
		return filterFindByG_N_D(groupId, name, description, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the d d m structures that the user has permission to view where groupId = &#63; and name = &#63; and description = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMStructureModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param description the description
	 * @param start the lower bound of the range of d d m structures
	 * @param end the upper bound of the range of d d m structures (not inclusive)
	 * @return the range of matching d d m structures that the user has permission to view
	 */
	@Override
	public List<DDMStructure> filterFindByG_N_D(long groupId, String name,
		String description, int start, int end) {
		return filterFindByG_N_D(groupId, name, description, start, end, null);
	}

	/**
	 * Returns an ordered range of all the d d m structures that the user has permissions to view where groupId = &#63; and name = &#63; and description = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMStructureModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param description the description
	 * @param start the lower bound of the range of d d m structures
	 * @param end the upper bound of the range of d d m structures (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching d d m structures that the user has permission to view
	 */
	@Override
	public List<DDMStructure> filterFindByG_N_D(long groupId, String name,
		String description, int start, int end,
		OrderByComparator<DDMStructure> orderByComparator) {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_N_D(groupId, name, description, start, end,
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
			query.append(_FILTER_SQL_SELECT_DDMSTRUCTURE_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_DDMSTRUCTURE_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_G_N_D_GROUPID_2);

		boolean bindName = false;

		if (name == null) {
			query.append(_FINDER_COLUMN_G_N_D_NAME_1);
		}
		else if (name.equals(StringPool.BLANK)) {
			query.append(_FINDER_COLUMN_G_N_D_NAME_3);
		}
		else {
			bindName = true;

			query.append(_FINDER_COLUMN_G_N_D_NAME_2);
		}

		boolean bindDescription = false;

		if (description == null) {
			query.append(_FINDER_COLUMN_G_N_D_DESCRIPTION_1);
		}
		else if (description.equals(StringPool.BLANK)) {
			query.append(_FINDER_COLUMN_G_N_D_DESCRIPTION_3);
		}
		else {
			bindDescription = true;

			query.append(_FINDER_COLUMN_G_N_D_DESCRIPTION_2);
		}

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_DDMSTRUCTURE_NO_INLINE_DISTINCT_WHERE_2);
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
				query.append(DDMStructureModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(DDMStructureModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				DDMStructure.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				q.addEntity(_FILTER_ENTITY_ALIAS, DDMStructureImpl.class);
			}
			else {
				q.addEntity(_FILTER_ENTITY_TABLE, DDMStructureImpl.class);
			}

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			if (bindName) {
				qPos.add(name);
			}

			if (bindDescription) {
				qPos.add(description);
			}

			return (List<DDMStructure>)QueryUtil.list(q, getDialect(), start,
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
	 * Returns the d d m structures before and after the current d d m structure in the ordered set of d d m structures that the user has permission to view where groupId = &#63; and name = &#63; and description = &#63;.
	 *
	 * @param structureId the primary key of the current d d m structure
	 * @param groupId the group ID
	 * @param name the name
	 * @param description the description
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next d d m structure
	 * @throws NoSuchStructureException if a d d m structure with the primary key could not be found
	 */
	@Override
	public DDMStructure[] filterFindByG_N_D_PrevAndNext(long structureId,
		long groupId, String name, String description,
		OrderByComparator<DDMStructure> orderByComparator)
		throws NoSuchStructureException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_N_D_PrevAndNext(structureId, groupId, name,
				description, orderByComparator);
		}

		DDMStructure ddmStructure = findByPrimaryKey(structureId);

		Session session = null;

		try {
			session = openSession();

			DDMStructure[] array = new DDMStructureImpl[3];

			array[0] = filterGetByG_N_D_PrevAndNext(session, ddmStructure,
					groupId, name, description, orderByComparator, true);

			array[1] = ddmStructure;

			array[2] = filterGetByG_N_D_PrevAndNext(session, ddmStructure,
					groupId, name, description, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected DDMStructure filterGetByG_N_D_PrevAndNext(Session session,
		DDMStructure ddmStructure, long groupId, String name,
		String description, OrderByComparator<DDMStructure> orderByComparator,
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
			query.append(_FILTER_SQL_SELECT_DDMSTRUCTURE_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_DDMSTRUCTURE_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_G_N_D_GROUPID_2);

		boolean bindName = false;

		if (name == null) {
			query.append(_FINDER_COLUMN_G_N_D_NAME_1);
		}
		else if (name.equals(StringPool.BLANK)) {
			query.append(_FINDER_COLUMN_G_N_D_NAME_3);
		}
		else {
			bindName = true;

			query.append(_FINDER_COLUMN_G_N_D_NAME_2);
		}

		boolean bindDescription = false;

		if (description == null) {
			query.append(_FINDER_COLUMN_G_N_D_DESCRIPTION_1);
		}
		else if (description.equals(StringPool.BLANK)) {
			query.append(_FINDER_COLUMN_G_N_D_DESCRIPTION_3);
		}
		else {
			bindDescription = true;

			query.append(_FINDER_COLUMN_G_N_D_DESCRIPTION_2);
		}

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_DDMSTRUCTURE_NO_INLINE_DISTINCT_WHERE_2);
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
				query.append(DDMStructureModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(DDMStructureModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				DDMStructure.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		SQLQuery q = session.createSynchronizedSQLQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		if (getDB().isSupportsInlineDistinct()) {
			q.addEntity(_FILTER_ENTITY_ALIAS, DDMStructureImpl.class);
		}
		else {
			q.addEntity(_FILTER_ENTITY_TABLE, DDMStructureImpl.class);
		}

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		if (bindName) {
			qPos.add(name);
		}

		if (bindDescription) {
			qPos.add(description);
		}

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(ddmStructure);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<DDMStructure> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the d d m structures where groupId = &#63; and name = &#63; and description = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param description the description
	 */
	@Override
	public void removeByG_N_D(long groupId, String name, String description) {
		for (DDMStructure ddmStructure : findByG_N_D(groupId, name,
				description, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(ddmStructure);
		}
	}

	/**
	 * Returns the number of d d m structures where groupId = &#63; and name = &#63; and description = &#63;.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param description the description
	 * @return the number of matching d d m structures
	 */
	@Override
	public int countByG_N_D(long groupId, String name, String description) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_G_N_D;

		Object[] finderArgs = new Object[] { groupId, name, description };

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs,
				this);

		if (count == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_COUNT_DDMSTRUCTURE_WHERE);

			query.append(_FINDER_COLUMN_G_N_D_GROUPID_2);

			boolean bindName = false;

			if (name == null) {
				query.append(_FINDER_COLUMN_G_N_D_NAME_1);
			}
			else if (name.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_G_N_D_NAME_3);
			}
			else {
				bindName = true;

				query.append(_FINDER_COLUMN_G_N_D_NAME_2);
			}

			boolean bindDescription = false;

			if (description == null) {
				query.append(_FINDER_COLUMN_G_N_D_DESCRIPTION_1);
			}
			else if (description.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_G_N_D_DESCRIPTION_3);
			}
			else {
				bindDescription = true;

				query.append(_FINDER_COLUMN_G_N_D_DESCRIPTION_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (bindName) {
					qPos.add(name);
				}

				if (bindDescription) {
					qPos.add(description);
				}

				count = (Long)q.uniqueResult();

				FinderCacheUtil.putResult(finderPath, finderArgs, count);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the number of d d m structures that the user has permission to view where groupId = &#63; and name = &#63; and description = &#63;.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param description the description
	 * @return the number of matching d d m structures that the user has permission to view
	 */
	@Override
	public int filterCountByG_N_D(long groupId, String name, String description) {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByG_N_D(groupId, name, description);
		}

		StringBundler query = new StringBundler(4);

		query.append(_FILTER_SQL_COUNT_DDMSTRUCTURE_WHERE);

		query.append(_FINDER_COLUMN_G_N_D_GROUPID_2);

		boolean bindName = false;

		if (name == null) {
			query.append(_FINDER_COLUMN_G_N_D_NAME_1);
		}
		else if (name.equals(StringPool.BLANK)) {
			query.append(_FINDER_COLUMN_G_N_D_NAME_3);
		}
		else {
			bindName = true;

			query.append(_FINDER_COLUMN_G_N_D_NAME_2);
		}

		boolean bindDescription = false;

		if (description == null) {
			query.append(_FINDER_COLUMN_G_N_D_DESCRIPTION_1);
		}
		else if (description.equals(StringPool.BLANK)) {
			query.append(_FINDER_COLUMN_G_N_D_DESCRIPTION_3);
		}
		else {
			bindDescription = true;

			query.append(_FINDER_COLUMN_G_N_D_DESCRIPTION_2);
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				DDMStructure.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME,
				com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			if (bindName) {
				qPos.add(name);
			}

			if (bindDescription) {
				qPos.add(description);
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

	private static final String _FINDER_COLUMN_G_N_D_GROUPID_2 = "ddmStructure.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_N_D_NAME_1 = "ddmStructure.name IS NULL AND ";
	private static final String _FINDER_COLUMN_G_N_D_NAME_2 = "ddmStructure.name = ? AND ";
	private static final String _FINDER_COLUMN_G_N_D_NAME_3 = "(ddmStructure.name IS NULL OR ddmStructure.name = '') AND ";
	private static final String _FINDER_COLUMN_G_N_D_DESCRIPTION_1 = "ddmStructure.description IS NULL";
	private static final String _FINDER_COLUMN_G_N_D_DESCRIPTION_2 = "ddmStructure.description = ?";
	private static final String _FINDER_COLUMN_G_N_D_DESCRIPTION_3 = "(ddmStructure.description IS NULL OR ddmStructure.description = '')";

	public DDMStructurePersistenceImpl() {
		setModelClass(DDMStructure.class);
	}

	/**
	 * Caches the d d m structure in the entity cache if it is enabled.
	 *
	 * @param ddmStructure the d d m structure
	 */
	@Override
	public void cacheResult(DDMStructure ddmStructure) {
		EntityCacheUtil.putResult(DDMStructureModelImpl.ENTITY_CACHE_ENABLED,
			DDMStructureImpl.class, ddmStructure.getPrimaryKey(), ddmStructure);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_UUID_G,
			new Object[] { ddmStructure.getUuid(), ddmStructure.getGroupId() },
			ddmStructure);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_C_S,
			new Object[] {
				ddmStructure.getGroupId(), ddmStructure.getClassNameId(),
				ddmStructure.getStructureKey()
			}, ddmStructure);

		ddmStructure.resetOriginalValues();
	}

	/**
	 * Caches the d d m structures in the entity cache if it is enabled.
	 *
	 * @param ddmStructures the d d m structures
	 */
	@Override
	public void cacheResult(List<DDMStructure> ddmStructures) {
		for (DDMStructure ddmStructure : ddmStructures) {
			if (EntityCacheUtil.getResult(
						DDMStructureModelImpl.ENTITY_CACHE_ENABLED,
						DDMStructureImpl.class, ddmStructure.getPrimaryKey()) == null) {
				cacheResult(ddmStructure);
			}
			else {
				ddmStructure.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all d d m structures.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		EntityCacheUtil.clearCache(DDMStructureImpl.class);

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the d d m structure.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(DDMStructure ddmStructure) {
		EntityCacheUtil.removeResult(DDMStructureModelImpl.ENTITY_CACHE_ENABLED,
			DDMStructureImpl.class, ddmStructure.getPrimaryKey());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache(ddmStructure);
	}

	@Override
	public void clearCache(List<DDMStructure> ddmStructures) {
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (DDMStructure ddmStructure : ddmStructures) {
			EntityCacheUtil.removeResult(DDMStructureModelImpl.ENTITY_CACHE_ENABLED,
				DDMStructureImpl.class, ddmStructure.getPrimaryKey());

			clearUniqueFindersCache(ddmStructure);
		}
	}

	protected void cacheUniqueFindersCache(DDMStructure ddmStructure) {
		if (ddmStructure.isNew()) {
			Object[] args = new Object[] {
					ddmStructure.getUuid(), ddmStructure.getGroupId()
				};

			FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_UUID_G, args,
				Long.valueOf(1));
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_UUID_G, args,
				ddmStructure);

			args = new Object[] {
					ddmStructure.getGroupId(), ddmStructure.getClassNameId(),
					ddmStructure.getStructureKey()
				};

			FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_G_C_S, args,
				Long.valueOf(1));
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_C_S, args,
				ddmStructure);
		}
		else {
			DDMStructureModelImpl ddmStructureModelImpl = (DDMStructureModelImpl)ddmStructure;

			if ((ddmStructureModelImpl.getColumnBitmask() &
					FINDER_PATH_FETCH_BY_UUID_G.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						ddmStructure.getUuid(), ddmStructure.getGroupId()
					};

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_UUID_G, args,
					Long.valueOf(1));
				FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_UUID_G, args,
					ddmStructure);
			}

			if ((ddmStructureModelImpl.getColumnBitmask() &
					FINDER_PATH_FETCH_BY_G_C_S.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						ddmStructure.getGroupId(), ddmStructure.getClassNameId(),
						ddmStructure.getStructureKey()
					};

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_G_C_S, args,
					Long.valueOf(1));
				FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_C_S, args,
					ddmStructure);
			}
		}
	}

	protected void clearUniqueFindersCache(DDMStructure ddmStructure) {
		DDMStructureModelImpl ddmStructureModelImpl = (DDMStructureModelImpl)ddmStructure;

		Object[] args = new Object[] {
				ddmStructure.getUuid(), ddmStructure.getGroupId()
			};

		FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_UUID_G, args);
		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_UUID_G, args);

		if ((ddmStructureModelImpl.getColumnBitmask() &
				FINDER_PATH_FETCH_BY_UUID_G.getColumnBitmask()) != 0) {
			args = new Object[] {
					ddmStructureModelImpl.getOriginalUuid(),
					ddmStructureModelImpl.getOriginalGroupId()
				};

			FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_UUID_G, args);
			FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_UUID_G, args);
		}

		args = new Object[] {
				ddmStructure.getGroupId(), ddmStructure.getClassNameId(),
				ddmStructure.getStructureKey()
			};

		FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_G_C_S, args);
		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_G_C_S, args);

		if ((ddmStructureModelImpl.getColumnBitmask() &
				FINDER_PATH_FETCH_BY_G_C_S.getColumnBitmask()) != 0) {
			args = new Object[] {
					ddmStructureModelImpl.getOriginalGroupId(),
					ddmStructureModelImpl.getOriginalClassNameId(),
					ddmStructureModelImpl.getOriginalStructureKey()
				};

			FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_G_C_S, args);
			FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_G_C_S, args);
		}
	}

	/**
	 * Creates a new d d m structure with the primary key. Does not add the d d m structure to the database.
	 *
	 * @param structureId the primary key for the new d d m structure
	 * @return the new d d m structure
	 */
	@Override
	public DDMStructure create(long structureId) {
		DDMStructure ddmStructure = new DDMStructureImpl();

		ddmStructure.setNew(true);
		ddmStructure.setPrimaryKey(structureId);

		String uuid = PortalUUIDUtil.generate();

		ddmStructure.setUuid(uuid);

		return ddmStructure;
	}

	/**
	 * Removes the d d m structure with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param structureId the primary key of the d d m structure
	 * @return the d d m structure that was removed
	 * @throws NoSuchStructureException if a d d m structure with the primary key could not be found
	 */
	@Override
	public DDMStructure remove(long structureId)
		throws NoSuchStructureException {
		return remove((Serializable)structureId);
	}

	/**
	 * Removes the d d m structure with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the d d m structure
	 * @return the d d m structure that was removed
	 * @throws NoSuchStructureException if a d d m structure with the primary key could not be found
	 */
	@Override
	public DDMStructure remove(Serializable primaryKey)
		throws NoSuchStructureException {
		Session session = null;

		try {
			session = openSession();

			DDMStructure ddmStructure = (DDMStructure)session.get(DDMStructureImpl.class,
					primaryKey);

			if (ddmStructure == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchStructureException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(ddmStructure);
		}
		catch (NoSuchStructureException nsee) {
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
	protected DDMStructure removeImpl(DDMStructure ddmStructure) {
		ddmStructure = toUnwrappedModel(ddmStructure);

		ddmStructureToDLFileEntryTypeTableMapper.deleteLeftPrimaryKeyTableMappings(ddmStructure.getPrimaryKey());

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(ddmStructure)) {
				ddmStructure = (DDMStructure)session.get(DDMStructureImpl.class,
						ddmStructure.getPrimaryKeyObj());
			}

			if (ddmStructure != null) {
				session.delete(ddmStructure);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (ddmStructure != null) {
			clearCache(ddmStructure);
		}

		return ddmStructure;
	}

	@Override
	public DDMStructure updateImpl(DDMStructure ddmStructure) {
		ddmStructure = toUnwrappedModel(ddmStructure);

		boolean isNew = ddmStructure.isNew();

		DDMStructureModelImpl ddmStructureModelImpl = (DDMStructureModelImpl)ddmStructure;

		if (Validator.isNull(ddmStructure.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			ddmStructure.setUuid(uuid);
		}

		Session session = null;

		try {
			session = openSession();

			if (ddmStructure.isNew()) {
				session.save(ddmStructure);

				ddmStructure.setNew(false);
			}
			else {
				session.merge(ddmStructure);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (isNew || !DDMStructureModelImpl.COLUMN_BITMASK_ENABLED) {
			FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}

		else {
			if ((ddmStructureModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						ddmStructureModelImpl.getOriginalUuid()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_UUID, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID,
					args);

				args = new Object[] { ddmStructureModelImpl.getUuid() };

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_UUID, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID,
					args);
			}

			if ((ddmStructureModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						ddmStructureModelImpl.getOriginalUuid(),
						ddmStructureModelImpl.getOriginalCompanyId()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_UUID_C, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C,
					args);

				args = new Object[] {
						ddmStructureModelImpl.getUuid(),
						ddmStructureModelImpl.getCompanyId()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_UUID_C, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C,
					args);
			}

			if ((ddmStructureModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						ddmStructureModelImpl.getOriginalGroupId()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_GROUPID, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID,
					args);

				args = new Object[] { ddmStructureModelImpl.getGroupId() };

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_GROUPID, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID,
					args);
			}

			if ((ddmStructureModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_PARENTSTRUCTUREID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						ddmStructureModelImpl.getOriginalParentStructureId()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_PARENTSTRUCTUREID,
					args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_PARENTSTRUCTUREID,
					args);

				args = new Object[] { ddmStructureModelImpl.getParentStructureId() };

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_PARENTSTRUCTUREID,
					args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_PARENTSTRUCTUREID,
					args);
			}

			if ((ddmStructureModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_CLASSNAMEID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						ddmStructureModelImpl.getOriginalClassNameId()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_CLASSNAMEID,
					args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_CLASSNAMEID,
					args);

				args = new Object[] { ddmStructureModelImpl.getClassNameId() };

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_CLASSNAMEID,
					args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_CLASSNAMEID,
					args);
			}

			if ((ddmStructureModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_STRUCTUREKEY.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						ddmStructureModelImpl.getOriginalStructureKey()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_STRUCTUREKEY,
					args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_STRUCTUREKEY,
					args);

				args = new Object[] { ddmStructureModelImpl.getStructureKey() };

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_STRUCTUREKEY,
					args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_STRUCTUREKEY,
					args);
			}

			if ((ddmStructureModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_P.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						ddmStructureModelImpl.getOriginalGroupId(),
						ddmStructureModelImpl.getOriginalParentStructureId()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_G_P, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_P,
					args);

				args = new Object[] {
						ddmStructureModelImpl.getGroupId(),
						ddmStructureModelImpl.getParentStructureId()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_G_P, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_P,
					args);
			}

			if ((ddmStructureModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_C.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						ddmStructureModelImpl.getOriginalGroupId(),
						ddmStructureModelImpl.getOriginalClassNameId()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_G_C, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_C,
					args);

				args = new Object[] {
						ddmStructureModelImpl.getGroupId(),
						ddmStructureModelImpl.getClassNameId()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_G_C, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_C,
					args);
			}

			if ((ddmStructureModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_C.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						ddmStructureModelImpl.getOriginalCompanyId(),
						ddmStructureModelImpl.getOriginalClassNameId()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_C_C, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_C,
					args);

				args = new Object[] {
						ddmStructureModelImpl.getCompanyId(),
						ddmStructureModelImpl.getClassNameId()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_C_C, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_C,
					args);
			}

			if ((ddmStructureModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_N_D.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						ddmStructureModelImpl.getOriginalGroupId(),
						ddmStructureModelImpl.getOriginalName(),
						ddmStructureModelImpl.getOriginalDescription()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_G_N_D, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_N_D,
					args);

				args = new Object[] {
						ddmStructureModelImpl.getGroupId(),
						ddmStructureModelImpl.getName(),
						ddmStructureModelImpl.getDescription()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_G_N_D, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_N_D,
					args);
			}
		}

		EntityCacheUtil.putResult(DDMStructureModelImpl.ENTITY_CACHE_ENABLED,
			DDMStructureImpl.class, ddmStructure.getPrimaryKey(), ddmStructure,
			false);

		clearUniqueFindersCache(ddmStructure);
		cacheUniqueFindersCache(ddmStructure);

		ddmStructure.resetOriginalValues();

		return ddmStructure;
	}

	protected DDMStructure toUnwrappedModel(DDMStructure ddmStructure) {
		if (ddmStructure instanceof DDMStructureImpl) {
			return ddmStructure;
		}

		DDMStructureImpl ddmStructureImpl = new DDMStructureImpl();

		ddmStructureImpl.setNew(ddmStructure.isNew());
		ddmStructureImpl.setPrimaryKey(ddmStructure.getPrimaryKey());

		ddmStructureImpl.setUuid(ddmStructure.getUuid());
		ddmStructureImpl.setStructureId(ddmStructure.getStructureId());
		ddmStructureImpl.setGroupId(ddmStructure.getGroupId());
		ddmStructureImpl.setCompanyId(ddmStructure.getCompanyId());
		ddmStructureImpl.setUserId(ddmStructure.getUserId());
		ddmStructureImpl.setUserName(ddmStructure.getUserName());
		ddmStructureImpl.setCreateDate(ddmStructure.getCreateDate());
		ddmStructureImpl.setModifiedDate(ddmStructure.getModifiedDate());
		ddmStructureImpl.setParentStructureId(ddmStructure.getParentStructureId());
		ddmStructureImpl.setClassNameId(ddmStructure.getClassNameId());
		ddmStructureImpl.setStructureKey(ddmStructure.getStructureKey());
		ddmStructureImpl.setVersion(ddmStructure.getVersion());
		ddmStructureImpl.setName(ddmStructure.getName());
		ddmStructureImpl.setDescription(ddmStructure.getDescription());
		ddmStructureImpl.setDefinition(ddmStructure.getDefinition());
		ddmStructureImpl.setStorageType(ddmStructure.getStorageType());
		ddmStructureImpl.setType(ddmStructure.getType());

		return ddmStructureImpl;
	}

	/**
	 * Returns the d d m structure with the primary key or throws a {@link com.liferay.portal.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the d d m structure
	 * @return the d d m structure
	 * @throws NoSuchStructureException if a d d m structure with the primary key could not be found
	 */
	@Override
	public DDMStructure findByPrimaryKey(Serializable primaryKey)
		throws NoSuchStructureException {
		DDMStructure ddmStructure = fetchByPrimaryKey(primaryKey);

		if (ddmStructure == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchStructureException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				primaryKey);
		}

		return ddmStructure;
	}

	/**
	 * Returns the d d m structure with the primary key or throws a {@link NoSuchStructureException} if it could not be found.
	 *
	 * @param structureId the primary key of the d d m structure
	 * @return the d d m structure
	 * @throws NoSuchStructureException if a d d m structure with the primary key could not be found
	 */
	@Override
	public DDMStructure findByPrimaryKey(long structureId)
		throws NoSuchStructureException {
		return findByPrimaryKey((Serializable)structureId);
	}

	/**
	 * Returns the d d m structure with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the d d m structure
	 * @return the d d m structure, or <code>null</code> if a d d m structure with the primary key could not be found
	 */
	@Override
	public DDMStructure fetchByPrimaryKey(Serializable primaryKey) {
		DDMStructure ddmStructure = (DDMStructure)EntityCacheUtil.getResult(DDMStructureModelImpl.ENTITY_CACHE_ENABLED,
				DDMStructureImpl.class, primaryKey);

		if (ddmStructure == _nullDDMStructure) {
			return null;
		}

		if (ddmStructure == null) {
			Session session = null;

			try {
				session = openSession();

				ddmStructure = (DDMStructure)session.get(DDMStructureImpl.class,
						primaryKey);

				if (ddmStructure != null) {
					cacheResult(ddmStructure);
				}
				else {
					EntityCacheUtil.putResult(DDMStructureModelImpl.ENTITY_CACHE_ENABLED,
						DDMStructureImpl.class, primaryKey, _nullDDMStructure);
				}
			}
			catch (Exception e) {
				EntityCacheUtil.removeResult(DDMStructureModelImpl.ENTITY_CACHE_ENABLED,
					DDMStructureImpl.class, primaryKey);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return ddmStructure;
	}

	/**
	 * Returns the d d m structure with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param structureId the primary key of the d d m structure
	 * @return the d d m structure, or <code>null</code> if a d d m structure with the primary key could not be found
	 */
	@Override
	public DDMStructure fetchByPrimaryKey(long structureId) {
		return fetchByPrimaryKey((Serializable)structureId);
	}

	@Override
	public Map<Serializable, DDMStructure> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {
		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, DDMStructure> map = new HashMap<Serializable, DDMStructure>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			DDMStructure ddmStructure = fetchByPrimaryKey(primaryKey);

			if (ddmStructure != null) {
				map.put(primaryKey, ddmStructure);
			}

			return map;
		}

		Set<Serializable> uncachedPrimaryKeys = null;

		for (Serializable primaryKey : primaryKeys) {
			DDMStructure ddmStructure = (DDMStructure)EntityCacheUtil.getResult(DDMStructureModelImpl.ENTITY_CACHE_ENABLED,
					DDMStructureImpl.class, primaryKey);

			if (ddmStructure == null) {
				if (uncachedPrimaryKeys == null) {
					uncachedPrimaryKeys = new HashSet<Serializable>();
				}

				uncachedPrimaryKeys.add(primaryKey);
			}
			else {
				map.put(primaryKey, ddmStructure);
			}
		}

		if (uncachedPrimaryKeys == null) {
			return map;
		}

		StringBundler query = new StringBundler((uncachedPrimaryKeys.size() * 2) +
				1);

		query.append(_SQL_SELECT_DDMSTRUCTURE_WHERE_PKS_IN);

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

			for (DDMStructure ddmStructure : (List<DDMStructure>)q.list()) {
				map.put(ddmStructure.getPrimaryKeyObj(), ddmStructure);

				cacheResult(ddmStructure);

				uncachedPrimaryKeys.remove(ddmStructure.getPrimaryKeyObj());
			}

			for (Serializable primaryKey : uncachedPrimaryKeys) {
				EntityCacheUtil.putResult(DDMStructureModelImpl.ENTITY_CACHE_ENABLED,
					DDMStructureImpl.class, primaryKey, _nullDDMStructure);
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
	 * Returns all the d d m structures.
	 *
	 * @return the d d m structures
	 */
	@Override
	public List<DDMStructure> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the d d m structures.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMStructureModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of d d m structures
	 * @param end the upper bound of the range of d d m structures (not inclusive)
	 * @return the range of d d m structures
	 */
	@Override
	public List<DDMStructure> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the d d m structures.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMStructureModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of d d m structures
	 * @param end the upper bound of the range of d d m structures (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of d d m structures
	 */
	@Override
	public List<DDMStructure> findAll(int start, int end,
		OrderByComparator<DDMStructure> orderByComparator) {
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

		List<DDMStructure> list = (List<DDMStructure>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 3));

				query.append(_SQL_SELECT_DDMSTRUCTURE);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_DDMSTRUCTURE;

				if (pagination) {
					sql = sql.concat(DDMStructureModelImpl.ORDER_BY_JPQL);
				}
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (!pagination) {
					list = (List<DDMStructure>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<DDMStructure>)QueryUtil.list(q, getDialect(),
							start, end);
				}

				cacheResult(list);

				FinderCacheUtil.putResult(finderPath, finderArgs, list);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Removes all the d d m structures from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (DDMStructure ddmStructure : findAll()) {
			remove(ddmStructure);
		}
	}

	/**
	 * Returns the number of d d m structures.
	 *
	 * @return the number of d d m structures
	 */
	@Override
	public int countAll() {
		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_DDMSTRUCTURE);

				count = (Long)q.uniqueResult();

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_ALL,
					FINDER_ARGS_EMPTY, count);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_ALL,
					FINDER_ARGS_EMPTY);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the primaryKeys of document library file entry types associated with the d d m structure.
	 *
	 * @param pk the primary key of the d d m structure
	 * @return long[] of the primaryKeys of document library file entry types associated with the d d m structure
	 */
	@Override
	public long[] getDLFileEntryTypePrimaryKeys(long pk) {
		long[] pks = ddmStructureToDLFileEntryTypeTableMapper.getRightPrimaryKeys(pk);

		return pks.clone();
	}

	/**
	 * Returns all the document library file entry types associated with the d d m structure.
	 *
	 * @param pk the primary key of the d d m structure
	 * @return the document library file entry types associated with the d d m structure
	 */
	@Override
	public List<com.liferay.portlet.documentlibrary.model.DLFileEntryType> getDLFileEntryTypes(
		long pk) {
		return getDLFileEntryTypes(pk, QueryUtil.ALL_POS, QueryUtil.ALL_POS);
	}

	/**
	 * Returns a range of all the document library file entry types associated with the d d m structure.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMStructureModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param pk the primary key of the d d m structure
	 * @param start the lower bound of the range of d d m structures
	 * @param end the upper bound of the range of d d m structures (not inclusive)
	 * @return the range of document library file entry types associated with the d d m structure
	 */
	@Override
	public List<com.liferay.portlet.documentlibrary.model.DLFileEntryType> getDLFileEntryTypes(
		long pk, int start, int end) {
		return getDLFileEntryTypes(pk, start, end, null);
	}

	/**
	 * Returns an ordered range of all the document library file entry types associated with the d d m structure.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMStructureModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param pk the primary key of the d d m structure
	 * @param start the lower bound of the range of d d m structures
	 * @param end the upper bound of the range of d d m structures (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of document library file entry types associated with the d d m structure
	 */
	@Override
	public List<com.liferay.portlet.documentlibrary.model.DLFileEntryType> getDLFileEntryTypes(
		long pk, int start, int end,
		OrderByComparator<com.liferay.portlet.documentlibrary.model.DLFileEntryType> orderByComparator) {
		return ddmStructureToDLFileEntryTypeTableMapper.getRightBaseModels(pk,
			start, end, orderByComparator);
	}

	/**
	 * Returns the number of document library file entry types associated with the d d m structure.
	 *
	 * @param pk the primary key of the d d m structure
	 * @return the number of document library file entry types associated with the d d m structure
	 */
	@Override
	public int getDLFileEntryTypesSize(long pk) {
		long[] pks = ddmStructureToDLFileEntryTypeTableMapper.getRightPrimaryKeys(pk);

		return pks.length;
	}

	/**
	 * Returns <code>true</code> if the document library file entry type is associated with the d d m structure.
	 *
	 * @param pk the primary key of the d d m structure
	 * @param dlFileEntryTypePK the primary key of the document library file entry type
	 * @return <code>true</code> if the document library file entry type is associated with the d d m structure; <code>false</code> otherwise
	 */
	@Override
	public boolean containsDLFileEntryType(long pk, long dlFileEntryTypePK) {
		return ddmStructureToDLFileEntryTypeTableMapper.containsTableMapping(pk,
			dlFileEntryTypePK);
	}

	/**
	 * Returns <code>true</code> if the d d m structure has any document library file entry types associated with it.
	 *
	 * @param pk the primary key of the d d m structure to check for associations with document library file entry types
	 * @return <code>true</code> if the d d m structure has any document library file entry types associated with it; <code>false</code> otherwise
	 */
	@Override
	public boolean containsDLFileEntryTypes(long pk) {
		if (getDLFileEntryTypesSize(pk) > 0) {
			return true;
		}
		else {
			return false;
		}
	}

	/**
	 * Adds an association between the d d m structure and the document library file entry type. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the d d m structure
	 * @param dlFileEntryTypePK the primary key of the document library file entry type
	 */
	@Override
	public void addDLFileEntryType(long pk, long dlFileEntryTypePK) {
		ddmStructureToDLFileEntryTypeTableMapper.addTableMapping(pk,
			dlFileEntryTypePK);
	}

	/**
	 * Adds an association between the d d m structure and the document library file entry type. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the d d m structure
	 * @param dlFileEntryType the document library file entry type
	 */
	@Override
	public void addDLFileEntryType(long pk,
		com.liferay.portlet.documentlibrary.model.DLFileEntryType dlFileEntryType) {
		ddmStructureToDLFileEntryTypeTableMapper.addTableMapping(pk,
			dlFileEntryType.getPrimaryKey());
	}

	/**
	 * Adds an association between the d d m structure and the document library file entry types. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the d d m structure
	 * @param dlFileEntryTypePKs the primary keys of the document library file entry types
	 */
	@Override
	public void addDLFileEntryTypes(long pk, long[] dlFileEntryTypePKs) {
		for (long dlFileEntryTypePK : dlFileEntryTypePKs) {
			ddmStructureToDLFileEntryTypeTableMapper.addTableMapping(pk,
				dlFileEntryTypePK);
		}
	}

	/**
	 * Adds an association between the d d m structure and the document library file entry types. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the d d m structure
	 * @param dlFileEntryTypes the document library file entry types
	 */
	@Override
	public void addDLFileEntryTypes(long pk,
		List<com.liferay.portlet.documentlibrary.model.DLFileEntryType> dlFileEntryTypes) {
		for (com.liferay.portlet.documentlibrary.model.DLFileEntryType dlFileEntryType : dlFileEntryTypes) {
			ddmStructureToDLFileEntryTypeTableMapper.addTableMapping(pk,
				dlFileEntryType.getPrimaryKey());
		}
	}

	/**
	 * Clears all associations between the d d m structure and its document library file entry types. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the d d m structure to clear the associated document library file entry types from
	 */
	@Override
	public void clearDLFileEntryTypes(long pk) {
		ddmStructureToDLFileEntryTypeTableMapper.deleteLeftPrimaryKeyTableMappings(pk);
	}

	/**
	 * Removes the association between the d d m structure and the document library file entry type. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the d d m structure
	 * @param dlFileEntryTypePK the primary key of the document library file entry type
	 */
	@Override
	public void removeDLFileEntryType(long pk, long dlFileEntryTypePK) {
		ddmStructureToDLFileEntryTypeTableMapper.deleteTableMapping(pk,
			dlFileEntryTypePK);
	}

	/**
	 * Removes the association between the d d m structure and the document library file entry type. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the d d m structure
	 * @param dlFileEntryType the document library file entry type
	 */
	@Override
	public void removeDLFileEntryType(long pk,
		com.liferay.portlet.documentlibrary.model.DLFileEntryType dlFileEntryType) {
		ddmStructureToDLFileEntryTypeTableMapper.deleteTableMapping(pk,
			dlFileEntryType.getPrimaryKey());
	}

	/**
	 * Removes the association between the d d m structure and the document library file entry types. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the d d m structure
	 * @param dlFileEntryTypePKs the primary keys of the document library file entry types
	 */
	@Override
	public void removeDLFileEntryTypes(long pk, long[] dlFileEntryTypePKs) {
		for (long dlFileEntryTypePK : dlFileEntryTypePKs) {
			ddmStructureToDLFileEntryTypeTableMapper.deleteTableMapping(pk,
				dlFileEntryTypePK);
		}
	}

	/**
	 * Removes the association between the d d m structure and the document library file entry types. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the d d m structure
	 * @param dlFileEntryTypes the document library file entry types
	 */
	@Override
	public void removeDLFileEntryTypes(long pk,
		List<com.liferay.portlet.documentlibrary.model.DLFileEntryType> dlFileEntryTypes) {
		for (com.liferay.portlet.documentlibrary.model.DLFileEntryType dlFileEntryType : dlFileEntryTypes) {
			ddmStructureToDLFileEntryTypeTableMapper.deleteTableMapping(pk,
				dlFileEntryType.getPrimaryKey());
		}
	}

	/**
	 * Sets the document library file entry types associated with the d d m structure, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the d d m structure
	 * @param dlFileEntryTypePKs the primary keys of the document library file entry types to be associated with the d d m structure
	 */
	@Override
	public void setDLFileEntryTypes(long pk, long[] dlFileEntryTypePKs) {
		Set<Long> newDLFileEntryTypePKsSet = SetUtil.fromArray(dlFileEntryTypePKs);
		Set<Long> oldDLFileEntryTypePKsSet = SetUtil.fromArray(ddmStructureToDLFileEntryTypeTableMapper.getRightPrimaryKeys(
					pk));

		Set<Long> removeDLFileEntryTypePKsSet = new HashSet<Long>(oldDLFileEntryTypePKsSet);

		removeDLFileEntryTypePKsSet.removeAll(newDLFileEntryTypePKsSet);

		for (long removeDLFileEntryTypePK : removeDLFileEntryTypePKsSet) {
			ddmStructureToDLFileEntryTypeTableMapper.deleteTableMapping(pk,
				removeDLFileEntryTypePK);
		}

		newDLFileEntryTypePKsSet.removeAll(oldDLFileEntryTypePKsSet);

		for (long newDLFileEntryTypePK : newDLFileEntryTypePKsSet) {
			ddmStructureToDLFileEntryTypeTableMapper.addTableMapping(pk,
				newDLFileEntryTypePK);
		}
	}

	/**
	 * Sets the document library file entry types associated with the d d m structure, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the d d m structure
	 * @param dlFileEntryTypes the document library file entry types to be associated with the d d m structure
	 */
	@Override
	public void setDLFileEntryTypes(long pk,
		List<com.liferay.portlet.documentlibrary.model.DLFileEntryType> dlFileEntryTypes) {
		try {
			long[] dlFileEntryTypePKs = new long[dlFileEntryTypes.size()];

			for (int i = 0; i < dlFileEntryTypes.size(); i++) {
				com.liferay.portlet.documentlibrary.model.DLFileEntryType dlFileEntryType =
					dlFileEntryTypes.get(i);

				dlFileEntryTypePKs[i] = dlFileEntryType.getPrimaryKey();
			}

			setDLFileEntryTypes(pk, dlFileEntryTypePKs);
		}
		catch (Exception e) {
			throw processException(e);
		}
	}

	@Override
	protected Set<String> getBadColumnNames() {
		return _badColumnNames;
	}

	/**
	 * Initializes the d d m structure persistence.
	 */
	public void afterPropertiesSet() {
		ddmStructureToDLFileEntryTypeTableMapper = TableMapperFactory.getTableMapper("DLFileEntryTypes_DDMStructures",
				"structureId", "fileEntryTypeId", this,
				dlFileEntryTypePersistence);
	}

	public void destroy() {
		EntityCacheUtil.removeCache(DDMStructureImpl.class.getName());
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		TableMapperFactory.removeTableMapper("DLFileEntryTypes_DDMStructures");
	}

	@BeanReference(type = DLFileEntryTypePersistence.class)
	protected DLFileEntryTypePersistence dlFileEntryTypePersistence;
	protected TableMapper<DDMStructure, com.liferay.portlet.documentlibrary.model.DLFileEntryType> ddmStructureToDLFileEntryTypeTableMapper;
	private static final String _SQL_SELECT_DDMSTRUCTURE = "SELECT ddmStructure FROM DDMStructure ddmStructure";
	private static final String _SQL_SELECT_DDMSTRUCTURE_WHERE_PKS_IN = "SELECT ddmStructure FROM DDMStructure ddmStructure WHERE structureId IN (";
	private static final String _SQL_SELECT_DDMSTRUCTURE_WHERE = "SELECT ddmStructure FROM DDMStructure ddmStructure WHERE ";
	private static final String _SQL_COUNT_DDMSTRUCTURE = "SELECT COUNT(ddmStructure) FROM DDMStructure ddmStructure";
	private static final String _SQL_COUNT_DDMSTRUCTURE_WHERE = "SELECT COUNT(ddmStructure) FROM DDMStructure ddmStructure WHERE ";
	private static final String _FILTER_ENTITY_TABLE_FILTER_PK_COLUMN = "ddmStructure.structureId";
	private static final String _FILTER_SQL_SELECT_DDMSTRUCTURE_WHERE = "SELECT DISTINCT {ddmStructure.*} FROM DDMStructure ddmStructure WHERE ";
	private static final String _FILTER_SQL_SELECT_DDMSTRUCTURE_NO_INLINE_DISTINCT_WHERE_1 =
		"SELECT {DDMStructure.*} FROM (SELECT DISTINCT ddmStructure.structureId FROM DDMStructure ddmStructure WHERE ";
	private static final String _FILTER_SQL_SELECT_DDMSTRUCTURE_NO_INLINE_DISTINCT_WHERE_2 =
		") TEMP_TABLE INNER JOIN DDMStructure ON TEMP_TABLE.structureId = DDMStructure.structureId";
	private static final String _FILTER_SQL_COUNT_DDMSTRUCTURE_WHERE = "SELECT COUNT(DISTINCT ddmStructure.structureId) AS COUNT_VALUE FROM DDMStructure ddmStructure WHERE ";
	private static final String _FILTER_ENTITY_ALIAS = "ddmStructure";
	private static final String _FILTER_ENTITY_TABLE = "DDMStructure";
	private static final String _ORDER_BY_ENTITY_ALIAS = "ddmStructure.";
	private static final String _ORDER_BY_ENTITY_TABLE = "DDMStructure.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No DDMStructure exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No DDMStructure exists with the key {";
	private static final Log _log = LogFactoryUtil.getLog(DDMStructurePersistenceImpl.class);
	private static final Set<String> _badColumnNames = SetUtil.fromArray(new String[] {
				"uuid", "type"
			});
	private static final DDMStructure _nullDDMStructure = new DDMStructureImpl() {
			@Override
			public Object clone() {
				return this;
			}

			@Override
			public CacheModel<DDMStructure> toCacheModel() {
				return _nullDDMStructureCacheModel;
			}
		};

	private static final CacheModel<DDMStructure> _nullDDMStructureCacheModel = new CacheModel<DDMStructure>() {
			@Override
			public DDMStructure toEntityModel() {
				return _nullDDMStructure;
			}
		};
}