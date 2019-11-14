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

package com.liferay.portlet.announcements.service.persistence.impl;

import com.liferay.announcements.kernel.exception.NoSuchFlagException;
import com.liferay.announcements.kernel.model.AnnouncementsFlag;
import com.liferay.announcements.kernel.service.persistence.AnnouncementsFlagPersistence;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.orm.EntityCache;
import com.liferay.portal.kernel.dao.orm.EntityCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portlet.announcements.model.impl.AnnouncementsFlagImpl;
import com.liferay.portlet.announcements.model.impl.AnnouncementsFlagModelImpl;

import java.io.Serializable;

import java.lang.reflect.InvocationHandler;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The persistence implementation for the announcements flag service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class AnnouncementsFlagPersistenceImpl
	extends BasePersistenceImpl<AnnouncementsFlag>
	implements AnnouncementsFlagPersistence {

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>AnnouncementsFlagUtil</code> to access the announcements flag persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		AnnouncementsFlagImpl.class.getName();

	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List1";

	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List2";

	private FinderPath _finderPathWithPaginationFindAll;
	private FinderPath _finderPathWithoutPaginationFindAll;
	private FinderPath _finderPathCountAll;
	private FinderPath _finderPathWithPaginationFindByCompanyId;
	private FinderPath _finderPathWithoutPaginationFindByCompanyId;
	private FinderPath _finderPathCountByCompanyId;

	/**
	 * Returns all the announcements flags where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching announcements flags
	 */
	@Override
	public List<AnnouncementsFlag> findByCompanyId(long companyId) {
		return findByCompanyId(
			companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the announcements flags where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AnnouncementsFlagModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of announcements flags
	 * @param end the upper bound of the range of announcements flags (not inclusive)
	 * @return the range of matching announcements flags
	 */
	@Override
	public List<AnnouncementsFlag> findByCompanyId(
		long companyId, int start, int end) {

		return findByCompanyId(companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the announcements flags where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AnnouncementsFlagModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of announcements flags
	 * @param end the upper bound of the range of announcements flags (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching announcements flags
	 */
	@Override
	public List<AnnouncementsFlag> findByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<AnnouncementsFlag> orderByComparator) {

		return findByCompanyId(companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the announcements flags where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AnnouncementsFlagModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of announcements flags
	 * @param end the upper bound of the range of announcements flags (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching announcements flags
	 */
	@Override
	public List<AnnouncementsFlag> findByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<AnnouncementsFlag> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByCompanyId;
				finderArgs = new Object[] {companyId};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByCompanyId;
			finderArgs = new Object[] {
				companyId, start, end, orderByComparator
			};
		}

		List<AnnouncementsFlag> list = null;

		if (useFinderCache) {
			list = (List<AnnouncementsFlag>)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (AnnouncementsFlag announcementsFlag : list) {
					if (companyId != announcementsFlag.getCompanyId()) {
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

			query.append(_SQL_SELECT_ANNOUNCEMENTSFLAG_WHERE);

			query.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				query.append(AnnouncementsFlagModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				list = (List<AnnouncementsFlag>)QueryUtil.list(
					q, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					FinderCacheUtil.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception e) {
				if (useFinderCache) {
					FinderCacheUtil.removeResult(finderPath, finderArgs);
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
	 * Returns the first announcements flag in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching announcements flag
	 * @throws NoSuchFlagException if a matching announcements flag could not be found
	 */
	@Override
	public AnnouncementsFlag findByCompanyId_First(
			long companyId,
			OrderByComparator<AnnouncementsFlag> orderByComparator)
		throws NoSuchFlagException {

		AnnouncementsFlag announcementsFlag = fetchByCompanyId_First(
			companyId, orderByComparator);

		if (announcementsFlag != null) {
			return announcementsFlag;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("companyId=");
		msg.append(companyId);

		msg.append("}");

		throw new NoSuchFlagException(msg.toString());
	}

	/**
	 * Returns the first announcements flag in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching announcements flag, or <code>null</code> if a matching announcements flag could not be found
	 */
	@Override
	public AnnouncementsFlag fetchByCompanyId_First(
		long companyId,
		OrderByComparator<AnnouncementsFlag> orderByComparator) {

		List<AnnouncementsFlag> list = findByCompanyId(
			companyId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last announcements flag in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching announcements flag
	 * @throws NoSuchFlagException if a matching announcements flag could not be found
	 */
	@Override
	public AnnouncementsFlag findByCompanyId_Last(
			long companyId,
			OrderByComparator<AnnouncementsFlag> orderByComparator)
		throws NoSuchFlagException {

		AnnouncementsFlag announcementsFlag = fetchByCompanyId_Last(
			companyId, orderByComparator);

		if (announcementsFlag != null) {
			return announcementsFlag;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("companyId=");
		msg.append(companyId);

		msg.append("}");

		throw new NoSuchFlagException(msg.toString());
	}

	/**
	 * Returns the last announcements flag in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching announcements flag, or <code>null</code> if a matching announcements flag could not be found
	 */
	@Override
	public AnnouncementsFlag fetchByCompanyId_Last(
		long companyId,
		OrderByComparator<AnnouncementsFlag> orderByComparator) {

		int count = countByCompanyId(companyId);

		if (count == 0) {
			return null;
		}

		List<AnnouncementsFlag> list = findByCompanyId(
			companyId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the announcements flags before and after the current announcements flag in the ordered set where companyId = &#63;.
	 *
	 * @param flagId the primary key of the current announcements flag
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next announcements flag
	 * @throws NoSuchFlagException if a announcements flag with the primary key could not be found
	 */
	@Override
	public AnnouncementsFlag[] findByCompanyId_PrevAndNext(
			long flagId, long companyId,
			OrderByComparator<AnnouncementsFlag> orderByComparator)
		throws NoSuchFlagException {

		AnnouncementsFlag announcementsFlag = findByPrimaryKey(flagId);

		Session session = null;

		try {
			session = openSession();

			AnnouncementsFlag[] array = new AnnouncementsFlagImpl[3];

			array[0] = getByCompanyId_PrevAndNext(
				session, announcementsFlag, companyId, orderByComparator, true);

			array[1] = announcementsFlag;

			array[2] = getByCompanyId_PrevAndNext(
				session, announcementsFlag, companyId, orderByComparator,
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

	protected AnnouncementsFlag getByCompanyId_PrevAndNext(
		Session session, AnnouncementsFlag announcementsFlag, long companyId,
		OrderByComparator<AnnouncementsFlag> orderByComparator,
		boolean previous) {

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_ANNOUNCEMENTSFLAG_WHERE);

		query.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

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
			query.append(AnnouncementsFlagModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(companyId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						announcementsFlag)) {

				qPos.add(orderByConditionValue);
			}
		}

		List<AnnouncementsFlag> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the announcements flags where companyId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 */
	@Override
	public void removeByCompanyId(long companyId) {
		for (AnnouncementsFlag announcementsFlag :
				findByCompanyId(
					companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(announcementsFlag);
		}
	}

	/**
	 * Returns the number of announcements flags where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching announcements flags
	 */
	@Override
	public int countByCompanyId(long companyId) {
		FinderPath finderPath = _finderPathCountByCompanyId;

		Object[] finderArgs = new Object[] {companyId};

		Long count = (Long)FinderCacheUtil.getResult(
			finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_ANNOUNCEMENTSFLAG_WHERE);

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

	private static final String _FINDER_COLUMN_COMPANYID_COMPANYID_2 =
		"announcementsFlag.companyId = ?";

	private FinderPath _finderPathWithPaginationFindByEntryId;
	private FinderPath _finderPathWithoutPaginationFindByEntryId;
	private FinderPath _finderPathCountByEntryId;

	/**
	 * Returns all the announcements flags where entryId = &#63;.
	 *
	 * @param entryId the entry ID
	 * @return the matching announcements flags
	 */
	@Override
	public List<AnnouncementsFlag> findByEntryId(long entryId) {
		return findByEntryId(
			entryId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the announcements flags where entryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AnnouncementsFlagModelImpl</code>.
	 * </p>
	 *
	 * @param entryId the entry ID
	 * @param start the lower bound of the range of announcements flags
	 * @param end the upper bound of the range of announcements flags (not inclusive)
	 * @return the range of matching announcements flags
	 */
	@Override
	public List<AnnouncementsFlag> findByEntryId(
		long entryId, int start, int end) {

		return findByEntryId(entryId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the announcements flags where entryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AnnouncementsFlagModelImpl</code>.
	 * </p>
	 *
	 * @param entryId the entry ID
	 * @param start the lower bound of the range of announcements flags
	 * @param end the upper bound of the range of announcements flags (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching announcements flags
	 */
	@Override
	public List<AnnouncementsFlag> findByEntryId(
		long entryId, int start, int end,
		OrderByComparator<AnnouncementsFlag> orderByComparator) {

		return findByEntryId(entryId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the announcements flags where entryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AnnouncementsFlagModelImpl</code>.
	 * </p>
	 *
	 * @param entryId the entry ID
	 * @param start the lower bound of the range of announcements flags
	 * @param end the upper bound of the range of announcements flags (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching announcements flags
	 */
	@Override
	public List<AnnouncementsFlag> findByEntryId(
		long entryId, int start, int end,
		OrderByComparator<AnnouncementsFlag> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByEntryId;
				finderArgs = new Object[] {entryId};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByEntryId;
			finderArgs = new Object[] {entryId, start, end, orderByComparator};
		}

		List<AnnouncementsFlag> list = null;

		if (useFinderCache) {
			list = (List<AnnouncementsFlag>)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (AnnouncementsFlag announcementsFlag : list) {
					if (entryId != announcementsFlag.getEntryId()) {
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

			query.append(_SQL_SELECT_ANNOUNCEMENTSFLAG_WHERE);

			query.append(_FINDER_COLUMN_ENTRYID_ENTRYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				query.append(AnnouncementsFlagModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(entryId);

				list = (List<AnnouncementsFlag>)QueryUtil.list(
					q, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					FinderCacheUtil.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception e) {
				if (useFinderCache) {
					FinderCacheUtil.removeResult(finderPath, finderArgs);
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
	 * Returns the first announcements flag in the ordered set where entryId = &#63;.
	 *
	 * @param entryId the entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching announcements flag
	 * @throws NoSuchFlagException if a matching announcements flag could not be found
	 */
	@Override
	public AnnouncementsFlag findByEntryId_First(
			long entryId,
			OrderByComparator<AnnouncementsFlag> orderByComparator)
		throws NoSuchFlagException {

		AnnouncementsFlag announcementsFlag = fetchByEntryId_First(
			entryId, orderByComparator);

		if (announcementsFlag != null) {
			return announcementsFlag;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("entryId=");
		msg.append(entryId);

		msg.append("}");

		throw new NoSuchFlagException(msg.toString());
	}

	/**
	 * Returns the first announcements flag in the ordered set where entryId = &#63;.
	 *
	 * @param entryId the entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching announcements flag, or <code>null</code> if a matching announcements flag could not be found
	 */
	@Override
	public AnnouncementsFlag fetchByEntryId_First(
		long entryId, OrderByComparator<AnnouncementsFlag> orderByComparator) {

		List<AnnouncementsFlag> list = findByEntryId(
			entryId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last announcements flag in the ordered set where entryId = &#63;.
	 *
	 * @param entryId the entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching announcements flag
	 * @throws NoSuchFlagException if a matching announcements flag could not be found
	 */
	@Override
	public AnnouncementsFlag findByEntryId_Last(
			long entryId,
			OrderByComparator<AnnouncementsFlag> orderByComparator)
		throws NoSuchFlagException {

		AnnouncementsFlag announcementsFlag = fetchByEntryId_Last(
			entryId, orderByComparator);

		if (announcementsFlag != null) {
			return announcementsFlag;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("entryId=");
		msg.append(entryId);

		msg.append("}");

		throw new NoSuchFlagException(msg.toString());
	}

	/**
	 * Returns the last announcements flag in the ordered set where entryId = &#63;.
	 *
	 * @param entryId the entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching announcements flag, or <code>null</code> if a matching announcements flag could not be found
	 */
	@Override
	public AnnouncementsFlag fetchByEntryId_Last(
		long entryId, OrderByComparator<AnnouncementsFlag> orderByComparator) {

		int count = countByEntryId(entryId);

		if (count == 0) {
			return null;
		}

		List<AnnouncementsFlag> list = findByEntryId(
			entryId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the announcements flags before and after the current announcements flag in the ordered set where entryId = &#63;.
	 *
	 * @param flagId the primary key of the current announcements flag
	 * @param entryId the entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next announcements flag
	 * @throws NoSuchFlagException if a announcements flag with the primary key could not be found
	 */
	@Override
	public AnnouncementsFlag[] findByEntryId_PrevAndNext(
			long flagId, long entryId,
			OrderByComparator<AnnouncementsFlag> orderByComparator)
		throws NoSuchFlagException {

		AnnouncementsFlag announcementsFlag = findByPrimaryKey(flagId);

		Session session = null;

		try {
			session = openSession();

			AnnouncementsFlag[] array = new AnnouncementsFlagImpl[3];

			array[0] = getByEntryId_PrevAndNext(
				session, announcementsFlag, entryId, orderByComparator, true);

			array[1] = announcementsFlag;

			array[2] = getByEntryId_PrevAndNext(
				session, announcementsFlag, entryId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected AnnouncementsFlag getByEntryId_PrevAndNext(
		Session session, AnnouncementsFlag announcementsFlag, long entryId,
		OrderByComparator<AnnouncementsFlag> orderByComparator,
		boolean previous) {

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_ANNOUNCEMENTSFLAG_WHERE);

		query.append(_FINDER_COLUMN_ENTRYID_ENTRYID_2);

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
			query.append(AnnouncementsFlagModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(entryId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						announcementsFlag)) {

				qPos.add(orderByConditionValue);
			}
		}

		List<AnnouncementsFlag> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the announcements flags where entryId = &#63; from the database.
	 *
	 * @param entryId the entry ID
	 */
	@Override
	public void removeByEntryId(long entryId) {
		for (AnnouncementsFlag announcementsFlag :
				findByEntryId(
					entryId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(announcementsFlag);
		}
	}

	/**
	 * Returns the number of announcements flags where entryId = &#63;.
	 *
	 * @param entryId the entry ID
	 * @return the number of matching announcements flags
	 */
	@Override
	public int countByEntryId(long entryId) {
		FinderPath finderPath = _finderPathCountByEntryId;

		Object[] finderArgs = new Object[] {entryId};

		Long count = (Long)FinderCacheUtil.getResult(
			finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_ANNOUNCEMENTSFLAG_WHERE);

			query.append(_FINDER_COLUMN_ENTRYID_ENTRYID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(entryId);

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

	private static final String _FINDER_COLUMN_ENTRYID_ENTRYID_2 =
		"announcementsFlag.entryId = ?";

	private FinderPath _finderPathFetchByU_E_V;
	private FinderPath _finderPathCountByU_E_V;

	/**
	 * Returns the announcements flag where userId = &#63; and entryId = &#63; and value = &#63; or throws a <code>NoSuchFlagException</code> if it could not be found.
	 *
	 * @param userId the user ID
	 * @param entryId the entry ID
	 * @param value the value
	 * @return the matching announcements flag
	 * @throws NoSuchFlagException if a matching announcements flag could not be found
	 */
	@Override
	public AnnouncementsFlag findByU_E_V(long userId, long entryId, int value)
		throws NoSuchFlagException {

		AnnouncementsFlag announcementsFlag = fetchByU_E_V(
			userId, entryId, value);

		if (announcementsFlag == null) {
			StringBundler msg = new StringBundler(8);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("userId=");
			msg.append(userId);

			msg.append(", entryId=");
			msg.append(entryId);

			msg.append(", value=");
			msg.append(value);

			msg.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(msg.toString());
			}

			throw new NoSuchFlagException(msg.toString());
		}

		return announcementsFlag;
	}

	/**
	 * Returns the announcements flag where userId = &#63; and entryId = &#63; and value = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param userId the user ID
	 * @param entryId the entry ID
	 * @param value the value
	 * @return the matching announcements flag, or <code>null</code> if a matching announcements flag could not be found
	 */
	@Override
	public AnnouncementsFlag fetchByU_E_V(
		long userId, long entryId, int value) {

		return fetchByU_E_V(userId, entryId, value, true);
	}

	/**
	 * Returns the announcements flag where userId = &#63; and entryId = &#63; and value = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param userId the user ID
	 * @param entryId the entry ID
	 * @param value the value
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching announcements flag, or <code>null</code> if a matching announcements flag could not be found
	 */
	@Override
	public AnnouncementsFlag fetchByU_E_V(
		long userId, long entryId, int value, boolean useFinderCache) {

		Object[] finderArgs = null;

		if (useFinderCache) {
			finderArgs = new Object[] {userId, entryId, value};
		}

		Object result = null;

		if (useFinderCache) {
			result = FinderCacheUtil.getResult(
				_finderPathFetchByU_E_V, finderArgs, this);
		}

		if (result instanceof AnnouncementsFlag) {
			AnnouncementsFlag announcementsFlag = (AnnouncementsFlag)result;

			if ((userId != announcementsFlag.getUserId()) ||
				(entryId != announcementsFlag.getEntryId()) ||
				(value != announcementsFlag.getValue())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(5);

			query.append(_SQL_SELECT_ANNOUNCEMENTSFLAG_WHERE);

			query.append(_FINDER_COLUMN_U_E_V_USERID_2);

			query.append(_FINDER_COLUMN_U_E_V_ENTRYID_2);

			query.append(_FINDER_COLUMN_U_E_V_VALUE_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId);

				qPos.add(entryId);

				qPos.add(value);

				List<AnnouncementsFlag> list = q.list();

				if (list.isEmpty()) {
					if (useFinderCache) {
						FinderCacheUtil.putResult(
							_finderPathFetchByU_E_V, finderArgs, list);
					}
				}
				else {
					AnnouncementsFlag announcementsFlag = list.get(0);

					result = announcementsFlag;

					cacheResult(announcementsFlag);
				}
			}
			catch (Exception e) {
				if (useFinderCache) {
					FinderCacheUtil.removeResult(
						_finderPathFetchByU_E_V, finderArgs);
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
			return (AnnouncementsFlag)result;
		}
	}

	/**
	 * Removes the announcements flag where userId = &#63; and entryId = &#63; and value = &#63; from the database.
	 *
	 * @param userId the user ID
	 * @param entryId the entry ID
	 * @param value the value
	 * @return the announcements flag that was removed
	 */
	@Override
	public AnnouncementsFlag removeByU_E_V(long userId, long entryId, int value)
		throws NoSuchFlagException {

		AnnouncementsFlag announcementsFlag = findByU_E_V(
			userId, entryId, value);

		return remove(announcementsFlag);
	}

	/**
	 * Returns the number of announcements flags where userId = &#63; and entryId = &#63; and value = &#63;.
	 *
	 * @param userId the user ID
	 * @param entryId the entry ID
	 * @param value the value
	 * @return the number of matching announcements flags
	 */
	@Override
	public int countByU_E_V(long userId, long entryId, int value) {
		FinderPath finderPath = _finderPathCountByU_E_V;

		Object[] finderArgs = new Object[] {userId, entryId, value};

		Long count = (Long)FinderCacheUtil.getResult(
			finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_COUNT_ANNOUNCEMENTSFLAG_WHERE);

			query.append(_FINDER_COLUMN_U_E_V_USERID_2);

			query.append(_FINDER_COLUMN_U_E_V_ENTRYID_2);

			query.append(_FINDER_COLUMN_U_E_V_VALUE_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId);

				qPos.add(entryId);

				qPos.add(value);

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

	private static final String _FINDER_COLUMN_U_E_V_USERID_2 =
		"announcementsFlag.userId = ? AND ";

	private static final String _FINDER_COLUMN_U_E_V_ENTRYID_2 =
		"announcementsFlag.entryId = ? AND ";

	private static final String _FINDER_COLUMN_U_E_V_VALUE_2 =
		"announcementsFlag.value = ?";

	public AnnouncementsFlagPersistenceImpl() {
		setModelClass(AnnouncementsFlag.class);

		setModelImplClass(AnnouncementsFlagImpl.class);
		setModelPKClass(long.class);
		setEntityCacheEnabled(AnnouncementsFlagModelImpl.ENTITY_CACHE_ENABLED);
	}

	/**
	 * Caches the announcements flag in the entity cache if it is enabled.
	 *
	 * @param announcementsFlag the announcements flag
	 */
	@Override
	public void cacheResult(AnnouncementsFlag announcementsFlag) {
		EntityCacheUtil.putResult(
			AnnouncementsFlagModelImpl.ENTITY_CACHE_ENABLED,
			AnnouncementsFlagImpl.class, announcementsFlag.getPrimaryKey(),
			announcementsFlag);

		FinderCacheUtil.putResult(
			_finderPathFetchByU_E_V,
			new Object[] {
				announcementsFlag.getUserId(), announcementsFlag.getEntryId(),
				announcementsFlag.getValue()
			},
			announcementsFlag);

		announcementsFlag.resetOriginalValues();
	}

	/**
	 * Caches the announcements flags in the entity cache if it is enabled.
	 *
	 * @param announcementsFlags the announcements flags
	 */
	@Override
	public void cacheResult(List<AnnouncementsFlag> announcementsFlags) {
		for (AnnouncementsFlag announcementsFlag : announcementsFlags) {
			if (EntityCacheUtil.getResult(
					AnnouncementsFlagModelImpl.ENTITY_CACHE_ENABLED,
					AnnouncementsFlagImpl.class,
					announcementsFlag.getPrimaryKey()) == null) {

				cacheResult(announcementsFlag);
			}
			else {
				announcementsFlag.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all announcements flags.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>com.liferay.portal.kernel.dao.orm.FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		EntityCacheUtil.clearCache(AnnouncementsFlagImpl.class);

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the announcements flag.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>com.liferay.portal.kernel.dao.orm.FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(AnnouncementsFlag announcementsFlag) {
		EntityCacheUtil.removeResult(
			AnnouncementsFlagModelImpl.ENTITY_CACHE_ENABLED,
			AnnouncementsFlagImpl.class, announcementsFlag.getPrimaryKey());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache(
			(AnnouncementsFlagModelImpl)announcementsFlag, true);
	}

	@Override
	public void clearCache(List<AnnouncementsFlag> announcementsFlags) {
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (AnnouncementsFlag announcementsFlag : announcementsFlags) {
			EntityCacheUtil.removeResult(
				AnnouncementsFlagModelImpl.ENTITY_CACHE_ENABLED,
				AnnouncementsFlagImpl.class, announcementsFlag.getPrimaryKey());

			clearUniqueFindersCache(
				(AnnouncementsFlagModelImpl)announcementsFlag, true);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (Serializable primaryKey : primaryKeys) {
			EntityCacheUtil.removeResult(
				AnnouncementsFlagModelImpl.ENTITY_CACHE_ENABLED,
				AnnouncementsFlagImpl.class, primaryKey);
		}
	}

	protected void cacheUniqueFindersCache(
		AnnouncementsFlagModelImpl announcementsFlagModelImpl) {

		Object[] args = new Object[] {
			announcementsFlagModelImpl.getUserId(),
			announcementsFlagModelImpl.getEntryId(),
			announcementsFlagModelImpl.getValue()
		};

		FinderCacheUtil.putResult(
			_finderPathCountByU_E_V, args, Long.valueOf(1), false);
		FinderCacheUtil.putResult(
			_finderPathFetchByU_E_V, args, announcementsFlagModelImpl, false);
	}

	protected void clearUniqueFindersCache(
		AnnouncementsFlagModelImpl announcementsFlagModelImpl,
		boolean clearCurrent) {

		if (clearCurrent) {
			Object[] args = new Object[] {
				announcementsFlagModelImpl.getUserId(),
				announcementsFlagModelImpl.getEntryId(),
				announcementsFlagModelImpl.getValue()
			};

			FinderCacheUtil.removeResult(_finderPathCountByU_E_V, args);
			FinderCacheUtil.removeResult(_finderPathFetchByU_E_V, args);
		}

		if ((announcementsFlagModelImpl.getColumnBitmask() &
			 _finderPathFetchByU_E_V.getColumnBitmask()) != 0) {

			Object[] args = new Object[] {
				announcementsFlagModelImpl.getOriginalUserId(),
				announcementsFlagModelImpl.getOriginalEntryId(),
				announcementsFlagModelImpl.getOriginalValue()
			};

			FinderCacheUtil.removeResult(_finderPathCountByU_E_V, args);
			FinderCacheUtil.removeResult(_finderPathFetchByU_E_V, args);
		}
	}

	/**
	 * Creates a new announcements flag with the primary key. Does not add the announcements flag to the database.
	 *
	 * @param flagId the primary key for the new announcements flag
	 * @return the new announcements flag
	 */
	@Override
	public AnnouncementsFlag create(long flagId) {
		AnnouncementsFlag announcementsFlag = new AnnouncementsFlagImpl();

		announcementsFlag.setNew(true);
		announcementsFlag.setPrimaryKey(flagId);

		announcementsFlag.setCompanyId(CompanyThreadLocal.getCompanyId());

		return announcementsFlag;
	}

	/**
	 * Removes the announcements flag with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param flagId the primary key of the announcements flag
	 * @return the announcements flag that was removed
	 * @throws NoSuchFlagException if a announcements flag with the primary key could not be found
	 */
	@Override
	public AnnouncementsFlag remove(long flagId) throws NoSuchFlagException {
		return remove((Serializable)flagId);
	}

	/**
	 * Removes the announcements flag with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the announcements flag
	 * @return the announcements flag that was removed
	 * @throws NoSuchFlagException if a announcements flag with the primary key could not be found
	 */
	@Override
	public AnnouncementsFlag remove(Serializable primaryKey)
		throws NoSuchFlagException {

		Session session = null;

		try {
			session = openSession();

			AnnouncementsFlag announcementsFlag =
				(AnnouncementsFlag)session.get(
					AnnouncementsFlagImpl.class, primaryKey);

			if (announcementsFlag == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchFlagException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(announcementsFlag);
		}
		catch (NoSuchFlagException nsee) {
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
	protected AnnouncementsFlag removeImpl(
		AnnouncementsFlag announcementsFlag) {

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(announcementsFlag)) {
				announcementsFlag = (AnnouncementsFlag)session.get(
					AnnouncementsFlagImpl.class,
					announcementsFlag.getPrimaryKeyObj());
			}

			if (announcementsFlag != null) {
				session.delete(announcementsFlag);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (announcementsFlag != null) {
			clearCache(announcementsFlag);
		}

		return announcementsFlag;
	}

	@Override
	public AnnouncementsFlag updateImpl(AnnouncementsFlag announcementsFlag) {
		boolean isNew = announcementsFlag.isNew();

		if (!(announcementsFlag instanceof AnnouncementsFlagModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(announcementsFlag.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					announcementsFlag);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in announcementsFlag proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom AnnouncementsFlag implementation " +
					announcementsFlag.getClass());
		}

		AnnouncementsFlagModelImpl announcementsFlagModelImpl =
			(AnnouncementsFlagModelImpl)announcementsFlag;

		Session session = null;

		try {
			session = openSession();

			if (announcementsFlag.isNew()) {
				session.save(announcementsFlag);

				announcementsFlag.setNew(false);
			}
			else {
				announcementsFlag = (AnnouncementsFlag)session.merge(
					announcementsFlag);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (!AnnouncementsFlagModelImpl.COLUMN_BITMASK_ENABLED) {
			FinderCacheUtil.clearCache(
				FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}
		else if (isNew) {
			Object[] args = new Object[] {
				announcementsFlagModelImpl.getCompanyId()
			};

			FinderCacheUtil.removeResult(_finderPathCountByCompanyId, args);
			FinderCacheUtil.removeResult(
				_finderPathWithoutPaginationFindByCompanyId, args);

			args = new Object[] {announcementsFlagModelImpl.getEntryId()};

			FinderCacheUtil.removeResult(_finderPathCountByEntryId, args);
			FinderCacheUtil.removeResult(
				_finderPathWithoutPaginationFindByEntryId, args);

			FinderCacheUtil.removeResult(
				_finderPathCountAll, FINDER_ARGS_EMPTY);
			FinderCacheUtil.removeResult(
				_finderPathWithoutPaginationFindAll, FINDER_ARGS_EMPTY);
		}
		else {
			if ((announcementsFlagModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByCompanyId.
					 getColumnBitmask()) != 0) {

				Object[] args = new Object[] {
					announcementsFlagModelImpl.getOriginalCompanyId()
				};

				FinderCacheUtil.removeResult(_finderPathCountByCompanyId, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByCompanyId, args);

				args = new Object[] {announcementsFlagModelImpl.getCompanyId()};

				FinderCacheUtil.removeResult(_finderPathCountByCompanyId, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByCompanyId, args);
			}

			if ((announcementsFlagModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByEntryId.
					 getColumnBitmask()) != 0) {

				Object[] args = new Object[] {
					announcementsFlagModelImpl.getOriginalEntryId()
				};

				FinderCacheUtil.removeResult(_finderPathCountByEntryId, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByEntryId, args);

				args = new Object[] {announcementsFlagModelImpl.getEntryId()};

				FinderCacheUtil.removeResult(_finderPathCountByEntryId, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByEntryId, args);
			}
		}

		EntityCacheUtil.putResult(
			AnnouncementsFlagModelImpl.ENTITY_CACHE_ENABLED,
			AnnouncementsFlagImpl.class, announcementsFlag.getPrimaryKey(),
			announcementsFlag, false);

		clearUniqueFindersCache(announcementsFlagModelImpl, false);
		cacheUniqueFindersCache(announcementsFlagModelImpl);

		announcementsFlag.resetOriginalValues();

		return announcementsFlag;
	}

	/**
	 * Returns the announcements flag with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the announcements flag
	 * @return the announcements flag
	 * @throws NoSuchFlagException if a announcements flag with the primary key could not be found
	 */
	@Override
	public AnnouncementsFlag findByPrimaryKey(Serializable primaryKey)
		throws NoSuchFlagException {

		AnnouncementsFlag announcementsFlag = fetchByPrimaryKey(primaryKey);

		if (announcementsFlag == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchFlagException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return announcementsFlag;
	}

	/**
	 * Returns the announcements flag with the primary key or throws a <code>NoSuchFlagException</code> if it could not be found.
	 *
	 * @param flagId the primary key of the announcements flag
	 * @return the announcements flag
	 * @throws NoSuchFlagException if a announcements flag with the primary key could not be found
	 */
	@Override
	public AnnouncementsFlag findByPrimaryKey(long flagId)
		throws NoSuchFlagException {

		return findByPrimaryKey((Serializable)flagId);
	}

	/**
	 * Returns the announcements flag with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param flagId the primary key of the announcements flag
	 * @return the announcements flag, or <code>null</code> if a announcements flag with the primary key could not be found
	 */
	@Override
	public AnnouncementsFlag fetchByPrimaryKey(long flagId) {
		return fetchByPrimaryKey((Serializable)flagId);
	}

	/**
	 * Returns all the announcements flags.
	 *
	 * @return the announcements flags
	 */
	@Override
	public List<AnnouncementsFlag> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the announcements flags.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AnnouncementsFlagModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of announcements flags
	 * @param end the upper bound of the range of announcements flags (not inclusive)
	 * @return the range of announcements flags
	 */
	@Override
	public List<AnnouncementsFlag> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the announcements flags.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AnnouncementsFlagModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of announcements flags
	 * @param end the upper bound of the range of announcements flags (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of announcements flags
	 */
	@Override
	public List<AnnouncementsFlag> findAll(
		int start, int end,
		OrderByComparator<AnnouncementsFlag> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the announcements flags.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>AnnouncementsFlagModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of announcements flags
	 * @param end the upper bound of the range of announcements flags (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of announcements flags
	 */
	@Override
	public List<AnnouncementsFlag> findAll(
		int start, int end,
		OrderByComparator<AnnouncementsFlag> orderByComparator,
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

		List<AnnouncementsFlag> list = null;

		if (useFinderCache) {
			list = (List<AnnouncementsFlag>)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);
		}

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				query.append(_SQL_SELECT_ANNOUNCEMENTSFLAG);

				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_ANNOUNCEMENTSFLAG;

				sql = sql.concat(AnnouncementsFlagModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				list = (List<AnnouncementsFlag>)QueryUtil.list(
					q, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					FinderCacheUtil.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception e) {
				if (useFinderCache) {
					FinderCacheUtil.removeResult(finderPath, finderArgs);
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
	 * Removes all the announcements flags from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (AnnouncementsFlag announcementsFlag : findAll()) {
			remove(announcementsFlag);
		}
	}

	/**
	 * Returns the number of announcements flags.
	 *
	 * @return the number of announcements flags
	 */
	@Override
	public int countAll() {
		Long count = (Long)FinderCacheUtil.getResult(
			_finderPathCountAll, FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_ANNOUNCEMENTSFLAG);

				count = (Long)q.uniqueResult();

				FinderCacheUtil.putResult(
					_finderPathCountAll, FINDER_ARGS_EMPTY, count);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(
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
	protected EntityCache getEntityCache() {
		return EntityCacheUtil.getEntityCache();
	}

	@Override
	protected String getPKDBName() {
		return "flagId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_ANNOUNCEMENTSFLAG;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return AnnouncementsFlagModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the announcements flag persistence.
	 */
	public void afterPropertiesSet() {
		_finderPathWithPaginationFindAll = new FinderPath(
			AnnouncementsFlagModelImpl.ENTITY_CACHE_ENABLED,
			AnnouncementsFlagModelImpl.FINDER_CACHE_ENABLED,
			AnnouncementsFlagImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findAll", new String[0]);

		_finderPathWithoutPaginationFindAll = new FinderPath(
			AnnouncementsFlagModelImpl.ENTITY_CACHE_ENABLED,
			AnnouncementsFlagModelImpl.FINDER_CACHE_ENABLED,
			AnnouncementsFlagImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll",
			new String[0]);

		_finderPathCountAll = new FinderPath(
			AnnouncementsFlagModelImpl.ENTITY_CACHE_ENABLED,
			AnnouncementsFlagModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll",
			new String[0]);

		_finderPathWithPaginationFindByCompanyId = new FinderPath(
			AnnouncementsFlagModelImpl.ENTITY_CACHE_ENABLED,
			AnnouncementsFlagModelImpl.FINDER_CACHE_ENABLED,
			AnnouncementsFlagImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByCompanyId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByCompanyId = new FinderPath(
			AnnouncementsFlagModelImpl.ENTITY_CACHE_ENABLED,
			AnnouncementsFlagModelImpl.FINDER_CACHE_ENABLED,
			AnnouncementsFlagImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByCompanyId",
			new String[] {Long.class.getName()},
			AnnouncementsFlagModelImpl.COMPANYID_COLUMN_BITMASK |
			AnnouncementsFlagModelImpl.USERID_COLUMN_BITMASK |
			AnnouncementsFlagModelImpl.CREATEDATE_COLUMN_BITMASK);

		_finderPathCountByCompanyId = new FinderPath(
			AnnouncementsFlagModelImpl.ENTITY_CACHE_ENABLED,
			AnnouncementsFlagModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByCompanyId",
			new String[] {Long.class.getName()});

		_finderPathWithPaginationFindByEntryId = new FinderPath(
			AnnouncementsFlagModelImpl.ENTITY_CACHE_ENABLED,
			AnnouncementsFlagModelImpl.FINDER_CACHE_ENABLED,
			AnnouncementsFlagImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByEntryId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByEntryId = new FinderPath(
			AnnouncementsFlagModelImpl.ENTITY_CACHE_ENABLED,
			AnnouncementsFlagModelImpl.FINDER_CACHE_ENABLED,
			AnnouncementsFlagImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByEntryId",
			new String[] {Long.class.getName()},
			AnnouncementsFlagModelImpl.ENTRYID_COLUMN_BITMASK |
			AnnouncementsFlagModelImpl.USERID_COLUMN_BITMASK |
			AnnouncementsFlagModelImpl.CREATEDATE_COLUMN_BITMASK);

		_finderPathCountByEntryId = new FinderPath(
			AnnouncementsFlagModelImpl.ENTITY_CACHE_ENABLED,
			AnnouncementsFlagModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByEntryId",
			new String[] {Long.class.getName()});

		_finderPathFetchByU_E_V = new FinderPath(
			AnnouncementsFlagModelImpl.ENTITY_CACHE_ENABLED,
			AnnouncementsFlagModelImpl.FINDER_CACHE_ENABLED,
			AnnouncementsFlagImpl.class, FINDER_CLASS_NAME_ENTITY,
			"fetchByU_E_V",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName()
			},
			AnnouncementsFlagModelImpl.USERID_COLUMN_BITMASK |
			AnnouncementsFlagModelImpl.ENTRYID_COLUMN_BITMASK |
			AnnouncementsFlagModelImpl.VALUE_COLUMN_BITMASK);

		_finderPathCountByU_E_V = new FinderPath(
			AnnouncementsFlagModelImpl.ENTITY_CACHE_ENABLED,
			AnnouncementsFlagModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByU_E_V",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName()
			});
	}

	public void destroy() {
		EntityCacheUtil.removeCache(AnnouncementsFlagImpl.class.getName());
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	private static final String _SQL_SELECT_ANNOUNCEMENTSFLAG =
		"SELECT announcementsFlag FROM AnnouncementsFlag announcementsFlag";

	private static final String _SQL_SELECT_ANNOUNCEMENTSFLAG_WHERE =
		"SELECT announcementsFlag FROM AnnouncementsFlag announcementsFlag WHERE ";

	private static final String _SQL_COUNT_ANNOUNCEMENTSFLAG =
		"SELECT COUNT(announcementsFlag) FROM AnnouncementsFlag announcementsFlag";

	private static final String _SQL_COUNT_ANNOUNCEMENTSFLAG_WHERE =
		"SELECT COUNT(announcementsFlag) FROM AnnouncementsFlag announcementsFlag WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS = "announcementsFlag.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No AnnouncementsFlag exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No AnnouncementsFlag exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		AnnouncementsFlagPersistenceImpl.class);

}