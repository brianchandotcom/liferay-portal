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
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.CacheModel;
import com.liferay.portal.service.persistence.CompanyProvider;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;

import com.liferay.portlet.announcements.NoSuchFlagException;
import com.liferay.portlet.announcements.model.AnnouncementsFlag;
import com.liferay.portlet.announcements.model.impl.AnnouncementsFlagImpl;
import com.liferay.portlet.announcements.model.impl.AnnouncementsFlagModelImpl;
import com.liferay.portlet.announcements.service.persistence.AnnouncementsFlagPersistence;

import java.io.Serializable;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
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
 * @see AnnouncementsFlagPersistence
 * @see com.liferay.portlet.announcements.service.persistence.AnnouncementsFlagUtil
 * @generated
 */
@ProviderType
public class AnnouncementsFlagPersistenceImpl extends BasePersistenceImpl<AnnouncementsFlag>
	implements AnnouncementsFlagPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link AnnouncementsFlagUtil} to access the announcements flag persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = AnnouncementsFlagImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(AnnouncementsFlagModelImpl.ENTITY_CACHE_ENABLED,
			AnnouncementsFlagModelImpl.FINDER_CACHE_ENABLED,
			AnnouncementsFlagImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(AnnouncementsFlagModelImpl.ENTITY_CACHE_ENABLED,
			AnnouncementsFlagModelImpl.FINDER_CACHE_ENABLED,
			AnnouncementsFlagImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(AnnouncementsFlagModelImpl.ENTITY_CACHE_ENABLED,
			AnnouncementsFlagModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_ENTRYID = new FinderPath(AnnouncementsFlagModelImpl.ENTITY_CACHE_ENABLED,
			AnnouncementsFlagModelImpl.FINDER_CACHE_ENABLED,
			AnnouncementsFlagImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByEntryId",
			new String[] {
				Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_ENTRYID =
		new FinderPath(AnnouncementsFlagModelImpl.ENTITY_CACHE_ENABLED,
			AnnouncementsFlagModelImpl.FINDER_CACHE_ENABLED,
			AnnouncementsFlagImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByEntryId",
			new String[] { Long.class.getName() },
			AnnouncementsFlagModelImpl.ENTRYID_COLUMN_BITMASK |
			AnnouncementsFlagModelImpl.USERID_COLUMN_BITMASK |
			AnnouncementsFlagModelImpl.CREATEDATE_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_ENTRYID = new FinderPath(AnnouncementsFlagModelImpl.ENTITY_CACHE_ENABLED,
			AnnouncementsFlagModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByEntryId",
			new String[] { Long.class.getName() });

	/**
	 * Returns all the announcements flags where entryId = &#63;.
	 *
	 * @param entryId the entry ID
	 * @return the matching announcements flags
	 */
	@Override
	public List<AnnouncementsFlag> findByEntryId(long entryId) {
		return findByEntryId(entryId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the announcements flags where entryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AnnouncementsFlagModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param entryId the entry ID
	 * @param start the lower bound of the range of announcements flags
	 * @param end the upper bound of the range of announcements flags (not inclusive)
	 * @return the range of matching announcements flags
	 */
	@Override
	public List<AnnouncementsFlag> findByEntryId(long entryId, int start,
		int end) {
		return findByEntryId(entryId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the announcements flags where entryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AnnouncementsFlagModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param entryId the entry ID
	 * @param start the lower bound of the range of announcements flags
	 * @param end the upper bound of the range of announcements flags (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching announcements flags
	 */
	@Override
	public List<AnnouncementsFlag> findByEntryId(long entryId, int start,
		int end, OrderByComparator<AnnouncementsFlag> orderByComparator) {
		return findByEntryId(entryId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the announcements flags where entryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AnnouncementsFlagModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param entryId the entry ID
	 * @param start the lower bound of the range of announcements flags
	 * @param end the upper bound of the range of announcements flags (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching announcements flags
	 */
	@Override
	public List<AnnouncementsFlag> findByEntryId(long entryId, int start,
		int end, OrderByComparator<AnnouncementsFlag> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_ENTRYID;
			finderArgs = new Object[] { entryId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_ENTRYID;
			finderArgs = new Object[] { entryId, start, end, orderByComparator };
		}

		List<AnnouncementsFlag> list = null;

		if (retrieveFromCache) {
			list = (List<AnnouncementsFlag>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (AnnouncementsFlag announcementsFlag : list) {
					if ((entryId != announcementsFlag.getEntryId())) {
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

			query.append(_SQL_SELECT_ANNOUNCEMENTSFLAG_WHERE);

			query.append(_FINDER_COLUMN_ENTRYID_ENTRYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(AnnouncementsFlagModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(entryId);

				if (!pagination) {
					list = (List<AnnouncementsFlag>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<AnnouncementsFlag>)QueryUtil.list(q,
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
	 * Returns the first announcements flag in the ordered set where entryId = &#63;.
	 *
	 * @param entryId the entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching announcements flag
	 * @throws NoSuchFlagException if a matching announcements flag could not be found
	 */
	@Override
	public AnnouncementsFlag findByEntryId_First(long entryId,
		OrderByComparator<AnnouncementsFlag> orderByComparator)
		throws NoSuchFlagException {
		AnnouncementsFlag announcementsFlag = fetchByEntryId_First(entryId,
				orderByComparator);

		if (announcementsFlag != null) {
			return announcementsFlag;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("entryId=");
		msg.append(entryId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

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
	public AnnouncementsFlag fetchByEntryId_First(long entryId,
		OrderByComparator<AnnouncementsFlag> orderByComparator) {
		List<AnnouncementsFlag> list = findByEntryId(entryId, 0, 1,
				orderByComparator);

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
	public AnnouncementsFlag findByEntryId_Last(long entryId,
		OrderByComparator<AnnouncementsFlag> orderByComparator)
		throws NoSuchFlagException {
		AnnouncementsFlag announcementsFlag = fetchByEntryId_Last(entryId,
				orderByComparator);

		if (announcementsFlag != null) {
			return announcementsFlag;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("entryId=");
		msg.append(entryId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

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
	public AnnouncementsFlag fetchByEntryId_Last(long entryId,
		OrderByComparator<AnnouncementsFlag> orderByComparator) {
		int count = countByEntryId(entryId);

		if (count == 0) {
			return null;
		}

		List<AnnouncementsFlag> list = findByEntryId(entryId, count - 1, count,
				orderByComparator);

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
	public AnnouncementsFlag[] findByEntryId_PrevAndNext(long flagId,
		long entryId, OrderByComparator<AnnouncementsFlag> orderByComparator)
		throws NoSuchFlagException {
		AnnouncementsFlag announcementsFlag = findByPrimaryKey(flagId);

		Session session = null;

		try {
			session = openSession();

			AnnouncementsFlag[] array = new AnnouncementsFlagImpl[3];

			array[0] = getByEntryId_PrevAndNext(session, announcementsFlag,
					entryId, orderByComparator, true);

			array[1] = announcementsFlag;

			array[2] = getByEntryId_PrevAndNext(session, announcementsFlag,
					entryId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected AnnouncementsFlag getByEntryId_PrevAndNext(Session session,
		AnnouncementsFlag announcementsFlag, long entryId,
		OrderByComparator<AnnouncementsFlag> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_ANNOUNCEMENTSFLAG_WHERE);

		query.append(_FINDER_COLUMN_ENTRYID_ENTRYID_2);

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
			query.append(AnnouncementsFlagModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(entryId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(announcementsFlag);

			for (Object value : values) {
				qPos.add(value);
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
		for (AnnouncementsFlag announcementsFlag : findByEntryId(entryId,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
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
		FinderPath finderPath = FINDER_PATH_COUNT_BY_ENTRYID;

		Object[] finderArgs = new Object[] { entryId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

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

	private static final String _FINDER_COLUMN_ENTRYID_ENTRYID_2 = "announcementsFlag.entryId = ?";
	public static final FinderPath FINDER_PATH_FETCH_BY_U_E_V = new FinderPath(AnnouncementsFlagModelImpl.ENTITY_CACHE_ENABLED,
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
	public static final FinderPath FINDER_PATH_COUNT_BY_U_E_V = new FinderPath(AnnouncementsFlagModelImpl.ENTITY_CACHE_ENABLED,
			AnnouncementsFlagModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByU_E_V",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName()
			});

	/**
	 * Returns the announcements flag where userId = &#63; and entryId = &#63; and value = &#63; or throws a {@link NoSuchFlagException} if it could not be found.
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
		AnnouncementsFlag announcementsFlag = fetchByU_E_V(userId, entryId,
				value);

		if (announcementsFlag == null) {
			StringBundler msg = new StringBundler(8);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("userId=");
			msg.append(userId);

			msg.append(", entryId=");
			msg.append(entryId);

			msg.append(", value=");
			msg.append(value);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
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
	public AnnouncementsFlag fetchByU_E_V(long userId, long entryId, int value) {
		return fetchByU_E_V(userId, entryId, value, true);
	}

	/**
	 * Returns the announcements flag where userId = &#63; and entryId = &#63; and value = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param userId the user ID
	 * @param entryId the entry ID
	 * @param value the value
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the matching announcements flag, or <code>null</code> if a matching announcements flag could not be found
	 */
	@Override
	public AnnouncementsFlag fetchByU_E_V(long userId, long entryId, int value,
		boolean retrieveFromCache) {
		Object[] finderArgs = new Object[] { userId, entryId, value };

		Object result = null;

		if (retrieveFromCache) {
			result = finderCache.getResult(FINDER_PATH_FETCH_BY_U_E_V,
					finderArgs, this);
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
					finderCache.putResult(FINDER_PATH_FETCH_BY_U_E_V,
						finderArgs, list);
				}
				else {
					AnnouncementsFlag announcementsFlag = list.get(0);

					result = announcementsFlag;

					cacheResult(announcementsFlag);

					if ((announcementsFlag.getUserId() != userId) ||
							(announcementsFlag.getEntryId() != entryId) ||
							(announcementsFlag.getValue() != value)) {
						finderCache.putResult(FINDER_PATH_FETCH_BY_U_E_V,
							finderArgs, announcementsFlag);
					}
				}
			}
			catch (Exception e) {
				finderCache.removeResult(FINDER_PATH_FETCH_BY_U_E_V, finderArgs);

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
		AnnouncementsFlag announcementsFlag = findByU_E_V(userId, entryId, value);

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
		FinderPath finderPath = FINDER_PATH_COUNT_BY_U_E_V;

		Object[] finderArgs = new Object[] { userId, entryId, value };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

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

	private static final String _FINDER_COLUMN_U_E_V_USERID_2 = "announcementsFlag.userId = ? AND ";
	private static final String _FINDER_COLUMN_U_E_V_ENTRYID_2 = "announcementsFlag.entryId = ? AND ";
	private static final String _FINDER_COLUMN_U_E_V_VALUE_2 = "announcementsFlag.value = ?";

	public AnnouncementsFlagPersistenceImpl() {
		setModelClass(AnnouncementsFlag.class);
	}

	/**
	 * Caches the announcements flag in the entity cache if it is enabled.
	 *
	 * @param announcementsFlag the announcements flag
	 */
	@Override
	public void cacheResult(AnnouncementsFlag announcementsFlag) {
		entityCache.putResult(AnnouncementsFlagModelImpl.ENTITY_CACHE_ENABLED,
			AnnouncementsFlagImpl.class, announcementsFlag.getPrimaryKey(),
			announcementsFlag);

		finderCache.putResult(FINDER_PATH_FETCH_BY_U_E_V,
			new Object[] {
				announcementsFlag.getUserId(), announcementsFlag.getEntryId(),
				announcementsFlag.getValue()
			}, announcementsFlag);

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
			if (entityCache.getResult(
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
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(AnnouncementsFlagImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the announcements flag.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(AnnouncementsFlag announcementsFlag) {
		entityCache.removeResult(AnnouncementsFlagModelImpl.ENTITY_CACHE_ENABLED,
			AnnouncementsFlagImpl.class, announcementsFlag.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache((AnnouncementsFlagModelImpl)announcementsFlag);
	}

	@Override
	public void clearCache(List<AnnouncementsFlag> announcementsFlags) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (AnnouncementsFlag announcementsFlag : announcementsFlags) {
			entityCache.removeResult(AnnouncementsFlagModelImpl.ENTITY_CACHE_ENABLED,
				AnnouncementsFlagImpl.class, announcementsFlag.getPrimaryKey());

			clearUniqueFindersCache((AnnouncementsFlagModelImpl)announcementsFlag);
		}
	}

	protected void cacheUniqueFindersCache(
		AnnouncementsFlagModelImpl announcementsFlagModelImpl, boolean isNew) {
		if (isNew) {
			Object[] args = new Object[] {
					announcementsFlagModelImpl.getUserId(),
					announcementsFlagModelImpl.getEntryId(),
					announcementsFlagModelImpl.getValue()
				};

			finderCache.putResult(FINDER_PATH_COUNT_BY_U_E_V, args,
				Long.valueOf(1));
			finderCache.putResult(FINDER_PATH_FETCH_BY_U_E_V, args,
				announcementsFlagModelImpl);
		}
		else {
			if ((announcementsFlagModelImpl.getColumnBitmask() &
					FINDER_PATH_FETCH_BY_U_E_V.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						announcementsFlagModelImpl.getUserId(),
						announcementsFlagModelImpl.getEntryId(),
						announcementsFlagModelImpl.getValue()
					};

				finderCache.putResult(FINDER_PATH_COUNT_BY_U_E_V, args,
					Long.valueOf(1));
				finderCache.putResult(FINDER_PATH_FETCH_BY_U_E_V, args,
					announcementsFlagModelImpl);
			}
		}
	}

	protected void clearUniqueFindersCache(
		AnnouncementsFlagModelImpl announcementsFlagModelImpl) {
		Object[] args = new Object[] {
				announcementsFlagModelImpl.getUserId(),
				announcementsFlagModelImpl.getEntryId(),
				announcementsFlagModelImpl.getValue()
			};

		finderCache.removeResult(FINDER_PATH_COUNT_BY_U_E_V, args);
		finderCache.removeResult(FINDER_PATH_FETCH_BY_U_E_V, args);

		if ((announcementsFlagModelImpl.getColumnBitmask() &
				FINDER_PATH_FETCH_BY_U_E_V.getColumnBitmask()) != 0) {
			args = new Object[] {
					announcementsFlagModelImpl.getOriginalUserId(),
					announcementsFlagModelImpl.getOriginalEntryId(),
					announcementsFlagModelImpl.getOriginalValue()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_U_E_V, args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_U_E_V, args);
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

			AnnouncementsFlag announcementsFlag = (AnnouncementsFlag)session.get(AnnouncementsFlagImpl.class,
					primaryKey);

			if (announcementsFlag == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchFlagException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
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
	protected AnnouncementsFlag removeImpl(AnnouncementsFlag announcementsFlag) {
		announcementsFlag = toUnwrappedModel(announcementsFlag);

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(announcementsFlag)) {
				announcementsFlag = (AnnouncementsFlag)session.get(AnnouncementsFlagImpl.class,
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
		announcementsFlag = toUnwrappedModel(announcementsFlag);

		boolean isNew = announcementsFlag.isNew();

		AnnouncementsFlagModelImpl announcementsFlagModelImpl = (AnnouncementsFlagModelImpl)announcementsFlag;

		Session session = null;

		try {
			session = openSession();

			if (announcementsFlag.isNew()) {
				session.save(announcementsFlag);

				announcementsFlag.setNew(false);
			}
			else {
				announcementsFlag = (AnnouncementsFlag)session.merge(announcementsFlag);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (isNew || !AnnouncementsFlagModelImpl.COLUMN_BITMASK_ENABLED) {
			finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}

		else {
			if ((announcementsFlagModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_ENTRYID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						announcementsFlagModelImpl.getOriginalEntryId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_ENTRYID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_ENTRYID,
					args);

				args = new Object[] { announcementsFlagModelImpl.getEntryId() };

				finderCache.removeResult(FINDER_PATH_COUNT_BY_ENTRYID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_ENTRYID,
					args);
			}
		}

		entityCache.putResult(AnnouncementsFlagModelImpl.ENTITY_CACHE_ENABLED,
			AnnouncementsFlagImpl.class, announcementsFlag.getPrimaryKey(),
			announcementsFlag, false);

		clearUniqueFindersCache(announcementsFlagModelImpl);
		cacheUniqueFindersCache(announcementsFlagModelImpl, isNew);

		announcementsFlag.resetOriginalValues();

		return announcementsFlag;
	}

	protected AnnouncementsFlag toUnwrappedModel(
		AnnouncementsFlag announcementsFlag) {
		if (announcementsFlag instanceof AnnouncementsFlagImpl) {
			return announcementsFlag;
		}

		AnnouncementsFlagImpl announcementsFlagImpl = new AnnouncementsFlagImpl();

		announcementsFlagImpl.setNew(announcementsFlag.isNew());
		announcementsFlagImpl.setPrimaryKey(announcementsFlag.getPrimaryKey());

		announcementsFlagImpl.setFlagId(announcementsFlag.getFlagId());
		announcementsFlagImpl.setCompanyId(announcementsFlag.getCompanyId());
		announcementsFlagImpl.setUserId(announcementsFlag.getUserId());
		announcementsFlagImpl.setCreateDate(announcementsFlag.getCreateDate());
		announcementsFlagImpl.setEntryId(announcementsFlag.getEntryId());
		announcementsFlagImpl.setValue(announcementsFlag.getValue());

		return announcementsFlagImpl;
	}

	/**
	 * Returns the announcements flag with the primary key or throws a {@link com.liferay.portal.NoSuchModelException} if it could not be found.
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
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchFlagException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				primaryKey);
		}

		return announcementsFlag;
	}

	/**
	 * Returns the announcements flag with the primary key or throws a {@link NoSuchFlagException} if it could not be found.
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
	 * @param primaryKey the primary key of the announcements flag
	 * @return the announcements flag, or <code>null</code> if a announcements flag with the primary key could not be found
	 */
	@Override
	public AnnouncementsFlag fetchByPrimaryKey(Serializable primaryKey) {
		AnnouncementsFlag announcementsFlag = (AnnouncementsFlag)entityCache.getResult(AnnouncementsFlagModelImpl.ENTITY_CACHE_ENABLED,
				AnnouncementsFlagImpl.class, primaryKey);

		if (announcementsFlag == _nullAnnouncementsFlag) {
			return null;
		}

		if (announcementsFlag == null) {
			Session session = null;

			try {
				session = openSession();

				announcementsFlag = (AnnouncementsFlag)session.get(AnnouncementsFlagImpl.class,
						primaryKey);

				if (announcementsFlag != null) {
					cacheResult(announcementsFlag);
				}
				else {
					entityCache.putResult(AnnouncementsFlagModelImpl.ENTITY_CACHE_ENABLED,
						AnnouncementsFlagImpl.class, primaryKey,
						_nullAnnouncementsFlag);
				}
			}
			catch (Exception e) {
				entityCache.removeResult(AnnouncementsFlagModelImpl.ENTITY_CACHE_ENABLED,
					AnnouncementsFlagImpl.class, primaryKey);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return announcementsFlag;
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

	@Override
	public Map<Serializable, AnnouncementsFlag> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {
		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, AnnouncementsFlag> map = new HashMap<Serializable, AnnouncementsFlag>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			AnnouncementsFlag announcementsFlag = fetchByPrimaryKey(primaryKey);

			if (announcementsFlag != null) {
				map.put(primaryKey, announcementsFlag);
			}

			return map;
		}

		Set<Serializable> uncachedPrimaryKeys = null;

		for (Serializable primaryKey : primaryKeys) {
			AnnouncementsFlag announcementsFlag = (AnnouncementsFlag)entityCache.getResult(AnnouncementsFlagModelImpl.ENTITY_CACHE_ENABLED,
					AnnouncementsFlagImpl.class, primaryKey);

			if (announcementsFlag == null) {
				if (uncachedPrimaryKeys == null) {
					uncachedPrimaryKeys = new HashSet<Serializable>();
				}

				uncachedPrimaryKeys.add(primaryKey);
			}
			else {
				map.put(primaryKey, announcementsFlag);
			}
		}

		if (uncachedPrimaryKeys == null) {
			return map;
		}

		StringBundler query = new StringBundler((uncachedPrimaryKeys.size() * 2) +
				1);

		query.append(_SQL_SELECT_ANNOUNCEMENTSFLAG_WHERE_PKS_IN);

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

			for (AnnouncementsFlag announcementsFlag : (List<AnnouncementsFlag>)q.list()) {
				map.put(announcementsFlag.getPrimaryKeyObj(), announcementsFlag);

				cacheResult(announcementsFlag);

				uncachedPrimaryKeys.remove(announcementsFlag.getPrimaryKeyObj());
			}

			for (Serializable primaryKey : uncachedPrimaryKeys) {
				entityCache.putResult(AnnouncementsFlagModelImpl.ENTITY_CACHE_ENABLED,
					AnnouncementsFlagImpl.class, primaryKey,
					_nullAnnouncementsFlag);
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AnnouncementsFlagModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AnnouncementsFlagModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of announcements flags
	 * @param end the upper bound of the range of announcements flags (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of announcements flags
	 */
	@Override
	public List<AnnouncementsFlag> findAll(int start, int end,
		OrderByComparator<AnnouncementsFlag> orderByComparator) {
		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the announcements flags.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AnnouncementsFlagModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of announcements flags
	 * @param end the upper bound of the range of announcements flags (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of announcements flags
	 */
	@Override
	public List<AnnouncementsFlag> findAll(int start, int end,
		OrderByComparator<AnnouncementsFlag> orderByComparator,
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

		List<AnnouncementsFlag> list = null;

		if (retrieveFromCache) {
			list = (List<AnnouncementsFlag>)finderCache.getResult(finderPath,
					finderArgs, this);
		}

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 3));

				query.append(_SQL_SELECT_ANNOUNCEMENTSFLAG);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_ANNOUNCEMENTSFLAG;

				if (pagination) {
					sql = sql.concat(AnnouncementsFlagModelImpl.ORDER_BY_JPQL);
				}
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (!pagination) {
					list = (List<AnnouncementsFlag>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<AnnouncementsFlag>)QueryUtil.list(q,
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
		Long count = (Long)finderCache.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_ANNOUNCEMENTSFLAG);

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
	protected Map<String, Integer> getTableColumnsMap() {
		return AnnouncementsFlagModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the announcements flag persistence.
	 */
	public void afterPropertiesSet() {
	}

	public void destroy() {
		entityCache.removeCache(AnnouncementsFlagImpl.class.getName());
		finderCache.removeCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@BeanReference(type = CompanyProvider.class)
	protected CompanyProvider companyProvider;
	protected EntityCache entityCache = EntityCacheUtil.getEntityCache();
	protected FinderCache finderCache = FinderCacheUtil.getFinderCache();
	private static final String _SQL_SELECT_ANNOUNCEMENTSFLAG = "SELECT announcementsFlag FROM AnnouncementsFlag announcementsFlag";
	private static final String _SQL_SELECT_ANNOUNCEMENTSFLAG_WHERE_PKS_IN = "SELECT announcementsFlag FROM AnnouncementsFlag announcementsFlag WHERE flagId IN (";
	private static final String _SQL_SELECT_ANNOUNCEMENTSFLAG_WHERE = "SELECT announcementsFlag FROM AnnouncementsFlag announcementsFlag WHERE ";
	private static final String _SQL_COUNT_ANNOUNCEMENTSFLAG = "SELECT COUNT(announcementsFlag) FROM AnnouncementsFlag announcementsFlag";
	private static final String _SQL_COUNT_ANNOUNCEMENTSFLAG_WHERE = "SELECT COUNT(announcementsFlag) FROM AnnouncementsFlag announcementsFlag WHERE ";
	private static final String _ORDER_BY_ENTITY_ALIAS = "announcementsFlag.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No AnnouncementsFlag exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No AnnouncementsFlag exists with the key {";
	private static final Log _log = LogFactoryUtil.getLog(AnnouncementsFlagPersistenceImpl.class);
	private static final AnnouncementsFlag _nullAnnouncementsFlag = new AnnouncementsFlagImpl() {
			@Override
			public Object clone() {
				return this;
			}

			@Override
			public CacheModel<AnnouncementsFlag> toCacheModel() {
				return _nullAnnouncementsFlagCacheModel;
			}
		};

	private static final CacheModel<AnnouncementsFlag> _nullAnnouncementsFlagCacheModel =
		new CacheModel<AnnouncementsFlag>() {
			@Override
			public AnnouncementsFlag toEntityModel() {
				return _nullAnnouncementsFlag;
			}
		};
}