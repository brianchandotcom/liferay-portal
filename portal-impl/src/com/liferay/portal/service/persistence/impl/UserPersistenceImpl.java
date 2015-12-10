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

package com.liferay.portal.service.persistence.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.NoSuchUserException;
import com.liferay.portal.kernel.bean.BeanReference;
import com.liferay.portal.kernel.dao.orm.EntityCache;
import com.liferay.portal.kernel.dao.orm.EntityCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderCache;
import com.liferay.portal.kernel.dao.orm.FinderCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
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
import com.liferay.portal.model.MVCCModel;
import com.liferay.portal.model.User;
import com.liferay.portal.model.impl.UserImpl;
import com.liferay.portal.model.impl.UserModelImpl;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceContextThreadLocal;
import com.liferay.portal.service.persistence.CompanyProvider;
import com.liferay.portal.service.persistence.GroupPersistence;
import com.liferay.portal.service.persistence.OrganizationPersistence;
import com.liferay.portal.service.persistence.RolePersistence;
import com.liferay.portal.service.persistence.TeamPersistence;
import com.liferay.portal.service.persistence.UserGroupPersistence;
import com.liferay.portal.service.persistence.UserPersistence;

import java.io.Serializable;

import java.sql.Timestamp;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
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
 * @see com.liferay.portal.service.persistence.UserUtil
 * @generated
 */
