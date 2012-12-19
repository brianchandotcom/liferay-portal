/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.journal.service.persistence;

import com.liferay.portal.NoSuchModelException;
import com.liferay.portal.kernel.cache.CacheRegistryUtil;
import com.liferay.portal.kernel.dao.orm.EntityCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.SQLQuery;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.InstanceFactory;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.UnmodifiableList;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;
import com.liferay.portal.model.CacheModel;
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.security.permission.InlineSQLHelperUtil;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;

import com.liferay.portlet.journal.NoSuchStructureException;
import com.liferay.portlet.journal.model.JournalStructure;
import com.liferay.portlet.journal.model.impl.JournalStructureImpl;
import com.liferay.portlet.journal.model.impl.JournalStructureModelImpl;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The persistence implementation for the journal structure service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see JournalStructurePersistence
 * @see JournalStructureUtil
 * @generated
 */
public class JournalStructurePersistenceImpl extends BasePersistenceImpl<JournalStructure>
	implements JournalStructurePersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link JournalStructureUtil} to access the journal structure persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = JournalStructureImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(JournalStructureModelImpl.ENTITY_CACHE_ENABLED,
			JournalStructureModelImpl.FINDER_CACHE_ENABLED,
			JournalStructureImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(JournalStructureModelImpl.ENTITY_CACHE_ENABLED,
			JournalStructureModelImpl.FINDER_CACHE_ENABLED,
			JournalStructureImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(JournalStructureModelImpl.ENTITY_CACHE_ENABLED,
			JournalStructureModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_UUID = new FinderPath(JournalStructureModelImpl.ENTITY_CACHE_ENABLED,
			JournalStructureModelImpl.FINDER_CACHE_ENABLED,
			JournalStructureImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByUuid",
			new String[] {
				String.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID = new FinderPath(JournalStructureModelImpl.ENTITY_CACHE_ENABLED,
			JournalStructureModelImpl.FINDER_CACHE_ENABLED,
			JournalStructureImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid",
			new String[] { String.class.getName() },
			JournalStructureModelImpl.UUID_COLUMN_BITMASK |
			JournalStructureModelImpl.STRUCTUREID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID = new FinderPath(JournalStructureModelImpl.ENTITY_CACHE_ENABLED,
			JournalStructureModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUuid",
			new String[] { String.class.getName() });

	/**
	 * Returns all the journal structures where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching journal structures
	 * @throws SystemException if a system exception occurred
	 */
	public List<JournalStructure> findByUuid(String uuid)
		throws SystemException {
		return findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the journal structures where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.journal.model.impl.JournalStructureModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of journal structures
	 * @param end the upper bound of the range of journal structures (not inclusive)
	 * @return the range of matching journal structures
	 * @throws SystemException if a system exception occurred
	 */
	public List<JournalStructure> findByUuid(String uuid, int start, int end)
		throws SystemException {
		return findByUuid(uuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the journal structures where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.journal.model.impl.JournalStructureModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of journal structures
	 * @param end the upper bound of the range of journal structures (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching journal structures
	 * @throws SystemException if a system exception occurred
	 */
	public List<JournalStructure> findByUuid(String uuid, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
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

		List<JournalStructure> list = (List<JournalStructure>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (JournalStructure journalStructure : list) {
				if (!Validator.equals(uuid, journalStructure.getUuid())) {
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

			query.append(_SQL_SELECT_JOURNALSTRUCTURE_WHERE);

			if (uuid == null) {
				query.append(_FINDER_COLUMN_UUID_UUID_1);
			}
			else {
				if (uuid.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_UUID_UUID_3);
				}
				else {
					query.append(_FINDER_COLUMN_UUID_UUID_2);
				}
			}

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(JournalStructureModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (uuid != null) {
					qPos.add(uuid);
				}

				if (!pagination) {
					list = (List<JournalStructure>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = new UnmodifiableList<JournalStructure>(list);
				}
				else {
					list = (List<JournalStructure>)QueryUtil.list(q,
							getDialect(), start, end);
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
	 * Returns the first journal structure in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching journal structure
	 * @throws com.liferay.portlet.journal.NoSuchStructureException if a matching journal structure could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public JournalStructure findByUuid_First(String uuid,
		OrderByComparator orderByComparator)
		throws NoSuchStructureException, SystemException {
		JournalStructure journalStructure = fetchByUuid_First(uuid,
				orderByComparator);

		if (journalStructure != null) {
			return journalStructure;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchStructureException(msg.toString());
	}

	/**
	 * Returns the first journal structure in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching journal structure, or <code>null</code> if a matching journal structure could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public JournalStructure fetchByUuid_First(String uuid,
		OrderByComparator orderByComparator) throws SystemException {
		List<JournalStructure> list = findByUuid(uuid, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last journal structure in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching journal structure
	 * @throws com.liferay.portlet.journal.NoSuchStructureException if a matching journal structure could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public JournalStructure findByUuid_Last(String uuid,
		OrderByComparator orderByComparator)
		throws NoSuchStructureException, SystemException {
		JournalStructure journalStructure = fetchByUuid_Last(uuid,
				orderByComparator);

		if (journalStructure != null) {
			return journalStructure;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchStructureException(msg.toString());
	}

	/**
	 * Returns the last journal structure in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching journal structure, or <code>null</code> if a matching journal structure could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public JournalStructure fetchByUuid_Last(String uuid,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByUuid(uuid);

		List<JournalStructure> list = findByUuid(uuid, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the journal structures before and after the current journal structure in the ordered set where uuid = &#63;.
	 *
	 * @param id the primary key of the current journal structure
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next journal structure
	 * @throws com.liferay.portlet.journal.NoSuchStructureException if a journal structure with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public JournalStructure[] findByUuid_PrevAndNext(long id, String uuid,
		OrderByComparator orderByComparator)
		throws NoSuchStructureException, SystemException {
		JournalStructure journalStructure = findByPrimaryKey(id);

		Session session = null;

		try {
			session = openSession();

			JournalStructure[] array = new JournalStructureImpl[3];

			array[0] = getByUuid_PrevAndNext(session, journalStructure, uuid,
					orderByComparator, true);

			array[1] = journalStructure;

			array[2] = getByUuid_PrevAndNext(session, journalStructure, uuid,
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

	protected JournalStructure getByUuid_PrevAndNext(Session session,
		JournalStructure journalStructure, String uuid,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_JOURNALSTRUCTURE_WHERE);

		if (uuid == null) {
			query.append(_FINDER_COLUMN_UUID_UUID_1);
		}
		else {
			if (uuid.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_UUID_UUID_3);
			}
			else {
				query.append(_FINDER_COLUMN_UUID_UUID_2);
			}
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
			query.append(JournalStructureModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		if (uuid != null) {
			qPos.add(uuid);
		}

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(journalStructure);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<JournalStructure> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the journal structures where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByUuid(String uuid) throws SystemException {
		for (JournalStructure journalStructure : findByUuid(uuid,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(journalStructure);
		}
	}

	/**
	 * Returns the number of journal structures where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching journal structures
	 * @throws SystemException if a system exception occurred
	 */
	public int countByUuid(String uuid) throws SystemException {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_UUID;

		Object[] finderArgs = new Object[] { uuid };

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs,
				this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_JOURNALSTRUCTURE_WHERE);

			if (uuid == null) {
				query.append(_FINDER_COLUMN_UUID_UUID_1);
			}
			else {
				if (uuid.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_UUID_UUID_3);
				}
				else {
					query.append(_FINDER_COLUMN_UUID_UUID_2);
				}
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (uuid != null) {
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

	private static final String _FINDER_COLUMN_UUID_UUID_1 = "journalStructure.uuid IS NULL";
	private static final String _FINDER_COLUMN_UUID_UUID_2 = "journalStructure.uuid = ?";
	private static final String _FINDER_COLUMN_UUID_UUID_3 = "(journalStructure.uuid IS NULL OR journalStructure.uuid = ?)";
	public static final FinderPath FINDER_PATH_FETCH_BY_UUID_G = new FinderPath(JournalStructureModelImpl.ENTITY_CACHE_ENABLED,
			JournalStructureModelImpl.FINDER_CACHE_ENABLED,
			JournalStructureImpl.class, FINDER_CLASS_NAME_ENTITY,
			"fetchByUUID_G",
			new String[] { String.class.getName(), Long.class.getName() },
			JournalStructureModelImpl.UUID_COLUMN_BITMASK |
			JournalStructureModelImpl.GROUPID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID_G = new FinderPath(JournalStructureModelImpl.ENTITY_CACHE_ENABLED,
			JournalStructureModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUUID_G",
			new String[] { String.class.getName(), Long.class.getName() });

	/**
	 * Returns the journal structure where uuid = &#63; and groupId = &#63; or throws a {@link com.liferay.portlet.journal.NoSuchStructureException} if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching journal structure
	 * @throws com.liferay.portlet.journal.NoSuchStructureException if a matching journal structure could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public JournalStructure findByUUID_G(String uuid, long groupId)
		throws NoSuchStructureException, SystemException {
		JournalStructure journalStructure = fetchByUUID_G(uuid, groupId);

		if (journalStructure == null) {
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

		return journalStructure;
	}

	/**
	 * Returns the journal structure where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching journal structure, or <code>null</code> if a matching journal structure could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public JournalStructure fetchByUUID_G(String uuid, long groupId)
		throws SystemException {
		return fetchByUUID_G(uuid, groupId, true);
	}

	/**
	 * Returns the journal structure where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param retrieveFromCache whether to use the finder cache
	 * @return the matching journal structure, or <code>null</code> if a matching journal structure could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public JournalStructure fetchByUUID_G(String uuid, long groupId,
		boolean retrieveFromCache) throws SystemException {
		Object[] finderArgs = new Object[] { uuid, groupId };

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_UUID_G,
					finderArgs, this);
		}

		if (result instanceof JournalStructure) {
			JournalStructure journalStructure = (JournalStructure)result;

			if (!Validator.equals(uuid, journalStructure.getUuid()) ||
					(groupId != journalStructure.getGroupId())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_SELECT_JOURNALSTRUCTURE_WHERE);

			if (uuid == null) {
				query.append(_FINDER_COLUMN_UUID_G_UUID_1);
			}
			else {
				if (uuid.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_UUID_G_UUID_3);
				}
				else {
					query.append(_FINDER_COLUMN_UUID_G_UUID_2);
				}
			}

			query.append(_FINDER_COLUMN_UUID_G_GROUPID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (uuid != null) {
					qPos.add(uuid);
				}

				qPos.add(groupId);

				List<JournalStructure> list = q.list();

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_UUID_G,
						finderArgs, list);
				}
				else {
					JournalStructure journalStructure = list.get(0);

					result = journalStructure;

					cacheResult(journalStructure);

					if ((journalStructure.getUuid() == null) ||
							!journalStructure.getUuid().equals(uuid) ||
							(journalStructure.getGroupId() != groupId)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_UUID_G,
							finderArgs, journalStructure);
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
			return (JournalStructure)result;
		}
	}

	/**
	 * Removes the journal structure where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the journal structure that was removed
	 * @throws SystemException if a system exception occurred
	 */
	public JournalStructure removeByUUID_G(String uuid, long groupId)
		throws NoSuchStructureException, SystemException {
		JournalStructure journalStructure = findByUUID_G(uuid, groupId);

		return remove(journalStructure);
	}

	/**
	 * Returns the number of journal structures where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching journal structures
	 * @throws SystemException if a system exception occurred
	 */
	public int countByUUID_G(String uuid, long groupId)
		throws SystemException {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_UUID_G;

		Object[] finderArgs = new Object[] { uuid, groupId };

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs,
				this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_JOURNALSTRUCTURE_WHERE);

			if (uuid == null) {
				query.append(_FINDER_COLUMN_UUID_G_UUID_1);
			}
			else {
				if (uuid.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_UUID_G_UUID_3);
				}
				else {
					query.append(_FINDER_COLUMN_UUID_G_UUID_2);
				}
			}

			query.append(_FINDER_COLUMN_UUID_G_GROUPID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (uuid != null) {
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

	private static final String _FINDER_COLUMN_UUID_G_UUID_1 = "journalStructure.uuid IS NULL AND ";
	private static final String _FINDER_COLUMN_UUID_G_UUID_2 = "journalStructure.uuid = ? AND ";
	private static final String _FINDER_COLUMN_UUID_G_UUID_3 = "(journalStructure.uuid IS NULL OR journalStructure.uuid = ?) AND ";
	private static final String _FINDER_COLUMN_UUID_G_GROUPID_2 = "journalStructure.groupId = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_UUID_C = new FinderPath(JournalStructureModelImpl.ENTITY_CACHE_ENABLED,
			JournalStructureModelImpl.FINDER_CACHE_ENABLED,
			JournalStructureImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByUuid_C",
			new String[] {
				String.class.getName(), Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C =
		new FinderPath(JournalStructureModelImpl.ENTITY_CACHE_ENABLED,
			JournalStructureModelImpl.FINDER_CACHE_ENABLED,
			JournalStructureImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid_C",
			new String[] { String.class.getName(), Long.class.getName() },
			JournalStructureModelImpl.UUID_COLUMN_BITMASK |
			JournalStructureModelImpl.COMPANYID_COLUMN_BITMASK |
			JournalStructureModelImpl.STRUCTUREID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID_C = new FinderPath(JournalStructureModelImpl.ENTITY_CACHE_ENABLED,
			JournalStructureModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUuid_C",
			new String[] { String.class.getName(), Long.class.getName() });

	/**
	 * Returns all the journal structures where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching journal structures
	 * @throws SystemException if a system exception occurred
	 */
	public List<JournalStructure> findByUuid_C(String uuid, long companyId)
		throws SystemException {
		return findByUuid_C(uuid, companyId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the journal structures where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.journal.model.impl.JournalStructureModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of journal structures
	 * @param end the upper bound of the range of journal structures (not inclusive)
	 * @return the range of matching journal structures
	 * @throws SystemException if a system exception occurred
	 */
	public List<JournalStructure> findByUuid_C(String uuid, long companyId,
		int start, int end) throws SystemException {
		return findByUuid_C(uuid, companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the journal structures where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.journal.model.impl.JournalStructureModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of journal structures
	 * @param end the upper bound of the range of journal structures (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching journal structures
	 * @throws SystemException if a system exception occurred
	 */
	public List<JournalStructure> findByUuid_C(String uuid, long companyId,
		int start, int end, OrderByComparator orderByComparator)
		throws SystemException {
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

		List<JournalStructure> list = (List<JournalStructure>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (JournalStructure journalStructure : list) {
				if (!Validator.equals(uuid, journalStructure.getUuid()) ||
						(companyId != journalStructure.getCompanyId())) {
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

			query.append(_SQL_SELECT_JOURNALSTRUCTURE_WHERE);

			if (uuid == null) {
				query.append(_FINDER_COLUMN_UUID_C_UUID_1);
			}
			else {
				if (uuid.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_UUID_C_UUID_3);
				}
				else {
					query.append(_FINDER_COLUMN_UUID_C_UUID_2);
				}
			}

			query.append(_FINDER_COLUMN_UUID_C_COMPANYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(JournalStructureModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (uuid != null) {
					qPos.add(uuid);
				}

				qPos.add(companyId);

				if (!pagination) {
					list = (List<JournalStructure>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = new UnmodifiableList<JournalStructure>(list);
				}
				else {
					list = (List<JournalStructure>)QueryUtil.list(q,
							getDialect(), start, end);
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
	 * Returns the first journal structure in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching journal structure
	 * @throws com.liferay.portlet.journal.NoSuchStructureException if a matching journal structure could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public JournalStructure findByUuid_C_First(String uuid, long companyId,
		OrderByComparator orderByComparator)
		throws NoSuchStructureException, SystemException {
		JournalStructure journalStructure = fetchByUuid_C_First(uuid,
				companyId, orderByComparator);

		if (journalStructure != null) {
			return journalStructure;
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
	 * Returns the first journal structure in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching journal structure, or <code>null</code> if a matching journal structure could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public JournalStructure fetchByUuid_C_First(String uuid, long companyId,
		OrderByComparator orderByComparator) throws SystemException {
		List<JournalStructure> list = findByUuid_C(uuid, companyId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last journal structure in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching journal structure
	 * @throws com.liferay.portlet.journal.NoSuchStructureException if a matching journal structure could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public JournalStructure findByUuid_C_Last(String uuid, long companyId,
		OrderByComparator orderByComparator)
		throws NoSuchStructureException, SystemException {
		JournalStructure journalStructure = fetchByUuid_C_Last(uuid, companyId,
				orderByComparator);

		if (journalStructure != null) {
			return journalStructure;
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
	 * Returns the last journal structure in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching journal structure, or <code>null</code> if a matching journal structure could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public JournalStructure fetchByUuid_C_Last(String uuid, long companyId,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByUuid_C(uuid, companyId);

		List<JournalStructure> list = findByUuid_C(uuid, companyId, count - 1,
				count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the journal structures before and after the current journal structure in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param id the primary key of the current journal structure
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next journal structure
	 * @throws com.liferay.portlet.journal.NoSuchStructureException if a journal structure with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public JournalStructure[] findByUuid_C_PrevAndNext(long id, String uuid,
		long companyId, OrderByComparator orderByComparator)
		throws NoSuchStructureException, SystemException {
		JournalStructure journalStructure = findByPrimaryKey(id);

		Session session = null;

		try {
			session = openSession();

			JournalStructure[] array = new JournalStructureImpl[3];

			array[0] = getByUuid_C_PrevAndNext(session, journalStructure, uuid,
					companyId, orderByComparator, true);

			array[1] = journalStructure;

			array[2] = getByUuid_C_PrevAndNext(session, journalStructure, uuid,
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

	protected JournalStructure getByUuid_C_PrevAndNext(Session session,
		JournalStructure journalStructure, String uuid, long companyId,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_JOURNALSTRUCTURE_WHERE);

		if (uuid == null) {
			query.append(_FINDER_COLUMN_UUID_C_UUID_1);
		}
		else {
			if (uuid.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_UUID_C_UUID_3);
			}
			else {
				query.append(_FINDER_COLUMN_UUID_C_UUID_2);
			}
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
			query.append(JournalStructureModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		if (uuid != null) {
			qPos.add(uuid);
		}

		qPos.add(companyId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(journalStructure);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<JournalStructure> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the journal structures where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByUuid_C(String uuid, long companyId)
		throws SystemException {
		for (JournalStructure journalStructure : findByUuid_C(uuid, companyId,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(journalStructure);
		}
	}

	/**
	 * Returns the number of journal structures where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching journal structures
	 * @throws SystemException if a system exception occurred
	 */
	public int countByUuid_C(String uuid, long companyId)
		throws SystemException {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_UUID_C;

		Object[] finderArgs = new Object[] { uuid, companyId };

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs,
				this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_JOURNALSTRUCTURE_WHERE);

			if (uuid == null) {
				query.append(_FINDER_COLUMN_UUID_C_UUID_1);
			}
			else {
				if (uuid.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_UUID_C_UUID_3);
				}
				else {
					query.append(_FINDER_COLUMN_UUID_C_UUID_2);
				}
			}

			query.append(_FINDER_COLUMN_UUID_C_COMPANYID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (uuid != null) {
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

	private static final String _FINDER_COLUMN_UUID_C_UUID_1 = "journalStructure.uuid IS NULL AND ";
	private static final String _FINDER_COLUMN_UUID_C_UUID_2 = "journalStructure.uuid = ? AND ";
	private static final String _FINDER_COLUMN_UUID_C_UUID_3 = "(journalStructure.uuid IS NULL OR journalStructure.uuid = ?) AND ";
	private static final String _FINDER_COLUMN_UUID_C_COMPANYID_2 = "journalStructure.companyId = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_GROUPID = new FinderPath(JournalStructureModelImpl.ENTITY_CACHE_ENABLED,
			JournalStructureModelImpl.FINDER_CACHE_ENABLED,
			JournalStructureImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByGroupId",
			new String[] {
				Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID =
		new FinderPath(JournalStructureModelImpl.ENTITY_CACHE_ENABLED,
			JournalStructureModelImpl.FINDER_CACHE_ENABLED,
			JournalStructureImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByGroupId",
			new String[] { Long.class.getName() },
			JournalStructureModelImpl.GROUPID_COLUMN_BITMASK |
			JournalStructureModelImpl.STRUCTUREID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_GROUPID = new FinderPath(JournalStructureModelImpl.ENTITY_CACHE_ENABLED,
			JournalStructureModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByGroupId",
			new String[] { Long.class.getName() });
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_COUNT_BY_GROUPID = new FinderPath(JournalStructureModelImpl.ENTITY_CACHE_ENABLED,
			JournalStructureModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "countByGroupId",
			new String[] { Long.class.getName() });

	/**
	 * Returns all the journal structures where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching journal structures
	 * @throws SystemException if a system exception occurred
	 */
	public List<JournalStructure> findByGroupId(long groupId)
		throws SystemException {
		return findByGroupId(groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the journal structures where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.journal.model.impl.JournalStructureModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of journal structures
	 * @param end the upper bound of the range of journal structures (not inclusive)
	 * @return the range of matching journal structures
	 * @throws SystemException if a system exception occurred
	 */
	public List<JournalStructure> findByGroupId(long groupId, int start, int end)
		throws SystemException {
		return findByGroupId(groupId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the journal structures where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.journal.model.impl.JournalStructureModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of journal structures
	 * @param end the upper bound of the range of journal structures (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching journal structures
	 * @throws SystemException if a system exception occurred
	 */
	public List<JournalStructure> findByGroupId(long groupId, int start,
		int end, OrderByComparator orderByComparator) throws SystemException {
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

		List<JournalStructure> list = (List<JournalStructure>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (JournalStructure journalStructure : list) {
				if ((groupId != journalStructure.getGroupId())) {
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

			query.append(_SQL_SELECT_JOURNALSTRUCTURE_WHERE);

			query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(JournalStructureModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (!pagination) {
					list = (List<JournalStructure>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = new UnmodifiableList<JournalStructure>(list);
				}
				else {
					list = (List<JournalStructure>)QueryUtil.list(q,
							getDialect(), start, end);
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
	 * Returns the first journal structure in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching journal structure
	 * @throws com.liferay.portlet.journal.NoSuchStructureException if a matching journal structure could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public JournalStructure findByGroupId_First(long groupId,
		OrderByComparator orderByComparator)
		throws NoSuchStructureException, SystemException {
		JournalStructure journalStructure = fetchByGroupId_First(groupId,
				orderByComparator);

		if (journalStructure != null) {
			return journalStructure;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchStructureException(msg.toString());
	}

	/**
	 * Returns the first journal structure in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching journal structure, or <code>null</code> if a matching journal structure could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public JournalStructure fetchByGroupId_First(long groupId,
		OrderByComparator orderByComparator) throws SystemException {
		List<JournalStructure> list = findByGroupId(groupId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last journal structure in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching journal structure
	 * @throws com.liferay.portlet.journal.NoSuchStructureException if a matching journal structure could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public JournalStructure findByGroupId_Last(long groupId,
		OrderByComparator orderByComparator)
		throws NoSuchStructureException, SystemException {
		JournalStructure journalStructure = fetchByGroupId_Last(groupId,
				orderByComparator);

		if (journalStructure != null) {
			return journalStructure;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchStructureException(msg.toString());
	}

	/**
	 * Returns the last journal structure in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching journal structure, or <code>null</code> if a matching journal structure could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public JournalStructure fetchByGroupId_Last(long groupId,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByGroupId(groupId);

		List<JournalStructure> list = findByGroupId(groupId, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the journal structures before and after the current journal structure in the ordered set where groupId = &#63;.
	 *
	 * @param id the primary key of the current journal structure
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next journal structure
	 * @throws com.liferay.portlet.journal.NoSuchStructureException if a journal structure with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public JournalStructure[] findByGroupId_PrevAndNext(long id, long groupId,
		OrderByComparator orderByComparator)
		throws NoSuchStructureException, SystemException {
		JournalStructure journalStructure = findByPrimaryKey(id);

		Session session = null;

		try {
			session = openSession();

			JournalStructure[] array = new JournalStructureImpl[3];

			array[0] = getByGroupId_PrevAndNext(session, journalStructure,
					groupId, orderByComparator, true);

			array[1] = journalStructure;

			array[2] = getByGroupId_PrevAndNext(session, journalStructure,
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

	protected JournalStructure getByGroupId_PrevAndNext(Session session,
		JournalStructure journalStructure, long groupId,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_JOURNALSTRUCTURE_WHERE);

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
			query.append(JournalStructureModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(journalStructure);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<JournalStructure> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the journal structures that the user has permission to view where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching journal structures that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<JournalStructure> filterFindByGroupId(long groupId)
		throws SystemException {
		return filterFindByGroupId(groupId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the journal structures that the user has permission to view where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.journal.model.impl.JournalStructureModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of journal structures
	 * @param end the upper bound of the range of journal structures (not inclusive)
	 * @return the range of matching journal structures that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<JournalStructure> filterFindByGroupId(long groupId, int start,
		int end) throws SystemException {
		return filterFindByGroupId(groupId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the journal structures that the user has permissions to view where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.journal.model.impl.JournalStructureModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of journal structures
	 * @param end the upper bound of the range of journal structures (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching journal structures that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<JournalStructure> filterFindByGroupId(long groupId, int start,
		int end, OrderByComparator orderByComparator) throws SystemException {
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
			query.append(_FILTER_SQL_SELECT_JOURNALSTRUCTURE_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_JOURNALSTRUCTURE_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_JOURNALSTRUCTURE_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			if (getDB().isSupportsInlineDistinct()) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_TABLE,
					orderByComparator);
			}
		}
		else {
			if (getDB().isSupportsInlineDistinct()) {
				query.append(JournalStructureModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(JournalStructureModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				JournalStructure.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				q.addEntity(_FILTER_ENTITY_ALIAS, JournalStructureImpl.class);
			}
			else {
				q.addEntity(_FILTER_ENTITY_TABLE, JournalStructureImpl.class);
			}

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			return (List<JournalStructure>)QueryUtil.list(q, getDialect(),
				start, end);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Returns the journal structures before and after the current journal structure in the ordered set of journal structures that the user has permission to view where groupId = &#63;.
	 *
	 * @param id the primary key of the current journal structure
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next journal structure
	 * @throws com.liferay.portlet.journal.NoSuchStructureException if a journal structure with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public JournalStructure[] filterFindByGroupId_PrevAndNext(long id,
		long groupId, OrderByComparator orderByComparator)
		throws NoSuchStructureException, SystemException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByGroupId_PrevAndNext(id, groupId, orderByComparator);
		}

		JournalStructure journalStructure = findByPrimaryKey(id);

		Session session = null;

		try {
			session = openSession();

			JournalStructure[] array = new JournalStructureImpl[3];

			array[0] = filterGetByGroupId_PrevAndNext(session,
					journalStructure, groupId, orderByComparator, true);

			array[1] = journalStructure;

			array[2] = filterGetByGroupId_PrevAndNext(session,
					journalStructure, groupId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected JournalStructure filterGetByGroupId_PrevAndNext(Session session,
		JournalStructure journalStructure, long groupId,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_JOURNALSTRUCTURE_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_JOURNALSTRUCTURE_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_JOURNALSTRUCTURE_NO_INLINE_DISTINCT_WHERE_2);
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
				query.append(JournalStructureModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(JournalStructureModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				JournalStructure.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		SQLQuery q = session.createSQLQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		if (getDB().isSupportsInlineDistinct()) {
			q.addEntity(_FILTER_ENTITY_ALIAS, JournalStructureImpl.class);
		}
		else {
			q.addEntity(_FILTER_ENTITY_TABLE, JournalStructureImpl.class);
		}

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(journalStructure);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<JournalStructure> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the journal structures that the user has permission to view where groupId = any &#63;.
	 *
	 * @param groupIds the group IDs
	 * @return the matching journal structures that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<JournalStructure> filterFindByGroupId(long[] groupIds)
		throws SystemException {
		return filterFindByGroupId(groupIds, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the journal structures that the user has permission to view where groupId = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.journal.model.impl.JournalStructureModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupIds the group IDs
	 * @param start the lower bound of the range of journal structures
	 * @param end the upper bound of the range of journal structures (not inclusive)
	 * @return the range of matching journal structures that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<JournalStructure> filterFindByGroupId(long[] groupIds,
		int start, int end) throws SystemException {
		return filterFindByGroupId(groupIds, start, end, null);
	}

	/**
	 * Returns an ordered range of all the journal structures that the user has permission to view where groupId = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.journal.model.impl.JournalStructureModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupIds the group IDs
	 * @param start the lower bound of the range of journal structures
	 * @param end the upper bound of the range of journal structures (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching journal structures that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<JournalStructure> filterFindByGroupId(long[] groupIds,
		int start, int end, OrderByComparator orderByComparator)
		throws SystemException {
		if (!InlineSQLHelperUtil.isEnabled(groupIds)) {
			return findByGroupId(groupIds, start, end, orderByComparator);
		}

		StringBundler query = new StringBundler();

		if (getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_JOURNALSTRUCTURE_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_JOURNALSTRUCTURE_NO_INLINE_DISTINCT_WHERE_1);
		}

		boolean conjunctionable = false;

		if ((groupIds == null) || (groupIds.length > 0)) {
			if (conjunctionable) {
				query.append(WHERE_AND);
			}

			query.append(StringPool.OPEN_PARENTHESIS);

			for (int i = 0; i < groupIds.length; i++) {
				query.append(_FINDER_COLUMN_GROUPID_GROUPID_5);

				if ((i + 1) < groupIds.length) {
					query.append(WHERE_OR);
				}
			}

			query.append(StringPool.CLOSE_PARENTHESIS);

			conjunctionable = true;
		}

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_JOURNALSTRUCTURE_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			if (getDB().isSupportsInlineDistinct()) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_TABLE,
					orderByComparator);
			}
		}
		else {
			if (getDB().isSupportsInlineDistinct()) {
				query.append(JournalStructureModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(JournalStructureModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				JournalStructure.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupIds);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				q.addEntity(_FILTER_ENTITY_ALIAS, JournalStructureImpl.class);
			}
			else {
				q.addEntity(_FILTER_ENTITY_TABLE, JournalStructureImpl.class);
			}

			QueryPos qPos = QueryPos.getInstance(q);

			if (groupIds != null) {
				qPos.add(groupIds);
			}

			return (List<JournalStructure>)QueryUtil.list(q, getDialect(),
				start, end);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Returns all the journal structures where groupId = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.journal.model.impl.JournalStructureModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupIds the group IDs
	 * @return the matching journal structures
	 * @throws SystemException if a system exception occurred
	 */
	public List<JournalStructure> findByGroupId(long[] groupIds)
		throws SystemException {
		return findByGroupId(groupIds, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the journal structures where groupId = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.journal.model.impl.JournalStructureModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupIds the group IDs
	 * @param start the lower bound of the range of journal structures
	 * @param end the upper bound of the range of journal structures (not inclusive)
	 * @return the range of matching journal structures
	 * @throws SystemException if a system exception occurred
	 */
	public List<JournalStructure> findByGroupId(long[] groupIds, int start,
		int end) throws SystemException {
		return findByGroupId(groupIds, start, end, null);
	}

	/**
	 * Returns an ordered range of all the journal structures where groupId = any &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.journal.model.impl.JournalStructureModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupIds the group IDs
	 * @param start the lower bound of the range of journal structures
	 * @param end the upper bound of the range of journal structures (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching journal structures
	 * @throws SystemException if a system exception occurred
	 */
	public List<JournalStructure> findByGroupId(long[] groupIds, int start,
		int end, OrderByComparator orderByComparator) throws SystemException {
		if ((groupIds != null) && (groupIds.length == 1)) {
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

		List<JournalStructure> list = (List<JournalStructure>)FinderCacheUtil.getResult(FINDER_PATH_WITH_PAGINATION_FIND_BY_GROUPID,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (JournalStructure journalStructure : list) {
				if (!ArrayUtil.contains(groupIds, journalStructure.getGroupId())) {
					list = null;

					break;
				}
			}
		}

		if (list == null) {
			StringBundler query = new StringBundler();

			query.append(_SQL_SELECT_JOURNALSTRUCTURE_WHERE);

			boolean conjunctionable = false;

			if ((groupIds == null) || (groupIds.length > 0)) {
				if (conjunctionable) {
					query.append(WHERE_AND);
				}

				query.append(StringPool.OPEN_PARENTHESIS);

				for (int i = 0; i < groupIds.length; i++) {
					query.append(_FINDER_COLUMN_GROUPID_GROUPID_5);

					if ((i + 1) < groupIds.length) {
						query.append(WHERE_OR);
					}
				}

				query.append(StringPool.CLOSE_PARENTHESIS);

				conjunctionable = true;
			}

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(JournalStructureModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (groupIds != null) {
					qPos.add(groupIds);
				}

				if (!pagination) {
					list = (List<JournalStructure>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = new UnmodifiableList<JournalStructure>(list);
				}
				else {
					list = (List<JournalStructure>)QueryUtil.list(q,
							getDialect(), start, end);
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
	 * Removes all the journal structures where groupId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByGroupId(long groupId) throws SystemException {
		for (JournalStructure journalStructure : findByGroupId(groupId,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(journalStructure);
		}
	}

	/**
	 * Returns the number of journal structures where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching journal structures
	 * @throws SystemException if a system exception occurred
	 */
	public int countByGroupId(long groupId) throws SystemException {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_GROUPID;

		Object[] finderArgs = new Object[] { groupId };

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs,
				this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_JOURNALSTRUCTURE_WHERE);

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
	 * Returns the number of journal structures where groupId = any &#63;.
	 *
	 * @param groupIds the group IDs
	 * @return the number of matching journal structures
	 * @throws SystemException if a system exception occurred
	 */
	public int countByGroupId(long[] groupIds) throws SystemException {
		Object[] finderArgs = new Object[] { StringUtil.merge(groupIds) };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_WITH_PAGINATION_COUNT_BY_GROUPID,
				finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler();

			query.append(_SQL_COUNT_JOURNALSTRUCTURE_WHERE);

			boolean conjunctionable = false;

			if ((groupIds == null) || (groupIds.length > 0)) {
				if (conjunctionable) {
					query.append(WHERE_AND);
				}

				query.append(StringPool.OPEN_PARENTHESIS);

				for (int i = 0; i < groupIds.length; i++) {
					query.append(_FINDER_COLUMN_GROUPID_GROUPID_5);

					if ((i + 1) < groupIds.length) {
						query.append(WHERE_OR);
					}
				}

				query.append(StringPool.CLOSE_PARENTHESIS);

				conjunctionable = true;
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (groupIds != null) {
					qPos.add(groupIds);
				}

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
	 * Returns the number of journal structures that the user has permission to view where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching journal structures that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public int filterCountByGroupId(long groupId) throws SystemException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByGroupId(groupId);
		}

		StringBundler query = new StringBundler(2);

		query.append(_FILTER_SQL_COUNT_JOURNALSTRUCTURE_WHERE);

		query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				JournalStructure.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSQLQuery(sql);

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
	 * Returns the number of journal structures that the user has permission to view where groupId = any &#63;.
	 *
	 * @param groupIds the group IDs
	 * @return the number of matching journal structures that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public int filterCountByGroupId(long[] groupIds) throws SystemException {
		if (!InlineSQLHelperUtil.isEnabled(groupIds)) {
			return countByGroupId(groupIds);
		}

		StringBundler query = new StringBundler();

		query.append(_FILTER_SQL_COUNT_JOURNALSTRUCTURE_WHERE);

		boolean conjunctionable = false;

		if ((groupIds == null) || (groupIds.length > 0)) {
			if (conjunctionable) {
				query.append(WHERE_AND);
			}

			query.append(StringPool.OPEN_PARENTHESIS);

			for (int i = 0; i < groupIds.length; i++) {
				query.append(_FINDER_COLUMN_GROUPID_GROUPID_5);

				if ((i + 1) < groupIds.length) {
					query.append(WHERE_OR);
				}
			}

			query.append(StringPool.CLOSE_PARENTHESIS);

			conjunctionable = true;
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				JournalStructure.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupIds);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME,
				com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			if (groupIds != null) {
				qPos.add(groupIds);
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

	private static final String _FINDER_COLUMN_GROUPID_GROUPID_2 = "journalStructure.groupId = ?";
	private static final String _FINDER_COLUMN_GROUPID_GROUPID_5 = "(" +
		_removeConjunction(_FINDER_COLUMN_GROUPID_GROUPID_2) + ")";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_STRUCTUREID =
		new FinderPath(JournalStructureModelImpl.ENTITY_CACHE_ENABLED,
			JournalStructureModelImpl.FINDER_CACHE_ENABLED,
			JournalStructureImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByStructureId",
			new String[] {
				String.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_STRUCTUREID =
		new FinderPath(JournalStructureModelImpl.ENTITY_CACHE_ENABLED,
			JournalStructureModelImpl.FINDER_CACHE_ENABLED,
			JournalStructureImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByStructureId",
			new String[] { String.class.getName() },
			JournalStructureModelImpl.STRUCTUREID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_STRUCTUREID = new FinderPath(JournalStructureModelImpl.ENTITY_CACHE_ENABLED,
			JournalStructureModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByStructureId",
			new String[] { String.class.getName() });

	/**
	 * Returns all the journal structures where structureId = &#63;.
	 *
	 * @param structureId the structure ID
	 * @return the matching journal structures
	 * @throws SystemException if a system exception occurred
	 */
	public List<JournalStructure> findByStructureId(String structureId)
		throws SystemException {
		return findByStructureId(structureId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the journal structures where structureId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.journal.model.impl.JournalStructureModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param structureId the structure ID
	 * @param start the lower bound of the range of journal structures
	 * @param end the upper bound of the range of journal structures (not inclusive)
	 * @return the range of matching journal structures
	 * @throws SystemException if a system exception occurred
	 */
	public List<JournalStructure> findByStructureId(String structureId,
		int start, int end) throws SystemException {
		return findByStructureId(structureId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the journal structures where structureId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.journal.model.impl.JournalStructureModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param structureId the structure ID
	 * @param start the lower bound of the range of journal structures
	 * @param end the upper bound of the range of journal structures (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching journal structures
	 * @throws SystemException if a system exception occurred
	 */
	public List<JournalStructure> findByStructureId(String structureId,
		int start, int end, OrderByComparator orderByComparator)
		throws SystemException {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_STRUCTUREID;
			finderArgs = new Object[] { structureId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_STRUCTUREID;
			finderArgs = new Object[] { structureId, start, end, orderByComparator };
		}

		List<JournalStructure> list = (List<JournalStructure>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (JournalStructure journalStructure : list) {
				if (!Validator.equals(structureId,
							journalStructure.getStructureId())) {
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

			query.append(_SQL_SELECT_JOURNALSTRUCTURE_WHERE);

			if (structureId == null) {
				query.append(_FINDER_COLUMN_STRUCTUREID_STRUCTUREID_1);
			}
			else {
				if (structureId.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_STRUCTUREID_STRUCTUREID_3);
				}
				else {
					query.append(_FINDER_COLUMN_STRUCTUREID_STRUCTUREID_2);
				}
			}

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(JournalStructureModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (structureId != null) {
					qPos.add(structureId);
				}

				if (!pagination) {
					list = (List<JournalStructure>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = new UnmodifiableList<JournalStructure>(list);
				}
				else {
					list = (List<JournalStructure>)QueryUtil.list(q,
							getDialect(), start, end);
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
	 * Returns the first journal structure in the ordered set where structureId = &#63;.
	 *
	 * @param structureId the structure ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching journal structure
	 * @throws com.liferay.portlet.journal.NoSuchStructureException if a matching journal structure could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public JournalStructure findByStructureId_First(String structureId,
		OrderByComparator orderByComparator)
		throws NoSuchStructureException, SystemException {
		JournalStructure journalStructure = fetchByStructureId_First(structureId,
				orderByComparator);

		if (journalStructure != null) {
			return journalStructure;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("structureId=");
		msg.append(structureId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchStructureException(msg.toString());
	}

	/**
	 * Returns the first journal structure in the ordered set where structureId = &#63;.
	 *
	 * @param structureId the structure ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching journal structure, or <code>null</code> if a matching journal structure could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public JournalStructure fetchByStructureId_First(String structureId,
		OrderByComparator orderByComparator) throws SystemException {
		List<JournalStructure> list = findByStructureId(structureId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last journal structure in the ordered set where structureId = &#63;.
	 *
	 * @param structureId the structure ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching journal structure
	 * @throws com.liferay.portlet.journal.NoSuchStructureException if a matching journal structure could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public JournalStructure findByStructureId_Last(String structureId,
		OrderByComparator orderByComparator)
		throws NoSuchStructureException, SystemException {
		JournalStructure journalStructure = fetchByStructureId_Last(structureId,
				orderByComparator);

		if (journalStructure != null) {
			return journalStructure;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("structureId=");
		msg.append(structureId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchStructureException(msg.toString());
	}

	/**
	 * Returns the last journal structure in the ordered set where structureId = &#63;.
	 *
	 * @param structureId the structure ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching journal structure, or <code>null</code> if a matching journal structure could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public JournalStructure fetchByStructureId_Last(String structureId,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByStructureId(structureId);

		List<JournalStructure> list = findByStructureId(structureId, count - 1,
				count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the journal structures before and after the current journal structure in the ordered set where structureId = &#63;.
	 *
	 * @param id the primary key of the current journal structure
	 * @param structureId the structure ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next journal structure
	 * @throws com.liferay.portlet.journal.NoSuchStructureException if a journal structure with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public JournalStructure[] findByStructureId_PrevAndNext(long id,
		String structureId, OrderByComparator orderByComparator)
		throws NoSuchStructureException, SystemException {
		JournalStructure journalStructure = findByPrimaryKey(id);

		Session session = null;

		try {
			session = openSession();

			JournalStructure[] array = new JournalStructureImpl[3];

			array[0] = getByStructureId_PrevAndNext(session, journalStructure,
					structureId, orderByComparator, true);

			array[1] = journalStructure;

			array[2] = getByStructureId_PrevAndNext(session, journalStructure,
					structureId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected JournalStructure getByStructureId_PrevAndNext(Session session,
		JournalStructure journalStructure, String structureId,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_JOURNALSTRUCTURE_WHERE);

		if (structureId == null) {
			query.append(_FINDER_COLUMN_STRUCTUREID_STRUCTUREID_1);
		}
		else {
			if (structureId.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_STRUCTUREID_STRUCTUREID_3);
			}
			else {
				query.append(_FINDER_COLUMN_STRUCTUREID_STRUCTUREID_2);
			}
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
			query.append(JournalStructureModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		if (structureId != null) {
			qPos.add(structureId);
		}

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(journalStructure);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<JournalStructure> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the journal structures where structureId = &#63; from the database.
	 *
	 * @param structureId the structure ID
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByStructureId(String structureId)
		throws SystemException {
		for (JournalStructure journalStructure : findByStructureId(
				structureId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(journalStructure);
		}
	}

	/**
	 * Returns the number of journal structures where structureId = &#63;.
	 *
	 * @param structureId the structure ID
	 * @return the number of matching journal structures
	 * @throws SystemException if a system exception occurred
	 */
	public int countByStructureId(String structureId) throws SystemException {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_STRUCTUREID;

		Object[] finderArgs = new Object[] { structureId };

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs,
				this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_JOURNALSTRUCTURE_WHERE);

			if (structureId == null) {
				query.append(_FINDER_COLUMN_STRUCTUREID_STRUCTUREID_1);
			}
			else {
				if (structureId.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_STRUCTUREID_STRUCTUREID_3);
				}
				else {
					query.append(_FINDER_COLUMN_STRUCTUREID_STRUCTUREID_2);
				}
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (structureId != null) {
					qPos.add(structureId);
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

	private static final String _FINDER_COLUMN_STRUCTUREID_STRUCTUREID_1 = "journalStructure.structureId IS NULL";
	private static final String _FINDER_COLUMN_STRUCTUREID_STRUCTUREID_2 = "journalStructure.structureId = ?";
	private static final String _FINDER_COLUMN_STRUCTUREID_STRUCTUREID_3 = "(journalStructure.structureId IS NULL OR journalStructure.structureId = ?)";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_PARENTSTRUCTUREID =
		new FinderPath(JournalStructureModelImpl.ENTITY_CACHE_ENABLED,
			JournalStructureModelImpl.FINDER_CACHE_ENABLED,
			JournalStructureImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByParentStructureId",
			new String[] {
				String.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_PARENTSTRUCTUREID =
		new FinderPath(JournalStructureModelImpl.ENTITY_CACHE_ENABLED,
			JournalStructureModelImpl.FINDER_CACHE_ENABLED,
			JournalStructureImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByParentStructureId", new String[] { String.class.getName() },
			JournalStructureModelImpl.PARENTSTRUCTUREID_COLUMN_BITMASK |
			JournalStructureModelImpl.STRUCTUREID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_PARENTSTRUCTUREID = new FinderPath(JournalStructureModelImpl.ENTITY_CACHE_ENABLED,
			JournalStructureModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByParentStructureId", new String[] { String.class.getName() });

	/**
	 * Returns all the journal structures where parentStructureId = &#63;.
	 *
	 * @param parentStructureId the parent structure ID
	 * @return the matching journal structures
	 * @throws SystemException if a system exception occurred
	 */
	public List<JournalStructure> findByParentStructureId(
		String parentStructureId) throws SystemException {
		return findByParentStructureId(parentStructureId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the journal structures where parentStructureId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.journal.model.impl.JournalStructureModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param parentStructureId the parent structure ID
	 * @param start the lower bound of the range of journal structures
	 * @param end the upper bound of the range of journal structures (not inclusive)
	 * @return the range of matching journal structures
	 * @throws SystemException if a system exception occurred
	 */
	public List<JournalStructure> findByParentStructureId(
		String parentStructureId, int start, int end) throws SystemException {
		return findByParentStructureId(parentStructureId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the journal structures where parentStructureId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.journal.model.impl.JournalStructureModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param parentStructureId the parent structure ID
	 * @param start the lower bound of the range of journal structures
	 * @param end the upper bound of the range of journal structures (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching journal structures
	 * @throws SystemException if a system exception occurred
	 */
	public List<JournalStructure> findByParentStructureId(
		String parentStructureId, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
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

		List<JournalStructure> list = (List<JournalStructure>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (JournalStructure journalStructure : list) {
				if (!Validator.equals(parentStructureId,
							journalStructure.getParentStructureId())) {
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

			query.append(_SQL_SELECT_JOURNALSTRUCTURE_WHERE);

			if (parentStructureId == null) {
				query.append(_FINDER_COLUMN_PARENTSTRUCTUREID_PARENTSTRUCTUREID_1);
			}
			else {
				if (parentStructureId.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_PARENTSTRUCTUREID_PARENTSTRUCTUREID_3);
				}
				else {
					query.append(_FINDER_COLUMN_PARENTSTRUCTUREID_PARENTSTRUCTUREID_2);
				}
			}

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(JournalStructureModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (parentStructureId != null) {
					qPos.add(parentStructureId);
				}

				if (!pagination) {
					list = (List<JournalStructure>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = new UnmodifiableList<JournalStructure>(list);
				}
				else {
					list = (List<JournalStructure>)QueryUtil.list(q,
							getDialect(), start, end);
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
	 * Returns the first journal structure in the ordered set where parentStructureId = &#63;.
	 *
	 * @param parentStructureId the parent structure ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching journal structure
	 * @throws com.liferay.portlet.journal.NoSuchStructureException if a matching journal structure could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public JournalStructure findByParentStructureId_First(
		String parentStructureId, OrderByComparator orderByComparator)
		throws NoSuchStructureException, SystemException {
		JournalStructure journalStructure = fetchByParentStructureId_First(parentStructureId,
				orderByComparator);

		if (journalStructure != null) {
			return journalStructure;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("parentStructureId=");
		msg.append(parentStructureId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchStructureException(msg.toString());
	}

	/**
	 * Returns the first journal structure in the ordered set where parentStructureId = &#63;.
	 *
	 * @param parentStructureId the parent structure ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching journal structure, or <code>null</code> if a matching journal structure could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public JournalStructure fetchByParentStructureId_First(
		String parentStructureId, OrderByComparator orderByComparator)
		throws SystemException {
		List<JournalStructure> list = findByParentStructureId(parentStructureId,
				0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last journal structure in the ordered set where parentStructureId = &#63;.
	 *
	 * @param parentStructureId the parent structure ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching journal structure
	 * @throws com.liferay.portlet.journal.NoSuchStructureException if a matching journal structure could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public JournalStructure findByParentStructureId_Last(
		String parentStructureId, OrderByComparator orderByComparator)
		throws NoSuchStructureException, SystemException {
		JournalStructure journalStructure = fetchByParentStructureId_Last(parentStructureId,
				orderByComparator);

		if (journalStructure != null) {
			return journalStructure;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("parentStructureId=");
		msg.append(parentStructureId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchStructureException(msg.toString());
	}

	/**
	 * Returns the last journal structure in the ordered set where parentStructureId = &#63;.
	 *
	 * @param parentStructureId the parent structure ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching journal structure, or <code>null</code> if a matching journal structure could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public JournalStructure fetchByParentStructureId_Last(
		String parentStructureId, OrderByComparator orderByComparator)
		throws SystemException {
		int count = countByParentStructureId(parentStructureId);

		List<JournalStructure> list = findByParentStructureId(parentStructureId,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the journal structures before and after the current journal structure in the ordered set where parentStructureId = &#63;.
	 *
	 * @param id the primary key of the current journal structure
	 * @param parentStructureId the parent structure ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next journal structure
	 * @throws com.liferay.portlet.journal.NoSuchStructureException if a journal structure with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public JournalStructure[] findByParentStructureId_PrevAndNext(long id,
		String parentStructureId, OrderByComparator orderByComparator)
		throws NoSuchStructureException, SystemException {
		JournalStructure journalStructure = findByPrimaryKey(id);

		Session session = null;

		try {
			session = openSession();

			JournalStructure[] array = new JournalStructureImpl[3];

			array[0] = getByParentStructureId_PrevAndNext(session,
					journalStructure, parentStructureId, orderByComparator, true);

			array[1] = journalStructure;

			array[2] = getByParentStructureId_PrevAndNext(session,
					journalStructure, parentStructureId, orderByComparator,
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

	protected JournalStructure getByParentStructureId_PrevAndNext(
		Session session, JournalStructure journalStructure,
		String parentStructureId, OrderByComparator orderByComparator,
		boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_JOURNALSTRUCTURE_WHERE);

		if (parentStructureId == null) {
			query.append(_FINDER_COLUMN_PARENTSTRUCTUREID_PARENTSTRUCTUREID_1);
		}
		else {
			if (parentStructureId.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_PARENTSTRUCTUREID_PARENTSTRUCTUREID_3);
			}
			else {
				query.append(_FINDER_COLUMN_PARENTSTRUCTUREID_PARENTSTRUCTUREID_2);
			}
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
			query.append(JournalStructureModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		if (parentStructureId != null) {
			qPos.add(parentStructureId);
		}

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(journalStructure);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<JournalStructure> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the journal structures where parentStructureId = &#63; from the database.
	 *
	 * @param parentStructureId the parent structure ID
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByParentStructureId(String parentStructureId)
		throws SystemException {
		for (JournalStructure journalStructure : findByParentStructureId(
				parentStructureId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(journalStructure);
		}
	}

	/**
	 * Returns the number of journal structures where parentStructureId = &#63;.
	 *
	 * @param parentStructureId the parent structure ID
	 * @return the number of matching journal structures
	 * @throws SystemException if a system exception occurred
	 */
	public int countByParentStructureId(String parentStructureId)
		throws SystemException {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_PARENTSTRUCTUREID;

		Object[] finderArgs = new Object[] { parentStructureId };

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs,
				this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_JOURNALSTRUCTURE_WHERE);

			if (parentStructureId == null) {
				query.append(_FINDER_COLUMN_PARENTSTRUCTUREID_PARENTSTRUCTUREID_1);
			}
			else {
				if (parentStructureId.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_PARENTSTRUCTUREID_PARENTSTRUCTUREID_3);
				}
				else {
					query.append(_FINDER_COLUMN_PARENTSTRUCTUREID_PARENTSTRUCTUREID_2);
				}
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (parentStructureId != null) {
					qPos.add(parentStructureId);
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

	private static final String _FINDER_COLUMN_PARENTSTRUCTUREID_PARENTSTRUCTUREID_1 =
		"journalStructure.parentStructureId IS NULL";
	private static final String _FINDER_COLUMN_PARENTSTRUCTUREID_PARENTSTRUCTUREID_2 =
		"journalStructure.parentStructureId = ?";
	private static final String _FINDER_COLUMN_PARENTSTRUCTUREID_PARENTSTRUCTUREID_3 =
		"(journalStructure.parentStructureId IS NULL OR journalStructure.parentStructureId = ?)";
	public static final FinderPath FINDER_PATH_FETCH_BY_G_S = new FinderPath(JournalStructureModelImpl.ENTITY_CACHE_ENABLED,
			JournalStructureModelImpl.FINDER_CACHE_ENABLED,
			JournalStructureImpl.class, FINDER_CLASS_NAME_ENTITY, "fetchByG_S",
			new String[] { Long.class.getName(), String.class.getName() },
			JournalStructureModelImpl.GROUPID_COLUMN_BITMASK |
			JournalStructureModelImpl.STRUCTUREID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_G_S = new FinderPath(JournalStructureModelImpl.ENTITY_CACHE_ENABLED,
			JournalStructureModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_S",
			new String[] { Long.class.getName(), String.class.getName() });

	/**
	 * Returns the journal structure where groupId = &#63; and structureId = &#63; or throws a {@link com.liferay.portlet.journal.NoSuchStructureException} if it could not be found.
	 *
	 * @param groupId the group ID
	 * @param structureId the structure ID
	 * @return the matching journal structure
	 * @throws com.liferay.portlet.journal.NoSuchStructureException if a matching journal structure could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public JournalStructure findByG_S(long groupId, String structureId)
		throws NoSuchStructureException, SystemException {
		JournalStructure journalStructure = fetchByG_S(groupId, structureId);

		if (journalStructure == null) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("groupId=");
			msg.append(groupId);

			msg.append(", structureId=");
			msg.append(structureId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchStructureException(msg.toString());
		}

		return journalStructure;
	}

	/**
	 * Returns the journal structure where groupId = &#63; and structureId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param groupId the group ID
	 * @param structureId the structure ID
	 * @return the matching journal structure, or <code>null</code> if a matching journal structure could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public JournalStructure fetchByG_S(long groupId, String structureId)
		throws SystemException {
		return fetchByG_S(groupId, structureId, true);
	}

	/**
	 * Returns the journal structure where groupId = &#63; and structureId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param groupId the group ID
	 * @param structureId the structure ID
	 * @param retrieveFromCache whether to use the finder cache
	 * @return the matching journal structure, or <code>null</code> if a matching journal structure could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public JournalStructure fetchByG_S(long groupId, String structureId,
		boolean retrieveFromCache) throws SystemException {
		Object[] finderArgs = new Object[] { groupId, structureId };

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_G_S,
					finderArgs, this);
		}

		if (result instanceof JournalStructure) {
			JournalStructure journalStructure = (JournalStructure)result;

			if ((groupId != journalStructure.getGroupId()) ||
					!Validator.equals(structureId,
						journalStructure.getStructureId())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_SELECT_JOURNALSTRUCTURE_WHERE);

			query.append(_FINDER_COLUMN_G_S_GROUPID_2);

			if (structureId == null) {
				query.append(_FINDER_COLUMN_G_S_STRUCTUREID_1);
			}
			else {
				if (structureId.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_G_S_STRUCTUREID_3);
				}
				else {
					query.append(_FINDER_COLUMN_G_S_STRUCTUREID_2);
				}
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (structureId != null) {
					qPos.add(structureId);
				}

				List<JournalStructure> list = q.list();

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_S,
						finderArgs, list);
				}
				else {
					JournalStructure journalStructure = list.get(0);

					result = journalStructure;

					cacheResult(journalStructure);

					if ((journalStructure.getGroupId() != groupId) ||
							(journalStructure.getStructureId() == null) ||
							!journalStructure.getStructureId()
												 .equals(structureId)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_S,
							finderArgs, journalStructure);
					}
				}
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_G_S,
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
			return (JournalStructure)result;
		}
	}

	/**
	 * Removes the journal structure where groupId = &#63; and structureId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param structureId the structure ID
	 * @return the journal structure that was removed
	 * @throws SystemException if a system exception occurred
	 */
	public JournalStructure removeByG_S(long groupId, String structureId)
		throws NoSuchStructureException, SystemException {
		JournalStructure journalStructure = findByG_S(groupId, structureId);

		return remove(journalStructure);
	}

	/**
	 * Returns the number of journal structures where groupId = &#63; and structureId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param structureId the structure ID
	 * @return the number of matching journal structures
	 * @throws SystemException if a system exception occurred
	 */
	public int countByG_S(long groupId, String structureId)
		throws SystemException {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_G_S;

		Object[] finderArgs = new Object[] { groupId, structureId };

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs,
				this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_JOURNALSTRUCTURE_WHERE);

			query.append(_FINDER_COLUMN_G_S_GROUPID_2);

			if (structureId == null) {
				query.append(_FINDER_COLUMN_G_S_STRUCTUREID_1);
			}
			else {
				if (structureId.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_G_S_STRUCTUREID_3);
				}
				else {
					query.append(_FINDER_COLUMN_G_S_STRUCTUREID_2);
				}
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (structureId != null) {
					qPos.add(structureId);
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

	private static final String _FINDER_COLUMN_G_S_GROUPID_2 = "journalStructure.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_S_STRUCTUREID_1 = "journalStructure.structureId IS NULL";
	private static final String _FINDER_COLUMN_G_S_STRUCTUREID_2 = "journalStructure.structureId = ?";
	private static final String _FINDER_COLUMN_G_S_STRUCTUREID_3 = "(journalStructure.structureId IS NULL OR journalStructure.structureId = ?)";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_G_P = new FinderPath(JournalStructureModelImpl.ENTITY_CACHE_ENABLED,
			JournalStructureModelImpl.FINDER_CACHE_ENABLED,
			JournalStructureImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByG_P",
			new String[] {
				Long.class.getName(), String.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_P = new FinderPath(JournalStructureModelImpl.ENTITY_CACHE_ENABLED,
			JournalStructureModelImpl.FINDER_CACHE_ENABLED,
			JournalStructureImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByG_P",
			new String[] { Long.class.getName(), String.class.getName() },
			JournalStructureModelImpl.GROUPID_COLUMN_BITMASK |
			JournalStructureModelImpl.PARENTSTRUCTUREID_COLUMN_BITMASK |
			JournalStructureModelImpl.STRUCTUREID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_G_P = new FinderPath(JournalStructureModelImpl.ENTITY_CACHE_ENABLED,
			JournalStructureModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_P",
			new String[] { Long.class.getName(), String.class.getName() });

	/**
	 * Returns all the journal structures where groupId = &#63; and parentStructureId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param parentStructureId the parent structure ID
	 * @return the matching journal structures
	 * @throws SystemException if a system exception occurred
	 */
	public List<JournalStructure> findByG_P(long groupId,
		String parentStructureId) throws SystemException {
		return findByG_P(groupId, parentStructureId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the journal structures where groupId = &#63; and parentStructureId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.journal.model.impl.JournalStructureModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param parentStructureId the parent structure ID
	 * @param start the lower bound of the range of journal structures
	 * @param end the upper bound of the range of journal structures (not inclusive)
	 * @return the range of matching journal structures
	 * @throws SystemException if a system exception occurred
	 */
	public List<JournalStructure> findByG_P(long groupId,
		String parentStructureId, int start, int end) throws SystemException {
		return findByG_P(groupId, parentStructureId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the journal structures where groupId = &#63; and parentStructureId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.journal.model.impl.JournalStructureModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param parentStructureId the parent structure ID
	 * @param start the lower bound of the range of journal structures
	 * @param end the upper bound of the range of journal structures (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching journal structures
	 * @throws SystemException if a system exception occurred
	 */
	public List<JournalStructure> findByG_P(long groupId,
		String parentStructureId, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
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

		List<JournalStructure> list = (List<JournalStructure>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (JournalStructure journalStructure : list) {
				if ((groupId != journalStructure.getGroupId()) ||
						!Validator.equals(parentStructureId,
							journalStructure.getParentStructureId())) {
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

			query.append(_SQL_SELECT_JOURNALSTRUCTURE_WHERE);

			query.append(_FINDER_COLUMN_G_P_GROUPID_2);

			if (parentStructureId == null) {
				query.append(_FINDER_COLUMN_G_P_PARENTSTRUCTUREID_1);
			}
			else {
				if (parentStructureId.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_G_P_PARENTSTRUCTUREID_3);
				}
				else {
					query.append(_FINDER_COLUMN_G_P_PARENTSTRUCTUREID_2);
				}
			}

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(JournalStructureModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (parentStructureId != null) {
					qPos.add(parentStructureId);
				}

				if (!pagination) {
					list = (List<JournalStructure>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = new UnmodifiableList<JournalStructure>(list);
				}
				else {
					list = (List<JournalStructure>)QueryUtil.list(q,
							getDialect(), start, end);
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
	 * Returns the first journal structure in the ordered set where groupId = &#63; and parentStructureId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param parentStructureId the parent structure ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching journal structure
	 * @throws com.liferay.portlet.journal.NoSuchStructureException if a matching journal structure could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public JournalStructure findByG_P_First(long groupId,
		String parentStructureId, OrderByComparator orderByComparator)
		throws NoSuchStructureException, SystemException {
		JournalStructure journalStructure = fetchByG_P_First(groupId,
				parentStructureId, orderByComparator);

		if (journalStructure != null) {
			return journalStructure;
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
	 * Returns the first journal structure in the ordered set where groupId = &#63; and parentStructureId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param parentStructureId the parent structure ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching journal structure, or <code>null</code> if a matching journal structure could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public JournalStructure fetchByG_P_First(long groupId,
		String parentStructureId, OrderByComparator orderByComparator)
		throws SystemException {
		List<JournalStructure> list = findByG_P(groupId, parentStructureId, 0,
				1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last journal structure in the ordered set where groupId = &#63; and parentStructureId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param parentStructureId the parent structure ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching journal structure
	 * @throws com.liferay.portlet.journal.NoSuchStructureException if a matching journal structure could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public JournalStructure findByG_P_Last(long groupId,
		String parentStructureId, OrderByComparator orderByComparator)
		throws NoSuchStructureException, SystemException {
		JournalStructure journalStructure = fetchByG_P_Last(groupId,
				parentStructureId, orderByComparator);

		if (journalStructure != null) {
			return journalStructure;
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
	 * Returns the last journal structure in the ordered set where groupId = &#63; and parentStructureId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param parentStructureId the parent structure ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching journal structure, or <code>null</code> if a matching journal structure could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public JournalStructure fetchByG_P_Last(long groupId,
		String parentStructureId, OrderByComparator orderByComparator)
		throws SystemException {
		int count = countByG_P(groupId, parentStructureId);

		List<JournalStructure> list = findByG_P(groupId, parentStructureId,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the journal structures before and after the current journal structure in the ordered set where groupId = &#63; and parentStructureId = &#63;.
	 *
	 * @param id the primary key of the current journal structure
	 * @param groupId the group ID
	 * @param parentStructureId the parent structure ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next journal structure
	 * @throws com.liferay.portlet.journal.NoSuchStructureException if a journal structure with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public JournalStructure[] findByG_P_PrevAndNext(long id, long groupId,
		String parentStructureId, OrderByComparator orderByComparator)
		throws NoSuchStructureException, SystemException {
		JournalStructure journalStructure = findByPrimaryKey(id);

		Session session = null;

		try {
			session = openSession();

			JournalStructure[] array = new JournalStructureImpl[3];

			array[0] = getByG_P_PrevAndNext(session, journalStructure, groupId,
					parentStructureId, orderByComparator, true);

			array[1] = journalStructure;

			array[2] = getByG_P_PrevAndNext(session, journalStructure, groupId,
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

	protected JournalStructure getByG_P_PrevAndNext(Session session,
		JournalStructure journalStructure, long groupId,
		String parentStructureId, OrderByComparator orderByComparator,
		boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_JOURNALSTRUCTURE_WHERE);

		query.append(_FINDER_COLUMN_G_P_GROUPID_2);

		if (parentStructureId == null) {
			query.append(_FINDER_COLUMN_G_P_PARENTSTRUCTUREID_1);
		}
		else {
			if (parentStructureId.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_G_P_PARENTSTRUCTUREID_3);
			}
			else {
				query.append(_FINDER_COLUMN_G_P_PARENTSTRUCTUREID_2);
			}
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
			query.append(JournalStructureModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		if (parentStructureId != null) {
			qPos.add(parentStructureId);
		}

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(journalStructure);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<JournalStructure> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the journal structures that the user has permission to view where groupId = &#63; and parentStructureId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param parentStructureId the parent structure ID
	 * @return the matching journal structures that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<JournalStructure> filterFindByG_P(long groupId,
		String parentStructureId) throws SystemException {
		return filterFindByG_P(groupId, parentStructureId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the journal structures that the user has permission to view where groupId = &#63; and parentStructureId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.journal.model.impl.JournalStructureModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param parentStructureId the parent structure ID
	 * @param start the lower bound of the range of journal structures
	 * @param end the upper bound of the range of journal structures (not inclusive)
	 * @return the range of matching journal structures that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<JournalStructure> filterFindByG_P(long groupId,
		String parentStructureId, int start, int end) throws SystemException {
		return filterFindByG_P(groupId, parentStructureId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the journal structures that the user has permissions to view where groupId = &#63; and parentStructureId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.journal.model.impl.JournalStructureModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param parentStructureId the parent structure ID
	 * @param start the lower bound of the range of journal structures
	 * @param end the upper bound of the range of journal structures (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching journal structures that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public List<JournalStructure> filterFindByG_P(long groupId,
		String parentStructureId, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
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
			query.append(_FILTER_SQL_SELECT_JOURNALSTRUCTURE_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_JOURNALSTRUCTURE_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_G_P_GROUPID_2);

		if (parentStructureId == null) {
			query.append(_FINDER_COLUMN_G_P_PARENTSTRUCTUREID_1);
		}
		else {
			if (parentStructureId.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_G_P_PARENTSTRUCTUREID_3);
			}
			else {
				query.append(_FINDER_COLUMN_G_P_PARENTSTRUCTUREID_2);
			}
		}

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_JOURNALSTRUCTURE_NO_INLINE_DISTINCT_WHERE_2);
		}

		if (orderByComparator != null) {
			if (getDB().isSupportsInlineDistinct()) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_TABLE,
					orderByComparator);
			}
		}
		else {
			if (getDB().isSupportsInlineDistinct()) {
				query.append(JournalStructureModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(JournalStructureModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				JournalStructure.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				q.addEntity(_FILTER_ENTITY_ALIAS, JournalStructureImpl.class);
			}
			else {
				q.addEntity(_FILTER_ENTITY_TABLE, JournalStructureImpl.class);
			}

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			if (parentStructureId != null) {
				qPos.add(parentStructureId);
			}

			return (List<JournalStructure>)QueryUtil.list(q, getDialect(),
				start, end);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * Returns the journal structures before and after the current journal structure in the ordered set of journal structures that the user has permission to view where groupId = &#63; and parentStructureId = &#63;.
	 *
	 * @param id the primary key of the current journal structure
	 * @param groupId the group ID
	 * @param parentStructureId the parent structure ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next journal structure
	 * @throws com.liferay.portlet.journal.NoSuchStructureException if a journal structure with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public JournalStructure[] filterFindByG_P_PrevAndNext(long id,
		long groupId, String parentStructureId,
		OrderByComparator orderByComparator)
		throws NoSuchStructureException, SystemException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return findByG_P_PrevAndNext(id, groupId, parentStructureId,
				orderByComparator);
		}

		JournalStructure journalStructure = findByPrimaryKey(id);

		Session session = null;

		try {
			session = openSession();

			JournalStructure[] array = new JournalStructureImpl[3];

			array[0] = filterGetByG_P_PrevAndNext(session, journalStructure,
					groupId, parentStructureId, orderByComparator, true);

			array[1] = journalStructure;

			array[2] = filterGetByG_P_PrevAndNext(session, journalStructure,
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

	protected JournalStructure filterGetByG_P_PrevAndNext(Session session,
		JournalStructure journalStructure, long groupId,
		String parentStructureId, OrderByComparator orderByComparator,
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
			query.append(_FILTER_SQL_SELECT_JOURNALSTRUCTURE_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_JOURNALSTRUCTURE_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_G_P_GROUPID_2);

		if (parentStructureId == null) {
			query.append(_FINDER_COLUMN_G_P_PARENTSTRUCTUREID_1);
		}
		else {
			if (parentStructureId.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_G_P_PARENTSTRUCTUREID_3);
			}
			else {
				query.append(_FINDER_COLUMN_G_P_PARENTSTRUCTUREID_2);
			}
		}

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_JOURNALSTRUCTURE_NO_INLINE_DISTINCT_WHERE_2);
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
				query.append(JournalStructureModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(JournalStructureModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				JournalStructure.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		SQLQuery q = session.createSQLQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		if (getDB().isSupportsInlineDistinct()) {
			q.addEntity(_FILTER_ENTITY_ALIAS, JournalStructureImpl.class);
		}
		else {
			q.addEntity(_FILTER_ENTITY_TABLE, JournalStructureImpl.class);
		}

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		if (parentStructureId != null) {
			qPos.add(parentStructureId);
		}

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(journalStructure);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<JournalStructure> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the journal structures where groupId = &#63; and parentStructureId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param parentStructureId the parent structure ID
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByG_P(long groupId, String parentStructureId)
		throws SystemException {
		for (JournalStructure journalStructure : findByG_P(groupId,
				parentStructureId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(journalStructure);
		}
	}

	/**
	 * Returns the number of journal structures where groupId = &#63; and parentStructureId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param parentStructureId the parent structure ID
	 * @return the number of matching journal structures
	 * @throws SystemException if a system exception occurred
	 */
	public int countByG_P(long groupId, String parentStructureId)
		throws SystemException {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_G_P;

		Object[] finderArgs = new Object[] { groupId, parentStructureId };

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs,
				this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_JOURNALSTRUCTURE_WHERE);

			query.append(_FINDER_COLUMN_G_P_GROUPID_2);

			if (parentStructureId == null) {
				query.append(_FINDER_COLUMN_G_P_PARENTSTRUCTUREID_1);
			}
			else {
				if (parentStructureId.equals(StringPool.BLANK)) {
					query.append(_FINDER_COLUMN_G_P_PARENTSTRUCTUREID_3);
				}
				else {
					query.append(_FINDER_COLUMN_G_P_PARENTSTRUCTUREID_2);
				}
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (parentStructureId != null) {
					qPos.add(parentStructureId);
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
	 * Returns the number of journal structures that the user has permission to view where groupId = &#63; and parentStructureId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param parentStructureId the parent structure ID
	 * @return the number of matching journal structures that the user has permission to view
	 * @throws SystemException if a system exception occurred
	 */
	public int filterCountByG_P(long groupId, String parentStructureId)
		throws SystemException {
		if (!InlineSQLHelperUtil.isEnabled(groupId)) {
			return countByG_P(groupId, parentStructureId);
		}

		StringBundler query = new StringBundler(3);

		query.append(_FILTER_SQL_COUNT_JOURNALSTRUCTURE_WHERE);

		query.append(_FINDER_COLUMN_G_P_GROUPID_2);

		if (parentStructureId == null) {
			query.append(_FINDER_COLUMN_G_P_PARENTSTRUCTUREID_1);
		}
		else {
			if (parentStructureId.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_G_P_PARENTSTRUCTUREID_3);
			}
			else {
				query.append(_FINDER_COLUMN_G_P_PARENTSTRUCTUREID_2);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				JournalStructure.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN, groupId);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME,
				com.liferay.portal.kernel.dao.orm.Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			if (parentStructureId != null) {
				qPos.add(parentStructureId);
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

	private static final String _FINDER_COLUMN_G_P_GROUPID_2 = "journalStructure.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_P_PARENTSTRUCTUREID_1 = "journalStructure.parentStructureId IS NULL";
	private static final String _FINDER_COLUMN_G_P_PARENTSTRUCTUREID_2 = "journalStructure.parentStructureId = ?";
	private static final String _FINDER_COLUMN_G_P_PARENTSTRUCTUREID_3 = "(journalStructure.parentStructureId IS NULL OR journalStructure.parentStructureId = ?)";

	/**
	 * Caches the journal structure in the entity cache if it is enabled.
	 *
	 * @param journalStructure the journal structure
	 */
	public void cacheResult(JournalStructure journalStructure) {
		EntityCacheUtil.putResult(JournalStructureModelImpl.ENTITY_CACHE_ENABLED,
			JournalStructureImpl.class, journalStructure.getPrimaryKey(),
			journalStructure);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_UUID_G,
			new Object[] {
				journalStructure.getUuid(),
				Long.valueOf(journalStructure.getGroupId())
			}, journalStructure);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_S,
			new Object[] {
				Long.valueOf(journalStructure.getGroupId()),
				
			journalStructure.getStructureId()
			}, journalStructure);

		journalStructure.resetOriginalValues();
	}

	/**
	 * Caches the journal structures in the entity cache if it is enabled.
	 *
	 * @param journalStructures the journal structures
	 */
	public void cacheResult(List<JournalStructure> journalStructures) {
		for (JournalStructure journalStructure : journalStructures) {
			if (EntityCacheUtil.getResult(
						JournalStructureModelImpl.ENTITY_CACHE_ENABLED,
						JournalStructureImpl.class,
						journalStructure.getPrimaryKey()) == null) {
				cacheResult(journalStructure);
			}
			else {
				journalStructure.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all journal structures.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		if (_HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			CacheRegistryUtil.clear(JournalStructureImpl.class.getName());
		}

		EntityCacheUtil.clearCache(JournalStructureImpl.class.getName());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the journal structure.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(JournalStructure journalStructure) {
		EntityCacheUtil.removeResult(JournalStructureModelImpl.ENTITY_CACHE_ENABLED,
			JournalStructureImpl.class, journalStructure.getPrimaryKey());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache(journalStructure);
	}

	@Override
	public void clearCache(List<JournalStructure> journalStructures) {
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (JournalStructure journalStructure : journalStructures) {
			EntityCacheUtil.removeResult(JournalStructureModelImpl.ENTITY_CACHE_ENABLED,
				JournalStructureImpl.class, journalStructure.getPrimaryKey());

			clearUniqueFindersCache(journalStructure);
		}
	}

	protected void cacheUniqueFindersCache(JournalStructure journalStructure) {
		if (journalStructure.isNew()) {
			Object[] args = new Object[] {
					journalStructure.getUuid(),
					Long.valueOf(journalStructure.getGroupId())
				};

			FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_UUID_G, args,
				Long.valueOf(1));
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_UUID_G, args,
				journalStructure);

			args = new Object[] {
					Long.valueOf(journalStructure.getGroupId()),
					
					journalStructure.getStructureId()
				};

			FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_G_S, args,
				Long.valueOf(1));
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_S, args,
				journalStructure);
		}
		else {
			JournalStructureModelImpl journalStructureModelImpl = (JournalStructureModelImpl)journalStructure;

			if ((journalStructureModelImpl.getColumnBitmask() &
					FINDER_PATH_FETCH_BY_UUID_G.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						journalStructure.getUuid(),
						Long.valueOf(journalStructure.getGroupId())
					};

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_UUID_G, args,
					Long.valueOf(1));
				FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_UUID_G, args,
					journalStructure);
			}

			if ((journalStructureModelImpl.getColumnBitmask() &
					FINDER_PATH_FETCH_BY_G_S.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						Long.valueOf(journalStructure.getGroupId()),
						
						journalStructure.getStructureId()
					};

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_G_S, args,
					Long.valueOf(1));
				FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_G_S, args,
					journalStructure);
			}
		}
	}

	protected void clearUniqueFindersCache(JournalStructure journalStructure) {
		JournalStructureModelImpl journalStructureModelImpl = (JournalStructureModelImpl)journalStructure;

		Object[] args = new Object[] {
				journalStructure.getUuid(),
				Long.valueOf(journalStructure.getGroupId())
			};

		FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_UUID_G, args);
		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_UUID_G, args);

		if ((journalStructureModelImpl.getColumnBitmask() &
				FINDER_PATH_FETCH_BY_UUID_G.getColumnBitmask()) != 0) {
			args = new Object[] {
					journalStructureModelImpl.getOriginalUuid(),
					Long.valueOf(journalStructureModelImpl.getOriginalGroupId())
				};

			FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_UUID_G, args);
			FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_UUID_G, args);
		}

		args = new Object[] {
				Long.valueOf(journalStructure.getGroupId()),
				
				journalStructure.getStructureId()
			};

		FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_G_S, args);
		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_G_S, args);

		if ((journalStructureModelImpl.getColumnBitmask() &
				FINDER_PATH_FETCH_BY_G_S.getColumnBitmask()) != 0) {
			args = new Object[] {
					Long.valueOf(journalStructureModelImpl.getOriginalGroupId()),
					
					journalStructureModelImpl.getOriginalStructureId()
				};

			FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_G_S, args);
			FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_G_S, args);
		}
	}

	/**
	 * Creates a new journal structure with the primary key. Does not add the journal structure to the database.
	 *
	 * @param id the primary key for the new journal structure
	 * @return the new journal structure
	 */
	public JournalStructure create(long id) {
		JournalStructure journalStructure = new JournalStructureImpl();

		journalStructure.setNew(true);
		journalStructure.setPrimaryKey(id);

		String uuid = PortalUUIDUtil.generate();

		journalStructure.setUuid(uuid);

		return journalStructure;
	}

	/**
	 * Removes the journal structure with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param id the primary key of the journal structure
	 * @return the journal structure that was removed
	 * @throws com.liferay.portlet.journal.NoSuchStructureException if a journal structure with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public JournalStructure remove(long id)
		throws NoSuchStructureException, SystemException {
		return remove(Long.valueOf(id));
	}

	/**
	 * Removes the journal structure with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the journal structure
	 * @return the journal structure that was removed
	 * @throws com.liferay.portlet.journal.NoSuchStructureException if a journal structure with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public JournalStructure remove(Serializable primaryKey)
		throws NoSuchStructureException, SystemException {
		Session session = null;

		try {
			session = openSession();

			JournalStructure journalStructure = (JournalStructure)session.get(JournalStructureImpl.class,
					primaryKey);

			if (journalStructure == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchStructureException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(journalStructure);
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
	protected JournalStructure removeImpl(JournalStructure journalStructure)
		throws SystemException {
		journalStructure = toUnwrappedModel(journalStructure);

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(journalStructure)) {
				journalStructure = (JournalStructure)session.get(JournalStructureImpl.class,
						journalStructure.getPrimaryKeyObj());
			}

			if (journalStructure != null) {
				session.delete(journalStructure);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (journalStructure != null) {
			clearCache(journalStructure);
		}

		return journalStructure;
	}

	@Override
	public JournalStructure updateImpl(
		com.liferay.portlet.journal.model.JournalStructure journalStructure)
		throws SystemException {
		journalStructure = toUnwrappedModel(journalStructure);

		boolean isNew = journalStructure.isNew();

		JournalStructureModelImpl journalStructureModelImpl = (JournalStructureModelImpl)journalStructure;

		if (Validator.isNull(journalStructure.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			journalStructure.setUuid(uuid);
		}

		Session session = null;

		try {
			session = openSession();

			if (journalStructure.isNew()) {
				session.save(journalStructure);

				journalStructure.setNew(false);
			}
			else {
				session.merge(journalStructure);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (isNew || !JournalStructureModelImpl.COLUMN_BITMASK_ENABLED) {
			FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}

		else {
			if ((journalStructureModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						journalStructureModelImpl.getOriginalUuid()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_UUID, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID,
					args);

				args = new Object[] { journalStructureModelImpl.getUuid() };

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_UUID, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID,
					args);
			}

			if ((journalStructureModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						journalStructureModelImpl.getOriginalUuid(),
						Long.valueOf(journalStructureModelImpl.getOriginalCompanyId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_UUID_C, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C,
					args);

				args = new Object[] {
						journalStructureModelImpl.getUuid(),
						Long.valueOf(journalStructureModelImpl.getCompanyId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_UUID_C, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C,
					args);
			}

			if ((journalStructureModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						Long.valueOf(journalStructureModelImpl.getOriginalGroupId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_GROUPID, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID,
					args);

				args = new Object[] {
						Long.valueOf(journalStructureModelImpl.getGroupId())
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_GROUPID, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID,
					args);
			}

			if ((journalStructureModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_STRUCTUREID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						journalStructureModelImpl.getOriginalStructureId()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_STRUCTUREID,
					args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_STRUCTUREID,
					args);

				args = new Object[] { journalStructureModelImpl.getStructureId() };

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_STRUCTUREID,
					args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_STRUCTUREID,
					args);
			}

			if ((journalStructureModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_PARENTSTRUCTUREID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						journalStructureModelImpl.getOriginalParentStructureId()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_PARENTSTRUCTUREID,
					args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_PARENTSTRUCTUREID,
					args);

				args = new Object[] {
						journalStructureModelImpl.getParentStructureId()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_PARENTSTRUCTUREID,
					args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_PARENTSTRUCTUREID,
					args);
			}

			if ((journalStructureModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_P.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						Long.valueOf(journalStructureModelImpl.getOriginalGroupId()),
						
						journalStructureModelImpl.getOriginalParentStructureId()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_G_P, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_P,
					args);

				args = new Object[] {
						Long.valueOf(journalStructureModelImpl.getGroupId()),
						
						journalStructureModelImpl.getParentStructureId()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_G_P, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_P,
					args);
			}
		}

		EntityCacheUtil.putResult(JournalStructureModelImpl.ENTITY_CACHE_ENABLED,
			JournalStructureImpl.class, journalStructure.getPrimaryKey(),
			journalStructure);

		clearUniqueFindersCache(journalStructure);
		cacheUniqueFindersCache(journalStructure);

		return journalStructure;
	}

	protected JournalStructure toUnwrappedModel(
		JournalStructure journalStructure) {
		if (journalStructure instanceof JournalStructureImpl) {
			return journalStructure;
		}

		JournalStructureImpl journalStructureImpl = new JournalStructureImpl();

		journalStructureImpl.setNew(journalStructure.isNew());
		journalStructureImpl.setPrimaryKey(journalStructure.getPrimaryKey());

		journalStructureImpl.setUuid(journalStructure.getUuid());
		journalStructureImpl.setId(journalStructure.getId());
		journalStructureImpl.setGroupId(journalStructure.getGroupId());
		journalStructureImpl.setCompanyId(journalStructure.getCompanyId());
		journalStructureImpl.setUserId(journalStructure.getUserId());
		journalStructureImpl.setUserName(journalStructure.getUserName());
		journalStructureImpl.setCreateDate(journalStructure.getCreateDate());
		journalStructureImpl.setModifiedDate(journalStructure.getModifiedDate());
		journalStructureImpl.setStructureId(journalStructure.getStructureId());
		journalStructureImpl.setParentStructureId(journalStructure.getParentStructureId());
		journalStructureImpl.setName(journalStructure.getName());
		journalStructureImpl.setDescription(journalStructure.getDescription());
		journalStructureImpl.setXsd(journalStructure.getXsd());

		return journalStructureImpl;
	}

	/**
	 * Returns the journal structure with the primary key or throws a {@link com.liferay.portal.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the journal structure
	 * @return the journal structure
	 * @throws com.liferay.portal.NoSuchModelException if a journal structure with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public JournalStructure findByPrimaryKey(Serializable primaryKey)
		throws NoSuchModelException, SystemException {
		return findByPrimaryKey(((Long)primaryKey).longValue());
	}

	/**
	 * Returns the journal structure with the primary key or throws a {@link com.liferay.portlet.journal.NoSuchStructureException} if it could not be found.
	 *
	 * @param id the primary key of the journal structure
	 * @return the journal structure
	 * @throws com.liferay.portlet.journal.NoSuchStructureException if a journal structure with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public JournalStructure findByPrimaryKey(long id)
		throws NoSuchStructureException, SystemException {
		JournalStructure journalStructure = fetchByPrimaryKey(id);

		if (journalStructure == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + id);
			}

			throw new NoSuchStructureException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				id);
		}

		return journalStructure;
	}

	/**
	 * Returns the journal structure with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the journal structure
	 * @return the journal structure, or <code>null</code> if a journal structure with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public JournalStructure fetchByPrimaryKey(Serializable primaryKey)
		throws SystemException {
		return fetchByPrimaryKey(((Long)primaryKey).longValue());
	}

	/**
	 * Returns the journal structure with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param id the primary key of the journal structure
	 * @return the journal structure, or <code>null</code> if a journal structure with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public JournalStructure fetchByPrimaryKey(long id)
		throws SystemException {
		JournalStructure journalStructure = (JournalStructure)EntityCacheUtil.getResult(JournalStructureModelImpl.ENTITY_CACHE_ENABLED,
				JournalStructureImpl.class, id);

		if (journalStructure == _nullJournalStructure) {
			return null;
		}

		if (journalStructure == null) {
			Session session = null;

			try {
				session = openSession();

				journalStructure = (JournalStructure)session.get(JournalStructureImpl.class,
						Long.valueOf(id));

				if (journalStructure != null) {
					cacheResult(journalStructure);
				}
				else {
					EntityCacheUtil.putResult(JournalStructureModelImpl.ENTITY_CACHE_ENABLED,
						JournalStructureImpl.class, id, _nullJournalStructure);
				}
			}
			catch (Exception e) {
				EntityCacheUtil.removeResult(JournalStructureModelImpl.ENTITY_CACHE_ENABLED,
					JournalStructureImpl.class, id);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return journalStructure;
	}

	/**
	 * Returns all the journal structures.
	 *
	 * @return the journal structures
	 * @throws SystemException if a system exception occurred
	 */
	public List<JournalStructure> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the journal structures.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.journal.model.impl.JournalStructureModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of journal structures
	 * @param end the upper bound of the range of journal structures (not inclusive)
	 * @return the range of journal structures
	 * @throws SystemException if a system exception occurred
	 */
	public List<JournalStructure> findAll(int start, int end)
		throws SystemException {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the journal structures.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.journal.model.impl.JournalStructureModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of journal structures
	 * @param end the upper bound of the range of journal structures (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of journal structures
	 * @throws SystemException if a system exception occurred
	 */
	public List<JournalStructure> findAll(int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
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

		List<JournalStructure> list = (List<JournalStructure>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 3));

				query.append(_SQL_SELECT_JOURNALSTRUCTURE);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_JOURNALSTRUCTURE;

				if (pagination) {
					sql = sql.concat(JournalStructureModelImpl.ORDER_BY_JPQL);
				}
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (!pagination) {
					list = (List<JournalStructure>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = new UnmodifiableList<JournalStructure>(list);
				}
				else {
					list = (List<JournalStructure>)QueryUtil.list(q,
							getDialect(), start, end);
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
	 * Removes all the journal structures from the database.
	 *
	 * @throws SystemException if a system exception occurred
	 */
	public void removeAll() throws SystemException {
		for (JournalStructure journalStructure : findAll()) {
			remove(journalStructure);
		}
	}

	/**
	 * Returns the number of journal structures.
	 *
	 * @return the number of journal structures
	 * @throws SystemException if a system exception occurred
	 */
	public int countAll() throws SystemException {
		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_JOURNALSTRUCTURE);

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
	 * Initializes the journal structure persistence.
	 */
	public void afterPropertiesSet() {
		String[] listenerClassNames = StringUtil.split(GetterUtil.getString(
					com.liferay.portal.util.PropsUtil.get(
						"value.object.listener.com.liferay.portlet.journal.model.JournalStructure")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<JournalStructure>> listenersList = new ArrayList<ModelListener<JournalStructure>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<JournalStructure>)InstanceFactory.newInstance(
							listenerClassName));
				}

				listeners = listenersList.toArray(new ModelListener[listenersList.size()]);
			}
			catch (Exception e) {
				_log.error(e);
			}
		}
	}

	public void destroy() {
		EntityCacheUtil.removeCache(JournalStructureImpl.class.getName());
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	private static final String _SQL_SELECT_JOURNALSTRUCTURE = "SELECT journalStructure FROM JournalStructure journalStructure";
	private static final String _SQL_SELECT_JOURNALSTRUCTURE_WHERE = "SELECT journalStructure FROM JournalStructure journalStructure WHERE ";
	private static final String _SQL_COUNT_JOURNALSTRUCTURE = "SELECT COUNT(journalStructure) FROM JournalStructure journalStructure";
	private static final String _SQL_COUNT_JOURNALSTRUCTURE_WHERE = "SELECT COUNT(journalStructure) FROM JournalStructure journalStructure WHERE ";

	private static String _removeConjunction(String sql) {
		int pos = sql.indexOf(" AND ");

		if (pos != -1) {
			sql = sql.substring(0, pos);
		}

		return sql;
	}

	private static final String _FILTER_ENTITY_TABLE_FILTER_PK_COLUMN = "journalStructure.id_";
	private static final String _FILTER_SQL_SELECT_JOURNALSTRUCTURE_WHERE = "SELECT DISTINCT {journalStructure.*} FROM JournalStructure journalStructure WHERE ";
	private static final String _FILTER_SQL_SELECT_JOURNALSTRUCTURE_NO_INLINE_DISTINCT_WHERE_1 =
		"SELECT {JournalStructure.*} FROM (SELECT DISTINCT journalStructure.id_ FROM JournalStructure journalStructure WHERE ";
	private static final String _FILTER_SQL_SELECT_JOURNALSTRUCTURE_NO_INLINE_DISTINCT_WHERE_2 =
		") TEMP_TABLE INNER JOIN JournalStructure ON TEMP_TABLE.id_ = JournalStructure.id_";
	private static final String _FILTER_SQL_COUNT_JOURNALSTRUCTURE_WHERE = "SELECT COUNT(DISTINCT journalStructure.id_) AS COUNT_VALUE FROM JournalStructure journalStructure WHERE ";
	private static final String _FILTER_ENTITY_ALIAS = "journalStructure";
	private static final String _FILTER_ENTITY_TABLE = "JournalStructure";
	private static final String _ORDER_BY_ENTITY_ALIAS = "journalStructure.";
	private static final String _ORDER_BY_ENTITY_TABLE = "JournalStructure.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No JournalStructure exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No JournalStructure exists with the key {";
	private static final boolean _HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE = com.liferay.portal.util.PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE;
	private static Log _log = LogFactoryUtil.getLog(JournalStructurePersistenceImpl.class);
	private static JournalStructure _nullJournalStructure = new JournalStructureImpl() {
			@Override
			public Object clone() {
				return this;
			}

			@Override
			public CacheModel<JournalStructure> toCacheModel() {
				return _nullJournalStructureCacheModel;
			}
		};

	private static CacheModel<JournalStructure> _nullJournalStructureCacheModel = new CacheModel<JournalStructure>() {
			public JournalStructure toEntityModel() {
				return _nullJournalStructure;
			}
		};
}