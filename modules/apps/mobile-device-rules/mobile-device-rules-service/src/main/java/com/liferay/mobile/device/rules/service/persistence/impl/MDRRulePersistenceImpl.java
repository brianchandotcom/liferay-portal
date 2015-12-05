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

package com.liferay.mobile.device.rules.service.persistence.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.mobile.device.rules.exception.NoSuchRuleException;
import com.liferay.mobile.device.rules.model.MDRRule;
import com.liferay.mobile.device.rules.model.impl.MDRRuleImpl;
import com.liferay.mobile.device.rules.model.impl.MDRRuleModelImpl;
import com.liferay.mobile.device.rules.service.persistence.MDRRulePersistence;

import com.liferay.portal.kernel.dao.orm.EntityCache;
import com.liferay.portal.kernel.dao.orm.FinderCache;
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
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;
import com.liferay.portal.model.CacheModel;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceContextThreadLocal;
import com.liferay.portal.service.persistence.CompanyProvider;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.spring.extender.service.ServiceReference;

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
 * The persistence implementation for the m d r rule service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Edward C. Han
 * @see MDRRulePersistence
 * @see com.liferay.mobile.device.rules.service.persistence.MDRRuleUtil
 * @generated
 */
@ProviderType
public class MDRRulePersistenceImpl extends BasePersistenceImpl<MDRRule>
	implements MDRRulePersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link MDRRuleUtil} to access the m d r rule persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = MDRRuleImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(MDRRuleModelImpl.ENTITY_CACHE_ENABLED,
			MDRRuleModelImpl.FINDER_CACHE_ENABLED, MDRRuleImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(MDRRuleModelImpl.ENTITY_CACHE_ENABLED,
			MDRRuleModelImpl.FINDER_CACHE_ENABLED, MDRRuleImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(MDRRuleModelImpl.ENTITY_CACHE_ENABLED,
			MDRRuleModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_UUID = new FinderPath(MDRRuleModelImpl.ENTITY_CACHE_ENABLED,
			MDRRuleModelImpl.FINDER_CACHE_ENABLED, MDRRuleImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUuid",
			new String[] {
				String.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID = new FinderPath(MDRRuleModelImpl.ENTITY_CACHE_ENABLED,
			MDRRuleModelImpl.FINDER_CACHE_ENABLED, MDRRuleImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid",
			new String[] { String.class.getName() },
			MDRRuleModelImpl.UUID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID = new FinderPath(MDRRuleModelImpl.ENTITY_CACHE_ENABLED,
			MDRRuleModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUuid",
			new String[] { String.class.getName() });

	/**
	 * Returns all the m d r rules where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching m d r rules
	 */
	@Override
	public List<MDRRule> findByUuid(String uuid) {
		return findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the m d r rules where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MDRRuleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of m d r rules
	 * @param end the upper bound of the range of m d r rules (not inclusive)
	 * @return the range of matching m d r rules
	 */
	@Override
	public List<MDRRule> findByUuid(String uuid, int start, int end) {
		return findByUuid(uuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the m d r rules where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MDRRuleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of m d r rules
	 * @param end the upper bound of the range of m d r rules (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching m d r rules
	 */
	@Override
	public List<MDRRule> findByUuid(String uuid, int start, int end,
		OrderByComparator<MDRRule> orderByComparator) {
		return findByUuid(uuid, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the m d r rules where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MDRRuleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of m d r rules
	 * @param end the upper bound of the range of m d r rules (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching m d r rules
	 */
	@Override
	public List<MDRRule> findByUuid(String uuid, int start, int end,
		OrderByComparator<MDRRule> orderByComparator, boolean retrieveFromCache) {
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

		List<MDRRule> list = null;

		if (retrieveFromCache) {
			list = (List<MDRRule>)finderCache.getResult(finderPath, finderArgs,
					this);

			if ((list != null) && !list.isEmpty()) {
				for (MDRRule mdrRule : list) {
					if (!Validator.equals(uuid, mdrRule.getUuid())) {
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

			query.append(_SQL_SELECT_MDRRULE_WHERE);

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
				query.append(MDRRuleModelImpl.ORDER_BY_JPQL);
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
					list = (List<MDRRule>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<MDRRule>)QueryUtil.list(q, getDialect(),
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
	 * Returns the first m d r rule in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching m d r rule
	 * @throws com.liferay.mobile.device.rules.NoSuchRuleException if a matching m d r rule could not be found
	 */
	@Override
	public MDRRule findByUuid_First(String uuid,
		OrderByComparator<MDRRule> orderByComparator)
		throws NoSuchRuleException {
		MDRRule mdrRule = fetchByUuid_First(uuid, orderByComparator);

		if (mdrRule != null) {
			return mdrRule;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchRuleException(msg.toString());
	}

	/**
	 * Returns the first m d r rule in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching m d r rule, or <code>null</code> if a matching m d r rule could not be found
	 */
	@Override
	public MDRRule fetchByUuid_First(String uuid,
		OrderByComparator<MDRRule> orderByComparator) {
		List<MDRRule> list = findByUuid(uuid, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last m d r rule in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching m d r rule
	 * @throws com.liferay.mobile.device.rules.NoSuchRuleException if a matching m d r rule could not be found
	 */
	@Override
	public MDRRule findByUuid_Last(String uuid,
		OrderByComparator<MDRRule> orderByComparator)
		throws NoSuchRuleException {
		MDRRule mdrRule = fetchByUuid_Last(uuid, orderByComparator);

		if (mdrRule != null) {
			return mdrRule;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchRuleException(msg.toString());
	}

	/**
	 * Returns the last m d r rule in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching m d r rule, or <code>null</code> if a matching m d r rule could not be found
	 */
	@Override
	public MDRRule fetchByUuid_Last(String uuid,
		OrderByComparator<MDRRule> orderByComparator) {
		int count = countByUuid(uuid);

		if (count == 0) {
			return null;
		}

		List<MDRRule> list = findByUuid(uuid, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the m d r rules before and after the current m d r rule in the ordered set where uuid = &#63;.
	 *
	 * @param ruleId the primary key of the current m d r rule
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next m d r rule
	 * @throws com.liferay.mobile.device.rules.NoSuchRuleException if a m d r rule with the primary key could not be found
	 */
	@Override
	public MDRRule[] findByUuid_PrevAndNext(long ruleId, String uuid,
		OrderByComparator<MDRRule> orderByComparator)
		throws NoSuchRuleException {
		MDRRule mdrRule = findByPrimaryKey(ruleId);

		Session session = null;

		try {
			session = openSession();

			MDRRule[] array = new MDRRuleImpl[3];

			array[0] = getByUuid_PrevAndNext(session, mdrRule, uuid,
					orderByComparator, true);

			array[1] = mdrRule;

			array[2] = getByUuid_PrevAndNext(session, mdrRule, uuid,
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

	protected MDRRule getByUuid_PrevAndNext(Session session, MDRRule mdrRule,
		String uuid, OrderByComparator<MDRRule> orderByComparator,
		boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_MDRRULE_WHERE);

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
			query.append(MDRRuleModelImpl.ORDER_BY_JPQL);
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
			Object[] values = orderByComparator.getOrderByConditionValues(mdrRule);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<MDRRule> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the m d r rules where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	@Override
	public void removeByUuid(String uuid) {
		for (MDRRule mdrRule : findByUuid(uuid, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS, null)) {
			remove(mdrRule);
		}
	}

	/**
	 * Returns the number of m d r rules where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching m d r rules
	 */
	@Override
	public int countByUuid(String uuid) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_UUID;

		Object[] finderArgs = new Object[] { uuid };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_MDRRULE_WHERE);

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

	private static final String _FINDER_COLUMN_UUID_UUID_1 = "mdrRule.uuid IS NULL";
	private static final String _FINDER_COLUMN_UUID_UUID_2 = "mdrRule.uuid = ?";
	private static final String _FINDER_COLUMN_UUID_UUID_3 = "(mdrRule.uuid IS NULL OR mdrRule.uuid = '')";
	public static final FinderPath FINDER_PATH_FETCH_BY_UUID_G = new FinderPath(MDRRuleModelImpl.ENTITY_CACHE_ENABLED,
			MDRRuleModelImpl.FINDER_CACHE_ENABLED, MDRRuleImpl.class,
			FINDER_CLASS_NAME_ENTITY, "fetchByUUID_G",
			new String[] { String.class.getName(), Long.class.getName() },
			MDRRuleModelImpl.UUID_COLUMN_BITMASK |
			MDRRuleModelImpl.GROUPID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID_G = new FinderPath(MDRRuleModelImpl.ENTITY_CACHE_ENABLED,
			MDRRuleModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUUID_G",
			new String[] { String.class.getName(), Long.class.getName() });

	/**
	 * Returns the m d r rule where uuid = &#63; and groupId = &#63; or throws a {@link com.liferay.mobile.device.rules.NoSuchRuleException} if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching m d r rule
	 * @throws com.liferay.mobile.device.rules.NoSuchRuleException if a matching m d r rule could not be found
	 */
	@Override
	public MDRRule findByUUID_G(String uuid, long groupId)
		throws NoSuchRuleException {
		MDRRule mdrRule = fetchByUUID_G(uuid, groupId);

		if (mdrRule == null) {
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

			throw new NoSuchRuleException(msg.toString());
		}

		return mdrRule;
	}

	/**
	 * Returns the m d r rule where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching m d r rule, or <code>null</code> if a matching m d r rule could not be found
	 */
	@Override
	public MDRRule fetchByUUID_G(String uuid, long groupId) {
		return fetchByUUID_G(uuid, groupId, true);
	}

	/**
	 * Returns the m d r rule where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the matching m d r rule, or <code>null</code> if a matching m d r rule could not be found
	 */
	@Override
	public MDRRule fetchByUUID_G(String uuid, long groupId,
		boolean retrieveFromCache) {
		Object[] finderArgs = new Object[] { uuid, groupId };

		Object result = null;

		if (retrieveFromCache) {
			result = finderCache.getResult(FINDER_PATH_FETCH_BY_UUID_G,
					finderArgs, this);
		}

		if (result instanceof MDRRule) {
			MDRRule mdrRule = (MDRRule)result;

			if (!Validator.equals(uuid, mdrRule.getUuid()) ||
					(groupId != mdrRule.getGroupId())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_SELECT_MDRRULE_WHERE);

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

				List<MDRRule> list = q.list();

				if (list.isEmpty()) {
					finderCache.putResult(FINDER_PATH_FETCH_BY_UUID_G,
						finderArgs, list);
				}
				else {
					MDRRule mdrRule = list.get(0);

					result = mdrRule;

					cacheResult(mdrRule);

					if ((mdrRule.getUuid() == null) ||
							!mdrRule.getUuid().equals(uuid) ||
							(mdrRule.getGroupId() != groupId)) {
						finderCache.putResult(FINDER_PATH_FETCH_BY_UUID_G,
							finderArgs, mdrRule);
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
			return (MDRRule)result;
		}
	}

	/**
	 * Removes the m d r rule where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the m d r rule that was removed
	 */
	@Override
	public MDRRule removeByUUID_G(String uuid, long groupId)
		throws NoSuchRuleException {
		MDRRule mdrRule = findByUUID_G(uuid, groupId);

		return remove(mdrRule);
	}

	/**
	 * Returns the number of m d r rules where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching m d r rules
	 */
	@Override
	public int countByUUID_G(String uuid, long groupId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_UUID_G;

		Object[] finderArgs = new Object[] { uuid, groupId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_MDRRULE_WHERE);

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

	private static final String _FINDER_COLUMN_UUID_G_UUID_1 = "mdrRule.uuid IS NULL AND ";
	private static final String _FINDER_COLUMN_UUID_G_UUID_2 = "mdrRule.uuid = ? AND ";
	private static final String _FINDER_COLUMN_UUID_G_UUID_3 = "(mdrRule.uuid IS NULL OR mdrRule.uuid = '') AND ";
	private static final String _FINDER_COLUMN_UUID_G_GROUPID_2 = "mdrRule.groupId = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_UUID_C = new FinderPath(MDRRuleModelImpl.ENTITY_CACHE_ENABLED,
			MDRRuleModelImpl.FINDER_CACHE_ENABLED, MDRRuleImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUuid_C",
			new String[] {
				String.class.getName(), Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C =
		new FinderPath(MDRRuleModelImpl.ENTITY_CACHE_ENABLED,
			MDRRuleModelImpl.FINDER_CACHE_ENABLED, MDRRuleImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid_C",
			new String[] { String.class.getName(), Long.class.getName() },
			MDRRuleModelImpl.UUID_COLUMN_BITMASK |
			MDRRuleModelImpl.COMPANYID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID_C = new FinderPath(MDRRuleModelImpl.ENTITY_CACHE_ENABLED,
			MDRRuleModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUuid_C",
			new String[] { String.class.getName(), Long.class.getName() });

	/**
	 * Returns all the m d r rules where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching m d r rules
	 */
	@Override
	public List<MDRRule> findByUuid_C(String uuid, long companyId) {
		return findByUuid_C(uuid, companyId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the m d r rules where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MDRRuleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of m d r rules
	 * @param end the upper bound of the range of m d r rules (not inclusive)
	 * @return the range of matching m d r rules
	 */
	@Override
	public List<MDRRule> findByUuid_C(String uuid, long companyId, int start,
		int end) {
		return findByUuid_C(uuid, companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the m d r rules where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MDRRuleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of m d r rules
	 * @param end the upper bound of the range of m d r rules (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching m d r rules
	 */
	@Override
	public List<MDRRule> findByUuid_C(String uuid, long companyId, int start,
		int end, OrderByComparator<MDRRule> orderByComparator) {
		return findByUuid_C(uuid, companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the m d r rules where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MDRRuleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of m d r rules
	 * @param end the upper bound of the range of m d r rules (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching m d r rules
	 */
	@Override
	public List<MDRRule> findByUuid_C(String uuid, long companyId, int start,
		int end, OrderByComparator<MDRRule> orderByComparator,
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

		List<MDRRule> list = null;

		if (retrieveFromCache) {
			list = (List<MDRRule>)finderCache.getResult(finderPath, finderArgs,
					this);

			if ((list != null) && !list.isEmpty()) {
				for (MDRRule mdrRule : list) {
					if (!Validator.equals(uuid, mdrRule.getUuid()) ||
							(companyId != mdrRule.getCompanyId())) {
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

			query.append(_SQL_SELECT_MDRRULE_WHERE);

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
				query.append(MDRRuleModelImpl.ORDER_BY_JPQL);
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
					list = (List<MDRRule>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<MDRRule>)QueryUtil.list(q, getDialect(),
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
	 * Returns the first m d r rule in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching m d r rule
	 * @throws com.liferay.mobile.device.rules.NoSuchRuleException if a matching m d r rule could not be found
	 */
	@Override
	public MDRRule findByUuid_C_First(String uuid, long companyId,
		OrderByComparator<MDRRule> orderByComparator)
		throws NoSuchRuleException {
		MDRRule mdrRule = fetchByUuid_C_First(uuid, companyId, orderByComparator);

		if (mdrRule != null) {
			return mdrRule;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(", companyId=");
		msg.append(companyId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchRuleException(msg.toString());
	}

	/**
	 * Returns the first m d r rule in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching m d r rule, or <code>null</code> if a matching m d r rule could not be found
	 */
	@Override
	public MDRRule fetchByUuid_C_First(String uuid, long companyId,
		OrderByComparator<MDRRule> orderByComparator) {
		List<MDRRule> list = findByUuid_C(uuid, companyId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last m d r rule in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching m d r rule
	 * @throws com.liferay.mobile.device.rules.NoSuchRuleException if a matching m d r rule could not be found
	 */
	@Override
	public MDRRule findByUuid_C_Last(String uuid, long companyId,
		OrderByComparator<MDRRule> orderByComparator)
		throws NoSuchRuleException {
		MDRRule mdrRule = fetchByUuid_C_Last(uuid, companyId, orderByComparator);

		if (mdrRule != null) {
			return mdrRule;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(", companyId=");
		msg.append(companyId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchRuleException(msg.toString());
	}

	/**
	 * Returns the last m d r rule in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching m d r rule, or <code>null</code> if a matching m d r rule could not be found
	 */
	@Override
	public MDRRule fetchByUuid_C_Last(String uuid, long companyId,
		OrderByComparator<MDRRule> orderByComparator) {
		int count = countByUuid_C(uuid, companyId);

		if (count == 0) {
			return null;
		}

		List<MDRRule> list = findByUuid_C(uuid, companyId, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the m d r rules before and after the current m d r rule in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param ruleId the primary key of the current m d r rule
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next m d r rule
	 * @throws com.liferay.mobile.device.rules.NoSuchRuleException if a m d r rule with the primary key could not be found
	 */
	@Override
	public MDRRule[] findByUuid_C_PrevAndNext(long ruleId, String uuid,
		long companyId, OrderByComparator<MDRRule> orderByComparator)
		throws NoSuchRuleException {
		MDRRule mdrRule = findByPrimaryKey(ruleId);

		Session session = null;

		try {
			session = openSession();

			MDRRule[] array = new MDRRuleImpl[3];

			array[0] = getByUuid_C_PrevAndNext(session, mdrRule, uuid,
					companyId, orderByComparator, true);

			array[1] = mdrRule;

			array[2] = getByUuid_C_PrevAndNext(session, mdrRule, uuid,
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

	protected MDRRule getByUuid_C_PrevAndNext(Session session, MDRRule mdrRule,
		String uuid, long companyId,
		OrderByComparator<MDRRule> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_MDRRULE_WHERE);

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
			query.append(MDRRuleModelImpl.ORDER_BY_JPQL);
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
			Object[] values = orderByComparator.getOrderByConditionValues(mdrRule);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<MDRRule> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the m d r rules where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	@Override
	public void removeByUuid_C(String uuid, long companyId) {
		for (MDRRule mdrRule : findByUuid_C(uuid, companyId, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS, null)) {
			remove(mdrRule);
		}
	}

	/**
	 * Returns the number of m d r rules where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching m d r rules
	 */
	@Override
	public int countByUuid_C(String uuid, long companyId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_UUID_C;

		Object[] finderArgs = new Object[] { uuid, companyId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_MDRRULE_WHERE);

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

	private static final String _FINDER_COLUMN_UUID_C_UUID_1 = "mdrRule.uuid IS NULL AND ";
	private static final String _FINDER_COLUMN_UUID_C_UUID_2 = "mdrRule.uuid = ? AND ";
	private static final String _FINDER_COLUMN_UUID_C_UUID_3 = "(mdrRule.uuid IS NULL OR mdrRule.uuid = '') AND ";
	private static final String _FINDER_COLUMN_UUID_C_COMPANYID_2 = "mdrRule.companyId = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_RULEGROUPID =
		new FinderPath(MDRRuleModelImpl.ENTITY_CACHE_ENABLED,
			MDRRuleModelImpl.FINDER_CACHE_ENABLED, MDRRuleImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByRuleGroupId",
			new String[] {
				Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_RULEGROUPID =
		new FinderPath(MDRRuleModelImpl.ENTITY_CACHE_ENABLED,
			MDRRuleModelImpl.FINDER_CACHE_ENABLED, MDRRuleImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByRuleGroupId",
			new String[] { Long.class.getName() },
			MDRRuleModelImpl.RULEGROUPID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_RULEGROUPID = new FinderPath(MDRRuleModelImpl.ENTITY_CACHE_ENABLED,
			MDRRuleModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByRuleGroupId",
			new String[] { Long.class.getName() });

	/**
	 * Returns all the m d r rules where ruleGroupId = &#63;.
	 *
	 * @param ruleGroupId the rule group ID
	 * @return the matching m d r rules
	 */
	@Override
	public List<MDRRule> findByRuleGroupId(long ruleGroupId) {
		return findByRuleGroupId(ruleGroupId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the m d r rules where ruleGroupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MDRRuleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param ruleGroupId the rule group ID
	 * @param start the lower bound of the range of m d r rules
	 * @param end the upper bound of the range of m d r rules (not inclusive)
	 * @return the range of matching m d r rules
	 */
	@Override
	public List<MDRRule> findByRuleGroupId(long ruleGroupId, int start, int end) {
		return findByRuleGroupId(ruleGroupId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the m d r rules where ruleGroupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MDRRuleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param ruleGroupId the rule group ID
	 * @param start the lower bound of the range of m d r rules
	 * @param end the upper bound of the range of m d r rules (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching m d r rules
	 */
	@Override
	public List<MDRRule> findByRuleGroupId(long ruleGroupId, int start,
		int end, OrderByComparator<MDRRule> orderByComparator) {
		return findByRuleGroupId(ruleGroupId, start, end, orderByComparator,
			true);
	}

	/**
	 * Returns an ordered range of all the m d r rules where ruleGroupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MDRRuleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param ruleGroupId the rule group ID
	 * @param start the lower bound of the range of m d r rules
	 * @param end the upper bound of the range of m d r rules (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching m d r rules
	 */
	@Override
	public List<MDRRule> findByRuleGroupId(long ruleGroupId, int start,
		int end, OrderByComparator<MDRRule> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_RULEGROUPID;
			finderArgs = new Object[] { ruleGroupId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_RULEGROUPID;
			finderArgs = new Object[] { ruleGroupId, start, end, orderByComparator };
		}

		List<MDRRule> list = null;

		if (retrieveFromCache) {
			list = (List<MDRRule>)finderCache.getResult(finderPath, finderArgs,
					this);

			if ((list != null) && !list.isEmpty()) {
				for (MDRRule mdrRule : list) {
					if ((ruleGroupId != mdrRule.getRuleGroupId())) {
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

			query.append(_SQL_SELECT_MDRRULE_WHERE);

			query.append(_FINDER_COLUMN_RULEGROUPID_RULEGROUPID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(MDRRuleModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(ruleGroupId);

				if (!pagination) {
					list = (List<MDRRule>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<MDRRule>)QueryUtil.list(q, getDialect(),
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
	 * Returns the first m d r rule in the ordered set where ruleGroupId = &#63;.
	 *
	 * @param ruleGroupId the rule group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching m d r rule
	 * @throws com.liferay.mobile.device.rules.NoSuchRuleException if a matching m d r rule could not be found
	 */
	@Override
	public MDRRule findByRuleGroupId_First(long ruleGroupId,
		OrderByComparator<MDRRule> orderByComparator)
		throws NoSuchRuleException {
		MDRRule mdrRule = fetchByRuleGroupId_First(ruleGroupId,
				orderByComparator);

		if (mdrRule != null) {
			return mdrRule;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("ruleGroupId=");
		msg.append(ruleGroupId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchRuleException(msg.toString());
	}

	/**
	 * Returns the first m d r rule in the ordered set where ruleGroupId = &#63;.
	 *
	 * @param ruleGroupId the rule group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching m d r rule, or <code>null</code> if a matching m d r rule could not be found
	 */
	@Override
	public MDRRule fetchByRuleGroupId_First(long ruleGroupId,
		OrderByComparator<MDRRule> orderByComparator) {
		List<MDRRule> list = findByRuleGroupId(ruleGroupId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last m d r rule in the ordered set where ruleGroupId = &#63;.
	 *
	 * @param ruleGroupId the rule group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching m d r rule
	 * @throws com.liferay.mobile.device.rules.NoSuchRuleException if a matching m d r rule could not be found
	 */
	@Override
	public MDRRule findByRuleGroupId_Last(long ruleGroupId,
		OrderByComparator<MDRRule> orderByComparator)
		throws NoSuchRuleException {
		MDRRule mdrRule = fetchByRuleGroupId_Last(ruleGroupId, orderByComparator);

		if (mdrRule != null) {
			return mdrRule;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("ruleGroupId=");
		msg.append(ruleGroupId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchRuleException(msg.toString());
	}

	/**
	 * Returns the last m d r rule in the ordered set where ruleGroupId = &#63;.
	 *
	 * @param ruleGroupId the rule group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching m d r rule, or <code>null</code> if a matching m d r rule could not be found
	 */
	@Override
	public MDRRule fetchByRuleGroupId_Last(long ruleGroupId,
		OrderByComparator<MDRRule> orderByComparator) {
		int count = countByRuleGroupId(ruleGroupId);

		if (count == 0) {
			return null;
		}

		List<MDRRule> list = findByRuleGroupId(ruleGroupId, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the m d r rules before and after the current m d r rule in the ordered set where ruleGroupId = &#63;.
	 *
	 * @param ruleId the primary key of the current m d r rule
	 * @param ruleGroupId the rule group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next m d r rule
	 * @throws com.liferay.mobile.device.rules.NoSuchRuleException if a m d r rule with the primary key could not be found
	 */
	@Override
	public MDRRule[] findByRuleGroupId_PrevAndNext(long ruleId,
		long ruleGroupId, OrderByComparator<MDRRule> orderByComparator)
		throws NoSuchRuleException {
		MDRRule mdrRule = findByPrimaryKey(ruleId);

		Session session = null;

		try {
			session = openSession();

			MDRRule[] array = new MDRRuleImpl[3];

			array[0] = getByRuleGroupId_PrevAndNext(session, mdrRule,
					ruleGroupId, orderByComparator, true);

			array[1] = mdrRule;

			array[2] = getByRuleGroupId_PrevAndNext(session, mdrRule,
					ruleGroupId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected MDRRule getByRuleGroupId_PrevAndNext(Session session,
		MDRRule mdrRule, long ruleGroupId,
		OrderByComparator<MDRRule> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_MDRRULE_WHERE);

		query.append(_FINDER_COLUMN_RULEGROUPID_RULEGROUPID_2);

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
			query.append(MDRRuleModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(ruleGroupId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(mdrRule);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<MDRRule> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the m d r rules where ruleGroupId = &#63; from the database.
	 *
	 * @param ruleGroupId the rule group ID
	 */
	@Override
	public void removeByRuleGroupId(long ruleGroupId) {
		for (MDRRule mdrRule : findByRuleGroupId(ruleGroupId,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(mdrRule);
		}
	}

	/**
	 * Returns the number of m d r rules where ruleGroupId = &#63;.
	 *
	 * @param ruleGroupId the rule group ID
	 * @return the number of matching m d r rules
	 */
	@Override
	public int countByRuleGroupId(long ruleGroupId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_RULEGROUPID;

		Object[] finderArgs = new Object[] { ruleGroupId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_MDRRULE_WHERE);

			query.append(_FINDER_COLUMN_RULEGROUPID_RULEGROUPID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(ruleGroupId);

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

	private static final String _FINDER_COLUMN_RULEGROUPID_RULEGROUPID_2 = "mdrRule.ruleGroupId = ?";

	public MDRRulePersistenceImpl() {
		setModelClass(MDRRule.class);
	}

	/**
	 * Caches the m d r rule in the entity cache if it is enabled.
	 *
	 * @param mdrRule the m d r rule
	 */
	@Override
	public void cacheResult(MDRRule mdrRule) {
		entityCache.putResult(MDRRuleModelImpl.ENTITY_CACHE_ENABLED,
			MDRRuleImpl.class, mdrRule.getPrimaryKey(), mdrRule);

		finderCache.putResult(FINDER_PATH_FETCH_BY_UUID_G,
			new Object[] { mdrRule.getUuid(), mdrRule.getGroupId() }, mdrRule);

		mdrRule.resetOriginalValues();
	}

	/**
	 * Caches the m d r rules in the entity cache if it is enabled.
	 *
	 * @param mdrRules the m d r rules
	 */
	@Override
	public void cacheResult(List<MDRRule> mdrRules) {
		for (MDRRule mdrRule : mdrRules) {
			if (entityCache.getResult(MDRRuleModelImpl.ENTITY_CACHE_ENABLED,
						MDRRuleImpl.class, mdrRule.getPrimaryKey()) == null) {
				cacheResult(mdrRule);
			}
			else {
				mdrRule.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all m d r rules.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(MDRRuleImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the m d r rule.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(MDRRule mdrRule) {
		entityCache.removeResult(MDRRuleModelImpl.ENTITY_CACHE_ENABLED,
			MDRRuleImpl.class, mdrRule.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache((MDRRuleModelImpl)mdrRule);
	}

	@Override
	public void clearCache(List<MDRRule> mdrRules) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (MDRRule mdrRule : mdrRules) {
			entityCache.removeResult(MDRRuleModelImpl.ENTITY_CACHE_ENABLED,
				MDRRuleImpl.class, mdrRule.getPrimaryKey());

			clearUniqueFindersCache((MDRRuleModelImpl)mdrRule);
		}
	}

	protected void cacheUniqueFindersCache(MDRRuleModelImpl mdrRuleModelImpl,
		boolean isNew) {
		if (isNew) {
			Object[] args = new Object[] {
					mdrRuleModelImpl.getUuid(), mdrRuleModelImpl.getGroupId()
				};

			finderCache.putResult(FINDER_PATH_COUNT_BY_UUID_G, args,
				Long.valueOf(1));
			finderCache.putResult(FINDER_PATH_FETCH_BY_UUID_G, args,
				mdrRuleModelImpl);
		}
		else {
			if ((mdrRuleModelImpl.getColumnBitmask() &
					FINDER_PATH_FETCH_BY_UUID_G.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						mdrRuleModelImpl.getUuid(),
						mdrRuleModelImpl.getGroupId()
					};

				finderCache.putResult(FINDER_PATH_COUNT_BY_UUID_G, args,
					Long.valueOf(1));
				finderCache.putResult(FINDER_PATH_FETCH_BY_UUID_G, args,
					mdrRuleModelImpl);
			}
		}
	}

	protected void clearUniqueFindersCache(MDRRuleModelImpl mdrRuleModelImpl) {
		Object[] args = new Object[] {
				mdrRuleModelImpl.getUuid(), mdrRuleModelImpl.getGroupId()
			};

		finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID_G, args);
		finderCache.removeResult(FINDER_PATH_FETCH_BY_UUID_G, args);

		if ((mdrRuleModelImpl.getColumnBitmask() &
				FINDER_PATH_FETCH_BY_UUID_G.getColumnBitmask()) != 0) {
			args = new Object[] {
					mdrRuleModelImpl.getOriginalUuid(),
					mdrRuleModelImpl.getOriginalGroupId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID_G, args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_UUID_G, args);
		}
	}

	/**
	 * Creates a new m d r rule with the primary key. Does not add the m d r rule to the database.
	 *
	 * @param ruleId the primary key for the new m d r rule
	 * @return the new m d r rule
	 */
	@Override
	public MDRRule create(long ruleId) {
		MDRRule mdrRule = new MDRRuleImpl();

		mdrRule.setNew(true);
		mdrRule.setPrimaryKey(ruleId);

		String uuid = PortalUUIDUtil.generate();

		mdrRule.setUuid(uuid);

		return mdrRule;
	}

	/**
	 * Removes the m d r rule with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param ruleId the primary key of the m d r rule
	 * @return the m d r rule that was removed
	 * @throws com.liferay.mobile.device.rules.NoSuchRuleException if a m d r rule with the primary key could not be found
	 */
	@Override
	public MDRRule remove(long ruleId) throws NoSuchRuleException {
		return remove((Serializable)ruleId);
	}

	/**
	 * Removes the m d r rule with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the m d r rule
	 * @return the m d r rule that was removed
	 * @throws com.liferay.mobile.device.rules.NoSuchRuleException if a m d r rule with the primary key could not be found
	 */
	@Override
	public MDRRule remove(Serializable primaryKey) throws NoSuchRuleException {
		Session session = null;

		try {
			session = openSession();

			MDRRule mdrRule = (MDRRule)session.get(MDRRuleImpl.class, primaryKey);

			if (mdrRule == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchRuleException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(mdrRule);
		}
		catch (NoSuchRuleException nsee) {
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
	protected MDRRule removeImpl(MDRRule mdrRule) {
		mdrRule = toUnwrappedModel(mdrRule);

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(mdrRule)) {
				mdrRule = (MDRRule)session.get(MDRRuleImpl.class,
						mdrRule.getPrimaryKeyObj());
			}

			if (mdrRule != null) {
				session.delete(mdrRule);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (mdrRule != null) {
			clearCache(mdrRule);
		}

		return mdrRule;
	}

	@Override
	public MDRRule updateImpl(MDRRule mdrRule) {
		mdrRule = toUnwrappedModel(mdrRule);

		boolean isNew = mdrRule.isNew();

		MDRRuleModelImpl mdrRuleModelImpl = (MDRRuleModelImpl)mdrRule;

		if (Validator.isNull(mdrRule.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			mdrRule.setUuid(uuid);
		}

		ServiceContext serviceContext = ServiceContextThreadLocal.getServiceContext();

		Date now = new Date();

		if (isNew && (mdrRule.getCreateDate() == null)) {
			if (serviceContext == null) {
				mdrRule.setCreateDate(now);
			}
			else {
				mdrRule.setCreateDate(serviceContext.getCreateDate(now));
			}
		}

		if (!mdrRuleModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				mdrRule.setModifiedDate(now);
			}
			else {
				mdrRule.setModifiedDate(serviceContext.getModifiedDate(now));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (mdrRule.isNew()) {
				session.save(mdrRule);

				mdrRule.setNew(false);
			}
			else {
				mdrRule = (MDRRule)session.merge(mdrRule);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (isNew || !MDRRuleModelImpl.COLUMN_BITMASK_ENABLED) {
			finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}

		else {
			if ((mdrRuleModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] { mdrRuleModelImpl.getOriginalUuid() };

				finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID,
					args);

				args = new Object[] { mdrRuleModelImpl.getUuid() };

				finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID,
					args);
			}

			if ((mdrRuleModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						mdrRuleModelImpl.getOriginalUuid(),
						mdrRuleModelImpl.getOriginalCompanyId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID_C, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C,
					args);

				args = new Object[] {
						mdrRuleModelImpl.getUuid(),
						mdrRuleModelImpl.getCompanyId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID_C, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C,
					args);
			}

			if ((mdrRuleModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_RULEGROUPID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						mdrRuleModelImpl.getOriginalRuleGroupId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_RULEGROUPID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_RULEGROUPID,
					args);

				args = new Object[] { mdrRuleModelImpl.getRuleGroupId() };

				finderCache.removeResult(FINDER_PATH_COUNT_BY_RULEGROUPID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_RULEGROUPID,
					args);
			}
		}

		entityCache.putResult(MDRRuleModelImpl.ENTITY_CACHE_ENABLED,
			MDRRuleImpl.class, mdrRule.getPrimaryKey(), mdrRule, false);

		clearUniqueFindersCache(mdrRuleModelImpl);
		cacheUniqueFindersCache(mdrRuleModelImpl, isNew);

		mdrRule.resetOriginalValues();

		return mdrRule;
	}

	protected MDRRule toUnwrappedModel(MDRRule mdrRule) {
		if (mdrRule instanceof MDRRuleImpl) {
			return mdrRule;
		}

		MDRRuleImpl mdrRuleImpl = new MDRRuleImpl();

		mdrRuleImpl.setNew(mdrRule.isNew());
		mdrRuleImpl.setPrimaryKey(mdrRule.getPrimaryKey());

		mdrRuleImpl.setUuid(mdrRule.getUuid());
		mdrRuleImpl.setRuleId(mdrRule.getRuleId());
		mdrRuleImpl.setGroupId(mdrRule.getGroupId());
		mdrRuleImpl.setCompanyId(mdrRule.getCompanyId());
		mdrRuleImpl.setUserId(mdrRule.getUserId());
		mdrRuleImpl.setUserName(mdrRule.getUserName());
		mdrRuleImpl.setCreateDate(mdrRule.getCreateDate());
		mdrRuleImpl.setModifiedDate(mdrRule.getModifiedDate());
		mdrRuleImpl.setRuleGroupId(mdrRule.getRuleGroupId());
		mdrRuleImpl.setName(mdrRule.getName());
		mdrRuleImpl.setDescription(mdrRule.getDescription());
		mdrRuleImpl.setType(mdrRule.getType());
		mdrRuleImpl.setTypeSettings(mdrRule.getTypeSettings());
		mdrRuleImpl.setLastPublishDate(mdrRule.getLastPublishDate());

		return mdrRuleImpl;
	}

	/**
	 * Returns the m d r rule with the primary key or throws a {@link com.liferay.portal.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the m d r rule
	 * @return the m d r rule
	 * @throws com.liferay.mobile.device.rules.NoSuchRuleException if a m d r rule with the primary key could not be found
	 */
	@Override
	public MDRRule findByPrimaryKey(Serializable primaryKey)
		throws NoSuchRuleException {
		MDRRule mdrRule = fetchByPrimaryKey(primaryKey);

		if (mdrRule == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchRuleException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				primaryKey);
		}

		return mdrRule;
	}

	/**
	 * Returns the m d r rule with the primary key or throws a {@link com.liferay.mobile.device.rules.NoSuchRuleException} if it could not be found.
	 *
	 * @param ruleId the primary key of the m d r rule
	 * @return the m d r rule
	 * @throws com.liferay.mobile.device.rules.NoSuchRuleException if a m d r rule with the primary key could not be found
	 */
	@Override
	public MDRRule findByPrimaryKey(long ruleId) throws NoSuchRuleException {
		return findByPrimaryKey((Serializable)ruleId);
	}

	/**
	 * Returns the m d r rule with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the m d r rule
	 * @return the m d r rule, or <code>null</code> if a m d r rule with the primary key could not be found
	 */
	@Override
	public MDRRule fetchByPrimaryKey(Serializable primaryKey) {
		MDRRule mdrRule = (MDRRule)entityCache.getResult(MDRRuleModelImpl.ENTITY_CACHE_ENABLED,
				MDRRuleImpl.class, primaryKey);

		if (mdrRule == _nullMDRRule) {
			return null;
		}

		if (mdrRule == null) {
			Session session = null;

			try {
				session = openSession();

				mdrRule = (MDRRule)session.get(MDRRuleImpl.class, primaryKey);

				if (mdrRule != null) {
					cacheResult(mdrRule);
				}
				else {
					entityCache.putResult(MDRRuleModelImpl.ENTITY_CACHE_ENABLED,
						MDRRuleImpl.class, primaryKey, _nullMDRRule);
				}
			}
			catch (Exception e) {
				entityCache.removeResult(MDRRuleModelImpl.ENTITY_CACHE_ENABLED,
					MDRRuleImpl.class, primaryKey);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return mdrRule;
	}

	/**
	 * Returns the m d r rule with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param ruleId the primary key of the m d r rule
	 * @return the m d r rule, or <code>null</code> if a m d r rule with the primary key could not be found
	 */
	@Override
	public MDRRule fetchByPrimaryKey(long ruleId) {
		return fetchByPrimaryKey((Serializable)ruleId);
	}

	@Override
	public Map<Serializable, MDRRule> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {
		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, MDRRule> map = new HashMap<Serializable, MDRRule>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			MDRRule mdrRule = fetchByPrimaryKey(primaryKey);

			if (mdrRule != null) {
				map.put(primaryKey, mdrRule);
			}

			return map;
		}

		Set<Serializable> uncachedPrimaryKeys = null;

		for (Serializable primaryKey : primaryKeys) {
			MDRRule mdrRule = (MDRRule)entityCache.getResult(MDRRuleModelImpl.ENTITY_CACHE_ENABLED,
					MDRRuleImpl.class, primaryKey);

			if (mdrRule == null) {
				if (uncachedPrimaryKeys == null) {
					uncachedPrimaryKeys = new HashSet<Serializable>();
				}

				uncachedPrimaryKeys.add(primaryKey);
			}
			else {
				map.put(primaryKey, mdrRule);
			}
		}

		if (uncachedPrimaryKeys == null) {
			return map;
		}

		StringBundler query = new StringBundler((uncachedPrimaryKeys.size() * 2) +
				1);

		query.append(_SQL_SELECT_MDRRULE_WHERE_PKS_IN);

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

			for (MDRRule mdrRule : (List<MDRRule>)q.list()) {
				map.put(mdrRule.getPrimaryKeyObj(), mdrRule);

				cacheResult(mdrRule);

				uncachedPrimaryKeys.remove(mdrRule.getPrimaryKeyObj());
			}

			for (Serializable primaryKey : uncachedPrimaryKeys) {
				entityCache.putResult(MDRRuleModelImpl.ENTITY_CACHE_ENABLED,
					MDRRuleImpl.class, primaryKey, _nullMDRRule);
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
	 * Returns all the m d r rules.
	 *
	 * @return the m d r rules
	 */
	@Override
	public List<MDRRule> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the m d r rules.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MDRRuleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of m d r rules
	 * @param end the upper bound of the range of m d r rules (not inclusive)
	 * @return the range of m d r rules
	 */
	@Override
	public List<MDRRule> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the m d r rules.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MDRRuleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of m d r rules
	 * @param end the upper bound of the range of m d r rules (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of m d r rules
	 */
	@Override
	public List<MDRRule> findAll(int start, int end,
		OrderByComparator<MDRRule> orderByComparator) {
		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the m d r rules.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link MDRRuleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of m d r rules
	 * @param end the upper bound of the range of m d r rules (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of m d r rules
	 */
	@Override
	public List<MDRRule> findAll(int start, int end,
		OrderByComparator<MDRRule> orderByComparator, boolean retrieveFromCache) {
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

		List<MDRRule> list = null;

		if (retrieveFromCache) {
			list = (List<MDRRule>)finderCache.getResult(finderPath, finderArgs,
					this);
		}

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 3));

				query.append(_SQL_SELECT_MDRRULE);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_MDRRULE;

				if (pagination) {
					sql = sql.concat(MDRRuleModelImpl.ORDER_BY_JPQL);
				}
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (!pagination) {
					list = (List<MDRRule>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<MDRRule>)QueryUtil.list(q, getDialect(),
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
	 * Removes all the m d r rules from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (MDRRule mdrRule : findAll()) {
			remove(mdrRule);
		}
	}

	/**
	 * Returns the number of m d r rules.
	 *
	 * @return the number of m d r rules
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_MDRRULE);

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
		return MDRRuleModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the m d r rule persistence.
	 */
	public void afterPropertiesSet() {
	}

	public void destroy() {
		entityCache.removeCache(MDRRuleImpl.class.getName());
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
	private static final String _SQL_SELECT_MDRRULE = "SELECT mdrRule FROM MDRRule mdrRule";
	private static final String _SQL_SELECT_MDRRULE_WHERE_PKS_IN = "SELECT mdrRule FROM MDRRule mdrRule WHERE ruleId IN (";
	private static final String _SQL_SELECT_MDRRULE_WHERE = "SELECT mdrRule FROM MDRRule mdrRule WHERE ";
	private static final String _SQL_COUNT_MDRRULE = "SELECT COUNT(mdrRule) FROM MDRRule mdrRule";
	private static final String _SQL_COUNT_MDRRULE_WHERE = "SELECT COUNT(mdrRule) FROM MDRRule mdrRule WHERE ";
	private static final String _ORDER_BY_ENTITY_ALIAS = "mdrRule.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No MDRRule exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No MDRRule exists with the key {";
	private static final Log _log = LogFactoryUtil.getLog(MDRRulePersistenceImpl.class);
	private static final Set<String> _badColumnNames = SetUtil.fromArray(new String[] {
				"uuid", "type"
			});
	private static final MDRRule _nullMDRRule = new MDRRuleImpl() {
			@Override
			public Object clone() {
				return this;
			}

			@Override
			public CacheModel<MDRRule> toCacheModel() {
				return _nullMDRRuleCacheModel;
			}
		};

	private static final CacheModel<MDRRule> _nullMDRRuleCacheModel = new CacheModel<MDRRule>() {
			@Override
			public MDRRule toEntityModel() {
				return _nullMDRRule;
			}
		};
}