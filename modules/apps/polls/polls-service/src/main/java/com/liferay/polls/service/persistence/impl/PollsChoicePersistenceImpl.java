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

package com.liferay.polls.service.persistence.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.polls.exception.NoSuchChoiceException;
import com.liferay.polls.model.PollsChoice;
import com.liferay.polls.model.impl.PollsChoiceImpl;
import com.liferay.polls.model.impl.PollsChoiceModelImpl;
import com.liferay.polls.service.persistence.PollsChoicePersistence;

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
 * The persistence implementation for the polls choice service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see PollsChoicePersistence
 * @see com.liferay.polls.service.persistence.PollsChoiceUtil
 * @generated
 */
@ProviderType
public class PollsChoicePersistenceImpl extends BasePersistenceImpl<PollsChoice>
	implements PollsChoicePersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link PollsChoiceUtil} to access the polls choice persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = PollsChoiceImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(PollsChoiceModelImpl.ENTITY_CACHE_ENABLED,
			PollsChoiceModelImpl.FINDER_CACHE_ENABLED, PollsChoiceImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(PollsChoiceModelImpl.ENTITY_CACHE_ENABLED,
			PollsChoiceModelImpl.FINDER_CACHE_ENABLED, PollsChoiceImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(PollsChoiceModelImpl.ENTITY_CACHE_ENABLED,
			PollsChoiceModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_UUID = new FinderPath(PollsChoiceModelImpl.ENTITY_CACHE_ENABLED,
			PollsChoiceModelImpl.FINDER_CACHE_ENABLED, PollsChoiceImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUuid",
			new String[] {
				String.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID = new FinderPath(PollsChoiceModelImpl.ENTITY_CACHE_ENABLED,
			PollsChoiceModelImpl.FINDER_CACHE_ENABLED, PollsChoiceImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid",
			new String[] { String.class.getName() },
			PollsChoiceModelImpl.UUID_COLUMN_BITMASK |
			PollsChoiceModelImpl.QUESTIONID_COLUMN_BITMASK |
			PollsChoiceModelImpl.NAME_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID = new FinderPath(PollsChoiceModelImpl.ENTITY_CACHE_ENABLED,
			PollsChoiceModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUuid",
			new String[] { String.class.getName() });

	/**
	 * Returns all the polls choices where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching polls choices
	 */
	@Override
	public List<PollsChoice> findByUuid(String uuid) {
		return findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the polls choices where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link PollsChoiceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of polls choices
	 * @param end the upper bound of the range of polls choices (not inclusive)
	 * @return the range of matching polls choices
	 */
	@Override
	public List<PollsChoice> findByUuid(String uuid, int start, int end) {
		return findByUuid(uuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the polls choices where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link PollsChoiceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of polls choices
	 * @param end the upper bound of the range of polls choices (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching polls choices
	 */
	@Override
	public List<PollsChoice> findByUuid(String uuid, int start, int end,
		OrderByComparator<PollsChoice> orderByComparator) {
		return findByUuid(uuid, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the polls choices where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link PollsChoiceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of polls choices
	 * @param end the upper bound of the range of polls choices (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching polls choices
	 */
	@Override
	public List<PollsChoice> findByUuid(String uuid, int start, int end,
		OrderByComparator<PollsChoice> orderByComparator,
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

		List<PollsChoice> list = null;

		if (retrieveFromCache) {
			list = (List<PollsChoice>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (PollsChoice pollsChoice : list) {
					if (!Validator.equals(uuid, pollsChoice.getUuid())) {
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

			query.append(_SQL_SELECT_POLLSCHOICE_WHERE);

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
				query.append(PollsChoiceModelImpl.ORDER_BY_JPQL);
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
					list = (List<PollsChoice>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<PollsChoice>)QueryUtil.list(q, getDialect(),
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
	 * Returns the first polls choice in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching polls choice
	 * @throws com.liferay.polls.NoSuchChoiceException if a matching polls choice could not be found
	 */
	@Override
	public PollsChoice findByUuid_First(String uuid,
		OrderByComparator<PollsChoice> orderByComparator)
		throws NoSuchChoiceException {
		PollsChoice pollsChoice = fetchByUuid_First(uuid, orderByComparator);

		if (pollsChoice != null) {
			return pollsChoice;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchChoiceException(msg.toString());
	}

	/**
	 * Returns the first polls choice in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching polls choice, or <code>null</code> if a matching polls choice could not be found
	 */
	@Override
	public PollsChoice fetchByUuid_First(String uuid,
		OrderByComparator<PollsChoice> orderByComparator) {
		List<PollsChoice> list = findByUuid(uuid, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last polls choice in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching polls choice
	 * @throws com.liferay.polls.NoSuchChoiceException if a matching polls choice could not be found
	 */
	@Override
	public PollsChoice findByUuid_Last(String uuid,
		OrderByComparator<PollsChoice> orderByComparator)
		throws NoSuchChoiceException {
		PollsChoice pollsChoice = fetchByUuid_Last(uuid, orderByComparator);

		if (pollsChoice != null) {
			return pollsChoice;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchChoiceException(msg.toString());
	}

	/**
	 * Returns the last polls choice in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching polls choice, or <code>null</code> if a matching polls choice could not be found
	 */
	@Override
	public PollsChoice fetchByUuid_Last(String uuid,
		OrderByComparator<PollsChoice> orderByComparator) {
		int count = countByUuid(uuid);

		if (count == 0) {
			return null;
		}

		List<PollsChoice> list = findByUuid(uuid, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the polls choices before and after the current polls choice in the ordered set where uuid = &#63;.
	 *
	 * @param choiceId the primary key of the current polls choice
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next polls choice
	 * @throws com.liferay.polls.NoSuchChoiceException if a polls choice with the primary key could not be found
	 */
	@Override
	public PollsChoice[] findByUuid_PrevAndNext(long choiceId, String uuid,
		OrderByComparator<PollsChoice> orderByComparator)
		throws NoSuchChoiceException {
		PollsChoice pollsChoice = findByPrimaryKey(choiceId);

		Session session = null;

		try {
			session = openSession();

			PollsChoice[] array = new PollsChoiceImpl[3];

			array[0] = getByUuid_PrevAndNext(session, pollsChoice, uuid,
					orderByComparator, true);

			array[1] = pollsChoice;

			array[2] = getByUuid_PrevAndNext(session, pollsChoice, uuid,
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

	protected PollsChoice getByUuid_PrevAndNext(Session session,
		PollsChoice pollsChoice, String uuid,
		OrderByComparator<PollsChoice> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_POLLSCHOICE_WHERE);

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
			query.append(PollsChoiceModelImpl.ORDER_BY_JPQL);
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
			Object[] values = orderByComparator.getOrderByConditionValues(pollsChoice);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<PollsChoice> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the polls choices where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	@Override
	public void removeByUuid(String uuid) {
		for (PollsChoice pollsChoice : findByUuid(uuid, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS, null)) {
			remove(pollsChoice);
		}
	}

	/**
	 * Returns the number of polls choices where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching polls choices
	 */
	@Override
	public int countByUuid(String uuid) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_UUID;

		Object[] finderArgs = new Object[] { uuid };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_POLLSCHOICE_WHERE);

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

	private static final String _FINDER_COLUMN_UUID_UUID_1 = "pollsChoice.uuid IS NULL";
	private static final String _FINDER_COLUMN_UUID_UUID_2 = "pollsChoice.uuid = ?";
	private static final String _FINDER_COLUMN_UUID_UUID_3 = "(pollsChoice.uuid IS NULL OR pollsChoice.uuid = '')";
	public static final FinderPath FINDER_PATH_FETCH_BY_UUID_G = new FinderPath(PollsChoiceModelImpl.ENTITY_CACHE_ENABLED,
			PollsChoiceModelImpl.FINDER_CACHE_ENABLED, PollsChoiceImpl.class,
			FINDER_CLASS_NAME_ENTITY, "fetchByUUID_G",
			new String[] { String.class.getName(), Long.class.getName() },
			PollsChoiceModelImpl.UUID_COLUMN_BITMASK |
			PollsChoiceModelImpl.GROUPID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID_G = new FinderPath(PollsChoiceModelImpl.ENTITY_CACHE_ENABLED,
			PollsChoiceModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUUID_G",
			new String[] { String.class.getName(), Long.class.getName() });

	/**
	 * Returns the polls choice where uuid = &#63; and groupId = &#63; or throws a {@link com.liferay.polls.NoSuchChoiceException} if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching polls choice
	 * @throws com.liferay.polls.NoSuchChoiceException if a matching polls choice could not be found
	 */
	@Override
	public PollsChoice findByUUID_G(String uuid, long groupId)
		throws NoSuchChoiceException {
		PollsChoice pollsChoice = fetchByUUID_G(uuid, groupId);

		if (pollsChoice == null) {
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

			throw new NoSuchChoiceException(msg.toString());
		}

		return pollsChoice;
	}

	/**
	 * Returns the polls choice where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching polls choice, or <code>null</code> if a matching polls choice could not be found
	 */
	@Override
	public PollsChoice fetchByUUID_G(String uuid, long groupId) {
		return fetchByUUID_G(uuid, groupId, true);
	}

	/**
	 * Returns the polls choice where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the matching polls choice, or <code>null</code> if a matching polls choice could not be found
	 */
	@Override
	public PollsChoice fetchByUUID_G(String uuid, long groupId,
		boolean retrieveFromCache) {
		Object[] finderArgs = new Object[] { uuid, groupId };

		Object result = null;

		if (retrieveFromCache) {
			result = finderCache.getResult(FINDER_PATH_FETCH_BY_UUID_G,
					finderArgs, this);
		}

		if (result instanceof PollsChoice) {
			PollsChoice pollsChoice = (PollsChoice)result;

			if (!Validator.equals(uuid, pollsChoice.getUuid()) ||
					(groupId != pollsChoice.getGroupId())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_SELECT_POLLSCHOICE_WHERE);

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

				List<PollsChoice> list = q.list();

				if (list.isEmpty()) {
					finderCache.putResult(FINDER_PATH_FETCH_BY_UUID_G,
						finderArgs, list);
				}
				else {
					PollsChoice pollsChoice = list.get(0);

					result = pollsChoice;

					cacheResult(pollsChoice);

					if ((pollsChoice.getUuid() == null) ||
							!pollsChoice.getUuid().equals(uuid) ||
							(pollsChoice.getGroupId() != groupId)) {
						finderCache.putResult(FINDER_PATH_FETCH_BY_UUID_G,
							finderArgs, pollsChoice);
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
			return (PollsChoice)result;
		}
	}

	/**
	 * Removes the polls choice where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the polls choice that was removed
	 */
	@Override
	public PollsChoice removeByUUID_G(String uuid, long groupId)
		throws NoSuchChoiceException {
		PollsChoice pollsChoice = findByUUID_G(uuid, groupId);

		return remove(pollsChoice);
	}

	/**
	 * Returns the number of polls choices where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching polls choices
	 */
	@Override
	public int countByUUID_G(String uuid, long groupId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_UUID_G;

		Object[] finderArgs = new Object[] { uuid, groupId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_POLLSCHOICE_WHERE);

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

	private static final String _FINDER_COLUMN_UUID_G_UUID_1 = "pollsChoice.uuid IS NULL AND ";
	private static final String _FINDER_COLUMN_UUID_G_UUID_2 = "pollsChoice.uuid = ? AND ";
	private static final String _FINDER_COLUMN_UUID_G_UUID_3 = "(pollsChoice.uuid IS NULL OR pollsChoice.uuid = '') AND ";
	private static final String _FINDER_COLUMN_UUID_G_GROUPID_2 = "pollsChoice.groupId = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_UUID_C = new FinderPath(PollsChoiceModelImpl.ENTITY_CACHE_ENABLED,
			PollsChoiceModelImpl.FINDER_CACHE_ENABLED, PollsChoiceImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUuid_C",
			new String[] {
				String.class.getName(), Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C =
		new FinderPath(PollsChoiceModelImpl.ENTITY_CACHE_ENABLED,
			PollsChoiceModelImpl.FINDER_CACHE_ENABLED, PollsChoiceImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid_C",
			new String[] { String.class.getName(), Long.class.getName() },
			PollsChoiceModelImpl.UUID_COLUMN_BITMASK |
			PollsChoiceModelImpl.COMPANYID_COLUMN_BITMASK |
			PollsChoiceModelImpl.QUESTIONID_COLUMN_BITMASK |
			PollsChoiceModelImpl.NAME_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_UUID_C = new FinderPath(PollsChoiceModelImpl.ENTITY_CACHE_ENABLED,
			PollsChoiceModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUuid_C",
			new String[] { String.class.getName(), Long.class.getName() });

	/**
	 * Returns all the polls choices where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching polls choices
	 */
	@Override
	public List<PollsChoice> findByUuid_C(String uuid, long companyId) {
		return findByUuid_C(uuid, companyId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the polls choices where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link PollsChoiceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of polls choices
	 * @param end the upper bound of the range of polls choices (not inclusive)
	 * @return the range of matching polls choices
	 */
	@Override
	public List<PollsChoice> findByUuid_C(String uuid, long companyId,
		int start, int end) {
		return findByUuid_C(uuid, companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the polls choices where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link PollsChoiceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of polls choices
	 * @param end the upper bound of the range of polls choices (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching polls choices
	 */
	@Override
	public List<PollsChoice> findByUuid_C(String uuid, long companyId,
		int start, int end, OrderByComparator<PollsChoice> orderByComparator) {
		return findByUuid_C(uuid, companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the polls choices where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link PollsChoiceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of polls choices
	 * @param end the upper bound of the range of polls choices (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching polls choices
	 */
	@Override
	public List<PollsChoice> findByUuid_C(String uuid, long companyId,
		int start, int end, OrderByComparator<PollsChoice> orderByComparator,
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

		List<PollsChoice> list = null;

		if (retrieveFromCache) {
			list = (List<PollsChoice>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (PollsChoice pollsChoice : list) {
					if (!Validator.equals(uuid, pollsChoice.getUuid()) ||
							(companyId != pollsChoice.getCompanyId())) {
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

			query.append(_SQL_SELECT_POLLSCHOICE_WHERE);

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
				query.append(PollsChoiceModelImpl.ORDER_BY_JPQL);
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
					list = (List<PollsChoice>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<PollsChoice>)QueryUtil.list(q, getDialect(),
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
	 * Returns the first polls choice in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching polls choice
	 * @throws com.liferay.polls.NoSuchChoiceException if a matching polls choice could not be found
	 */
	@Override
	public PollsChoice findByUuid_C_First(String uuid, long companyId,
		OrderByComparator<PollsChoice> orderByComparator)
		throws NoSuchChoiceException {
		PollsChoice pollsChoice = fetchByUuid_C_First(uuid, companyId,
				orderByComparator);

		if (pollsChoice != null) {
			return pollsChoice;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(", companyId=");
		msg.append(companyId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchChoiceException(msg.toString());
	}

	/**
	 * Returns the first polls choice in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching polls choice, or <code>null</code> if a matching polls choice could not be found
	 */
	@Override
	public PollsChoice fetchByUuid_C_First(String uuid, long companyId,
		OrderByComparator<PollsChoice> orderByComparator) {
		List<PollsChoice> list = findByUuid_C(uuid, companyId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last polls choice in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching polls choice
	 * @throws com.liferay.polls.NoSuchChoiceException if a matching polls choice could not be found
	 */
	@Override
	public PollsChoice findByUuid_C_Last(String uuid, long companyId,
		OrderByComparator<PollsChoice> orderByComparator)
		throws NoSuchChoiceException {
		PollsChoice pollsChoice = fetchByUuid_C_Last(uuid, companyId,
				orderByComparator);

		if (pollsChoice != null) {
			return pollsChoice;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(", companyId=");
		msg.append(companyId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchChoiceException(msg.toString());
	}

	/**
	 * Returns the last polls choice in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching polls choice, or <code>null</code> if a matching polls choice could not be found
	 */
	@Override
	public PollsChoice fetchByUuid_C_Last(String uuid, long companyId,
		OrderByComparator<PollsChoice> orderByComparator) {
		int count = countByUuid_C(uuid, companyId);

		if (count == 0) {
			return null;
		}

		List<PollsChoice> list = findByUuid_C(uuid, companyId, count - 1,
				count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the polls choices before and after the current polls choice in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param choiceId the primary key of the current polls choice
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next polls choice
	 * @throws com.liferay.polls.NoSuchChoiceException if a polls choice with the primary key could not be found
	 */
	@Override
	public PollsChoice[] findByUuid_C_PrevAndNext(long choiceId, String uuid,
		long companyId, OrderByComparator<PollsChoice> orderByComparator)
		throws NoSuchChoiceException {
		PollsChoice pollsChoice = findByPrimaryKey(choiceId);

		Session session = null;

		try {
			session = openSession();

			PollsChoice[] array = new PollsChoiceImpl[3];

			array[0] = getByUuid_C_PrevAndNext(session, pollsChoice, uuid,
					companyId, orderByComparator, true);

			array[1] = pollsChoice;

			array[2] = getByUuid_C_PrevAndNext(session, pollsChoice, uuid,
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

	protected PollsChoice getByUuid_C_PrevAndNext(Session session,
		PollsChoice pollsChoice, String uuid, long companyId,
		OrderByComparator<PollsChoice> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_POLLSCHOICE_WHERE);

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
			query.append(PollsChoiceModelImpl.ORDER_BY_JPQL);
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
			Object[] values = orderByComparator.getOrderByConditionValues(pollsChoice);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<PollsChoice> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the polls choices where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	@Override
	public void removeByUuid_C(String uuid, long companyId) {
		for (PollsChoice pollsChoice : findByUuid_C(uuid, companyId,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(pollsChoice);
		}
	}

	/**
	 * Returns the number of polls choices where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching polls choices
	 */
	@Override
	public int countByUuid_C(String uuid, long companyId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_UUID_C;

		Object[] finderArgs = new Object[] { uuid, companyId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_POLLSCHOICE_WHERE);

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

	private static final String _FINDER_COLUMN_UUID_C_UUID_1 = "pollsChoice.uuid IS NULL AND ";
	private static final String _FINDER_COLUMN_UUID_C_UUID_2 = "pollsChoice.uuid = ? AND ";
	private static final String _FINDER_COLUMN_UUID_C_UUID_3 = "(pollsChoice.uuid IS NULL OR pollsChoice.uuid = '') AND ";
	private static final String _FINDER_COLUMN_UUID_C_COMPANYID_2 = "pollsChoice.companyId = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_QUESTIONID =
		new FinderPath(PollsChoiceModelImpl.ENTITY_CACHE_ENABLED,
			PollsChoiceModelImpl.FINDER_CACHE_ENABLED, PollsChoiceImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByQuestionId",
			new String[] {
				Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_QUESTIONID =
		new FinderPath(PollsChoiceModelImpl.ENTITY_CACHE_ENABLED,
			PollsChoiceModelImpl.FINDER_CACHE_ENABLED, PollsChoiceImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByQuestionId",
			new String[] { Long.class.getName() },
			PollsChoiceModelImpl.QUESTIONID_COLUMN_BITMASK |
			PollsChoiceModelImpl.NAME_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_QUESTIONID = new FinderPath(PollsChoiceModelImpl.ENTITY_CACHE_ENABLED,
			PollsChoiceModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByQuestionId",
			new String[] { Long.class.getName() });

	/**
	 * Returns all the polls choices where questionId = &#63;.
	 *
	 * @param questionId the question ID
	 * @return the matching polls choices
	 */
	@Override
	public List<PollsChoice> findByQuestionId(long questionId) {
		return findByQuestionId(questionId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the polls choices where questionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link PollsChoiceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param questionId the question ID
	 * @param start the lower bound of the range of polls choices
	 * @param end the upper bound of the range of polls choices (not inclusive)
	 * @return the range of matching polls choices
	 */
	@Override
	public List<PollsChoice> findByQuestionId(long questionId, int start,
		int end) {
		return findByQuestionId(questionId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the polls choices where questionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link PollsChoiceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param questionId the question ID
	 * @param start the lower bound of the range of polls choices
	 * @param end the upper bound of the range of polls choices (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching polls choices
	 */
	@Override
	public List<PollsChoice> findByQuestionId(long questionId, int start,
		int end, OrderByComparator<PollsChoice> orderByComparator) {
		return findByQuestionId(questionId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the polls choices where questionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link PollsChoiceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param questionId the question ID
	 * @param start the lower bound of the range of polls choices
	 * @param end the upper bound of the range of polls choices (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching polls choices
	 */
	@Override
	public List<PollsChoice> findByQuestionId(long questionId, int start,
		int end, OrderByComparator<PollsChoice> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_QUESTIONID;
			finderArgs = new Object[] { questionId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_QUESTIONID;
			finderArgs = new Object[] { questionId, start, end, orderByComparator };
		}

		List<PollsChoice> list = null;

		if (retrieveFromCache) {
			list = (List<PollsChoice>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (PollsChoice pollsChoice : list) {
					if ((questionId != pollsChoice.getQuestionId())) {
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

			query.append(_SQL_SELECT_POLLSCHOICE_WHERE);

			query.append(_FINDER_COLUMN_QUESTIONID_QUESTIONID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(PollsChoiceModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(questionId);

				if (!pagination) {
					list = (List<PollsChoice>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<PollsChoice>)QueryUtil.list(q, getDialect(),
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
	 * Returns the first polls choice in the ordered set where questionId = &#63;.
	 *
	 * @param questionId the question ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching polls choice
	 * @throws com.liferay.polls.NoSuchChoiceException if a matching polls choice could not be found
	 */
	@Override
	public PollsChoice findByQuestionId_First(long questionId,
		OrderByComparator<PollsChoice> orderByComparator)
		throws NoSuchChoiceException {
		PollsChoice pollsChoice = fetchByQuestionId_First(questionId,
				orderByComparator);

		if (pollsChoice != null) {
			return pollsChoice;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("questionId=");
		msg.append(questionId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchChoiceException(msg.toString());
	}

	/**
	 * Returns the first polls choice in the ordered set where questionId = &#63;.
	 *
	 * @param questionId the question ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching polls choice, or <code>null</code> if a matching polls choice could not be found
	 */
	@Override
	public PollsChoice fetchByQuestionId_First(long questionId,
		OrderByComparator<PollsChoice> orderByComparator) {
		List<PollsChoice> list = findByQuestionId(questionId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last polls choice in the ordered set where questionId = &#63;.
	 *
	 * @param questionId the question ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching polls choice
	 * @throws com.liferay.polls.NoSuchChoiceException if a matching polls choice could not be found
	 */
	@Override
	public PollsChoice findByQuestionId_Last(long questionId,
		OrderByComparator<PollsChoice> orderByComparator)
		throws NoSuchChoiceException {
		PollsChoice pollsChoice = fetchByQuestionId_Last(questionId,
				orderByComparator);

		if (pollsChoice != null) {
			return pollsChoice;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("questionId=");
		msg.append(questionId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchChoiceException(msg.toString());
	}

	/**
	 * Returns the last polls choice in the ordered set where questionId = &#63;.
	 *
	 * @param questionId the question ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching polls choice, or <code>null</code> if a matching polls choice could not be found
	 */
	@Override
	public PollsChoice fetchByQuestionId_Last(long questionId,
		OrderByComparator<PollsChoice> orderByComparator) {
		int count = countByQuestionId(questionId);

		if (count == 0) {
			return null;
		}

		List<PollsChoice> list = findByQuestionId(questionId, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the polls choices before and after the current polls choice in the ordered set where questionId = &#63;.
	 *
	 * @param choiceId the primary key of the current polls choice
	 * @param questionId the question ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next polls choice
	 * @throws com.liferay.polls.NoSuchChoiceException if a polls choice with the primary key could not be found
	 */
	@Override
	public PollsChoice[] findByQuestionId_PrevAndNext(long choiceId,
		long questionId, OrderByComparator<PollsChoice> orderByComparator)
		throws NoSuchChoiceException {
		PollsChoice pollsChoice = findByPrimaryKey(choiceId);

		Session session = null;

		try {
			session = openSession();

			PollsChoice[] array = new PollsChoiceImpl[3];

			array[0] = getByQuestionId_PrevAndNext(session, pollsChoice,
					questionId, orderByComparator, true);

			array[1] = pollsChoice;

			array[2] = getByQuestionId_PrevAndNext(session, pollsChoice,
					questionId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected PollsChoice getByQuestionId_PrevAndNext(Session session,
		PollsChoice pollsChoice, long questionId,
		OrderByComparator<PollsChoice> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_POLLSCHOICE_WHERE);

		query.append(_FINDER_COLUMN_QUESTIONID_QUESTIONID_2);

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
			query.append(PollsChoiceModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(questionId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(pollsChoice);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<PollsChoice> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the polls choices where questionId = &#63; from the database.
	 *
	 * @param questionId the question ID
	 */
	@Override
	public void removeByQuestionId(long questionId) {
		for (PollsChoice pollsChoice : findByQuestionId(questionId,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(pollsChoice);
		}
	}

	/**
	 * Returns the number of polls choices where questionId = &#63;.
	 *
	 * @param questionId the question ID
	 * @return the number of matching polls choices
	 */
	@Override
	public int countByQuestionId(long questionId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_QUESTIONID;

		Object[] finderArgs = new Object[] { questionId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_POLLSCHOICE_WHERE);

			query.append(_FINDER_COLUMN_QUESTIONID_QUESTIONID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(questionId);

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

	private static final String _FINDER_COLUMN_QUESTIONID_QUESTIONID_2 = "pollsChoice.questionId = ?";
	public static final FinderPath FINDER_PATH_FETCH_BY_Q_N = new FinderPath(PollsChoiceModelImpl.ENTITY_CACHE_ENABLED,
			PollsChoiceModelImpl.FINDER_CACHE_ENABLED, PollsChoiceImpl.class,
			FINDER_CLASS_NAME_ENTITY, "fetchByQ_N",
			new String[] { Long.class.getName(), String.class.getName() },
			PollsChoiceModelImpl.QUESTIONID_COLUMN_BITMASK |
			PollsChoiceModelImpl.NAME_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_Q_N = new FinderPath(PollsChoiceModelImpl.ENTITY_CACHE_ENABLED,
			PollsChoiceModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByQ_N",
			new String[] { Long.class.getName(), String.class.getName() });

	/**
	 * Returns the polls choice where questionId = &#63; and name = &#63; or throws a {@link com.liferay.polls.NoSuchChoiceException} if it could not be found.
	 *
	 * @param questionId the question ID
	 * @param name the name
	 * @return the matching polls choice
	 * @throws com.liferay.polls.NoSuchChoiceException if a matching polls choice could not be found
	 */
	@Override
	public PollsChoice findByQ_N(long questionId, String name)
		throws NoSuchChoiceException {
		PollsChoice pollsChoice = fetchByQ_N(questionId, name);

		if (pollsChoice == null) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("questionId=");
			msg.append(questionId);

			msg.append(", name=");
			msg.append(name);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchChoiceException(msg.toString());
		}

		return pollsChoice;
	}

	/**
	 * Returns the polls choice where questionId = &#63; and name = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param questionId the question ID
	 * @param name the name
	 * @return the matching polls choice, or <code>null</code> if a matching polls choice could not be found
	 */
	@Override
	public PollsChoice fetchByQ_N(long questionId, String name) {
		return fetchByQ_N(questionId, name, true);
	}

	/**
	 * Returns the polls choice where questionId = &#63; and name = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param questionId the question ID
	 * @param name the name
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the matching polls choice, or <code>null</code> if a matching polls choice could not be found
	 */
	@Override
	public PollsChoice fetchByQ_N(long questionId, String name,
		boolean retrieveFromCache) {
		Object[] finderArgs = new Object[] { questionId, name };

		Object result = null;

		if (retrieveFromCache) {
			result = finderCache.getResult(FINDER_PATH_FETCH_BY_Q_N,
					finderArgs, this);
		}

		if (result instanceof PollsChoice) {
			PollsChoice pollsChoice = (PollsChoice)result;

			if ((questionId != pollsChoice.getQuestionId()) ||
					!Validator.equals(name, pollsChoice.getName())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_SELECT_POLLSCHOICE_WHERE);

			query.append(_FINDER_COLUMN_Q_N_QUESTIONID_2);

			boolean bindName = false;

			if (name == null) {
				query.append(_FINDER_COLUMN_Q_N_NAME_1);
			}
			else if (name.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_Q_N_NAME_3);
			}
			else {
				bindName = true;

				query.append(_FINDER_COLUMN_Q_N_NAME_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(questionId);

				if (bindName) {
					qPos.add(name);
				}

				List<PollsChoice> list = q.list();

				if (list.isEmpty()) {
					finderCache.putResult(FINDER_PATH_FETCH_BY_Q_N, finderArgs,
						list);
				}
				else {
					PollsChoice pollsChoice = list.get(0);

					result = pollsChoice;

					cacheResult(pollsChoice);

					if ((pollsChoice.getQuestionId() != questionId) ||
							(pollsChoice.getName() == null) ||
							!pollsChoice.getName().equals(name)) {
						finderCache.putResult(FINDER_PATH_FETCH_BY_Q_N,
							finderArgs, pollsChoice);
					}
				}
			}
			catch (Exception e) {
				finderCache.removeResult(FINDER_PATH_FETCH_BY_Q_N, finderArgs);

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
			return (PollsChoice)result;
		}
	}

	/**
	 * Removes the polls choice where questionId = &#63; and name = &#63; from the database.
	 *
	 * @param questionId the question ID
	 * @param name the name
	 * @return the polls choice that was removed
	 */
	@Override
	public PollsChoice removeByQ_N(long questionId, String name)
		throws NoSuchChoiceException {
		PollsChoice pollsChoice = findByQ_N(questionId, name);

		return remove(pollsChoice);
	}

	/**
	 * Returns the number of polls choices where questionId = &#63; and name = &#63;.
	 *
	 * @param questionId the question ID
	 * @param name the name
	 * @return the number of matching polls choices
	 */
	@Override
	public int countByQ_N(long questionId, String name) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_Q_N;

		Object[] finderArgs = new Object[] { questionId, name };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_POLLSCHOICE_WHERE);

			query.append(_FINDER_COLUMN_Q_N_QUESTIONID_2);

			boolean bindName = false;

			if (name == null) {
				query.append(_FINDER_COLUMN_Q_N_NAME_1);
			}
			else if (name.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_Q_N_NAME_3);
			}
			else {
				bindName = true;

				query.append(_FINDER_COLUMN_Q_N_NAME_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(questionId);

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

	private static final String _FINDER_COLUMN_Q_N_QUESTIONID_2 = "pollsChoice.questionId = ? AND ";
	private static final String _FINDER_COLUMN_Q_N_NAME_1 = "pollsChoice.name IS NULL";
	private static final String _FINDER_COLUMN_Q_N_NAME_2 = "pollsChoice.name = ?";
	private static final String _FINDER_COLUMN_Q_N_NAME_3 = "(pollsChoice.name IS NULL OR pollsChoice.name = '')";

	public PollsChoicePersistenceImpl() {
		setModelClass(PollsChoice.class);
	}

	/**
	 * Caches the polls choice in the entity cache if it is enabled.
	 *
	 * @param pollsChoice the polls choice
	 */
	@Override
	public void cacheResult(PollsChoice pollsChoice) {
		entityCache.putResult(PollsChoiceModelImpl.ENTITY_CACHE_ENABLED,
			PollsChoiceImpl.class, pollsChoice.getPrimaryKey(), pollsChoice);

		finderCache.putResult(FINDER_PATH_FETCH_BY_UUID_G,
			new Object[] { pollsChoice.getUuid(), pollsChoice.getGroupId() },
			pollsChoice);

		finderCache.putResult(FINDER_PATH_FETCH_BY_Q_N,
			new Object[] { pollsChoice.getQuestionId(), pollsChoice.getName() },
			pollsChoice);

		pollsChoice.resetOriginalValues();
	}

	/**
	 * Caches the polls choices in the entity cache if it is enabled.
	 *
	 * @param pollsChoices the polls choices
	 */
	@Override
	public void cacheResult(List<PollsChoice> pollsChoices) {
		for (PollsChoice pollsChoice : pollsChoices) {
			if (entityCache.getResult(
						PollsChoiceModelImpl.ENTITY_CACHE_ENABLED,
						PollsChoiceImpl.class, pollsChoice.getPrimaryKey()) == null) {
				cacheResult(pollsChoice);
			}
			else {
				pollsChoice.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all polls choices.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(PollsChoiceImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the polls choice.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(PollsChoice pollsChoice) {
		entityCache.removeResult(PollsChoiceModelImpl.ENTITY_CACHE_ENABLED,
			PollsChoiceImpl.class, pollsChoice.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache((PollsChoiceModelImpl)pollsChoice);
	}

	@Override
	public void clearCache(List<PollsChoice> pollsChoices) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (PollsChoice pollsChoice : pollsChoices) {
			entityCache.removeResult(PollsChoiceModelImpl.ENTITY_CACHE_ENABLED,
				PollsChoiceImpl.class, pollsChoice.getPrimaryKey());

			clearUniqueFindersCache((PollsChoiceModelImpl)pollsChoice);
		}
	}

	protected void cacheUniqueFindersCache(
		PollsChoiceModelImpl pollsChoiceModelImpl, boolean isNew) {
		if (isNew) {
			Object[] args = new Object[] {
					pollsChoiceModelImpl.getUuid(),
					pollsChoiceModelImpl.getGroupId()
				};

			finderCache.putResult(FINDER_PATH_COUNT_BY_UUID_G, args,
				Long.valueOf(1));
			finderCache.putResult(FINDER_PATH_FETCH_BY_UUID_G, args,
				pollsChoiceModelImpl);

			args = new Object[] {
					pollsChoiceModelImpl.getQuestionId(),
					pollsChoiceModelImpl.getName()
				};

			finderCache.putResult(FINDER_PATH_COUNT_BY_Q_N, args,
				Long.valueOf(1));
			finderCache.putResult(FINDER_PATH_FETCH_BY_Q_N, args,
				pollsChoiceModelImpl);
		}
		else {
			if ((pollsChoiceModelImpl.getColumnBitmask() &
					FINDER_PATH_FETCH_BY_UUID_G.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						pollsChoiceModelImpl.getUuid(),
						pollsChoiceModelImpl.getGroupId()
					};

				finderCache.putResult(FINDER_PATH_COUNT_BY_UUID_G, args,
					Long.valueOf(1));
				finderCache.putResult(FINDER_PATH_FETCH_BY_UUID_G, args,
					pollsChoiceModelImpl);
			}

			if ((pollsChoiceModelImpl.getColumnBitmask() &
					FINDER_PATH_FETCH_BY_Q_N.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						pollsChoiceModelImpl.getQuestionId(),
						pollsChoiceModelImpl.getName()
					};

				finderCache.putResult(FINDER_PATH_COUNT_BY_Q_N, args,
					Long.valueOf(1));
				finderCache.putResult(FINDER_PATH_FETCH_BY_Q_N, args,
					pollsChoiceModelImpl);
			}
		}
	}

	protected void clearUniqueFindersCache(
		PollsChoiceModelImpl pollsChoiceModelImpl) {
		Object[] args = new Object[] {
				pollsChoiceModelImpl.getUuid(),
				pollsChoiceModelImpl.getGroupId()
			};

		finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID_G, args);
		finderCache.removeResult(FINDER_PATH_FETCH_BY_UUID_G, args);

		if ((pollsChoiceModelImpl.getColumnBitmask() &
				FINDER_PATH_FETCH_BY_UUID_G.getColumnBitmask()) != 0) {
			args = new Object[] {
					pollsChoiceModelImpl.getOriginalUuid(),
					pollsChoiceModelImpl.getOriginalGroupId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID_G, args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_UUID_G, args);
		}

		args = new Object[] {
				pollsChoiceModelImpl.getQuestionId(),
				pollsChoiceModelImpl.getName()
			};

		finderCache.removeResult(FINDER_PATH_COUNT_BY_Q_N, args);
		finderCache.removeResult(FINDER_PATH_FETCH_BY_Q_N, args);

		if ((pollsChoiceModelImpl.getColumnBitmask() &
				FINDER_PATH_FETCH_BY_Q_N.getColumnBitmask()) != 0) {
			args = new Object[] {
					pollsChoiceModelImpl.getOriginalQuestionId(),
					pollsChoiceModelImpl.getOriginalName()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_Q_N, args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_Q_N, args);
		}
	}

	/**
	 * Creates a new polls choice with the primary key. Does not add the polls choice to the database.
	 *
	 * @param choiceId the primary key for the new polls choice
	 * @return the new polls choice
	 */
	@Override
	public PollsChoice create(long choiceId) {
		PollsChoice pollsChoice = new PollsChoiceImpl();

		pollsChoice.setNew(true);
		pollsChoice.setPrimaryKey(choiceId);

		String uuid = PortalUUIDUtil.generate();

		pollsChoice.setUuid(uuid);

		return pollsChoice;
	}

	/**
	 * Removes the polls choice with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param choiceId the primary key of the polls choice
	 * @return the polls choice that was removed
	 * @throws com.liferay.polls.NoSuchChoiceException if a polls choice with the primary key could not be found
	 */
	@Override
	public PollsChoice remove(long choiceId) throws NoSuchChoiceException {
		return remove((Serializable)choiceId);
	}

	/**
	 * Removes the polls choice with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the polls choice
	 * @return the polls choice that was removed
	 * @throws com.liferay.polls.NoSuchChoiceException if a polls choice with the primary key could not be found
	 */
	@Override
	public PollsChoice remove(Serializable primaryKey)
		throws NoSuchChoiceException {
		Session session = null;

		try {
			session = openSession();

			PollsChoice pollsChoice = (PollsChoice)session.get(PollsChoiceImpl.class,
					primaryKey);

			if (pollsChoice == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchChoiceException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(pollsChoice);
		}
		catch (NoSuchChoiceException nsee) {
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
	protected PollsChoice removeImpl(PollsChoice pollsChoice) {
		pollsChoice = toUnwrappedModel(pollsChoice);

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(pollsChoice)) {
				pollsChoice = (PollsChoice)session.get(PollsChoiceImpl.class,
						pollsChoice.getPrimaryKeyObj());
			}

			if (pollsChoice != null) {
				session.delete(pollsChoice);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (pollsChoice != null) {
			clearCache(pollsChoice);
		}

		return pollsChoice;
	}

	@Override
	public PollsChoice updateImpl(PollsChoice pollsChoice) {
		pollsChoice = toUnwrappedModel(pollsChoice);

		boolean isNew = pollsChoice.isNew();

		PollsChoiceModelImpl pollsChoiceModelImpl = (PollsChoiceModelImpl)pollsChoice;

		if (Validator.isNull(pollsChoice.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			pollsChoice.setUuid(uuid);
		}

		ServiceContext serviceContext = ServiceContextThreadLocal.getServiceContext();

		Date now = new Date();

		if (isNew && (pollsChoice.getCreateDate() == null)) {
			if (serviceContext == null) {
				pollsChoice.setCreateDate(now);
			}
			else {
				pollsChoice.setCreateDate(serviceContext.getCreateDate(now));
			}
		}

		if (!pollsChoiceModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				pollsChoice.setModifiedDate(now);
			}
			else {
				pollsChoice.setModifiedDate(serviceContext.getModifiedDate(now));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (pollsChoice.isNew()) {
				session.save(pollsChoice);

				pollsChoice.setNew(false);
			}
			else {
				pollsChoice = (PollsChoice)session.merge(pollsChoice);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (isNew || !PollsChoiceModelImpl.COLUMN_BITMASK_ENABLED) {
			finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}

		else {
			if ((pollsChoiceModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						pollsChoiceModelImpl.getOriginalUuid()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID,
					args);

				args = new Object[] { pollsChoiceModelImpl.getUuid() };

				finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID,
					args);
			}

			if ((pollsChoiceModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						pollsChoiceModelImpl.getOriginalUuid(),
						pollsChoiceModelImpl.getOriginalCompanyId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID_C, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C,
					args);

				args = new Object[] {
						pollsChoiceModelImpl.getUuid(),
						pollsChoiceModelImpl.getCompanyId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_UUID_C, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_UUID_C,
					args);
			}

			if ((pollsChoiceModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_QUESTIONID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						pollsChoiceModelImpl.getOriginalQuestionId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_QUESTIONID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_QUESTIONID,
					args);

				args = new Object[] { pollsChoiceModelImpl.getQuestionId() };

				finderCache.removeResult(FINDER_PATH_COUNT_BY_QUESTIONID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_QUESTIONID,
					args);
			}
		}

		entityCache.putResult(PollsChoiceModelImpl.ENTITY_CACHE_ENABLED,
			PollsChoiceImpl.class, pollsChoice.getPrimaryKey(), pollsChoice,
			false);

		clearUniqueFindersCache(pollsChoiceModelImpl);
		cacheUniqueFindersCache(pollsChoiceModelImpl, isNew);

		pollsChoice.resetOriginalValues();

		return pollsChoice;
	}

	protected PollsChoice toUnwrappedModel(PollsChoice pollsChoice) {
		if (pollsChoice instanceof PollsChoiceImpl) {
			return pollsChoice;
		}

		PollsChoiceImpl pollsChoiceImpl = new PollsChoiceImpl();

		pollsChoiceImpl.setNew(pollsChoice.isNew());
		pollsChoiceImpl.setPrimaryKey(pollsChoice.getPrimaryKey());

		pollsChoiceImpl.setUuid(pollsChoice.getUuid());
		pollsChoiceImpl.setChoiceId(pollsChoice.getChoiceId());
		pollsChoiceImpl.setGroupId(pollsChoice.getGroupId());
		pollsChoiceImpl.setCompanyId(pollsChoice.getCompanyId());
		pollsChoiceImpl.setUserId(pollsChoice.getUserId());
		pollsChoiceImpl.setUserName(pollsChoice.getUserName());
		pollsChoiceImpl.setCreateDate(pollsChoice.getCreateDate());
		pollsChoiceImpl.setModifiedDate(pollsChoice.getModifiedDate());
		pollsChoiceImpl.setQuestionId(pollsChoice.getQuestionId());
		pollsChoiceImpl.setName(pollsChoice.getName());
		pollsChoiceImpl.setDescription(pollsChoice.getDescription());
		pollsChoiceImpl.setLastPublishDate(pollsChoice.getLastPublishDate());

		return pollsChoiceImpl;
	}

	/**
	 * Returns the polls choice with the primary key or throws a {@link com.liferay.portal.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the polls choice
	 * @return the polls choice
	 * @throws com.liferay.polls.NoSuchChoiceException if a polls choice with the primary key could not be found
	 */
	@Override
	public PollsChoice findByPrimaryKey(Serializable primaryKey)
		throws NoSuchChoiceException {
		PollsChoice pollsChoice = fetchByPrimaryKey(primaryKey);

		if (pollsChoice == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchChoiceException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				primaryKey);
		}

		return pollsChoice;
	}

	/**
	 * Returns the polls choice with the primary key or throws a {@link com.liferay.polls.NoSuchChoiceException} if it could not be found.
	 *
	 * @param choiceId the primary key of the polls choice
	 * @return the polls choice
	 * @throws com.liferay.polls.NoSuchChoiceException if a polls choice with the primary key could not be found
	 */
	@Override
	public PollsChoice findByPrimaryKey(long choiceId)
		throws NoSuchChoiceException {
		return findByPrimaryKey((Serializable)choiceId);
	}

	/**
	 * Returns the polls choice with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the polls choice
	 * @return the polls choice, or <code>null</code> if a polls choice with the primary key could not be found
	 */
	@Override
	public PollsChoice fetchByPrimaryKey(Serializable primaryKey) {
		PollsChoice pollsChoice = (PollsChoice)entityCache.getResult(PollsChoiceModelImpl.ENTITY_CACHE_ENABLED,
				PollsChoiceImpl.class, primaryKey);

		if (pollsChoice == _nullPollsChoice) {
			return null;
		}

		if (pollsChoice == null) {
			Session session = null;

			try {
				session = openSession();

				pollsChoice = (PollsChoice)session.get(PollsChoiceImpl.class,
						primaryKey);

				if (pollsChoice != null) {
					cacheResult(pollsChoice);
				}
				else {
					entityCache.putResult(PollsChoiceModelImpl.ENTITY_CACHE_ENABLED,
						PollsChoiceImpl.class, primaryKey, _nullPollsChoice);
				}
			}
			catch (Exception e) {
				entityCache.removeResult(PollsChoiceModelImpl.ENTITY_CACHE_ENABLED,
					PollsChoiceImpl.class, primaryKey);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return pollsChoice;
	}

	/**
	 * Returns the polls choice with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param choiceId the primary key of the polls choice
	 * @return the polls choice, or <code>null</code> if a polls choice with the primary key could not be found
	 */
	@Override
	public PollsChoice fetchByPrimaryKey(long choiceId) {
		return fetchByPrimaryKey((Serializable)choiceId);
	}

	@Override
	public Map<Serializable, PollsChoice> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {
		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, PollsChoice> map = new HashMap<Serializable, PollsChoice>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			PollsChoice pollsChoice = fetchByPrimaryKey(primaryKey);

			if (pollsChoice != null) {
				map.put(primaryKey, pollsChoice);
			}

			return map;
		}

		Set<Serializable> uncachedPrimaryKeys = null;

		for (Serializable primaryKey : primaryKeys) {
			PollsChoice pollsChoice = (PollsChoice)entityCache.getResult(PollsChoiceModelImpl.ENTITY_CACHE_ENABLED,
					PollsChoiceImpl.class, primaryKey);

			if (pollsChoice == null) {
				if (uncachedPrimaryKeys == null) {
					uncachedPrimaryKeys = new HashSet<Serializable>();
				}

				uncachedPrimaryKeys.add(primaryKey);
			}
			else {
				map.put(primaryKey, pollsChoice);
			}
		}

		if (uncachedPrimaryKeys == null) {
			return map;
		}

		StringBundler query = new StringBundler((uncachedPrimaryKeys.size() * 2) +
				1);

		query.append(_SQL_SELECT_POLLSCHOICE_WHERE_PKS_IN);

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

			for (PollsChoice pollsChoice : (List<PollsChoice>)q.list()) {
				map.put(pollsChoice.getPrimaryKeyObj(), pollsChoice);

				cacheResult(pollsChoice);

				uncachedPrimaryKeys.remove(pollsChoice.getPrimaryKeyObj());
			}

			for (Serializable primaryKey : uncachedPrimaryKeys) {
				entityCache.putResult(PollsChoiceModelImpl.ENTITY_CACHE_ENABLED,
					PollsChoiceImpl.class, primaryKey, _nullPollsChoice);
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
	 * Returns all the polls choices.
	 *
	 * @return the polls choices
	 */
	@Override
	public List<PollsChoice> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the polls choices.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link PollsChoiceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of polls choices
	 * @param end the upper bound of the range of polls choices (not inclusive)
	 * @return the range of polls choices
	 */
	@Override
	public List<PollsChoice> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the polls choices.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link PollsChoiceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of polls choices
	 * @param end the upper bound of the range of polls choices (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of polls choices
	 */
	@Override
	public List<PollsChoice> findAll(int start, int end,
		OrderByComparator<PollsChoice> orderByComparator) {
		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the polls choices.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link PollsChoiceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of polls choices
	 * @param end the upper bound of the range of polls choices (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of polls choices
	 */
	@Override
	public List<PollsChoice> findAll(int start, int end,
		OrderByComparator<PollsChoice> orderByComparator,
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

		List<PollsChoice> list = null;

		if (retrieveFromCache) {
			list = (List<PollsChoice>)finderCache.getResult(finderPath,
					finderArgs, this);
		}

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 3));

				query.append(_SQL_SELECT_POLLSCHOICE);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_POLLSCHOICE;

				if (pagination) {
					sql = sql.concat(PollsChoiceModelImpl.ORDER_BY_JPQL);
				}
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (!pagination) {
					list = (List<PollsChoice>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<PollsChoice>)QueryUtil.list(q, getDialect(),
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
	 * Removes all the polls choices from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (PollsChoice pollsChoice : findAll()) {
			remove(pollsChoice);
		}
	}

	/**
	 * Returns the number of polls choices.
	 *
	 * @return the number of polls choices
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_POLLSCHOICE);

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
		return PollsChoiceModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the polls choice persistence.
	 */
	public void afterPropertiesSet() {
	}

	public void destroy() {
		entityCache.removeCache(PollsChoiceImpl.class.getName());
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
	private static final String _SQL_SELECT_POLLSCHOICE = "SELECT pollsChoice FROM PollsChoice pollsChoice";
	private static final String _SQL_SELECT_POLLSCHOICE_WHERE_PKS_IN = "SELECT pollsChoice FROM PollsChoice pollsChoice WHERE choiceId IN (";
	private static final String _SQL_SELECT_POLLSCHOICE_WHERE = "SELECT pollsChoice FROM PollsChoice pollsChoice WHERE ";
	private static final String _SQL_COUNT_POLLSCHOICE = "SELECT COUNT(pollsChoice) FROM PollsChoice pollsChoice";
	private static final String _SQL_COUNT_POLLSCHOICE_WHERE = "SELECT COUNT(pollsChoice) FROM PollsChoice pollsChoice WHERE ";
	private static final String _ORDER_BY_ENTITY_ALIAS = "pollsChoice.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No PollsChoice exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No PollsChoice exists with the key {";
	private static final Log _log = LogFactoryUtil.getLog(PollsChoicePersistenceImpl.class);
	private static final Set<String> _badColumnNames = SetUtil.fromArray(new String[] {
				"uuid"
			});
	private static final PollsChoice _nullPollsChoice = new PollsChoiceImpl() {
			@Override
			public Object clone() {
				return this;
			}

			@Override
			public CacheModel<PollsChoice> toCacheModel() {
				return _nullPollsChoiceCacheModel;
			}
		};

	private static final CacheModel<PollsChoice> _nullPollsChoiceCacheModel = new CacheModel<PollsChoice>() {
			@Override
			public PollsChoice toEntityModel() {
				return _nullPollsChoice;
			}
		};
}