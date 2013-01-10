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

package com.liferay.portal.service.persistence;

import com.liferay.portal.NoSuchUserException;
import com.liferay.portal.kernel.bean.BeanReference;
import com.liferay.portal.kernel.cache.CacheRegistryUtil;
import com.liferay.portal.kernel.dao.jdbc.MappingSqlQuery;
import com.liferay.portal.kernel.dao.jdbc.MappingSqlQueryFactoryUtil;
import com.liferay.portal.kernel.dao.jdbc.RowMapper;
import com.liferay.portal.kernel.dao.jdbc.SqlUpdate;
import com.liferay.portal.kernel.dao.jdbc.SqlUpdateFactoryUtil;
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
import com.liferay.portal.kernel.util.CalendarUtil;
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
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.model.User;
import com.liferay.portal.model.impl.UserImpl;
import com.liferay.portal.model.impl.UserModelImpl;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * The persistence implementation for the user service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see UserPersistence
 * @see UserUtil
 * @generated
 */
public class UserPersistenceImpl extends BasePersistenceImpl<User>
	implements UserPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link UserUtil} to access the user persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = UserImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(UserModelImpl.ENTITY_CACHE_ENABLED,
			UserModelImpl.FINDER_CACHE_ENABLED, UserImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(UserModelImpl.ENTITY_CACHE_ENABLED,
			UserModelImpl.FINDER_CACHE_ENABLED, UserImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(UserModelImpl.ENTITY_CACHE_ENABLED,
			UserModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_UUID = new FinderPath(UserModelImpl.ENTITY_CACHE_ENABLED,
			UserModelImpl.FINDER_CACHE_ENABLED, UserImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUuid",
			new String[] {
				String.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID = new FinderPath(UserModelImpl.ENTITY_CACHE_ENABLED,
			UserModelImpl.FINDER_CACHE_ENABLED, UserImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid",
			new String[] { String.class.getName() },
			UserModelImpl.UUID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID = new FinderPath(UserModelImpl.ENTITY_CACHE_ENABLED,
			UserModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUuid",
			new String[] { String.class.getName() });

	/**
	 * Returns all the users where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching users
	 * @throws SystemException if a system exception occurred
	 */
	public List<User> findByUuid(String uuid) throws SystemException {
		return findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the users where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.UserModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of users
	 * @param end the upper bound of the range of users (not inclusive)
	 * @return the range of matching users
	 * @throws SystemException if a system exception occurred
	 */
	public List<User> findByUuid(String uuid, int start, int end)
		throws SystemException {
		return findByUuid(uuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the users where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.UserModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of users
	 * @param end the upper bound of the range of users (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching users
	 * @throws SystemException if a system exception occurred
	 */
	public List<User> findByUuid(String uuid, int start, int end,
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

		List<User> list = (List<User>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (User user : list) {
				if (!Validator.equals(uuid, user.getUuid())) {
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

			query.append(_SQL_SELECT_USER_WHERE);

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
				query.append(UserModelImpl.ORDER_BY_JPQL);
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
					list = (List<User>)QueryUtil.list(q, getDialect(), start,
							end, false);

					Collections.sort(list);

					list = new UnmodifiableList<User>(list);
				}
				else {
					list = (List<User>)QueryUtil.list(q, getDialect(), start,
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
	 * Returns the first user in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching user
	 * @throws com.liferay.portal.NoSuchUserException if a matching user could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public User findByUuid_First(String uuid,
		OrderByComparator orderByComparator)
		throws NoSuchUserException, SystemException {
		User user = fetchByUuid_First(uuid, orderByComparator);

		if (user != null) {
			return user;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchUserException(msg.toString());
	}

	/**
	 * Returns the first user in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching user, or <code>null</code> if a matching user could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public User fetchByUuid_First(String uuid,
		OrderByComparator orderByComparator) throws SystemException {
		List<User> list = findByUuid(uuid, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last user in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching user
	 * @throws com.liferay.portal.NoSuchUserException if a matching user could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public User findByUuid_Last(String uuid, OrderByComparator orderByComparator)
		throws NoSuchUserException, SystemException {
		User user = fetchByUuid_Last(uuid, orderByComparator);

		if (user != null) {
			return user;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchUserException(msg.toString());
	}

	/**
	 * Returns the last user in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching user, or <code>null</code> if a matching user could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public User fetchByUuid_Last(String uuid,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByUuid(uuid);

		List<User> list = findByUuid(uuid, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the users before and after the current user in the ordered set where uuid = &#63;.
	 *
	 * @param userId the primary key of the current user
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next user
	 * @throws com.liferay.portal.NoSuchUserException if a user with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public User[] findByUuid_PrevAndNext(long userId, String uuid,
		OrderByComparator orderByComparator)
		throws NoSuchUserException, SystemException {
		User user = findByPrimaryKey(userId);

		Session session = null;

		try {
			session = openSession();

			User[] array = new UserImpl[3];

			array[0] = getByUuid_PrevAndNext(session, user, uuid,
					orderByComparator, true);

			array[1] = user;

			array[2] = getByUuid_PrevAndNext(session, user, uuid,
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

	protected User getByUuid_PrevAndNext(Session session, User user,
		String uuid, OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_USER_WHERE);

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
			query.append(UserModelImpl.ORDER_BY_JPQL);
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
			Object[] values = orderByComparator.getOrderByConditionValues(user);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<User> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the users where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByUuid(String uuid) throws SystemException {
		for (User user : findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
				null)) {
			remove(user);
		}
	}

	/**
	 * Returns the number of users where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching users
	 * @throws SystemException if a system exception occurred
	 */
	public int countByUuid(String uuid) throws SystemException {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_UUID;

		Object[] finderArgs = new Object[] { uuid };

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs,
				this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_USER_WHERE);

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

	private static final String _FINDER_COLUMN_UUID_UUID_1 = "user.uuid IS NULL";
	private static final String _FINDER_COLUMN_UUID_UUID_2 = "user.uuid = ?";
	private static final String _FINDER_COLUMN_UUID_UUID_3 = "(user.uuid IS NULL OR user.uuid = '')";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_UUID_C = new FinderPath(UserModelImpl.ENTITY_CACHE_ENABLED,
			UserModelImpl.FINDER_CACHE_ENABLED, UserImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUuid_C",
			new String[] {
				String.class.getName(), Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C =
		new FinderPath(UserModelImpl.ENTITY_CACHE_ENABLED,
			UserModelImpl.FINDER_CACHE_ENABLED, UserImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid_C",
			new String[] { String.class.getName(), Long.class.getName() },
			UserModelImpl.UUID_COLUMN_BITMASK |
			UserModelImpl.COMPANYID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID_C = new FinderPath(UserModelImpl.ENTITY_CACHE_ENABLED,
			UserModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUuid_C",
			new String[] { String.class.getName(), Long.class.getName() });

	/**
	 * Returns all the users where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching users
	 * @throws SystemException if a system exception occurred
	 */
	public List<User> findByUuid_C(String uuid, long companyId)
		throws SystemException {
		return findByUuid_C(uuid, companyId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the users where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.UserModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of users
	 * @param end the upper bound of the range of users (not inclusive)
	 * @return the range of matching users
	 * @throws SystemException if a system exception occurred
	 */
	public List<User> findByUuid_C(String uuid, long companyId, int start,
		int end) throws SystemException {
		return findByUuid_C(uuid, companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the users where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.UserModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of users
	 * @param end the upper bound of the range of users (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching users
	 * @throws SystemException if a system exception occurred
	 */
	public List<User> findByUuid_C(String uuid, long companyId, int start,
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

		List<User> list = (List<User>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (User user : list) {
				if (!Validator.equals(uuid, user.getUuid()) ||
						(companyId != user.getCompanyId())) {
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

			query.append(_SQL_SELECT_USER_WHERE);

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
				query.append(UserModelImpl.ORDER_BY_JPQL);
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
					list = (List<User>)QueryUtil.list(q, getDialect(), start,
							end, false);

					Collections.sort(list);

					list = new UnmodifiableList<User>(list);
				}
				else {
					list = (List<User>)QueryUtil.list(q, getDialect(), start,
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
	 * Returns the first user in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching user
	 * @throws com.liferay.portal.NoSuchUserException if a matching user could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public User findByUuid_C_First(String uuid, long companyId,
		OrderByComparator orderByComparator)
		throws NoSuchUserException, SystemException {
		User user = fetchByUuid_C_First(uuid, companyId, orderByComparator);

		if (user != null) {
			return user;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(", companyId=");
		msg.append(companyId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchUserException(msg.toString());
	}

	/**
	 * Returns the first user in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching user, or <code>null</code> if a matching user could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public User fetchByUuid_C_First(String uuid, long companyId,
		OrderByComparator orderByComparator) throws SystemException {
		List<User> list = findByUuid_C(uuid, companyId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last user in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching user
	 * @throws com.liferay.portal.NoSuchUserException if a matching user could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public User findByUuid_C_Last(String uuid, long companyId,
		OrderByComparator orderByComparator)
		throws NoSuchUserException, SystemException {
		User user = fetchByUuid_C_Last(uuid, companyId, orderByComparator);

		if (user != null) {
			return user;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(", companyId=");
		msg.append(companyId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchUserException(msg.toString());
	}

	/**
	 * Returns the last user in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching user, or <code>null</code> if a matching user could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public User fetchByUuid_C_Last(String uuid, long companyId,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByUuid_C(uuid, companyId);

		List<User> list = findByUuid_C(uuid, companyId, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the users before and after the current user in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param userId the primary key of the current user
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next user
	 * @throws com.liferay.portal.NoSuchUserException if a user with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public User[] findByUuid_C_PrevAndNext(long userId, String uuid,
		long companyId, OrderByComparator orderByComparator)
		throws NoSuchUserException, SystemException {
		User user = findByPrimaryKey(userId);

		Session session = null;

		try {
			session = openSession();

			User[] array = new UserImpl[3];

			array[0] = getByUuid_C_PrevAndNext(session, user, uuid, companyId,
					orderByComparator, true);

			array[1] = user;

			array[2] = getByUuid_C_PrevAndNext(session, user, uuid, companyId,
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

	protected User getByUuid_C_PrevAndNext(Session session, User user,
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

		query.append(_SQL_SELECT_USER_WHERE);

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
			query.append(UserModelImpl.ORDER_BY_JPQL);
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
			Object[] values = orderByComparator.getOrderByConditionValues(user);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<User> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the users where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByUuid_C(String uuid, long companyId)
		throws SystemException {
		for (User user : findByUuid_C(uuid, companyId, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS, null)) {
			remove(user);
		}
	}

	/**
	 * Returns the number of users where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching users
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

			query.append(_SQL_COUNT_USER_WHERE);

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

	private static final String _FINDER_COLUMN_UUID_C_UUID_1 = "user.uuid IS NULL AND ";
	private static final String _FINDER_COLUMN_UUID_C_UUID_2 = "user.uuid = ? AND ";
	private static final String _FINDER_COLUMN_UUID_C_UUID_3 = "(user.uuid IS NULL OR user.uuid = '') AND ";
	private static final String _FINDER_COLUMN_UUID_C_COMPANYID_2 = "user.companyId = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_COMPANYID =
		new FinderPath(UserModelImpl.ENTITY_CACHE_ENABLED,
			UserModelImpl.FINDER_CACHE_ENABLED, UserImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByCompanyId",
			new String[] {
				Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID =
		new FinderPath(UserModelImpl.ENTITY_CACHE_ENABLED,
			UserModelImpl.FINDER_CACHE_ENABLED, UserImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByCompanyId",
			new String[] { Long.class.getName() },
			UserModelImpl.COMPANYID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_COMPANYID = new FinderPath(UserModelImpl.ENTITY_CACHE_ENABLED,
			UserModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByCompanyId",
			new String[] { Long.class.getName() });

	/**
	 * Returns all the users where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching users
	 * @throws SystemException if a system exception occurred
	 */
	public List<User> findByCompanyId(long companyId) throws SystemException {
		return findByCompanyId(companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the users where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.UserModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of users
	 * @param end the upper bound of the range of users (not inclusive)
	 * @return the range of matching users
	 * @throws SystemException if a system exception occurred
	 */
	public List<User> findByCompanyId(long companyId, int start, int end)
		throws SystemException {
		return findByCompanyId(companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the users where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.UserModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of users
	 * @param end the upper bound of the range of users (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching users
	 * @throws SystemException if a system exception occurred
	 */
	public List<User> findByCompanyId(long companyId, int start, int end,
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

		List<User> list = (List<User>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (User user : list) {
				if ((companyId != user.getCompanyId())) {
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

			query.append(_SQL_SELECT_USER_WHERE);

			query.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(UserModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				if (!pagination) {
					list = (List<User>)QueryUtil.list(q, getDialect(), start,
							end, false);

					Collections.sort(list);

					list = new UnmodifiableList<User>(list);
				}
				else {
					list = (List<User>)QueryUtil.list(q, getDialect(), start,
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
	 * Returns the first user in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching user
	 * @throws com.liferay.portal.NoSuchUserException if a matching user could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public User findByCompanyId_First(long companyId,
		OrderByComparator orderByComparator)
		throws NoSuchUserException, SystemException {
		User user = fetchByCompanyId_First(companyId, orderByComparator);

		if (user != null) {
			return user;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("companyId=");
		msg.append(companyId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchUserException(msg.toString());
	}

	/**
	 * Returns the first user in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching user, or <code>null</code> if a matching user could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public User fetchByCompanyId_First(long companyId,
		OrderByComparator orderByComparator) throws SystemException {
		List<User> list = findByCompanyId(companyId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last user in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching user
	 * @throws com.liferay.portal.NoSuchUserException if a matching user could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public User findByCompanyId_Last(long companyId,
		OrderByComparator orderByComparator)
		throws NoSuchUserException, SystemException {
		User user = fetchByCompanyId_Last(companyId, orderByComparator);

		if (user != null) {
			return user;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("companyId=");
		msg.append(companyId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchUserException(msg.toString());
	}

	/**
	 * Returns the last user in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching user, or <code>null</code> if a matching user could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public User fetchByCompanyId_Last(long companyId,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByCompanyId(companyId);

		List<User> list = findByCompanyId(companyId, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the users before and after the current user in the ordered set where companyId = &#63;.
	 *
	 * @param userId the primary key of the current user
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next user
	 * @throws com.liferay.portal.NoSuchUserException if a user with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public User[] findByCompanyId_PrevAndNext(long userId, long companyId,
		OrderByComparator orderByComparator)
		throws NoSuchUserException, SystemException {
		User user = findByPrimaryKey(userId);

		Session session = null;

		try {
			session = openSession();

			User[] array = new UserImpl[3];

			array[0] = getByCompanyId_PrevAndNext(session, user, companyId,
					orderByComparator, true);

			array[1] = user;

			array[2] = getByCompanyId_PrevAndNext(session, user, companyId,
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

	protected User getByCompanyId_PrevAndNext(Session session, User user,
		long companyId, OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_USER_WHERE);

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
			query.append(UserModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(companyId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(user);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<User> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the users where companyId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByCompanyId(long companyId) throws SystemException {
		for (User user : findByCompanyId(companyId, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS, null)) {
			remove(user);
		}
	}

	/**
	 * Returns the number of users where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching users
	 * @throws SystemException if a system exception occurred
	 */
	public int countByCompanyId(long companyId) throws SystemException {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_COMPANYID;

		Object[] finderArgs = new Object[] { companyId };

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs,
				this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_USER_WHERE);

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

	private static final String _FINDER_COLUMN_COMPANYID_COMPANYID_2 = "user.companyId = ?";
	public static final FinderPath FINDER_PATH_FETCH_BY_CONTACTID = new FinderPath(UserModelImpl.ENTITY_CACHE_ENABLED,
			UserModelImpl.FINDER_CACHE_ENABLED, UserImpl.class,
			FINDER_CLASS_NAME_ENTITY, "fetchByContactId",
			new String[] { Long.class.getName() },
			UserModelImpl.CONTACTID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_CONTACTID = new FinderPath(UserModelImpl.ENTITY_CACHE_ENABLED,
			UserModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByContactId",
			new String[] { Long.class.getName() });

	/**
	 * Returns the user where contactId = &#63; or throws a {@link com.liferay.portal.NoSuchUserException} if it could not be found.
	 *
	 * @param contactId the contact ID
	 * @return the matching user
	 * @throws com.liferay.portal.NoSuchUserException if a matching user could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public User findByContactId(long contactId)
		throws NoSuchUserException, SystemException {
		User user = fetchByContactId(contactId);

		if (user == null) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("contactId=");
			msg.append(contactId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchUserException(msg.toString());
		}

		return user;
	}

	/**
	 * Returns the user where contactId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param contactId the contact ID
	 * @return the matching user, or <code>null</code> if a matching user could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public User fetchByContactId(long contactId) throws SystemException {
		return fetchByContactId(contactId, true);
	}

	/**
	 * Returns the user where contactId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param contactId the contact ID
	 * @param retrieveFromCache whether to use the finder cache
	 * @return the matching user, or <code>null</code> if a matching user could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public User fetchByContactId(long contactId, boolean retrieveFromCache)
		throws SystemException {
		Object[] finderArgs = new Object[] { contactId };

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_CONTACTID,
					finderArgs, this);
		}

		if (result instanceof User) {
			User user = (User)result;

			if ((contactId != user.getContactId())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_SELECT_USER_WHERE);

			query.append(_FINDER_COLUMN_CONTACTID_CONTACTID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(contactId);

				List<User> list = q.list();

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_CONTACTID,
						finderArgs, list);
				}
				else {
					User user = list.get(0);

					result = user;

					cacheResult(user);

					if ((user.getContactId() != contactId)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_CONTACTID,
							finderArgs, user);
					}
				}
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_CONTACTID,
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
			return (User)result;
		}
	}

	/**
	 * Removes the user where contactId = &#63; from the database.
	 *
	 * @param contactId the contact ID
	 * @return the user that was removed
	 * @throws SystemException if a system exception occurred
	 */
	public User removeByContactId(long contactId)
		throws NoSuchUserException, SystemException {
		User user = findByContactId(contactId);

		return remove(user);
	}

	/**
	 * Returns the number of users where contactId = &#63;.
	 *
	 * @param contactId the contact ID
	 * @return the number of matching users
	 * @throws SystemException if a system exception occurred
	 */
	public int countByContactId(long contactId) throws SystemException {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_CONTACTID;

		Object[] finderArgs = new Object[] { contactId };

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs,
				this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_USER_WHERE);

			query.append(_FINDER_COLUMN_CONTACTID_CONTACTID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(contactId);

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

	private static final String _FINDER_COLUMN_CONTACTID_CONTACTID_2 = "user.contactId = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_EMAILADDRESS =
		new FinderPath(UserModelImpl.ENTITY_CACHE_ENABLED,
			UserModelImpl.FINDER_CACHE_ENABLED, UserImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByEmailAddress",
			new String[] {
				String.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_EMAILADDRESS =
		new FinderPath(UserModelImpl.ENTITY_CACHE_ENABLED,
			UserModelImpl.FINDER_CACHE_ENABLED, UserImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByEmailAddress",
			new String[] { String.class.getName() },
			UserModelImpl.EMAILADDRESS_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_EMAILADDRESS = new FinderPath(UserModelImpl.ENTITY_CACHE_ENABLED,
			UserModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByEmailAddress",
			new String[] { String.class.getName() });

	/**
	 * Returns all the users where emailAddress = &#63;.
	 *
	 * @param emailAddress the email address
	 * @return the matching users
	 * @throws SystemException if a system exception occurred
	 */
	public List<User> findByEmailAddress(String emailAddress)
		throws SystemException {
		return findByEmailAddress(emailAddress, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the users where emailAddress = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.UserModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param emailAddress the email address
	 * @param start the lower bound of the range of users
	 * @param end the upper bound of the range of users (not inclusive)
	 * @return the range of matching users
	 * @throws SystemException if a system exception occurred
	 */
	public List<User> findByEmailAddress(String emailAddress, int start, int end)
		throws SystemException {
		return findByEmailAddress(emailAddress, start, end, null);
	}

	/**
	 * Returns an ordered range of all the users where emailAddress = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.UserModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param emailAddress the email address
	 * @param start the lower bound of the range of users
	 * @param end the upper bound of the range of users (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching users
	 * @throws SystemException if a system exception occurred
	 */
	public List<User> findByEmailAddress(String emailAddress, int start,
		int end, OrderByComparator orderByComparator) throws SystemException {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_EMAILADDRESS;
			finderArgs = new Object[] { emailAddress };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_EMAILADDRESS;
			finderArgs = new Object[] {
					emailAddress,
					
					start, end, orderByComparator
				};
		}

		List<User> list = (List<User>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (User user : list) {
				if (!Validator.equals(emailAddress, user.getEmailAddress())) {
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

			query.append(_SQL_SELECT_USER_WHERE);

			boolean bindEmailAddress = false;

			if (emailAddress == null) {
				query.append(_FINDER_COLUMN_EMAILADDRESS_EMAILADDRESS_1);
			}
			else if (emailAddress.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_EMAILADDRESS_EMAILADDRESS_3);
			}
			else {
				bindEmailAddress = true;

				query.append(_FINDER_COLUMN_EMAILADDRESS_EMAILADDRESS_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(UserModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindEmailAddress) {
					qPos.add(emailAddress);
				}

				if (!pagination) {
					list = (List<User>)QueryUtil.list(q, getDialect(), start,
							end, false);

					Collections.sort(list);

					list = new UnmodifiableList<User>(list);
				}
				else {
					list = (List<User>)QueryUtil.list(q, getDialect(), start,
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
	 * Returns the first user in the ordered set where emailAddress = &#63;.
	 *
	 * @param emailAddress the email address
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching user
	 * @throws com.liferay.portal.NoSuchUserException if a matching user could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public User findByEmailAddress_First(String emailAddress,
		OrderByComparator orderByComparator)
		throws NoSuchUserException, SystemException {
		User user = fetchByEmailAddress_First(emailAddress, orderByComparator);

		if (user != null) {
			return user;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("emailAddress=");
		msg.append(emailAddress);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchUserException(msg.toString());
	}

	/**
	 * Returns the first user in the ordered set where emailAddress = &#63;.
	 *
	 * @param emailAddress the email address
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching user, or <code>null</code> if a matching user could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public User fetchByEmailAddress_First(String emailAddress,
		OrderByComparator orderByComparator) throws SystemException {
		List<User> list = findByEmailAddress(emailAddress, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last user in the ordered set where emailAddress = &#63;.
	 *
	 * @param emailAddress the email address
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching user
	 * @throws com.liferay.portal.NoSuchUserException if a matching user could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public User findByEmailAddress_Last(String emailAddress,
		OrderByComparator orderByComparator)
		throws NoSuchUserException, SystemException {
		User user = fetchByEmailAddress_Last(emailAddress, orderByComparator);

		if (user != null) {
			return user;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("emailAddress=");
		msg.append(emailAddress);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchUserException(msg.toString());
	}

	/**
	 * Returns the last user in the ordered set where emailAddress = &#63;.
	 *
	 * @param emailAddress the email address
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching user, or <code>null</code> if a matching user could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public User fetchByEmailAddress_Last(String emailAddress,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByEmailAddress(emailAddress);

		List<User> list = findByEmailAddress(emailAddress, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the users before and after the current user in the ordered set where emailAddress = &#63;.
	 *
	 * @param userId the primary key of the current user
	 * @param emailAddress the email address
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next user
	 * @throws com.liferay.portal.NoSuchUserException if a user with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public User[] findByEmailAddress_PrevAndNext(long userId,
		String emailAddress, OrderByComparator orderByComparator)
		throws NoSuchUserException, SystemException {
		User user = findByPrimaryKey(userId);

		Session session = null;

		try {
			session = openSession();

			User[] array = new UserImpl[3];

			array[0] = getByEmailAddress_PrevAndNext(session, user,
					emailAddress, orderByComparator, true);

			array[1] = user;

			array[2] = getByEmailAddress_PrevAndNext(session, user,
					emailAddress, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected User getByEmailAddress_PrevAndNext(Session session, User user,
		String emailAddress, OrderByComparator orderByComparator,
		boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_USER_WHERE);

		boolean bindEmailAddress = false;

		if (emailAddress == null) {
			query.append(_FINDER_COLUMN_EMAILADDRESS_EMAILADDRESS_1);
		}
		else if (emailAddress.equals(StringPool.BLANK)) {
			query.append(_FINDER_COLUMN_EMAILADDRESS_EMAILADDRESS_3);
		}
		else {
			bindEmailAddress = true;

			query.append(_FINDER_COLUMN_EMAILADDRESS_EMAILADDRESS_2);
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
			query.append(UserModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		if (bindEmailAddress) {
			qPos.add(emailAddress);
		}

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(user);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<User> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the users where emailAddress = &#63; from the database.
	 *
	 * @param emailAddress the email address
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByEmailAddress(String emailAddress)
		throws SystemException {
		for (User user : findByEmailAddress(emailAddress, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS, null)) {
			remove(user);
		}
	}

	/**
	 * Returns the number of users where emailAddress = &#63;.
	 *
	 * @param emailAddress the email address
	 * @return the number of matching users
	 * @throws SystemException if a system exception occurred
	 */
	public int countByEmailAddress(String emailAddress)
		throws SystemException {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_EMAILADDRESS;

		Object[] finderArgs = new Object[] { emailAddress };

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs,
				this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_USER_WHERE);

			boolean bindEmailAddress = false;

			if (emailAddress == null) {
				query.append(_FINDER_COLUMN_EMAILADDRESS_EMAILADDRESS_1);
			}
			else if (emailAddress.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_EMAILADDRESS_EMAILADDRESS_3);
			}
			else {
				bindEmailAddress = true;

				query.append(_FINDER_COLUMN_EMAILADDRESS_EMAILADDRESS_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindEmailAddress) {
					qPos.add(emailAddress);
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

	private static final String _FINDER_COLUMN_EMAILADDRESS_EMAILADDRESS_1 = "user.emailAddress IS NULL";
	private static final String _FINDER_COLUMN_EMAILADDRESS_EMAILADDRESS_2 = "user.emailAddress = ?";
	private static final String _FINDER_COLUMN_EMAILADDRESS_EMAILADDRESS_3 = "(user.emailAddress IS NULL OR user.emailAddress = '')";
	public static final FinderPath FINDER_PATH_FETCH_BY_PORTRAITID = new FinderPath(UserModelImpl.ENTITY_CACHE_ENABLED,
			UserModelImpl.FINDER_CACHE_ENABLED, UserImpl.class,
			FINDER_CLASS_NAME_ENTITY, "fetchByPortraitId",
			new String[] { Long.class.getName() },
			UserModelImpl.PORTRAITID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_PORTRAITID = new FinderPath(UserModelImpl.ENTITY_CACHE_ENABLED,
			UserModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByPortraitId",
			new String[] { Long.class.getName() });

	/**
	 * Returns the user where portraitId = &#63; or throws a {@link com.liferay.portal.NoSuchUserException} if it could not be found.
	 *
	 * @param portraitId the portrait ID
	 * @return the matching user
	 * @throws com.liferay.portal.NoSuchUserException if a matching user could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public User findByPortraitId(long portraitId)
		throws NoSuchUserException, SystemException {
		User user = fetchByPortraitId(portraitId);

		if (user == null) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("portraitId=");
			msg.append(portraitId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchUserException(msg.toString());
		}

		return user;
	}

	/**
	 * Returns the user where portraitId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param portraitId the portrait ID
	 * @return the matching user, or <code>null</code> if a matching user could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public User fetchByPortraitId(long portraitId) throws SystemException {
		return fetchByPortraitId(portraitId, true);
	}

	/**
	 * Returns the user where portraitId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param portraitId the portrait ID
	 * @param retrieveFromCache whether to use the finder cache
	 * @return the matching user, or <code>null</code> if a matching user could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public User fetchByPortraitId(long portraitId, boolean retrieveFromCache)
		throws SystemException {
		Object[] finderArgs = new Object[] { portraitId };

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_PORTRAITID,
					finderArgs, this);
		}

		if (result instanceof User) {
			User user = (User)result;

			if ((portraitId != user.getPortraitId())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_SELECT_USER_WHERE);

			query.append(_FINDER_COLUMN_PORTRAITID_PORTRAITID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(portraitId);

				List<User> list = q.list();

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_PORTRAITID,
						finderArgs, list);
				}
				else {
					if ((list.size() > 1) && _log.isWarnEnabled()) {
						_log.warn(
							"UserPersistenceImpl.fetchByPortraitId(long, boolean) with parameters (" +
							StringUtil.merge(finderArgs) +
							") yields a result set with more than 1 result. This violates the logical unique restriction. There is no order guarantee on which result is returned by this finder.");
					}

					User user = list.get(0);

					result = user;

					cacheResult(user);

					if ((user.getPortraitId() != portraitId)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_PORTRAITID,
							finderArgs, user);
					}
				}
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_PORTRAITID,
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
			return (User)result;
		}
	}

	/**
	 * Removes the user where portraitId = &#63; from the database.
	 *
	 * @param portraitId the portrait ID
	 * @return the user that was removed
	 * @throws SystemException if a system exception occurred
	 */
	public User removeByPortraitId(long portraitId)
		throws NoSuchUserException, SystemException {
		User user = findByPortraitId(portraitId);

		return remove(user);
	}

	/**
	 * Returns the number of users where portraitId = &#63;.
	 *
	 * @param portraitId the portrait ID
	 * @return the number of matching users
	 * @throws SystemException if a system exception occurred
	 */
	public int countByPortraitId(long portraitId) throws SystemException {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_PORTRAITID;

		Object[] finderArgs = new Object[] { portraitId };

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs,
				this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_USER_WHERE);

			query.append(_FINDER_COLUMN_PORTRAITID_PORTRAITID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(portraitId);

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

	private static final String _FINDER_COLUMN_PORTRAITID_PORTRAITID_2 = "user.portraitId = ?";
	public static final FinderPath FINDER_PATH_FETCH_BY_C_U = new FinderPath(UserModelImpl.ENTITY_CACHE_ENABLED,
			UserModelImpl.FINDER_CACHE_ENABLED, UserImpl.class,
			FINDER_CLASS_NAME_ENTITY, "fetchByC_U",
			new String[] { Long.class.getName(), Long.class.getName() },
			UserModelImpl.COMPANYID_COLUMN_BITMASK |
			UserModelImpl.USERID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_C_U = new FinderPath(UserModelImpl.ENTITY_CACHE_ENABLED,
			UserModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_U",
			new String[] { Long.class.getName(), Long.class.getName() });

	/**
	 * Returns the user where companyId = &#63; and userId = &#63; or throws a {@link com.liferay.portal.NoSuchUserException} if it could not be found.
	 *
	 * @param companyId the company ID
	 * @param userId the user ID
	 * @return the matching user
	 * @throws com.liferay.portal.NoSuchUserException if a matching user could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public User findByC_U(long companyId, long userId)
		throws NoSuchUserException, SystemException {
		User user = fetchByC_U(companyId, userId);

		if (user == null) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("companyId=");
			msg.append(companyId);

			msg.append(", userId=");
			msg.append(userId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchUserException(msg.toString());
		}

		return user;
	}

	/**
	 * Returns the user where companyId = &#63; and userId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param companyId the company ID
	 * @param userId the user ID
	 * @return the matching user, or <code>null</code> if a matching user could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public User fetchByC_U(long companyId, long userId)
		throws SystemException {
		return fetchByC_U(companyId, userId, true);
	}

	/**
	 * Returns the user where companyId = &#63; and userId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param companyId the company ID
	 * @param userId the user ID
	 * @param retrieveFromCache whether to use the finder cache
	 * @return the matching user, or <code>null</code> if a matching user could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public User fetchByC_U(long companyId, long userId,
		boolean retrieveFromCache) throws SystemException {
		Object[] finderArgs = new Object[] { companyId, userId };

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_C_U,
					finderArgs, this);
		}

		if (result instanceof User) {
			User user = (User)result;

			if ((companyId != user.getCompanyId()) ||
					(userId != user.getUserId())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_SELECT_USER_WHERE);

			query.append(_FINDER_COLUMN_C_U_COMPANYID_2);

			query.append(_FINDER_COLUMN_C_U_USERID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				qPos.add(userId);

				List<User> list = q.list();

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_U,
						finderArgs, list);
				}
				else {
					User user = list.get(0);

					result = user;

					cacheResult(user);

					if ((user.getCompanyId() != companyId) ||
							(user.getUserId() != userId)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_U,
							finderArgs, user);
					}
				}
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_C_U,
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
			return (User)result;
		}
	}

	/**
	 * Removes the user where companyId = &#63; and userId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param userId the user ID
	 * @return the user that was removed
	 * @throws SystemException if a system exception occurred
	 */
	public User removeByC_U(long companyId, long userId)
		throws NoSuchUserException, SystemException {
		User user = findByC_U(companyId, userId);

		return remove(user);
	}

	/**
	 * Returns the number of users where companyId = &#63; and userId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param userId the user ID
	 * @return the number of matching users
	 * @throws SystemException if a system exception occurred
	 */
	public int countByC_U(long companyId, long userId)
		throws SystemException {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_C_U;

		Object[] finderArgs = new Object[] { companyId, userId };

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs,
				this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_USER_WHERE);

			query.append(_FINDER_COLUMN_C_U_COMPANYID_2);

			query.append(_FINDER_COLUMN_C_U_USERID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				qPos.add(userId);

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

	private static final String _FINDER_COLUMN_C_U_COMPANYID_2 = "user.companyId = ? AND ";
	private static final String _FINDER_COLUMN_C_U_USERID_2 = "user.userId = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_C_CD = new FinderPath(UserModelImpl.ENTITY_CACHE_ENABLED,
			UserModelImpl.FINDER_CACHE_ENABLED, UserImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByC_CD",
			new String[] {
				Long.class.getName(), Date.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_CD = new FinderPath(UserModelImpl.ENTITY_CACHE_ENABLED,
			UserModelImpl.FINDER_CACHE_ENABLED, UserImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByC_CD",
			new String[] { Long.class.getName(), Date.class.getName() },
			UserModelImpl.COMPANYID_COLUMN_BITMASK |
			UserModelImpl.CREATEDATE_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_C_CD = new FinderPath(UserModelImpl.ENTITY_CACHE_ENABLED,
			UserModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_CD",
			new String[] { Long.class.getName(), Date.class.getName() });

	/**
	 * Returns all the users where companyId = &#63; and createDate = &#63;.
	 *
	 * @param companyId the company ID
	 * @param createDate the create date
	 * @return the matching users
	 * @throws SystemException if a system exception occurred
	 */
	public List<User> findByC_CD(long companyId, Date createDate)
		throws SystemException {
		return findByC_CD(companyId, createDate, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the users where companyId = &#63; and createDate = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.UserModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param createDate the create date
	 * @param start the lower bound of the range of users
	 * @param end the upper bound of the range of users (not inclusive)
	 * @return the range of matching users
	 * @throws SystemException if a system exception occurred
	 */
	public List<User> findByC_CD(long companyId, Date createDate, int start,
		int end) throws SystemException {
		return findByC_CD(companyId, createDate, start, end, null);
	}

	/**
	 * Returns an ordered range of all the users where companyId = &#63; and createDate = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.UserModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param createDate the create date
	 * @param start the lower bound of the range of users
	 * @param end the upper bound of the range of users (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching users
	 * @throws SystemException if a system exception occurred
	 */
	public List<User> findByC_CD(long companyId, Date createDate, int start,
		int end, OrderByComparator orderByComparator) throws SystemException {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_CD;
			finderArgs = new Object[] { companyId, createDate };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_C_CD;
			finderArgs = new Object[] {
					companyId, createDate,
					
					start, end, orderByComparator
				};
		}

		List<User> list = (List<User>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (User user : list) {
				if ((companyId != user.getCompanyId()) ||
						!Validator.equals(createDate, user.getCreateDate())) {
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

			query.append(_SQL_SELECT_USER_WHERE);

			query.append(_FINDER_COLUMN_C_CD_COMPANYID_2);

			boolean bindCreateDate = false;

			if (createDate == null) {
				query.append(_FINDER_COLUMN_C_CD_CREATEDATE_1);
			}
			else {
				bindCreateDate = true;

				query.append(_FINDER_COLUMN_C_CD_CREATEDATE_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(UserModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				if (bindCreateDate) {
					qPos.add(CalendarUtil.getTimestamp(createDate));
				}

				if (!pagination) {
					list = (List<User>)QueryUtil.list(q, getDialect(), start,
							end, false);

					Collections.sort(list);

					list = new UnmodifiableList<User>(list);
				}
				else {
					list = (List<User>)QueryUtil.list(q, getDialect(), start,
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
	 * Returns the first user in the ordered set where companyId = &#63; and createDate = &#63;.
	 *
	 * @param companyId the company ID
	 * @param createDate the create date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching user
	 * @throws com.liferay.portal.NoSuchUserException if a matching user could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public User findByC_CD_First(long companyId, Date createDate,
		OrderByComparator orderByComparator)
		throws NoSuchUserException, SystemException {
		User user = fetchByC_CD_First(companyId, createDate, orderByComparator);

		if (user != null) {
			return user;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("companyId=");
		msg.append(companyId);

		msg.append(", createDate=");
		msg.append(createDate);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchUserException(msg.toString());
	}

	/**
	 * Returns the first user in the ordered set where companyId = &#63; and createDate = &#63;.
	 *
	 * @param companyId the company ID
	 * @param createDate the create date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching user, or <code>null</code> if a matching user could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public User fetchByC_CD_First(long companyId, Date createDate,
		OrderByComparator orderByComparator) throws SystemException {
		List<User> list = findByC_CD(companyId, createDate, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last user in the ordered set where companyId = &#63; and createDate = &#63;.
	 *
	 * @param companyId the company ID
	 * @param createDate the create date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching user
	 * @throws com.liferay.portal.NoSuchUserException if a matching user could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public User findByC_CD_Last(long companyId, Date createDate,
		OrderByComparator orderByComparator)
		throws NoSuchUserException, SystemException {
		User user = fetchByC_CD_Last(companyId, createDate, orderByComparator);

		if (user != null) {
			return user;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("companyId=");
		msg.append(companyId);

		msg.append(", createDate=");
		msg.append(createDate);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchUserException(msg.toString());
	}

	/**
	 * Returns the last user in the ordered set where companyId = &#63; and createDate = &#63;.
	 *
	 * @param companyId the company ID
	 * @param createDate the create date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching user, or <code>null</code> if a matching user could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public User fetchByC_CD_Last(long companyId, Date createDate,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByC_CD(companyId, createDate);

		List<User> list = findByC_CD(companyId, createDate, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the users before and after the current user in the ordered set where companyId = &#63; and createDate = &#63;.
	 *
	 * @param userId the primary key of the current user
	 * @param companyId the company ID
	 * @param createDate the create date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next user
	 * @throws com.liferay.portal.NoSuchUserException if a user with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public User[] findByC_CD_PrevAndNext(long userId, long companyId,
		Date createDate, OrderByComparator orderByComparator)
		throws NoSuchUserException, SystemException {
		User user = findByPrimaryKey(userId);

		Session session = null;

		try {
			session = openSession();

			User[] array = new UserImpl[3];

			array[0] = getByC_CD_PrevAndNext(session, user, companyId,
					createDate, orderByComparator, true);

			array[1] = user;

			array[2] = getByC_CD_PrevAndNext(session, user, companyId,
					createDate, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected User getByC_CD_PrevAndNext(Session session, User user,
		long companyId, Date createDate, OrderByComparator orderByComparator,
		boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_USER_WHERE);

		query.append(_FINDER_COLUMN_C_CD_COMPANYID_2);

		boolean bindCreateDate = false;

		if (createDate == null) {
			query.append(_FINDER_COLUMN_C_CD_CREATEDATE_1);
		}
		else {
			bindCreateDate = true;

			query.append(_FINDER_COLUMN_C_CD_CREATEDATE_2);
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
			query.append(UserModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(companyId);

		if (bindCreateDate) {
			qPos.add(CalendarUtil.getTimestamp(createDate));
		}

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(user);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<User> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the users where companyId = &#63; and createDate = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param createDate the create date
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByC_CD(long companyId, Date createDate)
		throws SystemException {
		for (User user : findByC_CD(companyId, createDate, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS, null)) {
			remove(user);
		}
	}

	/**
	 * Returns the number of users where companyId = &#63; and createDate = &#63;.
	 *
	 * @param companyId the company ID
	 * @param createDate the create date
	 * @return the number of matching users
	 * @throws SystemException if a system exception occurred
	 */
	public int countByC_CD(long companyId, Date createDate)
		throws SystemException {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_C_CD;

		Object[] finderArgs = new Object[] { companyId, createDate };

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs,
				this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_USER_WHERE);

			query.append(_FINDER_COLUMN_C_CD_COMPANYID_2);

			boolean bindCreateDate = false;

			if (createDate == null) {
				query.append(_FINDER_COLUMN_C_CD_CREATEDATE_1);
			}
			else {
				bindCreateDate = true;

				query.append(_FINDER_COLUMN_C_CD_CREATEDATE_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				if (bindCreateDate) {
					qPos.add(CalendarUtil.getTimestamp(createDate));
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

	private static final String _FINDER_COLUMN_C_CD_COMPANYID_2 = "user.companyId = ? AND ";
	private static final String _FINDER_COLUMN_C_CD_CREATEDATE_1 = "user.createDate IS NULL";
	private static final String _FINDER_COLUMN_C_CD_CREATEDATE_2 = "user.createDate = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_C_MD = new FinderPath(UserModelImpl.ENTITY_CACHE_ENABLED,
			UserModelImpl.FINDER_CACHE_ENABLED, UserImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByC_MD",
			new String[] {
				Long.class.getName(), Date.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_MD = new FinderPath(UserModelImpl.ENTITY_CACHE_ENABLED,
			UserModelImpl.FINDER_CACHE_ENABLED, UserImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByC_MD",
			new String[] { Long.class.getName(), Date.class.getName() },
			UserModelImpl.COMPANYID_COLUMN_BITMASK |
			UserModelImpl.MODIFIEDDATE_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_C_MD = new FinderPath(UserModelImpl.ENTITY_CACHE_ENABLED,
			UserModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_MD",
			new String[] { Long.class.getName(), Date.class.getName() });

	/**
	 * Returns all the users where companyId = &#63; and modifiedDate = &#63;.
	 *
	 * @param companyId the company ID
	 * @param modifiedDate the modified date
	 * @return the matching users
	 * @throws SystemException if a system exception occurred
	 */
	public List<User> findByC_MD(long companyId, Date modifiedDate)
		throws SystemException {
		return findByC_MD(companyId, modifiedDate, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the users where companyId = &#63; and modifiedDate = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.UserModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param modifiedDate the modified date
	 * @param start the lower bound of the range of users
	 * @param end the upper bound of the range of users (not inclusive)
	 * @return the range of matching users
	 * @throws SystemException if a system exception occurred
	 */
	public List<User> findByC_MD(long companyId, Date modifiedDate, int start,
		int end) throws SystemException {
		return findByC_MD(companyId, modifiedDate, start, end, null);
	}

	/**
	 * Returns an ordered range of all the users where companyId = &#63; and modifiedDate = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.UserModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param modifiedDate the modified date
	 * @param start the lower bound of the range of users
	 * @param end the upper bound of the range of users (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching users
	 * @throws SystemException if a system exception occurred
	 */
	public List<User> findByC_MD(long companyId, Date modifiedDate, int start,
		int end, OrderByComparator orderByComparator) throws SystemException {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_MD;
			finderArgs = new Object[] { companyId, modifiedDate };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_C_MD;
			finderArgs = new Object[] {
					companyId, modifiedDate,
					
					start, end, orderByComparator
				};
		}

		List<User> list = (List<User>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (User user : list) {
				if ((companyId != user.getCompanyId()) ||
						!Validator.equals(modifiedDate, user.getModifiedDate())) {
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

			query.append(_SQL_SELECT_USER_WHERE);

			query.append(_FINDER_COLUMN_C_MD_COMPANYID_2);

			boolean bindModifiedDate = false;

			if (modifiedDate == null) {
				query.append(_FINDER_COLUMN_C_MD_MODIFIEDDATE_1);
			}
			else {
				bindModifiedDate = true;

				query.append(_FINDER_COLUMN_C_MD_MODIFIEDDATE_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(UserModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				if (bindModifiedDate) {
					qPos.add(CalendarUtil.getTimestamp(modifiedDate));
				}

				if (!pagination) {
					list = (List<User>)QueryUtil.list(q, getDialect(), start,
							end, false);

					Collections.sort(list);

					list = new UnmodifiableList<User>(list);
				}
				else {
					list = (List<User>)QueryUtil.list(q, getDialect(), start,
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
	 * Returns the first user in the ordered set where companyId = &#63; and modifiedDate = &#63;.
	 *
	 * @param companyId the company ID
	 * @param modifiedDate the modified date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching user
	 * @throws com.liferay.portal.NoSuchUserException if a matching user could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public User findByC_MD_First(long companyId, Date modifiedDate,
		OrderByComparator orderByComparator)
		throws NoSuchUserException, SystemException {
		User user = fetchByC_MD_First(companyId, modifiedDate, orderByComparator);

		if (user != null) {
			return user;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("companyId=");
		msg.append(companyId);

		msg.append(", modifiedDate=");
		msg.append(modifiedDate);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchUserException(msg.toString());
	}

	/**
	 * Returns the first user in the ordered set where companyId = &#63; and modifiedDate = &#63;.
	 *
	 * @param companyId the company ID
	 * @param modifiedDate the modified date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching user, or <code>null</code> if a matching user could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public User fetchByC_MD_First(long companyId, Date modifiedDate,
		OrderByComparator orderByComparator) throws SystemException {
		List<User> list = findByC_MD(companyId, modifiedDate, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last user in the ordered set where companyId = &#63; and modifiedDate = &#63;.
	 *
	 * @param companyId the company ID
	 * @param modifiedDate the modified date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching user
	 * @throws com.liferay.portal.NoSuchUserException if a matching user could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public User findByC_MD_Last(long companyId, Date modifiedDate,
		OrderByComparator orderByComparator)
		throws NoSuchUserException, SystemException {
		User user = fetchByC_MD_Last(companyId, modifiedDate, orderByComparator);

		if (user != null) {
			return user;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("companyId=");
		msg.append(companyId);

		msg.append(", modifiedDate=");
		msg.append(modifiedDate);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchUserException(msg.toString());
	}

	/**
	 * Returns the last user in the ordered set where companyId = &#63; and modifiedDate = &#63;.
	 *
	 * @param companyId the company ID
	 * @param modifiedDate the modified date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching user, or <code>null</code> if a matching user could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public User fetchByC_MD_Last(long companyId, Date modifiedDate,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByC_MD(companyId, modifiedDate);

		List<User> list = findByC_MD(companyId, modifiedDate, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the users before and after the current user in the ordered set where companyId = &#63; and modifiedDate = &#63;.
	 *
	 * @param userId the primary key of the current user
	 * @param companyId the company ID
	 * @param modifiedDate the modified date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next user
	 * @throws com.liferay.portal.NoSuchUserException if a user with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public User[] findByC_MD_PrevAndNext(long userId, long companyId,
		Date modifiedDate, OrderByComparator orderByComparator)
		throws NoSuchUserException, SystemException {
		User user = findByPrimaryKey(userId);

		Session session = null;

		try {
			session = openSession();

			User[] array = new UserImpl[3];

			array[0] = getByC_MD_PrevAndNext(session, user, companyId,
					modifiedDate, orderByComparator, true);

			array[1] = user;

			array[2] = getByC_MD_PrevAndNext(session, user, companyId,
					modifiedDate, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected User getByC_MD_PrevAndNext(Session session, User user,
		long companyId, Date modifiedDate, OrderByComparator orderByComparator,
		boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_USER_WHERE);

		query.append(_FINDER_COLUMN_C_MD_COMPANYID_2);

		boolean bindModifiedDate = false;

		if (modifiedDate == null) {
			query.append(_FINDER_COLUMN_C_MD_MODIFIEDDATE_1);
		}
		else {
			bindModifiedDate = true;

			query.append(_FINDER_COLUMN_C_MD_MODIFIEDDATE_2);
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
			query.append(UserModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(companyId);

		if (bindModifiedDate) {
			qPos.add(CalendarUtil.getTimestamp(modifiedDate));
		}

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(user);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<User> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the users where companyId = &#63; and modifiedDate = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param modifiedDate the modified date
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByC_MD(long companyId, Date modifiedDate)
		throws SystemException {
		for (User user : findByC_MD(companyId, modifiedDate, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS, null)) {
			remove(user);
		}
	}

	/**
	 * Returns the number of users where companyId = &#63; and modifiedDate = &#63;.
	 *
	 * @param companyId the company ID
	 * @param modifiedDate the modified date
	 * @return the number of matching users
	 * @throws SystemException if a system exception occurred
	 */
	public int countByC_MD(long companyId, Date modifiedDate)
		throws SystemException {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_C_MD;

		Object[] finderArgs = new Object[] { companyId, modifiedDate };

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs,
				this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_USER_WHERE);

			query.append(_FINDER_COLUMN_C_MD_COMPANYID_2);

			boolean bindModifiedDate = false;

			if (modifiedDate == null) {
				query.append(_FINDER_COLUMN_C_MD_MODIFIEDDATE_1);
			}
			else {
				bindModifiedDate = true;

				query.append(_FINDER_COLUMN_C_MD_MODIFIEDDATE_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				if (bindModifiedDate) {
					qPos.add(CalendarUtil.getTimestamp(modifiedDate));
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

	private static final String _FINDER_COLUMN_C_MD_COMPANYID_2 = "user.companyId = ? AND ";
	private static final String _FINDER_COLUMN_C_MD_MODIFIEDDATE_1 = "user.modifiedDate IS NULL";
	private static final String _FINDER_COLUMN_C_MD_MODIFIEDDATE_2 = "user.modifiedDate = ?";
	public static final FinderPath FINDER_PATH_FETCH_BY_C_DU = new FinderPath(UserModelImpl.ENTITY_CACHE_ENABLED,
			UserModelImpl.FINDER_CACHE_ENABLED, UserImpl.class,
			FINDER_CLASS_NAME_ENTITY, "fetchByC_DU",
			new String[] { Long.class.getName(), Boolean.class.getName() },
			UserModelImpl.COMPANYID_COLUMN_BITMASK |
			UserModelImpl.DEFAULTUSER_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_C_DU = new FinderPath(UserModelImpl.ENTITY_CACHE_ENABLED,
			UserModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_DU",
			new String[] { Long.class.getName(), Boolean.class.getName() });

	/**
	 * Returns the user where companyId = &#63; and defaultUser = &#63; or throws a {@link com.liferay.portal.NoSuchUserException} if it could not be found.
	 *
	 * @param companyId the company ID
	 * @param defaultUser the default user
	 * @return the matching user
	 * @throws com.liferay.portal.NoSuchUserException if a matching user could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public User findByC_DU(long companyId, boolean defaultUser)
		throws NoSuchUserException, SystemException {
		User user = fetchByC_DU(companyId, defaultUser);

		if (user == null) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("companyId=");
			msg.append(companyId);

			msg.append(", defaultUser=");
			msg.append(defaultUser);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchUserException(msg.toString());
		}

		return user;
	}

	/**
	 * Returns the user where companyId = &#63; and defaultUser = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param companyId the company ID
	 * @param defaultUser the default user
	 * @return the matching user, or <code>null</code> if a matching user could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public User fetchByC_DU(long companyId, boolean defaultUser)
		throws SystemException {
		return fetchByC_DU(companyId, defaultUser, true);
	}

	/**
	 * Returns the user where companyId = &#63; and defaultUser = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param companyId the company ID
	 * @param defaultUser the default user
	 * @param retrieveFromCache whether to use the finder cache
	 * @return the matching user, or <code>null</code> if a matching user could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public User fetchByC_DU(long companyId, boolean defaultUser,
		boolean retrieveFromCache) throws SystemException {
		Object[] finderArgs = new Object[] { companyId, defaultUser };

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_C_DU,
					finderArgs, this);
		}

		if (result instanceof User) {
			User user = (User)result;

			if ((companyId != user.getCompanyId()) ||
					(defaultUser != user.getDefaultUser())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_SELECT_USER_WHERE);

			query.append(_FINDER_COLUMN_C_DU_COMPANYID_2);

			query.append(_FINDER_COLUMN_C_DU_DEFAULTUSER_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				qPos.add(defaultUser);

				List<User> list = q.list();

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_DU,
						finderArgs, list);
				}
				else {
					if ((list.size() > 1) && _log.isWarnEnabled()) {
						_log.warn(
							"UserPersistenceImpl.fetchByC_DU(long, boolean, boolean) with parameters (" +
							StringUtil.merge(finderArgs) +
							") yields a result set with more than 1 result. This violates the logical unique restriction. There is no order guarantee on which result is returned by this finder.");
					}

					User user = list.get(0);

					result = user;

					cacheResult(user);

					if ((user.getCompanyId() != companyId) ||
							(user.getDefaultUser() != defaultUser)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_DU,
							finderArgs, user);
					}
				}
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_C_DU,
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
			return (User)result;
		}
	}

	/**
	 * Removes the user where companyId = &#63; and defaultUser = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param defaultUser the default user
	 * @return the user that was removed
	 * @throws SystemException if a system exception occurred
	 */
	public User removeByC_DU(long companyId, boolean defaultUser)
		throws NoSuchUserException, SystemException {
		User user = findByC_DU(companyId, defaultUser);

		return remove(user);
	}

	/**
	 * Returns the number of users where companyId = &#63; and defaultUser = &#63;.
	 *
	 * @param companyId the company ID
	 * @param defaultUser the default user
	 * @return the number of matching users
	 * @throws SystemException if a system exception occurred
	 */
	public int countByC_DU(long companyId, boolean defaultUser)
		throws SystemException {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_C_DU;

		Object[] finderArgs = new Object[] { companyId, defaultUser };

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs,
				this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_USER_WHERE);

			query.append(_FINDER_COLUMN_C_DU_COMPANYID_2);

			query.append(_FINDER_COLUMN_C_DU_DEFAULTUSER_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				qPos.add(defaultUser);

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

	private static final String _FINDER_COLUMN_C_DU_COMPANYID_2 = "user.companyId = ? AND ";
	private static final String _FINDER_COLUMN_C_DU_DEFAULTUSER_2 = "user.defaultUser = ?";
	public static final FinderPath FINDER_PATH_FETCH_BY_C_SN = new FinderPath(UserModelImpl.ENTITY_CACHE_ENABLED,
			UserModelImpl.FINDER_CACHE_ENABLED, UserImpl.class,
			FINDER_CLASS_NAME_ENTITY, "fetchByC_SN",
			new String[] { Long.class.getName(), String.class.getName() },
			UserModelImpl.COMPANYID_COLUMN_BITMASK |
			UserModelImpl.SCREENNAME_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_C_SN = new FinderPath(UserModelImpl.ENTITY_CACHE_ENABLED,
			UserModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_SN",
			new String[] { Long.class.getName(), String.class.getName() });

	/**
	 * Returns the user where companyId = &#63; and screenName = &#63; or throws a {@link com.liferay.portal.NoSuchUserException} if it could not be found.
	 *
	 * @param companyId the company ID
	 * @param screenName the screen name
	 * @return the matching user
	 * @throws com.liferay.portal.NoSuchUserException if a matching user could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public User findByC_SN(long companyId, String screenName)
		throws NoSuchUserException, SystemException {
		User user = fetchByC_SN(companyId, screenName);

		if (user == null) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("companyId=");
			msg.append(companyId);

			msg.append(", screenName=");
			msg.append(screenName);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchUserException(msg.toString());
		}

		return user;
	}

	/**
	 * Returns the user where companyId = &#63; and screenName = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param companyId the company ID
	 * @param screenName the screen name
	 * @return the matching user, or <code>null</code> if a matching user could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public User fetchByC_SN(long companyId, String screenName)
		throws SystemException {
		return fetchByC_SN(companyId, screenName, true);
	}

	/**
	 * Returns the user where companyId = &#63; and screenName = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param companyId the company ID
	 * @param screenName the screen name
	 * @param retrieveFromCache whether to use the finder cache
	 * @return the matching user, or <code>null</code> if a matching user could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public User fetchByC_SN(long companyId, String screenName,
		boolean retrieveFromCache) throws SystemException {
		Object[] finderArgs = new Object[] { companyId, screenName };

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_C_SN,
					finderArgs, this);
		}

		if (result instanceof User) {
			User user = (User)result;

			if ((companyId != user.getCompanyId()) ||
					!Validator.equals(screenName, user.getScreenName())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_SELECT_USER_WHERE);

			query.append(_FINDER_COLUMN_C_SN_COMPANYID_2);

			boolean bindScreenName = false;

			if (screenName == null) {
				query.append(_FINDER_COLUMN_C_SN_SCREENNAME_1);
			}
			else if (screenName.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_C_SN_SCREENNAME_3);
			}
			else {
				bindScreenName = true;

				query.append(_FINDER_COLUMN_C_SN_SCREENNAME_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				if (bindScreenName) {
					qPos.add(screenName);
				}

				List<User> list = q.list();

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_SN,
						finderArgs, list);
				}
				else {
					User user = list.get(0);

					result = user;

					cacheResult(user);

					if ((user.getCompanyId() != companyId) ||
							(user.getScreenName() == null) ||
							!user.getScreenName().equals(screenName)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_SN,
							finderArgs, user);
					}
				}
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_C_SN,
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
			return (User)result;
		}
	}

	/**
	 * Removes the user where companyId = &#63; and screenName = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param screenName the screen name
	 * @return the user that was removed
	 * @throws SystemException if a system exception occurred
	 */
	public User removeByC_SN(long companyId, String screenName)
		throws NoSuchUserException, SystemException {
		User user = findByC_SN(companyId, screenName);

		return remove(user);
	}

	/**
	 * Returns the number of users where companyId = &#63; and screenName = &#63;.
	 *
	 * @param companyId the company ID
	 * @param screenName the screen name
	 * @return the number of matching users
	 * @throws SystemException if a system exception occurred
	 */
	public int countByC_SN(long companyId, String screenName)
		throws SystemException {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_C_SN;

		Object[] finderArgs = new Object[] { companyId, screenName };

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs,
				this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_USER_WHERE);

			query.append(_FINDER_COLUMN_C_SN_COMPANYID_2);

			boolean bindScreenName = false;

			if (screenName == null) {
				query.append(_FINDER_COLUMN_C_SN_SCREENNAME_1);
			}
			else if (screenName.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_C_SN_SCREENNAME_3);
			}
			else {
				bindScreenName = true;

				query.append(_FINDER_COLUMN_C_SN_SCREENNAME_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				if (bindScreenName) {
					qPos.add(screenName);
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

	private static final String _FINDER_COLUMN_C_SN_COMPANYID_2 = "user.companyId = ? AND ";
	private static final String _FINDER_COLUMN_C_SN_SCREENNAME_1 = "user.screenName IS NULL";
	private static final String _FINDER_COLUMN_C_SN_SCREENNAME_2 = "user.screenName = ?";
	private static final String _FINDER_COLUMN_C_SN_SCREENNAME_3 = "(user.screenName IS NULL OR user.screenName = '')";
	public static final FinderPath FINDER_PATH_FETCH_BY_C_EA = new FinderPath(UserModelImpl.ENTITY_CACHE_ENABLED,
			UserModelImpl.FINDER_CACHE_ENABLED, UserImpl.class,
			FINDER_CLASS_NAME_ENTITY, "fetchByC_EA",
			new String[] { Long.class.getName(), String.class.getName() },
			UserModelImpl.COMPANYID_COLUMN_BITMASK |
			UserModelImpl.EMAILADDRESS_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_C_EA = new FinderPath(UserModelImpl.ENTITY_CACHE_ENABLED,
			UserModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_EA",
			new String[] { Long.class.getName(), String.class.getName() });

	/**
	 * Returns the user where companyId = &#63; and emailAddress = &#63; or throws a {@link com.liferay.portal.NoSuchUserException} if it could not be found.
	 *
	 * @param companyId the company ID
	 * @param emailAddress the email address
	 * @return the matching user
	 * @throws com.liferay.portal.NoSuchUserException if a matching user could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public User findByC_EA(long companyId, String emailAddress)
		throws NoSuchUserException, SystemException {
		User user = fetchByC_EA(companyId, emailAddress);

		if (user == null) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("companyId=");
			msg.append(companyId);

			msg.append(", emailAddress=");
			msg.append(emailAddress);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchUserException(msg.toString());
		}

		return user;
	}

	/**
	 * Returns the user where companyId = &#63; and emailAddress = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param companyId the company ID
	 * @param emailAddress the email address
	 * @return the matching user, or <code>null</code> if a matching user could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public User fetchByC_EA(long companyId, String emailAddress)
		throws SystemException {
		return fetchByC_EA(companyId, emailAddress, true);
	}

	/**
	 * Returns the user where companyId = &#63; and emailAddress = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param companyId the company ID
	 * @param emailAddress the email address
	 * @param retrieveFromCache whether to use the finder cache
	 * @return the matching user, or <code>null</code> if a matching user could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public User fetchByC_EA(long companyId, String emailAddress,
		boolean retrieveFromCache) throws SystemException {
		Object[] finderArgs = new Object[] { companyId, emailAddress };

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_C_EA,
					finderArgs, this);
		}

		if (result instanceof User) {
			User user = (User)result;

			if ((companyId != user.getCompanyId()) ||
					!Validator.equals(emailAddress, user.getEmailAddress())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_SELECT_USER_WHERE);

			query.append(_FINDER_COLUMN_C_EA_COMPANYID_2);

			boolean bindEmailAddress = false;

			if (emailAddress == null) {
				query.append(_FINDER_COLUMN_C_EA_EMAILADDRESS_1);
			}
			else if (emailAddress.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_C_EA_EMAILADDRESS_3);
			}
			else {
				bindEmailAddress = true;

				query.append(_FINDER_COLUMN_C_EA_EMAILADDRESS_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				if (bindEmailAddress) {
					qPos.add(emailAddress);
				}

				List<User> list = q.list();

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_EA,
						finderArgs, list);
				}
				else {
					User user = list.get(0);

					result = user;

					cacheResult(user);

					if ((user.getCompanyId() != companyId) ||
							(user.getEmailAddress() == null) ||
							!user.getEmailAddress().equals(emailAddress)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_EA,
							finderArgs, user);
					}
				}
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_C_EA,
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
			return (User)result;
		}
	}

	/**
	 * Removes the user where companyId = &#63; and emailAddress = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param emailAddress the email address
	 * @return the user that was removed
	 * @throws SystemException if a system exception occurred
	 */
	public User removeByC_EA(long companyId, String emailAddress)
		throws NoSuchUserException, SystemException {
		User user = findByC_EA(companyId, emailAddress);

		return remove(user);
	}

	/**
	 * Returns the number of users where companyId = &#63; and emailAddress = &#63;.
	 *
	 * @param companyId the company ID
	 * @param emailAddress the email address
	 * @return the number of matching users
	 * @throws SystemException if a system exception occurred
	 */
	public int countByC_EA(long companyId, String emailAddress)
		throws SystemException {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_C_EA;

		Object[] finderArgs = new Object[] { companyId, emailAddress };

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs,
				this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_USER_WHERE);

			query.append(_FINDER_COLUMN_C_EA_COMPANYID_2);

			boolean bindEmailAddress = false;

			if (emailAddress == null) {
				query.append(_FINDER_COLUMN_C_EA_EMAILADDRESS_1);
			}
			else if (emailAddress.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_C_EA_EMAILADDRESS_3);
			}
			else {
				bindEmailAddress = true;

				query.append(_FINDER_COLUMN_C_EA_EMAILADDRESS_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				if (bindEmailAddress) {
					qPos.add(emailAddress);
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

	private static final String _FINDER_COLUMN_C_EA_COMPANYID_2 = "user.companyId = ? AND ";
	private static final String _FINDER_COLUMN_C_EA_EMAILADDRESS_1 = "user.emailAddress IS NULL";
	private static final String _FINDER_COLUMN_C_EA_EMAILADDRESS_2 = "user.emailAddress = ?";
	private static final String _FINDER_COLUMN_C_EA_EMAILADDRESS_3 = "(user.emailAddress IS NULL OR user.emailAddress = '')";
	public static final FinderPath FINDER_PATH_FETCH_BY_C_FID = new FinderPath(UserModelImpl.ENTITY_CACHE_ENABLED,
			UserModelImpl.FINDER_CACHE_ENABLED, UserImpl.class,
			FINDER_CLASS_NAME_ENTITY, "fetchByC_FID",
			new String[] { Long.class.getName(), Long.class.getName() },
			UserModelImpl.COMPANYID_COLUMN_BITMASK |
			UserModelImpl.FACEBOOKID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_C_FID = new FinderPath(UserModelImpl.ENTITY_CACHE_ENABLED,
			UserModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_FID",
			new String[] { Long.class.getName(), Long.class.getName() });

	/**
	 * Returns the user where companyId = &#63; and facebookId = &#63; or throws a {@link com.liferay.portal.NoSuchUserException} if it could not be found.
	 *
	 * @param companyId the company ID
	 * @param facebookId the facebook ID
	 * @return the matching user
	 * @throws com.liferay.portal.NoSuchUserException if a matching user could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public User findByC_FID(long companyId, long facebookId)
		throws NoSuchUserException, SystemException {
		User user = fetchByC_FID(companyId, facebookId);

		if (user == null) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("companyId=");
			msg.append(companyId);

			msg.append(", facebookId=");
			msg.append(facebookId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchUserException(msg.toString());
		}

		return user;
	}

	/**
	 * Returns the user where companyId = &#63; and facebookId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param companyId the company ID
	 * @param facebookId the facebook ID
	 * @return the matching user, or <code>null</code> if a matching user could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public User fetchByC_FID(long companyId, long facebookId)
		throws SystemException {
		return fetchByC_FID(companyId, facebookId, true);
	}

	/**
	 * Returns the user where companyId = &#63; and facebookId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param companyId the company ID
	 * @param facebookId the facebook ID
	 * @param retrieveFromCache whether to use the finder cache
	 * @return the matching user, or <code>null</code> if a matching user could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public User fetchByC_FID(long companyId, long facebookId,
		boolean retrieveFromCache) throws SystemException {
		Object[] finderArgs = new Object[] { companyId, facebookId };

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_C_FID,
					finderArgs, this);
		}

		if (result instanceof User) {
			User user = (User)result;

			if ((companyId != user.getCompanyId()) ||
					(facebookId != user.getFacebookId())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_SELECT_USER_WHERE);

			query.append(_FINDER_COLUMN_C_FID_COMPANYID_2);

			query.append(_FINDER_COLUMN_C_FID_FACEBOOKID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				qPos.add(facebookId);

				List<User> list = q.list();

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_FID,
						finderArgs, list);
				}
				else {
					if ((list.size() > 1) && _log.isWarnEnabled()) {
						_log.warn(
							"UserPersistenceImpl.fetchByC_FID(long, long, boolean) with parameters (" +
							StringUtil.merge(finderArgs) +
							") yields a result set with more than 1 result. This violates the logical unique restriction. There is no order guarantee on which result is returned by this finder.");
					}

					User user = list.get(0);

					result = user;

					cacheResult(user);

					if ((user.getCompanyId() != companyId) ||
							(user.getFacebookId() != facebookId)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_FID,
							finderArgs, user);
					}
				}
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_C_FID,
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
			return (User)result;
		}
	}

	/**
	 * Removes the user where companyId = &#63; and facebookId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param facebookId the facebook ID
	 * @return the user that was removed
	 * @throws SystemException if a system exception occurred
	 */
	public User removeByC_FID(long companyId, long facebookId)
		throws NoSuchUserException, SystemException {
		User user = findByC_FID(companyId, facebookId);

		return remove(user);
	}

	/**
	 * Returns the number of users where companyId = &#63; and facebookId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param facebookId the facebook ID
	 * @return the number of matching users
	 * @throws SystemException if a system exception occurred
	 */
	public int countByC_FID(long companyId, long facebookId)
		throws SystemException {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_C_FID;

		Object[] finderArgs = new Object[] { companyId, facebookId };

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs,
				this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_USER_WHERE);

			query.append(_FINDER_COLUMN_C_FID_COMPANYID_2);

			query.append(_FINDER_COLUMN_C_FID_FACEBOOKID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				qPos.add(facebookId);

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

	private static final String _FINDER_COLUMN_C_FID_COMPANYID_2 = "user.companyId = ? AND ";
	private static final String _FINDER_COLUMN_C_FID_FACEBOOKID_2 = "user.facebookId = ?";
	public static final FinderPath FINDER_PATH_FETCH_BY_C_O = new FinderPath(UserModelImpl.ENTITY_CACHE_ENABLED,
			UserModelImpl.FINDER_CACHE_ENABLED, UserImpl.class,
			FINDER_CLASS_NAME_ENTITY, "fetchByC_O",
			new String[] { Long.class.getName(), String.class.getName() },
			UserModelImpl.COMPANYID_COLUMN_BITMASK |
			UserModelImpl.OPENID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_C_O = new FinderPath(UserModelImpl.ENTITY_CACHE_ENABLED,
			UserModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_O",
			new String[] { Long.class.getName(), String.class.getName() });

	/**
	 * Returns the user where companyId = &#63; and openId = &#63; or throws a {@link com.liferay.portal.NoSuchUserException} if it could not be found.
	 *
	 * @param companyId the company ID
	 * @param openId the open ID
	 * @return the matching user
	 * @throws com.liferay.portal.NoSuchUserException if a matching user could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public User findByC_O(long companyId, String openId)
		throws NoSuchUserException, SystemException {
		User user = fetchByC_O(companyId, openId);

		if (user == null) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("companyId=");
			msg.append(companyId);

			msg.append(", openId=");
			msg.append(openId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchUserException(msg.toString());
		}

		return user;
	}

	/**
	 * Returns the user where companyId = &#63; and openId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param companyId the company ID
	 * @param openId the open ID
	 * @return the matching user, or <code>null</code> if a matching user could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public User fetchByC_O(long companyId, String openId)
		throws SystemException {
		return fetchByC_O(companyId, openId, true);
	}

	/**
	 * Returns the user where companyId = &#63; and openId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param companyId the company ID
	 * @param openId the open ID
	 * @param retrieveFromCache whether to use the finder cache
	 * @return the matching user, or <code>null</code> if a matching user could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public User fetchByC_O(long companyId, String openId,
		boolean retrieveFromCache) throws SystemException {
		Object[] finderArgs = new Object[] { companyId, openId };

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_C_O,
					finderArgs, this);
		}

		if (result instanceof User) {
			User user = (User)result;

			if ((companyId != user.getCompanyId()) ||
					!Validator.equals(openId, user.getOpenId())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_SELECT_USER_WHERE);

			query.append(_FINDER_COLUMN_C_O_COMPANYID_2);

			boolean bindOpenId = false;

			if (openId == null) {
				query.append(_FINDER_COLUMN_C_O_OPENID_1);
			}
			else if (openId.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_C_O_OPENID_3);
			}
			else {
				bindOpenId = true;

				query.append(_FINDER_COLUMN_C_O_OPENID_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				if (bindOpenId) {
					qPos.add(openId);
				}

				List<User> list = q.list();

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_O,
						finderArgs, list);
				}
				else {
					if ((list.size() > 1) && _log.isWarnEnabled()) {
						_log.warn(
							"UserPersistenceImpl.fetchByC_O(long, String, boolean) with parameters (" +
							StringUtil.merge(finderArgs) +
							") yields a result set with more than 1 result. This violates the logical unique restriction. There is no order guarantee on which result is returned by this finder.");
					}

					User user = list.get(0);

					result = user;

					cacheResult(user);

					if ((user.getCompanyId() != companyId) ||
							(user.getOpenId() == null) ||
							!user.getOpenId().equals(openId)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_O,
							finderArgs, user);
					}
				}
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_C_O,
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
			return (User)result;
		}
	}

	/**
	 * Removes the user where companyId = &#63; and openId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param openId the open ID
	 * @return the user that was removed
	 * @throws SystemException if a system exception occurred
	 */
	public User removeByC_O(long companyId, String openId)
		throws NoSuchUserException, SystemException {
		User user = findByC_O(companyId, openId);

		return remove(user);
	}

	/**
	 * Returns the number of users where companyId = &#63; and openId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param openId the open ID
	 * @return the number of matching users
	 * @throws SystemException if a system exception occurred
	 */
	public int countByC_O(long companyId, String openId)
		throws SystemException {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_C_O;

		Object[] finderArgs = new Object[] { companyId, openId };

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs,
				this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_USER_WHERE);

			query.append(_FINDER_COLUMN_C_O_COMPANYID_2);

			boolean bindOpenId = false;

			if (openId == null) {
				query.append(_FINDER_COLUMN_C_O_OPENID_1);
			}
			else if (openId.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_C_O_OPENID_3);
			}
			else {
				bindOpenId = true;

				query.append(_FINDER_COLUMN_C_O_OPENID_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				if (bindOpenId) {
					qPos.add(openId);
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

	private static final String _FINDER_COLUMN_C_O_COMPANYID_2 = "user.companyId = ? AND ";
	private static final String _FINDER_COLUMN_C_O_OPENID_1 = "user.openId IS NULL";
	private static final String _FINDER_COLUMN_C_O_OPENID_2 = "user.openId = ?";
	private static final String _FINDER_COLUMN_C_O_OPENID_3 = "(user.openId IS NULL OR user.openId = '')";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_C_S = new FinderPath(UserModelImpl.ENTITY_CACHE_ENABLED,
			UserModelImpl.FINDER_CACHE_ENABLED, UserImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByC_S",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_S = new FinderPath(UserModelImpl.ENTITY_CACHE_ENABLED,
			UserModelImpl.FINDER_CACHE_ENABLED, UserImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByC_S",
			new String[] { Long.class.getName(), Integer.class.getName() },
			UserModelImpl.COMPANYID_COLUMN_BITMASK |
			UserModelImpl.STATUS_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_C_S = new FinderPath(UserModelImpl.ENTITY_CACHE_ENABLED,
			UserModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_S",
			new String[] { Long.class.getName(), Integer.class.getName() });

	/**
	 * Returns all the users where companyId = &#63; and status = &#63;.
	 *
	 * @param companyId the company ID
	 * @param status the status
	 * @return the matching users
	 * @throws SystemException if a system exception occurred
	 */
	public List<User> findByC_S(long companyId, int status)
		throws SystemException {
		return findByC_S(companyId, status, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the users where companyId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.UserModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param status the status
	 * @param start the lower bound of the range of users
	 * @param end the upper bound of the range of users (not inclusive)
	 * @return the range of matching users
	 * @throws SystemException if a system exception occurred
	 */
	public List<User> findByC_S(long companyId, int status, int start, int end)
		throws SystemException {
		return findByC_S(companyId, status, start, end, null);
	}

	/**
	 * Returns an ordered range of all the users where companyId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.UserModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param status the status
	 * @param start the lower bound of the range of users
	 * @param end the upper bound of the range of users (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching users
	 * @throws SystemException if a system exception occurred
	 */
	public List<User> findByC_S(long companyId, int status, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_S;
			finderArgs = new Object[] { companyId, status };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_C_S;
			finderArgs = new Object[] {
					companyId, status,
					
					start, end, orderByComparator
				};
		}

		List<User> list = (List<User>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (User user : list) {
				if ((companyId != user.getCompanyId()) ||
						(status != user.getStatus())) {
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

			query.append(_SQL_SELECT_USER_WHERE);

			query.append(_FINDER_COLUMN_C_S_COMPANYID_2);

			query.append(_FINDER_COLUMN_C_S_STATUS_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(UserModelImpl.ORDER_BY_JPQL);
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
					list = (List<User>)QueryUtil.list(q, getDialect(), start,
							end, false);

					Collections.sort(list);

					list = new UnmodifiableList<User>(list);
				}
				else {
					list = (List<User>)QueryUtil.list(q, getDialect(), start,
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
	 * Returns the first user in the ordered set where companyId = &#63; and status = &#63;.
	 *
	 * @param companyId the company ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching user
	 * @throws com.liferay.portal.NoSuchUserException if a matching user could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public User findByC_S_First(long companyId, int status,
		OrderByComparator orderByComparator)
		throws NoSuchUserException, SystemException {
		User user = fetchByC_S_First(companyId, status, orderByComparator);

		if (user != null) {
			return user;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("companyId=");
		msg.append(companyId);

		msg.append(", status=");
		msg.append(status);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchUserException(msg.toString());
	}

	/**
	 * Returns the first user in the ordered set where companyId = &#63; and status = &#63;.
	 *
	 * @param companyId the company ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching user, or <code>null</code> if a matching user could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public User fetchByC_S_First(long companyId, int status,
		OrderByComparator orderByComparator) throws SystemException {
		List<User> list = findByC_S(companyId, status, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last user in the ordered set where companyId = &#63; and status = &#63;.
	 *
	 * @param companyId the company ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching user
	 * @throws com.liferay.portal.NoSuchUserException if a matching user could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public User findByC_S_Last(long companyId, int status,
		OrderByComparator orderByComparator)
		throws NoSuchUserException, SystemException {
		User user = fetchByC_S_Last(companyId, status, orderByComparator);

		if (user != null) {
			return user;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("companyId=");
		msg.append(companyId);

		msg.append(", status=");
		msg.append(status);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchUserException(msg.toString());
	}

	/**
	 * Returns the last user in the ordered set where companyId = &#63; and status = &#63;.
	 *
	 * @param companyId the company ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching user, or <code>null</code> if a matching user could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public User fetchByC_S_Last(long companyId, int status,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByC_S(companyId, status);

		List<User> list = findByC_S(companyId, status, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the users before and after the current user in the ordered set where companyId = &#63; and status = &#63;.
	 *
	 * @param userId the primary key of the current user
	 * @param companyId the company ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next user
	 * @throws com.liferay.portal.NoSuchUserException if a user with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public User[] findByC_S_PrevAndNext(long userId, long companyId,
		int status, OrderByComparator orderByComparator)
		throws NoSuchUserException, SystemException {
		User user = findByPrimaryKey(userId);

		Session session = null;

		try {
			session = openSession();

			User[] array = new UserImpl[3];

			array[0] = getByC_S_PrevAndNext(session, user, companyId, status,
					orderByComparator, true);

			array[1] = user;

			array[2] = getByC_S_PrevAndNext(session, user, companyId, status,
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

	protected User getByC_S_PrevAndNext(Session session, User user,
		long companyId, int status, OrderByComparator orderByComparator,
		boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_USER_WHERE);

		query.append(_FINDER_COLUMN_C_S_COMPANYID_2);

		query.append(_FINDER_COLUMN_C_S_STATUS_2);

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
			query.append(UserModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(companyId);

		qPos.add(status);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(user);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<User> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the users where companyId = &#63; and status = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param status the status
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByC_S(long companyId, int status)
		throws SystemException {
		for (User user : findByC_S(companyId, status, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS, null)) {
			remove(user);
		}
	}

	/**
	 * Returns the number of users where companyId = &#63; and status = &#63;.
	 *
	 * @param companyId the company ID
	 * @param status the status
	 * @return the number of matching users
	 * @throws SystemException if a system exception occurred
	 */
	public int countByC_S(long companyId, int status) throws SystemException {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_C_S;

		Object[] finderArgs = new Object[] { companyId, status };

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs,
				this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_USER_WHERE);

			query.append(_FINDER_COLUMN_C_S_COMPANYID_2);

			query.append(_FINDER_COLUMN_C_S_STATUS_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				qPos.add(status);

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

	private static final String _FINDER_COLUMN_C_S_COMPANYID_2 = "user.companyId = ? AND ";
	private static final String _FINDER_COLUMN_C_S_STATUS_2 = "user.status = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_C_CD_MD = new FinderPath(UserModelImpl.ENTITY_CACHE_ENABLED,
			UserModelImpl.FINDER_CACHE_ENABLED, UserImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByC_CD_MD",
			new String[] {
				Long.class.getName(), Date.class.getName(), Date.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_CD_MD =
		new FinderPath(UserModelImpl.ENTITY_CACHE_ENABLED,
			UserModelImpl.FINDER_CACHE_ENABLED, UserImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByC_CD_MD",
			new String[] {
				Long.class.getName(), Date.class.getName(), Date.class.getName()
			},
			UserModelImpl.COMPANYID_COLUMN_BITMASK |
			UserModelImpl.CREATEDATE_COLUMN_BITMASK |
			UserModelImpl.MODIFIEDDATE_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_C_CD_MD = new FinderPath(UserModelImpl.ENTITY_CACHE_ENABLED,
			UserModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_CD_MD",
			new String[] {
				Long.class.getName(), Date.class.getName(), Date.class.getName()
			});

	/**
	 * Returns all the users where companyId = &#63; and createDate = &#63; and modifiedDate = &#63;.
	 *
	 * @param companyId the company ID
	 * @param createDate the create date
	 * @param modifiedDate the modified date
	 * @return the matching users
	 * @throws SystemException if a system exception occurred
	 */
	public List<User> findByC_CD_MD(long companyId, Date createDate,
		Date modifiedDate) throws SystemException {
		return findByC_CD_MD(companyId, createDate, modifiedDate,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the users where companyId = &#63; and createDate = &#63; and modifiedDate = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.UserModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param createDate the create date
	 * @param modifiedDate the modified date
	 * @param start the lower bound of the range of users
	 * @param end the upper bound of the range of users (not inclusive)
	 * @return the range of matching users
	 * @throws SystemException if a system exception occurred
	 */
	public List<User> findByC_CD_MD(long companyId, Date createDate,
		Date modifiedDate, int start, int end) throws SystemException {
		return findByC_CD_MD(companyId, createDate, modifiedDate, start, end,
			null);
	}

	/**
	 * Returns an ordered range of all the users where companyId = &#63; and createDate = &#63; and modifiedDate = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.UserModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param createDate the create date
	 * @param modifiedDate the modified date
	 * @param start the lower bound of the range of users
	 * @param end the upper bound of the range of users (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching users
	 * @throws SystemException if a system exception occurred
	 */
	public List<User> findByC_CD_MD(long companyId, Date createDate,
		Date modifiedDate, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_CD_MD;
			finderArgs = new Object[] { companyId, createDate, modifiedDate };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_C_CD_MD;
			finderArgs = new Object[] {
					companyId, createDate, modifiedDate,
					
					start, end, orderByComparator
				};
		}

		List<User> list = (List<User>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (User user : list) {
				if ((companyId != user.getCompanyId()) ||
						!Validator.equals(createDate, user.getCreateDate()) ||
						!Validator.equals(modifiedDate, user.getModifiedDate())) {
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

			query.append(_SQL_SELECT_USER_WHERE);

			query.append(_FINDER_COLUMN_C_CD_MD_COMPANYID_2);

			boolean bindCreateDate = false;

			if (createDate == null) {
				query.append(_FINDER_COLUMN_C_CD_MD_CREATEDATE_1);
			}
			else {
				bindCreateDate = true;

				query.append(_FINDER_COLUMN_C_CD_MD_CREATEDATE_2);
			}

			boolean bindModifiedDate = false;

			if (modifiedDate == null) {
				query.append(_FINDER_COLUMN_C_CD_MD_MODIFIEDDATE_1);
			}
			else {
				bindModifiedDate = true;

				query.append(_FINDER_COLUMN_C_CD_MD_MODIFIEDDATE_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(UserModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				if (bindCreateDate) {
					qPos.add(CalendarUtil.getTimestamp(createDate));
				}

				if (bindModifiedDate) {
					qPos.add(CalendarUtil.getTimestamp(modifiedDate));
				}

				if (!pagination) {
					list = (List<User>)QueryUtil.list(q, getDialect(), start,
							end, false);

					Collections.sort(list);

					list = new UnmodifiableList<User>(list);
				}
				else {
					list = (List<User>)QueryUtil.list(q, getDialect(), start,
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
	 * Returns the first user in the ordered set where companyId = &#63; and createDate = &#63; and modifiedDate = &#63;.
	 *
	 * @param companyId the company ID
	 * @param createDate the create date
	 * @param modifiedDate the modified date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching user
	 * @throws com.liferay.portal.NoSuchUserException if a matching user could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public User findByC_CD_MD_First(long companyId, Date createDate,
		Date modifiedDate, OrderByComparator orderByComparator)
		throws NoSuchUserException, SystemException {
		User user = fetchByC_CD_MD_First(companyId, createDate, modifiedDate,
				orderByComparator);

		if (user != null) {
			return user;
		}

		StringBundler msg = new StringBundler(8);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("companyId=");
		msg.append(companyId);

		msg.append(", createDate=");
		msg.append(createDate);

		msg.append(", modifiedDate=");
		msg.append(modifiedDate);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchUserException(msg.toString());
	}

	/**
	 * Returns the first user in the ordered set where companyId = &#63; and createDate = &#63; and modifiedDate = &#63;.
	 *
	 * @param companyId the company ID
	 * @param createDate the create date
	 * @param modifiedDate the modified date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching user, or <code>null</code> if a matching user could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public User fetchByC_CD_MD_First(long companyId, Date createDate,
		Date modifiedDate, OrderByComparator orderByComparator)
		throws SystemException {
		List<User> list = findByC_CD_MD(companyId, createDate, modifiedDate, 0,
				1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last user in the ordered set where companyId = &#63; and createDate = &#63; and modifiedDate = &#63;.
	 *
	 * @param companyId the company ID
	 * @param createDate the create date
	 * @param modifiedDate the modified date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching user
	 * @throws com.liferay.portal.NoSuchUserException if a matching user could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public User findByC_CD_MD_Last(long companyId, Date createDate,
		Date modifiedDate, OrderByComparator orderByComparator)
		throws NoSuchUserException, SystemException {
		User user = fetchByC_CD_MD_Last(companyId, createDate, modifiedDate,
				orderByComparator);

		if (user != null) {
			return user;
		}

		StringBundler msg = new StringBundler(8);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("companyId=");
		msg.append(companyId);

		msg.append(", createDate=");
		msg.append(createDate);

		msg.append(", modifiedDate=");
		msg.append(modifiedDate);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchUserException(msg.toString());
	}

	/**
	 * Returns the last user in the ordered set where companyId = &#63; and createDate = &#63; and modifiedDate = &#63;.
	 *
	 * @param companyId the company ID
	 * @param createDate the create date
	 * @param modifiedDate the modified date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching user, or <code>null</code> if a matching user could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public User fetchByC_CD_MD_Last(long companyId, Date createDate,
		Date modifiedDate, OrderByComparator orderByComparator)
		throws SystemException {
		int count = countByC_CD_MD(companyId, createDate, modifiedDate);

		List<User> list = findByC_CD_MD(companyId, createDate, modifiedDate,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the users before and after the current user in the ordered set where companyId = &#63; and createDate = &#63; and modifiedDate = &#63;.
	 *
	 * @param userId the primary key of the current user
	 * @param companyId the company ID
	 * @param createDate the create date
	 * @param modifiedDate the modified date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next user
	 * @throws com.liferay.portal.NoSuchUserException if a user with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public User[] findByC_CD_MD_PrevAndNext(long userId, long companyId,
		Date createDate, Date modifiedDate, OrderByComparator orderByComparator)
		throws NoSuchUserException, SystemException {
		User user = findByPrimaryKey(userId);

		Session session = null;

		try {
			session = openSession();

			User[] array = new UserImpl[3];

			array[0] = getByC_CD_MD_PrevAndNext(session, user, companyId,
					createDate, modifiedDate, orderByComparator, true);

			array[1] = user;

			array[2] = getByC_CD_MD_PrevAndNext(session, user, companyId,
					createDate, modifiedDate, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected User getByC_CD_MD_PrevAndNext(Session session, User user,
		long companyId, Date createDate, Date modifiedDate,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_USER_WHERE);

		query.append(_FINDER_COLUMN_C_CD_MD_COMPANYID_2);

		boolean bindCreateDate = false;

		if (createDate == null) {
			query.append(_FINDER_COLUMN_C_CD_MD_CREATEDATE_1);
		}
		else {
			bindCreateDate = true;

			query.append(_FINDER_COLUMN_C_CD_MD_CREATEDATE_2);
		}

		boolean bindModifiedDate = false;

		if (modifiedDate == null) {
			query.append(_FINDER_COLUMN_C_CD_MD_MODIFIEDDATE_1);
		}
		else {
			bindModifiedDate = true;

			query.append(_FINDER_COLUMN_C_CD_MD_MODIFIEDDATE_2);
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
			query.append(UserModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(companyId);

		if (bindCreateDate) {
			qPos.add(CalendarUtil.getTimestamp(createDate));
		}

		if (bindModifiedDate) {
			qPos.add(CalendarUtil.getTimestamp(modifiedDate));
		}

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(user);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<User> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the users where companyId = &#63; and createDate = &#63; and modifiedDate = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param createDate the create date
	 * @param modifiedDate the modified date
	 * @throws SystemException if a system exception occurred
	 */
	public void removeByC_CD_MD(long companyId, Date createDate,
		Date modifiedDate) throws SystemException {
		for (User user : findByC_CD_MD(companyId, createDate, modifiedDate,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(user);
		}
	}

	/**
	 * Returns the number of users where companyId = &#63; and createDate = &#63; and modifiedDate = &#63;.
	 *
	 * @param companyId the company ID
	 * @param createDate the create date
	 * @param modifiedDate the modified date
	 * @return the number of matching users
	 * @throws SystemException if a system exception occurred
	 */
	public int countByC_CD_MD(long companyId, Date createDate, Date modifiedDate)
		throws SystemException {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_C_CD_MD;

		Object[] finderArgs = new Object[] { companyId, createDate, modifiedDate };

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs,
				this);

		if (count == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_COUNT_USER_WHERE);

			query.append(_FINDER_COLUMN_C_CD_MD_COMPANYID_2);

			boolean bindCreateDate = false;

			if (createDate == null) {
				query.append(_FINDER_COLUMN_C_CD_MD_CREATEDATE_1);
			}
			else {
				bindCreateDate = true;

				query.append(_FINDER_COLUMN_C_CD_MD_CREATEDATE_2);
			}

			boolean bindModifiedDate = false;

			if (modifiedDate == null) {
				query.append(_FINDER_COLUMN_C_CD_MD_MODIFIEDDATE_1);
			}
			else {
				bindModifiedDate = true;

				query.append(_FINDER_COLUMN_C_CD_MD_MODIFIEDDATE_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				if (bindCreateDate) {
					qPos.add(CalendarUtil.getTimestamp(createDate));
				}

				if (bindModifiedDate) {
					qPos.add(CalendarUtil.getTimestamp(modifiedDate));
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

	private static final String _FINDER_COLUMN_C_CD_MD_COMPANYID_2 = "user.companyId = ? AND ";
	private static final String _FINDER_COLUMN_C_CD_MD_CREATEDATE_1 = "user.createDate IS NULL AND ";
	private static final String _FINDER_COLUMN_C_CD_MD_CREATEDATE_2 = "user.createDate = ? AND ";
	private static final String _FINDER_COLUMN_C_CD_MD_MODIFIEDDATE_1 = "user.modifiedDate IS NULL";
	private static final String _FINDER_COLUMN_C_CD_MD_MODIFIEDDATE_2 = "user.modifiedDate = ?";

	/**
	 * Caches the user in the entity cache if it is enabled.
	 *
	 * @param user the user
	 */
	public void cacheResult(User user) {
		EntityCacheUtil.putResult(UserModelImpl.ENTITY_CACHE_ENABLED,
			UserImpl.class, user.getPrimaryKey(), user);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_CONTACTID,
			new Object[] { user.getContactId() }, user);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_PORTRAITID,
			new Object[] { user.getPortraitId() }, user);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_U,
			new Object[] { user.getCompanyId(), user.getUserId() }, user);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_DU,
			new Object[] { user.getCompanyId(), user.getDefaultUser() }, user);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_SN,
			new Object[] { user.getCompanyId(), user.getScreenName() }, user);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_EA,
			new Object[] { user.getCompanyId(), user.getEmailAddress() }, user);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_FID,
			new Object[] { user.getCompanyId(), user.getFacebookId() }, user);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_O,
			new Object[] { user.getCompanyId(), user.getOpenId() }, user);

		user.resetOriginalValues();
	}

	/**
	 * Caches the users in the entity cache if it is enabled.
	 *
	 * @param users the users
	 */
	public void cacheResult(List<User> users) {
		for (User user : users) {
			if (EntityCacheUtil.getResult(UserModelImpl.ENTITY_CACHE_ENABLED,
						UserImpl.class, user.getPrimaryKey()) == null) {
				cacheResult(user);
			}
			else {
				user.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all users.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		if (_HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			CacheRegistryUtil.clear(UserImpl.class.getName());
		}

		EntityCacheUtil.clearCache(UserImpl.class.getName());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the user.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(User user) {
		EntityCacheUtil.removeResult(UserModelImpl.ENTITY_CACHE_ENABLED,
			UserImpl.class, user.getPrimaryKey());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache(user);
	}

	@Override
	public void clearCache(List<User> users) {
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (User user : users) {
			EntityCacheUtil.removeResult(UserModelImpl.ENTITY_CACHE_ENABLED,
				UserImpl.class, user.getPrimaryKey());

			clearUniqueFindersCache(user);
		}
	}

	protected void cacheUniqueFindersCache(User user) {
		if (user.isNew()) {
			Object[] args = new Object[] { user.getContactId() };

			FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_CONTACTID, args,
				Long.valueOf(1));
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_CONTACTID, args, user);

			args = new Object[] { user.getPortraitId() };

			FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_PORTRAITID, args,
				Long.valueOf(1));
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_PORTRAITID, args,
				user);

			args = new Object[] { user.getCompanyId(), user.getUserId() };

			FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_C_U, args,
				Long.valueOf(1));
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_U, args, user);

			args = new Object[] { user.getCompanyId(), user.getDefaultUser() };

			FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_C_DU, args,
				Long.valueOf(1));
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_DU, args, user);

			args = new Object[] { user.getCompanyId(), user.getScreenName() };

			FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_C_SN, args,
				Long.valueOf(1));
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_SN, args, user);

			args = new Object[] { user.getCompanyId(), user.getEmailAddress() };

			FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_C_EA, args,
				Long.valueOf(1));
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_EA, args, user);

			args = new Object[] { user.getCompanyId(), user.getFacebookId() };

			FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_C_FID, args,
				Long.valueOf(1));
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_FID, args, user);

			args = new Object[] { user.getCompanyId(), user.getOpenId() };

			FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_C_O, args,
				Long.valueOf(1));
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_O, args, user);
		}
		else {
			UserModelImpl userModelImpl = (UserModelImpl)user;

			if ((userModelImpl.getColumnBitmask() &
					FINDER_PATH_FETCH_BY_CONTACTID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] { user.getContactId() };

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_CONTACTID, args,
					Long.valueOf(1));
				FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_CONTACTID, args,
					user);
			}

			if ((userModelImpl.getColumnBitmask() &
					FINDER_PATH_FETCH_BY_PORTRAITID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] { user.getPortraitId() };

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_PORTRAITID,
					args, Long.valueOf(1));
				FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_PORTRAITID,
					args, user);
			}

			if ((userModelImpl.getColumnBitmask() &
					FINDER_PATH_FETCH_BY_C_U.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						user.getCompanyId(), user.getUserId()
					};

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_C_U, args,
					Long.valueOf(1));
				FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_U, args, user);
			}

			if ((userModelImpl.getColumnBitmask() &
					FINDER_PATH_FETCH_BY_C_DU.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						user.getCompanyId(), user.getDefaultUser()
					};

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_C_DU, args,
					Long.valueOf(1));
				FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_DU, args, user);
			}

			if ((userModelImpl.getColumnBitmask() &
					FINDER_PATH_FETCH_BY_C_SN.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						user.getCompanyId(), user.getScreenName()
					};

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_C_SN, args,
					Long.valueOf(1));
				FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_SN, args, user);
			}

			if ((userModelImpl.getColumnBitmask() &
					FINDER_PATH_FETCH_BY_C_EA.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						user.getCompanyId(), user.getEmailAddress()
					};

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_C_EA, args,
					Long.valueOf(1));
				FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_EA, args, user);
			}

			if ((userModelImpl.getColumnBitmask() &
					FINDER_PATH_FETCH_BY_C_FID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						user.getCompanyId(), user.getFacebookId()
					};

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_C_FID, args,
					Long.valueOf(1));
				FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_FID, args, user);
			}

			if ((userModelImpl.getColumnBitmask() &
					FINDER_PATH_FETCH_BY_C_O.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						user.getCompanyId(), user.getOpenId()
					};

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_C_O, args,
					Long.valueOf(1));
				FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_O, args, user);
			}
		}
	}

	protected void clearUniqueFindersCache(User user) {
		UserModelImpl userModelImpl = (UserModelImpl)user;

		Object[] args = new Object[] { user.getContactId() };

		FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_CONTACTID, args);
		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_CONTACTID, args);

		if ((userModelImpl.getColumnBitmask() &
				FINDER_PATH_FETCH_BY_CONTACTID.getColumnBitmask()) != 0) {
			args = new Object[] { userModelImpl.getOriginalContactId() };

			FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_CONTACTID, args);
			FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_CONTACTID, args);
		}

		args = new Object[] { user.getPortraitId() };

		FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_PORTRAITID, args);
		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_PORTRAITID, args);

		if ((userModelImpl.getColumnBitmask() &
				FINDER_PATH_FETCH_BY_PORTRAITID.getColumnBitmask()) != 0) {
			args = new Object[] { userModelImpl.getOriginalPortraitId() };

			FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_PORTRAITID, args);
			FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_PORTRAITID, args);
		}

		args = new Object[] { user.getCompanyId(), user.getUserId() };

		FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_C_U, args);
		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_C_U, args);

		if ((userModelImpl.getColumnBitmask() &
				FINDER_PATH_FETCH_BY_C_U.getColumnBitmask()) != 0) {
			args = new Object[] {
					userModelImpl.getOriginalCompanyId(),
					userModelImpl.getOriginalUserId()
				};

			FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_C_U, args);
			FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_C_U, args);
		}

		args = new Object[] { user.getCompanyId(), user.getDefaultUser() };

		FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_C_DU, args);
		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_C_DU, args);

		if ((userModelImpl.getColumnBitmask() &
				FINDER_PATH_FETCH_BY_C_DU.getColumnBitmask()) != 0) {
			args = new Object[] {
					userModelImpl.getOriginalCompanyId(),
					userModelImpl.getOriginalDefaultUser()
				};

			FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_C_DU, args);
			FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_C_DU, args);
		}

		args = new Object[] { user.getCompanyId(), user.getScreenName() };

		FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_C_SN, args);
		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_C_SN, args);

		if ((userModelImpl.getColumnBitmask() &
				FINDER_PATH_FETCH_BY_C_SN.getColumnBitmask()) != 0) {
			args = new Object[] {
					userModelImpl.getOriginalCompanyId(),
					userModelImpl.getOriginalScreenName()
				};

			FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_C_SN, args);
			FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_C_SN, args);
		}

		args = new Object[] { user.getCompanyId(), user.getEmailAddress() };

		FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_C_EA, args);
		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_C_EA, args);

		if ((userModelImpl.getColumnBitmask() &
				FINDER_PATH_FETCH_BY_C_EA.getColumnBitmask()) != 0) {
			args = new Object[] {
					userModelImpl.getOriginalCompanyId(),
					userModelImpl.getOriginalEmailAddress()
				};

			FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_C_EA, args);
			FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_C_EA, args);
		}

		args = new Object[] { user.getCompanyId(), user.getFacebookId() };

		FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_C_FID, args);
		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_C_FID, args);

		if ((userModelImpl.getColumnBitmask() &
				FINDER_PATH_FETCH_BY_C_FID.getColumnBitmask()) != 0) {
			args = new Object[] {
					userModelImpl.getOriginalCompanyId(),
					userModelImpl.getOriginalFacebookId()
				};

			FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_C_FID, args);
			FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_C_FID, args);
		}

		args = new Object[] { user.getCompanyId(), user.getOpenId() };

		FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_C_O, args);
		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_C_O, args);

		if ((userModelImpl.getColumnBitmask() &
				FINDER_PATH_FETCH_BY_C_O.getColumnBitmask()) != 0) {
			args = new Object[] {
					userModelImpl.getOriginalCompanyId(),
					userModelImpl.getOriginalOpenId()
				};

			FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_C_O, args);
			FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_C_O, args);
		}
	}

	/**
	 * Creates a new user with the primary key. Does not add the user to the database.
	 *
	 * @param userId the primary key for the new user
	 * @return the new user
	 */
	public User create(long userId) {
		User user = new UserImpl();

		user.setNew(true);
		user.setPrimaryKey(userId);

		String uuid = PortalUUIDUtil.generate();

		user.setUuid(uuid);

		return user;
	}

	/**
	 * Removes the user with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param userId the primary key of the user
	 * @return the user that was removed
	 * @throws com.liferay.portal.NoSuchUserException if a user with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public User remove(long userId) throws NoSuchUserException, SystemException {
		return remove((Serializable)userId);
	}

	/**
	 * Removes the user with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the user
	 * @return the user that was removed
	 * @throws com.liferay.portal.NoSuchUserException if a user with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public User remove(Serializable primaryKey)
		throws NoSuchUserException, SystemException {
		Session session = null;

		try {
			session = openSession();

			User user = (User)session.get(UserImpl.class, primaryKey);

			if (user == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchUserException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(user);
		}
		catch (NoSuchUserException nsee) {
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
	protected User removeImpl(User user) throws SystemException {
		user = toUnwrappedModel(user);

		try {
			clearGroups.clear(user.getPrimaryKey());
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(UserModelImpl.MAPPING_TABLE_USERS_GROUPS_NAME);
		}

		try {
			clearOrganizations.clear(user.getPrimaryKey());
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(UserModelImpl.MAPPING_TABLE_USERS_ORGS_NAME);
		}

		try {
			clearRoles.clear(user.getPrimaryKey());
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(UserModelImpl.MAPPING_TABLE_USERS_ROLES_NAME);
		}

		try {
			clearTeams.clear(user.getPrimaryKey());
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(UserModelImpl.MAPPING_TABLE_USERS_TEAMS_NAME);
		}

		try {
			clearUserGroups.clear(user.getPrimaryKey());
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(UserModelImpl.MAPPING_TABLE_USERS_USERGROUPS_NAME);
		}

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(user)) {
				user = (User)session.get(UserImpl.class, user.getPrimaryKeyObj());
			}

			if (user != null) {
				session.delete(user);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (user != null) {
			clearCache(user);
		}

		return user;
	}

	@Override
	public User updateImpl(com.liferay.portal.model.User user)
		throws SystemException {
		user = toUnwrappedModel(user);

		boolean isNew = user.isNew();

		UserModelImpl userModelImpl = (UserModelImpl)user;

		if (Validator.isNull(user.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			user.setUuid(uuid);
		}

		Session session = null;

		try {
			session = openSession();

			if (user.isNew()) {
				session.save(user);

				user.setNew(false);
			}
			else {
				session.merge(user);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (isNew || !UserModelImpl.COLUMN_BITMASK_ENABLED) {
			FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}

		else {
			if ((userModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] { userModelImpl.getOriginalUuid() };

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_UUID, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID,
					args);

				args = new Object[] { userModelImpl.getUuid() };

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_UUID, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID,
					args);
			}

			if ((userModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						userModelImpl.getOriginalUuid(),
						userModelImpl.getOriginalCompanyId()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_UUID_C, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C,
					args);

				args = new Object[] {
						userModelImpl.getUuid(), userModelImpl.getCompanyId()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_UUID_C, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C,
					args);
			}

			if ((userModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						userModelImpl.getOriginalCompanyId()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_COMPANYID,
					args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID,
					args);

				args = new Object[] { userModelImpl.getCompanyId() };

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_COMPANYID,
					args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID,
					args);
			}

			if ((userModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_EMAILADDRESS.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						userModelImpl.getOriginalEmailAddress()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_EMAILADDRESS,
					args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_EMAILADDRESS,
					args);

				args = new Object[] { userModelImpl.getEmailAddress() };

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_EMAILADDRESS,
					args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_EMAILADDRESS,
					args);
			}

			if ((userModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_CD.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						userModelImpl.getOriginalCompanyId(),
						userModelImpl.getOriginalCreateDate()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_C_CD, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_CD,
					args);

				args = new Object[] {
						userModelImpl.getCompanyId(),
						userModelImpl.getCreateDate()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_C_CD, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_CD,
					args);
			}

			if ((userModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_MD.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						userModelImpl.getOriginalCompanyId(),
						userModelImpl.getOriginalModifiedDate()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_C_MD, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_MD,
					args);

				args = new Object[] {
						userModelImpl.getCompanyId(),
						userModelImpl.getModifiedDate()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_C_MD, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_MD,
					args);
			}

			if ((userModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_S.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						userModelImpl.getOriginalCompanyId(),
						userModelImpl.getOriginalStatus()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_C_S, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_S,
					args);

				args = new Object[] {
						userModelImpl.getCompanyId(), userModelImpl.getStatus()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_C_S, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_S,
					args);
			}

			if ((userModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_CD_MD.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						userModelImpl.getOriginalCompanyId(),
						userModelImpl.getOriginalCreateDate(),
						userModelImpl.getOriginalModifiedDate()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_C_CD_MD, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_CD_MD,
					args);

				args = new Object[] {
						userModelImpl.getCompanyId(),
						userModelImpl.getCreateDate(),
						userModelImpl.getModifiedDate()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_C_CD_MD, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_CD_MD,
					args);
			}
		}

		EntityCacheUtil.putResult(UserModelImpl.ENTITY_CACHE_ENABLED,
			UserImpl.class, user.getPrimaryKey(), user);

		clearUniqueFindersCache(user);
		cacheUniqueFindersCache(user);

		return user;
	}

	protected User toUnwrappedModel(User user) {
		if (user instanceof UserImpl) {
			return user;
		}

		UserImpl userImpl = new UserImpl();

		userImpl.setNew(user.isNew());
		userImpl.setPrimaryKey(user.getPrimaryKey());

		userImpl.setUuid(user.getUuid());
		userImpl.setUserId(user.getUserId());
		userImpl.setCompanyId(user.getCompanyId());
		userImpl.setCreateDate(user.getCreateDate());
		userImpl.setModifiedDate(user.getModifiedDate());
		userImpl.setDefaultUser(user.isDefaultUser());
		userImpl.setContactId(user.getContactId());
		userImpl.setPassword(user.getPassword());
		userImpl.setPasswordEncrypted(user.isPasswordEncrypted());
		userImpl.setPasswordReset(user.isPasswordReset());
		userImpl.setPasswordModifiedDate(user.getPasswordModifiedDate());
		userImpl.setDigest(user.getDigest());
		userImpl.setReminderQueryQuestion(user.getReminderQueryQuestion());
		userImpl.setReminderQueryAnswer(user.getReminderQueryAnswer());
		userImpl.setGraceLoginCount(user.getGraceLoginCount());
		userImpl.setScreenName(user.getScreenName());
		userImpl.setEmailAddress(user.getEmailAddress());
		userImpl.setFacebookId(user.getFacebookId());
		userImpl.setLdapServerId(user.getLdapServerId());
		userImpl.setOpenId(user.getOpenId());
		userImpl.setPortraitId(user.getPortraitId());
		userImpl.setLanguageId(user.getLanguageId());
		userImpl.setTimeZoneId(user.getTimeZoneId());
		userImpl.setGreeting(user.getGreeting());
		userImpl.setComments(user.getComments());
		userImpl.setFirstName(user.getFirstName());
		userImpl.setMiddleName(user.getMiddleName());
		userImpl.setLastName(user.getLastName());
		userImpl.setJobTitle(user.getJobTitle());
		userImpl.setLoginDate(user.getLoginDate());
		userImpl.setLoginIP(user.getLoginIP());
		userImpl.setLastLoginDate(user.getLastLoginDate());
		userImpl.setLastLoginIP(user.getLastLoginIP());
		userImpl.setLastFailedLoginDate(user.getLastFailedLoginDate());
		userImpl.setFailedLoginAttempts(user.getFailedLoginAttempts());
		userImpl.setLockout(user.isLockout());
		userImpl.setLockoutDate(user.getLockoutDate());
		userImpl.setAgreedToTermsOfUse(user.isAgreedToTermsOfUse());
		userImpl.setEmailAddressVerified(user.isEmailAddressVerified());
		userImpl.setStatus(user.getStatus());

		return userImpl;
	}

	/**
	 * Returns the user with the primary key or throws a {@link com.liferay.portal.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the user
	 * @return the user
	 * @throws com.liferay.portal.NoSuchUserException if a user with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public User findByPrimaryKey(Serializable primaryKey)
		throws NoSuchUserException, SystemException {
		User user = fetchByPrimaryKey(primaryKey);

		if (user == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchUserException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				primaryKey);
		}

		return user;
	}

	/**
	 * Returns the user with the primary key or throws a {@link com.liferay.portal.NoSuchUserException} if it could not be found.
	 *
	 * @param userId the primary key of the user
	 * @return the user
	 * @throws com.liferay.portal.NoSuchUserException if a user with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public User findByPrimaryKey(long userId)
		throws NoSuchUserException, SystemException {
		return findByPrimaryKey((Serializable)userId);
	}

	/**
	 * Returns the user with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the user
	 * @return the user, or <code>null</code> if a user with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public User fetchByPrimaryKey(Serializable primaryKey)
		throws SystemException {
		User user = (User)EntityCacheUtil.getResult(UserModelImpl.ENTITY_CACHE_ENABLED,
				UserImpl.class, primaryKey);

		if (user == _nullUser) {
			return null;
		}

		if (user == null) {
			Session session = null;

			try {
				session = openSession();

				user = (User)session.get(UserImpl.class, primaryKey);

				if (user != null) {
					cacheResult(user);
				}
				else {
					EntityCacheUtil.putResult(UserModelImpl.ENTITY_CACHE_ENABLED,
						UserImpl.class, primaryKey, _nullUser);
				}
			}
			catch (Exception e) {
				EntityCacheUtil.removeResult(UserModelImpl.ENTITY_CACHE_ENABLED,
					UserImpl.class, primaryKey);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return user;
	}

	/**
	 * Returns the user with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param userId the primary key of the user
	 * @return the user, or <code>null</code> if a user with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public User fetchByPrimaryKey(long userId) throws SystemException {
		return fetchByPrimaryKey((Serializable)userId);
	}

	/**
	 * Returns all the users.
	 *
	 * @return the users
	 * @throws SystemException if a system exception occurred
	 */
	public List<User> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the users.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.UserModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of users
	 * @param end the upper bound of the range of users (not inclusive)
	 * @return the range of users
	 * @throws SystemException if a system exception occurred
	 */
	public List<User> findAll(int start, int end) throws SystemException {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the users.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.UserModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of users
	 * @param end the upper bound of the range of users (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of users
	 * @throws SystemException if a system exception occurred
	 */
	public List<User> findAll(int start, int end,
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

		List<User> list = (List<User>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 3));

				query.append(_SQL_SELECT_USER);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_USER;

				if (pagination) {
					sql = sql.concat(UserModelImpl.ORDER_BY_JPQL);
				}
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (!pagination) {
					list = (List<User>)QueryUtil.list(q, getDialect(), start,
							end, false);

					Collections.sort(list);

					list = new UnmodifiableList<User>(list);
				}
				else {
					list = (List<User>)QueryUtil.list(q, getDialect(), start,
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
	 * Removes all the users from the database.
	 *
	 * @throws SystemException if a system exception occurred
	 */
	public void removeAll() throws SystemException {
		for (User user : findAll()) {
			remove(user);
		}
	}

	/**
	 * Returns the number of users.
	 *
	 * @return the number of users
	 * @throws SystemException if a system exception occurred
	 */
	public int countAll() throws SystemException {
		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_USER);

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
	 * Returns all the groups associated with the user.
	 *
	 * @param pk the primary key of the user
	 * @return the groups associated with the user
	 * @throws SystemException if a system exception occurred
	 */
	public List<com.liferay.portal.model.Group> getGroups(long pk)
		throws SystemException {
		return getGroups(pk, QueryUtil.ALL_POS, QueryUtil.ALL_POS);
	}

	/**
	 * Returns a range of all the groups associated with the user.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.UserModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param pk the primary key of the user
	 * @param start the lower bound of the range of users
	 * @param end the upper bound of the range of users (not inclusive)
	 * @return the range of groups associated with the user
	 * @throws SystemException if a system exception occurred
	 */
	public List<com.liferay.portal.model.Group> getGroups(long pk, int start,
		int end) throws SystemException {
		return getGroups(pk, start, end, null);
	}

	public static final FinderPath FINDER_PATH_GET_GROUPS = new FinderPath(com.liferay.portal.model.impl.GroupModelImpl.ENTITY_CACHE_ENABLED,
			UserModelImpl.FINDER_CACHE_ENABLED_USERS_GROUPS,
			com.liferay.portal.model.impl.GroupImpl.class,
			UserModelImpl.MAPPING_TABLE_USERS_GROUPS_NAME, "getGroups",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

	static {
		FINDER_PATH_GET_GROUPS.setCacheKeyGeneratorCacheName(null);
	}

	/**
	 * Returns an ordered range of all the groups associated with the user.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.UserModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param pk the primary key of the user
	 * @param start the lower bound of the range of users
	 * @param end the upper bound of the range of users (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of groups associated with the user
	 * @throws SystemException if a system exception occurred
	 */
	public List<com.liferay.portal.model.Group> getGroups(long pk, int start,
		int end, OrderByComparator orderByComparator) throws SystemException {
		boolean pagination = true;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderArgs = new Object[] { pk };
		}
		else {
			finderArgs = new Object[] { pk, start, end, orderByComparator };
		}

		List<com.liferay.portal.model.Group> list = (List<com.liferay.portal.model.Group>)FinderCacheUtil.getResult(FINDER_PATH_GET_GROUPS,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				String sql = null;

				if (orderByComparator != null) {
					sql = _SQL_GETGROUPS.concat(ORDER_BY_CLAUSE)
										.concat(orderByComparator.getOrderBy());
				}
				else {
					sql = _SQL_GETGROUPS;

					if (pagination) {
						sql = sql.concat(com.liferay.portal.model.impl.GroupModelImpl.ORDER_BY_SQL);
					}
				}

				SQLQuery q = session.createSQLQuery(sql);

				q.addEntity("Group_",
					com.liferay.portal.model.impl.GroupImpl.class);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(pk);

				if (!pagination) {
					list = (List<com.liferay.portal.model.Group>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = new UnmodifiableList<com.liferay.portal.model.Group>(list);
				}
				else {
					list = (List<com.liferay.portal.model.Group>)QueryUtil.list(q,
							getDialect(), start, end);
				}

				groupPersistence.cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_GET_GROUPS, finderArgs,
					list);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(FINDER_PATH_GET_GROUPS, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	public static final FinderPath FINDER_PATH_GET_GROUPS_SIZE = new FinderPath(com.liferay.portal.model.impl.GroupModelImpl.ENTITY_CACHE_ENABLED,
			UserModelImpl.FINDER_CACHE_ENABLED_USERS_GROUPS, Long.class,
			UserModelImpl.MAPPING_TABLE_USERS_GROUPS_NAME, "getGroupsSize",
			new String[] { Long.class.getName() });

	static {
		FINDER_PATH_GET_GROUPS_SIZE.setCacheKeyGeneratorCacheName(null);
	}

	/**
	 * Returns the number of groups associated with the user.
	 *
	 * @param pk the primary key of the user
	 * @return the number of groups associated with the user
	 * @throws SystemException if a system exception occurred
	 */
	public int getGroupsSize(long pk) throws SystemException {
		Object[] finderArgs = new Object[] { pk };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_GET_GROUPS_SIZE,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				SQLQuery q = session.createSQLQuery(_SQL_GETGROUPSSIZE);

				q.addScalar(COUNT_COLUMN_NAME,
					com.liferay.portal.kernel.dao.orm.Type.LONG);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(pk);

				count = (Long)q.uniqueResult();

				FinderCacheUtil.putResult(FINDER_PATH_GET_GROUPS_SIZE,
					finderArgs, count);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(FINDER_PATH_GET_GROUPS_SIZE,
					finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	public static final FinderPath FINDER_PATH_CONTAINS_GROUP = new FinderPath(com.liferay.portal.model.impl.GroupModelImpl.ENTITY_CACHE_ENABLED,
			UserModelImpl.FINDER_CACHE_ENABLED_USERS_GROUPS, Boolean.class,
			UserModelImpl.MAPPING_TABLE_USERS_GROUPS_NAME, "containsGroup",
			new String[] { Long.class.getName(), Long.class.getName() });

	/**
	 * Returns <code>true</code> if the group is associated with the user.
	 *
	 * @param pk the primary key of the user
	 * @param groupPK the primary key of the group
	 * @return <code>true</code> if the group is associated with the user; <code>false</code> otherwise
	 * @throws SystemException if a system exception occurred
	 */
	public boolean containsGroup(long pk, long groupPK)
		throws SystemException {
		Object[] finderArgs = new Object[] { pk, groupPK };

		Boolean value = (Boolean)FinderCacheUtil.getResult(FINDER_PATH_CONTAINS_GROUP,
				finderArgs, this);

		if (value == null) {
			try {
				value = Boolean.valueOf(containsGroup.contains(pk, groupPK));

				FinderCacheUtil.putResult(FINDER_PATH_CONTAINS_GROUP,
					finderArgs, value);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(FINDER_PATH_CONTAINS_GROUP,
					finderArgs);

				throw processException(e);
			}
		}

		return value.booleanValue();
	}

	/**
	 * Returns <code>true</code> if the user has any groups associated with it.
	 *
	 * @param pk the primary key of the user to check for associations with groups
	 * @return <code>true</code> if the user has any groups associated with it; <code>false</code> otherwise
	 * @throws SystemException if a system exception occurred
	 */
	public boolean containsGroups(long pk) throws SystemException {
		if (getGroupsSize(pk) > 0) {
			return true;
		}
		else {
			return false;
		}
	}

	/**
	 * Adds an association between the user and the group. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the user
	 * @param groupPK the primary key of the group
	 * @throws SystemException if a system exception occurred
	 */
	public void addGroup(long pk, long groupPK) throws SystemException {
		try {
			addGroup.add(pk, groupPK);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(UserModelImpl.MAPPING_TABLE_USERS_GROUPS_NAME);
		}
	}

	/**
	 * Adds an association between the user and the group. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the user
	 * @param group the group
	 * @throws SystemException if a system exception occurred
	 */
	public void addGroup(long pk, com.liferay.portal.model.Group group)
		throws SystemException {
		try {
			addGroup.add(pk, group.getPrimaryKey());
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(UserModelImpl.MAPPING_TABLE_USERS_GROUPS_NAME);
		}
	}

	/**
	 * Adds an association between the user and the groups. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the user
	 * @param groupPKs the primary keys of the groups
	 * @throws SystemException if a system exception occurred
	 */
	public void addGroups(long pk, long[] groupPKs) throws SystemException {
		try {
			for (long groupPK : groupPKs) {
				addGroup.add(pk, groupPK);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(UserModelImpl.MAPPING_TABLE_USERS_GROUPS_NAME);
		}
	}

	/**
	 * Adds an association between the user and the groups. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the user
	 * @param groups the groups
	 * @throws SystemException if a system exception occurred
	 */
	public void addGroups(long pk, List<com.liferay.portal.model.Group> groups)
		throws SystemException {
		try {
			for (com.liferay.portal.model.Group group : groups) {
				addGroup.add(pk, group.getPrimaryKey());
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(UserModelImpl.MAPPING_TABLE_USERS_GROUPS_NAME);
		}
	}

	/**
	 * Clears all associations between the user and its groups. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the user to clear the associated groups from
	 * @throws SystemException if a system exception occurred
	 */
	public void clearGroups(long pk) throws SystemException {
		try {
			clearGroups.clear(pk);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(UserModelImpl.MAPPING_TABLE_USERS_GROUPS_NAME);
		}
	}

	/**
	 * Removes the association between the user and the group. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the user
	 * @param groupPK the primary key of the group
	 * @throws SystemException if a system exception occurred
	 */
	public void removeGroup(long pk, long groupPK) throws SystemException {
		try {
			removeGroup.remove(pk, groupPK);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(UserModelImpl.MAPPING_TABLE_USERS_GROUPS_NAME);
		}
	}

	/**
	 * Removes the association between the user and the group. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the user
	 * @param group the group
	 * @throws SystemException if a system exception occurred
	 */
	public void removeGroup(long pk, com.liferay.portal.model.Group group)
		throws SystemException {
		try {
			removeGroup.remove(pk, group.getPrimaryKey());
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(UserModelImpl.MAPPING_TABLE_USERS_GROUPS_NAME);
		}
	}

	/**
	 * Removes the association between the user and the groups. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the user
	 * @param groupPKs the primary keys of the groups
	 * @throws SystemException if a system exception occurred
	 */
	public void removeGroups(long pk, long[] groupPKs)
		throws SystemException {
		try {
			for (long groupPK : groupPKs) {
				removeGroup.remove(pk, groupPK);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(UserModelImpl.MAPPING_TABLE_USERS_GROUPS_NAME);
		}
	}

	/**
	 * Removes the association between the user and the groups. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the user
	 * @param groups the groups
	 * @throws SystemException if a system exception occurred
	 */
	public void removeGroups(long pk,
		List<com.liferay.portal.model.Group> groups) throws SystemException {
		try {
			for (com.liferay.portal.model.Group group : groups) {
				removeGroup.remove(pk, group.getPrimaryKey());
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(UserModelImpl.MAPPING_TABLE_USERS_GROUPS_NAME);
		}
	}

	/**
	 * Sets the groups associated with the user, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the user
	 * @param groupPKs the primary keys of the groups to be associated with the user
	 * @throws SystemException if a system exception occurred
	 */
	public void setGroups(long pk, long[] groupPKs) throws SystemException {
		try {
			Set<Long> groupPKSet = SetUtil.fromArray(groupPKs);

			List<com.liferay.portal.model.Group> groups = getGroups(pk);

			for (com.liferay.portal.model.Group group : groups) {
				if (!groupPKSet.remove(group.getPrimaryKey())) {
					removeGroup.remove(pk, group.getPrimaryKey());
				}
			}

			for (Long groupPK : groupPKSet) {
				addGroup.add(pk, groupPK);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(UserModelImpl.MAPPING_TABLE_USERS_GROUPS_NAME);
		}
	}

	/**
	 * Sets the groups associated with the user, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the user
	 * @param groups the groups to be associated with the user
	 * @throws SystemException if a system exception occurred
	 */
	public void setGroups(long pk, List<com.liferay.portal.model.Group> groups)
		throws SystemException {
		try {
			long[] groupPKs = new long[groups.size()];

			for (int i = 0; i < groups.size(); i++) {
				com.liferay.portal.model.Group group = groups.get(i);

				groupPKs[i] = group.getPrimaryKey();
			}

			setGroups(pk, groupPKs);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(UserModelImpl.MAPPING_TABLE_USERS_GROUPS_NAME);
		}
	}

	/**
	 * Returns all the organizations associated with the user.
	 *
	 * @param pk the primary key of the user
	 * @return the organizations associated with the user
	 * @throws SystemException if a system exception occurred
	 */
	public List<com.liferay.portal.model.Organization> getOrganizations(long pk)
		throws SystemException {
		return getOrganizations(pk, QueryUtil.ALL_POS, QueryUtil.ALL_POS);
	}

	/**
	 * Returns a range of all the organizations associated with the user.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.UserModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param pk the primary key of the user
	 * @param start the lower bound of the range of users
	 * @param end the upper bound of the range of users (not inclusive)
	 * @return the range of organizations associated with the user
	 * @throws SystemException if a system exception occurred
	 */
	public List<com.liferay.portal.model.Organization> getOrganizations(
		long pk, int start, int end) throws SystemException {
		return getOrganizations(pk, start, end, null);
	}

	public static final FinderPath FINDER_PATH_GET_ORGANIZATIONS = new FinderPath(com.liferay.portal.model.impl.OrganizationModelImpl.ENTITY_CACHE_ENABLED,
			UserModelImpl.FINDER_CACHE_ENABLED_USERS_ORGS,
			com.liferay.portal.model.impl.OrganizationImpl.class,
			UserModelImpl.MAPPING_TABLE_USERS_ORGS_NAME, "getOrganizations",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

	static {
		FINDER_PATH_GET_ORGANIZATIONS.setCacheKeyGeneratorCacheName(null);
	}

	/**
	 * Returns an ordered range of all the organizations associated with the user.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.UserModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param pk the primary key of the user
	 * @param start the lower bound of the range of users
	 * @param end the upper bound of the range of users (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of organizations associated with the user
	 * @throws SystemException if a system exception occurred
	 */
	public List<com.liferay.portal.model.Organization> getOrganizations(
		long pk, int start, int end, OrderByComparator orderByComparator)
		throws SystemException {
		boolean pagination = true;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderArgs = new Object[] { pk };
		}
		else {
			finderArgs = new Object[] { pk, start, end, orderByComparator };
		}

		List<com.liferay.portal.model.Organization> list = (List<com.liferay.portal.model.Organization>)FinderCacheUtil.getResult(FINDER_PATH_GET_ORGANIZATIONS,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				String sql = null;

				if (orderByComparator != null) {
					sql = _SQL_GETORGANIZATIONS.concat(ORDER_BY_CLAUSE)
											   .concat(orderByComparator.getOrderBy());
				}
				else {
					sql = _SQL_GETORGANIZATIONS;

					if (pagination) {
						sql = sql.concat(com.liferay.portal.model.impl.OrganizationModelImpl.ORDER_BY_SQL);
					}
				}

				SQLQuery q = session.createSQLQuery(sql);

				q.addEntity("Organization_",
					com.liferay.portal.model.impl.OrganizationImpl.class);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(pk);

				if (!pagination) {
					list = (List<com.liferay.portal.model.Organization>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = new UnmodifiableList<com.liferay.portal.model.Organization>(list);
				}
				else {
					list = (List<com.liferay.portal.model.Organization>)QueryUtil.list(q,
							getDialect(), start, end);
				}

				organizationPersistence.cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_GET_ORGANIZATIONS,
					finderArgs, list);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(FINDER_PATH_GET_ORGANIZATIONS,
					finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	public static final FinderPath FINDER_PATH_GET_ORGANIZATIONS_SIZE = new FinderPath(com.liferay.portal.model.impl.OrganizationModelImpl.ENTITY_CACHE_ENABLED,
			UserModelImpl.FINDER_CACHE_ENABLED_USERS_ORGS, Long.class,
			UserModelImpl.MAPPING_TABLE_USERS_ORGS_NAME,
			"getOrganizationsSize", new String[] { Long.class.getName() });

	static {
		FINDER_PATH_GET_ORGANIZATIONS_SIZE.setCacheKeyGeneratorCacheName(null);
	}

	/**
	 * Returns the number of organizations associated with the user.
	 *
	 * @param pk the primary key of the user
	 * @return the number of organizations associated with the user
	 * @throws SystemException if a system exception occurred
	 */
	public int getOrganizationsSize(long pk) throws SystemException {
		Object[] finderArgs = new Object[] { pk };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_GET_ORGANIZATIONS_SIZE,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				SQLQuery q = session.createSQLQuery(_SQL_GETORGANIZATIONSSIZE);

				q.addScalar(COUNT_COLUMN_NAME,
					com.liferay.portal.kernel.dao.orm.Type.LONG);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(pk);

				count = (Long)q.uniqueResult();

				FinderCacheUtil.putResult(FINDER_PATH_GET_ORGANIZATIONS_SIZE,
					finderArgs, count);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(FINDER_PATH_GET_ORGANIZATIONS_SIZE,
					finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	public static final FinderPath FINDER_PATH_CONTAINS_ORGANIZATION = new FinderPath(com.liferay.portal.model.impl.OrganizationModelImpl.ENTITY_CACHE_ENABLED,
			UserModelImpl.FINDER_CACHE_ENABLED_USERS_ORGS, Boolean.class,
			UserModelImpl.MAPPING_TABLE_USERS_ORGS_NAME,
			"containsOrganization",
			new String[] { Long.class.getName(), Long.class.getName() });

	/**
	 * Returns <code>true</code> if the organization is associated with the user.
	 *
	 * @param pk the primary key of the user
	 * @param organizationPK the primary key of the organization
	 * @return <code>true</code> if the organization is associated with the user; <code>false</code> otherwise
	 * @throws SystemException if a system exception occurred
	 */
	public boolean containsOrganization(long pk, long organizationPK)
		throws SystemException {
		Object[] finderArgs = new Object[] { pk, organizationPK };

		Boolean value = (Boolean)FinderCacheUtil.getResult(FINDER_PATH_CONTAINS_ORGANIZATION,
				finderArgs, this);

		if (value == null) {
			try {
				value = Boolean.valueOf(containsOrganization.contains(pk,
							organizationPK));

				FinderCacheUtil.putResult(FINDER_PATH_CONTAINS_ORGANIZATION,
					finderArgs, value);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(FINDER_PATH_CONTAINS_ORGANIZATION,
					finderArgs);

				throw processException(e);
			}
		}

		return value.booleanValue();
	}

	/**
	 * Returns <code>true</code> if the user has any organizations associated with it.
	 *
	 * @param pk the primary key of the user to check for associations with organizations
	 * @return <code>true</code> if the user has any organizations associated with it; <code>false</code> otherwise
	 * @throws SystemException if a system exception occurred
	 */
	public boolean containsOrganizations(long pk) throws SystemException {
		if (getOrganizationsSize(pk) > 0) {
			return true;
		}
		else {
			return false;
		}
	}

	/**
	 * Adds an association between the user and the organization. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the user
	 * @param organizationPK the primary key of the organization
	 * @throws SystemException if a system exception occurred
	 */
	public void addOrganization(long pk, long organizationPK)
		throws SystemException {
		try {
			addOrganization.add(pk, organizationPK);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(UserModelImpl.MAPPING_TABLE_USERS_ORGS_NAME);
		}
	}

	/**
	 * Adds an association between the user and the organization. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the user
	 * @param organization the organization
	 * @throws SystemException if a system exception occurred
	 */
	public void addOrganization(long pk,
		com.liferay.portal.model.Organization organization)
		throws SystemException {
		try {
			addOrganization.add(pk, organization.getPrimaryKey());
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(UserModelImpl.MAPPING_TABLE_USERS_ORGS_NAME);
		}
	}

	/**
	 * Adds an association between the user and the organizations. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the user
	 * @param organizationPKs the primary keys of the organizations
	 * @throws SystemException if a system exception occurred
	 */
	public void addOrganizations(long pk, long[] organizationPKs)
		throws SystemException {
		try {
			for (long organizationPK : organizationPKs) {
				addOrganization.add(pk, organizationPK);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(UserModelImpl.MAPPING_TABLE_USERS_ORGS_NAME);
		}
	}

	/**
	 * Adds an association between the user and the organizations. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the user
	 * @param organizations the organizations
	 * @throws SystemException if a system exception occurred
	 */
	public void addOrganizations(long pk,
		List<com.liferay.portal.model.Organization> organizations)
		throws SystemException {
		try {
			for (com.liferay.portal.model.Organization organization : organizations) {
				addOrganization.add(pk, organization.getPrimaryKey());
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(UserModelImpl.MAPPING_TABLE_USERS_ORGS_NAME);
		}
	}

	/**
	 * Clears all associations between the user and its organizations. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the user to clear the associated organizations from
	 * @throws SystemException if a system exception occurred
	 */
	public void clearOrganizations(long pk) throws SystemException {
		try {
			clearOrganizations.clear(pk);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(UserModelImpl.MAPPING_TABLE_USERS_ORGS_NAME);
		}
	}

	/**
	 * Removes the association between the user and the organization. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the user
	 * @param organizationPK the primary key of the organization
	 * @throws SystemException if a system exception occurred
	 */
	public void removeOrganization(long pk, long organizationPK)
		throws SystemException {
		try {
			removeOrganization.remove(pk, organizationPK);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(UserModelImpl.MAPPING_TABLE_USERS_ORGS_NAME);
		}
	}

	/**
	 * Removes the association between the user and the organization. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the user
	 * @param organization the organization
	 * @throws SystemException if a system exception occurred
	 */
	public void removeOrganization(long pk,
		com.liferay.portal.model.Organization organization)
		throws SystemException {
		try {
			removeOrganization.remove(pk, organization.getPrimaryKey());
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(UserModelImpl.MAPPING_TABLE_USERS_ORGS_NAME);
		}
	}

	/**
	 * Removes the association between the user and the organizations. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the user
	 * @param organizationPKs the primary keys of the organizations
	 * @throws SystemException if a system exception occurred
	 */
	public void removeOrganizations(long pk, long[] organizationPKs)
		throws SystemException {
		try {
			for (long organizationPK : organizationPKs) {
				removeOrganization.remove(pk, organizationPK);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(UserModelImpl.MAPPING_TABLE_USERS_ORGS_NAME);
		}
	}

	/**
	 * Removes the association between the user and the organizations. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the user
	 * @param organizations the organizations
	 * @throws SystemException if a system exception occurred
	 */
	public void removeOrganizations(long pk,
		List<com.liferay.portal.model.Organization> organizations)
		throws SystemException {
		try {
			for (com.liferay.portal.model.Organization organization : organizations) {
				removeOrganization.remove(pk, organization.getPrimaryKey());
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(UserModelImpl.MAPPING_TABLE_USERS_ORGS_NAME);
		}
	}

	/**
	 * Sets the organizations associated with the user, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the user
	 * @param organizationPKs the primary keys of the organizations to be associated with the user
	 * @throws SystemException if a system exception occurred
	 */
	public void setOrganizations(long pk, long[] organizationPKs)
		throws SystemException {
		try {
			Set<Long> organizationPKSet = SetUtil.fromArray(organizationPKs);

			List<com.liferay.portal.model.Organization> organizations = getOrganizations(pk);

			for (com.liferay.portal.model.Organization organization : organizations) {
				if (!organizationPKSet.remove(organization.getPrimaryKey())) {
					removeOrganization.remove(pk, organization.getPrimaryKey());
				}
			}

			for (Long organizationPK : organizationPKSet) {
				addOrganization.add(pk, organizationPK);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(UserModelImpl.MAPPING_TABLE_USERS_ORGS_NAME);
		}
	}

	/**
	 * Sets the organizations associated with the user, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the user
	 * @param organizations the organizations to be associated with the user
	 * @throws SystemException if a system exception occurred
	 */
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
			FinderCacheUtil.clearCache(UserModelImpl.MAPPING_TABLE_USERS_ORGS_NAME);
		}
	}

	/**
	 * Returns all the roles associated with the user.
	 *
	 * @param pk the primary key of the user
	 * @return the roles associated with the user
	 * @throws SystemException if a system exception occurred
	 */
	public List<com.liferay.portal.model.Role> getRoles(long pk)
		throws SystemException {
		return getRoles(pk, QueryUtil.ALL_POS, QueryUtil.ALL_POS);
	}

	/**
	 * Returns a range of all the roles associated with the user.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.UserModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param pk the primary key of the user
	 * @param start the lower bound of the range of users
	 * @param end the upper bound of the range of users (not inclusive)
	 * @return the range of roles associated with the user
	 * @throws SystemException if a system exception occurred
	 */
	public List<com.liferay.portal.model.Role> getRoles(long pk, int start,
		int end) throws SystemException {
		return getRoles(pk, start, end, null);
	}

	public static final FinderPath FINDER_PATH_GET_ROLES = new FinderPath(com.liferay.portal.model.impl.RoleModelImpl.ENTITY_CACHE_ENABLED,
			UserModelImpl.FINDER_CACHE_ENABLED_USERS_ROLES,
			com.liferay.portal.model.impl.RoleImpl.class,
			UserModelImpl.MAPPING_TABLE_USERS_ROLES_NAME, "getRoles",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

	static {
		FINDER_PATH_GET_ROLES.setCacheKeyGeneratorCacheName(null);
	}

	/**
	 * Returns an ordered range of all the roles associated with the user.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.UserModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param pk the primary key of the user
	 * @param start the lower bound of the range of users
	 * @param end the upper bound of the range of users (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of roles associated with the user
	 * @throws SystemException if a system exception occurred
	 */
	public List<com.liferay.portal.model.Role> getRoles(long pk, int start,
		int end, OrderByComparator orderByComparator) throws SystemException {
		boolean pagination = true;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderArgs = new Object[] { pk };
		}
		else {
			finderArgs = new Object[] { pk, start, end, orderByComparator };
		}

		List<com.liferay.portal.model.Role> list = (List<com.liferay.portal.model.Role>)FinderCacheUtil.getResult(FINDER_PATH_GET_ROLES,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				String sql = null;

				if (orderByComparator != null) {
					sql = _SQL_GETROLES.concat(ORDER_BY_CLAUSE)
									   .concat(orderByComparator.getOrderBy());
				}
				else {
					sql = _SQL_GETROLES;

					if (pagination) {
						sql = sql.concat(com.liferay.portal.model.impl.RoleModelImpl.ORDER_BY_SQL);
					}
				}

				SQLQuery q = session.createSQLQuery(sql);

				q.addEntity("Role_",
					com.liferay.portal.model.impl.RoleImpl.class);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(pk);

				if (!pagination) {
					list = (List<com.liferay.portal.model.Role>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = new UnmodifiableList<com.liferay.portal.model.Role>(list);
				}
				else {
					list = (List<com.liferay.portal.model.Role>)QueryUtil.list(q,
							getDialect(), start, end);
				}

				rolePersistence.cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_GET_ROLES, finderArgs,
					list);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(FINDER_PATH_GET_ROLES, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	public static final FinderPath FINDER_PATH_GET_ROLES_SIZE = new FinderPath(com.liferay.portal.model.impl.RoleModelImpl.ENTITY_CACHE_ENABLED,
			UserModelImpl.FINDER_CACHE_ENABLED_USERS_ROLES, Long.class,
			UserModelImpl.MAPPING_TABLE_USERS_ROLES_NAME, "getRolesSize",
			new String[] { Long.class.getName() });

	static {
		FINDER_PATH_GET_ROLES_SIZE.setCacheKeyGeneratorCacheName(null);
	}

	/**
	 * Returns the number of roles associated with the user.
	 *
	 * @param pk the primary key of the user
	 * @return the number of roles associated with the user
	 * @throws SystemException if a system exception occurred
	 */
	public int getRolesSize(long pk) throws SystemException {
		Object[] finderArgs = new Object[] { pk };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_GET_ROLES_SIZE,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				SQLQuery q = session.createSQLQuery(_SQL_GETROLESSIZE);

				q.addScalar(COUNT_COLUMN_NAME,
					com.liferay.portal.kernel.dao.orm.Type.LONG);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(pk);

				count = (Long)q.uniqueResult();

				FinderCacheUtil.putResult(FINDER_PATH_GET_ROLES_SIZE,
					finderArgs, count);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(FINDER_PATH_GET_ROLES_SIZE,
					finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	public static final FinderPath FINDER_PATH_CONTAINS_ROLE = new FinderPath(com.liferay.portal.model.impl.RoleModelImpl.ENTITY_CACHE_ENABLED,
			UserModelImpl.FINDER_CACHE_ENABLED_USERS_ROLES, Boolean.class,
			UserModelImpl.MAPPING_TABLE_USERS_ROLES_NAME, "containsRole",
			new String[] { Long.class.getName(), Long.class.getName() });

	/**
	 * Returns <code>true</code> if the role is associated with the user.
	 *
	 * @param pk the primary key of the user
	 * @param rolePK the primary key of the role
	 * @return <code>true</code> if the role is associated with the user; <code>false</code> otherwise
	 * @throws SystemException if a system exception occurred
	 */
	public boolean containsRole(long pk, long rolePK) throws SystemException {
		Object[] finderArgs = new Object[] { pk, rolePK };

		Boolean value = (Boolean)FinderCacheUtil.getResult(FINDER_PATH_CONTAINS_ROLE,
				finderArgs, this);

		if (value == null) {
			try {
				value = Boolean.valueOf(containsRole.contains(pk, rolePK));

				FinderCacheUtil.putResult(FINDER_PATH_CONTAINS_ROLE,
					finderArgs, value);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(FINDER_PATH_CONTAINS_ROLE,
					finderArgs);

				throw processException(e);
			}
		}

		return value.booleanValue();
	}

	/**
	 * Returns <code>true</code> if the user has any roles associated with it.
	 *
	 * @param pk the primary key of the user to check for associations with roles
	 * @return <code>true</code> if the user has any roles associated with it; <code>false</code> otherwise
	 * @throws SystemException if a system exception occurred
	 */
	public boolean containsRoles(long pk) throws SystemException {
		if (getRolesSize(pk) > 0) {
			return true;
		}
		else {
			return false;
		}
	}

	/**
	 * Adds an association between the user and the role. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the user
	 * @param rolePK the primary key of the role
	 * @throws SystemException if a system exception occurred
	 */
	public void addRole(long pk, long rolePK) throws SystemException {
		try {
			addRole.add(pk, rolePK);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(UserModelImpl.MAPPING_TABLE_USERS_ROLES_NAME);
		}
	}

	/**
	 * Adds an association between the user and the role. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the user
	 * @param role the role
	 * @throws SystemException if a system exception occurred
	 */
	public void addRole(long pk, com.liferay.portal.model.Role role)
		throws SystemException {
		try {
			addRole.add(pk, role.getPrimaryKey());
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(UserModelImpl.MAPPING_TABLE_USERS_ROLES_NAME);
		}
	}

	/**
	 * Adds an association between the user and the roles. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the user
	 * @param rolePKs the primary keys of the roles
	 * @throws SystemException if a system exception occurred
	 */
	public void addRoles(long pk, long[] rolePKs) throws SystemException {
		try {
			for (long rolePK : rolePKs) {
				addRole.add(pk, rolePK);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(UserModelImpl.MAPPING_TABLE_USERS_ROLES_NAME);
		}
	}

	/**
	 * Adds an association between the user and the roles. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the user
	 * @param roles the roles
	 * @throws SystemException if a system exception occurred
	 */
	public void addRoles(long pk, List<com.liferay.portal.model.Role> roles)
		throws SystemException {
		try {
			for (com.liferay.portal.model.Role role : roles) {
				addRole.add(pk, role.getPrimaryKey());
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(UserModelImpl.MAPPING_TABLE_USERS_ROLES_NAME);
		}
	}

	/**
	 * Clears all associations between the user and its roles. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the user to clear the associated roles from
	 * @throws SystemException if a system exception occurred
	 */
	public void clearRoles(long pk) throws SystemException {
		try {
			clearRoles.clear(pk);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(UserModelImpl.MAPPING_TABLE_USERS_ROLES_NAME);
		}
	}

	/**
	 * Removes the association between the user and the role. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the user
	 * @param rolePK the primary key of the role
	 * @throws SystemException if a system exception occurred
	 */
	public void removeRole(long pk, long rolePK) throws SystemException {
		try {
			removeRole.remove(pk, rolePK);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(UserModelImpl.MAPPING_TABLE_USERS_ROLES_NAME);
		}
	}

	/**
	 * Removes the association between the user and the role. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the user
	 * @param role the role
	 * @throws SystemException if a system exception occurred
	 */
	public void removeRole(long pk, com.liferay.portal.model.Role role)
		throws SystemException {
		try {
			removeRole.remove(pk, role.getPrimaryKey());
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(UserModelImpl.MAPPING_TABLE_USERS_ROLES_NAME);
		}
	}

	/**
	 * Removes the association between the user and the roles. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the user
	 * @param rolePKs the primary keys of the roles
	 * @throws SystemException if a system exception occurred
	 */
	public void removeRoles(long pk, long[] rolePKs) throws SystemException {
		try {
			for (long rolePK : rolePKs) {
				removeRole.remove(pk, rolePK);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(UserModelImpl.MAPPING_TABLE_USERS_ROLES_NAME);
		}
	}

	/**
	 * Removes the association between the user and the roles. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the user
	 * @param roles the roles
	 * @throws SystemException if a system exception occurred
	 */
	public void removeRoles(long pk, List<com.liferay.portal.model.Role> roles)
		throws SystemException {
		try {
			for (com.liferay.portal.model.Role role : roles) {
				removeRole.remove(pk, role.getPrimaryKey());
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(UserModelImpl.MAPPING_TABLE_USERS_ROLES_NAME);
		}
	}

	/**
	 * Sets the roles associated with the user, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the user
	 * @param rolePKs the primary keys of the roles to be associated with the user
	 * @throws SystemException if a system exception occurred
	 */
	public void setRoles(long pk, long[] rolePKs) throws SystemException {
		try {
			Set<Long> rolePKSet = SetUtil.fromArray(rolePKs);

			List<com.liferay.portal.model.Role> roles = getRoles(pk);

			for (com.liferay.portal.model.Role role : roles) {
				if (!rolePKSet.remove(role.getPrimaryKey())) {
					removeRole.remove(pk, role.getPrimaryKey());
				}
			}

			for (Long rolePK : rolePKSet) {
				addRole.add(pk, rolePK);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(UserModelImpl.MAPPING_TABLE_USERS_ROLES_NAME);
		}
	}

	/**
	 * Sets the roles associated with the user, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the user
	 * @param roles the roles to be associated with the user
	 * @throws SystemException if a system exception occurred
	 */
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
			FinderCacheUtil.clearCache(UserModelImpl.MAPPING_TABLE_USERS_ROLES_NAME);
		}
	}

	/**
	 * Returns all the teams associated with the user.
	 *
	 * @param pk the primary key of the user
	 * @return the teams associated with the user
	 * @throws SystemException if a system exception occurred
	 */
	public List<com.liferay.portal.model.Team> getTeams(long pk)
		throws SystemException {
		return getTeams(pk, QueryUtil.ALL_POS, QueryUtil.ALL_POS);
	}

	/**
	 * Returns a range of all the teams associated with the user.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.UserModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param pk the primary key of the user
	 * @param start the lower bound of the range of users
	 * @param end the upper bound of the range of users (not inclusive)
	 * @return the range of teams associated with the user
	 * @throws SystemException if a system exception occurred
	 */
	public List<com.liferay.portal.model.Team> getTeams(long pk, int start,
		int end) throws SystemException {
		return getTeams(pk, start, end, null);
	}

	public static final FinderPath FINDER_PATH_GET_TEAMS = new FinderPath(com.liferay.portal.model.impl.TeamModelImpl.ENTITY_CACHE_ENABLED,
			UserModelImpl.FINDER_CACHE_ENABLED_USERS_TEAMS,
			com.liferay.portal.model.impl.TeamImpl.class,
			UserModelImpl.MAPPING_TABLE_USERS_TEAMS_NAME, "getTeams",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

	static {
		FINDER_PATH_GET_TEAMS.setCacheKeyGeneratorCacheName(null);
	}

	/**
	 * Returns an ordered range of all the teams associated with the user.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.UserModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param pk the primary key of the user
	 * @param start the lower bound of the range of users
	 * @param end the upper bound of the range of users (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of teams associated with the user
	 * @throws SystemException if a system exception occurred
	 */
	public List<com.liferay.portal.model.Team> getTeams(long pk, int start,
		int end, OrderByComparator orderByComparator) throws SystemException {
		boolean pagination = true;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderArgs = new Object[] { pk };
		}
		else {
			finderArgs = new Object[] { pk, start, end, orderByComparator };
		}

		List<com.liferay.portal.model.Team> list = (List<com.liferay.portal.model.Team>)FinderCacheUtil.getResult(FINDER_PATH_GET_TEAMS,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				String sql = null;

				if (orderByComparator != null) {
					sql = _SQL_GETTEAMS.concat(ORDER_BY_CLAUSE)
									   .concat(orderByComparator.getOrderBy());
				}
				else {
					sql = _SQL_GETTEAMS;

					if (pagination) {
						sql = sql.concat(com.liferay.portal.model.impl.TeamModelImpl.ORDER_BY_SQL);
					}
				}

				SQLQuery q = session.createSQLQuery(sql);

				q.addEntity("Team", com.liferay.portal.model.impl.TeamImpl.class);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(pk);

				if (!pagination) {
					list = (List<com.liferay.portal.model.Team>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = new UnmodifiableList<com.liferay.portal.model.Team>(list);
				}
				else {
					list = (List<com.liferay.portal.model.Team>)QueryUtil.list(q,
							getDialect(), start, end);
				}

				teamPersistence.cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_GET_TEAMS, finderArgs,
					list);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(FINDER_PATH_GET_TEAMS, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	public static final FinderPath FINDER_PATH_GET_TEAMS_SIZE = new FinderPath(com.liferay.portal.model.impl.TeamModelImpl.ENTITY_CACHE_ENABLED,
			UserModelImpl.FINDER_CACHE_ENABLED_USERS_TEAMS, Long.class,
			UserModelImpl.MAPPING_TABLE_USERS_TEAMS_NAME, "getTeamsSize",
			new String[] { Long.class.getName() });

	static {
		FINDER_PATH_GET_TEAMS_SIZE.setCacheKeyGeneratorCacheName(null);
	}

	/**
	 * Returns the number of teams associated with the user.
	 *
	 * @param pk the primary key of the user
	 * @return the number of teams associated with the user
	 * @throws SystemException if a system exception occurred
	 */
	public int getTeamsSize(long pk) throws SystemException {
		Object[] finderArgs = new Object[] { pk };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_GET_TEAMS_SIZE,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				SQLQuery q = session.createSQLQuery(_SQL_GETTEAMSSIZE);

				q.addScalar(COUNT_COLUMN_NAME,
					com.liferay.portal.kernel.dao.orm.Type.LONG);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(pk);

				count = (Long)q.uniqueResult();

				FinderCacheUtil.putResult(FINDER_PATH_GET_TEAMS_SIZE,
					finderArgs, count);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(FINDER_PATH_GET_TEAMS_SIZE,
					finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	public static final FinderPath FINDER_PATH_CONTAINS_TEAM = new FinderPath(com.liferay.portal.model.impl.TeamModelImpl.ENTITY_CACHE_ENABLED,
			UserModelImpl.FINDER_CACHE_ENABLED_USERS_TEAMS, Boolean.class,
			UserModelImpl.MAPPING_TABLE_USERS_TEAMS_NAME, "containsTeam",
			new String[] { Long.class.getName(), Long.class.getName() });

	/**
	 * Returns <code>true</code> if the team is associated with the user.
	 *
	 * @param pk the primary key of the user
	 * @param teamPK the primary key of the team
	 * @return <code>true</code> if the team is associated with the user; <code>false</code> otherwise
	 * @throws SystemException if a system exception occurred
	 */
	public boolean containsTeam(long pk, long teamPK) throws SystemException {
		Object[] finderArgs = new Object[] { pk, teamPK };

		Boolean value = (Boolean)FinderCacheUtil.getResult(FINDER_PATH_CONTAINS_TEAM,
				finderArgs, this);

		if (value == null) {
			try {
				value = Boolean.valueOf(containsTeam.contains(pk, teamPK));

				FinderCacheUtil.putResult(FINDER_PATH_CONTAINS_TEAM,
					finderArgs, value);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(FINDER_PATH_CONTAINS_TEAM,
					finderArgs);

				throw processException(e);
			}
		}

		return value.booleanValue();
	}

	/**
	 * Returns <code>true</code> if the user has any teams associated with it.
	 *
	 * @param pk the primary key of the user to check for associations with teams
	 * @return <code>true</code> if the user has any teams associated with it; <code>false</code> otherwise
	 * @throws SystemException if a system exception occurred
	 */
	public boolean containsTeams(long pk) throws SystemException {
		if (getTeamsSize(pk) > 0) {
			return true;
		}
		else {
			return false;
		}
	}

	/**
	 * Adds an association between the user and the team. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the user
	 * @param teamPK the primary key of the team
	 * @throws SystemException if a system exception occurred
	 */
	public void addTeam(long pk, long teamPK) throws SystemException {
		try {
			addTeam.add(pk, teamPK);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(UserModelImpl.MAPPING_TABLE_USERS_TEAMS_NAME);
		}
	}

	/**
	 * Adds an association between the user and the team. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the user
	 * @param team the team
	 * @throws SystemException if a system exception occurred
	 */
	public void addTeam(long pk, com.liferay.portal.model.Team team)
		throws SystemException {
		try {
			addTeam.add(pk, team.getPrimaryKey());
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(UserModelImpl.MAPPING_TABLE_USERS_TEAMS_NAME);
		}
	}

	/**
	 * Adds an association between the user and the teams. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the user
	 * @param teamPKs the primary keys of the teams
	 * @throws SystemException if a system exception occurred
	 */
	public void addTeams(long pk, long[] teamPKs) throws SystemException {
		try {
			for (long teamPK : teamPKs) {
				addTeam.add(pk, teamPK);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(UserModelImpl.MAPPING_TABLE_USERS_TEAMS_NAME);
		}
	}

	/**
	 * Adds an association between the user and the teams. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the user
	 * @param teams the teams
	 * @throws SystemException if a system exception occurred
	 */
	public void addTeams(long pk, List<com.liferay.portal.model.Team> teams)
		throws SystemException {
		try {
			for (com.liferay.portal.model.Team team : teams) {
				addTeam.add(pk, team.getPrimaryKey());
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(UserModelImpl.MAPPING_TABLE_USERS_TEAMS_NAME);
		}
	}

	/**
	 * Clears all associations between the user and its teams. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the user to clear the associated teams from
	 * @throws SystemException if a system exception occurred
	 */
	public void clearTeams(long pk) throws SystemException {
		try {
			clearTeams.clear(pk);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(UserModelImpl.MAPPING_TABLE_USERS_TEAMS_NAME);
		}
	}

	/**
	 * Removes the association between the user and the team. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the user
	 * @param teamPK the primary key of the team
	 * @throws SystemException if a system exception occurred
	 */
	public void removeTeam(long pk, long teamPK) throws SystemException {
		try {
			removeTeam.remove(pk, teamPK);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(UserModelImpl.MAPPING_TABLE_USERS_TEAMS_NAME);
		}
	}

	/**
	 * Removes the association between the user and the team. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the user
	 * @param team the team
	 * @throws SystemException if a system exception occurred
	 */
	public void removeTeam(long pk, com.liferay.portal.model.Team team)
		throws SystemException {
		try {
			removeTeam.remove(pk, team.getPrimaryKey());
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(UserModelImpl.MAPPING_TABLE_USERS_TEAMS_NAME);
		}
	}

	/**
	 * Removes the association between the user and the teams. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the user
	 * @param teamPKs the primary keys of the teams
	 * @throws SystemException if a system exception occurred
	 */
	public void removeTeams(long pk, long[] teamPKs) throws SystemException {
		try {
			for (long teamPK : teamPKs) {
				removeTeam.remove(pk, teamPK);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(UserModelImpl.MAPPING_TABLE_USERS_TEAMS_NAME);
		}
	}

	/**
	 * Removes the association between the user and the teams. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the user
	 * @param teams the teams
	 * @throws SystemException if a system exception occurred
	 */
	public void removeTeams(long pk, List<com.liferay.portal.model.Team> teams)
		throws SystemException {
		try {
			for (com.liferay.portal.model.Team team : teams) {
				removeTeam.remove(pk, team.getPrimaryKey());
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(UserModelImpl.MAPPING_TABLE_USERS_TEAMS_NAME);
		}
	}

	/**
	 * Sets the teams associated with the user, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the user
	 * @param teamPKs the primary keys of the teams to be associated with the user
	 * @throws SystemException if a system exception occurred
	 */
	public void setTeams(long pk, long[] teamPKs) throws SystemException {
		try {
			Set<Long> teamPKSet = SetUtil.fromArray(teamPKs);

			List<com.liferay.portal.model.Team> teams = getTeams(pk);

			for (com.liferay.portal.model.Team team : teams) {
				if (!teamPKSet.remove(team.getPrimaryKey())) {
					removeTeam.remove(pk, team.getPrimaryKey());
				}
			}

			for (Long teamPK : teamPKSet) {
				addTeam.add(pk, teamPK);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(UserModelImpl.MAPPING_TABLE_USERS_TEAMS_NAME);
		}
	}

	/**
	 * Sets the teams associated with the user, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the user
	 * @param teams the teams to be associated with the user
	 * @throws SystemException if a system exception occurred
	 */
	public void setTeams(long pk, List<com.liferay.portal.model.Team> teams)
		throws SystemException {
		try {
			long[] teamPKs = new long[teams.size()];

			for (int i = 0; i < teams.size(); i++) {
				com.liferay.portal.model.Team team = teams.get(i);

				teamPKs[i] = team.getPrimaryKey();
			}

			setTeams(pk, teamPKs);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(UserModelImpl.MAPPING_TABLE_USERS_TEAMS_NAME);
		}
	}

	/**
	 * Returns all the user groups associated with the user.
	 *
	 * @param pk the primary key of the user
	 * @return the user groups associated with the user
	 * @throws SystemException if a system exception occurred
	 */
	public List<com.liferay.portal.model.UserGroup> getUserGroups(long pk)
		throws SystemException {
		return getUserGroups(pk, QueryUtil.ALL_POS, QueryUtil.ALL_POS);
	}

	/**
	 * Returns a range of all the user groups associated with the user.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.UserModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param pk the primary key of the user
	 * @param start the lower bound of the range of users
	 * @param end the upper bound of the range of users (not inclusive)
	 * @return the range of user groups associated with the user
	 * @throws SystemException if a system exception occurred
	 */
	public List<com.liferay.portal.model.UserGroup> getUserGroups(long pk,
		int start, int end) throws SystemException {
		return getUserGroups(pk, start, end, null);
	}

	public static final FinderPath FINDER_PATH_GET_USERGROUPS = new FinderPath(com.liferay.portal.model.impl.UserGroupModelImpl.ENTITY_CACHE_ENABLED,
			UserModelImpl.FINDER_CACHE_ENABLED_USERS_USERGROUPS,
			com.liferay.portal.model.impl.UserGroupImpl.class,
			UserModelImpl.MAPPING_TABLE_USERS_USERGROUPS_NAME, "getUserGroups",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

	static {
		FINDER_PATH_GET_USERGROUPS.setCacheKeyGeneratorCacheName(null);
	}

	/**
	 * Returns an ordered range of all the user groups associated with the user.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.UserModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param pk the primary key of the user
	 * @param start the lower bound of the range of users
	 * @param end the upper bound of the range of users (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of user groups associated with the user
	 * @throws SystemException if a system exception occurred
	 */
	public List<com.liferay.portal.model.UserGroup> getUserGroups(long pk,
		int start, int end, OrderByComparator orderByComparator)
		throws SystemException {
		boolean pagination = true;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderArgs = new Object[] { pk };
		}
		else {
			finderArgs = new Object[] { pk, start, end, orderByComparator };
		}

		List<com.liferay.portal.model.UserGroup> list = (List<com.liferay.portal.model.UserGroup>)FinderCacheUtil.getResult(FINDER_PATH_GET_USERGROUPS,
				finderArgs, this);

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				String sql = null;

				if (orderByComparator != null) {
					sql = _SQL_GETUSERGROUPS.concat(ORDER_BY_CLAUSE)
											.concat(orderByComparator.getOrderBy());
				}
				else {
					sql = _SQL_GETUSERGROUPS;

					if (pagination) {
						sql = sql.concat(com.liferay.portal.model.impl.UserGroupModelImpl.ORDER_BY_SQL);
					}
				}

				SQLQuery q = session.createSQLQuery(sql);

				q.addEntity("UserGroup",
					com.liferay.portal.model.impl.UserGroupImpl.class);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(pk);

				if (!pagination) {
					list = (List<com.liferay.portal.model.UserGroup>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = new UnmodifiableList<com.liferay.portal.model.UserGroup>(list);
				}
				else {
					list = (List<com.liferay.portal.model.UserGroup>)QueryUtil.list(q,
							getDialect(), start, end);
				}

				userGroupPersistence.cacheResult(list);

				FinderCacheUtil.putResult(FINDER_PATH_GET_USERGROUPS,
					finderArgs, list);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(FINDER_PATH_GET_USERGROUPS,
					finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	public static final FinderPath FINDER_PATH_GET_USERGROUPS_SIZE = new FinderPath(com.liferay.portal.model.impl.UserGroupModelImpl.ENTITY_CACHE_ENABLED,
			UserModelImpl.FINDER_CACHE_ENABLED_USERS_USERGROUPS, Long.class,
			UserModelImpl.MAPPING_TABLE_USERS_USERGROUPS_NAME,
			"getUserGroupsSize", new String[] { Long.class.getName() });

	static {
		FINDER_PATH_GET_USERGROUPS_SIZE.setCacheKeyGeneratorCacheName(null);
	}

	/**
	 * Returns the number of user groups associated with the user.
	 *
	 * @param pk the primary key of the user
	 * @return the number of user groups associated with the user
	 * @throws SystemException if a system exception occurred
	 */
	public int getUserGroupsSize(long pk) throws SystemException {
		Object[] finderArgs = new Object[] { pk };

		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_GET_USERGROUPS_SIZE,
				finderArgs, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				SQLQuery q = session.createSQLQuery(_SQL_GETUSERGROUPSSIZE);

				q.addScalar(COUNT_COLUMN_NAME,
					com.liferay.portal.kernel.dao.orm.Type.LONG);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(pk);

				count = (Long)q.uniqueResult();

				FinderCacheUtil.putResult(FINDER_PATH_GET_USERGROUPS_SIZE,
					finderArgs, count);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(FINDER_PATH_GET_USERGROUPS_SIZE,
					finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	public static final FinderPath FINDER_PATH_CONTAINS_USERGROUP = new FinderPath(com.liferay.portal.model.impl.UserGroupModelImpl.ENTITY_CACHE_ENABLED,
			UserModelImpl.FINDER_CACHE_ENABLED_USERS_USERGROUPS, Boolean.class,
			UserModelImpl.MAPPING_TABLE_USERS_USERGROUPS_NAME,
			"containsUserGroup",
			new String[] { Long.class.getName(), Long.class.getName() });

	/**
	 * Returns <code>true</code> if the user group is associated with the user.
	 *
	 * @param pk the primary key of the user
	 * @param userGroupPK the primary key of the user group
	 * @return <code>true</code> if the user group is associated with the user; <code>false</code> otherwise
	 * @throws SystemException if a system exception occurred
	 */
	public boolean containsUserGroup(long pk, long userGroupPK)
		throws SystemException {
		Object[] finderArgs = new Object[] { pk, userGroupPK };

		Boolean value = (Boolean)FinderCacheUtil.getResult(FINDER_PATH_CONTAINS_USERGROUP,
				finderArgs, this);

		if (value == null) {
			try {
				value = Boolean.valueOf(containsUserGroup.contains(pk,
							userGroupPK));

				FinderCacheUtil.putResult(FINDER_PATH_CONTAINS_USERGROUP,
					finderArgs, value);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(FINDER_PATH_CONTAINS_USERGROUP,
					finderArgs);

				throw processException(e);
			}
		}

		return value.booleanValue();
	}

	/**
	 * Returns <code>true</code> if the user has any user groups associated with it.
	 *
	 * @param pk the primary key of the user to check for associations with user groups
	 * @return <code>true</code> if the user has any user groups associated with it; <code>false</code> otherwise
	 * @throws SystemException if a system exception occurred
	 */
	public boolean containsUserGroups(long pk) throws SystemException {
		if (getUserGroupsSize(pk) > 0) {
			return true;
		}
		else {
			return false;
		}
	}

	/**
	 * Adds an association between the user and the user group. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the user
	 * @param userGroupPK the primary key of the user group
	 * @throws SystemException if a system exception occurred
	 */
	public void addUserGroup(long pk, long userGroupPK)
		throws SystemException {
		try {
			addUserGroup.add(pk, userGroupPK);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(UserModelImpl.MAPPING_TABLE_USERS_USERGROUPS_NAME);
		}
	}

	/**
	 * Adds an association between the user and the user group. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the user
	 * @param userGroup the user group
	 * @throws SystemException if a system exception occurred
	 */
	public void addUserGroup(long pk,
		com.liferay.portal.model.UserGroup userGroup) throws SystemException {
		try {
			addUserGroup.add(pk, userGroup.getPrimaryKey());
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(UserModelImpl.MAPPING_TABLE_USERS_USERGROUPS_NAME);
		}
	}

	/**
	 * Adds an association between the user and the user groups. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the user
	 * @param userGroupPKs the primary keys of the user groups
	 * @throws SystemException if a system exception occurred
	 */
	public void addUserGroups(long pk, long[] userGroupPKs)
		throws SystemException {
		try {
			for (long userGroupPK : userGroupPKs) {
				addUserGroup.add(pk, userGroupPK);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(UserModelImpl.MAPPING_TABLE_USERS_USERGROUPS_NAME);
		}
	}

	/**
	 * Adds an association between the user and the user groups. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the user
	 * @param userGroups the user groups
	 * @throws SystemException if a system exception occurred
	 */
	public void addUserGroups(long pk,
		List<com.liferay.portal.model.UserGroup> userGroups)
		throws SystemException {
		try {
			for (com.liferay.portal.model.UserGroup userGroup : userGroups) {
				addUserGroup.add(pk, userGroup.getPrimaryKey());
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(UserModelImpl.MAPPING_TABLE_USERS_USERGROUPS_NAME);
		}
	}

	/**
	 * Clears all associations between the user and its user groups. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the user to clear the associated user groups from
	 * @throws SystemException if a system exception occurred
	 */
	public void clearUserGroups(long pk) throws SystemException {
		try {
			clearUserGroups.clear(pk);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(UserModelImpl.MAPPING_TABLE_USERS_USERGROUPS_NAME);
		}
	}

	/**
	 * Removes the association between the user and the user group. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the user
	 * @param userGroupPK the primary key of the user group
	 * @throws SystemException if a system exception occurred
	 */
	public void removeUserGroup(long pk, long userGroupPK)
		throws SystemException {
		try {
			removeUserGroup.remove(pk, userGroupPK);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(UserModelImpl.MAPPING_TABLE_USERS_USERGROUPS_NAME);
		}
	}

	/**
	 * Removes the association between the user and the user group. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the user
	 * @param userGroup the user group
	 * @throws SystemException if a system exception occurred
	 */
	public void removeUserGroup(long pk,
		com.liferay.portal.model.UserGroup userGroup) throws SystemException {
		try {
			removeUserGroup.remove(pk, userGroup.getPrimaryKey());
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(UserModelImpl.MAPPING_TABLE_USERS_USERGROUPS_NAME);
		}
	}

	/**
	 * Removes the association between the user and the user groups. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the user
	 * @param userGroupPKs the primary keys of the user groups
	 * @throws SystemException if a system exception occurred
	 */
	public void removeUserGroups(long pk, long[] userGroupPKs)
		throws SystemException {
		try {
			for (long userGroupPK : userGroupPKs) {
				removeUserGroup.remove(pk, userGroupPK);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(UserModelImpl.MAPPING_TABLE_USERS_USERGROUPS_NAME);
		}
	}

	/**
	 * Removes the association between the user and the user groups. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the user
	 * @param userGroups the user groups
	 * @throws SystemException if a system exception occurred
	 */
	public void removeUserGroups(long pk,
		List<com.liferay.portal.model.UserGroup> userGroups)
		throws SystemException {
		try {
			for (com.liferay.portal.model.UserGroup userGroup : userGroups) {
				removeUserGroup.remove(pk, userGroup.getPrimaryKey());
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(UserModelImpl.MAPPING_TABLE_USERS_USERGROUPS_NAME);
		}
	}

	/**
	 * Sets the user groups associated with the user, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the user
	 * @param userGroupPKs the primary keys of the user groups to be associated with the user
	 * @throws SystemException if a system exception occurred
	 */
	public void setUserGroups(long pk, long[] userGroupPKs)
		throws SystemException {
		try {
			Set<Long> userGroupPKSet = SetUtil.fromArray(userGroupPKs);

			List<com.liferay.portal.model.UserGroup> userGroups = getUserGroups(pk);

			for (com.liferay.portal.model.UserGroup userGroup : userGroups) {
				if (!userGroupPKSet.remove(userGroup.getPrimaryKey())) {
					removeUserGroup.remove(pk, userGroup.getPrimaryKey());
				}
			}

			for (Long userGroupPK : userGroupPKSet) {
				addUserGroup.add(pk, userGroupPK);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			FinderCacheUtil.clearCache(UserModelImpl.MAPPING_TABLE_USERS_USERGROUPS_NAME);
		}
	}

	/**
	 * Sets the user groups associated with the user, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the user
	 * @param userGroups the user groups to be associated with the user
	 * @throws SystemException if a system exception occurred
	 */
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
			FinderCacheUtil.clearCache(UserModelImpl.MAPPING_TABLE_USERS_USERGROUPS_NAME);
		}
	}

	/**
	 * Initializes the user persistence.
	 */
	public void afterPropertiesSet() {
		String[] listenerClassNames = StringUtil.split(GetterUtil.getString(
					com.liferay.portal.util.PropsUtil.get(
						"value.object.listener.com.liferay.portal.model.User")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<User>> listenersList = new ArrayList<ModelListener<User>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<User>)InstanceFactory.newInstance(
							listenerClassName));
				}

				listeners = listenersList.toArray(new ModelListener[listenersList.size()]);
			}
			catch (Exception e) {
				_log.error(e);
			}
		}

		containsGroup = new ContainsGroup();

		addGroup = new AddGroup();
		clearGroups = new ClearGroups();
		removeGroup = new RemoveGroup();

		containsOrganization = new ContainsOrganization();

		addOrganization = new AddOrganization();
		clearOrganizations = new ClearOrganizations();
		removeOrganization = new RemoveOrganization();

		containsRole = new ContainsRole();

		addRole = new AddRole();
		clearRoles = new ClearRoles();
		removeRole = new RemoveRole();

		containsTeam = new ContainsTeam();

		addTeam = new AddTeam();
		clearTeams = new ClearTeams();
		removeTeam = new RemoveTeam();

		containsUserGroup = new ContainsUserGroup();

		addUserGroup = new AddUserGroup();
		clearUserGroups = new ClearUserGroups();
		removeUserGroup = new RemoveUserGroup();
	}

	public void destroy() {
		EntityCacheUtil.removeCache(UserImpl.class.getName());
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@BeanReference(type = GroupPersistence.class)
	protected GroupPersistence groupPersistence;
	protected ContainsGroup containsGroup;
	protected AddGroup addGroup;
	protected ClearGroups clearGroups;
	protected RemoveGroup removeGroup;
	@BeanReference(type = OrganizationPersistence.class)
	protected OrganizationPersistence organizationPersistence;
	protected ContainsOrganization containsOrganization;
	protected AddOrganization addOrganization;
	protected ClearOrganizations clearOrganizations;
	protected RemoveOrganization removeOrganization;
	@BeanReference(type = RolePersistence.class)
	protected RolePersistence rolePersistence;
	protected ContainsRole containsRole;
	protected AddRole addRole;
	protected ClearRoles clearRoles;
	protected RemoveRole removeRole;
	@BeanReference(type = TeamPersistence.class)
	protected TeamPersistence teamPersistence;
	protected ContainsTeam containsTeam;
	protected AddTeam addTeam;
	protected ClearTeams clearTeams;
	protected RemoveTeam removeTeam;
	@BeanReference(type = UserGroupPersistence.class)
	protected UserGroupPersistence userGroupPersistence;
	protected ContainsUserGroup containsUserGroup;
	protected AddUserGroup addUserGroup;
	protected ClearUserGroups clearUserGroups;
	protected RemoveUserGroup removeUserGroup;

	protected class ContainsGroup {
		protected ContainsGroup() {
			_mappingSqlQuery = MappingSqlQueryFactoryUtil.getMappingSqlQuery(getDataSource(),
					_SQL_CONTAINSGROUP,
					new int[] { java.sql.Types.BIGINT, java.sql.Types.BIGINT },
					RowMapper.COUNT);
		}

		protected boolean contains(long userId, long groupId) {
			List<Integer> results = _mappingSqlQuery.execute(new Object[] {
						new Long(userId), new Long(groupId)
					});

			if (results.size() > 0) {
				Integer count = results.get(0);

				if (count.intValue() > 0) {
					return true;
				}
			}

			return false;
		}

		private MappingSqlQuery<Integer> _mappingSqlQuery;
	}

	protected class AddGroup {
		protected AddGroup() {
			_sqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(getDataSource(),
					"INSERT INTO Users_Groups (userId, groupId) VALUES (?, ?)",
					new int[] { java.sql.Types.BIGINT, java.sql.Types.BIGINT });
		}

		protected void add(long userId, long groupId) throws SystemException {
			if (!containsGroup.contains(userId, groupId)) {
				ModelListener<com.liferay.portal.model.Group>[] groupListeners = groupPersistence.getListeners();

				for (ModelListener<User> listener : listeners) {
					listener.onBeforeAddAssociation(userId,
						com.liferay.portal.model.Group.class.getName(), groupId);
				}

				for (ModelListener<com.liferay.portal.model.Group> listener : groupListeners) {
					listener.onBeforeAddAssociation(groupId,
						User.class.getName(), userId);
				}

				_sqlUpdate.update(new Object[] {
						new Long(userId), new Long(groupId)
					});

				for (ModelListener<User> listener : listeners) {
					listener.onAfterAddAssociation(userId,
						com.liferay.portal.model.Group.class.getName(), groupId);
				}

				for (ModelListener<com.liferay.portal.model.Group> listener : groupListeners) {
					listener.onAfterAddAssociation(groupId,
						User.class.getName(), userId);
				}
			}
		}

		private SqlUpdate _sqlUpdate;
	}

	protected class ClearGroups {
		protected ClearGroups() {
			_sqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(getDataSource(),
					"DELETE FROM Users_Groups WHERE userId = ?",
					new int[] { java.sql.Types.BIGINT });
		}

		protected void clear(long userId) throws SystemException {
			ModelListener<com.liferay.portal.model.Group>[] groupListeners = groupPersistence.getListeners();

			List<com.liferay.portal.model.Group> groups = null;

			if ((listeners.length > 0) || (groupListeners.length > 0)) {
				groups = getGroups(userId);

				for (com.liferay.portal.model.Group group : groups) {
					for (ModelListener<User> listener : listeners) {
						listener.onBeforeRemoveAssociation(userId,
							com.liferay.portal.model.Group.class.getName(),
							group.getPrimaryKey());
					}

					for (ModelListener<com.liferay.portal.model.Group> listener : groupListeners) {
						listener.onBeforeRemoveAssociation(group.getPrimaryKey(),
							User.class.getName(), userId);
					}
				}
			}

			_sqlUpdate.update(new Object[] { new Long(userId) });

			if ((listeners.length > 0) || (groupListeners.length > 0)) {
				for (com.liferay.portal.model.Group group : groups) {
					for (ModelListener<User> listener : listeners) {
						listener.onAfterRemoveAssociation(userId,
							com.liferay.portal.model.Group.class.getName(),
							group.getPrimaryKey());
					}

					for (ModelListener<com.liferay.portal.model.Group> listener : groupListeners) {
						listener.onAfterRemoveAssociation(group.getPrimaryKey(),
							User.class.getName(), userId);
					}
				}
			}
		}

		private SqlUpdate _sqlUpdate;
	}

	protected class RemoveGroup {
		protected RemoveGroup() {
			_sqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(getDataSource(),
					"DELETE FROM Users_Groups WHERE userId = ? AND groupId = ?",
					new int[] { java.sql.Types.BIGINT, java.sql.Types.BIGINT });
		}

		protected void remove(long userId, long groupId)
			throws SystemException {
			if (containsGroup.contains(userId, groupId)) {
				ModelListener<com.liferay.portal.model.Group>[] groupListeners = groupPersistence.getListeners();

				for (ModelListener<User> listener : listeners) {
					listener.onBeforeRemoveAssociation(userId,
						com.liferay.portal.model.Group.class.getName(), groupId);
				}

				for (ModelListener<com.liferay.portal.model.Group> listener : groupListeners) {
					listener.onBeforeRemoveAssociation(groupId,
						User.class.getName(), userId);
				}

				_sqlUpdate.update(new Object[] {
						new Long(userId), new Long(groupId)
					});

				for (ModelListener<User> listener : listeners) {
					listener.onAfterRemoveAssociation(userId,
						com.liferay.portal.model.Group.class.getName(), groupId);
				}

				for (ModelListener<com.liferay.portal.model.Group> listener : groupListeners) {
					listener.onAfterRemoveAssociation(groupId,
						User.class.getName(), userId);
				}
			}
		}

		private SqlUpdate _sqlUpdate;
	}

	protected class ContainsOrganization {
		protected ContainsOrganization() {
			_mappingSqlQuery = MappingSqlQueryFactoryUtil.getMappingSqlQuery(getDataSource(),
					_SQL_CONTAINSORGANIZATION,
					new int[] { java.sql.Types.BIGINT, java.sql.Types.BIGINT },
					RowMapper.COUNT);
		}

		protected boolean contains(long userId, long organizationId) {
			List<Integer> results = _mappingSqlQuery.execute(new Object[] {
						new Long(userId), new Long(organizationId)
					});

			if (results.size() > 0) {
				Integer count = results.get(0);

				if (count.intValue() > 0) {
					return true;
				}
			}

			return false;
		}

		private MappingSqlQuery<Integer> _mappingSqlQuery;
	}

	protected class AddOrganization {
		protected AddOrganization() {
			_sqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(getDataSource(),
					"INSERT INTO Users_Orgs (userId, organizationId) VALUES (?, ?)",
					new int[] { java.sql.Types.BIGINT, java.sql.Types.BIGINT });
		}

		protected void add(long userId, long organizationId)
			throws SystemException {
			if (!containsOrganization.contains(userId, organizationId)) {
				ModelListener<com.liferay.portal.model.Organization>[] organizationListeners =
					organizationPersistence.getListeners();

				for (ModelListener<User> listener : listeners) {
					listener.onBeforeAddAssociation(userId,
						com.liferay.portal.model.Organization.class.getName(),
						organizationId);
				}

				for (ModelListener<com.liferay.portal.model.Organization> listener : organizationListeners) {
					listener.onBeforeAddAssociation(organizationId,
						User.class.getName(), userId);
				}

				_sqlUpdate.update(new Object[] {
						new Long(userId), new Long(organizationId)
					});

				for (ModelListener<User> listener : listeners) {
					listener.onAfterAddAssociation(userId,
						com.liferay.portal.model.Organization.class.getName(),
						organizationId);
				}

				for (ModelListener<com.liferay.portal.model.Organization> listener : organizationListeners) {
					listener.onAfterAddAssociation(organizationId,
						User.class.getName(), userId);
				}
			}
		}

		private SqlUpdate _sqlUpdate;
	}

	protected class ClearOrganizations {
		protected ClearOrganizations() {
			_sqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(getDataSource(),
					"DELETE FROM Users_Orgs WHERE userId = ?",
					new int[] { java.sql.Types.BIGINT });
		}

		protected void clear(long userId) throws SystemException {
			ModelListener<com.liferay.portal.model.Organization>[] organizationListeners =
				organizationPersistence.getListeners();

			List<com.liferay.portal.model.Organization> organizations = null;

			if ((listeners.length > 0) || (organizationListeners.length > 0)) {
				organizations = getOrganizations(userId);

				for (com.liferay.portal.model.Organization organization : organizations) {
					for (ModelListener<User> listener : listeners) {
						listener.onBeforeRemoveAssociation(userId,
							com.liferay.portal.model.Organization.class.getName(),
							organization.getPrimaryKey());
					}

					for (ModelListener<com.liferay.portal.model.Organization> listener : organizationListeners) {
						listener.onBeforeRemoveAssociation(organization.getPrimaryKey(),
							User.class.getName(), userId);
					}
				}
			}

			_sqlUpdate.update(new Object[] { new Long(userId) });

			if ((listeners.length > 0) || (organizationListeners.length > 0)) {
				for (com.liferay.portal.model.Organization organization : organizations) {
					for (ModelListener<User> listener : listeners) {
						listener.onAfterRemoveAssociation(userId,
							com.liferay.portal.model.Organization.class.getName(),
							organization.getPrimaryKey());
					}

					for (ModelListener<com.liferay.portal.model.Organization> listener : organizationListeners) {
						listener.onAfterRemoveAssociation(organization.getPrimaryKey(),
							User.class.getName(), userId);
					}
				}
			}
		}

		private SqlUpdate _sqlUpdate;
	}

	protected class RemoveOrganization {
		protected RemoveOrganization() {
			_sqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(getDataSource(),
					"DELETE FROM Users_Orgs WHERE userId = ? AND organizationId = ?",
					new int[] { java.sql.Types.BIGINT, java.sql.Types.BIGINT });
		}

		protected void remove(long userId, long organizationId)
			throws SystemException {
			if (containsOrganization.contains(userId, organizationId)) {
				ModelListener<com.liferay.portal.model.Organization>[] organizationListeners =
					organizationPersistence.getListeners();

				for (ModelListener<User> listener : listeners) {
					listener.onBeforeRemoveAssociation(userId,
						com.liferay.portal.model.Organization.class.getName(),
						organizationId);
				}

				for (ModelListener<com.liferay.portal.model.Organization> listener : organizationListeners) {
					listener.onBeforeRemoveAssociation(organizationId,
						User.class.getName(), userId);
				}

				_sqlUpdate.update(new Object[] {
						new Long(userId), new Long(organizationId)
					});

				for (ModelListener<User> listener : listeners) {
					listener.onAfterRemoveAssociation(userId,
						com.liferay.portal.model.Organization.class.getName(),
						organizationId);
				}

				for (ModelListener<com.liferay.portal.model.Organization> listener : organizationListeners) {
					listener.onAfterRemoveAssociation(organizationId,
						User.class.getName(), userId);
				}
			}
		}

		private SqlUpdate _sqlUpdate;
	}

	protected class ContainsRole {
		protected ContainsRole() {
			_mappingSqlQuery = MappingSqlQueryFactoryUtil.getMappingSqlQuery(getDataSource(),
					_SQL_CONTAINSROLE,
					new int[] { java.sql.Types.BIGINT, java.sql.Types.BIGINT },
					RowMapper.COUNT);
		}

		protected boolean contains(long userId, long roleId) {
			List<Integer> results = _mappingSqlQuery.execute(new Object[] {
						new Long(userId), new Long(roleId)
					});

			if (results.size() > 0) {
				Integer count = results.get(0);

				if (count.intValue() > 0) {
					return true;
				}
			}

			return false;
		}

		private MappingSqlQuery<Integer> _mappingSqlQuery;
	}

	protected class AddRole {
		protected AddRole() {
			_sqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(getDataSource(),
					"INSERT INTO Users_Roles (userId, roleId) VALUES (?, ?)",
					new int[] { java.sql.Types.BIGINT, java.sql.Types.BIGINT });
		}

		protected void add(long userId, long roleId) throws SystemException {
			if (!containsRole.contains(userId, roleId)) {
				ModelListener<com.liferay.portal.model.Role>[] roleListeners = rolePersistence.getListeners();

				for (ModelListener<User> listener : listeners) {
					listener.onBeforeAddAssociation(userId,
						com.liferay.portal.model.Role.class.getName(), roleId);
				}

				for (ModelListener<com.liferay.portal.model.Role> listener : roleListeners) {
					listener.onBeforeAddAssociation(roleId,
						User.class.getName(), userId);
				}

				_sqlUpdate.update(new Object[] {
						new Long(userId), new Long(roleId)
					});

				for (ModelListener<User> listener : listeners) {
					listener.onAfterAddAssociation(userId,
						com.liferay.portal.model.Role.class.getName(), roleId);
				}

				for (ModelListener<com.liferay.portal.model.Role> listener : roleListeners) {
					listener.onAfterAddAssociation(roleId,
						User.class.getName(), userId);
				}
			}
		}

		private SqlUpdate _sqlUpdate;
	}

	protected class ClearRoles {
		protected ClearRoles() {
			_sqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(getDataSource(),
					"DELETE FROM Users_Roles WHERE userId = ?",
					new int[] { java.sql.Types.BIGINT });
		}

		protected void clear(long userId) throws SystemException {
			ModelListener<com.liferay.portal.model.Role>[] roleListeners = rolePersistence.getListeners();

			List<com.liferay.portal.model.Role> roles = null;

			if ((listeners.length > 0) || (roleListeners.length > 0)) {
				roles = getRoles(userId);

				for (com.liferay.portal.model.Role role : roles) {
					for (ModelListener<User> listener : listeners) {
						listener.onBeforeRemoveAssociation(userId,
							com.liferay.portal.model.Role.class.getName(),
							role.getPrimaryKey());
					}

					for (ModelListener<com.liferay.portal.model.Role> listener : roleListeners) {
						listener.onBeforeRemoveAssociation(role.getPrimaryKey(),
							User.class.getName(), userId);
					}
				}
			}

			_sqlUpdate.update(new Object[] { new Long(userId) });

			if ((listeners.length > 0) || (roleListeners.length > 0)) {
				for (com.liferay.portal.model.Role role : roles) {
					for (ModelListener<User> listener : listeners) {
						listener.onAfterRemoveAssociation(userId,
							com.liferay.portal.model.Role.class.getName(),
							role.getPrimaryKey());
					}

					for (ModelListener<com.liferay.portal.model.Role> listener : roleListeners) {
						listener.onAfterRemoveAssociation(role.getPrimaryKey(),
							User.class.getName(), userId);
					}
				}
			}
		}

		private SqlUpdate _sqlUpdate;
	}

	protected class RemoveRole {
		protected RemoveRole() {
			_sqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(getDataSource(),
					"DELETE FROM Users_Roles WHERE userId = ? AND roleId = ?",
					new int[] { java.sql.Types.BIGINT, java.sql.Types.BIGINT });
		}

		protected void remove(long userId, long roleId)
			throws SystemException {
			if (containsRole.contains(userId, roleId)) {
				ModelListener<com.liferay.portal.model.Role>[] roleListeners = rolePersistence.getListeners();

				for (ModelListener<User> listener : listeners) {
					listener.onBeforeRemoveAssociation(userId,
						com.liferay.portal.model.Role.class.getName(), roleId);
				}

				for (ModelListener<com.liferay.portal.model.Role> listener : roleListeners) {
					listener.onBeforeRemoveAssociation(roleId,
						User.class.getName(), userId);
				}

				_sqlUpdate.update(new Object[] {
						new Long(userId), new Long(roleId)
					});

				for (ModelListener<User> listener : listeners) {
					listener.onAfterRemoveAssociation(userId,
						com.liferay.portal.model.Role.class.getName(), roleId);
				}

				for (ModelListener<com.liferay.portal.model.Role> listener : roleListeners) {
					listener.onAfterRemoveAssociation(roleId,
						User.class.getName(), userId);
				}
			}
		}

		private SqlUpdate _sqlUpdate;
	}

	protected class ContainsTeam {
		protected ContainsTeam() {
			_mappingSqlQuery = MappingSqlQueryFactoryUtil.getMappingSqlQuery(getDataSource(),
					_SQL_CONTAINSTEAM,
					new int[] { java.sql.Types.BIGINT, java.sql.Types.BIGINT },
					RowMapper.COUNT);
		}

		protected boolean contains(long userId, long teamId) {
			List<Integer> results = _mappingSqlQuery.execute(new Object[] {
						new Long(userId), new Long(teamId)
					});

			if (results.size() > 0) {
				Integer count = results.get(0);

				if (count.intValue() > 0) {
					return true;
				}
			}

			return false;
		}

		private MappingSqlQuery<Integer> _mappingSqlQuery;
	}

	protected class AddTeam {
		protected AddTeam() {
			_sqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(getDataSource(),
					"INSERT INTO Users_Teams (userId, teamId) VALUES (?, ?)",
					new int[] { java.sql.Types.BIGINT, java.sql.Types.BIGINT });
		}

		protected void add(long userId, long teamId) throws SystemException {
			if (!containsTeam.contains(userId, teamId)) {
				ModelListener<com.liferay.portal.model.Team>[] teamListeners = teamPersistence.getListeners();

				for (ModelListener<User> listener : listeners) {
					listener.onBeforeAddAssociation(userId,
						com.liferay.portal.model.Team.class.getName(), teamId);
				}

				for (ModelListener<com.liferay.portal.model.Team> listener : teamListeners) {
					listener.onBeforeAddAssociation(teamId,
						User.class.getName(), userId);
				}

				_sqlUpdate.update(new Object[] {
						new Long(userId), new Long(teamId)
					});

				for (ModelListener<User> listener : listeners) {
					listener.onAfterAddAssociation(userId,
						com.liferay.portal.model.Team.class.getName(), teamId);
				}

				for (ModelListener<com.liferay.portal.model.Team> listener : teamListeners) {
					listener.onAfterAddAssociation(teamId,
						User.class.getName(), userId);
				}
			}
		}

		private SqlUpdate _sqlUpdate;
	}

	protected class ClearTeams {
		protected ClearTeams() {
			_sqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(getDataSource(),
					"DELETE FROM Users_Teams WHERE userId = ?",
					new int[] { java.sql.Types.BIGINT });
		}

		protected void clear(long userId) throws SystemException {
			ModelListener<com.liferay.portal.model.Team>[] teamListeners = teamPersistence.getListeners();

			List<com.liferay.portal.model.Team> teams = null;

			if ((listeners.length > 0) || (teamListeners.length > 0)) {
				teams = getTeams(userId);

				for (com.liferay.portal.model.Team team : teams) {
					for (ModelListener<User> listener : listeners) {
						listener.onBeforeRemoveAssociation(userId,
							com.liferay.portal.model.Team.class.getName(),
							team.getPrimaryKey());
					}

					for (ModelListener<com.liferay.portal.model.Team> listener : teamListeners) {
						listener.onBeforeRemoveAssociation(team.getPrimaryKey(),
							User.class.getName(), userId);
					}
				}
			}

			_sqlUpdate.update(new Object[] { new Long(userId) });

			if ((listeners.length > 0) || (teamListeners.length > 0)) {
				for (com.liferay.portal.model.Team team : teams) {
					for (ModelListener<User> listener : listeners) {
						listener.onAfterRemoveAssociation(userId,
							com.liferay.portal.model.Team.class.getName(),
							team.getPrimaryKey());
					}

					for (ModelListener<com.liferay.portal.model.Team> listener : teamListeners) {
						listener.onAfterRemoveAssociation(team.getPrimaryKey(),
							User.class.getName(), userId);
					}
				}
			}
		}

		private SqlUpdate _sqlUpdate;
	}

	protected class RemoveTeam {
		protected RemoveTeam() {
			_sqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(getDataSource(),
					"DELETE FROM Users_Teams WHERE userId = ? AND teamId = ?",
					new int[] { java.sql.Types.BIGINT, java.sql.Types.BIGINT });
		}

		protected void remove(long userId, long teamId)
			throws SystemException {
			if (containsTeam.contains(userId, teamId)) {
				ModelListener<com.liferay.portal.model.Team>[] teamListeners = teamPersistence.getListeners();

				for (ModelListener<User> listener : listeners) {
					listener.onBeforeRemoveAssociation(userId,
						com.liferay.portal.model.Team.class.getName(), teamId);
				}

				for (ModelListener<com.liferay.portal.model.Team> listener : teamListeners) {
					listener.onBeforeRemoveAssociation(teamId,
						User.class.getName(), userId);
				}

				_sqlUpdate.update(new Object[] {
						new Long(userId), new Long(teamId)
					});

				for (ModelListener<User> listener : listeners) {
					listener.onAfterRemoveAssociation(userId,
						com.liferay.portal.model.Team.class.getName(), teamId);
				}

				for (ModelListener<com.liferay.portal.model.Team> listener : teamListeners) {
					listener.onAfterRemoveAssociation(teamId,
						User.class.getName(), userId);
				}
			}
		}

		private SqlUpdate _sqlUpdate;
	}

	protected class ContainsUserGroup {
		protected ContainsUserGroup() {
			_mappingSqlQuery = MappingSqlQueryFactoryUtil.getMappingSqlQuery(getDataSource(),
					_SQL_CONTAINSUSERGROUP,
					new int[] { java.sql.Types.BIGINT, java.sql.Types.BIGINT },
					RowMapper.COUNT);
		}

		protected boolean contains(long userId, long userGroupId) {
			List<Integer> results = _mappingSqlQuery.execute(new Object[] {
						new Long(userId), new Long(userGroupId)
					});

			if (results.size() > 0) {
				Integer count = results.get(0);

				if (count.intValue() > 0) {
					return true;
				}
			}

			return false;
		}

		private MappingSqlQuery<Integer> _mappingSqlQuery;
	}

	protected class AddUserGroup {
		protected AddUserGroup() {
			_sqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(getDataSource(),
					"INSERT INTO Users_UserGroups (userId, userGroupId) VALUES (?, ?)",
					new int[] { java.sql.Types.BIGINT, java.sql.Types.BIGINT });
		}

		protected void add(long userId, long userGroupId)
			throws SystemException {
			if (!containsUserGroup.contains(userId, userGroupId)) {
				ModelListener<com.liferay.portal.model.UserGroup>[] userGroupListeners =
					userGroupPersistence.getListeners();

				for (ModelListener<User> listener : listeners) {
					listener.onBeforeAddAssociation(userId,
						com.liferay.portal.model.UserGroup.class.getName(),
						userGroupId);
				}

				for (ModelListener<com.liferay.portal.model.UserGroup> listener : userGroupListeners) {
					listener.onBeforeAddAssociation(userGroupId,
						User.class.getName(), userId);
				}

				_sqlUpdate.update(new Object[] {
						new Long(userId), new Long(userGroupId)
					});

				for (ModelListener<User> listener : listeners) {
					listener.onAfterAddAssociation(userId,
						com.liferay.portal.model.UserGroup.class.getName(),
						userGroupId);
				}

				for (ModelListener<com.liferay.portal.model.UserGroup> listener : userGroupListeners) {
					listener.onAfterAddAssociation(userGroupId,
						User.class.getName(), userId);
				}
			}
		}

		private SqlUpdate _sqlUpdate;
	}

	protected class ClearUserGroups {
		protected ClearUserGroups() {
			_sqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(getDataSource(),
					"DELETE FROM Users_UserGroups WHERE userId = ?",
					new int[] { java.sql.Types.BIGINT });
		}

		protected void clear(long userId) throws SystemException {
			ModelListener<com.liferay.portal.model.UserGroup>[] userGroupListeners =
				userGroupPersistence.getListeners();

			List<com.liferay.portal.model.UserGroup> userGroups = null;

			if ((listeners.length > 0) || (userGroupListeners.length > 0)) {
				userGroups = getUserGroups(userId);

				for (com.liferay.portal.model.UserGroup userGroup : userGroups) {
					for (ModelListener<User> listener : listeners) {
						listener.onBeforeRemoveAssociation(userId,
							com.liferay.portal.model.UserGroup.class.getName(),
							userGroup.getPrimaryKey());
					}

					for (ModelListener<com.liferay.portal.model.UserGroup> listener : userGroupListeners) {
						listener.onBeforeRemoveAssociation(userGroup.getPrimaryKey(),
							User.class.getName(), userId);
					}
				}
			}

			_sqlUpdate.update(new Object[] { new Long(userId) });

			if ((listeners.length > 0) || (userGroupListeners.length > 0)) {
				for (com.liferay.portal.model.UserGroup userGroup : userGroups) {
					for (ModelListener<User> listener : listeners) {
						listener.onAfterRemoveAssociation(userId,
							com.liferay.portal.model.UserGroup.class.getName(),
							userGroup.getPrimaryKey());
					}

					for (ModelListener<com.liferay.portal.model.UserGroup> listener : userGroupListeners) {
						listener.onAfterRemoveAssociation(userGroup.getPrimaryKey(),
							User.class.getName(), userId);
					}
				}
			}
		}

		private SqlUpdate _sqlUpdate;
	}

	protected class RemoveUserGroup {
		protected RemoveUserGroup() {
			_sqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(getDataSource(),
					"DELETE FROM Users_UserGroups WHERE userId = ? AND userGroupId = ?",
					new int[] { java.sql.Types.BIGINT, java.sql.Types.BIGINT });
		}

		protected void remove(long userId, long userGroupId)
			throws SystemException {
			if (containsUserGroup.contains(userId, userGroupId)) {
				ModelListener<com.liferay.portal.model.UserGroup>[] userGroupListeners =
					userGroupPersistence.getListeners();

				for (ModelListener<User> listener : listeners) {
					listener.onBeforeRemoveAssociation(userId,
						com.liferay.portal.model.UserGroup.class.getName(),
						userGroupId);
				}

				for (ModelListener<com.liferay.portal.model.UserGroup> listener : userGroupListeners) {
					listener.onBeforeRemoveAssociation(userGroupId,
						User.class.getName(), userId);
				}

				_sqlUpdate.update(new Object[] {
						new Long(userId), new Long(userGroupId)
					});

				for (ModelListener<User> listener : listeners) {
					listener.onAfterRemoveAssociation(userId,
						com.liferay.portal.model.UserGroup.class.getName(),
						userGroupId);
				}

				for (ModelListener<com.liferay.portal.model.UserGroup> listener : userGroupListeners) {
					listener.onAfterRemoveAssociation(userGroupId,
						User.class.getName(), userId);
				}
			}
		}

		private SqlUpdate _sqlUpdate;
	}

	private static final String _SQL_SELECT_USER = "SELECT user FROM User user";
	private static final String _SQL_SELECT_USER_WHERE = "SELECT user FROM User user WHERE ";
	private static final String _SQL_COUNT_USER = "SELECT COUNT(user) FROM User user";
	private static final String _SQL_COUNT_USER_WHERE = "SELECT COUNT(user) FROM User user WHERE ";
	private static final String _SQL_GETGROUPS = "SELECT {Group_.*} FROM Group_ INNER JOIN Users_Groups ON (Users_Groups.groupId = Group_.groupId) WHERE (Users_Groups.userId = ?)";
	private static final String _SQL_GETGROUPSSIZE = "SELECT COUNT(*) AS COUNT_VALUE FROM Users_Groups WHERE userId = ?";
	private static final String _SQL_CONTAINSGROUP = "SELECT COUNT(*) AS COUNT_VALUE FROM Users_Groups WHERE userId = ? AND groupId = ?";
	private static final String _SQL_GETORGANIZATIONS = "SELECT {Organization_.*} FROM Organization_ INNER JOIN Users_Orgs ON (Users_Orgs.organizationId = Organization_.organizationId) WHERE (Users_Orgs.userId = ?)";
	private static final String _SQL_GETORGANIZATIONSSIZE = "SELECT COUNT(*) AS COUNT_VALUE FROM Users_Orgs WHERE userId = ?";
	private static final String _SQL_CONTAINSORGANIZATION = "SELECT COUNT(*) AS COUNT_VALUE FROM Users_Orgs WHERE userId = ? AND organizationId = ?";
	private static final String _SQL_GETROLES = "SELECT {Role_.*} FROM Role_ INNER JOIN Users_Roles ON (Users_Roles.roleId = Role_.roleId) WHERE (Users_Roles.userId = ?)";
	private static final String _SQL_GETROLESSIZE = "SELECT COUNT(*) AS COUNT_VALUE FROM Users_Roles WHERE userId = ?";
	private static final String _SQL_CONTAINSROLE = "SELECT COUNT(*) AS COUNT_VALUE FROM Users_Roles WHERE userId = ? AND roleId = ?";
	private static final String _SQL_GETTEAMS = "SELECT {Team.*} FROM Team INNER JOIN Users_Teams ON (Users_Teams.teamId = Team.teamId) WHERE (Users_Teams.userId = ?)";
	private static final String _SQL_GETTEAMSSIZE = "SELECT COUNT(*) AS COUNT_VALUE FROM Users_Teams WHERE userId = ?";
	private static final String _SQL_CONTAINSTEAM = "SELECT COUNT(*) AS COUNT_VALUE FROM Users_Teams WHERE userId = ? AND teamId = ?";
	private static final String _SQL_GETUSERGROUPS = "SELECT {UserGroup.*} FROM UserGroup INNER JOIN Users_UserGroups ON (Users_UserGroups.userGroupId = UserGroup.userGroupId) WHERE (Users_UserGroups.userId = ?)";
	private static final String _SQL_GETUSERGROUPSSIZE = "SELECT COUNT(*) AS COUNT_VALUE FROM Users_UserGroups WHERE userId = ?";
	private static final String _SQL_CONTAINSUSERGROUP = "SELECT COUNT(*) AS COUNT_VALUE FROM Users_UserGroups WHERE userId = ? AND userGroupId = ?";
	private static final String _ORDER_BY_ENTITY_ALIAS = "user.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No User exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No User exists with the key {";
	private static final boolean _HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE = com.liferay.portal.util.PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE;
	private static Log _log = LogFactoryUtil.getLog(UserPersistenceImpl.class);
	private static User _nullUser = new UserImpl() {
			@Override
			public Object clone() {
				return this;
			}

			@Override
			public CacheModel<User> toCacheModel() {
				return _nullUserCacheModel;
			}
		};

	private static CacheModel<User> _nullUserCacheModel = new CacheModel<User>() {
			public User toEntityModel() {
				return _nullUser;
			}
		};
}