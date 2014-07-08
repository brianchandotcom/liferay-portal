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

import com.liferay.portal.NoSuchPasswordPolicyException;
import com.liferay.portal.kernel.cache.CacheRegistryUtil;
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
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.InstanceFactory;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;
import com.liferay.portal.model.CacheModel;
import com.liferay.portal.model.MVCCModel;
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.model.PasswordPolicy;
import com.liferay.portal.model.impl.PasswordPolicyImpl;
import com.liferay.portal.model.impl.PasswordPolicyModelImpl;
import com.liferay.portal.security.permission.InlineSQLHelperUtil;
import com.liferay.portal.service.persistence.PasswordPolicyPersistence;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The persistence implementation for the password policy service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see PasswordPolicyPersistence
 * @see PasswordPolicyUtil
 * @generated
 */
public class PasswordPolicyPersistenceImpl extends BasePersistenceImpl<PasswordPolicy>
	implements PasswordPolicyPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link PasswordPolicyUtil} to access the password policy persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = PasswordPolicyImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(PasswordPolicyModelImpl.ENTITY_CACHE_ENABLED,
			PasswordPolicyModelImpl.FINDER_CACHE_ENABLED,
			PasswordPolicyImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(PasswordPolicyModelImpl.ENTITY_CACHE_ENABLED,
			PasswordPolicyModelImpl.FINDER_CACHE_ENABLED,
			PasswordPolicyImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(PasswordPolicyModelImpl.ENTITY_CACHE_ENABLED,
			PasswordPolicyModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_UUID = new FinderPath(PasswordPolicyModelImpl.ENTITY_CACHE_ENABLED,
			PasswordPolicyModelImpl.FINDER_CACHE_ENABLED,
			PasswordPolicyImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByUuid",
			new String[] {
				String.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID = new FinderPath(PasswordPolicyModelImpl.ENTITY_CACHE_ENABLED,
			PasswordPolicyModelImpl.FINDER_CACHE_ENABLED,
			PasswordPolicyImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid",
			new String[] { String.class.getName() },
			PasswordPolicyModelImpl.UUID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID = new FinderPath(PasswordPolicyModelImpl.ENTITY_CACHE_ENABLED,
			PasswordPolicyModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUuid",
			new String[] { String.class.getName() });

	/**
	 * Returns all the password policies where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching password policies
	 */
	@Override
	public List<PasswordPolicy> findByUuid(String uuid) {
		return findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the password policies where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.PasswordPolicyModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of password policies
	 * @param end the upper bound of the range of password policies (not inclusive)
	 * @return the range of matching password policies
	 */
	@Override
	public List<PasswordPolicy> findByUuid(String uuid, int start, int end) {
		return findByUuid(uuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the password policies where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.PasswordPolicyModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of password policies
	 * @param end the upper bound of the range of password policies (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching password policies
	 */
	@Override
	public List<PasswordPolicy> findByUuid(String uuid, int start, int end,
		OrderByComparator orderByComparator) {
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

		List<PasswordPolicy> list = (List<PasswordPolicy>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (PasswordPolicy passwordPolicy : list) {
				if (!Validator.equals(uuid, passwordPolicy.getUuid())) {
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

			query.append(_SQL_SELECT_PASSWORDPOLICY_WHERE);

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
				query.append(PasswordPolicyModelImpl.ORDER_BY_JPQL);
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
					list = (List<PasswordPolicy>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<PasswordPolicy>)QueryUtil.list(q,
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
	 * Returns the first password policy in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching password policy
	 * @throws com.liferay.portal.NoSuchPasswordPolicyException if a matching password policy could not be found
	 */
	@Override
	public PasswordPolicy findByUuid_First(String uuid,
		OrderByComparator orderByComparator)
		throws NoSuchPasswordPolicyException {
		PasswordPolicy passwordPolicy = fetchByUuid_First(uuid,
				orderByComparator);

		if (passwordPolicy != null) {
			return passwordPolicy;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchPasswordPolicyException(msg.toString());
	}

	/**
	 * Returns the first password policy in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching password policy, or <code>null</code> if a matching password policy could not be found
	 */
	@Override
	public PasswordPolicy fetchByUuid_First(String uuid,
		OrderByComparator orderByComparator) {
		List<PasswordPolicy> list = findByUuid(uuid, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last password policy in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching password policy
	 * @throws com.liferay.portal.NoSuchPasswordPolicyException if a matching password policy could not be found
	 */
	@Override
	public PasswordPolicy findByUuid_Last(String uuid,
		OrderByComparator orderByComparator)
		throws NoSuchPasswordPolicyException {
		PasswordPolicy passwordPolicy = fetchByUuid_Last(uuid, orderByComparator);

		if (passwordPolicy != null) {
			return passwordPolicy;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchPasswordPolicyException(msg.toString());
	}

	/**
	 * Returns the last password policy in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching password policy, or <code>null</code> if a matching password policy could not be found
	 */
	@Override
	public PasswordPolicy fetchByUuid_Last(String uuid,
		OrderByComparator orderByComparator) {
		int count = countByUuid(uuid);

		if (count == 0) {
			return null;
		}

		List<PasswordPolicy> list = findByUuid(uuid, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the password policies before and after the current password policy in the ordered set where uuid = &#63;.
	 *
	 * @param passwordPolicyId the primary key of the current password policy
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next password policy
	 * @throws com.liferay.portal.NoSuchPasswordPolicyException if a password policy with the primary key could not be found
	 */
	@Override
	public PasswordPolicy[] findByUuid_PrevAndNext(long passwordPolicyId,
		String uuid, OrderByComparator orderByComparator)
		throws NoSuchPasswordPolicyException {
		PasswordPolicy passwordPolicy = findByPrimaryKey(passwordPolicyId);

		Session session = null;

		try {
			session = openSession();

			PasswordPolicy[] array = new PasswordPolicyImpl[3];

			array[0] = getByUuid_PrevAndNext(session, passwordPolicy, uuid,
					orderByComparator, true);

			array[1] = passwordPolicy;

			array[2] = getByUuid_PrevAndNext(session, passwordPolicy, uuid,
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

	protected PasswordPolicy getByUuid_PrevAndNext(Session session,
		PasswordPolicy passwordPolicy, String uuid,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_PASSWORDPOLICY_WHERE);

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
			query.append(PasswordPolicyModelImpl.ORDER_BY_JPQL);
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
			Object[] values = orderByComparator.getOrderByConditionValues(passwordPolicy);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<PasswordPolicy> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the password policies that the user has permission to view where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching password policies that the user has permission to view
	 */
	@Override
	public List<PasswordPolicy> filterFindByUuid(String uuid) {
		return filterFindByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the password policies that the user has permission to view where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.PasswordPolicyModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of password policies
	 * @param end the upper bound of the range of password policies (not inclusive)
	 * @return the range of matching password policies that the user has permission to view
	 */
	@Override
	public List<PasswordPolicy> filterFindByUuid(String uuid, int start, int end) {
		return filterFindByUuid(uuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the password policies that the user has permissions to view where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.PasswordPolicyModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of password policies
	 * @param end the upper bound of the range of password policies (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching password policies that the user has permission to view
	 */
	@Override
	public List<PasswordPolicy> filterFindByUuid(String uuid, int start,
		int end, OrderByComparator orderByComparator) {
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
			query.append(_FILTER_SQL_SELECT_PASSWORDPOLICY_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_PASSWORDPOLICY_NO_INLINE_DISTINCT_WHERE_1);
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
			query.append(_FILTER_SQL_SELECT_PASSWORDPOLICY_NO_INLINE_DISTINCT_WHERE_2);
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
				query.append(PasswordPolicyModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(PasswordPolicyModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				PasswordPolicy.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				q.addEntity(_FILTER_ENTITY_ALIAS, PasswordPolicyImpl.class);
			}
			else {
				q.addEntity(_FILTER_ENTITY_TABLE, PasswordPolicyImpl.class);
			}

			QueryPos qPos = QueryPos.getInstance(q);

			if (bindUuid) {
				qPos.add(uuid);
			}

			return (List<PasswordPolicy>)QueryUtil.list(q, getDialect(), start,
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
	 * Returns the password policies before and after the current password policy in the ordered set of password policies that the user has permission to view where uuid = &#63;.
	 *
	 * @param passwordPolicyId the primary key of the current password policy
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next password policy
	 * @throws com.liferay.portal.NoSuchPasswordPolicyException if a password policy with the primary key could not be found
	 */
	@Override
	public PasswordPolicy[] filterFindByUuid_PrevAndNext(
		long passwordPolicyId, String uuid, OrderByComparator orderByComparator)
		throws NoSuchPasswordPolicyException {
		if (!InlineSQLHelperUtil.isEnabled()) {
			return findByUuid_PrevAndNext(passwordPolicyId, uuid,
				orderByComparator);
		}

		PasswordPolicy passwordPolicy = findByPrimaryKey(passwordPolicyId);

		Session session = null;

		try {
			session = openSession();

			PasswordPolicy[] array = new PasswordPolicyImpl[3];

			array[0] = filterGetByUuid_PrevAndNext(session, passwordPolicy,
					uuid, orderByComparator, true);

			array[1] = passwordPolicy;

			array[2] = filterGetByUuid_PrevAndNext(session, passwordPolicy,
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

	protected PasswordPolicy filterGetByUuid_PrevAndNext(Session session,
		PasswordPolicy passwordPolicy, String uuid,
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
			query.append(_FILTER_SQL_SELECT_PASSWORDPOLICY_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_PASSWORDPOLICY_NO_INLINE_DISTINCT_WHERE_1);
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
			query.append(_FILTER_SQL_SELECT_PASSWORDPOLICY_NO_INLINE_DISTINCT_WHERE_2);
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
				query.append(PasswordPolicyModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(PasswordPolicyModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				PasswordPolicy.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		SQLQuery q = session.createSynchronizedSQLQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		if (getDB().isSupportsInlineDistinct()) {
			q.addEntity(_FILTER_ENTITY_ALIAS, PasswordPolicyImpl.class);
		}
		else {
			q.addEntity(_FILTER_ENTITY_TABLE, PasswordPolicyImpl.class);
		}

		QueryPos qPos = QueryPos.getInstance(q);

		if (bindUuid) {
			qPos.add(uuid);
		}

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(passwordPolicy);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<PasswordPolicy> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the password policies where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	@Override
	public void removeByUuid(String uuid) {
		for (PasswordPolicy passwordPolicy : findByUuid(uuid,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(passwordPolicy);
		}
	}

	/**
	 * Returns the number of password policies where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching password policies
	 */
	@Override
	public int countByUuid(String uuid) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_UUID;

		Object[] finderArgs = new Object[] { uuid };

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs,
				this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_PASSWORDPOLICY_WHERE);

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

	/**
	 * Returns the number of password policies that the user has permission to view where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching password policies that the user has permission to view
	 */
	@Override
	public int filterCountByUuid(String uuid) {
		if (!InlineSQLHelperUtil.isEnabled()) {
			return countByUuid(uuid);
		}

		StringBundler query = new StringBundler(2);

		query.append(_FILTER_SQL_COUNT_PASSWORDPOLICY_WHERE);

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
				PasswordPolicy.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

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

	private static final String _FINDER_COLUMN_UUID_UUID_1 = "passwordPolicy.uuid IS NULL";
	private static final String _FINDER_COLUMN_UUID_UUID_2 = "passwordPolicy.uuid = ?";
	private static final String _FINDER_COLUMN_UUID_UUID_3 = "(passwordPolicy.uuid IS NULL OR passwordPolicy.uuid = '')";
	private static final String _FINDER_COLUMN_UUID_UUID_1_SQL = "passwordPolicy.uuid_ IS NULL";
	private static final String _FINDER_COLUMN_UUID_UUID_2_SQL = "passwordPolicy.uuid_ = ?";
	private static final String _FINDER_COLUMN_UUID_UUID_3_SQL = "(passwordPolicy.uuid_ IS NULL OR passwordPolicy.uuid_ = '')";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_UUID_C = new FinderPath(PasswordPolicyModelImpl.ENTITY_CACHE_ENABLED,
			PasswordPolicyModelImpl.FINDER_CACHE_ENABLED,
			PasswordPolicyImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByUuid_C",
			new String[] {
				String.class.getName(), Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C =
		new FinderPath(PasswordPolicyModelImpl.ENTITY_CACHE_ENABLED,
			PasswordPolicyModelImpl.FINDER_CACHE_ENABLED,
			PasswordPolicyImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid_C",
			new String[] { String.class.getName(), Long.class.getName() },
			PasswordPolicyModelImpl.UUID_COLUMN_BITMASK |
			PasswordPolicyModelImpl.COMPANYID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID_C = new FinderPath(PasswordPolicyModelImpl.ENTITY_CACHE_ENABLED,
			PasswordPolicyModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUuid_C",
			new String[] { String.class.getName(), Long.class.getName() });

	/**
	 * Returns all the password policies where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching password policies
	 */
	@Override
	public List<PasswordPolicy> findByUuid_C(String uuid, long companyId) {
		return findByUuid_C(uuid, companyId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the password policies where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.PasswordPolicyModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of password policies
	 * @param end the upper bound of the range of password policies (not inclusive)
	 * @return the range of matching password policies
	 */
	@Override
	public List<PasswordPolicy> findByUuid_C(String uuid, long companyId,
		int start, int end) {
		return findByUuid_C(uuid, companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the password policies where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.PasswordPolicyModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of password policies
	 * @param end the upper bound of the range of password policies (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching password policies
	 */
	@Override
	public List<PasswordPolicy> findByUuid_C(String uuid, long companyId,
		int start, int end, OrderByComparator orderByComparator) {
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

		List<PasswordPolicy> list = (List<PasswordPolicy>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (PasswordPolicy passwordPolicy : list) {
				if (!Validator.equals(uuid, passwordPolicy.getUuid()) ||
						(companyId != passwordPolicy.getCompanyId())) {
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

			query.append(_SQL_SELECT_PASSWORDPOLICY_WHERE);

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
				query.append(PasswordPolicyModelImpl.ORDER_BY_JPQL);
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
					list = (List<PasswordPolicy>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<PasswordPolicy>)QueryUtil.list(q,
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
	 * Returns the first password policy in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching password policy
	 * @throws com.liferay.portal.NoSuchPasswordPolicyException if a matching password policy could not be found
	 */
	@Override
	public PasswordPolicy findByUuid_C_First(String uuid, long companyId,
		OrderByComparator orderByComparator)
		throws NoSuchPasswordPolicyException {
		PasswordPolicy passwordPolicy = fetchByUuid_C_First(uuid, companyId,
				orderByComparator);

		if (passwordPolicy != null) {
			return passwordPolicy;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(", companyId=");
		msg.append(companyId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchPasswordPolicyException(msg.toString());
	}

	/**
	 * Returns the first password policy in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching password policy, or <code>null</code> if a matching password policy could not be found
	 */
	@Override
	public PasswordPolicy fetchByUuid_C_First(String uuid, long companyId,
		OrderByComparator orderByComparator) {
		List<PasswordPolicy> list = findByUuid_C(uuid, companyId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last password policy in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching password policy
	 * @throws com.liferay.portal.NoSuchPasswordPolicyException if a matching password policy could not be found
	 */
	@Override
	public PasswordPolicy findByUuid_C_Last(String uuid, long companyId,
		OrderByComparator orderByComparator)
		throws NoSuchPasswordPolicyException {
		PasswordPolicy passwordPolicy = fetchByUuid_C_Last(uuid, companyId,
				orderByComparator);

		if (passwordPolicy != null) {
			return passwordPolicy;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(", companyId=");
		msg.append(companyId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchPasswordPolicyException(msg.toString());
	}

	/**
	 * Returns the last password policy in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching password policy, or <code>null</code> if a matching password policy could not be found
	 */
	@Override
	public PasswordPolicy fetchByUuid_C_Last(String uuid, long companyId,
		OrderByComparator orderByComparator) {
		int count = countByUuid_C(uuid, companyId);

		if (count == 0) {
			return null;
		}

		List<PasswordPolicy> list = findByUuid_C(uuid, companyId, count - 1,
				count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the password policies before and after the current password policy in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param passwordPolicyId the primary key of the current password policy
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next password policy
	 * @throws com.liferay.portal.NoSuchPasswordPolicyException if a password policy with the primary key could not be found
	 */
	@Override
	public PasswordPolicy[] findByUuid_C_PrevAndNext(long passwordPolicyId,
		String uuid, long companyId, OrderByComparator orderByComparator)
		throws NoSuchPasswordPolicyException {
		PasswordPolicy passwordPolicy = findByPrimaryKey(passwordPolicyId);

		Session session = null;

		try {
			session = openSession();

			PasswordPolicy[] array = new PasswordPolicyImpl[3];

			array[0] = getByUuid_C_PrevAndNext(session, passwordPolicy, uuid,
					companyId, orderByComparator, true);

			array[1] = passwordPolicy;

			array[2] = getByUuid_C_PrevAndNext(session, passwordPolicy, uuid,
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

	protected PasswordPolicy getByUuid_C_PrevAndNext(Session session,
		PasswordPolicy passwordPolicy, String uuid, long companyId,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_PASSWORDPOLICY_WHERE);

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
			query.append(PasswordPolicyModelImpl.ORDER_BY_JPQL);
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
			Object[] values = orderByComparator.getOrderByConditionValues(passwordPolicy);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<PasswordPolicy> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the password policies that the user has permission to view where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching password policies that the user has permission to view
	 */
	@Override
	public List<PasswordPolicy> filterFindByUuid_C(String uuid, long companyId) {
		return filterFindByUuid_C(uuid, companyId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the password policies that the user has permission to view where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.PasswordPolicyModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of password policies
	 * @param end the upper bound of the range of password policies (not inclusive)
	 * @return the range of matching password policies that the user has permission to view
	 */
	@Override
	public List<PasswordPolicy> filterFindByUuid_C(String uuid, long companyId,
		int start, int end) {
		return filterFindByUuid_C(uuid, companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the password policies that the user has permissions to view where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.PasswordPolicyModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of password policies
	 * @param end the upper bound of the range of password policies (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching password policies that the user has permission to view
	 */
	@Override
	public List<PasswordPolicy> filterFindByUuid_C(String uuid, long companyId,
		int start, int end, OrderByComparator orderByComparator) {
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
			query.append(_FILTER_SQL_SELECT_PASSWORDPOLICY_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_PASSWORDPOLICY_NO_INLINE_DISTINCT_WHERE_1);
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
			query.append(_FILTER_SQL_SELECT_PASSWORDPOLICY_NO_INLINE_DISTINCT_WHERE_2);
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
				query.append(PasswordPolicyModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(PasswordPolicyModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				PasswordPolicy.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				q.addEntity(_FILTER_ENTITY_ALIAS, PasswordPolicyImpl.class);
			}
			else {
				q.addEntity(_FILTER_ENTITY_TABLE, PasswordPolicyImpl.class);
			}

			QueryPos qPos = QueryPos.getInstance(q);

			if (bindUuid) {
				qPos.add(uuid);
			}

			qPos.add(companyId);

			return (List<PasswordPolicy>)QueryUtil.list(q, getDialect(), start,
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
	 * Returns the password policies before and after the current password policy in the ordered set of password policies that the user has permission to view where uuid = &#63; and companyId = &#63;.
	 *
	 * @param passwordPolicyId the primary key of the current password policy
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next password policy
	 * @throws com.liferay.portal.NoSuchPasswordPolicyException if a password policy with the primary key could not be found
	 */
	@Override
	public PasswordPolicy[] filterFindByUuid_C_PrevAndNext(
		long passwordPolicyId, String uuid, long companyId,
		OrderByComparator orderByComparator)
		throws NoSuchPasswordPolicyException {
		if (!InlineSQLHelperUtil.isEnabled()) {
			return findByUuid_C_PrevAndNext(passwordPolicyId, uuid, companyId,
				orderByComparator);
		}

		PasswordPolicy passwordPolicy = findByPrimaryKey(passwordPolicyId);

		Session session = null;

		try {
			session = openSession();

			PasswordPolicy[] array = new PasswordPolicyImpl[3];

			array[0] = filterGetByUuid_C_PrevAndNext(session, passwordPolicy,
					uuid, companyId, orderByComparator, true);

			array[1] = passwordPolicy;

			array[2] = filterGetByUuid_C_PrevAndNext(session, passwordPolicy,
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

	protected PasswordPolicy filterGetByUuid_C_PrevAndNext(Session session,
		PasswordPolicy passwordPolicy, String uuid, long companyId,
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
			query.append(_FILTER_SQL_SELECT_PASSWORDPOLICY_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_PASSWORDPOLICY_NO_INLINE_DISTINCT_WHERE_1);
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
			query.append(_FILTER_SQL_SELECT_PASSWORDPOLICY_NO_INLINE_DISTINCT_WHERE_2);
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
				query.append(PasswordPolicyModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(PasswordPolicyModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				PasswordPolicy.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		SQLQuery q = session.createSynchronizedSQLQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		if (getDB().isSupportsInlineDistinct()) {
			q.addEntity(_FILTER_ENTITY_ALIAS, PasswordPolicyImpl.class);
		}
		else {
			q.addEntity(_FILTER_ENTITY_TABLE, PasswordPolicyImpl.class);
		}

		QueryPos qPos = QueryPos.getInstance(q);

		if (bindUuid) {
			qPos.add(uuid);
		}

		qPos.add(companyId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(passwordPolicy);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<PasswordPolicy> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the password policies where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	@Override
	public void removeByUuid_C(String uuid, long companyId) {
		for (PasswordPolicy passwordPolicy : findByUuid_C(uuid, companyId,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(passwordPolicy);
		}
	}

	/**
	 * Returns the number of password policies where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching password policies
	 */
	@Override
	public int countByUuid_C(String uuid, long companyId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_UUID_C;

		Object[] finderArgs = new Object[] { uuid, companyId };

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs,
				this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_PASSWORDPOLICY_WHERE);

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

	/**
	 * Returns the number of password policies that the user has permission to view where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching password policies that the user has permission to view
	 */
	@Override
	public int filterCountByUuid_C(String uuid, long companyId) {
		if (!InlineSQLHelperUtil.isEnabled()) {
			return countByUuid_C(uuid, companyId);
		}

		StringBundler query = new StringBundler(3);

		query.append(_FILTER_SQL_COUNT_PASSWORDPOLICY_WHERE);

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
				PasswordPolicy.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

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

	private static final String _FINDER_COLUMN_UUID_C_UUID_1 = "passwordPolicy.uuid IS NULL AND ";
	private static final String _FINDER_COLUMN_UUID_C_UUID_2 = "passwordPolicy.uuid = ? AND ";
	private static final String _FINDER_COLUMN_UUID_C_UUID_3 = "(passwordPolicy.uuid IS NULL OR passwordPolicy.uuid = '') AND ";
	private static final String _FINDER_COLUMN_UUID_C_UUID_1_SQL = "passwordPolicy.uuid_ IS NULL AND ";
	private static final String _FINDER_COLUMN_UUID_C_UUID_2_SQL = "passwordPolicy.uuid_ = ? AND ";
	private static final String _FINDER_COLUMN_UUID_C_UUID_3_SQL = "(passwordPolicy.uuid_ IS NULL OR passwordPolicy.uuid_ = '') AND ";
	private static final String _FINDER_COLUMN_UUID_C_COMPANYID_2 = "passwordPolicy.companyId = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_COMPANYID =
		new FinderPath(PasswordPolicyModelImpl.ENTITY_CACHE_ENABLED,
			PasswordPolicyModelImpl.FINDER_CACHE_ENABLED,
			PasswordPolicyImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByCompanyId",
			new String[] {
				Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID =
		new FinderPath(PasswordPolicyModelImpl.ENTITY_CACHE_ENABLED,
			PasswordPolicyModelImpl.FINDER_CACHE_ENABLED,
			PasswordPolicyImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByCompanyId",
			new String[] { Long.class.getName() },
			PasswordPolicyModelImpl.COMPANYID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_COMPANYID = new FinderPath(PasswordPolicyModelImpl.ENTITY_CACHE_ENABLED,
			PasswordPolicyModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByCompanyId",
			new String[] { Long.class.getName() });

	/**
	 * Returns all the password policies where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching password policies
	 */
	@Override
	public List<PasswordPolicy> findByCompanyId(long companyId) {
		return findByCompanyId(companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the password policies where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.PasswordPolicyModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of password policies
	 * @param end the upper bound of the range of password policies (not inclusive)
	 * @return the range of matching password policies
	 */
	@Override
	public List<PasswordPolicy> findByCompanyId(long companyId, int start,
		int end) {
		return findByCompanyId(companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the password policies where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.PasswordPolicyModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of password policies
	 * @param end the upper bound of the range of password policies (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching password policies
	 */
	@Override
	public List<PasswordPolicy> findByCompanyId(long companyId, int start,
		int end, OrderByComparator orderByComparator) {
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

		List<PasswordPolicy> list = (List<PasswordPolicy>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (PasswordPolicy passwordPolicy : list) {
				if ((companyId != passwordPolicy.getCompanyId())) {
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

			query.append(_SQL_SELECT_PASSWORDPOLICY_WHERE);

			query.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(PasswordPolicyModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				if (!pagination) {
					list = (List<PasswordPolicy>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<PasswordPolicy>)QueryUtil.list(q,
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
	 * Returns the first password policy in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching password policy
	 * @throws com.liferay.portal.NoSuchPasswordPolicyException if a matching password policy could not be found
	 */
	@Override
	public PasswordPolicy findByCompanyId_First(long companyId,
		OrderByComparator orderByComparator)
		throws NoSuchPasswordPolicyException {
		PasswordPolicy passwordPolicy = fetchByCompanyId_First(companyId,
				orderByComparator);

		if (passwordPolicy != null) {
			return passwordPolicy;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("companyId=");
		msg.append(companyId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchPasswordPolicyException(msg.toString());
	}

	/**
	 * Returns the first password policy in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching password policy, or <code>null</code> if a matching password policy could not be found
	 */
	@Override
	public PasswordPolicy fetchByCompanyId_First(long companyId,
		OrderByComparator orderByComparator) {
		List<PasswordPolicy> list = findByCompanyId(companyId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last password policy in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching password policy
	 * @throws com.liferay.portal.NoSuchPasswordPolicyException if a matching password policy could not be found
	 */
	@Override
	public PasswordPolicy findByCompanyId_Last(long companyId,
		OrderByComparator orderByComparator)
		throws NoSuchPasswordPolicyException {
		PasswordPolicy passwordPolicy = fetchByCompanyId_Last(companyId,
				orderByComparator);

		if (passwordPolicy != null) {
			return passwordPolicy;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("companyId=");
		msg.append(companyId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchPasswordPolicyException(msg.toString());
	}

	/**
	 * Returns the last password policy in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching password policy, or <code>null</code> if a matching password policy could not be found
	 */
	@Override
	public PasswordPolicy fetchByCompanyId_Last(long companyId,
		OrderByComparator orderByComparator) {
		int count = countByCompanyId(companyId);

		if (count == 0) {
			return null;
		}

		List<PasswordPolicy> list = findByCompanyId(companyId, count - 1,
				count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the password policies before and after the current password policy in the ordered set where companyId = &#63;.
	 *
	 * @param passwordPolicyId the primary key of the current password policy
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next password policy
	 * @throws com.liferay.portal.NoSuchPasswordPolicyException if a password policy with the primary key could not be found
	 */
	@Override
	public PasswordPolicy[] findByCompanyId_PrevAndNext(long passwordPolicyId,
		long companyId, OrderByComparator orderByComparator)
		throws NoSuchPasswordPolicyException {
		PasswordPolicy passwordPolicy = findByPrimaryKey(passwordPolicyId);

		Session session = null;

		try {
			session = openSession();

			PasswordPolicy[] array = new PasswordPolicyImpl[3];

			array[0] = getByCompanyId_PrevAndNext(session, passwordPolicy,
					companyId, orderByComparator, true);

			array[1] = passwordPolicy;

			array[2] = getByCompanyId_PrevAndNext(session, passwordPolicy,
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

	protected PasswordPolicy getByCompanyId_PrevAndNext(Session session,
		PasswordPolicy passwordPolicy, long companyId,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_PASSWORDPOLICY_WHERE);

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
			query.append(PasswordPolicyModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(companyId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(passwordPolicy);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<PasswordPolicy> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Returns all the password policies that the user has permission to view where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching password policies that the user has permission to view
	 */
	@Override
	public List<PasswordPolicy> filterFindByCompanyId(long companyId) {
		return filterFindByCompanyId(companyId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the password policies that the user has permission to view where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.PasswordPolicyModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of password policies
	 * @param end the upper bound of the range of password policies (not inclusive)
	 * @return the range of matching password policies that the user has permission to view
	 */
	@Override
	public List<PasswordPolicy> filterFindByCompanyId(long companyId,
		int start, int end) {
		return filterFindByCompanyId(companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the password policies that the user has permissions to view where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.PasswordPolicyModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of password policies
	 * @param end the upper bound of the range of password policies (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching password policies that the user has permission to view
	 */
	@Override
	public List<PasswordPolicy> filterFindByCompanyId(long companyId,
		int start, int end, OrderByComparator orderByComparator) {
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
			query.append(_FILTER_SQL_SELECT_PASSWORDPOLICY_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_PASSWORDPOLICY_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_PASSWORDPOLICY_NO_INLINE_DISTINCT_WHERE_2);
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
				query.append(PasswordPolicyModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(PasswordPolicyModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				PasswordPolicy.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			if (getDB().isSupportsInlineDistinct()) {
				q.addEntity(_FILTER_ENTITY_ALIAS, PasswordPolicyImpl.class);
			}
			else {
				q.addEntity(_FILTER_ENTITY_TABLE, PasswordPolicyImpl.class);
			}

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(companyId);

			return (List<PasswordPolicy>)QueryUtil.list(q, getDialect(), start,
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
	 * Returns the password policies before and after the current password policy in the ordered set of password policies that the user has permission to view where companyId = &#63;.
	 *
	 * @param passwordPolicyId the primary key of the current password policy
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next password policy
	 * @throws com.liferay.portal.NoSuchPasswordPolicyException if a password policy with the primary key could not be found
	 */
	@Override
	public PasswordPolicy[] filterFindByCompanyId_PrevAndNext(
		long passwordPolicyId, long companyId,
		OrderByComparator orderByComparator)
		throws NoSuchPasswordPolicyException {
		if (!InlineSQLHelperUtil.isEnabled()) {
			return findByCompanyId_PrevAndNext(passwordPolicyId, companyId,
				orderByComparator);
		}

		PasswordPolicy passwordPolicy = findByPrimaryKey(passwordPolicyId);

		Session session = null;

		try {
			session = openSession();

			PasswordPolicy[] array = new PasswordPolicyImpl[3];

			array[0] = filterGetByCompanyId_PrevAndNext(session,
					passwordPolicy, companyId, orderByComparator, true);

			array[1] = passwordPolicy;

			array[2] = filterGetByCompanyId_PrevAndNext(session,
					passwordPolicy, companyId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected PasswordPolicy filterGetByCompanyId_PrevAndNext(Session session,
		PasswordPolicy passwordPolicy, long companyId,
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
			query.append(_FILTER_SQL_SELECT_PASSWORDPOLICY_WHERE);
		}
		else {
			query.append(_FILTER_SQL_SELECT_PASSWORDPOLICY_NO_INLINE_DISTINCT_WHERE_1);
		}

		query.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

		if (!getDB().isSupportsInlineDistinct()) {
			query.append(_FILTER_SQL_SELECT_PASSWORDPOLICY_NO_INLINE_DISTINCT_WHERE_2);
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
				query.append(PasswordPolicyModelImpl.ORDER_BY_JPQL);
			}
			else {
				query.append(PasswordPolicyModelImpl.ORDER_BY_SQL);
			}
		}

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				PasswordPolicy.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

		SQLQuery q = session.createSynchronizedSQLQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		if (getDB().isSupportsInlineDistinct()) {
			q.addEntity(_FILTER_ENTITY_ALIAS, PasswordPolicyImpl.class);
		}
		else {
			q.addEntity(_FILTER_ENTITY_TABLE, PasswordPolicyImpl.class);
		}

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(companyId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(passwordPolicy);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<PasswordPolicy> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the password policies where companyId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 */
	@Override
	public void removeByCompanyId(long companyId) {
		for (PasswordPolicy passwordPolicy : findByCompanyId(companyId,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(passwordPolicy);
		}
	}

	/**
	 * Returns the number of password policies where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching password policies
	 */
	@Override
	public int countByCompanyId(long companyId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_COMPANYID;

		Object[] finderArgs = new Object[] { companyId };

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs,
				this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_PASSWORDPOLICY_WHERE);

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

	/**
	 * Returns the number of password policies that the user has permission to view where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching password policies that the user has permission to view
	 */
	@Override
	public int filterCountByCompanyId(long companyId) {
		if (!InlineSQLHelperUtil.isEnabled()) {
			return countByCompanyId(companyId);
		}

		StringBundler query = new StringBundler(2);

		query.append(_FILTER_SQL_COUNT_PASSWORDPOLICY_WHERE);

		query.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

		String sql = InlineSQLHelperUtil.replacePermissionCheck(query.toString(),
				PasswordPolicy.class.getName(),
				_FILTER_ENTITY_TABLE_FILTER_PK_COLUMN);

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

	private static final String _FINDER_COLUMN_COMPANYID_COMPANYID_2 = "passwordPolicy.companyId = ?";
	public static final FinderPath FINDER_PATH_FETCH_BY_C_DP = new FinderPath(PasswordPolicyModelImpl.ENTITY_CACHE_ENABLED,
			PasswordPolicyModelImpl.FINDER_CACHE_ENABLED,
			PasswordPolicyImpl.class, FINDER_CLASS_NAME_ENTITY, "fetchByC_DP",
			new String[] { Long.class.getName(), Boolean.class.getName() },
			PasswordPolicyModelImpl.COMPANYID_COLUMN_BITMASK |
			PasswordPolicyModelImpl.DEFAULTPOLICY_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_C_DP = new FinderPath(PasswordPolicyModelImpl.ENTITY_CACHE_ENABLED,
			PasswordPolicyModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_DP",
			new String[] { Long.class.getName(), Boolean.class.getName() });

	/**
	 * Returns the password policy where companyId = &#63; and defaultPolicy = &#63; or throws a {@link com.liferay.portal.NoSuchPasswordPolicyException} if it could not be found.
	 *
	 * @param companyId the company ID
	 * @param defaultPolicy the default policy
	 * @return the matching password policy
	 * @throws com.liferay.portal.NoSuchPasswordPolicyException if a matching password policy could not be found
	 */
	@Override
	public PasswordPolicy findByC_DP(long companyId, boolean defaultPolicy)
		throws NoSuchPasswordPolicyException {
		PasswordPolicy passwordPolicy = fetchByC_DP(companyId, defaultPolicy);

		if (passwordPolicy == null) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("companyId=");
			msg.append(companyId);

			msg.append(", defaultPolicy=");
			msg.append(defaultPolicy);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchPasswordPolicyException(msg.toString());
		}

		return passwordPolicy;
	}

	/**
	 * Returns the password policy where companyId = &#63; and defaultPolicy = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param companyId the company ID
	 * @param defaultPolicy the default policy
	 * @return the matching password policy, or <code>null</code> if a matching password policy could not be found
	 */
	@Override
	public PasswordPolicy fetchByC_DP(long companyId, boolean defaultPolicy) {
		return fetchByC_DP(companyId, defaultPolicy, true);
	}

	/**
	 * Returns the password policy where companyId = &#63; and defaultPolicy = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param companyId the company ID
	 * @param defaultPolicy the default policy
	 * @param retrieveFromCache whether to use the finder cache
	 * @return the matching password policy, or <code>null</code> if a matching password policy could not be found
	 */
	@Override
	public PasswordPolicy fetchByC_DP(long companyId, boolean defaultPolicy,
		boolean retrieveFromCache) {
		Object[] finderArgs = new Object[] { companyId, defaultPolicy };

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_C_DP,
					finderArgs, this);
		}

		if (result instanceof PasswordPolicy) {
			PasswordPolicy passwordPolicy = (PasswordPolicy)result;

			if ((companyId != passwordPolicy.getCompanyId()) ||
					(defaultPolicy != passwordPolicy.getDefaultPolicy())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_SELECT_PASSWORDPOLICY_WHERE);

			query.append(_FINDER_COLUMN_C_DP_COMPANYID_2);

			query.append(_FINDER_COLUMN_C_DP_DEFAULTPOLICY_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				qPos.add(defaultPolicy);

				List<PasswordPolicy> list = q.list();

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_DP,
						finderArgs, list);
				}
				else {
					if ((list.size() > 1) && _log.isWarnEnabled()) {
						_log.warn(
							"PasswordPolicyPersistenceImpl.fetchByC_DP(long, boolean, boolean) with parameters (" +
							StringUtil.merge(finderArgs) +
							") yields a result set with more than 1 result. This violates the logical unique restriction. There is no order guarantee on which result is returned by this finder.");
					}

					PasswordPolicy passwordPolicy = list.get(0);

					result = passwordPolicy;

					cacheResult(passwordPolicy);

					if ((passwordPolicy.getCompanyId() != companyId) ||
							(passwordPolicy.getDefaultPolicy() != defaultPolicy)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_DP,
							finderArgs, passwordPolicy);
					}
				}
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_C_DP,
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
			return (PasswordPolicy)result;
		}
	}

	/**
	 * Removes the password policy where companyId = &#63; and defaultPolicy = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param defaultPolicy the default policy
	 * @return the password policy that was removed
	 */
	@Override
	public PasswordPolicy removeByC_DP(long companyId, boolean defaultPolicy)
		throws NoSuchPasswordPolicyException {
		PasswordPolicy passwordPolicy = findByC_DP(companyId, defaultPolicy);

		return remove(passwordPolicy);
	}

	/**
	 * Returns the number of password policies where companyId = &#63; and defaultPolicy = &#63;.
	 *
	 * @param companyId the company ID
	 * @param defaultPolicy the default policy
	 * @return the number of matching password policies
	 */
	@Override
	public int countByC_DP(long companyId, boolean defaultPolicy) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_C_DP;

		Object[] finderArgs = new Object[] { companyId, defaultPolicy };

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs,
				this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_PASSWORDPOLICY_WHERE);

			query.append(_FINDER_COLUMN_C_DP_COMPANYID_2);

			query.append(_FINDER_COLUMN_C_DP_DEFAULTPOLICY_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				qPos.add(defaultPolicy);

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

	private static final String _FINDER_COLUMN_C_DP_COMPANYID_2 = "passwordPolicy.companyId = ? AND ";
	private static final String _FINDER_COLUMN_C_DP_DEFAULTPOLICY_2 = "passwordPolicy.defaultPolicy = ?";
	public static final FinderPath FINDER_PATH_FETCH_BY_C_N = new FinderPath(PasswordPolicyModelImpl.ENTITY_CACHE_ENABLED,
			PasswordPolicyModelImpl.FINDER_CACHE_ENABLED,
			PasswordPolicyImpl.class, FINDER_CLASS_NAME_ENTITY, "fetchByC_N",
			new String[] { Long.class.getName(), String.class.getName() },
			PasswordPolicyModelImpl.COMPANYID_COLUMN_BITMASK |
			PasswordPolicyModelImpl.NAME_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_C_N = new FinderPath(PasswordPolicyModelImpl.ENTITY_CACHE_ENABLED,
			PasswordPolicyModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_N",
			new String[] { Long.class.getName(), String.class.getName() });

	/**
	 * Returns the password policy where companyId = &#63; and name = &#63; or throws a {@link com.liferay.portal.NoSuchPasswordPolicyException} if it could not be found.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @return the matching password policy
	 * @throws com.liferay.portal.NoSuchPasswordPolicyException if a matching password policy could not be found
	 */
	@Override
	public PasswordPolicy findByC_N(long companyId, String name)
		throws NoSuchPasswordPolicyException {
		PasswordPolicy passwordPolicy = fetchByC_N(companyId, name);

		if (passwordPolicy == null) {
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

			throw new NoSuchPasswordPolicyException(msg.toString());
		}

		return passwordPolicy;
	}

	/**
	 * Returns the password policy where companyId = &#63; and name = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @return the matching password policy, or <code>null</code> if a matching password policy could not be found
	 */
	@Override
	public PasswordPolicy fetchByC_N(long companyId, String name) {
		return fetchByC_N(companyId, name, true);
	}

	/**
	 * Returns the password policy where companyId = &#63; and name = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @param retrieveFromCache whether to use the finder cache
	 * @return the matching password policy, or <code>null</code> if a matching password policy could not be found
	 */
	@Override
	public PasswordPolicy fetchByC_N(long companyId, String name,
		boolean retrieveFromCache) {
		Object[] finderArgs = new Object[] { companyId, name };

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_C_N,
					finderArgs, this);
		}

		if (result instanceof PasswordPolicy) {
			PasswordPolicy passwordPolicy = (PasswordPolicy)result;

			if ((companyId != passwordPolicy.getCompanyId()) ||
					!Validator.equals(name, passwordPolicy.getName())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_SELECT_PASSWORDPOLICY_WHERE);

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

				List<PasswordPolicy> list = q.list();

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_N,
						finderArgs, list);
				}
				else {
					PasswordPolicy passwordPolicy = list.get(0);

					result = passwordPolicy;

					cacheResult(passwordPolicy);

					if ((passwordPolicy.getCompanyId() != companyId) ||
							(passwordPolicy.getName() == null) ||
							!passwordPolicy.getName().equals(name)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_N,
							finderArgs, passwordPolicy);
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
			return (PasswordPolicy)result;
		}
	}

	/**
	 * Removes the password policy where companyId = &#63; and name = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @return the password policy that was removed
	 */
	@Override
	public PasswordPolicy removeByC_N(long companyId, String name)
		throws NoSuchPasswordPolicyException {
		PasswordPolicy passwordPolicy = findByC_N(companyId, name);

		return remove(passwordPolicy);
	}

	/**
	 * Returns the number of password policies where companyId = &#63; and name = &#63;.
	 *
	 * @param companyId the company ID
	 * @param name the name
	 * @return the number of matching password policies
	 */
	@Override
	public int countByC_N(long companyId, String name) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_C_N;

		Object[] finderArgs = new Object[] { companyId, name };

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs,
				this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_PASSWORDPOLICY_WHERE);

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

	private static final String _FINDER_COLUMN_C_N_COMPANYID_2 = "passwordPolicy.companyId = ? AND ";
	private static final String _FINDER_COLUMN_C_N_NAME_1 = "passwordPolicy.name IS NULL";
	private static final String _FINDER_COLUMN_C_N_NAME_2 = "passwordPolicy.name = ?";
	private static final String _FINDER_COLUMN_C_N_NAME_3 = "(passwordPolicy.name IS NULL OR passwordPolicy.name = '')";

	public PasswordPolicyPersistenceImpl() {
		setModelClass(PasswordPolicy.class);
	}

	/**
	 * Caches the password policy in the entity cache if it is enabled.
	 *
	 * @param passwordPolicy the password policy
	 */
	@Override
	public void cacheResult(PasswordPolicy passwordPolicy) {
		EntityCacheUtil.putResult(PasswordPolicyModelImpl.ENTITY_CACHE_ENABLED,
			PasswordPolicyImpl.class, passwordPolicy.getPrimaryKey(),
			passwordPolicy);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_DP,
			new Object[] {
				passwordPolicy.getCompanyId(), passwordPolicy.getDefaultPolicy()
			}, passwordPolicy);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_N,
			new Object[] { passwordPolicy.getCompanyId(), passwordPolicy.getName() },
			passwordPolicy);

		passwordPolicy.resetOriginalValues();
	}

	/**
	 * Caches the password policies in the entity cache if it is enabled.
	 *
	 * @param passwordPolicies the password policies
	 */
	@Override
	public void cacheResult(List<PasswordPolicy> passwordPolicies) {
		for (PasswordPolicy passwordPolicy : passwordPolicies) {
			if (EntityCacheUtil.getResult(
						PasswordPolicyModelImpl.ENTITY_CACHE_ENABLED,
						PasswordPolicyImpl.class, passwordPolicy.getPrimaryKey()) == null) {
				cacheResult(passwordPolicy);
			}
			else {
				passwordPolicy.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all password policies.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		if (_HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			CacheRegistryUtil.clear(PasswordPolicyImpl.class.getName());
		}

		EntityCacheUtil.clearCache(PasswordPolicyImpl.class);

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the password policy.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(PasswordPolicy passwordPolicy) {
		EntityCacheUtil.removeResult(PasswordPolicyModelImpl.ENTITY_CACHE_ENABLED,
			PasswordPolicyImpl.class, passwordPolicy.getPrimaryKey());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache(passwordPolicy);
	}

	@Override
	public void clearCache(List<PasswordPolicy> passwordPolicies) {
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (PasswordPolicy passwordPolicy : passwordPolicies) {
			EntityCacheUtil.removeResult(PasswordPolicyModelImpl.ENTITY_CACHE_ENABLED,
				PasswordPolicyImpl.class, passwordPolicy.getPrimaryKey());

			clearUniqueFindersCache(passwordPolicy);
		}
	}

	protected void cacheUniqueFindersCache(PasswordPolicy passwordPolicy) {
		if (passwordPolicy.isNew()) {
			Object[] args = new Object[] {
					passwordPolicy.getCompanyId(),
					passwordPolicy.getDefaultPolicy()
				};

			FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_C_DP, args,
				Long.valueOf(1));
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_DP, args,
				passwordPolicy);

			args = new Object[] {
					passwordPolicy.getCompanyId(), passwordPolicy.getName()
				};

			FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_C_N, args,
				Long.valueOf(1));
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_N, args,
				passwordPolicy);
		}
		else {
			PasswordPolicyModelImpl passwordPolicyModelImpl = (PasswordPolicyModelImpl)passwordPolicy;

			if ((passwordPolicyModelImpl.getColumnBitmask() &
					FINDER_PATH_FETCH_BY_C_DP.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						passwordPolicy.getCompanyId(),
						passwordPolicy.getDefaultPolicy()
					};

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_C_DP, args,
					Long.valueOf(1));
				FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_DP, args,
					passwordPolicy);
			}

			if ((passwordPolicyModelImpl.getColumnBitmask() &
					FINDER_PATH_FETCH_BY_C_N.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						passwordPolicy.getCompanyId(), passwordPolicy.getName()
					};

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_C_N, args,
					Long.valueOf(1));
				FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_C_N, args,
					passwordPolicy);
			}
		}
	}

	protected void clearUniqueFindersCache(PasswordPolicy passwordPolicy) {
		PasswordPolicyModelImpl passwordPolicyModelImpl = (PasswordPolicyModelImpl)passwordPolicy;

		Object[] args = new Object[] {
				passwordPolicy.getCompanyId(), passwordPolicy.getDefaultPolicy()
			};

		FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_C_DP, args);
		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_C_DP, args);

		if ((passwordPolicyModelImpl.getColumnBitmask() &
				FINDER_PATH_FETCH_BY_C_DP.getColumnBitmask()) != 0) {
			args = new Object[] {
					passwordPolicyModelImpl.getOriginalCompanyId(),
					passwordPolicyModelImpl.getOriginalDefaultPolicy()
				};

			FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_C_DP, args);
			FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_C_DP, args);
		}

		args = new Object[] {
				passwordPolicy.getCompanyId(), passwordPolicy.getName()
			};

		FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_C_N, args);
		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_C_N, args);

		if ((passwordPolicyModelImpl.getColumnBitmask() &
				FINDER_PATH_FETCH_BY_C_N.getColumnBitmask()) != 0) {
			args = new Object[] {
					passwordPolicyModelImpl.getOriginalCompanyId(),
					passwordPolicyModelImpl.getOriginalName()
				};

			FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_C_N, args);
			FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_C_N, args);
		}
	}

	/**
	 * Creates a new password policy with the primary key. Does not add the password policy to the database.
	 *
	 * @param passwordPolicyId the primary key for the new password policy
	 * @return the new password policy
	 */
	@Override
	public PasswordPolicy create(long passwordPolicyId) {
		PasswordPolicy passwordPolicy = new PasswordPolicyImpl();

		passwordPolicy.setNew(true);
		passwordPolicy.setPrimaryKey(passwordPolicyId);

		String uuid = PortalUUIDUtil.generate();

		passwordPolicy.setUuid(uuid);

		return passwordPolicy;
	}

	/**
	 * Removes the password policy with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param passwordPolicyId the primary key of the password policy
	 * @return the password policy that was removed
	 * @throws com.liferay.portal.NoSuchPasswordPolicyException if a password policy with the primary key could not be found
	 */
	@Override
	public PasswordPolicy remove(long passwordPolicyId)
		throws NoSuchPasswordPolicyException {
		return remove((Serializable)passwordPolicyId);
	}

	/**
	 * Removes the password policy with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the password policy
	 * @return the password policy that was removed
	 * @throws com.liferay.portal.NoSuchPasswordPolicyException if a password policy with the primary key could not be found
	 */
	@Override
	public PasswordPolicy remove(Serializable primaryKey)
		throws NoSuchPasswordPolicyException {
		Session session = null;

		try {
			session = openSession();

			PasswordPolicy passwordPolicy = (PasswordPolicy)session.get(PasswordPolicyImpl.class,
					primaryKey);

			if (passwordPolicy == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchPasswordPolicyException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(passwordPolicy);
		}
		catch (NoSuchPasswordPolicyException nsee) {
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
	protected PasswordPolicy removeImpl(PasswordPolicy passwordPolicy) {
		passwordPolicy = toUnwrappedModel(passwordPolicy);

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(passwordPolicy)) {
				passwordPolicy = (PasswordPolicy)session.get(PasswordPolicyImpl.class,
						passwordPolicy.getPrimaryKeyObj());
			}

			if (passwordPolicy != null) {
				session.delete(passwordPolicy);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (passwordPolicy != null) {
			clearCache(passwordPolicy);
		}

		return passwordPolicy;
	}

	@Override
	public PasswordPolicy updateImpl(
		com.liferay.portal.model.PasswordPolicy passwordPolicy) {
		passwordPolicy = toUnwrappedModel(passwordPolicy);

		boolean isNew = passwordPolicy.isNew();

		PasswordPolicyModelImpl passwordPolicyModelImpl = (PasswordPolicyModelImpl)passwordPolicy;

		if (Validator.isNull(passwordPolicy.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			passwordPolicy.setUuid(uuid);
		}

		Session session = null;

		try {
			session = openSession();

			if (passwordPolicy.isNew()) {
				session.save(passwordPolicy);

				passwordPolicy.setNew(false);
			}
			else {
				session.merge(passwordPolicy);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (isNew || !PasswordPolicyModelImpl.COLUMN_BITMASK_ENABLED) {
			FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}

		else {
			if ((passwordPolicyModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						passwordPolicyModelImpl.getOriginalUuid()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_UUID, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID,
					args);

				args = new Object[] { passwordPolicyModelImpl.getUuid() };

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_UUID, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID,
					args);
			}

			if ((passwordPolicyModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						passwordPolicyModelImpl.getOriginalUuid(),
						passwordPolicyModelImpl.getOriginalCompanyId()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_UUID_C, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C,
					args);

				args = new Object[] {
						passwordPolicyModelImpl.getUuid(),
						passwordPolicyModelImpl.getCompanyId()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_UUID_C, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C,
					args);
			}

			if ((passwordPolicyModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						passwordPolicyModelImpl.getOriginalCompanyId()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_COMPANYID,
					args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID,
					args);

				args = new Object[] { passwordPolicyModelImpl.getCompanyId() };

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_COMPANYID,
					args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMPANYID,
					args);
			}
		}

		EntityCacheUtil.putResult(PasswordPolicyModelImpl.ENTITY_CACHE_ENABLED,
			PasswordPolicyImpl.class, passwordPolicy.getPrimaryKey(),
			passwordPolicy, false);

		clearUniqueFindersCache(passwordPolicy);
		cacheUniqueFindersCache(passwordPolicy);

		passwordPolicy.resetOriginalValues();

		return passwordPolicy;
	}

	protected PasswordPolicy toUnwrappedModel(PasswordPolicy passwordPolicy) {
		if (passwordPolicy instanceof PasswordPolicyImpl) {
			return passwordPolicy;
		}

		PasswordPolicyImpl passwordPolicyImpl = new PasswordPolicyImpl();

		passwordPolicyImpl.setNew(passwordPolicy.isNew());
		passwordPolicyImpl.setPrimaryKey(passwordPolicy.getPrimaryKey());

		passwordPolicyImpl.setMvccVersion(passwordPolicy.getMvccVersion());
		passwordPolicyImpl.setUuid(passwordPolicy.getUuid());
		passwordPolicyImpl.setPasswordPolicyId(passwordPolicy.getPasswordPolicyId());
		passwordPolicyImpl.setCompanyId(passwordPolicy.getCompanyId());
		passwordPolicyImpl.setUserId(passwordPolicy.getUserId());
		passwordPolicyImpl.setUserName(passwordPolicy.getUserName());
		passwordPolicyImpl.setCreateDate(passwordPolicy.getCreateDate());
		passwordPolicyImpl.setModifiedDate(passwordPolicy.getModifiedDate());
		passwordPolicyImpl.setDefaultPolicy(passwordPolicy.isDefaultPolicy());
		passwordPolicyImpl.setName(passwordPolicy.getName());
		passwordPolicyImpl.setDescription(passwordPolicy.getDescription());
		passwordPolicyImpl.setChangeable(passwordPolicy.isChangeable());
		passwordPolicyImpl.setChangeRequired(passwordPolicy.isChangeRequired());
		passwordPolicyImpl.setMinAge(passwordPolicy.getMinAge());
		passwordPolicyImpl.setCheckSyntax(passwordPolicy.isCheckSyntax());
		passwordPolicyImpl.setAllowDictionaryWords(passwordPolicy.isAllowDictionaryWords());
		passwordPolicyImpl.setMinAlphanumeric(passwordPolicy.getMinAlphanumeric());
		passwordPolicyImpl.setMinLength(passwordPolicy.getMinLength());
		passwordPolicyImpl.setMinLowerCase(passwordPolicy.getMinLowerCase());
		passwordPolicyImpl.setMinNumbers(passwordPolicy.getMinNumbers());
		passwordPolicyImpl.setMinSymbols(passwordPolicy.getMinSymbols());
		passwordPolicyImpl.setMinUpperCase(passwordPolicy.getMinUpperCase());
		passwordPolicyImpl.setRegex(passwordPolicy.getRegex());
		passwordPolicyImpl.setHistory(passwordPolicy.isHistory());
		passwordPolicyImpl.setHistoryCount(passwordPolicy.getHistoryCount());
		passwordPolicyImpl.setExpireable(passwordPolicy.isExpireable());
		passwordPolicyImpl.setMaxAge(passwordPolicy.getMaxAge());
		passwordPolicyImpl.setWarningTime(passwordPolicy.getWarningTime());
		passwordPolicyImpl.setGraceLimit(passwordPolicy.getGraceLimit());
		passwordPolicyImpl.setLockout(passwordPolicy.isLockout());
		passwordPolicyImpl.setMaxFailure(passwordPolicy.getMaxFailure());
		passwordPolicyImpl.setLockoutDuration(passwordPolicy.getLockoutDuration());
		passwordPolicyImpl.setRequireUnlock(passwordPolicy.isRequireUnlock());
		passwordPolicyImpl.setResetFailureCount(passwordPolicy.getResetFailureCount());
		passwordPolicyImpl.setResetTicketMaxAge(passwordPolicy.getResetTicketMaxAge());

		return passwordPolicyImpl;
	}

	/**
	 * Returns the password policy with the primary key or throws a {@link com.liferay.portal.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the password policy
	 * @return the password policy
	 * @throws com.liferay.portal.NoSuchPasswordPolicyException if a password policy with the primary key could not be found
	 */
	@Override
	public PasswordPolicy findByPrimaryKey(Serializable primaryKey)
		throws NoSuchPasswordPolicyException {
		PasswordPolicy passwordPolicy = fetchByPrimaryKey(primaryKey);

		if (passwordPolicy == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchPasswordPolicyException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				primaryKey);
		}

		return passwordPolicy;
	}

	/**
	 * Returns the password policy with the primary key or throws a {@link com.liferay.portal.NoSuchPasswordPolicyException} if it could not be found.
	 *
	 * @param passwordPolicyId the primary key of the password policy
	 * @return the password policy
	 * @throws com.liferay.portal.NoSuchPasswordPolicyException if a password policy with the primary key could not be found
	 */
	@Override
	public PasswordPolicy findByPrimaryKey(long passwordPolicyId)
		throws NoSuchPasswordPolicyException {
		return findByPrimaryKey((Serializable)passwordPolicyId);
	}

	/**
	 * Returns the password policy with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the password policy
	 * @return the password policy, or <code>null</code> if a password policy with the primary key could not be found
	 */
	@Override
	public PasswordPolicy fetchByPrimaryKey(Serializable primaryKey) {
		PasswordPolicy passwordPolicy = (PasswordPolicy)EntityCacheUtil.getResult(PasswordPolicyModelImpl.ENTITY_CACHE_ENABLED,
				PasswordPolicyImpl.class, primaryKey);

		if (passwordPolicy == _nullPasswordPolicy) {
			return null;
		}

		if (passwordPolicy == null) {
			Session session = null;

			try {
				session = openSession();

				passwordPolicy = (PasswordPolicy)session.get(PasswordPolicyImpl.class,
						primaryKey);

				if (passwordPolicy != null) {
					cacheResult(passwordPolicy);
				}
				else {
					EntityCacheUtil.putResult(PasswordPolicyModelImpl.ENTITY_CACHE_ENABLED,
						PasswordPolicyImpl.class, primaryKey,
						_nullPasswordPolicy);
				}
			}
			catch (Exception e) {
				EntityCacheUtil.removeResult(PasswordPolicyModelImpl.ENTITY_CACHE_ENABLED,
					PasswordPolicyImpl.class, primaryKey);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return passwordPolicy;
	}

	/**
	 * Returns the password policy with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param passwordPolicyId the primary key of the password policy
	 * @return the password policy, or <code>null</code> if a password policy with the primary key could not be found
	 */
	@Override
	public PasswordPolicy fetchByPrimaryKey(long passwordPolicyId) {
		return fetchByPrimaryKey((Serializable)passwordPolicyId);
	}

	@Override
	public Map<Serializable, PasswordPolicy> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {
		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, PasswordPolicy> map = new HashMap<Serializable, PasswordPolicy>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			PasswordPolicy passwordPolicy = fetchByPrimaryKey(primaryKey);

			if (passwordPolicy != null) {
				map.put(primaryKey, passwordPolicy);
			}

			return map;
		}

		Set<Serializable> uncachedPrimaryKeys = null;

		for (Serializable primaryKey : primaryKeys) {
			PasswordPolicy passwordPolicy = (PasswordPolicy)EntityCacheUtil.getResult(PasswordPolicyModelImpl.ENTITY_CACHE_ENABLED,
					PasswordPolicyImpl.class, primaryKey);

			if (passwordPolicy == null) {
				if (uncachedPrimaryKeys == null) {
					uncachedPrimaryKeys = new HashSet<Serializable>();
				}

				uncachedPrimaryKeys.add(primaryKey);
			}
			else {
				map.put(primaryKey, passwordPolicy);
			}
		}

		if (uncachedPrimaryKeys == null) {
			return map;
		}

		StringBundler query = new StringBundler((uncachedPrimaryKeys.size() * 2) +
				1);

		query.append(_SQL_SELECT_PASSWORDPOLICY_WHERE_PKS_IN);

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

			for (PasswordPolicy passwordPolicy : (List<PasswordPolicy>)q.list()) {
				map.put(passwordPolicy.getPrimaryKeyObj(), passwordPolicy);

				cacheResult(passwordPolicy);

				uncachedPrimaryKeys.remove(passwordPolicy.getPrimaryKeyObj());
			}

			for (Serializable primaryKey : uncachedPrimaryKeys) {
				EntityCacheUtil.putResult(PasswordPolicyModelImpl.ENTITY_CACHE_ENABLED,
					PasswordPolicyImpl.class, primaryKey, _nullPasswordPolicy);
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
	 * Returns all the password policies.
	 *
	 * @return the password policies
	 */
	@Override
	public List<PasswordPolicy> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the password policies.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.PasswordPolicyModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of password policies
	 * @param end the upper bound of the range of password policies (not inclusive)
	 * @return the range of password policies
	 */
	@Override
	public List<PasswordPolicy> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the password policies.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.PasswordPolicyModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of password policies
	 * @param end the upper bound of the range of password policies (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of password policies
	 */
	@Override
	public List<PasswordPolicy> findAll(int start, int end,
		OrderByComparator orderByComparator) {
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

		List<PasswordPolicy> list = (List<PasswordPolicy>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 3));

				query.append(_SQL_SELECT_PASSWORDPOLICY);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_PASSWORDPOLICY;

				if (pagination) {
					sql = sql.concat(PasswordPolicyModelImpl.ORDER_BY_JPQL);
				}
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (!pagination) {
					list = (List<PasswordPolicy>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<PasswordPolicy>)QueryUtil.list(q,
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
	 * Removes all the password policies from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (PasswordPolicy passwordPolicy : findAll()) {
			remove(passwordPolicy);
		}
	}

	/**
	 * Returns the number of password policies.
	 *
	 * @return the number of password policies
	 */
	@Override
	public int countAll() {
		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_PASSWORDPOLICY);

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

	@Override
	protected Set<String> getBadColumnNames() {
		return _badColumnNames;
	}

	/**
	 * Initializes the password policy persistence.
	 */
	public void afterPropertiesSet() {
		String[] listenerClassNames = StringUtil.split(GetterUtil.getString(
					com.liferay.portal.util.PropsUtil.get(
						"value.object.listener.com.liferay.portal.model.PasswordPolicy")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<PasswordPolicy>> listenersList = new ArrayList<ModelListener<PasswordPolicy>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<PasswordPolicy>)InstanceFactory.newInstance(
							getClassLoader(), listenerClassName));
				}

				listeners = listenersList.toArray(new ModelListener[listenersList.size()]);
			}
			catch (Exception e) {
				_log.error(e);
			}
		}
	}

	public void destroy() {
		EntityCacheUtil.removeCache(PasswordPolicyImpl.class.getName());
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	private static final String _SQL_SELECT_PASSWORDPOLICY = "SELECT passwordPolicy FROM PasswordPolicy passwordPolicy";
	private static final String _SQL_SELECT_PASSWORDPOLICY_WHERE_PKS_IN = "SELECT passwordPolicy FROM PasswordPolicy passwordPolicy WHERE passwordPolicyId IN (";
	private static final String _SQL_SELECT_PASSWORDPOLICY_WHERE = "SELECT passwordPolicy FROM PasswordPolicy passwordPolicy WHERE ";
	private static final String _SQL_COUNT_PASSWORDPOLICY = "SELECT COUNT(passwordPolicy) FROM PasswordPolicy passwordPolicy";
	private static final String _SQL_COUNT_PASSWORDPOLICY_WHERE = "SELECT COUNT(passwordPolicy) FROM PasswordPolicy passwordPolicy WHERE ";
	private static final String _FILTER_ENTITY_TABLE_FILTER_PK_COLUMN = "passwordPolicy.passwordPolicyId";
	private static final String _FILTER_SQL_SELECT_PASSWORDPOLICY_WHERE = "SELECT DISTINCT {passwordPolicy.*} FROM PasswordPolicy passwordPolicy WHERE ";
	private static final String _FILTER_SQL_SELECT_PASSWORDPOLICY_NO_INLINE_DISTINCT_WHERE_1 =
		"SELECT {PasswordPolicy.*} FROM (SELECT DISTINCT passwordPolicy.passwordPolicyId FROM PasswordPolicy passwordPolicy WHERE ";
	private static final String _FILTER_SQL_SELECT_PASSWORDPOLICY_NO_INLINE_DISTINCT_WHERE_2 =
		") TEMP_TABLE INNER JOIN PasswordPolicy ON TEMP_TABLE.passwordPolicyId = PasswordPolicy.passwordPolicyId";
	private static final String _FILTER_SQL_COUNT_PASSWORDPOLICY_WHERE = "SELECT COUNT(DISTINCT passwordPolicy.passwordPolicyId) AS COUNT_VALUE FROM PasswordPolicy passwordPolicy WHERE ";
	private static final String _FILTER_ENTITY_ALIAS = "passwordPolicy";
	private static final String _FILTER_ENTITY_TABLE = "PasswordPolicy";
	private static final String _ORDER_BY_ENTITY_ALIAS = "passwordPolicy.";
	private static final String _ORDER_BY_ENTITY_TABLE = "PasswordPolicy.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No PasswordPolicy exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No PasswordPolicy exists with the key {";
	private static final boolean _HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE = com.liferay.portal.util.PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE;
	private static Log _log = LogFactoryUtil.getLog(PasswordPolicyPersistenceImpl.class);
	private static Set<String> _badColumnNames = SetUtil.fromArray(new String[] {
				"uuid"
			});
	private static PasswordPolicy _nullPasswordPolicy = new PasswordPolicyImpl() {
			@Override
			public Object clone() {
				return this;
			}

			@Override
			public CacheModel<PasswordPolicy> toCacheModel() {
				return _nullPasswordPolicyCacheModel;
			}
		};

	private static CacheModel<PasswordPolicy> _nullPasswordPolicyCacheModel = new NullCacheModel();

	private static class NullCacheModel implements CacheModel<PasswordPolicy>,
		MVCCModel {
		@Override
		public long getMvccVersion() {
			return 0;
		}

		@Override
		public void setMvccVersion(long mvccVersion) {
		}

		@Override
		public PasswordPolicy toEntityModel() {
			return _nullPasswordPolicy;
		}
	}
}