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

import com.liferay.friendly.url.exception.NoSuchFriendlyURLLocalizationException;
import com.liferay.friendly.url.model.FriendlyURLLocalization;
import com.liferay.friendly.url.model.impl.FriendlyURLLocalizationImpl;
import com.liferay.friendly.url.model.impl.FriendlyURLLocalizationModelImpl;
import com.liferay.friendly.url.service.persistence.FriendlyURLLocalizationPersistence;

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
 * The persistence implementation for the friendly url localization service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see FriendlyURLLocalizationPersistence
 * @see com.liferay.friendly.url.service.persistence.FriendlyURLLocalizationUtil
 * @generated
 */
@ProviderType
public class FriendlyURLLocalizationPersistenceImpl extends BasePersistenceImpl<FriendlyURLLocalization>
	implements FriendlyURLLocalizationPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link FriendlyURLLocalizationUtil} to access the friendly url localization persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = FriendlyURLLocalizationImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(FriendlyURLLocalizationModelImpl.ENTITY_CACHE_ENABLED,
			FriendlyURLLocalizationModelImpl.FINDER_CACHE_ENABLED,
			FriendlyURLLocalizationImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(FriendlyURLLocalizationModelImpl.ENTITY_CACHE_ENABLED,
			FriendlyURLLocalizationModelImpl.FINDER_CACHE_ENABLED,
			FriendlyURLLocalizationImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(FriendlyURLLocalizationModelImpl.ENTITY_CACHE_ENABLED,
			FriendlyURLLocalizationModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_G_F = new FinderPath(FriendlyURLLocalizationModelImpl.ENTITY_CACHE_ENABLED,
			FriendlyURLLocalizationModelImpl.FINDER_CACHE_ENABLED,
			FriendlyURLLocalizationImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_F",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_F = new FinderPath(FriendlyURLLocalizationModelImpl.ENTITY_CACHE_ENABLED,
			FriendlyURLLocalizationModelImpl.FINDER_CACHE_ENABLED,
			FriendlyURLLocalizationImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByG_F",
			new String[] { Long.class.getName(), Long.class.getName() },
			FriendlyURLLocalizationModelImpl.GROUPID_COLUMN_BITMASK |
			FriendlyURLLocalizationModelImpl.FRIENDLYURLID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_G_F = new FinderPath(FriendlyURLLocalizationModelImpl.ENTITY_CACHE_ENABLED,
			FriendlyURLLocalizationModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_F",
			new String[] { Long.class.getName(), Long.class.getName() });

	/**
	 * Returns all the friendly url localizations where groupId = &#63; and friendlyURLId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param friendlyURLId the friendly url ID
	 * @return the matching friendly url localizations
	 */
	@Override
	public List<FriendlyURLLocalization> findByG_F(long groupId,
		long friendlyURLId) {
		return findByG_F(groupId, friendlyURLId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the friendly url localizations where groupId = &#63; and friendlyURLId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link FriendlyURLLocalizationModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param friendlyURLId the friendly url ID
	 * @param start the lower bound of the range of friendly url localizations
	 * @param end the upper bound of the range of friendly url localizations (not inclusive)
	 * @return the range of matching friendly url localizations
	 */
	@Override
	public List<FriendlyURLLocalization> findByG_F(long groupId,
		long friendlyURLId, int start, int end) {
		return findByG_F(groupId, friendlyURLId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the friendly url localizations where groupId = &#63; and friendlyURLId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link FriendlyURLLocalizationModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param friendlyURLId the friendly url ID
	 * @param start the lower bound of the range of friendly url localizations
	 * @param end the upper bound of the range of friendly url localizations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching friendly url localizations
	 */
	@Override
	public List<FriendlyURLLocalization> findByG_F(long groupId,
		long friendlyURLId, int start, int end,
		OrderByComparator<FriendlyURLLocalization> orderByComparator) {
		return findByG_F(groupId, friendlyURLId, start, end, orderByComparator,
			true);
	}

	/**
	 * Returns an ordered range of all the friendly url localizations where groupId = &#63; and friendlyURLId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link FriendlyURLLocalizationModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param friendlyURLId the friendly url ID
	 * @param start the lower bound of the range of friendly url localizations
	 * @param end the upper bound of the range of friendly url localizations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching friendly url localizations
	 */
	@Override
	public List<FriendlyURLLocalization> findByG_F(long groupId,
		long friendlyURLId, int start, int end,
		OrderByComparator<FriendlyURLLocalization> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_F;
			finderArgs = new Object[] { groupId, friendlyURLId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_G_F;
			finderArgs = new Object[] {
					groupId, friendlyURLId,
					
					start, end, orderByComparator
				};
		}

		List<FriendlyURLLocalization> list = null;

		if (retrieveFromCache) {
			list = (List<FriendlyURLLocalization>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (FriendlyURLLocalization friendlyURLLocalization : list) {
					if ((groupId != friendlyURLLocalization.getGroupId()) ||
							(friendlyURLId != friendlyURLLocalization.getFriendlyURLId())) {
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

			query.append(_SQL_SELECT_FRIENDLYURLLOCALIZATION_WHERE);

			query.append(_FINDER_COLUMN_G_F_GROUPID_2);

			query.append(_FINDER_COLUMN_G_F_FRIENDLYURLID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(FriendlyURLLocalizationModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(friendlyURLId);

				if (!pagination) {
					list = (List<FriendlyURLLocalization>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<FriendlyURLLocalization>)QueryUtil.list(q,
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
	 * Returns the first friendly url localization in the ordered set where groupId = &#63; and friendlyURLId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param friendlyURLId the friendly url ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching friendly url localization
	 * @throws NoSuchFriendlyURLLocalizationException if a matching friendly url localization could not be found
	 */
	@Override
	public FriendlyURLLocalization findByG_F_First(long groupId,
		long friendlyURLId,
		OrderByComparator<FriendlyURLLocalization> orderByComparator)
		throws NoSuchFriendlyURLLocalizationException {
		FriendlyURLLocalization friendlyURLLocalization = fetchByG_F_First(groupId,
				friendlyURLId, orderByComparator);

		if (friendlyURLLocalization != null) {
			return friendlyURLLocalization;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", friendlyURLId=");
		msg.append(friendlyURLId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchFriendlyURLLocalizationException(msg.toString());
	}

	/**
	 * Returns the first friendly url localization in the ordered set where groupId = &#63; and friendlyURLId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param friendlyURLId the friendly url ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching friendly url localization, or <code>null</code> if a matching friendly url localization could not be found
	 */
	@Override
	public FriendlyURLLocalization fetchByG_F_First(long groupId,
		long friendlyURLId,
		OrderByComparator<FriendlyURLLocalization> orderByComparator) {
		List<FriendlyURLLocalization> list = findByG_F(groupId, friendlyURLId,
				0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last friendly url localization in the ordered set where groupId = &#63; and friendlyURLId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param friendlyURLId the friendly url ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching friendly url localization
	 * @throws NoSuchFriendlyURLLocalizationException if a matching friendly url localization could not be found
	 */
	@Override
	public FriendlyURLLocalization findByG_F_Last(long groupId,
		long friendlyURLId,
		OrderByComparator<FriendlyURLLocalization> orderByComparator)
		throws NoSuchFriendlyURLLocalizationException {
		FriendlyURLLocalization friendlyURLLocalization = fetchByG_F_Last(groupId,
				friendlyURLId, orderByComparator);

		if (friendlyURLLocalization != null) {
			return friendlyURLLocalization;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", friendlyURLId=");
		msg.append(friendlyURLId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchFriendlyURLLocalizationException(msg.toString());
	}

	/**
	 * Returns the last friendly url localization in the ordered set where groupId = &#63; and friendlyURLId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param friendlyURLId the friendly url ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching friendly url localization, or <code>null</code> if a matching friendly url localization could not be found
	 */
	@Override
	public FriendlyURLLocalization fetchByG_F_Last(long groupId,
		long friendlyURLId,
		OrderByComparator<FriendlyURLLocalization> orderByComparator) {
		int count = countByG_F(groupId, friendlyURLId);

		if (count == 0) {
			return null;
		}

		List<FriendlyURLLocalization> list = findByG_F(groupId, friendlyURLId,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the friendly url localizations before and after the current friendly url localization in the ordered set where groupId = &#63; and friendlyURLId = &#63;.
	 *
	 * @param friendlyURLLocalizationId the primary key of the current friendly url localization
	 * @param groupId the group ID
	 * @param friendlyURLId the friendly url ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next friendly url localization
	 * @throws NoSuchFriendlyURLLocalizationException if a friendly url localization with the primary key could not be found
	 */
	@Override
	public FriendlyURLLocalization[] findByG_F_PrevAndNext(
		long friendlyURLLocalizationId, long groupId, long friendlyURLId,
		OrderByComparator<FriendlyURLLocalization> orderByComparator)
		throws NoSuchFriendlyURLLocalizationException {
		FriendlyURLLocalization friendlyURLLocalization = findByPrimaryKey(friendlyURLLocalizationId);

		Session session = null;

		try {
			session = openSession();

			FriendlyURLLocalization[] array = new FriendlyURLLocalizationImpl[3];

			array[0] = getByG_F_PrevAndNext(session, friendlyURLLocalization,
					groupId, friendlyURLId, orderByComparator, true);

			array[1] = friendlyURLLocalization;

			array[2] = getByG_F_PrevAndNext(session, friendlyURLLocalization,
					groupId, friendlyURLId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected FriendlyURLLocalization getByG_F_PrevAndNext(Session session,
		FriendlyURLLocalization friendlyURLLocalization, long groupId,
		long friendlyURLId,
		OrderByComparator<FriendlyURLLocalization> orderByComparator,
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

		query.append(_SQL_SELECT_FRIENDLYURLLOCALIZATION_WHERE);

		query.append(_FINDER_COLUMN_G_F_GROUPID_2);

		query.append(_FINDER_COLUMN_G_F_FRIENDLYURLID_2);

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
			query.append(FriendlyURLLocalizationModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		qPos.add(friendlyURLId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(friendlyURLLocalization);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<FriendlyURLLocalization> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the friendly url localizations where groupId = &#63; and friendlyURLId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param friendlyURLId the friendly url ID
	 */
	@Override
	public void removeByG_F(long groupId, long friendlyURLId) {
		for (FriendlyURLLocalization friendlyURLLocalization : findByG_F(
				groupId, friendlyURLId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
				null)) {
			remove(friendlyURLLocalization);
		}
	}

	/**
	 * Returns the number of friendly url localizations where groupId = &#63; and friendlyURLId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param friendlyURLId the friendly url ID
	 * @return the number of matching friendly url localizations
	 */
	@Override
	public int countByG_F(long groupId, long friendlyURLId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_G_F;

		Object[] finderArgs = new Object[] { groupId, friendlyURLId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_FRIENDLYURLLOCALIZATION_WHERE);

			query.append(_FINDER_COLUMN_G_F_GROUPID_2);

			query.append(_FINDER_COLUMN_G_F_FRIENDLYURLID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(friendlyURLId);

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

	private static final String _FINDER_COLUMN_G_F_GROUPID_2 = "friendlyURLLocalization.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_F_FRIENDLYURLID_2 = "friendlyURLLocalization.friendlyURLId = ?";
	public static final FinderPath FINDER_PATH_FETCH_BY_G_F_L = new FinderPath(FriendlyURLLocalizationModelImpl.ENTITY_CACHE_ENABLED,
			FriendlyURLLocalizationModelImpl.FINDER_CACHE_ENABLED,
			FriendlyURLLocalizationImpl.class, FINDER_CLASS_NAME_ENTITY,
			"fetchByG_F_L",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				String.class.getName()
			},
			FriendlyURLLocalizationModelImpl.GROUPID_COLUMN_BITMASK |
			FriendlyURLLocalizationModelImpl.FRIENDLYURLID_COLUMN_BITMASK |
			FriendlyURLLocalizationModelImpl.LANGUAGEID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_G_F_L = new FinderPath(FriendlyURLLocalizationModelImpl.ENTITY_CACHE_ENABLED,
			FriendlyURLLocalizationModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_F_L",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				String.class.getName()
			});

	/**
	 * Returns the friendly url localization where groupId = &#63; and friendlyURLId = &#63; and languageId = &#63; or throws a {@link NoSuchFriendlyURLLocalizationException} if it could not be found.
	 *
	 * @param groupId the group ID
	 * @param friendlyURLId the friendly url ID
	 * @param languageId the language ID
	 * @return the matching friendly url localization
	 * @throws NoSuchFriendlyURLLocalizationException if a matching friendly url localization could not be found
	 */
	@Override
	public FriendlyURLLocalization findByG_F_L(long groupId,
		long friendlyURLId, String languageId)
		throws NoSuchFriendlyURLLocalizationException {
		FriendlyURLLocalization friendlyURLLocalization = fetchByG_F_L(groupId,
				friendlyURLId, languageId);

		if (friendlyURLLocalization == null) {
			StringBundler msg = new StringBundler(8);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("groupId=");
			msg.append(groupId);

			msg.append(", friendlyURLId=");
			msg.append(friendlyURLId);

			msg.append(", languageId=");
			msg.append(languageId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isDebugEnabled()) {
				_log.debug(msg.toString());
			}

			throw new NoSuchFriendlyURLLocalizationException(msg.toString());
		}

		return friendlyURLLocalization;
	}

	/**
	 * Returns the friendly url localization where groupId = &#63; and friendlyURLId = &#63; and languageId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param groupId the group ID
	 * @param friendlyURLId the friendly url ID
	 * @param languageId the language ID
	 * @return the matching friendly url localization, or <code>null</code> if a matching friendly url localization could not be found
	 */
	@Override
	public FriendlyURLLocalization fetchByG_F_L(long groupId,
		long friendlyURLId, String languageId) {
		return fetchByG_F_L(groupId, friendlyURLId, languageId, true);
	}

	/**
	 * Returns the friendly url localization where groupId = &#63; and friendlyURLId = &#63; and languageId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param groupId the group ID
	 * @param friendlyURLId the friendly url ID
	 * @param languageId the language ID
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the matching friendly url localization, or <code>null</code> if a matching friendly url localization could not be found
	 */
	@Override
	public FriendlyURLLocalization fetchByG_F_L(long groupId,
		long friendlyURLId, String languageId, boolean retrieveFromCache) {
		Object[] finderArgs = new Object[] { groupId, friendlyURLId, languageId };

		Object result = null;

		if (retrieveFromCache) {
			result = finderCache.getResult(FINDER_PATH_FETCH_BY_G_F_L,
					finderArgs, this);
		}

		if (result instanceof FriendlyURLLocalization) {
			FriendlyURLLocalization friendlyURLLocalization = (FriendlyURLLocalization)result;

			if ((groupId != friendlyURLLocalization.getGroupId()) ||
					(friendlyURLId != friendlyURLLocalization.getFriendlyURLId()) ||
					!Objects.equals(languageId,
						friendlyURLLocalization.getLanguageId())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(5);

			query.append(_SQL_SELECT_FRIENDLYURLLOCALIZATION_WHERE);

			query.append(_FINDER_COLUMN_G_F_L_GROUPID_2);

			query.append(_FINDER_COLUMN_G_F_L_FRIENDLYURLID_2);

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

				qPos.add(friendlyURLId);

				if (bindLanguageId) {
					qPos.add(languageId);
				}

				List<FriendlyURLLocalization> list = q.list();

				if (list.isEmpty()) {
					finderCache.putResult(FINDER_PATH_FETCH_BY_G_F_L,
						finderArgs, list);
				}
				else {
					FriendlyURLLocalization friendlyURLLocalization = list.get(0);

					result = friendlyURLLocalization;

					cacheResult(friendlyURLLocalization);

					if ((friendlyURLLocalization.getGroupId() != groupId) ||
							(friendlyURLLocalization.getFriendlyURLId() != friendlyURLId) ||
							(friendlyURLLocalization.getLanguageId() == null) ||
							!friendlyURLLocalization.getLanguageId()
														.equals(languageId)) {
						finderCache.putResult(FINDER_PATH_FETCH_BY_G_F_L,
							finderArgs, friendlyURLLocalization);
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
			return (FriendlyURLLocalization)result;
		}
	}

	/**
	 * Removes the friendly url localization where groupId = &#63; and friendlyURLId = &#63; and languageId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param friendlyURLId the friendly url ID
	 * @param languageId the language ID
	 * @return the friendly url localization that was removed
	 */
	@Override
	public FriendlyURLLocalization removeByG_F_L(long groupId,
		long friendlyURLId, String languageId)
		throws NoSuchFriendlyURLLocalizationException {
		FriendlyURLLocalization friendlyURLLocalization = findByG_F_L(groupId,
				friendlyURLId, languageId);

		return remove(friendlyURLLocalization);
	}

	/**
	 * Returns the number of friendly url localizations where groupId = &#63; and friendlyURLId = &#63; and languageId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param friendlyURLId the friendly url ID
	 * @param languageId the language ID
	 * @return the number of matching friendly url localizations
	 */
	@Override
	public int countByG_F_L(long groupId, long friendlyURLId, String languageId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_G_F_L;

		Object[] finderArgs = new Object[] { groupId, friendlyURLId, languageId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_COUNT_FRIENDLYURLLOCALIZATION_WHERE);

			query.append(_FINDER_COLUMN_G_F_L_GROUPID_2);

			query.append(_FINDER_COLUMN_G_F_L_FRIENDLYURLID_2);

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

				qPos.add(friendlyURLId);

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

	private static final String _FINDER_COLUMN_G_F_L_GROUPID_2 = "friendlyURLLocalization.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_F_L_FRIENDLYURLID_2 = "friendlyURLLocalization.friendlyURLId = ? AND ";
	private static final String _FINDER_COLUMN_G_F_L_LANGUAGEID_1 = "friendlyURLLocalization.languageId IS NULL";
	private static final String _FINDER_COLUMN_G_F_L_LANGUAGEID_2 = "friendlyURLLocalization.languageId = ?";
	private static final String _FINDER_COLUMN_G_F_L_LANGUAGEID_3 = "(friendlyURLLocalization.languageId IS NULL OR friendlyURLLocalization.languageId = '')";
	public static final FinderPath FINDER_PATH_FETCH_BY_G_U_L = new FinderPath(FriendlyURLLocalizationModelImpl.ENTITY_CACHE_ENABLED,
			FriendlyURLLocalizationModelImpl.FINDER_CACHE_ENABLED,
			FriendlyURLLocalizationImpl.class, FINDER_CLASS_NAME_ENTITY,
			"fetchByG_U_L",
			new String[] {
				Long.class.getName(), String.class.getName(),
				String.class.getName()
			},
			FriendlyURLLocalizationModelImpl.GROUPID_COLUMN_BITMASK |
			FriendlyURLLocalizationModelImpl.URLTITLE_COLUMN_BITMASK |
			FriendlyURLLocalizationModelImpl.LANGUAGEID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_G_U_L = new FinderPath(FriendlyURLLocalizationModelImpl.ENTITY_CACHE_ENABLED,
			FriendlyURLLocalizationModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_U_L",
			new String[] {
				Long.class.getName(), String.class.getName(),
				String.class.getName()
			});

	/**
	 * Returns the friendly url localization where groupId = &#63; and urlTitle = &#63; and languageId = &#63; or throws a {@link NoSuchFriendlyURLLocalizationException} if it could not be found.
	 *
	 * @param groupId the group ID
	 * @param urlTitle the url title
	 * @param languageId the language ID
	 * @return the matching friendly url localization
	 * @throws NoSuchFriendlyURLLocalizationException if a matching friendly url localization could not be found
	 */
	@Override
	public FriendlyURLLocalization findByG_U_L(long groupId, String urlTitle,
		String languageId) throws NoSuchFriendlyURLLocalizationException {
		FriendlyURLLocalization friendlyURLLocalization = fetchByG_U_L(groupId,
				urlTitle, languageId);

		if (friendlyURLLocalization == null) {
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

			throw new NoSuchFriendlyURLLocalizationException(msg.toString());
		}

		return friendlyURLLocalization;
	}

	/**
	 * Returns the friendly url localization where groupId = &#63; and urlTitle = &#63; and languageId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param groupId the group ID
	 * @param urlTitle the url title
	 * @param languageId the language ID
	 * @return the matching friendly url localization, or <code>null</code> if a matching friendly url localization could not be found
	 */
	@Override
	public FriendlyURLLocalization fetchByG_U_L(long groupId, String urlTitle,
		String languageId) {
		return fetchByG_U_L(groupId, urlTitle, languageId, true);
	}

	/**
	 * Returns the friendly url localization where groupId = &#63; and urlTitle = &#63; and languageId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param groupId the group ID
	 * @param urlTitle the url title
	 * @param languageId the language ID
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the matching friendly url localization, or <code>null</code> if a matching friendly url localization could not be found
	 */
	@Override
	public FriendlyURLLocalization fetchByG_U_L(long groupId, String urlTitle,
		String languageId, boolean retrieveFromCache) {
		Object[] finderArgs = new Object[] { groupId, urlTitle, languageId };

		Object result = null;

		if (retrieveFromCache) {
			result = finderCache.getResult(FINDER_PATH_FETCH_BY_G_U_L,
					finderArgs, this);
		}

		if (result instanceof FriendlyURLLocalization) {
			FriendlyURLLocalization friendlyURLLocalization = (FriendlyURLLocalization)result;

			if ((groupId != friendlyURLLocalization.getGroupId()) ||
					!Objects.equals(urlTitle,
						friendlyURLLocalization.getUrlTitle()) ||
					!Objects.equals(languageId,
						friendlyURLLocalization.getLanguageId())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(5);

			query.append(_SQL_SELECT_FRIENDLYURLLOCALIZATION_WHERE);

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

				List<FriendlyURLLocalization> list = q.list();

				if (list.isEmpty()) {
					finderCache.putResult(FINDER_PATH_FETCH_BY_G_U_L,
						finderArgs, list);
				}
				else {
					FriendlyURLLocalization friendlyURLLocalization = list.get(0);

					result = friendlyURLLocalization;

					cacheResult(friendlyURLLocalization);

					if ((friendlyURLLocalization.getGroupId() != groupId) ||
							(friendlyURLLocalization.getUrlTitle() == null) ||
							!friendlyURLLocalization.getUrlTitle()
														.equals(urlTitle) ||
							(friendlyURLLocalization.getLanguageId() == null) ||
							!friendlyURLLocalization.getLanguageId()
														.equals(languageId)) {
						finderCache.putResult(FINDER_PATH_FETCH_BY_G_U_L,
							finderArgs, friendlyURLLocalization);
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
			return (FriendlyURLLocalization)result;
		}
	}

	/**
	 * Removes the friendly url localization where groupId = &#63; and urlTitle = &#63; and languageId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param urlTitle the url title
	 * @param languageId the language ID
	 * @return the friendly url localization that was removed
	 */
	@Override
	public FriendlyURLLocalization removeByG_U_L(long groupId, String urlTitle,
		String languageId) throws NoSuchFriendlyURLLocalizationException {
		FriendlyURLLocalization friendlyURLLocalization = findByG_U_L(groupId,
				urlTitle, languageId);

		return remove(friendlyURLLocalization);
	}

	/**
	 * Returns the number of friendly url localizations where groupId = &#63; and urlTitle = &#63; and languageId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param urlTitle the url title
	 * @param languageId the language ID
	 * @return the number of matching friendly url localizations
	 */
	@Override
	public int countByG_U_L(long groupId, String urlTitle, String languageId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_G_U_L;

		Object[] finderArgs = new Object[] { groupId, urlTitle, languageId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_COUNT_FRIENDLYURLLOCALIZATION_WHERE);

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

	private static final String _FINDER_COLUMN_G_U_L_GROUPID_2 = "friendlyURLLocalization.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_U_L_URLTITLE_1 = "friendlyURLLocalization.urlTitle IS NULL AND ";
	private static final String _FINDER_COLUMN_G_U_L_URLTITLE_2 = "friendlyURLLocalization.urlTitle = ? AND ";
	private static final String _FINDER_COLUMN_G_U_L_URLTITLE_3 = "(friendlyURLLocalization.urlTitle IS NULL OR friendlyURLLocalization.urlTitle = '') AND ";
	private static final String _FINDER_COLUMN_G_U_L_LANGUAGEID_1 = "friendlyURLLocalization.languageId IS NULL";
	private static final String _FINDER_COLUMN_G_U_L_LANGUAGEID_2 = "friendlyURLLocalization.languageId = ?";
	private static final String _FINDER_COLUMN_G_U_L_LANGUAGEID_3 = "(friendlyURLLocalization.languageId IS NULL OR friendlyURLLocalization.languageId = '')";

	public FriendlyURLLocalizationPersistenceImpl() {
		setModelClass(FriendlyURLLocalization.class);
	}

	/**
	 * Caches the friendly url localization in the entity cache if it is enabled.
	 *
	 * @param friendlyURLLocalization the friendly url localization
	 */
	@Override
	public void cacheResult(FriendlyURLLocalization friendlyURLLocalization) {
		entityCache.putResult(FriendlyURLLocalizationModelImpl.ENTITY_CACHE_ENABLED,
			FriendlyURLLocalizationImpl.class,
			friendlyURLLocalization.getPrimaryKey(), friendlyURLLocalization);

		finderCache.putResult(FINDER_PATH_FETCH_BY_G_F_L,
			new Object[] {
				friendlyURLLocalization.getGroupId(),
				friendlyURLLocalization.getFriendlyURLId(),
				friendlyURLLocalization.getLanguageId()
			}, friendlyURLLocalization);

		finderCache.putResult(FINDER_PATH_FETCH_BY_G_U_L,
			new Object[] {
				friendlyURLLocalization.getGroupId(),
				friendlyURLLocalization.getUrlTitle(),
				friendlyURLLocalization.getLanguageId()
			}, friendlyURLLocalization);

		friendlyURLLocalization.resetOriginalValues();
	}

	/**
	 * Caches the friendly url localizations in the entity cache if it is enabled.
	 *
	 * @param friendlyURLLocalizations the friendly url localizations
	 */
	@Override
	public void cacheResult(
		List<FriendlyURLLocalization> friendlyURLLocalizations) {
		for (FriendlyURLLocalization friendlyURLLocalization : friendlyURLLocalizations) {
			if (entityCache.getResult(
						FriendlyURLLocalizationModelImpl.ENTITY_CACHE_ENABLED,
						FriendlyURLLocalizationImpl.class,
						friendlyURLLocalization.getPrimaryKey()) == null) {
				cacheResult(friendlyURLLocalization);
			}
			else {
				friendlyURLLocalization.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all friendly url localizations.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(FriendlyURLLocalizationImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the friendly url localization.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(FriendlyURLLocalization friendlyURLLocalization) {
		entityCache.removeResult(FriendlyURLLocalizationModelImpl.ENTITY_CACHE_ENABLED,
			FriendlyURLLocalizationImpl.class,
			friendlyURLLocalization.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache((FriendlyURLLocalizationModelImpl)friendlyURLLocalization,
			true);
	}

	@Override
	public void clearCache(
		List<FriendlyURLLocalization> friendlyURLLocalizations) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (FriendlyURLLocalization friendlyURLLocalization : friendlyURLLocalizations) {
			entityCache.removeResult(FriendlyURLLocalizationModelImpl.ENTITY_CACHE_ENABLED,
				FriendlyURLLocalizationImpl.class,
				friendlyURLLocalization.getPrimaryKey());

			clearUniqueFindersCache((FriendlyURLLocalizationModelImpl)friendlyURLLocalization,
				true);
		}
	}

	protected void cacheUniqueFindersCache(
		FriendlyURLLocalizationModelImpl friendlyURLLocalizationModelImpl) {
		Object[] args = new Object[] {
				friendlyURLLocalizationModelImpl.getGroupId(),
				friendlyURLLocalizationModelImpl.getFriendlyURLId(),
				friendlyURLLocalizationModelImpl.getLanguageId()
			};

		finderCache.putResult(FINDER_PATH_COUNT_BY_G_F_L, args,
			Long.valueOf(1), false);
		finderCache.putResult(FINDER_PATH_FETCH_BY_G_F_L, args,
			friendlyURLLocalizationModelImpl, false);

		args = new Object[] {
				friendlyURLLocalizationModelImpl.getGroupId(),
				friendlyURLLocalizationModelImpl.getUrlTitle(),
				friendlyURLLocalizationModelImpl.getLanguageId()
			};

		finderCache.putResult(FINDER_PATH_COUNT_BY_G_U_L, args,
			Long.valueOf(1), false);
		finderCache.putResult(FINDER_PATH_FETCH_BY_G_U_L, args,
			friendlyURLLocalizationModelImpl, false);
	}

	protected void clearUniqueFindersCache(
		FriendlyURLLocalizationModelImpl friendlyURLLocalizationModelImpl,
		boolean clearCurrent) {
		if (clearCurrent) {
			Object[] args = new Object[] {
					friendlyURLLocalizationModelImpl.getGroupId(),
					friendlyURLLocalizationModelImpl.getFriendlyURLId(),
					friendlyURLLocalizationModelImpl.getLanguageId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_G_F_L, args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_G_F_L, args);
		}

		if ((friendlyURLLocalizationModelImpl.getColumnBitmask() &
				FINDER_PATH_FETCH_BY_G_F_L.getColumnBitmask()) != 0) {
			Object[] args = new Object[] {
					friendlyURLLocalizationModelImpl.getOriginalGroupId(),
					friendlyURLLocalizationModelImpl.getOriginalFriendlyURLId(),
					friendlyURLLocalizationModelImpl.getOriginalLanguageId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_G_F_L, args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_G_F_L, args);
		}

		if (clearCurrent) {
			Object[] args = new Object[] {
					friendlyURLLocalizationModelImpl.getGroupId(),
					friendlyURLLocalizationModelImpl.getUrlTitle(),
					friendlyURLLocalizationModelImpl.getLanguageId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_G_U_L, args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_G_U_L, args);
		}

		if ((friendlyURLLocalizationModelImpl.getColumnBitmask() &
				FINDER_PATH_FETCH_BY_G_U_L.getColumnBitmask()) != 0) {
			Object[] args = new Object[] {
					friendlyURLLocalizationModelImpl.getOriginalGroupId(),
					friendlyURLLocalizationModelImpl.getOriginalUrlTitle(),
					friendlyURLLocalizationModelImpl.getOriginalLanguageId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_G_U_L, args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_G_U_L, args);
		}
	}

	/**
	 * Creates a new friendly url localization with the primary key. Does not add the friendly url localization to the database.
	 *
	 * @param friendlyURLLocalizationId the primary key for the new friendly url localization
	 * @return the new friendly url localization
	 */
	@Override
	public FriendlyURLLocalization create(long friendlyURLLocalizationId) {
		FriendlyURLLocalization friendlyURLLocalization = new FriendlyURLLocalizationImpl();

		friendlyURLLocalization.setNew(true);
		friendlyURLLocalization.setPrimaryKey(friendlyURLLocalizationId);

		friendlyURLLocalization.setCompanyId(companyProvider.getCompanyId());

		return friendlyURLLocalization;
	}

	/**
	 * Removes the friendly url localization with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param friendlyURLLocalizationId the primary key of the friendly url localization
	 * @return the friendly url localization that was removed
	 * @throws NoSuchFriendlyURLLocalizationException if a friendly url localization with the primary key could not be found
	 */
	@Override
	public FriendlyURLLocalization remove(long friendlyURLLocalizationId)
		throws NoSuchFriendlyURLLocalizationException {
		return remove((Serializable)friendlyURLLocalizationId);
	}

	/**
	 * Removes the friendly url localization with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the friendly url localization
	 * @return the friendly url localization that was removed
	 * @throws NoSuchFriendlyURLLocalizationException if a friendly url localization with the primary key could not be found
	 */
	@Override
	public FriendlyURLLocalization remove(Serializable primaryKey)
		throws NoSuchFriendlyURLLocalizationException {
		Session session = null;

		try {
			session = openSession();

			FriendlyURLLocalization friendlyURLLocalization = (FriendlyURLLocalization)session.get(FriendlyURLLocalizationImpl.class,
					primaryKey);

			if (friendlyURLLocalization == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchFriendlyURLLocalizationException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(friendlyURLLocalization);
		}
		catch (NoSuchFriendlyURLLocalizationException nsee) {
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
	protected FriendlyURLLocalization removeImpl(
		FriendlyURLLocalization friendlyURLLocalization) {
		friendlyURLLocalization = toUnwrappedModel(friendlyURLLocalization);

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(friendlyURLLocalization)) {
				friendlyURLLocalization = (FriendlyURLLocalization)session.get(FriendlyURLLocalizationImpl.class,
						friendlyURLLocalization.getPrimaryKeyObj());
			}

			if (friendlyURLLocalization != null) {
				session.delete(friendlyURLLocalization);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (friendlyURLLocalization != null) {
			clearCache(friendlyURLLocalization);
		}

		return friendlyURLLocalization;
	}

	@Override
	public FriendlyURLLocalization updateImpl(
		FriendlyURLLocalization friendlyURLLocalization) {
		friendlyURLLocalization = toUnwrappedModel(friendlyURLLocalization);

		boolean isNew = friendlyURLLocalization.isNew();

		FriendlyURLLocalizationModelImpl friendlyURLLocalizationModelImpl = (FriendlyURLLocalizationModelImpl)friendlyURLLocalization;

		Session session = null;

		try {
			session = openSession();

			if (friendlyURLLocalization.isNew()) {
				session.save(friendlyURLLocalization);

				friendlyURLLocalization.setNew(false);
			}
			else {
				friendlyURLLocalization = (FriendlyURLLocalization)session.merge(friendlyURLLocalization);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (isNew || !FriendlyURLLocalizationModelImpl.COLUMN_BITMASK_ENABLED) {
			finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}

		else {
			if ((friendlyURLLocalizationModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_F.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						friendlyURLLocalizationModelImpl.getOriginalGroupId(),
						friendlyURLLocalizationModelImpl.getOriginalFriendlyURLId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_G_F, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_F,
					args);

				args = new Object[] {
						friendlyURLLocalizationModelImpl.getGroupId(),
						friendlyURLLocalizationModelImpl.getFriendlyURLId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_G_F, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_F,
					args);
			}
		}

		entityCache.putResult(FriendlyURLLocalizationModelImpl.ENTITY_CACHE_ENABLED,
			FriendlyURLLocalizationImpl.class,
			friendlyURLLocalization.getPrimaryKey(), friendlyURLLocalization,
			false);

		clearUniqueFindersCache(friendlyURLLocalizationModelImpl, false);
		cacheUniqueFindersCache(friendlyURLLocalizationModelImpl);

		friendlyURLLocalization.resetOriginalValues();

		return friendlyURLLocalization;
	}

	protected FriendlyURLLocalization toUnwrappedModel(
		FriendlyURLLocalization friendlyURLLocalization) {
		if (friendlyURLLocalization instanceof FriendlyURLLocalizationImpl) {
			return friendlyURLLocalization;
		}

		FriendlyURLLocalizationImpl friendlyURLLocalizationImpl = new FriendlyURLLocalizationImpl();

		friendlyURLLocalizationImpl.setNew(friendlyURLLocalization.isNew());
		friendlyURLLocalizationImpl.setPrimaryKey(friendlyURLLocalization.getPrimaryKey());

		friendlyURLLocalizationImpl.setFriendlyURLLocalizationId(friendlyURLLocalization.getFriendlyURLLocalizationId());
		friendlyURLLocalizationImpl.setGroupId(friendlyURLLocalization.getGroupId());
		friendlyURLLocalizationImpl.setCompanyId(friendlyURLLocalization.getCompanyId());
		friendlyURLLocalizationImpl.setFriendlyURLId(friendlyURLLocalization.getFriendlyURLId());
		friendlyURLLocalizationImpl.setUrlTitle(friendlyURLLocalization.getUrlTitle());
		friendlyURLLocalizationImpl.setLanguageId(friendlyURLLocalization.getLanguageId());

		return friendlyURLLocalizationImpl;
	}

	/**
	 * Returns the friendly url localization with the primary key or throws a {@link com.liferay.portal.kernel.exception.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the friendly url localization
	 * @return the friendly url localization
	 * @throws NoSuchFriendlyURLLocalizationException if a friendly url localization with the primary key could not be found
	 */
	@Override
	public FriendlyURLLocalization findByPrimaryKey(Serializable primaryKey)
		throws NoSuchFriendlyURLLocalizationException {
		FriendlyURLLocalization friendlyURLLocalization = fetchByPrimaryKey(primaryKey);

		if (friendlyURLLocalization == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchFriendlyURLLocalizationException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				primaryKey);
		}

		return friendlyURLLocalization;
	}

	/**
	 * Returns the friendly url localization with the primary key or throws a {@link NoSuchFriendlyURLLocalizationException} if it could not be found.
	 *
	 * @param friendlyURLLocalizationId the primary key of the friendly url localization
	 * @return the friendly url localization
	 * @throws NoSuchFriendlyURLLocalizationException if a friendly url localization with the primary key could not be found
	 */
	@Override
	public FriendlyURLLocalization findByPrimaryKey(
		long friendlyURLLocalizationId)
		throws NoSuchFriendlyURLLocalizationException {
		return findByPrimaryKey((Serializable)friendlyURLLocalizationId);
	}

	/**
	 * Returns the friendly url localization with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the friendly url localization
	 * @return the friendly url localization, or <code>null</code> if a friendly url localization with the primary key could not be found
	 */
	@Override
	public FriendlyURLLocalization fetchByPrimaryKey(Serializable primaryKey) {
		Serializable serializable = entityCache.getResult(FriendlyURLLocalizationModelImpl.ENTITY_CACHE_ENABLED,
				FriendlyURLLocalizationImpl.class, primaryKey);

		if (serializable == nullModel) {
			return null;
		}

		FriendlyURLLocalization friendlyURLLocalization = (FriendlyURLLocalization)serializable;

		if (friendlyURLLocalization == null) {
			Session session = null;

			try {
				session = openSession();

				friendlyURLLocalization = (FriendlyURLLocalization)session.get(FriendlyURLLocalizationImpl.class,
						primaryKey);

				if (friendlyURLLocalization != null) {
					cacheResult(friendlyURLLocalization);
				}
				else {
					entityCache.putResult(FriendlyURLLocalizationModelImpl.ENTITY_CACHE_ENABLED,
						FriendlyURLLocalizationImpl.class, primaryKey, nullModel);
				}
			}
			catch (Exception e) {
				entityCache.removeResult(FriendlyURLLocalizationModelImpl.ENTITY_CACHE_ENABLED,
					FriendlyURLLocalizationImpl.class, primaryKey);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return friendlyURLLocalization;
	}

	/**
	 * Returns the friendly url localization with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param friendlyURLLocalizationId the primary key of the friendly url localization
	 * @return the friendly url localization, or <code>null</code> if a friendly url localization with the primary key could not be found
	 */
	@Override
	public FriendlyURLLocalization fetchByPrimaryKey(
		long friendlyURLLocalizationId) {
		return fetchByPrimaryKey((Serializable)friendlyURLLocalizationId);
	}

	@Override
	public Map<Serializable, FriendlyURLLocalization> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {
		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, FriendlyURLLocalization> map = new HashMap<Serializable, FriendlyURLLocalization>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			FriendlyURLLocalization friendlyURLLocalization = fetchByPrimaryKey(primaryKey);

			if (friendlyURLLocalization != null) {
				map.put(primaryKey, friendlyURLLocalization);
			}

			return map;
		}

		Set<Serializable> uncachedPrimaryKeys = null;

		for (Serializable primaryKey : primaryKeys) {
			Serializable serializable = entityCache.getResult(FriendlyURLLocalizationModelImpl.ENTITY_CACHE_ENABLED,
					FriendlyURLLocalizationImpl.class, primaryKey);

			if (serializable != nullModel) {
				if (serializable == null) {
					if (uncachedPrimaryKeys == null) {
						uncachedPrimaryKeys = new HashSet<Serializable>();
					}

					uncachedPrimaryKeys.add(primaryKey);
				}
				else {
					map.put(primaryKey, (FriendlyURLLocalization)serializable);
				}
			}
		}

		if (uncachedPrimaryKeys == null) {
			return map;
		}

		StringBundler query = new StringBundler((uncachedPrimaryKeys.size() * 2) +
				1);

		query.append(_SQL_SELECT_FRIENDLYURLLOCALIZATION_WHERE_PKS_IN);

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

			for (FriendlyURLLocalization friendlyURLLocalization : (List<FriendlyURLLocalization>)q.list()) {
				map.put(friendlyURLLocalization.getPrimaryKeyObj(),
					friendlyURLLocalization);

				cacheResult(friendlyURLLocalization);

				uncachedPrimaryKeys.remove(friendlyURLLocalization.getPrimaryKeyObj());
			}

			for (Serializable primaryKey : uncachedPrimaryKeys) {
				entityCache.putResult(FriendlyURLLocalizationModelImpl.ENTITY_CACHE_ENABLED,
					FriendlyURLLocalizationImpl.class, primaryKey, nullModel);
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
	 * Returns all the friendly url localizations.
	 *
	 * @return the friendly url localizations
	 */
	@Override
	public List<FriendlyURLLocalization> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the friendly url localizations.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link FriendlyURLLocalizationModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of friendly url localizations
	 * @param end the upper bound of the range of friendly url localizations (not inclusive)
	 * @return the range of friendly url localizations
	 */
	@Override
	public List<FriendlyURLLocalization> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the friendly url localizations.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link FriendlyURLLocalizationModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of friendly url localizations
	 * @param end the upper bound of the range of friendly url localizations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of friendly url localizations
	 */
	@Override
	public List<FriendlyURLLocalization> findAll(int start, int end,
		OrderByComparator<FriendlyURLLocalization> orderByComparator) {
		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the friendly url localizations.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link FriendlyURLLocalizationModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of friendly url localizations
	 * @param end the upper bound of the range of friendly url localizations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of friendly url localizations
	 */
	@Override
	public List<FriendlyURLLocalization> findAll(int start, int end,
		OrderByComparator<FriendlyURLLocalization> orderByComparator,
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

		List<FriendlyURLLocalization> list = null;

		if (retrieveFromCache) {
			list = (List<FriendlyURLLocalization>)finderCache.getResult(finderPath,
					finderArgs, this);
		}

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 2));

				query.append(_SQL_SELECT_FRIENDLYURLLOCALIZATION);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_FRIENDLYURLLOCALIZATION;

				if (pagination) {
					sql = sql.concat(FriendlyURLLocalizationModelImpl.ORDER_BY_JPQL);
				}
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (!pagination) {
					list = (List<FriendlyURLLocalization>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<FriendlyURLLocalization>)QueryUtil.list(q,
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
	 * Removes all the friendly url localizations from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (FriendlyURLLocalization friendlyURLLocalization : findAll()) {
			remove(friendlyURLLocalization);
		}
	}

	/**
	 * Returns the number of friendly url localizations.
	 *
	 * @return the number of friendly url localizations
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_FRIENDLYURLLOCALIZATION);

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
		return FriendlyURLLocalizationModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the friendly url localization persistence.
	 */
	public void afterPropertiesSet() {
	}

	public void destroy() {
		entityCache.removeCache(FriendlyURLLocalizationImpl.class.getName());
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
	private static final String _SQL_SELECT_FRIENDLYURLLOCALIZATION = "SELECT friendlyURLLocalization FROM FriendlyURLLocalization friendlyURLLocalization";
	private static final String _SQL_SELECT_FRIENDLYURLLOCALIZATION_WHERE_PKS_IN =
		"SELECT friendlyURLLocalization FROM FriendlyURLLocalization friendlyURLLocalization WHERE friendlyURLLocalizationId IN (";
	private static final String _SQL_SELECT_FRIENDLYURLLOCALIZATION_WHERE = "SELECT friendlyURLLocalization FROM FriendlyURLLocalization friendlyURLLocalization WHERE ";
	private static final String _SQL_COUNT_FRIENDLYURLLOCALIZATION = "SELECT COUNT(friendlyURLLocalization) FROM FriendlyURLLocalization friendlyURLLocalization";
	private static final String _SQL_COUNT_FRIENDLYURLLOCALIZATION_WHERE = "SELECT COUNT(friendlyURLLocalization) FROM FriendlyURLLocalization friendlyURLLocalization WHERE ";
	private static final String _ORDER_BY_ENTITY_ALIAS = "friendlyURLLocalization.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No FriendlyURLLocalization exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No FriendlyURLLocalization exists with the key {";
	private static final Log _log = LogFactoryUtil.getLog(FriendlyURLLocalizationPersistenceImpl.class);
}