@ProviderType
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
	 */
	@Override
	public List<User> findByUuid(String uuid) {
		return findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the users where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link UserModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of users
	 * @param end the upper bound of the range of users (not inclusive)
	 * @return the range of matching users
	 */
	@Override
	public List<User> findByUuid(String uuid, int start, int end) {
		return findByUuid(uuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the users where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link UserModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of users
	 * @param end the upper bound of the range of users (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching users
	 */
	@Override
	public List<User> findByUuid(String uuid, int start, int end,
		OrderByComparator<User> orderByComparator) {
		return findByUuid(uuid, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the users where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link UserModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of users
	 * @param end the upper bound of the range of users (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching users
	 */
	@Override
	public List<User> findByUuid(String uuid, int start, int end,
		OrderByComparator<User> orderByComparator, boolean retrieveFromCache) {
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

		List<User> list = null;

		if (retrieveFromCache) {
			list = (List<User>)finderCache.getResult(finderPath, finderArgs,
					this);

			if ((list != null) && !list.isEmpty()) {
				for (User user : list) {
					if (!Validator.equals(uuid, user.getUuid())) {
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

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<User>)QueryUtil.list(q, getDialect(), start,
							end);
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
	 * Returns the first user in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching user
	 * @throws NoSuchUserException if a matching user could not be found
	 */
	@Override
	public User findByUuid_First(String uuid,
		OrderByComparator<User> orderByComparator) throws NoSuchUserException {
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
	 */
	@Override
	public User fetchByUuid_First(String uuid,
		OrderByComparator<User> orderByComparator) {
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
	 * @throws NoSuchUserException if a matching user could not be found
	 */
	@Override
	public User findByUuid_Last(String uuid,
		OrderByComparator<User> orderByComparator) throws NoSuchUserException {
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
	 */
	@Override
	public User fetchByUuid_Last(String uuid,
		OrderByComparator<User> orderByComparator) {
		int count = countByUuid(uuid);

		if (count == 0) {
			return null;
		}

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
	 * @throws NoSuchUserException if a user with the primary key could not be found
	 */
	@Override
	public User[] findByUuid_PrevAndNext(long userId, String uuid,
		OrderByComparator<User> orderByComparator) throws NoSuchUserException {
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
		String uuid, OrderByComparator<User> orderByComparator, boolean previous) {
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
	 */
	@Override
	public void removeByUuid(String uuid) {
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
	 */
	@Override
	public int countByUuid(String uuid) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_UUID;

		Object[] finderArgs = new Object[] { uuid };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

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
	 */
	@Override
	public List<User> findByUuid_C(String uuid, long companyId) {
		return findByUuid_C(uuid, companyId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the users where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link UserModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of users
	 * @param end the upper bound of the range of users (not inclusive)
	 * @return the range of matching users
	 */
	@Override
	public List<User> findByUuid_C(String uuid, long companyId, int start,
		int end) {
		return findByUuid_C(uuid, companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the users where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link UserModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of users
	 * @param end the upper bound of the range of users (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching users
	 */
	@Override
	public List<User> findByUuid_C(String uuid, long companyId, int start,
		int end, OrderByComparator<User> orderByComparator) {
		return findByUuid_C(uuid, companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the users where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link UserModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of users
	 * @param end the upper bound of the range of users (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching users
	 */
	@Override
	public List<User> findByUuid_C(String uuid, long companyId, int start,
		int end, OrderByComparator<User> orderByComparator,
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

		List<User> list = null;

		if (retrieveFromCache) {
			list = (List<User>)finderCache.getResult(finderPath, finderArgs,
					this);

			if ((list != null) && !list.isEmpty()) {
				for (User user : list) {
					if (!Validator.equals(uuid, user.getUuid()) ||
							(companyId != user.getCompanyId())) {
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

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<User>)QueryUtil.list(q, getDialect(), start,
							end);
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
	 * Returns the first user in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching user
	 * @throws NoSuchUserException if a matching user could not be found
	 */
	@Override
	public User findByUuid_C_First(String uuid, long companyId,
		OrderByComparator<User> orderByComparator) throws NoSuchUserException {
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
	 */
	@Override
	public User fetchByUuid_C_First(String uuid, long companyId,
		OrderByComparator<User> orderByComparator) {
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
	 * @throws NoSuchUserException if a matching user could not be found
	 */
	@Override
	public User findByUuid_C_Last(String uuid, long companyId,
		OrderByComparator<User> orderByComparator) throws NoSuchUserException {
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
	 */
	@Override
	public User fetchByUuid_C_Last(String uuid, long companyId,
		OrderByComparator<User> orderByComparator) {
		int count = countByUuid_C(uuid, companyId);

		if (count == 0) {
			return null;
		}

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
	 * @throws NoSuchUserException if a user with the primary key could not be found
	 */
	@Override
	public User[] findByUuid_C_PrevAndNext(long userId, String uuid,
		long companyId, OrderByComparator<User> orderByComparator)
		throws NoSuchUserException {
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
		String uuid, long companyId, OrderByComparator<User> orderByComparator,
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
	 */
	@Override
	public void removeByUuid_C(String uuid, long companyId) {
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
	 */
	@Override
	public int countByUuid_C(String uuid, long companyId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_UUID_C;

		Object[] finderArgs = new Object[] { uuid, companyId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

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
	 */
	@Override
	public List<User> findByCompanyId(long companyId) {
		return findByCompanyId(companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the users where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link UserModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of users
	 * @param end the upper bound of the range of users (not inclusive)
	 * @return the range of matching users
	 */
	@Override
	public List<User> findByCompanyId(long companyId, int start, int end) {
		return findByCompanyId(companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the users where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link UserModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of users
	 * @param end the upper bound of the range of users (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching users
	 */
	@Override
	public List<User> findByCompanyId(long companyId, int start, int end,
		OrderByComparator<User> orderByComparator) {
		return findByCompanyId(companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the users where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link UserModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of users
	 * @param end the upper bound of the range of users (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching users
	 */
	@Override
	public List<User> findByCompanyId(long companyId, int start, int end,
		OrderByComparator<User> orderByComparator, boolean retrieveFromCache) {
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

		List<User> list = null;

		if (retrieveFromCache) {
			list = (List<User>)finderCache.getResult(finderPath, finderArgs,
					this);

			if ((list != null) && !list.isEmpty()) {
				for (User user : list) {
					if ((companyId != user.getCompanyId())) {
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

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<User>)QueryUtil.list(q, getDialect(), start,
							end);
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
	 * Returns the first user in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching user
	 * @throws NoSuchUserException if a matching user could not be found
	 */
	@Override
	public User findByCompanyId_First(long companyId,
		OrderByComparator<User> orderByComparator) throws NoSuchUserException {
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
	 */
	@Override
	public User fetchByCompanyId_First(long companyId,
		OrderByComparator<User> orderByComparator) {
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
	 * @throws NoSuchUserException if a matching user could not be found
	 */
	@Override
	public User findByCompanyId_Last(long companyId,
		OrderByComparator<User> orderByComparator) throws NoSuchUserException {
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
	 */
	@Override
	public User fetchByCompanyId_Last(long companyId,
		OrderByComparator<User> orderByComparator) {
		int count = countByCompanyId(companyId);

		if (count == 0) {
			return null;
		}

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
	 * @throws NoSuchUserException if a user with the primary key could not be found
	 */
	@Override
	public User[] findByCompanyId_PrevAndNext(long userId, long companyId,
		OrderByComparator<User> orderByComparator) throws NoSuchUserException {
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
		long companyId, OrderByComparator<User> orderByComparator,
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
	 */
	@Override
	public void removeByCompanyId(long companyId) {
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
	 */
	@Override
	public int countByCompanyId(long companyId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_COMPANYID;

		Object[] finderArgs = new Object[] { companyId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

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
	 * Returns the user where contactId = &#63; or throws a {@link NoSuchUserException} if it could not be found.
	 *
	 * @param contactId the contact ID
	 * @return the matching user
	 * @throws NoSuchUserException if a matching user could not be found
	 */
	@Override
	public User findByContactId(long contactId) throws NoSuchUserException {
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
	 */
	@Override
	public User fetchByContactId(long contactId) {
		return fetchByContactId(contactId, true);
	}

	/**
	 * Returns the user where contactId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param contactId the contact ID
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the matching user, or <code>null</code> if a matching user could not be found
	 */
	@Override
	public User fetchByContactId(long contactId, boolean retrieveFromCache) {
		Object[] finderArgs = new Object[] { contactId };

		Object result = null;

		if (retrieveFromCache) {
			result = finderCache.getResult(FINDER_PATH_FETCH_BY_CONTACTID,
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
					finderCache.putResult(FINDER_PATH_FETCH_BY_CONTACTID,
						finderArgs, list);
				}
				else {
					User user = list.get(0);

					result = user;

					cacheResult(user);

					if ((user.getContactId() != contactId)) {
						finderCache.putResult(FINDER_PATH_FETCH_BY_CONTACTID,
							finderArgs, user);
					}
				}
			}
			catch (Exception e) {
				finderCache.removeResult(FINDER_PATH_FETCH_BY_CONTACTID,
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
	 */
	@Override
	public User removeByContactId(long contactId) throws NoSuchUserException {
		User user = findByContactId(contactId);

		return remove(user);
	}

	/**
	 * Returns the number of users where contactId = &#63;.
	 *
	 * @param contactId the contact ID
	 * @return the number of matching users
	 */
	@Override
	public int countByContactId(long contactId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_CONTACTID;

		Object[] finderArgs = new Object[] { contactId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

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
	 */
	@Override
	public List<User> findByEmailAddress(String emailAddress) {
		return findByEmailAddress(emailAddress, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the users where emailAddress = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link UserModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param emailAddress the email address
	 * @param start the lower bound of the range of users
	 * @param end the upper bound of the range of users (not inclusive)
	 * @return the range of matching users
	 */
	@Override
	public List<User> findByEmailAddress(String emailAddress, int start, int end) {
		return findByEmailAddress(emailAddress, start, end, null);
	}

	/**
	 * Returns an ordered range of all the users where emailAddress = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link UserModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param emailAddress the email address
	 * @param start the lower bound of the range of users
	 * @param end the upper bound of the range of users (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching users
	 */
	@Override
	public List<User> findByEmailAddress(String emailAddress, int start,
		int end, OrderByComparator<User> orderByComparator) {
		return findByEmailAddress(emailAddress, start, end, orderByComparator,
			true);
	}

	/**
	 * Returns an ordered range of all the users where emailAddress = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link UserModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param emailAddress the email address
	 * @param start the lower bound of the range of users
	 * @param end the upper bound of the range of users (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching users
	 */
	@Override
	public List<User> findByEmailAddress(String emailAddress, int start,
		int end, OrderByComparator<User> orderByComparator,
		boolean retrieveFromCache) {
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

		List<User> list = null;

		if (retrieveFromCache) {
			list = (List<User>)finderCache.getResult(finderPath, finderArgs,
					this);

			if ((list != null) && !list.isEmpty()) {
				for (User user : list) {
					if (!Validator.equals(emailAddress, user.getEmailAddress())) {
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

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<User>)QueryUtil.list(q, getDialect(), start,
							end);
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
	 * Returns the first user in the ordered set where emailAddress = &#63;.
	 *
	 * @param emailAddress the email address
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching user
	 * @throws NoSuchUserException if a matching user could not be found
	 */
	@Override
	public User findByEmailAddress_First(String emailAddress,
		OrderByComparator<User> orderByComparator) throws NoSuchUserException {
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
	 */
	@Override
	public User fetchByEmailAddress_First(String emailAddress,
		OrderByComparator<User> orderByComparator) {
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
	 * @throws NoSuchUserException if a matching user could not be found
	 */
	@Override
	public User findByEmailAddress_Last(String emailAddress,
		OrderByComparator<User> orderByComparator) throws NoSuchUserException {
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
	 */
	@Override
	public User fetchByEmailAddress_Last(String emailAddress,
		OrderByComparator<User> orderByComparator) {
		int count = countByEmailAddress(emailAddress);

		if (count == 0) {
			return null;
		}

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
	 * @throws NoSuchUserException if a user with the primary key could not be found
	 */
	@Override
	public User[] findByEmailAddress_PrevAndNext(long userId,
		String emailAddress, OrderByComparator<User> orderByComparator)
		throws NoSuchUserException {
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
		String emailAddress, OrderByComparator<User> orderByComparator,
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
	 */
	@Override
	public void removeByEmailAddress(String emailAddress) {
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
	 */
	@Override
	public int countByEmailAddress(String emailAddress) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_EMAILADDRESS;

		Object[] finderArgs = new Object[] { emailAddress };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

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
	 * Returns the user where portraitId = &#63; or throws a {@link NoSuchUserException} if it could not be found.
	 *
	 * @param portraitId the portrait ID
	 * @return the matching user
	 * @throws NoSuchUserException if a matching user could not be found
	 */
	@Override
	public User findByPortraitId(long portraitId) throws NoSuchUserException {
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
	 */
	@Override
	public User fetchByPortraitId(long portraitId) {
		return fetchByPortraitId(portraitId, true);
	}

	/**
	 * Returns the user where portraitId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param portraitId the portrait ID
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the matching user, or <code>null</code> if a matching user could not be found
	 */
	@Override
	public User fetchByPortraitId(long portraitId, boolean retrieveFromCache) {
		Object[] finderArgs = new Object[] { portraitId };

		Object result = null;

		if (retrieveFromCache) {
			result = finderCache.getResult(FINDER_PATH_FETCH_BY_PORTRAITID,
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
					finderCache.putResult(FINDER_PATH_FETCH_BY_PORTRAITID,
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
						finderCache.putResult(FINDER_PATH_FETCH_BY_PORTRAITID,
							finderArgs, user);
					}
				}
			}
			catch (Exception e) {
				finderCache.removeResult(FINDER_PATH_FETCH_BY_PORTRAITID,
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
	 */
	@Override
	public User removeByPortraitId(long portraitId) throws NoSuchUserException {
		User user = findByPortraitId(portraitId);

		return remove(user);
	}

	/**
	 * Returns the number of users where portraitId = &#63;.
	 *
	 * @param portraitId the portrait ID
	 * @return the number of matching users
	 */
	@Override
	public int countByPortraitId(long portraitId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_PORTRAITID;

		Object[] finderArgs = new Object[] { portraitId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

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
	 * Returns the user where companyId = &#63; and userId = &#63; or throws a {@link NoSuchUserException} if it could not be found.
	 *
	 * @param companyId the company ID
	 * @param userId the user ID
	 * @return the matching user
	 * @throws NoSuchUserException if a matching user could not be found
	 */
	@Override
	public User findByC_U(long companyId, long userId)
		throws NoSuchUserException {
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
	 */
	@Override
	public User fetchByC_U(long companyId, long userId) {
		return fetchByC_U(companyId, userId, true);
	}

	/**
	 * Returns the user where companyId = &#63; and userId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param companyId the company ID
	 * @param userId the user ID
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the matching user, or <code>null</code> if a matching user could not be found
	 */
	@Override
	public User fetchByC_U(long companyId, long userId,
		boolean retrieveFromCache) {
		Object[] finderArgs = new Object[] { companyId, userId };

		Object result = null;

		if (retrieveFromCache) {
			result = finderCache.getResult(FINDER_PATH_FETCH_BY_C_U,
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
					finderCache.putResult(FINDER_PATH_FETCH_BY_C_U, finderArgs,
						list);
				}
				else {
					User user = list.get(0);

					result = user;

					cacheResult(user);

					if ((user.getCompanyId() != companyId) ||
							(user.getUserId() != userId)) {
						finderCache.putResult(FINDER_PATH_FETCH_BY_C_U,
							finderArgs, user);
					}
				}
			}
			catch (Exception e) {
				finderCache.removeResult(FINDER_PATH_FETCH_BY_C_U, finderArgs);

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
	 */
	@Override
	public User removeByC_U(long companyId, long userId)
		throws NoSuchUserException {
		User user = findByC_U(companyId, userId);

		return remove(user);
	}

	/**
	 * Returns the number of users where companyId = &#63; and userId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param userId the user ID
	 * @return the number of matching users
	 */
	@Override
	public int countByC_U(long companyId, long userId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_C_U;

		Object[] finderArgs = new Object[] { companyId, userId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

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
	 */
	@Override
	public List<User> findByC_CD(long companyId, Date createDate) {
		return findByC_CD(companyId, createDate, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the users where companyId = &#63; and createDate = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link UserModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param createDate the create date
	 * @param start the lower bound of the range of users
	 * @param end the upper bound of the range of users (not inclusive)
	 * @return the range of matching users
	 */
	@Override
	public List<User> findByC_CD(long companyId, Date createDate, int start,
		int end) {
		return findByC_CD(companyId, createDate, start, end, null);
	}

	/**
	 * Returns an ordered range of all the users where companyId = &#63; and createDate = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link UserModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param createDate the create date
	 * @param start the lower bound of the range of users
	 * @param end the upper bound of the range of users (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching users
	 */
	@Override
	public List<User> findByC_CD(long companyId, Date createDate, int start,
		int end, OrderByComparator<User> orderByComparator) {
		return findByC_CD(companyId, createDate, start, end, orderByComparator,
			true);
	}

	/**
	 * Returns an ordered range of all the users where companyId = &#63; and createDate = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link UserModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param createDate the create date
	 * @param start the lower bound of the range of users
	 * @param end the upper bound of the range of users (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching users
	 */
	@Override
	public List<User> findByC_CD(long companyId, Date createDate, int start,
		int end, OrderByComparator<User> orderByComparator,
		boolean retrieveFromCache) {
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

		List<User> list = null;

		if (retrieveFromCache) {
			list = (List<User>)finderCache.getResult(finderPath, finderArgs,
					this);

			if ((list != null) && !list.isEmpty()) {
				for (User user : list) {
					if ((companyId != user.getCompanyId()) ||
							!Validator.equals(createDate, user.getCreateDate())) {
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
					qPos.add(new Timestamp(createDate.getTime()));
				}

				if (!pagination) {
					list = (List<User>)QueryUtil.list(q, getDialect(), start,
							end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<User>)QueryUtil.list(q, getDialect(), start,
							end);
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
	 * Returns the first user in the ordered set where companyId = &#63; and createDate = &#63;.
	 *
	 * @param companyId the company ID
	 * @param createDate the create date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching user
	 * @throws NoSuchUserException if a matching user could not be found
	 */
	@Override
	public User findByC_CD_First(long companyId, Date createDate,
		OrderByComparator<User> orderByComparator) throws NoSuchUserException {
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
	 */
	@Override
	public User fetchByC_CD_First(long companyId, Date createDate,
		OrderByComparator<User> orderByComparator) {
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
	 * @throws NoSuchUserException if a matching user could not be found
	 */
	@Override
	public User findByC_CD_Last(long companyId, Date createDate,
		OrderByComparator<User> orderByComparator) throws NoSuchUserException {
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
	 */
	@Override
	public User fetchByC_CD_Last(long companyId, Date createDate,
		OrderByComparator<User> orderByComparator) {
		int count = countByC_CD(companyId, createDate);

		if (count == 0) {
			return null;
		}

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
	 * @throws NoSuchUserException if a user with the primary key could not be found
	 */
	@Override
	public User[] findByC_CD_PrevAndNext(long userId, long companyId,
		Date createDate, OrderByComparator<User> orderByComparator)
		throws NoSuchUserException {
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
		long companyId, Date createDate,
		OrderByComparator<User> orderByComparator, boolean previous) {
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
			qPos.add(new Timestamp(createDate.getTime()));
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
	 */
	@Override
	public void removeByC_CD(long companyId, Date createDate) {
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
	 */
	@Override
	public int countByC_CD(long companyId, Date createDate) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_C_CD;

		Object[] finderArgs = new Object[] { companyId, createDate };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

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
					qPos.add(new Timestamp(createDate.getTime()));
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
	 */
	@Override
	public List<User> findByC_MD(long companyId, Date modifiedDate) {
		return findByC_MD(companyId, modifiedDate, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the users where companyId = &#63; and modifiedDate = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link UserModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param modifiedDate the modified date
	 * @param start the lower bound of the range of users
	 * @param end the upper bound of the range of users (not inclusive)
	 * @return the range of matching users
	 */
	@Override
	public List<User> findByC_MD(long companyId, Date modifiedDate, int start,
		int end) {
		return findByC_MD(companyId, modifiedDate, start, end, null);
	}

	/**
	 * Returns an ordered range of all the users where companyId = &#63; and modifiedDate = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link UserModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param modifiedDate the modified date
	 * @param start the lower bound of the range of users
	 * @param end the upper bound of the range of users (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching users
	 */
	@Override
	public List<User> findByC_MD(long companyId, Date modifiedDate, int start,
		int end, OrderByComparator<User> orderByComparator) {
		return findByC_MD(companyId, modifiedDate, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the users where companyId = &#63; and modifiedDate = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link UserModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param modifiedDate the modified date
	 * @param start the lower bound of the range of users
	 * @param end the upper bound of the range of users (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching users
	 */
	@Override
	public List<User> findByC_MD(long companyId, Date modifiedDate, int start,
		int end, OrderByComparator<User> orderByComparator,
		boolean retrieveFromCache) {
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

		List<User> list = null;

		if (retrieveFromCache) {
			list = (List<User>)finderCache.getResult(finderPath, finderArgs,
					this);

			if ((list != null) && !list.isEmpty()) {
				for (User user : list) {
					if ((companyId != user.getCompanyId()) ||
							!Validator.equals(modifiedDate,
								user.getModifiedDate())) {
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
					qPos.add(new Timestamp(modifiedDate.getTime()));
				}

				if (!pagination) {
					list = (List<User>)QueryUtil.list(q, getDialect(), start,
							end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<User>)QueryUtil.list(q, getDialect(), start,
							end);
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
	 * Returns the first user in the ordered set where companyId = &#63; and modifiedDate = &#63;.
	 *
	 * @param companyId the company ID
	 * @param modifiedDate the modified date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching user
	 * @throws NoSuchUserException if a matching user could not be found
	 */
	@Override
	public User findByC_MD_First(long companyId, Date modifiedDate,
		OrderByComparator<User> orderByComparator) throws NoSuchUserException {
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
	 */
	@Override
	public User fetchByC_MD_First(long companyId, Date modifiedDate,
		OrderByComparator<User> orderByComparator) {
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
	 * @throws NoSuchUserException if a matching user could not be found
	 */
	@Override
	public User findByC_MD_Last(long companyId, Date modifiedDate,
		OrderByComparator<User> orderByComparator) throws NoSuchUserException {
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
	 */
	@Override
	public User fetchByC_MD_Last(long companyId, Date modifiedDate,
		OrderByComparator<User> orderByComparator) {
		int count = countByC_MD(companyId, modifiedDate);

		if (count == 0) {
			return null;
		}

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
	 * @throws NoSuchUserException if a user with the primary key could not be found
	 */
	@Override
	public User[] findByC_MD_PrevAndNext(long userId, long companyId,
		Date modifiedDate, OrderByComparator<User> orderByComparator)
		throws NoSuchUserException {
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
		long companyId, Date modifiedDate,
		OrderByComparator<User> orderByComparator, boolean previous) {
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
			qPos.add(new Timestamp(modifiedDate.getTime()));
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
	 */
	@Override
	public void removeByC_MD(long companyId, Date modifiedDate) {
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
	 */
	@Override
	public int countByC_MD(long companyId, Date modifiedDate) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_C_MD;

		Object[] finderArgs = new Object[] { companyId, modifiedDate };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

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
					qPos.add(new Timestamp(modifiedDate.getTime()));
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
	 * Returns the user where companyId = &#63; and defaultUser = &#63; or throws a {@link NoSuchUserException} if it could not be found.
	 *
	 * @param companyId the company ID
	 * @param defaultUser the default user
	 * @return the matching user
	 * @throws NoSuchUserException if a matching user could not be found
	 */
	@Override
	public User findByC_DU(long companyId, boolean defaultUser)
		throws NoSuchUserException {
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
	 */
	@Override
	public User fetchByC_DU(long companyId, boolean defaultUser) {
		return fetchByC_DU(companyId, defaultUser, true);
	}

	/**
	 * Returns the user where companyId = &#63; and defaultUser = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param companyId the company ID
	 * @param defaultUser the default user
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the matching user, or <code>null</code> if a matching user could not be found
	 */
	@Override
	public User fetchByC_DU(long companyId, boolean defaultUser,
		boolean retrieveFromCache) {
		Object[] finderArgs = new Object[] { companyId, defaultUser };

		Object result = null;

		if (retrieveFromCache) {
			result = finderCache.getResult(FINDER_PATH_FETCH_BY_C_DU,
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
					finderCache.putResult(FINDER_PATH_FETCH_BY_C_DU,
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
						finderCache.putResult(FINDER_PATH_FETCH_BY_C_DU,
							finderArgs, user);
					}
				}
			}
			catch (Exception e) {
				finderCache.removeResult(FINDER_PATH_FETCH_BY_C_DU, finderArgs);

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
	 */
	@Override
	public User removeByC_DU(long companyId, boolean defaultUser)
		throws NoSuchUserException {
		User user = findByC_DU(companyId, defaultUser);

		return remove(user);
	}

	/**
	 * Returns the number of users where companyId = &#63; and defaultUser = &#63;.
	 *
	 * @param companyId the company ID
	 * @param defaultUser the default user
	 * @return the number of matching users
	 */
	@Override
	public int countByC_DU(long companyId, boolean defaultUser) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_C_DU;

		Object[] finderArgs = new Object[] { companyId, defaultUser };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

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
	 * Returns the user where companyId = &#63; and screenName = &#63; or throws a {@link NoSuchUserException} if it could not be found.
	 *
	 * @param companyId the company ID
	 * @param screenName the screen name
	 * @return the matching user
	 * @throws NoSuchUserException if a matching user could not be found
	 */
	@Override
	public User findByC_SN(long companyId, String screenName)
		throws NoSuchUserException {
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
	 */
	@Override
	public User fetchByC_SN(long companyId, String screenName) {
		return fetchByC_SN(companyId, screenName, true);
	}

	/**
	 * Returns the user where companyId = &#63; and screenName = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param companyId the company ID
	 * @param screenName the screen name
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the matching user, or <code>null</code> if a matching user could not be found
	 */
	@Override
	public User fetchByC_SN(long companyId, String screenName,
		boolean retrieveFromCache) {
		Object[] finderArgs = new Object[] { companyId, screenName };

		Object result = null;

		if (retrieveFromCache) {
			result = finderCache.getResult(FINDER_PATH_FETCH_BY_C_SN,
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
					finderCache.putResult(FINDER_PATH_FETCH_BY_C_SN,
						finderArgs, list);
				}
				else {
					User user = list.get(0);

					result = user;

					cacheResult(user);

					if ((user.getCompanyId() != companyId) ||
							(user.getScreenName() == null) ||
							!user.getScreenName().equals(screenName)) {
						finderCache.putResult(FINDER_PATH_FETCH_BY_C_SN,
							finderArgs, user);
					}
				}
			}
			catch (Exception e) {
				finderCache.removeResult(FINDER_PATH_FETCH_BY_C_SN, finderArgs);

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
	 */
	@Override
	public User removeByC_SN(long companyId, String screenName)
		throws NoSuchUserException {
		User user = findByC_SN(companyId, screenName);

		return remove(user);
	}

	/**
	 * Returns the number of users where companyId = &#63; and screenName = &#63;.
	 *
	 * @param companyId the company ID
	 * @param screenName the screen name
	 * @return the number of matching users
	 */
	@Override
	public int countByC_SN(long companyId, String screenName) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_C_SN;

		Object[] finderArgs = new Object[] { companyId, screenName };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

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
	 * Returns the user where companyId = &#63; and emailAddress = &#63; or throws a {@link NoSuchUserException} if it could not be found.
	 *
	 * @param companyId the company ID
	 * @param emailAddress the email address
	 * @return the matching user
	 * @throws NoSuchUserException if a matching user could not be found
	 */
	@Override
	public User findByC_EA(long companyId, String emailAddress)
		throws NoSuchUserException {
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
	 */
	@Override
	public User fetchByC_EA(long companyId, String emailAddress) {
		return fetchByC_EA(companyId, emailAddress, true);
	}

	/**
	 * Returns the user where companyId = &#63; and emailAddress = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param companyId the company ID
	 * @param emailAddress the email address
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the matching user, or <code>null</code> if a matching user could not be found
	 */
	@Override
	public User fetchByC_EA(long companyId, String emailAddress,
		boolean retrieveFromCache) {
		Object[] finderArgs = new Object[] { companyId, emailAddress };

		Object result = null;

		if (retrieveFromCache) {
			result = finderCache.getResult(FINDER_PATH_FETCH_BY_C_EA,
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
					finderCache.putResult(FINDER_PATH_FETCH_BY_C_EA,
						finderArgs, list);
				}
				else {
					User user = list.get(0);

					result = user;

					cacheResult(user);

					if ((user.getCompanyId() != companyId) ||
							(user.getEmailAddress() == null) ||
							!user.getEmailAddress().equals(emailAddress)) {
						finderCache.putResult(FINDER_PATH_FETCH_BY_C_EA,
							finderArgs, user);
					}
				}
			}
			catch (Exception e) {
				finderCache.removeResult(FINDER_PATH_FETCH_BY_C_EA, finderArgs);

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
	 */
	@Override
	public User removeByC_EA(long companyId, String emailAddress)
		throws NoSuchUserException {
		User user = findByC_EA(companyId, emailAddress);

		return remove(user);
	}

	/**
	 * Returns the number of users where companyId = &#63; and emailAddress = &#63;.
	 *
	 * @param companyId the company ID
	 * @param emailAddress the email address
	 * @return the number of matching users
	 */
	@Override
	public int countByC_EA(long companyId, String emailAddress) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_C_EA;

		Object[] finderArgs = new Object[] { companyId, emailAddress };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

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
	 * Returns the user where companyId = &#63; and facebookId = &#63; or throws a {@link NoSuchUserException} if it could not be found.
	 *
	 * @param companyId the company ID
	 * @param facebookId the facebook ID
	 * @return the matching user
	 * @throws NoSuchUserException if a matching user could not be found
	 */
	@Override
	public User findByC_FID(long companyId, long facebookId)
		throws NoSuchUserException {
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
	 */
	@Override
	public User fetchByC_FID(long companyId, long facebookId) {
		return fetchByC_FID(companyId, facebookId, true);
	}

	/**
	 * Returns the user where companyId = &#63; and facebookId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param companyId the company ID
	 * @param facebookId the facebook ID
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the matching user, or <code>null</code> if a matching user could not be found
	 */
	@Override
	public User fetchByC_FID(long companyId, long facebookId,
		boolean retrieveFromCache) {
		Object[] finderArgs = new Object[] { companyId, facebookId };

		Object result = null;

		if (retrieveFromCache) {
			result = finderCache.getResult(FINDER_PATH_FETCH_BY_C_FID,
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
					finderCache.putResult(FINDER_PATH_FETCH_BY_C_FID,
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
						finderCache.putResult(FINDER_PATH_FETCH_BY_C_FID,
							finderArgs, user);
					}
				}
			}
			catch (Exception e) {
				finderCache.removeResult(FINDER_PATH_FETCH_BY_C_FID, finderArgs);

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
	 */
	@Override
	public User removeByC_FID(long companyId, long facebookId)
		throws NoSuchUserException {
		User user = findByC_FID(companyId, facebookId);

		return remove(user);
	}

	/**
	 * Returns the number of users where companyId = &#63; and facebookId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param facebookId the facebook ID
	 * @return the number of matching users
	 */
	@Override
	public int countByC_FID(long companyId, long facebookId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_C_FID;

		Object[] finderArgs = new Object[] { companyId, facebookId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

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
	 * Returns the user where companyId = &#63; and openId = &#63; or throws a {@link NoSuchUserException} if it could not be found.
	 *
	 * @param companyId the company ID
	 * @param openId the open ID
	 * @return the matching user
	 * @throws NoSuchUserException if a matching user could not be found
	 */
	@Override
	public User findByC_O(long companyId, String openId)
		throws NoSuchUserException {
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
	 */
	@Override
	public User fetchByC_O(long companyId, String openId) {
		return fetchByC_O(companyId, openId, true);
	}

	/**
	 * Returns the user where companyId = &#63; and openId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param companyId the company ID
	 * @param openId the open ID
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the matching user, or <code>null</code> if a matching user could not be found
	 */
	@Override
	public User fetchByC_O(long companyId, String openId,
		boolean retrieveFromCache) {
		Object[] finderArgs = new Object[] { companyId, openId };

		Object result = null;

		if (retrieveFromCache) {
			result = finderCache.getResult(FINDER_PATH_FETCH_BY_C_O,
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
					finderCache.putResult(FINDER_PATH_FETCH_BY_C_O, finderArgs,
						list);
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
						finderCache.putResult(FINDER_PATH_FETCH_BY_C_O,
							finderArgs, user);
					}
				}
			}
			catch (Exception e) {
				finderCache.removeResult(FINDER_PATH_FETCH_BY_C_O, finderArgs);

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
	 */
	@Override
	public User removeByC_O(long companyId, String openId)
		throws NoSuchUserException {
		User user = findByC_O(companyId, openId);

		return remove(user);
	}

	/**
	 * Returns the number of users where companyId = &#63; and openId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param openId the open ID
	 * @return the number of matching users
	 */
	@Override
	public int countByC_O(long companyId, String openId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_C_O;

		Object[] finderArgs = new Object[] { companyId, openId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

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
	 */
	@Override
	public List<User> findByC_S(long companyId, int status) {
		return findByC_S(companyId, status, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the users where companyId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link UserModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param status the status
	 * @param start the lower bound of the range of users
	 * @param end the upper bound of the range of users (not inclusive)
	 * @return the range of matching users
	 */
	@Override
	public List<User> findByC_S(long companyId, int status, int start, int end) {
		return findByC_S(companyId, status, start, end, null);
	}

	/**
	 * Returns an ordered range of all the users where companyId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link UserModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param status the status
	 * @param start the lower bound of the range of users
	 * @param end the upper bound of the range of users (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching users
	 */
	@Override
	public List<User> findByC_S(long companyId, int status, int start, int end,
		OrderByComparator<User> orderByComparator) {
		return findByC_S(companyId, status, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the users where companyId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link UserModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param status the status
	 * @param start the lower bound of the range of users
	 * @param end the upper bound of the range of users (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching users
	 */
	@Override
	public List<User> findByC_S(long companyId, int status, int start, int end,
		OrderByComparator<User> orderByComparator, boolean retrieveFromCache) {
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

		List<User> list = null;

		if (retrieveFromCache) {
			list = (List<User>)finderCache.getResult(finderPath, finderArgs,
					this);

			if ((list != null) && !list.isEmpty()) {
				for (User user : list) {
					if ((companyId != user.getCompanyId()) ||
							(status != user.getStatus())) {
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

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<User>)QueryUtil.list(q, getDialect(), start,
							end);
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
	 * Returns the first user in the ordered set where companyId = &#63; and status = &#63;.
	 *
	 * @param companyId the company ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching user
	 * @throws NoSuchUserException if a matching user could not be found
	 */
	@Override
	public User findByC_S_First(long companyId, int status,
		OrderByComparator<User> orderByComparator) throws NoSuchUserException {
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
	 */
	@Override
	public User fetchByC_S_First(long companyId, int status,
		OrderByComparator<User> orderByComparator) {
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
	 * @throws NoSuchUserException if a matching user could not be found
	 */
	@Override
	public User findByC_S_Last(long companyId, int status,
		OrderByComparator<User> orderByComparator) throws NoSuchUserException {
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
	 */
	@Override
	public User fetchByC_S_Last(long companyId, int status,
		OrderByComparator<User> orderByComparator) {
		int count = countByC_S(companyId, status);

		if (count == 0) {
			return null;
		}

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
	 * @throws NoSuchUserException if a user with the primary key could not be found
	 */
	@Override
	public User[] findByC_S_PrevAndNext(long userId, long companyId,
		int status, OrderByComparator<User> orderByComparator)
		throws NoSuchUserException {
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
		long companyId, int status, OrderByComparator<User> orderByComparator,
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
	 */
	@Override
	public void removeByC_S(long companyId, int status) {
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
	 */
	@Override
	public int countByC_S(long companyId, int status) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_C_S;

		Object[] finderArgs = new Object[] { companyId, status };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

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
	 */
	@Override
	public List<User> findByC_CD_MD(long companyId, Date createDate,
		Date modifiedDate) {
		return findByC_CD_MD(companyId, createDate, modifiedDate,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the users where companyId = &#63; and createDate = &#63; and modifiedDate = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link UserModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param createDate the create date
	 * @param modifiedDate the modified date
	 * @param start the lower bound of the range of users
	 * @param end the upper bound of the range of users (not inclusive)
	 * @return the range of matching users
	 */
	@Override
	public List<User> findByC_CD_MD(long companyId, Date createDate,
		Date modifiedDate, int start, int end) {
		return findByC_CD_MD(companyId, createDate, modifiedDate, start, end,
			null);
	}

	/**
	 * Returns an ordered range of all the users where companyId = &#63; and createDate = &#63; and modifiedDate = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link UserModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param createDate the create date
	 * @param modifiedDate the modified date
	 * @param start the lower bound of the range of users
	 * @param end the upper bound of the range of users (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching users
	 */
	@Override
	public List<User> findByC_CD_MD(long companyId, Date createDate,
		Date modifiedDate, int start, int end,
		OrderByComparator<User> orderByComparator) {
		return findByC_CD_MD(companyId, createDate, modifiedDate, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the users where companyId = &#63; and createDate = &#63; and modifiedDate = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link UserModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param createDate the create date
	 * @param modifiedDate the modified date
	 * @param start the lower bound of the range of users
	 * @param end the upper bound of the range of users (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching users
	 */
	@Override
	public List<User> findByC_CD_MD(long companyId, Date createDate,
		Date modifiedDate, int start, int end,
		OrderByComparator<User> orderByComparator, boolean retrieveFromCache) {
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

		List<User> list = null;

		if (retrieveFromCache) {
			list = (List<User>)finderCache.getResult(finderPath, finderArgs,
					this);

			if ((list != null) && !list.isEmpty()) {
				for (User user : list) {
					if ((companyId != user.getCompanyId()) ||
							!Validator.equals(createDate, user.getCreateDate()) ||
							!Validator.equals(modifiedDate,
								user.getModifiedDate())) {
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
					qPos.add(new Timestamp(createDate.getTime()));
				}

				if (bindModifiedDate) {
					qPos.add(new Timestamp(modifiedDate.getTime()));
				}

				if (!pagination) {
					list = (List<User>)QueryUtil.list(q, getDialect(), start,
							end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<User>)QueryUtil.list(q, getDialect(), start,
							end);
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
	 * Returns the first user in the ordered set where companyId = &#63; and createDate = &#63; and modifiedDate = &#63;.
	 *
	 * @param companyId the company ID
	 * @param createDate the create date
	 * @param modifiedDate the modified date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching user
	 * @throws NoSuchUserException if a matching user could not be found
	 */
	@Override
	public User findByC_CD_MD_First(long companyId, Date createDate,
		Date modifiedDate, OrderByComparator<User> orderByComparator)
		throws NoSuchUserException {
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
	 */
	@Override
	public User fetchByC_CD_MD_First(long companyId, Date createDate,
		Date modifiedDate, OrderByComparator<User> orderByComparator) {
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
	 * @throws NoSuchUserException if a matching user could not be found
	 */
	@Override
	public User findByC_CD_MD_Last(long companyId, Date createDate,
		Date modifiedDate, OrderByComparator<User> orderByComparator)
		throws NoSuchUserException {
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
	 */
	@Override
	public User fetchByC_CD_MD_Last(long companyId, Date createDate,
		Date modifiedDate, OrderByComparator<User> orderByComparator) {
		int count = countByC_CD_MD(companyId, createDate, modifiedDate);

		if (count == 0) {
			return null;
		}

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
	 * @throws NoSuchUserException if a user with the primary key could not be found
	 */
	@Override
	public User[] findByC_CD_MD_PrevAndNext(long userId, long companyId,
		Date createDate, Date modifiedDate,
		OrderByComparator<User> orderByComparator) throws NoSuchUserException {
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
		OrderByComparator<User> orderByComparator, boolean previous) {
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
			qPos.add(new Timestamp(createDate.getTime()));
		}

		if (bindModifiedDate) {
			qPos.add(new Timestamp(modifiedDate.getTime()));
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
	 */
	@Override
	public void removeByC_CD_MD(long companyId, Date createDate,
		Date modifiedDate) {
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
	 */
	@Override
	public int countByC_CD_MD(long companyId, Date createDate, Date modifiedDate) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_C_CD_MD;

		Object[] finderArgs = new Object[] { companyId, createDate, modifiedDate };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

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
					qPos.add(new Timestamp(createDate.getTime()));
				}

				if (bindModifiedDate) {
					qPos.add(new Timestamp(modifiedDate.getTime()));
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

	private static final String _FINDER_COLUMN_C_CD_MD_COMPANYID_2 = "user.companyId = ? AND ";
	private static final String _FINDER_COLUMN_C_CD_MD_CREATEDATE_1 = "user.createDate IS NULL AND ";
	private static final String _FINDER_COLUMN_C_CD_MD_CREATEDATE_2 = "user.createDate = ? AND ";
	private static final String _FINDER_COLUMN_C_CD_MD_MODIFIEDDATE_1 = "user.modifiedDate IS NULL";
	private static final String _FINDER_COLUMN_C_CD_MD_MODIFIEDDATE_2 = "user.modifiedDate = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_C_DU_S = new FinderPath(UserModelImpl.ENTITY_CACHE_ENABLED,
			UserModelImpl.FINDER_CACHE_ENABLED, UserImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByC_DU_S",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				Integer.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_DU_S =
		new FinderPath(UserModelImpl.ENTITY_CACHE_ENABLED,
			UserModelImpl.FINDER_CACHE_ENABLED, UserImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByC_DU_S",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				Integer.class.getName()
			},
			UserModelImpl.COMPANYID_COLUMN_BITMASK |
			UserModelImpl.DEFAULTUSER_COLUMN_BITMASK |
			UserModelImpl.STATUS_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_C_DU_S = new FinderPath(UserModelImpl.ENTITY_CACHE_ENABLED,
			UserModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_DU_S",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				Integer.class.getName()
			});

	/**
	 * Returns all the users where companyId = &#63; and defaultUser = &#63; and status = &#63;.
	 *
	 * @param companyId the company ID
	 * @param defaultUser the default user
	 * @param status the status
	 * @return the matching users
	 */
	@Override
	public List<User> findByC_DU_S(long companyId, boolean defaultUser,
		int status) {
		return findByC_DU_S(companyId, defaultUser, status, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the users where companyId = &#63; and defaultUser = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link UserModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param defaultUser the default user
	 * @param status the status
	 * @param start the lower bound of the range of users
	 * @param end the upper bound of the range of users (not inclusive)
	 * @return the range of matching users
	 */
	@Override
	public List<User> findByC_DU_S(long companyId, boolean defaultUser,
		int status, int start, int end) {
		return findByC_DU_S(companyId, defaultUser, status, start, end, null);
	}

	/**
	 * Returns an ordered range of all the users where companyId = &#63; and defaultUser = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link UserModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param defaultUser the default user
	 * @param status the status
	 * @param start the lower bound of the range of users
	 * @param end the upper bound of the range of users (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching users
	 */
	@Override
	public List<User> findByC_DU_S(long companyId, boolean defaultUser,
		int status, int start, int end,
		OrderByComparator<User> orderByComparator) {
		return findByC_DU_S(companyId, defaultUser, status, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the users where companyId = &#63; and defaultUser = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link UserModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param defaultUser the default user
	 * @param status the status
	 * @param start the lower bound of the range of users
	 * @param end the upper bound of the range of users (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching users
	 */
	@Override
	public List<User> findByC_DU_S(long companyId, boolean defaultUser,
		int status, int start, int end,
		OrderByComparator<User> orderByComparator, boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_DU_S;
			finderArgs = new Object[] { companyId, defaultUser, status };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_C_DU_S;
			finderArgs = new Object[] {
					companyId, defaultUser, status,
					
					start, end, orderByComparator
				};
		}

		List<User> list = null;

		if (retrieveFromCache) {
			list = (List<User>)finderCache.getResult(finderPath, finderArgs,
					this);

			if ((list != null) && !list.isEmpty()) {
				for (User user : list) {
					if ((companyId != user.getCompanyId()) ||
							(defaultUser != user.getDefaultUser()) ||
							(status != user.getStatus())) {
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

			query.append(_SQL_SELECT_USER_WHERE);

			query.append(_FINDER_COLUMN_C_DU_S_COMPANYID_2);

			query.append(_FINDER_COLUMN_C_DU_S_DEFAULTUSER_2);

			query.append(_FINDER_COLUMN_C_DU_S_STATUS_2);

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

				qPos.add(defaultUser);

				qPos.add(status);

				if (!pagination) {
					list = (List<User>)QueryUtil.list(q, getDialect(), start,
							end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<User>)QueryUtil.list(q, getDialect(), start,
							end);
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
	 * Returns the first user in the ordered set where companyId = &#63; and defaultUser = &#63; and status = &#63;.
	 *
	 * @param companyId the company ID
	 * @param defaultUser the default user
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching user
	 * @throws NoSuchUserException if a matching user could not be found
	 */
	@Override
	public User findByC_DU_S_First(long companyId, boolean defaultUser,
		int status, OrderByComparator<User> orderByComparator)
		throws NoSuchUserException {
		User user = fetchByC_DU_S_First(companyId, defaultUser, status,
				orderByComparator);

		if (user != null) {
			return user;
		}

		StringBundler msg = new StringBundler(8);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("companyId=");
		msg.append(companyId);

		msg.append(", defaultUser=");
		msg.append(defaultUser);

		msg.append(", status=");
		msg.append(status);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchUserException(msg.toString());
	}

	/**
	 * Returns the first user in the ordered set where companyId = &#63; and defaultUser = &#63; and status = &#63;.
	 *
	 * @param companyId the company ID
	 * @param defaultUser the default user
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching user, or <code>null</code> if a matching user could not be found
	 */
	@Override
	public User fetchByC_DU_S_First(long companyId, boolean defaultUser,
		int status, OrderByComparator<User> orderByComparator) {
		List<User> list = findByC_DU_S(companyId, defaultUser, status, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last user in the ordered set where companyId = &#63; and defaultUser = &#63; and status = &#63;.
	 *
	 * @param companyId the company ID
	 * @param defaultUser the default user
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching user
	 * @throws NoSuchUserException if a matching user could not be found
	 */
	@Override
	public User findByC_DU_S_Last(long companyId, boolean defaultUser,
		int status, OrderByComparator<User> orderByComparator)
		throws NoSuchUserException {
		User user = fetchByC_DU_S_Last(companyId, defaultUser, status,
				orderByComparator);

		if (user != null) {
			return user;
		}

		StringBundler msg = new StringBundler(8);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("companyId=");
		msg.append(companyId);

		msg.append(", defaultUser=");
		msg.append(defaultUser);

		msg.append(", status=");
		msg.append(status);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchUserException(msg.toString());
	}

	/**
	 * Returns the last user in the ordered set where companyId = &#63; and defaultUser = &#63; and status = &#63;.
	 *
	 * @param companyId the company ID
	 * @param defaultUser the default user
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching user, or <code>null</code> if a matching user could not be found
	 */
	@Override
	public User fetchByC_DU_S_Last(long companyId, boolean defaultUser,
		int status, OrderByComparator<User> orderByComparator) {
		int count = countByC_DU_S(companyId, defaultUser, status);

		if (count == 0) {
			return null;
		}

		List<User> list = findByC_DU_S(companyId, defaultUser, status,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the users before and after the current user in the ordered set where companyId = &#63; and defaultUser = &#63; and status = &#63;.
	 *
	 * @param userId the primary key of the current user
	 * @param companyId the company ID
	 * @param defaultUser the default user
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next user
	 * @throws NoSuchUserException if a user with the primary key could not be found
	 */
	@Override
	public User[] findByC_DU_S_PrevAndNext(long userId, long companyId,
		boolean defaultUser, int status,
		OrderByComparator<User> orderByComparator) throws NoSuchUserException {
		User user = findByPrimaryKey(userId);

		Session session = null;

		try {
			session = openSession();

			User[] array = new UserImpl[3];

			array[0] = getByC_DU_S_PrevAndNext(session, user, companyId,
					defaultUser, status, orderByComparator, true);

			array[1] = user;

			array[2] = getByC_DU_S_PrevAndNext(session, user, companyId,
					defaultUser, status, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected User getByC_DU_S_PrevAndNext(Session session, User user,
		long companyId, boolean defaultUser, int status,
		OrderByComparator<User> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_USER_WHERE);

		query.append(_FINDER_COLUMN_C_DU_S_COMPANYID_2);

		query.append(_FINDER_COLUMN_C_DU_S_DEFAULTUSER_2);

		query.append(_FINDER_COLUMN_C_DU_S_STATUS_2);

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

		qPos.add(defaultUser);

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
	 * Removes all the users where companyId = &#63; and defaultUser = &#63; and status = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param defaultUser the default user
	 * @param status the status
	 */
	@Override
	public void removeByC_DU_S(long companyId, boolean defaultUser, int status) {
		for (User user : findByC_DU_S(companyId, defaultUser, status,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(user);
		}
	}

	/**
	 * Returns the number of users where companyId = &#63; and defaultUser = &#63; and status = &#63;.
	 *
	 * @param companyId the company ID
	 * @param defaultUser the default user
	 * @param status the status
	 * @return the number of matching users
	 */
	@Override
	public int countByC_DU_S(long companyId, boolean defaultUser, int status) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_C_DU_S;

		Object[] finderArgs = new Object[] { companyId, defaultUser, status };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_COUNT_USER_WHERE);

			query.append(_FINDER_COLUMN_C_DU_S_COMPANYID_2);

			query.append(_FINDER_COLUMN_C_DU_S_DEFAULTUSER_2);

			query.append(_FINDER_COLUMN_C_DU_S_STATUS_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				qPos.add(defaultUser);

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

	private static final String _FINDER_COLUMN_C_DU_S_COMPANYID_2 = "user.companyId = ? AND ";
	private static final String _FINDER_COLUMN_C_DU_S_DEFAULTUSER_2 = "user.defaultUser = ? AND ";
	private static final String _FINDER_COLUMN_C_DU_S_STATUS_2 = "user.status = ?";

	public UserPersistenceImpl() {
		setModelClass(User.class);
	}

	/**
	 * Caches the user in the entity cache if it is enabled.
	 *
	 * @param user the user
	 */
	@Override
	public void cacheResult(User user) {
		entityCache.putResult(UserModelImpl.ENTITY_CACHE_ENABLED,
			UserImpl.class, user.getPrimaryKey(), user);

		finderCache.putResult(FINDER_PATH_FETCH_BY_CONTACTID,
			new Object[] { user.getContactId() }, user);

		finderCache.putResult(FINDER_PATH_FETCH_BY_PORTRAITID,
			new Object[] { user.getPortraitId() }, user);

		finderCache.putResult(FINDER_PATH_FETCH_BY_C_U,
			new Object[] { user.getCompanyId(), user.getUserId() }, user);

		finderCache.putResult(FINDER_PATH_FETCH_BY_C_DU,
			new Object[] { user.getCompanyId(), user.getDefaultUser() }, user);

		finderCache.putResult(FINDER_PATH_FETCH_BY_C_SN,
			new Object[] { user.getCompanyId(), user.getScreenName() }, user);

		finderCache.putResult(FINDER_PATH_FETCH_BY_C_EA,
			new Object[] { user.getCompanyId(), user.getEmailAddress() }, user);

		finderCache.putResult(FINDER_PATH_FETCH_BY_C_FID,
			new Object[] { user.getCompanyId(), user.getFacebookId() }, user);

		finderCache.putResult(FINDER_PATH_FETCH_BY_C_O,
			new Object[] { user.getCompanyId(), user.getOpenId() }, user);

		user.resetOriginalValues();
	}

	/**
	 * Caches the users in the entity cache if it is enabled.
	 *
	 * @param users the users
	 */
	@Override
	public void cacheResult(List<User> users) {
		for (User user : users) {
			if (entityCache.getResult(UserModelImpl.ENTITY_CACHE_ENABLED,
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
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(UserImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the user.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(User user) {
		entityCache.removeResult(UserModelImpl.ENTITY_CACHE_ENABLED,
			UserImpl.class, user.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache((UserModelImpl)user);
	}

	@Override
	public void clearCache(List<User> users) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (User user : users) {
			entityCache.removeResult(UserModelImpl.ENTITY_CACHE_ENABLED,
				UserImpl.class, user.getPrimaryKey());

			clearUniqueFindersCache((UserModelImpl)user);
		}
	}

	protected void cacheUniqueFindersCache(UserModelImpl userModelImpl,
		boolean isNew) {
		if (isNew) {
			Object[] args = new Object[] { userModelImpl.getContactId() };

			finderCache.putResult(FINDER_PATH_COUNT_BY_CONTACTID, args,
				Long.valueOf(1));
			finderCache.putResult(FINDER_PATH_FETCH_BY_CONTACTID, args,
				userModelImpl);

			args = new Object[] { userModelImpl.getPortraitId() };

			finderCache.putResult(FINDER_PATH_COUNT_BY_PORTRAITID, args,
				Long.valueOf(1));
			finderCache.putResult(FINDER_PATH_FETCH_BY_PORTRAITID, args,
				userModelImpl);

			args = new Object[] {
					userModelImpl.getCompanyId(), userModelImpl.getUserId()
				};

			finderCache.putResult(FINDER_PATH_COUNT_BY_C_U, args,
				Long.valueOf(1));
			finderCache.putResult(FINDER_PATH_FETCH_BY_C_U, args, userModelImpl);

			args = new Object[] {
					userModelImpl.getCompanyId(), userModelImpl.getDefaultUser()
				};

			finderCache.putResult(FINDER_PATH_COUNT_BY_C_DU, args,
				Long.valueOf(1));
			finderCache.putResult(FINDER_PATH_FETCH_BY_C_DU, args, userModelImpl);

			args = new Object[] {
					userModelImpl.getCompanyId(), userModelImpl.getScreenName()
				};

			finderCache.putResult(FINDER_PATH_COUNT_BY_C_SN, args,
				Long.valueOf(1));
			finderCache.putResult(FINDER_PATH_FETCH_BY_C_SN, args, userModelImpl);

			args = new Object[] {
					userModelImpl.getCompanyId(),
					userModelImpl.getEmailAddress()
				};

			finderCache.putResult(FINDER_PATH_COUNT_BY_C_EA, args,
				Long.valueOf(1));
			finderCache.putResult(FINDER_PATH_FETCH_BY_C_EA, args, userModelImpl);

			args = new Object[] {
					userModelImpl.getCompanyId(), userModelImpl.getFacebookId()
				};

			finderCache.putResult(FINDER_PATH_COUNT_BY_C_FID, args,
				Long.valueOf(1));
			finderCache.putResult(FINDER_PATH_FETCH_BY_C_FID, args,
				userModelImpl);

			args = new Object[] {
					userModelImpl.getCompanyId(), userModelImpl.getOpenId()
				};

			finderCache.putResult(FINDER_PATH_COUNT_BY_C_O, args,
				Long.valueOf(1));
			finderCache.putResult(FINDER_PATH_FETCH_BY_C_O, args, userModelImpl);
		}
		else {
			if ((userModelImpl.getColumnBitmask() &
					FINDER_PATH_FETCH_BY_CONTACTID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] { userModelImpl.getContactId() };

				finderCache.putResult(FINDER_PATH_COUNT_BY_CONTACTID, args,
					Long.valueOf(1));
				finderCache.putResult(FINDER_PATH_FETCH_BY_CONTACTID, args,
					userModelImpl);
			}

			if ((userModelImpl.getColumnBitmask() &
					FINDER_PATH_FETCH_BY_PORTRAITID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] { userModelImpl.getPortraitId() };

				finderCache.putResult(FINDER_PATH_COUNT_BY_PORTRAITID, args,
					Long.valueOf(1));
				finderCache.putResult(FINDER_PATH_FETCH_BY_PORTRAITID, args,
					userModelImpl);
			}

			if ((userModelImpl.getColumnBitmask() &
					FINDER_PATH_FETCH_BY_C_U.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						userModelImpl.getCompanyId(), userModelImpl.getUserId()
					};

				finderCache.putResult(FINDER_PATH_COUNT_BY_C_U, args,
					Long.valueOf(1));
				finderCache.putResult(FINDER_PATH_FETCH_BY_C_U, args,
					userModelImpl);
			}

			if ((userModelImpl.getColumnBitmask() &
					FINDER_PATH_FETCH_BY_C_DU.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						userModelImpl.getCompanyId(),
						userModelImpl.getDefaultUser()
					};

				finderCache.putResult(FINDER_PATH_COUNT_BY_C_DU, args,
					Long.valueOf(1));
				finderCache.putResult(FINDER_PATH_FETCH_BY_C_DU, args,
					userModelImpl);
			}

			if ((userModelImpl.getColumnBitmask() &
					FINDER_PATH_FETCH_BY_C_SN.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						userModelImpl.getCompanyId(),
						userModelImpl.getScreenName()
					};

				finderCache.putResult(FINDER_PATH_COUNT_BY_C_SN, args,
					Long.valueOf(1));
				finderCache.putResult(FINDER_PATH_FETCH_BY_C_SN, args,
					userModelImpl);
			}

			if ((userModelImpl.getColumnBitmask() &
					FINDER_PATH_FETCH_BY_C_EA.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						userModelImpl.getCompanyId(),
						userModelImpl.getEmailAddress()
					};

				finderCache.putResult(FINDER_PATH_COUNT_BY_C_EA, args,
					Long.valueOf(1));
				finderCache.putResult(FINDER_PATH_FETCH_BY_C_EA, args,
					userModelImpl);
			}

			if ((userModelImpl.getColumnBitmask() &
					FINDER_PATH_FETCH_BY_C_FID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						userModelImpl.getCompanyId(),
						userModelImpl.getFacebookId()
					};

				finderCache.putResult(FINDER_PATH_COUNT_BY_C_FID, args,
					Long.valueOf(1));
				finderCache.putResult(FINDER_PATH_FETCH_BY_C_FID, args,
					userModelImpl);
			}

			if ((userModelImpl.getColumnBitmask() &
					FINDER_PATH_FETCH_BY_C_O.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						userModelImpl.getCompanyId(), userModelImpl.getOpenId()
					};

				finderCache.putResult(FINDER_PATH_COUNT_BY_C_O, args,
					Long.valueOf(1));
				finderCache.putResult(FINDER_PATH_FETCH_BY_C_O, args,
					userModelImpl);
			}
		}
	}

	protected void clearUniqueFindersCache(UserModelImpl userModelImpl) {
		Object[] args = new Object[] { userModelImpl.getContactId() };

		finderCache.removeResult(FINDER_PATH_COUNT_BY_CONTACTID, args);
		finderCache.removeResult(FINDER_PATH_FETCH_BY_CONTACTID, args);

		if ((userModelImpl.getColumnBitmask() &
				FINDER_PATH_FETCH_BY_CONTACTID.getColumnBitmask()) != 0) {
			args = new Object[] { userModelImpl.getOriginalContactId() };

			finderCache.removeResult(FINDER_PATH_COUNT_BY_CONTACTID, args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_CONTACTID, args);
		}

		args = new Object[] { userModelImpl.getPortraitId() };

		finderCache.removeResult(FINDER_PATH_COUNT_BY_PORTRAITID, args);
		finderCache.removeResult(FINDER_PATH_FETCH_BY_PORTRAITID, args);

		if ((userModelImpl.getColumnBitmask() &
				FINDER_PATH_FETCH_BY_PORTRAITID.getColumnBitmask()) != 0) {
			args = new Object[] { userModelImpl.getOriginalPortraitId() };

			finderCache.removeResult(FINDER_PATH_COUNT_BY_PORTRAITID, args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_PORTRAITID, args);
		}

		args = new Object[] {
				userModelImpl.getCompanyId(), userModelImpl.getUserId()
			};

		finderCache.removeResult(FINDER_PATH_COUNT_BY_C_U, args);
		finderCache.removeResult(FINDER_PATH_FETCH_BY_C_U, args);

		if ((userModelImpl.getColumnBitmask() &
				FINDER_PATH_FETCH_BY_C_U.getColumnBitmask()) != 0) {
			args = new Object[] {
					userModelImpl.getOriginalCompanyId(),
					userModelImpl.getOriginalUserId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_C_U, args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_C_U, args);
		}

		args = new Object[] {
				userModelImpl.getCompanyId(), userModelImpl.getDefaultUser()
			};

		finderCache.removeResult(FINDER_PATH_COUNT_BY_C_DU, args);
		finderCache.removeResult(FINDER_PATH_FETCH_BY_C_DU, args);

		if ((userModelImpl.getColumnBitmask() &
				FINDER_PATH_FETCH_BY_C_DU.getColumnBitmask()) != 0) {
			args = new Object[] {
					userModelImpl.getOriginalCompanyId(),
					userModelImpl.getOriginalDefaultUser()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_C_DU, args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_C_DU, args);
		}

		args = new Object[] {
				userModelImpl.getCompanyId(), userModelImpl.getScreenName()
			};

		finderCache.removeResult(FINDER_PATH_COUNT_BY_C_SN, args);
		finderCache.removeResult(FINDER_PATH_FETCH_BY_C_SN, args);

		if ((userModelImpl.getColumnBitmask() &
				FINDER_PATH_FETCH_BY_C_SN.getColumnBitmask()) != 0) {
			args = new Object[] {
					userModelImpl.getOriginalCompanyId(),
					userModelImpl.getOriginalScreenName()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_C_SN, args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_C_SN, args);
		}

		args = new Object[] {
				userModelImpl.getCompanyId(), userModelImpl.getEmailAddress()
			};

		finderCache.removeResult(FINDER_PATH_COUNT_BY_C_EA, args);
		finderCache.removeResult(FINDER_PATH_FETCH_BY_C_EA, args);

		if ((userModelImpl.getColumnBitmask() &
				FINDER_PATH_FETCH_BY_C_EA.getColumnBitmask()) != 0) {
			args = new Object[] {
					userModelImpl.getOriginalCompanyId(),
					userModelImpl.getOriginalEmailAddress()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_C_EA, args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_C_EA, args);
		}

		args = new Object[] {
				userModelImpl.getCompanyId(), userModelImpl.getFacebookId()
			};

		finderCache.removeResult(FINDER_PATH_COUNT_BY_C_FID, args);
		finderCache.removeResult(FINDER_PATH_FETCH_BY_C_FID, args);

		if ((userModelImpl.getColumnBitmask() &
				FINDER_PATH_FETCH_BY_C_FID.getColumnBitmask()) != 0) {
			args = new Object[] {
					userModelImpl.getOriginalCompanyId(),
					userModelImpl.getOriginalFacebookId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_C_FID, args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_C_FID, args);
		}

		args = new Object[] {
				userModelImpl.getCompanyId(), userModelImpl.getOpenId()
			};

		finderCache.removeResult(FINDER_PATH_COUNT_BY_C_O, args);
		finderCache.removeResult(FINDER_PATH_FETCH_BY_C_O, args);

		if ((userModelImpl.getColumnBitmask() &
				FINDER_PATH_FETCH_BY_C_O.getColumnBitmask()) != 0) {
			args = new Object[] {
					userModelImpl.getOriginalCompanyId(),
					userModelImpl.getOriginalOpenId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_C_O, args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_C_O, args);
		}
	}

	/**
	 * Creates a new user with the primary key. Does not add the user to the database.
	 *
	 * @param userId the primary key for the new user
	 * @return the new user
	 */
	@Override
	public User create(long userId) {
		User user = new UserImpl();

		user.setNew(true);
		user.setPrimaryKey(userId);

		String uuid = PortalUUIDUtil.generate();

		user.setUuid(uuid);

		user.setCompanyId(companyProvider.getCompanyId());

		return user;
	}

	/**
	 * Removes the user with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param userId the primary key of the user
	 * @return the user that was removed
	 * @throws NoSuchUserException if a user with the primary key could not be found
	 */
	@Override
	public User remove(long userId) throws NoSuchUserException {
		return remove((Serializable)userId);
	}

	/**
	 * Removes the user with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the user
	 * @return the user that was removed
	 * @throws NoSuchUserException if a user with the primary key could not be found
	 */
	@Override
	public User remove(Serializable primaryKey) throws NoSuchUserException {
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
	protected User removeImpl(User user) {
		user = toUnwrappedModel(user);

		userToGroupTableMapper.deleteLeftPrimaryKeyTableMappings(companyProvider.getCompanyId(),
			user.getPrimaryKey());

		userToOrganizationTableMapper.deleteLeftPrimaryKeyTableMappings(companyProvider.getCompanyId(),
			user.getPrimaryKey());

		userToRoleTableMapper.deleteLeftPrimaryKeyTableMappings(companyProvider.getCompanyId(),
			user.getPrimaryKey());

		userToTeamTableMapper.deleteLeftPrimaryKeyTableMappings(companyProvider.getCompanyId(),
			user.getPrimaryKey());

		userToUserGroupTableMapper.deleteLeftPrimaryKeyTableMappings(companyProvider.getCompanyId(),
			user.getPrimaryKey());

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
	public User updateImpl(User user) {
		user = toUnwrappedModel(user);

		boolean isNew = user.isNew();

		UserModelImpl userModelImpl = (UserModelImpl)user;

		if (Validator.isNull(user.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			user.setUuid(uuid);
		}

		ServiceContext serviceContext = ServiceContextThreadLocal.getServiceContext();

		Date now = new Date();

		if (isNew && (user.getCreateDate() == null)) {
			if (serviceContext == null) {
				user.setCreateDate(now);
			}
			else {
				user.setCreateDate(serviceContext.getCreateDate(now));
			}
		}

		if (!userModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				user.setModifiedDate(now);
			}
			else {
				user.setModifiedDate(serviceContext.getModifiedDate(now));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (user.isNew()) {
				session.save(user);

				user.setNew(false);
			}
			else {
				user = (User)session.merge(user);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (isNew || !UserModelImpl.COLUMN_BITMASK_ENABLED) {
			finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}

		else {
			if ((userModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] { userModelImpl.getOriginalUuid() };

				finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID,
					args);

				args = new Object[] { userModelImpl.getUuid() };

				finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID,
					args);
			}

			if ((userModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						userModelImpl.getOriginalUuid(),
						userModelImpl.getOriginalCompanyId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID_C, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C,
					args);

				args = new Object[] {
						userModelImpl.getUuid(), userModelImpl.getCompanyId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID_C, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C,
					args);
			}

			if ((userModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						userModelImpl.getOriginalCompanyId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_COMPANYID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID,
					args);

				args = new Object[] { userModelImpl.getCompanyId() };

				finderCache.removeResult(FINDER_PATH_COUNT_BY_COMPANYID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID,
					args);
			}

			if ((userModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_EMAILADDRESS.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						userModelImpl.getOriginalEmailAddress()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_EMAILADDRESS, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_EMAILADDRESS,
					args);

				args = new Object[] { userModelImpl.getEmailAddress() };

				finderCache.removeResult(FINDER_PATH_COUNT_BY_EMAILADDRESS, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_EMAILADDRESS,
					args);
			}

			if ((userModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_CD.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						userModelImpl.getOriginalCompanyId(),
						userModelImpl.getOriginalCreateDate()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_C_CD, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_CD,
					args);

				args = new Object[] {
						userModelImpl.getCompanyId(),
						userModelImpl.getCreateDate()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_C_CD, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_CD,
					args);
			}

			if ((userModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_MD.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						userModelImpl.getOriginalCompanyId(),
						userModelImpl.getOriginalModifiedDate()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_C_MD, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_MD,
					args);

				args = new Object[] {
						userModelImpl.getCompanyId(),
						userModelImpl.getModifiedDate()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_C_MD, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_MD,
					args);
			}

			if ((userModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_S.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						userModelImpl.getOriginalCompanyId(),
						userModelImpl.getOriginalStatus()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_C_S, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_S,
					args);

				args = new Object[] {
						userModelImpl.getCompanyId(), userModelImpl.getStatus()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_C_S, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_S,
					args);
			}

			if ((userModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_CD_MD.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						userModelImpl.getOriginalCompanyId(),
						userModelImpl.getOriginalCreateDate(),
						userModelImpl.getOriginalModifiedDate()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_C_CD_MD, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_CD_MD,
					args);

				args = new Object[] {
						userModelImpl.getCompanyId(),
						userModelImpl.getCreateDate(),
						userModelImpl.getModifiedDate()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_C_CD_MD, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_CD_MD,
					args);
			}

			if ((userModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_DU_S.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						userModelImpl.getOriginalCompanyId(),
						userModelImpl.getOriginalDefaultUser(),
						userModelImpl.getOriginalStatus()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_C_DU_S, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_DU_S,
					args);

				args = new Object[] {
						userModelImpl.getCompanyId(),
						userModelImpl.getDefaultUser(),
						userModelImpl.getStatus()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_C_DU_S, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_DU_S,
					args);
			}
		}

		entityCache.putResult(UserModelImpl.ENTITY_CACHE_ENABLED,
			UserImpl.class, user.getPrimaryKey(), user, false);

		clearUniqueFindersCache(userModelImpl);
		cacheUniqueFindersCache(userModelImpl, isNew);

		user.resetOriginalValues();

		return user;
	}

	protected User toUnwrappedModel(User user) {
		if (user instanceof UserImpl) {
			return user;
		}

		UserImpl userImpl = new UserImpl();

		userImpl.setNew(user.isNew());
		userImpl.setPrimaryKey(user.getPrimaryKey());

		userImpl.setMvccVersion(user.getMvccVersion());
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
	 * @throws NoSuchUserException if a user with the primary key could not be found
	 */
	@Override
	public User findByPrimaryKey(Serializable primaryKey)
		throws NoSuchUserException {
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
	 * Returns the user with the primary key or throws a {@link NoSuchUserException} if it could not be found.
	 *
	 * @param userId the primary key of the user
	 * @return the user
	 * @throws NoSuchUserException if a user with the primary key could not be found
	 */
	@Override
	public User findByPrimaryKey(long userId) throws NoSuchUserException {
		return findByPrimaryKey((Serializable)userId);
	}

	/**
	 * Returns the user with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the user
	 * @return the user, or <code>null</code> if a user with the primary key could not be found
	 */
	@Override
	public User fetchByPrimaryKey(Serializable primaryKey) {
		User user = (User)entityCache.getResult(UserModelImpl.ENTITY_CACHE_ENABLED,
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
					entityCache.putResult(UserModelImpl.ENTITY_CACHE_ENABLED,
						UserImpl.class, primaryKey, _nullUser);
				}
			}
			catch (Exception e) {
				entityCache.removeResult(UserModelImpl.ENTITY_CACHE_ENABLED,
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
	 */
	@Override
	public User fetchByPrimaryKey(long userId) {
		return fetchByPrimaryKey((Serializable)userId);
	}

	@Override
	public Map<Serializable, User> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {
		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, User> map = new HashMap<Serializable, User>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			User user = fetchByPrimaryKey(primaryKey);

			if (user != null) {
				map.put(primaryKey, user);
			}

			return map;
		}

		Set<Serializable> uncachedPrimaryKeys = null;

		for (Serializable primaryKey : primaryKeys) {
			User user = (User)entityCache.getResult(UserModelImpl.ENTITY_CACHE_ENABLED,
					UserImpl.class, primaryKey);

			if (user == null) {
				if (uncachedPrimaryKeys == null) {
					uncachedPrimaryKeys = new HashSet<Serializable>();
				}

				uncachedPrimaryKeys.add(primaryKey);
			}
			else {
				map.put(primaryKey, user);
			}
		}

		if (uncachedPrimaryKeys == null) {
			return map;
		}

		StringBundler query = new StringBundler((uncachedPrimaryKeys.size() * 2) +
				1);

		query.append(_SQL_SELECT_USER_WHERE_PKS_IN);

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

			for (User user : (List<User>)q.list()) {
				map.put(user.getPrimaryKeyObj(), user);

				cacheResult(user);

				uncachedPrimaryKeys.remove(user.getPrimaryKeyObj());
			}

			for (Serializable primaryKey : uncachedPrimaryKeys) {
				entityCache.putResult(UserModelImpl.ENTITY_CACHE_ENABLED,
					UserImpl.class, primaryKey, _nullUser);
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
	 * Returns all the users.
	 *
	 * @return the users
	 */
	@Override
	public List<User> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the users.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link UserModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of users
	 * @param end the upper bound of the range of users (not inclusive)
	 * @return the range of users
	 */
	@Override
	public List<User> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the users.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link UserModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of users
	 * @param end the upper bound of the range of users (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of users
	 */
	@Override
	public List<User> findAll(int start, int end,
		OrderByComparator<User> orderByComparator) {
		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the users.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link UserModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of users
	 * @param end the upper bound of the range of users (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of users
	 */
	@Override
	public List<User> findAll(int start, int end,
		OrderByComparator<User> orderByComparator, boolean retrieveFromCache) {
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

		List<User> list = null;

		if (retrieveFromCache) {
			list = (List<User>)finderCache.getResult(finderPath, finderArgs,
					this);
		}

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

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<User>)QueryUtil.list(q, getDialect(), start,
							end);
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
	 * Removes all the users from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (User user : findAll()) {
			remove(user);
		}
	}

	/**
	 * Returns the number of users.
	 *
	 * @return the number of users
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_USER);

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

	/**
	 * Returns the primaryKeys of groups associated with the user.
	 *
	 * @param pk the primary key of the user
	 * @return long[] of the primaryKeys of groups associated with the user
	 */
	@Override
	public long[] getGroupPrimaryKeys(long pk) {
		long[] pks = userToGroupTableMapper.getRightPrimaryKeys(companyProvider.getCompanyId(),
				pk);

		return pks.clone();
	}

	/**
	 * Returns all the groups associated with the user.
	 *
	 * @param pk the primary key of the user
	 * @return the groups associated with the user
	 */
	@Override
	public List<com.liferay.portal.model.Group> getGroups(long pk) {
		return getGroups(pk, QueryUtil.ALL_POS, QueryUtil.ALL_POS);
	}

	/**
	 * Returns a range of all the groups associated with the user.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link UserModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param pk the primary key of the user
	 * @param start the lower bound of the range of users
	 * @param end the upper bound of the range of users (not inclusive)
	 * @return the range of groups associated with the user
	 */
	@Override
	public List<com.liferay.portal.model.Group> getGroups(long pk, int start,
		int end) {
		return getGroups(pk, start, end, null);
	}

	/**
	 * Returns an ordered range of all the groups associated with the user.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link UserModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param pk the primary key of the user
	 * @param start the lower bound of the range of users
	 * @param end the upper bound of the range of users (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of groups associated with the user
	 */
	@Override
	public List<com.liferay.portal.model.Group> getGroups(long pk, int start,
		int end,
		OrderByComparator<com.liferay.portal.model.Group> orderByComparator) {
		return userToGroupTableMapper.getRightBaseModels(companyProvider.getCompanyId(),
			pk, start, end, orderByComparator);
	}

	/**
	 * Returns the number of groups associated with the user.
	 *
	 * @param pk the primary key of the user
	 * @return the number of groups associated with the user
	 */
	@Override
	public int getGroupsSize(long pk) {
		long[] pks = userToGroupTableMapper.getRightPrimaryKeys(companyProvider.getCompanyId(),
				pk);

		return pks.length;
	}

	/**
	 * Returns <code>true</code> if the group is associated with the user.
	 *
	 * @param pk the primary key of the user
	 * @param groupPK the primary key of the group
	 * @return <code>true</code> if the group is associated with the user; <code>false</code> otherwise
	 */
	@Override
	public boolean containsGroup(long pk, long groupPK) {
		return userToGroupTableMapper.containsTableMapping(companyProvider.getCompanyId(),
			pk, groupPK);
	}

	/**
	 * Returns <code>true</code> if the user has any groups associated with it.
	 *
	 * @param pk the primary key of the user to check for associations with groups
	 * @return <code>true</code> if the user has any groups associated with it; <code>false</code> otherwise
	 */
	@Override
	public boolean containsGroups(long pk) {
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
	 */
	@Override
	public void addGroup(long pk, long groupPK) {
		userToGroupTableMapper.addTableMapping(companyProvider.getCompanyId(),
			pk, groupPK);
	}

	/**
	 * Adds an association between the user and the group. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the user
	 * @param group the group
	 */
	@Override
	public void addGroup(long pk, com.liferay.portal.model.Group group) {
		userToGroupTableMapper.addTableMapping(companyProvider.getCompanyId(),
			pk, group.getPrimaryKey());
	}

	/**
	 * Adds an association between the user and the groups. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the user
	 * @param groupPKs the primary keys of the groups
	 */
	@Override
	public void addGroups(long pk, long[] groupPKs) {
		for (long groupPK : groupPKs) {
			userToGroupTableMapper.addTableMapping(companyProvider.getCompanyId(),
				pk, groupPK);
		}
	}

	/**
	 * Adds an association between the user and the groups. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the user
	 * @param groups the groups
	 */
	@Override
	public void addGroups(long pk, List<com.liferay.portal.model.Group> groups) {
		for (com.liferay.portal.model.Group group : groups) {
			userToGroupTableMapper.addTableMapping(companyProvider.getCompanyId(),
				pk, group.getPrimaryKey());
		}
	}

	/**
	 * Clears all associations between the user and its groups. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the user to clear the associated groups from
	 */
	@Override
	public void clearGroups(long pk) {
		userToGroupTableMapper.deleteLeftPrimaryKeyTableMappings(companyProvider.getCompanyId(),
			pk);
	}

	/**
	 * Removes the association between the user and the group. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the user
	 * @param groupPK the primary key of the group
	 */
	@Override
	public void removeGroup(long pk, long groupPK) {
		userToGroupTableMapper.deleteTableMapping(companyProvider.getCompanyId(),
			pk, groupPK);
	}

	/**
	 * Removes the association between the user and the group. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the user
	 * @param group the group
	 */
	@Override
	public void removeGroup(long pk, com.liferay.portal.model.Group group) {
		userToGroupTableMapper.deleteTableMapping(companyProvider.getCompanyId(),
			pk, group.getPrimaryKey());
	}

	/**
	 * Removes the association between the user and the groups. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the user
	 * @param groupPKs the primary keys of the groups
	 */
	@Override
	public void removeGroups(long pk, long[] groupPKs) {
		for (long groupPK : groupPKs) {
			userToGroupTableMapper.deleteTableMapping(companyProvider.getCompanyId(),
				pk, groupPK);
		}
	}

	/**
	 * Removes the association between the user and the groups. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the user
	 * @param groups the groups
	 */
	@Override
	public void removeGroups(long pk,
		List<com.liferay.portal.model.Group> groups) {
		for (com.liferay.portal.model.Group group : groups) {
			userToGroupTableMapper.deleteTableMapping(companyProvider.getCompanyId(),
				pk, group.getPrimaryKey());
		}
	}

	/**
	 * Sets the groups associated with the user, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the user
	 * @param groupPKs the primary keys of the groups to be associated with the user
	 */
	@Override
	public void setGroups(long pk, long[] groupPKs) {
		Set<Long> newGroupPKsSet = SetUtil.fromArray(groupPKs);
		Set<Long> oldGroupPKsSet = SetUtil.fromArray(userToGroupTableMapper.getRightPrimaryKeys(
					companyProvider.getCompanyId(), pk));

		Set<Long> removeGroupPKsSet = new HashSet<Long>(oldGroupPKsSet);

		removeGroupPKsSet.removeAll(newGroupPKsSet);

		for (long removeGroupPK : removeGroupPKsSet) {
			userToGroupTableMapper.deleteTableMapping(companyProvider.getCompanyId(),
				pk, removeGroupPK);
		}

		newGroupPKsSet.removeAll(oldGroupPKsSet);

		for (long newGroupPK : newGroupPKsSet) {
			userToGroupTableMapper.addTableMapping(companyProvider.getCompanyId(),
				pk, newGroupPK);
		}
	}

	/**
	 * Sets the groups associated with the user, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the user
	 * @param groups the groups to be associated with the user
	 */
	@Override
	public void setGroups(long pk, List<com.liferay.portal.model.Group> groups) {
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
	}

	/**
	 * Returns the primaryKeys of organizations associated with the user.
	 *
	 * @param pk the primary key of the user
	 * @return long[] of the primaryKeys of organizations associated with the user
	 */
	@Override
	public long[] getOrganizationPrimaryKeys(long pk) {
		long[] pks = userToOrganizationTableMapper.getRightPrimaryKeys(companyProvider.getCompanyId(),
				pk);

		return pks.clone();
	}

	/**
	 * Returns all the organizations associated with the user.
	 *
	 * @param pk the primary key of the user
	 * @return the organizations associated with the user
	 */
	@Override
	public List<com.liferay.portal.model.Organization> getOrganizations(long pk) {
		return getOrganizations(pk, QueryUtil.ALL_POS, QueryUtil.ALL_POS);
	}

	/**
	 * Returns a range of all the organizations associated with the user.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link UserModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param pk the primary key of the user
	 * @param start the lower bound of the range of users
	 * @param end the upper bound of the range of users (not inclusive)
	 * @return the range of organizations associated with the user
	 */
	@Override
	public List<com.liferay.portal.model.Organization> getOrganizations(
		long pk, int start, int end) {
		return getOrganizations(pk, start, end, null);
	}

	/**
	 * Returns an ordered range of all the organizations associated with the user.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link UserModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param pk the primary key of the user
	 * @param start the lower bound of the range of users
	 * @param end the upper bound of the range of users (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of organizations associated with the user
	 */
	@Override
	public List<com.liferay.portal.model.Organization> getOrganizations(
		long pk, int start, int end,
		OrderByComparator<com.liferay.portal.model.Organization> orderByComparator) {
		return userToOrganizationTableMapper.getRightBaseModels(companyProvider.getCompanyId(),
			pk, start, end, orderByComparator);
	}

	/**
	 * Returns the number of organizations associated with the user.
	 *
	 * @param pk the primary key of the user
	 * @return the number of organizations associated with the user
	 */
	@Override
	public int getOrganizationsSize(long pk) {
		long[] pks = userToOrganizationTableMapper.getRightPrimaryKeys(companyProvider.getCompanyId(),
				pk);

		return pks.length;
	}

	/**
	 * Returns <code>true</code> if the organization is associated with the user.
	 *
	 * @param pk the primary key of the user
	 * @param organizationPK the primary key of the organization
	 * @return <code>true</code> if the organization is associated with the user; <code>false</code> otherwise
	 */
	@Override
	public boolean containsOrganization(long pk, long organizationPK) {
		return userToOrganizationTableMapper.containsTableMapping(companyProvider.getCompanyId(),
			pk, organizationPK);
	}

	/**
	 * Returns <code>true</code> if the user has any organizations associated with it.
	 *
	 * @param pk the primary key of the user to check for associations with organizations
	 * @return <code>true</code> if the user has any organizations associated with it; <code>false</code> otherwise
	 */
	@Override
	public boolean containsOrganizations(long pk) {
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
	 */
	@Override
	public void addOrganization(long pk, long organizationPK) {
		userToOrganizationTableMapper.addTableMapping(companyProvider.getCompanyId(),
			pk, organizationPK);
	}

	/**
	 * Adds an association between the user and the organization. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the user
	 * @param organization the organization
	 */
	@Override
	public void addOrganization(long pk,
		com.liferay.portal.model.Organization organization) {
		userToOrganizationTableMapper.addTableMapping(companyProvider.getCompanyId(),
			pk, organization.getPrimaryKey());
	}

	/**
	 * Adds an association between the user and the organizations. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the user
	 * @param organizationPKs the primary keys of the organizations
	 */
	@Override
	public void addOrganizations(long pk, long[] organizationPKs) {
		for (long organizationPK : organizationPKs) {
			userToOrganizationTableMapper.addTableMapping(companyProvider.getCompanyId(),
				pk, organizationPK);
		}
	}

	/**
	 * Adds an association between the user and the organizations. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the user
	 * @param organizations the organizations
	 */
	@Override
	public void addOrganizations(long pk,
		List<com.liferay.portal.model.Organization> organizations) {
		for (com.liferay.portal.model.Organization organization : organizations) {
			userToOrganizationTableMapper.addTableMapping(companyProvider.getCompanyId(),
				pk, organization.getPrimaryKey());
		}
	}

	/**
	 * Clears all associations between the user and its organizations. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the user to clear the associated organizations from
	 */
	@Override
	public void clearOrganizations(long pk) {
		userToOrganizationTableMapper.deleteLeftPrimaryKeyTableMappings(companyProvider.getCompanyId(),
			pk);
	}

	/**
	 * Removes the association between the user and the organization. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the user
	 * @param organizationPK the primary key of the organization
	 */
	@Override
	public void removeOrganization(long pk, long organizationPK) {
		userToOrganizationTableMapper.deleteTableMapping(companyProvider.getCompanyId(),
			pk, organizationPK);
	}

	/**
	 * Removes the association between the user and the organization. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the user
	 * @param organization the organization
	 */
	@Override
	public void removeOrganization(long pk,
		com.liferay.portal.model.Organization organization) {
		userToOrganizationTableMapper.deleteTableMapping(companyProvider.getCompanyId(),
			pk, organization.getPrimaryKey());
	}

	/**
	 * Removes the association between the user and the organizations. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the user
	 * @param organizationPKs the primary keys of the organizations
	 */
	@Override
	public void removeOrganizations(long pk, long[] organizationPKs) {
		for (long organizationPK : organizationPKs) {
			userToOrganizationTableMapper.deleteTableMapping(companyProvider.getCompanyId(),
				pk, organizationPK);
		}
	}

	/**
	 * Removes the association between the user and the organizations. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the user
	 * @param organizations the organizations
	 */
	@Override
	public void removeOrganizations(long pk,
		List<com.liferay.portal.model.Organization> organizations) {
		for (com.liferay.portal.model.Organization organization : organizations) {
			userToOrganizationTableMapper.deleteTableMapping(companyProvider.getCompanyId(),
				pk, organization.getPrimaryKey());
		}
	}

	/**
	 * Sets the organizations associated with the user, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the user
	 * @param organizationPKs the primary keys of the organizations to be associated with the user
	 */
	@Override
	public void setOrganizations(long pk, long[] organizationPKs) {
		Set<Long> newOrganizationPKsSet = SetUtil.fromArray(organizationPKs);
		Set<Long> oldOrganizationPKsSet = SetUtil.fromArray(userToOrganizationTableMapper.getRightPrimaryKeys(
					companyProvider.getCompanyId(), pk));

		Set<Long> removeOrganizationPKsSet = new HashSet<Long>(oldOrganizationPKsSet);

		removeOrganizationPKsSet.removeAll(newOrganizationPKsSet);

		for (long removeOrganizationPK : removeOrganizationPKsSet) {
			userToOrganizationTableMapper.deleteTableMapping(companyProvider.getCompanyId(),
				pk, removeOrganizationPK);
		}

		newOrganizationPKsSet.removeAll(oldOrganizationPKsSet);

		for (long newOrganizationPK : newOrganizationPKsSet) {
			userToOrganizationTableMapper.addTableMapping(companyProvider.getCompanyId(),
				pk, newOrganizationPK);
		}
	}

	/**
	 * Sets the organizations associated with the user, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the user
	 * @param organizations the organizations to be associated with the user
	 */
	@Override
	public void setOrganizations(long pk,
		List<com.liferay.portal.model.Organization> organizations) {
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
	}

	/**
	 * Returns the primaryKeys of roles associated with the user.
	 *
	 * @param pk the primary key of the user
	 * @return long[] of the primaryKeys of roles associated with the user
	 */
	@Override
	public long[] getRolePrimaryKeys(long pk) {
		long[] pks = userToRoleTableMapper.getRightPrimaryKeys(companyProvider.getCompanyId(),
				pk);

		return pks.clone();
	}

	/**
	 * Returns all the roles associated with the user.
	 *
	 * @param pk the primary key of the user
	 * @return the roles associated with the user
	 */
	@Override
	public List<com.liferay.portal.model.Role> getRoles(long pk) {
		return getRoles(pk, QueryUtil.ALL_POS, QueryUtil.ALL_POS);
	}

	/**
	 * Returns a range of all the roles associated with the user.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link UserModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param pk the primary key of the user
	 * @param start the lower bound of the range of users
	 * @param end the upper bound of the range of users (not inclusive)
	 * @return the range of roles associated with the user
	 */
	@Override
	public List<com.liferay.portal.model.Role> getRoles(long pk, int start,
		int end) {
		return getRoles(pk, start, end, null);
	}

	/**
	 * Returns an ordered range of all the roles associated with the user.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link UserModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param pk the primary key of the user
	 * @param start the lower bound of the range of users
	 * @param end the upper bound of the range of users (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of roles associated with the user
	 */
	@Override
	public List<com.liferay.portal.model.Role> getRoles(long pk, int start,
		int end,
		OrderByComparator<com.liferay.portal.model.Role> orderByComparator) {
		return userToRoleTableMapper.getRightBaseModels(companyProvider.getCompanyId(),
			pk, start, end, orderByComparator);
	}

	/**
	 * Returns the number of roles associated with the user.
	 *
	 * @param pk the primary key of the user
	 * @return the number of roles associated with the user
	 */
	@Override
	public int getRolesSize(long pk) {
		long[] pks = userToRoleTableMapper.getRightPrimaryKeys(companyProvider.getCompanyId(),
				pk);

		return pks.length;
	}

	/**
	 * Returns <code>true</code> if the role is associated with the user.
	 *
	 * @param pk the primary key of the user
	 * @param rolePK the primary key of the role
	 * @return <code>true</code> if the role is associated with the user; <code>false</code> otherwise
	 */
	@Override
	public boolean containsRole(long pk, long rolePK) {
		return userToRoleTableMapper.containsTableMapping(companyProvider.getCompanyId(),
			pk, rolePK);
	}

	/**
	 * Returns <code>true</code> if the user has any roles associated with it.
	 *
	 * @param pk the primary key of the user to check for associations with roles
	 * @return <code>true</code> if the user has any roles associated with it; <code>false</code> otherwise
	 */
	@Override
	public boolean containsRoles(long pk) {
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
	 */
	@Override
	public void addRole(long pk, long rolePK) {
		userToRoleTableMapper.addTableMapping(companyProvider.getCompanyId(),
			pk, rolePK);
	}

	/**
	 * Adds an association between the user and the role. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the user
	 * @param role the role
	 */
	@Override
	public void addRole(long pk, com.liferay.portal.model.Role role) {
		userToRoleTableMapper.addTableMapping(companyProvider.getCompanyId(),
			pk, role.getPrimaryKey());
	}

	/**
	 * Adds an association between the user and the roles. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the user
	 * @param rolePKs the primary keys of the roles
	 */
	@Override
	public void addRoles(long pk, long[] rolePKs) {
		for (long rolePK : rolePKs) {
			userToRoleTableMapper.addTableMapping(companyProvider.getCompanyId(),
				pk, rolePK);
		}
	}

	/**
	 * Adds an association between the user and the roles. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the user
	 * @param roles the roles
	 */
	@Override
	public void addRoles(long pk, List<com.liferay.portal.model.Role> roles) {
		for (com.liferay.portal.model.Role role : roles) {
			userToRoleTableMapper.addTableMapping(companyProvider.getCompanyId(),
				pk, role.getPrimaryKey());
		}
	}

	/**
	 * Clears all associations between the user and its roles. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the user to clear the associated roles from
	 */
	@Override
	public void clearRoles(long pk) {
		userToRoleTableMapper.deleteLeftPrimaryKeyTableMappings(companyProvider.getCompanyId(),
			pk);
	}

	/**
	 * Removes the association between the user and the role. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the user
	 * @param rolePK the primary key of the role
	 */
	@Override
	public void removeRole(long pk, long rolePK) {
		userToRoleTableMapper.deleteTableMapping(companyProvider.getCompanyId(),
			pk, rolePK);
	}

	/**
	 * Removes the association between the user and the role. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the user
	 * @param role the role
	 */
	@Override
	public void removeRole(long pk, com.liferay.portal.model.Role role) {
		userToRoleTableMapper.deleteTableMapping(companyProvider.getCompanyId(),
			pk, role.getPrimaryKey());
	}

	/**
	 * Removes the association between the user and the roles. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the user
	 * @param rolePKs the primary keys of the roles
	 */
	@Override
	public void removeRoles(long pk, long[] rolePKs) {
		for (long rolePK : rolePKs) {
			userToRoleTableMapper.deleteTableMapping(companyProvider.getCompanyId(),
				pk, rolePK);
		}
	}

	/**
	 * Removes the association between the user and the roles. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the user
	 * @param roles the roles
	 */
	@Override
	public void removeRoles(long pk, List<com.liferay.portal.model.Role> roles) {
		for (com.liferay.portal.model.Role role : roles) {
			userToRoleTableMapper.deleteTableMapping(companyProvider.getCompanyId(),
				pk, role.getPrimaryKey());
		}
	}

	/**
	 * Sets the roles associated with the user, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the user
	 * @param rolePKs the primary keys of the roles to be associated with the user
	 */
	@Override
	public void setRoles(long pk, long[] rolePKs) {
		Set<Long> newRolePKsSet = SetUtil.fromArray(rolePKs);
		Set<Long> oldRolePKsSet = SetUtil.fromArray(userToRoleTableMapper.getRightPrimaryKeys(
					companyProvider.getCompanyId(), pk));

		Set<Long> removeRolePKsSet = new HashSet<Long>(oldRolePKsSet);

		removeRolePKsSet.removeAll(newRolePKsSet);

		for (long removeRolePK : removeRolePKsSet) {
			userToRoleTableMapper.deleteTableMapping(companyProvider.getCompanyId(),
				pk, removeRolePK);
		}

		newRolePKsSet.removeAll(oldRolePKsSet);

		for (long newRolePK : newRolePKsSet) {
			userToRoleTableMapper.addTableMapping(companyProvider.getCompanyId(),
				pk, newRolePK);
		}
	}

	/**
	 * Sets the roles associated with the user, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the user
	 * @param roles the roles to be associated with the user
	 */
	@Override
	public void setRoles(long pk, List<com.liferay.portal.model.Role> roles) {
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
	}

	/**
	 * Returns the primaryKeys of teams associated with the user.
	 *
	 * @param pk the primary key of the user
	 * @return long[] of the primaryKeys of teams associated with the user
	 */
	@Override
	public long[] getTeamPrimaryKeys(long pk) {
		long[] pks = userToTeamTableMapper.getRightPrimaryKeys(companyProvider.getCompanyId(),
				pk);

		return pks.clone();
	}

	/**
	 * Returns all the teams associated with the user.
	 *
	 * @param pk the primary key of the user
	 * @return the teams associated with the user
	 */
	@Override
	public List<com.liferay.portal.model.Team> getTeams(long pk) {
		return getTeams(pk, QueryUtil.ALL_POS, QueryUtil.ALL_POS);
	}

	/**
	 * Returns a range of all the teams associated with the user.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link UserModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param pk the primary key of the user
	 * @param start the lower bound of the range of users
	 * @param end the upper bound of the range of users (not inclusive)
	 * @return the range of teams associated with the user
	 */
	@Override
	public List<com.liferay.portal.model.Team> getTeams(long pk, int start,
		int end) {
		return getTeams(pk, start, end, null);
	}

	/**
	 * Returns an ordered range of all the teams associated with the user.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link UserModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param pk the primary key of the user
	 * @param start the lower bound of the range of users
	 * @param end the upper bound of the range of users (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of teams associated with the user
	 */
	@Override
	public List<com.liferay.portal.model.Team> getTeams(long pk, int start,
		int end,
		OrderByComparator<com.liferay.portal.model.Team> orderByComparator) {
		return userToTeamTableMapper.getRightBaseModels(companyProvider.getCompanyId(),
			pk, start, end, orderByComparator);
	}

	/**
	 * Returns the number of teams associated with the user.
	 *
	 * @param pk the primary key of the user
	 * @return the number of teams associated with the user
	 */
	@Override
	public int getTeamsSize(long pk) {
		long[] pks = userToTeamTableMapper.getRightPrimaryKeys(companyProvider.getCompanyId(),
				pk);

		return pks.length;
	}

	/**
	 * Returns <code>true</code> if the team is associated with the user.
	 *
	 * @param pk the primary key of the user
	 * @param teamPK the primary key of the team
	 * @return <code>true</code> if the team is associated with the user; <code>false</code> otherwise
	 */
	@Override
	public boolean containsTeam(long pk, long teamPK) {
		return userToTeamTableMapper.containsTableMapping(companyProvider.getCompanyId(),
			pk, teamPK);
	}

	/**
	 * Returns <code>true</code> if the user has any teams associated with it.
	 *
	 * @param pk the primary key of the user to check for associations with teams
	 * @return <code>true</code> if the user has any teams associated with it; <code>false</code> otherwise
	 */
	@Override
	public boolean containsTeams(long pk) {
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
	 */
	@Override
	public void addTeam(long pk, long teamPK) {
		userToTeamTableMapper.addTableMapping(companyProvider.getCompanyId(),
			pk, teamPK);
	}

	/**
	 * Adds an association between the user and the team. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the user
	 * @param team the team
	 */
	@Override
	public void addTeam(long pk, com.liferay.portal.model.Team team) {
		userToTeamTableMapper.addTableMapping(companyProvider.getCompanyId(),
			pk, team.getPrimaryKey());
	}

	/**
	 * Adds an association between the user and the teams. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the user
	 * @param teamPKs the primary keys of the teams
	 */
	@Override
	public void addTeams(long pk, long[] teamPKs) {
		for (long teamPK : teamPKs) {
			userToTeamTableMapper.addTableMapping(companyProvider.getCompanyId(),
				pk, teamPK);
		}
	}

	/**
	 * Adds an association between the user and the teams. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the user
	 * @param teams the teams
	 */
	@Override
	public void addTeams(long pk, List<com.liferay.portal.model.Team> teams) {
		for (com.liferay.portal.model.Team team : teams) {
			userToTeamTableMapper.addTableMapping(companyProvider.getCompanyId(),
				pk, team.getPrimaryKey());
		}
	}

	/**
	 * Clears all associations between the user and its teams. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the user to clear the associated teams from
	 */
	@Override
	public void clearTeams(long pk) {
		userToTeamTableMapper.deleteLeftPrimaryKeyTableMappings(companyProvider.getCompanyId(),
			pk);
	}

	/**
	 * Removes the association between the user and the team. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the user
	 * @param teamPK the primary key of the team
	 */
	@Override
	public void removeTeam(long pk, long teamPK) {
		userToTeamTableMapper.deleteTableMapping(companyProvider.getCompanyId(),
			pk, teamPK);
	}

	/**
	 * Removes the association between the user and the team. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the user
	 * @param team the team
	 */
	@Override
	public void removeTeam(long pk, com.liferay.portal.model.Team team) {
		userToTeamTableMapper.deleteTableMapping(companyProvider.getCompanyId(),
			pk, team.getPrimaryKey());
	}

	/**
	 * Removes the association between the user and the teams. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the user
	 * @param teamPKs the primary keys of the teams
	 */
	@Override
	public void removeTeams(long pk, long[] teamPKs) {
		for (long teamPK : teamPKs) {
			userToTeamTableMapper.deleteTableMapping(companyProvider.getCompanyId(),
				pk, teamPK);
		}
	}

	/**
	 * Removes the association between the user and the teams. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the user
	 * @param teams the teams
	 */
	@Override
	public void removeTeams(long pk, List<com.liferay.portal.model.Team> teams) {
		for (com.liferay.portal.model.Team team : teams) {
			userToTeamTableMapper.deleteTableMapping(companyProvider.getCompanyId(),
				pk, team.getPrimaryKey());
		}
	}

	/**
	 * Sets the teams associated with the user, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the user
	 * @param teamPKs the primary keys of the teams to be associated with the user
	 */
	@Override
	public void setTeams(long pk, long[] teamPKs) {
		Set<Long> newTeamPKsSet = SetUtil.fromArray(teamPKs);
		Set<Long> oldTeamPKsSet = SetUtil.fromArray(userToTeamTableMapper.getRightPrimaryKeys(
					companyProvider.getCompanyId(), pk));

		Set<Long> removeTeamPKsSet = new HashSet<Long>(oldTeamPKsSet);

		removeTeamPKsSet.removeAll(newTeamPKsSet);

		for (long removeTeamPK : removeTeamPKsSet) {
			userToTeamTableMapper.deleteTableMapping(companyProvider.getCompanyId(),
				pk, removeTeamPK);
		}

		newTeamPKsSet.removeAll(oldTeamPKsSet);

		for (long newTeamPK : newTeamPKsSet) {
			userToTeamTableMapper.addTableMapping(companyProvider.getCompanyId(),
				pk, newTeamPK);
		}
	}

	/**
	 * Sets the teams associated with the user, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the user
	 * @param teams the teams to be associated with the user
	 */
	@Override
	public void setTeams(long pk, List<com.liferay.portal.model.Team> teams) {
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
	}

	/**
	 * Returns the primaryKeys of user groups associated with the user.
	 *
	 * @param pk the primary key of the user
	 * @return long[] of the primaryKeys of user groups associated with the user
	 */
	@Override
	public long[] getUserGroupPrimaryKeys(long pk) {
		long[] pks = userToUserGroupTableMapper.getRightPrimaryKeys(companyProvider.getCompanyId(),
				pk);

		return pks.clone();
	}

	/**
	 * Returns all the user groups associated with the user.
	 *
	 * @param pk the primary key of the user
	 * @return the user groups associated with the user
	 */
	@Override
	public List<com.liferay.portal.model.UserGroup> getUserGroups(long pk) {
		return getUserGroups(pk, QueryUtil.ALL_POS, QueryUtil.ALL_POS);
	}

	/**
	 * Returns a range of all the user groups associated with the user.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link UserModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param pk the primary key of the user
	 * @param start the lower bound of the range of users
	 * @param end the upper bound of the range of users (not inclusive)
	 * @return the range of user groups associated with the user
	 */
	@Override
	public List<com.liferay.portal.model.UserGroup> getUserGroups(long pk,
		int start, int end) {
		return getUserGroups(pk, start, end, null);
	}

	/**
	 * Returns an ordered range of all the user groups associated with the user.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link UserModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param pk the primary key of the user
	 * @param start the lower bound of the range of users
	 * @param end the upper bound of the range of users (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of user groups associated with the user
	 */
	@Override
	public List<com.liferay.portal.model.UserGroup> getUserGroups(long pk,
		int start, int end,
		OrderByComparator<com.liferay.portal.model.UserGroup> orderByComparator) {
		return userToUserGroupTableMapper.getRightBaseModels(companyProvider.getCompanyId(),
			pk, start, end, orderByComparator);
	}

	/**
	 * Returns the number of user groups associated with the user.
	 *
	 * @param pk the primary key of the user
	 * @return the number of user groups associated with the user
	 */
	@Override
	public int getUserGroupsSize(long pk) {
		long[] pks = userToUserGroupTableMapper.getRightPrimaryKeys(companyProvider.getCompanyId(),
				pk);

		return pks.length;
	}

	/**
	 * Returns <code>true</code> if the user group is associated with the user.
	 *
	 * @param pk the primary key of the user
	 * @param userGroupPK the primary key of the user group
	 * @return <code>true</code> if the user group is associated with the user; <code>false</code> otherwise
	 */
	@Override
	public boolean containsUserGroup(long pk, long userGroupPK) {
		return userToUserGroupTableMapper.containsTableMapping(companyProvider.getCompanyId(),
			pk, userGroupPK);
	}

	/**
	 * Returns <code>true</code> if the user has any user groups associated with it.
	 *
	 * @param pk the primary key of the user to check for associations with user groups
	 * @return <code>true</code> if the user has any user groups associated with it; <code>false</code> otherwise
	 */
	@Override
	public boolean containsUserGroups(long pk) {
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
	 */
	@Override
	public void addUserGroup(long pk, long userGroupPK) {
		userToUserGroupTableMapper.addTableMapping(companyProvider.getCompanyId(),
			pk, userGroupPK);
	}

	/**
	 * Adds an association between the user and the user group. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the user
	 * @param userGroup the user group
	 */
	@Override
	public void addUserGroup(long pk,
		com.liferay.portal.model.UserGroup userGroup) {
		userToUserGroupTableMapper.addTableMapping(companyProvider.getCompanyId(),
			pk, userGroup.getPrimaryKey());
	}

	/**
	 * Adds an association between the user and the user groups. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the user
	 * @param userGroupPKs the primary keys of the user groups
	 */
	@Override
	public void addUserGroups(long pk, long[] userGroupPKs) {
		for (long userGroupPK : userGroupPKs) {
			userToUserGroupTableMapper.addTableMapping(companyProvider.getCompanyId(),
				pk, userGroupPK);
		}
	}

	/**
	 * Adds an association between the user and the user groups. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the user
	 * @param userGroups the user groups
	 */
	@Override
	public void addUserGroups(long pk,
		List<com.liferay.portal.model.UserGroup> userGroups) {
		for (com.liferay.portal.model.UserGroup userGroup : userGroups) {
			userToUserGroupTableMapper.addTableMapping(companyProvider.getCompanyId(),
				pk, userGroup.getPrimaryKey());
		}
	}

	/**
	 * Clears all associations between the user and its user groups. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the user to clear the associated user groups from
	 */
	@Override
	public void clearUserGroups(long pk) {
		userToUserGroupTableMapper.deleteLeftPrimaryKeyTableMappings(companyProvider.getCompanyId(),
			pk);
	}

	/**
	 * Removes the association between the user and the user group. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the user
	 * @param userGroupPK the primary key of the user group
	 */
	@Override
	public void removeUserGroup(long pk, long userGroupPK) {
		userToUserGroupTableMapper.deleteTableMapping(companyProvider.getCompanyId(),
			pk, userGroupPK);
	}

	/**
	 * Removes the association between the user and the user group. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the user
	 * @param userGroup the user group
	 */
	@Override
	public void removeUserGroup(long pk,
		com.liferay.portal.model.UserGroup userGroup) {
		userToUserGroupTableMapper.deleteTableMapping(companyProvider.getCompanyId(),
			pk, userGroup.getPrimaryKey());
	}

	/**
	 * Removes the association between the user and the user groups. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the user
	 * @param userGroupPKs the primary keys of the user groups
	 */
	@Override
	public void removeUserGroups(long pk, long[] userGroupPKs) {
		for (long userGroupPK : userGroupPKs) {
			userToUserGroupTableMapper.deleteTableMapping(companyProvider.getCompanyId(),
				pk, userGroupPK);
		}
	}

	/**
	 * Removes the association between the user and the user groups. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the user
	 * @param userGroups the user groups
	 */
	@Override
	public void removeUserGroups(long pk,
		List<com.liferay.portal.model.UserGroup> userGroups) {
		for (com.liferay.portal.model.UserGroup userGroup : userGroups) {
			userToUserGroupTableMapper.deleteTableMapping(companyProvider.getCompanyId(),
				pk, userGroup.getPrimaryKey());
		}
	}

	/**
	 * Sets the user groups associated with the user, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the user
	 * @param userGroupPKs the primary keys of the user groups to be associated with the user
	 */
	@Override
	public void setUserGroups(long pk, long[] userGroupPKs) {
		Set<Long> newUserGroupPKsSet = SetUtil.fromArray(userGroupPKs);
		Set<Long> oldUserGroupPKsSet = SetUtil.fromArray(userToUserGroupTableMapper.getRightPrimaryKeys(
					companyProvider.getCompanyId(), pk));

		Set<Long> removeUserGroupPKsSet = new HashSet<Long>(oldUserGroupPKsSet);

		removeUserGroupPKsSet.removeAll(newUserGroupPKsSet);

		for (long removeUserGroupPK : removeUserGroupPKsSet) {
			userToUserGroupTableMapper.deleteTableMapping(companyProvider.getCompanyId(),
				pk, removeUserGroupPK);
		}

		newUserGroupPKsSet.removeAll(oldUserGroupPKsSet);

		for (long newUserGroupPK : newUserGroupPKsSet) {
			userToUserGroupTableMapper.addTableMapping(companyProvider.getCompanyId(),
				pk, newUserGroupPK);
		}
	}

	/**
	 * Sets the user groups associated with the user, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the user
	 * @param userGroups the user groups to be associated with the user
	 */
	@Override
	public void setUserGroups(long pk,
		List<com.liferay.portal.model.UserGroup> userGroups) {
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
	}

	@Override
	public Set<String> getBadColumnNames() {
		return _badColumnNames;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return UserModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the user persistence.
	 */
	public void afterPropertiesSet() {
		userToGroupTableMapper = TableMapperFactory.getTableMapper("Users_Groups",
				"companyId", "userId", "groupId", this, groupPersistence);

		userToOrganizationTableMapper = TableMapperFactory.getTableMapper("Users_Orgs",
				"companyId", "userId", "organizationId", this,
				organizationPersistence);

		userToRoleTableMapper = TableMapperFactory.getTableMapper("Users_Roles",
				"companyId", "userId", "roleId", this, rolePersistence);

		userToTeamTableMapper = TableMapperFactory.getTableMapper("Users_Teams",
				"companyId", "userId", "teamId", this, teamPersistence);

		userToUserGroupTableMapper = TableMapperFactory.getTableMapper("Users_UserGroups",
				"companyId", "userId", "userGroupId", this, userGroupPersistence);
	}

	public void destroy() {
		entityCache.removeCache(UserImpl.class.getName());
		finderCache.removeCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		TableMapperFactory.removeTableMapper("Users_Groups");
		TableMapperFactory.removeTableMapper("Users_Orgs");
		TableMapperFactory.removeTableMapper("Users_Roles");
		TableMapperFactory.removeTableMapper("Users_Teams");
		TableMapperFactory.removeTableMapper("Users_UserGroups");
	}

	@BeanReference(type = CompanyProvider.class)
	protected CompanyProvider companyProvider;
	protected EntityCache entityCache = EntityCacheUtil.getEntityCache();
	protected FinderCache finderCache = FinderCacheUtil.getFinderCache();
	@BeanReference(type = GroupPersistence.class)
	protected GroupPersistence groupPersistence;
	protected TableMapper<User, com.liferay.portal.model.Group> userToGroupTableMapper;
	@BeanReference(type = OrganizationPersistence.class)
	protected OrganizationPersistence organizationPersistence;
	protected TableMapper<User, com.liferay.portal.model.Organization> userToOrganizationTableMapper;
	@BeanReference(type = RolePersistence.class)
	protected RolePersistence rolePersistence;
	protected TableMapper<User, com.liferay.portal.model.Role> userToRoleTableMapper;
	@BeanReference(type = TeamPersistence.class)
	protected TeamPersistence teamPersistence;
	protected TableMapper<User, com.liferay.portal.model.Team> userToTeamTableMapper;
	@BeanReference(type = UserGroupPersistence.class)
	protected UserGroupPersistence userGroupPersistence;
	protected TableMapper<User, com.liferay.portal.model.UserGroup> userToUserGroupTableMapper;
	private static final String _SQL_SELECT_USER = "SELECT user FROM User user";
	private static final String _SQL_SELECT_USER_WHERE_PKS_IN = "SELECT user FROM User user WHERE userId IN (";
	private static final String _SQL_SELECT_USER_WHERE = "SELECT user FROM User user WHERE ";
	private static final String _SQL_COUNT_USER = "SELECT COUNT(user) FROM User user";
	private static final String _SQL_COUNT_USER_WHERE = "SELECT COUNT(user) FROM User user WHERE ";
	private static final String _ORDER_BY_ENTITY_ALIAS = "user.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No User exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No User exists with the key {";
	private static final Log _log = LogFactoryUtil.getLog(UserPersistenceImpl.class);
	private static final Set<String> _badColumnNames = SetUtil.fromArray(new String[] {
				"uuid", "password"
			});
	private static final User _nullUser = new UserImpl() {
			@Override
			public Object clone() {
				return this;
			}

			@Override
			public CacheModel<User> toCacheModel() {
				return _nullUserCacheModel;
			}
		};

	private static final CacheModel<User> _nullUserCacheModel = new NullCacheModel();

	private static class NullCacheModel implements CacheModel<User>, MVCCModel {
		@Override
		public long getMvccVersion() {
			return -1;
		}

		@Override
		public void setMvccVersion(long mvccVersion) {
		}

		@Override
		public User toEntityModel() {
			return _nullUser;
		}
	}
}