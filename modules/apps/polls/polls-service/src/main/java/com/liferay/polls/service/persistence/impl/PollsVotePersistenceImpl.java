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
import com.liferay.polls.model.impl.PollsVoteImpl;
import com.liferay.polls.model.impl.PollsVoteModelImpl;
import com.liferay.polls.service.persistence.PollsVotePersistence;
import com.liferay.polls.service.persistence.impl.constants.PollsPersistenceConstants;
import com.liferay.portal.kernel.configuration.Configuration;
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
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.util.GetterUtil;
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

import javax.sql.DataSource;

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
@Component(service = PollsVotePersistence.class)
public class PollsVotePersistenceImpl
	extends BasePersistenceImpl<PollsVote> implements PollsVotePersistence {

	/**
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
				finderPath, finderArgs, this);

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
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(
					3 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				query = new StringBundler(3);
			}

			query.append(_SQL_SELECT_POLLSVOTE_WHERE);

			boolean bindUuid = false;

			if (uuid.isEmpty()) {
				query.append(_FINDER_COLUMN_UUID_UUID_3);
			}
			else {
				bindUuid = true;

				query.append(_FINDER_COLUMN_UUID_UUID_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				query.append(PollsVoteModelImpl.ORDER_BY_JPQL);
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

				list = (List<PollsVote>)QueryUtil.list(
					q, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					finderCache.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception e) {
				if (useFinderCache) {
					finderCache.removeResult(finderPath, finderArgs);
				}

				throw processException(e);
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

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append("}");

		throw new NoSuchVoteException(msg.toString());
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

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append("}");

		throw new NoSuchVoteException(msg.toString());
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
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected PollsVote getByUuid_PrevAndNext(
		Session session, PollsVote pollsVote, String uuid,
		OrderByComparator<PollsVote> orderByComparator, boolean previous) {

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_POLLSVOTE_WHERE);

		boolean bindUuid = false;

		if (uuid.isEmpty()) {
			query.append(_FINDER_COLUMN_UUID_UUID_3);
		}
		else {
			bindUuid = true;

			query.append(_FINDER_COLUMN_UUID_UUID_2);
		}

		if (orderByComparator != null) {
			String[] orderByConditionFields =
				orderByComparator.getOrderByConditionFields();

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
			query.append(PollsVoteModelImpl.ORDER_BY_JPQL);
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
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(pollsVote)) {

				qPos.add(orderByConditionValue);
			}
		}

		List<PollsVote> list = q.list();

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

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_POLLSVOTE_WHERE);

			boolean bindUuid = false;

			if (uuid.isEmpty()) {
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
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("uuid=");
			msg.append(uuid);

			msg.append(", groupId=");
			msg.append(groupId);

			msg.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(msg.toString());
			}

			throw new NoSuchVoteException(msg.toString());
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
				_finderPathFetchByUUID_G, finderArgs, this);
		}

		if (result instanceof PollsVote) {
			PollsVote pollsVote = (PollsVote)result;

			if (!Objects.equals(uuid, pollsVote.getUuid()) ||
				(groupId != pollsVote.getGroupId())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_SELECT_POLLSVOTE_WHERE);

			boolean bindUuid = false;

			if (uuid.isEmpty()) {
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

				List<PollsVote> list = q.list();

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
			catch (Exception e) {
				if (useFinderCache) {
					finderCache.removeResult(
						_finderPathFetchByUUID_G, finderArgs);
				}

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

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_POLLSVOTE_WHERE);

			boolean bindUuid = false;

			if (uuid.isEmpty()) {
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
				finderPath, finderArgs, this);

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
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(
					4 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				query = new StringBundler(4);
			}

			query.append(_SQL_SELECT_POLLSVOTE_WHERE);

			boolean bindUuid = false;

			if (uuid.isEmpty()) {
				query.append(_FINDER_COLUMN_UUID_C_UUID_3);
			}
			else {
				bindUuid = true;

				query.append(_FINDER_COLUMN_UUID_C_UUID_2);
			}

			query.append(_FINDER_COLUMN_UUID_C_COMPANYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				query.append(PollsVoteModelImpl.ORDER_BY_JPQL);
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

				list = (List<PollsVote>)QueryUtil.list(
					q, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					finderCache.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception e) {
				if (useFinderCache) {
					finderCache.removeResult(finderPath, finderArgs);
				}

				throw processException(e);
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

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(", companyId=");
		msg.append(companyId);

		msg.append("}");

		throw new NoSuchVoteException(msg.toString());
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

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(", companyId=");
		msg.append(companyId);

		msg.append("}");

		throw new NoSuchVoteException(msg.toString());
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
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected PollsVote getByUuid_C_PrevAndNext(
		Session session, PollsVote pollsVote, String uuid, long companyId,
		OrderByComparator<PollsVote> orderByComparator, boolean previous) {

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(
				5 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(4);
		}

		query.append(_SQL_SELECT_POLLSVOTE_WHERE);

		boolean bindUuid = false;

		if (uuid.isEmpty()) {
			query.append(_FINDER_COLUMN_UUID_C_UUID_3);
		}
		else {
			bindUuid = true;

			query.append(_FINDER_COLUMN_UUID_C_UUID_2);
		}

		query.append(_FINDER_COLUMN_UUID_C_COMPANYID_2);

		if (orderByComparator != null) {
			String[] orderByConditionFields =
				orderByComparator.getOrderByConditionFields();

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
			query.append(PollsVoteModelImpl.ORDER_BY_JPQL);
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
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(pollsVote)) {

				qPos.add(orderByConditionValue);
			}
		}

		List<PollsVote> list = q.list();

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

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_POLLSVOTE_WHERE);

			boolean bindUuid = false;

			if (uuid.isEmpty()) {
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
				finderPath, finderArgs, this);

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
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(
					3 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				query = new StringBundler(3);
			}

			query.append(_SQL_SELECT_POLLSVOTE_WHERE);

			query.append(_FINDER_COLUMN_QUESTIONID_QUESTIONID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				query.append(PollsVoteModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(questionId);

				list = (List<PollsVote>)QueryUtil.list(
					q, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					finderCache.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception e) {
				if (useFinderCache) {
					finderCache.removeResult(finderPath, finderArgs);
				}

				throw processException(e);
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

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("questionId=");
		msg.append(questionId);

		msg.append("}");

		throw new NoSuchVoteException(msg.toString());
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

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("questionId=");
		msg.append(questionId);

		msg.append("}");

		throw new NoSuchVoteException(msg.toString());
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
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected PollsVote getByQuestionId_PrevAndNext(
		Session session, PollsVote pollsVote, long questionId,
		OrderByComparator<PollsVote> orderByComparator, boolean previous) {

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_POLLSVOTE_WHERE);

		query.append(_FINDER_COLUMN_QUESTIONID_QUESTIONID_2);

		if (orderByComparator != null) {
			String[] orderByConditionFields =
				orderByComparator.getOrderByConditionFields();

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
			query.append(PollsVoteModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(questionId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(pollsVote)) {

				qPos.add(orderByConditionValue);
			}
		}

		List<PollsVote> list = q.list();

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

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_POLLSVOTE_WHERE);

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
				finderPath, finderArgs, this);

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
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(
					3 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				query = new StringBundler(3);
			}

			query.append(_SQL_SELECT_POLLSVOTE_WHERE);

			query.append(_FINDER_COLUMN_CHOICEID_CHOICEID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				query.append(PollsVoteModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(choiceId);

				list = (List<PollsVote>)QueryUtil.list(
					q, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					finderCache.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception e) {
				if (useFinderCache) {
					finderCache.removeResult(finderPath, finderArgs);
				}

				throw processException(e);
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

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("choiceId=");
		msg.append(choiceId);

		msg.append("}");

		throw new NoSuchVoteException(msg.toString());
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

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("choiceId=");
		msg.append(choiceId);

		msg.append("}");

		throw new NoSuchVoteException(msg.toString());
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
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected PollsVote getByChoiceId_PrevAndNext(
		Session session, PollsVote pollsVote, long choiceId,
		OrderByComparator<PollsVote> orderByComparator, boolean previous) {

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_POLLSVOTE_WHERE);

		query.append(_FINDER_COLUMN_CHOICEID_CHOICEID_2);

		if (orderByComparator != null) {
			String[] orderByConditionFields =
				orderByComparator.getOrderByConditionFields();

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
			query.append(PollsVoteModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(choiceId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(pollsVote)) {

				qPos.add(orderByConditionValue);
			}
		}

		List<PollsVote> list = q.list();

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

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_POLLSVOTE_WHERE);

			query.append(_FINDER_COLUMN_CHOICEID_CHOICEID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(choiceId);

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
				finderPath, finderArgs, this);

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
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(
					4 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				query = new StringBundler(4);
			}

			query.append(_SQL_SELECT_POLLSVOTE_WHERE);

			query.append(_FINDER_COLUMN_Q_U_QUESTIONID_2);

			query.append(_FINDER_COLUMN_Q_U_USERID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				query.append(PollsVoteModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(questionId);

				qPos.add(userId);

				list = (List<PollsVote>)QueryUtil.list(
					q, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					finderCache.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception e) {
				if (useFinderCache) {
					finderCache.removeResult(finderPath, finderArgs);
				}

				throw processException(e);
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

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("questionId=");
		msg.append(questionId);

		msg.append(", userId=");
		msg.append(userId);

		msg.append("}");

		throw new NoSuchVoteException(msg.toString());
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

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("questionId=");
		msg.append(questionId);

		msg.append(", userId=");
		msg.append(userId);

		msg.append("}");

		throw new NoSuchVoteException(msg.toString());
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
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected PollsVote getByQ_U_PrevAndNext(
		Session session, PollsVote pollsVote, long questionId, long userId,
		OrderByComparator<PollsVote> orderByComparator, boolean previous) {

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(
				5 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(4);
		}

		query.append(_SQL_SELECT_POLLSVOTE_WHERE);

		query.append(_FINDER_COLUMN_Q_U_QUESTIONID_2);

		query.append(_FINDER_COLUMN_Q_U_USERID_2);

		if (orderByComparator != null) {
			String[] orderByConditionFields =
				orderByComparator.getOrderByConditionFields();

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
			query.append(PollsVoteModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(questionId);

		qPos.add(userId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(pollsVote)) {

				qPos.add(orderByConditionValue);
			}
		}

		List<PollsVote> list = q.list();

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

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_POLLSVOTE_WHERE);

			query.append(_FINDER_COLUMN_Q_U_QUESTIONID_2);

			query.append(_FINDER_COLUMN_Q_U_USERID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(questionId);

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

	private static final String _FINDER_COLUMN_Q_U_QUESTIONID_2 =
		"pollsVote.questionId = ? AND ";

	private static final String _FINDER_COLUMN_Q_U_USERID_2 =
		"pollsVote.userId = ?";

	public PollsVotePersistenceImpl() {
		setModelClass(PollsVote.class);

		setModelImplClass(PollsVoteImpl.class);
		setModelPKClass(long.class);

		Map<String, String> dbColumnNames = new HashMap<String, String>();

		dbColumnNames.put("uuid", "uuid_");

		setDBColumnNames(dbColumnNames);
	}

	/**
	 * Caches the polls vote in the entity cache if it is enabled.
	 *
	 * @param pollsVote the polls vote
	 */
	@Override
	public void cacheResult(PollsVote pollsVote) {
		entityCache.putResult(
			entityCacheEnabled, PollsVoteImpl.class, pollsVote.getPrimaryKey(),
			pollsVote);

		finderCache.putResult(
			_finderPathFetchByUUID_G,
			new Object[] {pollsVote.getUuid(), pollsVote.getGroupId()},
			pollsVote);

		pollsVote.resetOriginalValues();
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
					entityCacheEnabled, PollsVoteImpl.class,
					pollsVote.getPrimaryKey()) == null) {

				cacheResult(pollsVote);
			}
			else {
				pollsVote.resetOriginalValues();
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

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
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
		entityCache.removeResult(
			entityCacheEnabled, PollsVoteImpl.class, pollsVote.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache((PollsVoteModelImpl)pollsVote, true);
	}

	@Override
	public void clearCache(List<PollsVote> pollsVotes) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (PollsVote pollsVote : pollsVotes) {
			entityCache.removeResult(
				entityCacheEnabled, PollsVoteImpl.class,
				pollsVote.getPrimaryKey());

			clearUniqueFindersCache((PollsVoteModelImpl)pollsVote, true);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(
				entityCacheEnabled, PollsVoteImpl.class, primaryKey);
		}
	}

	protected void cacheUniqueFindersCache(
		PollsVoteModelImpl pollsVoteModelImpl) {

		Object[] args = new Object[] {
			pollsVoteModelImpl.getUuid(), pollsVoteModelImpl.getGroupId()
		};

		finderCache.putResult(
			_finderPathCountByUUID_G, args, Long.valueOf(1), false);
		finderCache.putResult(
			_finderPathFetchByUUID_G, args, pollsVoteModelImpl, false);
	}

	protected void clearUniqueFindersCache(
		PollsVoteModelImpl pollsVoteModelImpl, boolean clearCurrent) {

		if (clearCurrent) {
			Object[] args = new Object[] {
				pollsVoteModelImpl.getUuid(), pollsVoteModelImpl.getGroupId()
			};

			finderCache.removeResult(_finderPathCountByUUID_G, args);
			finderCache.removeResult(_finderPathFetchByUUID_G, args);
		}

		if ((pollsVoteModelImpl.getColumnBitmask() &
			 _finderPathFetchByUUID_G.getColumnBitmask()) != 0) {

			Object[] args = new Object[] {
				pollsVoteModelImpl.getOriginalUuid(),
				pollsVoteModelImpl.getOriginalGroupId()
			};

			finderCache.removeResult(_finderPathCountByUUID_G, args);
			finderCache.removeResult(_finderPathFetchByUUID_G, args);
		}
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
		catch (NoSuchVoteException nsee) {
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
		catch (Exception e) {
			throw processException(e);
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

			if (pollsVote.isNew()) {
				session.save(pollsVote);

				pollsVote.setNew(false);
			}
			else {
				pollsVote = (PollsVote)session.merge(pollsVote);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (!_columnBitmaskEnabled) {
			finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}
		else if (isNew) {
			Object[] args = new Object[] {pollsVoteModelImpl.getUuid()};

			finderCache.removeResult(_finderPathCountByUuid, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByUuid, args);

			args = new Object[] {
				pollsVoteModelImpl.getUuid(), pollsVoteModelImpl.getCompanyId()
			};

			finderCache.removeResult(_finderPathCountByUuid_C, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByUuid_C, args);

			args = new Object[] {pollsVoteModelImpl.getQuestionId()};

			finderCache.removeResult(_finderPathCountByQuestionId, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByQuestionId, args);

			args = new Object[] {pollsVoteModelImpl.getChoiceId()};

			finderCache.removeResult(_finderPathCountByChoiceId, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByChoiceId, args);

			args = new Object[] {
				pollsVoteModelImpl.getQuestionId(),
				pollsVoteModelImpl.getUserId()
			};

			finderCache.removeResult(_finderPathCountByQ_U, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByQ_U, args);

			finderCache.removeResult(_finderPathCountAll, FINDER_ARGS_EMPTY);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindAll, FINDER_ARGS_EMPTY);
		}
		else {
			if ((pollsVoteModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByUuid.getColumnBitmask()) !=
					 0) {

				Object[] args = new Object[] {
					pollsVoteModelImpl.getOriginalUuid()
				};

				finderCache.removeResult(_finderPathCountByUuid, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByUuid, args);

				args = new Object[] {pollsVoteModelImpl.getUuid()};

				finderCache.removeResult(_finderPathCountByUuid, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByUuid, args);
			}

			if ((pollsVoteModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByUuid_C.getColumnBitmask()) !=
					 0) {

				Object[] args = new Object[] {
					pollsVoteModelImpl.getOriginalUuid(),
					pollsVoteModelImpl.getOriginalCompanyId()
				};

				finderCache.removeResult(_finderPathCountByUuid_C, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByUuid_C, args);

				args = new Object[] {
					pollsVoteModelImpl.getUuid(),
					pollsVoteModelImpl.getCompanyId()
				};

				finderCache.removeResult(_finderPathCountByUuid_C, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByUuid_C, args);
			}

			if ((pollsVoteModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByQuestionId.
					 getColumnBitmask()) != 0) {

				Object[] args = new Object[] {
					pollsVoteModelImpl.getOriginalQuestionId()
				};

				finderCache.removeResult(_finderPathCountByQuestionId, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByQuestionId, args);

				args = new Object[] {pollsVoteModelImpl.getQuestionId()};

				finderCache.removeResult(_finderPathCountByQuestionId, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByQuestionId, args);
			}

			if ((pollsVoteModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByChoiceId.
					 getColumnBitmask()) != 0) {

				Object[] args = new Object[] {
					pollsVoteModelImpl.getOriginalChoiceId()
				};

				finderCache.removeResult(_finderPathCountByChoiceId, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByChoiceId, args);

				args = new Object[] {pollsVoteModelImpl.getChoiceId()};

				finderCache.removeResult(_finderPathCountByChoiceId, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByChoiceId, args);
			}

			if ((pollsVoteModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByQ_U.getColumnBitmask()) !=
					 0) {

				Object[] args = new Object[] {
					pollsVoteModelImpl.getOriginalQuestionId(),
					pollsVoteModelImpl.getOriginalUserId()
				};

				finderCache.removeResult(_finderPathCountByQ_U, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByQ_U, args);

				args = new Object[] {
					pollsVoteModelImpl.getQuestionId(),
					pollsVoteModelImpl.getUserId()
				};

				finderCache.removeResult(_finderPathCountByQ_U, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByQ_U, args);
			}
		}

		entityCache.putResult(
			entityCacheEnabled, PollsVoteImpl.class, pollsVote.getPrimaryKey(),
			pollsVote, false);

		clearUniqueFindersCache(pollsVoteModelImpl, false);
		cacheUniqueFindersCache(pollsVoteModelImpl);

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
				finderPath, finderArgs, this);
		}

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				query.append(_SQL_SELECT_POLLSVOTE);

				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_POLLSVOTE;

				sql = sql.concat(PollsVoteModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				list = (List<PollsVote>)QueryUtil.list(
					q, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					finderCache.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception e) {
				if (useFinderCache) {
					finderCache.removeResult(finderPath, finderArgs);
				}

				throw processException(e);
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
			_finderPathCountAll, FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_POLLSVOTE);

				count = (Long)q.uniqueResult();

				finderCache.putResult(
					_finderPathCountAll, FINDER_ARGS_EMPTY, count);
			}
			catch (Exception e) {
				finderCache.removeResult(
					_finderPathCountAll, FINDER_ARGS_EMPTY);

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
	public void activate() {
		PollsVoteModelImpl.setEntityCacheEnabled(entityCacheEnabled);
		PollsVoteModelImpl.setFinderCacheEnabled(finderCacheEnabled);

		_finderPathWithPaginationFindAll = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, PollsVoteImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);

		_finderPathWithoutPaginationFindAll = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, PollsVoteImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll",
			new String[0]);

		_finderPathCountAll = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll",
			new String[0]);

		_finderPathWithPaginationFindByUuid = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, PollsVoteImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUuid",
			new String[] {
				String.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByUuid = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, PollsVoteImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid",
			new String[] {String.class.getName()},
			PollsVoteModelImpl.UUID_COLUMN_BITMASK);

		_finderPathCountByUuid = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUuid",
			new String[] {String.class.getName()});

		_finderPathFetchByUUID_G = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, PollsVoteImpl.class,
			FINDER_CLASS_NAME_ENTITY, "fetchByUUID_G",
			new String[] {String.class.getName(), Long.class.getName()},
			PollsVoteModelImpl.UUID_COLUMN_BITMASK |
			PollsVoteModelImpl.GROUPID_COLUMN_BITMASK);

		_finderPathCountByUUID_G = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUUID_G",
			new String[] {String.class.getName(), Long.class.getName()});

		_finderPathWithPaginationFindByUuid_C = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, PollsVoteImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUuid_C",
			new String[] {
				String.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByUuid_C = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, PollsVoteImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid_C",
			new String[] {String.class.getName(), Long.class.getName()},
			PollsVoteModelImpl.UUID_COLUMN_BITMASK |
			PollsVoteModelImpl.COMPANYID_COLUMN_BITMASK);

		_finderPathCountByUuid_C = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUuid_C",
			new String[] {String.class.getName(), Long.class.getName()});

		_finderPathWithPaginationFindByQuestionId = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, PollsVoteImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByQuestionId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByQuestionId = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, PollsVoteImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByQuestionId",
			new String[] {Long.class.getName()},
			PollsVoteModelImpl.QUESTIONID_COLUMN_BITMASK);

		_finderPathCountByQuestionId = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByQuestionId",
			new String[] {Long.class.getName()});

		_finderPathWithPaginationFindByChoiceId = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, PollsVoteImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByChoiceId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByChoiceId = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, PollsVoteImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByChoiceId",
			new String[] {Long.class.getName()},
			PollsVoteModelImpl.CHOICEID_COLUMN_BITMASK);

		_finderPathCountByChoiceId = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByChoiceId",
			new String[] {Long.class.getName()});

		_finderPathWithPaginationFindByQ_U = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, PollsVoteImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByQ_U",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByQ_U = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, PollsVoteImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByQ_U",
			new String[] {Long.class.getName(), Long.class.getName()},
			PollsVoteModelImpl.QUESTIONID_COLUMN_BITMASK |
			PollsVoteModelImpl.USERID_COLUMN_BITMASK);

		_finderPathCountByQ_U = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByQ_U",
			new String[] {Long.class.getName(), Long.class.getName()});
	}

	@Deactivate
	public void deactivate() {
		entityCache.removeCache(PollsVoteImpl.class.getName());
		finderCache.removeCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@Override
	@Reference(
		target = PollsPersistenceConstants.SERVICE_CONFIGURATION_FILTER,
		unbind = "-"
	)
	public void setConfiguration(Configuration configuration) {
		super.setConfiguration(configuration);

		_columnBitmaskEnabled = GetterUtil.getBoolean(
			configuration.get(
				"value.object.column.bitmask.enabled.com.liferay.polls.model.PollsVote"),
			true);
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

	private boolean _columnBitmaskEnabled;

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

	static {
		try {
			Class.forName(PollsPersistenceConstants.class.getName());
		}
		catch (ClassNotFoundException cnfe) {
			throw new ExceptionInInitializerError(cnfe);
		}
	}

}