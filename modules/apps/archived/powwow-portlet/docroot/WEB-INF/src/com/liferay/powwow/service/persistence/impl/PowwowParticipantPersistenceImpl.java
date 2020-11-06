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

package com.liferay.powwow.service.persistence.impl;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.orm.ArgumentsResolver;
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
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.powwow.exception.NoSuchParticipantException;
import com.liferay.powwow.model.PowwowParticipant;
import com.liferay.powwow.model.PowwowParticipantTable;
import com.liferay.powwow.model.impl.PowwowParticipantImpl;
import com.liferay.powwow.model.impl.PowwowParticipantModelImpl;
import com.liferay.powwow.service.persistence.PowwowParticipantPersistence;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;
import com.liferay.registry.ServiceRegistration;

import java.io.Serializable;

import java.lang.reflect.InvocationHandler;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * The persistence implementation for the powwow participant service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Shinn Lok
 * @generated
 */
public class PowwowParticipantPersistenceImpl
	extends BasePersistenceImpl<PowwowParticipant>
	implements PowwowParticipantPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>PowwowParticipantUtil</code> to access the powwow participant persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		PowwowParticipantImpl.class.getName();

	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List1";

	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List2";

	private FinderPath _finderPathWithPaginationFindAll;
	private FinderPath _finderPathWithoutPaginationFindAll;
	private FinderPath _finderPathCountAll;
	private FinderPath _finderPathWithPaginationFindByPowwowMeetingId;
	private FinderPath _finderPathWithoutPaginationFindByPowwowMeetingId;
	private FinderPath _finderPathCountByPowwowMeetingId;

	/**
	 * Returns all the powwow participants where powwowMeetingId = &#63;.
	 *
	 * @param powwowMeetingId the powwow meeting ID
	 * @return the matching powwow participants
	 */
	@Override
	public List<PowwowParticipant> findByPowwowMeetingId(long powwowMeetingId) {
		return findByPowwowMeetingId(
			powwowMeetingId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the powwow participants where powwowMeetingId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>PowwowParticipantModelImpl</code>.
	 * </p>
	 *
	 * @param powwowMeetingId the powwow meeting ID
	 * @param start the lower bound of the range of powwow participants
	 * @param end the upper bound of the range of powwow participants (not inclusive)
	 * @return the range of matching powwow participants
	 */
	@Override
	public List<PowwowParticipant> findByPowwowMeetingId(
		long powwowMeetingId, int start, int end) {

		return findByPowwowMeetingId(powwowMeetingId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the powwow participants where powwowMeetingId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>PowwowParticipantModelImpl</code>.
	 * </p>
	 *
	 * @param powwowMeetingId the powwow meeting ID
	 * @param start the lower bound of the range of powwow participants
	 * @param end the upper bound of the range of powwow participants (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching powwow participants
	 */
	@Override
	public List<PowwowParticipant> findByPowwowMeetingId(
		long powwowMeetingId, int start, int end,
		OrderByComparator<PowwowParticipant> orderByComparator) {

		return findByPowwowMeetingId(
			powwowMeetingId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the powwow participants where powwowMeetingId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>PowwowParticipantModelImpl</code>.
	 * </p>
	 *
	 * @param powwowMeetingId the powwow meeting ID
	 * @param start the lower bound of the range of powwow participants
	 * @param end the upper bound of the range of powwow participants (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching powwow participants
	 */
	@Override
	public List<PowwowParticipant> findByPowwowMeetingId(
		long powwowMeetingId, int start, int end,
		OrderByComparator<PowwowParticipant> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByPowwowMeetingId;
				finderArgs = new Object[] {powwowMeetingId};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByPowwowMeetingId;
			finderArgs = new Object[] {
				powwowMeetingId, start, end, orderByComparator
			};
		}

		List<PowwowParticipant> list = null;

		if (useFinderCache) {
			list = (List<PowwowParticipant>)FinderCacheUtil.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (PowwowParticipant powwowParticipant : list) {
					if (powwowMeetingId !=
							powwowParticipant.getPowwowMeetingId()) {

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

			sb.append(_SQL_SELECT_POWWOWPARTICIPANT_WHERE);

			sb.append(_FINDER_COLUMN_POWWOWMEETINGID_POWWOWMEETINGID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(PowwowParticipantModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(powwowMeetingId);

				list = (List<PowwowParticipant>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					FinderCacheUtil.putResult(finderPath, finderArgs, list);
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
	 * Returns the first powwow participant in the ordered set where powwowMeetingId = &#63;.
	 *
	 * @param powwowMeetingId the powwow meeting ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching powwow participant
	 * @throws NoSuchParticipantException if a matching powwow participant could not be found
	 */
	@Override
	public PowwowParticipant findByPowwowMeetingId_First(
			long powwowMeetingId,
			OrderByComparator<PowwowParticipant> orderByComparator)
		throws NoSuchParticipantException {

		PowwowParticipant powwowParticipant = fetchByPowwowMeetingId_First(
			powwowMeetingId, orderByComparator);

		if (powwowParticipant != null) {
			return powwowParticipant;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("powwowMeetingId=");
		sb.append(powwowMeetingId);

		sb.append("}");

		throw new NoSuchParticipantException(sb.toString());
	}

	/**
	 * Returns the first powwow participant in the ordered set where powwowMeetingId = &#63;.
	 *
	 * @param powwowMeetingId the powwow meeting ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching powwow participant, or <code>null</code> if a matching powwow participant could not be found
	 */
	@Override
	public PowwowParticipant fetchByPowwowMeetingId_First(
		long powwowMeetingId,
		OrderByComparator<PowwowParticipant> orderByComparator) {

		List<PowwowParticipant> list = findByPowwowMeetingId(
			powwowMeetingId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last powwow participant in the ordered set where powwowMeetingId = &#63;.
	 *
	 * @param powwowMeetingId the powwow meeting ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching powwow participant
	 * @throws NoSuchParticipantException if a matching powwow participant could not be found
	 */
	@Override
	public PowwowParticipant findByPowwowMeetingId_Last(
			long powwowMeetingId,
			OrderByComparator<PowwowParticipant> orderByComparator)
		throws NoSuchParticipantException {

		PowwowParticipant powwowParticipant = fetchByPowwowMeetingId_Last(
			powwowMeetingId, orderByComparator);

		if (powwowParticipant != null) {
			return powwowParticipant;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("powwowMeetingId=");
		sb.append(powwowMeetingId);

		sb.append("}");

		throw new NoSuchParticipantException(sb.toString());
	}

	/**
	 * Returns the last powwow participant in the ordered set where powwowMeetingId = &#63;.
	 *
	 * @param powwowMeetingId the powwow meeting ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching powwow participant, or <code>null</code> if a matching powwow participant could not be found
	 */
	@Override
	public PowwowParticipant fetchByPowwowMeetingId_Last(
		long powwowMeetingId,
		OrderByComparator<PowwowParticipant> orderByComparator) {

		int count = countByPowwowMeetingId(powwowMeetingId);

		if (count == 0) {
			return null;
		}

		List<PowwowParticipant> list = findByPowwowMeetingId(
			powwowMeetingId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the powwow participants before and after the current powwow participant in the ordered set where powwowMeetingId = &#63;.
	 *
	 * @param powwowParticipantId the primary key of the current powwow participant
	 * @param powwowMeetingId the powwow meeting ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next powwow participant
	 * @throws NoSuchParticipantException if a powwow participant with the primary key could not be found
	 */
	@Override
	public PowwowParticipant[] findByPowwowMeetingId_PrevAndNext(
			long powwowParticipantId, long powwowMeetingId,
			OrderByComparator<PowwowParticipant> orderByComparator)
		throws NoSuchParticipantException {

		PowwowParticipant powwowParticipant = findByPrimaryKey(
			powwowParticipantId);

		Session session = null;

		try {
			session = openSession();

			PowwowParticipant[] array = new PowwowParticipantImpl[3];

			array[0] = getByPowwowMeetingId_PrevAndNext(
				session, powwowParticipant, powwowMeetingId, orderByComparator,
				true);

			array[1] = powwowParticipant;

			array[2] = getByPowwowMeetingId_PrevAndNext(
				session, powwowParticipant, powwowMeetingId, orderByComparator,
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

	protected PowwowParticipant getByPowwowMeetingId_PrevAndNext(
		Session session, PowwowParticipant powwowParticipant,
		long powwowMeetingId,
		OrderByComparator<PowwowParticipant> orderByComparator,
		boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(3);
		}

		sb.append(_SQL_SELECT_POWWOWPARTICIPANT_WHERE);

		sb.append(_FINDER_COLUMN_POWWOWMEETINGID_POWWOWMEETINGID_2);

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
			sb.append(PowwowParticipantModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(powwowMeetingId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						powwowParticipant)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<PowwowParticipant> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the powwow participants where powwowMeetingId = &#63; from the database.
	 *
	 * @param powwowMeetingId the powwow meeting ID
	 */
	@Override
	public void removeByPowwowMeetingId(long powwowMeetingId) {
		for (PowwowParticipant powwowParticipant :
				findByPowwowMeetingId(
					powwowMeetingId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(powwowParticipant);
		}
	}

	/**
	 * Returns the number of powwow participants where powwowMeetingId = &#63;.
	 *
	 * @param powwowMeetingId the powwow meeting ID
	 * @return the number of matching powwow participants
	 */
	@Override
	public int countByPowwowMeetingId(long powwowMeetingId) {
		FinderPath finderPath = _finderPathCountByPowwowMeetingId;

		Object[] finderArgs = new Object[] {powwowMeetingId};

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_POWWOWPARTICIPANT_WHERE);

			sb.append(_FINDER_COLUMN_POWWOWMEETINGID_POWWOWMEETINGID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(powwowMeetingId);

				count = (Long)query.uniqueResult();

				FinderCacheUtil.putResult(finderPath, finderArgs, count);
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

	private static final String
		_FINDER_COLUMN_POWWOWMEETINGID_POWWOWMEETINGID_2 =
			"powwowParticipant.powwowMeetingId = ?";

	private FinderPath _finderPathFetchByPMI_PUI;
	private FinderPath _finderPathCountByPMI_PUI;

	/**
	 * Returns the powwow participant where powwowMeetingId = &#63; and participantUserId = &#63; or throws a <code>NoSuchParticipantException</code> if it could not be found.
	 *
	 * @param powwowMeetingId the powwow meeting ID
	 * @param participantUserId the participant user ID
	 * @return the matching powwow participant
	 * @throws NoSuchParticipantException if a matching powwow participant could not be found
	 */
	@Override
	public PowwowParticipant findByPMI_PUI(
			long powwowMeetingId, long participantUserId)
		throws NoSuchParticipantException {

		PowwowParticipant powwowParticipant = fetchByPMI_PUI(
			powwowMeetingId, participantUserId);

		if (powwowParticipant == null) {
			StringBundler sb = new StringBundler(6);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("powwowMeetingId=");
			sb.append(powwowMeetingId);

			sb.append(", participantUserId=");
			sb.append(participantUserId);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchParticipantException(sb.toString());
		}

		return powwowParticipant;
	}

	/**
	 * Returns the powwow participant where powwowMeetingId = &#63; and participantUserId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param powwowMeetingId the powwow meeting ID
	 * @param participantUserId the participant user ID
	 * @return the matching powwow participant, or <code>null</code> if a matching powwow participant could not be found
	 */
	@Override
	public PowwowParticipant fetchByPMI_PUI(
		long powwowMeetingId, long participantUserId) {

		return fetchByPMI_PUI(powwowMeetingId, participantUserId, true);
	}

	/**
	 * Returns the powwow participant where powwowMeetingId = &#63; and participantUserId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param powwowMeetingId the powwow meeting ID
	 * @param participantUserId the participant user ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching powwow participant, or <code>null</code> if a matching powwow participant could not be found
	 */
	@Override
	public PowwowParticipant fetchByPMI_PUI(
		long powwowMeetingId, long participantUserId, boolean useFinderCache) {

		Object[] finderArgs = null;

		if (useFinderCache) {
			finderArgs = new Object[] {powwowMeetingId, participantUserId};
		}

		Object result = null;

		if (useFinderCache) {
			result = FinderCacheUtil.getResult(
				_finderPathFetchByPMI_PUI, finderArgs);
		}

		if (result instanceof PowwowParticipant) {
			PowwowParticipant powwowParticipant = (PowwowParticipant)result;

			if ((powwowMeetingId != powwowParticipant.getPowwowMeetingId()) ||
				(participantUserId !=
					powwowParticipant.getParticipantUserId())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_SELECT_POWWOWPARTICIPANT_WHERE);

			sb.append(_FINDER_COLUMN_PMI_PUI_POWWOWMEETINGID_2);

			sb.append(_FINDER_COLUMN_PMI_PUI_PARTICIPANTUSERID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(powwowMeetingId);

				queryPos.add(participantUserId);

				List<PowwowParticipant> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache) {
						FinderCacheUtil.putResult(
							_finderPathFetchByPMI_PUI, finderArgs, list);
					}
				}
				else {
					if (list.size() > 1) {
						Collections.sort(list, Collections.reverseOrder());

						if (_log.isWarnEnabled()) {
							if (!useFinderCache) {
								finderArgs = new Object[] {
									powwowMeetingId, participantUserId
								};
							}

							_log.warn(
								"PowwowParticipantPersistenceImpl.fetchByPMI_PUI(long, long, boolean) with parameters (" +
									StringUtil.merge(finderArgs) +
										") yields a result set with more than 1 result. This violates the logical unique restriction. There is no order guarantee on which result is returned by this finder.");
						}
					}

					PowwowParticipant powwowParticipant = list.get(0);

					result = powwowParticipant;

					cacheResult(powwowParticipant);
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
			return (PowwowParticipant)result;
		}
	}

	/**
	 * Removes the powwow participant where powwowMeetingId = &#63; and participantUserId = &#63; from the database.
	 *
	 * @param powwowMeetingId the powwow meeting ID
	 * @param participantUserId the participant user ID
	 * @return the powwow participant that was removed
	 */
	@Override
	public PowwowParticipant removeByPMI_PUI(
			long powwowMeetingId, long participantUserId)
		throws NoSuchParticipantException {

		PowwowParticipant powwowParticipant = findByPMI_PUI(
			powwowMeetingId, participantUserId);

		return remove(powwowParticipant);
	}

	/**
	 * Returns the number of powwow participants where powwowMeetingId = &#63; and participantUserId = &#63;.
	 *
	 * @param powwowMeetingId the powwow meeting ID
	 * @param participantUserId the participant user ID
	 * @return the number of matching powwow participants
	 */
	@Override
	public int countByPMI_PUI(long powwowMeetingId, long participantUserId) {
		FinderPath finderPath = _finderPathCountByPMI_PUI;

		Object[] finderArgs = new Object[] {powwowMeetingId, participantUserId};

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_POWWOWPARTICIPANT_WHERE);

			sb.append(_FINDER_COLUMN_PMI_PUI_POWWOWMEETINGID_2);

			sb.append(_FINDER_COLUMN_PMI_PUI_PARTICIPANTUSERID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(powwowMeetingId);

				queryPos.add(participantUserId);

				count = (Long)query.uniqueResult();

				FinderCacheUtil.putResult(finderPath, finderArgs, count);
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

	private static final String _FINDER_COLUMN_PMI_PUI_POWWOWMEETINGID_2 =
		"powwowParticipant.powwowMeetingId = ? AND ";

	private static final String _FINDER_COLUMN_PMI_PUI_PARTICIPANTUSERID_2 =
		"powwowParticipant.participantUserId = ?";

	private FinderPath _finderPathFetchByPMI_EA;
	private FinderPath _finderPathCountByPMI_EA;

	/**
	 * Returns the powwow participant where powwowMeetingId = &#63; and emailAddress = &#63; or throws a <code>NoSuchParticipantException</code> if it could not be found.
	 *
	 * @param powwowMeetingId the powwow meeting ID
	 * @param emailAddress the email address
	 * @return the matching powwow participant
	 * @throws NoSuchParticipantException if a matching powwow participant could not be found
	 */
	@Override
	public PowwowParticipant findByPMI_EA(
			long powwowMeetingId, String emailAddress)
		throws NoSuchParticipantException {

		PowwowParticipant powwowParticipant = fetchByPMI_EA(
			powwowMeetingId, emailAddress);

		if (powwowParticipant == null) {
			StringBundler sb = new StringBundler(6);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("powwowMeetingId=");
			sb.append(powwowMeetingId);

			sb.append(", emailAddress=");
			sb.append(emailAddress);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchParticipantException(sb.toString());
		}

		return powwowParticipant;
	}

	/**
	 * Returns the powwow participant where powwowMeetingId = &#63; and emailAddress = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param powwowMeetingId the powwow meeting ID
	 * @param emailAddress the email address
	 * @return the matching powwow participant, or <code>null</code> if a matching powwow participant could not be found
	 */
	@Override
	public PowwowParticipant fetchByPMI_EA(
		long powwowMeetingId, String emailAddress) {

		return fetchByPMI_EA(powwowMeetingId, emailAddress, true);
	}

	/**
	 * Returns the powwow participant where powwowMeetingId = &#63; and emailAddress = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param powwowMeetingId the powwow meeting ID
	 * @param emailAddress the email address
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching powwow participant, or <code>null</code> if a matching powwow participant could not be found
	 */
	@Override
	public PowwowParticipant fetchByPMI_EA(
		long powwowMeetingId, String emailAddress, boolean useFinderCache) {

		emailAddress = Objects.toString(emailAddress, "");

		Object[] finderArgs = null;

		if (useFinderCache) {
			finderArgs = new Object[] {powwowMeetingId, emailAddress};
		}

		Object result = null;

		if (useFinderCache) {
			result = FinderCacheUtil.getResult(
				_finderPathFetchByPMI_EA, finderArgs);
		}

		if (result instanceof PowwowParticipant) {
			PowwowParticipant powwowParticipant = (PowwowParticipant)result;

			if ((powwowMeetingId != powwowParticipant.getPowwowMeetingId()) ||
				!Objects.equals(
					emailAddress, powwowParticipant.getEmailAddress())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_SELECT_POWWOWPARTICIPANT_WHERE);

			sb.append(_FINDER_COLUMN_PMI_EA_POWWOWMEETINGID_2);

			boolean bindEmailAddress = false;

			if (emailAddress.isEmpty()) {
				sb.append(_FINDER_COLUMN_PMI_EA_EMAILADDRESS_3);
			}
			else {
				bindEmailAddress = true;

				sb.append(_FINDER_COLUMN_PMI_EA_EMAILADDRESS_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(powwowMeetingId);

				if (bindEmailAddress) {
					queryPos.add(emailAddress);
				}

				List<PowwowParticipant> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache) {
						FinderCacheUtil.putResult(
							_finderPathFetchByPMI_EA, finderArgs, list);
					}
				}
				else {
					PowwowParticipant powwowParticipant = list.get(0);

					result = powwowParticipant;

					cacheResult(powwowParticipant);
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
			return (PowwowParticipant)result;
		}
	}

	/**
	 * Removes the powwow participant where powwowMeetingId = &#63; and emailAddress = &#63; from the database.
	 *
	 * @param powwowMeetingId the powwow meeting ID
	 * @param emailAddress the email address
	 * @return the powwow participant that was removed
	 */
	@Override
	public PowwowParticipant removeByPMI_EA(
			long powwowMeetingId, String emailAddress)
		throws NoSuchParticipantException {

		PowwowParticipant powwowParticipant = findByPMI_EA(
			powwowMeetingId, emailAddress);

		return remove(powwowParticipant);
	}

	/**
	 * Returns the number of powwow participants where powwowMeetingId = &#63; and emailAddress = &#63;.
	 *
	 * @param powwowMeetingId the powwow meeting ID
	 * @param emailAddress the email address
	 * @return the number of matching powwow participants
	 */
	@Override
	public int countByPMI_EA(long powwowMeetingId, String emailAddress) {
		emailAddress = Objects.toString(emailAddress, "");

		FinderPath finderPath = _finderPathCountByPMI_EA;

		Object[] finderArgs = new Object[] {powwowMeetingId, emailAddress};

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_POWWOWPARTICIPANT_WHERE);

			sb.append(_FINDER_COLUMN_PMI_EA_POWWOWMEETINGID_2);

			boolean bindEmailAddress = false;

			if (emailAddress.isEmpty()) {
				sb.append(_FINDER_COLUMN_PMI_EA_EMAILADDRESS_3);
			}
			else {
				bindEmailAddress = true;

				sb.append(_FINDER_COLUMN_PMI_EA_EMAILADDRESS_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(powwowMeetingId);

				if (bindEmailAddress) {
					queryPos.add(emailAddress);
				}

				count = (Long)query.uniqueResult();

				FinderCacheUtil.putResult(finderPath, finderArgs, count);
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

	private static final String _FINDER_COLUMN_PMI_EA_POWWOWMEETINGID_2 =
		"powwowParticipant.powwowMeetingId = ? AND ";

	private static final String _FINDER_COLUMN_PMI_EA_EMAILADDRESS_2 =
		"powwowParticipant.emailAddress = ?";

	private static final String _FINDER_COLUMN_PMI_EA_EMAILADDRESS_3 =
		"(powwowParticipant.emailAddress IS NULL OR powwowParticipant.emailAddress = '')";

	private FinderPath _finderPathWithPaginationFindByPMI_T;
	private FinderPath _finderPathWithoutPaginationFindByPMI_T;
	private FinderPath _finderPathCountByPMI_T;

	/**
	 * Returns all the powwow participants where powwowMeetingId = &#63; and type = &#63;.
	 *
	 * @param powwowMeetingId the powwow meeting ID
	 * @param type the type
	 * @return the matching powwow participants
	 */
	@Override
	public List<PowwowParticipant> findByPMI_T(long powwowMeetingId, int type) {
		return findByPMI_T(
			powwowMeetingId, type, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the powwow participants where powwowMeetingId = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>PowwowParticipantModelImpl</code>.
	 * </p>
	 *
	 * @param powwowMeetingId the powwow meeting ID
	 * @param type the type
	 * @param start the lower bound of the range of powwow participants
	 * @param end the upper bound of the range of powwow participants (not inclusive)
	 * @return the range of matching powwow participants
	 */
	@Override
	public List<PowwowParticipant> findByPMI_T(
		long powwowMeetingId, int type, int start, int end) {

		return findByPMI_T(powwowMeetingId, type, start, end, null);
	}

	/**
	 * Returns an ordered range of all the powwow participants where powwowMeetingId = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>PowwowParticipantModelImpl</code>.
	 * </p>
	 *
	 * @param powwowMeetingId the powwow meeting ID
	 * @param type the type
	 * @param start the lower bound of the range of powwow participants
	 * @param end the upper bound of the range of powwow participants (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching powwow participants
	 */
	@Override
	public List<PowwowParticipant> findByPMI_T(
		long powwowMeetingId, int type, int start, int end,
		OrderByComparator<PowwowParticipant> orderByComparator) {

		return findByPMI_T(
			powwowMeetingId, type, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the powwow participants where powwowMeetingId = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>PowwowParticipantModelImpl</code>.
	 * </p>
	 *
	 * @param powwowMeetingId the powwow meeting ID
	 * @param type the type
	 * @param start the lower bound of the range of powwow participants
	 * @param end the upper bound of the range of powwow participants (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching powwow participants
	 */
	@Override
	public List<PowwowParticipant> findByPMI_T(
		long powwowMeetingId, int type, int start, int end,
		OrderByComparator<PowwowParticipant> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByPMI_T;
				finderArgs = new Object[] {powwowMeetingId, type};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByPMI_T;
			finderArgs = new Object[] {
				powwowMeetingId, type, start, end, orderByComparator
			};
		}

		List<PowwowParticipant> list = null;

		if (useFinderCache) {
			list = (List<PowwowParticipant>)FinderCacheUtil.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (PowwowParticipant powwowParticipant : list) {
					if ((powwowMeetingId !=
							powwowParticipant.getPowwowMeetingId()) ||
						(type != powwowParticipant.getType())) {

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

			sb.append(_SQL_SELECT_POWWOWPARTICIPANT_WHERE);

			sb.append(_FINDER_COLUMN_PMI_T_POWWOWMEETINGID_2);

			sb.append(_FINDER_COLUMN_PMI_T_TYPE_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(PowwowParticipantModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(powwowMeetingId);

				queryPos.add(type);

				list = (List<PowwowParticipant>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					FinderCacheUtil.putResult(finderPath, finderArgs, list);
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
	 * Returns the first powwow participant in the ordered set where powwowMeetingId = &#63; and type = &#63;.
	 *
	 * @param powwowMeetingId the powwow meeting ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching powwow participant
	 * @throws NoSuchParticipantException if a matching powwow participant could not be found
	 */
	@Override
	public PowwowParticipant findByPMI_T_First(
			long powwowMeetingId, int type,
			OrderByComparator<PowwowParticipant> orderByComparator)
		throws NoSuchParticipantException {

		PowwowParticipant powwowParticipant = fetchByPMI_T_First(
			powwowMeetingId, type, orderByComparator);

		if (powwowParticipant != null) {
			return powwowParticipant;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("powwowMeetingId=");
		sb.append(powwowMeetingId);

		sb.append(", type=");
		sb.append(type);

		sb.append("}");

		throw new NoSuchParticipantException(sb.toString());
	}

	/**
	 * Returns the first powwow participant in the ordered set where powwowMeetingId = &#63; and type = &#63;.
	 *
	 * @param powwowMeetingId the powwow meeting ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching powwow participant, or <code>null</code> if a matching powwow participant could not be found
	 */
	@Override
	public PowwowParticipant fetchByPMI_T_First(
		long powwowMeetingId, int type,
		OrderByComparator<PowwowParticipant> orderByComparator) {

		List<PowwowParticipant> list = findByPMI_T(
			powwowMeetingId, type, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last powwow participant in the ordered set where powwowMeetingId = &#63; and type = &#63;.
	 *
	 * @param powwowMeetingId the powwow meeting ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching powwow participant
	 * @throws NoSuchParticipantException if a matching powwow participant could not be found
	 */
	@Override
	public PowwowParticipant findByPMI_T_Last(
			long powwowMeetingId, int type,
			OrderByComparator<PowwowParticipant> orderByComparator)
		throws NoSuchParticipantException {

		PowwowParticipant powwowParticipant = fetchByPMI_T_Last(
			powwowMeetingId, type, orderByComparator);

		if (powwowParticipant != null) {
			return powwowParticipant;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("powwowMeetingId=");
		sb.append(powwowMeetingId);

		sb.append(", type=");
		sb.append(type);

		sb.append("}");

		throw new NoSuchParticipantException(sb.toString());
	}

	/**
	 * Returns the last powwow participant in the ordered set where powwowMeetingId = &#63; and type = &#63;.
	 *
	 * @param powwowMeetingId the powwow meeting ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching powwow participant, or <code>null</code> if a matching powwow participant could not be found
	 */
	@Override
	public PowwowParticipant fetchByPMI_T_Last(
		long powwowMeetingId, int type,
		OrderByComparator<PowwowParticipant> orderByComparator) {

		int count = countByPMI_T(powwowMeetingId, type);

		if (count == 0) {
			return null;
		}

		List<PowwowParticipant> list = findByPMI_T(
			powwowMeetingId, type, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the powwow participants before and after the current powwow participant in the ordered set where powwowMeetingId = &#63; and type = &#63;.
	 *
	 * @param powwowParticipantId the primary key of the current powwow participant
	 * @param powwowMeetingId the powwow meeting ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next powwow participant
	 * @throws NoSuchParticipantException if a powwow participant with the primary key could not be found
	 */
	@Override
	public PowwowParticipant[] findByPMI_T_PrevAndNext(
			long powwowParticipantId, long powwowMeetingId, int type,
			OrderByComparator<PowwowParticipant> orderByComparator)
		throws NoSuchParticipantException {

		PowwowParticipant powwowParticipant = findByPrimaryKey(
			powwowParticipantId);

		Session session = null;

		try {
			session = openSession();

			PowwowParticipant[] array = new PowwowParticipantImpl[3];

			array[0] = getByPMI_T_PrevAndNext(
				session, powwowParticipant, powwowMeetingId, type,
				orderByComparator, true);

			array[1] = powwowParticipant;

			array[2] = getByPMI_T_PrevAndNext(
				session, powwowParticipant, powwowMeetingId, type,
				orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected PowwowParticipant getByPMI_T_PrevAndNext(
		Session session, PowwowParticipant powwowParticipant,
		long powwowMeetingId, int type,
		OrderByComparator<PowwowParticipant> orderByComparator,
		boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				5 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(4);
		}

		sb.append(_SQL_SELECT_POWWOWPARTICIPANT_WHERE);

		sb.append(_FINDER_COLUMN_PMI_T_POWWOWMEETINGID_2);

		sb.append(_FINDER_COLUMN_PMI_T_TYPE_2);

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
			sb.append(PowwowParticipantModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(powwowMeetingId);

		queryPos.add(type);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						powwowParticipant)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<PowwowParticipant> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the powwow participants where powwowMeetingId = &#63; and type = &#63; from the database.
	 *
	 * @param powwowMeetingId the powwow meeting ID
	 * @param type the type
	 */
	@Override
	public void removeByPMI_T(long powwowMeetingId, int type) {
		for (PowwowParticipant powwowParticipant :
				findByPMI_T(
					powwowMeetingId, type, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(powwowParticipant);
		}
	}

	/**
	 * Returns the number of powwow participants where powwowMeetingId = &#63; and type = &#63;.
	 *
	 * @param powwowMeetingId the powwow meeting ID
	 * @param type the type
	 * @return the number of matching powwow participants
	 */
	@Override
	public int countByPMI_T(long powwowMeetingId, int type) {
		FinderPath finderPath = _finderPathCountByPMI_T;

		Object[] finderArgs = new Object[] {powwowMeetingId, type};

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_POWWOWPARTICIPANT_WHERE);

			sb.append(_FINDER_COLUMN_PMI_T_POWWOWMEETINGID_2);

			sb.append(_FINDER_COLUMN_PMI_T_TYPE_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(powwowMeetingId);

				queryPos.add(type);

				count = (Long)query.uniqueResult();

				FinderCacheUtil.putResult(finderPath, finderArgs, count);
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

	private static final String _FINDER_COLUMN_PMI_T_POWWOWMEETINGID_2 =
		"powwowParticipant.powwowMeetingId = ? AND ";

	private static final String _FINDER_COLUMN_PMI_T_TYPE_2 =
		"powwowParticipant.type = ?";

	public PowwowParticipantPersistenceImpl() {
		Map<String, String> dbColumnNames = new HashMap<String, String>();

		dbColumnNames.put("type", "type_");

		setDBColumnNames(dbColumnNames);

		setModelClass(PowwowParticipant.class);

		setModelImplClass(PowwowParticipantImpl.class);
		setModelPKClass(long.class);

		setTable(PowwowParticipantTable.INSTANCE);
	}

	/**
	 * Caches the powwow participant in the entity cache if it is enabled.
	 *
	 * @param powwowParticipant the powwow participant
	 */
	@Override
	public void cacheResult(PowwowParticipant powwowParticipant) {
		EntityCacheUtil.putResult(
			PowwowParticipantImpl.class, powwowParticipant.getPrimaryKey(),
			powwowParticipant);

		FinderCacheUtil.putResult(
			_finderPathFetchByPMI_PUI,
			new Object[] {
				powwowParticipant.getPowwowMeetingId(),
				powwowParticipant.getParticipantUserId()
			},
			powwowParticipant);

		FinderCacheUtil.putResult(
			_finderPathFetchByPMI_EA,
			new Object[] {
				powwowParticipant.getPowwowMeetingId(),
				powwowParticipant.getEmailAddress()
			},
			powwowParticipant);
	}

	/**
	 * Caches the powwow participants in the entity cache if it is enabled.
	 *
	 * @param powwowParticipants the powwow participants
	 */
	@Override
	public void cacheResult(List<PowwowParticipant> powwowParticipants) {
		for (PowwowParticipant powwowParticipant : powwowParticipants) {
			if (EntityCacheUtil.getResult(
					PowwowParticipantImpl.class,
					powwowParticipant.getPrimaryKey()) == null) {

				cacheResult(powwowParticipant);
			}
		}
	}

	/**
	 * Clears the cache for all powwow participants.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		EntityCacheUtil.clearCache(PowwowParticipantImpl.class);

		FinderCacheUtil.clearCache(PowwowParticipantImpl.class);
	}

	/**
	 * Clears the cache for the powwow participant.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(PowwowParticipant powwowParticipant) {
		EntityCacheUtil.removeResult(
			PowwowParticipantImpl.class, powwowParticipant);
	}

	@Override
	public void clearCache(List<PowwowParticipant> powwowParticipants) {
		for (PowwowParticipant powwowParticipant : powwowParticipants) {
			EntityCacheUtil.removeResult(
				PowwowParticipantImpl.class, powwowParticipant);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		FinderCacheUtil.clearCache(PowwowParticipantImpl.class);

		for (Serializable primaryKey : primaryKeys) {
			EntityCacheUtil.removeResult(
				PowwowParticipantImpl.class, primaryKey);
		}
	}

	protected void cacheUniqueFindersCache(
		PowwowParticipantModelImpl powwowParticipantModelImpl) {

		Object[] args = new Object[] {
			powwowParticipantModelImpl.getPowwowMeetingId(),
			powwowParticipantModelImpl.getParticipantUserId()
		};

		FinderCacheUtil.putResult(
			_finderPathCountByPMI_PUI, args, Long.valueOf(1));
		FinderCacheUtil.putResult(
			_finderPathFetchByPMI_PUI, args, powwowParticipantModelImpl);

		args = new Object[] {
			powwowParticipantModelImpl.getPowwowMeetingId(),
			powwowParticipantModelImpl.getEmailAddress()
		};

		FinderCacheUtil.putResult(
			_finderPathCountByPMI_EA, args, Long.valueOf(1));
		FinderCacheUtil.putResult(
			_finderPathFetchByPMI_EA, args, powwowParticipantModelImpl);
	}

	/**
	 * Creates a new powwow participant with the primary key. Does not add the powwow participant to the database.
	 *
	 * @param powwowParticipantId the primary key for the new powwow participant
	 * @return the new powwow participant
	 */
	@Override
	public PowwowParticipant create(long powwowParticipantId) {
		PowwowParticipant powwowParticipant = new PowwowParticipantImpl();

		powwowParticipant.setNew(true);
		powwowParticipant.setPrimaryKey(powwowParticipantId);

		powwowParticipant.setCompanyId(CompanyThreadLocal.getCompanyId());

		return powwowParticipant;
	}

	/**
	 * Removes the powwow participant with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param powwowParticipantId the primary key of the powwow participant
	 * @return the powwow participant that was removed
	 * @throws NoSuchParticipantException if a powwow participant with the primary key could not be found
	 */
	@Override
	public PowwowParticipant remove(long powwowParticipantId)
		throws NoSuchParticipantException {

		return remove((Serializable)powwowParticipantId);
	}

	/**
	 * Removes the powwow participant with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the powwow participant
	 * @return the powwow participant that was removed
	 * @throws NoSuchParticipantException if a powwow participant with the primary key could not be found
	 */
	@Override
	public PowwowParticipant remove(Serializable primaryKey)
		throws NoSuchParticipantException {

		Session session = null;

		try {
			session = openSession();

			PowwowParticipant powwowParticipant =
				(PowwowParticipant)session.get(
					PowwowParticipantImpl.class, primaryKey);

			if (powwowParticipant == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchParticipantException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(powwowParticipant);
		}
		catch (NoSuchParticipantException noSuchEntityException) {
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
	protected PowwowParticipant removeImpl(
		PowwowParticipant powwowParticipant) {

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(powwowParticipant)) {
				powwowParticipant = (PowwowParticipant)session.get(
					PowwowParticipantImpl.class,
					powwowParticipant.getPrimaryKeyObj());
			}

			if (powwowParticipant != null) {
				session.delete(powwowParticipant);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (powwowParticipant != null) {
			clearCache(powwowParticipant);
		}

		return powwowParticipant;
	}

	@Override
	public PowwowParticipant updateImpl(PowwowParticipant powwowParticipant) {
		boolean isNew = powwowParticipant.isNew();

		if (!(powwowParticipant instanceof PowwowParticipantModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(powwowParticipant.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					powwowParticipant);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in powwowParticipant proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom PowwowParticipant implementation " +
					powwowParticipant.getClass());
		}

		PowwowParticipantModelImpl powwowParticipantModelImpl =
			(PowwowParticipantModelImpl)powwowParticipant;

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Date now = new Date();

		if (isNew && (powwowParticipant.getCreateDate() == null)) {
			if (serviceContext == null) {
				powwowParticipant.setCreateDate(now);
			}
			else {
				powwowParticipant.setCreateDate(
					serviceContext.getCreateDate(now));
			}
		}

		if (!powwowParticipantModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				powwowParticipant.setModifiedDate(now);
			}
			else {
				powwowParticipant.setModifiedDate(
					serviceContext.getModifiedDate(now));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (isNew) {
				session.save(powwowParticipant);
			}
			else {
				powwowParticipant = (PowwowParticipant)session.merge(
					powwowParticipant);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		EntityCacheUtil.putResult(
			PowwowParticipantImpl.class, powwowParticipantModelImpl, false,
			true);

		cacheUniqueFindersCache(powwowParticipantModelImpl);

		if (isNew) {
			powwowParticipant.setNew(false);
		}

		powwowParticipant.resetOriginalValues();

		return powwowParticipant;
	}

	/**
	 * Returns the powwow participant with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the powwow participant
	 * @return the powwow participant
	 * @throws NoSuchParticipantException if a powwow participant with the primary key could not be found
	 */
	@Override
	public PowwowParticipant findByPrimaryKey(Serializable primaryKey)
		throws NoSuchParticipantException {

		PowwowParticipant powwowParticipant = fetchByPrimaryKey(primaryKey);

		if (powwowParticipant == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchParticipantException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return powwowParticipant;
	}

	/**
	 * Returns the powwow participant with the primary key or throws a <code>NoSuchParticipantException</code> if it could not be found.
	 *
	 * @param powwowParticipantId the primary key of the powwow participant
	 * @return the powwow participant
	 * @throws NoSuchParticipantException if a powwow participant with the primary key could not be found
	 */
	@Override
	public PowwowParticipant findByPrimaryKey(long powwowParticipantId)
		throws NoSuchParticipantException {

		return findByPrimaryKey((Serializable)powwowParticipantId);
	}

	/**
	 * Returns the powwow participant with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param powwowParticipantId the primary key of the powwow participant
	 * @return the powwow participant, or <code>null</code> if a powwow participant with the primary key could not be found
	 */
	@Override
	public PowwowParticipant fetchByPrimaryKey(long powwowParticipantId) {
		return fetchByPrimaryKey((Serializable)powwowParticipantId);
	}

	/**
	 * Returns all the powwow participants.
	 *
	 * @return the powwow participants
	 */
	@Override
	public List<PowwowParticipant> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the powwow participants.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>PowwowParticipantModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of powwow participants
	 * @param end the upper bound of the range of powwow participants (not inclusive)
	 * @return the range of powwow participants
	 */
	@Override
	public List<PowwowParticipant> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the powwow participants.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>PowwowParticipantModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of powwow participants
	 * @param end the upper bound of the range of powwow participants (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of powwow participants
	 */
	@Override
	public List<PowwowParticipant> findAll(
		int start, int end,
		OrderByComparator<PowwowParticipant> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the powwow participants.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>PowwowParticipantModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of powwow participants
	 * @param end the upper bound of the range of powwow participants (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of powwow participants
	 */
	@Override
	public List<PowwowParticipant> findAll(
		int start, int end,
		OrderByComparator<PowwowParticipant> orderByComparator,
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

		List<PowwowParticipant> list = null;

		if (useFinderCache) {
			list = (List<PowwowParticipant>)FinderCacheUtil.getResult(
				finderPath, finderArgs);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_POWWOWPARTICIPANT);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_POWWOWPARTICIPANT;

				sql = sql.concat(PowwowParticipantModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<PowwowParticipant>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					FinderCacheUtil.putResult(finderPath, finderArgs, list);
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
	 * Removes all the powwow participants from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (PowwowParticipant powwowParticipant : findAll()) {
			remove(powwowParticipant);
		}
	}

	/**
	 * Returns the number of powwow participants.
	 *
	 * @return the number of powwow participants
	 */
	@Override
	public int countAll() {
		Long count = (Long)FinderCacheUtil.getResult(
			_finderPathCountAll, FINDER_ARGS_EMPTY);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(_SQL_COUNT_POWWOWPARTICIPANT);

				count = (Long)query.uniqueResult();

				FinderCacheUtil.putResult(
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
		return EntityCacheUtil.getEntityCache();
	}

	@Override
	protected String getPKDBName() {
		return "powwowParticipantId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_POWWOWPARTICIPANT;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return PowwowParticipantModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the powwow participant persistence.
	 */
	public void afterPropertiesSet() {
		Registry registry = RegistryUtil.getRegistry();

		_argumentsResolverServiceRegistration = registry.registerService(
			ArgumentsResolver.class,
			new PowwowParticipantModelArgumentsResolver());

		_finderPathWithPaginationFindAll = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0],
			new String[0], true);

		_finderPathWithoutPaginationFindAll = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0],
			new String[0], true);

		_finderPathCountAll = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll",
			new String[0], new String[0], false);

		_finderPathWithPaginationFindByPowwowMeetingId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByPowwowMeetingId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"powwowMeetingId"}, true);

		_finderPathWithoutPaginationFindByPowwowMeetingId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByPowwowMeetingId",
			new String[] {Long.class.getName()},
			new String[] {"powwowMeetingId"}, true);

		_finderPathCountByPowwowMeetingId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByPowwowMeetingId",
			new String[] {Long.class.getName()},
			new String[] {"powwowMeetingId"}, false);

		_finderPathFetchByPMI_PUI = new FinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByPMI_PUI",
			new String[] {Long.class.getName(), Long.class.getName()},
			new String[] {"powwowMeetingId", "participantUserId"}, true);

		_finderPathCountByPMI_PUI = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByPMI_PUI",
			new String[] {Long.class.getName(), Long.class.getName()},
			new String[] {"powwowMeetingId", "participantUserId"}, false);

		_finderPathFetchByPMI_EA = new FinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByPMI_EA",
			new String[] {Long.class.getName(), String.class.getName()},
			new String[] {"powwowMeetingId", "emailAddress"}, true);

		_finderPathCountByPMI_EA = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByPMI_EA",
			new String[] {Long.class.getName(), String.class.getName()},
			new String[] {"powwowMeetingId", "emailAddress"}, false);

		_finderPathWithPaginationFindByPMI_T = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByPMI_T",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {"powwowMeetingId", "type_"}, true);

		_finderPathWithoutPaginationFindByPMI_T = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByPMI_T",
			new String[] {Long.class.getName(), Integer.class.getName()},
			new String[] {"powwowMeetingId", "type_"}, true);

		_finderPathCountByPMI_T = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByPMI_T",
			new String[] {Long.class.getName(), Integer.class.getName()},
			new String[] {"powwowMeetingId", "type_"}, false);
	}

	public void destroy() {
		EntityCacheUtil.removeCache(PowwowParticipantImpl.class.getName());

		_argumentsResolverServiceRegistration.unregister();
	}

	private static final String _SQL_SELECT_POWWOWPARTICIPANT =
		"SELECT powwowParticipant FROM PowwowParticipant powwowParticipant";

	private static final String _SQL_SELECT_POWWOWPARTICIPANT_WHERE =
		"SELECT powwowParticipant FROM PowwowParticipant powwowParticipant WHERE ";

	private static final String _SQL_COUNT_POWWOWPARTICIPANT =
		"SELECT COUNT(powwowParticipant) FROM PowwowParticipant powwowParticipant";

	private static final String _SQL_COUNT_POWWOWPARTICIPANT_WHERE =
		"SELECT COUNT(powwowParticipant) FROM PowwowParticipant powwowParticipant WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS = "powwowParticipant.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No PowwowParticipant exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No PowwowParticipant exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		PowwowParticipantPersistenceImpl.class);

	private static final Set<String> _badColumnNames = SetUtil.fromArray(
		new String[] {"type"});

	@Override
	protected FinderCache getFinderCache() {
		return FinderCacheUtil.getFinderCache();
	}

	private ServiceRegistration<ArgumentsResolver>
		_argumentsResolverServiceRegistration;

	private static class PowwowParticipantModelArgumentsResolver
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

			PowwowParticipantModelImpl powwowParticipantModelImpl =
				(PowwowParticipantModelImpl)baseModel;

			long columnBitmask = powwowParticipantModelImpl.getColumnBitmask();

			if (!checkColumn || (columnBitmask == 0)) {
				return _getValue(
					powwowParticipantModelImpl, columnNames, original);
			}

			Long finderPathColumnBitmask = _finderPathColumnBitmasksCache.get(
				finderPath);

			if (finderPathColumnBitmask == null) {
				finderPathColumnBitmask = 0L;

				for (String columnName : columnNames) {
					finderPathColumnBitmask |=
						powwowParticipantModelImpl.getColumnBitmask(columnName);
				}

				_finderPathColumnBitmasksCache.put(
					finderPath, finderPathColumnBitmask);
			}

			if ((columnBitmask & finderPathColumnBitmask) != 0) {
				return _getValue(
					powwowParticipantModelImpl, columnNames, original);
			}

			return null;
		}

		@Override
		public String getClassName() {
			return PowwowParticipantImpl.class.getName();
		}

		@Override
		public String getTableName() {
			return PowwowParticipantTable.INSTANCE.getTableName();
		}

		private Object[] _getValue(
			PowwowParticipantModelImpl powwowParticipantModelImpl,
			String[] columnNames, boolean original) {

			Object[] arguments = new Object[columnNames.length];

			for (int i = 0; i < arguments.length; i++) {
				String columnName = columnNames[i];

				if (original) {
					arguments[i] =
						powwowParticipantModelImpl.getColumnOriginalValue(
							columnName);
				}
				else {
					arguments[i] = powwowParticipantModelImpl.getColumnValue(
						columnName);
				}
			}

			return arguments;
		}

		private static Map<FinderPath, Long> _finderPathColumnBitmasksCache =
			new ConcurrentHashMap<>();

	}

}