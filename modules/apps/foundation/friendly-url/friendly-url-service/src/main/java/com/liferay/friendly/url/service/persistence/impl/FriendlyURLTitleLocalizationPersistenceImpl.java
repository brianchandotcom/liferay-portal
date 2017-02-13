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

import com.liferay.friendly.url.exception.NoSuchFriendlyURLTitleLocalizationException;
import com.liferay.friendly.url.model.FriendlyURLTitleLocalization;
import com.liferay.friendly.url.model.impl.FriendlyURLTitleLocalizationImpl;
import com.liferay.friendly.url.model.impl.FriendlyURLTitleLocalizationModelImpl;
import com.liferay.friendly.url.service.persistence.FriendlyURLTitleLocalizationPersistence;

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
 * The persistence implementation for the friendly url title localization service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see FriendlyURLTitleLocalizationPersistence
 * @see com.liferay.friendly.url.service.persistence.FriendlyURLTitleLocalizationUtil
 * @generated
 */
@ProviderType
public class FriendlyURLTitleLocalizationPersistenceImpl
	extends BasePersistenceImpl<FriendlyURLTitleLocalization>
	implements FriendlyURLTitleLocalizationPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link FriendlyURLTitleLocalizationUtil} to access the friendly url title localization persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = FriendlyURLTitleLocalizationImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(FriendlyURLTitleLocalizationModelImpl.ENTITY_CACHE_ENABLED,
			FriendlyURLTitleLocalizationModelImpl.FINDER_CACHE_ENABLED,
			FriendlyURLTitleLocalizationImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(FriendlyURLTitleLocalizationModelImpl.ENTITY_CACHE_ENABLED,
			FriendlyURLTitleLocalizationModelImpl.FINDER_CACHE_ENABLED,
			FriendlyURLTitleLocalizationImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(FriendlyURLTitleLocalizationModelImpl.ENTITY_CACHE_ENABLED,
			FriendlyURLTitleLocalizationModelImpl.FINDER_CACHE_ENABLED,
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll",
			new String[0]);
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_G_F = new FinderPath(FriendlyURLTitleLocalizationModelImpl.ENTITY_CACHE_ENABLED,
			FriendlyURLTitleLocalizationModelImpl.FINDER_CACHE_ENABLED,
			FriendlyURLTitleLocalizationImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_F",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_F = new FinderPath(FriendlyURLTitleLocalizationModelImpl.ENTITY_CACHE_ENABLED,
			FriendlyURLTitleLocalizationModelImpl.FINDER_CACHE_ENABLED,
			FriendlyURLTitleLocalizationImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByG_F",
			new String[] { Long.class.getName(), Long.class.getName() },
			FriendlyURLTitleLocalizationModelImpl.GROUPID_COLUMN_BITMASK |
			FriendlyURLTitleLocalizationModelImpl.FRIENDLYURLID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_G_F = new FinderPath(FriendlyURLTitleLocalizationModelImpl.ENTITY_CACHE_ENABLED,
			FriendlyURLTitleLocalizationModelImpl.FINDER_CACHE_ENABLED,
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByG_F",
			new String[] { Long.class.getName(), Long.class.getName() });

	/**
	 * Returns all the friendly url title localizations where groupId = &#63; and friendlyURLId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param friendlyURLId the friendly url ID
	 * @return the matching friendly url title localizations
	 */
	@Override
	public List<FriendlyURLTitleLocalization> findByG_F(long groupId,
		long friendlyURLId) {
		return findByG_F(groupId, friendlyURLId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the friendly url title localizations where groupId = &#63; and friendlyURLId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link FriendlyURLTitleLocalizationModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param friendlyURLId the friendly url ID
	 * @param start the lower bound of the range of friendly url title localizations
	 * @param end the upper bound of the range of friendly url title localizations (not inclusive)
	 * @return the range of matching friendly url title localizations
	 */
	@Override
	public List<FriendlyURLTitleLocalization> findByG_F(long groupId,
		long friendlyURLId, int start, int end) {
		return findByG_F(groupId, friendlyURLId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the friendly url title localizations where groupId = &#63; and friendlyURLId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link FriendlyURLTitleLocalizationModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param friendlyURLId the friendly url ID
	 * @param start the lower bound of the range of friendly url title localizations
	 * @param end the upper bound of the range of friendly url title localizations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching friendly url title localizations
	 */
	@Override
	public List<FriendlyURLTitleLocalization> findByG_F(long groupId,
		long friendlyURLId, int start, int end,
		OrderByComparator<FriendlyURLTitleLocalization> orderByComparator) {
		return findByG_F(groupId, friendlyURLId, start, end, orderByComparator,
			true);
	}

	/**
	 * Returns an ordered range of all the friendly url title localizations where groupId = &#63; and friendlyURLId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link FriendlyURLTitleLocalizationModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param friendlyURLId the friendly url ID
	 * @param start the lower bound of the range of friendly url title localizations
	 * @param end the upper bound of the range of friendly url title localizations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching friendly url title localizations
	 */
	@Override
	public List<FriendlyURLTitleLocalization> findByG_F(long groupId,
		long friendlyURLId, int start, int end,
		OrderByComparator<FriendlyURLTitleLocalization> orderByComparator,
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

		List<FriendlyURLTitleLocalization> list = null;

		if (retrieveFromCache) {
			list = (List<FriendlyURLTitleLocalization>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (FriendlyURLTitleLocalization friendlyURLTitleLocalization : list) {
					if ((groupId != friendlyURLTitleLocalization.getGroupId()) ||
							(friendlyURLId != friendlyURLTitleLocalization.getFriendlyURLId())) {
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

			query.append(_SQL_SELECT_FRIENDLYURLTITLELOCALIZATION_WHERE);

			query.append(_FINDER_COLUMN_G_F_GROUPID_2);

			query.append(_FINDER_COLUMN_G_F_FRIENDLYURLID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(FriendlyURLTitleLocalizationModelImpl.ORDER_BY_JPQL);
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
					list = (List<FriendlyURLTitleLocalization>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<FriendlyURLTitleLocalization>)QueryUtil.list(q,
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
	 * Returns the first friendly url title localization in the ordered set where groupId = &#63; and friendlyURLId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param friendlyURLId the friendly url ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching friendly url title localization
	 * @throws NoSuchFriendlyURLTitleLocalizationException if a matching friendly url title localization could not be found
	 */
	@Override
	public FriendlyURLTitleLocalization findByG_F_First(long groupId,
		long friendlyURLId,
		OrderByComparator<FriendlyURLTitleLocalization> orderByComparator)
		throws NoSuchFriendlyURLTitleLocalizationException {
		FriendlyURLTitleLocalization friendlyURLTitleLocalization = fetchByG_F_First(groupId,
				friendlyURLId, orderByComparator);

		if (friendlyURLTitleLocalization != null) {
			return friendlyURLTitleLocalization;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", friendlyURLId=");
		msg.append(friendlyURLId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchFriendlyURLTitleLocalizationException(msg.toString());
	}

	/**
	 * Returns the first friendly url title localization in the ordered set where groupId = &#63; and friendlyURLId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param friendlyURLId the friendly url ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching friendly url title localization, or <code>null</code> if a matching friendly url title localization could not be found
	 */
	@Override
	public FriendlyURLTitleLocalization fetchByG_F_First(long groupId,
		long friendlyURLId,
		OrderByComparator<FriendlyURLTitleLocalization> orderByComparator) {
		List<FriendlyURLTitleLocalization> list = findByG_F(groupId,
				friendlyURLId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last friendly url title localization in the ordered set where groupId = &#63; and friendlyURLId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param friendlyURLId the friendly url ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching friendly url title localization
	 * @throws NoSuchFriendlyURLTitleLocalizationException if a matching friendly url title localization could not be found
	 */
	@Override
	public FriendlyURLTitleLocalization findByG_F_Last(long groupId,
		long friendlyURLId,
		OrderByComparator<FriendlyURLTitleLocalization> orderByComparator)
		throws NoSuchFriendlyURLTitleLocalizationException {
		FriendlyURLTitleLocalization friendlyURLTitleLocalization = fetchByG_F_Last(groupId,
				friendlyURLId, orderByComparator);

		if (friendlyURLTitleLocalization != null) {
			return friendlyURLTitleLocalization;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", friendlyURLId=");
		msg.append(friendlyURLId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchFriendlyURLTitleLocalizationException(msg.toString());
	}

	/**
	 * Returns the last friendly url title localization in the ordered set where groupId = &#63; and friendlyURLId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param friendlyURLId the friendly url ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching friendly url title localization, or <code>null</code> if a matching friendly url title localization could not be found
	 */
	@Override
	public FriendlyURLTitleLocalization fetchByG_F_Last(long groupId,
		long friendlyURLId,
		OrderByComparator<FriendlyURLTitleLocalization> orderByComparator) {
		int count = countByG_F(groupId, friendlyURLId);

		if (count == 0) {
			return null;
		}

		List<FriendlyURLTitleLocalization> list = findByG_F(groupId,
				friendlyURLId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the friendly url title localizations before and after the current friendly url title localization in the ordered set where groupId = &#63; and friendlyURLId = &#63;.
	 *
	 * @param friendlyURLTitleLocalizationId the primary key of the current friendly url title localization
	 * @param groupId the group ID
	 * @param friendlyURLId the friendly url ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next friendly url title localization
	 * @throws NoSuchFriendlyURLTitleLocalizationException if a friendly url title localization with the primary key could not be found
	 */
	@Override
	public FriendlyURLTitleLocalization[] findByG_F_PrevAndNext(
		long friendlyURLTitleLocalizationId, long groupId, long friendlyURLId,
		OrderByComparator<FriendlyURLTitleLocalization> orderByComparator)
		throws NoSuchFriendlyURLTitleLocalizationException {
		FriendlyURLTitleLocalization friendlyURLTitleLocalization = findByPrimaryKey(friendlyURLTitleLocalizationId);

		Session session = null;

		try {
			session = openSession();

			FriendlyURLTitleLocalization[] array = new FriendlyURLTitleLocalizationImpl[3];

			array[0] = getByG_F_PrevAndNext(session,
					friendlyURLTitleLocalization, groupId, friendlyURLId,
					orderByComparator, true);

			array[1] = friendlyURLTitleLocalization;

			array[2] = getByG_F_PrevAndNext(session,
					friendlyURLTitleLocalization, groupId, friendlyURLId,
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

	protected FriendlyURLTitleLocalization getByG_F_PrevAndNext(
		Session session,
		FriendlyURLTitleLocalization friendlyURLTitleLocalization,
		long groupId, long friendlyURLId,
		OrderByComparator<FriendlyURLTitleLocalization> orderByComparator,
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

		query.append(_SQL_SELECT_FRIENDLYURLTITLELOCALIZATION_WHERE);

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
			query.append(FriendlyURLTitleLocalizationModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		qPos.add(friendlyURLId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(friendlyURLTitleLocalization);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<FriendlyURLTitleLocalization> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the friendly url title localizations where groupId = &#63; and friendlyURLId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param friendlyURLId the friendly url ID
	 */
	@Override
	public void removeByG_F(long groupId, long friendlyURLId) {
		for (FriendlyURLTitleLocalization friendlyURLTitleLocalization : findByG_F(
				groupId, friendlyURLId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
				null)) {
			remove(friendlyURLTitleLocalization);
		}
	}

	/**
	 * Returns the number of friendly url title localizations where groupId = &#63; and friendlyURLId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param friendlyURLId the friendly url ID
	 * @return the number of matching friendly url title localizations
	 */
	@Override
	public int countByG_F(long groupId, long friendlyURLId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_G_F;

		Object[] finderArgs = new Object[] { groupId, friendlyURLId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_FRIENDLYURLTITLELOCALIZATION_WHERE);

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

	private static final String _FINDER_COLUMN_G_F_GROUPID_2 = "friendlyURLTitleLocalization.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_F_FRIENDLYURLID_2 = "friendlyURLTitleLocalization.friendlyURLId = ?";
	public static final FinderPath FINDER_PATH_FETCH_BY_G_F_L = new FinderPath(FriendlyURLTitleLocalizationModelImpl.ENTITY_CACHE_ENABLED,
			FriendlyURLTitleLocalizationModelImpl.FINDER_CACHE_ENABLED,
			FriendlyURLTitleLocalizationImpl.class, FINDER_CLASS_NAME_ENTITY,
			"fetchByG_F_L",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				String.class.getName()
			},
			FriendlyURLTitleLocalizationModelImpl.GROUPID_COLUMN_BITMASK |
			FriendlyURLTitleLocalizationModelImpl.FRIENDLYURLID_COLUMN_BITMASK |
			FriendlyURLTitleLocalizationModelImpl.LANGUAGEID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_G_F_L = new FinderPath(FriendlyURLTitleLocalizationModelImpl.ENTITY_CACHE_ENABLED,
			FriendlyURLTitleLocalizationModelImpl.FINDER_CACHE_ENABLED,
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByG_F_L",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				String.class.getName()
			});

	/**
	 * Returns the friendly url title localization where groupId = &#63; and friendlyURLId = &#63; and languageId = &#63; or throws a {@link NoSuchFriendlyURLTitleLocalizationException} if it could not be found.
	 *
	 * @param groupId the group ID
	 * @param friendlyURLId the friendly url ID
	 * @param languageId the language ID
	 * @return the matching friendly url title localization
	 * @throws NoSuchFriendlyURLTitleLocalizationException if a matching friendly url title localization could not be found
	 */
	@Override
	public FriendlyURLTitleLocalization findByG_F_L(long groupId,
		long friendlyURLId, String languageId)
		throws NoSuchFriendlyURLTitleLocalizationException {
		FriendlyURLTitleLocalization friendlyURLTitleLocalization = fetchByG_F_L(groupId,
				friendlyURLId, languageId);

		if (friendlyURLTitleLocalization == null) {
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

			throw new NoSuchFriendlyURLTitleLocalizationException(msg.toString());
		}

		return friendlyURLTitleLocalization;
	}

	/**
	 * Returns the friendly url title localization where groupId = &#63; and friendlyURLId = &#63; and languageId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param groupId the group ID
	 * @param friendlyURLId the friendly url ID
	 * @param languageId the language ID
	 * @return the matching friendly url title localization, or <code>null</code> if a matching friendly url title localization could not be found
	 */
	@Override
	public FriendlyURLTitleLocalization fetchByG_F_L(long groupId,
		long friendlyURLId, String languageId) {
		return fetchByG_F_L(groupId, friendlyURLId, languageId, true);
	}

	/**
	 * Returns the friendly url title localization where groupId = &#63; and friendlyURLId = &#63; and languageId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param groupId the group ID
	 * @param friendlyURLId the friendly url ID
	 * @param languageId the language ID
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the matching friendly url title localization, or <code>null</code> if a matching friendly url title localization could not be found
	 */
	@Override
	public FriendlyURLTitleLocalization fetchByG_F_L(long groupId,
		long friendlyURLId, String languageId, boolean retrieveFromCache) {
		Object[] finderArgs = new Object[] { groupId, friendlyURLId, languageId };

		Object result = null;

		if (retrieveFromCache) {
			result = finderCache.getResult(FINDER_PATH_FETCH_BY_G_F_L,
					finderArgs, this);
		}

		if (result instanceof FriendlyURLTitleLocalization) {
			FriendlyURLTitleLocalization friendlyURLTitleLocalization = (FriendlyURLTitleLocalization)result;

			if ((groupId != friendlyURLTitleLocalization.getGroupId()) ||
					(friendlyURLId != friendlyURLTitleLocalization.getFriendlyURLId()) ||
					!Objects.equals(languageId,
						friendlyURLTitleLocalization.getLanguageId())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(5);

			query.append(_SQL_SELECT_FRIENDLYURLTITLELOCALIZATION_WHERE);

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

				List<FriendlyURLTitleLocalization> list = q.list();

				if (list.isEmpty()) {
					finderCache.putResult(FINDER_PATH_FETCH_BY_G_F_L,
						finderArgs, list);
				}
				else {
					FriendlyURLTitleLocalization friendlyURLTitleLocalization = list.get(0);

					result = friendlyURLTitleLocalization;

					cacheResult(friendlyURLTitleLocalization);

					if ((friendlyURLTitleLocalization.getGroupId() != groupId) ||
							(friendlyURLTitleLocalization.getFriendlyURLId() != friendlyURLId) ||
							(friendlyURLTitleLocalization.getLanguageId() == null) ||
							!friendlyURLTitleLocalization.getLanguageId()
															 .equals(languageId)) {
						finderCache.putResult(FINDER_PATH_FETCH_BY_G_F_L,
							finderArgs, friendlyURLTitleLocalization);
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
			return (FriendlyURLTitleLocalization)result;
		}
	}

	/**
	 * Removes the friendly url title localization where groupId = &#63; and friendlyURLId = &#63; and languageId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param friendlyURLId the friendly url ID
	 * @param languageId the language ID
	 * @return the friendly url title localization that was removed
	 */
	@Override
	public FriendlyURLTitleLocalization removeByG_F_L(long groupId,
		long friendlyURLId, String languageId)
		throws NoSuchFriendlyURLTitleLocalizationException {
		FriendlyURLTitleLocalization friendlyURLTitleLocalization = findByG_F_L(groupId,
				friendlyURLId, languageId);

		return remove(friendlyURLTitleLocalization);
	}

	/**
	 * Returns the number of friendly url title localizations where groupId = &#63; and friendlyURLId = &#63; and languageId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param friendlyURLId the friendly url ID
	 * @param languageId the language ID
	 * @return the number of matching friendly url title localizations
	 */
	@Override
	public int countByG_F_L(long groupId, long friendlyURLId, String languageId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_G_F_L;

		Object[] finderArgs = new Object[] { groupId, friendlyURLId, languageId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_COUNT_FRIENDLYURLTITLELOCALIZATION_WHERE);

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

	private static final String _FINDER_COLUMN_G_F_L_GROUPID_2 = "friendlyURLTitleLocalization.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_F_L_FRIENDLYURLID_2 = "friendlyURLTitleLocalization.friendlyURLId = ? AND ";
	private static final String _FINDER_COLUMN_G_F_L_LANGUAGEID_1 = "friendlyURLTitleLocalization.languageId IS NULL";
	private static final String _FINDER_COLUMN_G_F_L_LANGUAGEID_2 = "friendlyURLTitleLocalization.languageId = ?";
	private static final String _FINDER_COLUMN_G_F_L_LANGUAGEID_3 = "(friendlyURLTitleLocalization.languageId IS NULL OR friendlyURLTitleLocalization.languageId = '')";
	public static final FinderPath FINDER_PATH_FETCH_BY_G_U_L = new FinderPath(FriendlyURLTitleLocalizationModelImpl.ENTITY_CACHE_ENABLED,
			FriendlyURLTitleLocalizationModelImpl.FINDER_CACHE_ENABLED,
			FriendlyURLTitleLocalizationImpl.class, FINDER_CLASS_NAME_ENTITY,
			"fetchByG_U_L",
			new String[] {
				Long.class.getName(), String.class.getName(),
				String.class.getName()
			},
			FriendlyURLTitleLocalizationModelImpl.GROUPID_COLUMN_BITMASK |
			FriendlyURLTitleLocalizationModelImpl.URLTITLE_COLUMN_BITMASK |
			FriendlyURLTitleLocalizationModelImpl.LANGUAGEID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_G_U_L = new FinderPath(FriendlyURLTitleLocalizationModelImpl.ENTITY_CACHE_ENABLED,
			FriendlyURLTitleLocalizationModelImpl.FINDER_CACHE_ENABLED,
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByG_U_L",
			new String[] {
				Long.class.getName(), String.class.getName(),
				String.class.getName()
			});

	/**
	 * Returns the friendly url title localization where groupId = &#63; and urlTitle = &#63; and languageId = &#63; or throws a {@link NoSuchFriendlyURLTitleLocalizationException} if it could not be found.
	 *
	 * @param groupId the group ID
	 * @param urlTitle the url title
	 * @param languageId the language ID
	 * @return the matching friendly url title localization
	 * @throws NoSuchFriendlyURLTitleLocalizationException if a matching friendly url title localization could not be found
	 */
	@Override
	public FriendlyURLTitleLocalization findByG_U_L(long groupId,
		String urlTitle, String languageId)
		throws NoSuchFriendlyURLTitleLocalizationException {
		FriendlyURLTitleLocalization friendlyURLTitleLocalization = fetchByG_U_L(groupId,
				urlTitle, languageId);

		if (friendlyURLTitleLocalization == null) {
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

			throw new NoSuchFriendlyURLTitleLocalizationException(msg.toString());
		}

		return friendlyURLTitleLocalization;
	}

	/**
	 * Returns the friendly url title localization where groupId = &#63; and urlTitle = &#63; and languageId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param groupId the group ID
	 * @param urlTitle the url title
	 * @param languageId the language ID
	 * @return the matching friendly url title localization, or <code>null</code> if a matching friendly url title localization could not be found
	 */
	@Override
	public FriendlyURLTitleLocalization fetchByG_U_L(long groupId,
		String urlTitle, String languageId) {
		return fetchByG_U_L(groupId, urlTitle, languageId, true);
	}

	/**
	 * Returns the friendly url title localization where groupId = &#63; and urlTitle = &#63; and languageId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param groupId the group ID
	 * @param urlTitle the url title
	 * @param languageId the language ID
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the matching friendly url title localization, or <code>null</code> if a matching friendly url title localization could not be found
	 */
	@Override
	public FriendlyURLTitleLocalization fetchByG_U_L(long groupId,
		String urlTitle, String languageId, boolean retrieveFromCache) {
		Object[] finderArgs = new Object[] { groupId, urlTitle, languageId };

		Object result = null;

		if (retrieveFromCache) {
			result = finderCache.getResult(FINDER_PATH_FETCH_BY_G_U_L,
					finderArgs, this);
		}

		if (result instanceof FriendlyURLTitleLocalization) {
			FriendlyURLTitleLocalization friendlyURLTitleLocalization = (FriendlyURLTitleLocalization)result;

			if ((groupId != friendlyURLTitleLocalization.getGroupId()) ||
					!Objects.equals(urlTitle,
						friendlyURLTitleLocalization.getUrlTitle()) ||
					!Objects.equals(languageId,
						friendlyURLTitleLocalization.getLanguageId())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(5);

			query.append(_SQL_SELECT_FRIENDLYURLTITLELOCALIZATION_WHERE);

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

				List<FriendlyURLTitleLocalization> list = q.list();

				if (list.isEmpty()) {
					finderCache.putResult(FINDER_PATH_FETCH_BY_G_U_L,
						finderArgs, list);
				}
				else {
					FriendlyURLTitleLocalization friendlyURLTitleLocalization = list.get(0);

					result = friendlyURLTitleLocalization;

					cacheResult(friendlyURLTitleLocalization);

					if ((friendlyURLTitleLocalization.getGroupId() != groupId) ||
							(friendlyURLTitleLocalization.getUrlTitle() == null) ||
							!friendlyURLTitleLocalization.getUrlTitle()
															 .equals(urlTitle) ||
							(friendlyURLTitleLocalization.getLanguageId() == null) ||
							!friendlyURLTitleLocalization.getLanguageId()
															 .equals(languageId)) {
						finderCache.putResult(FINDER_PATH_FETCH_BY_G_U_L,
							finderArgs, friendlyURLTitleLocalization);
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
			return (FriendlyURLTitleLocalization)result;
		}
	}

	/**
	 * Removes the friendly url title localization where groupId = &#63; and urlTitle = &#63; and languageId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param urlTitle the url title
	 * @param languageId the language ID
	 * @return the friendly url title localization that was removed
	 */
	@Override
	public FriendlyURLTitleLocalization removeByG_U_L(long groupId,
		String urlTitle, String languageId)
		throws NoSuchFriendlyURLTitleLocalizationException {
		FriendlyURLTitleLocalization friendlyURLTitleLocalization = findByG_U_L(groupId,
				urlTitle, languageId);

		return remove(friendlyURLTitleLocalization);
	}

	/**
	 * Returns the number of friendly url title localizations where groupId = &#63; and urlTitle = &#63; and languageId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param urlTitle the url title
	 * @param languageId the language ID
	 * @return the number of matching friendly url title localizations
	 */
	@Override
	public int countByG_U_L(long groupId, String urlTitle, String languageId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_G_U_L;

		Object[] finderArgs = new Object[] { groupId, urlTitle, languageId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_COUNT_FRIENDLYURLTITLELOCALIZATION_WHERE);

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

	private static final String _FINDER_COLUMN_G_U_L_GROUPID_2 = "friendlyURLTitleLocalization.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_U_L_URLTITLE_1 = "friendlyURLTitleLocalization.urlTitle IS NULL AND ";
	private static final String _FINDER_COLUMN_G_U_L_URLTITLE_2 = "friendlyURLTitleLocalization.urlTitle = ? AND ";
	private static final String _FINDER_COLUMN_G_U_L_URLTITLE_3 = "(friendlyURLTitleLocalization.urlTitle IS NULL OR friendlyURLTitleLocalization.urlTitle = '') AND ";
	private static final String _FINDER_COLUMN_G_U_L_LANGUAGEID_1 = "friendlyURLTitleLocalization.languageId IS NULL";
	private static final String _FINDER_COLUMN_G_U_L_LANGUAGEID_2 = "friendlyURLTitleLocalization.languageId = ?";
	private static final String _FINDER_COLUMN_G_U_L_LANGUAGEID_3 = "(friendlyURLTitleLocalization.languageId IS NULL OR friendlyURLTitleLocalization.languageId = '')";

	public FriendlyURLTitleLocalizationPersistenceImpl() {
		setModelClass(FriendlyURLTitleLocalization.class);
	}

	/**
	 * Caches the friendly url title localization in the entity cache if it is enabled.
	 *
	 * @param friendlyURLTitleLocalization the friendly url title localization
	 */
	@Override
	public void cacheResult(
		FriendlyURLTitleLocalization friendlyURLTitleLocalization) {
		entityCache.putResult(FriendlyURLTitleLocalizationModelImpl.ENTITY_CACHE_ENABLED,
			FriendlyURLTitleLocalizationImpl.class,
			friendlyURLTitleLocalization.getPrimaryKey(),
			friendlyURLTitleLocalization);

		finderCache.putResult(FINDER_PATH_FETCH_BY_G_F_L,
			new Object[] {
				friendlyURLTitleLocalization.getGroupId(),
				friendlyURLTitleLocalization.getFriendlyURLId(),
				friendlyURLTitleLocalization.getLanguageId()
			}, friendlyURLTitleLocalization);

		finderCache.putResult(FINDER_PATH_FETCH_BY_G_U_L,
			new Object[] {
				friendlyURLTitleLocalization.getGroupId(),
				friendlyURLTitleLocalization.getUrlTitle(),
				friendlyURLTitleLocalization.getLanguageId()
			}, friendlyURLTitleLocalization);

		friendlyURLTitleLocalization.resetOriginalValues();
	}

	/**
	 * Caches the friendly url title localizations in the entity cache if it is enabled.
	 *
	 * @param friendlyURLTitleLocalizations the friendly url title localizations
	 */
	@Override
	public void cacheResult(
		List<FriendlyURLTitleLocalization> friendlyURLTitleLocalizations) {
		for (FriendlyURLTitleLocalization friendlyURLTitleLocalization : friendlyURLTitleLocalizations) {
			if (entityCache.getResult(
						FriendlyURLTitleLocalizationModelImpl.ENTITY_CACHE_ENABLED,
						FriendlyURLTitleLocalizationImpl.class,
						friendlyURLTitleLocalization.getPrimaryKey()) == null) {
				cacheResult(friendlyURLTitleLocalization);
			}
			else {
				friendlyURLTitleLocalization.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all friendly url title localizations.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(FriendlyURLTitleLocalizationImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the friendly url title localization.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(
		FriendlyURLTitleLocalization friendlyURLTitleLocalization) {
		entityCache.removeResult(FriendlyURLTitleLocalizationModelImpl.ENTITY_CACHE_ENABLED,
			FriendlyURLTitleLocalizationImpl.class,
			friendlyURLTitleLocalization.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache((FriendlyURLTitleLocalizationModelImpl)friendlyURLTitleLocalization,
			true);
	}

	@Override
	public void clearCache(
		List<FriendlyURLTitleLocalization> friendlyURLTitleLocalizations) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (FriendlyURLTitleLocalization friendlyURLTitleLocalization : friendlyURLTitleLocalizations) {
			entityCache.removeResult(FriendlyURLTitleLocalizationModelImpl.ENTITY_CACHE_ENABLED,
				FriendlyURLTitleLocalizationImpl.class,
				friendlyURLTitleLocalization.getPrimaryKey());

			clearUniqueFindersCache((FriendlyURLTitleLocalizationModelImpl)friendlyURLTitleLocalization,
				true);
		}
	}

	protected void cacheUniqueFindersCache(
		FriendlyURLTitleLocalizationModelImpl friendlyURLTitleLocalizationModelImpl) {
		Object[] args = new Object[] {
				friendlyURLTitleLocalizationModelImpl.getGroupId(),
				friendlyURLTitleLocalizationModelImpl.getFriendlyURLId(),
				friendlyURLTitleLocalizationModelImpl.getLanguageId()
			};

		finderCache.putResult(FINDER_PATH_COUNT_BY_G_F_L, args,
			Long.valueOf(1), false);
		finderCache.putResult(FINDER_PATH_FETCH_BY_G_F_L, args,
			friendlyURLTitleLocalizationModelImpl, false);

		args = new Object[] {
				friendlyURLTitleLocalizationModelImpl.getGroupId(),
				friendlyURLTitleLocalizationModelImpl.getUrlTitle(),
				friendlyURLTitleLocalizationModelImpl.getLanguageId()
			};

		finderCache.putResult(FINDER_PATH_COUNT_BY_G_U_L, args,
			Long.valueOf(1), false);
		finderCache.putResult(FINDER_PATH_FETCH_BY_G_U_L, args,
			friendlyURLTitleLocalizationModelImpl, false);
	}

	protected void clearUniqueFindersCache(
		FriendlyURLTitleLocalizationModelImpl friendlyURLTitleLocalizationModelImpl,
		boolean clearCurrent) {
		if (clearCurrent) {
			Object[] args = new Object[] {
					friendlyURLTitleLocalizationModelImpl.getGroupId(),
					friendlyURLTitleLocalizationModelImpl.getFriendlyURLId(),
					friendlyURLTitleLocalizationModelImpl.getLanguageId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_G_F_L, args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_G_F_L, args);
		}

		if ((friendlyURLTitleLocalizationModelImpl.getColumnBitmask() &
				FINDER_PATH_FETCH_BY_G_F_L.getColumnBitmask()) != 0) {
			Object[] args = new Object[] {
					friendlyURLTitleLocalizationModelImpl.getOriginalGroupId(),
					friendlyURLTitleLocalizationModelImpl.getOriginalFriendlyURLId(),
					friendlyURLTitleLocalizationModelImpl.getOriginalLanguageId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_G_F_L, args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_G_F_L, args);
		}

		if (clearCurrent) {
			Object[] args = new Object[] {
					friendlyURLTitleLocalizationModelImpl.getGroupId(),
					friendlyURLTitleLocalizationModelImpl.getUrlTitle(),
					friendlyURLTitleLocalizationModelImpl.getLanguageId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_G_U_L, args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_G_U_L, args);
		}

		if ((friendlyURLTitleLocalizationModelImpl.getColumnBitmask() &
				FINDER_PATH_FETCH_BY_G_U_L.getColumnBitmask()) != 0) {
			Object[] args = new Object[] {
					friendlyURLTitleLocalizationModelImpl.getOriginalGroupId(),
					friendlyURLTitleLocalizationModelImpl.getOriginalUrlTitle(),
					friendlyURLTitleLocalizationModelImpl.getOriginalLanguageId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_G_U_L, args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_G_U_L, args);
		}
	}

	/**
	 * Creates a new friendly url title localization with the primary key. Does not add the friendly url title localization to the database.
	 *
	 * @param friendlyURLTitleLocalizationId the primary key for the new friendly url title localization
	 * @return the new friendly url title localization
	 */
	@Override
	public FriendlyURLTitleLocalization create(
		long friendlyURLTitleLocalizationId) {
		FriendlyURLTitleLocalization friendlyURLTitleLocalization = new FriendlyURLTitleLocalizationImpl();

		friendlyURLTitleLocalization.setNew(true);
		friendlyURLTitleLocalization.setPrimaryKey(friendlyURLTitleLocalizationId);

		friendlyURLTitleLocalization.setCompanyId(companyProvider.getCompanyId());

		return friendlyURLTitleLocalization;
	}

	/**
	 * Removes the friendly url title localization with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param friendlyURLTitleLocalizationId the primary key of the friendly url title localization
	 * @return the friendly url title localization that was removed
	 * @throws NoSuchFriendlyURLTitleLocalizationException if a friendly url title localization with the primary key could not be found
	 */
	@Override
	public FriendlyURLTitleLocalization remove(
		long friendlyURLTitleLocalizationId)
		throws NoSuchFriendlyURLTitleLocalizationException {
		return remove((Serializable)friendlyURLTitleLocalizationId);
	}

	/**
	 * Removes the friendly url title localization with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the friendly url title localization
	 * @return the friendly url title localization that was removed
	 * @throws NoSuchFriendlyURLTitleLocalizationException if a friendly url title localization with the primary key could not be found
	 */
	@Override
	public FriendlyURLTitleLocalization remove(Serializable primaryKey)
		throws NoSuchFriendlyURLTitleLocalizationException {
		Session session = null;

		try {
			session = openSession();

			FriendlyURLTitleLocalization friendlyURLTitleLocalization = (FriendlyURLTitleLocalization)session.get(FriendlyURLTitleLocalizationImpl.class,
					primaryKey);

			if (friendlyURLTitleLocalization == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchFriendlyURLTitleLocalizationException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(friendlyURLTitleLocalization);
		}
		catch (NoSuchFriendlyURLTitleLocalizationException nsee) {
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
	protected FriendlyURLTitleLocalization removeImpl(
		FriendlyURLTitleLocalization friendlyURLTitleLocalization) {
		friendlyURLTitleLocalization = toUnwrappedModel(friendlyURLTitleLocalization);

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(friendlyURLTitleLocalization)) {
				friendlyURLTitleLocalization = (FriendlyURLTitleLocalization)session.get(FriendlyURLTitleLocalizationImpl.class,
						friendlyURLTitleLocalization.getPrimaryKeyObj());
			}

			if (friendlyURLTitleLocalization != null) {
				session.delete(friendlyURLTitleLocalization);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (friendlyURLTitleLocalization != null) {
			clearCache(friendlyURLTitleLocalization);
		}

		return friendlyURLTitleLocalization;
	}

	@Override
	public FriendlyURLTitleLocalization updateImpl(
		FriendlyURLTitleLocalization friendlyURLTitleLocalization) {
		friendlyURLTitleLocalization = toUnwrappedModel(friendlyURLTitleLocalization);

		boolean isNew = friendlyURLTitleLocalization.isNew();

		FriendlyURLTitleLocalizationModelImpl friendlyURLTitleLocalizationModelImpl =
			(FriendlyURLTitleLocalizationModelImpl)friendlyURLTitleLocalization;

		Session session = null;

		try {
			session = openSession();

			if (friendlyURLTitleLocalization.isNew()) {
				session.save(friendlyURLTitleLocalization);

				friendlyURLTitleLocalization.setNew(false);
			}
			else {
				friendlyURLTitleLocalization = (FriendlyURLTitleLocalization)session.merge(friendlyURLTitleLocalization);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (isNew ||
				!FriendlyURLTitleLocalizationModelImpl.COLUMN_BITMASK_ENABLED) {
			finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}

		else {
			if ((friendlyURLTitleLocalizationModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_F.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						friendlyURLTitleLocalizationModelImpl.getOriginalGroupId(),
						friendlyURLTitleLocalizationModelImpl.getOriginalFriendlyURLId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_G_F, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_F,
					args);

				args = new Object[] {
						friendlyURLTitleLocalizationModelImpl.getGroupId(),
						friendlyURLTitleLocalizationModelImpl.getFriendlyURLId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_G_F, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_F,
					args);
			}
		}

		entityCache.putResult(FriendlyURLTitleLocalizationModelImpl.ENTITY_CACHE_ENABLED,
			FriendlyURLTitleLocalizationImpl.class,
			friendlyURLTitleLocalization.getPrimaryKey(),
			friendlyURLTitleLocalization, false);

		clearUniqueFindersCache(friendlyURLTitleLocalizationModelImpl, false);
		cacheUniqueFindersCache(friendlyURLTitleLocalizationModelImpl);

		friendlyURLTitleLocalization.resetOriginalValues();

		return friendlyURLTitleLocalization;
	}

	protected FriendlyURLTitleLocalization toUnwrappedModel(
		FriendlyURLTitleLocalization friendlyURLTitleLocalization) {
		if (friendlyURLTitleLocalization instanceof FriendlyURLTitleLocalizationImpl) {
			return friendlyURLTitleLocalization;
		}

		FriendlyURLTitleLocalizationImpl friendlyURLTitleLocalizationImpl = new FriendlyURLTitleLocalizationImpl();

		friendlyURLTitleLocalizationImpl.setNew(friendlyURLTitleLocalization.isNew());
		friendlyURLTitleLocalizationImpl.setPrimaryKey(friendlyURLTitleLocalization.getPrimaryKey());

		friendlyURLTitleLocalizationImpl.setFriendlyURLTitleLocalizationId(friendlyURLTitleLocalization.getFriendlyURLTitleLocalizationId());
		friendlyURLTitleLocalizationImpl.setGroupId(friendlyURLTitleLocalization.getGroupId());
		friendlyURLTitleLocalizationImpl.setCompanyId(friendlyURLTitleLocalization.getCompanyId());
		friendlyURLTitleLocalizationImpl.setFriendlyURLId(friendlyURLTitleLocalization.getFriendlyURLId());
		friendlyURLTitleLocalizationImpl.setUrlTitle(friendlyURLTitleLocalization.getUrlTitle());
		friendlyURLTitleLocalizationImpl.setLanguageId(friendlyURLTitleLocalization.getLanguageId());

		return friendlyURLTitleLocalizationImpl;
	}

	/**
	 * Returns the friendly url title localization with the primary key or throws a {@link com.liferay.portal.kernel.exception.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the friendly url title localization
	 * @return the friendly url title localization
	 * @throws NoSuchFriendlyURLTitleLocalizationException if a friendly url title localization with the primary key could not be found
	 */
	@Override
	public FriendlyURLTitleLocalization findByPrimaryKey(
		Serializable primaryKey)
		throws NoSuchFriendlyURLTitleLocalizationException {
		FriendlyURLTitleLocalization friendlyURLTitleLocalization = fetchByPrimaryKey(primaryKey);

		if (friendlyURLTitleLocalization == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchFriendlyURLTitleLocalizationException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				primaryKey);
		}

		return friendlyURLTitleLocalization;
	}

	/**
	 * Returns the friendly url title localization with the primary key or throws a {@link NoSuchFriendlyURLTitleLocalizationException} if it could not be found.
	 *
	 * @param friendlyURLTitleLocalizationId the primary key of the friendly url title localization
	 * @return the friendly url title localization
	 * @throws NoSuchFriendlyURLTitleLocalizationException if a friendly url title localization with the primary key could not be found
	 */
	@Override
	public FriendlyURLTitleLocalization findByPrimaryKey(
		long friendlyURLTitleLocalizationId)
		throws NoSuchFriendlyURLTitleLocalizationException {
		return findByPrimaryKey((Serializable)friendlyURLTitleLocalizationId);
	}

	/**
	 * Returns the friendly url title localization with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the friendly url title localization
	 * @return the friendly url title localization, or <code>null</code> if a friendly url title localization with the primary key could not be found
	 */
	@Override
	public FriendlyURLTitleLocalization fetchByPrimaryKey(
		Serializable primaryKey) {
		Serializable serializable = entityCache.getResult(FriendlyURLTitleLocalizationModelImpl.ENTITY_CACHE_ENABLED,
				FriendlyURLTitleLocalizationImpl.class, primaryKey);

		if (serializable == nullModel) {
			return null;
		}

		FriendlyURLTitleLocalization friendlyURLTitleLocalization = (FriendlyURLTitleLocalization)serializable;

		if (friendlyURLTitleLocalization == null) {
			Session session = null;

			try {
				session = openSession();

				friendlyURLTitleLocalization = (FriendlyURLTitleLocalization)session.get(FriendlyURLTitleLocalizationImpl.class,
						primaryKey);

				if (friendlyURLTitleLocalization != null) {
					cacheResult(friendlyURLTitleLocalization);
				}
				else {
					entityCache.putResult(FriendlyURLTitleLocalizationModelImpl.ENTITY_CACHE_ENABLED,
						FriendlyURLTitleLocalizationImpl.class, primaryKey,
						nullModel);
				}
			}
			catch (Exception e) {
				entityCache.removeResult(FriendlyURLTitleLocalizationModelImpl.ENTITY_CACHE_ENABLED,
					FriendlyURLTitleLocalizationImpl.class, primaryKey);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return friendlyURLTitleLocalization;
	}

	/**
	 * Returns the friendly url title localization with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param friendlyURLTitleLocalizationId the primary key of the friendly url title localization
	 * @return the friendly url title localization, or <code>null</code> if a friendly url title localization with the primary key could not be found
	 */
	@Override
	public FriendlyURLTitleLocalization fetchByPrimaryKey(
		long friendlyURLTitleLocalizationId) {
		return fetchByPrimaryKey((Serializable)friendlyURLTitleLocalizationId);
	}

	@Override
	public Map<Serializable, FriendlyURLTitleLocalization> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {
		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, FriendlyURLTitleLocalization> map = new HashMap<Serializable, FriendlyURLTitleLocalization>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			FriendlyURLTitleLocalization friendlyURLTitleLocalization = fetchByPrimaryKey(primaryKey);

			if (friendlyURLTitleLocalization != null) {
				map.put(primaryKey, friendlyURLTitleLocalization);
			}

			return map;
		}

		Set<Serializable> uncachedPrimaryKeys = null;

		for (Serializable primaryKey : primaryKeys) {
			Serializable serializable = entityCache.getResult(FriendlyURLTitleLocalizationModelImpl.ENTITY_CACHE_ENABLED,
					FriendlyURLTitleLocalizationImpl.class, primaryKey);

			if (serializable != nullModel) {
				if (serializable == null) {
					if (uncachedPrimaryKeys == null) {
						uncachedPrimaryKeys = new HashSet<Serializable>();
					}

					uncachedPrimaryKeys.add(primaryKey);
				}
				else {
					map.put(primaryKey,
						(FriendlyURLTitleLocalization)serializable);
				}
			}
		}

		if (uncachedPrimaryKeys == null) {
			return map;
		}

		StringBundler query = new StringBundler((uncachedPrimaryKeys.size() * 2) +
				1);

		query.append(_SQL_SELECT_FRIENDLYURLTITLELOCALIZATION_WHERE_PKS_IN);

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

			for (FriendlyURLTitleLocalization friendlyURLTitleLocalization : (List<FriendlyURLTitleLocalization>)q.list()) {
				map.put(friendlyURLTitleLocalization.getPrimaryKeyObj(),
					friendlyURLTitleLocalization);

				cacheResult(friendlyURLTitleLocalization);

				uncachedPrimaryKeys.remove(friendlyURLTitleLocalization.getPrimaryKeyObj());
			}

			for (Serializable primaryKey : uncachedPrimaryKeys) {
				entityCache.putResult(FriendlyURLTitleLocalizationModelImpl.ENTITY_CACHE_ENABLED,
					FriendlyURLTitleLocalizationImpl.class, primaryKey,
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
	 * Returns all the friendly url title localizations.
	 *
	 * @return the friendly url title localizations
	 */
	@Override
	public List<FriendlyURLTitleLocalization> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the friendly url title localizations.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link FriendlyURLTitleLocalizationModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of friendly url title localizations
	 * @param end the upper bound of the range of friendly url title localizations (not inclusive)
	 * @return the range of friendly url title localizations
	 */
	@Override
	public List<FriendlyURLTitleLocalization> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the friendly url title localizations.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link FriendlyURLTitleLocalizationModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of friendly url title localizations
	 * @param end the upper bound of the range of friendly url title localizations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of friendly url title localizations
	 */
	@Override
	public List<FriendlyURLTitleLocalization> findAll(int start, int end,
		OrderByComparator<FriendlyURLTitleLocalization> orderByComparator) {
		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the friendly url title localizations.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link FriendlyURLTitleLocalizationModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of friendly url title localizations
	 * @param end the upper bound of the range of friendly url title localizations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of friendly url title localizations
	 */
	@Override
	public List<FriendlyURLTitleLocalization> findAll(int start, int end,
		OrderByComparator<FriendlyURLTitleLocalization> orderByComparator,
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

		List<FriendlyURLTitleLocalization> list = null;

		if (retrieveFromCache) {
			list = (List<FriendlyURLTitleLocalization>)finderCache.getResult(finderPath,
					finderArgs, this);
		}

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 2));

				query.append(_SQL_SELECT_FRIENDLYURLTITLELOCALIZATION);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_FRIENDLYURLTITLELOCALIZATION;

				if (pagination) {
					sql = sql.concat(FriendlyURLTitleLocalizationModelImpl.ORDER_BY_JPQL);
				}
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (!pagination) {
					list = (List<FriendlyURLTitleLocalization>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<FriendlyURLTitleLocalization>)QueryUtil.list(q,
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
	 * Removes all the friendly url title localizations from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (FriendlyURLTitleLocalization friendlyURLTitleLocalization : findAll()) {
			remove(friendlyURLTitleLocalization);
		}
	}

	/**
	 * Returns the number of friendly url title localizations.
	 *
	 * @return the number of friendly url title localizations
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_FRIENDLYURLTITLELOCALIZATION);

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
		return FriendlyURLTitleLocalizationModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the friendly url title localization persistence.
	 */
	public void afterPropertiesSet() {
	}

	public void destroy() {
		entityCache.removeCache(FriendlyURLTitleLocalizationImpl.class.getName());
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
	private static final String _SQL_SELECT_FRIENDLYURLTITLELOCALIZATION = "SELECT friendlyURLTitleLocalization FROM FriendlyURLTitleLocalization friendlyURLTitleLocalization";
	private static final String _SQL_SELECT_FRIENDLYURLTITLELOCALIZATION_WHERE_PKS_IN =
		"SELECT friendlyURLTitleLocalization FROM FriendlyURLTitleLocalization friendlyURLTitleLocalization WHERE friendlyURLTitleLocalizationId IN (";
	private static final String _SQL_SELECT_FRIENDLYURLTITLELOCALIZATION_WHERE = "SELECT friendlyURLTitleLocalization FROM FriendlyURLTitleLocalization friendlyURLTitleLocalization WHERE ";
	private static final String _SQL_COUNT_FRIENDLYURLTITLELOCALIZATION = "SELECT COUNT(friendlyURLTitleLocalization) FROM FriendlyURLTitleLocalization friendlyURLTitleLocalization";
	private static final String _SQL_COUNT_FRIENDLYURLTITLELOCALIZATION_WHERE = "SELECT COUNT(friendlyURLTitleLocalization) FROM FriendlyURLTitleLocalization friendlyURLTitleLocalization WHERE ";
	private static final String _ORDER_BY_ENTITY_ALIAS = "friendlyURLTitleLocalization.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No FriendlyURLTitleLocalization exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No FriendlyURLTitleLocalization exists with the key {";
	private static final Log _log = LogFactoryUtil.getLog(FriendlyURLTitleLocalizationPersistenceImpl.class);
}