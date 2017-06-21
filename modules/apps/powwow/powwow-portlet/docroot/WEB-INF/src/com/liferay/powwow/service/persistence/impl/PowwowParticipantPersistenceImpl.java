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

import aQute.bnd.annotation.ProviderType;

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
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.service.persistence.CompanyProvider;
import com.liferay.portal.kernel.service.persistence.CompanyProviderWrapper;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ReflectionUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;

import com.liferay.powwow.exception.NoSuchParticipantException;
import com.liferay.powwow.model.PowwowParticipant;
import com.liferay.powwow.model.impl.PowwowParticipantImpl;
import com.liferay.powwow.model.impl.PowwowParticipantModelImpl;
import com.liferay.powwow.service.persistence.PowwowParticipantPersistence;

import java.io.Serializable;

import java.lang.reflect.Field;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
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
 * @see com.liferay.powwow.service.persistence.PowwowParticipantUtil
 * @generated
 */
@ProviderType
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
	 */
	@Override
	public List<PowwowParticipant> findByPowwowMeetingId(long powwowMeetingId) {
		return findByPowwowMeetingId(powwowMeetingId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the powwow participants where powwowMeetingId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link PowwowParticipantModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param powwowMeetingId the powwow meeting ID
	 * @param start the lower bound of the range of powwow participants
	 * @param end the upper bound of the range of powwow participants (not inclusive)
	 * @return the range of matching powwow participants
	 */
	@Override
	public List<PowwowParticipant> findByPowwowMeetingId(long powwowMeetingId,
		int start, int end) {
		return findByPowwowMeetingId(powwowMeetingId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the powwow participants where powwowMeetingId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link PowwowParticipantModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param powwowMeetingId the powwow meeting ID
	 * @param start the lower bound of the range of powwow participants
	 * @param end the upper bound of the range of powwow participants (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching powwow participants
	 */
	@Override
	public List<PowwowParticipant> findByPowwowMeetingId(long powwowMeetingId,
		int start, int end,
		OrderByComparator<PowwowParticipant> orderByComparator) {
		return findByPowwowMeetingId(powwowMeetingId, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the powwow participants where powwowMeetingId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link PowwowParticipantModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param powwowMeetingId the powwow meeting ID
	 * @param start the lower bound of the range of powwow participants
	 * @param end the upper bound of the range of powwow participants (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching powwow participants
	 */
	@Override
	public List<PowwowParticipant> findByPowwowMeetingId(long powwowMeetingId,
		int start, int end,
		OrderByComparator<PowwowParticipant> orderByComparator,
		boolean retrieveFromCache) {
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

		List<PowwowParticipant> list = null;

		if (retrieveFromCache) {
			list = (List<PowwowParticipant>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (PowwowParticipant powwowParticipant : list) {
					if ((powwowMeetingId != powwowParticipant.getPowwowMeetingId())) {
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
						(orderByComparator.getOrderByFields().length * 2));
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

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<PowwowParticipant>)QueryUtil.list(q,
							getDialect(), start, end);
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
	 * Returns the first powwow participant in the ordered set where powwowMeetingId = &#63;.
	 *
	 * @param powwowMeetingId the powwow meeting ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching powwow participant
	 * @throws NoSuchParticipantException if a matching powwow participant could not be found
	 */
	@Override
	public PowwowParticipant findByPowwowMeetingId_First(long powwowMeetingId,
		OrderByComparator<PowwowParticipant> orderByComparator)
		throws NoSuchParticipantException {
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
	 */
	@Override
	public PowwowParticipant fetchByPowwowMeetingId_First(
		long powwowMeetingId,
		OrderByComparator<PowwowParticipant> orderByComparator) {
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
	 * @throws NoSuchParticipantException if a matching powwow participant could not be found
	 */
	@Override
	public PowwowParticipant findByPowwowMeetingId_Last(long powwowMeetingId,
		OrderByComparator<PowwowParticipant> orderByComparator)
		throws NoSuchParticipantException {
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
	 */
	@Override
	public PowwowParticipant fetchByPowwowMeetingId_Last(long powwowMeetingId,
		OrderByComparator<PowwowParticipant> orderByComparator) {
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
	 * @throws NoSuchParticipantException if a powwow participant with the primary key could not be found
	 */
	@Override
	public PowwowParticipant[] findByPowwowMeetingId_PrevAndNext(
		long powwowParticipantId, long powwowMeetingId,
		OrderByComparator<PowwowParticipant> orderByComparator)
		throws NoSuchParticipantException {
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
		long powwowMeetingId,
		OrderByComparator<PowwowParticipant> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(4 +
					(orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
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
	 */
	@Override
	public void removeByPowwowMeetingId(long powwowMeetingId) {
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
	 */
	@Override
	public int countByPowwowMeetingId(long powwowMeetingId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_POWWOWMEETINGID;

		Object[] finderArgs = new Object[] { powwowMeetingId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

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
	 * Returns the powwow participant where powwowMeetingId = &#63; and participantUserId = &#63; or throws a {@link NoSuchParticipantException} if it could not be found.
	 *
	 * @param powwowMeetingId the powwow meeting ID
	 * @param participantUserId the participant user ID
	 * @return the matching powwow participant
	 * @throws NoSuchParticipantException if a matching powwow participant could not be found
	 */
	@Override
	public PowwowParticipant findByPMI_PUI(long powwowMeetingId,
		long participantUserId) throws NoSuchParticipantException {
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

			if (_log.isDebugEnabled()) {
				_log.debug(msg.toString());
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
	 */
	@Override
	public PowwowParticipant fetchByPMI_PUI(long powwowMeetingId,
		long participantUserId) {
		return fetchByPMI_PUI(powwowMeetingId, participantUserId, true);
	}

	/**
	 * Returns the powwow participant where powwowMeetingId = &#63; and participantUserId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param powwowMeetingId the powwow meeting ID
	 * @param participantUserId the participant user ID
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the matching powwow participant, or <code>null</code> if a matching powwow participant could not be found
	 */
	@Override
	public PowwowParticipant fetchByPMI_PUI(long powwowMeetingId,
		long participantUserId, boolean retrieveFromCache) {
		Object[] finderArgs = new Object[] { powwowMeetingId, participantUserId };

		Object result = null;

		if (retrieveFromCache) {
			result = finderCache.getResult(FINDER_PATH_FETCH_BY_PMI_PUI,
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
					finderCache.putResult(FINDER_PATH_FETCH_BY_PMI_PUI,
						finderArgs, list);
				}
				else {
					if (list.size() > 1) {
						Collections.sort(list, Collections.reverseOrder());

						if (_log.isWarnEnabled()) {
							_log.warn(
								"PowwowParticipantPersistenceImpl.fetchByPMI_PUI(long, long, boolean) with parameters (" +
								StringUtil.merge(finderArgs) +
								") yields a result set with more than 1 result. This violates the logical unique restriction. There is no order guarantee on which result is returned by this finder.");
						}
					}

					PowwowParticipant powwowParticipant = list.get(0);

					result = powwowParticipant;

					cacheResult(powwowParticipant);

					if ((powwowParticipant.getPowwowMeetingId() != powwowMeetingId) ||
							(powwowParticipant.getParticipantUserId() != participantUserId)) {
						finderCache.putResult(FINDER_PATH_FETCH_BY_PMI_PUI,
							finderArgs, powwowParticipant);
					}
				}
			}
			catch (Exception e) {
				finderCache.removeResult(FINDER_PATH_FETCH_BY_PMI_PUI,
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
	 */
	@Override
	public PowwowParticipant removeByPMI_PUI(long powwowMeetingId,
		long participantUserId) throws NoSuchParticipantException {
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
	 */
	@Override
	public int countByPMI_PUI(long powwowMeetingId, long participantUserId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_PMI_PUI;

		Object[] finderArgs = new Object[] { powwowMeetingId, participantUserId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

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
	 * Returns the powwow participant where powwowMeetingId = &#63; and emailAddress = &#63; or throws a {@link NoSuchParticipantException} if it could not be found.
	 *
	 * @param powwowMeetingId the powwow meeting ID
	 * @param emailAddress the email address
	 * @return the matching powwow participant
	 * @throws NoSuchParticipantException if a matching powwow participant could not be found
	 */
	@Override
	public PowwowParticipant findByPMI_EA(long powwowMeetingId,
		String emailAddress) throws NoSuchParticipantException {
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

			if (_log.isDebugEnabled()) {
				_log.debug(msg.toString());
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
	 */
	@Override
	public PowwowParticipant fetchByPMI_EA(long powwowMeetingId,
		String emailAddress) {
		return fetchByPMI_EA(powwowMeetingId, emailAddress, true);
	}

	/**
	 * Returns the powwow participant where powwowMeetingId = &#63; and emailAddress = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param powwowMeetingId the powwow meeting ID
	 * @param emailAddress the email address
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the matching powwow participant, or <code>null</code> if a matching powwow participant could not be found
	 */
	@Override
	public PowwowParticipant fetchByPMI_EA(long powwowMeetingId,
		String emailAddress, boolean retrieveFromCache) {
		Object[] finderArgs = new Object[] { powwowMeetingId, emailAddress };

		Object result = null;

		if (retrieveFromCache) {
			result = finderCache.getResult(FINDER_PATH_FETCH_BY_PMI_EA,
					finderArgs, this);
		}

		if (result instanceof PowwowParticipant) {
			PowwowParticipant powwowParticipant = (PowwowParticipant)result;

			if ((powwowMeetingId != powwowParticipant.getPowwowMeetingId()) ||
					!Objects.equals(emailAddress,
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
					finderCache.putResult(FINDER_PATH_FETCH_BY_PMI_EA,
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
						finderCache.putResult(FINDER_PATH_FETCH_BY_PMI_EA,
							finderArgs, powwowParticipant);
					}
				}
			}
			catch (Exception e) {
				finderCache.removeResult(FINDER_PATH_FETCH_BY_PMI_EA, finderArgs);

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
	 */
	@Override
	public PowwowParticipant removeByPMI_EA(long powwowMeetingId,
		String emailAddress) throws NoSuchParticipantException {
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
	 */
	@Override
	public int countByPMI_EA(long powwowMeetingId, String emailAddress) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_PMI_EA;

		Object[] finderArgs = new Object[] { powwowMeetingId, emailAddress };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

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
	 */
	@Override
	public List<PowwowParticipant> findByPMI_T(long powwowMeetingId, int type) {
		return findByPMI_T(powwowMeetingId, type, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the powwow participants where powwowMeetingId = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link PowwowParticipantModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param powwowMeetingId the powwow meeting ID
	 * @param type the type
	 * @param start the lower bound of the range of powwow participants
	 * @param end the upper bound of the range of powwow participants (not inclusive)
	 * @return the range of matching powwow participants
	 */
	@Override
	public List<PowwowParticipant> findByPMI_T(long powwowMeetingId, int type,
		int start, int end) {
		return findByPMI_T(powwowMeetingId, type, start, end, null);
	}

	/**
	 * Returns an ordered range of all the powwow participants where powwowMeetingId = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link PowwowParticipantModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	public List<PowwowParticipant> findByPMI_T(long powwowMeetingId, int type,
		int start, int end,
		OrderByComparator<PowwowParticipant> orderByComparator) {
		return findByPMI_T(powwowMeetingId, type, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the powwow participants where powwowMeetingId = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link PowwowParticipantModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param powwowMeetingId the powwow meeting ID
	 * @param type the type
	 * @param start the lower bound of the range of powwow participants
	 * @param end the upper bound of the range of powwow participants (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching powwow participants
	 */
	@Override
	public List<PowwowParticipant> findByPMI_T(long powwowMeetingId, int type,
		int start, int end,
		OrderByComparator<PowwowParticipant> orderByComparator,
		boolean retrieveFromCache) {
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

		List<PowwowParticipant> list = null;

		if (retrieveFromCache) {
			list = (List<PowwowParticipant>)finderCache.getResult(finderPath,
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
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(4 +
						(orderByComparator.getOrderByFields().length * 2));
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

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<PowwowParticipant>)QueryUtil.list(q,
							getDialect(), start, end);
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
	 * Returns the first powwow participant in the ordered set where powwowMeetingId = &#63; and type = &#63;.
	 *
	 * @param powwowMeetingId the powwow meeting ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching powwow participant
	 * @throws NoSuchParticipantException if a matching powwow participant could not be found
	 */
	@Override
	public PowwowParticipant findByPMI_T_First(long powwowMeetingId, int type,
		OrderByComparator<PowwowParticipant> orderByComparator)
		throws NoSuchParticipantException {
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
	 */
	@Override
	public PowwowParticipant fetchByPMI_T_First(long powwowMeetingId, int type,
		OrderByComparator<PowwowParticipant> orderByComparator) {
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
	 * @throws NoSuchParticipantException if a matching powwow participant could not be found
	 */
	@Override
	public PowwowParticipant findByPMI_T_Last(long powwowMeetingId, int type,
		OrderByComparator<PowwowParticipant> orderByComparator)
		throws NoSuchParticipantException {
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
	 */
	@Override
	public PowwowParticipant fetchByPMI_T_Last(long powwowMeetingId, int type,
		OrderByComparator<PowwowParticipant> orderByComparator) {
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
	 * @throws NoSuchParticipantException if a powwow participant with the primary key could not be found
	 */
	@Override
	public PowwowParticipant[] findByPMI_T_PrevAndNext(
		long powwowParticipantId, long powwowMeetingId, int type,
		OrderByComparator<PowwowParticipant> orderByComparator)
		throws NoSuchParticipantException {
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
		OrderByComparator<PowwowParticipant> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(5 +
					(orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(4);
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
	 */
	@Override
	public void removeByPMI_T(long powwowMeetingId, int type) {
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
	 */
	@Override
	public int countByPMI_T(long powwowMeetingId, int type) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_PMI_T;

		Object[] finderArgs = new Object[] { powwowMeetingId, type };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

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

	private static final String _FINDER_COLUMN_PMI_T_POWWOWMEETINGID_2 = "powwowParticipant.powwowMeetingId = ? AND ";
	private static final String _FINDER_COLUMN_PMI_T_TYPE_2 = "powwowParticipant.type = ?";

	public PowwowParticipantPersistenceImpl() {
		setModelClass(PowwowParticipant.class);

		try {
			Field field = ReflectionUtil.getDeclaredField(BasePersistenceImpl.class,
					"_dbColumnNames");

			Map<String, String> dbColumnNames = new HashMap<String, String>();

			dbColumnNames.put("type", "type_");

			field.set(this, dbColumnNames);
		}
		catch (Exception e) {
			if (_log.isDebugEnabled()) {
				_log.debug(e, e);
			}
		}
	}

	/**
	 * Caches the powwow participant in the entity cache if it is enabled.
	 *
	 * @param powwowParticipant the powwow participant
	 */
	@Override
	public void cacheResult(PowwowParticipant powwowParticipant) {
		entityCache.putResult(PowwowParticipantModelImpl.ENTITY_CACHE_ENABLED,
			PowwowParticipantImpl.class, powwowParticipant.getPrimaryKey(),
			powwowParticipant);

		finderCache.putResult(FINDER_PATH_FETCH_BY_PMI_PUI,
			new Object[] {
				powwowParticipant.getPowwowMeetingId(),
				powwowParticipant.getParticipantUserId()
			}, powwowParticipant);

		finderCache.putResult(FINDER_PATH_FETCH_BY_PMI_EA,
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
			if (entityCache.getResult(
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
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(PowwowParticipantImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the powwow participant.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(PowwowParticipant powwowParticipant) {
		entityCache.removeResult(PowwowParticipantModelImpl.ENTITY_CACHE_ENABLED,
			PowwowParticipantImpl.class, powwowParticipant.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache((PowwowParticipantModelImpl)powwowParticipant,
			true);
	}

	@Override
	public void clearCache(List<PowwowParticipant> powwowParticipants) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (PowwowParticipant powwowParticipant : powwowParticipants) {
			entityCache.removeResult(PowwowParticipantModelImpl.ENTITY_CACHE_ENABLED,
				PowwowParticipantImpl.class, powwowParticipant.getPrimaryKey());

			clearUniqueFindersCache((PowwowParticipantModelImpl)powwowParticipant,
				true);
		}
	}

	protected void cacheUniqueFindersCache(
		PowwowParticipantModelImpl powwowParticipantModelImpl) {
		Object[] args = new Object[] {
				powwowParticipantModelImpl.getPowwowMeetingId(),
				powwowParticipantModelImpl.getParticipantUserId()
			};

		finderCache.putResult(FINDER_PATH_COUNT_BY_PMI_PUI, args,
			Long.valueOf(1), false);
		finderCache.putResult(FINDER_PATH_FETCH_BY_PMI_PUI, args,
			powwowParticipantModelImpl, false);

		args = new Object[] {
				powwowParticipantModelImpl.getPowwowMeetingId(),
				powwowParticipantModelImpl.getEmailAddress()
			};

		finderCache.putResult(FINDER_PATH_COUNT_BY_PMI_EA, args,
			Long.valueOf(1), false);
		finderCache.putResult(FINDER_PATH_FETCH_BY_PMI_EA, args,
			powwowParticipantModelImpl, false);
	}

	protected void clearUniqueFindersCache(
		PowwowParticipantModelImpl powwowParticipantModelImpl,
		boolean clearCurrent) {
		if (clearCurrent) {
			Object[] args = new Object[] {
					powwowParticipantModelImpl.getPowwowMeetingId(),
					powwowParticipantModelImpl.getParticipantUserId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_PMI_PUI, args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_PMI_PUI, args);
		}

		if ((powwowParticipantModelImpl.getColumnBitmask() &
				FINDER_PATH_FETCH_BY_PMI_PUI.getColumnBitmask()) != 0) {
			Object[] args = new Object[] {
					powwowParticipantModelImpl.getOriginalPowwowMeetingId(),
					powwowParticipantModelImpl.getOriginalParticipantUserId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_PMI_PUI, args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_PMI_PUI, args);
		}

		if (clearCurrent) {
			Object[] args = new Object[] {
					powwowParticipantModelImpl.getPowwowMeetingId(),
					powwowParticipantModelImpl.getEmailAddress()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_PMI_EA, args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_PMI_EA, args);
		}

		if ((powwowParticipantModelImpl.getColumnBitmask() &
				FINDER_PATH_FETCH_BY_PMI_EA.getColumnBitmask()) != 0) {
			Object[] args = new Object[] {
					powwowParticipantModelImpl.getOriginalPowwowMeetingId(),
					powwowParticipantModelImpl.getOriginalEmailAddress()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_PMI_EA, args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_PMI_EA, args);
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

		powwowParticipant.setCompanyId(companyProvider.getCompanyId());

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

			PowwowParticipant powwowParticipant = (PowwowParticipant)session.get(PowwowParticipantImpl.class,
					primaryKey);

			if (powwowParticipant == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
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
	protected PowwowParticipant removeImpl(PowwowParticipant powwowParticipant) {
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
	public PowwowParticipant updateImpl(PowwowParticipant powwowParticipant) {
		powwowParticipant = toUnwrappedModel(powwowParticipant);

		boolean isNew = powwowParticipant.isNew();

		PowwowParticipantModelImpl powwowParticipantModelImpl = (PowwowParticipantModelImpl)powwowParticipant;

		ServiceContext serviceContext = ServiceContextThreadLocal.getServiceContext();

		Date now = new Date();

		if (isNew && (powwowParticipant.getCreateDate() == null)) {
			if (serviceContext == null) {
				powwowParticipant.setCreateDate(now);
			}
			else {
				powwowParticipant.setCreateDate(serviceContext.getCreateDate(
						now));
			}
		}

		if (!powwowParticipantModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				powwowParticipant.setModifiedDate(now);
			}
			else {
				powwowParticipant.setModifiedDate(serviceContext.getModifiedDate(
						now));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (powwowParticipant.isNew()) {
				session.save(powwowParticipant);

				powwowParticipant.setNew(false);
			}
			else {
				powwowParticipant = (PowwowParticipant)session.merge(powwowParticipant);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (!PowwowParticipantModelImpl.COLUMN_BITMASK_ENABLED) {
			finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}
		else
		 if (isNew) {
			Object[] args = new Object[] {
					powwowParticipantModelImpl.getPowwowMeetingId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_POWWOWMEETINGID, args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_POWWOWMEETINGID,
				args);

			args = new Object[] {
					powwowParticipantModelImpl.getPowwowMeetingId(),
					powwowParticipantModelImpl.getType()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_PMI_T, args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_PMI_T,
				args);

			finderCache.removeResult(FINDER_PATH_COUNT_ALL, FINDER_ARGS_EMPTY);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL,
				FINDER_ARGS_EMPTY);
		}

		else {
			if ((powwowParticipantModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_POWWOWMEETINGID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						powwowParticipantModelImpl.getOriginalPowwowMeetingId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_POWWOWMEETINGID,
					args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_POWWOWMEETINGID,
					args);

				args = new Object[] {
						powwowParticipantModelImpl.getPowwowMeetingId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_POWWOWMEETINGID,
					args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_POWWOWMEETINGID,
					args);
			}

			if ((powwowParticipantModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_PMI_T.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						powwowParticipantModelImpl.getOriginalPowwowMeetingId(),
						powwowParticipantModelImpl.getOriginalType()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_PMI_T, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_PMI_T,
					args);

				args = new Object[] {
						powwowParticipantModelImpl.getPowwowMeetingId(),
						powwowParticipantModelImpl.getType()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_PMI_T, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_PMI_T,
					args);
			}
		}

		entityCache.putResult(PowwowParticipantModelImpl.ENTITY_CACHE_ENABLED,
			PowwowParticipantImpl.class, powwowParticipant.getPrimaryKey(),
			powwowParticipant, false);

		clearUniqueFindersCache(powwowParticipantModelImpl, false);
		cacheUniqueFindersCache(powwowParticipantModelImpl);

		powwowParticipant.resetOriginalValues();

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
	 * Returns the powwow participant with the primary key or throws a {@link com.liferay.portal.kernel.exception.NoSuchModelException} if it could not be found.
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

			throw new NoSuchParticipantException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				primaryKey);
		}

		return powwowParticipant;
	}

	/**
	 * Returns the powwow participant with the primary key or throws a {@link NoSuchParticipantException} if it could not be found.
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
	 * @param primaryKey the primary key of the powwow participant
	 * @return the powwow participant, or <code>null</code> if a powwow participant with the primary key could not be found
	 */
	@Override
	public PowwowParticipant fetchByPrimaryKey(Serializable primaryKey) {
		Serializable serializable = entityCache.getResult(PowwowParticipantModelImpl.ENTITY_CACHE_ENABLED,
				PowwowParticipantImpl.class, primaryKey);

		if (serializable == nullModel) {
			return null;
		}

		PowwowParticipant powwowParticipant = (PowwowParticipant)serializable;

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
					entityCache.putResult(PowwowParticipantModelImpl.ENTITY_CACHE_ENABLED,
						PowwowParticipantImpl.class, primaryKey, nullModel);
				}
			}
			catch (Exception e) {
				entityCache.removeResult(PowwowParticipantModelImpl.ENTITY_CACHE_ENABLED,
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
	 */
	@Override
	public PowwowParticipant fetchByPrimaryKey(long powwowParticipantId) {
		return fetchByPrimaryKey((Serializable)powwowParticipantId);
	}

	@Override
	public Map<Serializable, PowwowParticipant> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {
		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, PowwowParticipant> map = new HashMap<Serializable, PowwowParticipant>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			PowwowParticipant powwowParticipant = fetchByPrimaryKey(primaryKey);

			if (powwowParticipant != null) {
				map.put(primaryKey, powwowParticipant);
			}

			return map;
		}

		Set<Serializable> uncachedPrimaryKeys = null;

		for (Serializable primaryKey : primaryKeys) {
			Serializable serializable = entityCache.getResult(PowwowParticipantModelImpl.ENTITY_CACHE_ENABLED,
					PowwowParticipantImpl.class, primaryKey);

			if (serializable != nullModel) {
				if (serializable == null) {
					if (uncachedPrimaryKeys == null) {
						uncachedPrimaryKeys = new HashSet<Serializable>();
					}

					uncachedPrimaryKeys.add(primaryKey);
				}
				else {
					map.put(primaryKey, (PowwowParticipant)serializable);
				}
			}
		}

		if (uncachedPrimaryKeys == null) {
			return map;
		}

		StringBundler query = new StringBundler((uncachedPrimaryKeys.size() * 2) +
				1);

		query.append(_SQL_SELECT_POWWOWPARTICIPANT_WHERE_PKS_IN);

		for (Serializable primaryKey : uncachedPrimaryKeys) {
			query.append((long)primaryKey);

			query.append(StringPool.COMMA);
		}

		query.setIndex(query.index() - 1);

		query.append(StringPool.CLOSE_PARENTHESIS);

		String sql = query.toString();

		Session session = null;

		try {
			session = openSession();

			Query q = session.createQuery(sql);

			for (PowwowParticipant powwowParticipant : (List<PowwowParticipant>)q.list()) {
				map.put(powwowParticipant.getPrimaryKeyObj(), powwowParticipant);

				cacheResult(powwowParticipant);

				uncachedPrimaryKeys.remove(powwowParticipant.getPrimaryKeyObj());
			}

			for (Serializable primaryKey : uncachedPrimaryKeys) {
				entityCache.putResult(PowwowParticipantModelImpl.ENTITY_CACHE_ENABLED,
					PowwowParticipantImpl.class, primaryKey, nullModel);
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link PowwowParticipantModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link PowwowParticipantModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of powwow participants
	 * @param end the upper bound of the range of powwow participants (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of powwow participants
	 */
	@Override
	public List<PowwowParticipant> findAll(int start, int end,
		OrderByComparator<PowwowParticipant> orderByComparator) {
		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the powwow participants.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link PowwowParticipantModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of powwow participants
	 * @param end the upper bound of the range of powwow participants (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of powwow participants
	 */
	@Override
	public List<PowwowParticipant> findAll(int start, int end,
		OrderByComparator<PowwowParticipant> orderByComparator,
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

		List<PowwowParticipant> list = null;

		if (retrieveFromCache) {
			list = (List<PowwowParticipant>)finderCache.getResult(finderPath,
					finderArgs, this);
		}

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 2));

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

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<PowwowParticipant>)QueryUtil.list(q,
							getDialect(), start, end);
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
		Long count = (Long)finderCache.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_POWWOWPARTICIPANT);

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
		return PowwowParticipantModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the powwow participant persistence.
	 */
	public void afterPropertiesSet() {
	}

	public void destroy() {
		entityCache.removeCache(PowwowParticipantImpl.class.getName());
		finderCache.removeCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@BeanReference(type = CompanyProviderWrapper.class)
	protected CompanyProvider companyProvider;
	protected EntityCache entityCache = EntityCacheUtil.getEntityCache();
	protected FinderCache finderCache = FinderCacheUtil.getFinderCache();
	private static final String _SQL_SELECT_POWWOWPARTICIPANT = "SELECT powwowParticipant FROM PowwowParticipant powwowParticipant";
	private static final String _SQL_SELECT_POWWOWPARTICIPANT_WHERE_PKS_IN = "SELECT powwowParticipant FROM PowwowParticipant powwowParticipant WHERE powwowParticipantId IN (";
	private static final String _SQL_SELECT_POWWOWPARTICIPANT_WHERE = "SELECT powwowParticipant FROM PowwowParticipant powwowParticipant WHERE ";
	private static final String _SQL_COUNT_POWWOWPARTICIPANT = "SELECT COUNT(powwowParticipant) FROM PowwowParticipant powwowParticipant";
	private static final String _SQL_COUNT_POWWOWPARTICIPANT_WHERE = "SELECT COUNT(powwowParticipant) FROM PowwowParticipant powwowParticipant WHERE ";
	private static final String _ORDER_BY_ENTITY_ALIAS = "powwowParticipant.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No PowwowParticipant exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No PowwowParticipant exists with the key {";
	private static final Log _log = LogFactoryUtil.getLog(PowwowParticipantPersistenceImpl.class);
	private static final Set<String> _badColumnNames = SetUtil.fromArray(new String[] {
				"type"
			});
}