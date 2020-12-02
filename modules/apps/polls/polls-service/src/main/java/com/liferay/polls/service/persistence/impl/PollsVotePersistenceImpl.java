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
import com.liferay.polls.exception.NoSuchVoteException;
import com.liferay.polls.model.PollsVote;
import com.liferay.polls.model.PollsVoteTable;
import com.liferay.polls.model.impl.PollsVoteImpl;
import com.liferay.polls.model.impl.PollsVoteModelImpl;
import com.liferay.polls.service.persistence.PollsVotePersistence;
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
 * The persistence implementation for the polls vote service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
@Component(service = {PollsVotePersistence.class, BasePersistence.class})
public class PollsVotePersistenceImpl
	extends BasePersistenceImpl<PollsVote> implements PollsVotePersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>PollsVoteUtil</code> to access the polls vote persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		PollsVoteImpl.class.getName();

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
	 * Returns all the polls votes where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching polls votes
	 */
	@Override
	public List<PollsVote> findByUuid(String uuid) {
		return findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the polls votes where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>PollsVoteModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of polls votes
	 * @param end the upper bound of the range of polls votes (not inclusive)
	 * @return the range of matching polls votes
	 */
	@Override
	public List<PollsVote> findByUuid(String uuid, int start, int end) {
		return findByUuid(uuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the polls votes where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>PollsVoteModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of polls votes
	 * @param end the upper bound of the range of polls votes (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching polls votes
	 */
	@Override
	public List<PollsVote> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<PollsVote> orderByComparator) {

		return findByUuid(uuid, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the polls votes where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>PollsVoteModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of polls votes
	 * @param end the upper bound of the range of polls votes (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching polls votes
	 */
	@Override
	public List<PollsVote> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<PollsVote> orderByComparator,
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

		List<PollsVote> list = null;

		if (useFinderCache) {
			list = (List<PollsVote>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (PollsVote pollsVote : list) {
					if (!uuid.equals(pollsVote.getUuid())) {
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

			sb.append(_SQL_SELECT_POLLSVOTE_WHERE);

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
				sb.append(PollsVoteModelImpl.ORDER_BY_JPQL);
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

				list = (List<PollsVote>)QueryUtil.list(
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
	 * Returns the first polls vote in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching polls vote
	 * @throws NoSuchVoteException if a matching polls vote could not be found
	 */
	@Override
	public PollsVote findByUuid_First(
			String uuid, OrderByComparator<PollsVote> orderByComparator)
		throws NoSuchVoteException {

		PollsVote pollsVote = fetchByUuid_First(uuid, orderByComparator);

		if (pollsVote != null) {
			return pollsVote;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append("}");

		throw new NoSuchVoteException(sb.toString());
	}

	/**
	 * Returns the first polls vote in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching polls vote, or <code>null</code> if a matching polls vote could not be found
	 */
	@Override
	public PollsVote fetchByUuid_First(
		String uuid, OrderByComparator<PollsVote> orderByComparator) {

		List<PollsVote> list = findByUuid(uuid, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last polls vote in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching polls vote
	 * @throws NoSuchVoteException if a matching polls vote could not be found
	 */
	@Override
	public PollsVote findByUuid_Last(
			String uuid, OrderByComparator<PollsVote> orderByComparator)
		throws NoSuchVoteException {

		PollsVote pollsVote = fetchByUuid_Last(uuid, orderByComparator);

		if (pollsVote != null) {
			return pollsVote;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append("}");

		throw new NoSuchVoteException(sb.toString());
	}

	/**
	 * Returns the last polls vote in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching polls vote, or <code>null</code> if a matching polls vote could not be found
	 */
	@Override
	public PollsVote fetchByUuid_Last(
		String uuid, OrderByComparator<PollsVote> orderByComparator) {

		int count = countByUuid(uuid);

		if (count == 0) {
			return null;
		}

		List<PollsVote> list = findByUuid(
			uuid, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the polls votes before and after the current polls vote in the ordered set where uuid = &#63;.
	 *
	 * @param voteId the primary key of the current polls vote
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next polls vote
	 * @throws NoSuchVoteException if a polls vote with the primary key could not be found
	 */
	@Override
	public PollsVote[] findByUuid_PrevAndNext(
			long voteId, String uuid,
			OrderByComparator<PollsVote> orderByComparator)
		throws NoSuchVoteException {

		uuid = Objects.toString(uuid, "");

		PollsVote pollsVote = findByPrimaryKey(voteId);

		Session session = null;

		try {
			session = openSession();

			PollsVote[] array = new PollsVoteImpl[3];

			array[0] = getByUuid_PrevAndNext(
				session, pollsVote, uuid, orderByComparator, true);

			array[1] = pollsVote;

			array[2] = getByUuid_PrevAndNext(
				session, pollsVote, uuid, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected PollsVote getByUuid_PrevAndNext(
		Session session, PollsVote pollsVote, String uuid,
		OrderByComparator<PollsVote> orderByComparator, boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(3);
		}

		sb.append(_SQL_SELECT_POLLSVOTE_WHERE);

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
			sb.append(PollsVoteModelImpl.ORDER_BY_JPQL);
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
					orderByComparator.getOrderByConditionValues(pollsVote)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<PollsVote> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the polls votes where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	@Override
	public void removeByUuid(String uuid) {
		for (PollsVote pollsVote :
				findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(pollsVote);
		}
	}

	/**
	 * Returns the number of polls votes where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching polls votes
	 */
	@Override
	public int countByUuid(String uuid) {
		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = _finderPathCountByUuid;

		Object[] finderArgs = new Object[] {uuid};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_POLLSVOTE_WHERE);

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
		"pollsVote.uuid = ?";

	private static final String _FINDER_COLUMN_UUID_UUID_3 =
		"(pollsVote.uuid IS NULL OR pollsVote.uuid = '')";

	private FinderPath _finderPathFetchByUUID_G;
	private FinderPath _finderPathCountByUUID_G;

	/**
	 * Returns the polls vote where uuid = &#63; and groupId = &#63; or throws a <code>NoSuchVoteException</code> if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching polls vote
	 * @throws NoSuchVoteException if a matching polls vote could not be found
	 */
	@Override
	public PollsVote findByUUID_G(String uuid, long groupId)
		throws NoSuchVoteException {

		PollsVote pollsVote = fetchByUUID_G(uuid, groupId);

		if (pollsVote == null) {
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

			throw new NoSuchVoteException(sb.toString());
		}

		return pollsVote;
	}

	/**
	 * Returns the polls vote where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching polls vote, or <code>null</code> if a matching polls vote could not be found
	 */
	@Override
	public PollsVote fetchByUUID_G(String uuid, long groupId) {
		return fetchByUUID_G(uuid, groupId, true);
	}

	/**
	 * Returns the polls vote where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching polls vote, or <code>null</code> if a matching polls vote could not be found
	 */
	@Override
	public PollsVote fetchByUUID_G(
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

		if (result instanceof PollsVote) {
			PollsVote pollsVote = (PollsVote)result;

			if (!Objects.equals(uuid, pollsVote.getUuid()) ||
				(groupId != pollsVote.getGroupId())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_SELECT_POLLSVOTE_WHERE);

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

				List<PollsVote> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache) {
						finderCache.putResult(
							_finderPathFetchByUUID_G, finderArgs, list);
					}
				}
				else {
					PollsVote pollsVote = list.get(0);

					result = pollsVote;

					cacheResult(pollsVote);
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
			return (PollsVote)result;
		}
	}

	/**
	 * Removes the polls vote where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the polls vote that was removed
	 */
	@Override
	public PollsVote removeByUUID_G(String uuid, long groupId)
		throws NoSuchVoteException {

		PollsVote pollsVote = findByUUID_G(uuid, groupId);

		return remove(pollsVote);
	}

	/**
	 * Returns the number of polls votes where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching polls votes
	 */
	@Override
	public int countByUUID_G(String uuid, long groupId) {
		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = _finderPathCountByUUID_G;

		Object[] finderArgs = new Object[] {uuid, groupId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_POLLSVOTE_WHERE);

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
		"pollsVote.uuid = ? AND ";

	private static final String _FINDER_COLUMN_UUID_G_UUID_3 =
		"(pollsVote.uuid IS NULL OR pollsVote.uuid = '') AND ";

	private static final String _FINDER_COLUMN_UUID_G_GROUPID_2 =
		"pollsVote.groupId = ?";

	private FinderPath _finderPathWithPaginationFindByUuid_C;
	private FinderPath _finderPathWithoutPaginationFindByUuid_C;
	private FinderPath _finderPathCountByUuid_C;

	/**
	 * Returns all the polls votes where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching polls votes
	 */
	@Override
	public List<PollsVote> findByUuid_C(String uuid, long companyId) {
		return findByUuid_C(
			uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the polls votes where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>PollsVoteModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of polls votes
	 * @param end the upper bound of the range of polls votes (not inclusive)
	 * @return the range of matching polls votes
	 */
	@Override
	public List<PollsVote> findByUuid_C(
		String uuid, long companyId, int start, int end) {

		return findByUuid_C(uuid, companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the polls votes where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>PollsVoteModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of polls votes
	 * @param end the upper bound of the range of polls votes (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching polls votes
	 */
	@Override
	public List<PollsVote> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<PollsVote> orderByComparator) {

		return findByUuid_C(
			uuid, companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the polls votes where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>PollsVoteModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of polls votes
	 * @param end the upper bound of the range of polls votes (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching polls votes
	 */
	@Override
	public List<PollsVote> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<PollsVote> orderByComparator,
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

		List<PollsVote> list = null;

		if (useFinderCache) {
			list = (List<PollsVote>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (PollsVote pollsVote : list) {
					if (!uuid.equals(pollsVote.getUuid()) ||
						(companyId != pollsVote.getCompanyId())) {

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

			sb.append(_SQL_SELECT_POLLSVOTE_WHERE);

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
				sb.append(PollsVoteModelImpl.ORDER_BY_JPQL);
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

				list = (List<PollsVote>)QueryUtil.list(
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
	 * Returns the first polls vote in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching polls vote
	 * @throws NoSuchVoteException if a matching polls vote could not be found
	 */
	@Override
	public PollsVote findByUuid_C_First(
			String uuid, long companyId,
			OrderByComparator<PollsVote> orderByComparator)
		throws NoSuchVoteException {

		PollsVote pollsVote = fetchByUuid_C_First(
			uuid, companyId, orderByComparator);

		if (pollsVote != null) {
			return pollsVote;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append(", companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchVoteException(sb.toString());
	}

	/**
	 * Returns the first polls vote in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching polls vote, or <code>null</code> if a matching polls vote could not be found
	 */
	@Override
	public PollsVote fetchByUuid_C_First(
		String uuid, long companyId,
		OrderByComparator<PollsVote> orderByComparator) {

		List<PollsVote> list = findByUuid_C(
			uuid, companyId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last polls vote in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching polls vote
	 * @throws NoSuchVoteException if a matching polls vote could not be found
	 */
	@Override
	public PollsVote findByUuid_C_Last(
			String uuid, long companyId,
			OrderByComparator<PollsVote> orderByComparator)
		throws NoSuchVoteException {

		PollsVote pollsVote = fetchByUuid_C_Last(
			uuid, companyId, orderByComparator);

		if (pollsVote != null) {
			return pollsVote;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append(", companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchVoteException(sb.toString());
	}

	/**
	 * Returns the last polls vote in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching polls vote, or <code>null</code> if a matching polls vote could not be found
	 */
	@Override
	public PollsVote fetchByUuid_C_Last(
		String uuid, long companyId,
		OrderByComparator<PollsVote> orderByComparator) {

		int count = countByUuid_C(uuid, companyId);

		if (count == 0) {
			return null;
		}

		List<PollsVote> list = findByUuid_C(
			uuid, companyId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the polls votes before and after the current polls vote in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param voteId the primary key of the current polls vote
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next polls vote
	 * @throws NoSuchVoteException if a polls vote with the primary key could not be found
	 */
	@Override
	public PollsVote[] findByUuid_C_PrevAndNext(
			long voteId, String uuid, long companyId,
			OrderByComparator<PollsVote> orderByComparator)
		throws NoSuchVoteException {

		uuid = Objects.toString(uuid, "");

		PollsVote pollsVote = findByPrimaryKey(voteId);

		Session session = null;

		try {
			session = openSession();

			PollsVote[] array = new PollsVoteImpl[3];

			array[0] = getByUuid_C_PrevAndNext(
				session, pollsVote, uuid, companyId, orderByComparator, true);

			array[1] = pollsVote;

			array[2] = getByUuid_C_PrevAndNext(
				session, pollsVote, uuid, companyId, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected PollsVote getByUuid_C_PrevAndNext(
		Session session, PollsVote pollsVote, String uuid, long companyId,
		OrderByComparator<PollsVote> orderByComparator, boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				5 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(4);
		}

		sb.append(_SQL_SELECT_POLLSVOTE_WHERE);

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
			sb.append(PollsVoteModelImpl.ORDER_BY_JPQL);
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
					orderByComparator.getOrderByConditionValues(pollsVote)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<PollsVote> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the polls votes where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	@Override
	public void removeByUuid_C(String uuid, long companyId) {
		for (PollsVote pollsVote :
				findByUuid_C(
					uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(pollsVote);
		}
	}

	/**
	 * Returns the number of polls votes where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching polls votes
	 */
	@Override
	public int countByUuid_C(String uuid, long companyId) {
		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = _finderPathCountByUuid_C;

		Object[] finderArgs = new Object[] {uuid, companyId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_POLLSVOTE_WHERE);

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
		"pollsVote.uuid = ? AND ";

	private static final String _FINDER_COLUMN_UUID_C_UUID_3 =
		"(pollsVote.uuid IS NULL OR pollsVote.uuid = '') AND ";

	private static final String _FINDER_COLUMN_UUID_C_COMPANYID_2 =
		"pollsVote.companyId = ?";

	private FinderPath _finderPathWithPaginationFindByQuestionId;
	private FinderPath _finderPathWithoutPaginationFindByQuestionId;
	private FinderPath _finderPathCountByQuestionId;

	/**
	 * Returns all the polls votes where questionId = &#63;.
	 *
	 * @param questionId the question ID
	 * @return the matching polls votes
	 */
	@Override
	public List<PollsVote> findByQuestionId(long questionId) {
		return findByQuestionId(
			questionId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the polls votes where questionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>PollsVoteModelImpl</code>.
	 * </p>
	 *
	 * @param questionId the question ID
	 * @param start the lower bound of the range of polls votes
	 * @param end the upper bound of the range of polls votes (not inclusive)
	 * @return the range of matching polls votes
	 */
	@Override
	public List<PollsVote> findByQuestionId(
		long questionId, int start, int end) {

		return findByQuestionId(questionId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the polls votes where questionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>PollsVoteModelImpl</code>.
	 * </p>
	 *
	 * @param questionId the question ID
	 * @param start the lower bound of the range of polls votes
	 * @param end the upper bound of the range of polls votes (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching polls votes
	 */
	@Override
	public List<PollsVote> findByQuestionId(
		long questionId, int start, int end,
		OrderByComparator<PollsVote> orderByComparator) {

		return findByQuestionId(
			questionId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the polls votes where questionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>PollsVoteModelImpl</code>.
	 * </p>
	 *
	 * @param questionId the question ID
	 * @param start the lower bound of the range of polls votes
	 * @param end the upper bound of the range of polls votes (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching polls votes
	 */
	@Override
	public List<PollsVote> findByQuestionId(
		long questionId, int start, int end,
		OrderByComparator<PollsVote> orderByComparator,
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

		List<PollsVote> list = null;

		if (useFinderCache) {
			list = (List<PollsVote>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (PollsVote pollsVote : list) {
					if (questionId != pollsVote.getQuestionId()) {
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

			sb.append(_SQL_SELECT_POLLSVOTE_WHERE);

			sb.append(_FINDER_COLUMN_QUESTIONID_QUESTIONID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(PollsVoteModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(questionId);

				list = (List<PollsVote>)QueryUtil.list(
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
	 * Returns the first polls vote in the ordered set where questionId = &#63;.
	 *
	 * @param questionId the question ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching polls vote
	 * @throws NoSuchVoteException if a matching polls vote could not be found
	 */
	@Override
	public PollsVote findByQuestionId_First(
			long questionId, OrderByComparator<PollsVote> orderByComparator)
		throws NoSuchVoteException {

		PollsVote pollsVote = fetchByQuestionId_First(
			questionId, orderByComparator);

		if (pollsVote != null) {
			return pollsVote;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("questionId=");
		sb.append(questionId);

		sb.append("}");

		throw new NoSuchVoteException(sb.toString());
	}

	/**
	 * Returns the first polls vote in the ordered set where questionId = &#63;.
	 *
	 * @param questionId the question ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching polls vote, or <code>null</code> if a matching polls vote could not be found
	 */
	@Override
	public PollsVote fetchByQuestionId_First(
		long questionId, OrderByComparator<PollsVote> orderByComparator) {

		List<PollsVote> list = findByQuestionId(
			questionId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last polls vote in the ordered set where questionId = &#63;.
	 *
	 * @param questionId the question ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching polls vote
	 * @throws NoSuchVoteException if a matching polls vote could not be found
	 */
	@Override
	public PollsVote findByQuestionId_Last(
			long questionId, OrderByComparator<PollsVote> orderByComparator)
		throws NoSuchVoteException {

		PollsVote pollsVote = fetchByQuestionId_Last(
			questionId, orderByComparator);

		if (pollsVote != null) {
			return pollsVote;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("questionId=");
		sb.append(questionId);

		sb.append("}");

		throw new NoSuchVoteException(sb.toString());
	}

	/**
	 * Returns the last polls vote in the ordered set where questionId = &#63;.
	 *
	 * @param questionId the question ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching polls vote, or <code>null</code> if a matching polls vote could not be found
	 */
	@Override
	public PollsVote fetchByQuestionId_Last(
		long questionId, OrderByComparator<PollsVote> orderByComparator) {

		int count = countByQuestionId(questionId);

		if (count == 0) {
			return null;
		}

		List<PollsVote> list = findByQuestionId(
			questionId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the polls votes before and after the current polls vote in the ordered set where questionId = &#63;.
	 *
	 * @param voteId the primary key of the current polls vote
	 * @param questionId the question ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next polls vote
	 * @throws NoSuchVoteException if a polls vote with the primary key could not be found
	 */
	@Override
	public PollsVote[] findByQuestionId_PrevAndNext(
			long voteId, long questionId,
			OrderByComparator<PollsVote> orderByComparator)
		throws NoSuchVoteException {

		PollsVote pollsVote = findByPrimaryKey(voteId);

		Session session = null;

		try {
			session = openSession();

			PollsVote[] array = new PollsVoteImpl[3];

			array[0] = getByQuestionId_PrevAndNext(
				session, pollsVote, questionId, orderByComparator, true);

			array[1] = pollsVote;

			array[2] = getByQuestionId_PrevAndNext(
				session, pollsVote, questionId, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected PollsVote getByQuestionId_PrevAndNext(
		Session session, PollsVote pollsVote, long questionId,
		OrderByComparator<PollsVote> orderByComparator, boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(3);
		}

		sb.append(_SQL_SELECT_POLLSVOTE_WHERE);

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
			sb.append(PollsVoteModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(questionId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(pollsVote)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<PollsVote> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the polls votes where questionId = &#63; from the database.
	 *
	 * @param questionId the question ID
	 */
	@Override
	public void removeByQuestionId(long questionId) {
		for (PollsVote pollsVote :
				findByQuestionId(
					questionId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(pollsVote);
		}
	}

	/**
	 * Returns the number of polls votes where questionId = &#63;.
	 *
	 * @param questionId the question ID
	 * @return the number of matching polls votes
	 */
	@Override
	public int countByQuestionId(long questionId) {
		FinderPath finderPath = _finderPathCountByQuestionId;

		Object[] finderArgs = new Object[] {questionId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_POLLSVOTE_WHERE);

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
		"pollsVote.questionId = ?";

	private FinderPath _finderPathWithPaginationFindByChoiceId;
	private FinderPath _finderPathWithoutPaginationFindByChoiceId;
	private FinderPath _finderPathCountByChoiceId;

	/**
	 * Returns all the polls votes where choiceId = &#63;.
	 *
	 * @param choiceId the choice ID
	 * @return the matching polls votes
	 */
	@Override
	public List<PollsVote> findByChoiceId(long choiceId) {
		return findByChoiceId(
			choiceId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the polls votes where choiceId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>PollsVoteModelImpl</code>.
	 * </p>
	 *
	 * @param choiceId the choice ID
	 * @param start the lower bound of the range of polls votes
	 * @param end the upper bound of the range of polls votes (not inclusive)
	 * @return the range of matching polls votes
	 */
	@Override
	public List<PollsVote> findByChoiceId(long choiceId, int start, int end) {
		return findByChoiceId(choiceId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the polls votes where choiceId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>PollsVoteModelImpl</code>.
	 * </p>
	 *
	 * @param choiceId the choice ID
	 * @param start the lower bound of the range of polls votes
	 * @param end the upper bound of the range of polls votes (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching polls votes
	 */
	@Override
	public List<PollsVote> findByChoiceId(
		long choiceId, int start, int end,
		OrderByComparator<PollsVote> orderByComparator) {

		return findByChoiceId(choiceId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the polls votes where choiceId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>PollsVoteModelImpl</code>.
	 * </p>
	 *
	 * @param choiceId the choice ID
	 * @param start the lower bound of the range of polls votes
	 * @param end the upper bound of the range of polls votes (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching polls votes
	 */
	@Override
	public List<PollsVote> findByChoiceId(
		long choiceId, int start, int end,
		OrderByComparator<PollsVote> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByChoiceId;
				finderArgs = new Object[] {choiceId};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByChoiceId;
			finderArgs = new Object[] {choiceId, start, end, orderByComparator};
		}

		List<PollsVote> list = null;

		if (useFinderCache) {
			list = (List<PollsVote>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (PollsVote pollsVote : list) {
					if (choiceId != pollsVote.getChoiceId()) {
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

			sb.append(_SQL_SELECT_POLLSVOTE_WHERE);

			sb.append(_FINDER_COLUMN_CHOICEID_CHOICEID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(PollsVoteModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(choiceId);

				list = (List<PollsVote>)QueryUtil.list(
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
	 * Returns the first polls vote in the ordered set where choiceId = &#63;.
	 *
	 * @param choiceId the choice ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching polls vote
	 * @throws NoSuchVoteException if a matching polls vote could not be found
	 */
	@Override
	public PollsVote findByChoiceId_First(
			long choiceId, OrderByComparator<PollsVote> orderByComparator)
		throws NoSuchVoteException {

		PollsVote pollsVote = fetchByChoiceId_First(
			choiceId, orderByComparator);

		if (pollsVote != null) {
			return pollsVote;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("choiceId=");
		sb.append(choiceId);

		sb.append("}");

		throw new NoSuchVoteException(sb.toString());
	}

	/**
	 * Returns the first polls vote in the ordered set where choiceId = &#63;.
	 *
	 * @param choiceId the choice ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching polls vote, or <code>null</code> if a matching polls vote could not be found
	 */
	@Override
	public PollsVote fetchByChoiceId_First(
		long choiceId, OrderByComparator<PollsVote> orderByComparator) {

		List<PollsVote> list = findByChoiceId(
			choiceId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last polls vote in the ordered set where choiceId = &#63;.
	 *
	 * @param choiceId the choice ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching polls vote
	 * @throws NoSuchVoteException if a matching polls vote could not be found
	 */
	@Override
	public PollsVote findByChoiceId_Last(
			long choiceId, OrderByComparator<PollsVote> orderByComparator)
		throws NoSuchVoteException {

		PollsVote pollsVote = fetchByChoiceId_Last(choiceId, orderByComparator);

		if (pollsVote != null) {
			return pollsVote;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("choiceId=");
		sb.append(choiceId);

		sb.append("}");

		throw new NoSuchVoteException(sb.toString());
	}

	/**
	 * Returns the last polls vote in the ordered set where choiceId = &#63;.
	 *
	 * @param choiceId the choice ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching polls vote, or <code>null</code> if a matching polls vote could not be found
	 */
	@Override
	public PollsVote fetchByChoiceId_Last(
		long choiceId, OrderByComparator<PollsVote> orderByComparator) {

		int count = countByChoiceId(choiceId);

		if (count == 0) {
			return null;
		}

		List<PollsVote> list = findByChoiceId(
			choiceId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the polls votes before and after the current polls vote in the ordered set where choiceId = &#63;.
	 *
	 * @param voteId the primary key of the current polls vote
	 * @param choiceId the choice ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next polls vote
	 * @throws NoSuchVoteException if a polls vote with the primary key could not be found
	 */
	@Override
	public PollsVote[] findByChoiceId_PrevAndNext(
			long voteId, long choiceId,
			OrderByComparator<PollsVote> orderByComparator)
		throws NoSuchVoteException {

		PollsVote pollsVote = findByPrimaryKey(voteId);

		Session session = null;

		try {
			session = openSession();

			PollsVote[] array = new PollsVoteImpl[3];

			array[0] = getByChoiceId_PrevAndNext(
				session, pollsVote, choiceId, orderByComparator, true);

			array[1] = pollsVote;

			array[2] = getByChoiceId_PrevAndNext(
				session, pollsVote, choiceId, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected PollsVote getByChoiceId_PrevAndNext(
		Session session, PollsVote pollsVote, long choiceId,
		OrderByComparator<PollsVote> orderByComparator, boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(3);
		}

		sb.append(_SQL_SELECT_POLLSVOTE_WHERE);

		sb.append(_FINDER_COLUMN_CHOICEID_CHOICEID_2);

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
			sb.append(PollsVoteModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(choiceId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(pollsVote)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<PollsVote> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the polls votes where choiceId = &#63; from the database.
	 *
	 * @param choiceId the choice ID
	 */
	@Override
	public void removeByChoiceId(long choiceId) {
		for (PollsVote pollsVote :
				findByChoiceId(
					choiceId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(pollsVote);
		}
	}

	/**
	 * Returns the number of polls votes where choiceId = &#63;.
	 *
	 * @param choiceId the choice ID
	 * @return the number of matching polls votes
	 */
	@Override
	public int countByChoiceId(long choiceId) {
		FinderPath finderPath = _finderPathCountByChoiceId;

		Object[] finderArgs = new Object[] {choiceId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_POLLSVOTE_WHERE);

			sb.append(_FINDER_COLUMN_CHOICEID_CHOICEID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(choiceId);

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

	private static final String _FINDER_COLUMN_CHOICEID_CHOICEID_2 =
		"pollsVote.choiceId = ?";

	private FinderPath _finderPathWithPaginationFindByQ_U;
	private FinderPath _finderPathWithoutPaginationFindByQ_U;
	private FinderPath _finderPathCountByQ_U;

	/**
	 * Returns all the polls votes where questionId = &#63; and userId = &#63;.
	 *
	 * @param questionId the question ID
	 * @param userId the user ID
	 * @return the matching polls votes
	 */
	@Override
	public List<PollsVote> findByQ_U(long questionId, long userId) {
		return findByQ_U(
			questionId, userId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the polls votes where questionId = &#63; and userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>PollsVoteModelImpl</code>.
	 * </p>
	 *
	 * @param questionId the question ID
	 * @param userId the user ID
	 * @param start the lower bound of the range of polls votes
	 * @param end the upper bound of the range of polls votes (not inclusive)
	 * @return the range of matching polls votes
	 */
	@Override
	public List<PollsVote> findByQ_U(
		long questionId, long userId, int start, int end) {

		return findByQ_U(questionId, userId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the polls votes where questionId = &#63; and userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>PollsVoteModelImpl</code>.
	 * </p>
	 *
	 * @param questionId the question ID
	 * @param userId the user ID
	 * @param start the lower bound of the range of polls votes
	 * @param end the upper bound of the range of polls votes (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching polls votes
	 */
	@Override
	public List<PollsVote> findByQ_U(
		long questionId, long userId, int start, int end,
		OrderByComparator<PollsVote> orderByComparator) {

		return findByQ_U(
			questionId, userId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the polls votes where questionId = &#63; and userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>PollsVoteModelImpl</code>.
	 * </p>
	 *
	 * @param questionId the question ID
	 * @param userId the user ID
	 * @param start the lower bound of the range of polls votes
	 * @param end the upper bound of the range of polls votes (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching polls votes
	 */
	@Override
	public List<PollsVote> findByQ_U(
		long questionId, long userId, int start, int end,
		OrderByComparator<PollsVote> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByQ_U;
				finderArgs = new Object[] {questionId, userId};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByQ_U;
			finderArgs = new Object[] {
				questionId, userId, start, end, orderByComparator
			};
		}

		List<PollsVote> list = null;

		if (useFinderCache) {
			list = (List<PollsVote>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (PollsVote pollsVote : list) {
					if ((questionId != pollsVote.getQuestionId()) ||
						(userId != pollsVote.getUserId())) {

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

			sb.append(_SQL_SELECT_POLLSVOTE_WHERE);

			sb.append(_FINDER_COLUMN_Q_U_QUESTIONID_2);

			sb.append(_FINDER_COLUMN_Q_U_USERID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(PollsVoteModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(questionId);

				queryPos.add(userId);

				list = (List<PollsVote>)QueryUtil.list(
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
	 * Returns the first polls vote in the ordered set where questionId = &#63; and userId = &#63;.
	 *
	 * @param questionId the question ID
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching polls vote
	 * @throws NoSuchVoteException if a matching polls vote could not be found
	 */
	@Override
	public PollsVote findByQ_U_First(
			long questionId, long userId,
			OrderByComparator<PollsVote> orderByComparator)
		throws NoSuchVoteException {

		PollsVote pollsVote = fetchByQ_U_First(
			questionId, userId, orderByComparator);

		if (pollsVote != null) {
			return pollsVote;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("questionId=");
		sb.append(questionId);

		sb.append(", userId=");
		sb.append(userId);

		sb.append("}");

		throw new NoSuchVoteException(sb.toString());
	}

	/**
	 * Returns the first polls vote in the ordered set where questionId = &#63; and userId = &#63;.
	 *
	 * @param questionId the question ID
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching polls vote, or <code>null</code> if a matching polls vote could not be found
	 */
	@Override
	public PollsVote fetchByQ_U_First(
		long questionId, long userId,
		OrderByComparator<PollsVote> orderByComparator) {

		List<PollsVote> list = findByQ_U(
			questionId, userId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last polls vote in the ordered set where questionId = &#63; and userId = &#63;.
	 *
	 * @param questionId the question ID
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching polls vote
	 * @throws NoSuchVoteException if a matching polls vote could not be found
	 */
	@Override
	public PollsVote findByQ_U_Last(
			long questionId, long userId,
			OrderByComparator<PollsVote> orderByComparator)
		throws NoSuchVoteException {

		PollsVote pollsVote = fetchByQ_U_Last(
			questionId, userId, orderByComparator);

		if (pollsVote != null) {
			return pollsVote;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("questionId=");
		sb.append(questionId);

		sb.append(", userId=");
		sb.append(userId);

		sb.append("}");

		throw new NoSuchVoteException(sb.toString());
	}

	/**
	 * Returns the last polls vote in the ordered set where questionId = &#63; and userId = &#63;.
	 *
	 * @param questionId the question ID
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching polls vote, or <code>null</code> if a matching polls vote could not be found
	 */
	@Override
	public PollsVote fetchByQ_U_Last(
		long questionId, long userId,
		OrderByComparator<PollsVote> orderByComparator) {

		int count = countByQ_U(questionId, userId);

		if (count == 0) {
			return null;
		}

		List<PollsVote> list = findByQ_U(
			questionId, userId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the polls votes before and after the current polls vote in the ordered set where questionId = &#63; and userId = &#63;.
	 *
	 * @param voteId the primary key of the current polls vote
	 * @param questionId the question ID
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next polls vote
	 * @throws NoSuchVoteException if a polls vote with the primary key could not be found
	 */
	@Override
	public PollsVote[] findByQ_U_PrevAndNext(
			long voteId, long questionId, long userId,
			OrderByComparator<PollsVote> orderByComparator)
		throws NoSuchVoteException {

		PollsVote pollsVote = findByPrimaryKey(voteId);

		Session session = null;

		try {
			session = openSession();

			PollsVote[] array = new PollsVoteImpl[3];

			array[0] = getByQ_U_PrevAndNext(
				session, pollsVote, questionId, userId, orderByComparator,
				true);

			array[1] = pollsVote;

			array[2] = getByQ_U_PrevAndNext(
				session, pollsVote, questionId, userId, orderByComparator,
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

	protected PollsVote getByQ_U_PrevAndNext(
		Session session, PollsVote pollsVote, long questionId, long userId,
		OrderByComparator<PollsVote> orderByComparator, boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				5 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(4);
		}

		sb.append(_SQL_SELECT_POLLSVOTE_WHERE);

		sb.append(_FINDER_COLUMN_Q_U_QUESTIONID_2);

		sb.append(_FINDER_COLUMN_Q_U_USERID_2);

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
			sb.append(PollsVoteModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(questionId);

		queryPos.add(userId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(pollsVote)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<PollsVote> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the polls votes where questionId = &#63; and userId = &#63; from the database.
	 *
	 * @param questionId the question ID
	 * @param userId the user ID
	 */
	@Override
	public void removeByQ_U(long questionId, long userId) {
		for (PollsVote pollsVote :
				findByQ_U(
					questionId, userId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(pollsVote);
		}
	}

	/**
	 * Returns the number of polls votes where questionId = &#63; and userId = &#63;.
	 *
	 * @param questionId the question ID
	 * @param userId the user ID
	 * @return the number of matching polls votes
	 */
	@Override
	public int countByQ_U(long questionId, long userId) {
		FinderPath finderPath = _finderPathCountByQ_U;

		Object[] finderArgs = new Object[] {questionId, userId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_POLLSVOTE_WHERE);

			sb.append(_FINDER_COLUMN_Q_U_QUESTIONID_2);

			sb.append(_FINDER_COLUMN_Q_U_USERID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(questionId);

				queryPos.add(userId);

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

	private static final String _FINDER_COLUMN_Q_U_QUESTIONID_2 =
		"pollsVote.questionId = ? AND ";

	private static final String _FINDER_COLUMN_Q_U_USERID_2 =
		"pollsVote.userId = ?";

	public PollsVotePersistenceImpl() {
		Map<String, String> dbColumnNames = new HashMap<String, String>();

		dbColumnNames.put("uuid", "uuid_");

		setDBColumnNames(dbColumnNames);

		setModelClass(PollsVote.class);

		setModelImplClass(PollsVoteImpl.class);
		setModelPKClass(long.class);

		setTable(PollsVoteTable.INSTANCE);
	}

	/**
	 * Caches the polls vote in the entity cache if it is enabled.
	 *
	 * @param pollsVote the polls vote
	 */
	@Override
	public void cacheResult(PollsVote pollsVote) {
		entityCache.putResult(
			PollsVoteImpl.class, pollsVote.getPrimaryKey(), pollsVote);

		finderCache.putResult(
			_finderPathFetchByUUID_G,
			new Object[] {pollsVote.getUuid(), pollsVote.getGroupId()},
			pollsVote);
	}

	/**
	 * Caches the polls votes in the entity cache if it is enabled.
	 *
	 * @param pollsVotes the polls votes
	 */
	@Override
	public void cacheResult(List<PollsVote> pollsVotes) {
		for (PollsVote pollsVote : pollsVotes) {
			if (entityCache.getResult(
					PollsVoteImpl.class, pollsVote.getPrimaryKey()) == null) {

				cacheResult(pollsVote);
			}
		}
	}

	/**
	 * Clears the cache for all polls votes.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(PollsVoteImpl.class);

		finderCache.clearCache(PollsVoteImpl.class);
	}

	/**
	 * Clears the cache for the polls vote.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(PollsVote pollsVote) {
		entityCache.removeResult(PollsVoteImpl.class, pollsVote);
	}

	@Override
	public void clearCache(List<PollsVote> pollsVotes) {
		for (PollsVote pollsVote : pollsVotes) {
			entityCache.removeResult(PollsVoteImpl.class, pollsVote);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(PollsVoteImpl.class);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(PollsVoteImpl.class, primaryKey);
		}
	}

	protected void cacheUniqueFindersCache(
		PollsVoteModelImpl pollsVoteModelImpl) {

		Object[] args = new Object[] {
			pollsVoteModelImpl.getUuid(), pollsVoteModelImpl.getGroupId()
		};

		finderCache.putResult(_finderPathCountByUUID_G, args, Long.valueOf(1));
		finderCache.putResult(
			_finderPathFetchByUUID_G, args, pollsVoteModelImpl);
	}

	/**
	 * Creates a new polls vote with the primary key. Does not add the polls vote to the database.
	 *
	 * @param voteId the primary key for the new polls vote
	 * @return the new polls vote
	 */
	@Override
	public PollsVote create(long voteId) {
		PollsVote pollsVote = new PollsVoteImpl();

		pollsVote.setNew(true);
		pollsVote.setPrimaryKey(voteId);

		String uuid = PortalUUIDUtil.generate();

		pollsVote.setUuid(uuid);

		pollsVote.setCompanyId(CompanyThreadLocal.getCompanyId());

		return pollsVote;
	}

	/**
	 * Removes the polls vote with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param voteId the primary key of the polls vote
	 * @return the polls vote that was removed
	 * @throws NoSuchVoteException if a polls vote with the primary key could not be found
	 */
	@Override
	public PollsVote remove(long voteId) throws NoSuchVoteException {
		return remove((Serializable)voteId);
	}

	/**
	 * Removes the polls vote with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the polls vote
	 * @return the polls vote that was removed
	 * @throws NoSuchVoteException if a polls vote with the primary key could not be found
	 */
	@Override
	public PollsVote remove(Serializable primaryKey)
		throws NoSuchVoteException {

		Session session = null;

		try {
			session = openSession();

			PollsVote pollsVote = (PollsVote)session.get(
				PollsVoteImpl.class, primaryKey);

			if (pollsVote == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchVoteException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(pollsVote);
		}
		catch (NoSuchVoteException noSuchEntityException) {
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
	protected PollsVote removeImpl(PollsVote pollsVote) {
		Session session = null;

		try {
			session = openSession();

			if (!session.contains(pollsVote)) {
				pollsVote = (PollsVote)session.get(
					PollsVoteImpl.class, pollsVote.getPrimaryKeyObj());
			}

			if (pollsVote != null) {
				session.delete(pollsVote);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (pollsVote != null) {
			clearCache(pollsVote);
		}

		return pollsVote;
	}

	@Override
	public PollsVote updateImpl(PollsVote pollsVote) {
		boolean isNew = pollsVote.isNew();

		if (!(pollsVote instanceof PollsVoteModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(pollsVote.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(pollsVote);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in pollsVote proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom PollsVote implementation " +
					pollsVote.getClass());
		}

		PollsVoteModelImpl pollsVoteModelImpl = (PollsVoteModelImpl)pollsVote;

		if (Validator.isNull(pollsVote.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			pollsVote.setUuid(uuid);
		}

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Date now = new Date();

		if (isNew && (pollsVote.getCreateDate() == null)) {
			if (serviceContext == null) {
				pollsVote.setCreateDate(now);
			}
			else {
				pollsVote.setCreateDate(serviceContext.getCreateDate(now));
			}
		}

		if (!pollsVoteModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				pollsVote.setModifiedDate(now);
			}
			else {
				pollsVote.setModifiedDate(serviceContext.getModifiedDate(now));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (isNew) {
				session.save(pollsVote);
			}
			else {
				pollsVote = (PollsVote)session.merge(pollsVote);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		entityCache.putResult(
			PollsVoteImpl.class, pollsVoteModelImpl, false, true);

		cacheUniqueFindersCache(pollsVoteModelImpl);

		if (isNew) {
			pollsVote.setNew(false);
		}

		pollsVote.resetOriginalValues();

		return pollsVote;
	}

	/**
	 * Returns the polls vote with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the polls vote
	 * @return the polls vote
	 * @throws NoSuchVoteException if a polls vote with the primary key could not be found
	 */
	@Override
	public PollsVote findByPrimaryKey(Serializable primaryKey)
		throws NoSuchVoteException {

		PollsVote pollsVote = fetchByPrimaryKey(primaryKey);

		if (pollsVote == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchVoteException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return pollsVote;
	}

	/**
	 * Returns the polls vote with the primary key or throws a <code>NoSuchVoteException</code> if it could not be found.
	 *
	 * @param voteId the primary key of the polls vote
	 * @return the polls vote
	 * @throws NoSuchVoteException if a polls vote with the primary key could not be found
	 */
	@Override
	public PollsVote findByPrimaryKey(long voteId) throws NoSuchVoteException {
		return findByPrimaryKey((Serializable)voteId);
	}

	/**
	 * Returns the polls vote with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param voteId the primary key of the polls vote
	 * @return the polls vote, or <code>null</code> if a polls vote with the primary key could not be found
	 */
	@Override
	public PollsVote fetchByPrimaryKey(long voteId) {
		return fetchByPrimaryKey((Serializable)voteId);
	}

	/**
	 * Returns all the polls votes.
	 *
	 * @return the polls votes
	 */
	@Override
	public List<PollsVote> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the polls votes.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>PollsVoteModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of polls votes
	 * @param end the upper bound of the range of polls votes (not inclusive)
	 * @return the range of polls votes
	 */
	@Override
	public List<PollsVote> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the polls votes.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>PollsVoteModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of polls votes
	 * @param end the upper bound of the range of polls votes (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of polls votes
	 */
	@Override
	public List<PollsVote> findAll(
		int start, int end, OrderByComparator<PollsVote> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the polls votes.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>PollsVoteModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of polls votes
	 * @param end the upper bound of the range of polls votes (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of polls votes
	 */
	@Override
	public List<PollsVote> findAll(
		int start, int end, OrderByComparator<PollsVote> orderByComparator,
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

		List<PollsVote> list = null;

		if (useFinderCache) {
			list = (List<PollsVote>)finderCache.getResult(
				finderPath, finderArgs);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_POLLSVOTE);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_POLLSVOTE;

				sql = sql.concat(PollsVoteModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<PollsVote>)QueryUtil.list(
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
	 * Removes all the polls votes from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (PollsVote pollsVote : findAll()) {
			remove(pollsVote);
		}
	}

	/**
	 * Returns the number of polls votes.
	 *
	 * @return the number of polls votes
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(
			_finderPathCountAll, FINDER_ARGS_EMPTY);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(_SQL_COUNT_POLLSVOTE);

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
		return "voteId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_POLLSVOTE;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return PollsVoteModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the polls vote persistence.
	 */
	@Activate
	public void activate(BundleContext bundleContext) {
		_bundleContext = bundleContext;

		_argumentsResolverServiceRegistration = _bundleContext.registerService(
			ArgumentsResolver.class, new PollsVoteModelArgumentsResolver(),
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

		_finderPathWithPaginationFindByChoiceId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByChoiceId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"choiceId"}, true);

		_finderPathWithoutPaginationFindByChoiceId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByChoiceId",
			new String[] {Long.class.getName()}, new String[] {"choiceId"},
			true);

		_finderPathCountByChoiceId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByChoiceId",
			new String[] {Long.class.getName()}, new String[] {"choiceId"},
			false);

		_finderPathWithPaginationFindByQ_U = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByQ_U",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {"questionId", "userId"}, true);

		_finderPathWithoutPaginationFindByQ_U = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByQ_U",
			new String[] {Long.class.getName(), Long.class.getName()},
			new String[] {"questionId", "userId"}, true);

		_finderPathCountByQ_U = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByQ_U",
			new String[] {Long.class.getName(), Long.class.getName()},
			new String[] {"questionId", "userId"}, false);
	}

	@Deactivate
	public void deactivate() {
		entityCache.removeCache(PollsVoteImpl.class.getName());

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

	private static final String _SQL_SELECT_POLLSVOTE =
		"SELECT pollsVote FROM PollsVote pollsVote";

	private static final String _SQL_SELECT_POLLSVOTE_WHERE =
		"SELECT pollsVote FROM PollsVote pollsVote WHERE ";

	private static final String _SQL_COUNT_POLLSVOTE =
		"SELECT COUNT(pollsVote) FROM PollsVote pollsVote";

	private static final String _SQL_COUNT_POLLSVOTE_WHERE =
		"SELECT COUNT(pollsVote) FROM PollsVote pollsVote WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS = "pollsVote.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No PollsVote exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No PollsVote exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		PollsVotePersistenceImpl.class);

	private static final Set<String> _badColumnNames = SetUtil.fromArray(
		new String[] {"uuid"});

	@Override
	protected FinderCache getFinderCache() {
		return finderCache;
	}

	private ServiceRegistration<ArgumentsResolver>
		_argumentsResolverServiceRegistration;

	private static class PollsVoteModelArgumentsResolver
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

			PollsVoteModelImpl pollsVoteModelImpl =
				(PollsVoteModelImpl)baseModel;

			long columnBitmask = pollsVoteModelImpl.getColumnBitmask();

			if (!checkColumn || (columnBitmask == 0)) {
				return _getValue(pollsVoteModelImpl, columnNames, original);
			}

			Long finderPathColumnBitmask = _finderPathColumnBitmasksCache.get(
				finderPath);

			if (finderPathColumnBitmask == null) {
				finderPathColumnBitmask = 0L;

				for (String columnName : columnNames) {
					finderPathColumnBitmask |=
						pollsVoteModelImpl.getColumnBitmask(columnName);
				}

				_finderPathColumnBitmasksCache.put(
					finderPath, finderPathColumnBitmask);
			}

			if ((columnBitmask & finderPathColumnBitmask) != 0) {
				return _getValue(pollsVoteModelImpl, columnNames, original);
			}

			return null;
		}

		@Override
		public String getClassName() {
			return PollsVoteImpl.class.getName();
		}

		@Override
		public String getTableName() {
			return PollsVoteTable.INSTANCE.getTableName();
		}

		private Object[] _getValue(
			PollsVoteModelImpl pollsVoteModelImpl, String[] columnNames,
			boolean original) {

			Object[] arguments = new Object[columnNames.length];

			for (int i = 0; i < arguments.length; i++) {
				String columnName = columnNames[i];

				if (original) {
					arguments[i] = pollsVoteModelImpl.getColumnOriginalValue(
						columnName);
				}
				else {
					arguments[i] = pollsVoteModelImpl.getColumnValue(
						columnName);
				}
			}

			return arguments;
		}

		private static Map<FinderPath, Long> _finderPathColumnBitmasksCache =
			new ConcurrentHashMap<>();

	}

}