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

import com.liferay.petra.string.StringBundler;
import com.liferay.polls.exception.NoSuchChoiceException;
import com.liferay.polls.model.PollsChoice;
import com.liferay.polls.model.PollsChoiceTable;
import com.liferay.polls.model.impl.PollsChoiceImpl;
import com.liferay.polls.model.impl.PollsChoiceModelImpl;
import com.liferay.polls.service.persistence.PollsChoicePersistence;
import com.liferay.polls.service.persistence.impl.constants.PollsPersistenceConstants;
import com.liferay.portal.kernel.configuration.Configuration;
import com.liferay.portal.kernel.dao.orm.ArgumentsResolver;
import com.liferay.portal.kernel.dao.orm.EntityCache;
import com.liferay.portal.kernel.dao.orm.FinderCache;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.dao.orm.SessionFactory;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.service.persistence.BasePersistence;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;

import java.io.Serializable;

import java.lang.reflect.InvocationHandler;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import javax.sql.DataSource;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * The persistence implementation for the polls choice service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
@Component(service = {PollsChoicePersistence.class, BasePersistence.class})
public class PollsChoicePersistenceImpl
	extends BasePersistenceImpl<PollsChoice> implements PollsChoicePersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>PollsChoiceUtil</code> to access the polls choice persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		PollsChoiceImpl.class.getName();

	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List1";

	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List2";

	private FinderPath _finderPathWithPaginationFindAll;
	private FinderPath _finderPathWithoutPaginationFindAll;
	private FinderPath _finderPathCountAll;
	private FinderPath _finderPathWithPaginationFindByUuid;
	private FinderPath _finderPathWithoutPaginationFindByUuid;
	private FinderPath _finderPathCountByUuid;

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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>PollsChoiceModelImpl</code>.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>PollsChoiceModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of polls choices
	 * @param end the upper bound of the range of polls choices (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching polls choices
	 */
	@Override
	public List<PollsChoice> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<PollsChoice> orderByComparator) {

		return findByUuid(uuid, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the polls choices where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>PollsChoiceModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of polls choices
	 * @param end the upper bound of the range of polls choices (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching polls choices
	 */
	@Override
	public List<PollsChoice> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<PollsChoice> orderByComparator,
		boolean useFinderCache) {

		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByUuid;
				finderArgs = new Object[] {uuid};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByUuid;
			finderArgs = new Object[] {uuid, start, end, orderByComparator};
		}

		List<PollsChoice> list = null;

		if (useFinderCache) {
			list = (List<PollsChoice>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (PollsChoice pollsChoice : list) {
					if (!uuid.equals(pollsChoice.getUuid())) {
						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler sb = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					3 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				sb = new StringBundler(3);
			}

			sb.append(_SQL_SELECT_POLLSCHOICE_WHERE);

			boolean bindUuid = false;

			if (uuid.isEmpty()) {
				sb.append(_FINDER_COLUMN_UUID_UUID_3);
			}
			else {
				bindUuid = true;

				sb.append(_FINDER_COLUMN_UUID_UUID_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(PollsChoiceModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindUuid) {
					queryPos.add(uuid);
				}

				list = (List<PollsChoice>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					finderCache.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception exception) {
				throw processException(exception);
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
	 * @throws NoSuchChoiceException if a matching polls choice could not be found
	 */
	@Override
	public PollsChoice findByUuid_First(
			String uuid, OrderByComparator<PollsChoice> orderByComparator)
		throws NoSuchChoiceException {

		PollsChoice pollsChoice = fetchByUuid_First(uuid, orderByComparator);

		if (pollsChoice != null) {
			return pollsChoice;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append("}");

		throw new NoSuchChoiceException(sb.toString());
	}

	/**
	 * Returns the first polls choice in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching polls choice, or <code>null</code> if a matching polls choice could not be found
	 */
	@Override
	public PollsChoice fetchByUuid_First(
		String uuid, OrderByComparator<PollsChoice> orderByComparator) {

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
	 * @throws NoSuchChoiceException if a matching polls choice could not be found
	 */
	@Override
	public PollsChoice findByUuid_Last(
			String uuid, OrderByComparator<PollsChoice> orderByComparator)
		throws NoSuchChoiceException {

		PollsChoice pollsChoice = fetchByUuid_Last(uuid, orderByComparator);

		if (pollsChoice != null) {
			return pollsChoice;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append("}");

		throw new NoSuchChoiceException(sb.toString());
	}

	/**
	 * Returns the last polls choice in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching polls choice, or <code>null</code> if a matching polls choice could not be found
	 */
	@Override
	public PollsChoice fetchByUuid_Last(
		String uuid, OrderByComparator<PollsChoice> orderByComparator) {

		int count = countByUuid(uuid);

		if (count == 0) {
			return null;
		}

		List<PollsChoice> list = findByUuid(
			uuid, count - 1, count, orderByComparator);

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
	 * @throws NoSuchChoiceException if a polls choice with the primary key could not be found
	 */
	@Override
	public PollsChoice[] findByUuid_PrevAndNext(
			long choiceId, String uuid,
			OrderByComparator<PollsChoice> orderByComparator)
		throws NoSuchChoiceException {

		uuid = Objects.toString(uuid, "");

		PollsChoice pollsChoice = findByPrimaryKey(choiceId);

		Session session = null;

		try {
			session = openSession();

			PollsChoice[] array = new PollsChoiceImpl[3];

			array[0] = getByUuid_PrevAndNext(
				session, pollsChoice, uuid, orderByComparator, true);

			array[1] = pollsChoice;

			array[2] = getByUuid_PrevAndNext(
				session, pollsChoice, uuid, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected PollsChoice getByUuid_PrevAndNext(
		Session session, PollsChoice pollsChoice, String uuid,
		OrderByComparator<PollsChoice> orderByComparator, boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(3);
		}

		sb.append(_SQL_SELECT_POLLSCHOICE_WHERE);

		boolean bindUuid = false;

		if (uuid.isEmpty()) {
			sb.append(_FINDER_COLUMN_UUID_UUID_3);
		}
		else {
			bindUuid = true;

			sb.append(_FINDER_COLUMN_UUID_UUID_2);
		}

		if (orderByComparator != null) {
			String[] orderByConditionFields =
				orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				sb.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				sb.append(_ORDER_BY_ENTITY_ALIAS);
				sb.append(orderByConditionFields[i]);

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						sb.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(WHERE_GREATER_THAN);
					}
					else {
						sb.append(WHERE_LESSER_THAN);
					}
				}
			}

			sb.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				sb.append(_ORDER_BY_ENTITY_ALIAS);
				sb.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						sb.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(ORDER_BY_ASC);
					}
					else {
						sb.append(ORDER_BY_DESC);
					}
				}
			}
		}
		else {
			sb.append(PollsChoiceModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		if (bindUuid) {
			queryPos.add(uuid);
		}

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(pollsChoice)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<PollsChoice> list = query.list();

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
		for (PollsChoice pollsChoice :
				findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

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
		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = _finderPathCountByUuid;

		Object[] finderArgs = new Object[] {uuid};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_POLLSCHOICE_WHERE);

			boolean bindUuid = false;

			if (uuid.isEmpty()) {
				sb.append(_FINDER_COLUMN_UUID_UUID_3);
			}
			else {
				bindUuid = true;

				sb.append(_FINDER_COLUMN_UUID_UUID_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindUuid) {
					queryPos.add(uuid);
				}

				count = (Long)query.uniqueResult();

				finderCache.putResult(finderPath, finderArgs, count);
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_UUID_UUID_2 =
		"pollsChoice.uuid = ?";

	private static final String _FINDER_COLUMN_UUID_UUID_3 =
		"(pollsChoice.uuid IS NULL OR pollsChoice.uuid = '')";

	private FinderPath _finderPathFetchByUUID_G;
	private FinderPath _finderPathCountByUUID_G;

	/**
	 * Returns the polls choice where uuid = &#63; and groupId = &#63; or throws a <code>NoSuchChoiceException</code> if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching polls choice
	 * @throws NoSuchChoiceException if a matching polls choice could not be found
	 */
	@Override
	public PollsChoice findByUUID_G(String uuid, long groupId)
		throws NoSuchChoiceException {

		PollsChoice pollsChoice = fetchByUUID_G(uuid, groupId);

		if (pollsChoice == null) {
			StringBundler sb = new StringBundler(6);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("uuid=");
			sb.append(uuid);

			sb.append(", groupId=");
			sb.append(groupId);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchChoiceException(sb.toString());
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
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching polls choice, or <code>null</code> if a matching polls choice could not be found
	 */
	@Override
	public PollsChoice fetchByUUID_G(
		String uuid, long groupId, boolean useFinderCache) {

		uuid = Objects.toString(uuid, "");

		Object[] finderArgs = null;

		if (useFinderCache) {
			finderArgs = new Object[] {uuid, groupId};
		}

		Object result = null;

		if (useFinderCache) {
			result = finderCache.getResult(
				_finderPathFetchByUUID_G, finderArgs);
		}

		if (result instanceof PollsChoice) {
			PollsChoice pollsChoice = (PollsChoice)result;

			if (!Objects.equals(uuid, pollsChoice.getUuid()) ||
				(groupId != pollsChoice.getGroupId())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_SELECT_POLLSCHOICE_WHERE);

			boolean bindUuid = false;

			if (uuid.isEmpty()) {
				sb.append(_FINDER_COLUMN_UUID_G_UUID_3);
			}
			else {
				bindUuid = true;

				sb.append(_FINDER_COLUMN_UUID_G_UUID_2);
			}

			sb.append(_FINDER_COLUMN_UUID_G_GROUPID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindUuid) {
					queryPos.add(uuid);
				}

				queryPos.add(groupId);

				List<PollsChoice> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache) {
						finderCache.putResult(
							_finderPathFetchByUUID_G, finderArgs, list);
					}
				}
				else {
					PollsChoice pollsChoice = list.get(0);

					result = pollsChoice;

					cacheResult(pollsChoice);
				}
			}
			catch (Exception exception) {
				throw processException(exception);
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
		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = _finderPathCountByUUID_G;

		Object[] finderArgs = new Object[] {uuid, groupId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_POLLSCHOICE_WHERE);

			boolean bindUuid = false;

			if (uuid.isEmpty()) {
				sb.append(_FINDER_COLUMN_UUID_G_UUID_3);
			}
			else {
				bindUuid = true;

				sb.append(_FINDER_COLUMN_UUID_G_UUID_2);
			}

			sb.append(_FINDER_COLUMN_UUID_G_GROUPID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindUuid) {
					queryPos.add(uuid);
				}

				queryPos.add(groupId);

				count = (Long)query.uniqueResult();

				finderCache.putResult(finderPath, finderArgs, count);
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_UUID_G_UUID_2 =
		"pollsChoice.uuid = ? AND ";

	private static final String _FINDER_COLUMN_UUID_G_UUID_3 =
		"(pollsChoice.uuid IS NULL OR pollsChoice.uuid = '') AND ";

	private static final String _FINDER_COLUMN_UUID_G_GROUPID_2 =
		"pollsChoice.groupId = ?";

	private FinderPath _finderPathWithPaginationFindByUuid_C;
	private FinderPath _finderPathWithoutPaginationFindByUuid_C;
	private FinderPath _finderPathCountByUuid_C;

	/**
	 * Returns all the polls choices where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching polls choices
	 */
	@Override
	public List<PollsChoice> findByUuid_C(String uuid, long companyId) {
		return findByUuid_C(
			uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the polls choices where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>PollsChoiceModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of polls choices
	 * @param end the upper bound of the range of polls choices (not inclusive)
	 * @return the range of matching polls choices
	 */
	@Override
	public List<PollsChoice> findByUuid_C(
		String uuid, long companyId, int start, int end) {

		return findByUuid_C(uuid, companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the polls choices where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>PollsChoiceModelImpl</code>.
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
	public List<PollsChoice> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<PollsChoice> orderByComparator) {

		return findByUuid_C(
			uuid, companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the polls choices where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>PollsChoiceModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of polls choices
	 * @param end the upper bound of the range of polls choices (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching polls choices
	 */
	@Override
	public List<PollsChoice> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<PollsChoice> orderByComparator,
		boolean useFinderCache) {

		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByUuid_C;
				finderArgs = new Object[] {uuid, companyId};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByUuid_C;
			finderArgs = new Object[] {
				uuid, companyId, start, end, orderByComparator
			};
		}

		List<PollsChoice> list = null;

		if (useFinderCache) {
			list = (List<PollsChoice>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (PollsChoice pollsChoice : list) {
					if (!uuid.equals(pollsChoice.getUuid()) ||
						(companyId != pollsChoice.getCompanyId())) {

						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler sb = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					4 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				sb = new StringBundler(4);
			}

			sb.append(_SQL_SELECT_POLLSCHOICE_WHERE);

			boolean bindUuid = false;

			if (uuid.isEmpty()) {
				sb.append(_FINDER_COLUMN_UUID_C_UUID_3);
			}
			else {
				bindUuid = true;

				sb.append(_FINDER_COLUMN_UUID_C_UUID_2);
			}

			sb.append(_FINDER_COLUMN_UUID_C_COMPANYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(PollsChoiceModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindUuid) {
					queryPos.add(uuid);
				}

				queryPos.add(companyId);

				list = (List<PollsChoice>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					finderCache.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception exception) {
				throw processException(exception);
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
	 * @throws NoSuchChoiceException if a matching polls choice could not be found
	 */
	@Override
	public PollsChoice findByUuid_C_First(
			String uuid, long companyId,
			OrderByComparator<PollsChoice> orderByComparator)
		throws NoSuchChoiceException {

		PollsChoice pollsChoice = fetchByUuid_C_First(
			uuid, companyId, orderByComparator);

		if (pollsChoice != null) {
			return pollsChoice;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append(", companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchChoiceException(sb.toString());
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
	public PollsChoice fetchByUuid_C_First(
		String uuid, long companyId,
		OrderByComparator<PollsChoice> orderByComparator) {

		List<PollsChoice> list = findByUuid_C(
			uuid, companyId, 0, 1, orderByComparator);

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
	 * @throws NoSuchChoiceException if a matching polls choice could not be found
	 */
	@Override
	public PollsChoice findByUuid_C_Last(
			String uuid, long companyId,
			OrderByComparator<PollsChoice> orderByComparator)
		throws NoSuchChoiceException {

		PollsChoice pollsChoice = fetchByUuid_C_Last(
			uuid, companyId, orderByComparator);

		if (pollsChoice != null) {
			return pollsChoice;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append(", companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchChoiceException(sb.toString());
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
	public PollsChoice fetchByUuid_C_Last(
		String uuid, long companyId,
		OrderByComparator<PollsChoice> orderByComparator) {

		int count = countByUuid_C(uuid, companyId);

		if (count == 0) {
			return null;
		}

		List<PollsChoice> list = findByUuid_C(
			uuid, companyId, count - 1, count, orderByComparator);

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
	 * @throws NoSuchChoiceException if a polls choice with the primary key could not be found
	 */
	@Override
	public PollsChoice[] findByUuid_C_PrevAndNext(
			long choiceId, String uuid, long companyId,
			OrderByComparator<PollsChoice> orderByComparator)
		throws NoSuchChoiceException {

		uuid = Objects.toString(uuid, "");

		PollsChoice pollsChoice = findByPrimaryKey(choiceId);

		Session session = null;

		try {
			session = openSession();

			PollsChoice[] array = new PollsChoiceImpl[3];

			array[0] = getByUuid_C_PrevAndNext(
				session, pollsChoice, uuid, companyId, orderByComparator, true);

			array[1] = pollsChoice;

			array[2] = getByUuid_C_PrevAndNext(
				session, pollsChoice, uuid, companyId, orderByComparator,
				false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected PollsChoice getByUuid_C_PrevAndNext(
		Session session, PollsChoice pollsChoice, String uuid, long companyId,
		OrderByComparator<PollsChoice> orderByComparator, boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				5 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(4);
		}

		sb.append(_SQL_SELECT_POLLSCHOICE_WHERE);

		boolean bindUuid = false;

		if (uuid.isEmpty()) {
			sb.append(_FINDER_COLUMN_UUID_C_UUID_3);
		}
		else {
			bindUuid = true;

			sb.append(_FINDER_COLUMN_UUID_C_UUID_2);
		}

		sb.append(_FINDER_COLUMN_UUID_C_COMPANYID_2);

		if (orderByComparator != null) {
			String[] orderByConditionFields =
				orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				sb.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				sb.append(_ORDER_BY_ENTITY_ALIAS);
				sb.append(orderByConditionFields[i]);

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						sb.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(WHERE_GREATER_THAN);
					}
					else {
						sb.append(WHERE_LESSER_THAN);
					}
				}
			}

			sb.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				sb.append(_ORDER_BY_ENTITY_ALIAS);
				sb.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						sb.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(ORDER_BY_ASC);
					}
					else {
						sb.append(ORDER_BY_DESC);
					}
				}
			}
		}
		else {
			sb.append(PollsChoiceModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		if (bindUuid) {
			queryPos.add(uuid);
		}

		queryPos.add(companyId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(pollsChoice)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<PollsChoice> list = query.list();

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
		for (PollsChoice pollsChoice :
				findByUuid_C(
					uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

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
		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = _finderPathCountByUuid_C;

		Object[] finderArgs = new Object[] {uuid, companyId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_POLLSCHOICE_WHERE);

			boolean bindUuid = false;

			if (uuid.isEmpty()) {
				sb.append(_FINDER_COLUMN_UUID_C_UUID_3);
			}
			else {
				bindUuid = true;

				sb.append(_FINDER_COLUMN_UUID_C_UUID_2);
			}

			sb.append(_FINDER_COLUMN_UUID_C_COMPANYID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindUuid) {
					queryPos.add(uuid);
				}

				queryPos.add(companyId);

				count = (Long)query.uniqueResult();

				finderCache.putResult(finderPath, finderArgs, count);
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_UUID_C_UUID_2 =
		"pollsChoice.uuid = ? AND ";

	private static final String _FINDER_COLUMN_UUID_C_UUID_3 =
		"(pollsChoice.uuid IS NULL OR pollsChoice.uuid = '') AND ";

	private static final String _FINDER_COLUMN_UUID_C_COMPANYID_2 =
		"pollsChoice.companyId = ?";

	private FinderPath _finderPathWithPaginationFindByQuestionId;
	private FinderPath _finderPathWithoutPaginationFindByQuestionId;
	private FinderPath _finderPathCountByQuestionId;

	/**
	 * Returns all the polls choices where questionId = &#63;.
	 *
	 * @param questionId the question ID
	 * @return the matching polls choices
	 */
	@Override
	public List<PollsChoice> findByQuestionId(long questionId) {
		return findByQuestionId(
			questionId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the polls choices where questionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>PollsChoiceModelImpl</code>.
	 * </p>
	 *
	 * @param questionId the question ID
	 * @param start the lower bound of the range of polls choices
	 * @param end the upper bound of the range of polls choices (not inclusive)
	 * @return the range of matching polls choices
	 */
	@Override
	public List<PollsChoice> findByQuestionId(
		long questionId, int start, int end) {

		return findByQuestionId(questionId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the polls choices where questionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>PollsChoiceModelImpl</code>.
	 * </p>
	 *
	 * @param questionId the question ID
	 * @param start the lower bound of the range of polls choices
	 * @param end the upper bound of the range of polls choices (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching polls choices
	 */
	@Override
	public List<PollsChoice> findByQuestionId(
		long questionId, int start, int end,
		OrderByComparator<PollsChoice> orderByComparator) {

		return findByQuestionId(
			questionId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the polls choices where questionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>PollsChoiceModelImpl</code>.
	 * </p>
	 *
	 * @param questionId the question ID
	 * @param start the lower bound of the range of polls choices
	 * @param end the upper bound of the range of polls choices (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching polls choices
	 */
	@Override
	public List<PollsChoice> findByQuestionId(
		long questionId, int start, int end,
		OrderByComparator<PollsChoice> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByQuestionId;
				finderArgs = new Object[] {questionId};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByQuestionId;
			finderArgs = new Object[] {
				questionId, start, end, orderByComparator
			};
		}

		List<PollsChoice> list = null;

		if (useFinderCache) {
			list = (List<PollsChoice>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (PollsChoice pollsChoice : list) {
					if (questionId != pollsChoice.getQuestionId()) {
						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler sb = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					3 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				sb = new StringBundler(3);
			}

			sb.append(_SQL_SELECT_POLLSCHOICE_WHERE);

			sb.append(_FINDER_COLUMN_QUESTIONID_QUESTIONID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(PollsChoiceModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(questionId);

				list = (List<PollsChoice>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					finderCache.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception exception) {
				throw processException(exception);
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
	 * @throws NoSuchChoiceException if a matching polls choice could not be found
	 */
	@Override
	public PollsChoice findByQuestionId_First(
			long questionId, OrderByComparator<PollsChoice> orderByComparator)
		throws NoSuchChoiceException {

		PollsChoice pollsChoice = fetchByQuestionId_First(
			questionId, orderByComparator);

		if (pollsChoice != null) {
			return pollsChoice;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("questionId=");
		sb.append(questionId);

		sb.append("}");

		throw new NoSuchChoiceException(sb.toString());
	}

	/**
	 * Returns the first polls choice in the ordered set where questionId = &#63;.
	 *
	 * @param questionId the question ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching polls choice, or <code>null</code> if a matching polls choice could not be found
	 */
	@Override
	public PollsChoice fetchByQuestionId_First(
		long questionId, OrderByComparator<PollsChoice> orderByComparator) {

		List<PollsChoice> list = findByQuestionId(
			questionId, 0, 1, orderByComparator);

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
	 * @throws NoSuchChoiceException if a matching polls choice could not be found
	 */
	@Override
	public PollsChoice findByQuestionId_Last(
			long questionId, OrderByComparator<PollsChoice> orderByComparator)
		throws NoSuchChoiceException {

		PollsChoice pollsChoice = fetchByQuestionId_Last(
			questionId, orderByComparator);

		if (pollsChoice != null) {
			return pollsChoice;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("questionId=");
		sb.append(questionId);

		sb.append("}");

		throw new NoSuchChoiceException(sb.toString());
	}

	/**
	 * Returns the last polls choice in the ordered set where questionId = &#63;.
	 *
	 * @param questionId the question ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching polls choice, or <code>null</code> if a matching polls choice could not be found
	 */
	@Override
	public PollsChoice fetchByQuestionId_Last(
		long questionId, OrderByComparator<PollsChoice> orderByComparator) {

		int count = countByQuestionId(questionId);

		if (count == 0) {
			return null;
		}

		List<PollsChoice> list = findByQuestionId(
			questionId, count - 1, count, orderByComparator);

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
	 * @throws NoSuchChoiceException if a polls choice with the primary key could not be found
	 */
	@Override
	public PollsChoice[] findByQuestionId_PrevAndNext(
			long choiceId, long questionId,
			OrderByComparator<PollsChoice> orderByComparator)
		throws NoSuchChoiceException {

		PollsChoice pollsChoice = findByPrimaryKey(choiceId);

		Session session = null;

		try {
			session = openSession();

			PollsChoice[] array = new PollsChoiceImpl[3];

			array[0] = getByQuestionId_PrevAndNext(
				session, pollsChoice, questionId, orderByComparator, true);

			array[1] = pollsChoice;

			array[2] = getByQuestionId_PrevAndNext(
				session, pollsChoice, questionId, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected PollsChoice getByQuestionId_PrevAndNext(
		Session session, PollsChoice pollsChoice, long questionId,
		OrderByComparator<PollsChoice> orderByComparator, boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(3);
		}

		sb.append(_SQL_SELECT_POLLSCHOICE_WHERE);

		sb.append(_FINDER_COLUMN_QUESTIONID_QUESTIONID_2);

		if (orderByComparator != null) {
			String[] orderByConditionFields =
				orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				sb.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				sb.append(_ORDER_BY_ENTITY_ALIAS);
				sb.append(orderByConditionFields[i]);

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						sb.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(WHERE_GREATER_THAN);
					}
					else {
						sb.append(WHERE_LESSER_THAN);
					}
				}
			}

			sb.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				sb.append(_ORDER_BY_ENTITY_ALIAS);
				sb.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						sb.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(ORDER_BY_ASC);
					}
					else {
						sb.append(ORDER_BY_DESC);
					}
				}
			}
		}
		else {
			sb.append(PollsChoiceModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(questionId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(pollsChoice)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<PollsChoice> list = query.list();

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
		for (PollsChoice pollsChoice :
				findByQuestionId(
					questionId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

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
		FinderPath finderPath = _finderPathCountByQuestionId;

		Object[] finderArgs = new Object[] {questionId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_POLLSCHOICE_WHERE);

			sb.append(_FINDER_COLUMN_QUESTIONID_QUESTIONID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(questionId);

				count = (Long)query.uniqueResult();

				finderCache.putResult(finderPath, finderArgs, count);
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_QUESTIONID_QUESTIONID_2 =
		"pollsChoice.questionId = ?";

	private FinderPath _finderPathFetchByQ_N;
	private FinderPath _finderPathCountByQ_N;

	/**
	 * Returns the polls choice where questionId = &#63; and name = &#63; or throws a <code>NoSuchChoiceException</code> if it could not be found.
	 *
	 * @param questionId the question ID
	 * @param name the name
	 * @return the matching polls choice
	 * @throws NoSuchChoiceException if a matching polls choice could not be found
	 */
	@Override
	public PollsChoice findByQ_N(long questionId, String name)
		throws NoSuchChoiceException {

		PollsChoice pollsChoice = fetchByQ_N(questionId, name);

		if (pollsChoice == null) {
			StringBundler sb = new StringBundler(6);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("questionId=");
			sb.append(questionId);

			sb.append(", name=");
			sb.append(name);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchChoiceException(sb.toString());
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
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching polls choice, or <code>null</code> if a matching polls choice could not be found
	 */
	@Override
	public PollsChoice fetchByQ_N(
		long questionId, String name, boolean useFinderCache) {

		name = Objects.toString(name, "");

		Object[] finderArgs = null;

		if (useFinderCache) {
			finderArgs = new Object[] {questionId, name};
		}

		Object result = null;

		if (useFinderCache) {
			result = finderCache.getResult(_finderPathFetchByQ_N, finderArgs);
		}

		if (result instanceof PollsChoice) {
			PollsChoice pollsChoice = (PollsChoice)result;

			if ((questionId != pollsChoice.getQuestionId()) ||
				!Objects.equals(name, pollsChoice.getName())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_SELECT_POLLSCHOICE_WHERE);

			sb.append(_FINDER_COLUMN_Q_N_QUESTIONID_2);

			boolean bindName = false;

			if (name.isEmpty()) {
				sb.append(_FINDER_COLUMN_Q_N_NAME_3);
			}
			else {
				bindName = true;

				sb.append(_FINDER_COLUMN_Q_N_NAME_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(questionId);

				if (bindName) {
					queryPos.add(name);
				}

				List<PollsChoice> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache) {
						finderCache.putResult(
							_finderPathFetchByQ_N, finderArgs, list);
					}
				}
				else {
					PollsChoice pollsChoice = list.get(0);

					result = pollsChoice;

					cacheResult(pollsChoice);
				}
			}
			catch (Exception exception) {
				throw processException(exception);
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
		name = Objects.toString(name, "");

		FinderPath finderPath = _finderPathCountByQ_N;

		Object[] finderArgs = new Object[] {questionId, name};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_POLLSCHOICE_WHERE);

			sb.append(_FINDER_COLUMN_Q_N_QUESTIONID_2);

			boolean bindName = false;

			if (name.isEmpty()) {
				sb.append(_FINDER_COLUMN_Q_N_NAME_3);
			}
			else {
				bindName = true;

				sb.append(_FINDER_COLUMN_Q_N_NAME_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(questionId);

				if (bindName) {
					queryPos.add(name);
				}

				count = (Long)query.uniqueResult();

				finderCache.putResult(finderPath, finderArgs, count);
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_Q_N_QUESTIONID_2 =
		"pollsChoice.questionId = ? AND ";

	private static final String _FINDER_COLUMN_Q_N_NAME_2 =
		"pollsChoice.name = ?";

	private static final String _FINDER_COLUMN_Q_N_NAME_3 =
		"(pollsChoice.name IS NULL OR pollsChoice.name = '')";

	public PollsChoicePersistenceImpl() {
		Map<String, String> dbColumnNames = new HashMap<String, String>();

		dbColumnNames.put("uuid", "uuid_");

		setDBColumnNames(dbColumnNames);

		setModelClass(PollsChoice.class);

		setModelImplClass(PollsChoiceImpl.class);
		setModelPKClass(long.class);

		setTable(PollsChoiceTable.INSTANCE);
	}

	/**
	 * Caches the polls choice in the entity cache if it is enabled.
	 *
	 * @param pollsChoice the polls choice
	 */
	@Override
	public void cacheResult(PollsChoice pollsChoice) {
		entityCache.putResult(
			PollsChoiceImpl.class, pollsChoice.getPrimaryKey(), pollsChoice);

		finderCache.putResult(
			_finderPathFetchByUUID_G,
			new Object[] {pollsChoice.getUuid(), pollsChoice.getGroupId()},
			pollsChoice);

		finderCache.putResult(
			_finderPathFetchByQ_N,
			new Object[] {pollsChoice.getQuestionId(), pollsChoice.getName()},
			pollsChoice);
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
					PollsChoiceImpl.class, pollsChoice.getPrimaryKey()) ==
						null) {

				cacheResult(pollsChoice);
			}
		}
	}

	/**
	 * Clears the cache for all polls choices.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(PollsChoiceImpl.class);

		finderCache.clearCache(PollsChoiceImpl.class);
	}

	/**
	 * Clears the cache for the polls choice.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(PollsChoice pollsChoice) {
		entityCache.removeResult(PollsChoiceImpl.class, pollsChoice);
	}

	@Override
	public void clearCache(List<PollsChoice> pollsChoices) {
		for (PollsChoice pollsChoice : pollsChoices) {
			entityCache.removeResult(PollsChoiceImpl.class, pollsChoice);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(PollsChoiceImpl.class);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(PollsChoiceImpl.class, primaryKey);
		}
	}

	protected void cacheUniqueFindersCache(
		PollsChoiceModelImpl pollsChoiceModelImpl) {

		Object[] args = new Object[] {
			pollsChoiceModelImpl.getUuid(), pollsChoiceModelImpl.getGroupId()
		};

		finderCache.putResult(_finderPathCountByUUID_G, args, Long.valueOf(1));
		finderCache.putResult(
			_finderPathFetchByUUID_G, args, pollsChoiceModelImpl);

		args = new Object[] {
			pollsChoiceModelImpl.getQuestionId(), pollsChoiceModelImpl.getName()
		};

		finderCache.putResult(_finderPathCountByQ_N, args, Long.valueOf(1));
		finderCache.putResult(
			_finderPathFetchByQ_N, args, pollsChoiceModelImpl);
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

		pollsChoice.setCompanyId(CompanyThreadLocal.getCompanyId());

		return pollsChoice;
	}

	/**
	 * Removes the polls choice with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param choiceId the primary key of the polls choice
	 * @return the polls choice that was removed
	 * @throws NoSuchChoiceException if a polls choice with the primary key could not be found
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
	 * @throws NoSuchChoiceException if a polls choice with the primary key could not be found
	 */
	@Override
	public PollsChoice remove(Serializable primaryKey)
		throws NoSuchChoiceException {

		Session session = null;

		try {
			session = openSession();

			PollsChoice pollsChoice = (PollsChoice)session.get(
				PollsChoiceImpl.class, primaryKey);

			if (pollsChoice == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchChoiceException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(pollsChoice);
		}
		catch (NoSuchChoiceException noSuchEntityException) {
			throw noSuchEntityException;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	@Override
	protected PollsChoice removeImpl(PollsChoice pollsChoice) {
		Session session = null;

		try {
			session = openSession();

			if (!session.contains(pollsChoice)) {
				pollsChoice = (PollsChoice)session.get(
					PollsChoiceImpl.class, pollsChoice.getPrimaryKeyObj());
			}

			if (pollsChoice != null) {
				session.delete(pollsChoice);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
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
		boolean isNew = pollsChoice.isNew();

		if (!(pollsChoice instanceof PollsChoiceModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(pollsChoice.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(pollsChoice);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in pollsChoice proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom PollsChoice implementation " +
					pollsChoice.getClass());
		}

		PollsChoiceModelImpl pollsChoiceModelImpl =
			(PollsChoiceModelImpl)pollsChoice;

		if (Validator.isNull(pollsChoice.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			pollsChoice.setUuid(uuid);
		}

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

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
				pollsChoice.setModifiedDate(
					serviceContext.getModifiedDate(now));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (isNew) {
				session.save(pollsChoice);
			}
			else {
				pollsChoice = (PollsChoice)session.merge(pollsChoice);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		entityCache.putResult(
			PollsChoiceImpl.class, pollsChoiceModelImpl, false, true);

		cacheUniqueFindersCache(pollsChoiceModelImpl);

		if (isNew) {
			pollsChoice.setNew(false);
		}

		pollsChoice.resetOriginalValues();

		return pollsChoice;
	}

	/**
	 * Returns the polls choice with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the polls choice
	 * @return the polls choice
	 * @throws NoSuchChoiceException if a polls choice with the primary key could not be found
	 */
	@Override
	public PollsChoice findByPrimaryKey(Serializable primaryKey)
		throws NoSuchChoiceException {

		PollsChoice pollsChoice = fetchByPrimaryKey(primaryKey);

		if (pollsChoice == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchChoiceException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return pollsChoice;
	}

	/**
	 * Returns the polls choice with the primary key or throws a <code>NoSuchChoiceException</code> if it could not be found.
	 *
	 * @param choiceId the primary key of the polls choice
	 * @return the polls choice
	 * @throws NoSuchChoiceException if a polls choice with the primary key could not be found
	 */
	@Override
	public PollsChoice findByPrimaryKey(long choiceId)
		throws NoSuchChoiceException {

		return findByPrimaryKey((Serializable)choiceId);
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>PollsChoiceModelImpl</code>.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>PollsChoiceModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of polls choices
	 * @param end the upper bound of the range of polls choices (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of polls choices
	 */
	@Override
	public List<PollsChoice> findAll(
		int start, int end, OrderByComparator<PollsChoice> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the polls choices.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>PollsChoiceModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of polls choices
	 * @param end the upper bound of the range of polls choices (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of polls choices
	 */
	@Override
	public List<PollsChoice> findAll(
		int start, int end, OrderByComparator<PollsChoice> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindAll;
				finderArgs = FINDER_ARGS_EMPTY;
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindAll;
			finderArgs = new Object[] {start, end, orderByComparator};
		}

		List<PollsChoice> list = null;

		if (useFinderCache) {
			list = (List<PollsChoice>)finderCache.getResult(
				finderPath, finderArgs);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_POLLSCHOICE);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_POLLSCHOICE;

				sql = sql.concat(PollsChoiceModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<PollsChoice>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					finderCache.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception exception) {
				throw processException(exception);
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
		Long count = (Long)finderCache.getResult(
			_finderPathCountAll, FINDER_ARGS_EMPTY);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(_SQL_COUNT_POLLSCHOICE);

				count = (Long)query.uniqueResult();

				finderCache.putResult(
					_finderPathCountAll, FINDER_ARGS_EMPTY, count);
			}
			catch (Exception exception) {
				throw processException(exception);
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
	protected EntityCache getEntityCache() {
		return entityCache;
	}

	@Override
	protected String getPKDBName() {
		return "choiceId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_POLLSCHOICE;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return PollsChoiceModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the polls choice persistence.
	 */
	@Activate
	public void activate(BundleContext bundleContext) {
		_bundleContext = bundleContext;

		_argumentsResolverServiceRegistration = _bundleContext.registerService(
			ArgumentsResolver.class, new PollsChoiceModelArgumentsResolver(),
			new HashMapDictionary<>());

		_finderPathWithPaginationFindAll = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0],
			new String[0], true);

		_finderPathWithoutPaginationFindAll = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0],
			new String[0], true);

		_finderPathCountAll = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll",
			new String[0], new String[0], false);

		_finderPathWithPaginationFindByUuid = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUuid",
			new String[] {
				String.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"uuid_"}, true);

		_finderPathWithoutPaginationFindByUuid = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid",
			new String[] {String.class.getName()}, new String[] {"uuid_"},
			true);

		_finderPathCountByUuid = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUuid",
			new String[] {String.class.getName()}, new String[] {"uuid_"},
			false);

		_finderPathFetchByUUID_G = new FinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByUUID_G",
			new String[] {String.class.getName(), Long.class.getName()},
			new String[] {"uuid_", "groupId"}, true);

		_finderPathCountByUUID_G = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUUID_G",
			new String[] {String.class.getName(), Long.class.getName()},
			new String[] {"uuid_", "groupId"}, false);

		_finderPathWithPaginationFindByUuid_C = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUuid_C",
			new String[] {
				String.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {"uuid_", "companyId"}, true);

		_finderPathWithoutPaginationFindByUuid_C = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid_C",
			new String[] {String.class.getName(), Long.class.getName()},
			new String[] {"uuid_", "companyId"}, true);

		_finderPathCountByUuid_C = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUuid_C",
			new String[] {String.class.getName(), Long.class.getName()},
			new String[] {"uuid_", "companyId"}, false);

		_finderPathWithPaginationFindByQuestionId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByQuestionId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"questionId"}, true);

		_finderPathWithoutPaginationFindByQuestionId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByQuestionId",
			new String[] {Long.class.getName()}, new String[] {"questionId"},
			true);

		_finderPathCountByQuestionId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByQuestionId",
			new String[] {Long.class.getName()}, new String[] {"questionId"},
			false);

		_finderPathFetchByQ_N = new FinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByQ_N",
			new String[] {Long.class.getName(), String.class.getName()},
			new String[] {"questionId", "name"}, true);

		_finderPathCountByQ_N = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByQ_N",
			new String[] {Long.class.getName(), String.class.getName()},
			new String[] {"questionId", "name"}, false);
	}

	@Deactivate
	public void deactivate() {
		entityCache.removeCache(PollsChoiceImpl.class.getName());

		_argumentsResolverServiceRegistration.unregister();
	}

	@Override
	@Reference(
		target = PollsPersistenceConstants.SERVICE_CONFIGURATION_FILTER,
		unbind = "-"
	)
	public void setConfiguration(Configuration configuration) {
	}

	@Override
	@Reference(
		target = PollsPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setDataSource(DataSource dataSource) {
		super.setDataSource(dataSource);
	}

	@Override
	@Reference(
		target = PollsPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setSessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	private BundleContext _bundleContext;

	@Reference
	protected EntityCache entityCache;

	@Reference
	protected FinderCache finderCache;

	private static final String _SQL_SELECT_POLLSCHOICE =
		"SELECT pollsChoice FROM PollsChoice pollsChoice";

	private static final String _SQL_SELECT_POLLSCHOICE_WHERE =
		"SELECT pollsChoice FROM PollsChoice pollsChoice WHERE ";

	private static final String _SQL_COUNT_POLLSCHOICE =
		"SELECT COUNT(pollsChoice) FROM PollsChoice pollsChoice";

	private static final String _SQL_COUNT_POLLSCHOICE_WHERE =
		"SELECT COUNT(pollsChoice) FROM PollsChoice pollsChoice WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS = "pollsChoice.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No PollsChoice exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No PollsChoice exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		PollsChoicePersistenceImpl.class);

	private static final Set<String> _badColumnNames = SetUtil.fromArray(
		new String[] {"uuid"});

	@Override
	protected FinderCache getFinderCache() {
		return finderCache;
	}

	private ServiceRegistration<ArgumentsResolver>
		_argumentsResolverServiceRegistration;

	private static class PollsChoiceModelArgumentsResolver
		implements ArgumentsResolver {

		@Override
		public Object[] getArguments(
			FinderPath finderPath, BaseModel<?> baseModel, boolean checkColumn,
			boolean original) {

			String[] columnNames = finderPath.getColumnNames();

			if ((columnNames == null) || (columnNames.length == 0)) {
				if (baseModel.isNew()) {
					return FINDER_ARGS_EMPTY;
				}

				return null;
			}

			PollsChoiceModelImpl pollsChoiceModelImpl =
				(PollsChoiceModelImpl)baseModel;

			long columnBitmask = pollsChoiceModelImpl.getColumnBitmask();

			if (!checkColumn || (columnBitmask == 0)) {
				return _getValue(pollsChoiceModelImpl, columnNames, original);
			}

			Long finderPathColumnBitmask = _finderPathColumnBitmasksCache.get(
				finderPath);

			if (finderPathColumnBitmask == null) {
				finderPathColumnBitmask = 0L;

				for (String columnName : columnNames) {
					finderPathColumnBitmask |=
						pollsChoiceModelImpl.getColumnBitmask(columnName);
				}

				_finderPathColumnBitmasksCache.put(
					finderPath, finderPathColumnBitmask);
			}

			if ((columnBitmask & finderPathColumnBitmask) != 0) {
				return _getValue(pollsChoiceModelImpl, columnNames, original);
			}

			return null;
		}

		@Override
		public String getClassName() {
			return PollsChoiceImpl.class.getName();
		}

		@Override
		public String getTableName() {
			return PollsChoiceTable.INSTANCE.getTableName();
		}

		private Object[] _getValue(
			PollsChoiceModelImpl pollsChoiceModelImpl, String[] columnNames,
			boolean original) {

			Object[] arguments = new Object[columnNames.length];

			for (int i = 0; i < arguments.length; i++) {
				String columnName = columnNames[i];

				if (original) {
					arguments[i] = pollsChoiceModelImpl.getColumnOriginalValue(
						columnName);
				}
				else {
					arguments[i] = pollsChoiceModelImpl.getColumnValue(
						columnName);
				}
			}

			return arguments;
		}

		private static Map<FinderPath, Long> _finderPathColumnBitmasksCache =
			new ConcurrentHashMap<>();

	}

}