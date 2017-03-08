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

package com.liferay.friendly.url.service.persistence.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.friendly.url.exception.NoSuchFriendlyURLEntryLocalizationException;
import com.liferay.friendly.url.model.FriendlyURLEntryLocalization;
import com.liferay.friendly.url.model.impl.FriendlyURLEntryLocalizationImpl;
import com.liferay.friendly.url.model.impl.FriendlyURLEntryLocalizationModelImpl;
import com.liferay.friendly.url.service.persistence.FriendlyURLEntryLocalizationPersistence;

import com.liferay.portal.kernel.dao.orm.EntityCache;
import com.liferay.portal.kernel.dao.orm.FinderCache;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.service.persistence.CompanyProvider;
import com.liferay.portal.kernel.service.persistence.CompanyProviderWrapper;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.spring.extender.service.ServiceReference;

import java.io.Serializable;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * The persistence implementation for the friendly url entry localization service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see FriendlyURLEntryLocalizationPersistence
 * @see com.liferay.friendly.url.service.persistence.FriendlyURLEntryLocalizationUtil
 * @generated
 */
@ProviderType
public class FriendlyURLEntryLocalizationPersistenceImpl
	extends BasePersistenceImpl<FriendlyURLEntryLocalization>
	implements FriendlyURLEntryLocalizationPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link FriendlyURLEntryLocalizationUtil} to access the friendly url entry localization persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = FriendlyURLEntryLocalizationImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(FriendlyURLEntryLocalizationModelImpl.ENTITY_CACHE_ENABLED,
			FriendlyURLEntryLocalizationModelImpl.FINDER_CACHE_ENABLED,
			FriendlyURLEntryLocalizationImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(FriendlyURLEntryLocalizationModelImpl.ENTITY_CACHE_ENABLED,
			FriendlyURLEntryLocalizationModelImpl.FINDER_CACHE_ENABLED,
			FriendlyURLEntryLocalizationImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(FriendlyURLEntryLocalizationModelImpl.ENTITY_CACHE_ENABLED,
			FriendlyURLEntryLocalizationModelImpl.FINDER_CACHE_ENABLED,
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll",
			new String[0]);
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_G_F = new FinderPath(FriendlyURLEntryLocalizationModelImpl.ENTITY_CACHE_ENABLED,
			FriendlyURLEntryLocalizationModelImpl.FINDER_CACHE_ENABLED,
			FriendlyURLEntryLocalizationImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_F",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_F = new FinderPath(FriendlyURLEntryLocalizationModelImpl.ENTITY_CACHE_ENABLED,
			FriendlyURLEntryLocalizationModelImpl.FINDER_CACHE_ENABLED,
			FriendlyURLEntryLocalizationImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByG_F",
			new String[] { Long.class.getName(), Long.class.getName() },
			FriendlyURLEntryLocalizationModelImpl.GROUPID_COLUMN_BITMASK |
			FriendlyURLEntryLocalizationModelImpl.FRIENDLYURLENTRYID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_G_F = new FinderPath(FriendlyURLEntryLocalizationModelImpl.ENTITY_CACHE_ENABLED,
			FriendlyURLEntryLocalizationModelImpl.FINDER_CACHE_ENABLED,
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByG_F",
			new String[] { Long.class.getName(), Long.class.getName() });

	/**
	 * Returns all the friendly url entry localizations where groupId = &#63; and friendlyURLEntryId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param friendlyURLEntryId the friendly url entry ID
	 * @return the matching friendly url entry localizations
	 */
	@Override
	public List<FriendlyURLEntryLocalization> findByG_F(long groupId,
		long friendlyURLEntryId) {
		return findByG_F(groupId, friendlyURLEntryId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the friendly url entry localizations where groupId = &#63; and friendlyURLEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link FriendlyURLEntryLocalizationModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param friendlyURLEntryId the friendly url entry ID
	 * @param start the lower bound of the range of friendly url entry localizations
	 * @param end the upper bound of the range of friendly url entry localizations (not inclusive)
	 * @return the range of matching friendly url entry localizations
	 */
	@Override
	public List<FriendlyURLEntryLocalization> findByG_F(long groupId,
		long friendlyURLEntryId, int start, int end) {
		return findByG_F(groupId, friendlyURLEntryId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the friendly url entry localizations where groupId = &#63; and friendlyURLEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link FriendlyURLEntryLocalizationModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param friendlyURLEntryId the friendly url entry ID
	 * @param start the lower bound of the range of friendly url entry localizations
	 * @param end the upper bound of the range of friendly url entry localizations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching friendly url entry localizations
	 */
	@Override
	public List<FriendlyURLEntryLocalization> findByG_F(long groupId,
		long friendlyURLEntryId, int start, int end,
		OrderByComparator<FriendlyURLEntryLocalization> orderByComparator) {
		return findByG_F(groupId, friendlyURLEntryId, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the friendly url entry localizations where groupId = &#63; and friendlyURLEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link FriendlyURLEntryLocalizationModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param friendlyURLEntryId the friendly url entry ID
	 * @param start the lower bound of the range of friendly url entry localizations
	 * @param end the upper bound of the range of friendly url entry localizations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching friendly url entry localizations
	 */
	@Override
	public List<FriendlyURLEntryLocalization> findByG_F(long groupId,
		long friendlyURLEntryId, int start, int end,
		OrderByComparator<FriendlyURLEntryLocalization> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_F;
			finderArgs = new Object[] { groupId, friendlyURLEntryId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_G_F;
			finderArgs = new Object[] {
					groupId, friendlyURLEntryId,
					
					start, end, orderByComparator
				};
		}

		List<FriendlyURLEntryLocalization> list = null;

		if (retrieveFromCache) {
			list = (List<FriendlyURLEntryLocalization>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (FriendlyURLEntryLocalization friendlyURLEntryLocalization : list) {
					if ((groupId != friendlyURLEntryLocalization.getGroupId()) ||
							(friendlyURLEntryId != friendlyURLEntryLocalization.getFriendlyURLEntryId())) {
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

			query.append(_SQL_SELECT_FRIENDLYURLENTRYLOCALIZATION_WHERE);

			query.append(_FINDER_COLUMN_G_F_GROUPID_2);

			query.append(_FINDER_COLUMN_G_F_FRIENDLYURLENTRYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(FriendlyURLEntryLocalizationModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(friendlyURLEntryId);

				if (!pagination) {
					list = (List<FriendlyURLEntryLocalization>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<FriendlyURLEntryLocalization>)QueryUtil.list(q,
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
	 * Returns the first friendly url entry localization in the ordered set where groupId = &#63; and friendlyURLEntryId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param friendlyURLEntryId the friendly url entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching friendly url entry localization
	 * @throws NoSuchFriendlyURLEntryLocalizationException if a matching friendly url entry localization could not be found
	 */
	@Override
	public FriendlyURLEntryLocalization findByG_F_First(long groupId,
		long friendlyURLEntryId,
		OrderByComparator<FriendlyURLEntryLocalization> orderByComparator)
		throws NoSuchFriendlyURLEntryLocalizationException {
		FriendlyURLEntryLocalization friendlyURLEntryLocalization = fetchByG_F_First(groupId,
				friendlyURLEntryId, orderByComparator);

		if (friendlyURLEntryLocalization != null) {
			return friendlyURLEntryLocalization;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", friendlyURLEntryId=");
		msg.append(friendlyURLEntryId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchFriendlyURLEntryLocalizationException(msg.toString());
	}

	/**
	 * Returns the first friendly url entry localization in the ordered set where groupId = &#63; and friendlyURLEntryId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param friendlyURLEntryId the friendly url entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching friendly url entry localization, or <code>null</code> if a matching friendly url entry localization could not be found
	 */
	@Override
	public FriendlyURLEntryLocalization fetchByG_F_First(long groupId,
		long friendlyURLEntryId,
		OrderByComparator<FriendlyURLEntryLocalization> orderByComparator) {
		List<FriendlyURLEntryLocalization> list = findByG_F(groupId,
				friendlyURLEntryId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last friendly url entry localization in the ordered set where groupId = &#63; and friendlyURLEntryId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param friendlyURLEntryId the friendly url entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching friendly url entry localization
	 * @throws NoSuchFriendlyURLEntryLocalizationException if a matching friendly url entry localization could not be found
	 */
	@Override
	public FriendlyURLEntryLocalization findByG_F_Last(long groupId,
		long friendlyURLEntryId,
		OrderByComparator<FriendlyURLEntryLocalization> orderByComparator)
		throws NoSuchFriendlyURLEntryLocalizationException {
		FriendlyURLEntryLocalization friendlyURLEntryLocalization = fetchByG_F_Last(groupId,
				friendlyURLEntryId, orderByComparator);

		if (friendlyURLEntryLocalization != null) {
			return friendlyURLEntryLocalization;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", friendlyURLEntryId=");
		msg.append(friendlyURLEntryId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchFriendlyURLEntryLocalizationException(msg.toString());
	}

	/**
	 * Returns the last friendly url entry localization in the ordered set where groupId = &#63; and friendlyURLEntryId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param friendlyURLEntryId the friendly url entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching friendly url entry localization, or <code>null</code> if a matching friendly url entry localization could not be found
	 */
	@Override
	public FriendlyURLEntryLocalization fetchByG_F_Last(long groupId,
		long friendlyURLEntryId,
		OrderByComparator<FriendlyURLEntryLocalization> orderByComparator) {
		int count = countByG_F(groupId, friendlyURLEntryId);

		if (count == 0) {
			return null;
		}

		List<FriendlyURLEntryLocalization> list = findByG_F(groupId,
				friendlyURLEntryId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the friendly url entry localizations before and after the current friendly url entry localization in the ordered set where groupId = &#63; and friendlyURLEntryId = &#63;.
	 *
	 * @param friendlyURLEntryLocalizationId the primary key of the current friendly url entry localization
	 * @param groupId the group ID
	 * @param friendlyURLEntryId the friendly url entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next friendly url entry localization
	 * @throws NoSuchFriendlyURLEntryLocalizationException if a friendly url entry localization with the primary key could not be found
	 */
	@Override
	public FriendlyURLEntryLocalization[] findByG_F_PrevAndNext(
		long friendlyURLEntryLocalizationId, long groupId,
		long friendlyURLEntryId,
		OrderByComparator<FriendlyURLEntryLocalization> orderByComparator)
		throws NoSuchFriendlyURLEntryLocalizationException {
		FriendlyURLEntryLocalization friendlyURLEntryLocalization = findByPrimaryKey(friendlyURLEntryLocalizationId);

		Session session = null;

		try {
			session = openSession();

			FriendlyURLEntryLocalization[] array = new FriendlyURLEntryLocalizationImpl[3];

			array[0] = getByG_F_PrevAndNext(session,
					friendlyURLEntryLocalization, groupId, friendlyURLEntryId,
					orderByComparator, true);

			array[1] = friendlyURLEntryLocalization;

			array[2] = getByG_F_PrevAndNext(session,
					friendlyURLEntryLocalization, groupId, friendlyURLEntryId,
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

	protected FriendlyURLEntryLocalization getByG_F_PrevAndNext(
		Session session,
		FriendlyURLEntryLocalization friendlyURLEntryLocalization,
		long groupId, long friendlyURLEntryId,
		OrderByComparator<FriendlyURLEntryLocalization> orderByComparator,
		boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(5 +
					(orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(4);
		}

		query.append(_SQL_SELECT_FRIENDLYURLENTRYLOCALIZATION_WHERE);

		query.append(_FINDER_COLUMN_G_F_GROUPID_2);

		query.append(_FINDER_COLUMN_G_F_FRIENDLYURLENTRYID_2);

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
			query.append(FriendlyURLEntryLocalizationModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		qPos.add(friendlyURLEntryId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(friendlyURLEntryLocalization);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<FriendlyURLEntryLocalization> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the friendly url entry localizations where groupId = &#63; and friendlyURLEntryId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param friendlyURLEntryId the friendly url entry ID
	 */
	@Override
	public void removeByG_F(long groupId, long friendlyURLEntryId) {
		for (FriendlyURLEntryLocalization friendlyURLEntryLocalization : findByG_F(
				groupId, friendlyURLEntryId, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS, null)) {
			remove(friendlyURLEntryLocalization);
		}
	}

	/**
	 * Returns the number of friendly url entry localizations where groupId = &#63; and friendlyURLEntryId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param friendlyURLEntryId the friendly url entry ID
	 * @return the number of matching friendly url entry localizations
	 */
	@Override
	public int countByG_F(long groupId, long friendlyURLEntryId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_G_F;

		Object[] finderArgs = new Object[] { groupId, friendlyURLEntryId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_FRIENDLYURLENTRYLOCALIZATION_WHERE);

			query.append(_FINDER_COLUMN_G_F_GROUPID_2);

			query.append(_FINDER_COLUMN_G_F_FRIENDLYURLENTRYID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(friendlyURLEntryId);

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

	private static final String _FINDER_COLUMN_G_F_GROUPID_2 = "friendlyURLEntryLocalization.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_F_FRIENDLYURLENTRYID_2 = "friendlyURLEntryLocalization.friendlyURLEntryId = ?";
	public static final FinderPath FINDER_PATH_FETCH_BY_G_F_L = new FinderPath(FriendlyURLEntryLocalizationModelImpl.ENTITY_CACHE_ENABLED,
			FriendlyURLEntryLocalizationModelImpl.FINDER_CACHE_ENABLED,
			FriendlyURLEntryLocalizationImpl.class, FINDER_CLASS_NAME_ENTITY,
			"fetchByG_F_L",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				String.class.getName()
			},
			FriendlyURLEntryLocalizationModelImpl.GROUPID_COLUMN_BITMASK |
			FriendlyURLEntryLocalizationModelImpl.FRIENDLYURLENTRYID_COLUMN_BITMASK |
			FriendlyURLEntryLocalizationModelImpl.LANGUAGEID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_G_F_L = new FinderPath(FriendlyURLEntryLocalizationModelImpl.ENTITY_CACHE_ENABLED,
			FriendlyURLEntryLocalizationModelImpl.FINDER_CACHE_ENABLED,
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByG_F_L",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				String.class.getName()
			});

	/**
	 * Returns the friendly url entry localization where groupId = &#63; and friendlyURLEntryId = &#63; and languageId = &#63; or throws a {@link NoSuchFriendlyURLEntryLocalizationException} if it could not be found.
	 *
	 * @param groupId the group ID
	 * @param friendlyURLEntryId the friendly url entry ID
	 * @param languageId the language ID
	 * @return the matching friendly url entry localization
	 * @throws NoSuchFriendlyURLEntryLocalizationException if a matching friendly url entry localization could not be found
	 */
	@Override
	public FriendlyURLEntryLocalization findByG_F_L(long groupId,
		long friendlyURLEntryId, String languageId)
		throws NoSuchFriendlyURLEntryLocalizationException {
		FriendlyURLEntryLocalization friendlyURLEntryLocalization = fetchByG_F_L(groupId,
				friendlyURLEntryId, languageId);

		if (friendlyURLEntryLocalization == null) {
			StringBundler msg = new StringBundler(8);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("groupId=");
			msg.append(groupId);

			msg.append(", friendlyURLEntryId=");
			msg.append(friendlyURLEntryId);

			msg.append(", languageId=");
			msg.append(languageId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isDebugEnabled()) {
				_log.debug(msg.toString());
			}

			throw new NoSuchFriendlyURLEntryLocalizationException(msg.toString());
		}

		return friendlyURLEntryLocalization;
	}

	/**
	 * Returns the friendly url entry localization where groupId = &#63; and friendlyURLEntryId = &#63; and languageId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param groupId the group ID
	 * @param friendlyURLEntryId the friendly url entry ID
	 * @param languageId the language ID
	 * @return the matching friendly url entry localization, or <code>null</code> if a matching friendly url entry localization could not be found
	 */
	@Override
	public FriendlyURLEntryLocalization fetchByG_F_L(long groupId,
		long friendlyURLEntryId, String languageId) {
		return fetchByG_F_L(groupId, friendlyURLEntryId, languageId, true);
	}

	/**
	 * Returns the friendly url entry localization where groupId = &#63; and friendlyURLEntryId = &#63; and languageId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param groupId the group ID
	 * @param friendlyURLEntryId the friendly url entry ID
	 * @param languageId the language ID
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the matching friendly url entry localization, or <code>null</code> if a matching friendly url entry localization could not be found
	 */
	@Override
	public FriendlyURLEntryLocalization fetchByG_F_L(long groupId,
		long friendlyURLEntryId, String languageId, boolean retrieveFromCache) {
		Object[] finderArgs = new Object[] {
				groupId, friendlyURLEntryId, languageId
			};

		Object result = null;

		if (retrieveFromCache) {
			result = finderCache.getResult(FINDER_PATH_FETCH_BY_G_F_L,
					finderArgs, this);
		}

		if (result instanceof FriendlyURLEntryLocalization) {
			FriendlyURLEntryLocalization friendlyURLEntryLocalization = (FriendlyURLEntryLocalization)result;

			if ((groupId != friendlyURLEntryLocalization.getGroupId()) ||
					(friendlyURLEntryId != friendlyURLEntryLocalization.getFriendlyURLEntryId()) ||
					!Objects.equals(languageId,
						friendlyURLEntryLocalization.getLanguageId())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(5);

			query.append(_SQL_SELECT_FRIENDLYURLENTRYLOCALIZATION_WHERE);

			query.append(_FINDER_COLUMN_G_F_L_GROUPID_2);

			query.append(_FINDER_COLUMN_G_F_L_FRIENDLYURLENTRYID_2);

			boolean bindLanguageId = false;

			if (languageId == null) {
				query.append(_FINDER_COLUMN_G_F_L_LANGUAGEID_1);
			}
			else if (languageId.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_G_F_L_LANGUAGEID_3);
			}
			else {
				bindLanguageId = true;

				query.append(_FINDER_COLUMN_G_F_L_LANGUAGEID_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(friendlyURLEntryId);

				if (bindLanguageId) {
					qPos.add(languageId);
				}

				List<FriendlyURLEntryLocalization> list = q.list();

				if (list.isEmpty()) {
					finderCache.putResult(FINDER_PATH_FETCH_BY_G_F_L,
						finderArgs, list);
				}
				else {
					FriendlyURLEntryLocalization friendlyURLEntryLocalization = list.get(0);

					result = friendlyURLEntryLocalization;

					cacheResult(friendlyURLEntryLocalization);

					if ((friendlyURLEntryLocalization.getGroupId() != groupId) ||
							(friendlyURLEntryLocalization.getFriendlyURLEntryId() != friendlyURLEntryId) ||
							(friendlyURLEntryLocalization.getLanguageId() == null) ||
							!friendlyURLEntryLocalization.getLanguageId()
															 .equals(languageId)) {
						finderCache.putResult(FINDER_PATH_FETCH_BY_G_F_L,
							finderArgs, friendlyURLEntryLocalization);
					}
				}
			}
			catch (Exception e) {
				finderCache.removeResult(FINDER_PATH_FETCH_BY_G_F_L, finderArgs);

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
			return (FriendlyURLEntryLocalization)result;
		}
	}

	/**
	 * Removes the friendly url entry localization where groupId = &#63; and friendlyURLEntryId = &#63; and languageId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param friendlyURLEntryId the friendly url entry ID
	 * @param languageId the language ID
	 * @return the friendly url entry localization that was removed
	 */
	@Override
	public FriendlyURLEntryLocalization removeByG_F_L(long groupId,
		long friendlyURLEntryId, String languageId)
		throws NoSuchFriendlyURLEntryLocalizationException {
		FriendlyURLEntryLocalization friendlyURLEntryLocalization = findByG_F_L(groupId,
				friendlyURLEntryId, languageId);

		return remove(friendlyURLEntryLocalization);
	}

	/**
	 * Returns the number of friendly url entry localizations where groupId = &#63; and friendlyURLEntryId = &#63; and languageId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param friendlyURLEntryId the friendly url entry ID
	 * @param languageId the language ID
	 * @return the number of matching friendly url entry localizations
	 */
	@Override
	public int countByG_F_L(long groupId, long friendlyURLEntryId,
		String languageId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_G_F_L;

		Object[] finderArgs = new Object[] {
				groupId, friendlyURLEntryId, languageId
			};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_COUNT_FRIENDLYURLENTRYLOCALIZATION_WHERE);

			query.append(_FINDER_COLUMN_G_F_L_GROUPID_2);

			query.append(_FINDER_COLUMN_G_F_L_FRIENDLYURLENTRYID_2);

			boolean bindLanguageId = false;

			if (languageId == null) {
				query.append(_FINDER_COLUMN_G_F_L_LANGUAGEID_1);
			}
			else if (languageId.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_G_F_L_LANGUAGEID_3);
			}
			else {
				bindLanguageId = true;

				query.append(_FINDER_COLUMN_G_F_L_LANGUAGEID_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(friendlyURLEntryId);

				if (bindLanguageId) {
					qPos.add(languageId);
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

	private static final String _FINDER_COLUMN_G_F_L_GROUPID_2 = "friendlyURLEntryLocalization.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_F_L_FRIENDLYURLENTRYID_2 = "friendlyURLEntryLocalization.friendlyURLEntryId = ? AND ";
	private static final String _FINDER_COLUMN_G_F_L_LANGUAGEID_1 = "friendlyURLEntryLocalization.languageId IS NULL";
	private static final String _FINDER_COLUMN_G_F_L_LANGUAGEID_2 = "friendlyURLEntryLocalization.languageId = ?";
	private static final String _FINDER_COLUMN_G_F_L_LANGUAGEID_3 = "(friendlyURLEntryLocalization.languageId IS NULL OR friendlyURLEntryLocalization.languageId = '')";
	public static final FinderPath FINDER_PATH_FETCH_BY_G_U_L = new FinderPath(FriendlyURLEntryLocalizationModelImpl.ENTITY_CACHE_ENABLED,
			FriendlyURLEntryLocalizationModelImpl.FINDER_CACHE_ENABLED,
			FriendlyURLEntryLocalizationImpl.class, FINDER_CLASS_NAME_ENTITY,
			"fetchByG_U_L",
			new String[] {
				Long.class.getName(), String.class.getName(),
				String.class.getName()
			},
			FriendlyURLEntryLocalizationModelImpl.GROUPID_COLUMN_BITMASK |
			FriendlyURLEntryLocalizationModelImpl.URLTITLE_COLUMN_BITMASK |
			FriendlyURLEntryLocalizationModelImpl.LANGUAGEID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_G_U_L = new FinderPath(FriendlyURLEntryLocalizationModelImpl.ENTITY_CACHE_ENABLED,
			FriendlyURLEntryLocalizationModelImpl.FINDER_CACHE_ENABLED,
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByG_U_L",
			new String[] {
				Long.class.getName(), String.class.getName(),
				String.class.getName()
			});

	/**
	 * Returns the friendly url entry localization where groupId = &#63; and urlTitle = &#63; and languageId = &#63; or throws a {@link NoSuchFriendlyURLEntryLocalizationException} if it could not be found.
	 *
	 * @param groupId the group ID
	 * @param urlTitle the url title
	 * @param languageId the language ID
	 * @return the matching friendly url entry localization
	 * @throws NoSuchFriendlyURLEntryLocalizationException if a matching friendly url entry localization could not be found
	 */
	@Override
	public FriendlyURLEntryLocalization findByG_U_L(long groupId,
		String urlTitle, String languageId)
		throws NoSuchFriendlyURLEntryLocalizationException {
		FriendlyURLEntryLocalization friendlyURLEntryLocalization = fetchByG_U_L(groupId,
				urlTitle, languageId);

		if (friendlyURLEntryLocalization == null) {
			StringBundler msg = new StringBundler(8);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("groupId=");
			msg.append(groupId);

			msg.append(", urlTitle=");
			msg.append(urlTitle);

			msg.append(", languageId=");
			msg.append(languageId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isDebugEnabled()) {
				_log.debug(msg.toString());
			}

			throw new NoSuchFriendlyURLEntryLocalizationException(msg.toString());
		}

		return friendlyURLEntryLocalization;
	}

	/**
	 * Returns the friendly url entry localization where groupId = &#63; and urlTitle = &#63; and languageId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param groupId the group ID
	 * @param urlTitle the url title
	 * @param languageId the language ID
	 * @return the matching friendly url entry localization, or <code>null</code> if a matching friendly url entry localization could not be found
	 */
	@Override
	public FriendlyURLEntryLocalization fetchByG_U_L(long groupId,
		String urlTitle, String languageId) {
		return fetchByG_U_L(groupId, urlTitle, languageId, true);
	}

	/**
	 * Returns the friendly url entry localization where groupId = &#63; and urlTitle = &#63; and languageId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param groupId the group ID
	 * @param urlTitle the url title
	 * @param languageId the language ID
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the matching friendly url entry localization, or <code>null</code> if a matching friendly url entry localization could not be found
	 */
	@Override
	public FriendlyURLEntryLocalization fetchByG_U_L(long groupId,
		String urlTitle, String languageId, boolean retrieveFromCache) {
		Object[] finderArgs = new Object[] { groupId, urlTitle, languageId };

		Object result = null;

		if (retrieveFromCache) {
			result = finderCache.getResult(FINDER_PATH_FETCH_BY_G_U_L,
					finderArgs, this);
		}

		if (result instanceof FriendlyURLEntryLocalization) {
			FriendlyURLEntryLocalization friendlyURLEntryLocalization = (FriendlyURLEntryLocalization)result;

			if ((groupId != friendlyURLEntryLocalization.getGroupId()) ||
					!Objects.equals(urlTitle,
						friendlyURLEntryLocalization.getUrlTitle()) ||
					!Objects.equals(languageId,
						friendlyURLEntryLocalization.getLanguageId())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(5);

			query.append(_SQL_SELECT_FRIENDLYURLENTRYLOCALIZATION_WHERE);

			query.append(_FINDER_COLUMN_G_U_L_GROUPID_2);

			boolean bindUrlTitle = false;

			if (urlTitle == null) {
				query.append(_FINDER_COLUMN_G_U_L_URLTITLE_1);
			}
			else if (urlTitle.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_G_U_L_URLTITLE_3);
			}
			else {
				bindUrlTitle = true;

				query.append(_FINDER_COLUMN_G_U_L_URLTITLE_2);
			}

			boolean bindLanguageId = false;

			if (languageId == null) {
				query.append(_FINDER_COLUMN_G_U_L_LANGUAGEID_1);
			}
			else if (languageId.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_G_U_L_LANGUAGEID_3);
			}
			else {
				bindLanguageId = true;

				query.append(_FINDER_COLUMN_G_U_L_LANGUAGEID_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (bindUrlTitle) {
					qPos.add(urlTitle);
				}

				if (bindLanguageId) {
					qPos.add(languageId);
				}

				List<FriendlyURLEntryLocalization> list = q.list();

				if (list.isEmpty()) {
					finderCache.putResult(FINDER_PATH_FETCH_BY_G_U_L,
						finderArgs, list);
				}
				else {
					FriendlyURLEntryLocalization friendlyURLEntryLocalization = list.get(0);

					result = friendlyURLEntryLocalization;

					cacheResult(friendlyURLEntryLocalization);

					if ((friendlyURLEntryLocalization.getGroupId() != groupId) ||
							(friendlyURLEntryLocalization.getUrlTitle() == null) ||
							!friendlyURLEntryLocalization.getUrlTitle()
															 .equals(urlTitle) ||
							(friendlyURLEntryLocalization.getLanguageId() == null) ||
							!friendlyURLEntryLocalization.getLanguageId()
															 .equals(languageId)) {
						finderCache.putResult(FINDER_PATH_FETCH_BY_G_U_L,
							finderArgs, friendlyURLEntryLocalization);
					}
				}
			}
			catch (Exception e) {
				finderCache.removeResult(FINDER_PATH_FETCH_BY_G_U_L, finderArgs);

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
			return (FriendlyURLEntryLocalization)result;
		}
	}

	/**
	 * Removes the friendly url entry localization where groupId = &#63; and urlTitle = &#63; and languageId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param urlTitle the url title
	 * @param languageId the language ID
	 * @return the friendly url entry localization that was removed
	 */
	@Override
	public FriendlyURLEntryLocalization removeByG_U_L(long groupId,
		String urlTitle, String languageId)
		throws NoSuchFriendlyURLEntryLocalizationException {
		FriendlyURLEntryLocalization friendlyURLEntryLocalization = findByG_U_L(groupId,
				urlTitle, languageId);

		return remove(friendlyURLEntryLocalization);
	}

	/**
	 * Returns the number of friendly url entry localizations where groupId = &#63; and urlTitle = &#63; and languageId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param urlTitle the url title
	 * @param languageId the language ID
	 * @return the number of matching friendly url entry localizations
	 */
	@Override
	public int countByG_U_L(long groupId, String urlTitle, String languageId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_G_U_L;

		Object[] finderArgs = new Object[] { groupId, urlTitle, languageId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_COUNT_FRIENDLYURLENTRYLOCALIZATION_WHERE);

			query.append(_FINDER_COLUMN_G_U_L_GROUPID_2);

			boolean bindUrlTitle = false;

			if (urlTitle == null) {
				query.append(_FINDER_COLUMN_G_U_L_URLTITLE_1);
			}
			else if (urlTitle.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_G_U_L_URLTITLE_3);
			}
			else {
				bindUrlTitle = true;

				query.append(_FINDER_COLUMN_G_U_L_URLTITLE_2);
			}

			boolean bindLanguageId = false;

			if (languageId == null) {
				query.append(_FINDER_COLUMN_G_U_L_LANGUAGEID_1);
			}
			else if (languageId.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_G_U_L_LANGUAGEID_3);
			}
			else {
				bindLanguageId = true;

				query.append(_FINDER_COLUMN_G_U_L_LANGUAGEID_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (bindUrlTitle) {
					qPos.add(urlTitle);
				}

				if (bindLanguageId) {
					qPos.add(languageId);
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

	private static final String _FINDER_COLUMN_G_U_L_GROUPID_2 = "friendlyURLEntryLocalization.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_U_L_URLTITLE_1 = "friendlyURLEntryLocalization.urlTitle IS NULL AND ";
	private static final String _FINDER_COLUMN_G_U_L_URLTITLE_2 = "friendlyURLEntryLocalization.urlTitle = ? AND ";
	private static final String _FINDER_COLUMN_G_U_L_URLTITLE_3 = "(friendlyURLEntryLocalization.urlTitle IS NULL OR friendlyURLEntryLocalization.urlTitle = '') AND ";
	private static final String _FINDER_COLUMN_G_U_L_LANGUAGEID_1 = "friendlyURLEntryLocalization.languageId IS NULL";
	private static final String _FINDER_COLUMN_G_U_L_LANGUAGEID_2 = "friendlyURLEntryLocalization.languageId = ?";
	private static final String _FINDER_COLUMN_G_U_L_LANGUAGEID_3 = "(friendlyURLEntryLocalization.languageId IS NULL OR friendlyURLEntryLocalization.languageId = '')";

	public FriendlyURLEntryLocalizationPersistenceImpl() {
		setModelClass(FriendlyURLEntryLocalization.class);
	}

	/**
	 * Caches the friendly url entry localization in the entity cache if it is enabled.
	 *
	 * @param friendlyURLEntryLocalization the friendly url entry localization
	 */
	@Override
	public void cacheResult(
		FriendlyURLEntryLocalization friendlyURLEntryLocalization) {
		entityCache.putResult(FriendlyURLEntryLocalizationModelImpl.ENTITY_CACHE_ENABLED,
			FriendlyURLEntryLocalizationImpl.class,
			friendlyURLEntryLocalization.getPrimaryKey(),
			friendlyURLEntryLocalization);

		finderCache.putResult(FINDER_PATH_FETCH_BY_G_F_L,
			new Object[] {
				friendlyURLEntryLocalization.getGroupId(),
				friendlyURLEntryLocalization.getFriendlyURLEntryId(),
				friendlyURLEntryLocalization.getLanguageId()
			}, friendlyURLEntryLocalization);

		finderCache.putResult(FINDER_PATH_FETCH_BY_G_U_L,
			new Object[] {
				friendlyURLEntryLocalization.getGroupId(),
				friendlyURLEntryLocalization.getUrlTitle(),
				friendlyURLEntryLocalization.getLanguageId()
			}, friendlyURLEntryLocalization);

		friendlyURLEntryLocalization.resetOriginalValues();
	}

	/**
	 * Caches the friendly url entry localizations in the entity cache if it is enabled.
	 *
	 * @param friendlyURLEntryLocalizations the friendly url entry localizations
	 */
	@Override
	public void cacheResult(
		List<FriendlyURLEntryLocalization> friendlyURLEntryLocalizations) {
		for (FriendlyURLEntryLocalization friendlyURLEntryLocalization : friendlyURLEntryLocalizations) {
			if (entityCache.getResult(
						FriendlyURLEntryLocalizationModelImpl.ENTITY_CACHE_ENABLED,
						FriendlyURLEntryLocalizationImpl.class,
						friendlyURLEntryLocalization.getPrimaryKey()) == null) {
				cacheResult(friendlyURLEntryLocalization);
			}
			else {
				friendlyURLEntryLocalization.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all friendly url entry localizations.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(FriendlyURLEntryLocalizationImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the friendly url entry localization.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(
		FriendlyURLEntryLocalization friendlyURLEntryLocalization) {
		entityCache.removeResult(FriendlyURLEntryLocalizationModelImpl.ENTITY_CACHE_ENABLED,
			FriendlyURLEntryLocalizationImpl.class,
			friendlyURLEntryLocalization.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache((FriendlyURLEntryLocalizationModelImpl)friendlyURLEntryLocalization,
			true);
	}

	@Override
	public void clearCache(
		List<FriendlyURLEntryLocalization> friendlyURLEntryLocalizations) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (FriendlyURLEntryLocalization friendlyURLEntryLocalization : friendlyURLEntryLocalizations) {
			entityCache.removeResult(FriendlyURLEntryLocalizationModelImpl.ENTITY_CACHE_ENABLED,
				FriendlyURLEntryLocalizationImpl.class,
				friendlyURLEntryLocalization.getPrimaryKey());

			clearUniqueFindersCache((FriendlyURLEntryLocalizationModelImpl)friendlyURLEntryLocalization,
				true);
		}
	}

	protected void cacheUniqueFindersCache(
		FriendlyURLEntryLocalizationModelImpl friendlyURLEntryLocalizationModelImpl) {
		Object[] args = new Object[] {
				friendlyURLEntryLocalizationModelImpl.getGroupId(),
				friendlyURLEntryLocalizationModelImpl.getFriendlyURLEntryId(),
				friendlyURLEntryLocalizationModelImpl.getLanguageId()
			};

		finderCache.putResult(FINDER_PATH_COUNT_BY_G_F_L, args,
			Long.valueOf(1), false);
		finderCache.putResult(FINDER_PATH_FETCH_BY_G_F_L, args,
			friendlyURLEntryLocalizationModelImpl, false);

		args = new Object[] {
				friendlyURLEntryLocalizationModelImpl.getGroupId(),
				friendlyURLEntryLocalizationModelImpl.getUrlTitle(),
				friendlyURLEntryLocalizationModelImpl.getLanguageId()
			};

		finderCache.putResult(FINDER_PATH_COUNT_BY_G_U_L, args,
			Long.valueOf(1), false);
		finderCache.putResult(FINDER_PATH_FETCH_BY_G_U_L, args,
			friendlyURLEntryLocalizationModelImpl, false);
	}

	protected void clearUniqueFindersCache(
		FriendlyURLEntryLocalizationModelImpl friendlyURLEntryLocalizationModelImpl,
		boolean clearCurrent) {
		if (clearCurrent) {
			Object[] args = new Object[] {
					friendlyURLEntryLocalizationModelImpl.getGroupId(),
					friendlyURLEntryLocalizationModelImpl.getFriendlyURLEntryId(),
					friendlyURLEntryLocalizationModelImpl.getLanguageId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_G_F_L, args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_G_F_L, args);
		}

		if ((friendlyURLEntryLocalizationModelImpl.getColumnBitmask() &
				FINDER_PATH_FETCH_BY_G_F_L.getColumnBitmask()) != 0) {
			Object[] args = new Object[] {
					friendlyURLEntryLocalizationModelImpl.getOriginalGroupId(),
					friendlyURLEntryLocalizationModelImpl.getOriginalFriendlyURLEntryId(),
					friendlyURLEntryLocalizationModelImpl.getOriginalLanguageId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_G_F_L, args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_G_F_L, args);
		}

		if (clearCurrent) {
			Object[] args = new Object[] {
					friendlyURLEntryLocalizationModelImpl.getGroupId(),
					friendlyURLEntryLocalizationModelImpl.getUrlTitle(),
					friendlyURLEntryLocalizationModelImpl.getLanguageId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_G_U_L, args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_G_U_L, args);
		}

		if ((friendlyURLEntryLocalizationModelImpl.getColumnBitmask() &
				FINDER_PATH_FETCH_BY_G_U_L.getColumnBitmask()) != 0) {
			Object[] args = new Object[] {
					friendlyURLEntryLocalizationModelImpl.getOriginalGroupId(),
					friendlyURLEntryLocalizationModelImpl.getOriginalUrlTitle(),
					friendlyURLEntryLocalizationModelImpl.getOriginalLanguageId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_G_U_L, args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_G_U_L, args);
		}
	}

	/**
	 * Creates a new friendly url entry localization with the primary key. Does not add the friendly url entry localization to the database.
	 *
	 * @param friendlyURLEntryLocalizationId the primary key for the new friendly url entry localization
	 * @return the new friendly url entry localization
	 */
	@Override
	public FriendlyURLEntryLocalization create(
		long friendlyURLEntryLocalizationId) {
		FriendlyURLEntryLocalization friendlyURLEntryLocalization = new FriendlyURLEntryLocalizationImpl();

		friendlyURLEntryLocalization.setNew(true);
		friendlyURLEntryLocalization.setPrimaryKey(friendlyURLEntryLocalizationId);

		friendlyURLEntryLocalization.setCompanyId(companyProvider.getCompanyId());

		return friendlyURLEntryLocalization;
	}

	/**
	 * Removes the friendly url entry localization with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param friendlyURLEntryLocalizationId the primary key of the friendly url entry localization
	 * @return the friendly url entry localization that was removed
	 * @throws NoSuchFriendlyURLEntryLocalizationException if a friendly url entry localization with the primary key could not be found
	 */
	@Override
	public FriendlyURLEntryLocalization remove(
		long friendlyURLEntryLocalizationId)
		throws NoSuchFriendlyURLEntryLocalizationException {
		return remove((Serializable)friendlyURLEntryLocalizationId);
	}

	/**
	 * Removes the friendly url entry localization with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the friendly url entry localization
	 * @return the friendly url entry localization that was removed
	 * @throws NoSuchFriendlyURLEntryLocalizationException if a friendly url entry localization with the primary key could not be found
	 */
	@Override
	public FriendlyURLEntryLocalization remove(Serializable primaryKey)
		throws NoSuchFriendlyURLEntryLocalizationException {
		Session session = null;

		try {
			session = openSession();

			FriendlyURLEntryLocalization friendlyURLEntryLocalization = (FriendlyURLEntryLocalization)session.get(FriendlyURLEntryLocalizationImpl.class,
					primaryKey);

			if (friendlyURLEntryLocalization == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchFriendlyURLEntryLocalizationException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(friendlyURLEntryLocalization);
		}
		catch (NoSuchFriendlyURLEntryLocalizationException nsee) {
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
	protected FriendlyURLEntryLocalization removeImpl(
		FriendlyURLEntryLocalization friendlyURLEntryLocalization) {
		friendlyURLEntryLocalization = toUnwrappedModel(friendlyURLEntryLocalization);

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(friendlyURLEntryLocalization)) {
				friendlyURLEntryLocalization = (FriendlyURLEntryLocalization)session.get(FriendlyURLEntryLocalizationImpl.class,
						friendlyURLEntryLocalization.getPrimaryKeyObj());
			}

			if (friendlyURLEntryLocalization != null) {
				session.delete(friendlyURLEntryLocalization);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (friendlyURLEntryLocalization != null) {
			clearCache(friendlyURLEntryLocalization);
		}

		return friendlyURLEntryLocalization;
	}

	@Override
	public FriendlyURLEntryLocalization updateImpl(
		FriendlyURLEntryLocalization friendlyURLEntryLocalization) {
		friendlyURLEntryLocalization = toUnwrappedModel(friendlyURLEntryLocalization);

		boolean isNew = friendlyURLEntryLocalization.isNew();

		FriendlyURLEntryLocalizationModelImpl friendlyURLEntryLocalizationModelImpl =
			(FriendlyURLEntryLocalizationModelImpl)friendlyURLEntryLocalization;

		Session session = null;

		try {
			session = openSession();

			if (friendlyURLEntryLocalization.isNew()) {
				session.save(friendlyURLEntryLocalization);

				friendlyURLEntryLocalization.setNew(false);
			}
			else {
				friendlyURLEntryLocalization = (FriendlyURLEntryLocalization)session.merge(friendlyURLEntryLocalization);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (!FriendlyURLEntryLocalizationModelImpl.COLUMN_BITMASK_ENABLED) {
			finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}
		else
		 if (isNew) {
			Object[] args = new Object[] {
					friendlyURLEntryLocalizationModelImpl.getGroupId(),
					friendlyURLEntryLocalizationModelImpl.getFriendlyURLEntryId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_G_F, args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_F,
				args);

			finderCache.removeResult(FINDER_PATH_COUNT_ALL, FINDER_ARGS_EMPTY);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL,
				FINDER_ARGS_EMPTY);
		}

		else {
			if ((friendlyURLEntryLocalizationModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_F.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						friendlyURLEntryLocalizationModelImpl.getOriginalGroupId(),
						friendlyURLEntryLocalizationModelImpl.getOriginalFriendlyURLEntryId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_G_F, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_F,
					args);

				args = new Object[] {
						friendlyURLEntryLocalizationModelImpl.getGroupId(),
						friendlyURLEntryLocalizationModelImpl.getFriendlyURLEntryId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_G_F, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_F,
					args);
			}
		}

		entityCache.putResult(FriendlyURLEntryLocalizationModelImpl.ENTITY_CACHE_ENABLED,
			FriendlyURLEntryLocalizationImpl.class,
			friendlyURLEntryLocalization.getPrimaryKey(),
			friendlyURLEntryLocalization, false);

		clearUniqueFindersCache(friendlyURLEntryLocalizationModelImpl, false);
		cacheUniqueFindersCache(friendlyURLEntryLocalizationModelImpl);

		friendlyURLEntryLocalization.resetOriginalValues();

		return friendlyURLEntryLocalization;
	}

	protected FriendlyURLEntryLocalization toUnwrappedModel(
		FriendlyURLEntryLocalization friendlyURLEntryLocalization) {
		if (friendlyURLEntryLocalization instanceof FriendlyURLEntryLocalizationImpl) {
			return friendlyURLEntryLocalization;
		}

		FriendlyURLEntryLocalizationImpl friendlyURLEntryLocalizationImpl = new FriendlyURLEntryLocalizationImpl();

		friendlyURLEntryLocalizationImpl.setNew(friendlyURLEntryLocalization.isNew());
		friendlyURLEntryLocalizationImpl.setPrimaryKey(friendlyURLEntryLocalization.getPrimaryKey());

		friendlyURLEntryLocalizationImpl.setFriendlyURLEntryLocalizationId(friendlyURLEntryLocalization.getFriendlyURLEntryLocalizationId());
		friendlyURLEntryLocalizationImpl.setGroupId(friendlyURLEntryLocalization.getGroupId());
		friendlyURLEntryLocalizationImpl.setCompanyId(friendlyURLEntryLocalization.getCompanyId());
		friendlyURLEntryLocalizationImpl.setFriendlyURLEntryId(friendlyURLEntryLocalization.getFriendlyURLEntryId());
		friendlyURLEntryLocalizationImpl.setUrlTitle(friendlyURLEntryLocalization.getUrlTitle());
		friendlyURLEntryLocalizationImpl.setLanguageId(friendlyURLEntryLocalization.getLanguageId());

		return friendlyURLEntryLocalizationImpl;
	}

	/**
	 * Returns the friendly url entry localization with the primary key or throws a {@link com.liferay.portal.kernel.exception.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the friendly url entry localization
	 * @return the friendly url entry localization
	 * @throws NoSuchFriendlyURLEntryLocalizationException if a friendly url entry localization with the primary key could not be found
	 */
	@Override
	public FriendlyURLEntryLocalization findByPrimaryKey(
		Serializable primaryKey)
		throws NoSuchFriendlyURLEntryLocalizationException {
		FriendlyURLEntryLocalization friendlyURLEntryLocalization = fetchByPrimaryKey(primaryKey);

		if (friendlyURLEntryLocalization == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchFriendlyURLEntryLocalizationException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				primaryKey);
		}

		return friendlyURLEntryLocalization;
	}

	/**
	 * Returns the friendly url entry localization with the primary key or throws a {@link NoSuchFriendlyURLEntryLocalizationException} if it could not be found.
	 *
	 * @param friendlyURLEntryLocalizationId the primary key of the friendly url entry localization
	 * @return the friendly url entry localization
	 * @throws NoSuchFriendlyURLEntryLocalizationException if a friendly url entry localization with the primary key could not be found
	 */
	@Override
	public FriendlyURLEntryLocalization findByPrimaryKey(
		long friendlyURLEntryLocalizationId)
		throws NoSuchFriendlyURLEntryLocalizationException {
		return findByPrimaryKey((Serializable)friendlyURLEntryLocalizationId);
	}

	/**
	 * Returns the friendly url entry localization with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the friendly url entry localization
	 * @return the friendly url entry localization, or <code>null</code> if a friendly url entry localization with the primary key could not be found
	 */
	@Override
	public FriendlyURLEntryLocalization fetchByPrimaryKey(
		Serializable primaryKey) {
		Serializable serializable = entityCache.getResult(FriendlyURLEntryLocalizationModelImpl.ENTITY_CACHE_ENABLED,
				FriendlyURLEntryLocalizationImpl.class, primaryKey);

		if (serializable == nullModel) {
			return null;
		}

		FriendlyURLEntryLocalization friendlyURLEntryLocalization = (FriendlyURLEntryLocalization)serializable;

		if (friendlyURLEntryLocalization == null) {
			Session session = null;

			try {
				session = openSession();

				friendlyURLEntryLocalization = (FriendlyURLEntryLocalization)session.get(FriendlyURLEntryLocalizationImpl.class,
						primaryKey);

				if (friendlyURLEntryLocalization != null) {
					cacheResult(friendlyURLEntryLocalization);
				}
				else {
					entityCache.putResult(FriendlyURLEntryLocalizationModelImpl.ENTITY_CACHE_ENABLED,
						FriendlyURLEntryLocalizationImpl.class, primaryKey,
						nullModel);
				}
			}
			catch (Exception e) {
				entityCache.removeResult(FriendlyURLEntryLocalizationModelImpl.ENTITY_CACHE_ENABLED,
					FriendlyURLEntryLocalizationImpl.class, primaryKey);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return friendlyURLEntryLocalization;
	}

	/**
	 * Returns the friendly url entry localization with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param friendlyURLEntryLocalizationId the primary key of the friendly url entry localization
	 * @return the friendly url entry localization, or <code>null</code> if a friendly url entry localization with the primary key could not be found
	 */
	@Override
	public FriendlyURLEntryLocalization fetchByPrimaryKey(
		long friendlyURLEntryLocalizationId) {
		return fetchByPrimaryKey((Serializable)friendlyURLEntryLocalizationId);
	}

	@Override
	public Map<Serializable, FriendlyURLEntryLocalization> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {
		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, FriendlyURLEntryLocalization> map = new HashMap<Serializable, FriendlyURLEntryLocalization>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			FriendlyURLEntryLocalization friendlyURLEntryLocalization = fetchByPrimaryKey(primaryKey);

			if (friendlyURLEntryLocalization != null) {
				map.put(primaryKey, friendlyURLEntryLocalization);
			}

			return map;
		}

		Set<Serializable> uncachedPrimaryKeys = null;

		for (Serializable primaryKey : primaryKeys) {
			Serializable serializable = entityCache.getResult(FriendlyURLEntryLocalizationModelImpl.ENTITY_CACHE_ENABLED,
					FriendlyURLEntryLocalizationImpl.class, primaryKey);

			if (serializable != nullModel) {
				if (serializable == null) {
					if (uncachedPrimaryKeys == null) {
						uncachedPrimaryKeys = new HashSet<Serializable>();
					}

					uncachedPrimaryKeys.add(primaryKey);
				}
				else {
					map.put(primaryKey,
						(FriendlyURLEntryLocalization)serializable);
				}
			}
		}

		if (uncachedPrimaryKeys == null) {
			return map;
		}

		StringBundler query = new StringBundler((uncachedPrimaryKeys.size() * 2) +
				1);

		query.append(_SQL_SELECT_FRIENDLYURLENTRYLOCALIZATION_WHERE_PKS_IN);

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

			for (FriendlyURLEntryLocalization friendlyURLEntryLocalization : (List<FriendlyURLEntryLocalization>)q.list()) {
				map.put(friendlyURLEntryLocalization.getPrimaryKeyObj(),
					friendlyURLEntryLocalization);

				cacheResult(friendlyURLEntryLocalization);

				uncachedPrimaryKeys.remove(friendlyURLEntryLocalization.getPrimaryKeyObj());
			}

			for (Serializable primaryKey : uncachedPrimaryKeys) {
				entityCache.putResult(FriendlyURLEntryLocalizationModelImpl.ENTITY_CACHE_ENABLED,
					FriendlyURLEntryLocalizationImpl.class, primaryKey,
					nullModel);
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
	 * Returns all the friendly url entry localizations.
	 *
	 * @return the friendly url entry localizations
	 */
	@Override
	public List<FriendlyURLEntryLocalization> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the friendly url entry localizations.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link FriendlyURLEntryLocalizationModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of friendly url entry localizations
	 * @param end the upper bound of the range of friendly url entry localizations (not inclusive)
	 * @return the range of friendly url entry localizations
	 */
	@Override
	public List<FriendlyURLEntryLocalization> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the friendly url entry localizations.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link FriendlyURLEntryLocalizationModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of friendly url entry localizations
	 * @param end the upper bound of the range of friendly url entry localizations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of friendly url entry localizations
	 */
	@Override
	public List<FriendlyURLEntryLocalization> findAll(int start, int end,
		OrderByComparator<FriendlyURLEntryLocalization> orderByComparator) {
		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the friendly url entry localizations.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link FriendlyURLEntryLocalizationModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of friendly url entry localizations
	 * @param end the upper bound of the range of friendly url entry localizations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of friendly url entry localizations
	 */
	@Override
	public List<FriendlyURLEntryLocalization> findAll(int start, int end,
		OrderByComparator<FriendlyURLEntryLocalization> orderByComparator,
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

		List<FriendlyURLEntryLocalization> list = null;

		if (retrieveFromCache) {
			list = (List<FriendlyURLEntryLocalization>)finderCache.getResult(finderPath,
					finderArgs, this);
		}

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 2));

				query.append(_SQL_SELECT_FRIENDLYURLENTRYLOCALIZATION);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_FRIENDLYURLENTRYLOCALIZATION;

				if (pagination) {
					sql = sql.concat(FriendlyURLEntryLocalizationModelImpl.ORDER_BY_JPQL);
				}
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (!pagination) {
					list = (List<FriendlyURLEntryLocalization>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<FriendlyURLEntryLocalization>)QueryUtil.list(q,
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
	 * Removes all the friendly url entry localizations from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (FriendlyURLEntryLocalization friendlyURLEntryLocalization : findAll()) {
			remove(friendlyURLEntryLocalization);
		}
	}

	/**
	 * Returns the number of friendly url entry localizations.
	 *
	 * @return the number of friendly url entry localizations
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_FRIENDLYURLENTRYLOCALIZATION);

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
		return FriendlyURLEntryLocalizationModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the friendly url entry localization persistence.
	 */
	public void afterPropertiesSet() {
	}

	public void destroy() {
		entityCache.removeCache(FriendlyURLEntryLocalizationImpl.class.getName());
		finderCache.removeCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@ServiceReference(type = CompanyProviderWrapper.class)
	protected CompanyProvider companyProvider;
	@ServiceReference(type = EntityCache.class)
	protected EntityCache entityCache;
	@ServiceReference(type = FinderCache.class)
	protected FinderCache finderCache;
	private static final String _SQL_SELECT_FRIENDLYURLENTRYLOCALIZATION = "SELECT friendlyURLEntryLocalization FROM FriendlyURLEntryLocalization friendlyURLEntryLocalization";
	private static final String _SQL_SELECT_FRIENDLYURLENTRYLOCALIZATION_WHERE_PKS_IN =
		"SELECT friendlyURLEntryLocalization FROM FriendlyURLEntryLocalization friendlyURLEntryLocalization WHERE friendlyURLEntryLocalizationId IN (";
	private static final String _SQL_SELECT_FRIENDLYURLENTRYLOCALIZATION_WHERE = "SELECT friendlyURLEntryLocalization FROM FriendlyURLEntryLocalization friendlyURLEntryLocalization WHERE ";
	private static final String _SQL_COUNT_FRIENDLYURLENTRYLOCALIZATION = "SELECT COUNT(friendlyURLEntryLocalization) FROM FriendlyURLEntryLocalization friendlyURLEntryLocalization";
	private static final String _SQL_COUNT_FRIENDLYURLENTRYLOCALIZATION_WHERE = "SELECT COUNT(friendlyURLEntryLocalization) FROM FriendlyURLEntryLocalization friendlyURLEntryLocalization WHERE ";
	private static final String _ORDER_BY_ENTITY_ALIAS = "friendlyURLEntryLocalization.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No FriendlyURLEntryLocalization exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No FriendlyURLEntryLocalization exists with the key {";
	private static final Log _log = LogFactoryUtil.getLog(FriendlyURLEntryLocalizationPersistenceImpl.class);
}