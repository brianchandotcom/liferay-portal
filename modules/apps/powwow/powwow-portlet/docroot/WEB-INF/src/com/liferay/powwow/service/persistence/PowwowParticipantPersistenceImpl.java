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

package com.liferay.powwow.service.persistence;

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
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.UnmodifiableList;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.CacheModel;
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;

import com.liferay.powwow.NoSuchParticipantException;
import com.liferay.powwow.model.PowwowParticipant;
import com.liferay.powwow.model.impl.PowwowParticipantImpl;
import com.liferay.powwow.model.impl.PowwowParticipantModelImpl;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * The persistence implementation for the powwow participant service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Shinn Lok
 * @see PowwowParticipantPersistence
 * @see PowwowParticipantUtil
 * @generated
 */
public class PowwowParticipantPersistenceImpl extends BasePersistenceImpl<PowwowParticipant>
	implements PowwowParticipantPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link PowwowParticipantUtil} to access the powwow participant persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = PowwowParticipantImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(PowwowParticipantModelImpl.ENTITY_CACHE_ENABLED,
			PowwowParticipantModelImpl.FINDER_CACHE_ENABLED,
			PowwowParticipantImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(PowwowParticipantModelImpl.ENTITY_CACHE_ENABLED,
			PowwowParticipantModelImpl.FINDER_CACHE_ENABLED,
			PowwowParticipantImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(PowwowParticipantModelImpl.ENTITY_CACHE_ENABLED,
			PowwowParticipantModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_POWWOWMEETINGID =
		new FinderPath(PowwowParticipantModelImpl.ENTITY_CACHE_ENABLED,
			PowwowParticipantModelImpl.FINDER_CACHE_ENABLED,
			PowwowParticipantImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByPowwowMeetingId",
			new String[] {
				Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_POWWOWMEETINGID =
		new FinderPath(PowwowParticipantModelImpl.ENTITY_CACHE_ENABLED,
			PowwowParticipantModelImpl.FINDER_CACHE_ENABLED,
			PowwowParticipantImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByPowwowMeetingId",
			new String[] { Long.class.getName() },
			PowwowParticipantModelImpl.POWWOWMEETINGID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_POWWOWMEETINGID = new FinderPath(PowwowParticipantModelImpl.ENTITY_CACHE_ENABLED,
			PowwowParticipantModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByPowwowMeetingId", new String[] { Long.class.getName() });

	/**
	 * Returns all the powwow participants where powwowMeetingId = &#63;.
	 *
	 * @param powwowMeetingId the powwow meeting ID
	 * @return the matching powwow participants
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<PowwowParticipant> findByPowwowMeetingId(long powwowMeetingId)
		throws SystemException {
		return findByPowwowMeetingId(powwowMeetingId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the powwow participants where powwowMeetingId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.powwow.model.impl.PowwowParticipantModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param powwowMeetingId the powwow meeting ID
	 * @param start the lower bound of the range of powwow participants
	 * @param end the upper bound of the range of powwow participants (not inclusive)
	 * @return the range of matching powwow participants
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<PowwowParticipant> findByPowwowMeetingId(long powwowMeetingId,
		int start, int end) throws SystemException {
		return findByPowwowMeetingId(powwowMeetingId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the powwow participants where powwowMeetingId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.powwow.model.impl.PowwowParticipantModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param powwowMeetingId the powwow meeting ID
	 * @param start the lower bound of the range of powwow participants
	 * @param end the upper bound of the range of powwow participants (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching powwow participants
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<PowwowParticipant> findByPowwowMeetingId(long powwowMeetingId,
		int start, int end, OrderByComparator orderByComparator)
		throws SystemException {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_POWWOWMEETINGID;
			finderArgs = new Object[] { powwowMeetingId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_POWWOWMEETINGID;
			finderArgs = new Object[] {
					powwowMeetingId,
					
					start, end, orderByComparator
				};
		}

		List<PowwowParticipant> list = (List<PowwowParticipant>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (PowwowParticipant powwowParticipant : list) {
				if ((powwowMeetingId != powwowParticipant.getPowwowMeetingId())) {
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

			query.append(_SQL_SELECT_POWWOWPARTICIPANT_WHERE);

			query.append(_FINDER_COLUMN_POWWOWMEETINGID_POWWOWMEETINGID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(PowwowParticipantModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(powwowMeetingId);

				if (!pagination) {
					list = (List<PowwowParticipant>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = new UnmodifiableList<PowwowParticipant>(list);
				}
				else {
					list = (List<PowwowParticipant>)QueryUtil.list(q,
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
	 * Returns the first powwow participant in the ordered set where powwowMeetingId = &#63;.
	 *
	 * @param powwowMeetingId the powwow meeting ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching powwow participant
	 * @throws com.liferay.powwow.NoSuchParticipantException if a matching powwow participant could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public PowwowParticipant findByPowwowMeetingId_First(long powwowMeetingId,
		OrderByComparator orderByComparator)
		throws NoSuchParticipantException, SystemException {
		PowwowParticipant powwowParticipant = fetchByPowwowMeetingId_First(powwowMeetingId,
				orderByComparator);

		if (powwowParticipant != null) {
			return powwowParticipant;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("powwowMeetingId=");
		msg.append(powwowMeetingId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchParticipantException(msg.toString());
	}

	/**
	 * Returns the first powwow participant in the ordered set where powwowMeetingId = &#63;.
	 *
	 * @param powwowMeetingId the powwow meeting ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching powwow participant, or <code>null</code> if a matching powwow participant could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public PowwowParticipant fetchByPowwowMeetingId_First(
		long powwowMeetingId, OrderByComparator orderByComparator)
		throws SystemException {
		List<PowwowParticipant> list = findByPowwowMeetingId(powwowMeetingId,
				0, 1, orderByComparator);

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
	 * @throws com.liferay.powwow.NoSuchParticipantException if a matching powwow participant could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public PowwowParticipant findByPowwowMeetingId_Last(long powwowMeetingId,
		OrderByComparator orderByComparator)
		throws NoSuchParticipantException, SystemException {
		PowwowParticipant powwowParticipant = fetchByPowwowMeetingId_Last(powwowMeetingId,
				orderByComparator);

		if (powwowParticipant != null) {
			return powwowParticipant;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("powwowMeetingId=");
		msg.append(powwowMeetingId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchParticipantException(msg.toString());
	}

	/**
	 * Returns the last powwow participant in the ordered set where powwowMeetingId = &#63;.
	 *
	 * @param powwowMeetingId the powwow meeting ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching powwow participant, or <code>null</code> if a matching powwow participant could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public PowwowParticipant fetchByPowwowMeetingId_Last(long powwowMeetingId,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByPowwowMeetingId(powwowMeetingId);

		if (count == 0) {
			return null;
		}

		List<PowwowParticipant> list = findByPowwowMeetingId(powwowMeetingId,
				count - 1, count, orderByComparator);

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
	 * @throws com.liferay.powwow.NoSuchParticipantException if a powwow participant with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public PowwowParticipant[] findByPowwowMeetingId_PrevAndNext(
		long powwowParticipantId, long powwowMeetingId,
		OrderByComparator orderByComparator)
		throws NoSuchParticipantException, SystemException {
		PowwowParticipant powwowParticipant = findByPrimaryKey(powwowParticipantId);

		Session session = null;

		try {
			session = openSession();

			PowwowParticipant[] array = new PowwowParticipantImpl[3];

			array[0] = getByPowwowMeetingId_PrevAndNext(session,
					powwowParticipant, powwowMeetingId, orderByComparator, true);

			array[1] = powwowParticipant;

			array[2] = getByPowwowMeetingId_PrevAndNext(session,
					powwowParticipant, powwowMeetingId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected PowwowParticipant getByPowwowMeetingId_PrevAndNext(
		Session session, PowwowParticipant powwowParticipant,
		long powwowMeetingId, OrderByComparator orderByComparator,
		boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_POWWOWPARTICIPANT_WHERE);

		query.append(_FINDER_COLUMN_POWWOWMEETINGID_POWWOWMEETINGID_2);

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
			query.append(PowwowParticipantModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(powwowMeetingId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(powwowParticipant);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<PowwowParticipant> list = q.list();

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
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public void removeByPowwowMeetingId(long powwowMeetingId)
		throws SystemException {
		for (PowwowParticipant powwowParticipant : findByPowwowMeetingId(
				powwowMeetingId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(powwowParticipant);
		}
	}

	/**
	 * Returns the number of powwow participants where powwowMeetingId = &#63;.
	 *
	 * @param powwowMeetingId the powwow meeting ID
	 * @return the number of matching powwow participants
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public int countByPowwowMeetingId(long powwowMeetingId)
		throws SystemException {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_POWWOWMEETINGID;

		Object[] finderArgs = new Object[] { powwowMeetingId };

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs,
				this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_POWWOWPARTICIPANT_WHERE);

			query.append(_FINDER_COLUMN_POWWOWMEETINGID_POWWOWMEETINGID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(powwowMeetingId);

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

	private static final String _FINDER_COLUMN_POWWOWMEETINGID_POWWOWMEETINGID_2 =
		"powwowParticipant.powwowMeetingId = ?";
	public static final FinderPath FINDER_PATH_FETCH_BY_PMI_PUI = new FinderPath(PowwowParticipantModelImpl.ENTITY_CACHE_ENABLED,
			PowwowParticipantModelImpl.FINDER_CACHE_ENABLED,
			PowwowParticipantImpl.class, FINDER_CLASS_NAME_ENTITY,
			"fetchByPMI_PUI",
			new String[] { Long.class.getName(), Long.class.getName() },
			PowwowParticipantModelImpl.POWWOWMEETINGID_COLUMN_BITMASK |
			PowwowParticipantModelImpl.PARTICIPANTUSERID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_PMI_PUI = new FinderPath(PowwowParticipantModelImpl.ENTITY_CACHE_ENABLED,
			PowwowParticipantModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByPMI_PUI",
			new String[] { Long.class.getName(), Long.class.getName() });

	/**
	 * Returns the powwow participant where powwowMeetingId = &#63; and participantUserId = &#63; or throws a {@link com.liferay.powwow.NoSuchParticipantException} if it could not be found.
	 *
	 * @param powwowMeetingId the powwow meeting ID
	 * @param participantUserId the participant user ID
	 * @return the matching powwow participant
	 * @throws com.liferay.powwow.NoSuchParticipantException if a matching powwow participant could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public PowwowParticipant findByPMI_PUI(long powwowMeetingId,
		long participantUserId)
		throws NoSuchParticipantException, SystemException {
		PowwowParticipant powwowParticipant = fetchByPMI_PUI(powwowMeetingId,
				participantUserId);

		if (powwowParticipant == null) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("powwowMeetingId=");
			msg.append(powwowMeetingId);

			msg.append(", participantUserId=");
			msg.append(participantUserId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchParticipantException(msg.toString());
		}

		return powwowParticipant;
	}

	/**
	 * Returns the powwow participant where powwowMeetingId = &#63; and participantUserId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param powwowMeetingId the powwow meeting ID
	 * @param participantUserId the participant user ID
	 * @return the matching powwow participant, or <code>null</code> if a matching powwow participant could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public PowwowParticipant fetchByPMI_PUI(long powwowMeetingId,
		long participantUserId) throws SystemException {
		return fetchByPMI_PUI(powwowMeetingId, participantUserId, true);
	}

	/**
	 * Returns the powwow participant where powwowMeetingId = &#63; and participantUserId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param powwowMeetingId the powwow meeting ID
	 * @param participantUserId the participant user ID
	 * @param retrieveFromCache whether to use the finder cache
	 * @return the matching powwow participant, or <code>null</code> if a matching powwow participant could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public PowwowParticipant fetchByPMI_PUI(long powwowMeetingId,
		long participantUserId, boolean retrieveFromCache)
		throws SystemException {
		Object[] finderArgs = new Object[] { powwowMeetingId, participantUserId };

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_PMI_PUI,
					finderArgs, this);
		}

		if (result instanceof PowwowParticipant) {
			PowwowParticipant powwowParticipant = (PowwowParticipant)result;

			if ((powwowMeetingId != powwowParticipant.getPowwowMeetingId()) ||
					(participantUserId != powwowParticipant.getParticipantUserId())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_SELECT_POWWOWPARTICIPANT_WHERE);

			query.append(_FINDER_COLUMN_PMI_PUI_POWWOWMEETINGID_2);

			query.append(_FINDER_COLUMN_PMI_PUI_PARTICIPANTUSERID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(powwowMeetingId);

				qPos.add(participantUserId);

				List<PowwowParticipant> list = q.list();

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_PMI_PUI,
						finderArgs, list);
				}
				else {
					if ((list.size() > 1) && _log.isWarnEnabled()) {
						_log.warn(
							"PowwowParticipantPersistenceImpl.fetchByPMI_PUI(long, long, boolean) with parameters (" +
							StringUtil.merge(finderArgs) +
							") yields a result set with more than 1 result. This violates the logical unique restriction. There is no order guarantee on which result is returned by this finder.");
					}

					PowwowParticipant powwowParticipant = list.get(0);

					result = powwowParticipant;

					cacheResult(powwowParticipant);

					if ((powwowParticipant.getPowwowMeetingId() != powwowMeetingId) ||
							(powwowParticipant.getParticipantUserId() != participantUserId)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_PMI_PUI,
							finderArgs, powwowParticipant);
					}
				}
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_PMI_PUI,
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
			return (PowwowParticipant)result;
		}
	}

	/**
	 * Removes the powwow participant where powwowMeetingId = &#63; and participantUserId = &#63; from the database.
	 *
	 * @param powwowMeetingId the powwow meeting ID
	 * @param participantUserId the participant user ID
	 * @return the powwow participant that was removed
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public PowwowParticipant removeByPMI_PUI(long powwowMeetingId,
		long participantUserId)
		throws NoSuchParticipantException, SystemException {
		PowwowParticipant powwowParticipant = findByPMI_PUI(powwowMeetingId,
				participantUserId);

		return remove(powwowParticipant);
	}

	/**
	 * Returns the number of powwow participants where powwowMeetingId = &#63; and participantUserId = &#63;.
	 *
	 * @param powwowMeetingId the powwow meeting ID
	 * @param participantUserId the participant user ID
	 * @return the number of matching powwow participants
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public int countByPMI_PUI(long powwowMeetingId, long participantUserId)
		throws SystemException {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_PMI_PUI;

		Object[] finderArgs = new Object[] { powwowMeetingId, participantUserId };

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs,
				this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_POWWOWPARTICIPANT_WHERE);

			query.append(_FINDER_COLUMN_PMI_PUI_POWWOWMEETINGID_2);

			query.append(_FINDER_COLUMN_PMI_PUI_PARTICIPANTUSERID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(powwowMeetingId);

				qPos.add(participantUserId);

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

	private static final String _FINDER_COLUMN_PMI_PUI_POWWOWMEETINGID_2 = "powwowParticipant.powwowMeetingId = ? AND ";
	private static final String _FINDER_COLUMN_PMI_PUI_PARTICIPANTUSERID_2 = "powwowParticipant.participantUserId = ?";
	public static final FinderPath FINDER_PATH_FETCH_BY_PMI_EA = new FinderPath(PowwowParticipantModelImpl.ENTITY_CACHE_ENABLED,
			PowwowParticipantModelImpl.FINDER_CACHE_ENABLED,
			PowwowParticipantImpl.class, FINDER_CLASS_NAME_ENTITY,
			"fetchByPMI_EA",
			new String[] { Long.class.getName(), String.class.getName() },
			PowwowParticipantModelImpl.POWWOWMEETINGID_COLUMN_BITMASK |
			PowwowParticipantModelImpl.EMAILADDRESS_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_PMI_EA = new FinderPath(PowwowParticipantModelImpl.ENTITY_CACHE_ENABLED,
			PowwowParticipantModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByPMI_EA",
			new String[] { Long.class.getName(), String.class.getName() });

	/**
	 * Returns the powwow participant where powwowMeetingId = &#63; and emailAddress = &#63; or throws a {@link com.liferay.powwow.NoSuchParticipantException} if it could not be found.
	 *
	 * @param powwowMeetingId the powwow meeting ID
	 * @param emailAddress the email address
	 * @return the matching powwow participant
	 * @throws com.liferay.powwow.NoSuchParticipantException if a matching powwow participant could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public PowwowParticipant findByPMI_EA(long powwowMeetingId,
		String emailAddress) throws NoSuchParticipantException, SystemException {
		PowwowParticipant powwowParticipant = fetchByPMI_EA(powwowMeetingId,
				emailAddress);

		if (powwowParticipant == null) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("powwowMeetingId=");
			msg.append(powwowMeetingId);

			msg.append(", emailAddress=");
			msg.append(emailAddress);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchParticipantException(msg.toString());
		}

		return powwowParticipant;
	}

	/**
	 * Returns the powwow participant where powwowMeetingId = &#63; and emailAddress = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param powwowMeetingId the powwow meeting ID
	 * @param emailAddress the email address
	 * @return the matching powwow participant, or <code>null</code> if a matching powwow participant could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public PowwowParticipant fetchByPMI_EA(long powwowMeetingId,
		String emailAddress) throws SystemException {
		return fetchByPMI_EA(powwowMeetingId, emailAddress, true);
	}

	/**
	 * Returns the powwow participant where powwowMeetingId = &#63; and emailAddress = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param powwowMeetingId the powwow meeting ID
	 * @param emailAddress the email address
	 * @param retrieveFromCache whether to use the finder cache
	 * @return the matching powwow participant, or <code>null</code> if a matching powwow participant could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public PowwowParticipant fetchByPMI_EA(long powwowMeetingId,
		String emailAddress, boolean retrieveFromCache)
		throws SystemException {
		Object[] finderArgs = new Object[] { powwowMeetingId, emailAddress };

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_PMI_EA,
					finderArgs, this);
		}

		if (result instanceof PowwowParticipant) {
			PowwowParticipant powwowParticipant = (PowwowParticipant)result;

			if ((powwowMeetingId != powwowParticipant.getPowwowMeetingId()) ||
					!Validator.equals(emailAddress,
						powwowParticipant.getEmailAddress())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_SELECT_POWWOWPARTICIPANT_WHERE);

			query.append(_FINDER_COLUMN_PMI_EA_POWWOWMEETINGID_2);

			boolean bindEmailAddress = false;

			if (emailAddress == null) {
				query.append(_FINDER_COLUMN_PMI_EA_EMAILADDRESS_1);
			}
			else if (emailAddress.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_PMI_EA_EMAILADDRESS_3);
			}
			else {
				bindEmailAddress = true;

				query.append(_FINDER_COLUMN_PMI_EA_EMAILADDRESS_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(powwowMeetingId);

				if (bindEmailAddress) {
					qPos.add(emailAddress);
				}

				List<PowwowParticipant> list = q.list();

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_PMI_EA,
						finderArgs, list);
				}
				else {
					PowwowParticipant powwowParticipant = list.get(0);

					result = powwowParticipant;

					cacheResult(powwowParticipant);

					if ((powwowParticipant.getPowwowMeetingId() != powwowMeetingId) ||
							(powwowParticipant.getEmailAddress() == null) ||
							!powwowParticipant.getEmailAddress()
												  .equals(emailAddress)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_PMI_EA,
							finderArgs, powwowParticipant);
					}
				}
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_PMI_EA,
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
			return (PowwowParticipant)result;
		}
	}

	/**
	 * Removes the powwow participant where powwowMeetingId = &#63; and emailAddress = &#63; from the database.
	 *
	 * @param powwowMeetingId the powwow meeting ID
	 * @param emailAddress the email address
	 * @return the powwow participant that was removed
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public PowwowParticipant removeByPMI_EA(long powwowMeetingId,
		String emailAddress) throws NoSuchParticipantException, SystemException {
		PowwowParticipant powwowParticipant = findByPMI_EA(powwowMeetingId,
				emailAddress);

		return remove(powwowParticipant);
	}

	/**
	 * Returns the number of powwow participants where powwowMeetingId = &#63; and emailAddress = &#63;.
	 *
	 * @param powwowMeetingId the powwow meeting ID
	 * @param emailAddress the email address
	 * @return the number of matching powwow participants
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public int countByPMI_EA(long powwowMeetingId, String emailAddress)
		throws SystemException {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_PMI_EA;

		Object[] finderArgs = new Object[] { powwowMeetingId, emailAddress };

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs,
				this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_POWWOWPARTICIPANT_WHERE);

			query.append(_FINDER_COLUMN_PMI_EA_POWWOWMEETINGID_2);

			boolean bindEmailAddress = false;

			if (emailAddress == null) {
				query.append(_FINDER_COLUMN_PMI_EA_EMAILADDRESS_1);
			}
			else if (emailAddress.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_PMI_EA_EMAILADDRESS_3);
			}
			else {
				bindEmailAddress = true;

				query.append(_FINDER_COLUMN_PMI_EA_EMAILADDRESS_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(powwowMeetingId);

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

	private static final String _FINDER_COLUMN_PMI_EA_POWWOWMEETINGID_2 = "powwowParticipant.powwowMeetingId = ? AND ";
	private static final String _FINDER_COLUMN_PMI_EA_EMAILADDRESS_1 = "powwowParticipant.emailAddress IS NULL";
	private static final String _FINDER_COLUMN_PMI_EA_EMAILADDRESS_2 = "powwowParticipant.emailAddress = ?";
	private static final String _FINDER_COLUMN_PMI_EA_EMAILADDRESS_3 = "(powwowParticipant.emailAddress IS NULL OR powwowParticipant.emailAddress = '')";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_PMI_T = new FinderPath(PowwowParticipantModelImpl.ENTITY_CACHE_ENABLED,
			PowwowParticipantModelImpl.FINDER_CACHE_ENABLED,
			PowwowParticipantImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByPMI_T",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_PMI_T = new FinderPath(PowwowParticipantModelImpl.ENTITY_CACHE_ENABLED,
			PowwowParticipantModelImpl.FINDER_CACHE_ENABLED,
			PowwowParticipantImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByPMI_T",
			new String[] { Long.class.getName(), Integer.class.getName() },
			PowwowParticipantModelImpl.POWWOWMEETINGID_COLUMN_BITMASK |
			PowwowParticipantModelImpl.TYPE_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_PMI_T = new FinderPath(PowwowParticipantModelImpl.ENTITY_CACHE_ENABLED,
			PowwowParticipantModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByPMI_T",
			new String[] { Long.class.getName(), Integer.class.getName() });

	/**
	 * Returns all the powwow participants where powwowMeetingId = &#63; and type = &#63;.
	 *
	 * @param powwowMeetingId the powwow meeting ID
	 * @param type the type
	 * @return the matching powwow participants
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<PowwowParticipant> findByPMI_T(long powwowMeetingId, int type)
		throws SystemException {
		return findByPMI_T(powwowMeetingId, type, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the powwow participants where powwowMeetingId = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.powwow.model.impl.PowwowParticipantModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param powwowMeetingId the powwow meeting ID
	 * @param type the type
	 * @param start the lower bound of the range of powwow participants
	 * @param end the upper bound of the range of powwow participants (not inclusive)
	 * @return the range of matching powwow participants
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<PowwowParticipant> findByPMI_T(long powwowMeetingId, int type,
		int start, int end) throws SystemException {
		return findByPMI_T(powwowMeetingId, type, start, end, null);
	}

	/**
	 * Returns an ordered range of all the powwow participants where powwowMeetingId = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.powwow.model.impl.PowwowParticipantModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param powwowMeetingId the powwow meeting ID
	 * @param type the type
	 * @param start the lower bound of the range of powwow participants
	 * @param end the upper bound of the range of powwow participants (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching powwow participants
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<PowwowParticipant> findByPMI_T(long powwowMeetingId, int type,
		int start, int end, OrderByComparator orderByComparator)
		throws SystemException {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_PMI_T;
			finderArgs = new Object[] { powwowMeetingId, type };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_PMI_T;
			finderArgs = new Object[] {
					powwowMeetingId, type,
					
					start, end, orderByComparator
				};
		}

		List<PowwowParticipant> list = (List<PowwowParticipant>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (PowwowParticipant powwowParticipant : list) {
				if ((powwowMeetingId != powwowParticipant.getPowwowMeetingId()) ||
						(type != powwowParticipant.getType())) {
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

			query.append(_SQL_SELECT_POWWOWPARTICIPANT_WHERE);

			query.append(_FINDER_COLUMN_PMI_T_POWWOWMEETINGID_2);

			query.append(_FINDER_COLUMN_PMI_T_TYPE_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(PowwowParticipantModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(powwowMeetingId);

				qPos.add(type);

				if (!pagination) {
					list = (List<PowwowParticipant>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = new UnmodifiableList<PowwowParticipant>(list);
				}
				else {
					list = (List<PowwowParticipant>)QueryUtil.list(q,
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
	 * Returns the first powwow participant in the ordered set where powwowMeetingId = &#63; and type = &#63;.
	 *
	 * @param powwowMeetingId the powwow meeting ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching powwow participant
	 * @throws com.liferay.powwow.NoSuchParticipantException if a matching powwow participant could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public PowwowParticipant findByPMI_T_First(long powwowMeetingId, int type,
		OrderByComparator orderByComparator)
		throws NoSuchParticipantException, SystemException {
		PowwowParticipant powwowParticipant = fetchByPMI_T_First(powwowMeetingId,
				type, orderByComparator);

		if (powwowParticipant != null) {
			return powwowParticipant;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("powwowMeetingId=");
		msg.append(powwowMeetingId);

		msg.append(", type=");
		msg.append(type);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchParticipantException(msg.toString());
	}

	/**
	 * Returns the first powwow participant in the ordered set where powwowMeetingId = &#63; and type = &#63;.
	 *
	 * @param powwowMeetingId the powwow meeting ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching powwow participant, or <code>null</code> if a matching powwow participant could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public PowwowParticipant fetchByPMI_T_First(long powwowMeetingId, int type,
		OrderByComparator orderByComparator) throws SystemException {
		List<PowwowParticipant> list = findByPMI_T(powwowMeetingId, type, 0, 1,
				orderByComparator);

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
	 * @throws com.liferay.powwow.NoSuchParticipantException if a matching powwow participant could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public PowwowParticipant findByPMI_T_Last(long powwowMeetingId, int type,
		OrderByComparator orderByComparator)
		throws NoSuchParticipantException, SystemException {
		PowwowParticipant powwowParticipant = fetchByPMI_T_Last(powwowMeetingId,
				type, orderByComparator);

		if (powwowParticipant != null) {
			return powwowParticipant;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("powwowMeetingId=");
		msg.append(powwowMeetingId);

		msg.append(", type=");
		msg.append(type);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchParticipantException(msg.toString());
	}

	/**
	 * Returns the last powwow participant in the ordered set where powwowMeetingId = &#63; and type = &#63;.
	 *
	 * @param powwowMeetingId the powwow meeting ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching powwow participant, or <code>null</code> if a matching powwow participant could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public PowwowParticipant fetchByPMI_T_Last(long powwowMeetingId, int type,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByPMI_T(powwowMeetingId, type);

		if (count == 0) {
			return null;
		}

		List<PowwowParticipant> list = findByPMI_T(powwowMeetingId, type,
				count - 1, count, orderByComparator);

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
	 * @throws com.liferay.powwow.NoSuchParticipantException if a powwow participant with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public PowwowParticipant[] findByPMI_T_PrevAndNext(
		long powwowParticipantId, long powwowMeetingId, int type,
		OrderByComparator orderByComparator)
		throws NoSuchParticipantException, SystemException {
		PowwowParticipant powwowParticipant = findByPrimaryKey(powwowParticipantId);

		Session session = null;

		try {
			session = openSession();

			PowwowParticipant[] array = new PowwowParticipantImpl[3];

			array[0] = getByPMI_T_PrevAndNext(session, powwowParticipant,
					powwowMeetingId, type, orderByComparator, true);

			array[1] = powwowParticipant;

			array[2] = getByPMI_T_PrevAndNext(session, powwowParticipant,
					powwowMeetingId, type, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected PowwowParticipant getByPMI_T_PrevAndNext(Session session,
		PowwowParticipant powwowParticipant, long powwowMeetingId, int type,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_POWWOWPARTICIPANT_WHERE);

		query.append(_FINDER_COLUMN_PMI_T_POWWOWMEETINGID_2);

		query.append(_FINDER_COLUMN_PMI_T_TYPE_2);

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
			query.append(PowwowParticipantModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(powwowMeetingId);

		qPos.add(type);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(powwowParticipant);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<PowwowParticipant> list = q.list();

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
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public void removeByPMI_T(long powwowMeetingId, int type)
		throws SystemException {
		for (PowwowParticipant powwowParticipant : findByPMI_T(
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
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public int countByPMI_T(long powwowMeetingId, int type)
		throws SystemException {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_PMI_T;

		Object[] finderArgs = new Object[] { powwowMeetingId, type };

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs,
				this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_POWWOWPARTICIPANT_WHERE);

			query.append(_FINDER_COLUMN_PMI_T_POWWOWMEETINGID_2);

			query.append(_FINDER_COLUMN_PMI_T_TYPE_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(powwowMeetingId);

				qPos.add(type);

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

	private static final String _FINDER_COLUMN_PMI_T_POWWOWMEETINGID_2 = "powwowParticipant.powwowMeetingId = ? AND ";
	private static final String _FINDER_COLUMN_PMI_T_TYPE_2 = "powwowParticipant.type = ?";

	public PowwowParticipantPersistenceImpl() {
		setModelClass(PowwowParticipant.class);
	}

	/**
	 * Caches the powwow participant in the entity cache if it is enabled.
	 *
	 * @param powwowParticipant the powwow participant
	 */
	@Override
	public void cacheResult(PowwowParticipant powwowParticipant) {
		EntityCacheUtil.putResult(PowwowParticipantModelImpl.ENTITY_CACHE_ENABLED,
			PowwowParticipantImpl.class, powwowParticipant.getPrimaryKey(),
			powwowParticipant);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_PMI_PUI,
			new Object[] {
				powwowParticipant.getPowwowMeetingId(),
				powwowParticipant.getParticipantUserId()
			}, powwowParticipant);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_PMI_EA,
			new Object[] {
				powwowParticipant.getPowwowMeetingId(),
				powwowParticipant.getEmailAddress()
			}, powwowParticipant);

		powwowParticipant.resetOriginalValues();
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
						PowwowParticipantModelImpl.ENTITY_CACHE_ENABLED,
						PowwowParticipantImpl.class,
						powwowParticipant.getPrimaryKey()) == null) {
				cacheResult(powwowParticipant);
			}
			else {
				powwowParticipant.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all powwow participants.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		if (_HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			CacheRegistryUtil.clear(PowwowParticipantImpl.class.getName());
		}

		EntityCacheUtil.clearCache(PowwowParticipantImpl.class.getName());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the powwow participant.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(PowwowParticipant powwowParticipant) {
		EntityCacheUtil.removeResult(PowwowParticipantModelImpl.ENTITY_CACHE_ENABLED,
			PowwowParticipantImpl.class, powwowParticipant.getPrimaryKey());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache(powwowParticipant);
	}

	@Override
	public void clearCache(List<PowwowParticipant> powwowParticipants) {
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (PowwowParticipant powwowParticipant : powwowParticipants) {
			EntityCacheUtil.removeResult(PowwowParticipantModelImpl.ENTITY_CACHE_ENABLED,
				PowwowParticipantImpl.class, powwowParticipant.getPrimaryKey());

			clearUniqueFindersCache(powwowParticipant);
		}
	}

	protected void cacheUniqueFindersCache(PowwowParticipant powwowParticipant) {
		if (powwowParticipant.isNew()) {
			Object[] args = new Object[] {
					powwowParticipant.getPowwowMeetingId(),
					powwowParticipant.getParticipantUserId()
				};

			FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_PMI_PUI, args,
				Long.valueOf(1));
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_PMI_PUI, args,
				powwowParticipant);

			args = new Object[] {
					powwowParticipant.getPowwowMeetingId(),
					powwowParticipant.getEmailAddress()
				};

			FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_PMI_EA, args,
				Long.valueOf(1));
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_PMI_EA, args,
				powwowParticipant);
		}
		else {
			PowwowParticipantModelImpl powwowParticipantModelImpl = (PowwowParticipantModelImpl)powwowParticipant;

			if ((powwowParticipantModelImpl.getColumnBitmask() &
					FINDER_PATH_FETCH_BY_PMI_PUI.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						powwowParticipant.getPowwowMeetingId(),
						powwowParticipant.getParticipantUserId()
					};

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_PMI_PUI, args,
					Long.valueOf(1));
				FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_PMI_PUI, args,
					powwowParticipant);
			}

			if ((powwowParticipantModelImpl.getColumnBitmask() &
					FINDER_PATH_FETCH_BY_PMI_EA.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						powwowParticipant.getPowwowMeetingId(),
						powwowParticipant.getEmailAddress()
					};

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_PMI_EA, args,
					Long.valueOf(1));
				FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_PMI_EA, args,
					powwowParticipant);
			}
		}
	}

	protected void clearUniqueFindersCache(PowwowParticipant powwowParticipant) {
		PowwowParticipantModelImpl powwowParticipantModelImpl = (PowwowParticipantModelImpl)powwowParticipant;

		Object[] args = new Object[] {
				powwowParticipant.getPowwowMeetingId(),
				powwowParticipant.getParticipantUserId()
			};

		FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_PMI_PUI, args);
		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_PMI_PUI, args);

		if ((powwowParticipantModelImpl.getColumnBitmask() &
				FINDER_PATH_FETCH_BY_PMI_PUI.getColumnBitmask()) != 0) {
			args = new Object[] {
					powwowParticipantModelImpl.getOriginalPowwowMeetingId(),
					powwowParticipantModelImpl.getOriginalParticipantUserId()
				};

			FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_PMI_PUI, args);
			FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_PMI_PUI, args);
		}

		args = new Object[] {
				powwowParticipant.getPowwowMeetingId(),
				powwowParticipant.getEmailAddress()
			};

		FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_PMI_EA, args);
		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_PMI_EA, args);

		if ((powwowParticipantModelImpl.getColumnBitmask() &
				FINDER_PATH_FETCH_BY_PMI_EA.getColumnBitmask()) != 0) {
			args = new Object[] {
					powwowParticipantModelImpl.getOriginalPowwowMeetingId(),
					powwowParticipantModelImpl.getOriginalEmailAddress()
				};

			FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_PMI_EA, args);
			FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_PMI_EA, args);
		}
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

		return powwowParticipant;
	}

	/**
	 * Removes the powwow participant with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param powwowParticipantId the primary key of the powwow participant
	 * @return the powwow participant that was removed
	 * @throws com.liferay.powwow.NoSuchParticipantException if a powwow participant with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public PowwowParticipant remove(long powwowParticipantId)
		throws NoSuchParticipantException, SystemException {
		return remove((Serializable)powwowParticipantId);
	}

	/**
	 * Removes the powwow participant with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the powwow participant
	 * @return the powwow participant that was removed
	 * @throws com.liferay.powwow.NoSuchParticipantException if a powwow participant with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public PowwowParticipant remove(Serializable primaryKey)
		throws NoSuchParticipantException, SystemException {
		Session session = null;

		try {
			session = openSession();

			PowwowParticipant powwowParticipant = (PowwowParticipant)session.get(PowwowParticipantImpl.class,
					primaryKey);

			if (powwowParticipant == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchParticipantException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(powwowParticipant);
		}
		catch (NoSuchParticipantException nsee) {
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
	protected PowwowParticipant removeImpl(PowwowParticipant powwowParticipant)
		throws SystemException {
		powwowParticipant = toUnwrappedModel(powwowParticipant);

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(powwowParticipant)) {
				powwowParticipant = (PowwowParticipant)session.get(PowwowParticipantImpl.class,
						powwowParticipant.getPrimaryKeyObj());
			}

			if (powwowParticipant != null) {
				session.delete(powwowParticipant);
			}
		}
		catch (Exception e) {
			throw processException(e);
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
	public PowwowParticipant updateImpl(
		com.liferay.powwow.model.PowwowParticipant powwowParticipant)
		throws SystemException {
		powwowParticipant = toUnwrappedModel(powwowParticipant);

		boolean isNew = powwowParticipant.isNew();

		PowwowParticipantModelImpl powwowParticipantModelImpl = (PowwowParticipantModelImpl)powwowParticipant;

		Session session = null;

		try {
			session = openSession();

			if (powwowParticipant.isNew()) {
				session.save(powwowParticipant);

				powwowParticipant.setNew(false);
			}
			else {
				session.merge(powwowParticipant);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (isNew || !PowwowParticipantModelImpl.COLUMN_BITMASK_ENABLED) {
			FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}

		else {
			if ((powwowParticipantModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_POWWOWMEETINGID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						powwowParticipantModelImpl.getOriginalPowwowMeetingId()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_POWWOWMEETINGID,
					args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_POWWOWMEETINGID,
					args);

				args = new Object[] {
						powwowParticipantModelImpl.getPowwowMeetingId()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_POWWOWMEETINGID,
					args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_POWWOWMEETINGID,
					args);
			}

			if ((powwowParticipantModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_PMI_T.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						powwowParticipantModelImpl.getOriginalPowwowMeetingId(),
						powwowParticipantModelImpl.getOriginalType()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_PMI_T, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_PMI_T,
					args);

				args = new Object[] {
						powwowParticipantModelImpl.getPowwowMeetingId(),
						powwowParticipantModelImpl.getType()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_PMI_T, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_PMI_T,
					args);
			}
		}

		EntityCacheUtil.putResult(PowwowParticipantModelImpl.ENTITY_CACHE_ENABLED,
			PowwowParticipantImpl.class, powwowParticipant.getPrimaryKey(),
			powwowParticipant);

		clearUniqueFindersCache(powwowParticipant);
		cacheUniqueFindersCache(powwowParticipant);

		return powwowParticipant;
	}

	protected PowwowParticipant toUnwrappedModel(
		PowwowParticipant powwowParticipant) {
		if (powwowParticipant instanceof PowwowParticipantImpl) {
			return powwowParticipant;
		}

		PowwowParticipantImpl powwowParticipantImpl = new PowwowParticipantImpl();

		powwowParticipantImpl.setNew(powwowParticipant.isNew());
		powwowParticipantImpl.setPrimaryKey(powwowParticipant.getPrimaryKey());

		powwowParticipantImpl.setPowwowParticipantId(powwowParticipant.getPowwowParticipantId());
		powwowParticipantImpl.setGroupId(powwowParticipant.getGroupId());
		powwowParticipantImpl.setCompanyId(powwowParticipant.getCompanyId());
		powwowParticipantImpl.setUserId(powwowParticipant.getUserId());
		powwowParticipantImpl.setUserName(powwowParticipant.getUserName());
		powwowParticipantImpl.setCreateDate(powwowParticipant.getCreateDate());
		powwowParticipantImpl.setModifiedDate(powwowParticipant.getModifiedDate());
		powwowParticipantImpl.setPowwowMeetingId(powwowParticipant.getPowwowMeetingId());
		powwowParticipantImpl.setName(powwowParticipant.getName());
		powwowParticipantImpl.setParticipantUserId(powwowParticipant.getParticipantUserId());
		powwowParticipantImpl.setEmailAddress(powwowParticipant.getEmailAddress());
		powwowParticipantImpl.setType(powwowParticipant.getType());
		powwowParticipantImpl.setStatus(powwowParticipant.getStatus());

		return powwowParticipantImpl;
	}

	/**
	 * Returns the powwow participant with the primary key or throws a {@link com.liferay.portal.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the powwow participant
	 * @return the powwow participant
	 * @throws com.liferay.powwow.NoSuchParticipantException if a powwow participant with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public PowwowParticipant findByPrimaryKey(Serializable primaryKey)
		throws NoSuchParticipantException, SystemException {
		PowwowParticipant powwowParticipant = fetchByPrimaryKey(primaryKey);

		if (powwowParticipant == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchParticipantException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				primaryKey);
		}

		return powwowParticipant;
	}

	/**
	 * Returns the powwow participant with the primary key or throws a {@link com.liferay.powwow.NoSuchParticipantException} if it could not be found.
	 *
	 * @param powwowParticipantId the primary key of the powwow participant
	 * @return the powwow participant
	 * @throws com.liferay.powwow.NoSuchParticipantException if a powwow participant with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public PowwowParticipant findByPrimaryKey(long powwowParticipantId)
		throws NoSuchParticipantException, SystemException {
		return findByPrimaryKey((Serializable)powwowParticipantId);
	}

	/**
	 * Returns the powwow participant with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the powwow participant
	 * @return the powwow participant, or <code>null</code> if a powwow participant with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public PowwowParticipant fetchByPrimaryKey(Serializable primaryKey)
		throws SystemException {
		PowwowParticipant powwowParticipant = (PowwowParticipant)EntityCacheUtil.getResult(PowwowParticipantModelImpl.ENTITY_CACHE_ENABLED,
				PowwowParticipantImpl.class, primaryKey);

		if (powwowParticipant == _nullPowwowParticipant) {
			return null;
		}

		if (powwowParticipant == null) {
			Session session = null;

			try {
				session = openSession();

				powwowParticipant = (PowwowParticipant)session.get(PowwowParticipantImpl.class,
						primaryKey);

				if (powwowParticipant != null) {
					cacheResult(powwowParticipant);
				}
				else {
					EntityCacheUtil.putResult(PowwowParticipantModelImpl.ENTITY_CACHE_ENABLED,
						PowwowParticipantImpl.class, primaryKey,
						_nullPowwowParticipant);
				}
			}
			catch (Exception e) {
				EntityCacheUtil.removeResult(PowwowParticipantModelImpl.ENTITY_CACHE_ENABLED,
					PowwowParticipantImpl.class, primaryKey);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return powwowParticipant;
	}

	/**
	 * Returns the powwow participant with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param powwowParticipantId the primary key of the powwow participant
	 * @return the powwow participant, or <code>null</code> if a powwow participant with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public PowwowParticipant fetchByPrimaryKey(long powwowParticipantId)
		throws SystemException {
		return fetchByPrimaryKey((Serializable)powwowParticipantId);
	}

	/**
	 * Returns all the powwow participants.
	 *
	 * @return the powwow participants
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<PowwowParticipant> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the powwow participants.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.powwow.model.impl.PowwowParticipantModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of powwow participants
	 * @param end the upper bound of the range of powwow participants (not inclusive)
	 * @return the range of powwow participants
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<PowwowParticipant> findAll(int start, int end)
		throws SystemException {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the powwow participants.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.powwow.model.impl.PowwowParticipantModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of powwow participants
	 * @param end the upper bound of the range of powwow participants (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of powwow participants
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<PowwowParticipant> findAll(int start, int end,
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

		List<PowwowParticipant> list = (List<PowwowParticipant>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 3));

				query.append(_SQL_SELECT_POWWOWPARTICIPANT);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_POWWOWPARTICIPANT;

				if (pagination) {
					sql = sql.concat(PowwowParticipantModelImpl.ORDER_BY_JPQL);
				}
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (!pagination) {
					list = (List<PowwowParticipant>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = new UnmodifiableList<PowwowParticipant>(list);
				}
				else {
					list = (List<PowwowParticipant>)QueryUtil.list(q,
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
	 * Removes all the powwow participants from the database.
	 *
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public void removeAll() throws SystemException {
		for (PowwowParticipant powwowParticipant : findAll()) {
			remove(powwowParticipant);
		}
	}

	/**
	 * Returns the number of powwow participants.
	 *
	 * @return the number of powwow participants
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

				Query q = session.createQuery(_SQL_COUNT_POWWOWPARTICIPANT);

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
	 * Initializes the powwow participant persistence.
	 */
	public void afterPropertiesSet() {
		String[] listenerClassNames = StringUtil.split(GetterUtil.getString(
					com.liferay.util.service.ServiceProps.get(
						"value.object.listener.com.liferay.powwow.model.PowwowParticipant")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<PowwowParticipant>> listenersList = new ArrayList<ModelListener<PowwowParticipant>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<PowwowParticipant>)InstanceFactory.newInstance(
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
		EntityCacheUtil.removeCache(PowwowParticipantImpl.class.getName());
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	private static final String _SQL_SELECT_POWWOWPARTICIPANT = "SELECT powwowParticipant FROM PowwowParticipant powwowParticipant";
	private static final String _SQL_SELECT_POWWOWPARTICIPANT_WHERE = "SELECT powwowParticipant FROM PowwowParticipant powwowParticipant WHERE ";
	private static final String _SQL_COUNT_POWWOWPARTICIPANT = "SELECT COUNT(powwowParticipant) FROM PowwowParticipant powwowParticipant";
	private static final String _SQL_COUNT_POWWOWPARTICIPANT_WHERE = "SELECT COUNT(powwowParticipant) FROM PowwowParticipant powwowParticipant WHERE ";
	private static final String _ORDER_BY_ENTITY_ALIAS = "powwowParticipant.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No PowwowParticipant exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No PowwowParticipant exists with the key {";
	private static final boolean _HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE = GetterUtil.getBoolean(PropsUtil.get(
				PropsKeys.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE));
	private static Log _log = LogFactoryUtil.getLog(PowwowParticipantPersistenceImpl.class);
	private static Set<String> _badColumnNames = SetUtil.fromArray(new String[] {
				"type"
			});
	private static PowwowParticipant _nullPowwowParticipant = new PowwowParticipantImpl() {
			@Override
			public Object clone() {
				return this;
			}

			@Override
			public CacheModel<PowwowParticipant> toCacheModel() {
				return _nullPowwowParticipantCacheModel;
			}
		};

	private static CacheModel<PowwowParticipant> _nullPowwowParticipantCacheModel =
		new CacheModel<PowwowParticipant>() {
			@Override
			public PowwowParticipant toEntityModel() {
				return _nullPowwowParticipant;
			}
		};
}