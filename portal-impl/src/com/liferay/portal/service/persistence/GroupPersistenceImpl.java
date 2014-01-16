/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.service.persistence;

import com.liferay.portal.NoSuchGroupException;
import com.liferay.portal.kernel.bean.BeanReference;
import com.liferay.portal.kernel.cache.CacheRegistryUtil;
import com.liferay.portal.kernel.dao.orm.EntityCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.InstanceFactory;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.UnmodifiableList;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;
import com.liferay.portal.model.CacheModel;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.model.impl.GroupImpl;
import com.liferay.portal.model.impl.GroupModelImpl;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.service.persistence.impl.TableMapper;
import com.liferay.portal.service.persistence.impl.TableMapperFactory;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * The persistence implementation for the group service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see GroupPersistence
 * @see GroupUtil
 * @generated
 */
public class GroupPersistenceImpl extends BasePersistenceImpl<Group>
	implements GroupPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link GroupUtil} to access the group persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = GroupImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(GroupModelImpl.ENTITY_CACHE_ENABLED,
			GroupModelImpl.FINDER_CACHE_ENABLED, GroupImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(GroupModelImpl.ENTITY_CACHE_ENABLED,
			GroupModelImpl.FINDER_CACHE_ENABLED, GroupImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(GroupModelImpl.ENTITY_CACHE_ENABLED,
			GroupModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_UUID = new FinderPath(GroupModelImpl.ENTITY_CACHE_ENABLED,
			GroupModelImpl.FINDER_CACHE_ENABLED, GroupImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUuid",
			new String[] {
				String.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID = new FinderPath(GroupModelImpl.ENTITY_CACHE_ENABLED,
			GroupModelImpl.FINDER_CACHE_ENABLED, GroupImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid",
			new String[] { String.class.getName() },
			GroupModelImpl.UUID_COLUMN_BITMASK |
			GroupModelImpl.NAME_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID = new FinderPath(GroupModelImpl.ENTITY_CACHE_ENABLED,
			GroupModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUuid",
			new String[] { String.class.getName() });

	/**
	 * Returns all the groups where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching groups
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<Group> findByUuid(String uuid) throws SystemException {
		return findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the groups where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.GroupModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of groups
	 * @param end the upper bound of the range of groups (not inclusive)
	 * @return the range of matching groups
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<Group> findByUuid(String uuid, int start, int end)
		throws SystemException {
		return findByUuid(uuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the groups where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.GroupModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of groups
	 * @param end the upper bound of the range of groups (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching groups
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<Group> findByUuid(String uuid, int start, int end,
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

		List<Group> list = (List<Group>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (Group group : list) {
				if (!Validator.equals(uuid, group.getUuid())) {
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

			query.append(_SQL_SELECT_GROUP__WHERE);

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
				query.append(GroupModelImpl.ORDER_BY_JPQL);
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
					list = (List<Group>)QueryUtil.list(q, getDialect(), start,
							end, false);

					Collections.sort(list);

					list = new UnmodifiableList<Group>(list);
				}
				else {
					list = (List<Group>)QueryUtil.list(q, getDialect(), start,
							end);
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
	 * Returns the first group in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching group
	 * @throws com.liferay.portal.NoSuchGroupException if a matching group could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public Group findByUuid_First(String uuid,
		OrderByComparator orderByComparator)
		throws NoSuchGroupException, SystemException {
		Group group = fetchByUuid_First(uuid, orderByComparator);

		if (group != null) {
			return group;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchGroupException(msg.toString());
	}

	/**
	 * Returns the first group in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching group, or <code>null</code> if a matching group could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public Group fetchByUuid_First(String uuid,
		OrderByComparator orderByComparator) throws SystemException {
		List<Group> list = findByUuid(uuid, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last group in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching group
	 * @throws com.liferay.portal.NoSuchGroupException if a matching group could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public Group findByUuid_Last(String uuid,
		OrderByComparator orderByComparator)
		throws NoSuchGroupException, SystemException {
		Group group = fetchByUuid_Last(uuid, orderByComparator);

		if (group != null) {
			return group;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchGroupException(msg.toString());
	}

	/**
	 * Returns the last group in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching group, or <code>null</code> if a matching group could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public Group fetchByUuid_Last(String uuid,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByUuid(uuid);

		if (count == 0) {
			return null;
		}

		List<Group> list = findByUuid(uuid, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the groups before and after the current group in the ordered set where uuid = &#63;.
	 *
	 * @param groupId the primary key of the current group
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next group
	 * @throws com.liferay.portal.NoSuchGroupException if a group with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public Group[] findByUuid_PrevAndNext(long groupId, String uuid,
		OrderByComparator orderByComparator)
		throws NoSuchGroupException, SystemException {
		Group group = findByPrimaryKey(groupId);

		Session session = null;

		try {
			session = openSession();

			Group[] array = new GroupImpl[3];

			array[0] = getByUuid_PrevAndNext(session, group, uuid,
					orderByComparator, true);

			array[1] = group;

			array[2] = getByUuid_PrevAndNext(session, group, uuid,
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

	protected Group getByUuid_PrevAndNext(Session session, Group group,
		String uuid, OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_GROUP__WHERE);

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
			query.append(GroupModelImpl.ORDER_BY_JPQL);
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
			Object[] values = orderByComparator.getOrderByConditionValues(group);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<Group> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the groups where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public void removeByUuid(String uuid) throws SystemException {
		for (Group group : findByUuid(uuid, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS, null)) {
			remove(group);
		}
	}

	/**
	 * Returns the number of groups where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching groups
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public int countByUuid(String uuid) throws SystemException {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_UUID;

		Object[] finderArgs = new Object[] { uuid };

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs,
				this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_GROUP__WHERE);

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

	private static final String _FINDER_COLUMN_UUID_UUID_1 = "group_.uuid IS NULL";
	private static final String _FINDER_COLUMN_UUID_UUID_2 = "group_.uuid = ?";
	private static final String _FINDER_COLUMN_UUID_UUID_3 = "(group_.uuid IS NULL OR group_.uuid = '')";
	public static final FinderPath FINDER_PATH_FETCH_BY_UUID_G = new FinderPath(GroupModelImpl.ENTITY_CACHE_ENABLED,
			GroupModelImpl.FINDER_CACHE_ENABLED, GroupImpl.class,
			FINDER_CLASS_NAME_ENTITY, "fetchByUUID_G",
			new String[] { String.class.getName(), Long.class.getName() },
			GroupModelImpl.UUID_COLUMN_BITMASK |
			GroupModelImpl.GROUPID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID_G = new FinderPath(GroupModelImpl.ENTITY_CACHE_ENABLED,
			GroupModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUUID_G",
			new String[] { String.class.getName(), Long.class.getName() });

	/**
	 * Returns the group where uuid = &#63; and groupId = &#63; or throws a {@link com.liferay.portal.NoSuchGroupException} if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching group
	 * @throws com.liferay.portal.NoSuchGroupException if a matching group could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public Group findByUUID_G(String uuid, long groupId)
		throws NoSuchGroupException, SystemException {
		Group group = fetchByUUID_G(uuid, groupId);

		if (group == null) {
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

			throw new NoSuchGroupException(msg.toString());
		}

		return group;
	}

	/**
	 * Returns the group where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching group, or <code>null</code> if a matching group could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public Group fetchByUUID_G(String uuid, long groupId)
		throws SystemException {
		return fetchByUUID_G(uuid, groupId, true);
	}

	/**
	 * Returns the group where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param retrieveFromCache whether to use the finder cache
	 * @return the matching group, or <code>null</code> if a matching group could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public Group fetchByUUID_G(String uuid, long groupId,
		boolean retrieveFromCache) throws SystemException {
		Object[] finderArgs = new Object[] { uuid, groupId };

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_UUID_G,
					finderArgs, this);
		}

		if (result instanceof Group) {
			Group group = (Group)result;

			if (!Validator.equals(uuid, group.getUuid()) ||
					(groupId != group.getGroupId())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_SELECT_GROUP__WHERE);

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

				List<Group> list = q.list();

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_UUID_G,
						finderArgs, list);
				}
				else {
					Group group = list.get(0);

					result = group;

					cacheResult(group);

					if ((group.getUuid() == null) ||
							!group.getUuid().equals(uuid) ||
							(group.getGroupId() != groupId)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_UUID_G,
							finderArgs, group);
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
			return (Group)result;
		}
	}

	/**
	 * Removes the group where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the group that was removed
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public Group removeByUUID_G(String uuid, long groupId)
		throws NoSuchGroupException, SystemException {
		Group group = findByUUID_G(uuid, groupId);

		return remove(group);
	}

	/**
	 * Returns the number of groups where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching groups
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public int countByUUID_G(String uuid, long groupId)
		throws SystemException {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_UUID_G;

		Object[] finderArgs = new Object[] { uuid, groupId };

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs,
				this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_GROUP__WHERE);

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

	private static final String _FINDER_COLUMN_UUID_G_UUID_1 = "group_.uuid IS NULL AND ";
	private static final String _FINDER_COLUMN_UUID_G_UUID_2 = "group_.uuid = ? AND ";
	private static final String _FINDER_COLUMN_UUID_G_UUID_3 = "(group_.uuid IS NULL OR group_.uuid = '') AND ";
	private static final String _FINDER_COLUMN_UUID_G_GROUPID_2 = "group_.groupId = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_UUID_C = new FinderPath(GroupModelImpl.ENTITY_CACHE_ENABLED,
			GroupModelImpl.FINDER_CACHE_ENABLED, GroupImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUuid_C",
			new String[] {
				String.class.getName(), Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C =
		new FinderPath(GroupModelImpl.ENTITY_CACHE_ENABLED,
			GroupModelImpl.FINDER_CACHE_ENABLED, GroupImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid_C",
			new String[] { String.class.getName(), Long.class.getName() },
			GroupModelImpl.UUID_COLUMN_BITMASK |
			GroupModelImpl.COMPANYID_COLUMN_BITMASK |
			GroupModelImpl.NAME_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID_C = new FinderPath(GroupModelImpl.ENTITY_CACHE_ENABLED,
			GroupModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUuid_C",
			new String[] { String.class.getName(), Long.class.getName() });

	/**
	 * Returns all the groups where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching groups
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<Group> findByUuid_C(String uuid, long companyId)
		throws SystemException {
		return findByUuid_C(uuid, companyId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the groups where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.GroupModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of groups
	 * @param end the upper bound of the range of groups (not inclusive)
	 * @return the range of matching groups
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<Group> findByUuid_C(String uuid, long companyId, int start,
		int end) throws SystemException {
		return findByUuid_C(uuid, companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the groups where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.GroupModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of groups
	 * @param end the upper bound of the range of groups (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching groups
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<Group> findByUuid_C(String uuid, long companyId, int start,
		int end, OrderByComparator orderByComparator) throws SystemException {
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

		List<Group> list = (List<Group>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (Group group : list) {
				if (!Validator.equals(uuid, group.getUuid()) ||
						(companyId != group.getCompanyId())) {
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

			query.append(_SQL_SELECT_GROUP__WHERE);

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
				query.append(GroupModelImpl.ORDER_BY_JPQL);
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
					list = (List<Group>)QueryUtil.list(q, getDialect(), start,
							end, false);

					Collections.sort(list);

					list = new UnmodifiableList<Group>(list);
				}
				else {
					list = (List<Group>)QueryUtil.list(q, getDialect(), start,
							end);
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
	 * Returns the first group in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching group
	 * @throws com.liferay.portal.NoSuchGroupException if a matching group could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public Group findByUuid_C_First(String uuid, long companyId,
		OrderByComparator orderByComparator)
		throws NoSuchGroupException, SystemException {
		Group group = fetchByUuid_C_First(uuid, companyId, orderByComparator);

		if (group != null) {
			return group;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(", companyId=");
		msg.append(companyId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchGroupException(msg.toString());
	}

	/**
	 * Returns the first group in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching group, or <code>null</code> if a matching group could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public Group fetchByUuid_C_First(String uuid, long companyId,
		OrderByComparator orderByComparator) throws SystemException {
		List<Group> list = findByUuid_C(uuid, companyId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last group in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching group
	 * @throws com.liferay.portal.NoSuchGroupException if a matching group could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public Group findByUuid_C_Last(String uuid, long companyId,
		OrderByComparator orderByComparator)
		throws NoSuchGroupException, SystemException {
		Group group = fetchByUuid_C_Last(uuid, companyId, orderByComparator);

		if (group != null) {
			return group;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(", companyId=");
		msg.append(companyId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchGroupException(msg.toString());
	}

	/**
	 * Returns the last group in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching group, or <code>null</code> if a matching group could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public Group fetchByUuid_C_Last(String uuid, long companyId,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByUuid_C(uuid, companyId);

		if (count == 0) {
			return null;
		}

		List<Group> list = findByUuid_C(uuid, companyId, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the groups before and after the current group in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param groupId the primary key of the current group
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next group
	 * @throws com.liferay.portal.NoSuchGroupException if a group with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public Group[] findByUuid_C_PrevAndNext(long groupId, String uuid,
		long companyId, OrderByComparator orderByComparator)
		throws NoSuchGroupException, SystemException {
		Group group = findByPrimaryKey(groupId);

		Session session = null;

		try {
			session = openSession();

			Group[] array = new GroupImpl[3];

			array[0] = getByUuid_C_PrevAndNext(session, group, uuid, companyId,
					orderByComparator, true);

			array[1] = group;

			array[2] = getByUuid_C_PrevAndNext(session, group, uuid, companyId,
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

	protected Group getByUuid_C_PrevAndNext(Session session, Group group,
		String uuid, long companyId, OrderByComparator orderByComparator,
		boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_GROUP__WHERE);

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
			query.append(GroupModelImpl.ORDER_BY_JPQL);
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
			Object[] values = orderByComparator.getOrderByConditionValues(group);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<Group> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the groups where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public void removeByUuid_C(String uuid, long companyId)
		throws SystemException {
		for (Group group : findByUuid_C(uuid, companyId, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS, null)) {
			remove(group);
		}
	}

	/**
	 * Returns the number of groups where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching groups
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public int countByUuid_C(String uuid, long companyId)
		throws SystemException {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_UUID_C;

		Object[] finderArgs = new Object[] { uuid, companyId };

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs,
				this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_GROUP__WHERE);

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

	private static final String _FINDER_COLUMN_UUID_C_UUID_1 = "group_.uuid IS NULL AND ";
	private static final String _FINDER_COLUMN_UUID_C_UUID_2 = "group_.uuid = ? AND ";
	private static final String _FINDER_COLUMN_UUID_C_UUID_3 = "(group_.uuid IS NULL OR group_.uuid = '') AND ";
	private static final String _FINDER_COLUMN_UUID_C_COMPANYID_2 = "group_.companyId = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_COMPANYID =
		new FinderPath(GroupModelImpl.ENTITY_CACHE_ENABLED,
			GroupModelImpl.FINDER_CACHE_ENABLED, GroupImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByCompanyId",
			new String[] {
				Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID =
		new FinderPath(GroupModelImpl.ENTITY_CACHE_ENABLED,
			GroupModelImpl.FINDER_CACHE_ENABLED, GroupImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByCompanyId",
			new String[] { Long.class.getName() },
			GroupModelImpl.COMPANYID_COLUMN_BITMASK |
			GroupModelImpl.NAME_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_COMPANYID = new FinderPath(GroupModelImpl.ENTITY_CACHE_ENABLED,
			GroupModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByCompanyId",
			new String[] { Long.class.getName() });

	/**
	 * Returns all the groups where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching groups
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<Group> findByCompanyId(long companyId)
		throws SystemException {
		return findByCompanyId(companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the groups where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.GroupModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of groups
	 * @param end the upper bound of the range of groups (not inclusive)
	 * @return the range of matching groups
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<Group> findByCompanyId(long companyId, int start, int end)
		throws SystemException {
		return findByCompanyId(companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the groups where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.GroupModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of groups
	 * @param end the upper bound of the range of groups (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching groups
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<Group> findByCompanyId(long companyId, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
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

		List<Group> list = (List<Group>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (Group group : list) {
				if ((companyId != group.getCompanyId())) {
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

			query.append(_SQL_SELECT_GROUP__WHERE);

			query.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(GroupModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				if (!pagination) {
					list = (List<Group>)QueryUtil.list(q, getDialect(), start,
							end, false);

					Collections.sort(list);

					list = new UnmodifiableList<Group>(list);
				}
				else {
					list = (List<Group>)QueryUtil.list(q, getDialect(), start,
							end);
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
	 * Returns the first group in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching group
	 * @throws com.liferay.portal.NoSuchGroupException if a matching group could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public Group findByCompanyId_First(long companyId,
		OrderByComparator orderByComparator)
		throws NoSuchGroupException, SystemException {
		Group group = fetchByCompanyId_First(companyId, orderByComparator);

		if (group != null) {
			return group;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("companyId=");
		msg.append(companyId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchGroupException(msg.toString());
	}

	/**
	 * Returns the first group in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching group, or <code>null</code> if a matching group could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public Group fetchByCompanyId_First(long companyId,
		OrderByComparator orderByComparator) throws SystemException {
		List<Group> list = findByCompanyId(companyId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last group in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching group
	 * @throws com.liferay.portal.NoSuchGroupException if a matching group could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public Group findByCompanyId_Last(long companyId,
		OrderByComparator orderByComparator)
		throws NoSuchGroupException, SystemException {
		Group group = fetchByCompanyId_Last(companyId, orderByComparator);

		if (group != null) {
			return group;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("companyId=");
		msg.append(companyId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchGroupException(msg.toString());
	}

	/**
	 * Returns the last group in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching group, or <code>null</code> if a matching group could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public Group fetchByCompanyId_Last(long companyId,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByCompanyId(companyId);

		if (count == 0) {
			return null;
		}

		List<Group> list = findByCompanyId(companyId, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the groups before and after the current group in the ordered set where companyId = &#63;.
	 *
	 * @param groupId the primary key of the current group
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next group
	 * @throws com.liferay.portal.NoSuchGroupException if a group with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public Group[] findByCompanyId_PrevAndNext(long groupId, long companyId,
		OrderByComparator orderByComparator)
		throws NoSuchGroupException, SystemException {
		Group group = findByPrimaryKey(groupId);

		Session session = null;

		try {
			session = openSession();

			Group[] array = new GroupImpl[3];

			array[0] = getByCompanyId_PrevAndNext(session, group, companyId,
					orderByComparator, true);

			array[1] = group;

			array[2] = getByCompanyId_PrevAndNext(session, group, companyId,
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

	protected Group getByCompanyId_PrevAndNext(Session session, Group group,
		long companyId, OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_GROUP__WHERE);

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
			query.append(GroupModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(companyId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(group);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<Group> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the groups where companyId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public void removeByCompanyId(long companyId) throws SystemException {
		for (Group group : findByCompanyId(companyId, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS, null)) {
			remove(group);
		}
	}

	/**
	 * Returns the number of groups where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching groups
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public int countByCompanyId(long companyId) throws SystemException {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_COMPANYID;

		Object[] finderArgs = new Object[] { companyId };

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs,
				this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_GROUP__WHERE);

			query.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

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

	private static final String _FINDER_COLUMN_COMPANYID_COMPANYID_2 = "group_.companyId = ?";
	public static final FinderPath FINDER_PATH_FETCH_BY_LIVEGROUPID = new FinderPath(GroupModelImpl.ENTITY_CACHE_ENABLED,
			GroupModelImpl.FINDER_CACHE_ENABLED, GroupImpl.class,
			FINDER_CLASS_NAME_ENTITY, "fetchByLiveGroupId",
			new String[] { Long.class.getName() },
			GroupModelImpl.LIVEGROUPID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_LIVEGROUPID = new FinderPath(GroupModelImpl.ENTITY_CACHE_ENABLED,
			GroupModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByLiveGroupId",
			new String[] { Long.class.getName() });

	/**
	 * Returns the group where liveGroupId = &#63; or throws a {@link com.liferay.portal.NoSuchGroupException} if it could not be found.
	 *
	 * @param liveGroupId the live group ID
	 * @return the matching group
	 * @throws com.liferay.portal.NoSuchGroupException if a matching group could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public Group findByLiveGroupId(long liveGroupId)
		throws NoSuchGroupException, SystemException {
		Group group = fetchByLiveGroupId(liveGroupId);

		if (group == null) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("liveGroupId=");
			msg.append(liveGroupId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchGroupException(msg.toString());
		}

		return group;
	}

	/**
	 * Returns the group where liveGroupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param liveGroupId the live group ID
	 * @return the matching group, or <code>null</code> if a matching group could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public Group fetchByLiveGroupId(long liveGroupId) throws SystemException {
		return fetchByLiveGroupId(liveGroupId, true);
	}

	/**
	 * Returns the group where liveGroupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param liveGroupId the live group ID
	 * @param retrieveFromCache whether to use the finder cache
	 * @return the matching group, or <code>null</code> if a matching group could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public Group fetchByLiveGroupId(long liveGroupId, boolean retrieveFromCache)
		throws SystemException {
		Object[] finderArgs = new Object[] { liveGroupId };

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_LIVEGROUPID,
					finderArgs, this);
		}

		if (result instanceof Group) {
			Group group = (Group)result;

			if ((liveGroupId != group.getLiveGroupId())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_SELECT_GROUP__WHERE);

			query.append(_FINDER_COLUMN_LIVEGROUPID_LIVEGROUPID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(liveGroupId);

				List<Group> list = q.list();

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_LIVEGROUPID,
						finderArgs, list);
				}
				else {
					if ((list.size() > 1) && _log.isWarnEnabled()) {
						_log.warn(
							"GroupPersistenceImpl.fetchByLiveGroupId(long, boolean) with parameters (" +
							StringUtil.merge(finderArgs) +
							") yields a result set with more than 1 result. This violates the logical unique restriction. There is no order guarantee on which result is returned by this finder.");
					}

					Group group = list.get(0);

					result = group;

					cacheResult(group);

					if ((group.getLiveGroupId() != liveGroupId)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_LIVEGROUPID,
							finderArgs, group);
					}
				}
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_LIVEGROUPID,
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
			return (Group)result;
		}
	}

	/**
	 * Removes the group where liveGroupId = &#63; from the database.
	 *
	 * @param liveGroupId the live group ID
	 * @return the group that was removed
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public Group removeByLiveGroupId(long liveGroupId)
		throws NoSuchGroupException, SystemException {
		Group group = findByLiveGroupId(liveGroupId);

		return remove(group);
	}

	/**
	 * Returns the number of groups where liveGroupId = &#63;.
	 *
	 * @param liveGroupId the live group ID
	 * @return the number of matching groups
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public int countByLiveGroupId(long liveGroupId) throws SystemException {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_LIVEGROUPID;

		Object[] finderArgs = new Object[] { liveGroupId };

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs,
				this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_GROUP__WHERE);

			query.append(_FINDER_COLUMN_LIVEGROUPID_LIVEGROUPID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(liveGroupId);

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

	private static final String _FINDER_COLUMN_LIVEGROUPID_LIVEGROUPID_2 = "group_.liveGroupId = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_C_C = new FinderPath(GroupModelImpl.ENTITY_CACHE_ENABLED,
			GroupModelImpl.FINDER_CACHE_ENABLED, GroupImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByC_C",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_C = new FinderPath(GroupModelImpl.ENTITY_CACHE_ENABLED,
			GroupModelImpl.FINDER_CACHE_ENABLED, GroupImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByC_C",
			new String[] { Long.class.getName(), Long.class.getName() },
			GroupModelImpl.COMPANYID_COLUMN_BITMASK |
			GroupModelImpl.CLASSNAMEID_COLUMN_BITMASK |
			GroupModelImpl.NAME_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_C_C = new FinderPath(GroupModelImpl.ENTITY_CACHE_ENABLED,
			GroupModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_C",
			new String[] { Long.class.getName(), Long.class.getName() });

	/**
	 * Returns all the groups where companyId = &#63; and classNameId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @return the matching groups
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<Group> findByC_C(long companyId, long classNameId)
		throws SystemException {
		return findByC_C(companyId, classNameId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the groups where companyId = &#63; and classNameId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.GroupModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param start the lower bound of the range of groups
	 * @param end the upper bound of the range of groups (not inclusive)
	 * @return the range of matching groups
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<Group> findByC_C(long companyId, long classNameId, int start,
		int end) throws SystemException {
		return findByC_C(companyId, classNameId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the groups where companyId = &#63; and classNameId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.GroupModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param start the lower bound of the range of groups
	 * @param end the upper bound of the range of groups (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching groups
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<Group> findByC_C(long companyId, long classNameId, int start,
		int end, OrderByComparator orderByComparator) throws SystemException {
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

		List<Group> list = (List<Group>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (Group group : list) {
				if ((companyId != group.getCompanyId()) ||
						(classNameId != group.getClassNameId())) {
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

			query.append(_SQL_SELECT_GROUP__WHERE);

			query.append(_FINDER_COLUMN_C_C_COMPANYID_2);

			query.append(_FINDER_COLUMN_C_C_CLASSNAMEID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(GroupModelImpl.ORDER_BY_JPQL);
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
					list = (List<Group>)QueryUtil.list(q, getDialect(), start,
							end, false);

					Collections.sort(list);

					list = new UnmodifiableList<Group>(list);
				}
				else {
					list = (List<Group>)QueryUtil.list(q, getDialect(), start,
							end);
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
	 * Returns the first group in the ordered set where companyId = &#63; and classNameId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching group
	 * @throws com.liferay.portal.NoSuchGroupException if a matching group could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public Group findByC_C_First(long companyId, long classNameId,
		OrderByComparator orderByComparator)
		throws NoSuchGroupException, SystemException {
		Group group = fetchByC_C_First(companyId, classNameId, orderByComparator);

		if (group != null) {
			return group;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("companyId=");
		msg.append(companyId);

		msg.append(", classNameId=");
		msg.append(classNameId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchGroupException(msg.toString());
	}

	/**
	 * Returns the first group in the ordered set where companyId = &#63; and classNameId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching group, or <code>null</code> if a matching group could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public Group fetchByC_C_First(long companyId, long classNameId,
		OrderByComparator orderByComparator) throws SystemException {
		List<Group> list = findByC_C(companyId, classNameId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last group in the ordered set where companyId = &#63; and classNameId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching group
	 * @throws com.liferay.portal.NoSuchGroupException if a matching group could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public Group findByC_C_Last(long companyId, long classNameId,
		OrderByComparator orderByComparator)
		throws NoSuchGroupException, SystemException {
		Group group = fetchByC_C_Last(companyId, classNameId, orderByComparator);

		if (group != null) {
			return group;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("companyId=");
		msg.append(companyId);

		msg.append(", classNameId=");
		msg.append(classNameId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchGroupException(msg.toString());
	}

	/**
	 * Returns the last group in the ordered set where companyId = &#63; and classNameId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching group, or <code>null</code> if a matching group could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public Group fetchByC_C_Last(long companyId, long classNameId,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByC_C(companyId, classNameId);

		if (count == 0) {
			return null;
		}

		List<Group> list = findByC_C(companyId, classNameId, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the groups before and after the current group in the ordered set where companyId = &#63; and classNameId = &#63;.
	 *
	 * @param groupId the primary key of the current group
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next group
	 * @throws com.liferay.portal.NoSuchGroupException if a group with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public Group[] findByC_C_PrevAndNext(long groupId, long companyId,
		long classNameId, OrderByComparator orderByComparator)
		throws NoSuchGroupException, SystemException {
		Group group = findByPrimaryKey(groupId);

		Session session = null;

		try {
			session = openSession();

			Group[] array = new GroupImpl[3];

			array[0] = getByC_C_PrevAndNext(session, group, companyId,
					classNameId, orderByComparator, true);

			array[1] = group;

			array[2] = getByC_C_PrevAndNext(session, group, companyId,
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

	protected Group getByC_C_PrevAndNext(Session session, Group group,
		long companyId, long classNameId, OrderByComparator orderByComparator,
		boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_GROUP__WHERE);

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
			query.append(GroupModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(companyId);

		qPos.add(classNameId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(group);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<Group> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the groups where companyId = &#63; and classNameId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public void removeByC_C(long companyId, long classNameId)
		throws SystemException {
		for (Group group : findByC_C(companyId, classNameId, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS, null)) {
			remove(group);
		}
	}

	/**
	 * Returns the number of groups where companyId = &#63; and classNameId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @return the number of matching groups
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public int countByC_C(long companyId, long classNameId)
		throws SystemException {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_C_C;

		Object[] finderArgs = new Object[] { companyId, classNameId };

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs,
				this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_GROUP__WHERE);

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

	private static final String _FINDER_COLUMN_C_C_COMPANYID_2 = "group_.companyId = ? AND ";
	private static final String _FINDER_COLUMN_C_C_CLASSNAMEID_2 = "group_.classNameId = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_C_P = new FinderPath(GroupModelImpl.ENTITY_CACHE_ENABLED,
			GroupModelImpl.FINDER_CACHE_ENABLED, GroupImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByC_P",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_P = new FinderPath(GroupModelImpl.ENTITY_CACHE_ENABLED,
			GroupModelImpl.FINDER_CACHE_ENABLED, GroupImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByC_P",
			new String[] { Long.class.getName(), Long.class.getName() },
			GroupModelImpl.COMPANYID_COLUMN_BITMASK |
			GroupModelImpl.PARENTGROUPID_COLUMN_BITMASK |
			GroupModelImpl.NAME_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_C_P = new FinderPath(GroupModelImpl.ENTITY_CACHE_ENABLED,
			GroupModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_P",
			new String[] { Long.class.getName(), Long.class.getName() });

	/**
	 * Returns all the groups where companyId = &#63; and parentGroupId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param parentGroupId the parent group ID
	 * @return the matching groups
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<Group> findByC_P(long companyId, long parentGroupId)
		throws SystemException {
		return findByC_P(companyId, parentGroupId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the groups where companyId = &#63; and parentGroupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.GroupModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param parentGroupId the parent group ID
	 * @param start the lower bound of the range of groups
	 * @param end the upper bound of the range of groups (not inclusive)
	 * @return the range of matching groups
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<Group> findByC_P(long companyId, long parentGroupId, int start,
		int end) throws SystemException {
		return findByC_P(companyId, parentGroupId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the groups where companyId = &#63; and parentGroupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.GroupModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param parentGroupId the parent group ID
	 * @param start the lower bound of the range of groups
	 * @param end the upper bound of the range of groups (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching groups
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<Group> findByC_P(long companyId, long parentGroupId, int start,
		int end, OrderByComparator orderByComparator) throws SystemException {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_P;
			finderArgs = new Object[] { companyId, parentGroupId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_C_P;
			finderArgs = new Object[] {
					companyId, parentGroupId,
					
					start, end, orderByComparator
				};
		}

		List<Group> list = (List<Group>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (Group group : list) {
				if ((companyId != group.getCompanyId()) ||
						(parentGroupId != group.getParentGroupId())) {
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

			query.append(_SQL_SELECT_GROUP__WHERE);

			query.append(_FINDER_COLUMN_C_P_COMPANYID_2);

			query.append(_FINDER_COLUMN_C_P_PARENTGROUPID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(GroupModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				qPos.add(parentGroupId);

				if (!pagination) {
					list = (List<Group>)QueryUtil.list(q, getDialect(), start,
							end, false);

					Collections.sort(list);

					list = new UnmodifiableList<Group>(list);
				}
				else {
					list = (List<Group>)QueryUtil.list(q, getDialect(), start,
							end);
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
	 * Returns the first group in the ordered set where companyId = &#63; and parentGroupId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param parentGroupId the parent group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching group
	 * @throws com.liferay.portal.NoSuchGroupException if a matching group could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public Group findByC_P_First(long companyId, long parentGroupId,
		OrderByComparator orderByComparator)
		throws NoSuchGroupException, SystemException {
		Group group = fetchByC_P_First(companyId, parentGroupId,
				orderByComparator);

		if (group != null) {
			return group;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("companyId=");
		msg.append(companyId);

		msg.append(", parentGroupId=");
		msg.append(parentGroupId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchGroupException(msg.toString());
	}

	/**
	 * Returns the first group in the ordered set where companyId = &#63; and parentGroupId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param parentGroupId the parent group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching group, or <code>null</code> if a matching group could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public Group fetchByC_P_First(long companyId, long parentGroupId,
		OrderByComparator orderByComparator) throws SystemException {
		List<Group> list = findByC_P(companyId, parentGroupId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last group in the ordered set where companyId = &#63; and parentGroupId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param parentGroupId the parent group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching group
	 * @throws com.liferay.portal.NoSuchGroupException if a matching group could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public Group findByC_P_Last(long companyId, long parentGroupId,
		OrderByComparator orderByComparator)
		throws NoSuchGroupException, SystemException {
		Group group = fetchByC_P_Last(companyId, parentGroupId,
				orderByComparator);

		if (group != null) {
			return group;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("companyId=");
		msg.append(companyId);

		msg.append(", parentGroupId=");
		msg.append(parentGroupId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchGroupException(msg.toString());
	}

	/**
	 * Returns the last group in the ordered set where companyId = &#63; and parentGroupId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param parentGroupId the parent group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching group, or <code>null</code> if a matching group could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public Group fetchByC_P_Last(long companyId, long parentGroupId,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByC_P(companyId, parentGroupId);

		if (count == 0) {
			return null;
		}

		List<Group> list = findByC_P(companyId, parentGroupId, count - 1,
				count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the groups before and after the current group in the ordered set where companyId = &#63; and parentGroupId = &#63;.
	 *
	 * @param groupId the primary key of the current group
	 * @param companyId the company ID
	 * @param parentGroupId the parent group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next group
	 * @throws com.liferay.portal.NoSuchGroupException if a group with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public Group[] findByC_P_PrevAndNext(long groupId, long companyId,
		long parentGroupId, OrderByComparator orderByComparator)
		throws NoSuchGroupException, SystemException {
		Group group = findByPrimaryKey(groupId);

		Session session = null;

		try {
			session = openSession();

			Group[] array = new GroupImpl[3];

			array[0] = getByC_P_PrevAndNext(session, group, companyId,
					parentGroupId, orderByComparator, true);

			array[1] = group;

			array[2] = getByC_P_PrevAndNext(session, group, companyId,
					parentGroupId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected Group getByC_P_PrevAndNext(Session session, Group group,
		long companyId, long parentGroupId,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_GROUP__WHERE);

		query.append(_FINDER_COLUMN_C_P_COMPANYID_2);

		query.append(_FINDER_COLUMN_C_P_PARENTGROUPID_2);

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
			query.append(GroupModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(companyId);

		qPos.add(parentGroupId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(group);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<Group> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the groups where companyId = &#63; and parentGroupId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param parentGroupId the parent group ID
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public void removeByC_P(long companyId, long parentGroupId)
		throws SystemException {
		for (Group group : findByC_P(companyId, parentGroupId,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(group);
		}
	}

	/**
	 * Returns the number of groups where companyId = &#63; and parentGroupId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param parentGroupId the parent group ID
	 * @return the number of matching groups
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public int countByC_P(long companyId, long parentGroupId)
		throws SystemException {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_C_P;

		Object[] finderArgs = new Object[] { companyId, parentGroupId };

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs,
				this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_GROUP__WHERE);

			query.append(_FINDER_COLUMN_C_P_COMPANYID_2);

			query.append(_FINDER_COLUMN_C_P_PARENTGROUPID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				qPos.add(parentGroupId);

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

	private static final String _FINDER_COLUMN_C_P_COMPANYID_2 = "group_.companyId = ? AND ";
	private static final String _FINDER_COLUMN_C_P_PARENTGROUPID_2 = "group_.parentGroupId = ?";
	public static final FinderPath FINDER_PATH_FETCH_BY_C_N = new FinderPath(GroupModelImpl.ENTITY_CACHE_ENABLED,
			GroupModelImpl.FINDER_CACHE_ENABLED, GroupImpl.class,
			FINDER_CLASS_NAME_ENTITY, "fetchByC_N",
			new String[] { Long.class.getName(), String.class.getName() },
			GroupModelImpl.COMPANYID_COLUMN_BITMASK |
			GroupModelImpl.NAME_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_C_N = new FinderPath(GroupModelImpl.ENTITY_CACHE_ENABLED,
			GroupModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_N",
			new String[] { Long.class.getName(), String.class.getName() });

	/**
	 * Returns the group where companyId = &#63; and name = &#63; or throws a {@link com.liferay.portal.NoSuchGroupException} if it could not be found.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @return the matching group
	 * @throws com.liferay.portal.NoSuchGroupException if a matching group could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public Group findByC_N(long companyId, String name)
		throws NoSuchGroupException, SystemException {
		Group group = fetchByC_N(companyId, name);

		if (group == null) {
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

			throw new NoSuchGroupException(msg.toString());
		}

		return group;
	}

	/**
	 * Returns the group where companyId = &#63; and name = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @return the matching group, or <code>null</code> if a matching group could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public Group fetchByC_N(long companyId, String name)
		throws SystemException {
		return fetchByC_N(companyId, name, true);
	}

	/**
	 * Returns the group where companyId = &#63; and name = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @param retrieveFromCache whether to use the finder cache
	 * @return the matching group, or <code>null</code> if a matching group could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public Group fetchByC_N(long companyId, String name,
		boolean retrieveFromCache) throws SystemException {
		Object[] finderArgs = new Object[] { companyId, name };

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_C_N,
					finderArgs, this);
		}

		if (result instanceof Group) {
			Group group = (Group)result;

			if ((companyId != group.getCompanyId()) ||
					!Validator.equals(name, group.getName())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_SELECT_GROUP__WHERE);

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
					qPos.add(name);
				}

				List<Group> list = q.list();

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_N,
						finderArgs, list);
				}
				else {
					Group group = list.get(0);

					result = group;

					cacheResult(group);

					if ((group.getCompanyId() != companyId) ||
							(group.getName() == null) ||
							!group.getName().equals(name)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_N,
							finderArgs, group);
					}
				}
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_C_N,
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
			return (Group)result;
		}
	}

	/**
	 * Removes the group where companyId = &#63; and name = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @return the group that was removed
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public Group removeByC_N(long companyId, String name)
		throws NoSuchGroupException, SystemException {
		Group group = findByC_N(companyId, name);

		return remove(group);
	}

	/**
	 * Returns the number of groups where companyId = &#63; and name = &#63;.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @return the number of matching groups
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public int countByC_N(long companyId, String name)
		throws SystemException {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_C_N;

		Object[] finderArgs = new Object[] { companyId, name };

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs,
				this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_GROUP__WHERE);

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
					qPos.add(name);
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

	private static final String _FINDER_COLUMN_C_N_COMPANYID_2 = "group_.companyId = ? AND ";
	private static final String _FINDER_COLUMN_C_N_NAME_1 = "group_.name IS NULL";
	private static final String _FINDER_COLUMN_C_N_NAME_2 = "group_.name = ?";
	private static final String _FINDER_COLUMN_C_N_NAME_3 = "(group_.name IS NULL OR group_.name = '')";
	public static final FinderPath FINDER_PATH_FETCH_BY_C_F = new FinderPath(GroupModelImpl.ENTITY_CACHE_ENABLED,
			GroupModelImpl.FINDER_CACHE_ENABLED, GroupImpl.class,
			FINDER_CLASS_NAME_ENTITY, "fetchByC_F",
			new String[] { Long.class.getName(), String.class.getName() },
			GroupModelImpl.COMPANYID_COLUMN_BITMASK |
			GroupModelImpl.FRIENDLYURL_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_C_F = new FinderPath(GroupModelImpl.ENTITY_CACHE_ENABLED,
			GroupModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_F",
			new String[] { Long.class.getName(), String.class.getName() });

	/**
	 * Returns the group where companyId = &#63; and friendlyURL = &#63; or throws a {@link com.liferay.portal.NoSuchGroupException} if it could not be found.
	 *
	 * @param companyId the company ID
	 * @param friendlyURL the friendly u r l
	 * @return the matching group
	 * @throws com.liferay.portal.NoSuchGroupException if a matching group could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public Group findByC_F(long companyId, String friendlyURL)
		throws NoSuchGroupException, SystemException {
		Group group = fetchByC_F(companyId, friendlyURL);

		if (group == null) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("companyId=");
			msg.append(companyId);

			msg.append(", friendlyURL=");
			msg.append(friendlyURL);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchGroupException(msg.toString());
		}

		return group;
	}

	/**
	 * Returns the group where companyId = &#63; and friendlyURL = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param companyId the company ID
	 * @param friendlyURL the friendly u r l
	 * @return the matching group, or <code>null</code> if a matching group could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public Group fetchByC_F(long companyId, String friendlyURL)
		throws SystemException {
		return fetchByC_F(companyId, friendlyURL, true);
	}

	/**
	 * Returns the group where companyId = &#63; and friendlyURL = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param companyId the company ID
	 * @param friendlyURL the friendly u r l
	 * @param retrieveFromCache whether to use the finder cache
	 * @return the matching group, or <code>null</code> if a matching group could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public Group fetchByC_F(long companyId, String friendlyURL,
		boolean retrieveFromCache) throws SystemException {
		Object[] finderArgs = new Object[] { companyId, friendlyURL };

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_C_F,
					finderArgs, this);
		}

		if (result instanceof Group) {
			Group group = (Group)result;

			if ((companyId != group.getCompanyId()) ||
					!Validator.equals(friendlyURL, group.getFriendlyURL())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_SELECT_GROUP__WHERE);

			query.append(_FINDER_COLUMN_C_F_COMPANYID_2);

			boolean bindFriendlyURL = false;

			if (friendlyURL == null) {
				query.append(_FINDER_COLUMN_C_F_FRIENDLYURL_1);
			}
			else if (friendlyURL.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_C_F_FRIENDLYURL_3);
			}
			else {
				bindFriendlyURL = true;

				query.append(_FINDER_COLUMN_C_F_FRIENDLYURL_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				if (bindFriendlyURL) {
					qPos.add(friendlyURL);
				}

				List<Group> list = q.list();

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_F,
						finderArgs, list);
				}
				else {
					Group group = list.get(0);

					result = group;

					cacheResult(group);

					if ((group.getCompanyId() != companyId) ||
							(group.getFriendlyURL() == null) ||
							!group.getFriendlyURL().equals(friendlyURL)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_F,
							finderArgs, group);
					}
				}
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_C_F,
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
			return (Group)result;
		}
	}

	/**
	 * Removes the group where companyId = &#63; and friendlyURL = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param friendlyURL the friendly u r l
	 * @return the group that was removed
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public Group removeByC_F(long companyId, String friendlyURL)
		throws NoSuchGroupException, SystemException {
		Group group = findByC_F(companyId, friendlyURL);

		return remove(group);
	}

	/**
	 * Returns the number of groups where companyId = &#63; and friendlyURL = &#63;.
	 *
	 * @param companyId the company ID
	 * @param friendlyURL the friendly u r l
	 * @return the number of matching groups
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public int countByC_F(long companyId, String friendlyURL)
		throws SystemException {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_C_F;

		Object[] finderArgs = new Object[] { companyId, friendlyURL };

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs,
				this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_GROUP__WHERE);

			query.append(_FINDER_COLUMN_C_F_COMPANYID_2);

			boolean bindFriendlyURL = false;

			if (friendlyURL == null) {
				query.append(_FINDER_COLUMN_C_F_FRIENDLYURL_1);
			}
			else if (friendlyURL.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_C_F_FRIENDLYURL_3);
			}
			else {
				bindFriendlyURL = true;

				query.append(_FINDER_COLUMN_C_F_FRIENDLYURL_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				if (bindFriendlyURL) {
					qPos.add(friendlyURL);
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

	private static final String _FINDER_COLUMN_C_F_COMPANYID_2 = "group_.companyId = ? AND ";
	private static final String _FINDER_COLUMN_C_F_FRIENDLYURL_1 = "group_.friendlyURL IS NULL";
	private static final String _FINDER_COLUMN_C_F_FRIENDLYURL_2 = "group_.friendlyURL = ?";
	private static final String _FINDER_COLUMN_C_F_FRIENDLYURL_3 = "(group_.friendlyURL IS NULL OR group_.friendlyURL = '')";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_C_S = new FinderPath(GroupModelImpl.ENTITY_CACHE_ENABLED,
			GroupModelImpl.FINDER_CACHE_ENABLED, GroupImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByC_S",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_S = new FinderPath(GroupModelImpl.ENTITY_CACHE_ENABLED,
			GroupModelImpl.FINDER_CACHE_ENABLED, GroupImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByC_S",
			new String[] { Long.class.getName(), Boolean.class.getName() },
			GroupModelImpl.COMPANYID_COLUMN_BITMASK |
			GroupModelImpl.SITE_COLUMN_BITMASK |
			GroupModelImpl.NAME_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_C_S = new FinderPath(GroupModelImpl.ENTITY_CACHE_ENABLED,
			GroupModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_S",
			new String[] { Long.class.getName(), Boolean.class.getName() });

	/**
	 * Returns all the groups where companyId = &#63; and site = &#63;.
	 *
	 * @param companyId the company ID
	 * @param site the site
	 * @return the matching groups
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<Group> findByC_S(long companyId, boolean site)
		throws SystemException {
		return findByC_S(companyId, site, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the groups where companyId = &#63; and site = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.GroupModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param site the site
	 * @param start the lower bound of the range of groups
	 * @param end the upper bound of the range of groups (not inclusive)
	 * @return the range of matching groups
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<Group> findByC_S(long companyId, boolean site, int start,
		int end) throws SystemException {
		return findByC_S(companyId, site, start, end, null);
	}

	/**
	 * Returns an ordered range of all the groups where companyId = &#63; and site = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.GroupModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param site the site
	 * @param start the lower bound of the range of groups
	 * @param end the upper bound of the range of groups (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching groups
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<Group> findByC_S(long companyId, boolean site, int start,
		int end, OrderByComparator orderByComparator) throws SystemException {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_S;
			finderArgs = new Object[] { companyId, site };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_C_S;
			finderArgs = new Object[] {
					companyId, site,
					
					start, end, orderByComparator
				};
		}

		List<Group> list = (List<Group>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (Group group : list) {
				if ((companyId != group.getCompanyId()) ||
						(site != group.getSite())) {
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

			query.append(_SQL_SELECT_GROUP__WHERE);

			query.append(_FINDER_COLUMN_C_S_COMPANYID_2);

			query.append(_FINDER_COLUMN_C_S_SITE_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(GroupModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				qPos.add(site);

				if (!pagination) {
					list = (List<Group>)QueryUtil.list(q, getDialect(), start,
							end, false);

					Collections.sort(list);

					list = new UnmodifiableList<Group>(list);
				}
				else {
					list = (List<Group>)QueryUtil.list(q, getDialect(), start,
							end);
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
	 * Returns the first group in the ordered set where companyId = &#63; and site = &#63;.
	 *
	 * @param companyId the company ID
	 * @param site the site
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching group
	 * @throws com.liferay.portal.NoSuchGroupException if a matching group could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public Group findByC_S_First(long companyId, boolean site,
		OrderByComparator orderByComparator)
		throws NoSuchGroupException, SystemException {
		Group group = fetchByC_S_First(companyId, site, orderByComparator);

		if (group != null) {
			return group;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("companyId=");
		msg.append(companyId);

		msg.append(", site=");
		msg.append(site);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchGroupException(msg.toString());
	}

	/**
	 * Returns the first group in the ordered set where companyId = &#63; and site = &#63;.
	 *
	 * @param companyId the company ID
	 * @param site the site
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching group, or <code>null</code> if a matching group could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public Group fetchByC_S_First(long companyId, boolean site,
		OrderByComparator orderByComparator) throws SystemException {
		List<Group> list = findByC_S(companyId, site, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last group in the ordered set where companyId = &#63; and site = &#63;.
	 *
	 * @param companyId the company ID
	 * @param site the site
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching group
	 * @throws com.liferay.portal.NoSuchGroupException if a matching group could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public Group findByC_S_Last(long companyId, boolean site,
		OrderByComparator orderByComparator)
		throws NoSuchGroupException, SystemException {
		Group group = fetchByC_S_Last(companyId, site, orderByComparator);

		if (group != null) {
			return group;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("companyId=");
		msg.append(companyId);

		msg.append(", site=");
		msg.append(site);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchGroupException(msg.toString());
	}

	/**
	 * Returns the last group in the ordered set where companyId = &#63; and site = &#63;.
	 *
	 * @param companyId the company ID
	 * @param site the site
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching group, or <code>null</code> if a matching group could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public Group fetchByC_S_Last(long companyId, boolean site,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByC_S(companyId, site);

		if (count == 0) {
			return null;
		}

		List<Group> list = findByC_S(companyId, site, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the groups before and after the current group in the ordered set where companyId = &#63; and site = &#63;.
	 *
	 * @param groupId the primary key of the current group
	 * @param companyId the company ID
	 * @param site the site
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next group
	 * @throws com.liferay.portal.NoSuchGroupException if a group with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public Group[] findByC_S_PrevAndNext(long groupId, long companyId,
		boolean site, OrderByComparator orderByComparator)
		throws NoSuchGroupException, SystemException {
		Group group = findByPrimaryKey(groupId);

		Session session = null;

		try {
			session = openSession();

			Group[] array = new GroupImpl[3];

			array[0] = getByC_S_PrevAndNext(session, group, companyId, site,
					orderByComparator, true);

			array[1] = group;

			array[2] = getByC_S_PrevAndNext(session, group, companyId, site,
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

	protected Group getByC_S_PrevAndNext(Session session, Group group,
		long companyId, boolean site, OrderByComparator orderByComparator,
		boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_GROUP__WHERE);

		query.append(_FINDER_COLUMN_C_S_COMPANYID_2);

		query.append(_FINDER_COLUMN_C_S_SITE_2);

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
			query.append(GroupModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(companyId);

		qPos.add(site);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(group);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<Group> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the groups where companyId = &#63; and site = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param site the site
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public void removeByC_S(long companyId, boolean site)
		throws SystemException {
		for (Group group : findByC_S(companyId, site, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS, null)) {
			remove(group);
		}
	}

	/**
	 * Returns the number of groups where companyId = &#63; and site = &#63;.
	 *
	 * @param companyId the company ID
	 * @param site the site
	 * @return the number of matching groups
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public int countByC_S(long companyId, boolean site)
		throws SystemException {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_C_S;

		Object[] finderArgs = new Object[] { companyId, site };

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs,
				this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_GROUP__WHERE);

			query.append(_FINDER_COLUMN_C_S_COMPANYID_2);

			query.append(_FINDER_COLUMN_C_S_SITE_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				qPos.add(site);

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

	private static final String _FINDER_COLUMN_C_S_COMPANYID_2 = "group_.companyId = ? AND ";
	private static final String _FINDER_COLUMN_C_S_SITE_2 = "group_.site = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_T_A = new FinderPath(GroupModelImpl.ENTITY_CACHE_ENABLED,
			GroupModelImpl.FINDER_CACHE_ENABLED, GroupImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByT_A",
			new String[] {
				Integer.class.getName(), Boolean.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_T_A = new FinderPath(GroupModelImpl.ENTITY_CACHE_ENABLED,
			GroupModelImpl.FINDER_CACHE_ENABLED, GroupImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByT_A",
			new String[] { Integer.class.getName(), Boolean.class.getName() },
			GroupModelImpl.TYPE_COLUMN_BITMASK |
			GroupModelImpl.ACTIVE_COLUMN_BITMASK |
			GroupModelImpl.NAME_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_T_A = new FinderPath(GroupModelImpl.ENTITY_CACHE_ENABLED,
			GroupModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByT_A",
			new String[] { Integer.class.getName(), Boolean.class.getName() });

	/**
	 * Returns all the groups where type = &#63; and active = &#63;.
	 *
	 * @param type the type
	 * @param active the active
	 * @return the matching groups
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<Group> findByT_A(int type, boolean active)
		throws SystemException {
		return findByT_A(type, active, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the groups where type = &#63; and active = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.GroupModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param type the type
	 * @param active the active
	 * @param start the lower bound of the range of groups
	 * @param end the upper bound of the range of groups (not inclusive)
	 * @return the range of matching groups
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<Group> findByT_A(int type, boolean active, int start, int end)
		throws SystemException {
		return findByT_A(type, active, start, end, null);
	}

	/**
	 * Returns an ordered range of all the groups where type = &#63; and active = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.GroupModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param type the type
	 * @param active the active
	 * @param start the lower bound of the range of groups
	 * @param end the upper bound of the range of groups (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching groups
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<Group> findByT_A(int type, boolean active, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_T_A;
			finderArgs = new Object[] { type, active };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_T_A;
			finderArgs = new Object[] {
					type, active,
					
					start, end, orderByComparator
				};
		}

		List<Group> list = (List<Group>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (Group group : list) {
				if ((type != group.getType()) || (active != group.getActive())) {
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

			query.append(_SQL_SELECT_GROUP__WHERE);

			query.append(_FINDER_COLUMN_T_A_TYPE_2);

			query.append(_FINDER_COLUMN_T_A_ACTIVE_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(GroupModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(type);

				qPos.add(active);

				if (!pagination) {
					list = (List<Group>)QueryUtil.list(q, getDialect(), start,
							end, false);

					Collections.sort(list);

					list = new UnmodifiableList<Group>(list);
				}
				else {
					list = (List<Group>)QueryUtil.list(q, getDialect(), start,
							end);
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
	 * Returns the first group in the ordered set where type = &#63; and active = &#63;.
	 *
	 * @param type the type
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching group
	 * @throws com.liferay.portal.NoSuchGroupException if a matching group could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public Group findByT_A_First(int type, boolean active,
		OrderByComparator orderByComparator)
		throws NoSuchGroupException, SystemException {
		Group group = fetchByT_A_First(type, active, orderByComparator);

		if (group != null) {
			return group;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("type=");
		msg.append(type);

		msg.append(", active=");
		msg.append(active);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchGroupException(msg.toString());
	}

	/**
	 * Returns the first group in the ordered set where type = &#63; and active = &#63;.
	 *
	 * @param type the type
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching group, or <code>null</code> if a matching group could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public Group fetchByT_A_First(int type, boolean active,
		OrderByComparator orderByComparator) throws SystemException {
		List<Group> list = findByT_A(type, active, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last group in the ordered set where type = &#63; and active = &#63;.
	 *
	 * @param type the type
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching group
	 * @throws com.liferay.portal.NoSuchGroupException if a matching group could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public Group findByT_A_Last(int type, boolean active,
		OrderByComparator orderByComparator)
		throws NoSuchGroupException, SystemException {
		Group group = fetchByT_A_Last(type, active, orderByComparator);

		if (group != null) {
			return group;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("type=");
		msg.append(type);

		msg.append(", active=");
		msg.append(active);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchGroupException(msg.toString());
	}

	/**
	 * Returns the last group in the ordered set where type = &#63; and active = &#63;.
	 *
	 * @param type the type
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching group, or <code>null</code> if a matching group could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public Group fetchByT_A_Last(int type, boolean active,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByT_A(type, active);

		if (count == 0) {
			return null;
		}

		List<Group> list = findByT_A(type, active, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the groups before and after the current group in the ordered set where type = &#63; and active = &#63;.
	 *
	 * @param groupId the primary key of the current group
	 * @param type the type
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next group
	 * @throws com.liferay.portal.NoSuchGroupException if a group with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public Group[] findByT_A_PrevAndNext(long groupId, int type,
		boolean active, OrderByComparator orderByComparator)
		throws NoSuchGroupException, SystemException {
		Group group = findByPrimaryKey(groupId);

		Session session = null;

		try {
			session = openSession();

			Group[] array = new GroupImpl[3];

			array[0] = getByT_A_PrevAndNext(session, group, type, active,
					orderByComparator, true);

			array[1] = group;

			array[2] = getByT_A_PrevAndNext(session, group, type, active,
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

	protected Group getByT_A_PrevAndNext(Session session, Group group,
		int type, boolean active, OrderByComparator orderByComparator,
		boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_GROUP__WHERE);

		query.append(_FINDER_COLUMN_T_A_TYPE_2);

		query.append(_FINDER_COLUMN_T_A_ACTIVE_2);

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
			query.append(GroupModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(type);

		qPos.add(active);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(group);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<Group> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the groups where type = &#63; and active = &#63; from the database.
	 *
	 * @param type the type
	 * @param active the active
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public void removeByT_A(int type, boolean active) throws SystemException {
		for (Group group : findByT_A(type, active, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS, null)) {
			remove(group);
		}
	}

	/**
	 * Returns the number of groups where type = &#63; and active = &#63;.
	 *
	 * @param type the type
	 * @param active the active
	 * @return the number of matching groups
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public int countByT_A(int type, boolean active) throws SystemException {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_T_A;

		Object[] finderArgs = new Object[] { type, active };

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs,
				this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_GROUP__WHERE);

			query.append(_FINDER_COLUMN_T_A_TYPE_2);

			query.append(_FINDER_COLUMN_T_A_ACTIVE_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(type);

				qPos.add(active);

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

	private static final String _FINDER_COLUMN_T_A_TYPE_2 = "group_.type = ? AND ";
	private static final String _FINDER_COLUMN_T_A_ACTIVE_2 = "group_.active = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_G_C_P = new FinderPath(GroupModelImpl.ENTITY_CACHE_ENABLED,
			GroupModelImpl.FINDER_CACHE_ENABLED, GroupImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_C_P",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_COUNT_BY_G_C_P = new FinderPath(GroupModelImpl.ENTITY_CACHE_ENABLED,
			GroupModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "countByG_C_P",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName()
			});

	/**
	 * Returns all the groups where groupId &gt; &#63; and companyId = &#63; and parentGroupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param parentGroupId the parent group ID
	 * @return the matching groups
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<Group> findByG_C_P(long groupId, long companyId,
		long parentGroupId) throws SystemException {
		return findByG_C_P(groupId, companyId, parentGroupId,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the groups where groupId &gt; &#63; and companyId = &#63; and parentGroupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.GroupModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param parentGroupId the parent group ID
	 * @param start the lower bound of the range of groups
	 * @param end the upper bound of the range of groups (not inclusive)
	 * @return the range of matching groups
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<Group> findByG_C_P(long groupId, long companyId,
		long parentGroupId, int start, int end) throws SystemException {
		return findByG_C_P(groupId, companyId, parentGroupId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the groups where groupId &gt; &#63; and companyId = &#63; and parentGroupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.GroupModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param parentGroupId the parent group ID
	 * @param start the lower bound of the range of groups
	 * @param end the upper bound of the range of groups (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching groups
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<Group> findByG_C_P(long groupId, long companyId,
		long parentGroupId, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_G_C_P;
		finderArgs = new Object[] {
				groupId, companyId, parentGroupId,
				
				start, end, orderByComparator
			};

		List<Group> list = (List<Group>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (Group group : list) {
				if ((groupId >= group.getGroupId()) ||
						(companyId != group.getCompanyId()) ||
						(parentGroupId != group.getParentGroupId())) {
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

			query.append(_SQL_SELECT_GROUP__WHERE);

			query.append(_FINDER_COLUMN_G_C_P_GROUPID_2);

			query.append(_FINDER_COLUMN_G_C_P_COMPANYID_2);

			query.append(_FINDER_COLUMN_G_C_P_PARENTGROUPID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(GroupModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(companyId);

				qPos.add(parentGroupId);

				if (!pagination) {
					list = (List<Group>)QueryUtil.list(q, getDialect(), start,
							end, false);

					Collections.sort(list);

					list = new UnmodifiableList<Group>(list);
				}
				else {
					list = (List<Group>)QueryUtil.list(q, getDialect(), start,
							end);
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
	 * Returns the first group in the ordered set where groupId &gt; &#63; and companyId = &#63; and parentGroupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param parentGroupId the parent group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching group
	 * @throws com.liferay.portal.NoSuchGroupException if a matching group could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public Group findByG_C_P_First(long groupId, long companyId,
		long parentGroupId, OrderByComparator orderByComparator)
		throws NoSuchGroupException, SystemException {
		Group group = fetchByG_C_P_First(groupId, companyId, parentGroupId,
				orderByComparator);

		if (group != null) {
			return group;
		}

		StringBundler msg = new StringBundler(8);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", companyId=");
		msg.append(companyId);

		msg.append(", parentGroupId=");
		msg.append(parentGroupId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchGroupException(msg.toString());
	}

	/**
	 * Returns the first group in the ordered set where groupId &gt; &#63; and companyId = &#63; and parentGroupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param parentGroupId the parent group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching group, or <code>null</code> if a matching group could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public Group fetchByG_C_P_First(long groupId, long companyId,
		long parentGroupId, OrderByComparator orderByComparator)
		throws SystemException {
		List<Group> list = findByG_C_P(groupId, companyId, parentGroupId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last group in the ordered set where groupId &gt; &#63; and companyId = &#63; and parentGroupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param parentGroupId the parent group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching group
	 * @throws com.liferay.portal.NoSuchGroupException if a matching group could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public Group findByG_C_P_Last(long groupId, long companyId,
		long parentGroupId, OrderByComparator orderByComparator)
		throws NoSuchGroupException, SystemException {
		Group group = fetchByG_C_P_Last(groupId, companyId, parentGroupId,
				orderByComparator);

		if (group != null) {
			return group;
		}

		StringBundler msg = new StringBundler(8);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", companyId=");
		msg.append(companyId);

		msg.append(", parentGroupId=");
		msg.append(parentGroupId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchGroupException(msg.toString());
	}

	/**
	 * Returns the last group in the ordered set where groupId &gt; &#63; and companyId = &#63; and parentGroupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param parentGroupId the parent group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching group, or <code>null</code> if a matching group could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public Group fetchByG_C_P_Last(long groupId, long companyId,
		long parentGroupId, OrderByComparator orderByComparator)
		throws SystemException {
		int count = countByG_C_P(groupId, companyId, parentGroupId);

		if (count == 0) {
			return null;
		}

		List<Group> list = findByG_C_P(groupId, companyId, parentGroupId,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Removes all the groups where groupId &gt; &#63; and companyId = &#63; and parentGroupId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param parentGroupId the parent group ID
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public void removeByG_C_P(long groupId, long companyId, long parentGroupId)
		throws SystemException {
		for (Group group : findByG_C_P(groupId, companyId, parentGroupId,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(group);
		}
	}

	/**
	 * Returns the number of groups where groupId &gt; &#63; and companyId = &#63; and parentGroupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param companyId the company ID
	 * @param parentGroupId the parent group ID
	 * @return the number of matching groups
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public int countByG_C_P(long groupId, long companyId, long parentGroupId)
		throws SystemException {
		FinderPath finderPath = FINDER_PATH_WITH_PAGINATION_COUNT_BY_G_C_P;

		Object[] finderArgs = new Object[] { groupId, companyId, parentGroupId };

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs,
				this);

		if (count == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_COUNT_GROUP__WHERE);

			query.append(_FINDER_COLUMN_G_C_P_GROUPID_2);

			query.append(_FINDER_COLUMN_G_C_P_COMPANYID_2);

			query.append(_FINDER_COLUMN_G_C_P_PARENTGROUPID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(companyId);

				qPos.add(parentGroupId);

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

	private static final String _FINDER_COLUMN_G_C_P_GROUPID_2 = "group_.groupId > ? AND ";
	private static final String _FINDER_COLUMN_G_C_P_COMPANYID_2 = "group_.companyId = ? AND ";
	private static final String _FINDER_COLUMN_G_C_P_PARENTGROUPID_2 = "group_.parentGroupId = ?";
	public static final FinderPath FINDER_PATH_FETCH_BY_C_C_C = new FinderPath(GroupModelImpl.ENTITY_CACHE_ENABLED,
			GroupModelImpl.FINDER_CACHE_ENABLED, GroupImpl.class,
			FINDER_CLASS_NAME_ENTITY, "fetchByC_C_C",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName()
			},
			GroupModelImpl.COMPANYID_COLUMN_BITMASK |
			GroupModelImpl.CLASSNAMEID_COLUMN_BITMASK |
			GroupModelImpl.CLASSPK_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_C_C_C = new FinderPath(GroupModelImpl.ENTITY_CACHE_ENABLED,
			GroupModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_C_C",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName()
			});

	/**
	 * Returns the group where companyId = &#63; and classNameId = &#63; and classPK = &#63; or throws a {@link com.liferay.portal.NoSuchGroupException} if it could not be found.
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @return the matching group
	 * @throws com.liferay.portal.NoSuchGroupException if a matching group could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public Group findByC_C_C(long companyId, long classNameId, long classPK)
		throws NoSuchGroupException, SystemException {
		Group group = fetchByC_C_C(companyId, classNameId, classPK);

		if (group == null) {
			StringBundler msg = new StringBundler(8);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("companyId=");
			msg.append(companyId);

			msg.append(", classNameId=");
			msg.append(classNameId);

			msg.append(", classPK=");
			msg.append(classPK);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchGroupException(msg.toString());
		}

		return group;
	}

	/**
	 * Returns the group where companyId = &#63; and classNameId = &#63; and classPK = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @return the matching group, or <code>null</code> if a matching group could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public Group fetchByC_C_C(long companyId, long classNameId, long classPK)
		throws SystemException {
		return fetchByC_C_C(companyId, classNameId, classPK, true);
	}

	/**
	 * Returns the group where companyId = &#63; and classNameId = &#63; and classPK = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @param retrieveFromCache whether to use the finder cache
	 * @return the matching group, or <code>null</code> if a matching group could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public Group fetchByC_C_C(long companyId, long classNameId, long classPK,
		boolean retrieveFromCache) throws SystemException {
		Object[] finderArgs = new Object[] { companyId, classNameId, classPK };

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_C_C_C,
					finderArgs, this);
		}

		if (result instanceof Group) {
			Group group = (Group)result;

			if ((companyId != group.getCompanyId()) ||
					(classNameId != group.getClassNameId()) ||
					(classPK != group.getClassPK())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(5);

			query.append(_SQL_SELECT_GROUP__WHERE);

			query.append(_FINDER_COLUMN_C_C_C_COMPANYID_2);

			query.append(_FINDER_COLUMN_C_C_C_CLASSNAMEID_2);

			query.append(_FINDER_COLUMN_C_C_C_CLASSPK_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				qPos.add(classNameId);

				qPos.add(classPK);

				List<Group> list = q.list();

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_C_C,
						finderArgs, list);
				}
				else {
					Group group = list.get(0);

					result = group;

					cacheResult(group);

					if ((group.getCompanyId() != companyId) ||
							(group.getClassNameId() != classNameId) ||
							(group.getClassPK() != classPK)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_C_C,
							finderArgs, group);
					}
				}
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_C_C_C,
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
			return (Group)result;
		}
	}

	/**
	 * Removes the group where companyId = &#63; and classNameId = &#63; and classPK = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @return the group that was removed
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public Group removeByC_C_C(long companyId, long classNameId, long classPK)
		throws NoSuchGroupException, SystemException {
		Group group = findByC_C_C(companyId, classNameId, classPK);

		return remove(group);
	}

	/**
	 * Returns the number of groups where companyId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param classPK the class p k
	 * @return the number of matching groups
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public int countByC_C_C(long companyId, long classNameId, long classPK)
		throws SystemException {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_C_C_C;

		Object[] finderArgs = new Object[] { companyId, classNameId, classPK };

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs,
				this);

		if (count == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_COUNT_GROUP__WHERE);

			query.append(_FINDER_COLUMN_C_C_C_COMPANYID_2);

			query.append(_FINDER_COLUMN_C_C_C_CLASSNAMEID_2);

			query.append(_FINDER_COLUMN_C_C_C_CLASSPK_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				qPos.add(classNameId);

				qPos.add(classPK);

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

	private static final String _FINDER_COLUMN_C_C_C_COMPANYID_2 = "group_.companyId = ? AND ";
	private static final String _FINDER_COLUMN_C_C_C_CLASSNAMEID_2 = "group_.classNameId = ? AND ";
	private static final String _FINDER_COLUMN_C_C_C_CLASSPK_2 = "group_.classPK = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_C_C_P = new FinderPath(GroupModelImpl.ENTITY_CACHE_ENABLED,
			GroupModelImpl.FINDER_CACHE_ENABLED, GroupImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByC_C_P",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_C_P = new FinderPath(GroupModelImpl.ENTITY_CACHE_ENABLED,
			GroupModelImpl.FINDER_CACHE_ENABLED, GroupImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByC_C_P",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName()
			},
			GroupModelImpl.COMPANYID_COLUMN_BITMASK |
			GroupModelImpl.CLASSNAMEID_COLUMN_BITMASK |
			GroupModelImpl.PARENTGROUPID_COLUMN_BITMASK |
			GroupModelImpl.NAME_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_C_C_P = new FinderPath(GroupModelImpl.ENTITY_CACHE_ENABLED,
			GroupModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_C_P",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName()
			});

	/**
	 * Returns all the groups where companyId = &#63; and classNameId = &#63; and parentGroupId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param parentGroupId the parent group ID
	 * @return the matching groups
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<Group> findByC_C_P(long companyId, long classNameId,
		long parentGroupId) throws SystemException {
		return findByC_C_P(companyId, classNameId, parentGroupId,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the groups where companyId = &#63; and classNameId = &#63; and parentGroupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.GroupModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param parentGroupId the parent group ID
	 * @param start the lower bound of the range of groups
	 * @param end the upper bound of the range of groups (not inclusive)
	 * @return the range of matching groups
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<Group> findByC_C_P(long companyId, long classNameId,
		long parentGroupId, int start, int end) throws SystemException {
		return findByC_C_P(companyId, classNameId, parentGroupId, start, end,
			null);
	}

	/**
	 * Returns an ordered range of all the groups where companyId = &#63; and classNameId = &#63; and parentGroupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.GroupModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param parentGroupId the parent group ID
	 * @param start the lower bound of the range of groups
	 * @param end the upper bound of the range of groups (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching groups
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<Group> findByC_C_P(long companyId, long classNameId,
		long parentGroupId, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_C_P;
			finderArgs = new Object[] { companyId, classNameId, parentGroupId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_C_C_P;
			finderArgs = new Object[] {
					companyId, classNameId, parentGroupId,
					
					start, end, orderByComparator
				};
		}

		List<Group> list = (List<Group>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (Group group : list) {
				if ((companyId != group.getCompanyId()) ||
						(classNameId != group.getClassNameId()) ||
						(parentGroupId != group.getParentGroupId())) {
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

			query.append(_SQL_SELECT_GROUP__WHERE);

			query.append(_FINDER_COLUMN_C_C_P_COMPANYID_2);

			query.append(_FINDER_COLUMN_C_C_P_CLASSNAMEID_2);

			query.append(_FINDER_COLUMN_C_C_P_PARENTGROUPID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(GroupModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				qPos.add(classNameId);

				qPos.add(parentGroupId);

				if (!pagination) {
					list = (List<Group>)QueryUtil.list(q, getDialect(), start,
							end, false);

					Collections.sort(list);

					list = new UnmodifiableList<Group>(list);
				}
				else {
					list = (List<Group>)QueryUtil.list(q, getDialect(), start,
							end);
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
	 * Returns the first group in the ordered set where companyId = &#63; and classNameId = &#63; and parentGroupId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param parentGroupId the parent group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching group
	 * @throws com.liferay.portal.NoSuchGroupException if a matching group could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public Group findByC_C_P_First(long companyId, long classNameId,
		long parentGroupId, OrderByComparator orderByComparator)
		throws NoSuchGroupException, SystemException {
		Group group = fetchByC_C_P_First(companyId, classNameId, parentGroupId,
				orderByComparator);

		if (group != null) {
			return group;
		}

		StringBundler msg = new StringBundler(8);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("companyId=");
		msg.append(companyId);

		msg.append(", classNameId=");
		msg.append(classNameId);

		msg.append(", parentGroupId=");
		msg.append(parentGroupId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchGroupException(msg.toString());
	}

	/**
	 * Returns the first group in the ordered set where companyId = &#63; and classNameId = &#63; and parentGroupId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param parentGroupId the parent group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching group, or <code>null</code> if a matching group could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public Group fetchByC_C_P_First(long companyId, long classNameId,
		long parentGroupId, OrderByComparator orderByComparator)
		throws SystemException {
		List<Group> list = findByC_C_P(companyId, classNameId, parentGroupId,
				0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last group in the ordered set where companyId = &#63; and classNameId = &#63; and parentGroupId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param parentGroupId the parent group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching group
	 * @throws com.liferay.portal.NoSuchGroupException if a matching group could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public Group findByC_C_P_Last(long companyId, long classNameId,
		long parentGroupId, OrderByComparator orderByComparator)
		throws NoSuchGroupException, SystemException {
		Group group = fetchByC_C_P_Last(companyId, classNameId, parentGroupId,
				orderByComparator);

		if (group != null) {
			return group;
		}

		StringBundler msg = new StringBundler(8);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("companyId=");
		msg.append(companyId);

		msg.append(", classNameId=");
		msg.append(classNameId);

		msg.append(", parentGroupId=");
		msg.append(parentGroupId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchGroupException(msg.toString());
	}

	/**
	 * Returns the last group in the ordered set where companyId = &#63; and classNameId = &#63; and parentGroupId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param parentGroupId the parent group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching group, or <code>null</code> if a matching group could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public Group fetchByC_C_P_Last(long companyId, long classNameId,
		long parentGroupId, OrderByComparator orderByComparator)
		throws SystemException {
		int count = countByC_C_P(companyId, classNameId, parentGroupId);

		if (count == 0) {
			return null;
		}

		List<Group> list = findByC_C_P(companyId, classNameId, parentGroupId,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the groups before and after the current group in the ordered set where companyId = &#63; and classNameId = &#63; and parentGroupId = &#63;.
	 *
	 * @param groupId the primary key of the current group
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param parentGroupId the parent group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next group
	 * @throws com.liferay.portal.NoSuchGroupException if a group with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public Group[] findByC_C_P_PrevAndNext(long groupId, long companyId,
		long classNameId, long parentGroupId,
		OrderByComparator orderByComparator)
		throws NoSuchGroupException, SystemException {
		Group group = findByPrimaryKey(groupId);

		Session session = null;

		try {
			session = openSession();

			Group[] array = new GroupImpl[3];

			array[0] = getByC_C_P_PrevAndNext(session, group, companyId,
					classNameId, parentGroupId, orderByComparator, true);

			array[1] = group;

			array[2] = getByC_C_P_PrevAndNext(session, group, companyId,
					classNameId, parentGroupId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected Group getByC_C_P_PrevAndNext(Session session, Group group,
		long companyId, long classNameId, long parentGroupId,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_GROUP__WHERE);

		query.append(_FINDER_COLUMN_C_C_P_COMPANYID_2);

		query.append(_FINDER_COLUMN_C_C_P_CLASSNAMEID_2);

		query.append(_FINDER_COLUMN_C_C_P_PARENTGROUPID_2);

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
			query.append(GroupModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(companyId);

		qPos.add(classNameId);

		qPos.add(parentGroupId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(group);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<Group> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the groups where companyId = &#63; and classNameId = &#63; and parentGroupId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param parentGroupId the parent group ID
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public void removeByC_C_P(long companyId, long classNameId,
		long parentGroupId) throws SystemException {
		for (Group group : findByC_C_P(companyId, classNameId, parentGroupId,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(group);
		}
	}

	/**
	 * Returns the number of groups where companyId = &#63; and classNameId = &#63; and parentGroupId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param parentGroupId the parent group ID
	 * @return the number of matching groups
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public int countByC_C_P(long companyId, long classNameId, long parentGroupId)
		throws SystemException {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_C_C_P;

		Object[] finderArgs = new Object[] { companyId, classNameId, parentGroupId };

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs,
				this);

		if (count == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_COUNT_GROUP__WHERE);

			query.append(_FINDER_COLUMN_C_C_P_COMPANYID_2);

			query.append(_FINDER_COLUMN_C_C_P_CLASSNAMEID_2);

			query.append(_FINDER_COLUMN_C_C_P_PARENTGROUPID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				qPos.add(classNameId);

				qPos.add(parentGroupId);

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

	private static final String _FINDER_COLUMN_C_C_P_COMPANYID_2 = "group_.companyId = ? AND ";
	private static final String _FINDER_COLUMN_C_C_P_CLASSNAMEID_2 = "group_.classNameId = ? AND ";
	private static final String _FINDER_COLUMN_C_C_P_PARENTGROUPID_2 = "group_.parentGroupId = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_C_P_S = new FinderPath(GroupModelImpl.ENTITY_CACHE_ENABLED,
			GroupModelImpl.FINDER_CACHE_ENABLED, GroupImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByC_P_S",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Boolean.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_P_S = new FinderPath(GroupModelImpl.ENTITY_CACHE_ENABLED,
			GroupModelImpl.FINDER_CACHE_ENABLED, GroupImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByC_P_S",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Boolean.class.getName()
			},
			GroupModelImpl.COMPANYID_COLUMN_BITMASK |
			GroupModelImpl.PARENTGROUPID_COLUMN_BITMASK |
			GroupModelImpl.SITE_COLUMN_BITMASK |
			GroupModelImpl.NAME_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_C_P_S = new FinderPath(GroupModelImpl.ENTITY_CACHE_ENABLED,
			GroupModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_P_S",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Boolean.class.getName()
			});

	/**
	 * Returns all the groups where companyId = &#63; and parentGroupId = &#63; and site = &#63;.
	 *
	 * @param companyId the company ID
	 * @param parentGroupId the parent group ID
	 * @param site the site
	 * @return the matching groups
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<Group> findByC_P_S(long companyId, long parentGroupId,
		boolean site) throws SystemException {
		return findByC_P_S(companyId, parentGroupId, site, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the groups where companyId = &#63; and parentGroupId = &#63; and site = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.GroupModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param parentGroupId the parent group ID
	 * @param site the site
	 * @param start the lower bound of the range of groups
	 * @param end the upper bound of the range of groups (not inclusive)
	 * @return the range of matching groups
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<Group> findByC_P_S(long companyId, long parentGroupId,
		boolean site, int start, int end) throws SystemException {
		return findByC_P_S(companyId, parentGroupId, site, start, end, null);
	}

	/**
	 * Returns an ordered range of all the groups where companyId = &#63; and parentGroupId = &#63; and site = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.GroupModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param parentGroupId the parent group ID
	 * @param site the site
	 * @param start the lower bound of the range of groups
	 * @param end the upper bound of the range of groups (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching groups
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<Group> findByC_P_S(long companyId, long parentGroupId,
		boolean site, int start, int end, OrderByComparator orderByComparator)
		throws SystemException {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_P_S;
			finderArgs = new Object[] { companyId, parentGroupId, site };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_C_P_S;
			finderArgs = new Object[] {
					companyId, parentGroupId, site,
					
					start, end, orderByComparator
				};
		}

		List<Group> list = (List<Group>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (Group group : list) {
				if ((companyId != group.getCompanyId()) ||
						(parentGroupId != group.getParentGroupId()) ||
						(site != group.getSite())) {
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

			query.append(_SQL_SELECT_GROUP__WHERE);

			query.append(_FINDER_COLUMN_C_P_S_COMPANYID_2);

			query.append(_FINDER_COLUMN_C_P_S_PARENTGROUPID_2);

			query.append(_FINDER_COLUMN_C_P_S_SITE_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(GroupModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				qPos.add(parentGroupId);

				qPos.add(site);

				if (!pagination) {
					list = (List<Group>)QueryUtil.list(q, getDialect(), start,
							end, false);

					Collections.sort(list);

					list = new UnmodifiableList<Group>(list);
				}
				else {
					list = (List<Group>)QueryUtil.list(q, getDialect(), start,
							end);
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
	 * Returns the first group in the ordered set where companyId = &#63; and parentGroupId = &#63; and site = &#63;.
	 *
	 * @param companyId the company ID
	 * @param parentGroupId the parent group ID
	 * @param site the site
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching group
	 * @throws com.liferay.portal.NoSuchGroupException if a matching group could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public Group findByC_P_S_First(long companyId, long parentGroupId,
		boolean site, OrderByComparator orderByComparator)
		throws NoSuchGroupException, SystemException {
		Group group = fetchByC_P_S_First(companyId, parentGroupId, site,
				orderByComparator);

		if (group != null) {
			return group;
		}

		StringBundler msg = new StringBundler(8);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("companyId=");
		msg.append(companyId);

		msg.append(", parentGroupId=");
		msg.append(parentGroupId);

		msg.append(", site=");
		msg.append(site);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchGroupException(msg.toString());
	}

	/**
	 * Returns the first group in the ordered set where companyId = &#63; and parentGroupId = &#63; and site = &#63;.
	 *
	 * @param companyId the company ID
	 * @param parentGroupId the parent group ID
	 * @param site the site
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching group, or <code>null</code> if a matching group could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public Group fetchByC_P_S_First(long companyId, long parentGroupId,
		boolean site, OrderByComparator orderByComparator)
		throws SystemException {
		List<Group> list = findByC_P_S(companyId, parentGroupId, site, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last group in the ordered set where companyId = &#63; and parentGroupId = &#63; and site = &#63;.
	 *
	 * @param companyId the company ID
	 * @param parentGroupId the parent group ID
	 * @param site the site
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching group
	 * @throws com.liferay.portal.NoSuchGroupException if a matching group could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public Group findByC_P_S_Last(long companyId, long parentGroupId,
		boolean site, OrderByComparator orderByComparator)
		throws NoSuchGroupException, SystemException {
		Group group = fetchByC_P_S_Last(companyId, parentGroupId, site,
				orderByComparator);

		if (group != null) {
			return group;
		}

		StringBundler msg = new StringBundler(8);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("companyId=");
		msg.append(companyId);

		msg.append(", parentGroupId=");
		msg.append(parentGroupId);

		msg.append(", site=");
		msg.append(site);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchGroupException(msg.toString());
	}

	/**
	 * Returns the last group in the ordered set where companyId = &#63; and parentGroupId = &#63; and site = &#63;.
	 *
	 * @param companyId the company ID
	 * @param parentGroupId the parent group ID
	 * @param site the site
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching group, or <code>null</code> if a matching group could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public Group fetchByC_P_S_Last(long companyId, long parentGroupId,
		boolean site, OrderByComparator orderByComparator)
		throws SystemException {
		int count = countByC_P_S(companyId, parentGroupId, site);

		if (count == 0) {
			return null;
		}

		List<Group> list = findByC_P_S(companyId, parentGroupId, site,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the groups before and after the current group in the ordered set where companyId = &#63; and parentGroupId = &#63; and site = &#63;.
	 *
	 * @param groupId the primary key of the current group
	 * @param companyId the company ID
	 * @param parentGroupId the parent group ID
	 * @param site the site
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next group
	 * @throws com.liferay.portal.NoSuchGroupException if a group with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public Group[] findByC_P_S_PrevAndNext(long groupId, long companyId,
		long parentGroupId, boolean site, OrderByComparator orderByComparator)
		throws NoSuchGroupException, SystemException {
		Group group = findByPrimaryKey(groupId);

		Session session = null;

		try {
			session = openSession();

			Group[] array = new GroupImpl[3];

			array[0] = getByC_P_S_PrevAndNext(session, group, companyId,
					parentGroupId, site, orderByComparator, true);

			array[1] = group;

			array[2] = getByC_P_S_PrevAndNext(session, group, companyId,
					parentGroupId, site, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected Group getByC_P_S_PrevAndNext(Session session, Group group,
		long companyId, long parentGroupId, boolean site,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_GROUP__WHERE);

		query.append(_FINDER_COLUMN_C_P_S_COMPANYID_2);

		query.append(_FINDER_COLUMN_C_P_S_PARENTGROUPID_2);

		query.append(_FINDER_COLUMN_C_P_S_SITE_2);

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
			query.append(GroupModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(companyId);

		qPos.add(parentGroupId);

		qPos.add(site);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(group);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<Group> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the groups where companyId = &#63; and parentGroupId = &#63; and site = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param parentGroupId the parent group ID
	 * @param site the site
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public void removeByC_P_S(long companyId, long parentGroupId, boolean site)
		throws SystemException {
		for (Group group : findByC_P_S(companyId, parentGroupId, site,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(group);
		}
	}

	/**
	 * Returns the number of groups where companyId = &#63; and parentGroupId = &#63; and site = &#63;.
	 *
	 * @param companyId the company ID
	 * @param parentGroupId the parent group ID
	 * @param site the site
	 * @return the number of matching groups
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public int countByC_P_S(long companyId, long parentGroupId, boolean site)
		throws SystemException {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_C_P_S;

		Object[] finderArgs = new Object[] { companyId, parentGroupId, site };

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs,
				this);

		if (count == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_COUNT_GROUP__WHERE);

			query.append(_FINDER_COLUMN_C_P_S_COMPANYID_2);

			query.append(_FINDER_COLUMN_C_P_S_PARENTGROUPID_2);

			query.append(_FINDER_COLUMN_C_P_S_SITE_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				qPos.add(parentGroupId);

				qPos.add(site);

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

	private static final String _FINDER_COLUMN_C_P_S_COMPANYID_2 = "group_.companyId = ? AND ";
	private static final String _FINDER_COLUMN_C_P_S_PARENTGROUPID_2 = "group_.parentGroupId = ? AND ";
	private static final String _FINDER_COLUMN_C_P_S_SITE_2 = "group_.site = ?";
	public static final FinderPath FINDER_PATH_FETCH_BY_C_L_N = new FinderPath(GroupModelImpl.ENTITY_CACHE_ENABLED,
			GroupModelImpl.FINDER_CACHE_ENABLED, GroupImpl.class,
			FINDER_CLASS_NAME_ENTITY, "fetchByC_L_N",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				String.class.getName()
			},
			GroupModelImpl.COMPANYID_COLUMN_BITMASK |
			GroupModelImpl.LIVEGROUPID_COLUMN_BITMASK |
			GroupModelImpl.NAME_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_C_L_N = new FinderPath(GroupModelImpl.ENTITY_CACHE_ENABLED,
			GroupModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_L_N",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				String.class.getName()
			});

	/**
	 * Returns the group where companyId = &#63; and liveGroupId = &#63; and name = &#63; or throws a {@link com.liferay.portal.NoSuchGroupException} if it could not be found.
	 *
	 * @param companyId the company ID
	 * @param liveGroupId the live group ID
	 * @param name the name
	 * @return the matching group
	 * @throws com.liferay.portal.NoSuchGroupException if a matching group could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public Group findByC_L_N(long companyId, long liveGroupId, String name)
		throws NoSuchGroupException, SystemException {
		Group group = fetchByC_L_N(companyId, liveGroupId, name);

		if (group == null) {
			StringBundler msg = new StringBundler(8);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("companyId=");
			msg.append(companyId);

			msg.append(", liveGroupId=");
			msg.append(liveGroupId);

			msg.append(", name=");
			msg.append(name);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchGroupException(msg.toString());
		}

		return group;
	}

	/**
	 * Returns the group where companyId = &#63; and liveGroupId = &#63; and name = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param companyId the company ID
	 * @param liveGroupId the live group ID
	 * @param name the name
	 * @return the matching group, or <code>null</code> if a matching group could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public Group fetchByC_L_N(long companyId, long liveGroupId, String name)
		throws SystemException {
		return fetchByC_L_N(companyId, liveGroupId, name, true);
	}

	/**
	 * Returns the group where companyId = &#63; and liveGroupId = &#63; and name = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param companyId the company ID
	 * @param liveGroupId the live group ID
	 * @param name the name
	 * @param retrieveFromCache whether to use the finder cache
	 * @return the matching group, or <code>null</code> if a matching group could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public Group fetchByC_L_N(long companyId, long liveGroupId, String name,
		boolean retrieveFromCache) throws SystemException {
		Object[] finderArgs = new Object[] { companyId, liveGroupId, name };

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_C_L_N,
					finderArgs, this);
		}

		if (result instanceof Group) {
			Group group = (Group)result;

			if ((companyId != group.getCompanyId()) ||
					(liveGroupId != group.getLiveGroupId()) ||
					!Validator.equals(name, group.getName())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(5);

			query.append(_SQL_SELECT_GROUP__WHERE);

			query.append(_FINDER_COLUMN_C_L_N_COMPANYID_2);

			query.append(_FINDER_COLUMN_C_L_N_LIVEGROUPID_2);

			boolean bindName = false;

			if (name == null) {
				query.append(_FINDER_COLUMN_C_L_N_NAME_1);
			}
			else if (name.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_C_L_N_NAME_3);
			}
			else {
				bindName = true;

				query.append(_FINDER_COLUMN_C_L_N_NAME_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				qPos.add(liveGroupId);

				if (bindName) {
					qPos.add(name);
				}

				List<Group> list = q.list();

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_L_N,
						finderArgs, list);
				}
				else {
					Group group = list.get(0);

					result = group;

					cacheResult(group);

					if ((group.getCompanyId() != companyId) ||
							(group.getLiveGroupId() != liveGroupId) ||
							(group.getName() == null) ||
							!group.getName().equals(name)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_L_N,
							finderArgs, group);
					}
				}
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_C_L_N,
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
			return (Group)result;
		}
	}

	/**
	 * Removes the group where companyId = &#63; and liveGroupId = &#63; and name = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param liveGroupId the live group ID
	 * @param name the name
	 * @return the group that was removed
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public Group removeByC_L_N(long companyId, long liveGroupId, String name)
		throws NoSuchGroupException, SystemException {
		Group group = findByC_L_N(companyId, liveGroupId, name);

		return remove(group);
	}

	/**
	 * Returns the number of groups where companyId = &#63; and liveGroupId = &#63; and name = &#63;.
	 *
	 * @param companyId the company ID
	 * @param liveGroupId the live group ID
	 * @param name the name
	 * @return the number of matching groups
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public int countByC_L_N(long companyId, long liveGroupId, String name)
		throws SystemException {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_C_L_N;

		Object[] finderArgs = new Object[] { companyId, liveGroupId, name };

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs,
				this);

		if (count == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_COUNT_GROUP__WHERE);

			query.append(_FINDER_COLUMN_C_L_N_COMPANYID_2);

			query.append(_FINDER_COLUMN_C_L_N_LIVEGROUPID_2);

			boolean bindName = false;

			if (name == null) {
				query.append(_FINDER_COLUMN_C_L_N_NAME_1);
			}
			else if (name.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_C_L_N_NAME_3);
			}
			else {
				bindName = true;

				query.append(_FINDER_COLUMN_C_L_N_NAME_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				qPos.add(liveGroupId);

				if (bindName) {
					qPos.add(name);
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

	private static final String _FINDER_COLUMN_C_L_N_COMPANYID_2 = "group_.companyId = ? AND ";
	private static final String _FINDER_COLUMN_C_L_N_LIVEGROUPID_2 = "group_.liveGroupId = ? AND ";
	private static final String _FINDER_COLUMN_C_L_N_NAME_1 = "group_.name IS NULL";
	private static final String _FINDER_COLUMN_C_L_N_NAME_2 = "group_.name = ?";
	private static final String _FINDER_COLUMN_C_L_N_NAME_3 = "(group_.name IS NULL OR group_.name = '')";
	public static final FinderPath FINDER_PATH_FETCH_BY_C_C_L_N = new FinderPath(GroupModelImpl.ENTITY_CACHE_ENABLED,
			GroupModelImpl.FINDER_CACHE_ENABLED, GroupImpl.class,
			FINDER_CLASS_NAME_ENTITY, "fetchByC_C_L_N",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName(),
				String.class.getName()
			},
			GroupModelImpl.COMPANYID_COLUMN_BITMASK |
			GroupModelImpl.CLASSNAMEID_COLUMN_BITMASK |
			GroupModelImpl.LIVEGROUPID_COLUMN_BITMASK |
			GroupModelImpl.NAME_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_C_C_L_N = new FinderPath(GroupModelImpl.ENTITY_CACHE_ENABLED,
			GroupModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_C_L_N",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName(),
				String.class.getName()
			});

	/**
	 * Returns the group where companyId = &#63; and classNameId = &#63; and liveGroupId = &#63; and name = &#63; or throws a {@link com.liferay.portal.NoSuchGroupException} if it could not be found.
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param liveGroupId the live group ID
	 * @param name the name
	 * @return the matching group
	 * @throws com.liferay.portal.NoSuchGroupException if a matching group could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public Group findByC_C_L_N(long companyId, long classNameId,
		long liveGroupId, String name)
		throws NoSuchGroupException, SystemException {
		Group group = fetchByC_C_L_N(companyId, classNameId, liveGroupId, name);

		if (group == null) {
			StringBundler msg = new StringBundler(10);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("companyId=");
			msg.append(companyId);

			msg.append(", classNameId=");
			msg.append(classNameId);

			msg.append(", liveGroupId=");
			msg.append(liveGroupId);

			msg.append(", name=");
			msg.append(name);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchGroupException(msg.toString());
		}

		return group;
	}

	/**
	 * Returns the group where companyId = &#63; and classNameId = &#63; and liveGroupId = &#63; and name = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param liveGroupId the live group ID
	 * @param name the name
	 * @return the matching group, or <code>null</code> if a matching group could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public Group fetchByC_C_L_N(long companyId, long classNameId,
		long liveGroupId, String name) throws SystemException {
		return fetchByC_C_L_N(companyId, classNameId, liveGroupId, name, true);
	}

	/**
	 * Returns the group where companyId = &#63; and classNameId = &#63; and liveGroupId = &#63; and name = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param liveGroupId the live group ID
	 * @param name the name
	 * @param retrieveFromCache whether to use the finder cache
	 * @return the matching group, or <code>null</code> if a matching group could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public Group fetchByC_C_L_N(long companyId, long classNameId,
		long liveGroupId, String name, boolean retrieveFromCache)
		throws SystemException {
		Object[] finderArgs = new Object[] {
				companyId, classNameId, liveGroupId, name
			};

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_C_C_L_N,
					finderArgs, this);
		}

		if (result instanceof Group) {
			Group group = (Group)result;

			if ((companyId != group.getCompanyId()) ||
					(classNameId != group.getClassNameId()) ||
					(liveGroupId != group.getLiveGroupId()) ||
					!Validator.equals(name, group.getName())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(6);

			query.append(_SQL_SELECT_GROUP__WHERE);

			query.append(_FINDER_COLUMN_C_C_L_N_COMPANYID_2);

			query.append(_FINDER_COLUMN_C_C_L_N_CLASSNAMEID_2);

			query.append(_FINDER_COLUMN_C_C_L_N_LIVEGROUPID_2);

			boolean bindName = false;

			if (name == null) {
				query.append(_FINDER_COLUMN_C_C_L_N_NAME_1);
			}
			else if (name.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_C_C_L_N_NAME_3);
			}
			else {
				bindName = true;

				query.append(_FINDER_COLUMN_C_C_L_N_NAME_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				qPos.add(classNameId);

				qPos.add(liveGroupId);

				if (bindName) {
					qPos.add(name);
				}

				List<Group> list = q.list();

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_C_L_N,
						finderArgs, list);
				}
				else {
					Group group = list.get(0);

					result = group;

					cacheResult(group);

					if ((group.getCompanyId() != companyId) ||
							(group.getClassNameId() != classNameId) ||
							(group.getLiveGroupId() != liveGroupId) ||
							(group.getName() == null) ||
							!group.getName().equals(name)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_C_L_N,
							finderArgs, group);
					}
				}
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_C_C_L_N,
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
			return (Group)result;
		}
	}

	/**
	 * Removes the group where companyId = &#63; and classNameId = &#63; and liveGroupId = &#63; and name = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param liveGroupId the live group ID
	 * @param name the name
	 * @return the group that was removed
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public Group removeByC_C_L_N(long companyId, long classNameId,
		long liveGroupId, String name)
		throws NoSuchGroupException, SystemException {
		Group group = findByC_C_L_N(companyId, classNameId, liveGroupId, name);

		return remove(group);
	}

	/**
	 * Returns the number of groups where companyId = &#63; and classNameId = &#63; and liveGroupId = &#63; and name = &#63;.
	 *
	 * @param companyId the company ID
	 * @param classNameId the class name ID
	 * @param liveGroupId the live group ID
	 * @param name the name
	 * @return the number of matching groups
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public int countByC_C_L_N(long companyId, long classNameId,
		long liveGroupId, String name) throws SystemException {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_C_C_L_N;

		Object[] finderArgs = new Object[] {
				companyId, classNameId, liveGroupId, name
			};

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs,
				this);

		if (count == null) {
			StringBundler query = new StringBundler(5);

			query.append(_SQL_COUNT_GROUP__WHERE);

			query.append(_FINDER_COLUMN_C_C_L_N_COMPANYID_2);

			query.append(_FINDER_COLUMN_C_C_L_N_CLASSNAMEID_2);

			query.append(_FINDER_COLUMN_C_C_L_N_LIVEGROUPID_2);

			boolean bindName = false;

			if (name == null) {
				query.append(_FINDER_COLUMN_C_C_L_N_NAME_1);
			}
			else if (name.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_C_C_L_N_NAME_3);
			}
			else {
				bindName = true;

				query.append(_FINDER_COLUMN_C_C_L_N_NAME_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				qPos.add(classNameId);

				qPos.add(liveGroupId);

				if (bindName) {
					qPos.add(name);
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

	private static final String _FINDER_COLUMN_C_C_L_N_COMPANYID_2 = "group_.companyId = ? AND ";
	private static final String _FINDER_COLUMN_C_C_L_N_CLASSNAMEID_2 = "group_.classNameId = ? AND ";
	private static final String _FINDER_COLUMN_C_C_L_N_LIVEGROUPID_2 = "group_.liveGroupId = ? AND ";
	private static final String _FINDER_COLUMN_C_C_L_N_NAME_1 = "group_.name IS NULL";
	private static final String _FINDER_COLUMN_C_C_L_N_NAME_2 = "group_.name = ?";
	private static final String _FINDER_COLUMN_C_C_L_N_NAME_3 = "(group_.name IS NULL OR group_.name = '')";

	public GroupPersistenceImpl() {
		setModelClass(Group.class);
	}

	/**
	 * Caches the group in the entity cache if it is enabled.
	 *
	 * @param group the group
	 */
	@Override
	public void cacheResult(Group group) {
		EntityCacheUtil.putResult(GroupModelImpl.ENTITY_CACHE_ENABLED,
			GroupImpl.class, group.getPrimaryKey(), group);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_UUID_G,
			new Object[] { group.getUuid(), group.getGroupId() }, group);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_LIVEGROUPID,
			new Object[] { group.getLiveGroupId() }, group);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_N,
			new Object[] { group.getCompanyId(), group.getName() }, group);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_F,
			new Object[] { group.getCompanyId(), group.getFriendlyURL() }, group);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_C_C,
			new Object[] {
				group.getCompanyId(), group.getClassNameId(), group.getClassPK()
			}, group);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_L_N,
			new Object[] {
				group.getCompanyId(), group.getLiveGroupId(), group.getName()
			}, group);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_C_L_N,
			new Object[] {
				group.getCompanyId(), group.getClassNameId(),
				group.getLiveGroupId(), group.getName()
			}, group);

		group.resetOriginalValues();
	}

	/**
	 * Caches the groups in the entity cache if it is enabled.
	 *
	 * @param groups the groups
	 */
	@Override
	public void cacheResult(List<Group> groups) {
		for (Group group : groups) {
			if (EntityCacheUtil.getResult(GroupModelImpl.ENTITY_CACHE_ENABLED,
						GroupImpl.class, group.getPrimaryKey()) == null) {
				cacheResult(group);
			}
			else {
				group.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all groups.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		if (_HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			CacheRegistryUtil.clear(GroupImpl.class.getName());
		}

		EntityCacheUtil.clearCache(GroupImpl.class.getName());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the group.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(Group group) {
		EntityCacheUtil.removeResult(GroupModelImpl.ENTITY_CACHE_ENABLED,
			GroupImpl.class, group.getPrimaryKey());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache(group);
	}

	@Override
	public void clearCache(List<Group> groups) {
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (Group group : groups) {
			EntityCacheUtil.removeResult(GroupModelImpl.ENTITY_CACHE_ENABLED,
				GroupImpl.class, group.getPrimaryKey());

			clearUniqueFindersCache(group);
		}
	}

	protected void cacheUniqueFindersCache(Group group) {
		if (group.isNew()) {
			Object[] args = new Object[] { group.getUuid(), group.getGroupId() };

			FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_UUID_G, args,
				Long.valueOf(1));
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_UUID_G, args, group);

			args = new Object[] { group.getLiveGroupId() };

			FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_LIVEGROUPID, args,
				Long.valueOf(1));
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_LIVEGROUPID, args,
				group);

			args = new Object[] { group.getCompanyId(), group.getName() };

			FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_C_N, args,
				Long.valueOf(1));
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_N, args, group);

			args = new Object[] { group.getCompanyId(), group.getFriendlyURL() };

			FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_C_F, args,
				Long.valueOf(1));
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_F, args, group);

			args = new Object[] {
					group.getCompanyId(), group.getClassNameId(),
					group.getClassPK()
				};

			FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_C_C_C, args,
				Long.valueOf(1));
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_C_C, args, group);

			args = new Object[] {
					group.getCompanyId(), group.getLiveGroupId(),
					group.getName()
				};

			FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_C_L_N, args,
				Long.valueOf(1));
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_L_N, args, group);

			args = new Object[] {
					group.getCompanyId(), group.getClassNameId(),
					group.getLiveGroupId(), group.getName()
				};

			FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_C_C_L_N, args,
				Long.valueOf(1));
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_C_L_N, args, group);
		}
		else {
			GroupModelImpl groupModelImpl = (GroupModelImpl)group;

			if ((groupModelImpl.getColumnBitmask() &
					FINDER_PATH_FETCH_BY_UUID_G.getColumnBitmask()) != 0) {
				Object[] args = new Object[] { group.getUuid(), group.getGroupId() };

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_UUID_G, args,
					Long.valueOf(1));
				FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_UUID_G, args,
					group);
			}

			if ((groupModelImpl.getColumnBitmask() &
					FINDER_PATH_FETCH_BY_LIVEGROUPID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] { group.getLiveGroupId() };

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_LIVEGROUPID,
					args, Long.valueOf(1));
				FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_LIVEGROUPID,
					args, group);
			}

			if ((groupModelImpl.getColumnBitmask() &
					FINDER_PATH_FETCH_BY_C_N.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						group.getCompanyId(), group.getName()
					};

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_C_N, args,
					Long.valueOf(1));
				FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_N, args, group);
			}

			if ((groupModelImpl.getColumnBitmask() &
					FINDER_PATH_FETCH_BY_C_F.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						group.getCompanyId(), group.getFriendlyURL()
					};

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_C_F, args,
					Long.valueOf(1));
				FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_F, args, group);
			}

			if ((groupModelImpl.getColumnBitmask() &
					FINDER_PATH_FETCH_BY_C_C_C.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						group.getCompanyId(), group.getClassNameId(),
						group.getClassPK()
					};

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_C_C_C, args,
					Long.valueOf(1));
				FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_C_C, args,
					group);
			}

			if ((groupModelImpl.getColumnBitmask() &
					FINDER_PATH_FETCH_BY_C_L_N.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						group.getCompanyId(), group.getLiveGroupId(),
						group.getName()
					};

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_C_L_N, args,
					Long.valueOf(1));
				FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_L_N, args,
					group);
			}

			if ((groupModelImpl.getColumnBitmask() &
					FINDER_PATH_FETCH_BY_C_C_L_N.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						group.getCompanyId(), group.getClassNameId(),
						group.getLiveGroupId(), group.getName()
					};

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_C_C_L_N, args,
					Long.valueOf(1));
				FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_C_L_N, args,
					group);
			}
		}
	}

	protected void clearUniqueFindersCache(Group group) {
		GroupModelImpl groupModelImpl = (GroupModelImpl)group;

		Object[] args = new Object[] { group.getUuid(), group.getGroupId() };

		FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_UUID_G, args);
		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_UUID_G, args);

		if ((groupModelImpl.getColumnBitmask() &
				FINDER_PATH_FETCH_BY_UUID_G.getColumnBitmask()) != 0) {
			args = new Object[] {
					groupModelImpl.getOriginalUuid(),
					groupModelImpl.getOriginalGroupId()
				};

			FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_UUID_G, args);
			FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_UUID_G, args);
		}

		args = new Object[] { group.getLiveGroupId() };

		FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_LIVEGROUPID, args);
		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_LIVEGROUPID, args);

		if ((groupModelImpl.getColumnBitmask() &
				FINDER_PATH_FETCH_BY_LIVEGROUPID.getColumnBitmask()) != 0) {
			args = new Object[] { groupModelImpl.getOriginalLiveGroupId() };

			FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_LIVEGROUPID, args);
			FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_LIVEGROUPID, args);
		}

		args = new Object[] { group.getCompanyId(), group.getName() };

		FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_C_N, args);
		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_C_N, args);

		if ((groupModelImpl.getColumnBitmask() &
				FINDER_PATH_FETCH_BY_C_N.getColumnBitmask()) != 0) {
			args = new Object[] {
					groupModelImpl.getOriginalCompanyId(),
					groupModelImpl.getOriginalName()
				};

			FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_C_N, args);
			FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_C_N, args);
		}

		args = new Object[] { group.getCompanyId(), group.getFriendlyURL() };

		FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_C_F, args);
		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_C_F, args);

		if ((groupModelImpl.getColumnBitmask() &
				FINDER_PATH_FETCH_BY_C_F.getColumnBitmask()) != 0) {
			args = new Object[] {
					groupModelImpl.getOriginalCompanyId(),
					groupModelImpl.getOriginalFriendlyURL()
				};

			FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_C_F, args);
			FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_C_F, args);
		}

		args = new Object[] {
				group.getCompanyId(), group.getClassNameId(), group.getClassPK()
			};

		FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_C_C_C, args);
		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_C_C_C, args);

		if ((groupModelImpl.getColumnBitmask() &
				FINDER_PATH_FETCH_BY_C_C_C.getColumnBitmask()) != 0) {
			args = new Object[] {
					groupModelImpl.getOriginalCompanyId(),
					groupModelImpl.getOriginalClassNameId(),
					groupModelImpl.getOriginalClassPK()
				};

			FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_C_C_C, args);
			FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_C_C_C, args);
		}

		args = new Object[] {
				group.getCompanyId(), group.getLiveGroupId(), group.getName()
			};

		FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_C_L_N, args);
		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_C_L_N, args);

		if ((groupModelImpl.getColumnBitmask() &
				FINDER_PATH_FETCH_BY_C_L_N.getColumnBitmask()) != 0) {
			args = new Object[] {
					groupModelImpl.getOriginalCompanyId(),
					groupModelImpl.getOriginalLiveGroupId(),
					groupModelImpl.getOriginalName()
				};

			FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_C_L_N, args);
			FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_C_L_N, args);
		}

		args = new Object[] {
				group.getCompanyId(), group.getClassNameId(),
				group.getLiveGroupId(), group.getName()
			};

		FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_C_C_L_N, args);
		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_C_C_L_N, args);

		if ((groupModelImpl.getColumnBitmask() &
				FINDER_PATH_FETCH_BY_C_C_L_N.getColumnBitmask()) != 0) {
			args = new Object[] {
					groupModelImpl.getOriginalCompanyId(),
					groupModelImpl.getOriginalClassNameId(),
					groupModelImpl.getOriginalLiveGroupId(),
					groupModelImpl.getOriginalName()
				};

			FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_C_C_L_N, args);
			FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_C_C_L_N, args);
		}
	}

	/**
	 * Creates a new group with the primary key. Does not add the group to the database.
	 *
	 * @param groupId the primary key for the new group
	 * @return the new group
	 */
	@Override
	public Group create(long groupId) {
		Group group = new GroupImpl();

		group.setNew(true);
		group.setPrimaryKey(groupId);

		String uuid = PortalUUIDUtil.generate();

		group.setUuid(uuid);

		return group;
	}

	/**
	 * Removes the group with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param groupId the primary key of the group
	 * @return the group that was removed
	 * @throws com.liferay.portal.NoSuchGroupException if a group with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public Group remove(long groupId)
		throws NoSuchGroupException, SystemException {
		return remove((Serializable)groupId);
	}

	/**
	 * Removes the group with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the group
	 * @return the group that was removed
	 * @throws com.liferay.portal.NoSuchGroupException if a group with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public Group remove(Serializable primaryKey)
		throws NoSuchGroupException, SystemException {
		Session session = null;

		try {
			session = openSession();

			Group group = (Group)session.get(GroupImpl.class, primaryKey);

			if (group == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchGroupException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(group);
		}
		catch (NoSuchGroupException nsee) {
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
	protected Group removeImpl(Group group) throws SystemException {
		group = toUnwrappedModel(group);

		groupToOrganizationTableMapper.deleteLeftPrimaryKeyTableMappings(group.getPrimaryKey());

		groupToRoleTableMapper.deleteLeftPrimaryKeyTableMappings(group.getPrimaryKey());

		groupToUserGroupTableMapper.deleteLeftPrimaryKeyTableMappings(group.getPrimaryKey());

		groupToUserTableMapper.deleteLeftPrimaryKeyTableMappings(group.getPrimaryKey());

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(group)) {
				group = (Group)session.get(GroupImpl.class,
						group.getPrimaryKeyObj());
			}

			if (group != null) {
				session.delete(group);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (group != null) {
			clearCache(group);
		}

		return group;
	}

	@Override
	public Group updateImpl(com.liferay.portal.model.Group group)
		throws SystemException {
		group = toUnwrappedModel(group);

		boolean isNew = group.isNew();

		GroupModelImpl groupModelImpl = (GroupModelImpl)group;

		if (Validator.isNull(group.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			group.setUuid(uuid);
		}

		Session session = null;

		try {
			session = openSession();

			if (group.isNew()) {
				session.save(group);

				group.setNew(false);
			}
			else {
				session.merge(group);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (isNew || !GroupModelImpl.COLUMN_BITMASK_ENABLED) {
			FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}

		else {
			if ((groupModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] { groupModelImpl.getOriginalUuid() };

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_UUID, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID,
					args);

				args = new Object[] { groupModelImpl.getUuid() };

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_UUID, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID,
					args);
			}

			if ((groupModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						groupModelImpl.getOriginalUuid(),
						groupModelImpl.getOriginalCompanyId()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_UUID_C, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C,
					args);

				args = new Object[] {
						groupModelImpl.getUuid(), groupModelImpl.getCompanyId()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_UUID_C, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C,
					args);
			}

			if ((groupModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						groupModelImpl.getOriginalCompanyId()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_COMPANYID,
					args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID,
					args);

				args = new Object[] { groupModelImpl.getCompanyId() };

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_COMPANYID,
					args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID,
					args);
			}

			if ((groupModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_C.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						groupModelImpl.getOriginalCompanyId(),
						groupModelImpl.getOriginalClassNameId()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_C_C, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_C,
					args);

				args = new Object[] {
						groupModelImpl.getCompanyId(),
						groupModelImpl.getClassNameId()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_C_C, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_C,
					args);
			}

			if ((groupModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_P.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						groupModelImpl.getOriginalCompanyId(),
						groupModelImpl.getOriginalParentGroupId()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_C_P, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_P,
					args);

				args = new Object[] {
						groupModelImpl.getCompanyId(),
						groupModelImpl.getParentGroupId()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_C_P, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_P,
					args);
			}

			if ((groupModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_S.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						groupModelImpl.getOriginalCompanyId(),
						groupModelImpl.getOriginalSite()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_C_S, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_S,
					args);

				args = new Object[] {
						groupModelImpl.getCompanyId(), groupModelImpl.getSite()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_C_S, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_S,
					args);
			}

			if ((groupModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_T_A.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						groupModelImpl.getOriginalType(),
						groupModelImpl.getOriginalActive()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_T_A, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_T_A,
					args);

				args = new Object[] {
						groupModelImpl.getType(), groupModelImpl.getActive()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_T_A, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_T_A,
					args);
			}

			if ((groupModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_C_P.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						groupModelImpl.getOriginalCompanyId(),
						groupModelImpl.getOriginalClassNameId(),
						groupModelImpl.getOriginalParentGroupId()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_C_C_P, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_C_P,
					args);

				args = new Object[] {
						groupModelImpl.getCompanyId(),
						groupModelImpl.getClassNameId(),
						groupModelImpl.getParentGroupId()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_C_C_P, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_C_P,
					args);
			}

			if ((groupModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_P_S.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						groupModelImpl.getOriginalCompanyId(),
						groupModelImpl.getOriginalParentGroupId(),
						groupModelImpl.getOriginalSite()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_C_P_S, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_P_S,
					args);

				args = new Object[] {
						groupModelImpl.getCompanyId(),
						groupModelImpl.getParentGroupId(),
						groupModelImpl.getSite()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_C_P_S, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_P_S,
					args);
			}
		}

		EntityCacheUtil.putResult(GroupModelImpl.ENTITY_CACHE_ENABLED,
			GroupImpl.class, group.getPrimaryKey(), group);

		clearUniqueFindersCache(group);
		cacheUniqueFindersCache(group);

		group.resetOriginalValues();

		return group;
	}

	protected Group toUnwrappedModel(Group group) {
		if (group instanceof GroupImpl) {
			return group;
		}

		GroupImpl groupImpl = new GroupImpl();

		groupImpl.setNew(group.isNew());
		groupImpl.setPrimaryKey(group.getPrimaryKey());

		groupImpl.setUuid(group.getUuid());
		groupImpl.setGroupId(group.getGroupId());
		groupImpl.setCompanyId(group.getCompanyId());
		groupImpl.setCreatorUserId(group.getCreatorUserId());
		groupImpl.setClassNameId(group.getClassNameId());
		groupImpl.setClassPK(group.getClassPK());
		groupImpl.setParentGroupId(group.getParentGroupId());
		groupImpl.setLiveGroupId(group.getLiveGroupId());
		groupImpl.setTreePath(group.getTreePath());
		groupImpl.setName(group.getName());
		groupImpl.setDescription(group.getDescription());
		groupImpl.setType(group.getType());
		groupImpl.setTypeSettings(group.getTypeSettings());
		groupImpl.setManualMembership(group.isManualMembership());
		groupImpl.setMembershipRestriction(group.getMembershipRestriction());
		groupImpl.setFriendlyURL(group.getFriendlyURL());
		groupImpl.setSite(group.isSite());
		groupImpl.setRemoteStagingGroupCount(group.getRemoteStagingGroupCount());
		groupImpl.setActive(group.isActive());
		groupImpl.setMvccVersion(group.getMvccVersion());

		return groupImpl;
	}

	/**
	 * Returns the group with the primary key or throws a {@link com.liferay.portal.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the group
	 * @return the group
	 * @throws com.liferay.portal.NoSuchGroupException if a group with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public Group findByPrimaryKey(Serializable primaryKey)
		throws NoSuchGroupException, SystemException {
		Group group = fetchByPrimaryKey(primaryKey);

		if (group == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchGroupException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				primaryKey);
		}

		return group;
	}

	/**
	 * Returns the group with the primary key or throws a {@link com.liferay.portal.NoSuchGroupException} if it could not be found.
	 *
	 * @param groupId the primary key of the group
	 * @return the group
	 * @throws com.liferay.portal.NoSuchGroupException if a group with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public Group findByPrimaryKey(long groupId)
		throws NoSuchGroupException, SystemException {
		return findByPrimaryKey((Serializable)groupId);
	}

	/**
	 * Returns the group with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the group
	 * @return the group, or <code>null</code> if a group with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public Group fetchByPrimaryKey(Serializable primaryKey)
		throws SystemException {
		Group group = (Group)EntityCacheUtil.getResult(GroupModelImpl.ENTITY_CACHE_ENABLED,
				GroupImpl.class, primaryKey);

		if (group == _nullGroup) {
			return null;
		}

		if (group == null) {
			Session session = null;

			try {
				session = openSession();

				group = (Group)session.get(GroupImpl.class, primaryKey);

				if (group != null) {
					cacheResult(group);
				}
				else {
					EntityCacheUtil.putResult(GroupModelImpl.ENTITY_CACHE_ENABLED,
						GroupImpl.class, primaryKey, _nullGroup);
				}
			}
			catch (Exception e) {
				EntityCacheUtil.removeResult(GroupModelImpl.ENTITY_CACHE_ENABLED,
					GroupImpl.class, primaryKey);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return group;
	}

	/**
	 * Returns the group with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param groupId the primary key of the group
	 * @return the group, or <code>null</code> if a group with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public Group fetchByPrimaryKey(long groupId) throws SystemException {
		return fetchByPrimaryKey((Serializable)groupId);
	}

	/**
	 * Returns all the groups.
	 *
	 * @return the groups
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<Group> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the groups.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.GroupModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of groups
	 * @param end the upper bound of the range of groups (not inclusive)
	 * @return the range of groups
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<Group> findAll(int start, int end) throws SystemException {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the groups.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.GroupModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of groups
	 * @param end the upper bound of the range of groups (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of groups
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<Group> findAll(int start, int end,
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

		List<Group> list = (List<Group>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 3));

				query.append(_SQL_SELECT_GROUP_);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_GROUP_;

				if (pagination) {
					sql = sql.concat(GroupModelImpl.ORDER_BY_JPQL);
				}
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (!pagination) {
					list = (List<Group>)QueryUtil.list(q, getDialect(), start,
							end, false);

					Collections.sort(list);

					list = new UnmodifiableList<Group>(list);
				}
				else {
					list = (List<Group>)QueryUtil.list(q, getDialect(), start,
							end);
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
	 * Removes all the groups from the database.
	 *
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public void removeAll() throws SystemException {
		for (Group group : findAll()) {
			remove(group);
		}
	}

	/**
	 * Returns the number of groups.
	 *
	 * @return the number of groups
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public int countAll() throws SystemException {
		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_GROUP_);

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
	 * Returns all the organizations associated with the group.
	 *
	 * @param pk the primary key of the group
	 * @return the organizations associated with the group
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<com.liferay.portal.model.Organization> getOrganizations(long pk)
		throws SystemException {
		return getOrganizations(pk, QueryUtil.ALL_POS, QueryUtil.ALL_POS);
	}

	/**
	 * Returns a range of all the organizations associated with the group.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.GroupModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param pk the primary key of the group
	 * @param start the lower bound of the range of groups
	 * @param end the upper bound of the range of groups (not inclusive)
	 * @return the range of organizations associated with the group
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<com.liferay.portal.model.Organization> getOrganizations(
		long pk, int start, int end) throws SystemException {
		return getOrganizations(pk, start, end, null);
	}

	/**
	 * Returns an ordered range of all the organizations associated with the group.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.GroupModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param pk the primary key of the group
	 * @param start the lower bound of the range of groups
	 * @param end the upper bound of the range of groups (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of organizations associated with the group
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<com.liferay.portal.model.Organization> getOrganizations(
		long pk, int start, int end, OrderByComparator orderByComparator)
		throws SystemException {
		return groupToOrganizationTableMapper.getRightBaseModels(pk, start,
			end, orderByComparator);
	}

	/**
	 * Returns the number of organizations associated with the group.
	 *
	 * @param pk the primary key of the group
	 * @return the number of organizations associated with the group
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public int getOrganizationsSize(long pk) throws SystemException {
		long[] pks = groupToOrganizationTableMapper.getRightPrimaryKeys(pk);

		return pks.length;
	}

	/**
	 * Returns <code>true</code> if the organization is associated with the group.
	 *
	 * @param pk the primary key of the group
	 * @param organizationPK the primary key of the organization
	 * @return <code>true</code> if the organization is associated with the group; <code>false</code> otherwise
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public boolean containsOrganization(long pk, long organizationPK)
		throws SystemException {
		return groupToOrganizationTableMapper.containsTableMapping(pk,
			organizationPK);
	}

	/**
	 * Returns <code>true</code> if the group has any organizations associated with it.
	 *
	 * @param pk the primary key of the group to check for associations with organizations
	 * @return <code>true</code> if the group has any organizations associated with it; <code>false</code> otherwise
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public boolean containsOrganizations(long pk) throws SystemException {
		if (getOrganizationsSize(pk) > 0) {
			return true;
		}
		else {
			return false;
		}
	}

	/**
	 * Adds an association between the group and the organization. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the group
	 * @param organizationPK the primary key of the organization
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public void addOrganization(long pk, long organizationPK)
		throws SystemException {
		groupToOrganizationTableMapper.addTableMapping(pk, organizationPK);
	}

	/**
	 * Adds an association between the group and the organization. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the group
	 * @param organization the organization
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public void addOrganization(long pk,
		com.liferay.portal.model.Organization organization)
		throws SystemException {
		groupToOrganizationTableMapper.addTableMapping(pk,
			organization.getPrimaryKey());
	}

	/**
	 * Adds an association between the group and the organizations. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the group
	 * @param organizationPKs the primary keys of the organizations
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public void addOrganizations(long pk, long[] organizationPKs)
		throws SystemException {
		for (long organizationPK : organizationPKs) {
			groupToOrganizationTableMapper.addTableMapping(pk, organizationPK);
		}
	}

	/**
	 * Adds an association between the group and the organizations. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the group
	 * @param organizations the organizations
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public void addOrganizations(long pk,
		List<com.liferay.portal.model.Organization> organizations)
		throws SystemException {
		for (com.liferay.portal.model.Organization organization : organizations) {
			groupToOrganizationTableMapper.addTableMapping(pk,
				organization.getPrimaryKey());
		}
	}

	/**
	 * Clears all associations between the group and its organizations. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the group to clear the associated organizations from
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public void clearOrganizations(long pk) throws SystemException {
		groupToOrganizationTableMapper.deleteLeftPrimaryKeyTableMappings(pk);
	}

	/**
	 * Removes the association between the group and the organization. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the group
	 * @param organizationPK the primary key of the organization
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public void removeOrganization(long pk, long organizationPK)
		throws SystemException {
		groupToOrganizationTableMapper.deleteTableMapping(pk, organizationPK);
	}

	/**
	 * Removes the association between the group and the organization. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the group
	 * @param organization the organization
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public void removeOrganization(long pk,
		com.liferay.portal.model.Organization organization)
		throws SystemException {
		groupToOrganizationTableMapper.deleteTableMapping(pk,
			organization.getPrimaryKey());
	}

	/**
	 * Removes the association between the group and the organizations. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the group
	 * @param organizationPKs the primary keys of the organizations
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public void removeOrganizations(long pk, long[] organizationPKs)
		throws SystemException {
		for (long organizationPK : organizationPKs) {
			groupToOrganizationTableMapper.deleteTableMapping(pk, organizationPK);
		}
	}

	/**
	 * Removes the association between the group and the organizations. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the group
	 * @param organizations the organizations
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public void removeOrganizations(long pk,
		List<com.liferay.portal.model.Organization> organizations)
		throws SystemException {
		for (com.liferay.portal.model.Organization organization : organizations) {
			groupToOrganizationTableMapper.deleteTableMapping(pk,
				organization.getPrimaryKey());
		}
	}

	/**
	 * Sets the organizations associated with the group, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the group
	 * @param organizationPKs the primary keys of the organizations to be associated with the group
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public void setOrganizations(long pk, long[] organizationPKs)
		throws SystemException {
		Set<Long> newOrganizationPKsSet = SetUtil.fromArray(organizationPKs);
		Set<Long> oldOrganizationPKsSet = SetUtil.fromArray(groupToOrganizationTableMapper.getRightPrimaryKeys(
					pk));

		Set<Long> removeOrganizationPKsSet = new HashSet<Long>(oldOrganizationPKsSet);

		removeOrganizationPKsSet.removeAll(newOrganizationPKsSet);

		for (long removeOrganizationPK : removeOrganizationPKsSet) {
			groupToOrganizationTableMapper.deleteTableMapping(pk,
				removeOrganizationPK);
		}

		newOrganizationPKsSet.removeAll(oldOrganizationPKsSet);

		for (long newOrganizationPK : newOrganizationPKsSet) {
			groupToOrganizationTableMapper.addTableMapping(pk, newOrganizationPK);
		}
	}

	/**
	 * Sets the organizations associated with the group, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the group
	 * @param organizations the organizations to be associated with the group
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public void setOrganizations(long pk,
		List<com.liferay.portal.model.Organization> organizations)
		throws SystemException {
		try {
			long[] organizationPKs = new long[organizations.size()];

			for (int i = 0; i < organizations.size(); i++) {
				com.liferay.portal.model.Organization organization = organizations.get(i);

				organizationPKs[i] = organization.getPrimaryKey();
			}

			setOrganizations(pk, organizationPKs);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(GroupModelImpl.MAPPING_TABLE_GROUPS_ORGS_NAME);
		}
	}

	/**
	 * Returns all the roles associated with the group.
	 *
	 * @param pk the primary key of the group
	 * @return the roles associated with the group
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<com.liferay.portal.model.Role> getRoles(long pk)
		throws SystemException {
		return getRoles(pk, QueryUtil.ALL_POS, QueryUtil.ALL_POS);
	}

	/**
	 * Returns a range of all the roles associated with the group.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.GroupModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param pk the primary key of the group
	 * @param start the lower bound of the range of groups
	 * @param end the upper bound of the range of groups (not inclusive)
	 * @return the range of roles associated with the group
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<com.liferay.portal.model.Role> getRoles(long pk, int start,
		int end) throws SystemException {
		return getRoles(pk, start, end, null);
	}

	/**
	 * Returns an ordered range of all the roles associated with the group.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.GroupModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param pk the primary key of the group
	 * @param start the lower bound of the range of groups
	 * @param end the upper bound of the range of groups (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of roles associated with the group
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<com.liferay.portal.model.Role> getRoles(long pk, int start,
		int end, OrderByComparator orderByComparator) throws SystemException {
		return groupToRoleTableMapper.getRightBaseModels(pk, start, end,
			orderByComparator);
	}

	/**
	 * Returns the number of roles associated with the group.
	 *
	 * @param pk the primary key of the group
	 * @return the number of roles associated with the group
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public int getRolesSize(long pk) throws SystemException {
		long[] pks = groupToRoleTableMapper.getRightPrimaryKeys(pk);

		return pks.length;
	}

	/**
	 * Returns <code>true</code> if the role is associated with the group.
	 *
	 * @param pk the primary key of the group
	 * @param rolePK the primary key of the role
	 * @return <code>true</code> if the role is associated with the group; <code>false</code> otherwise
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public boolean containsRole(long pk, long rolePK) throws SystemException {
		return groupToRoleTableMapper.containsTableMapping(pk, rolePK);
	}

	/**
	 * Returns <code>true</code> if the group has any roles associated with it.
	 *
	 * @param pk the primary key of the group to check for associations with roles
	 * @return <code>true</code> if the group has any roles associated with it; <code>false</code> otherwise
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public boolean containsRoles(long pk) throws SystemException {
		if (getRolesSize(pk) > 0) {
			return true;
		}
		else {
			return false;
		}
	}

	/**
	 * Adds an association between the group and the role. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the group
	 * @param rolePK the primary key of the role
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public void addRole(long pk, long rolePK) throws SystemException {
		groupToRoleTableMapper.addTableMapping(pk, rolePK);
	}

	/**
	 * Adds an association between the group and the role. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the group
	 * @param role the role
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public void addRole(long pk, com.liferay.portal.model.Role role)
		throws SystemException {
		groupToRoleTableMapper.addTableMapping(pk, role.getPrimaryKey());
	}

	/**
	 * Adds an association between the group and the roles. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the group
	 * @param rolePKs the primary keys of the roles
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public void addRoles(long pk, long[] rolePKs) throws SystemException {
		for (long rolePK : rolePKs) {
			groupToRoleTableMapper.addTableMapping(pk, rolePK);
		}
	}

	/**
	 * Adds an association between the group and the roles. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the group
	 * @param roles the roles
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public void addRoles(long pk, List<com.liferay.portal.model.Role> roles)
		throws SystemException {
		for (com.liferay.portal.model.Role role : roles) {
			groupToRoleTableMapper.addTableMapping(pk, role.getPrimaryKey());
		}
	}

	/**
	 * Clears all associations between the group and its roles. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the group to clear the associated roles from
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public void clearRoles(long pk) throws SystemException {
		groupToRoleTableMapper.deleteLeftPrimaryKeyTableMappings(pk);
	}

	/**
	 * Removes the association between the group and the role. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the group
	 * @param rolePK the primary key of the role
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public void removeRole(long pk, long rolePK) throws SystemException {
		groupToRoleTableMapper.deleteTableMapping(pk, rolePK);
	}

	/**
	 * Removes the association between the group and the role. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the group
	 * @param role the role
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public void removeRole(long pk, com.liferay.portal.model.Role role)
		throws SystemException {
		groupToRoleTableMapper.deleteTableMapping(pk, role.getPrimaryKey());
	}

	/**
	 * Removes the association between the group and the roles. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the group
	 * @param rolePKs the primary keys of the roles
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public void removeRoles(long pk, long[] rolePKs) throws SystemException {
		for (long rolePK : rolePKs) {
			groupToRoleTableMapper.deleteTableMapping(pk, rolePK);
		}
	}

	/**
	 * Removes the association between the group and the roles. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the group
	 * @param roles the roles
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public void removeRoles(long pk, List<com.liferay.portal.model.Role> roles)
		throws SystemException {
		for (com.liferay.portal.model.Role role : roles) {
			groupToRoleTableMapper.deleteTableMapping(pk, role.getPrimaryKey());
		}
	}

	/**
	 * Sets the roles associated with the group, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the group
	 * @param rolePKs the primary keys of the roles to be associated with the group
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public void setRoles(long pk, long[] rolePKs) throws SystemException {
		Set<Long> newRolePKsSet = SetUtil.fromArray(rolePKs);
		Set<Long> oldRolePKsSet = SetUtil.fromArray(groupToRoleTableMapper.getRightPrimaryKeys(
					pk));

		Set<Long> removeRolePKsSet = new HashSet<Long>(oldRolePKsSet);

		removeRolePKsSet.removeAll(newRolePKsSet);

		for (long removeRolePK : removeRolePKsSet) {
			groupToRoleTableMapper.deleteTableMapping(pk, removeRolePK);
		}

		newRolePKsSet.removeAll(oldRolePKsSet);

		for (long newRolePK : newRolePKsSet) {
			groupToRoleTableMapper.addTableMapping(pk, newRolePK);
		}
	}

	/**
	 * Sets the roles associated with the group, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the group
	 * @param roles the roles to be associated with the group
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public void setRoles(long pk, List<com.liferay.portal.model.Role> roles)
		throws SystemException {
		try {
			long[] rolePKs = new long[roles.size()];

			for (int i = 0; i < roles.size(); i++) {
				com.liferay.portal.model.Role role = roles.get(i);

				rolePKs[i] = role.getPrimaryKey();
			}

			setRoles(pk, rolePKs);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(GroupModelImpl.MAPPING_TABLE_GROUPS_ROLES_NAME);
		}
	}

	/**
	 * Returns all the user groups associated with the group.
	 *
	 * @param pk the primary key of the group
	 * @return the user groups associated with the group
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<com.liferay.portal.model.UserGroup> getUserGroups(long pk)
		throws SystemException {
		return getUserGroups(pk, QueryUtil.ALL_POS, QueryUtil.ALL_POS);
	}

	/**
	 * Returns a range of all the user groups associated with the group.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.GroupModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param pk the primary key of the group
	 * @param start the lower bound of the range of groups
	 * @param end the upper bound of the range of groups (not inclusive)
	 * @return the range of user groups associated with the group
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<com.liferay.portal.model.UserGroup> getUserGroups(long pk,
		int start, int end) throws SystemException {
		return getUserGroups(pk, start, end, null);
	}

	/**
	 * Returns an ordered range of all the user groups associated with the group.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.GroupModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param pk the primary key of the group
	 * @param start the lower bound of the range of groups
	 * @param end the upper bound of the range of groups (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of user groups associated with the group
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<com.liferay.portal.model.UserGroup> getUserGroups(long pk,
		int start, int end, OrderByComparator orderByComparator)
		throws SystemException {
		return groupToUserGroupTableMapper.getRightBaseModels(pk, start, end,
			orderByComparator);
	}

	/**
	 * Returns the number of user groups associated with the group.
	 *
	 * @param pk the primary key of the group
	 * @return the number of user groups associated with the group
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public int getUserGroupsSize(long pk) throws SystemException {
		long[] pks = groupToUserGroupTableMapper.getRightPrimaryKeys(pk);

		return pks.length;
	}

	/**
	 * Returns <code>true</code> if the user group is associated with the group.
	 *
	 * @param pk the primary key of the group
	 * @param userGroupPK the primary key of the user group
	 * @return <code>true</code> if the user group is associated with the group; <code>false</code> otherwise
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public boolean containsUserGroup(long pk, long userGroupPK)
		throws SystemException {
		return groupToUserGroupTableMapper.containsTableMapping(pk, userGroupPK);
	}

	/**
	 * Returns <code>true</code> if the group has any user groups associated with it.
	 *
	 * @param pk the primary key of the group to check for associations with user groups
	 * @return <code>true</code> if the group has any user groups associated with it; <code>false</code> otherwise
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public boolean containsUserGroups(long pk) throws SystemException {
		if (getUserGroupsSize(pk) > 0) {
			return true;
		}
		else {
			return false;
		}
	}

	/**
	 * Adds an association between the group and the user group. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the group
	 * @param userGroupPK the primary key of the user group
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public void addUserGroup(long pk, long userGroupPK)
		throws SystemException {
		groupToUserGroupTableMapper.addTableMapping(pk, userGroupPK);
	}

	/**
	 * Adds an association between the group and the user group. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the group
	 * @param userGroup the user group
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public void addUserGroup(long pk,
		com.liferay.portal.model.UserGroup userGroup) throws SystemException {
		groupToUserGroupTableMapper.addTableMapping(pk,
			userGroup.getPrimaryKey());
	}

	/**
	 * Adds an association between the group and the user groups. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the group
	 * @param userGroupPKs the primary keys of the user groups
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public void addUserGroups(long pk, long[] userGroupPKs)
		throws SystemException {
		for (long userGroupPK : userGroupPKs) {
			groupToUserGroupTableMapper.addTableMapping(pk, userGroupPK);
		}
	}

	/**
	 * Adds an association between the group and the user groups. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the group
	 * @param userGroups the user groups
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public void addUserGroups(long pk,
		List<com.liferay.portal.model.UserGroup> userGroups)
		throws SystemException {
		for (com.liferay.portal.model.UserGroup userGroup : userGroups) {
			groupToUserGroupTableMapper.addTableMapping(pk,
				userGroup.getPrimaryKey());
		}
	}

	/**
	 * Clears all associations between the group and its user groups. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the group to clear the associated user groups from
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public void clearUserGroups(long pk) throws SystemException {
		groupToUserGroupTableMapper.deleteLeftPrimaryKeyTableMappings(pk);
	}

	/**
	 * Removes the association between the group and the user group. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the group
	 * @param userGroupPK the primary key of the user group
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public void removeUserGroup(long pk, long userGroupPK)
		throws SystemException {
		groupToUserGroupTableMapper.deleteTableMapping(pk, userGroupPK);
	}

	/**
	 * Removes the association between the group and the user group. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the group
	 * @param userGroup the user group
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public void removeUserGroup(long pk,
		com.liferay.portal.model.UserGroup userGroup) throws SystemException {
		groupToUserGroupTableMapper.deleteTableMapping(pk,
			userGroup.getPrimaryKey());
	}

	/**
	 * Removes the association between the group and the user groups. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the group
	 * @param userGroupPKs the primary keys of the user groups
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public void removeUserGroups(long pk, long[] userGroupPKs)
		throws SystemException {
		for (long userGroupPK : userGroupPKs) {
			groupToUserGroupTableMapper.deleteTableMapping(pk, userGroupPK);
		}
	}

	/**
	 * Removes the association between the group and the user groups. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the group
	 * @param userGroups the user groups
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public void removeUserGroups(long pk,
		List<com.liferay.portal.model.UserGroup> userGroups)
		throws SystemException {
		for (com.liferay.portal.model.UserGroup userGroup : userGroups) {
			groupToUserGroupTableMapper.deleteTableMapping(pk,
				userGroup.getPrimaryKey());
		}
	}

	/**
	 * Sets the user groups associated with the group, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the group
	 * @param userGroupPKs the primary keys of the user groups to be associated with the group
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public void setUserGroups(long pk, long[] userGroupPKs)
		throws SystemException {
		Set<Long> newUserGroupPKsSet = SetUtil.fromArray(userGroupPKs);
		Set<Long> oldUserGroupPKsSet = SetUtil.fromArray(groupToUserGroupTableMapper.getRightPrimaryKeys(
					pk));

		Set<Long> removeUserGroupPKsSet = new HashSet<Long>(oldUserGroupPKsSet);

		removeUserGroupPKsSet.removeAll(newUserGroupPKsSet);

		for (long removeUserGroupPK : removeUserGroupPKsSet) {
			groupToUserGroupTableMapper.deleteTableMapping(pk, removeUserGroupPK);
		}

		newUserGroupPKsSet.removeAll(oldUserGroupPKsSet);

		for (long newUserGroupPK : newUserGroupPKsSet) {
			groupToUserGroupTableMapper.addTableMapping(pk, newUserGroupPK);
		}
	}

	/**
	 * Sets the user groups associated with the group, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the group
	 * @param userGroups the user groups to be associated with the group
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public void setUserGroups(long pk,
		List<com.liferay.portal.model.UserGroup> userGroups)
		throws SystemException {
		try {
			long[] userGroupPKs = new long[userGroups.size()];

			for (int i = 0; i < userGroups.size(); i++) {
				com.liferay.portal.model.UserGroup userGroup = userGroups.get(i);

				userGroupPKs[i] = userGroup.getPrimaryKey();
			}

			setUserGroups(pk, userGroupPKs);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(GroupModelImpl.MAPPING_TABLE_GROUPS_USERGROUPS_NAME);
		}
	}

	/**
	 * Returns all the users associated with the group.
	 *
	 * @param pk the primary key of the group
	 * @return the users associated with the group
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<com.liferay.portal.model.User> getUsers(long pk)
		throws SystemException {
		return getUsers(pk, QueryUtil.ALL_POS, QueryUtil.ALL_POS);
	}

	/**
	 * Returns a range of all the users associated with the group.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.GroupModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param pk the primary key of the group
	 * @param start the lower bound of the range of groups
	 * @param end the upper bound of the range of groups (not inclusive)
	 * @return the range of users associated with the group
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<com.liferay.portal.model.User> getUsers(long pk, int start,
		int end) throws SystemException {
		return getUsers(pk, start, end, null);
	}

	/**
	 * Returns an ordered range of all the users associated with the group.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.GroupModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param pk the primary key of the group
	 * @param start the lower bound of the range of groups
	 * @param end the upper bound of the range of groups (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of users associated with the group
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<com.liferay.portal.model.User> getUsers(long pk, int start,
		int end, OrderByComparator orderByComparator) throws SystemException {
		return groupToUserTableMapper.getRightBaseModels(pk, start, end,
			orderByComparator);
	}

	/**
	 * Returns the number of users associated with the group.
	 *
	 * @param pk the primary key of the group
	 * @return the number of users associated with the group
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public int getUsersSize(long pk) throws SystemException {
		long[] pks = groupToUserTableMapper.getRightPrimaryKeys(pk);

		return pks.length;
	}

	/**
	 * Returns <code>true</code> if the user is associated with the group.
	 *
	 * @param pk the primary key of the group
	 * @param userPK the primary key of the user
	 * @return <code>true</code> if the user is associated with the group; <code>false</code> otherwise
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public boolean containsUser(long pk, long userPK) throws SystemException {
		return groupToUserTableMapper.containsTableMapping(pk, userPK);
	}

	/**
	 * Returns <code>true</code> if the group has any users associated with it.
	 *
	 * @param pk the primary key of the group to check for associations with users
	 * @return <code>true</code> if the group has any users associated with it; <code>false</code> otherwise
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public boolean containsUsers(long pk) throws SystemException {
		if (getUsersSize(pk) > 0) {
			return true;
		}
		else {
			return false;
		}
	}

	/**
	 * Adds an association between the group and the user. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the group
	 * @param userPK the primary key of the user
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public void addUser(long pk, long userPK) throws SystemException {
		groupToUserTableMapper.addTableMapping(pk, userPK);
	}

	/**
	 * Adds an association between the group and the user. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the group
	 * @param user the user
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public void addUser(long pk, com.liferay.portal.model.User user)
		throws SystemException {
		groupToUserTableMapper.addTableMapping(pk, user.getPrimaryKey());
	}

	/**
	 * Adds an association between the group and the users. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the group
	 * @param userPKs the primary keys of the users
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public void addUsers(long pk, long[] userPKs) throws SystemException {
		for (long userPK : userPKs) {
			groupToUserTableMapper.addTableMapping(pk, userPK);
		}
	}

	/**
	 * Adds an association between the group and the users. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the group
	 * @param users the users
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public void addUsers(long pk, List<com.liferay.portal.model.User> users)
		throws SystemException {
		for (com.liferay.portal.model.User user : users) {
			groupToUserTableMapper.addTableMapping(pk, user.getPrimaryKey());
		}
	}

	/**
	 * Clears all associations between the group and its users. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the group to clear the associated users from
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public void clearUsers(long pk) throws SystemException {
		groupToUserTableMapper.deleteLeftPrimaryKeyTableMappings(pk);
	}

	/**
	 * Removes the association between the group and the user. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the group
	 * @param userPK the primary key of the user
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public void removeUser(long pk, long userPK) throws SystemException {
		groupToUserTableMapper.deleteTableMapping(pk, userPK);
	}

	/**
	 * Removes the association between the group and the user. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the group
	 * @param user the user
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public void removeUser(long pk, com.liferay.portal.model.User user)
		throws SystemException {
		groupToUserTableMapper.deleteTableMapping(pk, user.getPrimaryKey());
	}

	/**
	 * Removes the association between the group and the users. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the group
	 * @param userPKs the primary keys of the users
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public void removeUsers(long pk, long[] userPKs) throws SystemException {
		for (long userPK : userPKs) {
			groupToUserTableMapper.deleteTableMapping(pk, userPK);
		}
	}

	/**
	 * Removes the association between the group and the users. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the group
	 * @param users the users
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public void removeUsers(long pk, List<com.liferay.portal.model.User> users)
		throws SystemException {
		for (com.liferay.portal.model.User user : users) {
			groupToUserTableMapper.deleteTableMapping(pk, user.getPrimaryKey());
		}
	}

	/**
	 * Sets the users associated with the group, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the group
	 * @param userPKs the primary keys of the users to be associated with the group
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public void setUsers(long pk, long[] userPKs) throws SystemException {
		Set<Long> newUserPKsSet = SetUtil.fromArray(userPKs);
		Set<Long> oldUserPKsSet = SetUtil.fromArray(groupToUserTableMapper.getRightPrimaryKeys(
					pk));

		Set<Long> removeUserPKsSet = new HashSet<Long>(oldUserPKsSet);

		removeUserPKsSet.removeAll(newUserPKsSet);

		for (long removeUserPK : removeUserPKsSet) {
			groupToUserTableMapper.deleteTableMapping(pk, removeUserPK);
		}

		newUserPKsSet.removeAll(oldUserPKsSet);

		for (long newUserPK : newUserPKsSet) {
			groupToUserTableMapper.addTableMapping(pk, newUserPK);
		}
	}

	/**
	 * Sets the users associated with the group, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the group
	 * @param users the users to be associated with the group
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public void setUsers(long pk, List<com.liferay.portal.model.User> users)
		throws SystemException {
		try {
			long[] userPKs = new long[users.size()];

			for (int i = 0; i < users.size(); i++) {
				com.liferay.portal.model.User user = users.get(i);

				userPKs[i] = user.getPrimaryKey();
			}

			setUsers(pk, userPKs);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(GroupModelImpl.MAPPING_TABLE_USERS_GROUPS_NAME);
		}
	}

	@Override
	protected Set<String> getBadColumnNames() {
		return _badColumnNames;
	}

	/**
	 * Initializes the group persistence.
	 */
	public void afterPropertiesSet() {
		String[] listenerClassNames = StringUtil.split(GetterUtil.getString(
					com.liferay.portal.util.PropsUtil.get(
						"value.object.listener.com.liferay.portal.model.Group")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<Group>> listenersList = new ArrayList<ModelListener<Group>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<Group>)InstanceFactory.newInstance(
							getClassLoader(), listenerClassName));
				}

				listeners = listenersList.toArray(new ModelListener[listenersList.size()]);
			}
			catch (Exception e) {
				_log.error(e);
			}
		}

		groupToOrganizationTableMapper = TableMapperFactory.getTableMapper("Groups_Orgs",
				"groupId", "organizationId", this, organizationPersistence);

		groupToRoleTableMapper = TableMapperFactory.getTableMapper("Groups_Roles",
				"groupId", "roleId", this, rolePersistence);

		groupToUserGroupTableMapper = TableMapperFactory.getTableMapper("Groups_UserGroups",
				"groupId", "userGroupId", this, userGroupPersistence);

		groupToUserTableMapper = TableMapperFactory.getTableMapper("Users_Groups",
				"groupId", "userId", this, userPersistence);
	}

	public void destroy() {
		EntityCacheUtil.removeCache(GroupImpl.class.getName());
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@BeanReference(type = OrganizationPersistence.class)
	protected OrganizationPersistence organizationPersistence;
	protected TableMapper<Group, com.liferay.portal.model.Organization> groupToOrganizationTableMapper;
	@BeanReference(type = RolePersistence.class)
	protected RolePersistence rolePersistence;
	protected TableMapper<Group, com.liferay.portal.model.Role> groupToRoleTableMapper;
	@BeanReference(type = UserGroupPersistence.class)
	protected UserGroupPersistence userGroupPersistence;
	protected TableMapper<Group, com.liferay.portal.model.UserGroup> groupToUserGroupTableMapper;
	@BeanReference(type = UserPersistence.class)
	protected UserPersistence userPersistence;
	protected TableMapper<Group, com.liferay.portal.model.User> groupToUserTableMapper;
	private static final String _SQL_SELECT_GROUP_ = "SELECT group_ FROM Group group_";
	private static final String _SQL_SELECT_GROUP__WHERE = "SELECT group_ FROM Group group_ WHERE ";
	private static final String _SQL_COUNT_GROUP_ = "SELECT COUNT(group_) FROM Group group_";
	private static final String _SQL_COUNT_GROUP__WHERE = "SELECT COUNT(group_) FROM Group group_ WHERE ";
	private static final String _ORDER_BY_ENTITY_ALIAS = "group_.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No Group exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No Group exists with the key {";
	private static final boolean _HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE = com.liferay.portal.util.PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE;
	private static Log _log = LogFactoryUtil.getLog(GroupPersistenceImpl.class);
	private static Set<String> _badColumnNames = SetUtil.fromArray(new String[] {
				"uuid", "type", "active"
			});
	private static Group _nullGroup = new GroupImpl() {
			@Override
			public Object clone() {
				return this;
			}

			@Override
			public CacheModel<Group> toCacheModel() {
				return _nullGroupCacheModel;
			}
		};

	private static CacheModel<Group> _nullGroupCacheModel = new CacheModel<Group>() {
			@Override
			public Group toEntityModel() {
				return _nullGroup;
			}
		};
}